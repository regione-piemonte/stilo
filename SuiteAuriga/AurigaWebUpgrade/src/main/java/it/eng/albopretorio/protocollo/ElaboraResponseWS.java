/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.axis.message.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElaboraResponseWS {
	
	private static Logger logger = Logger.getLogger(ElaboraResponseWS.class);
	private String idAlbo;
	
	public String elaboraResponse (SOAPEnvelope response) throws Exception {
	
		Document respDocument = null;
		try {
			respDocument = response.getAsDocument();
		} catch (Exception e) {
			logger.warn(e);
		}
			
		respDocument.getDocumentElement().normalize();
		
	    NodeList nodeList = respDocument.getElementsByTagName("RispostaDocumento");
	   
	    boolean inError = false;
	    String errors = null;
	    
	    for (int i=0; i < nodeList.getLength(); ++i) {
	
	      Node node=nodeList.item(i);
	      NodeList children=node.getChildNodes();
	      
	      for (int j=0; j < children.getLength(); ++j) {
	        Node child=children.item(j);
	        
	        if (child.getNodeName().equals("IdAlbo")) {       
	        	logger.debug("L'idAlbo ottenuto e': " + child.getFirstChild().getNodeValue());
	        	idAlbo = child.getFirstChild().getNodeValue();
	        } else if (child.getNodeName().equals("Esito")) {
	        	logger.debug("L'esito ottenuto e': " + child.getFirstChild().getNodeValue());
	        	if(child.getFirstChild().getNodeValue().equalsIgnoreCase("ERRORE")) {
	        		inError = true;
	        	}
	        }
	        if (child.getNodeName().equals("Errore")) {
	        	String errorCode = "";
	        	String errorMessage = "";
	        	NodeList children2 =  child.getChildNodes();        	
	        	for (int k=0; k < children2.getLength(); ++k) {
	    		   Node child2=children2.item(k);
	    		   if (child2.getNodeName().equals("Codice")) {
	    			   errorCode = child2.getFirstChild().getNodeValue();
	    			   logger.debug("Il codice dell'errore restitutito e': " + errorCode);
	    		   } else if (child2.getNodeName().equals("Messaggio")) {  
	    			   errorMessage = child2.getFirstChild().getNodeValue();	    			   
	    			   logger.debug("Il messaggio d'errore ottenuto e': " + errorMessage);
	    		   }
	        	}
	        	if(errors == null) {
	        		errors = "[" + errorCode + "] " + errorMessage;
	        	} else {
	        		errors += "; [" + errorCode + "] " + errorMessage;
	        	}
	        }
	      }
	    }
	    
	    if(inError) {
	    	throw new Exception(errors);
	    }
	    
	    return idAlbo;
	}
}
