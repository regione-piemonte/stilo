/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class ModelloDispositivoXmlOutBean {

	@XmlVariabile(nome = "ModelloDispositivo.DesDirezioneProponente", tipo = TipoVariabile.SEMPLICE)
	private String modelloDispositivoDesDirezioneProponente;
	
	@XmlVariabile(nome = "ModelloDispositivo.FlgRespDiConcertoDiAltreDirezioni", tipo = TipoVariabile.SEMPLICE)
	private Flag modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	
	@XmlVariabile(nome = "ModelloDispositivo.DesDirezioneRespDiConcerto", tipo = TipoVariabile.SEMPLICE)
	private String modelloDispositivoDesDirezioneRespDiConcerto;
	
	@XmlVariabile(nome = "ModelloDispositivo.DesRUP", tipo = TipoVariabile.SEMPLICE)
	private String modelloDispositivoDesRUP;
	
	@XmlVariabile(nome = "ModelloDispositivo.DesRdP", tipo = TipoVariabile.SEMPLICE)
	private String modelloDispositivoDesRdP;
	
	@XmlVariabile(nome = "ModelloDispositivo.DesDirezioneRUP", tipo = TipoVariabile.SEMPLICE)
	private String modelloDispositivoDesDirezioneRUP;
	
	@XmlVariabile(nome = "ModelloDispositivo.RespPEG", tipo = TipoVariabile.LISTA)
	private List<ValueBean> modelloDispositivoResponsabiliPEG;
	
	@XmlVariabile(nome = "ModelloDispositivo.DirRespDiConcerto", tipo = TipoVariabile.LISTA)
	private List<ValueBean> modelloDispositivoDirezioniConcerto;
	
	@XmlVariabile(nome = "ModelloDispositivo.Firmatari", tipo = TipoVariabile.LISTA)
	private List<FirmatariModelloDispositivoOutBean> modelloDispositivoFirmatari;
	
	@XmlVariabile(nome = "estremiPropostaAtto", tipo = TipoVariabile.SEMPLICE)
	private String estremiPropostaAtto;
	
	@XmlVariabile(nome = "dataPropostaAtto", tipo = TipoVariabile.SEMPLICE)
	private String dataPropostaAtto;
	
	public String getModelloDispositivoDesDirezioneProponente() {
		return modelloDispositivoDesDirezioneProponente;
	}

	public void setModelloDispositivoDesDirezioneProponente(String modelloDispositivoDesDirezioneProponente) {
		this.modelloDispositivoDesDirezioneProponente = modelloDispositivoDesDirezioneProponente;
	}

	public Flag getModelloDispositivoFlgRespDiConcertoDiAltreDirezioni() {
		return modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	}
	
	public void setModelloDispositivoFlgRespDiConcertoDiAltreDirezioni(Flag modelloDispositivoFlgRespDiConcertoDiAltreDirezioni) {
		this.modelloDispositivoFlgRespDiConcertoDiAltreDirezioni = modelloDispositivoFlgRespDiConcertoDiAltreDirezioni;
	}
	
	public String getModelloDispositivoDesDirezioneRespDiConcerto() {
		return modelloDispositivoDesDirezioneRespDiConcerto;
	}
	
	public void setModelloDispositivoDesDirezioneRespDiConcerto(String modelloDispositivoDesDirezioneRespDiConcerto) {
		this.modelloDispositivoDesDirezioneRespDiConcerto = modelloDispositivoDesDirezioneRespDiConcerto;
	}

	public String getModelloDispositivoDesRUP() {
		return modelloDispositivoDesRUP;
	}

	public void setModelloDispositivoDesRUP(String modelloDispositivoDesRUP) {
		this.modelloDispositivoDesRUP = modelloDispositivoDesRUP;
	}

	public String getModelloDispositivoDesRdP() {
		return modelloDispositivoDesRdP;
	}

	public void setModelloDispositivoDesRdP(String modelloDispositivoDesRdP) {
		this.modelloDispositivoDesRdP = modelloDispositivoDesRdP;
	}
	
	public String getModelloDispositivoDesDirezioneRUP() {
		return modelloDispositivoDesDirezioneRUP;
	}

	public void setModelloDispositivoDesDirezioneRUP(String modelloDispositivoDesDirezioneRUP) {
		this.modelloDispositivoDesDirezioneRUP = modelloDispositivoDesDirezioneRUP;
	}
	
	public List<ValueBean> getModelloDispositivoResponsabiliPEG() {
		return modelloDispositivoResponsabiliPEG;
	}

	public void setModelloDispositivoResponsabiliPEG(List<ValueBean> modelloDispositivoResponsabiliPEG) {
		this.modelloDispositivoResponsabiliPEG = modelloDispositivoResponsabiliPEG;
	}

	public List<ValueBean> getModelloDispositivoDirezioniConcerto() {
		return modelloDispositivoDirezioniConcerto;
	}

	public void setModelloDispositivoDirezioniConcerto(List<ValueBean> modelloDispositivoDirezioniConcerto) {
		this.modelloDispositivoDirezioniConcerto = modelloDispositivoDirezioniConcerto;
	}

	public List<FirmatariModelloDispositivoOutBean> getModelloDispositivoFirmatari() {
		return modelloDispositivoFirmatari;
	}

	public void setModelloDispositivoFirmatari(List<FirmatariModelloDispositivoOutBean> modelloDispositivoFirmatari) {
		this.modelloDispositivoFirmatari = modelloDispositivoFirmatari;
	}

	public String getEstremiPropostaAtto() {
		return estremiPropostaAtto;
	}
	
	public void setEstremiPropostaAtto(String estremiPropostaAtto) {
		this.estremiPropostaAtto = estremiPropostaAtto;
	}

	public String getDataPropostaAtto() {
		return dataPropostaAtto;
	}
	
	public void setDataPropostaAtto(String dataPropostaAtto) {
		this.dataPropostaAtto = dataPropostaAtto;
	}
	
}
