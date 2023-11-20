/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.acta.ws.handler.queue.LogHandler;
import it.eng.acta.ws.handler.queue.MessageBean;
import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.listener.AbstractLogListener;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.model.core.QueueDocument;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.concurrent.BlockingQueue;

/**
 * Class for {@link MessageBean} log messages.
 */
@Singleton
public class LogListener extends AbstractLogListener<MessageBean> {

    private final BlockingQueue<MessageBean> queue = LogHandler.queue;

    @Schedule(second = "0/15", minute = "*", hour = "*", persistent = false)
    private void checkMessage() {
        logger.info(getClass().getSimpleName() + "-checkMessage");
        int maxItems = 20;
        while (maxItems > 0) {
            MessageBean messageBean = queue.poll();
            if (messageBean != null) {
                processMessage(messageBean);
                maxItems--;
            } else {
                maxItems = 0;
            }
        }
    }

    @Override
    protected void processMessage(final MessageBean messageBean) {
        logger.info("[MessageBean-received]");

        final DocumentLog documentLog = new DocumentLog();
        try {
            documentLog.setFlowId(messageBean.getFlowId());

            final Object refEntity = messageBean.getRefDocument();
            if (refEntity != null) {
                documentLog.setQueueDocument(genericDataAccessEJB.load(QueueDocument.class, refEntity));
            }

            final Boolean outBound = messageBean.getOutBound();
            documentLog.setOutBound(outBound);

            saveLog(messageBean.getText(), documentLog);
        } catch (DatabaseException e) {
            logger.error("", e);
        }
    }

}
