package it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean;

import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.IstanzePortaleRiscossioneXmlBean;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

import java.util.Map;

public class IstanzePortaleRiscossioneXmlBeanDeserializationHelper extends DeserializationHelper {

	protected String idNode;

	public IstanzePortaleRiscossioneXmlBeanDeserializationHelper(Map<String, String> remapConditions) {

		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {

		IstanzePortaleRiscossioneXmlBean bean = (IstanzePortaleRiscossioneXmlBean) obj;

	}
}