package dialoj;

import controlador.MedicoController;
import modelo.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// Clase encargada de mostrar una ventana modal para buscar y seleccionar un médico.
// Un JDialog en Java es un componente de Java Swing utilizado para crear ventanas secundarias o emergentes en una aplicación
public class BuscarMedicoDialog extends JDialog {
    // Controlador utilizado para acceder a las operaciones relacionadas con los médicos.
    private MedicoController medicoController;
    // Componentes de la interfaz.
    private JTextField txtApellido;
    private JTable tablaMedicos;
    private DefaultTableModel modeloTabla;
    // Botones de la ventana.
    private JButton btnBuscar;
    private JButton btnSeleccionar;
    private JButton btnCancelar;
    // Almacena el médico seleccionado por el usuario.
    private Medico medicoSeleccionado;
    // Constructor del diáloj
    public BuscarMedicoDialog(Frame parent, MedicoController medicoController) {
        // Crea un diálogo modal asociado a la ventana principal.
        super(parent, "Buscar Médico", true);
        this.medicoController = medicoController;
        // Inicializa la interfaz, configura la ventana y registra los eventos.
        inicializarComponentes();
        configurarVentana();
        configurarEventos();

    }
    // Configura las propiedades principales de la ventana.
    private void configurarVentana() {
        setSize(650,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
    // Crea todos los componentes gráficos del diálogo.
    private void inicializarComponentes() {
        // Panel principal.
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        panelPrincipal.setLayout(new BorderLayout(10,10));
        // Panel destinado a la búsqueda por apellido.
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
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
        tablaMedicos = new JTable(modeloTabla);
        // Solo permitimos seleccionar una fila a la vez.
        tablaMedicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaMedicos);

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
        // Busca médicos por apellido.
        btnBuscar.addActionListener(e -> buscarMedicos());
        // Selecciona el médico marcado en la tabla.
        btnSeleccionar.addActionListener(e -> seleccionarMedico());

        btnCancelar.addActionListener(e -> dispose());

        tablaMedicos.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){seleccionarMedico();}
            }

        });

        txtApellido.addActionListener(e -> buscarMedicos());

        getRootPane().setDefaultButton(btnBuscar);

    }
    // Busca médicos cuyo apellido coincida con el ingresado.
    private void buscarMedicos() {
        String apellido = txtApellido.getText().trim();

        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un apellido.");
            return;// Verifica que el usuario haya ingresado un apellido.

        }
        // Solicita al controlador la lista de médicos encontrados  y limpiamos tabla
        ArrayList<Medico> medicos = medicoController.buscarPorApellido(apellido);
        modeloTabla.setRowCount(0);
        //si no se encuentran medicos
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron médicos.");
            return;
        }
        // Agrega cada médico encontrado a la tabla.
        for (Medico medico : medicos) {
            modeloTabla.addRow(new Object[]{medico.getId(), medico.getApellido(), medico.getNombre()});
        }
    }
    // Obtiene el médico seleccionado por el usuario.
    private void seleccionarMedico() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) { // Verifica que exista una fila seleccionada.
            JOptionPane.showMessageDialog(this, "Seleccione un médico.");
            return;
        }
        // Obtiene el ID del médico seleccionado.
        int idMedico = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        medicoSeleccionado = medicoController.buscarMedico(idMedico);
        dispose();
    }
    // Devuelve el médico seleccionado a la ventana que abrió el diálogo.
    public Medico getMedicoSeleccionado() {
        return medicoSeleccionado;
    }

}