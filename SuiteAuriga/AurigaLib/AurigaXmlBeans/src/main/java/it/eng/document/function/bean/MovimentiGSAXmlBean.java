/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class MovimentiGSAXmlBean {
	
	@NumeroColonna(numero = "1")
	private String codTipoMovimento;

	@NumeroColonna(numero = "2")
	private String desTipoMovimento;

	@NumeroColonna(numero = "3")
	private String flgEntrataUscita;

	@NumeroColonna(numero = "4")
	private String numeroCapitolo;

	@NumeroColonna(numero = "5")
	private String numeroArticolo;
	
	@NumeroColonna(numero = "6")
	private String idUoStrutturaCompetente;
	
	@NumeroColonna(numero = "7")
	private String codRapidoStrutturaCompetente;
	
	@NumeroColonna(numero = "8")
	private String desStrutturaCompetente;
	
	@NumeroColonna(numero = "9")
	private String numeroMovimento;

	@NumeroColonna(numero = "10")
	private String annoMovimento;

	@NumeroColonna(numero = "11")
	private String descrizioneMovimento;
	
	@NumeroColonna(numero = "12")
	private String numeroSub;
	
	@NumeroColonna(numero = "13")
	private String annoSub;
	
	@NumeroColonna(numero = "14")
	private String numeroModifica;

	@NumeroColonna(numero = "15")
	private String annoModifica;
	
	@NumeroColonna(numero = "16")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInserimento;
	
	@NumeroColonna(numero = "17")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRegistrazione;
	
	@NumeroColonna(numero = "18")
	private String importo;	

	@NumeroColonna(numero = "19")
	private String codiceCIG;

	@NumeroColonna(numero = "20")
	private String codiceCUP;
	
	@NumeroColonna(numero = "21")
	private String codiceSoggetto;
	
	@NumeroColonna(numero = "22")
	private String datiGsa;

	public String getCodTipoMovimento() {
		return codTipoMovimento;
	}

	public void setCodTipoMovimento(String codTipoMovimento) {
		this.codTipoMovimento = codTipoMovimento;
	}

	public String getDesTipoMovimento() {
		return desTipoMovimento;
	}

	public void setDesTipoMovimento(String desTipoMovimento) {
		this.desTipoMovimento = desTipoMovimento;
	}

	public String getFlgEntrataUscita() {
		return flgEntrataUscita;
	}

	public void setFlgEntrataUscita(String flgEntrataUscita) {
		this.flgEntrataUscita = flgEntrataUscita;
	}

	public String getNumeroCapitolo() {
		return numeroCapitolo;
	}

	public void setNumeroCapitolo(String numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}

	public String getNumeroArticolo() {
		return numeroArticolo;
	}

	public void setNumeroArticolo(String numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}

	public String getIdUoStrutturaCompetente() {
		return idUoStrutturaCompetente;
	}

	public void setIdUoStrutturaCompetente(String idUoStrutturaCompetente) {
		this.idUoStrutturaCompetente = idUoStrutturaCompetente;
	}

	public String getCodRapidoStrutturaCompetente() {
		return codRapidoStrutturaCompetente;
	}

	public void setCodRapidoStrutturaCompetente(String codRapidoStrutturaCompetente) {
		this.codRapidoStrutturaCompetente = codRapidoStrutturaCompetente;
	}

	public String getDesStrutturaCompetente() {
		return desStrutturaCompetente;
	}

	public void setDesStrutturaCompetente(String desStrutturaCompetente) {
		this.desStrutturaCompetente = desStrutturaCompetente;
	}

	public String getNumeroMovimento() {
		return numeroMovimento;
	}

	public void setNumeroMovimento(String numeroMovimento) {
		this.numeroMovimento = numeroMovimento;
	}

	public String getAnnoMovimento() {
		return annoMovimento;
	}

	public void setAnnoMovimento(String annoMovimento) {
		this.annoMovimento = annoMovimento;
	}

	public String getDescrizioneMovimento() {
		return descrizioneMovimento;
	}

	public void setDescrizioneMovimento(String descrizioneMovimento) {
		this.descrizioneMovimento = descrizioneMovimento;
	}

	public String getNumeroSub() {
		return numeroSub;
	}

	public void setNumeroSub(String numeroSub) {
		this.numeroSub = numeroSub;
	}

	public String getAnnoSub() {
		return annoSub;
	}

	public void setAnnoSub(String annoSub) {
		this.annoSub = annoSub;
	}

	public String getNumeroModifica() {
		return numeroModifica;
	}

	public void setNumeroModifica(String numeroModifica) {
		this.numeroModifica = numeroModifica;
	}

	public String getAnnoModifica() {
		return annoModifica;
	}

	public void setAnnoModifica(String annoModifica) {
		this.annoModifica = annoModifica;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
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

	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}

	public String getDatiGsa() {
		return datiGsa;
	}

	public void setDatiGsa(String datiGsa) {
		this.datiGsa = datiGsa;
	}
	
}
