package pizzaria.entidades;

import java.math.BigDecimal;

public abstract class Forma {

	private String nome;
	private BigDecimal tamanho;
	private BigDecimal tamanhoMinimo;
	private BigDecimal tamanhoMaximo;

	public Forma(BigDecimal tamanhoMinimo, BigDecimal tamanhoMaximo, String nome) {
		this.setTamanhoMinimo(tamanhoMinimo);
		this.setTamanhoMaximo(tamanhoMaximo);
		this.setNome(nome);
	}

	public abstract BigDecimal calcularTamanhoPelaArea(BigDecimal area);

	public abstract BigDecimal calcularArea();

	public abstract BigDecimal calcularValor(BigDecimal valor);

	public boolean validarTamanho(BigDecimal tamanho) {
		return tamanho.compareTo(tamanhoMinimo) >= 0 && tamanho.compareTo(tamanhoMaximo) <= 0;
	}

	public BigDecimal getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public void setTamanhoMaximo(BigDecimal tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}

	public BigDecimal getTamanhoMinimo() {
		return tamanhoMinimo;
	}

	public void setTamanhoMinimo(BigDecimal tamanhoMinimo) {
		this.tamanhoMinimo = tamanhoMinimo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getTamanho() {
		return tamanho;
	}

	public void setTamanho(BigDecimal tamanho) {
		this.tamanho = tamanho;
	}
}
