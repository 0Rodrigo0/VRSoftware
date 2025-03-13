package com.testePraticoVRSoftware.VRSoftware.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftware.repository.ClienteRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.ProdutoRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaProdutoHistoricoRepository;
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
	private VendaProdutoHistoricoRepository vendaProdutoHistoricoRepository;

	public Venda salvarVenda(Venda venda) {

		Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

		List<Produto> produtos = venda.getProdutos().stream()
				.map(produto -> produtoRepository.findById(produto.getId())
						.orElseThrow(() -> new RuntimeException("Produto não encontrado: " + produto.getId())))
				.collect(Collectors.toList());

		BigDecimal valorTotal = produtos.stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);

		verificaVencimentoFatura(cliente);

		venda.setCliente(cliente);
		venda.setProdutos(produtos);
		venda.setValorTotal(valorTotal);
		venda.setDataVenda(LocalDate.now());
		Venda vendaSalva = vendaRepository.save(venda);

		salvarHistoricoVenda(venda);

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
			venda.setProdutos(vendaAtualizada.getProdutos());
			return vendaRepository.save(venda);
		});
	}

	public void excluir(UUID id) {
		vendaRepository.deleteById(id);
	}

	public List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
		return vendaRepository.findByDataVendaBetween(inicio, fim);
	}

	@Transactional
	public void salvarHistoricoVenda(Venda venda) {
		List<VendaProdutoHistorico> historico = venda.getProdutos().stream().map(produto -> {
			VendaProdutoHistorico registro = new VendaProdutoHistorico();
			registro.setVendaId(venda.getId());
			registro.setClienteId(venda.getCliente().getId());
			registro.setClienteNome(venda.getCliente().getNome());
			registro.setProdutoId(produto.getId());
			registro.setProdutoDescricao(produto.getDescricao());
			registro.setProdutoPreco(produto.getPreco());
			registro.setDataVenda(venda.getDataVenda());
			registro.setValorTotal(venda.getValorTotal());
			return registro;
		}).collect(Collectors.toList());

		vendaProdutoHistoricoRepository.saveAll(historico);
	}

	private void verificaVencimentoFatura(Cliente cliente) {
		LocalDate dataFechamento = calcularDiaVencimento(cliente.getDiaFechamentoFatura());
		if (LocalDate.now().isAfter(dataFechamento)) {
			String saldoCreditoRelatorio = calcularSaldoCredito(cliente);
			System.out.println(saldoCreditoRelatorio);
		}

	}

	private String calcularSaldoCredito(Cliente cliente) {
		LocalDate dataFechamento = calcularDiaVencimento(cliente.getDiaFechamentoFatura());
		List<Venda> vendasRealizadas = vendaRepository.findByClienteIdAfterDiaFechamento(cliente.getId(),
				dataFechamento);

		BigDecimal totalCompras = vendasRealizadas.stream().map(Venda::getValorTotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

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

}
