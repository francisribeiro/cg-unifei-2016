package pacotes_28309.control;

import java.awt.event.*;

//Ser� chamado a cada blitter na p�gina
public class Chrono implements ActionListener {

	AppControl ac;

	// Construtor que recebe o Canvas e que ser� repintado a cada 60
	// milisengundo
	Chrono(AppControl ac) {
		this.ac = ac;
	}

	// M�todo chamaddo para repintar a anima��o
	public void actionPerformed(ActionEvent arg0) {
		ac.myRepaint();
	}

}
