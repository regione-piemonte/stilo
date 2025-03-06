package it.eng.applet.util;

import java.io.FileOutputStream;
import java.util.List;

//import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPage;

import it.eng.applet.configuration.TestoBarcodeBean;
import it.eng.applet.configuration.bean.PdfRigaBean;

/**
 * 
 * @author FECACCO
 *
 */
public class CommonUtils {

	//public final static Logger logger = Logger.getLogger(CommonUtils.class);
	
	/**
	 * Converte una dimensione in millimetri
	 * @param strMisura La dimensione (deve terminare con l'unita'† di misura)
	 * @return La dimensione convertita in millimentri
	 */
	public static float convertLengthMeasurementToMm(String strMisura){
		LogWriter.writeLog("Converto " + strMisura + " in millimetri");
		int fattConv = 1;
		String strMisuraSenzaUnita = strMisura;
		if (strMisura.toLowerCase().indexOf("mm") != -1){
			// La misura e' in millimetri
			fattConv = 1;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("mm"));
		}else if (strMisura.toLowerCase().indexOf("cm") != -1){
			// La misura e' in centimentri
			fattConv = 10;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("cm"));
		}
		Float misuraSenzaUnita = Float.parseFloat(strMisuraSenzaUnita);
		LogWriter.writeLog("Misura da convertire: " + strMisuraSenzaUnita + ", Fattore di conversione: " + fattConv + ", Misura convertita: " + misuraSenzaUnita * fattConv);
		return misuraSenzaUnita * fattConv;
	}
	
	/**
	 * Rimuove l'unita'† di misura dalla dimensione
	 * @param inputValue La dimensione con l'unita'† di misura
	 * @param unitOfMeasure L'unit√† di misura da rimuovere
	 * @return La dimensione senza unita'† di misura
	 */
	public static String removeUnitOfMeasure(String inputValue, String unitOfMeasure){
		String result = inputValue;
		if (inputValue.toLowerCase().indexOf(unitOfMeasure) != -1){
			result = inputValue.substring(0, inputValue.toLowerCase().indexOf(unitOfMeasure));
		}
		LogWriter.writeLog("Valore in input: " + inputValue + ", Unit√† da rimuovere: " + unitOfMeasure + ", Output: " + result);
		return result;
	}
	
}
