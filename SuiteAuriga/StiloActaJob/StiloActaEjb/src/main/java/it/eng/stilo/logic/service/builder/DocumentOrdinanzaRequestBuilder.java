/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.doqui.acta.acaris.archive.DocumentoSemplicePropertiesType;
import it.doqui.acta.acaris.archive.EnumDocPrimarioType;
import it.doqui.acta.acaris.archive.EnumTipoDocumentoType;
import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.acaris.common.AnnotazioniPropertiesType;
import it.doqui.acta.acaris.common.CodiceFiscaleType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.documentservice.EnumStepErrorAction;
import it.doqui.acta.acaris.documentservice.EnumTipoDocumentoArchivistico;
import it.doqui.acta.acaris.documentservice.StepErrorAction;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EVitalRecordCode;

public abstract class DocumentOrdinanzaRequestBuilder extends DocumentRequestBuilder<DocumentoSemplicePropertiesType> {

    protected void initDocumentProperties() {
        this.documentoPropertiesType = new DocumentoSemplicePropertiesType();
    }

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
        
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	    	
    	//Tipo di documento:
    	//- semplice il caso pi� frequente, il classico documento)
    	//- database
    	//- registro (solo in serie tipologiche di documenti con registri=SI)
    	documentoArchivisticoIRC.setTipoDocumento(EnumTipoDocumentoArchivistico.DOCUMENTO_SEMPLICE);
    	
    	//Oggetto del documento
    	if( dynamicAttributesMap.containsKey("oggetto") && dynamicAttributesMap.get("oggetto")!=null){
    		logger.info("::: Oggetto " + dynamicAttributesMap.get("oggetto"));
    		documentoPropertiesType.setOggetto( dynamicAttributesMap.get("oggetto") );
    	}
    	
    	//Indica se il documento ha origine interna
        documentoPropertiesType.setOrigineInterna(getValue(EAttributeType.INTERNO));
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (Da valorizzare a SI se su STILO vi e una versione della determina con omissis)
        if( dynamicAttributesMap.containsKey("datiSensibili")  && dynamicAttributesMap.get("datiSensibili")!=null ){
        	documentoPropertiesType.setDatiSensibili(Boolean.valueOf(dynamicAttributesMap.get("datiSensibili") ));
        } else {
        	documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        }
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati riservati (Da valorizzare a SI se su STILO vi e una versione della determina con omissis)
        if( dynamicAttributesMap.containsKey("datiRiservati")  && dynamicAttributesMap.get("datiRiservati")!=null ){
        	documentoPropertiesType.setDatiRiservati( Boolean.valueOf(dynamicAttributesMap.get("datiRiservati") ) );
        } else {
        	documentoPropertiesType.setDatiRiservati(getValue(EAttributeType.RISERVATO));
        }
        
        //Sono le parole chiave associabili al documento che possono servire per la sua ricerca
        if( dynamicAttributesMap.containsKey("paroleChiave") && dynamicAttributesMap.get("paroleChiave")!=null){
        	documentoPropertiesType.setParoleChiave( dynamicAttributesMap.get("paroleChiave") );
        }
        if( dynamicAttributesMap.containsKey("parolaChiaveFiltro") && dynamicAttributesMap.get("parolaChiaveFiltro")!=null){
        	if( documentoPropertiesType.getParoleChiave()!=null )
        		documentoPropertiesType.setParoleChiave( documentoPropertiesType.getParoleChiave() + "; " + dynamicAttributesMap.get("parolaChiaveFiltro") );
        	else {
        		documentoPropertiesType.setParoleChiave( dynamicAttributesMap.get("parolaChiaveFiltro") );
        	}
        }	
        
        //Eventuale "codice a barre" presente sul documento 
        if( dynamicAttributesMap.containsKey("codBarre") && dynamicAttributesMap.get("codBarre")!=null)
        	documentoPropertiesType.setCodBarre( dynamicAttributesMap.get("codBarre") );
        
