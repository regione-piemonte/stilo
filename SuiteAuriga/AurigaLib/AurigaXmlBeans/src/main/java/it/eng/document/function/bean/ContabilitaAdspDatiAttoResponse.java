/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspDatiAttoResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer progAtto;
	private String kStato;
	private String oggettoAt;
	private String coduteRup;
	private String codCup;
	private String importoE;
	private String importoS;
	private List<ContabilitaAdspRichiesteContabiliResponse> dettaglioRichieste;
	
	public Integer getProgAtto() {
		return progAtto;
	}
	
	public void setProgAtto(Integer progAtto) {
		this.progAtto = progAtto;
	}
	
	public String getkStato() {
		return kStato;
	}
	
	public void setkStato(String kStato) {
		this.kStato = kStato;
	}
	
	public String getOggettoAt() {
		return oggettoAt;
	}
	
	public void setOggettoAt(String oggettoAt) {
		this.oggettoAt = oggettoAt;
	}
	
	public String getCoduteRup() {
		return coduteRup;
	}
	
	public void setCoduteRup(String coduteRup) {
		this.coduteRup = coduteRup;
	}
	
	public String getCodCup() {
		return codCup;
	}
	
	public void setCodCup(String codCup) {
		this.codCup = codCup;
	}
	
	public String getImportoE() {
		return importoE;
	}
	
	public void setImportoE(String importoE) {
		this.importoE = importoE;
	}
	
	public String getImportoS() {
		return importoS;
	}
	
	public void setImportoS(String importoS) {
		this.importoS = importoS;
	}

	public List<ContabilitaAdspRichiesteContabiliResponse> getDettaglioRichieste() {
		return dettaglioRichieste;
	}

	public void setDettaglioRichieste(List<ContabilitaAdspRichiesteContabiliResponse> dettaglioRichieste) {
		this.dettaglioRichieste = dettaglioRichieste;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspDatiAttoResponse [progAtto=" + progAtto + ", kStato=" + kStato + ", oggettoAt="
				+ oggettoAt + ", coduteRup=" + coduteRup + ", codCup=" + codCup + ", importoE=" + importoE + ", importoS=" + importoS + ", dettaglioRichieste=" + dettaglioRichieste
				+ "]";
	}
	
}
