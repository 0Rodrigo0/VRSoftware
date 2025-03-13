package com.testePraticoVRSoftware.VRSoftware.service;

import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaRepository;

import jakarta.inject.Inject;

@Service
public class VendaService {

	@Inject
	private VendaRepository vendaRepository;

	public Venda salvarVenda(Venda venda) {
		return vendaRepository.save(venda);
	}

}
