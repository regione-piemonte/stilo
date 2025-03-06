package it.eng.utility;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.BasicHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/*
 * Se si vuole attivare i log per vedere richiesta/risposta SOAP, decommentare il corpo del metodo 'invoke'
 */
public class AxisLogHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AxisLogHandler.class);

	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		// logMessage(msgContext);
	}// invoke

	@Override
	public void onFault(MessageContext msgContext) {
		super.onFault(msgContext);
		try {
			logMessage(msgContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// onFault

	private void logMessage(MessageContext msgContext) throws AxisFault {
		Message inMsg = msgContext.getRequestMessage();
		Message outMsg = msgContext.getResponseMessage();
		String logMessage = "";
		if (outMsg != null) {
			if (outMsg.getSOAPPart() != null) {
				logMessage += "RESPONSE\n" + StringEscapeUtils.unescapeXml(outMsg.getSOAPPartAsString()) + "\n";
				
				if (logger.isDebugEnabled()) {
					//logger.debug("RESPONSE\n" + StringEscapeUtils.unescapeXml(outMsg.getSOAPPartAsString()));
					logger.debug(logMessage);
				}
			}
		} else if (inMsg != null) {
			//logger.debug("Timeout: " + ((msgContext.getTimeout() / 1000) / 60) + " min");
			logMessage += "Timeout: " + ((msgContext.getTimeout() / 1000) / 60) + " min \n";
			final OperationDesc operation = msgContext.getOperation();
			if (operation != null) {
				//logger.debug("Operation Name: " + operation.getName());
				logMessage += "Operation Name: " + operation.getName() + "\n";
			}
			if (inMsg.getSOAPPart() != null) {
				//logger.info("REQUEST\n" + StringEscapeUtils.unescapeXml(inMsg.getSOAPPartAsString()));
				logMessage += "REQUEST\n" + StringEscapeUtils.unescapeXml(inMsg.getSOAPPartAsString()) + "\n";
			}
		}
	}// logMessage

}
