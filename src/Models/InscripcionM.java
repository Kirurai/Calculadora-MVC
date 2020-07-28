/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.InscripcionDAO;
import java.sql.Date;
import java.util.Set;



/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class InscripcionM {
    private long codigo;
    private String nombre;
    private long CodigoCarrera;
    private Date fecha;
    private InscripcionDAO DB = new InscripcionDAO();
    
    public InscripcionM() {
        
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

    public long getCodigoCarrera() {
        return CodigoCarrera;
    }

    public void setCodigoCarrera(long CodigoCarrera) {
        this.CodigoCarrera = CodigoCarrera;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public InscripcionDAO getDB() {
        return DB;
    }

    public void setDB(InscripcionDAO DB) {
        this.DB = DB;
    }//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(InscripcionM inscripcion){
        return DB.cargarDato(inscripcion);
    }
    public boolean editarDato(InscripcionM inscripcion){
        return DB.editarDato(inscripcion);
    }
    public boolean removerDato(InscripcionM inscripcion){
        return DB.removerDato(inscripcion);
    }//</editor-fold>
    
    
    public boolean verificarUnicidad(InscripcionM inscripcion){
        return DB.verificarUnicidad(inscripcion);
    }
    
    public boolean buscarCodigo(long car_cod){
        return DB.buscarCodigo(car_cod);
    }
    public InscripcionM buscarInscripcion(long car_cod){
        return DB.buscarInscripcion(car_cod);
    }
    public Set<String> traerCodigoCarrera(){
        return DB.traerCodigoCarrera();
    }
    public boolean inscripcionLibre(InscripcionM inscripcion){
        return DB.inscripcionLibre(inscripcion);
    }

}
