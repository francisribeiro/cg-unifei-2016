package pacotes_28309.control;

import java.awt.*;

public class Transformacoes {

	// ESCALAMENTO - O escalamento foi representado na forma matricial.
	public Point escalamento(int xp, int yp, Point p, double sx, double sy) {
		Point aux, aux2;

		double m[][] = { { sx, 0, 0 }, { 0, sy, 0 }, { 0, 0, 1 } };

		aux = translacao(-1 * xp, -1 * yp, p);
		aux2 = multiplicarMatrizes(m, aux);

		return translacao(xp, yp, aux2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// ROTAÇÂO - A rotação foi representado na forma matricial.
	public Point rotacao(int xp, int yp, Point p, double ang) {
		Point aux, aux2;
		double teta = (ang * Math.PI) / 180;

		double m[][] = { { Math.cos(teta), Math.sin(teta), 0 }, { -1 * Math.sin(teta), Math.cos(teta), 0 },
				{ 0, 0, 1 } };

		aux = translacao(-1 * xp, -1 * yp, p);
		aux2 = multiplicarMatrizes(m, aux);

		return translacao(xp, yp, aux2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// TRANSLAÇÃO - A translação é feita por soma de matrizes
	public Point translacao(int h, int v, Point p) {
		int m[][] = { { h }, { v } };

		return somarMatrizes(m, (int) p.getX(), (int) p.getY());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// MULTIPLICAR MATRIZES - Multiplica duas matrizes 3 x 3
	private Point multiplicarMatrizes(double m[][], Point p) {
		double x = 0, y = 0;

		double mr[][] = new double[3][1];
		double soma = 0.0;
		double m2[][] = { { p.getX() }, { p.getY() }, { 1 } };

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 1; j++) {
				soma = 0;
				for (int k = 0; k < 3; k++) {
					soma += m[i][k] * m2[k][j];
				}
				mr[i][j] = soma;
			}
		}

		x = mr[0][0];
		y = mr[1][0];

		return new Point((int) x, (int) y);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// SOMAR MATRIZES - Soma duas matrizes para as transformações sucessivas
	private Point somarMatrizes(int m[][], int x, int y) {
		m[0][0] += x;
		m[1][0] += y;

		return new Point((int) m[0][0], (int) m[1][0]);
	}
}
