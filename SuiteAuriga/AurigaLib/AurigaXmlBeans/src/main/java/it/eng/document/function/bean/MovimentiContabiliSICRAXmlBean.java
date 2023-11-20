/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class MovimentiContabiliSICRAXmlBean {
	
	@NumeroColonna(numero = "1")
	private String flgEntrataUscita;

	@NumeroColonna(numero = "3")
	private String numeroImpAcc;

	@NumeroColonna(numero = "4")
	private String annoImpAcc;
	
	@NumeroColonna(numero = "5")
	private String codiceImpAcc;
	
	@NumeroColonna(numero = "6")
	private String idImpAcc;
	
	@NumeroColonna(numero = "7")
	private String oggetto;
	
	@NumeroColonna(numero = "8")
	private String codCentroCosto;

	@NumeroColonna(numero = "9")
	private String idCapitolo;

	@NumeroColonna(numero = "10")
	private String numeroCapitolo;

	@NumeroColonna(numero = "11")
	private String descrizioneCapitolo;

	@NumeroColonna(numero = "12")
	private String livelliPdC;
	
	@NumeroColonna(numero = "13")
	private String annoCompetenza;

	@NumeroColonna(numero = "14")
	private String importo;

	@NumeroColonna(numero = "15")
	private String codiceCIG;

	@NumeroColonna(numero = "16")
	private String codiceCUP;
	
	@NumeroColonna(numero = "17")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataValuta;
	
	@NumeroColonna(numero = "18")
	private String codiceSoggetto;
		
	@NumeroColonna(numero = "19")
	private String note;

	@NumeroColonna(numero = "20")
	private String descrizioneEstesa;
	
	@NumeroColonna(numero = "21")
	private String importoDisponibile;
	
	@NumeroColonna(numero = "22")
	private String flgCopertoDaFPV;
	
	@NumeroColonna(numero = "23")
	private String pianoDeiContiFinanz;
	
	@NumeroColonna(numero = "24")
	private String codiceCapitolo;
	
	@NumeroColonna(numero = "25")
	private String tipoSoggetto;
	
	@NumeroColonna(numero = "26")
	private String flgSoggEstero;

	@NumeroColonna(numero = "27")
	private String denominazioneSogg;

	@NumeroColonna(numero = "28")
	private String codFiscaleSogg;

	@NumeroColonna(numero = "29")
	private String codPIVASogg;
	
	@NumeroColonna(numero = "30")
	private String indirizzoSogg;

	@NumeroColonna(numero = "31")
	private String cap;

	@NumeroColonna(numero = "32")
	private String localita;

	@NumeroColonna(numero = "33")
	private String provincia;
	
	@NumeroColonna(numero = "34")
	private String descrizionePianoDeiConti;
	
	@NumeroColonna(numero = "35")
	private String flgPrenotazione;
	
	@NumeroColonna(numero = "36")
	private String cognomeSogg;

	@NumeroColonna(numero = "37")
	private String nomeSogg;
	
	@NumeroColonna(numero = "38")
	private String flgAutoIncrementante;

	public String getFlgEntrataUscita() {
		return flgEntrataUscita;
	}

	public void setFlgEntrataUscita(String flgEntrataUscita) {
		this.flgEntrataUscita = flgEntrataUscita;
	}

	public String getNumeroImpAcc() {
		return numeroImpAcc;
	}

	public void setNumeroImpAcc(String numeroImpAcc) {
		this.numeroImpAcc = numeroImpAcc;
	}

	public String getAnnoImpAcc() {
		return annoImpAcc;
	}

	public void setAnnoImpAcc(String annoImpAcc) {
		this.annoImpAcc = annoImpAcc;
	}

	public String getCodiceImpAcc() {
		return codiceImpAcc;
	}

	public void setCodiceImpAcc(String codiceImpAcc) {
		this.codiceImpAcc = codiceImpAcc;
	}

	public String getIdImpAcc() {
		return idImpAcc;
	}

	public void setIdImpAcc(String idImpAcc) {
		this.idImpAcc = idImpAcc;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getCodCentroCosto() {
		return codCentroCosto;
	}

	public void setCodCentroCosto(String codCentroCosto) {
		this.codCentroCosto = codCentroCosto;
	}

	public String getIdCapitolo() {
		return idCapitolo;
	}

	public void setIdCapitolo(String idCapitolo) {
		this.idCapitolo = idCapitolo;
	}

	public String getNumeroCapitolo() {
		return numeroCapitolo;
	}

	public void setNumeroCapitolo(String numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public String getLivelliPdC() {
		return livelliPdC;
	}

	public void setLivelliPdC(String livelliPdC) {
		this.livelliPdC = livelliPdC;
	}

	public String getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(String annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
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

	public Date getDataValuta() {
		return dataValuta;
	}

	public void setDataValuta(Date dataValuta) {
		this.dataValuta = dataValuta;
	}

	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getImportoDisponibile() {
		return importoDisponibile;
	}

	public void setImportoDisponibile(String importoDisponibile) {
		this.importoDisponibile = importoDisponibile;
	}

	public String getFlgCopertoDaFPV() {
		return flgCopertoDaFPV;
	}

	public void setFlgCopertoDaFPV(String flgCopertoDaFPV) {
		this.flgCopertoDaFPV = flgCopertoDaFPV;
	}

	public String getPianoDeiContiFinanz() {
		return pianoDeiContiFinanz;
	}

	public void setPianoDeiContiFinanz(String pianoDeiContiFinanz) {
		this.pianoDeiContiFinanz = pianoDeiContiFinanz;
	}

	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}

	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getFlgSoggEstero() {
		return flgSoggEstero;
	}

	public void setFlgSoggEstero(String flgSoggEstero) {
		this.flgSoggEstero = flgSoggEstero;
	}

	public String getDenominazioneSogg() {
		return denominazioneSogg;
	}

	public void setDenominazioneSogg(String denominazioneSogg) {
		this.denominazioneSogg = denominazioneSogg;
	}

	public String getCodFiscaleSogg() {
		return codFiscaleSogg;
	}

	public void setCodFiscaleSogg(String codFiscaleSogg) {
		this.codFiscaleSogg = codFiscaleSogg;
	}

	public String getCodPIVASogg() {
		return codPIVASogg;
	}

	public void setCodPIVASogg(String codPIVASogg) {
		this.codPIVASogg = codPIVASogg;
	}

	public String getIndirizzoSogg() {
		return indirizzoSogg;
	}

	public void setIndirizzoSogg(String indirizzoSogg) {
		this.indirizzoSogg = indirizzoSogg;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDescrizionePianoDeiConti() {
		return descrizionePianoDeiConti;
	}

	public void setDescrizionePianoDeiConti(String descrizionePianoDeiConti) {
		this.descrizionePianoDeiConti = descrizionePianoDeiConti;
	}

	public String getFlgPrenotazione() {
		return flgPrenotazione;
	}

	public void setFlgPrenotazione(String flgPrenotazione) {
		this.flgPrenotazione = flgPrenotazione;
	}

	public String getCognomeSogg() {
		return cognomeSogg;
	}

	public void setCognomeSogg(String cognomeSogg) {
		this.cognomeSogg = cognomeSogg;
	}

	public String getNomeSogg() {
		return nomeSogg;
	}

	public void setNomeSogg(String nomeSogg) {
		this.nomeSogg = nomeSogg;
	}

	public String getFlgAutoIncrementante() {
		return flgAutoIncrementante;
	}

	public void setFlgAutoIncrementante(String flgAutoIncrementante) {
		this.flgAutoIncrementante = flgAutoIncrementante;
	}
	
}
