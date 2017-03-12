package pacotes_28309.control;

import java.awt.*;
import java.util.*;

public class Preenchimento extends Bresenham {

	private Graphics graphics;
	private ArrayList<Point> pontos;
	private ArrayList<Scanline> scanlines = new ArrayList<Scanline>();
	private double menorPontoEmY;
	private double maiorPontoEmX;

	// Construtor, instãncia um objeto preenchido com base numa lista de pontos
	public Preenchimento(Graphics aDraw, ArrayList<Point> p) {
		super(aDraw);
		this.graphics = aDraw;
		setPontos(p);
		criarScanlines();
		encontrarMaximoMinimo();
		preenchimento();
	}

	// Adiciona os pontos ao array de pontos
	private void setPontos(ArrayList<Point> p) {
		pontos = p;
	}

	// Gera as linhas para preencher a figura
	private void criarScanlines() {
		for (int i = 1; i < pontos.size(); i++) {
			scanlines.add(new Scanline(pontos.get(i - 1).x, pontos.get(i).x, pontos.get(i - 1).y, pontos.get(i).y));
		}
	}

	// Encontra os maiores e menores valore de X e Y
	private void encontrarMaximoMinimo() {
		menorPontoEmY = 9999;
		maiorPontoEmX = -9999;

		for (int i = 0; i < pontos.size(); i++) {
			double temp = pontos.get(i).y;
			if (temp < menorPontoEmY)
				menorPontoEmY = temp;
			else if (temp > maiorPontoEmX)
				maiorPontoEmX = temp;
		}
	}

	// Acha a intersecção dos pontos com as retas
	private ArrayList<Integer> localizarInterssecoes(double y) {
		ArrayList<Integer> encontros = new ArrayList<Integer>();

		for (int i = 0; i < scanlines.size(); i++) {
			Scanline l = scanlines.get(i);
			if (l.cruzamentoEmY(y) == 1) {
				encontros.add(l.cruzamentoEmX(y));
			}
		}

		// Ordenação
		Collections.sort(encontros);

		return encontros;
	}

	// Desenha o poligo, imprimindo cada reta
	public void preenchimento() {
		for (int y = (int) menorPontoEmY; y < maiorPontoEmX; y++) {
			ArrayList<Integer> pontosDeEncontro = localizarInterssecoes(y);
			for (int i = 1; i < pontosDeEncontro.size(); i += 2) {
				bresenhamLine(pontosDeEncontro.get(i), (int) y, pontosDeEncontro.get(i - 1), (int) y);
			}
		}
	}

}