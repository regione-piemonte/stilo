/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.FilePrimarioOutBean;

@XmlRootElement
public class AlboUDXmlBean {

	@XmlVariabile(nome = "#NroPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String pubblicazioneNumero;
	
	@XmlVariabile(nome = "#NroAtto", tipo = TipoVariabile.SEMPLICE)
	private String attoNumero;

	@XmlVariabile(nome = "#DataAtto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private String dataAtto;

	@XmlVariabile(nome = "#DataInizioPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String dataInizioPubbl;

	@XmlVariabile(nome = "#DataFinePubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String dataFinePubbl;

	@XmlVariabile(nome = "#RichiedentePubbl", tipo = TipoVariabile.SEMPLICE)
	private String richiedente;

	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipo;

	@XmlVariabile(nome = "#StatoDettPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String statoPubblicazione;
	
	@XmlVariabile(nome = "#IdUDAttoRettifica", tipo = TipoVariabile.SEMPLICE)
	private String idUdRettifica;

	@XmlVariabile(nome = "#DataOraPubblicazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsPubblicazione;

	@XmlVariabile(nome = "#FormaPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String formaPubblicazione;

	@XmlVariabile(nome = "#MotivoAnnullamentoPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String motivoAnnullamento;

	@XmlVariabile(nome = "#DataEsecutivita", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private String esecutivoDal;
	
	@XmlVariabile(nome = "#@Allegati", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> allegati;

	@XmlVariabile(nome = "#IdDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimario;

	@XmlVariabile(nome = "#FilePrimario", tipo = TipoVariabile.NESTED)
	private FilePrimarioOutBean filePrimario;
	
	@XmlVariabile(nome = "#FilePrimario.Impronta", tipo = TipoVariabile.NESTED)
	private String improntaPrimario;
	
	@XmlVariabile(nome = "#FilePrimario.AlgoritmoImpronta", tipo = TipoVariabile.NESTED)
	private String algoritmoPrimario;
	
	@XmlVariabile(nome = "#FilePrimario.EncodingImpronta", tipo = TipoVariabile.NESTED)
	private String encodingPrimario;
	
	@XmlVariabile(nome = "#DataAdozione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataAdozione;
	
	@XmlVariabile(nome = "#ImmediatementeEseguibile", tipo = TipoVariabile.SEMPLICE)
	private String flgImmediatamenteEsegiubile;
	
	public String getPubblicazioneNumero() {
		return pubblicazioneNumero;
	}

	public String getAttoNumero() {
		return attoNumero;
	}

	public String getDataAtto() {
		return dataAtto;
	}

	public String getDataInizioPubbl() {
		return dataInizioPubbl;
	}

	public String getDataFinePubbl() {
		return dataFinePubbl;
	}

	public String getRichiedente() {
		return richiedente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}

	public String getIdUdRettifica() {
		return idUdRettifica;
	}

	public Date getTsPubblicazione() {
		return tsPubblicazione;
	}

	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}

	public String getMotivoAnnullamento() {
		return motivoAnnullamento;
	}

	public String getEsecutivoDal() {
		return esecutivoDal;
	}

	public List<AllegatiOutBean> getAllegati() {
		return allegati;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public FilePrimarioOutBean getFilePrimario() {
		return filePrimario;
	}

	public void setPubblicazioneNumero(String pubblicazioneNumero) {
		this.pubblicazioneNumero = pubblicazioneNumero;
	}

	public void setAttoNumero(String attoNumero) {
		this.attoNumero = attoNumero;
	}

	public void setDataAtto(String dataAtto) {
		this.dataAtto = dataAtto;
	}

	public void setDataInizioPubbl(String dataInizioPubbl) {
		this.dataInizioPubbl = dataInizioPubbl;
	}

	public void setDataFinePubbl(String dataFinePubbl) {
		this.dataFinePubbl = dataFinePubbl;
	}

	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}

	public void setIdUdRettifica(String idUdRettifica) {
		this.idUdRettifica = idUdRettifica;
	}

	public void setTsPubblicazione(Date tsPubblicazione) {
		this.tsPubblicazione = tsPubblicazione;
	}

	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}

	public void setMotivoAnnullamento(String motivoAnnullamento) {
		this.motivoAnnullamento = motivoAnnullamento;
	}

	public void setEsecutivoDal(String esecutivoDal) {
		this.esecutivoDal = esecutivoDal;
	}

	public void setAllegati(List<AllegatiOutBean> allegati) {
		this.allegati = allegati;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public void setFilePrimario(FilePrimarioOutBean filePrimario) {
		this.filePrimario = filePrimario;
	}

	public String getImprontaPrimario() {
		return improntaPrimario;
	}

	public void setImprontaPrimario(String improntaPrimario) {
		this.improntaPrimario = improntaPrimario;
	}

	public String getAlgoritmoPrimario() {
		return algoritmoPrimario;
	}

	public void setAlgoritmoPrimario(String algoritmoPrimario) {
		this.algoritmoPrimario = algoritmoPrimario;
	}

	public String getEncodingPrimario() {
		return encodingPrimario;
	}

	public void setEncodingPrimario(String encodingPrimario) {
		this.encodingPrimario = encodingPrimario;
	}

	public Date getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}

	public String getFlgImmediatamenteEsegiubile() {
		return flgImmediatamenteEsegiubile;
	}

	public void setFlgImmediatamenteEsegiubile(String flgImmediatamenteEsegiubile) {
		this.flgImmediatamenteEsegiubile = flgImmediatamenteEsegiubile;
	}
	
}
