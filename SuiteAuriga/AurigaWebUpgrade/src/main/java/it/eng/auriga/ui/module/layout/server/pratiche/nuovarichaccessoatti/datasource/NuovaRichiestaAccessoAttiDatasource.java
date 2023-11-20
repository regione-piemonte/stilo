/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.ArchivioDatasource;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovarichaccessoatti.datasource.bean.NuovaRichiestaAccessoAttiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttiRichiestiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.client.RecuperoDocumenti;
import it.eng.document.function.bean.AttiRichiestiXMLBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id="NuovaRichiestaAccessoAttiDatasource")
public class NuovaRichiestaAccessoAttiDatasource extends AbstractDataSource<NuovaRichiestaAccessoAttiBean, NuovaRichiestaAccessoAttiBean> {
	
	private static Logger logger = Logger.getLogger(NuovaRichiestaAccessoAttiDatasource.class);

	public ProtocolloDataSource getProtocolloDataSource(final NuovaRichiestaAccessoAttiBean pRichiestaAccessoAttiBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				salvaAttributiCustomRichiestaAccessoAtti(pRichiestaAccessoAttiBean, pSezioneCacheAttributiDinamici);
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaRichiestaAccessoAttiDatasource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	public ArchivioDatasource getArchivioDatasource() {	
		
		ArchivioDatasource lArchivioDatasource = new ArchivioDatasource();		
		lArchivioDatasource.setSession(getSession());
		lArchivioDatasource.setExtraparams(getExtraparams());	
		// devo settare in ArchivioDatasource i messages di NuovaRichiestaAccessoAttiDatasource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lArchivioDatasource.setMessages(getMessages()); 
		
		return lArchivioDatasource;
	}
	
	@Override
	public Map<String, String> getExtraparams() {		
		
		Map<String, String> extraparams = super.getExtraparams();
		extraparams.put("isRichiestaAccessoAtti", "true");
		return extraparams;		
	}
	
	@Override
	public NuovaRichiestaAccessoAttiBean get(NuovaRichiestaAccessoAttiBean bean) throws Exception {	
		
 		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		String idProcess = getExtraparams().get("idProcess");
		String taskName = getExtraparams().get("taskName");
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
		lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
		lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
		lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
		
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocollazioneBean lProtocollazioneBean = new ProtocolloUtility(getSession()).getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());			
		
		bean.setTipoDocumento(lProtocollazioneBean.getTipoDocumento());
		bean.setNomeTipoDocumento(lProtocollazioneBean.getNomeTipoDocumento());
		bean.setRowidDoc(lProtocollazioneBean.getRowidDoc());
		bean.setIdDocPrimario(lProtocollazioneBean.getIdDocPrimario() != null ?  String.valueOf(lProtocollazioneBean.getIdDocPrimario().longValue()) : null);
		bean.setNomeFilePrimario(lProtocollazioneBean.getNomeFilePrimario());
		bean.setUriFilePrimario(lProtocollazioneBean.getUriFilePrimario());
		bean.setRemoteUriFilePrimario(lProtocollazioneBean.getRemoteUriFilePrimario());
		bean.setInfoFilePrimario(lProtocollazioneBean.getInfoFile());
		bean.setIsChangedFilePrimario(lProtocollazioneBean.getIsDocPrimarioChanged());
		bean.setFlgDatiSensibili(lProtocollazioneBean.getFlgDatiSensibili());
		
		if(lProtocollazioneBean.getFilePrimarioOmissis() != null) {
			bean.setIdDocPrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getIdDoc());
			bean.setNomeFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getNomeFile());
			bean.setUriFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getUriFile());
			bean.setRemoteUriFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getRemoteUri());
			bean.setInfoFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getInfoFile());	
			bean.setIsChangedFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis().getIsChanged());
		}
		
		bean.setListaMittenti(lProtocollazioneBean.getListaMittenti());			
		bean.setListaEsibenti(lProtocollazioneBean.getListaEsibenti());
		bean.setListaAltreVie(lProtocollazioneBean.getListaAltreVie());
		bean.setSiglaProtocolloPregresso(lProtocollazioneBean.getSiglaProtocolloPregresso());
		bean.setNroProtocolloPregresso(lProtocollazioneBean.getNroProtocolloPregresso());
		bean.setAnnoProtocolloPregresso(lProtocollazioneBean.getAnnoProtocolloPregresso());
		bean.setDataProtocolloPregresso(lProtocollazioneBean.getDataProtocolloPregresso());
		bean.setFlgTipoDocConVie(lProtocollazioneBean.getFlgTipoDocConVie());
		
		bean.setFlgRichiestaAccessoAtti(lProtocollazioneBean.getFlgRichiestaAccessoAtti());
		bean.setTipoRichiedente(lProtocollazioneBean.getTipoRichiedente());
		bean.setCodStatoRichAccessoAtti(lProtocollazioneBean.getCodStatoRichAccessoAtti());
		bean.setDesStatoRichAccessoAtti(lProtocollazioneBean.getDesStatoRichAccessoAtti());
		bean.setSiglaPraticaSuSistUfficioRichiedente(lProtocollazioneBean.getSiglaPraticaSuSistUfficioRichiedente());
		bean.setNumeroPraticaSuSistUfficioRichiedente(lProtocollazioneBean.getNumeroPraticaSuSistUfficioRichiedente());
		bean.setAnnoPraticaSuSistUfficioRichiedente(lProtocollazioneBean.getAnnoPraticaSuSistUfficioRichiedente());
		bean.setListaRichiedentiInterni(lProtocollazioneBean.getListaRichiedentiInterni());
		bean.setFlgRichAttiFabbricaVisuraSue(lProtocollazioneBean.getFlgRichAttiFabbricaVisuraSue());
		bean.setFlgRichModificheVisuraSue(lProtocollazioneBean.getFlgRichModificheVisuraSue());
		bean.setListaAttiRichiesti(lProtocollazioneBean.getListaAttiRichiesti());
		bean.setFlgAltriAttiDaRicercareVisuraSue(lProtocollazioneBean.getFlgAltriAttiDaRicercareVisuraSue());
		bean.setListaRichiedentiDelegati(lProtocollazioneBean.getListaRichiedentiDelegati());
		bean.setMotivoRichiestaAccessoAtti(lProtocollazioneBean.getMotivoRichiestaAccessoAtti());
		bean.setDettagliRichiestaAccessoAtti(lProtocollazioneBean.getDettagliRichiestaAccessoAtti());	
		bean.setIdIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getIdIncaricatoPrelievoPerUffRichiedente());
		bean.setUsernameIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getUsernameIncaricatoPrelievoPerUffRichiedente());
		bean.setCodRapidoIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getCodRapidoIncaricatoPrelievoPerUffRichiedente());
		bean.setCognomeIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getCognomeIncaricatoPrelievoPerUffRichiedente());
		bean.setNomeIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getNomeIncaricatoPrelievoPerUffRichiedente());
		bean.setTelefonoIncaricatoPrelievoPerUffRichiedente(lProtocollazioneBean.getTelefonoIncaricatoPrelievoPerUffRichiedente());	
		bean.setCognomeIncaricatoPrelievoPerRichEsterno(lProtocollazioneBean.getCognomeIncaricatoPrelievoPerRichEsterno());
		bean.setNomeIncaricatoPrelievoPerRichEsterno(lProtocollazioneBean.getNomeIncaricatoPrelievoPerRichEsterno());
		bean.setCodiceFiscaleIncaricatoPrelievoPerRichEsterno(lProtocollazioneBean.getCodiceFiscaleIncaricatoPrelievoPerRichEsterno());
		bean.setEmailIncaricatoPrelievoPerRichEsterno(lProtocollazioneBean.getEmailIncaricatoPrelievoPerRichEsterno());
		bean.setTelefonoIncaricatoPrelievoPerRichEsterno(lProtocollazioneBean.getTelefonoIncaricatoPrelievoPerRichEsterno());
		bean.setDataAppuntamento(lProtocollazioneBean.getDataAppuntamento());
		bean.setOrarioAppuntamento(lProtocollazioneBean.getOrarioAppuntamento());
		bean.setProvenienzaAppuntamento(lProtocollazioneBean.getProvenienzaAppuntamento());
		bean.setDataPrelievo(lProtocollazioneBean.getDataPrelievo());
		bean.setDataRestituzionePrelievo(lProtocollazioneBean.getDataRestituzionePrelievo());
		bean.setRestituzionePrelievoAttestataDa(lProtocollazioneBean.getRestituzionePrelievoAttestataDa());
		bean.setIdNodoDefaultRicercaAtti(lProtocollazioneBean.getIdNodoDefaultRicercaAtti());
		bean.setAbilVisualizzaDettStdProt(lProtocollazioneBean.getAbilVisualizzaDettStdProt());	
		bean.setAbilInvioInApprovazione(lProtocollazioneBean.getAbilInvioInApprovazione());
		bean.setAbilApprovazione(lProtocollazioneBean.getAbilApprovazione());
		bean.setAbilInvioEsitoVerificaArchivio(lProtocollazioneBean.getAbilInvioEsitoVerificaArchivio());
		bean.setAbilAbilitaAppuntamentoDaAgenda(lProtocollazioneBean.getAbilAbilitaAppuntamentoDaAgenda());
		bean.setAbilSetAppuntamento(lProtocollazioneBean.getAbilSetAppuntamento());
		bean.setAbilAnnullamentoAppuntamento(lProtocollazioneBean.getAbilAnnullamentoAppuntamento());
		bean.setAbilRegistraPrelievo(lProtocollazioneBean.getAbilRegistraPrelievo());
		bean.setAbilRegistraRestituzione(lProtocollazioneBean.getAbilRegistraRestituzione());
		bean.setAbilAnnullamento(lProtocollazioneBean.getAbilAnnullamento());
		bean.setAbilStampaFoglioPrelievo(lProtocollazioneBean.getAbilStampaFoglioPrelievo());
		bean.setAbilRichAccessoAttiChiusura(lProtocollazioneBean.getAbilRichAccessoAttiChiusura());
		bean.setAbilRichAccessoAttiRiapertura(lProtocollazioneBean.getAbilRichAccessoAttiRiapertura());
		bean.setAbilRichAccessoAttiRipristino(lProtocollazioneBean.getAbilRichAccessoAttiRipristino());	
		
