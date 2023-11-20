/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DMT_DESTINATARI_DOC database table.
 * 
 */
@Entity
@Table(name="DMT_DESTINATARI_DOC")
public class DmtDestinatariDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DESTINATARIO")
	private long idDestinatario;

//	@Column(name="ALTRI_ATTRIBUTI")
//	private Object altriAttributi;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;

	@Column(name="COD_IST_APPL_OWNER")
	private String codIstApplOwner;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_IN_RUBRICA")
	private BigDecimal idInRubrica;

	@Column(name="ID_SP_AOO")
	private BigDecimal idSpAoo;

	@Column(name="ID_USER_INS")
	private BigDecimal idUserIns;

	@Column(name="ID_USER_LAST_UPD")
	private BigDecimal idUserLastUpd;

	@Column(name="MOTIVI_ANN")
	private String motiviAnn;

	@Column(name="PROV_CI_DEST_DOC")
	private String provCiDestDoc;

    @Temporal( TemporalType.DATE)
	@Column(name="TS_INS")
	private Date tsIns;

    @Temporal( TemporalType.DATE)
	@Column(name="TS_LAST_UPD")
	private Date tsLastUpd;
    
	//bi-directional many-to-one association to DmtRelDestinatariUo
	@OneToMany(mappedBy="dmtDestinatariDoc")
	private Set<DmtRelDestinatariUo> dmtRelDestinatariUos;

    public DmtDestinatariDoc() {
    }

	public long getIdDestinatario() {
		return this.idDestinatario;
	}

	public void setIdDestinatario(long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

//	public Object getAltriAttributi() {
//		return this.altriAttributi;
//	}
//
//	public void setAltriAttributi(Object altriAttributi) {
//		this.altriAttributi = altriAttributi;
//	}

	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getCodIstApplOwner() {
		return this.codIstApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getIdInRubrica() {
		return this.idInRubrica;
	}

	public void setIdInRubrica(BigDecimal idInRubrica) {
		this.idInRubrica = idInRubrica;
	}

	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public BigDecimal getIdUserIns() {
		return this.idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
	}

	public BigDecimal getIdUserLastUpd() {
		return this.idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}

	public String getMotiviAnn() {
		return this.motiviAnn;
	}

	public void setMotiviAnn(String motiviAnn) {
		this.motiviAnn = motiviAnn;
	}

	public String getProvCiDestDoc() {
		return this.provCiDestDoc;
	}

	public void setProvCiDestDoc(String provCiDestDoc) {
		this.provCiDestDoc = provCiDestDoc;
	}

	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	public Set<DmtRelDestinatariUo> getDmtRelDestinatariUos() {
		return this.dmtRelDestinatariUos;
	}

	public void setDmtRelDestinatariUos(Set<DmtRelDestinatariUo> dmtRelDestinatariUos) {
		this.dmtRelDestinatariUos = dmtRelDestinatariUos;
	}
	
}