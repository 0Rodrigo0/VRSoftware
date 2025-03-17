package com.testePraticoVRSoftware.VRSoftware.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaProdutoHistoricoRepository;

@Service
public class VendaProdutoHistoricoService {

	private final VendaProdutoHistoricoRepository repository;

	@Autowired
	public VendaProdutoHistoricoService(VendaProdutoHistoricoRepository repository) {
		this.repository = repository;
	}

	public VendaProdutoHistorico salvar(VendaProdutoHistorico vendaProdutoHistorico) {
		return repository.save(vendaProdutoHistorico);
	}

	public Optional<VendaProdutoHistorico> buscarPorId(UUID id) {
		return repository.findById(id);
	}

	public List<VendaProdutoHistorico> buscarTodos() {
		return repository.findAll();
	}

	public void excluir(UUID id) {
		repository.deleteById(id);
	}

	public VendaProdutoHistorico atualizar(UUID id, VendaProdutoHistorico vendaProdutoHistorico) {
		if (!repository.existsById(id)) {
			return null;
		}
		vendaProdutoHistorico.setId(id);
		return repository.save(vendaProdutoHistorico);
	}

}
