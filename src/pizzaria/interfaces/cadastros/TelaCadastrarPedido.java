package pizzaria.interfaces.cadastros;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import pizzaria.entidades.Cliente;
import pizzaria.entidades.Forma;
import pizzaria.entidades.Pedido;
import pizzaria.entidades.Pizza;
import pizzaria.entidades.Sabor;
import pizzaria.infra.Utils;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.listagens.TelaPedidos;

public class TelaCadastrarPedido extends Tela implements ActionListener {

	private static final long serialVersionUID = 8491912666643339659L;
	private Cliente cliente;

	private List<JPanel> pnlPizzas;
	private JPanel pnlForm;
	private int pizzaCount;

	public TelaCadastrarPedido(TelaInicial telaInicial, Cliente cliente) {
		super(telaInicial);
		this.setCliente(cliente);
		inicializarTela();
	}

	private void inicializarTela() {
		var titulo = "Cadastro de Pedidos";
		inicializarCabecalho(titulo);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		var pnlCabecalho = new JPanel();
		pnlCabecalho.add(new JLabel("Nome cliente:"));
		pnlCabecalho.add(new JLabel(cliente.getNome()));
		pnlCabecalho.add(new JLabel("Telefone cliente:"));
		pnlCabecalho.add(new JLabel(cliente.getTelefoneString()));

		var pnlForm = getPnlForm();
		JScrollPane scrollPane = new JScrollPane(pnlForm);
		this.getContentPane().add(pnlCabecalho, BorderLayout.PAGE_START);
		this.getContentPane().add(scrollPane);

		TelaCadastroRodape<TelaCadastrarPedido> telaRodape = new TelaCadastroRodape<>(this);
		var pnlRodape = telaRodape.getPnlRodape();
		getContentPane().add(pnlRodape, BorderLayout.PAGE_END);

		this.pack();
	}

