/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 15-lug-2015 10.24.04 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TBtDecTax generated by hbm2java
 */
@Entity
@Table(name = "T_BT_DEC_TAX")
public class TBtDecTax implements java.io.Serializable {

	private static final long serialVersionUID = -4607192471210210233L;

	private TBtDecTaxId id;

	public TBtDecTax() {
	}

	public TBtDecTax(TBtDecTaxId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "taxType", column = @Column(name = "TAX_TYPE")),
			@AttributeOverride(name = "taxTypeDescr", column = @Column(name = "TAX_TYPE_DESCR")),
			@AttributeOverride(name = "natura", column = @Column(name = "NATURA")),
			@AttributeOverride(name = "iva", column = @Column(name = "IVA")),
			@AttributeOverride(name = "idSpAoo", column = @Column(name = "ID_SP_AOO", nullable = false)),
			@AttributeOverride(name = "esigibilita", column = @Column(name = "ESIGIBILITA", length = 1)) })
	public TBtDecTaxId getId() {
		return this.id;
	}

	public void setId(TBtDecTaxId id) {
		this.id = id;
	}

}
