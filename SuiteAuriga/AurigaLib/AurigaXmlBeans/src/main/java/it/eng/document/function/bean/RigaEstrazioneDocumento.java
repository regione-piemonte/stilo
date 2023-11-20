/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RigaEstrazioneDocumento extends AbstractFileIndice implements Serializable{

private static final long serialVersionUID = -1137260141813089421L;
	
	private String societa;
	private String tipoDocumento;
	private String numeroDocumento;
	private String annoDocumento;
	

	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAnnoDocumento() {
		return annoDocumento;
	}
	public void setAnnoDocumento(String annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	@Override
	public String toString() {
		return String
				.format("AirLiquideMetadatiFileRigaIndice [societa=%s, tipoDocumento=%s, NumeroDocumento=%s, AnnoDocumento=%s]",
						societa, tipoDocumento, numeroDocumento, annoDocumento);
	}
}