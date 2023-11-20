/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class FirmaGrafometricaBean implements Serializable{

	private byte[] bytesFileDaFirmare; // bytes del file da firmare
	private String nomeFileDaFirmare; // nome del file da firmare
	private String base64EncodeFile; // stringa base64 del file da firmare
	private String base64EncodeLabel; // stringa base64 label associata alla firma 

	public byte[] getBytesFileDaFirmare() {
		return bytesFileDaFirmare;
	}

	public void setBytesFileDaFirmare(byte[] bytesFileDaFirmare) {
		this.bytesFileDaFirmare = bytesFileDaFirmare;
	}

	public String getNomeFileDaFirmare() {
		return nomeFileDaFirmare;
	}

	public void setNomeFileDaFirmare(String nomeFileDaFirmare) {
		this.nomeFileDaFirmare = nomeFileDaFirmare;
	}

	public String getBase64EncodeFile() {
		return base64EncodeFile;
	}

	public void setBase64EncodeFile(String base64EncodeFile) {
		this.base64EncodeFile = base64EncodeFile;
	}

	public String getBase64EncodeLabel() {
		return base64EncodeLabel;
	}

	public void setBase64EncodeLabel(String base64EncodeLabel) {
		this.base64EncodeLabel = base64EncodeLabel;
	}
	
}
