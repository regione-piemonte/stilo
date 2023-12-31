/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PtvNomdocId generated by hbm2java
 */
@Embeddable
public class PtvNomdocId implements java.io.Serializable {

	private int idDoc;
	private Integer idAnag;
	private String flgEsibDest;
	private String flgAltriNom;
	private Integer idTopon;
	private Integer numCiv;
	private String espCiv;
	private String desInd;
	private Integer idCom;
	private String desCom;
	private Integer cap;
	private Date dtInsNomdoc;
	private Integer uteInsNomdoc;
	private Date dtUltModNomdoc;
	private Integer uteUltModNomdoc;
	private String flgDestCopia;
	private Short idTpFis;
	private Date dtRaccomandata;
	private String nroRaccomandata;
	private BigDecimal costoSped;
	private String flgNovisStampe;
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

	public PtvNomdocId() {
	}

	public PtvNomdocId(int idDoc) {
		this.idDoc = idDoc;
	}

	public PtvNomdocId(int idDoc, Integer idAnag, String flgEsibDest,
			String flgAltriNom, Integer idTopon, Integer numCiv, String espCiv,
			String desInd, Integer idCom, String desCom, Integer cap,
			Date dtInsNomdoc, Integer uteInsNomdoc, Date dtUltModNomdoc,
			Integer uteUltModNomdoc, String flgDestCopia, Short idTpFis,
			Date dtRaccomandata, String nroRaccomandata, BigDecimal costoSped,
			String flgNovisStampe, String desAnag, String flgTpAnag,
			String codFis, String parIva, Integer matAnag, Date dtIniVld,
			Date dtFinVld, String email, String indicepaCodAmm,
			String indicepaCodAoo) {
		this.idDoc = idDoc;
		this.idAnag = idAnag;
		this.flgEsibDest = flgEsibDest;
		this.flgAltriNom = flgAltriNom;
		this.idTopon = idTopon;
		this.numCiv = numCiv;
		this.espCiv = espCiv;
		this.desInd = desInd;
		this.idCom = idCom;
		this.desCom = desCom;
		this.cap = cap;
		this.dtInsNomdoc = dtInsNomdoc;
		this.uteInsNomdoc = uteInsNomdoc;
		this.dtUltModNomdoc = dtUltModNomdoc;
		this.uteUltModNomdoc = uteUltModNomdoc;
		this.flgDestCopia = flgDestCopia;
		this.idTpFis = idTpFis;
		this.dtRaccomandata = dtRaccomandata;
		this.nroRaccomandata = nroRaccomandata;
		this.costoSped = costoSped;
		this.flgNovisStampe = flgNovisStampe;
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

	@Column(name = "ID_ANAG", precision = 9, scale = 0)
	public Integer getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}

	@Column(name = "FLG_ESIB_DEST", length = 1)
	public String getFlgEsibDest() {
		return this.flgEsibDest;
	}

	public void setFlgEsibDest(String flgEsibDest) {
		this.flgEsibDest = flgEsibDest;
	}

	@Column(name = "FLG_ALTRI_NOM", length = 1)
	public String getFlgAltriNom() {
		return this.flgAltriNom;
	}

	public void setFlgAltriNom(String flgAltriNom) {
		this.flgAltriNom = flgAltriNom;
	}

	@Column(name = "ID_TOPON", precision = 9, scale = 0)
	public Integer getIdTopon() {
		return this.idTopon;
	}

	public void setIdTopon(Integer idTopon) {
		this.idTopon = idTopon;
	}

	@Column(name = "NUM_CIV", precision = 5, scale = 0)
	public Integer getNumCiv() {
		return this.numCiv;
	}

	public void setNumCiv(Integer numCiv) {
		this.numCiv = numCiv;
	}

	@Column(name = "ESP_CIV", length = 12)
	public String getEspCiv() {
		return this.espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
	}

	@Column(name = "DES_IND", length = 60)
	public String getDesInd() {
		return this.desInd;
	}

	public void setDesInd(String desInd) {
		this.desInd = desInd;
	}

	@Column(name = "ID_COM", precision = 6, scale = 0)
	public Integer getIdCom() {
		return this.idCom;
	}

	public void setIdCom(Integer idCom) {
		this.idCom = idCom;
	}

	@Column(name = "DES_COM", length = 60)
	public String getDesCom() {
		return this.desCom;
	}

	public void setDesCom(String desCom) {
		this.desCom = desCom;
	}

