/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.albopretorio.bean.AlboPretorioAttachBean;
import it.eng.albopretorio.bean.FTPUploadFileBean;
import it.eng.albopretorio.bean.ProxyBean;
import it.eng.albopretorio.protocollo.CaricaDocumento;
import it.eng.albopretorio.protocollo.ElaboraResponseWS;
import it.eng.albopretorio.protocollo.FTPUploadFile;
import it.eng.albopretorio.protocollo.SetSystemProxy;
import it.eng.albopretorio.ws.DocumentoType;
import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.AttributiDinamiciDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.OperazioneMassivaAttiBean;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaScrivaniaBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaModelloNuovaPropostaAtto2Bean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteAdottanteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteDiConcertoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EstensoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.FirmatariModelloDispositivoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2Bean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.PeriodoPubblicazioneBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabileDiProcedimentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabilePEGBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.TaskNuovaPropostaAtto2FileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.client.DmpkCoreFindud;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.CIGCUPBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FirmatariModelloDispositivoOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.ModelloDispositivoXmlOutBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.RespDiConcertoBean;
import it.eng.document.function.bean.RespSpesaBean;
import it.eng.document.function.bean.ScrivaniaEstensoreBean;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.ValueBean;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.FileUtil;
import it.eng.utility.MessageUtil;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;
import net.sf.jooreports.templates.image.RenderedImageSource;

@Datasource(id = "NuovaPropostaAtto2DataSource")
public class NuovaPropostaAtto2DataSource extends AbstractDataSource<NuovaPropostaAtto2Bean, NuovaPropostaAtto2Bean> {

	private static final Logger logger = Logger.getLogger(NuovaPropostaAtto2DataSource.class);
	
	private static String _FLG_SPESA_SI = "SI";
	private static String _FLG_SPESA_SI_SENZA_VLD_RIL_IMP = "SI, senza validazione/rilascio impegni";
	private static String _FLG_SPESA_NO = "NO";
	
	public ProtocolloDataSource getProtocolloDataSource(final NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
				return false;
			}		
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pNuovaPropostaAtto2Bean != null) {
					salvaAttributiCustomProposta(pNuovaPropostaAtto2Bean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2DataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lProtocolloDataSource.setMessages(getMessages()); 
		
		return lProtocolloDataSource;
	}
	
	public DatiContabiliSIBDataSource getDatiContabiliSIBDataSource() {	
		DatiContabiliSIBDataSource lDatiContabiliSIBDataSource = new DatiContabiliSIBDataSource();
		lDatiContabiliSIBDataSource.setSession(getSession());
		lDatiContabiliSIBDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lDatiContabiliSIBDataSource.setMessages(getMessages()); 		
		return lDatiContabiliSIBDataSource;
	}	
	
	@Override
	public Map<String, String> getExtraparams() {		
		
		Map<String, String> extraparams = super.getExtraparams();
		extraparams.put("isPropostaAtto", "true");
		return extraparams;		
	}
	
	public boolean skipDatiContabiliAMC(NuovaPropostaAtto2Bean bean) {
		
		String idUdSkipDatiContabiliAMC = ParametriDBUtil.getParametroDB(getSession(), "ID_UD_SKIP_DATI_CONTAB_AMC");
		return idUdSkipDatiContabiliAMC != null && bean.getIdUd() != null && bean.getIdUd().equals(idUdSkipDatiContabiliAMC);
	}
	
	public boolean isAttivaRequestMovimentiDaAMC(NuovaPropostaAtto2Bean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {			
			boolean isAttivaRequestMovimentiDaAMC = getExtraparams().get("flgAttivaRequestMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaRequestMovimentiDaAMC"));
			return isAttivaRequestMovimentiDaAMC;
		} else {
			/*  
			 * dopo la contabilità non devono più essere recuperati gli impegni da SIB, e quelli salvati in DB non devono essere più sovrascritti. 
			 * bisogna dare un errore bloccante quando il recupero dei dati contabili di SIB non va a buon fine
			 */
			// se l'atto risulta già firmato la situazione deve rimanere congelata a quella salvata in DB, perciò devo inibire il recupero dei dati da SIB
			boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();
			return (!isAttoFirmato && !skipDatiContabiliAMC(bean));
		}	
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC(NuovaPropostaAtto2Bean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {			
			boolean isAttivaSalvataggioMovimentiDaAMC = getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC"));
			return isAttivaSalvataggioMovimentiDaAMC;
		} else {
			return !skipDatiContabiliAMC(bean);
		}
	}
	
	@Override
	public NuovaPropostaAtto2Bean get(NuovaPropostaAtto2Bean bean) throws Exception {	
		
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
		
		// Avvio revoca atto
		bean.setIdTipoProcRevocaAtto(lDocumentoXmlOutBean.getIdTipoProcRevocaAtto());
		bean.setNomeTipoProcRevocaAtto(lDocumentoXmlOutBean.getNomeTipoProcRevocaAtto());
		bean.setIdDefFlussoWFRevocaAtto(lDocumentoXmlOutBean.getIdDefFlussoWFRevocaAtto());
		bean.setIdTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getIdTipoDocPropostaRevocaAtto());
		bean.setNomeTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getNomeTipoDocPropostaRevocaAtto());
		bean.setSiglaPropostaRevocaAtto(lDocumentoXmlOutBean.getSiglaPropostaRevocaAtto());		
		
		/* Dati scheda - Registrazione */
		
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
		
		bean.setFlgDettRevocaAtto(lDocumentoXmlOutBean.getFlgDettRevocaAtto() == Flag.SETTED);
		bean.setIdPropostaAMC(lDocumentoXmlOutBean.getCodPropostaSistContabile());
		
		/* Dati scheda - Dati di pubblicazione */
		
		// se i dati di pubblicazione non sono salvati in DB mi tengo quelli che già mi arrivano nella chiamata
		if(lDocumentoXmlOutBean.getDataInizioPubbl() != null) {
			bean.setDataInizioPubblicazione(lDocumentoXmlOutBean.getDataInizioPubbl());
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getGiorniDurataPubbl())) {
			bean.setGiorniPubblicazione(lDocumentoXmlOutBean.getGiorniDurataPubbl());
		}
		
		/* Dati scheda - Ruoli */
		
		// Ufficio proponente
		bean.setUfficioProponente(lDocumentoXmlOutBean.getIdUOProponente());
		bean.setCodUfficioProponente(lDocumentoXmlOutBean.getCodUOProponente());
		bean.setDesUfficioProponente(lDocumentoXmlOutBean.getDesUOProponente());
		bean.setDesDirezioneProponente(lDocumentoXmlOutBean.getDesDirProponente());
		List<SelezionaUOBean> listaUfficioProponente = new ArrayList<SelezionaUOBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOProponente())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOProponente());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOProponente());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOProponente());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			listaUfficioProponente.add(lSelezionaUOBean);
		}
		bean.setListaUfficioProponente(listaUfficioProponente);
		
		// Dirigente adottante
		List<DirigenteAdottanteBean> listaAdottante = new ArrayList<DirigenteAdottanteBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaAdottante())) {
			DirigenteAdottanteBean lDirigenteAdottanteBean = new DirigenteAdottanteBean();
			lDirigenteAdottanteBean.setDirigenteAdottante(lDocumentoXmlOutBean.getIdScrivaniaAdottante());
			lDirigenteAdottanteBean.setDirigenteAdottanteFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaAdottante());						
			lDirigenteAdottanteBean.setDesDirigenteAdottante(lDocumentoXmlOutBean.getDesScrivaniaAdottante());
			lDirigenteAdottanteBean.setCodUoDirigenteAdottante(lDocumentoXmlOutBean.getCodUOScrivaniaAdottante());
			lDirigenteAdottanteBean.setFlgAdottanteAncheRdP(lDocumentoXmlOutBean.getFlgAdottanteAncheRespProc() == Flag.SETTED);	
			lDirigenteAdottanteBean.setFlgAdottanteAncheRUP(lDocumentoXmlOutBean.getFlgAdottanteAncheRUP() == Flag.SETTED);	
			listaAdottante.add(lDirigenteAdottanteBean);
		} else {
			listaAdottante.add(new DirigenteAdottanteBean());
		}
		bean.setListaAdottante(listaAdottante);		
		
		// Responsabile di Procedimento
		List<ResponsabileDiProcedimentoBean> listaRdP = new ArrayList<ResponsabileDiProcedimentoBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespProc())) {
			ResponsabileDiProcedimentoBean lResponsabileDiProcedimentoBean = new ResponsabileDiProcedimentoBean();
			lResponsabileDiProcedimentoBean.setIdScrivania(lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lResponsabileDiProcedimentoBean.setCodUoScrivania(lDocumentoXmlOutBean.getCodUOScrivaniaRespProc());
			lResponsabileDiProcedimentoBean.setDescrizione(lDocumentoXmlOutBean.getDesScrivaniaRespProc());
			lResponsabileDiProcedimentoBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesScrivaniaRespProc());				
			lResponsabileDiProcedimentoBean.setOrganigramma("SV" + lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lResponsabileDiProcedimentoBean.setOrganigrammaFromLoadDett("SV" + lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lResponsabileDiProcedimentoBean.setFlgRdPAncheRUP(lDocumentoXmlOutBean.getFlgRespProcAncheRUP() == Flag.SETTED);
			listaRdP.add(lResponsabileDiProcedimentoBean);
		} else {
			listaRdP.add(new ResponsabileDiProcedimentoBean());
		}
		bean.setListaRdP(listaRdP);		
				
		// Responsabile Unico Provvedimento
		List<SelezionaScrivaniaBean> listaRUP = new ArrayList<SelezionaScrivaniaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRUP())) {
			SelezionaScrivaniaBean lSelezionaScrivaniaBean = new SelezionaScrivaniaBean();
			lSelezionaScrivaniaBean.setIdScrivania(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lSelezionaScrivaniaBean.setCodUoScrivania(lDocumentoXmlOutBean.getCodUOScrivaniaRUP());
			lSelezionaScrivaniaBean.setDescrizione(lDocumentoXmlOutBean.getDesScrivaniaRUP());
			lSelezionaScrivaniaBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesScrivaniaRUP());				
			lSelezionaScrivaniaBean.setOrganigramma("SV" + lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lSelezionaScrivaniaBean.setOrganigrammaFromLoadDett("SV" + lDocumentoXmlOutBean.getIdScrivaniaRUP());
			listaRUP.add(lSelezionaScrivaniaBean);
		} else {
			listaRUP.add(new SelezionaScrivaniaBean());
		}
		bean.setListaRUP(listaRUP);
		
		//Richiedi visto Direttore
		bean.setFlgRichiediVistoDirettore(lDocumentoXmlOutBean.getFlgRichiediVistoDirettore() == Flag.SETTED);
		
		/* Dati scheda - Proposta di concerto con */
		
		List<DirigenteDiConcertoBean> listaDirigentiConcerto = new ArrayList<DirigenteDiConcertoBean>();
		if(lDocumentoXmlOutBean.getFlgDetDiConcerto() == Flag.SETTED) {
			if (lDocumentoXmlOutBean.getResponsabiliDiConcerto() != null && lDocumentoXmlOutBean.getResponsabiliDiConcerto().size() > 0) {
				for (RespDiConcertoBean lRespDiConcertoBean : lDocumentoXmlOutBean.getResponsabiliDiConcerto()) {
					DirigenteDiConcertoBean lDirigenteDiConcertoBean = new DirigenteDiConcertoBean();
					lDirigenteDiConcertoBean.setDirigenteConcerto(lRespDiConcertoBean.getIdSV());
					lDirigenteDiConcertoBean.setDirigenteConcertoFromLoadDett(lRespDiConcertoBean.getIdSV());
					lDirigenteDiConcertoBean.setCodUoDirigenteConcerto(lRespDiConcertoBean.getCodUO());
					lDirigenteDiConcertoBean.setDesDirigenteConcerto(lRespDiConcertoBean.getDescrizione());
					lDirigenteDiConcertoBean.setFlgDirigenteConcertoFirmatario(lRespDiConcertoBean.getFlgFirmatario());
					listaDirigentiConcerto.add(lDirigenteDiConcertoBean);
				}
			}
		}
		bean.setListaDirigentiConcerto(listaDirigentiConcerto);
		
		/* Dati scheda - Estensori */
		
		List<EstensoreBean> listaEstensori = new ArrayList<EstensoreBean>();
		if (lDocumentoXmlOutBean.getEstensori() != null && lDocumentoXmlOutBean.getEstensori().size() > 0) {
			for (ScrivaniaEstensoreBean lScrivaniaEstensoreBean : lDocumentoXmlOutBean.getEstensori()) {
				EstensoreBean lEstensoreBean = new EstensoreBean();
				lEstensoreBean.setEstensore(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setEstensoreFromLoadDett(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setCodUoEstensore(lScrivaniaEstensoreBean.getCodUO());
				lEstensoreBean.setDesEstensore(lScrivaniaEstensoreBean.getDescrizione());
				listaEstensori.add(lEstensoreBean);
			}
		}
		bean.setListaEstensori(listaEstensori);
		
		/* Dati scheda - CIG */
		List<CIGCUPBean> listaCIG = new ArrayList<CIGCUPBean>();
		if (lDocumentoXmlOutBean.getListaCIG() != null && !lDocumentoXmlOutBean.getListaCIG().isEmpty()) {
			listaCIG = lDocumentoXmlOutBean.getListaCIG();
		}
		bean.setListaCIG(listaCIG);
						
		/* Dati scheda - Oggetto */
		
		bean.setOggetto(lProtocollazioneBean.getOggetto());
		// Se non ho l'oggetto html metto l'oggetto normale
		bean.setOggettoHtml(StringUtils.isNotBlank(lDocumentoXmlOutBean.getOggettoHtml()) ? lDocumentoXmlOutBean.getOggettoHtml() : lProtocollazioneBean.getOggetto());
		
		/* Dati scheda - Specificità del provvedimento */

		bean.setFlgDeterminaAContrarreTramiteProceduraGara(lDocumentoXmlOutBean.getFlgDetContrConGara() == Flag.SETTED);
		bean.setFlgDeterminaAggiudicaProceduraGara(lDocumentoXmlOutBean.getFlgDetAggiudicaGara() == Flag.SETTED);
		bean.setFlgDeterminaRimodulazioneSpesaGaraAggiudicata(lDocumentoXmlOutBean.getFlgDetRimodSpesaGaraAggiud() == Flag.SETTED);
		bean.setFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro(lDocumentoXmlOutBean.getFlgDetPersonale() == Flag.SETTED);
				
		if(lDocumentoXmlOutBean.getDocCollegato() != null) {			
			bean.setIdUdAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getIdUd());
			bean.setCategoriaRegAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getTipo() != null ? lDocumentoXmlOutBean.getDocCollegato().getTipo().toString() : null);
			bean.setSiglaAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getRegistro());
			bean.setNumeroAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getNro());
			bean.setAnnoAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getAnno());
		} 
		
		bean.setFlgSpesa(lDocumentoXmlOutBean.getFlgDetConSpesa());
		
		bean.setFlgRichVerificaDiBilancioCorrente(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioCorrente() == Flag.SETTED);		
		bean.setFlgRichVerificaDiBilancioContoCapitale(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioContoCapitale() == Flag.SETTED);				
		bean.setFlgRichVerificaDiContabilita(lDocumentoXmlOutBean.getFlgRichVerificaDiContabilita() == Flag.SETTED);			
			
		bean.setFlgPresaVisioneContabilita(lDocumentoXmlOutBean.getFlgRichPresaVisContabilita() == Flag.SETTED);		
		
		/* Dati scheda - Atti presupposti */
		
