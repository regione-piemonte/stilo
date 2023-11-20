/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jobscheduler.acta.document.ActaItemReader;
import it.eng.stilo.logic.service.QueueDocumentsEJB;

import javax.ejb.EJB;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class ActaAttachmentItemReader extends ActaItemReader {

    @EJB
    private QueueDocumentsEJB queueDocumentsEJB;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        logger.debug(getClass().getSimpleName() + "-open");

        logger.debug("Cerco gli allegati da reuperare");
        queueDocuments = queueDocumentsEJB.findFixableAttachment(createQueryAttributes());
        logger.info("[#Documents]" + queueDocuments.size());
    }

}
