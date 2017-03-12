package CONTROL_28309;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import MODEL_28309.*;
import VIEW_28309.ViewPort;

public class ViewPortControl implements ActionListener, MouseListener, MouseMotionListener {

	private Graphics draw;
	private ViewPort appViewPort;

	private CirculoControl circuloControl;
	private RetanguloControl retanguloControl;
	private RetaControl retaControl;

	private ArrayList<RetanguloModel> listaRetangulos = new ArrayList<RetanguloModel>();
	private ArrayList<RetanguloModel> listaRetangulosViewPort = new ArrayList<RetanguloModel>();

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Dimension dim = tk.getScreenSize();

	private boolean mapear = false;
	private boolean habilitarClique = false;
	private boolean ativarSucessivas = false;
	private boolean ativarCompostas = false;
	private int oQueFazer = 0;

	public ViewPortControl(ArrayList<RetanguloModel> listaRetangulos) {
		appViewPort = new ViewPort(this);

		draw = appViewPort.startDrawing();

		circuloControl = new CirculoControl(draw);
		retanguloControl = new RetanguloControl();
		retaControl = new RetaControl(draw);

		this.listaRetangulos = listaRetangulos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Mapear")) {
			mapeamento();
		}

		if (e.getActionCommand().equals("Transladar")) {
			oQueFazer = 1;
			translacao(chooseYourDestin(), 0, 0);
		}

		if (e.getActionCommand().equals("Escalar")) {
			oQueFazer = 2;
			escalamento(chooseYourDestin(), 0, 0);
		}

		if (e.getActionCommand().equals("Rotacionar")) {
			oQueFazer = 3;
			rotacao(chooseYourDestin(), 0, 0);
		}

		if (e.getActionCommand().equals("Transforma��es sucessivas")) {
			sucessivas(ativarSucessivas());
		}

		if (e.getActionCommand().equals("Tranforma��es Compostas")) {
			compostas();
		}

