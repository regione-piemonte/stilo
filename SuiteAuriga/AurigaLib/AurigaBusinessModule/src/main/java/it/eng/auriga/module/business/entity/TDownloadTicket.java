/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_DOWNLOAD_TICKET")
@org.hibernate.annotations.Entity(dynamicUpdate = false, dynamicInsert = false)

public class TDownloadTicket implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticketId;
	private String uri;
	private String displayFilename;
	private BigDecimal idUd;
	private boolean flgDaSbustare;
//	private boolean flgDaTimbrare;
	
	public TDownloadTicket() {
	}
	
	public TDownloadTicket(String ticketId, String uri, String displayFilename, BigDecimal idUd, boolean flgDaSbustare/*, boolean flgDaTimbrare*/) {
	
		this.ticketId = ticketId;
		this.uri = uri;
		this.displayFilename = displayFilename;
		this.idUd = idUd;
		this.flgDaSbustare = flgDaSbustare;
//		this.flgDaTimbrare = flgDaTimbrare;
	
	}
	
	@Id
	@Column(name = "TICKET_ID", nullable = false, length = 64)
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name = "URI", nullable = false, length = 1000)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name = "DISPLAY_FILENAME", nullable = false, length = 500)
	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	@Column(name = "FLG_DA_SBUSTARE", nullable = false, precision = 1, scale = 0)
	public boolean isFlgDaSbustare() {
		return this.flgDaSbustare;
	}

	public void setFlgDaSbustare(boolean DaSbustare) {
		this.flgDaSbustare = DaSbustare;
	}

//	@Column(name = "FLG_DA_TIMBRARE", nullable = false, precision = 1, scale = 0)
//	public boolean isFlgDaTimbrare() {
//		return flgDaTimbrare;
//	}
//
//	public void setFlgDaTimbrare(boolean flgDaTimbrare) {
//		this.flgDaTimbrare = flgDaTimbrare;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayFilename == null) ? 0 : displayFilename.hashCode());
		result = prime * result + ((ticketId == null) ? 0 : ticketId.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((idUd == null) ? 0 : idUd.hashCode());
		result = prime * result + (flgDaSbustare ? 1 : 0);
//		result = prime * result + (flgDaTimbrare ? 1 : 0);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TDownloadTicket other = (TDownloadTicket) obj;
		if (displayFilename == null) {
			if (other.displayFilename != null)
				return false;
		} else if (!displayFilename.equals(other.displayFilename))
			return false;
		if (ticketId == null) {
			if (other.ticketId != null)
				return false;
		} else if (!ticketId.equals(other.ticketId))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		if (idUd == null) {
			if (other.idUd != null)
				return false;
		} else if (!idUd.equals(other.idUd))
			return false;
		if (flgDaSbustare != other.flgDaSbustare)
				return false;
//		if (flgDaTimbrare != other.flgDaTimbrare)
//			return false;
		return true;
	}
	
	
}
