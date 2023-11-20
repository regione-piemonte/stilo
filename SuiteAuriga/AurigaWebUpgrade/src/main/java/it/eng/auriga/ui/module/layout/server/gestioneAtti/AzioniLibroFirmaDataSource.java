/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2InviarimuovidoclibrofirmaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.DocInfoLibroFirma;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.OperazioneMassivaAttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.OperazioneMassivaInvioLibroFirmaBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.OperazioneMassivaProcedimentiBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiInIter.bean.ProcedimentiInIterBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.CallExecAttDatasource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.NuovaPropostaAtto2CompletaDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.client.DmpkCore2Inviarimuovidoclibrofirma;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AzioniLibroFirmaDataSource")
public class AzioniLibroFirmaDataSource extends AbstractDataSource<AttiBean,AttiBean>  {

	private static final Logger log = Logger.getLogger(AzioniLibroFirmaDataSource.class);
	
	public OperazioneMassivaInvioLibroFirmaBean mandaALibroFirma (OperazioneMassivaAttiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(AttiBean atto : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(atto.getUnitaDocumentariaId());
			docInfo.setIdProcess(atto.getIdProcedimento());
			docInfo.setFlgPrevistaNumerazione(atto.getFlgPrevistaNumerazione());
			docInfo.setFlgGeneraFileUnionePerLibroFirma(atto.getFlgGeneraFileUnionePerLibroFirma());
			docInfo.setActivityName(atto.getActivityName());
			docInfo.setProssimoTaskAppongoFirmaVisto(atto.getProssimoTaskAppongoFirmaVisto());
			docInfo.setProssimoTaskRifiutoFirmaVisto(atto.getProssimoTaskRifiutoFirmaVisto());
			docInfo.setSegnatura(atto.getNumeroProposta() + " - " + atto.getTipoAtto());
			inputList.add(docInfo);
		}
		OperazioneMassivaInvioLibroFirmaBean result = new OperazioneMassivaInvioLibroFirmaBean();
		result.setListaRecord(inputList);
		return result;
	}
	
