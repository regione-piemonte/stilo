/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the DMT_NESTLE_CLUSTER database table.
 * 
 */
@Entity
@Table(name = "DMT_NESTLE_CLUSTER")
public class DmtNestleCluster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CODICE")
	private String idCodice;

	private String descrizione;

	@Column(name = "FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name = "ID_UO")
	private BigDecimal idUo;

	private String layout;

	private String prefix;

	@Column(name = "TIPO_DOC")
	private String tipoDoc;

	// //bi-directional many-to-one association to DmtNestleRelDocType
	// @ManyToOne
	// @JoinColumn(name="TIPO_DOC")
	// private DmtNestleRelDocType dmtNestleRelDocType;

	// bi-directional many-to-one association to DmtNestleBusiness
	@ManyToOne
	@JoinColumn(name = "ID_COD_BUSINESS")
	private DmtNestleBusiness dmtNestleBusiness;

	// bi-directional many-to-one association to DmtNestleDistribChannel
	@ManyToOne
	@JoinColumn(name = "ID_COD_DISTR_CH")
	private DmtNestleDistribChannel dmtNestleDistribChannel;

	// bi-directional many-to-one association to DmtNestleSaleOrganization
	@ManyToOne
	@JoinColumn(name = "ID_COD_SALE_ORG")
	private DmtNestleSaleOrganization dmtNestleSaleOrganization;

	public DmtNestleCluster() {
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

	public BigDecimal getIdUo() {
		return this.idUo;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public DmtNestleBusiness getDmtNestleBusiness() {
		return this.dmtNestleBusiness;
	}

	public void setDmtNestleBusiness(DmtNestleBusiness dmtNestleBusiness) {
		this.dmtNestleBusiness = dmtNestleBusiness;
	}

	public DmtNestleDistribChannel getDmtNestleDistribChannel() {
		return this.dmtNestleDistribChannel;
	}

	public void setDmtNestleDistribChannel(DmtNestleDistribChannel dmtNestleDistribChannel) {
		this.dmtNestleDistribChannel = dmtNestleDistribChannel;
	}

	public DmtNestleSaleOrganization getDmtNestleSaleOrganization() {
		return this.dmtNestleSaleOrganization;
	}

	public void setDmtNestleSaleOrganization(DmtNestleSaleOrganization dmtNestleSaleOrganization) {
		this.dmtNestleSaleOrganization = dmtNestleSaleOrganization;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	// public DmtNestleRelDocType getDmtNestleRelDocType() {
	// return dmtNestleRelDocType;
	// }
	//
	// public void setDmtNestleRelDocType(DmtNestleRelDocType
	// dmtNestleRelDocType) {
	// this.dmtNestleRelDocType = dmtNestleRelDocType;
	// }

}