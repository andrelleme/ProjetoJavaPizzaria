package pizzaria.interfaces.listagens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pizzaria.interfaces.Tela;

public class TelaListagemRodape<T extends Tela> {
	
	private T tela;

	private JPanel pnlRodape;
	private JButton btnVoltar;

	public TelaListagemRodape(T tela) {
		this.tela = tela;
		inicializarRodape();
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tela.limparCampos();
				tela.voltarParaTelaInicial();
			}
		});
	}

	public void inicializarRodape() {
		this.tela.getContentPane().add(getPnlRodape(), BorderLayout.PAGE_END);
	}

	private JPanel getPnlRodape() {
		if (pnlRodape == null) {
			pnlRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
			btnVoltar = new JButton("Voltar");
			pnlRodape.add(btnVoltar);
		}
		return pnlRodape;
	}

}
