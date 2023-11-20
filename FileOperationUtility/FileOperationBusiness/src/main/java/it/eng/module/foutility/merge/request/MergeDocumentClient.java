/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MergeDocumentClient {

	static Logger aLogger = LogManager.getLogger(MergeDocumentClient.class.getName());

	public String callService(File allegati[]) {
		try {
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = sfc.createConnection();
			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			soapEnvelope.addNamespaceDeclaration("mer", "http://mergedocument.webservices.jaxws.eng.it/");
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			SOAPBody soapBody = soapEnvelope.getBody();
			Name bodyName = soapEnvelope.createName("mer:merge");
			SOAPBodyElement gltp = soapBody.addBodyElement(bodyName);

			for (int i = 0; i < allegati.length; i++) {
				soapMessage.addAttachmentPart(soapMessage.createAttachmentPart(new DataHandler(new FileDataSource(allegati[i]))));
			}

			aLogger.debug("\n Soap Request MergeDocument:\n");

			String serviceHost = "localhost";// (String) aurigaProperties.get("service.host");
			String serviceHostPort = "8081";// (String) aurigaProperties.get("service.port");

			// URL endpoint = new URL("http://" + serviceHost + ":" + serviceHostPort + "/mergedocument/MergeDocument");
			URL endpoint = new URL("http://localhost:9999/ws/merge");
			SOAPMessage response = connection.call(soapMessage, endpoint);

			aLogger.debug("\n Soap Response:\n");
			aLogger.debug(response.getSOAPBody().getTextContent());

			Iterator iterator = response.getAttachments();
			File extractOne = new File("C:/temp/MergeTotale.pdf");

			byte bufferAU[] = new byte[1024];
			int count = 0;

			InputStream inst = null;

			FileOutputStream fost = new FileOutputStream(extractOne);
			boolean salvato = false;
			while (iterator.hasNext() && !salvato) {
				AttachmentPart type = (AttachmentPart) iterator.next();
				inst = type.getDataHandler().getInputStream();
				while ((count = inst.read(bufferAU)) > 0) {
					fost.write(bufferAU, 0, count);
				}
				inst.close();
				fost.close();
				salvato = true;
			}
			return response.getSOAPBody().getTextContent();
		} catch (Exception e) {
			aLogger.error("Eccezione callService MergeDocumentClient", e);
		}
		return null;
	}

	public static void main(String[] args) {
		MergeDocumentClient client = new MergeDocumentClient();
		// MergeDocumentRequest MergeDocumentRequest = new MergeDocumentRequest();
		// System.out.println(client.callService(MergeDocumentRequest).getMergeDocumentReturn());
		File[] allegati = new File[2];
		allegati[0] = new File("C:/Users/Administrator/Desktop/elenco_ comuni_italiani_11_marzo_2013.xls");
		allegati[1] = new File("C:/Users/Administrator/Desktop/mimmo.txt");
		String response = client.callService(allegati);
		aLogger.debug(response);

	}
}
