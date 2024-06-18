package pizzaria.interfaces.cadastros;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pizzaria.entidades.Sabor;
import pizzaria.entidades.TipoSabor;
import pizzaria.infra.Utils;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.listagens.TelaListagemSaboresPizza;

public class TelaCadastrarSabor extends Tela {

	private static final long serialVersionUID = 274937596941525779L;

	private JPanel pnlForm;

	private JTextField txtNome;
	private JComboBox<String> comboTipo;

	public TelaCadastrarSabor(TelaInicial telaInicial) {
		super(telaInicial);
		inicializarTela();
	}

	
	@Override
	public void cadastrar() {
		String nome = txtNome.getText();
		String tipo = (String) comboTipo.getSelectedItem();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo Nome não pode ser vazio!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (tipo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo tipo deve ser selecionado!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtém os valores dos campos
		cadastrarSabor(nome, tipo);

		// Limpa os campos após o cadastro
		txtNome.setText("");
		comboTipo.setSelectedIndex(0);
	}
	
	private void inicializarTela() {
		var titulo = "Cadastro de Sabor de Pizza";
		inicializarCabecalho(titulo);
		setSize(600, 400);
		setLocationRelativeTo(null);
		
		this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
		
		TelaCadastroRodape<TelaCadastrarSabor> telaRodape = new TelaCadastroRodape<TelaCadastrarSabor>(this);
		telaRodape.inicializarRodape();
		this.pack();
	}

	private void cadastrarSabor(String nome, String tipo) {
		
		var banco = getTelaInicial().getBanco();
		var sabores = banco.getSabores();
		var tipoSabor = banco.getTipoSabores().stream().filter(t -> t.getNome().equals(tipo)).findFirst().get();
		var sabor = new Sabor(nome, tipoSabor);
		sabores.add(sabor);
		banco.setSabores(sabores);
		
		JOptionPane.showMessageDialog(null, "Sabor cadastrado:\n\nNome: " + sabor.getNome() + "\nTipo Sabor: "
				+ sabor.getTipoSabor().getNome());
		
		TelaListagemSaboresPizza telaListagemSaboresPizza = new TelaListagemSaboresPizza(getTelaInicial());
		telaListagemSaboresPizza.setVisible(true);
		setVisible(false);
	}

	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel();
			pnlForm.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			// campos
			JLabel lblNome = new JLabel("Nome:");
			JLabel lblTipo = new JLabel("Tipo:");
			txtNome = new JTextField(20);
			comboTipo = new JComboBox<>(getTipoSabores());

			pnlForm.add(lblNome);
			pnlForm.add(txtNome);
			pnlForm.add(lblTipo);
			pnlForm.add(comboTipo);

		}
		return pnlForm;
	}

	private String[] getTipoSabores() {
		var tipoSabores = getTelaInicial().getBanco().getTipoSabores();
		var arraySabores = tipoSabores.stream()
				.map(TipoSabor::getNome)
				.toArray(String[]::new);
		return Utils.unificarArrays(new String[] {""}, arraySabores);
	}
	
	public void voltar() {
		TelaListagemSaboresPizza telaListagemSaboresPizza = new TelaListagemSaboresPizza(getTelaInicial());
		telaListagemSaboresPizza.setVisible(true);
		setVisible(false);
	}
}