//		List<SimpleValueBean> listaAttiPresupposti = new ArrayList<SimpleValueBean>();
//		if (lDocumentoXmlOutBean.getAttiPresupposti() != null && lDocumentoXmlOutBean.getAttiPresupposti().size() > 0) {
//			for (ValueBean lValueBean : lDocumentoXmlOutBean.getAttiPresupposti()) {
//				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
//				lSimpleValueBean.setValue(lValueBean.getValue());
//				listaAttiPresupposti.add(lSimpleValueBean);
//			}
//		}
//		bean.setListaAttiPresupposti(listaAttiPresupposti);
		
		bean.setAttiPresupposti(lDocumentoXmlOutBean.getAttiPresupposti());	
		
		/* Dati scheda - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = new ArrayList<SimpleValueBean>();
		if (lDocumentoXmlOutBean.getRiferimentiNormativi() != null && lDocumentoXmlOutBean.getRiferimentiNormativi().size() > 0) {
			for (ValueBean lValueBean : lDocumentoXmlOutBean.getRiferimentiNormativi()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lValueBean.getValue());
				listaRiferimentiNormativi.add(lSimpleValueBean);
			}
		}
		bean.setListaRiferimentiNormativi(listaRiferimentiNormativi);
		
		/* Dati scheda - Motivazioni */
		
		bean.setMotivazioni(lDocumentoXmlOutBean.getMotivazioniAtto());
	
		/* Dati scheda - Dispositivo */
		
		bean.setDispositivo(lDocumentoXmlOutBean.getDispositivoAtto());
		bean.setLoghiAggiuntiviDispositivo(lDocumentoXmlOutBean.getLoghiDispositivoAtto());
		
		/* Dati scheda - Allegati */
		
		bean.setListaAllegati(lProtocollazioneBean.getListaAllegati() != null ? lProtocollazioneBean.getListaAllegati() : new ArrayList<AllegatoProtocolloBean>());					
		
		/* Dati spesa - Ruoli */

		// Responsabili PEG
		bean.setFlgAdottanteUnicoRespPEG(lDocumentoXmlOutBean.getFlgAdottanteUnicoRespSpesa() == Flag.SETTED);	
		List<ResponsabilePEGBean> listaResponsabiliPEG = new ArrayList<ResponsabilePEGBean>();
		if (lDocumentoXmlOutBean.getResponsabiliSpesa() != null && lDocumentoXmlOutBean.getResponsabiliSpesa().size() > 0) {
			for (RespSpesaBean lRespSpesaBean : lDocumentoXmlOutBean.getResponsabiliSpesa()) {
				ResponsabilePEGBean lResponsabilePEGBean = new ResponsabilePEGBean();
				lResponsabilePEGBean.setResponsabilePEG(lRespSpesaBean.getIdSV());
				lResponsabilePEGBean.setResponsabilePEGFromLoadDett(lRespSpesaBean.getIdSV());
				lResponsabilePEGBean.setCodUoResponsabilePEG(lRespSpesaBean.getCodUO());
				lResponsabilePEGBean.setDesResponsabilePEG(lRespSpesaBean.getDescrizione());			
				listaResponsabiliPEG.add(lResponsabilePEGBean);
			}
		}
		bean.setListaResponsabiliPEG(listaResponsabiliPEG);
		
		// Ufficio definizione spesa
		bean.setUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getIdUOCompSpesa());
		bean.setCodUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getCodUOCompSpesa());
		bean.setDesUfficioDefinizioneSpesa(lDocumentoXmlOutBean.getDesUOCompSpesa());
		List<SelezionaUOBean> listaUfficioDefinizioneSpesa = new ArrayList<SelezionaUOBean>();		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOCompSpesa())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOCompSpesa());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOCompSpesa());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOCompSpesa());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOCompSpesa());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOCompSpesa());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOCompSpesa());
			listaUfficioDefinizioneSpesa.add(lSelezionaUOBean);
		}
		bean.setListaUfficioDefinizioneSpesa(listaUfficioDefinizioneSpesa);		
						
		/* Dati spesa - Opzioni */
		
		bean.setFlgSpesaCorrente(lDocumentoXmlOutBean.getFlgDetConSpesaCorrente() == Flag.SETTED);
		bean.setFlgImpegniCorrenteGiaValidati(lDocumentoXmlOutBean.getFlgDetConImpCorrValid() == Flag.SETTED);
		bean.setFlgSpesaContoCapitale(lDocumentoXmlOutBean.getFlgDetConSpesaContoCap() == Flag.SETTED);
		bean.setFlgImpegniContoCapitaleGiaRilasciati(lDocumentoXmlOutBean.getFlgDetConImpCCapRil() == Flag.SETTED);
		bean.setFlgSoloSubImpSubCrono(lDocumentoXmlOutBean.getFlgSoloSubImpSubCrono() == Flag.SETTED);
		bean.setFlgConVerificaContabilita(lDocumentoXmlOutBean.getFlgRichVerificaContabilita() == Flag.SETTED);
		bean.setFlgRichiediParereRevisoriContabili(lDocumentoXmlOutBean.getFlgRichParereRevContabili() == Flag.SETTED);		
		
		/* Dati spesa corrente - Opzioni */
		
		bean.setFlgDisattivaAutoRequestDatiContabiliSIBCorrente(lDocumentoXmlOutBean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() == Flag.SETTED);
		bean.setPrenotazioneSpesaSIBDiCorrente(lDocumentoXmlOutBean.getPrenotazioneSpesaSIBDiCorrente());
		bean.setModalitaInvioDatiSpesaARagioneriaCorrente(lDocumentoXmlOutBean.getModalitaInvioDatiSpesaARagioneriaCorrente());		
		bean.setListaDatiContabiliSIBCorrente(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaDatiContabiliSIBCorrente().get(i)); 
				bean.getListaDatiContabiliSIBCorrente().add(lDatiContabiliBean);
			}
		}
		bean.setListaInvioDatiSpesaCorrente(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente() != null) {
			for(int i = 0; i < lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaInvioDatiSpesaCorrente().get(i)); 
				bean.getListaInvioDatiSpesaCorrente().add(lDatiContabiliBean);
			}
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFileXlsCorrente())) {
			bean.setFileXlsCorrente(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFileXlsCorrente()));
		}
		bean.setNomeFileTracciatoXlsCorrente("Tracciato_SIB.xls");
		bean.setUriFileTracciatoXlsCorrente(ParametriDBUtil.getParametroDB(getSession(), "URI_TRACCIATO_XLS_SIB"));
		bean.setNoteCorrente(lDocumentoXmlOutBean.getNoteCorrente());		
		
		/* Dati spesa in conto capitale - Opzioni */
		
		bean.setFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale(lDocumentoXmlOutBean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() == Flag.SETTED);
		bean.setModalitaInvioDatiSpesaARagioneriaContoCapitale(lDocumentoXmlOutBean.getModalitaInvioDatiSpesaARagioneriaContoCapitale());		
		bean.setListaDatiContabiliSIBContoCapitale(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaDatiContabiliSIBContoCapitale().get(i)); 
				bean.getListaDatiContabiliSIBContoCapitale().add(lDatiContabiliBean);
			}
		}
		bean.setListaInvioDatiSpesaContoCapitale(new ArrayList<DatiContabiliBean>());
		if(lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale() != null) {
			for(int i = 0; i < lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale().size(); i++) {
				DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
				lDatiContabiliBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliBean, lDocumentoXmlOutBean.getListaInvioDatiSpesaContoCapitale().get(i)); 
				bean.getListaInvioDatiSpesaContoCapitale().add(lDatiContabiliBean);
			}
		}
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFileXlsContoCapitale())) {
			bean.setFileXlsContoCapitale(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFileXlsContoCapitale()));
		}
		bean.setNomeFileTracciatoXlsContoCapitale("Tracciato_SIB.xls");
		bean.setUriFileTracciatoXlsContoCapitale(ParametriDBUtil.getParametroDB(getSession(), "URI_TRACCIATO_XLS_SIB"));
		bean.setNoteContoCapitale(lDocumentoXmlOutBean.getNoteContoCapitale());	
			
		/* Dati spesa personale */
		/*
		bean.setListaDatiSpesaAnnualiXDetPersonale(lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() != null ? lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() : new ArrayList<DatiSpesaAnnualiXDetPersonaleXmlBean>());
		bean.setCapitoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getCapitoloDatiSpesaAnnuaXDetPers());
		bean.setArticoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getArticoloDatiSpesaAnnuaXDetPers());
		bean.setNumeroDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getNumeroDatiSpesaAnnuaXDetPers());	
		bean.setImportoDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getImportoDatiSpesaAnnuaXDetPers());
		*/
		
		/*Id tipo documento albo pretorio*/
		bean.setIdTipoDocAlbo(lDocumentoXmlOutBean.getIdTipoDocAlbo());
		
		populateListaVociPEGNoVerifDisp(bean);
		
		if(isAttivaRequestMovimentiDaAMC(bean)) {
			if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente()) {
				populateListaDatiContabiliSIBCorrente(bean);
			}
			if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale()) {
				populateListaDatiContabiliSIBContoCapitale(bean);
			}
		}
		
		// DECOMMENTARE (IN DEBUG) NEL CASO SI VOGLIA CHIAMARE IL SERVIZIO CREAPROPOSTA DI SIB SULLE SEGUENTI PROPOSTE
