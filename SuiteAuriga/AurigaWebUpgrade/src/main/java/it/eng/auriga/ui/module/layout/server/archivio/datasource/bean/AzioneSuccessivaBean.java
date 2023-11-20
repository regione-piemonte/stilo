/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;

/**
 * 
 * @author MMANIERO
 *
 */

public class AzioneSuccessivaBean {

	private HashMap<String, String> errorMessages;
	private String idUd;
	private String motivo;
	private Boolean flgProtReg;
	private List<AssegnazioneBean> protRegAssegnazione;
	private Boolean flgFirma;
	private List<AssegnazioneBean> invioFirmaDigitaleAssegnazione;
	private Boolean flgVistoEle;
	private List<AssegnazioneBean> invioVistoElettronicoAssegnazione;
	private Boolean flgNoAzione;
	
	private Boolean flgRestituzioneAlMittente;
	private Boolean flgMandaAlMittenteDocumento;
	
	private List<ArchivioBean> listUD;
	
	
	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}
	
	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public String getIdUd() {
		return idUd;
	}
	
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	
	public List<AssegnazioneBean> getProtRegAssegnazione() {
		return protRegAssegnazione;
	}

	
	public void setProtRegAssegnazione(List<AssegnazioneBean> protRegAssegnazione) {
		this.protRegAssegnazione = protRegAssegnazione;
	}

	
	public List<AssegnazioneBean> getInvioFirmaDigitaleAssegnazione() {
		return invioFirmaDigitaleAssegnazione;
	}

	
	public void setInvioFirmaDigitaleAssegnazione(List<AssegnazioneBean> invioFirmaDigitaleAssegnazione) {
		this.invioFirmaDigitaleAssegnazione = invioFirmaDigitaleAssegnazione;
	}

	
	public List<AssegnazioneBean> getInvioVistoElettronicoAssegnazione() {
		return invioVistoElettronicoAssegnazione;
	}

	
	public void setInvioVistoElettronicoAssegnazione(List<AssegnazioneBean> invioVistoElettronicoAssegnazione) {
		this.invioVistoElettronicoAssegnazione = invioVistoElettronicoAssegnazione;
	}

	
	public Boolean getFlgProtReg() {
		return flgProtReg;
	}

	
	public void setFlgProtReg(Boolean flgProtReg) {
		this.flgProtReg = flgProtReg;
	}

	
	public Boolean getFlgFirma() {
		return flgFirma;
	}

	
	public void setFlgFirma(Boolean flgFirma) {
		this.flgFirma = flgFirma;
	}

	
	public Boolean getFlgVistoEle() {
		return flgVistoEle;
	}

	
	public void setFlgVistoEle(Boolean flgVistoEle) {
		this.flgVistoEle = flgVistoEle;
	}

	
	public Boolean getFlgNoAzione() {
		return flgNoAzione;
	}

	
	public void setFlgNoAzione(Boolean flgNoAzione) {
		this.flgNoAzione = flgNoAzione;
	}

	public Boolean getFlgRestituzioneAlMittente() {
		return flgRestituzioneAlMittente;
	}

	public void setFlgRestituzioneAlMittente(Boolean flgRestituzioneAlMittente) {
		this.flgRestituzioneAlMittente = flgRestituzioneAlMittente;
	}

	public Boolean getFlgMandaAlMittenteDocumento() {
		return flgMandaAlMittenteDocumento;
	}

	public void setFlgMandaAlMittenteDocumento(Boolean flgMandaAlMittenteDocumento) {
		this.flgMandaAlMittenteDocumento = flgMandaAlMittenteDocumento;
	}

	public List<ArchivioBean> getListUD() {
		return listUD;
	}

	public void setListUD(List<ArchivioBean> listUD) {
		this.listUD = listUD;
	}

}
