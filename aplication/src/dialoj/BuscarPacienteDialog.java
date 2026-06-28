package dialoj;

import controlador.PacienteController;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// Clase encargada de mostrar una ventana modal para buscar y seleccionar un paciente.
// Un JDialog en Java es un componente de Java Swing utilizado para crear ventanas secundarias o emergentes en una aplicación
public class BuscarPacienteDialog extends JDialog {
    // Controlador utilizado para acceder a las operaciones
    private PacienteController pacienteController;
    // Componentes de la interfaz.
    private JTextField txtApellido;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    // Botones de la ventana.
    private JButton btnBuscar;
    private JButton btnSeleccionar;
    private JButton btnCancelar;
    // Almacena el médico seleccionado por el usuario.
    private Paciente pacienteSeleccionado;
    // Constructor del diáloj
    public BuscarPacienteDialog(Frame parent, PacienteController pacienteController) {
        super(parent, "Buscar Paciente", true);
        this.pacienteController = pacienteController;
        // Inicializa la interfaz, configura la ventana y registra los eventos.
        inicializarComponentes();
        configurarVentana();
        configurarEventos();

    }
    // Configura las propiedades principales de la ventana.
    private void configurarVentana() {
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    // Crea todos los componentes gráficos del diálogo.
    private void inicializarComponentes() {
        // Panel principal.
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        // Panel destinado a la búsqueda por apellido.
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panelBusqueda.add(new JLabel("Apellido"), gbc);

        gbc.gridy++;
        txtApellido = new JTextField(25);
        panelBusqueda.add(txtApellido, gbc);

        gbc.gridx = 1;
        btnBuscar = new JButton("Buscar");

        panelBusqueda.add(btnBuscar, gbc);
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);

        // Modelo y tabla donde se mostrarán los médicos encontrados.
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Nombre");
        tablaPacientes = new JTable(modeloTabla);

        // Solo permitimos seleccionar una fila a la vez.
        tablaPacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaPacientes);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        // Panel inferior con los botones de acción
        JPanel panelBotones = new JPanel();
        btnSeleccionar = new JButton("Seleccionar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

    }
    // Asocia los eventos de los componentes con sus respectivos métodos.
    private void configurarEventos() {
        // Busca pacientes por apellido.
        btnBuscar.addActionListener(e -> buscarPacientes());
        // Selecciona el médico marcado en la tabla.
        btnSeleccionar.addActionListener(e -> seleccionarPaciente());
        btnCancelar.addActionListener(e -> dispose());
        tablaPacientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarPaciente();
                }
            }

        });
        txtApellido.addActionListener(e -> buscarPacientes());
        getRootPane().setDefaultButton(btnBuscar);
    }
    // Busca pacientes cuyo apellido coincida con el ingresado.
    private void buscarPacientes() {
        String apellido = txtApellido.getText().trim();
        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un apellido.");
            return;
        }
        // Solicita al controlador la lista de encontrados  y limpiamos tabla
        ArrayList<Paciente> pacientes = pacienteController.buscarPorApellido(apellido);
        modeloTabla.setRowCount(0);
        //si no se encuentran
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron pacientes.");
            return;
        }
        // Agrega cada pacientes encontrado a la tabla.
        for (Paciente paciente : pacientes) {
            modeloTabla.addRow(new Object[]{paciente.getId(), paciente.getApellido(), paciente.getNombre()});
        }

    }
    // Obtiene el seleccionado por el usuario.
    private void seleccionarPaciente() {

        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {// Verifica que exista una fila seleccionada.
            JOptionPane.showMessageDialog(this,"Seleccione un paciente.");
            return;
        }
        // Obtiene el ID del médico seleccionado.
        int idPaciente = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        pacienteSeleccionado = pacienteController.buscarPaciente(idPaciente);dispose();
    }
    // Devuelve el médico seleccionado a la ventana que abrió el diálogo.
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }
}



