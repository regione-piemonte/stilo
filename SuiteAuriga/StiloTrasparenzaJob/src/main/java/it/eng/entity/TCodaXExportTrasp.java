/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CODA_X_EXPORT_TRASP")
public class TCodaXExportTrasp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idOggetto;
	private String fruitore;
	private String tipoOggetto;
	private String oggDaEsportare;
	private BigDecimal dipendeDaExpOgg;
	private String expPath;
	private String expFilename;
	private Date tsLastTry;
	private BigDecimal numTry;
	private BigDecimal errCode;
	private String errMsg;
	private String esitoElab;
	
	public TCodaXExportTrasp() {
		
	}
	
	public TCodaXExportTrasp(BigDecimal idOggetto, String fruitore, String tipoOggetto, 
							 String oggDaEsportare, String expPath, BigDecimal numTry) {
		
		this.idOggetto = idOggetto;
		this.fruitore = fruitore;
		this.tipoOggetto = tipoOggetto;
		this.oggDaEsportare = oggDaEsportare;
		this.expPath = expPath;
		this.numTry = numTry;
	}
	
	public TCodaXExportTrasp(BigDecimal idOggetto, String fruitore, String tipoOggetto, 
			 String oggDaEsportare, BigDecimal dipendeDaExpOgg, String expPath, 
			 String expFilename, Date tsLastTry, BigDecimal numTry, BigDecimal errCode, 
			 String errMsg, String esitoElab) {
		
		this.idOggetto = idOggetto;
		this.fruitore = fruitore;
		this.tipoOggetto = tipoOggetto;
		this.oggDaEsportare = oggDaEsportare;
		this.dipendeDaExpOgg = dipendeDaExpOgg;
		this.expPath = expPath;
		this.expFilename = expFilename;
		this.tsLastTry = tsLastTry;
		this.numTry = numTry;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.esitoElab = esitoElab;
	}
	@Id
	@Column(name = "ID_OGGETTO", nullable = false, precision = 38, scale = 0)
	public BigDecimal getIdOggetto() {
		return this.idOggetto;
	}

	public void setIdOggetto(BigDecimal idOggetto) {
		this.idOggetto = idOggetto;
	}
	
	@Column(name = "FRUITORE", nullable = false, length = 30)
	public String getFruitore() {
		return this.fruitore;
	}

	public void setFruitore(String fruitore) {
		this.fruitore = fruitore;
	}
	
	@Column(name = "TIPO_OGGETTO", nullable = false, length = 30)
	public String getTipoOggetto() {
		return this.tipoOggetto;
	}

	public void setTipoOggetto(String tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
	
	@Column(name = "OGG_DA_ESPORTARE", nullable = false)
	public String getOggDaEsportare() {
		return this.oggDaEsportare;
	}

	public void setOggDaEsportare(String oggDaEsportare) {
		this.oggDaEsportare = oggDaEsportare;
	}
	
	@Column(name = "DIPENDE_DA_EXP_OGG", precision = 38, scale = 0)
	public BigDecimal getDipendeDaExpOgg() {
		return this.dipendeDaExpOgg;
	}

	public void setDipendeDaExpOgg(BigDecimal dipendeDaExpOgg) {
		this.dipendeDaExpOgg = dipendeDaExpOgg;
	}
	
	@Column(name = "EXP_PATH", nullable = false, length = 1000)
	public String getExpPath() {
		return this.expPath;
	}

	public void setExpPath(String expPath) {
		this.expPath = expPath;
	}
	
	@Column(name = "EXP_FILENAME", length = 1000)
	public String getExpFilename() {
		return this.expFilename;
	}

	public void setExpFilename(String expFilename) {
		this.expFilename = expFilename;
	}
	
	@Column(name = "TS_LAST_TRY")
	public Date getTsLastTry() {
		return this.tsLastTry;
	}

	public void setTsLastTry(Date tsLastTry) {
		this.tsLastTry = tsLastTry;
	}
	
	@Column(name = "NUM_TRY", nullable = false, precision = 38, scale = 0)
	public BigDecimal getNumTry() {
		return this.numTry;
	}

	public void setNumTry(BigDecimal numTry) {
		this.numTry = numTry;
	}

	@Column(name = "ERR_CODE", precision = 38, scale = 0)
	public BigDecimal getErrCode() {
		return this.errCode;
	}

	public void setErrCode(BigDecimal errCode) {
		this.errCode = errCode;
	}
	
	@Column(name = "ERR_MSG")
	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@Column(name = "ESITO_ELAB", length = 2)
	public String getEsitoElab() {
		return this.esitoElab;
	}

	public void setEsitoElab(String esitoElab) {
		this.esitoElab = esitoElab;
	}

	@Override
	public String toString() {
		return "TCodaXExportTrasp [idOggetto=" + idOggetto + ", fruitore=" + fruitore + ", tipoOggetto=" + tipoOggetto
				+ ", oggDaEsportare=" + oggDaEsportare + ", dipendeDaExpOgg=" + dipendeDaExpOgg + ", expPath=" + expPath
				+ ", expFilename=" + expFilename + ", tsLastTry=" + tsLastTry + ", numTry=" + numTry + ", errCode="
				+ errCode + ", errMsg=" + errMsg + ", esitoElab=" + esitoElab + "]";
	}
	
}
