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

	public Cliente salvarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Optional<Cliente> buscarPorId(UUID id) {
		return clienteRepository.findById(id);
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> atualizarCliente(UUID id, Cliente clienteAtualizado) {
		return clienteRepository.findById(id).map(cliente -> {
			cliente.setNome(clienteAtualizado.getNome());
			cliente.setLimiteCompra(clienteAtualizado.getLimiteCompra());
			cliente.setDiaFechamentoFatura(clienteAtualizado.getDiaFechamentoFatura());
			return clienteRepository.save(cliente);
		});
	}

	@Transactional
	public void excluirCliente(UUID clienteId) {
		vendaRepository.deletarVendasPorCliente(clienteId);
		clienteRepository.deletarClientePorId(clienteId);
	}

}
