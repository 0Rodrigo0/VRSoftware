package com.testePraticoVRSoftware.VRSoftwareFront.utils;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VRSoftwareUtils {

	public static void configurarJanela(JFrame frame, String titulo, int largura, int altura) {
		frame.setTitle(titulo);
		frame.setSize(largura, altura);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	public static GroupLayout configurarPainelComGroupLayout(JPanel panel) {
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		return layout;
	}

}
