package Vista;

import controlador.MedicoController;
import controlador.PacienteController;
import controlador.TurnoController;

import dialoj.BuscarMedicoDialog;
import dialoj.BuscarPacienteDialog;
import modelo.Medico;
import modelo.Paciente;
import modelo.Turno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
// Clase encargada de gestionar la reserva, búsqueda y cancelación de turnos.
public class VistaTurno extends JFrame {
    // Controladores utilizados para acceder a la lógica del sistema.
    private TurnoController turnoController;
    private PacienteController pacienteController;
    private MedicoController medicoController;
    // Campos donde se muestran los datos del paciente seleccionado.
    private JTextField txtApellidoPaciente;
    private JTextField txtNombrePaciente;
    private JTextField txtIdPaciente;
    // Campos donde se muestran los datos del médico seleccionado.
    private JTextField txtNombreMedico;
    private JTextField txtApellidoMedico;
    private JTextField txtIdMedico;
    // Objetos que almacenan el paciente y el médico seleccionados.
    private Paciente pacienteSeleccionado;
    private Medico medicoSeleccionado;
    // Componentes de la interfaz.
    private JTextArea areaMedicos;
    private JButton btnBuscarPaciente;
    private JButton btnBuscarMedico;
    private JButton btnReservar;
    private JButton btnBuscarTurno;
    private JButton btnCancelar;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtBuscarTurno;
    // Tabla utilizada para mostrar los turnos registrados.
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;


