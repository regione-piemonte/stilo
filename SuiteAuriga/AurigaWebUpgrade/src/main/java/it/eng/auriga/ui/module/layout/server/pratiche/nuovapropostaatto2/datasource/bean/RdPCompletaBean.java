/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

public class RdPCompletaBean {
	
	private String responsabileDiProcedimento; 
	private String responsabileDiProcedimentoFromLoadDett;
	private String codUoResponsabileDiProcedimento;
	private String desResponsabileDiProcedimento;
	private Boolean flgResponsabileDiProcedimentoFirmatario;
	private Boolean flgRdPAncheRUP;

	public String getResponsabileDiProcedimento() {
		return responsabileDiProcedimento;
	}

	public void setResponsabileDiProcedimento(String responsabileDiProcedimento) {
		this.responsabileDiProcedimento = responsabileDiProcedimento;
	}

	public String getResponsabileDiProcedimentoFromLoadDett() {
		return responsabileDiProcedimentoFromLoadDett;
	}

	public void setResponsabileDiProcedimentoFromLoadDett(String responsabileDiProcedimentoFromLoadDett) {
		this.responsabileDiProcedimentoFromLoadDett = responsabileDiProcedimentoFromLoadDett;
	}

	public String getCodUoResponsabileDiProcedimento() {
		return codUoResponsabileDiProcedimento;
	}

	public void setCodUoResponsabileDiProcedimento(String codUoResponsabileDiProcedimento) {
		this.codUoResponsabileDiProcedimento = codUoResponsabileDiProcedimento;
	}

	public String getDesResponsabileDiProcedimento() {
		return desResponsabileDiProcedimento;
	}

	public void setDesResponsabileDiProcedimento(String desResponsabileDiProcedimento) {
		this.desResponsabileDiProcedimento = desResponsabileDiProcedimento;
	}

	public Boolean getFlgResponsabileDiProcedimentoFirmatario() {
		return flgResponsabileDiProcedimentoFirmatario;
	}

	public void setFlgResponsabileDiProcedimentoFirmatario(Boolean flgResponsabileDiProcedimentoFirmatario) {
		this.flgResponsabileDiProcedimentoFirmatario = flgResponsabileDiProcedimentoFirmatario;
	}

	public Boolean getFlgRdPAncheRUP() {
		return flgRdPAncheRUP;
	}

	public void setFlgRdPAncheRUP(Boolean flgRdPAncheRUP) {
		this.flgRdPAncheRUP = flgRdPAncheRUP;
	}
	
}
