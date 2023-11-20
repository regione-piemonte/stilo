/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import it.eng.job.aggiungiMarca.bean.FileMarcatoBean;

public class MarcaUtils {

	private static final Logger log = Logger.getLogger(MarcaUtils.class);
	
	public static FileMarcatoBean isMarcato(File file, String tipoFile ) throws Exception{
		if( tipoFile==null || tipoFile.equalsIgnoreCase("PADES")){
			//return isMarcatoPades(file);
			try {
				return new WSClientUtils().verificaFileMarcato(file);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage());
				throw e;
				//FileMarcatoBean fileMarcatoBean = new FileMarcatoBean();
				//fileMarcatoBean.setMarcato(true);
				//return fileMarcatoBean;
				//return true;
			}
		} else {
			try {
				return new WSClientUtils().verificaFileMarcato(file);
			} catch (Exception e) {
				log.error("Errore: " + e.getMessage());
				throw e;
				//FileMarcatoBean fileMarcatoBean = new FileMarcatoBean();
				//fileMarcatoBean.setMarcato(true);
				//return fileMarcatoBean;
				//return true;
			}
		}
	}
	
	public static boolean isMarcatoPades(File file){
		Provider provider = Security.getProvider("BC");
		log.info("Prima lettura provider: " + provider);

		//if( provider==null )
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		provider = Security.getProvider("BC");
		log.info("Seconda lettura provider: " + provider);
		
		FileInputStream fis = null;
		try {
			log.info("apro PdfReader");
			
			fis = FileUtils.openInputStream(file);
			PdfReader r = new PdfReader(fis);
			log.info("dopo PdfReader");
			
			ByteArrayOutputStream fout = new ByteArrayOutputStream();
			PdfStamper stp = PdfStamper.createSignature(r, fout, '\0', null, true);
			
			AcroFields fields = stp.getAcroFields();
			List<String> names = fields.getSignatureNames();
			for(String name : names){
				log.info("name:: " + name);
				if( name!=null && name.equalsIgnoreCase("marca")){
					log.info("Il file e' marcato");
					return true;
				}
			}
			log.info("Il file non e' marcato");
			
			return false;
		} catch (IOException e) {
			log.error("", e);
			return false;
		} catch (DocumentException e) {
			log.error("", e);
			return false;
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				log.error("", e);
				
			}
		}
	}
}
