/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the T_TEMPLATE_EMAIL_BODY database table.
 * 
 */
@Entity
@Table(name="T_TEMPLATE_EMAIL_BODY")
public class TTemplateEmailBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_TEMPLATE_BODY")
	private String codTemplateBody;

    @Lob()
	private String body;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_UTE_INS")
	private String idUteIns;

	@Column(name="ID_UTE_ULTIMO_AGG")
	private String idUteUltimoAgg;

	@Column(name="TS_INS")
	private Timestamp tsIns;

	@Column(name="TS_ULTIMO_AGG")
	private Timestamp tsUltimoAgg;

	//bi-directional many-to-one association to TAnagTemplateEmail
	@OneToMany(mappedBy="TTemplateEmailBody")
	private Set<TAnagTemplateEmail> TAnagTemplateEmails;

    public TTemplateEmailBody() {
    }

	public String getCodTemplateBody() {
		return this.codTemplateBody;
	}

	public void setCodTemplateBody(String codTemplateBody) {
		this.codTemplateBody = codTemplateBody;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getIdUteIns() {
		return this.idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return this.idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public Timestamp getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

	public Timestamp getTsUltimoAgg() {
		return this.tsUltimoAgg;
	}

	public void setTsUltimoAgg(Timestamp tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public Set<TAnagTemplateEmail> getTAnagTemplateEmails() {
		return this.TAnagTemplateEmails;
	}

	public void setTAnagTemplateEmails(Set<TAnagTemplateEmail> TAnagTemplateEmails) {
		this.TAnagTemplateEmails = TAnagTemplateEmails;
	}
	
}