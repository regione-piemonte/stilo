/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;


/**
 * The persistent class for the DMT_NESTLE_ANAG_NC database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_ANAG_NC")
public class DmtNestleAnagNc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ANAG_NC")
	private String idAnagNc;

	@Column(name="DATA_DOC")
	private Date dataDoc;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;
	
	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="INVIO")
	private String invio;

	@Column(name="NUMERO_DOC")
	private String numeroDoc;

	@Column(name="TS_INS")
	private Timestamp tsIns;

    public DmtNestleAnagNc() {
    }

	public String getIdAnagNc() {
		return this.idAnagNc;
	}

	public void setIdAnagNc(String idAnagNc) {
		this.idAnagNc = idAnagNc;
	}

	public Date getDataDoc() {
		return this.dataDoc;
	}

	public void setDataDoc(Date dataDoc) {
		this.dataDoc = dataDoc;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getInvio() {
		return this.invio;
	}

	public void setInvio(String invio) {
		this.invio = invio;
	}

	public String getNumeroDoc() {
		return this.numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public Timestamp getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

}