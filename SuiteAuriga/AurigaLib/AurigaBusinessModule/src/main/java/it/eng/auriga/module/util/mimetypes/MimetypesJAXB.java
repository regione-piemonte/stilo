/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * Classe di utilità che effettua le operazioni JAXB sull'xml contenente al suo interno l'elenco dei mimetype ammissibili.
 * @author upescato
 *
 */
public class MimetypesJAXB {
	
	private static Logger log = Logger.getLogger(MimetypesJAXB.class);

	/**
	 * Dato l'xml dei mimetype, ne restituisce una struttura object oriented.
	 * @return Mimetypes
	 */
	//JAXB unmarshaller
	public static Mimetypes getMimetypes() {
		InputStream is = null;
		try {
			is = Mimetypes.class.getResourceAsStream("/it/eng/mimetypes/mimetypes.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Mimetypes.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Mimetypes mimetypes = (Mimetypes) jaxbUnmarshaller.unmarshal(is);

			if(mimetypes==null || mimetypes.getCategory()==null || mimetypes.getCategory().isEmpty()) {
				throw new JAXBException("Il file xml che si sta tentando di trasformare in istanze Java non contiene mimetypes");
			}
			
			else
				return mimetypes;

		} catch (JAXBException e) {
			log.warn(e);
			return null;
		} finally {
			if (is!=null)
				try {
					is.close();
				} catch (IOException e) {
					log.warn(e);
				}
		}
	}
	/**
	 * Marshalling, attualmente non funzionante.
	 * @param mimetypes
	 * @throws JAXBException 
	 */
	public static void setMimetypes(Mimetypes mimetypes) throws JAXBException {
		throw new JAXBException("L'operazione di marshalling non è attualmente implementata.");
	}


}
