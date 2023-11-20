/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DMT_WORKFLOW_STATI database table.
 * 
 */
@Entity
@Table(name="DMT_WORKFLOW_STATI")
public class DmtWorkflowStati implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_STATO")
	private String codStato;

	@Column(name="CHECK_CLASS")
	private String checkClass;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;

	@Column(name="COD_STATO_PREC")
	private String codStatoPrec;

	private String controls;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_SP_AOO")
	private BigDecimal idSpAoo;

	@Column(name="TS_INS")
	private Timestamp tsIns;

	//bi-directional many-to-one association to DmtWorkflowStep
	@OneToMany(mappedBy="codStato")
	private Set<DmtWorkflowStep> stato;

	//bi-directional many-to-one association to DmtWorkflowStep
	@OneToMany(mappedBy="codStatoSucc")
	private Set<DmtWorkflowStep> statiSuccessivi;
	
	@Column(name="MAIL_SETTINGS")
	private String mailSettings;
	
	@Formula("(select diz.VALORE from T_VAL_DIZIONARIO diz where diz.ID_REC_DIZ = COD_STATO)")
	private String descrizione;

    public DmtWorkflowStati() {
    }

	public String getCodStato() {
		return this.codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public String getCheckClass() {
		return this.checkClass;
	}

	public void setCheckClass(String checkClass) {
		this.checkClass = checkClass;
	}

	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getCodStatoPrec() {
		return this.codStatoPrec;
	}

	public void setCodStatoPrec(String codStatoPrec) {
		this.codStatoPrec = codStatoPrec;
	}

	public String getControls() {
		return this.controls;
	}

	public void setControls(String controls) {
		this.controls = controls;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public Timestamp getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

	public Set<DmtWorkflowStep> getStato() {
		return stato;
	}

	public void setStato(Set<DmtWorkflowStep> stato) {
		this.stato = stato;
	}

	public Set<DmtWorkflowStep> getStatiSuccessivi() {
		return statiSuccessivi;
	}

	public void setStatiSuccessivi(Set<DmtWorkflowStep> statiSuccessivi) {
		this.statiSuccessivi = statiSuccessivi;
	}

	public String getMailSettings() {
		return mailSettings;
	}

	public void setMailSettings(String mailSettings) {
		this.mailSettings = mailSettings;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}