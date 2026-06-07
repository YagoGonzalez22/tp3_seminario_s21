package controlador;

import dao.PacienteDAO;
import modelo.Paciente;

import java.util.ArrayList;
//controlador de paciente
//intermediarios entre las vistas y la capa de acceso a datos
public class PacienteController {
    //declaramos pacienteDao
    private PacienteDAO pacienteDAO;
    //constructor
    public PacienteController() {
        pacienteDAO = new PacienteDAO();
    }
    //metodo agregar paciente (llamamos desde pacienteDao)
    public boolean agregarPaciente(Paciente paciente) {
        return pacienteDAO.guardarPaciente(paciente);
    }
    //metodo listar paciente  (llamamos desde pacienteDao)
    public ArrayList<Paciente> listarPacientes() {
        return pacienteDAO.listarPacientes();
    }
    //metodo buscarpaciente  (llamamos desde pacienteDao)
    public Paciente buscarPaciente(int id) {
        return pacienteDAO.buscarPaciente(id);
    }
    //metodo eliminar eliminarpaciente  (llamamos desde pacienteDao)
    public boolean eliminarPaciente(int id) {
        return pacienteDAO.eliminarPaciente(id);
    }
}