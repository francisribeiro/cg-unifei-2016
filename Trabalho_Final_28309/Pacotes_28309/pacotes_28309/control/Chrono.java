package pacotes_28309.control;

import java.awt.event.*;

//Será chamado a cada blitter na página
public class Chrono implements ActionListener {

	AppControl ac;

	// Construtor que recebe o Canvas e que será repintado a cada 60
	// milisengundo
	Chrono(AppControl ac) {
		this.ac = ac;
	}

	// Método chamaddo para repintar a animação
	public void actionPerformed(ActionEvent arg0) {
		ac.myRepaint();
	}

}
