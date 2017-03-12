package pacotes_28309.model;

import java.awt.Point;

public class CirculoModel {
	private Point centro;
	private int raio;

	public CirculoModel(Point aCentro, int aRaio) {
		this.centro = aCentro;
		this.raio = aRaio;
	}

	public Point getCentro() {
		return centro;
	}

	public int getRaio() {
		return raio;
	}

}
