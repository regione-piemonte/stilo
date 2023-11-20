/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class UtilityFileBarcode{
	
	private static Logger logger = LogManager.getLogger(UtilityFileBarcode.class);
	
	private static final String ALGORITMO = "SHA-256";
	private static final String ENCODING = "base64";
	
	public static String calcolaImpronta(File file, String algoritmo, String encoding) {
	
			BufferedInputStream bis = null;
			byte[] hash = null;
			String encodedHash = null;
			
			algoritmo = algoritmo!=null && !"".equals(algoritmo) ? algoritmo : ALGORITMO;
			encoding = encoding!=null && !"".equals(encoding) ? encoding : ENCODING;
	
			try {
	
				if (algoritmo==null || "".equals(algoritmo)) {
					logger.error("Algoritmo per il calcolo dell'impronta non valorizzato");
				} else {
					bis = new BufferedInputStream(new FileInputStream(file));
					if (algoritmo.equalsIgnoreCase("SHA-256")) {
						hash = DigestUtils.sha256(bis);
					} else if (algoritmo.equalsIgnoreCase("SHA-1")) {
						hash = DigestUtils.sha(bis);
					} else {
						logger.error(String.format("L'algoritmo %1$s non è uno di quelli previsti", algoritmo));
					}
				}
	
				if (encoding==null || "".equals(encoding)) {
					logger.error("Encoding per il calcolo dell'impronta non valorizzato");
				} else if (encoding.equalsIgnoreCase("base64")) {
					encodedHash = Base64.encodeBase64String(hash);
				} else if (encoding.equalsIgnoreCase("hex")) {
					encodedHash = Hex.encodeHexString(hash);
				} else {
					logger.error(String.format("L'encoding %1$s non è uno di quelli previsti", encoding));
				}
			} catch (Exception e) {
				logger.error("Durante il calcolo dell'impronta si è verificata la seguente eccezione", e);
			} finally {
				try {
					if(bis!=null) {
						bis.close();
					}
				} catch (IOException ioe) {
				}
			}
	
			return encodedHash;
	
		}
	
		public static File fromImageToPdf(List<String> listaPathImage, boolean fromScannedException) throws Exception {
			
			File pdfFinale = File.createTempFile("pdfTemp", ".pdf");
			Document document = null;
			
			try {
				if (fromScannedException) {
					document = new Document(PageSize.A4,0,0,0,0);
				} else {
					document = new Document();
				}
				PdfWriter.getInstance(document, new FileOutputStream(pdfFinale));
				document.open();

				for (String pathImage : listaPathImage) {
					
					BufferedImage bImage = ImageIO.read(new File(pathImage));
					File immagineFinale = File.createTempFile("bImage", ".jpg");
					ImageIO.write(bImage, "jpg", immagineFinale);
				
					Image image = Image.getInstance(immagineFinale.getAbsolutePath());
					image.setScaleToFitHeight(true);				
					
					image.setAbsolutePosition(0, 0);
					image.setBorder(Image.NO_BORDER);
					image.setBorderWidth(0);
					
					float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
				               - document.rightMargin() ) / image.getWidth()) * 100;

					image.scalePercent(scaler);
				
					document.setPageSize(new Rectangle(image.getScaledWidth()-1, image.getScaledHeight()-1));
					document.newPage();
					document.add(image);
					
					immagineFinale.delete();
					
				}
				
				return pdfFinale;
				
			} catch (Exception e) {
				logger.error("Errore nel metodo fromImageToPdf", e);
				throw new Exception(e.getMessage(), e);
			} finally {
				if (document != null) {
					document.close();
				}
			}
		}	
	
}

