/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraTestataImpegno implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parte;
	private String azione;
	private Long codice;
	private BigInteger codAnnuale;
	private Integer anno;
	private Calendar dataRegistrazione;
	private String descrizione;
	private Long idImpegno;
	private BigInteger numAtto;
	private Calendar dataAtto;
	private Integer annoAtto;
	private String tipoAtto;
	private Boolean esecutivo;
	private Boolean autoincrementante;
	private Long idPrenotazionePartenza;
	private String descrizione2;
	private String descrizione3;
	private String descrizioneEstesa;
	private String descrizioneEstesa2;
	private String descrizioneEstesa3;
	private String codSettore;
	private Long codTipoAtto;
	private String siglaTipoAtto;
	private Boolean flagNoDodicesimiImp;
	private BigInteger numAttoOri;
	private Calendar dataAttoOri;
	private Integer annoAttoOri;
	private String tipoAttoOri;
	private String codSettoreOri;
	private Long codTipoAttoOri;
	private String siglaTipoAttoOri;

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getAzione() {
		return azione;
	}

	public void setAzione(String azione) {
		this.azione = azione;
	}

	public Long getCodice() {
		return codice;
	}

	public void setCodice(Long codice) {
		this.codice = codice;
	}

	public BigInteger getCodAnnuale() {
		return codAnnuale;
	}

	public void setCodAnnuale(BigInteger codAnnuale) {
		this.codAnnuale = codAnnuale;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Calendar getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Calendar dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getIdImpegno() {
		return idImpegno;
	}

	public void setIdImpegno(Long idImpegno) {
		this.idImpegno = idImpegno;
	}

	public BigInteger getNumAtto() {
		return numAtto;
	}

	public void setNumAtto(BigInteger numAtto) {
		this.numAtto = numAtto;
	}

	public Calendar getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Calendar dataAtto) {
		this.dataAtto = dataAtto;
	}

	public Integer getAnnoAtto() {
		return annoAtto;
	}

	public void setAnnoAtto(Integer annoAtto) {
		this.annoAtto = annoAtto;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public Boolean getEsecutivo() {
		return esecutivo;
	}

	public void setEsecutivo(Boolean esecutivo) {
		this.esecutivo = esecutivo;
	}

	public Boolean getAutoincrementante() {
		return autoincrementante;
	}

	public void setAutoincrementante(Boolean autoincrementante) {
		this.autoincrementante = autoincrementante;
	}

	public Long getIdPrenotazionePartenza() {
		return idPrenotazionePartenza;
	}

	public void setIdPrenotazionePartenza(Long idPrenotazionePartenza) {
		this.idPrenotazionePartenza = idPrenotazionePartenza;
	}

	public String getDescrizione2() {
		return descrizione2;
	}

	public void setDescrizione2(String descrizione2) {
		this.descrizione2 = descrizione2;
	}

	public String getDescrizione3() {
		return descrizione3;
	}

	public void setDescrizione3(String descrizione3) {
		this.descrizione3 = descrizione3;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getDescrizioneEstesa2() {
		return descrizioneEstesa2;
	}

	public void setDescrizioneEstesa2(String descrizioneEstesa2) {
		this.descrizioneEstesa2 = descrizioneEstesa2;
	}

	public String getDescrizioneEstesa3() {
		return descrizioneEstesa3;
	}

	public void setDescrizioneEstesa3(String descrizioneEstesa3) {
		this.descrizioneEstesa3 = descrizioneEstesa3;
	}

	public String getCodSettore() {
		return codSettore;
	}

	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
	}

	public Long getCodTipoAtto() {
		return codTipoAtto;
	}

	public void setCodTipoAtto(Long codTipoAtto) {
		this.codTipoAtto = codTipoAtto;
	}

	public String getSiglaTipoAtto() {
		return siglaTipoAtto;
	}

	public void setSiglaTipoAtto(String siglaTipoAtto) {
		this.siglaTipoAtto = siglaTipoAtto;
	}

	public Boolean getFlagNoDodicesimiImp() {
		return flagNoDodicesimiImp;
	}

	public void setFlagNoDodicesimiImp(Boolean flagNoDodicesimiImp) {
		this.flagNoDodicesimiImp = flagNoDodicesimiImp;
	}

	public BigInteger getNumAttoOri() {
		return numAttoOri;
	}

	public void setNumAttoOri(BigInteger numAttoOri) {
		this.numAttoOri = numAttoOri;
	}

	public Calendar getDataAttoOri() {
		return dataAttoOri;
	}

	public void setDataAttoOri(Calendar dataAttoOri) {
		this.dataAttoOri = dataAttoOri;
	}

	public Integer getAnnoAttoOri() {
		return annoAttoOri;
	}

	public void setAnnoAttoOri(Integer annoAttoOri) {
		this.annoAttoOri = annoAttoOri;
	}

	public String getTipoAttoOri() {
		return tipoAttoOri;
	}

	public void setTipoAttoOri(String tipoAttoOri) {
		this.tipoAttoOri = tipoAttoOri;
	}

	public String getCodSettoreOri() {
		return codSettoreOri;
	}

	public void setCodSettoreOri(String codSettoreOri) {
		this.codSettoreOri = codSettoreOri;
	}

	public Long getCodTipoAttoOri() {
		return codTipoAttoOri;
	}

	public void setCodTipoAttoOri(Long codTipoAttoOri) {
		this.codTipoAttoOri = codTipoAttoOri;
	}

	public String getSiglaTipoAttoOri() {
		return siglaTipoAttoOri;
	}

	public void setSiglaTipoAttoOri(String siglaTipoAttoOri) {
		this.siglaTipoAttoOri = siglaTipoAttoOri;
	}

}// SicraTestataImpegno
