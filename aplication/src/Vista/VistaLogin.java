package Vista;

import controlador.UsuarioController;
import modelo.Usuario;

import java.util.Scanner;
 //vista del login
public class VistaLogin {
     //declaracion de variable
    private UsuarioController usuarioController;
    private Scanner teclado;
     //constructor
    public VistaLogin(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        this.teclado = new Scanner(System.in);
    }
   //menu
    public Usuario iniciarSesion() {

        System.out.println("\n===== INICIO DE SESION =====");
        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Contraseña: ");
        String password = teclado.nextLine();

        Usuario usuarioLogueado = usuarioController.login(usuario, password);

        if (usuarioLogueado != null) {
            System.out.println("\nBienvenido " + usuarioLogueado.getNombreUsuario());

            return usuarioLogueado;
        }

        System.out.println("\nUsuario o contraseña incorrectos.");

        return null;
    }
}