package pacotes_28309.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class PoligonoModel {

	private ArrayList<Point> vertices = new ArrayList<Point>();

	public PoligonoModel(ArrayList<Point> aVertices) {
		this.vertices = aVertices;
	}

	public ArrayList<Point> getVertices() {
		return vertices;
	}

	public Point p(int i) {
		return vertices.get(i);
	}

	public int size() {
		return vertices.size();
	}

}