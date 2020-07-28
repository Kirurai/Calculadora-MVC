/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DB.CursadoDAO;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CursadoM {
    private long codigo;
    private long aluDni;
    private long nota;
    private CursadoDAO DB = new CursadoDAO();
    
    public CursadoM() {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getAluDni() {
        return aluDni;
    }

    public void setAluDni(long AluDni) {
        this.aluDni = AluDni;
    }

    public long getNota() {
        return nota;
    }

    public void setNota(long Nota) {
        this.nota = Nota;
    }
    
    public CursadoDAO getDB() {
        return DB;
    }

    public void setDB(CursadoDAO DB) {
        this.DB = DB;
    }//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Carga, edición y eliminación de datos en la DB">
    public boolean cargarDato(CursadoM cursado){
        return DB.cargarDato(cursado);
    }
    public boolean editarDato(CursadoM cursado){
        return DB.editarDato(cursado);
    }
    public boolean removerDato(CursadoM cursado){
        return DB.removerDato(cursado);
    }//</editor-fold>
    
    
    public ArrayList<String> traerDatos(CursadoM cursado){
        return DB.traerDatos(cursado);
    }
    
    public boolean verificarUnicidad(CursadoM cursado){
        return DB.verificarUnicidad(cursado);
    }
    
    public boolean buscarCodigo(long mat_cod){
        return DB.buscarCodigo(mat_cod);
    }
    public boolean buscarAluDni(long mat_alu_dni){
        return DB.buscarAluDni(mat_alu_dni);
    }
    public Set<String> traerCodigo(){
        return DB.traerCodigo();
    }
    public Set<String> traerAluDni(){
        return DB.traerAluDni();
    }
     //No lo uso? Si lo uso, lloro
    public CursadoM buscarCursado(long cur_alu_dni, long cur_cod){
        return DB.buscarCursado(cur_alu_dni, cur_cod);
    }


}
