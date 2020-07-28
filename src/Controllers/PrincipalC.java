/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Maurizio
 */
public class PrincipalC implements ActionListener{
    private PrincipalV vista;
    private PrincipalM modelo;
    
    public PrincipalC(PrincipalM mPrincipal, PrincipalV vPrincipal){
        this.vista = vPrincipal;
        this.modelo = mPrincipal;
        
                
        vista.setVisible(true);
        vista.setTitle("Menu Principal");
        vista.setLocationRelativeTo(null);
        
        //<editor-fold defaultstate="collapsed" desc="Escucha de botones">
        vista.getBtnAlumnos().addActionListener(this);
        vista.getBtnCarrera().addActionListener(this);
        vista.getBtnCursado().addActionListener(this);
        vista.getBtnInscripcion().addActionListener(this);
        vista.getBtnMaterias().addActionListener(this);
        vista.getBtnProfesores().addActionListener(this);
        //</editor-fold>
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae);
        //<editor-fold defaultstate="collapsed" desc="Generador de la nueva ventana">
        if(ae.getSource().equals(vista.getBtnAlumnos())){    //Alumno
            
            AlumnoM mod = new AlumnoM();
            AlumnoV vis = new AlumnoV();
            AlumnoC con = new AlumnoC(mod, vis);
            this.vista.dispose();
        
        }else if (ae.getSource().equals(vista.getBtnCarrera())) {   //Carrera
            
            CarreraM mod = new CarreraM();
            CarreraV vis = new CarreraV();
            CarreraC con = new CarreraC(mod, vis);
            this.vista.dispose();
            
        }else if (ae.getSource().equals(vista.getBtnCursado())) {   //Cursado
            
            CursadoM mod = new CursadoM();
            CursadoV vis = new CursadoV();
            CursadoC con = new CursadoC(mod, vis);
            this.vista.dispose();
            
        }else if (ae.getSource().equals(vista.getBtnInscripcion())) {   //Inscripcion
            
            InscripcionM mod = new InscripcionM();
            InscripcionV vis = new InscripcionV();
            InscripcionC con = new InscripcionC(mod, vis);
            this.vista.dispose();
            
        }else if (ae.getSource().equals(vista.getBtnMaterias())) {   //Materia
            
            MateriaM mod = new MateriaM();
            MateriaV vis = new MateriaV();
            MateriaC con = new MateriaC(mod, vis);
            this.vista.dispose();
            
        }else if (ae.getSource().equals(vista.getBtnProfesores())) {   //Profesor
            
            ProfesorM mod = new ProfesorM();
            ProfesorV vis = new ProfesorV();
            ProfesorC con = new ProfesorC(mod, vis);
            this.vista.dispose();
            
        }
        //</editor-fold>
        
    }
    
    
}
