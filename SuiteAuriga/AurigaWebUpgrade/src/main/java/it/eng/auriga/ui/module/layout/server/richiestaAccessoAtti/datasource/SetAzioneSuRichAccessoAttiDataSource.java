/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreSetazionesurichaccessoattiBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.AzioneSuRichAccessoAttiBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.DatiOperazioneSuRichAccessoAttiXmlBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.EsitoOperazioneSuRichAccessoAttiXmlBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiXmlBean;
import it.eng.client.DmpkCoreSetazionesurichaccessoatti;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author cristiano
 *
 */

@Datasource(id = "SetAzioneSuRichAccessoAttiDataSource")
public class SetAzioneSuRichAccessoAttiDataSource extends AbstractDataSource<AzioneSuRichAccessoAttiBean, AzioneSuRichAccessoAttiBean> {

	private static final Logger log = Logger.getLogger(SetAzioneSuRichAccessoAttiDataSource.class);

	@Override
	public AzioneSuRichAccessoAttiBean add(AzioneSuRichAccessoAttiBean bean) throws Exception {

		
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		for(RichiestaAccessoAttiBean item : bean.getListaRecord()){

			DmpkCoreSetazionesurichaccessoattiBean lDmpkCoreSetazionesurichaccessoattiBean = new DmpkCoreSetazionesurichaccessoattiBean();
			lDmpkCoreSetazionesurichaccessoattiBean.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkCoreSetazionesurichaccessoattiBean.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			
			lDmpkCoreSetazionesurichaccessoattiBean.setCodoperazionein(bean.getCodOperazione());
			lDmpkCoreSetazionesurichaccessoattiBean.setListaidudin(buildListaIdUdXml(item));
			lDmpkCoreSetazionesurichaccessoattiBean.setMotiviin(bean.getMotivi());
			lDmpkCoreSetazionesurichaccessoattiBean.setDatioperazionein(buildDatiOperazioneXml(bean));
	
			DmpkCoreSetazionesurichaccessoatti lDmpkCoreSetazionesurichaccessoatti = new DmpkCoreSetazionesurichaccessoatti();
			StoreResultBean<DmpkCoreSetazionesurichaccessoattiBean> lStoreResultBean = lDmpkCoreSetazionesurichaccessoatti.execute(getLocale(), loginBean, lDmpkCoreSetazionesurichaccessoattiBean);
	
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				if (lStoreResultBean.isInError()) {
					if(StringUtils.isNotBlank(lStoreResultBean.getResultBean().getEsitiout())) {
						List<EsitoOperazioneSuRichAccessoAttiXmlBean> listaEsitiOperazioneSuRichAccessoAtti = XmlListaUtility.recuperaLista(lStoreResultBean.getResultBean().getEsitiout(), EsitoOperazioneSuRichAccessoAttiXmlBean.class);
						if(listaEsitiOperazioneSuRichAccessoAtti != null) {
							for(EsitoOperazioneSuRichAccessoAttiXmlBean esito : listaEsitiOperazioneSuRichAccessoAtti) {
								if(esito.getEsitoOperazione() != null && esito.getEsitoOperazione().equalsIgnoreCase("KO")) {
									errorMessages.put(esito.getIdUd(), esito.getDatiPrincipaliRichiesta() + "@#@" + esito.getMessaggioErrore());
								}
							}			
						}
					}else{
						throw new StoreException(lStoreResultBean);
					}
				} 
			}
		}
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
	
