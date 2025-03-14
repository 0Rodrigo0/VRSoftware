package com.testePraticoVRSoftware.VRSoftware.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "venda_produto_historico")
public class VendaProdutoHistorico {

	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID id;

	@Column(nullable = false)
	private UUID vendaId;

	@Column(nullable = false)
	private UUID clienteId;

	@Column(nullable = false, length = 100)
	private String clienteNome;

	@Column(nullable = false)
	private UUID produtoId;

	@Column(nullable = false, length = 100)
	private String produtoDescricao;

	@Column(nullable = false)
	private BigDecimal produtoPreco;

	@Column(nullable = false)
	private LocalDate dataVenda;

	@Column(nullable = false)
	private BigDecimal valorTotal;

}
