package com.testePraticoVRSoftware.VRSoftware.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;

import jakarta.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

	@Modifying
	@Transactional
	@Query("DELETE FROM Cliente c WHERE c.id = :clienteId")
	void deletarClientePorId(@Param("clienteId") UUID clienteId);

}
