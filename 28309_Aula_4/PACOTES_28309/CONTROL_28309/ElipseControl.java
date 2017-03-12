package CONTROL_28309;

import java.awt.*;

import MODEL_28309.RetanguloModel;

public class ElipseControl {
	private Graphics draw;

	public ElipseControl(Graphics aDraw) {
		this.draw = aDraw;
	}

	// Método para plotar um único pixel na tela.
	private void plotaPixel(double x, double y) {
		draw.drawLine((int) x, (int) y, (int) x, (int) y);
	}

	// Método que desenha uma elipse
	public void desenhaElipse(ElipseModel elipse, Color color) {
		int teta, graus;
		double xc, yc, rx, ry;
		double x, y;

		draw.setColor(color);

		teta = 0;
		graus = 45;

		xc = elipse.getXc();
		yc = elipse.getYc();
		rx = elipse.getRx();
		ry = elipse.getRy();

		for (int i = 0; i < 360; i++) {
			x = xc + ry * Math.cos(teta);
			y = yc + rx * Math.sin(teta);
			plotaPixel((int) x, (int) y);
			teta = graus--;
		}
	}

	public ElipseModel modelaElipse(RetanguloModel retangulo) {
		double xc, yc, rx, ry, xa, ya;

		xc = Math.abs((retangulo.getLado1().getPoint_2().x + retangulo.getLado1().getPoint_1().x) / 2) + 1;
		yc = Math.abs((retangulo.getLado2().getPoint_2().y + retangulo.getLado2().getPoint_1().y) / 2);

		xa = retangulo.getLado2().getPoint_1().x;
		ya = retangulo.getLado1().getPoint_1().y;

		rx = (int) (Math.sqrt(((yc - ya) * (yc - ya))));
		ry = (int) (Math.sqrt(((xc - xa) * (xc - xa))));

		return new ElipseModel(xc, yc, rx, ry);

	}
}
