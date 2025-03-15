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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.CadastroClienteController;

public class CadastroClienteFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome, txtLimiteCompra, txtDiaFechamento;
	private JTextArea areaInformacoes;
	private JButton btnSalvar, btnAtualizarLista, btnAtualizar, btnExcluir, btnLimparCampos;
	private JTable tabelaClientes;
	private DefaultTableModel modeloTabela;
	private CadastroClienteController cadastroClienteController;
	private UUID idClienteSelecionado;

	public CadastroClienteFrame(RestTemplate restTemplate) {
		cadastroClienteController = new CadastroClienteController(this, restTemplate);

		setTitle("VR Software - Cadastro de Clientes");
		setSize(1080, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		JLabel lblNome = new JLabel("Nome:");
		txtNome = new JTextField();
		JLabel lblLimiteCompra = new JLabel("Limite de Compra:");
		txtLimiteCompra = new JTextField();
		JLabel lblDiaFechamento = new JLabel("Dia de Fechamento:");
		txtDiaFechamento = new JTextField();

		btnAtualizarLista = new JButton("Atualizar lista");
		btnSalvar = new JButton("Salvar");
		btnAtualizar = new JButton("Atualizar Registro");
		btnExcluir = new JButton("Excluir Registro");
		btnLimparCampos = new JButton("Limpar Campos");

		btnAtualizar.setEnabled(false);

		areaInformacoes = new JTextArea();
		configuracaoInstrucoes(areaInformacoes);

		JScrollPane scrollInfo = new JScrollPane(areaInformacoes);
		scrollInfo.setPreferredSize(new Dimension(320, 130));

		configurarCampos(layout, lblNome, lblLimiteCompra, lblDiaFechamento, scrollInfo);

		add(panel, BorderLayout.NORTH);

		configurarCaixaTexto(txtNome, txtLimiteCompra, txtDiaFechamento);

		// Criando a tabela para exibir os clientes
		String[] colunas = { "ID", "Nome", "Limite de Compra", "Dia Fechamento" };
		modeloTabela = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};

		tabelaClientes = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaClientes);
		configurarTabela();

		add(scrollPane, BorderLayout.CENTER);

		btnAtualizarLista.addActionListener(e -> carregarClientes());
		btnSalvar.addActionListener(e -> salvarCliente());
		btnAtualizar.addActionListener(e -> atualizarCliente());
		btnExcluir.addActionListener(e -> excluirClientes());
		btnLimparCampos.addActionListener(e -> limparCampos());

		carregarClientes();

	}

	private void configurarCaixaTexto(JTextField txtNome2, JTextField txtLimiteCompra2, JTextField txtDiaFechamento2) {
		txtNome.setPreferredSize(new Dimension(300, 30));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isLetter(c) && c != ' ') {
					e.consume();
				}
			}
		});

		txtLimiteCompra.setPreferredSize(new Dimension(300, 30));
		txtLimiteCompra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '.' && c != ',') {
					e.consume();
				}
			}
		});

		txtDiaFechamento.setPreferredSize(new Dimension(300, 30));
		txtDiaFechamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '.' && c != ',') {
					e.consume();
				}
				if (txtDiaFechamento.getText().length() >= 2) {
					e.consume();
				}
				String text = txtDiaFechamento.getText() + c;
				try {
					int dia = Integer.parseInt(text);
					if (dia < 1 || dia > 31) {
						e.consume();
					}
				} catch (NumberFormatException ex) {
					e.consume();
				}
			}
		});
	}

	private void configuracaoInstrucoes(JTextArea areaInformacoes) {

		areaInformacoes.setText("Preencha os campos corretamente.\n" + "- Nome: Apenas letras.\n"
				+ "- Limite de Compra: Apenas números.\n" + "- Dia de Fechamento: Número entre 1 e 31.\n"
				+ "Clique em 'Salvar' para cadastrar.\n"
				+ "Clique 2x em um registro para alterar e em 'Atualizar Registro' para salvar a alteração.\n"
				+ "Para excluir selecione um registro e clique em 'Excluir'.");
		areaInformacoes.setEditable(false);
		areaInformacoes.setLineWrap(true);
		areaInformacoes.setWrapStyleWord(true);
		areaInformacoes.setPreferredSize(new Dimension(300, 90));

	}

	private void configurarCampos(GroupLayout layout, JLabel lblNome, JLabel lblLimiteCompra, JLabel lblDiaFechamento,
			JScrollPane scrollInfo) {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblNome)
						.addComponent(lblLimiteCompra).addComponent(lblDiaFechamento))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLimiteCompra, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDiaFechamento, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(btnAtualizarLista).addGap(10)
										.addComponent(btnSalvar).addGap(10).addComponent(btnAtualizar).addGap(10)
										.addComponent(btnExcluir).addGap(10).addComponent(btnLimparCampos))))
				.addComponent(scrollInfo, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblNome)
						.addComponent(txtNome).addComponent(scrollInfo))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblLimiteCompra)
						.addComponent(txtLimiteCompra))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblDiaFechamento)
						.addComponent(txtDiaFechamento))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnAtualizarLista)
						.addComponent(btnSalvar).addComponent(btnAtualizar).addComponent(btnExcluir)
						.addComponent(btnLimparCampos)));

	}

	public void carregarClientes() {
		Cliente[] clientes = cadastroClienteController.carregarClientes();
		modeloTabela.setRowCount(0);
		for (Cliente c : clientes) {
			modeloTabela
					.addRow(new Object[] { c.getId(), c.getNome(), c.getLimiteCompra(), c.getDiaFechamentoFatura() });
		}
	}

	private void salvarCliente() {
		String nome = txtNome.getText();
		String limite = txtLimiteCompra.getText();
		String diaFechamento = txtDiaFechamento.getText();

		if (txtNome.getText().isEmpty() || txtLimiteCompra.getText().isEmpty()
				|| txtDiaFechamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Preencha os campos!.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		cadastroClienteController.salvarCliente(nome, limite, diaFechamento);
		limparCampos();
		carregarClientes();

	}

	private void atualizarCliente() {
		btnAtualizar.addActionListener(e -> {

			if (txtNome.getText().isEmpty() || txtLimiteCompra.getText().isEmpty()
					|| txtDiaFechamento.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha os campos!.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (idClienteSelecionado == null) {
				JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			String novoNome = txtNome.getText();
			String novoLimite = txtLimiteCompra.getText();
			String novoDiaFechamento = txtDiaFechamento.getText();

			try {
				cadastroClienteController.atualizarCliente(idClienteSelecionado, novoNome, novoLimite,
						novoDiaFechamento);
				JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception er) {
				JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + er.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

			limparCampos();
			carregarClientes();
			btnAtualizar.setEnabled(false);
			btnSalvar.setEnabled(true);
		});

	}

	private void limparCampos() {
		txtNome.setText("");
		txtLimiteCompra.setText("");
		txtDiaFechamento.setText("");
		btnAtualizar.setEnabled(false);
		btnSalvar.setEnabled(true);
	}

	private void excluirClientes() {
		int linhaSelecionada = tabelaClientes.getSelectedRow();
		if (linhaSelecionada == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este cliente?",
				"Confirmação", JOptionPane.YES_NO_OPTION);

		if (confirmacao == JOptionPane.YES_OPTION) {
			UUID idCliente = (UUID) modeloTabela.getValueAt(linhaSelecionada, 0);

			try {
				cadastroClienteController.excluirCliente(idCliente);
				JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
				carregarClientes(); // Atualiza a lista após a exclusão
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void configurarTabela() {
		tabelaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int linhaSelecionada = tabelaClientes.getSelectedRow();
					if (linhaSelecionada != -1) {
						UUID idCliente = (UUID) modeloTabela.getValueAt(linhaSelecionada, 0);
						String nomeCliente = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
						BigDecimal limiteCliente = (BigDecimal) modeloTabela.getValueAt(linhaSelecionada, 2);
						Integer diaFechamento = (Integer) modeloTabela.getValueAt(linhaSelecionada, 3);

						txtNome.setText(nomeCliente);
						txtLimiteCompra.setText(limiteCliente.toString());
						txtDiaFechamento.setText(diaFechamento.toString());

						idClienteSelecionado = idCliente;

						btnSalvar.setEnabled(false);
						btnAtualizar.setEnabled(true);
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CadastroClienteFrame(null).setVisible(true));
	}
}