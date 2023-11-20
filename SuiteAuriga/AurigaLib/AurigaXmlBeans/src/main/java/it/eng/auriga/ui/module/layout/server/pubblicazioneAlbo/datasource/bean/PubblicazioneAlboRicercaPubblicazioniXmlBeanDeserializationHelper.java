package it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean;

import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;



public class PubblicazioneAlboRicercaPubblicazioniXmlBeanDeserializationHelper extends DeserializationHelper {

	public PubblicazioneAlboRicercaPubblicazioniXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
	}
	
	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
	}
}
