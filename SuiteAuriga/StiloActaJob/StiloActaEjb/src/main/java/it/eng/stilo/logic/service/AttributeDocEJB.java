/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBException;

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.model.core.QueueDocAttribute;
import it.eng.stilo.model.core.QueueDocAttribute_;

@Stateless
public class AttributeDocEJB extends DataAccessEJB {

    
    /**
     * Get a bytes array from provided uri.
     *
     * @param uri The input uri.
     * @return The bytes array related to provided uri.
     * @throws JAXBException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<QueueDocAttribute> find(final Long idDoc) throws DatabaseException, JAXBException {
        logger.info(getClass().getSimpleName() + "-find[" + idDoc + "]");

        List<QueueDocAttribute> attributes = new ArrayList<QueueDocAttribute>();
        List<QueueDocAttribute> results = loadList(QueueDocAttribute.class, QueueDocAttribute_.idDocument, idDoc);
      for(QueueDocAttribute result : results){
    	  if( result.getIdDocument().equals( idDoc)){
    		  attributes.add( result);
    	  }
      }

        return attributes;
    }

   
}
