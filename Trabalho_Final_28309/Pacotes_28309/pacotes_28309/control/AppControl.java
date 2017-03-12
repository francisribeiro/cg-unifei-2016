package pacotes_28309.control;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import javax.swing.Timer;
import pacotes_28309.model.*;
import pacotes_28309.view.Tela;

@SuppressWarnings("serial")
public class AppControl extends Canvas implements ActionListener {

	// Graficos na tela
	private Graphics draw;

	// Vari�veis das curvas para controle do usu�rio
	private Bezier bezier = new Bezier(draw);
	private Hermite hermite = new Hermite(draw);

	// Arrays para manipula��o das anima��es
	@SuppressWarnings("unchecked")
	private ArrayList<Reta> arrayBezier = (ArrayList<Reta>) bezier.getRetas().clone();
	@SuppressWarnings("unchecked")
	private ArrayList<Reta> arrayHermite = (ArrayList<Reta>) hermite.getRetas().clone();

	// Arrays para auxiliar as anima��es
	private ArrayList<Reta> auxAnimacao = new ArrayList<Reta>();
	private ArrayList<Reta> auxAnimacaoU = new ArrayList<Reta>();

	// Controle de anima��o para manipula��o do usu�rio
	private Animacoes animacaoH = new Animacoes(); // Hermite
	private Animacoes animacaoB = new Animacoes(); // Bezier
	private Animacoes animacaoUH = new Animacoes(); // Usu�rio Hermite
	private Animacoes animacaoUB = new Animacoes(); // Usu�rio Bezier

	// Uma flag se a pintura estiver em progresso (necess�ria caso os c�lculos
	// sejam muito longos)
	private volatile boolean repaintInProgress = false;

	// Flags para controle dos botões superiores
	private volatile boolean btnBezier = false;
	private volatile boolean btnHermite = false;
	private volatile boolean btnAnimacaoBezier = false;
	private volatile boolean btnAnimacaoHermite = false;
	private volatile boolean btnFinalizarAnimacao = false;
	private volatile boolean btnPreenchimento = false;

	// Flags de controle de parada
	private volatile boolean wasBezier = false;
	public static boolean wasHermite = false;

	private int xb = -5; // Deslocamento no eixo x em bezier
	private int yb = -5; // Deslocamento no eixo y em bezier
	private int xh = -12; // Deslocamento no eixo x em hermite
	private int yh = -12; // Deslocamento no eixo y em hermite
	private int anguloB = 2; // Angulo de rota��o em bezier
	private int anguloH = -2; // Angulo de rota��o em hermite
	private float escala = 0.1f; // Fator de escala
	private int velocidade = 1; // velocidade da anima��o

	// Flags para controle de zoom
	private volatile boolean zoomAtivado = false;
	private volatile boolean btnZoomIn = false;
	private volatile boolean btnZoomOut = false;

	// Flags para controle de movimento
	private volatile boolean moverEmX = false;
	private volatile boolean moverEmY = false;
	private volatile boolean btnMoveLeftOrRight = false;
	private volatile boolean btnMoveUpOrDown = false;

	// Flags para controle das anima��es
	private volatile boolean sucessivas = false;
	public static boolean teste = false;
	public static boolean animacaoAtiva = false;

	////////////////////////////////////////////////////////////////////////////////////////////////

