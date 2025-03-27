/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.gestioneAtti.atti_in_iter_anno_prec.bean;

import java.math.BigDecimal;

public class AttiInIterAnnoPrecBean  {
	
	private BigDecimal nroTotaleProposteDecreto;
	private BigDecimal nroTotaleProposteDecretoConMovimentiContabili;
	private BigDecimal nroProposteDecretoInFaseIstruttoria;
	private Boolean flgAnnullaConRilevContabFaseIstrutNoValue;
	private Boolean flgAnnullaConRilevContabFaseIstrutSiValue;
	private Boolean flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab;
	private BigDecimal nroProposteDecretoInVerificaBilancio;
	private BigDecimal nroProposteDecretoInFasePerfezionamento;
    private Boolean flgAnnullaConRilevContabFasePerfezNoValue;
    private Boolean flgAnnullaConRilevContabFasePerfezSiValue;
    private Boolean flgAnnullaConRilevContabFasePerfezSiSenzaMovContab;
    private BigDecimal nroProposteRda;
    private Boolean flgProposteRdaDaAnnullare;
    
    
	public BigDecimal getNroTotaleProposteDecreto() {
		return nroTotaleProposteDecreto;
	}
	public void setNroTotaleProposteDecreto(BigDecimal nroTotaleProposteDecreto) {
		this.nroTotaleProposteDecreto = nroTotaleProposteDecreto;
	}
	public BigDecimal getNroTotaleProposteDecretoConMovimentiContabili() {
		return nroTotaleProposteDecretoConMovimentiContabili;
	}
	public void setNroTotaleProposteDecretoConMovimentiContabili(BigDecimal nroTotaleProposteDecretoConMovimentiContabili) {
		this.nroTotaleProposteDecretoConMovimentiContabili = nroTotaleProposteDecretoConMovimentiContabili;
	}
	public BigDecimal getNroProposteDecretoInFaseIstruttoria() {
		return nroProposteDecretoInFaseIstruttoria;
	}
	public void setNroProposteDecretoInFaseIstruttoria(BigDecimal nroProposteDecretoInFaseIstruttoria) {
		this.nroProposteDecretoInFaseIstruttoria = nroProposteDecretoInFaseIstruttoria;
	}
	public Boolean getFlgAnnullaConRilevContabFaseIstrutNoValue() {
		return flgAnnullaConRilevContabFaseIstrutNoValue;
	}
	public void setFlgAnnullaConRilevContabFaseIstrutNoValue(Boolean flgAnnullaConRilevContabFaseIstrutNoValue) {
		this.flgAnnullaConRilevContabFaseIstrutNoValue = flgAnnullaConRilevContabFaseIstrutNoValue;
	}
	public Boolean getFlgAnnullaConRilevContabFaseIstrutSiValue() {
		return flgAnnullaConRilevContabFaseIstrutSiValue;
	}
	public void setFlgAnnullaConRilevContabFaseIstrutSiValue(Boolean flgAnnullaConRilevContabFaseIstrutSiValue) {
		this.flgAnnullaConRilevContabFaseIstrutSiValue = flgAnnullaConRilevContabFaseIstrutSiValue;
	}
	public Boolean getFlgAnnullaConRilevContabFaseIstrutSiSenzaMovContab() {
		return flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab;
	}
	public void setFlgAnnullaConRilevContabFaseIstrutSiSenzaMovContab(
			Boolean flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab) {
		this.flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab = flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab;
	}
	public BigDecimal getNroProposteDecretoInVerificaBilancio() {
		return nroProposteDecretoInVerificaBilancio;
	}
	public void setNroProposteDecretoInVerificaBilancio(BigDecimal nroProposteDecretoInVerificaBilancio) {
		this.nroProposteDecretoInVerificaBilancio = nroProposteDecretoInVerificaBilancio;
	}
	public BigDecimal getNroProposteDecretoInFasePerfezionamento() {
		return nroProposteDecretoInFasePerfezionamento;
	}
	public void setNroProposteDecretoInFasePerfezionamento(BigDecimal nroProposteDecretoInFasePerfezionamento) {
		this.nroProposteDecretoInFasePerfezionamento = nroProposteDecretoInFasePerfezionamento;
	}
	public Boolean getFlgAnnullaConRilevContabFasePerfezNoValue() {
		return flgAnnullaConRilevContabFasePerfezNoValue;
	}
	public void setFlgAnnullaConRilevContabFasePerfezNoValue(Boolean flgAnnullaConRilevContabFasePerfezNoValue) {
		this.flgAnnullaConRilevContabFasePerfezNoValue = flgAnnullaConRilevContabFasePerfezNoValue;
	}
	public Boolean getFlgAnnullaConRilevContabFasePerfezSiValue() {
		return flgAnnullaConRilevContabFasePerfezSiValue;
	}
	public void setFlgAnnullaConRilevContabFasePerfezSiValue(Boolean flgAnnullaConRilevContabFasePerfezSiValue) {
		this.flgAnnullaConRilevContabFasePerfezSiValue = flgAnnullaConRilevContabFasePerfezSiValue;
	}
	public Boolean getFlgAnnullaConRilevContabFasePerfezSiSenzaMovContab() {
		return flgAnnullaConRilevContabFasePerfezSiSenzaMovContab;
	}
	public void setFlgAnnullaConRilevContabFasePerfezSiSenzaMovContab(
			Boolean flgAnnullaConRilevContabFasePerfezSiSenzaMovContab) {
		this.flgAnnullaConRilevContabFasePerfezSiSenzaMovContab = flgAnnullaConRilevContabFasePerfezSiSenzaMovContab;
	}
	public BigDecimal getNroProposteRda() {
		return nroProposteRda;
	}
	public void setNroProposteRda(BigDecimal nroProposteRda) {
		this.nroProposteRda = nroProposteRda;
	}
	public Boolean getFlgProposteRdaDaAnnullare() {
		return flgProposteRdaDaAnnullare;
	}
	public void setFlgProposteRdaDaAnnullare(Boolean flgProposteRdaDaAnnullare) {
		this.flgProposteRdaDaAnnullare = flgProposteRdaDaAnnullare;
	}
	
	
}
