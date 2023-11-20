/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class DocumentoMonclerXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public DocumentoMonclerXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		DocumentoMonclerXmlBean currentBean = (DocumentoMonclerXmlBean)obj;
		
		// ottavio : mostro sempre la lente
		currentBean.setAbilViewFilePrimario("1");
	}
}