package com.testePraticoVRSoftware.VRSoftware.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;

import jakarta.transaction.Transactional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

	@Modifying
	@Transactional
	@Query("DELETE FROM Venda v WHERE v.cliente.id = :clienteId")
	void deletarVendasPorCliente(@Param("clienteId") UUID clienteId);

}
