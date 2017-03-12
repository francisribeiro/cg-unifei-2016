package CONTROL_28309;

import java.awt.*;

import MODEL_28309.RetaModel;

public class RetaControl {

	private Graphics draw;

	public RetaControl(Graphics aDraw) {
		this.draw = aDraw;
	}

	// Método para plotar um único pixel na tela.
	private void plotaPixel(double x, double y) {
		draw.drawLine((int) x, (int) y, (int) x, (int) y);
	}
	
	// Método para desenhar a reta na tela ponto a ponto.
	public void classico(RetaModel reta, Color color) {
		Point point_1, point_2, aux;
		double dx, dy, j;

		draw.setColor(color);

		point_1 = reta.getPoint_1();
		point_2 = reta.getPoint_2();

		//swap
		if (point_1.x > point_2.x) {
			aux = point_1;
			point_1 = point_2;
			point_2 = point_1;
		}

		dx = point_2.x - point_1.x;
		dy = point_2.y - point_1.y;

		if (dx == 0)
			dx = 2;

		for (int i = point_1.x; i <= point_2.x; i++) {
			j = point_1.y + dy * (i - point_1.x) / dx;
			plotaPixel(i, j);
		}
	}

	// Método para desenhar a reta na tela ponto a ponto.
	public void ddaSimples(RetaModel reta, Color color) {
		double x, x2, y, y2;
		double dx, dy, m, xt, yt;
		double m_inverso;
		int i = 0;

		draw.setColor(color);

		x = reta.getPoint_1().x;
		y = reta.getPoint_1().y;
		x2 = reta.getPoint_2().x;
		y2 = reta.getPoint_2().y;

		dx = x2 - x;
		dy = y2 - y;

		m = dy / dx;

		// PLOTANDO PRIMEIRO PIXEL
		if ((Math.abs(m) < 1 && (x > x2)) || ((Math.abs(m) > 1) && (y > y2))) {
			troca(x, y, x2, y2);
		}

		plotaPixel(x, y);

		// PLOTANDO OS PIXELS INTERMEDIÁRIOS
		if (Math.abs(m) < 1) {
			yt = y;
			for (i = (int) (x + 1); i < (int) (x2 - 1); i++) {
				yt += m;
				plotaPixel((double) i, yt);
			}
		} else {
			m_inverso = 1 / m;
			xt = x;
			for (i = (int) (y + 1); i < (int) (y2 - 1); i++) {
				xt += m_inverso;
				plotaPixel(xt, (double) i);
			}
		}

		// PLOTANDO ULTIMO PIXEL
		plotaPixel(x2, y2);

	}

	// Método para desenhar a reta na tela ponto a ponto.
	public void ddaInteiro(RetaModel reta, Color color) {
		int w, h, dx1, dy1, dx2, dy2;
		int x, x2, y, y2;
		int curto, longo, numerador;

		draw.setColor(color);

		x = reta.getPoint_1().x;
		y = reta.getPoint_1().y;
		x2 = reta.getPoint_2().x;
		y2 = reta.getPoint_2().y;

		w = x2 - x;
		h = y2 - y;

		dx1 = 0;
		dy1 = 0;
		dx2 = 0;
		dy2 = 0;

		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;

		longo = Math.abs(w);
		curto = Math.abs(h);

		if (!(longo > curto)) {
			longo = Math.abs(h);
			curto = Math.abs(w);

			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;

			dx2 = 0;
		}

		numerador = longo >> 1;

		for (int i = 0; i <= longo; i++) {
			plotaPixel(x, y);
			numerador += curto;

			if (!(numerador < longo)) {
				numerador -= longo;
				x += dx1;
				y += dy1;
			} else {
				x += dx2;
				y += dy2;
			}
		}
	}

	//Swap
	private void troca(double x1, double y1, double x2, double y2) {
		double aux;

		aux = x2;
		x2 = x1;
		x1 = aux;
		aux = y2;
		y2 = y1;
		y1 = aux;
	}

