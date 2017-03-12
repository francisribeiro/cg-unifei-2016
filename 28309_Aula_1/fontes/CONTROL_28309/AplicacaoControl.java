package CONTROL_28309;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import MODEL_28309.RetaModel;
import VIEW_28309.RetaView;

public class AplicacaoControl implements ActionListener {

	private RetaView retaView;
	private Graphics draw;
	private RetaControl retaControl;
	private ArrayList<RetaModel> lista;

	public AplicacaoControl() {
		retaView = new RetaView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Inicia a aplicação
		if (e.getActionCommand().equals("Abrir...")) {
			lista = new ArrayList<RetaModel>();
			draw = retaView.startDrawing();
			retaControl = new RetaControl(draw);
			this.lerArquivo();
			this.showRetas();
		}

		//limpa a tela
		if (e.getActionCommand().equals("Limpar")) {
			retaView.limparTela();
		}

		// Finaliza a aplicação
		if (e.getActionCommand().equals("Fechar")) {
			int option;

			option = JOptionPane.showConfirmDialog(null, "Deseja finalizar a aplicação?", "Fechar",
					JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	private String lerArquivo() {

		JFileChooser arquivo;
		File diretorio, nomeArq = null;
		int saida, pontos[], i, x1, y1, x2, y2;
		FileReader arq;
		BufferedReader lerArq;
		String linha, arrayValores[], msg, nomeArqLido = null;
		RetaModel reta;
		Point p1, p2;

		// Localizando o arquivo
		arquivo = new JFileChooser();
		diretorio = new File("..\\");
		arquivo.setCurrentDirectory(diretorio);
		arquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		saida = arquivo.showOpenDialog(arquivo);

		if (saida == JFileChooser.APPROVE_OPTION) {

			try {

				// Fazendo a leitura do arquivo
				nomeArq = arquivo.getSelectedFile();
				arq = new FileReader(nomeArq);
				lerArq = new BufferedReader(arq);
				linha = lerArq.readLine();
				pontos = new int[4];

				// Lendo linha a linha
				while (linha != null) {
					i = 0;
					arrayValores = linha.split(" ");

					// Separando os pontos da linha do arquivo
					for (String s : arrayValores) {
						pontos[i++] = Integer.parseInt(s.trim());
					}

					// Instanciando primeiro ponto
					x1 = pontos[0];
					y1 = pontos[1];
					p1 = new Point(x1, y1);

					// Instanciando segundo ponto
					x2 = pontos[2];
					y2 = pontos[3];
					p2 = new Point(x2, y2);

					// Instanciando reta
					reta = new RetaModel(p1, p2);

					// Add reta a lista
					lista.add(reta);

					// passa para a proxima linha;
					linha = lerArq.readLine();
				}

				// Fechando buffer e arquivo
				lerArq.close();
				arq.close();

				nomeArqLido = nomeArq.toString();
			} catch (FileNotFoundException e) {
				msg = "Arquivo " + nomeArq + " não encontrado";
				JOptionPane.showMessageDialog(null, msg, "", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				msg = "Erro no arquivo: " + nomeArq;
				JOptionPane.showMessageDialog(null, msg, "", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				msg = "Erro no arquivo: " + nomeArq;
				JOptionPane.showMessageDialog(null, msg, "", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		return nomeArqLido;

	}

	private void showRetas() {
		int x1, y1, x2, y2;
		Point p1, p2;

		for (RetaModel reta : lista) {

			// Pontos
			p1 = reta.getPoint_1();
			p2 = reta.getPoint_2();

			// Coordenadas do ponto 1
			x1 = p1.x;
			y1 = p1.y;

			// Coordenadas do ponto 2
			x2 = p2.x;
			y2 = p2.y;

			// Saída no terminal
			System.out.println(x1 + ", " + y1 + " - " + x2 + ", " + y2);

			// desenhando as retas
			retaControl.drawReta(reta);
		}
	}
}
