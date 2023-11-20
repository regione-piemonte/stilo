/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.type.SignerType;

public class SbustamentoBean {

	private File extracttempfile;
	private String nomeFileSbustato = "";
	private SignerType formatoBusta;
	private SignerType formatoBustaEsterna;
	private Boolean fileSigned = null;
	private AbstractSigner signer = null;
	
	public File getExtracttempfile() {
		return extracttempfile;
	}
	public void setExtracttempfile(File extracttempfile) {
		this.extracttempfile = extracttempfile;
	}
	public String getNomeFileSbustato() {
		return nomeFileSbustato;
	}
	public void setNomeFileSbustato(String nomeFileSbustato) {
		this.nomeFileSbustato = nomeFileSbustato;
	}
	public SignerType getFormatoBusta() {
		return formatoBusta;
	}
	public void setFormatoBusta(SignerType formatoBusta) {
		this.formatoBusta = formatoBusta;
	}
	public SignerType getFormatoBustaEsterna() {
		return formatoBustaEsterna;
	}
	public void setFormatoBustaEsterna(SignerType formatoBustaEsterna) {
		this.formatoBustaEsterna = formatoBustaEsterna;
	}
	public Boolean getFileSigned() {
		return fileSigned;
	}
	public void setFileSigned(Boolean fileSigned) {
		this.fileSigned = fileSigned;
	}
	public AbstractSigner getSigner() {
		return signer;
	}
	public void setSigner(AbstractSigner signer) {
		this.signer = signer;
	}
	
	
	
}
