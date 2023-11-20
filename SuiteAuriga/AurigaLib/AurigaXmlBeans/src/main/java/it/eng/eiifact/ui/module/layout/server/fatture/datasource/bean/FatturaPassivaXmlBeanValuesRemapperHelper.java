/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class FatturaPassivaXmlBeanValuesRemapperHelper extends DeserializationHelper{

	protected String tipoRegFatture;
	
	public FatturaPassivaXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
		tipoRegFatture = (String) remapConditions.get("tipoRegFatture");
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		FatturaPassivaXmlBean node = (FatturaPassivaXmlBean)obj;
		
		if(node.getStatoRegistrazione() != null) {
			if(node.getStatoRegistrazione().toUpperCase().contains("DA_INVIARE")) {
				node.setStatoRegistrazione("Da inviare");						
			} else if(node.getStatoRegistrazione().toUpperCase().contains("INVIATA")){						
				node.setStatoRegistrazione("Inviata");
			} else if(node.getStatoRegistrazione().toUpperCase().contains("REGISTRATA")){						
				node.setStatoRegistrazione("Registrata");
			}
		}
		if(node.getDescStatoDettFattura() != null && ("da firmare e stornare".equals(node.getDescStatoDettFattura())    || 
                "rifiutata da SdI".equals(node.getDescStatoDettFattura())         || 
                "da firmare".equals(node.getDescStatoDettFattura())               || 
                "ricevuto esito negativo".equals(node.getDescStatoDettFattura())  || 
                "fattura non recapitabile".equals(node.getDescStatoDettFattura()) ||
                "CARICATA CON WARNING - DA RIGENERARE".equals(node.getDescStatoDettFattura().toUpperCase()) 
               )
		  ) {
				//Per abilitare la modifica guardo solo lo stato della modifica, e non se Ã¨ stata inserita manualmente o meno
				//node.setFlgAbilModifica(node.getFlgInsManuale() != null && "1".equals(node.getFlgInsManuale()));
				node.setFlgAbilModifica(true);
			}
		
		// La modifica e' abilitata se e' una fattura inserita manualmente PDF   
		if ( ( node.getFlgInsManualeNoXml() != null && "1".equals(node.getFlgInsManualeNoXml()) )  
		   ){
			node.setFlgAbilModifica(true);
		}
		
		// La visualizzazione del dettaglio e' abilitata se e' una fattura inserita manualmente XML  oppure una fattura inserita manualmente PDF   
		if ( ( node.getFlgInsManuale()      != null && "1".equals(node.getFlgInsManuale())      ) ||
			 ( node.getFlgInsManualeNoXml() != null && "1".equals(node.getFlgInsManualeNoXml()) )  
		   ){
			node.setFlgAbilView(true);
		}
		
		String importo = node.getImportoFattura();
        if(importo!=null && !importo.equalsIgnoreCase("")){
            importo = importo.replace(".", "");
            importo = importo.replace(",", ".");
            try
            {
              float f = Float.valueOf(importo.trim()).floatValue();
              node.setImportoFatturaNumeric(f);
            }
            catch (NumberFormatException nfe)
            {}
        }
        
	}
}
