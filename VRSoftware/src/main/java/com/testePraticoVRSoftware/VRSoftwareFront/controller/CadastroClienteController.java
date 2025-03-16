package com.testePraticoVRSoftware.VRSoftwareFront.controller;

import java.math.BigDecimal;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftwareFront.service.CadastroClienteService;
import com.testePraticoVRSoftware.VRSoftwareFront.view.CadastroClienteFrame;
import com.testePraticoVRSoftware.VRSoftwareFront.view.RealizarVendaFrame;

public class CadastroClienteController {

	private CadastroClienteService cadastroClienteService;
	private CadastroClienteFrame cadastroClienteFrame;

	public CadastroClienteController(CadastroClienteFrame cadastroClienteFrame, RestTemplate restTemplate) {
		this.cadastroClienteFrame = cadastroClienteFrame;
		this.cadastroClienteService = new CadastroClienteService(restTemplate);
	}
	
	public CadastroClienteController(RealizarVendaFrame realizarVendaFrame, RestTemplate restTemplate) {
		this.cadastroClienteService = new CadastroClienteService(restTemplate);
	}

	public void salvarCliente(String nome, String limiteCompra, String diaFechamento) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setLimiteCompra(new BigDecimal(limiteCompra));
		cliente.setDiaFechamentoFatura(Integer.parseInt(diaFechamento));

		if (cadastroClienteService.salvarCliente(cliente)) {
			JOptionPane.showMessageDialog(cadastroClienteFrame, "Cliente " + nome + " cadastrado com sucesso!");
			cadastroClienteFrame.carregarClientes();
		} else {
			JOptionPane.showMessageDialog(cadastroClienteFrame, "Erro ao cadastrar cliente.");
		}
	}

	public void excluirCliente(UUID idCliente) {
		cadastroClienteService.excluirCliente(idCliente);
	}

	public Cliente[] carregarClientes() {
		return cadastroClienteService.listarClientes();
	}

	public void atualizarCliente(UUID idCliente, String novoNome, String novoLimite, String novoDiaFechamento) {
		cadastroClienteService.atualizarCliente(idCliente, novoNome, novoLimite, novoDiaFechamento);
		
	}
}
