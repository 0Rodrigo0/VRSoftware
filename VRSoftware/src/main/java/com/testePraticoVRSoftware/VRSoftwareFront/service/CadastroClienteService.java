package com.testePraticoVRSoftware.VRSoftwareFront.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;

public class CadastroClienteService {

	public CadastroClienteService(RestTemplate restTemplate) {
	}

	public boolean salvarCliente(Cliente cliente) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/cliente";
			ResponseEntity<String> response = restTemplate.postForEntity(url, cliente, String.class);
			return response.getStatusCode() == HttpStatus.CREATED;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Cliente[] listarClientes() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/cliente";
			ResponseEntity<Cliente[]> response = restTemplate.getForEntity(url, Cliente[].class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return new Cliente[0];
		}
	}
}
