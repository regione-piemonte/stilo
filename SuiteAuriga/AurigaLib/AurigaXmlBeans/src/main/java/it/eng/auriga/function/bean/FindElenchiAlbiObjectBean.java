/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindElenchiAlbiObjectBean implements Serializable {
	
	private static final long serialVersionUID = -8760783452102848371L;
	
	private String userIdLavoro; 	
	private String filtroFullText; 
	private String[] checkAttributes; 
	private String[] filterObjectType; 
	private Integer searchAllTerms;
	private String matchByIndexer;
	private String flgIndexerOverflow;
	private String flg2ndCall;
	private String criteriPersonalizzati; 
	private String advancedFilters;
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
	private BigDecimal idTipoElencoAlbo;
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
	public String[] getFilterObjectType() {
		return filterObjectType;
	}
	public void setFilterObjectType(String[] filterObjectType) {
		this.filterObjectType = filterObjectType;
	}
	public Integer getSearchAllTerms() {
		return searchAllTerms;
	}
	public void setSearchAllTerms(Integer searchAllTerms) {
		this.searchAllTerms = searchAllTerms;
	}
	public String getMatchByIndexer() {
		return matchByIndexer;
	}
	public void setMatchByIndexer(String matchByIndexer) {
		this.matchByIndexer = matchByIndexer;
	}
	public String getFlgIndexerOverflow() {
		return flgIndexerOverflow;
	}
	public void setFlgIndexerOverflow(String flgIndexerOverflow) {
		this.flgIndexerOverflow = flgIndexerOverflow;
	}
	public String getFlg2ndCall() {
		return flg2ndCall;
	}
	public void setFlg2ndCall(String flg2ndCall) {
		this.flg2ndCall = flg2ndCall;
	}
	public String getCriteriPersonalizzati() {
		return criteriPersonalizzati;
	}
	public void setCriteriPersonalizzati(String criteriPersonalizzati) {
		this.criteriPersonalizzati = criteriPersonalizzati;
	}
	public String getAdvancedFilters() {
		return advancedFilters;
	}
	public void setAdvancedFilters(String advancedFilters) {
		this.advancedFilters = advancedFilters;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getIdTipoElencoAlbo() {
		return idTipoElencoAlbo;
	}
	public void setIdTipoElencoAlbo(BigDecimal idTipoElencoAlbo) {
		this.idTipoElencoAlbo = idTipoElencoAlbo;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}	
	
}
