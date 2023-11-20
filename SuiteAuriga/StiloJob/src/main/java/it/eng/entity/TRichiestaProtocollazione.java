/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_RICHIESTA_PROTOCOLLAZIONE")
public class TRichiestaProtocollazione {

	@Id
	@Column(name = "ID_RICHIESTA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idRichiesta;

	private BigDecimal idJob;
	private Date tsIns;
	private String stato;
	private String xmlFragment;
	private String jsonRequest;
	private Date tsInizioElaborazione;
	private Date tsFineElaborazione;
	private BigDecimal idUd;
	private String numProtocollo;
	private Date tsLastUpd;
	private Date tsValidazione;
	private String tsMessaggioErrore;
	private String responseProt;
	private String responseCustom;
	private BigDecimal tryProt;
	private Date tsLastTryProt;
	private BigDecimal tryInvioEsito;
	private Date tsLastTryInvioEsito;
	private BigDecimal tryInvioEsitoCustom;
	private Date tsLastTryInvioEsitoCustom;
	private Boolean protocollazioneAvvenuta;
	private Boolean protocollazioneRichiamata;
	private Boolean invioEsitoProtocollazioneAvvenuto;
	private Boolean invioEsitoCustomAvvenuto;
	private String codApplicazione;
	private String idEmailInviata;
	private BigDecimal tryInvioEmail;
	private Date tsLastTryInvioEmail;
	private String operazione;

	public TRichiestaProtocollazione() {
	}

	@Id
	@Column(name = "ID_RICHIESTA", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	@Column(name = "ID_JOB", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdJob() {
		return idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "STATO", nullable = false, length = 50)
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "XML_FRAGMENT", nullable = true)
	public String getXmlFragment() {
		return xmlFragment;
	}

	@Column(name = "JSON_REQUEST", nullable = true)
	public String getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	public void setXmlFragment(String xmlFragment) {
		this.xmlFragment = xmlFragment;
	}

	@Column(name = "TS_INIZIO_ELABORAZIONE")
	public Date getTsInizioElaborazione() {
		return tsInizioElaborazione;
	}

	public void setTsInizioElaborazione(Date tsInizioElaborazione) {
		this.tsInizioElaborazione = tsInizioElaborazione;
	}

	@Column(name = "TS_FINE_ELABORAZIONE")
	public Date getTsFineElaborazione() {
		return tsFineElaborazione;
	}

	public void setTsFineElaborazione(Date tsFineElaborazione) {
		this.tsFineElaborazione = tsFineElaborazione;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	@Column(name = "NUM_PROTOCOLLO", nullable = true, length = 20)
	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

	@Column(name = "TS_LAST_UPD", nullable = true)
	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@Column(name = "TS_VALIDAZIONE", nullable = true)
	public Date getTsValidazione() {
		return tsValidazione;
	}

	public void setTsValidazione(Date tsValidazione) {
		this.tsValidazione = tsValidazione;
	}

	@Column(name = "TS_MESSAGGIO_ERRORE", nullable = true)
	public String getTsMessaggioErrore() {
		return tsMessaggioErrore;
	}

	public void setTsMessaggioErrore(String tsMessaggioErrore) {
		this.tsMessaggioErrore = tsMessaggioErrore;
	}

	@Column(name = "RESPONSE_PROT", nullable = true)
	public String getResponseProt() {
		return responseProt;
	}

	public void setResponseProt(String responseProt) {
		this.responseProt = responseProt;
	}

	@Column(name = "RESPONSE_CUSTOM", nullable = true)
	public String getResponseCustom() {
		return responseCustom;
	}

	public void setResponseCustom(String responseCustom) {
		this.responseCustom = responseCustom;
	}

	@Column(name = "TRY#_PROT", precision = 38, scale = 0)
	public BigDecimal getTryProt() {
		return tryProt;
	}

	public void setTryProt(BigDecimal tryProt) {
		this.tryProt = tryProt;
	}

	@Column(name = "TS_LAST_TRY_PROT", nullable = true)
	public Date getTsLastTryProt() {
		return tsLastTryProt;
	}

	public void setTsLastTryProt(Date tsLastTryProt) {
		this.tsLastTryProt = tsLastTryProt;
	}

	@Column(name = "TRY#_INVIO_ESITO_PROT", precision = 38, scale = 0)
	public BigDecimal getTryInvioEsito() {
		return tryInvioEsito;
	}

	public void setTryInvioEsito(BigDecimal tryInvioEsito) {
		this.tryInvioEsito = tryInvioEsito;
	}

	@Column(name = "TS_LAST_TRY_INVIO_ESITO_PROT", nullable = true)
	public Date getTsLastTryInvioEsito() {
		return tsLastTryInvioEsito;
	}

	public void setTsLastTryInvioEsito(Date tsLastTryInvioEsito) {
		this.tsLastTryInvioEsito = tsLastTryInvioEsito;
	}
	
	@Column(name = "TRY#_INVIO_ESITO_CUSTOM", precision = 38, scale = 0)
	public BigDecimal getTryInvioEsitoCustom() {
		return tryInvioEsitoCustom;
	}

	public void setTryInvioEsitoCustom(BigDecimal tryInvioEsitoCustom) {
		this.tryInvioEsitoCustom = tryInvioEsitoCustom;
	}

	@Column(name = "TS_LAST_TRY_INVIO_ESITO_CUSTOM", nullable = true)
	public Date getTsLastTryInvioEsitoCustom() {
		return tsLastTryInvioEsitoCustom;
	}

	public void setTsLastTryInvioEsitoCustom(Date tsLastTryInvioEsitoCustom) {
		this.tsLastTryInvioEsitoCustom = tsLastTryInvioEsitoCustom;
	}

	@Column(name = "PROTOCOLLAZIONE_AVVENUTA", precision = 1, scale = 0)
	public Boolean getProtocollazioneAvvenuta() {
		return protocollazioneAvvenuta;
	}

	public void setProtocollazioneAvvenuta(Boolean protocollazioneAvvenuta) {
		this.protocollazioneAvvenuta = protocollazioneAvvenuta;
	}
	
	@Column(name = "PROTOCOLLAZIONE_RICHIAMATA", precision = 1, scale = 0)
	public Boolean getProtocollazioneRichiamata() {
		return protocollazioneRichiamata;
	}

	public void setProtocollazioneRichiamata(Boolean protocollazioneRichiamata) {
		this.protocollazioneRichiamata = protocollazioneRichiamata;
	}

	@Column(name = "INVIO_ESITO_AVVENUTO", precision = 1, scale = 0)
	public Boolean getInvioEsitoProtocollazioneAvvenuto() {
		return invioEsitoProtocollazioneAvvenuto;
	}

	public void setInvioEsitoProtocollazioneAvvenuto(Boolean invioEsitoProtocollazioneAvvenuto) {
		this.invioEsitoProtocollazioneAvvenuto = invioEsitoProtocollazioneAvvenuto;
	}
	
	@Column(name = "INVIO_ESITO_CUSTOM_AVVENUTO", precision = 1, scale = 0)
	public Boolean getInvioEsitoCustomAvvenuto() {
		return invioEsitoCustomAvvenuto;
	}

	public void setInvioEsitoCustomAvvenuto(Boolean invioEsitoCustomAvvenuto) {
		this.invioEsitoCustomAvvenuto = invioEsitoCustomAvvenuto;
	}

	@Column(name = "COD_APPLICAZIONE", nullable = true)
	public String getCodApplicazione() {
		return codApplicazione;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	@Column(name = "ID_EMAIL_INVIATA")
	public String getIdEmailInviata() {
		return idEmailInviata;
	}

	public void setIdEmailInviata(String idEmailInviata) {
		this.idEmailInviata = idEmailInviata;
	}

	@Column(name = "TRY#_INVIO_EMAIL", precision = 38, scale = 0, nullable = false)
	public BigDecimal getTryInvioEmail() {
		return tryInvioEmail;
	}

	public void setTryInvioEmail(BigDecimal tryInvioEmail) {
		this.tryInvioEmail = tryInvioEmail;
	}

	@Column(name = "TS_LAST_TRY_INVIO_EMAIL")
	public Date getTsLastTryInvioEmail() {
		return tsLastTryInvioEmail;
	}

	public void setTsLastTryInvioEmail(Date tsLastTryInvioEmail) {
		this.tsLastTryInvioEmail = tsLastTryInvioEmail;
	}
	
	@Column(name = "OPERAZIONE", nullable = true)
	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

}
