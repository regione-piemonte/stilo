/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputRicercaVociBilancio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parte;
	private String tipoRisultato;
	private String codifica;
	private Integer annoCompetenza;
	private Calendar dataValuta;
	private String centroDiCosto;
	private String codResponsabileProcedimento;
	private BigInteger codMissione;
	private BigInteger codProgramma;
	private BigInteger codTitolo;
	private BigInteger codMacroAggregato;
	private BigInteger codTipologia;
	private BigInteger codCategoria;
	private BigInteger numCapitolo;
	private String descrizione;
	private BigInteger codCentroCosto;
	private String siglaCPT;
	private String siglaPianoFinanziario;
	private Boolean copertoDaFPV;
	private BigInteger codLavoro;
	private BigInteger codTipoSpesa;
	private BigInteger codTipoFinanz;
	private BigInteger codProgetto;
	private Boolean flagRaggruppaCentroCosto;
	private Boolean flagRaggruppaCPT;
	private Boolean flagRaggruppaPianoFin;
	private Boolean flagRaggruppaFPV;
	private Boolean flagRaggruppaLavoro;
	private Boolean flagRaggruppaTipoSpesa;
	private Boolean flagRaggruppaTipoFinanz;
	private Boolean flagRaggruppaProgetto;
	private Boolean aclBilancio;

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public String getTipoRisultato() {
		return tipoRisultato;
	}

	public void setTipoRisultato(String tipoRisultato) {
		this.tipoRisultato = tipoRisultato;
	}

	public String getCodifica() {
		return codifica;
	}

	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}

	public Integer getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(Integer annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}

	public Calendar getDataValuta() {
		return dataValuta;
	}

	public void setDataValuta(Calendar dataValuta) {
		this.dataValuta = dataValuta;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getCodResponsabileProcedimento() {
		return codResponsabileProcedimento;
	}

	public void setCodResponsabileProcedimento(String codResponsabileProcedimento) {
		this.codResponsabileProcedimento = codResponsabileProcedimento;
	}

	public BigInteger getCodMissione() {
		return codMissione;
	}

	public void setCodMissione(BigInteger codMissione) {
		this.codMissione = codMissione;
	}

	public BigInteger getCodProgramma() {
		return codProgramma;
	}

	public void setCodProgramma(BigInteger codProgramma) {
		this.codProgramma = codProgramma;
	}

	public BigInteger getCodTitolo() {
		return codTitolo;
	}

	public void setCodTitolo(BigInteger codTitolo) {
		this.codTitolo = codTitolo;
	}

	public BigInteger getCodMacroAggregato() {
		return codMacroAggregato;
	}

	public void setCodMacroAggregato(BigInteger codMacroAggregato) {
		this.codMacroAggregato = codMacroAggregato;
	}

	public BigInteger getCodTipologia() {
		return codTipologia;
	}

	public void setCodTipologia(BigInteger codTipologia) {
		this.codTipologia = codTipologia;
	}

	public BigInteger getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(BigInteger codCategoria) {
		this.codCategoria = codCategoria;
	}

	public BigInteger getNumCapitolo() {
		return numCapitolo;
	}

	public void setNumCapitolo(BigInteger numCapitolo) {
		this.numCapitolo = numCapitolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigInteger getCodCentroCosto() {
		return codCentroCosto;
	}

	public void setCodCentroCosto(BigInteger codCentroCosto) {
		this.codCentroCosto = codCentroCosto;
	}

	public String getSiglaCPT() {
		return siglaCPT;
	}

	public void setSiglaCPT(String siglaCPT) {
		this.siglaCPT = siglaCPT;
	}

	public String getSiglaPianoFinanziario() {
		return siglaPianoFinanziario;
	}

	public void setSiglaPianoFinanziario(String siglaPianoFinanziario) {
		this.siglaPianoFinanziario = siglaPianoFinanziario;
	}

	public Boolean getCopertoDaFPV() {
		return copertoDaFPV;
	}

	public void setCopertoDaFPV(Boolean copertoDaFPV) {
		this.copertoDaFPV = copertoDaFPV;
	}

	public BigInteger getCodLavoro() {
		return codLavoro;
	}

	public void setCodLavoro(BigInteger codLavoro) {
		this.codLavoro = codLavoro;
	}

	public BigInteger getCodTipoSpesa() {
		return codTipoSpesa;
	}

	public void setCodTipoSpesa(BigInteger codTipoSpesa) {
		this.codTipoSpesa = codTipoSpesa;
	}

	public BigInteger getCodTipoFinanz() {
		return codTipoFinanz;
	}

	public void setCodTipoFinanz(BigInteger codTipoFinanz) {
		this.codTipoFinanz = codTipoFinanz;
	}

	public BigInteger getCodProgetto() {
		return codProgetto;
	}

	public void setCodProgetto(BigInteger codProgetto) {
		this.codProgetto = codProgetto;
	}

	public Boolean getFlagRaggruppaCentroCosto() {
		return flagRaggruppaCentroCosto;
	}

	public void setFlagRaggruppaCentroCosto(Boolean flagRaggruppaCentroCosto) {
		this.flagRaggruppaCentroCosto = flagRaggruppaCentroCosto;
	}

	public Boolean getFlagRaggruppaCPT() {
		return flagRaggruppaCPT;
	}

	public void setFlagRaggruppaCPT(Boolean flagRaggruppaCPT) {
		this.flagRaggruppaCPT = flagRaggruppaCPT;
	}

	public Boolean getFlagRaggruppaPianoFin() {
		return flagRaggruppaPianoFin;
	}

	public void setFlagRaggruppaPianoFin(Boolean flagRaggruppaPianoFin) {
		this.flagRaggruppaPianoFin = flagRaggruppaPianoFin;
	}

	public Boolean getFlagRaggruppaFPV() {
		return flagRaggruppaFPV;
	}

	public void setFlagRaggruppaFPV(Boolean flagRaggruppaFPV) {
		this.flagRaggruppaFPV = flagRaggruppaFPV;
	}

	public Boolean getFlagRaggruppaLavoro() {
		return flagRaggruppaLavoro;
	}

	public void setFlagRaggruppaLavoro(Boolean flagRaggruppaLavoro) {
		this.flagRaggruppaLavoro = flagRaggruppaLavoro;
	}

	public Boolean getFlagRaggruppaTipoSpesa() {
		return flagRaggruppaTipoSpesa;
	}

	public void setFlagRaggruppaTipoSpesa(Boolean flagRaggruppaTipoSpesa) {
		this.flagRaggruppaTipoSpesa = flagRaggruppaTipoSpesa;
	}

	public Boolean getFlagRaggruppaTipoFinanz() {
		return flagRaggruppaTipoFinanz;
	}

	public void setFlagRaggruppaTipoFinanz(Boolean flagRaggruppaTipoFinanz) {
		this.flagRaggruppaTipoFinanz = flagRaggruppaTipoFinanz;
	}

	public Boolean getFlagRaggruppaProgetto() {
		return flagRaggruppaProgetto;
	}

	public void setFlagRaggruppaProgetto(Boolean flagRaggruppaProgetto) {
		this.flagRaggruppaProgetto = flagRaggruppaProgetto;
	}

	public Boolean getAclBilancio() {
		return aclBilancio;
	}

	public void setAclBilancio(Boolean aclBilancio) {
		this.aclBilancio = aclBilancio;
	}

}// SicraInputRicercaVociBilancio
