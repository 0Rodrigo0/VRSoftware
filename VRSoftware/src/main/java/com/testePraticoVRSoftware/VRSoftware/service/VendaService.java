package com.testePraticoVRSoftware.VRSoftware.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.DTO.VendaProdutoDTO;
import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftware.model.VendaProduto;
import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftware.repository.ClienteRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.ProdutoRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaProdutoHistoricoRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaProdutoRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Service
public class VendaService {

	@Inject
	private VendaRepository vendaRepository;

	@Inject
	private ClienteRepository clienteRepository;

	@Inject
	private ProdutoRepository produtoRepository;

	@Inject
	private VendaProdutoRepository vendaProdutoRepository;

	@Inject
	private VendaProdutoHistoricoRepository vendaProdutoHistoricoRepository;

	public Venda salvarVenda(Venda venda, List<VendaProdutoDTO> produtos) {

		validarProdutosDuplicados(venda.getProdutos());

		Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

		List<VendaProduto> vendaProdutos = venda.getProdutos().stream().map(dto -> {
			Produto produto = produtoRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Produto não encontrado"));

			VendaProduto vendaProduto = new VendaProduto();
			vendaProduto.setVenda(venda);
			vendaProduto.setProduto(produto);
			vendaProduto.setQuantidade(dto.getQuantidade());

			return vendaProduto;
		}).collect(Collectors.toList());

		BigDecimal valorTotal = vendaProdutos.stream()
				.map(vendaProduto -> vendaProduto.getProduto().getPreco()
						.multiply(BigDecimal.valueOf(vendaProduto.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		venda.setCliente(cliente);
		venda.setProdutos(vendaProdutos);
		venda.setValorTotal(valorTotal);
		venda.setDataVenda(LocalDate.now());
		Venda vendaSalva = vendaRepository.save(venda);

		salvarHistoricoVenda(venda);
		String relatorio = verificaVencimentoFatura(cliente);
		vendaSalva.setRelatorioCredito(relatorio);

		return vendaSalva;
	}

	public Optional<Venda> buscarPorId(UUID id) {
		return vendaRepository.findById(id);
	}

	public List<Venda> listarTodos() {
		return vendaRepository.findAll();
	}

	public Optional<Venda> atualizarVenda(UUID id, Venda vendaAtualizada) {
		return vendaRepository.findById(id).map(venda -> {

			venda.setCliente(vendaAtualizada.getCliente());
			venda.setDataVenda(vendaAtualizada.getDataVenda());
			venda.setValorTotal(vendaAtualizada.getValorTotal());

			List<VendaProduto> produtosOriginais = venda.getProdutos();
			produtosOriginais.clear();

			for (VendaProduto produtoAtualizado : vendaAtualizada.getProdutos()) {
				Optional<VendaProduto> vendaProdutoOptional = vendaProdutoRepository
						.findById(produtoAtualizado.getId());

				if (vendaProdutoOptional.isPresent()) {
					VendaProduto vendaProdutoExistente = vendaProdutoOptional.get();

					Optional<Produto> produtoOptional = produtoRepository
							.findById(vendaProdutoExistente.getProduto().getId());
					produtoOptional.ifPresent(produto -> {
						vendaProdutoExistente.setProduto(produto);
						vendaProdutoExistente.setVenda(venda);
					});

					produtosOriginais.add(vendaProdutoExistente);
				} else {
					produtoAtualizado.setVenda(venda);
					produtosOriginais.add(produtoAtualizado);
				}
			}

			return Optional.of(vendaRepository.save(venda));
		}).orElse(Optional.empty());
	}

	public void excluir(UUID id) {
		vendaRepository.deleteById(id);
	}

	public List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
		return vendaRepository.findByDataVendaBetween(inicio, fim);
	}

	@Transactional
	public void salvarHistoricoVenda(Venda venda) {
		List<VendaProdutoHistorico> historico = venda.getProdutos().stream().map(vendaProduto -> {
			Produto produto = vendaProduto.getProduto();
			Long quantidade = Long.valueOf(vendaProduto.getQuantidade());
			BigDecimal precoUnitario = produto.getPreco();

			BigDecimal valorTotalProduto = precoUnitario.multiply(new BigDecimal(quantidade));

			VendaProdutoHistorico registro = new VendaProdutoHistorico();
			registro.setVendaId(venda.getId());
			registro.setClienteId(venda.getCliente().getId());
			registro.setClienteNome(venda.getCliente().getNome());
			registro.setProdutoId(produto.getId());
			registro.setProdutoDescricao(produto.getDescricao());
			registro.setProdutoPreco(precoUnitario);
			registro.setDataVenda(venda.getDataVenda());
			registro.setValorTotal(valorTotalProduto);

			return registro;
		}).collect(Collectors.toList());

		vendaProdutoHistoricoRepository.saveAll(historico);
	}

	private String verificaVencimentoFatura(Cliente cliente) {
		LocalDate dataFechamento = calcularDiaVencimento(cliente.getDiaFechamentoFatura());
		String saldoCreditoRelatorio = null;
		if (LocalDate.now().isAfter(dataFechamento)) {
			saldoCreditoRelatorio = calcularSaldoCredito(cliente);
			System.out.println(saldoCreditoRelatorio);
		}
		return saldoCreditoRelatorio;
	}

	private String calcularSaldoCredito(Cliente cliente) {
		LocalDate dataFechamento = calcularDiaVencimento(cliente.getDiaFechamentoFatura());
		List<Venda> vendasRealizadas = vendaRepository.findByClienteIdAfterDiaFechamento(cliente.getId(),
				dataFechamento);

		BigDecimal totalCompras = vendasRealizadas.stream().map(Venda::getValorTotal).filter(valor -> valor != null)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal saldoCredito = cliente.getLimiteCompra().subtract(totalCompras);

		LocalDate proximoFechamento = calcularProximoFechamento(cliente.getDiaFechamentoFatura());
		return String.format(
				"Cliente: %s\nLimite de Crédito: %.2f\nCompras após fechamento: %.2f\nSaldo de Crédito: %.2f\nPróximo fechamento: %s",
				cliente.getNome(), cliente.getLimiteCompra(), totalCompras, saldoCredito, proximoFechamento);
	}

	private LocalDate calcularDiaVencimento(int diaFechamentoFatura) {
		LocalDate hoje = LocalDate.now();
		LocalDate proximoFechamento = hoje.withDayOfMonth(diaFechamentoFatura);
		return proximoFechamento;
	}

	private LocalDate calcularProximoFechamento(int diaFechamentoFatura) {
		LocalDate hoje = LocalDate.now();
		LocalDate proximoFechamento = hoje.withDayOfMonth(diaFechamentoFatura);
		if (hoje.isAfter(proximoFechamento)) {
			proximoFechamento = proximoFechamento.plusMonths(1);
		}
		return proximoFechamento;
	}

	private void validarProdutosDuplicados(List<VendaProduto> produtos) {
		Map<UUID, Long> produtoQuantidades = new HashMap<>();

		for (VendaProduto vendaProduto : produtos) {
			if (vendaProduto.getId() != null) {
				produtoQuantidades.put(vendaProduto.getId(),
						produtoQuantidades.getOrDefault(vendaProduto.getId(), 0L) + 1);
			}
		}

		for (Map.Entry<UUID, Long> entry : produtoQuantidades.entrySet()) {
			if (entry.getValue() > 1) {
				throw new IllegalArgumentException("Produto repetido. Utilize o campo quantidade.");
			}
		}
	}

}
