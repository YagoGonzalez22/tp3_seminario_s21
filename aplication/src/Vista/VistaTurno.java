package Vista;

import controlador.PacienteController;
import controlador.MedicoController;
import controlador.TurnoController;

import modelo.Paciente;
import modelo.Medico;
import modelo.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
//vista de turnos
public class VistaTurno {
//variables
    private TurnoController turnoController;
    private PacienteController pacienteController;
    private MedicoController medicoController;
    private Scanner teclado;
//menu iniciar
    public void iniciar() {
        int opcion;
        do {
             //menu
            System.out.println("\n===== GESTION DE TURNOS =====");
            System.out.println("1. Reservar turno");
            System.out.println("2. Listar turnos");
            System.out.println("3. Cancelar turno");
            System.out.println("0. Volver");

            System.out.print("Opcion: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
            //casos
                case 1:
                    reservarTurno();
                    break;

                case 2:
                    listarTurnos();
                    break;

                case 3:
                    cancelarTurno();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

    }
 //constructor
    public VistaTurno(TurnoController turnoController, PacienteController pacienteController, MedicoController medicoController) {
        this.turnoController = turnoController;
        this.pacienteController = pacienteController;
        this.medicoController = medicoController;

        this.teclado = new Scanner(System.in);
    }
    //reserva de turnos
    private void reservarTurno() {

        try {

            System.out.print("ID Paciente: ");
            int idPaciente = teclado.nextInt();
            System.out.print("ID Medico: ");
            int idMedico = teclado.nextInt();
            teclado.nextLine();

            Paciente paciente = pacienteController.buscarPaciente(idPaciente);
            Medico medico = medicoController.buscarMedico(idMedico);

            if (paciente == null || medico == null) {
                System.out.println("Paciente o medico inexistente.");
                return;
            }

            System.out.print("Fecha (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(teclado.nextLine());

            System.out.print("Hora (HH:MM): ");
            LocalTime hora = LocalTime.parse(teclado.nextLine());

            Turno turno = new Turno(0, paciente, medico, fecha, hora, "RESERVADO");

            boolean reservado = turnoController.reservarTurno(turno);

            if (reservado) {
                System.out.println("Turno reservado correctamente.");

            } else {
                System.out.println("Horario ocupado.");

            }

        } catch (Exception e) {
            System.out.println("Error en los datos ingresados.");

        }

    }
    //listado de turnos
    private void listarTurnos() {

        var turnos = turnoController.listarTurnos();

        for (Turno t : turnos) {
            System.out.println(t.getIdTurno() + " | " + t.getPaciente().getNombre() + " " + t.getPaciente().getApellido()
                    + " | " + t.getMedico().getNombre() + " " + t.getMedico().getApellido() + " | " + t.getFecha()
                    + " " + t.getHora() + " | " + t.getEstado());
        }

    }
    //cancelar turno
    private void cancelarTurno() {

        System.out.print("ID Turno: ");

        int id = teclado.nextInt();

        boolean cancelado = turnoController.cancelarTurno(id);

        if (cancelado) {
            System.out.println("Turno cancelado.");

        } else {
            System.out.println("No existe el turno.");

        }

    }


}