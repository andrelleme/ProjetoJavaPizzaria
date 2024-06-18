package pizzaria.interfaces.listagens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pizzaria.entidades.Cliente;
import pizzaria.infra.Utils;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.cadastros.TelaCadastrarPedido;

public class TelaPedidos extends Tela {

	private JPanel pnlForm;
	private JComboBox<String> comboClientes;
	private JButton btnSelecionar;
	private JButton btnCadastrar;
	private JLabel lNomeCliente;
	private JTable tabelaPedidos;
	private Cliente cliente;

	public TelaPedidos(TelaInicial telaInicial, Cliente cliente) {
		super(telaInicial);
		this.cliente = cliente;
		inicializarTela();

		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getCliente() == null) {
					JOptionPane.showMessageDialog(null, "Selecione um cliente na lista!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else {
					TelaCadastrarPedido telaCadastrarPedido = new TelaCadastrarPedido(telaInicial, getCliente());
					telaCadastrarPedido.setVisible(true);
					setVisible(false);
				}
			}
		});

		btnSelecionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String clienteSelecionado = (String) comboClientes.getSelectedItem();
				if (!clienteSelecionado.isBlank()) {
					setCliente(getTelaInicial().getBanco().buscarClientePorTelefone(clienteSelecionado));
					exibirPedidos();
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente na lista!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	private static final long serialVersionUID = -5194948857256369131L;

	private void inicializarTela() {
		inicializarCabecalho("Pedidos");
		setSize(600, 400);
		setLocationRelativeTo(null);
		this.getContentPane().add(getPnlForm(), BorderLayout.PAGE_START);
		TelaListagemRodape<TelaPedidos> telaRodape = new TelaListagemRodape<TelaPedidos>(this);
		telaRodape.inicializarRodape();

	}

	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel();
			comboClientes = new JComboBox<>(getNomeClientes());
			btnSelecionar = new JButton("Selecionar");
			pnlForm.add(new JLabel("Cliente:"));
			pnlForm.add(comboClientes);
			pnlForm.add(btnSelecionar);

			lNomeCliente = new JLabel("");
			pnlForm.add(new JLabel("Nome cliente:"));
			lNomeCliente.setText("");
			pnlForm.add(lNomeCliente);
			btnCadastrar = new JButton("Cadastrar Novo Pedido");
			pnlForm.add(btnCadastrar);

			if (lNomeCliente.getText().equals("")) {
				DefaultTableModel tableModel = new DefaultTableModel();
				Object[] rowData = { "" };
				tableModel.addColumn("ID");
				tableModel.addColumn("Pizzas");
				tableModel.addColumn("Preço Total");
				tableModel.addRow(rowData);
				this.tabelaPedidos = new JTable(tableModel);
				JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
				add(scrollPane, BorderLayout.CENTER);
			}

			if (getCliente() != null) {
				comboClientes.setSelectedItem(getCliente().getTelefoneString());
				exibirPedidos();
			}
		}
		return pnlForm;
	}

	private String[] getNomeClientes() {
		var clientes = getTelaInicial().getBanco().getClientes();
		var arrayClientes = clientes.stream().map(Cliente::getTelefoneString).toArray(String[]::new);
		return Utils.unificarArrays(new String[] { "" }, arrayClientes);
	}

	private void exibirPedidos() {
		var cliente = getCliente();
		lNomeCliente.setText(cliente.getNome());
		if (cliente != null) {
			var pedidos = getBanco().getPedidosPeloCliente(cliente.getId());
			DefaultTableModel tableModel = new DefaultTableModel();
			if (!pedidos.isEmpty()) {
				tableModel.addColumn("ID");
				tableModel.addColumn("Pizza");
				tableModel.addColumn("Tipo pizza");
				tableModel.addColumn("Preço Total");

				pedidos.forEach(pedido -> {
					pedido.getPizzas().forEach(pizza -> {
						Object[] rowData = { pedido.getId(), pizza.getNomePizza(), pizza.getNomeTipoSabor(),
								pizza.getValor() };
						tableModel.addRow(rowData);
					});
				});
			} else {
				Object[] rowData = { "Nenhum pedido cadastrado." };
				tableModel.addColumn("Mensagem");
				tableModel.addRow(rowData);
			}

			this.tabelaPedidos = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
			add(scrollPane, BorderLayout.CENTER);
		}
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
