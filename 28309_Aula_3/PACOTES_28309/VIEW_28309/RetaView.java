package VIEW_28309;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import CONTROL_28309.AplicacaoControl;

@SuppressWarnings("serial")
public class RetaView extends JFrame {

	private JPanel canvas;
	private JLabel coordenadas;
	Graphics draw;

	public RetaView(AplicacaoControl aplicacaoControl) {

		// Setando as preferências.
		this.setTitle("Retas");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());

		// O painel canvas.
		canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		canvas.setSize(this.getWidth(), this.getHeight());
		canvas.setBackground(Color.GRAY);
		canvas.addMouseListener(aplicacaoControl);
		canvas.addMouseMotionListener(aplicacaoControl);

		// Label que exibe as coordenadas do mouse.
		coordenadas = new JLabel("");
		coordenadas.setHorizontalAlignment(SwingConstants.SOUTH_EAST);
		coordenadas.setForeground(Color.BLACK);
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
		JButton btnClassico, btnDdaSimples, btnDdaInteiro;
		JButton btnH, btnV, btnD, btnT, btnLimparTela, btnCruzamentos, btnPerpendiculares;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		// Cria os botões.
		btnClassico = new JButton("Clássico");
		btnDdaSimples = new JButton("DDA Simples");
		btnDdaInteiro = new JButton("DDA Inteiro");
		btnH = new JButton("Horizontais");
		btnV = new JButton("Verticais");
		btnD = new JButton("Diagonais");
		btnT = new JButton("Todas");
		btnCruzamentos = new JButton("Apontar Cruzamentos");
		btnPerpendiculares = new JButton("Apontar Perpendiculares");
		btnLimparTela = new JButton("Limpar Tela");

		// Cria os listeners.
		btnClassico.addActionListener(aplicacaoControl);
		btnDdaSimples.addActionListener(aplicacaoControl);
		btnDdaInteiro.addActionListener(aplicacaoControl);
		btnH.addActionListener(aplicacaoControl);
		btnV.addActionListener(aplicacaoControl);
		btnD.addActionListener(aplicacaoControl);
		btnT.addActionListener(aplicacaoControl);
		btnCruzamentos.addActionListener(aplicacaoControl);
		btnPerpendiculares.addActionListener(aplicacaoControl);
		btnLimparTela.addActionListener(aplicacaoControl);

		// Add botões à barra.
		toolBar.add(btnClassico);
		toolBar.addSeparator();
		
		toolBar.add(btnDdaSimples);
		toolBar.addSeparator();
		
		toolBar.add(btnDdaInteiro);
		toolBar.addSeparator();		
		
		toolBar.addSeparator();
		toolBar.addSeparator();		
		toolBar.addSeparator();		
		
		toolBar.add(btnT);
		toolBar.addSeparator();

		toolBar.add(btnH);
		toolBar.addSeparator();

		toolBar.add(btnV);
		toolBar.addSeparator();

		toolBar.add(btnD);
		toolBar.addSeparator();

		toolBar.addSeparator();
		toolBar.addSeparator();		
		toolBar.addSeparator();		
		
		toolBar.add(btnCruzamentos);
		toolBar.addSeparator();

		toolBar.add(btnPerpendiculares);
		toolBar.addSeparator();

		toolBar.addSeparator();
		toolBar.addSeparator();		
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
		this.draw.setColor(Color.GRAY);
		this.draw.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	// Seta o texto das coordenadas.
	public void setLabel(String coordenadas) {
		this.coordenadas.setText(coordenadas);
	}
}
