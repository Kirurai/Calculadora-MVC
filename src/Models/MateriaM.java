/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.MateriaDAO;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class MateriaM {
    private long codigo;
    private String nombre;
    private long ProfeDni;
    private MateriaDAO DB = new MateriaDAO();
    
    public MateriaM() {
        
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

    public long getProfeDni() {
        return ProfeDni;
    }

    public void setProfeDni(long ProfeDni) {
        this.ProfeDni = ProfeDni;
    }

    public MateriaDAO getDB() {
        return DB;
    }

    public void setDB(MateriaDAO DB) {
        this.DB = DB;
    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(MateriaM materia){
        return DB.cargarDato(materia);
    }
    public boolean editarDato(MateriaM materia){
        return DB.editarDato(materia);
    }
    public boolean removerDato(MateriaM materia){
        return DB.removerDato(materia);
    }//</editor-fold>
    
    
    public boolean verificarUnicidad(MateriaM materia){
        return DB.verificarUnicidad(materia);
    }
    
    public boolean buscarCodigo(long mat_cod){
        return DB.buscarCodigo(mat_cod);
    }
    public MateriaM buscarMateria(long mat_cod){
        return DB.buscarMateria(mat_cod);
    }
    public Set<String> traerProfeDni(){
        return DB.traerProfeDni();
    }
    public boolean materiaLibre(MateriaM materia){
        return DB.materiaLibre(materia);
    }

}

