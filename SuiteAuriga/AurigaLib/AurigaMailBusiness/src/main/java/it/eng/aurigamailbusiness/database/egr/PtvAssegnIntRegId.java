/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvAssegnIntRegId generated by hbm2java
 */
@Embeddable
public class PtvAssegnIntRegId implements java.io.Serializable {

	private int idDoc;
	private short nroPosiz;
	private Integer idUo;
	private Integer idAnag;
	private Integer idIndice;
	private Integer idFascicolo;
	private Integer numSottofasc;
	private Boolean flgCc;
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private Short idTpFis;
	private String desAnag;
	private String flgTpAnag;
	private String codFis;
	private String parIva;
	private Integer matAnag;
	private Date dtIniVld;
	private Date dtFinVld;
	private String email;
	private String indicepaCodAmm;
	private String indicepaCodAoo;

	public PtvAssegnIntRegId() {
	}

	public PtvAssegnIntRegId(int idDoc, short nroPosiz) {
		this.idDoc = idDoc;
		this.nroPosiz = nroPosiz;
	}

	public PtvAssegnIntRegId(int idDoc, short nroPosiz, Integer idUo,
			Integer idAnag, Integer idIndice, Integer idFascicolo,
			Integer numSottofasc, Boolean flgCc, Date dtIns, Integer uteIns,
			Date dtUltMod, Integer uteUltMod, Short idTpFis, String desAnag,
			String flgTpAnag, String codFis, String parIva, Integer matAnag,
			Date dtIniVld, Date dtFinVld, String email, String indicepaCodAmm,
			String indicepaCodAoo) {
		this.idDoc = idDoc;
		this.nroPosiz = nroPosiz;
		this.idUo = idUo;
		this.idAnag = idAnag;
		this.idIndice = idIndice;
		this.idFascicolo = idFascicolo;
		this.numSottofasc = numSottofasc;
		this.flgCc = flgCc;
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.idTpFis = idTpFis;
		this.desAnag = desAnag;
		this.flgTpAnag = flgTpAnag;
		this.codFis = codFis;
		this.parIva = parIva;
		this.matAnag = matAnag;
		this.dtIniVld = dtIniVld;
		this.dtFinVld = dtFinVld;
		this.email = email;
		this.indicepaCodAmm = indicepaCodAmm;
		this.indicepaCodAoo = indicepaCodAoo;
	}

