/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.CarreraDAO;
import java.util.ArrayList;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CarreraM {
    private long codigo;
    private String nombre;
    private long duracion;
    private CarreraDAO DB = new CarreraDAO();
    
    public CarreraM() {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    public CarreraDAO getDB() {
        return DB;
    }

    public void setDB(CarreraDAO DB) {
        this.DB = DB;
    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(CarreraM carrera){
        return DB.cargarDato(carrera);
    }
    public boolean editarDato(CarreraM carrera){
        return DB.editarDato(carrera);
    }
    public boolean removerDato(CarreraM carrera){
        return DB.removerDato(carrera);
    }//</editor-fold>
    
    
    public boolean verificarNombreUnico(CarreraM carrera){
        return DB.verificarNombreUnico(carrera);
    }
    
    public boolean buscarCodigo(long car_cod){
        return DB.buscarCodigo(car_cod);
    }
    public CarreraM buscarCarrera(long car_cod){
        return DB.buscarCarrera(car_cod);
    }
    public boolean carreraLibre(CarreraM carrera){
        return DB.CarreraLibre(carrera);
    }

}
