package pizzaria.interfaces.listagens;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.cadastros.TelaCadastrarSabor;

public class TelaListagemSaboresPizza extends Tela {
	private static final long serialVersionUID = -5588936182266089730L;
	private JTable tabelaSabores;
	private JButton btnVoltar;
	private JTextField txtFiltro;
	private JButton btnCadastrar;
	private JPanel pnlRodape;

	public TelaListagemSaboresPizza(TelaInicial telaInicial) {
		super(telaInicial);
		inicializarTela();

		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				voltarParaTelaInicial();
			}
		});

		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaCadastro();
			}
		});

	}

	private void inicializarTela() {
		setTitle("Listagem de Sabores de Pizza");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null); // Centraliza a janela na tela
		setLayout(new BorderLayout());

		DefaultTableModel tableModel = new DefaultTableModel();
		var sabores = getTelaInicial().getBanco().getSabores();
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
		this.btnCadastrar = new JButton("Cadastrar");
		jpanel.add(btnCadastrar);

		this.getContentPane().add(jpanel, BorderLayout.PAGE_START);

		if (!sabores.isEmpty()) {
			tableModel.addColumn("Nome");
			tableModel.addColumn("Tipo Sabor");
			tableModel.addColumn("Valor");

			sabores.forEach(sabor -> {
				Object[] rowData = { sabor.getNome(), sabor.getTipoSabor().getNome(), sabor.getTipoSabor().getValor() };
				tableModel.addRow(rowData);
			});
		} else {
			Object[] rowData = { "Nenhum sabor cadastrado." };
			tableModel.addColumn("Mensagem");
			tableModel.addRow(rowData);
		}

		tabelaSabores = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(tabelaSabores);
		add(scrollPane, BorderLayout.CENTER);

		this.getContentPane().add(getPnlRodape(), BorderLayout.PAGE_END);
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
		DefaultTableModel tableModel = (DefaultTableModel) tabelaSabores.getModel();
		TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
		tabelaSabores.setRowSorter(rowSorter);

		String filtro = txtFiltro.getText().trim();
		if (filtro.length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + filtro);
			rowSorter.setRowFilter(rf);
		}
	}

	private void abrirTelaCadastro() {
		TelaCadastrarSabor telaCadastrarSabor = new TelaCadastrarSabor(getTelaInicial());
		telaCadastrarSabor.setVisible(true);
		setVisible(false);
	}	
}