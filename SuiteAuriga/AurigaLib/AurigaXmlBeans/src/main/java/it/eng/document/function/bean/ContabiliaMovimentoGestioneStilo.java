/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaMovimentoGestioneStilo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoCompetenza;
	private Integer annoCompetenzaModifica;
	private Integer annoCompetenzaSubAccertamento;
	private Integer annoCompetenzaSubImpegno;
	private ContabiliaCapitolo capitolo;
	private ContabiliaCategoria categoria;
	private String cig;
	private ContabiliaClasseSoggettoStilo classeSoggetto;
	private ContabiliaClasseSoggettoStilo classeSoggettoIniziale;
	private ContabiliaClasseSoggettoStilo classseSoggettoFinale;
	private ContabiliaClassificatoreGenerico codUE;
	private ContabiliaClassificatoreGenerico cofog;
	private String cup;
	private String descrizioneAccertamento;
	private String descrizioneImpegno;
	private String descrizioneModifica;
	private String descrizioneSubAccertamento;
	private String descrizioneSubImpegno;
	private ContabiliaClassificatoreGenerico gsa;
	private BigDecimal importoAttualeAccertamento;
	private BigDecimal importoAttualeImpegno;
	private BigDecimal importoAttualeSubAccertamento;
	private BigDecimal importoAttualeSubImpegno;
	private BigDecimal importoInizialeAccertamento;
	private BigDecimal importoInizialeImpegno;
	private BigDecimal importoInizialeSubAccertamento;
	private BigDecimal importoInizialeSubImpegno;
	private BigDecimal importoModifica;
	private ContabiliaMacroaggregato macroaggregato;
	private ContabiliaMissione missione;
	private String motivoAssenzaCig;
	private ContabiliaClassificatoreGenerico naturaRicorrente;
	private BigDecimal numeroAccertamento;
	private BigDecimal numeroImpegno;
	private Integer numeroModifica;
	private BigDecimal numeroSubAccertamento;
	private BigDecimal numeroSubImpegno;
	private ContabiliaPianoDeiContiFinanziario pianoDeiContiFinanziario;
	private String prenotazione;
	private String prenotazioneLiquidabile;
	private ContabiliaProgettoStilo progetto;
	private ContabiliaProgramma programma;
	private ContabiliaSoggettoStilo soggetto;
	private ContabiliaSoggettoStilo soggettoFinale;
	private ContabiliaSoggettoStilo soggettoIniziale;
	private ContabiliaTipoDebitoSiopeStilo tipoDebitoSiope;
	private ContabiliaTipoFinanziamento tipoFinanziamento;
	private String tipoMovimentoGestione;
	private ContabiliaTipologia tipologia;
	private ContabiliaTitolo titolo;
	private ContabiliaVincoliStilo vincoli;
	
	public Integer getAnnoCompetenza() {
		return annoCompetenza;
	}
	
	public void setAnnoCompetenza(Integer annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}
	
	public Integer getAnnoCompetenzaModifica() {
		return annoCompetenzaModifica;
	}
	
	public void setAnnoCompetenzaModifica(Integer annoCompetenzaModifica) {
		this.annoCompetenzaModifica = annoCompetenzaModifica;
	}
	
	public Integer getAnnoCompetenzaSubAccertamento() {
		return annoCompetenzaSubAccertamento;
	}
	
	public void setAnnoCompetenzaSubAccertamento(Integer annoCompetenzaSubAccertamento) {
		this.annoCompetenzaSubAccertamento = annoCompetenzaSubAccertamento;
	}
	
	public Integer getAnnoCompetenzaSubImpegno() {
		return annoCompetenzaSubImpegno;
	}
	
	public void setAnnoCompetenzaSubImpegno(Integer annoCompetenzaSubImpegno) {
		this.annoCompetenzaSubImpegno = annoCompetenzaSubImpegno;
	}
	
	public ContabiliaCapitolo getCapitolo() {
		return capitolo;
	}
	
	public void setCapitolo(ContabiliaCapitolo capitolo) {
		this.capitolo = capitolo;
	}
	
	public ContabiliaCategoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(ContabiliaCategoria categoria) {
		this.categoria = categoria;
	}
	
	public String getCig() {
		return cig;
	}
	
	public void setCig(String cig) {
		this.cig = cig;
	}
	
	public ContabiliaClassificatoreGenerico getCodUE() {
		return codUE;
	}
	
	public void setCodUE(ContabiliaClassificatoreGenerico codUE) {
		this.codUE = codUE;
	}
	
	public ContabiliaClassificatoreGenerico getCofog() {
		return cofog;
	}
	
	public void setCofog(ContabiliaClassificatoreGenerico cofog) {
		this.cofog = cofog;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public String getDescrizioneAccertamento() {
		return descrizioneAccertamento;
	}
	
	public void setDescrizioneAccertamento(String descrizioneAccertamento) {
		this.descrizioneAccertamento = descrizioneAccertamento;
	}
	
	public String getDescrizioneImpegno() {
		return descrizioneImpegno;
	}
	
	public void setDescrizioneImpegno(String descrizioneImpegno) {
		this.descrizioneImpegno = descrizioneImpegno;
	}
	
	public String getDescrizioneModifica() {
		return descrizioneModifica;
	}
	
	public void setDescrizioneModifica(String descrizioneModifica) {
		this.descrizioneModifica = descrizioneModifica;
	}
	
	public String getDescrizioneSubAccertamento() {
		return descrizioneSubAccertamento;
	}
	
	public void setDescrizioneSubAccertamento(String descrizioneSubAccertamento) {
		this.descrizioneSubAccertamento = descrizioneSubAccertamento;
	}
	
	public String getDescrizioneSubImpegno() {
		return descrizioneSubImpegno;
	}
	
	public void setDescrizioneSubImpegno(String descrizioneSubImpegno) {
		this.descrizioneSubImpegno = descrizioneSubImpegno;
	}
	
	public ContabiliaClassificatoreGenerico getGsa() {
		return gsa;
	}
	
	public void setGsa(ContabiliaClassificatoreGenerico gsa) {
		this.gsa = gsa;
	}
	
	public BigDecimal getImportoAttualeAccertamento() {
		return importoAttualeAccertamento;
	}
	
	public void setImportoAttualeAccertamento(BigDecimal importoAttualeAccertamento) {
		this.importoAttualeAccertamento = importoAttualeAccertamento;
	}
	
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	
	public BigDecimal getImportoAttualeSubAccertamento() {
		return importoAttualeSubAccertamento;
	}
	
	public void setImportoAttualeSubAccertamento(BigDecimal importoAttualeSubAccertamento) {
		this.importoAttualeSubAccertamento = importoAttualeSubAccertamento;
	}
	
	public BigDecimal getImportoAttualeSubImpegno() {
		return importoAttualeSubImpegno;
	}
	
	public void setImportoAttualeSubImpegno(BigDecimal importoAttualeSubImpegno) {
		this.importoAttualeSubImpegno = importoAttualeSubImpegno;
	}
	
	public BigDecimal getImportoInizialeAccertamento() {
		return importoInizialeAccertamento;
	}
	
	public void setImportoInizialeAccertamento(BigDecimal importoInizialeAccertamento) {
		this.importoInizialeAccertamento = importoInizialeAccertamento;
	}
	
	public BigDecimal getImportoInizialeImpegno() {
		return importoInizialeImpegno;
	}
	
	public void setImportoInizialeImpegno(BigDecimal importoInizialeImpegno) {
		this.importoInizialeImpegno = importoInizialeImpegno;
	}
	
	public BigDecimal getImportoInizialeSubAccertamento() {
		return importoInizialeSubAccertamento;
	}
	
	public void setImportoInizialeSubAccertamento(BigDecimal importoInizialeSubAccertamento) {
		this.importoInizialeSubAccertamento = importoInizialeSubAccertamento;
	}
	
	public BigDecimal getImportoInizialeSubImpegno() {
		return importoInizialeSubImpegno;
	}
	
	public void setImportoInizialeSubImpegno(BigDecimal importoInizialeSubImpegno) {
		this.importoInizialeSubImpegno = importoInizialeSubImpegno;
	}
	
	public BigDecimal getImportoModifica() {
		return importoModifica;
	}
	
	public void setImportoModifica(BigDecimal importoModifica) {
		this.importoModifica = importoModifica;
	}
	
	public ContabiliaMacroaggregato getMacroaggregato() {
		return macroaggregato;
	}
	
	public void setMacroaggregato(ContabiliaMacroaggregato macroaggregato) {
		this.macroaggregato = macroaggregato;
	}
	
	public ContabiliaMissione getMissione() {
		return missione;
	}
	
	public void setMissione(ContabiliaMissione missione) {
		this.missione = missione;
	}
	
	public String getMotivoAssenzaCig() {
		return motivoAssenzaCig;
	}
	
	public void setMotivoAssenzaCig(String motivoAssenzaCig) {
		this.motivoAssenzaCig = motivoAssenzaCig;
	}
	
	public ContabiliaClasseSoggettoStilo getClasseSoggetto() {
		return classeSoggetto;
	}
	
	public void setClasseSoggetto(ContabiliaClasseSoggettoStilo classeSoggetto) {
		this.classeSoggetto = classeSoggetto;
	}
	
	public ContabiliaClasseSoggettoStilo getClasseSoggettoIniziale() {
		return classeSoggettoIniziale;
	}
	
	public void setClasseSoggettoIniziale(ContabiliaClasseSoggettoStilo classeSoggettoIniziale) {
		this.classeSoggettoIniziale = classeSoggettoIniziale;
	}
	
	public ContabiliaClasseSoggettoStilo getClassseSoggettoFinale() {
		return classseSoggettoFinale;
	}
	
	public void setClassseSoggettoFinale(ContabiliaClasseSoggettoStilo classseSoggettoFinale) {
		this.classseSoggettoFinale = classseSoggettoFinale;
	}
	
	public ContabiliaClassificatoreGenerico getNaturaRicorrente() {
		return naturaRicorrente;
	}
	
	public void setNaturaRicorrente(ContabiliaClassificatoreGenerico naturaRicorrente) {
		this.naturaRicorrente = naturaRicorrente;
	}
	
	public BigDecimal getNumeroAccertamento() {
		return numeroAccertamento;
	}
	
	public void setNumeroAccertamento(BigDecimal numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	public BigDecimal getNumeroImpegno() {
		return numeroImpegno;
	}
	
	public void setNumeroImpegno(BigDecimal numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	
	public Integer getNumeroModifica() {
		return numeroModifica;
	}
	
	public void setNumeroModifica(Integer numeroModifica) {
		this.numeroModifica = numeroModifica;
	}
	
	public BigDecimal getNumeroSubAccertamento() {
		return numeroSubAccertamento;
	}
	
	public void setNumeroSubAccertamento(BigDecimal numeroSubAccertamento) {
		this.numeroSubAccertamento = numeroSubAccertamento;
	}
	
	public BigDecimal getNumeroSubImpegno() {
		return numeroSubImpegno;
	}
	
	public void setNumeroSubImpegno(BigDecimal numeroSubImpegno) {
		this.numeroSubImpegno = numeroSubImpegno;
	}
	
	public ContabiliaPianoDeiContiFinanziario getPianoDeiContiFinanziario() {
		return pianoDeiContiFinanziario;
	}
	
	public void setPianoDeiContiFinanziario(ContabiliaPianoDeiContiFinanziario pianoDeiContiFinanziario) {
		this.pianoDeiContiFinanziario = pianoDeiContiFinanziario;
	}
	
	public String getPrenotazione() {
		return prenotazione;
	}
	
	public void setPrenotazione(String prenotazione) {
		this.prenotazione = prenotazione;
	}
	
	public String getPrenotazioneLiquidabile() {
		return prenotazioneLiquidabile;
	}

	public void setPrenotazioneLiquidabile(String prenotazioneLiquidabile) {
		this.prenotazioneLiquidabile = prenotazioneLiquidabile;
	}
	
	public ContabiliaProgettoStilo getProgetto() {
		return progetto;
	}
	
	public void setProgetto(ContabiliaProgettoStilo progetto) {
		this.progetto = progetto;
	}
	
	public ContabiliaProgramma getProgramma() {
		return programma;
	}
	
	public void setProgramma(ContabiliaProgramma programma) {
		this.programma = programma;
	}
	
	public ContabiliaSoggettoStilo getSoggetto() {
		return soggetto;
	}
	
	public void setSoggetto(ContabiliaSoggettoStilo soggetto) {
		this.soggetto = soggetto;
	}
	
	public ContabiliaSoggettoStilo getSoggettoFinale() {
		return soggettoFinale;
	}
	
	public void setSoggettoFinale(ContabiliaSoggettoStilo soggettoFinale) {
		this.soggettoFinale = soggettoFinale;
	}
	
	public ContabiliaSoggettoStilo getSoggettoIniziale() {
		return soggettoIniziale;
	}
	
	public void setSoggettoIniziale(ContabiliaSoggettoStilo soggettoIniziale) {
		this.soggettoIniziale = soggettoIniziale;
	}
	
	public ContabiliaTipoDebitoSiopeStilo getTipoDebitoSiope() {
		return tipoDebitoSiope;
	}
	
	public void setTipoDebitoSiope(ContabiliaTipoDebitoSiopeStilo tipoDebitoSiope) {
		this.tipoDebitoSiope = tipoDebitoSiope;
	}
	
	public ContabiliaTipoFinanziamento getTipoFinanziamento() {
		return tipoFinanziamento;
	}
	
	public void setTipoFinanziamento(ContabiliaTipoFinanziamento tipoFinanziamento) {
		this.tipoFinanziamento = tipoFinanziamento;
	}
	
	public String getTipoMovimentoGestione() {
		return tipoMovimentoGestione;
	}
	
	public void setTipoMovimentoGestione(String tipoMovimentoGestione) {
		this.tipoMovimentoGestione = tipoMovimentoGestione;
	}
	
	public ContabiliaTipologia getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(ContabiliaTipologia tipologia) {
		this.tipologia = tipologia;
	}
	
	public ContabiliaTitolo getTitolo() {
		return titolo;
	}
	
	public void setTitolo(ContabiliaTitolo titolo) {
		this.titolo = titolo;
	}
	
	public ContabiliaVincoliStilo getVincoli() {
		return vincoli;
	}
	
	public void setVincoli(ContabiliaVincoliStilo vincoli) {
		this.vincoli = vincoli;
	}
	
	@Override
	public String toString() {
		return "ContabiliaMovimentoGestioneStilo [annoCompetenza=" + annoCompetenza + ", annoCompetenzaModifica="
				+ annoCompetenzaModifica + ", annoCompetenzaSubAccertamento=" + annoCompetenzaSubAccertamento
				+ ", annoCompetenzaSubImpegno=" + annoCompetenzaSubImpegno + ", capitolo=" + capitolo + ", categoria="
				+ categoria + ", cig=" + cig + ", classeSoggetto=" + classeSoggetto + ", classeSoggettoIniziale="
				+ classeSoggettoIniziale + ", classseSoggettoFinale=" + classseSoggettoFinale + ", codUE=" + codUE
				+ ", cofog=" + cofog + ", cup=" + cup + ", descrizioneAccertamento=" + descrizioneAccertamento
				+ ", descrizioneImpegno=" + descrizioneImpegno + ", descrizioneModifica=" + descrizioneModifica
				+ ", descrizioneSubAccertamento=" + descrizioneSubAccertamento + ", descrizioneSubImpegno="
				+ descrizioneSubImpegno + ", gsa=" + gsa + ", importoAttualeAccertamento=" + importoAttualeAccertamento
				+ ", importoAttualeImpegno=" + importoAttualeImpegno + ", importoAttualeSubAccertamento="
				+ importoAttualeSubAccertamento + ", importoAttualeSubImpegno=" + importoAttualeSubImpegno
				+ ", importoInizialeAccertamento=" + importoInizialeAccertamento + ", importoInizialeImpegno="
				+ importoInizialeImpegno + ", importoInizialeSubAccertamento=" + importoInizialeSubAccertamento
				+ ", importoInizialeSubImpegno=" + importoInizialeSubImpegno + ", importoModifica=" + importoModifica
				+ ", macroaggregato=" + macroaggregato + ", missione=" + missione + ", motivoAssenzaCig="
				+ motivoAssenzaCig + ", naturaRicorrente=" + naturaRicorrente + ", numeroAccertamento="
				+ numeroAccertamento + ", numeroImpegno=" + numeroImpegno + ", numeroModifica=" + numeroModifica
				+ ", numeroSubAccertamento=" + numeroSubAccertamento + ", numeroSubImpegno=" + numeroSubImpegno
				+ ", pianoDeiContiFinanziario=" + pianoDeiContiFinanziario + ", prenotazione=" + prenotazione
				+ ", prenotazioneLiquidabile=" + prenotazioneLiquidabile + ", progetto=" + progetto + ", programma="
				+ programma + ", soggetto=" + soggetto + ", soggettoFinale=" + soggettoFinale + ", soggettoIniziale="
				+ soggettoIniziale + ", tipoDebitoSiope=" + tipoDebitoSiope + ", tipoFinanziamento=" + tipoFinanziamento
				+ ", tipoMovimentoGestione=" + tipoMovimentoGestione + ", tipologia=" + tipologia + ", titolo=" + titolo
				+ ", vincoli=" + vincoli + "]";
	}
	
}
