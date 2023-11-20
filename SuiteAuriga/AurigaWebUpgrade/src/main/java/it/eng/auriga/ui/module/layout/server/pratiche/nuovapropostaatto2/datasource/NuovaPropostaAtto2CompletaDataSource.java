/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.albopretorio.bean.AlboPretorioAttachBean;
import it.eng.albopretorio.bean.FTPUploadFileBean;
import it.eng.albopretorio.bean.ProxyBean;
import it.eng.albopretorio.protocollo.CaricaDocumento;
import it.eng.albopretorio.protocollo.ElaboraResponseWS;
import it.eng.albopretorio.protocollo.FTPUploadFile;
import it.eng.albopretorio.protocollo.SetSystemProxy;
import it.eng.albopretorio.ws.DocumentoType;
import it.eng.auriga.compiler.FileDaIniettareBean;
import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2RollbacknumerazioneudBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocExtractvermodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetdatixmodellipraticaBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetopzuoindettattoBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiGetlistaemendamentiBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindsoggettoinrubricaBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetdatinuovoiterattocomecopiaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.ArchivioDatasource;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.AttributiDinamiciDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.MergeDocument;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.ConversionePdfDataSource;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.ConversionePdfBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.FileDaConvertireBean;
import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean.FileDaFirmare;
import it.eng.auriga.ui.module.layout.server.firmaHsm.datasource.FirmaHsmDataSource;
import it.eng.auriga.ui.module.layout.server.modelliDoc.datasource.bean.ModelliDocBean;
import it.eng.auriga.ui.module.layout.server.organigramma.datasource.bean.SelezionaUOBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.PraticheDataSource;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.CompilaModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ImpostazioniUnioneFileBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.ModelloAttivitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.PraticaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.UnioneFileAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AllegatoParteIntSeparatoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AltriDirRespRegTecnicaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AssessoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AttoRiferimentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaListaModelliNuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CompilaModelloNuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ConsigliereBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.CoordinatoreCompCircBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliATERSIRBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiLiquidazioneAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DestAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DestVantaggioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteAdottanteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteDiConcertoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteProponenteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DirigenteRespRegTecnicaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DisimpegnoAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EmendamentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EmendamentoXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.EstensoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioConMotiviBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespFirmatarioSostitutoAltriDirTecBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IdSVRespVistoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ImputazioneContabileAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.IstruttoreBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.MovimentiContabiliSICRABean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovoAttoComeCopiaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.OpzUOInDettAttoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.OpzUOInDettAttoXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.PeriodoPubblicazioneBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ProponenteAttoConsiglioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ProponentiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RUPCompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RdPCompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RespVisAltBilancioBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabilePEGBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabileUfficioPropBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabileVistiConformitaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ResponsabileVistiPerfezionamentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RifSpesaEntrataAVBBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ScrivaniaDECBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ScrivaniaProponentiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.TaskNuovaPropostaAtto2CompletaFileFirmatiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.UfficioGareAcquistiBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.UtenteRifAttoConsiglioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.ProtocolloDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.VisualizzaVersioniFileDataSource;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiSoggXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OperazioneMassivaProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.TaskFileDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VisualizzaVersioniFileBean;
import it.eng.auriga.ui.module.layout.server.timbra.OpzioniTimbroBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraResultBean;
import it.eng.auriga.ui.module.layout.server.timbra.TimbraUtility;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.bean.Result;
import it.eng.client.AlboPretorioAVBImpl;
import it.eng.client.AlboPretorioReggioImpl;
import it.eng.client.AurigaMailService;
import it.eng.client.DaoTRelAlboAvbVsAuriga;
import it.eng.client.DmpkCore2Rollbacknumerazioneud;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreDel_ud_doc_ver;
import it.eng.client.DmpkCoreFindud;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkModelliDocExtractvermodello;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkProcessesGetdatixmodellipratica;
import it.eng.client.DmpkProcessesGetopzuoindettatto;
import it.eng.client.DmpkRepositoryGuiGetlistaemendamenti;
import it.eng.client.DmpkUtilityFindsoggettoinrubrica;
import it.eng.client.DmpkWfGetdatinuovoiterattocomecopia;
import it.eng.client.GestioneDocumenti;
import it.eng.client.ProtocollazioneProsaImpl;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.client.SalvataggioFile;
import it.eng.document.function.AllegatoVersConOmissisStoreBean;
import it.eng.document.function.bean.AssessoreProponenteBean;
import it.eng.document.function.bean.CIGCUPBean;
import it.eng.document.function.bean.CUIBean;
import it.eng.document.function.bean.ConsigliereProponenteBean;
import it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse;
import it.eng.document.function.bean.ContabilitaAdspResponse;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DatiContabiliADSPXmlBean;
import it.eng.document.function.bean.DatiLiquidazioneAVBXmlBean;
import it.eng.document.function.bean.DestNotificaAttoXmlBean;
import it.eng.document.function.bean.DestinatariNotificaMessiXmlBean;
import it.eng.document.function.bean.DestinatariNotificaPECXmlBean;
import it.eng.document.function.bean.DestinatarioAttoBean;
import it.eng.document.function.bean.DestinatarioVantaggioBean;
import it.eng.document.function.bean.DirRespRegTecnicaBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.KeyValueBean;
import it.eng.document.function.bean.MovimentiContabiliaXmlBean;
import it.eng.document.function.bean.MovimentiGSAXmlBean;
import it.eng.document.function.bean.PeriziaXmlBean;
import it.eng.document.function.bean.ProponentiXmlBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.RespDiConcertoBean;
import it.eng.document.function.bean.RespSpesaBean;
import it.eng.document.function.bean.RespVistiConformitaBean;
import it.eng.document.function.bean.RiepilogoDatiLiquidazioneAVB;
import it.eng.document.function.bean.ScrivaniaDirProponenteBean;
import it.eng.document.function.bean.ScrivaniaEstensoreBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoIn;
import it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoResponseReturn;
import it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoIn;
import it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoResponseReturn;
import it.eng.document.function.bean.alboreggio.AlboReggioAllegato;
import it.eng.document.function.bean.alboreggio.AlboReggioAtto;
import it.eng.document.function.bean.alboreggio.AlboReggioResult;
import it.eng.document.function.bean.prosa.ProsaAllegato;
import it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato;
import it.eng.document.function.bean.prosa.ProsaMittenteDestinatario;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.FirmaUtility;
import it.eng.utility.PdfUtility;
import it.eng.utility.XmlListaSimpleBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniTimbroAttachEmail;
import it.eng.utility.ui.module.layout.shared.bean.SimpleKeyValueBean;
import it.eng.utility.ui.module.layout.shared.bean.TipoFileUnione;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "NuovaPropostaAtto2CompletaDataSource")
public class NuovaPropostaAtto2CompletaDataSource extends AbstractDataSource<NuovaPropostaAtto2CompletaBean, NuovaPropostaAtto2CompletaBean> {
	
	private static final Logger logger = Logger.getLogger(NuovaPropostaAtto2CompletaDataSource.class);
	
	public static final String _FLG_SI = "SI";
//	public static final String _FLG_SI_SENZA_VLD_RIL_IMP = "SI, ma senza movimenti contabili"; // su Milano il valore è "SI, senza validazione/rilascio impegni"
	public static final String _FLG_NO = "NO";
	
	public static final String _MANDATORY = "mandatory"; 
	public static final String _OPTIONAL = "optional";
	
	public static final String _DECORR_PUBBL_STD = "standard";
	public static final String _DECORR_PUBBL_POST = "posticipata";	
	
	public static final String _FLG_EMENDAMENTO_SU_FILE_D = "D";
	public static final String _FLG_EMENDAMENTO_SU_FILE_A = "A";	
	
	public static final String _PERMANENTE = "permanente";
	public static final String _TEMPORANEA = "temporanea";
	
	public static final String _TIPO_LUOGO_DA_TOPONOMASTICA = "da toponomastica";
	public static final String _TIPO_LUOGO_TESTO_LIBERO = "testo libero";	
	
	public static final String _NUM_MESI = "In numero mesi";
	public static final String _DA_AL = "dal / al";
	
	public static final String _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA = "indice classif. esteso";
	public static final String _OPZIONE_AGGREGATO_ACTA = "aggregato";	
	
	/**STATI DELL'ATTO SUL SISTEMA CONTABILE ADSP*/
	public static final int STATO_ATTO_ADSP_ATTO_IN_BOZZA = 0;
	public static final int STATO_ATTO_ADSP_ATTO_CONFERMATO = 1;
	public static final int STATO_ATTO_ADSP_ATTO_ATTESA_CONFORMITA = 2;
	public static final int STATO_ATTO_ADSP_ATTO_VALIDATO = 3;
	public static final int STATO_ATTO_ADSP_ATTO_ADOTTATO = 4;
	
	private final static String SEPARATORE_FILE_DA_INIETTARE = "##@@FILE_DA_INIETTARE@@##";
	
	private String idPubblicazione;
		
	public ProtocolloDataSource getProtocolloDataSource(final NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {	
		
		ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource() {
			
			@Override
			public boolean isAppendRelazioniVsUD(ProtocollazioneBean beanDettaglio) {
				return false;
			}		
			
			@Override
			protected void salvaDocumentiCollegati(ProtocollazioneBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) {
				bean.setListaDocumentiCollegati(null); // per evitare che se cancello l'atto di riferimento che era già stato salvato, poi venga ripassato in salvataggio in #@RelazioniVsUD
				super.salvaDocumentiCollegati(bean, lCreaModDocumentoInBean);			
			}
			
			@Override
			protected void salvaAttributiCustom(ProtocollazioneBean pProtocollazioneBean, SezioneCache pSezioneCacheAttributiDinamici) throws Exception {
				super.salvaAttributiCustom(pProtocollazioneBean, pSezioneCacheAttributiDinamici);
				if(pNuovaPropostaAtto2CompletaBean != null) {
					salvaAttributiCustomProposta(pNuovaPropostaAtto2CompletaBean, pSezioneCacheAttributiDinamici);
				}
			};		
		};		
		lProtocolloDataSource.setSession(getSession());
		lProtocolloDataSource.setExtraparams(getExtraparams());	
		// devo settare in ProtocolloDataSource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
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
		// devo settare in ArchivioDatasource i messages di NuovaPropostaAtto2CompletaDataSource per mostrare a video gli errori in salvataggio dei file
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lArchivioDatasource.setMessages(getMessages()); 
		
		return lArchivioDatasource;
	}
	
	public ConversionePdfDataSource getConversionePdfDataSource() {
		ConversionePdfDataSource lConversionePdfDataSource = new ConversionePdfDataSource();
		lConversionePdfDataSource.setSession(getSession());
		lConversionePdfDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lConversionePdfDataSource.setMessages(getMessages()); 		
		return lConversionePdfDataSource;
	}	
	
	public PraticheDataSource getPraticheDataSource() {
		PraticheDataSource lPraticheDataSource = new PraticheDataSource();
		lPraticheDataSource.setSession(getSession());
		lPraticheDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lPraticheDataSource.setMessages(getMessages()); 		
		return lPraticheDataSource;
	}	
	
	public SIBDataSource getSIBDataSource() {	
		SIBDataSource lSIBDataSource = new SIBDataSource();
		lSIBDataSource.setSession(getSession());
		lSIBDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSIBDataSource.setMessages(getMessages()); 		
		return lSIBDataSource;
	}
	
	public ContabiliaDataSource getContabiliaDataSource() {
		ContabiliaDataSource lContabiliaDataSource = new ContabiliaDataSource();
		lContabiliaDataSource.setSession(getSession());
		lContabiliaDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lContabiliaDataSource.setMessages(getMessages()); 		
		return lContabiliaDataSource;
	}	
	
	public SICRADataSource getSICRADataSource() {
		SICRADataSource lSICRADataSource = new SICRADataSource();
		lSICRADataSource.setSession(getSession());
		lSICRADataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lSICRADataSource.setMessages(getMessages()); 		
		return lSICRADataSource;
	}
	
	public ContabilitaADSPDataSource getContabilitaADSPDataSource() {
		ContabilitaADSPDataSource lContabilitaADSPDataSource = new ContabilitaADSPDataSource();
		lContabilitaADSPDataSource.setSession(getSession());
		lContabilitaADSPDataSource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lContabilitaADSPDataSource.setMessages(getMessages()); 		
		return lContabilitaADSPDataSource;
	}	
	
	public AttributiDinamiciDatasource getAttributiDinamiciDatasource() {
		AttributiDinamiciDatasource lAttributiDinamiciDatasource = new AttributiDinamiciDatasource();
		lAttributiDinamiciDatasource.setSession(getSession());
		lAttributiDinamiciDatasource.setLoginBean(AurigaUserUtil.getLoginInfo(getSession()));				
		lAttributiDinamiciDatasource.setExtraparams(getExtraparams());	
		if(getMessages() == null) {
			setMessages(new ArrayList<MessageBean>());
		}
		lAttributiDinamiciDatasource.setMessages(getMessages()); 		
		return lAttributiDinamiciDatasource;
	}	

	@Override
	public Map<String, String> getExtraparams() {		
		
		Map<String, String> extraparams = super.getExtraparams();
		extraparams.put("isPropostaAtto", "true");
		extraparams.put("flgSalvaOrdAllegati", "true");
		return extraparams;		
	}
	
	public boolean skipDatiContabiliAMC(NuovaPropostaAtto2CompletaBean bean) {
		
		String idUdSkipDatiContabiliAMC = ParametriDBUtil.getParametroDB(getSession(), "ID_UD_SKIP_DATI_CONTAB_AMC");
		return idUdSkipDatiContabiliAMC != null && bean.getIdUd() != null && bean.getIdUd().equals(idUdSkipDatiContabiliAMC);
	}
		
	public boolean isAttivaRequestMovimentiDaAMC(NuovaPropostaAtto2CompletaBean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {			
			boolean isAttivaRequestMovimentiDaAMC = getExtraparams().get("flgAttivaRequestMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaRequestMovimentiDaAMC"));
			return isAttivaRequestMovimentiDaAMC;
		} else {
			/*  
			 * dopo la contabilità non devono più essere recuperati gli impegni da AMC, e quelli salvati in DB non devono essere più sovrascritti. 
			 * bisogna dare un errore bloccante quando il recupero dei dati contabili di AMC non va a buon fine
			 */
			// se l'atto risulta già firmato la situazione deve rimanere congelata a quella salvata in DB, perciò devo inibire il recupero dei dati da AMC
			boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();
			return !isAttoFirmato && !skipDatiContabiliAMC(bean);			
		}
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC(NuovaPropostaAtto2CompletaBean bean) {
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "INT_AMC_OTTIMIZZATA")) {		
			boolean isAttivaSalvataggioMovimentiDaAMC = getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC") != null && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaSalvataggioMovimentiDaAMC"));
			return isAttivaSalvataggioMovimentiDaAMC;
		} else {
			return !skipDatiContabiliAMC(bean);			
		}
	}
	
	public String getPrefixRegNum(NuovaPropostaAtto2CompletaBean bean) {
		if (StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
			String annoRegistrazione = bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione()) : bean.getAnnoRegistrazione();
			return bean.getSiglaRegistrazione() + "_" + bean.getNumeroRegistrazione() + "_" + annoRegistrazione + "_";					
		} else if (StringUtils.isNotBlank(bean.getNumeroRegProvvisoria())) {
			String annoRegProvvisoria = bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria()) : bean.getAnnoRegProvvisoria();
			return bean.getSiglaRegProvvisoria() + "_" + bean.getNumeroRegProvvisoria() + "_" + annoRegProvvisoria + "_";	
		}
		return "";
	}
	
	@Override
	public NuovaPropostaAtto2CompletaBean get(NuovaPropostaAtto2CompletaBean bean) throws Exception {	
		
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
				
		bean.setIdUd(idUd != null ? String.valueOf(idUd.longValue()) : null);
		bean.setIdUdNuovoComeCopia(lProtocollazioneBean.getIdUdNuovoComeCopia() != null ? String.valueOf(lProtocollazioneBean.getIdUdNuovoComeCopia().longValue()) : null);
		bean.setFlgTipoDocDiverso(lProtocollazioneBean.getFlgTipoDocDiverso());
		bean.setDerivaDaRdA(lProtocollazioneBean.getDerivaDaRdA() != null ? "" + lProtocollazioneBean.getDerivaDaRdA() : null);
		bean.setIdTipoDocDaCopiare(lProtocollazioneBean.getIdTipoDocDaCopiare());
		bean.setTimestampGetData(lProtocollazioneBean.getTimestampGetData());
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
		
		// Aggiornata versione da pubblicare
		bean.setFlgAggiornataVersDaPubblicare(lDocumentoXmlOutBean.getFlgAggiornataVersDaPubblicare() == Flag.SETTED);
		
		// Forzata modifica atto
		bean.setFlgForzataModificaAtto(lDocumentoXmlOutBean.getFlgForzataModificaAtto() == Flag.SETTED);
		
		// Modifica cod. atto/LL.PP./beni e servizi
		bean.setAbilModificaCodAttoCMTO(lProtocollazioneBean.getAbilModificaCodAttoCMTO());		
		
		// Avvio revoca atto
		bean.setIdTipoProcRevocaAtto(lDocumentoXmlOutBean.getIdTipoProcRevocaAtto());
		bean.setNomeTipoProcRevocaAtto(lDocumentoXmlOutBean.getNomeTipoProcRevocaAtto());
		bean.setIdDefFlussoWFRevocaAtto(lDocumentoXmlOutBean.getIdDefFlussoWFRevocaAtto());
		bean.setIdTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getIdTipoDocPropostaRevocaAtto());
		bean.setNomeTipoDocPropostaRevocaAtto(lDocumentoXmlOutBean.getNomeTipoDocPropostaRevocaAtto());
		bean.setSiglaPropostaRevocaAtto(lDocumentoXmlOutBean.getSiglaPropostaRevocaAtto());
				
		// Avvio emendamento		
		bean.setIdTipoProcEmendamento(lDocumentoXmlOutBean.getIdTipoProcEmendamento());
		bean.setNomeTipoProcEmendamento(lDocumentoXmlOutBean.getNomeTipoProcEmendamento());
		bean.setIdDefFlussoWFEmendamento(lDocumentoXmlOutBean.getIdDefFlussoWFEmendamento());
		bean.setIdTipoDocPropostaEmendamento(lDocumentoXmlOutBean.getIdTipoDocPropostaEmendamento());
		bean.setNomeTipoDocPropostaEmendamento(lDocumentoXmlOutBean.getNomeTipoDocPropostaEmendamento());
		bean.setSiglaPropostaEmendamento(lDocumentoXmlOutBean.getSiglaPropostaEmendamento());			
		bean.setTipoAttoRifEmendamento(lDocumentoXmlOutBean.getTipoAttoRifEmendamento());			
		bean.setSiglaAttoRifEmendamento(lDocumentoXmlOutBean.getSiglaAttoRifEmendamento());			
		bean.setNumeroAttoRifEmendamento(lDocumentoXmlOutBean.getNumeroAttoRifEmendamento());			
		bean.setAnnoAttoRifEmendamento(lDocumentoXmlOutBean.getAnnoAttoRifEmendamento());
		bean.setIdEmendamentoRif(lDocumentoXmlOutBean.getIdEmendamentoRif());			
		bean.setNumeroEmendamentoRif(lDocumentoXmlOutBean.getNumeroEmendamentoRif());			
		
		/* Dati scheda - Registrazione */
		
		if(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null) {
			// Numerazione finale
			bean.setSiglaRegistrazione(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegistrazione(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegistrazione(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegistrazione(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegistrazione(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegistrazione(lProtocollazioneBean.getDesUOProtocollo());		
			// Numerazione provvisoria
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaNumerazioneSecondaria());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNumeroNumerazioneSecondaria() != null ? String.valueOf(lProtocollazioneBean.getNumeroNumerazioneSecondaria().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataRegistrazioneNumerazioneSecondaria());
			bean.setAnnoRegProvvisoria(lProtocollazioneBean.getAnnoNumerazioneSecondaria());
		} else if(lProtocollazioneBean.getCodCategoriaProtocollo() != null && "R".equals(lProtocollazioneBean.getCodCategoriaProtocollo())) {
			// Numerazione finale
			bean.setSiglaRegistrazione(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegistrazione(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegistrazione(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegistrazione(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegistrazione(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegistrazione(lProtocollazioneBean.getDesUOProtocollo());	
		} else {
			// Numerazione provvisoria
			bean.setSiglaRegProvvisoria(lProtocollazioneBean.getSiglaProtocollo());
			bean.setNumeroRegProvvisoria(lProtocollazioneBean.getNroProtocollo() != null ? String.valueOf(lProtocollazioneBean.getNroProtocollo().longValue()) : null);
			bean.setDataRegProvvisoria(lProtocollazioneBean.getDataProtocollo());
			bean.setAnnoRegProvvisoria(lProtocollazioneBean.getAnnoProtocollo());
			bean.setDesUserRegProvvisoria(lProtocollazioneBean.getDesUserProtocollo());
			bean.setDesUORegProvvisoria(lProtocollazioneBean.getDesUOProtocollo());	
		}
		bean.setEstremiRepertorioPerStruttura(lProtocollazioneBean.getEstremiRepertorioPerStruttura());	
		bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
		bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
		bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
		bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());
		bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
		bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
		bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
		bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
		// Archiviazione in ACTA
		bean.setEsitoInvioACTASerieAttiIntegrali(lProtocollazioneBean.getEsitoInvioACTASerieAttiIntegrali());
		bean.setEsitoInvioACTASeriePubbl(lProtocollazioneBean.getEsitoInvioACTASeriePubbl());								
		
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
		
		/* Dati scheda - Emenda */
		
		bean.setTipoAttoEmendamento(lDocumentoXmlOutBean.getTipoAttoEmendamento());	
		bean.setSiglaAttoEmendamento(lDocumentoXmlOutBean.getSiglaAttoEmendamento());	
		bean.setNumeroAttoEmendamento(lDocumentoXmlOutBean.getNumeroAttoEmendamento());			
		bean.setAnnoAttoEmendamento(lDocumentoXmlOutBean.getAnnoAttoEmendamento());	
		bean.setIdEmendamento(lDocumentoXmlOutBean.getIdEmendamento());	 
		bean.setNumeroEmendamento(lDocumentoXmlOutBean.getNumeroEmendamento());	 
		bean.setFlgEmendamentoSuFile(lDocumentoXmlOutBean.getFlgEmendamentoSuFile());	
		bean.setNumeroAllegatoEmendamento(lDocumentoXmlOutBean.getNumeroAllegatoEmendamento());	 
		bean.setFlgEmendamentoIntegraleAllegato(lDocumentoXmlOutBean.getFlgEmendamentoIntegraleAllegato() == Flag.SETTED);		
		bean.setNumeroPaginaEmendamento(lDocumentoXmlOutBean.getNumeroPaginaEmendamento());	
		bean.setNumeroRigaEmendamento(lDocumentoXmlOutBean.getNumeroRigaEmendamento());			
		bean.setEffettoEmendamento(lDocumentoXmlOutBean.getEffettoEmendamento());	
				
		/* Dati scheda - Destinatari */		
		
		bean.setFlgAttivaDestinatari(lDocumentoXmlOutBean.getFlgAttivaDestinatari() == Flag.SETTED);		
		
		List<DestAttoBean> listaDestinatariAtto = new ArrayList<DestAttoBean>();
		if (lDocumentoXmlOutBean.getListaDestinatariAtto() != null && lDocumentoXmlOutBean.getListaDestinatariAtto().size() > 0) {
			for (DestinatarioAttoBean lDestinatarioAttoBean : lDocumentoXmlOutBean.getListaDestinatariAtto()) {
				DestAttoBean lDestAttoBean = new DestAttoBean();
				lDestAttoBean.setPrefisso(lDestinatarioAttoBean.getPrefisso());
				lDestAttoBean.setDenominazione(lDestinatarioAttoBean.getDenominazione());
				lDestAttoBean.setIndirizzo(lDestinatarioAttoBean.getIndirizzo());
				lDestAttoBean.setCorteseAttenzione(lDestinatarioAttoBean.getCorteseAttenzione());
				lDestAttoBean.setIdSoggRubrica(lDestinatarioAttoBean.getIdSoggRubrica());				
				listaDestinatariAtto.add(lDestAttoBean);
			}
		} else {
			listaDestinatariAtto.add(new DestAttoBean());
		}
		bean.setListaDestinatariAtto(listaDestinatariAtto);
		
		List<DestAttoBean> listaDestinatariPCAtto = new ArrayList<DestAttoBean>();
		if (lDocumentoXmlOutBean.getListaDestinatariPCAtto() != null && lDocumentoXmlOutBean.getListaDestinatariPCAtto().size() > 0) {
			for (DestinatarioAttoBean lDestinatarioPCAttoBean : lDocumentoXmlOutBean.getListaDestinatariPCAtto()) {
				DestAttoBean lDestPCAttoBean = new DestAttoBean();
				lDestPCAttoBean.setPrefisso(lDestinatarioPCAttoBean.getPrefisso());
				lDestPCAttoBean.setDenominazione(lDestinatarioPCAttoBean.getDenominazione());
				lDestPCAttoBean.setIndirizzo(lDestinatarioPCAttoBean.getIndirizzo());
				lDestPCAttoBean.setCorteseAttenzione(lDestinatarioPCAttoBean.getCorteseAttenzione());
				lDestPCAttoBean.setIdSoggRubrica(lDestinatarioPCAttoBean.getIdSoggRubrica());					
				listaDestinatariPCAtto.add(lDestPCAttoBean);
			}
		} else {
			listaDestinatariPCAtto.add(new DestAttoBean());
		}
		bean.setListaDestinatariPCAtto(listaDestinatariPCAtto);
		
		/* Dati scheda - Tipo proposta */
		
		bean.setIniziativaProposta(lDocumentoXmlOutBean.getIniziativaPropAtto());		
		bean.setFlgAttoMeroIndirizzo(lDocumentoXmlOutBean.getFlgAttoMeroIndirizzo() == Flag.SETTED);		
		bean.setFlgAttoCommissario(lDocumentoXmlOutBean.getFlgAttoCommissario() == Flag.SETTED);			
		bean.setFlgModificaRegolamento(lDocumentoXmlOutBean.getFlgModificaRegolamento() == Flag.SETTED);			
		bean.setFlgModificaStatuto(lDocumentoXmlOutBean.getFlgModificaStatuto() == Flag.SETTED);	
		bean.setFlgNomina(lDocumentoXmlOutBean.getFlgNomina() == Flag.SETTED);	
		bean.setFlgRatificaDeliberaUrgenza(lDocumentoXmlOutBean.getFlgRatificaDeliberaUrgenza() == Flag.SETTED);		
		bean.setFlgAttoUrgente(lDocumentoXmlOutBean.getFlgAttoUrgente() == Flag.SETTED);		
		bean.setFlgCommissioniTipoProposta(lDocumentoXmlOutBean.getFlgCommissioni() == Flag.SETTED);		
		
		/* Dati scheda - Circoscrizioni proponenti delibera */
		
		List<SimpleKeyValueBean> listaCircoscrizioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getCircoscrizioniProponenti() != null && lDocumentoXmlOutBean.getCircoscrizioniProponenti().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getCircoscrizioniProponenti()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaCircoscrizioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaCircoscrizioni(listaCircoscrizioni);
		
		/* Dati scheda - Interpellanza */
		
		bean.setTipoInterpellanza(lDocumentoXmlOutBean.getTipoInterpellanza());
		bean.setMotivazioneInterpellanzaRispScritta(lDocumentoXmlOutBean.getMotivazioneInterpellanzaRispScritta());
		
		/* Dati scheda - Ordinanza di mobilità */
		
		bean.setTipoOrdMobilita(lDocumentoXmlOutBean.getTipoOrdMobilita());
		bean.setDataInizioVldOrdinanza(lDocumentoXmlOutBean.getDataInizioVldOrdinanza());
		bean.setDataFineVldOrdinanza(lDocumentoXmlOutBean.getDataFineVldOrdinanza());
		
		bean.setTipoLuogoOrdMobilita(lDocumentoXmlOutBean.getTipoLuogoOrdMobilita());
		bean.setListaIndirizziOrdMobilita(lProtocollazioneBean.getListaAltreVie() != null ? lProtocollazioneBean.getListaAltreVie() : new ArrayList<AltraViaProtBean>());
		bean.setLuogoOrdMobilita(lDocumentoXmlOutBean.getLuogoOrdMobilita());
		bean.setLuogoOrdMobilitaFile(lDocumentoXmlOutBean.getLuogoOrdMobilitaFile());
		
		List<SimpleKeyValueBean> listaCircoscrizioniOrdMobilita = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita() != null && lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getCircoscrizioniOrdMobilita()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaCircoscrizioniOrdMobilita.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaCircoscrizioniOrdMobilita(listaCircoscrizioniOrdMobilita);
		
		bean.setDescrizioneOrdMobilita(lDocumentoXmlOutBean.getDescrizioneOrdMobilita());
		bean.setDescrizioneOrdMobilitaFile(lDocumentoXmlOutBean.getDescrizioneOrdMobilitaFile());
		
		/* Dati scheda - Ruoli */
		
		// Proponenti		
		List<ProponentiBean> listaProponenti = new ArrayList<ProponentiBean>();
		if(lDocumentoXmlOutBean.getProponenti() != null) {
			LinkedHashMap<String, ProponentiBean> mappaProponenti = new LinkedHashMap<String, ProponentiBean>();
			for(int i = 0; i < lDocumentoXmlOutBean.getProponenti().size(); i++) {
				ProponentiBean lProponentiBean = null;
				if(mappaProponenti.containsKey(lDocumentoXmlOutBean.getProponenti().get(i).getIdUo())) {
					lProponentiBean = mappaProponenti.get(lDocumentoXmlOutBean.getProponenti().get(i).getIdUo());
				} else {
					lProponentiBean = new ProponentiBean();
					lProponentiBean.setIdUo(lDocumentoXmlOutBean.getProponenti().get(i).getIdUo());
					lProponentiBean.setCodRapido(lDocumentoXmlOutBean.getProponenti().get(i).getCodRapido());
					lProponentiBean.setDescrizione(lDocumentoXmlOutBean.getProponenti().get(i).getDescrizione());
					lProponentiBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getProponenti().get(i).getDescrizione());
					lProponentiBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getProponenti().get(i).getIdUo());
					lProponentiBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getProponenti().get(i).getIdUo());
					lProponentiBean.setFlgUfficioGare(null);
					lProponentiBean.setListaRdP(new ArrayList<ScrivaniaProponentiBean>());
					lProponentiBean.setListaDirigenti(new ArrayList<ScrivaniaProponentiBean>());
					lProponentiBean.setListaDirettori(new ArrayList<ScrivaniaProponentiBean>());					
//					lProponentiBean.setIdScrivaniaDirettore(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirettore());
//					lProponentiBean.setIdScrivaniaDirettoreFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirettore());
//					lProponentiBean.setCodUoScrivaniaDirettore(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaDirettore());
//					lProponentiBean.setDesScrivaniaDirettore(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaDirettore());
//					lProponentiBean.setTipoVistoScrivaniaDirettore(lDocumentoXmlOutBean.getProponenti().get(i).getTipoVistoScrivaniaDirettore());
//					lProponentiBean.setFlgForzaDispFirmaScrivaniaDirettore(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirettore() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirettore()));
					mappaProponenti.put(lDocumentoXmlOutBean.getProponenti().get(i).getIdUo(), lProponentiBean);
				}
				if(lDocumentoXmlOutBean.getProponenti().get(i).getFlgAggiuntaRdP() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgAggiuntaRdP())) {
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP())) {
						ScrivaniaProponentiBean lScrivaniaRdPProponentiBean = new ScrivaniaProponentiBean();
						lScrivaniaRdPProponentiBean.setIdScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setIdScrivaniaFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setCodUoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setDesScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaRdP());
						lProponentiBean.getListaRdP().add(lScrivaniaRdPProponentiBean);
					}
				} else if(lDocumentoXmlOutBean.getProponenti().get(i).getFlgAggiuntaDirigente() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgAggiuntaDirigente())) {
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente())) {
						ScrivaniaProponentiBean lScrivaniaDirigenteProponentiBean = new ScrivaniaProponentiBean();
						lScrivaniaDirigenteProponentiBean.setIdScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setIdScrivaniaFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setCodUoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setDesScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaDirigente());	
						lScrivaniaDirigenteProponentiBean.setTipoVistoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getTipoVistoScrivaniaDirigente());	
						lScrivaniaDirigenteProponentiBean.setFlgForzaDispFirmaScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirigente() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirigente()));
						lProponentiBean.getListaDirigenti().add(lScrivaniaDirigenteProponentiBean);
					}
				} else {
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP())) {
						ScrivaniaProponentiBean lScrivaniaRdPProponentiBean = new ScrivaniaProponentiBean();
						lScrivaniaRdPProponentiBean.setIdScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setIdScrivaniaFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setCodUoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaRdP());
						lScrivaniaRdPProponentiBean.setDesScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaRdP());
						lProponentiBean.getListaRdP().add(lScrivaniaRdPProponentiBean);
					}
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente())) {
						ScrivaniaProponentiBean lScrivaniaDirigenteProponentiBean = new ScrivaniaProponentiBean();
						lScrivaniaDirigenteProponentiBean.setIdScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setIdScrivaniaFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setCodUoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaDirigente());
						lScrivaniaDirigenteProponentiBean.setDesScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaDirigente());		
						lScrivaniaDirigenteProponentiBean.setTipoVistoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getTipoVistoScrivaniaDirigente());	
						lScrivaniaDirigenteProponentiBean.setFlgForzaDispFirmaScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirigente() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirigente()));
						lProponentiBean.getListaDirigenti().add(lScrivaniaDirigenteProponentiBean);
					}
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirettore())) {
						ScrivaniaProponentiBean lScrivaniaDirettoreProponentiBean = new ScrivaniaProponentiBean();
						lScrivaniaDirettoreProponentiBean.setIdScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirettore());
						lScrivaniaDirettoreProponentiBean.setIdScrivaniaFromLoadDett(lDocumentoXmlOutBean.getProponenti().get(i).getIdScrivaniaDirettore());
						lScrivaniaDirettoreProponentiBean.setCodUoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getCodUoScrivaniaDirettore());
						lScrivaniaDirettoreProponentiBean.setDesScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getDesScrivaniaDirettore());	
						lScrivaniaDirettoreProponentiBean.setTipoVistoScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getTipoVistoScrivaniaDirettore());	
						lScrivaniaDirettoreProponentiBean.setFlgForzaDispFirmaScrivania(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirettore() != null && "1".equals(lDocumentoXmlOutBean.getProponenti().get(i).getFlgForzaDispFirmaScrivaniaDirettore()));
						lProponentiBean.getListaDirettori().add(lScrivaniaDirettoreProponentiBean);
					}
				}
			}
			for(String key : mappaProponenti.keySet()) {
				listaProponenti.add(mappaProponenti.get(key));
			}			
		}
		bean.setListaProponenti(listaProponenti);
		
		// Struttura proponente		
		bean.setUfficioProponente(lDocumentoXmlOutBean.getIdUOProponente());
		bean.setCodUfficioProponente(lDocumentoXmlOutBean.getCodUOProponente());
		bean.setDesUfficioProponente(lDocumentoXmlOutBean.getDesUOProponente());
		bean.setDesDirezioneProponente(lDocumentoXmlOutBean.getDesDirProponente());
		bean.setFlgUfficioProponenteGare(lDocumentoXmlOutBean.getFlgUfficioProponenteGare());
		List<SelezionaUOBean> listaUfficioProponente = new ArrayList<SelezionaUOBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOProponente())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOProponente());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOProponente());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOProponente());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOProponente());
			lSelezionaUOBean.setFlgUfficioGare(lDocumentoXmlOutBean.getFlgUfficioProponenteGare());			
			listaUfficioProponente.add(lSelezionaUOBean);
		}
		bean.setListaUfficioProponente(listaUfficioProponente);
		
		// Tipo provvedimento e sotto-tipo delibera		
		bean.setTipoProvvedimento(lDocumentoXmlOutBean.getTipoProvvedimento());
		bean.setSottotipoDelibera(lDocumentoXmlOutBean.getSottotipoDelibera());
		
		// Ufficio gare acquisti		
		List<UfficioGareAcquistiBean> listaUfficioGareAcquisti = new ArrayList<UfficioGareAcquistiBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOGareAcquisti())) {
			UfficioGareAcquistiBean lUfficioGareAcquistiBean = new UfficioGareAcquistiBean();
			lUfficioGareAcquistiBean.setUfficioGareAcquisti(lDocumentoXmlOutBean.getIdUOGareAcquisti());
			lUfficioGareAcquistiBean.setDesUfficioGareAcquisti(lDocumentoXmlOutBean.getDesUOGareAcquisti());
			listaUfficioGareAcquisti.add(lUfficioGareAcquistiBean);
		} else {
			listaUfficioGareAcquisti.add(new UfficioGareAcquistiBean());
		}
		bean.setListaUfficioGareAcquisti(listaUfficioGareAcquisti);
		
		// Inerente il codice appalti
		bean.setFlgProcExCodAppalti(lDocumentoXmlOutBean.getFlgProcExCodAppalti());
		
		// Ufficio competente		
		bean.setUfficioCompetente(lDocumentoXmlOutBean.getIdUOCompetente());
		bean.setCodUfficioCompetente(lDocumentoXmlOutBean.getCodUOCompetente());
		bean.setDesUfficioCompetente(lDocumentoXmlOutBean.getDesUOCompetente());
		bean.setCdrUOCompetente(lDocumentoXmlOutBean.getCdrUOCompetente());
		
		OpzUOInDettAttoBean lOpzUOInDettAttoBean = new OpzUOInDettAttoBean();		
		lOpzUOInDettAttoBean.setFlgVistoRespUffVisibilita(lDocumentoXmlOutBean.getFlgVistoRespUffVisibilita() == Flag.SETTED);
		lOpzUOInDettAttoBean.setFlgVistoRespUffEditabile(lDocumentoXmlOutBean.getFlgVistoRespUffEditabile() == Flag.SETTED);	
		lOpzUOInDettAttoBean.setFlgVistoRespUffValoriSelectScrivanie(lDocumentoXmlOutBean.getFlgVistoRespUffValoriSelectScrivanie());
		lOpzUOInDettAttoBean.setFlgVistoDirSup1Visibilita(lDocumentoXmlOutBean.getFlgVistoDirSup1Visibilita() == Flag.SETTED);
		lOpzUOInDettAttoBean.setFlgVistoDirSup1Editabile(lDocumentoXmlOutBean.getFlgVistoDirSup1Editabile() == Flag.SETTED);
		lOpzUOInDettAttoBean.setFlgVistoDirSup1ValoriSelectScrivanie(lDocumentoXmlOutBean.getFlgVistoDirSup1ValoriSelectScrivanie());
		lOpzUOInDettAttoBean.setFlgVistoDirSup2Visibilita(lDocumentoXmlOutBean.getFlgVistoDirSup2Visibilita() == Flag.SETTED);
		lOpzUOInDettAttoBean.setFlgVistoDirSup2Editabile(lDocumentoXmlOutBean.getFlgVistoDirSup2Editabile() == Flag.SETTED);
		lOpzUOInDettAttoBean.setFlgVistoDirSup2ValoriSelectScrivanie(lDocumentoXmlOutBean.getFlgVistoDirSup2ValoriSelectScrivanie());		
		bean.setOpzUOCompetente(lOpzUOInDettAttoBean);
		
		List<SelezionaUOBean> listaUfficioCompetente = new ArrayList<SelezionaUOBean>();		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdUOCompetente())) {
			SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
			lSelezionaUOBean.setIdUo(lDocumentoXmlOutBean.getIdUOCompetente());
			lSelezionaUOBean.setCodRapido(lDocumentoXmlOutBean.getCodUOCompetente());
			lSelezionaUOBean.setDescrizione(lDocumentoXmlOutBean.getDesUOCompetente());
			lSelezionaUOBean.setDescrizioneEstesa(lDocumentoXmlOutBean.getDesUOCompetente());				
			lSelezionaUOBean.setOrganigramma("UO" + lDocumentoXmlOutBean.getIdUOCompetente());
			lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + lDocumentoXmlOutBean.getIdUOCompetente());
			listaUfficioCompetente.add(lSelezionaUOBean);
		}
		bean.setListaUfficioCompetente(listaUfficioCompetente);	
		
		// Responsabile Unico Provvedimento codice appalti
		List<RUPCompletaBean> listaRUPCodAppalti = new ArrayList<RUPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRUP())) {
			RUPCompletaBean lRUPCompletaBean = new RUPCompletaBean();
			lRUPCompletaBean.setResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setResponsabileUnicoProvvedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setCodUoResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRUP());
			lRUPCompletaBean.setDesResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getDesScrivaniaRUP());
			lRUPCompletaBean.setCodFiscaleResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getCodFiscaleRUP());
			lRUPCompletaBean.setFlgRUPAncheAdottante(lDocumentoXmlOutBean.getFlgRUPAncheAdottante() == Flag.SETTED);				
			listaRUPCodAppalti.add(lRUPCompletaBean);
		} else {
			listaRUPCodAppalti.add(new RUPCompletaBean());			
		}
		bean.setListaRUPCodAppalti(listaRUPCodAppalti);
		
		// Dir. adottante
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
		
		// Responsabile di Procedimento codice appalti
		List<RdPCompletaBean> listaRdPCodAppalti = new ArrayList<RdPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespProc())) {
			RdPCompletaBean lRdPCompletaBean = new RdPCompletaBean();
			lRdPCompletaBean.setResponsabileDiProcedimento(lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lRdPCompletaBean.setResponsabileDiProcedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRespProc());								
			lRdPCompletaBean.setCodUoResponsabileDiProcedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRespProc());
			lRdPCompletaBean.setDesResponsabileDiProcedimento(lDocumentoXmlOutBean.getDesScrivaniaRespProc());		
			lRdPCompletaBean.setFlgResponsabileDiProcedimentoFirmatario(lDocumentoXmlOutBean.getFlgFirmatarioRespProc() == Flag.SETTED);
			lRdPCompletaBean.setFlgRdPAncheRUP(lDocumentoXmlOutBean.getFlgRespProcAncheRUP() == Flag.SETTED);
			listaRdPCodAppalti.add(lRdPCompletaBean);			
		} else {
			listaRdPCodAppalti.add(new RdPCompletaBean());
		}
		bean.setListaRdPCodAppalti(listaRdPCodAppalti);
		
		// Centro di Costo
		bean.setCentroDiCosto(lDocumentoXmlOutBean.getCentroDiCosto());		
		
		// Adottanti di concerto
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

		// Dir. resp reg. tecnica
		List<DirigenteRespRegTecnicaBean> listaDirRespRegTecnica = new ArrayList<DirigenteRespRegTecnicaBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica())) {
			DirigenteRespRegTecnicaBean lDirigenteRespRegTecnicaBean = new DirigenteRespRegTecnicaBean();
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnica(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaDirRespRegTecnica());									
			lDirigenteRespRegTecnicaBean.setCodUoDirigenteRespRegTecnica(lDocumentoXmlOutBean.getLivelliUOScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setDesDirigenteRespRegTecnica(lDocumentoXmlOutBean.getDesScrivaniaDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setFlgDirRespRegTecnicaAncheRdP(lDocumentoXmlOutBean.getFlgDirAncheRespProc() == Flag.SETTED);
			lDirigenteRespRegTecnicaBean.setFlgDirRespRegTecnicaAncheRUP(lDocumentoXmlOutBean.getFlgDirAncheRUP() == Flag.SETTED);
			
			// Sostituto
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnicaSostituto(lDocumentoXmlOutBean.getIdScrivaniaSostitutoDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDettSostituto(lDocumentoXmlOutBean.getIdScrivaniaSostitutoDirRespRegTecnica());									
			lDirigenteRespRegTecnicaBean.setDesDirigenteRespRegTecnicaSostituto(lDocumentoXmlOutBean.getDesScrivaniaSostitutoDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setProvvedimentoSostituto(lDocumentoXmlOutBean.getProvvSostituzioneDirRespRegTecnica());
			lDirigenteRespRegTecnicaBean.setCodUoDirigenteRespRegTecnicaSostituto(lDocumentoXmlOutBean.getLivelliUOScrivaniaSostitutoDirRespRegTecnica());
			listaDirRespRegTecnica.add(lDirigenteRespRegTecnicaBean);
		} else {
			listaDirRespRegTecnica.add(new DirigenteRespRegTecnicaBean());
		}
		bean.setListaDirRespRegTecnica(listaDirRespRegTecnica);
		
		// Altri pareri reg. tecnica
		List<AltriDirRespRegTecnicaBean> listaAltriDirRespTecnica = new ArrayList<AltriDirRespRegTecnicaBean>();
		if (lDocumentoXmlOutBean.getAltriDirRespTecnica() != null && lDocumentoXmlOutBean.getAltriDirRespTecnica().size() > 0) {
			for (DirRespRegTecnicaBean lDirRespRegTecnicaBean : lDocumentoXmlOutBean.getAltriDirRespTecnica()) {
				AltriDirRespRegTecnicaBean lAltriDirRespRegTecnicaBean = new AltriDirRespRegTecnicaBean();
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getIdSV());
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDett(lDirRespRegTecnicaBean.getIdSV());
				lAltriDirRespRegTecnicaBean.setCodUoDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getCodUO());
				lAltriDirRespRegTecnicaBean.setDesDirigenteRespRegTecnica(lDirRespRegTecnicaBean.getDescrizione());
				lAltriDirRespRegTecnicaBean.setFlgDirigenteRespRegTecnicaFirmatario(lDirRespRegTecnicaBean.getFlgFirmatario());
				
				// Sostituto
				lAltriDirRespRegTecnicaBean.setDesDirigenteRespRegTecnicaSostituto(lDirRespRegTecnicaBean.getDesScrivaniaSostituto());
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnicaSostituto(lDirRespRegTecnicaBean.getIdSVSostituto());
				lAltriDirRespRegTecnicaBean.setDirigenteRespRegTecnicaFromLoadDettSostituto(lDirRespRegTecnicaBean.getIdSVSostituto());
				lAltriDirRespRegTecnicaBean.setProvvedimentoSostituto(lDirRespRegTecnicaBean.getProvvedimentoSostituzione());
				lAltriDirRespRegTecnicaBean.setCodUoDirigenteRespRegTecnicaSostituto(lDirRespRegTecnicaBean.getNriLivelliSostituto());
				lAltriDirRespRegTecnicaBean.setFlgModificabileSostituto((lDirRespRegTecnicaBean.getFlgModificabileSostituto()!=null ? lDirRespRegTecnicaBean.getFlgModificabileSostituto() : true));
				listaAltriDirRespTecnica.add(lAltriDirRespRegTecnicaBean);
			}
		}
		bean.setListaAltriDirRespRegTecnica(listaAltriDirRespTecnica);
		
		// Responsabile di Procedimento
		List<RdPCompletaBean> listaRdP = new ArrayList<RdPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespProc())) {
			RdPCompletaBean lRdPCompletaBean = new RdPCompletaBean();
			lRdPCompletaBean.setResponsabileDiProcedimento(lDocumentoXmlOutBean.getIdScrivaniaRespProc());
			lRdPCompletaBean.setResponsabileDiProcedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRespProc());								
			lRdPCompletaBean.setCodUoResponsabileDiProcedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRespProc());
			lRdPCompletaBean.setDesResponsabileDiProcedimento(lDocumentoXmlOutBean.getDesScrivaniaRespProc());		
			lRdPCompletaBean.setFlgResponsabileDiProcedimentoFirmatario(lDocumentoXmlOutBean.getFlgFirmatarioRespProc() == Flag.SETTED);
			lRdPCompletaBean.setFlgRdPAncheRUP(lDocumentoXmlOutBean.getFlgRespProcAncheRUP() == Flag.SETTED);
			listaRdP.add(lRdPCompletaBean);			
		} else {
			listaRdP.add(new RdPCompletaBean());
		}
		bean.setListaRdP(listaRdP);		
		
		// Procedimento
		bean.setCodProcedimento(lDocumentoXmlOutBean.getCodProcedimento());		
		bean.setDesProcedimento(lDocumentoXmlOutBean.getDesProcedimento());		
				
		// Responsabile Unico Provvedimento
		List<RUPCompletaBean> listaRUP = new ArrayList<RUPCompletaBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRUP())) {
			RUPCompletaBean lRUPCompletaBean = new RUPCompletaBean();
			lRUPCompletaBean.setResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setResponsabileUnicoProvvedimentoFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRUP());
			lRUPCompletaBean.setCodUoResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getCodUOScrivaniaRUP());
			lRUPCompletaBean.setDesResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getDesScrivaniaRUP());
			lRUPCompletaBean.setCodFiscaleResponsabileUnicoProvvedimento(lDocumentoXmlOutBean.getCodFiscaleRUP());
			listaRUP.add(lRUPCompletaBean);
		} else {
			listaRUP.add(new RUPCompletaBean());			
		}
		bean.setListaRUP(listaRUP);
		
		// DEC
		List<ScrivaniaDECBean> listaScrivaniaDEC = new ArrayList<ScrivaniaDECBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaDEC())) {
			ScrivaniaDECBean lScrivaniaDECBean = new ScrivaniaDECBean();
			lScrivaniaDECBean.setScrivaniaDEC(lDocumentoXmlOutBean.getIdScrivaniaDEC());
			lScrivaniaDECBean.setScrivaniaDECFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaDEC());								
			lScrivaniaDECBean.setCodUoScrivaniaDEC(lDocumentoXmlOutBean.getCodUOScrivaniaDEC());
			lScrivaniaDECBean.setDesScrivaniaDEC(lDocumentoXmlOutBean.getDesScrivaniaDEC());		
			listaScrivaniaDEC.add(lScrivaniaDECBean);		
		} else {
			listaScrivaniaDEC.add(new ScrivaniaDECBean());
		}
		bean.setListaScrivaniaDEC(listaScrivaniaDEC);
		
		// Assessore proponente
		List<AssessoreBean> listaAssessori = new ArrayList<AssessoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdAssessoreProponente())) {
			AssessoreBean lAssessoreBean = new AssessoreBean();
			lAssessoreBean.setAssessore(lDocumentoXmlOutBean.getIdAssessoreProponente());
			lAssessoreBean.setAssessoreFromLoadDett(lDocumentoXmlOutBean.getIdAssessoreProponente());
			lAssessoreBean.setDesAssessore(lDocumentoXmlOutBean.getDesAssessoreProponente());
			listaAssessori.add(lAssessoreBean);
		} else {
			listaAssessori.add(new AssessoreBean());
		}
		bean.setListaAssessori(listaAssessori);
		
		// Altri assessori
		List<AssessoreBean> listaAltriAssessori = new ArrayList<AssessoreBean>();
		if (lDocumentoXmlOutBean.getAltriAssessori() != null && lDocumentoXmlOutBean.getAltriAssessori().size() > 0) {
			for (AssessoreProponenteBean lAssessoreProponenteBean : lDocumentoXmlOutBean.getAltriAssessori()) {
				AssessoreBean lAssessoreBean = new AssessoreBean();
				lAssessoreBean.setAssessore(lAssessoreProponenteBean.getIdSvUt());
				lAssessoreBean.setAssessoreFromLoadDett(lAssessoreProponenteBean.getIdSvUt());
				lAssessoreBean.setDesAssessore(lAssessoreProponenteBean.getDescrizione());
				lAssessoreBean.setFlgAssessoreFirmatario(lAssessoreProponenteBean.getFlgFirmatario());
				listaAltriAssessori.add(lAssessoreBean);
			}
		}
		bean.setListaAltriAssessori(listaAltriAssessori);
		
		// Proponente
		List<ProponenteAttoConsiglioBean> listaProponenteAttoConsiglio = new ArrayList<ProponenteAttoConsiglioBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdConsigliereProponente())) {
			ProponenteAttoConsiglioBean lProponenteAttoConsiglioBean = new ProponenteAttoConsiglioBean();
			lProponenteAttoConsiglioBean.setProponenteAttoConsiglio(lDocumentoXmlOutBean.getIdProponenteAttoConsiglio());
			lProponenteAttoConsiglioBean.setProponenteAttoConsiglioFromLoadDett(lDocumentoXmlOutBean.getIdProponenteAttoConsiglio());
			lProponenteAttoConsiglioBean.setDesProponenteAttoConsiglio(lDocumentoXmlOutBean.getDesProponenteAttoConsiglio());
			listaProponenteAttoConsiglio.add(lProponenteAttoConsiglioBean);
		} else {
			listaProponenteAttoConsiglio.add(new ProponenteAttoConsiglioBean());
		}
		bean.setListaProponenteAttoConsiglio(listaProponenteAttoConsiglio);
				
		// Consigliere proponente
		List<ConsigliereBean> listaConsiglieri = new ArrayList<ConsigliereBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdConsigliereProponente())) {
			ConsigliereBean lConsigliereBean = new ConsigliereBean();
			lConsigliereBean.setConsigliere(lDocumentoXmlOutBean.getIdConsigliereProponente());
			lConsigliereBean.setConsigliereFromLoadDett(lDocumentoXmlOutBean.getIdConsigliereProponente());
			lConsigliereBean.setDesConsigliere(lDocumentoXmlOutBean.getDesConsigliereProponente());
			lConsigliereBean.setFlgConsigliereFirmaInSostSindaco(lDocumentoXmlOutBean.getFlgFirmaInSostSindaco() != null && "1".equals(lDocumentoXmlOutBean.getFlgFirmaInSostSindaco()));
			listaConsiglieri.add(lConsigliereBean);
		} else {
			listaConsiglieri.add(new ConsigliereBean());
		}
		bean.setListaConsiglieri(listaConsiglieri);
		
		// Altri consiglieri
		List<ConsigliereBean> listaAltriConsiglieri = new ArrayList<ConsigliereBean>();
		if (lDocumentoXmlOutBean.getAltriConsiglieri() != null && lDocumentoXmlOutBean.getAltriConsiglieri().size() > 0) {
			for (ConsigliereProponenteBean lConsigliereProponenteBean : lDocumentoXmlOutBean.getAltriConsiglieri()) {
				ConsigliereBean lConsigliereBean = new ConsigliereBean();
				lConsigliereBean.setConsigliere(lConsigliereProponenteBean.getIdSvUt());
				lConsigliereBean.setConsigliereFromLoadDett(lConsigliereProponenteBean.getIdSvUt());
				lConsigliereBean.setDesConsigliere(lConsigliereProponenteBean.getDescrizione());
				lConsigliereBean.setFlgConsigliereFirmatario(lConsigliereProponenteBean.getFlgFirmatario());
				listaAltriConsiglieri.add(lConsigliereBean);
			}
		}
		bean.setListaAltriConsiglieri(listaAltriConsiglieri);
		
		// Data termine firme consiglieri
		bean.setDataTermFirmeConsiglieri(lDocumentoXmlOutBean.getDataTermFirmeConsiglieri());
		
		// Dirigente proponente
		List<DirigenteProponenteBean> listaDirigentiProponenti = new ArrayList<DirigenteProponenteBean>();
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaDirProponente())) {
			DirigenteProponenteBean lDirigenteProponenteBean = new DirigenteProponenteBean();
			lDirigenteProponenteBean.setDirigenteProponente(lDocumentoXmlOutBean.getIdScrivaniaDirProponente());
			lDirigenteProponenteBean.setDirigenteProponenteFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaDirProponente());						
			lDirigenteProponenteBean.setDesDirigenteProponente(lDocumentoXmlOutBean.getDesScrivaniaDirProponente());
			lDirigenteProponenteBean.setCodUoDirigenteProponente(lDocumentoXmlOutBean.getLivelliUOScrivaniaDirProponente());
			listaDirigentiProponenti.add(lDirigenteProponenteBean);
		} else {
			listaDirigentiProponenti.add(new DirigenteProponenteBean());
		}
		bean.setListaDirigentiProponenti(listaDirigentiProponenti);	
				
		// Altri dirigenti proponenti
		List<DirigenteProponenteBean> listaAltriDirigentiProponenti = new ArrayList<DirigenteProponenteBean>();
		if (lDocumentoXmlOutBean.getAltriDirProponenti() != null && lDocumentoXmlOutBean.getAltriDirProponenti().size() > 0) {
			for (ScrivaniaDirProponenteBean lScrivaniaDirProponenteBean : lDocumentoXmlOutBean.getAltriDirProponenti()) {
				DirigenteProponenteBean lDirigenteProponenteBean = new DirigenteProponenteBean();
				lDirigenteProponenteBean.setDirigenteProponente(lScrivaniaDirProponenteBean.getIdSV());
				lDirigenteProponenteBean.setDirigenteProponenteFromLoadDett(lScrivaniaDirProponenteBean.getIdSV());
				lDirigenteProponenteBean.setCodUoDirigenteProponente(lScrivaniaDirProponenteBean.getCodUO());
				lDirigenteProponenteBean.setDesDirigenteProponente(lScrivaniaDirProponenteBean.getDescrizione());
				lDirigenteProponenteBean.setFlgDirigenteProponenteFirmatario(lScrivaniaDirProponenteBean.getFlgFirmatario());
				lDirigenteProponenteBean.setMotiviDirigenteProponente(lScrivaniaDirProponenteBean.getMotivi());
				listaAltriDirigentiProponenti.add(lDirigenteProponenteBean);
			}
		}
		bean.setListaAltriDirigentiProponenti(listaAltriDirigentiProponenti);
		
		// Coordinatore competente per materia
		List<CoordinatoreCompCircBean> listaCoordinatoriCompCirc = new ArrayList<CoordinatoreCompCircBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc())) {
			CoordinatoreCompCircBean lCoordinatoreCompCircBean = new CoordinatoreCompCircBean();
			lCoordinatoreCompCircBean.setCoordinatoreCompCirc(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc());
			lCoordinatoreCompCircBean.setCoordinatoreCompCircFromLoadDett(lDocumentoXmlOutBean.getIdCoordinatoreCompCirc());
			lCoordinatoreCompCircBean.setDesCoordinatoreCompCirc(lDocumentoXmlOutBean.getDesCoordinatoreCompCirc());
			listaCoordinatoriCompCirc.add(lCoordinatoreCompCircBean);
		} else {
			listaCoordinatoriCompCirc.add(new CoordinatoreCompCircBean());
		}
		bean.setListaCoordinatoriCompCirc(listaCoordinatoriCompCirc);
		
		// Richiesto visto Dir. sovraordinato
		bean.setFlgRichiediVistoDirettore(lDocumentoXmlOutBean.getFlgRichiediVistoDirettore() == Flag.SETTED);
		
		// Visti di conformità
		List<ResponsabileVistiConformitaBean> listaRespVistiConformita = new ArrayList<ResponsabileVistiConformitaBean>();
		if (lDocumentoXmlOutBean.getRespVistiConformita() != null && lDocumentoXmlOutBean.getRespVistiConformita().size() > 0) {
			for (RespVistiConformitaBean lRespVistiConformitaBean : lDocumentoXmlOutBean.getRespVistiConformita()) {
				ResponsabileVistiConformitaBean lResponsabileVistiConformitaBean = new ResponsabileVistiConformitaBean();
				lResponsabileVistiConformitaBean.setRespVistiConformita(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiConformitaBean.setRespVistiConformitaFromLoadDett(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiConformitaBean.setCodUoRespVistiConformita(lRespVistiConformitaBean.getCodUO());
				lResponsabileVistiConformitaBean.setDesRespVistiConformita(lRespVistiConformitaBean.getDescrizione());
				lResponsabileVistiConformitaBean.setFlgRespVistiConformitaFirmatario(lRespVistiConformitaBean.getFlgFirmatario());
				lResponsabileVistiConformitaBean.setMotiviRespVistiConformita(lRespVistiConformitaBean.getMotivi());
				lResponsabileVistiConformitaBean.setFlgRiacqVistoInRitornoIterRespVistiConformita(lRespVistiConformitaBean.getFlgRiacqVistoInRitornoIter());				
				listaRespVistiConformita.add(lResponsabileVistiConformitaBean);
			}
		}
		bean.setListaRespVistiConformita(listaRespVistiConformita);
		
		// Visto responsabile struttura
		List<ResponsabileUfficioPropBean> listaRespUfficioProp = new ArrayList<ResponsabileUfficioPropBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespUOProp())) {
			ResponsabileUfficioPropBean lResponsabileUfficioPropBean = new ResponsabileUfficioPropBean();
			lResponsabileUfficioPropBean.setRespUfficioProp(lDocumentoXmlOutBean.getIdScrivaniaRespUOProp());
			lResponsabileUfficioPropBean.setRespUfficioPropFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRespUOProp());
			lResponsabileUfficioPropBean.setCodUoRespUfficioProp(lDocumentoXmlOutBean.getLivelliUOScrivaniaRespUOProp());
			lResponsabileUfficioPropBean.setDesRespUfficioProp(lDocumentoXmlOutBean.getDesScrivaniaRespUOProp());
			listaRespUfficioProp.add(lResponsabileUfficioPropBean);
		} else {
			listaRespUfficioProp.add(new ResponsabileUfficioPropBean());
		}
		bean.setListaRespUfficioProp(listaRespUfficioProp);
		
		// Visti di perfezionamento
		List<ResponsabileVistiPerfezionamentoBean> listaRespVistiPerfezionamento = new ArrayList<ResponsabileVistiPerfezionamentoBean>();
		if (lDocumentoXmlOutBean.getRespVistiPerfezionamento() != null && lDocumentoXmlOutBean.getRespVistiPerfezionamento().size() > 0) {
			for (RespVistiConformitaBean lRespVistiConformitaBean : lDocumentoXmlOutBean.getRespVistiPerfezionamento()) {
				ResponsabileVistiPerfezionamentoBean lResponsabileVistiPerfezionamentoBean = new ResponsabileVistiPerfezionamentoBean();
				lResponsabileVistiPerfezionamentoBean.setRespVistiPerfezionamento(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiPerfezionamentoBean.setRespVistiPerfezionamentoFromLoadDett(lRespVistiConformitaBean.getIdSV());
				lResponsabileVistiPerfezionamentoBean.setCodUoRespVistiPerfezionamento(lRespVistiConformitaBean.getCodUO());
				lResponsabileVistiPerfezionamentoBean.setDesRespVistiPerfezionamento(lRespVistiConformitaBean.getDescrizione());
				lResponsabileVistiPerfezionamentoBean.setFlgRespVistiPerfezionamentoFirmatario(lRespVistiConformitaBean.getFlgFirmatario());
				lResponsabileVistiPerfezionamentoBean.setMotiviRespVistiPerfezionamento(lRespVistiConformitaBean.getMotivi());
				lResponsabileVistiPerfezionamentoBean.setFlgRiacqVistoInRitornoIterRespVistiPerfezionamento(lRespVistiConformitaBean.getFlgRiacqVistoInRitornoIter());				
				listaRespVistiPerfezionamento.add(lResponsabileVistiPerfezionamentoBean);
			}
		}
		bean.setListaRespVistiPerfezionamento(listaRespVistiPerfezionamento);
		
		// Visto bilancio
		bean.setFlgVistoBilancio(lDocumentoXmlOutBean.getFlgVistoBilancio());
		
		// Resp. visto alternativo bilancio
		List<RespVisAltBilancioBean> listaRespVisAltBilancio = new ArrayList<RespVisAltBilancioBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaRespVisAltBilancio())) {
			RespVisAltBilancioBean lRespVisAltBilancioBean = new RespVisAltBilancioBean();
			lRespVisAltBilancioBean.setResponsabileVistoAlternativoBilancio(lDocumentoXmlOutBean.getIdScrivaniaRespVisAltBilancio());
			lRespVisAltBilancioBean.setResponsabileVistoAlternativoBilancioFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaRespVisAltBilancio());									
			lRespVisAltBilancioBean.setCodUoResponsabileVistoAlternativoBilancio(lDocumentoXmlOutBean.getLivelliUOScrivaniaRespVisAltBilancio());
			lRespVisAltBilancioBean.setDesResponsabileVistoAlternativoBilancio(lDocumentoXmlOutBean.getDesScrivaniaRespVisAltBilancio());
			listaRespVisAltBilancio.add(lRespVisAltBilancioBean);
		} else {
			listaRespVisAltBilancio.add(new RespVisAltBilancioBean());
		}
		bean.setListaRespVisAltBilancio(listaRespVisAltBilancio);
				
		// Tipo bilancio
		bean.setTipoVistoBilancio(lDocumentoXmlOutBean.getTipoVistoBilancio());
		
		// Visto SG
		bean.setFlgVistoSG(lDocumentoXmlOutBean.getFlgVistoSG() == Flag.SETTED);
		
		// Visto presidente
		bean.setFlgVistoPresidente(lDocumentoXmlOutBean.getFlgVistoPresidente() == Flag.SETTED);
				
		// Estensore principale
		List<EstensoreBean> listaEstensori = new ArrayList<EstensoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain())) {
			EstensoreBean lEstensoreBean = new EstensoreBean();
			lEstensoreBean.setEstensore(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain());
			lEstensoreBean.setEstensoreFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaEstensoreMain());
			lEstensoreBean.setCodUoEstensore(lDocumentoXmlOutBean.getLivelliUOScrivaniaEstensoreMain());
			lEstensoreBean.setDesEstensore(lDocumentoXmlOutBean.getDesScrivaniaEstensoreMain());
			listaEstensori.add(lEstensoreBean);
		} else {
			listaEstensori.add(new EstensoreBean());
		}
		bean.setListaEstensori(listaEstensori);
		
		// Altri estensori
		List<EstensoreBean> listaAltriEstensori = new ArrayList<EstensoreBean>();
		if (lDocumentoXmlOutBean.getAltriEstensori() != null && lDocumentoXmlOutBean.getAltriEstensori().size() > 0) {
			for (ScrivaniaEstensoreBean lScrivaniaEstensoreBean : lDocumentoXmlOutBean.getAltriEstensori()) {
				EstensoreBean lEstensoreBean = new EstensoreBean();
				lEstensoreBean.setEstensore(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setEstensoreFromLoadDett(lScrivaniaEstensoreBean.getIdSV());
				lEstensoreBean.setCodUoEstensore(lScrivaniaEstensoreBean.getCodUO());
				lEstensoreBean.setDesEstensore(lScrivaniaEstensoreBean.getDescrizione());
				listaAltriEstensori.add(lEstensoreBean);
			}
		}
		bean.setListaAltriEstensori(listaAltriEstensori);
		
		// Resp. istruttoria
		List<IstruttoreBean> listaIstruttori = new ArrayList<IstruttoreBean>();
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain())) {
			IstruttoreBean lIstruttoreBean = new IstruttoreBean();
			lIstruttoreBean.setIstruttore(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain());
			lIstruttoreBean.setIstruttoreFromLoadDett(lDocumentoXmlOutBean.getIdScrivaniaIstruttoreMain());
			lIstruttoreBean.setCodUoIstruttore(lDocumentoXmlOutBean.getLivelliUOScrivaniaIstruttoreMain());
			lIstruttoreBean.setDesIstruttore(lDocumentoXmlOutBean.getDesScrivaniaIstruttoreMain());
			listaIstruttori.add(lIstruttoreBean);
		} else {
			listaIstruttori.add(new IstruttoreBean());
		}
		bean.setListaIstruttori(listaIstruttori);
		
		// Altri istruttori
		List<IstruttoreBean> listaAltriIstruttori = new ArrayList<IstruttoreBean>();
		if (lDocumentoXmlOutBean.getAltriIstruttori() != null && lDocumentoXmlOutBean.getAltriIstruttori().size() > 0) {
			for (ScrivaniaEstensoreBean lScrivaniaIstruttoreBean : lDocumentoXmlOutBean.getAltriIstruttori()) {
				IstruttoreBean lIstruttoreBean = new IstruttoreBean();
				lIstruttoreBean.setIstruttore(lScrivaniaIstruttoreBean.getIdSV());
				lIstruttoreBean.setIstruttoreFromLoadDett(lScrivaniaIstruttoreBean.getIdSV());
				lIstruttoreBean.setCodUoIstruttore(lScrivaniaIstruttoreBean.getCodUO());
				lIstruttoreBean.setDesIstruttore(lScrivaniaIstruttoreBean.getDescrizione());
				listaAltriIstruttori.add(lIstruttoreBean);
			}
		}
		bean.setListaAltriIstruttori(listaAltriIstruttori);
		
		// Assessore/consigliere di riferimento
		List<UtenteRifAttoConsiglioBean> listaUtenteRifAttoConsiglio = new ArrayList<UtenteRifAttoConsiglioBean>();
		if (lDocumentoXmlOutBean.getUtenteRifAttoConsiglio() != null && lDocumentoXmlOutBean.getUtenteRifAttoConsiglio().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getUtenteRifAttoConsiglio()) {
				UtenteRifAttoConsiglioBean lUtenteRifAttoConsiglioBean = new UtenteRifAttoConsiglioBean();
				lUtenteRifAttoConsiglioBean.setUtenteRifAttoConsiglio(lKeyValueBean.getKey());
				lUtenteRifAttoConsiglioBean.setUtenteRifAttoConsiglioFromLoadDett(lKeyValueBean.getKey());
				lUtenteRifAttoConsiglioBean.setDesUtenteRifAttoConsiglio(lKeyValueBean.getValue());
				listaUtenteRifAttoConsiglio.add(lUtenteRifAttoConsiglioBean);
			}
		}
		bean.setListaUtenteRifAttoConsiglio(listaUtenteRifAttoConsiglio);

		// Senza validazione PO
		bean.setFlgSenzaValidazionePO(lDocumentoXmlOutBean.getFlgSenzaValidazionePO() == Flag.SETTED);
		
		/* Dati scheda - Visti dir. superiori */
		
		bean.setFlgVistoRespUff(lDocumentoXmlOutBean.getFlgVistoRespUff() == Flag.SETTED);		
		bean.setFlgVistoDirSup1(lDocumentoXmlOutBean.getFlgVistoDirSup1() == Flag.SETTED);
		bean.setFlgVistoDirSup2(lDocumentoXmlOutBean.getFlgVistoDirSup2() == Flag.SETTED);
		
		bean.setIdScrivaniaVistoRespUff(lDocumentoXmlOutBean.getIdScrivaniaVistoRespUff());
		bean.setIdScrivaniaVistoDirSup1(lDocumentoXmlOutBean.getIdScrivaniaVistoDirSup1());
		bean.setIdScrivaniaVistoDirSup2(lDocumentoXmlOutBean.getIdScrivaniaVistoDirSup2());
		
		/* Dati scheda - Parere della/e circoscrizioni */
		
		List<SimpleKeyValueBean> listaParereCircoscrizioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getParereCircoscrizioni() != null && lDocumentoXmlOutBean.getParereCircoscrizioni().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getParereCircoscrizioni()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaParereCircoscrizioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaParereCircoscrizioni(listaParereCircoscrizioni);
		
		/* Dati scheda - Parere della/e commissioni */
		
		bean.setFlgCommissioni(lDocumentoXmlOutBean.getFlgCommissioni() == Flag.SETTED);		
		
		List<SimpleKeyValueBean> listaParereCommissioni = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getParereCommissioni() != null && lDocumentoXmlOutBean.getParereCommissioni().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getParereCommissioni()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaParereCommissioni.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaParereCommissioni(listaParereCommissioni);
		
		/* Dati scheda - Oggetto */
		
		bean.setDesOgg(lProtocollazioneBean.getOggetto());
		bean.setOggetto(lProtocollazioneBean.getOggetto());
		// Se non ho l'oggetto html metto l'oggetto normale
		bean.setOggettoHtml(StringUtils.isNotBlank(lDocumentoXmlOutBean.getOggettoHtml()) ? lDocumentoXmlOutBean.getOggettoHtml() : lProtocollazioneBean.getOggetto());
		bean.setOggettoHtmlFile(lDocumentoXmlOutBean.getOggettoHtmlFile());
		
		/* Dati scheda - Atto di riferimento */
		
		/*
//		if(lDocumentoXmlOutBean.getDocCollegato() != null) {
//			bean.setFlgAttoRifASistema(null);
//			bean.setIdUdAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getIdUd());
//			bean.setCategoriaRegAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getTipo() != null ? lDocumentoXmlOutBean.getDocCollegato().getTipo().toString() : null);
//			bean.setSiglaAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getRegistro());
//			bean.setNumeroAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getNro());
//			bean.setAnnoAttoDeterminaAContrarre(lDocumentoXmlOutBean.getDocCollegato().getAnno());
//		}		
		if(lDocumentoXmlOutBean.getDocumentiCollegati() != null  && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			DocumentoCollegato lDocumentoCollegato = lDocumentoXmlOutBean.getDocumentiCollegati().get(0);
			bean.setFlgAttoRifASistema(lDocumentoCollegato.getFlgPresenteASistema());
			bean.setIdUdAttoDeterminaAContrarre(lDocumentoCollegato.getIdUd());
			bean.setCategoriaRegAttoDeterminaAContrarre(lDocumentoCollegato.getTipo() != null ? lDocumentoCollegato.getTipo().toString() : null);
			bean.setSiglaAttoDeterminaAContrarre(lDocumentoCollegato.getSiglaRegistro());
			bean.setNumeroAttoDeterminaAContrarre(lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero().intValue()) : null);
			bean.setAnnoAttoDeterminaAContrarre(lDocumentoCollegato.getAnno() != null ? String.valueOf(lDocumentoCollegato.getAnno().intValue()) : null);
		} 
		*/
		
		List<AttoRiferimentoBean> listaAttiRiferimento = new ArrayList<AttoRiferimentoBean>();
		if(lDocumentoXmlOutBean.getDocumentiCollegati() != null  && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			for(int i = 0; i < lDocumentoXmlOutBean.getDocumentiCollegati().size(); i++) {
				DocumentoCollegato lDocumentoCollegato = lDocumentoXmlOutBean.getDocumentiCollegati().get(i);
				AttoRiferimentoBean lAttoRiferimentoBean = new AttoRiferimentoBean();
				lAttoRiferimentoBean.setFlgPresenteASistema(lDocumentoCollegato.getFlgPresenteASistema());
				lAttoRiferimentoBean.setIdUd(lDocumentoCollegato.getIdUd());
				lAttoRiferimentoBean.setCategoriaReg(lDocumentoCollegato.getTipo() != null ? lDocumentoCollegato.getTipo().toString() : null);
				lAttoRiferimentoBean.setTipoAttoRif(lDocumentoCollegato.getIdTipoDoc());
				lAttoRiferimentoBean.setNomeTipoAttoRif(lDocumentoCollegato.getNomeTipoDoc());				
				lAttoRiferimentoBean.setSigla(lDocumentoCollegato.getSiglaRegistro());
				lAttoRiferimentoBean.setNumero(lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero().intValue()) : null);
				lAttoRiferimentoBean.setAnno(lDocumentoCollegato.getAnno() != null ? String.valueOf(lDocumentoCollegato.getAnno().intValue()) : null);
				listaAttiRiferimento.add(lAttoRiferimentoBean);
			}
		} 
		bean.setListaAttiRiferimento(listaAttiRiferimento);
		
		/* Dati scheda - Specificità del provvedimento */
		
		bean.setOggLiquidazione(lDocumentoXmlOutBean.getOggLiquidazione());
		bean.setDataScadenzaLiquidazione(lDocumentoXmlOutBean.getDataScadenzaLiquidazione());
		bean.setUrgenzaLiquidazione(lDocumentoXmlOutBean.getUrgenzaLiquidazione());
		bean.setFlgLiqXUffCassa(lDocumentoXmlOutBean.getFlgLiqXUffCassa() == Flag.SETTED);
		bean.setImportoAnticipoCassa(lDocumentoXmlOutBean.getImportoAnticipoCassa());
		bean.setDataDecorrenzaContratto(lDocumentoXmlOutBean.getDataDecorrenzaContratto());
		bean.setAnniDurataContratto(lDocumentoXmlOutBean.getAnniDurataContratto());
		
		bean.setFlgAffidamento(lDocumentoXmlOutBean.getFlgAffidamento() == Flag.SETTED);
		bean.setFlgDeterminaAContrarreTramiteProceduraGara(lDocumentoXmlOutBean.getFlgDetContrConGara() == Flag.SETTED);
		bean.setFlgDeterminaAggiudicaProceduraGara(lDocumentoXmlOutBean.getFlgDetAggiudicaGara() == Flag.SETTED);
		bean.setFlgDeterminaRimodulazioneSpesaGaraAggiudicata(lDocumentoXmlOutBean.getFlgDetRimodSpesaGaraAggiud() == Flag.SETTED);
		bean.setFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro(lDocumentoXmlOutBean.getFlgDetPersonale() == Flag.SETTED);
		bean.setFlgDeterminaRiaccertamento(lDocumentoXmlOutBean.getFlgDetRiaccert() == Flag.SETTED);
		bean.setFlgDeterminaAccertRadiaz(lDocumentoXmlOutBean.getFlgDetAccertRadiaz() == Flag.SETTED);
		bean.setFlgDeterminaVariazBil(lDocumentoXmlOutBean.getFlgDetVariazBil() == Flag.SETTED);
		bean.setFlgVantaggiEconomici(lDocumentoXmlOutBean.getFlgVantaggiEconomici() == Flag.SETTED);
		bean.setFlgDecretoReggio(lDocumentoXmlOutBean.getFlgDecretoReggio() == Flag.SETTED);
		bean.setFlgAvvocatura(lDocumentoXmlOutBean.getFlgAvvocatura() == Flag.SETTED);
		bean.setFlgDeterminaArchiviazione(lDocumentoXmlOutBean.getFlgDetArchiviazione() == Flag.SETTED);
		
		// il check "contributi" non è esclusivo come gli altri sopra
		bean.setFlgContributi(lDocumentoXmlOutBean.getFlgContributi() == Flag.SETTED);
		
		bean.setFlgSpesa(lDocumentoXmlOutBean.getFlgDetConSpesa());
		bean.setFlgDatiRilevantiGSA(lDocumentoXmlOutBean.getFlgDatiRilevantiGSA());
		List<SimpleKeyValueBean> listaUfficiCompetentiRag = new ArrayList<SimpleKeyValueBean>();
		if (lDocumentoXmlOutBean.getUfficiCompetentiRag() != null && lDocumentoXmlOutBean.getUfficiCompetentiRag().size() > 0) {
			for (KeyValueBean lKeyValueBean : lDocumentoXmlOutBean.getUfficiCompetentiRag()) {
				SimpleKeyValueBean lSimpleKeyValueBean = new SimpleKeyValueBean();
				lSimpleKeyValueBean.setKey(lKeyValueBean.getKey());
				lSimpleKeyValueBean.setValue(lKeyValueBean.getValue());
				listaUfficiCompetentiRag.add(lSimpleKeyValueBean);
			}
		}
		bean.setListaUfficiCompetentiRag(listaUfficiCompetentiRag);
		bean.setAnnoContabileCompetenza(lDocumentoXmlOutBean.getAnnoContabileCompetenza());	
		bean.setFlgCorteConti(lDocumentoXmlOutBean.getFlgCorteConti() == Flag.SETTED);
		bean.setFlgLiqContestualeImpegno(lDocumentoXmlOutBean.getFlgLiqContestualeImpegno() == Flag.SETTED);
		bean.setFlgLiqContestualeAltriAspettiRilCont(lDocumentoXmlOutBean.getFlgLiqContestualeAltriAspettiRilCont() == Flag.SETTED);
		bean.setFlgDetConLiquidazione(lDocumentoXmlOutBean.getFlgDetConLiquidazione() == Flag.SETTED);
		bean.setFlgCompQuadroFinRagDec(lDocumentoXmlOutBean.getFlgCompQuadroFinRagDec() == Flag.SETTED);		
		bean.setFlgNuoviImpAcc(lDocumentoXmlOutBean.getFlgNuoviImpAcc() == Flag.SETTED);
		bean.setFlgImpSuAnnoCorrente(lDocumentoXmlOutBean.getFlgImpSuAnnoCorrente() == Flag.SETTED);
		bean.setFlgInsMovARagioneria(lDocumentoXmlOutBean.getFlgInsMovARagioneria() == Flag.SETTED);
		bean.setFlgPresaVisioneContabilita(lDocumentoXmlOutBean.getFlgRichPresaVisContabilita() == Flag.SETTED);
		bean.setFlgSpesaCorrente(lDocumentoXmlOutBean.getFlgDetConSpesaCorrente() == Flag.SETTED);
		bean.setFlgImpegniCorrenteGiaValidati(lDocumentoXmlOutBean.getFlgDetConImpCorrValid() == Flag.SETTED);
		bean.setFlgSpesaContoCapitale(lDocumentoXmlOutBean.getFlgDetConSpesaContoCap() == Flag.SETTED);
		bean.setFlgImpegniContoCapitaleGiaRilasciati(lDocumentoXmlOutBean.getFlgDetConImpCCapRil() == Flag.SETTED);
		bean.setFlgSoloSubImpSubCrono(lDocumentoXmlOutBean.getFlgSoloSubImpSubCrono() == Flag.SETTED);
		bean.setTipoAttoInDeliberaPEG(lDocumentoXmlOutBean.getTipoAttoInDelPeg());
		bean.setTipoAffidamento(lDocumentoXmlOutBean.getTipoAffidamento());
		bean.setNormRifAffidamento(lDocumentoXmlOutBean.getNormRifAffidamento());
		bean.setRespAffidamento(lDocumentoXmlOutBean.getRespAffidamento());
		bean.setMateriaTipoAtto(lDocumentoXmlOutBean.getMateriaNaturaAtto());
		bean.setDesMateriaTipoAtto(lDocumentoXmlOutBean.getDesMateriaNaturaAtto());
		bean.setTipoFinanziamentoPNRR(lDocumentoXmlOutBean.getTipoFinanziamentoPNRR());
		bean.setFlgSottotipoAtto(lDocumentoXmlOutBean.getFlgSottotipoAtto());
		bean.setFlgTipoIter(lDocumentoXmlOutBean.getFlgTipoIter());
		bean.setFlgFondiEuropeiPON(lDocumentoXmlOutBean.getFlgFondiEuropeiPON() == Flag.SETTED);
		bean.setFlgFondiPNRRRadio(lDocumentoXmlOutBean.getFlgFondiPNRRRadio());		
		bean.setFlgFondiPNRR(lDocumentoXmlOutBean.getFlgFondiPNRR() == Flag.SETTED);
		bean.setFlgFondiPNRRRigen(lDocumentoXmlOutBean.getFlgFondiPNRRRigen() == Flag.SETTED);
		bean.setFlgFondiPRU(lDocumentoXmlOutBean.getFlgFondiPRU() == Flag.SETTED);
		bean.setFlgVistoUtenze(lDocumentoXmlOutBean.getFlgVistoUtenze() == Flag.SETTED);
		bean.setFlgVistoCapitolatiSottoSoglia(lDocumentoXmlOutBean.getFlgVistoCapitolatiSottoSoglia() == Flag.SETTED);
		bean.setFlgVistoCapitolatiSopraSoglia(lDocumentoXmlOutBean.getFlgVistoCapitolatiSopraSoglia() == Flag.SETTED);
		bean.setFlgVistoPar117_2013(lDocumentoXmlOutBean.getFlgVistoPar117_2013() == Flag.SETTED);		
		bean.setFlgNotificaDaMessi(lDocumentoXmlOutBean.getFlgNotificaDaMessi() == Flag.SETTED);
		bean.setFlgSenzaImpegniCont(lDocumentoXmlOutBean.getFlgSenzaImpegniCont() == Flag.SETTED);
		bean.setFlgMEPACONSIP(lDocumentoXmlOutBean.getFlgMEPACONSIP());	
		bean.setFlgServeDUVRI(lDocumentoXmlOutBean.getFlgServeDUVRI());	
		bean.setImponibileComplessivo(lDocumentoXmlOutBean.getImponibileComplessivo());		
		bean.setImportoOneriSicurezza(lDocumentoXmlOutBean.getImportoOneriSicurezza());		
		bean.setFlgLLPP(lDocumentoXmlOutBean.getFlgLLPP());	
		bean.setAnnoProgettoLLPP(lDocumentoXmlOutBean.getAnnoProgettoLLPP());	
		bean.setNumProgettoLLPP(lDocumentoXmlOutBean.getNumProgettoLLPP());	
		bean.setFlgBeniServizi(lDocumentoXmlOutBean.getFlgBeniServizi());
		bean.setAnnoProgettoBeniServizi(lDocumentoXmlOutBean.getAnnoProgettoBeniServizi());	
		bean.setNumProgettoBeniServizi(lDocumentoXmlOutBean.getNumProgettoBeniServizi());
		bean.setFlgProgrammazioneAcquisti(lDocumentoXmlOutBean.getFlgProgrammazioneAcquisti());
		bean.setFlgPrivacy(lDocumentoXmlOutBean.getFlgPrivacy());	
		bean.setFlgDatiProtettiTipo1(lDocumentoXmlOutBean.getFlgDatiProtettiTipo1() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo2(lDocumentoXmlOutBean.getFlgDatiProtettiTipo2() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo3(lDocumentoXmlOutBean.getFlgDatiProtettiTipo3() == Flag.SETTED);
		bean.setFlgDatiProtettiTipo4(lDocumentoXmlOutBean.getFlgDatiProtettiTipo4() == Flag.SETTED);
		bean.setNumGara(lDocumentoXmlOutBean.getNumGara());
		bean.setFlgControlloLegittimita(lDocumentoXmlOutBean.getFlgControlloLegittimita());	
		bean.setMotivazioniEsclControlloLegittimita(lDocumentoXmlOutBean.getMotivazioniEsclControlloLegittimita());
		
		/* Dati scheda - Dest. vantaggio */		
		
		List<DestVantaggioBean> listaDestVantaggio = new ArrayList<DestVantaggioBean>();
		if (lDocumentoXmlOutBean.getDestinatariVantaggio() != null && lDocumentoXmlOutBean.getDestinatariVantaggio().size() > 0) {
			for (DestinatarioVantaggioBean lDestinatarioVantaggioBean : lDocumentoXmlOutBean.getDestinatariVantaggio()) {
				DestVantaggioBean lDestVantaggioBean = new DestVantaggioBean();
				lDestVantaggioBean.setTipoPersona(lDestinatarioVantaggioBean.getTipoPersona());
				lDestVantaggioBean.setCognome(lDestinatarioVantaggioBean.getCognome());
				lDestVantaggioBean.setNome(lDestinatarioVantaggioBean.getNome());
				lDestVantaggioBean.setRagioneSociale(lDestinatarioVantaggioBean.getRagioneSociale());
				lDestVantaggioBean.setCodFiscalePIVA(lDestinatarioVantaggioBean.getCodFiscalePIVA());
				lDestVantaggioBean.setImporto(lDestinatarioVantaggioBean.getImporto());
				listaDestVantaggio.add(lDestVantaggioBean);
			}
		} else {
			listaDestVantaggio.add(new DestVantaggioBean());
		}
		bean.setListaDestVantaggio(listaDestVantaggio);
				
		/* Dati scheda - Ruoli e visti per dati contabili */		
		
		// Responsabili PEG
		bean.setFlgAdottanteUnicoRespPEG(lDocumentoXmlOutBean.getFlgAdottanteUnicoRespSpesa() == Flag.SETTED);	
		
		// Responsabili PEG
		bean.setFlgDirRespRegTecnicaUnicoRespSpesa(lDocumentoXmlOutBean.getFlgDirRespRegTecnicaUnicoRespSpesa() == Flag.SETTED);	
		
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
		
		bean.setOpzAssCompSpesa(lDocumentoXmlOutBean.getOpzAssCompSpesa());
			
		bean.setFlgRichVerificaDiBilancioCorrente(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioCorrente() == Flag.SETTED);		
		bean.setFlgRichVerificaDiBilancioContoCapitale(lDocumentoXmlOutBean.getFlgRichVerificaDiBilancioContoCapitale() == Flag.SETTED);				
		bean.setFlgRichVerificaDiContabilita(lDocumentoXmlOutBean.getFlgRichVerificaDiContabilita() == Flag.SETTED);		
		bean.setFlgConVerificaContabilita(lDocumentoXmlOutBean.getFlgRichVerificaContabilita() == Flag.SETTED);
		bean.setFlgRichiediParereRevisoriContabili(lDocumentoXmlOutBean.getFlgRichParereRevContabili() == Flag.SETTED);		
			
		/* Dati scheda - CIG */
		
		bean.setFlgOpCommerciale(lDocumentoXmlOutBean.getFlgOpCommerciale());
		bean.setFlgEscludiCIG(lDocumentoXmlOutBean.getFlgEscludiCIG() == Flag.SETTED);
		bean.setFlgCIGDaAcquisire(lDocumentoXmlOutBean.getFlgCIGDaAcquisire() == Flag.SETTED);
		bean.setMotivoEsclusioneCIG(lDocumentoXmlOutBean.getMotivoEsclusioneCIG());
		bean.setCodiceCIGPadre(lDocumentoXmlOutBean.getCodiceCIGPadre());
		
		List<CIGCUPBean> listaCIG = new ArrayList<CIGCUPBean>();
		if (lDocumentoXmlOutBean.getListaCIG() != null && !lDocumentoXmlOutBean.getListaCIG().isEmpty()) {
			listaCIG = lDocumentoXmlOutBean.getListaCIG();
		}
		bean.setListaCIG(listaCIG);
		
		/* Dati scheda - CUI */
		
		List<CUIBean> listaCUI = new ArrayList<CUIBean>();
		if (lDocumentoXmlOutBean.getListaCUI() != null && !lDocumentoXmlOutBean.getListaCUI().isEmpty()) {
			listaCUI = lDocumentoXmlOutBean.getListaCUI();
		}
		bean.setListaCUI(listaCUI);
		
		/* Dati scheda - Categoria di rischio */
		
		bean.setCategoriaRischio(lDocumentoXmlOutBean.getCategoriaRischio());				
		
		/* Dati scheda - sotto-fascicolo RdA */
		
		bean.setSubfolderRda(lDocumentoXmlOutBean.getSubfolderRda());				
				
		/* Classificazione e fascicolazione */
		
		bean.setListaClassFasc(lProtocollazioneBean.getListaClassFasc() != null ? lProtocollazioneBean.getListaClassFasc() : new ArrayList<ClassificazioneFascicoloBean>());
		
		
		/* Dati dispositivo - Upload pdf atto */
		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFilePdfAtto())) {
			bean.setFilePdfAtto(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFilePdfAtto()));
		}
		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFilePdfAttoOmissis())) {
			bean.setFilePdfAttoOmissis(AttributiDinamiciDatasource.buildDocumentBean(lDocumentoXmlOutBean.getFilePdfAttoOmissis()));
		}
		
		/* Dati dispositivo - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = new ArrayList<SimpleValueBean>();
		if (lDocumentoXmlOutBean.getRiferimentiNormativi() != null && lDocumentoXmlOutBean.getRiferimentiNormativi().size() > 0) {
			for (ValueBean lValueBean : lDocumentoXmlOutBean.getRiferimentiNormativi()) {
				SimpleValueBean lSimpleValueBean = new SimpleValueBean();
				lSimpleValueBean.setValue(lValueBean.getValue());
				listaRiferimentiNormativi.add(lSimpleValueBean);
			}
		}
		bean.setListaRiferimentiNormativi(listaRiferimentiNormativi);
		
		/* Dati dispositivo - Atti presupposti */
		
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
		bean.setAttiPresuppostiFile(lDocumentoXmlOutBean.getAttiPresuppostiFile());
		
		/* Dati dispositivo - Motivazioni */
		
		bean.setMotivazioni(lDocumentoXmlOutBean.getMotivazioniAtto());
		bean.setMotivazioniFile(lDocumentoXmlOutBean.getMotivazioniAttoFile());
	
		/* Dati dispositivo - Premessa */
		
		bean.setPremessa(lDocumentoXmlOutBean.getPremessaAtto());
		bean.setPremessaFile(lDocumentoXmlOutBean.getPremessaAttoFile());
		
		/* Dati dispositivo - Riferimenti normativi */
		
		bean.setRifNormativiLiberi(lDocumentoXmlOutBean.getRifNormativiLiberi());
		bean.setRifNormativiLiberiFile(lDocumentoXmlOutBean.getRifNormativiLiberiFile());
		
		/* Dati dispositivo - Dispositivo */
		
		bean.setDispositivo(lDocumentoXmlOutBean.getDispositivoAtto());
		bean.setDispositivoFile(lDocumentoXmlOutBean.getDispositivoAttoFile());
		bean.setLoghiAggiuntiviDispositivo(lDocumentoXmlOutBean.getLoghiDispositivoAtto());		
		
		/* Dati dispositivo 2 - Premessa 2 */
		
		bean.setPremessa2(lDocumentoXmlOutBean.getPremessaAtto2());
		bean.setPremessa2File(lDocumentoXmlOutBean.getPremessaAtto2File());
		
		/* Dati dispositivo 2 - Dispositivo 2 */
		
		bean.setDispositivo2(lDocumentoXmlOutBean.getDispositivoAtto2());
		bean.setDispositivo2File(lDocumentoXmlOutBean.getDispositivoAtto2File());
		
		/* Allegati */
		
		bean.setFlgPubblicaAllegatiSeparati(lDocumentoXmlOutBean.getFlgPubblicaAllegatiSeparati() == Flag.SETTED);
		bean.setListaAllegati(lProtocollazioneBean.getListaAllegati() != null ? lProtocollazioneBean.getListaAllegati() : new ArrayList<AllegatoProtocolloBean>());
		
		/* Documenti fascicolo */
		
		bean.setListaDocFasc(lProtocollazioneBean.getListaDocProcFolder() != null ? lProtocollazioneBean.getListaDocProcFolder() : new ArrayList<AllegatoProtocolloBean>());
		
		/* Pubblicazione/notifiche - Opzioni testo */	
		bean.setFlgEscludiPremesseFisseDaTestoAtto(lDocumentoXmlOutBean.getFlgEscludiPremesseFisseDaTestoAtto() == Flag.SETTED);
		bean.setFlgEscludiOggettoDaTestoAtto(lDocumentoXmlOutBean.getFlgEscludiOggettoDaTestoAtto() == Flag.SETTED);
		
		/* Pubblicazione/notifiche - Opzioni avanzate testo */	
		bean.setScrittaTraPremessaEDispositivoAtto(lDocumentoXmlOutBean.getScrittaTraPremessaEDispositivoAtto());
		bean.setNumRighePreScrittaTraPremessaEDispositivoAtto(lDocumentoXmlOutBean.getNumRighePreScrittaTraPremessaEDispositivoAtto());
		bean.setNumRighePostScrittaTraPremessaEDispositivoAtto(lDocumentoXmlOutBean.getNumRighePostScrittaTraPremessaEDispositivoAtto());
		
		/* Pubblicazione/notifiche - Opzioni iter */	
		bean.setFlgAcquisitiVistiTornaVerificaUoProp(lDocumentoXmlOutBean.getFlgAcquisitiVistiTornaVerificaUoProp() == Flag.SETTED);
		
		/* Pubblicazione/notifiche - Opzioni visualizzazione */	
		bean.setFlgVisibilitaPubblPostAdozione(lDocumentoXmlOutBean.getFlgVisibilitaPubblPostAdozione() == Flag.SETTED);
		
		/* Pubblicazione/notifiche - Pubblicazione all'Albo */
		
		bean.setFlgPubblAlbo(lDocumentoXmlOutBean.getFlgPubblAlbo());
		bean.setTipoDecorrenzaPubblAlbo(lDocumentoXmlOutBean.getTipoDecorrenzaPubblAlbo());
		bean.setDataPubblAlboDal(lDocumentoXmlOutBean.getDataPubblAlboDal());
		bean.setNumGiorniPubblAlbo(lDocumentoXmlOutBean.getNumGiorniPubblAlbo());
		bean.setDataPubblAlboAl(lDocumentoXmlOutBean.getDataPubblAlboAl());		
		bean.setFlgUrgentePubblAlbo(lDocumentoXmlOutBean.getFlgUrgentePubblAlbo() == Flag.SETTED);
		bean.setDataPubblAlboEntro(lDocumentoXmlOutBean.getDataPubblAlboEntro());
		bean.setDataRipubblAlboDal(lDocumentoXmlOutBean.getDataRipubblAlboDal());
		bean.setNumGiorniRipubblAlbo(lDocumentoXmlOutBean.getNumGiorniRipubblAlbo());
		bean.setDataRipubblAlboAl(lDocumentoXmlOutBean.getDataRipubblAlboAl());
				
		/* Pubblicazione/notifiche - Pubblicazione in Amm. Trasparente */
		
		bean.setFlgPubblAmmTrasp(lDocumentoXmlOutBean.getFlgPubblAmmTrasp());
		bean.setAnnoTerminePubblAmmTrasp(lDocumentoXmlOutBean.getAnnoTerminePubblAmmTrasp());
		bean.setSezionePubblAmmTrasp(lDocumentoXmlOutBean.getSezionePubblAmmTrasp());
		bean.setSottoSezionePubblAmmTrasp(lDocumentoXmlOutBean.getSottoSezionePubblAmmTrasp());
		
		/* Pubblicazione/notifiche - Pubblicazione al B.U. */
		
		bean.setFlgPubblBUR(lDocumentoXmlOutBean.getFlgPubblBUR());
		bean.setAnnoTerminePubblBUR(lDocumentoXmlOutBean.getAnnoTerminePubblBUR());
		bean.setTipoDecorrenzaPubblBUR(lDocumentoXmlOutBean.getTipoDecorrenzaPubblBUR());
		bean.setDataPubblBURDal(lDocumentoXmlOutBean.getDataPubblBURDal());
		bean.setFlgUrgentePubblBUR(lDocumentoXmlOutBean.getFlgUrgentePubblBUR() == Flag.SETTED);
		bean.setDataPubblBUREntro(lDocumentoXmlOutBean.getDataPubblBUREntro());
		
		/* Pubblicazione/notifiche - Pubblicazione sul notiziario */
		
		bean.setFlgPubblNotiziario(lDocumentoXmlOutBean.getFlgPubblNotiziario());
			
		/*IdUO albo pretorio reggio calabria*/
		bean.setIdUoAlboReggio(lDocumentoXmlOutBean.getIdUoAlboReggio());
		
		/*Id tipo documento albo pretorio */
		bean.setIdTipoDocAlbo(lDocumentoXmlOutBean.getIdTipoDocAlbo());
		
		/* Pubblicazione/notifiche - Esecutività */
		
		bean.setDataEsecutivitaDal(lDocumentoXmlOutBean.getDtEsecutivita());
		bean.setFlgImmediatamenteEseguibile(lDocumentoXmlOutBean.getFlgImmediatamenteEseg() == Flag.SETTED);
		bean.setMotiviImmediatamenteEseguibile(lDocumentoXmlOutBean.getMotiviImmediatamenteEseguibile());
		bean.setMotiviImmediatamenteEseguibileFile(lDocumentoXmlOutBean.getMotiviImmediatamenteEseguibileFile());
		
		/* Pubblicazione/notifiche - Notifiche */
		
		if (lDocumentoXmlOutBean.getListaDestNotificaAtto() != null && lDocumentoXmlOutBean.getListaDestNotificaAtto().size() > 0) {
			String listaDestNotificaAtto = "";
			for (DestNotificaAttoXmlBean lDestNotificaAttoXmlBean : lDocumentoXmlOutBean.getListaDestNotificaAtto()) {
				if(StringUtils.isBlank(listaDestNotificaAtto)) {
					listaDestNotificaAtto += lDestNotificaAttoXmlBean.getIndirizzoEmail();
				} else {
					listaDestNotificaAtto += "; " + lDestNotificaAttoXmlBean.getIndirizzoEmail();
				}	
			}
			bean.setListaDestNotificaAtto(listaDestNotificaAtto);
		}
		
		/* Pubblicazione/notifiche - Notifica a mezzo messi */
		
		bean.setFlgMessiNotificatori(lDocumentoXmlOutBean.getFlgMessiNotificatori() == Flag.SETTED);		
		bean.setListaDestinatariNotificaMessi(lDocumentoXmlOutBean.getListaDestinatariNotificaMessi() != null ? lDocumentoXmlOutBean.getListaDestinatariNotificaMessi() : new ArrayList<DestinatariNotificaMessiXmlBean>());		
			
		/* Pubblicazione/notifiche - Notifica PEC */
		
		bean.setFlgNotificaPEC(lDocumentoXmlOutBean.getFlgNotificaPEC() == Flag.SETTED);		
		bean.setListaDestinatariNotificaPEC(lDocumentoXmlOutBean.getListaDestinatariNotificaPEC() != null ? lDocumentoXmlOutBean.getListaDestinatariNotificaPEC() : new ArrayList<DestinatariNotificaPECXmlBean>());
		
		/* Pubblicazione/notifiche - Visibilità pubblica post adozione */
		
		bean.setFlgVisibPubblicaPostAdozione(lDocumentoXmlOutBean.getFlgVisibPubblicaPostAdozione() == Flag.SETTED);		
		
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
		bean.setNoteCorrenteFile(lDocumentoXmlOutBean.getNoteCorrenteFile());
		
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
		bean.setNoteContoCapitaleFile(lDocumentoXmlOutBean.getNoteContoCapitaleFile());	
			
		/* Dati spesa personale */
		/*
		bean.setListaDatiSpesaAnnualiXDetPersonale(lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() != null ? lDocumentoXmlOutBean.getListaDatiSpesaAnnualiXDetPersonale() : new ArrayList<DatiSpesaAnnualiXDetPersonaleXmlBean>());
		bean.setCapitoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getCapitoloDatiSpesaAnnuaXDetPers());
		bean.setArticoloDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getArticoloDatiSpesaAnnuaXDetPers());
		bean.setNumeroDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getNumeroDatiSpesaAnnuaXDetPers());	
		bean.setImportoDatiSpesaAnnuaXDetPers(lDocumentoXmlOutBean.getImportoDatiSpesaAnnuaXDetPers());
		*/
		
		if(isAttivoSIB(bean)) {
			
			populateListaVociPEGNoVerifDisp(bean);
			
			if(isAttivaRequestMovimentiDaAMC(bean)) {
				if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBCorrente()) {
					populateListaDatiContabiliSIBCorrente(bean);
				}
				if(!bean.getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale()) {
					populateListaDatiContabiliSIBContoCapitale(bean);
				}
			}
		}
		
		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI
		//TODEL togliere i dati di test e leggere da lDocumentoXmlOutBean
		List<EmendamentoBean> listaEmendamenti = new ArrayList<EmendamentoBean>();
		for (int i = 0; i < 5; i++) {
			EmendamentoBean emendamento = new EmendamentoBean();
			emendamento.setEstremiEmendamento("Emendamento " + i);
			emendamento.setTestoEmendamento("Questo è il testo dell'emendamento " + i);
			listaEmendamenti.add(emendamento);
		}
		
		for (int i = 5; i < 8; i++) {
			EmendamentoBean emendamento = new EmendamentoBean();
			emendamento.setEstremiEmendamento("Emendamento " + i);
			emendamento.setTestoEmendamento("Questo è il testo dell'emendamento " + i);
			emendamento.setNomeFileEmendamento(bean.getNomeFilePrimario());
			emendamento.setUriFileEmendamento(bean.getUriFilePrimario());
			emendamento.setInfoFileEmendamento(bean.getInfoFilePrimario());
			listaEmendamenti.add(emendamento);
		}
		
		bean.setListaEmendamenti(listaEmendamenti);
		****** [EMEND] ELIMINA RIGA PER EMENDAMENTI */
		
		
		/* Movimenti contabili - Contabilia */
		
		bean.setListaMovimentiContabilia(new ArrayList<MovimentiContabiliaXmlBean>());
		if(lDocumentoXmlOutBean.getListaMovimentiContabilia() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaMovimentiContabilia().size(); i++) {
				MovimentiContabiliaXmlBean lMovimentiContabiliaXmlBean = new MovimentiContabiliaXmlBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMovimentiContabiliaXmlBean, lDocumentoXmlOutBean.getListaMovimentiContabilia().get(i)); 
				bean.getListaMovimentiContabilia().add(lMovimentiContabiliaXmlBean);
			}
		}
		
		bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
		bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
		bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());	
		
		if(isAttivoContabilia(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaMovimentiContabilia(bean);
		}
		
		// DA UTILIZZARE NEL CASO SI VOGLIANO RECUPERARE I MOVIMENTI DA CONTABILIA PER RISALVARLI IN BOZZA CON LA DETERMINA, QUANDO L'ITER E' GIA' CONCLUSO
		boolean isRecuperoMovimentiContabilia = getExtraparams().get("isRecuperoMovimentiContabilia") != null && "true".equalsIgnoreCase(getExtraparams().get("isRecuperoMovimentiContabilia"));		
		if(isRecuperoMovimentiContabilia && isAttivoContabilia(bean) /*&& (bean.getListaMovimentiContabilia() == null || bean.getListaMovimentiContabilia().size() == 0)*/) {
			populateListaMovimentiContabilia(bean);
			aggiornaMovimentiContabilia(bean);
		}
		
		/* Movimenti contabili - SICRA */
		
		bean.setListaInvioMovimentiContabiliSICRA(new ArrayList<MovimentiContabiliSICRABean>());
		if(lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA().size(); i++) {
				MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
				lMovimentiContabiliSICRABean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMovimentiContabiliSICRABean, lDocumentoXmlOutBean.getListaMovimentiContabiliSICRA().get(i)); 
				bean.getListaInvioMovimentiContabiliSICRA().add(lMovimentiContabiliSICRABean);
			}
		}

		// DECOMMENTARE (IN DEBUG) NEL CASO SI VOGLIA CANCELLARE UN MOVIMENTO SU STILO (CHE E' GIA' STATO CANCELLATO SU SICRA)
//		if(isAttivoSICRA(bean)) {
//			cancellaMovimentoSICRA(bean, numImpAcc);
//		}
		
		/* Dati GSA */
		
		bean.setListaMovimentiGSA(new ArrayList<MovimentiGSAXmlBean>());
		if(lDocumentoXmlOutBean.getListaMovimentiGSA() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaMovimentiGSA().size(); i++) {
				MovimentiGSAXmlBean lMovimentiGSAXmlBean = new MovimentiGSAXmlBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lMovimentiGSAXmlBean, lDocumentoXmlOutBean.getListaMovimentiGSA().get(i)); 
				bean.getListaMovimentiGSA().add(lMovimentiGSAXmlBean);
			}
		}
		
		/* Aggregato/smistamento ACTA */
		
		bean.setCodAOOXSelNodoACTA(lDocumentoXmlOutBean.getCodAOOXSelNodoACTA()); // bean.setCodAOOXSelNodoACTA("A19000");	
		bean.setCodStrutturaXSelNodoACTA(lDocumentoXmlOutBean.getCodStrutturaXSelNodoACTA()); // bean.setCodStrutturaXSelNodoACTA("A1906A");
				
		bean.setFlgAggregatoACTA(lDocumentoXmlOutBean.getFlgAggregatoACTA() == Flag.SETTED);
		bean.setFlgSmistamentoACTA(lDocumentoXmlOutBean.getFlgSmistamentoACTA() == Flag.SETTED);
		bean.setFlgIndiceClassificazioneACTA(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA() != null && _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA.equalsIgnoreCase(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA()));
		bean.setFlgFascicoloACTA(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA() != null && _OPZIONE_AGGREGATO_ACTA.equalsIgnoreCase(lDocumentoXmlOutBean.getOpzioneIndiceClassificazioneFascicoloACTA()));		
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getCodIndiceClassificazioneACTA())) {
			bean.setCodIndiceClassificazioneACTA(lDocumentoXmlOutBean.getCodIndiceClassificazioneACTA());
			bean.setFlgPresenzaClassificazioneACTA(true);
		} else {
			bean.setCodIndiceClassificazioneACTA(null);
			bean.setFlgPresenzaClassificazioneACTA(false);
		}
		bean.setCodVoceTitolarioACTA(lDocumentoXmlOutBean.getCodVoceTitolarioACTA());
		bean.setCodFascicoloACTA(lDocumentoXmlOutBean.getCodFascicoloACTA());
		bean.setSuffissoCodFascicoloACTA(lDocumentoXmlOutBean.getSuffissoCodFascicoloACTA());
		bean.setCodSottofascicoloACTA(lDocumentoXmlOutBean.getCodSottofascicoloACTA());		
		bean.setIdFascicoloACTA(lDocumentoXmlOutBean.getIdFascicoloACTA());
		bean.setIdNodoSmistamentoACTA(lDocumentoXmlOutBean.getIdNodoSmistamentoACTA());
		bean.setDesNodoSmistamentoACTA(lDocumentoXmlOutBean.getDesNodoSmistamentoACTA());	
		
		/* Opere ADSP */
		
		bean.setListaOpereADSP(lProtocollazioneBean.getListaPerizie() != null ? lProtocollazioneBean.getListaPerizie() : new ArrayList<PeriziaBean>());
		
		/* Dati contabili ADSP */
		
		bean.setListaDatiContabiliADSP(lDocumentoXmlOutBean.getListaDatiContabiliADSP() != null ? lDocumentoXmlOutBean.getListaDatiContabiliADSP() : new ArrayList<DatiContabiliADSPXmlBean>());		
		for(DatiContabiliADSPXmlBean datiContabili: bean.getListaDatiContabiliADSP()) {
			if(datiContabili.getStatoSistemaContabile().equalsIgnoreCase("allineato")) {
				datiContabili.setUltimoImportoAllineato(datiContabili.getImporto());
				datiContabili.setUltimoKeyCapitoloAllineato(datiContabili.getKeyCapitolo());
			}
		}
		
		bean.setFlgSavedAttoSuSistemaContabile(lDocumentoXmlOutBean.getFlgSavedAttoSuSistemaContabile() != null && lDocumentoXmlOutBean.getFlgSavedAttoSuSistemaContabile() == Flag.SETTED ? true : false);
		
		if(bean.getFlgSavedAttoSuSistemaContabile()!= null && bean.getFlgSavedAttoSuSistemaContabile()) {
			getContabilitaADSPDataSource().verificaStatoAttoContabilita(bean);
		}
		
		/* Dati contabili AVB */
		bean.setListaImpegniAVB(new ArrayList<DatiContabiliAVBBean>());
		if(lDocumentoXmlOutBean.getListaImpegniAVB() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaImpegniAVB().size(); i++) {
				DatiContabiliAVBBean lDatiContabiliAVBBean = new DatiContabiliAVBBean();
				lDatiContabiliAVBBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliAVBBean, lDocumentoXmlOutBean.getListaImpegniAVB().get(i)); 
				bean.getListaImpegniAVB().add(lDatiContabiliAVBBean);
			}
		}
		bean.setListaAccertamentiAVB(new ArrayList<DatiContabiliAVBBean>());
		if(lDocumentoXmlOutBean.getListaAccertamentiAVB() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaAccertamentiAVB().size(); i++) {
				DatiContabiliAVBBean lDatiContabiliAVBBean = new DatiContabiliAVBBean();
				lDatiContabiliAVBBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliAVBBean, lDocumentoXmlOutBean.getListaAccertamentiAVB().get(i)); 
				bean.getListaAccertamentiAVB().add(lDatiContabiliAVBBean);
			}
		}
		bean.setListaLiquidazioniAVB(new ArrayList<DatiContabiliAVBBean>());
		if(lDocumentoXmlOutBean.getListaLiquidazioniAVB() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaLiquidazioniAVB().size(); i++) {
				DatiContabiliAVBBean lDatiContabiliAVBBean = new DatiContabiliAVBBean();
				lDatiContabiliAVBBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliAVBBean, lDocumentoXmlOutBean.getListaLiquidazioniAVB().get(i)); 
				bean.getListaLiquidazioniAVB().add(lDatiContabiliAVBBean);
			}
		}
		
		
		/* Dati contabili ATERSIR*/
		bean.setListaDatiContabiliATERSIR(new ArrayList<DatiContabiliATERSIRBean>());
		if(lDocumentoXmlOutBean.getListaDatiContabiliATERSIR() != null) {			
			for(int i = 0; i < lDocumentoXmlOutBean.getListaDatiContabiliATERSIR().size(); i++) {
				DatiContabiliATERSIRBean lDatiContabiliATERSIRBean = new DatiContabiliATERSIRBean();
				lDatiContabiliATERSIRBean.setId(i + "");				
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lDatiContabiliATERSIRBean, lDocumentoXmlOutBean.getListaDatiContabiliATERSIR().get(i)); 
				bean.getListaDatiContabiliATERSIR().add(lDatiContabiliATERSIRBean);
			}
		}
		
		
		/* Trasparenza AVB */
		
		bean.setFlgErogVantaggiEconomici(lDocumentoXmlOutBean.getFlgErogVantaggiEconomici());
		bean.setTipoTrasparenzaVantEcon(lDocumentoXmlOutBean.getTipoTrasparenzaVantEcon());
		bean.setFlgBeneficiariObbligXTipoTraspVantEcon(lDocumentoXmlOutBean.getFlgBeneficiariObbligXTipoTraspVantEcon());
		bean.setSottotipoTrasparenzaVantEcon(lDocumentoXmlOutBean.getSottotipoTrasparenzaVantEcon());
		bean.setFlgBeneficiariObbligXSottotipoTraspVantEcon(lDocumentoXmlOutBean.getFlgBeneficiariObbligXSottotipoTraspVantEcon());		
		bean.setFlgInterventi(lDocumentoXmlOutBean.getFlgInterventi());
		bean.setTipoInterventi(lDocumentoXmlOutBean.getTipoInterventi());
		bean.setFlgBeneficiariObbligXTipoInterventi(lDocumentoXmlOutBean.getFlgBeneficiariObbligXTipoInterventi());
		bean.setSottotipoInterventi(lDocumentoXmlOutBean.getSottotipoInterventi());
		bean.setFlgBeneficiariObbligXSottotipoInterventi(lDocumentoXmlOutBean.getFlgBeneficiariObbligXSottotipoInterventi());
		bean.setFlgAltriProvvedimenti(lDocumentoXmlOutBean.getFlgAltriProvvedimenti());
		bean.setTipoAltriProvvedimenti(lDocumentoXmlOutBean.getTipoAltriProvvedimenti());
		bean.setFlgBeneficiariObbligXTipoAltriProvvedimenti(lDocumentoXmlOutBean.getFlgBeneficiariObbligXTipoAltriProvvedimenti());
		bean.setSottotipoAltriProvvedimenti(lDocumentoXmlOutBean.getSottotipoAltriProvvedimenti());
		bean.setFlgBeneficiariObbligXSottotipoAltriProvvedimenti(lDocumentoXmlOutBean.getFlgBeneficiariObbligXSottotipoAltriProvvedimenti());
		bean.setNormaTrasparenzaVantEcon(lDocumentoXmlOutBean.getNormaTrasparenzaVantEcon());
		bean.setModalitaIndividuazioneBeneficiario(lDocumentoXmlOutBean.getModalitaIndividuazioneBeneficiario());
		bean.setEstremiDocumentiPrincipaliFascicolo(lDocumentoXmlOutBean.getEstremiDocumentiPrincipaliFascicolo());
		bean.setAvvisiPerLaCompilazione(lDocumentoXmlOutBean.getAvvisiPerLaCompilazione());
		bean.setUfficioCompetenteTabTrasp(lDocumentoXmlOutBean.getUfficioCompetenteTabTrasp());		
		bean.setResponsabileProcedimentoTabTrasp(lDocumentoXmlOutBean.getResponsabileProcedimentoTabTrasp());
		bean.setDataAvvioProcedimento(lDocumentoXmlOutBean.getDataAvvioProcedimento());
		bean.setOggettoProvvedimento(lDocumentoXmlOutBean.getOggettoProvvedimento());		
		bean.setDurataRapportoGiuridico(lDocumentoXmlOutBean.getDurataRapportoGiuridico());
		bean.setNumeroMesiDurataRapporto(lDocumentoXmlOutBean.getNumeroMesiDurataRapporto());
		bean.setDalDurataRapporto(lDocumentoXmlOutBean.getDalDurataRapporto());
		bean.setAlDurataRapporto(lDocumentoXmlOutBean.getAlDurataRapporto());
		bean.setListaAllegatiObbligatoriTrasparenza(lDocumentoXmlOutBean.getListaAllegatiObbligatoriTrasparenza());
		bean.setListaIdAllegatiObbligatoriTrasparenza(lDocumentoXmlOutBean.getListaIdAllegatiObbligatoriTrasparenza());
		bean.setListaAllegatiNonObbligatoriTrasparenza(lDocumentoXmlOutBean.getListaAllegatiNonObbligatoriTrasparenza());
		bean.setListaIdAllegatiNonObbligatoriTrasparenza(lDocumentoXmlOutBean.getListaIdAllegatiNonObbligatoriTrasparenza());
		List<DestVantaggioBean> listaBeneficiariTrasparenza = new ArrayList<DestVantaggioBean>();
		if (lDocumentoXmlOutBean.getBeneficiariTrasparenza() != null && lDocumentoXmlOutBean.getBeneficiariTrasparenza().size() > 0) {
			for (DestinatarioVantaggioBean lDestinatarioVantaggioBean : lDocumentoXmlOutBean.getBeneficiariTrasparenza()) {
				DestVantaggioBean lDestVantaggioBean = new DestVantaggioBean();
				lDestVantaggioBean.setTipo(lDestinatarioVantaggioBean.getTipo());
				lDestVantaggioBean.setTipoPersona(lDestinatarioVantaggioBean.getTipoPersona());
				lDestVantaggioBean.setCognome(lDestinatarioVantaggioBean.getCognome());
				lDestVantaggioBean.setNome(lDestinatarioVantaggioBean.getNome());
				lDestVantaggioBean.setRagioneSociale(lDestinatarioVantaggioBean.getRagioneSociale());
				lDestVantaggioBean.setCodFiscalePIVA(lDestinatarioVantaggioBean.getCodFiscalePIVA());
				lDestVantaggioBean.setImporto(lDestinatarioVantaggioBean.getImporto());
				lDestVantaggioBean.setFlgPrivacy(lDestinatarioVantaggioBean.getFlgPrivacy());
				listaBeneficiariTrasparenza.add(lDestVantaggioBean);
			}
		} else {
			/**
			 * Non và più chiamato dal momento che listaBeneficiariTrasparenza ora è una GridItem
			 */
			//listaBeneficiariTrasparenza.add(new DestVantaggioBean());
		}
		bean.setListaBeneficiariTrasparenza(listaBeneficiariTrasparenza);		
		
		/* Dati liquidazione AVB */
		
		List<DatiLiquidazioneAVBBean> listaRiepilogoDatiLiquidazioneAVB = new ArrayList<DatiLiquidazioneAVBBean>();
		if (lDocumentoXmlOutBean.getListaRiepilogoDatiLiquidazioneAVB() != null && lDocumentoXmlOutBean.getListaRiepilogoDatiLiquidazioneAVB().size() > 0) {
			for (RiepilogoDatiLiquidazioneAVB lRiepilogoDatiLiquidazioneAVB : lDocumentoXmlOutBean.getListaRiepilogoDatiLiquidazioneAVB()) {
				DatiLiquidazioneAVBBean lRiepilogo = new DatiLiquidazioneAVBBean();
				lRiepilogo.setUriExcel(lRiepilogoDatiLiquidazioneAVB.getUriExcel());
				lRiepilogo.setAttoImpegno(lRiepilogoDatiLiquidazioneAVB.getOggettoSpesaNumDetImpegno());
				lRiepilogo.setOggettoSpesaOggetto(lRiepilogoDatiLiquidazioneAVB.getOggettoSpesaOggetto());
				lRiepilogo.setBeneficiarioSede(lRiepilogoDatiLiquidazioneAVB.getBeneficiarioPagamentoBeneficiario());
				lRiepilogo.setBeneficiarioPagamentoIva(lRiepilogoDatiLiquidazioneAVB.getBeneficiarioPagamentoIva());
				lRiepilogo.setBeneficiarioPagamentoCodFiscale(lRiepilogoDatiLiquidazioneAVB.getBeneficiarioPagamentoCodFiscale());
				lRiepilogo.setDocumentoDiDebito(lRiepilogoDatiLiquidazioneAVB.getDocumentoDiDebito());
				lRiepilogo.setBeneficiarioPagamentoImporto(lRiepilogoDatiLiquidazioneAVB.getBeneficiarioPagamentoImporto());
				listaRiepilogoDatiLiquidazioneAVB.add(lRiepilogo);
			}
		}
		bean.setListaDatiLiquidazioneAVB(listaRiepilogoDatiLiquidazioneAVB);
		
		return bean;
	}

	public NuovaPropostaAtto2CompletaBean nuovoComeCopia(NuovoAttoComeCopiaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());				

		DmpkWfGetdatinuovoiterattocomecopiaBean input = new DmpkWfGetdatinuovoiterattocomecopiaBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		input.setFlgtiponumerazionein(bean.getTipoNumerazioneCopia());
		input.setSiglanumerazionein(bean.getSiglaCopia());
		input.setNumeroin(StringUtils.isNotBlank(bean.getNumeroCopia()) ? new BigDecimal(bean.getNumeroCopia()) : null);
		input.setAnnonumerazionein(StringUtils.isNotBlank(bean.getAnnoCopia()) ? new BigDecimal(bean.getAnnoCopia()) : null);
		input.setIdproctypedaavviarein(StringUtils.isNotBlank(bean.getIdTipoProc()) ? new BigDecimal(bean.getIdTipoProc()) : null);

		DmpkWfGetdatinuovoiterattocomecopia lDmpkWfGetdatinuovoiterattocomecopia = new DmpkWfGetdatinuovoiterattocomecopia();
		StoreResultBean<DmpkWfGetdatinuovoiterattocomecopiaBean> storeOutput = lDmpkWfGetdatinuovoiterattocomecopia.execute(getLocale(), loginBean, input);
		
		if (storeOutput.isInError()) {
			throw new StoreException(storeOutput);
		} 
		
		NuovaPropostaAtto2CompletaBean copiaBean = new NuovaPropostaAtto2CompletaBean();
		
		if (storeOutput.getResultBean() != null) {
			copiaBean.setIdUd(storeOutput.getResultBean().getIduddacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIduddacopiareout().longValue()) : null);
			copiaBean.setIdProcess(storeOutput.getResultBean().getIdprocessdacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIdprocessdacopiareout().longValue()) : null);
				
			copiaBean = get(copiaBean);
			
			copiaBean.setDerivaDaRdA(storeOutput.getResultBean().getSiglanumerazionein() != null && storeOutput.getResultBean().getSiglanumerazionein().equals("RDA") ? "true" : null);
			copiaBean.setIdUdNuovoComeCopia(storeOutput.getResultBean().getIduddacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIduddacopiareout().longValue()) : null);
			copiaBean.setFlgTipoDocDiverso(storeOutput.getResultBean().getFlgdoctypediversoout() != null ? String.valueOf(storeOutput.getResultBean().getFlgdoctypediversoout().intValue()) : null);
			copiaBean.setSiglaRegAttoDaCopiare(storeOutput.getResultBean().getSiglaregattodacopiareout());
			copiaBean.setIdTipoDocDaCopiare(storeOutput.getResultBean().getIddoctypedacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIddoctypedacopiareout().longValue()) : null);
			if(storeOutput.getResultBean().getNumattodacopiareout() != null) {
				if (copiaBean.getListaAttiRiferimento() == null) {
					copiaBean.setListaAttiRiferimento(new ArrayList<AttoRiferimentoBean>());
				}
				AttoRiferimentoBean lAttoRiferimentoBean = new AttoRiferimentoBean();
				lAttoRiferimentoBean.setFlgPresenteASistema("SI");
				lAttoRiferimentoBean.setTipoAttoRif(storeOutput.getResultBean().getIddoctypedacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIddoctypedacopiareout().longValue()) : null);
				lAttoRiferimentoBean.setIdUd(storeOutput.getResultBean().getIduddacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getIduddacopiareout().longValue()) : null);
				lAttoRiferimentoBean.setCategoriaReg(storeOutput.getResultBean().getCodcatregattodacopiareout());
				lAttoRiferimentoBean.setSigla(storeOutput.getResultBean().getSiglaregattodacopiareout());
				lAttoRiferimentoBean.setAnno(storeOutput.getResultBean().getAnnoattodacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getAnnoattodacopiareout().intValue()) : null);
				lAttoRiferimentoBean.setNumero(storeOutput.getResultBean().getNumattodacopiareout() != null ? String.valueOf(storeOutput.getResultBean().getNumattodacopiareout().intValue()) : null);
				copiaBean.getListaAttiRiferimento().add(lAttoRiferimentoBean);
			}
			
			if(storeOutput.getResultBean().getFlgdoctypediversoout() != null && storeOutput.getResultBean().getFlgdoctypediversoout().intValue() == 1 && storeOutput.getResultBean().getSiglaregattodacopiareout() != null && "RDA".equalsIgnoreCase(storeOutput.getResultBean().getSiglaregattodacopiareout())) {
				// A differenza di CIG e CUP il "Numero gara" non deve essere importato sulla proposta di decreto quando si fa il nuovo come copia partendo da una RDA (quando la stored GetDatiNuovoIterAttoComeCopia restituisce SiglaRegAttoDaCopiareOut = RDA)
				copiaBean.setNumGara(null);
				if (copiaBean.getListaCIG() != null) {
					for (CIGCUPBean lCIGCUPBean : copiaBean.getListaCIG()) {
						// Il flag "escludi CIG" deve invece essere importato dalla RDA alla proposta di decreto (sempre nell'avvio decreto come copia partendo da RdA, quindi quando la stored GetDatiNuovoIterAttoComeCopia restituisce SiglaRegAttoDaCopiareOut = RDA)				
						lCIGCUPBean.setNumGara(null);
					}
				}
				// Quando si avvia una proposta di decreto come copia di una RDA preimpostare che si tratta di "cod. appalti" (in modo che in automatico compaia anche RUP di RDA)
				copiaBean.setFlgProcExCodAppalti(_FLG_SI);
				// Quando si avvia una proposta di decreto come copia di una RDA la UO proponente RDA deve essere proposta come UO competente della PDCR
				if(StringUtils.isNotBlank(copiaBean.getUfficioProponente())) {
					copiaBean.setUfficioCompetente(copiaBean.getUfficioProponente());
					copiaBean.setCodUfficioCompetente(copiaBean.getCodUfficioProponente());
					copiaBean.setDesUfficioCompetente(copiaBean.getDesUfficioProponente());				
					List<SelezionaUOBean> listaUfficioCompetente = new ArrayList<SelezionaUOBean>();		
					if(StringUtils.isNotBlank(copiaBean.getUfficioProponente())) {
						SelezionaUOBean lSelezionaUOBean = new SelezionaUOBean();
						lSelezionaUOBean.setIdUo(copiaBean.getUfficioProponente());
						lSelezionaUOBean.setCodRapido(copiaBean.getCodUfficioProponente());
						lSelezionaUOBean.setDescrizione(copiaBean.getDesUfficioProponente());
						lSelezionaUOBean.setDescrizioneEstesa(copiaBean.getDesUfficioProponente());				
						lSelezionaUOBean.setOrganigramma("UO" + copiaBean.getUfficioProponente());
						lSelezionaUOBean.setOrganigrammaFromLoadDett("UO" + copiaBean.getUfficioProponente());
						listaUfficioCompetente.add(lSelezionaUOBean);
					}
					copiaBean.setListaUfficioCompetente(listaUfficioCompetente);
				}
			}
		}
		
		if (bean.getFlgCopiaAllegatiPareri() != null && bean.getFlgCopiaAllegatiPareri()) {
			if (copiaBean.getListaAllegati() != null) {
				List<AllegatoProtocolloBean> listaAllegatiCopiati = new ArrayList<AllegatoProtocolloBean>();
				copiaBean.setFlgCopiaAllegatiPareri(true);
				for (AllegatoProtocolloBean allegato : copiaBean.getListaAllegati()) {
					AllegatoProtocolloBean copiaAllegato = new AllegatoProtocolloBean();
					if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
						InputStream streamAllegato = StorageImplementation.getStorage().extract(allegato.getUriFileAllegato());
						String uriCopiaAllegato = StorageImplementation.getStorage().storeStream(streamAllegato);
						copiaAllegato.setUriFileAllegato(uriCopiaAllegato);
						copiaAllegato.setIsChanged(true);
						copiaAllegato.setRemoteUri(false);
					}
					if (StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
						InputStream streamAllegatoOmissis = StorageImplementation.getStorage().extract(allegato.getUriFileOmissis());
						String uriCopiaAllegatoOmissis = StorageImplementation.getStorage().storeStream(streamAllegatoOmissis);
						copiaAllegato.setUriFileOmissis(uriCopiaAllegatoOmissis);
						copiaAllegato.setIsChangedOmissis(true);
						copiaAllegato.setRemoteUriOmissis(false);
					}
					copiaAllegato.setIdTipoFileAllegato(allegato.getIdTipoFileAllegato());
					copiaAllegato.setDescTipoFileAllegato(allegato.getDescTipoFileAllegato());
					copiaAllegato.setDictionaryEntrySezione(allegato.getDictionaryEntrySezione());
					copiaAllegato.setDescrizioneFileAllegato(allegato.getDescrizioneFileAllegato());	
					copiaAllegato.setPercorsoRelFileAllegati(allegato.getPercorsoRelFileAllegati());
					copiaAllegato.setNomeFileAllegato(allegato.getNomeFileAllegato());
					copiaAllegato.setIdAttachEmail(allegato.getIdAttachEmail());
					copiaAllegato.setInfoFile(allegato.getInfoFile());
					copiaAllegato.setFlgProvEsterna(allegato.getFlgProvEsterna());
					copiaAllegato.setFlgParteDispositivo(allegato.getFlgParteDispositivo());
					copiaAllegato.setFlgParteDispositivoSalvato(allegato.getFlgParteDispositivoSalvato());
					copiaAllegato.setFlgParere(allegato.getFlgParere());
					copiaAllegato.setFlgParereDaUnire(allegato.getFlgParereDaUnire());
					copiaAllegato.setFlgDatiProtettiTipo1(allegato.getFlgDatiProtettiTipo1());
					copiaAllegato.setFlgDatiProtettiTipo2(allegato.getFlgDatiProtettiTipo2());
					copiaAllegato.setFlgDatiProtettiTipo3(allegato.getFlgDatiProtettiTipo3());
					copiaAllegato.setFlgDatiProtettiTipo4(allegato.getFlgDatiProtettiTipo4());
					copiaAllegato.setFlgNoPubblAllegato(allegato.getFlgNoPubblAllegato());
					copiaAllegato.setFlgPubblicaSeparato(allegato.getFlgPubblicaSeparato());
					copiaAllegato.setFlgDatiSensibili(allegato.getFlgDatiSensibili());
					copiaAllegato.setFlgOriginaleCartaceo(allegato.getFlgOriginaleCartaceo());
					copiaAllegato.setFlgCopiaSostitutiva(allegato.getFlgCopiaSostitutiva());	
					copiaAllegato.setIdEmail(allegato.getIdEmail());
					// Vers. con omissis
					copiaAllegato.setNomeFileOmissis(allegato.getNomeFileOmissis()); 
					copiaAllegato.setInfoFileOmissis(allegato.getInfoFileOmissis()); 
					copiaAllegato.setNroProtocollo(allegato.getNroProtocollo());
					copiaAllegato.setAnnoProtocollo(allegato.getAnnoProtocollo());
					copiaAllegato.setDataProtocollo(allegato.getDataProtocollo());
					copiaAllegato.setFlgTipoProvXProt(allegato.getFlgTipoProvXProt());
					copiaAllegato.setIdUDScansione(allegato.getIdUDScansione());	
					listaAllegatiCopiati.add(copiaAllegato);
				}
				copiaBean.setListaAllegati(listaAllegatiCopiati);
			}
		}
		
		if(StringUtils.isNotBlank(copiaBean.getRowidDoc())) {
			
			AttributiDinamiciInputBean attributiDinamiciDocInput = new AttributiDinamiciInputBean();
			attributiDinamiciDocInput.setNomeTabella("DMT_DOCUMENTS");
			attributiDinamiciDocInput.setTipoEntita(copiaBean.getTipoDocumento());
			attributiDinamiciDocInput.setRowId(copiaBean.getRowidDoc());
			
			AttributiDinamiciOutputBean attributiDinamiciDocOuput = getAttributiDinamiciDatasource().call(attributiDinamiciDocInput);	
			
			if (attributiDinamiciDocOuput.getAttributiAdd() != null) {
				Map<String, Object> attributiDinamiciDocValues = new HashMap<String, Object>();
				for (AttributoBean attr : attributiDinamiciDocOuput.getAttributiAdd()) {
					if ("LISTA".equals(attr.getTipo())) {
						if (attributiDinamiciDocOuput.getMappaValoriAttrLista().get(attr.getNome()) != null) {
							List<Map<String, Object>> valoriLista = new ArrayList<Map<String, Object>>();
							for (Map<String, String> mapValori : attributiDinamiciDocOuput.getMappaValoriAttrLista().get(attr.getNome())) {
								Map<String, Object> valori = new HashMap<String, Object>(mapValori);
								for (DettColonnaAttributoListaBean dett : attributiDinamiciDocOuput.getMappaDettAttrLista().get(attr.getNome())) {
									if ("CHECK".equals(dett.getTipo())) {
										if (valori.get((String) dett.getNome()) != null
												&& !"".equals(valori.get((String) dett.getNome()))) {
											String valueStr = valori.get((String) dett.getNome()) != null ? ((String) valori.get((String) dett.getNome())).trim() : "";
											Boolean value = Boolean.FALSE;
											if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
												value = Boolean.TRUE;
											}
											valori.put((String) dett.getNome(), value);
										}
									} else if ("DOCUMENT".equals(dett.getTipo())) {
										String value = mapValori.get((String) dett.getNome());
										valori.put((String) dett.getNome(), value != null && !"".equals(value) ? attributiDinamiciDocOuput.getMappaDocumenti().get(value)
												: null);
									}
								}
								valoriLista.add(valori);
							}
							attributiDinamiciDocValues.put(attr.getNome(), valoriLista);
						}
					} else if ("DOCUMENT".equals(attr.getTipo())) {
						String value = attr.getValore();
						attributiDinamiciDocValues.put(attr.getNome(), value != null && !"".equals(value) ? attributiDinamiciDocOuput.getMappaDocumenti().get(value) : null);
					} else {
						if (attr.getValore() != null && !"".equals(attr.getValore())) {
							if ("CHECK".equals(attr.getTipo())) {
								String valueStr = attr.getValore() != null ? attr.getValore().trim() : "";
								Boolean value = Boolean.FALSE;
								if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
									value = Boolean.TRUE;
								}
								attributiDinamiciDocValues.put(attr.getNome(), value);
							} else
								attributiDinamiciDocValues.put(attr.getNome(), attr.getValore());
						}
	
					}
				}
				copiaBean.setValori(attributiDinamiciDocValues);
			}
		}
		
		return copiaBean;
	}

	private void populateListaVociPEGNoVerifDisp(NuovaPropostaAtto2CompletaBean bean) throws Exception {
	
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
	
	public NuovaPropostaAtto2CompletaBean getListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoSIB(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaDatiContabiliSIBCorrente(bean);
		}
		return bean;
	}
	
	private void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		populateListaDatiContabiliSIBCorrente(bean, true);
	}

	private void populateListaDatiContabiliSIBCorrente(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {
		try {			
			List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getSIBDataSource().getListaDatiContabiliCorrente(bean);
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
	
	public NuovaPropostaAtto2CompletaBean getListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoSIB(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			populateListaDatiContabiliSIBContoCapitale(bean);
		}
		return bean;
	}
	
	private void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		populateListaDatiContabiliSIBContoCapitale(bean, true);
	}

	private void populateListaDatiContabiliSIBContoCapitale(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {
		try {	
			List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) {
				List<DatiContabiliBean> listaDatiContabiliSIB = getSIBDataSource().getListaDatiContabiliContoCapitale(bean);
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
	
	public NuovaPropostaAtto2CompletaBean getListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoContabilia(bean) && isAttivaRequestMovimentiDaAMC(bean)) {
			// Recupero i dati della struttura da passare ai servizi di Contabilia
			try {
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
				bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
				bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
				bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());				
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}		
			populateListaMovimentiContabilia(bean);
		}
		return bean;
	}
	
	private void populateListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {	
		populateListaMovimentiContabilia(bean, true);
	}

	private void populateListaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean, boolean skipError) throws Exception {	
		try {	
			List<MovimentiContabiliaXmlBean> listaMovimentiContabilia = new ArrayList<MovimentiContabiliaXmlBean>();
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC()) && bean.getFlgSpesa() != null && _FLG_SI.equalsIgnoreCase(bean.getFlgSpesa())) {					
				String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
				if(lSistAMC != null) {
					if("CONTABILIA".equalsIgnoreCase(lSistAMC)) {
						listaMovimentiContabilia.addAll(getContabiliaDataSource().ricercaImpegno(bean));
						listaMovimentiContabilia.addAll(getContabiliaDataSource().ricercaAccertamento(bean));
					} else if("CONTABILIA2".equalsIgnoreCase(lSistAMC)) {
						List<MovimentiContabiliaXmlBean> listaMovimentiContabilia2 = getContabiliaDataSource().ricercaMovimentoGestione(bean);
						if(listaMovimentiContabilia2 != null && listaMovimentiContabilia2.size() == 0 && StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
							// se non trovo niente con la numerazione di proposta provo a passare la numerazione definitiva
							listaMovimentiContabilia2 = getContabiliaDataSource().ricercaMovimentoGestioneWithNumDefinitiva(bean);
						}
						listaMovimentiContabilia.addAll(listaMovimentiContabilia2);	
					}
				}
			}
			bean.setListaMovimentiContabilia(listaMovimentiContabilia);
			bean.setErrorMessageMovimentiContabilia(null);
		} catch(Exception e) {
			bean.setListaMovimentiContabilia(new ArrayList<MovimentiContabiliaXmlBean>());
			bean.setErrorMessageMovimentiContabilia("<font color=\"red\">Si è verificato un'errore durante la chiamata ai servizi di integrazione con Contabilia</font>");
			if(skipError) {
				addMessage("Si è verificato un'errore durante il recupero dei movimenti da Contabilia", "", MessageType.WARNING);
			} else {
				throw new StoreException("Si è verificato un'errore durante il recupero dei movimenti da Contabilia");
			}
		}	
	}
	
	public void cancellaMovimentoSICRA(NuovaPropostaAtto2CompletaBean bean, String numImpAcc) {
		if (bean.getListaInvioMovimentiContabiliSICRA() != null) {
			int posMovimentoToRemove = -1;
			for(int i = 0; i < bean.getListaInvioMovimentiContabiliSICRA().size(); i++) {
				if(StringUtils.isNotBlank(bean.getListaInvioMovimentiContabiliSICRA().get(i).getNumeroImpAcc()) && StringUtils.isNotBlank(numImpAcc) && bean.getListaInvioMovimentiContabiliSICRA().get(i).getNumeroImpAcc().equals(numImpAcc)) {
					posMovimentoToRemove = i;
				}
			}
			if(posMovimentoToRemove != -1) {
				bean.setEsitoSetMovimentiAttoSICRA("OK");
				bean.getListaInvioMovimentiContabiliSICRA().remove(posMovimentoToRemove);
			}
		}
		try {
			aggiornaMovimentiSICRA(bean);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public NuovaPropostaAtto2CompletaBean importaPdfLiquidazioneSICRA(NuovaPropostaAtto2CompletaBean bean) throws Exception {	
		if(isAttivoSICRA(bean)) {
			return getSICRADataSource().importaPdfLiquidazione(bean);		
		}
		return bean;
	}
	
	@Override
	public NuovaPropostaAtto2CompletaBean add(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		ProtocollazioneBean lProtocollazioneBeanInput = new ProtocollazioneBean();	
		lProtocollazioneBeanInput.setIdUdNuovoComeCopia(StringUtils.isNotBlank(bean.getIdUdNuovoComeCopia()) ? new BigDecimal(bean.getIdUdNuovoComeCopia()) : null);
		lProtocollazioneBeanInput.setPrefKeyModello(bean.getPrefKeyModello());
		lProtocollazioneBeanInput.setPrefNameModello(bean.getPrefNameModello());
		lProtocollazioneBeanInput.setUseridModello(bean.getUseridModello());
		lProtocollazioneBeanInput.setIdUoModello(bean.getIdUoModello());
		lProtocollazioneBeanInput.setFlgTipoDocDiverso(bean.getFlgTipoDocDiverso());
		lProtocollazioneBeanInput.setIdTipoDocDaCopiare(bean.getIdTipoDocDaCopiare());
		
		// Copio i dati a maschera nel bean di salvataggio
		populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBeanInput, bean);
		
		boolean isNumPropostaDiffXStruttura = getExtraparams().get("isNumPropostaDiffXStruttura") != null && "true".equalsIgnoreCase(getExtraparams().get("isNumPropostaDiffXStruttura"));
		
		String siglaRegSuffix = null;
		
		if(isNumPropostaDiffXStruttura) {		
			try {
				DmpkUtilityFindsoggettoinrubricaBean input = new DmpkUtilityFindsoggettoinrubricaBean();
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : loginBean.getIdUser());
				input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());
				input.setFlgcompletadatidarubrin(1);
				input.setFlginorganigrammain("UO");
				
				DatiSoggXMLIOBean lDatiSoggXMLIOBean = new DatiSoggXMLIOBean();
				if (StringUtils.isNotBlank(bean.getCodUfficioProponente())) {
					lDatiSoggXMLIOBean.setCodRapidoSoggetto(bean.getCodUfficioProponente());
				}
	
				Riga lRigaIn = new Riga();
				Colonna lColonnaIn = new Colonna();
				lColonnaIn.setContent(bean.getCodUfficioProponente());
				lColonnaIn.setNro(new BigInteger("27"));
				lRigaIn.getColonna().add(lColonnaIn);
			
				StringWriter lStringWriter = new StringWriter();
				SingletonJAXBContext.getInstance().createMarshaller().marshal(lRigaIn, lStringWriter);
				input.setDatisoggxmlio(lStringWriter.toString());
	
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(loginBean.getSchema());
				DmpkUtilityFindsoggettoinrubrica lDmpkUtilityFindsoggettoinrubrica = new DmpkUtilityFindsoggettoinrubrica();
				StoreResultBean<DmpkUtilityFindsoggettoinrubricaBean> output = lDmpkUtilityFindsoggettoinrubrica.execute(getLocale(), lSchemaBean, input);
			
				if (output.isInError()) {
					throw new StoreException(output);
				}
				
				if (StringUtils.isNotBlank(output.getResultBean().getDatisoggxmlio())) {
					StringReader lStringReader = new StringReader(output.getResultBean().getDatisoggxmlio());
					Riga lRigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(lStringReader);
					if (lRigaOut != null) {
						for (int i = 0; i < lRigaOut.getColonna().size(); i++) {
							if (lRigaOut.getColonna().get(i).getNro().toString().equals("40")) {
								siglaRegSuffix = lRigaOut.getColonna().get(i).getContent();
							}
						}
					}
				}
			
			} catch (Exception e) {
				logger.error(e);
				throw new StoreException("Si è verificato un errore durante il recupero del codice struttura da appendere alla sigla di numerazione della proposta");
			}
		}
		
		// Aggiungo i valori dei tab dinamici da salvare in avvio, tutti con il suffisso _Doc
		lProtocollazioneBeanInput.setValori(new HashMap<String, Object>());		
		if (bean.getValori() != null) {
			for (String attrName : bean.getValori().keySet()) {
				lProtocollazioneBeanInput.getValori().put(attrName + "_Doc", bean.getValori().get(attrName));
			}
		}		
		lProtocollazioneBeanInput.setTipiValori(new HashMap<String, String>());
		if (bean.getTipiValori() != null) {
			for (String attrName : bean.getTipiValori().keySet()) {
				if(!attrName.contains(".")) {
					lProtocollazioneBeanInput.getTipiValori().put(attrName + "_Doc", bean.getTipiValori().get(attrName));
				} else {
					// se contiene il punto è l'attributo relativo alla colonna di un attributo lista
					lProtocollazioneBeanInput.getTipiValori().put(attrName.substring(0, attrName.indexOf(".")) + "_Doc" + attrName.substring(attrName.indexOf(".")), bean.getTipiValori().get(attrName));
				}
			}
		}
		
		// per la numerazione da dare all'avvio dell'atto
		lProtocollazioneBeanInput.setCodCategoriaProtocollo(bean.getCategoriaRegAvvio());			
		if(StringUtils.isNotBlank(siglaRegSuffix)) {
			lProtocollazioneBeanInput.setSiglaProtocollo(bean.getSiglaRegAvvio() + siglaRegSuffix);		
		} else {
			lProtocollazioneBeanInput.setSiglaProtocollo(bean.getSiglaRegAvvio());			
		}
		
		try {
			ProtocollazioneBean lProtocollazioneBeanOutput = getProtocolloDataSource(bean).add(lProtocollazioneBeanInput);
			if(lProtocollazioneBeanOutput.getErroriFile() != null && !lProtocollazioneBeanOutput.getErroriFile().isEmpty()) {
				bean.setErroriFile(lProtocollazioneBeanOutput.getErroriFile());
				return bean;
			}
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
		
		bean.setOggetto(lProtocollazioneBeanInput.getOggetto()); // devo settare l'oggetto, da passare al servizio di SIB, con la versione con omissis di oggettoHtml
//		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di SIB, con la versione con omissis di oggettoHtml

		if(bean.getListaDocFasc() != null && StringUtils.isNotBlank(bean.getIdFolder())) {
			try {
				ArchivioBean lArchivioBeanInput = new ArchivioBean();
				lArchivioBeanInput.setIdUdFolder(bean.getIdFolder());
				lArchivioBeanInput.setListaDocumentiIstruttoria(bean.getListaDocFasc());
				ArchivioBean lArchivioBeanOutput = getArchivioDatasource().updateDocumentiIstruttoriaFascicoloFromUdAtto(lArchivioBeanInput, new BigDecimal(bean.getIdUd()));			
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
		
		// Chiamo il servizio CreaProposta di SIB
		if(isAttivoSIB(bean)) {
			bean.setEventoSIB("creaProposta");
			getSIBDataSource().creaProposta(bean);	 		
			aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB
		}													
		
		if(isAttivoContabilia(bean)) {
			boolean isAvvioLiquidazioneContabilia = getExtraparams().get("isAvvioLiquidazioneContabilia") != null && "true".equalsIgnoreCase(getExtraparams().get("isAvvioLiquidazioneContabilia"));
			if(isAvvioLiquidazioneContabilia) {
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
					bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
					bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
					bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());
					RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
					if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria())) {
						// Numerazione finale
						bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
						bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
						// Numerazione provvisoria
						bean.setSiglaRegProvvisoria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
						bean.setNumeroRegProvvisoria(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
						bean.setDataRegProvvisoria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());	
						bean.setAnnoRegProvvisoria(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());						
					}  else if(lRegNumPrincipale != null && lRegNumPrincipale.getCodCategoria() != null && "R".equals(lRegNumPrincipale.getCodCategoria())) {
						// Numerazione finale
						bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
						bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
					} else {
						// Numerazione provvisoria
						bean.setSiglaRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
						bean.setNumeroRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
						bean.setDataRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
						bean.setAnnoRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);		
						bean.setDesUserRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
						bean.setDesUORegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
					}		
					bean.setEstremiRepertorioPerStruttura(lDocumentoXmlOutBean.getEstremiRepertorioPerStruttura());	
					bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
					bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
					bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
					bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());
					bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
					bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
					bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
					bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
				} catch (StoreException se) {
					throw se;
				} catch (Exception e) {
					throw new StoreException(e.getMessage());
				}
				try {
					bean.setEventoContabilia("creaLiquidazione");
					getContabiliaDataSource().creaLiquidazione(bean);
					aggiornaDatiLiquidazioneContabilia(bean); // salvo i dati relativi a Contabilia in DB			
					if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
						throw new StoreException(bean.getErrMsgEventoContabilia());					
					}
				} catch(Exception e) {
					annullamentoLogicoUdAtto(bean);
					throw e;
				}
			} else if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {
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
					bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
					bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
					bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());					
				} catch (StoreException se) {
					throw se;
				} catch (Exception e) {
					throw new StoreException(e.getMessage());
				}
				try {
					bean.setEventoContabilia("invioProposta");
					getContabiliaDataSource().invioProposta(bean);
					aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB
					if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
						throw new StoreException(bean.getErrMsgEventoContabilia());
					}
				} catch(Exception e) {
					annullamentoLogicoUdAtto(bean);
					throw e;
				}				
			}
		}
		
		return bean;
	}
	
	public void annullamentoLogicoUdAtto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
			DmpkCoreDel_ud_doc_verBean delUdDocVerInput = new DmpkCoreDel_ud_doc_verBean();
			delUdDocVerInput.setFlgtipotargetin("U");
			delUdDocVerInput.setIduddocin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);
			delUdDocVerInput.setFlgcancfisicain(null); // annullamento logico
	
			DmpkCoreDel_ud_doc_ver delUdDocVer = new DmpkCoreDel_ud_doc_ver();
			StoreResultBean<DmpkCoreDel_ud_doc_verBean> output = delUdDocVer.execute(getLocale(), loginBean, delUdDocVerInput);
	
			if (output.isInError()) {
				throw new StoreException(output);					
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addMessage(e.getMessage(), "", MessageType.WARNING);			
		}
	}

	@Override
	public NuovaPropostaAtto2CompletaBean update(NuovaPropostaAtto2CompletaBean bean, NuovaPropostaAtto2CompletaBean oldvalue) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if (isAttivoSICRA(bean) && bean.getListaInvioMovimentiContabiliSICRA() != null && bean.getListaInvioMovimentiContabiliSICRA().size() > 0 && bean.getFlgSpesa() != null && !_FLG_SI.equals(bean.getFlgSpesa())) {
			if(_FLG_NO.equals(bean.getFlgSpesa())) {
				throw new StoreException("E' stato indicato che l'atto è senza rilevanza contabile: non è consentito indicare dei movimenti contabili");
			} else if(getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(bean.getFlgSpesa())) {
				throw new StoreException("E' stato indicato che l'atto è con rilevanza contabile ma senza movimenti: non è consentito indicare dei movimenti contabili");
			} 
		}
		
		if(isAttivaRequestMovimentiDaAMC(bean) && isAttivaSalvataggioMovimentiDaAMC(bean)) {			
			// in salvataggio se non ho dei movimenti contabili rifaccio la chiamata ad AMC e se va' in errore blocco tutto
			if(isAttivoSIB(bean)) {
				if (bean.getListaDatiContabiliSIBCorrente() == null || bean.getListaDatiContabiliSIBCorrente().isEmpty()) {
					populateListaDatiContabiliSIBCorrente(bean, false);	
				}
				if (bean.getListaDatiContabiliSIBContoCapitale() == null || bean.getListaDatiContabiliSIBContoCapitale().isEmpty()) {
					populateListaDatiContabiliSIBContoCapitale(bean, false);	
				}				
			}
			if(isAttivoContabilia(bean) && (bean.getListaMovimentiContabilia() == null || bean.getListaMovimentiContabilia().isEmpty())) {
				populateListaMovimentiContabilia(bean, false);				
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
		lProtocollazioneBeanInput.setFlgTipoDocDiverso(bean.getFlgTipoDocDiverso());
		lProtocollazioneBeanInput.setIdTipoDocDaCopiare(bean.getIdTipoDocDaCopiare());
		lProtocollazioneBeanInput.setTimestampGetData(bean.getTimestampGetData());
		
		// se l'atto risulta già firmato devo inibire il versionamento del dispositivo (vers. integrale e omissis) per non sovrascrivere il file unione
		boolean isAttoFirmato = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().isFirmato();
		
		// se ho appena generato il file unione ed è attivo lo skip della firma devo comunque inibire il versionamento del dispositivo (vers. integrale e omissis) per non sovrascriverlo	
		boolean isAttoWithFileUnione = getExtraparams().get("isAttoWithFileUnione") != null && "true".equalsIgnoreCase(getExtraparams().get("isAttoWithFileUnione"));
		
		// Genero il file dispositivo atto (vers. integrale e omissis) da passare in salvataggio
		if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "VERS_DISPOSITIVO_NUOVA_PROPOSTA_ATTO_2")) {
			try {
				boolean versionaFileDispositivo = getExtraparams().get("versionaFileDispositivo") != null && "true".equalsIgnoreCase(getExtraparams().get("versionaFileDispositivo"));
				if(versionaFileDispositivo && !isAttoWithFileUnione && !isAttoFirmato && StringUtils.isNotBlank(bean.getIdModello())) {
					bean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale			
	//				FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
					FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(bean, true); // ma noi vogliamo salvarlo in pdf
					if(lFileDaFirmareBean != null) {
						aggiornaPrimario(bean, lFileDaFirmareBean);
					}
					boolean hasPrimarioDatiSensibili = getExtraparams().get("hasPrimarioDatiSensibili") != null && "true".equalsIgnoreCase(getExtraparams().get("hasPrimarioDatiSensibili"));		
					if(hasPrimarioDatiSensibili) {
						bean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis			
	//					FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean, false); // in questo modo lo salverebbe in .doc, come faceva prima	
						FileDaFirmareBean lFileDaFirmareBeanOmissis = generaDispositivoDaModello(bean, true); // ma noi vogliamo salvarlo in pdf
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
		populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBeanInput, bean);
		
		// Aggiungo i valori dei tab dinamici da salvare in avvio, tutti con il suffisso _Doc
		lProtocollazioneBeanInput.setValori(new HashMap<String, Object>());		
		if (bean.getValori() != null) {
			for (String attrName : bean.getValori().keySet()) {
				lProtocollazioneBeanInput.getValori().put(attrName + "_Doc", bean.getValori().get(attrName));
			}
		}		
		lProtocollazioneBeanInput.setTipiValori(new HashMap<String, String>());
		if (bean.getTipiValori() != null) {
			for (String attrName : bean.getTipiValori().keySet()) {
				if(!attrName.contains(".")) {
					lProtocollazioneBeanInput.getTipiValori().put(attrName + "_Doc", bean.getTipiValori().get(attrName));
				} else {
					// se contiene il punto è l'attributo relativo alla colonna di un attributo lista
					lProtocollazioneBeanInput.getTipiValori().put(attrName.substring(0, attrName.indexOf(".")) + "_Doc" + attrName.substring(attrName.indexOf(".")), bean.getTipiValori().get(attrName));
				}
			}
		}
		
		// Passo le numerazioni da dare in salvataggio, se non sono già presenti
		HashSet<String> setSigleRegistroAttiPresenti = new HashSet<String>();
		if(StringUtils.isNotBlank(lProtocollazioneBeanInput.getSiglaProtocollo())) {
			setSigleRegistroAttiPresenti.add(lProtocollazioneBeanInput.getSiglaProtocollo());
		}
		if(StringUtils.isNotBlank(lProtocollazioneBeanInput.getSiglaNumerazioneSecondaria())) {
			setSigleRegistroAttiPresenti.add(lProtocollazioneBeanInput.getSiglaNumerazioneSecondaria());
		}
		List<TipoNumerazioneBean> listaTipiNumerazioneDaDare = new ArrayList<TipoNumerazioneBean>();
		if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto")) && !setSigleRegistroAttiPresenti.contains(getExtraparams().get("siglaRegistroAtto"))) {
			TipoNumerazioneBean lTipoNumerazioneBean = new TipoNumerazioneBean();			
			lTipoNumerazioneBean.setSigla(getExtraparams().get("siglaRegistroAtto"));
			lTipoNumerazioneBean.setCategoria("R");		
			listaTipiNumerazioneDaDare.add(lTipoNumerazioneBean);
		}
		if(StringUtils.isNotBlank(getExtraparams().get("siglaRegistroAtto2")) && !setSigleRegistroAttiPresenti.contains(getExtraparams().get("siglaRegistroAtto2"))) {
			TipoNumerazioneBean lTipoNumerazione2Bean = new TipoNumerazioneBean();			
			lTipoNumerazione2Bean.setSigla(getExtraparams().get("siglaRegistroAtto2"));
			lTipoNumerazione2Bean.setCategoria("R");		
			listaTipiNumerazioneDaDare.add(lTipoNumerazione2Bean);
		}
		if(listaTipiNumerazioneDaDare.size() > 0) {
			lProtocollazioneBeanInput.setListaTipiNumerazioneDaDare(listaTipiNumerazioneDaDare);
		}
		
		// Sto aggiornando lo stato del documento solamente da convocazione sedute quando lo stato non è in lavori_conclusi
		if(getExtraparams().get("codStatoDett") != null && !"".equalsIgnoreCase(getExtraparams().get("codStatoDett"))) {
			lProtocollazioneBeanInput.setCodStatoDett(getExtraparams().get("codStatoDett"));
		}
				
		try {
			boolean isSalvataggioDefinitivoPreCompleteTask = getExtraparams().get("flgSalvataggioDefinitivoPreCompleteTask") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvataggioDefinitivoPreCompleteTask"));
			boolean isSalvaDatiDefinitivoSenzaEseguiAzioni = getExtraparams().get("flgSalvaDatiDefinitivoSenzaEseguiAzioni") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvaDatiDefinitivoSenzaEseguiAzioni"));
			
			ProtocollazioneBean lProtocollazioneBeanOutput = null;
			// Avendo già fatto il salvataggio provvisorio all'inizio non serve che risalvo tutto quanto in quello definitivo, ma solo file primario e allegati
			// Se seleziono però un esito senza azioni non viene fatto all'inizio il salvataggio provvisorio, quindi devo salvare tutto in quello definitivo
			// Anche se ho la versione word atto da salvare nell'attributo VERS_ATTO_FMT_DOC devo risalvare tutto in quello definitivo
			if(isSalvataggioDefinitivoPreCompleteTask && !isSalvaDatiDefinitivoSenzaEseguiAzioni && StringUtils.isBlank(bean.getUriDocGeneratoFormatoOdt())) {
				lProtocollazioneBeanOutput = getProtocolloDataSource(bean).updateAllegatiDocumento(lProtocollazioneBeanInput);
			} else {
				lProtocollazioneBeanOutput = getProtocolloDataSource(bean).update(lProtocollazioneBeanInput, null);
			}
			if(lProtocollazioneBeanOutput.getErroriFile() != null && !lProtocollazioneBeanOutput.getErroriFile().isEmpty()) {
				bean.setErroriFile(lProtocollazioneBeanOutput.getErroriFile());
				return bean;
			}
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
		// devo anche recuperarmi i dati della struttura da passare ai servizi di Contabilia
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
			bean.setDirigenteResponsabileContabilia(lDocumentoXmlOutBean.getDirigenteResponsabileContabilia());
			bean.setCentroResponsabilitaContabilia(lDocumentoXmlOutBean.getCentroResponsabilitaContabilia());
			bean.setCentroCostoContabilia(lDocumentoXmlOutBean.getCentroCostoContabilia());
			RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
			if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria())) {
				// Numerazione finale
				bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
				bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
				// Numerazione provvisoria
				bean.setSiglaRegProvvisoria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
				bean.setNumeroRegProvvisoria(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
				bean.setDataRegProvvisoria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());	
				bean.setAnnoRegProvvisoria(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());						
			}  else if(lRegNumPrincipale != null && lRegNumPrincipale.getCodCategoria() != null && "R".equals(lRegNumPrincipale.getCodCategoria())) {
				// Numerazione finale
				bean.setSiglaRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);
				bean.setDesUserRegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegistrazione(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
			} else {
				// Numerazione provvisoria
				bean.setSiglaRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getSigla() : null);
				bean.setNumeroRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getNro() : null);
				bean.setDataRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getTsRegistrazione() : null);
				bean.setAnnoRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getAnno() : null);		
				bean.setDesUserRegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUser() : null);
				bean.setDesUORegProvvisoria(lRegNumPrincipale != null ? lRegNumPrincipale.getDesUO() : null);	
			}
			bean.setEstremiRepertorioPerStruttura(lDocumentoXmlOutBean.getEstremiRepertorioPerStruttura());
			bean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
			bean.setIdDocPrimarioLiquidazione(lDocumentoXmlOutBean.getIdDocPrimarioLiquidazione());
			bean.setCodTipoLiquidazioneXContabilia(lDocumentoXmlOutBean.getCodTipoLiquidazioneXContabilia());
			bean.setSiglaRegLiquidazione(lDocumentoXmlOutBean.getSiglaRegLiquidazione());			
			bean.setAnnoRegLiquidazione(lDocumentoXmlOutBean.getAnnoRegLiquidazione());
			bean.setNroRegLiquidazione(lDocumentoXmlOutBean.getNroRegLiquidazione());
			bean.setDataAdozioneLiquidazione(lDocumentoXmlOutBean.getDataAdozioneLiquidazione());
			bean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		
		
		bean.setOggetto(lProtocollazioneBeanInput.getOggetto()); // devo settare l'oggetto, da passare al servizio di SIB, con la versione con omissis di oggettoHtml
//		bean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml()));// devo settare l'oggetto, da passare ai servizi di SIB, con la versione con omissis di oggettoHtml
		
		if(bean.getListaDocFasc() != null && StringUtils.isNotBlank(bean.getIdFolder())) {
			try {
				ArchivioBean lArchivioBeanInput = new ArchivioBean();
				lArchivioBeanInput.setIdUdFolder(bean.getIdFolder());
				lArchivioBeanInput.setListaDocumentiIstruttoria(bean.getListaDocFasc());
				ArchivioBean lArchivioBeanOutput = getArchivioDatasource().updateDocumentiIstruttoriaFascicoloFromUdAtto(lArchivioBeanInput, new BigDecimal(bean.getIdUd()));			
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
		
		// Chiamo il servizio CreaProposta o AggiornaAtto di SIB
		if(isAttivoSIB(bean)) {		
			if (StringUtils.isBlank(bean.getIdPropostaAMC())) {			
				bean.setEventoSIB("creaProposta");
				getSIBDataSource().creaProposta(bean);											 		
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB		
			} else {			
				getSIBDataSource().aggiornaAtto(bean);			
				if(StringUtils.isNotBlank(bean.getEventoSIB())) {
					if(bean.getEsitoEventoSIB() != null && bean.getEsitoEventoSIB().equals("KO")) {
						addMessage(bean.getErrMsgEventoSIB(), "", MessageType.WARNING);
					}
				}			
				aggiornaDatiSIB(bean); // salvo i dati relativi a SIB in DB			
			}
		}
		
		if(isAttivoContabilia(bean)) {
			if(bean.getEventoContabilia() != null && "creaLiquidazione".equalsIgnoreCase(bean.getEventoContabilia())) {
				getContabiliaDataSource().creaLiquidazione(bean);
				aggiornaDatiLiquidazioneContabilia(bean); // salvo i dati relativi a Contabilia in DB			
				if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
					throw new StoreException(bean.getErrMsgEventoContabilia());					
				}
			} else if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {	
				if(StringUtils.isBlank(bean.getIdPropostaAMC())) {
					bean.setEventoContabilia("invioProposta");
					getContabiliaDataSource().invioProposta(bean);
					aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB	
					if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
						throw new StoreException(bean.getErrMsgEventoContabilia());
					}
				}
				if(StringUtils.isNotBlank(bean.getEventoContabilia())) {
					StringSplitterServer st = new StringSplitterServer(bean.getEventoContabilia(), ";");
					while(st.hasMoreTokens()) {
		    			eventoContabilia(st.nextToken(), bean);
		    		}		    		
				}		
			}
		}
		
		if(isAttivoSICRA(bean)) {
			if(bean.getFlgSpesa() != null && bean.getFlgSpesa().toUpperCase().startsWith(_FLG_SI)) {	
				if(_FLG_SI.equals(bean.getFlgSpesa())) {
					getSICRADataSource().setMovimentiAtto(bean);
					aggiornaMovimentiSICRA(bean); // per aggiornare i movimenti contabili di SICRA in DB
				}
				if(StringUtils.isNotBlank(bean.getEventoSICRA())) {
					if("archiviaAtto".equalsIgnoreCase(bean.getEventoSICRA())) {
						getSICRADataSource().archiviaAtto(bean);
					} else if("setEsecutivitaAtto".equalsIgnoreCase(bean.getEventoSICRA())) {
						getSICRADataSource().setEsecutivitaAtto(bean);				
					} else if("aggiornaRifAttoLiquidazione".equalsIgnoreCase(bean.getEventoSICRA())) {
						getSICRADataSource().aggiornaRifAttoLiquidazione(bean);
					}
				}
				aggiornaDatiEventoSICRA(bean); // per aggiornare esito, data ed eventuale messaggio di errore dell'evento SICRA in DB
				if(bean.getEsitoEventoSICRA() != null && bean.getEsitoEventoSICRA().equals("KO")) {
					if("aggiornaRifAttoLiquidazione".equalsIgnoreCase(bean.getEventoSICRA())) {
						addMessage("N.ro proposta atto non trovato in Sicra: impossibile riportare in automatico su Sicra il n.ro definitivo dell'atto", "", MessageType.WARNING);						
					} else {
						throw new StoreException(bean.getErrMsgEventoSICRA());
					}
				}
			}
		}
		
		/*FLG CHE MI DICE SE STO SALVANDO IN BOZZA O FACCIO SALVA E PROCEDI*/
		boolean flgSalvaInBozza = getExtraparams().get("flgSalvataggioProvvisorioInBozza") != null && "true".equalsIgnoreCase(getExtraparams().get("flgSalvataggioProvvisorioInBozza"));
		String errorMsgInsertAttoContabilita = "";
		
		/***
		 * 
		 * INTEGRAZIONE CON I SERVIZI CONTABILI DI ADSP
		 * 
		 * */
		
		final boolean flgDisattivaIntegrazioneSistemaContabile = getContabilitaADSPDataSource().getFlgDisattivaIntegrazioneSistemaContabile(bean); /*&& TODO: DISCRIMINANTE INTEGRAZIONE SISTEMA CONTABILE */
		
		if(isAttivoCWOL(bean) && !flgDisattivaIntegrazioneSistemaContabile) {
			if(bean.getListaDatiContabiliADSP()!=null && bean.getListaDatiContabiliADSP().size()>0) {
				//Se non è già stato inserito l'atto sul sistema contabile lo creo chiamando la insert
				if(!bean.getFlgSavedAttoSuSistemaContabile()) {
					boolean flgInserito = false; 
					try {
						ContabilitaAdspInsertAttoResponse response = getContabilitaADSPDataSource().inserisciAtto(bean);
						if(response.isOk()) {
							flgInserito = true;
						}else {
							errorMsgInsertAttoContabilita = response.getMsg();
						}
					} catch (Exception e) {
						errorMsgInsertAttoContabilita = e.getMessage();
					}					
					bean.setFlgSavedAttoSuSistemaContabile(flgInserito);
					
					//Se ho fatto salva e procedi e l'atto non è inserito sul sistema contabile blocco l'avanzamento del passo lanciando un eccezione
					if(!flgSalvaInBozza && !flgInserito) {
						throw new StoreException("C'è stato un errore nell'inviare i dati contabili, riprovare l'operazione o contattare l'assistenza: \nErrore: " + errorMsgInsertAttoContabilita);
					}		
					
					getContabilitaADSPDataSource().aggiornaDBConDatiContabiliADSP(bean);					
					
				}else {		
					//L'atto è già stato inserito sul sistema contabile aggiungo solo i movimenti
					getContabilitaADSPDataSource().inviaMovimentiContabili(bean);
					
					getContabilitaADSPDataSource().aggiornaDBConDatiContabiliADSP(bean);
					
					/*
					 * Se non sto facendo salva in bozza ma salva e procedi, se ho dati contabili non allineati blocco l'avanzamento del passo per far allineare prima i dati
					 * 
					 * N.B. lato client è gestito questo flg nella maschera TaskNuovaPropostaAtto2CompletaDetail
					 * */
					if(!flgSalvaInBozza) {
						for(DatiContabiliADSPXmlBean movimentoContabile : bean.getListaDatiContabiliADSP()) {
							if(movimentoContabile.getStatoSistemaContabile().equals("non_allineato")) {
								bean.setFlgDatiContabiliADSPNonAllineati(true);
								return bean;
							}
						}						
					}
					
					if(StringUtils.isNotBlank(bean.getEventoCWOL())) {
						StringSplitterServer st = new StringSplitterServer(bean.getEventoCWOL(), ";");
						while(st.hasMoreTokens()) {
			    			eventoCWOL(st.nextToken(), bean);
			    		}		    		
					}	
					
					if(bean.getFlgRecuperaMovimentiContabDefinitivi()!=null && bean.getFlgRecuperaMovimentiContabDefinitivi()) {
						
						getContabilitaADSPDataSource().getMovimentiDefinitivi(bean);
						
						getContabilitaADSPDataSource().aggiornaDBConDatiContabiliADSP(bean);
					}
					
				}	
			}						
		}else {
			if(!bean.getFlgSenzaImpegniCont()) {
				if(StringUtils.isNotBlank(bean.getEventoCWOL())) {
					StringSplitterServer st = new StringSplitterServer(bean.getEventoCWOL(), ";");
					while(st.hasMoreTokens()) {
		    			if("CtrlDisponbilitaRdA".equalsIgnoreCase(st.nextToken())) {
		    				getContabilitaADSPDataSource().verificaDisponibilitaProposte(bean);
		    			}
		    		}		    		
				}
			}
		}
		
		boolean flgProtocollazioneProsa = getExtraparams().get("flgProtocollazioneProsa") != null && "true".equalsIgnoreCase(getExtraparams().get("flgProtocollazioneProsa"));
		if(flgProtocollazioneProsa) {
			eseguiProtocollazioneProsa(bean);
		}
		
		return bean;
	}
	
	public void eventoContabilia(String evento, NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(StringUtils.isNotBlank(evento)) {
			if("creaLiquidazione".equalsIgnoreCase(evento)) {
				getContabiliaDataSource().creaLiquidazione(bean);
				aggiornaDatiLiquidazioneContabilia(bean); // salvo i dati relativi a Contabilia in DB							
			} else {
				if("aggiornaProposta".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().aggiornaProposta(bean);
				} else if("bloccoDatiProposta".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().bloccoDatiProposta(bean);				
				} else if("invioAttoDef".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().invioAttoDef(bean);				
				} else if("invioAttoDefEsec".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().invioAttoDefEsec(bean);				
				} else if("invioAttoEsec".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().invioAttoEsec(bean);				
				} else if("sbloccoDatiProposta".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().sbloccoDatiProposta(bean);				
				} else if("annullamentoProposta".equalsIgnoreCase(evento)) {
					getContabiliaDataSource().annullamentoProposta(bean);				
				}	
				aggiornaDatiPropostaContabilia(bean); // salvo i dati relativi a Contabilia in DB			
			}
			if(bean.getEsitoEventoContabilia() != null && bean.getEsitoEventoContabilia().equals("KO")) {
				if("aggiornaProposta".equalsIgnoreCase(bean.getEventoContabilia())) {
					addMessage(bean.getErrMsgEventoContabilia(), "", MessageType.WARNING);
				} else {
					throw new StoreException(bean.getErrMsgEventoContabilia());
				}
			}
		}
	}
	
	private void eventoCWOL(String evento, NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(StringUtils.isNotBlank(evento)) {
			
			NuovaPropostaAtto2CompletaBean beanForStato = getContabilitaADSPDataSource().verificaStatoAttoContabilita(bean);
			Integer statoAtto = beanForStato.getStatoAttoContabilita();
			
			if(statoAtto==99) {
				throw new StoreException("Errore durante il recupero dello stato dell'atto sul sistema contabile: " + bean.getErrMsgEventoContabilia());
			}

			ContabilitaAdspResponse response = null;
			if("attoAdottato".equalsIgnoreCase(evento) && statoAtto!=STATO_ATTO_ADSP_ATTO_ADOTTATO) {
				response = getContabilitaADSPDataSource().attoAdottato(bean);
			} else if("attoConfermato".equalsIgnoreCase(evento) && statoAtto==STATO_ATTO_ADSP_ATTO_IN_BOZZA) {
				response = getContabilitaADSPDataSource().attoConfermato(bean);				
			} else if("attoInBozza".equalsIgnoreCase(evento) && statoAtto!=STATO_ATTO_ADSP_ATTO_IN_BOZZA) {
				response = getContabilitaADSPDataSource().attoInBozza(bean);				
			} else if("attoValidato".equalsIgnoreCase(evento) && statoAtto!=STATO_ATTO_ADSP_ATTO_VALIDATO) {
				if(statoAtto==STATO_ATTO_ADSP_ATTO_CONFERMATO) {
					throw new StoreException("Gli impegni/accertamenti non sono stati gestiti nel sistema contabile: non puoi procedere "
							+ "finché non siano stati gestiti nel sistema contabile");
				}else {
					response = getContabilitaADSPDataSource().attoValidato(bean);
				}
			} else if("cancellaAtto".equalsIgnoreCase(evento)) {
				response = getContabilitaADSPDataSource().cancellaAtto(bean);				
			} else if("attesaConformita".equalsIgnoreCase(evento) && statoAtto!=STATO_ATTO_ADSP_ATTO_ATTESA_CONFORMITA) {
				response = getContabilitaADSPDataSource().attesaConformita(bean);
			}else {
				response = new ContabilitaAdspResponse();
				response.setOk(true);
			}
			
			String errorMessage = "C'è stato un errore nell'aggiornamento atto sul sistema contabile: - ";
			if (response == null) {
				throw new StoreException(errorMessage + "Errore nell'invocazione dell'operazione " + evento);
			} else if (!response.isOk()) {
				throw new StoreException(errorMessage + response.getMsg());
			}
		}
	}
	
	private void aggiornaDatiSIB(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(isAttivoSIB(bean)/* && bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())*/) {				
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
	
	private void aggiornaMovimentiContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {				
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if (bean.getListaMovimentiContabilia() != null && bean.getFlgSpesa() != null && _FLG_SI.equals(bean.getFlgSpesa())) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_CONTABILIA_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaMovimentiContabilia()));					
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
	
	private void aggiornaDatiLiquidazioneContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
			}				
			
			if(StringUtils.isNotBlank(bean.getEventoContabilia())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoContabilia() != null ? bean.getEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoContabilia() != null ? bean.getEsitoEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoContabilia() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoContabilia()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoContabilia() != null ? bean.getErrMsgEventoContabilia() : "");
			}
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
	
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(loginBean.getToken());
				input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
	
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimarioLiquidazione()) ? new BigDecimal(bean.getIdDocPrimarioLiquidazione()) : null);
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
	
	private void aggiornaDatiPropostaContabilia(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getIdPropostaAMC())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROPOSTA_SIST_CONT_Doc", bean.getIdPropostaAMC() != null ? bean.getIdPropostaAMC() : "");
			}				
			
			if(StringUtils.isNotBlank(bean.getEventoContabilia()) /*&& "aggiornaProposta".equalsIgnoreCase(bean.getEventoContabilia())*/) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoContabilia() != null ? bean.getEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoContabilia() != null ? bean.getEsitoEventoContabilia() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoContabilia() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoContabilia()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoContabilia() != null ? bean.getErrMsgEventoContabilia() : "");
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
	
	private void aggiornaMovimentiSICRA(NuovaPropostaAtto2CompletaBean bean) throws Exception {				
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			boolean isSetMovimentiAttoSicraOK = bean.getEsitoSetMovimentiAttoSICRA() != null && bean.getEsitoSetMovimentiAttoSICRA().equalsIgnoreCase("OK");
			boolean isSetMovimentiAttoSicraWARNWithoutMessageXml = bean.getEsitoSetMovimentiAttoSICRA() != null && bean.getEsitoSetMovimentiAttoSICRA().equalsIgnoreCase("WARN") && StringUtils.isBlank(bean.getCodXSalvataggioConWarning());

			if (bean.getListaInvioMovimentiContabiliSICRA() != null && bean.getFlgSpesa() != null && _FLG_SI.equals(bean.getFlgSpesa()) && (isSetMovimentiAttoSicraOK || isSetMovimentiAttoSicraWARNWithoutMessageXml)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_SICRA_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaInvioMovimentiContabiliSICRA()));					
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
		
	private void aggiornaDatiEventoSICRA(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			if(StringUtils.isNotBlank(bean.getEventoSICRA())) { 
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_EVENTO_AMC_Doc", bean.getEventoSICRA() != null ? bean.getEventoSICRA() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ESITO_EVENTO_AMC_Doc", bean.getEsitoEventoSICRA() != null ? bean.getEsitoEventoSICRA() : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TS_EVENTO_AMC_Doc", bean.getDataEventoSICRA() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataEventoSICRA()) : "");
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ERR_MSG_EVENTO_AMC_Doc", bean.getErrMsgEventoSICRA() != null ? bean.getErrMsgEventoSICRA() : "");
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
	
	public static void populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBeanForFilePrimarioIntegrale(ProtocollazioneBean pProtocollazioneBean, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		if(pProtocollazioneBean != null && pNuovaPropostaAtto2CompletaBean != null) {
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setUriFilePrimario(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());
			pProtocollazioneBean.setNomeFilePrimario(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimario());
		}
	}
		
	public static void populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBeanForFilePrimarioIntegraleConOmissis(ProtocollazioneBean pProtocollazioneBean, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		if(pProtocollazioneBean != null && pNuovaPropostaAtto2CompletaBean != null) {
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pNuovaPropostaAtto2CompletaBean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
		}
	}
	
	public static void populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(ProtocollazioneBean pProtocollazioneBean, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		
		if(pProtocollazioneBean != null && pNuovaPropostaAtto2CompletaBean != null) {
			
			HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(pNuovaPropostaAtto2CompletaBean);

			/* Hidden */
			
			pProtocollazioneBean.setIdUd(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUd()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null);
			pProtocollazioneBean.setFlgTipoProv("I");
			pProtocollazioneBean.setFlgTipoDocDiverso(pNuovaPropostaAtto2CompletaBean.getFlgTipoDocDiverso());
			pProtocollazioneBean.setIdTipoDocDaCopiare(pNuovaPropostaAtto2CompletaBean.getIdTipoDocDaCopiare());
			pProtocollazioneBean.setTipoDocumento(pNuovaPropostaAtto2CompletaBean.getTipoDocumento());
			pProtocollazioneBean.setNomeTipoDocumento(pNuovaPropostaAtto2CompletaBean.getNomeTipoDocumento());
			pProtocollazioneBean.setRowidDoc(pNuovaPropostaAtto2CompletaBean.getRowidDoc());
			pProtocollazioneBean.setIdDocPrimario(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) : null);
			pProtocollazioneBean.setNomeFilePrimario(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimario());
			pProtocollazioneBean.setUriFilePrimario(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());
			pProtocollazioneBean.setRemoteUriFilePrimario(pNuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimario());
			pProtocollazioneBean.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario());
			pProtocollazioneBean.setIsDocPrimarioChanged(pNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario());
			pProtocollazioneBean.setFlgDatiSensibili(pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili());
			
			DocumentBean lFilePrimarioOmissis = new DocumentBean();			
			lFilePrimarioOmissis.setIdDoc(pNuovaPropostaAtto2CompletaBean.getIdDocPrimarioOmissis());
			lFilePrimarioOmissis.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimarioOmissis());
			lFilePrimarioOmissis.setUriFile(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
			lFilePrimarioOmissis.setRemoteUri(pNuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimarioOmissis());		
			lFilePrimarioOmissis.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis());
			lFilePrimarioOmissis.setIsChanged(pNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis());
			pProtocollazioneBean.setFilePrimarioOmissis(lFilePrimarioOmissis);
			
			/* Dati scheda - Registrazione */
			
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione())) {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2CompletaBean.getSiglaRegistrazione());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegistrazione()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2CompletaBean.getDataRegistrazione());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUserRegistrazione());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUORegistrazione());		
				pProtocollazioneBean.setSiglaNumerazioneSecondaria(pNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNumeroNumerazioneSecondaria(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataRegistrazioneNumerazioneSecondaria(pNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria());			
			} else {
				pProtocollazioneBean.setSiglaProtocollo(pNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria());
				pProtocollazioneBean.setNroProtocollo(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) ? new BigDecimal(pNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) : null);
				pProtocollazioneBean.setDataProtocollo(pNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria());
				pProtocollazioneBean.setDesUserProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUserRegProvvisoria());
				pProtocollazioneBean.setDesUOProtocollo(pNuovaPropostaAtto2CompletaBean.getDesUORegProvvisoria());	
			}
			
			/* Dati scheda - Ordinanza di mobilità */
			
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ALTRE_UBICAZIONI")) {
				String tipoLuogoOrdMobilita = "";
				if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_LUOGO_ORD_MOBILITA")) {
					tipoLuogoOrdMobilita = pNuovaPropostaAtto2CompletaBean.getTipoLuogoOrdMobilita() != null ? pNuovaPropostaAtto2CompletaBean.getTipoLuogoOrdMobilita() : "";
				}
				if (_TIPO_LUOGO_DA_TOPONOMASTICA.equalsIgnoreCase(tipoLuogoOrdMobilita)) {
					pProtocollazioneBean.setListaAltreVie(pNuovaPropostaAtto2CompletaBean.getListaIndirizziOrdMobilita());
				}
			}

			/* Dati scheda - Ruoli */
				
			// Ufficio proponente
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUfficioProponente())) {							
				pProtocollazioneBean.setUoProtocollante("UO" + pNuovaPropostaAtto2CompletaBean.getUfficioProponente());
				pProtocollazioneBean.setIdUoProponente(pNuovaPropostaAtto2CompletaBean.getUfficioProponente());
			}
			
			/* Dati scheda - Oggetto */
			
			if(isPresenteAttributoCustomCablato(setAttributiCustomCablati, "DES_OGG") && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getDesOgg())) {
				pProtocollazioneBean.setOggetto(pNuovaPropostaAtto2CompletaBean.getDesOgg());
			} else if(!isPresenteAttributoCustomCablato(setAttributiCustomCablati, "NASCONDI_OGGETTO")) {
				pProtocollazioneBean.setOggetto(estraiTestoOmissisDaHtml(pNuovaPropostaAtto2CompletaBean.getOggettoHtml()));
			}
			
			/* Dati scheda - Atto di riferimento */
			
			/*
			List<DocCollegatoBean> listaDocumentiDaCollegare = new ArrayList<DocCollegatoBean>();
			if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUdAttoDeterminaAContrarre())) {
				DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
				lDocCollegatoBean.setFlgPresenteASistema(pNuovaPropostaAtto2CompletaBean.getFlgAttoRifASistema());
				lDocCollegatoBean.setIdUdCollegata(pNuovaPropostaAtto2CompletaBean.getIdUdAttoDeterminaAContrarre());
				lDocCollegatoBean.setTipo(pNuovaPropostaAtto2CompletaBean.getCategoriaRegAttoDeterminaAContrarre());
				lDocCollegatoBean.setSiglaRegistro(pNuovaPropostaAtto2CompletaBean.getSiglaAttoDeterminaAContrarre());
				lDocCollegatoBean.setNumero(pNuovaPropostaAtto2CompletaBean.getNumeroAttoDeterminaAContrarre());
				lDocCollegatoBean.setAnno(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre()) ? new Integer(pNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre()) : null);			
				listaDocumentiDaCollegare.add(lDocCollegatoBean);				
			}
			pProtocollazioneBean.setListaDocumentiDaCollegare(listaDocumentiDaCollegare);
			*/
			
			List<DocCollegatoBean> listaDocumentiDaCollegare = new ArrayList<DocCollegatoBean>();
			if(showAttributoCustomCablato(setAttributiCustomCablati, "ATTO_RIFERIMENTO")) {								
				if (pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento() != null && pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size() > 0) {
					for(int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size(); i++) {
						AttoRiferimentoBean lAttoRiferimentoBean = pNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().get(i);
						DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
						lDocCollegatoBean.setFlgPresenteASistema(lAttoRiferimentoBean.getFlgPresenteASistema());
						if(lAttoRiferimentoBean.getFlgPresenteASistema() != null && _FLG_NO.equals(lAttoRiferimentoBean.getFlgPresenteASistema())) {
							// non è un atto presente a sistema quindi non devo passare ne la categoria ne l'idUd
						} else {
							lDocCollegatoBean.setTipo(lAttoRiferimentoBean.getCategoriaReg());
							lDocCollegatoBean.setIdUdCollegata(lAttoRiferimentoBean.getIdUd());
						}
						lDocCollegatoBean.setIdTipoDoc(lAttoRiferimentoBean.getTipoAttoRif());
						lDocCollegatoBean.setNomeTipoDoc(lAttoRiferimentoBean.getNomeTipoAttoRif());	
						lDocCollegatoBean.setSiglaRegistro(lAttoRiferimentoBean.getSigla());
						lDocCollegatoBean.setNumero(lAttoRiferimentoBean.getNumero());
						lDocCollegatoBean.setAnno(StringUtils.isNotBlank(lAttoRiferimentoBean.getAnno()) ? new Integer(lAttoRiferimentoBean.getAnno()) : null);			
						listaDocumentiDaCollegare.add(lDocCollegatoBean);	
					}						
				}
			}
			pProtocollazioneBean.setListaDocumentiDaCollegare(listaDocumentiDaCollegare);
			
			// metto a null gli estremi dell'atto di riferimento o in salvataggio me lo mette doppio nei documenti collegati
			pProtocollazioneBean.setSiglaDocRiferimento(null);
			pProtocollazioneBean.setNroDocRiferimento(null);
			pProtocollazioneBean.setAnnoDocRiferimento(null);
						
			/* Dati scheda - Classificazione e fascicolazione */
			
			if(isPresenteAttributoCustomCablato(setAttributiCustomCablati, "CLASS_FASC")) {
				pProtocollazioneBean.setListaClassFasc(pNuovaPropostaAtto2CompletaBean.getListaClassFasc());
			}
			
			/* Dati spesa corrente - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegatiCorrente());			

			/* Dati spesa in conto capitale - Opzioni */
			
//			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegatiContoCapitale());	
			
			/* Allegati */
			
			pProtocollazioneBean.setListaAllegati(pNuovaPropostaAtto2CompletaBean.getListaAllegati());
			
			/* Documenti fascicolo */
						
			if(isPresenteAttributoCustomCablato(setAttributiCustomCablati, "DOC_FASC")) {
				pProtocollazioneBean.setListaDocProcFolder(pNuovaPropostaAtto2CompletaBean.getListaDocFasc());
			}
		}		
	}

	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici) throws Exception {
		salvaAttributiCustomProposta(bean, sezioneCacheAttributiDinamici, false);
	}
	
	protected void salvaAttributiCustomProposta(NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean flgGenModello) throws Exception {

		HashSet<String> setAttributiCustomCablati = null;
		if(flgGenModello) {
			// se sono in generazione da modello passo tutti gli attributi, perchè quando faccio l'anteprima da lista non chiamo la CallExecAtt
		} else {
			// se sono in salvataggio devo passare solo gli attributi cablati che si vedono a maschera
			setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		}
		
		boolean skipCtrlAttributiCustomCablati = setAttributiCustomCablati == null;  // Se flgGenModello è false come minimo setAttributiCustomCablati è una lista vuota
		
		String msgTaskProvvisorio = bean.getMsgTaskProvvisorio() != null ? bean.getMsgTaskProvvisorio() : "";
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MSG_TASK_PROVVISORIO_Ud", msgTaskProvvisorio);
		
		String flgAggiornataVersDaPubblicare = bean.getFlgAggiornataVersDaPubblicare() != null && bean.getFlgAggiornataVersDaPubblicare() ? "1" : "";
		if("1".equals(flgAggiornataVersDaPubblicare)) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_AGGIORNATA_VERS_PUBBL_Doc", flgAggiornataVersDaPubblicare);
		}
		
		String flgForzataModificaAtto = bean.getFlgForzataModificaAtto() != null && bean.getFlgForzataModificaAtto() ? "1" : "";
		if("1".equals(flgForzataModificaAtto)) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_FORZATA_MODIFICA_ATTO_Ud", flgForzataModificaAtto);
		}
		
		String flgDettRevocaAtto = bean.getFlgDettRevocaAtto() != null && bean.getFlgDettRevocaAtto() ? "1" : "";
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DET_ANN_REVOCA_Doc", flgDettRevocaAtto);
		
		if (!flgGenModello && StringUtils.isNotBlank(bean.getUriDocGeneratoFormatoOdt())) {
			try {
				File fileOdtGenerato = File.createTempFile("temp", ".odt");
				InputStream lInputStream = StorageImplementation.getStorage().extract(bean.getUriDocGeneratoFormatoOdt());
				FileUtils.copyInputStreamToFile(lInputStream, fileOdtGenerato);
				File fileDocGenerato = ModelliUtil.convertToDoc(fileOdtGenerato);
				String md5FileDocGenerato = getMd5StringFormFile(fileDocGenerato);
				FileSavedIn lFileSavedIn = new FileSavedIn();
				lFileSavedIn.setSaved(fileDocGenerato);
				SalvataggioFile lSalvataggioFile = new SalvataggioFile();
				FileSavedOut out = lSalvataggioFile.savefile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileSavedIn);
				if(out.getErrorInSaved() != null) {
					throw new Exception(out.getErrorInSaved());
				}
				DocumentBean fileDocDocumentBean = new DocumentBean();
				if (StringUtils.isNotBlank(bean.getNomeFilePrimario())){
					fileDocDocumentBean.setNomeFile(FilenameUtils.getBaseName(bean.getNomeFilePrimario()) + ".doc");
				} else {
					fileDocDocumentBean.setNomeFile("ATTO_COMPLETO.doc");
				}
				fileDocDocumentBean.setUriFile(out.getUri());
				String idUdFileDoc = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, fileDocDocumentBean, getSession());
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_DOC_Ud", idUdFileDoc);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_MD5_Ud", md5FileDocGenerato);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new StoreException("Si è verificato un errore durante il salvataggio della versione .doc del dispositivo atto");
			}
		}
		
		/* Dati scheda - Specificità del provvedimento */
		
		// Spesa 
		
		String flgSpesa = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA"))) {
			flgSpesa = bean.getFlgSpesa() != null ? bean.getFlgSpesa() : "";
			if("".equals(flgSpesa)) {
				throw new StoreException("Non è possibile non valorizzare il flag con o senza rilevanza contabile.");
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_Doc", flgSpesa);
		} else {
			throw new StoreException("Errore di configurazione: obbligatorio configurare il flag di atto con spesa.");
		}
		
		// Dati rilevanti GSA 
		
		String flgDatiRilevantiGSA = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RIL_GSA"))) {
			flgDatiRilevantiGSA = bean.getFlgDatiRilevantiGSA() != null ? bean.getFlgDatiRilevantiGSA() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RIL_GSA_Doc", flgDatiRilevantiGSA);
		}
		
		// Uffici competenti Rag
		List<KeyValueBean> listaUfficiCompetentiRag = new ArrayList<KeyValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "UFF_COMPETENTE_RAG"))) {
			if(_FLG_SI.equalsIgnoreCase(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {				
				if(bean.getListaUfficiCompetentiRag() != null) {
					for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaUfficiCompetentiRag()) {
						if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
							KeyValueBean lKValueBean = new KeyValueBean();
							lKValueBean.setKey(lSimpleKeyValueBean.getKey());
							lKValueBean.setValue(lSimpleKeyValueBean.getValue());
							listaUfficiCompetentiRag.add(lKValueBean);						
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "UFF_COMPETENTE_RAG_Doc", new XmlUtilitySerializer().createVariabileLista(listaUfficiCompetentiRag)); 
		}
		
		/* Dati scheda - Emenda */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_EMANDAMENTO"))) {

			String tipoAttoEmendamento = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_TIPO_ATTO"))) {
				tipoAttoEmendamento = bean.getTipoAttoEmendamento() != null ? bean.getTipoAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_TIPO_ATTO_Doc", tipoAttoEmendamento);		
			}
		
			String siglaAttoEmendamento = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_SIGLA_REG"))) {
				siglaAttoEmendamento = bean.getSiglaAttoEmendamento() != null ? bean.getSiglaAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_SIGLA_REG_Doc", siglaAttoEmendamento);		
			}
		
			String numeroAttoEmendamento = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_NRO"))) {
				numeroAttoEmendamento = bean.getNumeroAttoEmendamento() != null ? bean.getNumeroAttoEmendamento() : "";	
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_NRO_Doc", numeroAttoEmendamento);		
			}
		
			String annoAttoEmendamento = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ATTO_ANNO"))) {
				annoAttoEmendamento = bean.getAnnoAttoEmendamento() != null ? bean.getAnnoAttoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ATTO_ANNO_Doc", annoAttoEmendamento);				
			}
			
			String idEmendamento = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_ID"))) {
				idEmendamento = bean.getIdEmendamento() != null ? bean.getIdEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_ID_Doc", idEmendamento);		
			}
		
			String numeroEmendamento = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SUB_SU_EM_NRO"))) {
				numeroEmendamento = bean.getNumeroEmendamento() != null ? bean.getNumeroEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SUB_SU_EM_NRO_Doc", numeroEmendamento);		
			}
		
			String flgEmendamentoSuFile = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_FILE"))) {
				flgEmendamentoSuFile = bean.getFlgEmendamentoSuFile() != null ? bean.getFlgEmendamentoSuFile() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_FILE_Doc", flgEmendamentoSuFile);		
			}
		
			String numeroAllegatoEmendamento = ""; 		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_ALLEGATO_NRO"))) {
				if (_FLG_EMENDAMENTO_SU_FILE_A.equals(flgEmendamentoSuFile)) {
					numeroAllegatoEmendamento = bean.getNumeroAllegatoEmendamento() != null ? bean.getNumeroAllegatoEmendamento() : ""; 
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_ALLEGATO_NRO_Doc", numeroAllegatoEmendamento);
			}
		
			String flgEmendamentoIntegraleAllegato = "";	
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_INTEGRALE_ALLEGATO"))) {
				if (_FLG_EMENDAMENTO_SU_FILE_A.equals(flgEmendamentoSuFile)) {
					flgEmendamentoIntegraleAllegato = bean.getFlgEmendamentoIntegraleAllegato() != null && bean.getFlgEmendamentoIntegraleAllegato() ? "1" : "";	
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_INTEGRALE_ALLEGATO_Doc", flgEmendamentoIntegraleAllegato);	
			}
		
			String numeroPaginaEmendamento = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_PAGINA"))) {
				if (!"1".equals(flgEmendamentoIntegraleAllegato)) {
					numeroPaginaEmendamento = bean.getNumeroPaginaEmendamento() != null ? bean.getNumeroPaginaEmendamento() : "";
				}	
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_PAGINA_Doc", numeroPaginaEmendamento);
			}
		
			String numeroRigaEmendamento = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_SU_RIGA"))) {
				if (!"1".equals(flgEmendamentoIntegraleAllegato)) {
					numeroRigaEmendamento = bean.getNumeroRigaEmendamento() != null ? bean.getNumeroRigaEmendamento() : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_SU_RIGA_Doc", numeroRigaEmendamento);		
			}
	    
			String effettoEmendamento = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EMENDAMENTO_EFFETTO"))) {
				effettoEmendamento = bean.getEffettoEmendamento() != null ? bean.getEffettoEmendamento() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EMENDAMENTO_EFFETTO_Doc", effettoEmendamento);
			}
		
		}

		/* Dati scheda - Destinatari */		
		
		String flgAttivaDestinatari = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI"))) {
			flgAttivaDestinatari = bean.getFlgAttivaDestinatari() != null && bean.getFlgAttivaDestinatari() ? "1" : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ATTIVA_SEZIONE_DESTINATARI_Doc", flgAttivaDestinatari);		
		}
		
		List<DestinatarioAttoBean> listaDestinatariAtto = new ArrayList<DestinatarioAttoBean>();					
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_ATTO"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI")) || "1".equals(flgAttivaDestinatari)) {
				if(bean.getListaDestinatariAtto() != null) {
					for(DestAttoBean lDestAttoBean : bean.getListaDestinatariAtto()) {
						if(StringUtils.isNotBlank(lDestAttoBean.getPrefisso()) || StringUtils.isNotBlank(lDestAttoBean.getDenominazione()) || StringUtils.isNotBlank(lDestAttoBean.getIndirizzo()) || StringUtils.isNotBlank(lDestAttoBean.getCorteseAttenzione()) || StringUtils.isNotBlank(lDestAttoBean.getIdSoggRubrica())) {
							DestinatarioAttoBean lDestinatarioAttoBean = new DestinatarioAttoBean();
							lDestinatarioAttoBean.setPrefisso(lDestAttoBean.getPrefisso());
							lDestinatarioAttoBean.setDenominazione(lDestAttoBean.getDenominazione());
							lDestinatarioAttoBean.setIndirizzo(lDestAttoBean.getIndirizzo());
							lDestinatarioAttoBean.setCorteseAttenzione(lDestAttoBean.getCorteseAttenzione());
							lDestinatarioAttoBean.setIdSoggRubrica(lDestAttoBean.getIdSoggRubrica());
							listaDestinatariAtto.add(lDestinatarioAttoBean);
						}
					}
				}	
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariAtto));		
		}
		
		List<DestinatarioAttoBean> listaDestinatariPCAtto = new ArrayList<DestinatarioAttoBean>();					
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_PC_ATTO"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "ATTIVA_SEZIONE_DESTINATARI")) || "1".equals(flgAttivaDestinatari)) {
				if(bean.getListaDestinatariPCAtto() != null) {
					for(DestAttoBean lDestPCAttoBean : bean.getListaDestinatariPCAtto()) {
						if(StringUtils.isNotBlank(lDestPCAttoBean.getPrefisso()) || StringUtils.isNotBlank(lDestPCAttoBean.getDenominazione()) || StringUtils.isNotBlank(lDestPCAttoBean.getIndirizzo()) || StringUtils.isNotBlank(lDestPCAttoBean.getCorteseAttenzione()) || StringUtils.isNotBlank(lDestPCAttoBean.getIdSoggRubrica())) {
							DestinatarioAttoBean lDestinatarioPCAttoBean = new DestinatarioAttoBean();
							lDestinatarioPCAttoBean.setPrefisso(lDestPCAttoBean.getPrefisso());
							lDestinatarioPCAttoBean.setDenominazione(lDestPCAttoBean.getDenominazione());
							lDestinatarioPCAttoBean.setIndirizzo(lDestPCAttoBean.getIndirizzo());
							lDestinatarioPCAttoBean.setCorteseAttenzione(lDestPCAttoBean.getCorteseAttenzione());
							lDestinatarioPCAttoBean.setIdSoggRubrica(lDestPCAttoBean.getIdSoggRubrica());
							listaDestinatariPCAtto.add(lDestinatarioPCAttoBean);
						}
					}
				}				
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_PC_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariPCAtto));		
		}
						
		/* Dati scheda - Tipo proposta */
		
		String iniziativaProposta = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO"))) {
			iniziativaProposta = bean.getIniziativaProposta() != null ? bean.getIniziativaProposta() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO_Doc", iniziativaProposta);
		}
		
		String flgAttoMeroIndirizzo = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_MERO_INDIRIZZO"))) {
			flgAttoMeroIndirizzo = bean.getFlgAttoMeroIndirizzo() != null && bean.getFlgAttoMeroIndirizzo() ? "1" : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_ATTO_MERO_INDIRIZZO_Doc", flgAttoMeroIndirizzo);		
		}
		
		String flgAttoCommissario = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_COMMISSARIO"))) {
			flgAttoCommissario = bean.getFlgAttoCommissario() != null && bean.getFlgAttoCommissario() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_ATTO_COMMISSARIO_Doc", flgAttoCommissario);
		}
		
		String flgModificaRegolamento = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_MODIFICA_REGOLAMENTO"))) {
			flgModificaRegolamento = bean.getFlgModificaRegolamento() != null && bean.getFlgModificaRegolamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_MODIFICA_REGOLAMENTO_Doc", flgModificaRegolamento);
		}
		
		String flgModificaStatuto = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_MODIFICA_STATUTO"))) {
			flgModificaStatuto = bean.getFlgModificaStatuto() != null && bean.getFlgModificaStatuto() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_MODIFICA_STATUTO_Doc", flgModificaStatuto); 
		}
		
		String flgNomina = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DEL_NOMINA"))) {
			flgNomina = bean.getFlgNomina() != null && bean.getFlgNomina() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DEL_NOMINA_Doc", flgNomina); 
		}
		
		String flgRatificaDeliberaUrgenza = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RATIFICA_DEL_URGENZA"))) {
			flgRatificaDeliberaUrgenza = bean.getFlgRatificaDeliberaUrgenza() != null && bean.getFlgRatificaDeliberaUrgenza() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RATIFICA_DEL_URGENZA_Doc", flgRatificaDeliberaUrgenza); 
		}
		
		String flgAttoUrgente = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_URGENTE"))) {
			flgAttoUrgente = bean.getFlgAttoUrgente() != null && bean.getFlgAttoUrgente() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_ATTO_URGENTE_Doc", flgAttoUrgente);		
		}
		
		String flgCommissioniTipoProposta = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_X_COMMISSIONI") && !showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_COMMISSIONI"))) {
			flgCommissioniTipoProposta = bean.getFlgCommissioniTipoProposta() != null && bean.getFlgCommissioniTipoProposta() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_X_COMMISSIONI_Doc", flgCommissioniTipoProposta);		
		}
		
		/* Dati scheda - Circoscrizioni proponenti delibera */
		
		List<SimpleValueBean> listaCircoscrizioni = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CIRCOSCRIZIONE_PROPONENTE"))) {
			if("CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta)) {
				if(bean.getListaCircoscrizioni() != null) {
					for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaCircoscrizioni()) {
						if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
							listaCircoscrizioni.add(lSimpleValueBean);						
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIRCOSCRIZIONE_PROPONENTE_Doc", new XmlUtilitySerializer().createVariabileLista(listaCircoscrizioni)); 
		}
		
		/* Dati scheda - Interpellanza */
		
		String tipoInterpellanza = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_INTERPELLANZA"))) {
			tipoInterpellanza = bean.getTipoInterpellanza() != null ? bean.getTipoInterpellanza() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_INTERPELLANZA_Doc", tipoInterpellanza);		
		}
		
		String motivazioneInterpellanzaRispScritta = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVAZIONE_INT_RISP_SCRITTA"))) {
			motivazioneInterpellanzaRispScritta = bean.getMotivazioneInterpellanzaRispScritta() != null ? bean.getMotivazioneInterpellanzaRispScritta() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVAZIONE_INT_RISP_SCRITTA_Doc", motivazioneInterpellanzaRispScritta);		
		}
		
		/* Dati scheda - Ordinanza di mobilità */
		
		String tipoOrdMobilita = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_ORD_MOBILITA"))) {
			tipoOrdMobilita = bean.getTipoOrdMobilita() != null ? bean.getTipoOrdMobilita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_ORD_MOBILITA_Doc", tipoOrdMobilita); 
		}
		
		String dataInizioVldOrdinanza = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "INIZIO_VLD_ORDINANZA"))) {
			dataInizioVldOrdinanza = bean.getDataInizioVldOrdinanza() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataInizioVldOrdinanza()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "INIZIO_VLD_ORDINANZA_Doc", dataInizioVldOrdinanza); 
		}
		
		String dataFineVldOrdinanza = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FINE_VLD_ORDINANZA"))) {
			dataFineVldOrdinanza = bean.getDataFineVldOrdinanza() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataFineVldOrdinanza()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FINE_VLD_ORDINANZA_Doc", dataFineVldOrdinanza); 
		}
		
		String tipoLuogoOrdMobilita = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_LUOGO_ORD_MOBILITA"))) {
			tipoLuogoOrdMobilita = bean.getTipoLuogoOrdMobilita() != null ? bean.getTipoLuogoOrdMobilita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_LUOGO_ORD_MOBILITA_Doc", tipoLuogoOrdMobilita); 	
		}
		
		String luogoOrdMobilita = "";		
		String luogoOrdMobilitaFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LUOGO_ORD_MOBILITA"))) {
			if (_TIPO_LUOGO_TESTO_LIBERO.equalsIgnoreCase(tipoLuogoOrdMobilita)) {
				luogoOrdMobilita = bean.getLuogoOrdMobilita() != null ? bean.getLuogoOrdMobilita() : "";
				luogoOrdMobilitaFile = bean.getLuogoOrdMobilitaFile() != null ? bean.getLuogoOrdMobilitaFile() : "";
			}
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "LUOGO_ORD_MOBILITA_Doc", luogoOrdMobilita, "LUOGO_ORD_MOBILITA_FILE_Doc", luogoOrdMobilitaFile); 		
		}
		
		List<SimpleValueBean> listaCircoscrizioniOrdMobilita = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CIRCOSCRIZIONE_ORD_MOBILITA"))) {
			if(bean.getListaCircoscrizioniOrdMobilita() != null) {
				for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaCircoscrizioniOrdMobilita()) {
					if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
						listaCircoscrizioniOrdMobilita.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIRCOSCRIZIONE_ORD_MOBILITA_Doc", new XmlUtilitySerializer().createVariabileLista(listaCircoscrizioniOrdMobilita)); 
		}
		
		String descrizioneOrdMobilita = "";
		String descrizioneOrdMobilitaFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DESCRIZIONE_ORD_MOBILITA"))) {
			descrizioneOrdMobilita = bean.getDescrizioneOrdMobilita() != null ? bean.getDescrizioneOrdMobilita() : "";
			descrizioneOrdMobilitaFile = bean.getDescrizioneOrdMobilitaFile() != null ? bean.getDescrizioneOrdMobilitaFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "DESCRIZIONE_ORD_MOBILITA_Doc", descrizioneOrdMobilita, "DESCRIZIONE_ORD_MOBILITA_FILE_Doc", descrizioneOrdMobilitaFile); 
		}
		
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
		
		// Proponenti
		
		List<ProponentiXmlBean> listaProponenti = new ArrayList<ProponentiXmlBean>();		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PROPONENTI"))) {
			if(bean.getListaProponenti() != null) {
				for(ProponentiBean lProponentiBean : bean.getListaProponenti()) {
					if(StringUtils.isNotBlank(lProponentiBean.getIdUo())) {
						ProponentiXmlBean lProponentiXmlBean = new ProponentiXmlBean();
						lProponentiXmlBean.setIdUo(lProponentiBean.getIdUo());
						if(lProponentiBean.getListaRdP() != null && lProponentiBean.getListaRdP().size() > 0) {
							lProponentiXmlBean.setIdScrivaniaRdP(lProponentiBean.getListaRdP().get(0).getIdScrivania());
						}
						if(lProponentiBean.getListaDirigenti() != null && lProponentiBean.getListaDirigenti().size() > 0) {
							lProponentiXmlBean.setIdScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(0).getIdScrivania());
							lProponentiXmlBean.setTipoVistoScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(0).getTipoVistoScrivania());
							lProponentiXmlBean.setFlgForzaDispFirmaScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(0).getFlgForzaDispFirmaScrivania() != null && lProponentiBean.getListaDirigenti().get(0).getFlgForzaDispFirmaScrivania() ? "1" : null);														
						}						
						if(lProponentiBean.getListaDirettori() != null && lProponentiBean.getListaDirettori().size() > 0) {
							lProponentiXmlBean.setIdScrivaniaDirettore(lProponentiBean.getListaDirettori().get(0).getIdScrivania());
							lProponentiXmlBean.setTipoVistoScrivaniaDirettore(lProponentiBean.getListaDirettori().get(0).getTipoVistoScrivania());
							lProponentiXmlBean.setFlgForzaDispFirmaScrivaniaDirettore(lProponentiBean.getListaDirettori().get(0).getFlgForzaDispFirmaScrivania() != null && lProponentiBean.getListaDirettori().get(0).getFlgForzaDispFirmaScrivania() ? "1" : null);														
						}
//						lProponentiXmlBean.setIdScrivaniaDirettore(lProponentiBean.getIdScrivaniaDirettore());	
//						lProponentiXmlBean.setTipoVistoScrivaniaDirettore(lProponentiBean.getTipoVistoScrivaniaDirettore());
//						lProponentiXmlBean.setFlgForzaDispFirmaScrivaniaDirettore(lProponentiBean.getFlgForzaDispFirmaScrivaniaDirettore() != null && lProponentiBean.getFlgForzaDispFirmaScrivaniaDirettore() ? "1" : null);
						listaProponenti.add(lProponentiXmlBean);
						if(lProponentiBean.getListaRdP() != null && lProponentiBean.getListaRdP().size() > 1) {
							for(int i = 1; i < lProponentiBean.getListaRdP().size(); i++) {
								ProponentiXmlBean lProponentiXmlBeanAggiuntaRdP = new ProponentiXmlBean();
								lProponentiXmlBeanAggiuntaRdP.setIdUo(lProponentiBean.getIdUo());
								lProponentiXmlBeanAggiuntaRdP.setIdScrivaniaRdP(lProponentiBean.getListaRdP().get(i).getIdScrivania());	
								lProponentiXmlBeanAggiuntaRdP.setFlgAggiuntaRdP("1");
								listaProponenti.add(lProponentiXmlBeanAggiuntaRdP);
							}
						}
						if(lProponentiBean.getListaDirigenti() != null && lProponentiBean.getListaDirigenti().size() > 1) {
							for(int i = 1; i < lProponentiBean.getListaDirigenti().size(); i++) {
								ProponentiXmlBean lProponentiXmlBeanAggiuntaDirigente = new ProponentiXmlBean();
								lProponentiXmlBeanAggiuntaDirigente.setIdUo(lProponentiBean.getIdUo());
								lProponentiXmlBeanAggiuntaDirigente.setIdScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(i).getIdScrivania());
								lProponentiXmlBeanAggiuntaDirigente.setTipoVistoScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(i).getTipoVistoScrivania());
								lProponentiXmlBeanAggiuntaDirigente.setFlgForzaDispFirmaScrivaniaDirigente(lProponentiBean.getListaDirigenti().get(i).getFlgForzaDispFirmaScrivania() != null && lProponentiBean.getListaDirigenti().get(i).getFlgForzaDispFirmaScrivania() ? "1" : null);
								lProponentiXmlBeanAggiuntaDirigente.setFlgAggiuntaDirigente("1");
								listaProponenti.add(lProponentiXmlBeanAggiuntaDirigente);
							}
						}
					}
				}				
			}	
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "PROPONENTI_Ud", new XmlUtilitySerializer().createVariabileLista(listaProponenti));						
		}
		
		// Tipo provvedimento e sotto-tipo delibera
		
		String tipoProvvedimento = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_PROVVEDIMENTO"))) {
			tipoProvvedimento = bean.getTipoProvvedimento() != null ? bean.getTipoProvvedimento() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_PROVVEDIMENTO_Doc", tipoProvvedimento);
		}
		
		String sottotipoDelibera = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_SOTTOTIPO_DELIBERA"))) {
			if("delibera".equals(tipoProvvedimento)) {				
				sottotipoDelibera = bean.getSottotipoDelibera() != null ? bean.getSottotipoDelibera() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_SOTTOTIPO_DELIBERA_Doc", sottotipoDelibera);
		}
				
		// Ufficio gare acquisti
		
		String ufficioGareAcquisti = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_UO_GARE_ACQUISTI"))) {
			ufficioGareAcquisti = (bean.getListaUfficioGareAcquisti() != null && bean.getListaUfficioGareAcquisti().size() > 0) ? bean.getListaUfficioGareAcquisti().get(0).getUfficioGareAcquisti() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_UO_GARE_ACQUISTI_Ud", ufficioGareAcquisti);
		}		
		
		// Inerente il codice appalti

		String flgProcExCodAppalti = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI"))) {
			flgProcExCodAppalti = bean.getFlgProcExCodAppalti() != null ? bean.getFlgProcExCodAppalti() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI_Doc", flgProcExCodAppalti);
		}
		
		// Ufficio competente
		
		String ufficioCompetente = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_UO_COMPETENTE") && showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI"))) {
			ufficioCompetente = StringUtils.isNotBlank(bean.getUfficioCompetente()) ? bean.getUfficioCompetente() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_UO_COMPETENTE_Ud", ufficioCompetente);
		}				
				
		// Responsabile Unico Provvedimento codice appalti
		
		String responsabileUnicoProvvedimentoCodAppalti = "";
		String flgRUPCodAppaltiAncheAdottante = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI") && showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RUP"))) {
			if(_FLG_SI.equals(flgProcExCodAppalti)) {
				if(bean.getListaRUPCodAppalti() != null && bean.getListaRUPCodAppalti().size() > 0) {
					responsabileUnicoProvvedimentoCodAppalti = bean.getListaRUPCodAppalti().get(0).getResponsabileUnicoProvvedimento();	
					flgRUPCodAppaltiAncheAdottante = bean.getListaRUPCodAppalti().get(0).getFlgRUPAncheAdottante() != null && bean.getListaRUPCodAppalti().get(0).getFlgRUPAncheAdottante() ? "1" : "";
				}	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RUP_Ud", responsabileUnicoProvvedimentoCodAppalti);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_RUP_ANCHE_ADOTTANTE_Ud", flgRUPCodAppaltiAncheAdottante);
		}
				
		// Dir. adottante
		
		String dirigenteAdottante = "";
		String flgAdottanteAncheRdP = "";
		String flgAdottanteAncheRUP = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ADOTTANTE"))) {
			if ("1".equals(flgRUPCodAppaltiAncheAdottante)) { 
				dirigenteAdottante = responsabileUnicoProvvedimentoCodAppalti;						
			} else 	if(bean.getListaAdottante() != null && bean.getListaAdottante().size() > 0) {
				dirigenteAdottante = bean.getListaAdottante().get(0).getDirigenteAdottante();			
				flgAdottanteAncheRdP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRdP() ? "1" : "";
				flgAdottanteAncheRUP = bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() != null && bean.getListaAdottante().get(0).getFlgAdottanteAncheRUP() ? "1" : "";
			}		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ADOTTANTE_Ud", dirigenteAdottante);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RDP_Ud", flgAdottanteAncheRdP);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_ANCHE_RUP_Ud", flgAdottanteAncheRUP);
		}
		
		// Responsabile di Procedimento codice appalti
		
		String responsabileDiProcedimentoCodAppalti = "";
		String flgResponsabileDiProcedimentoCodAppaltiFirmatario = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI") && showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_PROC"))) {
			if(bean.getListaRdPCodAppalti() != null && bean.getListaRdPCodAppalti().size() > 0) {		
				responsabileDiProcedimentoCodAppalti = bean.getListaRdPCodAppalti().get(0).getResponsabileDiProcedimento();
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_FIRMA_RDP"))) {
					flgResponsabileDiProcedimentoCodAppaltiFirmatario = bean.getListaRdPCodAppalti().get(0).getFlgResponsabileDiProcedimentoFirmatario() != null && bean.getListaRdPCodAppalti().get(0).getFlgResponsabileDiProcedimentoFirmatario() ? "1" : "";
				}				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_PROC_Ud", responsabileDiProcedimentoCodAppalti);
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_FIRMA_RDP"))) {
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_FIRMA_RDP_Ud", flgResponsabileDiProcedimentoCodAppaltiFirmatario);
			}	
		}
		
		// Centro di Costo
		
		String centroDiCosto = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CDC_ATTO"))) {
			if (!_FLG_NO.equalsIgnoreCase(flgSpesa)) {	
				centroDiCosto = bean.getCentroDiCosto() != null ? bean.getCentroDiCosto() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "CDC_ATTO_Doc", centroDiCosto);
		}
				
		// Adottanti di concerto
		
		List<IdSVRespFirmatarioBean> listaDirigentiConcerto = new ArrayList<IdSVRespFirmatarioBean>();		
		String flgDeterminaConcerto = "";				
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_DI_CONCERTO"))) {
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
			flgDeterminaConcerto = listaDirigentiConcerto.size() > 0 ? "1" : "";				
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_DI_CONCERTO_Ud", new XmlUtilitySerializer().createVariabileLista(listaDirigentiConcerto));		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_DI_CONCERTO_Doc", flgDeterminaConcerto);		
		}
		
		// Dir. resp reg. tecnica
		
		String dirRespRegTecnica = "";
		String flgDirRespRegTecnicaAncheRdP = "";
		String flgDirRespRegTecnicaAncheRUP = "";		
		// Dir. resp reg. tecnica sostituto
		
		String dirRespRegTecnicaSostituto = "";
		String provvedimentoSostituto = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DIR_RESP_REG_TECNICA"))) {
			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaDirRespRegTecnica() != null && bean.getListaDirRespRegTecnica().size() > 0) {
					dirRespRegTecnica = bean.getListaDirRespRegTecnica().get(0).getDirigenteRespRegTecnica();			
					flgDirRespRegTecnicaAncheRdP = bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRdP() != null && bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRdP() ? "1" : "";
					flgDirRespRegTecnicaAncheRUP = bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRUP() != null && bean.getListaDirRespRegTecnica().get(0).getFlgDirRespRegTecnicaAncheRUP() ? "1" : "";
					dirRespRegTecnicaSostituto = (skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA"))) ? bean.getListaDirRespRegTecnica().get(0).getDirigenteRespRegTecnicaSostituto() : "";
					provvedimentoSostituto = (skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA"))) ? bean.getListaDirRespRegTecnica().get(0).getProvvedimentoSostituto() : "";
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_DIR_RESP_REG_TECNICA_Ud", dirRespRegTecnica);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DIR_ANCHE_RDP_Ud", flgDirRespRegTecnicaAncheRdP);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DIR_ANCHE_RUP_Ud", flgDirRespRegTecnicaAncheRUP);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_SOSTITUTO_DIR_RESP_REG_TECNICA_Ud", dirRespRegTecnicaSostituto);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PROVV_SOSTITUZIONE_DIR_RESP_REG_TECNICA_Ud", provvedimentoSostituto);
		}
				
		// Altri pareri reg. tecnica
		salvaAttributiCustomAltriDirRegRespTecnica(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, flgAttoMeroIndirizzo, skipCtrlAttributiCustomCablati);
		
		// Responsabile di Procedimento		
		String responsabileDiProcedimento = "";
		String flgResponsabileDiProcedimentoFirmatario = "";
		String flgRdPAncheRUP = "";
		if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI") && showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_PROC"))) {
			if ("1".equals(flgAdottanteAncheRdP)) { 
				responsabileDiProcedimento = dirigenteAdottante;						
			} else if("1".equals(flgDirRespRegTecnicaAncheRdP)) {
				responsabileDiProcedimento = dirRespRegTecnica;
			} else if(bean.getListaRdP() != null && bean.getListaRdP().size() > 0) {		
				responsabileDiProcedimento = bean.getListaRdP().get(0).getResponsabileDiProcedimento();
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_FIRMA_RDP"))) {
					flgResponsabileDiProcedimentoFirmatario = bean.getListaRdP().get(0).getFlgResponsabileDiProcedimentoFirmatario() != null && bean.getListaRdP().get(0).getFlgResponsabileDiProcedimentoFirmatario() ? "1" : "";
				}
				flgRdPAncheRUP = bean.getListaRdP().get(0).getFlgRdPAncheRUP() != null && bean.getListaRdP().get(0).getFlgRdPAncheRUP() ? "1" : "";			
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_PROC_Ud", responsabileDiProcedimento);
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_FIRMA_RDP"))) {
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_FIRMA_RDP_Ud", flgResponsabileDiProcedimentoFirmatario);
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_RDP_ANCHE_RUP_Ud", flgRdPAncheRUP);
		}
		
		// Procedimento
		
		String codProcedimento = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "COD_PROCEDIMENTO"))) {
			codProcedimento = bean.getCodProcedimento() != null ? bean.getCodProcedimento() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_PROCEDIMENTO_Doc", codProcedimento);			
		}
		
		// Responsabile Unico Provvedimento
		
		String responsabileUnicoProvvedimento = ""; 
		if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI") && showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RUP"))) {
			if ("1".equals(flgAdottanteAncheRUP)) { 
				responsabileUnicoProvvedimento = dirigenteAdottante;						
			} else if ("1".equals(flgDirRespRegTecnicaAncheRUP)) { 
				responsabileUnicoProvvedimento = dirRespRegTecnica;						
			} else if ("1".equals(flgRdPAncheRUP)) { 
				responsabileUnicoProvvedimento = responsabileDiProcedimento;						
			} else if(bean.getListaRUP() != null && bean.getListaRUP().size() > 0) {
				responsabileUnicoProvvedimento = bean.getListaRUP().get(0).getResponsabileUnicoProvvedimento();	
			}	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RUP_Ud", responsabileUnicoProvvedimento);		
		}
		
		// DEC
		
		String idScrivaniaDEC = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DEC"))) {
			idScrivaniaDEC = (bean.getListaScrivaniaDEC() != null && bean.getListaScrivaniaDEC().size() > 0) ? bean.getListaScrivaniaDEC().get(0).getScrivaniaDEC() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_DEC_Ud", idScrivaniaDEC);		
		}
		
		// Assessore proponente
		
		String assessoreProponente = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_ASSESSORE_PROPONENTE"))) {
			assessoreProponente = (bean.getListaAssessori() != null && bean.getListaAssessori().size() > 0) ? bean.getListaAssessori().get(0).getAssessore() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_ASSESSORE_PROPONENTE_Ud", assessoreProponente);		
		}
			
		// Altri assessori
		
		List<IdSVRespFirmatarioBean> listaAltriAssessori = new ArrayList<IdSVRespFirmatarioBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_ALTRI_ASSESSORI"))) {
			if(bean.getListaAltriAssessori() != null) {
				for(AssessoreBean lAssessoreBean : bean.getListaAltriAssessori()) {
					if(StringUtils.isNotBlank(lAssessoreBean.getAssessore())) {
						IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
						lIdSVRespFirmatarioBean.setIdSV(lAssessoreBean.getAssessore());
						lIdSVRespFirmatarioBean.setFlgFirmatario(lAssessoreBean.getFlgAssessoreFirmatario());
						listaAltriAssessori.add(lIdSVRespFirmatarioBean);
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_ALTRI_ASSESSORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriAssessori));		
		}
		
		// Proponente
		
		String proponenteAttoConsiglio = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_PROPONENTE_ATTO_CONSIGLIO"))) {
			proponenteAttoConsiglio = (bean.getListaProponenteAttoConsiglio() != null && bean.getListaProponenteAttoConsiglio().size() > 0) ?  bean.getListaProponenteAttoConsiglio().get(0).getProponenteAttoConsiglio() : "";				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_PROPONENTE_ATTO_CONSIGLIO_Ud", proponenteAttoConsiglio);		
		}

		// Consigliere proponente
		
		String consigliereProponente = "";
		String desConsigliereProponente = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_CONSIGLIERE_PROPONENTE"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO")) || (!"POPOLARE".equalsIgnoreCase(iniziativaProposta) && !"CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta))) {
				consigliereProponente = (bean.getListaConsiglieri() != null && bean.getListaConsiglieri().size() > 0) ?  bean.getListaConsiglieri().get(0).getConsigliere() : "";
				desConsigliereProponente = (bean.getListaConsiglieri() != null && bean.getListaConsiglieri().size() > 0) ?  bean.getListaConsiglieri().get(0).getDesConsigliere() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_CONSIGLIERE_PROPONENTE_Ud", consigliereProponente);		
		}			
		
		String flgFirmaInSostSindaco = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FIRMA_IN_SOST_SINDACO"))) {
			// non mi arriva la descrizione del consigliere quindi commento o mi va in NullPointerException
//			if(desConsigliereProponente.toUpperCase().contains("VICESINDAC") || desConsigliereProponente.toUpperCase().contains("VICE-SINDAC")) {
				if(bean.getListaConsiglieri() != null && bean.getListaConsiglieri().size() > 0) {				
					flgFirmaInSostSindaco = bean.getListaConsiglieri().get(0).getFlgConsigliereFirmaInSostSindaco() != null && bean.getListaConsiglieri().get(0).getFlgConsigliereFirmaInSostSindaco() ? "1" : "";
				}
//			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FIRMA_IN_SOST_SINDACO_Ud", flgFirmaInSostSindaco);		
		}
		
		// Altri consiglieri
				
		List<IdSVRespFirmatarioBean> listaAltriConsiglieri = new ArrayList<IdSVRespFirmatarioBean>();	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_ALTRI_CONSIGLIERI"))) {			
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INIZIATIVA_PROP_ATTO")) || (!"POPOLARE".equalsIgnoreCase(iniziativaProposta) && !"CIRCOSCRIZIONE".equalsIgnoreCase(iniziativaProposta))) {
				if(bean.getListaAltriConsiglieri() != null) {
					for(ConsigliereBean lConsigliereBean : bean.getListaAltriConsiglieri()) {
						if(StringUtils.isNotBlank(lConsigliereBean.getConsigliere())) {
							IdSVRespFirmatarioBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioBean();
							lIdSVRespFirmatarioBean.setIdSV(lConsigliereBean.getConsigliere());
							lIdSVRespFirmatarioBean.setFlgFirmatario(lConsigliereBean.getFlgConsigliereFirmatario());
							listaAltriConsiglieri.add(lIdSVRespFirmatarioBean);
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_ALTRI_CONSIGLIERI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriConsiglieri));		
		}
				
		// Data termine firme consiglieri
		salvaAttributiCustomTermineFirmeConsiglieri(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, skipCtrlAttributiCustomCablati);
		
		/*
		String dataTermFirmeConsiglieri = "";
		if(showAttributoCustomCablato(setAttributiCustomCablati, "DT_TERM_FIRME_CONSIGLIERI_COPROP")) {
			dataTermFirmeConsiglieri = bean.getDataTermFirmeConsiglieri() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataTermFirmeConsiglieri()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DT_TERM_FIRME_CONSIGLIERI_COPROP_Ud", dataTermFirmeConsiglieri); 
		}
		*/
		
		// Dirigente proponente
		
		String dirigenteProponente = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DIR_PROPONENTE"))) {
			dirigenteProponente = (bean.getListaDirigentiProponenti() != null && bean.getListaDirigentiProponenti().size() > 0) ?  bean.getListaDirigentiProponenti().get(0).getDirigenteProponente() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_DIR_PROPONENTE_Ud", dirigenteProponente);
		}
						
		// Altri dirigenti proponenti
		
		List<IdSVRespFirmatarioConMotiviBean> listaAltriDirProponenti = new ArrayList<IdSVRespFirmatarioConMotiviBean>();	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_DIR_PROPONENTI"))) {
			// Lo tolgo perchè dal documento delle specifiche non risulta che dovrebbero sparire. Se spunto mero indirizzo dovrebbero sparire solo ID_SV_DIR_RESP_REG_TECNICA e ID_SV_ALTRI_DIR_REG_TECNICA
//			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaAltriDirigentiProponenti() != null) {
					for(DirigenteProponenteBean lDirigenteProponenteBean : bean.getListaAltriDirigentiProponenti()) {
						if(StringUtils.isNotBlank(lDirigenteProponenteBean.getDirigenteProponente())) {
							IdSVRespFirmatarioConMotiviBean lIdSVRespFirmatarioConMotiviBean = new IdSVRespFirmatarioConMotiviBean();
							lIdSVRespFirmatarioConMotiviBean.setIdSV(lDirigenteProponenteBean.getDirigenteProponente());
							lIdSVRespFirmatarioConMotiviBean.setFlgFirmatario(lDirigenteProponenteBean.getFlgDirigenteProponenteFirmatario());
							lIdSVRespFirmatarioConMotiviBean.setMotivi(lDirigenteProponenteBean.getMotiviDirigenteProponente());
							listaAltriDirProponenti.add(lIdSVRespFirmatarioConMotiviBean);
						}
					}
				}
//			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_DIR_PROPONENTI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriDirProponenti));		
		}
				
		// Coordinatore competente per materia
		
		String coordinatoreCompCirc = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_COORDINATORE_COMP_CIRC"))) {
			coordinatoreCompCirc = (bean.getListaCoordinatoriCompCirc() != null && bean.getListaCoordinatoriCompCirc().size() > 0) ?  bean.getListaCoordinatoriCompCirc().get(0).getCoordinatoreCompCirc() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_COORDINATORE_COMP_CIRC_Ud", coordinatoreCompCirc);
		}
				
		// Richiedi visto Dir. sovraordinato
		
		String flgRichiediVistoDirettore = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP"))) {
			flgRichiediVistoDirettore = bean.getFlgRichiediVistoDirettore() != null && bean.getFlgRichiediVistoDirettore() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_Doc", flgRichiediVistoDirettore);			
		}
				
		// Visti di conformità
		
		List<IdSVRespVistoBean> listaRespVistiConformita = new ArrayList<IdSVRespVistoBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_VISTI_CONFORMITA"))) {
			if(bean.getListaRespVistiConformita() != null) {
				for(ResponsabileVistiConformitaBean lResponsabileVistiConformitaBean : bean.getListaRespVistiConformita()) {
					if(StringUtils.isNotBlank(lResponsabileVistiConformitaBean.getRespVistiConformita())) {
						IdSVRespVistoBean lIdSVRespVistoBean = new IdSVRespVistoBean();
						lIdSVRespVistoBean.setIdSV(lResponsabileVistiConformitaBean.getRespVistiConformita());
						lIdSVRespVistoBean.setFlgFirmatario(lResponsabileVistiConformitaBean.getFlgRespVistiConformitaFirmatario());
						lIdSVRespVistoBean.setMotivi(lResponsabileVistiConformitaBean.getMotiviRespVistiConformita());
						lIdSVRespVistoBean.setFlgRiacqVistoInRitornoIter(lResponsabileVistiConformitaBean.getFlgRiacqVistoInRitornoIterRespVistiConformita());
						listaRespVistiConformita.add(lIdSVRespVistoBean);	
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_VISTI_CONFORMITA_Ud", new XmlUtilitySerializer().createVariabileLista(listaRespVistiConformita));		
		}
		
		// Visti responsabile struttura
		
		String respUfficioProp = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_UO_PROP"))) {
			respUfficioProp = (bean.getListaRespUfficioProp() != null && bean.getListaRespUfficioProp().size() > 0) ?  bean.getListaRespUfficioProp().get(0).getRespUfficioProp() : "";			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_UO_PROP_Ud", respUfficioProp);		
		}
		
		// Visti di perfezionemento
		
		List<IdSVRespVistoBean> listaRespVistiPerfezionamento = new ArrayList<IdSVRespVistoBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_VISTI_PERFEZIONAMENTO"))) {
			if(bean.getListaRespVistiPerfezionamento() != null) {
				for(ResponsabileVistiPerfezionamentoBean lResponsabileVistiPerfezionamentoBean : bean.getListaRespVistiPerfezionamento()) {
					if(StringUtils.isNotBlank(lResponsabileVistiPerfezionamentoBean.getRespVistiPerfezionamento())) {
						IdSVRespVistoBean lIdSVRespVistoBean = new IdSVRespVistoBean();
						lIdSVRespVistoBean.setIdSV(lResponsabileVistiPerfezionamentoBean.getRespVistiPerfezionamento());
						lIdSVRespVistoBean.setFlgFirmatario(lResponsabileVistiPerfezionamentoBean.getFlgRespVistiPerfezionamentoFirmatario());
						lIdSVRespVistoBean.setMotivi(lResponsabileVistiPerfezionamentoBean.getMotiviRespVistiPerfezionamento());
						lIdSVRespVistoBean.setFlgRiacqVistoInRitornoIter(lResponsabileVistiPerfezionamentoBean.getFlgRiacqVistoInRitornoIterRespVistiPerfezionamento());
						listaRespVistiPerfezionamento.add(lIdSVRespVistoBean);	
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_VISTI_PERFEZIONAMENTO_Ud", new XmlUtilitySerializer().createVariabileLista(listaRespVistiPerfezionamento));		
		}
		
		// Visto bilancio
		
		String flgVistoBilancio = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_BILANCIO"))) {
			flgVistoBilancio = bean.getFlgVistoBilancio() != null ? bean.getFlgVistoBilancio() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_BILANCIO_Doc", flgVistoBilancio);
		}
		
		// Resp. visto alternativo bilancio
		
		String respVisAltBilancio = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_VISTO_ALTERNATIVO_BILANCIO"))) {
			if("altro".equals(flgVistoBilancio)) {		
				if(bean.getListaRespVisAltBilancio() != null && bean.getListaRespVisAltBilancio().size() > 0) {
					respVisAltBilancio = bean.getListaRespVisAltBilancio().get(0).getResponsabileVistoAlternativoBilancio();								
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_VISTO_ALTERNATIVO_BILANCIO_Ud", respVisAltBilancio);			
		}
		
		// Tipo bilancio
				
		String tipoVistoBilancio = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_VISTO_BILANCIO"))) {
			if("altro".equals(flgVistoBilancio)) {				
				tipoVistoBilancio = bean.getTipoVistoBilancio() != null ? bean.getTipoVistoBilancio() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_VISTO_BILANCIO_Doc", tipoVistoBilancio);
		}
		
		// Visto SG
		
		String flgVistoSG = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_SG"))) {
			if(!"nomina_sg".equals(flgVistoBilancio)) {				
				flgVistoSG = bean.getFlgVistoSG() != null && bean.getFlgVistoSG() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_SG_Doc", flgVistoSG);			
		}
		
		// Visto presidente
		
		String flgVistoPresidente = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_PRESIDENTE"))) {
			flgVistoPresidente = bean.getFlgVistoPresidente() != null && bean.getFlgVistoPresidente() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_PRESIDENTE_Doc", flgVistoPresidente);			
		}		
				
		// Estensore principale
		
		String estensorePrincipale = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ESTENSORE_MAIN"))) {
			estensorePrincipale = (bean.getListaEstensori() != null && bean.getListaEstensori().size() > 0) ?  bean.getListaEstensori().get(0).getEstensore() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ESTENSORE_MAIN_Ud", estensorePrincipale);		
		}
						
		// Altri estensori
		
		List<SimpleValueBean> listaAltriEstensori = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_ESTENSORI"))) {
			if(bean.getListaAltriEstensori() != null) {
				for(EstensoreBean lEstensoreBean : bean.getListaAltriEstensori()) {
					if(StringUtils.isNotBlank(lEstensoreBean.getEstensore())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lEstensoreBean.getEstensore());
						listaAltriEstensori.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_ESTENSORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriEstensori));		
		}
				
		// Resp. istruttoria
		
		String istruttorePrincipale = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ISTRUTTORE_MAIN"))) {
			istruttorePrincipale = (bean.getListaIstruttori() != null && bean.getListaIstruttori().size() > 0) ?  bean.getListaIstruttori().get(0).getIstruttore() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ISTRUTTORE_MAIN_Ud", istruttorePrincipale);		
		}
						
		// Altri istruttori
		
		List<SimpleValueBean> listaAltriIstruttori = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_ISTRUTTORI"))) {
			if(bean.getListaAltriIstruttori() != null) {
				for(IstruttoreBean lIstruttoreBean : bean.getListaAltriIstruttori()) {
					if(StringUtils.isNotBlank(lIstruttoreBean.getIstruttore())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lIstruttoreBean.getIstruttore());
						listaAltriIstruttori.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_ISTRUTTORI_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriIstruttori));
		}
		
		// Assessore/consigliere di riferimento
		
		List<SimpleValueBean> listaUtenteRifAttoConsiglio = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_USER_RIF_ATTO_CONSIGLIO"))) {
			if(bean.getListaUtenteRifAttoConsiglio() != null) {
				for(UtenteRifAttoConsiglioBean lUtenteRifAttoConsiglioBean : bean.getListaUtenteRifAttoConsiglio()) {
					if(StringUtils.isNotBlank(lUtenteRifAttoConsiglioBean.getUtenteRifAttoConsiglio())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lUtenteRifAttoConsiglioBean.getUtenteRifAttoConsiglio());
						listaUtenteRifAttoConsiglio.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_USER_RIF_ATTO_CONSIGLIO_Ud", new XmlUtilitySerializer().createVariabileLista(listaUtenteRifAttoConsiglio));
		}

		// Senza validazione PO
		
		String flgSenzaValidazionePO = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_NO_VALIDAZIONE_PO"))) {
			flgSenzaValidazionePO = bean.getFlgSenzaValidazionePO() != null && bean.getFlgSenzaValidazionePO() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_NO_VALIDAZIONE_PO_Doc", flgSenzaValidazionePO);			
		}
		
		/* Dati scheda - Visti dir. superiori */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "VISTI_DIR_SUPERIORI"))) {
			
			String flgVistoRespUff = "";			
			String idScrivaniaVistoRespUff = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_RESP_UFF"))) {
				flgVistoRespUff = bean.getFlgVistoRespUff() != null && bean.getFlgVistoRespUff() ? "1" : "";
				idScrivaniaVistoRespUff = bean.getIdScrivaniaVistoRespUff() != null ? bean.getIdScrivaniaVistoRespUff() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_RESP_UFF_Doc", flgVistoRespUff);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_VISTO_RESP_UFF_Ud", idScrivaniaVistoRespUff);
			}

			String flgVistoDirSup1 = "";			
			String idScrivaniaVistoDirSup1 = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP_1"))) {
				flgVistoDirSup1 = bean.getFlgVistoDirSup1() != null && bean.getFlgVistoDirSup1() ? "1" : "";	
				idScrivaniaVistoDirSup1 = bean.getIdScrivaniaVistoDirSup1() != null ? bean.getIdScrivaniaVistoDirSup1() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_1_Doc", flgVistoDirSup1);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_VISTO_DIR_SUP_1_Ud", idScrivaniaVistoDirSup1);
			}
		
			String flgVistoDirSup2 = "";	
			String idScrivaniaVistoDirSup2 = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_DIR_SUP_2"))) {
				flgVistoDirSup2 = bean.getFlgVistoDirSup2() != null && bean.getFlgVistoDirSup2() ? "1" : "";
				idScrivaniaVistoDirSup2 = bean.getIdScrivaniaVistoDirSup2() != null ? bean.getIdScrivaniaVistoDirSup2() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_DIR_SUP_2_Doc", flgVistoDirSup2);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_VISTO_DIR_SUP_2_Ud", idScrivaniaVistoDirSup2);
			}
		}
		
		/* Dati scheda - Parere della/e circoscrizioni */
			
		List<SimpleValueBean> listaParereCircoscrizioni = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "COD_CIRCOSCRIZIONE_X_PARERE"))) {
			if(bean.getListaParereCircoscrizioni() != null) {
				for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaParereCircoscrizioni()) {
					if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
						SimpleValueBean lSimpleValueBean = new SimpleValueBean();
						lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
						listaParereCircoscrizioni.add(lSimpleValueBean);						
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "COD_CIRCOSCRIZIONE_X_PARERE_Doc", new XmlUtilitySerializer().createVariabileLista(listaParereCircoscrizioni));
		}
		
		/* Dati scheda - Parere della/e commissioni */
		
		String flgCommissioni = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_X_COMMISSIONI") && showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_COMMISSIONI"))) {
			flgCommissioni = bean.getFlgCommissioni() != null && bean.getFlgCommissioni() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_X_COMMISSIONI_Doc", flgCommissioni);		
		}
		
		List<SimpleValueBean> listaParereCommissioni = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "COD_COMMISSIONE_X_PARERE"))) {
			boolean showFlgCommissioniInSezCommissioni = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_X_COMMISSIONI") && showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_COMMISSIONI"));
			if(!showFlgCommissioniInSezCommissioni || "1".equals(flgCommissioni)) {
				if(bean.getListaParereCommissioni() != null) {
					for(SimpleKeyValueBean lSimpleKeyValueBean : bean.getListaParereCommissioni()) {
						if(StringUtils.isNotBlank(lSimpleKeyValueBean.getKey())) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(lSimpleKeyValueBean.getKey());
							listaParereCommissioni.add(lSimpleValueBean);						
						}
					}
				}
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "COD_COMMISSIONE_X_PARERE_Doc", new XmlUtilitySerializer().createVariabileLista(listaParereCommissioni));
		}
		
		/* Oggetto HTML */

		String oggettoHtml = "";
		String oggettoHtmlFile = "";
		if(!isPresenteAttributoCustomCablato(setAttributiCustomCablati, "NASCONDI_OGGETTO")) {
			oggettoHtml = bean.getOggettoHtml() != null ? bean.getOggettoHtml() : "";
			oggettoHtmlFile = bean.getOggettoHtmlFile() != null ? bean.getOggettoHtmlFile() : "";
		}
		putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "OGGETTO_HTML_Doc", oggettoHtml, "OGGETTO_HTML_FILE_Doc", oggettoHtmlFile);
		
		/* Dati scheda - Specificità del provvedimento */
		
		String oggLiquidazione = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE"))) {
			oggLiquidazione = bean.getOggLiquidazione() != null ? bean.getOggLiquidazione() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_OGG_LIQUIDAZIONE_Doc", oggLiquidazione);
		}
		
		String dataScadenzaLiquidazione = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SCADENZA_LIQUIDAZIONE"))) {
			dataScadenzaLiquidazione = bean.getDataScadenzaLiquidazione() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataScadenzaLiquidazione()) : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SCADENZA_LIQUIDAZIONE_Doc", dataScadenzaLiquidazione);
		}
		
		String urgenzaLiquidazione = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "URGENZA_LIQUIDAZIONE"))) {
			urgenzaLiquidazione = bean.getUrgenzaLiquidazione() != null ? bean.getUrgenzaLiquidazione() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "URGENZA_LIQUIDAZIONE_Doc", urgenzaLiquidazione);
		}
		
		String flgLiqXUffCassa = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA"))) {
			flgLiqXUffCassa = bean.getFlgLiqXUffCassa() != null && bean.getFlgLiqXUffCassa() ? "1" : "";		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA_Doc", flgLiqXUffCassa);
		}
		
		String importoAnticipoCassa = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "IMPORTO_ANTICIPO_CASSA"))) {
			if("1".equals(flgLiqXUffCassa)) {
				importoAnticipoCassa = "1".equals(flgLiqXUffCassa) && bean.getImportoAnticipoCassa() != null ? bean.getImportoAnticipoCassa() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "IMPORTO_ANTICIPO_CASSA_Doc", importoAnticipoCassa);
		}
		
		String dataDecorrenzaContratto = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DECORRENZA_CONTRATTO"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE")) || oggLiquidazione.toUpperCase().contains("CONTRATTO")) {
				dataDecorrenzaContratto = bean.getDataDecorrenzaContratto() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataDecorrenzaContratto()) : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DECORRENZA_CONTRATTO_Doc", dataDecorrenzaContratto);
		}
		
		String anniDurataContratto = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNI_DURATA_CONTRATTO"))) {			
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OGG_LIQUIDAZIONE")) || oggLiquidazione.toUpperCase().contains("CONTRATTO")) {
				anniDurataContratto = bean.getAnniDurataContratto() != null ? bean.getAnniDurataContratto() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNI_DURATA_CONTRATTO_Doc", anniDurataContratto);		
		}
				
		String flgAffidamento = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO"))) {
			flgAffidamento = bean.getFlgAffidamento() != null && bean.getFlgAffidamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_AFFIDAMENTO_Doc", flgAffidamento);
		}
		
		String flgDeterminaAContrarreTramiteProceduraGara = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CONTR_CON_GARA"))) {
			flgDeterminaAContrarreTramiteProceduraGara = bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CONTR_CON_GARA_Doc", flgDeterminaAContrarreTramiteProceduraGara);
		}
		
		String flgDeterminaAggiudicaProceduraGara = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_AGGIUDICA_GARA"))) {
			flgDeterminaAggiudicaProceduraGara = bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_AGGIUDICA_GARA_Doc", flgDeterminaAggiudicaProceduraGara);
		}
		
		String flgDeterminaRimodulazioneSpesaGaraAggiudicata = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD"))) {
			flgDeterminaRimodulazioneSpesaGaraAggiudicata = bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD_Doc", flgDeterminaRimodulazioneSpesaGaraAggiudicata);
		}
		
		String flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_PERSONALE"))) {
			flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro = bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() != null && bean.getFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_PERSONALE_Doc", flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro);
		}
		
		String flgDeterminaRiaccertamento = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_RIACCERT"))) {
			flgDeterminaRiaccertamento = bean.getFlgDeterminaRiaccertamento() != null && bean.getFlgDeterminaRiaccertamento() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_RIACCERT_Doc", flgDeterminaRiaccertamento);		
		}
		
		String flgDeterminaAccertRadiaz = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_ACCERT_RADIAZ"))) {
			flgDeterminaAccertRadiaz = bean.getFlgDeterminaAccertRadiaz() != null && bean.getFlgDeterminaAccertRadiaz() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_ACCERT_RADIAZ_Doc", flgDeterminaAccertRadiaz);		
		}
		
		String flgDeterminaVariazBil = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_VARIAZ_BIL"))) {
			flgDeterminaVariazBil = bean.getFlgDeterminaVariazBil() != null && bean.getFlgDeterminaVariazBil() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_VARIAZ_BIL_Doc", flgDeterminaVariazBil);		
		}
		
		String flgVantaggiEconomici = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI"))) {
			flgVantaggiEconomici = bean.getFlgVantaggiEconomici() != null && bean.getFlgVantaggiEconomici() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI_Doc", flgVantaggiEconomici);
		}
		
		String flgDecretoReggio = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DECRETO_REGGIO"))) {
			flgDecretoReggio =  bean.getFlgDecretoReggio() != null && bean.getFlgDecretoReggio() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DECRETO_REGGIO_Doc", flgDecretoReggio); 
		}
		
		String flgAvvocatura = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_AVVOCATURA"))) {
			flgAvvocatura =  bean.getFlgAvvocatura() != null && bean.getFlgAvvocatura() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_AVVOCATURA_Doc", flgAvvocatura);
		}
		
		String flgDeterminaArchiviazione = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_AVVOCATURA"))) {
			flgDeterminaArchiviazione =  bean.getFlgDeterminaArchiviazione() != null && bean.getFlgDeterminaArchiviazione() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_ARCHIVIAZIONE_Doc", flgDeterminaArchiviazione);
		}
		
		// il check "contributi" non è esclusivo come gli altri sopra
		String flgContributi = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_CONTRIBUTI"))) {
			flgContributi =  bean.getFlgContributi() != null && bean.getFlgContributi() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_CONTRIBUTI_Doc", flgContributi); 
		}
		
		String annoContabileCompetenza = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_CONTABILE_COMPETENZA"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				annoContabileCompetenza = bean.getAnnoContabileCompetenza() != null ? bean.getAnnoContabileCompetenza() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_CONTABILE_COMPETENZA_Doc", annoContabileCompetenza);
		}
		
		String flgCorteConti = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_CORTE_CONTI"))) {
			if(_FLG_SI.equalsIgnoreCase(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
				flgCorteConti =  bean.getFlgCorteConti() != null && bean.getFlgCorteConti() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_CORTE_CONTI_Doc", flgCorteConti); 
		}
		
		String flgLiqContestualeImpegno = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LIQ_CONTESTUALE_IMPEGNO"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgLiqContestualeImpegno = bean.getFlgLiqContestualeImpegno() != null && bean.getFlgLiqContestualeImpegno() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LIQ_CONTESTUALE_IMPEGNO_Doc", flgLiqContestualeImpegno); 
		}
		
		String flgLiqContestualeAltriAspettiRilCont = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgLiqContestualeAltriAspettiRilCont = bean.getFlgLiqContestualeAltriAspettiRilCont() != null && bean.getFlgLiqContestualeAltriAspettiRilCont() ? "1" : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT_Doc", flgLiqContestualeAltriAspettiRilCont);
		}
		
		String flgDetConLiquidazione = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_DET_CON_LIQ"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgDetConLiquidazione = bean.getFlgDetConLiquidazione() != null && bean.getFlgDetConLiquidazione() ? "1" : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DET_CON_LIQ_Doc", flgDetConLiquidazione);
		}
		
		String flgCompQuadroFinRagDec = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgCompQuadroFinRagDec = bean.getFlgCompQuadroFinRagDec() != null && bean.getFlgCompQuadroFinRagDec() ? "1" : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC_Doc", flgCompQuadroFinRagDec);
		}

		String flgNuoviImpAcc = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_NUOVI_IMP_ACC"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgNuoviImpAcc = bean.getFlgNuoviImpAcc() != null && bean.getFlgNuoviImpAcc() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_NUOVI_IMP_ACC_Doc", flgNuoviImpAcc);
		}
				 
		String flgImpSuAnnoCorrente = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgImpSuAnnoCorrente = bean.getFlgImpSuAnnoCorrente() != null && bean.getFlgImpSuAnnoCorrente() ? "1" : "";					
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE_Doc", flgImpSuAnnoCorrente);
		}
		
		String flgInsMovARagioneria = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_INS_MOV_A_RAGIONERIA"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				flgInsMovARagioneria = bean.getFlgInsMovARagioneria() != null && bean.getFlgInsMovARagioneria() ? "1" : "";											
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_INS_MOV_A_RAGIONERIA_Doc", flgInsMovARagioneria); 
		}
		
		String flgPresaVisioneContabilita = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA"))) {
			if (_FLG_NO.equalsIgnoreCase(flgSpesa)) {
				flgPresaVisioneContabilita = bean.getFlgPresaVisioneContabilita() != null && bean.getFlgPresaVisioneContabilita() ? "1" : "";
			}			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA_Doc", flgPresaVisioneContabilita);						
		}
		
		String flgSpesaCorrente = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA_CORRENTE"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSpesaCorrente = bean.getFlgSpesaCorrente() != null && bean.getFlgSpesaCorrente() ? "1" : "";					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CORRENTE_Doc", flgSpesaCorrente);
		}
		
		String flgImpegniCorrenteGiaValidati = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_IMP_CORR_VALID"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					if("1".equals(flgSpesaCorrente)) {
						flgImpegniCorrenteGiaValidati = bean.getFlgImpegniCorrenteGiaValidati() != null && bean.getFlgImpegniCorrenteGiaValidati() ? "1" : "";
					}					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CORR_VALID_Doc", flgImpegniCorrenteGiaValidati);
		}
		
		String flgSpesaContoCapitale = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSpesaContoCapitale = bean.getFlgSpesaContoCapitale() != null && bean.getFlgSpesaContoCapitale() ? "1" : "";					
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP_Doc", flgSpesaContoCapitale);
		}
		
		String flgImpegniContoCapitaleGiaRilasciati = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					if("1".equals(flgSpesaContoCapitale)) {
						flgImpegniContoCapitaleGiaRilasciati = bean.getFlgImpegniContoCapitaleGiaRilasciati() != null && bean.getFlgImpegniContoCapitaleGiaRilasciati() ? "1" : "";
					}
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL_Doc", flgImpegniContoCapitaleGiaRilasciati);
		}
		
		String flgSoloSubImpSubCrono = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_SUB"))) {
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
				if(!"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgSoloSubImpSubCrono = bean.getFlgSoloSubImpSubCrono() != null && bean.getFlgSoloSubImpSubCrono() ? "1" : "";
				}
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_SUB_Doc", flgSoloSubImpSubCrono);
		}
		
		String tipoAttoInDeliberaPEG = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG"))) {
			if(_FLG_SI.equalsIgnoreCase(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
				tipoAttoInDeliberaPEG = bean.getTipoAttoInDeliberaPEG() != null ? bean.getTipoAttoInDeliberaPEG() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG_Doc", tipoAttoInDeliberaPEG);				
		}
		
		String tipoAffidamento = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_AFFIDAMENTO"))) {						
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO")) || "1".equals(flgAffidamento)) {
				tipoAffidamento = bean.getTipoAffidamento() != null ? bean.getTipoAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_AFFIDAMENTO_Doc", tipoAffidamento);
		}
	
		String normRifAffidamento = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NORM_RIF_AFFIDAMENTO"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO")) || "1".equals(flgAffidamento)) {
				normRifAffidamento = bean.getNormRifAffidamento() != null ? bean.getNormRifAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NORM_RIF_AFFIDAMENTO_Doc", normRifAffidamento);
		}
		
		String respAffidamento = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "RESP_AFFIDAMENTO"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_AFFIDAMENTO")) || "1".equals(flgAffidamento)) {
				respAffidamento = bean.getRespAffidamento() != null ? bean.getRespAffidamento() : "";				
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "RESP_AFFIDAMENTO_Doc", respAffidamento);
		}
		
		String materiaTipoAtto = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MATERIA_NATURA_ATTO"))) {
			materiaTipoAtto = bean.getMateriaTipoAtto() != null ? bean.getMateriaTipoAtto() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MATERIA_NATURA_ATTO_Doc", materiaTipoAtto);
		}
		
		String tipoFinanziamentoPNRR = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_FINANZIAMENTO_PNRR"))) {
			tipoFinanziamentoPNRR = bean.getTipoFinanziamentoPNRR() != null ? bean.getTipoFinanziamentoPNRR() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_FINANZIAMENTO_PNRR_Doc", tipoFinanziamentoPNRR);
		}
		
		String flgSottotipoAtto = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_SOTTOTIPO_ATTO_RADIO"))) {
			flgSottotipoAtto = bean.getFlgSottotipoAtto() != null ? bean.getFlgSottotipoAtto() : "";					
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_SOTTOTIPO_ATTO_RADIO_Doc", flgSottotipoAtto);
		}
		
		String flgTipoIter = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_TIPO_ITER"))) {
			flgTipoIter = bean.getFlgTipoIter() != null ? bean.getFlgTipoIter() : "";					
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_TIPO_ITER_Doc", flgTipoIter);
		}
				
		String flgFondiEuropeiPON = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_EUROPEI_PON"))) {
			if(!_FLG_NO.equalsIgnoreCase(flgSpesa)) {
				flgFondiEuropeiPON =  bean.getFlgFondiEuropeiPON() != null && bean.getFlgFondiEuropeiPON() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_EUROPEI_PON_Doc", flgFondiEuropeiPON);
		}
		
		String flgFondiPNRRRadio = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_PNRR_RADIO"))) {
			// TASK_RESULT_2_FONDI_PNRR_RADIO è visibile anche quando non è con spesa (comportamento diverso rispetto a TASK_RESULT_2_FONDI_PNRR)
			flgFondiPNRRRadio = bean.getFlgFondiPNRRRadio() != null ? bean.getFlgFondiPNRRRadio() : "";				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_PNRR_RADIO_Doc", flgFondiPNRRRadio);
		}
		
		String flgFondiPNRR = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_PNRR"))) {
			if(!_FLG_NO.equalsIgnoreCase(flgSpesa)) {
				flgFondiPNRR =  bean.getFlgFondiPNRR() != null && bean.getFlgFondiPNRR() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_PNRR_Doc", flgFondiPNRR);
		}
		
		String flgFondiPNRRRigen = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_PNRR_RIGEN"))) {
			if(!_FLG_NO.equalsIgnoreCase(flgSpesa)) {
				flgFondiPNRRRigen =  bean.getFlgFondiPNRRRigen() != null && bean.getFlgFondiPNRRRigen() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_PNRR_RIGEN_Doc", flgFondiPNRRRigen);
		}		
				 
		String flgFondiPRU = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FONDI_PRU"))) {
			flgFondiPRU = bean.getFlgFondiPRU() != null && bean.getFlgFondiPRU() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FONDI_PRU_Doc", flgFondiPRU);
		}
		
		String flgVistoUtenze = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_VISTO_UTENZE"))) {
			flgVistoUtenze = bean.getFlgVistoUtenze() != null && bean.getFlgVistoUtenze() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_VISTO_UTENZE_Doc", flgVistoUtenze);
		}
		
		String flgVistoCapitolatiSottoSoglia = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA"))) {
			if(_FLG_SI.equalsIgnoreCase(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
				flgVistoCapitolatiSottoSoglia = bean.getFlgVistoCapitolatiSottoSoglia() != null && bean.getFlgVistoCapitolatiSottoSoglia() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_CAPITOLATI_SOTTO_SOGLIA_Doc", flgVistoCapitolatiSottoSoglia);
		}
		
		String flgVistoCapitolatiSopraSoglia = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA"))) {
			if(_FLG_SI.equalsIgnoreCase(flgSpesa) || getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
				flgVistoCapitolatiSopraSoglia = bean.getFlgVistoCapitolatiSopraSoglia() != null && bean.getFlgVistoCapitolatiSopraSoglia() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_CAPITOLATI_SOPRA_SOGLIA_Doc", flgVistoCapitolatiSopraSoglia);
		}
		
		String flgVistoPar117_2013 = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISTO_PAR_117_2013"))) {
			flgVistoPar117_2013 = bean.getFlgVistoPar117_2013() != null && bean.getFlgVistoPar117_2013() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISTO_PAR_117_2013_Doc", flgVistoPar117_2013);
		}
		
		String flgNotificaDaMessi = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_NOTIFICA_DA_MESSI"))) {
			flgNotificaDaMessi = bean.getFlgNotificaDaMessi() != null && bean.getFlgNotificaDaMessi() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_NOTIFICA_DA_MESSI_Doc", flgNotificaDaMessi);
		}
				
		String flgSenzaImpegniCont = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_SENZA_IMPEGNI_CONT"))) {
			flgSenzaImpegniCont = bean.getFlgSenzaImpegniCont() != null && bean.getFlgSenzaImpegniCont() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_SENZA_IMPEGNI_CONT_Doc", flgSenzaImpegniCont);
		}
		
		String flgMEPACONSIP = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_MEPA_CONSIP"))) {
			flgMEPACONSIP = bean.getFlgMEPACONSIP() != null ? bean.getFlgMEPACONSIP() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_MEPA_CONSIP_Doc", flgMEPACONSIP);
		}
		
		String flgServeDUVRI = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_SERVE_DUVRI"))) {
			flgServeDUVRI = bean.getFlgServeDUVRI() != null ? bean.getFlgServeDUVRI() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_SERVE_DUVRI_Doc", flgServeDUVRI);
		}
		 		
		String imponibileComplessivo = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_IMPONIBILE_TOT"))) {
			imponibileComplessivo = bean.getImponibileComplessivo() != null ? bean.getImponibileComplessivo() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_IMPONIBILE_TOT_Doc", imponibileComplessivo);
		}
		
		String importoOneriSicurezza = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_IMPORTO_ONERI_SICUREZZA"))) {
			importoOneriSicurezza = bean.getImportoOneriSicurezza() != null ? bean.getImportoOneriSicurezza() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_IMPORTO_ONERI_SICUREZZA_Doc", importoOneriSicurezza);
		}
		
		String flgLLPP = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_LLPP"))) {
			flgLLPP = bean.getFlgLLPP() != null ? bean.getFlgLLPP() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LLPP_Doc", flgLLPP);
		}
		
		String annoProgettoLLPP = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_PROGETTO_LLPP"))) {
			if (_FLG_SI.equals(flgLLPP)) {
				annoProgettoLLPP = bean.getAnnoProgettoLLPP() != null ? bean.getAnnoProgettoLLPP() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_LLPP_Doc", annoProgettoLLPP);
		}
		
		String numProgettoLLPP = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_PROGETTO_LLPP"))) {
			if (_FLG_SI.equals(flgLLPP)) {	
				numProgettoLLPP = bean.getNumProgettoLLPP() != null ? bean.getNumProgettoLLPP() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_LLPP_Doc", numProgettoLLPP);
		}
		
		String flgBeniServizi = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_BENI_SERVIZI"))) {
			flgBeniServizi = bean.getFlgBeniServizi() != null ? bean.getFlgBeniServizi() : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_BENI_SERVIZI_Doc", flgBeniServizi);
		}
		
		String annoProgettoBeniServizi = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_PROGETTO_BENI_SERVIZI"))) {
			if (_FLG_SI.equals(flgBeniServizi)) {
				annoProgettoBeniServizi = bean.getAnnoProgettoBeniServizi() != null ? bean.getAnnoProgettoBeniServizi() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_BENI_SERVIZI_Doc", annoProgettoBeniServizi);
		}
		
		String numProgettoBeniServizi = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_PROGETTO_BENI_SERVIZI"))) {
			if (_FLG_SI.equals(flgBeniServizi)) {
				numProgettoBeniServizi = bean.getNumProgettoBeniServizi() != null ? bean.getNumProgettoBeniServizi() : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_BENI_SERVIZI_Doc", numProgettoBeniServizi);
		}
		
		String flgProgrammazioneAcquisti = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_BENI_SERVIZI")) || _FLG_SI.equals(flgBeniServizi)) {				
				flgProgrammazioneAcquisti = bean.getFlgProgrammazioneAcquisti() != null ? bean.getFlgProgrammazioneAcquisti() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI_Doc", flgProgrammazioneAcquisti);
		}
		
		String flgPrivacy = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI"))) {
			flgPrivacy = bean.getFlgPrivacy() != null  ? bean.getFlgPrivacy() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_ATTO_CON_DATI_RISERVATI_Doc", flgPrivacy);
		}
		
		String flgDatiProtettiTipo1 = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_1"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI")) || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo1 = bean.getFlgDatiProtettiTipo1() != null && bean.getFlgDatiProtettiTipo1() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_1_Doc", flgDatiProtettiTipo1);
		}

		String flgDatiProtettiTipo2 = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_2"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI")) || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo2 = bean.getFlgDatiProtettiTipo2() != null && bean.getFlgDatiProtettiTipo2() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_2_Doc", flgDatiProtettiTipo2);
		}
		
		String flgDatiProtettiTipo3 = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_3"))) {
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI")) || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo3 = bean.getFlgDatiProtettiTipo3() != null && bean.getFlgDatiProtettiTipo3() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_3_Doc", flgDatiProtettiTipo3);
		}
		
		String flgDatiProtettiTipo4 = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_DATI_PROTETTI_TIPO_4"))) {			
			if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ATTO_CON_DATI_RISERVATI")) || _FLG_SI.equals(flgPrivacy)) {
				flgDatiProtettiTipo4 = bean.getFlgDatiProtettiTipo4() != null && bean.getFlgDatiProtettiTipo4() ? "1" : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DATI_PROTETTI_TIPO_4_Doc", flgDatiProtettiTipo4);
		}
		
		String numGara = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_GARA") && !showAttributoCustomCablato(setAttributiCustomCablati, "CIG"))) {
			numGara = bean.getNumGara() != null ? bean.getNumGara() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_GARA_Doc", numGara);
		}
		
		String flgControlloLegittimita = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_CONTROLLO_LEGITTIMITA"))) {
			flgControlloLegittimita = bean.getFlgControlloLegittimita() != null  ? bean.getFlgControlloLegittimita() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_CONTROLLO_LEGITTIMITA_Doc", flgControlloLegittimita);
		}
		
		String motivazioniEsclControlloLegittimita = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVAZIONI_ESCL_CONTROLLO_LEGITTIMITA"))) {
			if(_FLG_NO.equals(flgControlloLegittimita)) {
				motivazioniEsclControlloLegittimita = bean.getMotivazioniEsclControlloLegittimita() != null ? bean.getMotivazioniEsclControlloLegittimita() : "";				
			}						
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVAZIONI_ESCL_CONTROLLO_LEGITTIMITA_Doc", motivazioniEsclControlloLegittimita);		
		}
		
		/* Dati scheda - Dest. vantaggio */				
		
		List<DestinatarioVantaggioBean> listaDestVantaggio = new ArrayList<DestinatarioVantaggioBean>();	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DEST_VANTAGGIO"))) {
			if("1".equals(flgVantaggiEconomici)) {			
				if(bean.getListaDestVantaggio() != null) {
					for(DestVantaggioBean lDestVantaggioBean : bean.getListaDestVantaggio()) {
						if(StringUtils.isNotBlank(lDestVantaggioBean.getTipoPersona()) && (StringUtils.isNotBlank(lDestVantaggioBean.getRagioneSociale()) || (StringUtils.isNotBlank(lDestVantaggioBean.getCognome()) && StringUtils.isNotBlank(lDestVantaggioBean.getNome())))) {
							DestinatarioVantaggioBean lDestinatarioVantaggioBean = new DestinatarioVantaggioBean();
							lDestinatarioVantaggioBean.setTipoPersona(lDestVantaggioBean.getTipoPersona());
							if("fisica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {
								lDestinatarioVantaggioBean.setCognome(lDestVantaggioBean.getCognome());
								lDestinatarioVantaggioBean.setNome(lDestVantaggioBean.getNome());
							} else if("giuridica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {							
								lDestinatarioVantaggioBean.setRagioneSociale(lDestVantaggioBean.getRagioneSociale());
							}
							lDestinatarioVantaggioBean.setCodFiscalePIVA(lDestVantaggioBean.getCodFiscalePIVA());
							lDestinatarioVantaggioBean.setImporto(lDestVantaggioBean.getImporto());
							listaDestVantaggio.add(lDestinatarioVantaggioBean);
						}
					}
				}
			}
			/**
			 * Utilizzando il metodo createVariabileListaSkipNullValues anzichè createVariabileLista non vengono passate in input le colonne non mappate o NULL
			 */
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DEST_VANTAGGIO_Doc", new XmlUtilitySerializer().createVariabileListaSkipNullValues(listaDestVantaggio));
		}
				
		/* Dati scheda - Ruoli e visti per dati contabili */	
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI"))) {	

			// Responsabili PEG
			
			String flgAdottanteUnicoRespPEG = "";							
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA"))) {
				if(_FLG_SI.equalsIgnoreCase(flgSpesa)) {
					if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ADOTTANTE"))) {
						flgAdottanteUnicoRespPEG = bean.getFlgAdottanteUnicoRespPEG() != null && bean.getFlgAdottanteUnicoRespPEG() ? "1" : "";
					}
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA_Ud", flgAdottanteUnicoRespPEG);	
			}
		
			// Flag spesa dirigente resp. reg. tec. 
			
			String flgDirRespRegTecnicaUnicoRespSpesa = "";							
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_DIR_RESP_REG_TECNICA_UNICO_RESP_SPESA"))) {
				if(_FLG_SI.equalsIgnoreCase(flgSpesa)) {
					if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_DIR_RESP_REG_TECNICA"))) {
						flgDirRespRegTecnicaUnicoRespSpesa = bean.getFlgDirRespRegTecnicaUnicoRespSpesa() != null && bean.getFlgDirRespRegTecnicaUnicoRespSpesa() ? "1" : "";
					}
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_DIR_RESP_REG_TECNICA_UNICO_RESP_SPESA_Ud", flgDirRespRegTecnicaUnicoRespSpesa);	
			}
			
			List<SimpleValueBean> listaResponsabiliPEG = new ArrayList<SimpleValueBean>();		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_RESP_SPESA"))) {
				if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
					if ("1".equals(flgAdottanteUnicoRespPEG)) {
						if(!"".equals(dirigenteAdottante)) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(dirigenteAdottante);				
							listaResponsabiliPEG.add(lSimpleValueBean);
						}
					} else if ("1".equals(flgDirRespRegTecnicaUnicoRespSpesa)) {
						if(!"".equals(dirRespRegTecnica)) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(dirRespRegTecnica);				
							listaResponsabiliPEG.add(lSimpleValueBean);
						}
					} else if(bean.getListaResponsabiliPEG() != null) {
						for(ResponsabilePEGBean lResponsabilePEGBean : bean.getListaResponsabiliPEG()) {
							SimpleValueBean lSimpleValueBean = new SimpleValueBean();
							lSimpleValueBean.setValue(lResponsabilePEGBean.getResponsabilePEG());
							listaResponsabiliPEG.add(lSimpleValueBean);
						}
					}			
				}
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_RESP_SPESA_Ud", new XmlUtilitySerializer().createVariabileLista(listaResponsabiliPEG));
			}
			
			// Ufficio definizione spesa
						
			String ufficioDefinizioneSpesa = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_UO_COMP_SPESA"))) {
				if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {			
					if (StringUtils.isNotBlank(bean.getUfficioDefinizioneSpesa())) {
						ufficioDefinizioneSpesa = bean.getUfficioDefinizioneSpesa();				
					}		
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_UO_COMP_SPESA_Ud", ufficioDefinizioneSpesa);		
			}
		
			// Definizione dati contabili in carico a
		
			String opzAssCompSpesa = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_OPZ_ASS_COMP_SPESA"))) {
				if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {
					opzAssCompSpesa = bean.getOpzAssCompSpesa() != null ? bean.getOpzAssCompSpesa() : "";		
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_OPZ_ASS_COMP_SPESA_Doc", opzAssCompSpesa);
			}
		
			String flgRichVerificaDiBilancioCorrente = "";	
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_BIL_CORR"))) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
					flgRichVerificaDiBilancioCorrente = bean.getFlgRichVerificaDiBilancioCorrente() != null && bean.getFlgRichVerificaDiBilancioCorrente() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CORR_Doc", flgRichVerificaDiBilancioCorrente);										
			}
		
			String flgRichVerificaDiBilancioContoCapitale = "";	
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP"))) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
					flgRichVerificaDiBilancioContoCapitale = bean.getFlgRichVerificaDiBilancioContoCapitale() != null && bean.getFlgRichVerificaDiBilancioContoCapitale() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP_Doc", flgRichVerificaDiBilancioContoCapitale);							
			}
		
			String flgRichVerificaDiContabilita = "";	
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_DET_CON_VRF_CONTABIL"))) {
				if (getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesa)) {
					flgRichVerificaDiContabilita = bean.getFlgRichVerificaDiContabilita() != null && bean.getFlgRichVerificaDiContabilita() ? "1" : "";	
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_DET_CON_VRF_CONTABIL_Doc", flgRichVerificaDiContabilita);					
			}
		
			String flgConVerificaContabilita = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA"))) {
				if (_FLG_SI.equalsIgnoreCase(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgConVerificaContabilita = bean.getFlgConVerificaContabilita() != null && bean.getFlgConVerificaContabilita() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA_Doc", flgConVerificaContabilita);
			}
		
			String flgRichiediParereRevisoriContabili = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI"))) {
				if (_FLG_SI.equalsIgnoreCase(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro)) {
					flgRichiediParereRevisoriContabili = bean.getFlgRichiediParereRevisoriContabili() != null && bean.getFlgRichiediParereRevisoriContabili() ? "1" : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI_Doc", flgRichiediParereRevisoriContabili);
			}

		}

		/* Dati scheda - CIG */
		
		String flgOpCommerciale = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"))) {
			flgOpCommerciale = bean.getFlgOpCommerciale() != null ? bean.getFlgOpCommerciale() : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_OP_COMMERCIALE_Doc", flgOpCommerciale);			
		}
		
		String flgEscludiCIG = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"))) {
			boolean showFlgOpCommerciale = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"));
			boolean showFlgProcExCodAppalti = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI"));
			boolean showFlgEscludiCIG = true;
			if(!skipCtrlAttributiCustomCablati) {
				if(!showFlgOpCommerciale && showFlgProcExCodAppalti && !_FLG_SI.equals(flgProcExCodAppalti)) {
					showFlgEscludiCIG = false;
				} else if(showFlgOpCommerciale && !_FLG_SI.equals(flgOpCommerciale)) {
					showFlgEscludiCIG = false;
				}
			}
			if(showFlgEscludiCIG) {
				flgEscludiCIG = bean.getFlgEscludiCIG() != null && bean.getFlgEscludiCIG() ? "1" : "";	
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_ESCL_CIG_Doc", flgEscludiCIG);			
		}
		
		String flgCIGDaAcquisire = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_CIG_IN_ACQUISIZIONE"))) {
			flgCIGDaAcquisire = bean.getFlgCIGDaAcquisire() != null && bean.getFlgCIGDaAcquisire() ? "1" : "";	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_CIG_IN_ACQUISIZIONE_Doc", flgCIGDaAcquisire);			
		}
		
		String motivoEsclusioneCIG = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVO_ESCLUSIONE_CIG"))) {
			boolean showFlgEscludiCIG = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"));
			boolean showFlgOpCommerciale = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"));
			boolean showFlgProcExCodAppalti = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI"));
			boolean showMotivoEsclusioneCIG = true;
			if(!skipCtrlAttributiCustomCablati) {				
				if(showFlgEscludiCIG && !"1".equals(flgEscludiCIG)) {
					showMotivoEsclusioneCIG = false;
				} else if(!showFlgOpCommerciale && showFlgProcExCodAppalti && !_FLG_SI.equals(flgProcExCodAppalti)) {
					showMotivoEsclusioneCIG = false;
				} else if(showFlgOpCommerciale && !_FLG_SI.equals(flgOpCommerciale)) {
					showMotivoEsclusioneCIG = false;
				}
			}
			if(showMotivoEsclusioneCIG) {
				motivoEsclusioneCIG = bean.getMotivoEsclusioneCIG() != null ? bean.getMotivoEsclusioneCIG() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOTIVO_ESCLUSIONE_CIG_Doc", motivoEsclusioneCIG);			 
		}
		
		String codiceCIGPadre = "";			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CIG_PADRE"))) {
			boolean showFlgEscludiCIG = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"));
			boolean showCodiceCIGPadre = true;
			if(!skipCtrlAttributiCustomCablati) {				
				if(showFlgEscludiCIG && "1".equals(flgEscludiCIG)) {
					showCodiceCIGPadre = false;
				}
			}
			if(showCodiceCIGPadre) {
				codiceCIGPadre = bean.getCodiceCIGPadre() != null ? bean.getCodiceCIGPadre() : "";
			}
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "CIG_PADRE_Doc", codiceCIGPadre);			 
		}
		
		List<CIGCUPBean> listaCIG = new ArrayList<CIGCUPBean>();		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CIG"))) {
			boolean showFlgEscludiCIG = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_ESCL_CIG"));
			boolean showFlgCIGDaAcquisire = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_CIG_IN_ACQUISIZIONE"));
			boolean showFlgOpCommerciale = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_OP_COMMERCIALE"));
			boolean showFlgProcExCodAppalti = skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PROC_EX_COD_APPALTI"));
			boolean showListaCIG = true;
			if(!skipCtrlAttributiCustomCablati) {				
				if(showFlgEscludiCIG && "1".equals(flgEscludiCIG)) {
					showListaCIG = false;
				} else if(showFlgCIGDaAcquisire && "1".equals(flgCIGDaAcquisire)) {
					showListaCIG = false;
				} else if(!showFlgOpCommerciale && showFlgProcExCodAppalti && !_FLG_SI.equals(flgProcExCodAppalti)) {
					showListaCIG = false;
				} else if(showFlgOpCommerciale && !_FLG_SI.equals(flgOpCommerciale)) {
					showListaCIG = false;
				}
			}
			if(showListaCIG) {
				listaCIG = bean.getListaCIG() != null ? bean.getListaCIG() : new ArrayList<CIGCUPBean>();	
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "CIG_CUP_Doc", new XmlUtilitySerializer().createVariabileLista(listaCIG));			
		}
		
		/* Dati scheda - CUI */
		
		List<CUIBean> listaCUI = new ArrayList<CUIBean>();		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CUI"))) {
			boolean showListaCUI = true;
			if(!skipCtrlAttributiCustomCablati) {				
				if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_PROGRAMMAZIONE_ACQUISTI") && !_FLG_SI.equals(flgProgrammazioneAcquisti)) {
					showListaCUI = false;
				}
			}
			if(showListaCUI) {
				listaCUI = bean.getListaCUI() != null ? bean.getListaCUI() : new ArrayList<CUIBean>();
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "LISTA_CUI_Doc", new XmlUtilitySerializer().createVariabileLista(listaCUI));			
		}
		
		/* Dati scheda - Categoria di rischio */
		
		String categoriaRischio = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "CATEGORIA_RISCHIO"))) {
			categoriaRischio = bean.getCategoriaRischio() != null ? bean.getCategoriaRischio() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "CATEGORIA_RISCHIO_Doc", categoriaRischio);
		}
		
		/* Dati dispositivo - Riferimenti normativi */
		
		List<SimpleValueBean> listaRiferimentiNormativi = new ArrayList<SimpleValueBean>();		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "RIFERIMENTI_NORMATIVI"))) {
			listaRiferimentiNormativi = bean.getListaRiferimentiNormativi() != null ? bean.getListaRiferimentiNormativi() : new ArrayList<SimpleValueBean>();
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "RIFERIMENTI_NORMATIVI_Doc", new XmlUtilitySerializer().createVariabileLista(listaRiferimentiNormativi));			
		}
		
		/* Dati scheda - sotto-fascicolo rda */
		
		String subFolderRda = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SUBFOLDER_RDA") && "true".equals(bean.getDerivaDaRdA()))) {
			subFolderRda = bean.getSubfolderRda() != null ? bean.getSubfolderRda() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SUBFOLDER_RDA_Doc", subFolderRda);
		}
		
		/* Dati dispositivo - Atti presupposti */
		
//		List<SimpleValueBean> listaAttiPresupposti = new ArrayList<SimpleValueBean>();
//		if(skipCtrlAttributiCustomCablati || showAttributoCustomCablato(setAttributiCustomCablati, "ATTI_PRESUPPOSTI"))) {
//			listaAttiPresupposti = bean.getListaAttiPresupposti() != null ? bean.getListaAttiPresupposti() : new ArrayList<SimpleValueBean>();
//			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", new XmlUtilitySerializer().createVariabileLista(listaAttiPresupposti));
//		}
		
		String attiPresupposti = "";
		String attiPresuppostiFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ATTI_PRESUPPOSTI"))) {
			attiPresupposti = bean.getAttiPresupposti() != null  ? bean.getAttiPresupposti() : "";
			attiPresuppostiFile = bean.getAttiPresuppostiFile() != null  ? bean.getAttiPresuppostiFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "ATTI_PRESUPPOSTI_Doc", attiPresupposti, "ATTI_PRESUPPOSTI_FILE_Doc", attiPresuppostiFile);
		}
		
		/* Dati dispositivo - Motivazioni */
		
		String motivazioni = "";
		String motivazioniFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVAZIONI_ATTO"))) {
			motivazioni = bean.getMotivazioni() != null  ? bean.getMotivazioni() : "";
			motivazioniFile = bean.getMotivazioniFile() != null  ? bean.getMotivazioniFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "MOTIVAZIONI_ATTO_Doc", motivazioni, "MOTIVAZIONI_ATTO_FILE_Doc", motivazioniFile);
		}
		
		/* Dati dispositivo - Premessa */
		
		String premessa = "";
		String premessaFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PREMESSA_ATTO"))) {
			premessa = bean.getPremessa() != null  ? bean.getPremessa() : "";
			premessaFile = bean.getPremessaFile() != null  ? bean.getPremessaFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "PREMESSA_ATTO_Doc", premessa, "PREMESSA_ATTO_FILE_Doc", premessaFile);
		}
				
		/* Dati dispositivo - Upload pdf atto */
		
		String idUdPdfAtto = "";
		if(bean.getFlgAttivaUploadPdfAtto() != null && bean.getFlgAttivaUploadPdfAtto()) {
			if(bean.getFilePdfAtto() != null && !flgGenModello) {
				if(bean.getFilePdfAtto().getInfoFile() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName().toLowerCase().endsWith(".pdf")) {
					if(StringUtils.isBlank(bean.getFilePdfAtto().getIdUd()) || (bean.getFilePdfAtto().getIsChanged() != null && bean.getFilePdfAtto().getIsChanged())) {
						idUdPdfAtto = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFilePdfAtto(), getSession());
					} else if(StringUtils.isNotBlank(bean.getFilePdfAtto().getIdUd())) {
						idUdPdfAtto = bean.getFilePdfAtto().getIdUd();
					}
				}
			}	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PDF_ATTO_DA_UPLOAD_Ud", idUdPdfAtto);			
		}
		
		String idUdPdfAttoOmissis = "";
		if(bean.getFlgAttivaUploadPdfAttoOmissis() != null && bean.getFlgAttivaUploadPdfAttoOmissis()) {
			if(bean.getFilePdfAttoOmissis() != null && !flgGenModello) {
				if(bean.getFilePdfAttoOmissis().getInfoFile() != null && bean.getFilePdfAttoOmissis().getInfoFile().getCorrectFileName() != null && bean.getFilePdfAttoOmissis().getInfoFile().getCorrectFileName().toLowerCase().endsWith(".pdf")) {
					if(StringUtils.isBlank(bean.getFilePdfAttoOmissis().getIdUd()) || (bean.getFilePdfAttoOmissis().getIsChanged() != null && bean.getFilePdfAttoOmissis().getIsChanged())) {
						idUdPdfAttoOmissis = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, bean.getFilePdfAttoOmissis(), getSession());
					} else if(StringUtils.isNotBlank(bean.getFilePdfAttoOmissis().getIdUd())) {
						idUdPdfAttoOmissis = bean.getFilePdfAttoOmissis().getIdUd();
					}
				}
			}	
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PDF_ATTO_OMISSIS_DA_UPLOAD_Ud", idUdPdfAttoOmissis);			
		}
		
		/* Dati dispositivo - Riferimenti normativi */
		
		String rifNormativiLiberi = "";
		String rifNormativiLiberiFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "RIF_NORMATIVI_LIBERI"))) {
			rifNormativiLiberi = bean.getRifNormativiLiberi() != null  ? bean.getRifNormativiLiberi() : "";
			rifNormativiLiberiFile = bean.getRifNormativiLiberiFile() != null  ? bean.getRifNormativiLiberiFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "RIF_NORMATIVI_LIBERI_Doc", rifNormativiLiberi, "RIF_NORMATIVI_LIBERI_FILE_Doc", rifNormativiLiberiFile);
		}		
		
		/* Dati dispositivo - Dispositivo */
		
		String dispositivo = "";
		String dispositivoFile = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DISPOSITIVO_ATTO"))) {
			dispositivo = bean.getDispositivo() != null  ? bean.getDispositivo() : "";
			dispositivoFile = bean.getDispositivoFile() != null  ? bean.getDispositivoFile() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "DISPOSITIVO_ATTO_Doc", dispositivo, "DISPOSITIVO_ATTO_FILE_Doc", dispositivoFile);
		}
		
		String loghiAggiuntiviDispositivo = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LOGHI_DISPOSITIVO_ATTO"))) {
			loghiAggiuntiviDispositivo = bean.getLoghiAggiuntiviDispositivo() != null  ? bean.getLoghiAggiuntiviDispositivo() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LOGHI_DISPOSITIVO_ATTO_Doc", loghiAggiuntiviDispositivo);
		}
		
		/* Dati dispositivo 2 - Premessa 2 */
		
		String premessa2 = "";
		String premessa2File = "";	
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PREMESSA_ATTO_2"))) {
			premessa2 = bean.getPremessa2() != null  ? bean.getPremessa2() : "";
			premessa2File = bean.getPremessa2File() != null  ? bean.getPremessa2File() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "PREMESSA_ATTO_2_Doc", premessa2, "PREMESSA_ATTO_2_FILE_Doc", premessa2File);
		}
		
		/* Dati dispositivo 2 - Dispositivo 2 */
		
		String dispositivo2 = "";
		String dispositivo2File = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DISPOSITIVO_ATTO_2"))) {
			dispositivo2 = bean.getDispositivo2() != null  ? bean.getDispositivo2() : "";
			dispositivo2File = bean.getDispositivo2File() != null  ? bean.getDispositivo2File() : "";
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "DISPOSITIVO_ATTO_2_Doc", dispositivo2, "DISPOSITIVO_ATTO_2_FILE_Doc", dispositivo2File);
		}
		
		/* Allegati */
		
		String flgPubblicaAllegatiSeparati = bean.getFlgPubblicaAllegatiSeparati() != null && bean.getFlgPubblicaAllegatiSeparati() ? "1" : "";		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_ALLEGATI_SEPARATA_Ud", flgPubblicaAllegatiSeparati);
		
		/* Pubblicazione/notifiche */
		
		salvaAttributiCustomPubblicazioneNotifiche(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, skipCtrlAttributiCustomCablati);
		
		/* Dati spesa corrente */
		
		if(isAttivoSIB(bean)) {	
		
			String flgDisattivaAutoRequestDatiContabiliSIBCorrente = "";
			String prenotazioneSpesaSIBDiCorrente = "";
			String modalitaInvioDatiSpesaARagioneriaCorrente = "";
			List<DatiContabiliBean> listaDatiContabiliSIBCorrente = new ArrayList<DatiContabiliBean>();
			List<DatiContabiliBean> listaInvioDatiSpesaCorrente = new ArrayList<DatiContabiliBean>();
			String idUdXlsCorrente = "";
			String noteCorrente = "";
			String noteCorrenteFile = "";
			
			if (_FLG_SI.equalsIgnoreCase(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaCorrente)) {					
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
				noteCorrenteFile = bean.getNoteCorrenteFile() != null ? bean.getNoteCorrenteFile() : "";
			}		
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CORR_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PRENOT_SPESA_DI_CORR_Doc", prenotazioneSpesaSIBDiCorrente);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CORR_Doc", modalitaInvioDatiSpesaARagioneriaCorrente);
			
			//ATTENZIONE: i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBCorrente));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CORR_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaCorrente));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CORR_Doc", idUdXlsCorrente);
				
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CORR_Doc", noteCorrente, "NOTE_CONT_CORR_FILE_Doc", noteCorrenteFile);
		}
		
		/* Dati spesa conto capitale */
		
		if(isAttivoSIB(bean)) {
		
			String flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = "";
			String modalitaInvioDatiSpesaARagioneriaContoCapitale = "";
			List<DatiContabiliBean> listaDatiContabiliSIBContoCapitale = new ArrayList<DatiContabiliBean>();
			List<DatiContabiliBean> listaInvioDatiSpesaContoCapitale = new ArrayList<DatiContabiliBean>();
			String idUdXlsContoCapitale = "";
			String noteContoCapitale = "";
			String noteContoCapitaleFile = "";
			
			if (_FLG_SI.equalsIgnoreCase(flgSpesa) && !"1".equals(flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro) && "1".equals(flgSpesaContoCapitale)) {					
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
				noteContoCapitaleFile = bean.getNoteContoCapitaleFile() != null ? bean.getNoteContoCapitaleFile() : "";
			}				
		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CCAP_AMC_Doc", flgDisattivaAutoRequestDatiContabiliSIBContoCapitale);
			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MOD_INVIO_CONT_CCAP_Doc", modalitaInvioDatiSpesaARagioneriaContoCapitale);
			
			//ATTENZIONE: i punti separatori delle migliaia non vengono messi nelle colonne con gli importi, di conseguenza non vengono iniettati correttamente nel modello dei dati di spesa
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AMC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliSIBContoCapitale));
			}
			
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_CCAP_AUR_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioDatiSpesaContoCapitale));
				
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "XLS_DATI_CONT_CCAP_Doc", idUdXlsContoCapitale);
				
			putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "NOTE_CONT_CCAP_Doc", noteContoCapitale, "NOTE_CONT_CCAP_FILE_Doc", noteContoCapitaleFile);
		}
		
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
		
		/* Trasparenza AVB */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_TRASP_AVB"))) {
			
			String flgErogVantaggiEconomici = "";
			String tipoTrasparenzaVantEcon = "";
			String sottotipoTrasparenzaVantEcon = "";
			
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EROG_VANT_ECON"))) {							
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "EROG_VANTAGGI_ECONOMICI"))) {
					flgErogVantaggiEconomici = bean.getFlgErogVantaggiEconomici() != null  ? bean.getFlgErogVantaggiEconomici() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "EROG_VANTAGGI_ECONOMICI_Doc", flgErogVantaggiEconomici);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_TIP_VANT_ECON"))) {
					tipoTrasparenzaVantEcon = bean.getTipoTrasparenzaVantEcon() != null  ? bean.getTipoTrasparenzaVantEcon() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_TIP_VANT_ECON_Doc", tipoTrasparenzaVantEcon);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_SOTTOTIP_VANT_ECON"))) {
					sottotipoTrasparenzaVantEcon = bean.getSottotipoTrasparenzaVantEcon() != null  ? bean.getSottotipoTrasparenzaVantEcon() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_SOTTOTIP_VANT_ECON_Doc", sottotipoTrasparenzaVantEcon);
				}
			}
			
			String flgInterventi = "";
			String tipoInterventi = "";
			String sottotipoInterventi = "";
			
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_INTERVENTI"))) {				 
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "INTERVENTI"))) {
					flgInterventi = bean.getFlgInterventi() != null  ? bean.getFlgInterventi() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "INTERVENTI_Doc", flgInterventi);		
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_TIP_INTERVENTI"))) {
					tipoInterventi = bean.getTipoInterventi() != null  ? bean.getTipoInterventi() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_TIP_INTERVENTI_Doc", tipoInterventi);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_SOTTOTIP_INTERVENTI"))) {
					sottotipoInterventi = bean.getSottotipoInterventi() != null  ? bean.getSottotipoInterventi() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_SOTTOTIP_INTERVENTI_Doc", sottotipoInterventi);		
				}			
			}
			
			String flgAltriProvvedimenti = "";
			String tipoAltriProvvedimenti = "";
			String sottotipoAltriProvvedimenti = "";
			
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ALTRI_PROVVED"))) {				 
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ALTR_PROVVEDIMENTI"))) {
					flgAltriProvvedimenti = bean.getFlgAltriProvvedimenti() != null  ? bean.getFlgAltriProvvedimenti() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ALTR_PROVVEDIMENTI_Doc", flgAltriProvvedimenti);		
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_TIP_ALTRI_PROVV"))) {
					tipoAltriProvvedimenti = bean.getTipoAltriProvvedimenti() != null  ? bean.getTipoAltriProvvedimenti() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_TIP_ALTRI_PROVV_Doc", tipoAltriProvvedimenti);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASPARENZA_SOTTOTIP_ALTRI_PROVV"))) {
					sottotipoAltriProvvedimenti = bean.getSottotipoAltriProvvedimenti() != null  ? bean.getSottotipoAltriProvvedimenti() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASPARENZA_SOTTOTIP_ALTRI_PROVV_Doc", sottotipoAltriProvvedimenti);		
				}			
			}
			
			String normaTrasparenzaVantEcon = "";
			String modalitaIndividuazioneBeneficiario = "";
			String estremiDocumentiPrincipaliFascicolo = "";
			String avvisiPerLaCompilazione = "";
			String ufficioCompetenteTabTrasp = "";
			String responsabileProcedimentoTabTrasp = "";
			String dataAvvioProcedimento = "";
			String oggettoProvvedimento = "";
			String durataRapportoGiuridico = "";
			String numeroMesiDurataRapporto = "";
			String dalDurataRapporto = "";
			String alDurataRapporto = "";
			String listaAllegatiObbligatoriTrasparenza = "";
			String listaIdAllegatiObbligatoriTrasparenza = "";
			String listaAllegatiNonObbligatoriTrasparenza = "";
			String listaIdAllegatiNonObbligatoriTrasparenza = "";
			
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DETTAGLIO_TRASPARENZA"))) {		
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_DETTAGLIO_NORMA_TITOLO"))) {
					normaTrasparenzaVantEcon = bean.getNormaTrasparenzaVantEcon() != null  ? bean.getNormaTrasparenzaVantEcon() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_DETTAGLIO_NORMA_TITOLO_Doc", normaTrasparenzaVantEcon);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_MOD_INDIVIDUAZIONE"))) {
					modalitaIndividuazioneBeneficiario = bean.getModalitaIndividuazioneBeneficiario() != null  ? bean.getModalitaIndividuazioneBeneficiario() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_MOD_INDIVIDUAZIONE_Doc", modalitaIndividuazioneBeneficiario);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_ESTREMI_DOC_FASC"))) {
					estremiDocumentiPrincipaliFascicolo = bean.getEstremiDocumentiPrincipaliFascicolo() != null  ? bean.getEstremiDocumentiPrincipaliFascicolo() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_ESTREMI_DOC_FASC_Doc", estremiDocumentiPrincipaliFascicolo);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_AVVISI_COMPILAZIONE"))) {
					avvisiPerLaCompilazione = bean.getAvvisiPerLaCompilazione() != null  ? bean.getAvvisiPerLaCompilazione() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_AVVISI_COMPILAZIONE_Doc", avvisiPerLaCompilazione);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_UFFICIO_COMPETENTE"))) {
					ufficioCompetenteTabTrasp = bean.getUfficioCompetenteTabTrasp() != null  ? bean.getUfficioCompetenteTabTrasp() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_UFFICIO_COMPETENTE_Doc", ufficioCompetenteTabTrasp);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_RESP_PROCEDIMENTO"))) {
					responsabileProcedimentoTabTrasp = bean.getResponsabileProcedimentoTabTrasp() != null  ? bean.getResponsabileProcedimentoTabTrasp() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_RESP_PROCEDIMENTO_Doc", responsabileProcedimentoTabTrasp);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_DATA_AVVIO_PROCED"))) {
					dataAvvioProcedimento = bean.getDataAvvioProcedimento() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDataAvvioProcedimento()) : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_DATA_AVVIO_PROCED_Doc", dataAvvioProcedimento);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_OGGETTO_PROVV"))) {
					oggettoProvvedimento = bean.getOggettoProvvedimento() != null  ? bean.getOggettoProvvedimento() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_OGGETTO_PROVV_Doc", oggettoProvvedimento);
				}			
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_TIPO_DURATA_RAPP_GIU"))) {
					durataRapportoGiuridico = bean.getDurataRapportoGiuridico() != null  ? bean.getDurataRapportoGiuridico() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_TIPO_DURATA_RAPP_GIU_Doc", durataRapportoGiuridico);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_NUM_MESI"))) {
					if (_NUM_MESI.equalsIgnoreCase(durataRapportoGiuridico)) {
						numeroMesiDurataRapporto = bean.getNumeroMesiDurataRapporto() != null  ? bean.getNumeroMesiDurataRapporto() : "";
					}
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_NUM_MESI_Doc", numeroMesiDurataRapporto);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_RAPPORTO_DAL"))) {
					if (_DA_AL.equalsIgnoreCase(durataRapportoGiuridico)) {					
						dalDurataRapporto = bean.getDalDurataRapporto() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getDalDurataRapporto()) : "";
					}
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_RAPPORTO_DAL_Doc", dalDurataRapporto);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TRASP_RAPPORTO_AL"))) {
					if (_DA_AL.equalsIgnoreCase(durataRapportoGiuridico)) {					
						alDurataRapporto = bean.getAlDurataRapporto() != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).format(bean.getAlDurataRapporto()) : "";
					}
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TRASP_RAPPORTO_AL_Doc", alDurataRapporto);
				}			
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LISTA_DESCRIZIONE_ALLEGATI_OBBLIGATORI"))) {
					listaAllegatiObbligatoriTrasparenza = bean.getListaAllegatiObbligatoriTrasparenza() != null  ? bean.getListaAllegatiObbligatoriTrasparenza() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LISTA_DESCRIZIONE_ALLEGATI_OBBLIGATORI_Doc", listaAllegatiObbligatoriTrasparenza);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LISTA_ID_DESCRIZIONE_ALLEGATI_OBBLIGATORI"))) {
					listaIdAllegatiObbligatoriTrasparenza = bean.getListaIdAllegatiObbligatoriTrasparenza() != null  ? bean.getListaIdAllegatiObbligatoriTrasparenza() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LISTA_ID_DESCRIZIONE_ALLEGATI_OBBLIGATORI_Doc", listaIdAllegatiObbligatoriTrasparenza);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LISTA_DESCRIZIONE_ALLEGATI_NON_OBBLIGATORI"))) {
					listaAllegatiNonObbligatoriTrasparenza = bean.getListaAllegatiNonObbligatoriTrasparenza() != null  ? bean.getListaAllegatiNonObbligatoriTrasparenza() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LISTA_DESCRIZIONE_ALLEGATI_NON_OBBLIGATORI_Doc", listaAllegatiNonObbligatoriTrasparenza);
				}
				if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "LISTA_ID_DESCRIZIONE_ALLEGATI_NON_OBBLIGATORI"))) {
					listaIdAllegatiNonObbligatoriTrasparenza = bean.getListaIdAllegatiNonObbligatoriTrasparenza() != null  ? bean.getListaIdAllegatiNonObbligatoriTrasparenza() : "";
					putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "LISTA_ID_DESCRIZIONE_ALLEGATI_NON_OBBLIGATORI_Doc", listaIdAllegatiNonObbligatoriTrasparenza);
				}								
			}
			
			List<DestinatarioVantaggioBean> listaBeneficiariTrasparenza = new ArrayList<DestinatarioVantaggioBean>();
			
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "BENEFICIARI_TRASPARENZA"))) {
				if(bean.getListaBeneficiariTrasparenza() != null) {
					for(DestVantaggioBean lDestVantaggioBean : bean.getListaBeneficiariTrasparenza()) {
						if(StringUtils.isNotBlank(lDestVantaggioBean.getTipoPersona()) && (StringUtils.isNotBlank(lDestVantaggioBean.getRagioneSociale()) || (StringUtils.isNotBlank(lDestVantaggioBean.getCognome()) && StringUtils.isNotBlank(lDestVantaggioBean.getNome())))) {
							DestinatarioVantaggioBean lDestinatarioVantaggioBean = new DestinatarioVantaggioBean();
							lDestinatarioVantaggioBean.setTipo(lDestVantaggioBean.getTipo());
							lDestinatarioVantaggioBean.setTipoPersona(lDestVantaggioBean.getTipoPersona());
							if("fisica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {
								lDestinatarioVantaggioBean.setCognome(lDestVantaggioBean.getCognome());
								lDestinatarioVantaggioBean.setNome(lDestVantaggioBean.getNome());
							} else if("giuridica".equalsIgnoreCase(lDestVantaggioBean.getTipoPersona())) {							
								lDestinatarioVantaggioBean.setRagioneSociale(lDestVantaggioBean.getRagioneSociale());
							}
							lDestinatarioVantaggioBean.setCodFiscalePIVA(lDestVantaggioBean.getCodFiscalePIVA());
							lDestinatarioVantaggioBean.setImporto(lDestVantaggioBean.getImporto());
							lDestinatarioVantaggioBean.setFlgPrivacy(lDestVantaggioBean.getFlgPrivacy());									
							listaBeneficiariTrasparenza.add(lDestinatarioVantaggioBean);
						}
					}
				}
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "BENEFICIARI_TRASPARENZA_Doc", new XmlUtilitySerializer().createVariabileLista(listaBeneficiariTrasparenza));		
			}		
		}
			
		/* Movimenti contabili */
		
		if(isAttivoContabilia(bean)) {	
			
			List<MovimentiContabiliaXmlBean> listaMovimentiContabilia = new ArrayList<MovimentiContabiliaXmlBean>();			
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {					
				if(bean.getListaMovimentiContabilia() != null) {
					listaMovimentiContabilia = bean.getListaMovimentiContabilia();
				}					
			}
			
			// solo se sto generando da modello oppure se è attivo il salvataggio dei movimenti AMC
			if(flgGenModello || isAttivaSalvataggioMovimentiDaAMC(bean)) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_CONTABILIA_Doc", new XmlUtilitySerializer().createVariabileLista(listaMovimentiContabilia));		
			}
		}
		
		// l'aggiornamento dei movimenti contabili di SICRA in DB va' fatto solo dopo la chiamata al servizio setMovimentiAtto di SICRA
		// MA nel caso di anteprima dei dati di spesa (generazione da modello) la variabile va' comunque passata, quindi la devo gestire anche qui
		if(isAttivoSICRA(bean)) {
			
			List<MovimentiContabiliSICRABean> listaInvioMovimentiContabiliSICRA = new ArrayList<MovimentiContabiliSICRABean>();					
			if (_FLG_SI.equalsIgnoreCase(flgSpesa)) {					
				if(bean.getListaInvioMovimentiContabiliSICRA() != null) {
					listaInvioMovimentiContabiliSICRA = bean.getListaInvioMovimentiContabiliSICRA();
				}	
			}
			
			// solo se sto generando da modello
			if(flgGenModello) { 
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "MOVIMENTO_SICRA_Doc", new XmlUtilitySerializer().createVariabileLista(listaInvioMovimentiContabiliSICRA));
			}
		}
		
		/* Dati GSA */
		
		List<MovimentiGSAXmlBean> listaMovimentiGSA = new ArrayList<MovimentiGSAXmlBean>();			
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_GSA"))) {
			if (_FLG_SI.equalsIgnoreCase(flgDatiRilevantiGSA)) {
				if(bean.getListaMovimentiGSA() != null) {
					listaMovimentiGSA = bean.getListaMovimentiGSA();
				}					
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_GSA_Doc", new XmlUtilitySerializer().createVariabileLista(listaMovimentiGSA));		
		}
		
		/* Aggregato/smistamento ACTA */
		
		String fascSmistACTA = ParametriDBUtil.getParametroDB(getSession(), "FASC_SMIST_ACTA");
		if(fascSmistACTA != null && (_MANDATORY.equalsIgnoreCase(fascSmistACTA) || _OPTIONAL.equalsIgnoreCase(fascSmistACTA))) {
			
			String flgAggregatoACTA = bean.getFlgAggregatoACTA() != null && bean.getFlgAggregatoACTA() ? "1" : "";
			String flgSmistamentoACTA = bean.getFlgSmistamentoACTA() != null && bean.getFlgSmistamentoACTA() ? "1" : "";
			String opzioneIndiceClassificazioneFascicoloACTA = "";
			if(bean.getFlgIndiceClassificazioneACTA() != null && bean.getFlgIndiceClassificazioneACTA()) {
				opzioneIndiceClassificazioneFascicoloACTA = _OPZIONE_INDICE_CLASSIFICAZIONE_ACTA;
			} else if(bean.getFlgFascicoloACTA() != null && bean.getFlgFascicoloACTA()) {
				opzioneIndiceClassificazioneFascicoloACTA = _OPZIONE_AGGREGATO_ACTA;
			}
			String codIndiceClassificazioneACTA = "";
			if(bean.getFlgPresenzaClassificazioneACTA() != null && bean.getFlgPresenzaClassificazioneACTA()) {
				codIndiceClassificazioneACTA = bean.getCodIndiceClassificazioneACTA() != null  ? bean.getCodIndiceClassificazioneACTA() : "";
			}
			String codVoceTitolarioACTA = bean.getCodVoceTitolarioACTA() != null  ? bean.getCodVoceTitolarioACTA() : "";		
			String codFascicoloACTA = bean.getCodFascicoloACTA() != null  ? bean.getCodFascicoloACTA() : "";		
			String suffissoCodFascicoloACTA = bean.getSuffissoCodFascicoloACTA() != null  ? bean.getSuffissoCodFascicoloACTA() : "";
			String codSottofascicoloACTA = bean.getCodSottofascicoloACTA() != null  ? bean.getCodSottofascicoloACTA() : "";			
			String idFascicoloACTA = bean.getIdFascicoloACTA() != null  ? bean.getIdFascicoloACTA() : "";		
			String idNodoSmistamentoACTA = bean.getIdNodoSmistamentoACTA() != null  ? bean.getIdNodoSmistamentoACTA() : "";	
			String desNodoSmistamentoACTA = bean.getDesNodoSmistamentoACTA() != null  ? bean.getDesNodoSmistamentoACTA() : "";	
		
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SCELTA_AGGR_ACTA_Ud", flgAggregatoACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SMIST_ACTA_Ud", flgSmistamentoACTA); 
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "OPZ_CLASSIF_AGGR_ACTA_Ud", opzioneIndiceClassificazioneFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_INDICE_CLASSIF_ACTA_Ud", codIndiceClassificazioneACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "COD_VOCE_TITOLARIO_ACTA_Ud", codVoceTitolarioACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_AGGREGATO_ACTA_Ud", codFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SUFF_NRO_AGGREGATO_ACTA_Ud", suffissoCodFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_SOTTOFASC_ACTA_Ud", codSottofascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_AGGREGATO_ACTA_Ud", idFascicoloACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ID_NODO_DEST_SMIST_ACTA_Ud", idNodoSmistamentoACTA);
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DES_NODO_DEST_SMIST_ACTA_Ud", desNodoSmistamentoACTA);			
		}
		
		/* Opere ADSP */
		
		salvaAttributiCustomOpereADSP(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, skipCtrlAttributiCustomCablati);
			
		/* Dati contabili ADSP */
		
		List<DatiContabiliADSPXmlBean> listaDatiContabiliADSP = new ArrayList<DatiContabiliADSPXmlBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_ADSP"))) {
			listaDatiContabiliADSP = bean.getListaDatiContabiliADSP() != null ? bean.getListaDatiContabiliADSP() : new ArrayList<DatiContabiliADSPXmlBean>();
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_ADSP_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliADSP));	
			
			String flgSavedAttoSuSistemaContabileADSP = bean.getFlgSavedAttoSuSistemaContabile() != null && bean.getFlgSavedAttoSuSistemaContabile() ? "1" : "0";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_SAVED_ATTO_SISTEMA_CONTABILE.Ud", flgSavedAttoSuSistemaContabileADSP);
			
		}
		
		/* Dati contabili ATERSIR */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_ATERSIR"))) {		
			List<DatiContabiliATERSIRBean> listaDatiContabiliATERSIR = new ArrayList<DatiContabiliATERSIRBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_ATERSIR"))) {
				listaDatiContabiliATERSIR = bean.getListaDatiContabiliATERSIR() != null ? bean.getListaDatiContabiliATERSIR() : new ArrayList<DatiContabiliATERSIRBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_ATERSIR_Doc", new XmlUtilitySerializer().createVariabileLista(listaDatiContabiliATERSIR));			
			}
		}
		
		/* Dati contabili AVB */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_AVB"))) {		
			
			List<DatiContabiliAVBBean> listaImpegniAVB = new ArrayList<DatiContabiliAVBBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_AVB_IMP"))) {
				listaImpegniAVB = bean.getListaImpegniAVB() != null ? bean.getListaImpegniAVB() : new ArrayList<DatiContabiliAVBBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_AVB_IMP_Doc", new XmlUtilitySerializer().createVariabileLista(listaImpegniAVB));			
			}
			
			List<DatiContabiliAVBBean> listaAccertamentiAVB = new ArrayList<DatiContabiliAVBBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_AVB_ACC"))) {
				listaAccertamentiAVB = bean.getListaAccertamentiAVB() != null ? bean.getListaAccertamentiAVB() : new ArrayList<DatiContabiliAVBBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_AVB_ACC_Doc", new XmlUtilitySerializer().createVariabileLista(listaAccertamentiAVB));			
			}
			
			List<DatiContabiliAVBBean> listaLiquidazioniAVB = new ArrayList<DatiContabiliAVBBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_CONTABILI_AVB_LIQ"))) {
				listaLiquidazioniAVB = bean.getListaLiquidazioniAVB() != null ? bean.getListaLiquidazioniAVB() : new ArrayList<DatiContabiliAVBBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_AVB_LIQ_Doc", new XmlUtilitySerializer().createVariabileLista(listaLiquidazioniAVB));			
			}
		}
				
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI-CONT-LIQ-AVB"))) {	
			
			List<DatiLiquidazioneAVBBean> listaLiquidazioniBeanAVB = bean.getListaDatiLiquidazioneAVB() != null ? bean.getListaDatiLiquidazioneAVB() : new ArrayList<DatiLiquidazioneAVBBean>();
			List<DatiLiquidazioneAVBXmlBean> listaLiquidazioniAVBxml = new ArrayList<DatiLiquidazioneAVBXmlBean>();
			for (DatiLiquidazioneAVBBean liquidazioneAVBfromExcel : listaLiquidazioniBeanAVB) {
				if (liquidazioneAVBfromExcel.isUploaded()) {
					AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
//					DatiLiquidazioneAVBXmlBean liquidazioneExcelToSave = liquidazioneAVBfromExcel;
//				// SALVATAGGIO DEFINITIVO NELLO STORAGE DELL'EXCEL - START
					String uriExcel = liquidazioneAVBfromExcel.getUriExcel();
					File lFileToSave = StorageImplementation.getStorage().extractFile(uriExcel);
					FileSavedIn lFileSavedIn = new FileSavedIn();
					lFileSavedIn.setSaved(lFileToSave);
					Locale local = new Locale(loginBean.getLinguaApplicazione());
					SalvataggioFile lSalvataggioFile = new SalvataggioFile();
					FileSavedOut out = lSalvataggioFile.savefile(local, loginBean, lFileSavedIn);
//				// FINE
//				// AGGIORNO L'URI DEL FILE CON QUELLO NELLO STORAGE DEFINITIVO
//					liquidazioneExcelToSave.setUriExcel(out.getUri());
					
					List<DisimpegnoAVBBean> listaDisimpegni = liquidazioneAVBfromExcel.getListaDisimpegni() != null ? liquidazioneAVBfromExcel.getListaDisimpegni() : new ArrayList<DisimpegnoAVBBean>();
					List<ImputazioneContabileAVBBean> listaImputazioneContabile = liquidazioneAVBfromExcel.getListaImputazioneContabile() != null ? liquidazioneAVBfromExcel.getListaImputazioneContabile() : new ArrayList<ImputazioneContabileAVBBean>();
					List<RifSpesaEntrataAVBBean> listaRifSpesaEntrata = liquidazioneAVBfromExcel.getListaRifSpesaEntrata() != null ? liquidazioneAVBfromExcel.getListaRifSpesaEntrata() : new ArrayList<RifSpesaEntrataAVBBean>();
					int count = 0;
					if (listaDisimpegni.size() > listaImputazioneContabile.size()) {
						if (listaDisimpegni.size() > listaRifSpesaEntrata.size()) {
							count = listaDisimpegni.size();
						} else {
							count = listaRifSpesaEntrata.size();
						}
					} else {
						if (listaImputazioneContabile.size() > listaRifSpesaEntrata.size()) {
							count = listaImputazioneContabile.size();
						} else {
							count = listaRifSpesaEntrata.size();
						}
					}
					for (int i=0; i < count; i++) {
						DatiLiquidazioneAVBXmlBean liquidazioneExcelToSave = new DatiLiquidazioneAVBXmlBean();
						liquidazioneExcelToSave = (DatiLiquidazioneAVBXmlBean) liquidazioneAVBfromExcel.clone();
						liquidazioneExcelToSave.setUriExcel(out.getUri());
						// DISIMPEGNO
						liquidazioneExcelToSave.setDisimpegnoCapitolo(listaDisimpegni.size() > i ? listaDisimpegni.get(i).getDisimpegnoCapitolo() : "");
						liquidazioneExcelToSave.setDisimpegnoImpegnoAnno(listaDisimpegni.size() > i ? listaDisimpegni.get(i).getDisimpegnoImpegnoAnno() : "");
						liquidazioneExcelToSave.setDisimpegnoImporto(listaDisimpegni.size() > i ? listaDisimpegni.get(i).getDisimpegnoImporto() : "");
						liquidazioneExcelToSave.setDisimpegnoSub(listaDisimpegni.size() > i ? listaDisimpegni.get(i).getDisimpegnoSub() : "");
						// IMPUTAZIONE CONTABILE
						liquidazioneExcelToSave.setImputazioneContabileCdc(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileCdc() : "");
						liquidazioneExcelToSave.setImputazioneContabileCapitolo(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileCapitolo() : "");
						liquidazioneExcelToSave.setImputazioneContabileCodSiope(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileCodSiope() : "");
						liquidazioneExcelToSave.setImputazioneContabileImpegno(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileImpegno() : "");
						liquidazioneExcelToSave.setImputazioneContabileImporto(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileImporto() : "");
						liquidazioneExcelToSave.setImputazioneContabileMacroaggregato(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileMacroaggregato() : "");
						liquidazioneExcelToSave.setImputazioneContabileMissione(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileMissione() : "");
						liquidazioneExcelToSave.setImputazioneContabileProgramma(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileProgramma() : "");
						liquidazioneExcelToSave.setImputazioneContabileRifBilancio(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileRifBilancio() : "");
						liquidazioneExcelToSave.setImputazioneContabileTitolo(listaImputazioneContabile.size() > i ? listaImputazioneContabile.get(i).getImputazioneContabileTitolo() : "");
						// RIFSPESAENTRATA
						liquidazioneExcelToSave.setSpesaEntrataDetNum(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataDetNum() : "");
						liquidazioneExcelToSave.setSpesaEntrataDetAccertamento(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataDetAccertamento() : "");
						liquidazioneExcelToSave.setSpesaEntrataCapitolo(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataCapitolo() : "");
						liquidazioneExcelToSave.setSpesaEntrataReversale(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataReversale() : "");
						liquidazioneExcelToSave.setSpesaEntrataImportoSomministrato(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataImportoSomministrato() : "");
						liquidazioneExcelToSave.setSpesaEntrataImportDaSomministrare(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataImportDaSomministrare() : "");
						liquidazioneExcelToSave.setSpesaEntrataModTempOp(listaRifSpesaEntrata.size() > i ? listaRifSpesaEntrata.get(i).getSpesaEntrataModTempOp() : "");
						
						listaLiquidazioniAVBxml.add(liquidazioneExcelToSave);
					}
				}
				
			}
			if (listaLiquidazioniAVBxml.size() > 0) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_LIQUIDAZIONI_AVB_Doc", new XmlUtilitySerializer().createVariabileLista(listaLiquidazioniAVBxml));
			}
			
			List<String> listaXlsToDeleteDatiLiqAVB = new ArrayList<String>();
			listaXlsToDeleteDatiLiqAVB = bean.getListaXlsToDeleteDatiLiqAVB() != null ? bean.getListaXlsToDeleteDatiLiqAVB() : new ArrayList<String>();
			String xlsLiquidazioniAVBToDelete = new String ();
			for (int i=0; i<listaXlsToDeleteDatiLiqAVB.size(); i++) {
				xlsLiquidazioniAVBToDelete += listaXlsToDeleteDatiLiqAVB.get(i);
				if (i != listaXlsToDeleteDatiLiqAVB.size()-1) {
					xlsLiquidazioniAVBToDelete += ";";
				}
			}
			if (!xlsLiquidazioniAVBToDelete.equals("")) {
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "#XlsLiquidazioniAVBToDelete", xlsLiquidazioniAVBToDelete);	
			}
			
		}
		
		// solo se sto generando da modello passo le liste degli allegati parte integrante separati
		if(flgGenModello) {			
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FlgAllegatiParteIntUniti", bean.getFlgAllegatiParteIntUniti() != null && bean.getFlgAllegatiParteIntUniti() ? "1" : "");
			
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparati = new ArrayList<AllegatoParteIntSeparatoBean>();		
			if(bean.getListaAllegatiParteIntSeparati() != null) {
				listaAllegatiParteIntSeparati = bean.getListaAllegatiParteIntSeparati();
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ListaAllegatiParteIntSeparati", new XmlUtilitySerializer().createVariabileLista(listaAllegatiParteIntSeparati));
						
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FlgAllegatiParteIntUnitiXPubbl", bean.getFlgAllegatiParteIntUnitiXPubbl() != null && bean.getFlgAllegatiParteIntUnitiXPubbl() ? "1" : "");
			
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();		
			if(bean.getListaAllegatiParteIntSeparatiXPubbl() != null) {
				listaAllegatiParteIntSeparatiXPubbl = bean.getListaAllegatiParteIntSeparatiXPubbl();
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ListaAllegatiParteIntSeparatiXPubbl", new XmlUtilitySerializer().createVariabileLista(listaAllegatiParteIntSeparatiXPubbl));			
		}

	}
	
	private void salvaAttributiCustomPubblicazioneNotifiche(HashSet<String> setAttributiCustomCablati, NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean skipCtrlAttributiCustomCablati) throws Exception {
		
		
		/* Pubblicazione/notifiche - Opzioni testo */	
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "OPZIONI_TESTO_ATTO"))) {
			
			String flgEscludiPremesseFisseDaTestoAtto = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ESCLUDI_PREMESSE_FISSE_DA_TESTO_ATTO"))) {
				flgEscludiPremesseFisseDaTestoAtto = bean.getFlgEscludiPremesseFisseDaTestoAtto() != null && bean.getFlgEscludiPremesseFisseDaTestoAtto() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_ESCLUDI_PREMESSE_FISSE_DA_TESTO_ATTO_Doc", flgEscludiPremesseFisseDaTestoAtto);
			}
			
			String flgEscludiOggettoDaTestoAtto = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ESCLUDI_OGGETTO_DA_TESTO_ATTO"))) {
				flgEscludiOggettoDaTestoAtto = bean.getFlgEscludiOggettoDaTestoAtto() != null && bean.getFlgEscludiOggettoDaTestoAtto() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_ESCLUDI_OGGETTO_DA_TESTO_ATTO_Doc", flgEscludiOggettoDaTestoAtto);
			}
			
		}
				
		/* Pubblicazione/notifiche - Opzioni avanzate testo */	
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "OPZIONI_AVANZATE_TESTO_ATTO"))) {
			
			String scrittaTraPremessaEDispositivoAtto = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO"))) {
				scrittaTraPremessaEDispositivoAtto = bean.getScrittaTraPremessaEDispositivoAtto() != null  ? bean.getScrittaTraPremessaEDispositivoAtto() : "";				
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO_Doc", scrittaTraPremessaEDispositivoAtto);
			}
			
			String numRighePreScrittaTraPremessaEDispositivoAtto = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_RIGHE_PRE_SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO"))) {
				numRighePreScrittaTraPremessaEDispositivoAtto = bean.getNumRighePreScrittaTraPremessaEDispositivoAtto() != null  ? bean.getNumRighePreScrittaTraPremessaEDispositivoAtto() : "";				
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_RIGHE_PRE_SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO_Doc", numRighePreScrittaTraPremessaEDispositivoAtto);
			}
			
			String numRighePostScrittaTraPremessaEDispositivoAtto = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_RIGHE_POST_SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO"))) {
				numRighePostScrittaTraPremessaEDispositivoAtto = bean.getNumRighePostScrittaTraPremessaEDispositivoAtto() != null  ? bean.getNumRighePostScrittaTraPremessaEDispositivoAtto() : "";				
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_RIGHE_POST_SCRITTA_TRA_PREMESSA_E_DISPOSITIVO_ATTO_Doc", numRighePostScrittaTraPremessaEDispositivoAtto);
			}
		
		}  
	
		/* Pubblicazione/notifiche - Opzioni iter */	
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "OPZIONI_ITER"))) {
			
			String flgAcquisitiVistiTornaVerificaUoProp = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_ACQUISITI_VISTI_TORNA_VERIFICA_UO_PROP"))) {
				flgAcquisitiVistiTornaVerificaUoProp = bean.getFlgAcquisitiVistiTornaVerificaUoProp() != null && bean.getFlgAcquisitiVistiTornaVerificaUoProp() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_ACQUISITI_VISTI_TORNA_VERIFICA_UO_PROP_Doc", flgAcquisitiVistiTornaVerificaUoProp);
			}
			
		}
		
		/* Pubblicazione/notifiche - Opzioni visualizzazione */	
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "OPZIONI_VISIBILITA"))) {
			
			String flgVisibilitaPubblPostAdozione = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_VISIBILITA_PUBBL_POST_ADOZIONE"))) {
				flgVisibilitaPubblPostAdozione = bean.getFlgVisibilitaPubblPostAdozione() != null && bean.getFlgVisibilitaPubblPostAdozione() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_VISIBILITA_PUBBL_POST_ADOZIONE_Doc", flgVisibilitaPubblPostAdozione);
			}
			
		}
		
		/* Pubblicazione/notifiche - Pubblicazione all'Albo */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_ALBO"))) {

			String flgPubblAlbo = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO"))) {
				flgPubblAlbo = bean.getFlgPubblAlbo() != null  ? bean.getFlgPubblAlbo() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_ALBO_Doc", flgPubblAlbo);
			}
		
			String tipoDecorrenzaPubblAlbo = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_ALBO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					tipoDecorrenzaPubblAlbo = bean.getTipoDecorrenzaPubblAlbo() != null  ? bean.getTipoDecorrenzaPubblAlbo() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_DECORRENZA_PUBBL_ALBO_Doc", tipoDecorrenzaPubblAlbo);
			}
		
			String dataPubblAlboDal = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_DAL"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO") || _FLG_SI.equals(flgPubblAlbo))) {
					if (skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_ALBO")) || _DECORR_PUBBL_POST.equals(tipoDecorrenzaPubblAlbo)) {
						dataPubblAlboDal = bean.getDataPubblAlboDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblAlboDal()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_ALBO_DAL_Doc", dataPubblAlboDal);
			}
			
			String numGiorniPubblAlbo = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_GIORNI_PUBBL_ALBO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					numGiorniPubblAlbo = bean.getNumGiorniPubblAlbo() != null  ? bean.getNumGiorniPubblAlbo() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_GIORNI_PUBBL_ALBO_Doc", numGiorniPubblAlbo);
			}
		
			String dataPubblAlboAl = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_AL"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					dataPubblAlboAl = bean.getDataPubblAlboAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblAlboAl()) : "";								
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_ALBO_AL_Doc", dataPubblAlboAl);
			}
		
			String flgUrgentePubblAlbo = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_URGENTE_PUBBL_ALBO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					flgUrgentePubblAlbo = bean.getFlgUrgentePubblAlbo() != null && bean.getFlgUrgentePubblAlbo() ? "1" : "";							
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_URGENTE_PUBBL_ALBO_Doc", flgUrgentePubblAlbo);
			}
		
			String dataPubblAlboEntro = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_ENTRO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					if ("1".equals(flgUrgentePubblAlbo)) {
						dataPubblAlboEntro = bean.getDataPubblAlboEntro() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblAlboEntro()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_ALBO_ENTRO_Doc", dataPubblAlboEntro);
			}
			
			String dataRipubblAlboDal = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "RIPUBBL_ALBO_DAL"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					dataRipubblAlboDal = bean.getDataRipubblAlboDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRipubblAlboDal()) : "";											
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "RIPUBBL_ALBO_DAL_Doc", dataRipubblAlboDal);
			}
			
			String numGiorniRipubblAlbo = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "NRO_GIORNI_RIPUBBL_ALBO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					numGiorniRipubblAlbo = bean.getNumGiorniRipubblAlbo() != null  ? bean.getNumGiorniRipubblAlbo() : "";
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_GIORNI_RIPUBBL_ALBO_Doc", numGiorniRipubblAlbo);
			}
		
			String dataRipubblAlboAl = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "RIPUBBL_ALBO_AL"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) || _FLG_SI.equals(flgPubblAlbo)) {
					dataRipubblAlboAl = bean.getDataRipubblAlboAl() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRipubblAlboAl()) : "";								
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "RIPUBBL_ALBO_AL_Doc", dataRipubblAlboAl);
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione in Amm. Trasparente */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_AMM_TRASP"))) {

			String flgPubblAmmTrasp = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA"))) {
				flgPubblAmmTrasp = bean.getFlgPubblAmmTrasp() != null  ? bean.getFlgPubblAmmTrasp() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_IN_TRASPARENZA_Doc", flgPubblAmmTrasp);
			}
			
			String annoTerminePubblAmmTrasp = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_TERMINE_PUBBL_IN_TRASPARENZA"))) {				
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA")) || _FLG_SI.equals(flgPubblAmmTrasp)) {
					annoTerminePubblAmmTrasp = bean.getAnnoTerminePubblAmmTrasp() != null  ? bean.getAnnoTerminePubblAmmTrasp() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_TERMINE_PUBBL_IN_TRASPARENZA_Doc", annoTerminePubblAmmTrasp);
			}
		
			String sezionePubblAmmTrasp = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SEZ1_PUBBL_IN_TRASPARENZA"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA")) || _FLG_SI.equals(flgPubblAmmTrasp)) {
					sezionePubblAmmTrasp = bean.getSezionePubblAmmTrasp() != null  ? bean.getSezionePubblAmmTrasp() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SEZ1_PUBBL_IN_TRASPARENZA_Doc", sezionePubblAmmTrasp);
			}
		
			String sottoSezionePubblAmmTrasp = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SEZ2_PUBBL_IN_TRASPARENZA"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_IN_TRASPARENZA")) || _FLG_SI.equals(flgPubblAmmTrasp)) {
					sottoSezionePubblAmmTrasp = bean.getSottoSezionePubblAmmTrasp() != null  ? bean.getSottoSezionePubblAmmTrasp() : "";			
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "SEZ2_PUBBL_IN_TRASPARENZA_Doc", sottoSezionePubblAmmTrasp);
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione al B.U. */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_PUBBL_BUR"))) {

			String flgPubblBUR = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR"))) {
				flgPubblBUR = bean.getFlgPubblBUR() != null  ? bean.getFlgPubblBUR() : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_PUBBL_BUR_Doc", flgPubblBUR);
			}
		
			String annoTerminePubblBUR = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ANNO_TERMINE_PUBBL_BUR"))) {				
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR") || _FLG_SI.equals(flgPubblBUR))) {
					annoTerminePubblBUR = bean.getAnnoTerminePubblBUR() != null  ? bean.getAnnoTerminePubblBUR() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_TERMINE_PUBBL_BUR_Doc", annoTerminePubblBUR);
			}
		
			String tipoDecorrenzaPubblBUR = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_BUR"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR")) || _FLG_SI.equals(flgPubblBUR)) {
					tipoDecorrenzaPubblBUR = bean.getTipoDecorrenzaPubblBUR() != null  ? bean.getTipoDecorrenzaPubblBUR() : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_DECORRENZA_PUBBL_BUR_Doc", tipoDecorrenzaPubblBUR);
			}
		
			String dataPubblBURDal = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_BUR_DAL"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR")) || _FLG_SI.equals(flgPubblBUR)) {
					if (_DECORR_PUBBL_POST.equals(tipoDecorrenzaPubblBUR)) {
						dataPubblBURDal = bean.getDataPubblBURDal() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblBURDal()) : "";			
					}
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_BUR_DAL_Doc", dataPubblBURDal);
			}
		
			String flgUrgentePubblBUR = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_URGENTE_PUBBL_BUR"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR")) || _FLG_SI.equals(flgPubblBUR)) {
					flgUrgentePubblBUR = bean.getFlgUrgentePubblBUR() != null && bean.getFlgUrgentePubblBUR() ? "1" : "";				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_URGENTE_PUBBL_BUR_Doc", flgUrgentePubblBUR);
			}
		
			String dataPubblBUREntro = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_BUR_ENTRO"))) {
				if(skipCtrlAttributiCustomCablati || (!showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_BUR")) || _FLG_SI.equals(flgPubblBUR)) {
					if ("1".equals(flgUrgentePubblBUR)) {
						dataPubblBUREntro = bean.getDataPubblBUREntro() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataPubblBUREntro()) : "";			
					}				
				}
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "PUBBL_BUR_ENTRO_Doc", dataPubblBUREntro);		
			}

		}

		/* Pubblicazione/notifiche - Pubblicazione sul notiziario */
		
		String flgPubblNotiziario = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO"))) {
			flgPubblNotiziario = bean.getFlgPubblNotiziario() != null  ? bean.getFlgPubblNotiziario() : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO_Doc", flgPubblNotiziario);
		}
		
		/* Pubblicazione/notifiche - Esecutività */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DATI_ESECUTIVITA"))) {
			
			String flgImmediatamenteEseguibile = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "FLG_IMMEDIATAMENTE_ESEGUIBILE"))) {
				flgImmediatamenteEseguibile = bean.getFlgImmediatamenteEseguibile() != null && bean.getFlgImmediatamenteEseguibile() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_IMMEDIATAMENTE_ESEGUIBILE_Ud", flgImmediatamenteEseguibile);
			}
		
			// la data di esecutività non si salva perchè è un dato sempre calcolato
		
			String motiviImmediatamenteEseguibile = "";
			String motiviImmediatamenteEseguibileFile = "";
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "MOTIVI_IE"))) {
				if ("1".equals(flgImmediatamenteEseguibile)) {
					motiviImmediatamenteEseguibile = bean.getMotiviImmediatamenteEseguibile();
					motiviImmediatamenteEseguibileFile = bean.getMotiviImmediatamenteEseguibileFile();
				}
				putVariabileCKEditorSezioneCache(sezioneCacheAttributiDinamici, "MOTIVI_IE_Ud", motiviImmediatamenteEseguibile, "MOTIVI_IE_FILE_Ud", motiviImmediatamenteEseguibileFile); 
			}

		}

		/* Pubblicazione/notifiche - Da notificare a */
		
		List<SimpleValueBean> listaDestNotificaAtto = new ArrayList<SimpleValueBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "IND_EMAIL_DEST_NOTIFICA_ATTO"))) {
			if(StringUtils.isNotBlank(bean.getListaDestNotificaAtto())) {
				String[] destinatari = IndirizziEmailSplitter.split(bean.getListaDestNotificaAtto());
				for(int i = 0; i < destinatari.length; i++) {
					SimpleValueBean lSimpleValueBean = new SimpleValueBean();
					lSimpleValueBean.setValue(destinatari[i]);
					listaDestNotificaAtto.add(lSimpleValueBean);
				}			
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "IND_EMAIL_DEST_NOTIFICA_ATTO_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestNotificaAtto)); 					
		}
		
		/* Pubblicazione/notifiche - Notifica a mezzo messi */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_NOTIFICA_MESSI"))) {
			
			String flgMessiNotificatori = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_MESSI_NOTIFICATORI"))) {
				flgMessiNotificatori = bean.getFlgMessiNotificatori() != null && bean.getFlgMessiNotificatori() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_MESSI_NOTIFICATORI_Doc", flgMessiNotificatori);
			}
			
			List<DestinatariNotificaMessiXmlBean> listaDestinatariNotificaMessi = new ArrayList<DestinatariNotificaMessiXmlBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_NOTIFICA_MESSI"))) {
				listaDestinatariNotificaMessi = bean.getListaDestinatariNotificaMessi() != null ? bean.getListaDestinatariNotificaMessi() : new ArrayList<DestinatariNotificaMessiXmlBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_NOTIFICA_MESSI_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariNotificaMessi));			
			}
		}
		
		/* Pubblicazione/notifiche - Notifica PEC */
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "SEZ_NOTIFICA_PEC"))) {
					
			String flgNotificaPEC = "";		
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_FLG_NOTIFICA_PEC"))) {
				flgNotificaPEC = bean.getFlgNotificaPEC() != null && bean.getFlgNotificaPEC() ? "1" : "";
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_FLG_NOTIFICA_PEC_Doc", flgNotificaPEC);
			}
			
			List<DestinatariNotificaPECXmlBean> listaDestinatariNotificaPEC = new ArrayList<DestinatariNotificaPECXmlBean>();
			if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DESTINATARI_NOTIFICA_PEC"))) {
				listaDestinatariNotificaPEC = bean.getListaDestinatariNotificaPEC() != null ? bean.getListaDestinatariNotificaPEC() : new ArrayList<DestinatariNotificaPECXmlBean>();
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DESTINATARI_NOTIFICA_PEC_Doc", new XmlUtilitySerializer().createVariabileLista(listaDestinatariNotificaPEC));			
			}
		}
		
		/* Pubblicazione/notifiche - Visibilità pubblica post adozione */
		
		String flgVisibPubblicaPostAdozione = "";		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_VISIB_PUBBLICA_POST_ADOZIONE"))) {
			flgVisibPubblicaPostAdozione = bean.getFlgVisibPubblicaPostAdozione() != null && bean.getFlgVisibPubblicaPostAdozione() ? "1" : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TASK_RESULT_2_VISIB_PUBBLICA_POST_ADOZIONE_Doc", flgVisibPubblicaPostAdozione);
		}		

	}
	
	private void salvaAttributiCustomOpereADSP(HashSet<String> setAttributiCustomCablati, NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean skipCtrlAttributiCustomCablati) throws Exception {
		
		List<PeriziaXmlBean> listaOpereADSP = new ArrayList<PeriziaXmlBean>();
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "PERIZIA_ADSP"))) {
			if (bean.getListaOpereADSP() != null) {
				for (PeriziaBean perizia : bean.getListaOpereADSP()) {				
					PeriziaXmlBean periziaBean = new PeriziaXmlBean();
					periziaBean.setPerizia(perizia.getPerizia());
					periziaBean.setDescrizione(perizia.getDescrizione());
					listaOpereADSP.add(periziaBean);
				}			
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "PERIZIA_ADSP_Ud", new XmlUtilitySerializer().createVariabileLista(listaOpereADSP));	
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
	
	private void putVariabileCKEditorSezioneCache(SezioneCache sezioneCache, String nomeVariabile, String valoreSemplice, String nomeVariabileFile, String valoreSempliceFile) throws Exception {
		// Salvo il testo del ckeditor
		int pos = getPosVariabileSezioneCache(sezioneCache, nomeVariabile);
		if(pos != -1) {
			sezioneCache.getVariabile().get(pos).setValoreSemplice(valoreSemplice);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabile, valoreSemplice));
		}
		// Salvo il file alternativo al testo
		String valoreSempliceFileDaSalvare = "";
		if (valoreSempliceFile != null && StringUtils.isNotBlank(valoreSempliceFile)) {
			valoreSempliceFileDaSalvare = valoreSempliceFile;
			Gson lGson = new Gson();  
			FileDaIniettareBean fileDaIniettare = lGson.fromJson(valoreSempliceFileDaSalvare, FileDaIniettareBean.class);
			if (fileDaIniettare.getFileIniezioneFile() != null && fileDaIniettare.getFileIniezioneFile().getIsChanged() != null && fileDaIniettare.getFileIniezioneFile().getIsChanged()) {
				AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
				Locale local = new Locale(lAurigaLoginBean.getLinguaApplicazione());
				File lFileToSave = StorageImplementation.getStorage().extractFile(fileDaIniettare.getFileIniezioneFile().getUriFile());
				FileSavedIn lFileSavedIn = new FileSavedIn();
				lFileSavedIn.setSaved(lFileToSave);
				SalvataggioFile lSalvataggioFile = new SalvataggioFile();
				FileSavedOut out = lSalvataggioFile.savefile(local, lAurigaLoginBean, lFileSavedIn);
				fileDaIniettare.getFileIniezioneFile().setUriFile(out.getUri());
				valoreSempliceFileDaSalvare = lGson.toJson(fileDaIniettare);
			}
		}
		int posFile = getPosVariabileSezioneCache(sezioneCache, nomeVariabileFile);
		if(posFile != -1) {
			sezioneCache.getVariabile().get(posFile).setValoreSemplice(valoreSempliceFileDaSalvare);			
		} else {
			sezioneCache.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice(nomeVariabileFile, valoreSempliceFileDaSalvare));
		}
	
	}
	
	// questa è vecchia e non andrebbe più usata
	private String getDatiXModelliPratica(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
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
	
	public String getDatiXGenDaModello(NuovaPropostaAtto2CompletaBean bean, String nomeModello, Boolean flgVersOmissis) throws Exception {
		
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
		
		String nomeModelloDatiContabili = ParametriDBUtil.getParametroDB(getSession(), "NOME_MODELLO_DATI_CONT");
		if(isClienteADSP() && StringUtils.isNotBlank(nomeModelloDatiContabili) && nomeModelloDatiContabili.equals(nomeModello) && bean.getFlgSpesa() != null && _FLG_SI.equalsIgnoreCase(bean.getFlgSpesa())) {			
			// Prospetto contabile ADSP: devo chiamare il WS per recuperare i movimenti contabili da passare al modello 
			getContabilitaADSPDataSource().getMovimentiDefinitivi(bean);
			if(bean.getListaDatiContabiliDefinitiviADSP()!=null) {
				putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "DATI_CONTABILI_ADSP_DEF_Doc", new XmlUtilitySerializer().createVariabileLista(bean.getListaDatiContabiliDefinitiviADSP()));	
			}					
		}
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "IsAnteprima", bean.getFlgAnteprima() != null && bean.getFlgAnteprima() ? "1" : "");
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VersioneFinale", bean.getFlgVersioneFinale() != null && bean.getFlgVersioneFinale() ? "1" : "");
		
		if(StringUtils.isNotBlank(getExtraparams().get("esitoTask"))) {
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "#EsitoTask", getExtraparams().get("esitoTask"));		
		}
		
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VersioneOmissis", flgVersOmissis != null && flgVersOmissis ? "1" : "");					
		
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
	
	public FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		
		if(StringUtils.isNotBlank(bean.getIdModello())) {
			
			/*
			if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
				String idProcessToForcePubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_PROC_TO_FORCE_PUBBL");
				if(idProcessToForcePubbl != null && bean.getIdProcess() != null && idProcessToForcePubbl.equals(bean.getIdProcess()) 
				   && bean.getDataInizioPubblicazione() != null && bean.getGiorniPubblicazione() != null) {
					pubblica(bean);
				}
			}
			*/
			
			boolean flgAllegatiParteIntUnitiVersIntegrale = false;
			boolean flgAllegatiParteIntUnitiVersXPubbl = false;
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersIntegrale = new ArrayList<AllegatoParteIntSeparatoBean>();
			List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();
			if (bean.getListaAllegati() != null) {
				boolean flgPubblicaAllegatiSeparati = bean.getFlgPubblicaAllegatiSeparati() != null && bean.getFlgPubblicaAllegatiSeparati(); // se è true tutti gli allegati sono da pubblicare separatamente		
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()){
					boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
					boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
					boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();				
					if (flgParteDispositivo) { // se è parte integrante						
						if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
							// se è la vers. integrale
							if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
								lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
								lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersIntegrale.add(lAllegatoParteIntSeparatoBean);						
							} else {
								flgAllegatiParteIntUnitiVersIntegrale = true;
							}
						} else if(!flgNoPubblAllegato) {
							// se è la vers. per la pubbl. e l'allegato non è escluso dalla pubblicazione
							if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
								if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
									AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBeanOmissis = new AllegatoParteIntSeparatoBean();
									lAllegatoParteIntSeparatoBeanOmissis.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
									lAllegatoParteIntSeparatoBeanOmissis.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
									lAllegatoParteIntSeparatoBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
									lAllegatoParteIntSeparatoBeanOmissis.setImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null);
									lAllegatoParteIntSeparatoBeanOmissis.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getAlgoritmo() : null);
									lAllegatoParteIntSeparatoBeanOmissis.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getEncoding() : null);
									listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBeanOmissis);	
								} else {
									AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
									lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
									lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
									lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
									lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
									lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
									lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
									listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBean);	
								}
							} else {
								flgAllegatiParteIntUnitiVersXPubbl = true;
							}
						}
					}
				}
			}
			
			if(bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
				// se è la vers. integrale
				bean.setFlgAllegatiParteIntUniti(flgAllegatiParteIntUnitiVersIntegrale);
				bean.setListaAllegatiParteIntSeparati(listaAllegatiParteIntSeparatiVersIntegrale);
				bean.setFlgAllegatiParteIntUnitiXPubbl(null);
				bean.setListaAllegatiParteIntSeparatiXPubbl(null);
			} else {
				// se è la vers. per la pubbl.
				bean.setFlgAllegatiParteIntUniti(null);
				bean.setListaAllegatiParteIntSeparati(null);
				bean.setFlgAllegatiParteIntUnitiXPubbl(flgAllegatiParteIntUnitiVersXPubbl);
				bean.setListaAllegatiParteIntSeparatiXPubbl(listaAllegatiParteIntSeparatiVersXPubbl);
			}
			
			if (getExtraparams().get("generaVersioneModificabile") != null && getExtraparams().get("generaVersioneModificabile").equalsIgnoreCase("true")){
				return generaDispositivoDaModello(bean, false);
			} else {
				return generaDispositivoDaModello(bean, true);
			}
		}
		
		return null;
	}
	
	private FileDaFirmareBean generaDispositivoDaModello(NuovaPropostaAtto2CompletaBean bean, boolean flgConvertiInPdf) throws Exception {
		logger.debug("#######INIZIO generaDispositivoDaModello######");
		if(StringUtils.isNotBlank(bean.getIdModello())) {
			
			boolean flgMostraDatiSensibili = bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili();
			logger.debug("#######INIZIO getDatiXGenDaModello######");
			if(flgMostraDatiSensibili) {				
				if(bean.getFilePdfAtto() != null && StringUtils.isNotBlank(bean.getFilePdfAtto().getUriFile()) && bean.getFilePdfAtto().getInfoFile() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName().toLowerCase().endsWith(".pdf")) {
					FileDaFirmareBean lFileDispositivoBean = new FileDaFirmareBean();
					lFileDispositivoBean.setNomeFile(getPrefixRegNum(bean) + "TESTO_VERS_INTEGRALE.pdf");
					lFileDispositivoBean.setUri(bean.getFilePdfAtto().getUriFile());
					lFileDispositivoBean.setInfoFile(bean.getFilePdfAtto().getInfoFile());
					lFileDispositivoBean.setUriFileOdtGenerato(null);
					return lFileDispositivoBean;
				} 
			} else {
				if(bean.getFilePdfAttoOmissis() != null && StringUtils.isNotBlank(bean.getFilePdfAttoOmissis().getUriFile()) && bean.getFilePdfAttoOmissis().getInfoFile() != null&& bean.getFilePdfAttoOmissis().getInfoFile().getCorrectFileName() != null  && bean.getFilePdfAttoOmissis().getInfoFile().getCorrectFileName().toLowerCase().endsWith(".pdf")) {
					FileDaFirmareBean lFileDispositivoOmissisBean = new FileDaFirmareBean();
					lFileDispositivoOmissisBean.setNomeFile(getPrefixRegNum(bean) + "TESTO_VERS_X_PUBBLICAZIONE.pdf");
					lFileDispositivoOmissisBean.setUri(bean.getFilePdfAttoOmissis().getUriFile());
					lFileDispositivoOmissisBean.setInfoFile(bean.getFilePdfAttoOmissis().getInfoFile());
					lFileDispositivoOmissisBean.setUriFileOdtGenerato(null);
					return lFileDispositivoOmissisBean;
				} else if(bean.getFilePdfAtto() != null && StringUtils.isNotBlank(bean.getFilePdfAtto().getUriFile()) && bean.getFilePdfAtto().getInfoFile() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName() != null && bean.getFilePdfAtto().getInfoFile().getCorrectFileName().toLowerCase().endsWith(".pdf")) {
					FileDaFirmareBean lFileDispositivoOmissisBean = new FileDaFirmareBean();
					lFileDispositivoOmissisBean.setNomeFile(getPrefixRegNum(bean) + "TESTO_VERS_X_PUBBLICAZIONE.pdf");
					lFileDispositivoOmissisBean.setUri(bean.getFilePdfAtto().getUriFile());
					lFileDispositivoOmissisBean.setInfoFile(bean.getFilePdfAtto().getInfoFile());
					lFileDispositivoOmissisBean.setUriFileOdtGenerato(null);
					return lFileDispositivoOmissisBean;
				} 
			}
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), !flgMostraDatiSensibili); // DISPOSITIVO_DETERMINA
			logger.debug("#######FINE getDatiXGenDaModello######");
			
			// Ho tolto la parte cablata (vedi il vecchio generaDispositivoDaModelloCablato) e passo direttamente la SezioneCache per l'iniezione nel modello		
			logger.debug("#######INIZIO createMapToFillTemplateFromSezioneCache######");
			Map<String, Object> model = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			logger.debug("#######FINE createMapToFillTemplateFromSezioneCache######");
			
			logger.debug("#######INIZIO fillFreeMarkerTemplateWithModel######");
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), model, flgConvertiInPdf, bean.getFlgMostraDatiSensibili(), bean.getFlgMostraOmissisBarrati(),  bean.getElencoCampiConGestioneOmissisDaIgnorare(), getSession());
			logger.debug("#######FINE fillFreeMarkerTemplateWithModel######");
	//		fillModelBean.getInfoFile().setCorrectFileName(bean.getDisplayFilenameModello());
			
			String ext = flgConvertiInPdf ? "pdf" : FilenameUtils.getExtension(fillModelBean.getNomeFile());
			String nomeFilePdf = null;
			if(flgMostraDatiSensibili) {
				if(bean.getFlgMostraOmissisBarrati() != null && bean.getFlgMostraOmissisBarrati()) {
					nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_PER_VERIFICA_OMISSIS.%s", ext);
				} else {
					nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_INTEGRALE.%s", ext);
				}
			} else {
				nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_VERS_X_PUBBLICAZIONE.%s", ext);
			}
			fillModelBean.setNomeFile(nomeFilePdf);
			logger.debug("#######FINE generaDispositivoDaModello######");
			return fillModelBean;
		}
		logger.debug("#######FINE generaDispositivoDaModello######");
		return null;
	}
	
	public FileDaFirmareBean generaDatiSpesaDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
	
		String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // APPENDICE DATI DI SPESA
		Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession());
		if(StringUtils.isNotBlank(bean.getDisplayFilenameModello())) {
			fillModelBean.setNomeFile(bean.getDisplayFilenameModello());
		} else {
			fillModelBean.setNomeFile(getPrefixRegNum(bean) + "MOVIMENTI_CONTABILI.pdf");
		}
		
		return fillModelBean;
	}	
	
	public FileDaFirmareBean generaRiepilogoFirmeVistiDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getIdModello()) && StringUtils.isNotBlank(bean.getNomeModello())) {
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // RIEPILOGO FIRME E VISTI
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession());
			if(StringUtils.isNotBlank(bean.getDisplayFilenameModello())) {
				fillModelBean.setNomeFile(bean.getDisplayFilenameModello());
			} else {
				fillModelBean.setNomeFile(getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO.pdf");
			}
			
			return fillModelBean;
			
		} else {				
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			if(StringUtils.isBlank(bean.getNomeModello())) {
				bean.setNomeModello("FOGLIO_FIRME_VISTI");
			}
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(bean.getNomeModello());
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			bean.setUriModello(lExtractvermodelloOutput.getResultBean().getUriverout());
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // RIEPILOGO FIRME E VISTI
			
			File templateWithValues = ModelliUtil.fillTemplateAndConvertToPdf(bean.getIdProcess(), bean.getUriModello(), null, templateValues, getSession());
					
			String nomeFile = getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO.pdf";
			String uriFile = StorageImplementation.getStorage().store(templateWithValues);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFile, false, null);
			
			FileDaFirmareBean fillModelBean = new FileDaFirmareBean();
			fillModelBean.setNomeFile(nomeFile);	
			fillModelBean.setUri(uriFile);
			fillModelBean.setInfoFile(lMimeTypeFirmaBean);
			
			return fillModelBean;
		} 
	}	
	
	public FileDaFirmareBean generaRiepilogoFirmeVisti2DaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getIdModello()) && StringUtils.isNotBlank(bean.getNomeModello())) {
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // RIEPILOGO FIRME E VISTI
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession());
			if(StringUtils.isNotBlank(bean.getDisplayFilenameModello())) {
				fillModelBean.setNomeFile(bean.getDisplayFilenameModello());
			} else {
				fillModelBean.setNomeFile(getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO_2.pdf");
			}
			
			return fillModelBean;
			
		} else {				
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			if(StringUtils.isBlank(bean.getNomeModello())) {
				bean.setNomeModello("FOGLIO_FIRME_VISTI_2");
			}
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(bean.getNomeModello());
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			bean.setUriModello(lExtractvermodelloOutput.getResultBean().getUriverout());
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // RIEPILOGO FIRME E VISTI
			
			File templateWithValues = ModelliUtil.fillTemplateAndConvertToPdf(bean.getIdProcess(), bean.getUriModello(), null, templateValues, getSession());
					
			String nomeFile = getPrefixRegNum(bean) + "FOGLIO_RIEPILOGO_2.pdf";
			String uriFile = StorageImplementation.getStorage().store(templateWithValues);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFile, false, null);
			
			FileDaFirmareBean fillModelBean = new FileDaFirmareBean();
			fillModelBean.setNomeFile(nomeFile);	
			fillModelBean.setUri(uriFile);
			fillModelBean.setInfoFile(lMimeTypeFirmaBean);
			
			return fillModelBean;
		} 
	}
		
	public FileDaFirmareBean generaSchedaTraspDaModello(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		if(StringUtils.isNotBlank(bean.getIdModello()) && StringUtils.isNotBlank(bean.getNomeModello())) {
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // SCHEDA TRASPARENZA
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), bean.getIdModello(), mappaValori, true, false, getSession());
			if(StringUtils.isNotBlank(bean.getDisplayFilenameModello())) {
				fillModelBean.setNomeFile(bean.getDisplayFilenameModello());
			} else {
				fillModelBean.setNomeFile(getPrefixRegNum(bean) + "SCHEDA_TRASPARENZA.pdf");
			}
			
			return fillModelBean;
			
		} else {				
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			
			if(StringUtils.isBlank(bean.getNomeModello())) {
				bean.setNomeModello("SCHEDA_TRASPARENZA");
			}
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(bean.getNomeModello());
			
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			bean.setUriModello(lExtractvermodelloOutput.getResultBean().getUriverout());
			
			String templateValues = getDatiXGenDaModello(bean, bean.getNomeModello(), null); // SCHEDA TRASPARENZA
			
			File templateWithValues = ModelliUtil.fillTemplateAndConvertToPdf(bean.getIdProcess(), bean.getUriModello(), null, templateValues, getSession());
					
			String nomeFile = getPrefixRegNum(bean) + "SCHEDA_TRASPARENZA.pdf";
			String uriFile = StorageImplementation.getStorage().store(templateWithValues);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFile, false, null);
			
			FileDaFirmareBean fillModelBean = new FileDaFirmareBean();
			fillModelBean.setNomeFile(nomeFile);	
			fillModelBean.setUri(uriFile);
			fillModelBean.setInfoFile(lMimeTypeFirmaBean);
			
			return fillModelBean;
		} 
	}
	
	public FileDaFirmareBean generaModuloNotifica(DestinatariNotificaMessiXmlBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if(StringUtils.isBlank(bean.getDescrizione()) || StringUtils.isBlank(bean.getIndirizzo()) || StringUtils.isBlank(bean.getNumeroNotifica())) {
			throw new StoreException("Per generare il modulo di notifica devi prima compilare nominativo, indirizzo e n.ro di notifica");
		}
		
		String idUdAtto = getExtraparams().get("idUdAtto");
		
		String nomeModelloNotificaDestinatarioAtto = "NOTIFICA_DESTINATARIO_ATTO";		
		
		SezioneCache lSezioneCache = new SezioneCache();
		
		Variabile varIdUd = new Variabile();
		varIdUd.setNome("IdUd");
		varIdUd.setValoreSemplice(idUdAtto != null ? idUdAtto : "");
		lSezioneCache.getVariabile().add(varIdUd);
			
		Variabile varNroNotifica = new Variabile();
		varNroNotifica.setNome("NroNotifica");
		varNroNotifica.setValoreSemplice(bean.getNumeroNotifica() != null ? bean.getNumeroNotifica() : "");
		lSezioneCache.getVariabile().add(varNroNotifica);
		
		Variabile varNomeDestinatarioNotifica = new Variabile();
		varNomeDestinatarioNotifica.setNome("NomeDestinatarioNotifica");
		varNomeDestinatarioNotifica.setValoreSemplice(bean.getDescrizione() != null ? bean.getDescrizione() : "");
		lSezioneCache.getVariabile().add(varNomeDestinatarioNotifica);
		
		Variabile varIndirizzoDestinatarioNotifica = new Variabile();
		varIndirizzoDestinatarioNotifica.setNome("IndirizzoDestinatarioNotifica");
		varIndirizzoDestinatarioNotifica.setValoreSemplice(bean.getIndirizzo() != null ? bean.getIndirizzo() : "");
		lSezioneCache.getVariabile().add(varIndirizzoDestinatarioNotifica);
		
		Variabile varAltriDatiDestinatarioNotifica = new Variabile();
		varAltriDatiDestinatarioNotifica.setNome("AltriDatiDestinatarioNotifica");
		varAltriDatiDestinatarioNotifica.setValoreSemplice(bean.getAltriDati() != null ? bean.getAltriDati() : "");
		lSezioneCache.getVariabile().add(varAltriDatiDestinatarioNotifica);
					
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		lMarshaller.marshal(lSezioneCache, lStringWriter);
		String attributiAddModuloNotifica = lStringWriter.toString();
		
		DmpkModelliDocGetdatixgendamodelloBean lDmpkModelliDocGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lDmpkModelliDocGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
		lDmpkModelliDocGetdatixgendamodelloInput.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		lDmpkModelliDocGetdatixgendamodelloInput.setIdobjrifin(idUdAtto);
		lDmpkModelliDocGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lDmpkModelliDocGetdatixgendamodelloInput.setNomemodelloin(nomeModelloNotificaDestinatarioAtto);
		lDmpkModelliDocGetdatixgendamodelloInput.setAttributiaddin(attributiAddModuloNotifica);
				
		DmpkModelliDocGetdatixgendamodello lDmpkModelliDocGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lDmpkModelliDocGetdatixgendamodello.execute(getLocale(), loginBean, lDmpkModelliDocGetdatixgendamodelloInput);
		if (lGetdatixgendamodelloOutput.isInError()) {
			throw new StoreException(lGetdatixgendamodelloOutput);
		} 
		
		String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
		
		String nominativoDestinatario = bean.getDescrizione().replaceAll("[^0-9a-zA-Z]", ""); // Nominativo destinatario tolti spazi e caratteri che non siano numeri e lettere
		String nomeFilePdf = "Notifica_N_" + bean.getNumeroNotifica() + "_a_" + nominativoDestinatario + ".pdf";
		
		String idModelloNotificaDestinatarioAtto = ParametriDBUtil.getParametroDB(getSession(), "ID_MODELLO_NOTIFICA_MESSI_SINGOLO_DEST");
		if(StringUtils.isNotBlank(idModelloNotificaDestinatarioAtto)) {
			
			Map<String, Object> mappaValori = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
			
			FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(null, idModelloNotificaDestinatarioAtto, mappaValori, true, true, false, null, getSession());			
			fillModelBean.setNomeFile(nomeFilePdf);
			
			return fillModelBean;
			
		} else {
			
			// In questo caso non avendo l'idModello non posso usare un modello profilato di tipo ODT_WITH_FREEMARKER
			
			DmpkModelliDocExtractvermodelloBean lExtractvermodelloInput = new DmpkModelliDocExtractvermodelloBean();
			lExtractvermodelloInput.setCodidconnectiontokenin(loginBean.getToken());
			lExtractvermodelloInput.setNomemodelloin(nomeModelloNotificaDestinatarioAtto);
			
			DmpkModelliDocExtractvermodello lExtractvermodello = new DmpkModelliDocExtractvermodello();
			StoreResultBean<DmpkModelliDocExtractvermodelloBean> lExtractvermodelloOutput = lExtractvermodello.execute(getLocale(), loginBean, lExtractvermodelloInput);
			if(lExtractvermodelloOutput.isInError()) {
				throw new StoreException(lExtractvermodelloOutput);
			}
			
			String uriModelloNotificaDestinatarioAtto = lExtractvermodelloOutput.getResultBean().getUriverout();
			
			File file = ModelliUtil.fillTemplateAndConvertToPdf(null, uriModelloNotificaDestinatarioAtto, null, templateValues, getSession());		
			
			String uriFile = StorageImplementation.getStorage().store(file);
			MimeTypeFirmaBean lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFile).toURI().toString(), nomeFilePdf, false, null);
			
			FileDaFirmareBean fillModelBean = new FileDaFirmareBean();
			fillModelBean.setNomeFile(nomeFilePdf);	
			fillModelBean.setUri(uriFile);
			fillModelBean.setInfoFile(lMimeTypeFirmaBean);
			
			return fillModelBean;
		}		
	}	
	
	public void versionaDocumento(IdFileBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		RebuildedFile lRebuildedFile = new RebuildedFile();
		lRebuildedFile.setIdDocumento(new BigDecimal(bean.getIdFile()));
		lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(StorageImplementation.getStorage().store(StorageImplementation.getStorage().extractFile(bean.getUri()))));
		MimeTypeFirmaBean lMimeTypeFirmaBean;
		if(bean.getInfoFile() == null) {
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
		} else {
			lMimeTypeFirmaBean = bean.getInfoFile();
		}
		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
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
			logger.error("VersionaDocumento: " + lVersionaDocumentoOutBean.getDefaultMessage());
			throw new StoreException(lVersionaDocumentoOutBean);
		}	
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnire(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception{
		logger.debug("#######INIZIO getListaFileDaUnire######");
		ArrayList<FileDaFirmareBean> listaFileDaUnire = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2CompletaBean.setFlgMostraDatiSensibili(true); // per generare la vers. integrale
		FileDaFirmareBean lFileDispositivoBean = generaDispositivoDaModello(pNuovaPropostaAtto2CompletaBean, true);
		if(lFileDispositivoBean != null) {
			lFileDispositivoBean.setIsDispositivoNuovaPropostaAtto2Completa(true);
			lFileDispositivoBean.setTipoFileUnione(TipoFileUnione.MODELLO_DISPOSITIVO);
			listaFileDaUnire.add(lFileDispositivoBean);
		}
		String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd();
				
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					if (pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() == null || !pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati()) { // se tutti gli allegati non sono pubblicati separatamente (ho messo qui il controllo e non a monte perchè ci sono anche i pareri da gestire nel ciclo for e quelli non guardano il flag di pubblicazione separata)
						if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() == null || !lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se non è da pubblicare separatamente						
							lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
							aggiungiAllegato(listaFileDaUnire, lAllegatoProtocolloBean);
						}
					}
				} else if(lAllegatoProtocolloBean.getFlgParere() != null && lAllegatoProtocolloBean.getFlgParere()) {
					if(lAllegatoProtocolloBean.getFlgParereDaUnire() != null && lAllegatoProtocolloBean.getFlgParereDaUnire()) {
						lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
						aggiungiAllegato(listaFileDaUnire, lAllegatoProtocolloBean);
					}
				}
			}
		}
		logger.debug("#######FINE getListaFileDaUnire######");
		return listaFileDaUnire;
	}
	
	public ArrayList<FileDaFirmareBean> getListaFileDaUnireOmissis(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception{
		ArrayList<FileDaFirmareBean> listaFileDaUnireOmissis = new ArrayList<FileDaFirmareBean>();
		pNuovaPropostaAtto2CompletaBean.setFlgMostraDatiSensibili(false); // per generare la vers. con omissis
		FileDaFirmareBean lFileDispositivoOmissisBean  = generaDispositivoDaModello(pNuovaPropostaAtto2CompletaBean, true);
		if(lFileDispositivoOmissisBean != null) {
			lFileDispositivoOmissisBean.setIsDispositivoNuovaPropostaAtto2Completa(true);
			lFileDispositivoOmissisBean.setTipoFileUnione(TipoFileUnione.MODELLO_DISPOSITIVO);
			listaFileDaUnireOmissis.add(lFileDispositivoOmissisBean);
		}
		String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd();					
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
					if (lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null || !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // se non è escluso dalla pubblicazione
						if (pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() == null || !pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati()) { // se tutti gli allegati non sono pubblicati separatamente (ho messo qui il controllo e non a monte perchè ci sono anche i pareri da gestire nel ciclo for e quelli non guardano il flag di pubblicazione separata)											
							if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() == null || !lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se non è da pubblicare separatamente
								lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
								if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
									aggiungiAllegatoOmissis(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
								} else {
									aggiungiAllegato(listaFileDaUnireOmissis, lAllegatoProtocolloBean);
								}
							}
						}
					}
				} else if(lAllegatoProtocolloBean.getFlgParere() != null && lAllegatoProtocolloBean.getFlgParere()) {
					if(lAllegatoProtocolloBean.getFlgParereDaUnire() != null && lAllegatoProtocolloBean.getFlgParereDaUnire()) {
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
	
	public AttoRiferimentoBean recuperaIdUdAttoRiferimento(AttoRiferimentoBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getCategoriaReg());
		input.setIddoctypein(StringUtils.isNotBlank(bean.getTipoAttoRif()) ? new BigDecimal(bean.getTipoAttoRif()) : null);
		input.setSiglaregin(bean.getSigla());
		input.setNumregin(StringUtils.isNotBlank(bean.getNumero()) ? Integer.parseInt(bean.getNumero()) : null);
		input.setAnnoregin(StringUtils.isNotBlank(bean.getAnno()) ? Integer.parseInt(bean.getAnno()) : null);
		
		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}		
		bean.setIdUd((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? String.valueOf(output.getResultBean().getIdudio().longValue()) : null);
			
		return bean;
	}
	
	/*
	public NuovaPropostaAtto2CompletaBean recuperaIdUdAttoDeterminaAContrarre(NuovaPropostaAtto2CompletaBean bean) throws Exception {

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
	*/
	
	public AllegatoProtocolloBean getAllegatoVistoContabile(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) {
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			String idTipoDocAllegatoVistoContabile = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_VISTO_CONTAB_ITER_ATTI");
			if (StringUtils.isNotBlank(idTipoDocAllegatoVistoContabile)) {
				for (int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAllegati().size(); i++) {
					AllegatoProtocolloBean lAllegatoProtocolloBean = pNuovaPropostaAtto2CompletaBean.getListaAllegati().get(i);
					if (lAllegatoProtocolloBean.getListaTipiFileAllegato() != null && lAllegatoProtocolloBean.getListaTipiFileAllegato().equalsIgnoreCase(idTipoDocAllegatoVistoContabile)) {
						return lAllegatoProtocolloBean;
					}
				}
			}
		}
		return null;
	}
	
	public NuovaPropostaAtto2CompletaBean pubblica(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		NuovaPropostaAtto2CompletaBean bean = null;
		String sistemaPubblicazione = ParametriDBUtil.getParametroDB(getSession(), "SIST_ALBO");		
		if(StringUtils.isNotBlank(sistemaPubblicazione) && !"AURIGA".equalsIgnoreCase(sistemaPubblicazione)) {
			// Se è una pubblicazione esterna
			
			/**
			 * Il controllo del flgDaPubblicare è stato gestito lato client nelle classi
			 * TaskNuovaPropostaAtto2CompletaDetail
			 * TaskRevocaNuovaPropostaAtto2CompletaDetail
			 */
//			HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(pNuovaPropostaAtto2CompletaBean);
//			boolean flgDaPubblicare = false;
//			if(showAttributoCustomCablato(setAttributiCustomCablati, "FLG_PUBBL_ALBO")) {
//				// Se a maschera è visibile FLG_PUBBL_ALBO e vale SI
//				flgDaPubblicare = pNuovaPropostaAtto2CompletaBean.getFlgPubblAlbo() != null && _FLG_SI.equals(pNuovaPropostaAtto2CompletaBean.getFlgPubblAlbo());							
//			} else {
//				// Se a maschera NON è visibile FLG_PUBBL_ALBO & è visibile e valorizzato uno tra (TIPO_DECORRENZA_PUBBL_ALBO e PUBBL_ALBO_DAL)
//				if(showAttributoCustomCablato(setAttributiCustomCablati, "TIPO_DECORRENZA_PUBBL_ALBO")) {
//					flgDaPubblicare = StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getTipoDecorrenzaPubblAlbo());
//				} else if(showAttributoCustomCablato(setAttributiCustomCablati, "PUBBL_ALBO_DAL")) {
//					flgDaPubblicare = pNuovaPropostaAtto2CompletaBean.getDataPubblAlboDal() != null;
//				}
//			} 
//			if(flgDaPubblicare) {			
				// Se la pubblicazione deve avvenire con chiamata ai WS dell'albo di ReggioCalabria
				if("ALBO_CORC".equalsIgnoreCase(sistemaPubblicazione)) {
					bean = pubblicaAlboReggio(pNuovaPropostaAtto2CompletaBean);
				}
				// Se la pubblicazione deve avvenire con chiamata ai WS dell'albo di Area Vasta Bari
				else if("ALBO_AVB".equalsIgnoreCase(sistemaPubblicazione)) {
					bean = pubblicaAlboAVB(pNuovaPropostaAtto2CompletaBean);
				}
				// Se la pubblicazione deve avvenire con chiamata ai WS dell'albo di Milano
				else if("INTERSAIL".equalsIgnoreCase(sistemaPubblicazione)) {
					bean = pubblicaAlbo(pNuovaPropostaAtto2CompletaBean);
				}
				else {
					throw new StoreException("Il parametro SIST_ALBO non è configurato correttamente");
				}	 
//			}
		}		
		if(bean == null) {
			 bean = get(pNuovaPropostaAtto2CompletaBean);
		}
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean pubblicaAlboReggio(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		boolean primarioOmissis=false;
		
		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
			boolean convPdfPreFirma = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "CONV_PDF_PRE_FIRMA");
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				if(!bean.getInfoFilePrimarioOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {					
					ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(bean.getUriFilePrimarioOmissis(),
							bean.getNomeFilePrimarioOmissis(), bean.getInfoFilePrimarioOmissis()));					
					bean.setUriFilePrimarioOmissis(outputBean.getFiles().get(0).getUri());
					bean.setNomeFilePrimarioOmissis(outputBean.getFiles().get(0).getNomeFile());
					bean.setInfoFilePrimarioOmissis(outputBean.getFiles().get(0).getInfoFile());
				}
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
				primarioOmissis = true;
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato()
					&& infoFileDaPubblicare.getTipoFirma() != null
					&& infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				AlboReggioAtto attoDaPubblicare = new AlboReggioAtto();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					attoDaPubblicare.setNumero(Integer.valueOf(bean.getNumeroRegistrazione()));
					attoDaPubblicare.setAnno(bean.getDataRegistrazione() != null? new Integer(new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataRegistrazione()).substring(6)): null);
					attoDaPubblicare.setData(setDateForAlbo(bean.getDataRegistrazione()));
					attoDaPubblicare.setDataInserimento(setDateForAlbo(bean.getDataInizioPubblicazione()));
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					GregorianCalendar calPubblicazione = new GregorianCalendar();
					calPubblicazione.setTime(bean.getDataInizioPubblicazione());
					attoDaPubblicare.setDataInizio(setDateForAlbo(calPubblicazione.getTime()));
					calPubblicazione.add(Calendar.DAY_OF_YEAR, Integer.parseInt(bean.getGiorniPubblicazione()));
					attoDaPubblicare.setDataFine(setDateForAlbo(calPubblicazione.getTime()));
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				attoDaPubblicare.setProvenienza(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune di Reggio Calabria");
				
//				String tipoDocumento = ParametriDBUtil.getParametroDB(getSession(), "TIPO_DOCUMENTO_ALBO_PRETORIO");
//				attoDaPubblicare.setIdType(tipoDocumento != null && !"".equals(tipoDocumento) ? tipoDocumento : "00000000-0000-0000-0000-000000000001");

				String idUtente = ParametriDBUtil.getParametroDB(getSession(), "ID_UTENTE_PUBBL_ALBO");
				attoDaPubblicare.setIdUtente(idUtente != null && !"".equals(idUtente) ? idUtente : "MuB6DV3JYUmWzBt]@[jZ");

				attoDaPubblicare.setIdType(bean.getIdTipoDocAlbo() != null && !"".equals(bean.getIdTipoDocAlbo()) ? bean.getIdTipoDocAlbo() : "00000000-0000-0000-0000-000000000001");
				
				attoDaPubblicare.setIdUo(bean.getIdUoAlboReggio());
				
				attoDaPubblicare.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml

				attoDaPubblicare.setManualeImport("I");
				
				logger.debug("---- INVOCO I WS DI PUBBLICAZIONE ALBO DI REGGIO CALABRIA ----");
				
				logger.debug("--REQUEST-- servizio nuovoAtto");
				//Trasformo l'oggetto in xml per poterlo scrivere nei log e averne traccia				
				logger.debug(convertBeanToXml(attoDaPubblicare));				

				// Invoco il WS Albo Pretorio di Reggio Calabria per la pubblicazione dell'atto senza file
				AlboPretorioReggioImpl service = new AlboPretorioReggioImpl();
				Result<AlboReggioResult> output = service.nuovoatto(getLocale(), attoDaPubblicare);

				//da Ok solo se viene pubblicato l'atto, e cioè se mi ritorna l'idRecord di pubblicazione (vedere nel progetto AlboReggioClient come inizializzo l ok)
				if (!output.isOk()) {
					String errore;
					if (output.isTimeout()) {
						errore = "La chiamata al WS di pubblicazione dell'atto è andata in timeout";
						logger.error(errore);
					} else if (output.isRispostaNonRicevuta()) {
						errore = "La chiamata al WS di pubblicazione dell'atto non ha resituito nessuna risposta: " +  output.getMessage();
						logger.error(errore);
					} else {
						if(output.getPayload()!=null && StringUtils.isNotBlank(output.getPayload().getMessaggio())) {
							 errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getMessaggio() + ", codice: " + output.getPayload().getCodice();
							 logger.error("--RESPONSE-- servizio nuovoAtto: " + errore);
						}else {
							errore = "La chiamata al WS di pubblicazione dell'atto non ha restiuito nessun esito"+  output.getMessage(); // se il Payload() == null o non c'è messaggio
							logger.error(errore);
						}
					}
					
					//Invio mail con relativo errore di fallita pubblicazione
					sendMailErrorePubblicazione("nuovoAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
				
				} else {
					// Se la pubblicazione degli estremi dell'atto è andata a buon fine aggiungo il  file primario chiamando il WS aggiungi allegato
					if (output.getPayload() != null && StringUtils.isNotBlank(output.getPayload().getIdRecord())) {
						
							logger.debug("--RESPONSE-- servizio nuovoAtto: " + convertBeanToXml(output.getPayload()));
						
							//id di pubblicazione restituito dal servizio dell'albo
							idPubblicazione = output.getPayload().getIdRecord();
							
							aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idPubblicazione);

							AlboReggioAllegato filePrimarioDaPubblicare = new AlboReggioAllegato();
							filePrimarioDaPubblicare.setIdAtto(idPubblicazione);
							filePrimarioDaPubblicare.setPrincipale("S"); // S=Si N=No
							filePrimarioDaPubblicare.setEstensione(ext);

							// trasformo il file in base64
							String base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileDaPubblicare));
							filePrimarioDaPubblicare.setBase64File(base64File);

							if (primarioOmissis) {
								filePrimarioDaPubblicare.setNomeFile(bean.getNomeFilePrimarioOmissis());
								filePrimarioDaPubblicare.setDescrizione(bean.getNomeFilePrimarioOmissis());
								filePrimarioDaPubblicare.setTipo("E"); // I= Integrale E = Estratto

							} else {
								filePrimarioDaPubblicare.setNomeFile(bean.getNomeFilePrimario());
								filePrimarioDaPubblicare.setDescrizione(bean.getNomeFilePrimario());
								filePrimarioDaPubblicare.setTipo("I"); // I= Integrale E = Estratto
							}
							
							logger.debug("--REQUEST-- servizio nuovoAllegato");
							logger.debug(convertBeanToXml(filePrimarioDaPubblicare));

							Result<AlboReggioResult> output2 = service.nuovoallegato(getLocale(), filePrimarioDaPubblicare);

							//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboReggioClient come inizializzo l' OK)
							if (!output2.isOk()) {
								String errore;
								if (output2.isTimeout()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
									logger.error(errore);
								} else if (output2.isRispostaNonRicevuta()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " +  output2.getMessage();
									logger.error(errore);
								} else {
									if(output2.getPayload()!=null && StringUtils.isNotBlank(output2.getPayload().getMessaggio())) {
										 errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output2.getPayload().getMessaggio() + ", codice: " + output2.getPayload().getCodice();
										 logger.error("--RESPONSE-- servizio nuovoAllegato: " + errore);
									}else {
										errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" +  output2.getMessage(); // se il Payload() == null o non c'è messaggio
										logger.error(errore);
									}
								}
								
								//Invio mail con relativo errore di fallita pubblicazione
								sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
							} else {
								
								logger.debug("--RESPONSE-- servizio nuovoAllegato: Aggiunto file primario: " + filePrimarioDaPubblicare.getDescrizione() + " all'atto pubblicato con id: " + idPubblicazione);
								
								List<AllegatoProtocolloBean> listaAllegatiDaPubblicare = new ArrayList<>();

								// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
								// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
								if(bean.getAllegatoVistoContabile() == null) {
									bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
								}
								if (bean.getAllegatoVistoContabile() != null) {
									listaAllegatiDaPubblicare.add(bean.getAllegatoVistoContabile());
								}

								// Controllo se ci sono anche allegati dell'atto da pubblicare separatamente
								if (bean.getListaAllegati() != null) {
									for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()) {
										if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
												&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
											if(lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null
													|| !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // // se non è escluso dalla pubblicazione
												if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
														&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
													listaAllegatiDaPubblicare.add(lAllegatoProtocolloBean);
												}
											}
										}
									}
								}

								if(listaAllegatiDaPubblicare!=null && listaAllegatiDaPubblicare.size()>=1) {
									logger.debug("Pubblico gli allegati");
								}
								
								for (AllegatoProtocolloBean allegato : listaAllegatiDaPubblicare) {
									File fileAllegatoDaPubblicare = null;
									boolean allegatoOmissis = false;

									if (allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili() && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
										if(!allegato.getInfoFileOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {		
											ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(allegato.getUriFileOmissis(),
													allegato.getNomeFileOmissis(), allegato.getInfoFileOmissis()));					
											allegato.setUriFileOmissis(outputBean.getFiles().get(0).getUri());
											allegato.setNomeFileOmissis(outputBean.getFiles().get(0).getNomeFile());
											allegato.setInfoFileOmissis(outputBean.getFiles().get(0).getInfoFile());
										}
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
										allegatoOmissis = true;
									}
									else if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
									}else {
										String errore = "La chiamata al WS di aggiunta allegato con nome: " + allegato.getNomeFileAllegato() != null ? allegato.getNomeFileAllegato() : allegato.getNomeFileOmissis() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: uri allegato non presente";
										logger.error(errore);
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
									}

									AlboReggioAllegato allegatoDaPubblicare = new AlboReggioAllegato();
									allegatoDaPubblicare.setIdAtto(idPubblicazione);
									allegatoDaPubblicare.setPrincipale("N"); // S=Si N=No

									// trasformo il file in base64
									String base64FileAllegato = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileAllegatoDaPubblicare));
									allegatoDaPubblicare.setBase64File(base64FileAllegato);
									
									if (allegatoOmissis) {
										allegatoDaPubblicare.setNomeFile(allegato.getNomeFileOmissis());
										allegatoDaPubblicare.setDescrizione(allegato.getNomeFileOmissis());  // è il nome del file che compare sull albo
										allegatoDaPubblicare.setTipo("E"); // I= Integrale E = Estretto
										allegatoDaPubblicare.setEstensione(FilenameUtils.getExtension(allegato.getNomeFileOmissis()));

									} else {
										allegatoDaPubblicare.setNomeFile(allegato.getNomeFileAllegato());
										allegatoDaPubblicare.setDescrizione(allegato.getNomeFileAllegato()); // è il nome del file che compare sull albo
										allegatoDaPubblicare.setTipo("I"); // I= Integrale E = Estretto
										allegatoDaPubblicare.setEstensione(FilenameUtils.getExtension(allegato.getNomeFileAllegato()));
									}

									logger.debug("--REQUEST-- servizio nuovoAllegato");
									logger.debug(convertBeanToXml(allegatoDaPubblicare));
									
									Result<AlboReggioResult> output3 = service.nuovoallegato(getLocale(), allegatoDaPubblicare);

									//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboReggioClient come inizializzo l' OK)
									if (!output3.isOk()) {
										String errore;
										if (output3.isTimeout()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
											logger.error(errore);
										} else if (output3.isRispostaNonRicevuta()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " + output3.getMessage();
											logger.error(errore);
										} else {
											if(output3.getPayload()!=null && StringUtils.isNotBlank(output3.getPayload().getMessaggio())) {
												 errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output3.getPayload().getMessaggio() + ", codice: " + output3.getPayload().getCodice();
												 logger.error("--RESPONSE-- servizio nuovoAllegato: " + errore);
											}else {
												errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getNomeFile() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" + output3.getMessage(); // se il Payload() == null o non c'è messaggio
												logger.error(errore);
											}
										}
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("nuovoAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
									
									}
									logger.debug("--RESPONSE-- servizio nuovoAllegato: Aggiunto file primario: " + allegatoDaPubblicare.getDescrizione() + " all'atto pubblicato con id: " + idPubblicazione);
								}
								
								logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
								
								addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);
								
							}
					}else {
						String errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getMessaggio() + ", codice: " + output.getPayload().getCodice();
						logger.error("--RESPONSE-- servizio nuovoAtto: " + errore);
						
						//Invio mail con relativo errore di fallita pubblicazione
						sendMailErrorePubblicazione("nuovoAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getAnno()));
					}
				}
			} else {
				throw new StoreException("Nessun file da pubblicare");
			}
		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean pubblicaAlboAVB(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		boolean primarioOmissis=false;
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
			boolean convPdfPreFirma = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "CONV_PDF_PRE_FIRMA");
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibili = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibili && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
				logger.debug("Pubblico il file primario (vers. integrale): " + bean.getUriFilePrimario());
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
				infoFileDaPubblicare = bean.getInfoFilePrimario();
			} else if (StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
				logger.debug("Pubblico il file primario (vers. con omissis): " + bean.getUriFilePrimarioOmissis());
				if(!bean.getInfoFilePrimarioOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {					
					ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(bean.getUriFilePrimarioOmissis(),
							bean.getNomeFilePrimarioOmissis(), bean.getInfoFilePrimarioOmissis()));					
					bean.setUriFilePrimarioOmissis(outputBean.getFiles().get(0).getUri());
					bean.setNomeFilePrimarioOmissis(outputBean.getFiles().get(0).getNomeFile());
					bean.setInfoFilePrimarioOmissis(outputBean.getFiles().get(0).getInfoFile());
				}
				fileDaPubblicare = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimarioOmissis());
				infoFileDaPubblicare = bean.getInfoFilePrimarioOmissis();
				primarioOmissis = true;
			}
			String ext = "pdf";
			if (infoFileDaPubblicare != null && infoFileDaPubblicare.isFirmato()
					&& infoFileDaPubblicare.getTipoFirma() != null
					&& infoFileDaPubblicare.getTipoFirma().startsWith("CAdES")) {
				ext = "pdf.p7m";
			}
			if (fileDaPubblicare != null) {
				// Setto i parametri della richiesta
				AlboAVBPubblicaAttoIn attoDaPubblicare = new AlboAVBPubblicaAttoIn();

				if (StringUtils.isNotBlank(bean.getSiglaRegistrazione()) && StringUtils.isNotBlank(bean.getNumeroRegistrazione()) && bean.getDataRegistrazione() != null) {
					attoDaPubblicare.setNumeroRegistroGenerale(String.valueOf(bean.getNumeroRegistrazione()));
					attoDaPubblicare.setDataAdozione(setDateForAlbo(bean.getDataRegistrazione()));
					attoDaPubblicare.setDataPubblicazione(setDateForAlbo(bean.getDataRegistrazione()));
				} else {
					throw new StoreException("Mancano gli estremi di registrazione del documento");
				}

				if (bean.getDataInizioPubblicazione() != null && StringUtils.isNotBlank(bean.getGiorniPubblicazione())) {
					attoDaPubblicare.setDurataPubblicazioneAtto(Integer.parseInt(bean.getGiorniPubblicazione()));
					
				} else {
					throw new StoreException("Mancano i dati relativi al periodo di pubblicazione");
				}

				String enteProvenienza = ParametriDBUtil.getParametroDB(getSession(), "ENTE_PROVENIENZA_ALBO_PRETORIO");
				attoDaPubblicare.setEnteRichiestaPubblicazione(enteProvenienza != null && !"".equals(enteProvenienza) ? enteProvenienza : "Comune aderente al progetto Area Vasta Metropoli Terra di Bari");
				
				DaoTRelAlboAvbVsAuriga dao = new DaoTRelAlboAvbVsAuriga();
				
				BigDecimal getidattoavb = dao.getidattoavb(getLocale(), loginBean, new BigDecimal(bean.getTipoDocumento()));
				
				Long idTipoAtto = getidattoavb.longValue();
				attoDaPubblicare.setIdTipoAtto(idTipoAtto != null ? idTipoAtto : new Long(1));

				String idUtente = ParametriDBUtil.getParametroDB(getSession(), "ID_UTENTE_PUBBL_ALBO");
				attoDaPubblicare.setUsernameResponsabilePubblicazione(idUtente != null && !"".equals(idUtente) ? idUtente : "");

				attoDaPubblicare.setNomeArea(bean.getUfficioProponente());
				
				attoDaPubblicare.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml

//				attoDaPubblicare.setManualeImport("I");
				
				logger.debug("---- INVOCO I WS DI PUBBLICAZIONE ALBO DI AREA VASTA BARI ----");
				
				logger.debug("--REQUEST-- servizio pubblicaatto");
				//Trasformo l'oggetto in xml per poterlo scrivere nei log e averne traccia				
				logger.debug(convertBeanToXml(attoDaPubblicare));				

				// Invoco il WS Albo Pretorio di Area Vasta per la pubblicazione dell'atto senza file
				AlboPretorioAVBImpl service = new AlboPretorioAVBImpl();
				Result<AlboAVBPubblicaAttoResponseReturn> output = service.pubblicaatto(getLocale(), attoDaPubblicare);

				//da Ok solo se viene pubblicato l'atto, e cioè se mi ritorna l'idRecord di pubblicazione (vedere nel progetto AlbAVBClient come inizializzo l ok)
				if (!output.isOk()) {
					String errore;
					if (output.isTimeout()) {
						errore = "La chiamata al WS di pubblicazione dell'atto è andata in timeout";
						logger.error(errore);
					} else if (output.isRispostaNonRicevuta()) {
						errore = "La chiamata al WS di pubblicazione dell'atto non ha resituito nessuna risposta: " +  output.getMessage();
						logger.error(errore);
					} else {
						if(output.getPayload()!=null && StringUtils.isNotBlank(output.getPayload().getAlboAvbAttoError().getDescription())) {
							 errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output.getPayload().getAlboAvbAttoError().getCode();
							 logger.error("--RESPONSE-- servizio pubblicaAtto: " + errore);
						}else {
							errore = "La chiamata al WS di pubblicazione dell'atto non ha restiuito nessun esito"+  output.getMessage(); // se il Payload() == null o non c'è messaggio
							logger.error(errore);
						}
					}
					
					//Invio mail con relativo errore di fallita pubblicazione
					sendMailErrorePubblicazione("pubblicaAtto", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
				
				} else {
					// Se la pubblicazione degli estremi dell'atto è andata a buon fine aggiungo il  file primario chiamando il WS salva allegato
					if (output.getPayload() != null && StringUtils.isNotBlank(String.valueOf(output.getPayload().getAlboAvbAtto().getIdAtto()))) {
						
							logger.debug("--RESPONSE-- servizio pubblicaAtto: " + convertBeanToXml(output.getPayload()));
						
							//id di pubblicazione restituito dal servizio dell'albo
							idPubblicazione = String.valueOf(output.getPayload().getAlboAvbAtto().getIdAtto());
							
							aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idPubblicazione);

							AlboAVBSalvaAllegatoIn filePrimarioDaPubblicare = new AlboAVBSalvaAllegatoIn();
							filePrimarioDaPubblicare.setIdAtto(Long.valueOf(idPubblicazione));
							filePrimarioDaPubblicare.setMainDocument(true);
							filePrimarioDaPubblicare.setMimeType(Files.probeContentType(fileDaPubblicare.toPath()));
							filePrimarioDaPubblicare.setFileContent(FileUtils.readFileToByteArray(fileDaPubblicare));
							
							if (primarioOmissis) {
								filePrimarioDaPubblicare.setFileName(bean.getNomeFilePrimarioOmissis());
								// Come discriminare omissis?
//								filePrimarioDaPubblicare.setTipo("E"); // I= Integrale E = Estratto

							} else {
								filePrimarioDaPubblicare.setFileName(bean.getNomeFilePrimarioOmissis());
								// Come discriminare omissis?
//								filePrimarioDaPubblicare.setTipo("I"); // I= Integrale E = Estratto
							}
							
							logger.debug("--REQUEST-- servizio salvaAllegato");
							logger.debug(convertBeanToXml(filePrimarioDaPubblicare));

							Result<AlboAVBSalvaAllegatoResponseReturn> output2 = service.salvaallegato(getLocale(), filePrimarioDaPubblicare);

							//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboAVBClient come inizializzo l' OK)
							if (!output2.isOk()) {
								String errore;
								if (output2.isTimeout()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
									logger.error(errore);
								} else if (output2.isRispostaNonRicevuta()) {
									errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " +  output2.getMessage();
									logger.error(errore);
								} else {
									if(output2.getPayload()!=null && StringUtils.isNotBlank(output2.getPayload().getAlboAvbAttoError().getDescription())) {
										 errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output2.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output2.getPayload().getAlboAvbAttoError().getDescription();
										 logger.error("--RESPONSE-- servizio salvaAllegato: " + errore);
									}else {
										errore = "La chiamata al WS di aggiunta file primario con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" +  output2.getMessage(); // se il Payload() == null o non c'è messaggio
										logger.error(errore);
									}
								}
								
								//Invio mail con relativo errore di fallita pubblicazione
								sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
							} else {
								
								logger.debug("--RESPONSE-- servizio salvaAllegato: Aggiunto file primario: " + filePrimarioDaPubblicare.getFileName() + " all'atto pubblicato con id: " + idPubblicazione);
								
								List<AllegatoProtocolloBean> listaAllegatiDaPubblicare = new ArrayList<>();

								// Nel caso di determina con spesa, devo recuperare le informazioni dell'allegato del visto contabile  generato in quel task
								// se non è stato generato in quel task, ma in uno precedente, me lo recupero dalla lista allegati 
								if(bean.getAllegatoVistoContabile() == null) {
									bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
								}
								if (bean.getAllegatoVistoContabile() != null) {
									listaAllegatiDaPubblicare.add(bean.getAllegatoVistoContabile());
								}

								// Controllo se ci sono anche allegati dell'atto da pubblicare separatamente
								if (bean.getListaAllegati() != null) {
									for (AllegatoProtocolloBean lAllegatoProtocolloBean : bean.getListaAllegati()) {
										if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null
												&& lAllegatoProtocolloBean.getFlgParteDispositivo()) { // se è parte integrante
											if(lAllegatoProtocolloBean.getFlgNoPubblAllegato() == null
													|| !lAllegatoProtocolloBean.getFlgNoPubblAllegato()) { // // se non è escluso dalla pubblicazione
												if (lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null
														&& lAllegatoProtocolloBean.getFlgPubblicaSeparato()) { // se è da pubblicare separatamente
													listaAllegatiDaPubblicare.add(lAllegatoProtocolloBean);
												}
											}
										}
									}
								}

								if(listaAllegatiDaPubblicare!=null && listaAllegatiDaPubblicare.size()>=1) {
									logger.debug("Pubblico gli allegati");
								}
								
								for (AllegatoProtocolloBean allegato : listaAllegatiDaPubblicare) {
									File fileAllegatoDaPubblicare = null;
									boolean allegatoOmissis = false;

									if (allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili() && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
										if(!allegato.getInfoFileOmissis().isFirmato() && skipOmissisInFirmaAdozioneAtto && convPdfPreFirma) {		
											ConversionePdfBean outputBean = getConversionePdfDataSource().call(getConversionePdfBean(allegato.getUriFileOmissis(),
													allegato.getNomeFileOmissis(), allegato.getInfoFileOmissis()));					
											allegato.setUriFileOmissis(outputBean.getFiles().get(0).getUri());
											allegato.setNomeFileOmissis(outputBean.getFiles().get(0).getNomeFile());
											allegato.setInfoFileOmissis(outputBean.getFiles().get(0).getInfoFile());
										}
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
										allegatoOmissis = true;
									}
									else if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
										fileAllegatoDaPubblicare = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
									}else {
										String errore = "La chiamata al WS di aggiunta allegato con nome: " + allegato.getNomeFileAllegato() != null ? allegato.getNomeFileAllegato() : allegato.getNomeFileOmissis() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: uri allegato non presente";
										logger.error(errore);
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
									}

									AlboAVBSalvaAllegatoIn allegatoDaPubblicare = new AlboAVBSalvaAllegatoIn();
									allegatoDaPubblicare.setIdAtto(Long.valueOf(idPubblicazione));
									allegatoDaPubblicare.setMainDocument(false);
									allegatoDaPubblicare.setFileContent(FileUtils.readFileToByteArray(fileAllegatoDaPubblicare));
									
									if (allegatoOmissis) {
										allegatoDaPubblicare.setFileName(allegato.getNomeFileOmissis());
										allegatoDaPubblicare.setMimeType(Files.probeContentType(fileAllegatoDaPubblicare.toPath()));
										// Come discriminare gli omissis?
//										allegatoDaPubblicare.setTipo("E"); // I= Integrale E = Estretto

									} else {
										allegatoDaPubblicare.setFileName(allegato.getNomeFileAllegato());
										allegatoDaPubblicare.setMimeType(Files.probeContentType(fileAllegatoDaPubblicare.toPath()));
										// Come discriminare gli omissis?
//										allegatoDaPubblicare.setTipo("I"); // I= Integrale E = Estretto
									}

									logger.debug("--REQUEST-- servizio salvaAllegato");
									logger.debug(convertBeanToXml(allegatoDaPubblicare));
									
									Result<AlboAVBSalvaAllegatoResponseReturn> output3 = service.salvaallegato(getLocale(), allegatoDaPubblicare);

									//da Ok solo se è stato aggiunto il file all'atto pubblicato (vedere nel progetto AlboAVBClient come inizializzo l' OK)
									if (!output3.isOk()) {
										String errore;
										if (output3.isTimeout()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + allegatoDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", è andata in timeout";
											logger.error(errore);
										} else if (output3.isRispostaNonRicevuta()) {
											errore = "La chiamata al WS di aggiunta file allegato con nome: " + allegatoDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha resituito nessuna risposta: " + output3.getMessage();
											logger.error(errore);
										} else {
											if(output3.getPayload()!=null && StringUtils.isNotBlank(output3.getPayload().getAlboAvbAttoError().getDescription())) {
												 errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", ha resituito il seguente errore: " + output3.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output3.getPayload().getAlboAvbAttoError().getCode();
												 logger.error("--RESPONSE-- servizio salvaAllegato: " + errore);
											}else {
												errore = "La chiamata al WS di aggiunta file allegato con nome: " + filePrimarioDaPubblicare.getFileName() + ", all'atto in pubblicazione con id: " + idPubblicazione + ", non ha restiuito nessun esito" + output3.getMessage(); // se il Payload() == null o non c'è messaggio
												logger.error(errore);
											}
										}
										
										//Invio mail con relativo errore di fallita pubblicazione
										sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
									
									}
									logger.debug("--RESPONSE-- servizio salvaAllegato: Aggiunto file primario: " + allegatoDaPubblicare.getFileName() + " all'atto pubblicato con id: " + idPubblicazione);
								}
								
								logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
								
								addMessage("Pubblicazione all'Albo Pretorio avvenuta con successo", "", it.eng.utility.ui.module.core.shared.message.MessageType.INFO);
								
							}
					}else {
						String errore = "La chiamata al WS di pubblicazione dell'atto ha resituito il seguente errore: " + output.getPayload().getAlboAvbAttoError().getDescription() + ", codice: " + output.getPayload().getAlboAvbAttoError().getCode();
						logger.error("--RESPONSE-- servizio salvaAtto: " + errore);
						
						//Invio mail con relativo errore di fallita pubblicazione
						sendMailErrorePubblicazione("salvaAllegato", errore, bean.getSiglaRegistrazione(), bean.getNumeroRegistrazione(), String.valueOf(attoDaPubblicare.getDataPubblicazione()));
					}
				}
			} else {
				throw new StoreException("Nessun file da pubblicare");
			}
		} catch (Throwable e) {
			logger.error("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage(), e);
			logger.debug("---- FINE INVOCAZIONE WS DI PUBBLICAZIONE ----");
			throw new StoreException("Si è verificato un errore durante la Pubblicazione all'Albo Pretorio: " + e.getMessage());
		}

		return bean;
	}
	
	private ConversionePdfBean getConversionePdfBean(String uriFile, String nomeFile, MimeTypeFirmaBean infoFile) {
		List<FileDaConvertireBean> files = new ArrayList<FileDaConvertireBean>();
		FileDaConvertireBean lFileDaConvertireBean = new FileDaConvertireBean();
		lFileDaConvertireBean.setUri(uriFile);
		lFileDaConvertireBean.setNomeFile(nomeFile);
		lFileDaConvertireBean.setInfoFile(infoFile);
		files.add(lFileDaConvertireBean);
		ConversionePdfBean inputBean = new ConversionePdfBean();
		inputBean.setFiles(files);
		
		return inputBean;
	}
	
	private String convertBeanToXml(Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(object, sw);
			// Verify XML Content
			String xmlContent = sw.toString();

			return xmlContent;
		} catch (Exception e) {
			logger.error("Errore nella conversione dell' Bean di input in Xml: " + e.getMessage(),e);
			throw new Exception("Errore nella conversione dell' Bean di input");
		}
	}


	private void sendMailErrorePubblicazione(String ws, String errore, String siglaAtto, String numeroAtto, String annoAtto) throws Exception {
		String mittenteMailNotifica = ParametriDBUtil.getParametroDB(getSession(), "EMAIL_MITT_NOTIF_ASS_INVIO_CC");
		String destinatariMailNotifica = ParametriDBUtil.getParametroDB(getSession(), "DEST_NOTIFICHE_ERRORE_PUBBL");
		
		String dettagliAtto = "<" + siglaAtto + "> <" + numeroAtto + ">/<" + annoAtto + ">";
		
		String oggetto = "AURIGA - Notifica errore pubblicazione atto " + dettagliAtto;
		
		String bodyMail = "";
		
		Date today = new Date();
		String data = setDateForAlbo(today);
		String ore = String.valueOf(today.getHours()) + ":" + String.valueOf(today.getMinutes());
		
		if("nuovoAtto".equalsIgnoreCase(ws)) {
			bodyMail="In data " + data + " ore " + ore + " l'invio in pubblicazione dell'atto " + dettagliAtto + " è andato in errore con il seguente messaggio di errore:" + errore;
		}else if("nuovoAllegato".equalsIgnoreCase(ws)) {
			bodyMail="In data " + data + " ore " + ore + " l'invio in pubblicazione dell'atto " + dettagliAtto + " è andato parzialmente in errore con il seguente messaggio di errore:" + errore;
		}		
		
		SenderBean senderBean = new SenderBean();
		senderBean.setFlgInvioSeparato(false);
		senderBean.setIsPec(false);
		senderBean.setAccount(mittenteMailNotifica);
		senderBean.setAddressFrom(mittenteMailNotifica);

		// Destinatari principali
		if(destinatariMailNotifica != null) {
			String[] destinatariTo = IndirizziEmailSplitter.split(destinatariMailNotifica);
			senderBean.setAddressTo(Arrays.asList(destinatariTo));
		} else {
			logger.error("Errore durante invio mail di notifica di mancata pubblicazione: destinatari non presenti");
			throw new Exception("Errore in pubblicazione: " + errore +" non è stato possbibile inviare la mail di notifica perchè destinatari non presenti");
		}		

		// Oggetto
		senderBean.setSubject(oggetto);

		// CORPO
		senderBean.setBody(bodyMail);
		senderBean.setIsHtml(false);

		// Conferma di lettura
		senderBean.setReturnReceipt(false);

		try {
			AurigaMailService.getMailSenderService().send(new Locale("it"), senderBean);
		} catch (Exception e) {
			logger.error("Errore durante invio mail di notifica di mancata pubblicazione: " + e.getMessage(), e);
			throw new Exception(errore + ", non è stato possibile inviare una mail di notifica: " + e.getMessage());
		}
		
		throw new Exception(errore);
	}

	private String setDateForAlbo(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = sdf.format(date);
		
		return dateString;
	}

	// SENZA SPESA: la pubblicazione nelle determine senza spesa avviene dopo la firma adozione se non è una proposta di concerto, altrimenti dopo la firma dei dirigenti di concerto, e devo pubblicare solo il file unione (dispositivo e allegati parte integrante) 
	// CON SPESA: la pubblicazione nelle determine con spesa avviene dopo la firma del visto contabile e devo pubblicare il file unione (dispositivo e allegati parte integrante) e come allegato il file del visto generato in quel task 
	public NuovaPropostaAtto2CompletaBean pubblicaAlbo(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		// Mi recupero i dati di registrazione dopo che ho salvato
		NuovaPropostaAtto2CompletaBean bean = get(pNuovaPropostaAtto2CompletaBean);
		
		try {
			File fileDaPubblicare = null;
			MimeTypeFirmaBean infoFileDaPubblicare = null;
			boolean flgDatiSensibiliPrimario = bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili();
			if (!flgDatiSensibiliPrimario && StringUtils.isNotBlank(bean.getUriFilePrimario())) {
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
					bean.setAllegatoVistoContabile(getAllegatoVistoContabile(pNuovaPropostaAtto2CompletaBean));
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
				
				
				if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
					// prendo il tipo del visto reg. contabile, se c'è
					String idTipoDocAllegatoVistoContabile = bean.getAllegatoVistoContabile() != null && bean.getAllegatoVistoContabile().getListaTipiFileAllegato() != null ? bean.getAllegatoVistoContabile().getListaTipiFileAllegato() : null;
					for (int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAllegati().size(); i++) {
						AllegatoProtocolloBean lAllegatoProtocolloBean = pNuovaPropostaAtto2CompletaBean.getListaAllegati().get(i);
						// devo escludere dagli allegati il visto reg. contabile, se c'è, perchè è già nella lista da mandare in pubblicazione (vedi sopra)
						if (StringUtils.isBlank(idTipoDocAllegatoVistoContabile) || lAllegatoProtocolloBean.getListaTipiFileAllegato() == null || !lAllegatoProtocolloBean.getListaTipiFileAllegato().equalsIgnoreCase(idTipoDocAllegatoVistoContabile)) {
							// se è un allegato parte integrante & separato & non escluso dalla pubblicazione allora lo devo aggiungere nella lista da mandare in pubblicazione
							boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
							boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();
							boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
							boolean flgPubblicaAllegatiSeparati = pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati();
							boolean flgDatiSensibili = lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili();
							if (flgParteDispositivo && !flgNoPubblAllegato && (flgPubblicaAllegatiSeparati || flgPubblicaSeparato)) {
								// metto la versione omissis se c'è, altrimenti la versione integrale
								if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis()) && flgDatiSensibili) {
									File fileAllegatoOmissis = StorageImplementation.getStorage().extractFile(lAllegatoProtocolloBean.getUriFileOmissis());																																
									AlboPretorioAttachBean fileAllegatoOmissisAttachBean = new AlboPretorioAttachBean();
									fileAllegatoOmissisAttachBean.setTipoFile("A");
									fileAllegatoOmissisAttachBean.setFileName(lAllegatoProtocolloBean.getNomeFileOmissis());
									fileAllegatoOmissisAttachBean.setUri(fileAllegatoOmissis.getPath());							
									listaFileAllegati.add(fileAllegatoOmissisAttachBean);
									listaFile.add(fileAllegatoOmissisAttachBean);
								} else if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
									File fileAllegato = StorageImplementation.getStorage().extractFile(lAllegatoProtocolloBean.getUriFileAllegato());																																
									AlboPretorioAttachBean fileAllegatoAttachBean = new AlboPretorioAttachBean();
									fileAllegatoAttachBean.setTipoFile("A");
									fileAllegatoAttachBean.setFileName(lAllegatoProtocolloBean.getNomeFileAllegato());
									fileAllegatoAttachBean.setUri(fileAllegato.getPath());							
									listaFileAllegati.add(fileAllegatoAttachBean);
									listaFile.add(fileAllegatoAttachBean);
								}
							}								
						}
					}
				}
				
				documentoType.setAllegati(listaFileAllegati);

				pubblicaSuAlboPretorio(listaFile, documentoType, pNuovaPropostaAtto2CompletaBean);

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
						NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {

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
					aggiornaIdAlbo(pNuovaPropostaAtto2CompletaBean.getIdUd(), idAlbo);
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
	
	public NuovaPropostaAtto2CompletaBean rollbackNumerazioneDefAtti(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCore2RollbacknumerazioneudBean input = new DmpkCore2RollbacknumerazioneudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIdudin(new BigDecimal(bean.getIdUd()));
		
		DmpkCore2Rollbacknumerazioneud store = new DmpkCore2Rollbacknumerazioneud();
		StoreResultBean<DmpkCore2RollbacknumerazioneudBean> output = store.execute(getLocale(), loginBean,input);

		if (output.isInError()) {
			logger.error("Errore rollback numerazione definitiva atto con idUd " + bean.getIdUd() + ": " + output.getDefaultMessage());
//			throw new StoreException(output);
		} else {			
			bean.setEsitoRollbackNumDefAtti("OK");
		}
		
		return bean;
	}	
	
	public OpzUOInDettAttoBean recuperaOpzUOInDettAtto(OpzUOInDettAttoBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkProcessesGetopzuoindettattoBean input = new DmpkProcessesGetopzuoindettattoBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setIduoin(new BigDecimal(bean.getIdUo()));
		
		DmpkProcessesGetopzuoindettatto store = new DmpkProcessesGetopzuoindettatto();
		StoreResultBean<DmpkProcessesGetopzuoindettattoBean> output = store.execute(getLocale(), loginBean,input);
		
		if (output.isInError()) {			
			throw new StoreException(output);
		}
		
		if(StringUtils.isNotBlank(output.getResultBean().getXmlopzout())) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			OpzUOInDettAttoXmlBean lOpzUOInDettAttoXmlBean = lXmlUtilityDeserializer.unbindXml(output.getResultBean().getXmlopzout(), OpzUOInDettAttoXmlBean.class);
			
			bean.setFlgVistoRespUffVisibilita(lOpzUOInDettAttoXmlBean.getFlgVistoRespUffVisibilita() == Flag.SETTED);
			bean.setFlgVistoRespUffEditabile(lOpzUOInDettAttoXmlBean.getFlgVistoRespUffEditabile() == Flag.SETTED);
			bean.setFlgVistoRespUffValoreDefault(lOpzUOInDettAttoXmlBean.getFlgVistoRespUffValoreDefault() == Flag.SETTED);
			bean.setFlgVistoRespUffValoriSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoRespUffValoriSelectScrivanie());
			bean.setFlgVistoRespUffValoreDefaultSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoRespUffValoreDefaultSelectScrivanie());
			
			bean.setFlgVistoDirSup1Visibilita(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup1Visibilita() == Flag.SETTED);
			bean.setFlgVistoDirSup1Editabile(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup1Editabile() == Flag.SETTED);
			bean.setFlgVistoDirSup1ValoreDefault(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup1ValoreDefault() == Flag.SETTED);
			bean.setFlgVistoDirSup1ValoriSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup1ValoriSelectScrivanie());
			bean.setFlgVistoDirSup1ValoreDefaultSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup1ValoreDefaultSelectScrivanie());
			
			bean.setFlgVistoDirSup2Visibilita(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup2Visibilita() == Flag.SETTED);
			bean.setFlgVistoDirSup2Editabile(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup2Editabile() == Flag.SETTED);
			bean.setFlgVistoDirSup2ValoreDefault(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup2ValoreDefault() == Flag.SETTED);
			bean.setFlgVistoDirSup2ValoriSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup2ValoriSelectScrivanie());
			bean.setFlgVistoDirSup2ValoreDefaultSelectScrivanie(lOpzUOInDettAttoXmlBean.getFlgVistoDirSup2ValoreDefaultSelectScrivanie());			
		}

		return bean;
	}
	
	public UnioneFileAttoBean unioneFile(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		
		boolean flgAttivaSceltaPosizioneAllegatiUniti = StringUtils.isNotBlank(getExtraparams().get("flgAttivaSceltaPosizioneAllegatiUniti")) && "true".equalsIgnoreCase(getExtraparams().get("flgAttivaSceltaPosizioneAllegatiUniti")) ? true : false;	
		if(pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile()!=null) pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile().setPosizioneAllegatiUnitiAttiva(flgAttivaSceltaPosizioneAllegatiUniti);
		
		
		boolean flgPostDiscussione = getExtraparams().get("flgPostDiscussione") != null ? new Boolean(getExtraparams().get("flgPostDiscussione")) : false;	
		// se ATTIVA_UNIONE_PARERI_TESTO_COORD_ATTI_COLLEG == true uniamo anche i pareri al file unione
		if(flgPostDiscussione && ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_UNIONE_PARERI_TESTO_COORD_ATTI_COLLEG")) {
			if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
				for (int i = 0; i < pNuovaPropostaAtto2CompletaBean.getListaAllegati().size(); i++){
					pNuovaPropostaAtto2CompletaBean.getListaAllegati().get(i).setFlgParereDaUnire(true);
				}
			}
		}

		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;		
		UnioneFileAttoBean lUnioneFileAttoBean = new UnioneFileAttoBean();
		try {			
			if(getExtraparams().get("isVersIntegrale") != null && "true".equalsIgnoreCase(getExtraparams().get("isVersIntegrale"))) {
				FileDaFirmareBean lFileUnioneBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true, fileDaTimbrare);
				lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneBean.getNomeFile());
				lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneBean.getUri());
				lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneBean.getInfoFile());				
				lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneBean.getUriFileOdtGenerato());
			} else if(getExtraparams().get("isVersXPubbl") != null && "true".equalsIgnoreCase(getExtraparams().get("isVersXPubbl"))) {
				FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, false, fileDaTimbrare);
				lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
				lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
				lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
				lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneVersConOmissisBean.getUriFileOdtGenerato());
			} else {
				if(pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili() != null && pNuovaPropostaAtto2CompletaBean.getFlgDatiSensibili()) {
					FileDaFirmareBean lFileUnioneVersIntegraleBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true, fileDaTimbrare);
					lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneVersIntegraleBean.getNomeFile());
					lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneVersIntegraleBean.getUri());
					lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneVersIntegraleBean.getInfoFile());
					FileDaFirmareBean lFileUnioneVersConOmissisBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, false, fileDaTimbrare);
					lUnioneFileAttoBean.setNomeFile(lFileUnioneVersConOmissisBean.getNomeFile());
					lUnioneFileAttoBean.setUri(lFileUnioneVersConOmissisBean.getUri());
					lUnioneFileAttoBean.setInfoFile(lFileUnioneVersConOmissisBean.getInfoFile());
					lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneVersConOmissisBean.getUriFileOdtGenerato());
				} else {
					FileDaFirmareBean lFileUnioneBean = generaFileUnione(pNuovaPropostaAtto2CompletaBean, true, fileDaTimbrare);
					lUnioneFileAttoBean.setNomeFileVersIntegrale(lFileUnioneBean.getNomeFile());
					lUnioneFileAttoBean.setUriVersIntegrale(lFileUnioneBean.getUri());
					lUnioneFileAttoBean.setInfoFileVersIntegrale(lFileUnioneBean.getInfoFile());
					lUnioneFileAttoBean.setNomeFile(null);
					lUnioneFileAttoBean.setUri(null);
					lUnioneFileAttoBean.setInfoFile(new MimeTypeFirmaBean());
					lUnioneFileAttoBean.setUriFileOdtGenerato(lFileUnioneBean.getUriFileOdtGenerato());
				}
			}					
		}  catch(StoreException e) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante l'unione dei file. " + e.getMessage());
		} catch (Exception e1) {
			logger.error("Si è verificato un errore durante l'unione dei file. " + e1.getMessage(), e1);
			throw new StoreException("Si è verificato un errore durante l'unione dei file.");
		}					
		return lUnioneFileAttoBean;
	}
	
	public TaskFileDaFirmareBean getFileDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		boolean fileDaTimbrare = getExtraparams().get("fileDaTimbrare") != null ? new Boolean(getExtraparams().get("fileDaTimbrare")) : false;		
//		boolean skipFirmaAllegatiParteIntegrante = getExtraparams().get("skipFirmaAllegatiParteIntegrante") != null ? new Boolean(getExtraparams().get("skipFirmaAllegatiParteIntegrante")) : false;
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Aggiungo il primario
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario())){
			aggiungiPrimario(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
		}
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis())){
			aggiungiPrimarioOmissis(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
//		if (!skipFirmaAllegatiParteIntegrante) {
			if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
				for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
					if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
						lAllegatoProtocolloBean.setIdUdAppartenenza(pNuovaPropostaAtto2CompletaBean.getIdUd());
						if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
							if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
								aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean);
							}
						} 
						if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
							if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())) {
								aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean);
							}
						}
					}
				}
			}
//		}
		if(fileDaTimbrare && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUd())) {
			ArrayList<FileDaFirmareBean> listaFileDaFirmareTimbrati = new ArrayList<FileDaFirmareBean>();
			for(int i = 0; i < listaFileDaFirmare.size(); i++) {
				listaFileDaFirmareTimbrati.add(timbraFile(listaFileDaFirmare.get(i), pNuovaPropostaAtto2CompletaBean.getIdUd()));
			}
			lTaskFileDaFirmareBean.setFiles(listaFileDaFirmareTimbrati);
		} else {
			lTaskFileDaFirmareBean.setFiles(listaFileDaFirmare);
		}		
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileVersPubblAggiornataDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileDaFirmare = new ArrayList<FileDaFirmareBean>();
		if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis())) {
//			if(!pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis().isFirmato()) {
				aggiungiPrimarioOmissis(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
//			}
		} else if (StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario())) {
//			if(!pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario().isFirmato()) {
				aggiungiPrimario(listaFileDaFirmare, pNuovaPropostaAtto2CompletaBean);
//			}
		}
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
				boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();
				boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
				boolean flgPubblicaAllegatiSeparati = pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati();
				boolean flgDatiSensibili = lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili();
				if (flgParteDispositivo && !flgNoPubblAllegato && (flgPubblicaAllegatiSeparati || flgPubblicaSeparato)) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis()) && flgDatiSensibili) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())) {
//							if (!lAllegatoProtocolloBean.getInfoFileOmissis().isFirmato()) {
								aggiungiAllegatoOmissis(listaFileDaFirmare, lAllegatoProtocolloBean);
//							}
						}
					} else if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())) {
	//						if (!lAllegatoProtocolloBean.getInfoFileAllegato().isFirmato()) {
								aggiungiAllegato(listaFileDaFirmare, lAllegatoProtocolloBean);
	//						}
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmare(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
							aggiungiAllegato(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					} 
					if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())) {
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())){
							aggiungiAllegatoOmissis(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileAllegatiDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	public TaskFileDaFirmareBean getFileAllegatiDaFirmareWithFileUnione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws StorageException, Exception{
		boolean skipOmissisInFirmaAdozioneAtto = ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
		TaskFileDaFirmareBean lTaskFileDaFirmareBean = new TaskFileDaFirmareBean();
		ArrayList<FileDaFirmareBean> listaFileAllegatiDaFirmare = new ArrayList<FileDaFirmareBean>();
		//Per ogni allegato devo recuperare solo quello che mi serve
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				if (lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo()) {
					String idUd = pNuovaPropostaAtto2CompletaBean.getIdUd() != null ? String.valueOf(pNuovaPropostaAtto2CompletaBean.getIdUd()) : null;
					lAllegatoProtocolloBean.setIdUdAppartenenza(idUd);
					if (StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileAllegato())){
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileAllegato(), lAllegatoProtocolloBean.getInfoFile())){
							aggiungiAllegato(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					} 
					if (!skipOmissisInFirmaAdozioneAtto && lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili() && StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileOmissis())){
						if (!skipFirmaAllegatiFirmati(lAllegatoProtocolloBean.getUriFileOmissis(), lAllegatoProtocolloBean.getInfoFileOmissis())){
							aggiungiAllegatoOmissis(listaFileAllegatiDaFirmare, lAllegatoProtocolloBean);
						}
					}
				}
			}
		}
		lTaskFileDaFirmareBean.setFiles(listaFileAllegatiDaFirmare);
		return lTaskFileDaFirmareBean;		
	}
	
	private FileDaFirmareBean timbraFile(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		logger.debug("TIMBRO FILE");		
		logger.debug("File " + lFileDaTimbrareBean.getNomeFile() + ": " + lFileDaTimbrareBean.getUri());
		if (StringUtils.isNotBlank(idUd)) {			
			logger.debug("idUd: " + idUd);
			if (lFileDaTimbrareBean.getInfoFile().isFirmato() && lFileDaTimbrareBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
				logger.debug("Il file è firmato in CAdES");
				FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaTimbrareBean);
				if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
					logger.debug("Il file sbustato è un pdf, quindi lo timbro");
					return timbraFilePdf(lFileSbustatoBean, idUd);						
				} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
					logger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
					logger.debug("mimetype: " + lFileDaTimbrareBean.getInfoFile().getMimetype());							
					try {
						FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
						return timbraFilePdf(lFileSbustatoConvertitoBean, idUd);	
					} catch (Exception e) {
						logger.debug("Errore durante la conversione del file sbustato: " + e.getMessage(), e);
					}
				} else {
					logger.debug("Il file sbustato non è un pdf e non è convertibile, quindi non lo timbro");
				}
			} else if (lFileDaTimbrareBean.getInfoFile().getMimetype().equals("application/pdf")) {
				logger.debug("Il file è un pdf, quindi lo timbro");
				return timbraFilePdf(lFileDaTimbrareBean, idUd);		
			} else if (lFileDaTimbrareBean.getInfoFile().isConvertibile()) {
				logger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo timbro");
				try {
					FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaTimbrareBean);
					return timbraFilePdf(lFileConvertitoBean, idUd);	
				} catch (Exception e) {
					logger.debug("Errore durante la conversione del file: " + e.getMessage(), e);
				}
			} else {
				logger.debug("Il file non è un pdf e non è convertibile, quindi non lo timbro");
			}							
		} else {
			logger.debug("idUd non valorizzata, quindi non lo timbro");
		}
		return lFileDaTimbrareBean;
	}
	
	private FileDaFirmareBean timbraFilePdf(FileDaFirmareBean lFileDaTimbrareBean, String idUd) throws Exception {
		OpzioniTimbroBean lOpzioniTimbroBean = new OpzioniTimbroBean();
		lOpzioniTimbroBean.setMimetype("application/pdf");
		lOpzioniTimbroBean.setUri(lFileDaTimbrareBean.getUri());
		lOpzioniTimbroBean.setNomeFile(lFileDaTimbrareBean.getNomeFile());
		lOpzioniTimbroBean.setIdUd(idUd);
		lOpzioniTimbroBean.setRemote(true);
		TimbraUtility timbraUtility = new TimbraUtility();
		lOpzioniTimbroBean = timbraUtility.loadSegnatureRegistrazioneDefault(lOpzioniTimbroBean, getSession(), getLocale());
		
		// Setto i parametri del timbro utilizzando dal config.xml il bean OpzioniTimbroAutoDocRegBean
		try{
			OpzioniTimbroAttachEmail lOpzTimbroAutoDocRegBean = (OpzioniTimbroAttachEmail) SpringAppContext.getContext().getBean("OpzioniTimbroAutoDocRegBean");
			if(lOpzTimbroAutoDocRegBean != null){
				lOpzioniTimbroBean.setPosizioneTimbro(lOpzTimbroAutoDocRegBean.getPosizioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getPosizioneTimbro()) ? lOpzTimbroAutoDocRegBean.getPosizioneTimbro().value() : "altoSn");
				lOpzioniTimbroBean.setRotazioneTimbro(lOpzTimbroAutoDocRegBean.getRotazioneTimbro() != null &&
						!"".equals(lOpzTimbroAutoDocRegBean.getRotazioneTimbro()) ? lOpzTimbroAutoDocRegBean.getRotazioneTimbro().value() : "verticale");
				if (lOpzTimbroAutoDocRegBean.getPaginaTimbro() != null) {
					if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina() != null) {
						lOpzioniTimbroBean.setTipoPagina(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getTipoPagina().value());
					} else if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine() != null) {
						lOpzioniTimbroBean.setTipoPagina("intervallo");
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaDa(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa()));
						}
						if (lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaDa() != null) {
							lOpzioniTimbroBean.setPaginaA(String.valueOf(lOpzTimbroAutoDocRegBean.getPaginaTimbro().getPagine().getPaginaA()));
						}
					}
				}
				lOpzioniTimbroBean.setTimbroSingolo(lOpzTimbroAutoDocRegBean.isTimbroSingolo());
				lOpzioniTimbroBean.setMoreLines(lOpzTimbroAutoDocRegBean.isRigheMultiple());
			}
		} catch (NoSuchBeanDefinitionException e) {
			/**
			 * Se il Bean OpzioniTimbroAutoDocRegBean non è correttamente configurato vengono utilizzare le preference del 
			 * bean OpzioniTimbroAttachEmail affinchè la timbratura vada a buon fine.
			 */
			logger.warn("OpzioniTimbroAutoDocRegBean non definito nel file di configurazione");
		}

		// Timbro il file
		TimbraResultBean lTimbraResultBean = timbraUtility.timbra(lOpzioniTimbroBean, getSession());
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
		InfoFileUtility lFileUtility = new InfoFileUtility();
		lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lFileTimbrato.toURI().toString(), lFileDaTimbrareBean.getNomeFile(), false, null);
		lMimeTypeFirmaBean.setFirmato(false);
		lMimeTypeFirmaBean.setFirmaValida(false);
		lMimeTypeFirmaBean.setConvertibile(false);
		lMimeTypeFirmaBean.setDaScansione(false);
		lFileDaTimbrareBean.setInfoFile(lMimeTypeFirmaBean);				
		return lFileDaTimbrareBean;
	}
	
	public FileDaFirmareBean generaFileUnione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean, boolean flgMostraDatiSensibili) throws Exception{
		return generaFileUnione(pNuovaPropostaAtto2CompletaBean, flgMostraDatiSensibili, false);
	}
	
	public FileDaFirmareBean generaFileUnione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean, boolean flgMostraDatiSensibili, boolean fileDaTimbrare) throws Exception{
		
		logger.debug("UNIONE DEI FILE");
		logger.debug("#######INIZIO generaFileUnione######");

		boolean flgAllegatiParteIntUnitiVersIntegrale = false;
		boolean flgAllegatiParteIntUnitiVersXPubbl = false;
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersIntegrale = new ArrayList<AllegatoParteIntSeparatoBean>();
		List<AllegatoParteIntSeparatoBean> listaAllegatiParteIntSeparatiVersXPubbl = new ArrayList<AllegatoParteIntSeparatoBean>();
		if (pNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			boolean flgPubblicaAllegatiSeparati = pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati() != null && pNuovaPropostaAtto2CompletaBean.getFlgPubblicaAllegatiSeparati(); // se è true tutti gli allegati sono da pubblicare separatamente		
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : pNuovaPropostaAtto2CompletaBean.getListaAllegati()){
				boolean flgParteDispositivo = lAllegatoProtocolloBean.getFlgParteDispositivo() != null && lAllegatoProtocolloBean.getFlgParteDispositivo();
				boolean flgPubblicaSeparato = lAllegatoProtocolloBean.getFlgPubblicaSeparato() != null && lAllegatoProtocolloBean.getFlgPubblicaSeparato();
				boolean flgNoPubblAllegato = lAllegatoProtocolloBean.getFlgNoPubblAllegato() != null && lAllegatoProtocolloBean.getFlgNoPubblAllegato();				
				if (flgParteDispositivo) { // se è parte integrante						
					if(flgMostraDatiSensibili) {
						// se è la vers. integrale
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
							lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
							lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
							lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
							lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
							lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
							lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
							listaAllegatiParteIntSeparatiVersIntegrale.add(lAllegatoParteIntSeparatoBean);						
						} else {
							flgAllegatiParteIntUnitiVersIntegrale = true;
						}
					} else if(!flgNoPubblAllegato) {
						// se è la vers. per la pubbl. e l'allegato non è escluso dalla pubblicazione
						if (flgPubblicaAllegatiSeparati || flgPubblicaSeparato) { // se è da pubblicare separatamente						
							if (lAllegatoProtocolloBean.getFlgDatiSensibili() != null && lAllegatoProtocolloBean.getFlgDatiSensibili()) {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBeanOmissis = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBeanOmissis.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBeanOmissis.setNomeFile(lAllegatoProtocolloBean.getNomeFileOmissis());
								lAllegatoParteIntSeparatoBeanOmissis.setImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getImpronta() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBeanOmissis.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFileOmissis() != null ? lAllegatoProtocolloBean.getInfoFileOmissis().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBeanOmissis);	
							} else {
								AllegatoParteIntSeparatoBean lAllegatoParteIntSeparatoBean = new AllegatoParteIntSeparatoBean();
								lAllegatoParteIntSeparatoBean.setDesTipoAllegato(lAllegatoProtocolloBean.getDescTipoFileAllegato());
								lAllegatoParteIntSeparatoBean.setDescrizione(lAllegatoProtocolloBean.getDescrizioneFileAllegato());
								lAllegatoParteIntSeparatoBean.setNomeFile(lAllegatoProtocolloBean.getNomeFileAllegato());
								lAllegatoParteIntSeparatoBean.setImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getImpronta() : null);
								lAllegatoParteIntSeparatoBean.setAlgoritmoImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getAlgoritmo() : null);
								lAllegatoParteIntSeparatoBean.setEncodingImpronta(lAllegatoProtocolloBean.getInfoFile() != null ? lAllegatoProtocolloBean.getInfoFile().getEncoding() : null);
								listaAllegatiParteIntSeparatiVersXPubbl.add(lAllegatoParteIntSeparatoBean);	
							}
						} else {
							flgAllegatiParteIntUnitiVersXPubbl = true;
						}
					}
				}
			}
		}
		
		if(flgMostraDatiSensibili) {
			// se è la vers. integrale
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUniti(flgAllegatiParteIntUnitiVersIntegrale);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparati(listaAllegatiParteIntSeparatiVersIntegrale);
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUnitiXPubbl(null);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparatiXPubbl(null);
		} else {
			// se è la vers. per la pubbl.
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUniti(null);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparati(null);
			pNuovaPropostaAtto2CompletaBean.setFlgAllegatiParteIntUnitiXPubbl(flgAllegatiParteIntUnitiVersXPubbl);
			pNuovaPropostaAtto2CompletaBean.setListaAllegatiParteIntSeparatiXPubbl(listaAllegatiParteIntSeparatiVersXPubbl);
		}

		String templateValuesPratica = null;
		List<FileDaFirmareBean> lListFileDaUnireBean = flgMostraDatiSensibili ? getListaFileDaUnire(pNuovaPropostaAtto2CompletaBean) : getListaFileDaUnireOmissis(pNuovaPropostaAtto2CompletaBean);
		String uriFileOdtDaSalvare = null;
		if (lListFileDaUnireBean != null && !lListFileDaUnireBean.isEmpty()) {		
//			List<InputStream> lListInputStream = new ArrayList<InputStream>();
			List<FileDaFirmareBean> lListFile = new ArrayList<FileDaFirmareBean>();
			
			if (pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile() != null && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile().getNroPagineFirmeGrafiche())) {
				Integer nroPagineFirmeGrafiche = new Integer(pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile().getNroPagineFirmeGrafiche());
				if (nroPagineFirmeGrafiche != null && nroPagineFirmeGrafiche > 0) {
					String idModelloFirmeGrafiche = pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile().getIdModelloDocFirmeGrafiche();
					String nomeModelloFirmeGrafiche = pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile().getNomeModelloDocFirmeGrafiche();
					
					if(StringUtils.isNotBlank(idModelloFirmeGrafiche) && StringUtils.isNotBlank(nomeModelloFirmeGrafiche)) {
						String templateValuesFirmaGrafica = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, nomeModelloFirmeGrafiche, !flgMostraDatiSensibili);				
						Map<String, Object> mappaValoriFirmaGrafica = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesFirmaGrafica, true);
						
						mappaValoriFirmaGrafica.put("primaPaginaModelloFirme", "true");
						FileDaFirmareBean lFileDaFirmareBeanFirmaGrafica = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), idModelloFirmeGrafiche, mappaValoriFirmaGrafica, true, flgMostraDatiSensibili, getSession()); 
						lFileDaFirmareBeanFirmaGrafica.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanFirmaGrafica.getUri()));
						lFileDaFirmareBeanFirmaGrafica.setTipoFileUnione(TipoFileUnione.MODELLO_FIRME_GRAFICHE);
						lListFile.add(lFileDaFirmareBeanFirmaGrafica);

						for (int i = 0; i < nroPagineFirmeGrafiche-1; i++) {	
							mappaValoriFirmaGrafica.put("primaPaginaModelloFirme", "false");
							lFileDaFirmareBeanFirmaGrafica = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), idModelloFirmeGrafiche, mappaValoriFirmaGrafica, true, flgMostraDatiSensibili, getSession()); 
							lFileDaFirmareBeanFirmaGrafica.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanFirmaGrafica.getUri()));
							lFileDaFirmareBeanFirmaGrafica.setTipoFileUnione(TipoFileUnione.MODELLO_FIRME_GRAFICHE);
							lListFile.add(lFileDaFirmareBeanFirmaGrafica);
						}
					}else {
						logger.warn("IdModello e nomeModello per firme grafiche non restituito");
					}
				}
			}
			
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModCopertinaFinale())) {	
				String templateValuesCopertinaFinale = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModCopertinaFinale(), !flgMostraDatiSensibili);				
//				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale(), templateValuesCopertinaFinale, true, getSession());
				Map<String, Object> mappaValoriCopertinaFinale = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertinaFinale, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertinaFinale = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertinaFinale(), mappaValoriCopertinaFinale, true, flgMostraDatiSensibili, getSession()); 
//				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertinaFinale.getUri()));	
				lFileDaFirmareBeanCopertinaFinale.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanCopertinaFinale.getUri()));
				lFileDaFirmareBeanCopertinaFinale.setTipoFileUnione(TipoFileUnione.MODELLO_COPERTINA_FINALE);
				lListFile.add(lFileDaFirmareBeanCopertinaFinale);
			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModCopertinaFinale())) {				
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
				}
				File fileCopertinaFinale = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModCopertinaFinale(), pNuovaPropostaAtto2CompletaBean.getTipoModCopertinaFinale(), templateValuesPratica, getSession());
//				lListInputStream.add(new FileInputStream(fileCopertinaFinale));		
				FileDaFirmareBean lfileCopertinaFinale = new FileDaFirmareBean();
				lfileCopertinaFinale.setFile(fileCopertinaFinale);
				lfileCopertinaFinale.setTipoFileUnione(TipoFileUnione.MODELLO_COPERTINA_FINALE);
				lListFile.add(lfileCopertinaFinale);
			} 
			if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModCopertina()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModCopertina())) {	
				String templateValuesCopertina = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModCopertina(), !flgMostraDatiSensibili);				
//				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertina(), templateValuesCopertina, true, getSession());
				Map<String, Object> mappaValoriCopertina = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesCopertina, true);
				FileDaFirmareBean lFileDaFirmareBeanCopertina = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModCopertina(), mappaValoriCopertina, true, flgMostraDatiSensibili, getSession()); 
//				lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanCopertina.getUri()));		
				lFileDaFirmareBeanCopertina.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanCopertina.getUri()));
				lFileDaFirmareBeanCopertina.setTipoFileUnione(TipoFileUnione.MODELLO_COPERTINA);
				lListFile.add(lFileDaFirmareBeanCopertina);

			} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModCopertina())) {
				if(templateValuesPratica == null) {
					templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
				}
				File fileCopertina = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModCopertina(), pNuovaPropostaAtto2CompletaBean.getTipoModCopertina(), templateValuesPratica, getSession());
//				lListInputStream.add(new FileInputStream(fileCopertina));		
				FileDaFirmareBean lfileCopertina = new FileDaFirmareBean();
				lfileCopertina.setFile(fileCopertina);
				lfileCopertina.setTipoFileUnione(TipoFileUnione.MODELLO_COPERTINA);
				lListFile.add(lfileCopertina);
			}
			logger.debug("#######INIZIO for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean)#######");
			for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean) {
				if (lFileDaUnireBean.getIsDispositivoNuovaPropostaAtto2Completa() != null && lFileDaUnireBean.getIsDispositivoNuovaPropostaAtto2Completa()) {
					uriFileOdtDaSalvare = lFileDaUnireBean.getUriFileOdtGenerato();
				}
				logger.debug("File " + lFileDaUnireBean.getNomeFile() + ": " + lFileDaUnireBean.getUri());
				if (lFileDaUnireBean.getInfoFile().isFirmato() && lFileDaUnireBean.getInfoFile().getTipoFirma().startsWith("CAdES")) {
					logger.debug("Il file è firmato in CAdES");
					FileDaFirmareBean lFileSbustatoBean = sbustaFile(lFileDaUnireBean);
					if (lFileDaUnireBean.getInfoFile().getMimetype().equals("application/pdf")) {
						logger.debug("Il file sbustato è un pdf, quindi lo aggiungo");
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoBean.getUri()));
						lFileSbustatoBean.setFile(StorageImplementation.getStorage().extractFile(lFileSbustatoBean.getUri()));
						lListFile.add(lFileSbustatoBean);
					} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
						logger.debug("Il file sbustato non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
						logger.debug("mimetype: " + lFileDaUnireBean.getInfoFile().getMimetype());							
						try {
							FileDaFirmareBean lFileSbustatoConvertitoBean = convertiFile(lFileSbustatoBean);
//							lListInputStream.add(StorageImplementation.getStorage().extract(lFileSbustatoConvertitoBean.getUri()));	
							lFileSbustatoConvertitoBean.setFile(StorageImplementation.getStorage().extractFile(lFileSbustatoConvertitoBean.getUri()));
							lListFile.add(lFileSbustatoConvertitoBean);
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
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUriVerPreFirma()));
						lFileDaUnireBean.setFile(StorageImplementation.getStorage().extractFile(lFileDaUnireBean.getUriVerPreFirma()));
						lListFile.add(lFileDaUnireBean);
					} else {
						logger.debug("Il file è un pdf, quindi lo aggiungo");
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaUnireBean.getUri()));
						lFileDaUnireBean.setFile(StorageImplementation.getStorage().extractFile(lFileDaUnireBean.getUri()));
						lListFile.add(lFileDaUnireBean);
					}
				} else if (lFileDaUnireBean.getInfoFile().isConvertibile()) {
					logger.debug("Il file non è un pdf ed è convertibile, quindi provo a convertirlo e lo aggiungo");
					try {
						FileDaFirmareBean lFileConvertitoBean = convertiFile(lFileDaUnireBean);
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileConvertitoBean.getUri()));
						lFileConvertitoBean.setFile(StorageImplementation.getStorage().extractFile(lFileConvertitoBean.getUri()));
						lListFile.add(lFileConvertitoBean);
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
			logger.debug("#######FINE for (FileDaFirmareBean lFileDaUnireBean : lListFileDaUnireBean)#######");
			if(flgMostraDatiSensibili) {
				// se è la vers. integrale e ho file allegati parte integrante sia uniti che separati
				if(listaAllegatiParteIntSeparatiVersIntegrale.size() > 0 && flgAllegatiParteIntUnitiVersIntegrale) {
					if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparati())) {	
						String templateValuesAllegatiParteIntSeparati = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparati(), !flgMostraDatiSensibili);				
//						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati(), templateValuesAllegatiParteIntSeparati, true, getSession());
						Map<String, Object> mappaValoriAllegatiParteIntSeparati = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAllegatiParteIntSeparati, true);
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparati(), mappaValoriAllegatiParteIntSeparati, true, flgMostraDatiSensibili, getSession()); 
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAllegatiParteIntSeparati.getUri()));		
						lFileDaFirmareBeanAllegatiParteIntSeparati.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAllegatiParteIntSeparati.getUri()));
						lFileDaFirmareBeanAllegatiParteIntSeparati.setTipoFileUnione(TipoFileUnione.MODELLO_ALLEGATI_SEPARATI);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparati);
		
					} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparati())) {
						if(templateValuesPratica == null) {
							templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
						}
						File fileAllegatiParteIntSeparati = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparati(), pNuovaPropostaAtto2CompletaBean.getTipoModAllegatiParteIntSeparati(), templateValuesPratica, getSession());
//						lListInputStream.add(new FileInputStream(fileAllegatiParteIntSeparati));		
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparati = new FileDaFirmareBean();
						lFileDaFirmareBeanAllegatiParteIntSeparati.setFile(fileAllegatiParteIntSeparati);
						lFileDaFirmareBeanAllegatiParteIntSeparati.setTipoFileUnione(TipoFileUnione.MODELLO_ALLEGATI_SEPARATI);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparati);
					}
				}
			} else {
				// se è la vers. per la pubbl. e ho file allegati parte integrante sia uniti che separati (e non esclusi)				
				if(listaAllegatiParteIntSeparatiVersXPubbl.size() > 0 && flgAllegatiParteIntUnitiVersXPubbl) {
					if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparatiXPubbl())) {	
						String templateValuesAllegatiParteIntSeparatiXPubbl = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAllegatiParteIntSeparatiXPubbl(), !flgMostraDatiSensibili);				
//						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl(), templateValuesAllegatiParteIntSeparatiXPubbl, true, getSession());
						Map<String, Object> mappaValoriAllegatiParteIntSeparatiXPubbl = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAllegatiParteIntSeparatiXPubbl, true);
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAllegatiParteIntSeparatiXPubbl(), mappaValoriAllegatiParteIntSeparatiXPubbl, true, flgMostraDatiSensibili, getSession()); 
//						lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.getUri()));		
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.getUri()));
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setTipoFileUnione(TipoFileUnione.MODELLO_ALLEGATI_SEPARATI);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl);
		
					} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparatiXPubbl())) {
						if(templateValuesPratica == null) {
							templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
						}
						File fileAllegatiParteIntSeparatiXPubbl = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAllegatiParteIntSeparatiXPubbl(), pNuovaPropostaAtto2CompletaBean.getTipoModAllegatiParteIntSeparatiXPubbl(), templateValuesPratica, getSession());
//						lListInputStream.add(new FileInputStream(fileAllegatiParteIntSeparatiXPubbl));		
						FileDaFirmareBean lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl = new FileDaFirmareBean();
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setFile(fileAllegatiParteIntSeparatiXPubbl);
						lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl.setTipoFileUnione(TipoFileUnione.MODELLO_ALLEGATI_SEPARATI);
						lListFile.add(lFileDaFirmareBeanAllegatiParteIntSeparatiXPubbl);
					}
				}
			}	
			logger.debug("#######INIZIO flgAppendiceDaUnire#######");
			if(pNuovaPropostaAtto2CompletaBean.getFlgAppendiceDaUnire() != null && pNuovaPropostaAtto2CompletaBean.getFlgAppendiceDaUnire()) {
				if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdModAppendice()) && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getNomeModAppendice())) {	
					String templateValuesAppendice = getDatiXGenDaModello(pNuovaPropostaAtto2CompletaBean, pNuovaPropostaAtto2CompletaBean.getNomeModAppendice(), !flgMostraDatiSensibili);				
//					FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillTemplate(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAppendice(), templateValuesAppendice, true, getSession());
					Map<String, Object> mappaValoriAppendice = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValuesAppendice, true);
					FileDaFirmareBean lFileDaFirmareBeanAppendice = ModelliUtil.fillFreeMarkerTemplateWithModel(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getIdModAppendice(), mappaValoriAppendice, true, flgMostraDatiSensibili, getSession()); 
//					lListInputStream.add(StorageImplementation.getStorage().extract(lFileDaFirmareBeanAppendice.getUri()));		
					logger.debug("#######INIZIO inf flgAppendiceDaUnire extractFile#######");
					lFileDaFirmareBeanAppendice.setFile(StorageImplementation.getStorage().extractFile(lFileDaFirmareBeanAppendice.getUri()));
					logger.debug("#######FINE inf flgAppendiceDaUnire extractFile#######");
					lFileDaFirmareBeanAppendice.setTipoFileUnione(TipoFileUnione.MODELLO_APPENDICE);
					lListFile.add(lFileDaFirmareBeanAppendice);
				} else if(StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getUriModAppendice())) {
					if(templateValuesPratica == null) {
						templateValuesPratica = getDatiXModelliPratica(pNuovaPropostaAtto2CompletaBean);
					}
					logger.debug("#######INIZIO inf flgAppendiceDaUnire ModelliUtil.fillTemplateAndConvertToPdf#######");
					File fileAppendice = ModelliUtil.fillTemplateAndConvertToPdf(pNuovaPropostaAtto2CompletaBean.getIdProcess(), pNuovaPropostaAtto2CompletaBean.getUriModAppendice(), pNuovaPropostaAtto2CompletaBean.getTipoModAppendice(), templateValuesPratica, getSession());
					logger.debug("#######FINE inf flgAppendiceDaUnire ModelliUtil.fillTemplateAndConvertToPdf#######");
//					lListInputStream.add(new FileInputStream(fileAppendice));	
					FileDaFirmareBean lfileAppendice = new FileDaFirmareBean();
					lfileAppendice.setFile(fileAppendice);
					lfileAppendice.setTipoFileUnione(TipoFileUnione.MODELLO_APPENDICE);
					lListFile.add(lfileAppendice);
				}
			}
			logger.debug("#######FINE flgAppendiceDaUnire#######");
			
//			File lFileUnione = unioneFilePdfOld(lListInputStream);
			logger.debug("#######INIZIO unioneFilePdf#######");
			File lFileUnione = unioneFilePdf(lListFile, pNuovaPropostaAtto2CompletaBean.getImpostazioniUnioneFile());
			logger.debug("#######FINE unioneFilePdf#######");
//			File lFileUnione = mergeFilePdf(lListFile);

			String nomeFileUnione = null;
			if(flgMostraDatiSensibili) {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnione")) ? getExtraparams().get("nomeFileUnione") : getPrefixRegNum(pNuovaPropostaAtto2CompletaBean) + "ATTO_COMPLETO_VERS_INTEGRALE.pdf";
			} else {
				nomeFileUnione = StringUtils.isNotBlank(getExtraparams().get("nomeFileUnioneOmissis")) ? getExtraparams().get("nomeFileUnioneOmissis") : getPrefixRegNum(pNuovaPropostaAtto2CompletaBean) + "ATTO_COMPLETO_VERS_X_PUBBLICAZIONE.pdf";
			}		
			logger.debug("#######INIZIO store file unione#######");
			String uriFileUnione = StorageImplementation.getStorage().store(lFileUnione, new String[] {});
			logger.debug("#######FINE store file unione#######");
			FileDaFirmareBean lFileUnioneBean = new FileDaFirmareBean();
			lFileUnioneBean.setUri(uriFileUnione);
			lFileUnioneBean.setNomeFile(nomeFileUnione);	
//			lFileUnioneBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(uriFileUnione).toURI().toString(), nomeFileUnione, false, null));
			MimeTypeFirmaBean infoFileUnione = new MimeTypeFirmaBean();
			infoFileUnione.setMimetype("application/pdf");
			infoFileUnione.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(lFileUnione));
			infoFileUnione.setDaScansione(false);
			infoFileUnione.setFirmato(false);
			infoFileUnione.setFirmaValida(false);
			infoFileUnione.setBytes(lFileUnione.length());
			infoFileUnione.setCorrectFileName(nomeFileUnione);
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			logger.debug("#######INIZIO getRealFile#######");
//			File realFile = StorageImplementation.getStorage().getRealFile(uriFileUnione);
			logger.debug("#######FINE getRealFile#######");
			logger.debug("#######INIZIO calcola impronta unione#######");
			logger.debug("#######REALFILEURI : " + lFileUnione.toURI().toString() + "#######");
			logger.debug("#######uriFileUnione : " + uriFileUnione + "#######");
			infoFileUnione.setImpronta(lInfoFileUtility.calcolaImpronta(lFileUnione.toURI().toString(), nomeFileUnione));
			logger.debug("#######FINE calcola impronta unione#######");
			lFileUnioneBean.setInfoFile(infoFileUnione);
			lFileUnioneBean.setUriFileOdtGenerato(uriFileOdtDaSalvare);			
			lFileUnioneBean.setIsFilePrincipaleAtto(true);
			logger.debug("#######FINE generaFileUnione######");
			if (fileDaTimbrare && StringUtils.isNotBlank(pNuovaPropostaAtto2CompletaBean.getIdUd())) {			
				return timbraFile(lFileUnioneBean, pNuovaPropostaAtto2CompletaBean.getIdUd());
			} else {
				return lFileUnioneBean;
			}								
		} else {
			String errorMessage = "E' obbligatorio inserire almeno un file per procedere";
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
	}

	public File unioneFilePdf(List<FileDaFirmareBean> lListFile, ImpostazioniUnioneFileBean impostazioneUnioneFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;   
	
		
		// recupero le impostazioni di unione relative al numero pagina degli allegati
		boolean nroPaginaAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaAttiva() && impostazioneUnioneFile.getNroPaginaAttiva() ? true : false;
		boolean nroPaginaEscludiAllegatiMaggioreA4 = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiMaggioreA4()!= null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiMaggioreA4() ? true : false;
		boolean nroPaginaEscludiAllegatiUnitiInFondo = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInFondo()!=null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInFondo() ? true : false;
		boolean nroPaginaEscludiFoglioFirmeGrafiche = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiFoglioFirmeGrafiche()!=null && impostazioneUnioneFile.getNroPaginaEscludiFoglioFirmeGrafiche() ? true : false;
		boolean nroPaginaEscludiListaAllegatiSeparati = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiListaAllegatiSeparati() !=null && impostazioneUnioneFile.getNroPaginaEscludiListaAllegatiSeparati() ? true : false;
		boolean nroPaginaEscludiAppendice = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAppendice() !=null && impostazioneUnioneFile.getNroPaginaEscludiAppendice() ? true : false;
		boolean nroPaginaNumerazioneDistintaAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaNumerazioneDistintaXFileUniti()!=null && impostazioneUnioneFile.getNroPaginaNumerazioneDistintaXFileUniti() ? true : false;
		boolean posizioneAllegatiUnitiAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getPosizioneAllegatiUnitiAttiva()!=null && impostazioneUnioneFile.getPosizioneAllegatiUnitiAttiva() ? true : false;
		int nTotDoc = 0;
		
		if (!nroPaginaNumerazioneDistintaAttiva) {
			nTotDoc = getTotalNumberPages(lListFile, impostazioneUnioneFile);
		}     
		
		// inizializzo un counter per gli allegati
		Integer counterAllegati = 0;
		//inizializzo il counter delle pagine per la numerazione 
		Integer numerazioneUltimaPagina = 0;
		
		/*In lListFile potrò avere una lista di file nella seguente sequenza (dipende dai modelli che sono attivati per l'unione)
		 * 
		 *  - MODELLO FIRME GRAFICHE
		 *  - COPERTINA
		 *  - COPERTINA FINALE
		 *  - MODELLO DISPOSITIVO
		 *  - N ALLEGATI
		 *  - MODELLO IMPRONTE ALLEGATI
		 *  - APPENDICE
		 *  
		 * */					
		for (FileDaFirmareBean fileDaFirmareBean : lListFile) {
			
			/*Scorro la lista sulle varie tipologie di file da unire, se è attiva la funzione di inserimento degli allegati
			 * in mezzo alle pagine del dispositivo richiamo la unisciAllegatiAFileDispositivo che mi aggiungerà al copy
			 * già le pagine del dispositivo e degli allegati quindi salto la tipologia allegati perchè sara stata già aggiunta al copy*/
			if (posizioneAllegatiUnitiAttiva) {
				if (fileDaFirmareBean.getTipoFileUnione() != null
						&& TipoFileUnione.MODELLO_DISPOSITIVO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
					
					numerazioneUltimaPagina = unisciAllegatiAFileDispositivo(numerazioneUltimaPagina, impostazioneUnioneFile, copy, lListFile);
					continue;
				} else if (fileDaFirmareBean.getTipoFileUnione() != null
						&& TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
					continue;
				}
			}			
				
			// recupero il file
			File lFile = fileDaFirmareBean.getFile();
			
			// Leggo il file
			reader = new PdfReader(lFile.getAbsolutePath());
			// Prendo il numero di pagine
			int numeroPagine = reader.getNumberOfPages();
			// e per ogni pagina
			if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())){
				counterAllegati++;
				fileDaFirmareBean.setNroProgAllegato(counterAllegati);
			}
			
			
			for (int indicePagina = 0; indicePagina < numeroPagine;) {
				PdfImportedPage importedPage = copy.getImportedPage(reader, ++indicePagina);
				Rectangle pageSizeWithRotation = reader.getPageSizeWithRotation(indicePagina);
				
				// se sto processando un allegato ed è attiva l'aggiunta dell'intestazione
				if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())){
					if(impostazioneUnioneFile != null && impostazioneUnioneFile.getInfoAllegatoAttiva()!= null && impostazioneUnioneFile.getInfoAllegatoAttiva()) {
						aggiungiIntestazioneAllegato(impostazioneUnioneFile, copy, fileDaFirmareBean, indicePagina, importedPage,
								pageSizeWithRotation);
					}
				}
				
				if (nroPaginaAttiva) {					
					boolean addNroPagina = !(nroPaginaEscludiAllegatiMaggioreA4 && PdfUtility.isFileMaggioreA4(reader.getPageSizeWithRotation(indicePagina))) || !nroPaginaEscludiAllegatiMaggioreA4;
					// se non è attiva l'esclusione dei formati maggiori di a4
					// o è attiva ma non è un allegato maggiore di a4 applico il nroPagina
					if (addNroPagina) {
						
						if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
								if(!nroPaginaEscludiAllegatiUnitiInFondo) {
								numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy,
										nroPaginaNumerazioneDistintaAttiva, nTotDoc,
										numerazioneUltimaPagina, numeroPagine, indicePagina, importedPage, pageSizeWithRotation);
								}
						}else if(fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_FIRME_GRAFICHE.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
								if(!nroPaginaEscludiFoglioFirmeGrafiche) {
									numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy,
									nroPaginaNumerazioneDistintaAttiva, nTotDoc,
									numerazioneUltimaPagina, numeroPagine, indicePagina, importedPage, pageSizeWithRotation);
								}
						}else if(fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_ALLEGATI_SEPARATI.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
								if(!nroPaginaEscludiListaAllegatiSeparati) {
									numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy,
									nroPaginaNumerazioneDistintaAttiva, nTotDoc,
									numerazioneUltimaPagina, numeroPagine, indicePagina, importedPage, pageSizeWithRotation);
								}
						}else if(fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_APPENDICE.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione())) {
								if(!nroPaginaEscludiAppendice) {
									numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy,
									nroPaginaNumerazioneDistintaAttiva, nTotDoc,
									numerazioneUltimaPagina, numeroPagine, indicePagina, importedPage, pageSizeWithRotation);
								}
						}else {
							numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy, nroPaginaNumerazioneDistintaAttiva,
									nTotDoc, numerazioneUltimaPagina, numeroPagine, indicePagina, importedPage,
									pageSizeWithRotation);
						}
						
					}
				}
				
				copy.addPage(importedPage);
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
			// Chiudo il reader
			reader.close();			
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}
	
	private Integer unisciAllegatiAFileDispositivo(Integer numerazioneUltimaPagina, ImpostazioniUnioneFileBean impostazioneUnioneFile, PdfCopy copy, List<FileDaFirmareBean> listaFileDaUnire) throws Exception {

		FileDaFirmareBean fileDispositivo = null;
		Map<Integer, List<FileDaFirmareBean>> mappaPagineInserAllegati = new HashMap<>();
		List<FileDaFirmareBean> listaAllegatiInCoda = new ArrayList<FileDaFirmareBean>();
		int numeroPagineDspositivoTot = 0;
		// inizializzo un counter per gli allegati
		Integer counterAllegati = 0;

		/*Nel for sottostante mi vado a recuperareil file dispositivo dalla lista e mi vado a creare una mappa che ha
		 *  come chiave la pagina del dispositivo dopo il quale aggiungere l'allegato e come valore la lista degli allegati da aggiungere*/
		for (FileDaFirmareBean fileDaUnire : listaFileDaUnire) {
			if (fileDaUnire.getTipoFileUnione() != null && TipoFileUnione.MODELLO_DISPOSITIVO.equalsIgnoreCase(fileDaUnire.getTipoFileUnione())) {
				fileDispositivo = fileDaUnire;
				File lFile = fileDaUnire.getFile();
				PdfReader readerCountPages = new PdfReader(lFile.getAbsolutePath());
				// Prendo il numero di pagine
				numeroPagineDspositivoTot = readerCountPages.getNumberOfPages();
				readerCountPages.close();
			} else if (fileDaUnire.getTipoFileUnione() != null && TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaUnire.getTipoFileUnione())) {
				
				counterAllegati++;
				fileDaUnire.setNroProgAllegato(counterAllegati);
				
				if (StringUtils.isNotBlank(fileDaUnire.getNroPagDispositivoUnione())) {
					Integer nroPagDispositivoUnione = Integer.valueOf(fileDaUnire.getNroPagDispositivoUnione());
					
					if(nroPagDispositivoUnione>numeroPagineDspositivoTot) {
						throw new StoreException("Il numero di pagina (" + nroPagDispositivoUnione + ") dopo il quale unire l'allegato: " + fileDaUnire.getNomeFile() + " è superiore al numero di pagine del documento principale (" + numeroPagineDspositivoTot + ")");
					}
					
					List<FileDaFirmareBean> listaAllegatiDaAggiungereAllaPagina;

					if (mappaPagineInserAllegati.get(nroPagDispositivoUnione) != null) {
						listaAllegatiDaAggiungereAllaPagina = mappaPagineInserAllegati.get(nroPagDispositivoUnione);
						listaAllegatiDaAggiungereAllaPagina.add(fileDaUnire);
					} else {
						listaAllegatiDaAggiungereAllaPagina = new ArrayList<FileDaFirmareBean>();
						listaAllegatiDaAggiungereAllaPagina.add(fileDaUnire);
					}
					mappaPagineInserAllegati.put(nroPagDispositivoUnione, listaAllegatiDaAggiungereAllaPagina);
				}else {
					/*se per un allegato non ho trovato un numero di pagina dopo il quale aggiungerlo
					 * lo inserisco in coda al dispositivo e agli eventuali allegati inseriti in mezzo*/
					listaAllegatiInCoda.add(fileDaUnire);
				}
			}
		}

		// recupero le impostazioni di unione relative al numero pagina degli allegati
		boolean nroPaginaAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaAttiva() && impostazioneUnioneFile.getNroPaginaAttiva() ? true : false;
		boolean nroPaginaEscludiAllegatiMaggioreA4 = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiMaggioreA4()!= null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiMaggioreA4() ? true : false;
		boolean nroPaginaNumerazioneDistintaAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaNumerazioneDistintaXFileUniti()!=null && impostazioneUnioneFile.getNroPaginaNumerazioneDistintaXFileUniti() ? true : false;
		boolean nroPaginaEscludiAllegatiUnitiInMezzo = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInMezzo() !=null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInMezzo() ? true : false;		
		
		int nTotDoc = 0;

		if (!nroPaginaNumerazioneDistintaAttiva) {
			nTotDoc = getTotalNumberPages(listaFileDaUnire, impostazioneUnioneFile);
		}

		PdfReader readerDispositivo = new PdfReader(fileDispositivo.getFile().getAbsolutePath());
		// Prendo il numero di pagine
		int numeroPagineDispositivo = readerDispositivo.getNumberOfPages();
		// e per ogni pagina
		for (int indicePaginaDisositivo = 0; indicePaginaDisositivo < numeroPagineDispositivo;) {
			PdfImportedPage importedPageDispositivo = copy.getImportedPage(readerDispositivo, ++indicePaginaDisositivo);
			Rectangle pageSizeWithRotationDispositivo = readerDispositivo.getPageSizeWithRotation(indicePaginaDisositivo);
			
			// se è attiva l'aggiunta del numero pagina
			if (nroPaginaAttiva) {
				boolean addNroPagina = !(nroPaginaEscludiAllegatiMaggioreA4 && PdfUtility.isFileMaggioreA4(readerDispositivo.getPageSizeWithRotation(indicePaginaDisositivo))) || !nroPaginaEscludiAllegatiMaggioreA4;
				// se non è attiva l'esclusione dei formati maggiori di a4
				// o è attiva ma non è un allegato maggiore di a4 applico il nroPagina
				if (addNroPagina) {
					numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy, nroPaginaNumerazioneDistintaAttiva, nTotDoc, numerazioneUltimaPagina, numeroPagineDispositivo, indicePaginaDisositivo, importedPageDispositivo, pageSizeWithRotationDispositivo);	
						}
					}

			
			copy.addPage(importedPageDispositivo);

			/*Controllo nella mappa se per questa pagina (indicePaginaDisositivo) è presente una lista di allegati da aggiungere*/
			if (mappaPagineInserAllegati.get(indicePaginaDisositivo) != null) {
				List<FileDaFirmareBean> listaFileDaAggiungereDopoPagina = mappaPagineInserAllegati.get(indicePaginaDisositivo);
				for (FileDaFirmareBean fileDaAggiungereDopoPagina : listaFileDaAggiungereDopoPagina) {
					PdfReader readerAllegato = new PdfReader(fileDaAggiungereDopoPagina.getFile().getAbsolutePath());
					// Prendo il numero di pagine
					int numeroPagineAllegato = readerAllegato.getNumberOfPages();
					// e per ogni pagina
					for (int indicePaginaAllegato = 0; indicePaginaAllegato < numeroPagineAllegato;) {
						PdfImportedPage importedPageAllegato = copy.getImportedPage(readerAllegato, ++indicePaginaAllegato);
						Rectangle pageSizeWithRotationAllegato = readerAllegato.getPageSizeWithRotation(indicePaginaAllegato);

						// se sto processando un allegato ed è attiva l'aggiunta dell'intestazione
						if (impostazioneUnioneFile != null && impostazioneUnioneFile.getInfoAllegatoAttiva() != null && impostazioneUnioneFile.getInfoAllegatoAttiva()) {
							aggiungiIntestazioneAllegato(impostazioneUnioneFile, copy, fileDaAggiungereDopoPagina, indicePaginaAllegato, importedPageAllegato, pageSizeWithRotationAllegato);
						}

						// se è attiva l'aggiunta del numero pagina
						if (nroPaginaAttiva) {
							boolean addNroPagina = !(nroPaginaEscludiAllegatiMaggioreA4 && PdfUtility.isFileMaggioreA4(readerAllegato.getPageSizeWithRotation(indicePaginaAllegato))) || !nroPaginaEscludiAllegatiMaggioreA4;
							// se non è attiva l'esclusione dei formati maggiori di a4
							// o è attiva ma non è un allegato maggiore di a4 applico il nroPagina
							if (addNroPagina && !nroPaginaEscludiAllegatiUnitiInMezzo) {
								numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy, nroPaginaNumerazioneDistintaAttiva, nTotDoc, numerazioneUltimaPagina, numeroPagineAllegato, indicePaginaAllegato, importedPageAllegato, pageSizeWithRotationAllegato);
							}
						}

						copy.addPage(importedPageAllegato);
					}
					
					copy.freeReader(readerAllegato);
					readerAllegato.close();														
				}
			}
			
		}
		
		copy.freeReader(readerDispositivo);
		readerDispositivo.close();
		
		
		/*Aggiungo in coda eventuali allegati per i quali non è stata specificata una pagina del dispositvo
		 * dopo la quale essere aggiunti*/
		if(listaAllegatiInCoda!=null && listaAllegatiInCoda.size()>0) {
			for(FileDaFirmareBean allegatoDaAggiungereInCoda : listaAllegatiInCoda) {

				// recupero il file
				File lFile = allegatoDaAggiungereInCoda.getFile();
				
				// Leggo il file
				PdfReader readerAllegatoDaAggiungereInCoda = new PdfReader(lFile.getAbsolutePath());
				// Prendo il numero di pagine
				int numeroPagineAllegatoDaAggiungereInCoda = readerAllegatoDaAggiungereInCoda.getNumberOfPages();
				// e per ogni pagina
				for (int indicePaginaAllegatoDaAggiungereInCoda = 0; indicePaginaAllegatoDaAggiungereInCoda < numeroPagineAllegatoDaAggiungereInCoda;) {
					PdfImportedPage importedPageAllegatoDaAggiungereInCoda = copy.getImportedPage(readerAllegatoDaAggiungereInCoda, ++indicePaginaAllegatoDaAggiungereInCoda);
					Rectangle pageSizeWithRotationAllegatoDaAggiungereInCoda = readerAllegatoDaAggiungereInCoda.getPageSizeWithRotation(indicePaginaAllegatoDaAggiungereInCoda);
					
					// se sto processando un allegato ed è attiva l'aggiunta dell'intestazione
					if (impostazioneUnioneFile != null && impostazioneUnioneFile.getInfoAllegatoAttiva()!= null && impostazioneUnioneFile.getInfoAllegatoAttiva()) {
						aggiungiIntestazioneAllegato(impostazioneUnioneFile, copy, allegatoDaAggiungereInCoda, indicePaginaAllegatoDaAggiungereInCoda, importedPageAllegatoDaAggiungereInCoda, pageSizeWithRotationAllegatoDaAggiungereInCoda);
					}
					
					// se è attiva l'aggiunta del numero pagina
					if (nroPaginaAttiva) {					
						boolean addNroPagina = !(nroPaginaEscludiAllegatiMaggioreA4 && PdfUtility.isFileMaggioreA4(readerAllegatoDaAggiungereInCoda.getPageSizeWithRotation(indicePaginaAllegatoDaAggiungereInCoda))) || !nroPaginaEscludiAllegatiMaggioreA4;
						// se non è attiva l'esclusione dei formati maggiori di a4
						// o è attiva ma non è un allegato maggiore di a4 applico il nroPagina
						if (addNroPagina) {
							numerazioneUltimaPagina = aggiungiNumeroPagina(impostazioneUnioneFile, copy, nroPaginaNumerazioneDistintaAttiva, nTotDoc, numerazioneUltimaPagina, numeroPagineAllegatoDaAggiungereInCoda, indicePaginaAllegatoDaAggiungereInCoda, importedPageAllegatoDaAggiungereInCoda, pageSizeWithRotationAllegatoDaAggiungereInCoda);
						}
					}
					
					copy.addPage(importedPageAllegatoDaAggiungereInCoda);
				}
				// con questo metodo faccio il flush del contenuto e libero il rader
				copy.freeReader(readerAllegatoDaAggiungereInCoda);
				// Chiudo il reader
				readerAllegatoDaAggiungereInCoda.close();			
			}
		}
		
		return numerazioneUltimaPagina;
		
	}

	/**
	 * @param impostazioneUnioneFile
	 * @param copy
	 * @param infoAllegatoMaxLenNomeFileAllegato
	 * @param counterAllegati
	 * @param fileDaFirmareBean
	 * @param indicePagina
	 * @param importedPage
	 * @param pageSizeWithRotation
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void aggiungiIntestazioneAllegato(ImpostazioniUnioneFileBean impostazioneUnioneFile, PdfCopy copy, 
			FileDaFirmareBean fileDaFirmareBean,
			int indicePagina, PdfImportedPage importedPage, Rectangle pageSizeWithRotation)
			throws NumberFormatException, IOException {
		
		// recupero le impostazioni di unione relative all'intestazione degli allegati
		int infoAllegatoMaxLenNomeFileAllegato = 100;
		
		String infoAllegatoTesto = getInfoAllegatoTesto(fileDaFirmareBean, impostazioneUnioneFile, infoAllegatoMaxLenNomeFileAllegato);
		
		String infoAllegatoMargine = "";
		String infoAllegatoOrientamento = "";
		String infoAllegatoPagine = "";
		Font infoAllegatoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);					
		infoAllegatoMaxLenNomeFileAllegato = impostazioneUnioneFile.getInfoAllegatoMaxLenNomeFileAllegato();
		infoAllegatoMargine = impostazioneUnioneFile.getInfoAllegatoMargine();
		infoAllegatoOrientamento = impostazioneUnioneFile.getInfoAllegatoOrientamento();
		infoAllegatoPagine = impostazioneUnioneFile.getInfoAllegatoPagine();		
		if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFontSize())) {
			infoAllegatoFont.setSize(Float.parseFloat(impostazioneUnioneFile.getInfoAllegatoFontSize()));
		}
		if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFont())) {
			infoAllegatoFont.setFamily(impostazioneUnioneFile.getInfoAllegatoFont());
		}
		if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoStyle())) {
			infoAllegatoFont.setStyle(impostazioneUnioneFile.getInfoAllegatoStyle().toLowerCase());
		}
		if (StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoTextColor())) {
			setFontColor(infoAllegatoFont, impostazioneUnioneFile.getInfoAllegatoTextColor());
		}
		
		if (infoAllegatoPagine.equalsIgnoreCase("prima")) {
			// aggiungo l'intestazione solo alla prima pagina
			if (indicePagina == 1) {
				PdfUtility.addTesto(copy, infoAllegatoTesto, infoAllegatoFont, importedPage, infoAllegatoMargine, infoAllegatoOrientamento, pageSizeWithRotation);
			}
		} else {
			PdfUtility.addTesto(copy, infoAllegatoTesto, infoAllegatoFont, importedPage, infoAllegatoMargine, infoAllegatoOrientamento, pageSizeWithRotation);
		}
	}
	
	public Integer aggiungiNumeroPagina(ImpostazioniUnioneFileBean impostazioneUnioneFile, PdfCopy copy,
			boolean nroPaginaNumerazioneDistintaAttiva, int nTotDoc,
			Integer numerazioneUltimaPagina, int numeroPagine, int indicePagina, PdfImportedPage importedPage, Rectangle pageSizeWithRotation)
			throws IOException {
		String nroPaginaPosizione = "";
		Font nroPaginaFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

		nroPaginaPosizione = impostazioneUnioneFile.getNroPaginaPosizione();
		String nroPaginaFontFamily = impostazioneUnioneFile.getNroPaginaFont();
		String nroPaginaFontSize = impostazioneUnioneFile.getNroPaginaFontSize();
		String nroPaginaFontStyle = impostazioneUnioneFile.getNroPaginaStyle();
		String nroPaginaFontColor = impostazioneUnioneFile.getNroPaginaTextColor();
		if (StringUtils.isNotBlank(nroPaginaFontSize)) {
			nroPaginaFont.setSize(Float.parseFloat(nroPaginaFontSize));
		}
		if (StringUtils.isNotBlank(nroPaginaFontFamily)) {
			nroPaginaFont.setFamily(nroPaginaFontFamily);
		}
		if (StringUtils.isNotBlank(nroPaginaFontStyle)) {
			nroPaginaFont.setStyle(nroPaginaFontStyle.toLowerCase());
		}
		if (StringUtils.isNotBlank(nroPaginaFontColor)) {
			setFontColor(nroPaginaFont, nroPaginaFontColor);
		}

		// definisco il formato del numero pagina per ogni pagina
		String nroPaginaTesto = "";
		if (nroPaginaNumerazioneDistintaAttiva) {
			// se è attiva la numerazione distinta, ad ogni file riparto da pagina 1
			if (StringUtils.isNotBlank(impostazioneUnioneFile.getNroPaginaFormato())) {
				nroPaginaTesto = impostazioneUnioneFile.getNroPaginaFormato().replace("$nroPagina$", indicePagina + "");
				nroPaginaTesto = nroPaginaTesto.replace("$totPagine$", numeroPagine + "");
			}
		} else {
			// la numerazione è unica per tutto il file unione
			if (StringUtils.isNotBlank(impostazioneUnioneFile.getNroPaginaFormato())) {
				nroPaginaTesto = impostazioneUnioneFile.getNroPaginaFormato().replace("$nroPagina$", ++numerazioneUltimaPagina + "");
				nroPaginaTesto = nroPaginaTesto.replace("$totPagine$", nTotDoc + "");
			}
		}
		PdfUtility.addTesto(copy, nroPaginaTesto, nroPaginaFont, importedPage, nroPaginaPosizione, "orizzontale",
				pageSizeWithRotation);
		
		return numerazioneUltimaPagina;
	}
	
	private String getInfoAllegatoTesto(FileDaFirmareBean fileDaFirmareBean, ImpostazioniUnioneFileBean impostazioneUnioneFile,
			int infoAllegatoMaxLenNomeFileAllegato) {
		
		String infoAllegatoTesto = "";
		// se devo aggiungere l'intestazione calcolo l'intestazione in base al formato desiderato
			if(StringUtils.isNotBlank(impostazioneUnioneFile.getInfoAllegatoFormato())) {		
				infoAllegatoTesto = impostazioneUnioneFile.getInfoAllegatoFormato().replace("$nroAllegato$", fileDaFirmareBean.getNroProgAllegato() + "");
				String nomeFileAllegato = fileDaFirmareBean.getNomeFile();
				if(nomeFileAllegato != null) {
					if (nomeFileAllegato.length()>infoAllegatoMaxLenNomeFileAllegato){
						nomeFileAllegato = nomeFileAllegato.substring(0,infoAllegatoMaxLenNomeFileAllegato);
					}
					nomeFileAllegato = nomeFileAllegato.replaceAll("_", " ");
				} else {
					nomeFileAllegato = "";
				}
				infoAllegatoTesto = infoAllegatoTesto.replace("$nomeFileAllegato$", nomeFileAllegato);
			}
		
		
		return infoAllegatoTesto;

	}

	protected int getTotalNumberPages(List<FileDaFirmareBean> lListFile, ImpostazioniUnioneFileBean impostazioneUnioneFile) throws IOException {
		PdfReader reader;
		boolean nroPaginaEscludiAllegatiUnitiInFondo = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInFondo()!=null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInFondo() ? true : false;
		boolean nroPaginaEscludiFoglioFirmeGrafiche = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiFoglioFirmeGrafiche()!=null && impostazioneUnioneFile.getNroPaginaEscludiFoglioFirmeGrafiche() ? true : false;
		boolean nroPaginaEscludiListaAllegatiSeparati = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiListaAllegatiSeparati() !=null && impostazioneUnioneFile.getNroPaginaEscludiListaAllegatiSeparati() ? true : false;
		boolean nroPaginaEscludiAppendice = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAppendice() !=null && impostazioneUnioneFile.getNroPaginaEscludiAppendice() ? true : false;
		boolean nroPaginaEscludiAllegatiUnitiInMezzo = impostazioneUnioneFile != null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInMezzo() !=null && impostazioneUnioneFile.getNroPaginaEscludiAllegatiUnitiInMezzo() ? true : false;		
		boolean posizioneAllegatiUnitiAttiva = impostazioneUnioneFile != null && impostazioneUnioneFile.getPosizioneAllegatiUnitiAttiva()!=null && impostazioneUnioneFile.getPosizioneAllegatiUnitiAttiva() ? true : false;
		int nTotDoc=0;
		
		for (FileDaFirmareBean fileDaFirmareBean : lListFile) {
			// Leggo il file
			reader = new PdfReader(fileDaFirmareBean.getFile().getAbsolutePath());
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// le aggiungo al numero di pagine totali
			if(fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.ALLEGATO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) &&
					((posizioneAllegatiUnitiAttiva && !nroPaginaEscludiAllegatiUnitiInMezzo) || (!posizioneAllegatiUnitiAttiva && !nroPaginaEscludiAllegatiUnitiInFondo))) {
				nTotDoc += n;
			}else if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_FIRME_GRAFICHE.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) && !nroPaginaEscludiFoglioFirmeGrafiche) {
				nTotDoc += n;
			}else if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_ALLEGATI_SEPARATI.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) && !nroPaginaEscludiListaAllegatiSeparati) {
				nTotDoc += n;
			}else if (fileDaFirmareBean.getTipoFileUnione()!=null && TipoFileUnione.MODELLO_APPENDICE.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) && !nroPaginaEscludiAppendice) {
				nTotDoc += n;
			}else if(fileDaFirmareBean.getTipoFileUnione()!=null && 
					(TipoFileUnione.MODELLO_DISPOSITIVO.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) || 
					 TipoFileUnione.MODELLO_COPERTINA.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()) ||
					 TipoFileUnione.MODELLO_COPERTINA_FINALE.equalsIgnoreCase(fileDaFirmareBean.getTipoFileUnione()))
					 ) {
				nTotDoc += n;
			}
				
			//chiudo il reader di questo file
			reader.close();
		}
		return nTotDoc;
	}

	protected void setFontColor(Font font, String fontColor) {
		switch (fontColor.toUpperCase()) {
		case "WHITE":
			font.setColor(BaseColor.WHITE);				
			break;
		case "LIGHT_GRAY":
			font.setColor(BaseColor.LIGHT_GRAY);				
			break;
		case "GRAY":
			font.setColor(BaseColor.GRAY);				
			break;
		case "DARK_GRAY":
			font.setColor(BaseColor.DARK_GRAY);
			break;
		case "GREEN":
			font.setColor(BaseColor.GREEN);
			break;
		case "YELLOW":
			font.setColor(BaseColor.YELLOW);
			break;
		case "ORANGE":
			font.setColor(BaseColor.ORANGE);
			break;
		case "PINK":
			font.setColor(BaseColor.PINK);
			break;
		case "RED":
			font.setColor(BaseColor.RED);
			break;
		case "MAGENTA":
			font.setColor(BaseColor.MAGENTA);
			break;
		case "CYAN":
			font.setColor(BaseColor.CYAN);
			break;
		case "BLUE":
			font.setColor(BaseColor.BLUE);
			break;
		case "BLACK ":
		default:
			font.setColor(BaseColor.BLACK);
			break;
		}
	}

	@Deprecated
	public File unioneFilePdfOld(List<InputStream> lListInputStream) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
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
			// Chiudo il reader
			reader.close();
			// Chiudo gli stream dei file in input
			try { lInputStream.close(); } catch(Exception e) {}
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();		
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdfOld executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public File unioneFilePdfSenzaIntestazioniNroPagina(List<File> lListFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = File.createTempFile("pdf", ".pdf");
		FileOutputStream out = new FileOutputStream(lFileUnione);
		Document document = new Document();
		// Istanzio una copia nell'output
		PdfCopy copy = new PdfCopy(document, out);
		// Apro per la modifica il nuovo documento
		document.open();
		// Istanzio un nuovo reader che ci servirà per leggere i singoli file
		PdfReader reader;
		// Per ogni file passato
		for (File lFile : lListFile) {
			// Leggo il file
			reader = new PdfReader(lFile.getAbsolutePath());
			// Prendo il numero di pagine
			int n = reader.getNumberOfPages();
			// e per ogni pagina
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			// con questo metodo faccio il flush del contenuto e libero il rader
			copy.freeReader(reader);
			// Chiudo il reader
			reader.close();
		}
		// Chiudo la copia
		copy.close();
		// Chiudo il documento
		document.close();
		// Chiudo lo stream del file in output
		out.close();
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("unioneFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public File mergeFilePdf(List<File> lListFile) throws Exception {
//		long start = new Date().getTime();
		File lFileUnione = new MergeDocument().mergeDocuments(lListFile.toArray(new File[lListFile.size()]));
//		long end = new Date().getTime();
//		long delay = end - start;
//		logger.debug("mergeFilePdf executed in " + delay + " ms");
		return lFileUnione;
	}
	
	public CompilaModelloNuovaPropostaAtto2CompletaBean compilazioneAutomaticaModelloPdf(CompilaModelloNuovaPropostaAtto2CompletaBean modelloBean) throws Exception {
		String templateValues = getDatiXGenDaModello(modelloBean.getDettaglioBean(), modelloBean.getNomeModello(), null);
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
	
	public CompilaListaModelliNuovaPropostaAtto2CompletaBean compilazioneAutomaticaListaModelliPdf(CompilaListaModelliNuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(bean.getListaRecordModelli() != null) {
			logger.debug("#######INIZIO for in compilazioneAutomaticaListaModelliPdf######");
			for(int i = 0; i < bean.getListaRecordModelli().size(); i++) {
				CompilaModelloAttivitaBean modelloBean = bean.getListaRecordModelli().get(i);
				logger.debug("#######INIZIO getDatiXGenDaModello in compilazioneAutomaticaListaModelliPdf######");
				String templateValues = getDatiXGenDaModello(bean.getDettaglioBean(), modelloBean.getNomeModello(), null);				
				logger.debug("#######FINE getDatiXGenDaModello in compilazioneAutomaticaListaModelliPdf######");
				if(StringUtils.isNotBlank(modelloBean.getIdModello())) {
					logger.debug("#######INIZIO modelloBean.getIdModello() IS NOT BLANK in compilazioneAutomaticaListaModelliPdf######");
					FileDaFirmareBean lFileDaFirmareBean = ModelliUtil.fillTemplate(bean.getProcessId(), modelloBean.getIdModello(), templateValues, true, getSession());			
					modelloBean.setUriFileGenerato(lFileDaFirmareBean.getUri());
					modelloBean.setInfoFileGenerato(lFileDaFirmareBean.getInfoFile());			
					logger.debug("#######FINE modelloBean.getIdModello() IS NOT BLANK in compilazioneAutomaticaListaModelliPdf######");
				} else {
					logger.debug("#######INIZIO modelloBean.getIdModello() IS BLANK in compilazioneAutomaticaListaModelliPdf######");
					logger.debug("#######INIZIO fillTemplateAndConvertToPdf in compilazioneAutomaticaListaModelliPdf######");
					File fileModelloPdf = ModelliUtil.fillTemplateAndConvertToPdf(bean.getProcessId(), modelloBean.getUri(), modelloBean.getTipoModello(), templateValues, getSession());
					logger.debug("#######FINE fillTemplateAndConvertToPdf in compilazioneAutomaticaListaModelliPdf######");
					logger.debug("#######INIZIO getStorage().store in compilazioneAutomaticaListaModelliPdf######");
					String storageUri = StorageImplementation.getStorage().store(fileModelloPdf);
					logger.debug("#######FINE getStorage().store in compilazioneAutomaticaListaModelliPdf######");
					modelloBean.setUriFileGenerato(storageUri);
					MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
					infoFile.setMimetype("application/pdf");
					logger.debug("#######INIZIO recuperaNumeroPagine in compilazioneAutomaticaListaModelliPdf######");
					infoFile.setNumPaginePdf(PdfUtil.recuperaNumeroPagine(fileModelloPdf));
					logger.debug("#######FINE recuperaNumeroPagine in compilazioneAutomaticaListaModelliPdf######");
					infoFile.setDaScansione(false);
					infoFile.setFirmato(false);
					infoFile.setFirmaValida(false);
					infoFile.setBytes(fileModelloPdf.length());
					infoFile.setCorrectFileName(modelloBean.getNomeFile());
					logger.debug("#######INIZIO getRealFile in compilazioneAutomaticaListaModelliPdf######");
					File realFile = StorageImplementation.getStorage().getRealFile(modelloBean.getUri());
					logger.debug("#######FINE getRealFile in compilazioneAutomaticaListaModelliPdf######");
					InfoFileUtility lInfoFileUtility = new InfoFileUtility();
					logger.debug("#######INIZIO calcolaImpronta in compilazioneAutomaticaListaModelliPdf######");
					infoFile.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), modelloBean.getNomeFile()));
					logger.debug("#######FINE calcolaImpronta in compilazioneAutomaticaListaModelliPdf######");
					modelloBean.setInfoFileGenerato(infoFile);	
					logger.debug("#######FINE modelloBean.getIdModello() IS BLANK in compilazioneAutomaticaListaModelliPdf######");
				}	
				if(modelloBean.getFlgFirmaAuto() != null && modelloBean.getFlgFirmaAuto()) {					
					logger.debug("#######INIZIO modelloBean.getFlgFirmaAuto() != null && modelloBean.getFlgFirmaAuto() in compilazioneAutomaticaListaModelliPdf######");
					// Firma automatica	con i seguenti parametri		
					String providerFirmaAuto = modelloBean.getProviderFirmaAuto();
					String userIdFirmaAuto = modelloBean.getUserIdFirmaAuto();
					String firmaInDelegaFirmaAuto = modelloBean.getFirmaInDelegaFirmaAuto();
					String passwordFirmaAuto = modelloBean.getPasswordFirmaAuto();					
					FirmaHsmBean lFirmaHsmBean = new FirmaHsmBean();					
					List<FileDaFirmare> listaFileDaFirmare = new ArrayList<>();
					// Settare i parametri del file da firmare
					FileDaFirmare fileDaFirmare = lFirmaHsmBean.new FileDaFirmare();
					fileDaFirmare.setUri(modelloBean.getUriFileGenerato());
					fileDaFirmare.setInfoFile(modelloBean.getInfoFileGenerato());
					String nomeFile = modelloBean.getNomeFile();
					boolean estensioneforzata = false;
					if (modelloBean.getInfoFileGenerato() != null && "application/pdf".equalsIgnoreCase(modelloBean.getInfoFileGenerato().getMimetype()) && modelloBean.getNomeFile() != null && !modelloBean.getNomeFile().toLowerCase(getLocale()).endsWith(".pdf")) {
						estensioneforzata = true;
						nomeFile += ".pdf";
					}
					fileDaFirmare.setNomeFile(nomeFile);					
					listaFileDaFirmare.add(fileDaFirmare);				
					lFirmaHsmBean.setListaFileDaFirmare(listaFileDaFirmare);
					String tipoMarca = "";
					if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "HSM_MARCA_SIGNATURE")) {
						tipoMarca = "HSM";
					} else {
						tipoMarca = ParametriDBUtil.getParametroDB(getSession(), "HSM_MARCA_SIGNATURE");
					}
					lFirmaHsmBean.setFileDaMarcare(tipoMarca);
					// Setto firmatario ed eventuale delegante
					if (StringUtils.isNotBlank(firmaInDelegaFirmaAuto)) {
						lFirmaHsmBean.setUsername(firmaInDelegaFirmaAuto);
						lFirmaHsmBean.setUsernameDelegante(userIdFirmaAuto);
					} else {
						lFirmaHsmBean.setUsername(userIdFirmaAuto);
						lFirmaHsmBean.setUsernameDelegante("");
					}
					lFirmaHsmBean.setPassword(passwordFirmaAuto);
					// Parametri per eventuale firma Medas
					// lFirmaHsmBean.setCodiceOtp(codiceOtp);
					// lFirmaHsmBean.setCertId(certId);
					// lFirmaHsmBean.setPotereDiFirma(potereDiFirma);
					// lFirmaHsmBean.setParametriHSMFromGui(parametriHSMFromGui);
					lFirmaHsmBean.setProviderHsmFromPreference(providerFirmaAuto);
					lFirmaHsmBean.setSkipControlloCoerenzaCertificatoFirma(true);					
					String parametriRettangoloFirmaJson =  ParametriDBUtil.getParametroDB(getSession(), "POSITION_GRAPHIC_SIGNATURE_IN_PADES");
					if ((parametriRettangoloFirmaJson != null) && (!"".equalsIgnoreCase(parametriRettangoloFirmaJson.trim()))) {
						RettangoloFirmaPadesBean rettangoloFirmaPades = new Gson().fromJson(parametriRettangoloFirmaJson, RettangoloFirmaPadesBean.class);
						lFirmaHsmBean.setRettangoloFirmaPades(rettangoloFirmaPades);
					}					
					lFirmaHsmBean.setParametriHSMFromGui(true);					
					FirmaHsmDataSource dataSource = new FirmaHsmDataSource();
					dataSource.setSession(getSession());					
					String hsmTipoFirmaAtti = ParametriDBUtil.getParametroDB(getSession(), "HSM_TIPO_FIRMA_ATTI");
					// Inserisco il parametro di forzatura dato dagli atti
					Map<String, String> extraParams = new LinkedHashMap<String, String>();
					extraParams.put("HSM_TIPO_FIRMA_FORZATO", hsmTipoFirmaAtti);
					dataSource.setExtraparams(extraParams);	
					// Chiamata del metodo del datasource
					logger.debug("#######INIZIO dataSource.firmaHsmMultiplaFileGeneratodaModello in compilazioneAutomaticaListaModelliPdf######");
					FirmaHsmBean result = dataSource.firmaHsmMultiplaFileGeneratodaModello(lFirmaHsmBean);							
					logger.debug("#######FINE dataSource.firmaHsmMultiplaFileGeneratodaModello in compilazioneAutomaticaListaModelliPdf######");
					if (result.getFileFirmati() != null && !result.getFileFirmati().isEmpty()) {
						FileDaFirmare fileFirmato = result.getFileFirmati().get(0);
						modelloBean.setNomeFile(fileFirmato.getNomeFile());
						if (estensioneforzata && fileFirmato.getNomeFile() != null && fileFirmato.getNomeFile().length() > 4) {
							modelloBean.setNomeFile(fileFirmato.getNomeFile().substring(0, fileFirmato.getNomeFile().length() - 4));
						}
						modelloBean.setUriFileGenerato(fileFirmato.getUri());
						modelloBean.setInfoFileGenerato(fileFirmato.getInfoFile());
					} else {
						// Nessun file firmato, in questo caso è stato deciso di retituire il file non firmato
						if(StringUtils.isNotBlank(result.getErrorMessage())) {
							logger.error("Errore nella firma del file generato da modello: " + result.getErrorMessage());
						} else {
							logger.error("Errore nella firma del file generato da modello");
						}
//						throw new StoreException("Errore nella firma file");
					}
					logger.debug("#######FINE modelloBean.getFlgFirmaAuto() != null && modelloBean.getFlgFirmaAuto() in compilazioneAutomaticaListaModelliPdf######");
				}
			}			
			logger.debug("#######FINE for in compilazioneAutomaticaListaModelliPdf######");
		}
		return bean;
	}
	
	public FileDaFirmareBean getInfoOggLiquidazione(NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		RecuperoFile lRecuperoFile = new RecuperoFile();
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(ParametriDBUtil.getParametroDB(getSession(), "URI_INFO_OGG_LIQUIDAZIONE"));
		FileExtractedOut out = lRecuperoFile.extractfile(getLocale(), loginBean, lFileExtractedIn);
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		String uri = StorageImplementation.getStorage().store(out.getExtracted());
		File lFile = StorageImplementation.getStorage().getRealFile(uri);				
		String nomeFile = "INFO_OGG_LIQUIDAZIONE.pdf";
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setNomeFile(nomeFile);
		lFileDaFirmareBean.setUri(uri);
		lFileDaFirmareBean.setInfoFile(lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null));
		return lFileDaFirmareBean;			
	}
	
	private void aggiungiPrimario(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setIdFile("primario" + pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimario());
		lFileDaFirmareBean.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(pNuovaPropostaAtto2CompletaBean.getUriFilePrimario());		
		lFileDaFirmareBean.setIsFilePrincipaleAtto(true);
		if(listaFile == null) {
			listaFile = new ArrayList<FileDaFirmareBean>();
		}
		listaFile.add(lFileDaFirmareBean);		
	}

	private void aggiungiPrimarioOmissis(ArrayList<FileDaFirmareBean> listaFile, NuovaPropostaAtto2CompletaBean pNuovaPropostaAtto2CompletaBean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBeanOmissis = new FileDaFirmareBean();
		lFileDaFirmareBeanOmissis.setIdFile("primarioOmissis" + pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setInfoFile(pNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setNomeFile(pNuovaPropostaAtto2CompletaBean.getNomeFilePrimarioOmissis());
		lFileDaFirmareBeanOmissis.setUri(pNuovaPropostaAtto2CompletaBean.getUriFilePrimarioOmissis());
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
			lFileAllegatoBean.setNroPagDispositivoUnione(lAllegatoProtocolloBean.getNroPagFileUnione());
			lFileAllegatoBean.setTipoFileUnione(TipoFileUnione.ALLEGATO);
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
			lFileAllegatoOmissisBean.setNroPagDispositivoUnione(lAllegatoProtocolloBean.getNroPagFileUnioneOmissis());
			lFileAllegatoOmissisBean.setTipoFileUnione(TipoFileUnione.ALLEGATO);
			if(listaFile == null) {
				listaFile = new ArrayList<FileDaFirmareBean>();
			}
			listaFile.add(lFileAllegatoOmissisBean);		
		}
	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaFileFirmati(TaskNuovaPropostaAtto2CompletaFileFirmatiBean pTaskNuovaPropostaAtto2CompletaFileFirmatiBean) throws Exception {
		NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getProtocolloOriginale();
		if (pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati() != null && pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati().getFiles() != null) {
			boolean firmaNonValida = false;
			for (FileDaFirmareBean lFileDaFirmareBean : pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati().getFiles()) {
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
					}  else if (idFile.startsWith("fileGenerato")) {
						logger.error("La firma del file generato " + lFileDaFirmareBean.getNomeFile() + " risulta essere non valida: "
								+ lFileDaFirmareBean.getUri());
					}
					firmaNonValida = true;
				}
				if (idFile.startsWith("primarioOmissis")) {
					aggiornaPrimarioOmissisFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("primario")) {
					aggiornaPrimarioFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegatoOmissis")) {
					aggiornaAllegatoOmissisFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("allegato")) {
					aggiornaAllegatoFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} else if (idFile.startsWith("fileGenerato")) {
					aggiornaFileGeneratoFirmato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
				} 
			}
			if (firmaNonValida) {
				throw new StoreException("La firma di uno o più file risulta essere non valida");
			}
		}
		return lNuovaPropostaAtto2CompletaBean;
	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaFile(TaskNuovaPropostaAtto2CompletaFileFirmatiBean pTaskNuovaPropostaAtto2CompletaFileFirmatiBean) throws Exception {
		NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getProtocolloOriginale();
		for (FileDaFirmareBean lFileDaFirmareBean : pTaskNuovaPropostaAtto2CompletaFileFirmatiBean.getFileFirmati().getFiles()){	
			String idFile = lFileDaFirmareBean.getIdFile();
			if (idFile.startsWith("primarioOmissis")) {
				aggiornaPrimarioOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("primario")) {
				aggiornaPrimario(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegatoOmissis")) {
				aggiornaAllegatoOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("allegato")) {
				aggiornaAllegato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
			} else if (idFile.startsWith("fileGenerato")) {
				aggiornaFileGenerato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
			} 
		}		
		return lNuovaPropostaAtto2CompletaBean;
	}
	
	private void aggiornaPrimarioFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		if(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getIdDocPrimario()) && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario() != null && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimario()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, lNuovaPropostaAtto2CompletaBean);			
			getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		if (lFileDaFirmareBean.getInfoFile() != null) {
			InfoFirmaMarca lInfoFirmaMarca = lFileDaFirmareBean.getInfoFile().getInfoFirmaMarca() != null ? lFileDaFirmareBean.getInfoFile().getInfoFirmaMarca() : new InfoFirmaMarca();
			lInfoFirmaMarca.setBustaCrittograficaInComplPassoIter(true);
			lFileDaFirmareBean.getInfoFile().setInfoFirmaMarca(lInfoFirmaMarca);
		}
		aggiornaPrimario(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaPrimarioOmissisFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		if(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getIdDocPrimarioOmissis()) && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis() != null && lNuovaPropostaAtto2CompletaBean.getIsChangedFilePrimarioOmissis()) {
			// Prima salvo la versione pre firma se è stata modificata
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, lNuovaPropostaAtto2CompletaBean);			
			getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		if (lFileDaFirmareBeanOmissis.getInfoFile() != null) {
			InfoFirmaMarca lInfoFirmaMarca = lFileDaFirmareBeanOmissis.getInfoFile().getInfoFirmaMarca() != null ? lFileDaFirmareBeanOmissis.getInfoFile().getInfoFirmaMarca() : new InfoFirmaMarca();
			lInfoFirmaMarca.setBustaCrittograficaInComplPassoIter(true);
			lFileDaFirmareBeanOmissis.getInfoFile().setInfoFirmaMarca(lInfoFirmaMarca);
		}
		aggiornaPrimarioOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaAllegatoFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean);
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileAllegato() != null && lAllegatoProtocolloBean.getUriFileAllegato().equals(uriFileOriginale)) {
				if(lAllegatoProtocolloBean.getIdDocAllegato() != null &&  lAllegatoProtocolloBean.getIsChanged() != null && lAllegatoProtocolloBean.getIsChanged()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegato(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
	}
	
	private void aggiornaAllegatoOmissisFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBeanOmissis) throws Exception {
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean);
		String uriFileOriginaleOmissis = lFileDaFirmareBeanOmissis.getIdFile().substring("allegatoOmissis".length());
		for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()) {
			if (lAllegatoProtocolloBean.getUriFileOmissis() != null && lAllegatoProtocolloBean.getUriFileOmissis().equals(uriFileOriginaleOmissis)) {
				if(lAllegatoProtocolloBean.getIdDocOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis() != null && lAllegatoProtocolloBean.getIsChangedOmissis()) {
					// Prima salvo la versione pre firma se è stata modificata
					lProtocolloDataSource.aggiornaFileAllegatoOmissis(lAllegatoProtocolloBean);
				}				
				break;
			}
		}
		aggiornaAllegatoOmissis(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBeanOmissis);
	}
	
	private void aggiornaFileGeneratoFirmato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		aggiornaFileGenerato(lNuovaPropostaAtto2CompletaBean, lFileDaFirmareBean);
	}	
	
	private void aggiornaPrimario(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2CompletaBean.setUriFilePrimario(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2CompletaBean.setNomeFilePrimario(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2CompletaBean.setRemoteUriFilePrimario(false);
		String precImpronta = lNuovaPropostaAtto2CompletaBean.getInfoFilePrimario() != null ? lNuovaPropostaAtto2CompletaBean.getInfoFilePrimario().getImpronta() : null;
		lNuovaPropostaAtto2CompletaBean.setInfoFilePrimario(lFileDaFirmareBean.getInfoFile());
		if (precImpronta == null || !precImpronta.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimario(true);
		}
	}
	
	private void aggiornaPrimarioOmissis(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) {
		lNuovaPropostaAtto2CompletaBean.setUriFilePrimarioOmissis(lFileDaFirmareBean.getUri());
		lNuovaPropostaAtto2CompletaBean.setNomeFilePrimarioOmissis(lFileDaFirmareBean.getNomeFile());
		lNuovaPropostaAtto2CompletaBean.setRemoteUriFilePrimarioOmissis(false);
		String precImprontaOmissis = lNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis() != null ? lNuovaPropostaAtto2CompletaBean.getInfoFilePrimarioOmissis().getImpronta() : null;
		lNuovaPropostaAtto2CompletaBean.setInfoFilePrimarioOmissis(lFileDaFirmareBean.getInfoFile());
		if (precImprontaOmissis == null || !precImprontaOmissis.equals(lFileDaFirmareBean.getInfoFile().getImpronta())) {
			lNuovaPropostaAtto2CompletaBean.setIsChangedFilePrimarioOmissis(true);
		}
	}

	private void aggiornaAllegato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegato".length());
		if(lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()){
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
	
	private void aggiornaAllegatoOmissis(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean,	FileDaFirmareBean lFileDaFirmareBean) {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("allegatoOmissis".length());
		if(lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()){
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
	
	private void aggiornaFileGenerato(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, FileDaFirmareBean lFileDaFirmareBean) throws Exception {
		String uriFileOriginale = lFileDaFirmareBean.getIdFile().substring("fileGenerato".length());		
		if(lNuovaPropostaAtto2CompletaBean.getListaAllegati() != null) {
			for (AllegatoProtocolloBean lAllegatoProtocolloBean : lNuovaPropostaAtto2CompletaBean.getListaAllegati()){
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
	public PaginatorBean<NuovaPropostaAtto2CompletaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		return null;
	}
	
	@Override
	public NuovaPropostaAtto2CompletaBean remove(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		return null;
	};
	
	public boolean isAttivoSIB(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoContabilia(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
	}
	
	public boolean isAttivoSICRA(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoCWOL(NuovaPropostaAtto2CompletaBean bean) {
		if(!isSiglaPropostaAttoXAMC(bean)) {
			return false;
		}
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "CWOL".equalsIgnoreCase(lSistAMC);
	}
	
	private boolean isSiglaPropostaAttoXAMC(NuovaPropostaAtto2CompletaBean bean) {
		String lSiglePropAttiXAMC = ParametriDBUtil.getParametroDB(getSession(), "SIGLE_PROP_ATTI_PER_AMC");
		if(StringUtils.isNotBlank(lSiglePropAttiXAMC)) {
			StringSplitterServer st = new StringSplitterServer(lSiglePropAttiXAMC, ";");
			boolean trovato = false;
    		while(st.hasMoreTokens()) {
    			String sigla = st.nextToken();
    			if(StringUtils.isNotBlank(sigla) && StringUtils.isNotBlank(bean.getSiglaRegProvvisoria()) && 
    					(bean.getSiglaRegProvvisoria().equalsIgnoreCase(sigla) || bean.getSiglaRegProvvisoria().toUpperCase().startsWith(sigla.toUpperCase() + "-"))) {
    				trovato = true;
    			}
    		}
    		if(!trovato) {
    			return false;
    		}
		}
		return true;
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
	
	private static String estraiTestoOmissisDaHtml(String html) {
		if (StringUtils.isNotBlank(html)) {
			html = FreeMarkerModelliUtil.replaceOmissisInHtml(html);
			return Jsoup.parse(html).text().replaceAll("\\<.*?>","");
		} else {
			return html;
		}
	}
	
	public void eseguiProtocollazioneProsa(NuovaPropostaAtto2CompletaBean nuovaPropostaAtto2CompletaBean) throws Exception {
		boolean allegatiErrorMessages = false;
		
		ProsaDocumentoProtocollato inputProsaProtocollaBean = new ProsaDocumentoProtocollato();
	
		File filePrimario = recuperaFile(nuovaPropostaAtto2CompletaBean.getRemoteUriFilePrimario(), nuovaPropostaAtto2CompletaBean.getUriFilePrimario());
		
		byte[] bytes = convertFileToBytes(filePrimario);
		
		inputProsaProtocollaBean.setNomeFileContenuto(filePrimario.getName());
		inputProsaProtocollaBean.setContenuto(bytes);
		
		inputProsaProtocollaBean.setOggetto(nuovaPropostaAtto2CompletaBean.getOggetto());
		inputProsaProtocollaBean.setTipoProtocollo("U");
		

		ProsaMittenteDestinatario mittdest = new ProsaMittenteDestinatario();
		mittdest.setDenominazione(nuovaPropostaAtto2CompletaBean.getDesUfficioProponente());
		mittdest.setCodiceMittenteDestinatario(nuovaPropostaAtto2CompletaBean.getCodUfficioProponente());
		ProsaMittenteDestinatario[] mitt = new ProsaMittenteDestinatario[1];
		mitt[0] = mittdest;

		inputProsaProtocollaBean.setMittentiDestinatari(mitt);		

		ProtocollazioneProsaImpl service = new ProtocollazioneProsaImpl();
		Result<ProsaDocumentoProtocollato> output = service.protocolla(getLocale(), inputProsaProtocollaBean);
		
		if (!output.isOk()) {
			if(output.isTimeout()) {
				logger.error("Errore WSprotocolla Prosa: timeout");
				throw new StoreException("Errore WSprotocolla Prosa: timeout");
			}else if(output.isRispostaNonRicevuta()){
				logger.error("Errore WSprotocolla Prosa: nessuna risposta dal servizio");
				throw new StoreException("Errore WSprotocolla Prosa: nessuna risposta dal servizio");
			}else {
				String errorMessage = "Fallita protocollazione: " + (StringUtils.isNotBlank(output.getMessage()) ? output.getMessage() : "");
				logger.error("Errore WSprotocolla Prosa: " + output.getMessage());
				throw new StoreException(errorMessage);
			}
		}else {
			 
			ProsaDocumentoProtocollato resultBeanProtocollazioneProsa = output.getPayload();
			
			//Dopo aver fatto la protocollazione sul file primario aggiungo gli allegati chiamando il WS di Prosa: inserisciAllegato 
			allegatiErrorMessages = aggiungiAllegatiProtocollazioneProsa(nuovaPropostaAtto2CompletaBean.getListaAllegati(), resultBeanProtocollazioneProsa.getId());
			
			if(StringUtils.isNotBlank(resultBeanProtocollazioneProsa.getNumeroProtocollo())) {
				//Se la protocollazione in Prosa è adnata a buon fine chiamo UpdDocUD
				
				//Converto le date dal formato di prosa al formato di Auriga
				Calendar calendar = GregorianCalendar.getInstance();				
				String date;
				if(resultBeanProtocollazioneProsa.getDataProtocollo()!=null) {
					date = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(resultBeanProtocollazioneProsa.getDataProtocollo().getTime());
				}else {
					date = new SimpleDateFormat(FMT_STD_TIMESTAMP).format(calendar.getTime());
				}
				
				String annoProtocolazioneProsa = String.valueOf(calendar.get(Calendar.YEAR));
				Date dataProtocollazioneProsa = new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(date);
				String numeroProtocolloProsa = resultBeanProtocollazioneProsa.getNumeroProtocollo();
				
				updateDatiProsa(nuovaPropostaAtto2CompletaBean.getIdUd(), numeroProtocolloProsa, dataProtocollazioneProsa, annoProtocolazioneProsa);				
						
			}else {
				throw new StoreException("Fallita Protocollazione");
			}
		}
		
		//Se sono avvenuti errori durante l'inserimento di uno o piu allegati
		if(allegatiErrorMessages) {
			addMessage("Non e' stato possibile aggiungere uno o più allegati al protocollo", "", MessageType.WARNING);
		}
		
	}


	private boolean aggiungiAllegatiProtocollazioneProsa(List<AllegatoProtocolloBean> listaAllegatiPROSA, Long idProtocolloProsa) throws Exception {
		boolean flgErroreInsAllegati = false;
		
		for(AllegatoProtocolloBean allegatoBean : listaAllegatiPROSA) {
			
			ProsaAllegato inputProsaAllegatoBean = new ProsaAllegato();
			
			File fileAllegato = recuperaFile(allegatoBean.getRemoteUri(), allegatoBean.getUriFileAllegato());
			
			byte[] bytes = convertFileToBytes(fileAllegato);
			
			inputProsaAllegatoBean.setContenuto(bytes);
			inputProsaAllegatoBean.setNomeFile(fileAllegato.getName());
			inputProsaAllegatoBean.setIdProfilo(idProtocolloProsa);
			
			ProtocollazioneProsaImpl service = new ProtocollazioneProsaImpl();
			Result<ProsaAllegato> output = service.inserisciallegato(getLocale(), inputProsaAllegatoBean);
			
			if (!output.isOk()) {
				String messageError = "";

				if (output.isTimeout()) {
					messageError = "timeout";
				} else if (output.isRispostaNonRicevuta()) {
					messageError = "nessuna risposta dal servizio";
				} else {
					messageError = (StringUtils.isNotBlank(output.getMessage()) ? output.getMessage() : "");
				}
				logger.error("Errore WSaggiungiAllegato Prosa sull'allegato chiamato: " + fileAllegato.getName() + ": " + messageError);
				flgErroreInsAllegati = true;
			}		
				
		}
		
		return flgErroreInsAllegati;
	}
		
	void updateDatiProsa(String idUd, String numeroProtocolloProsa, Date dataProtocollazioneProsa, String annoProtocolazioneProsa) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();	
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank((CharSequence) idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtipotargetin("D");
		input.setIduddocin(new BigDecimal(idUd));

		CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
		creaModDocumentoInBean.setAppendRegistroEmergenza("1");
		
		List<RegistroEmergenza> lListRegistrazioniDaDare = new ArrayList<RegistroEmergenza>();

		RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
		lRegistroEmergenza.setFisso("PG");
		lRegistroEmergenza.setRegistro(null);
		lRegistroEmergenza.setAnno(annoProtocolazioneProsa);
		lRegistroEmergenza.setNro(numeroProtocolloProsa);
		lRegistroEmergenza.setDataRegistrazione(dataProtocollazioneProsa);
		lListRegistrazioniDaDare.add(lRegistroEmergenza);
		
		creaModDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaDare);
		
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));

		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> outputStoreBean = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if (outputStoreBean.isInError()) {
			throw new StoreException("Fallita memorizzazione degli estremi di protocollo ricevuti da Prosa: " + outputStoreBean.getDefaultMessage());
		}
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
	
	byte[] convertFileToBytes(File file) throws Exception {		
		FileInputStream is = new FileInputStream(file);
		long length = file.length();
		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		
		return bytes;
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
	
	public NuovaPropostaAtto2CompletaBean modificaCodAttoCMTO(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtipotargetin("D");
		input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
		
		CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
		creaModDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
		
		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();		
		
		String tipoAffidamento = bean.getTipoAffidamento() != null ? bean.getTipoAffidamento() : "";				
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "TIPO_AFFIDAMENTO_Doc", tipoAffidamento);
		
		String materiaTipoAtto = bean.getMateriaTipoAtto() != null ? bean.getMateriaTipoAtto() : "";
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "MATERIA_NATURA_ATTO_Doc", materiaTipoAtto);
		
		String flgLLPP = bean.getFlgLLPP() != null ? bean.getFlgLLPP() : "";
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_LLPP_Doc", flgLLPP);
		String annoProgettoLLPP = "";	
		if (_FLG_SI.equals(flgLLPP)) {
			annoProgettoLLPP = bean.getAnnoProgettoLLPP() != null ? bean.getAnnoProgettoLLPP() : "";	
		}
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_LLPP_Doc", annoProgettoLLPP);	
		String numProgettoLLPP = "";	
		if (_FLG_SI.equals(flgLLPP)) {	
			numProgettoLLPP = bean.getNumProgettoLLPP() != null ? bean.getNumProgettoLLPP() : "";	
		}
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_LLPP_Doc", numProgettoLLPP);	
		
		String flgBeniServizi = bean.getFlgBeniServizi() != null ? bean.getFlgBeniServizi() : "";	
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "FLG_BENI_SERVIZI_Doc", flgBeniServizi);
		String annoProgettoBeniServizi = "";	
		if (_FLG_SI.equals(flgBeniServizi)) {
			annoProgettoBeniServizi = bean.getAnnoProgettoBeniServizi() != null ? bean.getAnnoProgettoBeniServizi() : "";
		}
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "ANNO_PROGETTO_BENI_SERVIZI_Doc", annoProgettoBeniServizi);	
		String numProgettoBeniServizi = "";	
		if (_FLG_SI.equals(flgBeniServizi)) {
			numProgettoBeniServizi = bean.getNumProgettoBeniServizi() != null ? bean.getNumProgettoBeniServizi() : "";	
		}
		putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "NRO_PROGETTO_BENI_SERVIZI_Doc", numProgettoBeniServizi);
		
		creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean modificaDatiPubblicazioneNotifiche(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		// devo passare solo gli attributi cablati che si vedono a maschera
		HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			salvaAttributiCustomPubblicazioneNotifiche(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, false);
						
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				String token = loginBean.getToken();
				String idUserLavoro = loginBean.getIdUserLavoro();
				
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
							
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
				creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				return bean;
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return bean;
	}

	public NuovaPropostaAtto2CompletaBean modificaOpereADSP(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		// devo passare solo gli attributi cablati che si vedono a maschera
		HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			salvaAttributiCustomOpereADSP(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, false);
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				String token = loginBean.getToken();
				String idUserLavoro = loginBean.getIdUserLavoro();
				
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
							
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
				creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				return bean;
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return bean;
	}
	
	
	
	public NuovaPropostaAtto2CompletaBean modificaAltriRespRegTecnicaCompleta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		// devo passare solo gli attributi cablati che si vedono a maschera
		HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			String flgAttoMeroIndirizzo = "";	
			if(showAttributoCustomCablato(setAttributiCustomCablati, "TASK_RESULT_2_ATTO_MERO_INDIRIZZO")) {
				flgAttoMeroIndirizzo = bean.getFlgAttoMeroIndirizzo() != null && bean.getFlgAttoMeroIndirizzo() ? "1" : "";	
			}

			
			salvaAttributiCustomAltriDirRegRespTecnica(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, flgAttoMeroIndirizzo, false);
			
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				String token = loginBean.getToken();
				String idUserLavoro = loginBean.getIdUserLavoro();
				
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
							
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
				creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				return bean;
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return bean;
	}
	
	
	public NuovaPropostaAtto2CompletaBean aggiornaStato(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		aggiornaFilePrimarioIntegraleEOmissis(bean);
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtipotargetin("U");
		input.setIduddocin(new BigDecimal(bean.getIdUd()));

		CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
		
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_GEN_FMT_DOC_ATTI_COLL") && StringUtils.isNotBlank(bean.getUriDocGeneratoFormatoOdt())) {
			try {
				File fileOdtGenerato = File.createTempFile("temp", ".odt");
				InputStream lInputStream = StorageImplementation.getStorage().extract(bean.getUriDocGeneratoFormatoOdt());
				FileUtils.copyInputStreamToFile(lInputStream, fileOdtGenerato);
				File fileDocGenerato = ModelliUtil.convertToDoc(fileOdtGenerato);
				String md5FileDocGenerato = getMd5StringFormFile(fileDocGenerato);
				FileSavedIn lFileSavedIn = new FileSavedIn();
				lFileSavedIn.setSaved(fileDocGenerato);
				SalvataggioFile lSalvataggioFile = new SalvataggioFile();
				FileSavedOut out = lSalvataggioFile.savefile(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lFileSavedIn);
				if(out.getErrorInSaved() != null) {
					throw new Exception(out.getErrorInSaved());
				}
				DocumentBean fileDocDocumentBean = new DocumentBean();
				if (StringUtils.isNotBlank(bean.getNomeFilePrimario())){
					fileDocDocumentBean.setNomeFile(FilenameUtils.getBaseName(bean.getNomeFilePrimario()) + ".doc");
				} else {
					fileDocDocumentBean.setNomeFile("ATTO_COMPLETO.doc");
				}
				fileDocDocumentBean.setUriFile(out.getUri());
				SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
				String idUdFileDoc = SezioneCacheAttributiDinamici.insertOrUpdateDocument(null, fileDocDocumentBean, getSession());
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_DOC_Ud", idUdFileDoc);
				putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "VERS_ATTO_FMT_MD5_Ud", md5FileDocGenerato);
				creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new StoreException("Si è verificato un errore durante il salvataggio della versione .doc del dispositivo atto");
			}
		}		
		
		creaModDocumentoInBean.setCodStatoDett(getExtraparams().get("codStato"));		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	private void aggiornaFilePrimarioIntegraleEOmissis(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		logger.debug("#######INIZIO populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean#######");
		if(StringUtils.isNotBlank(bean.getUriFilePrimario())) {
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
//			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, bean);			
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBeanForFilePrimarioIntegrale(lProtocollazioneBean, bean);			
			getProtocolloDataSource(bean).aggiornaFilePrimario(lProtocollazioneBean);
		}
		logger.debug("#######FINE populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean#######");
		logger.debug("#######INIZIO allegaticonomissis#######");
		if(StringUtils.isNotBlank(bean.getUriFilePrimarioOmissis())) {
			if(StringUtils.isBlank(bean.getIdDocPrimarioOmissis())) {
				DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
				lAdddocInput.setCodidconnectiontokenin(token);
				lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
				// La versione omissis del primario viene salvata come fosse un allegato
				AllegatoVersConOmissisStoreBean attributiOmissis = new AllegatoVersConOmissisStoreBean();
				attributiOmissis.setIdUd(new BigDecimal(bean.getIdUd()));
				attributiOmissis.setFlgVersConOmissis("1");
				attributiOmissis.setIdDocVersIntegrale(bean.getIdDocPrimario());							
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiOmissis));
				DmpkCoreAdddoc lDmpkCoreAdddoc = new DmpkCoreAdddoc();
				StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lDmpkCoreAdddoc.execute(getLocale(), loginBean, lAdddocInput);
				if (StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
					if (lAdddocOutput.isInError()) {
						throw new StoreException(lAdddocOutput);
					} else {
						addMessage(lAdddocOutput.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
				bean.setIdDocPrimarioOmissis(lAdddocOutput.getResultBean().getIddocout() != null ? String.valueOf(lAdddocOutput.getResultBean().getIddocout().longValue()) : null);							
			}
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
//			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, bean);			
			populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBeanForFilePrimarioIntegraleConOmissis(lProtocollazioneBean, bean);			
			getProtocolloDataSource(bean).aggiornaFilePrimarioOmissis(lProtocollazioneBean);
		}
		logger.debug("#######FINE allegaticonomissis#######");
	}
	
	public NuovaPropostaAtto2CompletaBean recuperaListaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkRepositoryGuiGetlistaemendamentiBean input = new DmpkRepositoryGuiGetlistaemendamentiBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlginclsubin(1);
		input.setIdudin(new BigDecimal(bean.getIdUd()));
		
		DmpkRepositoryGuiGetlistaemendamenti lDmpkRepositoryGuiGetlistaemendamenti = new DmpkRepositoryGuiGetlistaemendamenti();
		StoreResultBean<DmpkRepositoryGuiGetlistaemendamentiBean> output = lDmpkRepositoryGuiGetlistaemendamenti.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			throw new StoreException(output);
		}

		String strListaEmendamentiXml = output.getResultBean().getEmendamentiout();
		List<EmendamentoXmlBean> listaEmendamentiXml = new ArrayList<EmendamentoXmlBean>();
		List<EmendamentoBean> listaEmendamenti = new ArrayList<EmendamentoBean>();
		Map<String, List<EmendamentoBean>> subEmendamenti = new HashMap<>();

		if (StringUtils.isNotBlank(strListaEmendamentiXml)) {
			try {
				// Bisogna utilizzare questo o vanno in errore i Flag
				listaEmendamentiXml = XmlListaUtility.recuperaListaWithEnum(strListaEmendamentiXml, EmendamentoXmlBean.class);
//				listaEmendamentiXml = XmlListaUtility.recuperaLista(strListaEmendamentiXml, EmendamentoXmlBean.class);
				if (listaEmendamentiXml != null) {
					for (EmendamentoXmlBean emendamentoXmlBean : listaEmendamentiXml) {
						EmendamentoBean emendamentoBean = populateEmendamentoBeanFromEmendamentoXmlBean(emendamentoXmlBean);
						if (StringUtils.isNotBlank(emendamentoBean.getNroSubEmendamento())) {
							String nroEmendamento = emendamentoBean.getNroEmendamento();
							if (subEmendamenti.containsKey(nroEmendamento)) {
								subEmendamenti.get(nroEmendamento).add(emendamentoBean);
							} else {
								List<EmendamentoBean> listaSubEmendamenti = new ArrayList<>();
								listaSubEmendamenti.add(emendamentoBean);
								subEmendamenti.put(nroEmendamento, listaSubEmendamenti);
							}
						} else {
							listaEmendamenti.add(emendamentoBean);
						}
					}
				}

				for (EmendamentoBean emendamentoBean : listaEmendamenti) {
					emendamentoBean.setListaSubEmendamenti(subEmendamenti.get(emendamentoBean.getNroEmendamento()));
				}

				if (listaEmendamenti != null && !listaEmendamenti.isEmpty()) {
					Collections.sort(listaEmendamenti, EmendamentoBean.EmendamentoNo);
					bean.setListaEmendamenti(listaEmendamenti);
				} else if (subEmendamenti.values().size() == 1){
					for (List<EmendamentoBean> subEmendamentiList : subEmendamenti.values()) {
						Collections.sort(subEmendamentiList, EmendamentoBean.EmendamentoNo);
						bean.setListaEmendamenti(subEmendamentiList);
					}
				}
				bean.setListaEmendamentiBloccoRiordinoAut(output.getResultBean().getFlgbloccoriordautoout() != null && output.getResultBean().getFlgbloccoriordautoout() == 1 ? true : false);
				
			} catch (StoreException se) {
				throw se;
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
		}
				
		return bean;
	} 
	
	private EmendamentoBean populateEmendamentoBeanFromEmendamentoXmlBean(EmendamentoXmlBean emendamentoXmlBean) {
		
		EmendamentoBean result = new EmendamentoBean();
		result.setIdUd(emendamentoXmlBean.getIdUd());
		if (StringUtils.isNotBlank(emendamentoXmlBean.getNroEmendamento()) && emendamentoXmlBean.getNroEmendamento().contains("/")) {
			StringSplitterServer ss = new StringSplitterServer(emendamentoXmlBean.getNroEmendamento(), "/");
			String nroEmendamento = ss.getTokens()[0];
			String nroSub = ss.getTokens()[1];
			emendamentoXmlBean.setNroEmendamento(nroEmendamento);
			emendamentoXmlBean.setNroSubEmendamento(nroSub);
		} else if (StringUtils.isNotBlank(emendamentoXmlBean.getNroSubEmendamento()) && emendamentoXmlBean.getNroSubEmendamento().contains("/")) {
			StringSplitterServer ss = new StringSplitterServer(emendamentoXmlBean.getNroSubEmendamento(), "/");
			String nroEmendamento = ss.getTokens()[0];
			String nroSub = ss.getTokens()[1];
			emendamentoXmlBean.setNroEmendamento(nroEmendamento);
			emendamentoXmlBean.setNroSubEmendamento(nroSub);
		}
		result.setIdEmendamento(emendamentoXmlBean.getIdEmendamento());
		result.setNroEmendamento(emendamentoXmlBean.getNroEmendamento());
		result.setNroSubEmendamento(emendamentoXmlBean.getNroSubEmendamento());
		result.setTsCaricamento(emendamentoXmlBean.getTsCaricamento());
		result.setStrutturaProponente(emendamentoXmlBean.getStrutturaProponente());
		result.setCdcStrutturaProponente(emendamentoXmlBean.getCdcStrutturaProponente());
		result.setFirmatari(emendamentoXmlBean.getFirmatari());
		result.setTsPerfezionamento(emendamentoXmlBean.getTsPerfezionamento());
		result.setTipoFileRiferimento(emendamentoXmlBean.getTipoFileRiferimento());
		result.setNroAllegatoRiferimento(emendamentoXmlBean.getNroAllegatoRiferimento() != null ? emendamentoXmlBean.getNroAllegatoRiferimento() + "" : null);
		result.setNroPaginaRiferimento(emendamentoXmlBean.getNroPaginaRiferimento() != null ? emendamentoXmlBean.getNroPaginaRiferimento() + "" : null);
		result.setNroRigaRiferimento(emendamentoXmlBean.getNroRigaRiferimento() != null ? emendamentoXmlBean.getNroRigaRiferimento() + "" : null);
		result.setEffettoEmendamento(emendamentoXmlBean.getEffettoEmendamento());
		result.setEmendamentoIntegrale(emendamentoXmlBean.getEmendamentoIntegrale() != null && emendamentoXmlBean.getEmendamentoIntegrale() == Flag.SETTED);
		result.setTestoHtml(emendamentoXmlBean.getTestoHtml());
		result.setUriFile(emendamentoXmlBean.getUriFile());
		result.setNomeFile(emendamentoXmlBean.getNomeFile());
		result.setFirmato(emendamentoXmlBean.getFirmato() != null && emendamentoXmlBean.getFirmato() == Flag.SETTED);
		result.setMimetype(emendamentoXmlBean.getMimetype());
		result.setConvertibilePdf(emendamentoXmlBean.getConvertibilePdf() != null && emendamentoXmlBean.getConvertibilePdf() == Flag.SETTED);
		result.setPareriEspressi(emendamentoXmlBean.getPareriEspressi());
		result.setFilePareri(emendamentoXmlBean.getFilePareri());
		result.setIdProcess(emendamentoXmlBean.getIdProcess());
		result.setBytes(emendamentoXmlBean.getDimensioneFile() + "");
		result.setOrganoCollegiale("CONSIGLIO");
		return result;
		
	}
	
	public NuovaPropostaAtto2CompletaBean salvaListaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws StoreException {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);

		input.setFlgtipotargetin("U");
		input.setIduddocin(StringUtils.isNotBlank(bean.getIdUd()) ? new BigDecimal(bean.getIdUd()) : null);

		try {
			List<EmendamentoXmlBean> idUDEmendamentiOrdinati = new ArrayList<>();

			for (EmendamentoBean emendamento : bean.getListaEmendamenti()) {
				EmendamentoXmlBean lEmendamento = new EmendamentoXmlBean();
				lEmendamento.setIdUd(emendamento.getIdUd());
				idUDEmendamentiOrdinati.add(lEmendamento);
			}

			CreaModDocumentoInBean pModificaDocumentoInXOrdBean = new CreaModDocumentoInBean();
			pModificaDocumentoInXOrdBean.setIdUDEmendamentiOrdinati(idUDEmendamentiOrdinati);		

			XmlUtilitySerializer lXmlUtilitySerializerXOrd = new XmlUtilitySerializer();
			input.setAttributiuddocxmlin(lXmlUtilitySerializerXOrd.bindXml(pModificaDocumentoInXOrdBean));

			DmpkCoreUpddocud lDmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> lUpddocudOutput = lDmpkCoreUpddocud.execute(getLocale(), loginBean, input);

			if (lUpddocudOutput.isInError()) {
				throw new StoreException(lUpddocudOutput);
			}
			
			return bean;
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}		

	}
	
	public NuovaPropostaAtto2CompletaBean approvaEmendamenti(NuovaPropostaAtto2CompletaBean bean) throws StoreException {	
		return bean;
	}
	
	public FileDaFirmareBean getVersIntegraleSenzaFirma(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
		lFileDaFirmareBean.setNomeFile(bean.getNomeFilePrimario());
		lFileDaFirmareBean.setUri(bean.getUriFilePrimario());
		lFileDaFirmareBean.setInfoFile(bean.getInfoFilePrimario());
		return lFileDaFirmareBean;
	}
		
	public FileDaFirmareBean getLastVersPubblicazioneFirmata(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String idDocIn = null;		
		if(bean.getIdDocPrimarioOmissis() != null && !"".equals(bean.getIdDocPrimarioOmissis())) {
			idDocIn = bean.getIdDocPrimarioOmissis();
		} else if(bean.getIdDocPrimario() != null && !"".equals(bean.getIdDocPrimario())) {			
			idDocIn = bean.getIdDocPrimario();
		}
		
		if(idDocIn != null && !"".equals(idDocIn)) {
			
			Map<String, String> extraparams = super.getExtraparams();
			extraparams.put("idDocIn", idDocIn);			
			
			VisualizzaVersioniFileDataSource lVersioniDatasource = new VisualizzaVersioniFileDataSource();
			lVersioniDatasource.setSession(getSession());
			lVersioniDatasource.setExtraparams(extraparams);	
			if(getMessages() == null) {
				setMessages(new ArrayList<MessageBean>());
			}
			lVersioniDatasource.setMessages(getMessages()); 
			
			PaginatorBean<VisualizzaVersioniFileBean> versioni = lVersioniDatasource.fetch(null, null, null, null);
			
			List<VisualizzaVersioniFileBean> versioniList = versioni.getData();
			if(versioniList != null) {
				List<ComparableVisualizzaVersioniFileBean> sortedVersioniList = new ArrayList<ComparableVisualizzaVersioniFileBean>();
				for (int i = 0; i < versioniList.size(); i++) {
					ComparableVisualizzaVersioniFileBean lComparableVisualizzaVersioniFileBean = new ComparableVisualizzaVersioniFileBean();
					lComparableVisualizzaVersioniFileBean.setNr(versioniList.get(i).getNr());
					lComparableVisualizzaVersioniFileBean.setBean(versioniList.get(i));
					sortedVersioniList.add(lComparableVisualizzaVersioniFileBean);					
				}					
				Collections.sort(sortedVersioniList);
				for (int i = sortedVersioniList.size() - 1; i >= 0; i--) {
					VisualizzaVersioniFileBean currentVers = sortedVersioniList.get(i).getBean();
					if (currentVers.getIconaFirmata() != null && "1".equals(currentVers.getIconaFirmata())) {
						FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();
						lFileDaFirmareBean.setNomeFile(currentVers.getNome());
						lFileDaFirmareBean.setUri(currentVers.getUriFile());
						lFileDaFirmareBean.setInfoFile(new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(currentVers.getUriFile()).toURI().toString(), currentVers.getNome(), false, null));
						return lFileDaFirmareBean;
					}
				}
			}
		}

		return null;
	}
	
	protected static String getMd5StringFormFile(File lFile) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(new FileInputStream(lFile));
	}
	
	private boolean skipFirmaAllegatiFirmati(String uriFile, MimeTypeFirmaBean lInfoFile) throws Exception {
		// Se ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO non è presente o è false firmo sempre
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO")) {
			// Se il file non è firmato lo firmo sempre
			if (lInfoFile != null && lInfoFile.isFirmato()) {
				// Se il file è firmato ma è firmato da auriga lo rifirmo sempre
				boolean presentiFirmeExtraAuriga = lInfoFile.getInfoFirmaMarca().isFirmeExtraAuriga();
				if (presentiFirmeExtraAuriga) {
					// Se il file è firmato extra auriga lo rifirmo solamente se ha firma non valida
					boolean firmaValida = lInfoFile.isFirmaValida();
					if (firmaValida) {
						// La firma è valida e sono presenti firme extra Auriga
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaFileUnionePerLibroFirma(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		aggiornaFilePrimarioIntegraleEOmissis(bean);
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean aggiornaFileUnionePerRilascioVisto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		aggiornaFilePrimarioIntegraleEOmissis(bean);
		return bean;
	}
	
	private class ComparableVisualizzaVersioniFileBean implements Comparable<ComparableVisualizzaVersioniFileBean> {
		
		private Integer nr;
		private VisualizzaVersioniFileBean bean;
		
		public Integer getNr() {
			return nr;
		}

		public void setNr(Integer nr) {
			this.nr = nr;
		}

		public VisualizzaVersioniFileBean getBean() {
			return bean;
		}

		public void setBean(VisualizzaVersioniFileBean bean) {
			this.bean = bean;
		}

		@Override
		public int compareTo(ComparableVisualizzaVersioniFileBean o) {
			Integer nr1 = getNr() != null ? getNr() : 0;
			Integer nr2 = o != null && o.getNr() != null ? o.getNr() : 0;
			return nr1.compareTo(nr2);			
		}
		
	}
	
	public boolean isClienteComuneMilano() {
		return ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(getSession(), "CLIENTE").equalsIgnoreCase("CMMI");
	}
	
	public String getFLG_SI_SENZA_VLD_RIL_IMP() {
		if(isClienteComuneMilano()) {
			return "SI, senza validazione/rilascio impegni";
		}
		return "SI, ma senza movimenti contabili";	
	}
	
	public static HashSet<String> buildSetAttributiCustomCablati(NuovaPropostaAtto2CompletaBean bean) {
		HashSet<String> setAttributiCustomCablati = new HashSet<String>();		
		if (bean.getParametriTipoAtto() != null && bean.getParametriTipoAtto().getAttributiCustomCablati() != null && !bean.getParametriTipoAtto().getAttributiCustomCablati().isEmpty()) {
			for (int i = 0; i < bean.getParametriTipoAtto().getAttributiCustomCablati().size(); i++) {
				setAttributiCustomCablati.add(bean.getParametriTipoAtto().getAttributiCustomCablati().get(i).getAttrName());
			}
		}
		return setAttributiCustomCablati;
	}

	public static boolean isPresenteAttributoCustomCablato(HashSet<String> setAttributiCustomCablati, String nomeAttributo) {
		return setAttributiCustomCablati != null && setAttributiCustomCablati.contains(nomeAttributo);
	}
	
	public static boolean showAttributoCustomCablato(HashSet<String> setAttributiCustomCablati, String nomeAttributo) {
		return setAttributiCustomCablati == null || isPresenteAttributoCustomCablato(setAttributiCustomCablati, nomeAttributo);
	}
		
	private void salvaAttributiCustomAltriDirRegRespTecnica(HashSet<String> setAttributiCustomCablati, NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, String flgAttoMeroIndirizzo, boolean skipCtrlAttributiCustomCablati) throws Exception {
		
		List<IdSVRespFirmatarioSostitutoAltriDirTecBean> listaAltriDirRespRegTecnica = new ArrayList<IdSVRespFirmatarioSostitutoAltriDirTecBean>();
		
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_ALTRI_DIR_REG_TECNICA"))) {
			if(!"1".equals(flgAttoMeroIndirizzo)) {
				if(bean.getListaAltriDirRespRegTecnica() != null) {
					for(AltriDirRespRegTecnicaBean lAltriDirRespRegTecnicaBean : bean.getListaAltriDirRespRegTecnica()) {
						if(StringUtils.isNotBlank(lAltriDirRespRegTecnicaBean.getDirigenteRespRegTecnica())) {
							IdSVRespFirmatarioSostitutoAltriDirTecBean lIdSVRespFirmatarioBean = new IdSVRespFirmatarioSostitutoAltriDirTecBean();
							lIdSVRespFirmatarioBean.setIdSV(lAltriDirRespRegTecnicaBean.getDirigenteRespRegTecnica());
							lIdSVRespFirmatarioBean.setFlgFirmatario(lAltriDirRespRegTecnicaBean.getFlgDirigenteRespRegTecnicaFirmatario());
							
							// Dir. resp reg. tecnica sostituto
							lIdSVRespFirmatarioBean.setIdSVSostituto(showAttributoCustomCablato(setAttributiCustomCablati, "ID_SV_SOSTITUTO_ALTRI_DIR_REG_TECNICA") ? lAltriDirRespRegTecnicaBean.getDirigenteRespRegTecnicaSostituto() : "");
							lIdSVRespFirmatarioBean.setProvvedimentoSostituzione(showAttributoCustomCablato(setAttributiCustomCablati, "PROVV_SOSTITUZIONE_ALTRI_DIR_REG_TECNICA") ? lAltriDirRespRegTecnicaBean.getProvvedimentoSostituto() : "");
							listaAltriDirRespRegTecnica.add(lIdSVRespFirmatarioBean);					
						}
					}
				}		
			}
			putVariabileListaSezioneCache(sezioneCacheAttributiDinamici, "ID_SV_ALTRI_DIR_REG_TECNICA_Ud", new XmlUtilitySerializer().createVariabileLista(listaAltriDirRespRegTecnica));				
		}
	}
	
	private void salvaAttributiCustomTermineFirmeConsiglieri(HashSet<String> setAttributiCustomCablati, NuovaPropostaAtto2CompletaBean bean, SezioneCache sezioneCacheAttributiDinamici, boolean skipCtrlAttributiCustomCablati) throws Exception {
		
		String dataTermFirmeConsiglieri = "";
		if(skipCtrlAttributiCustomCablati || (showAttributoCustomCablato(setAttributiCustomCablati, "DT_TERM_FIRME_CONSIGLIERI_COPROP"))) {
			dataTermFirmeConsiglieri = bean.getDataTermFirmeConsiglieri() != null ? new SimpleDateFormat(FMT_STD_DATA).format(bean.getDataTermFirmeConsiglieri()) : "";
			putVariabileSempliceSezioneCache(sezioneCacheAttributiDinamici, "DT_TERM_FIRME_CONSIGLIERI_COPROP_Ud", dataTermFirmeConsiglieri); 
		}		
	}

	public NuovaPropostaAtto2CompletaBean modificaTermineTermineFirmeConsiglieri(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		// devo passare solo gli attributi cablati che si vedono a maschera
		HashSet<String> setAttributiCustomCablati = buildSetAttributiCustomCablati(bean);
		
		try {
			SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
			
			salvaAttributiCustomTermineFirmeConsiglieri(setAttributiCustomCablati, bean, sezioneCacheAttributiDinamici, false);
						
			if(sezioneCacheAttributiDinamici.getVariabile().size() > 0) {
			
				AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				String token = loginBean.getToken();
				String idUserLavoro = loginBean.getIdUserLavoro();
				
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setFlgtipotargetin("D");
				input.setIduddocin(StringUtils.isNotBlank(bean.getIdDocPrimario()) ? new BigDecimal(bean.getIdDocPrimario()) : null);
							
				CreaModDocumentoInBean creaModDocumentoInBean = new CreaModDocumentoInBean();
				creaModDocumentoInBean.setTipoDocumento(bean.getTipoDocumento());
				creaModDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(creaModDocumentoInBean));
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
				if(output.isInError()) {
					throw new StoreException(output);
				}
				
				return bean;
			}
		} catch (StoreException se) {
			throw se;
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return bean;
	}
	
	public boolean isClienteADSP() {
		return ParametriDBUtil.getParametroDB(getSession(), "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(getSession(), "CLIENTE").equalsIgnoreCase("ADSP");
	}
	
//	public OperazioneMassivaDocumentoDaConfrontareBean getDocumentoPerConfronto (OperazioneMassivaAttiBean bean) throws Exception {
//		OperazioneMassivaDocumentoDaConfrontareBean lOperazioneMassivaDocumentoDaConfrontareBean = new OperazioneMassivaDocumentoDaConfrontareBean();
//		List<DocumentoDaConfrontareBean> documentiDaConfrontareList = new ArrayList<DocumentoDaConfrontareBean>();
//		
//		for(AttiBean atto : bean.getListaRecord()) {		
//			
//			AttiCompletiBean lAttiCompletiBean = new AttiCompletiBean();
//			
//			if (StringUtils.isBlank(atto.getActivityName()) || StringUtils.isBlank(atto.getIdProcedimento())) {
//				OperazioneMassivaAttiBean lOperazioneMassivaAttiBean = new OperazioneMassivaAttiBean();
//				List<AttiBean> listaAtti = new ArrayList<AttiBean>();
//				listaAtti.add(atto);
//				lOperazioneMassivaAttiBean.setListaRecord(listaAtti);
//				OperazioneMassivaBean<AttiCompletiBean> listaAttiCompleti = getAttiBeanFromUnitaDocumentariaId(lOperazioneMassivaAttiBean);
//				if (listaAttiCompleti != null && listaAttiCompleti.getListaRecord() != null && listaAttiCompleti.getListaRecord().size() >= 1) {
//					lAttiCompletiBean = listaAttiCompleti.getListaRecord().get(0);
//				}
//			} else {
//				lAttiCompletiBean.setUnitaDocumentariaId(atto.getUnitaDocumentariaId());
//				lAttiCompletiBean.setIdProcedimento(atto.getIdProcedimento());
//				lAttiCompletiBean.setActivityName(atto.getActivityName());
//			}
//			
//			AttProcBean lAttProcBean = new AttProcBean();
//			lAttProcBean.setIdUd(lAttiCompletiBean.getUnitaDocumentariaId());
//			lAttProcBean.setIdProcess(lAttiCompletiBean.getIdProcedimento());
//			lAttProcBean.setActivityName(lAttiCompletiBean.getActivityName());
//						
//			lAttProcBean = getCallExecAttDatasource().call(lAttProcBean);
//			
//			NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean = new NuovaPropostaAtto2CompletaBean();
//			lNuovaPropostaAtto2CompletaBean.setIdUd(atto.getUnitaDocumentariaId());
//			lNuovaPropostaAtto2CompletaBean = get(lNuovaPropostaAtto2CompletaBean);
//			
//			setNuovaPropostaAtto2CompletaBeanFromAttProcBean(lNuovaPropostaAtto2CompletaBean, lAttProcBean);
//
//			String nomeFileUnione = lAttProcBean.getUnioneFileNomeFile();
//			String nomeFileUnioneOmissis = lAttProcBean.getUnioneFileNomeFileOmissis();
//			getExtraparams().put("nomeFileUnione", nomeFileUnione);
//			getExtraparams().put("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
//			
//			DocumentoDaConfrontareBean lDocumentoDaConfrontareBean = new DocumentoDaConfrontareBean();
//			
//			if ("dispositivo".equalsIgnoreCase(atto.getTipoDocDaConfrontare())) {
//				FileDaFirmareBean lFileDaFirmareBean = generaDispositivoDaModello(lNuovaPropostaAtto2CompletaBean);
//				String descrizioneFile = atto.getNumeroProposta();
//				lDocumentoDaConfrontareBean.setUriFile(lFileDaFirmareBean.getUri());
//				lDocumentoDaConfrontareBean.setNomeFile(lAttProcBean.getUnioneFileNomeFile());
//				lDocumentoDaConfrontareBean.setMimetypeFile("application/pdf");
//				lDocumentoDaConfrontareBean.setDescrizioneFile(descrizioneFile);
//				documentiDaConfrontareList.add(lDocumentoDaConfrontareBean);
//			} else {
//				UnioneFileAttoBean lUnioneFileAttoBean = unioneFile(lNuovaPropostaAtto2CompletaBean);
//				String descrizioneFile = atto.getNumeroProposta();
//				lDocumentoDaConfrontareBean.setUriFile(lUnioneFileAttoBean.getUriVersIntegrale());
//				lDocumentoDaConfrontareBean.setNomeFile(lAttProcBean.getUnioneFileNomeFile());
//				lDocumentoDaConfrontareBean.setMimetypeFile("application/pdf");
//				lDocumentoDaConfrontareBean.setDescrizioneFile(descrizioneFile);
//				documentiDaConfrontareList.add(lDocumentoDaConfrontareBean);
//			}
//		}
//		lOperazioneMassivaDocumentoDaConfrontareBean.setListaRecord(documentiDaConfrontareList);
//		return lOperazioneMassivaDocumentoDaConfrontareBean;
//	}

	public OperazioneMassivaProtocollazioneBean getEstremiUnitaDocumentarie(OperazioneMassivaProtocollazioneBean listaUnitaDocumentarie) throws Exception {
		
		OperazioneMassivaProtocollazioneBean operazioneMassivaAttiBeanToReturn = new OperazioneMassivaProtocollazioneBean();
		List<ProtocollazioneBean> listaProtocolli = new ArrayList<ProtocollazioneBean>();
		if (listaUnitaDocumentarie != null && listaUnitaDocumentarie.getListaRecord() != null) {
			for (ProtocollazioneBean protocollo : listaUnitaDocumentarie.getListaRecord()) {
				ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource();
				lProtocolloDataSource.setSession(getSession());
				Map<String, String> extraparams = new HashMap<String, String>();
				extraparams.put("flgSoloAbilAzioni", "1");
				lProtocolloDataSource.setExtraparams(extraparams);
				ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
				lProtocollazioneBean.setIdUd(protocollo.getIdUd());
				lProtocollazioneBean = lProtocolloDataSource.get(lProtocollazioneBean);
								
				listaProtocolli.add(lProtocollazioneBean);
			}
		}
		
		operazioneMassivaAttiBeanToReturn.setListaRecord(listaProtocolli);
		return operazioneMassivaAttiBeanToReturn;
	}

//	public OperazioneMassivaBean<AttiCompletiBean> getAttiBeanFromUnitaDocumentariaId(OperazioneMassivaAttiBean listaAtti) throws Exception {
//		
//		OperazioneMassivaBean<AttiCompletiBean> operazioneMassivaAttiBeanToReturn = new OperazioneMassivaBean<AttiCompletiBean>();
//		List<AttiCompletiBean> listaAttiCompleti = new ArrayList<AttiCompletiBean>();
//		if (listaAtti != null && listaAtti.getListaRecord() != null) {
//			for (AttiBean atto : listaAtti.getListaRecord()) {
//				AttiCompletiBean lAttiCompletiBean = new AttiCompletiBean();
//				
//				ProtocolloDataSource lProtocolloDataSource = new ProtocolloDataSource();
//				lProtocolloDataSource.setSession(getSession());
//				Map<String, String> extraparams = new HashMap<String, String>();
//				extraparams.put("flgSoloAbilAzioni", "1");
//				lProtocolloDataSource.setExtraparams(extraparams);
//				ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
//				lProtocollazioneBean.setIdUd(new BigDecimal(atto.getUnitaDocumentariaId()));
//				lProtocollazioneBean = lProtocolloDataSource.get(lProtocollazioneBean);
//				
//				PraticheDataSource lPraticheDataSource = new PraticheDataSource();
//				lPraticheDataSource.setSession(getSession());
//				PraticaBean lPraticaBean = new PraticaBean();
//				lPraticaBean.setIdPratica(lProtocollazioneBean.getIdProcess());
//				lPraticaBean = lPraticheDataSource.get(lPraticaBean);
//				
//				GetListaAttFlussoProcDatasource lGetListaAttFlussoProcDatasource = new GetListaAttFlussoProcDatasource();
//				lGetListaAttFlussoProcDatasource.setSession(getSession());
//				extraparams = new HashMap<String, String>();
//				extraparams.put("idProcess", lPraticaBean.getIdPratica());
//				extraparams.put("idTipoProc", lPraticaBean.getTipoProcedimento());
//				lGetListaAttFlussoProcDatasource.setExtraparams(extraparams);
//				PaginatorBean<AttProcBean> lPaginator = lGetListaAttFlussoProcDatasource.fetch(null, null, null, null);
//				
//				AttProcBean lAttProcBeanEseguibile = null;
//				if (lPaginator.getData() != null) {
//					for (AttProcBean lAttFlussoProc : lPaginator.getData()) {
//						if ("1".equalsIgnoreCase(lAttFlussoProc.getFlgEseguibile())) {
//							lAttProcBeanEseguibile = lAttFlussoProc;
//						}
//						
//					}
//				}
//				
//				lAttiCompletiBean.setUnitaDocumentariaId(atto.getUnitaDocumentariaId());
//				lAttiCompletiBean.setIdProcedimento(lPraticaBean.getIdPratica());
//				if (lAttProcBeanEseguibile != null) {
//					lAttiCompletiBean.setActivityName(lAttProcBeanEseguibile.getActivityName());	
//				}
//				lAttiCompletiBean.setNumeroProposta(lProtocollazioneBean.getSiglaProtocollo() + " " + lProtocollazioneBean.getNroProtocollo() + " / " + lProtocollazioneBean.getAnnoProtocollo());
//				
//				listaAttiCompleti.add(lAttiCompletiBean);
//			}
//		}
//		
//		operazioneMassivaAttiBeanToReturn.setListaRecord(listaAttiCompleti);
//		return operazioneMassivaAttiBeanToReturn;
//	}
	
//	private void setNuovaPropostaAtto2CompletaBeanFromAttProcBean(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, AttProcBean lAttProcBean) {
//		lNuovaPropostaAtto2CompletaBean.setIdProcess(lAttProcBean.getIdProcess());
//		lNuovaPropostaAtto2CompletaBean.setIdModCopertina(lAttProcBean.getIdModCopertina() != null ? lAttProcBean.getIdModCopertina() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModCopertina(lAttProcBean.getNomeModCopertina() != null ? lAttProcBean.getNomeModCopertina() : "");
//		lNuovaPropostaAtto2CompletaBean.setIdModCopertinaFinale(lAttProcBean.getIdModCopertinaFinale() != null ? lAttProcBean.getIdModCopertinaFinale() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModCopertinaFinale(lAttProcBean.getNomeModCopertinaFinale() != null ? lAttProcBean.getNomeModCopertinaFinale() : "");
//		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparati(lAttProcBean.getIdModAllegatiParteIntSeparati() != null ? lAttProcBean.getIdModAllegatiParteIntSeparati() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparati(lAttProcBean.getNomeModAllegatiParteIntSeparati() != null ? lAttProcBean.getNomeModAllegatiParteIntSeparati() : "");
//		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparati(lAttProcBean.getUriModAllegatiParteIntSeparati() != null ? lAttProcBean.getUriModAllegatiParteIntSeparati() : "");
//		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparati(lAttProcBean.getTipoModAllegatiParteIntSeparati() != null ? lAttProcBean.getTipoModAllegatiParteIntSeparati() : "");
//		lNuovaPropostaAtto2CompletaBean.setIdModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getIdModAllegatiParteIntSeparatiXPubbl() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getNomeModAllegatiParteIntSeparatiXPubbl() : "");
//		lNuovaPropostaAtto2CompletaBean.setUriModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getUriModAllegatiParteIntSeparatiXPubbl() : "");
//		lNuovaPropostaAtto2CompletaBean.setTipoModAllegatiParteIntSeparatiXPubbl(lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl() != null ? lAttProcBean.getTipoModAllegatiParteIntSeparatiXPubbl() : "");
//		lNuovaPropostaAtto2CompletaBean.setFlgAppendiceDaUnire(lAttProcBean.getFlgAppendiceDaUnire());		
//		lNuovaPropostaAtto2CompletaBean.setIdModAppendice(lAttProcBean.getIdModAppendice() != null ? lAttProcBean.getIdModAppendice() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModAppendice(lAttProcBean.getNomeModAppendice() != null ? lAttProcBean.getNomeModAppendice() : "");
//		lNuovaPropostaAtto2CompletaBean.setIdModello(lAttProcBean.getIdModAssDocTask() != null ? lAttProcBean.getIdModAssDocTask() : "");
//		lNuovaPropostaAtto2CompletaBean.setNomeModello(lAttProcBean.getNomeModAssDocTask() != null ? lAttProcBean.getNomeModAssDocTask() : "");
//		lNuovaPropostaAtto2CompletaBean.setDisplayFilenameModello(lAttProcBean.getDisplayFilenameModAssDocTask() != null ? lAttProcBean.getDisplayFilenameModAssDocTask() : "");
//		lNuovaPropostaAtto2CompletaBean.setIdUoDirAdottanteSIB(lAttProcBean.getIdUoDirAdottanteSIB() != null ? lAttProcBean.getIdUoDirAdottanteSIB() : "");
//		lNuovaPropostaAtto2CompletaBean.setCodUoDirAdottanteSIB(lAttProcBean.getCodUoDirAdottanteSIB() != null ? lAttProcBean.getCodUoDirAdottanteSIB() : "");
//		lNuovaPropostaAtto2CompletaBean.setDesUoDirAdottanteSIB(lAttProcBean.getDesUoDirAdottanteSIB() != null ? lAttProcBean.getDesUoDirAdottanteSIB() : "");	
//		lNuovaPropostaAtto2CompletaBean.setImpostazioniUnioneFile(lAttProcBean.getImpostazioniUnioneFile());
//	}
	
//	private CallExecAttDatasource getCallExecAttDatasource() {
//		CallExecAttDatasource lCallExecAttDatasource = new CallExecAttDatasource();
//		lCallExecAttDatasource.setSession(getSession());
//		lCallExecAttDatasource.setExtraparams(getExtraparams());	
//		if(getMessages() == null) {
//			setMessages(new ArrayList<MessageBean>());
//		}
//		lCallExecAttDatasource.setMessages(getMessages()); 		
//		return lCallExecAttDatasource;
//	}	
	
	
	public FileDaFirmareBean generaSchedaDatiAttoPerConfronto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		String nomeModelloSchedaAtto = ParametriDBUtil.getParametroDB(getSession(), "NOME_MODELLO_SCHEDA_DATI_ATTO");;
		String idModelloSchedaAtto = ParametriDBUtil.getParametroDB(getSession(), "ID_MODELLO_SCHEDA_DATI_ATTO");	
		if (StringUtils.isBlank(nomeModelloSchedaAtto) || StringUtils.isBlank(idModelloSchedaAtto)) {
			throw new Exception("Modello per generazione dati scheda atto non configurato correttamente");
		}
		
		// Chiamo la get per avere il dettaglio completo, quello che ho in ingresso è ricavato con il flag per sole abilitazioni
		NuovaPropostaAtto2CompletaDataSource lNuovaPropostaAtto2CompletaDataSource = new NuovaPropostaAtto2CompletaDataSource();
		lNuovaPropostaAtto2CompletaDataSource.setSession(getSession());
		bean = lNuovaPropostaAtto2CompletaDataSource.get(bean);				
		
		boolean flgMostraDatiSensibili = bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili();
		String templateValues = getDatiXGenDaModello(bean, nomeModelloSchedaAtto, !flgMostraDatiSensibili); // DISPOSITIVO_DETERMINA
				
		Map<String, Object> model = ModelliUtil.createMapToFillTemplateFromSezioneCache(templateValues, true);
		
		FileDaFirmareBean fillModelBean = ModelliUtil.fillFreeMarkerTemplateWithModel(bean.getIdProcess(), idModelloSchedaAtto, model, true, bean.getFlgMostraDatiSensibili(), bean.getFlgMostraOmissisBarrati(), bean.getElencoCampiConGestioneOmissisDaIgnorare(), getSession());
		
		String ext = "pdf";
		String nomeFilePdf = null;
		if(flgMostraDatiSensibili) {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "SCHEDA_DATI.%s", ext);
		} else {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "SCHEDA_DATI_OMISSIS.%s", ext);
		}
		fillModelBean.setNomeFile(nomeFilePdf);
		
		return fillModelBean;
	}
	
	public FileDaFirmareBean getTestoAttoPerConfronto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		boolean flgMostraDatiSensibili = bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili();
		
		FileDaFirmareBean beanToReturn = new FileDaFirmareBean();
			
		if (flgMostraDatiSensibili) {
			beanToReturn.setUri(bean.getUriFilePrimario());
			beanToReturn.setInfoFile(bean.getInfoFilePrimario());
		} else {
			beanToReturn.setUri(bean.getUriFilePrimarioOmissis());
			beanToReturn.setInfoFile(bean.getInfoFilePrimarioOmissis());
		}
		
		String ext = "pdf";
		String nomeFilePdf = null;
		if(flgMostraDatiSensibili) {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_ATTO_.%s", ext);
		} else {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "TESTO_ATTO_OMISSIS.%s", ext);
		}
		beanToReturn.setNomeFile(nomeFilePdf);
		
		return beanToReturn;
	}
	
	public FileDaFirmareBean generaSchedaDatiAttoConTestoAttoPerConfronto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		
		FileDaFirmareBean schedaDatiAtto = generaSchedaDatiAttoPerConfronto(bean);
		schedaDatiAtto.setFile(StorageImplementation.getStorage().extractFile(schedaDatiAtto.getUri()));
		
		FileDaFirmareBean testoAttoBean = new FileDaFirmareBean();
		if (bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili()) {
			testoAttoBean.setUri(bean.getUriFilePrimario());
			testoAttoBean.setInfoFile(bean.getInfoFilePrimario());
			testoAttoBean.setFile(StorageImplementation.getStorage().extractFile(testoAttoBean.getUri()));
		} else {
			testoAttoBean.setUri(bean.getUriFilePrimarioOmissis());
			testoAttoBean.setInfoFile(bean.getInfoFilePrimarioOmissis());
			testoAttoBean.setFile(StorageImplementation.getStorage().extractFile(testoAttoBean.getUri()));

		}
		
		List<File> listaFileDaUnire = new ArrayList<File>();
		listaFileDaUnire.add(schedaDatiAtto.getFile());
		listaFileDaUnire.add(testoAttoBean.getFile());
		
		File fileSchedaConTestoAtto = mergeFilePdf(listaFileDaUnire);
		
		String uriFileSchedaConTestoAtto = StorageImplementation.getStorage().store(fileSchedaConTestoAtto, new String[] {});
		
		FileDaFirmareBean beanToReturn = new FileDaFirmareBean();
		
		MimeTypeFirmaBean lMimeTypeSchedaConTestoAtto = new MimeTypeFirmaBean();
		lMimeTypeSchedaConTestoAtto.setFirmato(false);
		lMimeTypeSchedaConTestoAtto.setMimetype("application/pdf");
		lMimeTypeSchedaConTestoAtto.setBytes(fileSchedaConTestoAtto.length());
		
		beanToReturn.setUri(uriFileSchedaConTestoAtto);
		beanToReturn.setInfoFile(lMimeTypeSchedaConTestoAtto);
		
		String ext = "pdf";
		String nomeFilePdf = null;
		boolean flgMostraDatiSensibili = bean.getFlgMostraDatiSensibili() != null && bean.getFlgMostraDatiSensibili();
		if(flgMostraDatiSensibili) {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "SCHEDA_DATI_CON_TESTO_ATTO.%s", ext);
		} else {
			nomeFilePdf = String.format(getPrefixRegNum(bean) + "SCHEDA_DATI_CON_TESTO_ATTO_OMISSIS.%s", ext);
		}
		beanToReturn.setNomeFile(nomeFilePdf);
		
		return beanToReturn;
	}
	
	public void generaAllegatiDaModelloPerOperazioniMassiveDiAvanzamento(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean, String idUd, String idProcedimento, String activityName, String esito) throws Exception {
		logger.debug("#######INIZIO generaAllegatiDaModelloPerOperazioniMassiveDiAvanzamento######");	
		PraticheDataSource lPraticheDataSource = getPraticheDataSource();
		PraticaBean lPraticaBean = new PraticaBean();
		lPraticaBean.setIdPratica(idProcedimento);
		lPraticaBean = lPraticheDataSource.get(lPraticaBean);
		
		logger.debug("#######INIZIO for listaModelliAttivita.size######");
		LinkedHashMap<String, List<ModelloAttivitaBean>> mappaModelli = new LinkedHashMap<String, List<ModelloAttivitaBean>>();
		List<ModelloAttivitaBean> listaModelliAttivita = lPraticaBean.getListaModelliAttivita();
		if (listaModelliAttivita != null) {
			for (int i = 0; i < listaModelliAttivita.size(); i++) {
				ModelloAttivitaBean lModelloAttivitaBean = listaModelliAttivita.get(i);
				List<ModelloAttivitaBean> listaModelliAttivitaCorrente = mappaModelli.get(lModelloAttivitaBean.getActivityName());
				if(listaModelliAttivitaCorrente == null) {
					listaModelliAttivitaCorrente = new ArrayList<ModelloAttivitaBean>();
				}
				listaModelliAttivitaCorrente.add(lModelloAttivitaBean);
				mappaModelli.put(lModelloAttivitaBean.getActivityName(), listaModelliAttivitaCorrente);	
			}
		}
		logger.debug("#######FINE for listaModelliAttivita.size######");
		
		List<ModelloAttivitaBean> listaModelliPerVariEsiti = null;
		if(mappaModelli.get(activityName) != null) {
			listaModelliPerVariEsiti = mappaModelli.get(activityName);
		}
		
		List<CompilaModelloAttivitaBean> listaModelliDaGenerare = new ArrayList<CompilaModelloAttivitaBean>();
		if (listaModelliPerVariEsiti != null && listaModelliPerVariEsiti.size() > 0) {	
			List<CompilaModelloAttivitaBean> listaModelliConEsitoUguale = new ArrayList<CompilaModelloAttivitaBean>();		
			List<CompilaModelloAttivitaBean> listaModelliSenzaEsito = new ArrayList<CompilaModelloAttivitaBean>();		
			logger.debug("#######INIZIO for listaModelliPerVariEsiti.size######");
			for (int i = 0; i < listaModelliPerVariEsiti.size(); i++) {
				String listaEsitiXGenModello = listaModelliPerVariEsiti.get(i).getEsitiXGenModello();							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : listaEsitiXGenModello.split("\\|\\*\\|")) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							if(listaModelliPerVariEsiti.get(i).getFlgPostAvanzamentoFlusso() == null || !listaModelliPerVariEsiti.get(i).getFlgPostAvanzamentoFlusso()) {
								CompilaModelloAttivitaBean lCompilaModelloAttivitaBean = new CompilaModelloAttivitaBean();
								logger.debug("#######INIZIO getPropertyUtils().copyProperties######");
								BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lCompilaModelloAttivitaBean, listaModelliPerVariEsiti.get(i));
								logger.debug("#######FINE getPropertyUtils().copyProperties######");
								listaModelliConEsitoUguale.add(lCompilaModelloAttivitaBean);
							}
						}
					} 
				} else if(!listaModelliPerVariEsiti.get(i).getFlgPostAvanzamentoFlusso()) {
					CompilaModelloAttivitaBean lCompilaModelloAttivitaBean = new CompilaModelloAttivitaBean();
					logger.debug("#######INIZIO getPropertyUtils().copyProperties######");
					BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lCompilaModelloAttivitaBean, listaModelliPerVariEsiti.get(i));
					logger.debug("#######FINE getPropertyUtils().copyProperties######");
					listaModelliSenzaEsito.add(lCompilaModelloAttivitaBean);
				}
			}	
			logger.debug("#######FINE for listaModelliPerVariEsiti.size######");
			if(listaModelliConEsitoUguale != null && listaModelliConEsitoUguale.size() > 0) {
				listaModelliDaGenerare = listaModelliConEsitoUguale;
			} else if(listaModelliSenzaEsito != null && listaModelliSenzaEsito.size() > 0) {
				listaModelliDaGenerare = listaModelliSenzaEsito;
			} 
		}
		
		if (listaModelliDaGenerare != null && !listaModelliDaGenerare.isEmpty()) {
			CompilaListaModelliNuovaPropostaAtto2CompletaBean lCompilaListaModelli = new CompilaListaModelliNuovaPropostaAtto2CompletaBean();
			lCompilaListaModelli.setProcessId(idProcedimento);
			lCompilaListaModelli.setIdUd(idUd);
			lCompilaListaModelli.setListaRecordModelli(listaModelliDaGenerare);
			lCompilaListaModelli.setDettaglioBean(lNuovaPropostaAtto2CompletaBean);
			logger.debug("#######INIZIO compilazioneAutomaticaListaModelliPdf######");
			CompilaListaModelliNuovaPropostaAtto2CompletaBean lCompilaListaModelliNuovaPropostaAtto2CompletaBean = compilazioneAutomaticaListaModelliPdf(lCompilaListaModelli);
			List<CompilaModelloAttivitaBean> listaModelliGenerati = lCompilaListaModelliNuovaPropostaAtto2CompletaBean.getListaRecordModelli();
			logger.debug("#######FINE compilazioneAutomaticaListaModelliPdf######");
			logger.debug("#######INIZIO if (listaModelliGenerati######");
			if (listaModelliGenerati != null) {
				List<AllegatoProtocolloBean> listaAllegati = lNuovaPropostaAtto2CompletaBean.getListaAllegati();
				logger.debug("#######INIZIO for listaModelliGenerati.size()######");
				for (int i = 0; i < listaModelliGenerati.size(); i++) {
					String descrizioneFileAllegato = listaModelliGenerati.get(i).getDescrizione();
					String nomeFileAllegato = listaModelliGenerati.get(i).getNomeFile() + ".pdf";
					String uriFileAllegato = listaModelliGenerati.get(i).getUriFileGenerato();
					MimeTypeFirmaBean infoAllegato = listaModelliGenerati.get(i).getInfoFileGenerato();
					
					String idTipoModello = listaModelliGenerati.get(i).getIdTipoDoc();
					String nomeTipoModello = listaModelliGenerati.get(i).getNomeTipoDoc();
					boolean flgDaFirmare = listaModelliGenerati.get(i).getFlgDaFirmare() != null && listaModelliGenerati.get(i).getFlgDaFirmare();
					
					AllegatoProtocolloBean lBeanModello = new AllegatoProtocolloBean();
					int posModello = getPosAllegatoFromTipo(idTipoModello, listaAllegati);		
					if (posModello != -1) {
						lBeanModello = listaAllegati.get(posModello);
					}
					
					boolean flgParere = listaModelliGenerati.get(i).getFlgParere() != null && listaModelliGenerati.get(i).getFlgParere();									
					boolean flgParteDispositivo = listaModelliGenerati.get(i).getFlgParteDispositivo() != null && listaModelliGenerati.get(i).getFlgParteDispositivo();									
					boolean flgNoPubbl = listaModelliGenerati.get(i).getFlgNoPubbl() != null && listaModelliGenerati.get(i).getFlgNoPubbl();									
					boolean flgPubblicaSeparato = listaModelliGenerati.get(i).getFlgPubblicaSeparato() != null && listaModelliGenerati.get(i).getFlgPubblicaSeparato();									
					lBeanModello.setFlgParere(flgParere);
					if(flgParere) {
						lBeanModello.setFlgParteDispositivo(false);
						lBeanModello.setFlgNoPubblAllegato(flgNoPubbl);
						lBeanModello.setFlgPubblicaSeparato(true);
					} else {
						lBeanModello.setFlgParteDispositivo(flgParteDispositivo);
						if(!flgParteDispositivo) {
							lBeanModello.setFlgNoPubblAllegato(true);
							lBeanModello.setFlgPubblicaSeparato(false);
							lBeanModello.setFlgDatiSensibili(false);
						} else {
							lBeanModello.setFlgNoPubblAllegato(flgNoPubbl);	
							lBeanModello.setFlgPubblicaSeparato(flgPubblicaSeparato);
						}
					}
					
					lBeanModello.setNomeFileAllegato(nomeFileAllegato);
					lBeanModello.setUriFileAllegato(uriFileAllegato);
					lBeanModello.setDescrizioneFileAllegato(descrizioneFileAllegato);
	
					lBeanModello.setListaTipiFileAllegato(idTipoModello);
					lBeanModello.setIdTipoFileAllegato(idTipoModello);
					lBeanModello.setDescTipoFileAllegato(nomeTipoModello);
	
					lBeanModello.setRemoteUri(false);
					lBeanModello.setIsChanged(true);
					lBeanModello.setNomeFileVerPreFirma(nomeFileAllegato);
					lBeanModello.setUriFileVerPreFirma(uriFileAllegato);
					lBeanModello.setInfoFileVerPreFirma(infoAllegato);
					lBeanModello.setImprontaVerPreFirma(infoAllegato.getImpronta());
					lBeanModello.setInfoFile(infoAllegato);
									
					if (posModello == -1) {
						if (listaAllegati == null || listaAllegati.size() == 0) {
							listaAllegati = new ArrayList<AllegatoProtocolloBean>();
						}
						listaAllegati.add(0, lBeanModello);
					} else {
						listaAllegati.set(posModello, lBeanModello);
					}
				}
				logger.debug("#######FINE for listaModelliGenerati.size()######");
				// La listaAllegati potrebbe essere un oggetto nuovo, quiandi la risalvo
				lNuovaPropostaAtto2CompletaBean.setListaAllegati(listaAllegati);
			}
			logger.debug("#######FINE if (listaModelliGenerati######");
		}
		logger.debug("#######FINE generaAllegatiDaModelloPerOperazioniMassiveDiAvanzamento######");
	}
	
	public void salvaPrimarioEAllegatiPerOperazioniMassiveDiAvanzamento(NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean) throws Exception {
		logger.debug("#######INIZIO salvaPrimarioEAllegatiPerOperazioniMassiveDiAvanzamento######");
		ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();
		NuovaPropostaAtto2CompletaDataSource.populateProtocollazioneBeanFromNuovaPropostaAtto2CompletaBean(lProtocollazioneBean, lNuovaPropostaAtto2CompletaBean);
		ProtocolloDataSource lProtocolloDataSource = getProtocolloDataSource(lNuovaPropostaAtto2CompletaBean);
		lProtocolloDataSource.getExtraparams().put("isFromFirmaOVistoMassivi", "true");
		lProtocolloDataSource.getExtraparams().put("skipAggiornaMetadatiFileNonModificati", "true");
		lProtocolloDataSource.updateAllegatiDocumento(lProtocollazioneBean);
		logger.debug("#######FINE salvaPrimarioEAllegatiPerOperazioniMassiveDiAvanzamento######");
	}
	
	// Recupero la posizione dell'allegato di quel tipo
	private int getPosAllegatoFromTipo(String idTipoModello, List<AllegatoProtocolloBean> listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.size(); i++) {
				AllegatoProtocolloBean allegato = listaAllegati.get(i);
				if (allegato.getListaTipiFileAllegato() != null && allegato.getListaTipiFileAllegato().equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
	}

}