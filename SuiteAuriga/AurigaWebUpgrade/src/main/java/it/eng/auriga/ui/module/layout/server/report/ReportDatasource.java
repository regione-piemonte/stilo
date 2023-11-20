/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_statistiche.bean.DmpkStatisticheReportdoc_groupbysp_uoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.report.bean.AssegnatariXListaAccompRegBean;
import it.eng.auriga.ui.module.layout.server.report.bean.DatasetBean;
import it.eng.auriga.ui.module.layout.server.report.bean.ReportBean;
import it.eng.auriga.ui.module.layout.server.report.bean.StampaListaAccompagnatoriaProtocollazioniBean;
import it.eng.auriga.ui.module.layout.server.servlet.piechart.ReportResultBean;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkStatisticheReportdoc_groupbysp_uo;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "ReportDatasource")
public class ReportDatasource extends AbstractServiceDataSource<ReportBean, DatasetBean>{

	private static Logger logger = Logger.getLogger(ReportDatasource.class);
	
	@Override
	public DatasetBean call(ReportBean pInBean) throws Exception {
		SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DatasetBean lDatasetBean = new DatasetBean();
		DmpkStatisticheReportdoc_groupbysp_uoBean lDmpkStatisticheReportdoc_groupbysp_uoBean = 
			new DmpkStatisticheReportdoc_groupbysp_uoBean();
		lDmpkStatisticheReportdoc_groupbysp_uoBean.setDatadain(lSimpleDateFormat.format(pInBean.getDa()));
		lDmpkStatisticheReportdoc_groupbysp_uoBean.setDataain(lSimpleDateFormat.format(pInBean.getA()));
		//Se è diverso da -1 allora è una richiesta, altrimenti l'idsp viene ricavato dal token
		if (pInBean.getIdSoggetto()!=null)
			lDmpkStatisticheReportdoc_groupbysp_uoBean.setIdspaoouoreportonin(pInBean.getIdSoggetto());
		lDmpkStatisticheReportdoc_groupbysp_uoBean.setLivellodettreportin(pInBean.getLevel());
		String tipiRegistrazione = null;
		if(pInBean.getTipoDiRegistrazione() != null) {
			for(int i = 0; i < pInBean.getTipoDiRegistrazione().size(); i++) {
				if(tipiRegistrazione == null) {
					tipiRegistrazione = pInBean.getTipoDiRegistrazione().get(i);
				} else {
					tipiRegistrazione += "," + pInBean.getTipoDiRegistrazione().get(i);
				}
			}
		}
		lDmpkStatisticheReportdoc_groupbysp_uoBean.setTipiregistrazioniin(tipiRegistrazione);
		lDmpkStatisticheReportdoc_groupbysp_uoBean.setTiporeportin(pInBean.getTipoReport());
		DmpkStatisticheReportdoc_groupbysp_uo lDmpkStatisticheReportdoc_groupbysp_uo = 
			new DmpkStatisticheReportdoc_groupbysp_uo();

		StoreResultBean<DmpkStatisticheReportdoc_groupbysp_uoBean> result = lDmpkStatisticheReportdoc_groupbysp_uo.execute(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()), lDmpkStatisticheReportdoc_groupbysp_uoBean);
		if (result.isInError()){
			throw new Exception(result.getDefaultMessage());
		} else {
			lDmpkStatisticheReportdoc_groupbysp_uoBean = result.getResultBean();
		}
		lDatasetBean.setTitle(lDmpkStatisticheReportdoc_groupbysp_uoBean.getReporttitleout());
		String xmlOut = lDmpkStatisticheReportdoc_groupbysp_uoBean.getReportcontentsxmlout();

