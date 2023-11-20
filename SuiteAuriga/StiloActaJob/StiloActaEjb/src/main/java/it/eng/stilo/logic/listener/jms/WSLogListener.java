/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.util.jms.JmsUtil;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for Web Service log messages.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = JmsUtil.JMS_QUEUE_LOG),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = JmsUtil.JMS_LOG_TYPE + " = '"
                + JmsUtil.JMS_LOG_WS + "'")},
        mappedName = JmsUtil.JMS_QUEUE_LOG)
public class WSLogListener extends LogListener {

    @Override
    protected void processMessage(TextMessage textMessage) {
        logger.info("[TextMessage-received]");

        final DocumentLog documentLog = new DocumentLog();
        try {
            documentLog.setFlowId(textMessage.getStringProperty(JmsUtil.JMS_LOG_ID_FLOW));

            final Object refEntity = textMessage.getObjectProperty(JmsUtil.JMS_LOG_REF_ENTITY);
            if (refEntity != null) {
                documentLog.setQueueDocument(genericDataAccessEJB.load(QueueDocument.class, refEntity));
            }

            final Boolean outBound = textMessage.getBooleanProperty(JmsUtil.JMS_LOG_OUT_BOUND);
            documentLog.setOutBound(outBound);

            saveLog(textMessage.getText(), documentLog);
        } catch (DatabaseException | JMSException e) {
            logger.error("", e);
        }
    }


}