//		if(isAttivoSIB()) {	
//			String siglaRegProposta = "PDD";
//			Set<String> listaNumeriRegProposta= new HashSet<String>();
//			listaNumeriRegProposta.add();
//			if(bean.getSiglaRegProvvisoria() != null && siglaRegProposta.equalsIgnoreCase(bean.getSiglaRegProvvisoria()) && 
//				bean.getNumeroRegProvvisoria() != null && listaNumeriRegProposta.contains(bean.getNumeroRegProvvisoria())) {
//				getDatiContabiliSIBDataSource().creaProposta(bean);											 		
//				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB						
//			}
//		}
		
		return bean;
	}
	
	public void populateListaVociPEGNoVerifDisp(NuovaPropostaAtto2Bean bean) throws Exception {
		
		if(isAttivoSIB()) {
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
			String idUserLavoro = loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "";
			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			lDmpkLoadComboDmfn_load_comboBean.setCodidconnectiontokenin(loginBean.getToken());
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("VALORI_DIZIONARIO");
			String altriParametri = "ID_USER_LAVORO|*|" + idUserLavoro + "|*|DICTIONARY_ENTRY|*|CAP_PEG_NO_VERIF_DISPONIBILITA";
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);		
			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
					
			List<SimpleKeyValueBean> listaVociPEGNoVerifDisp = new ArrayList<SimpleKeyValueBean>();
			if(!lStoreResultBean.isInError()) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				for (XmlListaSimpleBean lXmlListaSimpleBean : XmlListaUtility.recuperaLista(xmlLista, XmlListaSimpleBean.class)) {
					SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
					lSimpleKeyValueBean.setKey(lXmlListaSimpleBean.getKey());
					lSimpleKeyValueBean.setValue(lXmlListaSimpleBean.getValue());
					listaVociPEGNoVerifDisp.add(lSimpleKeyValueBean);
				}		
			} 
			
			bean.setListaVociPEGNoVerifDisp(listaVociPEGNoVerifDisp);
		}
	}
	
	public NuovaPropostaAtto2Bean getListaDatiContabiliSIBCorrente(NuovaPropostaAtto2Bean bean) throws Exception {
		
		if(isAttivaRequestMovimentiDaAMC(bean)) {			
			populateListaDatiContabiliSIBCorrente(bean);
		}
		return bean;
	}
	
	public void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2Bean bean) throws Exception {
		populateListaDatiContabiliSIBCorrente(bean, true);
	}

	public void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2Bean bean, boolean skipError) throws Exception {
		
		try {			
			List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getDatiContabiliSIBDataSource().getListaDatiContabiliCorrente(bean);
				if(listaDatiContabiliSIB != null) {
					for(DatiContabiliBean lDatiContabiliBean : listaDatiContabiliSIB) {
						listaDatiContabiliSIBCorrente.add(lDatiContabiliBean);					
					}
				}
			}
			bean.setListaDatiContabiliSIBCorrente(listaDatiContabiliSIBCorrente);
			bean.setErrorMessageDatiContabiliSIBCorrente(null);			
		} catch(Exception e) {
			bean.setListaDatiContabiliSIBCorrente(new ArrayList<DatiContabiliBean>());
			bean.setErrorMessageDatiContabiliSIBCorrente("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con SIB</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei dati contabili da SIB", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei dati contabili da SIB");
			}
		}		
	}
	
	public NuovaPropostaAtto2Bean getListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2Bean bean) throws Exception {		
		
		if(isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaDatiContabiliSIBContoCapitale(bean);
		}
		return bean;
	}
	
	public void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2Bean bean) throws Exception {
		populateListaDatiContabiliSIBContoCapitale(bean, true);
	}

	public void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2Bean bean, boolean skipError) throws Exception {
		
		try {	
			List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getDatiContabiliSIBDataSource().getListaDatiContabiliContoCapitale(bean);
				if(listaDatiContabiliSIB != null) {
					for(DatiContabiliBean lDatiContabiliBean : listaDatiContabiliSIB) {
						listaDatiContabiliSIBContoCapitale.add(lDatiContabiliBean);					
					}
				}
			}
			bean.setListaDatiContabiliSIBContoCapitale(listaDatiContabiliSIBContoCapitale);
			bean.setErrorMessageDatiContabiliSIBContoCapitale(null);		
		} catch(Exception e) {
			bean.setListaDatiContabiliSIBContoCapitale(new ArrayList<DatiContabiliBean>());
			bean.setErrorMessageDatiContabiliSIBContoCapitale("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con SIB</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei dati contabili da SIB", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei dati contabili da SIB");
			}
		}		
	}
		
	@Override
	public NuovaPropostaAtto2Bean add(NuovaPropostaAtto2Bean bean) throws Exception {

		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		lProtocollazioneBeanInput.setIdUdNuovoComeCopia(StringUtils.isNotBlank(bean.getIdUdNuovoComeCopia()) ? new BigDecimal(bean.getIdUdNuovoComeCopia()) : null);
		lProtocollazioneBeanInput.setPrefKeyModello(bean.getPrefKeyModello());
		lProtocollazioneBeanInput.setPrefNameModello(bean.getPrefNameModello());
		lProtocollazioneBeanInput.setUseridModello(bean.getUseridModello());
		lProtocollazioneBeanInput.setIdUoModello(bean.getIdUoModello());
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromNuovaPropostaAtto2Bean(lProtocollazioneBeanInput, bean);
		
		// per la numerazione da dare all'avvio dell'atto
		lProtocollazioneBeanInput.setCodCategoriaProtocollo(bean.getCategoriaRegAvvio());				
		lProtocollazioneBeanInput.setSiglaProtocollo(bean.getSiglaRegAvvio());		
		
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
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di SIB, con la versione con omissis di oggettoHtml

		// Chiamo il servizio CreaProposta di SIB
		if(isAttivoSIB()) {
			getDatiContabiliSIBDataSource().creaProposta(bean);	 		
			aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB													
		}
		
		return bean;
	}

	@Override
	public NuovaPropostaAtto2Bean update(NuovaPropostaAtto2Bean bean, NuovaPropostaAtto2Bean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if(isAttivaRequestMovimentiDaAMC(bean) && isAttivaSalvataggioMovimentiDaAMC(bean)) {		
			// in salvataggio se non ho dei movimenti contabili rifaccio la chiamata ad AMC e se va' in errore blocco tutto
			if(isAttivoSIB()) {
				if (bean.getListaDatiContabiliSIBCorrente() == null || bean.getListaDatiContabiliSIBCorrente().isEmpty()) {
					populateListaDatiContabiliSIBCorrente(bean, false);	
				}
				if (bean.getListaDatiContabiliSIBContoCapitale() == null || bean.getListaDatiContabiliSIBContoCapitale().isEmpty()) {
					populateListaDatiContabiliSIBContoCapitale(bean, false);	
				}				
			}
		}
		
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();
		lProtocollazioneBeanInput.setIdUd(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
		try {
			lProtocollazioneBeanInput = getProtocolloDataSource(bean).get(lProtocollazioneBeanInput);
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		lProtocollazioneBeanInput.setIdUdNuovoComeCopia(StringUtils.isNotBlank(bean.getIdUdNuovoComeCopia()) ? new BigDecimal(bean.getIdUdNuovoComeCopia()) : null);
		lProtocollazioneBeanInput.setPrefKeyModello(bean.getPrefKeyModello());
		lProtocollazioneBeanInput.setPrefNameModello(bean.getPrefNameModello());
		lProtocollazioneBeanInput.setUseridModello(bean.getUseridModello());
		lProtocollazioneBeanInput.setIdUoModello(bean.getIdUoModello());
		
		// se l'atto risulta già firmato devo inibire il versionamento del dispositivo (vers. integrale e omissis) per non sovrascrivere il file unione
		boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();				
		
		// Genero il file dispositivo atto (vers. integrale e omissis) da passare in salvataggio
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "VERS_DISPOSITIVO_NUOVA_PROPOSTA_ATTO_2")) {
			try {				
				boolean versionaFileDispositivo = getExtraparams().get("versionaFileDispositivo") != null && "true".equalsIgnoreCase(getExtraparams().get("versionaFileDispositivo"));
				if(versionaFileDispositivo && !isAttoFirmato) {
					bean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale			
	//				FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
					FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean); // ma noi vogliamo salvarlo in pdf
					if(lFileDaFirmareBean != null) {
						aggiornaPrimario(bean, lFileDaFirmareBean);
					}
					boolean hasPrimarioDatiSensibili = getExtraparams().get("hasPrimarioDatiSensibili") != null && "true".equalsIgnoreCase(getExtraparams().get("hasPrimarioDatiSensibili"));		
					if(hasPrimarioDatiSensibili) {
						bean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis
	//					FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
						FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean); // ma noi vogliamo salvarlo in pdf
						if(lFileDaFirmareBeanOmissis != null) {
							aggiornaPrimarioOmissis(bean, lFileDaFirmareBeanOmissis);
						}				
					} else {
						// se il file primario non contiene dati sensibili devo annullare la versione
						bean.setUriFilePrimarioOmissis(null);
						bean.setNomeFilePrimarioOmissis(null);
						bean.setInfoFilePrimarioOmissis(null);
					}
				}
			} catch(Exception e) {
				throw new StoreException("Si è verificato un errore durante la generazione della nuova versione pdf dell'atto con i dati attuali: " + e.getMessage());
			}
		}
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromNuovaPropostaAtto2Bean(lProtocollazioneBeanInput, bean);
		
		// Passo le numerazioni da dare in salvataggio
		if (lProtocollazioneBeanInput.getNumeroNumerazioneSecondaria() == null) {
			List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
			if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto"))) {
				TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();			
				lTipoNumerazioneBean.setSigla(getExtraparams().get("siglaRegistroAtto"));
				lTipoNumerazioneBean.setCategoria("R");		
				listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
			}
			lProtocollazioneBeanInput.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
		}
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).update(lProtocollazioneBeanInput, null);
			if (lProtocollazioneBeanOutput.getFileInErrors() != null && lProtocollazioneBeanOutput.getFileInErrors().size() > 0) {
				for (String error : lProtocollazioneBeanOutput.getFileInErrors().values()) {
					logger.error(error);					
				}
				throw new StoreException("Si è verificato un errore durante il salvataggio: alcuni dei file sono andati in errore");
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		// Recupero i dati di registrazione 
		// se in salvataggio ho passato una nuova numerazione da dare devo recuperarmela
		try {
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
			if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria())) {
				// Numerazione finale
				RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
				bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
				// Numerazione provvisoria
				bean.setSiglaRegProvvisoria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
				bean.setNumeroRegProvvisoria(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
				bean.setDataRegProvvisoria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());			
			} else {
				// Numerazione provvisoria
				RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();			
				bean.setSiglaRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setDesUserRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}	
		
		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare ai servizi di SIB, con la versione con omissis di oggettoHtml
		
		// Chiamo il servizio CreaProposta o AggiornaAtto di SIB
		if(isAttivoSIB()) {	
			if (StringUtils.isBlank(bean.getIdPropostaAMC())) {			
				getDatiContabiliSIBDataSource().creaProposta(bean);											 		
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB		
			} else {			
				getDatiContabiliSIBDataSource().aggiornaAtto(bean);			
				if(StringUtils.isNotBlank(bean.getEventoSIB())) {
					if(bean.getEsitoEventoSIB() != null && bean.getEsitoEventoSIB().equals("KO")) {
						addMessage(bean.getErrMsgEventoSIB(), "", MessageType.WARNING);
					}
				}			
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB			
			}
		}
		
		return bean;
	}
	
	private void aggiornaDatiSIB(NuovaPropostaAtto2Bean bean) throws Exception {
		
		if(isAttivoSIB()/* && bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())*/) {				
			try {
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
				
				if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
				}
				
				if(StringUtils.isNotBlank(bean.getEventoSIB()) && !"aggiornamento".equals(bean.getEventoSIB())) { 
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", bean.getEventoSIB() != null ? bean.getEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_SIB_Doc", bean.getEventoSIB() != null ? bean.getEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_SIB_Doc", bean.getEsitoEventoSIB() != null ? bean.getEsitoEventoSIB() : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_SIB_Doc", bean.getDataEventoSIB() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoSIB()) : "");
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_SIB_Doc", bean.getErrMsgEventoSIB() != null ? bean.getErrMsgEventoSIB() : "");
				}
				
				if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
				
					AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
					DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
					input.setCodidconnectiontokenin(loginBean.getToken());
					input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		
					input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
					input.setFlgtipotargetin("D");
		
					CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
					lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);			
					
					XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
					input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));
			
					DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
					StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);
			
					if (lUpddocudOutput.isInError()) {
						throw new StoreException(lUpddocudOutput);
					}		
				}
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
	}
	
	private void populateProtocollazioneBeanFromNuovaPropostaAtto2Bean(ProtocollazioneBean pProtocollazioneBean, NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) {
		
		if(pProtocollazioneBean != null && pNuovaPropostaAtto2Bean != null) {
			
			/* Hidden */
			
			pProtocollazioneBean.setIdUd(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdUd()) ? new BigDecimal(pNuovaPropostaAtto2Bean.getIdUd()) : null);
			pProtocollazioneBean.setFlgTipoProv("I");
			pProtocollazioneBean.setTipoDocumento(pNuovaPropostaAtto2Bean.getTipoDocumento());
			pProtocollazioneBean.setNomeTipoDocumento(pNuovaPropostaAtto2Bean.getNomeTipoDocumento());
			pProtocollazioneBean.setRowidDoc(pNuovaPropostaAtto2Bean.getRowidDoc());
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdDocPrimario()) ? new BigDecimal(pNuovaPropostaAtto2Bean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setNomeFilePrimario(pNuovaPropostaAtto2Bean.getNomeFilePrimario());
			pProtocollazioneBean.setUriFilePrimario(pNuovaPropostaAtto2Bean.getUriFilePrimario());
			pProtocollazioneBean.setRemoteUriFilePrimario(pNuovaPropostaAtto2Bean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setInfoFile(pNuovaPropostaAtto2Bean.getInfoFilePrimario());
			pProtocollazioneBean.setIsDocPrimarioChanged(pNuovaPropostaAtto2Bean.getIsChangedFilePrimario());
			pProtocollazioneBean.setFlgDatiSensibili(pNuovaPropostaAtto2Bean.getFlgDatiSensibili());
			
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pNuovaPropostaAtto2Bean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pNuovaPropostaAtto2Bean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pNuovaPropostaAtto2Bean.getUriFilePrimarioOmissis());
			lFilePrimarioOmissis.setRemoteUri(pNuovaPropostaAtto2Bean.getRemoteUriFilePrimarioOmissis());		
			lFilePrimarioOmissis.setInfoFile(pNuovaPropostaAtto2Bean.getInfoFilePrimarioOmissis());
			lFilePrimarioOmissis.setIsChanged(pNuovaPropostaAtto2Bean.getIsChangedFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
			
			/* Dati scheda - Registrazione */
			
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNumeroRegistrazione())) {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2Bean.getSiglaRegistrazione());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNumeroRegistrazione()) ? new BigDecimal(pNuovaPropostaAtto2Bean.getNumeroRegistrazione()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2Bean.getDataRegistrazione());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2Bean.getDesUserRegistrazione());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2Bean.getDesUORegistrazione());		
				pProtocollazioneBean.setSiglaNumerazioneSecondaria(pNuovaPropostaAtto2Bean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNumeroNumerazioneSecondaria(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2Bean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataRegistrazioneNumerazioneSecondaria(pNuovaPropostaAtto2Bean.getDataRegProvvisoria());			
			} else {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2Bean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2Bean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2Bean.getDataRegProvvisoria());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2Bean.getDesUserRegProvvisoria());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2Bean.getDesUORegProvvisoria());	
			}
			
			/* Dati scheda - Ruoli */
			
			// Ufficio proponente
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUfficioProponente())) {							
				pProtocollazioneBean.setUoProtocollante("UO" + pNuovaPropostaAtto2Bean.getUfficioProponente());
				pProtocollazioneBean.setIdUoProponente(pNuovaPropostaAtto2Bean.getUfficioProponente());
			}
			
			/* Dati scheda - Oggetto */
			
			// pProtocollazioneBean.setOggetto(pNuovaPropostaAtto2Bean.getOggetto());
			pProtocollazioneBean.setOggetto(estraiTestoOmissisDaHtml(pNuovaPropostaAtto2Bean.getOggettoHtml()));
			
			/* Dati scheda - Specificità del provvedimento */
			
			List<DocCollegatoBean> listaDocumentiDaCollegare = new ArrayList<DocCollegatoBean>();			
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdUdAttoDeterminaAContrarre())) {
				DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
				lDocCollegatoBean.setIdUdCollegata(pNuovaPropostaAtto2Bean.getIdUdAttoDeterminaAContrarre());
				lDocCollegatoBean.setTipo(pNuovaPropostaAtto2Bean.getCategoriaRegAttoDeterminaAContrarre());
				lDocCollegatoBean.setSiglaRegistro(pNuovaPropostaAtto2Bean.getSiglaAttoDeterminaAContrarre());
				lDocCollegatoBean.setNumero(pNuovaPropostaAtto2Bean.getNumeroAttoDeterminaAContrarre());
				lDocCollegatoBean.setAnno(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getAnnoAttoDeterminaAContrarre()) ? new Integer(pNuovaPropostaAtto2Bean.getAnnoAttoDeterminaAContrarre()) : null);			
				listaDocumentiDaCollegare.add(lDocCollegatoBean);					
			}
			pProtocollazioneBean.setListaDocumentiDaCollegare(listaDocumentiDaCollegare);
			
			// metto a null gli estremi dell'atto di riferimento o in salvataggio me lo mette doppio nei documenti collegati
			pProtocollazioneBean.setSiglaDocRiferimento(null);
			pProtocollazioneBean.setNroDocRiferimento(null);
			pProtocollazioneBean.setAnnoDocRiferimento(null);
						
			/* Dati scheda - Allegati */
			
			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2Bean.getListaAllegati());
			
			/* Dati spesa corrente - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2Bean.getListaAllegatiCorrente());			

			/* Dati spesa in conto capitale - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2Bean.getListaAllegatiContoCapitale());	
			
		}
		
	}

	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2Bean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, false);
	}
	
	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2Bean bean, SezioneCache sezioneCacheAttributiDinamici, boolean flgGenModello) throws Exception {
				
		String flgDettRevocaAtto = bean.getFlgDettRevocaAtto() != null && bean.getFlgDettRevocaAtto() ? "1" : "";

		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DET_ANN_REVOCA_Doc", flgDettRevocaAtto);
		
		/* Dati scheda - Dati di pubblicazione */
		
		List<PeriodoPubblicazioneBean> listaPeriodoPubblicazione = new ArrayList<PeriodoPubblicazioneBean>();		
		if(bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
			PeriodoPubblicazioneBean lPeriodoPubblicazioneBean = new PeriodoPubblicazioneBean();
			lPeriodoPubblicazioneBean.setDataInizioPubblicazione(bean.getDataInizioPubblicazione());
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(bean.getDataInizioPubblicazione());
			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
			lPeriodoPubblicazioneBean.setDataFinePubblicazione(cal.getTime());		
			listaPeriodoPubblicazione.add(lPeriodoPubblicazioneBean);			
		}
		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "PERIODO_PUBBLICAZIONE_Doc", new XmlUtilitySerializer().createVariabileLista(listaPeriodoPubblicazione));
	
		/* Dati scheda - Ruoli */				
		
		String dirigenteAdottante = "";
		String flgAdottanteAncheRdP = "";
		String flgAdottanteAncheRUP = "";
		
		String responsabileProcedimento = "";
		String flgRdPAncheRUP = "";
			
		String responsabileUnicoProvvedimento = "";
		
		String flgRichiediVistoDirettore = bean.getFlgRichiediVistoDirettore() != null && bean.getFlgRichiediVistoDirettore() ? "1" : "";
		
		if(bean.getListaAdottante() != null && bean.getListaAdottante().size() > 0) {
			dirigenteAdottante = bean.getListaAdottante().get(0).getDirigenteAdottante();			
			flgAdottanteAncheRdP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() ? "1" : "";
			flgAdottanteAncheRUP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() ? "1" : "";
		}
		
		if ("1".equals(flgAdottanteAncheRdP)) { 
			responsabileProcedimento = dirigenteAdottante;						
		} else if(bean.getListaRdP() != null && bean.getListaRdP().size() > 0) {
			responsabileProcedimento = bean.getListaRdP().get(0).getIdScrivania();			
			flgRdPAncheRUP = bean.getListaRdP().get(0).getFlgRdPAncheRUP() != null && bean.getListaRdP().get(0).getFlgRdPAncheRUP() ? "1" : "";			
		}
		
		if ("1".equals(flgAdottanteAncheRUP)) { 
			responsabileUnicoProvvedimento = dirigenteAdottante;						
		} else if ("1".equals(flgRdPAncheRUP)) { 
			responsabileUnicoProvvedimento = responsabileProcedimento;						
		} else if(bean.getListaRUP() != null && bean.getListaRUP().size() > 0) {
			responsabileUnicoProvvedimento = bean.getListaRUP().get(0).getIdScrivania();	
		}	
		
		// Dirigente adottante
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ADOTTANTE_Ud", dirigenteAdottante);
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RDP_Ud", flgAdottanteAncheRdP);
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RUP_Ud", flgAdottanteAncheRUP);
		
		// Responsabile di Procedimento
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_PROC_Ud", responsabileProcedimento);
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_RDP_ANCHE_RUP_Ud", flgRdPAncheRUP);
				
		// Responsabile Unico Provvedimento
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RUP_Ud", responsabileUnicoProvvedimento);		
		
		// Richiedi visto Direttore
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIRETTORE_Doc", flgRichiediVistoDirettore);
		
		/* Dati scheda - Proposta di concerto con */
		
		List<IdSVRespFirmatarioBean> listaDirigentiConcerto = new ArrayList<IdSVRespFirmatarioBean>();		
		if(bean.getListaDirigentiConcerto() != null) {
			for(DirigenteDiConcertoBean lDirigenteDiConcertoBean : bean.getListaDirigentiConcerto()) {
				if(StringUtils.isNotBlank(lDirigenteDiConcertoBean.getDirigenteConcerto())) {
					IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
					lIdSVRespFirmatarioBean.setIdSV(lDirigenteDiConcertoBean.getDirigenteConcerto());
					lIdSVRespFirmatarioBean.setFlgFirmatario(lDirigenteDiConcertoBean.getFlgDirigenteConcertoFirmatario());
					listaDirigentiConcerto.add(lIdSVRespFirmatarioBean);
				}
			}
		}		
		String flgDeterminaConcerto = listaDirigentiConcerto.size() > 0 ? "1" : "";		
		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_DI_CONCERTO_Ud", new XmlUtilitySerializer().createVariabileLista(listaDirigentiConcerto));		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_DI_CONCERTO_Doc", flgDeterminaConcerto);
			
		/* Dati scheda - Estensori */
		
		List<SimpleValueBean> listaEstensori = new ArrayList<SimpleValueBean>();		
		if(bean.getListaEstensori() != null) {
			for(EstensoreBean lEstensoreBean : bean.getListaEstensori()) {
				if(StringUtils.isNotBlank(lEstensoreBean.getEstensore())) {
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(lEstensoreBean.getEstensore());
					listaEstensori.add(lSimpleValueBean);
				}
			}
		}		
		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ESTENSORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaEstensori));				
		
		/* Dati scheda - Specificità del provvedimento */

		String flgDeterminaAContrarreTramiteProceduraGara = bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara() ? "1" : "";
		String flgDeterminaAggiudicaProceduraGara = bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara() ? "1" : "";
		String flgDeterminaRimodulazioneSpesaGaraAggiudicata = bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() ? "1" : "";
		String flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() != null && bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() ? "1" : "";
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CONTR_CON_GARA_Doc", flgDeterminaAContrarreTramiteProceduraGara);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_AGGIUDICA_GARA_Doc", flgDeterminaAggiudicaProceduraGara);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD_Doc", flgDeterminaRimodulazioneSpesaGaraAggiudicata);
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_GEST_DET_PERS_EXTRA_AMC")) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_PERSONALE_Doc", flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro);
		}
		
		// Spesa
		
		String flgSpesa = bean.getFlgSpesa() != null ? bean.getFlgSpesa() : "";
		String flgRichVerificaDiBilancioCorrente = "";		
		String flgRichVerificaDiBilancioContoCapitale = "";		
		String flgRichVerificaDiContabilita = "";		
		String flgPresaVisioneContabilita = "";		
		
		if (_FLG_SPESA_NO.equals(flgSpesa)) {
			flgPresaVisioneContabilita = bean.getFlgPresaVisioneContabilita() != null && bean.getFlgPresaVisioneContabilita() ? "1" : "";
		}
		
		if (_FLG_SPESA_SI_SENZA_VLD_RIL_IMP.equals(flgSpesa)) {
			flgRichVerificaDiBilancioCorrente = bean.getFlgRichVerificaDiBilancioCorrente() != null && bean.getFlgRichVerificaDiBilancioCorrente() ? "1" : "";
			flgRichVerificaDiBilancioContoCapitale = bean.getFlgRichVerificaDiBilancioContoCapitale() != null && bean.getFlgRichVerificaDiBilancioContoCapitale() ? "1" : "";
			flgRichVerificaDiContabilita = bean.getFlgRichVerificaDiContabilita() != null && bean.getFlgRichVerificaDiContabilita() ? "1" : "";
		}
		
		if("".equals(flgSpesa)) {
			String faseSalvataggio = getExtraparams().get("faseSalvataggio") != null ? getExtraparams().get("faseSalvataggio") : "";
			logger.error("ERRORE SBIANCAMENTO DATI PROPOSTA ATTO MILANO - FASE SALVATAGGIO: " + faseSalvataggio);			
			throw new StoreException("Si è verificato un errore di comunicazione con il server. Si prega di ripetere l'operazione.");
		}
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_Doc", flgSpesa);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CORR_Doc", flgRichVerificaDiBilancioCorrente);							
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP_Doc", flgRichVerificaDiBilancioContoCapitale);							
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_CONTABIL_Doc", flgRichVerificaDiContabilita);					
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA_Doc", flgPresaVisioneContabilita);					
		
		/* Dati scheda - Atti presupposti */
		
