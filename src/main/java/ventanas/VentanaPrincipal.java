package ventanas;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import entidades.concretas.Usuario;
import entidades.concretas.UsuarioSistema;
import entidades.concretas.Persona;
import entidades.concretas.SessionManager;
import entidades.enumerables.TipoPermiso;
import ventanas.paneles.*;

public class VentanaPrincipal extends JFrame {
    private Usuario usuario;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private UsuarioSistema usuarioSistema;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioSistema = SessionManager.getCurrentUser();

        setTitle("Sistema Bancario");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Sistema Bancario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        String usuarioText = usuario != null ? usuario.getNombre() : obtenerNombreDesdeSesion();
        String rolText = usuarioSistema != null && usuarioSistema.getRol() != null ? usuarioSistema.getRol().toString() : "";
        JLabel lblUsuario = new JLabel("Usuario: " + usuarioText + "   Rol: " + rolText);
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            SessionManager.setCurrentUser(null);
            dispose();
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightHeader.add(lblUsuario);
        rightHeader.add(btnCerrarSesion);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        PanelInicio panelInicio = new PanelInicio(usuario);
        PanelCuentas panelCuentas = new PanelCuentas(usuario);
        PanelTarjetas panelTarjetas = new PanelTarjetas(usuario);
        PanelClientes panelClientes = new PanelClientes();
        PanelAdministrador panelAdministrador = new PanelAdministrador();
        PanelEmpleados panelEmpleados = new PanelEmpleados();
        PanelUsuarios panelUsuarios = new PanelUsuarios();
        PanelCajeros panelCajeros = new PanelCajeros();

        contentPanel.add(panelInicio, "inicio");
        contentPanel.add(panelCuentas, "cuentas");
        contentPanel.add(panelTarjetas, "tarjetas");
        contentPanel.add(panelClientes, "clientes");
        contentPanel.add(panelAdministrador, "administradores");
        contentPanel.add(panelEmpleados, "empleados");
        contentPanel.add(panelUsuarios, "usuarios");
        contentPanel.add(panelCajeros, "cajeros");

        JButton btnInicio = new JButton("Inicio");
        sidebar.add(btnInicio);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        btnInicio.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));

        if (tienePermiso(TipoPermiso.CUEN)) {
            JButton btnCuentas = new JButton("Cuentas");
            sidebar.add(btnCuentas);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnCuentas.addActionListener(e -> cardLayout.show(contentPanel, "cuentas"));
        }

        if (tienePermiso(TipoPermiso.TARJ)) {
            JButton btnTarjetas = new JButton("Tarjetas");
            sidebar.add(btnTarjetas);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnTarjetas.addActionListener(e -> cardLayout.show(contentPanel, "tarjetas"));
        }

        if (tienePermiso(TipoPermiso.CLIE)) {
            JButton btnClientes = new JButton("Clientes");
            sidebar.add(btnClientes);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnClientes.addActionListener(e -> cardLayout.show(contentPanel, "clientes"));
        }

        if (tienePermiso(TipoPermiso.ADMI)) {
            JButton btnAdministradores = new JButton("Administradores");
            sidebar.add(btnAdministradores);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnAdministradores.addActionListener(e -> cardLayout.show(contentPanel, "administradores"));
        }

        if (tienePermiso(TipoPermiso.EMPL)) {
            JButton btnEmpleados = new JButton("Empleados");
            sidebar.add(btnEmpleados);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnEmpleados.addActionListener(e -> cardLayout.show(contentPanel, "empleados"));
        }

        if (tienePermiso(TipoPermiso.USUA)) {
            JButton btnUsuarios = new JButton("Usuarios");
            sidebar.add(btnUsuarios);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnUsuarios.addActionListener(e -> cardLayout.show(contentPanel, "usuarios"));
        }

        if (tienePermiso(TipoPermiso.CAJE)) {
            JButton btnCajeros = new JButton("Cajeros");
            sidebar.add(btnCajeros);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            btnCajeros.addActionListener(e -> cardLayout.show(contentPanel, "cajeros"));
        }

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "inicio");
    }

    private boolean tienePermiso(TipoPermiso permiso) {
        if (usuarioSistema == null || usuarioSistema.getPermisos() == null) return false;
        List<TipoPermiso> permisos = usuarioSistema.getPermisos();
        return permisos.contains(permiso);
    }

    private String obtenerNombreDesdeSesion() {
        if (usuarioSistema == null) return "";
        Persona p = usuarioSistema.getPersona();
        if (p == null) return "";
        return p.getNombre();
    }
}
