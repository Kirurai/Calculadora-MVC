/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Models.ProfesorM;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class ProfesorDAO extends SQLQuery {
    
    private static ArrayList<ProfesorM> profesores = new ArrayList<>();
    private ProfesorM profesor;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<ProfesorM> getProfesores() {
        return profesores;
    }

    public void setProfesores(ArrayList<ProfesorM> profesores) {
        this.profesores = profesores;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(ProfesorM profesor)">
    public boolean cargarDato(ProfesorM profesor) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO profesor (prof_dni, prof_nombre, prof_apellido, prof_fec_nac, prof_domicilio, prof_telefono) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, profesor.getDni());
            preparedStmt.setString(2, profesor.getNombre());
            preparedStmt.setString(3, profesor.getApellido());
            preparedStmt.setDate(4, profesor.getFechaNac());
            preparedStmt.setString(5, profesor.getDomicilio());
            preparedStmt.setString(6, profesor.getTelefono());
            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato con DNI %d correctamente", profesor.getDni()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato con DNI %d", profesor.getDni()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(ProfesorM profesor) {">
    public boolean removerDato(ProfesorM profesor) {     
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM profesor WHERE prof_dni = ?");
            this.consulta.setLong(1, profesor.getDni());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato con DNI %d correctamente", profesor.getDni()));
            return true;

        } catch (ClassNotFoundException | SQLException  ex) {

            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato con DNI %d", profesor.getDni()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(ProfesorM profesor)   modifica el campo en la db">
    public boolean editarDato(ProfesorM profesor) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE profesor SET prof_nombre=?, prof_apellido=?, prof_fec_nac=?, prof_domicilio=?, prof_telefono=? WHERE prof_dni=?");
            
            preparedStmt.setLong(6, profesor.getDni());
            preparedStmt.setString(1, profesor.getNombre());
            preparedStmt.setString(2, profesor.getApellido());
            preparedStmt.setDate(3, profesor.getFechaNac());
            preparedStmt.setString(4, profesor.getDomicilio());
            preparedStmt.setString(5, profesor.getTelefono());


            preparedStmt.executeUpdate();
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato con DNI %d correctamente", profesor.getDni()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato con DNI %d", profesor.getDni()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<CarreraM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<ProfesorM> traerDatos() {    //busca los datos en la db
        try {
            this.profesores.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from profesor");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                profesor = new ProfesorM();
                profesor.setDni(resultados.getLong(1));
                profesor.setNombre(resultados.getString(2));
                profesor.setApellido(resultados.getString(3));
                profesor.setFechaNac(resultados.getDate(4));
                profesor.setDomicilio(resultados.getString(5));
                profesor.setTelefono(resultados.getString(6));
                if (!this.profesores.contains(profesor)){
                    this.profesores.add(profesor);
                }    
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.profesores;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarUnicidad(CarreraM carrera) True si el nombre NO existe">
    public boolean verificarUnicidad(ProfesorM profesor) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from profesor where prof_dni=?");
            this.consulta.setLong(1, profesor.getDni());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProfesorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>
    
    public boolean buscarDni(long prof_dni){

        traerDatos();
        for(ProfesorM prof: this.profesores){
            if (prof.getDni() == prof_dni){
                return true;
            }
        }
        return false;

    }    
    public ProfesorM buscarProfesor(long prof_dni){
        traerDatos();
        for(ProfesorM prof: this.profesores){
            if (prof.getDni() == prof_dni){
                return prof;
            }
        }
        return new ProfesorM();
    }
    public boolean profesorLibre(ProfesorM profesor){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from materia where mat_profe_dni=?");
            this.consulta.setLong(1, profesor.getDni());
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
