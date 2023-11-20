/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.util.jms.JmsUtil;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for generic log messages.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = JmsUtil.JMS_QUEUE_LOG),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue =
                JmsUtil.JMS_LOG_TYPE + " = '" + JmsUtil.JMS_LOG_GENERIC + "'")},
        mappedName = JmsUtil.JMS_QUEUE_LOG)
public class GenericLogListener extends LogListener {

    @Override
    protected void processMessage(TextMessage textMessage) {
        logger.info("[TextMessage-received]" + JmsUtil.JMS_LOG_GENERIC);
    }

}
