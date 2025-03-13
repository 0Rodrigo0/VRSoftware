package com.testePraticoVRSoftware.VRSoftware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

}
