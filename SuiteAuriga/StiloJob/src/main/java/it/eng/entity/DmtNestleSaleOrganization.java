/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DMT_NESTLE_SALE_ORGANIZATION database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_SALE_ORGANIZATION")
public class DmtNestleSaleOrganization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CODICE")
	private String idCodice;

	private String descrizione;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="FLG_INVIOMAIL_UT_WEB")
	private BigDecimal flgInviomailUtWeb;

	@Column(name="FLG_LETT_ADES_NUOVO_CL")
	private BigDecimal flgLettAdesNuovoCl;

	@Column(name="GG_INVIO_POSTA")
	private BigDecimal ggInvioPosta;

	@Column(name="GG_PRIMO_SOLLECITO")
	private BigDecimal ggPrimoSollecito;

	@Column(name="MITT_MAIL")
	private String mittMail;

	@Column(name="MITT_PEC")
	private String mittPec;

	//bi-directional many-to-one association to DmtNestleCluster
	@OneToMany(mappedBy="dmtNestleSaleOrganization")
	private Set<DmtNestleCluster> dmtNestleClusters;

    public DmtNestleSaleOrganization() {
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

	public BigDecimal getFlgInviomailUtWeb() {
		return this.flgInviomailUtWeb;
	}

	public void setFlgInviomailUtWeb(BigDecimal flgInviomailUtWeb) {
		this.flgInviomailUtWeb = flgInviomailUtWeb;
	}

	public BigDecimal getFlgLettAdesNuovoCl() {
		return this.flgLettAdesNuovoCl;
	}

	public void setFlgLettAdesNuovoCl(BigDecimal flgLettAdesNuovoCl) {
		this.flgLettAdesNuovoCl = flgLettAdesNuovoCl;
	}

	public BigDecimal getGgInvioPosta() {
		return this.ggInvioPosta;
	}

	public void setGgInvioPosta(BigDecimal ggInvioPosta) {
		this.ggInvioPosta = ggInvioPosta;
	}

	public BigDecimal getGgPrimoSollecito() {
		return this.ggPrimoSollecito;
	}

	public void setGgPrimoSollecito(BigDecimal ggPrimoSollecito) {
		this.ggPrimoSollecito = ggPrimoSollecito;
	}

	public String getMittMail() {
		return this.mittMail;
	}

	public void setMittMail(String mittMail) {
		this.mittMail = mittMail;
	}

	public String getMittPec() {
		return this.mittPec;
	}

	public void setMittPec(String mittPec) {
		this.mittPec = mittPec;
	}

	public Set<DmtNestleCluster> getDmtNestleClusters() {
		return this.dmtNestleClusters;
	}

	public void setDmtNestleClusters(Set<DmtNestleCluster> dmtNestleClusters) {
		this.dmtNestleClusters = dmtNestleClusters;
	}
	
}