	@Column(name = "CAP", precision = 5, scale = 0)
	public Integer getCap() {
		return this.cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	@Column(name = "DT_INS_NOMDOC", length = 7)
	public Date getDtInsNomdoc() {
		return this.dtInsNomdoc;
	}

	public void setDtInsNomdoc(Date dtInsNomdoc) {
		this.dtInsNomdoc = dtInsNomdoc;
	}

	@Column(name = "UTE_INS_NOMDOC", precision = 6, scale = 0)
	public Integer getUteInsNomdoc() {
		return this.uteInsNomdoc;
	}

	public void setUteInsNomdoc(Integer uteInsNomdoc) {
		this.uteInsNomdoc = uteInsNomdoc;
	}

	@Column(name = "DT_ULT_MOD_NOMDOC", length = 7)
	public Date getDtUltModNomdoc() {
		return this.dtUltModNomdoc;
	}

	public void setDtUltModNomdoc(Date dtUltModNomdoc) {
		this.dtUltModNomdoc = dtUltModNomdoc;
	}

	@Column(name = "UTE_ULT_MOD_NOMDOC", precision = 6, scale = 0)
	public Integer getUteUltModNomdoc() {
		return this.uteUltModNomdoc;
	}

	public void setUteUltModNomdoc(Integer uteUltModNomdoc) {
		this.uteUltModNomdoc = uteUltModNomdoc;
	}

	@Column(name = "FLG_DEST_COPIA", length = 1)
	public String getFlgDestCopia() {
		return this.flgDestCopia;
	}

	public void setFlgDestCopia(String flgDestCopia) {
		this.flgDestCopia = flgDestCopia;
	}

	@Column(name = "ID_TP_FIS", precision = 4, scale = 0)
	public Short getIdTpFis() {
		return this.idTpFis;
	}

	public void setIdTpFis(Short idTpFis) {
		this.idTpFis = idTpFis;
	}

	@Column(name = "DT_RACCOMANDATA", length = 7)
	public Date getDtRaccomandata() {
		return this.dtRaccomandata;
	}

	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}

