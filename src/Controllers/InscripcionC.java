/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.InscripcionM;
import Models.PrincipalM;
import Views.InscripcionV;
import Views.PrincipalV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class InscripcionC implements ActionListener{
    private InscripcionV vistaActual;
    private InscripcionM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public InscripcionC(InscripcionM modelo, InscripcionV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de inscripciones");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
        Calendar c = Calendar.getInstance();
        this.vistaActual.getJDFecha().setDate(c.getTime());
        completarCBCodigoCarrera();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new InscripcionM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>          
            }else if(this.vistaActual.getTxtCodigo().getText().isEmpty()){
                System.out.println("El campo Codigo es obligatorio para cualquier operación");
                JOptionPane.showMessageDialog(null, "El código de inscripción es obligatorio para cualquier operación");
            }else{
                if(ae.getSource().equals(this.vistaActual.getBtnEliminar())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                    System.out.println("Intentando eliminar un dato");
                    if(this.modeloActual.buscarCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()))){
                        modeloActual = modeloActual.buscarInscripcion(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                        if(this.modeloActual.inscripcionLibre(this.modeloActual)){
                            JOptionPane.showMessageDialog(null, "No puede borrar una inscripción que le pertenezca a un alumno");
                        }else if(this.modeloActual.removerDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Inscripción eliminada correctamente");
                            limpiaDatos();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la inscripcion. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                    }
                    //</editor-fold>

                }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                        System.out.println("Intentando cargar un dato de " + this.modeloActual.getCodigo());
                        if(!this.modeloActual.buscarCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()))){
                            JOptionPane.showMessageDialog(null, "El código ingresado no existe en la base de datos");
                        }else{
                            cargarDato(this.modeloActual.getCodigo());
                            JOptionPane.showMessageDialog(null, "Datos cargados con éxito!");
                        }
                        //</editor-fold>

                }else if(!(this.vistaActual.getTxtNombre().getText().isEmpty() || String.valueOf(this.vistaActual.getCBCodigoCarrera().getSelectedItem()).isEmpty())){
                    //<editor-fold defaultstate="collapsed" desc="Declaración del objeto">
                    java.util.Date date = this.vistaActual.getJDFecha().getDate();
                    this.modeloActual.setCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                    this.modeloActual.setNombre(this.vistaActual.getTxtNombre().getText());
                    this.modeloActual.setCodigoCarrera(Long.parseLong(String.valueOf(this.vistaActual.getCBCodigoCarrera().getSelectedItem())));
                    this.modeloActual.setFecha(new java.sql.Date(date.getTime()));

                    //</editor-fold>
                    
                    if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                        System.out.println("Intentando crear un dato");
                        if (modeloActual.verificarUnicidad(modeloActual)) {
                            if (this.modeloActual.cargarDato(modeloActual)) {   //todo en orden? cargamos el alumno
                                JOptionPane.showMessageDialog(null, "Inscripcion cargada correctamente");
                            }
                            limpiaDatos();
                        } else {
                            JOptionPane.showMessageDialog(null, "Nombre de Inscripcion ya existente");
                        }
                        //</editor-fold>

                    }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                        System.out.println("Intentando editar un dato");
                        if(this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){ //Busca por las dudas que el código sea incorrecto
                            if(this.modeloActual.editarDato(modeloActual)){
                                JOptionPane.showMessageDialog(null, "Inscripcion editada correctamente");
                                limpiaDatos();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Hubo un error al editar la Inscripcion. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                        }
                        //</editor-fold>

                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios, para crear o editar");
                }
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() {   //limpia los campos">
    public void limpiaDatos() {   
        completarCBCodigoCarrera();
        this.vistaActual.getTxtCodigo().setText("");
        this.vistaActual.getTxtNombre().setText("");
        this.vistaActual.getCBCodigoCarrera().setSelectedItem(0);
    }//</editor-fold>
    public void cargarDato(long codigo){
        completarCBCodigoCarrera();
        this.vistaActual.getTxtNombre().setText(this.modeloActual.buscarInscripcion(codigo).getNombre());
        this.vistaActual.getCBCodigoCarrera().setSelectedItem(Long.toString(this.modeloActual.buscarInscripcion(codigo).getCodigoCarrera()));
    }
    public void abrirVentanaPrincipal(){
        PrincipalM mod = new PrincipalM();
        PrincipalV vis = new PrincipalV();
        PrincipalC con = new PrincipalC(mod, vis);
        this.vistaActual.dispose();

    }
    public void completarCBCodigoCarrera(){ 
        this.vistaActual.getCBCodigoCarrera().removeAllItems();
        //this.vistaActual.getCBCodigoCarrera().insertItemAt("", 0);
        Set<String> codigoCarrera = this.modeloActual.traerCodigoCarrera();
        Iterator<String> codigoIterator = codigoCarrera.iterator();
        while(codigoIterator.hasNext()){
        this.vistaActual.getCBCodigoCarrera().addItem(codigoIterator.next());
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
