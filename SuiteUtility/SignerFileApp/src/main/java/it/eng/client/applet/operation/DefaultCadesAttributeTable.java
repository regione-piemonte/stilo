package it.eng.client.applet.operation;

import java.util.Hashtable;
import java.util.Map;

import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;

public class DefaultCadesAttributeTable extends DefaultSignedAttributeTableGenerator {

	@Override
	protected Hashtable createStandardAttributeTable(Map parameters) {
		// TODO Auto-generated method stub
		return super.createStandardAttributeTable(parameters);
	}
}