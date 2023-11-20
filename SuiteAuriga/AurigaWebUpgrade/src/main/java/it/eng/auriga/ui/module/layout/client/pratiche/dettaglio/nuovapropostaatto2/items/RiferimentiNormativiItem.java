/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.common.items.SelezionaValoriDizionarioItem;

public class RiferimentiNormativiItem extends SelezionaValoriDizionarioItem {

	@Override
	public String getDictionaryEntry() {
		return "RIFERIMENTI_NORMATIVI";
	}

	@Override
	public boolean isRequiredStrInDes() {
		return false;
	}
	
	@Override
	public boolean getAddUnknownValues() {
		return true;
	}

}
