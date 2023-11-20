/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.OperationResult;
import it.eng.stilo.logic.service.ActaIntegrationEJB;
import it.eng.stilo.model.core.QueueDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.ItemProcessor;
import javax.ejb.EJB;
import javax.inject.Named;


@Named
public class ActaClassificationItemProcessor implements ItemProcessor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @EJB
    private ActaIntegrationEJB actaIntegrationEJB;

    @Override
    public Object processItem(Object item) {
        logger.debug(getClass().getSimpleName() + "-processItem");
        final QueueDocument queueDocument = (QueueDocument) item;
        logger.info(String.format("[Document=%d]", queueDocument.getId()));

        try {
            final OperationResult operationResult = actaIntegrationEJB.createClassification(queueDocument);
            logger.info(String.format("[Document=%d][OpResult=%s][Key=%s]", queueDocument.getId(),
                    operationResult.getResultType().name(), operationResult.getKey()));
        } catch (Exception e) {
            logger.error(String.format("[ErrorProcessing-Document=%d]", queueDocument.getId()));
        }

        return queueDocument;
    }

}
