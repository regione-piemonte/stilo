/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;


public abstract class AbstractLogHandler implements SOAPHandler<SOAPMessageContext> {

    public static final String KEY_DOC_REF = "docRef";
    public static final String KEY_DOCALL_REF = "docALLRef";
    public static final String KEY_FLOW_ID = "flowId";
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logger.info(getClass().getSimpleName() + "-handleMessage()");
        return logSoapMessage(context);
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.info(getClass().getSimpleName() + "-handleFault()");
        return logSoapMessage(context);
    }

    @Override
    public void close(MessageContext context) {
    }

    private boolean logSoapMessage(final SOAPMessageContext context) {
        final Object referenceDocument = context.get(KEY_DOC_REF);
        logger.info("PropertyFound-" + KEY_DOC_REF + "[" + referenceDocument + "]");
        
        final Object referenceDocumentAttach = context.get(KEY_DOCALL_REF );
        logger.info("PropertyFound-" + KEY_DOCALL_REF + "[" + referenceDocumentAttach + "]");

        final Object flowId = context.get(KEY_FLOW_ID);
        logger.info("PropertyFound-" + KEY_FLOW_ID + "[" + flowId.toString() + "]");

        final Boolean isOutBound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final SOAPMessage soapMsg = context.getMessage();

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            soapMsg.writeTo(baos);
            logger.info(baos.toString());
            System.out.println(":::::::::::::::::::baos.toString()  "+ baos.toString());
            if( referenceDocumentAttach!=null )
            	send(baos.toString(), referenceDocumentAttach, flowId, isOutBound);
            else
            	send(baos.toString(), referenceDocument, flowId, isOutBound);
        } catch (Exception e) {
            logger.error("", e);
        }


        return true;
    }

    protected abstract void send(final String text, final Object referenceDocumentc, final Object flowId,
            final Boolean isOutBound) throws Exception;

}
