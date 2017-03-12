package pacotes_28309.model;

import java.awt.Point;

public class Curva {

	Point p0, p1, p2, p3;

	public Curva(Point p0, Point p1, Point p2, Point p3) {
		super();
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	public void setP0(Point p0) {
		this.p0 = p0;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public void setP3(Point p3) {
		this.p3 = p3;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	public Point getP0() {
		return p0;
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public Point getP3() {
		return p3;
	}

}
