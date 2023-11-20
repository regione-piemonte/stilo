/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class InvioNotificaInteropBean extends InvioMailBean {

	private String tipoNotifica;
	private String categoriaPartenza;
	private String motivo;
		
	public String getTipoNotifica() {
		return tipoNotifica;
	}
	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}
	public String getCategoriaPartenza() {
		return categoriaPartenza;
	}
	public void setCategoriaPartenza(String categoriaPartenza) {
		this.categoriaPartenza = categoriaPartenza;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
		
}
