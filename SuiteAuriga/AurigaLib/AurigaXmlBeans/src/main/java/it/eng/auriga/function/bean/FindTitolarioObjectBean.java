/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindTitolarioObjectBean implements Serializable {

	private static final long serialVersionUID = -8760783452102848371L;
	private String userIdLavoro; 
	private String filtroFullText; 
	private String[] checkAttributes; 
	private String[] flgObjectTypes; 
	private Integer searchAllTerms; 
	private Integer idClSearchIO; 
	private String flgSubClSearchIn; 
	private String tsRiferimento; 
	private String advancedFilters; 
	private String customFilters; 
	private String colsOrderBy; 
	private String flgDescOrderBy; 
	private Integer flgSenzaPaginazione; 
	private Integer numPagina; 
	private Integer numRighePagina; 
	private Integer overFlowLimit; 
	private String flgSenzaTot; 
	private Integer online; 
	private String colsToReturn; 
	private	String finalita;
	private String type;
	private String[] values;
	
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
	public String[] getFlgObjectTypes() {
		return flgObjectTypes;
	}
	public void setFlgObjectTypes(String[] flgObjectTypes) {
		this.flgObjectTypes = flgObjectTypes;
	}
	public Integer getSearchAllTerms() {
		return searchAllTerms;
	}
	public void setSearchAllTerms(Integer searchAllTerms) {
		this.searchAllTerms = searchAllTerms;
	}
	public Integer getIdClSearchIO() {
		return idClSearchIO;
	}
	public void setIdClSearchIO(Integer idClSearchIO) {
		this.idClSearchIO = idClSearchIO;
	}
	public String getFlgSubClSearchIn() {
		return flgSubClSearchIn;
	}
	public void setFlgSubClSearchIn(String flgSubClSearchIn) {
		this.flgSubClSearchIn = flgSubClSearchIn;
	}
	public String getTsRiferimento() {
		return tsRiferimento;
	}
	public void setTsRiferimento(String tsRiferimento) {
		this.tsRiferimento = tsRiferimento;
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
	public Integer getOverFlowLimit() {
		return overFlowLimit;
	}
	public void setOverFlowLimit(Integer overFlowLimit) {
		this.overFlowLimit = overFlowLimit;
	}
	public String getFlgSenzaTot() {
		return flgSenzaTot;
	}
	public void setFlgSenzaTot(String flgSenzaTot) {
		this.flgSenzaTot = flgSenzaTot;
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
	public String getFinalita() {
		return finalita;
	}
	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	
}
