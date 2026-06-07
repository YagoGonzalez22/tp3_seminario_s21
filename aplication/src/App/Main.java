
package App;

import controlador.*;

import modelo.Usuario;

import Vista.*;

public class Main {

    public static void main(String[] args) {

        //contriollers/controladores Los controladores actúan como intermediarios
        // entre las vistas y la capa de acceso a datos.
        //controlador de usuarios
        UsuarioController usuarioController = new UsuarioController();
        //controlador de paciente
        PacienteController pacienteController = new PacienteController();
        //controlador de medico
        MedicoController medicoController = new MedicoController();
        //controlador de turnos
        TurnoController turnoController = new TurnoController();
        //declaramos la vistas desde usuario controler donde determinamos un usuario como administrador
        // user: admin pass: 1234
        VistaLogin login = new VistaLogin(usuarioController);
        //llamamos iniciar sesion para que la persona se logee
        Usuario usuarioLogueado = login.iniciarSesion();
         //verificacion del login
        if (usuarioLogueado != null) {
            //login
            MenuPrincipal menu =
                    new MenuPrincipal(usuarioLogueado, pacienteController, medicoController, turnoController);
            menu.iniciar();
        } else {
            System.out.println(
                    "Acceso denegado."
            );
        }

    }
}