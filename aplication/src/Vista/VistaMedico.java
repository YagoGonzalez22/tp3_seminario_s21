package Vista;

import controlador.MedicoController;
import modelo.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
// Clase encargada de administrar la gestión de médicos.
// Permite registrar, buscar, listar y eliminar médicos del sistema.

public class VistaMedico extends JFrame {

    // Controlador utilizado para acceder a las operaciones de los médicos.
    private MedicoController medicoController;
    // Campos utilizados para registrar un nuevo médico.
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtEspecialidad;
    // Campos utilizados para realizar búsquedas.
    private JTextField txtBuscarId;
    private JTextField txtBuscarApellido;
    // Tabla donde se muestran los médicos registrados.
    private JTable tablaMedicos;
    private DefaultTableModel modeloTabla;


    // Constructor de la ventana de gestión de médicos.
    public VistaMedico(MedicoController medicoController) {
        this.medicoController = medicoController;
        configurarVentana();
        crearComponentes();
        cargarMedicos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Médicos");
        setSize(900, 600);
        // Configura la ventana, crea los componentes y carga los médicos existentes.
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // Crea todos los componentes gráficos de la ventana.
    private void crearComponentes() {
         //FORMULARIO//

        // Panel utilizado para registrar un nuevo médico.
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Médico"));

        // Campo Nombre.
        panelFormulario.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        // Campo Apellido.
        panelFormulario.add(new JLabel("Apellido"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        // Campo Teléfono.
        panelFormulario.add(new JLabel("Teléfono"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        // Campo Email.
        panelFormulario.add(new JLabel("Email"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        // Campo Especialidad.
        panelFormulario.add(new JLabel("Especialidad"));
        txtEspecialidad = new JTextField();
        panelFormulario.add(txtEspecialidad);

        // Botón para registrar un médico.
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarMedico());

        panelFormulario.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);

        //TABLA
        // Modelo de la tabla donde se mostrarán los médicos.
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Especialidad");

        tablaMedicos = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaMedicos);

        add(scroll, BorderLayout.CENTER);

        //PANEL INFERIOR
        // Panel con los controles de búsqueda y eliminación.
        JPanel panelInferior = new JPanel();
        txtBuscarId = new JTextField(5);
        txtBuscarApellido = new JTextField(10);

        JButton btnBuscarId = new JButton("Buscar ID");
        JButton btnBuscarApellido = new JButton("Buscar Apellido");
        JButton btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("ID"));
        panelInferior.add(txtBuscarId);
        panelInferior.add(btnBuscarId);

        panelInferior.add(new JLabel("Apellido"));
        panelInferior.add(txtBuscarApellido);
        panelInferior.add(btnBuscarApellido);

        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);

        // EVENTOS //

        // Asocia cada botón con su correspondiente acción.
        btnBuscarId.addActionListener(e -> buscarMedico());
        btnBuscarApellido.addActionListener(e -> buscarPorApellido());
        btnEliminar.addActionListener(e -> eliminarMedico());
    }

    // Registra un nuevo médico en el sistema.
    private void guardarMedico() {

        Medico medico = new Medico(0, txtNombre.getText(), txtApellido.getText(), txtTelefono.getText(), txtEmail.getText(), txtEspecialidad.getText());
        // Solicita al controlador registrar el médico.
        boolean guardado = medicoController.agregarMedico(medico);

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Médico registrado correctamente");
            limpiarCampos();
            cargarMedicos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar médico");
        }
    }
    // Carga todos los médicos registrados en la tabla.
    private void cargarMedicos() {
        modeloTabla.setRowCount(0); // Limpia la tabla antes de volver a cargarla.

        ArrayList<Medico> medicos = medicoController.listarMedicos();
        for (Medico m : medicos) { // Agrega cada médico al modelo de la tabla.
            modeloTabla.addRow(new Object[]{m.getId(), m.getNombre(), m.getApellido(), m.getEspecialidad()});
        }
    }
    // Busca un médico por su ID.
    private void buscarMedico() {

        try {

            int id = Integer.parseInt(txtBuscarId.getText());

            Medico medico = medicoController.buscarMedico(id);

            if (medico != null) {

                String datos = // Muestra toda la información del médico encontrado.
                        "ID: " + medico.getId()
                                + "\nNombre: " + medico.getNombre()
                                + "\nApellido: " + medico.getApellido()
                                + "\nTeléfono: " + medico.getTelefono()
                                + "\nEmail: " + medico.getEmail()
                                + "\nEspecialidad: " + medico.getEspecialidad();

                JOptionPane.showMessageDialog(this, datos);

            } else {
                JOptionPane.showMessageDialog(this, "Médico no encontrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
    // Busca médicos por apellido y actualiza la tabla.
    private void buscarPorApellido() {

        String apellido = txtBuscarApellido.getText();
        ArrayList<Medico> medicos = medicoController.buscarPorApellido(apellido);
        // Limpia la tabla antes de mostrar los resultados.
        modeloTabla.setRowCount(0);

        StringBuilder resultado = new StringBuilder();

        for (Medico m : medicos) {
            // Agrega los médicos encontrados a la tabla.
            modeloTabla.addRow(new Object[]{m.getId(), m.getNombre(), m.getApellido(), m.getEspecialidad()});

            resultado.append("ID: ")
                    .append(m.getId())
                    .append(" | ")
                    .append(m.getNombre())
                    .append(" ")
                    .append(m.getApellido())
                    .append("\n");
        }

        if (!medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, resultado.toString());

        } else {

            JOptionPane.showMessageDialog(this, "No se encontraron médicos");
        }
    }
    // Elimina un médico utilizando su ID.
    private void eliminarMedico() {

        try {

            int id = Integer.parseInt(txtBuscarId.getText());
            boolean eliminado = medicoController.eliminarMedico(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Médico eliminado");
                cargarMedicos(); // Actualiza la tabla luego de la eliminación.
            } else {
                JOptionPane.showMessageDialog(this, "No existe ese médico");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }
    // Limpia los campos del formulario de registro.
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtEspecialidad.setText("");
    }
}