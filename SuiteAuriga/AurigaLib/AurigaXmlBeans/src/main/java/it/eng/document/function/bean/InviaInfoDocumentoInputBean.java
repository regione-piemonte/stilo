/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class InviaInfoDocumentoInputBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<String> destinatari;
	private String mittente;
	private String oggetto;
	private String corpoMail;
	private String uriFileModello;
	private String idDocFileModello;
	private String nomeFileModello;
	private boolean flgInviaAllegati;
	private boolean flgTimbraAllegati;
	private boolean flgTimbraFileModello;
	private boolean flgVersionaModello;
	private String testoInChiaroBarCode;
	private String contenutoBarCode;
	private List<FileToSendBean> allegatiMail;
	
	public List<String> getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(List<String> destinatari) {
		this.destinatari = destinatari;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpoMail() {
		return corpoMail;
	}
	public void setCorpoMail(String corpoMail) {
		this.corpoMail = corpoMail;
	}
	public String getUriFileModello() {
		return uriFileModello;
	}
	public void setUriFileModello(String uriFileModello) {
		this.uriFileModello = uriFileModello;
	}
	public boolean isFlgVersionaModello() {
		return flgVersionaModello;
	}
	public void setFlgVersionaModello(boolean flgVersionaModello) {
		this.flgVersionaModello = flgVersionaModello;
	}
	public List<FileToSendBean> getAllegatiMail() {
		return allegatiMail;
	}
	public void setAllegatiMail(List<FileToSendBean> allegatiMail) {
		this.allegatiMail = allegatiMail;
	}
	public boolean isFlgInviaAllegati() {
		return flgInviaAllegati;
	}
	public void setFlgInviaAllegati(boolean flgInviaAllegati) {
		this.flgInviaAllegati = flgInviaAllegati;
	}
	public boolean isFlgTimbraAllegati() {
		return flgTimbraAllegati;
	}
	public void setFlgTimbraAllegati(boolean flgTimbraAllegati) {
		this.flgTimbraAllegati = flgTimbraAllegati;
	}
	public boolean isFlgTimbraFileModello() {
		return flgTimbraFileModello;
	}
	public void setFlgTimbraFileModello(boolean flgTimbraFileModello) {
		this.flgTimbraFileModello = flgTimbraFileModello;
	}
	public String getTestoInChiaroBarCode() {
		return testoInChiaroBarCode;
	}
	public void setTestoInChiaroBarCode(String testoInChiaroBarCode) {
		this.testoInChiaroBarCode = testoInChiaroBarCode;
	}
	public String getContenutoBarCode() {
		return contenutoBarCode;
	}
	public void setContenutoBarCode(String contenutoBarCode) {
		this.contenutoBarCode = contenutoBarCode;
	}
	public String getNomeFileModello() {
		return nomeFileModello;
	}
	public void setNomeFileModello(String nomeFileModello) {
		this.nomeFileModello = nomeFileModello;
	}
	public String getIdDocFileModello() {
		return idDocFileModello;
	}
	public void setIdDocFileModello(String idDocFileModello) {
		this.idDocFileModello = idDocFileModello;
	}
	
}
