package com.testePraticoVRSoftware.VRSoftwareFront.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	public void excluirCliente(UUID idCliente) {
		String url = "http://localhost:8080/api/cliente/";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(url + idCliente);

	}

	public void atualizarCliente(UUID idCliente, String novoNome, String novoLimite, String novoDiaFechamento) {
		String url = "http://localhost:8080/api/cliente/" + idCliente;
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    String jsonBody = "{"
	        + "\"nome\":\"" + novoNome + "\","
	        + "\"limiteCompra\":\"" + novoLimite + "\","
	        + "\"diaFechamentoFatura\":\"" + novoDiaFechamento + "\""
	        + "}";
	    
	    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		
	}
}
