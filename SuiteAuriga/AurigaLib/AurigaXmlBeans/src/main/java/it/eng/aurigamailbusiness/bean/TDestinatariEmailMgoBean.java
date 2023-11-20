/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TDestinatariEmailMgoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private String accountDestinatario;
	private boolean flgDestEffettivo;
	private boolean flgDestOrigInvio;
	private boolean flgNotifInteropAgg;
	private boolean flgNotifInteropAnn;
	private boolean flgNotifInteropConf;
	private boolean flgNotifInteropEcc;
	private String flgTipoDestinatario;
	private String idDestinatarioEmail;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String statoConsolidamento;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String idEmail;
	private String idVoceRubricaDest;
	private String accountDestinatarioCtx;
	private String statoConsegna;

	public String getAccountDestinatario() {
		return accountDestinatario;
	}

	public void setAccountDestinatario(String accountDestinatario) {
		this.accountDestinatario = accountDestinatario;
	}

	public boolean getFlgDestEffettivo() {
		return flgDestEffettivo;
	}

	public void setFlgDestEffettivo(boolean flgDestEffettivo) {
		this.flgDestEffettivo = flgDestEffettivo;
	}

	public boolean getFlgDestOrigInvio() {
		return flgDestOrigInvio;
	}

	public void setFlgDestOrigInvio(boolean flgDestOrigInvio) {
		this.flgDestOrigInvio = flgDestOrigInvio;
	}

	public boolean getFlgNotifInteropAgg() {
		return flgNotifInteropAgg;
	}

	public void setFlgNotifInteropAgg(boolean flgNotifInteropAgg) {
		this.flgNotifInteropAgg = flgNotifInteropAgg;
	}

	public boolean getFlgNotifInteropAnn() {
		return flgNotifInteropAnn;
	}

	@Override
	public String toString() {
		return "TDestinatariEmailMgoBean [accountDestinatario=" + accountDestinatario + ", idDestinatarioEmail=" + idDestinatarioEmail + ", idEmail=" + idEmail
				+ ", flgTipoDestinatario=" + flgTipoDestinatario + "]";
	}

	public void setFlgNotifInteropAnn(boolean flgNotifInteropAnn) {
		this.flgNotifInteropAnn = flgNotifInteropAnn;
	}

	public boolean getFlgNotifInteropConf() {
		return flgNotifInteropConf;
	}

	public void setFlgNotifInteropConf(boolean flgNotifInteropConf) {
		this.flgNotifInteropConf = flgNotifInteropConf;
	}

	public boolean getFlgNotifInteropEcc() {
		return flgNotifInteropEcc;
	}

	public void setFlgNotifInteropEcc(boolean flgNotifInteropEcc) {
		this.flgNotifInteropEcc = flgNotifInteropEcc;
	}

	public String getFlgTipoDestinatario() {
		return flgTipoDestinatario;
	}

	public void setFlgTipoDestinatario(String flgTipoDestinatario) {
		this.flgTipoDestinatario = flgTipoDestinatario;
	}

	public String getIdDestinatarioEmail() {
		return idDestinatarioEmail;
	}

	public void setIdDestinatarioEmail(String idDestinatarioEmail) {
		this.idDestinatarioEmail = idDestinatarioEmail;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdVoceRubricaDest() {
		return idVoceRubricaDest;
	}

	public void setIdVoceRubricaDest(String idVoceRubricaDest) {
		this.idVoceRubricaDest = idVoceRubricaDest;
	}

	public String getAccountDestinatarioCtx() {
		return accountDestinatarioCtx;
	}

	public void setAccountDestinatarioCtx(String accountDestinatarioCtx) {
		this.accountDestinatarioCtx = accountDestinatarioCtx;
	}

	public String getStatoConsegna() {
		return statoConsegna;
	}

	public void setStatoConsegna(String statoConsegna) {
		this.statoConsegna = statoConsegna;
	}

}