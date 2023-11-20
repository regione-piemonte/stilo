package it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean;

import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

public class RegoleProtocollazioneAutomaticaCaselleXmlBeanDeserializationHelper extends DeserializationHelper {

	public RegoleProtocollazioneAutomaticaCaselleXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		RegoleProtocollazioneAutomaticaCaselleXmlBean bean = (RegoleProtocollazioneAutomaticaCaselleXmlBean)obj; 
		bean.setFlgAnnullato("0");
		bean.setFlgValido("1");
	}
}