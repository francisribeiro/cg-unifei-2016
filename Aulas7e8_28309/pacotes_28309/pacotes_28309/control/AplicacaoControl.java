package pacotes_28309.control;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import pacotes_28309.model.*;
import pacotes_28309.view.*;

public class AplicacaoControl implements ActionListener, MouseListener, MouseMotionListener {

	private View appView;
	private Graphics draw;
	private RetaControl retaControl;
	private RetanguloControl retanguloControl;
	private CirculoControl circuloControl;
	private ArrayList<RetanguloModel> listaRetangulos;
	private ArrayList<CirculoModel> listaMarcadores;
	private Point pointStart, pointEnd;
	private int clicks = 0;

	public AplicacaoControl() {
		appView = new View(this);
		listaRetangulos = new ArrayList<RetanguloModel>();
		listaMarcadores = new ArrayList<CirculoModel>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("View Port")) {
			new ViewPortControl(listaRetangulos);
		}

		if (e.getActionCommand().equals("Limpar Tela")) {
			limparLista();
			appView.limparTela();
		}

	}

	private void apagarMarcadores() {
		for (CirculoModel c : listaMarcadores) {
			circuloControl.desenhaMarcador(c.getCentro(), Color.BLACK);
		}

		listaMarcadores.clear();
	}

	private void limparLista() {
		if (this.listaRetangulos.size() > 0)
			this.listaRetangulos.clear();

		if (this.listaMarcadores.size() > 0)
			this.listaMarcadores.clear();

	}

	// Exibe as coordenadas no canto da tela.
	private void coordenadas(MouseEvent e) {
		Point p = e.getPoint();
		appView.setCoordenadas(p);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		RetanguloModel newRetangulo;

		draw = appView.startDrawing();
		retaControl = new RetaControl(draw);
		retanguloControl = new RetanguloControl();
		circuloControl = new CirculoControl(draw);

		if (clicks == 0) {
			pointStart = e.getPoint();
			circuloControl.desenhaMarcador(pointStart, Color.RED);
			listaMarcadores.add(new CirculoModel(pointStart, 5));
			clicks++;
		} else {
			pointEnd = e.getPoint();

			newRetangulo = retanguloControl.modelaRetangulo(new RetaModel(pointStart, pointEnd));

			apagarMarcadores();

			retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.ORANGE);
			listaRetangulos.add(newRetangulo);

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
