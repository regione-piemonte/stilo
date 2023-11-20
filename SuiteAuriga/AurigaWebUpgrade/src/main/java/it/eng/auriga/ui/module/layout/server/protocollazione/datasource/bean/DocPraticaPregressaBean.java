/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DocPraticaPregressaBean extends AllegatoProtocolloBean {
	
	private Boolean flgFromLoadDett;
	private Boolean flgAllegato;
	private Boolean flgCapofila;
	private String tipoProt;
	private String siglaProtSettore;
	private String nroProt;
	private String annoProt;
	private String nroDeposito;
	private String annoDeposito;
	private String esibenti;
	private String oggetto;
	private Boolean flgUdDaDataEntryScansioni;
	private Boolean flgNextDocAllegato;
	
	public Boolean getFlgFromLoadDett() {
		return flgFromLoadDett;
	}
	public void setFlgFromLoadDett(Boolean flgFromLoadDett) {
		this.flgFromLoadDett = flgFromLoadDett;
	}
	public Boolean getFlgAllegato() {
		return flgAllegato;
	}
	public void setFlgAllegato(Boolean flgAllegato) {
		this.flgAllegato = flgAllegato;
	}
	public Boolean getFlgCapofila() {
		return flgCapofila;
	}
	public void setFlgCapofila(Boolean flgCapofila) {
		this.flgCapofila = flgCapofila;
	}
	public String getTipoProt() {
		return tipoProt;
	}
	public void setTipoProt(String tipoProt) {
		this.tipoProt = tipoProt;
	}	
	public String getSiglaProtSettore() {
		return siglaProtSettore;
	}
	public void setSiglaProtSettore(String siglaProtSettore) {
		this.siglaProtSettore = siglaProtSettore;
	}
	public String getNroProt() {
		return nroProt;
	}
	public void setNroProt(String nroProt) {
		this.nroProt = nroProt;
	}
	public String getAnnoProt() {
		return annoProt;
	}
	public void setAnnoProt(String annoProt) {
		this.annoProt = annoProt;
	}
	public String getNroDeposito() {
		return nroDeposito;
	}
	public void setNroDeposito(String nroDeposito) {
		this.nroDeposito = nroDeposito;
	}
	public String getAnnoDeposito() {
		return annoDeposito;
	}
	public void setAnnoDeposito(String annoDeposito) {
		this.annoDeposito = annoDeposito;
	}
	public String getEsibenti() {
		return esibenti;
	}
	public void setEsibenti(String esibenti) {
		this.esibenti = esibenti;
	}	
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Boolean getFlgUdDaDataEntryScansioni() {
		return flgUdDaDataEntryScansioni;
	}
	public void setFlgUdDaDataEntryScansioni(Boolean flgUdDaDataEntryScansioni) {
		this.flgUdDaDataEntryScansioni = flgUdDaDataEntryScansioni;
	}
	public Boolean getFlgNextDocAllegato() {
		return flgNextDocAllegato;
	}
	public void setFlgNextDocAllegato(Boolean flgNextDocAllegato) {
		this.flgNextDocAllegato = flgNextDocAllegato;
	}
	
}