/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class BarrasMySql {

    public static void Crear() throws SQLException, FileNotFoundException, DocumentException {
        PreparedStatement ps ;
        ResultSet rs;
        Connection con;
        Image img;

        Conexion conn = new Conexion();
        
        con = conn.Conexion();

        try {
            
            ps = con.prepareStatement("SELECT * FROM productos_cafeteria");
            
            rs = ps.executeQuery();
            
            Document doc = new Document();
            PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream("codigoscafeteria.pdf"));
            doc.open();

            Barcode39 code = new Barcode39();

            while (rs.next()) {
                code.setCode(rs.getString("codigo"));
                img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scalePercent(120);
                doc.add(img);
                doc.add(new Paragraph(" "));
            }
            
            doc.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger(BarrasMySql.class.getName()).log(Level.SEVERE, null,e);
        }

    }
}
