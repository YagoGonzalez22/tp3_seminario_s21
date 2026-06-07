package Vista;

import controlador.PacienteController;
import modelo.Paciente;

import java.util.Scanner;
//vista paciente
public class VistaPaciente {
//varible
    private PacienteController pacienteController;
    private Scanner teclado;
//constructor
    public VistaPaciente(PacienteController pacienteController) {

        this.pacienteController = pacienteController;
        this.teclado = new Scanner(System.in);

    }
//menu iniciar
    public void iniciar() {

        int opcion;

        do {
                //menu
            System.out.println("\n===== GESTION DE PACIENTES =====");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Buscar paciente");
            System.out.println("4. Eliminar paciente");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
              //casos
                case 1:
                    registrarPaciente();
                    break;

                case 2:
                    listarPacientes();
                    break;

                case 3:
                    buscarPaciente();
                    break;

                case 4:
                    eliminarPaciente();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }
 //registro de paciente
    private void registrarPaciente() {

        try {

            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Apellido: ");
            String apellido = teclado.nextLine();

            System.out.print("Telefono: ");
            String telefono = teclado.nextLine();

            System.out.print("Email: ");
            String email = teclado.nextLine();

            System.out.print("Direccion: ");
            String direccion = teclado.nextLine();

            Paciente paciente = new Paciente(0, nombre, apellido, telefono, email, direccion);

            boolean guardado = pacienteController.agregarPaciente(paciente);

            if (guardado) {
                System.out.println("\nPaciente registrado correctamente.");
            } else {
                System.out.println("\nError al registrar paciente.");
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }
    }
 //listado de paciente
    private void listarPacientes() {
        var pacientes = pacienteController.listarPacientes();

        if (pacientes.isEmpty()) {
            System.out.println("\nNo existen pacientes registrados.");
            return;
        }
        System.out.println("\n===== LISTA DE PACIENTES =====");

        for (Paciente p : pacientes) {
            System.out.println(p.getId() + " | " + p.getNombre() + " " + p.getApellido() + " | " + p.getTelefono());
        }
    }
    //busqueda de paciente
    private void buscarPaciente() {

        System.out.print("Ingrese ID del paciente: ");
        int id = teclado.nextInt();
        Paciente paciente = pacienteController.buscarPaciente(id);

        if (paciente != null) {

            System.out.println("\nPaciente encontrado:");
            System.out.println(paciente.getNombre() + " " + paciente.getApellido());
            System.out.println(paciente.getTelefono());
            System.out.println(paciente.getEmail());

        } else {

            System.out.println("Paciente no encontrado.");

        }
    }
//eliminar paciente
    private void eliminarPaciente() {

        System.out.print("Ingrese ID del paciente: ");
        int id = teclado.nextInt();

        boolean eliminado = pacienteController.eliminarPaciente(id);

        if (eliminado) {
            System.out.println("Paciente eliminado correctamente.");

        } else {
            System.out.println("No existe un paciente con ese ID.");

        }
    }
}