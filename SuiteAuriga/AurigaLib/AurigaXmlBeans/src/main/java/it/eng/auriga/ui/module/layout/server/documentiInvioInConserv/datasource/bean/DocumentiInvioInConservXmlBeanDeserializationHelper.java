/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class DocumentiInvioInConservXmlBeanDeserializationHelper extends DeserializationHelper {

	protected String idNode;

	public DocumentiInvioInConservXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
		idNode = (String) remapConditions.get("idNode");
		
	}
	
	@Override
	public void remapValues(Object obj, Riga objValues) throws Exception {

		DocumentoInvioInConservXmlBean bean = (DocumentoInvioInConservXmlBean) obj;

	}
}