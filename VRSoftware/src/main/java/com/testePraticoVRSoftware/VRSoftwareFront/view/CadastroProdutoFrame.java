package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Produto;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.CadastroProdutoController;
import com.testePraticoVRSoftware.VRSoftwareFront.utils.VRSoftwareUtils;

public class CadastroProdutoFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtDescricao, txtPreco;
	private JTextArea areaInformacoes;
	private JButton btnSalvar, btnAtualizarLista, btnAtualizar, btnExcluir, btnLimparCampos, btnHome;
	private JTable tabelaProdutos;
	private DefaultTableModel modeloTabela;
	private CadastroProdutoController cadastroProdutoController;
	private UUID idProdutoSelecionado;

	public CadastroProdutoFrame(RestTemplate restTemplate) {
		cadastroProdutoController = new CadastroProdutoController(this, restTemplate);

		VRSoftwareUtils.configurarJanela(this, "VR Software - Cadastro de Clientes", 1080, 800);

		JPanel panel = new JPanel();
		GroupLayout layout = VRSoftwareUtils.configurarPainelComGroupLayout(panel);

		JLabel lblDescricao = new JLabel("Descrição:");
		txtDescricao = new JTextField();
		JLabel lblPreco = new JLabel("Preço:");
		txtPreco = new JTextField();

		btnAtualizarLista = new JButton("Atualizar lista");
		btnSalvar = new JButton("Salvar");
		btnAtualizar = new JButton("Atualizar Registro");
		btnExcluir = new JButton("Excluir Registro");
		btnLimparCampos = new JButton("Limpar Campos");
		btnHome = new JButton("Home");

		btnAtualizar.setEnabled(false);

		areaInformacoes = new JTextArea();
		configuracaoInstrucoes(areaInformacoes);

		JScrollPane scrollInfo = new JScrollPane(areaInformacoes);
		scrollInfo.setPreferredSize(new Dimension(320, 130));

		configurarCampos(layout, lblDescricao, lblPreco, scrollInfo);

		add(panel, BorderLayout.NORTH);

		configurarCaixaTexto(txtDescricao, txtPreco);

		// Criando a tabela para exibir os clientes
		String[] colunas = { "ID", "Descrição", "Preço" };
		modeloTabela = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};

		tabelaProdutos = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
		configurarTabela();

		add(scrollPane, BorderLayout.CENTER);

		btnAtualizarLista.addActionListener(e -> carregarProdutos());
		btnSalvar.addActionListener(e -> salvarProduto());
		btnAtualizar.addActionListener(e -> atualizarProduto());
		btnExcluir.addActionListener(e -> excluirProduto());
		btnLimparCampos.addActionListener(e -> limparCampos());
		btnHome.addActionListener(e -> {
			dispose();
			new MainFrame().setVisible(true);
		});

		carregarProdutos();

	}

	private void configuracaoInstrucoes(JTextArea areaInformacoes) {
		areaInformacoes.setText("Preencha os campos corretamente.\n" + "- Descrição: Apenas letras.\n"
				+ "- Preço: Apenas números.\n" + "Clique em 'Salvar' para cadastrar.\n"
				+ "Clique 2x em um registro para alterar e em 'Atualizar Registro' para salvar a alteração.\n"
				+ "Para excluir selecione um registro e clique em 'Excluir'.");
		areaInformacoes.setEditable(false);
		areaInformacoes.setLineWrap(true);
		areaInformacoes.setWrapStyleWord(true);
		areaInformacoes.setPreferredSize(new Dimension(300, 90));

	}

	private void configurarCampos(GroupLayout layout, JLabel lblDescricao, JLabel lblPreco, JScrollPane scrollInfo) {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblDescricao)
						.addComponent(lblPreco))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtDescricao, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtPreco, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(btnAtualizarLista).addGap(10)
										.addComponent(btnSalvar).addGap(10).addComponent(btnAtualizar).addGap(10)
										.addComponent(btnExcluir).addGap(10).addComponent(btnLimparCampos).addGap(10)
										.addComponent(btnHome))))
				.addComponent(scrollInfo, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblDescricao)
						.addComponent(txtDescricao).addComponent(scrollInfo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPreco)
						.addComponent(txtPreco))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnAtualizarLista)
						.addComponent(btnSalvar).addComponent(btnAtualizar).addComponent(btnExcluir)
						.addComponent(btnLimparCampos).addComponent(btnHome)));

	}

	private void configurarCaixaTexto(JTextField txtDescricao, JTextField txtPreco) {
		txtDescricao.setPreferredSize(new Dimension(300, 30));
		txtDescricao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isLetter(c) && c != ' ') {
					e.consume();
				}
			}
		});

		txtPreco.setPreferredSize(new Dimension(300, 30));
		txtPreco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '.' && c != ',') {
					e.consume();
				}
			}
		});
	}

	private void configurarTabela() {
		tabelaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int linhaSelecionada = tabelaProdutos.getSelectedRow();
					if (linhaSelecionada != -1) {
						UUID idProduto = (UUID) modeloTabela.getValueAt(linhaSelecionada, 0);
						String descricaoProduto = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
						BigDecimal precoProduto = (BigDecimal) modeloTabela.getValueAt(linhaSelecionada, 2);

						txtDescricao.setText(descricaoProduto);
						txtPreco.setText(precoProduto.toString());

						idProdutoSelecionado = idProduto;

						btnSalvar.setEnabled(false);
						btnAtualizar.setEnabled(true);
					}
				}
			}
		});
	}

	public void carregarProdutos() {
		Produto[] produtos = cadastroProdutoController.carregarProdutos();
		modeloTabela.setRowCount(0);
		for (Produto c : produtos) {
			modeloTabela.addRow(new Object[] { c.getId(), c.getDescricao(), c.getPreco() });
		}
	}

	private void salvarProduto() {
		String descricao = txtDescricao.getText();
		String preco = txtPreco.getText();

		if (txtDescricao.getText().isEmpty() || txtPreco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha os campos!.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		cadastroProdutoController.salvarProduto(descricao, preco);
		limparCampos();
		carregarProdutos();

	}

	private void atualizarProduto() {
		btnAtualizar.addActionListener(e -> {

			if (txtDescricao.getText().isEmpty() || txtPreco.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha os campos!.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (idProdutoSelecionado == null) {
				JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			String novaDescricao = txtDescricao.getText();
			String novoPreco = txtPreco.getText();

			try {
				cadastroProdutoController.atualizarProduto(idProdutoSelecionado, novaDescricao, novoPreco);
				JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception er) {
				JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + er.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

			limparCampos();
			carregarProdutos();
			btnAtualizar.setEnabled(false);
			btnSalvar.setEnabled(true);
		});

	}

	private void excluirProduto() {
		int linhaSelecionada = tabelaProdutos.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este produto?",
				"Confirmação", JOptionPane.YES_NO_OPTION);

		if (confirmacao == JOptionPane.YES_OPTION) {
			UUID idProduto = (UUID) modeloTabela.getValueAt(linhaSelecionada, 0);

			try {
				cadastroProdutoController.excluirProduto(idProduto);
				JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
				carregarProdutos();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void limparCampos() {
		txtDescricao.setText("");
		txtPreco.setText("");
		btnAtualizar.setEnabled(false);
		btnSalvar.setEnabled(true);
	}

}
