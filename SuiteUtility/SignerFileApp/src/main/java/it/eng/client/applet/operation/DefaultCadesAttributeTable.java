/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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