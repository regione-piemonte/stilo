/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.option.ArubaOption;
import it.eng.hsm.client.option.ClientOption;
import it.eng.hsm.client.option.MedasOption;

public class HsmClientOptionFactory {

	public static ClientOption getClientOption(HsmType hsmType) {
		ClientOption clientOption = null;
		if (hsmType.equals(HsmType.ARUBA)) {
			clientOption = getArubaClientOption();
		} else if (hsmType.equals(HsmType.MEDAS)) {
			clientOption = getMedasClientOption();
		} else if (hsmType.equals(HsmType.INFOCERT)) {
			clientOption = getInfoCertClientOption();
		}
		return clientOption;
	}

	protected static ArubaOption getArubaClientOption() {
		return null;
	}

	protected static MedasOption getMedasClientOption() {
		MedasOption medasOption = new MedasOption();
		medasOption.setDocType("ANYDOC");
		return medasOption;
	}

	protected static ClientOption getInfoCertClientOption() {
		return null;
	}

}
