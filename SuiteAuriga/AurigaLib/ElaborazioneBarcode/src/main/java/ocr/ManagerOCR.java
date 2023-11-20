/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//import net.sourceforge.tess4j.util.LoadLibs;

public class ManagerOCR {
	
	public static boolean firmaPresente(File imageSection) throws Exception {

//		Tesseract instance = Tesseract.getInstance();
//		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
//		instance.setDatapath(tessDataFolder.getAbsolutePath());
//
//		try {
//		    String result = instance.doOCR(imageSection);
//		    System.out.println(result);
//		    result = result.replaceAll("\n", "").replaceAll("Il Cliente", "").replaceAll("II Cliente", "").replaceAll("\\(Timbro e Firma\\)", ""); 
//		    if(!"".equals(result)) {
//		    	return true;
//		    }else {
		    	return false;
//		    }
//		    
//		} catch (TesseractException e) {
//		    System.err.println(e.getMessage());
//		    throw new Exception(e.getMessage(), e);
//		}		
	}
}
