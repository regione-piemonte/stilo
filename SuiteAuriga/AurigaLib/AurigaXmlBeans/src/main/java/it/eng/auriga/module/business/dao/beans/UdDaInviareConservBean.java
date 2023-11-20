/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

 
@XmlRootElement
public class UdDaInviareConservBean extends AbstractBean implements Serializable {      

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476671280933149685L;
	
	private BigDecimal idUd;
	private String useridAppl;
	private String idSoggVers;
	private Date tsLastTry;
	private BigDecimal tryNum;
	private String msgErrInvio;
	private Date tsInvioConserv;
	private String idSip;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String fetchSize;
	
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
		this.getUpdatedProperties().add("idUd");
	}
	public String getUseridAppl() {
		return useridAppl;
	}
	public void setUseridAppl(String useridAppl) {
		this.useridAppl = useridAppl;
		this.getUpdatedProperties().add("useridAppl");
	}
	public String getIdSoggVers() {
		return idSoggVers;
	}
	public void setIdSoggVers(String idSoggVers) {
		this.idSoggVers = idSoggVers;
		this.getUpdatedProperties().add("idSoggVers");
	}
	public Date getTsLastTry() {
		return tsLastTry;
	}
	public void setTsLastTry(Date tsLastTry) {
		this.tsLastTry = tsLastTry;
		this.getUpdatedProperties().add("tsLastTry");
	}
	public BigDecimal getTryNum() {
		return tryNum;
	}
	public void setTryNum(BigDecimal tryNum) {
		this.tryNum = tryNum;
		this.getUpdatedProperties().add("tryNum");
	}
	public String getMsgErrInvio() {
		return msgErrInvio;
	}
	public void setMsgErrInvio(String msgErrInvio) {
		this.msgErrInvio = msgErrInvio;
		this.getUpdatedProperties().add("msgErrInvio");
	}
	public Date getTsInvioConserv() {
		return tsInvioConserv;
	}
	public void setTsInvioConserv(Date tsInvioConserv) {
		this.tsInvioConserv = tsInvioConserv;
		this.getUpdatedProperties().add("tsInvioConserv");
	}
	public String getIdSip() {
		return idSip;
	}
	public void setIdSip(String idSip) {
		this.idSip = idSip;
		this.getUpdatedProperties().add("idSip");
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}
	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}
	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
		this.getUpdatedProperties().add("tsUltimoAgg");
	}
	public String getFetchSize() {
		return fetchSize;
	}
	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
		this.getUpdatedProperties().add("fetchSize");
	}	
		
}   

