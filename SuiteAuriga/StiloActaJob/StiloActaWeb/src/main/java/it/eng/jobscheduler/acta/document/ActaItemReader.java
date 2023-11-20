/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.search.LimitMode;
import it.eng.stilo.logic.search.QueryAttributes;
import it.eng.stilo.logic.search.SortMode;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.model.core.QueueDocument_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import java.util.List;

public abstract class ActaItemReader extends AbstractItemReader {

    private static final String DOCUMENT_MAX_PROPERTY = "job-document-max";
    private static final String DOCUMENT_MAX_DEFAULT = "1";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected List<QueueDocument> queueDocuments;
    private int index = 0;

    @Inject
    private JobContext jobContext;

    protected QueryAttributes createQueryAttributes() {
        final QueryAttributes queryAttributes = new QueryAttributes();
        int numMaxRecord = Integer.valueOf(jobContext.getProperties().getProperty(DOCUMENT_MAX_PROPERTY,
                DOCUMENT_MAX_DEFAULT));
        logger.debug("imposto il numMax record a " + numMaxRecord);
        queryAttributes.setMax(numMaxRecord);
        queryAttributes.setLimitMode(LimitMode.LIMITED);
        queryAttributes.setOrderField(QueueDocument_.INSERTED);
        queryAttributes.setSortMode(SortMode.ASCENDING);

        return queryAttributes;
    }
    
    protected int getNumMaxRecord(){
    	int numMaxRecord = Integer.valueOf(jobContext.getProperties().getProperty(DOCUMENT_MAX_PROPERTY,
                DOCUMENT_MAX_DEFAULT));
        logger.debug("imposto il numMax record a " + numMaxRecord);
        return numMaxRecord;
    }

    @Override
    public QueueDocument readItem() {
        logger.info(getClass().getSimpleName() + "-readItem");
        return index < queueDocuments.size() ? queueDocuments.get(index++) : null;
    }

}