	// Método para desenhar um 'X' em cada ponto do segmento de reta.
	public void drawX(Point point, Color color) {
		draw.setColor(color);

		// Cada uma das linhas desenha uma "perna do x".
		ddaInteiro(new RetaModel(new Point(point.x, point.y), new Point(point.x + 5, point.y - 5)), color);
		ddaInteiro(new RetaModel(new Point(point.x, point.y), new Point(point.x - 5, point.y + 5)), color);
		ddaInteiro(new RetaModel(new Point(point.x - 5, point.y - 5), new Point(point.x, point.y)), color);
		ddaInteiro(new RetaModel(new Point(point.x + 5, point.y + 5), new Point(point.x, point.y)), color);
	}

	// Método que verifica se as retas tem pontos em comum.
	public void retasConcorrentes(RetaModel reta1, RetaModel reta2, Color color) {
		int x1, x2, x3, x4;
		int y1, y2, y3, y4;
		double d, xi, yi;
		Point pontoColisao;
		ColisaoControl colisao;

		colisao = new ColisaoControl();

		// Se os pontos se colidirem é verificado a coordenada exata da colisão.
		if (!colisao.seCruzam(reta1.getPoint_1(), reta1.getPoint_2(), reta2.getPoint_1(), reta2.getPoint_2())) {
			return;
		}

		// Coordenadas da reta 1.
		x1 = reta1.getPoint_1().x;
		x2 = reta1.getPoint_2().x;
		y1 = reta1.getPoint_1().y;
		y2 = reta1.getPoint_2().y;

		// Coordenadas da reta 2.
		x3 = reta2.getPoint_1().x;
		x4 = reta2.getPoint_2().x;
		y3 = reta2.getPoint_1().y;
		y4 = reta2.getPoint_2().y;

		// Cálculo do Determinante.
		d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

		if (d == 0)
			return;

		// Pontos onde as retas se cruzam.
		xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
		yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

		// Desenha o 'X' vermelho no ponto de cruzaento.
		pontoColisao = new Point((int) xi, (int) yi);
		drawX(pontoColisao, color);
	}

	// Método que verifica se as retas são perpendiculares.
	public boolean retasPerpendiculares(RetaModel reta1, RetaModel reta2) {
		int x1, x2, x3, x4;
		int y1, y2, y3, y4;
		double m1, m2;
		ColisaoControl colisao;

		// Coordenadas da reta 1.
		x1 = reta1.getPoint_1().x;
		x2 = reta1.getPoint_2().x;
		y1 = reta1.getPoint_1().y;
		y2 = reta1.getPoint_2().y;

		// Coordenadas da reta 2.
		x3 = reta2.getPoint_1().x;
		x4 = reta2.getPoint_2().x;
		y3 = reta2.getPoint_1().y;
		y4 = reta2.getPoint_2().y;

		// Caso especial de divisão por zero
		if (x1 - x2 == 0 || x3 - x4 == 0) {
			if (y1 == y2 || y3 == y4) {
				colisao = new ColisaoControl();

				if (colisao.seCruzam(reta1.getPoint_1(), reta1.getPoint_2(), reta2.getPoint_1(), reta2.getPoint_2())) {
					return true;
				}
			}
			return false;
		}

		// Coeficientes.
		m1 = (y2 - y1) / (x2 - x1);
		m2 = (y4 - y3) / (x4 - x3);

		// Verifica se são o Oposto do Inverso.
		if (m1 * m2 == -1) {
			return true;
		}

		return false;
	}

	// Método para verificar se existe uma reta
	// em determinada coordenada do mouse.
	public boolean IsOnLine(RetaModel reta, Point checkPoint) {
		int x1, x2, y1, y2;
		int x, y;
		int det, det2;

		// Coordenadas do mouse.
		y = checkPoint.y;
		x = checkPoint.x;

		// Coordenadas das retas.
		x1 = reta.getPoint_1().x;
		y1 = reta.getPoint_1().y;
		x2 = reta.getPoint_2().x;
		y2 = reta.getPoint_2().y;

		// Verifica se tem divisão por zero.
		if (y2 - y1 == 0 || x2 - x1 == 0)
			return true;

		// Coefiecientes da retas.
		det = (y - y1) / (y2 - y1);
		det2 = (x - x1) / (x2 - x1);

		// verifica se são iguais, se forem o ponto está sobre a reta.
		if (x1 - x2 != 0)
			return det - det2 == 0;

		return false;
	}

}
