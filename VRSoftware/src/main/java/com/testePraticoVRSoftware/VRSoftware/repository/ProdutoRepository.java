package com.testePraticoVRSoftware.VRSoftware.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;

import jakarta.transaction.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
		
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM venda_produto WHERE produto_id = :produtoId", nativeQuery = true)
    void removerProdutoDasVendas(@Param("produtoId") UUID produtoId);

}
