package Vista;

import controlador.MedicoController;
import controlador.PacienteController;
import controlador.TurnoController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
// Clase que representa el menú principal del sistema.
// Desde esta ventana el usuario puede acceder a la gestión
// de pacientes, médicos y turnos.
// clase fundamental de la librería Swing que permite crear y gestionar ventanas de escritorio para aplicaciones con interfaz gráfica

public class MenuPrincipal extends JFrame {
    // Usuario que inició sesión en el sistema.
    private Usuario usuario;
    // Controladores utilizados para comunicarse con la lógica del sistema.
    private PacienteController pacienteController;
    private MedicoController medicoController;
    private TurnoController turnoController;
    // Constructor del menú principal.
    public MenuPrincipal(Usuario usuario, PacienteController pacienteController, MedicoController medicoController, TurnoController turnoController) {
        this.usuario = usuario;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;
        this.turnoController = turnoController;
        // Configura la ventana e inicializa todos los componentes.
        configurarVentana();
        crearComponentes();
    }
    // Configura las propiedades principales de la ventana.
    private void configurarVentana() {
        // Establece el título de la ventana.
        setTitle("Sistema de Turnos Médicos");
        //tamaño
        setSize(900, 600);
        //centrado
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        // PANEL IZQUIERDO
        // Panel donde se muestra la información del sistema y del usuario.
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setPreferredSize(new Dimension(250, 600));
        panelIzquierdo.setBackground(new Color(0, 120, 215));
        panelIzquierdo.setLayout(null);
        // Título
        JLabel lblSistema = new JLabel("Sistema Médico");

        lblSistema.setForeground(Color.WHITE);
        lblSistema.setBounds(40, 80, 200, 30);
        panelIzquierdo.add(lblSistema);
        // Muestra el nombre del usuario que inició sesión.
        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombreUsuario());

        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(40, 130, 200, 30);
        panelIzquierdo.add(lblUsuario);
         // Muestra el rol
        JLabel lblRol = new JLabel("Rol: " + usuario.getRol());

        lblRol.setForeground(Color.WHITE);
        lblRol.setBounds(40, 170, 200, 30);
        panelIzquierdo.add(lblRol);
        add(panelIzquierdo, BorderLayout.WEST);

        // PANEL CENTRAL
        // Panel que contiene los botones principales del sistema.
        JPanel panelCentral = new JPanel();

        panelCentral.setLayout(null);

        // Botón para abrir la gestión de pacientes.
        JButton btnPacientes = new JButton("Gestionar Pacientes");
        btnPacientes.setBounds(220, 100, 250, 40);
        panelCentral.add(btnPacientes);

        // Botón para abrir la gestión de medicos.
        JButton btnMedicos = new JButton("Gestionar Médicos");
        btnMedicos.setBounds(220, 170, 250, 40);
        panelCentral.add(btnMedicos);

        // Botón para abrir la gestión de turnos
        JButton btnTurnos = new JButton("Gestionar Turnos");
        btnTurnos.setBounds(220, 240, 250, 40);
        panelCentral.add(btnTurnos);
        // Botón para abrir la gestión de salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(220, 330, 250, 40);
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);

        // EVENTOS
        // Abre la ventana pacientes
        btnPacientes.addActionListener(e -> {
                VistaPaciente vistaPaciente = new VistaPaciente(pacienteController);
                vistaPaciente.setVisible(true);
        });

        // Abre la ventana medicos
        btnMedicos.addActionListener(e -> {VistaMedico vistaMedico = new VistaMedico(medicoController);
            vistaMedico.setVisible(true);
        });


         // Abre la ventana turnos
        btnTurnos.addActionListener(e -> {VistaTurno vistaTurno = new VistaTurno(turnoController, pacienteController, medicoController);
            vistaTurno.setVisible(true);
        });

        // salir
        btnSalir.addActionListener(e -> {dispose();});
    }
}