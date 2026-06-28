package Vista;

import controlador.UsuarioController;
import controlador.TurnoController;
import controlador.PacienteController;
import controlador.MedicoController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

// Clase encargada de mostrar la ventana de inicio de sesión.
// Verifica las credenciales del usuario antes de permitir el acceso al sistema.
public class VistaLogin extends JFrame {
    // Componentes de la interfaz.
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    // Controlador utilizado para validar el inicio de sesión.
    private UsuarioController usuarioController;

    // Controladores
    PacienteController pacienteController = new PacienteController();
    MedicoController medicoController = new MedicoController();
    TurnoController turnoController = new TurnoController();

    // Constructor de la ventana de inicio de sesión.
    public VistaLogin(UsuarioController usuarioController) {

        this.usuarioController = usuarioController;
        configurarVentana();
        crearComponentes();
        setVisible(true);
    }
    // Configura las propiedades principales de la ventana.
    private void configurarVentana() {
        setTitle("Sistema de Turnos Médicos");
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
    }

    //componentes
    private void crearComponentes() {
        // Título principal.
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
        lblTitulo.setBounds(170, 20, 200, 30);
        add(lblTitulo);
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(80, 80, 100, 25);
        add(lblUsuario);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(180, 80, 180, 25);
        add(txtUsuario);
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(80, 130, 100, 25);
        add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 130, 180, 25);
        add(txtPassword);
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(180, 190, 120, 30);
        add(btnIngresar);
        btnIngresar.addActionListener(e -> iniciarSesion());
    }
    // Valida las credenciales ingresadas por el usuario.
    private void iniciarSesion() {
        // Obtiene el nombre de usuario y la contraseña ingresados.
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        Usuario usuarioLogueado = usuarioController.login(usuario, password);

        if (usuarioLogueado != null) {
           // Si el usuario existe, abre el menú principal.
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuarioLogueado.getNombreUsuario());
            dispose();
            MenuPrincipal menu = new MenuPrincipal(usuarioLogueado, pacienteController, medicoController, turnoController);
            menu.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }
}