package it.eng.auriga.ui.module.layout.server.tipologieDocumentali.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import java.util.Map;

public class UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBeanDeserializationHelper extends DeserializationHelper{

	public UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean currentBean = (UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean)obj;
		
		if(currentBean.getTipo() != null){
			
			if(currentBean.getTipo().equals("GP"))
				currentBean.setDescTipo("Sub-profilo");
			else 
				currentBean.setDescTipo("U.O.");
		}		
	}
}