/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollAnnarchiviasedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetconvocazionesedutaBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetfileargsedutaxdownloadBean;
import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollSaveconvocazionesedutaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.CommissioniXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ConvocazioneInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.ConvocazioneSedutaBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.FileArgSedutaXDownloadXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.InfoTrasmissioneAttiXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.FileUnitoBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OdgInfoXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OperazioneMassivaArgomentiOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OpzioniFileArgSedutaBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.OpzioniTrasmissioneAttiBean;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.delibere.bean.PresenzeOdgXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.shared.bean.FileDocumentoBean;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkSeduteOrgCollAnnarchiviaseduta;
import it.eng.client.DmpkSeduteOrgCollGetconvocazioneseduta;
import it.eng.client.DmpkSeduteOrgCollGetfileargsedutaxdownload;
import it.eng.client.DmpkSeduteOrgCollSaveconvocazioneseduta;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.SalvataggioFile;
import it.eng.core.business.FileUtil;
import it.eng.document.function.AllegatoStoreBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ConvocazioneSedutaDataSource")
public class ConvocazioneSedutaDataSource extends AbstractServiceDataSource<ConvocazioneSedutaBean,ConvocazioneSedutaBean> {

	private static Logger mLogger = Logger.getLogger(ConvocazioneSedutaDataSource.class);

	@Override
	public ConvocazioneSedutaBean call(ConvocazioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollGetconvocazionesedutaBean input = new DmpkSeduteOrgCollGetconvocazionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOrganocollegialein(pInBean.getOrganoCollegiale());
		input.setIdsedutaio(pInBean.getIdSeduta() != null ? pInBean.getIdSeduta() : null);
		/**
		 * (obblig. OrganoCollegialeIn = commissione) Lista con gli ID delle commissioni per cui si convoca seduta
		 */
		input.setListacommissioniin(pInBean.getListaCommissioni());
		
		DmpkSeduteOrgCollGetconvocazioneseduta dmpkSeduteOrgCollGetconvocazioneseduta = new DmpkSeduteOrgCollGetconvocazioneseduta();
		StoreResultBean<DmpkSeduteOrgCollGetconvocazionesedutaBean> output = dmpkSeduteOrgCollGetconvocazioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		ConvocazioneSedutaBean lConvocazioneSedutaBean = new ConvocazioneSedutaBean();
		/**
		 * (obblig. OrganoCollegialeIn = commessione) Lista con gli ID delle commissioni per cui si convoca seduta
		 */
		lConvocazioneSedutaBean.setIdSeduta(output.getResultBean().getIdsedutaio());
		/**
		 * N.ro di seduta progressivo per organo e per anno (è vuoto se StatoSedutaOut = nuova)
		 */
		lConvocazioneSedutaBean.setNumero(output.getResultBean().getNrosedutaout() != null ? String.valueOf(output.getResultBean().getNrosedutaout()) : null);
		lConvocazioneSedutaBean.setDtPrimaConvocazione(output.getResultBean().getDataora1aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora1aconvocazioneout()) : null);
		lConvocazioneSedutaBean.setLuogoPrimaConvocazione(output.getResultBean().getLuogo1aconvocazioneout());	
		lConvocazioneSedutaBean.setDtSecondaConvocazione(output.getResultBean().getDataora2aconvocazioneout() != null ? 
				new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(output.getResultBean().getDataora2aconvocazioneout()) : null);
		lConvocazioneSedutaBean.setLuogoSecondaConvocazione(output.getResultBean().getLuogo2aconvocazioneout());	
		
		lConvocazioneSedutaBean.setStato(output.getResultBean().getStatosedutaout());
		String statoSedutaOut = output.getResultBean().getStatosedutaout();
		if(StringUtils.isNotBlank(statoSedutaOut)) {
			statoSedutaOut = statoSedutaOut.replaceAll("_", " ");
			lConvocazioneSedutaBean.setDesStato(statoSedutaOut);
		}
		lConvocazioneSedutaBean.setTipoSessione(output.getResultBean().getTiposessioneout());
		
