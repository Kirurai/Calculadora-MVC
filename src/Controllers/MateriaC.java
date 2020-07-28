/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.MateriaM;
import Models.PrincipalM;
import Views.MateriaV;
import Views.PrincipalV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class MateriaC implements ActionListener{
    private MateriaV vistaActual;
    private MateriaM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public MateriaC(MateriaM modelo, MateriaV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de materias");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
        completarCBProfeDni();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new MateriaM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>          
            }else if(this.vistaActual.getTxtCodigo().getText().isEmpty()){
                System.out.println("El campo codigo de la Materiaes obligatorio para cualquier operación");
                JOptionPane.showMessageDialog(null, "El Codigo de la materia es obligatorio para cualquier operación");
            }else if(ae.getSource().equals(this.vistaActual.getBtnEliminar())){
                //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                this.modeloActual.setCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                System.out.println("Intentando eliminar un dato");
                if(this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){
                    modeloActual = modeloActual.buscarMateria(this.modeloActual.getCodigo());
                    if(this.modeloActual.materiaLibre(this.modeloActual)){
                            JOptionPane.showMessageDialog(null, "No puede eliminar una materia perteneciente a un cursado activo");
                    }
                    else if(this.modeloActual.removerDato(modeloActual)){
                        JOptionPane.showMessageDialog(null, "Materia eliminada correctamente");
                        limpiaDatos();
                        System.out.println("Ya limpió");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la materia. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                }
                //</editor-fold>

            }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                this.modeloActual.setCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                System.out.println("Intentando cargar un dato");
                if(!this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){
                    JOptionPane.showMessageDialog(null, "El código ingresado no existe en la base de datos");
                }else{
                    cargarDato(this.modeloActual.getCodigo());
                    JOptionPane.showMessageDialog(null, "Datos cargados con éxito!");
                }
                //</editor-fold>

            }else if(!(this.vistaActual.getTxtNombre().getText().isEmpty())){
                //<editor-fold defaultstate="collapsed" desc="Declaración del objeto">
                this.modeloActual.setCodigo(Long.parseLong(this.vistaActual.getTxtCodigo().getText()));
                this.modeloActual.setNombre(this.vistaActual.getTxtNombre().getText());
                this.modeloActual.setProfeDni(Long.parseLong(String.valueOf(this.vistaActual.getCBProfeDni().getSelectedItem())));
                    //</editor-fold>
                
                if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                    System.out.println("Intentando nuevo un dato");
                        if (modeloActual.verificarUnicidad(modeloActual)) {
                            if (this.modeloActual.cargarDato(modeloActual)) {   //todo en orden? cargamos el alumno
                                JOptionPane.showMessageDialog(null, "Materia cargada correctamente");
                            }
                            limpiaDatos();
                        } else {
                            JOptionPane.showMessageDialog(null, "Código de Materia ya existente");
                        }
                    //</editor-fold>

                }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                    System.out.println("Intentando editar un dato");
                    if(this.modeloActual.buscarCodigo(this.modeloActual.getCodigo())){ //Busca por las dudas que el código sea incorrecto
                        if(this.modeloActual.editarDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Materia editada correctamente");
                            limpiaDatos();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al editar la Materia. Vuelva a intentarlo.(Fijese que el código sea correcto)");
                    }
                    //</editor-fold>

                }
                
            }else{
                JOptionPane.showMessageDialog(null, "El nombre de la materia es obligatorio para crear o editar");
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() {   //limpia los campos">
    public void limpiaDatos() {   
        completarCBProfeDni();
        this.vistaActual.getTxtCodigo().setText("");
        this.vistaActual.getTxtNombre().setText("");
        this.vistaActual.getCBProfeDni().setSelectedIndex(0);

    }//</editor-fold>
    public void cargarDato(long codigo){
        completarCBProfeDni();
        this.vistaActual.getTxtNombre().setText(this.modeloActual.buscarMateria(codigo).getNombre());
        this.vistaActual.getCBProfeDni().setSelectedItem(Long.toString(this.modeloActual.buscarMateria(codigo).getProfeDni()));
    }
    public void abrirVentanaPrincipal(){
        PrincipalM mod = new PrincipalM();
        PrincipalV vis = new PrincipalV();
        PrincipalC con = new PrincipalC(mod, vis);
        this.vistaActual.dispose();

    }
    public void completarCBProfeDni(){ 
        this.vistaActual.getCBProfeDni().removeAllItems();
        //this.vistaActual.getCBProfeDni().insertItemAt("", 0);
        Set<String> aluDni = this.modeloActual.traerProfeDni();
        Iterator<String> dniIterator = aluDni.iterator();
        while(dniIterator.hasNext()){
            this.vistaActual.getCBProfeDni().addItem(dniIterator.next());
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
