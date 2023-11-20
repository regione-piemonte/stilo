/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

/**
 * message handler fittizio per far gestire il file allegato in response,
 * altriemnti se si allega un file con le api previste l'implementazione
 * standard jaxws (Metro) non ritorna il file allegato nella response
 * 
 */

public class NullMessageHandler implements SOAPHandler<SOAPMessageContext> {

	private Logger logger = Logger.getLogger(NullMessageHandler.class);

	public boolean handleMessage(SOAPMessageContext messageContext) {
		Boolean outMessageIndicator = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (!outMessageIndicator.booleanValue()) {
			SOAPEnvelope envelope;
			SOAPHeader header;
			try {
				SOAPMessage soapMessage = messageContext.getMessage();
				envelope = soapMessage.getSOAPPart().getEnvelope();
				header = envelope.getHeader();
				processSOAPHeader(header, messageContext);
			} catch (SOAPException e) {
				logger.debug("Problemi durante il recupero delle credenziali");
			}
		}

		return true;
	}

	private boolean processSOAPHeader(SOAPHeader soapHeader, SOAPMessageContext messageContext) {

		Iterator childElems = soapHeader.getChildElements(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"));

		// iterate through child elements
		while (childElems.hasNext()) {
			SOAPElement child = (SOAPElement) childElems.next();
			processSOAPHeaderInfo(child, messageContext);
		}
		return true;
	}

	public Set<QName> getHeaders() {
		return Collections.EMPTY_SET;
	}

	public boolean handleFault(SOAPMessageContext messageContext) {
		return true;
	}

	public void close(MessageContext context) {
		
	}

	private void processSOAPHeaderInfo(SOAPElement element, SOAPMessageContext messageContext) {
		String id = null;
		String password = null;
		Iterator childElements = element.getChildElements(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken"));
		while (childElements.hasNext()) {
			Object usernameToken = childElements.next();
			if (usernameToken instanceof SOAPElement) {
				Iterator childElements1 = ((SOAPElement) usernameToken).getChildElements(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username"));
				Iterator childElements2 = ((SOAPElement) usernameToken).getChildElements(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password"));

				while (childElements1.hasNext()) {
					Object next = childElements1.next();
					if (next instanceof SOAPElement) {
						String username = ((SOAPElement) next).getValue();
						messageContext.put("username", username);
						messageContext.setScope("username", MessageContext.Scope.APPLICATION);
						logger.debug("Recuperato utente: " + username);
					}
				}
				while (childElements2.hasNext()) {
					Object next = childElements2.next();
					if (next instanceof SOAPElement) {
						// password = ((SOAPElement) next).getValue();
						messageContext.put("password", ((SOAPElement) next).getValue());
						messageContext.setScope("password", MessageContext.Scope.APPLICATION);
						logger.debug("Recuperata password");
					}

				}

			}
		}

	}
}
