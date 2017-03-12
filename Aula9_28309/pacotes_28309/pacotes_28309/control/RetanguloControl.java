package pacotes_28309.control;

import pacotes_28309.model.*;
import java.awt.*;

public class RetanguloControl {
	private int radius = 0;

	public RetanguloModel modelaRetangulo(RetaModel reta) {
		RetaModel l1 = null, l2 = null, l3 = null, l4 = null;
		int x, y, x2, y2;

		x = reta.getPoint_1().x;
		y = reta.getPoint_1().y;
		x2 = reta.getPoint_2().x;
		y2 = reta.getPoint_2().y;

		if (x > x2 && y > y2) {
			l1 = new RetaModel(new Point(x - radius, y), new Point(x2 + radius, y));
			l2 = new RetaModel(new Point(x2, y - radius), new Point(x2, y2 + radius));
			l3 = new RetaModel(new Point(x2 + radius, y2), new Point(x - radius, y2));
			l4 = new RetaModel(new Point(x, y2 + radius), new Point(x, y - radius));
		} else if (x < x2 && y > y2) {
			l1 = new RetaModel(new Point(x + radius, y), new Point(x2 - radius, y));
			l2 = new RetaModel(new Point(x2, y - radius), new Point(x2, y2 + radius));
			l3 = new RetaModel(new Point(x2 - radius, y2), new Point(x + radius, y2));
			l4 = new RetaModel(new Point(x, y2 + radius), new Point(x, y - radius));
		} else if (x > x2 && y < y2) {
			l1 = new RetaModel(new Point(x - radius, y), new Point(x2 + radius, y));
			l2 = new RetaModel(new Point(x2, y + radius), new Point(x2, y2 - radius));
			l3 = new RetaModel(new Point(x2 + radius, y2), new Point(x - radius, y2));
			l4 = new RetaModel(new Point(x, y2 - radius), new Point(x, y + radius));
		} else {
			l1 = new RetaModel(new Point(x + radius, y), new Point(x2 - radius, y));
			l2 = new RetaModel(new Point(x2, y + radius), new Point(x2, y2 - radius));
			l3 = new RetaModel(new Point(x2 - radius, y2), new Point(x + radius, y2));
			l4 = new RetaModel(new Point(x, y2 - radius), new Point(x, y + radius));
		}

		return new RetanguloModel(l1, l2, l3, l4);
	}

	public void desenhaRetangulo(RetanguloModel retangulo, RetaControl reta, Color color) {
		reta.desenhaReta(retangulo.getLado1(), color);
		reta.desenhaReta(retangulo.getLado2(), color);
		reta.desenhaReta(retangulo.getLado3(), color);
		reta.desenhaReta(retangulo.getLado4(), color);
	}

	public void desenhaRetanguloTracejado(RetanguloModel retangulo, RetaControl reta, Color color) {
		reta.desenhaRetaTracejada(retangulo.getLado1(), color);
		reta.desenhaRetaTracejada(retangulo.getLado2(), color);
		reta.desenhaRetaTracejada(retangulo.getLado3(), color);
		reta.desenhaRetaTracejada(retangulo.getLado4(), color);
	}
}
