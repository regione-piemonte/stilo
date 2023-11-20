/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_NOTIF_EMAIL_INVIATE")
public class TNotifEmailInviate implements Serializable {
	
	private static final long serialVersionUID = -1015814565785625198L;
	
	private String idRichNotifica;

	private BigDecimal idSpAoo;

	private BigDecimal IdProcess;

	private BigDecimal idUd;

	private BigDecimal idFolder;

	private String triggerCondition;

	private String destNotifica;

	private String destCcNotifica;

	private String idAccountMitt;

	private String accountMittente;

	private String aliasAccountMittente;

	private String smtpEndpointAccountMitt;

	private String smtpPortAccountMitt;

	private String subject;

	private String body;  //questo sarebbe un CLOB, ma va gestita la conversione in String
	
	private Date tsLastTry;
	
	private BigDecimal tryNum;

	private String msgErrInvio;  //questo sarebbe un CLOB, ma va gestita la conversione in String

	private Date tsInvio;

	private String mailMessAgeId;

	private Date tsIns;

	private BigDecimal idUserIns;

	private Date tsLastUpd;
	
	private BigDecimal idUserLastUpd;
	
	public TNotifEmailInviate() {
		
	}
	
	public TNotifEmailInviate(String idRichNotifica, BigDecimal idSpAoo, String triggerCondition, 
			String destNotifica, String subject, BigDecimal tryNum, Date tsIns, Date tsLastUpd) {
		this.idRichNotifica = idRichNotifica;
		this.idSpAoo = idSpAoo;
		this.triggerCondition = triggerCondition;
		this.destNotifica = destNotifica;
		this.subject = subject;
		this.tryNum = tryNum;
		this.tsIns = tsIns;
		this.tsLastUpd = tsLastUpd;
	}
	
	public TNotifEmailInviate(String idRichNotifica, BigDecimal idSpAoo, BigDecimal IdProcess, BigDecimal idUd,
			BigDecimal idFolder, String triggerCondition, String destNotifica, String destCcNotifica,
			String idAccountMitt, String accountMittente, String aliasAccountMittente,
			String smtpEndpointAccountMitt, String smtpPortAccountMitt, String subject, String body,
			Date tsLastTry, BigDecimal tryNum, String msgErrInvio, Date tsInvio, String mailMessAgeId, 
			Date tsIns, BigDecimal idUserIns, Date tsLastUpd, BigDecimal idUserLastUpd) {
		this.idRichNotifica = idRichNotifica;
		this.idSpAoo = idSpAoo;
		this.IdProcess = IdProcess;
		this.idUd = idUd;
		this.idFolder = idFolder;
		this.triggerCondition = triggerCondition;
		this.destNotifica = destNotifica;
		this.destCcNotifica = destCcNotifica;
		this.idAccountMitt = idAccountMitt;
		this.accountMittente = accountMittente;
		this.aliasAccountMittente = aliasAccountMittente;
		this.smtpEndpointAccountMitt = smtpEndpointAccountMitt;
		this.smtpPortAccountMitt = smtpPortAccountMitt;
		this.subject = subject;
		this.body = body;
		this.tsLastTry = tsLastTry;
		this.tryNum = tryNum;
		this.msgErrInvio = msgErrInvio;
		this.tsInvio = tsInvio;
		this.mailMessAgeId = mailMessAgeId;
		this.tsIns = tsIns;
		this.idUserIns = idUserIns;
		this.tsLastUpd = tsLastUpd;
		this.idUserLastUpd = idUserLastUpd;
	}
	
	@Id
	@Column(name = "ID_RICH_NOTIFICA", unique = true, nullable = false, length = 64)
	public String getIdRichNotifica() {
		return idRichNotifica;
	}

	public void setIdRichNotifica(String idRichNotifica) {
		this.idRichNotifica = idRichNotifica;
	}
	
	@Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	@Column(name = "ID_PROCESS", precision = 22, scale = 0)
	public BigDecimal getIdProcess() {
		return IdProcess;
	}

