package ventanas;

import entidades.concretas.Cajero;
import entidades.concretas.Cliente;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.LoginView;
import entidades.concretas.Tarjeta;
import entidades.concretas.Usuario;
import entidades.enumerables.TipoMoneda;
import sistema.SistemaBanco;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin extends JFrame {

    public VentanaLogin() {
        setTitle("Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);
        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Iniciar Sesion");
        JButton btnATM = new JButton("Ir a Cajero");
        JButton btnRegistrar = new JButton("Registrar usuario");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtUsuario, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtPassword, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnATM, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnRegistrar, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUsuario.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña");
                return;
            }
            try {
                sistema.SistemaLogin sistemaLogin = new sistema.SistemaLogin();
                Usuario u = sistemaLogin.login(user, pass);
                if (u == null) {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                VentanaPrincipal app = new VentanaPrincipal(u);
                app.setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnATM.addActionListener(e -> {
            try {
                List<Cajero> todos = SistemaBanco.getInstance().getGestorCajeros().listarTodos();
                List<Cajero> disponibles = new ArrayList<>();
                for (Cajero c : todos) {
                    if (c.getDisponible()) disponibles.add(c);
                }

                if (disponibles.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay cajeros disponibles en este momento.");
                    return;
                }

                String[] opcionesCajeros = new String[disponibles.size()];
                for (int i = 0; i < disponibles.size(); i++) {
                    Cajero c = disponibles.get(i);
                    opcionesCajeros[i] = c.getId() + " - Disponible";
                }

                int sel = JOptionPane.showOptionDialog(
                        this,
                        "Seleccione un cajero:",
                        "Elegir Cajero",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcionesCajeros,
                        opcionesCajeros[0]
                );

                if (sel < 0) return;

                Cajero cajeroSeleccionado = disponibles.get(sel);

                String[] acciones = { "Depositar", "Retirar", "Cancelar" };
                int accion = JOptionPane.showOptionDialog(
                        this,
                        "Seleccione operación en cajero " + cajeroSeleccionado.getId(),
                        "Operación Cajero",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        acciones,
                        acciones[0]
                );

                if (accion < 0 || accion == 2) return;

                String numeroTarjeta = JOptionPane.showInputDialog(this, "Ingrese número de tarjeta:");
                if (numeroTarjeta == null || numeroTarjeta.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Número de tarjeta inválido.");
                    return;
                }
                final String numeroTarjetaFinal = numeroTarjeta.trim();

                List<Cliente> clientes = SistemaBanco.getInstance().getGestorClientes().listarTodos();
                if (clientes == null) {
                    JOptionPane.showMessageDialog(this, "No hay clientes registrados.");
                    return;
                }
                
                Tarjeta tarjeta = clientes.stream()
                        .flatMap(c -> c.getGestorTarjetas().listarTodos(c.getDNI()).stream())
                        .filter(t -> t.getNumeroTarjeta().equals(numeroTarjetaFinal))
                        .findFirst()
                        .orElse(null);


                if (tarjeta == null) {
                    JOptionPane.showMessageDialog(this, "Tarjeta no encontrada.");
                    return;
                }

                CuentaBancaria cuenta = tarjeta.getCuentaMoneda(TipoMoneda.Soles);
                if (cuenta == null) {
                    JOptionPane.showMessageDialog(this, "La tarjeta no tiene cuenta afiliada en Soles.");
                    return;
                }

                String montoStr = JOptionPane.showInputDialog(this, "Ingrese monto (ej: 100.50):");
                if (montoStr == null || montoStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Monto inválido.");
                    return;
                }

                double monto;
                try {
                    monto = Double.parseDouble(montoStr.trim());
                    if (monto <= 0) {
                        JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Monto inválido.");
                    return;
                }

                double saldoAntes = cuenta.getSaldo();

                if (accion == 0) {
                    cajeroSeleccionado.depositarConTarjeta(tarjeta, monto, SistemaBanco.getInstance().getGestorMovimientos());
                } else if (accion == 1) {
                    cajeroSeleccionado.retirarConTarjeta(tarjeta, monto, SistemaBanco.getInstance().getGestorMovimientos());
                }

                double saldoDespues = cuenta.getSaldo();

                if (saldoDespues > saldoAntes) {
                    JOptionPane.showMessageDialog(this, "Operación exitosa. Saldo anterior: " + saldoAntes + " - Saldo actual: " + saldoDespues);
                } else if (saldoDespues < saldoAntes) {
                    JOptionPane.showMessageDialog(this, "Operación exitosa. Saldo anterior: " + saldoAntes + " - Saldo actual: " + saldoDespues);
                } else {
                    JOptionPane.showMessageDialog(this, "No se realizó ningún cambio. Revise consola o historial de movimientos.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al operar con el cajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegistrar.addActionListener(e -> {
            try {
                var banco = SistemaBanco.getInstance().getBanco();

                String dni = JOptionPane.showInputDialog(this, "Ingrese su DNI:");
                if (dni == null || dni.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un DNI.");
                    return;
                }
                dni = dni.trim();

                var cliente = banco.getGestorClientes().buscarCliente(dni);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No existe un cliente registrado con ese DNI.\nDebe acercarse a plataforma para registrarse como cliente."
                    );
                    return;
                }

                String username = JOptionPane.showInputDialog(this, "Ingrese su nuevo nombre de usuario:");
                if (username == null || username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre de usuario no puede estar vacío.");
                    return;
                }
                username = username.trim();

                if (banco.getGestorUsuarios().buscarPorUsuario(username) != null) {
                    JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso.");
                    return;
                }

                String password = JOptionPane.showInputDialog(this, "Ingrese su nueva contraseña:");
                if (password == null || password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La contraseña no puede estar vacía.");
                    return;
                }
                password = password.trim();

                banco.getGestorUsuarios().crearUsuario(dni, new LoginView(username, password));

                if (banco.getGestorUsuarios().buscarPorUsuario(username) != null) {
                    JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente. Ya puede iniciar sesión.");
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error: No se pudo registrar el usuario.\n" +
                                    "Verifique si el cliente ya tiene una cuenta activa o si los datos son correctos."
                    );
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error durante el registro: " + ex.getMessage());
            }
        });

        add(panel, BorderLayout.CENTER);
    }
}
