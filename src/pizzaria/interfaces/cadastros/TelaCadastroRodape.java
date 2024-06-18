package pizzaria.interfaces.cadastros;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pizzaria.interfaces.Tela;

public class TelaCadastroRodape<T extends Tela> {
	
	private T tela;

	private JPanel pnlRodape;

	private JButton btnCadastrar;
	private JButton btnVoltar;

	public TelaCadastroRodape(T tela) {
		this.tela = tela;
		inicializarRodape();

		// Adiciona a ação ao botão "Cadastrar"
		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tela.cadastrar();
			}
		});

		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tela.limparCampos();
				tela.voltar();
			}
		});
	}

	public void inicializarRodape() {
		this.tela.getContentPane().add(getPnlRodape(), BorderLayout.PAGE_END);
	}

	public JPanel getPnlRodape() {
		if (pnlRodape == null) {
			pnlRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));

			btnCadastrar = new JButton("Salvar");
			btnVoltar = new JButton("Voltar");

			pnlRodape.add(btnCadastrar);
			pnlRodape.add(btnVoltar);
		}
		return pnlRodape;
	}

}
