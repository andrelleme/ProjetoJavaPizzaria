package pizzaria.interfaces.cadastros;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pizzaria.entidades.Pedido;
import pizzaria.infra.Utils;
import pizzaria.interfaces.Tela;
import pizzaria.interfaces.TelaInicial;
import pizzaria.interfaces.listagens.TelaListagemPedidos;

public class TelaAlterarEstado extends Tela {

	private static final long serialVersionUID = 659024923775480875L;
	
	private Pedido pedido;
	private JPanel pnlForm; 
	private JLabel lblEstado;
	private JComboBox<String> comboEstados;

	public TelaAlterarEstado(TelaInicial telaInicial, Pedido pedido) {
		super(telaInicial);
		this.pedido = pedido;
		inicializarTela();
	}
	
	private void inicializarTela() {
		var titulo = "Alterar Estado de Pedidos";
		inicializarCabecalho(titulo);
		setSize(600, 400);
		setLocationRelativeTo(null);
		var jpanel = new JPanel();
		jpanel.add(new JLabel("ID pedido:"));
		jpanel.add(new JLabel(pedido.getId().toString()));
		jpanel.add(new JLabel("Nome cliente:"));
		jpanel.add(new JLabel(pedido.getCliente().getNome()));
		jpanel.add(new JLabel("Telefone cliente:"));
		jpanel.add(new JLabel(pedido.getCliente().getTelefoneString()));

		this.getContentPane().add(jpanel, BorderLayout.PAGE_START);
		this.getContentPane().add(getPnlForm(), BorderLayout.CENTER);

		TelaCadastroRodape<TelaAlterarEstado> telaRodape = new TelaCadastroRodape<TelaAlterarEstado>(this);
		telaRodape.inicializarRodape();
		this.pack();
	}
	
	private JPanel getPnlForm() {
		if (pnlForm == null) {
			pnlForm = new JPanel(new GridBagLayout());
			pnlForm.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(5, 5, 5, 5);

			// Inicializa os componentes da tela
			lblEstado = new JLabel("Estado:");
			comboEstados = new JComboBox<String>(getEstadosComboBox());

			// Adiciona os componentes ao painel usando o GridBagLayout
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlForm.add(lblEstado, gbc);

			gbc.gridx = 1;
			pnlForm.add(comboEstados, gbc);

		}
		return pnlForm;
	}
	
	private String[] getEstadosComboBox() {
		return Utils.unificarArrays(new String[] {""}, getEstados());
	}
	
	private String[] getEstados() {
		return new String[] {"aberto", "a caminho", "entregue"};
	}
	
	private boolean validarEstados(String estadoAtual, String estadoAlterado) {
		var estados = List.of(getEstados());
		var indexAtual = estados.indexOf(estadoAtual);
		var indexAlterado = estados.indexOf(estadoAlterado);
		return indexAtual < indexAlterado;		
	}
	
	public void cadastrar() {
		var novoEstado = (String) comboEstados.getSelectedItem();
		
		if(novoEstado.isEmpty()) {			
			JOptionPane.showMessageDialog(null, "O campo estado não pode ser vazio!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!validarEstados(this.pedido.getEstado(),novoEstado)) {
			JOptionPane.showMessageDialog(null, "O campo estado não pode anterior ao atual!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		getBanco().getPedidos().stream().filter(pedido -> pedido.getId().equals(this.pedido.getId())).forEach(pedido -> pedido.setEstado(novoEstado));
		JOptionPane.showMessageDialog(null, "Estado do pedido alterado!");
		TelaListagemPedidos telaListagemPedidos = new TelaListagemPedidos(getTelaInicial());
		telaListagemPedidos.setVisible(true);
		setVisible(false);
	}

}
