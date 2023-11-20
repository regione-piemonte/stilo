/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class ProcedimentiInIterXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public ProcedimentiInIterXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		ProcedimentiInIterXmlBean bean = (ProcedimentiInIterXmlBean) obj;
		
	}

}