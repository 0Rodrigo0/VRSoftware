package com.testePraticoVRSoftware.VRSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
