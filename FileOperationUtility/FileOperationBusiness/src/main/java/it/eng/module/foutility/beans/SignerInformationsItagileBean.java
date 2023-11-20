/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult.SignerInformations;

/**
 * 
 * @author DANCRIST
 *
 */

public class SignerInformationsItagileBean {
	
	private SignerInformations signerInformations;
	private boolean allValid;
	
	public SignerInformations getSignerInformations() {
		return signerInformations;
	}
	public void setSignerInformations(SignerInformations signerInformations) {
		this.signerInformations = signerInformations;
	}
	public boolean isAllValid() {
		return allValid;
	}
	public void setAllValid(boolean allValid) {
		this.allValid = allValid;
	}

}