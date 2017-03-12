package pacotes_28309.control;

import pacotes_28309.model.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PoligonoCorteControl {

	List<double[]> subject, clipper, result;

	// Construtor, gera os novos poligonos.
	PoligonoCorteControl(RetanguloModel retangulo, ArrayList<PoligonoModel> poligono, Graphics draw) {
		PoligonoControl poligonoControl = new PoligonoControl(draw);
		RetaControl retaControl = new RetaControl(draw);

		double[][] clipPoints = {
				{ retangulo.getLado1().getPoint_1().getX(), retangulo.getLado1().getPoint_1().getY() },
				{ retangulo.getLado1().getPoint_2().getX(), retangulo.getLado1().getPoint_2().getY() },
				{ retangulo.getLado3().getPoint_1().getX(), retangulo.getLado3().getPoint_1().getY() },
				{ retangulo.getLado3().getPoint_2().getX(), retangulo.getLado3().getPoint_2().getY() } };

		clipper = new ArrayList<>(Arrays.asList(clipPoints));

		for (PoligonoModel p : poligono) {
			subject = PointsToList(p);
			result = new ArrayList<>(subject);
			cortaPoligono();
			poligonoControl.desenharPoligono(ListToPolygon(result), retaControl, Color.MAGENTA);
		}
	}

	private List<double[]> PointsToList(PoligonoModel p) {
		List<double[]> myList = new ArrayList<double[]>();

		for (Point v : p.getVertices())
			myList.add(new double[] { v.getX(), v.getY() });

		return myList;
	}

	private PoligonoModel ListToPolygon(List<double[]> subject) {
		ArrayList<Point> myList = new ArrayList<Point>();

		for (double[] v : subject)
			myList.add(new Point((int) v[0], (int) v[1]));

		return new PoligonoModel(myList);
	}

	private void cortaPoligono() {
		int len = clipper.size();

		for (int i = 0; i < len; i++) {
			int len2 = result.size();
			List<double[]> input = result;
			result = new ArrayList<>(len2);

			double[] A = clipper.get((i + len - 1) % len);
			double[] B = clipper.get(i);

			for (int j = 0; j < len2; j++) {

				double[] P = input.get((j + len2 - 1) % len2);
				double[] Q = input.get(j);

				if (estaDentro(A, B, Q)) {
					if (!estaDentro(A, B, P))
						result.add(intersecao(A, B, P, Q));
					result.add(Q);
				} else if (estaDentro(A, B, P))
					result.add(intersecao(A, B, P, Q));
			}
		}
	}

	private boolean estaDentro(double[] a, double[] b, double[] c) {
		return (a[0] - c[0]) * (b[1] - c[1]) > (a[1] - c[1]) * (b[0] - c[0]);
	}

	private double[] intersecao(double[] a, double[] b, double[] p, double[] q) {
		double A1 = b[1] - a[1];
		double B1 = a[0] - b[0];
		double C1 = A1 * a[0] + B1 * a[1];

		double A2 = q[1] - p[1];
		double B2 = p[0] - q[0];
		double C2 = A2 * p[0] + B2 * p[1];

		double det = A1 * B2 - A2 * B1;
		double x = (B2 * C1 - B1 * C2) / det;
		double y = (A1 * C2 - A2 * C1) / det;

		return new double[] { x, y };
	}
}
