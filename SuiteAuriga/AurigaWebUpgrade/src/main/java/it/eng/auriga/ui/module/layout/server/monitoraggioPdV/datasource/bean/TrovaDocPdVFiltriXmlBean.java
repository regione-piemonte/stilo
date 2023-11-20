/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class TrovaDocPdVFiltriXmlBean {
	
	@XmlVariabile(nome = "IdPdV", tipo = TipoVariabile.SEMPLICE)
	private String idPdV;
	
	@XmlVariabile(nome = "IdItemInviatoCons", tipo = TipoVariabile.SEMPLICE)
	private String idItemInviatoCons;
	
	@XmlVariabile(nome = "EsitoInvioCons", tipo = TipoVariabile.SEMPLICE)
	private String esitoInvioCons;

	@XmlVariabile(nome = "CodiciErrWarn", tipo = TipoVariabile.SEMPLICE)
	private String codiciErrWarn;
	
	@XmlVariabile(nome = "MsgErrWarn", tipo = TipoVariabile.SEMPLICE)
	private String msgErrWarn;
	
	@XmlVariabile(nome = "IdTipiItem", tipo = TipoVariabile.SEMPLICE)
	private String idTipiItem;
	
	@XmlVariabile(nome = "IdItemConservatore", tipo = TipoVariabile.SEMPLICE)
	private String idItemConservatore;

	
	public String getIdPdV() {
		return idPdV;
	}
	public void setIdPdV(String idPdV) {
		this.idPdV = idPdV;
	}
	public String getIdItemInviatoCons() {
		return idItemInviatoCons;
	}
	public void setIdItemInviatoCons(String idItemInviatoCons) {
		this.idItemInviatoCons = idItemInviatoCons;
	}
	public String getEsitoInvioCons() {
		return esitoInvioCons;
	}
	public void setEsitoInvioCons(String esitoInvioCons) {
		this.esitoInvioCons = esitoInvioCons;
	}
	public String getCodiciErrWarn() {
		return codiciErrWarn;
	}
	public void setCodiciErrWarn(String codiciErrWarn) {
		this.codiciErrWarn = codiciErrWarn;
	}
	public String getMsgErrWarn() {
		return msgErrWarn;
	}
	public void setMsgErrWarn(String msgErrWarn) {
		this.msgErrWarn = msgErrWarn;
	}
	public String getIdTipiItem() {
		return idTipiItem;
	}
	public void setIdTipiItem(String idTipiItem) {
		this.idTipiItem = idTipiItem;
	}
	public String getIdItemConservatore() {
		return idItemConservatore;
	}
	public void setIdItemConservatore(String idItemConservatore) {
		this.idItemConservatore = idItemConservatore;
	}
}
