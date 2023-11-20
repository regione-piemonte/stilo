/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.GsonBuilder;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.spi.resource.Singleton;

import fr.opensagres.xdocreport.document.json.JSONObject;
import io.swagger.annotations.Api;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovastruttorgobjBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettudxguimodprotBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkDefSecurityTrovastruttorgobj;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.client.DmpkRepositoryGuiLoaddettudxguimodprot;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.AlboPretorioTimbroWSConfigBean;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.albopretorio.AlboPretorioException;
import it.eng.document.function.bean.albopretorio.AlboUDBean;
import it.eng.document.function.bean.albopretorio.AlboUDXmlBean;
import it.eng.document.function.bean.albopretorio.CriteriAvanzatiAlbo;
import it.eng.document.function.bean.albopretorio.FileAlbo;
import it.eng.document.function.bean.albopretorio.FilesAlboPretorio;
import it.eng.document.function.bean.albopretorio.FiltriAlboPretorio;
import it.eng.document.function.bean.albopretorio.FiltroAlbo;
import it.eng.document.function.bean.albopretorio.ListaBean;
import it.eng.document.function.bean.albopretorio.OrganigrammaAlboBean;
import it.eng.document.function.bean.albopretorio.RegistrazioneAlbo;
import it.eng.document.function.bean.albopretorio.TipologiaTreeBean;
import it.eng.document.function.bean.albopretorio.XmlTipologiaOutBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FirmaUtility;
import it.eng.utility.TimbraUtil;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

/**
 * @author Antonio Peluso
 * 
 *         SERVIZI Business utilizzati dall' ALBO PRETORIO
 */

@Singleton
@Api
@Path("/alboPretorio")
public class AlboPretorioRestService{

	@Context
	ServletContext context;

	@Context
	HttpServletRequest request;

	private static final Logger logger = Logger.getLogger(AlboPretorioRestService.class);

	private static final String TOKEN_AURIGALOGIN = "token";
	private static final String SCHEMA_AURIGALOGIN = "schema";
	private static final String ID_USER_LAVORO_AURIGALOGIN = "idUserLavoro";
	private static final String STORE_ERROR = "599";
	
	private static final String KEY_MULTIPART_JSON = "jsonResponse";
	private static final String KEY_MULTIPART_FILE = "fileResponse";
	
	protected static AlboPretorioTimbroWSConfigBean lAlboPretorioTimbroWSConfigBean = null;
	
	private static String searchFullText = null;


