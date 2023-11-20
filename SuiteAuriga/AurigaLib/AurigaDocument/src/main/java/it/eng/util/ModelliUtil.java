/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.auriga.compiler.docx.CompositeCompiler;
import it.eng.auriga.compiler.docx.DocxCompiler;
import it.eng.auriga.compiler.docx.FormDocxCompiler;
import it.eng.auriga.compiler.odt.OdtCompiler;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocTrovamodelliBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.store.Getdatixgendamodello;
import it.eng.auriga.database.store.dmpk_modelli_doc.store.Trovamodelli;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.document.function.StoreException;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.util.bean.AttributiDinamiciXmlBean;
import it.eng.util.bean.ModelliDocXmlBean;
import it.eng.utility.TimbraUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

/**
 * Centralizza la generazione di template con dati iniettati
 * 
 * @author mattia zanin
 * 
 *         IMPORTANTE: SE LA CLASSE VIENE MODIFICATA VERIFICARE SE DEVE ESSERE MODIFICATA ANCHE L'ANALOGA CLASSE IN AURIGADOCUMENT
 *
 */
public class ModelliUtil {

	private static Logger logger = Logger.getLogger(ModelliUtil.class);

	/**
	 * Elenca i tipi di modello compatibili
	 */
	public enum TipoModello {