		List<ReportResultBean> lList = XmlListaUtility.recuperaLista(xmlOut, ReportResultBean.class);
		lDatasetBean.setDataset(lList);
		return lDatasetBean;
	}
	
	public StampaListaAccompagnatoriaProtocollazioniBean stampaListaAccompagnatoriaProtocollazioni(StampaListaAccompagnatoriaProtocollazioniBean input) throws Exception {
		
		StampaListaAccompagnatoriaProtocollazioniBean result = new StampaListaAccompagnatoriaProtocollazioniBean();
		result.setFoglioGenerato(false);
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		// Recupero l'uri del modello della copertina
		DmpkModelliDocExtractvermodello retrieveVersion = new DmpkModelliDocExtractvermodello();
		DmpkModelliDocExtractvermodelloBean modelloBean = new DmpkModelliDocExtractvermodelloBean();
		modelloBean.setCodidconnectiontokenin(loginBean.getToken());
		modelloBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		modelloBean.setNomemodelloin("LISTA_ACCOMPAGNATORIA_PROT");
		
		StoreResultBean<DmpkModelliDocExtractvermodelloBean> resultModello = retrieveVersion.execute(getLocale(), loginBean, modelloBean);
		if (resultModello.isInError()){
			result.setInError(true);
			result.setErrorMessage(StringUtils.isNotBlank(resultModello.getDefaultMessage()) ? resultModello.getDefaultMessage() : "Non è stato possibile recuperare il modello della lista accompagnatoria protocollazioni");
			logger.error("Non è stato possibile recuperare il modello della lista accompagnatoria protocollazioni (nome modello: " + modelloBean.getNomemodelloin());
			return result;
		}
		
		String uriModelloCopertinaDaCompilare = resultModello.getResultBean().getUriverout();
		
		DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		
		List<File> pdfDaUnire = new ArrayList<File>();
		
		ArrayList<String> listaIdUo = new ArrayList<String>();		
		if(input.getFlgDestinatariSelezionati() != null && input.getFlgDestinatariSelezionati()) {
//			StringTokenizer st = new StringTokenizer(input.getListaIdUO(), ";");
//			while (st.hasMoreElements()) {
//				listaIdUo.add(st.nextToken());
//			}			
			listaIdUo = input.getListaDestinatari();
		} else if(input.getFlgDestinatariTutti() != null && input.getFlgDestinatariTutti()) {
			LoadComboAssegnatariXListaAccompRegDatasource lLoadComboAssegnatariXListaAccompRegDatasource = new LoadComboAssegnatariXListaAccompRegDatasource();
			try {
				lLoadComboAssegnatariXListaAccompRegDatasource.setSession(getSession());
				Map<String,String> extraparams = new HashMap<String,String>();
				extraparams.put("dtInizioVld", input.getDtInizio() != null ? new SimpleDateFormat(FMT_STD_DATA).format(input.getDtInizio()) : "");
				extraparams.put("dtFineVld", input.getDtFine() != null ? new SimpleDateFormat(FMT_STD_DATA).format(input.getDtFine()) : "");
				if(input.getFlgProtUtenteSelezionato() != null && input.getFlgProtUtenteSelezionato()) {
					extraparams.put("idUserAss", input.getUtenteProtocollazione());
				} else if(input.getFlgProtSoloMie() != null && input.getFlgProtSoloMie()) {
					String idUser = loginBean.getIdUser() != null ? String.valueOf(loginBean.getIdUser()) : "";
					extraparams.put("idUserAss", StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? loginBean.getIdUserLavoro() : idUser);
				} else if(input.getFlgProtDiTutti() != null && input.getFlgProtDiTutti()) {
					extraparams.put("idUserAss", "");
				}
				lLoadComboAssegnatariXListaAccompRegDatasource.setExtraparams(extraparams);
				PaginatorBean<AssegnatariXListaAccompRegBean> lPaginatorBean = lLoadComboAssegnatariXListaAccompRegDatasource.fetch(new AdvancedCriteria(), null, null, null);
				if(lPaginatorBean.getData() != null && lPaginatorBean.getData().size() > 0) {
					for(AssegnatariXListaAccompRegBean assegnatario : lPaginatorBean.getData()) {
						listaIdUo.add(assegnatario.getIdUo());	
					}
				}
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}			
		}
							
		for (String idUo : listaIdUo) {
			
			String attributiAddListaAccompagnatoria = creaAttributiAddListaAccompagnatoria(input, idUo);
			
			lGetdatixgendamodelloInput.setNomemodelloin("LISTA_ACCOMPAGNATORIA_PROT");
			lGetdatixgendamodelloInput.setAttributiaddin(attributiAddListaAccompagnatoria);
			
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
					lGetdatixgendamodelloInput);
			
			if (lGetdatixgendamodelloOutput.isInError()) {
				logger.error("Non è stato possibile generare la lista accompagnatoria protocollazioni per la UO " + idUo + " (attributiAdd: " + attributiAddListaAccompagnatoria + ")");
				result.setErrorMessage("Non è stato possibile generare la lista accompagnatoria protocollazioni per tutte le UO");
				result.setInError(true);
			} else {
				String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
	
				logger.debug("Dati folder da iniettare nel modello: " + templateValues);
				
				File file = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModelloCopertinaDaCompilare, null, templateValues, getSession());
				pdfDaUnire.add(file);
			}
		}
		
		if (!pdfDaUnire.isEmpty()){
			File fileListaAccompagnatoria = File.createTempFile("listaAccompagnatoria", ".pdf");
			FileOutputStream output = new FileOutputStream(fileListaAccompagnatoria);
			
			MergeDocument mergeDoc = new MergeDocument();
			mergeDoc.mergeDocument(pdfDaUnire, output, false);
			
			String uriListaAccompagnatoria = StorageImplementation.getStorage().store(fileListaAccompagnatoria, new String[]{});
			
			result.setUri(uriListaAccompagnatoria);
			result.setNomeFile("Lista accompagnatoria protocollazioni.pdf");
			
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileListaAccompagnatoria));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileListaAccompagnatoria.length());
			infoFile.setCorrectFileName(result.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(result.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), result.getNomeFile()));
			result.setInfoFile(infoFile);
			result.setFoglioGenerato(true);
		} else {
			logger.error("Non è stata generata nessuna lista accompagnatoria protocollazioni");
			result.setErrorMessage("Non è stata generata nessuna lista accompagnatoria protocollazioni");
			result.setFoglioGenerato(false);
			return result;
		}
		
		return result;
	}
	
	private String creaAttributiAddListaAccompagnatoria(StampaListaAccompagnatoriaProtocollazioniBean input, String idUo) throws JAXBException{
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		SezioneCache lSezioneCache = new SezioneCache();
		
		Variabile varUtenteProtocollazione = new Variabile();
		varUtenteProtocollazione.setNome("UtenteProtocollazione");
		if(input.getFlgProtUtenteSelezionato() != null && input.getFlgProtUtenteSelezionato()) {
			varUtenteProtocollazione.setValoreSemplice(input.getUtenteProtocollazione());
		} else if(input.getFlgProtSoloMie() != null && input.getFlgProtSoloMie()) {
			String idUtente = loginBean.getIdUser() != null ? String.valueOf(loginBean.getIdUser()) : "";
			varUtenteProtocollazione.setValoreSemplice(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? loginBean.getIdUserLavoro() : idUtente);
		} else if(input.getFlgProtDiTutti() != null && input.getFlgProtDiTutti()) {
			varUtenteProtocollazione.setValoreSemplice("");
		}
		lSezioneCache.getVariabile().add(varUtenteProtocollazione);
		
		Variabile varProtocolliDal = new Variabile();
		varProtocolliDal.setNome("ProtocolliDal");
		varProtocolliDal.setValoreSemplice(input.getDtInizio() != null ? new SimpleDateFormat(FMT_STD_DATA).format(input.getDtInizio()) : "");
		lSezioneCache.getVariabile().add(varProtocolliDal);
		
		Variabile varProtocolliAl = new Variabile();
		varProtocolliAl.setNome("ProtocolliAl");
		varProtocolliAl.setValoreSemplice(input.getDtFine() != null ? new SimpleDateFormat(FMT_STD_DATA).format(input.getDtFine()) : "");
		lSezioneCache.getVariabile().add(varProtocolliAl);
		
		Variabile varUODestinataria = new Variabile();
		varUODestinataria.setNome("UODestinataria");
		varUODestinataria.setValoreSemplice(idUo);		
		lSezioneCache.getVariabile().add(varUODestinataria);
				
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		lMarshaller.marshal(lSezioneCache, lStringWriter);
		return lStringWriter.toString();
	}
	
}