        //Sono le persone giuridiche che hanno la responsabilita per emettere il documento. Puo essere anche una persona giuridica astratta
        if( dynamicAttributesMap.containsKey("autoreGiuridico") ){
        	String autoreGiuridicoString = dynamicAttributesMap.get("autoreGiuridico");
        	if(autoreGiuridicoString!=null ){
	        	String[] autoreGiuridicoList = autoreGiuridicoString.split(",");
	        	for( String autoreGiuridico : autoreGiuridicoList){
	        		documentoPropertiesType.getAutoreGiuridico().add( autoreGiuridico );
	        	}
        	}
        }
        //Sono le persone fisiche che hanno la responsabilita per emettere il documento
        if( dynamicAttributesMap.containsKey("autoreFisico") ){
        	String autoreFisicoString = dynamicAttributesMap.get("autoreFisico");
        	if(autoreFisicoString!=null ){
	        	String[] autoreFisicoList = autoreFisicoString.split(",");
	        	for( String autoreFisico : autoreFisicoList){
	        		documentoPropertiesType.getAutoreFisico().add( autoreFisico );
	        	}
        	}
        }
    	//E l autore materiale, la persona fisica che di fatto ha operato per conto dell Ente che firma il documento
        if( dynamicAttributesMap.containsKey("scrittore") ){
        	String scrittoreString = dynamicAttributesMap.get("scrittore");
        	if(scrittoreString!=null ){
	        	String[] scrittoreList = scrittoreString.split(",");
	        	for( String scrittore : scrittoreList){
	        		documentoPropertiesType.getScrittore().add( scrittore );
	        	}
        	}
        }
        //E la persona fisica che emette il documento, che inserisce il documento in archivio e che lo inoltra.
        //E una figura con responsabilita (dirigente).
        if( dynamicAttributesMap.containsKey("originatore") ) {
        	String originatoreString = dynamicAttributesMap.get("originatore");
        	if(originatoreString!=null ){
	        	String[] originatoreList = originatoreString.split(",");
	        	for( String originatore : originatoreList){
	        		documentoPropertiesType.getOriginatore().add( originatore );
	        	}
        	}
        }
        //Sono le persone giuridiche a cui il documento e diretto formalmente sia in senso fisico che in senso logico
        if( dynamicAttributesMap.containsKey("destinatarioGiuridico") ){
        	String destinatarioGiuridicoString = dynamicAttributesMap.get("destinatarioGiuridico");
        	if(destinatarioGiuridicoString!=null ){
	        	String[] destinatarioGiuridicoList = destinatarioGiuridicoString.split(",");
	        	for( String destinatarioGiuridico : destinatarioGiuridicoList){
	        		documentoPropertiesType.getDestinatarioGiuridico().add( destinatarioGiuridico );
	        	}
        	}
        }
        //Sono le persone fisiche a cui il documento e diretto formalmente sia in senso fisico che in senso logico
        if( dynamicAttributesMap.containsKey("destinatarioFisico") ){
        	String destinatarioFisicoString = dynamicAttributesMap.get("destinatarioFisico");
        	if(destinatarioFisicoString!=null ){
	        	String[] destinatarioFisicoList = destinatarioFisicoString.split(",");
	        	for( String destinatarioFisico : destinatarioFisicoList){
	        		documentoPropertiesType.getDestinatarioFisico().add( destinatarioFisico );
	        	}
        	}
        }
        //Il soggetto che ha l autorita e la competenza a produrre il documento informatico.
        if( dynamicAttributesMap.containsKey("soggettoProduttore") && dynamicAttributesMap.get("soggettoProduttore")!=null){
        	String soggettoProduttoreString = dynamicAttributesMap.get("soggettoProduttore");
        	if(soggettoProduttoreString!=null ){
	        	String[] soggettoProduttoreList = soggettoProduttoreString.split(",");
	        	for( String soggettoProduttore : soggettoProduttoreList){
	        		documentoPropertiesType.getSoggettoProduttore().add( soggettoProduttore );
	        	}
        	}
        }
        //Luogo in cui e redatto latto. E il luogo che appare sul documento
        if( dynamicAttributesMap.containsKey("dataTopica") && dynamicAttributesMap.get("dataTopica")!=null )
        	documentoPropertiesType.setDataDocTopica( dynamicAttributesMap.get("dataTopica") );
        //E la data in cui il documento e perfezionato (firmato, emesso). E la data che appare sul documento
        if( dynamicAttributesMap.containsKey("dataCronica") && dynamicAttributesMap.get("dataCronica")!=null ){
        	Date date; 
			try {
				date = format.parse( dynamicAttributesMap.get("dataCronica") );
				logger.debug("DATA CRONICA " + date);
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	logger.debug("DATA CRONICA " + cal);
	        	GregorianCalendar cal1 = new GregorianCalendar();
	        	cal1.setTimeInMillis(date.getTime());
	        	logger.debug("DATA CRONICA " + cal1);
	        	
				XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
				logger.debug("DATA CRONICA " + xmlGregCal);
	        	documentoPropertiesType.setDataDocCronica( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //E il numero che identifica il documento (Es. il numero del contratto, il numero della fattura, il numero della determina)
        if( dynamicAttributesMap.containsKey("numRepertorio") && dynamicAttributesMap.get("numRepertorio")!=null )
        	documentoPropertiesType.setNumRepertorio( dynamicAttributesMap.get("numRepertorio") );
        
        //Indica se il documento � stato autenticato
        if( dynamicAttributesMap.containsKey("docAutenticato") && dynamicAttributesMap.get("docAutenticato")!=null){
        	documentoPropertiesType.setDocAutenticato(Boolean.valueOf(dynamicAttributesMap.get("docAutenticato") ));
        } else {
        	documentoPropertiesType.setDocAutenticato(getValue(EAttributeType.AUTENTICATO));
        }
        //Indica se il documento e stato autenticato con firma autentica
        if( dynamicAttributesMap.containsKey("docAutenticatoFirmaAutenticata") && dynamicAttributesMap.get("docAutenticatoFirmaAutenticata")!=null ){
        	documentoPropertiesType.setDocAutenticatoFirmaAutenticata(Boolean.valueOf(dynamicAttributesMap.get("docAutenticatoFirmaAutenticata") ));
        } else {
        	documentoPropertiesType.setDocAutenticatoFirmaAutenticata(getValue(EAttributeType.AUTENTICATO_CON_FIRMA));
        }
        //Indica se il documento e stato autenticato come copia autentica
        if( dynamicAttributesMap.containsKey("docAutenticatoCopiaAutentica") && dynamicAttributesMap.get("docAutenticatoCopiaAutentica")!=null){
        	documentoPropertiesType.setDocAutenticatoCopiaAutentica(Boolean.valueOf(dynamicAttributesMap.get("docAutenticatoCopiaAutentica") ));
        } else {
        	 documentoPropertiesType.setDocAutenticatoCopiaAutentica(getValue(EAttributeType.AUTENTICATO_AUTENTICO));
        }
        
        //E la data in cui il documento esce dallEnte, puo essere diversa dalla data di upload del documento nel sistema
        if( dynamicAttributesMap.containsKey("dataTrasmissione") && dynamicAttributesMap.get("dataTrasmissione")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("dataTrasmissione") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.getDataTrasmissione().add( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //Indica se il documento e firmato elettronicamente o meno.
        if( dynamicAttributesMap.containsKey("firmaElettronica") && dynamicAttributesMap.get("firmaElettronica")!=null ){
        	documentoPropertiesType.setFirmaElettronica(Boolean.valueOf(dynamicAttributesMap.get("firmaElettronica") ));
        } else {
        	documentoPropertiesType.setFirmaElettronica(getValue(EAttributeType.FIRMA_ELETTRONICA));
        }
       
        //Indica se il documento e firmato digitalmente o meno. 
        if( dynamicAttributesMap.containsKey("firmaElettronicaDigitale") && dynamicAttributesMap.get("firmaElettronicaDigitale")!=null ){
        	documentoPropertiesType.setFirmaElettronicaDigitale(Boolean.valueOf(dynamicAttributesMap.get("firmaElettronicaDigitale") ));
        } else {
        	documentoPropertiesType.setFirmaElettronicaDigitale(getValue(EAttributeType.FIRMA_ELETTRONICA_DIGITALE));
        }
        
        //E il codice di vitalita del record.
        String vitalRecordCode = getValueString(EAttributeType.VITAL_RECORD);
        logger.debug("vitalRecordCode " + vitalRecordCode);
        if( vitalRecordCode!=null ){
        	try{
        		EVitalRecordCode vrc = EVitalRecordCode.valueOf(vitalRecordCode);
        		documentoPropertiesType.setIdVitalRecordCode(getValue(vrc));
        	} catch(Exception e){
        		logger.error("",e);
        		documentoPropertiesType.setIdVitalRecordCode(getValue(EVitalRecordCode.ALTO));
        	}
        } else 
        	documentoPropertiesType.setIdVitalRecordCode(getValue(EVitalRecordCode.ALTO));
		
		//Indica il tipo di documento elettronico che puo essere: - semplice - firmato
		documentoPropertiesType.setTipoDocFisico(EnumTipoDocumentoType.FIRMATO);
		
		//Indica la composizione del documento elettronico.
        documentoPropertiesType.setComposizione(EnumDocPrimarioType.DOCUMENTO_SINGOLO);

        //Indica se la composizione del documento precedentemente indicata dai valori Stato di efficacia, Tipo e Composizione si ripete o meno
        documentoPropertiesType.setMultiplo(getValue(EAttributeType.COMPOSIZIONE_MULTIPLA));
        
        /* if( dynamicAttributesMap.containsKey("annotazioni") ) {
        	List<ObjectIdType> listaAnnotazioni = documentoPropertiesType.getIdAnnotazioniList();
        	AnnotazioniPropertiesType annotazione = new AnnotazioniPropertiesType();
        	annotazione.setAnnotazioneFormale( getValue( EAttributeType.ANNOTAZIONE_DOC));
        	documentoPropertiesType.getIdAnnotazioniList().add( annotazione );
        }*/
        
        if( dynamicAttributesMap.containsKey("daConservareDopoIl") && dynamicAttributesMap.get("daConservareDopoIl")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("daConservareDopoIl") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDaConservareDopoIl( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        
        if( dynamicAttributesMap.containsKey("daConservarePrimaDel") && dynamicAttributesMap.get("daConservarePrimaDel")!=null ){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("daConservarePrimaDel") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDaConservarePrimaDel( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        
        String stepsFirmaDigitale = getValueString(EAttributeType.VERIFICA_FIRMA);
        if( stepsFirmaDigitale!=null){
        	StringTokenizer tokenizer = new StringTokenizer(stepsFirmaDigitale, ",");
        
        	while(tokenizer.hasMoreElements() ){
        		StepErrorAction step = new StepErrorAction();
        		String stepNumberString = (String)tokenizer.nextElement();
        		try{
        			Integer stepNumber = Integer.parseInt(stepNumberString);
        			step.setStep(stepNumber);
        			step.setAction(EnumStepErrorAction.INSERT);
        			documentoFisicoIRC.getAzioniVerificaFirma().add(step);
        		} catch(Exception e){
        			logger.error("errore nel parse degli step di verifica firma");
        		}

        	}
        }
    }

    public static void main(String[] args) {
//    	Date date; 
//		try {
//			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			date = format.parse( "2020-07-10 16:48:58" );
//			System.out.println("DATA CRONICA " + date);
//			GregorianCalendar cal = new GregorianCalendar();
//        	cal.setTime(date);
//        	System.out.println("DATA CRONICA " + cal);
//        	GregorianCalendar cal1 = new GregorianCalendar();
//        	cal1.setTimeInMillis(date.getTime());
//        	System.out.println("DATA CRONICA " + cal1);
//        	
//			XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//			System.out.println("DATA CRONICA " + xmlGregCal);
//        	
//			System.out.println("DATA CRONICA " + "2020-07-10 16:48:58".substring(0,10));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	//EVitalRecordCode vrc = EVitalRecordCode.valueOf("MEDIO");
    	//System.out.println(vrc);
    	
    	String sss = "FABIO AIMAR, ANNA PALAZZI";
    	String[] list = sss.split(",");
    	for( String s : list)
    		System.out.println(s);
		
	}
}
