package pizzaria.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class Pizza {

	private List<Sabor> sabores;
	private Forma forma;
	private BigDecimal tamanho;

	public Pizza() {
	}

	public Pizza(List<Sabor> sabores, Forma forma, BigDecimal tamanho) {
		super();
		this.sabores = sabores;
		this.forma = forma;
		this.tamanho = tamanho;
	}

	public List<Sabor> getSabores() {
		return sabores;
	}

	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}

	public Forma getForma() {
		return forma;
	}

	public void setForma(Forma forma) {
		this.forma = forma;
	}

	public BigDecimal getTamanho() {
		return tamanho;
	}

	public void setTamanho(BigDecimal tamanho) {
		this.tamanho = tamanho;
	}

	public String getNomePizza() {
		return this.sabores.stream().map(sabor -> {
			return sabor.getNome();
		}).collect(Collectors.joining(", "));
	}

	public String getNomeTipoSabor() {
		return this.sabores.stream().map(sabor -> {
			return sabor.getTipoSabor().getNome();
		}).collect(Collectors.joining(", "));
	}

	public BigDecimal getValor() {
		return this.sabores.stream().map(sabor -> {
			forma.setTamanho(this.tamanho);
			if (forma.validarTamanho(tamanho)) {
				return forma.calcularValor(sabor.getTipoSabor().getValor()).setScale(2, RoundingMode.HALF_EVEN);
			}
			return BigDecimal.ZERO;
		}).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(this.sabores.size())).setScale(2,
				RoundingMode.HALF_EVEN);
	}

}
