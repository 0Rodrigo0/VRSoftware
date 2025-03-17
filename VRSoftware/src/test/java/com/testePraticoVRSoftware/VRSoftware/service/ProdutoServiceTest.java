package com.testePraticoVRSoftware.VRSoftware.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftware.repository.ProdutoRepository;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private Produto produto1;
    private Produto produto2;
    private Produto produtoAtualizado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setDescricao("Notebook");
        produto.setPreco(new BigDecimal(100));
        
        produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setDescricao("Produto 1");
        produto1.setPreco(new BigDecimal(100));

        produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setDescricao("Produto 2");
        produto2.setPreco(new BigDecimal(100));
        
        produtoAtualizado = new Produto();
        produtoAtualizado = new Produto();
        produtoAtualizado.setDescricao("Produto Novo");
        produtoAtualizado.setPreco(new BigDecimal(100));
    }

    @Test
    public void testSalvarProduto() {
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoSalvo = produtoService.salvarProduto(produto);

        assertEquals(produto, produtoSalvo);

        verify(produtoRepository, times(1)).save(produto);
    }
    
    @Test
    public void testBuscarPorId_ProdutoExistente() {
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Optional<Produto> produtoBuscado = produtoService.buscarPorId(produto.getId());

        assertTrue(produtoBuscado.isPresent());
        assertEquals(produto.getId(), produtoBuscado.get().getId());
        assertEquals(produto.getDescricao(), produtoBuscado.get().getDescricao());

        verify(produtoRepository, times(1)).findById(produto.getId());
    }
    
    @Test
    public void testListarTodos_ProdutosExistentes() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);
        produtos.add(produto2);

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> produtosRetornados = produtoService.listarTodos();

        assertNotNull(produtosRetornados);
        assertEquals(2, produtosRetornados.size());
        assertTrue(produtosRetornados.contains(produto1));
        assertTrue(produtosRetornados.contains(produto2));

        verify(produtoRepository, times(1)).findAll();
    }
    
    @Test
    public void testAtualizarProduto_ProdutoExistente() {
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Produto> produtoRetornado = produtoService.atualizarProduto(produto.getId(), produtoAtualizado);

        assertTrue(produtoRetornado.isPresent());
        assertEquals(produto.getId(), produtoRetornado.get().getId());
        assertEquals(produtoAtualizado.getDescricao(), produtoRetornado.get().getDescricao());
        assertEquals(produtoAtualizado.getPreco(), produtoRetornado.get().getPreco());

        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(produtoRepository, times(1)).save(produto);
    }
    
    @Test
    public void testDeletarProduto_ProdutoExistente() {
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        produtoService.deletarProduto(produto.getId());

        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(produtoRepository, times(1)).removerProdutoDasVendas(produto.getId());
        verify(produtoRepository, times(1)).delete(produto);
    }
    
    @Test
    public void testDeletarProduto_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            produtoService.deletarProduto(produto.getId());
        });

        assertEquals("Produto não encontrado", exception.getMessage());

        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(produtoRepository, never()).removerProdutoDasVendas(any(UUID.class));
        verify(produtoRepository, never()).delete(any(Produto.class));
    }
}
