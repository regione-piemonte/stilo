/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.resource.Singleton;

import fr.opensagres.xdocreport.document.json.JSONObject;
import io.swagger.annotations.Api;
import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetcontenutisezBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetdaticonttabellaBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetdettcontdoccomplessoBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspRegaccessosezcontfoBean;
import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspTrovacontenutifoBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocGettimbrodigregBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.client.DmpkAmmTraspGetcontenutisez;
import it.eng.client.DmpkAmmTraspGetdaticonttabella;
import it.eng.client.DmpkAmmTraspGetdettcontdoccomplesso;
import it.eng.client.DmpkAmmTraspRegaccessosezcontfo;
import it.eng.client.DmpkAmmTraspTrovacontenutifo;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.DmpkLoginLogin;
import it.eng.client.DmpkRegistrazionedocGettimbrodigreg;
import it.eng.document.function.StoreException;
import it.eng.document.function.bean.albopretorio.ListaBean;
import it.eng.document.function.bean.amministrazionetrasparente.AmministrazioneTrasparenteException;
import it.eng.document.function.bean.amministrazionetrasparente.AmministrazioneTrasparenteTimbroWSConfigBean;
import it.eng.document.function.bean.amministrazionetrasparente.DettaglioDatiFileBean;
import it.eng.document.function.bean.amministrazionetrasparente.DettaglioDocumentoComplessoOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.DettaglioSezioneOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.DettaglioTabellaOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.Esito;
import it.eng.document.function.bean.amministrazionetrasparente.RicercaResultBean;
import it.eng.document.function.bean.amministrazionetrasparente.SezioneTreeBean;
import it.eng.document.function.bean.amministrazionetrasparente.XmlConteggiXSezioneOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.XmlContenutiOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.XmlContenutiRicercaResultOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.XmlInfoStrutturaTabOutBean;
import it.eng.document.function.bean.amministrazionetrasparente.XmlSezioneOutBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FirmaUtility;
import it.eng.utility.TimbraUtil;
import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;
import it.eng.xml.XmlListaUtility;

@Singleton
@Api
@Path("/ammnistrazionetrasparente")
public class AmministrazioneTrasparenteController {
	
	@Context
	ServletContext context;

	@Context
	HttpServletRequest request;

	private static final Logger logger = Logger.getLogger(AmministrazioneTrasparenteController.class);
	
	private static final String TOKEN_AURIGALOGIN = "token";
	private static final String SCHEMA_AURIGALOGIN = "schema";
	private static final String ID_USER_LAVORO_AURIGALOGIN = "idUserLavoro";
	private static final String STORE_ERROR = "599";
	
	private static final String KEY_MULTIPART_JSON = "jsonResponse";
	private static final String KEY_MULTIPART_FILE = "fileResponse";
	
	private static String searchFullText = null;
	
	protected static AmministrazioneTrasparenteTimbroWSConfigBean lAmministrazioneTrasparenteTimbroWSConfigBean = null;

	@POST
	@Path("/testService")
	public Response testService(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - testService");
		List<XmlContenutiOutBean> listaContenutiOut = new ArrayList<XmlContenutiOutBean>();
		DettaglioSezioneOutBean result = new DettaglioSezioneOutBean ();
		Esito esito = new Esito();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			loginBean.setToken("#RESERVED_TA_35");
			loginBean.setSchema("COLL_MAURIZIANO");
			DmpkAmmTraspGetcontenutisezBean inputBean = new DmpkAmmTraspGetcontenutisezBean();
			inputBean.setIdsezionein(new BigDecimal(30));
			DmpkAmmTraspGetcontenutisez store = new DmpkAmmTraspGetcontenutisez();
			
			StoreResultBean<DmpkAmmTraspGetcontenutisezBean> lStoreResultBean = store
					.execute(null, loginBean, inputBean);
			
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			if (lStoreResultBean.getResultBean().getListacontenutiout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListacontenutiout();
				listaContenutiOut = XmlListaUtility.recuperaLista(xmlLista, XmlContenutiOutBean.class);
			}
			esito.setValore("ACK");
		} catch (StoreException e) {
			logger.error("AmmTrasp - testService: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - testService: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		result.setEsito(esito);
		result.setListaContenuti(listaContenutiOut);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(result, DettaglioSezioneOutBean.class);

		return Response.ok(res).build();

	}
	
	@POST
	@Path("/getSezioni")
	public Response getSezioni(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getSezioni");

		List<XmlSezioneOutBean> listaSezioni = new ArrayList<>();
		List<SezioneTreeBean> listaSezioniTree = new ArrayList<>();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);

			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("SEZIONI_AMM_TRASPARENTE");

			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo
					.execute(null, loginBean, lDmpkLoadComboDmfn_load_comboBean);

			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			if (lStoreResultBean.getResultBean().getListaxmlout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				listaSezioni = XmlListaUtility.recuperaLista(xmlLista, XmlSezioneOutBean.class);
				
				listaSezioniTree = buidAlberatura(listaSezioni);
			}

		} catch (StoreException e) {
			logger.error("AmmTrasp - getSezioni: " + e.getMessage(), e);
			return new AmministrazioneTrasparenteException().throwException(STORE_ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("AmmTrasp - getSezioni: " + e.getMessage(), e);
			return new AmministrazioneTrasparenteException().throwException("500", "AmministrazioneTrasparente - getSezioni: " + e.getMessage() + " - " + e);
		}
		
		ListaBean<SezioneTreeBean> list = new ListaBean<>(listaSezioniTree);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(list, ListaBean.class);

		return Response.ok(res).build();
	}
	
