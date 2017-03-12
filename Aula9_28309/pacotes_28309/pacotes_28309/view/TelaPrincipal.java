package pacotes_28309.view;

import pacotes_28309.control.AppControl;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	private JPanel canvas;
	private JLabel coordenadas;
	private Graphics draw;
	public JButton btnLimparTela, btnDesenharReta, btnDesenharPoligono, btnDesenharAreaCorte, btnCortar,
			bntLimparAreaCorte, btnCortar2;

	public TelaPrincipal(AppControl AppControl) {

		// Setando as preferências.
		this.setTitle("Cortes");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		// O painel canvas.
		canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		canvas.setSize(this.getWidth(), this.getHeight());
		canvas.setBackground(Color.BLACK);
		canvas.addMouseListener(AppControl);
		canvas.addMouseMotionListener(AppControl);

		// Label que exibe as coordenadas do mouse.
		coordenadas = new JLabel("");
		coordenadas.setHorizontalAlignment(SwingConstants.SOUTH_EAST);
		coordenadas.setForeground(Color.WHITE);
		coordenadas.setBorder(new EmptyBorder(10, 10, 10, 10));
		canvas.add(coordenadas, BorderLayout.SOUTH);

		// Add o painel canvas ao frame.
		this.add(canvas);

		// Add a barra de ferramentas ao frame.
		toolBar(this, AppControl);

		// Empacota e mostra a aplicação.
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	// Barra de ferramentas
	private void toolBar(JFrame frame, AppControl AppControl) {
		JToolBar toolBar;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		// Cria os botões.
		btnLimparTela = new JButton("Limpar Tela");
		btnDesenharReta = new JButton("Desenhar Retas");
		btnDesenharPoligono = new JButton("Desenhar Poligono");
		btnDesenharAreaCorte = new JButton("Desenhar Área de Corte");
		btnCortar = new JButton("Cortar Retas");
		btnCortar2 = new JButton("Cortar Poligonos");
		bntLimparAreaCorte = new JButton("Limpar Retangulo Corte");

		// Cria os listeners.
		btnLimparTela.addActionListener(AppControl);
		btnDesenharReta.addActionListener(AppControl);
		btnDesenharPoligono.addActionListener(AppControl);
		btnDesenharAreaCorte.addActionListener(AppControl);
		btnCortar.addActionListener(AppControl);
		btnCortar2.addActionListener(AppControl);
		bntLimparAreaCorte.addActionListener(AppControl);

		toolBar.add(btnDesenharReta);
		toolBar.addSeparator();

		toolBar.add(btnDesenharPoligono);
		toolBar.addSeparator();

		toolBar.add(btnDesenharAreaCorte);
		toolBar.addSeparator();

		toolBar.add(btnCortar);
		toolBar.addSeparator();

		toolBar.add(btnCortar2);
		toolBar.addSeparator();

		toolBar.add(bntLimparAreaCorte);
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
	public void setCoordenadas(Point p) {
		this.coordenadas.setText("X = " + p.getX() + " | Y = " + p.getY());
	}

}