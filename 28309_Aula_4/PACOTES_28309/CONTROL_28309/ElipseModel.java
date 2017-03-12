package CONTROL_28309;

public class ElipseModel {

	private double xc;
	private double yc;
	private double rx;
	private double ry;

	public ElipseModel(double aXc, double aYc, double aRx, double aRy) {
		this.xc = aXc;
		this.yc = aYc;
		this.rx = aRx;
		this.ry = aRy;
	}

	public double getXc() {
		return xc;
	}

	public double getYc() {
		return yc;
	}

	public double getRx() {
		return rx;
	}

	public double getRy() {
		return ry;
	}

}
