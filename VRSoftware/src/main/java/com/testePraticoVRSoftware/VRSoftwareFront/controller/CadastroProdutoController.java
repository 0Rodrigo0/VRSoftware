package com.testePraticoVRSoftware.VRSoftwareFront.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftwareFront.service.CadastroProdutoService;
import com.testePraticoVRSoftware.VRSoftwareFront.view.CadastroProdutoFrame;
import com.testePraticoVRSoftware.VRSoftwareFront.view.RealizarVendaFrame;

public class CadastroProdutoController {

	private CadastroProdutoFrame cadastroProdutoFrame;
	private CadastroProdutoService cadastroProdutoService;

	public CadastroProdutoController(CadastroProdutoFrame cadastroProdutoFrame, RestTemplate restTemplate) {
		this.cadastroProdutoFrame = cadastroProdutoFrame;
		this.cadastroProdutoService = new CadastroProdutoService(restTemplate);
	}

	public CadastroProdutoController(RealizarVendaFrame realizarVendaFrame, RestTemplate restTemplate) {
		this.cadastroProdutoService = new CadastroProdutoService(restTemplate);
	}

	public Produto[] carregarProdutos() {
		return cadastroProdutoService.listarProdutos();
	}

	public void salvarProduto(String descricao, String preco) {
		Produto produto = new Produto();
		produto.setDescricao(descricao);
		produto.setPreco(new BigDecimal(preco));

		if (cadastroProdutoService.salvarProduto(produto)) {
			JOptionPane.showMessageDialog(cadastroProdutoFrame, "Produto " + descricao + " cadastrado com sucesso!");
			cadastroProdutoFrame.carregarProdutos();
		} else {
			JOptionPane.showMessageDialog(cadastroProdutoFrame, "Erro ao cadastrar produto.");
		}

	}

	public void atualizarProduto(UUID idProdutoSelecionado, String novaDescricao, String novoPreco) {
		cadastroProdutoService.atualizarProduto(idProdutoSelecionado, novaDescricao, novoPreco);

	}

	public void excluirProduto(UUID idProduto) {
		cadastroProdutoService.excluirProduto(idProduto);

	}

}
