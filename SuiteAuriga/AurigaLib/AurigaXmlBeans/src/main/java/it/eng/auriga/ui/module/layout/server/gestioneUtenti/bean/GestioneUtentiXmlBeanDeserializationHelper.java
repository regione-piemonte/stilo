/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class GestioneUtentiXmlBeanDeserializationHelper extends DeserializationHelper {

	public GestioneUtentiXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		GestioneUtentiXmlBean bean = (GestioneUtentiXmlBean)obj; 
				
		if(bean.getDesUser() != null){			
			String [] split = bean.getDesUser().split("\\|");			
			if(split!=null){				
				String cognome = null;
				String nome = null;				
				if(split.length>0){
					cognome = split[0].toString();	
				}
				if(split.length>1){
					nome = split[1].toString();	
				}				
				String cognomeNome = "";
				if (cognome != null){
					cognomeNome = cognomeNome + cognome.trim() + " ";
				}					
				if (nome != null){
					cognomeNome = cognomeNome + nome.trim();
				}
				bean.setCognomeNome(cognomeNome);				
			}		
		}	
	}
}