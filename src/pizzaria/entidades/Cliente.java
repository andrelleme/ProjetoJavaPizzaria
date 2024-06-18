package pizzaria.entidades;

import java.util.Collections;
import java.util.List;

public class Cliente {

	private Long id;
	private String nome;
	private String sobreNome;
	private Long telefone;
	private List<Pedido> pedidos;

	public Cliente() {
	}

	public Cliente(Long id, String nome, String sobreNome, Long telefone) {
		this.setId(id);
		this.setNome(nome);
		this.setSobreNome(sobreNome);
		this.setTelefone(telefone);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public String getTelefoneString() {
		return String.valueOf(telefone);
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public List<Pedido> getPedidos() {
		if (pedidos == null)
			return Collections.emptyList();

		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}
