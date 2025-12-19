package forms;

import config.ConexionDB;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private int intentos = 0;

    public Login() {
        setTitle("Login Bancario");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2));

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnIngresar = new JButton("Ingresar");
        add(btnIngresar);

        btnIngresar.addActionListener(e -> validarCredenciales());
        setVisible(true);
    }

    private void validarCredenciales() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        try (Connection conn = ConexionDB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT nombre, saldo FROM clientes WHERE usuario=? AND password=? AND activo=1"
            );
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double saldo = rs.getDouble("saldo");
                Cliente cliente = new Cliente(usuario, nombre, saldo);
                new Banco(cliente);
                dispose();
            } else {
                intentos++;
                JOptionPane.showMessageDialog(this, "Credenciales inválidas");
                if (intentos >= 3) {
                    JOptionPane.showMessageDialog(this, "Acceso bloqueado");
                    System.exit(0);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