	public AzioneSuRichAccessoAttiBean generaFoglioPrelievo(AzioneSuRichAccessoAttiBean bean) throws Exception {
		
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// AzioneSuRichAccessoAttiBean result = new AzioneSuRichAccessoAttiBean();
		bean.setRecuperoModelloInError(false);
		
		// Recupero l'uri del modello della copertina
		DmpkModelliDocExtractvermodello retrieveVersion = new DmpkModelliDocExtractvermodello();
		DmpkModelliDocExtractvermodelloBean modelloBean = new DmpkModelliDocExtractvermodelloBean();
		modelloBean.setCodidconnectiontokenin(loginBean.getToken());
		modelloBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		modelloBean.setNomemodelloin("FOGLIO_PRELIEVO_DA_ARCHIVIO");
		StoreResultBean<DmpkModelliDocExtractvermodelloBean> resultModello = retrieveVersion.execute(getLocale(), loginBean, modelloBean);
		
		if (resultModello.isInError()){
			String errorMessage = (StringUtils.isNotBlank(resultModello.getDefaultMessage())) ? resultModello.getDefaultMessage() : "Non è stato possibile recuperare il modello del foglio prelievo";
			bean.setRecuperoModelloErrorMessage(errorMessage);
			bean.setRecuperoModelloInError(true);
			return bean;
		}
		
		String uriModelloCopertinaDaCompilare = resultModello.getResultBean().getUriverout();
		
		DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		List<File> pdfDaUnire = new ArrayList<File>();
		
		if (bean.getListaRecord() != null){
			for (RichiestaAccessoAttiBean richiestaAccessoAttiBean : bean.getListaRecord()) {
				
	
					
					String strElencoIdFolderAttiRich = richiestaAccessoAttiBean.getElencoIdFolderAttiRich();
					String[] elencoIdFolderAttiRich = new String[0];
					if (StringUtils.isNotBlank(strElencoIdFolderAttiRich)){
						elencoIdFolderAttiRich = strElencoIdFolderAttiRich.split(";");
					}
					
					for (String idFolderAttoRichiesto : elencoIdFolderAttiRich) {
						
						String attributiAddIn = creaAttributiAddIn(idFolderAttoRichiesto);
						
						lGetdatixgendamodelloInput.setIdobjrifin(richiestaAccessoAttiBean.getIdUd());
						lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
						lGetdatixgendamodelloInput.setNomemodelloin("FOGLIO_PRELIEVO_DA_ARCHIVIO");
						lGetdatixgendamodelloInput.setAttributiaddin(attributiAddIn);
						
						StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
								lGetdatixgendamodelloInput);
						
						if (lGetdatixgendamodelloOutput.isInError()) {
							String errorMessage = StringUtils.isNotBlank(lGetdatixgendamodelloOutput.getDefaultMessage()) ? lGetdatixgendamodelloOutput.getDefaultMessage() : "Errore nella generazione del modello";
							errorMessages.put(richiestaAccessoAttiBean.getIdUd(), richiestaAccessoAttiBean.getProtocollo() + "@#@" + errorMessage);
						} else {
						
							String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
				
							log.debug("Dati folder da iniettare nel modello: " + templateValues);
							
							File file = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModelloCopertinaDaCompilare, "DOCX_FORM", templateValues, getSession());
							pdfDaUnire.add(file);
						}
					}
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		if (pdfDaUnire.size() > 0){
			File foglioPrelievo = File.createTempFile("Foglio_Prelievo", ".pdf");
			FileOutputStream output = new FileOutputStream(foglioPrelievo);
			
			MergeDocument mergeDoc = new MergeDocument();
			mergeDoc.mergeDocument(pdfDaUnire, output, false);
			
			String uriFoglioPrelievo = StorageImplementation.getStorage().store(foglioPrelievo, new String[]{});
			
			bean.setUriFileFoglioPrelievo(uriFoglioPrelievo);
			bean.setNomeFileFoglioPrelievo("Foglio di prelievo");
			
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(foglioPrelievo));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(foglioPrelievo.length());
			infoFile.setCorrectFileName(bean.getNomeFileFoglioPrelievo());
			File realFile = StorageImplementation.getStorage().getRealFile(bean.getUriFileFoglioPrelievo());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), bean.getNomeFileFoglioPrelievo()));
			bean.setInfoFileFoglioPrelievo(infoFile);
		} else {
			log.error("Non è stato generato nessun foglio prelievo (provare a verificare la presenza dei codici UDC)");
			String errorMessage = ("Non è stato generato nessun foglio prelievo");
			bean.setRecuperoModelloErrorMessage(errorMessage);
			bean.setRecuperoModelloInError(true);
			return bean;
		}
		
		return bean;
			
	}
	
	private String buildListaIdUdXml(RichiestaAccessoAttiXmlBean azioneSuRichAccessoAttiBean) throws Exception {
		List<SimpleValueBean> listaIdUd = new ArrayList<SimpleValueBean>();

		SimpleValueBean lSimpleValueBean = new SimpleValueBean();
		lSimpleValueBean.setValue(azioneSuRichAccessoAttiBean.getIdUd());
		listaIdUd.add(lSimpleValueBean);
		
		return new XmlUtilitySerializer().bindXmlList(listaIdUd);
	}
			
	private String buildDatiOperazioneXml(AzioneSuRichAccessoAttiBean bean) throws Exception {
		
		DatiOperazioneSuRichAccessoAttiXmlBean lDatiOperazioneSuRichAccessoAttiXmlBean = new DatiOperazioneSuRichAccessoAttiXmlBean();
		lDatiOperazioneSuRichAccessoAttiXmlBean.setDataAppuntamento(bean.getDataAppuntamento());
		if (StringUtils.isNotBlank(bean.getOrarioAppuntamento())){
			String orario = bean.getOrarioAppuntamento().substring(bean.getOrarioAppuntamento().indexOf(":") - 2, bean.getOrarioAppuntamento().indexOf(":") + 3);
			lDatiOperazioneSuRichAccessoAttiXmlBean.setOrarioAppuntamento(orario);
		}
		lDatiOperazioneSuRichAccessoAttiXmlBean.setDataPrelievo(bean.getDataPrelievo());
		lDatiOperazioneSuRichAccessoAttiXmlBean.setDataRestituzionePrelievo(bean.getDataRestituzione());
		
		return new XmlUtilitySerializer().bindXml(lDatiOperazioneSuRichAccessoAttiXmlBean);
	}
	
	private String creaAttributiAddIn(String idFolderAttoRichiesto) throws JAXBException{
		SezioneCache lSezioneCache = new SezioneCache();
		Variabile varCodApplicazioni = new Variabile();
		varCodApplicazioni.setNome("IDFOLDER_ATTO_RICHIESTO");
		varCodApplicazioni.setValoreSemplice(idFolderAttoRichiesto);
		lSezioneCache.getVariabile().add(varCodApplicazioni);
		
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		lMarshaller.marshal(lSezioneCache, lStringWriter);
		return lStringWriter.toString();
	}

	@Override
	public AzioneSuRichAccessoAttiBean get(AzioneSuRichAccessoAttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AzioneSuRichAccessoAttiBean remove(AzioneSuRichAccessoAttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AzioneSuRichAccessoAttiBean update(AzioneSuRichAccessoAttiBean bean, AzioneSuRichAccessoAttiBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<AzioneSuRichAccessoAttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AzioneSuRichAccessoAttiBean bean) throws Exception {
		return null;
	}			

}
