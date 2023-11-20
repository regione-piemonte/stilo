/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.doqui.acta.acaris.archive.DocumentoRegistroPropertiesType;
import it.doqui.acta.acaris.archive.EnumDocPrimarioType;
import it.doqui.acta.acaris.archive.EnumTipoDocumentoType;
import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.acaris.common.CodiceFiscaleType;
import it.doqui.acta.acaris.documentservice.EnumTipoDocumentoArchivistico;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EVitalRecordCode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class RegistroAttiRequestBuilder extends DocumentRequestBuilder<DocumentoRegistroPropertiesType> {

    protected void initDocumentProperties() {
        this.documentoPropertiesType = new DocumentoRegistroPropertiesType();
    }

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    	//documentoArchivisticoIRC.setClassificazionePrincipale(documentRequestBean.getClassificationId());
    	
    	if( dynamicAttributesMap.containsKey("numero") && dynamicAttributesMap.get("numero")!=null ){
        	classificazionePropertiesType.setNumeroInput( dynamicAttributesMap.get("numero") ); 
        }
    	
    	//Tipo di documento:
    	//- semplice (e il caso piu frequente, il classico documento)
    	//- database
    	//- registro (solo in serie tipologiche di documenti con registri=SI)
    	documentoArchivisticoIRC.setTipoDocumento(EnumTipoDocumentoArchivistico.DOCUMENTO_REGISTRO);
        
    	//Oggetto del documento
    	if( dynamicAttributesMap.containsKey("oggetto") && dynamicAttributesMap.get("oggetto")!=null ){
    		documentoPropertiesType.setOggetto( dynamicAttributesMap.get("oggetto") ); 
    	} else {
    		documentoPropertiesType.setOggetto(createObjectName());
    	}
    	 
    	//Indica se il documento ha origine interna
        documentoPropertiesType.setOrigineInterna(getValue(EAttributeType.INTERNO));
        
        //Indica se il documento ha associato un documento elettronico (fisso a true per tutti i tipi)
        // since all document are of type elettronico, the property Rappresentazione Digitale must be true
        documentoPropertiesType.setRappresentazioneDigitale(true);
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati personali (fisso e true per tutti i tipi)
        documentoPropertiesType.setDatiPersonali( getValue(EAttributeType.PERSONALE) );
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (Da valorizzare a SI )
        if( dynamicAttributesMap.containsKey("datiSensibili")  && dynamicAttributesMap.get("datiSensibili")!=null ){
        	documentoPropertiesType.setDatiSensibili(Boolean.valueOf(dynamicAttributesMap.get("datiSensibili") ));
        } else {
        	documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        }
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati riservati (Da valorizzare a SI )
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
        
        //E l autore materiale, la persona fisica che di fatto ha operato per conto dell�Ente che firma il documento
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
        // per il registro e fisso a STILO
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
        
        //Il soggetto che ha lautorita e la competenza a produrre il documento informatico.
        if( dynamicAttributesMap.containsKey("soggettoProduttore") ){
        	String soggettoProduttoreString = dynamicAttributesMap.get("soggettoProduttore");
        	if(soggettoProduttoreString!=null ){
	        	String[] soggettoProduttoreList = soggettoProduttoreString.split(",");
	        	for( String soggettoProduttore : soggettoProduttoreList){
	        		documentoPropertiesType.getSoggettoProduttore().add( soggettoProduttore );
	        	}
        	}
        }
        
        //Luogo in cui e redatto l atto. E il luogo che appare sul documento
        if( dynamicAttributesMap.containsKey("dataTopica") && dynamicAttributesMap.get("dataTopica")!=null)
        	documentoPropertiesType.setDataDocTopica( dynamicAttributesMap.get("dataTopica") );
        //E la data in cui il documento e perfezionato (firmato, emesso,). E la data che appare sul documento
        if( dynamicAttributesMap.containsKey("dataCronica") && dynamicAttributesMap.get("dataCronica")!=null){
        	Date date; 
			try {
				date = format.parse( dynamicAttributesMap.get("dataCronica") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDataDocCronica( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //E il numero che identifica il documento (Es. il numero del contratto, il numero della fattura, il numero della determina)
        if( dynamicAttributesMap.containsKey("numRepertorio") && dynamicAttributesMap.get("numRepertorio")!=null )
        	documentoPropertiesType.setNumRepertorio( dynamicAttributesMap.get("numRepertorio") );
        
        if( dynamicAttributesMap.containsKey("applicativoAlimentante") && dynamicAttributesMap.get("applicativoAlimentante")!=null )
        	documentoPropertiesType.setApplicativoAlimentante( dynamicAttributesMap.get("applicativoAlimentante") );
       
        documentoPropertiesType.setIdVitalRecordCode(getValue(EVitalRecordCode.MEDIO));
        
        documentoPropertiesType.setTipoDocFisico(EnumTipoDocumentoType.SEMPLICE);
       
        documentoPropertiesType.setComposizione(EnumDocPrimarioType.DOCUMENTO_SINGOLO);
        
        //Codice del registro
        if( dynamicAttributesMap.containsKey("codiceRegistro") && dynamicAttributesMap.get("codiceRegistro")!=null)
	    	documentoPropertiesType.setCodiceRegistro( dynamicAttributesMap.get("codiceRegistro") );
        //Anno a cui il registro si riferisce
        if( dynamicAttributesMap.containsKey("annoRegistro") && dynamicAttributesMap.get("annoRegistro")!=null ){
            try {
        	documentoPropertiesType.setAnnoRegistro(Integer.parseInt(dynamicAttributesMap.get("annoRegistro")));
            } catch(Exception e){
            	
            }
        }
        
        //Numero della prima registrazione presente nel registro
        if( dynamicAttributesMap.containsKey("numeroPrimaRegistrazione") && dynamicAttributesMap.get("numeroPrimaRegistrazione")!=null )
        	documentoPropertiesType.setNumeroPrimaRegistrazione( dynamicAttributesMap.get("numeroPrimaRegistrazione") );
        //Data della prima registrazione presente nel registro
        if( dynamicAttributesMap.containsKey("dataPrimaRegistrazione") && dynamicAttributesMap.get("dataPrimaRegistrazione")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("dataPrimaRegistrazione") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDataPrimaRegistrazione( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        //Numero dell'ultima registrazione presente nel registro
        if( dynamicAttributesMap.containsKey("numeroUltimaRegistrazione") && dynamicAttributesMap.get("numeroUltimaRegistrazione")!=null )
            documentoPropertiesType.setNumeroUltimaRegistrazione( dynamicAttributesMap.get("numeroUltimaRegistrazione") );
        //Data dell'ultima registrazione presente nel registro
        if( dynamicAttributesMap.containsKey("dataUltimaRegistrazione") && dynamicAttributesMap.get("dataUltimaRegistrazione")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("dataUltimaRegistrazione") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDataUltimaRegistrazione( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        
        //Data di apertura del registro
        if( dynamicAttributesMap.containsKey("dataAperturaRegistro") && dynamicAttributesMap.get("dataAperturaRegistro")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("dataAperturaRegistro") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDataApertura( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //Data di chiusura del registro
        if( dynamicAttributesMap.containsKey("dataChiusuraRegistro") && dynamicAttributesMap.get("dataChiusuraRegistro")!=null){
        	Date date;
			try {
				date = format.parse( dynamicAttributesMap.get("dataChiusuraRegistro") );
				GregorianCalendar cal = new GregorianCalendar();
	        	cal.setTime(date);
	        	XMLGregorianCalendar xmlGregCal =  DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        	documentoPropertiesType.setDataChiusura( xmlGregCal );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        //E il numero che identifica il documento (Es. il numero del contratto, il numero della fattura, il numero della determina)
        if( dynamicAttributesMap.containsKey("numRepertorio") && dynamicAttributesMap.get("numRepertorio")!=null )
            documentoPropertiesType.setNumRepertorio( dynamicAttributesMap.get("numRepertorio") );
        
        //Indica se il documento e stato autenticato       
        if( dynamicAttributesMap.containsKey("docAutenticato") && dynamicAttributesMap.get("docAutenticato")!=null){
        	documentoPropertiesType.setDocAutenticato(Boolean.valueOf(dynamicAttributesMap.get("docAutenticato") ));
        } else {
        	documentoPropertiesType.setDocAutenticato(getValue(EAttributeType.AUTENTICATO));
        }
        
        //Indica se il documento e stato autenticato con firma autentica
        if( dynamicAttributesMap.containsKey("docAutenticatoFirmaAutenticata") && dynamicAttributesMap.get("docAutenticatoFirmaAutenticata")!=null){
        	documentoPropertiesType.setDocAutenticatoFirmaAutenticata(Boolean.valueOf(dynamicAttributesMap.get("docAutenticatoFirmaAutenticata") ));
        } else {
        	documentoPropertiesType.setDocAutenticatoFirmaAutenticata(getValue(EAttributeType.AUTENTICATO_CON_FIRMA));
        }
        
        //Indica se il documento e stato autenticato come copia autentica
        if( dynamicAttributesMap.containsKey("docAutenticatoCopiaAutentica") && dynamicAttributesMap.get("docAutenticatoCopiaAutentica")!=null ){
        	documentoPropertiesType.setDocAutenticatoCopiaAutentica(Boolean.valueOf(dynamicAttributesMap.get("docAutenticatoCopiaAutentica") ));
        } else {
        	 documentoPropertiesType.setDocAutenticatoCopiaAutentica(getValue(EAttributeType.AUTENTICATO_AUTENTICO));
        }
        
        //E la data in cui il documento esce dall Ente, puo essere diversa dalla data di upload del documento nel sistema 
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
        
        
        
    }

    /*private String createObjectName() {
        final StringBuilder objectBuilder = new StringBuilder();
        objectBuilder.append("REGDD");
        objectBuilder.append(documentRequestBean.getAooCode());
        objectBuilder.append(LocalDate.now().getYear());
        objectBuilder.append(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
        objectBuilder.append("-Q");
        objectBuilder.append(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));

        return objectBuilder.toString();
    }*/
    
    private String createObjectName() {
        final StringBuilder objectBuilder = new StringBuilder();
        objectBuilder.append("REGDD");
        objectBuilder.append(documentRequestBean.getAooCode());
        objectBuilder.append(LocalDate.now().getYear());
        objectBuilder.append(LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
        
        return objectBuilder.toString();
    }
    
    public static void main(String[] args) {
    	int meseCorrente = LocalDate.now().getMonth().getValue();
    	System.out.println(meseCorrente);
	}
}
