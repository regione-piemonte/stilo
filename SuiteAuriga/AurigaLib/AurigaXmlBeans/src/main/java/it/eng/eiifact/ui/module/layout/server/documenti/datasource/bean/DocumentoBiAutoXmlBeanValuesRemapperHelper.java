/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class DocumentoBiAutoXmlBeanValuesRemapperHelper extends DeserializationHelper {

	public DocumentoBiAutoXmlBeanValuesRemapperHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		DocumentoBiAutoXmlBean currentBean = (DocumentoBiAutoXmlBean)obj;
		if(currentBean.getSiglaEnumeroDocumento()!=null && !currentBean.getSiglaEnumeroDocumento().equalsIgnoreCase("")){
			String [] splitsSigla = currentBean.getSiglaEnumeroDocumento().split("#");
			
			if(splitsSigla.length == 1){
				currentBean.setSiglaDocumento(splitsSigla[0].toString());	
			}
			if(splitsSigla.length == 2){
				currentBean.setSiglaDocumento(splitsSigla[0].toString());
				currentBean.setNumeroDocumento(splitsSigla[1].toString());
			}
		}
		if(currentBean.getTipoServizioValori()!=null && !currentBean.getTipoServizioValori().equalsIgnoreCase("")){
			List<String> entrylist = new ArrayList<String>();
			String [] splitsTipoServizio = currentBean.getTipoServizioValori().split(";");
			if(splitsTipoServizio.length > 0){
				for (String val : splitsTipoServizio){
				      entrylist.add(val.trim());						
					}	
			}
			currentBean.setTipoServizio(entrylist);
			
		}
		if(currentBean.getUriFilePrimario()!=null && !currentBean.getUriFilePrimario().equalsIgnoreCase("")){
			currentBean.setAbilViewFilePrimario("1");
		}
		
		currentBean.setFlgAbilModifica(true);
		   
		
		
	}
}