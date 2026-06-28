package dao;

import modelo.Paciente;

import java.sql.*;
import java.util.ArrayList;

public class PacienteDAO {
    //metodo insertar paciente
    public boolean guardarPaciente(Paciente paciente) {
        //metodo insertar medico jsql
        String sql = "INSERT INTO pacientes " + "(nombre,apellido,telefono,email,direccion) " + "VALUES (?,?,?,?,?)";
        try { //captura de exceptiones
              //conectamos y guardamos los datos
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getEmail());
            ps.setString(5, paciente.getDireccion());

            ps.executeUpdate();  //actualizamos

            return true;

        } catch (Exception e) { //captura de exceptiones

            e.printStackTrace();
        }

        return false;
    }
    //metodo listar paciente mediante jsq
    public ArrayList<Paciente> listarPacientes() {
        //metodo listar mediante jsql
        ArrayList<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";

        try {//captura de exceptiones
            //conectamos y listamos
            Connection con = ConexionBD.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Paciente paciente =
                        new Paciente(
                                rs.getInt("id_paciente"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("telefono"),
                                rs.getString("email"),
                                rs.getString("direccion")
                        );

                pacientes.add(paciente);
            }

        } catch (Exception e) {//captura de exceptiones

            e.printStackTrace();
        }

        return pacientes;
    }
    //metodo buscar
    public Paciente buscarPaciente(int id) {

        String sql = "SELECT * FROM pacientes WHERE id_paciente=?";

        try {//captura de exceptiones
              //conectamos y buscamos mediante el id
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                );
            }

        } catch (Exception e) {//captura de exceptiones

            e.printStackTrace();
        }

        return null;
    }
    //metodo eliminar
    public boolean eliminarPaciente(int id) {
       //eliminamos medicos mediante id y jsql
        String sql = "DELETE FROM pacientes " + "WHERE id_paciente = ?";

        try {//captura de exceptiones

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            //actualizacion
            return ps.executeUpdate() > 0;

        } catch (Exception e) {//captura de exceptiones

            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Paciente> buscarPorApellido(String apellido) {

        ArrayList<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM pacientes WHERE apellido LIKE ?";

        try {
            Connection con = ConexionBD.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + apellido + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Paciente paciente = new Paciente(
                               rs.getInt("id_paciente"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("telefono"),
                                rs.getString("email"),
                                rs.getString("direccion"));
                pacientes.add(paciente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacientes;
    }


}