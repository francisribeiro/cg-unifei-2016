package pacotes_28309.control;

import java.awt.*;
import java.util.*;

import javax.swing.JOptionPane;

import pacotes_28309.model.*;
import pacotes_28309.view.*;

public class TransformacoesControl {

	private Graphics draw;
	private ViewPort appViewPort;

	private ArrayList<RetanguloModel> listaRetangulos = new ArrayList<RetanguloModel>();
	private ArrayList<RetanguloModel> listaRetangulosViewPort = new ArrayList<RetanguloModel>();

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Dimension dim = tk.getScreenSize();

	private RetanguloControl retanguloControl;
	private RetaControl retaControl;

	TransformacoesControl(ViewPort avp, ArrayList<RetanguloModel> lr, ArrayList<RetanguloModel> lrvp, Graphics g) {
		this.appViewPort = avp;
		this.listaRetangulos = lr;
		this.listaRetangulosViewPort = lrvp;
		this.draw = g;

		retaControl = new RetaControl(draw);
		retanguloControl = new RetanguloControl();
	}

	public boolean mapeamento() {
		RetanguloModel newRetangulo;
		Point start, end, newStart, newEnd;
		int ci, di, cf, df;

		double wx = 0;
		double wy = 0;
		double wh = dim.height - 71;
		double wl = dim.width - 1;

		double vx = 0;
		double vy = 0;

		double vh = appViewPort.getAltura();
		double vl = appViewPort.getLargura();

		double constante = vl / wl;
		double constante1 = vh / wh;

		appViewPort.limparTela();

		for (RetanguloModel rm : listaRetangulos) {
			start = rm.getLado1().getP1();
			end = rm.getLado2().getP2();

			ci = (int) (constante * (start.getX() - wx) + vx);
			di = (int) (constante1 * (start.getY() - wy) + vy);
			cf = (int) (constante * (end.getX() - wx) + vx);
			df = (int) (constante1 * (end.getY() - wy) + vy);

			newStart = new Point(ci, di);
			newEnd = new Point(cf, df);

			newRetangulo = retanguloControl.modelaRetangulo(new RetaModel(newStart, newEnd));
			retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.ORANGE);
			listaRetangulosViewPort.add(newRetangulo);
		}

