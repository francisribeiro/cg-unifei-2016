package pacotes_28309.control;

import javax.swing.SwingUtilities;

import pacotes_28309.view.Tela;

public class Main {

	// Inicia aplicação
	public static void main(String[] args) {

		// Instância da aplicação
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tela();
			}
		});
	}
}
