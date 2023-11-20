/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.doqui.acta.acaris.archive.*;
import it.doqui.acta.acaris.common.CodiceFiscaleType;
import it.doqui.acta.acaris.common.EnumStreamId;
import it.doqui.acta.acaris.common.IdVitalRecordCodeType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.documentservice.ContenutoFisicoIRC;
import it.doqui.acta.acaris.documentservice.DocumentoArchivisticoIRC;
import it.doqui.acta.acaris.documentservice.DocumentoFisicoIRC;
import it.doqui.acta.acaris.documentservice.EnumStepErrorAction;
import it.doqui.acta.acaris.documentservice.StepErrorAction;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EVitalRecordCode;
import it.eng.stilo.model.core.QueueDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class DocumentRequestBuilder<T extends DocumentoPropertiesType> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final DocumentoArchivisticoIRC documentoArchivisticoIRC = new DocumentoArchivisticoIRC();
    protected final ContenutoFisicoIRC contenutoFisicoIRC = new ContenutoFisicoIRC();
    protected final DocumentoFisicoIRC documentoFisicoIRC = new DocumentoFisicoIRC();
    protected final ContenutoFisicoPropertiesType contenutoFisicoPropertiesType = new ContenutoFisicoPropertiesType();
    protected final DocumentoFisicoPropertiesType documentoFisicoPropertiesType = new DocumentoFisicoPropertiesType();
    protected final ClassificazionePropertiesType classificazionePropertiesType = new ClassificazionePropertiesType();
    protected DocumentRequestBean documentRequestBean;
    protected Set<QueueDocument> attachments;
    protected T documentoPropertiesType;

    public DocumentRequestBuilder() {
    }

    /**
     * Specific build for subclasses.
     */
    protected abstract void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType);

    /**
     * Specific document properties initialization for subclasses.
     */
    protected abstract void initDocumentProperties();

    /**
     * Build and return a {@link DocumentoArchivisticoIRC}.
     *
     * @param folderId    The folder identifier to set into returned object
     * @param requestBean The container bean with attributes needed for building
     * @param attachments The entity attachments
     * @return The built object.
     */
    public DocumentoArchivisticoIRC build(final ObjectIdType folderId, final DocumentRequestBean requestBean,
            final Set<QueueDocument> attachments) {
        logger.info("Build-[Folder]" + folderId.getValue());

        this.init();
        this.documentRequestBean = requestBean;
        this.attachments = attachments;
        
        //Indica se la classificazione del documento e cartacea (fisso e false per tutti i tipi)
        classificazionePropertiesType.setCartaceo( getValue(EAttributeType.CARTACEO ) );
        
        //Indica la collocazione fisica del documento cartaceo.
        //Se si seleziona Cartaceo = SI allora il campo diventa obbligatorio
        //non ha valore specificato per tutte le tipologie
        if( requestBean.getDynamicAttributesMap().containsKey("collocazioneCartacea") && requestBean.getDynamicAttributesMap().get("collocazioneCartacea")!=null )
        	classificazionePropertiesType.setCollocazioneCartacea( requestBean.getDynamicAttributesMap().get("collocazioneCartacea") );
        
        //Indica se la classificazione del documento si riferisce ad una copia cartacea del documento (fisso e false per tutti i tipi)
        classificazionePropertiesType.setCopiaCartacea( getValue(EAttributeType.COPIA_CARTACEA ) );
        
        //Indica se il documento ha associato un documento elettronico (fisso a true per tutti i tipi)
        // since all document are of type elettronico, the property Rappresentazione Digitale must be true
        documentoPropertiesType.setRappresentazioneDigitale(true);
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati personali (fisso e true per tutti i tipi)
        documentoPropertiesType.setDatiPersonali( getValue(EAttributeType.PERSONALE) );
        
        //Indica se il documento ha subito una registrazione mediante "protocollo" o "numero di repertorio" (fisso e true per tutti i tipi)
        documentoPropertiesType.setRegistrato(getValue(EAttributeType.REGISTRATO));
        
        //Indica se il documento e modificabile, ovvero in un formato che consente l'eventuale modifica diretta del file (.doc, .xls) 
        // (fisso a false per tutti i tipi)
        documentoPropertiesType.setModificabile(getValue(EAttributeType.MODIFICABILE));
        
        //Indica se il documento e definitivo, ovvero in un formato che non consente l'eventuale modifica diretta del file (.pdf, .xml.p7m)
        // (fisso a true per tutti i tipi)
        documentoPropertiesType.setDefinitivo(getValue(EAttributeType.DEFINITIVO));
        
        //E' la forma documentaria associata al documento. La si deve scegliere tra le forme documentaria valide definite dall'Ente
        documentoPropertiesType.setIdFormaDocumentaria(documentRequestBean.getFormatTypeId());
        
        if( requestBean.getDynamicAttributesMap().containsKey("applicativoAlimentante") && requestBean.getDynamicAttributesMap().get("applicativoAlimentante")!=null )
        	documentoPropertiesType.setApplicativoAlimentante( requestBean.getDynamicAttributesMap().get("applicativoAlimentante") );
       
        //Indica lo stato di efficacia che ha il documento elettronico.
        documentoPropertiesType.setIdStatoDiEfficacia(documentRequestBean.getEfficacyTypeId());
        
        //E' il boolean che indica se sbustare o meno il documento  in quanto si prevede, a breve, la spedizione del documento al di fuori dell'Ente mediante invio a protocollo. 
        contenutoFisicoPropertiesType.setSbustamento( getValue(EAttributeType.REINVIO_SBUSTAMENTO) );
        
        //Indica se l'aggregazione e in archivio corrente (SI) o in archivio id deposito (NO)
        
        //Data di creazione del documento
        Date dataCorrente;
		try {
			dataCorrente = new Date();
			GregorianCalendar cal = new GregorianCalendar();
        	cal.setTime(dataCorrente);
        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        	documentoPropertiesType.setDataCreazione(xmlGregCal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Utente da inserire come utente creatore del documento
		if( requestBean.getDynamicAttributesMap().containsKey("utenteCreazione") && requestBean.getDynamicAttributesMap().get("utenteCreazione")!=null ){
        	CodiceFiscaleType cf = new CodiceFiscaleType();
        	cf.setValue(requestBean.getDynamicAttributesMap().get("utenteCreazione"));
        	classificazionePropertiesType.setUtenteCreazione( cf ); 
        }
        
		
        contenutoFisicoPropertiesType.setContentStreamFilename( requestBean.getNomeFile() );
        contenutoFisicoPropertiesType.setInfoFormato(requestBean.getMimetype() );
        //contenutoFisicoPropertiesType.setModificabile( getValue(EAttributeType.MODIFICABILE) );
        
        if( requestBean.getDynamicAttributesMap().containsKey("modificheTecniche") && requestBean.getDynamicAttributesMap().get("modificheTecniche")!=null )
        	contenutoFisicoPropertiesType.setModificheTecniche( requestBean.getDynamicAttributesMap().get("modificheTecniche") );
        
        contenutoFisicoIRC.setStream(requestBean.getContentStream());
        contenutoFisicoIRC.setTipo(EnumStreamId.PRIMARY);
 
        documentoPropertiesType.setDaConservare(getValue(EAttributeType.DA_CONSERVARE));
        documentoPropertiesType.setProntoPerConservazione(getValue(EAttributeType.PRONTO_PER_CONSERVARE));
        documentoPropertiesType.setConservato(getValue(EAttributeType.CONSERVATO));
        

        if( requestBean.isPrimario())
        	documentoArchivisticoIRC.setParentFolderId(folderId);
        documentoArchivisticoIRC.setAllegato( getValue(EAttributeType.ALLEGATI));

        documentoFisicoPropertiesType.setDescrizione(requestBean.getContentStream().getFilename());

        // attachment section
        setAttachment();

        // delegate final building to subclass
        doBuild(requestBean.getDynamicAttributesMap(), requestBean.getEfficacyNonFirmatoTypeId());
        
        return documentoArchivisticoIRC;
    }

    private void init() {
        initDocumentProperties();
        contenutoFisicoIRC.setPropertiesContenutoFisico(contenutoFisicoPropertiesType);
        documentoFisicoIRC.setPropertiesDocumentoFisico(documentoFisicoPropertiesType);
        documentoFisicoIRC.getContenutiFisici().add(contenutoFisicoIRC);
        documentoArchivisticoIRC.setPropertiesDocumento(documentoPropertiesType);
        documentoArchivisticoIRC.setPropertiesClassificazione(classificazionePropertiesType);
        documentoArchivisticoIRC.getDocumentiFisici().add(documentoFisicoIRC);
    }

    protected void setAttachment() {
        if (attachments != null && attachments.size() > 0) {
            logger.info("Setto gli allegati");
        	GruppoAllegatiPropertiesType attachmentGroup = new GruppoAllegatiPropertiesType();
            attachmentGroup.setNumeroAllegati(attachments.size());
            documentoArchivisticoIRC.setGruppoAllegati(attachmentGroup);
            documentoPropertiesType.setDocConAllegati(true);
        } else {
        	 documentoPropertiesType.setDocConAllegati(false);
        }
    }

    protected Boolean getValue(final EAttributeType attributeType) {
        if( documentRequestBean.getAttributesMap().get(attributeType.getCode())!=null ){
        	Boolean value = Boolean.valueOf(documentRequestBean.getAttributesMap().get(attributeType.getCode()));
        	logger.debug("Attribute[" + attributeType.getCode() + "]-" + value);
        	return value;
        } else 
        	return false;        
    }
    
    protected String getValueString(final EAttributeType attributeType) {
        if( documentRequestBean.getAttributesMap().get(attributeType.getCode())!=null ){
        	String value = documentRequestBean.getAttributesMap().get(attributeType.getCode());
        	logger.debug("Attribute[" + attributeType.getCode() + "]-" + value);
        	return value;
        } else 
        	return null;        
    }

    protected IdVitalRecordCodeType getValue(final EVitalRecordCode attributeType) {
    	return documentRequestBean.getVitalRecordsMap().get(attributeType.name());
    }
    

}
