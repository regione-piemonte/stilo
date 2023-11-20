/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the T_ANAG_TEMPLATE_EMAIL database table.
 * 
 */
@Entity
@Table(name="T_ANAG_TEMPLATE_EMAIL")
public class TAnagTemplateEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TEMPLATE_EMAIL")
	private String idTemplateEmail;

	@Column(name="ACCOUNT_MITT")
	private String accountMitt;

	@Column(name="CI_APPLICAZIONE")
	private String ciApplicazione;

	@Column(name="CI_ISTANZA_APPLICAZIONE")
	private String ciIstanzaApplicazione;

	@Column(name="COD_LINGUA")
	private String codLingua;

	@Column(name="COD_TEMPLATE_SUBJECT")
	private String codTemplateSubject;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_ACCOUNT_MITT")
	private String idAccountMitt;

	@Column(name="ID_SP_AOO")
	private BigDecimal idSpAoo;

	@Column(name="ID_UTE_INS")
	private String idUteIns;

	@Column(name="ID_UTE_ULTIMO_AGG")
	private String idUteUltimoAgg;

	@Column(name="TIPO_INVIO")
	private String tipoInvio;

	@Column(name="TS_INS")
	private Timestamp tsIns;

	@Column(name="TS_ULTIMO_AGG")
	private Timestamp tsUltimoAgg;

	//bi-directional many-to-one association to TTemplateEmailBody
    @ManyToOne
	@JoinColumn(name="COD_TEMPLATE_BODY")
	private TTemplateEmailBody TTemplateEmailBody;

    public TAnagTemplateEmail() {
    }

	public String getIdTemplateEmail() {
		return this.idTemplateEmail;
	}

	public void setIdTemplateEmail(String idTemplateEmail) {
		this.idTemplateEmail = idTemplateEmail;
	}

	public String getAccountMitt() {
		return this.accountMitt;
	}

	public void setAccountMitt(String accountMitt) {
		this.accountMitt = accountMitt;
	}

	public String getCiApplicazione() {
		return this.ciApplicazione;
	}

	public void setCiApplicazione(String ciApplicazione) {
		this.ciApplicazione = ciApplicazione;
	}

	public String getCiIstanzaApplicazione() {
		return this.ciIstanzaApplicazione;
	}

	public void setCiIstanzaApplicazione(String ciIstanzaApplicazione) {
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
	}

	public String getCodLingua() {
		return this.codLingua;
	}

	public void setCodLingua(String codLingua) {
		this.codLingua = codLingua;
	}

	public String getCodTemplateSubject() {
		return this.codTemplateSubject;
	}

	public void setCodTemplateSubject(String codTemplateSubject) {
		this.codTemplateSubject = codTemplateSubject;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getIdAccountMitt() {
		return this.idAccountMitt;
	}

	public void setIdAccountMitt(String idAccountMitt) {
		this.idAccountMitt = idAccountMitt;
	}

	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
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

	public String getTipoInvio() {
		return this.tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
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

	public TTemplateEmailBody getTTemplateEmailBody() {
		return this.TTemplateEmailBody;
	}

	public void setTTemplateEmailBody(TTemplateEmailBody TTemplateEmailBody) {
		this.TTemplateEmailBody = TTemplateEmailBody;
	}
	
}