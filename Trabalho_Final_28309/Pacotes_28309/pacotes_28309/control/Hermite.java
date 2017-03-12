package pacotes_28309.control;

import java.awt.*;
import java.util.ArrayList;
import pacotes_28309.model.*;

public class Hermite extends Bresenham {

	private ArrayList<Curva> linhas;
	private ArrayList<Reta> retas;

	// Construtor das curvas de Hermite
	public Hermite(Graphics draw) {
		super(draw);
		linhas = new ArrayList<Curva>();
		retas = new ArrayList<Reta>();
		setPontos();
		generate();
	}

	// Gera as curvas de Hermite com base em 4 pontos
	public void hermite(Point p1, Point p2, Point t1, Point t2) {
		for (int i = 0; i < 1000; i++) {
			double t = (double) i / (double) 1000;

			double hp1 = (2 * t * t * t) - (3 * t * t) + 1;
			double hp2 = (-2 * t * t * t) + (3 * t) * t;
			double ht1 = (t * t * t) - (2 * t * t) + t;
			double ht2 = (t * t * t) - (t * t);

			int x = ((int) (hp1 * p1.getX() + ht1 * (t1.getX() - p1.getX()) + ht2 * (t2.getX() - p2.getX())
					+ hp2 * p2.getX()));
			int y = ((int) (hp1 * p1.getY() + ht1 * (t1.getY() - p1.getY()) + ht2 * (t2.getY() - p2.getY())
					+ hp2 * p2.getY()));

			// Adiciona cada ponto ao array de retas
			retas.add(new Reta(new Point(x, y), new Point(x, y)));
		}
	}

	// Faz a alocação dos pontos das curvas de Hermite no centro da tela
	private void setPontos() {
		Point p1, p2, t1, t2;

		int x = 50; // compensação em x
		int y = 40;// compensação em y

		// orelha direita
		p1 = new Point(x + 546, 130 + y);
		t1 = new Point(x + 546, 130 + y);
		p2 = new Point(x + 560, 190 + y);
		t2 = new Point(x + 560, 190 + y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// topo cabeça
		p1 = new Point(x + 560, 190 + y);
		t1 = new Point(x + 590, 160 + y);
		p2 = new Point(x + 600, 190 + y);
		t2 = new Point(x + 590, 195 + y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// orelha direita
		p1 = new Point(x + 600, 190 + y);
		t1 = new Point(x + 600, 190 + y);
		p2 = new Point(x + 614, 130 + y);
		t2 = new Point(x + 614, 130 + y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// topo asa direita
		p1 = new Point(x + 614, 130 + y);
		t1 = new Point(x + 700, 795);
		p2 = new Point(x + 726, 138 + y);
		t2 = new Point(x + 608, -40 + y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// asa direita
		p1 = new Point(x + 726, 138 + y);
		t1 = new Point(x + 1330, 420 + y);
		p2 = new Point(x + 772, 460);
		t2 = new Point(x + 230 + (2 * y), 700 - y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// baixo asa direita
		p1 = new Point(x + 772, 460);
		t1 = new Point(x + 850, 134);
		p2 = new Point(x + 683, 410);
		t2 = new Point(x + 560, 500);
		linhas.add(new Curva(p1, p2, t1, t2));

		// cauda direita
		p1 = new Point(x + 683, 410);
		t1 = new Point(x + 517, 144);
		p2 = new Point(x + 580, 490);
		t2 = new Point(x + 532, 613);
		linhas.add(new Curva(p1, p2, t1, t2));

		// cauda esquerda
		p1 = new Point(x + 580, 490);
		t1 = new Point(x + 486, 144);
		p2 = new Point(x + 477, 410);
		t2 = new Point(x + 360, 582);
		linhas.add(new Curva(p1, p2, t1, t2));

		// baixo asa esquerda
		p1 = new Point(x + 477, 410);
		t1 = new Point(x + 132, 134);
		p2 = new Point(x + 388, 460);
		t2 = new Point(x + 404, 500);
		linhas.add(new Curva(p1, p2, t1, t2));

		// asa esquerda
		p1 = new Point(x + 388, 460);
		t1 = new Point(x + -180, 200 - y);
		p2 = new Point(x + 432, 138 + y);
		t2 = new Point(x + 1020 - (2 * y), -150 + y);
		linhas.add(new Curva(p1, p2, t1, t2));

		// topo asa esquerda
		p1 = new Point(x + 432, 138 + y);
		t1 = new Point(x + 470, 800);
		p2 = new Point(x + 546, 130 + y);
		t2 = new Point(x + 500, -40 + y);

		linhas.add(new Curva(p1, p2, t1, t2));
	}

	// Gera as curvas de hermite para cada ponto
	private void generate() {
		for (Curva l : linhas)
			hermite(l.getP0(), l.getP1(), l.getP2(), l.getP3());
	}

	// Retorna um array de retas
	public ArrayList<Reta> getRetas() {
		return retas;
	}

	// Desenha o batman na tela ponto a ponto usando o algoritmo de Bresenham
	public void desenharBatman(ArrayList<Reta> listaRetas) {
		for (Reta r : listaRetas)
			bresenhamLine((int) r.getP1().getX(), (int) r.getP1().getY(), (int) r.getP2().getX(),
					(int) r.getP2().getY());
	}
}