//		List<SimpleValueBean> listaAttiPresupposti = bean.getListaAttiPresupposti() != null ? bean.getListaAttiPresupposti() : new ArrayList<SimpleValueBean>();
//		
//		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", new XmlUtilitySerializer().createVariabileLista(listaAttiPresupposti));
		
		String attiPresupposti = bean.getAttiPresupposti() != null  ? bean.getAttiPresupposti() : "";
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", attiPresupposti);
		
		/* Dati scheda - CIG */
		List<CIGCUPBean> listaCIG = bean.getListaCIG() != null ? bean.getListaCIG() : new ArrayList<CIGCUPBean>();
		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIG_CUP_Doc", new XmlUtilitySerializer().createVariabileLista(listaCIG));
		
		/* Dati scheda - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = bean.getListaRiferimentiNormativi() != null ? bean.getListaRiferimentiNormativi() : new ArrayList<SimpleValueBean>();
		
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "RIFERIMENTI_NORMATIVI_Doc", new XmlUtilitySerializer().createVariabileLista(listaRiferimentiNormativi));
		
		/* Dati scheda - Motivazioni */
		
		String motivazioni = bean.getMotivazioni() != null  ? bean.getMotivazioni() : "";
	
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVAZIONI_ATTO_Doc", motivazioni);
		
		/* Dati scheda - Dispositivo */
		
		String dispositivo = bean.getDispositivo() != null  ? bean.getDispositivo() : "";
		String loghiAggiuntiviDispositivo = bean.getLoghiAggiuntiviDispositivo() != null  ? bean.getLoghiAggiuntiviDispositivo() : "";
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DISPOSITIVO_ATTO_Doc", dispositivo);
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LOGHI_DISPOSITIVO_ATTO_Doc", loghiAggiuntiviDispositivo);
		
		/* Dati spesa - Ruoli */
		
		String flgAdottanteUnicoRespPEG = "";		
		List<SimpleValueBean> listaResponsabiliPEG = new ArrayList<SimpleValueBean>();
		
		String ufficioDefinizioneSpesa = "";
		
		if (_FLG_SPESA_SI.equals(flgSpesa)) {
			flgAdottanteUnicoRespPEG = bean.getFlgAdottanteUnicoRespPEG() != null && bean.getFlgAdottanteUnicoRespPEG() ? "1" : "";
			if ("1".equals(flgAdottanteUnicoRespPEG)) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(dirigenteAdottante);				
				listaResponsabiliPEG.add(lSimpleValueBean);				
			} else if(bean.getListaResponsabiliPEG() != null) {
				for(ResponsabilePEGBean lResponsabilePEGBean : bean.getListaResponsabiliPEG()) {
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(lResponsabilePEGBean.getResponsabilePEG());
					listaResponsabiliPEG.add(lSimpleValueBean);
				}
			}	
			if (StringUtils.isNotBlank(bean.getUfficioDefinizioneSpesa())) {
				ufficioDefinizioneSpesa = bean.getUfficioDefinizioneSpesa();				
			}		
		}
		
		// Responsabili PEG
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA_Ud", flgAdottanteUnicoRespPEG);	
		putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_SPESA_Ud", new XmlUtilitySerializer().createVariabileLista(listaResponsabiliPEG));
		
		// Ufficio definizione spesa
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_UO_COMP_SPESA_Ud", ufficioDefinizioneSpesa);		

		/* Dati spesa - Opzioni */
					
		String flgSpesaCorrente = "";
		String flgImpegniCorrenteGiaValidati = "";
		String flgSpesaContoCapitale = "";
		String flgImpegniContoCapitaleGiaRilasciati = "";
		String flgSoloSubImpSubCrono = "";
		String flgConVerificaContabilita = "";
		String flgRichiediParereRevisoriContabili = "";		
		
		if (_FLG_SPESA_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
			flgSpesaCorrente = bean.getFlgSpesaCorrente() != null && bean.getFlgSpesaCorrente() ? "1" : "";
			if(bean.getFlgSpesaCorrente() != null && bean.getFlgSpesaCorrente()) {
				flgImpegniCorrenteGiaValidati = bean.getFlgImpegniCorrenteGiaValidati() != null && bean.getFlgImpegniCorrenteGiaValidati() ? "1" : "";
			}
			flgSpesaContoCapitale = bean.getFlgSpesaContoCapitale() != null && bean.getFlgSpesaContoCapitale() ? "1" : "";
			if(bean.getFlgSpesaContoCapitale() != null && bean.getFlgSpesaContoCapitale()) {
				flgImpegniContoCapitaleGiaRilasciati = bean.getFlgImpegniContoCapitaleGiaRilasciati() != null && bean.getFlgImpegniContoCapitaleGiaRilasciati() ? "1" : "";
			}
			flgSoloSubImpSubCrono = bean.getFlgSoloSubImpSubCrono() != null && bean.getFlgSoloSubImpSubCrono() ? "1" : "";
			flgConVerificaContabilita = bean.getFlgConVerificaContabilita() != null && bean.getFlgConVerificaContabilita() ? "1" : "";
			flgRichiediParereRevisoriContabili = bean.getFlgRichiediParereRevisoriContabili() != null && bean.getFlgRichiediParereRevisoriContabili() ? "1" : "";
		}
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CORRENTE_Doc", flgSpesaCorrente);
			
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CORR_VALID_Doc", flgImpegniCorrenteGiaValidati);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP_Doc", flgSpesaContoCapitale);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL_Doc", flgImpegniContoCapitaleGiaRilasciati);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SUB_Doc", flgSoloSubImpSubCrono);
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA_Doc", flgConVerificaContabilita);
			
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI_Doc", flgRichiediParereRevisoriContabili);
		
		/* Dati spesa corrente */
				
		String flgDisattivaAutoRequestDatiContabiliSIBCorrente = "";
		String prenotazioneSpesaSIBDiCorrente = "";
		String modalitaInvioDatiSpesaARagioneriaCorrente = "";
		List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
		List<DatiContabiliBean> listaInvioDatiSpesaCorrente = new ArrayList<DatiContabiliBean>();
		String idUdXlsCorrente = "";
		String noteCorrente = "";
		
		if (_FLG_SPESA_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaCorrente)) {					
			flgDisattivaAutoRequestDatiContabiliSIBCorrente = bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() != null && bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() ? "1" : "";
			prenotazioneSpesaSIBDiCorrente = bean.getPrenotazioneSpesaSIBDiCorrente() != null ? bean.getPrenotazioneSpesaSIBDiCorrente() : "";
			modalitaInvioDatiSpesaARagioneriaCorrente = bean.getModalitaInvioDatiSpesaARagioneriaCorrente() != null ? bean.getModalitaInvioDatiSpesaARagioneriaCorrente() : "";
			if(bean.getListaDatiContabiliSIBCorrente() != null) {
				listaDatiContabiliSIBCorrente = bean.getListaDatiContabiliSIBCorrente();
			}	
			if(bean.getListaInvioDatiSpesaCorrente() != null) {
				listaInvioDatiSpesaCorrente = bean.getListaInvioDatiSpesaCorrente();
			}	
			if(bean.getFileXlsCorrente() != null && !flgGenModello) {
				if(StringUtils.isBlank(bean.getFileXlsCorrente().getIdUd()) || (bean.getFileXlsCorrente().getIsChanged() != null && bean.getFileXlsCorrente().getIsChanged())) {
					idUdXlsCorrente = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFileXlsCorrente(), getSession());
				} else if(StringUtils.isNotBlank(bean.getFileXlsCorrente().getIdUd())) {
					idUdXlsCorrente = bean.getFileXlsCorrente().getIdUd();
				}
			}				
			noteCorrente = bean.getNoteCorrente() != null ? bean.getNoteCorrente() : "";							
		}		
		
		if(isAttivoSIB()) {	
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CORR_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PRENOT_SPESA_DI_CORR_Doc", prenotazioneSpesaSIBDiCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CORR_Doc", modalitaInvioDatiSpesaARagioneriaCorrente);
			
			//TODO i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBCorrente));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaCorrente));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CORR_Doc", idUdXlsCorrente);
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CORR_Doc", noteCorrente);
		}
		
		/* Dati spesa conto capitale */
		
		String flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = "";
		String modalitaInvioDatiSpesaARagioneriaContoCapitale = "";
		List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
		List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale = new ArrayList<DatiContabiliBean>();
		String idUdXlsContoCapitale = "";
		String noteContoCapitale = "";
		
		if (_FLG_SPESA_SI.equals(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaContoCapitale)) {					
			flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() != null && bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() ? "1" : "";
			modalitaInvioDatiSpesaARagioneriaContoCapitale = bean.getModalitaInvioDatiSpesaARagioneriaContoCapitale() != null ? bean.getModalitaInvioDatiSpesaARagioneriaContoCapitale() : "";
			if(bean.getListaDatiContabiliSIBContoCapitale() != null) {
				listaDatiContabiliSIBContoCapitale = bean.getListaDatiContabiliSIBContoCapitale();
			}	
			if(bean.getListaInvioDatiSpesaContoCapitale() != null) {
				listaInvioDatiSpesaContoCapitale = bean.getListaInvioDatiSpesaContoCapitale();
			}	
			if(bean.getFileXlsContoCapitale() != null && !flgGenModello) {
				if(StringUtils.isBlank(bean.getFileXlsContoCapitale().getIdUd()) || (bean.getFileXlsContoCapitale().getIsChanged() != null && bean.getFileXlsContoCapitale().getIsChanged())) {
					idUdXlsContoCapitale = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFileXlsContoCapitale(), getSession());
				} else if(StringUtils.isNotBlank(bean.getFileXlsContoCapitale().getIdUd())) {
					idUdXlsContoCapitale = bean.getFileXlsContoCapitale().getIdUd();
				}
			}
			noteContoCapitale = bean.getNoteContoCapitale() != null ? bean.getNoteContoCapitale() : "";					
		}		
		
		if(isAttivoSIB()) {
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CCAP_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBContoCapitale);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CCAP_Doc", modalitaInvioDatiSpesaARagioneriaContoCapitale);
			
			//TODO i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBContoCapitale));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaContoCapitale));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CCAP_Doc", idUdXlsContoCapitale);
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CCAP_Doc", noteContoCapitale);
		}
		
		/* Oggetto HTML */
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "OGGETTO_HTML_Doc", bean.getOggettoHtml());
		
		/* Dati spesa personale */
		/*
		List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale = new ArrayList<DatiSpesaAnnualiXDetPersonaleXmlBean>();
		String capitoloDatiSpesaAnnuaXDetPers = "";		
		String articoloDatiSpesaAnnuaXDetPers = "";		
		String numeroDatiSpesaAnnuaXDetPers = "";		
		String importoDatiSpesaAnnuaXDetPers = "";		
		
		if("1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
			if(bean.getListaDatiSpesaAnnualiXDetPersonale() != null) {
				listaDatiSpesaAnnualiXDetPersonale = bean.getListaDatiSpesaAnnualiXDetPersonale();
			}
			capitoloDatiSpesaAnnuaXDetPers = bean.getCapitoloDatiSpesaAnnuaXDetPers() != null ? bean.getCapitoloDatiSpesaAnnuaXDetPers() : "";
			articoloDatiSpesaAnnuaXDetPers = bean.getArticoloDatiSpesaAnnuaXDetPers() != null ? bean.getArticoloDatiSpesaAnnuaXDetPers() : "";
			numeroDatiSpesaAnnuaXDetPers = bean.getNumeroDatiSpesaAnnuaXDetPers() != null ? bean.getNumeroDatiSpesaAnnuaXDetPers() : "";
			importoDatiSpesaAnnuaXDetPers = bean.getImportoDatiSpesaAnnuaXDetPers() != null ? bean.getImportoDatiSpesaAnnuaXDetPers() : "";
		}
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_GEST_DET_PERS_EXTRA_AMC")) {			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUALI_X_DET_PERSONALE", new XmlUtilitySerializer().createVariabileLista(listaDatiSpesaAnnualiXDetPersonale));
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_COD_CAPITOLO_Doc", capitoloDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_ARTICOLO_Doc", articoloDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUAX_DET_PERS_NRO_VOCE_PEG_Doc", numeroDatiSpesaAnnuaXDetPers);	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DATI_SPESA_ANNUA_X_DET_PERS_IMPORTO_Doc", importoDatiSpesaAnnuaXDetPers);			
		}
		*/
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
	
	// questa è vecchia e non andrebbe più usata
	private String getDatiXModelliPratica(NuovaPropostaAtto2Bean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				
		
		DmpkProcessesGetdatixmodellipratica lDmpkProcessesGetdatixmodellipratica = new DmpkProcessesGetdatixmodellipratica();
		DmpkProcessesGetdatixmodellipraticaBean lDmpkProcessesGetdatixmodellipraticaInput = new DmpkProcessesGetdatixmodellipraticaBean();
		lDmpkProcessesGetdatixmodellipraticaInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkProcessesGetdatixmodellipraticaInput.setIdprocessin(BigDecimal.valueOf(Long.valueOf(bean.getIdProcess())));
		// Creo gli attributi addizionali
		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, true);
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		lDmpkProcessesGetdatixmodellipraticaInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));
		
		StoreResultBean<DmpkProcessesGetdatixmodellipraticaBean> lDmpkProcessesGetdatixmodellipraticaOutput = lDmpkProcessesGetdatixmodellipratica.execute(getLocale(), loginBean,
				lDmpkProcessesGetdatixmodellipraticaInput);
		if (lDmpkProcessesGetdatixmodellipraticaOutput.isInError()) {
			throw new StoreException(lDmpkProcessesGetdatixmodellipraticaOutput);
		}
		
		return lDmpkProcessesGetdatixmodellipraticaOutput.getResultBean().getDatimodellixmlout();
	}
	
	private String getDatiXGenDaModello(NuovaPropostaAtto2Bean bean, String nomeModello) throws Exception {
		
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
		
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, true);
		
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
	
	public FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2Bean bean) throws Exception {
		/*
		if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
			String idProcessToForcePubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_PROC_TO_FORCE_PUBBL");
			if(idProcessToForcePubbl != null && bean.getIdProcess() != null && idProcessToForcePubbl.equals(bean.getIdProcess()) 
			   && bean.getDataInizioPubblicazione() != null && bean.getGiorniPubblicazione() != null) {
				pubblica(bean);
			}
		}
		*/
		return generaDispositivoDaModello(bean, true);
	}
	
	@Deprecated
	private FileDaFirmareBean generaDispositivoDaModelloCablato(NuovaPropostaAtto2Bean bean, boolean flgConvertiInPdf) throws Exception {
		
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // DISPOSITIVO_DETERMINA
		ModelloDispositivoXmlOutBean lModelloDispositivoXmlOutBean = new XmlUtilityDeserializer().unbindXml(templateValues, ModelloDispositivoXmlOutBean.class);
		
		/* Altri dati per generazione modello */
		
		bean.setModelloDispositivoDesDirezioneProponente(lModelloDispositivoXmlOutBean.getModelloDispositivoDesDirezioneProponente());
		bean.setModelloDispositivoFlgRespDiConcertoDiAltreDirezioni(lModelloDispositivoXmlOutBean.getModelloDispositivoFlgRespDiConcertoDiAltreDirezioni() == Flag.SETTED);
		bean.setModelloDispositivoDesRUP(lModelloDispositivoXmlOutBean.getModelloDispositivoDesRUP());
		bean.setModelloDispositivoDesRdP(lModelloDispositivoXmlOutBean.getModelloDispositivoDesRdP());
		bean.setModelloDispositivoDesDirezioneRUP(lModelloDispositivoXmlOutBean.getModelloDispositivoDesDirezioneRUP());
		
		List<SimpleValueBean> listaResponsabiliPEGModelloDispositivo = new ArrayList<SimpleValueBean>();
		if (lModelloDispositivoXmlOutBean.getModelloDispositivoResponsabiliPEG() != null && lModelloDispositivoXmlOutBean.getModelloDispositivoResponsabiliPEG().size() > 0) {
			for (ValueBean lValueBean : lModelloDispositivoXmlOutBean.getModelloDispositivoResponsabiliPEG()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lValueBean.getValue());
				listaResponsabiliPEGModelloDispositivo.add(lSimpleValueBean);
			}
		}
		bean.setListaResponsabiliPEGModelloDispositivo(listaResponsabiliPEGModelloDispositivo);

		List<SimpleValueBean> listaDirezioniConcertoModelloDispositivo = new ArrayList<SimpleValueBean>();
		if (lModelloDispositivoXmlOutBean.getModelloDispositivoDirezioniConcerto() != null && lModelloDispositivoXmlOutBean.getModelloDispositivoDirezioniConcerto().size() > 0) {
			for (ValueBean lValueBean : lModelloDispositivoXmlOutBean.getModelloDispositivoDirezioniConcerto()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lValueBean.getValue());
				listaDirezioniConcertoModelloDispositivo.add(lSimpleValueBean);
			}
		}
		bean.setListaDirezioniConcertoModelloDispositivo(listaDirezioniConcertoModelloDispositivo);
		
		List<FirmatariModelloDispositivoBean> listaFirmatariModelloDispositivo = new ArrayList<FirmatariModelloDispositivoBean>();
		if (lModelloDispositivoXmlOutBean.getModelloDispositivoFirmatari() != null && lModelloDispositivoXmlOutBean.getModelloDispositivoFirmatari().size() > 0) {
			for (FirmatariModelloDispositivoOutBean lFirmatariModelloDispositivoOutBean : lModelloDispositivoXmlOutBean.getModelloDispositivoFirmatari()) {
				FirmatariModelloDispositivoBean lFirmatariModelloDispositivo = new FirmatariModelloDispositivoBean();
				lFirmatariModelloDispositivo.setDenominazione(lFirmatariModelloDispositivoOutBean.getDenominazione());
				lFirmatariModelloDispositivo.setRuolo(lFirmatariModelloDispositivoOutBean.getRuolo());
				listaFirmatariModelloDispositivo.add(lFirmatariModelloDispositivo);
			}
		}
		bean.setListaFirmatariModelloDispositivo(listaFirmatariModelloDispositivo);
		
		bean.setEstremiPropostaAttoPerModello(lModelloDispositivoXmlOutBean.getEstremiPropostaAtto());
		bean.setDataPropostaAttoPerModello(lModelloDispositivoXmlOutBean.getDataPropostaAtto());
		
		Map<String, Object> model = createMapToFillFreeMarkerTemplate(bean);
		
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
		String nomeFilePdf = String.format(bean.getFlgMostraDatiSensibili() ? "Dispositivo versione integrale.%s" : "Dispositivo versione con omissis.%s", ext);
		fillModelBean.setNomeFile(nomeFilePdf);		
		
		return fillModelBean;
	}
	
	private FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2Bean bean, boolean flgConvertiInPdf) throws Exception {
		
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // DISPOSITIVO_DETERMINA
		
		// Ho tolto la parte cablata (vedi il vecchio generaDispositivoDaModelloCablato) e passo direttamente la SezioneCache per l'iniezione nel modello		
		Map<String, Object> model = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), model, flgConvertiInPdf, bean.getFlgMostraDatiSensibili(), getSession());
		
		fillModelBean.getInfoFile().setCorrectFileName(bean.getDisplayFilenameModello());
		
		String ext = flgConvertiInPdf ? "pdf" : FilenameUtils.getExtension(fillModelBean.getNomeFile());
		String nomeFilePdf = String.format(bean.getFlgMostraDatiSensibili() ? "Dispositivo versione integrale.%s" : "Dispositivo versione con omissis.%s", ext);
		fillModelBean.setNomeFile(nomeFilePdf);
		
		return fillModelBean;
	}
	
	public FileDaFirmareBean generaDatiSpesaDaModello(NuovaPropostaAtto2Bean bean) throws Exception {
	
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello()); // APPENDICE DATI DI SPESA
		Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession()); 
		fillModelBean.setNomeFile("Dati di spesa.pdf");		
		
		return fillModelBean;
	}
	
	public Map<String, Object> createMapToFillFreeMarkerTemplate(NuovaPropostaAtto2Bean bean) throws Exception {
		
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		
		// Denominazione direzione
		model.put("desDirezioneProponente", FreeMarkerModelliUtil.getTextModelValue(bean.getModelloDispositivoDesDirezioneProponente()));
		
		// Altre direzioni presenti
		String altreDirezioniPresenti = bean.getModelloDispositivoFlgRespDiConcertoDiAltreDirezioni() != null && bean.getModelloDispositivoFlgRespDiConcertoDiAltreDirezioni() ? "true" : "false";
		model.put("flgRespDiConcertoDiAltreDirezioni", altreDirezioniPresenti);
		
		// Lista direzioni concerto
		List<String> listaDirezioniConcerto = new ArrayList<String>();
		if(bean.getListaDirezioniConcertoModelloDispositivo() != null) {
			for(SimpleValueBean lSimpleValueBean : bean.getListaDirezioniConcertoModelloDispositivo()) {
				if (StringUtils.isNotBlank(lSimpleValueBean.getValue())) {
					listaDirezioniConcerto.add(FreeMarkerModelliUtil.getTextModelValue(lSimpleValueBean.getValue()));
				}
			}
		}
		model.put("listaDirezioniConcerto", listaDirezioniConcerto);
		
		// CIG
		List<Map<String, String>> listaCIG = new ArrayList<Map<String, String>>();
		String flgCIGPresenti = "false";
		if(bean.getListaCIG() != null) {
			// Accrocchio per inserire del testo a sinistra della tabella. 
			// Ho cerato una colonna in più che valorizzo solo nella prima riga
			boolean insertLabel = true;
			for(CIGCUPBean lCIGCUPBean : bean.getListaCIG()) {	
				if (StringUtils.isNotBlank(lCIGCUPBean.getCodiceCIG())) {
					Map<String, String> mappaValori = new HashMap<>();
					mappaValori.put("lbl",  insertLabel ? "CIG:" : "");
					mappaValori.put("value",  FreeMarkerModelliUtil.getTextModelValue(lCIGCUPBean.getCodiceCIG()));
					insertLabel = false;
					listaCIG.add(mappaValori);
					flgCIGPresenti = "true";
				}
			}
		}
		model.put("flgCIG", flgCIGPresenti);
		model.put("listaCIG", listaCIG);
		
		// Oggetto
		model.put("oggetto", FreeMarkerModelliUtil.getHtmlModelValue(bean.getOggettoHtml()));
		
		// Responsabile unico del provvedimento
		model.put("desRup", FreeMarkerModelliUtil.getTextModelValue(bean.getModelloDispositivoDesRUP()));
		
		// Responsabile del procedimento
		model.put("desRdP", FreeMarkerModelliUtil.getTextModelValue(bean.getModelloDispositivoDesRdP()));
		
		// Responsabili PEG
		List<String> listaResponsabiliPEG = new ArrayList<String>();
		if(bean.getListaResponsabiliPEGModelloDispositivo() != null) {
			for(SimpleValueBean lSimpleValueBean : bean.getListaResponsabiliPEGModelloDispositivo()) {	
				if (StringUtils.isNotBlank(lSimpleValueBean.getValue())) {
					listaResponsabiliPEG.add(FreeMarkerModelliUtil.getTextModelValue(lSimpleValueBean.getValue()));
				}
			}
		}
		model.put("listaResponsabiliPEG", listaResponsabiliPEG);
		
		// Direzione RUP
		model.put("desDirezioneRUP", FreeMarkerModelliUtil.getTextModelValue(bean.getModelloDispositivoDesDirezioneRUP()));
		
		// Riferimenti normativi
		List<Object> listaRiferimentiNormativi = new ArrayList<Object>();
		if(bean.getListaRiferimentiNormativi() != null) {
			for(SimpleValueBean lSimpleValueBean : bean.getListaRiferimentiNormativi()) {
				if (StringUtils.isNotBlank(lSimpleValueBean.getValue())) {
					listaRiferimentiNormativi.add(FreeMarkerModelliUtil.getTextModelValue(lSimpleValueBean.getValue()));
				}
			}
		}
		model.put("listaRiferimentiNormativi", listaRiferimentiNormativi);
		
		// Riferimenti atti presupposti
//		List<Object> listaRiferimentiAttiPresupposti = new ArrayList<Object>();
//		if(bean.getListaAttiPresupposti() != null) {
//			for(SimpleValueBean lSimpleValueBean : bean.getListaAttiPresupposti()) {
//				if (StringUtils.isNotBlank(lSimpleValueBean.getValue())) {
//					listaRiferimentiAttiPresupposti.add(ModelliDocDatasource.getTextModelValue(lSimpleValueBean.getValue()));
//				}
//			}
//		}
//		model.put("listaRiferimentiAttiPresupposti", listaRiferimentiAttiPresupposti);
		model.put("attiPresupposti", FreeMarkerModelliUtil.getHtmlModelValue(FreeMarkerModelliUtil.fixHTMLCKEDITOR(bean.getAttiPresupposti(), getSession())));
		
		// Motivazioni
		model.put("motivazioni", FreeMarkerModelliUtil.getHtmlModelValue(FreeMarkerModelliUtil.fixHTMLCKEDITOR(bean.getMotivazioni(), getSession())));
		
		// Dispositivo
		model.put("dispositivo", FreeMarkerModelliUtil.getHtmlModelValue(FreeMarkerModelliUtil.fixHTMLCKEDITOR(bean.getDispositivo(), getSession())));
		
		// Immagine del logo
		// Recupero l'aspectratio dell'immagine che ospita il logo
//		float aspetctRatio = 16.51f;
//		ModelliDocBean modello = ModelliUtil.getModello(getSession(), bean.getIdModello());
//		AllegatoProtocolloBean fileModello = getFileModello(modello);
//		File templateOdt = File.createTempFile("temp", ".odt");
//		FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(fileModello.getUriFileAllegato()), templateOdt);
//		TextDocument odtModello = TextDocument.loadDocument(templateOdt.getPath());		
//        
//		parseXMl(odtModello.getContentDom().getFirstChild());
//      parseXMl(odtModello.getStylesDom().getFirstChild());
        
		String nomeImmagineLogo = StringUtils.isNotBlank(bean.getLoghiAggiuntiviDispositivo()) ? bean.getLoghiAggiuntiviDispositivo() + ".png" : "BLANK.png";
		BufferedImage imgLogo = ImageIO.read(new File(getRequest().getServletContext().getRealPath("/images/loghiXTemplateDoc/" + nomeImmagineLogo)));
		BufferedImage joinedImg = joinBufferedImage(16.51f, imgLogo);
		// construct the buffered image
		// obtain it's graphics
		Graphics2D bImageGraphics = joinedImg.createGraphics();
		// draw the Image (image) into the BufferedImage (bImage)
		bImageGraphics.drawImage(joinedImg, null, null);
		// cast it to rendered image
		RenderedImage rImage = (RenderedImage) joinedImg;
		
		model.put("imgLogo", new RenderedImageSource(rImage));
		
		
		// Firmatari
		List<Map<String, Object>> listaFirmatari = new ArrayList<Map<String, Object>>();
		if(bean.getListaFirmatariModelloDispositivo() != null) {
			for(FirmatariModelloDispositivoBean lFirmatariModelloDispositivoBean : bean.getListaFirmatariModelloDispositivo()) {
				if (StringUtils.isNotBlank(lFirmatariModelloDispositivoBean.getDenominazione())) {
					Map<String, Object> riga = new HashMap<String, Object>();
					riga.put("descrizione", FreeMarkerModelliUtil.getTextModelValue(lFirmatariModelloDispositivoBean.getDenominazione()));
					riga.put("ruolo", FreeMarkerModelliUtil.getTextModelValue(lFirmatariModelloDispositivoBean.getRuolo()));
					listaFirmatari.add(riga);
				}
			}
		}
		model.put("firmatari", listaFirmatari);
		
		// Estremi proposta atto
		model.put("estremiPropostaAtto", FreeMarkerModelliUtil.getTextModelValue(bean.getEstremiPropostaAttoPerModello()));
		
		// Data proposta atto
		model.put("dataPropostaAtto", FreeMarkerModelliUtil.getTextModelValue(bean.getDataPropostaAttoPerModello()));
		
		return model;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnire(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception{
		
		ArrayList<FileDaFirmareBean> listaFileDaUnire = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2Bean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale
		FileDaFirmareBean lFileDispositivoBean  = generaDispositivoDaModello(pNuovaPropostaAtto2Bean);
		listaFileDaUnire.add(lFileDispositivoBean);		
		String idUd = pNuovaPropostaAtto2Bean.getIdUd();
		if (pNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2Bean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					aggiungiAllegato(listaFileDaUnire, lAllegatoProtocolloBean);		
				}
			}
		}
		return listaFileDaUnire;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnireOmissis(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception{
		
		ArrayList<FileDaFirmareBean> listaFileDaUnireOmissis = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2Bean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis
		FileDaFirmareBean lFileDispositivoOmissisBean  = generaDispositivoDaModello(pNuovaPropostaAtto2Bean);
		listaFileDaUnireOmissis.add(lFileDispositivoOmissisBean);		
		String idUd = pNuovaPropostaAtto2Bean.getIdUd();
		if (pNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2Bean.getListaAllegati()){
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
	
	public NuovaPropostaAtto2Bean recuperaIdUdAttoDeterminaAContrarre(NuovaPropostaAtto2Bean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCategoriaRegAttoDeterminaAContrarre());
		input.setSiglaregin(bean.getSiglaAttoDeterminaAContrarre());
		input.setNumregin(bean.getNumeroAttoDeterminaAContrarre() != null ? Integer.parseInt(bean.getNumeroAttoDeterminaAContrarre()) : null);
		input.setAnnoregin(bean.getAnnoAttoDeterminaAContrarre() != null ? Integer.parseInt(bean.getAnnoAttoDeterminaAContrarre()) : null);
		
		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}		
		bean.setIdUdAttoDeterminaAContrarre((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? String.valueOf(output.getResultBean().getIdudio().longValue()) : null);
			
		return bean;
	}
	
	public AllegatoProtocolloBean getAllegatoVistoContabile(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) {
		if (pNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			String idTipoDocAllegatoVistoContabile = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_VISTO_CONTAB_ITER_ATTI");
			if (StringUtils.isNotBlank(idTipoDocAllegatoVistoContabile)) {
				for (int i = 0; i < pNuovaPropostaAtto2Bean.getListaAllegati().size(); i++) {
					AllegatoProtocolloBean lAllegatoProtocolloBean = pNuovaPropostaAtto2Bean.getListaAllegati().get(i);
					if (lAllegatoProtocolloBean.getListaTipiFileAllegato() != null && lAllegatoProtocolloBean.getListaTipiFileAllegato().equalsIgnoreCase(idTipoDocAllegatoVistoContabile)) {
						return lAllegatoProtocolloBean;
					}
				}
			} else {
				for (int i = 0; i < pNuovaPropostaAtto2Bean.getListaAllegati().size(); i++) {
					AllegatoProtocolloBean lAllegatoProtocolloBean = pNuovaPropostaAtto2Bean.getListaAllegati().get(i);
					if (lAllegatoProtocolloBean.getDescTipoFileAllegato() != null && lAllegatoProtocolloBean.getDescTipoFileAllegato().equalsIgnoreCase("Visto / parere Regolarità Contabile")) {
						return lAllegatoProtocolloBean;
					}
				}
			}
		}
		return null;
	}
	
	public NuovaPropostaAtto2Bean pubblicaPost(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {
		return pubblica(pNuovaPropostaAtto2Bean);
	}
	
	// SENZA SPESA: la pubblicazione nelle determine senza spesa avviene dopo la firma adozione se non è una proposta di concerto, altrimenti dopo la firma dei dirigenti di concerto, e devo pubblicare solo il file unione (dispositivo e allegati parte integrante) 
	// CON SPESA: la pubblicazione nelle determine con spesa avviene dopo la firma del visto contabile e devo pubblicare il file unione (dispositivo e allegati parte integrante) e come allegato il file del visto generato in quel task 
	public NuovaPropostaAtto2Bean pubblica(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2Bean bean = get(pNuovaPropostaAtto2Bean);
		
		try {
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());				
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato() && infoFileDaPubblicare.getTipoFirma() != null && infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}	
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				DocumentoType documentoType = new DocumentoType();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					documentoType.setProtocollo(bean.getSiglaRegistrazione());
					documentoType.setNumeroDocumento(bean.getNumeroRegistrazione());
					documentoType.setAnnoDocumento(bean.getDataRegistrazione() != null ? 
						new Integer(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRegistrazione()).substring(6)) : null);
					documentoType.setDataDocumento(bean.getDataRegistrazione());
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				documentoType.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml
				documentoType.setSettore(bean.getDesDirezioneProponente());

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					GregorianCalendar calPubblicazione = new GregorianCalendar();
					calPubblicazione.setTime(bean.getDataInizioPubblicazione());
					documentoType.setDataInizioEsposizione(calPubblicazione.getTime());
					calPubblicazione.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
					documentoType.setDataFineEsposizione(calPubblicazione.getTime());
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String nomeFilePrimario= documentoType.getProtocollo() + "_" + documentoType.getAnnoDocumento() + "_" + documentoType.getNumeroDocumento() + "." + ext;
				
				documentoType.setNomeFile(nomeFilePrimario);
				documentoType.setUsername(loginBean.getUserid());
				
				Integer idTipoDocAlbo = bean.getIdTipoDocAlbo() != null && !"".equalsIgnoreCase(bean.getIdTipoDocAlbo()) ? 
						Integer.parseInt(bean.getIdTipoDocAlbo()) : null;
				if(idTipoDocAlbo != null) {
					documentoType.setTipoDocumento(idTipoDocAlbo);
				} else {
					String tipoDocumento = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOCUMENTO_ALBO_PRETORIO");
					documentoType.setTipoDocumento(tipoDocumento != null && !"".equals(tipoDocumento) ? Integer.parseInt(tipoDocumento) : 9050);
				}
				
				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				documentoType.setEnteProvenienza(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune Milano");
				documentoType.setNote(null);
				
				logger.debug("DocumentType.Protocollo: " + documentoType.getProtocollo());
				logger.debug("DocumentType.NumeroDocumento: " + documentoType.getNumeroDocumento());
				logger.debug("DocumentType.AnnoDocumento: " + documentoType.getAnnoDocumento());
				logger.debug("DocumentType.DataDocumento: " + documentoType.getDataDocumento());
				logger.debug("DocumentType.Oggetto: " + documentoType.getOggetto());
				logger.debug("DocumentType.Settore: " + documentoType.getSettore());
				logger.debug("DocumentType.DataInizioEsposizione: " + documentoType.getDataInizioEsposizione());
				logger.debug("DocumentType.DataFineEsposizione: " + documentoType.getDataFineEsposizione());
				logger.debug("DocumentType.NomeFile: " + documentoType.getNomeFile());
				logger.debug("DocumentType.Username: " + documentoType.getUsername());
				logger.debug("DocumentType.TipoDocumento: " + documentoType.getTipoDocumento());
				logger.debug("DocumentType.EnteProvenienza: " + documentoType.getEnteProvenienza());
				logger.debug("DocumentType.Note: " + documentoType.getNote());

				List<AlboPretorioAttachBean> listaFile = new ArrayList<AlboPretorioAttachBean>();
				List<AlboPretorioAttachBean> listaFileAllegati = new ArrayList<AlboPretorioAttachBean>();

				// Recupero le informazioni del file primario
				AlboPretorioAttachBean filePrimarioAttachBean = new AlboPretorioAttachBean();
				filePrimarioAttachBean.setTipoFile("P");				
				filePrimarioAttachBean.setFileName(nomeFilePrimario);
				filePrimarioAttachBean.setUri(fileDaPubblicare.getPath());
				listaFile.add(filePrimarioAttachBean);
									
				// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
				// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
				if(bean.getAllegatoVistoContabile() == null) {
					bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2Bean));
				}
				if (bean.getAllegatoVistoContabile() != null) {
					logger.debug("Pubblico il file allegato del visto contabile: " + bean.getAllegatoVistoContabile().getUriFileAllegato());
					File fileAllegatoVistoContabile = StorageImplementation.getStorage().extractFile(bean.getAllegatoVistoContabile().getUriFileAllegato());																																
					AlboPretorioAttachBean fileAllegatoVistoContabileAttachBean = new AlboPretorioAttachBean();
					fileAllegatoVistoContabileAttachBean.setTipoFile("A");
					fileAllegatoVistoContabileAttachBean.setFileName(bean.getAllegatoVistoContabile().getNomeFileAllegato());
					fileAllegatoVistoContabileAttachBean.setUri(fileAllegatoVistoContabile.getPath());							
					listaFileAllegati.add(fileAllegatoVistoContabileAttachBean);
					listaFile.add(fileAllegatoVistoContabileAttachBean);					
				}
				
				documentoType.setAllegati(listaFileAllegati);

				pubblicaSuAlboPretorio(listaFile, documentoType, pNuovaPropostaAtto2Bean);

			} else {
				throw new StoreException("Nessun file da pubblicare");
			}

		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	public void pubblicaSuAlboPretorio(List<AlboPretorioAttachBean> listaFiles, DocumentoType documentoType,
						NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {

		logger.debug("Inizio della procedura di collegamento all'Albo Pretorio");

		// Setto il proxy se necessario
		ProxyBean impostazioniProxy = new ProxyBean();
		boolean settoIlProxy = new SetSystemProxy().impostaProxy(impostazioniProxy);

		if (settoIlProxy) {
			logger.debug("Proxy settato");
		} else {
			logger.debug("Proxy non settato");
		}

		// Carico il file in FTP
		if (listaFiles != null && !listaFiles.isEmpty()) {
			for (int i = 0; i < listaFiles.size(); i++) {
				
				AlboPretorioAttachBean lAlboPretorioAttachBean = listaFiles.get(i);
				
				FTPUploadFileBean impostazioniUploadBean = new FTPUploadFileBean();
				impostazioniUploadBean.setFileRequest(lAlboPretorioAttachBean.getUri());
				impostazioniUploadBean.setNomeFileRemoto(lAlboPretorioAttachBean.getFileName());

				logger.debug("File request: " + lAlboPretorioAttachBean.getUri());
				logger.debug("Nome file: " + lAlboPretorioAttachBean.getFileName());
				
				boolean uploadFile = false;
				try {
					uploadFile = new FTPUploadFile().uploadFTP(impostazioniUploadBean);
				} catch (Exception e) {
					logger.error("Errore durante l'upload del file su FTP: " + e.getMessage(), e);
				}

				if (!uploadFile) {
					throw new Exception(
							"Errore durante l'upload del file su FTP. Impossibile procedere all'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		}

		// Invoco il WS Albo Pretorio con il file primario e gli allegati
		try {
			SOAPEnvelope responseDocument = new CaricaDocumento().caricaDocumento(null, documentoType);
			if (responseDocument != null) {
				String respString = responseDocument.getAsString();
				logger.debug("CaricaDocumento response: " + respString);
				// Elaboro la response del WS
				String idAlbo = new ElaboraResponseWS().elaboraResponse(responseDocument);
				if(idAlbo != null && !"".equals(idAlbo)) {
					aggiornaIdAlbo(pNuovaPropostaAtto2Bean.getIdUd(), idAlbo);
				} else {
					logger.error("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: nessun idAlbo restituito");
					throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
				}
			}
		} catch (Throwable e) {
			logger.error("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			throw new Exception("Errore nell'invocazione del servizio per la pubblicazione all'Albo Pretorio");
		}

		addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);

		logger.debug("Fine della procedura di collegamento all'Albo Pretorio");
	}
	
	private void aggiornaIdAlbo(String idUd, String idAlbo) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIduddocin(new BigDecimal(idUd));
		input.setFlgtipotargetin("U");

		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("ID_PUBBL_ALBO_Ud", idAlbo));

		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));

		DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);

		if (lUpddocudOutput.isInError()) {
			throw new StoreException(lUpddocudOutput);
		}		
	}
	
	public UnioneFileAttoBean unioneFile(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {		
		// Prima di tutto devo numerare
		if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto"))) {
			update(pNuovaPropostaAtto2Bean, null);
		}
		UnioneFileAttoBean lUnioneFileAttoBean = new UnioneFileAttoBean();
		try {
			if(pNuovaPropostaAtto2Bean.getFlgDatiSensibili() != null && pNuovaPropostaAtto2Bean.getFlgDatiSensibili()) {
				FileDaFirmareBean lFileUnioneVersIntegraleBean = generaFileUnione(pNuovaPropostaAtto2Bean, true);
				lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneVersIntegraleBean.getNomeFile());
				lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneVersIntegraleBean.getUri());
				lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneVersIntegraleBean.getInfoFile());
				FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pNuovaPropostaAtto2Bean, false);
				lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
				lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
				lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
			} else {
				FileDaFirmareBean lFileUnioneBean = generaFileUnione(pNuovaPropostaAtto2Bean, true);
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
	
	public TaskFileDaFirmareBean getFileDaFirmare(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUriFilePrimario())){
			aggiungiPrimario(listaFileDaFirmare, pNuovaPropostaAtto2Bean);
		}
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUriFilePrimarioOmissis())){
			aggiungiPrimarioOmissis(listaFileDaFirmare, pNuovaPropostaAtto2Bean);
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2Bean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2Bean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2Bean.getIdUd()) : null;
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
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmare(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2Bean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2Bean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2Bean.getIdUd()) : null;
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
	
	public FileDaFirmareBean generaFileUnione(NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean, boolean flgMostraDatiSensibili) throws Exception{
		
		logger.debug("UNIONE DEI FILE");		
		String templateValuesPratica = null;
		List<FileDaFirmareBean> lListFileDaUnireBean = flgMostraDatiSensibili ? getListaFileDaUnire(pNuovaPropostaAtto2Bean) : getListaFileDaUnireOmissis(pNuovaPropostaAtto2Bean);			
		if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {								
			List<InputStream> lListInputStream = new ArrayList<InputStream>();
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdModCopertinaFinale()) && StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNomeModCopertinaFinale())) {	
				String templateValuesCopertinaFinale = getDatiXGenDaModello(pNuovaPropostaAtto2Bean, pNuovaPropostaAtto2Bean.getNomeModCopertinaFinale());				
//				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillTemplate(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModCopertinaFinale(), templateValuesCopertinaFinale, true, getSession());
				Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertinaFinale, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModCopertinaFinale(), mappaValoriCopertinaFinale, true, flgMostraDatiSensibili, getSession()); 
				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertinaFinale.getUri()));					
			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUriModCopertinaFinale())) {				
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2Bean);
				}
				File fileCopertinaFinale = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getUriModCopertinaFinale(), pNuovaPropostaAtto2Bean.getTipoModCopertinaFinale(), templateValuesPratica, getSession());
				lListInputStream.add(new FileInputStream(fileCopertinaFinale));			
			} 
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdModCopertina()) && StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNomeModCopertina())) {	
				String templateValuesCopertina = getDatiXGenDaModello(pNuovaPropostaAtto2Bean, pNuovaPropostaAtto2Bean.getNomeModCopertina());				
//				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillTemplate(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModCopertina(), templateValuesCopertina, true, getSession());
				Map<String, Object> mappaValoriCopertina = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertina, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModCopertina(), mappaValoriCopertina, true, flgMostraDatiSensibili, getSession()); 
				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertina.getUri()));				
			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUriModCopertina())) {
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2Bean);
				}
				File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getUriModCopertina(), pNuovaPropostaAtto2Bean.getTipoModCopertina(), templateValuesPratica, getSession());
				lListInputStream.add(new FileInputStream(fileCopertina));				
			}
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
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getIdModAppendice()) && StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getNomeModAppendice())) {	
				String templateValuesAppendice = getDatiXGenDaModello(pNuovaPropostaAtto2Bean, pNuovaPropostaAtto2Bean.getNomeModAppendice());				
//				FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillTemplate(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModAppendice(), templateValuesAppendice, true, getSession());
				Map<String, Object> mappaValoriAppendice = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAppendice, true);
				FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getIdModAppendice(), mappaValoriAppendice, true, flgMostraDatiSensibili, getSession()); 
				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAppendice.getUri()));				
			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2Bean.getUriModAppendice())) {
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2Bean);
				}
				File fileAppendice = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2Bean.getIdProcess(), pNuovaPropostaAtto2Bean.getUriModAppendice(), pNuovaPropostaAtto2Bean.getTipoModAppendice(), templateValuesPratica, getSession());
				lListInputStream.add(new FileInputStream(fileAppendice));			
			}
			File lFileUnione = unioneFilePdf(lListInputStream);
			String nomeFileUnione = null;
			if(flgMostraDatiSensibili) {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : "Unione file versione integrale.pdf";
			} else {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnioneOmissis")) ? getExtraparams().get("nomeFileUnioneOmissis") : "Unione file versione con omissis.pdf";
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
	
	public CompilaModelloNuovaPropostaAtto2Bean compilazioneAutomaticaModelloPdf(CompilaModelloNuovaPropostaAtto2Bean modelloBean) throws Exception {

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
	
	private void aggiungiPrimario(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile("primario" + pNuovaPropostaAtto2Bean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(pNuovaPropostaAtto2Bean.getInfoFilePrimario());
		lFileDaFirmareBean.setNomeFile(pNuovaPropostaAtto2Bean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(pNuovaPropostaAtto2Bean.getUriFilePrimario());		
		lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBean);		
	}

	private void aggiungiPrimarioOmissis(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2Bean pNuovaPropostaAtto2Bean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
		lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pNuovaPropostaAtto2Bean.getUriFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setInfoFile(pNuovaPropostaAtto2Bean.getInfoFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setNomeFile(pNuovaPropostaAtto2Bean.getNomeFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setUri(pNuovaPropostaAtto2Bean.getUriFilePrimarioOmissis());
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
	
	public NuovaPropostaAtto2Bean aggiornaFileFirmati(TaskNuovaPropostaAtto2FileFirmatiBean pTaskNuovaPropostaAtto2FileFirmatiBean) throws Exception {
		NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean = pTaskNuovaPropostaAtto2FileFirmatiBean.getProtocolloOriginale();
		if (pTaskNuovaPropostaAtto2FileFirmatiBean.getFileFirmati() != null && pTaskNuovaPropostaAtto2FileFirmatiBean.getFileFirmati().getFiles() != null) {
			boolean firmaNonValida = false;
			for (FileDaFirmareBean lFileDaFirmareBean : pTaskNuovaPropostaAtto2FileFirmatiBean.getFileFirmati().getFiles()) {
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
					aggiornaPrimarioOmissisFirmato(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
				} else if (idFile.startsWith("primario")) {
					aggiornaPrimarioFirmato(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegatoOmissis")) {
					aggiornaAllegatoOmissisFirmato(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegato")) {
					aggiornaAllegatoFirmato(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
				} 
			}
			if (firmaNonValida) {
				throw new StoreException("La firma di uno o più file risulta essere non valida");
			}
		}
		return lNuovaPropostaAtto2Bean;
	}
	
	private void aggiornaPrimarioFirmato(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(lNuovaPropostaAtto2Bean.getIsChangedFilePrimario() != null && lNuovaPropostaAtto2Bean.getIsChangedFilePrimario()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2Bean(lProtocollazioneBean, lNuovaPropostaAtto2Bean);			
			getProtocolloDataSource(lNuovaPropostaAtto2Bean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		aggiornaPrimario(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(lNuovaPropostaAtto2Bean.getIsChangedFilePrimarioOmissis() != null && lNuovaPropostaAtto2Bean.getIsChangedFilePrimarioOmissis()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2Bean(lProtocollazioneBean, lNuovaPropostaAtto2Bean);			
			getProtocolloDataSource(lNuovaPropostaAtto2Bean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		aggiornaPrimarioOmissis(lNuovaPropostaAtto2Bean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2Bean);
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2Bean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lNuovaPropostaAtto2Bean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2Bean);
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2Bean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lNuovaPropostaAtto2Bean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaPrimario(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2Bean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2Bean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2Bean.setRemoteUriFilePrimario(false);
		String precImpronta = lNuovaPropostaAtto2Bean.getInfoFilePrimario() != null ? lNuovaPropostaAtto2Bean.getInfoFilePrimario().getImpronta() : null;
		lNuovaPropostaAtto2Bean.setInfoFilePrimario(lFileDaFirmareBean.getInfoFile());
		if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2Bean.setIsChangedFilePrimario(true);
		}
	}
	
	private void aggiornaPrimarioOmissis(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2Bean.setUriFilePrimarioOmissis(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2Bean.setNomeFilePrimarioOmissis(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2Bean.setRemoteUriFilePrimarioOmissis(false);
		String precImprontaOmissis = lNuovaPropostaAtto2Bean.getInfoFilePrimarioOmissis() != null ? lNuovaPropostaAtto2Bean.getInfoFilePrimarioOmissis().getImpronta() : null;
		lNuovaPropostaAtto2Bean.setInfoFilePrimarioOmissis(lFileDaFirmareBean.getInfoFile());
		if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2Bean.setIsChangedFilePrimarioOmissis(true);
		}
	}

	private void aggiornaAllegato(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		if(lNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2Bean.getListaAllegati()){
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
	
	private void aggiornaAllegatoOmissis(NuovaPropostaAtto2Bean lNuovaPropostaAtto2Bean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegatoOmissis".length());
		if(lNuovaPropostaAtto2Bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2Bean.getListaAllegati()){
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
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		File lFile = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));				
		String nomeFile = lFileDaConvertireBean.getInfoFile().getCorrectFileName() != null ? lFileDaConvertireBean.getInfoFile().getCorrectFileName() : "";
		String nomeFilePdf = FilenameUtils.getBaseName(nomeFile) + ".pdf";
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
	public PaginatorBean<NuovaPropostaAtto2Bean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(NuovaPropostaAtto2Bean bean) throws Exception {
		return null;
	}
	
	@Override
	public NuovaPropostaAtto2Bean remove(NuovaPropostaAtto2Bean bean) throws Exception {
		return null;
	};
	
	public boolean isAttivoSIB() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
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
	
	public DownloadDocsZipBean generaVRCzip(OperazioneMassivaAttiBean bean) throws Exception {
		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");

		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		DownloadDocsZipBean retValue = new DownloadDocsZipBean();

		File zipFile = new File(tempPath + "FileVRC.zip");

		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();

		for (int i = 0; i < bean.getListaRecord().size(); i++) {
			AttiBean lAttoBean = bean.getListaRecord().get(i);
			File fileExtract = null;
			if (lAttoBean.getUriVistoRegContabile() != null) {
				fileExtract = recuperaFile(true, lAttoBean.getUriVistoRegContabile()); // TODO: METTERE URI VRC
			}

			String attachmentFileName = null;

			if (fileExtract != null & lAttoBean.getDisplayFilenameVistoRegContabile() != null) {
				MimeTypeFirmaBean infoFile = new InfoFileUtility().getInfoFromFile(fileExtract.toURI().toString(), lAttoBean.getDisplayFilenameVistoRegContabile(), false, null);
				if (infoFile != null) {
					lengthZip += infoFile.getBytes();

					if (lengthZip > MAX_SIZE) {
						throw new StoreException(limiteMaxZipErrorMessage);
					}

					attachmentFileName = fileExtract.getName();
					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
					
					File currentAttachmentVRC = new File(tempPath + attachmentFileName);

					addFileZip(tempPath, infoFile, lAttoBean.getUriVistoRegContabile(), attachmentFileName, currentAttachmentVRC, zipFile);
				}
			} 

		}

		try {
			if (!zipFileNames.isEmpty()) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);

				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
			} else {
				retValue.setMessage("Le unità documentarie selezionate non hanno nessun Visto di Regolarità Contabile");
			}

		} finally {

			// una volta salvato in storage lo posso eliminare localmente
			FileDeleteStrategy.FORCE.delete(zipFile);

		}

		return retValue;

	}
	
	private String normalizeDuplicate(String value, Integer index) {

		String[] tokens = value.split("\\.");
		String attachmentFileName = "";
		if (tokens.length >= 2) {
			for (int x = 0; x < tokens.length - 1; x++)
				attachmentFileName += "." + tokens[x];

			attachmentFileName += "_" + index + "." + tokens[tokens.length - 1];
			// tolgo il primo punto
			attachmentFileName = attachmentFileName.substring(1, attachmentFileName.length());
		} else
			attachmentFileName = tokens[0] + "_" + index + (tokens.length > 1 ? "." + tokens[1] : "");

		return attachmentFileName;
	}
	
	private String checkAndRenameDuplicate(Map<String, Integer> zipFileNames, String attachmentFileName) {

		boolean duplicated = zipFileNames.containsKey(attachmentFileName);

		Integer index = 0;

		if (duplicated) {

			int x = zipFileNames.get(attachmentFileName);
			String attachmentFileNameoriginale = attachmentFileName;
			while (zipFileNames.containsKey(attachmentFileName)) {
				attachmentFileName = normalizeDuplicate(attachmentFileNameoriginale, x);
				x++;
			}

			zipFileNames.put(attachmentFileName, index);

		} else {

			zipFileNames.put(attachmentFileName, index);

		}

		return attachmentFileName;
	}
	
	private void addFileZip(String tempPath, MimeTypeFirmaBean infoFile, String uriFile, String attachmentFileName, File currentAttachment, File zipFile)
			throws Exception, StorageException, IOException, FileNotFoundException {

		if (infoFile != null && infoFile.isFirmato()) {
			String displayFilename = attachmentFileName;
			String fileNameToAdd = infoFile.getCorrectFileName();
			if (attachmentFileName.toLowerCase().endsWith(".p7m") || attachmentFileName.toLowerCase().endsWith(".tsd")) {
				displayFilename = attachmentFileName.substring(0, attachmentFileName.length() - 4);
			}
			if (fileNameToAdd.toLowerCase().endsWith(".p7m") || fileNameToAdd.toLowerCase().endsWith(".tsd")) {
				fileNameToAdd = fileNameToAdd.substring(0, fileNameToAdd.length() - 4);
			}
			
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream sbustatoStream = lInfoFileUtility.sbusta(currentAttachment, attachmentFileName);

			
			File currentAttachmentTemp = File.createTempFile("temp", displayFilename);
			File currentAttachmentSbustato;
			
			try {
				String pathWithoutTempName= currentAttachmentTemp.getPath().replaceAll(FilenameUtils.getName(currentAttachmentTemp.getPath()), displayFilename);
				
				currentAttachmentSbustato = new File(pathWithoutTempName);
				
				currentAttachmentTemp.renameTo(currentAttachmentSbustato);
			} catch (Exception e) {
				logger.error("Errore durante la rinomina del file da inserire nello zip: " + e.getMessage(), e);
				currentAttachmentSbustato = currentAttachmentTemp;
			}
			FileUtil.writeStreamToFile(sbustatoStream, currentAttachmentSbustato);

			StorageUtil.addFileToZip(currentAttachmentSbustato, fileNameToAdd, zipFile.getAbsolutePath());

			FileDeleteStrategy.FORCE.delete(currentAttachmentSbustato);
		} else if (!StringUtils.isBlank(uriFile)) {
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(uriFile), currentAttachment);
			StorageUtil.addFileToZip(currentAttachment, infoFile.getCorrectFileName(), zipFile.getAbsolutePath());

		}  

		FileDeleteStrategy.FORCE.delete(currentAttachment);
	}
	
	private File recuperaFile(Boolean remoteUriFile, String uriFile) throws StorageException {
		// Estraggo il file dal repository
		File file = null;
		if (remoteUriFile != null && remoteUriFile) {
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uriFile);
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileExtractedIn);
			file = out.getExtracted();
		} else {
			// File locale
			file = StorageImplementation.getStorage().extractFile(uriFile);
		}
		
		return file;
	}
	
}