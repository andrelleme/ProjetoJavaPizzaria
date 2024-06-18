package pizzaria.entidades;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido {

	private Long id;
	private Cliente cliente;
	private List<Pizza> pizzas;
	private String estado;

	public Pedido(Cliente cliente, List<Pizza> pizzas, String estado) {
		this.cliente = cliente;
		this.pizzas = pizzas;
		this.estado = estado;
	}
	
	public Pedido(Long id, Cliente cliente, List<Pizza> pizzas, String estado) {
		this.id = id;
		this.cliente = cliente;
		this.pizzas = pizzas;
		this.estado = estado;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	public String getNomePizzas() {
		return this.pizzas.stream().map(Pizza::getNomePizza).collect(Collectors.joining(", "));
	}

	public BigDecimal getPrecoTotal() {
		return pizzas.stream().map(pizza -> pizza.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
