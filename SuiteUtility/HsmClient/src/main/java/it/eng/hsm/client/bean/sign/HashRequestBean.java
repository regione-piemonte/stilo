/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.hsm.client.option.SignOption;

public class HashRequestBean {
	
	private String hash;
	private SignOption signOption;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public SignOption getSignOption() {
		return signOption;
	}
	public void setSignOption(SignOption signOption) {
		this.signOption = signOption;
	}
	
	
	
}
