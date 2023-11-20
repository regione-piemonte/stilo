/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Generated 21-nov-2019 12.01.08 by Hibernate Tools 5.1.7.Final

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_CODA_COPY_FILE")
public class TCodaCopyFile implements java.io.Serializable {

	private BigDecimal idOggetto;
	private String tipoOggetto;
	private String fromExpPath;
	private String toExpPath;
	private String expFilename;
	private Timestamp tsLastTry;
	private BigDecimal numTry;
	private BigDecimal errCode;
	private Clob errMsg;
	private String esitoElab;

	public TCodaCopyFile() {
	}

	

	@Id

	@Column(name = "ID_OGGETTO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdOggetto() {
		return this.idOggetto;
	}

	public void setIdOggetto(BigDecimal idOggetto) {
		this.idOggetto = idOggetto;
	}

	@Column(name = "TIPO_OGGETTO", nullable = false, length = 30)
	public String getTipoOggetto() {
		return this.tipoOggetto;
	}

	public void setTipoOggetto(String tipoOggetto) {
		this.tipoOggetto = tipoOggetto;
	}
	
	@Column(name = "FROM_EXP_PATH", nullable = false, length = 1000)
	public String getFromExpPath() {
		return fromExpPath;
	}
    
	public void setFromExpPath(String fromExpPath) {
		this.fromExpPath = fromExpPath;
	}


	@Column(name = "TO_EXP_PATH", nullable = false, length = 1000)
	public String getToExpPath() {
		return toExpPath;
	}



	public void setToExpPath(String toExpPath) {
		this.toExpPath = toExpPath;
	}
	
    
	
	@Column(name = "EXP_FILENAME", length = 1000)
	public String getExpFilename() {
		return this.expFilename;
	}
    
	public void setExpFilename(String expFilename) {
		this.expFilename = expFilename;
	}

	@Column(name = "TS_LAST_TRY")
	public Timestamp getTsLastTry() {
		return this.tsLastTry;
	}

	public void setTsLastTry(Timestamp tsLastTry) {
		this.tsLastTry = tsLastTry;
	}

	@Column(name = "NUM_TRY", nullable = false, precision = 22, scale = 0)
	public BigDecimal getNumTry() {
		return this.numTry;
	}

	public void setNumTry(BigDecimal numTry) {
		this.numTry = numTry;
	}

	@Column(name = "ERR_CODE", precision = 22, scale = 0)
	public BigDecimal getErrCode() {
		return this.errCode;
	}

	public void setErrCode(BigDecimal errCode) {
		this.errCode = errCode;
	}

	@Column(name = "ERR_MSG")
	public Clob getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(Clob errMsg) {
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
		return "TCodaCopyFile [idOggetto=" + idOggetto + ", tipoOggetto=" + tipoOggetto + ", fromExpPath=" + fromExpPath
				+ ", toExpPath=" + toExpPath + ", expFilename=" + expFilename + ", tsLastTry=" + tsLastTry + ", numTry="
				+ numTry + ", errCode=" + errCode + ", errMsg=" + errMsg + ", esitoElab=" + esitoElab + "]";
	}

	
	
}
