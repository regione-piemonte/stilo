/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the DMT_NESTLE_RANGE_ANAG_NC database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_RANGE_ANAG_NC")
public class DmtNestleRangeAnagNc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_RANGE_ANAG_NC")
	private String idRangeAnagNc;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="NUMERO_DOC_A")
	private String numeroDocA;

	@Column(name="NUMERO_DOC_DA")
	private String numeroDocDa;

	@Column(name="TS_INS")
	private Timestamp tsIns;

    public DmtNestleRangeAnagNc() {
    }

	public String getIdRangeAnagNc() {
		return idRangeAnagNc;
	}

	public void setIdRangeAnagNc(String idRangeAnagNc) {
		this.idRangeAnagNc = idRangeAnagNc;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public BigDecimal getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getNumeroDocA() {
		return numeroDocA;
	}

	public void setNumeroDocA(String numeroDocA) {
		this.numeroDocA = numeroDocA;
	}

	public String getNumeroDocDa() {
		return numeroDocDa;
	}

	public void setNumeroDocDa(String numeroDocDa) {
		this.numeroDocDa = numeroDocDa;
	}

	public Timestamp getTsIns() {
		return tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

}