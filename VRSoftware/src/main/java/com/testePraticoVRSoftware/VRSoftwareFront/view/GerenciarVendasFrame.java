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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.springframework.web.client.RestTemplate;

import com.testePraticoVRSoftware.VRSoftware.model.Venda;
import com.testePraticoVRSoftware.VRSoftwareFront.controller.RealizarVendaController;

public class GerenciarVendasFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tabelaVendas;
    private DefaultTableModel modeloTabela;

    private RealizarVendaController realizarVendaController;

    public GerenciarVendasFrame(RestTemplate restTemplate) {
        realizarVendaController = new RealizarVendaController(restTemplate);

        setTitle("Gerenciar Vendas");
        setSize(1080, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel tituloLabel = new JLabel("Gerenciar Vendas");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);

        tabelaVendas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        scrollPane.setPreferredSize(new java.awt.Dimension(950, 400));

        JButton btnAtualizar = new JButton("Atualizar Registro");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnHome = new JButton("Home");

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaVendas.getSelectedRow();
                if (selectedRow >= 0) {
                    JOptionPane.showMessageDialog(null, "Atualizando registro...");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para atualizar.");
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaVendas.getSelectedRow();
                if (selectedRow >= 0) {
                    JOptionPane.showMessageDialog(null, "Excluindo registro...");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para excluir.");
                }
            }
        });

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame().setVisible(true);
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
        constraints.insets = new Insets(10, 10, 10, 10);
        add(btnAtualizar, constraints);

        constraints.gridx = 1;
        add(btnExcluir, constraints);

        constraints.gridx = 2;
        add(btnHome, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(scrollPane, constraints);

        populateTable();
    }

    private void populateTable() {
        modeloTabela = new DefaultTableModel(new Object[] { "ID", "Data da Venda", "Valor Total", "Cliente" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        tabelaVendas.setModel(modeloTabela);
        configurarTabela();
        carregarVendas();
    }

    private void configurarTabela() {
        tabelaVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVendas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void carregarVendas() {
        Venda[] vendas = realizarVendaController.listarVendas();

        modeloTabela.setRowCount(0);

        for (Venda v : vendas) {
            modeloTabela.addRow(new Object[] { v.getId(), v.getDataVenda(), v.getValorTotal(), v.getCliente().getId() });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GerenciarVendasFrame(null).setVisible(true);
        });
    }
}
