/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import it.eng.core.business.beans.AbstractBean;

public class DmtEmailInvioDocElabMassBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -5386986906268990581L;

	private String useridApplicazione;
	private BigDecimal idSpAoo;
	private String tipologiaDoc;
	private String emailAccount;
	private String aliasAccount;
	private String idAccount;

	public String getUseridApplicazione() {
		return useridApplicazione;
	}

	public void setUseridApplicazione(String useridApplicazione) {
		this.useridApplicazione = useridApplicazione;
	}

	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public String getTipologiaDoc() {
		return tipologiaDoc;
	}

	public void setTipologiaDoc(String tipologiaDoc) {
		this.tipologiaDoc = tipologiaDoc;
	}

	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public String getAliasAccount() {
		return aliasAccount;
	}

	public void setAliasAccount(String aliasAccount) {
		this.aliasAccount = aliasAccount;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	public DmtEmailInvioDocElabMassBean() {
		super();
	}

	public DmtEmailInvioDocElabMassBean(String useridApplicazione, BigDecimal idSpAoo, String tipologiaDoc, String emailAccount, String idAccount) {
		super();
		this.useridApplicazione = useridApplicazione;
		this.idSpAoo = idSpAoo;
		this.tipologiaDoc = tipologiaDoc;
		this.emailAccount = emailAccount;
		this.idAccount = idAccount;
	}

	public DmtEmailInvioDocElabMassBean(String useridApplicazione, BigDecimal idSpAoo, String tipologiaDoc, String emailAccount, String aliasAccount,
			String idAccount) {
		super();
		this.useridApplicazione = useridApplicazione;
		this.idSpAoo = idSpAoo;
		this.tipologiaDoc = tipologiaDoc;
		this.emailAccount = emailAccount;
		this.aliasAccount = aliasAccount;
		this.idAccount = idAccount;
	}

}
