/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jobscheduler.acta.document.ActaDocumentItemWriter;

import javax.inject.Named;
import java.util.List;


@Named
public class ActaClassificationItemWriter extends ActaDocumentItemWriter {

    @Override
    public void writeItems(List<Object> items) {
        logger.debug(getClass().getSimpleName() + "-writeItems");
        super.writeItems(items);
    }

}
