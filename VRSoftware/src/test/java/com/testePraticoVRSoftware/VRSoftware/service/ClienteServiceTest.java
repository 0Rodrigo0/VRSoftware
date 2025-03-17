package com.testePraticoVRSoftware.VRSoftware.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.repository.ClienteRepository;
import com.testePraticoVRSoftware.VRSoftware.repository.VendaRepository;

@SpringBootTest
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private VendaRepository vendaRepository;

	@InjectMocks
	private ClienteService clienteService;

	private Cliente cliente;
	private Cliente cliente1;
	private Cliente cliente2;
	private Cliente clienteAtualizado;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		cliente = new Cliente();
		cliente.setId(UUID.randomUUID());
		cliente.setNome("João Silva");
		cliente.setLimiteCompra(BigDecimal.TEN);
		cliente.setDiaFechamentoFatura(10);

		cliente1 = new Cliente();
		cliente1.setId(UUID.randomUUID());
		cliente1.setNome("João");
		cliente1.setLimiteCompra(BigDecimal.TEN);
		cliente1.setDiaFechamentoFatura(10);

		cliente2 = new Cliente();
		cliente1.setId(UUID.randomUUID());
		cliente1.setNome("Pedro");
		cliente1.setLimiteCompra(BigDecimal.ONE);
		cliente1.setDiaFechamentoFatura(5);

		clienteAtualizado = new Cliente();
		// clienteAtualizado.setId(cliente.getId());
		clienteAtualizado.setNome("Pedro");
		clienteAtualizado.setLimiteCompra(BigDecimal.ONE);
		clienteAtualizado.setDiaFechamentoFatura(5);

	}

	@Test
	public void testSalvarCliente() {
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

		Cliente clienteSalvo = clienteService.salvarCliente(cliente);

		verify(clienteRepository).save(cliente);

		assertNotNull(clienteSalvo);
		assertEquals(cliente.getId(), clienteSalvo.getId());
		assertEquals(cliente.getNome(), clienteSalvo.getNome());
		assertEquals(cliente.getLimiteCompra(), clienteSalvo.getLimiteCompra());
		assertEquals(cliente.getDiaFechamentoFatura(), clienteSalvo.getDiaFechamentoFatura());
	}

	@Test
	public void testBuscarPorId_ClienteExistente() {
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

		Optional<Cliente> resultado = clienteService.buscarPorId(cliente.getId());

		assertTrue(resultado.isPresent());
		assertEquals(cliente, resultado.get());
		verify(clienteRepository, times(1)).findById(cliente.getId());
	}

	@Test
	public void testBuscarPorId_ClienteNaoExistente() {
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

		Optional<Cliente> resultado = clienteService.buscarPorId(cliente.getId());

		assertFalse(resultado.isPresent());
		verify(clienteRepository, times(1)).findById(cliente.getId());
	}

	@Test
	public void testListarTodos_ClientesExistentes() {
		when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

		List<Cliente> clientes = clienteService.listarTodos();

		assertNotNull(clientes);
		assertEquals(2, clientes.size());
		assertTrue(clientes.contains(cliente1));
		assertTrue(clientes.contains(cliente2));
		verify(clienteRepository, times(1)).findAll();
	}

	@Test
	public void testListarTodos_NenhumCliente() {
		when(clienteRepository.findAll()).thenReturn(Arrays.asList());

		List<Cliente> clientes = clienteService.listarTodos();

		assertNotNull(clientes);
		assertEquals(0, clientes.size());
		verify(clienteRepository, times(1)).findAll();
	}

	@Test
	public void testAtualizarCliente_ClienteExistente() {
		when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

		when(clienteRepository.save(cliente)).thenReturn(cliente);

		Optional<Cliente> clienteRetornado = clienteService.atualizarCliente(cliente.getId(), clienteAtualizado);

		assertTrue(clienteRetornado.isPresent());

		assertEquals(cliente.getId(), clienteRetornado.get().getId());
		assertEquals(clienteAtualizado.getNome(), clienteRetornado.get().getNome());
		verify(clienteRepository, times(1)).findById(cliente.getId());
		verify(clienteRepository, times(1)).save(cliente);
	}

	@Test
	public void testExcluirCliente_ClienteExistente() {
		UUID clienteId = UUID.randomUUID();

		clienteService.excluirCliente(clienteId);

		verify(vendaRepository, times(1)).deletarVendasPorCliente(clienteId);
		verify(clienteRepository, times(1)).deletarClientePorId(clienteId);
	}
}
