/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.bean.CompilaModelloPropostaOrganigrammaBean;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.bean.PropostaOrganigrammaBean;
import it.eng.auriga.ui.module.layout.server.proposteOrganigramma.bean.TaskPropostaOrganigrammaFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "PropostaOrganigrammaDataSource")
public class PropostaOrganigrammaDataSource extends AbstractDataSource<PropostaOrganigrammaBean, PropostaOrganigrammaBean> {

	private static final Logger logger = Logger.getLogger(PropostaOrganigrammaDataSource.class);
	
	public ProtocolloDataSource getProtocolloDataSource(final PropostaOrganigrammaBean pPropostaOrganigrammaBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
				return false;
			}		
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pPropostaOrganigrammaBean != null) {
					salvaAttributiCustomProposta(pPropostaOrganigrammaBean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di PropostaOrganigrammaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	@Override
	public Map<String, String> getExtraparams() {		
		
		Map<String, String> extraparams = super.getExtraparams();
		extraparams.put("isPropostaOrganigramma", "true");
		return extraparams;		
	}
	
	@Override
	public PropostaOrganigrammaBean get(PropostaOrganigrammaBean bean) throws Exception {	
		
 		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		String idProcess = getExtraparams().get("idProcess");
		String taskName = getExtraparams().get("taskName");
		BigDecimal idUd = StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null;
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
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
		
		bean.setIdUd(idUd != null ? String.valueOf(idUd.intValue()) : null);
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
		
		/* Registrazione */
		
		if(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null) {
			bean.setSiglaRegistrazione(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegistrazione(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegistrazione(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegistrazione(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegistrazione(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegistrazione(lProtocollazioneBean.getDesUOProtocollo());		
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaNumerazioneSecondaria());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null ? String.valueOf(lProtocollazioneBean.getNumeroNumerazioneSecondaria().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataRegistrazioneNumerazioneSecondaria());			
		} else {
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegProvvisoria(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegProvvisoria(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegProvvisoria(lProtocollazioneBean.getDesUOProtocollo());	
		}
		
		/* Ufficio revisione organigramma */
		bean.setIdUoRevisioneOrganigramma(lDocumentoXmlOutBean.getIdUoRevisioneOrganigramma());		
		bean.setIdUoPadreRevisioneOrganigramma(lDocumentoXmlOutBean.getIdUoPadreRevisioneOrganigramma());		
		bean.setCodRapidoUoRevisioneOrganigramma(lDocumentoXmlOutBean.getCodRapidoUoRevisioneOrganigramma());			
		bean.setNomeUoRevisioneOrganigramma(lDocumentoXmlOutBean.getNomeUoRevisioneOrganigramma());					
		bean.setTipoUoRevisioneOrganigramma(lDocumentoXmlOutBean.getTipoUoRevisioneOrganigramma());					
		bean.setLivelloUoRevisioneOrganigramma(lDocumentoXmlOutBean.getLivelloUoRevisioneOrganigramma());			
		
		/* Oggetto */
		
		bean.setOggetto(lProtocollazioneBean.getOggetto());				
	
		/* Testo proposta */
		
		bean.setTestoProposta(lDocumentoXmlOutBean.getTestoPropostaRevisioneOrganigramma());
		
		/* Allegati */
		
		bean.setListaAllegati(lProtocollazioneBean.getListaAllegati() != null ? lProtocollazioneBean.getListaAllegati() : new ArrayList<AllegatoProtocolloBean>());					
		
		return bean;
	}
	
	@Override
	public PropostaOrganigrammaBean add(PropostaOrganigrammaBean bean) throws Exception {

		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();	
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromPropostaOrganigrammaBean(lProtocollazioneBeanInput, bean);
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).add(lProtocollazioneBeanInput);
			bean.setIdUd(lProtocollazioneBeanOutput.getIdUd() != null ? String.valueOf(lProtocollazioneBeanOutput.getIdUd().longValue()) : null);
			bean.setRowidDoc(lProtocollazioneBeanOutput.getRowidDoc());
			bean.setIdDocPrimario(lProtocollazioneBeanOutput.getIdDocPrimario() != null ? String.valueOf(lProtocollazioneBeanOutput.getIdDocPrimario().longValue()) : null);
			bean.setSiglaRegProvvisoria(lProtocollazioneBeanOutput.getSiglaProtocollo());
			bean.setNumeroRegProvvisoria(lProtocollazioneBeanOutput.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBeanOutput.getNroProtocollo().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBeanOutput.getDataProtocollo());
			bean.setAnnoRegProvvisoria(lProtocollazioneBeanOutput.getAnnoProtocollo());
			bean.setDesUserRegProvvisoria(lProtocollazioneBeanOutput.getDesUserProtocollo());
			bean.setDesUORegProvvisoria(lProtocollazioneBeanOutput.getDesUOProtocollo());	
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		return bean;
	}

	@Override
	public PropostaOrganigrammaBean update(PropostaOrganigrammaBean bean, PropostaOrganigrammaBean oldvalue) throws Exception {

		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		try {
			lProtocollazioneBeanInput = getProtocolloDataSource(bean).get(lProtocollazioneBeanInput);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromPropostaOrganigrammaBean(lProtocollazioneBeanInput, bean);
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).update(lProtocollazioneBeanInput, null);
			//TODO qui mi posso già recuperare i dati di registrazione?
			if (lProtocollazioneBeanOutput.getFileInErrors() != null && lProtocollazioneBeanOutput.getFileInErrors().size() > 0) {
				for (String error : lProtocollazioneBeanOutput.getFileInErrors().values()) {
					logger.error(error);					
				}
				throw new StoreException("Si è verificato un errore durante il salvataggio: alcuni dei file sono andati in errore");
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		// Recupero i dati di registrazione
		try {
			lProtocollazioneBeanInput = getProtocolloDataSource(bean).get(lProtocollazioneBeanInput);
			if(lProtocollazioneBeanInput.getNumeroNumerazioneSecondaria() != null) {
				bean.setSiglaRegistrazione(lProtocollazioneBeanInput.getSiglaProtocollo());
				bean.setNumeroRegistrazione(lProtocollazioneBeanInput.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBeanInput.getNroProtocollo().longValue()) : null);
				bean.setDataRegistrazione(lProtocollazioneBeanInput.getDataProtocollo());
				bean.setDesUserRegistrazione(lProtocollazioneBeanInput.getDesUserProtocollo());
				bean.setDesUORegistrazione(lProtocollazioneBeanInput.getDesUOProtocollo());		
				bean.setSiglaRegProvvisoria(lProtocollazioneBeanInput.getSiglaNumerazioneSecondaria());
				bean.setNumeroRegProvvisoria(lProtocollazioneBeanInput.getNumeroNumerazioneSecondaria() != null ? String.valueOf(lProtocollazioneBeanInput.getNumeroNumerazioneSecondaria().longValue()) : null);
				bean.setDataRegProvvisoria(lProtocollazioneBeanInput.getDataRegistrazioneNumerazioneSecondaria());			
			} else {
				bean.setSiglaRegProvvisoria(lProtocollazioneBeanInput.getSiglaProtocollo());
				bean.setNumeroRegProvvisoria(lProtocollazioneBeanInput.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBeanInput.getNroProtocollo().longValue()) : null);
				bean.setDataRegProvvisoria(lProtocollazioneBeanInput.getDataProtocollo());
				bean.setDesUserRegProvvisoria(lProtocollazioneBeanInput.getDesUserProtocollo());
				bean.setDesUORegProvvisoria(lProtocollazioneBeanInput.getDesUOProtocollo());	
			}
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		return bean;
	}
	
	private void populateProtocollazioneBeanFromPropostaOrganigrammaBean(ProtocollazioneBean pProtocollazioneBean, PropostaOrganigrammaBean pPropostaOrganigrammaBean) {
		
		if(pProtocollazioneBean != null && pPropostaOrganigrammaBean != null) {
			
			/* Hidden */
			
			pProtocollazioneBean.setIdUd(StringUtils.isNotBlank(pPropostaOrganigrammaBean.getIdUd()) ? new BigDecimal(pPropostaOrganigrammaBean.getIdUd()) : null);
			pProtocollazioneBean.setTipoDocumento(pPropostaOrganigrammaBean.getTipoDocumento());
			pProtocollazioneBean.setNomeTipoDocumento(pPropostaOrganigrammaBean.getNomeTipoDocumento());
			pProtocollazioneBean.setRowidDoc(pPropostaOrganigrammaBean.getRowidDoc());
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pPropostaOrganigrammaBean.getIdDocPrimario()) ? new BigDecimal(pPropostaOrganigrammaBean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setNomeFilePrimario(pPropostaOrganigrammaBean.getNomeFilePrimario());
			pProtocollazioneBean.setUriFilePrimario(pPropostaOrganigrammaBean.getUriFilePrimario());
			pProtocollazioneBean.setRemoteUriFilePrimario(pPropostaOrganigrammaBean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setInfoFile(pPropostaOrganigrammaBean.getInfoFilePrimario());
			pProtocollazioneBean.setIsDocPrimarioChanged(pPropostaOrganigrammaBean.getIsChangedFilePrimario());
			pProtocollazioneBean.setFlgDatiSensibili(pPropostaOrganigrammaBean.getFlgDatiSensibili());
			
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pPropostaOrganigrammaBean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pPropostaOrganigrammaBean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pPropostaOrganigrammaBean.getUriFilePrimarioOmissis());
			lFilePrimarioOmissis.setRemoteUri(pPropostaOrganigrammaBean.getRemoteUriFilePrimarioOmissis());		
			lFilePrimarioOmissis.setInfoFile(pPropostaOrganigrammaBean.getInfoFilePrimarioOmissis());
			lFilePrimarioOmissis.setIsChanged(pPropostaOrganigrammaBean.getIsChangedFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
			
			/* Registrazione */
			
			if (StringUtils.isNotBlank(pPropostaOrganigrammaBean.getNumeroRegistrazione())) {
				pProtocollazioneBean.setSiglaProtocollo(pPropostaOrganigrammaBean.getSiglaRegistrazione());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pPropostaOrganigrammaBean.getNumeroRegistrazione()) ? new BigDecimal(pPropostaOrganigrammaBean.getNumeroRegistrazione()) : null);
				pProtocollazioneBean.setDataProtocollo(pPropostaOrganigrammaBean.getDataRegistrazione());
				pProtocollazioneBean.setDesUserProtocollo(pPropostaOrganigrammaBean.getDesUserRegistrazione());
				pProtocollazioneBean.setDesUOProtocollo(pPropostaOrganigrammaBean.getDesUORegistrazione());		
				pProtocollazioneBean.setSiglaNumerazioneSecondaria(pPropostaOrganigrammaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNumeroNumerazioneSecondaria(StringUtils.isNotBlank(pPropostaOrganigrammaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pPropostaOrganigrammaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataRegistrazioneNumerazioneSecondaria(pPropostaOrganigrammaBean.getDataRegProvvisoria());			
			} else {
				pProtocollazioneBean.setSiglaProtocollo(pPropostaOrganigrammaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pPropostaOrganigrammaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pPropostaOrganigrammaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataProtocollo(pPropostaOrganigrammaBean.getDataRegProvvisoria());
				pProtocollazioneBean.setDesUserProtocollo(pPropostaOrganigrammaBean.getDesUserRegProvvisoria());
				pProtocollazioneBean.setDesUOProtocollo(pPropostaOrganigrammaBean.getDesUORegProvvisoria());	
			}
			
			/* Ufficio revisione organigramma */			
			pProtocollazioneBean.setIdUoRevisioneOrganigramma(pPropostaOrganigrammaBean.getIdUoRevisioneOrganigramma());
			pProtocollazioneBean.setIdUoPadreRevisioneOrganigramma(pPropostaOrganigrammaBean.getIdUoPadreRevisioneOrganigramma());
			pProtocollazioneBean.setCodRapidoUoRevisioneOrganigramma(pPropostaOrganigrammaBean.getCodRapidoUoRevisioneOrganigramma());			
			pProtocollazioneBean.setNomeUoRevisioneOrganigramma(pPropostaOrganigrammaBean.getNomeUoRevisioneOrganigramma());					
			pProtocollazioneBean.setTipoUoRevisioneOrganigramma(pPropostaOrganigrammaBean.getTipoUoRevisioneOrganigramma());					
			pProtocollazioneBean.setLivelloUoRevisioneOrganigramma(pPropostaOrganigrammaBean.getLivelloUoRevisioneOrganigramma());			
			
			/* Oggetto */
			
			pProtocollazioneBean.setOggetto(pPropostaOrganigrammaBean.getOggetto());
			
			/* Allegati */
			
			pProtocollazioneBean.setListaAllegati(pPropostaOrganigrammaBean.getListaAllegati());
			
		}
		
	}

	protected void salvaAttributiCustomProposta(PropostaOrganigrammaBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
				
		/* Testo proposta */
		
		String testoProposta = bean.getTestoProposta() != null  ? bean.getTestoProposta() : "";
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TESTO_PROPOSTA_REVISIONE_ORGANIGRAMMA_Doc", testoProposta);
		
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
	
	private String getDatiXGenDaModello(PropostaOrganigrammaBean bean, String nomeModello) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUd());
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici);
		
		if(StringUtils.isNotBlank(getExtraparams().get("esitoTask"))) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "#EsitoTask", getExtraparams().get("esitoTask"));		
		}
		
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lDmpkModelliDocGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean,
				lDmpkModelliDocGetdatixgendamodelloInput);
		if (lDmpkModelliDocGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lDmpkModelliDocGetdatixgendamodelloOutput);
		}
		
		return lDmpkModelliDocGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();				
	}
	
	public FileDaFirmareBean generaPropostaDaModello(PropostaOrganigrammaBean bean) throws Exception {
		return generaPropostaDaModello(bean, true);
	}
	
	private FileDaFirmareBean generaPropostaDaModello(PropostaOrganigrammaBean bean, boolean flgConvertiInPdf) throws Exception {
		
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // PROPOSTA ORGANIGRAMMA
//		Map<String, Object> model = createMapToFillFreeMarkerTemplate(bean);
		Map<String, Object> model = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), model, flgConvertiInPdf, bean.getFlgMostraDatiSensibili(), getSession());
		
		/*
		SezioneCache lSezioneCache = null;
		if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
			lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(),
					getSession());
		} else {
			lSezioneCache = new SezioneCache();
		}
		// bisogna aggiungere in lSezioneCache gli altri campi a maschera
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(lSezioneCache);
		String xmlAttributiDinamici = null;
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		xmlAttributiDinamici = lXmlUtilitySerializer.bindXml(lAttributiDinamiciXmlBean);		
		
		File fileToReturn = ModelliUtil.fillTemplate(bean.getIdProcess(), bean.getIdModello(), xmlAttributiDinamici, bean.getFlgMostraDatiSensibili(), getLocale(), AurigaUserUtil.getLoginInfo(getSession()));
 		*/	
		
		fillModelBean.getInfoFile().setCorrectFileName(bean.getDisplayFilenameModello());
		
		String ext = flgConvertiInPdf ? "pdf" : FilenameUtils.getExtension(fillModelBean.getNomeFile());
		String nomeFilePdf = String.format(bean.getFlgMostraDatiSensibili() ? "Proposta versione integrale.%s" : "Proposta versione con omissis.%s", ext);
		fillModelBean.setNomeFile(nomeFilePdf);		
		
		return fillModelBean;
	}
	
//	public Map<String, Object> createMapToFillFreeMarkerTemplate(PropostaOrganigrammaBean bean) throws Exception {
//		
//		Map<String, Object> model = new LinkedHashMap<String, Object>();
//		
//		// Ufficio revisione organigramma
//		model.put("ufficioRevisioneOrganigramma", ModelliDocDatasource.getTextModelValue(bean.getCodRapidoUoRevisioneOrganigramma() + " - " + bean.getNomeUoRevisioneOrganigramma()));
//		
//		// Testo proposta
//		model.put("testoProposta", ModelliDocDatasource.getHtmlModelValue(ModelliDocDatasource.fixHTMLCKEDITOR(bean.getTestoProposta(), getSession())));
//		
//		return model;
//	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnire(PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws Exception{
		
		ArrayList<FileDaFirmareBean> listaFileDaUnire = new ArrayList<FileDaFirmareBean>();
		pPropostaOrganigrammaBean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale
		FileDaFirmareBean lFileDispositivoBean  = generaPropostaDaModello(pPropostaOrganigrammaBean);
		listaFileDaUnire.add(lFileDispositivoBean);		
		String idUd = pPropostaOrganigrammaBean.getIdUd();
		if (pPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					aggiungiAllegato(listaFileDaUnire, lAllegatoProtocolloBean);					
				}
			}
		}
		return listaFileDaUnire;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnireOmissis(PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws Exception{
		
		ArrayList<FileDaFirmareBean> listaFileDaUnireOmissis = new ArrayList<FileDaFirmareBean>();
		pPropostaOrganigrammaBean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis
		FileDaFirmareBean lFileDispositivoOmissisBean  = generaPropostaDaModello(pPropostaOrganigrammaBean);
		listaFileDaUnireOmissis.add(lFileDispositivoOmissisBean);		
		String idUd = pPropostaOrganigrammaBean.getIdUd();
		if (pPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					if (lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null || !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // se non è escluso dalla pubblicazione						
						lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
						if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
							aggiungiAllegatoOmissis(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
						} else {
							aggiungiAllegato(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		return listaFileDaUnireOmissis;
	}
	
	public UnioneFileAttoBean unioneFile(PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws Exception {		
		UnioneFileAttoBean lUnioneFileAttoBean = new UnioneFileAttoBean();
		try {
			if(pPropostaOrganigrammaBean.getFlgDatiSensibili() != null && pPropostaOrganigrammaBean.getFlgDatiSensibili()) {
				FileDaFirmareBean lFileUnioneVersIntegraleBean = generaFileUnione(pPropostaOrganigrammaBean, true);
				lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneVersIntegraleBean.getNomeFile());
				lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneVersIntegraleBean.getUri());
				lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneVersIntegraleBean.getInfoFile());
				FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pPropostaOrganigrammaBean, false);
				lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
				lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
				lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
			} else {
				FileDaFirmareBean lFileUnioneBean = generaFileUnione(pPropostaOrganigrammaBean, true);
				lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneBean.getNomeFile());
				lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneBean.getUri());
				lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneBean.getInfoFile());
				lUnioneFileAttoBean.setNomeFile(null);
				lUnioneFileAttoBean.setUri(null);
				lUnioneFileAttoBean.setInfoFile(new MimeTypeFirmaBean());
			}						
		} catch(StoreException e) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}		
		return lUnioneFileAttoBean;
	}
	
	public FileDaFirmareBean generaFileUnione(PropostaOrganigrammaBean pPropostaOrganigrammaBean, boolean flgMostraDatiSensibili) throws Exception{
		
		logger.debug("UNIONE DEI FILE");		
		List<FileDaFirmareBean> lListFileDaUnireBean = flgMostraDatiSensibili ? getListaFileDaUnire(pPropostaOrganigrammaBean) : getListaFileDaUnireOmissis(pPropostaOrganigrammaBean);			
		if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {								
			List<InputStream> lListInputStream = new ArrayList<InputStream>();
			for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
				logger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
				if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					logger.debug("Il file è firmato in CAdES");
					FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
					if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						logger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
						lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						logger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						logger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
						try {
							FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
						} catch (Exception e) {
							String errorMessage = "Errore durante la conversione del file sbustato";
							if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
								errorMessage = "Errore durante la conversione del file sbustato " + lFileSbustatoBean.getNomeFile();
							}
							logger.error(errorMessage + " : " + e.getMessage(), e);
							throw new StoreException(errorMessage);
						}
					} else {
						String errorMessage = "Il file sbustato non è un pdf e non è convertibile.";
						if (lFileSbustatoBean != null && StringUtils.isNotBlank(lFileSbustatoBean.getNomeFile())) {
							errorMessage = "Il file sbustato " + lFileSbustatoBean.getNomeFile() + " non è un pdf e non è convertibile.";
						}
						logger.error(errorMessage);
						throw new StoreException(errorMessage);
					}
				} else if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
					if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().equalsIgnoreCase("PADES")) {
						logger.debug("Il file è firmato in PAdES quindi devo prendere la versione precedente la firma");
						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
					} else {
						logger.debug("Il file è un pdf, quindi lo aggiungo");
						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
					}
				} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
					logger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
					try {
						FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
						lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));	
					} catch (Exception e) {
						String errorMessage = "Errore durante la conversione del file";
						if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
							errorMessage = "Errore durante la conversione del file " + lFileDaUnireBean.getNomeFile();
						}
						logger.error(errorMessage + " : " + e.getMessage(), e);
						throw new StoreException(errorMessage);
					}
				} else {
					String errorMessage = "Il file non è un pdf e non è convertibile.";
					if (lFileDaUnireBean != null && StringUtils.isNotBlank(lFileDaUnireBean.getNomeFile())) {
						errorMessage = "Il file " + lFileDaUnireBean.getNomeFile() + " non è un pdf e non è convertibile.";
					}
					logger.error(errorMessage);
					throw new StoreException(errorMessage);
				}					
			}			
			File lFileUnione = unioneFilePdf(lListInputStream);
			String nomeFileUnione = null;
			if(flgMostraDatiSensibili) {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : "UnioneFile.pdf";
			} else {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnioneOmissis")) ? getExtraparams().get("nomeFileUnioneOmissis") : "UnioneFileConOmissis.pdf";
			}			 
			String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
			FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
			lFileUnioneBean.setUri(uriFileUnione);
			lFileUnioneBean.setNomeFile(nomeFileUnione);		
			lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));
			return lFileUnioneBean;									
		} else {
			String errorMessage = "E' obbligatorio inserire almeno un file per procedere";
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
	}
	
	public File unioneFilePdf(List<InputStream> lListInputStream) throws Exception {
		Document document = new Document();
		// Istanzio una copia nell'output
		File lFile = File.createTempFile("pdf", ".pdf");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(lFile));
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (InputStream lInputStream : lListInputStream) {
			// Leggo il file
			reader = new PdfReader(lInputStream);
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
		}
		// Chiudo il documento
		document.close();
		return lFile;
	}
	
	public TaskFileDaFirmareBean getFileDaFirmare(PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pPropostaOrganigrammaBean.getUriFilePrimario())){
			aggiungiPrimario(listaFileDaFirmare, pPropostaOrganigrammaBean);
		}
		if (StringUtils.isNotBlank(pPropostaOrganigrammaBean.getUriFilePrimarioOmissis())){
			aggiungiPrimarioOmissis(listaFileDaFirmare, pPropostaOrganigrammaBean);
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pPropostaOrganigrammaBean.getIdUd() != null ? String.valueOf(pPropostaOrganigrammaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())){
						aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean);
					} 
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean);
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmare(PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pPropostaOrganigrammaBean.getIdUd() != null ? String.valueOf(pPropostaOrganigrammaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())){
						aggiungiAllegato(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
					} 
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						aggiungiAllegatoOmissis(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileAllegatiDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public CompilaModelloPropostaOrganigrammaBean compilazioneAutomaticaModelloPdf(CompilaModelloPropostaOrganigrammaBean modelloBean) throws Exception {

		String templateValues = getDatiXGenDaModello(modelloBean.getDettaglioBean(), modelloBean.getNomeModello());
		
		if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
			FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(modelloBean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
			modelloBean.setUri(lFileDaFirmareBean.getUri());
			modelloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());			
		} else {
			File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(modelloBean.getProcessId(), modelloBean.getUriModello(), modelloBean.getTipoModello(), templateValues, getSession());
			String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
			modelloBean.setUri(storageUri);
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(modelloBean.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
			modelloBean.setInfoFile(infoFile);	
		}		
		
		return modelloBean;
	}
	
	private void aggiungiPrimario(ArrayList<FileDaFirmareBean> listaFile, PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile("primario" + pPropostaOrganigrammaBean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(pPropostaOrganigrammaBean.getInfoFilePrimario());
		lFileDaFirmareBean.setNomeFile(pPropostaOrganigrammaBean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(pPropostaOrganigrammaBean.getUriFilePrimario());
		lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBean);		
	}

	private void aggiungiPrimarioOmissis(ArrayList<FileDaFirmareBean> listaFile, PropostaOrganigrammaBean pPropostaOrganigrammaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
		lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pPropostaOrganigrammaBean.getUriFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setInfoFile(pPropostaOrganigrammaBean.getInfoFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setNomeFile(pPropostaOrganigrammaBean.getNomeFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setUri(pPropostaOrganigrammaBean.getUriFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setIsFilePrincipaleAtto(true);
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBeanOmissis);			
	}
	
	private void aggiungiAllegato(ArrayList<FileDaFirmareBean> listaFile, AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
			FileDaFirmareBean lFileAllegatoBean = new FileDaFirmareBean();
			lFileAllegatoBean.setIdFile("allegato" + lAllegatoProtocolloBean.getUriFileAllegato());
			lFileAllegatoBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
			lFileAllegatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
			lFileAllegatoBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
			lFileAllegatoBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirma());
			if(listaFile == null) {
				listaFile = new ArrayList<FileDaFirmareBean>();
			}
			listaFile.add(lFileAllegatoBean);		
		}
	}
	
	private void aggiungiAllegatoOmissis(ArrayList<FileDaFirmareBean> listaFile, AllegatoProtocolloBean lAllegatoProtocolloBean) throws Exception {
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
			FileDaFirmareBean lFileAllegatoOmissisBean = new FileDaFirmareBean();
			lFileAllegatoOmissisBean.setIdFile("allegatoOmissis" + lAllegatoProtocolloBean.getUriFileOmissis());
			lFileAllegatoOmissisBean.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
			lFileAllegatoOmissisBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
			lFileAllegatoOmissisBean.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
			lFileAllegatoOmissisBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis());
			if(listaFile == null) {
				listaFile = new ArrayList<FileDaFirmareBean>();
			}
			listaFile.add(lFileAllegatoOmissisBean);		
		}
	}
	
	public PropostaOrganigrammaBean aggiornaFileFirmati(TaskPropostaOrganigrammaFileFirmatiBean pTaskPropostaOrganigrammaFileFirmatiBean) throws Exception {
		PropostaOrganigrammaBean lPropostaOrganigrammaBean = pTaskPropostaOrganigrammaFileFirmatiBean.getProtocolloOriginale();
		if (pTaskPropostaOrganigrammaFileFirmatiBean.getFileFirmati() != null && pTaskPropostaOrganigrammaFileFirmatiBean.getFileFirmati().getFiles() != null) {
			boolean firmaNonValida = false;
			for (FileDaFirmareBean lFileDaFirmareBean : pTaskPropostaOrganigrammaFileFirmatiBean.getFileFirmati().getFiles()) {
				String idFile = lFileDaFirmareBean.getIdFile();
				if (lFileDaFirmareBean.getInfoFile().isFirmato() && !lFileDaFirmareBean.getInfoFile().isFirmaValida()) {
					if (idFile.startsWith("primarioOmissis")) {
						logger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("primario")) {
						logger.error("La firma del file primario " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegatoOmissis")) {
						logger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " (vers. con omissis) risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					} else if (idFile.startsWith("allegato")) {
						logger.error("La firma del file allegato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					}
					firmaNonValida = true;
				}
				if (idFile.startsWith("primarioOmissis")) {
					aggiornaPrimarioOmissisFirmato(lPropostaOrganigrammaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("primario")) {
					aggiornaPrimarioFirmato(lPropostaOrganigrammaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegatoOmissis")) {
					aggiornaAllegatoOmissisFirmato(lPropostaOrganigrammaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegato")) {
					aggiornaAllegatoFirmato(lPropostaOrganigrammaBean, lFileDaFirmareBean);
				} 
			}
			if (firmaNonValida) {
				throw new StoreException("La firma di uno o più file risulta essere non valida");
			}
		}
		return lPropostaOrganigrammaBean;
	}
	
	private void aggiornaPrimarioFirmato(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(lPropostaOrganigrammaBean.getIsChangedFilePrimario() != null && lPropostaOrganigrammaBean.getIsChangedFilePrimario()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromPropostaOrganigrammaBean(lProtocollazioneBean, lPropostaOrganigrammaBean);			
			getProtocolloDataSource(lPropostaOrganigrammaBean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		aggiornaPrimario(lPropostaOrganigrammaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(lPropostaOrganigrammaBean.getIsChangedFilePrimarioOmissis() != null && lPropostaOrganigrammaBean.getIsChangedFilePrimarioOmissis()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromPropostaOrganigrammaBean(lProtocollazioneBean, lPropostaOrganigrammaBean);			
			getProtocolloDataSource(lPropostaOrganigrammaBean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		aggiornaPrimarioOmissis(lPropostaOrganigrammaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lPropostaOrganigrammaBean);
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lPropostaOrganigrammaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lPropostaOrganigrammaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lPropostaOrganigrammaBean);
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lPropostaOrganigrammaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lPropostaOrganigrammaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaPrimario(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lPropostaOrganigrammaBean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lPropostaOrganigrammaBean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lPropostaOrganigrammaBean.setRemoteUriFilePrimario(false);
		String precImpronta = lPropostaOrganigrammaBean.getInfoFilePrimario() != null ? lPropostaOrganigrammaBean.getInfoFilePrimario().getImpronta() : null;
		lPropostaOrganigrammaBean.setInfoFilePrimario(lFileDaFirmareBean.getInfoFile());
		if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lPropostaOrganigrammaBean.setIsChangedFilePrimario(true);
		}
	}
	
	private void aggiornaPrimarioOmissis(PropostaOrganigrammaBean lPropostaOrganigrammaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lPropostaOrganigrammaBean.setUriFilePrimarioOmissis(lFileDaFirmareBean.getUri());
		lPropostaOrganigrammaBean.setNomeFilePrimarioOmissis(lFileDaFirmareBean.getNomeFile());
		lPropostaOrganigrammaBean.setRemoteUriFilePrimarioOmissis(false);
		String precImprontaOmissis = lPropostaOrganigrammaBean.getInfoFilePrimarioOmissis() != null ? lPropostaOrganigrammaBean.getInfoFilePrimarioOmissis().getImpronta() : null;
		lPropostaOrganigrammaBean.setInfoFilePrimarioOmissis(lFileDaFirmareBean.getInfoFile());
		if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lPropostaOrganigrammaBean.setIsChangedFilePrimarioOmissis(true);
		}
	}

	private void aggiornaAllegato(PropostaOrganigrammaBean lPropostaOrganigrammaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		if(lPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {				
					lAllegatoProtocolloBean.setUriFileAllegato(lFileDaFirmareBean.getUri());
					lAllegatoProtocolloBean.setNomeFileAllegato(lFileDaFirmareBean.getNomeFile());
					lAllegatoProtocolloBean.setRemoteUri(false);
					String precImpronta = lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null;
					lAllegatoProtocolloBean.setInfoFile(lFileDaFirmareBean.getInfoFile());
					if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lAllegatoProtocolloBean.setIsChanged(true);
					}
					break;
				}
			}
		}
	}
	
	private void aggiornaAllegatoOmissis(PropostaOrganigrammaBean lPropostaOrganigrammaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegatoOmissis".length());
		if(lPropostaOrganigrammaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lPropostaOrganigrammaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginale)){
					lAllegatoProtocolloBean.setUriFileOmissis(lFileDaFirmareBean.getUri());
					lAllegatoProtocolloBean.setNomeFileOmissis(lFileDaFirmareBean.getNomeFile());
					lAllegatoProtocolloBean.setRemoteUriOmissis(false);
					String precImprontaOmissis = lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null;
					lAllegatoProtocolloBean.setInfoFileOmissis(lFileDaFirmareBean.getInfoFile());
					if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
						lAllegatoProtocolloBean.setIsChangedOmissis(true);
					}
					break;
				}
			}
		}
	}
	
	private FileDaFirmareBean convertiFile(FileDaFirmareBean lFileDaConvertireBean) throws Exception{
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(lFileDaConvertireBean.getUri());
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uriPdf = StorageImplementation.getStorage().storeStream(lInfoFileUtility.converti(lFile.toURI().toString(), nomeFile));
		lFileDaConvertireBean.setNomeFile(nomeFilePdf);
		lFileDaConvertireBean.setUri(uriPdf);
		lFileDaConvertireBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaConvertireBean;			
	}
	
	private FileDaFirmareBean sbustaFile(FileDaFirmareBean lFileDaSbustareBean) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(lFileDaSbustareBean.getUri());				
		String nomeFile = lFileDaSbustareBean.getInfoFile().getCorrectFileName() != null ? lFileDaSbustareBean.getInfoFile().getCorrectFileName() : "";		
		String nomeFileSbustato = (nomeFile != null && nomeFile.toLowerCase().endsWith(".p7m")) ? nomeFile.substring(0, nomeFile.length() - 4) : nomeFile;		
		String uriSbustato = StorageImplementation.getStorage().storeStream(lInfoFileUtility.sbusta(lFile.toURI().toString(), nomeFile));		
		lFileDaSbustareBean.setNomeFile(nomeFileSbustato);
		lFileDaSbustareBean.setUri(uriSbustato);
		lFileDaSbustareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaSbustareBean;				
	}
	
	@Override
	public PaginatorBean<PropostaOrganigrammaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(PropostaOrganigrammaBean bean) throws Exception {
		return null;
	}
	
	@Override
	public PropostaOrganigrammaBean remove(PropostaOrganigrammaBean bean) throws Exception {
		return null;
	};
	
	private BufferedImage joinBufferedImage(float aspectRatio, BufferedImage... imgs) throws IOException {

		// do some calculate first
		int totalWidth =  Math.round(100 * aspectRatio);
		int wid = 0;
		int height =  0;
		for (BufferedImage bufferedImage : imgs) {
			wid += bufferedImage.getWidth();
			height = Math.max(height, bufferedImage.getHeight());
		}
		
		BufferedImage blankImg = ImageIO.read(new File(getRequest().getServletContext().getRealPath("/images/pratiche/icone/blank.png")));
		Image tmp = blankImg.getScaledInstance((totalWidth - wid) / 2, height, Image.SCALE_SMOOTH);
	    BufferedImage scaledBalnkImage = new BufferedImage((totalWidth - wid) / 2, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = scaledBalnkImage.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	     
		BufferedImage newImage = new BufferedImage(totalWidth, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.fillRect(0, 0, totalWidth, height);
		// draw image
		g2.setColor(oldColor);
		int x = 0;
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		for (BufferedImage bufferedImage : imgs) {
			g2.drawImage(bufferedImage, null, x, 0);
			x += bufferedImage.getWidth();
		}
		g2.drawImage(scaledBalnkImage, null, x, 0);
		x += scaledBalnkImage.getWidth();
		g2.dispose();
		return newImage;
	}
	
	private AllegatoProtocolloBean getFileModello(ModelliDocBean bean) {
		
		if(bean.getListaModelli() != null && bean.getListaModelli().size() > 0) {			
			return bean.getListaModelli().get(0);
		}		
		
		return null;
	}	
	
	private String estraiTestoOmissisDaHtml(String html) {
		if (StringUtils.isNotBlank(html)) {
			html = FreeMarkerModelliUtil.replaceOmissisInHtml(html);
			return Jsoup.parse(html).text().replaceAll("\\<.*?>","");
		} else {
			return html;
		}
	}	
	
//	private float getLogoAspectRatio() throws Exception{             
//        TextDocument td = TextDocument.loadDocument(new File("multilingual.odt"));
//        OdfContentDom conDom=td.getContentDom();
//        Node n1=conDom.getFirstChild();
//        return parseXMl(n1);
//	}
	
	// private void parseXMl(Node n,OdfPackage pack) throws Exception{
//	private float parseXMl(Node n) throws Exception{
//       NodeList nl = n.getChildNodes();
//       if(nl==null || nl.getLength()==0){//leaf element
//    	   NamedNodeMap  map=n.getAttributes();
//    	   if("draw:image".equals(n.getNodeName())){
//    		   Node frameNode = n.getParentNode();
//    		   if (frameNode instanceof OdfDrawFrame) {
//    			   NamedNodeMap attributi = frameNode.getAttributes();
//    			   for (int i = 0; i < attributi.getLength(); i++){
//    				   if ("jooscript.image(imgLogo)".equals(attributi.item(i).getNodeValue())){
//    					   String altezza = ;
//    					   String larghezza = "";
//    					   if (StringUtils.isNotBlank(altezza) && altezza.lengh)
//    					   
//    					   String s = "ciao";    				   }
//    			   }
//    			   String s = "";
//    			   //if ("jooscript.image(imgLogo)".equalsIgnoreCase(frameNode.ge))
//    		   }
//    			   
//    		   String s = "";
//    		   return 0;
//    	   }
//    	   return 0;
//       }
//       for (int i=0; i < nl.getLength(); i++) {
//    	   Node   an = nl.item(i);
//    	   parseXMl(an);
//       }
//       return 0;
//
//	}
}