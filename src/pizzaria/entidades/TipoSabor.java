package pizzaria.entidades;

import java.math.BigDecimal;

public class TipoSabor {

	private String nome;
	private BigDecimal valor;
	
	public TipoSabor() {
		
	}

	public TipoSabor(String nome, BigDecimal valor) {
		setNome(nome);
		setValor(valor);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