	@Column(name = "NRO_RACCOMANDATA", length = 30)
	public String getNroRaccomandata() {
		return this.nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	@Column(name = "COSTO_SPED", precision = 11)
	public BigDecimal getCostoSped() {
		return this.costoSped;
	}

	public void setCostoSped(BigDecimal costoSped) {
		this.costoSped = costoSped;
	}

	@Column(name = "FLG_NOVIS_STAMPE", length = 1)
	public String getFlgNovisStampe() {
		return this.flgNovisStampe;
	}

	public void setFlgNovisStampe(String flgNovisStampe) {
		this.flgNovisStampe = flgNovisStampe;
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
		if (!(other instanceof PtvNomdocId))
			return false;
		PtvNomdocId castOther = (PtvNomdocId) other;

		return (this.getIdDoc() == castOther.getIdDoc())
				&& ((this.getIdAnag() == castOther.getIdAnag()) || (this
						.getIdAnag() != null && castOther.getIdAnag() != null && this
						.getIdAnag().equals(castOther.getIdAnag())))
				&& ((this.getFlgEsibDest() == castOther.getFlgEsibDest()) || (this
						.getFlgEsibDest() != null
						&& castOther.getFlgEsibDest() != null && this
						.getFlgEsibDest().equals(castOther.getFlgEsibDest())))
				&& ((this.getFlgAltriNom() == castOther.getFlgAltriNom()) || (this
						.getFlgAltriNom() != null
						&& castOther.getFlgAltriNom() != null && this
						.getFlgAltriNom().equals(castOther.getFlgAltriNom())))
				&& ((this.getIdTopon() == castOther.getIdTopon()) || (this
						.getIdTopon() != null && castOther.getIdTopon() != null && this
						.getIdTopon().equals(castOther.getIdTopon())))
				&& ((this.getNumCiv() == castOther.getNumCiv()) || (this
						.getNumCiv() != null && castOther.getNumCiv() != null && this
						.getNumCiv().equals(castOther.getNumCiv())))
				&& ((this.getEspCiv() == castOther.getEspCiv()) || (this
						.getEspCiv() != null && castOther.getEspCiv() != null && this
						.getEspCiv().equals(castOther.getEspCiv())))
				&& ((this.getDesInd() == castOther.getDesInd()) || (this
						.getDesInd() != null && castOther.getDesInd() != null && this
						.getDesInd().equals(castOther.getDesInd())))
				&& ((this.getIdCom() == castOther.getIdCom()) || (this
						.getIdCom() != null && castOther.getIdCom() != null && this
						.getIdCom().equals(castOther.getIdCom())))
				&& ((this.getDesCom() == castOther.getDesCom()) || (this
						.getDesCom() != null && castOther.getDesCom() != null && this
						.getDesCom().equals(castOther.getDesCom())))
				&& ((this.getCap() == castOther.getCap()) || (this.getCap() != null
						&& castOther.getCap() != null && this.getCap().equals(
						castOther.getCap())))
				&& ((this.getDtInsNomdoc() == castOther.getDtInsNomdoc()) || (this
						.getDtInsNomdoc() != null
						&& castOther.getDtInsNomdoc() != null && this
						.getDtInsNomdoc().equals(castOther.getDtInsNomdoc())))
				&& ((this.getUteInsNomdoc() == castOther.getUteInsNomdoc()) || (this
						.getUteInsNomdoc() != null
						&& castOther.getUteInsNomdoc() != null && this
						.getUteInsNomdoc().equals(castOther.getUteInsNomdoc())))
				&& ((this.getDtUltModNomdoc() == castOther.getDtUltModNomdoc()) || (this
						.getDtUltModNomdoc() != null
						&& castOther.getDtUltModNomdoc() != null && this
						.getDtUltModNomdoc().equals(
								castOther.getDtUltModNomdoc())))
				&& ((this.getUteUltModNomdoc() == castOther
						.getUteUltModNomdoc()) || (this.getUteUltModNomdoc() != null
						&& castOther.getUteUltModNomdoc() != null && this
						.getUteUltModNomdoc().equals(
								castOther.getUteUltModNomdoc())))
				&& ((this.getFlgDestCopia() == castOther.getFlgDestCopia()) || (this
						.getFlgDestCopia() != null
						&& castOther.getFlgDestCopia() != null && this
						.getFlgDestCopia().equals(castOther.getFlgDestCopia())))
				&& ((this.getIdTpFis() == castOther.getIdTpFis()) || (this
						.getIdTpFis() != null && castOther.getIdTpFis() != null && this
						.getIdTpFis().equals(castOther.getIdTpFis())))
				&& ((this.getDtRaccomandata() == castOther.getDtRaccomandata()) || (this
						.getDtRaccomandata() != null
						&& castOther.getDtRaccomandata() != null && this
						.getDtRaccomandata().equals(
								castOther.getDtRaccomandata())))
				&& ((this.getNroRaccomandata() == castOther
						.getNroRaccomandata()) || (this.getNroRaccomandata() != null
						&& castOther.getNroRaccomandata() != null && this
						.getNroRaccomandata().equals(
								castOther.getNroRaccomandata())))
				&& ((this.getCostoSped() == castOther.getCostoSped()) || (this
						.getCostoSped() != null
						&& castOther.getCostoSped() != null && this
						.getCostoSped().equals(castOther.getCostoSped())))
				&& ((this.getFlgNovisStampe() == castOther.getFlgNovisStampe()) || (this
						.getFlgNovisStampe() != null
						&& castOther.getFlgNovisStampe() != null && this
						.getFlgNovisStampe().equals(
								castOther.getFlgNovisStampe())))
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
		result = 37 * result
				+ (getIdAnag() == null ? 0 : this.getIdAnag().hashCode());
		result = 37
				* result
				+ (getFlgEsibDest() == null ? 0 : this.getFlgEsibDest()
						.hashCode());
		result = 37
				* result
				+ (getFlgAltriNom() == null ? 0 : this.getFlgAltriNom()
						.hashCode());
		result = 37 * result
				+ (getIdTopon() == null ? 0 : this.getIdTopon().hashCode());
		result = 37 * result
				+ (getNumCiv() == null ? 0 : this.getNumCiv().hashCode());
		result = 37 * result
				+ (getEspCiv() == null ? 0 : this.getEspCiv().hashCode());
		result = 37 * result
				+ (getDesInd() == null ? 0 : this.getDesInd().hashCode());
		result = 37 * result
				+ (getIdCom() == null ? 0 : this.getIdCom().hashCode());
		result = 37 * result
				+ (getDesCom() == null ? 0 : this.getDesCom().hashCode());
		result = 37 * result
				+ (getCap() == null ? 0 : this.getCap().hashCode());
		result = 37
				* result
				+ (getDtInsNomdoc() == null ? 0 : this.getDtInsNomdoc()
						.hashCode());
		result = 37
				* result
				+ (getUteInsNomdoc() == null ? 0 : this.getUteInsNomdoc()
						.hashCode());
		result = 37
				* result
				+ (getDtUltModNomdoc() == null ? 0 : this.getDtUltModNomdoc()
						.hashCode());
		result = 37
				* result
				+ (getUteUltModNomdoc() == null ? 0 : this.getUteUltModNomdoc()
						.hashCode());
		result = 37
				* result
				+ (getFlgDestCopia() == null ? 0 : this.getFlgDestCopia()
						.hashCode());
		result = 37 * result
				+ (getIdTpFis() == null ? 0 : this.getIdTpFis().hashCode());
		result = 37
				* result
				+ (getDtRaccomandata() == null ? 0 : this.getDtRaccomandata()
						.hashCode());
		result = 37
				* result
				+ (getNroRaccomandata() == null ? 0 : this.getNroRaccomandata()
						.hashCode());
		result = 37 * result
				+ (getCostoSped() == null ? 0 : this.getCostoSped().hashCode());
		result = 37
				* result
				+ (getFlgNovisStampe() == null ? 0 : this.getFlgNovisStampe()
						.hashCode());
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
