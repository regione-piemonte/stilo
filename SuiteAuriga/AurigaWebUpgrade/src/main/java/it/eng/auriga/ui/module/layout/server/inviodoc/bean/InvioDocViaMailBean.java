/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class InvioDocViaMailBean {

	private String id; // PK
	private String idRichiesta;
	private String codApplRich;
	private String descApplRich;
	private String tipoDocInv;
	private String decodificaTipoDocInv;
	private String xmlRichiesta;
	private String statoRichiesta;
	private String errorMessage;
	private Date dtSottRich; // FMT_STD_TIMESTAMP
	private String dtSottRichDa; // FMT_STD_TIMESTAMP
	private String dtSottRichA; // FMT_STD_TIMESTAMP
	private Date dtInvioEmail; // FMT_STD_TIMESTAMP
	private String dtInvioEmailDa; // FMT_STD_TIMESTAMP
	private String dtInvioEmailA; // FMT_STD_TIMESTAMP
	private String idEmailAss;
	private String statoInvioMail;
	private String statoAccettazioneMail;
	private String statoConsegnaMail;
	private Date dtUltimoAgg; // FMT_STD_TIMESTAMP

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public String getCodApplRich() {
		return codApplRich;
	}

	public void setCodApplRich(String codApplRich) {
		this.codApplRich = codApplRich;
	}

	public String getDescApplRich() {
		return descApplRich;
	}

	public void setDescApplRich(String descApplRich) {
		this.descApplRich = descApplRich;
	}

	public String getTipoDocInv() {
		return tipoDocInv;
	}

	public void setTipoDocInv(String tipoDocInv) {
		this.tipoDocInv = tipoDocInv;
	}

	public String getDecodificaTipoDocInv() {
		return decodificaTipoDocInv;
	}

	public void setDecodificaTipoDocInv(String decodificaTipoDocInv) {
		this.decodificaTipoDocInv = decodificaTipoDocInv;
	}

	public String getXmlRichiesta() {
		return xmlRichiesta;
	}

	public void setXmlRichiesta(String xmlRichiesta) {
		this.xmlRichiesta = xmlRichiesta;
	}

	public String getStatoRichiesta() {
		return statoRichiesta;
	}

	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getDtSottRich() {
		return dtSottRich;
	}

	public void setDtSottRich(Date dtSottRich) {
		this.dtSottRich = dtSottRich;
	}

	public Date getDtInvioEmail() {
		return dtInvioEmail;
	}

	public void setDtInvioEmail(Date dtInvioEmail) {
		this.dtInvioEmail = dtInvioEmail;
	}

	public String getIdEmailAss() {
		return idEmailAss;
	}

	public void setIdEmailAss(String idEmailAss) {
		this.idEmailAss = idEmailAss;
	}

	public Date getDtUltimoAgg() {
		return dtUltimoAgg;
	}

	public String getStatoInvioMail() {
		return statoInvioMail;
	}

	public void setStatoInvioMail(String statoInvioMail) {
		this.statoInvioMail = statoInvioMail;
	}

	public String getStatoAccettazioneMail() {
		return statoAccettazioneMail;
	}

	public void setStatoAccettazioneMail(String statoAccettazioneMail) {
		this.statoAccettazioneMail = statoAccettazioneMail;
	}

	public String getStatoConsegnaMail() {
		return statoConsegnaMail;
	}

	public void setStatoConsegnaMail(String statoConsegnaMail) {
		this.statoConsegnaMail = statoConsegnaMail;
	}

	public void setDtUltimoAgg(Date dtUltimoAgg) {
		this.dtUltimoAgg = dtUltimoAgg;
	}

	public String getDtSottRichDa() {
		return dtSottRichDa;
	}

	public void setDtSottRichDa(String dtSottRichDa) {
		this.dtSottRichDa = dtSottRichDa;
	}

	public String getDtSottRichA() {
		return dtSottRichA;
	}

	public void setDtSottRichA(String dtSottRichA) {
		this.dtSottRichA = dtSottRichA;
	}

	public String getDtInvioEmailDa() {
		return dtInvioEmailDa;
	}

	public void setDtInvioEmailDa(String dtInvioEmailDa) {
		this.dtInvioEmailDa = dtInvioEmailDa;
	}

	public String getDtInvioEmailA() {
		return dtInvioEmailA;
	}

	public void setDtInvioEmailA(String dtInvioEmailA) {
		this.dtInvioEmailA = dtInvioEmailA;
	}

}
