package com.testePraticoVRSoftware.VRSoftware.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.service.ClienteService;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Inject
	private ClienteService clienteService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente incluirCliente(@RequestBody Cliente cliente) {
		return clienteService.salvarCliente(cliente);
	}

	@GetMapping("/{id}")
	public Optional<Cliente> consultarCliente(@PathVariable UUID id) {
		return clienteService.buscarPorId(id);
	}

	@GetMapping
	public List<Cliente> listarClientes() {
		return clienteService.listarTodos();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> alterarCliente(@PathVariable UUID id, @RequestBody Cliente clienteAtualizado) {
		return clienteService.atualizarCliente(id, clienteAtualizado).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirCliente(@PathVariable UUID id) {
		clienteService.excluirCliente(id);
	}

}
