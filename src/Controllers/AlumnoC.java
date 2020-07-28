/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.PrincipalM;
import Models.AlumnoM;
import Views.AlumnoV;
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
public class AlumnoC implements ActionListener{
    private AlumnoV vistaActual;
    private AlumnoM modeloActual;
    private long tiempoDeUltimoEvento;

    
    public AlumnoC(AlumnoM modelo, AlumnoV vista){
        this.vistaActual = vista;
        this.modeloActual = modelo;

        vista.setVisible(true);
        vista.setTitle("Base de alumnos");
        vista.setLocationRelativeTo(null);

        this.vistaActual.getBtnNuevo().addActionListener(this);
        this.vistaActual.getBtnEditar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnEliminar().addActionListener(this);
        this.vistaActual.getBtnPrincipal().addActionListener(this);
        this.vistaActual.getBtnCargarDato().addActionListener(this);
        
        Calendar c = Calendar.getInstance();
        this.vistaActual.getFechaNac().setDate(c.getTime());
        completarCBInscripcion();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.modeloActual = new AlumnoM();
        if(comprobarTiempo(ae.getWhen())){
            System.out.println(ae);
            if(ae.getSource().equals(this.vistaActual.getBtnPrincipal())){
                //<editor-fold defaultstate="collapsed" desc="Botón Volver Pulsado">
                abrirVentanaPrincipal();
                //</editor-fold>          
            }else if(this.vistaActual.getTxtDni().getText().isEmpty()){
                System.out.println("El campo Dni es obligatorio para cualquier operación");
                JOptionPane.showMessageDialog(null, "El Dni del alumno es obligatorio para cualquier operación");
            }else{
                if(ae.getSource().equals(this.vistaActual.getBtnEliminar())){
                    //<editor-fold defaultstate="collapsed" desc="Botón Eliminar Pulsado">
                    System.out.println("Intentando eliminar un dato");
                    if(this.modeloActual.buscarDni(Long.parseLong(this.vistaActual.getTxtDni().getText()))){
                        modeloActual = modeloActual.buscarAlumno(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                         if(this.modeloActual.alumnoLibre(this.modeloActual)){
                            JOptionPane.showMessageDialog(null, "No puede borrar un alumno con cursadas activas");
                        }else if(this.modeloActual.removerDato(modeloActual)){
                            JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
                            limpiaDatos();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Hubo un error al eliminar la alumno. Vuelva a intentarlo.(Fijese que el código sea correcto)");
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

                }else if(!(String.valueOf(this.vistaActual.getCBInscripcion().getSelectedItem()).isEmpty() ||
                        this.vistaActual.getTxtNombre().getText().isEmpty() ||
                        this.vistaActual.getTxtApellido().getText().isEmpty())){
                    //<editor-fold defaultstate="collapsed" desc="Declaración del objeto">
                    this.modeloActual.setDni(Long.parseLong(this.vistaActual.getTxtDni().getText()));
                    this.modeloActual.setNombre(this.vistaActual.getTxtNombre().getText());
                    this.modeloActual.setApellido(this.vistaActual.getTxtApellido().getText());

                    java.util.Date date = this.vistaActual.getFechaNac().getDate();
                    this.modeloActual.setFechaNac(new java.sql.Date(date.getTime()));

                    this.modeloActual.setDomicilio(this.vistaActual.getTxtDomicilio().getText());
                    this.modeloActual.setTelefono(this.vistaActual.getTxtTelefono().getText());
                    this.modeloActual.setInscripcion(Long.parseLong(String.valueOf(this.vistaActual.getCBInscripcion().getSelectedItem())));

                    //</editor-fold>
                    
                    if (ae.getSource().equals(this.vistaActual.getBtnNuevo())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Nuevo Pulsado">
                        System.out.println("Intentando nuevo un dato");

                        if (modeloActual.verificarUnicidad(modeloActual)) {
                            if (this.modeloActual.cargarDato(modeloActual)) {   //todo en orden? cargamos al profesor
                                JOptionPane.showMessageDialog(null, "Alumno cargado correctamente");
                            }
                            limpiaDatos();
                        } else {
                            JOptionPane.showMessageDialog(null, "Dni de Alumno ya existente");
                        }
                        //</editor-fold>

                    }else if(ae.getSource().equals(this.vistaActual.getBtnEditar())){
                        //<editor-fold defaultstate="collapsed" desc="Botón Editar Pulsado">
                        System.out.println("Intentando editar un dato");
                        if(this.modeloActual.buscarDni(this.modeloActual.getDni())){ //Busca por las dudas que el código sea incorrecto
                            if(this.modeloActual.editarDato(modeloActual)){
                                JOptionPane.showMessageDialog(null, "Alumno editado correctamente");
                                limpiaDatos();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Hubo un error al editar al Alumno. Vuelva a intentarlo.(Fijese que el Dni sea correcto)");
                        }
                        //</editor-fold>

                    }
                }else{
                        JOptionPane.showMessageDialog(null, "Es obligatorio el campo de inscripción, nombre y apellido");
                }
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="public void limpiaDatos() y cargaDato(long dni)">
    public void limpiaDatos() {  
        completarCBInscripcion();
        this.vistaActual.getTxtDni().setText("");
        this.vistaActual.getTxtNombre().setText("");
        this.vistaActual.getTxtApellido().setText("");
        Calendar c = Calendar.getInstance();
        this.vistaActual.getFechaNac().setDate(c.getTime());
        this.vistaActual.getTxtDomicilio().setText("");
        this.vistaActual.getTxtTelefono().setText("");
        this.vistaActual.getCBInscripcion().setSelectedItem(0);
        
        System.out.println("Ya limpió");
    }
    
    public void cargarDato(long dni){
        completarCBInscripcion();
        AlumnoM alumno = this.modeloActual.buscarAlumno(dni);
        this.vistaActual.getTxtNombre().setText(alumno.getNombre());
        this.vistaActual.getTxtApellido().setText(alumno.getApellido());
        this.vistaActual.getFechaNac().setDate(alumno.getFechaNac());
        this.vistaActual.getTxtDomicilio().setText(alumno.getDomicilio());
        this.vistaActual.getTxtTelefono().setText(alumno.getTelefono());
        this.vistaActual.getCBInscripcion().setSelectedItem(Long.toString(this.modeloActual.buscarAlumno(dni).getInscripcion()));
    }//</editor-fold>

    public void abrirVentanaPrincipal(){
        PrincipalM mod = new PrincipalM();
        PrincipalV vis = new PrincipalV();
        PrincipalC con = new PrincipalC(mod, vis);
        this.vistaActual.dispose();

    }
    public void completarCBInscripcion(){ 
        Set<String> inscripcion = this.modeloActual.traerInscripcion();
        Iterator<String> inscripcionIterator = inscripcion.iterator();
        while(inscripcionIterator.hasNext()){
            this.vistaActual.getCBInscripcion().addItem(inscripcionIterator.next());
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
