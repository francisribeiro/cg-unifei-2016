package VIEW_28309;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import CONTROL_28309.*;

@SuppressWarnings("serial")
public class View extends JFrame {

	private JPanel canvas;
	private JLabel coordenadas;
	private Graphics draw;

	public View(AplicacaoControl aplicacaoControl) {

		// Setando as preferências.
		this.setTitle("Desenhar Ret�ngulos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());

		// O painel canvas.
		canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		canvas.setSize(this.getWidth(), this.getHeight());
		canvas.setBackground(Color.BLACK);
		canvas.addMouseListener(aplicacaoControl);
		canvas.addMouseMotionListener(aplicacaoControl);

		// Label que exibe as coordenadas do mouse.
		coordenadas = new JLabel("");
		coordenadas.setHorizontalAlignment(SwingConstants.SOUTH_EAST);
		coordenadas.setForeground(Color.WHITE);
		coordenadas.setBorder(new EmptyBorder(10, 10, 10, 10));
		canvas.add(coordenadas, BorderLayout.SOUTH);

		// Add o painel canvas ao frame.
		this.add(canvas);

		// Add a barra de ferramentas ao frame.
		toolBar(this, aplicacaoControl);

		// Empacota e mostra a aplicação.
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	// Barra de ferramentas
	private void toolBar(JFrame frame, AplicacaoControl aplicacaoControl) {
		JToolBar toolBar;
		JButton btnLimparTela, btnViewPort;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		// Cria os botões.
		btnLimparTela = new JButton("Limpar Tela");
		btnViewPort = new JButton("View Port");

		// Cria os listeners.
		btnLimparTela.addActionListener(aplicacaoControl);
		btnViewPort.addActionListener(aplicacaoControl);
		

		toolBar.add(btnViewPort);	
		toolBar.addSeparator();
		
		toolBar.add(btnLimparTela);
		toolBar.addSeparator();

		// Add toolBar ao frame.
		frame.add(toolBar, BorderLayout.NORTH);
	}

	public Graphics startDrawing() {

		// Add os elementos gráficos ao painel.
		draw = canvas.getGraphics();

		return (draw);
	}

	// Método para limpar a tela, repintando o canvas com a cor de fundo.
	public void limparTela() {
		this.draw.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.draw.setColor(Color.BLACK);
		this.draw.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	// Seta o texto das coordenadas.
	public void setLabel(String coordenadas) {
		this.coordenadas.setText(coordenadas);
	}
	
}
