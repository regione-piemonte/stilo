/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * message handler fittizio per far gestire il file allegato in response, altriemnti
 * se si allega un file con le api previste l'implementazione standard jaxws (Metro) 
 * non ritorna il file allegato nella response
 *
 */


public class NullMessageHandler implements SOAPHandler<SOAPMessageContext> {

	public boolean handleMessage(SOAPMessageContext messageContext) {
		 
		 
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
 
}

