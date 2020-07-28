/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Models.MateriaM;
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
public class MateriaDAO extends SQLQuery {
        
    private static ArrayList<MateriaM> materias = new ArrayList<>();
    private MateriaM materia;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<MateriaM> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<MateriaM> materias) {
        this.materias = materias;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(MateriaM materia)">
    public boolean cargarDato(MateriaM materia) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO materia (mat_cod, mat_nombre, mat_profe_dni) VALUES (?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, materia.getCodigo());
            preparedStmt.setString(2, materia.getNombre());
            preparedStmt.setLong(3, materia.getProfeDni());
            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato de código %d correctamente", materia.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato de código %d", materia.getCodigo()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(MateriaM materia) { ">
    public boolean removerDato(MateriaM materia) {  //Revisar, ya no estoy usando JTable
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM materia WHERE mat_cod = ?");
            this.consulta.setLong(1, materia.getCodigo());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato de código %d correctamente", materia.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato de código %d", materia.getCodigo()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(MateriaM materia)   modifica el campo en la db">
    public boolean editarDato(MateriaM materia) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE materia SET mat_nombre=?, mat_profe_dni=? WHERE mat_cod=?");
            
            preparedStmt.setString(1, materia.getNombre());
            preparedStmt.setLong(2, materia.getProfeDni());
            preparedStmt.setLong(3, materia.getCodigo());

            preparedStmt.executeUpdate();
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato de código %d correctamente", materia.getCodigo()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato de código %d", materia.getCodigo()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<MateriaM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<MateriaM> traerDatos() {    //busca los datos en la db
        try {
            this.materias.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from materia");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                materia = new MateriaM();
                materia.setCodigo(resultados.getLong(1));
                materia.setNombre(resultados.getString(2));
                materia.setProfeDni(resultados.getLong(3));
                if (!this.materias.contains(materia)){
                    this.materias.add(materia);
                }    
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.materias;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarNombreUnico(MateriaM materia) True si el nombre NO existe">
    public boolean verificarUnicidad(MateriaM materia) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from materia where mat_cod=?");
            this.consulta.setLong(1, materia.getCodigo());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MateriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>
    
    public boolean buscarCodigo(long mat_cod){
        traerDatos();
        for(MateriaM mat: this.materias){
            if (mat.getCodigo() == mat_cod){
                return true;
            }
        }
        return false;

    }    
    public MateriaM buscarMateria(long mat_cod){
        traerDatos();
        for(MateriaM mat: this.materias){
            if (mat.getCodigo() == mat_cod){
                return mat;
            }
        }
        return new MateriaM();
    }
    public Set<String> traerProfeDni() {    //Busca en la db todos los códigos de materias    
        Set<String> codigoInscripcion = new HashSet<>();   //Mejor un set? para evitar repetidos. Recordar... no tiene indices
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select prof_dni from profesor");
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
    public boolean materiaLibre(MateriaM materia){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from cursado where cur_mat_cod=?");
            this.consulta.setLong(1, materia.getCodigo());
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
