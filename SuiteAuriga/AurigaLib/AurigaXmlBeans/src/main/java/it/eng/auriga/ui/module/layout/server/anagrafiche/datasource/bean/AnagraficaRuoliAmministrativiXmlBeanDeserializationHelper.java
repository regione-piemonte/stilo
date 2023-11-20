/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class AnagraficaRuoliAmministrativiXmlBeanDeserializationHelper extends DeserializationHelper {

	public AnagraficaRuoliAmministrativiXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		AnagraficaRuoliAmministrativiXmlBean bean = (AnagraficaRuoliAmministrativiXmlBean)obj; 
		if( bean.getFlgAnnullato() !=null && !bean.getFlgAnnullato().equalsIgnoreCase("") ){
			if (bean.getFlgAnnullato().equalsIgnoreCase("0"))
				bean.setFlgValido("1");
			else
				bean.setFlgValido("0");
		}
		else {
			bean.setFlgValido("1");
		}
	}
}