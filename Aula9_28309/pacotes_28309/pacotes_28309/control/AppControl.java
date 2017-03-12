package pacotes_28309.control;

import pacotes_28309.model.*;
import pacotes_28309.view.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

public class AppControl implements ActionListener, MouseListener, MouseMotionListener {

	private TelaPrincipal appView;
	private Graphics draw;

	private RetaControl retaControl;
	private CirculoControl circuloControl;
	private RetanguloControl retanguloControl;
	private RetaCorteControl corteRetaControl;
	private PoligonoCorteControl cortePoligonoControl;
	private PoligonoControl poligonoControl;

	private ArrayList<RetaModel> listaRetas;
	private ArrayList<RetanguloModel> listaRetangulos;
	private ArrayList<PoligonoModel> listaPoligonos;
	private ArrayList<Point> listaVertices;

	private boolean ativarDesenharRetas = false;
	private boolean ativarDesenharRetangulo = false;
	private boolean ativarDesenharPoligono = false;

	private Point p1 = null, p2 = null, pAux = null;
	private int click = 0;

	public AppControl() {
		appView = new TelaPrincipal(this);
		listaRetas = new ArrayList<RetaModel>();
		listaRetangulos = new ArrayList<RetanguloModel>();
		listaPoligonos = new ArrayList<PoligonoModel>();
	}

	private void limparLista() {
		if (this.listaRetas.size() > 0)
			this.listaRetas.clear();

		if (this.listaRetangulos.size() > 0)
			this.listaRetangulos.clear();

		if (this.listaPoligonos.size() > 0)
			this.listaPoligonos.clear();
	}

	// Exibe as coordenadas no canto da tela.
	private void coordenadas(MouseEvent e) {
		Point p = e.getPoint();
		appView.setCoordenadas(p);
	}

	private void drawRetas() {
		for (RetaModel r : listaRetas)
			retaControl.desenhaReta(r, Color.BLUE);
	}

	private void drawPoligonos() {
		for (PoligonoModel p : listaPoligonos)
			poligonoControl.desenharPoligono(p, retaControl, Color.BLUE);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		coordenadas(arg0); // Exibe as coordenadas no canto da tela.

		if (this.listaRetas.size() > 0 || this.listaPoligonos.size() > 0)
			appView.btnLimparTela.setEnabled(true);
		else
			appView.btnLimparTela.setEnabled(false);

		if (this.listaRetangulos.size() > 0)
			appView.bntLimparAreaCorte.setEnabled(true);
		else
			appView.bntLimparAreaCorte.setEnabled(false);

		if (this.listaRetas.size() > 0 && this.listaRetangulos.size() > 0)
			appView.btnCortar.setEnabled(true);
		else
			appView.btnCortar.setEnabled(false);

		if (this.listaPoligonos.size() > 0 && this.listaRetangulos.size() > 0)
			appView.btnCortar2.setEnabled(true);
		else
			appView.btnCortar2.setEnabled(false);

		if (this.listaRetas.size() > 0 || this.listaPoligonos.size() > 0)
			appView.btnDesenharAreaCorte.setEnabled(true);
		else
			appView.btnDesenharAreaCorte.setEnabled(false);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		RetaModel reta;
		RetanguloModel retangulo;
		PoligonoModel poligono;

		draw = appView.startDrawing();
		retaControl = new RetaControl(draw);
		circuloControl = new CirculoControl(draw);
		retanguloControl = new RetanguloControl();
		poligonoControl = new PoligonoControl(draw);

		// Retas
		if (ativarDesenharRetas && click == 0) {
			p1 = arg0.getPoint();
			circuloControl.desenhaMarcador(p1, Color.RED);
			click++;
		} else if (ativarDesenharRetas && click == 1) {
			circuloControl.desenhaMarcador(p1, Color.BLACK);
			p2 = arg0.getPoint();
			reta = new RetaModel(p1, p2);
			retaControl.desenhaReta(reta, Color.BLUE);
			listaRetas.add(reta);
			click--;
			p1 = null;
			p2 = null;
		}

		// Retângulo
		if (ativarDesenharRetangulo && click == 0) {
			p1 = arg0.getPoint();
			circuloControl.desenhaMarcador(p1, Color.RED);
			click++;
		} else if (ativarDesenharRetangulo && click == 1) {
			circuloControl.desenhaMarcador(p1, Color.BLACK);
			p2 = arg0.getPoint();
			retangulo = retanguloControl.modelaRetangulo(new RetaModel(p1, p2));
			retanguloControl.desenhaRetanguloTracejado(retangulo, retaControl, Color.ORANGE);
			listaRetangulos.add(retangulo);
			ativarDesenharRetangulo = false;
			click--;
			p1 = null;
			p2 = null;
		}

		// Poligono
		if (ativarDesenharPoligono && click == 0) {
			listaVertices = new ArrayList<Point>();
			p1 = arg0.getPoint();
			circuloControl.desenhaMarcador(p1, Color.RED);
			listaVertices.add(p1);
			pAux = p1;
			click++;
		} else if (ativarDesenharPoligono && click > 0) {

			if (p2 != null)
				p1 = p2;

			p2 = arg0.getPoint();
			listaVertices.add(p2);

			reta = new RetaModel(p1, p2);
			retaControl.desenhaReta(reta, Color.BLUE);

			if (Math.abs(p2.getX() - pAux.getX()) < 3 && Math.abs(p2.getY() - pAux.getY()) < 3) {

				circuloControl.desenhaMarcador(pAux, Color.BLACK);

				p1 = null;
				p2 = null;
				pAux = null;
				click = 0;

				poligono = new PoligonoModel(listaVertices);
				listaPoligonos.add(poligono);
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("Limpar Tela")) {
			limparLista();
			appView.limparTela();
			ativarDesenharRetangulo = false;
			ativarDesenharRetas = false;
			ativarDesenharPoligono = false;
		}

		if (arg0.getActionCommand().equals("Desenhar Retas")) {
			ativarDesenharRetas = true;
			ativarDesenharRetangulo = false;
			ativarDesenharPoligono = false;
		}

		if (arg0.getActionCommand().equals("Desenhar Poligono")) {
			ativarDesenharRetas = false;
			ativarDesenharRetangulo = false;
			ativarDesenharPoligono = true;
		}

		if (arg0.getActionCommand().equals("Desenhar Área de Corte")) {
			ativarDesenharRetas = false;
			ativarDesenharRetangulo = true;
			ativarDesenharPoligono = false;
		}

		if (arg0.getActionCommand().equals("Cortar Retas")) {
			ativarDesenharRetas = false;
			ativarDesenharRetangulo = false;
			ativarDesenharPoligono = false;

			corteRetaControl = new RetaCorteControl(listaRetangulos.get(0), listaRetas, draw);
		}

		if (arg0.getActionCommand().equals("Cortar Poligonos")) {
			ativarDesenharRetas = false;
			ativarDesenharRetangulo = false;
			ativarDesenharPoligono = false;

			cortePoligonoControl = new PoligonoCorteControl(listaRetangulos.get(0), listaPoligonos, draw);
		}

		if (arg0.getActionCommand().equals("Limpar Retangulo Corte")) {
			ativarDesenharRetas = false;
			ativarDesenharRetangulo = false;
			ativarDesenharPoligono = false;

			appView.limparTela();
			listaRetangulos.clear();
			this.drawRetas();
			this.drawPoligonos();
		}
	}

}
