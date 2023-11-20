/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TRichiestaProtocollazioneBean extends AbstractBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	public String getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public BigDecimal getIdJob() {
		return idJob;
	}
	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getXmlFragment() {
		return xmlFragment;
	}
	public void setXmlFragment(String xmlFragment) {
		this.xmlFragment = xmlFragment;
	}
	public String getJsonRequest() {
		return jsonRequest;
	}
	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}
	public Date getTsInizioElaborazione() {
		return tsInizioElaborazione;
	}
	public void setTsInizioElaborazione(Date tsInizioElaborazione) {
		this.tsInizioElaborazione = tsInizioElaborazione;
	}
	public Date getTsFineElaborazione() {
		return tsFineElaborazione;
	}
	public void setTsFineElaborazione(Date tsFineElaborazione) {
		this.tsFineElaborazione = tsFineElaborazione;
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public Date getTsLastUpd() {
		return tsLastUpd;
	}
	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}
	public Date getTsValidazione() {
		return tsValidazione;
	}
	public void setTsValidazione(Date tsValidazione) {
		this.tsValidazione = tsValidazione;
	}
	public String getTsMessaggioErrore() {
		return tsMessaggioErrore;
	}
	public void setTsMessaggioErrore(String tsMessaggioErrore) {
		this.tsMessaggioErrore = tsMessaggioErrore;
	}
	public String getResponseProt() {
		return responseProt;
	}
	public void setResponseProt(String responseProt) {
		this.responseProt = responseProt;
	}
	public String getResponseCustom() {
		return responseCustom;
	}
	public void setResponseCustom(String responseCustom) {
		this.responseCustom = responseCustom;
	}
	public BigDecimal getTryProt() {
		return tryProt;
	}
	public void setTryProt(BigDecimal tryProt) {
		this.tryProt = tryProt;
	}
	public Date getTsLastTryProt() {
		return tsLastTryProt;
	}
	public void setTsLastTryProt(Date tsLastTryProt) {
		this.tsLastTryProt = tsLastTryProt;
	}
	public BigDecimal getTryInvioEsito() {
		return tryInvioEsito;
	}
	public void setTryInvioEsito(BigDecimal tryInvioEsito) {
		this.tryInvioEsito = tryInvioEsito;
	}
	public Date getTsLastTryInvioEsito() {
		return tsLastTryInvioEsito;
	}
	public void setTsLastTryInvioEsito(Date tsLastTryInvioEsito) {
		this.tsLastTryInvioEsito = tsLastTryInvioEsito;
	}
	public BigDecimal getTryInvioEsitoCustom() {
		return tryInvioEsitoCustom;
	}
	public void setTryInvioEsitoCustom(BigDecimal tryInvioEsitoCustom) {
		this.tryInvioEsitoCustom = tryInvioEsitoCustom;
	}
	public Date getTsLastTryInvioEsitoCustom() {
		return tsLastTryInvioEsitoCustom;
	}
	public void setTsLastTryInvioEsitoCustom(Date tsLastTryInvioEsitoCustom) {
		this.tsLastTryInvioEsitoCustom = tsLastTryInvioEsitoCustom;
	}
	public Boolean getProtocollazioneAvvenuta() {
		return protocollazioneAvvenuta;
	}
	public void setProtocollazioneAvvenuta(Boolean protocollazioneAvvenuta) {
		this.protocollazioneAvvenuta = protocollazioneAvvenuta;
	}
	public Boolean getProtocollazioneRichiamata() {
		return protocollazioneRichiamata;
	}
	public void setProtocollazioneRichiamata(Boolean protocollazioneRichiamata) {
		this.protocollazioneRichiamata = protocollazioneRichiamata;
	}
	public Boolean getInvioEsitoProtocollazioneAvvenuto() {
		return invioEsitoProtocollazioneAvvenuto;
	}
	public void setInvioEsitoProtocollazioneAvvenuto(Boolean invioEsitoProtocollazioneAvvenuto) {
		this.invioEsitoProtocollazioneAvvenuto = invioEsitoProtocollazioneAvvenuto;
	}
	public Boolean getInvioEsitoCustomAvvenuto() {
		return invioEsitoCustomAvvenuto;
	}
	public void setInvioEsitoCustomAvvenuto(Boolean invioEsitoCustomAvvenuto) {
		this.invioEsitoCustomAvvenuto = invioEsitoCustomAvvenuto;
	}
	public String getCodApplicazione() {
		return codApplicazione;
	}
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}
	public String getIdEmailInviata() {
		return idEmailInviata;
	}
	public void setIdEmailInviata(String idEmailInviata) {
		this.idEmailInviata = idEmailInviata;
	}
	public BigDecimal getTryInvioEmail() {
		return tryInvioEmail;
	}
	public void setTryInvioEmail(BigDecimal tryInvioEmail) {
		this.tryInvioEmail = tryInvioEmail;
	}
	public Date getTsLastTryInvioEmail() {
		return tsLastTryInvioEmail;
	}
	public void setTsLastTryInvioEmail(Date tsLastTryInvioEmail) {
		this.tsLastTryInvioEmail = tsLastTryInvioEmail;
	}
	public String getOperazione() {
		return operazione;
	}
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
