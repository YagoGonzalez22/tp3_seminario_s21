package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//coneccion de la base de datos
public class ConexionBD {
    public static Connection conectar() {
        try {//manejo de exepcion
            //url
            Class.forName("com.mysql.cj.jdbc.Driver");
            //url localhost
            String url = "jdbc:mysql://localhost:3306/turnos_medicos";
            String usuario = "root"; //usuario
            String password = "135792468"; //password

            // conexión
            return DriverManager.getConnection(url, usuario, password);
          //excepcion captura en caso de no tener el archivo jar
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: El archivo JAR del driver de MySQL no está en el proyecto");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            //error en la url captura de exception
            System.err.println("el servidor MySQL está apagado");
            e.printStackTrace();
            return null;
        }
    }
}