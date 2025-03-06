package it.eng.hybrid.module.stampaEtichette.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author FECACCO
 *
 */
public class CommonUtils {

	public final static Logger logger = Logger.getLogger(CommonUtils.class);
	
	/**
	 * Converte una dimensione in millimetri
	 * @param strMisura La dimensione (deve terminare con l'unità di misura)
	 * @return La dimensione convertita in millimentri
	 */
	public static float convertLengthMeasurementToMm(String strMisura){
		logger.debug("Converto " + strMisura + " in millimetri");
		int fattConv = 1;
		String strMisuraSenzaUnita = strMisura;
		if (strMisura.toLowerCase().indexOf("mm") != -1){
			// La misura è in millimetri
			fattConv = 1;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("mm"));
		}else if (strMisura.toLowerCase().indexOf("cm") != -1){
			// La misura è in centimentri
			fattConv = 10;
			strMisuraSenzaUnita = strMisura.substring(0, strMisura.toLowerCase().indexOf("cm"));
		}
		Float misuraSenzaUnita = Float.parseFloat(strMisuraSenzaUnita);
		logger.debug("Misura da convertire: " + strMisuraSenzaUnita + ", Fattore di conversione: " + fattConv + ", Misura convertita: " + misuraSenzaUnita * fattConv);
		return misuraSenzaUnita * fattConv;
	}
	
	/**
	 * Rimuove l'unità di misura dalla dimensione
	 * @param inputValue La dimensione con l'unità di misura
	 * @param unitOfMeasure L'unità di misura da rimuovere
	 * @return La dimensione senza unità di misura
	 */
	public static String removeUnitOfMeasure(String inputValue, String unitOfMeasure){
		String result = inputValue;
		if (inputValue.toLowerCase().indexOf(unitOfMeasure) != -1){
			result = inputValue.substring(0, inputValue.toLowerCase().indexOf(unitOfMeasure));
		}
		logger.debug("Valore in input: " + inputValue + ", Unità da rimuovere: " + unitOfMeasure + ", Output: " + result);
		return result;
	}
}
