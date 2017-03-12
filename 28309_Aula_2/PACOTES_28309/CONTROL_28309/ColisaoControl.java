package CONTROL_28309;

import java.awt.*;

public class ColisaoControl {

	// Dados três pontos colinerares 'p', 'q' e 'r'.
	// O método checa se o ponto 'q' se encontra no segmento de linha 'pr'.
	private boolean noSegmento(Point p, Point q, Point r) {
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
				&& q.y >= Math.min(p.y, r.y))
			return true;

		return false;
	}

	// Para encontrar orientação do triplete ordenado (p, q, r).
	// O método retorna os seguintes valores:
	// 0 -> p, q e r são colineares.
	// 1 -> sentido horário.
	// 2 -> sentido anti-horário.
	private int orientacao(Point p, Point q, Point r) {
		int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		// Colineares.
		if (val == 0)
			return 0;

		// Sentido horário ou sentido anti-horário.
		return (val > 0) ? 1 : 2;
	}

	// Este método retorna:
	// true -> pontos se cruzam.
	// false -> pontos não se cruzam.
	public boolean seCruzam(Point p1, Point q1, Point p2, Point q2) {
		// Localiza as quatro orientações necessárias
		// para o caso geral e para os casos especiais.
		int o1 = orientacao(p1, q1, p2);
		int o2 = orientacao(p1, q1, q2);
		int o3 = orientacao(p2, q2, p1);
		int o4 = orientacao(p2, q2, q1);

		// Caso geral.
		if (o1 != o2 && o3 != o4)
			return true;

		// Casos especiais.
		// p1, q1 e p2 são colineares e p2 se encontra no segmento p1q1.
		if (o1 == 0 && noSegmento(p1, p2, q1))
			return true;

		// p1, q1 e p2 são colineares e q2 se encontra no segmento p1q1.
		if (o2 == 0 && noSegmento(p1, q2, q1))
			return true;

		// p2, q2 e p1 são colineares e p1 se encontra no segmento p2q2.
		if (o3 == 0 && noSegmento(p2, p1, q2))
			return true;

		// p2, q2 e q1 são colineares e q1 se encontra no segmento p2q2.
		if (o4 == 0 && noSegmento(p2, q1, q2))
			return true;

		return false; /// Não caiu em nenhum dos casos.
	}
}
