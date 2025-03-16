package com.testePraticoVRSoftware.VRSoftwareFront.controller;

import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testePraticoVRSoftware.VRSoftwareFront.service.RealizarVendaService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class RealizarVendaController {
	private final RealizarVendaService realizarVendaService;
	private final RestTemplate restTemplate;

	public RealizarVendaController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.realizarVendaService = new RealizarVendaService(restTemplate);
	}

	public void finalizarVenda(Map<String, Object> vendaPayload) {
		try {
			ResponseEntity<String> response = realizarVendaService.salvarVenda(vendaPayload);

			if (response.getStatusCode().is2xxSuccessful()) {
				JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");

				String vendaSalva = response.getBody();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> vendaData = objectMapper.readValue(vendaSalva, Map.class);
				String relatorioCredito = (String) vendaData.get("relatorioCredito");
				if (relatorioCredito != null && !relatorioCredito.isEmpty()) {
					JOptionPane.showMessageDialog(null, relatorioCredito, "Alerta de Crédito",
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda: " + response.getBody());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao comunicar com o servidor: " + e.getMessage());
		}
	}

}
