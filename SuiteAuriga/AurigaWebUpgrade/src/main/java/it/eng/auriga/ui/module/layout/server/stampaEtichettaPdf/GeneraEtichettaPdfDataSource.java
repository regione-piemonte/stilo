/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.server.stampaEtichettaPdf.bean.GeneraEtichettaPdfBean;
import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;
import it.eng.hybrid.module.stampaEtichette.config.ParameterConfigurator;
import it.eng.hybrid.module.stampaEtichette.exception.InitException;
import it.eng.hybrid.module.stampaEtichette.util.PdfWriter;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id = "GeneraEtichettaPdfDataSource")
public class GeneraEtichettaPdfDataSource extends AbstractDataSource<GeneraEtichettaPdfBean, GeneraEtichettaPdfBean> {

	private final static Logger mLogger = Logger.getLogger(GeneraEtichettaPdfDataSource.class);

	public GeneraEtichettaPdfBean generaFileEtichette (GeneraEtichettaPdfBean bean) throws Exception {

		GeneraEtichettaPdfBean result = new GeneraEtichettaPdfBean ();
		// Prende i parametri nella mappa e li rifersa nel bean ParameterBean
		ParameterBean parameter = inizializzoParametri(bean.getlMapParams());
		// Prende le impostazioni di stampa delle etichette e le riversa nel bean PdfPropertiesBean
		PdfPropertiesBean pdfProperties = inizializzoProperties(parameter, bean.getImpostazioniPdf());
		// Genero il pdf
		File etichettaPdf = generaPDF(parameter, pdfProperties);
		String uriFilePdf = StorageImplementation.getStorage().store(etichettaPdf);
		InfoFileUtility lFileUtility = new InfoFileUtility();
		MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFilePdf).toURI().toString(), "Etichetta.pdf", false, null);
		result.setUriPdfResult(uriFilePdf);
		result.setInfoFile(lMimeTypeFirmaBean);
		
		return result;
		
	}
	
	private ParameterBean inizializzoParametri(Map<String, String> props) throws InitException {
		return ParameterConfigurator.getInstance(props).getParams();
	}

	private PdfPropertiesBean inizializzoProperties(ParameterBean parameter,String impostazioniPdf) throws IllegalAccessException, InvocationTargetException, IOException {
		PdfPropertiesBean lPdfPropertiesBean = new PdfPropertiesBean();
		Properties lProperties = new Properties();  // DEVO METTERE LE PROPERTIES DI STAMPA ETICHETTE
		// vedi initPdfProperties di ManagerConfiguration sul Bundle
		// leggere dall'utente impostazioni pdf

		lProperties = parsePropertiesString(impostazioniPdf);
		ManagerConfiguration.initPdfPropertiesBean(lPdfPropertiesBean, lProperties);
		return lPdfPropertiesBean;
	}
	
	private File generaPDF(ParameterBean pParameterBean, PdfPropertiesBean pdfPropertiesBean) throws Exception {
		mLogger.debug("Entro in printPdfLabel di StampaEtichetteApplication");
		File pdffile = File.createTempFile("temp", ".pdf");
		// Creo il pdf con le etichette
		String pdfFile = PdfWriter.writePdf(pdfPropertiesBean, pParameterBean, pdffile);
		mLogger.debug("Stringa pdfFile: " + pdfFile);
		File lFile = new File(pdfFile);
		return lFile;
	}
	

	private Properties parsePropertiesString(String s) throws IOException {
	    // grr at load() returning void rather than the Properties object
	    // so this takes 3 lines instead of "return new Properties().load(...);"
	    final Properties p = new Properties();
	    p.load(new StringReader(s));
	    return p;
	}
	
	private Map<String,String> convertRecordToMap (Record record) {
		Map<String,String> result = new HashMap <String,String> ();
		String[] attributes = record.getAttributes();
		for (int i=0;i<attributes.length;i+=2) {
			result.put(attributes[i], attributes[1+1]);
		}
		return result;
	}

	@Override
	public GeneraEtichettaPdfBean get(GeneraEtichettaPdfBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneraEtichettaPdfBean add(GeneraEtichettaPdfBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneraEtichettaPdfBean remove(GeneraEtichettaPdfBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneraEtichettaPdfBean update(GeneraEtichettaPdfBean bean, GeneraEtichettaPdfBean oldvalue) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginatorBean<GeneraEtichettaPdfBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(GeneraEtichettaPdfBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}