package pizzaria.infra;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import pizzaria.entidades.Circulo;
import pizzaria.entidades.Cliente;
import pizzaria.entidades.Forma;
import pizzaria.entidades.Pedido;
import pizzaria.entidades.Pizza;
import pizzaria.entidades.Quadrado;
import pizzaria.entidades.Sabor;
import pizzaria.entidades.TipoSabor;
import pizzaria.entidades.Triangulo;

public class Banco {

	private List<Cliente> clientes;
	private List<TipoSabor> tipoSabores;
	private List<Sabor> sabores;
	private List<Forma> formas;
	private List<Pedido> pedidos;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void excluirCliente(Long id) {
		this.clientes.removeIf(c -> c.getId().equals(id));
		this.pedidos.removeIf(p -> p.getCliente().getId().equals(id));
	}
	
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<TipoSabor> getTipoSabores() {
		return tipoSabores;
	}

	public void setTipoSabores(List<TipoSabor> tipoSabores) {
		this.tipoSabores = tipoSabores;
	}

	public List<Sabor> getSabores() {
		return sabores;
	}

	public void setSabores(List<Sabor> sabores) {
		this.sabores = sabores;
	}

	public List<Forma> getFormas() {
		return formas;
	}

	public void setFormas(List<Forma> formas) {
		this.formas = formas;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}
	
	public Pedido getPedidoPeloId(Long id) {
		return this.pedidos.stream().filter(pedido -> pedido.getId().equals(id)).findFirst().get();
	}

	public List<Pedido> getPedidosPeloCliente(Long idCliente) {
		return this.pedidos.stream().filter(pedido -> pedido.getCliente().getId().equals(idCliente))
				.collect(Collectors.toList());
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Banco() {
		this.clientes = gerarClientes();
		this.tipoSabores = gerarTipoSabores();
		this.sabores = gerarSabores();
		this.formas = gerarFormas();
		this.pedidos = gerarPedidos(this.clientes.get(0));
	}

	private List<Cliente> gerarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		var cliente = new Cliente(1L, "Stefany", "Santos", Long.valueOf("6531756040"));
		cliente.setPedidos(gerarPedidos(cliente));
		clientes.add(cliente);
		clientes.add(new Cliente(2L, "Ezeni", "Reis", Long.valueOf("41987758528")));
		clientes.add(new Cliente(3L, "Luiz", "Lopez", Long.valueOf("55984741297")));
		clientes.add(new Cliente(4L, "Joel", "Miller", Long.valueOf("27967478270")));
		clientes.add(new Cliente(5L, "Jhonathan", "Barroso", Long.valueOf("96994731361")));
		return clientes;
	}

	private List<TipoSabor> gerarTipoSabores() {
		List<TipoSabor> tipos = new ArrayList<TipoSabor>();
		tipos.add(new TipoSabor("Simples", new BigDecimal(2)));
		tipos.add(new TipoSabor("Especial", new BigDecimal(4)));
		tipos.add(new TipoSabor("Premium", new BigDecimal(6)));
		return tipos;
	}

	private List<Sabor> gerarSabores() {
		List<Sabor> sabores = new ArrayList<Sabor>();
		sabores.add(new Sabor("Calabresa", this.gerarTipoSabores().get(0)));
		sabores.add(new Sabor("Frango Catupiry", this.gerarTipoSabores().get(1)));
		return sabores;
	}

	private List<Forma> gerarFormas() {
		List<Forma> formas = new ArrayList<Forma>();
		formas.add(new Quadrado());
		formas.add(new Circulo());
		formas.add(new Triangulo());
		return formas;
	}

	public Cliente buscarClientePorTelefone(String telefone) {
		return this.clientes.stream().filter(cliente -> cliente.getTelefoneString().equals(telefone)).findFirst().get();
	}

	public List<Pedido> gerarPedidos(Cliente cliente) {
		var pedidos = new ArrayList<Pedido>();
		var pedido = new Pedido(1L, cliente, gerarPizzas(), "aberto");
		pedidos.add(pedido);
		return pedidos;
	}

	public List<Pizza> gerarPizzas() {
		var pizzaQuadrada = new Pizza(gerarSabores(), new Quadrado(), BigDecimal.valueOf(15));
		var pizzaTriangulo = new Pizza(Collections.singletonList(gerarSabores().get(1)), new Triangulo(), BigDecimal.valueOf(20));
		var pizzaCirculo = new Pizza(Collections.singletonList(gerarSabores().get(1)), new Circulo(), BigDecimal.valueOf(8));
		return Arrays.asList(pizzaQuadrada, pizzaTriangulo, pizzaCirculo);
	}
}
