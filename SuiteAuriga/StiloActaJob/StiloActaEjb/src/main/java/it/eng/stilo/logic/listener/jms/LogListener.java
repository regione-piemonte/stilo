/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.listener.AbstractLogListener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Abstract class implementing JMS MessageListener for TextMessage log messages.
 */
public abstract class LogListener extends AbstractLogListener<TextMessage> implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            logger.warn("[NoTextMessage]");
        } else {
            final TextMessage textMessage = (TextMessage) message;
            processMessage(textMessage);
        }
    }

    @Override
    protected abstract void processMessage(TextMessage textMessage);

}
