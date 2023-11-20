/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * 
 * @author cristiano
 *
 */

public class AzioneSuRichAccessoAttiBean extends OperazioneMassivaBean<RichiestaAccessoAttiBean> {

	private String codOperazione;

	private Date dataAppuntamento;
	private String orarioAppuntamento;
	private Date dataPrelievo;
	private Date dataRestituzione;
	private String motivi;

	// Campi per generazione foglio preliervo
	private String nomeFileFoglioPrelievo;
	private String uriFileFoglioPrelievo;
	private MimeTypeFirmaBean infoFileFoglioPrelievo;

	private boolean recuperoModelloInError;
	private String recuperoModelloErrorMessage;

	public String getCodOperazione() {
		return codOperazione;
	}

	public void setCodOperazione(String codOperazione) {
		this.codOperazione = codOperazione;
	}

	public Date getDataAppuntamento() {
		return dataAppuntamento;
	}

	public void setDataAppuntamento(Date dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}

	public String getOrarioAppuntamento() {
		return orarioAppuntamento;
	}

	public void setOrarioAppuntamento(String orarioAppuntamento) {
		this.orarioAppuntamento = orarioAppuntamento;
	}

	public Date getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public Date getDataRestituzione() {
		return dataRestituzione;
	}

	public void setDataRestituzione(Date dataRestituzione) {
		this.dataRestituzione = dataRestituzione;
	}

	public String getMotivi() {
		return motivi;
	}

	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}

	public String getNomeFileFoglioPrelievo() {
		return nomeFileFoglioPrelievo;
	}

	public void setNomeFileFoglioPrelievo(String nomeFileFoglioPrelievo) {
		this.nomeFileFoglioPrelievo = nomeFileFoglioPrelievo;
	}

	public String getUriFileFoglioPrelievo() {
		return uriFileFoglioPrelievo;
	}

	public void setUriFileFoglioPrelievo(String uriFileFoglioPrelievo) {
		this.uriFileFoglioPrelievo = uriFileFoglioPrelievo;
	}

	public MimeTypeFirmaBean getInfoFileFoglioPrelievo() {
		return infoFileFoglioPrelievo;
	}

	public void setInfoFileFoglioPrelievo(MimeTypeFirmaBean infoFileFoglioPrelievo) {
		this.infoFileFoglioPrelievo = infoFileFoglioPrelievo;
	}

	public boolean isRecuperoModelloInError() {
		return recuperoModelloInError;
	}

	public void setRecuperoModelloInError(boolean recuperoModelloInError) {
		this.recuperoModelloInError = recuperoModelloInError;
	}

	public String getRecuperoModelloErrorMessage() {
		return recuperoModelloErrorMessage;
	}

	public void setRecuperoModelloErrorMessage(String recuperoModelloErrorMessage) {
		this.recuperoModelloErrorMessage = recuperoModelloErrorMessage;
	}

}
