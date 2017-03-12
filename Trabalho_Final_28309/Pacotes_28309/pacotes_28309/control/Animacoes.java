package pacotes_28309.control;

import java.awt.*;
import java.util.*;
import pacotes_28309.model.*;

public class Animacoes {

	private Transformacoes t;

	// Parametros básicos iniciais de cada figura plotada
	private double ESCALA = 1;
	private int ANGULO = 0;
	private int XC = 658;
	private int YC = 312;

	// Pontos maximos e minimos da figura inicial
	private int MAIOR_X = 595;
	private int MAIOR_Y = 520;
	private int MENOR_X = 222;
	private int MENOR_Y = 203;

	// Construtor das animaÃ§Ãµes
	public Animacoes() {
		t = new Transformacoes();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Aumenta o zoom na figura
	public ArrayList<Reta> zoomIn(ArrayList<Reta> retas, double escala) {
		ArrayList<Reta> newRetas = new ArrayList<Reta>();

		// Define uma escala máxima para a figura
		if (AppControl.animacaoAtiva) {
			if (ESCALA < 0.8)
				ESCALA += escala;

			else
				AppControl.teste = false;
		} else {
			if (ESCALA < 1.5)
				ESCALA += escala;
		}

		// Realoca cada ponto
		for (Reta pd : retas) {
			newRetas.add(new Reta(t.escalamento(XC, YC, pd.getP1(), ESCALA, ESCALA),
					t.escalamento(XC, YC, pd.getP2(), ESCALA, ESCALA)));
		}

		// Recalcula a bounding box com base nos deslocamentos anteriores
		ArrayList<Point> pontos = retasToPontos(newRetas);
		boundingBox(pontos);

		return newRetas;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Diminui o zoom na figura
	public ArrayList<Reta> zoomOut(ArrayList<Reta> retas, double escala) {
		ArrayList<Reta> newRetas = new ArrayList<Reta>();

		// Define uma escala miníma para a figura
		if (ESCALA > 0.1)
			ESCALA -= escala;
		else {
			AppControl.teste = true;
		}

		// Realoca cada ponto
		for (Reta pd : retas) {
			newRetas.add(new Reta(t.escalamento(XC, YC, pd.getP1(), ESCALA, ESCALA),
					t.escalamento(XC, YC, pd.getP2(), ESCALA, ESCALA)));
		}

		// Recalcula a bounding box com base nos deslocamentos anteriores
		ArrayList<Point> pontos = retasToPontos(newRetas);
		boundingBox(pontos);

		return newRetas;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Faz a rotação da figura com o ângulo dado
	public ArrayList<Reta> girar(ArrayList<Reta> retas, int angulo) {
		ArrayList<Reta> newRetas = new ArrayList<Reta>();
		ANGULO += angulo; // Incrementa o ângulo inicial

		// Realoca cada ponto
		for (Reta pd : retas) {
			newRetas.add(new Reta(t.rotacao(XC, YC, pd.getP1(), ANGULO), t.rotacao(XC, YC, pd.getP2(), ANGULO)));
		}

		// Recalcula a bounding box com base nos deslocamentos anteriores
		ArrayList<Point> pontos = retasToPontos(newRetas);
		boundingBox(pontos);

		return newRetas;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Move a figura na horizontal
	public void translacaoHorizontal(int largura, ArrayList<Reta> retas) {

		// Realoca cada ponto
		for (Reta pd : retas) {
			pd.setP1(t.translacao(largura, 0, pd.getP1()));
			pd.setP2(t.translacao(largura, 0, pd.getP2()));
		}

		// Recalcula a bounding box com base nos deslocamentos anteriores
		ArrayList<Point> pontos = retasToPontos(retas);
		boundingBox(pontos);

		// Reajusta o centro Geométrico da figura
		ajustarCG(pontos);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Move a figura na vertical
	public void translacaoVertical(int altura, ArrayList<Reta> retas) {

		// Realoca cada ponto
		for (Reta pd : retas) {
			pd.setP1(t.translacao(0, altura, pd.getP1()));
			pd.setP2(t.translacao(0, altura, pd.getP2()));
		}

		// Recalcula a bounding box com base nos deslocamentos anteriores
		ArrayList<Point> pontos = retasToPontos(retas);
		boundingBox(pontos);

		// Reajusta o centro Geométrico da figura
		ajustarCG(pontos);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Método para retornar um array de pontos com base em um array de retas
	public ArrayList<Point> retasToPontos(ArrayList<Reta> retas) {
		ArrayList<Point> pontos = new ArrayList<Point>();

		// coloca cada ponto no array
		for (Reta r : retas) {
			pontos.add(r.getP1());
			pontos.add(r.getP2());
		}

		return pontos;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Recalcula a boundingBox
	private void boundingBox(ArrayList<Point> pontos) {
		MAIOR_X = MAIOR_Y = 0;
		MENOR_X = MENOR_Y = 1000000;

		for (Point p : pontos) {
			if (p.getX() > MAIOR_X)
				MAIOR_X = (int) p.getX();

			if (p.getX() < MENOR_X)
				MENOR_X = (int) p.getX();

			if (p.getY() > MAIOR_Y)
				MAIOR_Y = (int) p.getY();

			if (p.getY() < MENOR_Y)
				MENOR_Y = (int) p.getY();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Faz o reajuste do Centro Geométrico
	private void ajustarCG(ArrayList<Point> pontos) {
		int xr = pontos.get(0).x, yrmax = pontos.get(0).y;
		int posymax = 0, posymin = 0, posx = 0;

		for (int i = 0; i < pontos.size(); i++) {
			if (pontos.get(i).getY() > yrmax) {
				yrmax = pontos.get(i).y;
				posx = i;
			}

			if (pontos.get(i).getX() > xr) {
				xr = pontos.get(i).x;
				posymax = i;
			} else {
				posymin = i;
			}

		}

		XC = pontos.get(posx).x;
		YC = ((pontos.get(posymax).y + pontos.get(posymin).y) / 2) + 92;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Verifica a colisão com as bordas
	public int testaColisao() {
		int acrescimo = 0;

		if (AppControl.wasHermite == true)
			acrescimo = 5;

		if (MAIOR_Y > 700 - 125 - acrescimo) // Baixo
			return 1;

		if (MENOR_Y < 7 + acrescimo) // cima
			return 1;

		if (MENOR_X < 15 + acrescimo)// esquerda
			return 2;

		if (MAIOR_X > 1200 - 18 - acrescimo)// direita
			return 2;

		return 0;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Reseta animação
	public void resetarAnimacao() {
		ESCALA = 1;
		ANGULO = 0;
		XC = 658;
		YC = 312;
		MAIOR_X = 595;
		MAIOR_Y = 520;
		MENOR_X = 222;
		MENOR_Y = 203;
	}

}