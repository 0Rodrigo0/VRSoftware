package com.testePraticoVRSoftware.VRSoftware.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;

import jakarta.transaction.Transactional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {

	@Modifying
	@Transactional
	@Query("DELETE FROM Venda v WHERE v.cliente.id = :clienteId")
	void deletarVendasPorCliente(@Param("clienteId") UUID clienteId);

	List<Venda> findByClienteId(UUID clienteId);

	List<Venda> findByDataVendaBetween(LocalDate inicio, LocalDate fim);

	@Query("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId AND v.dataVenda > :dataFechamento")
	List<Venda> findByClienteIdAfterDiaFechamento(@Param("clienteId") UUID clienteId,
			@Param("dataFechamento") LocalDate dataFechamento);

}
