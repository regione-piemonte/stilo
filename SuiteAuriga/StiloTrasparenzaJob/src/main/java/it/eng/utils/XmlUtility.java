/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import it.eng.job.trasparenza.TrasparenzaJob;
import it.eng.utils.bean.ProvvedimentoExport;

public class XmlUtility {
	
	private static Logger log = Logger.getLogger(TrasparenzaJob.class);
	
	public static ProvvedimentoExport xmlToBean(String xml) {
		log.debug("inizio metodo xmlToBean");
		
		ProvvedimentoExport result = new ProvvedimentoExport();
		
		try {
			log.debug("XML con dati provvedimento " + xml);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(ProvvedimentoExport.class);
			
			StringReader reader = new StringReader(xml);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			result = (ProvvedimentoExport) jaxbUnmarshaller.unmarshal(reader);
			
			log.debug("unmarshal eseguito correttamente");
		} catch (JAXBException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		log.debug("inizio metodo xmlToBean");
		
		return result;
	}
	
}
