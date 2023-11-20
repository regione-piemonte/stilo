/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindProcessObjectBean implements Serializable{

	private static final long serialVersionUID = 8876393570621283594L;
	private String userIdLavoro; 
	private String filtroFullText;
	private String[] checkAttributes; 
	private String formatoEstremiReg;
	private Integer searchAllTerms; 
	private String advancedFilters;
	private String customFilters; 
	private String colsOrderBy; 
	private String flgDescOrderBy;
	private Integer flgSenzaPaginazione; 
	private Integer numPagina;
	private Integer numRighePagina; 
	private Integer online; 
	private String colsToReturn;
	private String attoriToReturn; 
	private String attrCustomToReturn; 
	private String finalita;
	public String getUserIdLavoro() {
		return userIdLavoro;
	}
	public void setUserIdLavoro(String userIdLavoro) {
		this.userIdLavoro = userIdLavoro;
	}
	public String getFiltroFullText() {
		return filtroFullText;
	}
	public void setFiltroFullText(String filtroFullText) {
		this.filtroFullText = filtroFullText;
	}
	public String[] getCheckAttributes() {
		return checkAttributes;
	}
	public void setCheckAttributes(String[] checkAttributes) {
		this.checkAttributes = checkAttributes;
	}
	public String getFormatoEstremiReg() {
		return formatoEstremiReg;
	}
	public void setFormatoEstremiReg(String formatoEstremiReg) {
		this.formatoEstremiReg = formatoEstremiReg;
	}
	public Integer getSearchAllTerms() {
		return searchAllTerms;
	}
	public void setSearchAllTerms(Integer searchAllTerms) {
		this.searchAllTerms = searchAllTerms;
	}
	public String getAdvancedFilters() {
		return advancedFilters;
	}
	public void setAdvancedFilters(String advancedFilters) {
		this.advancedFilters = advancedFilters;
	}
	public String getCustomFilters() {
		return customFilters;
	}
	public void setCustomFilters(String customFilters) {
		this.customFilters = customFilters;
	}
	public String getColsOrderBy() {
		return colsOrderBy;
	}
	public void setColsOrderBy(String colsOrderBy) {
		this.colsOrderBy = colsOrderBy;
	}
	public String getFlgDescOrderBy() {
		return flgDescOrderBy;
	}
	public void setFlgDescOrderBy(String flgDescOrderBy) {
		this.flgDescOrderBy = flgDescOrderBy;
	}
	public Integer getFlgSenzaPaginazione() {
		return flgSenzaPaginazione;
	}
	public void setFlgSenzaPaginazione(Integer flgSenzaPaginazione) {
		this.flgSenzaPaginazione = flgSenzaPaginazione;
	}
	public Integer getNumPagina() {
		return numPagina;
	}
	public void setNumPagina(Integer numPagina) {
		this.numPagina = numPagina;
	}
	public Integer getNumRighePagina() {
		return numRighePagina;
	}
	public void setNumRighePagina(Integer numRighePagina) {
		this.numRighePagina = numRighePagina;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public String getColsToReturn() {
		return colsToReturn;
	}
	public void setColsToReturn(String colsToReturn) {
		this.colsToReturn = colsToReturn;
	}
	public String getAttoriToReturn() {
		return attoriToReturn;
	}
	public void setAttoriToReturn(String attoriToReturn) {
		this.attoriToReturn = attoriToReturn;
	}
	public String getAttrCustomToReturn() {
		return attrCustomToReturn;
	}
	public void setAttrCustomToReturn(String attrCustomToReturn) {
		this.attrCustomToReturn = attrCustomToReturn;
	}
	public String getFinalita() {
		return finalita;
	}
	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

}
