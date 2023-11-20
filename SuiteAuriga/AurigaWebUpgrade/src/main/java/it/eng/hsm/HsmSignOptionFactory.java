/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.option.MedasOption;
import it.eng.hsm.client.option.SignOption;

public class HsmSignOptionFactory {

	public static SignOption getSignOption(HsmType hsmType) {
		SignOption signOption = null;
		if (hsmType.equals(HsmType.ARUBA)) {
			signOption = getArubaSignOption();
		} else if (hsmType.equals(HsmType.MEDAS)) {
			signOption = getMedasSignOption();
		} else if (hsmType.equals(HsmType.INFOCERT)) {
			signOption = getInfoCertSignOption();
		}
		return signOption;
	}

	protected static SignOption getArubaSignOption() {
		return null;
	}
	
	protected static SignOption getMedasSignOption() {
		SignOption signOption = new SignOption();
		MedasOption medasOption = HsmClientOptionFactory.getMedasClientOption();
		signOption.setClientOption(medasOption);
		return signOption;
	}

	protected static SignOption getInfoCertSignOption() {
		return null;
	}

}
