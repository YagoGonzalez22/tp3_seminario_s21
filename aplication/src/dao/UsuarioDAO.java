package dao;

import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
      //metodo de login del admin
    public Usuario login(String nombreUsuario, String password) {
       //usuario junto a su contrasela y usuario
        String sql = "SELECT * FROM usuarios " + "WHERE nombre_usuario = ? " + "AND password = ?";

        try {//exception

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombreUsuario);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
               //lo que se mostrara en pantallas datos del admin
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getString("email")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
     //guardado de usuario
    public boolean guardarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios " + "(nombre_usuario,password,rol,email) " + "VALUES (?,?,?,?)";

        try { //exception
           //coneccion y get de los datos
            Connection con = ConexionBD.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getEmail());
            ps.executeUpdate();

            return true;

        } catch (Exception e) {//exception

            e.printStackTrace();
        }

        return false;
    }
}