/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttoConFlussoWFXmlBean {

	@NumeroColonna(numero = "1")
	private String idTipoProc;
	@NumeroColonna(numero = "2")
	private String nomeTipoProc;
	@NumeroColonna(numero = "3")
	private String idTipoFlussoActiviti;
	@NumeroColonna(numero = "4")
	private String idTipoDocProposta;
	@NumeroColonna(numero = "5")
	private String nomeTipoDocProposta;
	@NumeroColonna(numero = "6")
	private String siglaProposta;
	@NumeroColonna(numero = "7")
	private String flgDelibera;
	@NumeroColonna(numero = "8")
	private String showDirigentiProponenti;
	@NumeroColonna(numero = "9")
	private String showAssessori;
	@NumeroColonna(numero = "10")
	private String showConsiglieri;	
	@NumeroColonna(numero = "11")
	private String categoriaProposta;	
	@NumeroColonna(numero = "12")
	private String flgRichiestaFirmaDigitale;
		
	public String getIdTipoProc() {
		return idTipoProc;
	}
	public void setIdTipoProc(String idTipoProc) {
		this.idTipoProc = idTipoProc;
	}
	public String getNomeTipoProc() {
		return nomeTipoProc;
	}
	public void setNomeTipoProc(String nomeTipoProc) {
		this.nomeTipoProc = nomeTipoProc;
	}
	public String getIdTipoFlussoActiviti() {
		return idTipoFlussoActiviti;
	}
	public void setIdTipoFlussoActiviti(String idTipoFlussoActiviti) {
		this.idTipoFlussoActiviti = idTipoFlussoActiviti;
	}
	public String getIdTipoDocProposta() {
		return idTipoDocProposta;
	}
	public void setIdTipoDocProposta(String idTipoDocProposta) {
		this.idTipoDocProposta = idTipoDocProposta;
	}
	public String getNomeTipoDocProposta() {
		return nomeTipoDocProposta;
	}
	public void setNomeTipoDocProposta(String nomeTipoDocProposta) {
		this.nomeTipoDocProposta = nomeTipoDocProposta;
	}
	public String getSiglaProposta() {
		return siglaProposta;
	}
	public void setSiglaProposta(String siglaProposta) {
		this.siglaProposta = siglaProposta;
	}
	public String getFlgDelibera() {
		return flgDelibera;
	}
	public void setFlgDelibera(String flgDelibera) {
		this.flgDelibera = flgDelibera;
	}
	public String getShowDirigentiProponenti() {
		return showDirigentiProponenti;
	}
	public void setShowDirigentiProponenti(String showDirigentiProponenti) {
		this.showDirigentiProponenti = showDirigentiProponenti;
	}
	public String getShowAssessori() {
		return showAssessori;
	}
	public void setShowAssessori(String showAssessori) {
		this.showAssessori = showAssessori;
	}
	public String getShowConsiglieri() {
		return showConsiglieri;
	}
	public void setShowConsiglieri(String showConsiglieri) {
		this.showConsiglieri = showConsiglieri;
	}
	public String getCategoriaProposta() {
		return categoriaProposta;
	}
	public void setCategoriaProposta(String categoriaProposta) {
		this.categoriaProposta = categoriaProposta;
	}
	public String getFlgRichiestaFirmaDigitale() {
		return flgRichiestaFirmaDigitale;
	}
	public void setFlgRichiestaFirmaDigitale(String flgRichiestaFirmaDigitale) {
		this.flgRichiestaFirmaDigitale = flgRichiestaFirmaDigitale;
	}
	
}
