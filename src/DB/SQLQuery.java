/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maurizio
 */
public class SQLQuery {
    protected Connection conn;
    protected PreparedStatement consulta;
    protected ResultSet datos;
    
    public void conectar(String server, String bd, String usuario, String pass) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //this.conn = DriverManager.getConnection("jdbc:mysql://"+server+"/"+bd, usuario, pass);
        this.conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + bd + "?useUnicode=true&serverTimezone=GMT-3", usuario, pass);
    }
    
    public void desconectar() throws SQLException{
        this.conn.close();
        this.consulta.close();
    }
    
    public void desconectar(ResultSet rs) throws SQLException{
        this.desconectar();
        rs.close();
    }
    
}
