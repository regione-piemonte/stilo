/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="richiestaTrovaValoriDizionario")
public class DictionaryLookupRequest extends MutualInput {
	
	@XmlElement(name="voceDizionario", required=true)
	private String voceDizionario;
	@XmlElement(name="codiceContenuto")
	private String codiceContenuto;
	@XmlElement(name="valoreContenuto")
	private String valoreContenuto;
	//------------------------------------------------------------
	@XmlElement(name="flagPaginazione", defaultValue="false")
	private boolean flagPaginazione;
	@XmlElement(name="pagina", defaultValue="1")
	private Integer pagina; 
	@XmlElement(name="elementiPerPagina", defaultValue="10")
	private Integer elementiPerPagina;
	//------------------------------------------------------------
	
	public String getVoceDizionario() {
		return voceDizionario;
	}
	public void setVoceDizionario(String voceDizionario) {
		this.voceDizionario = voceDizionario;
	}
	
	public String getCodiceContenuto() {
		return codiceContenuto;
	}
	public void setCodiceContenuto(String codiceContenuto) {
		this.codiceContenuto = codiceContenuto;
	}
	
	public String getValoreContenuto() {
		return valoreContenuto;
	}
	public void setValoreContenuto(String valoreContenuto) {
		this.valoreContenuto = valoreContenuto;
	}
	
	public boolean isFlagPaginazione() {
		return flagPaginazione;
	}
	public void setFlagPaginazione(boolean flagPaginazione) {
		this.flagPaginazione = flagPaginazione;
	}
	
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	
	public Integer getElementiPerPagina() {
		return elementiPerPagina;
	}
	public void setElementiPerPagina(Integer elementiPerPagina) {
		this.elementiPerPagina = elementiPerPagina;
	}
	
	
}//DictionaryLookupRequest