//		List<SelezionaScrivaniaBean> listaRespIstruttoria = new ArrayList<SelezionaScrivaniaBean>();
//		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespIstruttoria())) {
//			SelezionaScrivaniaBean lSelezionaScrivaniaBean = new SelezionaScrivaniaBean();
//			lSelezionaScrivaniaBean.setIdScrivania(lDocumentoXmlOutBean.getIdScrivaniaRespIstruttoria());
//			lSelezionaScrivaniaBean.setCodUoScrivania(lDocumentoXmlOutBean.getCodUOScrivaniaRespIstruttoria());
//			lSelezionaScrivaniaBean.setDescrizione(lDocumentoXmlOutBean.getDesScrivaniaRespIstruttoria());
//			lSelezionaScrivaniaBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesScrivaniaRespIstruttoria());				
//			lSelezionaScrivaniaBean.setOrganigramma("SV" + lDocumentoXmlOutBean.getIdScrivaniaRespIstruttoria());
//			listaRespIstruttoria.add(lSelezionaScrivaniaBean);
//		} else {
//			listaRespIstruttoria.add(new SelezionaScrivaniaBean());
//		}
//		bean.setListaRespIstruttoria(listaRespIstruttoria);	
		
		// Ricercatore incaricato
		bean.setIdUtenteRicercatore(lDocumentoXmlOutBean.getIdRicercatoreVisuraSUE());
		bean.setCognomeNomeRicercatore(lDocumentoXmlOutBean.getDesRicercatoreVisuraSUE());	
		
		bean.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());		
		
		bean.setListaDocumentiIstruttoria(lProtocollazioneBean.getListaDocProcFolder());	
				
		bean.setMezzoTrasmissione(lProtocollazioneBean.getMezzoTrasmissione());
		
		return bean;
	}
	
	@Override
	public NuovaRichiestaAccessoAttiBean update(NuovaRichiestaAccessoAttiBean bean, NuovaRichiestaAccessoAttiBean oldvalue) throws Exception {

		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		try {
			lProtocollazioneBeanInput = getProtocolloDataSource(bean).get(lProtocollazioneBeanInput);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		

		// Copio i dati a maschera nel bean di salvataggio
		populatProtocollazioneBeanFromNuovaRichiestaAccessoAttiBean(lProtocollazioneBeanInput, bean);
		
		try {
			ProtocollazioneBean  lProtocollazioneBeanOutput = getProtocolloDataSource(bean).update(lProtocollazioneBeanInput, null);
//			if (lProtocollazioneBeanOutput.getFileInErrors() != null && lProtocollazioneBeanOutput.getFileInErrors().size() > 0) {
//				for (String error : lProtocollazioneBeanOutput.getFileInErrors().values()) {
//					logger.error(error);					
//				}
//				throw new StoreException("Si è verificato un errore durante il salvataggio: alcuni dei file sono andati in errore");
//			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		// salvo la lista dei documenti di istruttoria sul folder
		try {
			ArchivioDatasource lArchivioDatasource = getArchivioDatasource();
			ArchivioBean lArchivioBean = new ArchivioBean();
			lArchivioBean.setIdUdFolder(bean.getIdFolder());
			lArchivioBean.setFlgUdFolder("F");
			lArchivioBean = lArchivioDatasource.get(lArchivioBean);
			List<AllegatoProtocolloBean> listaDocumentiIstruttoria = new ArrayList<AllegatoProtocolloBean>();
			if(bean.getListaDocumentiIstruttoria() != null) {
				for(AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaDocumentiIstruttoria()) {
					HashSet<String> idUdSet = new HashSet<String>(); 
					boolean isNewOrChanged = lAllegatoProtocolloBean.getIdDocAllegato() == null || (lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged());
					// passo solo i documenti istruttoria nuovi appena inseriti a maschera o che sono stati modificati
					// in caso di più documenti con lo stesso idUd mi recupero solo il primo, che è il primario, e i restanti allegati li ignoro
					// i nuovi file importati non vengono gestiti qua, ma devono essere agganciati al processo della richesta accesso atti
					boolean isAllegatoImportato = lAllegatoProtocolloBean.getFileImportato() != null ? lAllegatoProtocolloBean.getFileImportato() : false;
					if (!isAllegatoImportato && isNewOrChanged && !idUdSet.contains(lAllegatoProtocolloBean.getIdUdAppartenenza())) {
					// if (isNewOrChanged && !idUdSet.contains(lAllegatoProtocolloBean.getIdUdAppartenenza())) {
						idUdSet.add(lAllegatoProtocolloBean.getIdUdAppartenenza());
						if(StringUtils.isNotBlank(bean.getIdTipoDocToProt())) {
							String idTipoDocAllegato = lAllegatoProtocolloBean.getListaTipiFileAllegato();
							if(idTipoDocAllegato != null && idTipoDocAllegato.equals(bean.getIdTipoDocToProt())) {
								lAllegatoProtocolloBean.setFlgDaProtocollare(true);
							}
						} else if(StringUtils.isNotBlank(bean.getIdDocToProt())) {
							String idDocAllegato = lAllegatoProtocolloBean.getIdDocAllegato() != null ? String.valueOf(lAllegatoProtocolloBean.getIdDocAllegato().longValue()) : null;
							if(idDocAllegato != null && idDocAllegato.equals(bean.getIdDocToProt())) {
								lAllegatoProtocolloBean.setFlgDaProtocollare(true);
							}
						} 		
						listaDocumentiIstruttoria.add(lAllegatoProtocolloBean);
					}
				}
			}	
			lArchivioBean.setListaDocumentiIstruttoria(listaDocumentiIstruttoria);
			lArchivioDatasource.update(lArchivioBean, null);
			
			// Aggancio i documenti importati al fascicolo
			if(bean.getListaDocumentiIstruttoria() != null) {
				for(AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaDocumentiIstruttoria()) {
					boolean isAllegatoImportato = lAllegatoProtocolloBean.getFileImportato() != null ? lAllegatoProtocolloBean.getFileImportato() : false;
					if (isAllegatoImportato) {
						DmpkProcessesIuassociazioneudvsprocBean dmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
						dmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(new BigDecimal(bean.getIdProcess()));
						dmpkProcessesIuassociazioneudvsprocBean.setIdudin(new BigDecimal(lAllegatoProtocolloBean.getIdUdAppartenenza()));
						dmpkProcessesIuassociazioneudvsprocBean.setNroordinevsprocin(null);
	
						DmpkProcessesIuassociazioneudvsproc dmpkProcessesIuassociazioneudvsproc = new DmpkProcessesIuassociazioneudvsproc();
						StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> result = dmpkProcessesIuassociazioneudvsproc.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), dmpkProcessesIuassociazioneudvsprocBean);
	
						if (result.isInError()) {
							throw new StoreException(result);
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}	
		
		return bean;
	}
	
	private void populatProtocollazioneBeanFromNuovaRichiestaAccessoAttiBean(ProtocollazioneBean pProtocollazioneBean, NuovaRichiestaAccessoAttiBean pNuovaRichiestaAccessoAttiBean) {
		
		if(pProtocollazioneBean != null && pNuovaRichiestaAccessoAttiBean != null) {
			
			pProtocollazioneBean.setTipoDocumento(pNuovaRichiestaAccessoAttiBean.getTipoDocumento());
			pProtocollazioneBean.setNomeTipoDocumento(pNuovaRichiestaAccessoAttiBean.getNomeTipoDocumento());
			pProtocollazioneBean.setRowidDoc(pNuovaRichiestaAccessoAttiBean.getRowidDoc());
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pNuovaRichiestaAccessoAttiBean.getIdDocPrimario()) ? new BigDecimal(pNuovaRichiestaAccessoAttiBean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setNomeFilePrimario(pNuovaRichiestaAccessoAttiBean.getNomeFilePrimario());
			pProtocollazioneBean.setUriFilePrimario(pNuovaRichiestaAccessoAttiBean.getUriFilePrimario());
			pProtocollazioneBean.setRemoteUriFilePrimario(pNuovaRichiestaAccessoAttiBean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setInfoFile(pNuovaRichiestaAccessoAttiBean.getInfoFilePrimario());
			pProtocollazioneBean.setIsDocPrimarioChanged(pNuovaRichiestaAccessoAttiBean.getIsChangedFilePrimario());
			pProtocollazioneBean.setFlgDatiSensibili(pNuovaRichiestaAccessoAttiBean.getFlgDatiSensibili());
			
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pNuovaRichiestaAccessoAttiBean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pNuovaRichiestaAccessoAttiBean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pNuovaRichiestaAccessoAttiBean.getUriFilePrimarioOmissis());
			lFilePrimarioOmissis.setRemoteUri(pNuovaRichiestaAccessoAttiBean.getRemoteUriFilePrimarioOmissis());		
			lFilePrimarioOmissis.setInfoFile(pNuovaRichiestaAccessoAttiBean.getInfoFilePrimarioOmissis());
			lFilePrimarioOmissis.setIsChanged(pNuovaRichiestaAccessoAttiBean.getIsChangedFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
			
			pProtocollazioneBean.setListaMittenti(pNuovaRichiestaAccessoAttiBean.getListaMittenti());			
			pProtocollazioneBean.setListaEsibenti(pNuovaRichiestaAccessoAttiBean.getListaEsibenti());
			pProtocollazioneBean.setListaAltreVie(pNuovaRichiestaAccessoAttiBean.getListaAltreVie());
			pProtocollazioneBean.setSiglaProtocolloPregresso(pNuovaRichiestaAccessoAttiBean.getSiglaProtocolloPregresso());
			pProtocollazioneBean.setNroProtocolloPregresso(pNuovaRichiestaAccessoAttiBean.getNroProtocolloPregresso());
			pProtocollazioneBean.setAnnoProtocolloPregresso(pNuovaRichiestaAccessoAttiBean.getAnnoProtocolloPregresso());
			pProtocollazioneBean.setDataProtocolloPregresso(pNuovaRichiestaAccessoAttiBean.getDataProtocolloPregresso());
			pProtocollazioneBean.setFlgTipoDocConVie(pNuovaRichiestaAccessoAttiBean.getFlgTipoDocConVie());		
			
			pProtocollazioneBean.setRemoteUriFilePrimario(pNuovaRichiestaAccessoAttiBean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setFlgRichiestaAccessoAtti(pNuovaRichiestaAccessoAttiBean.getFlgRichiestaAccessoAtti());
			pProtocollazioneBean.setTipoRichiedente(pNuovaRichiestaAccessoAttiBean.getTipoRichiedente());
			pProtocollazioneBean.setCodStatoRichAccessoAtti(pNuovaRichiestaAccessoAttiBean.getCodStatoRichAccessoAtti());
			pProtocollazioneBean.setDesStatoRichAccessoAtti(pNuovaRichiestaAccessoAttiBean.getDesStatoRichAccessoAtti());
			pProtocollazioneBean.setSiglaPraticaSuSistUfficioRichiedente(pNuovaRichiestaAccessoAttiBean.getSiglaPraticaSuSistUfficioRichiedente());
			pProtocollazioneBean.setNumeroPraticaSuSistUfficioRichiedente(pNuovaRichiestaAccessoAttiBean.getNumeroPraticaSuSistUfficioRichiedente());
			pProtocollazioneBean.setAnnoPraticaSuSistUfficioRichiedente(pNuovaRichiestaAccessoAttiBean.getAnnoPraticaSuSistUfficioRichiedente());
			pProtocollazioneBean.setListaRichiedentiInterni(pNuovaRichiestaAccessoAttiBean.getListaRichiedentiInterni());
			pProtocollazioneBean.setFlgRichAttiFabbricaVisuraSue(pNuovaRichiestaAccessoAttiBean.getFlgRichAttiFabbricaVisuraSue());
			pProtocollazioneBean.setFlgRichModificheVisuraSue(pNuovaRichiestaAccessoAttiBean.getFlgRichModificheVisuraSue());
			pProtocollazioneBean.setListaAttiRichiesti(pNuovaRichiestaAccessoAttiBean.getListaAttiRichiesti());
			pProtocollazioneBean.setFlgAltriAttiDaRicercareVisuraSue(pNuovaRichiestaAccessoAttiBean.getFlgAltriAttiDaRicercareVisuraSue());
			pProtocollazioneBean.setListaRichiedentiDelegati(pNuovaRichiestaAccessoAttiBean.getListaRichiedentiDelegati());
			pProtocollazioneBean.setMotivoRichiestaAccessoAtti(pNuovaRichiestaAccessoAttiBean.getMotivoRichiestaAccessoAtti());
			pProtocollazioneBean.setDettagliRichiestaAccessoAtti(pNuovaRichiestaAccessoAttiBean.getDettagliRichiestaAccessoAtti());	
			pProtocollazioneBean.setIdIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getIdIncaricatoPrelievoPerUffRichiedente());
			pProtocollazioneBean.setUsernameIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getUsernameIncaricatoPrelievoPerUffRichiedente());
			pProtocollazioneBean.setCodRapidoIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getCodRapidoIncaricatoPrelievoPerUffRichiedente());
			pProtocollazioneBean.setCognomeIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getCognomeIncaricatoPrelievoPerUffRichiedente());
			pProtocollazioneBean.setNomeIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getNomeIncaricatoPrelievoPerUffRichiedente());
			pProtocollazioneBean.setTelefonoIncaricatoPrelievoPerUffRichiedente(pNuovaRichiestaAccessoAttiBean.getTelefonoIncaricatoPrelievoPerUffRichiedente());	
			pProtocollazioneBean.setCognomeIncaricatoPrelievoPerRichEsterno(pNuovaRichiestaAccessoAttiBean.getCognomeIncaricatoPrelievoPerRichEsterno());
			pProtocollazioneBean.setNomeIncaricatoPrelievoPerRichEsterno(pNuovaRichiestaAccessoAttiBean.getNomeIncaricatoPrelievoPerRichEsterno());
			pProtocollazioneBean.setCodiceFiscaleIncaricatoPrelievoPerRichEsterno(pNuovaRichiestaAccessoAttiBean.getCodiceFiscaleIncaricatoPrelievoPerRichEsterno());
			pProtocollazioneBean.setEmailIncaricatoPrelievoPerRichEsterno(pNuovaRichiestaAccessoAttiBean.getEmailIncaricatoPrelievoPerRichEsterno());
			pProtocollazioneBean.setTelefonoIncaricatoPrelievoPerRichEsterno(pNuovaRichiestaAccessoAttiBean.getTelefonoIncaricatoPrelievoPerRichEsterno());
			pProtocollazioneBean.setDataAppuntamento(pNuovaRichiestaAccessoAttiBean.getDataAppuntamento());
			pProtocollazioneBean.setOrarioAppuntamento(pNuovaRichiestaAccessoAttiBean.getOrarioAppuntamento());
			pProtocollazioneBean.setProvenienzaAppuntamento(pNuovaRichiestaAccessoAttiBean.getProvenienzaAppuntamento());
			pProtocollazioneBean.setDataPrelievo(pNuovaRichiestaAccessoAttiBean.getDataPrelievo());
			pProtocollazioneBean.setDataRestituzionePrelievo(pNuovaRichiestaAccessoAttiBean.getDataRestituzionePrelievo());
			pProtocollazioneBean.setRestituzionePrelievoAttestataDa(pNuovaRichiestaAccessoAttiBean.getRestituzionePrelievoAttestataDa());
			pProtocollazioneBean.setIdNodoDefaultRicercaAtti(pNuovaRichiestaAccessoAttiBean.getIdNodoDefaultRicercaAtti());
			pProtocollazioneBean.setAbilVisualizzaDettStdProt(pNuovaRichiestaAccessoAttiBean.getAbilVisualizzaDettStdProt());	
			pProtocollazioneBean.setAbilInvioInApprovazione(pNuovaRichiestaAccessoAttiBean.getAbilInvioInApprovazione());
			pProtocollazioneBean.setAbilApprovazione(pNuovaRichiestaAccessoAttiBean.getAbilApprovazione());
			pProtocollazioneBean.setAbilInvioEsitoVerificaArchivio(pNuovaRichiestaAccessoAttiBean.getAbilInvioEsitoVerificaArchivio());
			pProtocollazioneBean.setAbilAbilitaAppuntamentoDaAgenda(pNuovaRichiestaAccessoAttiBean.getAbilAbilitaAppuntamentoDaAgenda());
			pProtocollazioneBean.setAbilSetAppuntamento(pNuovaRichiestaAccessoAttiBean.getAbilSetAppuntamento());
			pProtocollazioneBean.setAbilAnnullamentoAppuntamento(pNuovaRichiestaAccessoAttiBean.getAbilAnnullamentoAppuntamento());
			pProtocollazioneBean.setAbilRegistraPrelievo(pNuovaRichiestaAccessoAttiBean.getAbilRegistraPrelievo());
			pProtocollazioneBean.setAbilRegistraRestituzione(pNuovaRichiestaAccessoAttiBean.getAbilRegistraRestituzione());
			pProtocollazioneBean.setAbilAnnullamento(pNuovaRichiestaAccessoAttiBean.getAbilAnnullamento());
			pProtocollazioneBean.setAbilStampaFoglioPrelievo(pNuovaRichiestaAccessoAttiBean.getAbilStampaFoglioPrelievo());
			pProtocollazioneBean.setAbilRichAccessoAttiChiusura(pNuovaRichiestaAccessoAttiBean.getAbilRichAccessoAttiChiusura());
			pProtocollazioneBean.setAbilRichAccessoAttiRiapertura(pNuovaRichiestaAccessoAttiBean.getAbilRichAccessoAttiRiapertura());
			pProtocollazioneBean.setAbilRichAccessoAttiRipristino(pNuovaRichiestaAccessoAttiBean.getAbilRichAccessoAttiRipristino());
			
			// Ricercatore incaricato
			pProtocollazioneBean.setIdRicercatoreVisuraSUE(pNuovaRichiestaAccessoAttiBean.getIdUtenteRicercatore());
	
			pProtocollazioneBean.setListaDocProcFolder(pNuovaRichiestaAccessoAttiBean.getListaDocumentiIstruttoria());
			
			pProtocollazioneBean.setMezzoTrasmissione(pNuovaRichiestaAccessoAttiBean.getMezzoTrasmissione());
		}
	}
	
	protected void salvaAttributiCustomRichiestaAccessoAtti(NuovaRichiestaAccessoAttiBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {

//		String responsabileIstruttoria = "";
//		
//		if(bean.getListaRespIstruttoria() != null && bean.getListaRespIstruttoria().size() > 0) {
//			responsabileIstruttoria = bean.getListaRespIstruttoria().get(0).getIdScrivania();	
//		}	
//		
//		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_ISTRUTTORIA_Ud", responsabileIstruttoria);		
		
	}
	
	private int getPosVariabileSezioneCache(SezioneCache sezioneCache, String nomeVariabile) {	
		if(sezioneCache != null && sezioneCache.getVariabile() != null) {
			for(int i = 0; i < sezioneCache.getVariabile().size(); i++) {
				Variabile var = sezioneCache.getVariabile().get(i);
				if(var.getNome().equals(nomeVariabile)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void putVariabileSempliceSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
	}
	
	private void putVariabileListaSezioneCache(SezioneCache sezioneCache, String nomeVariabile, Lista lista) {
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setLista(lista);	
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileLista(nomeVariabile, lista));
		}
	}

	@Override
	public NuovaRichiestaAccessoAttiBean add(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		return null;
	}
	
	@Override
	public NuovaRichiestaAccessoAttiBean remove(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<NuovaRichiestaAccessoAttiBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		return null;
	}
	
	public FileDaFirmareBean compilaModello(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUd());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(bean.getNomeModello());
		
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		
		salvaAttributiCustomRichiestaAccessoAtti(bean, sezioneCacheAttributiDinamici);
		
		if(StringUtils.isNotBlank(getExtraparams().get("esitoTask"))) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "#EsitoTask", getExtraparams().get("esitoTask"));		
		}
		
		List<AttiRichiestiXMLBean> listaAttiRichiesti = recuperaListaAttiRichiestaAccessoAtti(bean);
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "#@AttiRichiesti", new XmlUtilitySerializer().createVariabileLista(listaAttiRichiesti));		
		
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		String templateValues = lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();	
		// TODEL Dati di prova
