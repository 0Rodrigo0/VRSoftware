package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.testePraticoVRSoftware.VRSoftware.model.Cliente;
import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.RealizarVendaController;

public class EditarVendaDialogFrame extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtDataVenda, txtValorTotal, txtClienteId;
	private UUID idVenda;
	private RealizarVendaController vendaController;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public EditarVendaDialogFrame(UUID id, LocalDate dataVenda, BigDecimal valorTotal, UUID clienteId,
			RealizarVendaController controller) {
		this.idVenda = id;
		this.vendaController = controller;

		setTitle("Editar Venda");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setModal(true);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Data da Venda:"), gbc);

		gbc.gridx = 1;
		txtDataVenda = new JTextField(dataVenda.format(dateFormatter), 15);
		add(txtDataVenda, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Valor Total:"), gbc);

		gbc.gridx = 1;
		txtValorTotal = new JTextField(valorTotal.toString(), 15);
		add(txtValorTotal, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Cliente ID:"), gbc);

		gbc.gridx = 1;
		txtClienteId = new JTextField(clienteId.toString(), 15);
		add(txtClienteId, gbc);

		JPanel panelBotoes = new JPanel();
		JButton btnSalvar = new JButton("Salvar");
		JButton btnCancelar = new JButton("Cancelar");

		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarAlteracoes();
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panelBotoes.add(btnSalvar);
		panelBotoes.add(btnCancelar);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		add(panelBotoes, gbc);
	}

	private void salvarAlteracoes() {
		try {
			LocalDate novaDataVenda = LocalDate.parse(txtDataVenda.getText(), dateFormatter);
			BigDecimal novoValorTotal = new BigDecimal(txtValorTotal.getText());
			UUID novoClienteId = UUID.fromString(txtClienteId.getText());
			
			Cliente novoCliente = vendaController.buscaCliente(novoClienteId);		

			Venda vendaAtualizada = new Venda();
			vendaAtualizada.setId(idVenda);
			vendaAtualizada.setDataVenda(novaDataVenda);
			vendaAtualizada.setValorTotal(novoValorTotal);
			vendaAtualizada.setCliente(novoCliente);

			vendaController.atualizarVenda(vendaAtualizada);

			JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!");
			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}