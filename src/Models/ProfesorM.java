/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.ProfesorDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class ProfesorM {
    
    private long dni;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String domicilio;
    private String telefono;
    private ProfesorDAO DB = new ProfesorDAO();
    
    public ProfesorM() {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }


    public ProfesorDAO getDB() {
        return DB;
    }

    public void setDB(ProfesorDAO DB) {
        this.DB = DB;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }//</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(ProfesorM profesor){
        return DB.cargarDato(profesor);
    }
    public boolean editarDato(ProfesorM profesor){
        return DB.editarDato(profesor);
    }
    public boolean removerDato(ProfesorM profesor){
        return DB.removerDato(profesor);
    }//</editor-fold>
    
    
    public boolean verificarUnicidad(ProfesorM profesor){
        return DB.verificarUnicidad(profesor);
    }
    
    public boolean buscarDni(long prof_dni){
        return DB.buscarDni(prof_dni);
    }
    public ProfesorM buscarProfesor(long prof_dni){
        return DB.buscarProfesor(prof_dni);
    }
    public boolean profesorLibre(ProfesorM profesor){
        //System.out.println("llegó");
        return DB.profesorLibre(profesor);
    }


}
