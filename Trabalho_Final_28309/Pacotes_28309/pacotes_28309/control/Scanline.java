package pacotes_28309.control;

public class Scanline {

	private double x0, x1, y0, y1;
	private double m;

	//Construtor da scanline
	public Scanline(double x0, double x1, double y0, double y1) {
		super();
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.m = (y1 - y0) / (x1 - x0);
	}

	// Encontra o X referente ao Y dado
	public int cruzamentoEmX(double y) {
		return (int) (1 / m * (y - y0) + x0);
	}

	// Verifica se o Y é válido
	public int cruzamentoEmY(double y) {
		if (y >= y0 && y < y1)
			return 1;

		if (y >= y1 && y < y0)
			return 1;

		return 0;
	}

}