	private JPanel getPnlForm() {
		pnlPizzas = new ArrayList<JPanel>();
		pizzaCount = 0;

		pnlForm = new JPanel();
		pnlForm.setLayout(new GridBagLayout());

		JButton btnAdicionarPizza = new JButton("Adicionar Pizza");
		btnAdicionarPizza.addActionListener(this);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlForm.add(btnAdicionarPizza, gbc);

		JScrollPane scrollPane = new JScrollPane(pnlForm);
		add(scrollPane, BorderLayout.CENTER);

		return pnlForm;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Adicionar Pizza")) {
			adicionarPizzaForm();
		} else if (e.getActionCommand().equals("Salvar")) {
			cadastrar();
		}
	}

	private void adicionarPizzaForm() {
		pizzaCount++;

		JPanel pnlPizza = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);

		var sabores = getSabores();

		// Componentes do formulário da pizza
		JLabel lblTitulo = new JLabel("Pizza " + pizzaCount);
		JLabel lblSabores = new JLabel("Sabores:");
		JComboBox<String> saboresPrincipal = new JComboBox<String>(sabores);
		JCheckBox chkDoisSabores = new JCheckBox("Dois sabores?");
		JLabel lblSaborSecundario = new JLabel("Segundo sabor:");
		JComboBox<String> saboresSecundarios = new JComboBox<String>(sabores);
		JLabel lblForma = new JLabel("Forma:");
		JComboBox<String> comboForma = new JComboBox<>(getFormas());
		JLabel lblLado = new JLabel("Lado (cm):");
		JLabel lblRaio = new JLabel("Raio (cm):");
		JTextField txtLado = new JTextField(15);
		JTextField txtRaio = new JTextField(15);
		JLabel lblTamanho = new JLabel("Tamanho (cm²):");
		JTextField txtTamanho = new JTextField(15);
		lblSaborSecundario.setEnabled(chkDoisSabores.isSelected());
		saboresSecundarios.setEnabled(chkDoisSabores.isSelected());

		lblLado.setVisible(false);
		txtLado.setVisible(false);
		lblRaio.setVisible(false);
		txtRaio.setVisible(false);

		comboForma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var forma = (String) comboForma.getSelectedItem();
				if (forma.equals("Quadrado") || forma.equals("Triangulo")) {
					lblLado.setVisible(true);
					txtLado.setVisible(true);
					lblRaio.setVisible(false);
					txtRaio.setVisible(false);
				} else if (forma.equals("Circulo")) {
					lblRaio.setVisible(true);
					txtRaio.setVisible(true);
					lblLado.setVisible(false);
					txtLado.setVisible(false);
				}

			}
		});

		txtTamanho.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				var formaNome = (String) comboForma.getSelectedItem();
				var tamanho = txtTamanho.getText();

				if (!tamanho.matches(Utils.PATTERN_NUMERICO)) {
					JOptionPane.showMessageDialog(null, "O campo tamanho só aceita números inteiros!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				var bigTamanho = new BigDecimal(tamanho);
				Forma forma = getFormaPeloNome(formaNome);
				var valorTamanho = forma.calcularTamanhoPelaArea(bigTamanho);

				if (!validarValor(bigTamanho)) {
					JOptionPane.showMessageDialog(null, "O campo tamanho (cm²) tem que ser entre 100cm² e 1600cm²!",
							"Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (formaNome.equals("Quadrado") || formaNome.equals("Triangulo")) {
					txtLado.setText(valorTamanho.toString());
				} else if (formaNome.equals("Circulo")) {
					txtRaio.setText(valorTamanho.toString());
				}

			}
		});

		txtLado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String lado = txtLado.getText();
				if (!lado.matches(Utils.PATTERN_NUMERICO)) {
					JOptionPane.showMessageDialog(null, "O campo lado só aceita números inteiros!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				getTamanhoAreaCalculado(lblTitulo.getText(), comboForma, txtTamanho, lado);
			}
		});

		txtRaio.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String raio = txtRaio.getText();
				if (!raio.matches(Utils.PATTERN_NUMERICO)) {
					JOptionPane.showMessageDialog(null, "O campo raio só aceita números inteiros!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				getTamanhoAreaCalculado(lblTitulo.getText(), comboForma, txtTamanho, raio);

			}
		});

		// Ação para habilitar/desabilitar o campo do segundo sabor
		chkDoisSabores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSaborSecundario.setEnabled(chkDoisSabores.isSelected());
				saboresSecundarios.setEnabled(chkDoisSabores.isSelected());
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlPizza.add(lblTitulo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		pnlPizza.add(lblSabores, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlPizza.add(chkDoisSabores, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		pnlPizza.add(lblForma, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		pnlPizza.add(lblLado, gbc);
		pnlPizza.add(lblRaio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		pnlPizza.add(lblTamanho, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		pnlPizza.add(saboresPrincipal, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		pnlPizza.add(lblSaborSecundario, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		pnlPizza.add(saboresSecundarios, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		pnlPizza.add(comboForma, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		pnlPizza.add(txtLado, gbc);
		pnlPizza.add(txtRaio, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		pnlPizza.add(txtTamanho, gbc);

		pnlPizzas.add(pnlPizza);
		pnlForm.add(pnlPizza);

		// Posiciona o painel da nova pizza abaixo do anterior
		gbc.gridx = 0;
		gbc.gridy = pnlPizzas.size();
		gbc.gridwidth = 2;
		pnlForm.add(pnlPizza, gbc);
		pnlForm.revalidate();

		pnlForm.revalidate();
		pnlForm.repaint();
	}

	public static boolean validarValor(BigDecimal valor) {
		BigDecimal valorMinimo = BigDecimal.valueOf(100);
		BigDecimal valorMaximo = BigDecimal.valueOf(1600);
		return valor.compareTo(valorMinimo) >= 0 && valor.compareTo(valorMaximo) <= 0;
	}

	private void getTamanhoAreaCalculado(String titulo, JComboBox<String> comboForma, JTextField txtTamanho,
			String tamanho) {
		Forma forma = getFormaPeloNome((String) comboForma.getSelectedItem());
		if (!forma.validarTamanho(new BigDecimal(tamanho))) {
			JOptionPane.showMessageDialog(null, titulo + ": o campo tamanho não está entre os valores: "
					+ forma.getTamanhoMinimo() + " e " + forma.getTamanhoMaximo(), "Erro", JOptionPane.ERROR_MESSAGE);
		} else {
			forma.setTamanho(new BigDecimal(tamanho));
			txtTamanho.setText(forma.calcularArea().toString());
		}

	}

	public void cadastrar() {
		List<Pizza> pizzas = new ArrayList<Pizza>();
		for (JPanel pnlPizza : pnlPizzas) {
			JLabel lblTitulo = (JLabel) pnlPizza.getComponent(0);
			JComboBox<String> saborPrincipalBox = (JComboBox<String>) pnlPizza.getComponent(7);
			JCheckBox chkDoisSabores = (JCheckBox) pnlPizza.getComponent(2);
			JComboBox<String> comboForma = (JComboBox<String>) pnlPizza.getComponent(10);
			JTextField txtTamanho = new JTextField();

			String titulo = lblTitulo.getText();
			String saborPrincipal = (String) saborPrincipalBox.getSelectedItem();
			String txtForma = (String) comboForma.getSelectedItem();

			if (txtForma.equals("Quadrado") || txtForma.equals("Triangulo")) {
				txtTamanho = (JTextField) pnlPizza.getComponent(11);
			} else if (txtForma.equals("Circulo")) {
				txtTamanho = (JTextField) pnlPizza.getComponent(12);
			}

			String tamanho = txtTamanho.getText();

			if (saborPrincipal.isEmpty()) {
				JOptionPane.showMessageDialog(null, titulo + ": o campo sabores não pode ser vazio!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			List<Sabor> sabores = new ArrayList<Sabor>();
			sabores.add(getSaborPeloNomeCompleto(saborPrincipal));
			if (chkDoisSabores.isSelected()) {
				JComboBox<String> saborSecundarioBox = (JComboBox<String>) pnlPizza.getComponent(9);
				String saborSecundario = (String) saborSecundarioBox.getSelectedItem();
				if (saborSecundario.isEmpty()) {
					JOptionPane.showMessageDialog(null, titulo + ": o campo sabores não pode ser vazio!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				sabores.add(getSaborPeloNomeCompleto(saborSecundario));
			}

			if (txtForma.isEmpty()) {
				JOptionPane.showMessageDialog(null, titulo + ": o campo forma não pode ser vazio!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!tamanho.matches(Utils.PATTERN_NUMERICO)) {
				JOptionPane.showMessageDialog(null, titulo + ": o campo tamanho só aceita números inteiros!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Forma forma = getFormaPeloNome(txtForma);
			if (!forma.validarTamanho(new BigDecimal(tamanho))) {
				JOptionPane
						.showMessageDialog(
								null, titulo + ": o campo tamanho não está entre os valores: "
										+ forma.getTamanhoMinimo() + " e " + forma.getTamanhoMaximo(),
								"Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			var pizza = new Pizza(sabores, forma, new BigDecimal(tamanho));
			pizzas.add(pizza);
		}

		var pedidos = getPedidos();
		Pedido pedido = new Pedido(Utils.gerarId(pedidos.size()), this.cliente, pizzas, "aberto");
		pedidos.add(pedido);
		getBanco().setPedidos(pedidos);

		JOptionPane.showMessageDialog(null, "Pedido cadastrado:\n\nID: " + pedido.getId() + "\nCliente: "
				+ cliente.getNome() + "\nTelefone: " + cliente.getTelefone());

		TelaPedidos telaPedidos = new TelaPedidos(getTelaInicial(), cliente);
		telaPedidos.setVisible(true);
		setVisible(false);

	}

	private List<Pedido> getPedidos() {
		return getBanco().getPedidos();
	}

	private Forma getFormaPeloNome(String nome) {
		return getBanco().getFormas().stream().filter(forma -> forma.getNome().equals(nome)).findFirst().get();
	}

	private Sabor getSaborPeloNomeCompleto(String nomeCompleto) {
		return getBanco().getSabores().stream().filter(sabor -> sabor.getNomeCompleto().equals(nomeCompleto))
				.findFirst().get();
	}

	private String[] getSabores() {
		var sabores = getBanco().getSabores();
		return Utils.unificarArrays(new String[] { "" },
				sabores.stream().map(Sabor::getNomeCompleto).toArray(String[]::new));
	}

	private String[] getFormas() {
		var formas = getBanco().getFormas();
		return Utils.unificarArrays(new String[] { "" }, formas.stream().map(Forma::getNome).toArray(String[]::new));
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void voltar() {
		TelaPedidos telaPedidos = new TelaPedidos(getTelaInicial(), this.cliente);
		telaPedidos.setVisible(true);
		setVisible(false);
	}

}
