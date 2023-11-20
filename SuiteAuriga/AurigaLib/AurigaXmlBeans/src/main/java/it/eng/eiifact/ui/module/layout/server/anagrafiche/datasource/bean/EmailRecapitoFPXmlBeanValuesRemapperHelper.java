/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class EmailRecapitoFPXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public EmailRecapitoFPXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		EmailRecapitoFPXmlBean currentBean = (EmailRecapitoFPXmlBean)obj;		
		currentBean.setDenominazione(riga.getColonna().get(4).getContent());
	}
}