		return true;
	}

	public void translacao(int opcao, double xp, double yp, boolean mapear, boolean habilitarClique,
			boolean ativarSucessivas) {
		Point p1, p2, p3, p4;
		RetanguloModel newRetangulo;
		RetaModel l1, l2, l3, l4;
		double xi = 0, yi = 0;
		int h = 0, v = 0;
		int index = 0;

		int largura = appViewPort.getAltura();
		int altura = appViewPort.getLargura();

		if (mapear) {
			if (opcao == 0 || habilitarClique == true) {
				h = Integer.parseInt(JOptionPane.showInputDialog("Largura para translação:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getP1().getX() + h) > largura)
							|| ((rm.getLado2().getP2().getX() + h) > largura)) {
						JOptionPane.showMessageDialog(null,
								"Largura definida para translação excede o tamanho da área de trabalho!");
						h = Integer.parseInt(JOptionPane.showInputDialog("Largura para translação:"));
					}
				}

				v = Integer.parseInt(JOptionPane.showInputDialog("Altura para translação:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getP1().getY() + v) > altura)
							|| ((rm.getLado2().getP2().getY() + v) > altura)) {
						JOptionPane.showMessageDialog(null,
								"Altura definida para translação excede o tamanho da área de trabalho!");
						v = Integer.parseInt(JOptionPane.showInputDialog("Altura para translação:"));
					}
				}
			} else if (opcao == 1 && habilitarClique == false) {
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a referência!");
				habilitarClique = true;
				return;
			}

			appViewPort.limparTela();

			for (RetanguloModel rm : listaRetangulosViewPort) {

				p1 = rm.getLado1().getP1();
				p2 = rm.getLado2().getP1();
				p4 = rm.getLado3().getP1();
				p3 = rm.getLado4().getP1();

				xi = p1.getX() + xp + h;
				yi = p1.getY() + yp + v;
				p1 = new Point((int) xi, (int) yi);

				xi = p2.getX() + xp + h;
				yi = p2.getY() + yp + v;
				p2 = new Point((int) xi, (int) yi);

				xi = p3.getX() + xp + h;
				yi = p3.getY() + yp + v;
				p3 = new Point((int) xi, (int) yi);

				xi = p4.getX() + xp + h;
				yi = p4.getY() + yp + v;
				p4 = new Point((int) xi, (int) yi);

				l1 = new RetaModel(p1, p2);
				l2 = new RetaModel(p2, p4);
				l3 = new RetaModel(p4, p3);
				l4 = new RetaModel(p3, p1);

				newRetangulo = new RetanguloModel(l1, l2, l3, l4);
				retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.GREEN);

				if (ativarSucessivas) {
					listaRetangulosViewPort.remove(index);
					listaRetangulosViewPort.add(index, newRetangulo);
				}

				index++;
			}

			index = 0;
			habilitarClique = false;

		} else {
			JOptionPane.showMessageDialog(null, "Não existem retângulos mapeados!");
		}
	}

	public void escalamento(int opcao, double xp, double yp, boolean mapear, boolean habilitarClique,
			boolean ativarSucessivas) {
		Point p1, p2, p3, p4;
		RetanguloModel newRetangulo;
		RetaModel l1, l2, l3, l4;
		double xi = 0, yi = 0;
		int sx = 0, sy = 0;
		int index = 0;

		int largura = appViewPort.getAltura();
		int altura = appViewPort.getLargura();

		if (mapear) {
			if (opcao == 0 || habilitarClique == true) {
				sx = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Horizontal:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getP1().getX() + sx) > altura)
							|| ((rm.getLado2().getP2().getX() + sx) > altura)) {
						JOptionPane.showMessageDialog(null,
								"Fator de Escola Horizontal excede o tamanho da área de trabalho!");
						sx = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Horizontal:"));
					}
				}

				sy = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Vertical:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getP1().getY() + sy) > largura)
							|| ((rm.getLado2().getP2().getY() + sy) > largura)) {
						JOptionPane.showMessageDialog(null,
								"Fator de Escola Vertical excede o tamanho da área de trabalho!");
						sy = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Vertical:"));
					}
				}

			} else if (opcao == 1 && habilitarClique == false) {
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a referência!");
				habilitarClique = true;
				return;
			}

			appViewPort.limparTela();

			for (RetanguloModel rm : listaRetangulosViewPort) {

				p1 = rm.getLado1().getP1();
				p2 = rm.getLado2().getP1();
				p4 = rm.getLado3().getP1();
				p3 = rm.getLado4().getP1();

				xi = ((p1.getX() - xp) * sx) + xp;
				yi = ((p1.getY() - yp) * sy) + yp;
				p1 = new Point((int) xi, (int) yi);

				xi = ((p2.getX() - xp) * sx) + xp;
				yi = ((p2.getY() - yp) * sy) + yp;
				p2 = new Point((int) xi, (int) yi);

				xi = ((p3.getX() - xp) * sx) + xp;
				yi = ((p3.getY() - yp) * sy) + yp;
				p3 = new Point((int) xi, (int) yi);

				xi = ((p4.getX() - xp) * sx) + xp;
				yi = ((p4.getY() - yp) * sy) + yp;
				p4 = new Point((int) xi, (int) yi);

				l1 = new RetaModel(p1, p2);
				l2 = new RetaModel(p2, p4);
				l3 = new RetaModel(p4, p3);
				l4 = new RetaModel(p3, p1);

				newRetangulo = new RetanguloModel(l1, l2, l3, l4);
				retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.RED);

				if (ativarSucessivas) {
					listaRetangulosViewPort.remove(index);
					listaRetangulosViewPort.add(index, newRetangulo);
				}

				index++;
			}

			index = 0;
			habilitarClique = false;

		} else {
			JOptionPane.showMessageDialog(null, "Não existem retângulos mapeados!");
		}
	}

	public void rotacao(int opcao, double xp, double yp, boolean mapear, boolean habilitarClique,
			boolean ativarSucessivas) {
		Point p1, p2, p3, p4;
		RetanguloModel newRetangulo;
		RetaModel l1, l2, l3, l4;
		double xi = 0, yi = 0;
		double ang, teta;
		int index = 0;

		if (mapear) {
			if (opcao == 0 || habilitarClique == true) {
				ang = Double.parseDouble(JOptionPane.showInputDialog("Ângulo de Rotação em Graus:"));
				teta = (ang * Math.PI) / 180;

				appViewPort.limparTela();

				for (RetanguloModel rm : listaRetangulosViewPort) {
					p1 = rm.getLado1().getP1();
					p2 = rm.getLado2().getP1();
					p4 = rm.getLado3().getP1();
					p3 = rm.getLado4().getP1();

					xi = xp + (p1.getX() - xp) * Math.cos(teta) - (p1.getY() - yp) * Math.sin(teta);
					yi = yp + (p1.getY() - yp) * Math.cos(teta) + (p1.getX() - xp) * Math.sin(teta);
					p1 = new Point((int) xi, (int) yi);

					xi = xp + (p2.getX() - xp) * Math.cos(teta) - (p2.getY() - yp) * Math.sin(teta);
					yi = yp + (p2.getY() - yp) * Math.cos(teta) + (p2.getX() - xp) * Math.sin(teta);
					p2 = new Point((int) xi, (int) yi);

					xi = xp + (p3.getX() - xp) * Math.cos(teta) - (p3.getY() - yp) * Math.sin(teta);
					yi = yp + (p3.getY() - yp) * Math.cos(teta) + (p3.getX() - xp) * Math.sin(teta);
					p3 = new Point((int) xi, (int) yi);

					xi = xp + (p4.getX() - xp) * Math.cos(teta) - (p4.getY() - yp) * Math.sin(teta);
					yi = yp + (p4.getY() - yp) * Math.cos(teta) + (p4.getX() - xp) * Math.sin(teta);
					p4 = new Point((int) xi, (int) yi);

					l1 = new RetaModel(p1, p2);
					l2 = new RetaModel(p2, p4);
					l3 = new RetaModel(p4, p3);
					l4 = new RetaModel(p3, p1);

					newRetangulo = new RetanguloModel(l1, l2, l3, l4);
					retanguloControl.desenhaRetangulo(newRetangulo, retaControl, Color.CYAN);

					if (ativarSucessivas) {
						listaRetangulosViewPort.remove(index);
						listaRetangulosViewPort.add(index, newRetangulo);
					}
				}

				index = 0;
				habilitarClique = false;

			} else if (opcao == 1 && habilitarClique == false) {
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a referência!");
				habilitarClique = true;
				return;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Não existem retângulos mapeados!");
		}
	}

}
