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

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.service.ProdutoService;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	@Inject
	private ProdutoService produtoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto incluirProduto(@RequestBody Produto produto) {
		return produtoService.salvarProduto(produto);
	}

	@GetMapping("/{id}")
	public Optional<Produto> consultarProduto(@PathVariable UUID id) {
		return produtoService.buscarPorId(id);
	}

	@GetMapping
	public List<Produto> listarProdutod() {
		return produtoService.listarTodos();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> alterarProduto(@PathVariable UUID id, @RequestBody Produto produtoAtualizado) {
		return produtoService.atualizarProduto(id, produtoAtualizado).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarProduto(@PathVariable UUID id) {
		produtoService.deletarProduto(id);
	}

}
