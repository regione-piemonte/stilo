package it.eng.applet.configuration;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

import it.eng.applet.configuration.ParameterBean.TipoStampa;
import it.eng.applet.exception.InitException;
import it.eng.applet.util.LogWriter;

public class ParameterConfigurator {

	private static String TESTO_IN_CHIARO = "TESTO";
	private static String BARCODE_APPLET = "BARCODE";
	private static String TESTO_IN_CHIARO_BARCODE = "TESTOBARCODE";
	private static String TESTO_REPERTORIO = "TESTOREPERTORIO";
	private static String TESTO_FALDONE = "TESTOFALDONE";
	private static String BARCODE_FALDONE = "BARCODEFALDONE";
	private static String NUMERO_COPIE = "NUMEROCOPIE";
	private static String CALLBACKCLOSE = "CALLBACKCLOSE";
	private static String AUTOCLOSE = "AUTOCLOSE";
	private static String PROPERTIES_SERVLET = "PROPERTIESSERVLET";
	private static String PDF_SERVLET = "PDFSERVLET";
	private static String ID_UTENTE = "IDUTENTE";
	private static String TIPO_STAMPA = "TIPOSTAMPA";
	private static String NOME_STAMPANTE = "NOMESTAMPANTE";
	private static String ID_SCHEMA = "IDSCHEMA";
	private static String ID_DOMINIO = "IDDOMINIO";
	private static String PRINT_CODE_IN_SECOND_LABEL= "PRINTCODEINSECONDLABEL";
	private static String BARCODE_ENCODING= "BARCODEENCODING";

	private ParameterBean params;
	private Applet applet;

	private static ParameterConfigurator instance;

	public static ParameterConfigurator getInstance(Applet pApplet) throws InitException{
		if (instance == null){
			instance = new ParameterConfigurator(pApplet);
		}
		return instance;
	}


	private ParameterConfigurator(Applet pApplet) throws InitException{
		ParameterConfigurator lParameterConfigurator = new ParameterConfigurator();
		applet = pApplet;
		ParameterBean lParameterBean = new ParameterBean();
		try {
			lParameterBean.setNumeroCopie(Integer.valueOf(pApplet.getParameter(NUMERO_COPIE)));
		} catch (NumberFormatException e){
			throw new InitException("Il numero copie non � un numero valido");
		}
		int numeroCopie = lParameterBean.getNumeroCopie();
		List<TestoBarcodeBean> lTesti = new ArrayList<TestoBarcodeBean>();
		for (int i = 1; i<numeroCopie + 1; i++){
			TestoBarcodeBean lTestoBarcodeBean = new TestoBarcodeBean();
			lTestoBarcodeBean.setCounter(i);
			lTestoBarcodeBean.setTesto(getProperty(TESTO_IN_CHIARO + "" + i));
			lTestoBarcodeBean.setBarcode(getProperty(BARCODE_APPLET + "" + i));
			lTestoBarcodeBean.setTestoBarcode(getProperty(TESTO_IN_CHIARO_BARCODE + "" + i));
			lTestoBarcodeBean.setTestoRepertorio(getProperty(TESTO_REPERTORIO + "" + i));
			lTestoBarcodeBean.setTestoFaldone(getProperty(TESTO_FALDONE + "" + i));
			lTestoBarcodeBean.setBarcodeFaldone(getProperty(BARCODE_FALDONE + "" + i));
			lTesti.add(lTestoBarcodeBean);
		}
		lParameterBean.setTesto(lTesti);
		lParameterBean.setCallbackClose(getProperty(CALLBACKCLOSE));
		lParameterBean.setIdUtente(getProperty(ID_UTENTE));
		lParameterBean.setPdfServlet(getProperty(PDF_SERVLET));
		lParameterBean.setPropertiesServlet(getProperty(PROPERTIES_SERVLET));
		lParameterBean.setTipoStampa(TipoStampa.getRealValue(getProperty(TIPO_STAMPA)));
		lParameterBean.setNomeStampante(getProperty(NOME_STAMPANTE));
		lParameterBean.setIdSchema(getProperty(ID_SCHEMA));
		String autoCloseStr = getProperty(AUTOCLOSE);
		if( autoCloseStr!=null ){
			try{
				LogWriter.writeLog("Parametro autoclose " + autoCloseStr );
				lParameterBean.setAutoClose(Boolean.valueOf(autoCloseStr));
			} catch (Exception e){
				throw new InitException("AutoClose non � un valore booleano");
			}
		} else {
			LogWriter.writeLog("Imposto per default il parametro autoclose a true " );
			lParameterBean.setAutoClose(true);
		}
		lParameterBean.setIdDominio(getProperty(ID_DOMINIO));
		lParameterBean.setPrintCodeInSecondLabel(getProperty(PRINT_CODE_IN_SECOND_LABEL));
		lParameterBean.setBarcodeEncoding(getProperty(BARCODE_ENCODING));
		lParameterConfigurator.setParams(lParameterBean);
		instance = lParameterConfigurator;
		params = lParameterBean;
	}

	private ParameterConfigurator() {
		// TODO Auto-generated constructor stub
	}


	public String getProperty(String pStrProp) throws InitException{
		try {
			String propValue = applet.getParameter(pStrProp);
			LogWriter.writeLog("Leggo " + pStrProp + " = " + propValue);
			return propValue;
		} catch (Exception e){
			throw new InitException("Parametro " + pStrProp + " non trovato");
		}
	}


	public ParameterBean getParams() {
		return params;
	}


	public void setParams(ParameterBean params) {
		this.params = params;
	}


}
