/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

public class RichiestaAutotutelaCedBean {

	private String idRichiesta;
	private String descrizioneTipoRichiesta;
	private String codiceTipoRichiesta;
	private String descrizioneStatoRichiesta;
	private String codiceStatoRichiesta;
	private String codicefiscaleUtente;
	private String numeroDocumento;
	private String numeroProtocollo;
	private Date dataRichiesta;
	private Date dataCambioStato;
	private Boolean flagLetto;
	private String idEnte;
	
	private String esito;
	private String motivazione;
	private String ricevuta;
	private List<AllegatoRichiestaAutotutelaCedBean> listaAllegati;
	
	private String codiceAcs;
	private String intestatarioDenominazione;
	private String intestatarioIndirizzo;
	private String intestatarioCap;
	private String intestatarioComune;
	private String intestatarioProvincia;
	private Integer annoDocumento;
	private Date dataDocumento;
	private String idEntrata;
	private String entrata;
	private String entrataAlias;
	private String note;

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getDescrizioneTipoRichiesta() {
		return descrizioneTipoRichiesta;
	}

	public void setDescrizioneTipoRichiesta(String descrizioneTipoRichiesta) {
		this.descrizioneTipoRichiesta = descrizioneTipoRichiesta;
	}

	public String getCodiceTipoRichiesta() {
		return codiceTipoRichiesta;
	}

	public void setCodiceTipoRichiesta(String codiceTipoRichiesta) {
		this.codiceTipoRichiesta = codiceTipoRichiesta;
	}

	public String getDescrizioneStatoRichiesta() {
		return descrizioneStatoRichiesta;
	}

	public void setDescrizioneStatoRichiesta(String descrizioneStatoRichiesta) {
		this.descrizioneStatoRichiesta = descrizioneStatoRichiesta;
	}

	public String getCodiceStatoRichiesta() {
		return codiceStatoRichiesta;
	}

	public void setCodiceStatoRichiesta(String codiceStatoRichiesta) {
		this.codiceStatoRichiesta = codiceStatoRichiesta;
	}

	public String getCodicefiscaleUtente() {
		return codicefiscaleUtente;
	}

	public void setCodicefiscaleUtente(String codicefiscaleUtente) {
		this.codicefiscaleUtente = codicefiscaleUtente;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public Date getDataCambioStato() {
		return dataCambioStato;
	}

	public void setDataCambioStato(Date dataCambioStato) {
		this.dataCambioStato = dataCambioStato;
	}

	public Boolean getFlagLetto() {
		return flagLetto;
	}

	public void setFlagLetto(Boolean flagLetto) {
		this.flagLetto = flagLetto;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public String getRicevuta() {
		return ricevuta;
	}

	public void setRicevuta(String ricevuta) {
		this.ricevuta = ricevuta;
	}

	public List<AllegatoRichiestaAutotutelaCedBean> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<AllegatoRichiestaAutotutelaCedBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public String getCodiceAcs() {
		return codiceAcs;
	}

	public void setCodiceAcs(String codiceAcs) {
		this.codiceAcs = codiceAcs;
	}

	public String getIntestatarioDenominazione() {
		return intestatarioDenominazione;
	}

	public void setIntestatarioDenominazione(String intestatarioDenominazione) {
		this.intestatarioDenominazione = intestatarioDenominazione;
	}

	public String getIntestatarioIndirizzo() {
		return intestatarioIndirizzo;
	}

	public void setIntestatarioIndirizzo(String intestatarioIndirizzo) {
		this.intestatarioIndirizzo = intestatarioIndirizzo;
	}

	public String getIntestatarioCap() {
		return intestatarioCap;
	}

	public void setIntestatarioCap(String intestatarioCap) {
		this.intestatarioCap = intestatarioCap;
	}

	public String getIntestatarioComune() {
		return intestatarioComune;
	}

	public void setIntestatarioComune(String intestatarioComune) {
		this.intestatarioComune = intestatarioComune;
	}

	public String getIntestatarioProvincia() {
		return intestatarioProvincia;
	}

	public void setIntestatarioProvincia(String intestatarioProvincia) {
		this.intestatarioProvincia = intestatarioProvincia;
	}

	public Integer getAnnoDocumento() {
		return annoDocumento;
	}

	public void setAnnoDocumento(Integer annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getIdEntrata() {
		return idEntrata;
	}

	public void setIdEntrata(String idEntrata) {
		this.idEntrata = idEntrata;
	}

	public String getEntrata() {
		return entrata;
	}

	public void setEntrata(String entrata) {
		this.entrata = entrata;
	}

	public String getEntrataAlias() {
		return entrataAlias;
	}

	public void setEntrataAlias(String entrataAlias) {
		this.entrataAlias = entrataAlias;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
