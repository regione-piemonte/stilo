/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 
 * @author OTTAVIO PASSALACQUA
 *
 */

public class EstremiProtocolloSplitter {	
		
	private static final String delimsLista = ";,\n";
	private static final String delimsEstremi = "-";
	
	public static String[] splitListaEstremiProtocolli(String listaEstremiProtocolloIn) {
		Collection<String> listaEstremiProtocolloOut = new ArrayList<String>();
		if(listaEstremiProtocolloIn != null && !"".equals(listaEstremiProtocolloIn)) {
			String estremiProtocollo = "";
			for(int i = 0; i < listaEstremiProtocolloIn.length(); i++)  {				
				if(!delimsLista.contains("" + listaEstremiProtocolloIn.charAt(i))) {
					estremiProtocollo += listaEstremiProtocolloIn.charAt(i);
				} else if(!"".equals(estremiProtocollo)) {
					listaEstremiProtocolloOut.add(estremiProtocollo);
					estremiProtocollo = "";
				}
			}
			if(!"".equals(estremiProtocollo)) {
				listaEstremiProtocolloOut.add(estremiProtocollo);
			}
		}		
		return listaEstremiProtocolloOut.toArray(new String[0]);
	}
	
	public static String[] splitEstremiProtocollo(String estremiProtocolloIn) {
		Collection<String> estremiProtocolloOut = new ArrayList<String>();
		if(estremiProtocolloIn != null && !"".equals(estremiProtocolloIn)) {
			String estremiProtocollo = "";
			for(int i = 0; i < estremiProtocolloIn.length(); i++)  {				
				if(!delimsEstremi.contains("" + estremiProtocolloIn.charAt(i))) {
					estremiProtocollo += estremiProtocolloIn.charAt(i);
				} else if(!"".equals(estremiProtocollo)) {
					estremiProtocolloOut.add(estremiProtocollo);
					estremiProtocollo = "";
				}
			}
			if(!"".equals(estremiProtocollo)) {
				estremiProtocolloOut.add(estremiProtocollo);
			}
		}		
		return estremiProtocolloOut.toArray(new String[0]);
	}
	
}
