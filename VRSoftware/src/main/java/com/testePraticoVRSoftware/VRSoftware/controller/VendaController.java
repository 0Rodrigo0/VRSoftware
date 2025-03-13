package com.testePraticoVRSoftware.VRSoftware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftware.service.VendaService;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

	@Inject
	private VendaService vendaService;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Venda incluirVenda(@RequestBody Venda venda) {
        return vendaService.salvarVenda(venda);
	}

}
