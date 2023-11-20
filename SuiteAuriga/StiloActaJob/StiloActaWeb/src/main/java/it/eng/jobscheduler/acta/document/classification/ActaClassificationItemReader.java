/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jobscheduler.acta.document.ActaItemReader;
import it.eng.stilo.logic.service.QueueDocumentsEJB;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.core.QueueDocument_;
import it.eng.stilo.model.util.EClassificationStatus;

import javax.ejb.EJB;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class ActaClassificationItemReader extends ActaItemReader {

    @EJB
    private QueueDocumentsEJB queueDocumentsEJB;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        logger.debug(getClass().getSimpleName() + "-open");

        final QueueDocument example = new QueueDocument();
        example.setClassified(EClassificationStatus.FIXABLE);
        // find FIXABLE means previous error classifying document
        logger.debug("Cerco le classificazioni da recuperare");
        queueDocuments = queueDocumentsEJB.find(example, QueueDocument.class, QueueDocument_.attachments,
                createQueryAttributes());
        logger.info("[#Documents]" + queueDocuments.size());
    }

}
