/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class DatiContabiliATERSIRXmlBean {
	
	@NumeroColonna(numero = "1")
	private String denominazioneBeneficiario;
	
	@NumeroColonna(numero = "2")
	private String cfPIvaBeneficiario;
	
	@NumeroColonna(numero = "3")
	private String importo;
	
	@NumeroColonna(numero = "4")
	private String codiceCapitolo;
	
	@NumeroColonna(numero = "5")
	private String descrizioneCapitolo;
	
	@NumeroColonna(numero = "6")
	private String codiceMissione;
	
	@NumeroColonna(numero = "7")
	private String codiceProgramma;
	
	@NumeroColonna(numero = "8")
	private String codiceTitolo;
	
	@NumeroColonna(numero = "9")
	private String codiceMacroAggregato;
	
	@NumeroColonna(numero = "10")
	private String nrImpegno;
	
	@NumeroColonna(numero = "11")
	private String annoCompetenza;
	
	@NumeroColonna(numero = "12")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataImpegno;

	@NumeroColonna(numero = "13")
	private String descrizioneImpegno;
	
	@NumeroColonna(numero = "14")
	private String annoRegistrazione;
		
	@NumeroColonna(numero = "15")
	private String codiceCIG;		
	
	@NumeroColonna(numero = "16")
	private String codiceCUP;			
	
	@NumeroColonna(numero = "17")
	private String descrizioneMissione;
	
	@NumeroColonna(numero = "18")
	private String descrizioneProgramma;
	
	@NumeroColonna(numero = "19")
	private String descrizioneTitolo;
	
	@NumeroColonna(numero = "20")
	private String descrizioneMacroAggregato;

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getCfPIvaBeneficiario() {
		return cfPIvaBeneficiario;
	}

	public void setCfPIvaBeneficiario(String cfPIvaBeneficiario) {
		this.cfPIvaBeneficiario = cfPIvaBeneficiario;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}

	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public String getCodiceMissione() {
		return codiceMissione;
	}

	public void setCodiceMissione(String codiceMissione) {
		this.codiceMissione = codiceMissione;
	}

	public String getCodiceProgramma() {
		return codiceProgramma;
	}

	public void setCodiceProgramma(String codiceProgramma) {
		this.codiceProgramma = codiceProgramma;
	}

	public String getCodiceTitolo() {
		return codiceTitolo;
	}

	public void setCodiceTitolo(String codiceTitolo) {
		this.codiceTitolo = codiceTitolo;
	}

	public String getCodiceMacroAggregato() {
		return codiceMacroAggregato;
	}

	public void setCodiceMacroAggregato(String codiceMacroAggregato) {
		this.codiceMacroAggregato = codiceMacroAggregato;
	}

	public String getNrImpegno() {
		return nrImpegno;
	}

	public void setNrImpegno(String nrImpegno) {
		this.nrImpegno = nrImpegno;
	}

	public String getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(String annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}

	public Date getDataImpegno() {
		return dataImpegno;
	}

	public void setDataImpegno(Date dataImpegno) {
		this.dataImpegno = dataImpegno;
	}

	public String getDescrizioneImpegno() {
		return descrizioneImpegno;
	}

	public void setDescrizioneImpegno(String descrizioneImpegno) {
		this.descrizioneImpegno = descrizioneImpegno;
	}

	public String getAnnoRegistrazione() {
		return annoRegistrazione;
	}

	public void setAnnoRegistrazione(String annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public String getDescrizioneMissione() {
		return descrizioneMissione;
	}

	public void setDescrizioneMissione(String descrizioneMissione) {
		this.descrizioneMissione = descrizioneMissione;
	}

	public String getDescrizioneProgramma() {
		return descrizioneProgramma;
	}

	public void setDescrizioneProgramma(String descrizioneProgramma) {
		this.descrizioneProgramma = descrizioneProgramma;
	}

	public String getDescrizioneTitolo() {
		return descrizioneTitolo;
	}

	public void setDescrizioneTitolo(String descrizioneTitolo) {
		this.descrizioneTitolo = descrizioneTitolo;
	}

	public String getDescrizioneMacroAggregato() {
		return descrizioneMacroAggregato;
	}

	public void setDescrizioneMacroAggregato(String descrizioneMacroAggregato) {
		this.descrizioneMacroAggregato = descrizioneMacroAggregato;
	} 
		
}
