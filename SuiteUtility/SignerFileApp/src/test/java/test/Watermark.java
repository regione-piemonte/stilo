package test;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class Watermark {

	public static void main(String[] args) {
	   try {
           PdfReader reader = new PdfReader("C:/PDF_N.pdf");
           PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/watermark.pdf"));
           BufferedImage imgAwt = ImageIO.read(new File("c:/Michele/sun.jpg"));
           int width = imgAwt.getWidth();
           int heigth = imgAwt.getHeight();
           Image img = Image.getInstance("c:/Michele/sun.jpg");
           img.setAbsolutePosition(150, 300);
           byte gradient[] = new byte[width*heigth];
           for (int i=0; i<width; i++) {
               for(int j=0; j<heigth; j++) {
                   if(imgAwt.getRGB(i, j)>>24!=0) {
                       gradient[(j*imgAwt.getWidth())+i] = (byte) 80;
                   } else {
                       gradient[(j*imgAwt.getWidth())+i] = (byte) 0;
                   }
               }
           }
           Image smask = Image.getInstance(imgAwt.getWidth(), imgAwt.getHeight(), 1, 8, gradient);
           smask.makeMask();
           img.setImageMask(smask);
           PdfContentByte over = null;
           for(int i=1;i<=reader.getNumberOfPages();i++) {
               over = stamp.getOverContent(i);
               over.addImage(img);
           }
           stamp.close();
       } catch (Exception de) {
           de.printStackTrace();
       }
   }
	
}
