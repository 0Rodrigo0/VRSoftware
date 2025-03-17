package com.testePraticoVRSoftware.VRSoftwareFront.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
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

}
