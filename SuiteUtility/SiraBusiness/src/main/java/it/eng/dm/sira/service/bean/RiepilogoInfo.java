package it.eng.dm.sira.service.bean;

import java.util.Date;

public class RiepilogoInfo {
	
	private String numeroProcedimento;

	private String etcihettaProcedimento;

	private String desResponsabile;

	private String desRefAmministrativo;

	private String desRefTecnico;
	
	private Date dataAvvio;

	private Date dataFineTermini;

	public String getNumeroProcedimento() {
		return numeroProcedimento;
	}

	public void setNumeroProcedimento(String numeroProcedimento) {
		this.numeroProcedimento = numeroProcedimento;
	}

	public String getEtcihettaProcedimento() {
		return etcihettaProcedimento;
	}

	public void setEtcihettaProcedimento(String etcihettaProcedimento) {
		this.etcihettaProcedimento = etcihettaProcedimento;
	}

	public String getDesResponsabile() {
		return desResponsabile;
	}

	public void setDesResponsabile(String desResponsabile) {
		this.desResponsabile = desResponsabile;
	}

	public String getDesRefAmministrativo() {
		return desRefAmministrativo;
	}

	public void setDesRefAmministrativo(String desRefAmministrativo) {
		this.desRefAmministrativo = desRefAmministrativo;
	}

	public String getDesRefTecnico() {
		return desRefTecnico;
	}

	public void setDesRefTecnico(String desRefTecnico) {
		this.desRefTecnico = desRefTecnico;
	}

	public Date getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(Date dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public Date getDataFineTermini() {
		return dataFineTermini;
	}

	public void setDataFineTermini(Date dataFineTermini) {
		this.dataFineTermini = dataFineTermini;
	}

}
