/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "recuperaPerUriSorg", query = "from TCompDigitReply t join fetch t.TCompDigitali where upper(t.uriSorg) like upper(:uri)"), 
})
@Table(name = "T_COMP_DIGIT_REPLY")
public class TCompDigitReply implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @Column(name = "ID_REP")
	private String idRep;
	public String getIdRep() { return this.idRep; }
	public void setIdRep(String idRep) { this.idRep = idRep; }

	@Basic(optional=false) @Column(name = "FLG_ANN", nullable=false)
	private BigDecimal flgAnn;
	public BigDecimal getFlgAnn() {	return this.flgAnn;	}
	public void setFlgAnn(BigDecimal flgAnn) { this.flgAnn = flgAnn; }

	@Column(name = "ID_APPL_INS")
	private String idApplIns;
	public String getIdApplIns() { return this.idApplIns; }
	public void setIdApplIns(String idApplIns) { this.idApplIns = idApplIns; }

	@Column(name = "ID_APPL_ULTIMO_AGG")
	private String idApplUltimoAgg;
	public String getIdApplUltimoAgg() { return this.idApplUltimoAgg; }
	public void setIdApplUltimoAgg(String idApplUltimoAgg) { this.idApplUltimoAgg = idApplUltimoAgg; }

	@Basic(optional=false) @Column(name = "ID_SOGG_VERS", nullable=false)
	private String idSoggVers;
	public String getIdSoggVers() {	return this.idSoggVers; }
	public void setIdSoggVers(String idSoggVers) { this.idSoggVers = idSoggVers; }

	@Column(name = "ID_UTE_INS")
	private String idUteIns;
	public String getIdUteIns() { return this.idUteIns;	}
	public void setIdUteIns(String idUteIns) { this.idUteIns = idUteIns; }

	@Column(name = "ID_UTE_ULTIMO_AGG")
	private String idUteUltimoAgg;
	public String getIdUteUltimoAgg() {	return this.idUteUltimoAgg;	}
	public void setIdUteUltimoAgg(String idUteUltimoAgg) { this.idUteUltimoAgg = idUteUltimoAgg; }

	@Basic(optional=false) @Column(name = "NOME_FILE", nullable=false)
	private String nomeFile;
	public String getNomeFile() { return this.nomeFile;	}
	public void setNomeFile(String nomeFile) { this.nomeFile = nomeFile; }

	@Basic(optional=false) @Column(name = "TS_INS", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tsIns;
	public Date getTsIns() { return this.tsIns; }
	public void setTsIns(Date tsIns) { this.tsIns = tsIns; }

	@Basic(optional=false) @Column(name = "TS_ULTIMO_AGG", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tsUltimoAgg;
	public Date getTsUltimoAgg() {	return this.tsUltimoAgg; }
	public void setTsUltimoAgg(Date tsUltimoAgg) {	this.tsUltimoAgg = tsUltimoAgg;	}

	@Basic(optional=false) @Column(name = "URI_DEST", nullable=false)
	private String uriDest;
	public String getUriDest() { return uriDest; }
	public void setUriDest(String uriDest) { this.uriDest = uriDest; }

	@Basic(optional=false) @Column(name = "URI_SORG", nullable=false)
	private String uriSorg;
	public String getUriSorg() { return uriSorg; }
	public void setUriSorg(String uriSorg) { this.uriSorg = uriSorg; }

	// bi-directional many-to-one association to TCompDigitali
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ID_CD")
	private TCompDigitali TCompDigitali;
	public TCompDigitali getTCompDigitali() { return this.TCompDigitali; }
	public void setTCompDigitali(TCompDigitali TCompDigitali) { 
		this.TCompDigitali = TCompDigitali;	
	}

}