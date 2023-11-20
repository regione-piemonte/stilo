/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.acta.ws.handler.AbstractLogHandler;
import it.eng.stilo.util.jms.JmsUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class LogHandler extends AbstractLogHandler {

    public static final BlockingQueue<MessageBean> queue = new ArrayBlockingQueue<>(50);

    @Override
    protected void send(final String text, final Object referenceDocument, final Object flowId,
            final Boolean isOutBound) {

        final MessageBean messageBean = new MessageBean();
        messageBean.setLogType(JmsUtil.JMS_LOG_WS);
        messageBean.setRefDocument(referenceDocument);
        messageBean.setFlowId(flowId.toString());
        messageBean.setOutBound(isOutBound);
        messageBean.setText(text);

        try {
            queue.put(messageBean);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }
    
    public static void sendMessageError(final String text, final Object referenceDocument, final Object flowId,
            final Boolean isOutBound){
    	final MessageBean messageBean = new MessageBean();
        messageBean.setLogType(JmsUtil.JMS_LOG_WS);
        messageBean.setRefDocument(referenceDocument);
        messageBean.setFlowId(flowId.toString());
        messageBean.setOutBound(isOutBound);
        messageBean.setText(text);

        try {
            queue.put(messageBean);
        } catch (InterruptedException e) {
            //logger.error("", e);
        }
    }

}
