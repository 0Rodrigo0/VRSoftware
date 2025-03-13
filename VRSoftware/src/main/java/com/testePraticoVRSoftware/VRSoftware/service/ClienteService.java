package com.testePraticoVRSoftware.VRSoftware.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.repository.ClienteRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Inject
	private ClienteRepository clienteRepository;

	@Inject
	private VendaRepository vendaRepository;

	// Cadastra cliente
	public Cliente salvarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	// Lista cliente por id
	public Optional<Cliente> buscarPorId(UUID id) {
		return clienteRepository.findById(id);
	}

	// Listar Todos os Clientes
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	// Atualizar Cliente
	public Optional<Cliente> atualizarCliente(UUID id, Cliente clienteAtualizado) {
		return clienteRepository.findById(id).map(cliente -> {
			cliente.setNome(clienteAtualizado.getNome());
			cliente.setLimiteCompra(clienteAtualizado.getLimiteCompra());
			cliente.setDiaFechamentoFatura(clienteAtualizado.getDiaFechamentoFatura());
			return clienteRepository.save(cliente);
		});
	}

	// Excluir Cliente
	@Transactional
	public void excluirCliente(UUID clienteId) {
		vendaRepository.deletarVendasPorCliente(clienteId);
		clienteRepository.deletarClientePorId(clienteId);
	}

}
