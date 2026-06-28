package controlador;

import dao.TurnoDAO;
import modelo.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
//controlador de turnos
//intermediarios entre las vistas y la capa de acceso a datos
public class TurnoController {
    //declaramos turnoDao
    private TurnoDAO turnoDAO;
    //constructor
    public TurnoController() {
        turnoDAO = new TurnoDAO();
    }
    //metodo agregar turno (llamamos desde turnoDao)
    public ArrayList<Turno> listarTurnos() {
        return turnoDAO.listarTurnos();
    }
    //metodo reservar turno
    public boolean reservarTurno(Turno turno) {
         //en caso que el turno este disponible lo guardamos
        boolean disponible = turnoDAO.turnoDisponible(turno.getMedico().getId(), turno.getFecha(), turno.getHora());
        if (disponible) {
            return turnoDAO.guardarTurno(turno);
        }
        return false;
    }
    //metodo eliminar cancelarturno (llamamos desde turnoDao)
    public boolean cancelarTurno(int idTurno) {
        return turnoDAO.cancelarTurno(idTurno);

    }

    public Turno buscarTurno(int id) {
        return turnoDAO.buscarTurno(id);
    }

}