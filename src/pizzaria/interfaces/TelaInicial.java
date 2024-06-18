package pizzaria.interfaces;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import pizzaria.infra.Banco;
import pizzaria.interfaces.listagens.TelaListagemClientes;
import pizzaria.interfaces.listagens.TelaListagemPedidos;
import pizzaria.interfaces.listagens.TelaListagemSaboresPizza;
import pizzaria.interfaces.listagens.TelaPedidos;

public class TelaInicial extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnListagemClientes;
	private JButton btnListagemSabores;
	private JButton btnPedidos;
	private JButton btnTodosPedidos;

	private Banco banco;

	public TelaInicial() {
		setBanco(new Banco());

		// Configurações básicas da janela
		setTitle("Tela Inicial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null); // Centraliza a janela na tela
		setLayout(new GridLayout(2, 1)); // Layout da janela

		// Inicializa os botões
		btnListagemClientes = new JButton("Listagem de Clientes");
		btnListagemSabores = new JButton("Listagem de Sabores");
		btnPedidos = new JButton("Pedidos por Cliente");
		btnTodosPedidos = new JButton("Listagem todos os Pedidos");

		// Adiciona os botões à janela
		add(btnListagemClientes);
		add(btnListagemSabores);
		add(btnPedidos);
		add(btnTodosPedidos);

		btnListagemClientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaListagemClientes();
			}
		});

		btnListagemSabores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaListagemSabores();
			}
		});

		btnPedidos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaPedidos();
			}
		});

		btnTodosPedidos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaListagemPedidos();
			}
		});
	}

	public void voltarParaTelaInicial() {
		this.setVisible(true);
	}

	private void abrirTelaListagemClientes() {
		TelaListagemClientes telaListagem = new TelaListagemClientes(this);
		telaListagem.setVisible(true);
	}

	private void abrirTelaListagemSabores() {
		TelaListagemSaboresPizza telaListagem = new TelaListagemSaboresPizza(this);
		telaListagem.setVisible(true);
	}

	private void abrirTelaPedidos() {
		TelaPedidos telaPedidos = new TelaPedidos(this, null);
		telaPedidos.setVisible(true);
	}

	private void abrirTelaListagemPedidos() {
		TelaListagemPedidos telaListagemPedidos = new TelaListagemPedidos(this);
		telaListagemPedidos.setVisible(true);
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
}