		if(StringUtils.isNotBlank(output.getResultBean().getOdginfoout())) {
			OdgInfoXmlBean lOdgInfoBean = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getOdginfoout(), OdgInfoXmlBean.class);
			lConvocazioneSedutaBean.setDisattivatoRiordinoAutomatico(lOdgInfoBean.getDisattivatoRiordinoAutomatico() != null ?
					lOdgInfoBean.getDisattivatoRiordinoAutomatico() : null);
			lConvocazioneSedutaBean.setOdgInfo(lOdgInfoBean);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getConvocazioneinfoout())) {
			ConvocazioneInfoXmlBean conconvocazioneInfo = new XmlUtilityDeserializer().unbindXml(output.getResultBean().getConvocazioneinfoout(), ConvocazioneInfoXmlBean.class);
			lConvocazioneSedutaBean.setConvocazioneInfo(conconvocazioneInfo);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getArgomentiodgout())) {
			List<ArgomentiOdgXmlBean> listaArgomentiOdg = XmlListaUtility.recuperaLista(output.getResultBean().getArgomentiodgout(), ArgomentiOdgXmlBean.class);
			lConvocazioneSedutaBean.setListaArgomentiOdg(listaArgomentiOdg);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getPresenzeout())) {
			List<PresenzeOdgXmlBean> listaPresenzeOdg = XmlListaUtility.recuperaLista(output.getResultBean().getPresenzeout(), PresenzeOdgXmlBean.class);			
			if(listaPresenzeOdg != null) {
				String destinatari = "";				
				for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : listaPresenzeOdg) {
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getIdDelegato())) {
						lPresenzeOdgXmlBean.setDelegato(lPresenzeOdgXmlBean.getIdDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato());
					}
					if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getDestinatari())) {
						destinatari += lPresenzeOdgXmlBean.getDestinatari() + "; ";
					}
				}
				lConvocazioneSedutaBean.setDestinatari(destinatari);
			}
			lConvocazioneSedutaBean.setListaPresenzeOdg(listaPresenzeOdg);			
		}
		
		lConvocazioneSedutaBean.setListaCommissioni(pInBean.getListaCommissioni());
		
		// Estraggo la lista delle commissioni convocate
		if (lConvocazioneSedutaBean.getOdgInfo()!=null){
			if (lConvocazioneSedutaBean.getOdgInfo().getListaCommissioniConvocate()!=null && !lConvocazioneSedutaBean.getOdgInfo().getListaCommissioniConvocate().isEmpty()){
				String listaCommissioniConvocate = "";
				String listaCommissioniConvocateId = "";
				for(CommissioniXmlBean item : lConvocazioneSedutaBean.getOdgInfo().getListaCommissioniConvocate()) {
					if(item != null && item.getNomeCommissione() != null && !"".equals(item.getNomeCommissione())) {
						listaCommissioniConvocate   += item.getNomeCommissione() + "; \r\n";
						listaCommissioniConvocateId += item.getIdCommissione() + ";";
					}
				}
				// Lista dei nomi delle commissioni convocate
				lConvocazioneSedutaBean.setListaNomiCommissioniConvocate(listaCommissioniConvocate);
				
				// Lista dei ID delle commissioni convocate
				lConvocazioneSedutaBean.setListaIdCommissioniConvocate(listaCommissioniConvocateId);
			}
		}
		return lConvocazioneSedutaBean;
	}
	
	public ConvocazioneSedutaBean annullaSeduta(ConvocazioneSedutaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkSeduteOrgCollAnnarchiviasedutaBean input = new  DmpkSeduteOrgCollAnnarchiviasedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(bean.getIdSeduta());
		input.setAzionein("ANN");
		input.setMotiviin(bean.getMotivoAnnullamento());
		
		DmpkSeduteOrgCollAnnarchiviaseduta dmpkSeduteOrgCollAnnarchiviaseduta = new DmpkSeduteOrgCollAnnarchiviaseduta();
		StoreResultBean<DmpkSeduteOrgCollAnnarchiviasedutaBean> output = dmpkSeduteOrgCollAnnarchiviaseduta.execute(getLocale(), loginBean, input);
		
		if(output.isInError()) {
			throw new StoreException(output);						
		}
		
		return bean;
	}
	
	public ConvocazioneSedutaBean salvaConvocazioneSeduta(ConvocazioneSedutaBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		if(pInBean.getOdgInfo() != null && pInBean.getOdgInfo().getAzioneDaFare() != null) {
			mLogger.error("CONVOCAZIONE SEDUTA - Azione su OdG: " + pInBean.getOdgInfo().getAzioneDaFare());
		}
		
		if(pInBean.getConvocazioneInfo() != null && pInBean.getConvocazioneInfo().getAzioneDaFare() != null) {
			mLogger.error("CONVOCAZIONE SEDUTA - Azione su Convocazione: " + pInBean.getConvocazioneInfo().getAzioneDaFare());
		}
		
		// Devo persistere in archivio il documento generato da modello
		if (pInBean.getOdgInfo() != null && StringUtils.isNotBlank(pInBean.getOdgInfo().getUri())){
			OdgInfoXmlBean lOdgInfoXmlBean =  pInBean.getOdgInfo();
			// Recupero il file da persistere
			File lFile = StorageImplementation.getStorage().extractFile(lOdgInfoXmlBean.getUri());
			
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(lFile);
			Locale local = new Locale(loginBean.getLinguaApplicazione());
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut out = lSalvataggioFile.savefile(local, loginBean, lFileSavedIn);			
			
			if(out.getErrorInSaved() != null) {
				mLogger.error("CONVOCAZIONE SEDUTA - Errore durante il salvataggio del file OdG in repository: " + out.getErrorInSaved());
				throw new StoreException("Si è verificato un errore durante il salvataggio del file OdG in repository");
			}

			lOdgInfoXmlBean.setUri(out.getUri());
			pInBean.setOdgInfo(lOdgInfoXmlBean);
		}
		
		// Devo persistere in archivio il documento generato da modello
		if (pInBean.getConvocazioneInfo() != null && StringUtils.isNotBlank(pInBean.getConvocazioneInfo().getUri())){
			ConvocazioneInfoXmlBean lConvocazioneInfoXmlBean =  pInBean.getConvocazioneInfo();
			// Recupero il file da persistere
			File lFile = StorageImplementation.getStorage().extractFile(lConvocazioneInfoXmlBean.getUri());
			
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(lFile);
			Locale local = new Locale(loginBean.getLinguaApplicazione());
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut out = lSalvataggioFile.savefile(local, loginBean, lFileSavedIn);
			
			if(out.getErrorInSaved() != null) {
				mLogger.error("CONVOCAZIONE SEDUTA - Errore durante il salvataggio del file Convocazione in repository: " + out.getErrorInSaved());
				throw new StoreException("Si è verificato un errore durante il salvataggio del file Convocazione in repository");
			}
			
			lConvocazioneInfoXmlBean.setUri(out.getUri());
			pInBean.setConvocazioneInfo(lConvocazioneInfoXmlBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		DmpkSeduteOrgCollSaveconvocazionesedutaBean input = new DmpkSeduteOrgCollSaveconvocazionesedutaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setOrganocollegialein(pInBean.getOrganoCollegiale());
		
		
		//TODO da splittare
		//input.setListacommissioniin(pInBean.getListaCommissioni());
				
		// Salvo la lista con gli ID delle commissioni per cui si convoca seduta
		if (pInBean.getListaNomiCommissioniConvocate()!=null && !pInBean.getListaNomiCommissioniConvocate().equalsIgnoreCase("")) {
			input.setListacommissioniin(pInBean.getListaIdCommissioniConvocate());
		}
		
		/**
		 * In input può essere vuoto se la seduta è nuova, in output salvo in caso di errore è sempre valorizzato
		 */
		input.setIdsedutaio(pInBean.getIdSeduta());
		input.setDataora1aconvocazionein(pInBean.getDtPrimaConvocazione() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(pInBean.getDtPrimaConvocazione()) : null);
		input.setLuogo1aconvocazionein(pInBean.getLuogoPrimaConvocazione());
		input.setDataora2aconvocazionein(pInBean.getDtSecondaConvocazione() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(pInBean.getDtSecondaConvocazione()) : null);
		input.setLuogo2aconvocazionein(pInBean.getLuogoSecondaConvocazione());
		input.setTiposessionein(pInBean.getTipoSessione());
		input.setStatosedutain(pInBean.getStato());
		
		List<ArgomentiOdgXmlBean> listaArgomentiOdgCompleta = new ArrayList<ArgomentiOdgXmlBean>();
		if (pInBean.getListaArgomentiOdgEliminati() != null ) {
			for(ArgomentiOdgXmlBean itemEliminato : pInBean.getListaArgomentiOdgEliminati()) {
				listaArgomentiOdgCompleta.add(itemEliminato);
			}
		}
		if (pInBean.getListaArgomentiOdg() != null ) {
			for(ArgomentiOdgXmlBean itemCorrente : pInBean.getListaArgomentiOdg()) {
				listaArgomentiOdgCompleta.add(itemCorrente);
			}
		}
		input.setArgomentiodgin(lXmlUtilitySerializer.bindXmlList(listaArgomentiOdgCompleta));
		
		if (pInBean.getListaPresenzeOdg() != null) {
			for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : pInBean.getListaPresenzeOdg()) {
				if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getDelegato())) {
					lPresenzeOdgXmlBean.setIdDelegato(getIdDelegato(lPresenzeOdgXmlBean.getDelegato() + "|*|" + lPresenzeOdgXmlBean.getDecodificaDelegato()));
				} else {
					lPresenzeOdgXmlBean.setIdDelegato(null);
				}
			}
			input.setPresenzein(lXmlUtilitySerializer.bindXmlList(pInBean.getListaPresenzeOdg()));
		}		
		
		if (pInBean.getOdgInfo() != null) {
			pInBean.getOdgInfo().setDisattivatoRiordinoAutomatico(pInBean.getDisattivatoRiordinoAutomatico());
			input.setOdginfoio(lXmlUtilitySerializer.bindXml(pInBean.getOdgInfo()));
		}
		if(pInBean.getConvocazioneInfo() != null) {
			input.setConvocazioneinfoin(lXmlUtilitySerializer.bindXml(pInBean.getConvocazioneInfo()));
		}
		
		DmpkSeduteOrgCollSaveconvocazioneseduta dmpkSeduteOrgCollSaveconvocazioneseduta = new DmpkSeduteOrgCollSaveconvocazioneseduta();
		StoreResultBean<DmpkSeduteOrgCollSaveconvocazionesedutaBean> output = dmpkSeduteOrgCollSaveconvocazioneseduta.execute(getLocale(),loginBean, input);
		
		if(output.isInError()) {
			mLogger.error("CONVOCAZIONE SEDUTA - Errore store di salvataggio convocazione seduta: " + output.getDefaultMessage());
			throw new StoreException(output);						
		}
		
		pInBean.setIdSeduta(output.getResultBean().getIdsedutaio());

		/**
		 * N.ro di seduta progressivo per organo e per anno (salvo in caso di errore è sempre valorizzato)
		 */
		pInBean.setNumero(Integer.toString(output.getResultBean().getNrosedutaout()));
		if(StringUtils.isNotBlank(output.getResultBean().getOdginfoio())) {
			pInBean.setOdgInfo(new XmlUtilityDeserializer().unbindXml(output.getResultBean().getOdginfoio(), OdgInfoXmlBean.class));
		}

		return pInBean;		
	}
	
	public ConvocazioneSedutaBean getInfoFile(ConvocazioneSedutaBean bean) throws Exception {
		
		ConvocazioneSedutaBean output = new ConvocazioneSedutaBean();		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(StringUtils.isNotBlank(getExtraparams().get("idUd")) ? new BigDecimal(getExtraparams().get("idUd")) : null);
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(null);
		lRecuperaDocumentoInBean.setTsAnnDati(null);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
		FileDocumentoBean lFileDocumentoBean = new FileDocumentoBean();
		lFileDocumentoBean.setIdDoc(String.valueOf(documento.getIdDocPrimario()));
		lFileDocumentoBean.setDisplayFilename(documento.getNomeFilePrimario());
		lFileDocumentoBean.setUri(documento.getUriFilePrimario());
		lFileDocumentoBean.setInfo(documento.getInfoFile());
		
		output.setFileDocumento(lFileDocumentoBean);
		
		return output;
	}
	
	public FileDaFirmareBean generaModelloConvocazione(ConvocazioneSedutaBean lSalvaSedutaConvocazioneBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = lSalvaSedutaConvocazioneBean.getConvocazioneInfo() != null ? lSalvaSedutaConvocazioneBean.getConvocazioneInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModelloConvocazione(lSalvaSedutaConvocazioneBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, lSalvaSedutaConvocazioneBean.getConvocazioneInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());

		return templateWithValues;
	}
	
	private String getDatiXGenDaModelloConvocazione(ConvocazioneSedutaBean bean, String nomeModello) throws Exception {
		
		// Creo la sezione cache
		SezioneCache variabiliSezioneCache = creaSezioneCachePerModelloConvocazione(bean);
		
		// chiamo la get dati per modello
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdSeduta());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("S");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(variabiliSezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	private SezioneCache creaSezioneCachePerModelloConvocazione(ConvocazioneSedutaBean lConvocazioneSedutaBean) throws Exception {
		// Creo la sezione chache coi dati a maschera
		SezioneCache variabiliPerModello = new SezioneCache();
		// Metto tutte le variabile che mi servono
		// qua gestisco i ceck
		if (lConvocazioneSedutaBean.getConvocazioneInfo() != null && 
				StringUtils.isNotBlank(lConvocazioneSedutaBean.getConvocazioneInfo().getTestoHtml())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "TestoHtml", lConvocazioneSedutaBean.getConvocazioneInfo().getTestoHtml());
		}
		if(lConvocazioneSedutaBean.getConvocazioneInfo().getShowEditorDettSedute() != null &&
		   lConvocazioneSedutaBean.getConvocazioneInfo().getShowEditorDettSedute()){
		   if (lConvocazioneSedutaBean.getConvocazioneInfo() != null && 
					StringUtils.isNotBlank(lConvocazioneSedutaBean.getConvocazioneInfo().getDettSeduteHtml())) {
				putVariabileSempliceSezioneCache(variabiliPerModello, "DettSeduteHtml", lConvocazioneSedutaBean.getConvocazioneInfo().getDettSeduteHtml());
			}
		}
		

		return variabiliPerModello;	
		
	}
	
	public FileDaFirmareBean generaModelloOdg(ConvocazioneSedutaBean lSalvaSedutaConvocazioneBean) throws Exception {
		// Ricavo i dati da iniettare nel modello
		String nomeModello = lSalvaSedutaConvocazioneBean.getOdgInfo() != null ? lSalvaSedutaConvocazioneBean.getOdgInfo().getNomeModello() : ""; 
		String templateValues = getDatiXGenDaModelloOdg(lSalvaSedutaConvocazioneBean, nomeModello);
		// Genero da modello
		Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean templateWithValues = ModelliUtil.fillFreeMarkerTemplateWithModel(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), mappaValoriCopertinaFinale, true, true, getSession());
		// FileDaFirmareBean templateWithValues = ModelliUtil.fillTemplate(null, lSalvaSedutaConvocazioneBean.getOdgInfo().getIdModello(), templateValues, true, getSession());
		return templateWithValues;
	}
	
	private String getDatiXGenDaModelloOdg(ConvocazioneSedutaBean bean, String nomeModello) throws Exception {
				
		String tipoOdGConsolidato = getExtraparams().get("tipoOdGConsolidato");
		
		// Creo la sezione cache
		SezioneCache variabiliSezioneCache = creaSezioneCachePerModelloOdg(bean, tipoOdGConsolidato);
		
		// chiamo la get dati per modello
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdSeduta());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("S");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(variabiliSezioneCache);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	private SezioneCache creaSezioneCachePerModelloOdg(ConvocazioneSedutaBean lConvocazioneSedutaBean, String tipoOdGConsolidato) throws Exception {
		// Creo la sezione chache coi dati a maschera
		SezioneCache variabiliPerModello = new SezioneCache();
		// Metto tutte le variabile che mi servono
		// qua gestisco i ceck
		SimpleDateFormat dateFormatter = new SimpleDateFormat(FMT_STD_DATA);
		
		if (StringUtils.isNotBlank(tipoOdGConsolidato)) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "tipoOdGConsolidato", tipoOdGConsolidato);
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getIdSeduta())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "idSeduta", lConvocazioneSedutaBean.getIdSeduta());
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getOrganoCollegiale())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "organoCollegiale", lConvocazioneSedutaBean.getOrganoCollegiale());
		}
		if (lConvocazioneSedutaBean.getDtPrimaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "dtPrimaConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtPrimaConvocazione()));
		}
		if (lConvocazioneSedutaBean.getDtSecondaConvocazione() != null) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "dtSecondaConvocazione", dateFormatter.format(lConvocazioneSedutaBean.getDtSecondaConvocazione()));
		}
		if (StringUtils.isNotBlank(lConvocazioneSedutaBean.getTipoSessione())) {
			putVariabileSempliceSezioneCache(variabiliPerModello, "tipoSessione", lConvocazioneSedutaBean.getTipoSessione());
		}
		if (lConvocazioneSedutaBean.getListaArgomentiOdg() != null) {
			putVariabileListaSezioneCache(variabiliPerModello, "listaArgomentiOdg", new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaArgomentiOdg()));
		}
		if (lConvocazioneSedutaBean.getListaPresenzeOdg() != null) {
			for(PresenzeOdgXmlBean lPresenzeOdgXmlBean : lConvocazioneSedutaBean.getListaPresenzeOdg()) {
				if(StringUtils.isNotBlank(lPresenzeOdgXmlBean.getDelegato())) {
					lPresenzeOdgXmlBean.setIdDelegato(getIdDelegato(lPresenzeOdgXmlBean.getDelegato()));
				}
			}
			Lista listaPresenzeOdg = new XmlUtilitySerializer().createVariabileLista(lConvocazioneSedutaBean.getListaPresenzeOdg());
			putVariabileListaSezioneCache(variabiliPerModello, "listaPresenzeOdg", listaPresenzeOdg);		
		}
		
		return variabiliPerModello;	
		
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
		
	public FileDocumentoBean getFileFromIdUd(ArgomentiOdgXmlBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(new BigDecimal(bean.getIdUd()));
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni("1");
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
		FileDocumentoBean lFileDocumentoBean = new FileDocumentoBean();
		lFileDocumentoBean.setIdDoc(documento.getIdDocPrimario() != null ? String.valueOf(documento.getIdDocPrimario()) : null);
		lFileDocumentoBean.setDisplayFilename(documento.getNomeFilePrimario());
		lFileDocumentoBean.setUri(documento.getUriFilePrimario());
		lFileDocumentoBean.setInfo(documento.getInfoFile());
		
		return lFileDocumentoBean;
	}
	
	protected String getIdDelegato(String value) {
		String ret = "";
		if (value != null && !value.equalsIgnoreCase("")){
			String[] listaValoriString = value.split("\\|\\*\\|");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			String idDelegato = null;
			if (listaValoriList.size() > 0 ){
				idDelegato = listaValoriList.get(0).trim();
			}
			ret = idDelegato;
		}
		return ret;
	}
	
	public OperazioneMassivaArgomentiOdgXmlBean scaricaSchedaSintesi(OperazioneMassivaArgomentiOdgXmlBean bean) throws Exception {
		
		try {
			
			List<File> listaFileDaUnire = new ArrayList<File>();
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
						
			if(bean.getListaRecord().size() > 0) {
				for (int i = 0; i < bean.getListaRecord().size(); ++i) {
					try {
						ArgomentiOdgXmlBean lArgomentiOdgXmlBean = (ArgomentiOdgXmlBean) bean.getListaRecord().get(i);
						String uriScheda = lArgomentiOdgXmlBean.getUriSchedaSintesi();
						
						if(uriScheda != null && "#NULL".equalsIgnoreCase(uriScheda)) {
							mLogger.error("File non valido per l'unione");
						} else if(uriScheda != null && !"".equalsIgnoreCase(uriScheda)) {
							File tempScheda = StorageImplementation.getStorage().extractFile(uriScheda);
							listaFileDaUnire.add(tempScheda);
						} else {
							
							// l'uri è vuoto quindi devo generare da modello
							String idModSchedaSintesi = ParametriDBUtil.getParametroDB(getSession(), "ID_MODELLO_SCHEDA_SINTETICA_ATTO");
							String nomeModSchedaSintesi = ParametriDBUtil.getParametroDB(getSession(), "NOME_MODELLO_SCHEDA_SINTETICA_ATTO");	
							String nomeTipoDocSchedaSintesi = ParametriDBUtil.getParametroDB(getSession(), "NOME_TIPO_DOC_SCHEDA_SINTESI");	
							
							if(StringUtils.isNotBlank(idModSchedaSintesi) && StringUtils.isNotBlank(nomeModSchedaSintesi)) {	
								
								DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
								DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
								lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
								lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);						
								lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(lArgomentiOdgXmlBean.getIdUd());
								lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
								lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModSchedaSintesi);
					
								StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
										lDmpkModelliDocGetdatixgendamodelloInput);
								if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
									throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
								}
								
								String templateValuesSchedaSintesi = lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
								Map<String, Object> mappaValoriSchedaSintesi = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesSchedaSintesi, true);
								FileDaFirmareBean lFileDaFirmareBeanSchedaSintesi = ModelliUtil.fillFreeMarkerTemplateWithModel(null, idModSchedaSintesi, mappaValoriSchedaSintesi, true, true, getSession());
								lFileDaFirmareBeanSchedaSintesi.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanSchedaSintesi.getUri()));
								
								listaFileDaUnire.add(lFileDaFirmareBeanSchedaSintesi.getFile());
								
								if(StringUtils.isBlank(lArgomentiOdgXmlBean.getIdDocSchedaSintesi())) {
									DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
									lAdddocInput.setCodidconnectiontokenin(loginBean.getToken());
									lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);						
									AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();
									lAllegatoStoreBean.setIdUd(new BigDecimal(lArgomentiOdgXmlBean.getIdUd()));
									lAllegatoStoreBean.setNomeDocType(nomeTipoDocSchedaSintesi);
									XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
									lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lAllegatoStoreBean));
									DmpkCoreAdddoc lDmpkCoreAdddoc = new DmpkCoreAdddoc();
									StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lDmpkCoreAdddoc.execute(getLocale(), loginBean, lAdddocInput);
									if (StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
										if (lAdddocOutput.isInError()) {
											throw new StoreException(lAdddocOutput);
										} else {
											addMessage(lAdddocOutput.getDefaultMessage(), "", MessageType.WARNING);
										}
									}
									lArgomentiOdgXmlBean.setIdDocSchedaSintesi(lAdddocOutput.getResultBean().getIddocout() != null ? String.valueOf(lAdddocOutput.getResultBean().getIddocout().longValue()) : null);									
								}
								
								lFileDaFirmareBeanSchedaSintesi.setIdFile(lArgomentiOdgXmlBean.getIdDocSchedaSintesi());								
								versionaDocumentoAllegato(lFileDaFirmareBeanSchedaSintesi);
							} 
						}
					} catch (Exception e) {
						mLogger.error("Errore creazione allegato scheda sintesi con IdUD: " + bean.getListaRecord().get(i).getIdUd() + " URI: " +
						bean.getListaRecord().get(i).getUriSchedaSintesi(), e);		
						throw e;
					}
				}
			}
			
			if(!listaFileDaUnire.isEmpty()) {		
				File lFileUnione = new MergeDocument().mergeDocuments(listaFileDaUnire.toArray(new File[listaFileDaUnire.size()]));
				String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione);
				bean.setUriUnioneSchedaSintesi(uriFileUnione);
			}
			
		} catch (StoreException se) {
			throw new StoreException("Si è verificato un errore durante la creazione dell'allegato scheda sintesi: " + se.getMessage());
		} catch (Exception e) {
			throw new StoreException("Si è verificato un errore durante la creazione dell'allegato scheda sintesi");
		}
		
		return bean;
	}
	
	public void versionaDocumentoAllegato(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(
				StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
			
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.ALLEGATO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFile());
		lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
		lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
		lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
		lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
		if (lMimeTypeFirmaBean.isDaScansione()) {
			lGenericFile.setDaScansione(Flag.SETTED);
			lGenericFile.setDataScansione(new Date());
			lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
		}
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		lRebuildedFile.setInfo(lFileInfoBean);

		VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
			mLogger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}
	}
	
	public OpzioniFileArgSedutaBean scaricaFileArgSedutaXDownload(OpzioniFileArgSedutaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<SimpleValueBean> listaIdUdArgSelXmlBean = new ArrayList<SimpleValueBean>();
		for (int i = 0; i < bean.getListaArgomentiOdgXmlBean().size(); ++i) {
		
			ArgomentiOdgXmlBean lArgomentiOdgXmlBean = (ArgomentiOdgXmlBean) bean.getListaArgomentiOdgXmlBean().get(i);
			SimpleValueBean lSimpleValueBean = new SimpleValueBean();
			lSimpleValueBean.setValue(lArgomentiOdgXmlBean.getIdUd());
			listaIdUdArgSelXmlBean.add(lSimpleValueBean);
		}
				
		DmpkSeduteOrgCollGetfileargsedutaxdownloadBean input = new DmpkSeduteOrgCollGetfileargsedutaxdownloadBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(getExtraparams().get("idSeduta"));
		
		String listaIdUd = new XmlUtilitySerializer().bindXmlList(listaIdUdArgSelXmlBean);
		input.setListaidudargselin(listaIdUd);
		
		input.setFlginclusipareriin(bean.getFlgInclusiPareri() != null && "SI".equalsIgnoreCase(bean.getFlgInclusiPareri()) ? new BigDecimal(1) : new BigDecimal(0));
		
		if(bean.getFlgInclAllegatiPI().equalsIgnoreCase("NO")) {
			input.setFlginclallegatipiin(null);
		} else {
			input.setFlginclallegatipiin(bean.getFlgInclAllegatiPI());
		}

		if(bean.getFlgIntXPubbl().equalsIgnoreCase("entrambe")) {
			input.setFlgintxpubblin(null);
		} else {
			input.setFlgintxpubblin(bean.getFlgIntXPubbl());
		}
		
		DmpkSeduteOrgCollGetfileargsedutaxdownload dmpkSeduteOrgCollGetfileargsedutaxdownload = new DmpkSeduteOrgCollGetfileargsedutaxdownload();
		StoreResultBean<DmpkSeduteOrgCollGetfileargsedutaxdownloadBean> output = dmpkSeduteOrgCollGetfileargsedutaxdownload.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			throw new StoreException(output);
		} else {
			addMessage("Richiesta acquisita. L'elaborazione e scarico del file potrebbe richiedere qualche secondo", "", MessageType.WARNING);
		}

		List<FileArgSedutaXDownloadXmlBean> listaFileXDownload = new ArrayList<FileArgSedutaXDownloadXmlBean>(); 
		if(output.getResultBean() != null) {
			if(StringUtils.isNotBlank(output.getResultBean().getListafilexdownloadout())) {
				listaFileXDownload = XmlListaUtility.recuperaLista(output.getResultBean().getListafilexdownloadout(), FileArgSedutaXDownloadXmlBean.class);						
			}
		}
		
		if(!listaFileXDownload.isEmpty()) {
			
			try {
				Map<String, List<FileArgSedutaXDownloadXmlBean>> mappaUnioneFile = creaMappaFileDaUnire(listaFileXDownload);		
				
				List<FileUnitoBean> listaFileDaScaricare = creaFilesUnione(mappaUnioneFile);
				
				String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
				Date date = new Date();
				long timeMilli = date.getTime();
				File zipFile = new File(tempPath + "file_atto" +  timeMilli + ".zip");
				for(FileUnitoBean file : listaFileDaScaricare) {
					StorageUtil.addFileToZip(file.getFile(), file.getNomeFileUnito(), zipFile.getAbsolutePath());
				}
				
				String uriFileUnione = StorageImplementation.getStorage().store(zipFile);
				bean.setUriUnioneFile(uriFileUnione);
			}catch (Exception e) {
				String errorMessage = "Errore durante il processo di unione dei file o creazione del file zip: " + e.getMessage();
				mLogger.error(errorMessage, e);
				throw new StoreException(errorMessage);
			}
			
		}
		
		return bean;
	}
	
	public OpzioniTrasmissioneAttiBean trasmissioneAttiSelezionati(OpzioniTrasmissioneAttiBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<SimpleValueBean> listaIdUdArgSelXmlBean = new ArrayList<SimpleValueBean>();
		for (int i = 0; i < bean.getListaArgomentiOdgXmlBean().size(); ++i) {
		
			ArgomentiOdgXmlBean lArgomentiOdgXmlBean = (ArgomentiOdgXmlBean) bean.getListaArgomentiOdgXmlBean().get(i);
			SimpleValueBean lSimpleValueBean = new SimpleValueBean();
			lSimpleValueBean.setValue(lArgomentiOdgXmlBean.getIdUd());
			listaIdUdArgSelXmlBean.add(lSimpleValueBean);
		}
				
		DmpkSeduteOrgCollGetfileargsedutaxdownloadBean input = new DmpkSeduteOrgCollGetfileargsedutaxdownloadBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdsedutain(getExtraparams().get("idSeduta"));
		input.setFlgtrasmissioneattiin(1);
		
		String listaIdUd = new XmlUtilitySerializer().bindXmlList(listaIdUdArgSelXmlBean);
		input.setListaidudargselin(listaIdUd);
		
		input.setFlginclusipareriin(bean.getFlgInclusiPareri() != null && "SI".equalsIgnoreCase(bean.getFlgInclusiPareri()) ? new BigDecimal(1) : new BigDecimal(0));
		
		if(bean.getFlgInclAllegatiPI().equalsIgnoreCase("NO")) {
			input.setFlginclallegatipiin(null);
		} else {
			input.setFlginclallegatipiin(bean.getFlgInclAllegatiPI());
		}

		if(bean.getFlgIntXPubbl().equalsIgnoreCase("entrambe")) {
			input.setFlgintxpubblin(null);
		} else {
			input.setFlgintxpubblin(bean.getFlgIntXPubbl());
		}
		
		InfoTrasmissioneAttiXmlBean lInfoTrasmissioneXmlBean = new InfoTrasmissioneAttiXmlBean();
		lInfoTrasmissioneXmlBean.setReinvioAttiTrasmessi(bean.getFlgInvioAttiTrasmessi());        
		
		lInfoTrasmissioneXmlBean.setAccountMittente(bean.getMittente());
		List<XmlListaSimpleBean> listaDestinatariFinali = new ArrayList<XmlListaSimpleBean>();
		//Destinatari principali
		List<String> destinatariP = new ArrayList<String>();
		String[] lStringDestinatari = IndirizziEmailSplitter.split(bean.getDestinatari());
		destinatariP = Arrays.asList(lStringDestinatari);
		for(String destP : destinatariP) {
			XmlListaSimpleBean destPXml = new XmlListaSimpleBean();
			destPXml.setKey(destP);
			destPXml.setValue("P");
			listaDestinatariFinali.add(destPXml);
		}
		//Destinatari CC
		List<String> destinatariCC = new ArrayList<String>();
		String[] lStringDestinatariCC = IndirizziEmailSplitter.split(bean.getDestinatariCC());
		destinatariCC = Arrays.asList(lStringDestinatariCC);
		for(String destC : destinatariCC) {
			XmlListaSimpleBean destCCXml = new XmlListaSimpleBean();
			destCCXml.setKey(destC);
			destCCXml.setValue("CC");
			listaDestinatariFinali.add(destCCXml);
		}
		lInfoTrasmissioneXmlBean.setListaDestinatari(listaDestinatariFinali);
		lInfoTrasmissioneXmlBean.setSubject(bean.getOggetto());
		lInfoTrasmissioneXmlBean.setBody(bean.getBody());
		input.setInfotrasmissionexmlin(new XmlUtilitySerializer().bindXml(lInfoTrasmissioneXmlBean));
		
		DmpkSeduteOrgCollGetfileargsedutaxdownload dmpkSeduteOrgCollGetfileargsedutaxdownload = new DmpkSeduteOrgCollGetfileargsedutaxdownload();
		StoreResultBean<DmpkSeduteOrgCollGetfileargsedutaxdownloadBean> output = dmpkSeduteOrgCollGetfileargsedutaxdownload.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}

	/**
	 * @param listaFileXDownload
	 * @param mappaUnioneFile
	 * @return 
	 */
	public Map<String, List<FileArgSedutaXDownloadXmlBean>> creaMappaFileDaUnire(List<FileArgSedutaXDownloadXmlBean> listaFileXDownload) {
		Map<String, List<FileArgSedutaXDownloadXmlBean>> mappaUnioneFile = new HashMap<String, List<FileArgSedutaXDownloadXmlBean>>();

		for(FileArgSedutaXDownloadXmlBean fileBean : listaFileXDownload) {
			String idUd = fileBean.getIdUd();
			String flgIntegraleOmissis = fileBean.getFlgVersione();
			String chiaveMappa = idUd + "_" + flgIntegraleOmissis;
			if(mappaUnioneFile.containsKey(chiaveMappa)) {
				List<FileArgSedutaXDownloadXmlBean> listaFileDaUnire = mappaUnioneFile.get(chiaveMappa);
				listaFileDaUnire.add(fileBean);	
				mappaUnioneFile.put(chiaveMappa, listaFileDaUnire);
			} else {
				List<FileArgSedutaXDownloadXmlBean> listaFileDaUnire = new ArrayList<FileArgSedutaXDownloadXmlBean>();
				listaFileDaUnire.add(fileBean);
				mappaUnioneFile.put(chiaveMappa, listaFileDaUnire);
			}
		}
		
		return mappaUnioneFile;
	}

	private List<FileUnitoBean> creaFilesUnione(Map<String, List<FileArgSedutaXDownloadXmlBean>> mappaUnioneFile) throws Exception {
		List<FileUnitoBean> listaFileUniti = new ArrayList<FileUnitoBean>();
		
		for(String chiaveMappa : mappaUnioneFile.keySet()) {
			List<FileArgSedutaXDownloadXmlBean> listaFileDaUnireMappa = mappaUnioneFile.get(chiaveMappa);
			List<File> listaFileDaUnire = new ArrayList<File>();
			String nomeFileUnione = "FileUnione.pdf";
			for(FileArgSedutaXDownloadXmlBean fileBean : listaFileDaUnireMappa) {
				File fileDaUnire = File.createTempFile("temp", ".tmp");
				FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileBean.getUri()), fileDaUnire);			
				
				if(listaFileDaUnireMappa.size() == 1) {
					// se è l'unico file, non effettuare sbustamento o conversione
					listaFileDaUnire.add(fileDaUnire);
				} else {
					if(fileBean.getFlgFirmatoDaSbustare() == 1) {				
						fileDaUnire = sbustaFile(fileDaUnire, "tmp.p7m");					
					} else if (fileBean.getFlgConvertibile() == 1) {
						fileDaUnire = convertiFile(fileDaUnire, "tmp.p7m");
					}

					listaFileDaUnire.add(fileDaUnire);
				}
				
				nomeFileUnione = fileBean.getNomeFile();
			}
			
			if(listaFileDaUnire.size() == 1) {
				FileUnitoBean fileUnitoBean = new FileUnitoBean();
				fileUnitoBean.setNomeFileUnito(nomeFileUnione);
				fileUnitoBean.setFile(listaFileDaUnire.get(0));
				
				listaFileUniti.add(fileUnitoBean);
			} else {
				File mergedPdf = File.createTempFile("mergedPdf", ".pdf");
				try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(mergedPdf))){			
					MergeDocument merger = new MergeDocument();
					merger.mergeDocument(listaFileDaUnire, output, false, false);								
				}
				
				FileUnitoBean fileUnitoBean = new FileUnitoBean();
				fileUnitoBean.setNomeFileUnito(nomeFileUnione);
				fileUnitoBean.setFile(mergedPdf);
				
				listaFileUniti.add(fileUnitoBean);
			}

		}
		
		return listaFileUniti;
	}
	
	public File sbustaFile(File fileOriginale, String nomeFile)
			throws IOException, Exception, StorageException {
		
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		InputStream sbustatoStream = lInfoFileUtility.sbusta(fileOriginale, nomeFile);
		
		File fileSbustato = File.createTempFile("temp", nomeFile);
		
		FileUtil.writeStreamToFile(sbustatoStream, fileSbustato);
		
		return fileSbustato;
	}
	
	public File convertiFile(File fileOriginale, String nomeFile)
			throws IOException, Exception, StorageException {
		
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		InputStream sbustatoStream = lInfoFileUtility.converti(fileOriginale.toURI().toString(), nomeFile);
		
		File fileConvertito = File.createTempFile("temp", nomeFile);
		
		FileUtil.writeStreamToFile(sbustatoStream, fileConvertito);
		
		return fileConvertito;
	}
	 
}