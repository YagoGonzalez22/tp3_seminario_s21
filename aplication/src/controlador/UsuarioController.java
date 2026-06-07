package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
//controlador de usuario
//intermediarios entre las vistas y la capa de acceso a datos
public class UsuarioController {
    //declaramos usuariodao
    private UsuarioDAO usuarioDAO;
    //constructor
    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();

    }
   //metodo login
    public Usuario login(String usuario, String password) {
        return usuarioDAO.login(usuario, password);
    }
    //metodo registrar usuario
    public boolean registrarUsuario(Usuario usuario) {
        return usuarioDAO.guardarUsuario(usuario);
    }
}