    // Constructor de la ventana de gestión de turnos.
    public VistaTurno(TurnoController turnoController, PacienteController pacienteController, MedicoController medicoController) {
        // Guarda las referencias de los controladores.
        this.turnoController = turnoController;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;
        // Configura la ventana
        configurarVentana();
        crearComponentes();
        cargarTurnos();
    }
    // Configura las propiedades principales de la ventana.
    private void configurarVentana() {
        setTitle("Gestión de Turnos");
        setSize(900,750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245,245,245));
        setLayout(new BorderLayout(10,10));
    }

    // Crea todos los componentes gráficos de la ventana.
    private void crearComponentes() {
         // Fuentes utilizadas en la interfaz.
        Font fuente = new Font("Segoe UI", Font.PLAIN,14);
        Font titulo = new Font("Segoe UI",Font.BOLD,24);
         //PANEL PRINCIPAL//
        // Panel principal que contiene toda la interfaz.
        JPanel principal = new JPanel();
        principal.setBackground(new Color(245,245,245));
        principal.setLayout(new BorderLayout(15,15));

        JLabel lblTitulo = new JLabel("GESTIÓN DE TURNOS",SwingConstants.CENTER);
        lblTitulo.setFont(titulo);

        principal.add(lblTitulo,BorderLayout.NORTH);
        // Panel donde se ubican las secciones de paciente, médico y reserva.
        JPanel centro = new JPanel();
        centro.setBackground(new Color(245,245,245));
        centro.setLayout(new BoxLayout(centro,BoxLayout.Y_AXIS));

        // PACIENTE //
        // Panel destinado a seleccionar un paciente.
        JPanel paciente = new JPanel(new GridBagLayout());
        paciente.setBorder(BorderFactory.createTitledBorder("PACIENTE"));
        paciente.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets=new Insets(5,5,5,5);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;
        // Campo Apellido.
        paciente.add(new JLabel("Apellido"),c);

        c.gridy++;
        txtApellidoPaciente=new JTextField(20);
        txtApellidoPaciente.setEditable(false);
        txtApellidoPaciente.setFont(fuente);
        paciente.add(txtApellidoPaciente,c);
         // Botón para abrir la búsqueda de pacientes.
        c.gridy++;
        btnBuscarPaciente=new JButton("Buscar Paciente");
        paciente.add(btnBuscarPaciente,c);
        // Campo Nombre
        c.gridy++;
        paciente.add(new JLabel("Nombre"),c);

        c.gridy++;
        txtNombrePaciente=new JTextField();
        txtNombrePaciente.setEditable(false);
        txtNombrePaciente.setFont(fuente);
        paciente.add(txtNombrePaciente,c);
        // Campo ID.
        c.gridy++;
        paciente.add(new JLabel("ID"),c);

        c.gridy++;
        txtIdPaciente=new JTextField();
        txtIdPaciente.setEditable(false);
        txtIdPaciente.setFont(fuente);
        paciente.add(txtIdPaciente,c);

        centro.add(paciente);
        centro.add(Box.createVerticalStrut(15));

        //MEDICO//
        // Panel destinado a seleccionar un médico.
        JPanel medico = new JPanel(new GridBagLayout());
        medico.setBorder(BorderFactory.createTitledBorder("MÉDICO"));
        medico.setBackground(Color.WHITE);

        c = new GridBagConstraints();
        c.insets=new Insets(5,5,5,5);
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=0;

        medico.add(new JLabel("Apellido"),c);

        c.gridy++;
        txtApellidoMedico=new JTextField(20);
        txtApellidoMedico.setFont(fuente);
        medico.add(txtApellidoMedico,c);

        c.gridy++;
        btnBuscarMedico=new JButton("Buscar Médico");
        medico.add(btnBuscarMedico,c);

        c.gridy++;
        medico.add(new JLabel("Nombre"),c);

        c.gridy++;
        txtNombreMedico=new JTextField();
        txtNombreMedico.setEditable(false);
        txtNombreMedico.setFont(fuente);
        medico.add(txtNombreMedico,c);

        c.gridy++;
        medico.add(new JLabel("ID"),c);

        c.gridy++;
        txtIdMedico=new JTextField();
        txtIdMedico.setEditable(false);
        txtIdMedico.setFont(fuente);
        medico.add(txtIdMedico,c);

        centro.add(medico);
        centro.add(Box.createVerticalStrut(15));


        //RESERVA//
        // Panel donde se ingresan la fecha y la hora del turno.
        JPanel reserva = new JPanel(new GridBagLayout());
        reserva.setBackground(Color.WHITE);
        reserva.setBorder(BorderFactory.createTitledBorder("RESERVA"));

        c = new GridBagConstraints();
        c.insets=new Insets(5,5,5,5);
        c.gridx=0;
        c.gridy=0;
        reserva.add(new JLabel("Fecha"),c);
        c.gridx=1;
        reserva.add(new JLabel("Hora"),c);
        c.gridx=0;
        c.gridy=1;

        txtFecha=new JTextField(10);
        txtFecha.setFont(fuente);
        reserva.add(txtFecha,c);

        c.gridx=1;
        txtHora=new JTextField(10);
        txtHora.setFont(fuente);
        reserva.add(txtHora,c);

        c.gridx=0;
        c.gridy=2;
        c.gridwidth=2;

        btnReservar=new JButton("Reservar Turno");
        reserva.add(btnReservar,c);
        centro.add(reserva);
        principal.add(centro,BorderLayout.WEST);

        //TABLA //
        // Modelo y tabla donde se mostrarán los turnos registrados.
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Paciente");
        modeloTabla.addColumn("Médico");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");
        modeloTabla.addColumn("Estado");

        tablaTurnos = new JTable(modeloTabla);

        tablaTurnos.setRowHeight(28);
        tablaTurnos.setFont(fuente);

        JScrollPane scroll = new JScrollPane(tablaTurnos);
        scroll.setBorder(BorderFactory.createTitledBorder("TABLA DE TURNOS"));

        principal.add(scroll,BorderLayout.CENTER);

        //PANEL INFERIOR
        // Panel utilizado para buscar y cancelar turnos.
        JPanel inferior = new JPanel(new FlowLayout());
        inferior.add(new JLabel("ID Turno"));
        txtBuscarTurno = new JTextField(8);
        inferior.add(txtBuscarTurno);
        btnBuscarTurno = new JButton("Buscar");
        inferior.add(btnBuscarTurno);
        btnCancelar = new JButton("Cancelar Turno");
        inferior.add(btnCancelar);

        principal.add(inferior,BorderLayout.SOUTH);

        add(principal);

        //EVENTOS
        // Asocia cada botón con su correspondiente metodo.
        btnBuscarPaciente.addActionListener(e->buscarPacientePorApellido());
        btnBuscarMedico.addActionListener(e->buscarMedicoPorApellido());
        btnReservar.addActionListener(e->reservarTurno());
        btnBuscarTurno.addActionListener(e->buscarTurno());
        btnCancelar.addActionListener(e->cancelarTurno());
    }

    // Abre el diálogo de búsqueda de pacientes y carga los datos seleccionados.
    private void buscarPacientePorApellido() {
        // Crea la ventana de búsqueda de pacientes.
        BuscarPacienteDialog dialog = new BuscarPacienteDialog(this, pacienteController);
        // Muestra el diálogo de forma modal.
        dialog.setVisible(true);
        // Obtiene el paciente seleccionado.
        pacienteSeleccionado = dialog.getPacienteSeleccionado();
        // Si se seleccionó un paciente, completa los campos de la interfaz.
        if (pacienteSeleccionado != null) {

            txtApellidoPaciente.setText(pacienteSeleccionado.getApellido());
            txtNombrePaciente.setText(pacienteSeleccionado.getNombre());
            txtIdPaciente.setText(String.valueOf(pacienteSeleccionado.getId()));

        }
        // Posiciona el cursor al inicio de los campos.
        txtApellidoPaciente.setCaretPosition(0);
        txtNombrePaciente.setCaretPosition(0);
        txtIdPaciente.setCaretPosition(0);

    }
    // Abre el diálogo de búsqueda de médicos y carga los datos seleccionados.
    private void buscarMedicoPorApellido() {

        System.out.println("Entró buscarMedicoPorApellido");
        BuscarMedicoDialog dialog = new BuscarMedicoDialog(this, medicoController);
        dialog.setVisible(true);
        // Obtiene el médico seleccionado.
        medicoSeleccionado = dialog.getMedicoSeleccionado();
        // Si se seleccionó un médico, completa los campos correspondientes.
        if (medicoSeleccionado != null) {
            txtApellidoMedico.setText(medicoSeleccionado.getApellido());
            txtNombreMedico.setText(medicoSeleccionado.getNombre());
            txtIdMedico.setText(String.valueOf(medicoSeleccionado.getId()));
        }
    }
    // Registra un nuevo turno utilizando los datos ingresados.
    private void reservarTurno() {

        try {
            // Verifica que exista un paciente seleccionado.
            if (pacienteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un paciente.");
                return;
            }
            //
            JOptionPane.showMessageDialog(this, "Medico = " + medicoSeleccionado);
            // Verifica que exista un médico seleccionado.
            if (medicoSeleccionado == null) {

                JOptionPane.showMessageDialog(this, "Seleccione un médico.");
                return;
            }
            // Convierte los datos ingresados a los tipos correspondientes.
            LocalDate fecha = LocalDate.parse(txtFecha.getText());
            LocalTime hora = LocalTime.parse(txtHora.getText());
            // Crea el objeto Turno.
            Turno turno = new Turno(0, pacienteSeleccionado, medicoSeleccionado, fecha, hora, "RESERVADO");

            // Solicita al controlador registrar el turno.
            boolean reservado = turnoController.reservarTurno(turno);

            if (reservado) {

                JOptionPane.showMessageDialog(this, "Turno reservado correctamente.");

                cargarTurnos();
                txtFecha.setText("");
                txtHora.setText("");

            } else {
                JOptionPane.showMessageDialog(this, "Ese horario ya está ocupado.");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Fecha u hora inválidas.");
        }
    }
    // Carga todos los turnos registrados en la tabla.
    private void cargarTurnos() {
        // Limpia la tabla antes de actualizarla.
        modeloTabla.setRowCount(0);
        ArrayList<Turno> turnos = turnoController.listarTurnos();
        // Agrega cada turno al modelo de la tabla.
        for (Turno t : turnos) {

            modeloTabla.addRow(
                    new Object[]{
                            t.getIdTurno(),
                            t.getPaciente().getNombre() + " " + t.getPaciente().getApellido(),
                            t.getMedico().getNombre() + " " + t.getMedico().getApellido(),
                            t.getFecha(),
                            t.getHora(),
                            t.getEstado()
                    }
            );
        }
    }
    // Busca un turno mediante su ID.
    private void buscarTurno() {

        try {
            int id = Integer.parseInt(txtBuscarTurno.getText());
            Turno turno = turnoController.buscarTurno(id);

            if (turno != null) {
                // Muestra toda la información del turno encontrado.
                JOptionPane.showMessageDialog(
                        this,
                        "ID: " + turno.getIdTurno()
                                + "\nPaciente: " + turno.getPaciente().getNombre()
                                + " " + turno.getPaciente().getApellido()
                                + "\nMédico: " + turno.getMedico().getNombre()
                                + " " + turno.getMedico().getApellido()
                                + "\nFecha: " + turno.getFecha()
                                + "\nHora: " + turno.getHora()
                                + "\nEstado: " + turno.getEstado());

            } else {
                JOptionPane.showMessageDialog(this, "Turno no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
    // Cancela un turno utilizando su ID.
    private void cancelarTurno() {

        try {

            int id = Integer.parseInt(txtBuscarTurno.getText());
            boolean cancelado = turnoController.cancelarTurno(id);

            if (cancelado) {

                JOptionPane.showMessageDialog(this, "Turno cancelado");
                cargarTurnos();
                // Actualiza la tabla luego de la cancelación.

            } else {
                JOptionPane.showMessageDialog(this, "No existe el turno");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
}