	@POST
	@Path("/autenticazione/login")
	public Response login(@RequestBody String authorization) throws Exception {
		logger.debug("Inizio servizio login REST POSTs " + authorization);
		AurigaLoginBean login = new AurigaLoginBean();
		AurigaLoginBean loginOut = new AurigaLoginBean();
		Esito esito = new Esito();
		HttpHeaders responseHeaders = new HttpHeaders();
		Locale locale = new Locale("it", "IT");
		login.setLinguaApplicazione("it");
		login.setSchema(request.getHeader(SCHEMA_AURIGALOGIN));
		
		try {

//			if (!authorization.startsWith("Basic")) {
//				throw new Exception("header di autenticazione malformato");
//			}
			
			DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
			String[] splitStrs = authorization.split(" ");
			byte[] decoded = Base64.getDecoder().decode(authorization);
			String usrPwd = new String(decoded);
			String[] tokenUser = usrPwd.split(":");
			String usernameDominio = tokenUser[0];
			String passwordDominio = "";
			if(tokenUser.length>1 && tokenUser[1] != null) {
				passwordDominio = tokenUser[1];
			}
//			String[] tokenUserDominio = usernameDominio.split("/");
			lLoginInput.setUsernamein(usernameDominio);
			lLoginInput.setPasswordin(passwordDominio);
			lLoginInput.setFlgnoctrlpasswordin(1);
			lLoginInput.setFlgautocommitin(1);
			lLoginInput.setFlgrollbckfullin(0);

			DmpkLoginLogin lLogin = new DmpkLoginLogin();

			StoreResultBean<DmpkLoginLoginBean> lLoginOutput = lLogin.execute(locale, login, lLoginInput);

			if (lLoginOutput.isInError()) {
				throw new Exception(lLoginOutput.getDefaultMessage());
			}

//			DmpkLoginLoginBean lLoginBean = lLoginOutput.getResultBean();

			login.setToken(lLoginOutput.getResultBean().getCodidconnectiontokenout());
			SpecializzazioneBean specializzazioneBean  =new SpecializzazioneBean();
			specializzazioneBean.setIdDominio(lLoginOutput.getResultBean().getIddominioautio());
			login.setSpecializzazioneBean(specializzazioneBean);
			
		} catch (StoreException e) {
			logger.error("AmmTrasp - login: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
		} catch (Exception e) {
			logger.error("AmmTrasp - login: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
		}
		
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(login, AurigaLoginBean.class);

		return Response.ok(res).build();
	}
	
	@POST
	@Path("/sezione/getContenuto")
	public Response getContenutoSezione(@RequestBody String input) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getContenutoSezione");
		List<XmlContenutiOutBean> listaContenutiOut = new LinkedList<XmlContenutiOutBean>();
		DettaglioSezioneOutBean result = new DettaglioSezioneOutBean ();
		Esito esito = new Esito();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			DmpkAmmTraspGetcontenutisezBean inputBean = new DmpkAmmTraspGetcontenutisezBean();
			inputBean.setIdsezionein(new BigDecimal(input));
			DmpkAmmTraspGetcontenutisez store = new DmpkAmmTraspGetcontenutisez();
			
			StoreResultBean<DmpkAmmTraspGetcontenutisezBean> lStoreResultBean = store
					.execute(null, loginBean, inputBean);
			
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}

			if (lStoreResultBean.getResultBean().getListacontenutiout() != null) {
				String xmlLista = lStoreResultBean.getResultBean().getListacontenutiout();
				listaContenutiOut = XmlListaUtility.recuperaLista(xmlLista, XmlContenutiOutBean.class);
				aggiornaContenutiConPaginaDedicata(listaContenutiOut);
				result.setListaContenuti(listaContenutiOut);
			}
			buildDettaglioSezione(result, lStoreResultBean.getResultBean());
			esito.setValore("ACK");
		} catch (StoreException e) {
			logger.error("AmmTrasp - getContenutoSezione: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - getContenutoSezione: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		result.setEsito(esito);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(result, DettaglioSezioneOutBean.class);
		logger.debug("fine service AmministrazioneTrasparente - getContenutoSezione");
		return Response.ok(res).build();
	}
	
	@POST
	@Path("/docComplesso/getDettaglioDocumentoComplesso")
	public Response getDettaglioDocumentoComplesso(@RequestBody String input) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getDettaglioDocumentoComplesso");
		DettaglioDocumentoComplessoOutBean result = new DettaglioDocumentoComplessoOutBean();
		List<DettaglioDatiFileBean> listaFileAllegati = new LinkedList<DettaglioDatiFileBean>();
		List<DettaglioDatiFileBean> listaFilePrimario = new LinkedList<DettaglioDatiFileBean>();
		Esito esito = new Esito();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			DmpkAmmTraspGetdettcontdoccomplessoBean inputBean = new DmpkAmmTraspGetdettcontdoccomplessoBean ();
			inputBean.setIdcontenutosezin(new BigDecimal(input));
			DmpkAmmTraspGetdettcontdoccomplesso store = new DmpkAmmTraspGetdettcontdoccomplesso();
			StoreResultBean<DmpkAmmTraspGetdettcontdoccomplessoBean> lStoreResultBean = store.execute(null, loginBean, inputBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}
			if (lStoreResultBean.getResultBean().getDatifileprimarioout() != null) {
				String xmlFilePrimario = lStoreResultBean.getResultBean().getDatifileprimarioout();
				listaFilePrimario = XmlListaUtility.recuperaLista(xmlFilePrimario, DettaglioDatiFileBean.class);;
				result.setDatiFilePrimario(listaFilePrimario);
			}
			if (lStoreResultBean.getResultBean().getDatifileallegatiout() != null) {
				String xmlFileAllegati = lStoreResultBean.getResultBean().getDatifileallegatiout();
				listaFileAllegati = XmlListaUtility.recuperaLista(xmlFileAllegati, DettaglioDatiFileBean.class);
				result.setDatiFileAllegati(listaFileAllegati);
			}
			buildDettaglioDocumentoComplesso(result, lStoreResultBean.getResultBean());
			esito.setValore("ACK");
		} catch (StoreException e) {
			logger.error("AmmTrasp - getDettaglioDocumentoComplesso: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - getDettaglioDocumentoComplesso: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		result.setEsito(esito);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(result, DettaglioDocumentoComplessoOutBean.class);
		logger.debug("fine service AmministrazioneTrasparente - getDettaglioDocumentoComplesso");
		return Response.ok(res).build();
	}
	
	@POST
	@Path("/docComplesso/getDettaglioTabella")
	public Response getDettaglioTabella(@RequestBody String inputJson) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getDettaglioTabella");
		DettaglioTabellaOutBean result = new DettaglioTabellaOutBean();
		List<XmlInfoStrutturaTabOutBean> listaInfoStrutturaTab = new LinkedList<XmlInfoStrutturaTabOutBean>();
		Esito esito = new Esito();
		
		JSONObject obj = new JSONObject(inputJson);

		// (obblig.) Id. del contenuto di tipo 
		String idContenutoSezIn = (String) obj.get("idContenutoSezIn");	
		// Nro di record per pagina. Se non valorizzato la lista dei record NON viene paginata
		String nroRecXPaginaIn = (String) obj.get("nroRecXPaginaIn");
		// Pagina di cui estrarre i dati. Se NULL nessun dato viene estratto (ovvero ValoriRecTabOut sar� vuoto)
		String nroPaginaIO = (String) obj.get("nroPaginaIO");
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			DmpkAmmTraspGetdaticonttabellaBean inputBean = new DmpkAmmTraspGetdaticonttabellaBean();
			inputBean.setIdcontenutosezin(new BigDecimal(idContenutoSezIn));
			inputBean.setNrorecxpaginain((nroRecXPaginaIn != null && !nroRecXPaginaIn.trim().equals("")) ? new Integer(nroRecXPaginaIn) : null);
			inputBean.setNropaginaio((nroPaginaIO != null && !nroPaginaIO.trim().equals("")) ? new Integer(nroPaginaIO) : null);
			DmpkAmmTraspGetdaticonttabella store = new DmpkAmmTraspGetdaticonttabella();
			StoreResultBean<DmpkAmmTraspGetdaticonttabellaBean> lStoreResultBean = store.execute(null, loginBean, inputBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}
			if (lStoreResultBean.getResultBean().getInfostrutturatabout() != null) {
				String xmlInfostrutturatabout = lStoreResultBean.getResultBean().getInfostrutturatabout();
				listaInfoStrutturaTab = XmlListaUtility.recuperaLista(xmlInfostrutturatabout, XmlInfoStrutturaTabOutBean.class);;
				result.setInfoStrutturaTab(listaInfoStrutturaTab);
			}
			if (lStoreResultBean.getResultBean().getValorirectabout() != null) {
				String xmlValorirectabout = lStoreResultBean.getResultBean().getValorirectabout();
				LinkedHashMap<Integer, LinkedList<String>> mappaCelleTab = getMappaCelleTab(xmlValorirectabout, listaInfoStrutturaTab.size());
				result.setValoriRecTab(mappaCelleTab);
			}
			
			buildDettaglioTabella(result, lStoreResultBean.getResultBean());
			esito.setValore("ACK");
		} catch (StoreException e) {
			logger.error("AmmTrasp - getDettaglioTabella: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - getDettaglioTabella: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		result.setEsito(esito);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(result, DettaglioTabellaOutBean.class);
		logger.debug("fine service AmministrazioneTrasparente - getDettaglioTabella");
		return Response.ok(res).build();
	}
	
	private LinkedHashMap<Integer, LinkedList<String>> getMappaCelleTab (String xmlInput, int numeroColonneIntestazioni) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder; 
		LinkedHashMap<Integer, LinkedList<String>> mappa = new LinkedHashMap<Integer, LinkedList<String>>();
		int numeroRiga = 0;
		try {  
			builder = factory.newDocumentBuilder();  
			Document document = builder.parse(new InputSource(new StringReader(xmlInput))); 
			document.getDocumentElement().normalize();

			NodeList nList = document.getElementsByTagName("Lista");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node lista = nList.item(temp);

				NodeList righe = lista.getChildNodes();
				if (righe.getLength()>0) {
					for (int i = 0; i < righe.getLength(); i++) {
						LinkedList<String> celleTab = new LinkedList<String>();
						Node riga = righe.item(i);
						
						NodeList colonne = riga.getChildNodes();
						int count = 0;
						for (int j = 0; j < numeroColonneIntestazioni; j++) {
							Node colonna = colonne.item(j);
							if (colonna != null) {
								try {
									Element eElement = (Element) colonna;
									String nroColonna = eElement.getAttribute("Nro");
									do {
										if (!nroColonna.equals(""+(count+1))) {
											celleTab.add("");
											count++;
										}
									} while (!nroColonna.equals(""+(count+1)));
									if (eElement.getTextContent().contains("<Riga") || eElement.getTextContent().contains("<Colonna")) {
										String xmlFileAllegati = eElement.getTextContent();
										List<DettaglioDatiFileBean> tempList = XmlListaUtility.recuperaLista(xmlFileAllegati, DettaglioDatiFileBean.class);
										String json = new Gson().toJson(tempList);
										celleTab.add(json);
									} else if (eElement.getTextContent().contains("<Lista></Lista>")){
										celleTab.add("");
									} else {
										String textContent = eElement.getTextContent();
										celleTab.add(textContent);
									}
								} catch (Exception e) {
								}
							} else if (count < numeroColonneIntestazioni){
								celleTab.add("");
							}
							count++;
						}
						if (celleTab.size() > 0) {
							Node colonnaIdRiga = colonne.item(colonne.getLength()-1);
							try {
								Element eElement = (Element) colonnaIdRiga;
								String idRiga = eElement.getTextContent();
								if(!celleTab.get(celleTab.size()-1).equalsIgnoreCase(idRiga)) {
									celleTab.add(idRiga);
								}
							} catch (Exception e) {
							}
							mappa.put(numeroRiga, celleTab);
							numeroRiga++;
						}
					}
				}
			}
		} catch (Exception e) {  
		    e.printStackTrace();  
		}
		return mappa;
	}
	
	
	@POST
	@Path("/storage/getFile")
	public Response recuperaFile(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getFile");

		JSONObject obj = new JSONObject(dataInput);

		// recupero uri del file
		String uriFile = (String) obj.get("uriFile");	
		// recupero il mimetype del file
		String mimeType = (String) obj.get("mimetype");
		// recupero il nome del file
		String nomeFile = (String) obj.get("nomeFile");
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = (String) obj.get("flagIsOrMustBeFilePdf").toString();
		// recupero l'info se file è firmato
		String flgFirmato = (String) obj.get("flagFileFirmato").toString();

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
			logger.error("AmministrazioneTrasparente - getFile: " + e.getMessage() + ". DataInput: " + dataInput, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AmministrazioneTrasparente - getFile: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		} finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO RECUPERA FILE " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/storage/checkFirma")
	public Response checkFirma(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - checkFirma");

		JSONObject obj = new JSONObject(dataInput);

		// recupero uri del file
		String uriFile = (String) obj.get("uriFile");	
		// recupero il nome del file
		String nomeFile = (String) obj.get("nomeFile");

		File fileDaRestituire = null;
		
		long tSTART = System.currentTimeMillis();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stata richiesta la verifica della firma non è valorizzato. DataInput: " + dataInput);
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
			logger.error("AmministrazioneTrasparente - checkFirma:" + e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AmministrazioneTrasparente - checkFirma: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		} finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO CHECK FIRMA " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/storage/sbustaFile")
	public Response sbustaFile(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - sbustaFile");
		
		JSONObject obj = new JSONObject(dataInput);
		
		// recupero uri del file
		String uriFile = (String) obj.get("uriFile");	
		// recupero il nome del file
		String nomeFile = (String) obj.get("nomeFile");
		
		InputStream is = null;
		
		long tSTART = System.currentTimeMillis();

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file di cui è stato richiesto lo sbustato non è valorizzato. DataInput: " + dataInput);
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
			logger.error("AmministrazioneTrasparente - sbustaFile: " + e.getMessage(), e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AmministrazioneTrasparente - sbustaFile: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		} finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO SBUSTA FILE" + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/storage/getVersioneTimbrata")
	public Response getVersioneTimbrata(@RequestBody String dataInput) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - getVersioneTimbrata");
		
		JSONObject obj = new JSONObject(dataInput);
		
		// recupero uri del file
		String uriFile = (String) obj.get("uriFile");	
		// recupero il nome del file
		String nomeFile = (String) obj.get("nomeFile");	
		// recupero il mimetype del file
		String mimeType = (String) obj.get("mimetype");
		// recupero l'info se file è convertibile in PDF oppure no
		String flgConvertibile = (String) obj.get("flagIsOrMustBeFilePdf").toString();
		// recupero l'info se file è firmato
		String flgFirmato = (String) obj.get("flagFileFirmato").toString();
		// recupero l'idud
		String idUd = (String) obj.get("idUd");
		// recupero l'iddoc
		String idDoc = (String) obj.get("idDocFile");
		
		// Verficare se l'indicazione dell'ID_UD è mandatoria per DmpkRegistrazionedocGettimbrodigreg
		
		long tSTART = System.currentTimeMillis();
		
		try {
			
			if (StringUtils.isBlank(uriFile)) {
				throw new Exception("Uri del file richiesto non è valorizzato. DataInput: " + dataInput);
			}

			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			
			lAmministrazioneTrasparenteTimbroWSConfigBean = (AmministrazioneTrasparenteTimbroWSConfigBean) SpringAppContext.getContext().getBean("AmministrazioneTrasparenteTimbroWSConfigBean");
			
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
				
				File timbraFileAmministrazioneTrasparente = timbraFileAmmTrasp(loginBean, idUd, idDoc, is);
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAmministrazioneTrasparente));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();

			} else if (StringUtils.isNotBlank(flgConvertibile) && "1".equals(flgConvertibile)) {
				// se non è un pdf prima lo converto
				t0 = System.currentTimeMillis();
				InputStream is = TimbraUtil.converti(lFile, nomeFile);
				t1 = System.currentTimeMillis();
				logger.debug("*Performance logger* - " + nomeFile  + " - " + " CONVERTI FILEOP " + " timeOperation: " + ((t1 - t0)) + " ms");
				
				File timbraFileAmministrazioneTrasparente = timbraFileAmmTrasp(loginBean, idUd, idDoc, is);
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAmministrazioneTrasparente));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			} else {
				File timbraFileAmministrazioneTrasparente = timbraFileAmmTrasp(loginBean, idUd, idDoc, new FileInputStream(lFile));
				
				StreamingOutput streamFile = buildStreamFile(new FileInputStream(timbraFileAmministrazioneTrasparente));
				return Response.ok(streamFile).header("content-disposition", "attachment; filename = file").build();
			}

		} catch (Exception e) {
			logger.error("AmministrazioneTrasparente - getVersioneTimbrata: " + e.getMessage() + ". DataInput: " + dataInput, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.header("storeException", "false")
					.entity("AmministrazioneTrasparente - getVersioneTimbrata: " + e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}finally {
			long tEND = System.currentTimeMillis();
			logger.debug("*Performance logger* - " + nomeFile  + " - " + " TEMPO COMPLESSIVO GET VERSIONE TIMBRATA " + " timeOperation: " + ((tEND - tSTART)) + " ms");
		}
	}
	
	@POST
	@Path("/regAccessoSezCont")
	public Response regAccessoSezCont(@RequestBody String input) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - regAccessoSezCont");
	
		JSONObject obj = new JSONObject(input);
		
		String idClient = (String) obj.get("idClient");	
		String idSezione = null;
		if (obj.get("idSezione")!=null && !"".equals(obj.get("idSezione"))) {
			idSezione = (String) obj.get("idSezione");
		}	
		String idContenutoSez = null;
		if (obj.get("idContenutoSez")!=null && !"".equals(obj.get("idContenutoSez"))) {
			idContenutoSez = (String) obj.get("idContenutoSez");
		}
		Esito esito = new Esito();
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			SchemaBean schemaBean = new SchemaBean();
			schemaBean.setSchema(loginBean.getSchema());
			DmpkAmmTraspRegaccessosezcontfoBean inputBean = new DmpkAmmTraspRegaccessosezcontfoBean();
			inputBean.setIdclientin(idClient);
			inputBean.setIdsezionein(idSezione!= null? new BigDecimal(idSezione): null);
			inputBean.setIdcontenutosezin(idContenutoSez!=null? new BigDecimal(idContenutoSez): null);
			
			DmpkAmmTraspRegaccessosezcontfo store = new DmpkAmmTraspRegaccessosezcontfo();
			StoreResultBean<DmpkAmmTraspRegaccessosezcontfoBean> lStoreResultBean = store.execute(null, schemaBean, inputBean);
			
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}
			
			esito.setValore("ACK");
		} catch (StoreException e) {
			logger.error("AmmTrasp - regAccessoSezCont: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - regAccessoSezCont: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(esito, Esito.class);
		logger.debug("fine service AmministrazioneTrasparente - regAccessoSezCont");
		return Response.ok(res).build();
	}
	
	
	@POST
	@Path("/search")
	public Response search(@RequestBody String input) throws Exception {
		logger.debug("call service AmministrazioneTrasparente - search");
		JSONObject obj = new JSONObject(input);
		RicercaResultBean result = new RicercaResultBean();
		List<XmlConteggiXSezioneOutBean> listaXmlConteggiXSezione = new LinkedList<XmlConteggiXSezioneOutBean>();
		List<XmlContenutiRicercaResultOutBean> listaXmlContenutiRicercaResult = new LinkedList<XmlContenutiRicercaResultOutBean>();
		Esito esito = new Esito();
		String matchWordListIn = "";
		if (obj.get("matchWordListIn")!=null && !"".equals(obj.get("matchWordListIn"))) {
			matchWordListIn = (String) obj.get("matchWordListIn");
		}
		String idSezioneIn = null;
		if (obj.get("idSezioneIn")!=null && !"".equals(obj.get("idSezioneIn"))) {
			idSezioneIn = (String) obj.get("idSezioneIn");
		}
		String livSezioniConteggioIn = null;
		if (obj.get("livSezioniConteggioIn")!=null && !"".equals(obj.get("livSezioniConteggioIn"))) {
			livSezioniConteggioIn = (String) obj.get("livSezioniConteggioIn");
		}
		Integer nropaginaio = null;
		if (obj.get("nropaginaio")!=null && !"".equals(obj.get("nropaginaio"))) {
			nropaginaio = (Integer) obj.get("nropaginaio");
		}
		
		try {
			AurigaLoginBean loginBean = getAurigaLoginBean(request);
			DmpkAmmTraspTrovacontenutifoBean inputBean = new DmpkAmmTraspTrovacontenutifoBean();
			inputBean.setCodidconnectiontokenin(loginBean.getToken());
			inputBean.setMatchwordlistin(matchWordListIn);
			inputBean.setIdsezionein(idSezioneIn!= null? new BigDecimal(idSezioneIn): null);
			inputBean.setLivsezioniconteggioin(livSezioniConteggioIn!= null? new BigDecimal(livSezioniConteggioIn): null);
			inputBean.setNropaginaio(nropaginaio!= null? nropaginaio: null);
//			inputBean.setPagesizeio(50);
			
			DmpkAmmTraspTrovacontenutifo store = new DmpkAmmTraspTrovacontenutifo();
			StoreResultBean<DmpkAmmTraspTrovacontenutifoBean> lStoreResultBean = store.execute(null, loginBean, inputBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean.getDefaultMessage());
			}
			if (lStoreResultBean.getResultBean()!=null) {
				result.setNroPag(lStoreResultBean.getResultBean().getNropaginaio() != null
						? lStoreResultBean.getResultBean().getNropaginaio().toString()
						: "");
				result.setPagSize(lStoreResultBean.getResultBean().getPagesizeio() != null
						? lStoreResultBean.getResultBean().getPagesizeio().toString()
						: "");
				result.setNroRecTot(lStoreResultBean.getResultBean().getNrototrecout() != null
						? lStoreResultBean.getResultBean().getNrototrecout().toString()
						: "");
				result.setPagRecInPag(lStoreResultBean.getResultBean().getNrorecinpaginaout() != null
						? lStoreResultBean.getResultBean().getNrorecinpaginaout().toString()
						: "");
				if (lStoreResultBean.getResultBean().getConteggixsezioneout() != null) {
					String xmlConteggixsezioneout = lStoreResultBean.getResultBean().getConteggixsezioneout();
					listaXmlConteggiXSezione = XmlListaUtility.recuperaLista(xmlConteggixsezioneout,
							XmlConteggiXSezioneOutBean.class);
					result.setListaConteggiXSezione(listaXmlConteggiXSezione);
				}
				if (lStoreResultBean.getResultBean().getResultout() != null) {
					String xmlResultout = lStoreResultBean.getResultBean().getResultout();
					listaXmlContenutiRicercaResult = XmlListaUtility.recuperaLista(xmlResultout,
							XmlContenutiRicercaResultOutBean.class);
					result.setListaContenutoFromRicerca(listaXmlContenutiRicercaResult);
				} 
//				result.setNroRecTot("5");
//				String xmlCont = "<Lista>\r\n" + 
//						"<Riga><Colonna Nro=\"1\">852</Colonna><Colonna Nro=\"2\">paragrafo</Colonna><Colonna Nro=\"4\">&lt;p&gt;SEZIONE FLAT&lt;/p&gt;\r\n" + 
//						"</Colonna><Colonna Nro=\"5\">1</Colonna><Colonna Nro=\"6\">&lt;html&gt;Contenuto inserito in data &lt;b&gt;08/11/2021&lt;/b&gt; alle ore 09:58:53.&lt;/html&gt;</Colonna><Colonna Nro=\"25\">31</Colonna><Colonna Nro=\"26\">Atti Generali</Colonna></Riga><Riga><Colonna Nro=\"1\">1161</Colonna><Colonna Nro=\"2\">tabella</Colonna><Colonna Nro=\"3\">Enti pubblici vigilati</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"7\">3</Colonna><Colonna Nro=\"24\">0</Colonna><Colonna Nro=\"25\">29</Colonna><Colonna Nro=\"26\">ALTRI CONTENUTI - DATI ULTERIORI</Colonna></Riga><Riga><Colonna Nro=\"1\">855</Colonna><Colonna Nro=\"2\">paragrafo</Colonna><Colonna Nro=\"4\">&lt;p&gt;Contenuto terzo livello&lt;/p&gt;\r\n" + 
//						"</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"24\">0</Colonna><Colonna Nro=\"25\">29</Colonna><Colonna Nro=\"26\">ALTRI CONTENUTI - DATI ULTERIORI</Colonna></Riga><Riga><Colonna Nro=\"1\">1172</Colonna><Colonna Nro=\"2\">tabella</Colonna><Colonna Nro=\"3\">test tabella terzo livello</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"7\">2</Colonna><Colonna Nro=\"24\">0</Colonna><Colonna Nro=\"25\">1</Colonna><Colonna Nro=\"26\">DISPOSIZIONI GENERALI</Colonna></Riga><Riga><Colonna Nro=\"1\">928</Colonna><Colonna Nro=\"2\">tabella</Colonna><Colonna Nro=\"3\">test antonio</Colonna><Colonna Nro=\"5\">0</Colonna><Colonna Nro=\"7\">0</Colonna><Colonna Nro=\"24\">0</Colonna><Colonna Nro=\"25\">29</Colonna><Colonna Nro=\"26\">ALTRI CONTENUTI - DATI ULTERIORI</Colonna></Riga></Lista>";
//				listaXmlContenutiRicercaResult = XmlListaUtility.recuperaLista(xmlCont,
//						XmlContenutiRicercaResultOutBean.class);
//				result.setListaContenutoFromRicerca(listaXmlContenutiRicercaResult);
//				
//				String xmlConteggi = "<Lista>\r\n" + 
//						"<Riga><Colonna Nro=\"1\">31</Colonna><Colonna Nro=\"2\">1</Colonna><Colonna Nro=\"3\">Atti Generali</Colonna><Colonna Nro=\"4\">DISPOSIZIONE GENERALI</Colonna><Colonna Nro=\"5\">1</Colonna></Riga>\r\n" + 
//						"<Riga><Colonna Nro=\"1\">29</Colonna><Colonna Nro=\"2\"></Colonna><Colonna Nro=\"3\">ALTRI CONTENUTI - DATI ULTERIORI</Colonna><Colonna Nro=\"4\"></Colonna><Colonna Nro=\"5\">3</Colonna></Riga>\r\n" + 
//						"<Riga><Colonna Nro=\"1\">1</Colonna><Colonna Nro=\"2\"></Colonna><Colonna Nro=\"3\">DISPOSIZIONI GENERALI</Colonna><Colonna Nro=\"4\"></Colonna><Colonna Nro=\"5\">1</Colonna></Riga>\r\n" + 
//						"</Lista>";
//				listaXmlConteggiXSezione = XmlListaUtility.recuperaLista(xmlConteggi,
//						XmlConteggiXSezioneOutBean.class);
//				result.setListaConteggiXSezione(listaXmlConteggiXSezione);
				
				esito.setValore("ACK");
			}
		} catch (StoreException e) {
			logger.error("AmmTrasp - search: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		} catch (Exception e) {
			logger.error("AmmTrasp - search: " + e.getMessage(), e);
			esito.setMessaggio(e.getMessage());
			esito.setValore("KO");
		}
		result.setEsito(esito);
		GsonBuilder builder = new GsonBuilder();
		String res = builder.create().toJson(result, RicercaResultBean.class);
		logger.debug("fine service AmministrazioneTrasparente - search");
		return Response.ok(res).build();
	}
	
	private File timbraFileAmmTrasp(AurigaLoginBean loginBean, String idUd, String idDoc, InputStream is) throws Exception {
		
		try {
			DmpkRegistrazionedocGettimbrodigregBean result = getSegnaturaStore(loginBean, idUd, idDoc);
			if (result != null) {
				String contenutoBarcode = result.getContenutobarcodeout();
				String testoInChiaroBarcode = result.getTestoinchiaroout();
				
				File fileDaTimbrare = File.createTempFile("tmp", ".pdf");
				FileUtils.copyInputStreamToFile(is, fileDaTimbrare);
				
				File fileAgibilitaTimbrato = timbraFile(fileDaTimbrare, testoInChiaroBarcode, contenutoBarcode, lAmministrazioneTrasparenteTimbroWSConfigBean.getOpzioniTimbro());
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
	
	private DmpkRegistrazionedocGettimbrodigregBean getSegnaturaStore(AurigaLoginBean loginBean, String idUd, String idDoc) throws Exception {
		DmpkRegistrazionedocGettimbrodigregBean input = new DmpkRegistrazionedocGettimbrodigregBean();
		input.setIdudio(new BigDecimal(idUd));
		input.setIddocin(new BigDecimal(idDoc));
		input.setFinalitain("VERS_STAMPA_PUBBLICAZIONE");

		DmpkRegistrazionedocGettimbrodigreg store = new DmpkRegistrazionedocGettimbrodigreg();
		StoreResultBean<DmpkRegistrazionedocGettimbrodigregBean> result = store.execute(null, loginBean, input);

		if (result.isInError()) {
			logger.error("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
			throw new Exception("Errore durante il recupero della segnatura del timbro: " + result.getDefaultMessage());
		}	

		return result.getResultBean();
	}
	
	private AurigaLoginBean getAurigaLoginBean(HttpServletRequest request) {
		AurigaLoginBean aurigaLoginBean = new AurigaLoginBean();
		// recupero il token
		aurigaLoginBean.setToken(request.getHeader(TOKEN_AURIGALOGIN));
		// recupero lo schema
		aurigaLoginBean.setSchema(request.getHeader(SCHEMA_AURIGALOGIN));

		logger.debug("AmministrazioneTrasparente - Recupero parametri di autenticazione: Token - " + aurigaLoginBean.getToken()
		+ "; Schema - " + aurigaLoginBean.getSchema());

		return aurigaLoginBean;
	}
	
	private void buildDettaglioSezione (DettaglioSezioneOutBean dettaglio, DmpkAmmTraspGetcontenutisezBean storeResult) {
		dettaglio.setDatiagg(storeResult.getDatiaggout());
		dettaglio.setFlgpresenteheader(storeResult.getFlgpresenteheaderout());
		dettaglio.setFlgpresentirifnorm(storeResult.getFlgpresentirifnormout());
		dettaglio.setHeader(storeResult.getHeaderout());
		dettaglio.setIdsezione(storeResult.getIdsezionein());
		dettaglio.setNrocontenuti(storeResult.getNrocontenutiout());
		dettaglio.setRifnormativi(storeResult.getRifnormativiout());
	}
	
	private void buildDettaglioDocumentoComplesso (DettaglioDocumentoComplessoOutBean dettaglio, DmpkAmmTraspGetdettcontdoccomplessoBean storeResult) {
		dettaglio.setDatiAgg(storeResult.getDatiaggout());
		dettaglio.setFlgFilePrimario(storeResult.getFlgfileprimarioout());
		dettaglio.setFlgInfoDettUguali(storeResult.getFlginfodettugualiout());
		dettaglio.setFlgPresentiInfoInDett(storeResult.getFlgpresentiinfodettout());
		dettaglio.setInfoDettInDettaglio(storeResult.getInfodettindettaglioout());
		dettaglio.setInfoDettInSez(storeResult.getInfodettinsezout());
		dettaglio.setNroFileAllegati(storeResult.getNrofileallegatiout());
		dettaglio.setTitolo(storeResult.getTitoloout());
	}
	
	private void buildDettaglioTabella (DettaglioTabellaOutBean tabella, DmpkAmmTraspGetdaticonttabellaBean storeResult) {
		tabella.setDatiAgg(storeResult.getDatiaggout());
		tabella.setFlgInfoDettUguali(""+storeResult.getFlginfodettugualiout());
		tabella.setFlgPresentiInfoInDett(""+storeResult.getFlgpresentiinfodettout());
		tabella.setInfoDettInDettaglio(storeResult.getInfodettindettaglioout());
		tabella.setInfoDettInSez(storeResult.getInfodettinsezout());
		tabella.setNroPaginaIO(storeResult.getNropaginaio());
		tabella.setNroPagineTot(storeResult.getNropaginetotout());
		tabella.setNroRecInPagina(storeResult.getNrorecinpaginaout());
		tabella.setNroRecTotali(storeResult.getNrorectotaliout());
		tabella.setTitolo(storeResult.getTitoloout());
//		tabella.setValoriRecTab(storeResult.getValorirectabout());
	}

	private List<SezioneTreeBean> buidAlberatura(List<XmlSezioneOutBean> listaSezioni) {
		List<SezioneTreeBean> listaAlberata = new ArrayList<>();
		for (int i = 0; i < listaSezioni.size(); i++) {
			XmlSezioneOutBean sez = listaSezioni.get(i);
			SezioneTreeBean sezioneTreeBean = new SezioneTreeBean();
			if(sez.getIdPadre() != null && !"".equalsIgnoreCase(sez.getIdPadre())) {
				// è una sottosezione
				sezioneTreeBean.setId(sez.getId());
				sezioneTreeBean.setTitle(sez.getNomeSezione());
				sezioneTreeBean.setIdPadre(sez.getIdPadre());
				for (SezioneTreeBean sezione : listaAlberata) {
					if(sezione.getId().equalsIgnoreCase(sez.getIdPadre())) {
						sezione.getSubmenu().add(sezioneTreeBean);
					}
				}
			} else {
				sezioneTreeBean.setId(sez.getId());
				sezioneTreeBean.setTitle(sez.getNomeSezione());
				listaAlberata.add(sezioneTreeBean);
			}
			
		}
		return listaAlberata;
	}

	private StreamingOutput buildStreamFile(final InputStream in) throws FileNotFoundException {
		StreamingOutput stream = new StreamingOutput() {
			public void write(OutputStream out) throws IOException, WebApplicationException {
				try {
					
					long t0 = System.currentTimeMillis();
					
					int read = 0;
					byte[] bytes = new byte[1024];
					
					while ((read = in.read(bytes)) != -1) {
						out.write(bytes, 0, read);
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
	
	private InputStream sbustaFileFirmato(String p7m, String fileName, File tempFile) throws IOException, Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();		  			
		return lInfoFileUtility.sbusta(p7m, fileName);
	}
	
	public void aggiornaContenutiConPaginaDedicata(List<XmlContenutiOutBean> listaContenutiOut) {
		boolean flgPagDedicata = false;
		for (XmlContenutiOutBean xmlContenutiOutBean : listaContenutiOut) {
			if(xmlContenutiOutBean != null && xmlContenutiOutBean.getTipoContenuto().equals("titolo_sezione") && xmlContenutiOutBean.getFlgPaginaDedicata()==1) {
				flgPagDedicata = true;
			} else if(xmlContenutiOutBean != null && xmlContenutiOutBean.getTipoContenuto().equals("fine_sezione") && xmlContenutiOutBean.getFlgPaginaDedicata()==0 && flgPagDedicata) {
				xmlContenutiOutBean.setFlgPaginaDedicata(new Integer(1));
				flgPagDedicata = false;
			}
			if (flgPagDedicata) {
				if (xmlContenutiOutBean != null && !xmlContenutiOutBean.getTipoContenuto().equals("titolo_sezione") && !xmlContenutiOutBean.getTipoContenuto().equals("fine_sezione")) {
					xmlContenutiOutBean.setFlgPaginaDedicata(new Integer(1));
				}
			}
		}
	}
}
