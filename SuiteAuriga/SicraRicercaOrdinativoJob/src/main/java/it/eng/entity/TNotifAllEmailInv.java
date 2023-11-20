/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_NOTIF_ALL_EMAIL_INV")
public class TNotifAllEmailInv implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private BigDecimal idRichNotAll;
	private String idRichNotifica;
	private BigDecimal idSpAoo;
	private String idEmail;
	private String uri;
	private String displayFilename;
	private String stato;
	private Date tsLastTry;
	private BigDecimal tryNum;
	private Date tsIns;
	private BigDecimal idUserIns;
	private Date tsLastUpd;
	private BigDecimal idUserLastUpd;
	private BigDecimal errCode;
	private String errMsg;
	
	public TNotifAllEmailInv() {
		
	}
	
	public TNotifAllEmailInv(BigDecimal idRichNotAll, String idRichNotifica, BigDecimal idSpAoo, 
			  			  	 String uri, BigDecimal tryNum, Date tsIns, Date tsLastUpd) {
			
		this.idRichNotAll = idRichNotAll;
		this.idRichNotifica = idRichNotifica;
		this.idSpAoo = idSpAoo; 
		this.uri = uri;
		this.tryNum = tryNum;
		this.tsIns = tsIns;
		this.tsLastUpd = tsLastUpd;
		
	}
	
	public TNotifAllEmailInv(BigDecimal idRichNotAll, String idRichNotifica, BigDecimal idSpAoo, 
						  	 String idEmail, String uri, String displayFilename, String stato,
						  	 Date tsLastTry, BigDecimal tryNum, Date tsIns, BigDecimal idUserIns, 
						  	 Date tsLastUpd, BigDecimal idUserLastUpd, BigDecimal errCode, 
						  	 String errMsg) {
		
		this.idRichNotAll = idRichNotAll;
		this.idRichNotifica = idRichNotifica;
		this.idSpAoo = idSpAoo;
		this.idEmail = idEmail;
		this.uri = uri;
		this.displayFilename = displayFilename;
		this.stato = stato;
		this.tsLastTry = tsLastTry;
		this.tryNum = tryNum;
		this.tsIns = tsIns;
		this.idUserIns = idUserIns;
		this.tsLastUpd = tsLastUpd;
		this.idUserLastUpd = idUserLastUpd;
		this.errCode = errCode;
		this.errMsg = errMsg;
		
	}
	
	@Id
	@Column(name = "ID_RICH_NOT_ALL", unique = true, nullable = false, precision = 38, scale = 0)
	public BigDecimal getIdRichNotAll() {
		return idRichNotAll;
	}

	public void setIdRichNotAll(BigDecimal idRichNotAll) {
		this.idRichNotAll = idRichNotAll;
	}
	
	@Column(name = "ID_RICH_NOTIFICA", nullable = false, length = 64)
	public String getIdRichNotifica() {
		return idRichNotifica;
	}

	public void setIdRichNotifica(String idRichNotifica) {
		this.idRichNotifica = idRichNotifica;
	}
	
	@Column(name = "ID_SP_AOO", nullable = false, precision = 38, scale = 0)
	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	@Column(name = "ID_EMAIL", length = 64)
	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
	@Column(name = "URI", nullable = false, length = 2000)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Column(name = "DISPLAY_FILENAME", length = 1000)
	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	
	@Column(name = "STATO", length = 100)
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	@Column(name = "TS_LAST_TRY")
	public Date getTsLastTry() {
		return tsLastTry;
	}

	public void setTsLastTry(Date tsLastTry) {
		this.tsLastTry = tsLastTry;
	}
	
	@Column(name = "TRY_NUM", nullable = false, precision = 38, scale = 0)
	public BigDecimal getTryNum() {
		return tryNum;
	}

	public void setTryNum(BigDecimal tryNum) {
		this.tryNum = tryNum;
	}
	
	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	
	@Column(name = "ID_USER_INS", precision = 38, scale = 0)
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
	
	@Column(name = "ID_USER_LAST_UPD", precision = 38, scale = 0)
	public BigDecimal getIdUserLastUpd() {
		return idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}
	
	@Column(name = "ERR_CODE", precision = 38, scale = 0)
	public BigDecimal getErrCode() {
		return errCode;
	}

	public void setErrCode(BigDecimal errCode) {
		this.errCode = errCode;
	}
	
	@Column(name = "ERR_MSG")
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
