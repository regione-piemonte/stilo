/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.odftoolkit.odfdom.dom.element.draw.DrawControlElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.eng.auriga.compiler.utility.TemplateStorage;
import it.eng.auriga.compiler.utility.TemplateStorageFactory;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;

/**
 * Inietta nel documento specificato i valori definiti nella stringa fornita, in formato xml
 */
public class OdtCompiler {

	private static Logger logger = Logger.getLogger(OdtCompiler.class);

	//se impostato a true rimuove gli eventuali checkbox non valorizzati presenti nel documento
	private Boolean removeUncheckedCheckBoxes = Boolean.FALSE;
	
	public String fillHtmlDocument(String xmlParamsDoc, String htmlDoc) 
	throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setValidating(false);
		dbf.setSchema(null);
		Document paramsDoc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlParamsDoc)));
		logger.error("L'xml che sto leggendo vale: " + htmlDoc);
		Document odtDoc =  dbf.newDocumentBuilder().parse(new InputSource(new StringReader(htmlDoc)));
		replaceVariables(odtDoc, paramsDoc);
		logger.info("new doc: " + getStringFromDocument(odtDoc)) ;
		return getStringFromDocument(odtDoc);
		
	}

	private Map<String, String> getVariablesMap(Document xmlParamsDoc) throws XPathExpressionException{
		Map<String, String> res = new TreeMap<String, String>();
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList)xPath.evaluate("//*/Variabile",
				xmlParamsDoc.getDocumentElement(), XPathConstants.NODESET);   
		String varName = "";
		String varValue = "";
		for (int i = 0; i < nodes.getLength(); i++){
			Node n = nodes.item(i);
			NodeList children = n.getChildNodes();

			for (int ch = 0; ch < children.getLength(); ch++){
				Node child = children.item(ch);
				if ("Nome".equals(child.getNodeName())){
					varName = child.getTextContent();
				}else if ("ValoreSemplice".equals(child.getNodeName())){
					varValue = child.getTextContent();
				}
			}
			res.put(varName, varValue);
		}    
		return res;
	}

	private void replaceVariables(Document htmlDoc, Document paramsDoc) throws XPathExpressionException{
		Map<String, String> params = getVariablesMap(paramsDoc);
		for (String varName: params.keySet()){
			logger.info("replacing var: " + varName + "; value: " + params.get(varName));
			replaceInputs(htmlDoc, varName, params.get(varName));
			replaceTextAreas(htmlDoc, varName, params.get(varName));      
		}

	}

	private void replaceInputs(Document htmlDoc, String name, String value) throws XPathExpressionException{
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList)xPath.evaluate("//*/input[@name='" + name + "']",
				htmlDoc.getDocumentElement(), XPathConstants.NODESET);    
		for (int i = 0; i < nodes.getLength(); i++){
			Node n = nodes.item(i);
			((Element)n).setAttribute("value", value);
		}
		logger.info("nodes: " + nodes.getLength());
	}

	private void replaceTextAreas(Document htmlDoc, String name, String value) throws XPathExpressionException{
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList)xPath.evaluate("//*/textarea[@name='" + name + "']",
				htmlDoc.getDocumentElement(), XPathConstants.NODESET);    
		for (int i = 0; i < nodes.getLength(); i++){
			Node n = nodes.item(i);
			n.setTextContent(value);
		}
		logger.info("nodes: " + nodes.getLength());
	}  

	public String getStringFromDocument(Document doc)
	{
		try
		{
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty("method","xhtml");
			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch(TransformerException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Recupera dallo storage il documento di cui è stato specificato l'uri, fornendo una rappresentazione OdfDocument del medesimo
	 * @param uriModAssDocTask
	 * @return
	 * @throws Exception
	 */
	private org.odftoolkit.simple.Document retrieveOdfDocument(String uriModAssDocTask) throws Exception {
		
		TemplateStorage templateStorage = TemplateStorageFactory.getTemplateStorageImpl();
		
		InputStream lInputStream = templateStorage.extract(uriModAssDocTask);
		
		org.odftoolkit.simple.Document referenceDoc = org.odftoolkit.simple.Document.loadDocument(lInputStream);
		
		return referenceDoc;
	}
	
	/**
	 * Inietta nel documento di cui è stato specificato lo storage uri i valori delle variabili presenti nell'xml fornito in formato stringa
	 * @param datimodellixmlout
	 * @param uriModAssDocTask
	 * @return
	 * @throws Exception
	 */
	public File fillOdtDocument(String datimodellixmlout, String uriModAssDocTask) throws Exception {

		//template da completare
		org.odftoolkit.simple.Document document = retrieveOdfDocument(uriModAssDocTask);
		
		//dati da iniettare
		SezioneCache sezioneCache = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(datimodellixmlout));

		return populateDocument(document, sezioneCache);
	}

	/**
	 * Inietta nel documento specificato i dati presenti nella SezioneCache fornita
	 * @param template
	 * @param valuesToInject
	 * @return
	 * @throws Exception
	 * @throws DOMException
	 * @throws IOException
	 */
	public File populateDocument(org.odftoolkit.simple.Document template, SezioneCache valuesToInject)
			throws Exception, DOMException, IOException {
		
		NodeList textFormNodes = template.getContentDom().getElementsByTagName("form:text");
		NodeList textareasFormNodes = template.getContentDom().getElementsByTagName("form:textarea");
		NodeList tablesNodes = template.getContentDom().getElementsByTagName("table:table");

		//getElementsByTagName su form:fixed-text non mi ritorna tutti gli elementi, son dovuto ricorrere ad XPath
		NodeList fixedText = extractValues(template, "//*/form:fixed-text");
		
		@SuppressWarnings("rawtypes")
		Map[] sezioneCacheMaps = mapSezioneCache(valuesToInject);
		
		//K -> variabile, V -> valore semplice 
		@SuppressWarnings("unchecked")
		Map<String, String> valoriSempliciMap = (Map<String, String>)sezioneCacheMaps[0];

		//K -> variabile, V -> oggetto Lista contenente i valori associati alla variabile
		@SuppressWarnings("unchecked")
		Map<String, Lista> valoriComplessiMap = (Map<String, Lista>)sezioneCacheMaps[1];
				
		injectValues(textFormNodes, valoriSempliciMap, template);
		
		injectValues(textareasFormNodes, valoriSempliciMap, template);
		
		//rimuove elementi presenti nel documento in base ai valori specificati nei relativi campi
		if(removeUncheckedCheckBoxes) {
			removeElements(fixedText, valoriSempliciMap, template);
		}
		
		injectTableValues(tablesNodes, valoriComplessiMap, template);
		
		File lFile = File.createTempFile("odt", ".odt");
		template.save(lFile);
		return lFile;
	}
	
	/**
	 * Effettua un filtraggio XPath ritornando i nodi che rispettano il criterio specificato
	 * @param template
	 * @param xPathFilter 
	 * @return
	 * @throws XPathExpressionException
	 * @throws Exception
	 */
	private NodeList extractValues(org.odftoolkit.simple.Document template, String xPathFilter) throws XPathExpressionException, Exception {
		
		//dubito che questa sia la maniera più semplice di ottenere un XPath su cui fare query, ma al momento non ho tempo di fare di meglio
		XPath xPath = ((OdfFileDom)template.getContentRoot().getOwnerDocument()).getXPath();
		
		NodeList elements = (NodeList)xPath.evaluate(xPathFilter, template.getContentRoot(), XPathConstants.NODESET);    

		return elements;
		
	}
	
	private void removeElements(NodeList elements, Map<String, String> valoriSempliciMap, org.odftoolkit.simple.Document template) throws XPathExpressionException, Exception {
		
		//per ogni elemento della lista estraggo il nome, per vedere se è stato specificato un valore, e l'xmlId, per recuperare il relativo drawControl in cui iniettare i dati
		for(int i = 0; i < elements.getLength(); i++) {
			
			Element currentElement = ((Element)elements.item(i));
			String xmlId = currentElement.getAttribute("xml:id");
			
			String elementFormName = currentElement.getAttribute("form:name");
			
			String valueToInject = valoriSempliciMap.get(elementFormName);
			
			boolean toBeDeleted = (valueToInject == null || valueToInject.equals("0") || valueToInject.isEmpty());
			
			if(toBeDeleted) {
				
				currentElement.getParentNode().removeChild(currentElement);

				//rimuovo l'elemento grafico associato all'elemento logico appena rimosso, se utilizzo l'oggetto da drawControlsMap, non funziona
				//quindi recupero l'oggetto mediante xpath e rimuovo direttamente il paragrafo contenente l'elemento grafico, in maniera tale
				//da rimuovere i whitespace che si sono generati per via dell'eliminazione dell'elemento
				NodeList emptyElement = extractValues(template, "//*/draw:control[@draw:control='" + xmlId + "']");
				
				Node parentNode = emptyElement.item(0).getParentNode();
				parentNode.getParentNode().removeChild(parentNode);
				
			}
			
			valoriSempliciMap.remove(elementFormName);
		}
		
	}

	/**
	 * Inietta gli eventuali valori nelle tabelle trovate
	 * @param tablesNodes Nodes che rappresentano tables
	 * @param valoriComplessiMap valori da iniettare nelle tabelle
	 * @param drawControlsMap
	 * @param template documento di riferimento
	 * @throws Exception 
	 */
	private void injectTableValues(NodeList tablesNodes, Map<String, Lista> valoriComplessiMap, org.odftoolkit.simple.Document template) throws Exception {
		
		for(int i = 0; i < tablesNodes.getLength(); i++) { 
			
			Element currentElement = ((Element)tablesNodes.item(i));
			
			String tableName = currentElement.getAttribute("table:name");
			
			Lista valuesToInject = valoriComplessiMap.get(tableName);
	
			if(valuesToInject != null) {
				addTableRow(template.getTableByName(tableName), valuesToInject);
			}	
		}
	}
	
	/**
	 * Inietta i valori nell'insieme dei nodi specificati mediante la NodeList fornita, correggendo lo stile dei tag modificati
	 * @param elements elementi "logici" in cui iniettare i dati
	 * @param valoriSempliciMap valori da iniettare
	 * @param drawControlsMap elementi in cui iniettare i dati 
	 * @param template documento di riferimento
	 * @throws DOMException
	 * @throws Exception
	 */
	private void injectValues(NodeList elements, Map<String,String> valoriSempliciMap, org.odftoolkit.simple.Document template) throws DOMException, Exception {
		
		//per ogni elemento della lista estraggo il nome, per vedere se è stato specificato un valore, e l'xmlId, per recuperare il relativo drawControl in cui iniettare i dati
		for(int i = 0; i < elements.getLength(); i++) {
			
			Element currentElement = ((Element)elements.item(i));
			String xmlId = currentElement.getAttribute("xml:id");
			
			String elementFormName = currentElement.getAttribute("form:name");
			
			String valueToInject = valoriSempliciMap.get(elementFormName);  
			
			if(valueToInject != null) {
				
				NodeList drawControls = extractValues(template, "//*/draw:control[@draw:control='" + xmlId + "']");
				
				if(drawControls.getLength() > 0) {
					
					DrawControlElement drawControlElement = (DrawControlElement)drawControls.item(0);
									
					drawControlElement.setTextContent(valueToInject);
								
					convertToSpan(drawControlElement, template);
					
					//essendo elementi univoci, uan volta valorizzati/utilizzati per l'iniezione i valori, li rimuovo dalle relative mappe
					valoriSempliciMap.remove(elementFormName);
//					drawControlsMap.remove(xmlId);
					
				}
			}
		}
	}
	
	/**
	 * Aggiunge una nuova riga alla table specificata
	 * @param currentTable la table da modifciare
	 * @param valuesToInject valori da iniettare nella tabella
	 * @param document odf di riferimento
	 * @throws Exception
	 */
	private void addTableRow(Table currentTable, Lista valuesToInject) throws Exception {
		
		//la riga di indice 0 coincide con l'intestazione
		int rowIndex = 1;
		
		for(Riga currentRiga : valuesToInject.getRiga()) {
			
			int cellIndex = 0;
			
			//se non c'è la riga richiesta, questa viene creata
			Row row = currentTable.getRowByIndex(rowIndex);
			
			for(SezioneCache.Variabile.Lista.Riga.Colonna currentColonna : currentRiga.getColonna()) {
				
				Cell cell = row.getCellByIndex(cellIndex);
				cell.setStringValue(currentColonna.getContent());
				cellIndex++;
				
			}
			
			rowIndex++;
		}
	}

	/**
	 * Converte il l'elemento grafico associato all'elemento della form in uno span, ed impone lo stile specifico
	 * @throws Exception 
	 * @throws DOMException 
	 */
	protected void convertToSpan(Element drawControlElement, org.odftoolkit.simple.Document template) throws DOMException, Exception {
		
		DrawControlElement convertedElement = (DrawControlElement)template.getContentDom().renameNode(drawControlElement, "urn:oasis:names:tc:opendocument:xmlns:text:1.0", "text:span");
				
		OdfTextParagraph parent = (OdfTextParagraph)convertedElement.getParentNode();
		parent.setAttribute("text:style-name", "span_style");
		
	}
	
	/**
	 * Mappa i drawControls, K -> valore dell'attributo draw:control, V -> relativo Element che rappresenta il DrawControl
	 * @param drawControls
	 * @return
	 */
	private Map<String, Element> mapDrawControls(NodeList drawControls) {

		Map<String,Element> retValue = new LinkedHashMap<String, Element>();
		
		for (int k = 0; k<drawControls.getLength(); k++){
			
			Element currentElement = ((Element)drawControls.item(k));
			retValue.put(currentElement.getAttribute("draw:control"), currentElement);
			
		}
		
		return retValue;
	}

	/**
	 * Mappa il contenuto della sezione cache, K -> variabile, V -> valore semplice 
	 * @param valuesToInject
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map[] mapSezioneCache(SezioneCache valuesToInject) {
		
		Map[] retValue = new Map[2];
		
		Map<String,String> valoriSempliciMap = new LinkedHashMap<String, String>();
		Map<String, Lista> valoriComplessi = new LinkedHashMap<String, Lista>();
		
		for(Variabile var : valuesToInject.getVariabile()) {
			
			String valoreSemplice = var.getValoreSemplice();
			
			String variableName = var.getNome();
			if(valoreSemplice != null) {
				
				valoriSempliciMap.put(variableName, valoreSemplice);
				
			} else {
				
				Lista values = var.getLista();
				valoriComplessi.put(variableName, values);
			}
		}
		
		retValue[0] = valoriSempliciMap;
		retValue[1] = valoriComplessi;
		
		return retValue;
	}

	public Boolean getRemoveUncheckedCheckBoxes() {
		return removeUncheckedCheckBoxes;
	}

	public void setRemoveUncheckedCheckBoxes(Boolean removeUncheckedCheckBoxes) {
		this.removeUncheckedCheckBoxes = removeUncheckedCheckBoxes;
	}

}