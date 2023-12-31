/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PttNomdocId generated by hbm2java
 */
@Embeddable
public class PttNomdocId implements java.io.Serializable {

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
	private Date dtIns;
	private Integer uteIns;
	private Date dtUltMod;
	private Integer uteUltMod;
	private String flgDestCopia;
	private Short idTpFis;
	private Date dtRaccomandata;
	private String nroRaccomandata;
	private BigDecimal costoSped;
	private String flgNovisStampe;
	private String desAnag;
	private Date dtLettera;
	private String nroLettera;
	private String idRichPoste;
	private Date dtTelegramma;

	public PttNomdocId() {
	}

	public PttNomdocId(int idDoc) {
		this.idDoc = idDoc;
	}

	public PttNomdocId(int idDoc, Integer idAnag, String flgEsibDest,
			String flgAltriNom, Integer idTopon, Integer numCiv, String espCiv,
			String desInd, Integer idCom, String desCom, Integer cap,
			Date dtIns, Integer uteIns, Date dtUltMod, Integer uteUltMod,
			String flgDestCopia, Short idTpFis, Date dtRaccomandata,
			String nroRaccomandata, BigDecimal costoSped,
			String flgNovisStampe, String desAnag, Date dtLettera,
			String nroLettera, String idRichPoste, Date dtTelegramma) {
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
		this.dtIns = dtIns;
		this.uteIns = uteIns;
		this.dtUltMod = dtUltMod;
		this.uteUltMod = uteUltMod;
		this.flgDestCopia = flgDestCopia;
		this.idTpFis = idTpFis;
		this.dtRaccomandata = dtRaccomandata;
		this.nroRaccomandata = nroRaccomandata;
		this.costoSped = costoSped;
		this.flgNovisStampe = flgNovisStampe;
		this.desAnag = desAnag;
		this.dtLettera = dtLettera;
		this.nroLettera = nroLettera;
		this.idRichPoste = idRichPoste;
		this.dtTelegramma = dtTelegramma;
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

	@Column(name = "DES_ANAG", length = 150)
	public String getDesAnag() {
		return this.desAnag;
	}

	public void setDesAnag(String desAnag) {
		this.desAnag = desAnag;
	}

	@Column(name = "DT_LETTERA", length = 7)
	public Date getDtLettera() {
		return this.dtLettera;
	}

	public void setDtLettera(Date dtLettera) {
		this.dtLettera = dtLettera;
	}

	@Column(name = "NRO_LETTERA", length = 250)
	public String getNroLettera() {
		return this.nroLettera;
	}

	public void setNroLettera(String nroLettera) {
		this.nroLettera = nroLettera;
	}

	@Column(name = "ID_RICH_POSTE", length = 64)
	public String getIdRichPoste() {
		return this.idRichPoste;
	}

	public void setIdRichPoste(String idRichPoste) {
		this.idRichPoste = idRichPoste;
	}

	@Column(name = "DT_TELEGRAMMA", length = 7)
	public Date getDtTelegramma() {
		return this.dtTelegramma;
	}

	public void setDtTelegramma(Date dtTelegramma) {
		this.dtTelegramma = dtTelegramma;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PttNomdocId))
			return false;
		PttNomdocId castOther = (PttNomdocId) other;

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
				&& ((this.getDtLettera() == castOther.getDtLettera()) || (this
						.getDtLettera() != null
						&& castOther.getDtLettera() != null && this
						.getDtLettera().equals(castOther.getDtLettera())))
				&& ((this.getNroLettera() == castOther.getNroLettera()) || (this
						.getNroLettera() != null
						&& castOther.getNroLettera() != null && this
						.getNroLettera().equals(castOther.getNroLettera())))
				&& ((this.getIdRichPoste() == castOther.getIdRichPoste()) || (this
						.getIdRichPoste() != null
						&& castOther.getIdRichPoste() != null && this
						.getIdRichPoste().equals(castOther.getIdRichPoste())))
				&& ((this.getDtTelegramma() == castOther.getDtTelegramma()) || (this
						.getDtTelegramma() != null
						&& castOther.getDtTelegramma() != null && this
						.getDtTelegramma().equals(castOther.getDtTelegramma())));
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
		result = 37 * result
				+ (getDtIns() == null ? 0 : this.getDtIns().hashCode());
		result = 37 * result
				+ (getUteIns() == null ? 0 : this.getUteIns().hashCode());
		result = 37 * result
				+ (getDtUltMod() == null ? 0 : this.getDtUltMod().hashCode());
		result = 37 * result
				+ (getUteUltMod() == null ? 0 : this.getUteUltMod().hashCode());
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
				+ (getDtLettera() == null ? 0 : this.getDtLettera().hashCode());
		result = 37
				* result
				+ (getNroLettera() == null ? 0 : this.getNroLettera()
						.hashCode());
		result = 37
				* result
				+ (getIdRichPoste() == null ? 0 : this.getIdRichPoste()
						.hashCode());
		result = 37
				* result
				+ (getDtTelegramma() == null ? 0 : this.getDtTelegramma()
						.hashCode());
		return result;
	}

}
