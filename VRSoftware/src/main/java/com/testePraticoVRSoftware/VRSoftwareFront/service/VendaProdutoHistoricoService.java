package com.testePraticoVRSoftware.VRSoftwareFront.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;

@Service
public class VendaProdutoHistoricoService {
	
	public VendaProdutoHistoricoService(RestTemplate restTemplate) {
	}

	public VendaProdutoHistorico[] buscarTodos() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/venda-produto-historico";
			ResponseEntity<VendaProdutoHistorico[]> response = restTemplate.getForEntity(url, VendaProdutoHistorico[].class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return new VendaProdutoHistorico[0];
		}
	}

}
