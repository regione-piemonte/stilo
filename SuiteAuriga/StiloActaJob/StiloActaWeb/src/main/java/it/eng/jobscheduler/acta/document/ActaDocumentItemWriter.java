/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.QueueDocumentsEJB;
import it.eng.stilo.model.core.QueueDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;


@Named
public class ActaDocumentItemWriter extends AbstractItemWriter {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @EJB
    private QueueDocumentsEJB queueDocumentsEJB;

    @Override
    public void writeItems(List<Object> items) {
        logger.debug(getClass().getSimpleName() + "-writeItems");

        final List<QueueDocument> queueDocuments =
                items.stream().map(item -> (QueueDocument) item).collect(Collectors.toList());

        logger.debug("Aggiorno " + queueDocuments.size() + " documenti");
        try {
            queueDocumentsEJB.update(queueDocuments);
        } catch (DatabaseException e) {
            logger.error(String.format("[ErrorUpdating-Documents=#%d]", queueDocuments.size()));
        }
    }

}