	@Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)
	public int getIdDoc() {
		return this.idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	@Column(name = "NRO_POSIZ", nullable = false, precision = 4, scale = 0)
	public short getNroPosiz() {
		return this.nroPosiz;
	}

	public void setNroPosiz(short nroPosiz) {
		this.nroPosiz = nroPosiz;
	}

	@Column(name = "ID_UO", precision = 8, scale = 0)
	public Integer getIdUo() {
		return this.idUo;
	}

	public void setIdUo(Integer idUo) {
		this.idUo = idUo;
	}

	@Column(name = "ID_ANAG", precision = 8, scale = 0)
	public Integer getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "ID_INDICE", precision = 8, scale = 0)
	public Integer getIdIndice() {
		return this.idIndice;
	}

	public void setIdIndice(Integer idIndice) {
		this.idIndice = idIndice;
	}

	@Column(name = "ID_FASCICOLO", precision = 8, scale = 0)
	public Integer getIdFascicolo() {
		return this.idFascicolo;
	}

	public void setIdFascicolo(Integer idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	@Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)
	public Integer getNumSottofasc() {
		return this.numSottofasc;
	}

	public void setNumSottofasc(Integer numSottofasc) {
		this.numSottofasc = numSottofasc;
	}

	@Column(name = "FLG_CC", precision = 1, scale = 0)
	public Boolean getFlgCc() {
		return this.flgCc;
	}

	public void setFlgCc(Boolean flgCc) {
		this.flgCc = flgCc;
	}

	@Column(name = "DT_INS", length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "UTE_INS", precision = 6, scale = 0)
	public Integer getUteIns() {
		return this.uteIns;
	}

	public void setUteIns(Integer uteIns) {
		this.uteIns = uteIns;
	}

	@Column(name = "DT_ULT_MOD", length = 7)
	public Date getDtUltMod() {
		return this.dtUltMod;
	}

	public void setDtUltMod(Date dtUltMod) {
		this.dtUltMod = dtUltMod;
	}

	@Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)
	public Integer getUteUltMod() {
		return this.uteUltMod;
	}

	public void setUteUltMod(Integer uteUltMod) {
		this.uteUltMod = uteUltMod;
	}

	@Column(name = "ID_TP_FIS", precision = 4, scale = 0)
	public Short getIdTpFis() {
		return this.idTpFis;
	}

	public void setIdTpFis(Short idTpFis) {
		this.idTpFis = idTpFis;
	}

	@Column(name = "DES_ANAG", length = 750)
	public String getDesAnag() {
		return this.desAnag;
	}

	public void setDesAnag(String desAnag) {
		this.desAnag = desAnag;
	}

	@Column(name = "FLG_TP_ANAG", length = 1)
	public String getFlgTpAnag() {
		return this.flgTpAnag;
	}

	public void setFlgTpAnag(String flgTpAnag) {
		this.flgTpAnag = flgTpAnag;
	}

	@Column(name = "COD_FIS", length = 16)
	public String getCodFis() {
		return this.codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	@Column(name = "PAR_IVA", length = 11)
	public String getParIva() {
		return this.parIva;
	}

	public void setParIva(String parIva) {
		this.parIva = parIva;
	}

	@Column(name = "MAT_ANAG", precision = 9, scale = 0)
	public Integer getMatAnag() {
		return this.matAnag;
	}

	public void setMatAnag(Integer matAnag) {
		this.matAnag = matAnag;
	}

	@Column(name = "DT_INI_VLD", length = 7)
	public Date getDtIniVld() {
		return this.dtIniVld;
	}

	public void setDtIniVld(Date dtIniVld) {
		this.dtIniVld = dtIniVld;
	}

	@Column(name = "DT_FIN_VLD", length = 7)
	public Date getDtFinVld() {
		return this.dtFinVld;
	}

	public void setDtFinVld(Date dtFinVld) {
		this.dtFinVld = dtFinVld;
	}

	@Column(name = "EMAIL", length = 400)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "INDICEPA_COD_AMM", length = 100)
	public String getIndicepaCodAmm() {
		return this.indicepaCodAmm;
	}

	public void setIndicepaCodAmm(String indicepaCodAmm) {
		this.indicepaCodAmm = indicepaCodAmm;
	}

	@Column(name = "INDICEPA_COD_AOO", length = 100)
	public String getIndicepaCodAoo() {
		return this.indicepaCodAoo;
	}

	public void setIndicepaCodAoo(String indicepaCodAoo) {
		this.indicepaCodAoo = indicepaCodAoo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PtvAssegnIntRegId))
			return false;
		PtvAssegnIntRegId castOther = (PtvAssegnIntRegId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& (this.getNroPosiz() == castOther.getNroPosiz())
				&& ((this.getIdUo() == castOther.getIdUo()) || (this.getIdUo() != null
						&& castOther.getIdUo() != null && this.getIdUo()
						.equals(castOther.getIdUo())))
				&& ((this.getIdAnag() == castOther.getIdAnag()) || (this
						.getIdAnag() != null && castOther.getIdAnag() != null && this
						.getIdAnag().equals(castOther.getIdAnag())))
				&& ((this.getIdIndice() == castOther.getIdIndice()) || (this
						.getIdIndice() != null
						&& castOther.getIdIndice() != null && this
						.getIdIndice().equals(castOther.getIdIndice())))
				&& ((this.getIdFascicolo() == castOther.getIdFascicolo()) || (this
						.getIdFascicolo() != null
						&& castOther.getIdFascicolo() != null && this
						.getIdFascicolo().equals(castOther.getIdFascicolo())))
				&& ((this.getNumSottofasc() == castOther.getNumSottofasc()) || (this
						.getNumSottofasc() != null
						&& castOther.getNumSottofasc() != null && this
						.getNumSottofasc().equals(castOther.getNumSottofasc())))
				&& ((this.getFlgCc() == castOther.getFlgCc()) || (this
						.getFlgCc() != null && castOther.getFlgCc() != null && this
						.getFlgCc().equals(castOther.getFlgCc())))
				&& ((this.getDtIns() == castOther.getDtIns()) || (this
						.getDtIns() != null && castOther.getDtIns() != null && this
						.getDtIns().equals(castOther.getDtIns())))
				&& ((this.getUteIns() == castOther.getUteIns()) || (this
						.getUteIns() != null && castOther.getUteIns() != null && this
						.getUteIns().equals(castOther.getUteIns())))
				&& ((this.getDtUltMod() == castOther.getDtUltMod()) || (this
						.getDtUltMod() != null
						&& castOther.getDtUltMod() != null && this
						.getDtUltMod().equals(castOther.getDtUltMod())))
				&& ((this.getUteUltMod() == castOther.getUteUltMod()) || (this
						.getUteUltMod() != null
						&& castOther.getUteUltMod() != null && this
						.getUteUltMod().equals(castOther.getUteUltMod())))
				&& ((this.getIdTpFis() == castOther.getIdTpFis()) || (this
						.getIdTpFis() != null && castOther.getIdTpFis() != null && this
						.getIdTpFis().equals(castOther.getIdTpFis())))
				&& ((this.getDesAnag() == castOther.getDesAnag()) || (this
						.getDesAnag() != null && castOther.getDesAnag() != null && this
						.getDesAnag().equals(castOther.getDesAnag())))
				&& ((this.getFlgTpAnag() == castOther.getFlgTpAnag()) || (this
						.getFlgTpAnag() != null
						&& castOther.getFlgTpAnag() != null && this
						.getFlgTpAnag().equals(castOther.getFlgTpAnag())))
				&& ((this.getCodFis() == castOther.getCodFis()) || (this
						.getCodFis() != null && castOther.getCodFis() != null && this
						.getCodFis().equals(castOther.getCodFis())))
				&& ((this.getParIva() == castOther.getParIva()) || (this
						.getParIva() != null && castOther.getParIva() != null && this
						.getParIva().equals(castOther.getParIva())))
				&& ((this.getMatAnag() == castOther.getMatAnag()) || (this
						.getMatAnag() != null && castOther.getMatAnag() != null && this
						.getMatAnag().equals(castOther.getMatAnag())))
				&& ((this.getDtIniVld() == castOther.getDtIniVld()) || (this
						.getDtIniVld() != null
						&& castOther.getDtIniVld() != null && this
						.getDtIniVld().equals(castOther.getDtIniVld())))
				&& ((this.getDtFinVld() == castOther.getDtFinVld()) || (this
						.getDtFinVld() != null
						&& castOther.getDtFinVld() != null && this
						.getDtFinVld().equals(castOther.getDtFinVld())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null && castOther.getEmail() != null && this
						.getEmail().equals(castOther.getEmail())))
				&& ((this.getIndicepaCodAmm() == castOther.getIndicepaCodAmm()) || (this
						.getIndicepaCodAmm() != null
						&& castOther.getIndicepaCodAmm() != null && this
						.getIndicepaCodAmm().equals(
								castOther.getIndicepaCodAmm())))
				&& ((this.getIndicepaCodAoo() == castOther.getIndicepaCodAoo()) || (this
						.getIndicepaCodAoo() != null
						&& castOther.getIndicepaCodAoo() != null && this
						.getIndicepaCodAoo().equals(
								castOther.getIndicepaCodAoo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdDoc();
		result = 37 * result + this.getNroPosiz();
		result = 37 * result
				+ (getIdUo() == null ? 0 : this.getIdUo().hashCode());
		result = 37 * result
				+ (getIdAnag() == null ? 0 : this.getIdAnag().hashCode());
		result = 37 * result
				+ (getIdIndice() == null ? 0 : this.getIdIndice().hashCode());
		result = 37
				* result
				+ (getIdFascicolo() == null ? 0 : this.getIdFascicolo()
						.hashCode());
		result = 37
				* result
				+ (getNumSottofasc() == null ? 0 : this.getNumSottofasc()
						.hashCode());
		result = 37 * result
				+ (getFlgCc() == null ? 0 : this.getFlgCc().hashCode());
		result = 37 * result
				+ (getDtIns() == null ? 0 : this.getDtIns().hashCode());
		result = 37 * result
				+ (getUteIns() == null ? 0 : this.getUteIns().hashCode());
		result = 37 * result
				+ (getDtUltMod() == null ? 0 : this.getDtUltMod().hashCode());
		result = 37 * result
				+ (getUteUltMod() == null ? 0 : this.getUteUltMod().hashCode());
		result = 37 * result
				+ (getIdTpFis() == null ? 0 : this.getIdTpFis().hashCode());
		result = 37 * result
				+ (getDesAnag() == null ? 0 : this.getDesAnag().hashCode());
		result = 37 * result
				+ (getFlgTpAnag() == null ? 0 : this.getFlgTpAnag().hashCode());
		result = 37 * result
				+ (getCodFis() == null ? 0 : this.getCodFis().hashCode());
		result = 37 * result
				+ (getParIva() == null ? 0 : this.getParIva().hashCode());
		result = 37 * result
				+ (getMatAnag() == null ? 0 : this.getMatAnag().hashCode());
		result = 37 * result
				+ (getDtIniVld() == null ? 0 : this.getDtIniVld().hashCode());
		result = 37 * result
				+ (getDtFinVld() == null ? 0 : this.getDtFinVld().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37
				* result
				+ (getIndicepaCodAmm() == null ? 0 : this.getIndicepaCodAmm()
						.hashCode());
		result = 37
				* result
				+ (getIndicepaCodAoo() == null ? 0 : this.getIndicepaCodAoo()
						.hashCode());
		return result;
	}

}