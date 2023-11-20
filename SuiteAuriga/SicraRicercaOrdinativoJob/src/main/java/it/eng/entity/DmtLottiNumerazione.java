/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * The persistent class for the DMT_LOTTI_NUMERAZIONE database table.
 * 
 */
@Entity
@Table(name="DMT_LOTTI_NUMERAZIONE" ,  schema = "auri_owner_1")
public class DmtLottiNumerazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_LOTTO_NUM")
	private String idLottoNum;

	@Column(name="ANNO")
	private Integer anno;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;

	@Column(name="COD_SEZIONALE")
	private String codSezionale;

	@Column(name="COD_SOCIETA")
	private String codSocieta;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_DOC_TYPE")
	private BigDecimal idDocType;

	@Column(name="NOME_FILE")
	private String nomeFile;

	@Column(name="NUM_A")
	private String numA;

	@Column(name="NUM_DA")
	private String numDa;

    @Lob()
	@Column(name="NUMERAZIONI")
	private String numerazioni;

	@Column(name="TS_INS")
	private Timestamp tsIns;

	@Column(name="TS_LAST_UPD")
	private Timestamp tsLastUpd;

//	//bi-directional many-to-one association to DmtLottiCheckNum
//	@OneToMany(mappedBy="dmtLottiNumerazione")
//	private Set<DmtLottiCheckNum> dmtLottiCheckNums;

    public DmtLottiNumerazione() {
    }

	public String getIdLottoNum() {
		return this.idLottoNum;
	}

	public void setIdLottoNum(String idLottoNum) {
		this.idLottoNum = idLottoNum;
	}

	public Integer getAnno() {
		return this.anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getCodSezionale() {
		return this.codSezionale;
	}

	public void setCodSezionale(String codSezionale) {
		this.codSezionale = codSezionale;
	}

	public String getCodSocieta() {
		return this.codSocieta;
	}

	public void setCodSocieta(String codSocieta) {
		this.codSocieta = codSocieta;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getIdDocType() {
		return this.idDocType;
	}

	public void setIdDocType(BigDecimal idDocType) {
		this.idDocType = idDocType;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNumA() {
		return this.numA;
	}

	public void setNumA(String numA) {
		this.numA = numA;
	}

	public String getNumDa() {
		return this.numDa;
	}

	public void setNumDa(String numDa) {
		this.numDa = numDa;
	}

	
	public String getNumerazioni() {
		return numerazioni;
	}

	public void setNumerazioni(String numerazioni) {
		this.numerazioni = numerazioni;
	}

	public Timestamp getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

	public Timestamp getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Timestamp tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	
	
}