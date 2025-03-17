package com.testePraticoVRSoftware.VRSoftware.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProduto;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, UUID> {

}
