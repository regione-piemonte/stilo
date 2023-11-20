/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaCapitoloUscitaGestione extends ContabiliaCapitolo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<ContabiliaImportoCapitoloUscitaGestione> importiUG;
	private ContabiliaMacroaggregato macroaggregato;
	private ContabiliaMissione missione;
	private ContabiliaProgramma programma;
	
	public List<ContabiliaImportoCapitoloUscitaGestione> getImportiUG() {
		return importiUG;
	}
	
	public void setImportiUG(List<ContabiliaImportoCapitoloUscitaGestione> importiUG) {
		this.importiUG = importiUG;
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
	
	public ContabiliaProgramma getProgramma() {
		return programma;
	}
	
	public void setProgramma(ContabiliaProgramma programma) {
		this.programma = programma;
	}
	
	@Override
	public String toString() {
		return "ContabiliaCapitoloUscitaGestione [importiUG=" + importiUG + ", macroaggregato=" + macroaggregato
				+ ", missione=" + missione + ", programma=" + programma + "]";
	}
	
}