	public AppControl() {
		// Ignora a requisi��o de pintura do sistema (eu cuidarei dela)
		setIgnoreRepaint(true);

		// Constru��o do Chrono que ir� me chamar
		Chrono chrono = new Chrono(this);

		// Diz para o chrono me chamar a cada 60 vezes por segundo (= 16 ms)
		new Timer(velocidade, chrono).start();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Bezier
		if (arg0.getActionCommand().equals("Bezier")) {
			resetarOutros();
			btnBezier = true;
			resetarMovimento();
			sucessivas = false;
			resetarAnimacoes();
		}

		// Hermite
		if (arg0.getActionCommand().equals("Hermite")) {
			resetarOutros();
			btnHermite = true;
			resetarMovimento();
			sucessivas = false;
			resetarAnimacoes();
		}

		// Preencher Figura
		if (arg0.getActionCommand().equals("Preencher Figura")) {
			btnPreenchimento = true;
		}

		// Limpar Preenchimento
		if (arg0.getActionCommand().equals("Limpar Preenchimento")) {
			btnPreenchimento = false;
		}

		// Animar Bezier
		if (arg0.getActionCommand().equals("Anima��o Bezier")) {
			resetarOutros();
			btnAnimacaoBezier = true;
			resetarMovimento();
			wasHermite = false;
			wasBezier = false;
			sucessivas = false;
			resetarAnimacoes();
		}

		// Animar
		if (arg0.getActionCommand().equals("Anima��o Hermite")) {
			resetarOutros();
			btnAnimacaoHermite = true;
			resetarMovimento();
			wasHermite = false;
			wasBezier = false;
			sucessivas = false;
			resetarAnimacoes();
		}

		// Parar Anima��o
		if (arg0.getActionCommand().equals("Finalizar Anima��o")) {
			btnFinalizarAnimacao = true;
			resetarMovimento();
		}

		// Dar Zoom
		if (arg0.getActionCommand().equals("ZoomIn")) {
			zoomAtivado = true;
			btnZoomIn = true;
			btnZoomOut = false;
			resetarOutros();
			sucessivas = true;
			animacaoAtiva = false;
		}

		// Tirar Zoom
		if (arg0.getActionCommand().equals("ZoomOut")) {
			zoomAtivado = true;
			btnZoomOut = true;
			btnZoomIn = false;
			resetarOutros();
			sucessivas = true;
			animacaoAtiva = false;
		}

		// Mover para Direita
		if (arg0.getActionCommand().equals("Direita")) {
			atualizarX();
			moverEmX = true;
			btnMoveLeftOrRight = false; // Direita
			resetarOutros();
			sucessivas = true;
		}

		// Mover para Esquerda
		if (arg0.getActionCommand().equals("Esquerda")) {
			atualizarX();
			moverEmX = true;
			btnMoveLeftOrRight = true; // Esquerda
			resetarOutros();
			sucessivas = true;
		}

		// Mover para Cima
		if (arg0.getActionCommand().equals("Cima")) {
			atualizarY();
			moverEmY = true;
			btnMoveUpOrDown = true; // Cima
			resetarOutros();
			sucessivas = true;
		}

		// Mover para Baixo
		if (arg0.getActionCommand().equals("Baixo")) {
			atualizarY();
			moverEmY = true;
			btnMoveUpOrDown = false; // Baixo
			resetarOutros();
			sucessivas = true;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Desenha o Batman usando o m�todo de Bezier
	private void Bezier(Graphics g) {
		wasBezier = true;
		wasHermite = false;

		bezier = new Bezier(g);
		bezier.desenharBatman(bezier.getRetas());

		if (btnPreenchimento)
			new Preenchimento(g, animacaoB.retasToPontos(bezier.getRetas()));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Desenha o Batman usando o m�todo de Hermite
	private void Hermite(Graphics g) {
		wasBezier = false;
		wasHermite = true;

		hermite = new Hermite(g);
		hermite.desenharBatman(hermite.getRetas());

		if (btnPreenchimento)
			new Preenchimento(g, animacaoH.retasToPontos(hermite.getRetas()));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Realiza as transforma��es sucessivas em Bezier
	private void AnimacaoBezier(Graphics g) {
		animacaoAtiva = true;

		// Vari�veis de controle de parada
		wasBezier = true;
		wasHermite = false;

		bezier = new Bezier(g);

		// Faz a transla��o da figura, tanto em X como em Y
		animacaoB.translacaoVertical(yb, arrayBezier);
		animacaoB.translacaoHorizontal(xb, arrayBezier);

		// Faz o zooIn e zoomOut da figura
		if (!teste) {
			auxAnimacao = animacaoB.zoomIn(animacaoB.girar(arrayBezier, anguloB), 0.001);
			auxAnimacao = animacaoB.zoomOut(animacaoB.girar(arrayBezier, anguloB), 0.002);
		} else {
			auxAnimacao = animacaoB.zoomIn(animacaoB.girar(arrayBezier, anguloB), 0.002);
			auxAnimacao = animacaoB.zoomOut(animacaoB.girar(arrayBezier, anguloB), 0.001);
		}

		// Desenha o resultado das transforma��es
		bezier.desenharBatman(auxAnimacao);

		// Se o preenchimento estiver ativo
		if (btnPreenchimento)
			new Preenchimento(g, animacaoB.retasToPontos(auxAnimacao));

		// Verifica a colis�o
		int testa = animacaoB.testaColisao();

		if (testa == 2) { // se colidiu em X
			xb = -xb * 2;
			anguloB = -anguloB;
		} else {
			if (Math.abs(xb) > 5)
				xb = xb / 2;
		}

		if (testa == 1) {// se colidiu em Y
			yb = -yb * 2;
			anguloB = -anguloB;
		} else {
			if (Math.abs(yb) > 5)
				yb = yb / 2;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Realiza as transforma��es sucessivas em Hermite
	private void AnimacaoHermite(Graphics g) {
		animacaoAtiva = true;

		// Vari�veis de controle de parada
		wasBezier = false;
		wasHermite = true;

		hermite = new Hermite(g);

		// Faz a transla��o da figura, tanto em X como em Y
		animacaoH.translacaoVertical(yh, arrayHermite);
		animacaoH.translacaoHorizontal(xh, arrayHermite);

		// Faz o zooIn e zoomOut da figura
		if (!teste) {
			auxAnimacao = animacaoH.zoomIn(animacaoH.girar(arrayHermite, anguloH), 0.01);
			auxAnimacao = animacaoH.zoomOut(animacaoH.girar(arrayHermite, anguloH), 0.02);
		} else {
			auxAnimacao = animacaoH.zoomIn(animacaoH.girar(arrayHermite, anguloH), 0.02);
			auxAnimacao = animacaoH.zoomOut(animacaoH.girar(arrayHermite, anguloH), 0.01);
		}

		// Desenha o resultado das transforma��es
		hermite.desenharBatman(auxAnimacao);

		// Se o preenchimento estiver ativo
		if (btnPreenchimento)
			new Preenchimento(g, animacaoH.retasToPontos(auxAnimacao));

		// Verifica a colis�o
		int testa = animacaoH.testaColisao();

		if (testa == 2) { // se colidiu em X
			xh = -xh * 2;
			anguloH = -anguloH;
		} else {
			if (Math.abs(xh) > 12)
				xh = xh / 2;
		}

		if (testa == 1) {// se colidiu em Y
			yh = -yh * 2;
			anguloH = -anguloH;
		} else {
			if (Math.abs(yh) > 12)
				yh = yh / 2;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Para a anima��o no ato de movimento e friza a imagem na �ltima
	// transforma��o
	private void finalizarAnimacao(Graphics g) {
		btnAnimacaoBezier = false;
		btnAnimacaoHermite = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Aumenta o zoom do batman selecionado (Bezier ou Hermite)
	private void zoomIn(Graphics g) {
		if (wasHermite) {
			hermite = new Hermite(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = hermite.getRetas();

			if (zoomAtivado) // Executado todas vez que o bot�o � pressionado
				auxAnimacaoU = animacaoUH.zoomIn(hermite.getRetas(), escala);
		}

		if (wasBezier) {
			bezier = new Bezier(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = bezier.getRetas();

			if (zoomAtivado) // Executado todas vez que o bot�o � pressionado
				auxAnimacaoU = animacaoUB.zoomIn(bezier.getRetas(), escala);

		}

		zoomAtivado = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Diminui o zoom do batman selecionado (Bezier ou Hermite)
	private void zoomOut(Graphics g) {
		if (wasHermite) {
			hermite = new Hermite(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = hermite.getRetas();

			if (zoomAtivado) // Executado todas vez que o bot�o � pressionado
				auxAnimacaoU = animacaoUH.zoomOut(hermite.getRetas(), escala);
		}

		if (wasBezier) {
			bezier = new Bezier(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = bezier.getRetas();

			if (zoomAtivado) // Executado todas vez que o bot�o � pressionado
				auxAnimacaoU = animacaoUB.zoomOut(bezier.getRetas(), escala);

		}

		zoomAtivado = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Move o batman no eixo x (para direita ou esquerda)
	private void moverEmX(Graphics g) {
		int testa;

		if (wasHermite) {
			hermite = new Hermite(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = hermite.getRetas();

			if (btnMoveLeftOrRight)
				animacaoUH.translacaoHorizontal(xb, auxAnimacaoU);
			else
				animacaoUH.translacaoHorizontal(-xb, auxAnimacaoU);

		}

		if (wasBezier) {
			bezier = new Bezier(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = bezier.getRetas();

			if (btnMoveLeftOrRight)
				animacaoUB.translacaoHorizontal(xb, auxAnimacaoU);
			else
				animacaoUB.translacaoHorizontal(-xb, auxAnimacaoU);
		}

		// Verifica a colis�o
		testa = animacaoUB.testaColisao();

		if (testa == 2) { // se colidiu em X
			xb = -xb;
		}

		// Verifica a colis�o
		testa = animacaoUH.testaColisao();

		if (testa == 2) { // se colidiu em X
			xb = -xb;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Move o batman no eixo x (para direita ou esquerda)
	private void moverEmY(Graphics g) {
		int testa;

		if (wasHermite) {
			hermite = new Hermite(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = hermite.getRetas();

			if (btnMoveUpOrDown)
				animacaoUH.translacaoVertical(yb, auxAnimacaoU);
			else
				animacaoUH.translacaoVertical(-yb, auxAnimacaoU);
		}

		if (wasBezier) {
			bezier = new Bezier(g);

			// Se o array de retas estiver vazio, o preencheremos
			if (auxAnimacaoU.size() == 0)
				auxAnimacaoU = bezier.getRetas();

			if (btnMoveUpOrDown)
				animacaoUB.translacaoVertical(yb, auxAnimacaoU);
			else
				animacaoUB.translacaoVertical(-yb, auxAnimacaoU);
		}

		// Verifica a colis�o
		testa = animacaoUB.testaColisao();

		if (testa == 1) {// se colidiu em Y
			yb = -yb;
		}

		// Verifica a colis�o
		testa = animacaoUH.testaColisao();

		if (testa == 1) {// se colidiu em Y
			yb = -yb;
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Meu pr�prio m�todo de pintura que repinta offline e troca o buffer a ser
	// mostrado
	public void myRepaint() {

		// Se for gastar muito tempo repintando a tela.... o ignore
		if (repaintInProgress)
			return;

		// eu n�o serei chamado duas vezes atoa
		repaintInProgress = true;

		// Pega o tamanho atual do canvas para checar se esta fora das
		// bordas
		Dimension size = getSize();

		// Fazendo a repintura na p�gina n�o mostrada
		BufferStrategy strategy = getBufferStrategy();
		Graphics graphics = strategy.getDrawGraphics();

		// Preenche o fundo com a cor preta
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, size.width, size.height);

		// Cria um retângulo amarelo
		graphics.setColor(Color.YELLOW);
		graphics.drawRect(10, 2, getWidth() - 20, getHeight() - 10);

		// Desenha o Batman usando o m�todo de Bezier
		if (btnBezier) {
			Bezier(graphics);
		}

		// Desenha o Batman usando o m�todo de Hermite
		if (btnHermite) {
			Hermite(graphics);
		}

		// Realiza as transforma��es sucessivas em Bezier
		if (btnAnimacaoBezier) {
			AnimacaoBezier(graphics);
		}

		// Realiza as transforma��es sucessivas em Hermite
		if (btnAnimacaoHermite) {
			AnimacaoHermite(graphics);
		}

		// Para a anima��o no ato de movimento e friza a imagem na �ltima
		// transforma��o
		if (btnFinalizarAnimacao) {
			finalizarAnimacao(graphics);
		}

		// Aumenta o zoom do batman selecionado (Bezier ou Hermite)
		if (btnZoomIn) {
			zoomIn(graphics);
		}

		// Diminui o zoom do batman selecionado (Bezier ou Hermite)
		if (btnZoomOut) {
			zoomOut(graphics);
		}

		// Move o batman para a esquerda ou para a direita
		if (moverEmX) {
			moverEmX(graphics);
		}

		// Move o batman para cima ou para baixo
		if (moverEmY) {
			moverEmY(graphics);
		}

		// Desenha e preenche as anima��es em Hemite
		if (wasHermite && sucessivas) {
			hermite.desenharBatman(auxAnimacaoU);

			if (btnPreenchimento)
				new Preenchimento(graphics, animacaoUH.retasToPontos(auxAnimacaoU));
		}

		// Desenha e preenche as anima��es em Bezier
		if (wasBezier && sucessivas) {
			bezier.desenharBatman(auxAnimacaoU);

			if (btnPreenchimento)
				new Preenchimento(graphics, animacaoUB.retasToPontos(auxAnimacaoU));
		}

		// Se os gr�ficos n�o foram inciados, agora ser�o
		if (graphics != null)
			graphics.dispose();

		// Desativa e desativa op��es de anima��es manuais
		if (!wasHermite && !wasBezier) {
			Tela.destivarOpcoesUsuario();
		}

		// Ativa op��es de anima��es manuais
		if (wasHermite || wasBezier) {
			Tela.ativarOpcoesUsuario();
		}

		// Mostra o pr�ximo Buffer
		strategy.show();

		// sincroniza o blitter p�gina mostrada
		Toolkit.getDefaultToolkit().sync();

		// Ok, agora pode chamar a repintura novamente
		repaintInProgress = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Reseta as flags de movimentos
	private void resetarMovimento() {
		zoomAtivado = false;
		btnZoomIn = false;
		btnZoomOut = false;
		moverEmX = false;
		moverEmY = false;
		btnMoveLeftOrRight = false;
		btnMoveUpOrDown = false;
	}

	// Reseta outras flags
	private void resetarOutros() {
		btnHermite = false;
		btnBezier = false;
		btnFinalizarAnimacao = false;
		btnAnimacaoBezier = false;
		btnAnimacaoHermite = false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// atualiza o X caso necess�rio
	private void atualizarX() {
		if (xb > 0)
			xb *= -1;

		if (xh > 0)
			xh *= -1;
	}

	// atualiza o Y caso necess�rio
	private void atualizarY() {
		if (yb > 0)
			yb *= -1;

		if (yh > 0)
			yh *= -1;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	// Reseta anima��es
	private void resetarAnimacoes() {
		animacaoUH.resetarAnimacao();
		animacaoUB.resetarAnimacao();
	}

}
