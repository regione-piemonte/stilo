/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.util;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class FileIndiceWriter {
	
    private static FileIndiceWriter instance = new FileIndiceWriter();
    private final Lock fileLock = new ReentrantLock();

    private FileIndiceWriter() {
        // Costruttore privato per impedire la creazione diretta di istanze.
    }

    public static FileIndiceWriter getInstance() {
        return instance;
    }

	public void writeToFile(Document document, String rootFolderAttach, String nomeCartellaAttach) throws Exception {
		try {
			// Sostituisco con il percorso in cui desidero salvare il file XML.
			File outputFile = new File(rootFolderAttach + File.separator + nomeCartellaAttach, "indice.xml");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(outputFile);

			// Acquisisci il lock prima di scrivere sul file
			fileLock.lock();

			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.transform(source, result);
			} finally {
				// Rilascia il lock dopo aver scritto sul file
				fileLock.unlock();
			}
		} catch (Exception e) {
			throw new Exception("Errore durante la scrittura del file indice.xml: " + e.getMessage(), e);
		}
	}
}
