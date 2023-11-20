/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.acta.ws.handler.AbstractLogHandler;
import it.eng.stilo.util.jms.JmsUtil;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LogHandler extends AbstractLogHandler {

    @Override
    protected void send(final String text, final Object referenceDocument, final Object flowId,
            final Boolean isOutBound) throws NamingException, JMSException {
        // lookup
        final InitialContext initialContext = new InitialContext();
        final ConnectionFactory connectionFactory =
                (ConnectionFactory) initialContext.lookup(JmsUtil.JMS_CONNECTION_FACTORY);
        final Queue queue = (Queue) initialContext.lookup(JmsUtil.JMS_QUEUE_LOG);

        // create
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             MessageProducer producer = session.createProducer(queue)) {
            final TextMessage textMessage = session.createTextMessage(text);
            textMessage.setStringProperty(JmsUtil.JMS_LOG_TYPE, JmsUtil.JMS_LOG_WS);
            textMessage.setObjectProperty(JmsUtil.JMS_LOG_REF_ENTITY, referenceDocument);
            textMessage.setStringProperty(JmsUtil.JMS_LOG_ID_FLOW, flowId.toString());
            textMessage.setBooleanProperty(JmsUtil.JMS_LOG_OUT_BOUND, isOutBound);

            // send
            producer.send(textMessage);
        }
    }

}
