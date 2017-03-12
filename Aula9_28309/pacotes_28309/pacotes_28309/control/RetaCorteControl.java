package pacotes_28309.control;

import pacotes_28309.model.*;

import java.awt.*;
import java.util.ArrayList;

public class RetaCorteControl {

	public static final int DENTRO = 0;
	public static final int ESQUERDA = 1;
	public static final int DIREITA = 2;
	public static final int INFERIOR = 4;
	public static final int CIMA = 8;

	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;

	public RetaCorteControl(RetanguloModel retangulo, ArrayList<RetaModel> listaRetas, Graphics draw) {
		RetaModel clippedLine;
		RetaControl retaControl = new RetaControl(draw);

		this.xMin = retangulo.getLado1().getPoint_1().x;
		this.yMin = retangulo.getLado1().getPoint_1().y;
		this.xMax = retangulo.getLado2().getPoint_2().x;
		this.yMax = retangulo.getLado2().getPoint_2().y;

		if (retangulo.getLado1().getPoint_1().getX() < retangulo.getLado2().getPoint_2().x) {
			xMin = (int) retangulo.getLado1().getPoint_1().getX();
			xMax = (int) retangulo.getLado2().getPoint_2().getX();
		} else {
			xMax = (int) retangulo.getLado1().getPoint_1().getX();
			xMin = (int) retangulo.getLado2().getPoint_2().getX();
		}

		if (retangulo.getLado1().getPoint_1().getY() < retangulo.getLado2().getPoint_2().y) {
			yMin = (int) retangulo.getLado1().getPoint_1().getY();
			yMax = (int) retangulo.getLado2().getPoint_2().getY();
		} else {
			yMax = (int) retangulo.getLado1().getPoint_1().getY();
			yMin = (int) retangulo.getLado2().getPoint_2().getY();
		}

		for (RetaModel r : listaRetas) {

			clippedLine = this.cortar(r);
			retaControl.desenhaReta(r, Color.BLACK);

			if (clippedLine == null) {
				retaControl.desenhaReta(r, Color.BLUE);
			} else {
				retaControl.desenhaReta(new RetaModel(r.getPoint_1(),
						new Point(clippedLine.getPoint_1().x, clippedLine.getPoint_1().y)), Color.RED);
				retaControl.desenhaReta(new RetaModel(new Point(clippedLine.getPoint_2().x, clippedLine.getPoint_2().y),
						r.getPoint_2()), Color.RED);
				retaControl.desenhaReta(new RetaModel(new Point(clippedLine.getPoint_1().x, clippedLine.getPoint_1().y),
						new Point(clippedLine.getPoint_2().x, clippedLine.getPoint_2().y)), Color.GREEN);
			}
		}
	}

	/**
	 * Calcula o código de bits para um ponto (x, y) utilizando o retângulo de
	 * corte delimitada diagonalmente por (xmin, xmin), e (xmax, ymax) xmax,
	 * xmin, ymax e ymin são constantes globais.
	 */
	private int calculaOutCode(double x, double y) {
		int code = DENTRO;

		if (x < xMin) {
			code |= ESQUERDA;// À esquerda da janela de corte
		} else if (x > xMax) {
			code |= DIREITA;// À direita da janela de corte
		}
		if (y < yMin) {
			code |= INFERIOR;// Abaixo da janela de corte
		} else if (y > yMax) {
			code |= CIMA;// Acima da janela de corte
		}

		return code;
	}

	// Algoritmo de corte de linha de Cohen-Sutherland
	// P0 = (x0, y0) a P1 = (x1, y1) de encontro a um retângulo com
	// Diagonal a partir de (xmin, ymin) também (xmax, ymax).
	public RetaModel cortar(RetaModel line) {

		int x0 = line.getPoint_1().x, x1 = line.getPoint_2().x, y0 = line.getPoint_1().y, y1 = line.getPoint_2().y;

		// Calcular outcodes para P0, P1, e tudo o que ponto está fora do
		// retângulo de corte
		int outCode0 = calculaOutCode(x0, y0);
		int outCode1 = calculaOutCode(x1, y1);
		boolean accept = false;

		while (true) {
			if ((outCode0 | outCode1) == 0) { // Bit a bit OR é 0. Trivialmente
												// aceitar e sair do ciclo
				accept = true;
				break;
			} else if ((outCode0 & outCode1) != 0) { // Bit a bit e não é 0.
														// Trivialmente rejeitar
														// e sair do ciclo
				break;
			} else {
				// Falhou ambos os testes, então calcular o segmento de linha
				// para o clipe
				// A partir de um ponto fora a um cruzamento com borda clipe

				int x, y;

				// Pelo menos um ponto final está fora do retângulo de corte;
				// pegue-o.
				int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

				// Agora, encontrar o ponto de interseção;
				// use fórmulas y = y 0 + inclinação * (x - x0), x = x0 + (1 /
				// inclinação) * (y - y0)
				if ((outCodeOut & CIMA) != 0) {
					x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
					y = yMax;
				} else if ((outCodeOut & INFERIOR) != 0) {
					x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
					y = yMin;
				} else if ((outCodeOut & DIREITA) != 0) {
					y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
					x = xMax;
				} else {
					y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
					x = xMin;
				}

				// Agora vamos passar ponto fora para intersecção ponto de clipe
				// E prepare-se para o próximo passo.
				if (outCodeOut == outCode0) {
					x0 = x;
					y0 = y;
					outCode0 = calculaOutCode(x0, y0);
				} else {
					x1 = x;
					y1 = y;
					outCode1 = calculaOutCode(x1, y1);
				}
			}
		}

		if (accept)
			return new RetaModel(new Point(x0, y0), new Point(x1, y1));

		return null;
	}

}
