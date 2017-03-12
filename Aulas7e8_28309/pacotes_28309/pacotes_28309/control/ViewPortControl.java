package pacotes_28309.control;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import pacotes_28309.model.*;
import pacotes_28309.view.*;

public class ViewPortControl implements ActionListener, MouseListener, MouseMotionListener {

	private Graphics draw;
	private ViewPort appViewPort;

	private CirculoControl circuloControl;
	private RetanguloControl retanguloControl;
	private RetaControl retaControl;
	private TransformacoesControl transformacoes;

	private ArrayList<RetanguloModel> listaRetangulos = new ArrayList<RetanguloModel>();
	private ArrayList<RetanguloModel> listaRetangulosViewPort = new ArrayList<RetanguloModel>();

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
		transformacoes = new TransformacoesControl(appViewPort, listaRetangulos, listaRetangulosViewPort, draw);

		this.listaRetangulos = listaRetangulos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Mapear")) {
			mapear = transformacoes.mapeamento();
		}

		if (e.getActionCommand().equals("Transladar")) {
			oQueFazer = 1;
			transformacoes.translacao(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
		}

		if (e.getActionCommand().equals("Escalar")) {
			oQueFazer = 2;
			transformacoes.escalamento(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
		}

		if (e.getActionCommand().equals("Rotacionar")) {
			oQueFazer = 3;
			transformacoes.rotacao(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
		}

		if (e.getActionCommand().equals("Transformações sucessivas")) {
			sucessivas(ativarSucessivas());
		}

		if (e.getActionCommand().equals("Tranformações Compostas")) {
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

	private int escolha() {
		return appViewPort.escolha();
	}

	private void coordenadas(MouseEvent e) {
		Point p = e.getPoint();
		appViewPort.setCoordenadas(p);
	}

	private int ativarSucessivas() {
		return appViewPort.ativarSucessivas();
	}

	private void sucessivas(int opcao) {
		if (opcao == 0)
			ativarSucessivas = true;
		else
			ativarSucessivas = false;
	}

	private void compostas() {
		if (!mapear) {
			appViewPort.msg1();
		} else {

			if (!ativarSucessivas) {
				appViewPort.msg2();
				sucessivas(ativarSucessivas());
			}

			if (ativarSucessivas) {
				ativarCompostas = true;
			}

			if (ativarCompostas) {
				if (appViewPort.composicao(this) == 0) {
					if (appViewPort.chkTransladar.isSelected()) {
						oQueFazer = 1;
						transformacoes.translacao(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
					}

					if (appViewPort.chkEscalar.isSelected()) {
						oQueFazer = 2;
						transformacoes.escalamento(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
					}

					if (appViewPort.chkRotacionar.isSelected()) {
						oQueFazer = 3;
						transformacoes.rotacao(escolha(), 0, 0, mapear, habilitarClique, ativarSucessivas);
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

		if (habilitarClique)
			desenharMarcador(coordenada, Color.RED);

		if (oQueFazer == 1)
			transformacoes.translacao(1, coordenada.getX(), coordenada.getY(), mapear, habilitarClique,
					ativarSucessivas);

		if (oQueFazer == 2)
			transformacoes.escalamento(1, coordenada.getX(), coordenada.getY(), mapear, habilitarClique,
					ativarSucessivas);

		if (oQueFazer == 3)
			transformacoes.rotacao(1, coordenada.getX(), coordenada.getY(), mapear, habilitarClique, ativarSucessivas);

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

}
