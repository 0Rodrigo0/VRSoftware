package com.testePraticoVRSoftware.VRSoftware.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftware.service.VendaProdutoHistoricoService;

@RestController
@RequestMapping("/api/venda-produto-historico")
public class VendaProdutoHistoricoController {

	private final VendaProdutoHistoricoService service;

	@Autowired
	public VendaProdutoHistoricoController(VendaProdutoHistoricoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<VendaProdutoHistorico> salvar(@RequestBody VendaProdutoHistorico vendaProdutoHistorico) {
		VendaProdutoHistorico salva = service.salvar(vendaProdutoHistorico);
		return ResponseEntity.ok(salva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<VendaProdutoHistorico> buscarPorId(@PathVariable UUID id) {
		Optional<VendaProdutoHistorico> vendaProdutoHistorico = service.buscarPorId(id);
		return vendaProdutoHistorico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<VendaProdutoHistorico>> buscarTodos() {
		List<VendaProdutoHistorico> todos = service.buscarTodos();
		return ResponseEntity.ok(todos);
	}

	@PutMapping("/{id}")
	public ResponseEntity<VendaProdutoHistorico> atualizar(@PathVariable UUID id,
			@RequestBody VendaProdutoHistorico vendaProdutoHistorico) {
		VendaProdutoHistorico atualizado = service.atualizar(id, vendaProdutoHistorico);
		return atualizado != null ? ResponseEntity.ok(atualizado) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable UUID id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
