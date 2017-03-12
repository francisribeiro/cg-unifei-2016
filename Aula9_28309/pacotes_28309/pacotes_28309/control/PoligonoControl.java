package pacotes_28309.control;

import pacotes_28309.model.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class PoligonoControl {

	private Graphics draw;

	public PoligonoControl(Graphics aDraw) {
		this.draw = aDraw;
	}

	public void desenharPoligono(PoligonoModel poligono, RetaControl retaControl, Color color) {
		RetaModel reta;
		int len = poligono.getVertices().size();

		for (int i = 0; i < len; i++) {
			Point p1 = poligono.p(i);
			Point p2 = poligono.p((i + 1) % len);

			reta = new RetaModel(p1, p2);
			retaControl.desenhaReta(reta, color);
		}
	}
}
