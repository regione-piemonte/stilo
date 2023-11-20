/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jobscheduler.acta.document.ActaDocumentItemProcessor;

import javax.inject.Named;


@Named
public class ActaAttachmentItemProcessor extends ActaDocumentItemProcessor {

    @Override
    public Object processItem(Object item) {
        logger.debug(getClass().getSimpleName() + "-processItem");

        return super.processItem(item);
    }

}
