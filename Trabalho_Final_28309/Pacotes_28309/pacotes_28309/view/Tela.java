package pacotes_28309.view;

import java.awt.*;
import javax.swing.*;
import pacotes_28309.control.*;

@SuppressWarnings("serial")
public class Tela extends JFrame {
	private JButton btnBezier, btnHermite;
	private JButton btnAnimacaoBezier, btnAnimacaoHermite, btnPararAnimacao;
	private JButton btnPreencher, btnLimpar;
	private static JButton zoomIn;
	private static JButton zoomOut;
	private static JButton direita;
	private static JButton esquerda;
	private static JButton cima;
	private static JButton baixo;
	private JButton girarE;
	private JPanel barraDeFerramentas;

	public Tela() {
		// Descrição do Frame
		super("Trabalho Final");

		// Nosso canvas
		AppControl canvas = new AppControl();

		// Add o canvas ao frame.
		add(canvas, BorderLayout.CENTER);

		// Seta o que acontecerá quando o frame for fechado
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Seta o tamanho do frame
		setSize(1200, 700);

		barraDeFerramentas = new JPanel();
		barraDeFerramentas.setLayout(new BorderLayout());

		// Add a barra de ferramentas ao Painel.
		toolBar(barraDeFerramentas, canvas);
		add(barraDeFerramentas, BorderLayout.NORTH);

		// Coloca o frame no meio da tela
		setLocationRelativeTo(null);

		// Torna visível o frame
		setVisible(true);

		// Não£o deixa que o frame seja redimensionado
		setResizable(false);

		/*
		 * Agora que a tela está visivel nós podemos dizer a ela que usaremos 2
		 * buffers para repintar, antes de habilitar isso é necessário que o
		 * canvas esteja visivel. *
		 */
		canvas.createBufferStrategy(2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Barra de ferramentas
	private void toolBar(JPanel Panel, AppControl AppControl) {
		JToolBar toolBar, toolBar2;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		toolBar2 = new JToolBar();
		toolBar2.setRollover(true);

		// Cria os botões.
		btnBezier = new JButton("Bezier");
		btnHermite = new JButton("Hermite");
		btnAnimacaoBezier = new JButton("Animação Bezier");
		btnAnimacaoHermite = new JButton("Animação Hermite");
		btnPararAnimacao = new JButton("Finalizar Animação");
		btnPreencher = new JButton("Preencher Figura");
		btnLimpar = new JButton("Limpar Preenchimento");
		zoomIn = new JButton("ZoomIn");
		zoomOut = new JButton("ZoomOut");
		direita = new JButton("Direita");
		esquerda = new JButton("Esquerda");
		cima = new JButton("Cima");
		baixo = new JButton("Baixo");
		girarE = new JButton("Girar Esquerda");

		// Cria os listeners.
		btnBezier.addActionListener(AppControl);
		btnHermite.addActionListener(AppControl);
		btnAnimacaoBezier.addActionListener(AppControl);
		btnAnimacaoHermite.addActionListener(AppControl);
		btnPararAnimacao.addActionListener(AppControl);
		btnPreencher.addActionListener(AppControl);
		btnLimpar.addActionListener(AppControl);
		zoomIn.addActionListener(AppControl);
		zoomOut.addActionListener(AppControl);
		direita.addActionListener(AppControl);
		esquerda.addActionListener(AppControl);
		cima.addActionListener(AppControl);
		baixo.addActionListener(AppControl);
		girarE.addActionListener(AppControl);

		// Setando as cores
		toolBar.setBackground(Color.BLACK);
		toolBar2.setBackground(Color.BLACK);
		btnBezier.setBackground(new Color(0, 191, 255));
		btnHermite.setBackground(new Color(0, 191, 255));
		btnPreencher.setBackground(new Color(218, 112, 214));
		btnLimpar.setBackground(new Color(218, 112, 214));
		btnAnimacaoBezier.setBackground(new Color(50, 205, 50));
		btnAnimacaoHermite.setBackground(new Color(50, 205, 50));
		btnPararAnimacao.setBackground(new Color(255, 0, 0));
		zoomIn.setBackground(new Color(255, 215, 0));
		zoomOut.setBackground(new Color(255, 215, 0));
		direita.setBackground(new Color(139, 0, 0));
		esquerda.setBackground(new Color(139, 0, 0));
		cima.setBackground(new Color(139, 0, 0));
		baixo.setBackground(new Color(139, 0, 0));
		girarE.setBackground(new Color(255, 105, 180));

		// setando as bordas
		toolBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		toolBar2.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		btnBezier.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnHermite.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnPreencher.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnLimpar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnAnimacaoBezier.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnAnimacaoHermite.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		btnPararAnimacao.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		zoomIn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		zoomOut.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		direita.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		esquerda.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		cima.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		baixo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		girarE.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		// setando as cores da fonte
		btnPararAnimacao.setForeground(Color.WHITE);
		direita.setForeground(Color.WHITE);
		esquerda.setForeground(Color.WHITE);
		baixo.setForeground(Color.WHITE);
		cima.setForeground(Color.WHITE);

		// Add os botões ao toolBar
		toolBar.add(btnBezier);
		toolBar.addSeparator();

		toolBar.add(btnHermite);
		toolBar.addSeparator();

		toolBar.add(btnPreencher);
		toolBar.addSeparator();

		toolBar.add(btnLimpar);
		toolBar.addSeparator();

		toolBar.add(btnAnimacaoBezier);
		toolBar.addSeparator();

		toolBar.add(btnAnimacaoHermite);
		toolBar.addSeparator();

		toolBar.add(btnPararAnimacao);
		toolBar.addSeparator();

		toolBar2.add(zoomIn);
		toolBar2.addSeparator();

		toolBar2.add(zoomOut);
		toolBar2.addSeparator();

		toolBar2.add(direita);
		toolBar2.addSeparator();

		toolBar2.add(esquerda);
		toolBar2.addSeparator();

		toolBar2.add(cima);
		toolBar2.addSeparator();

		toolBar2.add(baixo);
		toolBar2.addSeparator();

		// Add toolBar ao frame.
		Panel.add(toolBar, BorderLayout.PAGE_START);
		Panel.add(toolBar2, BorderLayout.PAGE_END);
	}

	public static void destivarOpcoesUsuario() {
		zoomIn.setEnabled(false);
		zoomOut.setEnabled(false);
		direita.setEnabled(false);
		esquerda.setEnabled(false);
		cima.setEnabled(false);
		baixo.setEnabled(false);
	}

	public static void ativarOpcoesUsuario() {
		zoomIn.setEnabled(true);
		zoomOut.setEnabled(true);
		direita.setEnabled(true);
		esquerda.setEnabled(true);
		cima.setEnabled(true);
		baixo.setEnabled(true);
	}
}
