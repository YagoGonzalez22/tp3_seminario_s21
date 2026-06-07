package Vista;

import controlador.MedicoController;
import modelo.Medico;

import java.util.Scanner;
//menu de medico
public class VistaMedico {
//variables
    private MedicoController medicoController;
    private Scanner teclado;
//constructores
    public VistaMedico(MedicoController medicoController) {
        this.medicoController = medicoController;
        this.teclado = new Scanner(System.in);
    }
//iniciart medicos
    public void iniciar() {

        int opcion;

        do {
           //menu
            System.out.println("\n===== GESTION DE MEDICOS =====");
            System.out.println("1. Registrar medico");
            System.out.println("2. Listar medicos");
            System.out.println("3. Buscar medico");
            System.out.println("4. Eliminar medico");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            opcion = teclado.nextInt();
            teclado.nextLine();
            //opciones
            switch (opcion) {

                case 1:
                    registrarMedico();
                    break;

                case 2:
                    listarMedicos();
                    break;

                case 3:
                    buscarMedico();
                    break;

                case 4:
                    eliminarMedico();
                    break;

            }

        } while (opcion != 0);

    }
 //registro de medicos con cada uno de sus datos
    private void registrarMedico() {
       //datos
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Telefono: ");
        String telefono = teclado.nextLine();

        System.out.print("Email: ");
        String email = teclado.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = teclado.nextLine();

        Medico medico =
                new Medico(0, nombre, apellido, telefono, email, especialidad);

        medicoController.agregarMedico(medico);

        System.out.println("Medico registrado correctamente.");



    }
  //listado de medicos
    private void listarMedicos() {
        var medicos = medicoController.listarMedicos();
        for (Medico m : medicos) {
            System.out.println(m.getId() + " | " + m.getNombre() + " " + m.getApellido() + " | " + m.getEspecialidad());
        }

    }
 //buscar medico
    private void buscarMedico() {

        System.out.print("ID del medico: ");

        int id = teclado.nextInt();

        Medico medico = medicoController.buscarMedico(id);

        if (medico != null) {

            System.out.println(medico.getNombre() + " " + medico.getApellido());
            System.out.println(medico.getEspecialidad());

        } else {
            System.out.println("Medico no encontrado.");
        }

    }
//eliminacion de medico
    private void eliminarMedico() {

        System.out.print("ID del medico: ");
        int id = teclado.nextInt();
        boolean eliminado = medicoController.eliminarMedico(id);

        if (eliminado) {
            System.out.println("Medico eliminado.");

        } else {
            System.out.println("No existe ese medico.");

        }

    }

}