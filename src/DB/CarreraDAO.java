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
import Models.CarreraM;
import Models.MateriaM;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CarreraDAO extends SQLQuery {
    
    private static ArrayList<CarreraM> carreras = new ArrayList<>();
    private CarreraM carrera;

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public ArrayList<CarreraM> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<CarreraM> carreras) {
        this.carreras = carreras;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="public boolean cargarDato(CarreraM carrera)">
    public boolean cargarDato(CarreraM carrera) {  
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            this.datos = this.consulta.executeQuery();
            String sql = "INSERT INTO carrera (car_cod, car_nombre, car_duracion) VALUES (?,?,?)";
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement(sql);
            preparedStmt.setLong(1, carrera.getCodigo());
            preparedStmt.setString(2, carrera.getNombre());
            preparedStmt.setLong(3, carrera.getDuracion());
            preparedStmt.execute();
            this.desconectar();
            System.out.println(String.format("Se ha creado el dato de código %d correctamente", carrera.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido crear el dato de código %d", carrera.getCodigo()));
            return false;
        }

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean removerDato(CarreraM carrera) {  //Revisar, ya no estoy usando JTable">
    public boolean removerDato(CarreraM carrera) {  //Revisar, ya no estoy usando JTable
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("DELETE FROM carrera WHERE car_cod = ?");
            this.consulta.setLong(1, carrera.getCodigo());
            consulta.executeUpdate();

            this.desconectar();
            System.out.println(String.format("Se ha removido el dato de código %d correctamente", carrera.getCodigo()));
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido remover el dato de código %d", carrera.getCodigo()));
            return false;
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean editarDato(CarreraM carrera)   modifica el campo en la db">
    public boolean editarDato(CarreraM carrera) {    //modifica el campo en la db
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            PreparedStatement preparedStmt = (PreparedStatement) this.conn.prepareStatement("UPDATE carrera SET car_nombre=?, car_duracion=? WHERE car_cod=?");
            
            preparedStmt.setString(1, carrera.getNombre());
            preparedStmt.setLong(2, carrera.getDuracion());
            preparedStmt.setLong(3, carrera.getCodigo());
            preparedStmt.executeUpdate();
            
            this.desconectar();
            System.out.println(String.format("Se ha editado el dato de código %d correctamente", carrera.getCodigo()));
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(String.format("No se ha podido editar el dato de código %d", carrera.getCodigo()));
            return false;
        }

    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="public ArrayList<CarreraM> traerDatos() Devuelve todos los datos de la db y lo carga en this.carreras">
    public ArrayList<CarreraM> traerDatos() {    //busca los datos en la db
        try {
            this.carreras.clear();
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera");
            ResultSet resultados = consulta.executeQuery();
            while (resultados.next()) {
                carrera = new CarreraM();
                carrera.setCodigo(resultados.getLong(1));
                carrera.setNombre(resultados.getString(2));
                carrera.setDuracion(resultados.getLong(3));
                if (!this.carreras.contains(carrera)){
                    this.carreras.add(carrera);
                }    
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.carreras;

    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="public boolean verificarNombreUnico(CarreraM carrera) True si el nombre NO existe">
    public boolean verificarNombreUnico(CarreraM carrera) {  
        
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from carrera where car_cod=?");
            this.consulta.setLong(1, carrera.getCodigo());
            ResultSet resultados = consulta.executeQuery();
            if (resultados.next()) {
                return false;
            }
            this.desconectar();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }//</editor-fold>
    
    public boolean buscarCodigo(long car_cod){

        traerDatos();
        for(CarreraM car: this.carreras){
            if (car.getCodigo() == car_cod){
                return true;
            }
        }
        return false;

    }    
    public CarreraM buscarCarrera(long car_cod){
        traerDatos();
        for(CarreraM car: this.carreras){
            if (car.getCodigo() == car_cod){
                return car;
            }
        }
        return new CarreraM();
    }
    
    //CAMBIAR
    public boolean CarreraLibre(CarreraM carrera){
        try {
            this.conectar("localhost", "db_programacion2", "root", "mysql");
            this.consulta = this.conn.prepareStatement("select * from inscripcion where insc_car_cod=?");
            this.consulta.setLong(1, carrera.getCodigo());
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
