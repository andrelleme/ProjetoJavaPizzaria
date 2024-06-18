package pizzaria.entidades;

public class Sabor {

	private String nome;
	private TipoSabor tipoSabor;

	public Sabor() {
	}

	public Sabor(String nome, TipoSabor tipoSabor) {
		super();
		this.nome = nome;
		this.tipoSabor = tipoSabor;
	}

	public String getNomeCompleto() {
		return nome + " - " + getTipoSabor().getNome();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoSabor getTipoSabor() {
		return tipoSabor;
	}

	public void setTipoSabor(TipoSabor tipoSabor) {
		this.tipoSabor = tipoSabor;
	}
}
