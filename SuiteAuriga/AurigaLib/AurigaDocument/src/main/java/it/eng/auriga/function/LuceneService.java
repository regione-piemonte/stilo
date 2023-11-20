/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.function.bean.RetrieveIndexedFieldsBean;
import it.eng.auriga.repository2.lucene.LuceneException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.lucene.LuceneHelper;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.spring.utility.SpringAppContext;

import java.util.Properties;

@Service(name="LuceneService")
public class LuceneService {

	@Operation(name = "retrieveIndexedFields")
	public RetrieveIndexedFieldsBean retrieveIndexedFields(RetrieveIndexedFieldsBean pRetrieveIndexedFieldsBean) throws LuceneException{
		String categoria = pRetrieveIndexedFieldsBean.getIdentificativo();
		Properties handlerProperties = ((LuceneHandler)SpringAppContext.getContext().getBean("LuceneHandler")).getHandlerProperties();
		String path = (String) handlerProperties.get(categoria);
		if("REP_DOC".equals(categoria)) {
			if(handlerProperties.get("RETRIEVE_INDEX_REP_DOC") != null) {
				path = (String) handlerProperties.get("RETRIEVE_INDEX_REP_DOC");
			}				
		}
		pRetrieveIndexedFieldsBean.setResults(LuceneHelper.retrieveIndexedFields(path));
		return pRetrieveIndexedFieldsBean;
	} 
}
