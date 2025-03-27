/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.archivio.datasource.bean;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ConcessioneComboXmlBean;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class ConcessioneComboXmlBeanDeserializationHelper extends DeserializationHelper{

	public ConcessioneComboXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		ConcessioneComboXmlBean currentBean = (ConcessioneComboXmlBean)obj;
		currentBean.setIdCodice(currentBean.getCodice());
	}
}
