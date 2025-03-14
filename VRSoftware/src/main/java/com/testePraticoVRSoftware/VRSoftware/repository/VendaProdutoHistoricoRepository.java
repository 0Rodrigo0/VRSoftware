package com.testePraticoVRSoftware.VRSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;

@Repository
public interface VendaProdutoHistoricoRepository extends JpaRepository<VendaProdutoHistorico, Long>{

}
