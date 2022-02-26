/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author raton
 */
public class Conexion {
    private final String BD="administracion"; 
    private final String USER="root"; 
    private final String PASS=""; 
    private final String URl="Jdbc:mysql://localhost:3306/"+BD; 
    
    public Connection con =null;
    
    public Connection Conexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(this.URl,this.USER,this.PASS);
            if (con!=null) {
                JOptionPane.showMessageDialog(null, "Conexion Correcta");
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return con;
    }
}
