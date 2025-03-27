/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneAtti.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author dbe4235
 *
 */

public class TSOXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public TSOXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		TSOXmlBean bean = (TSOXmlBean) obj;
		
	}

}
