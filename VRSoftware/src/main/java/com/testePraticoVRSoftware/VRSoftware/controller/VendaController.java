package com.testePraticoVRSoftware.VRSoftware.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftware.service.VendaService;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

	@Inject
	private VendaService vendaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Venda incluirVenda(@RequestBody Venda venda) {
		return vendaService.salvarVenda(venda);
	}

	@GetMapping("/{id}")
	public Optional<Venda> consultarVenda(@PathVariable UUID id) {
		return vendaService.buscarPorId(id);
	}

	@GetMapping
	public List<Venda> listarVendas() {
		return vendaService.listarTodos();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Venda> alterarVenda(@PathVariable UUID id, @RequestBody Venda vendaAtualizada) {
		return vendaService.atualizarVenda(id, vendaAtualizada).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/periodo")
	public List<Venda> buscarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
		return vendaService.buscarPorPeriodo(inicio, fim);
	}
}
