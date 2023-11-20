/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DMT_NESTLE_DISTRIB_CHANNEL database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_DISTRIB_CHANNEL")
public class DmtNestleDistribChannel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CODICE")
	private String idCodice;

	private String descrizione;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="FLG_EXPORT")
	private BigDecimal flgExport;

	//bi-directional many-to-one association to DmtNestleCluster
	@OneToMany(mappedBy="dmtNestleDistribChannel")
	private Set<DmtNestleCluster> dmtNestleClusters;

    public DmtNestleDistribChannel() {
    }

	public String getIdCodice() {
		return this.idCodice;
	}

	public void setIdCodice(String idCodice) {
		this.idCodice = idCodice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getFlgExport() {
		return this.flgExport;
	}

	public void setFlgExport(BigDecimal flgExport) {
		this.flgExport = flgExport;
	}

	public Set<DmtNestleCluster> getDmtNestleClusters() {
		return this.dmtNestleClusters;
	}

	public void setDmtNestleClusters(Set<DmtNestleCluster> dmtNestleClusters) {
		this.dmtNestleClusters = dmtNestleClusters;
	}
	
}