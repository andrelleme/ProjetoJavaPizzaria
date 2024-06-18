package pizzaria.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Circulo extends Forma {
	public Circulo() {
		super(BigDecimal.valueOf(7), BigDecimal.valueOf(23), "Circulo");
	}

	public Circulo(BigDecimal tamanho) {
		super(BigDecimal.valueOf(7), BigDecimal.valueOf(23), "Circulo");
		setTamanho(tamanho);
	}

	@Override
	public BigDecimal calcularValor(BigDecimal valor) {
		return calcularArea().multiply(valor);
	}

	@Override
	public BigDecimal calcularArea() {
		BigDecimal pi = BigDecimal.valueOf(Math.PI);
        BigDecimal squaredRadius = getTamanho().multiply(getTamanho());
        return pi.multiply(squaredRadius);
	}

	@Override
	public BigDecimal calcularTamanhoPelaArea(BigDecimal area) {
		BigDecimal raio = BigDecimal.valueOf(Math.sqrt(area.doubleValue() / Math.PI));
        return raio.setScale(2, RoundingMode.HALF_EVEN);
	}
}
