/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

public class DestinatariRegistrazioneMultiplaUscitaXmlBean {

	private String uriExcel;
	private String tipo;
	private String denominazioneCognome;
	private String nome;
	private String codiceFiscale;
	private String piva;
	private String codRapido;
	private String mezzoTrasmissione;
	private String email;
	private String toponimo;
	private String indirizzo;
	private String numCivico;
	private String appendiceCivico;
	private String comuneCittaEstera;
	private String cap;
	private String statoEstero;
	private String localita;
	private String indirizzoRubrica;
	private String effettuaAssegnazioneCc;
	private String inviaEmail;
	private String nomeFilePrimario;
	private String percorsoRelFileAllegati;
	private String nomiFileAllegati;
	private Map<String, String> altreColonne = new LinkedHashMap<String, String>();
	private Map<String, String> mappaIntestazioniColonneValore = new LinkedHashMap<String, String>();
	
	public String getUriExcel() {
		return uriExcel;
	}
	public void setUriExcel(String uriExcel) {
		this.uriExcel = uriExcel;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDenominazioneCognome() {
		return denominazioneCognome;
	}
	public void setDenominazioneCognome(String denominazioneCognome) {
		this.denominazioneCognome = denominazioneCognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPiva() {
		return piva;
	}
	public void setPiva(String piva) {
		this.piva = piva;
	}
	public String getCodRapido() {
		return codRapido;
	}
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}
	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}
	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNumCivico() {
		return numCivico;
	}
	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}
	public String getAppendiceCivico() {
		return appendiceCivico;
	}
	public void setAppendiceCivico(String appendiceCivico) {
		this.appendiceCivico = appendiceCivico;
	}
	public String getComuneCittaEstera() {
		return comuneCittaEstera;
	}
	public void setComuneCittaEstera(String comuneCittaEstera) {
		this.comuneCittaEstera = comuneCittaEstera;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getStatoEstero() {
		return statoEstero;
	}
	public void setStatoEstero(String statoEstero) {
		this.statoEstero = statoEstero;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public String getIndirizzoRubrica() {
		return indirizzoRubrica;
	}
	public void setIndirizzoRubrica(String indirizzoRubrica) {
		this.indirizzoRubrica = indirizzoRubrica;
	}
	public String getEffettuaAssegnazioneCc() {
		return effettuaAssegnazioneCc;
	}
	public void setEffettuaAssegnazioneCc(String effettuaAssegnazioneCc) {
		this.effettuaAssegnazioneCc = effettuaAssegnazioneCc;
	}
	public String getInviaEmail() {
		return inviaEmail;
	}
	public void setInviaEmail(String inviaEmail) {
		this.inviaEmail = inviaEmail;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getPercorsoRelFileAllegati() {
		return percorsoRelFileAllegati;
	}
	public void setPercorsoRelFileAllegati(String percorsoRelFileAllegati) {
		this.percorsoRelFileAllegati = percorsoRelFileAllegati;
	}
	public String getNomiFileAllegati() {
		return nomiFileAllegati;
	}
	public void setNomiFileAllegati(String nomiFileAllegati) {
		this.nomiFileAllegati = nomiFileAllegati;
	}
	public Map<String, String> getAltreColonne() {
		return altreColonne;
	}
	public void setAltreColonne(Map<String, String> altreColonne) {
		this.altreColonne = altreColonne;
	}
	public Map<String, String> getMappaIntestazioniColonneValore() {
		return mappaIntestazioniColonneValore;
	}
	public void setMappaIntestazioniColonneValore(Map<String, String> mappaIntestazioniColonneValore) {
		this.mappaIntestazioniColonneValore = mappaIntestazioniColonneValore;
	}
}
