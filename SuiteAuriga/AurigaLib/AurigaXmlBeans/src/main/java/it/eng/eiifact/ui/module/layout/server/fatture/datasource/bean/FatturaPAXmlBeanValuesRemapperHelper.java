/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class FatturaPAXmlBeanValuesRemapperHelper extends DeserializationHelper{

	public FatturaPAXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		FatturaPAXmlBean node = (FatturaPAXmlBean)obj;
		
		if(node.getTipoInvioEmail() != null) {
			if(node.getTipoInvioEmail().contains("PEC")) {
				node.setEmailInviataFlgPEC(Boolean.TRUE);						
			} else if(node.getTipoInvioEmail().contains("PEO")){						
				node.setEmailInviataFlgPEO(Boolean.TRUE);
			} else if(node.getTipoInvioEmail().contains("INTEROP")){						
				node.setEmailInviataFlgInterop(Boolean.TRUE);
			}
		}
		
		if(node.getStato() != null && ("da firmare e stornare".equals(node.getStato())    || 
				                       "rifiutata da SdI".equals(node.getStato())         || 
				                       "da firmare".equals(node.getStato())               || 
				                       "ricevuto esito negativo".equals(node.getStato())  || 
				                       "fattura non recapitabile".equals(node.getStato()) ||
				                       "CARICATA CON WARNING - DA RIGENERARE".equals(node.getStato().toUpperCase()) 
				                      )
           ) {
			//Per abilitare la modifica guardo solo lo stato della modifica, e non se è stata inserita manualmente o meno
			//node.setFlgAbilModifica(node.getFlgInsManuale() != null && "1".equals(node.getFlgInsManuale()));
			node.setFlgAbilModifica(true);
		}
		
		String importo = node.getImporto();
        if(importo!=null && !importo.equalsIgnoreCase("")){
            importo = importo.replace(".", "");
            importo = importo.replace(",", ".");
            try
            {
              float f = Float.valueOf(importo.trim()).floatValue();
              node.setImportoNumeric(f);
            }
            catch (NumberFormatException nfe)
            {}
        }
        
        String siglaEnumeroFattura = node.getNumeroFattura();
        
        if(siglaEnumeroFattura!=null && !siglaEnumeroFattura.equalsIgnoreCase("")){
        	int i = 0;
        	String sigla = "";
        	String numero = "";
        	// Cerco il trattino "-" che separa la sigla dal numero
        	i = siglaEnumeroFattura.indexOf("-");
        	if( i > 0){
        		// prendo la parte sinistra
        		sigla = siglaEnumeroFattura.substring(0,i);
        		
        		// prendo la parte destra
        		numero = siglaEnumeroFattura.substring(i+1);
        		
        		// formatto il numero con gli '0'
        		numero = StringUtils.leftPad(numero, 20, '0');
        		
        		// ricompongo il numero fattura 
        		siglaEnumeroFattura = sigla + "-" + numero;
        		
        		node.setNumeroFatturaFormatted(siglaEnumeroFattura);
        	}
        	// Cerca il primo numero dopo la sigla
        	else{
        		i = trovaNumero(siglaEnumeroFattura);   
        		
        		if( i > 0){
        			// prendo la parte sinistra
        			sigla = siglaEnumeroFattura.substring(0,i);
        			
        			// prendo la parte destra
            		numero = siglaEnumeroFattura.substring(i);
            		
            		// formatto il numero con gli '0'
            		numero = StringUtils.leftPad(numero, 20, '0');
            		
            		// ricompongo il numero fattura 
            		siglaEnumeroFattura = sigla + numero;
            		
            		node.setNumeroFatturaFormatted(siglaEnumeroFattura);
        		}
        		else{
        			numero = siglaEnumeroFattura.substring(i);
        			
        			// formatto il numero con gli '0'
            		numero = StringUtils.leftPad(numero, 20, '0');
            		
            		node.setNumeroFatturaFormatted(numero);
        		}
        	}        		
        }
	}
	
	
	private int trovaNumero(String siglaEnumeroFattura)throws Exception {
		
		for(int i=0;i<siglaEnumeroFattura.length();i++)
		{
		   if(Character.isDigit(siglaEnumeroFattura.charAt(i)))
			   return i;
		}		
		return 0;
	}
	
	
}
