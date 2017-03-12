package CONTROL_28309;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import MODEL_28309.RetaModel;
import VIEW_28309.RetaView;

public class AplicacaoControl implements ActionListener, MouseListener, MouseMotionListener {

	private RetaView retaView;
	private Graphics draw;
	private RetaControl retaControl;
	private ArrayList<RetaModel> lista;
	private Point pointStart, pointEnd;
	private int clicks = 0;
	private boolean cruzamentos = false, perpendiculares = false;
	@SuppressWarnings("unused")
	private String tipo;

	public AplicacaoControl() {
		retaView = new RetaView(this); // Instância da interface.
		lista = new ArrayList<RetaModel>();
		draw = retaView.startDrawing();
		retaControl = new RetaControl(draw);
	}

	// Acionamento dos botões.
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Todas")) {
			tipo = "Todas";
			retaView.limparTela();
			this.showRetas("Todas");
		}

		if (e.getActionCommand().equals("Horizontais")) {
			tipo = "Horizontais";
			retaView.limparTela();
			this.showRetas("Horizontais");
		}

		if (e.getActionCommand().equals("Verticais")) {
			tipo = "Verticais";
			retaView.limparTela();
			this.showRetas("Verticais");
		}

		if (e.getActionCommand().equals("Diagonais")) {
			tipo = "Diagonais";
			retaView.limparTela();
			this.showRetas("Diagonais");
		}

		if (e.getActionCommand().equals("Apontar Cruzamentos")) {
			cruzamentos = true;
			perpendiculares = false;
		}

		if (e.getActionCommand().equals("Apontar Perpendiculares")) {
			perpendiculares = true;
			cruzamentos = false;
		}

		if (e.getActionCommand().equals("Limpar Tela")) {
			limparLista(); // Remove todos os elementos da lista.
			retaView.limparTela();
		}

	}

	// Remove todos os elementos da lista.
	private void limparLista() {
		if (this.lista.size() > 0) {
			this.lista.clear();
		}
	}

	private void desenhaRetas(RetaModel reta) {
		int x1, y1, x2, y2;
		Point p1, p2;

		cruzamentos = false;
		perpendiculares = false;

		// Pontos
		p1 = reta.getPoint_1();
		p2 = reta.getPoint_2();

		// Coordenadas do ponto 1.
		x1 = p1.x;
		y1 = p1.y;

		// Coordenadas do ponto 2.
		x2 = p2.x;
		y2 = p2.y;

		// Saída no terminal.
		System.out.println(x1 + ", " + y1 + " - " + x2 + ", " + y2);

		// Desenhando a reta.
		retaControl.drawReta(reta, Color.BLACK);

		// Redesenhando o 'X' na tela.
		retaControl.drawX(p1, Color.BLUE);
		retaControl.drawX(p2, Color.BLUE);
	}

	// Este método exibe as retas contidas no ArrayList.
	private void showRetas(String tipo) {
		int x1, y1, x2, y2;
		Point p1, p2;

		for (RetaModel reta : lista) {

			// Pontos
			p1 = reta.getPoint_1();
			p2 = reta.getPoint_2();

			// Coordenadas do ponto 1.
			x1 = p1.x;
			y1 = p1.y;

			// Coordenadas do ponto 2.
			x2 = p2.x;
			y2 = p2.y;

			// Desenha reta por tipo.
			if (tipo == "Todas") {
				desenhaRetas(reta);
			} else if (tipo == "Horizontais") {
				if (y1 == y2) {
					desenhaRetas(reta);
				}
			} else if (tipo == "Verticais") {
				if (x1 == x2) {
					desenhaRetas(reta);
				}
			} else if (tipo == "Diagonais") {
				if (x1 != x2 && y1 != y2) {
					desenhaRetas(reta);
				}
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		RetaModel reta;

		cruzamentos = false;
		perpendiculares = false;

		draw = retaView.startDrawing();
		retaControl = new RetaControl(draw);

		// Se for o primeiro clique:
		// Captura a coordenada do mouse.
		// Desenha um "X" nessa coordenada.
		if (clicks == 0) {
			pointStart = e.getPoint();
			retaControl.drawX(pointStart, Color.BLUE);
			clicks++;

			// Se for o segundo clique:
			// Captura a coordenada do mouse.
			// Desenha um 'X' nessa coordenada.
			// Cria uma reta com base no modelo eadiciona à lista
			// Exibe a reta na tela.
		} else {
			pointEnd = e.getPoint();
			retaControl.drawX(pointEnd, Color.BLUE);
			reta = new RetaModel(pointStart, pointEnd);
			lista.add(reta);
			this.showRetas("Todas");
			clicks--;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point temReta;
		String coordenadas;

		// Captura a coordenada do mouse.
		temReta = e.getPoint();

		// Exibe as coordenadas no canto direito
		coordenadas = "X = " + temReta.getX() + " | Y = " + temReta.getY();
		retaView.setLabel(coordenadas);

		// Verifica qual botão foi apertado.
		if (cruzamentos == true) {
			apontaCruzamentos(temReta);
		}

		if (perpendiculares == true) {
			apontaPerpendiculares(temReta);
		}

	}

	// Analisa os pontos de cruzamento.
	private void apontaCruzamentos(Point ponto) {
		int i = 0;
		for (RetaModel reta : lista) {
			if (retaControl.IsOnLine(reta, ponto)) {
				retaControl.drawReta(reta, Color.orange);
				for (i = 0; i <= lista.size() - 1; i++) {
					retaControl.retasConcorrentes(reta, lista.get(i), Color.RED);
				}
			} else {
				retaControl.drawReta(reta, Color.BLACK);

				for (i = 0; i <= lista.size() - 1; i++) {
					retaControl.retasConcorrentes(reta, lista.get(i), Color.GRAY);
				}
			}
		}
	}

	// Analisa as retas perpendiculares.
	private void apontaPerpendiculares(Point ponto) {
		int i = 0;
		for (RetaModel reta : lista) {
			if (retaControl.IsOnLine(reta, ponto)) {
				for (i = 0; i <= lista.size() - 2; i++) {
					if (retaControl.retasPerpendiculares(reta, lista.get(i))) {
						retaControl.drawReta(reta, Color.orange);
						retaControl.drawReta(lista.get(i), Color.orange);
					}
				}
			} else {
				retaControl.drawReta(reta, Color.BLACK);
				retaControl.drawReta(lista.get(i), Color.BLACK);
			}
		}
	}

}
