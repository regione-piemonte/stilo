/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Collection;
import java.util.Map;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.remoting.jaxws.JaxWsSoapFaultException;

public class Utility {
	
	@Resource
	private WebServiceContext context;
	
	public DataHandler[] getMessageDataHandlers() throws JaxWsSoapFaultException
	    {
	        // reperisco il contesto
			MessageContext msgContext = context.getMessageContext();
			Map<String,DataHandler> mapDataHandler = (Map<String,DataHandler>)msgContext.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
	
	        // restituisco gli attachment
			Collection<DataHandler> dataHandlers = mapDataHandler.values();
			DataHandler[] handlers = new DataHandler[dataHandlers.size()];
			handlers = dataHandlers.toArray(handlers);
	
			return handlers;
	    }
}

