package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.math.BigDecimal;
import java.util.UUID;

public class ProdutoSelecionado {
	private UUID id;
	private String descricao;
	private BigDecimal preco;
	private int quantidade;

	public ProdutoSelecionado(UUID id, String descricao, BigDecimal preco, int quantidade) {
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
