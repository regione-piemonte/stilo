/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class RegistrazioneEmergenzaBean {
	
	private String idRegEmergenza;
	private String registro;
	private String numero;
	private Date effettuataIl;
	private String tipoProt;
	private String effettuataDa; 
	private String desUoProt;
	private String oggetto;
	private String mittente;
	private String destinatari;
	private String protRicevuto;
	private Date dataOraArrivo;
	private Date riversataIl;
	private Integer flgCreataDaMe;
		
	public String getIdRegEmergenza() {
		return idRegEmergenza;
	}
	public void setIdRegEmergenza(String idRegEmergenza) {
		this.idRegEmergenza = idRegEmergenza;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getEffettuataIl() {
		return effettuataIl;
	}
	public void setEffettuataIl(Date effettuataIl) {
		this.effettuataIl = effettuataIl;
	}
	public String getTipoProt() {
		return tipoProt;
	}
	public void setTipoProt(String tipoProt) {
		this.tipoProt = tipoProt;
	}
	public String getEffettuataDa() {
		return effettuataDa;
	}
	public void setEffettuataDa(String effettuataDa) {
		this.effettuataDa = effettuataDa;
	}
	public String getDesUoProt() {
		return desUoProt;
	}
	public void setDesUoProt(String desUoProt) {
		this.desUoProt = desUoProt;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}
	public String getProtRicevuto() {
		return protRicevuto;
	}
	public void setProtRicevuto(String protRicevuto) {
		this.protRicevuto = protRicevuto;
	}
	public Date getDataOraArrivo() {
		return dataOraArrivo;
	}
	public void setDataOraArrivo(Date dataOraArrivo) {
		this.dataOraArrivo = dataOraArrivo;
	}
	public Date getRiversataIl() {
		return riversataIl;
	}
	public void setRiversataIl(Date riversataIl) {
		this.riversataIl = riversataIl;
	}	
	public Integer getFlgCreataDaMe() {
		return flgCreataDaMe;
	}
	public void setFlgCreataDaMe(Integer flgCreataDaMe) {
		this.flgCreataDaMe = flgCreataDaMe;
	}
	
}
