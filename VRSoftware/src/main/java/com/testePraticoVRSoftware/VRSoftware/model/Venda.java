package com.testePraticoVRSoftware.VRSoftware.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(generator = "uuid2")
	public UUID id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	public Cliente cliente;

	@Column(nullable = false)
	public LocalDate dataVenda;

	@Column(nullable = false)
	public BigDecimal valorTotal;

	@ManyToMany
	@JoinTable(name = "venda_produto", joinColumns = @JoinColumn(name = "venda_id"),
	           inverseJoinColumns = @JoinColumn(name = "produto_id"))
	public List<Produto> produtos;

}
