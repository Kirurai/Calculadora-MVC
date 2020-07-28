/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Models.AlumnoM;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class AlumnoDAO extends SQLQuery {
    
    private static ArrayList<AlumnoM> alumnos = new ArrayList<>();
    private AlumnoM alumno;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<AlumnoM> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<AlumnoM> alumnos) {
        AlumnoDAO.alumnos = alumnos;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(AlumnoM alumno)">
    public boolean cargarDato(AlumnoM alumno) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO alumno (alu_dni, alu_nombre, alu_apellido, alu_fec_nac, alu_domicilio, alu_telefono, alu_insc_cod) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, alumno.getDni());
            preparedStmt.setString(2, alumno.getNombre());
            preparedStmt.setString(3, alumno.getApellido());
            preparedStmt.setDate(4, alumno.getFechaNac());
            preparedStmt.setString(5, alumno.getDomicilio());
            preparedStmt.setString(6, alumno.getTelefono());
            preparedStmt.setLong(7, alumno.getInscripcion());
            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato con DNI %d correctamente", alumno.getDni()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato con DNI %d", alumno.getDni()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(AlumnoM alumno) ">
    public boolean removerDato(AlumnoM alumno) {
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM alumno WHERE alu_dni = ?");
            this.consulta.setLong(1, alumno.getDni());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato con DNI %d correctamente", alumno.getDni()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato con DNI %d", alumno.getDni()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(AlumnoM alumno)   modifica el campo en la db">
    public boolean editarDato(AlumnoM alumno) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE alumno SET alu_nombre=?, alu_apellido=?, alu_fec_nac=?, alu_domicilio=?, alu_telefono=?, alu_insc_cod=? WHERE alu_dni=?");
            
            
            preparedStmt.setString(1, alumno.getNombre());
            preparedStmt.setString(2, alumno.getApellido());
            preparedStmt.setDate(3, alumno.getFechaNac());
            preparedStmt.setString(4, alumno.getDomicilio());
            preparedStmt.setString(5, alumno.getTelefono());
            preparedStmt.setLong(6, alumno.getInscripcion());
            preparedStmt.setLong(7, alumno.getDni());

            preparedStmt.executeUpdate();
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato con DNI %d correctamente", alumno.getDni()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato con DNI %d", alumno.getDni()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<AlumnoM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<AlumnoM> traerDatos() {    //busca los datos en la db
        try {
            this.alumnos.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                alumno = new AlumnoM();
                alumno.setDni(resultados.getLong(1));
                alumno.setNombre(resultados.getString(2));
                alumno.setApellido(resultados.getString(3));
                alumno.setFechaNac(resultados.getDate(4));
                alumno.setDomicilio(resultados.getString(5));
                alumno.setTelefono(resultados.getString(6));
                alumno.setInscripcion(resultados.getLong(7));
                if (!AlumnoDAO.alumnos.contains(alumno)){
                    AlumnoDAO.alumnos.add(alumno);
                }    
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.alumnos;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarNombreUnico(AlumnoM alumno) True si el nombre NO existe">
    public boolean verificarUnicidad(AlumnoM alumno) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno where alu_dni=?");
            this.consulta.setLong(1, alumno.getDni());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>
    
    public boolean buscarCodigo(long alu_dni){
        traerDatos();
        for(AlumnoM alu: this.alumnos){
            if (alu.getDni() == alu_dni){
                return true;
            }
        }
        return false;

    }   
    
    public AlumnoM buscarAlumno(long alu_dni){
        traerDatos();
        for(AlumnoM alu: this.alumnos){
            if (alu.getDni() == alu_dni){
                return alu;
            }
        }
        return new AlumnoM();
    }
    public Set<String> traerInscripcion() {    //Busca en la db todos los códigos de materias    
        Set<String> codigoInscripcion = new HashSet<>();   //Mejor un set? para evitar repetidos. Recordar... no tiene indices
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select insc_cod from inscripcion");
            ResultSet resultados = consulta.executeQuery();
            codigoInscripcion.add("");
            while (resultados.next()) {
                codigoInscripcion.add(Long.toString(resultados.getLong(1)));
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigoInscripcion;
    }
    public boolean alumnoLibre(AlumnoM alumno){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from cursado where cur_alu_dni=?");
            this.consulta.setLong(1, alumno.getDni());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return true;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}