package modelo;
// extiende de persona
public class Paciente extends Persona {
    //declaracion de variable
    private String direccion;
    //constructor
    public Paciente(int id, String nombre, String apellido, String telefono, String email, String direccion) {
        super(id, nombre, apellido, telefono, email);
        this.direccion = direccion;
    }
    //getters an setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    //muestra de los datos del modelo
    @Override
    public void mostrarDatos() {

        System.out.println(
                                    getId() + " - "
                                  + getNombre() + " "
                                  + getApellido() + " "
                                  + getEmail() + " "
                                  + getTelefono()
        );

    }
}
