package pacotes_28309.control;

import java.awt.*;

import pacotes_28309.model.*;

public class CirculoControl {

	private Graphics draw;
	private int radius = 15;

	public CirculoControl(Graphics aDraw) {
		this.draw = aDraw;
	}

	// Método para plotar um único pixel na tela.
	private void plotaPixel(double x, double y) {
		draw.drawLine((int) x, (int) y, (int) x, (int) y);
	}

	// Desenha o cantos arredondados em cada retângulo
	public void desenhaCantos(RetanguloModel retangulo, Color color) {
		double l1_x, l1_y, l2_x, l2_y, l3_x, l3_y, l4_x, l4_y;
		int x, y, err;

		draw.setColor(color);

		l1_x = retangulo.getLado1().getPoint_1().x;
		l1_y = retangulo.getLado1().getPoint_1().y + radius;
		l2_x = retangulo.getLado2().getPoint_1().x - radius;
		l2_y = retangulo.getLado2().getPoint_1().y;
		l3_x = retangulo.getLado3().getPoint_1().x;
		l3_y = retangulo.getLado3().getPoint_1().y - radius;
		l4_x = retangulo.getLado4().getPoint_1().x + radius;
		l4_y = retangulo.getLado4().getPoint_1().y;

		if (l1_x > l3_x && l1_y < l3_y) {
			l1_x = retangulo.getLado2().getPoint_1().x + radius;
			l1_y = retangulo.getLado2().getPoint_1().y;
			l2_x = retangulo.getLado1().getPoint_1().x;
			l2_y = retangulo.getLado1().getPoint_1().y + radius;
			l3_x = retangulo.getLado4().getPoint_1().x - radius;
			l3_y = retangulo.getLado4().getPoint_1().y;
			l4_x = retangulo.getLado3().getPoint_1().x;
			l4_y = retangulo.getLado3().getPoint_1().y - radius;
		} else if (l1_x > l3_x && l1_y > l3_y) {
			l1_x = retangulo.getLado3().getPoint_1().x;
			l1_y = retangulo.getLado3().getPoint_1().y + radius;
			l2_x = retangulo.getLado4().getPoint_1().x - radius;
			l2_y = retangulo.getLado4().getPoint_1().y;
			l3_x = retangulo.getLado1().getPoint_1().x;
			l3_y = retangulo.getLado1().getPoint_1().y - radius;
			l4_x = retangulo.getLado2().getPoint_1().x + radius;
			l4_y = retangulo.getLado2().getPoint_1().y;
		} else if (l1_x < l3_x && l1_y > l3_y) {
			l1_x = retangulo.getLado4().getPoint_1().x + radius;
			l1_y = retangulo.getLado4().getPoint_1().y;
			l2_x = retangulo.getLado3().getPoint_1().x;
			l2_y = retangulo.getLado3().getPoint_1().y + radius;
			l3_x = retangulo.getLado2().getPoint_1().x - radius;
			l3_y = retangulo.getLado2().getPoint_1().y;
			l4_x = retangulo.getLado1().getPoint_1().x;
			l4_y = retangulo.getLado1().getPoint_1().y - radius;
		}

		x = radius;
		y = 0;
		err = 0;

		while (x >= y) {
			plotaPixel(l1_x - x, l1_y - y);
			plotaPixel(l1_x - y, l1_y - x);
			plotaPixel(l2_x + y, l2_y - x);
			plotaPixel(l2_x + x, l2_y - y);
			plotaPixel(l3_x + x, l3_y + y);
			plotaPixel(l3_x + y, l3_y + x);
			plotaPixel(l4_x - y, l4_y + x);
			plotaPixel(l4_x - x, l4_y + y);

			y += 1;
			err += 1 + 2 * y;

			if (2 * (err - x) + 1 > 0) {
				x -= 1;
				err += 1 - 2 * x;
			}
		}
	}

	// Método para desenhar um 'círculo' no começo do desenho.
	public void desenhaMarcador(Point point, Color color) {
		desenhaCirculoDDA(new CirculoModel(point, 5), color);
	}