	public OperazioneMassivaInvioLibroFirmaBean mandaALibroFirmaDaProcedimentiInIter (OperazioneMassivaProcedimentiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			docInfo.setSegnatura(procedimentiInIterBean.getOggetto());
			docInfo.setSkipNumerazioneEGenerazioniDaModello(true);
			inputList.add(docInfo);
		}		
		OperazioneMassivaInvioLibroFirmaBean result = new OperazioneMassivaInvioLibroFirmaBean();
		result.setListaRecord(inputList);
		return result;
	}
	
	public OperazioneMassivaInvioLibroFirmaBean mandaALibroFirmaDaProcedimentiPersonali (OperazioneMassivaProcedimentiBean bean) throws Exception {
		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			docInfo.setSegnatura(procedimentiInIterBean.getOggetto());
			docInfo.setSkipNumerazioneEGenerazioniDaModello(true);
			inputList.add(docInfo);
		}
		OperazioneMassivaInvioLibroFirmaBean result = new OperazioneMassivaInvioLibroFirmaBean();
		result.setListaRecord(inputList);
		return result;
	}

	public OperazioneMassivaInvioLibroFirmaBean mandaALibroFirmaCommon(OperazioneMassivaInvioLibroFirmaBean inputBean) throws Exception{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		if (inputBean.getErrorMessages() != null) {
			errorMessages = inputBean.getErrorMessages();
		} else {
			errorMessages = new HashMap<String, String>();
			inputBean.setErrorMessages(errorMessages);
		}
				
		List<DocInfoLibroFirma> recordDaInviareLibroFirma = new ArrayList<DocInfoLibroFirma>();
		if (inputBean.getListaRecord() != null) {
			HashMap<String, String> errorMessagesCorretto = new HashMap<String, String>();
			Set<String> errorMessageskeySet = errorMessages.keySet();
			for (String key : errorMessageskeySet) {
				String newKey = key;
				for (DocInfoLibroFirma lDocInfoLibroFirma : inputBean.getListaRecord()) {
					if (StringUtils.isNotBlank(lDocInfoLibroFirma.getIdUd()) && lDocInfoLibroFirma.getIdUd().equalsIgnoreCase(key)) {
						newKey = lDocInfoLibroFirma.getSegnatura();
						break;
					}
				}
				errorMessagesCorretto.put(newKey, errorMessages.get(key));
			}
			errorMessages = errorMessagesCorretto;
		
			// Tolgo dalla input list tutti i record andati in errore
			for (DocInfoLibroFirma record : inputBean.getListaRecord()) {
				recordDaInviareLibroFirma.add(record);
			}
		}
		
		// La chiamata al libro firma la faccio solo se ho dei record da inviare (potrebbe accadere che ai passi precedenti siano andati tutti in errore)
		if (!recordDaInviareLibroFirma.isEmpty()) {
			String inputListStr = new XmlUtilitySerializer().bindXmlList(recordDaInviareLibroFirma);
			
			DmpkCore2InviarimuovidoclibrofirmaBean input = new DmpkCore2InviarimuovidoclibrofirmaBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setAzionein("invio");
			input.setDocinfoin(inputListStr);
			
			DmpkCore2Inviarimuovidoclibrofirma store = new DmpkCore2Inviarimuovidoclibrofirma();
			StoreResultBean<DmpkCore2InviarimuovidoclibrofirmaBean> output = store.execute(getLocale(), loginBean, input);
	
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				throw new StoreException(output);
			}
	
			if (errorMessages.isEmpty()) {
				errorMessages = null;
			}
	
			if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
				if (errorMessages == null) {
					errorMessages = new HashMap<String, String>();
				}
				StringReader sr = new StringReader(output.getResultBean().getEsitiout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
					if (v.get(3).equalsIgnoreCase("ko")) {
						errorMessages.put(v.get(2), v.get(4));
					}
				}
			}
		}
		
		inputBean.setErrorMessages(errorMessages);
		return inputBean;
	}
	
	public DocInfoLibroFirma effettuaNumerazionePerInvioALibroFirma(DocInfoLibroFirma attoBean) throws Exception {
		HashMap<String, String> errorMessages = null;
		if (attoBean.getErrorMessages() != null) {
			errorMessages = attoBean.getErrorMessages();
		} else {
			errorMessages = new HashMap<String, String>();
			attoBean.setErrorMessages(errorMessages);
		}
		// Verifico se devo saltare l'eventuale numerazione (ad esempio se provengo da ced o autotutele)
		if (attoBean.getSkipNumerazioneEGenerazioniDaModello() == null || !attoBean.getSkipNumerazioneEGenerazioniDaModello()) {
			AttProcBean lAttProcBean = new AttProcBean();
			lAttProcBean.setIdUd(attoBean.getIdUd());
			lAttProcBean.setIdProcess(attoBean.getIdProcess());
			lAttProcBean.setActivityName(attoBean.getActivityName());
			try {
				lAttProcBean = getCallExecAttDatasource().call(lAttProcBean);
			} catch (Exception e) {
				log.error("Errore nella chiamata alla CallExecAtt per invio a libro firma: " +  e.getMessage(), e);
				attoBean.setEsitoNumerazioneOk(false);
				errorMessages.put(attoBean.getIdUd(), "Errore nell'avanzamento del flusso");
				return attoBean;
			}
			
			String siglaRegistroAtto = lAttProcBean.getSiglaRegistroAtto();
			String siglaRegistroAtto2 = lAttProcBean.getSiglaRegistroAtto2();
			attoBean.setEsitoNumerazioneOk(true);
			if (StringUtils.isNotBlank(siglaRegistroAtto) || StringUtils.isNotBlank(siglaRegistroAtto2)) {
				// La chiamata a effettuaNumerazione aggiorna errorMessages in caso di errori
				boolean esitoNumerazioneOk = effettuaNumerazione(lAttProcBean, errorMessages);
				if (!esitoNumerazioneOk) {
					attoBean.setEsitoNumerazioneOk(esitoNumerazioneOk);
				}
			}
			attoBean.setAttoProcedimento(lAttProcBean);
			attoBean.setErrorMessages(errorMessages);
		} else {
			attoBean.setEsitoNumerazioneOk(true);
		}
		return attoBean;
	}
	
	private boolean effettuaNumerazione(AttProcBean lAttProcBean, HashMap<String, String> errorMessages) throws Exception {
		boolean effettuaNumerazione = false;
		List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
		if(StringUtils.isNotBlank(lAttProcBean.getSiglaRegistroAtto())) {
			TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();			
			lTipoNumerazioneBean.setSigla(lAttProcBean.getSiglaRegistroAtto());
			lTipoNumerazioneBean.setCategoria("R");		
			listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
			effettuaNumerazione = true;
		}
		if(StringUtils.isNotBlank(lAttProcBean.getSiglaRegistroAtto2())) {
			TipoNumerazioneBean lTipoNumerazione2Bean = new TipoNumerazioneBean();			
			lTipoNumerazione2Bean.setSigla(lAttProcBean.getSiglaRegistroAtto2());
			lTipoNumerazione2Bean.setCategoria("R");		
			listaTipiNumerazioneDaDare.add(lTipoNumerazione2Bean);
			effettuaNumerazione = true;
		}
		
		if (effettuaNumerazione) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			String idUd = lAttProcBean.getIdUd();	
			
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtipotargetin("U");
			input.setIduddocin(new BigDecimal(idUd));
			
			CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
			lModificaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioneDaDare);
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));
			
			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
			if (output.isInError()) {	
				errorMessages.put(idUd, output.getDefaultMessage());
				return false;
			}
		}
		return true;
	}
	
	public DocInfoLibroFirma generaFileUnioneEAllegatiDaModelloPerInvioALibroFirma(DocInfoLibroFirma docInfoLibroFirma) throws Exception {
		HashMap<String, String> errorMessages = null;
		if (docInfoLibroFirma.getErrorMessages() != null) {
			errorMessages = docInfoLibroFirma.getErrorMessages();
		} else {
			errorMessages = new HashMap<String, String>();
			docInfoLibroFirma.setErrorMessages(errorMessages);
		}
		// Verifico se devo saltare le eventuali generazioni da modello (ad esempio se provengo da ced o autotutele)
		if (docInfoLibroFirma.getSkipNumerazioneEGenerazioniDaModello() == null || !docInfoLibroFirma.getSkipNumerazioneEGenerazioniDaModello()) {
			AttProcBean lAttProcBean = docInfoLibroFirma.getAttoProcedimento();
			boolean flgGeneraFileUnionePerLibroFirma = (StringUtils.isNotBlank(docInfoLibroFirma.getFlgGeneraFileUnionePerLibroFirma()) && "1".equalsIgnoreCase(docInfoLibroFirma.getFlgGeneraFileUnionePerLibroFirma()));
			NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();			
			NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean();
			lNuovaPropostaAtto2CompletaBean.setIdUd(docInfoLibroFirma.getIdUd());
			docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(true);
			try {
				lNuovaPropostaAtto2CompletaBean = lNuovaPropostaAtto2CompletaDataSource.get(lNuovaPropostaAtto2CompletaBean);
			} catch (Exception e) {
				log.error("Errore nella chiamata alla get di NuovaPropostaAtto2CompletaDataSource: " +  e.getMessage(), e);
				docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(false);
				errorMessages.put(docInfoLibroFirma.getIdUd(), "Errore nel recupero delle informazioni dell'unità documentale");
				return docInfoLibroFirma;
			}
			setNuovaPropostaAtto2CompletaBeanFromAttProcBean(lNuovaPropostaAtto2CompletaBean, lAttProcBean);
			if(flgGeneraFileUnionePerLibroFirma) {
				try {
					generaFileUnione(docInfoLibroFirma, lAttProcBean, lNuovaPropostaAtto2CompletaBean);
				} catch (Exception e) {
					log.error("Errore nella generazione del file unione per invio a libro firma: " +  e.getMessage(), e);
					docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(false);
					errorMessages.put(docInfoLibroFirma.getIdUd(), "Errore nella generazione del file unione");
					return docInfoLibroFirma;
				}
			}
			try {
				generaAllegatiGeneratiDaModello(docInfoLibroFirma, lAttProcBean, lNuovaPropostaAtto2CompletaBean);
			} catch (Exception e) {
				log.error("Errore nella generazione dei file allegati per invio a libro firma: " +  e.getMessage(), e);
				docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(false);
				errorMessages.put(docInfoLibroFirma.getIdUd(), "Errore nella generazione dei file allegati");
				return docInfoLibroFirma;
			}
			try {
				lNuovaPropostaAtto2CompletaDataSource.salvaPrimarioEAllegatiPerOperazioniMassiveDiAvanzamento(lNuovaPropostaAtto2CompletaBean);
			} catch (Exception e) {
				log.error("Errore nella chiamata a salvaPrimarioEAllegatiPerOperazioniMassiveDiAvanzamento di NuovaPropostaAtto2CompletaDataSource: " +  e.getMessage(), e);
				docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(false);
				errorMessages.put(docInfoLibroFirma.getIdUd(), "Errore nel salvataggio dei dati");
				return docInfoLibroFirma;
			}
		} else {
			docInfoLibroFirma.setEsitoGenerazioniDaModelloOk(true);
		}
		return docInfoLibroFirma;
	}
	
	private void generaFileUnione(DocInfoLibroFirma docInfoLibroFirma, AttProcBean lAttProcBean, NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean) throws Exception {
		String nomeFileUnione = lAttProcBean.getUnioneFileNomeFile();
		String nomeFileUnioneOmissis = lAttProcBean.getUnioneFileNomeFileOmissis();
		
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnione", nomeFileUnione);
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
		
		UnioneFileAttoBean lUnioneFileAttoBean = lNuovaPropostaAtto2CompletaDataSource.unioneFile(lNuovaPropostaAtto2CompletaBean);
		
		if(lUnioneFileAttoBean != null) {
			if (StringUtils.isNotBlank(lUnioneFileAttoBean.getUriVersIntegrale())) {
				lNuovaPropostaAtto2CompletaBean.setUriFilePrimario(lUnioneFileAttoBean.getUriVersIntegrale());
				lNuovaPropostaAtto2CompletaBean.setNomeFilePrimario(lUnioneFileAttoBean.getNomeFileVersIntegrale());
				lNuovaPropostaAtto2CompletaBean.setInfoFilePrimario(lUnioneFileAttoBean.getInfoFileVersIntegrale());
				lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimario(true);
			}
			if (StringUtils.isNotBlank(lUnioneFileAttoBean.getUri())) {
				lNuovaPropostaAtto2CompletaBean.setUriFilePrimarioOmissis(lUnioneFileAttoBean.getUri());
				lNuovaPropostaAtto2CompletaBean.setNomeFilePrimarioOmissis(lUnioneFileAttoBean.getNomeFile());
				lNuovaPropostaAtto2CompletaBean.setInfoFilePrimarioOmissis(lUnioneFileAttoBean.getInfoFile());
				lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimarioOmissis(true);
			}
		}
	}
	
	private void generaAllegatiGeneratiDaModello(DocInfoLibroFirma docInfoLibroFirma, AttProcBean lAttProcBean, NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean) throws Exception {
		String idUd = docInfoLibroFirma.getIdUd();
		String idProcedimento = docInfoLibroFirma.getIdProcess();
		String activityName = docInfoLibroFirma.getActivityName();
		String esito = docInfoLibroFirma.getProssimoTaskAppongoFirmaVisto();
		
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = getNuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("esitoTask", esito);
		lNuovaPropostaAtto2CompletaDataSource.generaAllegatiDaModelloPerOperazioniMassiveDiAvanzamento(lNuovaPropostaAtto2CompletaBean, idUd, idProcedimento, activityName, esito);
	}	
	
	private static void setNuovaPropostaAtto2CompletaBeanFromAttProcBean(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, AttProcBean lAttProcBean) {
		lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
		lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
		lNuovaPropostaAtto2CompletaBean.setIdModCopertina(lAttProcBean.getIdModCopertina() != null ? lAttProcBean.getIdModCopertina() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModCopertina(lAttProcBean.getNomeModCopertina() != null ? lAttProcBean.getNomeModCopertina() : "");
		lNuovaPropostaAtto2CompletaBean.setIdModCopertinaFinale(lAttProcBean.getIdModCopertinaFinale() != null ? lAttProcBean.getIdModCopertinaFinale() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModCopertinaFinale(lAttProcBean.getNomeModCopertinaFinale() != null ? lAttProcBean.getNomeModCopertinaFinale() : "");
		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparati(lAttProcBean.getIdModAllegatiParteIntSeparati() != null ? lAttProcBean.getIdModAllegatiParteIntSeparati() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparati(lAttProcBean.getNomeModAllegatiParteIntSeparati() != null ? lAttProcBean.getNomeModAllegatiParteIntSeparati() : "");
		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparati(lAttProcBean.getUriModAllegatiParteIntSeparati() != null ? lAttProcBean.getUriModAllegatiParteIntSeparati() : "");
		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparati(lAttProcBean.getTipoModAllegatiParteIntSeparati() != null ? lAttProcBean.getTipoModAllegatiParteIntSeparati() : "");
		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl() : "");
		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl() : "");
		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl() : "");
		lNuovaPropostaAtto2CompletaBean.setFlgAppendiceDaUnire(lAttProcBean.getFlgAppendiceDaUnire());		
		lNuovaPropostaAtto2CompletaBean.setIdModAppendice(lAttProcBean.getIdModAppendice() != null ? lAttProcBean.getIdModAppendice() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModAppendice(lAttProcBean.getNomeModAppendice() != null ? lAttProcBean.getNomeModAppendice() : "");
		lNuovaPropostaAtto2CompletaBean.setIdModello(lAttProcBean.getIdModAssDocTask() != null ? lAttProcBean.getIdModAssDocTask() : "");
		lNuovaPropostaAtto2CompletaBean.setNomeModello(lAttProcBean.getNomeModAssDocTask() != null ? lAttProcBean.getNomeModAssDocTask() : "");
		lNuovaPropostaAtto2CompletaBean.setDisplayFilenameModello(lAttProcBean.getDisplayFilenameModAssDocTask() != null ? lAttProcBean.getDisplayFilenameModAssDocTask() : "");
		lNuovaPropostaAtto2CompletaBean.setIdUoDirAdottanteSIB(lAttProcBean.getIdUoDirAdottanteSIB() != null ? lAttProcBean.getIdUoDirAdottanteSIB() : "");
		lNuovaPropostaAtto2CompletaBean.setCodUoDirAdottanteSIB(lAttProcBean.getCodUoDirAdottanteSIB() != null ? lAttProcBean.getCodUoDirAdottanteSIB() : "");
		lNuovaPropostaAtto2CompletaBean.setDesUoDirAdottanteSIB(lAttProcBean.getDesUoDirAdottanteSIB() != null ? lAttProcBean.getDesUoDirAdottanteSIB() : "");	
		lNuovaPropostaAtto2CompletaBean.setImpostazioniUnioneFile(lAttProcBean.getImpostazioniUnioneFile());
	}

	public OperazioneMassivaAttiBean togliDaLibroFirma (OperazioneMassivaAttiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(AttiBean atto : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(atto.getUnitaDocumentariaId());
			docInfo.setIdProcess(atto.getIdProcedimento());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean togliDaLibroFirmaDaProcedimentiInIter (OperazioneMassivaProcedimentiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}
	
	public OperazioneMassivaProcedimentiBean togliDaLibroFirmaDaProcedimentiPersonali (OperazioneMassivaProcedimentiBean bean) throws Exception {

		List<DocInfoLibroFirma> inputList = new ArrayList<>();
		for(ProcedimentiInIterBean procedimentiInIterBean : bean.getListaRecord()) {
			DocInfoLibroFirma docInfo = new DocInfoLibroFirma();
			docInfo.setIdUd(procedimentiInIterBean.getIdUdRispostaCedAutotutele());
			docInfo.setIdProcess(procedimentiInIterBean.getIdProcedimento());
			docInfo.setIdDocType(procedimentiInIterBean.getIdDocTypeRispostaCedAutotutele());
			inputList.add(docInfo);
		}
		HashMap<String, String> errorMessages = togliDaLibroFirmaCommon(inputList);
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	private HashMap<String, String> togliDaLibroFirmaCommon(List<DocInfoLibroFirma> inputList) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String inputListStr = new XmlUtilitySerializer().bindXmlList(inputList);
		
		DmpkCore2InviarimuovidoclibrofirmaBean input = new DmpkCore2InviarimuovidoclibrofirmaBean();
		input.setAzionein("rimuovi");
		input.setDocinfoin(inputListStr);
		
		DmpkCore2Inviarimuovidoclibrofirma store = new DmpkCore2Inviarimuovidoclibrofirma();
		StoreResultBean<DmpkCore2InviarimuovidoclibrofirmaBean> output = store.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);
		}

		HashMap<String, String> errorMessages = null;		

		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
		}
		return errorMessages;
	}

	public OperazioneMassivaAttiBean segnaDaRicontrollare (OperazioneMassivaAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo((HttpSession) this.getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		HashMap<String, String> errorMessages = new HashMap<String, String>();
		
		for (int i = 0; i < bean.getListaRecord().size(); ++i) {
			AttiBean atto = bean.getListaRecord().get(i);
			
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(atto.getUnitaDocumentariaId()));
				
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setCodStatoDett("DACONTR");				
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if (output.getDefaultMessage() != null) {	
					String dettagliAtto = "";
					if(StringUtils.isNotBlank(atto.getNumeroAtto())) {
						dettagliAtto = atto.getNumeroAtto() + " - ";
					}
					errorMessages.put(dettagliAtto + "Proposta " + atto.getNumeroProposta(), output.getDefaultMessage());
				}
		}
		
		if(errorMessages != null && !errorMessages.isEmpty()) {
			bean.setErrorMessages(errorMessages);
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	@Override
	public AttiBean get(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean add(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean remove(AttiBean bean) throws Exception {
		return null;
	}

	@Override
	public AttiBean update(AttiBean bean, AttiBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<AttiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AttiBean bean) throws Exception {
		return null;
	}
	
	private CallExecAttDatasource getCallExecAttDatasource() {
		CallExecAttDatasource lCallExecAttDatasource = new CallExecAttDatasource();
		lCallExecAttDatasource.setSession(getSession());
		lCallExecAttDatasource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lCallExecAttDatasource.setMessages(getMessages()); 		
		return lCallExecAttDatasource;
	}	

	private NuovaPropostaAtto2CompletaDataSource getNuovaPropostaAtto2CompletaDataSource() {
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.getExtraparams().put("isFromFirmaOVistoMassivi", "true");
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		lNuovaPropostaAtto2CompletaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lNuovaPropostaAtto2CompletaDataSource.setMessages(getMessages()); 		
		return lNuovaPropostaAtto2CompletaDataSource;
	}
	
}
