package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.math.BigDecimal;

public class ProdutoSelecionado {
	private String descricao;
	private BigDecimal preco;
	private int quantidade;

	public ProdutoSelecionado(String descricao, BigDecimal preco, int quantidade) {
		this.descricao = descricao;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
