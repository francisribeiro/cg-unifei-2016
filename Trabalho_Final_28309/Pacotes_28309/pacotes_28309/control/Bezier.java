package pacotes_28309.control;

import java.awt.*;
import java.util.*;
import pacotes_28309.model.*;

public class Bezier extends Bresenham {

	private ArrayList<Curva> linhas;
	private ArrayList<Reta> retas;

	// Contrutor da curva de Bezier
	public Bezier(Graphics draw) {
		super(draw);
		linhas = new ArrayList<Curva>();
		retas = new ArrayList<Reta>();
		setPontos();
		generate();
	}

	// Cria uma curva de Bezier ponto a ponto implementando o algoritmo de
	// Casteljau
	private void bezier(Point p0, Point p1, Point p2, Point p3) {
		int n = 200;
		float dt = 1.0F / n;
		float x = (float) p0.getX();
		float y = (float) p0.getY();
		float x0, y0;

		for (int i = 1; i <= n; i++) {
			float t = i * dt;
			float u = 1 - t;
			float tuTriple = 3 * t * u;
			float c0 = u * u * u;
			float c1 = tuTriple * u;
			float c2 = tuTriple * t;
			float c3 = t * t * t;

			x0 = x;
			y0 = y;

			x = (float) (c0 * p0.getX() + c1 * p1.getX() + c2 * p2.getX() + c3 * p3.getX());
			y = (float) (c0 * p0.getY() + c1 * p1.getY() + c2 * p2.getY() + c3 * p3.getY());

			// Adiciona cada ponto ao array de retas
			retas.add(new Reta(new Point((int) x0, (int) y0), new Point((int) x, (int) y)));

		}
	}

	// Aloca os pontos do desenho da figura no centro da tela
	private void setPontos() {
		Point p1, t1, t2, p2;
		int x = 210;
		int y = -40;

		// orelha esquerda
		p1 = new Point(368 + x, 203 + y);
		t1 = new Point(368 + x, 203 + y);
		t2 = new Point(389 + x, 237 + y);
		p2 = new Point(389 + x, 237 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// topo cabeça
		p1 = new Point(389 + x, 237 + y);
		t1 = new Point(389 + x, 230 + y);
		t2 = new Point(428 + x, 230 + y);
		p2 = new Point(428 + x, 237 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// orelha direita
		p1 = new Point(428 + x, 237 + y);
		t1 = new Point(428 + x, 237 + y);
		t2 = new Point(449 + x, 203 + y);
		p2 = new Point(449 + x, 203 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// topo asa direita
		p1 = new Point(537 + x, 218 + y);
		t1 = new Point(560 + x, 324 + y);
		t2 = new Point(440 + x, 324 + y);
		p2 = new Point(449 + x, 203 + y);

		linhas.add(new Curva(p2, t2, t1, p1));

		// asa direita
		p1 = new Point(537 + x, 218 + y);
		t1 = new Point(696 + x, 318 + y);
		t2 = new Point(696 + x, 398 + y);
		p2 = new Point(595 + x, 486 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// baixo asa direita
		p1 = new Point(595 + x, 486 + y);
		t1 = new Point(620 + x, 398 + y);
		t2 = new Point(528 + x, 398 + y);
		p2 = new Point(517 + x, 439 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// cauda direita
		p1 = new Point(517 + x, 439 + y);
		t1 = new Point(518 + x, 398 + y);
		t2 = new Point(440 + x, 398 + y);
		p2 = new Point(408 + x, 520 + y);

		linhas.add(new Curva(p1, t1, t2, p2));

		// cauda esquerda
		p1 = new Point(300 + x, 439 + y);
		t1 = new Point(298 + x, 398 + y);
		t2 = new Point(374 + x, 398 + y);
		p2 = new Point(408 + x, 520 + y);

		linhas.add(new Curva(p2, t2, t1, p1));

		// baixo asa esquerda
		p1 = new Point(222 + x, 486 + y);
		t1 = new Point(190 + x, 398 + y);
		t2 = new Point(289 + x, 398 + y);
		p2 = new Point(300 + x, 439 + y);

		linhas.add(new Curva(p2, t2, t1, p1));

		// asa esquerda
		p1 = new Point(280 + x, 218 + y);
		t1 = new Point(118 + x, 318 + y);
		t2 = new Point(118 + x, 398 + y);
		p2 = new Point(222 + x, 486 + y);

		linhas.add(new Curva(p2, t2, t1, p1));

		// topo asa esquerda
		p1 = new Point(280 + x, 218 + y);
		t1 = new Point(256 + x, 324 + y);
		t2 = new Point(374 + x, 324 + y);
		p2 = new Point(368 + x, 203 + y);

		linhas.add(new Curva(p1, t1, t2, p2));
	}

	// Gera as curvas do desenho de Bezier
	private void generate() {
		for (Curva l : linhas)
			bezier(l.getP0(), l.getP1(), l.getP2(), l.getP3());
	}

	// Retorna o array de retas
	public ArrayList<Reta> getRetas() {
		return retas;
	}

	// Desenha o batman na tela usando o algoritmo de Bresenham
	public void desenharBatman(ArrayList<Reta> listaRetas) {
		for (Reta r : listaRetas)
			bresenhamLine((int) r.getP1().getX(), (int) r.getP1().getY(), (int) r.getP2().getX(),
					(int) r.getP2().getY());
	}

}
