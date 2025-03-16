package com.testePraticoVRSoftware.VRSoftwareFront.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;

public class CadastroProdutoService {

	public CadastroProdutoService(RestTemplate restTemplate) {
	}

	public Produto[] listarProdutos() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/produto";
			ResponseEntity<Produto[]> response = restTemplate.getForEntity(url, Produto[].class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return new Produto[0];
		}
	}

	public boolean salvarProduto(Produto produto) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8080/api/produto";
			ResponseEntity<String> response = restTemplate.postForEntity(url, produto, String.class);
			return response.getStatusCode() == HttpStatus.CREATED;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void atualizarProduto(UUID idProdutoSelecionado, String novaDescricao, String novoPreco) {
		String url = "http://localhost:8080/api/produto/" + idProdutoSelecionado;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String jsonBody = "{" + "\"descricao\":\"" + novaDescricao + "\"," + "\"preco\":\"" + novoPreco + "\"" + "}";

		HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

	}

	public void excluirProduto(UUID idProduto) {
		String url = "http://localhost:8080/api/produto/";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(url + idProduto);

	}

}
