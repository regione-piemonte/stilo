/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement(name="alboUDBean")
public class AlboUDBean {

	@NumeroColonna(numero = "2")
	private String idUd;

	@NumeroColonna(numero = "14")
	private String pubblicazioneNumero;
	
	@NumeroColonna(numero = "4")
	private String attoNumero;

	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAtto;

	@NumeroColonna(numero = "270")
	private String dataInizioPubbl;

	@NumeroColonna(numero = "271")
	private String dataFinePubbl;

	@NumeroColonna(numero = "91")
	private String richiedente;

	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "32")
	private String tipo;

	@NumeroColonna(numero = "274")
	private String statoPubblicazione;
	
	@NumeroColonna(numero = "275")
	private String idUdRettifica;

	@NumeroColonna(numero = "15")
	@TipoData(tipo = Tipo.DATA)
	private Date tsPubblicazione;

	@NumeroColonna(numero = "273")
	private String formaPubblicazione;

	@NumeroColonna(numero = "276")
	private String motivoAnnullamento;

	@NumeroColonna(numero = "23")
	private String nroAllegati;
	
	@NumeroColonna(numero = "33")
	private String idDocPrimario;
	
	@NumeroColonna(numero = "71")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAdozione;
	
	@NumeroColonna(numero = "72")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date esecutivoDal;
	
	@NumeroColonna(numero = "101")
	private String flgImmediatamenteEsegiubile;
	
	@NumeroColonna(numero = "283")
	private String idPubblicazione;
	
	public String getIdUdRettifica() {
		return idUdRettifica;
	}

	public void setIdUdRettifica(String idUdRettifica) {
		this.idUdRettifica = idUdRettifica;
	}

	public String getIdPubblicazione() {
		return idPubblicazione;
	}

	public void setIdPubblicazione(String idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}

	private List<FileAlbo> filesAlbo;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getPubblicazioneNumero() {
		return pubblicazioneNumero;
	}

	public void setPubblicazioneNumero(String pubblicazioneNumero) {
		this.pubblicazioneNumero = pubblicazioneNumero;
	}

	public String getAttoNumero() {
		return attoNumero;
	}

	public void setAttoNumero(String attoNumero) {
		this.attoNumero = attoNumero;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	public String getDataInizioPubbl() {
		return dataInizioPubbl;
	}

	public void setDataInizioPubbl(String dataInizioPubbl) {
		this.dataInizioPubbl = dataInizioPubbl;
	}

	public String getDataFinePubbl() {
		return dataFinePubbl;
	}

	public void setDataFinePubbl(String dataFinePubbl) {
		this.dataFinePubbl = dataFinePubbl;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}

	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}

	public Date getTsPubblicazione() {
		return tsPubblicazione;
	}

	public void setTsPubblicazione(Date tsPubblicazione) {
		this.tsPubblicazione = tsPubblicazione;
	}

	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}

	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}

	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}

	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}

	public String getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(String nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public Date getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public Date getEsecutivoDal() {
		return esecutivoDal;
	}

	public void setEsecutivoDal(Date esecutivoDal) {
		this.esecutivoDal = esecutivoDal;
	}
	
	public String getFlgImmediatamenteEsegiubile() {
		return flgImmediatamenteEsegiubile;
	}

	public void setFlgImmediatamenteEsegiubile(String flgImmediatamenteEsegiubile) {
		this.flgImmediatamenteEsegiubile = flgImmediatamenteEsegiubile;
	}

	public List<FileAlbo> getFilesAlbo() {
		return filesAlbo;
	}

	public void setFilesAlbo(List<FileAlbo> filesAlbo) {
		this.filesAlbo = filesAlbo;
	}
	
}
