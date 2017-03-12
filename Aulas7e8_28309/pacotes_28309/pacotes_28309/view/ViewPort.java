package pacotes_28309.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import pacotes_28309.control.*;

@SuppressWarnings("serial")
public class ViewPort extends JFrame {
	private JPanel canvas2;
	private JLabel coordenadas2;
	private Graphics draw2;
	public JCheckBox chkTransladar, chkEscalar, chkRotacionar;

	public ViewPort(ViewPortControl viewPortControl) {

		// Setando as preferências.
		this.setTitle("View Port");
		this.setPreferredSize(new Dimension(950, 500));
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

		// Empacota e mostra a aplicação.
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

		// Cria os botões.
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

		// Add os elementos gráficos ao painel.
		draw2 = canvas2.getGraphics();

		return (draw2);
	}

	// Método para limpar a tela, repintando o canvas com a cor de fundo.
	public void limparTela() {
		this.draw2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
		this.draw2.setColor(Color.BLACK);
		this.draw2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
	}

	// Seta o texto das coordenadas.
	public void setCoordenadas(Point p) {
		this.coordenadas2.setText("X = " + p.getX() + " | Y = " + p.getY());
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

	public int ativarSucessivas() {
		Object[] options = { "Ativar", "Desativar" };
		int opcao = JOptionPane.showOptionDialog(null, "Deseja ATIVAR ou DESATIVAR as Transformações Sucessivas?",
				"Transformações Sucessivas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		return opcao;
	}

	public int escolha() {
		Object[] options = { "Origem", "Ponto Qualquer" };
		int opcao = JOptionPane.showOptionDialog(null, "Qual será a referência?", "Referência",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		return opcao;
	}

	public void msg1() {
		JOptionPane.showMessageDialog(null, "Não existem retângulos mapeados!");
	}

	public void msg2() {
		JOptionPane.showMessageDialog(null, "É necessário ATIVAR as tranformações sucessivas!");
	}

}
