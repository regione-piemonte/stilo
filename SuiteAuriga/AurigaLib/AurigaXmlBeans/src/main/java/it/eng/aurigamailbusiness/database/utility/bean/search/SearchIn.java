/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rametta
 *
 */
@XmlRootElement
public class SearchIn implements Serializable {

	private static final long serialVersionUID = 7267262706821737269L;

	@Obbligatorio
	private String idUtente;
	private String idEnteAOO;
	private List<String> classificazioniFolder;
	private List<String> folders;
	private FilterSearch filter;
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public List<String> getClassificazioniFolder() {
		return classificazioniFolder;
	}
	public void setClassificazioniFolder(List<String> classificazioniFolder) {
		this.classificazioniFolder = classificazioniFolder;
	}
	public List<String> getFolders() {
		return folders;
	}
	public void setFolders(List<String> folders) {
		this.folders = folders;
	}
	public FilterSearch getFilter() {
		return filter;
	}
	public void setFilter(FilterSearch filter) {
		this.filter = filter;
	}
	public void setIdEnteAOO(String idEnteAOO) {
		this.idEnteAOO = idEnteAOO;
	}
	public String getIdEnteAOO() {
		return idEnteAOO;
	}
	
	
}
