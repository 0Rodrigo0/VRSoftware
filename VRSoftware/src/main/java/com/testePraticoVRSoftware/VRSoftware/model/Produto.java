package com.testePraticoVRSoftware.VRSoftware.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID id;

	@Column(nullable = false, length = 100)
	private String descricao;

	@Column(nullable = false)
	private BigDecimal preco;

	@JsonIgnore
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<VendaProduto> vendas;

}
