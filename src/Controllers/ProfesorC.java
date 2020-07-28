/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.PrincipalM;
import Models.ProfesorM;
import Views.PrincipalV;
import Views.ProfesorV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JOptionPane;
/**
 *
 * @author Maurizio Miño <kd.maurii@gmail.com> aka "Kirurai"
 */
public class ProfesorC implements ActionListener{
    private ProfesorV vistaActual;
    private ProfesorM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public ProfesorC(ProfesorM modelo, ProfesorV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de profesores");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
        Calendar c = Calendar.getInstance();
        this.vistaActual.getFechaNac().setDate(c.getTime());
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new ProfesorM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>          
            }else if(this.vistaActual.getTxtDni().getText().isEmpty()){
                System.out.println("El campo Dni es obligatorio para cualquier operación");
                JOptionPane.showMessageDialog(null, "El Dni del profesor es obligatorio para cualquier operación");
            }else{
                this.modeloActual.setDni(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                if(ae.getSource().equals(this.vistaActual.getBtnEliminar())) {

                    //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                    System.out.println("Intentando eliminar un dato");
                    if(this.modeloActual.buscarDni(Long.parseLong(this.vistaActual.getTxtDni().getText()))){
                        modeloActual = modeloActual.buscarProfesor(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                        if(this.modeloActual.profesorLibre(this.modeloActual)){
                            JOptionPane.showMessageDialog(null, "No puede borrar un profesor que esté actualmente en una materia");
                        }else if(this.modeloActual.removerDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Profesor eliminado correctamente");
                            limpiaDatos();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la Profesor. Vuelva a intentarlo./nCorroboré que el Profesor no exista en otra materia");
                    }
                    //</editor-fold>

                }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                    System.out.println("Intentando cargar un dato");
                     if(!this.modeloActual.buscarDni(Long.parseLong(this.vistaActual.getTxtDni().getText()))){
                        JOptionPane.showMessageDialog(null, "El Dni ingresado no existe en la base de datos");
                    }else{
                        cargarDato(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                        JOptionPane.showMessageDialog(null, "Datos cargados con éxito!");
                    }
                    //</editor-fold>
                    
                }else if(!(this.vistaActual.getTxtNombre().getText().isEmpty() || this.vistaActual.getTxtApellido().getText().isEmpty())){
                    //<editor-fold defaultstate="collapsed" desc="declaración del Objeto">
                    java.util.Date date = this.vistaActual.getFechaNac().getDate();
                    this.modeloActual.setDni(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                    this.modeloActual.setNombre(this.vistaActual.getTxtNombre().getText());
                    this.modeloActual.setApellido(this.vistaActual.getTxtApellido().getText());
                    this.modeloActual.setFechaNac(new java.sql.Date(date.getTime()));
                    this.modeloActual.setDomicilio(this.vistaActual.getTxtDomicilio().getText());
                    this.modeloActual.setTelefono(this.vistaActual.getTxtTelefono().getText());
                //</editor-fold>

                    if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                        System.out.println("intentando crear nuevo un dato");
                            if(this.modeloActual.verificarUnicidad(this.modeloActual)) { 
                                if (this.modeloActual.cargarDato(modeloActual)) {   //todo en orden? cargamos al profesor
                                    JOptionPane.showMessageDialog(null, "Profesor cargado correctamente");
                                }
                                limpiaDatos();
                            }else{
                                JOptionPane.showMessageDialog(null, "Dni de profesor ya existente");
                            }
                        //</editor-fold>

                    }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                        System.out.println("Intentando editar un dato");
                        if(this.modeloActual.buscarDni(this.modeloActual.getDni())){ //Busca por las dudas que el código sea incorrecto y el mismo no exista
                            if(this.modeloActual.editarDato(modeloActual)){
                                JOptionPane.showMessageDialog(null, "Profesor editado correctamente");
                                limpiaDatos();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Hubo un error al editar al profesor. Vuelva a intentarlo.(Fijese que el Dni sea correcto)");
                        }
                        //</editor-fold>

                    }else if(ae.getSource().equals(this.vistaActual.getBtnCargarDato())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Cargar Pulsado">
                        System.out.println("Intentando cargar un dato");
                         if(!this.modeloActual.buscarDni(this.modeloActual.getDni())){
                            JOptionPane.showMessageDialog(null, "El Dni ingresado no existe en la base de datos");
                        }else{
                            cargarDato(this.modeloActual.getDni());
                        }
                        //</editor-fold>
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Los campos de dni, nombre y apellido son obligatorios, para crear o editar");
                }
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() y cargaDato(long dni)">
    public void limpiaDatos() {   
        this.vistaActual.getTxtDni().setText("");
        this.vistaActual.getTxtNombre().setText("");
        this.vistaActual.getTxtApellido().setText("");
        Calendar c = Calendar.getInstance();
        this.vistaActual.getFechaNac().setDate(c.getTime());
        this.vistaActual.getTxtDomicilio().setText("");
        this.vistaActual.getTxtTelefono().setText("");
        System.out.println("Ya limpió");

    }
    public void cargarDato(long dni){
        ProfesorM profesor = this.modeloActual.buscarProfesor(dni);
        this.vistaActual.getTxtNombre().setText(profesor.getNombre());
        this.vistaActual.getTxtApellido().setText(profesor.getApellido());
        this.vistaActual.getFechaNac().setDate(profesor.getFechaNac());
        this.vistaActual.getTxtDomicilio().setText(profesor.getDomicilio());
        this.vistaActual.getTxtTelefono().setText(profesor.getTelefono());
    }//</editor-fold>
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
