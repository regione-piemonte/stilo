/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DirRespRegTecnicaBean {

	@NumeroColonna(numero = "1")
	private String idSV;
	
	@NumeroColonna(numero = "2")
	private String codUO;
			
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	@NumeroColonna(numero = "4")
	private Boolean flgFirmatario;
	
	@NumeroColonna(numero = "5")
	private String cognomeNome;
	
	@NumeroColonna(numero = "7")
	private String idSVSostituto;
	
	@NumeroColonna(numero = "8")
	private String nriLivelliSostituto;
	
	@NumeroColonna(numero = "9")
	private String desScrivaniaSostituto;
	
	@NumeroColonna(numero = "10")
	private String provvedimentoSostituzione;
	
	@NumeroColonna(numero = "11")
	private Boolean flgModificabileSostituto;
	

	public String getIdSV() {
		return idSV;
	}

	public void setIdSV(String idSV) {
		this.idSV = idSV;
	}

	public String getCodUO() {
		return codUO;
	}

	public void setCodUO(String codUO) {
		this.codUO = codUO;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getFlgFirmatario() {
		return flgFirmatario;
	}

	public void setFlgFirmatario(Boolean flgFirmatario) {
		this.flgFirmatario = flgFirmatario;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getIdSVSostituto() {
		return idSVSostituto;
	}

	public void setIdSVSostituto(String idSVSostituto) {
		this.idSVSostituto = idSVSostituto;
	}

	public String getNriLivelliSostituto() {
		return nriLivelliSostituto;
	}

	public void setNriLivelliSostituto(String nriLivelliSostituto) {
		this.nriLivelliSostituto = nriLivelliSostituto;
	}

	public String getDesScrivaniaSostituto() {
		return desScrivaniaSostituto;
	}

	public void setDesScrivaniaSostituto(String desScrivaniaSostituto) {
		this.desScrivaniaSostituto = desScrivaniaSostituto;
	}

	public String getProvvedimentoSostituzione() {
		return provvedimentoSostituzione;
	}

	public void setProvvedimentoSostituzione(String provvedimentoSostituzione) {
		this.provvedimentoSostituzione = provvedimentoSostituzione;
	}

	public Boolean getFlgModificabileSostituto() {
		return flgModificabileSostituto;
	}

	public void setFlgModificabileSostituto(Boolean flgModificabileSostituto) {
		this.flgModificabileSostituto = flgModificabileSostituto;
	}

}
