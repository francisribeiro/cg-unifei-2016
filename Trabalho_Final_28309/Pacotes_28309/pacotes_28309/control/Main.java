package pacotes_28309.control;

import javax.swing.SwingUtilities;

import pacotes_28309.view.Tela;

public class Main {

	// Inicia aplica��o
	public static void main(String[] args) {

		// Inst�ncia da aplica��o
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tela();
			}
		});
	}
}
