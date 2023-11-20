/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Federico Cacco
 * 
 * Questi metodi di utility sono uguali a quelli contenuti nella classe it.eng.auriga.compiler.ModelliUtil di AurigaWeb
 * In caso di modifiche la logica va mentenuta uguale in entrambe le classi
 *
 */

public class FreeMarkerUtility {

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
    
    private static String getTextModelValue(Object value) {
		return value != null ? String.valueOf(value) : " ";
	}
    
    
}