	// Desenha o círculo usando o método clássico
	public void desenhaCirculoClassico(CirculoModel circulo, Color color) {
		double xc, yc, x, y1, y2, expressao;
		int raio;

		raio = circulo.getRaio();

		draw.setColor(color);

		xc = circulo.getCentro().x;
		yc = circulo.getCentro().y;

		for (x = xc - raio; x <= xc + raio; x++) {
			expressao = Math.sqrt((raio * raio) - ((x - xc) * (x - xc)));
			y1 = yc + expressao;
			y2 = yc - expressao;
			plotaPixel((int) x, (int) y1);
			plotaPixel((int) x, y2);
		}
	}

	// Desenha círculo usando o método polar simples.
	public void desenhaCirculoPolarSimples(CirculoModel circulo, Color color) {
		int teta, graus;
		double xc, yc, raio, x, y;

		teta = 0;
		graus = 45;

		raio = circulo.getRaio();

		draw.setColor(color);

		xc = circulo.getCentro().x;
		yc = circulo.getCentro().y;

		for (int i = 0; i < 360; i++) {
			x = xc + raio * Math.cos(teta);
			y = yc + raio * Math.sin(teta);
			plotaPixel((int) x, (int) y);
			teta = graus--;
		}
	}

	// Desenha círculo usando o método polar incremental.
	public void desenhaCirculoPolarIncremental(CirculoModel circulo, Color color) {
		double xc, yc, x, y, teta, deltaTeta;
		int raio;

		raio = circulo.getRaio();

		draw.setColor(color);

		xc = circulo.getCentro().x;
		yc = circulo.getCentro().y;

		x = raio;
		y = 0;
		teta = 0;
		deltaTeta = (double) 1 / raio;

		desenha8pontos((int) xc, (int) yc, (int) x, (int) y);

		while (x > y) {
			teta += deltaTeta;
			x = Math.round(raio * Math.cos(teta));
			y = Math.round(raio * Math.sin(teta));
			desenha8pontos((int) xc, (int) yc, (int) x, (int) y);
		}
	}

	// Metodo DDA para desenhar círculos
	public void desenhaCirculoDDA(CirculoModel circulo, Color color) {
		double xc, yc;
		int x, y, err, raio;

		raio = circulo.getRaio();

		draw.setColor(color);

		xc = circulo.getCentro().x;
		yc = circulo.getCentro().y;

		x = raio;
		y = 0;
		err = 0;

		while (x >= y) {
			desenha8pontos((int) xc, (int) yc, (int) x, (int) y);
			y += 1;
			err += 1 + 2 * y;

			if (2 * (err - x) + 1 > 0) {
				x -= 1;
				err += 1 - 2 * x;
			}
		}
	}

	// Desenha os pontos simétricos
	private void desenha8pontos(int xc, int yc, int x, int y) {
		plotaPixel(xc + x, yc + y);
		plotaPixel(xc + y, yc + x);
		plotaPixel(xc - y, yc + x);
		plotaPixel(xc - x, yc + y);
		plotaPixel(xc - x, yc - y);
		plotaPixel(xc - y, yc - x);
		plotaPixel(xc + y, yc - x);
		plotaPixel(xc + x, yc - y);
	}

	public CirculoModel circuloInscrito(RetanguloModel retangulo) {
		double xa1, xb1, ya2, yb2;
		double t1, t2;
		double xc, yc;
		double xa, ya;
		int raio;
		Point centro;

		xa1 = Math.abs(retangulo.getLado1().getPoint_1().x);
		xb1 = Math.abs(retangulo.getLado1().getPoint_2().x);
		t1 = Math.sqrt(Math.pow((xb1 - xa1), 2));

		ya2 = Math.abs(retangulo.getLado2().getPoint_2().y);
		yb2 = Math.abs(retangulo.getLado2().getPoint_1().y);
		t2 = Math.sqrt(Math.pow((yb2 - ya2), 2));

		xc = Math.abs((retangulo.getLado1().getPoint_2().x + retangulo.getLado1().getPoint_1().x) / 2);
		yc = Math.abs((retangulo.getLado2().getPoint_2().y + retangulo.getLado2().getPoint_1().y) / 2);

		if (t1 <= t2) {
			xa = retangulo.getLado2().getPoint_1().x;
			raio = (int) (Math.sqrt(Math.pow((xc - xa), 2)));
		} else {
			ya = retangulo.getLado1().getPoint_1().y;
			raio = (int) (Math.sqrt(Math.pow((yc - ya), 2)));
		}

		centro = new Point((int) xc + 1, (int) yc);

		return new CirculoModel(centro, raio);
	}

}
