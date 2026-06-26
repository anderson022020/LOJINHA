package view;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaLogin() {

        setTitle("Login do Operador");
        setSize(300,180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(3,2,5,5));

        painel.add(new JLabel("Usuário"));
        txtUsuario = new JTextField();
        painel.add(txtUsuario);

        painel.add(new JLabel("Senha"));
        txtSenha = new JPasswordField();
        painel.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnSair = new JButton("Sair");

        painel.add(btnEntrar);
        painel.add(btnSair);

        add(painel);

        btnEntrar.addActionListener(e -> login());

        btnSair.addActionListener(e -> System.exit(0));
    }

    private void login() {

        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        if(usuario.equals("adm") && senha.equals("4321")){

            new TelaPDV().setVisible(true);
            dispose();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário ou senha inválidos!"
            );

        }

    }

}