package com.testePraticoVRSoftware.VRSoftwareFront.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.model.Venda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class RealizarVendaService {

	private RestTemplate restTemplate;
    private final String API_URL = "http://localhost:8080/api/venda";

    public RealizarVendaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> salvarVenda(Map<String, Object> venda) {
    	RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(venda, headers);

        return restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);
    }
    
    public Venda[] listarVendas() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/venda";
			ResponseEntity<Venda[]> response = restTemplate.getForEntity(url, Venda[].class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return new Venda[0];
		}
	}

	public void atualizaerVenda(Venda vendaAtualizada) {
		Venda venda = buscarVenda(vendaAtualizada.getId());
		Gson gson = new GsonBuilder()
	            .excludeFieldsWithoutExposeAnnotation() 
	            .serializeNulls()
	            .create();
		String produtosJson = gson.toJson(venda.getProdutos());
		
		String url = "http://localhost:8080/api/venda/" + vendaAtualizada.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonBody = "{"
		        + "\"cliente\": {"
		        + "\"id\": \"" + vendaAtualizada.getCliente().getId() + "\""
		        + "},"
		        + "\"dataVenda\": \"" + vendaAtualizada.getDataVenda() + "\","
		        + "\"valorTotal\": \"" + vendaAtualizada.getValorTotal() + "\","
		        + "\"produtos\": " + produtosJson
		        + "}";

		
		HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
		System.out.println(entity);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
		
	}

	private Venda buscarVenda(UUID id) {
		String url = "http://localhost:8080/api/venda/" + id;
		RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Venda> response = restTemplate.exchange(url, HttpMethod.GET, null, Venda.class);
	    return response.getBody();
	}

	public Cliente buscaCliente(UUID novoClienteId) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/cliente/"+ novoClienteId;
			ResponseEntity<Cliente> response = restTemplate.getForEntity(url, Cliente.class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return new Cliente();
		}
		
	}

}
