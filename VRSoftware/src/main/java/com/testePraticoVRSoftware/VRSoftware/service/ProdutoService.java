package com.testePraticoVRSoftware.VRSoftware.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.repository.ProdutoRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

	@Inject
	private ProdutoRepository produtoRepository;

	public Produto salvarProduto(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Optional<Produto> buscarPorId(UUID id) {
		return produtoRepository.findById(id);
	}

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Optional<Produto> atualizarProduto(UUID id, Produto produtoAtualizado) {
		return produtoRepository.findById(id).map(produto -> {
			produto.setDescricao(produtoAtualizado.getDescricao());
			produto.setPreco(produtoAtualizado.getPreco());
			return produtoRepository.save(produto);
		});
	}

	@Transactional
	public void deletarProduto(UUID id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		produtoRepository.removerProdutoDasVendas(id);
		produtoRepository.delete(produto);
	}

}
