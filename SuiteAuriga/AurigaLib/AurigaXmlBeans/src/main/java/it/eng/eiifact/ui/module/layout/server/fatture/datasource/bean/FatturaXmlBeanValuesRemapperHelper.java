/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class FatturaXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public FatturaXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		FatturaXmlBean currentBean = (FatturaXmlBean)obj;
		
		if(currentBean.getSiglaFattura()!=null && !currentBean.getSiglaFattura().equalsIgnoreCase("")){
			if(currentBean.getSiglaFattura().contains("#")) {
				String [] splits = currentBean.getSiglaFattura().split("#");
				currentBean.setSiglaFattura(splits[0].toString());	
				currentBean.setNumeroFattura(splits[1].toString());
			}
		}
		
		// ottavio : Ho messo momentaneamente la sigla. Bisogna chiedere a valentina di 
		// gestire la descrizione !!
		currentBean.setDescSiglaFattura(currentBean.getSiglaFattura()); 	
		
		// ottavio : mostro sempre la lente
		currentBean.setAbilViewFilePrimario("1");
	}
}
