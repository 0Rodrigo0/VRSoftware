package com.testePraticoVRSoftware.VRSoftwareFront.controller;


import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftwareFront.service.VendaProdutoHistoricoService;
import com.testePraticoVRSoftware.VRSoftwareFront.view.VendaProdutoHistoricoFrame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaProdutoHistoricoController {

	private VendaProdutoHistoricoFrame vendaProdutoHistoricoFrame;
	private VendaProdutoHistoricoService vendaProdutoHistoricoService;

	public VendaProdutoHistoricoController(VendaProdutoHistoricoFrame vendaProdutoHistoricoFrame,
			RestTemplate restTemplate) {
		this.vendaProdutoHistoricoFrame = vendaProdutoHistoricoFrame;
		this.vendaProdutoHistoricoService = new VendaProdutoHistoricoService(restTemplate);
	}

	public VendaProdutoHistorico[] buscarTodos() {
		return vendaProdutoHistoricoService.buscarTodos();
	}

}
