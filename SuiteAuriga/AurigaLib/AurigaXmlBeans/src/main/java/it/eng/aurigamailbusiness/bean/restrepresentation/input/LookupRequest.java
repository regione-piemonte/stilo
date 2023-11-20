/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMetadataOrderingList;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMetadataList;
import it.eng.aurigamailbusiness.bean.restrepresentation.SezioneCacheFiltriTrovaEmail;

@XmlRootElement(name="richiestaTrovaEmail")
public class LookupRequest extends MutualInput {
	
	@XmlElement(name="filtri", required=true)
	private SezioneCacheFiltriTrovaEmail filtro;
	
	@XmlElement(name="colonne")
	private EmailMetadataList colonne;
	
	@XmlElement(name="ordinamento")
	private EmailMetadataOrderingList ordinamento;
	
	//------------------------------------------------------------
	@XmlElement(name="flagPaginazione", defaultValue="false")
	private boolean flagPaginazione;
	@XmlElement(name="pagina", defaultValue="1")
	private Integer pagina; 
	@XmlElement(name="elementiPerPagina", defaultValue="10")
	private Integer elementiPerPagina;
	//------------------------------------------------------------

	public SezioneCacheFiltriTrovaEmail getFiltro() {
		return filtro;
	}
	public void setFiltro(SezioneCacheFiltriTrovaEmail filtro) {
		this.filtro = filtro;
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
	
	public EmailMetadataList getColonne() {
		return colonne;
	}
	public void setColonne(EmailMetadataList colonne) {
		this.colonne = colonne;
	}
	
	public EmailMetadataOrderingList getOrdinamento() {
		return ordinamento;
	}
	public void setOrdinamento(EmailMetadataOrderingList ordinamento) {
		this.ordinamento = ordinamento;
	}
	
}//LookupRequest
