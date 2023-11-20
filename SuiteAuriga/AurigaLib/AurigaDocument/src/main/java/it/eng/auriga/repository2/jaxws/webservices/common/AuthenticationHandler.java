/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Antonio Peluso
 *
 */

public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {

    public static final Log log = LogFactory.getLog(AuthenticationHandler.class);

    /**
     * The method is used to handle all incoming messages and to authenticate
     * the user
     * 
     * @param context
     *            The message context which is used to retrieve the username and
     *            the password
     * @return True if the method was successfully handled and if the request
     *         may be forwarded to the respective handling methods. False if the
     *         request may not be further processed.
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        // Only inbound messages must be authenticated
        boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!isOutbound) {
            // Authenticate the call
            try {
				AurigaWSAuthentication.authenticate(context);
			} catch (Exception e) {
				generateSOAPErrMessage(
                        context.getMessage(),
                        "401",
                        "Non Autorizzato", e.getMessage());
				return false;
			}           	                        
        }

        return true;
    }

    /**
     * Generate a SOAP error message
     * 
     * @param msg
     *            The SOAP message
     * @param code
     *            The error code
     * @param reason
     *            The reason for the error
     */
    private void generateSOAPErrMessage(SOAPMessage msg, String code,
            String reason, String detail) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultCode(code);
            soapFault.setFaultString(reason);

            // Manually crate a failure element in order to guarentee that this
            // authentication handler returns the same type of soap fault as the
            // rest
            // of the application
            QName failureElement = new QName("http://yournamespacehere.com", "Failure", "ns3");
            QName codeElement = new QName("Code");
            QName reasonElement = new QName("Reason");
            QName detailElement = new QName("Detail");

            soapFault.addDetail().addDetailEntry(failureElement)
                    .addChildElement(codeElement).addTextNode(code)
                    .getParentElement().addChildElement(reasonElement)
                    .addTextNode(reason).getParentElement()
                    .addChildElement(detailElement).addTextNode(detail);

            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
        }
    }

    /**
     * Handles faults
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // do nothing
        return false;
    }

    /**
     * Close - not used
     */
    @Override
    public void close(MessageContext context) {
        // do nothing

    }

    /**
     * Get headers - not used
     */
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}