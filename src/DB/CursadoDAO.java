/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.CursadoM;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CursadoDAO extends SQLQuery {
    
    private static ArrayList<String> datosCursados = new ArrayList<>(); //Parece que tiene algo contra los long? HashMap<Long, ArrayList<Long>>
    private CursadoM cursado;
    private PreparedStatement consultarAlumno;
    private PreparedStatement consultarMateria;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<String> getCursados() {
        return datosCursados;
    }

    public void setCursados(ArrayList<String> datosCursados) {
        CursadoDAO.datosCursados = datosCursados;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(CursadoM cursado)">
    public boolean cargarDato(CursadoM cursado) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO cursado (cur_alu_dni, cur_mat_cod, cur_nota) VALUES (?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, cursado.getAluDni());
            preparedStmt.setLong(2, cursado.getCodigo());
            preparedStmt.setLong(3, cursado.getNota());
            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato de código %d correctamente", cursado.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato de código %d", cursado.getCodigo()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(CursadoM cursado)">
    public boolean removerDato(CursadoM cursado) {      
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM cursado WHERE cur_alu_dni=? AND cur_mat_cod=?");
            this.consulta.setLong(1, cursado.getAluDni());
            this.consulta.setLong(2, cursado.getCodigo());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato de código %d correctamente", cursado.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato de código %d", cursado.getCodigo()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(CarreraM materia)   modifica el campo en la db">
    public boolean editarDato(CursadoM cursado) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE cursado SET cur_nota=? WHERE cur_alu_dni=? AND cur_mat_cod=?");
            preparedStmt.setLong(1, cursado.getNota());
            preparedStmt.setLong(2, cursado.getAluDni());
            preparedStmt.setLong(3, cursado.getCodigo()); 

            preparedStmt.executeUpdate();
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato de código %d correctamente", cursado.getCodigo()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato de código %d", cursado.getCodigo()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<CarreraM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<String> traerDatos(CursadoM cursado) {    //busca los datos en la db //Se cambia a Hashmap necesita, Key Dni Value, Vallue ArrayList de cursados //Más que traer actualiza lso datos....
        try {
            this.datosCursados.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select cur_nota from cursado where cur_alu_dni=? AND cur_mat_cod=?");
            consulta.setLong(1, cursado.getAluDni());
            consulta.setLong(2, cursado.getCodigo());
            ResultSet resultadosConsulta = consulta.executeQuery();
            
            this.consultarAlumno = this.conn.prepareStatement("select alu_nombre from alumno where alu_dni=?");
            consultarAlumno.setLong(1, cursado.getAluDni());
            ResultSet resultadosAlumno = consultarAlumno.executeQuery();
            this.consultarMateria = this.conn.prepareStatement("select mat_nombre from materia where mat_cod=?");
            consultarMateria.setLong(1, cursado.getCodigo());
            ResultSet resultadosMateria = consultarMateria.executeQuery();
            
            if(resultadosConsulta.next() && resultadosMateria.next() && resultadosAlumno.next()){
                datosCursados.add(Long.toString(resultadosConsulta.getLong(1)));
                datosCursados.add(resultadosAlumno.getString(1));
                datosCursados.add(resultadosMateria.getString(1));
            }

            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CursadoDAO.datosCursados;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarUnicidad(CursadoM cursado) True si la PK NO existe">
    public boolean verificarUnicidad(CursadoM cursado) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from cursado where cur_mat_cod=? AND cur_alu_dni=?");
            this.consulta.setString(1, Long.toString(cursado.getCodigo()));
            this.consulta.setString(2, Long.toString(cursado.getAluDni()));
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>

    public CursadoM buscarCursado(long cur_alu_dni, long cur_mat_cod){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from cursado");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                if (resultados.getLong(2) == cur_mat_cod && resultados.getLong(1) == cur_alu_dni){
                    CursadoM provisorio = new CursadoM();
                    provisorio.setCodigo(cur_mat_cod);
                    provisorio.setAluDni(cur_alu_dni);
                    provisorio.setNota(resultados.getLong(3)); 
                    return provisorio;
                }
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new CursadoM();
    }
    
    public Set<String> traerAluDni() {    //Busca en la db todos los DNI de alumnos
        Set<String> dniAlumno = new HashSet<>();   //Mejor un set? para evitar repetidos. Recordar... no tiene indices
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select alu_dni from alumno");
            ResultSet resultados = consulta.executeQuery();
            dniAlumno.add("");
            while (resultados.next()) {
                dniAlumno.add(Long.toString(resultados.getLong(1)));
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dniAlumno;
    }
    
    public Set<String> traerCodigo() {    //Busca en la db todos los códigos de materias    
        Set<String> codigoMateria = new HashSet<>();   //Mejor un set? para evitar repetidos. Recordar... no tiene indices
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select mat_cod from materia");
            ResultSet resultados = consulta.executeQuery();
            codigoMateria.add("");
            while (resultados.next()) {
                codigoMateria.add(Long.toString(resultados.getLong(1)));
            }
            this.desconectar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CursadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigoMateria;
    }
    
    public boolean buscarCodigo(long mat_cod){
        return traerCodigo().contains(Long.toString(mat_cod));
    }
    public boolean buscarAluDni(long mat_alu_dni){
        return traerAluDni().contains(Long.toString(mat_alu_dni));
    }
}
