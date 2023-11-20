/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="valoreDizionario")
public class RigaListaXMLTrovaValoriDizionario {
	
	 @NumeroColonna(numero="1")
	 @XmlElement(name="valore")
	 private String valore;
	 
	 @NumeroColonna(numero="2")
	 @XmlElement(name="codice")
	 private String codice;
	 
	 @NumeroColonna(numero="3") @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	 @XmlElement(name="dataInizioValidita")
	 private Date dataInizioValidita;
	 
	 @NumeroColonna(numero="4") @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	 @XmlElement(name="dataFineValidita")
	 private Date dataFineValidita;
	 
	 @NumeroColonna(numero="5")
	 @XmlElement(name="significato")
	 private String significato;
	 
	 @NumeroColonna(numero="6")
	 @XmlElement(name="flagValoreRiservato")
	 private Integer flagValoreRiservato;
	 
	 @NumeroColonna(numero="7")
	 @XmlElement(name="vincolo")
	 private String vincolo;
	 
	 @NumeroColonna(numero="8")
	 @XmlElement(name="flagValoreValido")
	 private Integer flagValoreValido;
	 
	 @NumeroColonna(numero="9")
	 @XmlElement(name="flagValoreModificabile")
	 private Integer flagValoreModificabile;
	 
	 @NumeroColonna(numero="10")
	 @XmlElement(name="flagValoreEliminabile")
	 private Integer flagValoreEliminabile;
	 
	 @NumeroColonna(numero="11")
	 @XmlElement(name="denominazioneUO")
	 private String denominazioneUO;
	 
	 @NumeroColonna(numero="12")
	 @XmlElement(name="flagValoreVisibileAlleSottoUO")
	 private Integer flagValoreVisibileAlleSottoUO;
	 
	 @NumeroColonna(numero="13")
	 @XmlElement(name="flagValoreGestibileDalleSottoUO")
	 private Integer flagValoreGestibileDalleSottoUO;
	 

	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getSignificato() {
		return significato;
	}
	public void setSignificato(String significato) {
		this.significato = significato;
	}

	public Integer getFlagValoreRiservato() {
		return flagValoreRiservato;
	}
	public void setFlagValoreRiservato(Integer flagValoreRiservato) {
		this.flagValoreRiservato = flagValoreRiservato;
	}

	public String getVincolo() {
		return vincolo;
	}
	public void setVincolo(String vincolo) {
		this.vincolo = vincolo;
	}

	public Integer getFlagValoreValido() {
		return flagValoreValido;
	}
	public void setFlagValoreValido(Integer flagValoreValido) {
		this.flagValoreValido = flagValoreValido;
	}

	public Integer getFlagValoreModificabile() {
		return flagValoreModificabile;
	}
	public void setFlagValoreModificabile(Integer flagValoreModificabile) {
		this.flagValoreModificabile = flagValoreModificabile;
	}

	public Integer getFlagValoreEliminabile() {
		return flagValoreEliminabile;
	}
	public void setFlagValoreEliminabile(Integer flagValoreEliminabile) {
		this.flagValoreEliminabile = flagValoreEliminabile;
	}
	public String getDenominazioneUO() {
		return denominazioneUO;
	}
	public void setDenominazioneUO(String denominazioneUO) {
		this.denominazioneUO = denominazioneUO;
	}

	public Integer getFlagValoreVisibileAlleSottoUO() {
		return flagValoreVisibileAlleSottoUO;
	}
	public void setFlagValoreVisibileAlleSottoUO(Integer flagValoreVisibileAlleSottoUO) {
		this.flagValoreVisibileAlleSottoUO = flagValoreVisibileAlleSottoUO;
	}

	public Integer getFlagValoreGestibileDalleSottoUO() {
		return flagValoreGestibileDalleSottoUO;
	}
	public void setFlagValoreGestibileDalleSottoUO(Integer flagValoreGestibileDalleSottoUO) {
		this.flagValoreGestibileDalleSottoUO = flagValoreGestibileDalleSottoUO;
	}
	 

}
