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

public class ContenutoFoglioImportatoFilterXmlBean {
	
	@XmlVariabile(nome="IdFogli", tipo=TipoVariabile.SEMPLICE)
	private String idFogli;
	
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
	
	@XmlVariabile(nome="NroRigaDa", tipo=TipoVariabile.SEMPLICE)
	private String nroRigaDa;
	
	@XmlVariabile(nome="NroRigaA", tipo=TipoVariabile.SEMPLICE)
	private String nroRigaA;
	
	@XmlVariabile(nome="EsitoElaborazione", tipo=TipoVariabile.SEMPLICE)
	private String esitoElaborazione;
	
	@XmlVariabile(nome="MessaggioErrore", tipo=TipoVariabile.SEMPLICE)
	private String messaggioErrore;
	
	@XmlVariabile(nome="CodiciErrore", tipo=TipoVariabile.SEMPLICE)
	private String codiciErrore;
	
	@XmlVariabile(nome="ValoreCampoRiga", tipo=TipoVariabile.SEMPLICE)
	private String valoreCampoRiga;

	public String getIdFogli() {
		return idFogli;
	}

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

	public String getNroRigaDa() {
		return nroRigaDa;
	}

	public String getNroRigaA() {
		return nroRigaA;
	}

	public String getEsitoElaborazione() {
		return esitoElaborazione;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public String getCodiciErrore() {
		return codiciErrore;
	}

	public void setIdFogli(String idFogli) {
		this.idFogli = idFogli;
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

	public void setNroRigaDa(String nroRigaDa) {
		this.nroRigaDa = nroRigaDa;
	}

	public void setNroRigaA(String nroRigaA) {
		this.nroRigaA = nroRigaA;
	}

	public void setEsitoElaborazione(String esitoElaborazione) {
		this.esitoElaborazione = esitoElaborazione;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	public void setCodiciErrore(String codiciErrore) {
		this.codiciErrore = codiciErrore;
	}

	public String getValoreCampoRiga() {
		return valoreCampoRiga;
	}

	public void setValoreCampoRiga(String valoreCampoRiga) {
		this.valoreCampoRiga = valoreCampoRiga;
	}
	
}