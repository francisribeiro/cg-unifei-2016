package VIEW_28309;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import CONTROL_28309.AplicacaoControl;

@SuppressWarnings("serial")
public class RetaView extends JFrame {

	private JPanel header, canvas;
	Graphics draw;

	public RetaView(AplicacaoControl aplicacaoControl) {

		// Setando as preferências
		this.setTitle("Retas");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// O painel header
		header = new JPanel();
		header.setPreferredSize(new Dimension(0, 40));
		header.setBackground(Color.LIGHT_GRAY);

		// Add o painel header ao frame
		this.add(header);

		// O painel canvas
		canvas = new JPanel();

		// Add o painel canvas ao frame
		this.add(canvas);

		// Add a barra de menus
		menuBar(this, aplicacaoControl);

		// Empacota e mostra a aplicação
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	private void menuBar(JFrame frame, AplicacaoControl aplicacaoControl) {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem abrir, fechar, limpar;

		// Cria a barra de menus
		menuBar = new JMenuBar();

		// Cria o primeiro menu
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("Opções de arquivo");
		menuBar.add(menu);

		// Adiciona os itens do primeiro menu
		abrir = new JMenuItem("Abrir...");
		abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		abrir.getAccessibleContext().setAccessibleDescription("Abrir Arquivo");
		abrir.addActionListener(aplicacaoControl);
		menu.add(abrir);

		limpar = new JMenuItem("Limpar");
		limpar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		limpar.getAccessibleContext().setAccessibleDescription("Abrir Arquivo");
		limpar.addActionListener(aplicacaoControl);
		menu.add(limpar);

		fechar = new JMenuItem("Fechar");
		fechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
		fechar.getAccessibleContext().setAccessibleDescription("Fechar Aplicação");
		fechar.addActionListener(aplicacaoControl);
		menu.add(fechar);

		// Add a barra de menu ao framw
		frame.setJMenuBar(menuBar);
	}

	public Graphics startDrawing() {

		draw = canvas.getGraphics();

		return (draw);
	}

	public void limparTela() {
		this.repaint();
	}
}
