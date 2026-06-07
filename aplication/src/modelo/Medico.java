package modelo;
//medico extiende de persona
public class Medico extends Persona{
    //declaracion de variable
    private String especialidad;
      //constructor
    public Medico(int id, String nombre, String apellido, String telefono, String email, String especialidad) {
        super(id, nombre, apellido, telefono, email);
        this.especialidad = especialidad;
    }
  //getters an setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
 //muestra de los datos del modelo
    @Override
    public void mostrarDatos() {

        System.out.println(
                        + getId() + " "
                        + getNombre() + " "
                        + getApellido() + " "
                        + getTelefono() + " "
                        + " - "
                        + especialidad
        );

    }
}
