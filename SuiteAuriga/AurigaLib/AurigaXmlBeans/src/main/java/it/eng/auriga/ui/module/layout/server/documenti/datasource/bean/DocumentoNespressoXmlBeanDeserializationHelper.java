/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class DocumentoNespressoXmlBeanDeserializationHelper extends DeserializationHelper{

	public DocumentoNespressoXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		DocumentoNespressoXmlBean currentBean = (DocumentoNespressoXmlBean)obj;	
		
		String [] splits = currentBean.getSiglaFattura().split("#");
		currentBean.setSiglaFattura(splits[0].toString());	
		currentBean.setNumeroFattura(splits[1].toString());

		
	}

}
