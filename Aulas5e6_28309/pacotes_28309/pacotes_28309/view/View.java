package pacotes_28309.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import pacotes_28309.control.*;

@SuppressWarnings("serial")
public class View extends JFrame {

	private JPanel canvas;
	private JLabel coordenadas;
	private Graphics draw;

	public View(AplicacaoControl aplicacaoControl) {

		// Setando as preferÃªncias.
		this.setTitle("Círculos, Elipses e Retângulos ");
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

		// Empacota e mostra a aplicaÃ§Ã£o.
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	// Barra de ferramentas
	private void toolBar(JFrame frame, AplicacaoControl aplicacaoControl) {
		JToolBar toolBar;
		JButton btnLimparTela, btnCClassico, btnCPolarSimples, btnCPolarIncremental, btnCDDA, btnElipse;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		// Cria os botÃµes.
		btnLimparTela = new JButton("Limpar Tela");
		btnCClassico = new JButton("Círculo Clássico");
		btnCPolarSimples = new JButton("Círculo Polar Simples");
		btnCPolarIncremental = new JButton("Círculo Polar Incremental");
		btnCDDA = new JButton("Círculo DDA");
		btnElipse = new JButton("Elipses");

		// Cria os listeners.
		btnLimparTela.addActionListener(aplicacaoControl);
		btnCClassico.addActionListener(aplicacaoControl);
		btnCPolarSimples.addActionListener(aplicacaoControl);
		btnCPolarIncremental.addActionListener(aplicacaoControl);
		btnCDDA.addActionListener(aplicacaoControl);
		btnElipse.addActionListener(aplicacaoControl);

		// Add botÃµes Ã  barra.
		toolBar.add(btnCClassico);
		toolBar.addSeparator();

		toolBar.add(btnCPolarSimples);
		toolBar.addSeparator();

		toolBar.add(btnCPolarIncremental);
		toolBar.addSeparator();

		toolBar.add(btnCDDA);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();

		toolBar.add(btnElipse);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();

		toolBar.add(btnLimparTela);
		toolBar.addSeparator();

		// Add toolBar ao frame.
		frame.add(toolBar, BorderLayout.NORTH);
	}

	public Graphics startDrawing() {

		// Add os elementos grÃ¡ficos ao painel.
		draw = canvas.getGraphics();

		return (draw);
	}

	// MÃ©todo para limpar a tela, repintando o canvas com a cor de fundo.
	public void limparTela() {
		this.draw.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.draw.setColor(Color.BLACK);
		this.draw.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	// Seta o texto das coordenadas.
	public void setCoordenadas(Point p) {
		this.coordenadas.setText("X = " + p.getX() + " | Y = " + p.getY());
	}
}
