package com.testePraticoVRSoftware.VRSoftware.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "venda_produto")
public class VendaProduto {

	@Id
	@Expose
    @GeneratedValue(generator = "uuid2")
    private UUID id;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "venda_id", nullable = false)
	private Venda venda;

	@Expose
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;
	
	@Expose
	@Column(nullable = false)
	private Integer quantidade;

}
