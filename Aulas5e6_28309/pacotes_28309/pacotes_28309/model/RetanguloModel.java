package pacotes_28309.model;

public class RetanguloModel {
	private RetaModel lado1;
	private RetaModel lado2;
	private RetaModel lado3;
	private RetaModel lado4;

	public RetanguloModel(RetaModel aLado1, RetaModel aLado2, RetaModel aLado3, RetaModel aLado4) {
		this.lado1 = aLado1;
		this.lado2 = aLado2;
		this.lado3 = aLado3;
		this.lado4 = aLado4;
	}

	public RetaModel getLado1() {
		return lado1;
	}

	public RetaModel getLado2() {
		return lado2;
	}

	public RetaModel getLado3() {
		return lado3;
	}

	public RetaModel getLado4() {
		return lado4;
	}

}
