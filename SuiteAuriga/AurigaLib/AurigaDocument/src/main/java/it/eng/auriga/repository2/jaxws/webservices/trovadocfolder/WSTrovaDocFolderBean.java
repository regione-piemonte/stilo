/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSTrovaDocFolderBean implements Serializable {

	private String flgUDFolder;
	private Long cercaInFolder;
	private String flgCercaInSubFolder;
	private String filtroFullText;
	private Integer flgTutteLeParole;
	private String  attributiXRicercaFT;
	private String criteriAvanzati;
	private String criteriPersonalizzati;
	private String colOrderBy;
	private String flgDescOrderBy;
	private Integer flgSenzaPaginazione;
	private Integer nroPagina;
	private Integer bachSize;
	private Integer flgBatchSearch;
	private String colToReturn;
	
	public String getFlgUDFolder() {
		return flgUDFolder;
	}
	public void setFlgUDFolder(String flgUDFolder) {
		this.flgUDFolder = flgUDFolder;
	}
	
	
	
	public String getCriteriAvanzati() {
		return criteriAvanzati;
	}
	public void setCriteriAvanzati(String criteriAvanzati) {
		this.criteriAvanzati = criteriAvanzati;
	}
	public String getCriteriPersonalizzati() {
		return criteriPersonalizzati;
	}
	public void setCriteriPersonalizzati(String criteriPersonalizzati) {
		this.criteriPersonalizzati = criteriPersonalizzati;
	}
	public String getColOrderBy() {
		return colOrderBy;
	}
	public void setColOrderBy(String colOrderBy) {
		this.colOrderBy = colOrderBy;
	}
	public String getFlgDescOrderBy() {
		return flgDescOrderBy;
	}
	public void setFlgDescOrderBy(String flgDescOrderBy) {
		this.flgDescOrderBy = flgDescOrderBy;
	}
	public String getColToReturn() {
		return colToReturn;
	}
	public void setColToReturn(String colToReturn) {
		this.colToReturn = colToReturn;
	}
	public String getFiltroFullText() {
		return filtroFullText;
	}
	public void setFiltroFullText(String filtroFullText) {
		this.filtroFullText = filtroFullText;
	}
	public Integer getNroPagina() {
		return nroPagina;
	}
	public void setNroPagina(Integer nroPagina) {
		this.nroPagina = nroPagina;
	}
	public Integer getBachSize() {
		return bachSize;
	}
	public void setBachSize(Integer bachSize) {
		this.bachSize = bachSize;
	}
	public Integer getFlgSenzaPaginazione() {
		return flgSenzaPaginazione;
	}
	public void setFlgSenzaPaginazione(Integer flgSenzaPaginazione) {
		this.flgSenzaPaginazione = flgSenzaPaginazione;
	}
	public Integer getFlgTutteLeParole() {
		return flgTutteLeParole;
	}
	public void setFlgTutteLeParole(Integer flgTutteLeParole) {
		this.flgTutteLeParole = flgTutteLeParole;
	}
	public Long getCercaInFolder() {
		return cercaInFolder;
	}
	public void setCercaInFolder(Long cercaInFolder) {
		this.cercaInFolder = cercaInFolder;
	}
	public Integer getFlgBatchSearch() {
		return flgBatchSearch;
	}
	public void setFlgBatchSearch(Integer flgBatchSearch) {
		this.flgBatchSearch = flgBatchSearch;
	}
	public String getAttributiXRicercaFT() {
		return attributiXRicercaFT;
	}
	public void setAttributiXRicercaFT(String attributiXRicercaFT) {
		this.attributiXRicercaFT = attributiXRicercaFT;
	}
	public String getFlgCercaInSubFolder() {
		return flgCercaInSubFolder;
	}
	public void setFlgCercaInSubFolder(String flgCercaInSubFolder) {
		this.flgCercaInSubFolder = flgCercaInSubFolder;
	}
    
	
	}
