package com.testePraticoVRSoftware.VRSoftware.model;

import java.math.BigDecimal;
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
@Table(name = "cliente")
public class Cliente {

	@Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "limite_compra", nullable = false)
    private BigDecimal limiteCompra;

    @Column(name = "dia_fechamento_fatura", nullable = false)
    private Integer diaFechamentoFatura;
}
