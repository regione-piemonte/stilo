package it.eng.utility.client.acta.bean;

public class VoceTitolarioBean {
	
	private String codiceVoce;
	private String dbKeyVoce;
	private String dbKeyTitolario;
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
	
	public String getDbKeyTitolario() {
		return dbKeyTitolario;
	}
	public void setDbKeyTitolario(String dbKeyTitolario) {
		this.dbKeyTitolario = dbKeyTitolario;
	}
	@Override
	public String toString() {
		return "VoceTitolarioBean [codiceVoce=" + codiceVoce + ", dbKeyVoce=" + dbKeyVoce + ", dbKeyPadreVoce="
				+ dbKeyPadreVoce + ", dbKeyTitolario=" + dbKeyTitolario +", idVoce=" + idVoce + "]";
	}
	
	
}
