package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;

public class CadastroClienteFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome, txtLimiteCompra, txtDiaFechamento;
	private JButton btnSalvar;
	private JButton btnAtualizarLista;
	private JTable tabelaClientes;
	private DefaultTableModel modeloTabela;

	public CadastroClienteFrame(RestTemplate restTemplate) {
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
		configurarBotaoAtualizar(btnAtualizarLista, layout);

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
					if (dia > 31) {
						e.consume(); 
					}
				} catch (NumberFormatException ex) {
					e.consume();
				}
			}
		});

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

		add(panel, BorderLayout.NORTH);

		// Criando a tabela para exibir os clientes
		String[] colunas = { "ID", "Nome", "Limite de Compra", "Dia Fechamento" };
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaClientes = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaClientes);

		add(scrollPane, BorderLayout.CENTER);

		btnSalvar.addActionListener(e -> salvarCliente());

		carregarClientes();

	}

	private void salvarCliente() {
		String nome = txtNome.getText();
		String limite = txtLimiteCompra.getText();
		String dia = txtDiaFechamento.getText();

		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setLimiteCompra(new BigDecimal(limite));
		cliente.setDiaFechamentoFatura(Integer.parseInt(dia));

		RestTemplate restTemplate = new RestTemplate();
		CadastroClienteFrame frame = new CadastroClienteFrame(restTemplate);
		frame.setVisible(true);

		try {
			String url = "http://localhost:8080/api/cliente";

			ResponseEntity<String> response = restTemplate.postForEntity(url, cliente, String.class);

			if (response.getStatusCode() == HttpStatus.CREATED) {
				JOptionPane.showMessageDialog(this, "Cliente " + nome + " cadastrado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + response.getBody());
			}

			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CadastroClienteFrame(null).setVisible(true));
	}

	private void carregarClientes() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Cliente[]> resposta = restTemplate.getForEntity("http://localhost:8080/api/cliente",
					Cliente[].class);

			if (resposta.getStatusCode() == HttpStatus.OK) {
				Cliente[] clientes = resposta.getBody();
				modeloTabela.setRowCount(0); // Limpa a tabela antes de inserir novos dados

				for (Cliente c : clientes) {
					modeloTabela.addRow(
							new Object[] { c.getId(), c.getNome(), c.getLimiteCompra(), c.getDiaFechamentoFatura() });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro ao carregar clientes!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void configurarBotaoAtualizar(JButton btnAtualizar, GroupLayout layout) {
		btnAtualizar.addActionListener(e -> carregarClientes());
	}
}