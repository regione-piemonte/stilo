/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;

public class OrganigrammaTreeNodeBean extends TreeNodeBean {	
	
	private String idUoSvUt;
	private String codRapidoUo;
	private String codRapidoSvUt;
	private String descrUoSvUt;
	private String codFiscale;
	private String idRubrica;
	private String idUo;
	private String idUser;
	private String ciRelUserUo;
	private String tipoRelUtenteUo;	
	private String dataInizioValidita;
	private Boolean flgPuntoProtocollo;
	private Boolean flgPuntoRaccoltaEmail;
	private String username;
	private Boolean flgDestinatarioNeiPreferiti;
	private String flgSelXAssegnazione;
	private Boolean flgProtocollista;
	private Boolean abilitaUoProtEntrata;
	private Boolean abilitaUoProtUscita;
	
	
	public String getIdUoSvUt() {
		return idUoSvUt;
	}
	public void setIdUoSvUt(String idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}
	public String getCodRapidoUo() {
		return codRapidoUo;
	}
	public void setCodRapidoUo(String codRapidoUo) {
		this.codRapidoUo = codRapidoUo;
	}
	public String getCodRapidoSvUt() {
		return codRapidoSvUt;
	}
	public void setCodRapidoSvUt(String codRapidoSvUt) {
		this.codRapidoSvUt = codRapidoSvUt;
	}
	public String getDescrUoSvUt() {
		return descrUoSvUt;
	}
	public void setDescrUoSvUt(String descrUoSvUt) {
		this.descrUoSvUt = descrUoSvUt;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdRubrica() {
		return idRubrica;
	}
	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getCiRelUserUo() {
		return ciRelUserUo;
	}
	public void setCiRelUserUo(String ciRelUserUo) {
		this.ciRelUserUo = ciRelUserUo;
	}
	public String getTipoRelUtenteUo() {
		return tipoRelUtenteUo;
	}
	public void setTipoRelUtenteUo(String tipoRelUtenteUo) {
		this.tipoRelUtenteUo = tipoRelUtenteUo;
	}
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Boolean getFlgPuntoProtocollo() {
		return flgPuntoProtocollo;
	}
	public void setFlgPuntoProtocollo(Boolean flgPuntoProtocollo) {
		this.flgPuntoProtocollo = flgPuntoProtocollo;
	}
	public Boolean getFlgPuntoRaccoltaEmail() {
		return flgPuntoRaccoltaEmail;
	}
	public void setFlgPuntoRaccoltaEmail(Boolean flgPuntoRaccoltaEmail) {
		this.flgPuntoRaccoltaEmail = flgPuntoRaccoltaEmail;
	}
	public Boolean getFlgDestinatarioNeiPreferiti() {
		return flgDestinatarioNeiPreferiti;
	}
	public void setFlgDestinatarioNeiPreferiti(Boolean flgDestinatarioNeiPreferiti) {
		this.flgDestinatarioNeiPreferiti = flgDestinatarioNeiPreferiti;
	}
	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}
	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}
	public Boolean getFlgProtocollista() {
		return flgProtocollista;
	}
	public void setFlgProtocollista(Boolean flgProtocollista) {
		this.flgProtocollista = flgProtocollista;
	}
	public Boolean getAbilitaUoProtEntrata() {
		return abilitaUoProtEntrata;
	}
	public void setAbilitaUoProtEntrata(Boolean abilitaUoProtEntrata) {
		this.abilitaUoProtEntrata = abilitaUoProtEntrata;
	}
	public Boolean getAbilitaUoProtUscita() {
		return abilitaUoProtUscita;
	}
	public void setAbilitaUoProtUscita(Boolean abilitaUoProtUscita) {
		this.abilitaUoProtUscita = abilitaUoProtUscita;
	}
}
