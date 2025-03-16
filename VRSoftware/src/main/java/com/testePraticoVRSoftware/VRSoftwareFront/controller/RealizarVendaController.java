package com.testePraticoVRSoftware.VRSoftwareFront.controller;

import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftwareFront.service.RealizarVendaService;

public class RealizarVendaController {
	private final RealizarVendaService vendaService;

	public RealizarVendaController(RestTemplate restTemplate) {
		this.vendaService = new RealizarVendaService(restTemplate);
	}

	public void finalizarVenda(Map<String, Object> vendaPayload) {
		try {
			ResponseEntity<String> response = vendaService.salvarVenda(vendaPayload);

			if (response.getStatusCode().is2xxSuccessful()) {
				JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda: " + response.getBody());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao comunicar com o servidor: " + e.getMessage());
		}
	}

}
