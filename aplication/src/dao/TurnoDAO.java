package dao;

import modelo.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TurnoDAO {
      //metodo turno diponible
    public boolean turnoDisponible(int idMedico, LocalDate fecha, LocalTime hora) {
        //insetamos turno
        String sql = "SELECT * FROM turnos " + "WHERE id_medico = ? " + "AND fecha = ? " + "AND hora = ? " + "AND estado = 'RESERVADO'";
        try {//excepciones
            //coneccion y jquery
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idMedico);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(hora));

            ResultSet rs = ps.executeQuery();

            return !rs.next();
        } catch (Exception e) {//excepciones
            e.printStackTrace();
        }
        return false;
    }
   //guarmados turno
    public boolean guardarTurno(Turno turno) {
        //insetamos turno
        String sql = "INSERT INTO turnos " + "(id_paciente,id_medico,fecha,hora,estado) " + "VALUES (?,?,?,?,?)";
        try {//coneccion

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, turno.getPaciente().getId());

            ps.setInt(2, turno.getMedico().getId());
            ps.setDate(3, Date.valueOf(turno.getFecha()));
            ps.setTime(4, Time.valueOf(turno.getHora()));

            ps.setString(5, turno.getEstado());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {//exception
            e.printStackTrace();
        }
        return false;
    }
    //cancelamos turnos
    public boolean cancelarTurno(int idTurno) {
       //actualizamos el estado de los turnos jsql
        String sql = "UPDATE turnos " + "SET estado = 'CANCELADO' " + "WHERE id_turno = ?";

        try {//exception
            //coneccion base de datos
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idTurno);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {//exception
            e.printStackTrace();
        }

        return false;
    }
        //listado
    public ArrayList<Turno> listarTurnos() {

        ArrayList<Turno> turnos = new ArrayList<>();
        //listado de los datos
        String sql = "SELECT * " + "FROM turnos t " + "INNER JOIN pacientes p " + "ON t.id_paciente = p.id_paciente " + "INNER JOIN medicos m " + "ON t.id_medico = m.id_medico";

        try {

            Connection con = ConexionBD.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
              //listado de cada uno de los datos junto a los turnos
                Paciente paciente =
                        new Paciente(
                                rs.getInt("id_paciente"),
                                rs.getString("p.nombre"),
                                rs.getString("p.apellido"),
                                rs.getString("telefono"),
                                rs.getString("p.email"),
                                rs.getString("direccion")
                        );

                Medico medico =
                        new Medico(
                                rs.getInt("id_medico"),
                                rs.getString("m.nombre"),
                                rs.getString("m.apellido"),
                                rs.getString("m.telefono"),
                                rs.getString("m.email"),
                                rs.getString("especialidad")
                        );

                Turno turno =
                        new Turno(
                                rs.getInt("id_turno"),
                                paciente,
                                medico,
                                rs.getDate("fecha").toLocalDate(),
                                rs.getTime("hora").toLocalTime(),
                                rs.getString("estado")
                        );

                turnos.add(turno);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return turnos;
    }

    public Turno buscarTurno(int idTurno) {

        String sql =
                "SELECT * " +
                        "FROM turnos t " +
                        "INNER JOIN pacientes p ON t.id_paciente = p.id_paciente " +
                        "INNER JOIN medicos m ON t.id_medico = m.id_medico " +
                        "WHERE t.id_turno = ?";

        try {

            Connection con =
                    ConexionBD.conectar();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idTurno);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                Paciente paciente =
                        new Paciente(
                                rs.getInt("id_paciente"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("telefono"),
                                rs.getString("email"),
                                rs.getString("direccion")
                        );

                Medico medico =
                        new Medico(
                                rs.getInt("id_medico"),
                                rs.getString("m.nombre"),
                                rs.getString("m.apellido"),
                                rs.getString("m.telefono"),
                                rs.getString("m.email"),
                                rs.getString("especialidad")
                        );

                return new Turno(
                        rs.getInt("id_turno"),
                        paciente,
                        medico,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime(),
                        rs.getString("estado")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}