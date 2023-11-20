/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class CertificatoHsmBean implements Serializable {

	private String hsmType;

	// Dati autenticazione servizio hsm
	private String certId;
	private String potereDiFirma;
	private String serialNumber;
	private String descrizione;
	private String otps;
	
	public String getHsmType() {
		return hsmType;
	}
	
	public void setHsmType(String hsmType) {
		this.hsmType = hsmType;
	}
	
	public String getCertId() {
		return certId;
	}
	
	public void setCertId(String certId) {
		this.certId = certId;
	}
	
	public String getPotereDiFirma() {
		return potereDiFirma;
	}
	
	public void setPotereDiFirma(String potereDiFirma) {
		this.potereDiFirma = potereDiFirma;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getOtps() {
		return otps;
	}

	public void setOtps(String otps) {
		this.otps = otps;
	}

}