	/**
	 * Servizio per il recupero delle tipologie di documenti pubblicabili per popolare la select del filtro "Tipo atto"
	 * per la ricerca avanzata
	 * 
	 * dmpk_load_combo con TipoComboIn = TIPO_DOC e AltriParametriIn = FLG_SOLO_DA_PUBBLICARE|*|1, FlgSoloVldIn = 1
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getTipologieDoc")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTipologieDoc(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getTipologieDoc");

		List<XmlTipologiaOutBean> listaTipiDoc = new ArrayList<>();

		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_DA_PUBBLICARE|*|1|*|FINALITA|*|SCELTA");
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));

			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
					.execute(null, loginBean, lDmpkLoadComboDmfn_load_comboBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			if (lStoreResultBean.getResultBean().getListaxmlout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				listaTipiDoc = XmlListaUtility.recuperaLista(xmlLista, XmlTipologiaOutBean.class);
			}

		} catch (StoreException e) {
			logger.error("AlboPretorio - getTipologieDoc: " + e.getMessage(), e);
			return new AlboPretorioException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AlboPretorio - getTipologieDoc: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getTipologieDoc: " + e.getMessage() + " - " + e);
		}

		ListaBean<XmlTipologiaOutBean> list = new ListaBean<>(listaTipiDoc);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(list, ListaBean.class);

		return Response.ok(res).build();

	}

	/**
	 * Servizio per il recupero delle tipologie di documenti pubblicabili in forma alberata per popolare il menu di navigazione laterale
	 * 
	 * dmpk_load_combo con TipoComboIn = TIPO_DOC e AltriParametriIn = FLG_SOLO_DA_PUBBLICARE|*|1, FlgSoloVldIn = 1
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getTipologieDocTree")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getTipologieDocTree(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getTipologieDoc");


		List<TipologiaTreeBean> listaTipiDocTree = new ArrayList<>();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("TIPO_DOC");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("FLG_SOLO_DA_PUBBLICARE|*|1");
			lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(new BigDecimal(1));

			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
					.execute(null, loginBean, lDmpkLoadComboDmfn_load_comboBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			if (lStoreResultBean.getResultBean().getListaxmlout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				List<XmlTipologiaOutBean>  listaTipiDoc = XmlListaUtility.recuperaLista(xmlLista, XmlTipologiaOutBean.class);

				/*costruisco alberatura*/
				listaTipiDocTree = builAlberatura(listaTipiDoc);

			}

		} catch (StoreException e) {
			logger.error("AlboPretorio - getTipologieDocTree: " + e.getMessage(), e);
			return new AlboPretorioException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AlboPretorio - getTipologieDocTree: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getTipologieDocTree: " + e.getMessage() + " - " + e);
		}

		ListaBean<TipologiaTreeBean> list = new ListaBean<>(listaTipiDocTree);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(list, ListaBean.class);

		return Response.ok(res).build();

	}

	private List<TipologiaTreeBean> builAlberatura(List<XmlTipologiaOutBean> listaTipiDoc) {


		List<TipologiaTreeBean> listaAlberata = new ArrayList<>();

		/* nodo root dell'albero*/
		TipologiaTreeBean root = new TipologiaTreeBean();
		root.setIdType("/");
		root.setDescrizione("root");

		listaAlberata.add(root);

		//scorro la lista dei dati tornati, man mano che riesco a inserire (in una nuova struttura alberata) gli elementi come figli nell'ordine giusto, li cancello dalla lista,
		//altrimenti riparto da sopra e la scorro n volte finche non saranno piazzati tutti gli elementi
		int index = 0;

		for (int i = 0; i < listaTipiDoc.size(); i++) {
			XmlTipologiaOutBean tipoDocElement = listaTipiDoc.get(i);

			String idPadre;
			if (tipoDocElement.getIdTypePadre() != null && !"".equalsIgnoreCase(tipoDocElement.getIdTypePadre())) {
				// è un child
				idPadre = tipoDocElement.getIdTypePadre();
			}else {
				idPadre = "/";
			}

			if(!inserisciAlberaturaRicorsiva(listaAlberata.get(0), idPadre, tipoDocElement)) {
				TipologiaTreeBean newChild = new TipologiaTreeBean();
				newChild.setIdType(tipoDocElement.getIdType());
				newChild.setDescrizione(tipoDocElement.getDescrizione());
				newChild.setIdTypePadre(tipoDocElement.getIdTypePadre());

				listaAlberata.get(0).getChildren().add(newChild);
			};


		}

		return listaAlberata.get(0).getChildren();
	}

	private boolean inserisciAlberaturaRicorsiva(TipologiaTreeBean rootNode, String idPadre, XmlTipologiaOutBean element) {

		if(rootNode.getIdType().equals(idPadre)) {

			TipologiaTreeBean newChild = new TipologiaTreeBean();
			newChild.setIdType(element.getIdType());
			newChild.setDescrizione(element.getDescrizione());
			newChild.setIdTypePadre(element.getIdTypePadre());
			//			newChild.setPathAlberatura(rootNode.getPathAlberatura() + element.getId() + ";");

			rootNode.getChildren().add(newChild);

			return true;
		}

		for(TipologiaTreeBean titolarioAlberato : rootNode.getChildren()) {
			if(inserisciAlberaturaRicorsiva(titolarioAlberato, idPadre,element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Servizio per il recupero delle UO per popolare la select del filtro "Strutture interne"
	 * per la ricerca avanzata
	 * 
	 * o	in CriteriAvanzatiIO: FlgSoloUOAttive = 1
	 * o	in CriteriPersonalizzatiIO una riga fissa con: 
	 * 		-	colonna 1 = FLG_VISIB_IN_ALBO 
	 * 		-	colonna 2 = uguale 
	 *		-	colonna 3 = 1
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getUO")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUO(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getUO");

		List<OrganigrammaAlboBean> lListResult = new ArrayList<OrganigrammaAlboBean>();

		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkDefSecurityTrovastruttorgobjBean lInputBean = new DmpkDefSecurityTrovastruttorgobjBean();
			// flgSenzaPaginazione altrimenti torna solo 10 risultati
			lInputBean.setFlgsenzapaginazionein(1);

			// Setto i criteri avanzati all'input della chiamata alla TrovaStrutture
			CriteriAvanzatiAlbo lCriteriaIo = new CriteriAvanzatiAlbo();
			lCriteriaIo.setFlgSoloUOAttive("1");
			XmlUtilitySerializer xml = new XmlUtilitySerializer();
			lInputBean.setCriteriavanzatiio(xml.bindXml(lCriteriaIo));

			//Costruisco i criteri personalizzati 
			SezioneCache lSezioneCache = new SezioneCache();
			Variabile critPers = new Variabile();
			Lista listaCriteriPers = new Lista();
			Riga riga = new Riga();

			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent("FLG_VISIB_IN_ALBO");

			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent("uguale");

			Colonna col3 = new Colonna();
			col3.setNro(new BigInteger("3"));
			col3.setContent("1");

			riga.getColonna().add(col1);
			riga.getColonna().add(col2);
			riga.getColonna().add(col3);

			listaCriteriPers.getRiga().add(riga);
			critPers.setLista(listaCriteriPers);
			lSezioneCache.getVariabile().add(critPers);

			String xmlCriteriPersonalizzati = null;
			if (lSezioneCache != null) {
				StringWriter lStringWriter = new StringWriter();
				Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
				lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				lMarshaller.marshal(lSezioneCache, lStringWriter);
				xmlCriteriPersonalizzati = lStringWriter.toString();
			}
			lInputBean.setCriteripersonalizzatiio(xmlCriteriPersonalizzati);

			DmpkDefSecurityTrovastruttorgobj store = new DmpkDefSecurityTrovastruttorgobj();
			StoreResultBean<DmpkDefSecurityTrovastruttorgobjBean> lStoreResultBean = store.execute(null, loginBean, lInputBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			String xmlLista = lStoreResultBean.getResultBean().getResultout();
			try {
				lListResult = XmlListaUtility.recuperaLista(xmlLista, OrganigrammaAlboBean.class);
			} catch (Exception e) {
				throw new Exception(e);
			}

		}  catch (StoreException e) {
			logger.error("AlboPretorio - getUO: " + e.getMessage(), e);
			return new AlboPretorioException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AlboPretorio - getUO: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getUO: " + e.getMessage() + " - " + e);
		}

		ListaBean<OrganigrammaAlboBean> list = new ListaBean<>(lListResult);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(list, ListaBean.class);

		return Response.ok(res).build();

	}


	/**
	 * Servizio per il recupero del dettagli dell'atto a partire dall'idUD
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getDettaglio")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDettaglio(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getDettaglio");


		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		// recupero l'idUd dell'atto
		String idUd = dataInput.select("idUd").text();
		String idPubblicazione = dataInput.select("idPubblicazione").text();

		AlboUDBean attoBean = new AlboUDBean();
		try {

			if (StringUtils.isBlank(idUd)) {
				throw new Exception("AlboPretorio - getDettaglio: idUd dell'atto non valorizzato. DataInput: " + dataInputXml);
			}

			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(loginBean.getSchema());
			SubjectUtil.subject.set(subject);

			// metodo per il caricamento del dettaglio
			AlboUDXmlBean lAlboUDXmlBean = loadDettaglio(loginBean, idUd, idPubblicazione);

			List<FileAlbo> listaFileAlbo = createListaFilesAlbo(lAlboUDXmlBean, idUd);

			attoBean = createBeanForAlbo(lAlboUDXmlBean);
			attoBean.setIdUd(idUd);
			attoBean.setFilesAlbo(listaFileAlbo);
			attoBean.setNroAllegati(Integer.toString(listaFileAlbo.size()));

		}  catch (StoreException e) {
			logger.error("AlboPretorio - getDettaglio: " + e.getMessage(), e);
			return new AlboPretorioException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AlboPretorio - getDettaglio: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getDettaglio: " + e.getMessage() + " - " + e);
		}

		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(attoBean, AlboUDBean.class);

		return Response.ok(res).build();

	}


	/**
	 * Servizio per il recupero di un file. 
	 * Se viene passato solo l'uri il servizio è stato richiamato per effettuarne il download, 
	 * altrimenti è stato richiamato per effettuarne la preview
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getFile")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFile(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getFile");

		String base64File = null;
		String mimeType = null;
		JSONObject jsonResponse = new JSONObject();

		// Faccio il parse dell'xml in input al servizio
		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		// recupero uri del file
		String uriFile = dataInput.select("uriFile").text();	
		// recupero il mimetype del file
		String mime = dataInput.select("mimetype").text();
		// recupero il nome del file
		String nomeFile = dataInput.select("nomeFile").text();
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = dataInput.select("flgConvertibile").text();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file richiesto non è valorizzato. DataInput: " + dataInputXml);
			}

			// recupero il file fisico
			File lFile = DocumentStorage.extract(uriFile, null);

			if (StringUtils.isNotBlank(mime)){
				// servizio chiamato per la preview del file

				// calcolo l'infoFile del file di cui fare la preview
				MimeTypeFirmaBean infoFile = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), lFile.getName(), false, null);

				if (infoFile != null && infoFile.isFirmato() && 
						StringUtils.isNotBlank(infoFile.getTipoFirma()) && infoFile.getTipoFirma().toLowerCase().contains("cades")) {

					//se il file è un p7m faccio la preview dello sbustato
					String nomeFileSbustato = nomeFile.contains(".p7m") ? nomeFile.replace(".p7m", "") : nomeFile;
					File tempFile = File.createTempFile("tmp", "." + FilenameUtils.getExtension(nomeFileSbustato));

					// sbusto il file
					sbustaFileFirmatoOld(lFile, tempFile);

					// calcolo l'infoFile del file sbustato
					MimeTypeFirmaBean infoFileSbustato = new InfoFileUtility().getInfoFromFile(tempFile.toURI().toString(), tempFile.getName(), false, null);
					mimeType = infoFileSbustato.getMimetype();

					if (!"application/pdf".equals(mimeType)) {
						//se lo sbustato non è un pdf prima lo converto e poi restituisco il base64
						InputStream is = TimbraUtil.converti(tempFile, nomeFileSbustato);
						File lFileConvert = File.createTempFile("tmpConvert", ".pdf");
						FileUtils.copyInputStreamToFile(is, lFileConvert);
						mimeType = "application/pdf";
						base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFileConvert));
						nomeFile = nomeFileSbustato;
						if(is!=null) {
							is.close();
						}
					} else {
						//se lo sbustato è un pdf restituisco il base64
						base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(tempFile));
					}

				} else if ("application/pdf".equals(mime)) {
					// se è un pdf ritorno il base64
					base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFile));
					mimeType = mime;
				} else if (StringUtils.isNotBlank(flgConvertibile) && "1".equals(flgConvertibile)) {
					// se non è un pdf prima lo converto e poi ritorno il base64
					InputStream is = TimbraUtil.converti(lFile, nomeFile);
					File lFileConvert = File.createTempFile("tmpConvert", ".pdf");
					FileUtils.copyInputStreamToFile(is, lFileConvert);
					mimeType = "application/pdf";
					base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFileConvert));
					if(is!=null) {
						is.close();
					}
				} else {
					// se il file non è convertibile in pdf ritorno il base64 per il download
					base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFile));
					mimeType = "application/octet-stream";
				}
			} else {
				// se sto chiamando il servizio per il download
				base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFile));
				mimeType = "application/octet-stream";
			}

		} catch (Exception e) {
			logger.error("AlboPretorio - getFile: " + e.getMessage() + ". DataInput: " + dataInputXml, e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getFile: " + e.getMessage() + " - " + e);
		}

		jsonResponse.put("base64", base64File);
		jsonResponse.put("mimeType", mimeType);
		jsonResponse.put("nomeFile", nomeFile);
		String jsonResponseString = jsonResponse.toString();
		return Response.ok(jsonResponseString).build();
	}


	/**
	 * Servizio per ottenere la certificazione delle firme apposte sul file 
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/verificaFirma")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response verificaFirma(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - verificaFirma");

		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		String uriFile = dataInput.select("uriFile").text();
		String nomeFile = dataInput.select("nomeFile").text();
		String base64File = null;

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stata richiesta la verifica della firma non è valorizzato. DataInput: " + dataInputXml);
			}

			File lFile = DocumentStorage.extract(uriFile, null);

			File lFileCertificazione =  new FirmaUtility().creaFileCertificazione(lFile.toURI().toString(), nomeFile, false, null, null);
			base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(lFileCertificazione));

		} catch (Exception e) {
			logger.error("AlboPretorio - verificaFirma:" + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - verificaFirma:" + e.getMessage() + " - " + e);
		}

		return Response.ok(base64File).build();
	}


	/**
	 * 
	 * Servizio per ottenere il file contenuto nella busta di trasporto
	 * Sbustamento file p7m
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/sbustaFile")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response sbustaFile(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - sbustaFile");

		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		String uriFile = dataInput.select("uriFile").text();
		String base64File = null;
		String mimeTypeSbustato = null;
		String fileName = dataInput.select("nomeFile").text();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stato richiesto lo sbustato non è valorizzato. DataInput: " + dataInputXml);
			}

			File lFile = DocumentStorage.extract(uriFile, null);

			File tempFile = File.createTempFile("tmp", "");

			sbustaFileFirmatoOld(lFile, tempFile);

			MimeTypeFirmaBean infoFileSbustato = new InfoFileUtility().getInfoFromFile(tempFile.toURI().toString(), fileName, false, null);
			mimeTypeSbustato = infoFileSbustato.getMimetype();
			base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(tempFile));

		} catch (Exception e) {
			logger.error("AlboPretorio - sbustaFile: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - sbustaFile: " + e.getMessage() + " - " + e);
		}	

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("base64", base64File);
		jsonResponse.put("mimeType", mimeTypeSbustato);	

		String jsonResponseString = jsonResponse.toString();
		return Response.ok(jsonResponseString).build();
	}


	/**
	 * Servizio per il download di un archivio zip contenente tutti i file legati all'unità
	 * documentaria pubblicata
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/downloadZip")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response downloadZip(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - downloadZip");

		String base64File = null;
		File fileToZip;

		try {

			FilesAlboPretorio filesAlboPretorio = convertXmlToFilesAlbo(dataInputXml);

			fileToZip = File.createTempFile("Files", ".zip");

			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(fileToZip.getPath()));

			for (FileAlbo lFile : filesAlboPretorio.getFilesAlbo()) {

				File fileExtract = DocumentStorage.extract(lFile.getUriFile(), null);
				InputStream is = FileUtils.openInputStream(fileExtract);
				String lNomeFile = lFile.getNomeFile().contains("/") ? lFile.getNomeFile().replaceAll("/", "_") : lFile.getNomeFile();
				zip.putNextEntry(new ZipEntry(lNomeFile));

				int length;
				byte[] b = new byte[1024 * 10];

				while ((length = is.read(b)) > 0) {
					zip.write(b, 0, length);
				}

				zip.closeEntry();
				is.close();
			}

			zip.close();

		} catch (Exception e) {
			logger.error("AlboPretorio - downloadZip: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - downloadZip: " + e.getMessage() + " - " + e);
		}

		base64File = Base64.encodeBase64String(FileUtils.readFileToByteArray(fileToZip));

		return Response.ok(base64File).build();
	}

	/**
	 * Servizio per la ricerca degli atti dati i filtri in ingresso
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/search")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response search(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - search");

		List<AlboUDBean> listAlboBean = new ArrayList<AlboUDBean>();

		try {

			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			FiltriAlboPretorio filtriAlboPretorio = convertXmlToFiltriAlboPretorio(dataInputXml);

			String advancedFilters = manageFiltriPerStore(filtriAlboPretorio);

			FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(advancedFilters,
					loginBean);

			List<Object> resFinder = new ArrayList<>();
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(null, loginBean, lFindRepositoryObjectBean)
						.getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
			String xmlResultSetOut = (String) resFinder.get(0);

			if (xmlResultSetOut != null) {
				listAlboBean = XmlListaUtility.recuperaLista(xmlResultSetOut, AlboUDBean.class);
			}

		} catch (StoreException e) {
			logger.error("AlboPretorio - search: " + e.getMessage(), e);
			return new AlboPretorioException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AlboPretorio - search: " + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - search: " + e.getMessage() + " - " + e);
		}

		ListaBean<AlboUDBean> list = new ListaBean<>(listAlboBean);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(list, ListaBean.class);

		return Response.ok(res).build();
	}

	private AlboUDBean createBeanForAlbo(AlboUDXmlBean pAttoXmlBean) throws Exception {
		AlboUDBean alboUDBean = new AlboUDBean();

		alboUDBean.setAttoNumero(pAttoXmlBean.getAttoNumero());
		alboUDBean.setPubblicazioneNumero(pAttoXmlBean.getPubblicazioneNumero());
		alboUDBean.setDataAtto(parseDate(pAttoXmlBean.getDataAtto()));
		alboUDBean.setDataFinePubbl(pAttoXmlBean.getDataFinePubbl());
		alboUDBean.setDataInizioPubbl(pAttoXmlBean.getDataInizioPubbl());
		alboUDBean.setEsecutivoDal(parseDate(pAttoXmlBean.getEsecutivoDal()));
		alboUDBean.setFormaPubblicazione(pAttoXmlBean.getFormaPubblicazione());
		alboUDBean.setIdDocPrimario(pAttoXmlBean.getIdDocPrimario());
		alboUDBean.setMotivoAnnullamento(pAttoXmlBean.getMotivoAnnullamento());
		alboUDBean.setOggetto(pAttoXmlBean.getOggetto());
		alboUDBean.setRichiedente(pAttoXmlBean.getRichiedente());
		alboUDBean.setStatoPubblicazione(pAttoXmlBean.getStatoPubblicazione());
		alboUDBean.setTipo(pAttoXmlBean.getTipo());
		alboUDBean.setTsPubblicazione(pAttoXmlBean.getTsPubblicazione());
		alboUDBean.setFlgImmediatamenteEsegiubile(pAttoXmlBean.getFlgImmediatamenteEsegiubile());
		alboUDBean.setDataAdozione(pAttoXmlBean.getDataAdozione());
		return alboUDBean;
	}

	// Bisogna mappare questo bean da parametri di configurazione
	private FindRepositoryObjectBean createFindRepositoryObjectBean(String advancedFilters, AurigaLoginBean loginBean) {
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();

		if (StringUtils.isNotBlank(searchFullText)) {
			lFindRepositoryObjectBean.setFiltroFullText(searchFullText);
		}
		// questo parametro è utilizzato per la ricerca fulltext
		// chiedere a valentina in cosa cercare ricorsivamente
		String[] checkAttributes = new String[] { "NOTE", "DESTINATARI", "ESTREMI_REG_NUM", "MITTENTI", "NOME",
				"DES_OGG", "PAROLE_CHIAVE" };

		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setSearchAllTerms(1);
		lFindRepositoryObjectBean.setFlgSubfoderSearchIn("1");

		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setFlgUdFolder("U");
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(1);
		lFindRepositoryObjectBean.setColsOrderBy("6");
		String colsToReturn = "2,4,14,15,18,32,71,72,91,201,270,271,273,274,275,276,283,FLG_IMMEDIATAMENTE_ESEGUIBILE";
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);

		return lFindRepositoryObjectBean;
	}


	/**
	 * Metodo che mappa i filtri da dare in input alla store
	 * 
	 * @param filtriAlboPretorio
	 * @return
	 * @throws Exception
	 */
	private String manageFiltriPerStore(FiltriAlboPretorio filtriAlboPretorio) throws Exception {

		CriteriAvanzatiAlbo lCritAvanzati = new CriteriAvanzatiAlbo();
		String numeroAtto = null;
		Date dataAttoDa = null;
		Date dataAttoA = null;
		// la variabile statica per la ricerca con lucene va sbiancata sempre
		searchFullText = null;

		for (FiltroAlbo filtroAlbo : filtriAlboPretorio.getFiltriAlbo()) {
			if ("OGGETTO".equalsIgnoreCase(filtroAlbo.getName())) {
				// la ricerca per oggetto è sempre con operatore "contiene"
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					lCritAvanzati.setOggettoUD("%" + filtroAlbo.getValue() + "%");
				}
			} else if ("FULL_TEXT".equalsIgnoreCase(filtroAlbo.getName())) {
				// ricerca con lucene
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					searchFullText = filtroAlbo.getValue();
				}else {
					searchFullText = null;
				}
			} else if ("TIPO_DOC".equalsIgnoreCase(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					lCritAvanzati.setIdDocType(filtroAlbo.getValue());
				}
			} else if ("NUMERO".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					numeroAtto = filtroAlbo.getValue();
				}
			} else if ("DATA_DA".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataDa = filtroAlbo.getValue();
					GregorianCalendar start = new GregorianCalendar();
					start.setTime(parseDate(dataDa));
					start.set(Calendar.HOUR_OF_DAY, 0);
					start.set(Calendar.MINUTE, 0);
					start.set(Calendar.SECOND, 0);
					start.set(Calendar.MILLISECOND, 0);
					dataAttoDa = start.getTime();
				}

			} else if ("DATA_A".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataA = filtroAlbo.getValue();
					GregorianCalendar end = new GregorianCalendar();
					end.setTime(parseDate(dataA));
					end.set(Calendar.HOUR_OF_DAY, 23);
					end.set(Calendar.MINUTE, 59);
					end.set(Calendar.SECOND, 59);
					end.set(Calendar.MILLISECOND, 999);
					dataAttoA = end.getTime();
				}
			} else if ("NASCONDI_ANNULLATE".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					lCritAvanzati.setStatoPubblicazioneDett(filtroAlbo.getValue());
				}
			} else if ("DESC_RICHIEDENTE".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					lCritAvanzati.setMittenteUD(filtroAlbo.getValue());
				}
			} else if ("ID_RICHIEDENTE".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {
					lCritAvanzati.setIdMittenteUDInRubrica(filtroAlbo.getValue());
				}
			} else if ("INIZIO_PUBBL_DA".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataDa = filtroAlbo.getValue();
					GregorianCalendar start = new GregorianCalendar();
					start.setTime(parseDate(dataDa));
					start.set(Calendar.HOUR_OF_DAY, 0);
					start.set(Calendar.MINUTE, 0);
					start.set(Calendar.SECOND, 0);
					start.set(Calendar.MILLISECOND, 0);
					lCritAvanzati.setInizioPubblicazioneDal(start.getTime());
				}
			} else if ("INIZIO_PUBBL_A".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataA = filtroAlbo.getValue();
					GregorianCalendar end = new GregorianCalendar();
					end.setTime(parseDate(dataA));
					end.set(Calendar.HOUR_OF_DAY, 23);
					end.set(Calendar.MINUTE, 59);
					end.set(Calendar.SECOND, 59);
					end.set(Calendar.MILLISECOND, 999);
					lCritAvanzati.setInizioPubblicazioneAl(end.getTime());
				}
			} else if ("FINE_PUBBL_DA".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataDa = filtroAlbo.getValue();
					GregorianCalendar start = new GregorianCalendar();
					start.setTime(parseDate(dataDa));
					start.set(Calendar.HOUR_OF_DAY, 0);
					start.set(Calendar.MINUTE, 0);
					start.set(Calendar.SECOND, 0);
					start.set(Calendar.MILLISECOND, 0);
					lCritAvanzati.setTerminePubblicazioneDal(start.getTime());
				}
			} else if ("FINE_PUBBL_A".equals(filtroAlbo.getName())) {
				if (StringUtils.isNotBlank(filtroAlbo.getValue())) {

					String dataA = filtroAlbo.getValue();
					GregorianCalendar end = new GregorianCalendar();
					end.setTime(parseDate(dataA));
					end.set(Calendar.HOUR_OF_DAY, 23);
					end.set(Calendar.MINUTE, 59);
					end.set(Calendar.SECOND, 59);
					end.set(Calendar.MILLISECOND, 999);
					lCritAvanzati.setTerminePubblicazioneAl(end.getTime());
				}
			}
		}

		List<RegistrazioneAlbo> listaRegistrazioni = new ArrayList<RegistrazioneAlbo>();
		RegistrazioneAlbo registrazioneUD = new RegistrazioneAlbo();
		registrazioneUD.setCategoria("#U");
		registrazioneUD.setNumeroDa(numeroAtto);
		registrazioneUD.setNumeroA(numeroAtto);
		registrazioneUD.setDataDa(dataAttoDa);
		registrazioneUD.setDataA(dataAttoA);
		listaRegistrazioni.add(registrazioneUD);
		lCritAvanzati.setRegistrazioni(listaRegistrazioni);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		return lXmlUtilitySerializer.bindXml(lCritAvanzati);

	}

	private List<FileAlbo> createListaFilesAlbo(AlboUDXmlBean pAttomlBean, String idUd) {
		List<FileAlbo> listaFilesAlbo = new ArrayList<>();

		if (pAttomlBean.getFilePrimario() != null && pAttomlBean.getFilePrimario().getUri() != null) {
			// recupero il file primario
			FilePrimarioOutBean lfilePrimario = pAttomlBean.getFilePrimario();

			FileAlbo lFileAlbo = new FileAlbo();
			lFileAlbo.setUriFile(lfilePrimario.getUri());
			lFileAlbo.setNomeFile(lfilePrimario.getNomeOriginale());
			lFileAlbo.setDisplayFilename(lfilePrimario.getDisplayFilename());
			lFileAlbo.setDimensione(String.valueOf(lfilePrimario.getDimensione()));
			lFileAlbo.setFirmatari(lfilePrimario.getFirmatari());
			lFileAlbo.setFlgFirmato(lfilePrimario.getFlgFirmato().toString().equals("1") ? "1" : "0");
			lFileAlbo.setFlgPrimario("1");
			lFileAlbo.setMimetype(lfilePrimario.getMimetype());
			lFileAlbo.setFlgConvertibilePdf(lfilePrimario.getFlgConvertibilePdf().toString().equals("1") ? "1" : "0");
			lFileAlbo.setImpronta(lfilePrimario.getImpronta());
			lFileAlbo.setAlgoritmoImpronta(lfilePrimario.getAlgoritmoImpronta());
			lFileAlbo.setEncodingImpronta(lfilePrimario.getEncodingImpronta());
			lFileAlbo.setIdDoc(pAttomlBean.getIdDocPrimario());
			lFileAlbo.setIdUd(idUd);
			listaFilesAlbo.add(lFileAlbo);
		}

		// recupero gli allegati
		for (AllegatiOutBean lAllegato : pAttomlBean.getAllegati()) {
			FileAlbo lAllegatoAlbo = new FileAlbo();
			lAllegatoAlbo.setUriFile(lAllegato.getUri());
			lAllegatoAlbo.setNomeFile(lAllegato.getNomeOriginale());
			lAllegatoAlbo.setDisplayFilename(lAllegato.getDisplayFileName());
			lAllegatoAlbo.setDimensione(String.valueOf(lAllegato.getDimensione()));
			lAllegatoAlbo.setFirmatari(lAllegato.getFirmatari());
			lAllegatoAlbo.setFlgFirmato(lAllegato.getFlgFileFirmato().toString().equals("1") ? "1" : "0");
			lAllegatoAlbo.setFlgPrimario("0");
			lAllegatoAlbo.setMimetype(lAllegato.getMimetype());
			lAllegatoAlbo.setFlgConvertibilePdf(lAllegato.getFlgConvertibilePdf().toString().equals("1") ? "1" : "0");
			lAllegatoAlbo.setImpronta(lAllegato.getImpronta());
			lAllegatoAlbo.setAlgoritmoImpronta(lAllegato.getAlgoritmoImpronta());
			lAllegatoAlbo.setEncodingImpronta(lAllegato.getEncodingImpronta());
			lAllegatoAlbo.setIdDoc(lAllegato.getIdDoc());
			lAllegatoAlbo.setIdUd(idUd);
			listaFilesAlbo.add(lAllegatoAlbo);
		}


		return listaFilesAlbo;
	}

	private FiltriAlboPretorio convertXmlToFiltriAlboPretorio(String dataInputXml) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(FiltriAlboPretorio.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (FiltriAlboPretorio) jaxbUnmarshaller.unmarshal(new StringReader(dataInputXml));

	}

	private FilesAlboPretorio convertXmlToFilesAlbo(String dataInputXml) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(FilesAlboPretorio.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (FilesAlboPretorio) jaxbUnmarshaller.unmarshal(new StringReader(dataInputXml));

	}

	private AlboUDXmlBean loadDettaglio(AurigaLoginBean loginBean, String idUd, String idPubblicazione) throws StoreException, Exception {

		DmpkRepositoryGuiLoaddettudxguimodprot store = new DmpkRepositoryGuiLoaddettudxguimodprot();
		DmpkRepositoryGuiLoaddettudxguimodprotBean lInputBean = new DmpkRepositoryGuiLoaddettudxguimodprotBean();
		if(idPubblicazione!=null && !"".equals(idPubblicazione)) {
			lInputBean.setIdpubblicazionein(idPubblicazione);
		}
		lInputBean.setIdudin(new BigDecimal(idUd));

		StoreResultBean<DmpkRepositoryGuiLoaddettudxguimodprotBean> resultStore = store.execute(null, loginBean, lInputBean);

		if (resultStore.isInError()) {
			logger.error("AlboPretorio - getDettaglio: " + resultStore.getErrorContext() + " - " + resultStore.getErrorCode() + " - "
					+ resultStore.getDefaultMessage());
			throw new StoreException(resultStore.getDefaultMessage());
		}

		String lStrXmlIn = resultStore.getResultBean().getDatiudxmlout();
		logger.debug("AlboPretorio - getDettaglio idUd " + idUd + ": " + resultStore.getResultBean().getDatiudxmlout());

		return new XmlUtilityDeserializer().unbindXml(lStrXmlIn, AlboUDXmlBean.class);
	}

	/**
	 * Metodo per il recupero dei parametri per l'autenticazione dall'header della request
	 * 
	 * @param request
	 * @return
	 */
	private AurigaLoginBean getAurigaLoginBean(HttpServletRequest request) {
		AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		// recupero il token
		aurigaLoginBean.setToken(request.getHeader(TOKEN_AURIGALOGIN));
		// recupero lo schema
		aurigaLoginBean.setSchema(request.getHeader(SCHEMA_AURIGALOGIN));
		// recupero l'idUserLavoro
		aurigaLoginBean.setIdUserLavoro(StringUtils.isNotBlank(request.getHeader(ID_USER_LAVORO_AURIGALOGIN))? request.getHeader(ID_USER_LAVORO_AURIGALOGIN) : null);

		logger.debug("AlboPretorio - Recupero parametri di autenticazione: Token - " + aurigaLoginBean.getToken()
		+ "; Schema - " + aurigaLoginBean.getSchema() + "; IdUserLavoro - " + aurigaLoginBean.getIdUserLavoro());

		return aurigaLoginBean;
	}

	/**
	 * Metodo per sbustare un file p7m
	 * 
	 * @param p7m
	 * @param tempFile
	 * @return 
	 * @throws IOException
	 * @throws Exception
	 */
	private InputStream sbustaFileFirmato(String p7m, String fileName, File tempFile) throws IOException, Exception {

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();		  			
		return lInfoFileUtility.sbusta(p7m, fileName);

//		FileOutputStream fos = new FileOutputStream(tempFile);
//		try {
//			byte[] buff = new byte[1024];
//			int bytesRead = 0;
//			while((bytesRead = lInputStream.read(buff)) != -1) {
//				fos.write(buff, 0, bytesRead);
//			}
//			fos.flush();
//		} catch (Exception e) {	
//			throw e;
//		} finally {
//			fos.close();
//			lInputStream.close();
//		}
	}

	protected Date parseDate(String date) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse(date);
		} catch (Exception e1) {
			try {
				return displayDateFormat.parse(date);
			} catch (Exception e2) {
			}
		}
		return null;
	}
	
	/**
	 * Servizio per verificare la conformità dell'impronta con il documento 
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/verificaImpronta")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response verificaImpronta(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - verificaImpronta");

		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		String uriFile = dataInput.select("uriFile").text();
		String algoritmoImpronta = dataInput.select("algoritmoImpronta").text();
		String impronta = dataInput.select("impronta").text();
		String esito = null;

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stata richiesta la verifica dell'impronta non è valorizzato. DataInput: " + dataInputXml);
			}

			File lFile = DocumentStorage.extract(uriFile, null);
			
			String calcolaImpronta = calcolaImpronta(lFile, algoritmoImpronta);
			if(impronta.equals(calcolaImpronta)) {
				esito="L'impronta risulta conforme al documento";
			} else {
				esito="L'impronta risulta conforme al documento";
			}

		} catch (Exception e) {
			logger.error("AlboPretorio - verificaImpronta:" + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - verificaImpronta:" + e.getMessage() + " - " + e);
		}

		return Response.ok(esito).build();
	}
	
	
	/**
	 * Servizio per recuperare le info di un file dall'uri 
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/getInfoFile")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInfoFile(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getInfoFile");
		
		MimeTypeFirmaBean infoFile;
		
		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());
		
		String uriFile = dataInput.select("uriFile").text();
		String nomeFile = dataInput.select("nomeFile").text();
		
		try {
			
			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stata richiesta la verifica dell'impronta non è valorizzato. DataInput: " + dataInputXml);
			}
			
			File lFile = DocumentStorage.extract(uriFile, null);
			infoFile = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), nomeFile, false, null);
			
			JSONObject jsonResponse = new JSONObject();
			String jsonResponseString = "";
			
			if(infoFile!=null) {
				jsonResponse.put("mimeType", infoFile.getMimetype());
				jsonResponse.put("nomeFile", infoFile.getCorrectFileName());
				jsonResponse.put("algoritmo", infoFile.getAlgoritmo());
				jsonResponse.put("firmato", infoFile.isFirmato());
				jsonResponse.put("tipoFirma", infoFile.getTipoFirma());
				jsonResponse.put("infoFirma", infoFile.getInfoFirma());
				jsonResponse.put("convertibile", infoFile.isConvertibile());
				jsonResponse.put("firmaValida", infoFile.isFirmaValida());
				jsonResponse.put("impronta", infoFile.getImpronta());
				jsonResponse.put("encoding", infoFile.getEncoding());
				jsonResponse.put("idFormato", infoFile.getIdFormato());
				jsonResponseString = jsonResponse.toString();
			}
			
			return Response.ok(jsonResponseString).build();
			
		} catch (Exception e) {
			logger.error("AlboPretorio - getInfoFile:" + e.getMessage(), e);
			return new AlboPretorioException().throwException("500", "AlboPretorio - getInfoFile:" + e.getMessage() + " - " + e);
		}
	}
	
	/**
	 * 
	 ******************************************************************************************
	 * 	 SERVIZIO UGUALE A getFile MA CON LA GESTIONE DEI FILE OTTIMIZATA (NON COME BASE64)	  *															  *
	 ******************************************************************************************
	 * 
	 * 
	 * Servizio per il recupero di un file. 
	 * Se viene passato solo l'uri il servizio è stato richiamato per effettuarne il download, 
	 * altrimenti è stato richiamato per effettuarne la preview
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	
	@POST
	@Path("/recuperaFile")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response recuperaFile(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - recuperaFile");

		// Faccio il parse dell'xml in input al servizio
		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		// recupero uri del file
		String uriFile = dataInput.select("uriFile").text();	
		// recupero il mimetype del file
		String mimeType = dataInput.select("mimetype").text();
		// recupero il nome del file
		String nomeFile = dataInput.select("nomeFile").text();
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = dataInput.select("flgConvertibile").text();			
		// recupero l'info se file è firmato
		String flgFirmato = dataInput.select("flgFirmato").text();
		
		long tSTART = System.currentTimeMillis();
		
		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file richiesto non è valorizzato. DataInput: " + dataInput);
			}

			// recupero il file fisico
			long t0 = System.currentTimeMillis();
			File lFile = DocumentStorage.extract(uriFile, null);
			/* Performance logger */
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " EXTRACT DOCUMENT " + " timeOperation: " + ((t1 - t0)) + " ms");

			if (StringUtils.isNotBlank(flgFirmato) && "1".equalsIgnoreCase(flgFirmato) && (nomeFile.contains(".p7m") || nomeFile.contains(".tsd") || nomeFile.contains(".m7m"))) {

				// se il file è un p7m faccio la preview dello sbustato
				String nomeFileSbustato = nomeFile.contains(".p7m") ? nomeFile.replace(".p7m", "") : nomeFile;
				File tempFile = File.createTempFile("tmp", "." + FilenameUtils.getExtension(nomeFileSbustato));

				InputStream is = null;

				// sbusto il file
				t0 = System.currentTimeMillis();
				is = sbustaFileFirmato(lFile.toURI().toString(), nomeFile, tempFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " SBUSTA FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");

				if (!"application/pdf".equals(mimeType)) {
					// se lo sbustato non è un pdf prima lo converto e poi restituisco il file
					File lFileConvert = File.createTempFile("tmpConvert", ".pdf");
					t0 = System.currentTimeMillis();
					FileUtils.copyInputStreamToFile(is, lFileConvert);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " COPIA INPUT STREAM PER CONVERSIONE " + " timeOperation: " + ((t1 - t0)) + " ms");
					
					t0 = System.currentTimeMillis();
					is = TimbraUtil.converti(lFileConvert, nomeFileSbustato);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				}

				StreamingOutput streamFile = buildStreamFile(is);
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();

			} else if (StringUtils.isNotBlank(flgConvertibile) && "1".equals(flgConvertibile) && !"application/pdf".equals(mimeType)) {
				// se non è un pdf prima lo converto
				t0 = System.currentTimeMillis();
				InputStream is = TimbraUtil.converti(lFile, nomeFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				
				StreamingOutput streamFile = buildStreamFile(is);
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			} else {
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(lFile));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			}

		} catch (Exception e) {
			logger.error("AlboPretorio - recuperaFile: " + e.getMessage() + ". DataInput: " + dataInput, e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - recuperaFile: " + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - recuperaFile: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO RECUPERA FILE " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/getManualeRapido")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getManualeRapido(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - recuperaFile");

		// Faccio il parse dell'xml in input al servizio
		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		// recupero uri del file
		String uriFile = dataInput.select("uriFile").text();	
		// recupero il mimetype del file
		String mimeType = dataInput.select("mimetype").text();
		// recupero il nome del file
		String nomeFile = dataInput.select("nomeFile").text();
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = dataInput.select("flgConvertibile").text();			
		// recupero l'info se file è firmato
		String flgFirmato = dataInput.select("flgFirmato").text();
		
		long tSTART = System.currentTimeMillis();
		
		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file richiesto non è valorizzato. DataInput: " + dataInput);
			}

			// recupero il file fisico
			long t0 = System.currentTimeMillis();
			File lFile = new File(uriFile);
			/* Performance logger */
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " EXTRACT DOCUMENT " + " timeOperation: " + ((t1 - t0)) + " ms");

			if (StringUtils.isNotBlank(flgFirmato) && "1".equalsIgnoreCase(flgFirmato)) {

				// se il file è un p7m faccio la preview dello sbustato
				String nomeFileSbustato = nomeFile.contains(".p7m") ? nomeFile.replace(".p7m", "") : nomeFile;
				File tempFile = File.createTempFile("tmp", "." + FilenameUtils.getExtension(nomeFileSbustato));

				InputStream is = null;

				// sbusto il file
				t0 = System.currentTimeMillis();
				is = sbustaFileFirmato(lFile.toURI().toString(), nomeFile, tempFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " SBUSTA FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");

				if (!"application/pdf".equals(mimeType)) {
					// se lo sbustato non è un pdf prima lo converto e poi restituisco il file
					File lFileConvert = File.createTempFile("tmpConvert", ".pdf");
					t0 = System.currentTimeMillis();
					FileUtils.copyInputStreamToFile(is, lFileConvert);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " COPIA INPUT STREAM PER CONVERSIONE " + " timeOperation: " + ((t1 - t0)) + " ms");
					
					t0 = System.currentTimeMillis();
					is = TimbraUtil.converti(lFileConvert, nomeFileSbustato);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				}

				StreamingOutput streamFile = buildStreamFile(is);
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();

			} else if (StringUtils.isNotBlank(flgConvertibile) && "1".equals(flgConvertibile)) {
				// se non è un pdf prima lo converto
				t0 = System.currentTimeMillis();
				InputStream is = TimbraUtil.converti(lFile, nomeFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				
				StreamingOutput streamFile = buildStreamFile(is);
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			} else {
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(lFile));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			}

		} catch (Exception e) {
			logger.error("AlboPretorio - recuperaFile: " + e.getMessage() + ". DataInput: " + dataInput, e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - recuperaFile: " + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - recuperaFile: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO RECUPERA FILE " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	/**
	 * 
	 **********************************************************************************************
	 * 	 SERVIZIO UGUALE A downloadZip MA CON LA GESTIONE DEI FILE OTTIMIZATA (NON COME BASE64)	  *															  *
	 **********************************************************************************************
	 *
	 *
	 * Servizio per il download di un archivio zip contenente tutti i file legati all'unità
	 * documentaria pubblicata
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/scaricaZip")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response scaricaZip(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - scaricaZip");

		File fileDaRestituire = null;
		File fileToZip;

		long tSTART = System.currentTimeMillis();
		
		try {

			FilesAlboPretorio filesAlboPretorio = convertXmlToFilesAlbo(dataInputXml);

			fileToZip = File.createTempFile("Files", ".zip");

			long t0 = System.currentTimeMillis();
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(fileToZip.getPath()));

			for (FileAlbo lFile : filesAlboPretorio.getFilesAlbo()) {

				File fileExtract = DocumentStorage.extract(lFile.getUriFile(), null);
				InputStream is = FileUtils.openInputStream(fileExtract);
				String lNomeFile = lFile.getNomeFile().contains("/") ? lFile.getNomeFile().replaceAll("/", "_") : lFile.getNomeFile();
				zip.putNextEntry(new ZipEntry(lNomeFile));

				int length;
				byte[] b = new byte[1024 * 8];

				while ((length = is.read(b)) > 0) {
					zip.write(b, 0, length);
				}

				zip.closeEntry();
				is.close();
			}

			zip.close();
			
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + " CREAZIONE FILE ZIP " + " timeOperation: " + ((t1 - t0)) + " ms");
			
			fileDaRestituire = fileToZip;
			
			StreamingOutput streamFile = buildStreamFile(new FileInputStream(fileDaRestituire));

			return Response.ok(streamFile)
					.header("content-disposition", "attachment; filename = files.zip").build();

		} catch (Exception e) {
			logger.error("AlboPretorio - scaricaZip: " + e.getMessage(), e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - scaricaZip: " + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - scaricaZip: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - "  + " TEMPO COMPLESSIVO SCARICA ZIP " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	/**
	 * 
	 *************************************************************************************************
	 * 	 SERVIZIO UGUALE A verificaFirma MA CON LA GESTIONE DEI FILE OTTIMIZATA (NON COME BASE64)	 *															  *
	 *************************************************************************************************
	 * 
	 * 
	 * Servizio per ottenere la certificazione delle firme apposte sul file 
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/checkFirma")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response checkFirma(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - checkFirma");

		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		String uriFile = dataInput.select("uriFile").text();
		String nomeFile = dataInput.select("nomeFile").text();
		String impronta = dataInput.select("impronta").text();
		String algoritmo = dataInput.select("algoritmo").text();
		String encoding = dataInput.select("encoding").text();

		File fileDaRestituire = null;
		
		long tSTART = System.currentTimeMillis();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stata richiesta la verifica della firma non è valorizzato. DataInput: " + dataInputXml);
			}

			long t0 = System.currentTimeMillis();
			File lFile = DocumentStorage.extract(uriFile, null);
			/* Performance logger */
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " EXTRACT DOCUMENT " + " timeOperation: " + ((t1 - t0)) + " ms");
			
			t0 = System.currentTimeMillis();
			File lFileCertificazione =  new FirmaUtility().creaFileCertificazione(lFile.toURI().toString(), nomeFile, false, null, null);
			t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " VERIFICA FIRMA FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
			
			fileDaRestituire = lFileCertificazione;
			
			StreamingOutput streamFile = buildStreamFile(new FileInputStream(fileDaRestituire));

			return Response.ok(streamFile)
					.header("content-disposition", "attachment; filename = " + nomeFile).build();

		} catch (Exception e) {
			logger.error("AlboPretorio - checkFirma:" + e.getMessage(), e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - checkFirma:" + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - checkFirma: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO CHECK FIRMA " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}		
	}
	
	/**
	 * 
	 *********************************************************************************************
	 * 	 SERVIZIO UGUALE A sbustaFile MA CON LA GESTIONE DEI FILE OTTIMIZATA (NON COME BASE64)	 *															  *
	 *********************************************************************************************
	 * 
	 * 
	 * Servizio per ottenere il file contenuto nella busta di trasporto
	 * Sbustamento file p7m
	 * 
	 * 
	 * @param dataInputXml
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/sbusta")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response sbusta(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - sbusta");

		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		String uriFile = dataInput.select("uriFile").text();
		String nomeFile = dataInput.select("nomeFile").text();
		
		InputStream is = null;
		
		long tSTART = System.currentTimeMillis();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stato richiesto lo sbustato non è valorizzato. DataInput: " + dataInputXml);
			}

			long t0 = System.currentTimeMillis();
			File lFile = DocumentStorage.extract(uriFile, null);
			/* Performance logger */
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " EXTRACT DOCUMENT " + " timeOperation: " + ((t1 - t0)) + " ms");

			File tempFile = File.createTempFile("tmp", "");

			t0 = System.currentTimeMillis();
			is = sbustaFileFirmato(lFile.toURI().toString(), nomeFile, tempFile);
			t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " SBUSTA FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");

			StreamingOutput streamFile = buildStreamFile(is);
			
			return Response.ok(streamFile)
					.header("content-disposition", "attachment; filename = " + nomeFile).build();
			
		} catch (Exception e) {
			logger.error("AlboPretorio - sbusta: " + e.getMessage(), e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - sbusta: " + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - sbusta: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO SBUSTA " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/getVersioneTimbrata")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getVersioneTimbrata(@FormDataParam("dataInput") String dataInputXml) throws Exception {
		logger.debug("call service AlboPretorio - getVersioneTimbrata");

		// Faccio il parse dell'xml in input al servizio
		Document dataInput = Jsoup.parse(dataInputXml, "", Parser.xmlParser());

		// recupero uri del file
		String uriFile = dataInput.select("uriFile").text();	
		// recupero il mimetype del file
		String mimeType = dataInput.select("mimetype").text();
		// recupero il nome del file
		String nomeFile = dataInput.select("nomeFile").text();
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = dataInput.select("flgConvertibile").text();			
		// recupero l'info se file è firmato
		String flgFirmato = dataInput.select("flgFirmato").text();
		// recupero l'idud
		String idUd = dataInput.select("idUd").text();
		// recupero l'iddoc
		String idDoc = dataInput.select("idDoc").text();
		
		long tSTART = System.currentTimeMillis();
		
		try {
			
			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file richiesto non è valorizzato. DataInput: " + dataInput);
			}

			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			
			lAlboPretorioTimbroWSConfigBean = (AlboPretorioTimbroWSConfigBean) SpringAppContext.getContext().getBean("AlboPretorioTimbroWSConfigBean");
			
			// recupero il file fisico
			long t0 = System.currentTimeMillis();
			File lFile = DocumentStorage.extract(uriFile, null);
			/* Performance logger */
			long t1 = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " EXTRACT DOCUMENT " + " timeOperation: " + ((t1 - t0)) + " ms");

			if (StringUtils.isNotBlank(flgFirmato) && "1".equalsIgnoreCase(flgFirmato)) {

				// se il file è un p7m faccio la preview dello sbustato
				String nomeFileSbustato = nomeFile.contains(".p7m") ? nomeFile.replace(".p7m", "") : nomeFile;
				File tempFile = File.createTempFile("tmp", "." + FilenameUtils.getExtension(nomeFileSbustato));

				InputStream is = null;

				// sbusto il file
				t0 = System.currentTimeMillis();
				is = sbustaFileFirmato(lFile.toURI().toString(), nomeFile, tempFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " SBUSTA FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");

				if (!"application/pdf".equals(mimeType)) {
					// se lo sbustato non è un pdf prima lo converto e poi restituisco il file
					File lFileConvert = File.createTempFile("tmpConvert", ".pdf");
					t0 = System.currentTimeMillis();
					FileUtils.copyInputStreamToFile(is, lFileConvert);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " COPIA INPUT STREAM PER CONVERSIONE " + " timeOperation: " + ((t1 - t0)) + " ms");
					
					t0 = System.currentTimeMillis();
					is = TimbraUtil.converti(lFileConvert, nomeFileSbustato);
					t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				}
				
				File timbraFileAlbo = timbraFileAlbo(loginBean, idUd, idDoc, is);
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAlbo));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();

			} else if (StringUtils.isNotBlank(flgConvertibile) && "1".equals(flgConvertibile)) {
				// se non è un pdf prima lo converto
				t0 = System.currentTimeMillis();
				InputStream is = TimbraUtil.converti(lFile, nomeFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				
				File timbraFileAlbo = timbraFileAlbo(loginBean, idUd, idDoc, is);
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAlbo));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			} else {
				File timbraFileAlbo = timbraFileAlbo(loginBean, idUd, idDoc, new FileInputStream(lFile));
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAlbo));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			}

		} catch (Exception e) {
			logger.error("AlboPretorio - getVersioneTimbrata: " + e.getMessage() + ". DataInput: " + dataInput, e);
//			return new AlboPretorioException().throwException("500", "AlboPretorio - recuperaFile: " + e.getMessage() + " - " + e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AlboPretorio - getVersioneTimbrata: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO GET VERSIONE TIMBRATA " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/test1")
	public Response test1(@Context HttpServletRequest request) throws Exception {

		File file = new File ("C:\\temp\\sbustaTest.pdf");
		
		InputStream is = new FileInputStream(file);
		
		StreamingOutput streamFile = buildStreamFile(is);

		return Response.ok(streamFile)
				.header("content-disposition", "attachment; filename = sbustaTest.pdf").build();			
	}

	private StreamingOutput buildStreamFile(final InputStream in) throws FileNotFoundException {
//		final InputStream in = new FileInputStream(in);
		StreamingOutput stream = new StreamingOutput() {
			public void write(OutputStream out) throws IOException, WebApplicationException {
				try {
					
					long t0 = System.currentTimeMillis();
					
					int read = 0;
					byte[] bytes = new byte[1024];

//					Writer writer = new BufferedWriter(new OutputStreamWriter(out));
//					while ((read = in.read()) != -1) {
//						writer.write(read);
//                    }
//					writer.flush();
					
					while ((read = in.read(bytes)) != -1) {
						out.write(bytes, 0, read);
//						out.flush();
					}
					out.flush();
					out.close();
					
					long t1 = System.currentTimeMillis();
					logger.debug("*Performance logger* - " + " DOWNLOAD STREAM FILE " + " timeOperation: " + ((t1 - t0)) + " ms");

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new WebApplicationException(e);
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
		};
		
		return stream;
	}

	private Object buildMultiPartResponse(File fileDaRestituire, String jsonResponseString) {
		Object output;
		
		FormDataMultiPart multi = new FormDataMultiPart();
		multi.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		
		// aggiungo response json
		if(StringUtils.isNotBlank(jsonResponseString)) {
			multi.bodyPart(new FormDataBodyPart(KEY_MULTIPART_JSON, jsonResponseString, MediaType.APPLICATION_JSON_TYPE));
		}
		
		// aggiungo il file	
		if(fileDaRestituire != null) {
			FileDataBodyPart fdbp = new FileDataBodyPart(KEY_MULTIPART_FILE, fileDaRestituire);
			multi.bodyPart(fdbp);
		}
		
		output = multi;
		
		return output;
	}
	
	
	private String calcolaImpronta(File strIn, String algoritmoImpronta) throws Exception {

		String hashOut = null;

		it.eng.auriga.repository2.util.Base64 encoder = new it.eng.auriga.repository2.util.Base64();

		// creo il digester
		java.security.MessageDigest md = java.security.MessageDigest.getInstance(algoritmoImpronta);
		md.update( FileUtils.readFileToByteArray(strIn));

		// calcolo lo sha-1
		byte[] digest = md.digest();
		// lo codifico base64
		hashOut = encoder.encode(digest);

		return hashOut;
	}
	
	//TODO da eliminare una volta che tutti gli Albi sono aggiornati con le nuove chiamate
	private void sbustaFileFirmatoOld(File p7m, File tempFile) throws IOException, Exception {

		InfoFileUtility lInfoFileUtility = new InfoFileUtility();		  			
		InputStream lInputStream = lInfoFileUtility.sbusta(p7m, p7m.getName());

		FileOutputStream fos = new FileOutputStream(tempFile);
		try {
			byte[] buff = new byte[1024];
			int bytesRead = 0;
			while((bytesRead = lInputStream.read(buff)) != -1) {
				fos.write(buff, 0, bytesRead);
			}
			fos.flush();
		} catch (Exception e) {	
			throw e;
		} finally {
			fos.close();
			lInputStream.close();
		}
	}
	
	private DmpkRegistrazionedocGettimbrodigregBean getSegnaturaStore(AurigaLoginBean loginBean, String idUD, String idDoc) throws Exception {
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(idUD));
		input.setIddocin(new BigDecimal(idDoc));
		input.setFinalitain("");

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(null, loginBean, input);

		if (result.isInError()) {
			logger.error("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
			throw new Exception("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
		}	

		return result.getResultBean();
	}
	
	private File timbraFileAlbo(AurigaLoginBean loginBean,  String idUd, String idDoc, InputStream is) throws Exception {
		
		try {
			DmpkRegistrazionedocGettimbrodigregBean result = getSegnaturaStore(loginBean, idUd, idDoc);
			if (result != null) {
				String contenutoBarcode = result.getContenutobarcodeout();
				String testoInChiaroBarcode = result.getTestoinchiaroout();
				
				File fileDaTimbrare = File.createTempFile("tmp", ".pdf");
				FileUtils.copyInputStreamToFile(is, fileDaTimbrare);
				
//				nomeFile = FilenameUtils.getBaseName(nomeFile) + ".pdf";
				
				File fileAgibilitaTimbrato = timbraFile(fileDaTimbrare, testoInChiaroBarcode, contenutoBarcode, lAlboPretorioTimbroWSConfigBean.getOpzioniTimbro());
				return fileAgibilitaTimbrato;
			} else {
				throw new Exception("Errore durante l'apposizione del timbro al file: ");
			}
			
		} catch (Exception e) {
			throw new Exception("Errore durante l'apposizione del timbro al file: " + e );
		}
	}
	
	private File timbraFile(File filedaTimbrare, String testoInChiaroBarcode, String contenutoBarcode,
			OpzioniCopertinaTimbroBean opzioniTimbro) throws Exception {
		try {
			TimbraUtil.impostaTestoOpzioniTimbro(opzioniTimbro, testoInChiaroBarcode, contenutoBarcode);
			InputStream fileTimbratoStream = TimbraUtil.timbra(filedaTimbrare, filedaTimbrare.getName(), opzioniTimbro,
					false);
			File fileTimbrato = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(fileTimbratoStream, fileTimbrato);
			return fileTimbrato;
		} catch (Exception e) {
			logger.error("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
			throw new Exception("Errore durante la timbratura del file: " + filedaTimbrare.getName() + "\n" + e.getMessage(), e);
		}

	}
	
}