		ODT_FREEMARKERS, DOCX, DOCX_FORM, ODT, COMPOSITE 
	}

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml Sezione cache contenente i dati da iniettare
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xml) throws Exception {
		return createMapToFillTemplateFromSezioneCache(xml, false);
	}
	
	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param xml Sezione cache contenente i dati da iniettare
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 * @throws Exception
	 */
	public static Map<String, Object> createMapToFillTemplateFromSezioneCache(String xml, boolean creaMappaPerContext) throws Exception {
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        document.getDocumentElement().normalize();
        Node sezioneCacheNode = document.getFirstChild();
    	NodeList figliSezioneCache = sezioneCacheNode.getChildNodes();
    	return createMapToFillTemplateFromNodeList(figliSezioneCache, creaMappaPerContext);
    }

	/**
	 * Crea la mappa dei valori da iniettare
	 * 
	 * @param listaNodi Lista dei nodi Varibile della sezione cache
	 * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
	 * @return Mappa contenente la mappatura dei valori da iniettare
	 */
    private static Map<String, Object> createMapToFillTemplateFromNodeList(NodeList listaNodi, boolean creaMappaPerContext) {
    	Map<String, Object> mappaToReturn = new LinkedHashMap<String, Object>();
    	// Scorro tutti i nodi <Variaible>
    	for (int i = 0; i < listaNodi.getLength(); i++){
    		Node node = listaNodi.item(i);
    		// Estraggo il nome della variabile
    		String nomeVariabile = togliSuffisso(getChildNodeValue(node, "Nome"));
    		
    		// Estraggo il tipo della variabile
    		String tipoVariabile = getTipoVariabileSezioneCache(node);
    		if ("ValoreSemplice".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile semplice, estraggo il valore e lo inserisco nella mappa
    			String valoreVariabile = getChildNodeValue(node, "ValoreSemplice");
    			if (creaMappaPerContext) {
    				mappaToReturn.put(nomeVariabile, (getTextModelValue(valoreVariabile)));
    			}else {
    				mappaToReturn.put(nomeVariabile, valoreVariabile);
    			}
    		} else if ("Lista".equalsIgnoreCase(tipoVariabile)) {
    			// E' una variabile lista, genero la lista da iniettare
    			List<Object> listaValori = getListaSezioneCache(node, nomeVariabile, creaMappaPerContext);
    			mappaToReturn.put(nomeVariabile, listaValori);
    		}
    	}
    	return mappaToReturn;
    }
    
    /**
     * Restituisci il tipo di variabile di un nodo Variabile
     * 
     * @param nodo Il nodo Variabile da cui estrarre il tipo
     * @return Il tipo del nodo Variabile
     */
    private static String getTipoVariabileSezioneCache(Node nodo) {
    	NodeList figli = nodo.getChildNodes();
    	for (int i = 0; i < figli.getLength(); i++){
    		if ("ValoreSemplice".equalsIgnoreCase(figli.item(i).getNodeName())) {
    			return "ValoreSemplice";
    		}else if ("Lista".equalsIgnoreCase(figli.item(i).getNodeName())) {
    			return "Lista";
    		}
		}
    	return "";
    }
    
    /**
     * Restituisce il node value di un nodo figlio
     * 
     * @param nodo Il nodo da esaminare
     * @param childName Il nome del figlio da cui estrarre il node value
     * @return Il node value del figlio childName del node node
     */
    private static String getChildNodeValue(Node nodo, String childName) {
    	// Estraggo tutti i figli
    	NodeList figli = nodo.getChildNodes();
    	// Scorro i figli
    	for (int i = 0; i < figli.getLength(); i++){
    		// Verifico se è il figlio cercato
    		if (childName.equalsIgnoreCase(figli.item(i).getNodeName())) {
    			if (figli.item(i).getFirstChild() != null) {
    				// Estraggo il node value del nodo
    				return figli.item(i).getFirstChild().getNodeValue();
    			}
    		}
		}
    	return null;
    }
    
    /**
     * Trasforma un nodo Variabile di tipo Lista nella struttura List da iniettare nel modello
     * 
     * @param nodo Nodo Variabile di tipo lista
     * @param modello Bean del modello su cui iniettare i dati
     * @param nomeAttributoStrutturato Nome dell'attributo lista
     * @param creaMappaPerContext se è true le colonne hanno indice col1, col2, col3 invece di 1, 2, 3
     * @return List che rapprensta i dati da iniettare
     */
    private static List<Object> getListaSezioneCache(Node nodo, String nomeAttributoStrutturato, boolean creaMappaPerContext) {
    	List<Object> listaToReturn = new LinkedList<Object>();
    	// Verifico che il nodo abbia dei figli
    	if (nodo.getChildNodes() != null && nodo.getChildNodes().getLength() >= 1) {
    		// Estraggo il nodo Lista
    		Node nodoLista = getChildNodeByName(nodo, "Lista");
    		if (nodoLista != null) {
    			// Estraggo Tutte le Rige della Lista
    			NodeList lista = nodoLista.getChildNodes();
    			if (lista != null) {
    				if (lista.getLength() > 0) {
    					for (int i = 0; i < lista.getLength(); i++) {
    						if(lista.item(i) != null) {
	    						// La lista è composta da righe
	    	    				if ("Riga".equalsIgnoreCase(lista.item(i).getNodeName())) {
	    							// Per ogni riga creo la mappa dei valori Riga
	    							Map<String, Object> mappaValoriRiga = new LinkedHashMap<String, Object>(); 
									Node riga = lista.item(i);
									// Inserisco nella mappa tutti gli attributi della riga
									NodeList listaColonne = riga.getChildNodes();
									if (listaColonne != null && listaColonne.getLength() > 0) {
										for (int indiceColonna = 0; indiceColonna < listaColonne.getLength(); indiceColonna ++) {
				    						Node nodoColonna = listaColonne.item(indiceColonna);
				    						String numeroColonna = getAttributeValue(nodoColonna, "Nro");
				    						String valoreColonna = nodoColonna.getFirstChild() != null ? nodoColonna.getFirstChild().getNodeValue() : null;
				    						if (StringUtils.isNotBlank(numeroColonna)) {
				    							if (creaMappaPerContext) {
				    								mappaValoriRiga.put("col" + numeroColonna, getTextModelValue(valoreColonna));
				    							} else {
				    								mappaValoriRiga.put(numeroColonna, valoreColonna);
				    							}
				    						}
				    					}
					    				listaToReturn.add(mappaValoriRiga);
				    				}
	    						}     						
	    						// La lista è composta da valori semplici
	    						else if ("ValoreSemplice".equalsIgnoreCase(lista.item(i).getNodeName())) {
	    							if (lista.item(i).getNodeType() == 1) {
										if(creaMappaPerContext) {
											listaToReturn.add(getTextModelValue(lista.item(i).getTextContent()));
										} else {
											listaToReturn.add(lista.item(i).getTextContent());
										}
									}
	    						}
    						}
    					}
    				}
    			}
    		}
		}
    	return listaToReturn;
    }
    
    /**
     * Retituisce un nodo figlio con un determinato node name
     * 
     * @param nodo Il nodo su cui ricercare il figlio
     * @param nodeName Il node name del figlio da ricercare
     * @return Il nodo figlio con il node name ricercato
     */
    private static Node getChildNodeByName(Node nodo, String nodeName) {
    	if (nodo.getChildNodes() != null){
    		for (int i = 0; i < nodo.getChildNodes().getLength(); i++) {
    			if (nodo.getChildNodes().item(i) != null && nodeName.equalsIgnoreCase(nodo.getChildNodes().item(i).getNodeName())){
    				return nodo.getChildNodes().item(i);
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * Restitituisce il valore di un attributo del nodo
     * 
     * @param nodo Il nodo da cui estrarre lìattributo
     * @param attributeName Il nome dell'attributo da estrarre
     * @return Il valore dell'attributo cercato
     */
    private static String getAttributeValue(Node nodo, String attributeName){
    	if (nodo != null && nodo.getAttributes() != null && nodo.getAttributes().getNamedItem(attributeName) != null) {
    		return nodo.getAttributes().getNamedItem(attributeName).getNodeValue();
    	}
    	return null;
    }
    
    /**
     * Metodo che elimina il suffisso _Doc o _Ud dal una string
     * 
     * @param lString La stringa da elaborare 
     * @return La stringa ricevuta in ingresso privata del suffisso _Doc o _Ud
     */
    private static String togliSuffisso(String lString) {
    	if (StringUtils.isNotBlank(lString)) {
    		if (lString.endsWith("_Ud")) {
    			return lString.substring(0, lString.lastIndexOf("_Ud"));
    		}
    		if (lString.endsWith("_Doc")) {
    			return lString.substring(0, lString.lastIndexOf("_Doc"));
    		}
    	}
    	return lString;
    }
    
    public static String getTextModelValue(Object value) {
		return value != null ? String.valueOf(value) : " ";
	}
    
    public static File generaModelloPdf(ModelliDocXmlBean modelloBean, String sezioneCacheModello, List<String> listaValoriTimbroModello, String nomeModello, boolean convertiInPdfA) throws Exception {
    	File modello = creaModello(modelloBean, sezioneCacheModello, listaValoriTimbroModello);
    	File modelloPdf = convertToPdf(modello, StringUtils.isNotBlank(nomeModello) ? nomeModello : "Modello.pdf");
    	if(convertiInPdfA) {
    		return convertiPdfToPdfA3U(modelloPdf);
    	}else{
    		return modelloPdf;
    	}  	
    }
    
    public static File creaModello(ModelliDocXmlBean modelloBean, String sezioneCacheModello, List<String> listaValoriTimbroModello) throws Exception {
		String tipoModello = modelloBean.getTipoModello();
		String uriModello = modelloBean.getUriFile();
		
		File filledTemplate = null;
		
		try {
			logger.debug("uriModello: " + uriModello);
			logger.debug("tipoModello: " + tipoModello);
			logger.debug("templateValues: " + sezioneCacheModello);

			if (StringUtils.isNotBlank(uriModello)) {

				File templateOdt = DocumentStorage.extract(uriModello, null);

				if (tipoModello != null) {
					try {
						switch (tipoModello) {

						case "odt_con_freemarkers":
							DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
							InputStream templateInputStream = FileUtils.openInputStream(templateOdt);
							DocumentTemplate template = documentTemplateFactory.getTemplate(templateInputStream);
							
							Map<String, Object> mappaValori = ModelliUtil
									.createMapToFillTemplateFromSezioneCache(sezioneCacheModello, true);
							
							//converto le impronte dei file ritornati dalla store in timbri
							inserisciTimbro(mappaValori, listaValoriTimbroModello);

							File templateOdtWithValues = File.createTempFile("temp", ".odt");
							FileOutputStream odtOutputStream = new FileOutputStream(templateOdtWithValues);
							template.createDocument(mappaValori, odtOutputStream);

							filledTemplate = templateOdtWithValues;
							break;
						case "docx":
							filledTemplate = new DocxCompiler(sezioneCacheModello, uriModello).fillDocument();
							break;
						case "docx_form":
							filledTemplate = new FormDocxCompiler(sezioneCacheModello, uriModello).fillDocument();
							break;
						case "odt":
							filledTemplate = new OdtCompiler().fillOdtDocument(sezioneCacheModello, uriModello);
							break;
						case "composite":
							filledTemplate = new CompositeCompiler(sezioneCacheModello, uriModello).injectData();
							break;
						default:
							break;
						}
					} catch (Throwable e) {
						logger.error(
								"Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),e);
						throw new Exception("Durante l'iniezione dei dati si e verificato il seguente errore: " + e.getMessage(),e);
					}

				}
			} else {
				logger.error("URI del modello associato al task inesistente");
				throw new Exception("URI del modello associato al task inesistente");
			}
			if (filledTemplate == null) {
				logger.error("Si e verificato un errore durante la generazione del modello");
				throw new Exception("Si e verificato un errore durante la generazione del modello");
			}

		}catch(Exception e) {
			logger.error("Errore durante la creazione del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la creazione del modello: " + e.getMessage(), e);
		}

		return filledTemplate;
	}
    
  //Recupero le impronte dei file e li converto in immagini timbro dainniettare poi nel modello
  	private static void inserisciTimbro(Map<String, Object> mappaValori, List<String> listaValoriTimbro) throws Exception {
 		
  		try {
  			for(String key : mappaValori.keySet()) {
				if (listaValoriTimbro.contains(key)) {
					String impronta = (String) mappaValori.get(key);

					Object timbroImage = getImageTimbro(impronta);

					mappaValori.put(key, timbroImage);
				}							
  			}
  		}catch(Exception e) {
  			logger.error("Errore durante la conversione dell'impronta nel timbro: " + e.getMessage(),e);
//  			throw new Exception("Errore durante la conversione dell'impronta nel timbro");
  		}
  	}

  	private static Object getImageTimbro(String impronta) throws Exception{
  		Object timbroImage = null;
  		
  		ImpostazioniBarcodeBean impostazioniBarcodeBean = BarcodeUtility.getImpostazioniImmagineBarCode("QRCODE");
  		timbroImage = BarcodeUtility.getImageProvider(impronta, impostazioniBarcodeBean);
  		
  		return timbroImage;
  	}

	public static String getSezioneCacheModello(AurigaLoginBean logInBean, String idUd, String idDoc, String finalita, String nomeModello) throws Exception, StoreException {
		return getSezioneCacheModello(logInBean, idUd, idDoc, finalita,  nomeModello, null);		
	}
	
	public static String getSezioneCacheModello(AurigaLoginBean logInBean, String idUd, String idDoc, String finalita, String nomeModello, String contenutoFile) throws Exception, StoreException {
		/* RECUPERO I DATI (SEZIONE CACHE DA INNIETTARE NEL MODELLO) */
		
		logger.debug("Recupero i dati da inniettare nel modello");
		DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
		lGetdatixgendamodelloInput.setCodidconnectiontokenin(logInBean.getToken());
		lGetdatixgendamodelloInput.setIdobjrifin(StringUtils.isNotBlank(idUd) ? idUd : "");
		lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
		lGetdatixgendamodelloInput.setNomemodelloin(nomeModello);
		
		SezioneCache sezioneCache = new SezioneCache();
		sezioneCache.getVariabile().add(createVariabileSemplice("ID_DOC", StringUtils.isNotBlank(idDoc) ? idDoc : ""));
		sezioneCache.getVariabile().add(createVariabileSemplice("FINALITA", StringUtils.isNotBlank(finalita) ? finalita : ""));
		if (contenutoFile != null && !"".equals(contenutoFile)) {
			sezioneCache.getVariabile().add(createVariabileSemplice("XML", contenutoFile));
		}
		
		// Creo gli attributi addizionali
		AttributiDinamiciXmlBean lAttributiDinamiciXmlBean = new AttributiDinamiciXmlBean();
		lAttributiDinamiciXmlBean.setSezioneCacheAttributiDinamici(sezioneCache);
		lGetdatixgendamodelloInput.setAttributiaddin(new XmlUtilitySerializer().bindXml(lAttributiDinamiciXmlBean, true));

		Getdatixgendamodello lGetdatixgendamodello = new Getdatixgendamodello();
		StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello
				.execute(logInBean, lGetdatixgendamodelloInput);

		if (lGetdatixgendamodelloOutput.isInError()) {
			logger.error("Errore durante il recupero della sezione cahce da inniettare nel modello");
			throw new StoreException("Errore durante il recupero della sezione cahce da inniettare nel modello");
		}		

		String sezionCacheModello = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();
		return sezionCacheModello;		
		
	}

	private static File convertToPdf(File modelloWithValues, String nomeModello) throws Exception {
		logger.debug("Converto il modello in PDF");
		File pdfDaModello = null;
		try {
			InputStream targetStream = TimbraUtil.converti(modelloWithValues,nomeModello);
			pdfDaModello = File.createTempFile("tmp", ".pdf");
			FileUtils.copyInputStreamToFile(targetStream, pdfDaModello);
		}catch (Exception e) {
			logger.error("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
			throw new Exception("Errore durante la conversione in pdf del modello: " + e.getMessage(), e);
		}

		return pdfDaModello;
	}
	
	public static File convertiPdfToPdfA3U(File fileOriginalePdf) throws Exception {

		InputStream fileOriginalePdfIS = FileUtils.openInputStream(fileOriginalePdf);

		File filePdfA = File.createTempFile("fileTemporaneoPDFA", ".pdf");

		convertiPdfToPdfA3U(fileOriginalePdfIS, new FileOutputStream(filePdfA));

		return filePdfA;	
	}
	
	/**
	 * Metodo che dato l'inputStream di un file PDF e l'outputStream di un file temporaneo con estensione pdf, 
	 * scrive sull'OS il file PDF convertito in PDFA
	 * 
	 * @param fileOriginalePdfIS
	 * @param fileConvertitoOS
	 * @throws Exception
	 */
	public static void convertiPdfToPdfA3U(InputStream fileOriginalePdfIS, OutputStream fileConvertitoOS) throws Exception {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();

		PdfAWriter writer = PdfAWriter.getInstance(document, fileConvertitoOS, PdfAConformanceLevel.PDF_A_3U); 
		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();
		writer.createXmpMetadata();

		document.open();

		PdfContentByte cb = writer.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(fileOriginalePdfIS);
		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {                            
			addPageToDocument(document, writer, cb, reader, i, false);
		}

		fileConvertitoOS.flush();
		document.close();
		fileConvertitoOS.close();
		fileOriginalePdfIS.close();
		reader.close();
	}
	
	private static void addPageToDocument(com.itextpdf.text.Document document, PdfWriter writer, PdfContentByte cb, PdfReader reader, int pageNumber, boolean forceA4) {
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		// Federico Cacco 28.01.2016
		// Quando si concatenano pagine che derivano da scansioni si hanno dei problemi di rotazione, in
		// quanto la scansione solitamente produce una immagine orizzontale che viene poi in caso
		// ruotata nel verso giusto prima di essere inserita nel pdf finale. 
		// Devo quindi copiare la pagina tenendo conto di questa rotazione,
		// altrimenti rischio di inserirla storta
		//
		// Questa logica è presente nche nella classe it.eng.auriga.ui.module.layout.server.common.MergeDocument di ListExportUtility
		//
		// cb.addTemplate(page, 0, 0);

		// Verifico la rotazione della pagina corrente
		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			if (forceA4){
				document.setPageSize(PageSize.A4.rotate());
			}else{
				document.setPageSize(psize);
			}
		} else {
			if (forceA4){
				document.setPageSize(PageSize.A4);
			}else{
				document.setPageSize(psize);
			}
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
		document.newPage();
		// Raddrizzo l'immagine a seconda della rotazione

		switch (psize.getRotation()){
		case 0:
			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			break;
		case 90:
			cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
			break;
		case 180:
			cb.addTemplate(page,-1f, 0, 0, -1f, psize.getWidth(),psize.getHeight());
			break;
		case 270:
			cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
			break;
		default:
			break;
		}
	}
	
	public static ModelliDocXmlBean recuperaModello(AurigaLoginBean loginBean, String nomeModello) throws Exception {
		
		DmpkModelliDocTrovamodelliBean input = new DmpkModelliDocTrovamodelliBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setNomemodelloio(nomeModello);		

		Trovamodelli dmpkModelliDocTrovamodelli = new Trovamodelli();

		StoreResultBean<DmpkModelliDocTrovamodelliBean> output = dmpkModelliDocTrovamodelli.execute(loginBean, input);
		
		if (output.isInError()){
			throw new StoreException(StringUtils.isNotBlank(output.getDefaultMessage()) ? output.getDefaultMessage() : "Non è stato possibile recuperare il modello della lista accompagnatoria protocollazioni");
		}

		List<ModelliDocXmlBean> data = new ArrayList<ModelliDocXmlBean>();
		if(output.getResultBean().getListaxmlout() != null) {
			List<ModelliDocXmlBean> lista = XmlListaUtility.recuperaLista(output.getResultBean().getListaxmlout(), ModelliDocXmlBean.class);
			if(lista != null) {
				for(ModelliDocXmlBean lModelliDocXmlBean : lista) {
					data.add(lModelliDocXmlBean);
				}
			}
		}
		
		if(data.size()>0) {
			return data.get(0);
		}else {
			throw new Exception("Il modello " + nomeModello + " non e' stato trovato");
		}
	}

	public static Variabile createVariabileSemplice(String nome, String valoreSemplice) {
		Variabile var = new Variabile();
		var.setNome(nome);
		var.setValoreSemplice(valoreSemplice);
		return var;
	}
	
}