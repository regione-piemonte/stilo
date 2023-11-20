/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class FoglioImportatoFilterXmlBean {
	
	@XmlVariabile(nome="NomeFoglio", tipo=TipoVariabile.SEMPLICE)
	private String nomeFoglio;
	
	@XmlVariabile(nome="CodApplicazioni", tipo=TipoVariabile.SEMPLICE)
	private String codApplicazioni;
	
	@XmlVariabile(nome="Stati", tipo=TipoVariabile.SEMPLICE)
	private String stati;
	
	@XmlVariabile(nome="TipiContenuto", tipo=TipoVariabile.SEMPLICE)
	private String tipiContenuto;
	
	@XmlVariabile(nome="TsUploadDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsUploadDa;
	
	@XmlVariabile(nome="TsUploadA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsUploadA;
	
	@XmlVariabile(nome="TsInizioElabDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsInizioElabDa;
	
	@XmlVariabile(nome="TsInizioElabA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsInizioElabA;
	
	@XmlVariabile(nome="TsFineElabDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsFineElabDa;
	
	@XmlVariabile(nome="TsFineElabA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsFineElabA;
	
	@XmlVariabile(nome="IdUserUpload", tipo=TipoVariabile.SEMPLICE)
	private String idUserUpload;

	public String getNomeFoglio() {
		return nomeFoglio;
	}

	public String getCodApplicazioni() {
		return codApplicazioni;
	}

	public String getStati() {
		return stati;
	}

	public String getTipiContenuto() {
		return tipiContenuto;
	}

	public Date getTsUploadDa() {
		return tsUploadDa;
	}

	public Date getTsUploadA() {
		return tsUploadA;
	}

	public Date getTsInizioElabDa() {
		return tsInizioElabDa;
	}

	public Date getTsInizioElabA() {
		return tsInizioElabA;
	}

	public Date getTsFineElabDa() {
		return tsFineElabDa;
	}

	public Date getTsFineElabA() {
		return tsFineElabA;
	}

	public String getIdUserUpload() {
		return idUserUpload;
	}

	public void setNomeFoglio(String nomeFoglio) {
		this.nomeFoglio = nomeFoglio;
	}

	public void setCodApplicazioni(String codApplicazioni) {
		this.codApplicazioni = codApplicazioni;
	}

	public void setStati(String stati) {
		this.stati = stati;
	}

	public void setTipiContenuto(String tipiContenuto) {
		this.tipiContenuto = tipiContenuto;
	}

	public void setTsUploadDa(Date tsUploadDa) {
		this.tsUploadDa = tsUploadDa;
	}

	public void setTsUploadA(Date tsUploadA) {
		this.tsUploadA = tsUploadA;
	}

	public void setTsInizioElabDa(Date tsInizioElabDa) {
		this.tsInizioElabDa = tsInizioElabDa;
	}

	public void setTsInizioElabA(Date tsInizioElabA) {
		this.tsInizioElabA = tsInizioElabA;
	}

	public void setTsFineElabDa(Date tsFineElabDa) {
		this.tsFineElabDa = tsFineElabDa;
	}

	public void setTsFineElabA(Date tsFineElabA) {
		this.tsFineElabA = tsFineElabA;
	}

	public void setIdUserUpload(String idUserUpload) {
		this.idUserUpload = idUserUpload;
	}

	
}