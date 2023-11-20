/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class OrdiniXmlBeanValuesRemapperHelper extends DeserializationHelper {
	
	public OrdiniXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		OrdiniXmlBean currentBean = (OrdiniXmlBean)obj;
		//currentBean.setNumeroFattura(currentBean.getSiglaFattura());
		if(currentBean.getNumeroFattura()!=null && !currentBean.getNumeroFattura().equals(""))
		{
		 String [] splits = currentBean.getNumeroFattura().split("#");
		 currentBean.setOrdAssegnazione(switchValAssegnazione(splits[0], currentBean.getCodApplOwner()));
		 currentBean.setOrdClassificazione(switchValClassificazione(splits[1], currentBean.getCodApplOwner()));
		}
		currentBean.setAbilViewFilePrimario("1");
		
	}
    
	public String switchValAssegnazione(String val, String societa) {
		String ret ="";
//		 switch(val) 
//	        { 
//	            case "1": 
//	                ret="CUSTOMER care";
//	                break; 
//	            case "2": 
//	            	ret="AMMINISTRAZIONE CESALS"; 
//	                break; 
//	            case "3": 
//	            	ret="CNAT";
//	                break;
//	            case "4": 
//	            	ret="LOGISTICA";
//	                break;     
//	            default:
//	            	ret="";
//	        } 
		ret = remapConditions.get("ASSEGNAZIONE#"+societa+"#"+val);
		if(ret==null) ret = "";
		return ret;
	}
	
	public String switchValClassificazione(String val, String societa) {
		String ret ="";
//		 switch(val) 
//	        { 
//	            case "1": 
//	                ret="ORDINE MATERIALE";
//	                break; 
//	            case "2": 
//	            	ret="ORDINE DI PROGETTO"; 
//	                break; 
//	            case "3": 
//	            	ret="ORDINE DI MANUTENZIONE";
//	                break;
//	            case "4": 
//	            	ret="ORDINE DI NOLEGGIO BOMBOLE";
//	                break;
//	            case "5": 
//	            	ret="ORDINE LIQUIDO";
//	                break;
//	            case "6": 
//	            	ret="ORDINE GASSOSO";
//	                break;
//	            case "7": 
//	            	ret="ORDINE PER CANONE Servizio";
//	                break;
//	            case "8": 
//	            	ret="ALTRO";
//	                break;	                
//	            default:
//	            	ret="";
//	        } 
		ret = remapConditions.get("CLASSIFICAZIONE#"+societa+"#"+val);
		if(ret==null) ret = "";
		return ret;
	}
}
