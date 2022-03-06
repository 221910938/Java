/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author raton
 */
public class SqlUsuarios extends Conexion {

    public boolean registrar(Usuarios usr) throws SQLException {
        PreparedStatement ps = null;
        Connection con = Conexion();

        String sql = "INSERT INTO usuarios (usuario,password,nombre,correo,id_tipo) VALUES (?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getCorreo());
            //ps.setString(5, usr.getUltima_sesion());
            ps.setInt(5, usr.getId_tipo());

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

    public int existeUsuario(String usuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = Conexion();

        String sql = "SELECT count(id) FROM usuarios WHERE Usuario = ?";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, usuario);

            //ver por cualquier error la posicion y cambiar a la 4
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;

        } catch (Exception e) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }

        return 1;
    }

    public boolean esMail(String correo) {

        //Patron para validar el mail 
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);

        return mather.find();
    }

    public boolean Login(Usuarios usr) throws SQLException {
        
        System.err.println(usr);
        PreparedStatement ps = null;

        ResultSet rs = null;

        Connection con = Conexion();

        String sql = "SELECT id, usuario, password, nombre,correo,ultima_sesion, id_tipo FROM usuarios WHERE usuario = ? ";

        ps = con.prepareStatement(sql);

        ps.setString(1, usr.getUsuario());

        rs = ps.executeQuery();

        try {

            if (rs.next()) {

                if (usr.getPassword().equals(rs.getString(3))) {

                    String sqlupdate = "UPDATE usuarios set ultima_sesion = ? WHERE id = ?";
                    ps = con.prepareStatement(sqlupdate);
                    ps.setString(1, usr.getUltima_sesion());
                    ps.setInt(2, rs.getInt(1));
                    ps.execute();

                    usr.setId(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    usr.setId_tipo(rs.getInt(7));
                    return true;

                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

}
