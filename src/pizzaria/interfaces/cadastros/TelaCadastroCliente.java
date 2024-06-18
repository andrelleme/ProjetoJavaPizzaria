package pizzaria.interfaces.cadastros;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pizzaria.entidades.Cliente;
import pizzaria.infra.Utils;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.listagens.TelaListagemClientes;

public class TelaCadastroCliente extends Tela {
	private static final long serialVersionUID = 1L;

	private JPanel pnlForm;

	private JLabel lblNome;
	private JLabel lblSobrenome;
	private JLabel lblTelefone;
	private JTextField txtNome;
	private JTextField txtSobrenome;
	private JTextField txtTelefone;
	private Cliente cliente;

	public TelaCadastroCliente(TelaInicial telaInicial, Cliente cliente) {
		super(telaInicial);
		this.setCliente(cliente);
		inicializarTela();
	}

	public void inicializarTela() {
		var titulo = "";
		if (this.cliente == null) {
			titulo = "Cadastro de Cliente";
		} else {
			titulo = "Alterar Cliente";
		}
		inicializarCabecalho(titulo);
		setSize(600, 400);
		setLocationRelativeTo(null);

		this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);
		var telaRodape = new TelaCadastroRodape<TelaCadastroCliente>(this);
		telaRodape.inicializarRodape();
		this.pack();
	}

	private void cadastrarCliente(String nome, String sobrenome, String telefone) {
		var banco = getTelaInicial().getBanco();
		var clientes = banco.getClientes();

		if (this.cliente == null) {
			var cliente = new Cliente(Utils.gerarId(clientes.size()), nome, sobrenome, Long.valueOf(telefone));
			clientes.add(cliente);
			banco.setClientes(clientes);

			// Exibe uma mensagem com os dados do cliente cadastrado
			JOptionPane.showMessageDialog(null, "Cliente cadastrado:\n\nNome: " + cliente.getNome() + "\nSobrenome: "
					+ cliente.getSobreNome() + "\nTelefone: " + cliente.getTelefone());
			
		} else {
			var cliente = clientes.stream().filter(c -> c.getId().equals(this.cliente.getId())).findFirst()
					.orElse(new Cliente());
			cliente.setNome(nome);
			cliente.setSobreNome(sobrenome);
			cliente.setTelefone(Long.valueOf(telefone));

			JOptionPane.showMessageDialog(null, "Cliente alterado:\n\nNome: " + cliente.getNome() + "\nSobrenome: "
					+ cliente.getSobreNome() + "\nTelefone: " + cliente.getTelefone());
		}
		
		TelaListagemClientes telaListagemClientes = new TelaListagemClientes(getTelaInicial());
		telaListagemClientes.setVisible(true);
		setVisible(false);

	}

	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel(new GridBagLayout());
			pnlForm.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(5, 5, 5, 5);

			// Inicializa os componentes da tela
			lblNome = new JLabel("Nome:");
			lblSobrenome = new JLabel("Sobrenome:");
			lblTelefone = new JLabel("Telefone:");
			txtNome = new JTextField(20);
			txtSobrenome = new JTextField(20);
			txtTelefone = new JTextField(20);

			if (this.cliente != null) {
				txtNome.setText(this.cliente.getNome());
				txtSobrenome.setText(this.cliente.getSobreNome());
				txtTelefone.setText(this.cliente.getTelefone().toString());
			}

			// Adiciona os componentes ao painel usando o GridBagLayout
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlForm.add(lblNome, gbc);

			gbc.gridx = 1;
			pnlForm.add(txtNome, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			pnlForm.add(lblSobrenome, gbc);

			gbc.gridx = 1;
			pnlForm.add(txtSobrenome, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			pnlForm.add(lblTelefone, gbc);

			gbc.gridx = 1;
			pnlForm.add(txtTelefone, gbc);
		}
		return pnlForm;
	}

	@Override
	public void cadastrar() {
		String nome = txtNome.getText();
		String sobrenome = txtSobrenome.getText();
		String telefone = txtTelefone.getText();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo Nome não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (sobrenome.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo Sobrenome não pode ser vazio!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!telefone.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "O campo Telefone só aceita números inteiros!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Obtém os valores dos campos
		cadastrarCliente(nome, sobrenome, telefone);

		// Limpa os campos após o cadastro
		txtNome.setText("");
		txtSobrenome.setText("");
		txtTelefone.setText("");
	}
	
	public void voltar() {
		TelaListagemClientes telaListagemClientes = new TelaListagemClientes(getTelaInicial());
		telaListagemClientes.setVisible(true);
		setVisible(false);
	}

	@Override
	public void limparCampos() {
		txtNome.setText("");
		txtSobrenome.setText("");
		txtTelefone.setText("");
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
