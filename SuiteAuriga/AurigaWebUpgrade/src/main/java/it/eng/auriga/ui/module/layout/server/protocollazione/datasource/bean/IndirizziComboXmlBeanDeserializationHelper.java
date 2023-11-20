/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class IndirizziComboXmlBeanDeserializationHelper extends DeserializationHelper{

	public IndirizziComboXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		LoadComboIndirizzoSoggettoBean currentBean = (LoadComboIndirizzoSoggettoBean)obj;
		
		if (currentBean.getIndirizzoDisplay() == null)			
		    currentBean.setIndirizzoDisplay("");
	}
}
