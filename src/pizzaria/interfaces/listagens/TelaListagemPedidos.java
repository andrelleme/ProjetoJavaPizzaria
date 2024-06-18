package pizzaria.interfaces.listagens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pizzaria.entidades.Pedido;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.cadastros.TelaAlterarEstado;

public class TelaListagemPedidos extends Tela {

	private static final long serialVersionUID = 192962489409979320L;

	private JPanel pnlForm;
	private JButton btnAlterar;
	private JTable tabelaPedidos;

	public TelaListagemPedidos(TelaInicial telaInicial) {
		super(telaInicial);
		inicializarTela();

		btnAlterar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarPedido();
			}
		});
	}

	private void inicializarTela() {
		inicializarCabecalho("Todos os Pedidos");
		setSize(600, 400);
		setLocationRelativeTo(null);
		this.getContentPane().add(getPnlForm(), BorderLayout.PAGE_START);
		TelaListagemRodape<TelaListagemPedidos> telaRodape = new TelaListagemRodape<TelaListagemPedidos>(this);
		telaRodape.inicializarRodape();

	}

	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel();
			btnAlterar = new JButton("Alterar Estado");
			pnlForm.add(btnAlterar);

			getPedidos();
		}
		return pnlForm;
	}

	private void getPedidos() {
		var pedidos = getBanco().getPedidos();
		DefaultTableModel tableModel = new DefaultTableModel();
		if (!pedidos.isEmpty()) {
			tableModel.addColumn("ID");
			tableModel.addColumn("Cliente");
			tableModel.addColumn("Qtd Pizzas");
			tableModel.addColumn("Estado");
			tableModel.addColumn("Valor Total");

			pedidos.forEach(pedido -> {
				Object[] rowData = { pedido.getId(), pedido.getCliente().getNome(), pedido.getPizzas().size(),
						pedido.getEstado(), pedido.getPrecoTotal() };
				tableModel.addRow(rowData);

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

	private void alterarPedido() {
		var selectPedido = tabelaPedidos.getSelectedRow();
		if (selectPedido != -1) {
			// Obtém os dados do cliente selecionado na tabela
			Long id = (Long) tabelaPedidos.getValueAt(selectPedido, 0);
			Pedido pedido = getPedidoPeloId(id);

			// Exibe uma mensagem com os dados do cliente selecionado
			JOptionPane.showMessageDialog(null, "Pedido selecionado:\n\nID: " + pedido.getId() + "\nCliente: "
					+ pedido.getCliente().getNome() + "\nTelefone: " + pedido.getCliente().getTelefoneString());
			var telaCadastro = new TelaAlterarEstado(getTelaInicial(), pedido);
			setVisible(false);
			telaCadastro.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione um pedido na lista!", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	private Pedido getPedidoPeloId(Long id) {
		return getBanco().getPedidoPeloId(id);
	}

}
