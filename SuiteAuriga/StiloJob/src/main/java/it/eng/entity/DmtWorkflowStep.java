/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the DMT_WORKFLOW_STEPS database table.
 * 
 */
@Entity
@Table(name="DMT_WORKFLOW_STEPS")
public class DmtWorkflowStep implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_STEP")
	private String idStep;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="TS_INS")
	private Timestamp tsIns;

	//bi-directional many-to-one association to DmtWorkflowStati
    @ManyToOne
	@JoinColumn(name="COD_STATO")
	private DmtWorkflowStati codStato;

	//bi-directional many-to-one association to DmtWorkflowStati
    @ManyToOne
	@JoinColumn(name="COD_STATO_SUCC")
	private DmtWorkflowStati codStatoSucc;

    public DmtWorkflowStep() {
    }

	public String getIdStep() {
		return this.idStep;
	}

	public void setIdStep(String idStep) {
		this.idStep = idStep;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public Timestamp getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

	public DmtWorkflowStati getCodStato() {
		return codStato;
	}

	public void setCodStato(DmtWorkflowStati codStato) {
		this.codStato = codStato;
	}

	public DmtWorkflowStati getCodStatoSucc() {
		return codStatoSucc;
	}

	public void setCodStatoSucc(DmtWorkflowStati codStatoSucc) {
		this.codStatoSucc = codStatoSucc;
	}
	

	
}