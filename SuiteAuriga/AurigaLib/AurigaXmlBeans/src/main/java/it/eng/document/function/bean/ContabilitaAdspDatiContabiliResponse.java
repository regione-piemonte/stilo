/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class ContabilitaAdspDatiContabiliResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String stanziamentoResiduo;
	private String impAccResiduo;
	private String previsioneCassa;
	private String variazioniPosCassa;
	private String variazioniNegCassa;
	private String assestatoCassa;
	private String pagatoIncassatoComp;
	private String pagatoIncassatoRes;
	private String pagatoIncassatoTot;
	private String disponibilitaCassa;
	
	private List<ContabilitaAdspImportiCompetenzaResponse> importiCompetenza;
	
	public String getStanziamentoResiduo() {
		return stanziamentoResiduo;
	}
	
	public void setStanziamentoResiduo(String stanziamentoResiduo) {
		this.stanziamentoResiduo = stanziamentoResiduo;
	}
	
	public String getImpAccResiduo() {
		return impAccResiduo;
	}
	
	public void setImpAccResiduo(String impAccResiduo) {
		this.impAccResiduo = impAccResiduo;
	}
	
	public String getPrevisioneCassa() {
		return previsioneCassa;
	}
	
	public void setPrevisioneCassa(String previsioneCassa) {
		this.previsioneCassa = previsioneCassa;
	}
	
	public String getVariazioniPosCassa() {
		return variazioniPosCassa;
	}
	
	public void setVariazioniPosCassa(String variazioniPosCassa) {
		this.variazioniPosCassa = variazioniPosCassa;
	}
	
	public String getVariazioniNegCassa() {
		return variazioniNegCassa;
	}
	
	public void setVariazioniNegCassa(String variazioniNegCassa) {
		this.variazioniNegCassa = variazioniNegCassa;
	}
	
	public String getAssestatoCassa() {
		return assestatoCassa;
	}
	
	public void setAssestatoCassa(String assestatoCassa) {
		this.assestatoCassa = assestatoCassa;
	}
	
	public String getPagatoIncassatoComp() {
		return pagatoIncassatoComp;
	}
	
	public void setPagatoIncassatoComp(String pagatoIncassatoComp) {
		this.pagatoIncassatoComp = pagatoIncassatoComp;
	}
	
	public String getPagatoIncassatoRes() {
		return pagatoIncassatoRes;
	}
	
	public void setPagatoIncassatoRes(String pagatoIncassatoRes) {
		this.pagatoIncassatoRes = pagatoIncassatoRes;
	}
	
	public String getPagatoIncassatoTot() {
		return pagatoIncassatoTot;
	}
	
	public void setPagatoIncassatoTot(String pagatoIncassatoTot) {
		this.pagatoIncassatoTot = pagatoIncassatoTot;
	}
	
	public String getDisponibilitaCassa() {
		return disponibilitaCassa;
	}
	
	public void setDisponibilitaCassa(String disponibilitaCassa) {
		this.disponibilitaCassa = disponibilitaCassa;
	}

	public List<ContabilitaAdspImportiCompetenzaResponse> getImportiCompetenza() {
		return importiCompetenza;
	}

	public void setImportiCompetenza(List<ContabilitaAdspImportiCompetenzaResponse> importiCompetenza) {
		this.importiCompetenza = importiCompetenza;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspDatiContabiliResponse [stanziamentoResiduo=" + stanziamentoResiduo + ", impAccResiduo="
				+ impAccResiduo + ", previsioneCassa=" + previsioneCassa + ", variazioniPosCassa=" + variazioniPosCassa
				+ ", variazioniNegCassa=" + variazioniNegCassa + ", assestatoCassa=" + assestatoCassa
				+ ", pagatoIncassatoComp=" + pagatoIncassatoComp + ", pagatoIncassatoRes=" + pagatoIncassatoRes
				+ ", pagatoIncassatoTot=" + pagatoIncassatoTot + ", disponibilitaCassa=" + disponibilitaCassa
				+ ", importiCompetenza=" + importiCompetenza + "]";
	}

}
