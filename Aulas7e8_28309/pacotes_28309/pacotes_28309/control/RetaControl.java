package pacotes_28309.control;

import java.awt.*;

import pacotes_28309.model.*;

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
	public void desenhaReta(RetaModel reta, Color color) {
		int w, h, dx1, dy1, dx2, dy2;
		int x, x2, y, y2;
		int curto, longo, numerador;

		draw.setColor(color);

		x = reta.getP1().x;
		y = reta.getP1().y;
		x2 = reta.getP2().x;
		y2 = reta.getP2().y;

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
}
