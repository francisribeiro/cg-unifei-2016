package pacotes_28309.control;

import java.awt.*;

public class Bresenham {

	private Graphics graphics;

	// Método para plotar um único pixel na tela.
	protected void plotaPixel(double x, double y) {
		graphics.setColor(Color.YELLOW);

		// MÃ©todo do Java para plotar um pixel na tela
		graphics.drawLine((int) x, (int) y, (int) x, (int) y);
	}

	//Construtor da classe
	public Bresenham(Graphics aDraw) {
		this.graphics = aDraw;
	}

	//Desenha a reta DDA usando o algorimo de Bresenham
	public void bresenhamLine(int x0, int y0, int x1, int y1) {
		int delta_width = x1 - x0;
		int delta_height = y1 - y0;
		int dx0 = 0;
		int dy0 = 0;
		int dx1 = 0;
		int dy1 = 0;

		graphics.setColor(Color.YELLOW);

		if (delta_width < 0) {
			dx0 = -1;
		} else if (delta_width > 0) {
			dx0 = 1;
		}
		if (delta_height < 0) {
			dy0 = -1;
		} else if (delta_height > 0) {
			dy0 = 1;
		}
		if (delta_width < 0) {
			dx1 = -1;
		} else if (delta_width > 0) {
			dx1 = 1;
		}
		int longest = Math.abs(delta_width);
		int shortest = Math.abs(delta_height);
		if (!(longest > shortest)) {
			longest = Math.abs(delta_height);
			shortest = Math.abs(delta_width);
			if (delta_height < 0) {
				dy1 = -1;
			} else if (delta_height > 0) {
				dy1 = 1;
			}
			dx1 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i <= longest; i++) {
			plotaPixel(x0, y0);
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				x0 += dx0;
				y0 += dy0;
			} else {
				x0 += dx1;
				y0 += dy1;
			}
		}
	}

}