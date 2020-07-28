/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.CursadoM;
import Models.PrincipalM;
import Views.CursadoV;
import Views.PrincipalV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CursadoC implements ActionListener{
    private CursadoV vistaActual;
    private CursadoM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public CursadoC(CursadoM modelo, CursadoV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de cursados");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
        CompletarCBAluDni();
        CompletarCBCodigo();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new CursadoM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>
            }else if(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem()).isEmpty() ||
                     String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem()).isEmpty()){
                System.out.println("Por lo menos un campo necsario está vacío");
                JOptionPane.showMessageDialog(null, "El Dni del alumno y el código de materia son obligatorios");
            }else{
                  //<editor-fold defaultstate="collapsed" desc="Declaración parcial del objeto, falta la nota que no se necesita para cargar o eliminar">
                this.modeloActual.setAluDni(Long.parseLong(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem())));
                this.modeloActual.setCodigo(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem())));
                //</editor-fold>
                if(ae.getSource().equals(this.vistaActual.getBtnEliminar())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                    System.out.println("Intentando eliminar un dato");
                    if(this.modeloActual.buscarCodigo(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem())))&&
                            this.modeloActual.buscarAluDni(Long.parseLong(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem())))){
                    //<editor-fold defaultstate="collapsed" desc="Declaración del objeto">
                    this.modeloActual.setAluDni(Long.parseLong(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem())));
                    this.modeloActual.setCodigo(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem())));
                    //</editor-fold>
                    
                        if(this.modeloActual.removerDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Cursada Eliminada correctamente");
                            limpiaDatos();
                            System.out.println("Ya limpió");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la cursada. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                    }
                    //</editor-fold>

                }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                    System.out.println("Intentando cargar un dato");
                    if(!this.modeloActual.buscarCodigo(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem()))) &&
                            !this.modeloActual.buscarAluDni(Long.parseLong(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem())))){
                        JOptionPane.showMessageDialog(null, "la combinación de codigo y alumno ingresado no existe en la base de datos");
                    }else{
                        cargarDato(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigo().getSelectedItem())), Long.parseLong(String.valueOf(this.vistaActual.getCBAluDni().getSelectedItem())));
                        ArrayList<String> datosMixtos = this.modeloActual.traerDatos(this.modeloActual);
                        JOptionPane.showMessageDialog(null, String.format("Alumno: %s\nMateria: %s\nNota: %s", datosMixtos.get(1), datosMixtos.get(2), datosMixtos.get(0)));
                    }
                    //</editor-fold>

                }else{
                    //<editor-fold defaultstate="collapsed" desc="Declaración final del objeto">
                    String nota = this.vistaActual.getTxtNota().getText();
                    //</editor-fold>
                    
                    if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                        System.out.println("Intentando crear un dato");
                        if(!nota.isEmpty()){
                            this.modeloActual.setNota(Long.parseLong(nota));
                        }

                        if (modeloActual.verificarUnicidad(modeloActual)) {
                            if (this.modeloActual.cargarDato(modeloActual)) {   //todo en orden? cargamos el alumno
                                JOptionPane.showMessageDialog(null, "Cursada cargada correctamente");
                            }
                            limpiaDatos();
                        } else {
                            JOptionPane.showMessageDialog(null, "Cursados existente");
                        }
                    //</editor-fold>

                    }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                        System.out.println("Intentando editar un dato");
                        if(this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){ //Busca por las dudas que el código sea incorrecto
                            if(this.modeloActual.editarDato(modeloActual)){
                                JOptionPane.showMessageDialog(null, "Cursada editada correctamente");
                                limpiaDatos();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Hubo un error al editar la Cursada. Vuelva a intentarlo.(Fijese que el Dni y el código sea correcto)");
                        }
                        //</editor-fold>

                    }
                }
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() {   //limpia los campos">
    public void limpiaDatos() {   
        CompletarCBAluDni();
        CompletarCBCodigo();
        this.vistaActual.getTxtNota().setText("");
        this.vistaActual.getCBCodigo().setSelectedItem(0);
        this.vistaActual.getCBAluDni().setSelectedItem(0);


    }//</editor-fold>
    public void cargarDato(long codigo, long dni){
        this.vistaActual.getTxtNota().setText(Long.toString(this.modeloActual.buscarCursado(dni, codigo).getNota()));
    }
    public void abrirVentanaPrincipal(){
        PrincipalM mod = new PrincipalM();
        PrincipalV vis = new PrincipalV();
        PrincipalC con = new PrincipalC(mod, vis);
        this.vistaActual.dispose();

    }
    public void CompletarCBCodigo(){ 
        this.vistaActual.getCBCodigo().removeAllItems();
        Set<String> codMat = this.modeloActual.traerCodigo();
        Iterator<String> dniIterator = codMat.iterator();
        while(dniIterator.hasNext()){
            this.vistaActual.getCBCodigo().addItem(dniIterator.next());
        }
    }
    public void CompletarCBAluDni(){ 
        this.vistaActual.getCBAluDni().removeAllItems();
        //this.vistaActual.getCBAluDni().addItem("");
        Set<String> aluDni = this.modeloActual.traerAluDni();
        Iterator<String> dniIterator = aluDni.iterator();
        while(dniIterator.hasNext()){
            this.vistaActual.getCBAluDni().addItem(dniIterator.next());
        }
    }
    public boolean comprobarTiempo(long tiempoActual){
        if(this.tiempoDeUltimoEvento != tiempoActual){
                this.tiempoDeUltimoEvento = tiempoActual;
                return true;
        }
        return false;
    }
}
