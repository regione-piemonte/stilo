/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class DataSigner {

	private List<AbstractSigner> signersManager;

	public List<AbstractSigner> getSignersManager() {
		return signersManager;
	}

	public void setSignersManager(List<AbstractSigner> signersManager) {
		this.signersManager = signersManager;
	}
	
	
	
}
