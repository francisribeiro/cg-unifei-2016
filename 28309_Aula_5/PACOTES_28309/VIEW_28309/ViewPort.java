package VIEW_28309;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import CONTROL_28309.ViewPortControl;

@SuppressWarnings("serial")
public class ViewPort extends JFrame {
	private JPanel canvas2;
	private JLabel coordenadas2;
	private Graphics draw2;
	public JCheckBox chkTransladar, chkEscalar, chkRotacionar;

	public ViewPort(ViewPortControl viewPortControl) {

		// Setando as preferÃªncias.
		this.setTitle("View Port");
		this.setPreferredSize(new Dimension(800, 500));
		this.setLayout(new BorderLayout());

		// O painel canvas.
		canvas2 = new JPanel();
		canvas2.setLayout(new BorderLayout());
		canvas2.setSize(this.getWidth(), this.getHeight());
		canvas2.setBackground(Color.BLACK);
		canvas2.addMouseListener(viewPortControl);
		canvas2.addMouseMotionListener(viewPortControl);

		// Label que exibe as coordenadas do mouse.
		coordenadas2 = new JLabel("");
		coordenadas2.setHorizontalAlignment(SwingConstants.SOUTH_EAST);
		coordenadas2.setForeground(Color.WHITE);
		coordenadas2.setBorder(new EmptyBorder(10, 10, 10, 10));
		canvas2.add(coordenadas2, BorderLayout.SOUTH);

		// Add o painel canvas ao frame.
		this.add(canvas2);

		// Add a barra de ferramentas ao frame.
		toolBar(this, viewPortControl);

		// Empacota e mostra a aplicaÃ§Ã£o.
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	// Barra de ferramentas
	private void toolBar(JFrame frame, ViewPortControl viewPortControl) {
		JToolBar toolBar;
		JButton btnLimparTela, btnMapear, btnTransladar, btnEscalar, btnRotacionar, btnSucessivas, bntComposta;

		// Cria a barra de feramentas.
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		// Cria os botÃµes.
		btnLimparTela = new JButton("Limpar Tela");
		btnMapear = new JButton("Mapear");
		btnTransladar = new JButton("Transladar");
		btnEscalar = new JButton("Escalar");
		btnRotacionar = new JButton("Rotacionar");
		btnSucessivas = new JButton("Transformações sucessivas");
		bntComposta = new JButton("Tranformações Compostas");

		// Cria os listeners.
		btnLimparTela.addActionListener(viewPortControl);
		btnMapear.addActionListener(viewPortControl);
		btnTransladar.addActionListener(viewPortControl);
		btnEscalar.addActionListener(viewPortControl);
		btnRotacionar.addActionListener(viewPortControl);
		btnSucessivas.addActionListener(viewPortControl);
		bntComposta.addActionListener(viewPortControl);

		toolBar.add(btnMapear);
		toolBar.addSeparator();

		toolBar.add(btnTransladar);
		toolBar.addSeparator();

		toolBar.add(btnEscalar);
		toolBar.addSeparator();

		toolBar.add(btnRotacionar);
		toolBar.addSeparator();

		toolBar.add(btnSucessivas);
		toolBar.addSeparator();

		toolBar.add(bntComposta);
		toolBar.addSeparator();

		toolBar.add(btnLimparTela);
		toolBar.addSeparator();

		// Add toolBar ao frame.
		frame.add(toolBar, BorderLayout.NORTH);
	}

	public Graphics startDrawing() {

		// Add os elementos grÃ¡ficos ao painel.
		draw2 = canvas2.getGraphics();

		return (draw2);
	}

	// MÃ©todo para limpar a tela, repintando o canvas com a cor de fundo.
	public void limparTela() {
		this.draw2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
		this.draw2.setColor(Color.BLACK);
		this.draw2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
	}

	// Seta o texto das coordenadas.
	public void setLabel(String coordenadas) {
		this.coordenadas2.setText(coordenadas);
	}

	public int getLargura() {
		return canvas2.getWidth();
	}

	public int getAltura() {
		return canvas2.getHeight();
	}

	public int composicao(ViewPortControl viewPortControl) {
		String msg;
		int n;

		chkTransladar = new JCheckBox("Ativar Transalação");
		chkEscalar = new JCheckBox("Ativar Escala");
		chkRotacionar = new JCheckBox("Ativar Rotação");

		msg = "Selecione as opções abaixo para obter a transformação composta";

		Object[] msgContent = { msg, chkTransladar, chkEscalar, chkRotacionar };

		n = JOptionPane.showConfirmDialog(null, msgContent, "Escolha da Composição", JOptionPane.YES_NO_OPTION);

		return n;
	}

}
