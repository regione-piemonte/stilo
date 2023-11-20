/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class RegistrazioneEmergenzaXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idRegEmergenza;
	
	@NumeroColonna(numero = "2")
	private String idUd;
	
	@NumeroColonna(numero = "3")
	private String registro;
	
	@NumeroColonna(numero = "4")
	private String tipoProt;
	
	@NumeroColonna(numero = "5")
	private String annoRegistrazione;
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA )
	private Date effettuataIl;
	
	@NumeroColonna(numero = "7")
	private String numero;
	
	@NumeroColonna(numero = "8")
	private String desUoProt;
	
	@NumeroColonna(numero = "9")
	private String effettuataDa;
	
	@NumeroColonna(numero = "10")
	private String oggetto;
	
	@NumeroColonna(numero = "11")
	private String mittente;
	
	@NumeroColonna(numero = "12")
	@TipoData(tipo = Tipo.DATA )
	private Date dataOraArrivo;
	
	@NumeroColonna(numero = "13")
	@TipoData(tipo = Tipo.DATA )
	private Date riversataIl;
	
	@NumeroColonna(numero = "14")
	private String destinatari;
	
	@NumeroColonna(numero = "15")
	private String protRicevuto;
	
	@NumeroColonna(numero = "16")
	private Integer flgCreataDaMe;
		
	@NumeroColonna(numero = "17")
	private String segnaturaEmergenza;
	
	@NumeroColonna(numero = "18")
	private String segnaturaRegDefinitiva;

	public String getIdRegEmergenza() {
		return idRegEmergenza;
	}

	public void setIdRegEmergenza(String idRegEmergenza) {
		this.idRegEmergenza = idRegEmergenza;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getTipoProt() {
		return tipoProt;
	}

	public void setTipoProt(String tipoProt) {
		this.tipoProt = tipoProt;
	}

	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}

	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}

	public Date getEffettuataIl() {
		return effettuataIl;
	}

	public void setEffettuataIl(Date effettuataIl) {
		this.effettuataIl = effettuataIl;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDesUoProt() {
		return desUoProt;
	}

	public void setDesUoProt(String desUoProt) {
		this.desUoProt = desUoProt;
	}

	public String getEffettuataDa() {
		return effettuataDa;
	}

	public void setEffettuataDa(String effettuataDa) {
		this.effettuataDa = effettuataDa;
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

	public Integer getFlgCreataDaMe() {
		return flgCreataDaMe;
	}

	public void setFlgCreataDaMe(Integer flgCreataDaMe) {
		this.flgCreataDaMe = flgCreataDaMe;
	}

	public String getSegnaturaEmergenza() {
		return segnaturaEmergenza;
	}

	public void setSegnaturaEmergenza(String segnaturaEmergenza) {
		this.segnaturaEmergenza = segnaturaEmergenza;
	}

	public String getSegnaturaRegDefinitiva() {
		return segnaturaRegDefinitiva;
	}

	public void setSegnaturaRegDefinitiva(String segnaturaRegDefinitiva) {
		this.segnaturaRegDefinitiva = segnaturaRegDefinitiva;
	}
}