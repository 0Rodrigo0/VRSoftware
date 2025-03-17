package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.VendaProdutoHistorico;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.VendaProdutoHistoricoController;

public class VendaProdutoHistoricoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private VendaProdutoHistoricoController vendaProdutoHistoricoController;
	private JTable tabela;
	private DefaultTableModel tableModel;

	public VendaProdutoHistoricoFrame(RestTemplate restTemplate) {
		vendaProdutoHistoricoController = new VendaProdutoHistoricoController(this, restTemplate);

		setTitle("Venda Produto Histórico");
		setSize(1080, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());

		JButton btnHome = new JButton("Home");

		panelButtons.add(btnHome);

		add(panelButtons, BorderLayout.NORTH);

		tabela = new JTable();
		tableModel = new DefaultTableModel(
				new Object[] { "ID", "Cliente Id", "Nome Cliente", "Data Venda", "Descrição Produto", "Id Produto", "Preço Unitário",
						"Valor total", "Id Venda"}, 0){
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
		VendaProdutoHistorico[] historicos = vendaProdutoHistoricoController.buscarTodos();
		tableModel.setRowCount(0);

		for (VendaProdutoHistorico historico : historicos) {
			tableModel.addRow(
					new Object[] { historico.getId(), historico.getClienteId(), historico.getClienteNome(),
							historico.getDataVenda(), historico.getProdutoDescricao(), historico.getProdutoId(),
							historico.getProdutoPreco(), historico.getValorTotal(), historico.getVendaId()});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new VendaProdutoHistoricoFrame(null).setVisible(true));
	}

}
