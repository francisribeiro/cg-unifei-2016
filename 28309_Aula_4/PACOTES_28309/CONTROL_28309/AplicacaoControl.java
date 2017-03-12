package CONTROL_28309;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import MODEL_28309.*;
import VIEW_28309.*;

public class AplicacaoControl implements ActionListener, MouseListener, MouseMotionListener {

	private View appView;
	private Graphics draw;
	private RetaControl retaControl;
	private RetanguloControl retanguloControl;
	private CirculoControl circuloControl;
	private ElipseControl elipseControl;
	private ArrayList<RetanguloModel> listaRetangulos;
	private ArrayList<CirculoModel> listaCirculos;
	private ArrayList<CirculoModel> listaMarcadores;
	private ArrayList<ElipseModel> listaElipses;
	private Point pointStart, pointEnd;
	private int clicks = 0;

	public AplicacaoControl() {
		appView = new View(this); // InstÃ¢ncia da interface.
		listaRetangulos = new ArrayList<RetanguloModel>();
		listaCirculos = new ArrayList<CirculoModel>();
		listaMarcadores = new ArrayList<CirculoModel>();
		listaElipses = new ArrayList<ElipseModel>();
	}

	// Acionamento dos botÃµes.
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Círculo Clássico")) {
			plotaCirculo("Círculo Clássico", Color.GREEN);
			apagarMarcadores();
		}

		if (e.getActionCommand().equals("Círculo Polar Simples")) {
			plotaCirculo("Círculo Polar Simples", Color.GREEN);
			apagarMarcadores();
		}

		if (e.getActionCommand().equals("Círculo Polar Incremental")) {
			plotaCirculo("Círculo Polar Incremental", Color.GREEN);
			apagarMarcadores();
		}

		if (e.getActionCommand().equals("Círculo DDA")) {
			plotaCirculo("Círculo DDA", Color.GREEN);
			apagarMarcadores();
		}

		if (e.getActionCommand().equals("Elipses")) {
			plotaElipse(Color.RED);
			apagarMarcadores();
		}

		if (e.getActionCommand().equals("Limpar Tela")) {
			limparLista(); // Remove todos os elementos da lista.
			appView.limparTela();
		}

	}

	private void plotaCirculo(String metodo, Color color) {
		for (CirculoModel c : listaCirculos) {
			if (metodo.equals("Círculo Clássico")) {
				circuloControl.desenhaCirculoClassico(c, color);
			} else if (metodo.equals("Círculo Polar Simples")) {
				circuloControl.desenhaCirculoPolarSimples(c, color);
			} else if (metodo.equals("Círculo Polar Incremental")) {
				circuloControl.desenhaCirculoPolarIncremental(c, color);
			} else if (metodo.equals("Círculo DDA")) {
				circuloControl.desenhaCirculoDDA(c, color);
			}
		}
	}

	private void plotaElipse(Color color) {
		for (ElipseModel e : listaElipses)
			elipseControl.desenhaElipse(e, color);

	}

	private void apagarMarcadores() {
		for (CirculoModel c : listaMarcadores) {
			circuloControl.desenhaMarcador(c.getCentro(), Color.BLACK);
		}

		listaMarcadores.clear();
	}

	// Remove todos os elementos da lista.
	private void limparLista() {
		if (this.listaRetangulos.size() > 0) {
			this.listaRetangulos.clear();
		}

		if (this.listaCirculos.size() > 0) {
			this.listaCirculos.clear();
		}

		if (this.listaMarcadores.size() > 0) {
			this.listaMarcadores.clear();
		}

		if (this.listaElipses.size() > 0) {
			this.listaElipses.clear();
		}
	}

	// Exibe as coordenadas no canto da tela.
	private void coordenadas(MouseEvent e) {
		Point temReta;
		String coordenadas;

		// Captura a coordenada do mouse.
		temReta = e.getPoint();

		// Exibe as coordenadas no canto direito
		coordenadas = "X = " + temReta.getX() + " | Y = " + temReta.getY();
		appView.setLabel(coordenadas);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		RetanguloModel newRetangulo;

		// InstÃ¢ncia dos controladores
		draw = appView.startDrawing();
		retaControl = new RetaControl(draw);
		retanguloControl = new RetanguloControl();
		circuloControl = new CirculoControl(draw);
		elipseControl = new ElipseControl(draw);

		if (clicks == 0) {
			pointStart = e.getPoint(); // InÃ­cio do retÃ¢ngulo
			circuloControl.desenhaMarcador(pointStart, Color.BLUE);
			listaMarcadores.add(new CirculoModel(pointStart, 5));
			clicks++;
		} else {
			pointEnd = e.getPoint(); // Fim do retÃ¢ngulo

			// Modela um retÃ¢ngulo com base em dois pontos
			newRetangulo = retanguloControl.modelaRetangulo(new RetaModel(pointStart, pointEnd));
			retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.ORANGE);
			listaRetangulos.add(newRetangulo);

			circuloControl.desenhaCantos(newRetangulo, Color.ORANGE);
			circuloControl.desenhaMarcador(pointEnd, Color.BLUE);
			listaMarcadores.add(new CirculoModel(pointEnd, 5));
			listaCirculos.add(circuloControl.circuloInscrito(newRetangulo));

			listaElipses.add(elipseControl.modelaElipse(newRetangulo));

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
		coordenadas(e); // Exibe as coordenadas no canto da tela.
	}

}
