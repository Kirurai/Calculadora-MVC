/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Models.InscripcionM;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class InscripcionDAO extends SQLQuery {
    
    private static ArrayList<InscripcionM> inscripciones = new ArrayList<>();
    private InscripcionM inscripcion;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<InscripcionM> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(ArrayList<InscripcionM> inscripciones) {
        InscripcionDAO.inscripciones = inscripciones;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(CarreraM carrera)">
    public boolean cargarDato(InscripcionM inscripcion) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO inscripcion (insc_cod, insc_nombre, insc_fecha, insc_car_cod) VALUES (?,?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, inscripcion.getCodigo());
            preparedStmt.setString(2, inscripcion.getNombre());
            preparedStmt.setDate(3, inscripcion.getFecha());
            preparedStmt.setLong(4, inscripcion.getCodigoCarrera());

            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato con código %d correctamente", inscripcion.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato con código %d", inscripcion.getCodigo()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(CarreraM carrera) {  //Revisar, ya no estoy usando JTable">
    public boolean removerDato(InscripcionM inscripcion) {  //Revisar, ya no estoy usando JTable

        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM inscripcion WHERE insc_cod = ?");
            this.consulta.setLong(1, inscripcion.getCodigo());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato con código %d correctamente", inscripcion.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato con código %d", inscripcion.getCodigo()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(CarreraM carrera)   modifica el campo en la db">
    public boolean editarDato(InscripcionM inscripcion) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE inscripcion SET insc_nombre=?, insc_fecha=?, insc_car_cod=? WHERE insc_cod=?");
            preparedStmt.setString(1, inscripcion.getNombre());
            preparedStmt.setDate(2, inscripcion.getFecha());
            preparedStmt.setLong(3, inscripcion.getCodigoCarrera());
            preparedStmt.setLong(4, inscripcion.getCodigo());
            
            preparedStmt.executeUpdate();
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato con código %d correctamente", inscripcion.getCodigo()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato con código %d", inscripcion.getCodigo()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<CarreraM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<InscripcionM> traerDatos() {    //busca los datos en la db
        try {
            this.inscripciones.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from inscripcion");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                inscripcion = new InscripcionM();
                inscripcion.setCodigo(resultados.getLong(1));
                inscripcion.setNombre(resultados.getString(2));
                inscripcion.setFecha(resultados.getDate(3));
                inscripcion.setCodigoCarrera(resultados.getLong(4));
                //  System.out.println(resultados.getLong(1));
                if (!InscripcionDAO.inscripciones.contains(inscripcion)){
                    //System.out.println(String.format("Codigo: %d, Nombre: %s, Duracion: %d\tCargando!", carrera.getCodigo(), carrera.getNombre(), carrera.getDuracion()));
                    InscripcionDAO.inscripciones.add(inscripcion);
                }    
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.inscripciones;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarNombreUnico(CarreraM carrera) True si el nombre NO existe">
    public boolean verificarUnicidad(InscripcionM inscripcion) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from inscripcion where insc_cod=?");
            this.consulta.setLong(1, inscripcion.getCodigo());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                System.out.println(inscripcion.getCodigo() + resultados.getLong(1));
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>
    
    public boolean buscarCodigo(long insc_cod){

        traerDatos();
        for(InscripcionM alu: this.inscripciones){
            if (alu.getCodigo() == insc_cod){
                return true;
            }
        }
        return false;

    }    
    public InscripcionM buscarInscripcion(long insc_cod){
        traerDatos();
        for(InscripcionM alu: this.inscripciones){
            if (alu.getCodigo() == insc_cod){
                return alu;
            }
        }
        return new InscripcionM();
    }
        public Set<String> traerCodigoCarrera() {    //Busca en la db todos los códigos de materias    
        Set<String> codigoInscripcion = new HashSet<>();   //Mejor un set? para evitar repetidos. Recordar... no tiene indices
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select car_cod from carrera");
            ResultSet resultados = consulta.executeQuery();
            codigoInscripcion.add("");
            while (resultados.next()) {
                codigoInscripcion.add(Long.toString(resultados.getLong(1)));
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InscripcionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigoInscripcion;
    }
    public boolean inscripcionLibre(InscripcionM inscripcion){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from alumno where alu_insc_cod=?");
            this.consulta.setLong(1, inscripcion.getCodigo());
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
