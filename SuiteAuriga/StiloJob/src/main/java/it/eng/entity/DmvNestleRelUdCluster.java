/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DMV_NESTLE_REL_UD_CLUSTER database table.
 * 
 */
@Entity
@Table(name="DMV_NESTLE_REL_UD_CLUSTER")
public class DmvNestleRelUdCluster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_DESTINATARIO")
	private String codDestinatario;

	@Column(name="COD_STATO_DETT")
	private String codStatoDett;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_DOCUMENTO")
	private Date dataDocumento;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

    @ManyToOne
	@JoinColumn(name="ID_CLUSTER")
	private DmtNestleCluster cluster;

	@Column(name="ID_UD")
	@Id
	private BigDecimal idUd;

	@Column(name="ID_UO")
	private BigDecimal idUo;

	private String mimeype;

	@Column(name="NOME_FILE")
	private String nomeFile;

	@Column(name="NRO_DOCUMENTO")
	private String nroDocumento;

    @Temporal( TemporalType.DATE)
	@Column(name="TS_LAST_UPD")
	private Date tsLastUpd;

	@Column(name="URI_DOC")
	private String uriDoc;
	
	@Formula("(  trunc(SYSDATE) - trunc(TS_LAST_UPD) +1 - ((((TRUNC(SYSDATE,'D'))-(TRUNC(TS_LAST_UPD,'D')))/7)*2) - (CASE WHEN TO_CHAR(SYSDATE,'DY','nls_date_language=english')='SUN' THEN 1 ELSE 0 END) - (CASE WHEN TO_CHAR(TS_LAST_UPD,'DY','nls_date_language=english')='SAT' THEN 1 ELSE 0 END) )")
	private int diffNroGG;
	
	@Formula("( select rel.CANALE from DMT_REL_DESTINATARI_UO rel where rel.COD_DESTINATARIO=COD_DESTINATARIO and rel.ID_UO=ID_UO   )")
	private String canale;

    public DmvNestleRelUdCluster() {
    }

	public String getCodDestinatario() {
		return this.codDestinatario;
	}

	public void setCodDestinatario(String codDestinatario) {
		this.codDestinatario = codDestinatario;
	}

	public String getCodStatoDett() {
		return this.codStatoDett;
	}

	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}

	public Date getDataDocumento() {
		return this.dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getIdUd() {
		return this.idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public BigDecimal getIdUo() {
		return this.idUo;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
	}

	public String getMimeype() {
		return this.mimeype;
	}

	public void setMimeype(String mimeype) {
		this.mimeype = mimeype;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	public String getUriDoc() {
		return this.uriDoc;
	}

	public void setUriDoc(String uriDoc) {
		this.uriDoc = uriDoc;
	}

	public DmtNestleCluster getCluster() {
		return cluster;
	}

	public void setCluster(DmtNestleCluster cluster) {
		this.cluster = cluster;
	}

	public int getDiffNroGG() {
		return diffNroGG;
	}

	public void setDiffNroGG(int diffNroGG) {
		this.diffNroGG = diffNroGG;
	}

	public String getCanale() {
		return canale;
	}

	public void setCanale(String canale) {
		this.canale = canale;
	}
	
	

}