package it.eng.auriga.ui.module.layout.server.contenutiAmministrazioneTrasparente.datasource.bean;

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class ContenutiAmmTraspXmlBeanDeserializationHelper extends DeserializationHelper {

	protected String idSezione;
	
	public ContenutiAmmTraspXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
		idSezione = (String) remapConditions.get("idSezione");		
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		ContenutiAmmTraspXmlBean bean = (ContenutiAmmTraspXmlBean)obj; 
		
		if( bean.getFlgAnnullato() !=null && !bean.getFlgAnnullato().equalsIgnoreCase("") ){
			if (bean.getFlgAnnullato().equalsIgnoreCase("0"))
				bean.setFlgValido("1");
			else
				bean.setFlgValido("0");
		}
		else {
			bean.setFlgValido("1");
		}
		bean.setRecProtetto("0");
		bean.setIdSezione(idSezione);
	}
}