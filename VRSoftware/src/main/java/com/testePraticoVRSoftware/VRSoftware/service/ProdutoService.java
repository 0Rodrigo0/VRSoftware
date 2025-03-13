package com.testePraticoVRSoftware.VRSoftware.service;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.repository.ProdutoRepository;

import jakarta.inject.Inject;

@Service
public class ProdutoService {

	@Inject
	private ProdutoRepository produtoRepository;

	public Produto salvarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}
}
