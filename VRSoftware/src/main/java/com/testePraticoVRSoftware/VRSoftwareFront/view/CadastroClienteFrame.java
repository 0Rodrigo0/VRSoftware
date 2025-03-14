package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.CadastroClienteController;

public class CadastroClienteFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome, txtLimiteCompra, txtDiaFechamento;
	private JButton btnSalvar, btnAtualizarLista;
	private JTable tabelaClientes;
	private DefaultTableModel modeloTabela;
	private CadastroClienteController cadastroClienteController;

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

		btnSalvar = new JButton("Salvar");
		btnAtualizarLista = new JButton("Atualizar lista");

		configurarCampos(layout, lblNome, lblLimiteCompra, lblDiaFechamento);

		add(panel, BorderLayout.NORTH);

		configurarCaixaTexto(txtNome, txtLimiteCompra, txtDiaFechamento);

		// Criando a tabela para exibir os clientes
		String[] colunas = { "ID", "Nome", "Limite de Compra", "Dia Fechamento" };
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaClientes = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaClientes);

		add(scrollPane, BorderLayout.CENTER);

		btnSalvar.addActionListener(e -> salvarCliente());
		btnAtualizarLista.addActionListener(e -> carregarClientes());

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

	private void configurarCampos(GroupLayout layout, JLabel lblNome, JLabel lblLimiteCompra, JLabel lblDiaFechamento) {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblNome)
						.addComponent(lblLimiteCompra).addComponent(lblDiaFechamento))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLimiteCompra, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDiaFechamento, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addComponent(btnSalvar).addGap(10)
								.addComponent(btnAtualizarLista))));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblNome)
						.addComponent(txtNome))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblLimiteCompra)
						.addComponent(txtLimiteCompra))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblDiaFechamento)
						.addComponent(txtDiaFechamento))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnSalvar)
						.addComponent(btnAtualizarLista)));

	}

	private void salvarCliente() {
		String nome = txtNome.getText();
		String limite = txtLimiteCompra.getText();
		String dia = txtDiaFechamento.getText();

		cadastroClienteController.salvarCliente(nome, limite, dia);
	}

	public void carregarClientes() {
		Cliente[] clientes = cadastroClienteController.carregarClientes();
		modeloTabela.setRowCount(0);
		for (Cliente c : clientes) {
			modeloTabela
					.addRow(new Object[] { c.getId(), c.getNome(), c.getLimiteCompra(), c.getDiaFechamentoFatura() });
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CadastroClienteFrame(null).setVisible(true));
	}
}