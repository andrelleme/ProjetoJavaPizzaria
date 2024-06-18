package pizzaria.interfaces.listagens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import pizzaria.entidades.Cliente;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.cadastros.TelaCadastroCliente;

public class TelaListagemClientes extends Tela {

	private static final long serialVersionUID = -834369563776797984L;

	private JTable tabelaClientes;
	private JButton btnVoltar;
	private JPanel pnlRodape;
	private JTextField txtFiltro;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnCadastrarCliente;

	public TelaListagemClientes(TelaInicial telaInicial) {
		super(telaInicial);
		inicializarTela();

		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				getTelaInicial().voltarParaTelaInicial();
			}
		});

		btnCadastrarCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});

		btnAlterar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaClientes.getSelectedRow();
				if (selectedRow != -1) {
					// Obtém os dados do cliente selecionado na tabela
					Long id = (Long) tabelaClientes.getValueAt(selectedRow, 0);
					String nome = (String) tabelaClientes.getValueAt(selectedRow, 1);
					String sobrenome = (String) tabelaClientes.getValueAt(selectedRow, 2);
					Long telefone = (Long) tabelaClientes.getValueAt(selectedRow, 3);

					// Exibe uma mensagem com os dados do cliente selecionado
					JOptionPane.showMessageDialog(null, "Cliente selecionado:\n\nNome: " + nome + "\nSobrenome: "
							+ sobrenome + "\nTelefone: " + telefone);
					var telaCadastro = new TelaCadastroCliente(telaInicial, new Cliente(id, nome, sobrenome, telefone));
					setVisible(false);
					telaCadastro.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente na lista!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabelaClientes.getSelectedRow();
				if (selectedRow != -1) {
					// Obtém os dados do cliente selecionado na tabela
					Long id = (Long) tabelaClientes.getValueAt(selectedRow, 0);
					String nome = (String) tabelaClientes.getValueAt(selectedRow, 1);
					String sobrenome = (String) tabelaClientes.getValueAt(selectedRow, 2);
					Long telefone = (Long) tabelaClientes.getValueAt(selectedRow, 3);

					// Exibe uma mensagem com os dados do cliente selecionado
					JOptionPane.showMessageDialog(null, "Deseja realmente excluír o Cliente selecionado?\n\nNome: " + nome + "\nSobrenome: "
							+ sobrenome + "\nTelefone: " + telefone);
					
					getBanco().excluirCliente(id);
					DefaultTableModel model = (DefaultTableModel) tabelaClientes.getModel();
		            model.removeRow(selectedRow);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente na lista!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

	private void inicializarTela() {
		setTitle("Listagem de Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		var clientes = getTelaInicial().getBanco().getClientes();

		txtFiltro = new JTextField(20);

		txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarTabela();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarTabela();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarTabela();
			}
		});

		var jpanel = new JPanel();
		jpanel.add(new JLabel("Filtro:"));
		jpanel.add(txtFiltro);
		this.btnCadastrarCliente = new JButton("Cadastrar");
		jpanel.add(btnCadastrarCliente);
		this.btnAlterar = new JButton("Alterar");
		jpanel.add(btnAlterar);
		this.btnExcluir = new JButton("Excluir");
		jpanel.add(btnExcluir);

		this.getContentPane().add(jpanel, BorderLayout.PAGE_START);
		gerarTabela(clientes);

		this.getContentPane().add(getPnlRodape(), BorderLayout.PAGE_END);
	}

	private void gerarTabela(List<Cliente> clientes) {
		DefaultTableModel tableModel = new DefaultTableModel();
		if (!clientes.isEmpty()) {
			tableModel.addColumn("ID");
			tableModel.addColumn("Nome");
			tableModel.addColumn("Sobrenome");
			tableModel.addColumn("Telefone");

			clientes.forEach(cliente -> {
				Object[] rowData = { cliente.getId(), cliente.getNome(), cliente.getSobreNome(),
						cliente.getTelefone() };
				tableModel.addRow(rowData);
			});
		} else {
			Object[] rowData = { "Nenhum cliente cadastrado." };
			tableModel.addColumn("Mensagem");
			tableModel.addRow(rowData);
		}

		tabelaClientes = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(tabelaClientes);
		add(scrollPane, BorderLayout.CENTER);
	}

	protected JPanel getPnlRodape() {
		if (pnlRodape == null) {
			pnlRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
			btnVoltar = new JButton("Voltar");
			pnlRodape.add(btnVoltar);
		}
		return pnlRodape;
	}

	private void filtrarTabela() {
		DefaultTableModel tableModel = (DefaultTableModel) tabelaClientes.getModel();
		TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
		tabelaClientes.setRowSorter(rowSorter);

		String filtro = txtFiltro.getText().trim();
		if (filtro.length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + filtro);
			rowSorter.setRowFilter(rf);
		}
	}

	private void abrirTelaCadastro() {
		TelaCadastroCliente telaCadastro = new TelaCadastroCliente(getTelaInicial(), null);
		telaCadastro.setVisible(true);
		setVisible(false);
	}

}