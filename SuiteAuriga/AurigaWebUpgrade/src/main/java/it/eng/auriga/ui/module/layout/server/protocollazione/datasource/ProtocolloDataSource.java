/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciSetattrdinamiciBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindfascinarchivioBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaemailnotassinvioccBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetprotdapgwebBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFinddoctype_jBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.TipoIdBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.AurigaInvioUDMailDatasource;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioUDMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.LockUnlockMail;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ArchiviazioneEmailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ArchiviazioneEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.StampaFileBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltroRifBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssInviiCCBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssInviiCCXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssPreselMittBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttiRichiestiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ContribuenteBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ControinteressatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiProtPGWebXmlBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FileScaricoZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FolderCustomBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MezzoTrasmissioneDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OperazioneMassivaDocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OperazioneMassivaProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.SoggEsternoProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TipoDocumentoBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.auriga.ui.module.layout.shared.util.TipoRichiedente;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkAttributiDinamiciSetattrdinamici;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkCoreFindfascinarchivio;
import it.eng.client.DmpkCoreFindud;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkIntMgoEmailPreparaemailnotassinviocc;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkRepositoryGuiGetprotdapgweb;
import it.eng.client.DmpkUtilityFinddoctype_j;
import it.eng.client.GestioneDocumenti;
import it.eng.client.GestioneEmail;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.converter.DateConverter;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AltreUbicazioniBean;
import it.eng.document.function.bean.AltreUoCoinvolteBean;
import it.eng.document.function.bean.AltriRiferimentiBean;
import it.eng.document.function.bean.AssegnatariBean;
import it.eng.document.function.bean.AttachAndPosizioneBean;
import it.eng.document.function.bean.AttachAndPosizioneCollectionBean;
import it.eng.document.function.bean.AttiRichiestiXMLBean;
import it.eng.document.function.bean.ClassificheFascicoliDocumentoBean;
import it.eng.document.function.bean.ControinteressatiXmlBean;
import it.eng.document.function.bean.CreaDocWithFileBean;
import it.eng.document.function.bean.CreaModAttoInBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DestinatariBean;
import it.eng.document.function.bean.DistribuzioneBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.EmailProvBean;
import it.eng.document.function.bean.FileCompletiXAttiBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FogliXlsDestinatari;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.MailDocumentoIn;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.PeriziaXmlBean;
import it.eng.document.function.bean.ProtocolloRicevuto;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.SoggettoEsternoBean;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.document.function.bean.TipoDestinatario;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoMittente;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.TipoProtocollo;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.FormatiAmmessiUtil;
import it.eng.utility.MessageUtil;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractDataSourceUtilities;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ProtocolloDataSource")
public class ProtocolloDataSource extends AbstractDataSource<ProtocollazioneBean, ProtocollazioneBean> {

	private static final Logger logProtDS = Logger.getLogger(ProtocolloDataSource.class);

	public static final String _COD_ISTAT_ITALIA = "200";
	public static final String _NOME_STATO_ITALIA = "ITALIA";

	// public static final String _COD_ISTAT_MILANO = "015146";
	// public static final String _NOME_COMUNE_MILANO = "MILANO";

	/**
	 * CODICI PER GESTIRE L'ESITO DI ELABORAZIONE DEI FILE DA SCARICARE COME ZIP
	 * */
	public static int OK = 0;
	public static int SUPERATO_LIMITE = 1;
	public static int NESSUN_FILE = 2;
	public static int OK_CON_WARNING_TIMBRO = 3;
	public static int OK_CON_WARNING_SBUSTA= 4;
	
	public IdFileBean getInfoFromFile(IdFileBean bean) throws Exception {
		try {
			if(StringUtils.isNotBlank(bean.getUri())) {
				File fileToCheck = StorageImplementation.getStorage().getRealFile(bean.getUri());
				bean.setInfoFile(new InfoFileUtility().getInfoFromFile(fileToCheck.toURI().toString(), bean.getNomeFile(), false, null));			
			}
			return bean;
		} catch (Exception e) {
			logProtDS.error("Errore durante il recupero delle informazioni del file", e);
			throw new Exception("Errore durante il recupero delle informazioni del file", e);
		}		
	}

	public ProtocollazioneBean trasformaSecondarioInPrimario(ProtocollazioneBean bean) throws Exception {
		int indice = Integer.valueOf(getExtraparams().get("index"));		
		AllegatoProtocolloBean lAllegatoProtocolloBean = bean.getListaAllegati().get(indice);
		// Recupero se è presente un primario
		AllegatoProtocolloBean lAllegatoProtocolloBeanPrimario = null;
		if (bean.getInfoFile() != null && StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			lAllegatoProtocolloBeanPrimario = new AllegatoProtocolloBean();
			lAllegatoProtocolloBeanPrimario.setNumeroProgrAllegato(lAllegatoProtocolloBean.getNumeroProgrAllegato()); // devo mantenere la posizione originale
			lAllegatoProtocolloBeanPrimario.setNomeFileAllegato(bean.getNomeFilePrimario());
			lAllegatoProtocolloBeanPrimario.setUriFileAllegato(bean.getUriFilePrimario());
			lAllegatoProtocolloBeanPrimario.setRemoteUri(bean.getRemoteUriFilePrimario());
			lAllegatoProtocolloBeanPrimario.setIdAttachEmail(bean.getIdAttachEmailPrimario());
			lAllegatoProtocolloBeanPrimario.setInfoFile(bean.getInfoFile());
			lAllegatoProtocolloBeanPrimario.setFlgTimbraFilePostReg(bean.getFlgTimbraFilePostReg());
			lAllegatoProtocolloBeanPrimario.setFlgTimbratoFilePostReg(bean.getFlgTimbratoFilePostReg());
			lAllegatoProtocolloBeanPrimario.setOpzioniTimbro(bean.getOpzioniTimbro());
			lAllegatoProtocolloBeanPrimario.setIsChanged(true);
			lAllegatoProtocolloBeanPrimario.setFlgDatiSensibili(bean.getFlgDatiSensibili());
			// Vers. con omissis
			if(bean.getFilePrimarioOmissis() != null) {
				lAllegatoProtocolloBeanPrimario.setNomeFileOmissis(bean.getFilePrimarioOmissis().getNomeFile());
				lAllegatoProtocolloBeanPrimario.setUriFileOmissis(bean.getFilePrimarioOmissis().getUriFile());
				lAllegatoProtocolloBeanPrimario.setRemoteUriOmissis(bean.getFilePrimarioOmissis().getRemoteUri());
				lAllegatoProtocolloBeanPrimario.setInfoFileOmissis(bean.getFilePrimarioOmissis().getInfoFile());
				lAllegatoProtocolloBeanPrimario.setFlgTimbraFilePostRegOmissis(bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg());
				lAllegatoProtocolloBeanPrimario.setFlgTimbratoFilePostRegOmissis(bean.getFlgTimbratoFilePostReg());
				lAllegatoProtocolloBeanPrimario.setOpzioniTimbroOmissis(bean.getFilePrimarioOmissis().getOpzioniTimbro());
				lAllegatoProtocolloBeanPrimario.setIsChangedOmissis(true);
			}
		}
		bean.setNomeFilePrimario(lAllegatoProtocolloBean.getNomeFileAllegato());
		bean.setUriFilePrimario(lAllegatoProtocolloBean.getUriFileAllegato());
		bean.setRemoteUriFilePrimario(lAllegatoProtocolloBean.getRemoteUri());
		bean.setIdAttachEmailPrimario(lAllegatoProtocolloBean.getIdAttachEmail());
		bean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
		bean.setFlgTimbraFilePostReg(lAllegatoProtocolloBean.getFlgTimbraFilePostReg());
		bean.setFlgTimbratoFilePostReg(lAllegatoProtocolloBean.getFlgTimbratoFilePostReg());
		bean.setOpzioniTimbro(lAllegatoProtocolloBean.getOpzioniTimbro());		
		bean.setIsDocPrimarioChanged(true);
		bean.setFlgDatiSensibili(lAllegatoProtocolloBean.getFlgDatiSensibili());
		
		//Attributi A2A BarcodeImg ecc
		bean.setIdUDScansione(lAllegatoProtocolloBean.getIdUDScansione());
		bean.setTipoBarcodePrimario(lAllegatoProtocolloBean.getTipoBarcodeContratto());
		bean.setBarcodePrimario(lAllegatoProtocolloBean.getBarcodeContratto());
		bean.setEnergiaGasPrimario(lAllegatoProtocolloBean.getEnergiaGasContratto());
		bean.setTipoSezioneContrattoPrimario(lAllegatoProtocolloBean.getTipoSezioneContratto());
		bean.setCodContrattoPrimario(lAllegatoProtocolloBean.getCodContratto());
		bean.setFlgPresentiFirmeContrattoPrimario(lAllegatoProtocolloBean.getFlgPresentiFirmeContratto());
		bean.setFlgFirmeCompleteContrattoPrimario(lAllegatoProtocolloBean.getFlgFirmeCompleteContratto());
		bean.setNroFirmePrevisteContrattoPrimario(lAllegatoProtocolloBean.getNroFirmePrevisteContratto());
		bean.setNroFirmeCompilateContrattoPrimario(lAllegatoProtocolloBean.getNroFirmeCompilateContratto());
		bean.setFlgIncertezzaRilevazioneFirmePrimario(lAllegatoProtocolloBean.getFlgIncertezzaRilevazioneFirmeContratto());
		
		// Vers. con omissis
		if(bean.getFilePrimarioOmissis() != null) {
			bean.getFilePrimarioOmissis().setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
			bean.getFilePrimarioOmissis().setUriFile(lAllegatoProtocolloBean.getUriFileOmissis());
			bean.getFilePrimarioOmissis().setRemoteUri(lAllegatoProtocolloBean.getRemoteUriOmissis());
			bean.getFilePrimarioOmissis().setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
			bean.getFilePrimarioOmissis().setFlgTimbraFilePostReg(lAllegatoProtocolloBean.getFlgTimbraFilePostRegOmissis());
			bean.getFilePrimarioOmissis().setFlgTimbratoFilePostReg(lAllegatoProtocolloBean.getFlgTimbratoFilePostReg());
			bean.getFilePrimarioOmissis().setOpzioniTimbro(lAllegatoProtocolloBean.getOpzioniTimbroOmissis());		
			bean.getFilePrimarioOmissis().setIsChanged(true);			
		}
		List<AllegatoProtocolloBean> lListAllegati = bean.getListaAllegati();
		if (lAllegatoProtocolloBeanPrimario != null)
			lListAllegati.set(indice, lAllegatoProtocolloBeanPrimario);
		else
			lListAllegati.remove(indice);
		bean.setListaAllegati(lListAllegati);
		return bean;
	}

	public ProtocollazioneBean trasformaPrimarioInSecondario(ProtocollazioneBean bean) throws Exception {
		AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
		lAllegatoProtocolloBean.setNomeFileAllegato(bean.getNomeFilePrimario());
		lAllegatoProtocolloBean.setUriFileAllegato(bean.getUriFilePrimario());
		lAllegatoProtocolloBean.setRemoteUri(bean.getRemoteUriFilePrimario());
		lAllegatoProtocolloBean.setIdAttachEmail(bean.getIdAttachEmailPrimario());
		lAllegatoProtocolloBean.setInfoFile(bean.getInfoFile());
		lAllegatoProtocolloBean.setFlgTimbraFilePostReg(bean.getFlgTimbraFilePostReg());
		lAllegatoProtocolloBean.setOpzioniTimbro(bean.getOpzioniTimbro());
		lAllegatoProtocolloBean.setFlgDatiSensibili(bean.getFlgDatiSensibili());
		
		// Attributi A2A Barcode, img scansionate da associare ecc
		lAllegatoProtocolloBean.setIdUDScansione(bean.getIdUDScansione());
		lAllegatoProtocolloBean.setTipoBarcodeContratto(bean.getTipoBarcodePrimario());
		lAllegatoProtocolloBean.setBarcodeContratto(bean.getBarcodePrimario());
		lAllegatoProtocolloBean.setEnergiaGasContratto(bean.getEnergiaGasPrimario());
		lAllegatoProtocolloBean.setTipoSezioneContratto(bean.getTipoSezioneContrattoPrimario());
		lAllegatoProtocolloBean.setCodContratto(bean.getCodContrattoPrimario());
		lAllegatoProtocolloBean.setFlgPresentiFirmeContratto(bean.getFlgPresentiFirmeContrattoPrimario());
		lAllegatoProtocolloBean.setFlgFirmeCompleteContratto(bean.getFlgFirmeCompleteContrattoPrimario());
		lAllegatoProtocolloBean.setNroFirmePrevisteContratto(bean.getNroFirmePrevisteContrattoPrimario());
		lAllegatoProtocolloBean.setNroFirmeCompilateContratto(bean.getNroFirmeCompilateContrattoPrimario());
		lAllegatoProtocolloBean.setFlgIncertezzaRilevazioneFirmeContratto(bean.getFlgIncertezzaRilevazioneFirmePrimario());
		
		// Vers. con omissis
		if(bean.getFilePrimarioOmissis() != null) {
			lAllegatoProtocolloBean.setNomeFileOmissis(bean.getFilePrimarioOmissis().getNomeFile());
			lAllegatoProtocolloBean.setUriFileOmissis(bean.getFilePrimarioOmissis().getUriFile());
			lAllegatoProtocolloBean.setRemoteUriOmissis(bean.getFilePrimarioOmissis().getRemoteUri());
			lAllegatoProtocolloBean.setInfoFileOmissis(bean.getFilePrimarioOmissis().getInfoFile());
			lAllegatoProtocolloBean.setFlgTimbraFilePostRegOmissis(bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg());
			lAllegatoProtocolloBean.setOpzioniTimbroOmissis(bean.getFilePrimarioOmissis().getOpzioniTimbro());			
		}		
		List<AllegatoProtocolloBean> lListAllegati = bean.getListaAllegati() != null ? bean.getListaAllegati() : new ArrayList<AllegatoProtocolloBean>();
//		lAllegatoProtocolloBean.setNumeroProgrAllegato("" + (lListAllegati.size() + 1)); // lo lascio vuoto così nella grid il primario compare sempre come il primo
		lListAllegati.add(lAllegatoProtocolloBean);
		bean.setNomeFilePrimario(null);
		bean.setUriFilePrimario(null);
		bean.setRemoteUriFilePrimario(null);
		bean.setIdAttachEmailPrimario(null);
		bean.setInfoFile(null);
		bean.setFlgTimbraFilePostReg(false);
		bean.setIdUDScansione(null);
		bean.setOpzioniTimbro(null);
		bean.setFlgDatiSensibili(false);
		// Vers. con omissis
		if(bean.getFilePrimarioOmissis() != null) {
			bean.getFilePrimarioOmissis().setNomeFile(null);
			bean.getFilePrimarioOmissis().setUriFile(null);
			bean.getFilePrimarioOmissis().setRemoteUri(null);
			bean.getFilePrimarioOmissis().setInfoFile(null);
			bean.getFilePrimarioOmissis().setFlgTimbraFilePostReg(false);
			bean.getFilePrimarioOmissis().setOpzioniTimbro(null);
		}
		bean.setListaAllegati(lListAllegati);
		return bean;
	}

	@Override
	public ProtocollazioneBean get(ProtocollazioneBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idProcess = getExtraparams().get("idProcess");
		String taskName = getExtraparams().get("taskName");
		String listaIdUdScansioni = getExtraparams().get("listaIdUdScansioni");
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(bean.getIdUd());
		lRecuperaDocumentoInBean.setListaIdUdScansioni(listaIdUdScansioni);
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(getExtraparams().get("flgSoloAbilAzioni"));
		lRecuperaDocumentoInBean.setTsAnnDati(getExtraparams().get("tsAnnDati"));
		lRecuperaDocumentoInBean.setIdProcess(StringUtils.isNotBlank(idProcess) ? new BigDecimal(idProcess) : null);
		lRecuperaDocumentoInBean.setTaskName(StringUtils.isNotBlank(taskName) ? taskName : "#NONE");
		lRecuperaDocumentoInBean.setIdNodeScrivania(getExtraparams().get("idNode"));
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		lProtocollazioneBean.setIdUd(bean.getIdUd());
		if(lProtocollazioneBean.getFlgPresentiAssPreselMitt() != null && lProtocollazioneBean.getFlgPresentiAssPreselMitt() && bean.getIdUd() != null) {			
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();			
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("ASS_PRESEL_MITT");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_UD|*|" + String.valueOf(bean.getIdUd().longValue()));				
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);			
			lProtocollazioneBean.setListaAssPreselMitt(XmlListaUtility.recuperaLista(lStoreResultBean.getResultBean().getListaxmlout(), AssPreselMittBean.class));				
		} 
		lProtocollazioneBean.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
		lProtocollazioneBean.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
		lProtocollazioneBean.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
		lProtocollazioneBean.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
		lProtocollazioneBean.setAbilStampaEtichetta(lDocumentoXmlOutBean.getAbilStampaEtichetta());
		lProtocollazioneBean.setCodStatoDett(lDocumentoXmlOutBean.getCodStatoDett());
		boolean isProtocollazioneBozza = getExtraparams().get("isProtocollazioneBozza") != null && "true".equals(getExtraparams().get("isProtocollazioneBozza"));		
		if(isProtocollazioneBozza && bean.getFlgCompilazioneModulo() != null && bean.getFlgCompilazioneModulo()) {
			//TODO va generato il file primario da modello e vanno sistemati gli altri dati per la protocollazione (mitt, dest)
			lProtocollazioneBean.setNomeFilePrimario(bean.getNomeFilePrimario());
			lProtocollazioneBean.setUriFilePrimario(bean.getUriFilePrimario());
			lProtocollazioneBean.setRemoteUriFilePrimario(false);
			lProtocollazioneBean.setInfoFile(bean.getInfoFile());
			lProtocollazioneBean.setIsDocPrimarioChanged(true);			
		}
		return lProtocollazioneBean;
	}

	/**
	 * Reestituisce una lista di ProtocollazioneBean, dove tutti i file allegati sono delle copie salvate nella cartella temporanea
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public OperazioneMassivaProtocollazioneBean getProtocolloMassivo(OperazioneMassivaProtocollazioneBean bean) throws Exception {
		// Evito di fare la copia di tutti i file perchè rallenta molto ed è inutile (poi in salvataggio gli uri vengono comunque cambiati rispetto agli originali, essendo documenti nuovi)
		PerformanceLogger lPerformanceLogger = new PerformanceLogger("ProtocolloDataSource.getProtocolloMassivo()");
		lPerformanceLogger.start();				
		OperazioneMassivaProtocollazioneBean result = new OperazioneMassivaProtocollazioneBean();
		List<ProtocollazioneBean> copiedFileBeanList = new ArrayList<ProtocollazioneBean>();
		String elencoSegnatureInError = "";
		for (ProtocollazioneBean protocollazioneBean : bean.getListaRecord()) {
			ProtocollazioneBean copiedFileBean = get(protocollazioneBean);
			copiedFileBeanList.add(copiedFileBean);
		}
		HashMap errorMap = new HashMap();
		errorMap.put("segnatureInError", elencoSegnatureInError);
		result.setErrorMessages(errorMap);
		result.getErrorMessages().put("segnatureInError", elencoSegnatureInError);
		result.setListaRecord(copiedFileBeanList);
		lPerformanceLogger.end();
		return result;
	}
	
	private List<DocumentBean> recuperafileDaImportare(ProtocollazioneBean lProtocollazioneBean) {
		List<DocumentBean> listaFile = new ArrayList<>();

		DocumentBean filePrimarioOmissis = lProtocollazioneBean.getFilePrimarioOmissis();

		// File primario senza omissis
		if ((lProtocollazioneBean.getUriFilePrimario() != null && !"".equals(lProtocollazioneBean.getUriFilePrimario()))
				&& ((filePrimarioOmissis.getUriFile() == null) || "".equals(filePrimarioOmissis.getUriFile()))) {

			DocumentBean filePrimarioIntegrale = new DocumentBean();
			filePrimarioIntegrale.setIdUd(lProtocollazioneBean.getIdUd().toString());
			filePrimarioIntegrale.setIdDoc(lProtocollazioneBean.getIdDocPrimario().toString());
			filePrimarioIntegrale.setUriFile(lProtocollazioneBean.getUriFilePrimario());
			filePrimarioIntegrale.setInfoFile(lProtocollazioneBean.getInfoFile());
			filePrimarioIntegrale.setRemoteUri(lProtocollazioneBean.getRemoteUriFilePrimario());
			filePrimarioIntegrale.setIsChanged(false);
			filePrimarioIntegrale.setNomeFileOrig(lProtocollazioneBean.getNomeFilePrimario());
			filePrimarioIntegrale.setNroVersione(lProtocollazioneBean.getNroLastVer());
			filePrimarioIntegrale.setNroUltimaVersione(lProtocollazioneBean.getNroLastVer());
			filePrimarioIntegrale.setNomeFile(lProtocollazioneBean.getNomeFilePrimario());

			listaFile.add(filePrimarioIntegrale);
		}

		// File primario con versione omissis
		else if ((lProtocollazioneBean.getUriFilePrimario() != null
				&& !"".equals(lProtocollazioneBean.getUriFilePrimario()))
				&& ((filePrimarioOmissis.getUriFile() != null) && !"".equals(filePrimarioOmissis.getUriFile()))) {

			// File primario integrale
			DocumentBean filePrimarioIntegrale = new DocumentBean();
			filePrimarioIntegrale.setIdUd(lProtocollazioneBean.getIdUd().toString());
			filePrimarioIntegrale.setIdDoc(lProtocollazioneBean.getIdDocPrimario().toString());
			filePrimarioIntegrale.setUriFile(lProtocollazioneBean.getUriFilePrimario());
			filePrimarioIntegrale.setInfoFile(lProtocollazioneBean.getInfoFile());
			filePrimarioIntegrale.setRemoteUri(lProtocollazioneBean.getRemoteUriFilePrimario());
			filePrimarioIntegrale.setIsChanged(false);
			filePrimarioIntegrale.setNomeFileOrig(lProtocollazioneBean.getNomeFilePrimario());
			filePrimarioIntegrale.setNroVersione(lProtocollazioneBean.getNroLastVer());
			filePrimarioIntegrale.setNroUltimaVersione(lProtocollazioneBean.getNroLastVer());
			filePrimarioIntegrale
					.setNomeFile("File primario (vers. integrale) - " + lProtocollazioneBean.getNomeFilePrimario());

			listaFile.add(filePrimarioIntegrale);

			// File primario omissis
			DocumentBean filePrimarioOmissisDaSelezionare = new DocumentBean();
			filePrimarioOmissisDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
			filePrimarioOmissisDaSelezionare.setIdDoc(filePrimarioOmissis.getIdDoc());
			filePrimarioOmissisDaSelezionare.setUriFile(filePrimarioOmissis.getUriFile());
			filePrimarioOmissisDaSelezionare.setInfoFile(filePrimarioOmissis.getInfoFile());
			filePrimarioOmissisDaSelezionare.setRemoteUri(filePrimarioOmissis.getRemoteUri());
			filePrimarioOmissisDaSelezionare.setIsChanged(false);
			filePrimarioOmissisDaSelezionare.setNomeFileOrig(filePrimarioOmissis.getNomeFileOrig());
			filePrimarioOmissisDaSelezionare.setNroVersione(filePrimarioOmissis.getNroVersione());
			filePrimarioOmissisDaSelezionare.setNroUltimaVersione(filePrimarioOmissis.getNroUltimaVersione());
			filePrimarioOmissisDaSelezionare
					.setNomeFile("File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile());

			listaFile.add(filePrimarioOmissisDaSelezionare);
		}

		// File primario solo omissis
		else if ((lProtocollazioneBean.getUriFilePrimario() == null
				|| "".equals(lProtocollazioneBean.getUriFilePrimario()))
				&& ((filePrimarioOmissis.getUriFile() != null) && !"".equals(filePrimarioOmissis.getUriFile()))) {

			DocumentBean filePrimarioOmissisDaSelezionare = new DocumentBean();
			filePrimarioOmissisDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
			filePrimarioOmissisDaSelezionare.setIdDoc(filePrimarioOmissis.getIdDoc());
			filePrimarioOmissisDaSelezionare.setUriFile(filePrimarioOmissis.getUriFile());
			filePrimarioOmissisDaSelezionare.setInfoFile(filePrimarioOmissis.getInfoFile());
			filePrimarioOmissisDaSelezionare.setRemoteUri(filePrimarioOmissis.getRemoteUri());
			filePrimarioOmissisDaSelezionare.setIsChanged(false);
			filePrimarioOmissisDaSelezionare.setNomeFileOrig(filePrimarioOmissis.getNomeFileOrig());
			filePrimarioOmissisDaSelezionare.setNroVersione(filePrimarioOmissis.getNroVersione());
			filePrimarioOmissisDaSelezionare.setNroUltimaVersione(filePrimarioOmissis.getNroUltimaVersione());
			filePrimarioOmissisDaSelezionare
					.setNomeFile("File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile());

			listaFile.add(filePrimarioOmissisDaSelezionare);
		}

		// Aggiungo gli allegati
		List<AllegatoProtocolloBean> listaAllegati = lProtocollazioneBean.getListaAllegati();

		if (listaAllegati != null) {
			for (AllegatoProtocolloBean allegatoProtBean : listaAllegati) {
				// Allegato senza omissis
				if ((allegatoProtBean.getUriFileAllegato() != null && !"".equals(allegatoProtBean.getUriFileAllegato()))
						&& (allegatoProtBean.getUriFileOmissis() == null
								|| "".equals(allegatoProtBean.getUriFileOmissis()))) {

					DocumentBean allegatoDaSelezionare = new DocumentBean();
					allegatoDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
					allegatoDaSelezionare.setIdDoc(allegatoProtBean.getIdDocAllegato().toString());
					allegatoDaSelezionare.setUriFile(allegatoProtBean.getUriFileAllegato());
					allegatoDaSelezionare.setInfoFile(allegatoProtBean.getInfoFile());
					allegatoDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUri());
					allegatoDaSelezionare.setIsChanged(false);
					allegatoDaSelezionare.setNomeFileOrig(allegatoProtBean.getNomeFileAllegato());
					allegatoDaSelezionare.setNroVersione(allegatoProtBean.getNroUltimaVersione());
					allegatoDaSelezionare.setNroUltimaVersione(allegatoProtBean.getNroUltimaVersione());
					allegatoDaSelezionare.setNomeFile("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato() + " - "
							+ allegatoProtBean.getNomeFileAllegato());

					listaFile.add(allegatoDaSelezionare);
				}

				// Entrambi versioni di allegati
				else if ((allegatoProtBean.getUriFileAllegato() != null
						&& !"".equals(allegatoProtBean.getUriFileAllegato()))
						&& (allegatoProtBean.getUriFileOmissis() != null
								&& !"".equals(allegatoProtBean.getUriFileOmissis()))) {

					// Allegato integrale
					DocumentBean allegatoDaSelezionare = new DocumentBean();
					allegatoDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
					allegatoDaSelezionare.setIdDoc(allegatoProtBean.getIdDocAllegato().toString());
					allegatoDaSelezionare.setUriFile(allegatoProtBean.getUriFileAllegato());
					allegatoDaSelezionare.setInfoFile(allegatoProtBean.getInfoFile());
					allegatoDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUri());
					allegatoDaSelezionare.setIsChanged(false);
					allegatoDaSelezionare.setNomeFileOrig(allegatoProtBean.getNomeFileAllegato());
					allegatoDaSelezionare.setNroVersione(allegatoProtBean.getNroUltimaVersione());
					allegatoDaSelezionare.setNroUltimaVersione(allegatoProtBean.getNroUltimaVersione());
					allegatoDaSelezionare.setNomeFile("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato()
							+ " (vers. integrale) - " + allegatoProtBean.getNomeFileAllegato());

					listaFile.add(allegatoDaSelezionare);

					// Alegato omissis
					DocumentBean allegatoOmissisDaSelezionare = new DocumentBean();
					allegatoOmissisDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
					allegatoOmissisDaSelezionare.setIdDoc(allegatoProtBean.getIdDocOmissis().toString());
					allegatoOmissisDaSelezionare.setUriFile(allegatoProtBean.getUriFileOmissis());
					allegatoOmissisDaSelezionare.setInfoFile(allegatoProtBean.getInfoFileOmissis());
					allegatoOmissisDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUriOmissis());
					allegatoOmissisDaSelezionare.setIsChanged(false);
					allegatoOmissisDaSelezionare.setNomeFileOrig(allegatoProtBean.getNomeFileOmissis());
					allegatoOmissisDaSelezionare.setNroVersione(allegatoProtBean.getNroUltimaVersioneOmissis());
					allegatoOmissisDaSelezionare.setNroUltimaVersione(allegatoProtBean.getNroUltimaVersioneOmissis());
					allegatoOmissisDaSelezionare.setNomeFile("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato()
							+ " (vers. con omissis) - " + allegatoProtBean.getNomeFileOmissis());

					listaFile.add(allegatoOmissisDaSelezionare);
				}

				// Allegato solo omissis
				else if ((allegatoProtBean.getUriFileAllegato() == null
						|| "".equals(allegatoProtBean.getUriFileAllegato()))
						&& (allegatoProtBean.getUriFileOmissis() != null
								&& !"".equals(allegatoProtBean.getUriFileOmissis()))) {

					DocumentBean allegatoOmissisDaSelezionare = new DocumentBean();
					allegatoOmissisDaSelezionare.setIdUd(lProtocollazioneBean.getIdUd().toString());
					allegatoOmissisDaSelezionare.setIdDoc(allegatoProtBean.getIdDocOmissis().toString());
					allegatoOmissisDaSelezionare.setUriFile(allegatoProtBean.getUriFileOmissis());
					allegatoOmissisDaSelezionare.setInfoFile(allegatoProtBean.getInfoFileOmissis());
					allegatoOmissisDaSelezionare.setRemoteUri(allegatoProtBean.getRemoteUriOmissis());
					allegatoOmissisDaSelezionare.setIsChanged(false);
					allegatoOmissisDaSelezionare.setNomeFileOrig(allegatoProtBean.getNomeFileOmissis());
					allegatoOmissisDaSelezionare.setNroVersione(allegatoProtBean.getNroUltimaVersioneOmissis());
					allegatoOmissisDaSelezionare.setNroUltimaVersione(allegatoProtBean.getNroUltimaVersioneOmissis());
					allegatoOmissisDaSelezionare.setNomeFile("Allegato N°" + allegatoProtBean.getNumeroProgrAllegato()
							+ " (vers. con omissis) - " + allegatoProtBean.getNomeFileOmissis());

					listaFile.add(allegatoOmissisDaSelezionare);
				}
			}
		}

		return listaFile;
	}
	
	public OperazioneMassivaDocumentBean getElencoFileForImportInDocumentItem(OperazioneMassivaProtocollazioneBean bean) throws Exception {
		// Evito di fare la copia di tutti i file perchè rallenta molto ed è inutile (poi in salvataggio gli uri vengono comunque cambiati rispetto agli originali, essendo documenti nuovi)
		PerformanceLogger lPerformanceLogger = new PerformanceLogger("ProtocolloDataSource.getElencoFileForImportInDocumentItem()");
		lPerformanceLogger.start();				
		OperazioneMassivaDocumentBean result = new OperazioneMassivaDocumentBean();
		List<DocumentBean> elencoFile = new ArrayList<DocumentBean>();
		String elencoSegnatureInError = "";
		for (ProtocollazioneBean protocollazioneBean : bean.getListaRecord()) {
			ProtocollazioneBean protocolloBean = get(protocollazioneBean);
			List<DocumentBean> fileRecuperati = recuperafileDaImportare(protocolloBean);
			elencoFile.addAll(fileRecuperati);
		}
		HashMap errorMap = new HashMap();
		errorMap.put("segnatureInError", elencoSegnatureInError);
		result.setErrorMessages(errorMap);
		result.getErrorMessages().put("segnatureInError", elencoSegnatureInError);
		result.setListaRecord(elencoFile);
		lPerformanceLogger.end();
		return result;
	}

	@Override
	public ProtocollazioneBean add(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		boolean isBozza = getExtraparams().get("isBozza") != null && "true".equals(getExtraparams().get("isBozza"));
		boolean isRichiestaAccessoAtti = getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
		
		CreaDocWithFileBean lCreaDocWithFileBean = buildCreaDocWithFileBean(bean);
		if(bean.getErroriFile() != null && !bean.getErroriFile().isEmpty()) {
			return bean;
		}
		
		CreaModDocumentoInBean lCreaDocumentoInBean = lCreaDocWithFileBean.getCreaDocumentoIn();
		FilePrimarioBean lFilePrimarioBean = lCreaDocWithFileBean.getFilePrimario();
		AllegatiBean lAllegatiBean = lCreaDocWithFileBean.getAllegati();
		AttachAndPosizioneCollectionBean lAttachAndPosizioneCollectionBean = lCreaDocWithFileBean.getAttachAndPosizioneCollection();
		
		String lStringXml = null;
		try {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lStringXml = lXmlUtilitySerializer.bindXmlCompleta(lCreaDocumentoInBean);
			logProtDS.debug("lCreaDocumentoInBean: " + lStringXml);
		} catch (Exception e) {
			logProtDS.warn(e);
		} 
		
		CreaModDocumentoOutBean lCreaDocumentoOutBean = null;
		// Se vengo da una mail
		if (StringUtils.isNotBlank(bean.getIdEmailArrivo())) {
			EmailProvBean lEmailProvBean = new EmailProvBean();
			lEmailProvBean.setId(bean.getIdEmailArrivo());
			if (bean.getFlgCasellaIstituzionale() != null && bean.getFlgCasellaIstituzionale())
				lEmailProvBean.setFlgCasellaIstituzionale(Flag.SETTED);
			if (bean.getFlgDichIPA() != null && bean.getFlgDichIPA())
				lEmailProvBean.setFlgDichIPA(Flag.SETTED);
			if (bean.getFlgPEC() != null && bean.getFlgPEC())
				lEmailProvBean.setFlgPEC(Flag.SETTED);
			if (StringUtils.isNotBlank(bean.getGestorePEC()))
				lEmailProvBean.setGestorePEC(bean.getGestorePEC());
			if (StringUtils.isNotBlank(bean.getIndirizzo()))
				lEmailProvBean.setIndirizzo(bean.getIndirizzo());
			lCreaDocumentoInBean.setEmailProv(lEmailProvBean);

			GestioneEmail lGestioneEmail = new GestioneEmail();
			MailDocumentoIn lMailDocumentoIn = new MailDocumentoIn();
			ConvertUtils.register(new DateConverter(), Date.class);
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMailDocumentoIn, lCreaDocumentoInBean);
			// Map<String, Object> lMap =
			// BeanUtilsBean2.getInstance().getPropertyUtils().describe(lCreaDocumentoInBean);
			// for (String lString : lMap.keySet()){
			// if (lMap.get(lString)!=null){
			// BeanUtilsBean2.getInstance().setProperty(lMailDocumentoIn,
			// lString, lMap.get(lString));
			// }
			// }
			// BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMailDocumentoIn,
			// lCreaDocumentoInBean);
			lMailDocumentoIn.setIdEmail(bean.getIdEmailArrivo());
			TFilterFetch<TRelEmailFolderBean> lTFetch = new TFilterFetch<TRelEmailFolderBean>();
			TRelEmailFolderBean lTRelEmailFolderBeanSearch = new TRelEmailFolderBean();
			lTRelEmailFolderBeanSearch.setIdEmail(bean.getIdEmailArrivo());
			lTFetch.setFilter(lTRelEmailFolderBeanSearch);
			TPagingList<TRelEmailFolderBean> results = AurigaMailService.getDaoTRelEmailFolder().search(getLocale(), lTFetch);
			String idFolderCasella = results.getData().get(0).getIdFolderCasella();
			String classificazione = AurigaMailService.getDaoTFolderCaselle().get(getLocale(), idFolderCasella).getClassificazione();
			lMailDocumentoIn.setClassificazione(classificazione);
			// Setto il flag di chiusura mail
			if (isRichiestaAccessoAtti) {
				lMailDocumentoIn.setFlgNonArchiviareEmail(1);
			}

			lCreaDocumentoOutBean = lGestioneEmail.creadocumento(getLocale(), lAurigaLoginBean, lMailDocumentoIn, lFilePrimarioBean, lAllegatiBean,
					lAttachAndPosizioneCollectionBean);

			bean.setIdUd(lCreaDocumentoOutBean.getIdUd());

			if (lCreaDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaDocumentoOutBean);
			}

			invioNotificheAssegnazioneInvioCC(bean,lMailDocumentoIn,false);
			
			LockUnlockMail lLockUnlockEmail = new LockUnlockMail(getSession());
			TEmailMgoBean lTEmailMgoBean = lLockUnlockEmail.unlockMail(bean.getIdEmailArrivo());
			
			// boolean archiviaInAutomatico =
			// retrieveArchiviaInAutomatico(lMailDocumentoIn);
		}
		// Normale protocollazione
		else {

			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();

			if (isVecchiaPropostaAtto2Milano()) {

				CreaModAttoInBean lCreaAttoMilanoInBean = new CreaModAttoInBean();
				ConvertUtils.register(new DateConverter(), Date.class);
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lCreaAttoMilanoInBean, lCreaDocumentoInBean);

				lCreaAttoMilanoInBean.setSceltaIter(bean.getSceltaIter());
				lCreaAttoMilanoInBean.setFlgRichParereRevisori(bean.getFlgRichParereRevisori() != null && bean.getFlgRichParereRevisori() ? Flag.SETTED
						: Flag.NOT_SETTED);
				lCreaAttoMilanoInBean.setSiglaAttoRifSubImpegno(bean.getSiglaAttoRifSubImpegno());
				lCreaAttoMilanoInBean.setNumeroAttoRifSubImpegno(bean.getNumeroAttoRifSubImpegno());
				lCreaAttoMilanoInBean.setAnnoAttoRifSubImpegno(bean.getAnnoAttoRifSubImpegno());
				lCreaAttoMilanoInBean.setDataAttoRifSubImpegno(bean.getDataAttoRifSubImpegno());

				lCreaDocumentoOutBean = lGestioneDocumenti
						.creadocumento(getLocale(), lAurigaLoginBean, lCreaAttoMilanoInBean, lFilePrimarioBean, lAllegatiBean);

			} else {

				lCreaDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), lAurigaLoginBean, lCreaDocumentoInBean, lFilePrimarioBean, lAllegatiBean);

			}

			bean.setIdUd(lCreaDocumentoOutBean.getIdUd());
			bean.setRowidDoc(lCreaDocumentoOutBean.getRowidDoc());
			bean.setIdDocPrimario(lCreaDocumentoOutBean.getIdDoc());

			if (lCreaDocumentoOutBean.getDefaultMessage() != null) {
				throw new StoreException(lCreaDocumentoOutBean);
			}

			invioNotificheAssegnazioneInvioCC(bean, lCreaDocumentoInBean, false);
		}

		String result = "";
		String[] attributes = new String[3];
		attributes[0] = lCreaDocumentoOutBean.getNumero().toString();
		attributes[1] = lCreaDocumentoOutBean.getAnno().toString();
		String userLanguage = getLocale().getLanguage();
		if(isRichiestaAccessoAtti) {
			result = "Richiesta accesso atti inserita con successo";
		} else if (lCreaDocumentoInBean.getFlgTipoProv() == null) {
			result = "Documento inserito con successo";
		} else if (lCreaDocumentoInBean.getFlgTipoProv() == TipoProvenienza.ENTRATA || lCreaDocumentoInBean.getFlgTipoProv() == TipoProvenienza.USCITA) {
			if(lCreaDocumentoInBean.getTipoNumerazioni() != null && lCreaDocumentoInBean.getTipoNumerazioni().size() == 1) {
				if(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria() != null &&
						"R".equalsIgnoreCase(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria())) {
					attributes[0] = lCreaDocumentoOutBean.getSigla();
					attributes[1] = lCreaDocumentoOutBean.getNumero().toString();
					attributes[2] = lCreaDocumentoOutBean.getAnno().toString();
					result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_repertoriazione_esito_OK_value", attributes);
				} else {
					result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_generale_esito_OK_value", attributes);
				}
			} else {
				result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_generale_esito_OK_value", attributes);
			}
		} else if (lCreaDocumentoInBean.getFlgTipoProv() == TipoProvenienza.INTERNA) {
			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_REGISTRO_PG_X_PROT_INTERNA")) {
				if(lCreaDocumentoInBean.getTipoNumerazioni() != null && lCreaDocumentoInBean.getTipoNumerazioni().size() == 1) {
					if(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria() != null &&
							"R".equalsIgnoreCase(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria())) {
						attributes[0] = lCreaDocumentoOutBean.getSigla();
						attributes[1] = lCreaDocumentoOutBean.getNumero().toString();
						attributes[2] = lCreaDocumentoOutBean.getAnno().toString();
						result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_repertoriazione_esito_OK_value", attributes);
					} else {
						result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_generale_esito_OK_value", attributes);
					}
				} else {
					result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_generale_esito_OK_value", attributes);
				}
				result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_generale_esito_OK_value", attributes);
			} else {
				if(lCreaDocumentoInBean.getTipoNumerazioni() != null && lCreaDocumentoInBean.getTipoNumerazioni().size() == 1) {
					if(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria() != null &&
							"R".equalsIgnoreCase(lCreaDocumentoInBean.getTipoNumerazioni().get(0).getCategoria())) {
						attributes[0] = lCreaDocumentoOutBean.getSigla();
						attributes[1] = lCreaDocumentoOutBean.getNumero().toString();
						attributes[2] = lCreaDocumentoOutBean.getAnno().toString();
						result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_repertoriazione_esito_OK_value", attributes);
					} else {
						result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_interna_esito_OK_value", attributes);
					}
				} else {
					result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_registrazione_interna_esito_OK_value", attributes);
				}
			}
		}
		bean.setDataProtocollo(lCreaDocumentoOutBean.getData());
		bean.setNroProtocollo(new BigDecimal(lCreaDocumentoOutBean.getNumero() + ""));
		bean.setFileInErrors(lCreaDocumentoOutBean.getFileInErrors());		
		if (lCreaDocumentoOutBean.getFileInErrors() != null && lCreaDocumentoOutBean.getFileInErrors().size() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(result);
			for (String lStrFileInError : lCreaDocumentoOutBean.getFileInErrors().values()) {
				lStringBuffer.append("; " + lStrFileInError);
			}
			addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
		} else if (!isPropostaAtto()) {
			addMessage(result, "", MessageType.INFO);
		}
		if (lCreaDocumentoOutBean.getArchiviazioneError() != null && lCreaDocumentoOutBean.getArchiviazioneError()) {
			addMessage("Non è stato possibile eseguire l'archiviazione automatica dell'e-mail", "", MessageType.WARNING);
		}
		if (lCreaDocumentoOutBean.getInvioConfermaAutomaticaError() != null && lCreaDocumentoOutBean.getInvioConfermaAutomaticaError()) {
			addMessage("Non è stato possibile eseguire l'invio conferma automatica dell'e-mail", "", MessageType.WARNING);
		}
		if (lCreaDocumentoOutBean.getSalvataggioMetadatiError() != null && lCreaDocumentoOutBean.getSalvataggioMetadatiError()) {
			addMessage("Si è verificato un errore durante il salvataggio dei metadati su SharePoint", "", MessageType.WARNING);
		}

		/*
		 * Metodo invio mail automatica - viene inviata se e solo se: 1) Il controllo sulla profilatura è attivo 2) Ho un destinatario valido 3) La mail non è
		 * interoperabile 4) La mail sia in entrata 5) Id mail è valorizzato
		 */

		String controlloProfilatura = ParametriDBUtil.getParametroDB(getSession(), "INVIO_NOTIFICA_ACCETTAZIONE");
		if (controlloProfilatura != null && controlloProfilatura.equalsIgnoreCase("true")) {
			if (bean.getCasellaEmailDestinatario() != null && StringUtils.isNotBlank(bean.getCasellaEmailDestinatario())
					&& (bean.getEmailArrivoInteroperabile() == null || !bean.getEmailArrivoInteroperabile())
					&& (bean.getFlgTipoProv() != null && bean.getFlgTipoProv().equalsIgnoreCase("E"))
					&& (bean.getIdEmailArrivo() != null && StringUtils.isNotBlank(bean.getIdEmailArrivo()))) {
				invioNotificaPostRegistrazione(bean, lAurigaLoginBean);
			}
		}
		
		
		if (isRichiestaAccessoAtti && StringUtils.isNotBlank(bean.getIdEmailArrivo())) {
			try {
				invioMailRicevutaRegistrazioneRichiestaAccessoAtti(bean);
			} catch(Exception e) {
				addMessage("Fallito invio e-mail attestante l'avvenuta protocollazione", "", MessageType.WARNING);
			}
		}
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_TIMBRATURA_FILE_POST_REG") && !isPropostaAtto() && !isBozza) {
			apposizioneTimbroPostReg(bean);
		}
		
		return bean;
	}
	
	public CreaDocWithFileBean buildCreaDocWithFileBean(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		boolean isRepertorio = getExtraparams().get("isRepertorio") != null && "true".equals(getExtraparams().get("isRepertorio"));
		boolean isRegistrazioneFattura = getExtraparams().get("isRegistrazioneFattura") != null
				&& "true".equals(getExtraparams().get("isRegistrazioneFattura"));
		boolean isBozza = getExtraparams().get("isBozza") != null && "true".equals(getExtraparams().get("isBozza"));
		boolean isIstanza = getExtraparams().get("isIstanza") != null && "true".equals(getExtraparams().get("isIstanza"));
		boolean isRichiestaAccessoAtti = getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
			
		FormatiAmmessiUtil lFormatiAmmessiUtil = new FormatiAmmessiUtil();

		ArrayList<String> idUdDaCollegareList = new ArrayList<String>();

		boolean formatiNonAmmessi = false;
		boolean primarioNonAmmesso = false;
		List<String> fileConFormatiNonAmmessi = new ArrayList<String>();

		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			if (bean.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), bean.getInfoFile())) {
				primarioNonAmmesso = true;
			}		
		}
		if ((bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili()) && bean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getUriFile()) && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getNomeFile())) {
			if (bean.getFilePrimarioOmissis().getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), bean.getFilePrimarioOmissis().getInfoFile())) {
				primarioNonAmmesso = true;
			}
		}
		if (bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if (allegato.getInfoFile() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), allegato.getInfoFile())) {
						formatiNonAmmessi = true;
						fileConFormatiNonAmmessi.add(allegato.getNomeFileAllegato());
					}
				}
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) &&  StringUtils.isNotBlank(allegato.getUriFileOmissis()) && StringUtils.isNotBlank(allegato.getNomeFileOmissis())) {
					if (allegato.getInfoFileOmissis() != null && !lFormatiAmmessiUtil.isFormatiAmmessi(getSession(), allegato.getInfoFileOmissis())) {
						formatiNonAmmessi = true;
						fileConFormatiNonAmmessi.add(allegato.getNomeFileOmissis());
					}
				}
			}
		}
		if (primarioNonAmmesso || formatiNonAmmessi) {
			StringBuilder msg = new StringBuilder();
			if (primarioNonAmmesso && !formatiNonAmmessi) {
				msg.append("Attenzione! Il file primario risulta in un formato non ammesso: Impossibile protocollare.");
			} else if (primarioNonAmmesso && formatiNonAmmessi) {
				msg.append("Attenzione! Il file primario ");
				if (fileConFormatiNonAmmessi.size() == 1)
					msg.append("e l'allegato " + fileConFormatiNonAmmessi.get(0));
				else {
					msg.append("e gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi) {
						if (first)
							first = !first;
						else
							msg.append(", ");
						msg.append(lString);
					}
				}
				msg.append(" risultano in un formato non ammesso: Impossibile protocollare.");
			} else if (!primarioNonAmmesso && formatiNonAmmessi) {
				if (fileConFormatiNonAmmessi.size() == 1)
					msg.append("Attenzione! L'allegato " + fileConFormatiNonAmmessi.get(0) + " risulta in un formato non ammesso: Impossibile protocollare.");
				else {
					msg.append("Attenzione! Gli allegati ");
					boolean first = true;
					for (String lString : fileConFormatiNonAmmessi) {
						if (first)
							first = !first;
						else
							msg.append(" - ");
						msg.append(lString);
					}
					msg.append(" risultano in un formato non ammesso: Impossibile protocollare.");
				}
			}
			throw new StoreException(msg.toString());
		}

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		lCreaDocumentoInBean.setCopiaExIdUD(bean.getIdUdNuovoComeCopia() != null ? String.valueOf(bean.getIdUdNuovoComeCopia().longValue()) : null);		
		lCreaDocumentoInBean.setChiaveModelloSelezionato(bean.getPrefKeyModello());
		lCreaDocumentoInBean.setNomeModelloSelezionato(bean.getPrefNameModello());
		lCreaDocumentoInBean.setUseridModelloSelezionato(bean.getUseridModello());
		lCreaDocumentoInBean.setIdUOModelloSelezionato(bean.getIdUoModello());
		lCreaDocumentoInBean.setFlgTipoDocDiverso(bean.getFlgTipoDocDiverso() != null && "1".equals(bean.getFlgTipoDocDiverso()) ? new Integer(1) : null);
		lCreaDocumentoInBean.setIdTipoDocDaCopiare(bean.getIdTipoDocDaCopiare());
		lCreaDocumentoInBean.setIdOperRegistrazione(bean.getIdOperRegistrazione());
		
		// salvo il tipo di registrazione (#FlgTipoProv) : entrata (E), in
		// uscita (U) o interna/tra uffici (I)
		if (bean.getFlgTipoProv() != null/* && !isBozza*/) {
			TipoProvenienza lTipoProvenienza = null;
			if (bean.getFlgTipoProv().equalsIgnoreCase("E")) {
				lTipoProvenienza = TipoProvenienza.ENTRATA;
			} else if (bean.getFlgTipoProv().equalsIgnoreCase("U")) {
				lTipoProvenienza = TipoProvenienza.USCITA;
			} else if (bean.getFlgTipoProv().equalsIgnoreCase("I")) {
				lTipoProvenienza = TipoProvenienza.INTERNA;
			}
			lCreaDocumentoInBean.setFlgTipoProv(lTipoProvenienza);
		}
		
		if(bean.getListaTipiNumerazioneDaDare() != null && !bean.getListaTipiNumerazioneDaDare().isEmpty()) {
			lCreaDocumentoInBean.setTipoNumerazioni(bean.getListaTipiNumerazioneDaDare());			
		} else {

			// Salvo la TIPOLOGIA di NUMERAZIONE (SIGLA e CATEGORIA)
			List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
			
			boolean skipNumerazioniDaDare = !isPropostaAtto() && !isRepertorio && !isRegistrazioneFattura && !isIstanza && isBozza; // se è una bozza
	
			// Se e' una RICHIESTA ACCESSO ATTI allora devo saltare le numerazioni da dare solamente se il richiedente è esterno
			// e se non arrivo da una protocollazione mail
			if (isRichiestaAccessoAtti) {
				skipNumerazioniDaDare = !isRichiestaAccessoAttiRichiedenteInterno(bean);
				if (skipNumerazioniDaDare && StringUtils.isNotBlank(bean.getIdEmailArrivo())) {
					skipNumerazioniDaDare = false;
				}
				// Setto il mezzo di trasmizzione
				if (StringUtils.isNotBlank(bean.getMezzoTrasmissione())) {
					lCreaDocumentoInBean.setMessoTrasmissione(bean.getMezzoTrasmissione());
				}
				
			}
					
			if (!skipNumerazioniDaDare) {			
			
				// Proposta atto
				if (isPropostaAtto()) {
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla(bean.getSiglaProtocollo());
					lTipoNumerazioneBean.setCategoria(bean.getCodCategoriaProtocollo()); // nelle proposte che hanno una numerazione provvisoria la categoria è null
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);
				}
				// Repertorio
				else if (isRepertorio || (bean.getFlgAncheRepertorio() != null && bean.getFlgAncheRepertorio())) {
					bean.setIsRepertorio(true);
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla(bean.getRepertorio());
					int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					int annoPassato = annoCorrente - 1;
					lTipoNumerazioneBean.setAnno(bean.getAnnoPassato() != null && bean.getAnnoPassato() ? String.valueOf(annoPassato) : String.valueOf(annoCorrente));
					lTipoNumerazioneBean.setCategoria("R");
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);
					TipoNumerazioneBean lTipoProtocolloGenerale = creaTipoProtocolloGenerale(bean);
					if (lTipoProtocolloGenerale != null) {
						listaTipiNumerazioni.add(lTipoProtocolloGenerale);
					}
				}
				// Registrazione fattura
				else if (isRegistrazioneFattura) {
					bean.setIsRegistroFatture(true);
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla("FATTURE");
					lTipoNumerazioneBean.setCategoria("I");
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);
					TipoNumerazioneBean lTipoProtocolloGenerale = creaTipoProtocolloGenerale(bean);	
					if (lTipoProtocolloGenerale != null) {
						listaTipiNumerazioni.add(lTipoProtocolloGenerale);
					}
				}
				// Registrazione ISTANZA CED/AUTOTUTELA
				else if (isIstanza) {				
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla("ISTANZA");
					lTipoNumerazioneBean.setCategoria(null);
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);												
					TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();					
					lTipoProtocolloGenerale.setSigla(null);
					lTipoProtocolloGenerale.setCategoria("PG");
					lTipoProtocolloGenerale.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoProtocolloGenerale);						
				}
				// Registrazione ENTRATA o USCITA
				else if (bean.getFlgTipoProv() != null && (bean.getFlgTipoProv().equalsIgnoreCase("E") || bean.getFlgTipoProv().equalsIgnoreCase("U"))) {
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();				
	//				if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INT_PGWEB")) {
	//					lTipoNumerazioneBean.setSigla("PGWEB");
	//					lTipoNumerazioneBean.setCategoria("I");
	//				} else {
						lTipoNumerazioneBean.setSigla(null);
						lTipoNumerazioneBean.setCategoria("PG");
	//				}
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);				
					TipoNumerazioneBean lTipoAltraNumerazioneBean = creaTipoAltraNumerazione(bean);
					if(lTipoAltraNumerazioneBean != null) {
						listaTipiNumerazioni.add(lTipoAltraNumerazioneBean);	
					}
				}
				// Registrazione INTERNA/TRA UFFICI
				else if (bean.getFlgTipoProv() != null && bean.getFlgTipoProv().equalsIgnoreCase("I")) {
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_REGISTRO_PG_X_PROT_INTERNA")) {
						lTipoNumerazioneBean.setSigla(null);
						lTipoNumerazioneBean.setCategoria("PG");
					} else {
						lTipoNumerazioneBean.setSigla("P.I.");
						lTipoNumerazioneBean.setCategoria("I");
					}
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);								
					TipoNumerazioneBean lTipoAltraNumerazioneBean = creaTipoAltraNumerazione(bean);
					if(lTipoAltraNumerazioneBean != null) {
						listaTipiNumerazioni.add(lTipoAltraNumerazioneBean);	
					}
				}			
				// Richiesta accesso atti con richiedente interno
				else if (isRichiestaAccessoAtti) {
					// Verifico se sto protocollando una richiesta accesso atti da mail
					if (StringUtils.isNotBlank(bean.getIdEmailArrivo())){
						TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();				
						lTipoNumerazioneBean.setSigla(null);
						lTipoNumerazioneBean.setCategoria("PG");
						lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
						listaTipiNumerazioni.add(lTipoNumerazioneBean);				
	//					TipoNumerazioneBean lTipoAltraNumerazioneBean = creaTipoAltraNumerazione(bean);
	//					if(lTipoAltraNumerazioneBean != null) {
	//						listaTipiNumerazioni.add(lTipoAltraNumerazioneBean);	
	//					}
					} else {
						TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
						lTipoNumerazioneBean.setSigla("RAAI");
						lTipoNumerazioneBean.setCategoria("R");
						lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
						listaTipiNumerazioni.add(lTipoNumerazioneBean);
					}
				}			
				
			} 
			else if (StringUtils.isNotBlank(bean.getUoProtocollante())) {
				
				//Se è una BOZZA passo comunque l'UO protocollante
				TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
				lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);			
				listaTipiNumerazioni.add(lTipoNumerazioneBean);			
				
			}
			
			if(listaTipiNumerazioni != null && listaTipiNumerazioni.size() > 0) {
				lCreaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioni);
			}
		}
		
		boolean isProtocollazioneRepertoriazione = false;
		if(lCreaDocumentoInBean.getTipoNumerazioni() != null) {
			for(TipoNumerazioneBean lTipoNumerazioneBean : lCreaDocumentoInBean.getTipoNumerazioni()) {
				if(lTipoNumerazioneBean.getCategoria() != null && ("PG".equals(lTipoNumerazioneBean.getCategoria()) || "R".equals(lTipoNumerazioneBean.getCategoria()))) {
					isProtocollazioneRepertoriazione = true;
					break;
				}
			}			
		}
		if(isProtocollazioneRepertoriazione) {
			// se sto protocollando o repertoriando
			controlloFirmePrimarioEAllegatiXNumerazione(bean);
			if(bean.getErroriFile() != null && !bean.getErroriFile().isEmpty()) {
				return null;
			}
		}	
		
		// Tipologie particolari A2A
		lCreaDocumentoInBean.setFlgMulta(bean.getFlgMulta() != null && bean.getFlgMulta() ? new Integer(1) : null);
		lCreaDocumentoInBean.setFlgDocContratti(bean.getFlgDocContratti() != null && bean.getFlgDocContratti() ? new Integer(1) : null);
		lCreaDocumentoInBean.setCodContratti(bean.getCodContratti());
		if(StringUtils.isNotBlank(bean.getFlgFirmeCompilateContratti()) && !"N.A.".equals(bean.getFlgFirmeCompilateContratti())) {
			lCreaDocumentoInBean.setFlgFirmeCompilateContratti(bean.getFlgFirmeCompilateContratti());
		} else {
			lCreaDocumentoInBean.setFlgFirmeCompilateContratti(null);
		}
		lCreaDocumentoInBean.setFlgDocContrattiConBarcode(bean.getFlgDocContrattiBarcode() != null && bean.getFlgDocContrattiBarcode() ? new Integer(1) : null);

		// Salvo l'OGGETTO
		lCreaDocumentoInBean.setOggetto(bean.getOggetto());
		
		// Verifico se sono in una richiesta accesso atti con richiedente interno
		if (!isRichiestaAccessoAttiRichiedenteInterno(bean)){
			// Salvo i MITTENTI
			salvaMittenti(bean, lCreaDocumentoInBean);
		} else {
			// Salvo i RICHIEDENTI INTERNI
			salvaRichiedentiInterni(bean, lCreaDocumentoInBean);
		}
		
		// Richiesta accesso civico
		lCreaDocumentoInBean.setFlgAccessoCivicoSemplice(bean.getFlgRichAccCivSemplice() != null && bean.getFlgRichAccCivSemplice() ? new Integer(1) : null);
		lCreaDocumentoInBean.setFlgAccessoCivicoGeneralizzato(bean.getFlgRichAccCivGeneralizzato() != null && bean.getFlgRichAccCivGeneralizzato() ? new Integer(1) : null);

		// Salvo i destinatari
		salvaDestinatari(bean, lCreaDocumentoInBean);
		// Salvo gli assegnatari dal tab assegnazione
		salvaAssegnatari(bean, lCreaDocumentoInBean);
		// Salvo la lista degli invii per conoscenza
		salvaInvioDestCC(bean, lCreaDocumentoInBean);
		// Salvo la lista delle altre U.O. coinvolte
		salvaAltreUoCoinvolte(bean, lCreaDocumentoInBean);
		
		// Salvo la lista dei documenti collegati
		salvaDocumentiCollegati(bean, lCreaDocumentoInBean);
		// Salvo la lista degli altri riferimenti
		salvaAltriRiferimenti(bean, lCreaDocumentoInBean);
		// Salvo la lista delle perizie
		salvaPerizie(bean, lCreaDocumentoInBean);
				
		// Salvo COD STATO DETT
		lCreaDocumentoInBean.setCodStatoDett(bean.getCodStatoDett());
		// Salvo COD STATO
		lCreaDocumentoInBean.setCodStato(bean.getCodStato());
		
		// Salvo la CLASSIFICA e FASCICOLAZIONE
		// se non arrivo da una proposta atto e quindi da un dettaglio UD devo passare sempre la variabile lista, perchè sarà sempre presente a maschera
		if(!isPropostaAtto2()) {
			List<ClassificheFascicoliDocumentoBean> lListClassificheFascicoli = new ArrayList<ClassificheFascicoliDocumentoBean>();
			if (bean.getListaClassFasc() != null) {
				for (ClassificazioneFascicoloBean lClassificazioneFascicoloBean : bean.getListaClassFasc()) {
					ClassificheFascicoliDocumentoBean lClassificheFascicoliDocumentoBean = new ClassificheFascicoliDocumentoBean();
					lClassificheFascicoliDocumentoBean.setIdClassifica(lClassificazioneFascicoloBean.getIdClassifica());
					lClassificheFascicoliDocumentoBean.setIdFolderFascicolo(lClassificazioneFascicoloBean.getIdFolderFascicolo());
					lClassificheFascicoliDocumentoBean.setAnnoFascicolo(lClassificazioneFascicoloBean.getAnnoFascicolo());
					lClassificheFascicoliDocumentoBean.setNroFascicolo(lClassificazioneFascicoloBean.getNroFascicolo());
					lClassificheFascicoliDocumentoBean.setNroSottofascicolo(lClassificazioneFascicoloBean.getNroSottofascicolo());
					lClassificheFascicoliDocumentoBean.setNroInserto(lClassificazioneFascicoloBean.getNroInserto());
//					if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INT_PGWEB")) {
//						lClassificheFascicoliDocumentoBean.setProvCIClassif(lClassificazioneFascicoloBean.getProvCIClassif());
//						if (StringUtils.isBlank(lClassificheFascicoliDocumentoBean.getProvCIClassif())) {
//							throw new Exception("Manca l'identificativo della classifica su PG@WEB");
//						}
//					}
					if (lClassificazioneFascicoloBean.getFlgCapofila() != null && lClassificazioneFascicoloBean.getFlgCapofila()) {
						lClassificheFascicoliDocumentoBean.setTipoRelazione("CPF");
					}
					lListClassificheFascicoli.add(lClassificheFascicoliDocumentoBean);
				}
			}
			lCreaDocumentoInBean.setClassifichefascicoli(lListClassificheFascicoli);
		// se arrivo da una proposta atto devo passare la variabile solo se la lista è diversa da null, e quindi se nell'xml di configurazione è presente l'attributo CLASS_FASC
		} else if (bean.getListaClassFasc() != null) {
			List<ClassificheFascicoliDocumentoBean> lListClassificheFascicoli = new ArrayList<ClassificheFascicoliDocumentoBean>();
			for (ClassificazioneFascicoloBean lClassificazioneFascicoloBean : bean.getListaClassFasc()) {
				ClassificheFascicoliDocumentoBean lClassificheFascicoliDocumentoBean = new ClassificheFascicoliDocumentoBean();
				lClassificheFascicoliDocumentoBean.setIdClassifica(lClassificazioneFascicoloBean.getIdClassifica());
				lClassificheFascicoliDocumentoBean.setIdFolderFascicolo(lClassificazioneFascicoloBean.getIdFolderFascicolo());
				lClassificheFascicoliDocumentoBean.setAnnoFascicolo(lClassificazioneFascicoloBean.getAnnoFascicolo());
				lClassificheFascicoliDocumentoBean.setNroFascicolo(lClassificazioneFascicoloBean.getNroFascicolo());
				lClassificheFascicoliDocumentoBean.setNroSottofascicolo(lClassificazioneFascicoloBean.getNroSottofascicolo());
				lClassificheFascicoliDocumentoBean.setNroInserto(lClassificazioneFascicoloBean.getNroInserto());
//				if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INT_PGWEB")) {
//					lClassificheFascicoliDocumentoBean.setProvCIClassif(lClassificazioneFascicoloBean.getProvCIClassif());
//					if (StringUtils.isBlank(lClassificheFascicoliDocumentoBean.getProvCIClassif())) {
//						throw new Exception("Manca l'identificativo della classifica su PG@WEB");
//					}
//				}
				if (lClassificazioneFascicoloBean.getFlgCapofila() != null && lClassificazioneFascicoloBean.getFlgCapofila()) {
					lClassificheFascicoliDocumentoBean.setTipoRelazione("CPF");
				}
				lListClassificheFascicoli.add(lClassificheFascicoliDocumentoBean);
			}
			lCreaDocumentoInBean.setClassifichefascicoli(lListClassificheFascicoli);
		}

		// Salvo i FOLDER CUSTOM
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_FOLDER_CUSTOM") && !isPropostaAtto2()) {
			List<FolderCustom> lListFolderCustom = new ArrayList<FolderCustom>();
			if (bean.getListaFolderCustom() != null && bean.getListaFolderCustom().size() > 0) {
				for (FolderCustomBean lFolderCustomBean : bean.getListaFolderCustom()) {
					FolderCustom lFolderCustom = new FolderCustom();
					lFolderCustom.setId(lFolderCustomBean.getId());
					// lFolderCustom.setPath(lFolderCustomBean.getPath());
					if (lFolderCustomBean.getFlgCapofila() != null && lFolderCustomBean.getFlgCapofila()) {
						lFolderCustom.setTipoRelazione("CPF");
					}
					lListFolderCustom.add(lFolderCustom);
				}
			}
			lCreaDocumentoInBean.setFolderCustom(lListFolderCustom);
		} else if (bean.getListaFolderCustom() != null && bean.getListaFolderCustom().size() > 0) {
			List<FolderCustom> lListFolderCustom = new ArrayList<FolderCustom>();
			for (FolderCustomBean lFolderCustomBean : bean.getListaFolderCustom()) {
				FolderCustom lFolderCustom = new FolderCustom();
				lFolderCustom.setId(lFolderCustomBean.getId());
				// lFolderCustom.setPath(lFolderCustomBean.getPath());
				if (lFolderCustomBean.getFlgCapofila() != null && lFolderCustomBean.getFlgCapofila()) {
					lFolderCustom.setTipoRelazione("CPF");
				}
				lListFolderCustom.add(lFolderCustom);
			}
			lCreaDocumentoInBean.setFolderCustom(lListFolderCustom);
		}

		// Grado di risercatezza
		lCreaDocumentoInBean.setLivelloRiservatezza(bean.getLivelloRiservatezza() != null ? bean.getLivelloRiservatezza().intValue() + "" : null);
		// Data termine riservatezza
		if (bean.getLivelloRiservatezza() != null)
			lCreaDocumentoInBean.setTermineRiservatezza(bean.getDataRiservatezza());
		// Priorità
		lCreaDocumentoInBean.setPriorita(bean.getPrioritaRiservatezza());
		
		// Risposta urgente
		lCreaDocumentoInBean.setFlgRispostaUrgente(bean.getFlgRispostaUrgente() != null && bean.getFlgRispostaUrgente() ? new Integer(1) : null);

		// Documento riferimento
		DocumentoCollegato lDocumentoCollegato = new DocumentoCollegato();
		lDocumentoCollegato.setAnno(StringUtils.isNotBlank(bean.getAnnoDocRiferimento()) ? Integer.valueOf(bean.getAnnoDocRiferimento()) : null);
		lDocumentoCollegato.setNumero(bean.getNroDocRiferimento() != null ? bean.getNroDocRiferimento().intValue() : null);

		for (TipoProtocollo lTipoProtocollo : TipoProtocollo.values()) {
			if (lTipoProtocollo.toString().equals(bean.getSiglaDocRiferimento())) {
				lDocumentoCollegato.setTipo(lTipoProtocollo);
				if (lTipoProtocollo == TipoProtocollo.PROTOCOLLO_INTERNO) {
					lDocumentoCollegato.setSiglaRegistro("P.I.");
				}
			}
		}
		if (!(lDocumentoCollegato.getAnno() == null && lDocumentoCollegato.getNumero() == null && lDocumentoCollegato.getSiglaRegistro() == null))
			addDocumentoCollegato(lCreaDocumentoInBean, Arrays.asList(new DocumentoCollegato[] { lDocumentoCollegato }));

		// Collocazione fisica
		if (StringUtils.isNotBlank(bean.getNomeCollocazioneFisica())) {
			lCreaDocumentoInBean.setDescrizioneCollocazione(bean.getNomeCollocazioneFisica());
		}
		if (StringUtils.isNotBlank(bean.getIdToponimo())) {
			lCreaDocumentoInBean.setIdTopografico(bean.getIdToponimo());
		}

		lCreaDocumentoInBean.setFlgNoPubblPrimario(bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario() ? "1" : null);
		lCreaDocumentoInBean.setFlgDatiSensibiliPrimario(bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili() ? "1" : null);

		// Registrazioni da dare
		List<RegistroEmergenza> lListRegistrazioniDaDare = new ArrayList<RegistroEmergenza>();
		if (bean.getDataRegEmergenza() != null && bean.getNroRegEmergenza() != null && StringUtils.isNotBlank(bean.getRifRegEmergenza())) {
			// Registro emergenza
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			lRegistroEmergenza.setDataRegistrazione(bean.getDataRegEmergenza());
			lRegistroEmergenza.setNro(bean.getNroRegEmergenza().intValue() + "");
			lRegistroEmergenza.setRegistro(bean.getRifRegEmergenza());
			lRegistroEmergenza.setIdUserReg(bean.getIdUserRegEmergenza());
			lRegistroEmergenza.setIdUoReg(bean.getIdUoRegEmergenza());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);
		}		
		if (!isRichiestaAccessoAttiRichiedenteInterno(bean) && StringUtils.isNotBlank(bean.getNroProtocolloPregresso()) && (StringUtils.isNotBlank(bean.getAnnoProtocolloPregresso()) || (bean.getDataProtocolloPregresso() != null))) {
			//Registrazione protocollo PG@Web
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			// Inserisco l'id dell'utente loggato o del delegato
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			if (loginBean != null){
				lRegistroEmergenza.setIdUserReg(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? loginBean.getIdUserLavoro() : loginBean.getIdUser() + "");
			}
			lRegistroEmergenza.setDataRegistrazione(bean.getDataProtocolloPregresso());
			lRegistroEmergenza.setFisso("PG");
			lRegistroEmergenza.setRegistro(null);
			lRegistroEmergenza.setAnno(bean.getAnnoProtocolloPregresso());
			lRegistroEmergenza.setNro(bean.getNroProtocolloPregresso());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);			
		}
		if (StringUtils.isNotBlank(bean.getNumeroPraticaSuSistUfficioRichiedente()) && StringUtils.isNotBlank(bean.getAnnoPraticaSuSistUfficioRichiedente())) {
			// Salvo dati pratica su ufficio richiedente
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			lRegistroEmergenza.setFisso("A");
			lRegistroEmergenza.setRegistro(bean.getSiglaPraticaSuSistUfficioRichiedente());
			lRegistroEmergenza.setAnno(bean.getAnnoPraticaSuSistUfficioRichiedente());
			lRegistroEmergenza.setNro(bean.getNumeroPraticaSuSistUfficioRichiedente());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);
		}	
		
		lCreaDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaDare);

		// Altri dati
		lCreaDocumentoInBean.setNote(bean.getNote());
		lCreaDocumentoInBean.setDataStesura(bean.getDataDocumento());
		lCreaDocumentoInBean.setDataSpedizioneCartaceo(bean.getDataSpedizioneCartaceo());
		
		// Data di arrivo al protocollo
		lCreaDocumentoInBean.setDataArrivoProtocollo(bean.getDataArrivoProtocollo());
		
		// Tipo documento
		lCreaDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());

		if(isIstanza) {		
			
			lCreaDocumentoInBean.setDataArrivo(bean.getDataEOraArrivo());
			
			// Mezzo trasmissione
			lCreaDocumentoInBean.setMessoTrasmissione(bean.getMezzoTrasmissione());
			if(bean.getMezzoTrasmissione() != null &&  isCodiceRaccomandata(bean.getMezzoTrasmissione())){
				if (bean.getNroRaccomandata() != null) {
					lCreaDocumentoInBean.setNroRaccomandata(bean.getNroRaccomandata());
				}
				if (bean.getDataRaccomandata() != null) {
					lCreaDocumentoInBean.setDtRaccomandata(bean.getDataRaccomandata());
				}
				if (StringUtils.isNotBlank(bean.getNroListaRaccomandata())) {
					lCreaDocumentoInBean.setNroListaRaccomandata(bean.getNroListaRaccomandata());
				}
			}
			
			if (StringUtils.isNotBlank(bean.getSupportoOriginale())) {
				lCreaDocumentoInBean.setSupportoOriginale(bean.getSupportoOriginale());	
			}
			
		} else {	
			
			// Solo prot entrata - prot ricevuto
			if (bean.getFlgTipoProv() != null && bean.getFlgTipoProv().equalsIgnoreCase("E")) {
				ProtocolloRicevuto lProtocolloRicevuto = new ProtocolloRicevuto();
				if (StringUtils.isNotBlank(bean.getNroProtRicevuto()) && (StringUtils.isNotBlank(bean.getAnnoProtRicevuto()) || bean.getDataProtRicevuto() != null)) {
					lProtocolloRicevuto.setOrigine(bean.getRifOrigineProtRicevuto());
					lProtocolloRicevuto.setNumero(bean.getNroProtRicevuto());
					lProtocolloRicevuto.setAnno(bean.getAnnoProtRicevuto());
					lProtocolloRicevuto.setData(bean.getDataProtRicevuto());
				}
				lCreaDocumentoInBean.setProtocolloRicevuto(lProtocolloRicevuto);
				lCreaDocumentoInBean.setDataArrivo(bean.getDataEOraArrivo());
				// Mezzo trasmissione
				lCreaDocumentoInBean.setMessoTrasmissione(bean.getMezzoTrasmissione());
				if(bean.getMezzoTrasmissione() != null && isCodiceRaccomandata(bean.getMezzoTrasmissione()) //"R".equals(bean.getMezzoTrasmissione())
						) 
				{
					if (bean.getNroRaccomandata() != null) {
						lCreaDocumentoInBean.setNroRaccomandata(bean.getNroRaccomandata());
					}
					if (bean.getDataRaccomandata() != null) {
						lCreaDocumentoInBean.setDtRaccomandata(bean.getDataRaccomandata());
					}
					if (StringUtils.isNotBlank(bean.getNroListaRaccomandata())) {
						lCreaDocumentoInBean.setNroListaRaccomandata(bean.getNroListaRaccomandata());
					}
				}
			} else if (bean.getDataEOraRicezione() != null) {
				// Salvo al data e ora ricezione delle istanze CED/AUTOTUTELA
				lCreaDocumentoInBean.setDataArrivo(bean.getDataEOraRicezione());
			}	
			
			if (isAttivoProtocolloWizard(bean)) {
				lCreaDocumentoInBean.setFlgSkipControlliCartaceo(bean.getFlgSkipControlliCartaceo() != null && bean.getFlgSkipControlliCartaceo() ? "1" : null);
				if (StringUtils.isNotBlank(bean.getSupportoOriginale())) {
					lCreaDocumentoInBean.setSupportoOriginale(bean.getSupportoOriginale());	
				}
			}
			
		}
		
		// Data spedizione
		lCreaDocumentoInBean.setDataEOraSpedizione(bean.getDataEOraSpedizione());
		
		// Firmatari
		lCreaDocumentoInBean.setListaNominativiFirmaOlografa(bean.getListaNominativiFirmaOlografa());

		// Salvo i file ALLEGATI
		AttachAndPosizioneCollectionBean lAttachAndPosizioneCollectionBean = new AttachAndPosizioneCollectionBean();
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();

		AllegatiBean lAllegatiBean = null;
		if (bean.getListaAllegati() != null) {			
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<String> percorsoFileAllegati = new ArrayList<String>();
			List<String> percorsoRelFileAllegati = new ArrayList<String>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgProvEsterna = new ArrayList<Boolean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
			List<Boolean> flgPubblicaSeparato = new ArrayList<Boolean>();
			List<String> nroPagFileUnione = new ArrayList<String>();
			List<String> nroPagFileUnioneOmissis = new ArrayList<String>();
			List<Boolean> flgDatiProtettiTipo1 = new ArrayList<Boolean>();
			List<Boolean> flgDatiProtettiTipo2 = new ArrayList<Boolean>();
			List<Boolean> flgDatiProtettiTipo3 = new ArrayList<Boolean>();
			List<Boolean> flgDatiProtettiTipo4 = new ArrayList<Boolean>();
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();
			List<Boolean> flgGenAutoDaModello = new ArrayList<Boolean>();
			List<String> idUdFrom = new ArrayList<String>();
			List<String> idUdAllegato = new ArrayList<String>();
			// Vers. con omissis
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();			
			int i = 1;			
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgProvEsterna.add(allegato.getFlgProvEsterna());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				// non dobbiamo più salvare l'attributo SAVED_IN_TASK_ID_Doc
//				idTask.add(getExtraparams().get("idTaskCorrente"));
				idTask.add(null);
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());	
				flgPubblicaSeparato.add(allegato.getFlgPubblicaSeparato());	
				if(allegato.getFlgPaginaFileUnione() != null && "n.ro".equals(allegato.getFlgPaginaFileUnione())) {
					nroPagFileUnione.add(allegato.getNroPagFileUnione());
					nroPagFileUnioneOmissis.add(allegato.getNroPagFileUnioneOmissis());		
				} else {
					nroPagFileUnione.add(null);
					nroPagFileUnioneOmissis.add(null);								
				}
				flgDatiProtettiTipo1.add(allegato.getFlgDatiProtettiTipo1());	
				flgDatiProtettiTipo2.add(allegato.getFlgDatiProtettiTipo2());
				flgDatiProtettiTipo3.add(allegato.getFlgDatiProtettiTipo3());
				flgDatiProtettiTipo4.add(allegato.getFlgDatiProtettiTipo4());
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				flgGenAutoDaModello.add(allegato.getFlgGenAutoDaModello());	
				idUdFrom.add(allegato.getIdUdAppartenenza());
				idUdAllegato.add(allegato.getIdUdAllegato());
				if (allegato.getFileAllegato() != null && StringUtils.isNotBlank(bean.getPercorsoFileAllegati())) {
					// nel caso in cui venga passato il file allegato originale, in caso di registrazione in uscita massiva, l'infoFile verrà lasciato vuoto per poi calcolarlo in un secondo momento
					isNull.add(false);
					fileAllegati.add(allegato.getFileAllegato());
					percorsoFileAllegati.add(bean.getPercorsoFileAllegati());
					percorsoRelFileAllegati.add(allegato.getPercorsoRelFileAllegati());	
					info.add(new FileInfoBean());
				} else if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
					isNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					fileAllegati.add(lFile);
					percorsoFileAllegati.add(null);
					percorsoRelFileAllegati.add(null);		
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);						
				} else {
					isNull.add(true);
					percorsoFileAllegati.add(null);
					percorsoRelFileAllegati.add(null);		
					info.add(new FileInfoBean());				
				}
				// Vers. con omissis
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
					isNullOmissis.add(false);
					File lFileAllegatoOmissis = null;
					if (allegato.getRemoteUriOmissis()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileOmissis());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFileAllegatoOmissis = out.getExtracted();
					} else {
						// File locale
						lFileAllegatoOmissis = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
					}
					fileAllegatiOmissis.add(lFileAllegatoOmissis);
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null);
						if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					infoOmissis.add(new FileInfoBean());
					isNullOmissis.add(true);
				}
				if (StringUtils.isNotBlank(allegato.getIdAttachEmail())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				// Federico Cacco 22.06.2016
				// Integro la lista dei documenti da importare
				if ((allegato.getCollegaDocumentoImportato() != null) && (allegato.getCollegaDocumentoImportato())
						&& (StringUtils.isNotBlank(allegato.getIdUdAppartenenza()))) {
					if (!idUdDaCollegareList.contains(allegato.getIdUdAppartenenza())) {
						idUdDaCollegareList.add(allegato.getIdUdAppartenenza());
					}
				}
				i++;
			}
			// Federico Cacco 22.06.2016
			// Inserisco tutti i documenti da importare
			List<DocumentoCollegato> listaDocumentiCollegati = new ArrayList<DocumentoCollegato>();
			for (String idUdDaCollegare : idUdDaCollegareList) {
				DocumentoCollegato documentoCollegato = new DocumentoCollegato();
				documentoCollegato.setIdUd(idUdDaCollegare);
				documentoCollegato.setcValue("C");
				documentoCollegato.setPrcValue("PRC");
				listaDocumentiCollegati.add(documentoCollegato);
			}
			if (listaDocumentiCollegati.size() > 0) {
				addDocumentoCollegato(lCreaDocumentoInBean, listaDocumentiCollegati);
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setFlgSalvaOrdAllegati(getExtraparams().get("flgSalvaOrdAllegati") != null && "true".equals(getExtraparams().get("flgSalvaOrdAllegati")));
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setPercorsoFileAllegati(percorsoFileAllegati);
			lAllegatiBean.setPercorsoRelFileAllegati(percorsoRelFileAllegati);			
			lAllegatiBean.setInfo(info);			
			lAllegatiBean.setFlgProvEsterna(flgProvEsterna);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);		
			lAllegatiBean.setFlgPubblicaSeparato(flgPubblicaSeparato);		
			lAllegatiBean.setNroPagFileUnione(nroPagFileUnione);
			lAllegatiBean.setNroPagFileUnioneOmissis(nroPagFileUnioneOmissis);
			lAllegatiBean.setFlgDatiProtettiTipo1(flgDatiProtettiTipo1);
			lAllegatiBean.setFlgDatiProtettiTipo2(flgDatiProtettiTipo2);
			lAllegatiBean.setFlgDatiProtettiTipo3(flgDatiProtettiTipo3);
			lAllegatiBean.setFlgDatiProtettiTipo4(flgDatiProtettiTipo4);
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);
			lAllegatiBean.setFlgGenAutoDaModello(flgGenAutoDaModello);			
			lAllegatiBean.setIdUdFrom(idUdFrom);			
			lAllegatiBean.setIdUdAllegato(idUdAllegato);			
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);
		}

		// Salvo il file PRIMARIO
		FilePrimarioBean lFilePrimarioBean = retrieveFilePrimario(bean, lAurigaLoginBean);
		if (lFilePrimarioBean != null) {			
			if(lFilePrimarioBean.getFile() != null) {
				if (StringUtils.isNotBlank(lFilePrimarioBean.getPercorsoFilePrimario())) {
					lFilePrimarioBean.setInfo(new FileInfoBean());
				} else {
					MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), bean.getNomeFilePrimario(), false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getNomeFilePrimario());
						}
					}	
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.PRIMARIO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					lFilePrimarioBean.setInfo(lFileInfoBean);
				}
				if (StringUtils.isNotBlank(bean.getIdAttachEmailPrimario())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
					lAttachAndPosizioneBean.setPosizione(0);
					list.add(lAttachAndPosizioneBean);
				}
			}
			// Vers. con omissis
			if(lFilePrimarioBean.getFileOmissis() != null && bean.getFilePrimarioOmissis() != null) {
				MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = bean.getFilePrimarioOmissis().getInfoFile();
				if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
					File lFileOmissis = StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile());
					lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileOmissis.toURI().toString(), bean.getFilePrimarioOmissis().getNomeFile(), false, null);
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getFilePrimarioOmissis().getNomeFile() + " (vers. con omissis)");
					}
				}
				FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
				lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFileOmissis = new GenericFile();
				setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
				lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
				lGenericFileOmissis.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
				lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
				lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
					lGenericFileOmissis.setDaScansione(Flag.SETTED);
					lGenericFileOmissis.setDataScansione(new Date());
					lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				lFilePrimarioBean.setInfoOmissis(lFileInfoBeanOmissis);
			}
		}
		
		salvaAltriSoggettiEsterni(bean, lCreaDocumentoInBean);
		
		// Ricercatore incaricato visura
		lCreaDocumentoInBean.setIdRicercatoreVisuraSUE(bean.getIdRicercatoreVisuraSUE());
		
		// Federico Cacco 13.06.2017
		// Salvo i dati di richiesta accesso atti
		salvaListaAttiRichiestaAccessoAtti(bean, lCreaDocumentoInBean);

		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustom(bean, sezioneCacheAttributiDinamici);
		lCreaDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
//		logProtDS.debug(new XmlUtilitySerializer().bindXml(lCreaDocumentoInBean));  
		
		// Richiesta accesso civico
		lCreaDocumentoInBean.setFlgPresentiControinteressati(bean.getFlgPresentiControinteressati());
		List<ControinteressatiXmlBean> listaControinteressati = new ArrayList<ControinteressatiXmlBean>();
		if(bean.getFlgPresentiControinteressati() != null && "SI".equalsIgnoreCase(bean.getFlgPresentiControinteressati())) {
			if(bean.getListaControinteressati() != null && bean.getListaControinteressati().size() > 0) {
				for(ControinteressatoBean item : bean.getListaControinteressati()) {
					ControinteressatiXmlBean controinteressatiXmlBean = new ControinteressatiXmlBean();
					controinteressatiXmlBean.setTipoSoggetto(item.getTipoSoggetto());
					controinteressatiXmlBean.setDenominazione(item.getDenominazione());
					controinteressatiXmlBean.setCodFiscale(item.getCodFiscale());
					controinteressatiXmlBean.setpIva(item.getpIva());
					controinteressatiXmlBean.setNote(item.getNote());
					listaControinteressati.add(controinteressatiXmlBean);
				}
			}
		}
		lCreaDocumentoInBean.setListaControinteressati(listaControinteressati);		
		
		lAttachAndPosizioneCollectionBean.setLista(list);
		
		CreaDocWithFileBean lCreaDocWithFileBean = new CreaDocWithFileBean();
		lCreaDocWithFileBean.setCreaDocumentoIn(lCreaDocumentoInBean);
		lCreaDocWithFileBean.setFilePrimario(lFilePrimarioBean);
		lCreaDocWithFileBean.setAllegati(lAllegatiBean);
		lCreaDocWithFileBean.setAttachAndPosizioneCollection(lAttachAndPosizioneCollectionBean);
		
		return lCreaDocWithFileBean;
	}
	
	// Archivia la mail 
	private void archiviaEmail(String idEmailUDIn, String operazioneRichiestaIn) throws Exception {
		
		// Popolo input
		List<PostaElettronicaBean> listaArchiviazioneEmail = new ArrayList<PostaElettronicaBean>();
		PostaElettronicaBean lPostaElettronicaBean = new PostaElettronicaBean();
		
		lPostaElettronicaBean.setIdEmail(idEmailUDIn);	
		listaArchiviazioneEmail.add(lPostaElettronicaBean);
			
		// Lancio l'archiviazione
		ArchiviazioneEmailBean       inputDataSourceBean  = new ArchiviazioneEmailBean();
		inputDataSourceBean.setListaRecord(listaArchiviazioneEmail);
		inputDataSourceBean.setOperazioneRichiesta(operazioneRichiestaIn);
		inputDataSourceBean.setMotivazione(null);
		
		
		ArchiviazioneEmailBean       outputDataSourceBean = new ArchiviazioneEmailBean();
		ArchiviazioneEmailDataSource dataSource = new ArchiviazioneEmailDataSource();
		
		try {			
			dataSource.setSession(getSession());
			outputDataSourceBean = dataSource.add(inputDataSourceBean);   
	
			
			if (outputDataSourceBean.getErrorMessages()!=null){
				HashMap<String, String> errorMessages = null;
				errorMessages = new HashMap<String, String>();
				errorMessages.putAll(outputDataSourceBean.getErrorMessages());
				String key = errorMessages.keySet().toArray(new String[1])[0];
				String value = errorMessages.get(key);
				throw new StoreException("Fallita chiusura automatica della mail. Errore = " + value);							
			}
			
		}
		catch (Exception e) {
			throw new StoreException(e.getMessage());		
		}	
		
	}
	
	// Associa una mail inviata alla UD
	/*
	private void associaEmailUD(String idEmailIn, String idUIn, String codCategoriaReg, String siglaReg, String annoReg, String numeroReg, Date dataReg) throws Exception {
		
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		// Cerco il dettaglio della mail per leggere gli estremi della registrazione : idUd , categoriaRegUD, numRegUD , siglaRegUD , annoRegUD , tsRegUD
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------

		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		// Associo la mail all'UD ( nella T_REG_PROT_VS_EMAIL )
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		if (idUIn != null && !idUIn.equalsIgnoreCase("")) {
			// Inizializzo l'INPUT
			TRegProtVsEmailBean lTRegProtVsEmailSaveIn = new TRegProtVsEmailBean();
			lTRegProtVsEmailSaveIn.setAnnoReg(annoReg != null && !"".equals(annoReg) ? new Short(annoReg) : null);
			lTRegProtVsEmailSaveIn.setCategoriaReg(codCategoriaReg);
			lTRegProtVsEmailSaveIn.setIdEmail(idEmailIn);
			lTRegProtVsEmailSaveIn.setIdProvReg(idUIn);
			lTRegProtVsEmailSaveIn.setNumReg(numeroReg != null && !"".equals(numeroReg) ? new BigDecimal(numeroReg) : null);
			lTRegProtVsEmailSaveIn.setSiglaRegistro(siglaReg);
			lTRegProtVsEmailSaveIn.setTsReg(dataReg != null  ? (dataReg) : null);
			lTRegProtVsEmailSaveIn.setIdRegProtEmail(KeyGenerator.gen());

			// Eseguo il servizio
			try {
				AurigaMailService.getDaoTRegProtVsEmail().save(getLocale(), lTRegProtVsEmailSaveIn);
			} catch (Exception e) {
				String msgError = "Fallita associazione e-mail al documento.";
				throw new StoreException(msgError);
			}
		}

	}
	*/
	
	/**
	 * Recupera il file primario dallo storage e lo restituisce sotto forma di FilePrimarioBean
	 * 
	 * @param bean
	 * @param lAurigaLoginBean
	 * @return
	 * @throws StorageException
	 */
	private FilePrimarioBean retrieveFilePrimario(ProtocollazioneBean bean, AurigaLoginBean lAurigaLoginBean) throws StorageException {

		FilePrimarioBean filePrimarioBean = new FilePrimarioBean();
		if (bean.getFilePrimario() != null && StringUtils.isNotBlank(bean.getPercorsoFilePrimari()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			filePrimarioBean.setFile(bean.getFilePrimario()); 			
			filePrimarioBean.setPercorsoFilePrimario(bean.getPercorsoFilePrimari());
			filePrimarioBean.setNomeFilePrimario(bean.getNomeFilePrimario());
		} else if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			if (bean.getRemoteUriFilePrimario()) {
				// Il file è esterno
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				filePrimarioBean.setFile(out.getExtracted());
			} else {
				// File locale
				filePrimarioBean.setFile(StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario()));
			}
		}
		// Vers. con omissis
		if ((bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili()) && bean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getUriFile()) && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getNomeFile())) {
			if (bean.getFilePrimarioOmissis().getRemoteUri()) {
				// Il file è esterno
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getFilePrimarioOmissis().getUriFile());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				filePrimarioBean.setFileOmissis(out.getExtracted());
			} else {
				// File locale
				filePrimarioBean.setFileOmissis(StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile()));
			}
		}
		if(filePrimarioBean.getFile() != null || filePrimarioBean.getFileOmissis() != null) {
			return filePrimarioBean;
		}
		return null;
	}

	private void invioNotificaPostRegistrazione(ProtocollazioneBean bean, AurigaLoginBean lAurigaLoginBean) throws Exception {
		SenderBean sender = new SenderBean();

		sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		sender.setSubject("Notifica Automatica d'accettazione");
		sender.setBody("La richiesta inviata via email in data: "
				+ (bean.getDataEOraArrivo() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEOraArrivo()) : null)
				+ " è stata accettata e protocollata in data "
				+ (bean.getDataProtocollo() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataProtocollo()) : null));
		sender.setAccount(bean.getCasellaEmailDestinatario());// Set Account-
																// DaoTMailBoxAccount
		sender.setAddressFrom(bean.getCasellaEmailDestinatario());// Set
																	// Mittente
		List<String> indirizzoDest = new ArrayList<String>();
		indirizzoDest.add(bean.getIndirizzo());
		sender.setAddressTo(indirizzoDest);// il Mittente che ha spedito la mail
											// diventa il nuovo Destinatario
		sender.setIsHtml(false);
		sender.setIsPec(false);
		try {
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(getLocale(), sender);
			if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					throw new StoreException(output.getDefaultMessage());
				} else {
					logProtDS.error("Fallito invio notifica e-mail" + output.getDefaultMessage());
				}
			}
		} catch (Exception e) {
			logProtDS.error("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage());
		}
	}
	
	public AurigaInvioUDMailDatasource getAurigaInvioUDMailDatasource(LinkedHashMap<String, String> extraparams) {
		AurigaInvioUDMailDatasource lAurigaInvioUDMailDatasource = new AurigaInvioUDMailDatasource();
		lAurigaInvioUDMailDatasource.setSession(getSession());
		if(extraparams != null) {
			lAurigaInvioUDMailDatasource.setExtraparams(extraparams); 
		} else {
			lAurigaInvioUDMailDatasource.setExtraparams(getExtraparams());	
		}
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lAurigaInvioUDMailDatasource.setMessages(getMessages()); 		
		return lAurigaInvioUDMailDatasource;
	}	
	
	public void invioMailRicevutaRegistrazioneRichiestaAccessoAtti(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		LinkedHashMap<String, String> extraparams = new LinkedHashMap<String, String>();
		extraparams.put("tipoMail", "PEC");
		extraparams.put("finalita", "VISURA_SUE");
		InvioUDMailBean lInvioUDMailBean = getAurigaInvioUDMailDatasource(extraparams).callInvioRicevuta(bean);
		
		SenderBean sender = new SenderBean();
		sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		sender.setIsPec(true);	
		sender.setSubject(lInvioUDMailBean.getOggetto());
		sender.setBody(lInvioUDMailBean.getBodyHtml());
		sender.setIsHtml(true);
		sender.setAccount(lInvioUDMailBean.getMittente());
		sender.setAddressFrom(lInvioUDMailBean.getMittente());
		sender.setAliasAddressFrom(null);
		// Destinatari principali
		List<String> destinatari = new ArrayList<String>();
		String[] lStringDestinatari = IndirizziEmailSplitter.split(lInvioUDMailBean.getDestinatari());
		destinatari = Arrays.asList(lStringDestinatari);
		sender.setAddressTo(destinatari);
		// Destinatari CC
		if (StringUtils.isNotBlank(lInvioUDMailBean.getDestinatariCC())) {
			List<String> destinatariCC = new ArrayList<String>();
			String[] lStringDestinatariCC = IndirizziEmailSplitter.split(lInvioUDMailBean.getDestinatariCC());
			destinatariCC = Arrays.asList(lStringDestinatariCC);
			sender.setAddressCc(destinatariCC);
		}
		// Allegati
		List<SenderAttachmentsBean> listaAttachments = new ArrayList<SenderAttachmentsBean>();
		StampaFileBean lStampaFileBean = creaStampaDaModello(bean, null, "RICEVUTA_REGISTRAZIONE_VISURA_SUE", TipoModelloDoc.DOCX_CON_PLACEHOLDER.getValue(), "Ricevuta_protocollazione_richiesta_accesso_atti.pdf");
		if(lStampaFileBean != null) {
			SenderAttachmentsBean lSenderAttachmentsBean = new SenderAttachmentsBean();
			lSenderAttachmentsBean.setFile(StorageImplementation.getStorage().extractFile(lStampaFileBean.getUri()));
			lSenderAttachmentsBean.setFilename(lStampaFileBean.getNomeFile());
			lSenderAttachmentsBean.setFirmato(lStampaFileBean.getInfoFile().isFirmato());
			lSenderAttachmentsBean.setMimetype(lStampaFileBean.getInfoFile().getMimetype());
			lSenderAttachmentsBean.setOriginalName(lStampaFileBean.getNomeFile());
			listaAttachments.add(lSenderAttachmentsBean);
		}
		sender.setAttachments(listaAttachments);
		
		try {
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(locale, sender);
			if(output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}
		} catch (Exception e) {
			logProtDS.error(e.getMessage(), e);
			throw new StoreException("Fallito invio e-mail: " + e.getMessage());
		}
		
	}

	public ProtocollazioneBean aggiornaFilePrimario(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(bean.getIdDocPrimario());
		logProtDS.debug("#######INIZIO extractFile in(aggiornaFilePrimario)#######");
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario()));
		logProtDS.debug("#######FINE extractFile in(aggiornaFilePrimario)#######");

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		logProtDS.debug("#######INIZIO getInfoFromFile in(aggiornaFilePrimario)#######");
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		logProtDS.debug("#######FINE getInfoFromFile in(aggiornaFilePrimario)#######");

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		logProtDS.debug("#######INIZIO setProprietaGenericFile in(aggiornaFilePrimario)#######");
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		logProtDS.debug("#######FINE lRecuperoFile.extractfile in(aggiornaFilePrimario)#######");
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
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
		VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);

		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutput);
		}

		return bean;
	}
	
	public ProtocollazioneBean aggiornaFilePrimarioOmissis(ProtocollazioneBean bean) throws Exception {

		if(bean.getFilePrimarioOmissis() != null) {

			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
	
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
	
			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getIdDoc()) ? new BigDecimal(bean.getFilePrimarioOmissis().getIdDoc()) : null);
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile()));
	
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
	
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			logProtDS.debug("#######INIZIO setProprietaGenericFile in(aggiornaFilePrimarioOmissis)#######");
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			logProtDS.debug("#######FINE setProprietaGenericFile in(aggiornaFilePrimarioOmissis)#######");
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
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
			VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
	
			if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
				logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
				throw new StoreException(lVersionaDocumentoOutput);
			}
		}

		return bean;
	}
	
	public AllegatoProtocolloBean aggiornaFileAllegato(AllegatoProtocolloBean allegato) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(allegato.getIdDocAllegato());
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato()));

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.ALLEGATO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
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
		VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);

		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutput);
		}

		return allegato;
	}
	
	public AllegatoProtocolloBean aggiornaFileAllegatoOmissis(AllegatoProtocolloBean allegato) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(allegato.getIdDocOmissis());
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis()));

		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.ALLEGATO);
		GenericFile lGenericFile = new GenericFile();
		setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
		lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
		lGenericFile.setDisplayFilename(allegato.getNomeFileOmissis());
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
		VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);

		if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
			logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutput);
		}

		return allegato;
	}

	@Override
	public ProtocollazioneBean update(ProtocollazioneBean bean, ProtocollazioneBean oldvalue) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		boolean isRepertorio = getExtraparams().get("isRepertorio") != null && "true".equals(getExtraparams().get("isRepertorio"));
		boolean isRegistrazioneFattura = getExtraparams().get("isRegistrazioneFattura") != null
				&& "true".equals(getExtraparams().get("isRegistrazioneFattura"));
		boolean isBozza = getExtraparams().get("isBozza") != null && "true".equals(getExtraparams().get("isBozza"));
		boolean isIstanza = getExtraparams().get("isIstanza") != null && "true".equals(getExtraparams().get("isIstanza"));
		boolean isRichiestaAccessoAtti = getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
		boolean isCompilazioneModulo = getExtraparams().get("isCompilazioneModulo") != null && "true".equals(getExtraparams().get("isCompilazioneModulo"));
		boolean isModificaRegAccessoCivico = getExtraparams().get("isModificaRegAccessoCivico") != null && "true".equals(getExtraparams().get("isModificaRegAccessoCivico"));
		boolean isModificaDatiExtraIter = getExtraparams().get("isModificaDatiExtraIter") != null && "true".equals(getExtraparams().get("isModificaDatiExtraIter"));
		
		if(isModificaDatiExtraIter) {
			
			DmpkAttributiDinamiciSetattrdinamiciBean lBean = new DmpkAttributiDinamiciSetattrdinamiciBean();
			lBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
			lBean.setIduserlavoroin(StringUtils.isNotBlank(lAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(lAurigaLoginBean.getIdUserLavoro()) : null);
			lBean.setNometabellain("DMT_DOCUMENTS");
			lBean.setRowidin(bean.getRowidDoc());
			
			if(bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
				SezioneCache lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(), getSession());			
				StringWriter lStringWriter = new StringWriter();
				Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
				lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				lMarshaller.marshal(lSezioneCache, lStringWriter);
				lBean.setAttrvaluesxmlin(lStringWriter.toString());
			}
			
			DmpkAttributiDinamiciSetattrdinamici store = new DmpkAttributiDinamiciSetattrdinamici();
			StoreResultBean<DmpkAttributiDinamiciSetattrdinamiciBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
			if (result.isInError()){
				throw new StoreException(result);
			}
			
			return bean;
		}
		
		ArrayList<String> idUdDaCollegareList = new ArrayList<String>();

		// File lFilePrimario = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		lModificaDocumentoInBean.setCopiaExIdUD(bean.getIdUdNuovoComeCopia() != null ? String.valueOf(bean.getIdUdNuovoComeCopia().longValue()) : null);
		lModificaDocumentoInBean.setChiaveModelloSelezionato(bean.getPrefKeyModello());
		lModificaDocumentoInBean.setNomeModelloSelezionato(bean.getPrefNameModello());
		lModificaDocumentoInBean.setUseridModelloSelezionato(bean.getUseridModello());
		lModificaDocumentoInBean.setIdUOModelloSelezionato(bean.getIdUoModello());
		lModificaDocumentoInBean.setFlgTipoDocDiverso(bean.getFlgTipoDocDiverso() != null && "1".equals(bean.getFlgTipoDocDiverso()) ? new Integer(1) : null);
		lModificaDocumentoInBean.setIdTipoDocDaCopiare(bean.getIdTipoDocDaCopiare());
		lModificaDocumentoInBean.setTimestampGetData(bean.getTimestampGetData());
	
		// salvo il tipo di registrazione (#FlgTipoProv) : entrata (E), in
		// uscita (U) o interna/tra uffici (I)
		if (bean.getFlgTipoProv() != null/* && !isBozza*/) {
			TipoProvenienza lTipoProvenienza = null;
			if (bean.getFlgTipoProv().equalsIgnoreCase("E")) {
				lTipoProvenienza = TipoProvenienza.ENTRATA;
			} else if (bean.getFlgTipoProv().equalsIgnoreCase("U")) {
				lTipoProvenienza = TipoProvenienza.USCITA;
			} else if (bean.getFlgTipoProv().equalsIgnoreCase("I")) {
				lTipoProvenienza = TipoProvenienza.INTERNA;
			}
			lModificaDocumentoInBean.setFlgTipoProv(lTipoProvenienza);
		}
		
		boolean isSalvataggioProvvisorioInBozza = getExtraparams().get("flgSalvataggioProvvisorioInBozza") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvataggioProvvisorioInBozza"));
		boolean isSalvataggioProvvisorioPreCompleteTask = getExtraparams().get("flgSalvataggioProvvisorioPreCompleteTask") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvataggioProvvisorioPreCompleteTask"));
		boolean isSalvataggioDefinitivoPreCompleteTask = getExtraparams().get("flgSalvataggioDefinitivoPreCompleteTask") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvataggioDefinitivoPreCompleteTask"));
		if (isSalvataggioDefinitivoPreCompleteTask || isSalvataggioProvvisorioPreCompleteTask) {
			lModificaDocumentoInBean.setFlgFirstCallPreCompleteTask(new Integer(1));
			lModificaDocumentoInBean.setActivityName(getExtraparams().get("taskName"));
			lModificaDocumentoInBean.setEsitoTask(getExtraparams().get("esitoTask"));
			lModificaDocumentoInBean.setMsgEsitoTask(getExtraparams().get("msgEsitoTask"));
			if (bean.getListaAllegati() != null) {
				List<ValueBean> idDocTypeAllegati = new ArrayList<ValueBean>();
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()) {
					// devo controllare che ci sia il file
					boolean hasFile = StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato());
					boolean hasFileOmissis = lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis());
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getListaTipiFileAllegato()) && (hasFile || hasFileOmissis)) { 
						ValueBean lValueBean = new ValueBean();
						lValueBean.setValue(lAllegatoProtocolloBean.getListaTipiFileAllegato());
						idDocTypeAllegati.add(lValueBean);
					}
				}
				lModificaDocumentoInBean.setIdDocTypeAllegati(idDocTypeAllegati);
			}
		} else if (isSalvataggioProvvisorioInBozza) {
			lModificaDocumentoInBean.setActivityName(getExtraparams().get("taskName"));			
		}
		
		// usato se provengo da modifica fattura o repertorio
		boolean isRegistraAProtocolloGenerale = false;

		if ((isRegistrazioneFattura && (bean.getProtocolloGenerale() != null && bean.getProtocolloGenerale()))
				|| (isRepertorio && (bean.getProtocolloGenerale() != null && bean.getProtocolloGenerale())))
			isRegistraAProtocolloGenerale = true;

		if(bean.getListaTipiNumerazioneDaDare() != null && !bean.getListaTipiNumerazioneDaDare().isEmpty()) {
			lModificaDocumentoInBean.setTipoNumerazioni(bean.getListaTipiNumerazioneDaDare());			
		} else {
			// Salvo la TIPOLOGIA di NUMERAZIONE (SIGLA e CATEGORIA)
			List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();			
			// skip se è una proposta atto OR tipoProv null OR (tipoProv in (E,I)
			// AND nroProt valorizzato) OR (tipoProv = U and categoria = PG)
			boolean skipNumerazioniDaDare =  isPropostaAtto() || bean.getFlgTipoProv() == null || (isBozza && "NI".equals(bean.getTipoProtocollo())) || 
					(((bean.getFlgTipoProv().equals("E") || bean.getFlgTipoProv().equals("I")) && bean.getNroProtocollo() != null && !isModificaRegAccessoCivico && !"NI".equals(bean.getTipoProtocollo())) || 
					(bean.getFlgTipoProv().equals("U") && bean.getTipoProtocollo() != null && "PG".equals(bean.getTipoProtocollo())));

			// Se e' una RICHIESTA ACCESSO ATTI allora devo saltare sempre le numerazioni da dare
			if (isRichiestaAccessoAtti) {
				skipNumerazioniDaDare = true;
			}
			if(bean.getFlgAncheRepertorio() != null && bean.getFlgAncheRepertorio()) {
				skipNumerazioniDaDare = false;
			}
			if(isRegistraAProtocolloGenerale) {
				skipNumerazioniDaDare = false;
			}
			if (!skipNumerazioniDaDare) {		
				if(isModificaRegAccessoCivico) {
					String siglaRegAccessoCivico = ParametriDBUtil.getParametroDB(getSession(), "SIGLA_REGISTRO_ACCESSO_CIVICO");
					if(StringUtils.isNotBlank(siglaRegAccessoCivico)) {
						TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();			
						lTipoNumerazioneBean.setSigla(siglaRegAccessoCivico);
						lTipoNumerazioneBean.setCategoria("R");		
						listaTipiNumerazioni.add(lTipoNumerazioneBean);
					}
				}
				else if (isRegistrazioneFattura) {
					bean.setIsRegistroFatture(true);
					TipoNumerazioneBean lTipoProtocolloGenerale = creaTipoProtocolloGenerale(bean);
					if (lTipoProtocolloGenerale != null) {
						listaTipiNumerazioni.add(lTipoProtocolloGenerale);
					}
				}
				else if (isRepertorio) {
					bean.setIsRepertorio(true);
					TipoNumerazioneBean lTipoProtocolloGenerale = creaTipoProtocolloGenerale(bean);
					if (lTipoProtocolloGenerale != null) {
						listaTipiNumerazioni.add(lTipoProtocolloGenerale);
					}
				} 
				else if (bean.getFlgAncheRepertorio() != null && bean.getFlgAncheRepertorio()) {
					bean.setIsRepertorio(true);
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla(bean.getRepertorio());
					int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					int annoPassato = annoCorrente - 1;
					lTipoNumerazioneBean.setAnno(bean.getAnnoPassato() != null && bean.getAnnoPassato() ? String.valueOf(annoPassato) : String.valueOf(annoCorrente));
					lTipoNumerazioneBean.setCategoria("R");	
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);
				}
				// Registrazione ENTRATA o USCITA
				else if (bean.getFlgTipoProv() != null && (bean.getFlgTipoProv().equalsIgnoreCase("E") || bean.getFlgTipoProv().equalsIgnoreCase("U"))) {
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					lTipoNumerazioneBean.setSigla(null);
					lTipoNumerazioneBean.setCategoria("PG");
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);					
				}
				// Registrazione INTERNA/TRA UFFICI
				else if (bean.getFlgTipoProv() != null && bean.getFlgTipoProv().equalsIgnoreCase("I")) {
					TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();
					if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_REGISTRO_PG_X_PROT_INTERNA")) {
						lTipoNumerazioneBean.setSigla(null);
						lTipoNumerazioneBean.setCategoria("PG");
					} else {
						lTipoNumerazioneBean.setSigla("P.I.");
						lTipoNumerazioneBean.setCategoria("I");
					}			
					lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
					listaTipiNumerazioni.add(lTipoNumerazioneBean);
				}				
			}			
			if(listaTipiNumerazioni != null && listaTipiNumerazioni.size() > 0) {
				lModificaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioni);
			}			
		}
		
		boolean isModificaProtocolloRepertorio = bean.getNroProtocollo() != null && bean.getTipoProtocollo() != null && ("PG".equals(bean.getTipoProtocollo()) || "R".equals(bean.getTipoProtocollo()));
		boolean isProtocollazioneRepertoriazione = false;
		if(lModificaDocumentoInBean.getTipoNumerazioni() != null) {
			for(TipoNumerazioneBean lTipoNumerazioneBean : lModificaDocumentoInBean.getTipoNumerazioni()) {
				if(lTipoNumerazioneBean.getCategoria() != null && ("PG".equals(lTipoNumerazioneBean.getCategoria()) || "R".equals(lTipoNumerazioneBean.getCategoria()))) {
					isProtocollazioneRepertoriazione = true;
					break;
				}
			}			
		}
		if(isModificaProtocolloRepertorio || isProtocollazioneRepertoriazione) {
			// se sto protocollando o repertoriando
			controlloFirmePrimarioEAllegatiXNumerazione(bean);
			if(bean.getErroriFile() != null && !bean.getErroriFile().isEmpty()) {
				return bean;
			}
		}
		
		// Tipologie particolari A2A
		lModificaDocumentoInBean.setFlgMulta(bean.getFlgMulta() != null && bean.getFlgMulta() ? new Integer(1) : null);
		lModificaDocumentoInBean.setFlgDocContratti(bean.getFlgDocContratti() != null && bean.getFlgDocContratti() ? new Integer(1) : null);
		lModificaDocumentoInBean.setCodContratti(bean.getCodContratti());
		if(StringUtils.isNotBlank(bean.getFlgFirmeCompilateContratti()) && !"N.A.".equals(bean.getFlgFirmeCompilateContratti())) {
			lModificaDocumentoInBean.setFlgFirmeCompilateContratti(bean.getFlgFirmeCompilateContratti());
		} else {
			lModificaDocumentoInBean.setFlgFirmeCompilateContratti(null);
		}
		lModificaDocumentoInBean.setFlgDocContrattiConBarcode(bean.getFlgDocContrattiBarcode() != null && bean.getFlgDocContrattiBarcode() ? new Integer(1) : null);
		
		// Salvo l'OGGETTO
		lModificaDocumentoInBean.setOggetto(bean.getOggetto());
		
		if (!isRichiestaAccessoAttiRichiedenteInterno(bean)){
			// Salvo i MITTENTI
			salvaMittenti(bean, lModificaDocumentoInBean);
		}else {
			// Salvo i RICHIEDENTI INTERNI
			salvaRichiedentiInterni(bean, lModificaDocumentoInBean);
		}
		// Salvo i destinatari
		salvaDestinatari(bean, lModificaDocumentoInBean);
		// Salvo gli assegnatari dal tab assegnazione
		salvaAssegnatari(bean, lModificaDocumentoInBean);
		// Salvo la lista degli invii per conoscenza
		salvaInvioDestCC(bean, lModificaDocumentoInBean);
		// Salvo la lista delle altre U.O. coinvolte
		salvaAltreUoCoinvolte(bean, lModificaDocumentoInBean);
		// Salvo la lista dei documenti collegati
		salvaDocumentiCollegati(bean, lModificaDocumentoInBean);
		// Salvo la lista degli altri riferimenti
		salvaAltriRiferimenti(bean, lModificaDocumentoInBean);
		// Salvo la lista delle perizie
		salvaPerizie(bean, lModificaDocumentoInBean);
				
		// Salvo COD STATO DETT
		lModificaDocumentoInBean.setCodStatoDett(bean.getCodStatoDett());
		// Salvo COD STATO
		lModificaDocumentoInBean.setCodStato(bean.getCodStato());

		// Salvo la CLASSIFICA e FASCICOLAZIONE
		// se non arrivo da una proposta atto e quindi da un dettaglio UD devo passare sempre la variabile lista, perchè sarà sempre presente a maschera		
		if (!isPropostaAtto2()) {
			List<ClassificheFascicoliDocumentoBean> lListClassificheFascicoli = new ArrayList<ClassificheFascicoliDocumentoBean>();
			if (bean.getListaClassFasc() != null) {
				for (ClassificazioneFascicoloBean lClassificazioneFascicoloBean : bean.getListaClassFasc()) {
					ClassificheFascicoliDocumentoBean lClassificheFascicoliDocumentoBean = new ClassificheFascicoliDocumentoBean();
					lClassificheFascicoliDocumentoBean.setIdClassifica(lClassificazioneFascicoloBean.getIdClassifica());
					lClassificheFascicoliDocumentoBean.setIdFolderFascicolo(lClassificazioneFascicoloBean.getIdFolderFascicolo());
					lClassificheFascicoliDocumentoBean.setAnnoFascicolo(lClassificazioneFascicoloBean.getAnnoFascicolo());
					lClassificheFascicoliDocumentoBean.setNroFascicolo(lClassificazioneFascicoloBean.getNroFascicolo());
					lClassificheFascicoliDocumentoBean.setNroSottofascicolo(lClassificazioneFascicoloBean.getNroSottofascicolo());
					lClassificheFascicoliDocumentoBean.setNroInserto(lClassificazioneFascicoloBean.getNroInserto());
					if (lClassificazioneFascicoloBean.getFlgCapofila() != null && lClassificazioneFascicoloBean.getFlgCapofila()) {
						lClassificheFascicoliDocumentoBean.setTipoRelazione("CPF");
					}
					lListClassificheFascicoli.add(lClassificheFascicoliDocumentoBean);
				}
			}
			lModificaDocumentoInBean.setClassifichefascicoli(lListClassificheFascicoli);
		// se arrivo da una proposta atto devo passare la variabile solo se la lista è diversa da null, e quindi se nell'xml di configurazione è presente l'attributo CLASS_FASC
		} else if (bean.getListaClassFasc() != null) {
			List<ClassificheFascicoliDocumentoBean> lListClassificheFascicoli = new ArrayList<ClassificheFascicoliDocumentoBean>();
			for (ClassificazioneFascicoloBean lClassificazioneFascicoloBean : bean.getListaClassFasc()) {
				ClassificheFascicoliDocumentoBean lClassificheFascicoliDocumentoBean = new ClassificheFascicoliDocumentoBean();
				lClassificheFascicoliDocumentoBean.setIdClassifica(lClassificazioneFascicoloBean.getIdClassifica());
				lClassificheFascicoliDocumentoBean.setIdFolderFascicolo(lClassificazioneFascicoloBean.getIdFolderFascicolo());
				lClassificheFascicoliDocumentoBean.setAnnoFascicolo(lClassificazioneFascicoloBean.getAnnoFascicolo());
				lClassificheFascicoliDocumentoBean.setNroFascicolo(lClassificazioneFascicoloBean.getNroFascicolo());
				lClassificheFascicoliDocumentoBean.setNroSottofascicolo(lClassificazioneFascicoloBean.getNroSottofascicolo());
				lClassificheFascicoliDocumentoBean.setNroInserto(lClassificazioneFascicoloBean.getNroInserto());
				if (lClassificazioneFascicoloBean.getFlgCapofila() != null && lClassificazioneFascicoloBean.getFlgCapofila()) {
					lClassificheFascicoliDocumentoBean.setTipoRelazione("CPF");
				}
				lListClassificheFascicoli.add(lClassificheFascicoliDocumentoBean);
			}
			lModificaDocumentoInBean.setClassifichefascicoli(lListClassificheFascicoli);
		}

		// Salvo i FOLDER CUSTOM
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_FOLDER_CUSTOM") && !isPropostaAtto2()) {
			List<FolderCustom> lListFolderCustom = new ArrayList<FolderCustom>();
			if (bean.getListaFolderCustom() != null && bean.getListaFolderCustom().size() > 0) {
				for (FolderCustomBean lFolderCustomBean : bean.getListaFolderCustom()) {
					FolderCustom lFolderCustom = new FolderCustom();
					lFolderCustom.setId(lFolderCustomBean.getId());
					lFolderCustom.setPath(lFolderCustomBean.getPath());
					if (lFolderCustomBean.getFlgCapofila() != null && lFolderCustomBean.getFlgCapofila()) {
						lFolderCustom.setTipoRelazione("CPF");
					}
					lListFolderCustom.add(lFolderCustom);
				}
			}
			lModificaDocumentoInBean.setFolderCustom(lListFolderCustom);
		} else if (bean.getListaFolderCustom() != null && bean.getListaFolderCustom().size() > 0) {
			List<FolderCustom> lListFolderCustom = new ArrayList<FolderCustom>();
			for (FolderCustomBean lFolderCustomBean : bean.getListaFolderCustom()) {
				FolderCustom lFolderCustom = new FolderCustom();
				lFolderCustom.setId(lFolderCustomBean.getId());
				lFolderCustom.setPath(lFolderCustomBean.getPath());
				if (lFolderCustomBean.getFlgCapofila() != null && lFolderCustomBean.getFlgCapofila()) {
					lFolderCustom.setTipoRelazione("CPF");
				}
				lListFolderCustom.add(lFolderCustom);
			}
			lModificaDocumentoInBean.setFolderCustom(lListFolderCustom);
		}

		// Grado di risercatezza
		lModificaDocumentoInBean.setLivelloRiservatezza(bean.getLivelloRiservatezza() != null ? bean.getLivelloRiservatezza().intValue() + "" : null);
		// Data termine riservatezza
		if (bean.getLivelloRiservatezza() != null)
			lModificaDocumentoInBean.setTermineRiservatezza(bean.getDataRiservatezza());
		// Priorità
		lModificaDocumentoInBean.setPriorita(bean.getPrioritaRiservatezza());
		
		// Risposta urgente
		lModificaDocumentoInBean.setFlgRispostaUrgente(bean.getFlgRispostaUrgente() != null && bean.getFlgRispostaUrgente() ? new Integer(1) : null);
		
		// Richiesta accesso civico
		lModificaDocumentoInBean.setFlgAccessoCivicoSemplice(bean.getFlgRichAccCivSemplice() != null && bean.getFlgRichAccCivSemplice() ? new Integer(1) : null);
		lModificaDocumentoInBean.setFlgAccessoCivicoGeneralizzato(bean.getFlgRichAccCivGeneralizzato() != null && bean.getFlgRichAccCivGeneralizzato() ? new Integer(1) : null);
		
		// Documento riferimento
		DocumentoCollegato lDocumentoCollegato = new DocumentoCollegato();
		lDocumentoCollegato.setAnno(StringUtils.isNotBlank(bean.getAnnoDocRiferimento()) ? Integer.valueOf(bean.getAnnoDocRiferimento()) : null);
		lDocumentoCollegato.setNumero(bean.getNroDocRiferimento() != null ? bean.getNroDocRiferimento().intValue() : null);

		for (TipoProtocollo lTipoProtocollo : TipoProtocollo.values()) {
			if (lTipoProtocollo.toString().equals(bean.getSiglaDocRiferimento())) {
				lDocumentoCollegato.setTipo(lTipoProtocollo);
				if (lTipoProtocollo == TipoProtocollo.PROTOCOLLO_INTERNO) {
					lDocumentoCollegato.setSiglaRegistro("P.I.");
				}
			}
		}
		if (!(lDocumentoCollegato.getAnno() == null && lDocumentoCollegato.getNumero() == null && lDocumentoCollegato.getSiglaRegistro() == null))
			addDocumentoCollegato(lModificaDocumentoInBean, Arrays.asList(new DocumentoCollegato[] { lDocumentoCollegato }));
		// Collocazione fisica
		if (StringUtils.isNotBlank(bean.getNomeCollocazioneFisica())) {
			lModificaDocumentoInBean.setDescrizioneCollocazione(bean.getNomeCollocazioneFisica());
		}
		if (StringUtils.isNotBlank(bean.getIdToponimo())) {
			lModificaDocumentoInBean.setIdTopografico(bean.getIdToponimo());
		}

		lModificaDocumentoInBean.setFlgNoPubblPrimario(bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario() ? "1" : null);
		lModificaDocumentoInBean.setFlgDatiSensibiliPrimario(bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili() ? "1" : null);
		
		// Registrazioni da dare
		List<RegistroEmergenza> lListRegistrazioniDaDare = new ArrayList<RegistroEmergenza>();
		if (bean.getDataRegEmergenza() != null && bean.getNroRegEmergenza() != null && StringUtils.isNotBlank(bean.getRifRegEmergenza())) {
			// Registro emergenza
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			lRegistroEmergenza.setDataRegistrazione(bean.getDataRegEmergenza());
			lRegistroEmergenza.setNro(bean.getNroRegEmergenza().intValue() + "");
			lRegistroEmergenza.setRegistro(bean.getRifRegEmergenza());
			lRegistroEmergenza.setIdUserReg(bean.getIdUserRegEmergenza());
			lRegistroEmergenza.setIdUoReg(bean.getIdUoRegEmergenza());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);
		}
		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)		
		/*
		if (!isRichiestaAccessoAttiRichiedenteInterno(bean) && StringUtils.isNotBlank(bean.getNroProtocolloPregresso()) && (StringUtils.isNotBlank(bean.getAnnoProtocolloPregresso()) || (bean.getDataProtocolloPregresso() != null))) {
			//Registrazione protocollo PG@Web
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			lRegistroEmergenza.setDataRegistrazione(bean.getDataProtocolloPregresso());
			lRegistroEmergenza.setFisso("PG");
			lRegistroEmergenza.setRegistro(null);
			lRegistroEmergenza.setAnno(bean.getAnnoProtocolloPregresso());
			lRegistroEmergenza.setNro(bean.getNroProtocolloPregresso());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);			
		}
		*/
		if(isRichiestaAccessoAtti()) {
			if (StringUtils.isNotBlank(bean.getNumeroPraticaSuSistUfficioRichiedente()) && StringUtils.isNotBlank(bean.getAnnoPraticaSuSistUfficioRichiedente())) {
				// Salvo dati pratica su ufficio richiedente
				RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
				lRegistroEmergenza.setFisso("A");
				lRegistroEmergenza.setRegistro(bean.getSiglaPraticaSuSistUfficioRichiedente());
				lRegistroEmergenza.setAnno(bean.getAnnoPraticaSuSistUfficioRichiedente());
				lRegistroEmergenza.setNro(bean.getNumeroPraticaSuSistUfficioRichiedente());
				lListRegistrazioniDaDare.add(lRegistroEmergenza);
			}	
		}
				
		// se la lista è vuota in modifica non passo nulla altrimenti annullo le registrazioni precedenti
		if(lListRegistrazioniDaDare.size() > 0) {
			lModificaDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaDare);
		}
		
		// Altri dati
		lModificaDocumentoInBean.setNote(bean.getNote());
		lModificaDocumentoInBean.setDataStesura(bean.getDataDocumento());
		lModificaDocumentoInBean.setDataSpedizioneCartaceo(bean.getDataSpedizioneCartaceo());
		
		// Data di arrivo al protocollo
		lModificaDocumentoInBean.setDataArrivoProtocollo(bean.getDataArrivoProtocollo());
		
		// Tipo documento
		lModificaDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
		
		if(isIstanza) {	
			
			lModificaDocumentoInBean.setDataArrivo(bean.getDataEOraArrivo());
			
			// Mezzo trasmissione
			lModificaDocumentoInBean.setMessoTrasmissione(bean.getMezzoTrasmissione());
			if(bean.getMezzoTrasmissione() != null && isCodiceRaccomandata(bean.getMezzoTrasmissione())){
				if (bean.getNroRaccomandata() != null) {
					lModificaDocumentoInBean.setNroRaccomandata(bean.getNroRaccomandata());
				}
				if (bean.getDataRaccomandata() != null) {
					lModificaDocumentoInBean.setDtRaccomandata(bean.getDataRaccomandata());
				}
				if (StringUtils.isNotBlank(bean.getNroListaRaccomandata())) {
					lModificaDocumentoInBean.setNroListaRaccomandata(bean.getNroListaRaccomandata());
				}
			}
			
			if (StringUtils.isNotBlank(bean.getSupportoOriginale())) {
				lModificaDocumentoInBean.setSupportoOriginale(bean.getSupportoOriginale());	
			}
			
		} else {	
		
			// Solo prot entrata - prot ricevuto
			if (bean.getFlgTipoProv() != null && bean.getFlgTipoProv().equalsIgnoreCase("E")) {
				ProtocolloRicevuto lProtocolloRicevuto = new ProtocolloRicevuto();
				if (StringUtils.isNotBlank(bean.getNroProtRicevuto()) && (StringUtils.isNotBlank(bean.getAnnoProtRicevuto()) || bean.getDataProtRicevuto() != null)) {
					lProtocolloRicevuto.setOrigine(bean.getRifOrigineProtRicevuto());
					lProtocolloRicevuto.setNumero(bean.getNroProtRicevuto());
					lProtocolloRicevuto.setAnno(bean.getAnnoProtRicevuto());
					lProtocolloRicevuto.setData(bean.getDataProtRicevuto());
				}
				lModificaDocumentoInBean.setProtocolloRicevuto(lProtocolloRicevuto);
				lModificaDocumentoInBean.setDataArrivo(bean.getDataEOraArrivo());
				// Mezzo trasmissione
				lModificaDocumentoInBean.setMessoTrasmissione(bean.getMezzoTrasmissione());			
				if(bean.getMezzoTrasmissione() != null && isCodiceRaccomandata(bean.getMezzoTrasmissione()) //"R".equals(bean.getMezzoTrasmissione())
						) 
				{
					if (bean.getNroRaccomandata() != null) {
						lModificaDocumentoInBean.setNroRaccomandata(bean.getNroRaccomandata());
					}
					if (bean.getDataRaccomandata() != null) {
						lModificaDocumentoInBean.setDtRaccomandata(bean.getDataRaccomandata());
					}
					if (StringUtils.isNotBlank(bean.getNroListaRaccomandata())) {
						lModificaDocumentoInBean.setNroListaRaccomandata(bean.getNroListaRaccomandata());
					}
				}			
			} else if (bean.getDataEOraRicezione() != null) {
				// Salvo al data e ora ricezione delle istanze CED/AUTOTUTELA
				lModificaDocumentoInBean.setDataArrivo(bean.getDataEOraRicezione());
			}
			
			if (isAttivoProtocolloWizard(bean)) {
				lModificaDocumentoInBean.setFlgSkipControlliCartaceo(bean.getFlgSkipControlliCartaceo() != null && bean.getFlgSkipControlliCartaceo() ? "1" : null);
				if (StringUtils.isNotBlank(bean.getSupportoOriginale())) {
					lModificaDocumentoInBean.setSupportoOriginale(bean.getSupportoOriginale());	
				}
			}
		
		}
		
		// Data spedizione
		lModificaDocumentoInBean.setDataEOraSpedizione(bean.getDataEOraSpedizione());

		// Firmatari
		lModificaDocumentoInBean.setListaNominativiFirmaOlografa(bean.getListaNominativiFirmaOlografa());
		
		if (StringUtils.isNotBlank(bean.getMotivoVarDatiReg())) {
			lModificaDocumentoInBean.setMotiviModificaDatiReg(bean.getMotivoVarDatiReg());
		}
		
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();

		// Salvo i file ALLEGATI
		AllegatiBean lAllegatiBean = salvaAllegatiInUpdateDocumento(bean, lAurigaLoginBean, idUdDaCollegareList, lDocumentConfiguration, lModificaDocumentoInBean, list, false);

		// Salvo il file PRIMARIO
		FilePrimarioBean lFilePrimarioBean = salvaPrimarioInUpdateDocumento(bean, lAurigaLoginBean, lDocumentConfiguration, list);

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();

		salvaAltriSoggettiEsterni(bean, lModificaDocumentoInBean);
		
		// Ricercatore incaricato visura
		lModificaDocumentoInBean.setIdRicercatoreVisuraSUE(bean.getIdRicercatoreVisuraSUE());
		
		// Federico Cacco 13.06.2017
		// Salvo i dati di richiesta accesso atti
		salvaListaAttiRichiestaAccessoAtti(bean, lModificaDocumentoInBean);

		Map<String, Object> valori = bean.getValori() != null ? bean.getValori() : new HashMap<String, Object>();
		Map<String, String> tipiValori = bean.getTipiValori() != null ? bean.getTipiValori() : new HashMap<String, String>();
		SezioneCache sezioneCacheAttributiDinamici = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, valori, tipiValori, getSession());
		salvaAttributiCustom(bean, sezioneCacheAttributiDinamici);
		lModificaDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
//		logProtDS.debug(new XmlUtilitySerializer().bindXmlCompleta(lModificaDocumentoInBean));  
		
		// Richiesta accesso civico
		lModificaDocumentoInBean.setFlgPresentiControinteressati(bean.getFlgPresentiControinteressati());
		List<ControinteressatiXmlBean> listaControinteressati = new ArrayList<ControinteressatiXmlBean>();
		if(bean.getFlgPresentiControinteressati() != null && "SI".equalsIgnoreCase(bean.getFlgPresentiControinteressati())) {
			if(bean.getListaControinteressati() != null && bean.getListaControinteressati().size() > 0) {
				for(ControinteressatoBean item : bean.getListaControinteressati()) {
					ControinteressatiXmlBean controinteressatiXmlBean = new ControinteressatiXmlBean();
					controinteressatiXmlBean.setTipoSoggetto(item.getTipoSoggetto());
					controinteressatiXmlBean.setDenominazione(item.getDenominazione());
					controinteressatiXmlBean.setCodFiscale(item.getCodFiscale());
					controinteressatiXmlBean.setpIva(item.getpIva());
					controinteressatiXmlBean.setNote(item.getNote());
					listaControinteressati.add(controinteressatiXmlBean);
				}
			}
		}
		lModificaDocumentoInBean.setListaControinteressati(listaControinteressati);
		
		String lStringXml = null;
		try {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lStringXml = lXmlUtilitySerializer.bindXmlCompleta(lModificaDocumentoInBean);
			logProtDS.debug("lModificaDocumentoInBean: " + lStringXml);
		} catch (Exception e) {
			logProtDS.warn(e);
		} 
		
		CreaModDocumentoOutBean lModificaDocumentoOutBean = null;

		if (isVecchiaPropostaAtto2Milano()) {

			CreaModAttoInBean lModificaAttoMilanoInBean = new CreaModAttoInBean();
			ConvertUtils.register(new DateConverter(), Date.class);
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lModificaAttoMilanoInBean, lModificaDocumentoInBean);

			// lModificaAttoMilanoInBean.setModificatoDispositivoAtto(bean.getModificatoDispositivoAtto());
			lModificaAttoMilanoInBean.setSceltaIter(bean.getSceltaIter());
			lModificaAttoMilanoInBean.setFlgRichParereRevisori(bean.getFlgRichParereRevisori() != null && bean.getFlgRichParereRevisori() ? Flag.SETTED
					: Flag.NOT_SETTED);
			lModificaAttoMilanoInBean.setSiglaAttoRifSubImpegno(bean.getSiglaAttoRifSubImpegno());
			lModificaAttoMilanoInBean.setNumeroAttoRifSubImpegno(bean.getNumeroAttoRifSubImpegno());
			lModificaAttoMilanoInBean.setAnnoAttoRifSubImpegno(bean.getAnnoAttoRifSubImpegno());
			lModificaAttoMilanoInBean.setDataAttoRifSubImpegno(bean.getDataAttoRifSubImpegno());

			lModificaDocumentoOutBean = lGestioneDocumenti.modificadocumento(getLocale(), lAurigaLoginBean, bean.getIdUd(), bean.getIdDocPrimario(),
					lModificaAttoMilanoInBean, lFilePrimarioBean, lAllegatiBean);

		} else {

			lModificaDocumentoOutBean = lGestioneDocumenti.modificadocumento(getLocale(), lAurigaLoginBean, bean.getIdUd(), bean.getIdDocPrimario(),
					lModificaDocumentoInBean, lFilePrimarioBean, lAllegatiBean);

		}

		if (lModificaDocumentoOutBean.getDefaultMessage() != null) {
			throw new StoreException(lModificaDocumentoOutBean);
		}

		invioNotificheAssegnazioneInvioCC(bean, lModificaDocumentoInBean, true);

		String result = "";
		String[] attributes = new String[2];
		attributes[0] = lModificaDocumentoOutBean.getNumero() != null ? lModificaDocumentoOutBean.getNumero().toString() : "";
		attributes[1] = lModificaDocumentoOutBean.getAnno() != null ? lModificaDocumentoOutBean.getAnno().toString() : "";
		if(isRichiestaAccessoAtti) {
			result = "Richiesta accesso atti modificata con successo";
		} else if (lModificaDocumentoInBean.getFlgTipoProv() == null) {
			result = "Documento modificato con successo";
		} else {
			String userLanguage = getLocale().getLanguage();
			result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_modifica_registrazione_esito_OK_value", attributes);
		}
		bean.setDataProtocollo(lModificaDocumentoOutBean.getData());
		bean.setNroProtocollo(new BigDecimal(lModificaDocumentoOutBean.getNumero() + ""));
		bean.setIdUd(new BigDecimal(lModificaDocumentoOutBean.getIdUd() + ""));
		bean.setFileInErrors(lModificaDocumentoOutBean.getFileInErrors());
		if (lModificaDocumentoOutBean.getFileInErrors() != null && lModificaDocumentoOutBean.getFileInErrors().size() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(result);
			for (String lStrFileInError : lModificaDocumentoOutBean.getFileInErrors().values()) {
				lStringBuffer.append("; " + lStrFileInError);
			}
			addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
		} else if (!isPropostaAtto()) {
			addMessage(result, "", MessageType.INFO);			
		}
		if (lModificaDocumentoOutBean.getSalvataggioMetadatiError() != null && lModificaDocumentoOutBean.getSalvataggioMetadatiError()) {
			addMessage("Si è verificato un errore durante il salvataggio dei metadati su SharePoint", "", MessageType.WARNING);
		}
		
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_TIMBRATURA_FILE_POST_REG") && !isPropostaAtto() && !isBozza) {
			apposizioneTimbroPostReg(bean);
		}
		
		return bean;
	}

	private FilePrimarioBean salvaPrimarioInUpdateDocumento(ProtocollazioneBean bean, AurigaLoginBean lAurigaLoginBean, DocumentConfiguration lDocumentConfiguration, List<AttachAndPosizioneBean> list) throws StorageException, Exception, ParseException {
		FilePrimarioBean lFilePrimarioBean = retrieveFilePrimario(bean, lAurigaLoginBean);
		if (lFilePrimarioBean != null) {
			if(lFilePrimarioBean.getFile() != null) {				
				lFilePrimarioBean.setFlgSostituisciVerPrec(bean.getFlgSostituisciVerPrec());
				MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					logProtDS.debug("#######INIZIO lRecuperoFile.extractfile in(salvaPrimarioInUpdateDocumento)");
					File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
					logProtDS.debug("#######FINE lRecuperoFile.extractfile in(salvaPrimarioInUpdateDocumento)");
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), bean.getNomeFilePrimario(), false, null);
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getNomeFilePrimario());
					}
				}

				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFile = new GenericFile();
				logProtDS.debug("#######INIZIO setProprietaGenericFile in(salvaPrimarioInUpdateDocumento)#######");
				setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
				logProtDS.debug("#######FINE setProprietaGenericFile in(salvaPrimarioInUpdateDocumento)#######");
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
				lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
				lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
				lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBean.isDaScansione()) {
					lGenericFile.setDaScansione(Flag.SETTED);
					lGenericFile.setDataScansione(new Date());
					lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				if (StringUtils.isNotBlank(bean.getIdUDScansione())) {
					lGenericFile.setIdUdScansioneProv(bean.getIdUDScansione());
				}
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
				lFilePrimarioBean.setIdDocumento(bean.getIdDocPrimario());
				lFilePrimarioBean.setIsNewOrChanged(bean.getIdDocPrimario() == null || (bean.getIsDocPrimarioChanged() != null && bean.getIsDocPrimarioChanged()));
				lFilePrimarioBean.setInfo(lFileInfoBean);
				if (StringUtils.isNotBlank(bean.getIdAttachEmailPrimario())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
					lAttachAndPosizioneBean.setPosizione(0);
					list.add(lAttachAndPosizioneBean);
				}
			}
			if(lFilePrimarioBean.getFileOmissis() != null && bean.getFilePrimarioOmissis() != null) {	
				lFilePrimarioBean.setFlgSostituisciVerPrecOmissis(bean.getFilePrimarioOmissis().getFlgSostituisciVerPrec());
				MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = bean.getFilePrimarioOmissis().getInfoFile();
				if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
					File lFileOmissis = StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile());
					lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileOmissis.toURI().toString(), bean.getFilePrimarioOmissis().getNomeFile(), false, null);
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getFilePrimarioOmissis().getNomeFile() + " (vers. con omissis)");
					}
				}
				FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
				lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFileOmissis = new GenericFile();
				logProtDS.debug("#######INIZIO setProprietaGenericFile omissis in(salvaPrimarioInUpdateDocumento)#######");
				setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
				logProtDS.debug("#######FINE setProprietaGenericFile omissis in(salvaPrimarioInUpdateDocumento)#######");
				lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
				lGenericFileOmissis.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
				lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
				lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
					lGenericFileOmissis.setDaScansione(Flag.SETTED);
					lGenericFileOmissis.setDataScansione(new Date());
					lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				lFilePrimarioBean.setIdDocumentoOmissis(bean.getFilePrimarioOmissis().getIdDoc() != null ? new BigDecimal(bean.getFilePrimarioOmissis().getIdDoc()) : null);
				lFilePrimarioBean.setIsNewOrChangedOmissis(StringUtils.isBlank(bean.getFilePrimarioOmissis().getIdDoc()) || (bean.getFilePrimarioOmissis().getIsChanged() != null && bean.getFilePrimarioOmissis().getIsChanged()));
				lFilePrimarioBean.setInfoOmissis(lFileInfoBeanOmissis);				
			}
		}
		return lFilePrimarioBean;
	}

	private AllegatiBean salvaAllegatiInUpdateDocumento(ProtocollazioneBean bean, AurigaLoginBean lAurigaLoginBean, ArrayList<String> idUdDaCollegareList, DocumentConfiguration lDocumentConfiguration, CreaModDocumentoInBean lModificaDocumentoInBean, List<AttachAndPosizioneBean> list, boolean skipAggiornaMetadatiAllegatiNonModificati) throws StorageException, Exception, ParseException {
		List<AllegatoProtocolloBean> listaAllegati = bean.getListaAllegati();
		if (bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario()) {
			AllegatoProtocolloBean filePrimarioVerPubbl = bean.getListaFilePrimarioVerPubbl() != null && bean.getListaFilePrimarioVerPubbl().size() > 0 ? bean
					.getListaFilePrimarioVerPubbl().get(0) : null;
			if (filePrimarioVerPubbl != null) {
				String idTipoDocAllVerPubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_TIPO_DOC_ALL_VER_PUBBL");
				if (idTipoDocAllVerPubbl != null && !"".equals(idTipoDocAllVerPubbl)) {
					filePrimarioVerPubbl.setIdTipoFileAllegato(idTipoDocAllVerPubbl);
					filePrimarioVerPubbl.setListaTipiFileAllegato(idTipoDocAllVerPubbl);
				}
				String nomeDocAllVerPubbl = ParametriDBUtil.getParametroDB(getSession(), "NOME_DOC_ALL_VER_PUBBL");
				if (nomeDocAllVerPubbl != null && !"".equals(nomeDocAllVerPubbl)) {
					filePrimarioVerPubbl.setDescrizioneFileAllegato(nomeDocAllVerPubbl);
				}
				filePrimarioVerPubbl.setFlgProvEsterna(false);
				filePrimarioVerPubbl.setFlgParteDispositivo(true);
				filePrimarioVerPubbl.setFlgNoPubblAllegato(false);
				filePrimarioVerPubbl.setFlgPubblicaSeparato(false);
				filePrimarioVerPubbl.setFlgDatiProtettiTipo1(false);
				filePrimarioVerPubbl.setFlgDatiProtettiTipo2(false);
				filePrimarioVerPubbl.setFlgDatiProtettiTipo3(false);
				filePrimarioVerPubbl.setFlgDatiProtettiTipo4(false);
				if (listaAllegati == null) {
					listaAllegati = new ArrayList<AllegatoProtocolloBean>();
				}
				listaAllegati.add(0, filePrimarioVerPubbl);
			}
		}
		
		if (isAppendRelazioniVsUD(bean)) {
			lModificaDocumentoInBean.setAppendRelazioniVsUD("1");
		}

		AllegatiBean lAllegatiBean = null;
		if (listaAllegati != null) {			
			List<Boolean> flgNoModificaDati = new ArrayList<Boolean>();
			List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgProvEsterna = new ArrayList<Boolean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();			
			List<Boolean> flgPubblicaSeparato = new ArrayList<Boolean>();	
			List<String> nroPagFileUnione = new ArrayList<String>();
			List<String> nroPagFileUnioneOmissis = new ArrayList<String>();			
			List<Boolean> flgDatiProtettiTipo1 = new ArrayList<Boolean>();	
			List<Boolean> flgDatiProtettiTipo2 = new ArrayList<Boolean>();	
			List<Boolean> flgDatiProtettiTipo3 = new ArrayList<Boolean>();	
			List<Boolean> flgDatiProtettiTipo4 = new ArrayList<Boolean>();	
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();
			List<Boolean> flgSostituisciVerPrec = new ArrayList<Boolean>();
			List<Boolean> flgOriginaleCartaceo = new ArrayList<Boolean>();
			List<Boolean> flgCopiaSostitutiva = new ArrayList<Boolean>();
			List<Boolean> flgGenAutoDaModello = new ArrayList<Boolean>();
			List<String> idUdFrom = new ArrayList<String>();
			List<String> idUdAllegato = new ArrayList<String>();
			// Vers. con omissis
			List<BigDecimal> idDocumentoOmissis = new ArrayList<BigDecimal>();
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<Boolean> isNewOrChangedOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();			
			List<Boolean> flgSostituisciVerPrecOmissis = new ArrayList<Boolean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : listaAllegati) {
				File lFile = null;
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if (allegato.getRemoteUri() != null && allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						logProtDS.debug("#######INIZIO lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						logProtDS.debug("#######FINE lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						lFile = out.getExtracted();
					} else {
						// File locale
						logProtDS.debug("#######INIZIO lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
						logProtDS.debug("#######FINE lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
					}
					if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {						
						logProtDS.debug("#######INIZIO getInfoFromFile in(salvaAllegatiInUpdateDocumento)#######");
						allegato.setInfoFile(new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null));
						logProtDS.debug("#######FINE getInfoFromFile in(salvaAllegatiInUpdateDocumento)#######");
						if (allegato.getInfoFile() == null || StringUtils.isBlank(allegato.getInfoFile().getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
				}				
				boolean flgGenDaModelloDaFirmare = allegato.getFlgGenDaModelloDaFirmare() != null && allegato.getFlgGenDaModelloDaFirmare();
				boolean flgGenDaModelloFirmato = allegato.getInfoFile() != null && allegato.getInfoFile().isFirmato();
				// se ho un nuovo allegato (senza idDoc) generato da modello che era da firmare ma non è stato firmato quindi lo ignoro (così non lo salvo)
				if(allegato.getIdDocAllegato() == null && flgGenDaModelloDaFirmare && !flgGenDaModelloFirmato) {
					logProtDS.error("ATTENZIONE: ho un nuovo allegato (senza idDoc) generato da modello che era da firmare ma non è stato firmato quindi lo ignoro (così non lo salvo) -> " + allegato.getNomeFileAllegato());
					continue;
				}
				flgNoModificaDati.add(allegato.getFlgNoModificaDati());				
				idDocumento.add(allegato.getIdDocAllegato());
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgProvEsterna.add(allegato.getFlgProvEsterna());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				// non dobbiamo più salvare l'attributo SAVED_IN_TASK_ID_Doc
//				if (allegato.getIdDocAllegato() == null) {
					// salvo l'idTask solo se è un allegato inserito in quella attività e se l'attività è readonly o nell'xml di configurazione c'è l'attributo #ALLEGATI_PARTE_INTEGRANTE non editabile
//					idTask.add(getExtraparams().get("idTaskCorrente"));
//				} else {
//					idTask.add(allegato.getIdTask());
//				}
				idTask.add(null);
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());		
				flgPubblicaSeparato.add(allegato.getFlgPubblicaSeparato());
				if(allegato.getFlgPaginaFileUnione() != null && "n.ro".equals(allegato.getFlgPaginaFileUnione())) {
					nroPagFileUnione.add(allegato.getNroPagFileUnione());
					nroPagFileUnioneOmissis.add(allegato.getNroPagFileUnioneOmissis());		
				} else {
					nroPagFileUnione.add(null);
					nroPagFileUnioneOmissis.add(null);								
				}		
				flgDatiProtettiTipo1.add(allegato.getFlgDatiProtettiTipo1());	
				flgDatiProtettiTipo2.add(allegato.getFlgDatiProtettiTipo2());	
				flgDatiProtettiTipo3.add(allegato.getFlgDatiProtettiTipo3());	
				flgDatiProtettiTipo4.add(allegato.getFlgDatiProtettiTipo4());	
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				flgSostituisciVerPrec.add(allegato.getFlgSostituisciVerPrec());
				flgOriginaleCartaceo.add(allegato.getFlgOriginaleCartaceo());
				flgCopiaSostitutiva.add(allegato.getFlgCopiaSostitutiva());
				flgGenAutoDaModello.add(allegato.getFlgGenAutoDaModello());					
				idUdFrom.add(allegato.getIdUdAppartenenza());
				idUdAllegato.add(allegato.getIdUdAllegato());
				if (lFile != null) {
					isNull.add(false);
					isNewOrChanged.add(allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged()));					
					if (allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged())){
						fileAllegati.add(lFile);
					}					
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					logProtDS.debug("#######INIZIO setProprietaGenericFile in(salvaAllegatiInUpdateDocumento)#######");
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					logProtDS.debug("#######FINE setProprietaGenericFile in(salvaAllegatiInUpdateDocumento)#######");
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					if (StringUtils.isNotBlank(allegato.getIdUDScansione())) {
						lGenericFile.setIdUdScansioneProv(allegato.getIdUDScansione());
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					isNull.add(true);
					isNewOrChanged.add(null);
					info.add(new FileInfoBean());
				}	
				// Vers. con omissis
				idDocumentoOmissis.add(allegato.getIdDocOmissis());
				displayFilenameOmissis.add(allegato.getNomeFileOmissis());					
				flgSostituisciVerPrecOmissis.add(allegato.getFlgSostituisciVerPrecOmissis());
				File lFileAllegatoOmissis = null;
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) && StringUtils.isNotBlank(allegato.getUriFileOmissis()) && StringUtils.isNotBlank(allegato.getNomeFileOmissis())) {					
					if (allegato.getRemoteUriOmissis() != null && allegato.getRemoteUriOmissis()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileOmissis());
						logProtDS.debug("#######INIZIO lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						logProtDS.debug("#######FINE lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						lFileAllegatoOmissis = out.getExtracted();
					} else {
						// File locale
						logProtDS.debug("#######INIZIO lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
						lFileAllegatoOmissis = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
						logProtDS.debug("#######FINE lRecuperoFile.extractfile in(salvaAllegatiInUpdateDocumento)#######");
					}
					if (allegato.getInfoFileOmissis() == null || StringUtils.isBlank(allegato.getInfoFileOmissis().getImpronta())) {
						allegato.setInfoFileOmissis(new InfoFileUtility().getInfoFromFile(lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null));
						if (allegato.getInfoFileOmissis() == null || StringUtils.isBlank(allegato.getInfoFileOmissis().getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
				}
				if (lFileAllegatoOmissis != null) {
					isNullOmissis.add(false);
					isNewOrChangedOmissis.add(allegato.getIdDocOmissis() == null || (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis()));					
					if (allegato.getIdDocOmissis() == null || (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis())){					
						fileAllegatiOmissis.add(lFileAllegatoOmissis);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();					
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					logProtDS.debug("#######INIZIO setProprietaGenericFile in(salvaAllegatiInUpdateDocumento)#######");
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					logProtDS.debug("#######FINE setProprietaGenericFile in(salvaAllegatiInUpdateDocumento)#######");
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					isNullOmissis.add(true);
					isNewOrChangedOmissis.add(null);
					infoOmissis.add(new FileInfoBean());
				}					
				if (StringUtils.isNotBlank(allegato.getIdAttachEmail())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				// Federico Cacco 22.06.2016
				// Integro la lista dei documenti da importare
				if ((allegato.getCollegaDocumentoImportato() != null) && (allegato.getCollegaDocumentoImportato())
						&& (StringUtils.isNotBlank(allegato.getIdUdAppartenenza()))) {
					if (!idUdDaCollegareList.contains(allegato.getIdUdAppartenenza())) {
						idUdDaCollegareList.add(allegato.getIdUdAppartenenza());
					}
				}
				i++;
			}
			// Federico Cacco 22.06.2016
			// Inserisco tutti i documenti da importare
			List<DocumentoCollegato> listaDocumentiCollegati = new ArrayList<DocumentoCollegato>();
			for (String idUdDaCollegare : idUdDaCollegareList) {
				DocumentoCollegato documentoCollegato = new DocumentoCollegato();
				documentoCollegato.setIdUd(idUdDaCollegare);
				documentoCollegato.setcValue("C");
				documentoCollegato.setPrcValue("PRC");
				listaDocumentiCollegati.add(documentoCollegato);
			}
			if (listaDocumentiCollegati.size() > 0) {
				addDocumentoCollegato(lModificaDocumentoInBean, listaDocumentiCollegati);
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setFlgSalvaOrdAllegati(getExtraparams().get("flgSalvaOrdAllegati") != null && "true".equals(getExtraparams().get("flgSalvaOrdAllegati")));
			lAllegatiBean.setFlgNoModificaDati(flgNoModificaDati);
			lAllegatiBean.setIdDocumento(idDocumento);
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgProvEsterna(flgProvEsterna);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);	
			lAllegatiBean.setFlgPubblicaSeparato(flgPubblicaSeparato);				
			lAllegatiBean.setNroPagFileUnione(nroPagFileUnione);
			lAllegatiBean.setNroPagFileUnioneOmissis(nroPagFileUnioneOmissis);
			lAllegatiBean.setFlgDatiProtettiTipo1(flgDatiProtettiTipo1);
			lAllegatiBean.setFlgDatiProtettiTipo2(flgDatiProtettiTipo2);
			lAllegatiBean.setFlgDatiProtettiTipo3(flgDatiProtettiTipo3);
			lAllegatiBean.setFlgDatiProtettiTipo4(flgDatiProtettiTipo4);
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);
			lAllegatiBean.setFlgSostituisciVerPrec(flgSostituisciVerPrec);
			lAllegatiBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo);
			lAllegatiBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva);
			lAllegatiBean.setFlgGenAutoDaModello(flgGenAutoDaModello);			
			lAllegatiBean.setIdUdFrom(idUdFrom);		
			lAllegatiBean.setIdUdAllegato(idUdAllegato);
			lAllegatiBean.setIdDocumentoOmissis(idDocumentoOmissis);
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setIsNewOrChangedOmissis(isNewOrChangedOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);			
			lAllegatiBean.setFlgSostituisciVerPrecOmissis(flgSostituisciVerPrecOmissis);
			lAllegatiBean.setFlgSkipAggiornaMetadatiAllegatiNonModificati(skipAggiornaMetadatiAllegatiNonModificati);
		}
		return lAllegatiBean;
	}

	private void salvaAssegnatari(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		if (!isPropostaAtto2()) {
			List<AssegnatariBean> lListAssegnatari = new ArrayList<AssegnatariBean>();
			// Aggiungo quelli da destinatari e mittenti se ce ne sono
			if (lCreaModDocumentoInBean.getAssegnatari() != null && lCreaModDocumentoInBean.getAssegnatari().size() > 0) {
				lListAssegnatari.addAll(lCreaModDocumentoInBean.getAssegnatari());
			}
			if (bean.getListaAssegnazioni() != null && bean.getListaAssegnazioni().size() > 0) {
				for (AssegnazioneBean lAssegnazioneBean : bean.getListaAssegnazioni()) {
					AssegnatariBean lAssegnatariBean = new AssegnatariBean();
					lAssegnatariBean.setTipo(getTipoAssegnatario(lAssegnazioneBean));
					if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
						lAssegnatariBean.setIdSettato(lAssegnazioneBean.getGruppo());
					} else {
						lAssegnatariBean.setIdSettato(lAssegnazioneBean.getIdUo());
					}
					lAssegnatariBean.setPermessiAccesso("FC");
					//TODO ASSEGNAZIONE SAVE OK
					if(lAssegnazioneBean.getOpzioniInvio() != null) {																
						lAssegnatariBean.setMotivoInvio(lAssegnazioneBean.getOpzioniInvio().getMotivoInvio());
						lAssegnatariBean.setLivelloPriorita(lAssegnazioneBean.getOpzioniInvio().getLivelloPriorita());
						lAssegnatariBean.setMessaggioInvio(lAssegnazioneBean.getOpzioniInvio().getMessaggioInvio());
						if (lAssegnazioneBean.getOpzioniInvio().getFlgPresaInCarico() != null && lAssegnazioneBean.getOpzioniInvio().getFlgPresaInCarico()) {
							lAssegnatariBean.setFeedback(Flag.SETTED);
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && lAssegnazioneBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
							lAssegnatariBean.setNumeroGiorniFeedback(lAssegnazioneBean.getOpzioniInvio().getGiorniTrascorsi());
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgInviaFascicolo() != null && lAssegnazioneBean.getOpzioniInvio().getFlgInviaFascicolo()) {
							lAssegnatariBean.setFlgInviaFascicolo(Flag.SETTED);
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgInviaDocCollegati() != null && lAssegnazioneBean.getOpzioniInvio().getFlgInviaDocCollegati()) {
							lAssegnatariBean.setFlgInviaDocCollegati(Flag.SETTED);
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgMantieniCopiaUd() != null && lAssegnazioneBean.getOpzioniInvio().getFlgMantieniCopiaUd()) {
							lAssegnatariBean.setFlgMantieniCopiaUd(Flag.SETTED);
						}
						if (lAssegnazioneBean.getOpzioniInvio().getFlgMandaNotificaMail() != null && lAssegnazioneBean.getOpzioniInvio().getFlgMandaNotificaMail()) {
							lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
						}
					}
					addAssegnatarioToList(lListAssegnatari, lAssegnatariBean);
				}
			}
			if(StringUtils.isNotBlank(bean.getPuntoProtAssegnatario())) {
				if(lListAssegnatari.size() == 1) {
					lListAssegnatari.get(0).setPuntoProtocollo(bean.getPuntoProtAssegnatario());
				}
			}
			lCreaModDocumentoInBean.setAssegnatari(lListAssegnatari);
			lCreaModDocumentoInBean.setIdUserConfermaAssegnazione(bean.getIdUserConfermaAssegnazione() != null ? bean.getIdUserConfermaAssegnazione() : "");
		}
	}

	private void salvaInvioDestCC(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		if (!isPropostaAtto2()) {
			List<AssegnatariBean> lListInvioDestCC = new ArrayList<AssegnatariBean>();
			// Aggiungo quelli da destinatari se ce ne sono
			if (lCreaModDocumentoInBean.getInvioDestCC() != null && lCreaModDocumentoInBean.getInvioDestCC().size() > 0) {
				lListInvioDestCC.addAll(lCreaModDocumentoInBean.getInvioDestCC());
			}
			if (bean.getListaDestInvioCC() != null && bean.getListaDestInvioCC().size() > 0) {
				for (DestInvioCCBean lDestInvioCCBean : bean.getListaDestInvioCC()) {
					AssegnatariBean lAssegnatariBean = new AssegnatariBean();
					lAssegnatariBean.setTipo(getTipoAssegnatario(lDestInvioCCBean));
					if (lAssegnatariBean.getTipo() != null && lAssegnatariBean.getTipo().equals(TipoAssegnatario.GRUPPO)) {
						lAssegnatariBean.setIdSettato(lDestInvioCCBean.getGruppo());
					} else {
						lAssegnatariBean.setIdSettato(lDestInvioCCBean.getIdUo());
					}
					lAssegnatariBean.setPermessiAccesso("V");
					//TODO CONDIVISIONE SAVE OK
					if(lDestInvioCCBean.getOpzioniInvio() != null) {																
						lAssegnatariBean.setMotivoInvio(lDestInvioCCBean.getOpzioniInvio().getMotivoInvio());
						lAssegnatariBean.setLivelloPriorita(lDestInvioCCBean.getOpzioniInvio().getLivelloPriorita());
						lAssegnatariBean.setMessaggioInvio(lDestInvioCCBean.getOpzioniInvio().getMessaggioInvio());
						if (lDestInvioCCBean.getOpzioniInvio().getFlgPresaInCarico() != null && lDestInvioCCBean.getOpzioniInvio().getFlgPresaInCarico()) {
							lAssegnatariBean.setFeedback(Flag.SETTED);
						}
						if (lDestInvioCCBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && lDestInvioCCBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
							lAssegnatariBean.setNumeroGiorniFeedback(lDestInvioCCBean.getOpzioniInvio().getGiorniTrascorsi());
						}		
						if (lDestInvioCCBean.getOpzioniInvio().getFlgInviaFascicolo() != null && lDestInvioCCBean.getOpzioniInvio().getFlgInviaFascicolo()) {
							lAssegnatariBean.setFlgInviaFascicolo(Flag.SETTED);
						}
						if (lDestInvioCCBean.getOpzioniInvio().getFlgInviaDocCollegati() != null && lDestInvioCCBean.getOpzioniInvio().getFlgInviaDocCollegati()) {
							lAssegnatariBean.setFlgInviaDocCollegati(Flag.SETTED);
						}
						if (lDestInvioCCBean.getOpzioniInvio().getFlgMandaNotificaMail() != null && lDestInvioCCBean.getOpzioniInvio().getFlgMandaNotificaMail()) {
							lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
						}
					}
					addInvioDestCCToList(lListInvioDestCC, lAssegnatariBean);
				}
			}
			lCreaModDocumentoInBean.setInvioDestCC(lListInvioDestCC);
		}
	}

	private void salvaAltreUoCoinvolte(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		List<AltreUoCoinvolteBean> lListAltreUoCoinvolte = new ArrayList<AltreUoCoinvolteBean>();
		if (bean.getListaUoCoinvolte() != null) {
			for (DestInvioCCBean lDestInvioCCBean : bean.getListaUoCoinvolte()) {
				AltreUoCoinvolteBean lAltreUoCoinvolteBean = new AltreUoCoinvolteBean();
				lAltreUoCoinvolteBean.setIdUo(lDestInvioCCBean.getIdUo());
				lListAltreUoCoinvolte.add(lAltreUoCoinvolteBean);
			}
		}
		lCreaModDocumentoInBean.setAltreUoCoinvolte(lListAltreUoCoinvolte);
	}
	
	protected void salvaDocumentiCollegati(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		List<DocumentoCollegato> documentiCollegati = new ArrayList<DocumentoCollegato>();
		if(bean.getListaDocumentiDaCollegare() != null && bean.getListaDocumentiDaCollegare().size() > 0) {
			for(DocCollegatoBean docCollegato : bean.getListaDocumentiDaCollegare()) {
				documentiCollegati.add(buildDocumentoCollegatoFromDocCollegatoBean(docCollegato, bean.getAnnoDocRiferimento()));
			}
		} else if(bean.getListaDocumentiCollegati() != null && bean.getListaDocumentiCollegati().size() > 0) {
			for(DocCollegatoBean docCollegato : bean.getListaDocumentiCollegati()) {				
				documentiCollegati.add(buildDocumentoCollegatoFromDocCollegatoBean(docCollegato, bean.getAnnoDocRiferimento()));
			}
		}
		addDocumentoCollegato(lCreaModDocumentoInBean, documentiCollegati);		
	}
	
	public DocumentoCollegato buildDocumentoCollegatoFromDocCollegatoBean(DocCollegatoBean docCollegato, String annoDocRif) {
		DocumentoCollegato lDocumentoCollegato = new DocumentoCollegato();
		lDocumentoCollegato.setFlgPresenteASistema(docCollegato.getFlgPresenteASistema());
		lDocumentoCollegato.setAnno(StringUtils.isNotBlank(annoDocRif) ? Integer.valueOf(annoDocRif) : null); //TODO è questo?
		lDocumentoCollegato.setIdUd(docCollegato.getIdUdCollegata());
		if(docCollegato.getTipo() != null) {
			if(docCollegato.getTipo().equals("PG")) {					
				lDocumentoCollegato.setTipo(TipoProtocollo.PROTOCOLLO_GENERALE);
				lDocumentoCollegato.setSiglaRegistro(null);					
			} else if(docCollegato.getTipo().equals("PI")) {
				lDocumentoCollegato.setTipo(TipoProtocollo.PROTOCOLLO_INTERNO);					
				lDocumentoCollegato.setSiglaRegistro("P.I.");					
			} else if(docCollegato.getTipo().equals("NI")) {
				lDocumentoCollegato.setTipo(TipoProtocollo.PROTOCOLLO_INTERNO);					
				lDocumentoCollegato.setSiglaRegistro("N.I.");					
			} else if(docCollegato.getTipo().equals("PP")) {
				lDocumentoCollegato.setTipo(TipoProtocollo.PROTOCOLLO_PARTICOLARE);					
				lDocumentoCollegato.setSiglaRegistro(docCollegato.getSiglaRegistro());					
			} else if(docCollegato.getTipo().equals("R")) {
				lDocumentoCollegato.setTipo(TipoProtocollo.REPERTORIO);			
				lDocumentoCollegato.setSiglaRegistro(docCollegato.getSiglaRegistro());	
			}
		} else {
			lDocumentoCollegato.setSiglaRegistro(docCollegato.getSiglaRegistro());	
		}
		lDocumentoCollegato.setIdTipoDoc(docCollegato.getIdTipoDoc());
		lDocumentoCollegato.setNomeTipoDoc(docCollegato.getNomeTipoDoc());		
		lDocumentoCollegato.setAnno(docCollegato.getAnno());
		lDocumentoCollegato.setNumero(StringUtils.isNotBlank(docCollegato.getNumero()) ? Integer.valueOf(docCollegato.getNumero()) : null);
		lDocumentoCollegato.setSubNumero(StringUtils.isNotBlank(docCollegato.getSub()) ? Integer.valueOf(docCollegato.getSub()) : null);		       		
   		lDocumentoCollegato.setMotiviCollegamento(docCollegato.getMotivi());
   		return lDocumentoCollegato;
	}
	
	
	private void salvaPerizie(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		List<PeriziaXmlBean> listaPerizie = new ArrayList<PeriziaXmlBean>();
		if (bean.getListaPerizie() != null) {
			for (PeriziaBean perizia : bean.getListaPerizie()) {				
				PeriziaXmlBean periziaBean = new PeriziaXmlBean();
				periziaBean.setPerizia(perizia.getPerizia());
				periziaBean.setDescrizione(perizia.getDescrizione());
				listaPerizie.add(periziaBean);
			}			
			lCreaModDocumentoInBean.setListaPerizie(listaPerizie);
		}
	}
	
	private void salvaAltriRiferimenti(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		List<AltriRiferimentiBean> altriRiferimenti = new ArrayList<AltriRiferimentiBean>();
		if (bean.getListaAltriRiferimenti() != null) {
			for (AltroRifBean altroRif : bean.getListaAltriRiferimenti()) {
				AltriRiferimentiBean altriRifBean = new AltriRiferimentiBean();
				altriRifBean.setRegistroTipoRif(altroRif.getRegistroTipoRif());
				altriRifBean.setNumero(altroRif.getNumero());
				altriRifBean.setAnno(altroRif.getAnno());
				altriRifBean.setData(altroRif.getData());
				altriRifBean.setNote(altroRif.getNote());
				altriRiferimenti.add(altriRifBean);
			}
		}
		lCreaModDocumentoInBean.setAltriRiferimenti(altriRiferimenti);
	}

	private TipoAssegnatario getTipoAssegnatario(DestInvioBean lDestInvioBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lDestInvioBean.getTypeNodo()))
				return lTipoAssegnatario;
		}
		return null;
	}

	private String getIdSettatoFromMitt(MittenteProtBean lMittenteProtBean) {
		if (StringUtils.isNotBlank(lMittenteProtBean.getIdScrivaniaSoggetto())) {
			return lMittenteProtBean.getIdScrivaniaSoggetto();
		} else if (StringUtils.isNotBlank(lMittenteProtBean.getIdUserSoggetto())) {
			return lMittenteProtBean.getIdUserSoggetto();
		} else if (StringUtils.isNotBlank(lMittenteProtBean.getIdUoSoggetto())) {
			return lMittenteProtBean.getIdUoSoggetto();
		} else {
			return null;
		}
	}

	private String getIdSettatoFromDest(DestinatarioProtBean lDestinatarioProtBean) {
		if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
			return lDestinatarioProtBean.getIdScrivaniaSoggetto();
		} else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdUserSoggetto())) {
			return lDestinatarioProtBean.getIdUserSoggetto();
		} else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())) {
			return lDestinatarioProtBean.getIdUoSoggetto();
		}  else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdGruppoInterno())) {
			return lDestinatarioProtBean.getIdGruppoInterno(); // per assegnazione/invio in cc va' utilizzato sempre l'id. interno del gruppo
		} else {
			return null;
		}
	}

	private TipoAssegnatario getTipoAssegnatarioFromMitt(MittenteProtBean lMittenteProtBean) {
		if (StringUtils.isNotBlank(lMittenteProtBean.getIdScrivaniaSoggetto())) {
			return TipoAssegnatario.SCRIVANIA;
		} else if (StringUtils.isNotBlank(lMittenteProtBean.getIdUserSoggetto())) {
			return TipoAssegnatario.UTENTE;
		} else if (StringUtils.isNotBlank(lMittenteProtBean.getIdUoSoggetto())) {
			return TipoAssegnatario.UNITA_ORGANIZZATIVA;
		} else {
			return null;
		}
	}

	private TipoAssegnatario getTipoAssegnatarioFromDest(DestinatarioProtBean lDestinatarioProtBean) {
		if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
			return TipoAssegnatario.SCRIVANIA;
		} else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdUserSoggetto())) {
			return TipoAssegnatario.UTENTE;
		} else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())) {
			return TipoAssegnatario.UNITA_ORGANIZZATIVA;
		} else if (StringUtils.isNotBlank(lDestinatarioProtBean.getIdGruppoInterno())) {
			return TipoAssegnatario.GRUPPO;
		} else {
			return null;
		}
	}

	protected void salvaMittenti(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) throws Exception {
		List<MittentiDocumentoBean> lListMittenti = new ArrayList<MittentiDocumentoBean>();
		List<AssegnatariBean> lListAssegnatari = new ArrayList<AssegnatariBean>();
		if (lCreaModDocumentoInBean.getAssegnatari() != null && lCreaModDocumentoInBean.getAssegnatari().size() > 0) {
			lListAssegnatari.addAll(lCreaModDocumentoInBean.getAssegnatari());
		}
		boolean isIstanza = getExtraparams().get("isIstanza") != null && "true".equals(getExtraparams().get("isIstanza"));		
		if(isIstanza && bean.getListaContribuenti() != null) {
			// nel caso di istanza metto il contribuente come mittente
			for (ContribuenteBean lContribuenteBean : bean.getListaContribuenti()) {
				MittentiDocumentoBean lMittentiBean = new MittentiDocumentoBean();
				lMittentiBean.setIdSoggetto(lContribuenteBean.getIdSoggetto());
				lMittentiBean.setTipoSoggetto(lContribuenteBean.getTipoSoggetto());
				if (StringUtils.isBlank(lContribuenteBean.getTipoSoggetto()) || lContribuenteBean.getTipoSoggetto().equals("G")
						|| lContribuenteBean.getTipoSoggetto().equals("PG") || lContribuenteBean.getTipoSoggetto().equals("PA")
						|| lContribuenteBean.getTipoSoggetto().equals("AOOE")) {
					lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);		
					lMittentiBean.setDenominazioneCognome(lContribuenteBean.getDenominazione());
				} else if (lContribuenteBean.getTipoSoggetto().equals("F") || lContribuenteBean.getTipoSoggetto().equals("PF")) {
					lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);		
					lMittentiBean.setDenominazioneCognome(lContribuenteBean.getCognome());
					lMittentiBean.setNome(lContribuenteBean.getNome());
				}
				lMittentiBean.setCodiceFiscale(lContribuenteBean.getCodFiscale());
				lMittentiBean.setEmail(lContribuenteBean.getEmailContribuente());
				lMittentiBean.setTelefono(lContribuenteBean.getTelContribuente());							
				if(StringUtils.isNotBlank(lContribuenteBean.getIdSoggetto())) {
					lMittentiBean.setIdRubrica(lContribuenteBean.getIdSoggetto());
				} 
				// dati indirizzo
				lMittentiBean.setStato(lContribuenteBean.getStato());
				lMittentiBean.setNomeStato(lContribuenteBean.getNomeStato());
				if (StringUtils.isBlank(lMittentiBean.getStato()) || _COD_ISTAT_ITALIA.equals(lMittentiBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lMittentiBean.getStato())) {
					if (StringUtils.isNotBlank(lContribuenteBean.getCodToponimo())) {
						lMittentiBean.setCodToponimo(lContribuenteBean.getCodToponimo());
						lMittentiBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
						lMittentiBean.setComune(getCodIstatComuneRif());
						lMittentiBean.setNomeComuneCitta(getNomeComuneRif());
					} else {
						lMittentiBean.setTipoToponimo(lContribuenteBean.getTipoToponimo());
						lMittentiBean.setToponimoIndirizzo(lContribuenteBean.getToponimo());
						lMittentiBean.setComune(lContribuenteBean.getComune());
						lMittentiBean.setNomeComuneCitta(lContribuenteBean.getNomeComune());
					}
					lMittentiBean.setFrazione(lContribuenteBean.getFrazione());
					lMittentiBean.setCap(lContribuenteBean.getCap());
				} else {
					lMittentiBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
					lMittentiBean.setNomeComuneCitta(lContribuenteBean.getCitta());
				}
				lMittentiBean.setCivico(lContribuenteBean.getCivico());
				lMittentiBean.setInterno(lContribuenteBean.getInterno());
				lMittentiBean.setZona(lContribuenteBean.getZona());
				lMittentiBean.setComplementoIndirizzo(lContribuenteBean.getComplementoIndirizzo());
				lMittentiBean.setAppendici(lContribuenteBean.getAppendici());
				if (StringUtils.isNotBlank(lMittentiBean.getDenominazioneCognome())
						&& (lMittentiBean.getTipo() == TipoMittente.PERSONA_GIURIDICA || StringUtils.isNotBlank(lMittentiBean.getNome()))) {
					lListMittenti.add(lMittentiBean);
				}				
			}			
		}	
		if (bean.getListaMittenti() != null && bean.getListaMittenti().size() > 0) {
			for (MittenteProtBean lMittenteProtBean : bean.getListaMittenti()) {
				MittentiDocumentoBean lMittentiBean = new MittentiDocumentoBean();
				if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "MITT_SOLO_IN_ORGANIGRAMMA") && StringUtils.isNotBlank(lMittenteProtBean.getOrganigrammaMittente()) &&
					(StringUtils.isBlank(lMittenteProtBean.getTipoMittente()) || lMittenteProtBean.getTipoMittente().equals("UOI") || lMittenteProtBean.getTipoMittente().equals("UP"))) {
					String typeNode = lMittenteProtBean.getOrganigrammaMittente().substring(0, 2);
					String idUoSv = lMittenteProtBean.getOrganigrammaMittente().substring(2);
					lMittentiBean.setIdSoggetto(idUoSv);
					if(typeNode != null) {
						if(typeNode.equals("UO")) {
							lMittentiBean.setTipoSoggetto("UOI");					
							lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getDenominazioneMittente());
							lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);								
						} else if(typeNode.equals("SV")) {
							lMittentiBean.setTipoSoggetto("UP");					
							lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getCognomeMittente());
							lMittentiBean.setNome(lMittenteProtBean.getNomeMittente());
							lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);							
						} 
					}
				} else if (StringUtils.isBlank(lMittenteProtBean.getTipoMittente()) || lMittenteProtBean.getTipoMittente().equals("G")
						|| lMittenteProtBean.getTipoMittente().equals("PG") || lMittenteProtBean.getTipoMittente().equals("PA")
						|| lMittenteProtBean.getTipoMittente().equals("UOI") || lMittenteProtBean.getTipoMittente().equals("AOOE")) {
					lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getDenominazioneMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);
				} else if (lMittenteProtBean.getTipoMittente().equals("F") || lMittenteProtBean.getTipoMittente().equals("PF")
						|| lMittenteProtBean.getTipoMittente().equals("UP")) {
					lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getCognomeMittente());
					lMittentiBean.setNome(lMittenteProtBean.getNomeMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);
				}
				lMittentiBean.setCodiceFiscale(lMittenteProtBean.getCodfiscaleMittente());
				lMittentiBean.setEmail(lMittenteProtBean.getEmailMittente());
				lMittentiBean.setTelefono(lMittenteProtBean.getTelMittente());				
				if(StringUtils.isNotBlank(lMittenteProtBean.getIdSoggetto())) {
					lMittentiBean.setIdRubrica(lMittenteProtBean.getIdSoggetto());
				} else if(StringUtils.isNotBlank(lMittenteProtBean.getIdUoSoggetto())) {
					// Quando ho la preimpostazione della UO di lavoro nel mittente delle bozze e della prot. in uscita/interna 
					// mi manca l'idRubrica quindi passo l'idUo nel cod. di provenienza con prefisso [AUR.UO]
					lMittentiBean.setCodProvenienza("[AUR.UO]" + lMittenteProtBean.getIdUoSoggetto());
				}
//				if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INT_PGWEB") && bean.getFlgTipoProv() != null
//						&& "U".equals(bean.getFlgTipoProv())) {
//
//					DmpkUtilityFindsoggettoinrubricaBean input = new DmpkUtilityFindsoggettoinrubricaBean();
//					input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());
//					input.setFlgsolovldin(1);
//					input.setTsrifin(null);
//					input.setFlgcompletadatidarubrin(1);
//					input.setFlginorganigrammain("UO");
//
//					// IdUserLavoro valorizzato = Utente delegante ( se si
//					// lavora in delega ).
//					// IdUser valorizzato = Utente loggato( se non si lavora in
//					// delega )
//					AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
//					BigDecimal idUser = lAurigaLoginBean.getIdUser() != null && !"".equals(lAurigaLoginBean.getIdUser()) ? lAurigaLoginBean.getIdUser() : null;
//					input.setIduserlavoroin(StringUtils.isNotBlank(lAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(lAurigaLoginBean.getIdUserLavoro())
//							: idUser);
//
//					Riga lRiga = new Riga();
//					if (StringUtils.isNotBlank(lMittenteProtBean.getDenominazioneMittente())) {
//						Colonna lColonna = new Colonna();
//						lColonna.setContent(lMittenteProtBean.getDenominazioneMittente() + "%");
//						lColonna.setNro(new BigInteger("1"));
//						lRiga.getColonna().add(lColonna);
//					}
//					if (StringUtils.isNotBlank(lMittenteProtBean.getCodRapidoMittente())) {
//						Colonna lColonna = new Colonna();
//						lColonna.setContent(lMittenteProtBean.getCodRapidoMittente());
//						lColonna.setNro(new BigInteger("27"));
//						lRiga.getColonna().add(lColonna);
//					}
//					Colonna lColonna = new Colonna();
//					lColonna.setContent("UO;UOI");
//					lColonna.setNro(new BigInteger("34"));
//					lRiga.getColonna().add(lColonna);
//
//					StringWriter lStringWriter = new StringWriter();
//					SingletonJAXBContext.getInstance().createMarshaller().marshal(lRiga, lStringWriter);
//					String datiSoggXml = lStringWriter.toString();
//					input.setDatisoggxmlio(datiSoggXml);
//
//					SchemaBean lSchemaBean = new SchemaBean();
//					lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
//					DmpkUtilityFindsoggettoinrubrica lDmpkUtilityFindsoggettoinrubrica = new DmpkUtilityFindsoggettoinrubrica();
//					StoreResultBean<DmpkUtilityFindsoggettoinrubricaBean> output = lDmpkUtilityFindsoggettoinrubrica.execute(getLocale(), lSchemaBean, input);
//					if (output.isInError()) {
//						String errorMessage = "Errore durante il recupero dell'U.O. mittente in organigramma";
//						if (StringUtils.isNotBlank(output.getDefaultMessage())) {
//							errorMessage += ": " + output.getDefaultMessage();
//						}
//						throw new StoreException(errorMessage);
//					}
//					if (StringUtils.isNotBlank(output.getResultBean().getDatisoggxmlio())) {
//						Riga lRigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller()
//								.unmarshal(new StringReader(output.getResultBean().getDatisoggxmlio()));
//						if (lRigaOut != null) {
//							for (int i = 0; i < lRigaOut.getColonna().size(); i++) {
//								if (lRigaOut.getColonna().get(i).getNro().toString().equals("35")) {
//									lMittentiBean.setProvCIUo(lRigaOut.getColonna().get(i).getContent());
//								}
//							}
//						}
//					}
//					if (StringUtils.isBlank(lMittentiBean.getProvCIUo())) {
//						throw new Exception("U.O. mittente non presente in organigramma o non individuabile univocamente");
//					}
//				}
				if (isAttivoProtocolloWizard(bean)) {
					// dati indirizzo
					lMittentiBean.setStato(lMittenteProtBean.getStato());
					lMittentiBean.setNomeStato(lMittenteProtBean.getNomeStato());
					if (StringUtils.isBlank(lMittentiBean.getStato()) || _COD_ISTAT_ITALIA.equals(lMittentiBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lMittentiBean.getStato())) {
						if (StringUtils.isNotBlank(lMittenteProtBean.getCodToponimo())) {
							lMittentiBean.setCodToponimo(lMittenteProtBean.getCodToponimo());
							lMittentiBean.setToponimoIndirizzo(lMittenteProtBean.getIndirizzo());
							lMittentiBean.setComune(getCodIstatComuneRif());
							lMittentiBean.setNomeComuneCitta(getNomeComuneRif());
						} else {
							lMittentiBean.setTipoToponimo(lMittenteProtBean.getTipoToponimo());
							lMittentiBean.setToponimoIndirizzo(lMittenteProtBean.getToponimo());
							lMittentiBean.setComune(lMittenteProtBean.getComune());
							lMittentiBean.setNomeComuneCitta(lMittenteProtBean.getNomeComune());
						}
						lMittentiBean.setFrazione(lMittenteProtBean.getFrazione());
						lMittentiBean.setCap(lMittenteProtBean.getCap());
					} else {
						lMittentiBean.setToponimoIndirizzo(lMittenteProtBean.getIndirizzo());
						lMittentiBean.setNomeComuneCitta(lMittenteProtBean.getCitta());
					}
					lMittentiBean.setCivico(lMittenteProtBean.getCivico());
					lMittentiBean.setInterno(lMittenteProtBean.getInterno());
					lMittentiBean.setZona(lMittenteProtBean.getZona());
					lMittentiBean.setComplementoIndirizzo(lMittenteProtBean.getComplementoIndirizzo());
					lMittentiBean.setAppendici(lMittenteProtBean.getAppendici());
				}
				lListMittenti.add(lMittentiBean);
				if (lMittenteProtBean.getFlgAssegnaAlMittente() != null && lMittenteProtBean.getFlgAssegnaAlMittente()) {
					boolean skipAssegnazione = false;					
					if(StringUtils.isNotBlank(lMittenteProtBean.getIdAssegnatario())) {
						if(StringUtils.isNotBlank(lMittenteProtBean.getOrganigrammaMittente()) && lMittenteProtBean.getIdAssegnatario().equals(lMittenteProtBean.getOrganigrammaMittente())) {
							 skipAssegnazione = true;	
						} else if(StringUtils.isNotBlank(lMittenteProtBean.getIdUoSoggetto())&& lMittenteProtBean.getIdAssegnatario().equals("UO" + lMittenteProtBean.getIdUoSoggetto())) {
							skipAssegnazione = true;
						} else if(StringUtils.isNotBlank(lMittenteProtBean.getIdScrivaniaSoggetto())&& lMittenteProtBean.getIdAssegnatario().equals("SV" + lMittenteProtBean.getIdScrivaniaSoggetto())) {
							skipAssegnazione = true;
						} else if(StringUtils.isNotBlank(lMittenteProtBean.getIdUserSoggetto())&& lMittenteProtBean.getIdAssegnatario().equals("UT" + lMittenteProtBean.getIdUserSoggetto())) {
							skipAssegnazione = true;
						}						
					}
					if(!skipAssegnazione) {
						AssegnatariBean lAssegnatariBean = new AssegnatariBean();
						lAssegnatariBean.setTipo(getTipoAssegnatarioFromMitt(lMittenteProtBean));
						lAssegnatariBean.setIdSettato(getIdSettatoFromMitt(lMittenteProtBean));
						lAssegnatariBean.setPermessiAccesso("FC");
						//TODO ASSEGNAZIONE SAVE OK
						if (lMittenteProtBean.getOpzioniInvio() != null) {
							lAssegnatariBean.setMotivoInvio(lMittenteProtBean.getOpzioniInvio().getMotivoInvio());
							lAssegnatariBean.setLivelloPriorita(lMittenteProtBean.getOpzioniInvio().getLivelloPriorita());
							lAssegnatariBean.setMessaggioInvio(lMittenteProtBean.getOpzioniInvio().getMessaggioInvio());
							if (lMittenteProtBean.getOpzioniInvio().getFlgPresaInCarico() != null && lMittenteProtBean.getOpzioniInvio().getFlgPresaInCarico()) {
								lAssegnatariBean.setFeedback(Flag.SETTED);
							}
							if (lMittenteProtBean.getOpzioniInvio().getFlgMancataPresaInCarico() != null && lMittenteProtBean.getOpzioniInvio().getFlgMancataPresaInCarico()) {
								lAssegnatariBean.setNumeroGiorniFeedback(lMittenteProtBean.getOpzioniInvio().getGiorniTrascorsi());
							}
							if (lMittenteProtBean.getOpzioniInvio().getFlgInviaFascicolo() != null && lMittenteProtBean.getOpzioniInvio().getFlgInviaFascicolo()) {
								lAssegnatariBean.setFlgInviaFascicolo(Flag.SETTED);
							}
							if (lMittenteProtBean.getOpzioniInvio().getFlgInviaDocCollegati() != null && lMittenteProtBean.getOpzioniInvio().getFlgInviaDocCollegati()) {
								lAssegnatariBean.setFlgInviaDocCollegati(Flag.SETTED);
							}
							if (lMittenteProtBean.getOpzioniInvio().getFlgMantieniCopiaUd() != null && lMittenteProtBean.getOpzioniInvio().getFlgMantieniCopiaUd()) {
								lAssegnatariBean.setFlgMantieniCopiaUd(Flag.SETTED);
							}
							if (lMittenteProtBean.getOpzioniInvio().getFlgMandaNotificaMail() != null && lMittenteProtBean.getOpzioniInvio().getFlgMandaNotificaMail()) {
								lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
							}
						}
						addAssegnatarioToList(lListAssegnatari, lAssegnatariBean);
					}
				}
			}
		}
		lCreaModDocumentoInBean.setMittenti(lListMittenti);
		lCreaModDocumentoInBean.setAssegnatari(lListAssegnatari);
	}
	
	protected void salvaRichiedentiInterni(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) throws Exception {
		List<MittentiDocumentoBean> lListMittenti = new ArrayList<MittentiDocumentoBean>();
		if (bean.getListaRichiedentiInterni() != null && bean.getListaRichiedentiInterni().size() > 0) {
			for (MittenteProtBean lRichInterno : bean.getListaRichiedentiInterni()) {
				MittentiDocumentoBean lMittentiBean = new MittentiDocumentoBean();
				if (StringUtils.isBlank(lRichInterno.getTipoMittente()) || lRichInterno.getTipoMittente().equals("G")
						|| lRichInterno.getTipoMittente().equals("PG") || lRichInterno.getTipoMittente().equals("PA")
						|| lRichInterno.getTipoMittente().equals("UOI") || lRichInterno.getTipoMittente().equals("AOOE")) {
					lMittentiBean.setDenominazioneCognome(lRichInterno.getDenominazioneMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);
				} else if (lRichInterno.getTipoMittente().equals("F") || lRichInterno.getTipoMittente().equals("PF")
						|| lRichInterno.getTipoMittente().equals("UP")) {
					lMittentiBean.setDenominazioneCognome(lRichInterno.getCognomeMittente());
					lMittentiBean.setNome(lRichInterno.getNomeMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);
				}
				lMittentiBean.setCodiceFiscale(lRichInterno.getCodfiscaleMittente());
				lMittentiBean.setEmail(lRichInterno.getEmailMittente());
				lMittentiBean.setTelefono(lRichInterno.getTelMittente());
				if(StringUtils.isNotBlank(lRichInterno.getIdSoggetto())) {
					lMittentiBean.setIdRubrica(lRichInterno.getIdSoggetto());
				} else if(StringUtils.isNotBlank(lRichInterno.getIdUoSoggetto())) {
					// Quando ho la preimpostazione della UO di lavoro nel mittente delle bozze e della prot. in uscita/interna 
					// mi manca l'idRubrica quindi passo l'idUo nel cod. di provenienza con prefisso [AUR.UO]
					lMittentiBean.setCodProvenienza("[AUR.UO]" + lRichInterno.getIdUoSoggetto());
				}		
				lListMittenti.add(lMittentiBean);
			}
		}
		lCreaModDocumentoInBean.setMittenti(lListMittenti);
	}

	private void salvaDestinatari(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
		List<DestinatariBean> lListDestinatari = new ArrayList<DestinatariBean>();
		List<FogliXlsDestinatari> lListFogliXlsDestinatari = new ArrayList<FogliXlsDestinatari>();
		List<DistribuzioneBean> lListGruppi = new ArrayList<DistribuzioneBean>();
		List<AssegnatariBean> lListAssegnatari = new ArrayList<AssegnatariBean>();
		if (lCreaModDocumentoInBean.getAssegnatari() != null && lCreaModDocumentoInBean.getAssegnatari().size() > 0) {
			lListAssegnatari.addAll(lCreaModDocumentoInBean.getAssegnatari());
		}
		List<AssegnatariBean> lListInvioDestCC = new ArrayList<AssegnatariBean>();		
		if (lCreaModDocumentoInBean.getInvioDestCC() != null && lCreaModDocumentoInBean.getInvioDestCC().size() > 0) {
			lListInvioDestCC.addAll(lCreaModDocumentoInBean.getInvioDestCC());
		}
		if (bean.getListaDestinatari() != null && bean.getListaDestinatari().size() > 0) {
			int indiceRipetibileDestinatari = 1;
			for (DestinatarioProtBean lDestinatarioProtBean : bean.getListaDestinatari()) {
				if (lDestinatarioProtBean.getTipoDestinatario() != null && lDestinatarioProtBean.getTipoDestinatario().equals("XLS")) {
					FogliXlsDestinatari lFogliXlsDestinatari = new FogliXlsDestinatari();
					lFogliXlsDestinatari.setIdFoglio(lDestinatarioProtBean.getIdFoglioExcelDestinatari());
					lFogliXlsDestinatari.setNroDestinatario(String.valueOf(indiceRipetibileDestinatari));
					lFogliXlsDestinatari.setDisplayFileNameExcel(lDestinatarioProtBean.getDisplayFileNameExcel());
					
					lListFogliXlsDestinatari.add(lFogliXlsDestinatari);
					
					continue;
				}
				if (lDestinatarioProtBean.getTipoDestinatario() != null && lDestinatarioProtBean.getTipoDestinatario().equals("LD")) {
					DistribuzioneBean lDistribuzioneBean = new DistribuzioneBean();
					lDistribuzioneBean.setIdLista(lDestinatarioProtBean.getIdGruppoEsterno());
					if (lDestinatarioProtBean.getFlgAssegnaAlDestinatario() != null && lDestinatarioProtBean.getFlgAssegnaAlDestinatario()) {
						lDistribuzioneBean.setAssegna(Flag.SETTED);
					} else {
						lDistribuzioneBean.setAssegna(Flag.NULL);
					}
					if (lDestinatarioProtBean.getFlgPC() != null && lDestinatarioProtBean.getFlgPC()) {
						lDistribuzioneBean.setPerConoscenza(Flag.SETTED);
					} else {
						lDistribuzioneBean.setPerConoscenza(Flag.NULL);
					}
					lListGruppi.add(lDistribuzioneBean);
				} else if (lDestinatarioProtBean.getTipoDestinatario() != null && lDestinatarioProtBean.getTipoDestinatario().equals("PREF")) {
					if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdSoggetto()) || StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto()) || StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
						DestinatariBean lDestinatariBean = new DestinatariBean();
						lDestinatariBean.setDenominazioneCognome(lDestinatarioProtBean.getDenominazioneDestinatario());		
						lDestinatariBean.setIdRubrica(lDestinatarioProtBean.getIdSoggetto());
						if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())) {
							lDestinatariBean.setIdSoggetto(lDestinatarioProtBean.getIdUoSoggetto());
							lDestinatariBean.setTipoSoggetto("UOI");
						} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
							lDestinatariBean.setIdSoggetto(lDestinatarioProtBean.getIdScrivaniaSoggetto());
							lDestinatariBean.setTipoSoggetto("UP");
							lDestinatariBean.setTipo(TipoDestinatario.UNITA_DI_PERSONALE);
						}
						if (lDestinatarioProtBean.getFlgAssegnaAlDestinatario() != null && lDestinatarioProtBean.getFlgAssegnaAlDestinatario()) {
							lDestinatariBean.setAssegna(Flag.SETTED);
						} else {
							lDestinatariBean.setAssegna(Flag.NULL);
						}
						if (lDestinatarioProtBean.getFlgPC() != null && lDestinatarioProtBean.getFlgPC()) {
							lDestinatariBean.setPerConoscenza(Flag.SETTED);
						} else {
							lDestinatariBean.setPerConoscenza(Flag.NULL);
						}
						lListDestinatari.add(lDestinatariBean);
					}
				} else {
					DestinatariBean lDestinatariBean = new DestinatariBean();
					boolean showSelectOrganigramma = false;
					if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "DEST_INT_CON_SELECT")) {
						if (bean.getFlgTipoProv() == null || bean.getFlgTipoProv().equalsIgnoreCase("U")) {
							// se è un destinatario in uscita di default quando il tipo è null non devo mostrare la select organigramma
							showSelectOrganigramma = lDestinatarioProtBean.getTipoDestinatario() != null && lDestinatarioProtBean.getTipoDestinatario().equals("UP_UOI");
						} else {
							// al contrario se il tipo è null deve mostrare la select organigramma
							showSelectOrganigramma = StringUtils.isBlank(lDestinatarioProtBean.getTipoDestinatario()) || lDestinatarioProtBean.getTipoDestinatario().equals("UP_UOI");
						}
					}					
					if(showSelectOrganigramma && StringUtils.isNotBlank(lDestinatarioProtBean.getOrganigrammaDestinatario())) {
						String typeNode = lDestinatarioProtBean.getOrganigrammaDestinatario().substring(0, 2);
						String idUoSv = lDestinatarioProtBean.getOrganigrammaDestinatario().substring(2);
						lDestinatariBean.setIdSoggetto(idUoSv);
						if(typeNode != null) {
							if(typeNode.equals("UO")) {
								lDestinatariBean.setTipoSoggetto("UOI");
								lDestinatariBean.setDenominazioneCognome(lDestinatarioProtBean.getDenominazioneDestinatario());								
							} else if(typeNode.equals("SV")) {
								lDestinatariBean.setTipoSoggetto("UP");
								lDestinatariBean.setDenominazioneCognome(lDestinatarioProtBean.getCognomeDestinatario());
								lDestinatariBean.setNome(lDestinatarioProtBean.getNomeDestinatario());
								lDestinatariBean.setTipo(TipoDestinatario.UNITA_DI_PERSONALE);					
							} 
						}												
					} else {					
						if (StringUtils.isBlank(lDestinatarioProtBean.getTipoDestinatario()) || lDestinatarioProtBean.getTipoDestinatario().equals("G")
								|| lDestinatarioProtBean.getTipoDestinatario().equals("PG") || lDestinatarioProtBean.getTipoDestinatario().equals("PA")
								|| lDestinatarioProtBean.getTipoDestinatario().equals("UOI") || lDestinatarioProtBean.getTipoDestinatario().equals("AOOE")) {
							lDestinatariBean.setDenominazioneCognome(lDestinatarioProtBean.getDenominazioneDestinatario());
						} else if (lDestinatarioProtBean.getTipoDestinatario().equals("F") || lDestinatarioProtBean.getTipoDestinatario().equals("PF")
								|| lDestinatarioProtBean.getTipoDestinatario().equals("UP")) {
							lDestinatariBean.setDenominazioneCognome(lDestinatarioProtBean.getCognomeDestinatario());
							lDestinatariBean.setNome(lDestinatarioProtBean.getNomeDestinatario());
							lDestinatariBean.setTipo(TipoDestinatario.UNITA_DI_PERSONALE);
						}
						lDestinatariBean.setCodiceFiscale(lDestinatarioProtBean.getCodfiscaleDestinatario());
						if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdSoggetto())) {
							lDestinatariBean.setIdRubrica(lDestinatarioProtBean.getIdSoggetto());
						} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())) {
							// Quando ho la preimpostazione della UO di lavoro nel destinatario della prot. in entrata 
							// mi manca l'idRubrica quindi passo l'idUo nel cod. di provenienza con prefisso [AUR.UO]
							lDestinatariBean.setCodProvenienza("[AUR.UO]" + lDestinatarioProtBean.getIdUoSoggetto());
						}		
					}
					if (lDestinatarioProtBean.getFlgAssegnaAlDestinatario() != null && lDestinatarioProtBean.getFlgAssegnaAlDestinatario()) {
						lDestinatariBean.setAssegna(Flag.SETTED);
					} else {
						lDestinatariBean.setAssegna(Flag.NULL);
					}
					if (lDestinatarioProtBean.getFlgPC() != null && lDestinatarioProtBean.getFlgPC()) {
						lDestinatariBean.setPerConoscenza(Flag.SETTED);
					} else {
						lDestinatariBean.setPerConoscenza(Flag.NULL);
					}	
					if (isAttivoProtocolloWizard(bean)) {
						// dati indirizzo
						lDestinatariBean.setStato(lDestinatarioProtBean.getStato());
						lDestinatariBean.setNomeStato(lDestinatarioProtBean.getNomeStato());
						if (StringUtils.isBlank(lDestinatariBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDestinatariBean.getStato())
								|| _NOME_STATO_ITALIA.equals(lDestinatariBean.getStato())) {
							if (StringUtils.isNotBlank(lDestinatarioProtBean.getCodToponimo())) {
								lDestinatariBean.setCodToponimo(lDestinatarioProtBean.getCodToponimo());
								lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getIndirizzo());
								lDestinatariBean.setComune(getCodIstatComuneRif());
								lDestinatariBean.setNomeComuneCitta(getNomeComuneRif());
							} else {
								lDestinatariBean.setTipoToponimo(lDestinatarioProtBean.getTipoToponimo());
								lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getToponimo());
								lDestinatariBean.setComune(lDestinatarioProtBean.getComune());
								lDestinatariBean.setNomeComuneCitta(lDestinatarioProtBean.getNomeComune());
							}
							lDestinatariBean.setFrazione(lDestinatarioProtBean.getFrazione());
							lDestinatariBean.setCap(lDestinatarioProtBean.getCap());
						} else {
							lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getIndirizzo());
							lDestinatariBean.setNomeComuneCitta(lDestinatarioProtBean.getCitta());
						}
						lDestinatariBean.setCivico(lDestinatarioProtBean.getCivico());
						lDestinatariBean.setInterno(lDestinatarioProtBean.getInterno());
						lDestinatariBean.setZona(lDestinatarioProtBean.getZona());
						lDestinatariBean.setComplementoIndirizzo(lDestinatarioProtBean.getComplementoIndirizzo());
						lDestinatariBean.setAppendici(lDestinatarioProtBean.getAppendici());
					} else if (lDestinatarioProtBean.getMezzoTrasmissioneDestinatario() != null && ParametriDBUtil.getParametroDBAsBoolean(getSession(), "SHOW_DESTINATARI_ESTESI")) {
						// Salvo il mezzo di trasmissione
						MezzoTrasmissioneDestinatarioBean lMezzoTrasmissioneDestinatarioBean = new MezzoTrasmissioneDestinatarioBean();
						lMezzoTrasmissioneDestinatarioBean = lDestinatarioProtBean.getMezzoTrasmissioneDestinatario();
						lDestinatariBean.setMezzoTrasmissioneDestinatario(lMezzoTrasmissioneDestinatarioBean.getMezzoTrasmissioneDestinatario());
						if(lDestinatariBean.getMezzoTrasmissioneDestinatario() != null) {
							if (isCodiceRaccomandata(lDestinatariBean.getMezzoTrasmissioneDestinatario())){
								lDestinatariBean.setDataRaccomandataDestinatario(lMezzoTrasmissioneDestinatarioBean.getDataRaccomandataDestinatario());
								lDestinatariBean.setNroRaccomandataDestinatario(lMezzoTrasmissioneDestinatarioBean.getNroRaccomandataDestinatario());											
							}
							if("NM".equals(lDestinatariBean.getMezzoTrasmissioneDestinatario())) {
								lDestinatariBean.setDataNotificaDestinatario(lMezzoTrasmissioneDestinatarioBean.getDataNotificaDestinatario());
								lDestinatariBean.setNroNotificaDestinatario(lMezzoTrasmissioneDestinatarioBean.getNroNotificaDestinatario());									
							} else if ("PEC".equals(lDestinatariBean.getMezzoTrasmissioneDestinatario())) {
								lDestinatariBean.setIndirizzoMail(lMezzoTrasmissioneDestinatarioBean.getIndirizzoPECDestinatario() != null ? lMezzoTrasmissioneDestinatarioBean.getIndirizzoPECDestinatario().trim() : null);	
							} else if ("PEO".equals(lDestinatariBean.getMezzoTrasmissioneDestinatario())) {
								lDestinatariBean.setIndirizzoMail(lMezzoTrasmissioneDestinatarioBean.getIndirizzoPEODestinatario() != null ? lMezzoTrasmissioneDestinatarioBean.getIndirizzoPEODestinatario().trim() : null);	
							} else if ("EMAIL".equals(lDestinatariBean.getMezzoTrasmissioneDestinatario())) {
								lDestinatariBean.setIndirizzoMail(lMezzoTrasmissioneDestinatarioBean.getIndirizzoMailDestinatario());	
							} 
							if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INDIRIZZO_DEST_ESTESI")) {
								// dati indirizzo
								lDestinatariBean.setStato(lDestinatarioProtBean.getStato());
								lDestinatariBean.setNomeStato(lDestinatarioProtBean.getNomeStato());
								if (StringUtils.isBlank(lDestinatariBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDestinatariBean.getStato())
										|| _NOME_STATO_ITALIA.equals(lDestinatariBean.getStato())) {
									if (StringUtils.isNotBlank(lDestinatarioProtBean.getCodToponimo())) {
										lDestinatariBean.setCodToponimo(lDestinatarioProtBean.getCodToponimo());
										lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getIndirizzo());
										lDestinatariBean.setComune(getCodIstatComuneRif());
										lDestinatariBean.setNomeComuneCitta(getNomeComuneRif());
									} else {
										lDestinatariBean.setTipoToponimo(lDestinatarioProtBean.getTipoToponimo());
										lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getToponimo());
										lDestinatariBean.setComune(lDestinatarioProtBean.getComune());
										lDestinatariBean.setNomeComuneCitta(lDestinatarioProtBean.getNomeComune());
									}
									lDestinatariBean.setFrazione(lDestinatarioProtBean.getFrazione());
									lDestinatariBean.setCap(lDestinatarioProtBean.getCap());
								} else {
									lDestinatariBean.setToponimoIndirizzo(lDestinatarioProtBean.getIndirizzo());
									lDestinatariBean.setNomeComuneCitta(lDestinatarioProtBean.getCitta());
								}
								lDestinatariBean.setCivico(lDestinatarioProtBean.getCivico());
								lDestinatariBean.setInterno(lDestinatarioProtBean.getInterno());
								lDestinatariBean.setZona(lDestinatarioProtBean.getZona());
								lDestinatariBean.setComplementoIndirizzo(lDestinatarioProtBean.getComplementoIndirizzo());
								lDestinatariBean.setAppendici(lDestinatarioProtBean.getAppendici());
							} else {						
								lDestinatariBean.setTipoToponimo(lMezzoTrasmissioneDestinatarioBean.getTipoToponimo());
								lDestinatariBean.setCodToponimo(lMezzoTrasmissioneDestinatarioBean.getCiToponimo());
								lDestinatariBean.setToponimoIndirizzo(lMezzoTrasmissioneDestinatarioBean.getIndirizzo());
								lDestinatariBean.setIndirizzoDestinatario(lMezzoTrasmissioneDestinatarioBean.getIndirizzoDestinatario());
								lDestinatariBean.setFrazione(lMezzoTrasmissioneDestinatarioBean.getFrazione());
								lDestinatariBean.setCivico(lMezzoTrasmissioneDestinatarioBean.getCivico());
								lDestinatariBean.setInterno(lMezzoTrasmissioneDestinatarioBean.getInterno());
								lDestinatariBean.setScala(lMezzoTrasmissioneDestinatarioBean.getScala());
								lDestinatariBean.setPiano(lMezzoTrasmissioneDestinatarioBean.getPiano());
								lDestinatariBean.setCap(lMezzoTrasmissioneDestinatarioBean.getCap());
								lDestinatariBean.setComune(lMezzoTrasmissioneDestinatarioBean.getCodIstatComune());
								lDestinatariBean.setNomeComuneCitta(lMezzoTrasmissioneDestinatarioBean.getComune());
								lDestinatariBean.setStato(lMezzoTrasmissioneDestinatarioBean.getCodIstatStato());
								lDestinatariBean.setNomeStato(lMezzoTrasmissioneDestinatarioBean.getStato());
								lDestinatariBean.setZona(lMezzoTrasmissioneDestinatarioBean.getZona());
								lDestinatariBean.setComplementoIndirizzo(lMezzoTrasmissioneDestinatarioBean.getComplementoIndirizzo());
								lDestinatariBean.setAppendici(lMezzoTrasmissioneDestinatarioBean.getAppendici());
							}
						}
					}
					lListDestinatari.add(lDestinatariBean);
				}
				boolean isBozza = getExtraparams().get("isBozza") != null && "true".equals(getExtraparams().get("isBozza"));		
				if(!isBozza) {
					if (lDestinatarioProtBean.getFlgAssegnaAlDestinatario() != null && lDestinatarioProtBean.getFlgAssegnaAlDestinatario()) {
						boolean skipAssegnazione = false;
						if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdAssegnatario())) {
							if(StringUtils.isNotBlank(lDestinatarioProtBean.getOrganigrammaDestinatario()) && lDestinatarioProtBean.getIdAssegnatario().equals(lDestinatarioProtBean.getOrganigrammaDestinatario())) {
								 skipAssegnazione = true;	
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())&& lDestinatarioProtBean.getIdAssegnatario().equals("UO" + lDestinatarioProtBean.getIdUoSoggetto())) {
								skipAssegnazione = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())&& lDestinatarioProtBean.getIdAssegnatario().equals("SV" + lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
								skipAssegnazione = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUserSoggetto())&& lDestinatarioProtBean.getIdAssegnatario().equals("UT" + lDestinatarioProtBean.getIdUserSoggetto())) {
								skipAssegnazione = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdGruppoInterno())&& lDestinatarioProtBean.getIdAssegnatario().equals("LD" + lDestinatarioProtBean.getIdGruppoInterno())) {
								skipAssegnazione = true;
							}							
						}
						if(!skipAssegnazione) {
							AssegnatariBean lAssegnatariBean = new AssegnatariBean();
							lAssegnatariBean.setTipo(getTipoAssegnatarioFromDest(lDestinatarioProtBean));
							lAssegnatariBean.setIdSettato(getIdSettatoFromDest(lDestinatarioProtBean));
							lAssegnatariBean.setPermessiAccesso("FC");
							//TODO ASSEGNAZIONE SAVE OK
							if (lDestinatarioProtBean.getOpzioniInvioAssegnazione() != null) {
								lAssegnatariBean.setMotivoInvio(lDestinatarioProtBean.getOpzioniInvioAssegnazione().getMotivoInvio());
								lAssegnatariBean.setLivelloPriorita(lDestinatarioProtBean.getOpzioniInvioAssegnazione().getLivelloPriorita());
								lAssegnatariBean.setMessaggioInvio(lDestinatarioProtBean.getOpzioniInvioAssegnazione().getMessaggioInvio());
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgPresaInCarico() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgPresaInCarico()) {
									lAssegnatariBean.setFeedback(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMancataPresaInCarico() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMancataPresaInCarico()) {
									lAssegnatariBean.setNumeroGiorniFeedback(lDestinatarioProtBean.getOpzioniInvioAssegnazione().getGiorniTrascorsi());
								}
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgInviaFascicolo() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgInviaFascicolo()) {
									lAssegnatariBean.setFlgInviaFascicolo(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgInviaDocCollegati() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgInviaDocCollegati()) {
									lAssegnatariBean.setFlgInviaDocCollegati(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMantieniCopiaUd() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMantieniCopiaUd()) {
									lAssegnatariBean.setFlgMantieniCopiaUd(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMandaNotificaMail() != null && lDestinatarioProtBean.getOpzioniInvioAssegnazione().getFlgMandaNotificaMail()) {
									lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
								}
							}
							addAssegnatarioToList(lListAssegnatari, lAssegnatariBean);
						}
					}
					if (lDestinatarioProtBean.getFlgPC() != null && lDestinatarioProtBean.getFlgPC()) {
						boolean skipInvioCC = false;
						if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdDestInvioCC())) {
							if(StringUtils.isNotBlank(lDestinatarioProtBean.getOrganigrammaDestinatario()) && lDestinatarioProtBean.getIdDestInvioCC().equals(lDestinatarioProtBean.getOrganigrammaDestinatario())) {
								 skipInvioCC = true;	
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUoSoggetto())&& lDestinatarioProtBean.getIdDestInvioCC().equals("UO" + lDestinatarioProtBean.getIdUoSoggetto())) {
								skipInvioCC = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdScrivaniaSoggetto())&& lDestinatarioProtBean.getIdDestInvioCC().equals("SV" + lDestinatarioProtBean.getIdScrivaniaSoggetto())) {
								skipInvioCC = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdUserSoggetto())&& lDestinatarioProtBean.getIdDestInvioCC().equals("UT" + lDestinatarioProtBean.getIdUserSoggetto())) {
								skipInvioCC = true;
							} else if(StringUtils.isNotBlank(lDestinatarioProtBean.getIdGruppoInterno())&& lDestinatarioProtBean.getIdDestInvioCC().equals("LD" + lDestinatarioProtBean.getIdGruppoInterno())) {
								skipInvioCC = true;
							}							
						}
						if(!skipInvioCC) {
							AssegnatariBean lAssegnatariBean = new AssegnatariBean();
							lAssegnatariBean.setTipo(getTipoAssegnatarioFromDest(lDestinatarioProtBean));
							lAssegnatariBean.setIdSettato(getIdSettatoFromDest(lDestinatarioProtBean));
							lAssegnatariBean.setPermessiAccesso("V");
							//TODO CONDIVISIONE SAVE OK
							if (lDestinatarioProtBean.getOpzioniInvioCondivisione() != null) {
								lAssegnatariBean.setMotivoInvio(lDestinatarioProtBean.getOpzioniInvioCondivisione().getMotivoInvio());
								lAssegnatariBean.setLivelloPriorita(lDestinatarioProtBean.getOpzioniInvioCondivisione().getLivelloPriorita());
								lAssegnatariBean.setMessaggioInvio(lDestinatarioProtBean.getOpzioniInvioCondivisione().getMessaggioInvio());						
								if (lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgPresaInCarico() != null && lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgPresaInCarico()) {
									lAssegnatariBean.setFeedback(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgMancataPresaInCarico() != null && lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgMancataPresaInCarico()) {
									lAssegnatariBean.setNumeroGiorniFeedback(lDestinatarioProtBean.getOpzioniInvioCondivisione().getGiorniTrascorsi());
								}							
								if (lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgInviaFascicolo() != null && lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgInviaFascicolo()) {
									lAssegnatariBean.setFlgInviaFascicolo(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgInviaDocCollegati() != null && lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgInviaDocCollegati()) {
									lAssegnatariBean.setFlgInviaDocCollegati(Flag.SETTED);
								}
								if (lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgMandaNotificaMail() != null && lDestinatarioProtBean.getOpzioniInvioCondivisione().getFlgMandaNotificaMail()) {
									lAssegnatariBean.setFlgMandaNotificaMail(Flag.SETTED);
								}
							}					
							addInvioDestCCToList(lListInvioDestCC, lAssegnatariBean);
						}
					}
				}
				
				indiceRipetibileDestinatari++;
			}
		}
		lCreaModDocumentoInBean.setDestinatari(lListDestinatari);
		lCreaModDocumentoInBean.setFogliXlsDestinatari(lListFogliXlsDestinatari);
		lCreaModDocumentoInBean.setGruppi(lListGruppi);
		lCreaModDocumentoInBean.setAssegnatari(lListAssegnatari);
		lCreaModDocumentoInBean.setInvioDestCC(lListInvioDestCC);
	}

	public void addAssegnatarioToList(List<AssegnatariBean> lListAssegnatari, AssegnatariBean lAssegnatariBean) {
		if (lAssegnatariBean != null && StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
			boolean trovato = false;
			if (lListAssegnatari == null) {
				lListAssegnatari = new ArrayList<AssegnatariBean>();
			} else if (lListAssegnatari.size() > 0) {
				for (int i = 0; i < lListAssegnatari.size(); i++) {
					if (lListAssegnatari.get(i).getIdSettato() != null && lListAssegnatari.get(i).getIdSettato().equals(lAssegnatariBean.getIdSettato())) {
						trovato = true;
						break;
					}
				}
			}
			if (!trovato) {
				lListAssegnatari.add(lAssegnatariBean);
			}
		}
	}
	
	public void addInvioDestCCToList(List<AssegnatariBean> lListInvioDestCC, AssegnatariBean lAssegnatariBean) {
		if (lAssegnatariBean != null && StringUtils.isNotBlank(lAssegnatariBean.getIdSettato())) {
			boolean trovato = false;
			if (lListInvioDestCC == null) {
				lListInvioDestCC = new ArrayList<AssegnatariBean>();
			} else if (lListInvioDestCC.size() > 0) {
				for (int i = 0; i < lListInvioDestCC.size(); i++) {
					if (lListInvioDestCC.get(i).getIdSettato() != null && lListInvioDestCC.get(i).getIdSettato().equals(lAssegnatariBean.getIdSettato())) {
						trovato = true;
						break;
					}
				}
			}
			if (!trovato) {
				lListInvioDestCC.add(lAssegnatariBean);
			}
		}
	}

	protected void salvaAltriSoggettiEsterni(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) throws Exception {

		List<SoggettoEsternoBean> lListAltriSoggettiEsterni = null;
		boolean isIstanza = getExtraparams().get("isIstanza") != null && "true".equals(getExtraparams().get("isIstanza"));		
		if(isIstanza && bean.getMezzoTrasmissione() != null && "CM".equals(bean.getMezzoTrasmissione()) && bean.getListaContribuenti() != null) {
			// nel caso di istanza metto il contribuente come esibente se il canale è "sportello"
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			for (ContribuenteBean lContribuenteBean : bean.getListaContribuenti()) {
				if(lContribuenteBean.getTipoSoggetto().equals("F") || lContribuenteBean.getTipoSoggetto().equals("PF")) {
					SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();				
					lSoggettoEsternoBean.setIdSoggetto(lContribuenteBean.getIdSoggetto());
					lSoggettoEsternoBean.setTipoSoggetto(lContribuenteBean.getTipoSoggetto());
					lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lContribuenteBean.getCognome());
					lSoggettoEsternoBean.setNome(lContribuenteBean.getNome());				
					lSoggettoEsternoBean.setCodFiscale(lContribuenteBean.getCodFiscale());
					lSoggettoEsternoBean.setTipoDocRiconoscimento(lContribuenteBean.getTipoDocRiconoscimento());
					lSoggettoEsternoBean.setEstremiDocRiconoscimento(lContribuenteBean.getEstremiDocRiconoscimento());
					// dati indirizzo
					lSoggettoEsternoBean.setStato(lContribuenteBean.getStato());
					lSoggettoEsternoBean.setNomeStato(lContribuenteBean.getNomeStato());
					if (StringUtils.isBlank(lSoggettoEsternoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lSoggettoEsternoBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lSoggettoEsternoBean.getStato())) {
						if (StringUtils.isNotBlank(lContribuenteBean.getCodToponimo())) {
							lSoggettoEsternoBean.setCodToponimo(lContribuenteBean.getCodToponimo());
							lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
							lSoggettoEsternoBean.setComune(getCodIstatComuneRif());
							lSoggettoEsternoBean.setNomeComuneCitta(getNomeComuneRif());
						} else {
							lSoggettoEsternoBean.setTipoToponimo(lContribuenteBean.getTipoToponimo());
							lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getToponimo());
							lSoggettoEsternoBean.setComune(lContribuenteBean.getComune());
							lSoggettoEsternoBean.setNomeComuneCitta(lContribuenteBean.getNomeComune());
						}
						lSoggettoEsternoBean.setFrazione(lContribuenteBean.getFrazione());
						lSoggettoEsternoBean.setCap(lContribuenteBean.getCap());
					} else {
						lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
						lSoggettoEsternoBean.setNomeComuneCitta(lContribuenteBean.getCitta());
					}
					lSoggettoEsternoBean.setCivico(lContribuenteBean.getCivico());
					lSoggettoEsternoBean.setInterno(lContribuenteBean.getInterno());
					lSoggettoEsternoBean.setZona(lContribuenteBean.getZona());
					lSoggettoEsternoBean.setComplementoIndirizzo(lContribuenteBean.getComplementoIndirizzo());
					lSoggettoEsternoBean.setAppendici(lContribuenteBean.getAppendici());											
					// natura relazione
					lSoggettoEsternoBean.setCodNaturaRel("E");
					// altri dati contribuente
					lSoggettoEsternoBean.setProvCISogg(lContribuenteBean.getCodiceACS()); // codice ACS
					lSoggettoEsternoBean.setEmailContribuente(lContribuenteBean.getEmailContribuente()); // email contribuente		
					lSoggettoEsternoBean.setTelefonoContribuente(lContribuenteBean.getTelContribuente()); // tel contribuente								
					if (StringUtils.isNotBlank(lSoggettoEsternoBean.getDenominazioneCognome()) && StringUtils.isNotBlank(lSoggettoEsternoBean.getNome())) {
						lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);						
					}
				}
			}			
		}			
		if (isAttivoEsibente(bean) && bean.getListaEsibenti() != null) {
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			for (SoggEsternoProtBean lEsibenteProtBean : bean.getListaEsibenti()) {
				SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
				lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
				lSoggettoEsternoBean.setIdSoggetto(lEsibenteProtBean.getIdSoggetto());
				lSoggettoEsternoBean.setTipoSoggetto(lEsibenteProtBean.getTipoSoggetto());
				lSoggettoEsternoBean.setDenominazioneCognome(lEsibenteProtBean.getCognome());
				lSoggettoEsternoBean.setNome(lEsibenteProtBean.getNome());
				lSoggettoEsternoBean.setCodFiscale(lEsibenteProtBean.getCodFiscale());
				lSoggettoEsternoBean.setEmailContribuente(lEsibenteProtBean.getEmail());
				lSoggettoEsternoBean.setTipoDocRiconoscimento(lEsibenteProtBean.getTipoDocRiconoscimento());
				lSoggettoEsternoBean.setEstremiDocRiconoscimento(lEsibenteProtBean.getEstremiDocRiconoscimento());
				// dati indirizzo
				lSoggettoEsternoBean.setStato(lEsibenteProtBean.getStato());
				lSoggettoEsternoBean.setNomeStato(lEsibenteProtBean.getNomeStato());
				if (StringUtils.isBlank(lSoggettoEsternoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lSoggettoEsternoBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lSoggettoEsternoBean.getStato())) {
					if (StringUtils.isNotBlank(lEsibenteProtBean.getCodToponimo())) {
						lSoggettoEsternoBean.setCodToponimo(lEsibenteProtBean.getCodToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lEsibenteProtBean.getIndirizzo());
						lSoggettoEsternoBean.setComune(getCodIstatComuneRif());
						lSoggettoEsternoBean.setNomeComuneCitta(getNomeComuneRif());
					} else {
						lSoggettoEsternoBean.setTipoToponimo(lEsibenteProtBean.getTipoToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lEsibenteProtBean.getToponimo());
						lSoggettoEsternoBean.setComune(lEsibenteProtBean.getComune());
						lSoggettoEsternoBean.setNomeComuneCitta(lEsibenteProtBean.getNomeComune());
					}
					lSoggettoEsternoBean.setFrazione(lEsibenteProtBean.getFrazione());
					lSoggettoEsternoBean.setCap(lEsibenteProtBean.getCap());
				} else {
					lSoggettoEsternoBean.setToponimoIndirizzo(lEsibenteProtBean.getIndirizzo());
					lSoggettoEsternoBean.setNomeComuneCitta(lEsibenteProtBean.getCitta());
				}
				lSoggettoEsternoBean.setCivico(lEsibenteProtBean.getCivico());
				lSoggettoEsternoBean.setInterno(lEsibenteProtBean.getInterno());
				lSoggettoEsternoBean.setZona(lEsibenteProtBean.getZona());
				lSoggettoEsternoBean.setComplementoIndirizzo(lEsibenteProtBean.getComplementoIndirizzo());
				lSoggettoEsternoBean.setAppendici(lEsibenteProtBean.getAppendici());
				// natura relazione
				lSoggettoEsternoBean.setCodNaturaRel("E");
				if(lEsibenteProtBean.getFlgAncheMittente() != null && lEsibenteProtBean.getFlgAncheMittente()) {
					lSoggettoEsternoBean.setFlgAncheMittente(Flag.SETTED);
				}
				if (StringUtils.isNotBlank(lSoggettoEsternoBean.getDenominazioneCognome()) && StringUtils.isNotBlank(lSoggettoEsternoBean.getNome())) {
					lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
				}
			}
		}
		if (isAttivoInteressati(bean) && bean.getListaInteressati() != null) {
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			for (SoggEsternoProtBean lInteressatoProtBean : bean.getListaInteressati()) {
				SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
				lSoggettoEsternoBean.setIdSoggetto(lInteressatoProtBean.getIdSoggetto());
				lSoggettoEsternoBean.setTipoSoggetto(lInteressatoProtBean.getTipoSoggetto());
				if (StringUtils.isBlank(lInteressatoProtBean.getTipoSoggetto()) || lInteressatoProtBean.getTipoSoggetto().equals("G")
						|| lInteressatoProtBean.getTipoSoggetto().equals("PG") || lInteressatoProtBean.getTipoSoggetto().equals("PA")
						|| lInteressatoProtBean.getTipoSoggetto().equals("AOOE")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.NOT_SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lInteressatoProtBean.getDenominazione());
				} else if (lInteressatoProtBean.getTipoSoggetto().equals("F") || lInteressatoProtBean.getTipoSoggetto().equals("PF")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lInteressatoProtBean.getCognome());
					lSoggettoEsternoBean.setNome(lInteressatoProtBean.getNome());
				}
				lSoggettoEsternoBean.setCodFiscale(lInteressatoProtBean.getCodFiscale());
				// dati indirizzo
				lSoggettoEsternoBean.setStato(lInteressatoProtBean.getStato());
				lSoggettoEsternoBean.setNomeStato(lInteressatoProtBean.getNomeStato());
				if (StringUtils.isBlank(lSoggettoEsternoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lSoggettoEsternoBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lSoggettoEsternoBean.getStato())) {
					if (StringUtils.isNotBlank(lInteressatoProtBean.getCodToponimo())) {
						lSoggettoEsternoBean.setCodToponimo(lInteressatoProtBean.getCodToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getIndirizzo());
						lSoggettoEsternoBean.setComune(getCodIstatComuneRif());
						lSoggettoEsternoBean.setNomeComuneCitta(getNomeComuneRif());
					} else {
						lSoggettoEsternoBean.setTipoToponimo(lInteressatoProtBean.getTipoToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getToponimo());
						lSoggettoEsternoBean.setComune(lInteressatoProtBean.getComune());
						lSoggettoEsternoBean.setNomeComuneCitta(lInteressatoProtBean.getNomeComune());
					}
					lSoggettoEsternoBean.setFrazione(lInteressatoProtBean.getFrazione());
					lSoggettoEsternoBean.setCap(lInteressatoProtBean.getCap());
				} else {
					lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getIndirizzo());
					lSoggettoEsternoBean.setNomeComuneCitta(lInteressatoProtBean.getCitta());
				}
				lSoggettoEsternoBean.setCivico(lInteressatoProtBean.getCivico());
				lSoggettoEsternoBean.setInterno(lInteressatoProtBean.getInterno());
				lSoggettoEsternoBean.setZona(lInteressatoProtBean.getZona());
				lSoggettoEsternoBean.setComplementoIndirizzo(lInteressatoProtBean.getComplementoIndirizzo());
				lSoggettoEsternoBean.setAppendici(lInteressatoProtBean.getAppendici());
				// natura relazione
				lSoggettoEsternoBean.setCodNaturaRel("I");
				if (StringUtils.isNotBlank(lSoggettoEsternoBean.getDenominazioneCognome())
						&& (lSoggettoEsternoBean.getFlgPersFisica() != Flag.SETTED || StringUtils.isNotBlank(lSoggettoEsternoBean.getNome()))) {
					lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
				}
			}
		}
		if (bean.getListaContribuenti() != null) {
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			for (ContribuenteBean lContribuenteBean : bean.getListaContribuenti()) {
				SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
				lSoggettoEsternoBean.setIdSoggetto(lContribuenteBean.getIdSoggetto());
				lSoggettoEsternoBean.setTipoSoggetto(lContribuenteBean.getTipoSoggetto());
				if (StringUtils.isBlank(lContribuenteBean.getTipoSoggetto()) || lContribuenteBean.getTipoSoggetto().equals("G")
						|| lContribuenteBean.getTipoSoggetto().equals("PG") || lContribuenteBean.getTipoSoggetto().equals("PA")
						|| lContribuenteBean.getTipoSoggetto().equals("AOOE")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.NOT_SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lContribuenteBean.getDenominazione());
				} else if (lContribuenteBean.getTipoSoggetto().equals("F") || lContribuenteBean.getTipoSoggetto().equals("PF")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lContribuenteBean.getCognome());
					lSoggettoEsternoBean.setNome(lContribuenteBean.getNome());
				}
				lSoggettoEsternoBean.setCodFiscale(lContribuenteBean.getCodFiscale());
				lSoggettoEsternoBean.setTipoDocRiconoscimento(lContribuenteBean.getTipoDocRiconoscimento());
				lSoggettoEsternoBean.setEstremiDocRiconoscimento(lContribuenteBean.getEstremiDocRiconoscimento());
				// dati indirizzo
				lSoggettoEsternoBean.setStato(lContribuenteBean.getStato());
				lSoggettoEsternoBean.setNomeStato(lContribuenteBean.getNomeStato());
				if (StringUtils.isBlank(lSoggettoEsternoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lSoggettoEsternoBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lSoggettoEsternoBean.getStato())) {
					if (StringUtils.isNotBlank(lContribuenteBean.getCodToponimo())) {
						lSoggettoEsternoBean.setCodToponimo(lContribuenteBean.getCodToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
						lSoggettoEsternoBean.setComune(getCodIstatComuneRif());
						lSoggettoEsternoBean.setNomeComuneCitta(getNomeComuneRif());
					} else {
						lSoggettoEsternoBean.setTipoToponimo(lContribuenteBean.getTipoToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getToponimo());
						lSoggettoEsternoBean.setComune(lContribuenteBean.getComune());
						lSoggettoEsternoBean.setNomeComuneCitta(lContribuenteBean.getNomeComune());
					}
					lSoggettoEsternoBean.setFrazione(lContribuenteBean.getFrazione());
					lSoggettoEsternoBean.setCap(lContribuenteBean.getCap());
				} else {
					lSoggettoEsternoBean.setToponimoIndirizzo(lContribuenteBean.getIndirizzo());
					lSoggettoEsternoBean.setNomeComuneCitta(lContribuenteBean.getCitta());
				}
				lSoggettoEsternoBean.setCivico(lContribuenteBean.getCivico());
				lSoggettoEsternoBean.setInterno(lContribuenteBean.getInterno());
				lSoggettoEsternoBean.setZona(lContribuenteBean.getZona());
				lSoggettoEsternoBean.setComplementoIndirizzo(lContribuenteBean.getComplementoIndirizzo());
				lSoggettoEsternoBean.setAppendici(lContribuenteBean.getAppendici());
				// natura relazione
				lSoggettoEsternoBean.setCodNaturaRel("CL");
				// altri dati contribuente
				lSoggettoEsternoBean.setProvCISogg(lContribuenteBean.getCodiceACS()); // codice ACS
				lSoggettoEsternoBean.setEmailContribuente(lContribuenteBean.getEmailContribuente()); // email contribuente			
				lSoggettoEsternoBean.setTelefonoContribuente(lContribuenteBean.getTelContribuente()); // tel contribuente
				if (StringUtils.isNotBlank(lSoggettoEsternoBean.getDenominazioneCognome())
						&& (lSoggettoEsternoBean.getFlgPersFisica() != Flag.SETTED || StringUtils.isNotBlank(lSoggettoEsternoBean.getNome()))) {
					lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
				}
			}
		}
		// Cacco Federico 13.06.2017
		// Richiesta accesso atti - richiedenti delegati
		if (bean.getListaRichiedentiDelegati() != null) {
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			for (SoggEsternoProtBean lInteressatoProtBean : bean.getListaRichiedentiDelegati()) {
				SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
				lSoggettoEsternoBean.setIdSoggetto(lInteressatoProtBean.getIdSoggetto());
				lSoggettoEsternoBean.setTipoSoggetto(lInteressatoProtBean.getTipoSoggetto());
				if (StringUtils.isBlank(lInteressatoProtBean.getTipoSoggetto()) || lInteressatoProtBean.getTipoSoggetto().equals("G")
						|| lInteressatoProtBean.getTipoSoggetto().equals("PG") || lInteressatoProtBean.getTipoSoggetto().equals("PA")
						|| lInteressatoProtBean.getTipoSoggetto().equals("AOOE")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.NOT_SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lInteressatoProtBean.getDenominazione());
				} else if (lInteressatoProtBean.getTipoSoggetto().equals("F") || lInteressatoProtBean.getTipoSoggetto().equals("PF")) {
					lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
					lSoggettoEsternoBean.setDenominazioneCognome(lInteressatoProtBean.getCognome());
					lSoggettoEsternoBean.setNome(lInteressatoProtBean.getNome());
				}
				lSoggettoEsternoBean.setCodFiscale(lInteressatoProtBean.getCodFiscale());
				// dati indirizzo
				lSoggettoEsternoBean.setStato(lInteressatoProtBean.getStato());
				lSoggettoEsternoBean.setNomeStato(lInteressatoProtBean.getNomeStato());
				if (StringUtils.isBlank(lSoggettoEsternoBean.getStato()) || _COD_ISTAT_ITALIA.equals(lSoggettoEsternoBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lSoggettoEsternoBean.getStato())) {
					if (StringUtils.isNotBlank(lInteressatoProtBean.getCodToponimo())) {
						lSoggettoEsternoBean.setCodToponimo(lInteressatoProtBean.getCodToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getIndirizzo());
						lSoggettoEsternoBean.setComune(getCodIstatComuneRif());
						lSoggettoEsternoBean.setNomeComuneCitta(getNomeComuneRif());
					} else {
						lSoggettoEsternoBean.setTipoToponimo(lInteressatoProtBean.getTipoToponimo());
						lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getToponimo());
						lSoggettoEsternoBean.setComune(lInteressatoProtBean.getComune());
						lSoggettoEsternoBean.setNomeComuneCitta(lInteressatoProtBean.getNomeComune());
					}
					lSoggettoEsternoBean.setFrazione(lInteressatoProtBean.getFrazione());
					lSoggettoEsternoBean.setCap(lInteressatoProtBean.getCap());
				} else {
					lSoggettoEsternoBean.setToponimoIndirizzo(lInteressatoProtBean.getIndirizzo());
					lSoggettoEsternoBean.setNomeComuneCitta(lInteressatoProtBean.getCitta());
				}
				lSoggettoEsternoBean.setCivico(lInteressatoProtBean.getCivico());
				lSoggettoEsternoBean.setInterno(lInteressatoProtBean.getInterno());
				lSoggettoEsternoBean.setZona(lInteressatoProtBean.getZona());
				lSoggettoEsternoBean.setComplementoIndirizzo(lInteressatoProtBean.getComplementoIndirizzo());
				lSoggettoEsternoBean.setAppendici(lInteressatoProtBean.getAppendici());
				// natura relazione
				lSoggettoEsternoBean.setCodNaturaRel("DEL");
				// email e telefono
				lSoggettoEsternoBean.setEmailContribuente(lInteressatoProtBean.getEmail());
				lSoggettoEsternoBean.setTelefonoContribuente(lInteressatoProtBean.getTel());
				if (StringUtils.isNotBlank(lSoggettoEsternoBean.getDenominazioneCognome())
						&& (lSoggettoEsternoBean.getFlgPersFisica() != Flag.SETTED || StringUtils.isNotBlank(lSoggettoEsternoBean.getNome()))) {
					lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
				}
			}
		}	
		
		// Cacco Federico 19.06.2017
		// Richiesta accesso atti - ufficio richiedente
		if ((StringUtils.isNotBlank(bean.getCognomeIncaricatoPrelievoPerUffRichiedente())) && (StringUtils.isNotBlank(bean.getNomeIncaricatoPrelievoPerUffRichiedente()))){
			// Salvo la lista dell'incaricato del prelievo per l'ufficio richiedente
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
			lSoggettoEsternoBean.setIdSoggetto(bean.getIdIncaricatoPrelievoPerUffRichiedente());
			// Sono sempre unità personale
			lSoggettoEsternoBean.setTipoSoggetto("UP");				
			lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
			// natura relazione
			lSoggettoEsternoBean.setCodNaturaRel("IRIT");
			// dati richiedente
			lSoggettoEsternoBean.setTipoRichiedente("U");	
			lSoggettoEsternoBean.setDenominazioneCognome(bean.getCognomeIncaricatoPrelievoPerUffRichiedente());
			lSoggettoEsternoBean.setNome(bean.getNomeIncaricatoPrelievoPerUffRichiedente());
			lSoggettoEsternoBean.setCodiceRapido(bean.getCodRapidoIncaricatoPrelievoPerUffRichiedente());
			lSoggettoEsternoBean.setTelefonoContribuente(bean.getTelefonoIncaricatoPrelievoPerUffRichiedente());
			lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
		}
		
		// Cacco Federico 19.06.2017
		// Richiesta accesso atti - richiedente esterno
		if ((StringUtils.isNotBlank(bean.getCognomeIncaricatoPrelievoPerRichEsterno())) && (StringUtils.isNotBlank(bean.getCognomeIncaricatoPrelievoPerRichEsterno()))) {
			// Salvo la lista dell'incaricato del prelievo per il richiedenre esterno
			if (lListAltriSoggettiEsterni == null) {
				lListAltriSoggettiEsterni = new ArrayList<SoggettoEsternoBean>();
			}
			SoggettoEsternoBean lSoggettoEsternoBean = new SoggettoEsternoBean();
			// Sono sempre persone fisiche
			lSoggettoEsternoBean.setTipoSoggetto("PF");				
			lSoggettoEsternoBean.setFlgPersFisica(Flag.SETTED);
			// natura relazione
			lSoggettoEsternoBean.setCodNaturaRel("IRIT");
			// dati richiedente
			lSoggettoEsternoBean.setTipoRichiedente("E");	
			lSoggettoEsternoBean.setDenominazioneCognome(bean.getCognomeIncaricatoPrelievoPerRichEsterno());
			lSoggettoEsternoBean.setNome(bean.getNomeIncaricatoPrelievoPerRichEsterno());				
			lSoggettoEsternoBean.setCodFiscale(bean.getCodiceFiscaleIncaricatoPrelievoPerRichEsterno());
			lSoggettoEsternoBean.setEmailContribuente(bean.getEmailIncaricatoPrelievoPerRichEsterno());				
			lSoggettoEsternoBean.setTelefonoContribuente(bean.getTelefonoIncaricatoPrelievoPerRichEsterno());
			lListAltriSoggettiEsterni.add(lSoggettoEsternoBean);
		}
		
		lCreaModDocumentoInBean.setAltriSoggettiEsterni(lListAltriSoggettiEsterni);
	}
	
	// Dev'essere implementato come recuperaListaAttiRichiestaAccessoAtti() in NuovaRichiestaAccessoAttiDataSource (utilizzata per l'iniezione dei dati a maschera nel modello)
	protected void salvaListaAttiRichiestaAccessoAtti(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) throws Exception {	
		// Salvo gli atti richiesti. Se non ho atti devo comunque passare una lista vuota
		List<AttiRichiestiXMLBean> lAttiRichiestiXMLBeans = new ArrayList<AttiRichiestiXMLBean>();
		if (bean.getListaAttiRichiesti() != null){
			// Ho degli atti richiesti
			logProtDS.debug("Salvo gli atti richiesti");
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
		lCreaModDocumentoInBean.setAttiRichiesti(lAttiRichiestiXMLBeans);
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
	
	protected void salvaAttributiCustom(ProtocollazioneBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {		
		salvaAttributiCustomSemplici(bean, sezioneCacheAttributiDinamici);
		salvaAttributiCustomLista(bean, sezioneCacheAttributiDinamici);
	}
	
	protected void salvaAttributiCustomSemplici(ProtocollazioneBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		
		boolean isCompilazioneModulo = getExtraparams().get("isCompilazioneModulo") != null && "true".equals(getExtraparams().get("isCompilazioneModulo"));
		boolean isPropostaOrganigramma = getExtraparams().get("isPropostaOrganigramma") != null && "true".equals(getExtraparams().get("isPropostaOrganigramma"));
		
		if(isCompilazioneModulo) {
			sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_DA_COMP_MODULO_Ud", "1"));
		}
		
		if(isPropostaOrganigramma) {
			if(StringUtils.isNotBlank(bean.getIdUoRevisioneOrganigramma())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("ID_UO_REVISIONE_ORGANIGRAMMA_Doc", bean.getIdUoRevisioneOrganigramma()));
			}
			if(StringUtils.isNotBlank(bean.getIdUoPadreRevisioneOrganigramma())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("ID_UO_PADRE_REVISIONE_ORGANIGRAMMA_Doc", bean.getIdUoPadreRevisioneOrganigramma()));
			}
			if(StringUtils.isNotBlank(bean.getCodRapidoUoRevisioneOrganigramma())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("NRI_LIVELLI_UO_REVISIONE_ORGANIGRAMMA_Doc", bean.getCodRapidoUoRevisioneOrganigramma()));							
			}
			if(StringUtils.isNotBlank(bean.getNomeUoRevisioneOrganigramma())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("NOME_UO_REVISIONE_ORGANIGRAMMA_Doc", bean.getNomeUoRevisioneOrganigramma()));		
			}
		}
		
		if (isAttivoProtocolloWizard(bean)) {
			sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_ORIGINALE_CARTACEO_Doc",
							bean.getFlgOriginaleCartaceo() != null && bean.getFlgOriginaleCartaceo() ? "1" : "0"));
			sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_COPIA_SOSTITUTIVA_Doc",
							bean.getFlgCopiaSostitutiva() != null && bean.getFlgCopiaSostitutiva() ? "1" : "0"));
		}
		
		if (isClienteComuneMilano()) {
			//Identificativo di workflow
//			if(StringUtils.isNotBlank(bean.getSiglaIdentificativoWF())) {
//				sezioneCacheAttributiDinamici.getVariabile().add(
//					SezioneCacheAttributiDinamici.createVariabileSemplice("SEZIONALE_ONLYONE_Doc", 
//						bean.getSiglaIdentificativoWF() != null  ? bean.getSiglaIdentificativoWF() : ""));
//			}
			if(StringUtils.isNotBlank(bean.getNroIdentificativoWF())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("NRO_ONLYONE_Doc", 
							bean.getNroIdentificativoWF() != null  ? bean.getNroIdentificativoWF() : ""));
			}
			if(StringUtils.isNotBlank(bean.getAnnoIdentificativoWF())) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("ANNO_ONLYONE_Doc", 
							bean.getAnnoIdentificativoWF() != null  ? bean.getAnnoIdentificativoWF() : ""));
			}
			if(bean.getFlgCaricamentoPGPregressoDaGUI() != null && bean.getFlgCaricamentoPGPregressoDaGUI()) {
				sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_CARICAMENTO_PG_PREGRESSO_DA_GUI_Ud", "1"));
			}
		}

		// Salvo il responsabile del procedimento
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "SHOW_RESP_PROC_IN_ATTI")) {
			sezioneCacheAttributiDinamici.getVariabile().add(
					SezioneCacheAttributiDinamici.createVariabileSemplice("ID_USER_RESP_PROC_Ud", bean.getIdUserRespProc()));
		}
		
		// Se sono in richiesta accesso atti e il richiedente è interno
		if (isRichiestaAccessoAttiRichiedenteInterno(bean)){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("RICH_ACCESSO_ATTI_CON_RICH_INTERNO_Doc", "1"));
		}
				
		if (isRichiestaAccessoAtti()) {
			// Salvo FlgRichAttiFabbricaVisuraSue per richiesta accesso atti
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_RICH_ATTI_FABBRICA_VISURA_SUE_Doc", bean.getFlgRichAttiFabbricaVisuraSue() != null && bean.getFlgRichAttiFabbricaVisuraSue() ? "1" : "0"));
			
			// Salvo FlgRichModificheVisuraSue per richiesta accesso atti
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_RICH_MODIFICHE_VISURA_SUE_Doc", bean.getFlgRichModificheVisuraSue() != null && bean.getFlgRichModificheVisuraSue() ? "1" : "0"));
			
			// Salvo FlgAltriAttiDaRicercareVisuraSue per richiesta accesso atti
		    sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("FLG_ALTRI_ATTI_DA_RICERCARE_VISURA_SUE_Doc", bean.getFlgAltriAttiDaRicercareVisuraSue() != null && bean.getFlgAltriAttiDaRicercareVisuraSue() ? "1" : "0"));
		}
		
		// Salvo motivo e dettagli della richista accesso atti
		if (StringUtils.isNotBlank(bean.getMotivoRichiestaAccessoAtti())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("MOTIVO_RICHIESTA_ACCESSO_ATTI_Doc", bean.getMotivoRichiestaAccessoAtti()));
		}
		
		if (StringUtils.isNotBlank(bean.getDettagliRichiestaAccessoAtti())){
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("DETTAGLI_RICHIESTA_ACCESSO_ATTI_Doc", bean.getDettagliRichiestaAccessoAtti()));
		}
		
		if (StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(getSession(), "DOC_PRESSO_CENTRO_POSTA"))) {
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("PRESSO_CENTRO_POSTA_Ud", StringUtils.isNotBlank(bean.getDocPressoCentroPosta()) ? bean.getDocPressoCentroPosta() : ""));
		}
				
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "NOTE_OBBL_X_REG_SENZA_FILE_PRIMARIO")) {
			sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("NOTE_MANCANZA_FILE_Ud", StringUtils.isNotBlank(bean.getNoteMancanzaFile()) ? bean.getNoteMancanzaFile() : ""));
		}
		
	}

	protected void salvaAttributiCustomLista(ProtocollazioneBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		if (isAttivoProtocolloWizard(bean) || isAttivoAltreVieSenzaWizard() || isRichiestaAccessoAtti() || isPropostaAtto()) {
			if (bean.getListaAltreVie() != null) {
				ArrayList<AltreUbicazioniBean> lListAltreUbicazioni = new ArrayList<AltreUbicazioniBean>();
				for (AltraViaProtBean lAltraViaProtBean : bean.getListaAltreVie()) {
					AltreUbicazioniBean lAltreUbicazioniBean = new AltreUbicazioniBean();
					lAltreUbicazioniBean.setStato(lAltraViaProtBean.getStato());
					lAltreUbicazioniBean.setNomeStato(lAltraViaProtBean.getNomeStato());
					if (StringUtils.isBlank(lAltreUbicazioniBean.getStato()) || _COD_ISTAT_ITALIA.equals(lAltreUbicazioniBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lAltreUbicazioniBean.getStato())) {
						if (StringUtils.isNotBlank(lAltraViaProtBean.getCodToponimo())) {
							lAltreUbicazioniBean.setCodToponimo(lAltraViaProtBean.getCodToponimo());
							lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getIndirizzo());
							lAltreUbicazioniBean.setComune(getCodIstatComuneRif());
							lAltreUbicazioniBean.setNomeComuneCitta(getNomeComuneRif());
						} else {
							lAltreUbicazioniBean.setTipoToponimo(lAltraViaProtBean.getTipoToponimo());
							lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getToponimo());
							lAltreUbicazioniBean.setComune(lAltraViaProtBean.getComune());
							lAltreUbicazioniBean.setNomeComuneCitta(lAltraViaProtBean.getNomeComune());
						}
						lAltreUbicazioniBean.setFrazione(lAltraViaProtBean.getFrazione());
						lAltreUbicazioniBean.setCap(lAltraViaProtBean.getCap());
					} else {
						lAltreUbicazioniBean.setToponimoIndirizzo(lAltraViaProtBean.getIndirizzo());
						lAltreUbicazioniBean.setNomeComuneCitta(lAltraViaProtBean.getCitta());
					}
					lAltreUbicazioniBean.setCivico(lAltraViaProtBean.getCivico());
					lAltreUbicazioniBean.setAppendici(lAltraViaProtBean.getAppendici());
					lAltreUbicazioniBean.setZona(lAltraViaProtBean.getZona());
					lAltreUbicazioniBean.setComplementoIndirizzo(lAltraViaProtBean.getComplementoIndirizzo());

					lListAltreUbicazioni.add(lAltreUbicazioniBean);
				}
				sezioneCacheAttributiDinamici.getVariabile().add(
						SezioneCacheAttributiDinamici.createVariabileLista("ALTRE_UBICAZIONI_Doc",
								new XmlUtilitySerializer().createVariabileLista(lListAltreUbicazioni)));
			}
		}				
	}
	
	public ProtocollazioneBean annullamentoLogico(ProtocollazioneBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
		delUdDocVerInput.setFlgtipotargetin("U");
		delUdDocVerInput.setIduddocin(bean.getIdUd());
		delUdDocVerInput.setFlgcancfisicain(null);

		DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
		StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = delUdDocVer.execute(getLocale(), loginBean, delUdDocVerInput);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	public ProtocollazioneBean cancellazioneFisica(ProtocollazioneBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
		delUdDocVerInput.setFlgtipotargetin("U");
		delUdDocVerInput.setIduddocin(bean.getIdUd());
		delUdDocVerInput.setFlgcancfisicain(new Integer(1));

		DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
		StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = delUdDocVer.execute(getLocale(), loginBean, delUdDocVerInput);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		return bean;
	}

	@Override
	public ProtocollazioneBean remove(ProtocollazioneBean bean) throws Exception {
		return null;
	}

	@Override
	public PaginatorBean<ProtocollazioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ProtocollazioneBean bean) throws Exception {
		return null;
	}

	private TipoNumerazioneBean creaTipoProtocolloGenerale(ProtocollazioneBean bean) {

		if (bean.getProtocolloGenerale() != null && bean.getProtocolloGenerale()) {
			
			TipoNumerazioneBean lTipoProtocolloGenerale = new TipoNumerazioneBean();
			
			lTipoProtocolloGenerale.setCategoria("PG");
			lTipoProtocolloGenerale.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
			
			return lTipoProtocolloGenerale;			
		}
		
		return null;
	}
	
	private TipoNumerazioneBean creaTipoAltraNumerazione(ProtocollazioneBean bean) {

		if(StringUtils.isNotBlank(bean.getCodCategoriaAltraNumerazione()) || StringUtils.isNotBlank(bean.getSiglaAltraNumerazione())) {
			
			TipoNumerazioneBean lTipoAltraNumerazioneBean = new TipoNumerazioneBean();
			
			lTipoAltraNumerazioneBean.setCategoria(bean.getCodCategoriaAltraNumerazione());
			lTipoAltraNumerazioneBean.setSigla(bean.getSiglaAltraNumerazione());
			lTipoAltraNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
			
			return lTipoAltraNumerazioneBean;
		}
		
		return null;
	}

	public ExportBean stampaFileCertificazione(AttachmentBean attachment) throws Exception {

		boolean aggiungiFirma = Boolean.parseBoolean(getExtraparams().get("aggiungiFirma"));

		String processId = getExtraparams().get("processId");

		Date dataRiferimento = null;

		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_DATA_RIF_VERIFICA_FIRMA")) {
			String dataRiferimentoString = getExtraparams().get("dataRiferimento");
			if (dataRiferimentoString != null && !"".equals(dataRiferimentoString)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dataRiferimento = sdf.parse(dataRiferimentoString);
			}
		}		

		File realFile = StorageImplementation.getStorage().getRealFile(attachment.getUriAttach());
		String fileUrl = realFile.toURI().toString();
		String fileName = attachment.getInfoFileAttach().getCorrectFileName();
		
		FirmaUtility lFirmaUtility = new FirmaUtility();
		File file = lFirmaUtility.creaFileCertificazione(fileUrl, fileName, aggiungiFirma, processId, dataRiferimento);

		ExportBean exportBean = new ExportBean();

		if (file != null) {

			exportBean.setTempFileOut(StorageImplementation.getStorage().store(file));

			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setCorrectFileName("Certificazione.pdf");
			lMimeTypeFirmaBean.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(file));
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setFirmaValida(false);
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			exportBean.setInfoFileOut(lMimeTypeFirmaBean);

		}
		return exportBean;
	}

	/**
	 * Genera un archivio contenente tutti i documenti contenuti nell'unità documentale e nelle relative sotto unità
	 * 
	 * @param unitaDoc
	 * @return
	 * @throws Exception
	 */
	public DownloadDocsZipBean generateDocsZip(ProtocollazioneBean downloadDocZipBean) throws Exception {

		String operazione = getExtraparams().get("operazione");
		String posizioneTimbro = getExtraparams().get("posizioneTimbro");
		String rotazioneTimbro = getExtraparams().get("rotazioneTimbro");
		String tipoPagina = getExtraparams().get("tipoPagina");
		
		OpzioniTimbroBean opzioniTimbroScelteUtente = new OpzioniTimbroBean();
		opzioniTimbroScelteUtente.setPosizioneTimbro(posizioneTimbro);
		opzioniTimbroScelteUtente.setRotazioneTimbro(rotazioneTimbro);
		opzioniTimbroScelteUtente.setTipoPagina(tipoPagina);
		
		String errorLimiteZip = getExtraparams().get("limiteMaxZipError");
		// nome dei file presenti nello zip, i quali devono essere univoci
		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();

		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");

		String segnatura = escapeSegnatura(getExtraparams().get("segnatura"));

		// nome dello zip
		String prefissoNomeFileZip = AurigaAbstractDataSourceUtilities.getPrefissoNomeFileZip (operazione, false);
		File zipFile = new File(tempPath + prefissoNomeFileZip + segnatura + ".zip");

		int codice = addDocumentToZip(downloadDocZipBean, zipFileNames, zipFile, segnatura, operazione, opzioniTimbroScelteUtente);

		try {

			DownloadDocsZipBean retValue = new DownloadDocsZipBean();

			if (codice == OK || codice == OK_CON_WARNING_TIMBRO || codice == OK_CON_WARNING_SBUSTA) {
				String zipUri = StorageImplementation.getStorage().store(zipFile);
				retValue.setStorageZipRemoteUri(zipUri);
				retValue.setZipName(zipFile.getName());
				MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
				infoFile.setBytes(zipFile.length());
				retValue.setInfoFile(infoFile);
				if(codice == OK_CON_WARNING_TIMBRO) {
					retValue.setWarningTimbro("Per alcuni file NON è stato possibile apporre il timbro; sono presenti nello zip nella forma originale");
				}
				else if(codice == OK_CON_WARNING_SBUSTA) {
					retValue.setWarningSbusta("Per alcuni file NON è stato possibile estrarre il file contenuto della busta firma p7m; sono presenti nello zip nella forma originale di p7m");
				}
			}
			else if (codice == SUPERATO_LIMITE) {
				retValue.setMessage(errorLimiteZip);
			} else if (codice == NESSUN_FILE) {
				retValue.setMessage("Nessun documento con file è presente nella selezione");
			}

			return retValue;
		} finally {
			// una volta salvato in storage lo posso eliminare localmente
			deleteLocalCopy(zipFile);
		}
	}

	/**
	 * @param segnatura
	 * @return
	 */
	protected String escapeSegnatura(String segnatura) {
		if (segnatura != null) {
			segnatura = segnatura.replace(" ", "_");
			segnatura = segnatura.replace(".", "_");
			segnatura = segnatura.replace("\\", "_");
			segnatura = segnatura.replace("/", "_");
		} else {
			Calendar currentDate = Calendar.getInstance();

			segnatura = Integer.toString(currentDate.get(Calendar.DAY_OF_WEEK)) + "_" + Integer.toString(currentDate.get(Calendar.HOUR))
					+ Integer.toString(currentDate.get(Calendar.MINUTE)) + Integer.toString(currentDate.get(Calendar.SECOND))
					+ Integer.toString(currentDate.get(Calendar.MILLISECOND));
		}
		return segnatura;
	}

	/**
	 * Aggiunge l'unità documentaria specificata allo zip fornito
	 * 
	 * @param downloadDocZipBean
	 * @param zipFileNames
	 * @param lInfoFileUtility
	 * @param tempPath
	 * @param zipFile
	 * @param operazione 
	 * @param opzioniTimbroScelteUtente 
	 * @throws Exception
	 */
	protected int addDocumentToZip(ProtocollazioneBean downloadDocZipBean, Map<String, Integer> zipFileNames,
			 File zipFile, String segnatura, String operazione, OpzioniTimbroBean opzioniTimbroScelteUtente) throws Exception {
		
		boolean vuoto = true;

		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");

		long MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Long.decode(maxSize) : 104857600);
		long lengthZip = 0;

		List<FileScaricoZipBean> listaFileDaScaricare = new ArrayList<>();
		
		if(operazione != null && "scaricaCompletiXAtti".equals(operazione)) {
			
			List<FileCompletiXAttiBean> fileCompletiXAtti = downloadDocZipBean.getListaFileCompletiXAtti();

			if (fileCompletiXAtti != null) { 
				for (FileCompletiXAttiBean file : fileCompletiXAtti) {
					if (file.getUri() != null && !file.getUri().isEmpty()) {
						
						long bytes = file.getDimensione() != null ? file.getDimensione().longValue() : 0;
						lengthZip += bytes;

						if (lengthZip > MAX_SIZE) {
							return SUPERATO_LIMITE;
						}

						String attachmentFileName = segnatura + " - " + file.getNomeFile();
						
						FileScaricoZipBean fileDaScaricare = new FileScaricoZipBean();
						fileDaScaricare.setNomeFile(attachmentFileName);
						fileDaScaricare.setUriFile(file.getUri());
						
						vuoto = false;
						
						listaFileDaScaricare.add(fileDaScaricare);
					}
				}
			}
			
		} else {
		
			String filePrimarioUri = downloadDocZipBean.getUriFilePrimario();
			DocumentBean filePrimarioOmissis = downloadDocZipBean.getFilePrimarioOmissis();

			if (filePrimarioUri != null && !filePrimarioUri.isEmpty()) {
				if (filePrimarioOmissis.getUriFile() != null && !filePrimarioOmissis.getUriFile().isEmpty()) {
					// Se il file primario ha sia versione omissis che integrale
					if (filePrimarioOmissis.getInfoFile() != null) {
						
						lengthZip += filePrimarioOmissis.getInfoFile().getBytes();
	
						if (lengthZip > MAX_SIZE) {
							return SUPERATO_LIMITE;
						}
	
						String nomeFilePrimarioOmissis = segnatura + " - File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile();
	
						FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
						filePrimarioDaScaricare.setUriFile(filePrimarioOmissis.getUriFile());
						filePrimarioDaScaricare.setNomeFile(nomeFilePrimarioOmissis);
						filePrimarioDaScaricare.setFirmato(filePrimarioOmissis.getInfoFile().isFirmato());
						filePrimarioDaScaricare.setCanBeTimbrato(canBeTimbrato(filePrimarioOmissis.getInfoFile()));
						filePrimarioDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
						filePrimarioDaScaricare.setMimeType(filePrimarioOmissis.getInfoFile().getMimetype());
						filePrimarioDaScaricare.setIdDoc(new BigDecimal(filePrimarioOmissis.getIdDoc()));
	
						vuoto = false;
						
						listaFileDaScaricare.add(filePrimarioDaScaricare);
					}
					if (downloadDocZipBean.getInfoFile() != null) {
						
						lengthZip += downloadDocZipBean.getInfoFile().getBytes();
	
						if (lengthZip > MAX_SIZE) {
							return SUPERATO_LIMITE;
						}
	
						String nomeFilePrimarioIntegrale = segnatura + " - File primario (vers. integrale) - " + downloadDocZipBean.getNomeFilePrimario();
	
						FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
						filePrimarioDaScaricare.setUriFile(downloadDocZipBean.getUriFilePrimario());
						filePrimarioDaScaricare.setNomeFile(nomeFilePrimarioIntegrale);
						filePrimarioDaScaricare.setFirmato(downloadDocZipBean.getInfoFile().isFirmato());
						filePrimarioDaScaricare.setCanBeTimbrato(canBeTimbrato(downloadDocZipBean.getInfoFile()));
						filePrimarioDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
						filePrimarioDaScaricare.setMimeType(downloadDocZipBean.getInfoFile().getMimetype());
						filePrimarioDaScaricare.setIdDoc(downloadDocZipBean.getIdDocPrimario());
	
						vuoto = false;
						
						listaFileDaScaricare.add(filePrimarioDaScaricare);
					}
				} else {
					// Se il file primario ha solo versione integrale
					if (downloadDocZipBean.getInfoFile() != null) {
	
						lengthZip += downloadDocZipBean.getInfoFile().getBytes();
	
						if (lengthZip > MAX_SIZE) {
							return SUPERATO_LIMITE;
						}
	
						String nomeFilePrimarioIntegrale = segnatura + " - File primario - " + downloadDocZipBean.getNomeFilePrimario();
	
						FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
						filePrimarioDaScaricare.setUriFile(downloadDocZipBean.getUriFilePrimario());
						filePrimarioDaScaricare.setNomeFile(nomeFilePrimarioIntegrale);
						filePrimarioDaScaricare.setFirmato(downloadDocZipBean.getInfoFile().isFirmato());
						filePrimarioDaScaricare.setCanBeTimbrato(canBeTimbrato(downloadDocZipBean.getInfoFile()));
						filePrimarioDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
						filePrimarioDaScaricare.setMimeType(downloadDocZipBean.getInfoFile().getMimetype());
						filePrimarioDaScaricare.setIdDoc(downloadDocZipBean.getIdDocPrimario());
						
						vuoto = false;
						
						listaFileDaScaricare.add(filePrimarioDaScaricare);
					}
				}
			} else if (filePrimarioOmissis.getUriFile() != null  && !filePrimarioOmissis.getUriFile().isEmpty()) {
				// Se il file primario ha solo versione omissis
				if (filePrimarioOmissis.getInfoFile() != null) {
	
					lengthZip += filePrimarioOmissis.getInfoFile().getBytes();
	
					if (lengthZip > MAX_SIZE) {
						return SUPERATO_LIMITE;
					}
	
					String nomeFilePrimarioOmissis = segnatura + " - File primario (vers. con omissis) - " + filePrimarioOmissis.getNomeFile();
	
					FileScaricoZipBean filePrimarioDaScaricare = new FileScaricoZipBean();
					filePrimarioDaScaricare.setUriFile(filePrimarioOmissis.getUriFile());
					filePrimarioDaScaricare.setNomeFile(nomeFilePrimarioOmissis);
					filePrimarioDaScaricare.setFirmato(filePrimarioOmissis.getInfoFile().isFirmato());
					filePrimarioDaScaricare.setCanBeTimbrato(canBeTimbrato(filePrimarioOmissis.getInfoFile()));	
					filePrimarioDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
					filePrimarioDaScaricare.setMimeType(filePrimarioOmissis.getInfoFile().getMimetype());
					filePrimarioDaScaricare.setIdDoc(new BigDecimal(filePrimarioOmissis.getIdDoc()));
					
					vuoto = false;
					
					listaFileDaScaricare.add(filePrimarioDaScaricare);
				}
			}
			
			List<AllegatoProtocolloBean> allegati = downloadDocZipBean.getListaAllegati();
	
			if (allegati != null) {
				for (AllegatoProtocolloBean allegato : allegati) {
					if ((allegato.getUriFileAllegato() == null || allegato.getUriFileAllegato().isEmpty()) && 
							(allegato.getUriFileOmissis() == null || allegato.getUriFileOmissis().isEmpty()) ) {
						// Se non esiste un file associato all'allegato corrente, nè omissis nè integrale
						continue;
					}
					if (allegato.getUriFileAllegato() != null && !allegato.getUriFileAllegato().isEmpty()) {
						if(allegato.getUriFileOmissis() == null || allegato.getUriFileOmissis().isEmpty()) {
							// Se ho solo la versione integrale dell'allegato
							if (allegato.getInfoFile() != null) {
								
								lengthZip += allegato.getInfoFile().getBytes();
	
								if (lengthZip > MAX_SIZE) {
									return SUPERATO_LIMITE;
								}
	
								String attachmentFileName = segnatura + " - Allegato N° " + allegato.getNumeroProgrAllegato() + " - " + allegato.getNomeFileAllegato();
								
								FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
								fileAllegatoDaScaricare.setNomeFile(attachmentFileName);
								fileAllegatoDaScaricare.setUriFile(allegato.getUriFileAllegato());
								fileAllegatoDaScaricare.setFirmato(allegato.getInfoFile().isFirmato());
								fileAllegatoDaScaricare.setCanBeTimbrato(canBeTimbrato(allegato.getInfoFile()));
								fileAllegatoDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
								fileAllegatoDaScaricare.setMimeType(allegato.getInfoFile().getMimetype());
								fileAllegatoDaScaricare.setIdDoc(allegato.getIdDocAllegato());
								
								vuoto = false;
								
								listaFileDaScaricare.add(fileAllegatoDaScaricare);
							}
						} else {
							// Se ho sia versione integrale che versione con omissis dell'allegato
							if (allegato.getInfoFile() != null) {
								
								lengthZip += allegato.getInfoFile().getBytes();
								
								if (lengthZip > MAX_SIZE) {
									return SUPERATO_LIMITE;
								}
								
								String attachmentFileNameIntegrale = segnatura + " - Allegato N° " + allegato.getNumeroProgrAllegato() + " (vers. integrale) - " + allegato.getNomeFileAllegato();
								
								FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
								fileAllegatoDaScaricare.setNomeFile(attachmentFileNameIntegrale);
								fileAllegatoDaScaricare.setUriFile(allegato.getUriFileAllegato());
								fileAllegatoDaScaricare.setFirmato(allegato.getInfoFile().isFirmato());
								fileAllegatoDaScaricare.setCanBeTimbrato(canBeTimbrato(allegato.getInfoFile()));
								fileAllegatoDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
								fileAllegatoDaScaricare.setMimeType(allegato.getInfoFile().getMimetype());
								fileAllegatoDaScaricare.setIdDoc(allegato.getIdDocAllegato());
								
								vuoto = false;
								
								listaFileDaScaricare.add(fileAllegatoDaScaricare);
							}						
							if(allegato.getInfoFileOmissis() != null) {
								
								lengthZip += allegato.getInfoFileOmissis().getBytes();
	
								if (lengthZip > MAX_SIZE) {
									return SUPERATO_LIMITE;
								}
	
								String attachmentFileNameOmissis = segnatura + " - Allegato N° " + allegato.getNumeroProgrAllegato() + " (vers. con omissis) - " + allegato.getNomeFileOmissis();
								
								FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
								fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
								fileAllegatoDaScaricare.setUriFile(allegato.getUriFileOmissis());
								fileAllegatoDaScaricare.setFirmato(allegato.getInfoFileOmissis().isFirmato());
								fileAllegatoDaScaricare.setCanBeTimbrato(canBeTimbrato(allegato.getInfoFileOmissis()));
								fileAllegatoDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
								fileAllegatoDaScaricare.setMimeType(allegato.getInfoFile().getMimetype());
								fileAllegatoDaScaricare.setIdDoc(allegato.getIdDocAllegato());
								
								vuoto = false;
								
								listaFileDaScaricare.add(fileAllegatoDaScaricare);
							}
						}
					} else {
						// Se ho solo la versione con omissis dell'allegato
						if(allegato.getInfoFileOmissis() != null) {
							
							lengthZip += allegato.getInfoFileOmissis().getBytes();
	
							if (lengthZip > MAX_SIZE) {
								return SUPERATO_LIMITE;
							}
	
							String attachmentFileNameOmissis = segnatura + " - Allegato N° " + allegato.getNumeroProgrAllegato() + " (vers. con omissis) - " + allegato.getNomeFileOmissis();
							
							FileScaricoZipBean fileAllegatoDaScaricare = new FileScaricoZipBean();
							fileAllegatoDaScaricare.setNomeFile(attachmentFileNameOmissis);
							fileAllegatoDaScaricare.setUriFile(allegato.getUriFileOmissis());
							fileAllegatoDaScaricare.setFirmato(allegato.getInfoFileOmissis().isFirmato());
							fileAllegatoDaScaricare.setCanBeTimbrato(canBeTimbrato(allegato.getInfoFileOmissis()));
							fileAllegatoDaScaricare.setIdUd(downloadDocZipBean.getIdUd());
							fileAllegatoDaScaricare.setMimeType(allegato.getInfoFile().getMimetype());
							fileAllegatoDaScaricare.setIdDoc(allegato.getIdDocAllegato());
							
							listaFileDaScaricare.add(fileAllegatoDaScaricare);
							
							vuoto = false;
						}
					}	
				}
			}
			
		}
		
		Integer warning = AurigaAbstractDataSourceUtilities.addFileToZip(zipFileNames, zipFile, listaFileDaScaricare, operazione, opzioniTimbroScelteUtente, getSession());

		/*Se non ho aggiunto nessun file allo zip*/
		if(vuoto) {
			return NESSUN_FILE;
		}else {
			/*Se ho aggiunto i file allo zip ma c'è stato un errore nella timbratura o nello sbustamento lo segnalo con un warning*/
			if(warning!=null) {
				return warning;
			}else {
				/*se è stato confezionato correttamente lo zip*/
				return OK;
			}
		}
	}

	protected void deleteLocalCopy(File localCopy) {
		try {
			FileDeleteStrategy.FORCE.delete(localCopy);
		} catch (Exception e) {
			logProtDS.error(String.format("Non è stato possibile cancellare il file %1$s, si è verificata la seguente eccezione", localCopy.getAbsoluteFile()), e);
		}
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

	private static void addDocumentoCollegato(CreaModDocumentoInBean creaModDocumentoInBean, List<DocumentoCollegato> listaDaAggiungere) {
		List<DocumentoCollegato> listaCorrente = creaModDocumentoInBean.getDocCollegato();
		if (listaCorrente == null) {
			creaModDocumentoInBean.setDocCollegato(listaDaAggiungere);
		} else {
			listaCorrente.addAll(listaDaAggiungere);
			creaModDocumentoInBean.setDocCollegato(listaCorrente);
		}
	}

	public TipoDocumentoBean getIdDocType(TipoDocumentoBean tipoDocumentoBeanIn) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}

		// ***********************************
		// INPUT
		// Iddominioin = dominio
		// NomeDocTypeIn = nome del tipo
		// Flgsolovldin = 1
		// ***********************************
		DmpkUtilityFinddoctype_jBean input = new DmpkUtilityFinddoctype_jBean();
		input.setIdspaooin(StringUtils.isNotBlank(idDominio) ? new BigDecimal(idDominio) : null);
		input.setFlgsolovldin(new Integer(1));
		input.setNomedoctypein(tipoDocumentoBeanIn.getDescTipoDocumento());

		// ***********************************
		// OUTPUT
		// IdDocTypeOut = id del tipo
		// ***********************************

		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());

		DmpkUtilityFinddoctype_j lDmpkUtilityFinddoctype = new DmpkUtilityFinddoctype_j();
		StoreResultBean<DmpkUtilityFinddoctype_jBean> output = lDmpkUtilityFinddoctype.execute(getLocale(), lSchemaBean, input);
		TipoDocumentoBean lTipoDocumentoBean = new TipoDocumentoBean();
		if (output.isInError()) {
			addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
			return lTipoDocumentoBean;
		}

		if (output.getResultBean().getIddoctypeout() != null)
			lTipoDocumentoBean.setIdTipoDocumento(output.getResultBean().getIddoctypeout().toString());

		return lTipoDocumentoBean;

	}
	
	public ProtocollazioneBean recuperaDatiProtocolloPGWeb(ProtocollazioneBean bean) throws Exception {

		if(isClienteComuneMilano()) { 
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();

			DmpkRepositoryGuiGetprotdapgwebBean input = new DmpkRepositoryGuiGetprotdapgwebBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setSiglaprotin(StringUtils.isNotBlank(bean.getSiglaProtocolloPregresso()) ? bean.getSiglaProtocolloPregresso() : "PG");
			input.setNumprotin(StringUtils.isNotBlank(bean.getNroProtocolloPregresso()) ? new BigDecimal(bean.getNroProtocolloPregresso()) : null);
			input.setAnnoprotin(StringUtils.isNotBlank(bean.getAnnoProtocolloPregresso()) ? new BigDecimal(bean.getAnnoProtocolloPregresso()) : null);
			input.setSubprotin(StringUtils.isNotBlank(bean.getSubProtocolloPregresso()) ? new BigDecimal(bean.getSubProtocolloPregresso()) : null);
			
			DmpkRepositoryGuiGetprotdapgweb lDmpkRepositoryGuiGetprotdapgweb = new DmpkRepositoryGuiGetprotdapgweb();
			StoreResultBean<DmpkRepositoryGuiGetprotdapgwebBean> output = lDmpkRepositoryGuiGetprotdapgweb.execute(getLocale(), loginBean, input);
	
			if (output.isInError()) {
				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.WARNING);
				}
			}
			
			try {
				if(StringUtils.isNotBlank(output.getResultBean().getListaout())) {
					DatiProtPGWebXmlBean lDatiProtPGWebXmlBean = new DatiProtPGWebXmlBean();
			        XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			        lDatiProtPGWebXmlBean = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getListaout(), DatiProtPGWebXmlBean.class);      	       
			        if(lDatiProtPGWebXmlBean != null) {
			        	if(StringUtils.isNotBlank(lDatiProtPGWebXmlBean.getOggetto())) {
			        		bean.setOggetto(lDatiProtPGWebXmlBean.getOggetto());
			        	}
			        	if (lDatiProtPGWebXmlBean.getAltreUbicazioni() != null && lDatiProtPGWebXmlBean.getAltreUbicazioni().size() > 0) {
			        		List<AltraViaProtBean> listaAltreVie = new ArrayList<AltraViaProtBean>();
				        	for (AltreUbicazioniBean lAltraVia : lDatiProtPGWebXmlBean.getAltreUbicazioni()) {
								AltraViaProtBean lAltraViaProtBean = new AltraViaProtBean();
								lAltraViaProtBean.setStato(lAltraVia.getStato());
								lAltraViaProtBean.setNomeStato(lAltraVia.getNomeStato());
								if (StringUtils.isBlank(lAltraViaProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lAltraViaProtBean.getStato())
										|| _NOME_STATO_ITALIA.equals(lAltraViaProtBean.getStato())) {
									if (StringUtils.isNotBlank(lAltraVia.getCodToponimo()) || StringUtils.isBlank(lAltraVia.getToponimoIndirizzo())) {
										lAltraViaProtBean.setFlgFuoriComune(false);
										lAltraViaProtBean.setCodToponimo(lAltraVia.getCodToponimo());
										lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
										lAltraViaProtBean.setComune(getCodIstatComuneRif());
										lAltraViaProtBean.setNomeComune(getNomeComuneRif());
									} else {
										lAltraViaProtBean.setFlgFuoriComune(true);
										lAltraViaProtBean.setTipoToponimo(lAltraVia.getTipoToponimo());
										lAltraViaProtBean.setToponimo(lAltraVia.getToponimoIndirizzo());
										lAltraViaProtBean.setComune(lAltraVia.getComune());
										lAltraViaProtBean.setNomeComune(lAltraVia.getNomeComuneCitta());
									}
									lAltraViaProtBean.setFrazione(lAltraVia.getFrazione());
									lAltraViaProtBean.setCap(lAltraVia.getCap());
								} else {
									lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
									lAltraViaProtBean.setCitta(lAltraVia.getNomeComuneCitta());
								}
								lAltraViaProtBean.setCivico(lAltraVia.getCivico());
								lAltraViaProtBean.setAppendici(lAltraVia.getAppendici());
								lAltraViaProtBean.setZona(lAltraVia.getZona());
								lAltraViaProtBean.setComplementoIndirizzo(lAltraVia.getComplementoIndirizzo());
								listaAltreVie.add(lAltraViaProtBean);
							}		
							bean.setListaAltreVie(listaAltreVie);			
						}
			        }			
				}
			} catch(Exception e) {
				logProtDS.warn(e);
			}
			
		}
		
		return bean;
	}
	
	public ProtocollazioneBean recuperaIdUd(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin("PG");
		input.setAnnoregin(bean.getAnnoProtocollo() != null ? Integer.parseInt(bean.getAnnoProtocollo()) : null);
		input.setNumregin(bean.getNroProtocollo() != null ? Integer.valueOf(bean.getNroProtocollo().intValue()) : null);
		
		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}
		
		bean.setIdUd(null);
		if (output.getResultBean() != null) {
			bean.setIdUd(output.getResultBean().getIdudio());
		}
		
		return bean;
	}
	
	public AttiRichiestiBean recuperaDatiAttoRichiesto(AttiRichiestiBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		AttiRichiestiXMLBean attoXml = new AttiRichiestiXMLBean();
		
		
		
		if (StringUtils.isNotBlank(bean.getIdFolder())){
			attoXml.setIdFolder(bean.getIdFolder());
		}else if(StringUtils.isBlank(bean.getTipoProtocollo()) || "PG".equals(bean.getTipoProtocollo())) {
			attoXml.setTipoProtocollo("PG");
			attoXml.setNumeroProtocollo(bean.getNumProtocolloGenerale());
			attoXml.setAnnoProtocollo(bean.getAnnoProtocolloGenerale());	
		} else if("PS".equals(bean.getTipoProtocollo())) {
			attoXml.setTipoProtocollo("PS");
			attoXml.setRegProtocolloDiSettore(bean.getSiglaProtocolloSettore());
			attoXml.setNumeroProtocollo(bean.getNumProtocolloSettore());
			attoXml.setSubProtocolloDiSettore(bean.getSubProtocolloSettore());
			attoXml.setAnnoProtocollo(bean.getAnnoProtocolloSettore());
		} else {
			attoXml.setTipoProtocollo("WF");
			attoXml.setNumeroProtocollo(bean.getNumPraticaWorkflow());
			attoXml.setAnnoProtocollo(bean.getAnnoPraticaWorkflow());
		}
		
		Riga lRiga = buildRiga(attoXml);
		
		String attoXmlString = transformRigaToXml(lRiga);

		DmpkCoreFindfascinarchivioBean input = new DmpkCoreFindfascinarchivioBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setDatifascicoloxmlio(attoXmlString);
		
		DmpkCoreFindfascinarchivio lDmpkCoreFindfascinarchivio = new DmpkCoreFindfascinarchivio();
		StoreResultBean<DmpkCoreFindfascinarchivioBean> output = lDmpkCoreFindfascinarchivio.execute(getLocale(), loginBean, input);
		
		 AttiRichiestiBean returnBean = new AttiRichiestiBean();

		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.WARNING);
			}
		}
		
		try {
			if(StringUtils.isNotBlank(output.getResultBean().getDatifascicoloxmlio())) {
				
				Riga RigaOutput = transformXmlToRiga(output.getResultBean().getDatifascicoloxmlio());
				returnBean = populateAttiRichiestiBean(RigaOutput);
				
			}
		} catch(Exception e) {
			logProtDS.warn(e);
		}
		
		return returnBean;
	}


	public boolean isPropostaAtto() {
		boolean isPropostaAtto = getExtraparams().get("isPropostaAtto") != null && "true".equals(getExtraparams().get("isPropostaAtto"));
		return isPropostaAtto;
	}

	public boolean isPropostaAtto2() {
		return isPropostaAtto() && ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_PROPOSTA_ATTO_2");
	}
	
	public boolean isVecchiaPropostaAtto2Milano() {
		return isPropostaAtto2() && !ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_NUOVA_PROPOSTA_ATTO_2") && !ParametriDBUtil.getParametroDBAsBoolean(getSession(), "GESTIONE_ATTI_COMPLETA");
	}
	
	public boolean isRichiestaAccessoAtti() { 
		// Verifico se sono in una richiesta di accesso atti
		return getExtraparams().get("isRichiestaAccessoAtti") != null && "true".equals(getExtraparams().get("isRichiestaAccessoAtti"));
	}
	
	public boolean isRichiestaAccessoAttiRichiedenteInterno(ProtocollazioneBean bean) { 
		// Verifico se sono in una richiesta di accesso atti con richiedente interno
		return isRichiestaAccessoAtti() && TipoRichiedente.RICH_INTERNO.getValue().equalsIgnoreCase(bean.getTipoRichiedente());
	}

	public boolean isTaskDettUdGen() {
		boolean isTaskDettUdGen = getExtraparams().get("isTaskDettUdGen") != null && "true".equals(getExtraparams().get("isTaskDettUdGen"));
		return isTaskDettUdGen;
	}
	
	private String transformRigaToXml(Riga lRigaIn) throws JAXBException {
		StringWriter lStringWriter = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lRigaIn, lStringWriter);
		return lStringWriter.toString();
	}
	
	private Riga buildRiga(AttiRichiestiXMLBean lDatiSoggettoBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Riga lRiga = new Riga();
		Field[] lFields = lDatiSoggettoBean.getClass().getDeclaredFields();
		for (Field lField : lFields) {
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna != null) {
				String value = BeanUtilsBean2.getInstance().getProperty(lDatiSoggettoBean, lField.getName());
				if (value != null && !value.equalsIgnoreCase("")) {
					Colonna lColonna = new Colonna();
					lColonna.setContent(value);
					lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
					lRiga.getColonna().add(lColonna);
				}
			}
		}
		return lRiga;
	}
	
	private Riga transformXmlToRiga(String xmlStringIn) throws JAXBException {
		Riga rigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(xmlStringIn));
		return rigaOut;
	}
	
	private AttiRichiestiBean populateAttiRichiestiBean(Riga lRiga) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		AttiRichiestiBean bean = new AttiRichiestiBean();
		
		String siglaProtocollo = null;
		String numeroProtocollo = null;
		String subProtocollo = null;
		String annoProtocollo = null;
				
		// AssegnazioneBean ufficioPrelievo = new AssegnazioneBean();		
		// SoggEsternoProtBean responsabilePrelievo = new SoggEsternoProtBean();
		
		for (int i = 0; i < lRiga.getColonna().size(); i++) {
			String nroColonna = lRiga.getColonna().get(i).getNro().toString();
			String valueColonna = lRiga.getColonna().get(i).getContent();
			if (valueColonna == null)
				valueColonna = "";
			switch (Integer.parseInt(nroColonna)) {
			case 1: // colonna 1 : Tipo protocollo
				bean.setTipoProtocollo(valueColonna);
				break;
			case 2: // colonna 2 : numero di protocollo
				numeroProtocollo = valueColonna;				
				break;
			case 3: // colonna 3 : Registro protocollo di settore
				siglaProtocollo = valueColonna;
				break;
			case 4: // colonna 4 : Anno protocollo
				annoProtocollo = valueColonna;
				break;
			case 5: // colonna 5 : Sub protocollo di settore
				subProtocollo = valueColonna;
				break;
			case 6: // colonna 6 : Stato scansione
				bean.setStatoScansione(valueColonna);
				break;
			case 7: // colonna 7 : ID folder
				bean.setIdFolder(valueColonna);
				break;
			case 8: // colonna 8 : Stato
				bean.setStato(valueColonna);
				break;
			case 9: // colonna 9 : Unità di carico
				bean.setUdc(valueColonna);
				break;
			case 10: // colonna 10 : Denominazione ufficio che ha effettuato il prelievo
				bean.setDescrizioneUfficioPrelievo(valueColonna);
				break;
			case 11: // colonna 11 : Cod. dell'ufficio che ha effettuato il prelievo
				bean.setCodRapidoUfficioPrelievo(valueColonna);
				break;
			case 12: // colonna 12 : ID_UO che ha effettuato il prelievo
				bean.setIdUoUfficioPrelievo(valueColonna);
				bean.setOrganigrammaUfficioPrelievo("UO" + valueColonna);
				break;
			case 13: // colonna 13 : Cognome responsabile prelievo
				bean.setCognomeResponsabilePrelievo(valueColonna);
				break;
			case 14: // colonna 14 : Nome responsabile prelievo
				bean.setNomeResponsabilePrelievo(valueColonna);
				break;
			case 15: // colonna 15 : Username responsabile prelievo
				bean.setUsernameResponsabilePrelievo(valueColonna);
				break;
			case 16: // colonna 16 : ID_USER responsabile prelievo
				bean.setIdUserResponsabilePrelievo(valueColonna);
				break;
			case 17: // colonna 17: Data prelievo (GG/MM/AAAA)
				SimpleDateFormat sdf = new SimpleDateFormat(FMT_STD_DATA);
				Date date = null;
				try {
					date = sdf.parse(valueColonna);
				} catch (ParseException e) {
					logProtDS.error("Errore nel parsing della data", e);
				}
				bean.setDataPrelievo(date);
				break;
			case 18: // colonna 18: Note uff. richiedente
				bean.setNoteUffRichiedente(valueColonna);
				break;
			case 19: // colonna 19: Note Cittadella
				bean.setNoteCittadella(valueColonna);
				break;
			case 20: // colonna 20: Classifica
				bean.setClassifica(valueColonna);
				break;
			case 22: // colonna 22: Competenza di urbanistica
				bean.setCompetenzaDiUrbanistica(valueColonna);
				break;
			case 23: // colonna 23: Cartaceo reperibile
				bean.setCartaceoReperibile(valueColonna);
				break;
			case 24: // colonna 24: Archivio in qui si trova
				bean.setInArchivio(valueColonna);
				break;
			case 25: // colonna 25: Richiesta visione cartaceo
				bean.setFlgRichiestaVisioneCartaceo(valueColonna != null && "1".equalsIgnoreCase(valueColonna) ? true : false);
				break;
			case 26:
				bean.setTipoFascicolo(valueColonna);
				break;
			case 27:
				bean.setAnnoProtEdiliziaPrivata(valueColonna);
				break;
			case 28:
				bean.setNumeroProtEdiliziaPrivata(valueColonna);
				break;
			case 29:
				bean.setAnnoWorkflow(valueColonna);
				break;
			case 30:
				bean.setNumeroWorkflow(valueColonna);
				break;
			case 31:
				bean.setNumeroDeposito(valueColonna);
				break;
			case 32:
				bean.setTipoComunicazione(valueColonna);
				break;
			case 33:
				bean.setNoteSportello(valueColonna);
				break;
			case 34:
				bean.setVisureCollegate(valueColonna);
				break;
			}	
		}
				
		if(StringUtils.isBlank(bean.getTipoProtocollo()) || "PG".equals(bean.getTipoProtocollo())) {
			bean.setNumProtocolloGenerale(numeroProtocollo);
			bean.setAnnoProtocolloGenerale(annoProtocollo);			
		} else if ("PS".equals(bean.getTipoProtocollo())) {			
			bean.setSiglaProtocolloSettore(siglaProtocollo);
			bean.setNumProtocolloSettore(numeroProtocollo);		
			bean.setSubProtocolloSettore(subProtocollo);	
			bean.setAnnoProtocolloSettore(annoProtocollo);			
		} else {
			bean.setNumPraticaWorkflow(numeroProtocollo);
			bean.setAnnoPraticaWorkflow(annoProtocollo);
		}

		return bean;
	}
	
	/**
	 * Splitto la denominazione in nome e cognome. Suppongo che il nome sia composto da una sola parola
	 * @param denominazione
	 * @return la denominazione splittata in cognome e nome
	 */
	private String[] dividiCognomeENome(String denominazione){
		String[] result = new String[2]; 
		String[] split = denominazione.split(" ");
		String cognome = "";
		String nome = "";
		for (int i = 0; i < split.length; i++){
			if (i != (split.length - 1)){
				cognome += cognome + " " + split[i];
			}else{
				nome = split[i];
			}
		}
		result[0] = cognome;
		result[1] = nome;
		return result;
	}
	
	/**
	 * *************************************************************************************************************************************************
	 * *********************   SEZIONE INVIO NOTIIFCA PER ASSEGNAZIONE O INVIO PER CONOSCENZA  ********************* 
	 */
	private void invioNotificheAssegnazioneInvioCC(ProtocollazioneBean bean, CreaModDocumentoInBean xmlDocumento,Boolean isUpdate) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<TipoIdBean> listaAssegnatari = getListaAssegnatari(xmlDocumento);
		List<TipoIdBean> listaInvioCC =	getListaAssegnatariPerConoscenza(xmlDocumento);
		
		/**
		 * Operazioni per la presenza di utenti assegnatari
		 */
		if(listaAssegnatari != null && !listaAssegnatari.isEmpty()){
			manageAssegnazioni(bean.getIdUd(), isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaAssegnatari);
		}
		/**
		 * Operazioni per la presenza di utenti per invio in conoscenza
		 */
		if(listaInvioCC != null && !listaInvioCC.isEmpty()){
			manageInvioCC(bean.getIdUd(), isUpdate, lAurigaLoginBean, lXmlUtilitySerializer, listaInvioCC);
		}
	}
	
	private void manageAssegnazioni(BigDecimal idUd, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
		List<TipoIdBean> listaAssegnatari) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {

		/**
		 * Assegnazione per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
		 */
		
		List<TipoIdBean> listaAssNew = new ArrayList<TipoIdBean>();
		if (listaAssegnatari != null && !listaAssegnatari.isEmpty()) {
			for(int i = 0; i < listaAssegnatari.size(); i++) {
				if (attivaNotEmailAssegnUD(listaAssegnatari,i)) {
					listaAssNew.add(listaAssegnatari.get(i));
				}
			}	
		}
		
		if(listaAssNew != null && !listaAssNew.isEmpty()) {
			sendNotificaAssegnazioneInvioCC(idUd,lAurigaLoginBean,lXmlUtilitySerializer,listaAssNew,"ASSEGNAZIONE",isUpdate);
		} else {
			logProtDS.warn("Non è stata inviata alcuna notifica e-mail assegnazione" + "lista destinatari listaAssegnatari: " + listaAssegnatari.size()
			+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
			//addMessage("Non è stata inviata alcuna notifica e-mail dell'assegnazione", "", MessageType.WARNING);
		}
	}
	
	private void manageInvioCC(BigDecimal idUd, Boolean isUpdate, AurigaLoginBean lAurigaLoginBean, XmlUtilitySerializer lXmlUtilitySerializer,
			List<TipoIdBean> listaInvioCC) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		/**
		 * Invio per conoscenza di un DOCUMENTO, di conseguenza controllo il parametro nella dmt_def_config_param ATTIVA_NOT_EMAIL_ASSEGN_UD
		 */
		List<TipoIdBean> listaInvioCCNew = new ArrayList<TipoIdBean>();
		if (listaInvioCC != null && !listaInvioCC.isEmpty()) {
			for(int i = 0; i < listaInvioCC.size(); i++) {
				if (attivaNotEmailInvioCCUD(listaInvioCC,i)) {
					listaInvioCCNew.add(listaInvioCC.get(i));
				} 
			}	
		}
		
		if(listaInvioCCNew != null && !listaInvioCCNew.isEmpty()) {
			sendNotificaAssegnazioneInvioCC(idUd,lAurigaLoginBean,lXmlUtilitySerializer,listaInvioCCNew,"",isUpdate);
		} else {
			logProtDS.warn("Non è stata inviata alcuna notifica e-mail relativa all'invio per conoscenza" + "lista destinatari listaInvioCC: " + listaInvioCC.size()
			+ ", verificare parametro ATTIVA_NOT_EMAIL_ASSEGN_UD");
			//addMessage("Non è stata inviata alcuna notifica e-mail relativa all'invio per conoscenza", "", MessageType.WARNING);
		}
	}
	
	private void sendNotificaAssegnazioneInvioCC(BigDecimal idUd, AurigaLoginBean lAurigaLoginBean,XmlUtilitySerializer lXmlUtilitySerializer, 
			List<TipoIdBean> listaInvioCC,String tipoOperazione,Boolean isUpdate) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		String token = lAurigaLoginBean.getToken();
		String idUserLavoro = lAurigaLoginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailPreparaemailnotassinvioccBean lInputBean = new DmpkIntMgoEmailPreparaemailnotassinvioccBean();
		lInputBean.setCodidconnectiontokenin(token);
		lInputBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		if(isUpdate){
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		} else {
			if("ASSEGNAZIONE".equals(tipoOperazione)){
				lInputBean.setAssinvioccin("assegnazione_competenza");
			}else{
				lInputBean.setAssinvioccin("invio_conoscenza");
			}
		}

		List<TipoIdBean> listaUdFolder = new ArrayList<TipoIdBean>();
		TipoIdBean udFolder = new TipoIdBean();
		udFolder.setTipo("U");
		udFolder.setId(idUd != null ? String.valueOf(idUd.longValue()) : null);
		listaUdFolder.add(udFolder);
		lInputBean.setUdfolderxmlin(lXmlUtilitySerializer.bindXmlList(listaUdFolder));
		lInputBean.setDestassinvioccxmlin(lXmlUtilitySerializer.bindXmlList(listaInvioCC));
		lInputBean.setCodmotivoinvioin(null);
		lInputBean.setMotivoinvioin(null);
		lInputBean.setMessaggioinvioin(null);
		lInputBean.setLivelloprioritain(null);
		lInputBean.setTsdecorrassinvioccin(null);

		DmpkIntMgoEmailPreparaemailnotassinviocc preparaemailnotassinviocc = new DmpkIntMgoEmailPreparaemailnotassinviocc();
		StoreResultBean<DmpkIntMgoEmailPreparaemailnotassinvioccBean> lStoreResultBean = preparaemailnotassinviocc.execute(getLocale(), lAurigaLoginBean,
				lInputBean);

		if (lStoreResultBean.isInError()) {
				String message = "";
			if("ASSEGNAZIONE".equals(tipoOperazione)) {
				message = "Fallito invio notifica e-mail dell'assegnazione per gli utenti selezionati ";
			} else {
				message = "Fallito invio notifica e-mail dell'invio in conoscenza per gli utenti selezionati ";
			}
			if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
				if("ASSEGNAZIONE".equals(tipoOperazione)) {
					logProtDS.error("Fallito invio notifica e-mail dell'assegnazione per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
				} else {
					logProtDS.error("Fallito invio notifica e-mail dell'invio in conoscenza per il seguente motivo: " + lStoreResultBean.getDefaultMessage());
				}
				message += " per il seguente motivo: " + lStoreResultBean.getDefaultMessage();
			}
			addMessage(message, "", MessageType.WARNING);
		} else {
			if (lStoreResultBean.getResultBean().getFlgemailtosendout() != null && lStoreResultBean.getResultBean().getFlgemailtosendout().intValue() == 1) {
				SenderBean sender = new SenderBean();
				sender.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
				sender.setAccount(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				sender.setAddressFrom(lStoreResultBean.getResultBean().getIndirizzomittemailout());
				List<String> addressTo = new ArrayList<String>();
				StringReader sr = new StringReader(lStoreResultBean.getResultBean().getIndirizzidestemailout());
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				if (lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) {
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						addressTo.add(v.get(0));
					}
				}
				sender.setAddressTo(addressTo);
				sender.setSubject(lStoreResultBean.getResultBean().getOggemailout());
				sender.setBody(lStoreResultBean.getResultBean().getBodyemailout());
				sender.setIsHtml(true);
				sender.setIsPec(false);
				try {
					EmailSentReferenceBean lEmailSentReferenceBean = AurigaMailService.getMailSenderService().send(getLocale(), sender);
					String notSentMails = null;
					for (SenderBean lSenderBean : lEmailSentReferenceBean.getSentMails()) {
						if (!lSenderBean.getIsSent()) {
							if (notSentMails == null) {
								notSentMails = lSenderBean.getAddressTo().get(0);
							} else {
								notSentMails += ", " + lSenderBean.getAddressTo().get(0);
							}
						}
					}
					if (notSentMails != null) {
						if("ASSEGNAZIONE".equals(tipoOperazione)){
							logProtDS.warn("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'assegnazione per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}else{
							logProtDS.warn("Warning, Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails);
							addMessage("Fallito invio notifica e-mail dell'invio per conoscenza per i seguenti indirizzi: " + notSentMails, "", MessageType.WARNING);
						}
					}
				} catch (Exception e) {
					if("ASSEGNAZIONE".equals(tipoOperazione)){
						logProtDS.error("Fallito invio notifica e-mail dell'assegnazione:" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'assegnazione: " + e.getMessage(), "", MessageType.WARNING);
					}else{
						logProtDS.error("Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza" + e.getMessage(), e);
						addMessage("Fallito invio notifica e-mail dell'invio per conoscenza: " + e.getMessage(), "", MessageType.WARNING);
					}
				}
			} else {
				if("ASSEGNAZIONE".equals(tipoOperazione)){
					logProtDS.warn("Non è stata inviata alcuna notifica e-mail dell'assegnazione");
				}else{
					logProtDS.warn("Warning, Non è stata inviata alcuna notifica e-mail dell'invio per conoscenza,getFlgemailtosendout: "
							+ lStoreResultBean.getResultBean().getFlgemailtosendout());
				}
			}
		}
	}
	
	private List<TipoIdBean> getListaAssegnatari(CreaModDocumentoInBean xmlDocumento) throws Exception {
		List<TipoIdBean> listaDestAssCC = new ArrayList<TipoIdBean>();
		if (xmlDocumento.getAssegnatari() != null && !xmlDocumento.getAssegnatari().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlDocumento.getAssegnatari()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaDestAssCC.add(tipoIdBean);
			}
		}
		return listaDestAssCC;
	}
	
	private List<TipoIdBean> getListaAssegnatariPerConoscenza(CreaModDocumentoInBean xmlDocumento) {
		List<TipoIdBean> listaInvioCC = new ArrayList<TipoIdBean>();
		if (xmlDocumento.getInvioDestCC() != null && !xmlDocumento.getInvioDestCC().isEmpty()) {
			for (AssegnatariBean assegnatarioBean : xmlDocumento.getInvioDestCC()) {
				
				TipoIdBean tipoIdBean = new TipoIdBean();
				tipoIdBean.setTipo(getTipoAssegnatarioInvioCC(assegnatarioBean));
				tipoIdBean.setId(assegnatarioBean.getIdSettato());
				tipoIdBean.setFlgMandaNotificaMail(assegnatarioBean.getFlgMandaNotificaMail() != null && assegnatarioBean.getFlgMandaNotificaMail() == Flag.SETTED ?
						true : false );
				listaInvioCC.add(tipoIdBean);
			}
		}
		return listaInvioCC;
	}
	
	private String getTipoAssegnatarioInvioCC(AssegnatariBean lAssegnazioneBean) {
		TipoAssegnatario[] lTipoAssegnatarios = TipoAssegnatario.values();
		for (TipoAssegnatario lTipoAssegnatario : lTipoAssegnatarios) {
			if (lTipoAssegnatario.toString().equals(lAssegnazioneBean.getTipo().toString()))
				return lTipoAssegnatario.toString();
		}
		return null;
	}
	
	public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
		// Se non ho attivato il protocollo wizard inserisco i documenti collegati in append
		return !isAttivoProtocolloWizard(beanDettaglio);
	}
	
	public boolean isAttivoProtocolloWizard(ProtocollazioneBean beanDettaglio) {
		if(beanDettaglio == null || beanDettaglio.getIdUd() == null || StringUtils.isNotBlank(beanDettaglio.getSupportoOriginale())) {
			return ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_PROTOCOLLO_WIZARD");
		}
		return false;
	}
	
	public boolean isAttivoEsibente(ProtocollazioneBean beanDettaglio) {	
		// se è un protocollo in entrata
		if (beanDettaglio.getFlgTipoProv() != null && beanDettaglio.getFlgTipoProv().equalsIgnoreCase("E")) {
			// se è attiva la modalità wizard e il canale selezionato è lo sportello (consegna a mano)
			if (isAttivoProtocolloWizard(beanDettaglio) && beanDettaglio.getMezzoTrasmissione() != null && ("CM".equals(beanDettaglio.getMezzoTrasmissione()) || "PREGR".equals(beanDettaglio.getMezzoTrasmissione()))) {
				return true;	
			// se sono in una protocollazione richiesta accesso atti da mail
			} else if (isRichiestaAccessoAtti() && StringUtils.isNotBlank(beanDettaglio.getIdEmailArrivo())) {
				return true;
			}
			// se l'esibente va mostrato anche in modalità standard
			else if(isAttivoEsibenteSenzaWizard()) {
				return true;
			} 
		}
		return false;
	}
	
	public boolean isAttivoInteressati(ProtocollazioneBean beanDettaglio) {
		// se è attiva la modalità wizard
		if (isAttivoProtocolloWizard(beanDettaglio)) {
			return true;			
		}
		// se gli interessati vanno mostrati anche in modalità standard
		else if(isAttivoInteressatiSenzaWizard()) {
			return true;
		} 
		return false;
	}
	
	public boolean isAttivoEsibenteSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_ESIBENTE_SENZA_WIZARD");
	}
	
	public boolean isAttivoInteressatiSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_INTERESSATI_SENZA_WIZARD");
	}
	
	public boolean isAttivoAltreVieSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_VIE_SENZA_WIZARD");
	}
	
	public boolean isClienteComuneMilano() {
		return ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(getSession(), "CLIENTE").equalsIgnoreCase("CMMI");
	}

	public String getCodIstatComuneRif() {
		return ParametriDBUtil.getParametroDB(getSession(), "ISTAT_COMUNE_RIF");
	}

	public String getNomeComuneRif() {
		return ParametriDBUtil.getParametroDB(getSession(), "NOME_COMUNE_RIF");
	}
	
	public StampaFileBean creaStampa(ProtocollazioneBean bean) throws Exception {
		String nomeModello = getExtraparams().get("nomeModello");
		String tipoModello = nomeModello.equals("RICEVUTA_REGISTRAZIONE_UD") ? TipoModelloDoc.DOCX_CON_PLACEHOLDER.getValue() : "DOCX_FORM";
		String nomeFileStampa = getExtraparams().get("nomeFileStampa");
		return creaStampaDaModello(bean, null, nomeModello, tipoModello, nomeFileStampa);
	}
	
	//ATTENZIONE: se tipoModello è ODT_FREEMARKERS è necessario passare anche idModello oltre a nomeModello e tipoModello
	private StampaFileBean creaStampaDaModello(ProtocollazioneBean bean, String idModello, String nomeModello, String tipoModello, String nomeFileStampa) throws Exception {
		
		try {		
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			String uriModello = lExtractvermodelloOutput.getResultBean().getUriverout();
			
			DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
			DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
			lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lGetdatixgendamodelloInput.setIdobjrifin(String.valueOf(bean.getIdUd().longValue()));
			lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
			lGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
			
			StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
					lGetdatixgendamodelloInput);
			
			if(lGetdatixgendamodelloOutput.isInError()) {
				throw new StoreException(lGetdatixgendamodelloOutput);
			}
			
			String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();					
			
			File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModello, tipoModello, templateValues, getSession()); //TODO l'idModello prima veniva passato erroneamente nel parametro dell'idProcess, andrebbe gestito un nuovo metodo fillTemplateAndConvertToPdf che accetta in input anche idModello, ora non viene gestito
			
			StampaFileBean output = new StampaFileBean();
			output.setNomeFile(nomeFileStampa);
			output.setUri(StorageImplementation.getStorage().store(fileModelloPdf));
			
			MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
			infoFile.setMimetype("application/pdf");
			infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
			infoFile.setDaScansione(false);
			infoFile.setFirmato(false);
			infoFile.setFirmaValida(false);
			infoFile.setBytes(fileModelloPdf.length());
			infoFile.setCorrectFileName(output.getNomeFile());
			File realFile = StorageImplementation.getStorage().getRealFile(output.getUri());
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), output.getNomeFile()));
			output.setInfoFile(infoFile);
			
			return output;
			
		} catch(Exception e) {
			logProtDS.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la generazione della stampa");
		}
	}
	
	protected void apposizioneTimbroPostReg(ProtocollazioneBean bean) throws Exception {
		
		/*
		 * APPOSIZIONE TIMBRO SUI FILE DEL DOCUMENTO
		 */
		
		boolean fileDaTimbrare = false;
		if(bean.getFlgTimbraFilePostReg() != null && bean.getFlgTimbraFilePostReg()) {
			fileDaTimbrare = true;
		} else if(bean.getFilePrimarioOmissis() != null && bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg() != null && bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg()) {
			fileDaTimbrare = true;
		} else if (bean.getListaAllegati() != null && bean.getListaAllegati().size()>0) {
			for (int i = 0; i < bean.getListaAllegati().size(); i++){
				AllegatoProtocolloBean lAllegatoProtocolloBean = bean.getListaAllegati().get(i);
				if(lAllegatoProtocolloBean.getFlgTimbraFilePostReg() != null
						&& lAllegatoProtocolloBean.getFlgTimbraFilePostReg()) {
					fileDaTimbrare = true;
					break;
				}
				if(lAllegatoProtocolloBean.getFlgTimbraFilePostRegOmissis() != null
						&& lAllegatoProtocolloBean.getFlgTimbraFilePostRegOmissis()) {
					fileDaTimbrare = true;
					break;
				}
			}
		}
			
		if(fileDaTimbrare) {
			
			ProtocollazioneBean protBean = get(bean);				
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
			InfoFileUtility lFileUtility = new InfoFileUtility();		
			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			String flgSostVersFileTimbratoPostReg = ParametriDBUtil.getParametroDB(getSession(), "SOST_VERS_FILE_TIMBRATO_POST_REG");
			
			if(protBean != null) {			
				if(StringUtils.isNotBlank(protBean.getUriFilePrimario())){				
					try {				
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setIdFile("primario");
						lFileDaFirmareBean.setNomeFile(protBean.getNomeFilePrimario());
						lFileDaFirmareBean.setUri(protBean.getUriFilePrimario());
						lFileDaFirmareBean.setUriVerPreFirma(protBean.getUriFileVerPreFirma());
						lFileDaFirmareBean.setInfoFile(protBean.getInfoFile());
						
						// il flag relativo al check "apponi timbro" del file primario lo leggo dal bean originale altrimenti mi perdo il valore settato dall'utente
						if(bean.getFlgTimbraFilePostReg() != null && bean.getFlgTimbraFilePostReg()){
							
							FileDaFirmareBean lFilePrimarioBean = timbraFile(lFileDaFirmareBean, bean.getOpzioniTimbro(), String.valueOf(protBean.getIdUd()));
							
							RebuildedFile lRebuildedFile = new RebuildedFile();
							lRebuildedFile.setIdDocumento(protBean.getIdDocPrimario());
							lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(lFilePrimarioBean.getUri()));
								
							/**
							 * Doppia chiamata superflua, viene già effettuata la stessa dentro il metodo timbraFile(lFileDaFirmareBean, bean.getOpzioniTimbro(), String.valueOf(protBean.getIdUd()));
							 * e di conseguenza le informazioni per il mimetype, idFormato, impronta sono già presenti dentro FileDaFirmareBean.
							 */
//							MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
//							lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
							
							FileInfoBean lFileInfoBean = new FileInfoBean();
							lFileInfoBean.setTipo(TipoFile.PRIMARIO);
							GenericFile lGenericFile = new GenericFile();
							setProprietaGenericFile(lGenericFile, lFilePrimarioBean.getInfoFile());
							lGenericFile.setDisplayFilename(lFilePrimarioBean.getNomeFile());
							lGenericFile.setMimetype(lFilePrimarioBean.getInfoFile().getMimetype());
							lGenericFile.setIdFormato(lFilePrimarioBean.getInfoFile().getIdFormato());
							lGenericFile.setImpronta(lFilePrimarioBean.getInfoFile().getImpronta());
							lGenericFile.setImprontaFilePreFirma(lFilePrimarioBean.getInfoFile().getImprontaFilePreFirma());
							// lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
							// lGenericFile.setIdFormato(lMimeTypeFirmaBean.getIdFormato());
							// lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
							lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
							lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
							// Per ciascun file timbrato in automatico va settato l'attributo FLG_TIMBRO_REG_Ver = 1 
							lGenericFile.setFlgTimbroReg(Flag.SETTED);
							lFileInfoBean.setAllegatoRiferimento(lGenericFile);
		
							lRebuildedFile.setInfo(lFileInfoBean);
		
							if(StringUtils.isBlank(flgSostVersFileTimbratoPostReg) || "S".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
								lRebuildedFile.setUpdateVersion(true);
								lRebuildedFile.setAnnullaLastVer(false);
							} else if ("V".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
								lRebuildedFile.setUpdateVersion(false);
								lRebuildedFile.setAnnullaLastVer(true);								
							}
							
							VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
							BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
		
							VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
		
							if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
								logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
								throw new StoreException(lVersionaDocumentoOutput);
							}
						}
					} catch(Exception e) {
						logProtDS.error(e.getMessage(), e);
						addMessage("File primario non timbrato: il tipo di file non ne consente la timbratura", "", MessageType.WARNING);
					}
				}
				// Vers. con omissis
				if ((protBean.getFlgDatiSensibili() != null && protBean.getFlgDatiSensibili()) && protBean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(protBean.getFilePrimarioOmissis().getUriFile()) && StringUtils.isNotBlank(protBean.getFilePrimarioOmissis().getNomeFile())) {
					try {				
						FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
						lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis");
						lFileDaFirmareBeanOmissis.setNomeFile(protBean.getFilePrimarioOmissis().getNomeFile());
						lFileDaFirmareBeanOmissis.setUri(protBean.getFilePrimarioOmissis().getUriFile());
						lFileDaFirmareBeanOmissis.setUriVerPreFirma(protBean.getFilePrimarioOmissis().getUriFileVerPreFirma());
						lFileDaFirmareBeanOmissis.setInfoFile(protBean.getFilePrimarioOmissis().getInfoFile());
						
						// il flag relativo al check "apponi timbro" del file primario omissis lo leggo dal bean originale altrimenti mi perdo il valore settato dall'utente
						if(bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg() != null && bean.getFilePrimarioOmissis().getFlgTimbraFilePostReg()){
							
							FileDaFirmareBean lFilePrimarioBeanOmissis = timbraFileOmissis(lFileDaFirmareBeanOmissis, bean.getFilePrimarioOmissis().getOpzioniTimbro(), String.valueOf(protBean.getIdUd()));
							
							RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
							lRebuildedFileOmissis.setIdDocumento(protBean.getFilePrimarioOmissis().getIdDoc() != null ? new BigDecimal(protBean.getFilePrimarioOmissis().getIdDoc()) : null);
							lRebuildedFileOmissis.setFile(StorageImplementation.getStorage().extractFile(lFilePrimarioBeanOmissis.getUri()));
		
							MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
							lMimeTypeFirmaBeanOmissis = lFileUtility.getInfoFromFile(lRebuildedFileOmissis.getFile().toURI().toString(), lRebuildedFileOmissis.getFile().getName(), false, null);
		
							FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
							lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
							GenericFile lGenericFileOmissis = new GenericFile();
							setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
							lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
							lGenericFileOmissis.setIdFormato(lMimeTypeFirmaBeanOmissis.getIdFormato());
							lGenericFileOmissis.setDisplayFilename(lFilePrimarioBeanOmissis.getNomeFile());
							lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
							lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
							lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
							// Per ciascun file timbrato in automatico va settato l'attributo FLG_TIMBRO_REG_Ver = 1 
							lGenericFileOmissis.setFlgTimbroReg(Flag.SETTED);
							lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
		
							lRebuildedFileOmissis.setInfo(lFileInfoBeanOmissis);
		
							if(StringUtils.isBlank(flgSostVersFileTimbratoPostReg) || "S".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
								lRebuildedFileOmissis.setUpdateVersion(true);
								lRebuildedFileOmissis.setAnnullaLastVer(false);
							} else if ("V".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
								lRebuildedFileOmissis.setUpdateVersion(false);
								lRebuildedFileOmissis.setAnnullaLastVer(true);								
							}
							
							VersionaDocumentoInBean lVersionaDocumentoInBeanOmissis = new VersionaDocumentoInBean();
							BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBeanOmissis, lRebuildedFileOmissis);
		
							VersionaDocumentoOutBean lVersionaDocumentoOutputOmissis = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBeanOmissis);
		
							if (lVersionaDocumentoOutputOmissis.getDefaultMessage() != null) {
								logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutputOmissis.getDefaultMessage());
								throw new StoreException(lVersionaDocumentoOutputOmissis);
							}
						}
					} catch(Exception e) {
						logProtDS.error(e.getMessage(), e);
						addMessage("File primario (vers. con omissis) non timbrato: il tipo di file non ne consente la timbratura", "", MessageType.WARNING);
					}
				}			
				if (protBean.getListaAllegati()!=null && protBean.getListaAllegati().size()>0){
					for (int i = 0; i < protBean.getListaAllegati().size(); i++){
						AllegatoProtocolloBean lAllegatoProtocolloBean = protBean.getListaAllegati().get(i);
						
						// i flag relativi ai check "apponi timbro" degli allegati li leggo dal bean originale, altrimenti mi perdo i valori settati dall'utente
						try {
							FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
							lFileDaFirmareBean.setIdFile("allegato" + lAllegatoProtocolloBean.getUriFileAllegato());
							lFileDaFirmareBean.setInfoFile(lAllegatoProtocolloBean.getInfoFile());
							lFileDaFirmareBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
							lFileDaFirmareBean.setUri(lAllegatoProtocolloBean.getUriFileAllegato());
							lFileDaFirmareBean.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirma());
							lFileDaFirmareBean.setNroProgAllegato(new Integer(lAllegatoProtocolloBean.getNumeroProgrAllegato()));
								
							if(bean.getListaAllegati().get(i).getFlgTimbraFilePostReg() != null && bean.getListaAllegati().get(i).getFlgTimbraFilePostReg()){
								
								FileDaFirmareBean lFileAllegatoBean = timbraFileAllegato(lFileDaFirmareBean,bean, String.valueOf(protBean.getIdUd()), i);
								
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setIdDocumento(lAllegatoProtocolloBean.getIdDocAllegato());
								lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(lFileAllegatoBean.getUri()));
			
								MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
								lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			
								FileInfoBean lFileInfoBean = new FileInfoBean();
								lFileInfoBean.setTipo(TipoFile.ALLEGATO);
								GenericFile lGenericFile = new GenericFile();
								setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
								lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
								lGenericFile.setIdFormato(lMimeTypeFirmaBean.getIdFormato());
								lGenericFile.setDisplayFilename(lFileAllegatoBean.getNomeFile());
								lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
								lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
								lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
								// Per ciascun file timbrato in automatico va settato l'attributo FLG_TIMBRO_REG_Ver = 1 
								lGenericFile.setFlgTimbroReg(Flag.SETTED);
								lFileInfoBean.setPosizione(i);
								lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			
								lRebuildedFile.setInfo(lFileInfoBean);
								
								if(StringUtils.isBlank(flgSostVersFileTimbratoPostReg) || "S".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
									lRebuildedFile.setUpdateVersion(true);
									lRebuildedFile.setAnnullaLastVer(false);
								} else if ("V".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
									lRebuildedFile.setUpdateVersion(false);
									lRebuildedFile.setAnnullaLastVer(true);								
								}
								
								VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
								BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
			
								VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBean);
			
								if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
									logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutput.getDefaultMessage());
									throw new StoreException(lVersionaDocumentoOutput);
								}
							}
						} catch(Exception e) {
							logProtDS.error(e.getMessage(), e);
							addMessage("Allegato N. " + lAllegatoProtocolloBean.getNumeroProgrAllegato() + " non timbrato: il tipo di file non ne consente la timbratura", "", MessageType.WARNING);
						}
						
						// Vers. con omissis
						try {							
							if ((lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
								
								FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
								lFileDaFirmareBeanOmissis.setIdFile("allegatoOmissis" + lAllegatoProtocolloBean.getUriFileOmissis());
								lFileDaFirmareBeanOmissis.setInfoFile(lAllegatoProtocolloBean.getInfoFileOmissis());
								lFileDaFirmareBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
								lFileDaFirmareBeanOmissis.setUri(lAllegatoProtocolloBean.getUriFileOmissis());
								lFileDaFirmareBeanOmissis.setUriVerPreFirma(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis());
								
								if(bean.getListaAllegati().get(i).getFlgTimbraFilePostRegOmissis() != null && bean.getListaAllegati().get(i).getFlgTimbraFilePostRegOmissis()){
									
									FileDaFirmareBean lFileAllegatoBeanOmissis = timbraFileAllegatoOmissis(lFileDaFirmareBeanOmissis, bean, String.valueOf(protBean.getIdUd()), i);
									
									RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
									lRebuildedFileOmissis.setIdDocumento(lAllegatoProtocolloBean.getIdDocOmissis());
									lRebuildedFileOmissis.setFile(StorageImplementation.getStorage().extractFile(lFileAllegatoBeanOmissis.getUri()));
				
									MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
									lMimeTypeFirmaBeanOmissis = lFileUtility.getInfoFromFile(lRebuildedFileOmissis.getFile().toURI().toString(), lRebuildedFileOmissis.getFile().getName(), false, null);
				
									FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
									lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
									GenericFile lGenericFileOmissis = new GenericFile();
									setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
									lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
									lGenericFileOmissis.setIdFormato(lMimeTypeFirmaBeanOmissis.getIdFormato());
									lGenericFileOmissis.setDisplayFilename(lFileAllegatoBeanOmissis.getNomeFile());
									lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
									lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
									lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
									// Per ciascun file timbrato in automatico va settato l'attributo FLG_TIMBRO_REG_Ver = 1 
									lGenericFileOmissis.setFlgTimbroReg(Flag.SETTED);
									lFileInfoBeanOmissis.setPosizione(i);
									lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				
									lRebuildedFileOmissis.setInfo(lFileInfoBeanOmissis);
									
									if(StringUtils.isBlank(flgSostVersFileTimbratoPostReg) || "S".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
										lRebuildedFileOmissis.setUpdateVersion(true);
										lRebuildedFileOmissis.setAnnullaLastVer(false);
									} else if ("V".equalsIgnoreCase(flgSostVersFileTimbratoPostReg)) {
										lRebuildedFileOmissis.setUpdateVersion(false);
										lRebuildedFileOmissis.setAnnullaLastVer(true);								
									}
									
									VersionaDocumentoInBean lVersionaDocumentoInBeanOmissis = new VersionaDocumentoInBean();
									BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBeanOmissis, lRebuildedFileOmissis);
				
									VersionaDocumentoOutBean lVersionaDocumentoOutputOmissis = lGestioneDocumenti.versionadocumento(getLocale(), lAurigaLoginBean, lVersionaDocumentoInBeanOmissis);
				
									if (lVersionaDocumentoOutputOmissis.getDefaultMessage() != null) {
										logProtDS.error("VersionaDocumento: " + lVersionaDocumentoOutputOmissis.getDefaultMessage());
										throw new StoreException(lVersionaDocumentoOutputOmissis);
									}
								}
							}
						} catch(Exception e) {
							logProtDS.error(e.getMessage(), e);
							addMessage("Allegato N. " + lAllegatoProtocolloBean.getNumeroProgrAllegato() + " (vers. con omissis) non timbrato: il tipo di file non ne consente la timbratura", "", MessageType.WARNING);
						}
						
					}
				}
			}
		}
	}

	/**
	 * APPOSIZIONE TIMBRO PER FILE PRIMARIO
	 */
	private FileDaFirmareBean timbraFile(FileDaFirmareBean lFileDaTimbrareBean, OpzioniTimbroDocBean lOpzioniTimbroDocBean, String idUd) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = buildOpzioniApponiTimbro(idUd, lFileDaTimbrareBean, lOpzioniTimbroDocBean);
		
		// Timbro il file
		TimbraResultBean lTimbraResultBean = new TimbraUtility().timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}	
		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lFileDaTimbrareBean.setNomeFile(FilenameUtils.getBaseName(lFileDaTimbrareBean.getNomeFile()) + ".pdf");
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);	
		
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean timbraFileOmissis(FileDaFirmareBean lFileDaTimbrareBeanOmissis, OpzioniTimbroDocBean lOpzioniTimbroDocBean, String idUd) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBeanOmissis = buildOpzioniApponiTimbro(idUd, lFileDaTimbrareBeanOmissis, lOpzioniTimbroDocBean);
		
		// Timbro il file
		TimbraResultBean lTimbraResultBeanOmissis = new TimbraUtility().timbra(lOpzioniTimbroBeanOmissis, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBeanOmissis.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBeanOmissis.setUri(lTimbraResultBeanOmissis.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBeanOmissis.getError())) {
				errorMessage += ": " + lTimbraResultBeanOmissis.getError();
			}
			throw new Exception(errorMessage);
		}	
		
		File lFileTimbratoOmissis = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBeanOmissis.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
		lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileTimbratoOmissis.toURI().toString(), lFileDaTimbrareBeanOmissis.getNomeFile(), false, null);
		lFileDaTimbrareBeanOmissis.setNomeFile(FilenameUtils.getBaseName(lFileDaTimbrareBeanOmissis.getNomeFile()) + ".pdf");
		lMimeTypeFirmaBeanOmissis.setFirmato(false);
		lMimeTypeFirmaBeanOmissis.setFirmaValida(false);
		lMimeTypeFirmaBeanOmissis.setConvertibile(false);
		lMimeTypeFirmaBeanOmissis.setDaScansione(false);
		lFileDaTimbrareBeanOmissis.setInfoFile(lMimeTypeFirmaBeanOmissis);	
		
		return lFileDaTimbrareBeanOmissis;
	}
	
	public OpzioniTimbroBean buildOpzioniApponiTimbro(String idUd, FileDaFirmareBean lFileDaTimbrareBean, OpzioniTimbroDocBean lOpzioniTimbroDocBean) throws Exception, Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype(lFileDaTimbrareBean.getInfoFile() != null ? lFileDaTimbrareBean.getInfoFile().getMimetype() : null);
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		// Sono in fase di timbratura postreg, non ho iddoc e quindi passo -999 per dire alla store che sono in questa situazione
		lOpzioniTimbroBean.setIdDoc("-999");
		lOpzioniTimbroBean.setRemote(true);
		lOpzioniTimbroBean = new TimbraUtility().loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());
		
		/**
		 * Se l'utente vuole saltare la scelta delle preference di apposizione timbro allora:
		 * Scelta delle preference di apposizione timbro, se sono presenti le preference utilizzo queste altrimenti
		 * vengono recuperate detro il config.xml dal bean OpzioniTimbroAutoDocRegBean.
		 * Altrimenti utilizzo le preference utilizzate per ogni file.
		 */		
		
		OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = null;
		try{
			 lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			logProtDS.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}
		
		String rotazioneTimbroPref =  getExtraparams().get("rotazioneTimbroPref");
		String posizioneTimbroPref =  getExtraparams().get("posizioneTimbroPref");
		String tipoPaginaTimbroPref = getExtraparams().get("tipoPaginaTimbroPref");
			
		String rotazioneTimbroBean = lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale";
		String posizioneTimbroBean =  lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn";
		String tipoPaginaTimbroBean = lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null ? 
				lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value() : "TUTTE";
		
		if(StringUtils.isNotBlank(getExtraparams().get("skipSceltaApponiTimbro")) && "true".equals(getExtraparams().get("skipSceltaApponiTimbro"))){
			
			/**
			 * In ordine viene data la seguente priorità di opzioni di generazione timbro
			 * 1) Preference di generazione timbro globale
			 * 2) Preference presenti nel config.xml
			 */
			
			lOpzioniTimbroBean.setRotazioneTimbro(StringUtils.isNotBlank(rotazioneTimbroPref) ? rotazioneTimbroPref : rotazioneTimbroBean);
			lOpzioniTimbroBean.setPosizioneTimbro(StringUtils.isNotBlank(posizioneTimbroPref) ? posizioneTimbroPref : posizioneTimbroBean);
			lOpzioniTimbroBean.setTipoPagina(StringUtils.isNotBlank(tipoPaginaTimbroPref) ? tipoPaginaTimbroPref : tipoPaginaTimbroBean);
			lOpzioniTimbroBean.setTimbroSingolo(false);
			lOpzioniTimbroBean.setMoreLines(true);
			
		} else {
			/**
			 * In ordine viene data la seguente priorità di opzioni di generazione timbro
			 * 1) Preference di generazione timbro presenti nella select di scelta per ogni file
			 * 2) Preference di generazione timbro globale
			 * 3) Preference presenti nel config.xml
			 */
		
			if( lOpzioniTimbroDocBean != null ){
				
				String rotazioneOpzTimbro =  lOpzioniTimbroDocBean.getRotazioneTimbro();
				String posizioneOpzTimbro  =  lOpzioniTimbroDocBean.getPosizioneTimbro();
				String tipoPaginaOpzTimbro  = lOpzioniTimbroDocBean.getTipoPaginaTimbro();
				
				if(StringUtils.isNotBlank(rotazioneOpzTimbro)){
					lOpzioniTimbroBean.setRotazioneTimbro(rotazioneOpzTimbro);
				} else {
					lOpzioniTimbroBean.setRotazioneTimbro(StringUtils.isNotBlank(rotazioneTimbroPref) ? rotazioneTimbroPref : rotazioneTimbroBean);
				}
				if(StringUtils.isNotBlank(posizioneOpzTimbro)){
					lOpzioniTimbroBean.setPosizioneTimbro(posizioneOpzTimbro);
				} else {
					lOpzioniTimbroBean.setPosizioneTimbro(StringUtils.isNotBlank(posizioneTimbroPref) ? posizioneTimbroPref : posizioneTimbroBean);
				}
				if(StringUtils.isNotBlank(tipoPaginaOpzTimbro)){
					if("intervallo".equals(tipoPaginaOpzTimbro)){
						lOpzioniTimbroBean.setTipoPagina(null);
						lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzioniTimbroDocBean.getPaginaDa()));
						lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzioniTimbroDocBean.getPaginaA()));
					}else{
						lOpzioniTimbroBean.setTipoPagina(tipoPaginaOpzTimbro);
					}
				}else{
					lOpzioniTimbroBean.setTipoPagina(StringUtils.isNotBlank(tipoPaginaTimbroPref) ? tipoPaginaTimbroPref : tipoPaginaTimbroBean);
				}
				
				lOpzioniTimbroBean.setTimbroSingolo(false);
				lOpzioniTimbroBean.setMoreLines(true);
			
			}
		}
		
		return lOpzioniTimbroBean;
	}
	
	/**
	 *APPOSIZIONE TIMBRO PER FILE ALLEGATO
	 */
	private FileDaFirmareBean timbraFileAllegato(FileDaFirmareBean lFileDaTimbrareBean,	ProtocollazioneBean bean, String idUd, Integer index) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = buildOpzioniApponiTimbroAllegato(idUd, lFileDaTimbrareBean, bean.getListaAllegati().get(index).getOpzioniTimbro());
		
		// Timbro il file
		TimbraResultBean lTimbraResultBean = new TimbraUtility().timbra(lOpzioniTimbroBean, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBean.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBean.setUri(lTimbraResultBean.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBean.getError())) {
				errorMessage += ": " + lTimbraResultBean.getError();
			}
			throw new Exception(errorMessage);
		}	
		
		File lFileTimbrato = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBean.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lFileDaTimbrareBean.setNomeFile(FilenameUtils.getBaseName(lFileDaTimbrareBean.getNomeFile()) + ".pdf");
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);
		
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean timbraFileAllegatoOmissis(FileDaFirmareBean lFileDaTimbrareBeanOmissis,	ProtocollazioneBean bean, String idUd, Integer index) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBeanOmissis = buildOpzioniApponiTimbroAllegato(idUd, lFileDaTimbrareBeanOmissis, bean.getListaAllegati().get(index).getOpzioniTimbroOmissis());
		
		// Timbro il file
		TimbraResultBean lTimbraResultBeanOmissis = new TimbraUtility().timbra(lOpzioniTimbroBeanOmissis, getSession());
		// Verifico se la timbratura è andata a buon fine
		if (lTimbraResultBeanOmissis.isResult()) {
			// Aggiungo il file timbrato nella lista dei file da pubblicare
			lFileDaTimbrareBeanOmissis.setUri(lTimbraResultBeanOmissis.getUri());
		} else {
			// // La timbratura è fallita, pubblico il file sbustato
			// files.add(StorageImplementation.getStorage().extractFile(uriFileSbustato));
			String errorMessage = "Si è verificato un errore durante la timbratura del file";
			if (StringUtils.isNotBlank(lTimbraResultBeanOmissis.getError())) {
				errorMessage += ": " + lTimbraResultBeanOmissis.getError();
			}
			throw new Exception(errorMessage);
		}		
		
		File lFileTimbratoOmissis = StorageImplementation.getStorage().extractFile(lFileDaTimbrareBeanOmissis.getUri());		
		MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
		lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileTimbratoOmissis.toURI().toString(), lFileDaTimbrareBeanOmissis.getNomeFile(), false, null);
		lFileDaTimbrareBeanOmissis.setNomeFile(FilenameUtils.getBaseName(lFileDaTimbrareBeanOmissis.getNomeFile()) + ".pdf");
		lMimeTypeFirmaBeanOmissis.setFirmato(false);
		lMimeTypeFirmaBeanOmissis.setFirmaValida(false);
		lMimeTypeFirmaBeanOmissis.setConvertibile(false);
		lMimeTypeFirmaBeanOmissis.setDaScansione(false);
		lFileDaTimbrareBeanOmissis.setInfoFile(lMimeTypeFirmaBeanOmissis);	
		
		return lFileDaTimbrareBeanOmissis;
	}
	
	public OpzioniTimbroBean buildOpzioniApponiTimbroAllegato(String idUd, FileDaFirmareBean lFileDaTimbrareBean, OpzioniTimbroDocBean lOpzioniTimbroDocBean) throws Exception {
		
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype(lFileDaTimbrareBean.getInfoFile() != null ? lFileDaTimbrareBean.getInfoFile().getMimetype() : null);
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		// Sono in fase di timbratura postreg, non ho iddoc e quindi passo -999 per dire alla store che sono in questa situazione
		lOpzioniTimbroBean.setIdDoc("-999");
		lOpzioniTimbroBean.setRemote(true);
		lOpzioniTimbroBean.setNroProgAllegato(lFileDaTimbrareBean.getNroProgAllegato());
		lOpzioniTimbroBean = new TimbraUtility().loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());
		
		/**
		 * Se l'utente vuole saltare la scelta delle preference di apposizione timbro allora:
		 * Scelta delle preference di apposizione timbro, se sono presenti le preference utilizzo queste altrimenti
		 * vengono recuperate detro il config.xml dal bean OpzioniTimbroAutoDocRegBean.
		 * Altrimenti utilizzo le preference utilizzate per ogni file.
		 */

		OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = null;
		try{
			 lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			logProtDS.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}
		
		String rotazioneTimbroPref =  getExtraparams().get("rotazioneTimbroPref");
		String posizioneTimbroPref =  getExtraparams().get("posizioneTimbroPref");
		String tipoPaginaTimbroPref = getExtraparams().get("tipoPaginaTimbroPref");
			
		String rotazioneTimbroBean = lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale";
		String posizioneTimbroBean =  lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
				!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn";
		String tipoPaginaTimbroBean = lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null ? 
				lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value() : "TUTTE";
		
		if(StringUtils.isNotBlank(getExtraparams().get("skipSceltaApponiTimbro")) && "true".equals(getExtraparams().get("skipSceltaApponiTimbro"))){
			
			/**
			 * In ordine viene data la seguente priorità di opzioni di generazione timbro
			 * 1) Preference di generazione timbro globale
			 * 2) Preference presenti nel config.xml
			 */
			
			lOpzioniTimbroBean.setRotazioneTimbro(StringUtils.isNotBlank(rotazioneTimbroPref) ? rotazioneTimbroPref : rotazioneTimbroBean);
			lOpzioniTimbroBean.setPosizioneTimbro(StringUtils.isNotBlank(posizioneTimbroPref) ? posizioneTimbroPref : posizioneTimbroBean);
			lOpzioniTimbroBean.setTipoPagina(StringUtils.isNotBlank(tipoPaginaTimbroPref) ? tipoPaginaTimbroPref : tipoPaginaTimbroBean);
			lOpzioniTimbroBean.setTimbroSingolo(false);
			lOpzioniTimbroBean.setMoreLines(true);
			
		} else {
			/**
			 * In ordine viene data la seguente priorità di opzioni di generazione timbro
			 * 1) Preference di generazione timbro presenti nella select di scelta per ogni file
			 * 2) Preference di generazione timbro globale
			 * 3) Preference presenti nel config.xml
			 */
		
			if( lOpzioniTimbroDocBean != null ){
				
				String rotazioneOpzTimbro =   lOpzioniTimbroDocBean.getRotazioneTimbro();
				String posizioneOpzTimbro  =  lOpzioniTimbroDocBean.getPosizioneTimbro();
				String tipoPaginaOpzTimbro  = lOpzioniTimbroDocBean.getTipoPaginaTimbro();
				
				if(StringUtils.isNotBlank(rotazioneOpzTimbro)){
					lOpzioniTimbroBean.setRotazioneTimbro(rotazioneOpzTimbro);
				} else {
					lOpzioniTimbroBean.setRotazioneTimbro(StringUtils.isNotBlank(rotazioneTimbroPref) ? rotazioneTimbroPref : rotazioneTimbroBean);
				}
				if(StringUtils.isNotBlank(posizioneOpzTimbro)){
					lOpzioniTimbroBean.setPosizioneTimbro(posizioneOpzTimbro);
				} else {
					lOpzioniTimbroBean.setPosizioneTimbro(StringUtils.isNotBlank(posizioneTimbroPref) ? posizioneTimbroPref : posizioneTimbroBean);
				}
				if(StringUtils.isNotBlank(tipoPaginaOpzTimbro)){
					if("intervallo".equals(tipoPaginaOpzTimbro)){
						lOpzioniTimbroBean.setTipoPagina(null);
						lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzioniTimbroDocBean.getPaginaDa()));
						lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzioniTimbroDocBean.getPaginaA()));
					}else{
						lOpzioniTimbroBean.setTipoPagina(tipoPaginaOpzTimbro);
					}
				}else{
					lOpzioniTimbroBean.setTipoPagina(StringUtils.isNotBlank(tipoPaginaTimbroPref) ? tipoPaginaTimbroPref : tipoPaginaTimbroBean);
				}
				
				lOpzioniTimbroBean.setTimbroSingolo(false);
				lOpzioniTimbroBean.setMoreLines(true);
			}
		}
		
		return lOpzioniTimbroBean;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_ASSEGN_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailAssegnUD(List<TipoIdBean> listaAssegnatari, Integer index) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_ASSEGN_UD"))) {
			if(listaAssegnatari.get(index) != null &&
			   listaAssegnatari.get(index).getFlgMandaNotificaMail() != null &&
			   listaAssegnatari.get(index).getFlgMandaNotificaMail() ) {
					attivo = true;
			}
		}
		return attivo;
	}
	
	/**
	 * ATTIVA_NOT_EMAIL_INVIO_CC_UD con valori: 
	 * on-demand: si verifica il check lato gui flgMandaNotificaMail
	 * true
	 * false / null
	 */
	private Boolean attivaNotEmailInvioCCUD(List<TipoIdBean> listaAssegnatari,Integer index) {
		
		boolean attivo = false;
		if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
			"true".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			attivo = true;
		} else 	if (ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD") != null &&
				"on-demand".equals(ParametriDBUtil.getParametroDB(getSession(), "ATTIVA_NOT_EMAIL_INVIO_CC_UD"))) {
			if(listaAssegnatari.get(index) != null &&
			   listaAssegnatari.get(index).getFlgMandaNotificaMail() != null &&
			   listaAssegnatari.get(index).getFlgMandaNotificaMail() ) {
					attivo = true;
			}
		}
		return attivo;
	}
	
	public ProtocollazioneBean modificaTipologiaUD(ProtocollazioneBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIduddocin(bean.getIdDocPrimario());
		input.setFlgtipotargetin("D");

		CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
		lCreaModDocumentoInBean.setTipoDocumento(getExtraparams().get("tipoDocumento"));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean));

		DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean,input);

		if (lUpddocudOutput.isInError()) {
			throw new StoreException(lUpddocudOutput);
		}

		return bean;
	}
	
	public static boolean canBeTimbrato(MimeTypeFirmaBean mimeTypeFirmaBean) {
		if (mimeTypeFirmaBean == null)
			return false;
		else {
			String mimetype = mimeTypeFirmaBean.getMimetype() != null ? mimeTypeFirmaBean.getMimetype() : "";
			if (mimetype != null) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || mimeTypeFirmaBean.isConvertibile()) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}
	
	public AssInviiCCBean getListaAssInviiCCMulta(AssInviiCCBean bean) throws Exception {
		String assInviiCcMulte = ParametriDBUtil.getParametroDB(getSession(), "ASS_INVII_CC_MULTE");
		if(assInviiCcMulte != null && !"".equalsIgnoreCase(assInviiCcMulte)) {					
			List<AssegnazioneBean> listaAssegnazioni = new ArrayList<AssegnazioneBean>();
			List<DestInvioCCBean> listaDestInvioCC = new ArrayList<DestInvioCCBean>();
			List<AssInviiCCXmlBean> listaAssInviiCCMulte = XmlListaUtility.recuperaLista(assInviiCcMulte, AssInviiCCXmlBean.class);
			if(listaAssInviiCCMulte != null && !listaAssInviiCCMulte.isEmpty()) {
				for(AssInviiCCXmlBean item : listaAssInviiCCMulte) {
					if(item.getTipo() != null) {
						if("A".equalsIgnoreCase(item.getTipo())) {									
							AssegnazioneBean lAssegnazioneBean = new AssegnazioneBean();
							lAssegnazioneBean.setTipo("SV;UO");						
							lAssegnazioneBean.setTypeNodo("UO");
							lAssegnazioneBean.setOrganigramma("UO" + item.getIdUo());
							lAssegnazioneBean.setIdUo(item.getIdUo());
							lAssegnazioneBean.setCodRapido(item.getCodRapido());
							lAssegnazioneBean.setDescrizione(item.getDenominazione());
							lAssegnazioneBean.setDescrizioneEstesa(item.getDenominazione());
							listaAssegnazioni.add(lAssegnazioneBean);
						} else if("C".equalsIgnoreCase(item.getTipo())) {									
							DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
							lDestInvioCCBean.setTipo("SV;UO");						
							lDestInvioCCBean.setTypeNodo("UO");
							lDestInvioCCBean.setOrganigramma("UO" + item.getIdUo());
							lDestInvioCCBean.setIdUo(item.getIdUo());
							lDestInvioCCBean.setCodRapido(item.getCodRapido());
							lDestInvioCCBean.setDescrizione(item.getDenominazione());
							lDestInvioCCBean.setDescrizioneEstesa(item.getDenominazione());
							listaDestInvioCC.add(lDestInvioCCBean);					
						}		
					}
				}
			}
			bean.setListaAssegnazioni(listaAssegnazioni);
			bean.setListaDestInvioCC(listaDestInvioCC);
		}
		return bean;
	}
	
	public ProtocollazioneBean generaNroProtContratti(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		List<TipoNumerazioneBean> listaTipiNumerazioni = new ArrayList<TipoNumerazioneBean>();
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		
		lCreaDocumentoInBean.setFlgDocContrattiConBarcode(1);
		
		TipoProvenienza lTipoProvenienza = null;
		if (bean.getFlgTipoProv().equalsIgnoreCase("E")) {
			lTipoProvenienza = TipoProvenienza.ENTRATA;
		}
		lCreaDocumentoInBean.setFlgTipoProv(lTipoProvenienza);

		TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();				
		lTipoNumerazioneBean.setSigla(null);
		lTipoNumerazioneBean.setCategoria("PG");
		lTipoNumerazioneBean.setIdUo(StringUtils.isNotBlank(bean.getUoProtocollante()) ? bean.getUoProtocollante().substring(2) : null);
		listaTipiNumerazioni.add(lTipoNumerazioneBean);
		lCreaDocumentoInBean.setTipoNumerazioni(listaTipiNumerazioni);
		
		FilePrimarioBean filePrimarioBean = null;
		AllegatiBean lAllegatiBean = null;
		
		CreaModDocumentoOutBean lCreaDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), lAurigaLoginBean, lCreaDocumentoInBean, filePrimarioBean, lAllegatiBean);
		
		bean.setIdUd(lCreaDocumentoOutBean.getIdUd());
		
		if (lCreaDocumentoOutBean.getDefaultMessage() != null) {
			throw new StoreException(lCreaDocumentoOutBean);
		}
		
//		bean.setIdUd(new BigDecimal(6569065));
		
		return bean;
	}
	
	public AssInviiCCBean getListaAssInviiCCAccessoCivico(AssInviiCCBean bean) throws Exception {
		
		String richiestaAccesso = getExtraparams().get("richiestaAccesso");
		String mode = getExtraparams().get("mode");
		if(richiestaAccesso != null) {
			if("S".equalsIgnoreCase(richiestaAccesso)){
				String assInviiCcAccessoSemplice = ParametriDBUtil.getParametroDB(getSession(), "ASS_INVII_CC_ACCESSO_SEMPLICE");
				if(assInviiCcAccessoSemplice != null && !"".equalsIgnoreCase(assInviiCcAccessoSemplice)) {					
					List<DestinatarioProtBean> listaDestinatariAss = new ArrayList<DestinatarioProtBean>();
					List<DestinatarioProtBean> listaDestinatariInviiCC = new ArrayList<DestinatarioProtBean>();
					List<AssegnazioneBean> listaAssegnazioni = new ArrayList<AssegnazioneBean>();
					List<DestInvioCCBean> listaDestInvioCC = new ArrayList<DestInvioCCBean>();
					List<AssInviiCCXmlBean> listaAssInviiCCAccessoCivicoSemplice = XmlListaUtility.recuperaLista(assInviiCcAccessoSemplice, AssInviiCCXmlBean.class);
					if(listaAssInviiCCAccessoCivicoSemplice != null && !listaAssInviiCCAccessoCivicoSemplice.isEmpty()) {
						for(AssInviiCCXmlBean item : listaAssInviiCCAccessoCivicoSemplice) {
							if(item.getTipo() != null) {
								if("A".equalsIgnoreCase(item.getTipo())) {									
									if("modificaDati".equalsIgnoreCase(mode)) {
										AssegnazioneBean lAssegnazioneBean = new AssegnazioneBean();
										lAssegnazioneBean.setTipo("SV;UO");						
										lAssegnazioneBean.setTypeNodo("UO");
										lAssegnazioneBean.setOrganigramma("UO" + item.getIdUo());
										lAssegnazioneBean.setIdUo(item.getIdUo());
										lAssegnazioneBean.setCodRapido(item.getCodRapido());
										lAssegnazioneBean.setDescrizione(item.getDenominazione());
										lAssegnazioneBean.setDescrizioneEstesa(item.getDenominazione());
										listaAssegnazioni.add(lAssegnazioneBean);
									} else {
										DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();	
										if(ParametriDBUtil.getParametroDBAsBoolean(getSession(),"DEST_INT_CON_SELECT")) {
											lDestinatarioProtBean.setTipoDestinatario("UP_UOI");
										} else {
											lDestinatarioProtBean.setTipoDestinatario("UOI");
										}								
										lDestinatarioProtBean.setCodRapidoDestinatario(item.getCodRapido());
										lDestinatarioProtBean.setDenominazioneDestinatario(item.getDenominazione());
										lDestinatarioProtBean.setIdUoSoggetto(item.getIdUo());
										lDestinatarioProtBean.setOrganigrammaDestinatario("UO" + item.getIdUo());
										lDestinatarioProtBean.setFlgAssegnaAlDestinatario(true);
										lDestinatarioProtBean.setFlgAssegnaAlDestinatarioReadOnly(true);
										lDestinatarioProtBean.setFlgPC(false);										
										listaDestinatariAss.add(lDestinatarioProtBean);
									}
								} else if("C".equalsIgnoreCase(item.getTipo())) {									
									if("modificaDati".equalsIgnoreCase(mode)) {
										DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
										lDestInvioCCBean.setTipo("SV;UO");						
										lDestInvioCCBean.setTypeNodo("UO");
										lDestInvioCCBean.setOrganigramma("UO" + item.getIdUo());
										lDestInvioCCBean.setIdUo(item.getIdUo());
										lDestInvioCCBean.setCodRapido(item.getCodRapido());
										lDestInvioCCBean.setDescrizione(item.getDenominazione());
										lDestInvioCCBean.setDescrizioneEstesa(item.getDenominazione());
										listaDestInvioCC.add(lDestInvioCCBean);
									} else {
										DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
										if(ParametriDBUtil.getParametroDBAsBoolean(getSession(),"DEST_INT_CON_SELECT")) {
											lDestinatarioProtBean.setTipoDestinatario("UP_UOI");
										} else {
											lDestinatarioProtBean.setTipoDestinatario("UOI");
										}
										lDestinatarioProtBean.setCodRapidoDestinatario(item.getCodRapido());
										lDestinatarioProtBean.setDenominazioneDestinatario(item.getDenominazione());
										lDestinatarioProtBean.setIdUoSoggetto(item.getIdUo());
										lDestinatarioProtBean.setOrganigrammaDestinatario("UO" + item.getIdUo());
										lDestinatarioProtBean.setFlgAssegnaAlDestinatario(false);
										lDestinatarioProtBean.setFlgPC(true);
										lDestinatarioProtBean.setFlgPCReadOnly(true);
										listaDestinatariInviiCC.add(lDestinatarioProtBean);
									}								
								}		
							}
						}
					}
					bean.setListaDestinatariAssegnazioni(listaDestinatariAss);
					bean.setListaDestinatariInviiCC(listaDestinatariInviiCC);
					bean.setListaAssegnazioni(listaAssegnazioni);
					bean.setListaDestInvioCC(listaDestInvioCC);
				}	
			} else if("G".equalsIgnoreCase(richiestaAccesso)){
				String assInviiCcAccessoGeneralizzato = ParametriDBUtil.getParametroDB(getSession(), "ASS_INVII_CC_ACCESSO_GENERALIZZATO");
				if(assInviiCcAccessoGeneralizzato != null && !"".equalsIgnoreCase(assInviiCcAccessoGeneralizzato)) {
					List<DestinatarioProtBean> listaDestinatariAss = new ArrayList<DestinatarioProtBean>();
					List<DestinatarioProtBean> listaDestinatariInviiCC = new ArrayList<DestinatarioProtBean>();
					List<AssegnazioneBean> listaAssegnazioni = new ArrayList<AssegnazioneBean>();
					List<DestInvioCCBean> listaDestInvioCC = new ArrayList<DestInvioCCBean>();
					List<AssInviiCCXmlBean> listaAssInviiCCAccessoCivicoGeneralizzato = XmlListaUtility.recuperaLista(assInviiCcAccessoGeneralizzato, AssInviiCCXmlBean.class);
					if(listaAssInviiCCAccessoCivicoGeneralizzato != null && !listaAssInviiCCAccessoCivicoGeneralizzato.isEmpty()) {
						for(AssInviiCCXmlBean item : listaAssInviiCCAccessoCivicoGeneralizzato) {
							if(item.getTipo() != null) {
								if("A".equalsIgnoreCase(item.getTipo())) {
									if("modificaDati".equalsIgnoreCase(mode)) {
										AssegnazioneBean lAssegnazioneBean = new AssegnazioneBean();
										lAssegnazioneBean.setTipo("SV;UO");						
										lAssegnazioneBean.setTypeNodo("UO");
										lAssegnazioneBean.setOrganigramma("UO" + item.getIdUo());
										lAssegnazioneBean.setIdUo(item.getIdUo());
										lAssegnazioneBean.setCodRapido(item.getCodRapido());
										lAssegnazioneBean.setDescrizione(item.getDenominazione());
										lAssegnazioneBean.setDescrizioneEstesa(item.getDenominazione());
										listaAssegnazioni.add(lAssegnazioneBean);
									} else {
										DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();	
										if(ParametriDBUtil.getParametroDBAsBoolean(getSession(),"DEST_INT_CON_SELECT")) {
											lDestinatarioProtBean.setTipoDestinatario("UP_UOI");
										} else {
											lDestinatarioProtBean.setTipoDestinatario("UOI");
										}
										lDestinatarioProtBean.setCodRapidoDestinatario(item.getCodRapido());
										lDestinatarioProtBean.setDenominazioneDestinatario(item.getDenominazione());
										lDestinatarioProtBean.setIdUoSoggetto(item.getIdUo());
										lDestinatarioProtBean.setOrganigrammaDestinatario("UO" + item.getIdUo());
										lDestinatarioProtBean.setFlgAssegnaAlDestinatario(true);
										lDestinatarioProtBean.setFlgAssegnaAlDestinatarioReadOnly(true);
										lDestinatarioProtBean.setFlgPC(false);
										listaDestinatariAss.add(lDestinatarioProtBean);
									}
									
								} else if("C".equalsIgnoreCase(item.getTipo())) {
									if("modificaDati".equalsIgnoreCase(mode)) {
										DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
										lDestInvioCCBean.setTipo("SV;UO");						
										lDestInvioCCBean.setTypeNodo("UO");
										lDestInvioCCBean.setOrganigramma("UO" + item.getIdUo());
										lDestInvioCCBean.setIdUo(item.getIdUo());
										lDestInvioCCBean.setCodRapido(item.getCodRapido());
										lDestInvioCCBean.setDescrizione(item.getDenominazione());
										lDestInvioCCBean.setDescrizioneEstesa(item.getDenominazione());
										listaDestInvioCC.add(lDestInvioCCBean);
									} else {
										DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
										if(ParametriDBUtil.getParametroDBAsBoolean(getSession(),"DEST_INT_CON_SELECT")) {
											lDestinatarioProtBean.setTipoDestinatario("UP_UOI");
										} else {
											lDestinatarioProtBean.setTipoDestinatario("UOI");
										}
										lDestinatarioProtBean.setCodRapidoDestinatario(item.getCodRapido());
										lDestinatarioProtBean.setDenominazioneDestinatario(item.getDenominazione());
										lDestinatarioProtBean.setIdUoSoggetto(item.getIdUo());
										lDestinatarioProtBean.setOrganigrammaDestinatario("UO" + item.getIdUo());
										lDestinatarioProtBean.setFlgAssegnaAlDestinatario(false);
										lDestinatarioProtBean.setFlgPC(true);
										lDestinatarioProtBean.setFlgPCReadOnly(true);
										listaDestinatariInviiCC.add(lDestinatarioProtBean);
									}
								}	
							}
						}
					}
					bean.setListaDestinatariAssegnazioni(listaDestinatariAss);
					bean.setListaDestinatariInviiCC(listaDestinatariInviiCC);
					bean.setListaAssegnazioni(listaAssegnazioni);
					bean.setListaDestInvioCC(listaDestInvioCC);
				}
			}		
		}
		return bean;
	}
	
	private boolean isCodiceRaccomandata(String codiceMezzoTrasmissioneIn){
		
		if(StringUtils.isBlank(codiceMezzoTrasmissioneIn))
			return false;
		
		String codici = ParametriDBUtil.getParametroDB(getSession(), "COD_MEZZI_RACCOMANDATA");
		
		if(StringUtils.isBlank(codici))
			codici = "R;";
		
		// Se il codice del mezzo di trasmissione e' nel parametro, allora mostro i campi della raccomandata		
		String[] tokens = codici.split(";");
		
		if (tokens.length > 0) {			
			for (int i = 0; i < tokens.length; i++) {				
				if (tokens[i].equals(codiceMezzoTrasmissioneIn)) {
                   return true;					
				}
			}
		}
		return false;
	}
	
	public ProtocollazioneBean updateAllegatiDocumento(ProtocollazioneBean bean) throws Exception {
		
		logProtDS.debug("#######INIZIO updateAllegatiDocumento");
		boolean isFromFirmaOVistoMassivi = getExtraparams().get("isFromFirmaOVistoMassivi") != null && "true".equals(getExtraparams().get("isFromFirmaOVistoMassivi"));
		boolean skipAggiornaMetadatiFileNonModificati = getExtraparams().get("skipAggiornaMetadatiFileNonModificati") != null && "true".equals(getExtraparams().get("skipAggiornaMetadatiFileNonModificati"));
		
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		ArrayList<String> idUdDaCollegareList = new ArrayList<String>();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();

		AllegatiBean lAllegatiBean = salvaAllegatiInUpdateDocumento(bean, lAurigaLoginBean, idUdDaCollegareList, lDocumentConfiguration, lModificaDocumentoInBean, list, skipAggiornaMetadatiFileNonModificati);
		
		FilePrimarioBean lFilePrimarioBean = salvaPrimarioInUpdateDocumento(bean, lAurigaLoginBean, lDocumentConfiguration, list);
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		CreaModDocumentoOutBean lModificaDocumentoOutBean = lGestioneDocumenti.salvadocumentiud(getLocale(), lAurigaLoginBean, bean.getIdUd(), bean.getIdDocPrimario(), lFilePrimarioBean, lAllegatiBean);
		
		String result = "";
		String[] attributes = new String[2];
		attributes[0] = bean.getNroProtocollo() != null ? bean.getNroProtocollo().toString() : "";
		attributes[1] = bean.getAnnoProtocollo() != null ? bean.getAnnoProtocollo() : "";
		String userLanguage = getLocale().getLanguage();
		result = MessageUtil.getValue(userLanguage, getSession(), "protocollazione_message_modifica_registrazione_esito_OK_value", attributes);
		bean.setIdUd(new BigDecimal(lModificaDocumentoOutBean.getIdUd() + ""));
		bean.setFileInErrors(lModificaDocumentoOutBean.getFileInErrors());
		if (lModificaDocumentoOutBean.getFileInErrors() != null && lModificaDocumentoOutBean.getFileInErrors().size() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(result);
			for (String lStrFileInError : lModificaDocumentoOutBean.getFileInErrors().values()) {
				lStringBuffer.append("; " + lStrFileInError);
			}
			addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
		} else {
			if (!isFromFirmaOVistoMassivi) {
				addMessage(result, "", MessageType.INFO);
			}	
		}
		if (lModificaDocumentoOutBean.getSalvataggioMetadatiError() != null && lModificaDocumentoOutBean.getSalvataggioMetadatiError()) {
			if (!isFromFirmaOVistoMassivi) {
				addMessage("Si è verificato un errore durante il salvataggio dei metadati", "", MessageType.WARNING);
			} else {
				addMessage("Si è verificato un errore durante il salvataggio dei metadati su SharePoint", "", MessageType.WARNING);
			}
		}
		
		logProtDS.debug("#######FINE updateAllegatiDocumento");
		return bean;
	}
	
	public HashMap<String, String> getMappaTipiDocFlgRichiestaFirmaDigitale(HashSet<String> setTipiDocumento) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String tipiDocumento = null;
		if (setTipiDocumento != null) {
			for (String tipoDoc : setTipiDocumento) {
				if(StringUtils.isNotBlank(tipoDoc)) {
					if(tipiDocumento == null) {
						tipiDocumento = tipoDoc;
					} else {
						tipiDocumento += "," + tipoDoc;
					}
				}
			}
		}
		HashMap<String, String> mappaTipiDocFlgRichiestaFirmaDigitale = new HashMap<String, String>();
		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_USER_LAVORO|*|" + (loginBean.getIdUserLavoro() != null ? loginBean.getIdUserLavoro() : "") + "|*|ID_DOC_TYPE|*|" + tipiDocumento);
		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), loginBean, lDmpkLoadComboDmfn_load_comboBean);
		if(!lStoreResultBean.isInError()) {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<TipoDocumentoBean> lListXml = XmlListaUtility.recuperaLista(xmlLista, TipoDocumentoBean.class);
			for (TipoDocumentoBean lRiga : lListXml) {
				mappaTipiDocFlgRichiestaFirmaDigitale.put(lRiga.getIdTipoDocumento(), lRiga.getFlgRichiestaFirmaDigitale());
			}
		}
		return mappaTipiDocFlgRichiestaFirmaDigitale;
	}
	
	public void controlloFirmePrimarioEAllegatiXNumerazione(ProtocollazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		HashMap<String, String> mappaErroriFile = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		HashSet<String> setTipiDocumento = new HashSet<String>();
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			if(StringUtils.isNotBlank(bean.getTipoDocumento())) {
				setTipiDocumento.add(bean.getTipoDocumento());
			}
		}
		if (bean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					if(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato())) {
						setTipiDocumento.add(allegato.getListaTipiFileAllegato());
					}
				}
			}
		}	
		HashMap<String, String> mappaTipiDocFlgRichiestaFirmaDigitale = getMappaTipiDocFlgRichiestaFirmaDigitale(setTipiDocumento);
		// se sto modificando una UD già protocollata o repertoriata la data a cui verificare la firma è la data di registrazione (quella più vecchia se c'è sia #RegNumPrincipale.TsRegistrazione che #RegNumSecondaria.TsRegistrazione
		// altrimenti verifico con la data corrente
		Date dataRegistrazione = null;
		boolean isModificaProtocolloRepertorio = bean.getNroProtocollo() != null && bean.getTipoProtocollo() != null && ("PG".equals(bean.getTipoProtocollo()) || "R".equals(bean.getTipoProtocollo()));
		if(isModificaProtocolloRepertorio) {
			if(bean.getDataProtocollo() != null) {
				dataRegistrazione = bean.getDataProtocollo();
				boolean hasNumSecondariaProtocolloRepertorio = bean.getNumeroNumerazioneSecondaria() != null && bean.getTipoNumerazioneSecondaria() != null && ("PG".equals(bean.getTipoNumerazioneSecondaria()) || "R".equals(bean.getTipoNumerazioneSecondaria()));						
				if(hasNumSecondariaProtocolloRepertorio && bean.getDataRegistrazioneNumerazioneSecondaria() != null && bean.getDataRegistrazioneNumerazioneSecondaria().compareTo(bean.getDataProtocollo()) < 0) {
					dataRegistrazione = bean.getDataRegistrazioneNumerazioneSecondaria();
				}
			}
		}
		boolean valid = true;
		sb.append("Alcuni file non hanno superato i controlli previsti sulla presenza e validità di firma digitale:<br/>"); 
		sb.append("<ul>");
		if(!isPropostaAtto()) {
			String flgRichiestaFirmaDigitalePrimario = StringUtils.isNotBlank(bean.getTipoDocumento()) ? mappaTipiDocFlgRichiestaFirmaDigitale.get(bean.getTipoDocumento()) : null;
			if (StringUtils.isNotBlank(flgRichiestaFirmaDigitalePrimario)) {
				if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
					boolean isChangedTipoDocumento = StringUtils.isBlank(bean.getTipoDocumentoSalvato()) || !bean.getTipoDocumentoSalvato().equals(bean.getTipoDocumento());
					boolean isNewOrChanged = bean.getIdDocPrimario() == null || (bean.getIsDocPrimarioChanged() != null && bean.getIsDocPrimarioChanged());
					boolean isFileSalvatoPreRegistrazione = bean.getTsInsLastVerFilePrimario() != null && dataRegistrazione != null && bean.getTsInsLastVerFilePrimario().compareTo(dataRegistrazione) < 0;
					// se sono in modifica di un repertorio o protocollo fileOp lo devo chiamare solo se il primario era già stato salvato prima della registrazione (quindi la verifica è stata fatta con una dataRif precedente a quella di registrazione) ed è stato settato/cambiato il tipo
					// se invece sto registrando un nuovo protocollo/repertorio devo chiamare fileOp solo se il primario era già stato salvato in precedenza e quindi con una dataRif precedente a quella di registrazione (nel caso stia protocollando una bozza)
					// in entrambi i casi chiamerò fileOp solo se il file primario è firmato e ha una tipologia documentale su cui è richiesta una firma digitale valida alla registrazione
					if(bean.getInfoFile() != null && bean.getInfoFile().isFirmato() && "V".equals(flgRichiestaFirmaDigitalePrimario) && ((isModificaProtocolloRepertorio && !isNewOrChanged && isChangedTipoDocumento && isFileSalvatoPreRegistrazione) || (!isModificaProtocolloRepertorio && !isNewOrChanged))) {					
						File lFile = null;
						if (bean.getRemoteUriFilePrimario()) {
							// Il file è esterno
							RecuperoFile lRecuperoFile = new RecuperoFile();
							FileExtractedIn lFileExtractedIn = new FileExtractedIn();
							lFileExtractedIn.setUri(bean.getUriFilePrimario());
							FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), loginBean, lFileExtractedIn);
							lFile = out.getExtracted();
						} else {
							// File locale
							lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
						}
						bean.setInfoFile(new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), bean.getNomeFilePrimario(), false, isModificaProtocolloRepertorio ? dataRegistrazione : null));
					}
					if ("P".equals(flgRichiestaFirmaDigitalePrimario) && bean.getInfoFile() != null && !bean.getInfoFile().isFirmato()) {
						valid = false;
						sb.append("<li>Per la tipologia del documento è obbligatorio che il file primario sia firmato digitalmente.</li>");			
						mappaErroriFile.put("0", "Per la tipologia del documento è obbligatorio che il file sia firmato digitalmente");
					} else if ("V".equals(flgRichiestaFirmaDigitalePrimario) && bean.getInfoFile() != null && (!bean.getInfoFile().isFirmato() || !bean.getInfoFile().isFirmaValida())) {
						valid = false;
						sb.append("<li>Per la tipologia del documento è obbligatorio che il file primario sia firmato digitalmente con firma valida.</li>");
						mappaErroriFile.put("0", "Per la tipologia del documento è obbligatorio che il file sia firmato digitalmente con firma valida");
					}
				} else {
					if ("P".equals(flgRichiestaFirmaDigitalePrimario)) {
						valid = false;
						sb.append("<li>Per la tipologia del documento è obbligatorio che ci sia un file primario firmato digitalmente.</li>");
						mappaErroriFile.put("0", "Per la tipologia del documento è obbligatorio che ci sia un file firmato digitalmente");
					} else if ("V".equals(flgRichiestaFirmaDigitalePrimario)) {
						valid = false;
						sb.append("<li>Per la tipologia del documento è obbligatorio che ci sia un file primario firmato digitalmente con firma valida.</li>");
						mappaErroriFile.put("0", "Per la tipologia del documento è obbligatorio che ci sia un file primario firmato digitalmente con firma valida");
					}	
				}
			}
		}
		if (bean.getListaAllegati() != null) {
			int n = 0;
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				n++;				
				String flgRichiestaFirmaDigitaleAllegato = StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? mappaTipiDocFlgRichiestaFirmaDigitale.get(allegato.getListaTipiFileAllegato()) : null;
				if (StringUtils.isNotBlank(flgRichiestaFirmaDigitaleAllegato)) {
					if (StringUtils.isNotBlank(allegato.getUriFileAllegato()) && StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
						boolean isChangedTipoAllegato = StringUtils.isBlank(allegato.getIdTipoFileAllegatoSalvato()) || !allegato.getIdTipoFileAllegatoSalvato().equals(allegato.getListaTipiFileAllegato());
						boolean isNewOrChanged = allegato.getIdDocAllegato() == null || (allegato.getIsChanged() != null && allegato.getIsChanged());
						boolean isFileSalvatoPreRegistrazione = allegato.getTsInsLastVerFileAllegato() != null && dataRegistrazione != null && allegato.getTsInsLastVerFileAllegato().compareTo(dataRegistrazione) < 0;
						// se sono in modifica di un repertorio o protocollo fileOp lo devo chiamare solo sugli allegati che erano già stati salvati prima della registrazione (quindi la verifica è stata fatta con una dataRif precedente a quella di registrazione) e su cui è stato settato/cambiato il tipo
						// se invece sto registrando un nuovo protocollo/repertorio devo chiamare fileOp solo sugli allegati che erano già stati salvati in precedenza e quindi con una dataRif precedente a quella di registrazione (nel caso stia protocollando una bozza)
						// in entrambi i casi chiamerò fileOp solo sui file allegati firmati e che hanno una tipologia documentale su cui è richiesta una firma digitale valida alla registrazione
						if(allegato.getInfoFile() != null && allegato.getInfoFile().isFirmato() && "V".equals(flgRichiestaFirmaDigitaleAllegato) && ((isModificaProtocolloRepertorio && !isNewOrChanged && isChangedTipoAllegato && isFileSalvatoPreRegistrazione) || (!isModificaProtocolloRepertorio && !isNewOrChanged))) {					
							File lFile = null;
							if (allegato.getRemoteUri()) {
								// Il file è esterno
								RecuperoFile lRecuperoFile = new RecuperoFile();
								FileExtractedIn lFileExtractedIn = new FileExtractedIn();
								lFileExtractedIn.setUri(allegato.getUriFileAllegato());
								FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), loginBean, lFileExtractedIn);
								lFile = out.getExtracted();
							} else {
								// File locale
								lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
							}
							allegato.setInfoFile(new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, isModificaProtocolloRepertorio ? dataRegistrazione : null));
						}
						if ("P".equals(flgRichiestaFirmaDigitaleAllegato) && allegato.getInfoFile() != null && !allegato.getInfoFile().isFirmato()) {
							valid = false;
							sb.append("<li>Per la tipologia dell'allegato N. " + n + " è obbligatorio che il file sia firmato digitalmente.</li>");
							mappaErroriFile.put("" + n, "Per la tipologia dell'allegato è obbligatorio che il file sia firmato digitalmente");
						} else if ("V".equals(flgRichiestaFirmaDigitaleAllegato) && allegato.getInfoFile() != null && (!allegato.getInfoFile().isFirmato() || !allegato.getInfoFile().isFirmaValida())) {
							valid = false;
							sb.append("<li>Per la tipologia dell'allegato N. " + n + " è obbligatorio che il file sia firmato digitalmente con firma valida.</li>");
							mappaErroriFile.put("" + n, "Per la tipologia dell'allegato è obbligatorio che il file sia firmato digitalmente con firma valida");
						}
					} else {
						if ("P".equals(flgRichiestaFirmaDigitaleAllegato)) {
							valid = false;
							sb.append("<li>Per la tipologia dell'allegato N. " + n + " è obbligatorio che ci sia un file firmato digitalmente.</li>");
							mappaErroriFile.put("" + n, "Per la tipologia dell'allegato è obbligatorio che ci sia un file firmato digitalmente");
						} else if ("V".equals(flgRichiestaFirmaDigitaleAllegato)) {
							valid = false;
							sb.append("<li>Per la tipologia dell'allegato N. " + n + " è obbligatorio che ci sia un file firmato digitalmente con firma valida.</li>");
							mappaErroriFile.put("" + n, "Per la tipologia dell'allegato è obbligatorio che ci sia un file firmato digitalmente con firma valida");
						}
					}
				}											
			}
		}
		sb.append("</ul>");
		if(!valid) {
			logProtDS.error(sb.toString());
			if(getExtraparams().get("isAttivaGestioneErroriFile") != null && "true".equals(getExtraparams().get("isAttivaGestioneErroriFile"))) {
				addMessage("Alcuni file non hanno superato i controlli previsti sulla presenza e validità di firma digitale", "", MessageType.ERROR);
				bean.setErroriFile(mappaErroriFile);	
			} else {
				throw new StoreException(sb.toString());
			}
		}
	}
	
}