package it.eng.hybrid.module.stampaEtichette.util;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.StampaEtichettaPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.bean.ZebraRigaBean;
import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;

public class EtichetteWriter {

	public final static Logger logger = Logger.getLogger( EtichetteWriter.class );
	
	private static final String END_PAGE_COMMAND = "P\r\n";
	private static final String BARCODE_COMMAND = "B%s,%s,0,1,2,2,61,N,\"%s\"\r\n";
	private static final String RIGA_COMMAND = "A%s,%s,0,%s,1,1,N,\"%s\"\r\n";
	private static final String LABEL_LENGTH_FMT = "Q%s\r\n";
	private static final String LABEL_WIDTH = "q%s\r\n";
	private static final String START_STRING = "N\r\nR0,0\r\nZT\r\n";
	private static String nomeFileStampaAPPLET = "prnSpool.prn";

	private static StampaEtichettaPropertiesBean properties;
	private static ParameterBean parameters;

	public static boolean print(StampaEtichettaPropertiesBean pProperties,
			ParameterBean pParameterBean) throws Exception{
		properties = pProperties;
		parameters = pParameterBean;
		
		String lStrCommand = prepareCommand();
		logger.info("lStrCommand " + lStrCommand);
		StringBuffer lStringBuffer = new StringBuffer();
		int numeroCopie = parameters.getNumeroCopie();
		for (int i =0; i<numeroCopie; i++){
			TestoBarcodeBean lTestoBarcodeBean = RigaBarcodeUtil.getTestoBarcodeBean(i, parameters);
			String lStringToPrint = buildString(lTestoBarcodeBean);
			lStringBuffer.append(lStringToPrint);
		}
		FileWriter fileWriter = new FileWriter(ManagerConfiguration.userPrefDirPath + File.separator + nomeFileStampaAPPLET);
		fileWriter.write(lStringBuffer.toString());
		fileWriter.close();
		if( lStrCommand!=null  ) {
			boolean esito = RunUtil.runCommandString(lStrCommand);
			logger.info("esito esecuzione comando: " + esito);
			return esito;
		}
		else { 
			logger.info("Impossibile eseguire il comando " + lStrCommand);
		}
		return false;
	}

	private static String buildString(TestoBarcodeBean lTestoBarcodeBean) {
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append(START_STRING);
		lStringBuffer.append(String.format(LABEL_WIDTH, properties.getLabelWidth()));
		lStringBuffer.append(String.format(LABEL_LENGTH_FMT, makeLabelLengthFmtString()));
		int i = 1;
		for (String lStrSplit : lTestoBarcodeBean.getTesto().split("\\|\\*\\|")){
			ZebraRigaBean lZebraRigaBean = RigaBarcodeUtil.getZebraRigaBean(i, properties);
			if( lZebraRigaBean!=null )
				lStringBuffer.append(String.format(RIGA_COMMAND, lZebraRigaBean.getOffsetx(), lZebraRigaBean.getOffsety(), 
					lZebraRigaBean.getFontSize(),escapeLabelText(lStrSplit)));
			i++;
		}
		lStringBuffer.append(String.format(BARCODE_COMMAND, properties.getOffsetXBarcode(),
				properties.getOffsetYBarcode(), escapeLabelText(lTestoBarcodeBean.getBarcode())));
		lStringBuffer.append(END_PAGE_COMMAND);
		return lStringBuffer.toString();
	}
	/**
	 * funzione helper per effettuare l'escaping delle virgolette
	 * @param textData
	 * @return
	 */
	private static Object escapeLabelText(String pStrSplit) {
		String retVal;
		retVal = pStrSplit.replace("\\", "\\\\"); //converte la backslash in doppia backslash
		retVal = retVal.replace("\"", "\\\""); //converte le virgolette in backslash+virgolette
		return (retVal);
	}

	private static String makeLabelLengthFmtString() {
		String retVal = "";
		String labelSensorMode = properties.getLabelSensorMode();
		if( properties!=null ){
			Integer labelLength = properties.getLabelLength();
			Integer labelGapLength = properties.getLabelGapLength()==null ? 0 : properties.getLabelGapLength();
			Integer labelOffsetLength = properties.getLabelOffsetLength();
			if (labelSensorMode!=null && labelSensorMode.equals("GAP")) {
				retVal = String.format("%s,%s+%s", labelLength, labelGapLength, labelOffsetLength);
			}
			if (labelSensorMode!=null && labelSensorMode.equals("BL")) {
				if (labelOffsetLength < 0) {
					retVal = String.format("%s,B%s%s", labelLength, labelGapLength, labelOffsetLength);
				} else {
					retVal = String.format("%s,B%s+%s", labelLength, labelGapLength, labelOffsetLength);	
				}
			}
			if (labelSensorMode!=null && labelSensorMode.equals("CONT")) {
				retVal = String.format("%s,0+%s", labelLength, labelOffsetLength);
			}
		}
		return retVal;
	}

	protected static String prepareCommand() throws Exception {
		String pathFilePrint = ManagerConfiguration.userPrefDirPath + File.separator + nomeFileStampaAPPLET;
		logger.info("properties.getExepath() " + properties.getExepath());
		logger.info("parameters.getNomeStampante() " + parameters.getNomeStampante());
		
		if( properties.getExepath()==null || parameters.getNomeStampante()==null)
			return null;
		String execPath = properties.getExepath() + parameters.getNomeStampante();
		execPath = execPath.replace("%1", pathFilePrint);
		execPath = execPath.replace("%%", "%");
		File lFile = new File(pathFilePrint);
		if (!lFile.exists()){
			boolean lBoolean = lFile.createNewFile();
			if (!lBoolean){
				throw new Exception("Impossibile creare il file " + pathFilePrint);
			}
		}
		return execPath;
	}

}
