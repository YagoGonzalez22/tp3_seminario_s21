package controlador;

import dao.MedicoDAO;
import modelo.Medico;

import java.util.ArrayList;
//controlador de medico
//intermediarios entre las vistas y la capa de acceso a datos
public class MedicoController {
    //declaramos medicoDAo
    private MedicoDAO medicoDAO;
    //constructor
    public MedicoController() {
        medicoDAO = new MedicoDAO();
    }
    //metodo agregar medico (llamamos desde medicoDao)
    public boolean agregarMedico(Medico medico) {
        return medicoDAO.guardarMedico(medico);
    }
    //metodo listar medicos  (llamamos desde medicoDao)
    public ArrayList<Medico> listarMedicos() {
        return medicoDAO.listarMedicos();
    }
    //metodo buscarmedicos  (llamamos desde medicoDao)
    public Medico buscarMedico(int id) {
        return medicoDAO.buscarMedico(id);
    }
    //metodo eliminar medicos  (llamamos desde medicoDao)
    public boolean eliminarMedico(int id) {
        return medicoDAO.eliminarMedico(id);
    }

    public ArrayList<Medico> buscarPorApellido(String apellido) {
        return medicoDAO.buscarPorApellido(apellido);
    }
}