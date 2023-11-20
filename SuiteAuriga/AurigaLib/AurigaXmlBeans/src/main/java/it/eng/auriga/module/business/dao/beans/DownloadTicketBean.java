/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DownloadTicketBean  extends AbstractBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ticketId;
	private String uri;
	private String displayFilename;
	private BigDecimal idUd;
	private boolean flgDaSbustare; 
	private boolean flgDaTimbrare; 
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
		this.getUpdatedProperties().add("uri");
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
		this.getUpdatedProperties().add("ticketId");
	}
	public String getDisplayFilename() {
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
		this.getUpdatedProperties().add("displayFilename");
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
		this.getUpdatedProperties().add("idUd");
	}
	
	public boolean isFlgDaSbustare() {
		return flgDaSbustare;
	}
	
	public void setFlgDaSbustare(boolean flgDaSbustare) {
		this.flgDaSbustare = flgDaSbustare;
		this.getUpdatedProperties().add("flgDaSbustare");
	}
	
	public boolean isFlgDaTimbrare() {
		return flgDaTimbrare;
	}
	public void setFlgDaTimbrare(boolean flgDaTimbrare) {
		this.flgDaTimbrare = flgDaTimbrare;
		this.getUpdatedProperties().add("flgDaTimbrare");
	}
	
}
