package view;

import model.Produto;
import controller.VendaController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaPDV extends JFrame {

    private ArrayList<Produto> produtos;

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtPreco;
    private JTextField txtEstoque;

    private JTextField txtCodVenda;
    private JTextField txtQuantidade;

    private JTextField txtValorPago;

    private JTextArea areaVenda;

    private JLabel lblTotal;
    private JLabel lblTroco;

    private VendaController controller;

    public TelaPDV() {

        produtos = new ArrayList<>();
        controller = new VendaController();

        setTitle("Sistema PDV");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(
                new GridLayout(10,4,6,6)
        );



        painel.add(new JLabel("Código"));
        txtCodigo = new JTextField();
        painel.add(txtCodigo);

        painel.add(new JLabel("Nome"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Preço"));
        txtPreco = new JTextField();
        painel.add(txtPreco);

        painel.add(new JLabel("Estoque"));
        txtEstoque = new JTextField();
        painel.add(txtEstoque);

        JButton btnCadastrar =
                new JButton("Cadastrar Produto");

        JButton btnAdicionar =
                new JButton("Adicionar Item");

        painel.add(btnCadastrar);
        painel.add(new JLabel(""));

        painel.add(new JLabel("Código Produto"));
        txtCodVenda = new JTextField();
        painel.add(txtCodVenda);

        painel.add(new JLabel("Quantidade"));
        txtQuantidade = new JTextField();
        painel.add(txtQuantidade);

        painel.add(btnAdicionar);
        painel.add(new JLabel(""));

        JButton btnNova =
                new JButton("Nova Venda");

        JButton btnFinalizar =
                new JButton("Finalizar Venda");


        painel.add(new JLabel("Valor Pago"));
        txtValorPago = new JTextField();
        painel.add(txtValorPago);

        JPanel painelVenda = new JPanel();

        painelVenda.add(btnNova);
        painelVenda.add(btnFinalizar);

        painel.add(painelVenda);
        painel.add(new JLabel(""));

        areaVenda = new JTextArea();
        areaVenda.setEditable(false);

        areaVenda.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scroll = new JScrollPane(areaVenda);

        JPanel painelInfo = new JPanel(
                new GridLayout(2,1)
        );

        lblTotal = new JLabel(
                "Total: R$ 0.00",
                SwingConstants.CENTER
        );

        lblTroco = new JLabel(
                "Troco: R$ 0.00",
                SwingConstants.CENTER
        );

        painelInfo.add(lblTotal);
        painelInfo.add(lblTroco);

        add(painel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelInfo, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(
                e -> cadastrarProduto()
        );

        btnAdicionar.addActionListener(
                e -> adicionarItem()
        );

        btnNova.addActionListener(
                e -> novaVenda()
        );

        btnFinalizar.addActionListener(
                e -> finalizarVenda()
        );
    }

    private void cadastrarProduto() {

        try {

            int codigo =
                    Integer.parseInt(
                            txtCodigo.getText());

            String nome =
                    txtNome.getText();

            double preco =
                    Double.parseDouble(
                            txtPreco.getText());

            int estoque =
                    Integer.parseInt(
                            txtEstoque.getText());

            produtos.add(
                    new Produto(
                            codigo,
                            nome,
                            preco,
                            estoque
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Produto cadastrado!"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dados inválidos."
            );
        }
    }

    private Produto buscarProduto(int codigo) {

        for (Produto p : produtos) {

            if (p.getCodigo() == codigo) {
                return p;
            }
        }

        return null;
    }

    private void adicionarItem() {

        try {

            int codigo =
                    Integer.parseInt(
                            txtCodVenda.getText());

            int qtd =
                    Integer.parseInt(
                            txtQuantidade.getText());

            Produto p =
                    buscarProduto(codigo);

            if (p == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado."
                );

                return;
            }

            if (qtd > p.getEstoque()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Estoque insuficiente."
                );

                return;
            }

            controller.adicionarItem(p, qtd);

            areaVenda.append(
                    p.getNome() +
                            " x " + qtd +
                            " = R$ " +
                            String.format("%.2f",
                                    p.getPreco() * qtd)
                            + "\n"
            );

            lblTotal.setText(
                    "Total: R$ " +
                            String.format("%.2f",
                                    controller.calcularTotal())
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dados inválidos."
            );
        }
    }

    private void novaVenda() {

        controller.novaVenda();

        areaVenda.setText("");

        lblTotal.setText(
                "Total: R$ 0.00"
        );

        lblTroco.setText(
                "Troco: R$ 0.00"
        );
    }

    private void finalizarVenda() {

        try {

            double valorPago =
                    Double.parseDouble(
                            txtValorPago.getText());

            double troco =
                    controller.finalizarVenda(valorPago);

            lblTroco.setText(
                    "Troco: R$ " +
                            String.format("%.2f",
                                    troco)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Venda finalizada!"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Valor inválido."
            );
        }
    }
}