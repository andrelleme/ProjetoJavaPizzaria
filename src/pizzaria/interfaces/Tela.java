package pizzaria.interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import pizzaria.infra.Banco;

public abstract class Tela extends JFrame {

	private static final long serialVersionUID = 7673704517906885579L;

	private TelaInicial telaInicial;

	public Tela(TelaInicial telaInicial) {
		setTelaInicial(telaInicial);
	}
	
	public void inicializarCabecalho(String titulo) {
		setTitle(titulo);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
	}

	public void cadastrar() {
	}

	public void voltar() {

	}

	public void limparCampos() {

	}

	public TelaInicial getTelaInicial() {
		return telaInicial;
	}

	public void setTelaInicial(TelaInicial telaInicial) {
		this.telaInicial = telaInicial;
	}
	
	public void voltarParaTelaInicial() {
		setVisible(false);
		getTelaInicial().voltarParaTelaInicial();
	}
	
	public Banco getBanco() {
		return telaInicial.getBanco();
	}

}
