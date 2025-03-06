package it.eng.hybrid.module.stampaEtichette.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean.TipoStampa;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.exception.InitException;

public class ParameterConfigurator {

	public final static Logger logger = Logger.getLogger(ParameterConfigurator.class);

	private static String TESTO_IN_CHIARO = "testo";
	private static String BARCODE_APPLET = "barcode";
	private static String TESTO_IN_CHIARO_BARCODE = "testoBarcode";
	private static String TESTO_REPERTORIO = "testoRepertorio";
	private static String TESTO_FALDONE = "testoFaldone";
	private static String BARCODE_FALDONE = "barcodeFaldone";
	private static String NUMERO_COPIE = "numeroCopie";
	private static String CALLBACKCLOSE = "callbackClose";
	private static String CALLBACKCANCEL = "callBackCancel";
	private static String PROPERTIES_SERVLET = "propertiesServlet";
	private static String PDF_SERVLET = "pdfServlet";
	private static String ID_UTENTE = "idUtente";
	private static String TIPO_STAMPA = "tipoStampa";
	private static String NOME_STAMPANTE = "nomeStampante";
	private static String ID_SCHEMA = "idSchema";
	private static String ID_DOMINIO = "idDominio";
	private static String PRINT_CODE_IN_SECOND_LABEL = "printCodeInSecondLabel";
	private static String BARCODE_ENCODING = "barcodeEncoding";
	private static String NOME_PORTA_STAMPA_PORT = "nomePortaStampaPort";
	private static String COMANDO_TEST_STAMPA_PORT = "comandoTestStampaPort";

	private ParameterBean params;
	private Map<String, String> props;

	private static ParameterConfigurator instance;

	public static ParameterConfigurator getInstance(Map<String, String> props) throws InitException {
		// if (instance == null) {
		instance = new ParameterConfigurator(props);
		// }
		return instance;
	}
	
	public static ParameterConfigurator getInstance() {
		return instance;
	}

	private ParameterConfigurator() {

	}

	private ParameterConfigurator(Map<String, String> props) throws InitException {
		ParameterConfigurator lParameterConfigurator = new ParameterConfigurator();
		ParameterBean lParameterBean = new ParameterBean();
		try {
			lParameterBean.setNumeroCopie(Integer.valueOf(props.get(NUMERO_COPIE)));
		} catch (NumberFormatException e) {
			throw new InitException("Il numero copie non è un numero valido");
		}
		int numeroCopie = lParameterBean.getNumeroCopie();
		logger.info("NumeroCopie " + numeroCopie);
		List<TestoBarcodeBean> lTesti = new ArrayList<TestoBarcodeBean>();
		for (int i = 1; i < numeroCopie + 1; i++) {
			TestoBarcodeBean lTestoBarcodeBean = new TestoBarcodeBean();
			lTestoBarcodeBean.setCounter(i);
			lTestoBarcodeBean.setTesto(props.get(TESTO_IN_CHIARO + "" + i));
			lTestoBarcodeBean.setBarcode(props.get(BARCODE_APPLET + "" + i));
			lTestoBarcodeBean.setTestoBarcode(props.get(TESTO_IN_CHIARO_BARCODE + "" + i));
			lTestoBarcodeBean.setTestoRepertorio(props.get(TESTO_REPERTORIO + "" + i));
			lTestoBarcodeBean.setTestoFaldone(props.get(TESTO_FALDONE + "" + i));
			lTestoBarcodeBean.setBarcodeFaldone(props.get(BARCODE_FALDONE + "" + i));
			lTesti.add(lTestoBarcodeBean);
		}
		lParameterBean.setTesto(lTesti);
		lParameterBean.setCallbackClose(props.get(CALLBACKCLOSE));
		logger.info("CallbackClose: " + lParameterBean.getCallbackClose());
		lParameterBean.setCallbackCancel(props.get(CALLBACKCANCEL));
		logger.info("CallbackCancel: " + lParameterBean.getCallbackCancel());
		lParameterBean.setIdUtente(props.get(ID_UTENTE));
		logger.info("IdUtente: " + lParameterBean.getIdUtente());
		lParameterBean.setPdfServlet(props.get(PDF_SERVLET));
		logger.info("PdfServlet: " + lParameterBean.getPdfServlet());
		lParameterBean.setPropertiesServlet(props.get(PROPERTIES_SERVLET));
		logger.info("PropertiesServlet: " + lParameterBean.getPropertiesServlet());
		lParameterBean.setTipoStampa(TipoStampa.getRealValue(props.get(TIPO_STAMPA)));
		logger.info("TipoStampa: " + lParameterBean.getTipoStampa());
		lParameterBean.setNomeStampante(props.get(NOME_STAMPANTE));
		logger.info("NomeStampante: " + lParameterBean.getNomeStampante());
		lParameterBean.setIdSchema(props.get(ID_SCHEMA));
		logger.info("IdSchema: " + lParameterBean.getIdSchema());
		lParameterBean.setIdDominio(props.get(ID_DOMINIO));
		logger.info("IdDominio: " + lParameterBean.getIdDominio());
		lParameterBean.setPrintCodeInSecondLabel(props.get(PRINT_CODE_IN_SECOND_LABEL));
		logger.info("PrintCodeInSecondLabel: " + lParameterBean.getPrintCodeInSecondLabel());
		lParameterBean.setBarcodeEncoding(props.get(BARCODE_ENCODING));
		logger.info("BarcodeEncoding: " + lParameterBean.getBarcodeEncoding());
		lParameterBean.setNomePortaStampaPort(props.get(NOME_PORTA_STAMPA_PORT));
		logger.info("NomePortaStampaPort: " + lParameterBean.getNomePortaStampaPort());
		lParameterBean.setComandoTestStampaPort(props.get(COMANDO_TEST_STAMPA_PORT));
		logger.info("ComandoTestStampaPort: " + lParameterBean.getComandoTestStampaPort());
		
		lParameterConfigurator.setParams(lParameterBean);
		instance = lParameterConfigurator;
		params = lParameterBean;
		this.props = props;

		logger.info("***************************************************");
		logger.info("PARAMETRI LETTI");
		logger.info("***************************************************");
	}

	public ParameterBean getParams() {
		return params;
	}

	public void setParams(ParameterBean params) {
		this.params = params;
	}
	
	public Map<String, String> getProps() {
		return props;
	}

}
