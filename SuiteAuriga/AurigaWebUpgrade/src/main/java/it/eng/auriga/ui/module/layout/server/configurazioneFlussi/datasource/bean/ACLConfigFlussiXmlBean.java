/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class ACLConfigFlussiXmlBean {

	@NumeroColonna(numero = "1")
	private String tipoDestinatario;
	@NumeroColonna(numero = "2")
	private String idDestinatario;
	@NumeroColonna(numero = "3")
	private String denominazioneDestinatario;	
	@NumeroColonna(numero = "5")	
	private String codiceRapido;		
	@NumeroColonna(numero = "10")	
	private String uoSovraordinataDiLivello;		
	@NumeroColonna(numero = "11")	
	private String uoSovraordinataDiTipo;		
	@NumeroColonna(numero = "13")	
	private String idUoRuoloAmm;
	@NumeroColonna(numero = "14")	
	private String descrizioneUoRuoloAmm;
	@NumeroColonna(numero = "15")	
	private String codiceUoRuoloAmm;
	@NumeroColonna(numero = "16")
	private String flgUoSubordinateRuoloAmm;	
	@NumeroColonna(numero = "17")	
	private String idRuoloProcUoRuoloAmm;
	@NumeroColonna(numero = "18")	
	private String idRuoloProcSvRuoloAmm;
	@NumeroColonna(numero = "19")
	private String flgVisibilita;
	@NumeroColonna(numero = "20")
	private String flgEsecuzione;
	@NumeroColonna(numero = "23")
	private String flgInibitaEscalationSovraordinati;
	
	
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	public String getDenominazioneDestinatario() {
		return denominazioneDestinatario;
	}
	public void setDenominazioneDestinatario(String denominazioneDestinatario) {
		this.denominazioneDestinatario = denominazioneDestinatario;
	}
	public String getCodiceRapido() {
		return codiceRapido;
	}
	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}
	public String getUoSovraordinataDiLivello() {
		return uoSovraordinataDiLivello;
	}
	public void setUoSovraordinataDiLivello(String uoSovraordinataDiLivello) {
		this.uoSovraordinataDiLivello = uoSovraordinataDiLivello;
	}
	public String getUoSovraordinataDiTipo() {
		return uoSovraordinataDiTipo;
	}
	public void setUoSovraordinataDiTipo(String uoSovraordinataDiTipo) {
		this.uoSovraordinataDiTipo = uoSovraordinataDiTipo;
	}
	public String getIdUoRuoloAmm() {
		return idUoRuoloAmm;
	}
	public void setIdUoRuoloAmm(String idUoRuoloAmm) {
		this.idUoRuoloAmm = idUoRuoloAmm;
	}
	public String getDescrizioneUoRuoloAmm() {
		return descrizioneUoRuoloAmm;
	}
	public void setDescrizioneUoRuoloAmm(String descrizioneUoRuoloAmm) {
		this.descrizioneUoRuoloAmm = descrizioneUoRuoloAmm;
	}
	public String getCodiceUoRuoloAmm() {
		return codiceUoRuoloAmm;
	}
	public void setCodiceUoRuoloAmm(String codiceUoRuoloAmm) {
		this.codiceUoRuoloAmm = codiceUoRuoloAmm;
	}
	public String getFlgUoSubordinateRuoloAmm() {
		return flgUoSubordinateRuoloAmm;
	}
	public void setFlgUoSubordinateRuoloAmm(String flgUoSubordinateRuoloAmm) {
		this.flgUoSubordinateRuoloAmm = flgUoSubordinateRuoloAmm;
	}
	public String getIdRuoloProcUoRuoloAmm() {
		return idRuoloProcUoRuoloAmm;
	}
	public void setIdRuoloProcUoRuoloAmm(String idRuoloProcUoRuoloAmm) {
		this.idRuoloProcUoRuoloAmm = idRuoloProcUoRuoloAmm;
	}	
	public String getIdRuoloProcSvRuoloAmm() {
		return idRuoloProcSvRuoloAmm;
	}
	public void setIdRuoloProcSvRuoloAmm(String idRuoloProcSvRuoloAmm) {
		this.idRuoloProcSvRuoloAmm = idRuoloProcSvRuoloAmm;
	}	
	public String getFlgVisibilita() {
		return flgVisibilita;
	}
	public void setFlgVisibilita(String flgVisibilita) {
		this.flgVisibilita = flgVisibilita;
	}
	public String getFlgEsecuzione() {
		return flgEsecuzione;
	}
	public void setFlgEsecuzione(String flgEsecuzione) {
		this.flgEsecuzione = flgEsecuzione;
	}
	public String getFlgInibitaEscalationSovraordinati() {
		return flgInibitaEscalationSovraordinati;
	}
	public void setFlgInibitaEscalationSovraordinati(String flgInibitaEscalationSovraordinati) {
		this.flgInibitaEscalationSovraordinati = flgInibitaEscalationSovraordinati;
	}
	
}
