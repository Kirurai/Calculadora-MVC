/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.CarreraM;
import Models.PrincipalM;
import Views.CarreraV;
import Views.PrincipalV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class CarreraC implements ActionListener{
    private CarreraV vistaActual;
    private CarreraM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public CarreraC(CarreraM modelo, CarreraV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de carreras");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new CarreraM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>          
            }else if(this.vistaActual.getTxtCodigo().getText().isEmpty()){
                System.out.println("El campo Codigo es obligatorio para cualquier operación");
                JOptionPane.showMessageDialog(null, "El Codigo de carrera es obligatorio para cualquier operación");
            }else{
                if(ae.getSource().equals(this.vistaActual.getBtnEliminar())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                    System.out.println("Intentando eliminar un dato");
                    if(this.modeloActual.buscarCodigo((Long.parseLong(this.vistaActual.getTxtCodigo().getText())))){
                        modeloActual = modeloActual.buscarCarrera((Long.parseLong(this.vistaActual.getTxtCodigo().getText())));
                        if(this.modeloActual.carreraLibre(this.modeloActual)){
                            JOptionPane.showMessageDialog(null, "No puede borrar una carrera que esté asociada a una inscripción");
                        }else if(this.modeloActual.removerDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Carrera eliminada correctamente");
                            limpiaDatos();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la carrera. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                    }
                    //</editor-fold>

                }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                    System.out.println("Intentando cargar un dato");
                    if(!this.modeloActual.buscarCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()))){
                        JOptionPane.showMessageDialog(null, "El código ingresado no existe en la base de datos");
                    }else{
                        cargarDato(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                        JOptionPane.showMessageDialog(null, "Datos cargados con éxito!");
                    }
                    //</editor-fold>
                }else if(!(this.vistaActual.getTxtNombre().getText().isEmpty() || this.vistaActual.getTxtDuracion().getText().isEmpty())){
                    //<editor-fold defaultstate="collapsed" desc="Declaración del objeto">
                   this.modeloActual.setNombre(this.vistaActual.getTxtNombre().getText());
                   this.modeloActual.setDuracion(Long.parseLong(this.vistaActual.getTxtDuracion().getText()));
                   this.modeloActual.setCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                   //</editor-fold>

                   if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                       //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                       System.out.println("Intentando nuevo un dato");
                           if (modeloActual.verificarNombreUnico(modeloActual)) {
                               if (this.modeloActual.cargarDato(modeloActual)) { 
                                   JOptionPane.showMessageDialog(null, "Carrera cargada correctamente");
                               }
                               limpiaDatos();
                           } else {
                               JOptionPane.showMessageDialog(null, "Nombre de carrera ya existente");
                           }
                       //</editor-fold>

                   }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                       //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                       System.out.println("Intentando editar un dato");
                       if(this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){ //Busca por las dudas que el código sea incorrecto
                           if(this.modeloActual.editarDato(modeloActual)){
                               JOptionPane.showMessageDialog(null, "Carrera editada correctamente");
                               limpiaDatos();
                           }
                       }else{
                           JOptionPane.showMessageDialog(null, "Hubo un error al editar la carrera. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                       }
                       //</editor-fold>

                   }
                }
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() {   //limpia los campos">
    public void limpiaDatos() {   
        this.vistaActual.getTxtCodigo().setText("");
        this.vistaActual.getTxtNombre().setText("");
        this.vistaActual.getTxtDuracion().setText("");

    }//</editor-fold>
    public void cargarDato(long codigo){
        this.vistaActual.getTxtNombre().setText(this.modeloActual.buscarCarrera(codigo).getNombre());
        this.vistaActual.getTxtDuracion().setText(Long.toString(this.modeloActual.buscarCarrera(codigo).getDuracion()));
    }
    public void abrirVentanaPrincipal(){
        PrincipalM mod = new PrincipalM();
        PrincipalV vis = new PrincipalV();
        PrincipalC con = new PrincipalC(mod, vis);
        this.vistaActual.dispose();

    }
    public boolean comprobarTiempo(long tiempoActual){
        if(this.tiempoDeUltimoEvento != tiempoActual){
                this.tiempoDeUltimoEvento = tiempoActual;
                return true;
        }
        return false;
    }
}
