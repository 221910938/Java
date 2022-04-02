/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author raton
 */
public class SqlProductos extends Conexion {
    
    public int existeProducto(String cod) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = Conexion();

        String sql = "SELECT count(id) FROM productos_cafeteria WHERE codigo = ?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, cod);

            //ver por cualquier error la posicion y cambiar a la 4
            rs = ps.executeQuery();

            
            if (rs.next()) {
                
                return rs.getInt(1);
            }
            return 0;

        } catch (Exception e) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

     public boolean registrarProductos(Productos prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = Conexion();

        String sql = "INSERT INTO productos_cafeteria (codigo,nombre,precio,cantidad,usable,venta) VALUES (?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setInt(3, prod.getPrecio());
            ps.setInt(4, prod.getCantidad());
            ps.setBoolean(5, prod.getUsable());
            ps.setBoolean(6, prod.getVenta());

            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
    }
    
      public boolean modificarProductos(Productos prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = Conexion();

        String sql = "update productos_cafeteria set codigo=?, nombre=?, precio=?, cantidad=?,usable=?,venta=? where codigo=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setInt(3, prod.getPrecio());
            ps.setInt(4, prod.getCantidad());
            ps.setBoolean(3, prod.getUsable());
            ps.setBoolean(4, prod.getVenta());

            ps.setString(5, prod.getCodigo());
            
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
    }
      
       public boolean eliminarProductos(Productos prod) throws SQLException {
        PreparedStatement ps = null;
        Connection con = Conexion();

       String sql="delete from productos_cafeteria where codigo= ?";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, prod.getCodigo());

            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {

            try {
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
    }
    public ArrayList<Object[]> consultar() {
        ArrayList<Object[]> data = new ArrayList<>();
        Connection con = Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM productos_cafeteria";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[7];
                for (int i = 0; i <= 6; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                data.add(fila);
            }
            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrio este error: " + e.getMessage());
        } finally {
            return data;
        }

    }
    
    
    
}
