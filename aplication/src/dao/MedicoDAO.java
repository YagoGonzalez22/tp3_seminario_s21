package dao;

import modelo.Medico;

import java.sql.*;
import java.util.ArrayList;

public class MedicoDAO {
    //metodo insertar medico
    public boolean guardarMedico(Medico medico) {
        //metodo insertar medico mediante jsql
        String sql = "INSERT INTO medicos " + "(nombre,apellido,telefono,email,especialidad) " + "VALUES (?,?,?,?,?)";
        try { //captura de exceptiones
            //conectamos y guardamos los datos
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getEmail());
            ps.setString(5, medico.getEspecialidad());
            ps.executeUpdate();

            return true;
          //captura de exceptiones
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //metodo listar medico mediante jsq
    public ArrayList<Medico> listarMedicos() {

        ArrayList<Medico> medicos = new ArrayList<>();
        //metodo listar medico mediante jsql
        String sql = "SELECT * FROM medicos";

        try {//captura de exceptiones
             //conectamos y listamos
            Connection con = ConexionBD.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                medicos.add(
                        new Medico(
                                rs.getInt("id_medico"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("telefono"),
                                rs.getString("email"),
                                rs.getString("especialidad")
                        )
                );
            }
        } catch (Exception e) { //captura de exceptiones
            e.printStackTrace();
        }
        return medicos;
    }
    //metodo buscar medico
    public Medico buscarMedico(int id) {

        String sql = "SELECT * FROM medicos WHERE id_medico=?";

        try {//captura de exceptiones
            //conectamos y buscamos medicos mediante el id
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("especialidad")
                );
            }

        } catch (Exception e) {//captura de exceptiones
            e.printStackTrace();
        }

        return null;
    }
    //metodo eliminar
    public boolean eliminarMedico(int id) {
          //eliminamos medicos mediante id y jsql
        String sql = "DELETE FROM medicos WHERE id_medico=?";

        try {//captura de exceptiones

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
               //actualizamos
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
           //captura de exceptiones
            e.printStackTrace();
        }

        return false;
    }
}