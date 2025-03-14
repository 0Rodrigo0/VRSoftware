package com.testePraticoVRSoftware.VRSoftware.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class VendaProdutoId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID vendaId;
    private UUID produtoId;

    public VendaProdutoId() {}

    public VendaProdutoId(UUID vendaId, UUID produtoId) {
        this.vendaId = vendaId;
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaProdutoId that = (VendaProdutoId) o;
        return Objects.equals(vendaId, that.vendaId) &&
               Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendaId, produtoId);
    }
}


