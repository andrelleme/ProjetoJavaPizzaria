package pizzaria.entidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Triangulo extends Forma {

	public Triangulo() {
		super(BigDecimal.valueOf(20), BigDecimal.valueOf(60), "Triangulo");
	}

	public Triangulo(BigDecimal tamanho) {
		super(BigDecimal.valueOf(20), BigDecimal.valueOf(60), "Triangulo");
		setTamanho(tamanho);
	}

	@Override
	public BigDecimal calcularValor(BigDecimal valor) {
		return calcularArea().multiply(valor);
	}

	@Override
	public BigDecimal calcularArea() {
		BigDecimal squareRootOfThree = new BigDecimal(Math.sqrt(3));
        BigDecimal four = new BigDecimal(4);

        return squareRootOfThree.divide(four, RoundingMode.HALF_UP)
                .multiply(getTamanho())
                .multiply(getTamanho());
	}

	@Override
	public BigDecimal calcularTamanhoPelaArea(BigDecimal area) {
		BigDecimal lado = BigDecimal.valueOf(Math.sqrt(area.doubleValue() * 4 / Math.sqrt(3)));
        return lado.setScale(2, RoundingMode.HALF_EVEN);
	}
}
