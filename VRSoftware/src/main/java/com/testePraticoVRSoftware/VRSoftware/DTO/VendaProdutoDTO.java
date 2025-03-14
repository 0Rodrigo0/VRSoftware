package com.testePraticoVRSoftware.VRSoftware.DTO;

import java.util.UUID;

public class VendaProdutoDTO {
	private UUID produtoId;
	private Integer quantidade;

	public VendaProdutoDTO() {
	}

	public VendaProdutoDTO(UUID produtoId, Integer quantidade) {
		this.produtoId = produtoId;
		this.quantidade = quantidade;
	}

	public UUID getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(UUID produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
