/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class VoceTitolarioBean {
	
	private String codiceVoce;
	private String dbKeyVoce;
	private String descrizioneVoce;
	private String dbKeyPadreVoce;
	private String idVoce;
		
	public String getCodiceVoce() {
		return codiceVoce;
	}
	public void setCodiceVoce(String codiceVoce) {
		this.codiceVoce = codiceVoce;
	}
	public String getDbKeyVoce() {
		return dbKeyVoce;
	}
	public void setDbKeyVoce(String dbKeyVoce) {
		this.dbKeyVoce = dbKeyVoce;
	}
	public String getDbKeyPadreVoce() {
		return dbKeyPadreVoce;
	}
	public void setDbKeyPadreVoce(String dbKeyPadreVoce) {
		this.dbKeyPadreVoce = dbKeyPadreVoce;
	}
	public String getIdVoce() {
		return idVoce;
	}
	public void setIdVoce(String idVoce) {
		this.idVoce = idVoce;
	}
	
	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}
	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}
	@Override
	public String toString() {
		return "VoceTitolarioBean [codiceVoce=" + codiceVoce + ", dbKeyVoce=" + dbKeyVoce + ", dbKeyPadreVoce="
				+ dbKeyPadreVoce + ", descrizioneVoce=" + descrizioneVoce + ", idVoce=" + idVoce + "]";
	}
	
}
