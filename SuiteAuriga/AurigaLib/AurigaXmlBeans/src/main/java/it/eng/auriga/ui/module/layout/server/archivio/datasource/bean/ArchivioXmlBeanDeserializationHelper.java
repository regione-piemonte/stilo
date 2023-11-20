/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.text.SimpleDateFormat;
import java.util.Map;


public class ArchivioXmlBeanDeserializationHelper extends DeserializationHelper {

	protected String idNode;

	public ArchivioXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
		
		idNode = (String) remapConditions.get("idNode");
		
	}
	
	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		ArchivioXmlBean bean = (ArchivioXmlBean) obj;

		if (idNode != null && idNode.equals("D.23")) {
			String segnatura = getColonnaContent(riga, 14);
			bean.setSegnatura(segnatura); //altrimenti sarebbe la colonna 4
			bean.setSegnaturaXOrd(segnatura); //altrimenti sarebbe la colonna 6
			
			String tsRegistrazione = getColonnaContent(riga, 15);
			bean.setTsRegistrazione((tsRegistrazione != null && !tsRegistrazione.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsRegistrazione) : null); //altrimenti sarebbe la colonna 201
		}					        
		
	}
}
