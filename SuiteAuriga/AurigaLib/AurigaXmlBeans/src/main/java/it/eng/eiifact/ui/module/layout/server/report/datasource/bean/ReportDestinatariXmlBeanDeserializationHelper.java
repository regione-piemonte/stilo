/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.eiifact.ui.module.layout.server.report.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class ReportDestinatariXmlBeanDeserializationHelper extends DeserializationHelper{

	public ReportDestinatariXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		ReportDestinatariXmlBean currentBean = (ReportDestinatariXmlBean)obj;	
	}
}
