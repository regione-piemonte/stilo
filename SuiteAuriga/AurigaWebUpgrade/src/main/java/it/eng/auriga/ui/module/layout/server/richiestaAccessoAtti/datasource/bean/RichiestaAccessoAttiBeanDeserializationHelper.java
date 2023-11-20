/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;

public class RichiestaAccessoAttiBeanDeserializationHelper extends RichiestaAccessoAttiXmlBeanDeserializationHelper {

	public RichiestaAccessoAttiBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		super.remapValues(obj, objValues);
		
		RichiestaAccessoAttiBean bean = (RichiestaAccessoAttiBean) obj;

		bean.setPrelievoEffettuato(bean.getStatoPrelievo() != null && "effettuato".equals(bean.getStatoPrelievo()));
	}
	
}
