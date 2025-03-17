package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.VendaProdutoHistoricoController;

public class VendaProdutoHistoricoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private VendaProdutoHistoricoController vendaProdutoHistoricoController;
	private JTable tabela;
	private DefaultTableModel tableModel;
	private JTextField txtFiltro;
	private List<VendaProdutoHistorico> historicos;

	public VendaProdutoHistoricoFrame(RestTemplate restTemplate) {
		vendaProdutoHistoricoController = new VendaProdutoHistoricoController(this, restTemplate);

		setTitle("Venda Produto Histórico");
		setSize(1080, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		vendaProdutoHistoricoController = new VendaProdutoHistoricoController(this, restTemplate);

		setTitle("Venda Produto Histórico");
		setSize(1080, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		JLabel lblExplicativo = new JLabel(
				"<html><b>Histórico de Vendas:</b> Aqui você pode visualizar todas as vendas realizadas.<br>"
						+ "<b>Filtro:</b> Digite para filtrar os registros de vendas por nome do cliente, ID ou descrição do produto.</html>");
		lblExplicativo.setHorizontalAlignment(SwingConstants.CENTER);
		panelTop.add(lblExplicativo, BorderLayout.NORTH);

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT));

		txtFiltro = new JTextField(20);
		txtFiltro.setToolTipText("Digite para filtrar...");

		txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarTabela(txtFiltro.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarTabela(txtFiltro.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarTabela(txtFiltro.getText());
			}
		});

		JButton btnHome = new JButton("Home");

		panelButtons.add(txtFiltro);
		panelButtons.add(btnHome);

		panelTop.add(panelButtons, BorderLayout.SOUTH);

		add(panelTop, BorderLayout.NORTH);

		tabela = new JTable();
		tableModel = new DefaultTableModel(new Object[] { "ID", "Cliente Id", "Nome Cliente", "Data Venda",
				"Descrição Produto", "Id Produto", "Preço Unitário", "Valor total", "Id Venda" }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};

		tabela.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(tabela);
		add(scrollPane, BorderLayout.CENTER);

		btnHome.addActionListener(e -> {
			dispose();
			new MainFrame().setVisible(true);
		});

		carregarDados();
	}

	private void carregarDados() {
		historicos = new ArrayList<>();
		VendaProdutoHistorico[] historicoArray = vendaProdutoHistoricoController.buscarTodos();
		for (VendaProdutoHistorico historico : historicoArray) {
			historicos.add(historico);
			tableModel.addRow(new Object[] { historico.getId(), historico.getClienteId(), historico.getClienteNome(),
					historico.getDataVenda(), historico.getProdutoDescricao(), historico.getProdutoId(),
					historico.getProdutoPreco(), historico.getValorTotal(), historico.getVendaId() });
		}
	}

	private void filtrarTabela(String filtro) {
		tableModel.setRowCount(0);
		for (VendaProdutoHistorico historico : historicos) {
			if (historico.getClienteNome().toLowerCase().contains(filtro.toLowerCase())
					|| String.valueOf(historico.getId()).contains(filtro)
					|| String.valueOf(historico.getProdutoDescricao()).toLowerCase().contains(filtro.toLowerCase())) {
				tableModel
						.addRow(new Object[] { historico.getId(), historico.getClienteId(), historico.getClienteNome(),
								historico.getDataVenda(), historico.getProdutoDescricao(), historico.getProdutoId(),
								historico.getProdutoPreco(), historico.getValorTotal(), historico.getVendaId() });
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VendaProdutoHistoricoFrame(null).setVisible(true));
	}

}
