/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class AttiAutorizzazioneAnnRegXmlBeanDeserializationHelper extends DeserializationHelper {

	public AttiAutorizzazioneAnnRegXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		AttiAutorizzazioneAnnRegXmlBean bean = (AttiAutorizzazioneAnnRegXmlBean)obj;
		if( bean.getFlgAttoChiuso() !=null && !bean.getFlgAttoChiuso().equalsIgnoreCase("") && bean.getFlgAttoChiuso().equalsIgnoreCase("chiuso") ){
			bean.setFlgAttoChiuso("1");
		}
		else {
			bean.setFlgAttoChiuso(null);
		}
	}
}