package it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiPersonali.bean;

import java.util.Map;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.procedimentiPersonali.bean.ProcedimentiPersonaliXmlBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class ProcedimentiPersonaliXmlBeanDeserializationHelper extends DeserializationHelper {
	
	public ProcedimentiPersonaliXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		ProcedimentiPersonaliXmlBean bean = (ProcedimentiPersonaliXmlBean) obj;
		
	}

}