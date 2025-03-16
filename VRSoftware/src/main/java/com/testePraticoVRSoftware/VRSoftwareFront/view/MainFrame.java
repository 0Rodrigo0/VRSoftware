package com.testePraticoVRSoftware.VRSoftwareFront.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainFrame() {
		
		setTitle("VR Software - Sistema de Vendas");
		setSize(1080, 800); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		
		JLabel tituloLabel = new JLabel("Bem-vindo ao Sistema de Vendas");
		tituloLabel.setFont(new Font("Arial", Font.BOLD, 16)); 
		tituloLabel.setHorizontalAlignment(JLabel.CENTER); 

		
		JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
		JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
		JButton btnRealizarVenda = new JButton("Realizar Venda");

		
		btnCadastrarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CadastroProdutoFrame(null).setVisible(true);
				dispose();
			}
		});

		btnCadastrarCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CadastroClienteFrame(null).setVisible(true);
				dispose();
			}
		});

		btnRealizarVenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RealizarVendaFrame().setVisible(true);
				dispose();
			}
		});

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(tituloLabel, constraints);

		constraints.gridwidth = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 60, 10, 10);
		add(btnCadastrarProduto, constraints);

		constraints.gridy = 2;
		add(btnCadastrarCliente, constraints);

		constraints.gridy = 3;
		add(btnRealizarVenda, constraints);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new MainFrame().setVisible(true);
		});
	}
}
