/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.service.GenericDataAccessEJB;
import it.eng.stilo.model.core.DocumentLog;
import it.eng.stilo.model.core.QueueDocument;
import it.eng.stilo.util.xml.XMLBasicParser;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/**
 * Generic abstract class for log messages.
 */
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public abstract class AbstractLogListener<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    @EJB(beanName = "genericDataAccessEJB")
    protected GenericDataAccessEJB<QueueDocument> genericDataAccessEJB;

    protected abstract void processMessage(T textMessage);

    protected void saveLog(final String text, final DocumentLog documentLog) {
        try {
        	logger.info("messaggio:::: " + text);
            if( documentLog.getOutBound()==null ){
            	documentLog.setValue(text);
            } else {
	        	final XMLBasicParser parser = new XMLBasicParser();
	            final Document document = parser.parse(new StringReader(text));
	            Arrays.stream(HiddenElement.values()).forEach(hElem -> parser.setText(document, hElem.name,
	                    hElem.subName, hElem.replacement));
	            documentLog.setValue(parser.format(document, Format.getCompactFormat()));
            }
            logger.info("PRIMA DI PERSISTENT:::: " + documentLog.getValue() );
           
            genericDataAccessEJB.persist(documentLog);
        } catch (DatabaseException | IOException | JDOMException e) {
            logger.error("", e);
        }
    }

    private enum HiddenElement {

        REPOSITORY_ID("repositoryId", "value", ".."),
        PRINCIPAL_ID("principalId", "value", ".."),
        STREAM("stream", "streamMTOM", "..");

        private String name;
        private String subName;
        private String replacement;

        HiddenElement(final String eName, final String eSubName, final String eReplacement) {
            this.name = eName;
            this.subName = eSubName;
            this.replacement = eReplacement;
        }
    }
}
