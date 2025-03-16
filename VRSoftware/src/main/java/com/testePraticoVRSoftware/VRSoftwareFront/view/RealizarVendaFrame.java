package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.CadastroClienteController;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.CadastroProdutoController;

public class RealizarVendaFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tabelaProdutos;
	private JTable tabelaClientes;
	private DefaultTableModel modeloTabela;
	private DefaultTableModel modeloTabelaClientes;
	private CadastroProdutoController cadastroProdutoController;
	private CadastroClienteController cadastroClienteController;
	private DefaultListModel<String> listaModel;
	private BigDecimal totalCompra = BigDecimal.ZERO;
	private Map<String, ProdutoSelecionado> produtosSelecionados;
	private JLabel labelTotal;

	public RealizarVendaFrame(RestTemplate restTemplate) {
		setTitle("Tela de Vendas");
		setSize(1080, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		cadastroProdutoController = new CadastroProdutoController(this, restTemplate);
		cadastroClienteController = new CadastroClienteController(this, restTemplate);
		
		listaModel = new DefaultListModel<>();

		JPanel painelPrincipal = new JPanel(new GridLayout(2, 2, 10, 10));
		add(painelPrincipal, BorderLayout.CENTER);

		JPanel painelProdutos = new JPanel(new BorderLayout());
		painelProdutos.setBorder(BorderFactory.createTitledBorder("Lista de Produtos"));
		JTextField campoPesquisaProduto = new JTextField();
		modeloTabela = new DefaultTableModel(new Object[] { "ID", "Descrição", "Preço" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};

		funcaoPesquisaProduto(campoPesquisaProduto);

		tabelaProdutos = new JTable(modeloTabela);
		painelProdutos.add(campoPesquisaProduto, BorderLayout.NORTH);
		painelProdutos.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

		JPanel painelSelecionados = new JPanel(new BorderLayout());
		painelSelecionados.setBorder(BorderFactory.createTitledBorder("Produtos Selecionados"));
		JList<String> listaSelecionados = new JList<>(listaModel);
		JTextField campoQuantidade = new JTextField();
		painelSelecionados.add(new JScrollPane(listaSelecionados), BorderLayout.CENTER);
		painelSelecionados.add(campoQuantidade, BorderLayout.SOUTH);

		produtosSelecionados = new HashMap<>();

		funcaoAddProdutosListaSelecionados(listaSelecionados);

		JPanel painelClientes = new JPanel(new BorderLayout());
		painelClientes.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
		JTextField campoPesquisaCliente = new JTextField();
		modeloTabelaClientes = new DefaultTableModel(new Object[] { "ID", "Nome", "Limite", "Dia de fechamento" }, 0) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};
		
		funcaoPesquisaCliente(campoPesquisaCliente);
		
		tabelaClientes = new JTable(modeloTabelaClientes);
		painelClientes.add(campoPesquisaCliente, BorderLayout.NORTH);
		painelClientes.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

		JPanel painelResumo = new JPanel(new BorderLayout());
		painelResumo.setBorder(BorderFactory.createTitledBorder("Resumo da Compra"));
		labelTotal = new JLabel("Total: R$ 0,00");
		JButton botaoFinalizar = new JButton("Finalizar Compra");
		JButton botaoExcluirProduto = new JButton("Excluir Produto");
		JButton btnLimparCampos = new JButton("Limpar Lista");
		JButton btnHome = new JButton("Home");

		JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 5, 5));
		painelBotoes.add(botaoFinalizar);
		painelBotoes.add(botaoExcluirProduto);
		painelBotoes.add(btnLimparCampos);
		painelBotoes.add(btnHome);

		painelResumo.add(labelTotal, BorderLayout.NORTH);
		painelResumo.add(painelBotoes, BorderLayout.CENTER);

		painelPrincipal.add(painelProdutos);
		painelPrincipal.add(painelSelecionados);
		painelPrincipal.add(painelClientes);
		painelPrincipal.add(painelResumo);

		btnHome.addActionListener(e -> {
			dispose();
			new MainFrame().setVisible(true);
		});

		btnLimparCampos.addActionListener(e -> {
			produtosSelecionados.clear();
			atualizarListaSelecionados();
		});

		botaoExcluirProduto.addActionListener(e -> {
			int indexSelecionado = listaSelecionados.getSelectedIndex();
			if (indexSelecionado != -1) {
				String produtoSelecionado = listaSelecionados.getSelectedValue();
				String descricao = produtoSelecionado.split(" \\| ")[0];

				if (produtosSelecionados.containsKey(descricao)) {
					produtosSelecionados.remove(descricao);
				}

				atualizarListaSelecionados();
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
			}
		});

		carregarProdutos();
		carregarClientes();
		setVisible(true);
	}

	private void funcaoAddProdutosListaSelecionados(JList<String> listaSelecionados) {
		tabelaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = tabelaProdutos.getSelectedRow();
					if (row != -1) {

						String descricao = (String) modeloTabela.getValueAt(row, 1);
						BigDecimal preco = (BigDecimal) modeloTabela.getValueAt(row, 2);

						if (produtosSelecionados.containsKey(descricao)) {
							ProdutoSelecionado produto = produtosSelecionados.get(descricao);
							produto.setQuantidade(produto.getQuantidade() + 1);

						} else {
							produtosSelecionados.put(descricao, new ProdutoSelecionado(descricao, preco, 1));
						}

						atualizarListaSelecionados();
					}
				}
			}
		});

	}

	private void funcaoPesquisaProduto(JTextField campoPesquisaProduto) {
		campoPesquisaProduto.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarProdutos();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarProdutos();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarProdutos();
			}

			private void filtrarProdutos() {
				String query = campoPesquisaProduto.getText().toLowerCase();
				List<Object[]> produtosFiltrados = new ArrayList<>();

				Produto[] produtos = cadastroProdutoController.carregarProdutos();
				for (Produto p : produtos) {
					if (p.getDescricao().toLowerCase().contains(query)) {
						produtosFiltrados.add(new Object[] { p.getId(), p.getDescricao(), p.getPreco() });
					}
				}

				modeloTabela.setRowCount(0);
				for (Object[] produto : produtosFiltrados) {
					modeloTabela.addRow(produto);
				}
			}
		});

	}
	
	private void funcaoPesquisaCliente(JTextField campoPesquisaCliente) {
		campoPesquisaCliente.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarClientes();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarClientes();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarClientes();
			}

			private void filtrarClientes() {
				String query = campoPesquisaCliente.getText().toLowerCase();
				List<Object[]> clientesFiltrados = new ArrayList<>();

				Cliente[] clientes = cadastroClienteController.carregarClientes();
				for (Cliente c : clientes) {
					if (c.getNome().toLowerCase().contains(query)) {
						clientesFiltrados.add(new Object[] { c.getId(), c.getNome(), c.getLimiteCompra(), c.getDiaFechamentoFatura() });
					}
				}

				modeloTabelaClientes.setRowCount(0);
				for (Object[] cliente : clientesFiltrados) {
					modeloTabelaClientes.addRow(cliente);
				}
			}
		});

	}

	public void atualizarListaProdutos(Object[][] produtos) {
		modeloTabela.setRowCount(0);
		System.out.println("Produtos carregados:");
		for (Object[] produto : produtos) {
			System.out.println(Arrays.toString(produto));
			modeloTabela.addRow(produto);
		}
	}

	public void carregarProdutos() {
		Produto[] produtos = cadastroProdutoController.carregarProdutos();
		modeloTabela.setRowCount(0);
		for (Produto c : produtos) {
			modeloTabela.addRow(new Object[] { c.getId(), c.getDescricao(), c.getPreco() });
		}
	}
	
	private void carregarClientes() {
		Cliente[] clientes = cadastroClienteController.carregarClientes();
		modeloTabelaClientes.setRowCount(0);
		for (Cliente c : clientes) {
			modeloTabelaClientes.addRow(new Object[] { c.getId(), c.getNome(), c.getLimiteCompra(), c.getDiaFechamentoFatura() });
		}
		
	}

	private void atualizarListaSelecionados() {
		listaModel.clear();
		totalCompra = BigDecimal.ZERO;

		for (Map.Entry<String, ProdutoSelecionado> entry : produtosSelecionados.entrySet()) {
			String descricao = entry.getKey();
			ProdutoSelecionado produto = entry.getValue();
			int quantidade = produto.getQuantidade();
			BigDecimal preco = produto.getPreco();

			// Calculando o valor total do produto com base na quantidade
			BigDecimal totalItem = preco.multiply(new BigDecimal(quantidade));

			// Atualiza o total da compra
			totalCompra = totalCompra.add(totalItem);

			// Adiciona o produto à lista de selecionados
			listaModel.addElement(
					descricao + " | Quantidade: " + quantidade + " | Preço: " + preco + " | Total: " + totalItem);
		}

		// Atualiza o valor total da compra no resumo
		labelTotal.setText("Total: R$ " + totalCompra.setScale(2, RoundingMode.HALF_UP).toString());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new RealizarVendaFrame(null).setVisible(true));
	}

}
