/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 8-ago-2014 9.40.50 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TBaDecInvoiceType generated by hbm2java
 */
@Entity
@Table(name = "T_BA_DEC_INVOICE_TYPE")
public class TBaDecInvoiceType implements java.io.Serializable {

	private static final long serialVersionUID = 2642779740392856104L;

	private TBaDecInvoiceTypeId id;

	public TBaDecInvoiceType() {
	}

	public TBaDecInvoiceType(TBaDecInvoiceTypeId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "invoiceType", column = @Column(name = "INVOICE_TYPE")),
			@AttributeOverride(name = "tipoDocumento", column = @Column(name = "TIPO_DOCUMENTO")),
			@AttributeOverride(name = "idSpAoo", column = @Column(name = "ID_SP_AOO")) })
	public TBaDecInvoiceTypeId getId() {
		return this.id;
	}

	public void setId(TBaDecInvoiceTypeId id) {
		this.id = id;
	}

}
