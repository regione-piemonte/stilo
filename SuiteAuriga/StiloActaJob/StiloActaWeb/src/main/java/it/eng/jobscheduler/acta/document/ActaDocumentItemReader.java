/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.QueueDocumentsEJB;
import it.eng.stilo.logic.type.EIntegrationResource;
import it.eng.stilo.model.core.DocumentType;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.util.EDocumentStatus;

import javax.ejb.EJB;
import javax.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
public class ActaDocumentItemReader extends ActaItemReader {
    @EJB
    private QueueDocumentsEJB queueDocumentsEJB;

    @Override
    public void open(Serializable checkpoint) throws Exception {
    	logger.debug(getClass().getSimpleName() + "-open");

    	queueDocuments = new ArrayList<QueueDocument>();
    	
    	final QueueDocument example = new QueueDocument();

    	logger.debug("cerco i tipi di doc");
    	final List<DocumentType> documentTypes =
    			Arrays.asList(
    					//determine
    					queueDocumentsEJB.load(DocumentType.class,"DD"), 
    					queueDocumentsEJB.load(DocumentType.class, "DDO"), 
    					
    					//ordinanze
    					queueDocumentsEJB.load(DocumentType.class, "ORD"),
    					queueDocumentsEJB.load(DocumentType.class, "ORDO"),
    					//decreti presidente giunta
    					queueDocumentsEJB.load(DocumentType.class, "DPGR"),
    					queueDocumentsEJB.load(DocumentType.class, "DPGRO"),
    					//decreti
    					queueDocumentsEJB.load(DocumentType.class, "DCR"),
    					queueDocumentsEJB.load(DocumentType.class, "DCRO"),
    					//liquidazioni
    					queueDocumentsEJB.load(DocumentType.class, "LIQ"),
    					//delibere consiglio
    					queueDocumentsEJB.load(DocumentType.class, "DELCONS"),
    					queueDocumentsEJB.load(DocumentType.class, "DELCONSO"),
    					//delibere presidente giunta regionale
    					queueDocumentsEJB.load(DocumentType.class, "DELGR"),
    					queueDocumentsEJB.load(DocumentType.class, "DELGRO"),
    					//Decreti del Sindaco-Consigliere delegato 
    					queueDocumentsEJB.load(DocumentType.class, "DCRC"),
    					queueDocumentsEJB.load(DocumentType.class, "DCRCO"),
    					//Decreto del Sindaco o Vicesindaco
						queueDocumentsEJB.load(DocumentType.class, "DCRS"),
						queueDocumentsEJB.load(DocumentType.class, "DCRSO"),
						
						//Registri 
						queueDocumentsEJB.load(DocumentType.class, "REG"),
    					queueDocumentsEJB.load(DocumentType.class, "RPUBBL")
    			);
    	//        Arrays.asList(queueDocumentsEJB.load(DocumentType.class,EIntegrationResource.DETERMINA.getCode()), 
    	//        		queueDocumentsEJB.load(DocumentType.class, EIntegrationResource.DETERMINA_OMISSIS.getCode()), 
    	//        		queueDocumentsEJB.load(DocumentType.class, EIntegrationResource.REGISTRO_TRIMESTRALE_DETERMINE.getCode()));

    	logger.debug("Cerco se ci sono doc in elaborazione");
    	example.setStatus(EDocumentStatus.ELABORATION);
    	
    	List<QueueDocument> queueDocumentsElaborazione = queueDocumentsEJB.findAttachment(example, createQueryAttributes(), documentTypes);
    	
    	if( queueDocumentsElaborazione!=null && queueDocumentsElaborazione.size()!=0){
    		
			logger.info("Ci sono documenti in elaborazione, non processo altro");
    	
    	} else {

	    	logger.debug("Cerco se ci sono doc in errore");
	    	example.setStatus(EDocumentStatus.ERROR);
	    	
	    	List<QueueDocument> queueDocumentsErrore = queueDocumentsEJB.findAttachment(example, createQueryAttributes(), documentTypes);
	
	    	if( queueDocumentsErrore!=null && queueDocumentsErrore.size()!=0){
	    		
				logger.info("Ci sono documenti in errore, processo prima quelli e poi cerco quelli in stato R");
			
				logger.info("[#Documents in errore]" + queueDocumentsErrore.size());
				for(QueueDocument documentErrore : queueDocumentsErrore){
					queueDocuments.add(documentErrore );
				}
	    	} else {
	    		logger.info("Non ci sono documenti in errore");
	    	}
	    	
	    	int numMaxRisultati = getNumMaxRecord();
	    	logger.debug("Il numero max di risultati da elaborare e: " + numMaxRisultati);
	    	
	    	if( queueDocuments.size() < numMaxRisultati ){
	    		logger.info("Cerco i documenti in stato R");
	    		example.setStatus(EDocumentStatus.READY);
	    		
	    		List<QueueDocument> queueDocumentsReady = queueDocumentsEJB.findAttachment(example, createQueryAttributes(), documentTypes);
	    		if( queueDocumentsReady!=null && queueDocumentsReady.size()!=0){
	        		
	    			logger.info("Ci sono documenti in stato R, processo prima quelli e poi cerco quelli in stato R2");
	    		
	    			logger.info("[#Documents stato R]" + queueDocumentsReady.size());
	    			for(QueueDocument documentReady : queueDocumentsReady){
	    				if( queueDocuments.size() < numMaxRisultati ){
	    					queueDocuments.add(documentReady );
	    				}
	    			}
	        	} else {
	        		logger.info("Non ci sono documenti in stato R");
	        	}
	    		
	    		if( queueDocuments.size() < numMaxRisultati ){
		    		
		    		logger.info("cerco i documenti da riprocessare");
		    		example.setStatus(EDocumentStatus.READY2);
		
		    		List<QueueDocument> queueDocumentsDaRielaborare = queueDocumentsEJB.findAttachment(example, createQueryAttributes(), documentTypes);
		    		if( queueDocumentsDaRielaborare!=null && queueDocumentsDaRielaborare.size()!=0){
		    			
		    			logger.info("Ci sono documenti in stato R2");
		    			
		    			logger.info("[#Documents da rielaborare]" + queueDocumentsDaRielaborare.size());
		    			for(QueueDocument documentDaRielaborare : queueDocumentsDaRielaborare){
		    				if( queueDocuments.size() < numMaxRisultati ){
		    					queueDocuments.add(documentDaRielaborare );
		    				}
		    			}
		    		} else {
		    			logger.info("Non ci sono documenti in stato R2");
		    		}
	    		} else {
	        		logger.debug("Numero max di risultati raggiunto");
	        	}
	    	} else {
	    		logger.debug("Numero max di risultati raggiunto");
	    	}
	    	
	
	    	logger.info("[#Documents]" + queueDocuments.size());
	
	    	for(QueueDocument queue : queueDocuments){
	    		queue.setStatus(EDocumentStatus.ELABORATION);
	    		queue.setModified(LocalDateTime.now());
	    	}
	    	try {
	    		queueDocumentsEJB.update(queueDocuments);
	    	} catch (DatabaseException e) {
	    		logger.error(String.format("[ErrorUpdating-Documents=#%d]", queueDocuments.size()));
	    	}
    	}

    }

}
