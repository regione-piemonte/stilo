/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.addunitadoc.visure;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginconcredenzialiesterneBean;
import it.eng.auriga.database.store.dmpk_login.store.Loginconcredenzialiesterne;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.jaxws.webservices.util.XPathHelper;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.dm.engine.manage.bean.ActivitiProcess;
import it.eng.document.function.bean.AttachWSBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AddUdUtils {

	static Logger aLogger = Logger.getLogger(AddUdUtils.class.getName());    
    
	public static String getFlagImpresaPerUnGiorno(AurigaLoginBean loginBean, String user){
		String flagSportelloImpresaInUnGiorno = "0";

		// recupero il parametro per capire se si tratta o meno dell'applicazione di sportello ImpresaInUnGiorno
		DmpkLoginLoginconcredenzialiesterneBean input = new DmpkLoginLoginconcredenzialiesterneBean();
		input.setCodapplicazionein(loginBean.getCodApplicazione());
		input.setCodistanzaapplin(loginBean.getIdApplicazione() );
		//input.setPasswordin("");
		input.setFlgnoctrlpasswordin(1);
		input.setUsernamein( user );
		Loginconcredenzialiesterne service = new Loginconcredenzialiesterne();
		StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> output;
		try {
			output = service.execute(loginBean, input);
			if( output.isInError() ) {
				aLogger.error("Errore nella dmpkLoginLoginconcredenzialiesterne " + + output.getErrorCode() + " _ " + output.getDefaultMessage() );
			} else {
				String parametriConfigOut = output.getResultBean().getParametriconfigout();
				// aLogger.info("parametriConfigOut " + parametriConfigOut);
				StringReader parametriConfigOutString = new StringReader(parametriConfigOut);  
				Lista lsParametriConfig = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(parametriConfigOutString);
	
				if ( lsParametriConfig != null && lsParametriConfig.getRiga().size() > 0) {
					for(Riga riga : lsParametriConfig.getRiga()) {
						String content1 = getContentColonnaNro(riga, 1);
						if(content1!=null && content1.equalsIgnoreCase("FLG_SPORTELLO_IMPRESAINUNGIORNO")) {
							flagSportelloImpresaInUnGiorno =  getContentColonnaNro(riga, 2);
							aLogger.debug("valore flagSportelloImpresaInUnGiorno " + flagSportelloImpresaInUnGiorno);
						}
					}
				}
			}
		} catch (Exception e) {
			aLogger.error("Errore nella funzione getFlagImpresaPerUnGiorno ", e);
		}
		
		aLogger.info("flagSportelloImpresaInUnGiorno " + flagSportelloImpresaInUnGiorno);
		return flagSportelloImpresaInUnGiorno;
	}
	
	private static String getContentColonnaNro(Riga r, int nro) {
		
		if (r == null)
			return null;
		
		for (int i = 0; i < r.getColonna().size(); i++) {
			Colonna c = r.getColonna().get(i);
			if (c!=null && c.getNro().intValue() == nro) {
				return c.getContent();
			}
		}
		
		return null;
	}
    
	public static String avviaProcesso(String flowTypeId) throws Exception{		
		try{
			EngineManager lEngineManager = new EngineManager();
			aLogger.debug("EngineManager.startProcess()");
			long start = new Date().getTime();			
			String processInstanceId = lEngineManager.startProcess(flowTypeId);
			long end = new Date().getTime();			
			aLogger.debug("Eseguito in " + (end - start) + " ms");
			aLogger.debug("Procedimento avviato con processInstanceId: " + processInstanceId );
			return processInstanceId;
		} catch( Throwable t){
			aLogger.error("Errore ", t);
			throw new Exception("Errore nella creazione del processo activity");
		}
	}
	
	public static List<ActivitiProcess> getProcessi(String flowTypeId){		
		try{
			EngineManager lEngineManager = new EngineManager();
			aLogger.debug("EngineManager.getListaProcessi()");
			long start = new Date().getTime();			
			List<ActivitiProcess> processes = lEngineManager.getListaProcessi(flowTypeId);
			long end = new Date().getTime();			
			aLogger.debug("Eseguito in " + (end - start) + " ms");
			
			return processes;
		} catch( Throwable t){
			aLogger.error("Errore ", t);
			return null;
		}
	}
	
	public static List<String> getValoreTipoProcedimento(File file){
		List<String> listaValori = new ArrayList<String>();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			NodeList oggettoComunicazioneList = doc.getElementsByTagName("oggetto-comunicazione");
			
			if( oggettoComunicazioneList!=null ){
				
				int numeroOggetti = oggettoComunicazioneList.getLength();
				aLogger.debug("Numero di oggettoComunicazione presenti nel file " + numeroOggetti) ;
				
				for(int i=0; i<oggettoComunicazioneList.getLength(); i++){
					//ogni object è un documento
					Node oggettoComunicazione = oggettoComunicazioneList.item(i);
					if( oggettoComunicazione!=null ){
						NamedNodeMap oggettoComunicazioneAttr = oggettoComunicazione.getAttributes();
						if( oggettoComunicazioneAttr!=null ){
							Node idAttr = oggettoComunicazioneAttr.getNamedItem("tipo-procedimento");
							aLogger.debug(idAttr.getTextContent());
							if( idAttr!=null && idAttr.getTextContent()!=null )
								listaValori.add(idAttr.getTextContent());
						}
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			aLogger.debug("Errore ", e);
		} catch (SAXException e) {
			aLogger.debug("Errore ", e);
		} catch (IOException e) {
			aLogger.debug("Errore ", e);
		}
		return listaValori;
	}
	
	public static List<String> getCodiceVisura(File file){
		XPathHelper xpathHelper = new XPathHelper();
		
		List<String> listaValori = new ArrayList<String>();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			aLogger.info("doc root " + doc.getDocumentElement().getNodeName());
			if( doc.getDocumentElement()==null || doc.getDocumentElement().getNodeName()==null || !(doc.getDocumentElement().getNodeName().endsWith("riepilogo-pratica-suap"))){
				return null;
			}
			
			NodeList moduloList = xpathHelper.processXPath("//riepilogo-pratica-suap//struttura//modulo", doc.getDocumentElement());
			aLogger.info("moduloList " + moduloList);
			//NodeList oggettoComunicazioneList = doc.getElementsByTagName("oggetto-comunicazione");
			
			//if( oggettoComunicazioneList!=null ){
			if( moduloList!=null ){
				
				int numeroOggetti = moduloList.getLength();
				aLogger.debug("Numero di moduli presenti nel file " + numeroOggetti) ;
				
				for(int i=0; i<moduloList.getLength(); i++){
					//ogni object è un documento
					Node modulo = moduloList.item(i);
					if( modulo!=null ){
						NamedNodeMap oggettoComunicazioneAttr = modulo.getAttributes();
						if( oggettoComunicazioneAttr!=null ){
							Node idAttr = oggettoComunicazioneAttr.getNamedItem("cod");
							aLogger.debug("Valore attributo cod " + idAttr.getTextContent());
							if( idAttr!=null && idAttr.getTextContent()!=null )
								listaValori.add(idAttr.getTextContent());
						}
					}
				}
			} else {
				return null;
			}
			
		} catch (ParserConfigurationException e) {
			aLogger.debug("Errore ", e);
			return null;
		} catch (SAXException e) {
			aLogger.debug("Errore ", e);
			return null;
		} catch (IOException e) {
			aLogger.debug("Errore ", e);
			return null;
		}
		return listaValori;
	}
	
	public static void main(String[] args) {
		//String path = "C:/Users/Anna Tresauro/Desktop/test_sue/02313821007-08102018-0811.SUAP.xml";
		String path = "C:/Users/Anna Tresauro/Downloads/02313821007-08102018-0811.SUAP.xml";
		File file = new File(path);
		
		getCodiceVisura(file);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			Element root = doc.getDocumentElement();
			aLogger.info("doc root " + root.getLocalName() + " " + root.getNodeName() + " " + root.getNodeType());
			if( doc.getDocumentElement()==null || doc.getDocumentElement().getNodeName()==null || (doc.getDocumentElement().getNodeName().endsWith("riepilogo-pratica-suap"))){
				System.out.println("Qui");
			}
		} catch (ParserConfigurationException e) {
			aLogger.debug("Errore ", e);
			
		} catch (SAXException e) {
			aLogger.debug("Errore ", e);
			
		} catch (IOException e) {
			aLogger.debug("Errore ", e);
			
		}
	}

	public static String checkAttachOnFtp(String xml) throws Exception {
		String nomeFileOnFtp ="";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		NodeList nList = document.getElementsByTagName("NewUD");
		for (int i = 0, len = nList.getLength(); i < len; i++) {
		    Element elm = (Element)nList.item(i);
		    if (StringUtils.isNotBlank(elm.getAttribute("NomeArchivioFileInviatiSeparati"))) {
		    	nomeFileOnFtp = elm.getAttribute("NomeArchivioFileInviatiSeparati");
		    	
		    	return nomeFileOnFtp;
		    }
		}
		
		return nomeFileOnFtp;
	}
	
	public static String cancellaTagFileFtp(String xml) throws Exception {
		String xmlPulito = "";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		NodeList nList = document.getElementsByTagName("NewUD");
		for (int i = 0, len = nList.getLength(); i < len; i++) {
			Element elm = (Element) nList.item(i);
			if (StringUtils.isNotBlank(elm.getAttribute("NomeArchivioFileInviatiSeparati"))) {
				elm.removeAttribute("NomeArchivioFileInviatiSeparati");
				break;
			}
		}

		Transformer transformer;
		StreamResult result = null;

		transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(document);
		transformer.transform(source, result);

		xmlPulito = result.getWriter().toString();

		return xmlPulito;
	}
	
	public static String getTagTipoProvenienza(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);
		String valore = "I";

		NodeList nodeList = document.getElementsByTagName("TipoProvenienza");
		for (int i = 0; i < nodeList.getLength(); i++) {
			// Get element
			Element element = (Element) nodeList.item(i);
			Node child = element.getFirstChild();
			valore = child.getTextContent();
		}
		return valore;
	}

	public static String getTagTipoDocDecodificaNome(String xml) throws Exception {
try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		NodeList nodeList = document.getElementsByTagName("TipoDoc");
		// Get element
		Element element = (Element) nodeList.item(0);
		NodeList nodeListChildren = element.getChildNodes();
		for (int i = 0; i < nodeListChildren.getLength(); i++) {
			Node child = nodeListChildren.item(i);
			if ("Decodifica_Nome".equals(child.getNodeName())) {
				return child.getTextContent();
			}
		}
}catch(Exception e) {}
		return null;
	}

	// NewUD/TipoDoc/CodId NewUD/TipoDoc/Decodifica_Nome
	public static String getTagTipoDocCodId(String xml) throws Exception {
try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		NodeList nodeList = document.getElementsByTagName("TipoDoc");
		// Get element
		Element element = (Element) nodeList.item(0);
		NodeList nodeListChildren = element.getChildNodes();
		for (int i = 0; i < nodeListChildren.getLength(); i++) {
			Node child = nodeListChildren.item(i);
			if ("CodId".equals(child.getNodeName())) {
				return child.getTextContent();
			}
		}
	}catch(Exception e) {}
		return null;
	}

	public static AttachWSBean buildAttachWSBean(File fileAttach, String xml, int indiceFile, boolean flgImpresaInUnGiorno, AurigaLoginBean pAurigaLoginBean)
			throws Exception {
		AttachWSBean attachWSBean = new AttachWSBean();
		
		String nomeFile = fileAttach.getName();				
		
		try {
			if(flgImpresaInUnGiorno) {
				nomeFile = fileAttach.getName();
				
				//-- 3: Nro di attachment con cui il file appare nella request secondo schema NEWUD.xsd
				attachWSBean.setNumeroAttach(String.valueOf(1));
			}else {
				nomeFile = getNomeFile(xml, indiceFile+1);
				//-- 3: Nro di attachment con cui il file appare nella request secondo schema NEWUD.xsd
				attachWSBean.setNumeroAttach(String.valueOf(indiceFile+1));
			}
			
			String uriFile = DocumentStorage.storeInput(FileUtils.openInputStream(fileAttach), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio(), null);

			InfoFileUtility lFileUtility = new InfoFileUtility();

			MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFileNoOut(/*fileAttachTemp*/fileAttach.toURI().toString(), nomeFile, false, null, false);
			
			if (lMimeTypeFirmaBean.getFirmatari() != null) {
				String firmatari = "";
				for (String firmatarioInstance : lMimeTypeFirmaBean.getFirmatari()) {
					firmatari = firmatari + firmatarioInstance + ";";
				}
				
				//-- 12: Firmatari
				attachWSBean.setFirmatari(firmatari);
				
				//-- 10: Flg file firmato (valori 1/0)
				attachWSBean.setFlgFirmato("1");
				
			} else {
				
				//-- 10: Flg file firmato (valori 1/0)
				attachWSBean.setFlgFirmato("0");
			}
			
			//-- 13: Indicazione del tipo di firma (CAdES o PAdES)
			attachWSBean.setTipoFirma(lMimeTypeFirmaBean.getTipoFirma());
			
			//-- 14: Info di verifica della firma
			attachWSBean.setInfoVerificaFirma(lMimeTypeFirmaBean.getInfoFirma());								
			
			if(lMimeTypeFirmaBean.getInfoFirmaMarca()!=null) {
				//-- 17: Informazioni di verifica della marca temporale se presente
				attachWSBean.setInfoVerificaMarca(lMimeTypeFirmaBean.getInfoFirmaMarca().getInfoMarcaTemporale());
				
				//-- 15: Data e ora delle marca se presente marca temporale valida (nel formato DD/MM/RRR HH24:MI:SS)
				attachWSBean.setDataOraMarca(lMimeTypeFirmaBean.getInfoFirmaMarca().getDataOraMarcaTemporale());
				
				//-- 16: Tipo di marca temporale se presente
				attachWSBean.setTipoMarca(lMimeTypeFirmaBean.getInfoFirmaMarca().getTipoMarcaTemporale());
			}

			
			//-- 1: DisplayName del file 
			attachWSBean.setDisplayFilename(nomeFile);
			
			//-- 2: URI del file salvato su archivio definitivo in notazione storageUtil		
			attachWSBean.setUri(uriFile);					
			
			
			//-- 6: dimensione
			attachWSBean.setDimensione(new BigDecimal(fileAttach.length()));
			
			//-- 7: impronta
			attachWSBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
			
			//-- 8: algoritmo calcolo impronta
			attachWSBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
			
			//-- 9: encoding di calcolo impronta: colonna
			attachWSBean.setEncodingImpronta(lMimeTypeFirmaBean.getEncoding());
			
			//-- 11: Mimetype
			attachWSBean.setMimetype(lMimeTypeFirmaBean.getMimetype());
			
			// Puntamento al file
			attachWSBean.setFile(fileAttach);
			
			
			// Prendo i firmatari
			String listDataOraEmissioneCertificatoFirma = "";
			String listDataOraScadenzaCertificatoFirma = "";	
			String listTipoFirmaQA = "";
			String listCfFirmatario = "";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if (lMimeTypeFirmaBean.getBuste() != null) { 
				for (Firmatari bustaFileFirmato : lMimeTypeFirmaBean.getBuste()) {
					
					if (bustaFileFirmato!=null && bustaFileFirmato.getDataFirma() != null){
						
						// Leggo la data emissione certificato firmatario
						if (bustaFileFirmato.getDataEmissione()!=null){
							String dataEmissione = sdf.format(bustaFileFirmato.getDataEmissione());
							listDataOraEmissioneCertificatoFirma = listDataOraEmissioneCertificatoFirma + dataEmissione + ";";
						}
						
						// Leggo la data scadenza certificato firmatario
						if (bustaFileFirmato.getDataScadenza()!=null){
							String dataScadenza = sdf.format(bustaFileFirmato.getDataScadenza());
							listDataOraScadenzaCertificatoFirma = listDataOraScadenzaCertificatoFirma + dataScadenza + ";";
						}
						
						// Leggo il tipo di firma
						if (bustaFileFirmato.getTipoFirmaQA()!=null){
							String tipoFirmaQA = bustaFileFirmato.getTipoFirmaQA();
							listTipoFirmaQA = listTipoFirmaQA + tipoFirmaQA + ";";
						}
						
						// Leggo il cf del firmatario
						if (bustaFileFirmato.getCfFirmatario()!=null){
							String cfFirmatario = bustaFileFirmato.getCfFirmatario();
							listCfFirmatario = listCfFirmatario + cfFirmatario + ";";
						}
					}
				}
			}
			
			//-- 25: Data emissione certificato firmatario (se piu' di uno separati da ";")
			attachWSBean.setDataOraEmissioneCertificatoFirma(listDataOraEmissioneCertificatoFirma);
			
			//-- 26: Data scadenza certificato firmatario (se piu' di uno separati da ";")
			attachWSBean.setDataOraScadenzaCertificatoFirma(listDataOraScadenzaCertificatoFirma);
			
			//-- 27: Indica se firma Qualifica (=Q) o Avanzata (=A) (se piu' di uno separati da ";")
			attachWSBean.setTipoFirmaQA(listTipoFirmaQA);
			
			//-- 28: Cod. fiscali dei firmatari (se piu' di uno separati da ";")
			attachWSBean.setCfFirmatario(listCfFirmatario);
			
			//-- 29: Impornta file pre firma
			attachWSBean.setImprontaPreFirmaDaFileOp(lMimeTypeFirmaBean.getImprontaPreFirmaFileOp());
			
			
			return attachWSBean;
		} catch (Exception e) {
			aLogger.error("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
	        throw new Exception("Errore nel calcolo delle info per il file: " + nomeFile + " Error: " + e.getMessage(), e);
		}
	}
	
	
	private static String getNomeFile(String xml, int numAttach) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);
		
		NodeList nList = document.getElementsByTagName("VersioneElettronica");
        String nomeFile = null;
		for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            aLogger.info("Current Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               String numeroAttachXml = eElement.getElementsByTagName("NroAttachmentAssociato").item(0).getTextContent();
               aLogger.info("---NroAttachmentAssociato " + numeroAttachXml);
               String nomeFileXml = eElement.getElementsByTagName("NomeFile").item(0).getTextContent();
               aLogger.info("---NomeFile " + nomeFileXml);
               if( numeroAttachXml!=null && numeroAttachXml.equalsIgnoreCase(""+numAttach)){
            	   nomeFile = nomeFileXml;
               }
            }
         }
		return nomeFile;
	}

	public static boolean checkRequiredAttribute(String nomeFile, AttachWSBean attachWSBean) {
		if(attachWSBean!=null) {
			boolean isInError = false;
			
			String errorMessage = "Per il file: " + nomeFile + " c'è stato un errore di calcolo o non sono"
					+ "presenti i seguenti attributi: ";
			
			if(StringUtils.isBlank(attachWSBean.getUri())) {
				errorMessage = errorMessage + "uri - ";
				isInError = true;
			}
			if(StringUtils.isBlank(attachWSBean.getImpronta())) {
				errorMessage = errorMessage + "impronta - ";
				isInError = true;
			}
			if(StringUtils.isBlank(attachWSBean.getEncodingImpronta())) {
				errorMessage = errorMessage + "encdingImpronta - ";
				isInError = true;
			}
			if(StringUtils.isBlank(attachWSBean.getAlgoritmo())) {
				errorMessage = errorMessage + "algoritmo - ";
				isInError = true;
			}
			if(StringUtils.isBlank(attachWSBean.getMimetype())) {
				errorMessage = errorMessage + "mimeType - ";
				isInError = true;
			}
			if(attachWSBean.getDimensione()==null || attachWSBean.getDimensione().intValue()==0) {
				errorMessage = errorMessage + "dimensione - ";
				isInError = true;
			}
			
			if(isInError) {
				aLogger.error(errorMessage);
				return false;
			}else {
				return true;
			}
			
		} else {
			return false;
		}
	}

	
	/* SE IL CALCOLO DELLE INFO FILE NON HA RESTITUTIO QUALCHE INFORMAZIONE PEROVO A RICHIAMARLO FINO A UN MASSIMO DI 3 VOLTE*/
	public static void retryCallFileOp(File fileAttach, String xml, int indiceFile, boolean flgImpresaInUnGiorno,
			AurigaLoginBean pAurigaLoginBean) throws Exception {
		
		for(int i=0; i<3; i++) {
			aLogger.debug("Provo a richiamare fileop per calcolare le info del file: " + fileAttach.getName());
			AttachWSBean attachWSBean = buildAttachWSBean(fileAttach, xml, indiceFile, flgImpresaInUnGiorno, pAurigaLoginBean);
			if(checkRequiredAttribute(fileAttach.getName(), attachWSBean)) {
				return;
			}
		}
		
		throw new Exception("Errore nel calcolo delle infomazioni per il file: " + fileAttach.getName());		
	}	
}