//		templateValues = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
//				"<SezioneCache>" + 
//				"<Variabile>" + 
//				"<Nome>SedePratica</Nome>" + 
//				"<ValoreSemplice>Milano</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DataPratica</Nome>" + 
//				"<ValoreSemplice>22 novembre 2018</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_CognomeNome</Nome>" + 
//				"<ValoreSemplice>Cacco Federico</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_Indirizzo</Nome>" + 
//				"<ValoreSemplice>Via Giuliano da Maiano</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_Cap</Nome>" + 
//				"<ValoreSemplice>30034</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_ComuneResidenza</Nome>" + 
//				"<ValoreSemplice>Mira</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_ProvinciaResidenza</Nome>" + 
//				"<ValoreSemplice>Venezia</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>DestinatarioComunicazioni_Email</Nome>" + 
//				"<ValoreSemplice>federico.cacco@mail.it</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_CognomeNome</Nome>" + 
//				"<ValoreSemplice>Cacco Francesco</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_Indirizzo</Nome>" + 
//				"<ValoreSemplice>Via Martiri della Libertà</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_Cap</Nome>" + 
//				"<ValoreSemplice>30070</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_ComuneResidenza</Nome>" + 
//				"<ValoreSemplice>Ponte di Brenta</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_ProvinciaResidenza</Nome>" + 
//				"<ValoreSemplice>Padova</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>RichiedentePratica_Email</Nome>" + 
//				"<ValoreSemplice>francesco.cacco@mail.it</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>IndirizzoVisura</Nome>" + 
//				"<ValoreSemplice>Via Monte Napoleone 34, Milano (MI) 39838, </ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_NroProt</Nome>" + 
//				"<ValoreSemplice>100001</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_DataProt</Nome>" + 
//				"<ValoreSemplice>22/11/2018</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_Nro</Nome>" + 
//				"<ValoreSemplice>500</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_Anno</Nome>" + 
//				"<ValoreSemplice>2018</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>@IntegrazioniVisuraDaRichiedere</Nome>" + 
//				"<Lista>" + 
//				"<Riga>" + 
//				"	<Colonna Nro=\"1\">il tipo di opere effettuate</Colonna>" + 
//				"</Riga>" + 
//				"<Riga>" + 
//				"	<Colonna Nro=\"1\">il piano dell'immobile</Colonna>" + 
//				"</Riga>" + 
//				"<Riga>" + 
//				"	<Colonna Nro=\"1\">il nome dell’impresa costruttrice delle opere o il nome del proprietario che le ha commissionate</Colonna>" + 
//				"</Riga>" + 
//				"<Riga>" + 
//				"	<Colonna Nro=\"1\">anno o decennio in cui sono state effettuate le opere</Colonna>" + 
//				"</Riga>" + 
//				"</Lista>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_ResponsabileProcedimento</Nome>" + 
//				"<ValoreSemplice>Rossi Marco</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"<Variabile>" + 
//				"<Nome>Pratica_ResponsabileIstruttoria</Nome>" + 
//				"<ValoreSemplice>Verdi Matteo</ValoreSemplice>" + 
//				"</Variabile>" + 
//				"</SezioneCache>";
		Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
				
		FileDaFirmareBean fillModelBean = null;
		
		if (bean.getEstensioneFileDaGenerare() != null && "doc".equalsIgnoreCase(bean.getEstensioneFileDaGenerare())){
			fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, false, bean.getFlgMostraDatiSensibili(), getSession());			
		} 
		else if (bean.getEstensioneFileDaGenerare() != null && "pdf".equalsIgnoreCase(bean.getEstensioneFileDaGenerare())){
			fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, bean.getFlgMostraDatiSensibili(), getSession()); 
		}
		
		fillModelBean.getInfoFile().setCorrectFileName(bean.getDisplayFilenameModello());
		fillModelBean.setNomeFile(bean.getNomeModello() + "." + bean.getEstensioneFileDaGenerare());	
		
		return fillModelBean;
		
	}
	
	// Dev'essere implementato come salvaListaAttiRichiestaAccessoAtti() in ProtocolloDataSource (utilizzata per il salvataggio dei dati a maschera)
	protected List<AttiRichiestiXMLBean> recuperaListaAttiRichiestaAccessoAtti(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		// Salvo gli atti richiesti. Se non ho atti devo comunque passare una lista vuota
		List<AttiRichiestiXMLBean> lAttiRichiestiXMLBeans = new ArrayList<AttiRichiestiXMLBean>();
		if (bean.getListaAttiRichiesti() != null){
			// Ho degli atti richiesti
			for (AttiRichiestiBean attoRichiestoBean : bean.getListaAttiRichiesti()) {
				
				boolean attoVuoto = true;
				
				AttiRichiestiXMLBean attoRichiestoXMLBean = new AttiRichiestiXMLBean();				
				attoRichiestoXMLBean.setTipoProtocollo(attoRichiestoBean.getTipoProtocollo());
				
				if(StringUtils.isBlank(attoRichiestoBean.getTipoProtocollo()) || "PG".equals(attoRichiestoBean.getTipoProtocollo())) {
					if (!hasProtocolloGeneraleEmpty(attoRichiestoBean)){
						attoVuoto = false;
						attoRichiestoXMLBean.setNumeroProtocollo(attoRichiestoBean.getNumProtocolloGenerale());
						attoRichiestoXMLBean.setAnnoProtocollo(attoRichiestoBean.getAnnoProtocolloGenerale());
					}
				} else if ("PS".equals(attoRichiestoBean.getTipoProtocollo())) {
					if (!hasProtocolloSettoreEmpty(attoRichiestoBean)){
						attoVuoto = false;	
						attoRichiestoXMLBean.setRegProtocolloDiSettore(attoRichiestoBean.getSiglaProtocolloSettore());
						attoRichiestoXMLBean.setNumeroProtocollo(attoRichiestoBean.getNumProtocolloSettore());
						attoRichiestoXMLBean.setSubProtocolloDiSettore(attoRichiestoBean.getSubProtocolloSettore());
						attoRichiestoXMLBean.setAnnoProtocollo(attoRichiestoBean.getAnnoProtocolloSettore());
					}
				} else if ("WF".equals(attoRichiestoBean.getTipoProtocollo())) {
					if (!hasPraticaWorkflow(attoRichiestoBean)){
						attoVuoto = false;	
						attoRichiestoXMLBean.setNumeroProtocollo(attoRichiestoBean.getNumPraticaWorkflow());
						attoRichiestoXMLBean.setAnnoProtocollo(attoRichiestoBean.getAnnoPraticaWorkflow());
					}
				}
				if (!attoVuoto){
					attoRichiestoXMLBean.setClassifica(attoRichiestoBean.getClassifica());
					attoRichiestoXMLBean.setStatoScansione(attoRichiestoBean.getStatoScansione());
					attoRichiestoXMLBean.setIdFolder(attoRichiestoBean.getIdFolder());
					attoRichiestoXMLBean.setStato(attoRichiestoBean.getStato());

					if ((StringUtils.isNotBlank(attoRichiestoBean.getStato())) && (attoRichiestoBean.getStato().toLowerCase().indexOf("prelevato") > -1)){
						// Salvo l'ufficio prelievo
						attoRichiestoXMLBean.setDenUffPrelievo(attoRichiestoBean.getDescrizioneUfficioPrelievo());
						attoRichiestoXMLBean.setCodUffPrelievo(attoRichiestoBean.getCodRapidoUfficioPrelievo());
						attoRichiestoXMLBean.setIdUoPrelievo(attoRichiestoBean.getIdUoUfficioPrelievo());
						// Salvo data prelievo
						attoRichiestoXMLBean.setDataPrelievo(attoRichiestoBean.getDataPrelievo());
						// Salvo responsabile prelievo
						attoRichiestoXMLBean.setIdUserRespPrelievo(attoRichiestoBean.getIdUserResponsabilePrelievo());
						attoRichiestoXMLBean.setCognomeRespPrelievo(attoRichiestoBean.getCognomeResponsabilePrelievo());
						attoRichiestoXMLBean.setNomeRespPrelievo(attoRichiestoBean.getNomeResponsabilePrelievo());
						attoRichiestoXMLBean.setUsernameRespPrelievo(attoRichiestoBean.getUsernameResponsabilePrelievo());
	//					attoRichiestoXMLBean.setCodRapidoUsernameRespPrelievo(attoRichiestoBean.getCodRapidoUfficioPrelievo());
					}
					
					// Salvo udc
					attoRichiestoXMLBean.setUdc(attoRichiestoBean.getUdc());
					
					attoRichiestoXMLBean.setNoteUffRichiedente(attoRichiestoBean.getNoteUffRichiedente());
					attoRichiestoXMLBean.setNoteCittadella(attoRichiestoBean.getNoteCittadella());
					
					attoRichiestoXMLBean.setCompetenzaDiUrbanistica(attoRichiestoBean.getCompetenzaDiUrbanistica());
					attoRichiestoXMLBean.setCartaceoReperibile(attoRichiestoBean.getCartaceoReperibile());
					attoRichiestoXMLBean.setInArchivio(attoRichiestoBean.getInArchivio());
					attoRichiestoXMLBean.setFlgRichiestaVisioneCartaceo(attoRichiestoBean.getFlgRichiestaVisioneCartaceo() != null && attoRichiestoBean.getFlgRichiestaVisioneCartaceo() ? "1" : "0");
					
					attoRichiestoXMLBean.setTipoFascicolo(attoRichiestoBean.getTipoFascicolo());
					attoRichiestoXMLBean.setAnnoProtEdiliziaPrivata(attoRichiestoBean.getAnnoProtEdiliziaPrivata());
					attoRichiestoXMLBean.setNumeroProtEdiliziaPrivata(attoRichiestoBean.getNumeroProtEdiliziaPrivata());
					attoRichiestoXMLBean.setAnnoWorkflow(attoRichiestoBean.getAnnoWorkflow());
					attoRichiestoXMLBean.setNumeroWorkflow(attoRichiestoBean.getNumeroWorkflow());
					attoRichiestoXMLBean.setNumeroDeposito(attoRichiestoBean.getNumeroDeposito());
					attoRichiestoXMLBean.setTipoComunicazione(attoRichiestoBean.getTipoComunicazione());
					attoRichiestoXMLBean.setNoteSportello(attoRichiestoBean.getNoteSportello());
					attoRichiestoXMLBean.setVisureCollegate(attoRichiestoBean.getVisureCollegate());
		
					lAttiRichiestiXMLBeans.add(attoRichiestoXMLBean);
				}
			}
		}
		return lAttiRichiestiXMLBeans;
	}
	
	protected boolean hasProtocolloGeneraleEmpty(AttiRichiestiBean attoRichiestoBean) {

		boolean hasNumProtocolloGeneraleEmpty = StringUtils.isBlank(attoRichiestoBean.getNumProtocolloGenerale());
		boolean hasAnnoProtocolloGeneraleEmpty = StringUtils.isBlank(attoRichiestoBean.getAnnoProtocolloGenerale());
		return hasNumProtocolloGeneraleEmpty && hasAnnoProtocolloGeneraleEmpty;
	}

	protected boolean hasProtocolloSettoreEmpty(AttiRichiestiBean attoRichiestoBean) {

		boolean hasSiglaProtocolloSettoreEmpty = StringUtils.isBlank(attoRichiestoBean.getSiglaProtocolloSettore());
		boolean hasNumProtocolloSettoreEmpty = StringUtils.isBlank(attoRichiestoBean.getNumProtocolloSettore());
		boolean hasAnnoProtocolloSettoreEmpty = StringUtils.isBlank(attoRichiestoBean.getAnnoProtocolloSettore());
		return hasSiglaProtocolloSettoreEmpty || hasNumProtocolloSettoreEmpty || hasAnnoProtocolloSettoreEmpty;
	}
	
	protected boolean hasPraticaWorkflow(AttiRichiestiBean attoRichiestoBean) {

		boolean hasNumPraticaWorkflowEmpty = StringUtils.isBlank(attoRichiestoBean.getNumPraticaWorkflow());
		boolean haAnnoPraticaWorkflowEmpty = StringUtils.isBlank(attoRichiestoBean.getAnnoPraticaWorkflow());
		return hasNumPraticaWorkflowEmpty || haAnnoPraticaWorkflowEmpty;
	}
	
	/*
	public NuovaRichiestaAccessoAttiBean eseguiEventoSUE(NuovaRichiestaAccessoAttiBean bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getTipoEventoSUE())) {
			SUEServices lSUEServices = getSUEServicesClient();
			SUEResponseBean lSUEResponseBean = null;
			SUERequestBean lSUERequestBean = new SUERequestBean();
//			lSUERequestBean.setNroProtocollo(bean.getNroProtocolloSUE());
//			lSUERequestBean.setDataProtocollo(convertDateToString(bean.getDataProtocolloSUE()));
			lSUERequestBean.setAmministrazione(bean.getAmministrazioneSUE());
			lSUERequestBean.setAoo(bean.getAooSUE());
			lSUERequestBean.setIdPratica(bean.getIdPraticaSUE());
			lSUERequestBean.setCodiceFiscale(bean.getCodFiscaleSUE());
			lSUERequestBean.setTimer((StringUtils.isNotBlank(bean.getGiorniSospensioneSUE()) ? Integer.parseInt(bean.getGiorniSospensioneSUE()) : 0));
			// FIXME Verificare che arrivi true da bean
			lSUERequestBean.setPubblico(bean.getFlgPubblicoSUE());
//			lSUERequestBean.setPubblico(true);
			List<DatiAttoreBean> destinatariSUE = new ArrayList<DatiAttoreBean>();
			if(bean.getDestinatariSUE() != null) {
				for(DestinatarioSUEBean lDestinatarioSUEBean : bean.getDestinatariSUE()) {
					DatiAttoreBean lDatiAttoreBean = new DatiAttoreBean();
					lDatiAttoreBean.setNome(lDestinatarioSUEBean.getNome());
					lDatiAttoreBean.setIndirizzo(lDestinatarioSUEBean.getIndirizzoMail());
					lDatiAttoreBean.setTipoIndirizzo(lDestinatarioSUEBean.getTipoIndirizzo());
					destinatariSUE.add(lDatiAttoreBean);
				}
			}
			lSUERequestBean.setDestinatari(destinatariSUE);
			// Devo ricalcolare le uri dei documenti di istruttoria con lo Storage
			Map<String, String> allegati = new LinkedHashMap<>();
			// Devo tenere traccia della chiave usata per salvare l'elemento individuato dall'idDoc, in quanto in caso di 
			// divieto di presecuzione attività diventa il file obbligatorio
			String chiaveFileObbligatorio = "";
			if(bean.getListaDocumentiIstruttoria() != null) {
				for(AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaDocumentiIstruttoria()) {
					if(StringUtils.isNotBlank(bean.getIdTipoDocAllegatoSUE())) {
						String idTipoDocAllegato = lAllegatoProtocolloBean.getListaTipiFileAllegato();
						if(idTipoDocAllegato != null && idTipoDocAllegato.equals(bean.getIdTipoDocAllegatoSUE())) {
							String uriDocAllegato = StorageImplementation.getStorage().extractFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString(); 
							allegati.put(uriDocAllegato, lAllegatoProtocolloBean.getNomeFileAllegato());
							chiaveFileObbligatorio = uriDocAllegato;
							lSUERequestBean.setNroProtocollo(lAllegatoProtocolloBean.getNroProtocollo());
							lSUERequestBean.setDataProtocollo(convertDateToString(lAllegatoProtocolloBean.getDataProtocollo()));
						}
					} else if(StringUtils.isNotBlank(bean.getIdDocAllegatoSUE())) {
						String idDocAllegato = lAllegatoProtocolloBean.getIdDocAllegato() != null ? String.valueOf(lAllegatoProtocolloBean.getIdDocAllegato().longValue()) : null;
						if(idDocAllegato != null && idDocAllegato.equals(bean.getIdDocAllegatoSUE())) {
							String uriDocAllegato = StorageImplementation.getStorage().extractFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString();
							allegati.put(uriDocAllegato, lAllegatoProtocolloBean.getNomeFileAllegato());
							chiaveFileObbligatorio = uriDocAllegato;
							lSUERequestBean.setNroProtocollo(lAllegatoProtocolloBean.getNroProtocollo());
							lSUERequestBean.setDataProtocollo(convertDateToString(lAllegatoProtocolloBean.getDataProtocollo()));
						}
					} 					
				}
			}	
			// Verifico se ho altri file da pubblicare
			if (bean.getFileDaPubblicareSUE() != null) {
				for (FileDaPubblicareSUEBean fileDaPubblicare : bean.getFileDaPubblicareSUE()) {
					String uriFileDaPubblicare = StorageImplementation.getStorage().extractFile(fileDaPubblicare.getUri()).toURI().toString();
					allegati.put(uriFileDaPubblicare, fileDaPubblicare.getDiplayFileName());
				}
			}
			// Gestisco gli allegati e il file obbligatorio in base al servizio chiamato
		    if (bean.getTipoEventoSUE().equalsIgnoreCase("richiestaIntegrazione")) {
		    	lSUERequestBean.setAllegati(allegati);
		    	lSUEResponseBean = lSUEServices.richiestaIntegrazione(lSUERequestBean);
		    } else if (bean.getTipoEventoSUE().equalsIgnoreCase("comunicazioneDaSuapARichiedente")) {
		    	lSUERequestBean.setAllegati(allegati);
		    	lSUEResponseBean = lSUEServices.comunicazioneDaSuapARichiedente(lSUERequestBean);
		    } else if (bean.getTipoEventoSUE().equalsIgnoreCase("annullamentoPratica")) {
		    	lSUERequestBean.setAllegati(allegati);
		    	lSUEResponseBean = lSUEServices.annullamentoPratica(lSUERequestBean);
		    } else if (bean.getTipoEventoSUE().equalsIgnoreCase("chiusuraPositivaConComunicazione")) {
		    	List<String> listaAutorita = new LinkedList<>();
		    	listaAutorita.add("Richiedente");
		    	lSUERequestBean.setAutorita(listaAutorita);
		    	lSUERequestBean.setAllegati(allegati);
		    	lSUEResponseBean = lSUEServices.chiusuraPositivaConComunicazione(lSUERequestBean);
		    } else if ((bean.getTipoEventoSUE().equalsIgnoreCase("chiusuraPositiva")) || (bean.getTipoEventoSUE().equalsIgnoreCase("chiusuraNegativa"))) {
		    	if (allegati.size() > 0) {
		    		// Verifico se c'è un file obbligatorio
		    		if (StringUtils.isNotBlank(chiaveFileObbligatorio) && allegati.containsKey(chiaveFileObbligatorio)) {
		    			// Estraggo dalla mappa il file obbligatorio per salvarlo nel campo specifico
		    			String nomeFileObbligatorio = allegati.get(chiaveFileObbligatorio);
		    			Map<String, String> mappaFileObbligatorio = new LinkedHashMap<>();
		    			mappaFileObbligatorio.put(chiaveFileObbligatorio, nomeFileObbligatorio);
		    			lSUERequestBean.setAllegatoObbligatorio(mappaFileObbligatorio);
					   	// Rimuovo dalla mappa degli allegati il file obbligatorio
		    			allegati.remove(chiaveFileObbligatorio);
		    		}
		    		// Salvo gli eventuali altri allegati, ho già escluso l'eventuale file obbligatorio
		    		lSUERequestBean.setAllegati(allegati);
		    	}
		    	if (bean.getTipoEventoSUE().equalsIgnoreCase("chiusuraPositiva")){
		    		lSUEResponseBean = lSUEServices.chiusuraPositiva(lSUERequestBean);
		    	} else if (bean.getTipoEventoSUE().equalsIgnoreCase("chiusuraNegativa")) {
		    		lSUEResponseBean = lSUEServices.chiusuraNegativa(lSUERequestBean);
		    	}
		    } 
//		    else if(bean.getTipoEventoSUE().equalsIgnoreCase("aggiornaPratica")) {
//			   lSUEResponseBean = lSUEServices.aggiornaPratica(lSUERequestBean);
//		    }   
		 	boolean done = lSUEResponseBean.getDone() != null && lSUEResponseBean.getDone().equalsIgnoreCase("true");
			if(!done) {
				String errorMessage = "Si è verificato un errore durante la chiamata del servizio " + bean.getTipoEventoSUE();
				if(StringUtils.isNotBlank(lSUEResponseBean.getErrorMessage())) {
					errorMessage = lSUEResponseBean.getErrorMessage();
				} 
				throw new StoreException(errorMessage);								
			}		
		}
		return bean;		
	}
	
	private static String convertDateToString(Date data) {
		SimpleDateFormat spf = new SimpleDateFormat("yyyMMdd");
		return data != null ? spf.format(data) : null;
	}
	
	private SUEServices getSUEServicesClient() throws SUEClientConfigException {
		SUEClientConfig lProxySUEClientConfig =  (SUEClientConfig) SpringAppContext.getContext().getBean("SUEClientConfig");
		SUEServices lSUEServices = new SUEServices();
		lSUEServices.initClient(lProxySUEClientConfig);
		return lSUEServices;
	}
	*/

}
