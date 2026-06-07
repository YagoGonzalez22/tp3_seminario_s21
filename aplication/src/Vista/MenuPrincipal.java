package Vista;

import controlador.MedicoController;
import controlador.PacienteController;
import controlador.TurnoController;
import modelo.Usuario;
import modelo.Usuario;
import java.util.Scanner;
import java.util.Scanner;
//menu principal del programa
public class MenuPrincipal {
//declaracion variables
    private Usuario usuario;
    private Scanner teclado;
    private PacienteController pacienteController;
    private MedicoController medicoController;
    private TurnoController turnoController;
 //contructor de datos
    public MenuPrincipal(Usuario usuario, PacienteController pacienteController, MedicoController medicoController, TurnoController turnoController) {
        this.usuario = usuario;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;
        this.turnoController = turnoController;
        this.teclado = new Scanner(System.in);
    }
  //metodo iniciar con el menu
    public void iniciar() {
         //login
        System.out.println("\n=================================");
        System.out.println(" SISTEMA DE TURNOS MEDICOS ");
        System.out.println("=================================");
        System.out.println("Usuario: " + usuario.getNombreUsuario());

        System.out.println("Rol: " + usuario.getRol());

        int opcion;

        do {
           //menu
            System.out.println("=================================");
            System.out.println(" SISTEMA DE TURNOS MEDICOS ");
            System.out.println("=================================");
            System.out.println("1. Gestionar Pacientes");
            System.out.println("2. Gestionar Medicos");
            System.out.println("3. Gestionar Turnos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = teclado.nextInt();

            switch (opcion) {
             //opciones
                case 1:
                    VistaPaciente vistaPaciente = new VistaPaciente(pacienteController);
                    vistaPaciente.iniciar();

                    break;

                case 2:

                    Vista.VistaMedico vistaMedico = new Vista.VistaMedico(medicoController);
                    vistaMedico.iniciar();
                    break;

                case 3:
                    VistaTurno vistaTurno = new VistaTurno(turnoController, pacienteController, medicoController);
                    vistaTurno.iniciar();

                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);//fiin
    }
}