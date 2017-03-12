package CONTROL_28309;

import java.awt.*;

import MODEL_28309.RetaModel;

public class RetaControl {

	private Graphics draw;

	public RetaControl(Graphics aDraw) {
		this.draw = aDraw;
	}

	public void drawReta(RetaModel reta) {
		Point point_1, point_2;

		point_1 = reta.getPoint_1();
		point_2 = reta.getPoint_2();

		draw.drawLine(point_1.x, point_1.y, point_2.x, point_2.y);
	}
}
