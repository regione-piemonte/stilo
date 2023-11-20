/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ContabilitaAdspImportiCompetenzaResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer anno; 
	private String previsioneInizialeComp;
	private String variazioniPosDef;
	private String variazioniNegDef;
	private String previsioneAssestataComp;
	private String variazioniPosProv;
	private String variazioniNegProv;
	private String previsioneAssestataCompVarProv;
	private String impAccDefinitivo;
	private String impAccPrenotato;
	private String richiesteNonPrenotate;
	private String disponibilitaImpAcc;
	private String disponibilitaImpAccVarProv;
	private String disponibilitaMinimaUsabile;
	private String previsioneInizialeFPV;
	private String variazioniPosFPV;
	private String variazioniNegFPV;
	private String previsioneAssestataFPV;
	
	public Integer getAnno() {
		return anno;
	}
	
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	
	public String getPrevisioneInizialeComp() {
		return previsioneInizialeComp;
	}
	
	public void setPrevisioneInizialeComp(String previsioneInizialeComp) {
		this.previsioneInizialeComp = previsioneInizialeComp;
	}
	
	public String getVariazioniPosDef() {
		return variazioniPosDef;
	}
	
	public void setVariazioniPosDef(String variazioniPosDef) {
		this.variazioniPosDef = variazioniPosDef;
	}
	
	public String getVariazioniNegDef() {
		return variazioniNegDef;
	}
	
	public void setVariazioniNegDef(String variazioniNegDef) {
		this.variazioniNegDef = variazioniNegDef;
	}
	
	public String getPrevisioneAssestataComp() {
		return previsioneAssestataComp;
	}
	
	public void setPrevisioneAssestataComp(String previsioneAssestataComp) {
		this.previsioneAssestataComp = previsioneAssestataComp;
	}
	
	public String getVariazioniPosProv() {
		return variazioniPosProv;
	}
	
	public void setVariazioniPosProv(String variazioniPosProv) {
		this.variazioniPosProv = variazioniPosProv;
	}
	
	public String getVariazioniNegProv() {
		return variazioniNegProv;
	}
	
	public void setVariazioniNegProv(String variazioniNegProv) {
		this.variazioniNegProv = variazioniNegProv;
	}
	
	public String getPrevisioneAssestataCompVarProv() {
		return previsioneAssestataCompVarProv;
	}
	
	public void setPrevisioneAssestataCompVarProv(String previsioneAssestataCompVarProv) {
		this.previsioneAssestataCompVarProv = previsioneAssestataCompVarProv;
	}
	
	public String getImpAccDefinitivo() {
		return impAccDefinitivo;
	}
	
	public void setImpAccDefinitivo(String impAccDefinitivo) {
		this.impAccDefinitivo = impAccDefinitivo;
	}
	
	public String getImpAccPrenotato() {
		return impAccPrenotato;
	}
	
	public void setImpAccPrenotato(String impAccPrenotato) {
		this.impAccPrenotato = impAccPrenotato;
	}
	
	public String getRichiesteNonPrenotate() {
		return richiesteNonPrenotate;
	}
	
	public void setRichiesteNonPrenotate(String richiesteNonPrenotate) {
		this.richiesteNonPrenotate = richiesteNonPrenotate;
	}
	
	public String getDisponibilitaImpAcc() {
		return disponibilitaImpAcc;
	}
	
	public void setDisponibilitaImpAcc(String disponibilitaImpAcc) {
		this.disponibilitaImpAcc = disponibilitaImpAcc;
	}
	
	public String getDisponibilitaImpAccVarProv() {
		return disponibilitaImpAccVarProv;
	}
	
	public void setDisponibilitaImpAccVarProv(String disponibilitaImpAccVarProv) {
		this.disponibilitaImpAccVarProv = disponibilitaImpAccVarProv;
	}
	
	public String getDisponibilitaMinimaUsabile() {
		return disponibilitaMinimaUsabile;
	}
	
	public void setDisponibilitaMinimaUsabile(String disponibilitaMinimaUsabile) {
		this.disponibilitaMinimaUsabile = disponibilitaMinimaUsabile;
	}
	
	public String getPrevisioneInizialeFPV() {
		return previsioneInizialeFPV;
	}
	
	public void setPrevisioneInizialeFPV(String previsioneInizialeFPV) {
		this.previsioneInizialeFPV = previsioneInizialeFPV;
	}
	
	public String getVariazioniPosFPV() {
		return variazioniPosFPV;
	}
	
	public void setVariazioniPosFPV(String variazioniPosFPV) {
		this.variazioniPosFPV = variazioniPosFPV;
	}
	
	public String getVariazioniNegFPV() {
		return variazioniNegFPV;
	}
	
	public void setVariazioniNegFPV(String variazioniNegFPV) {
		this.variazioniNegFPV = variazioniNegFPV;
	}
	
	public String getPrevisioneAssestataFPV() {
		return previsioneAssestataFPV;
	}
	
	public void setPrevisioneAssestataFPV(String previsioneAssestataFPV) {
		this.previsioneAssestataFPV = previsioneAssestataFPV;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspImportiCompetenzaResponse [anno=" + anno + ", previsioneInizialeComp="
				+ previsioneInizialeComp + ", variazioniPosDef=" + variazioniPosDef + ", variazioniNegDef="
				+ variazioniNegDef + ", previsioneAssestataComp=" + previsioneAssestataComp + ", variazioniPosProv="
				+ variazioniPosProv + ", variazioniNegProv=" + variazioniNegProv + ", previsioneAssestataCompVarProv="
				+ previsioneAssestataCompVarProv + ", impAccDefinitivo=" + impAccDefinitivo + ", impAccPrenotato="
				+ impAccPrenotato + ", richiesteNonPrenotate=" + richiesteNonPrenotate + ", disponibilitaImpAcc="
				+ disponibilitaImpAcc + ", disponibilitaImpAccVarProv=" + disponibilitaImpAccVarProv
				+ ", disponibilitaMinimaUsabile=" + disponibilitaMinimaUsabile + ", previsioneInizialeFPV="
				+ previsioneInizialeFPV + ", variazioniPosFPV=" + variazioniPosFPV + ", variazioniNegFPV="
				+ variazioniNegFPV + ", previsioneAssestataFPV=" + previsioneAssestataFPV + "]";
	}
	
}
