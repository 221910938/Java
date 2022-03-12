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
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 *
 * @author Angel
 */

public class CodigoBarras {

    public static void main(String[]args) throws FileNotFoundException,DocumentException{
        
        Document doc = new Document();
        PdfWriter pdf= PdfWriter.getInstance(doc,new FileOutputStream("codigos.pdf"));
        doc.open();
        
        Barcode39 code = new Barcode39();
        code.setCode("1234567890");
        Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
        img.scalePercent(100);
        doc.add(img);
        
        doc.add(new Paragraph(" "));
        
        Barcode128 code128= new Barcode128();
        code128.setCode("1234567890");
        Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
        img128.scalePercent(100);
        doc.add(img128);
        doc.close();
                
                
    }
}