/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.AlumnoDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class AlumnoM {
    
    private long dni;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String domicilio;
    private String telefono;
    private long inscripcion;
    private AlumnoDAO DB = new AlumnoDAO();
    
    public AlumnoM() {
        
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

    public long getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(long inscripcion) {
        this.inscripcion = inscripcion;
    }

    public AlumnoDAO getDB() {
        return DB;
    }

    public void setDB(AlumnoDAO DB) {
        this.DB = DB;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }//</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(AlumnoM alumno){
        return DB.cargarDato(alumno);
    }
    public boolean editarDato(AlumnoM alumno){
        return DB.editarDato(alumno);
    }
    public boolean removerDato(AlumnoM alumno){
        return DB.removerDato(alumno);
    }//</editor-fold>
    
    
    public boolean verificarUnicidad(AlumnoM alumno){
        return DB.verificarUnicidad(alumno);
    }
    
    public boolean buscarDni(long alu_dni){
        return DB.buscarCodigo(alu_dni);
    }
    public AlumnoM buscarAlumno(long alu_dni){
        return DB.buscarAlumno(alu_dni);
    }
    public Set<String> traerInscripcion(){
        return DB.traerInscripcion();
    }
    public boolean alumnoLibre(AlumnoM alumno){
        return DB.alumnoLibre(alumno);
    }

}
