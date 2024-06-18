package pizzaria.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quadrado extends Forma {

	public Quadrado() {
		super(BigDecimal.valueOf(10), BigDecimal.valueOf(40), "Quadrado");
	}

	public Quadrado(BigDecimal tamanho) {
		super(BigDecimal.valueOf(10), BigDecimal.valueOf(40), "Quadrado");
		setTamanho(tamanho);
	}

	@Override
	public BigDecimal calcularValor(BigDecimal valor) {
		return calcularArea().multiply(valor);
	}

	@Override
	public BigDecimal calcularArea() {
		return this.getTamanho().multiply(getTamanho());
	}

	@Override
	public BigDecimal calcularTamanhoPelaArea(BigDecimal area) {
		BigDecimal lado = BigDecimal.valueOf(Math.sqrt(area.doubleValue()));
        return lado.setScale(2, RoundingMode.HALF_EVEN);
	}
}
