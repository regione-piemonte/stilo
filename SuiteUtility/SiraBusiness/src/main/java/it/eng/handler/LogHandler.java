package it.eng.handler;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

public class LogHandler extends BasicHandler {
	  
	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		Message req = msgContext.getRequestMessage();
		String lString = req.getSOAPPartAsString();
		System.out.println(lString);
	}
}
