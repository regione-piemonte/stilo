/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.doqui.acta.acaris.archive.IdStatoDiEfficaciaType;
import it.eng.stilo.logic.type.EAttributeType;
import it.eng.stilo.logic.type.EIntegrationResource;

public class DeliberaGiuntaOmissisRequestBuilder extends DocumentDecretoRequestBuilder {

    @Override
    protected void doBuild(Map<String, String> dynamicAttributesMap, IdStatoDiEfficaciaType idStatoDiEfficaciaNonFirmatoType) {
        super.doBuild( dynamicAttributesMap, idStatoDiEfficaciaNonFirmatoType );
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (fisso 
        documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati sensibili (fisso a false)
        documentoPropertiesType.setDatiSensibili(getValue(EAttributeType.SENSIBILE));
        
        //Definisce se la struttura aggregativa contiene o meno documenti aventi dati riservati (fisso a false)
        documentoPropertiesType.setDatiRiservati(getValue(EAttributeType.RISERVATO));
       
        //documentoPropertiesType.setOggetto(EIntegrationResource.DETERMINA_OMISSIS.name().toLowerCase());
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
    }

}
