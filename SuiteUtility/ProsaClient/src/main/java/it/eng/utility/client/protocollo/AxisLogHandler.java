package it.eng.utility.client.protocollo;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;

public class AxisLogHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AxisLogHandler.class);

	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		logMessage(msgContext);
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
		if (!logger.isDebugEnabled())
			return;
		Message inMsg = msgContext.getRequestMessage();
		Message outMsg = msgContext.getResponseMessage();
		if (outMsg != null) {
			if (outMsg.getSOAPPart() != null) {
				logger.debug("RESPONSE\n" + outMsg.getSOAPPartAsString());
			}
		} else if (inMsg != null) {
			logger.debug("Timeout: " + ((msgContext.getTimeout() / 1000) / 60) + " min");
			final OperationDesc operation = msgContext.getOperation();
			if (operation != null) {
				logger.debug("Operation Name: " + operation.getName());
			}
			if (inMsg.getSOAPPart() != null) {
				logger.debug("REQUEST\n" + inMsg.getSOAPPartAsString());
			}
		}
	}// logMessage

}
