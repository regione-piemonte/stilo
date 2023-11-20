/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author dbe4235
 *
 */

public class RichiesteAutotuteleCedPersonaliXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public RichiesteAutotuteleCedPersonaliXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		RichiesteAutotuteleCedPersonaliXmlBean bean = (RichiesteAutotuteleCedPersonaliXmlBean) obj;
		
	}

}