	public void setIdProcess(BigDecimal idProcess) {
		IdProcess = idProcess;
	}
	
	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	@Column(name = "ID_FOLDER", precision = 22, scale = 0)
	public BigDecimal getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}
	
	@Column(name = "TRIGGER_CONDITION", nullable = false, length = 1000)
	public String getTriggerCondition() {
		return triggerCondition;
	}

	public void setTriggerCondition(String triggerCondition) {
		this.triggerCondition = triggerCondition;
	}
	
	@Column(name = "DEST_NOTIFICA", nullable = false, length = 4000)
	public String getDestNotifica() {
		return destNotifica;
	}

	public void setDestNotifica(String destNotifica) {
		this.destNotifica = destNotifica;
	}
	
	@Column(name = "DEST_CC_NOTIFICA", length = 4000)
	public String getDestCcNotifica() {
		return destCcNotifica;
	}

	public void setDestCcNotifica(String destCcNotifica) {
		this.destCcNotifica = destCcNotifica;
	}
	
	@Column(name = "ID_ACCOUNT_MITT", length = 64)
	public String getIdAccountMitt() {
		return idAccountMitt;
	}

	public void setIdAccountMitt(String idAccountMitt) {
		this.idAccountMitt = idAccountMitt;
	}
	
	@Column(name = "ACCOUNT_MITTENTE", length = 500)
	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}
	
	@Column(name = "ALIAS_ACCOUNT_MITTENTE", length = 500)
	public String getAliasAccountMittente() {
		return aliasAccountMittente;
	}

	public void setAliasAccountMittente(String aliasAccountMittente) {
		this.aliasAccountMittente = aliasAccountMittente;
	}
	
	@Column(name = "SMTP_ENDPOINT_ACCOUNT_MITT", length = 500)
	public String getSmtpEndpointAccountMitt() {
		return smtpEndpointAccountMitt;
	}

	public void setSmtpEndpointAccountMitt(String smtpEndpointAccountMitt) {
		this.smtpEndpointAccountMitt = smtpEndpointAccountMitt;
	}
	
	@Column(name = "SMTP_PORT_ACCOUNT_MITT", length = 500)
	public String getSmtpPortAccountMitt() {
		return smtpPortAccountMitt;
	}

	public void setSmtpPortAccountMitt(String smtpPortAccountMitt) {
		this.smtpPortAccountMitt = smtpPortAccountMitt;
	}
	
	@Column(name = "SUBJECT", nullable = false, length = 1000)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name = "BODY")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@Column(name = "TS_LAST_TRY")
	public Date getTsLastTry() {
		return tsLastTry;
	}

	public void setTsLastTry(Date tsLastTry) {
		this.tsLastTry = tsLastTry;
	}
	
	@Column(name = "TRY_NUM", nullable = false, precision = 22, scale = 0)
	public BigDecimal getTryNum() {
		return tryNum;
	}

	public void setTryNum(BigDecimal tryNum) {
		this.tryNum = tryNum;
	}
	
	@Column(name = "MSG_ERR_INVIO")
	public String getMsgErrInvio() {
		return msgErrInvio;
	}

	public void setMsgErrInvio(String msgErrInvio) {
		this.msgErrInvio = msgErrInvio;
	}
	
	@Column(name = "TS_INVIO")
	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}
	
	@Column(name = "MAIL_MESSAGE_ID", length = 1000)
	public String getMailMessAgeId() {
		return mailMessAgeId;
	}

	public void setMailMessAgeId(String mailMessAgeId) {
		this.mailMessAgeId = mailMessAgeId;
	}
	
	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	
	@Column(name = "ID_USER_INS", precision = 22, scale = 0)
	public BigDecimal getIdUserIns() {
		return idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
	}
	
	@Column(name = "TS_LAST_UPD", nullable = false)
	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}
	
	@Column(name = "ID_USER_LAST_UPD", precision = 22, scale = 0)
	public BigDecimal getIdUserLastUpd() {
		return idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}
	
}