		if (e.getActionCommand().equals("Limpar Tela")) {
			limparLista();
			appViewPort.limparTela();
			mapear = false;
		}
	}

	private void desenharMarcador(Point coordenada, Color color) {
		circuloControl.desenhaMarcador(coordenada, color);
	}

	private void limparLista() {
		if (this.listaRetangulosViewPort.size() > 0)
			this.listaRetangulosViewPort.clear();
	}

	private int chooseYourDestin() {
		Object[] options = { "Origem", "Ponto Qualquer" };
		int opcao = JOptionPane.showOptionDialog(null, "Qual ser� a refer�ncia?", "Refer�ncia",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		return opcao;
	}

	private void coordenadas(MouseEvent e) {
		Point temReta;
		String coordenadas;

		temReta = e.getPoint();

		coordenadas = "X = " + temReta.getX() + " | Y = " + temReta.getY();
		appViewPort.setLabel(coordenadas);
	}

	private int ativarSucessivas() {
		Object[] options = { "Ativar", "Desativar" };
		int opcao = JOptionPane.showOptionDialog(null, "Deseja ATIVAR ou DESATIVAR as Transforma��es Sucessivas?",
				"Transforma��es Sucessivas", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		return opcao;
	}

	private void sucessivas(int opcao) {
		if (opcao == 0)
			ativarSucessivas = true;
		else
			ativarSucessivas = false;
	}

	private void compostas() {
		if (!mapear) {
			JOptionPane.showMessageDialog(null, "N�o existem ret�ngulos mapeados!");
		} else {

			if (!ativarSucessivas) {
				JOptionPane.showMessageDialog(null, "� necess�rio ATIVAR as tranforma��es sucessivas!");
				sucessivas(ativarSucessivas());
			}

			if (ativarSucessivas) {
				ativarCompostas = true;
			}

			if (ativarCompostas) {
				if (appViewPort.composicao(this) == 0) {
					if (appViewPort.chkTransladar.isSelected()) {
						oQueFazer = 1;
						translacao(chooseYourDestin(), 0, 0);
					}

					if (appViewPort.chkEscalar.isSelected()) {
						oQueFazer = 2;
						escalamento(chooseYourDestin(), 0, 0);
					}

					if (appViewPort.chkRotacionar.isSelected()) {
						oQueFazer = 3;
						rotacao(chooseYourDestin(), 0, 0);
					}
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		coordenadas(arg0);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point coordenada = arg0.getPoint();

		if (habilitarClique) {
			desenharMarcador(coordenada, Color.RED);
		}

		if (oQueFazer == 1) {
			translacao(1, coordenada.getX(), coordenada.getY());
		}

		if (oQueFazer == 2) {
			escalamento(1, coordenada.getX(), coordenada.getY());
		}

		if (oQueFazer == 3) {
			rotacao(1, coordenada.getX(), coordenada.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void mapeamento() {
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
			start = rm.getLado1().getPoint_1();
			end = rm.getLado2().getPoint_2();

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

		mapear = true;
	}

	private void translacao(int opcao, double xp, double yp) {
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
				h = Integer.parseInt(JOptionPane.showInputDialog("Largura para transla��o:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getPoint_1().getX() + h) > largura)
							|| ((rm.getLado2().getPoint_2().getX() + h) > largura)) {
						JOptionPane.showMessageDialog(null,
								"Largura definida para transla��o excede o tamanho da �rea de trabalho!");
						h = Integer.parseInt(JOptionPane.showInputDialog("Largura para transla��o:"));
					}
				}

				v = Integer.parseInt(JOptionPane.showInputDialog("Altura para transla��o:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getPoint_1().getY() + v) > altura)
							|| ((rm.getLado2().getPoint_2().getY() + v) > altura)) {
						JOptionPane.showMessageDialog(null,
								"Altura definida para transla��o excede o tamanho da �rea de trabalho!");
						v = Integer.parseInt(JOptionPane.showInputDialog("Altura para transla��o:"));
					}
				}
			} else if (opcao == 1 && habilitarClique == false) {
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a refer�ncia!");
				habilitarClique = true;
				return;
			}

			appViewPort.limparTela();

			for (RetanguloModel rm : listaRetangulosViewPort) {

				p1 = rm.getLado1().getPoint_1();
				p2 = rm.getLado2().getPoint_1();
				p4 = rm.getLado3().getPoint_1();
				p3 = rm.getLado4().getPoint_1();

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
			JOptionPane.showMessageDialog(null, "N�o existem ret�ngulos mapeados!");
		}
	}

	private void escalamento(int opcao, double xp, double yp) {
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
					while (((rm.getLado1().getPoint_1().getX() + sx) > altura)
							|| ((rm.getLado2().getPoint_2().getX() + sx) > altura)) {
						JOptionPane.showMessageDialog(null,
								"Fator de Escola Horizontal excede o tamanho da �rea de trabalho!");
						sx = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Horizontal:"));
					}
				}

				sy = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Vertical:"));

				for (RetanguloModel rm : listaRetangulosViewPort) {
					while (((rm.getLado1().getPoint_1().getY() + sy) > largura)
							|| ((rm.getLado2().getPoint_2().getY() + sy) > largura)) {
						JOptionPane.showMessageDialog(null,
								"Fator de Escola Vertical excede o tamanho da �rea de trabalho!");
						sy = Integer.parseInt(JOptionPane.showInputDialog("Fator de Escola Vertical:"));
					}
				}

			} else if (opcao == 1 && habilitarClique == false) {
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a refer�ncia!");
				habilitarClique = true;
				return;
			}

			appViewPort.limparTela();

			for (RetanguloModel rm : listaRetangulosViewPort) {

				p1 = rm.getLado1().getPoint_1();
				p2 = rm.getLado2().getPoint_1();
				p4 = rm.getLado3().getPoint_1();
				p3 = rm.getLado4().getPoint_1();

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
			JOptionPane.showMessageDialog(null, "N�o existem ret�ngulos mapeados!");
		}
	}

	private void rotacao(int opcao, double xp, double yp) {
		Point p1, p2, p3, p4;
		RetanguloModel newRetangulo;
		RetaModel l1, l2, l3, l4;
		double xi = 0, yi = 0;
		double ang, teta;
		int index = 0;

		if (mapear) {
			if (opcao == 0 || habilitarClique == true) {
				ang = Double.parseDouble(JOptionPane.showInputDialog("�ngulo de Rota��o em Graus:"));
				teta = (ang * Math.PI) / 180;

				appViewPort.limparTela();

				for (RetanguloModel rm : listaRetangulosViewPort) {
					p1 = rm.getLado1().getPoint_1();
					p2 = rm.getLado2().getPoint_1();
					p4 = rm.getLado3().getPoint_1();
					p3 = rm.getLado4().getPoint_1();

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
				JOptionPane.showMessageDialog(null, "Clique em um ponto na tela para ser a refer�ncia!");
				habilitarClique = true;
				return;
			}

		} else {
			JOptionPane.showMessageDialog(null, "N�o existem ret�ngulos mapeados!");
		}
	}
}
