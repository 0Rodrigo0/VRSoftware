package com.testePraticoVRSoftware.VRSoftwareFront.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

}
