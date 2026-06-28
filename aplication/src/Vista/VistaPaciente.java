package Vista;

import controlador.PacienteController;
import modelo.Paciente;
import dao.PacienteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Clase encargada de administrar la gestión de pacientes.
// Permite registrar, buscar, listar y eliminar pacientes del sistema.
public class VistaPaciente extends JFrame {
    // Controlador utilizado para acceder a las operaciones
    private PacienteController pacienteController;
    // Campos utilizados para registrar

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    // Campos utilizados para realizar búsquedas
    private JTextField txtBuscarId;
    private JTextField txtApellidoBusqueda;
    // Tabla donde se muestran los pacientes registrados
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;

    // Constructor de la ventana
    public VistaPaciente(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
        configurarVentana();
        crearComponentes();
        cargarPacientes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Pacientes");
        setSize(900, 600);
        // Configura la ventana, crea los componentes y carga los médicos existentes.
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel utilizado para registrar un nuevo médico.
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Paciente"));
        // Campo Nombre.
        panelFormulario.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        // Campo apellido
        panelFormulario.add(new JLabel("Apellido"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);
        // Campo telefono
        panelFormulario.add(new JLabel("Telefono"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);
        // Campo email
        panelFormulario.add(new JLabel("Email"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        // Campo direccion
        panelFormulario.add(new JLabel("Direccion"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        // Campo guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPaciente());
        panelFormulario.add(btnGuardar);
        add(panelFormulario, BorderLayout.NORTH);

        //TABLA
        // Modelo de la tabla

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Email");

        tablaPacientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaPacientes);
        add(scroll, BorderLayout.CENTER);

        //PANEL INFERIOR
        // Panel con los controles de búsqueda y eliminación.
        JPanel panelInferior = new JPanel();
        txtBuscarId = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");

        JButton btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("ID:"));
        panelInferior.add(txtBuscarId);
        panelInferior.add(btnBuscar);
        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);
        btnBuscar.addActionListener(e -> buscarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());

        //buscar por apellido
        txtApellidoBusqueda = new JTextField(15);
        JButton btnBuscarApellido = new JButton("Buscar por Apellido");
        panelInferior.add(new JLabel("Apellido:"));

        // EVENTOS //

        // Asocia cada botón con su correspondiente acción.
        panelInferior.add(txtApellidoBusqueda);
        panelInferior.add(btnBuscarApellido);
        btnBuscarApellido.addActionListener(e -> buscarPorApellido());

    }
    // Registra un nuevo paciente
    private void guardarPaciente() {

        try {

            Paciente paciente = new Paciente(0, txtNombre.getText(), txtApellido.getText(), txtTelefono.getText(), txtEmail.getText(), txtDireccion.getText());
            boolean guardado = pacienteController.agregarPaciente(paciente);

            if (guardado) {

                JOptionPane.showMessageDialog(this, "Paciente registrado correctamente");
                limpiarCampos();
                cargarPacientes();

            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar paciente");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    // Carga todos los pacientes registrados en la tabla.
    private void cargarPacientes() {

        modeloTabla.setRowCount(0);//limpia campos

        ArrayList<Paciente> pacientes = pacienteController.listarPacientes();

        for (Paciente p : pacientes) {// Agrega cada pacientes al modelo de la tabla.
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getApellido(), p.getTelefono(), p.getEmail()});
        }
    }
    //buscar ID
    private void buscarPaciente() {

        try {
            int id = Integer.parseInt(txtBuscarId.getText());
            Paciente paciente = pacienteController.buscarPaciente(id);
            if (paciente != null) {//Muestra toda la información del médico encontrado.
                    String datos = "ID: " + paciente.getId()
                                    + "\nNombre: " + paciente.getNombre()
                                    + "\nApellido: " + paciente.getApellido()
                                    + "\nTelefono: " + paciente.getTelefono()
                                    + "\nEmail: " + paciente.getEmail()
                                    + "\nDireccion: " + paciente.getDireccion();
                    JOptionPane.showMessageDialog(this, datos, "Datos del Paciente", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
    // Elimina un paciente utilizando su ID.
    private void eliminarPaciente() {
        try {
            int id = Integer.parseInt(txtBuscarId.getText());
            boolean eliminado = pacienteController.eliminarPaciente(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(this, "Paciente eliminado");
                cargarPacientes();// Actualiza la tabla luego de la eliminación.

            } else {
                JOptionPane.showMessageDialog(this, "No existe paciente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
    // Busca pacientes por apellido y actualiza la tabla.
    private void buscarPorApellido() {

        String apellido = txtApellidoBusqueda.getText();
        ArrayList<Paciente> pacientes = pacienteController.buscarPorApellido(apellido);
        // Limpia la tabla antes de mostrar los resultados.
        modeloTabla.setRowCount(0);

        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron pacientes");
            return;
        }
        StringBuilder resultado = new StringBuilder();
        for (Paciente p : pacientes) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getApellido(), p.getTelefono(), p.getEmail()});
            // Agrega los médicos encontrados a la tabla.
            resultado.append("ID: ")
                    .append(p.getId())
                    .append(" | Nombre: ")
                    .append(p.getNombre())
                    .append(" ")
                    .append(p.getApellido())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                resultado.toString(),
                "Pacientes encontrados",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    // Limpia los campos del formulario de registro.
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }
}