/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindRepositoryObjectBean implements Serializable {

	private static final long serialVersionUID = 7708518904757037899L;
	
	private String userIdLavoro;
	private String filtroFullText;
	private String[] checkAttributes;
	private String formatoEstremiReg;
	private Integer searchAllTerms;
	private String flgUdFolder;
	private Long idFolderSearchIn;
	private String flgSubfoderSearchIn;
	private String advancedFilters;
	private String customFilters;
	private String colsOrderBy;
	private String flgDescOrderBy;
	private Integer flgSenzaPaginazione;
	private Integer numPagina;
	private Integer numRighePagina;
	private Integer online;
	private String colsToReturn;
	private String percorsoRicerca;
	private String flagTipoRicerca;
	private String finalita;

	private Integer overflowlimitin;

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

	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public Long getIdFolderSearchIn() {
		return idFolderSearchIn;
	}

	public void setIdFolderSearchIn(Long idFolderSearchIn) {
		this.idFolderSearchIn = idFolderSearchIn;
	}

	public String getFlgSubfoderSearchIn() {
		return flgSubfoderSearchIn;
	}

	public void setFlgSubfoderSearchIn(String flgSubfoderSearchIn) {
		this.flgSubfoderSearchIn = flgSubfoderSearchIn;
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

	public String getPercorsoRicerca() {
		return percorsoRicerca;
	}

	public void setPercorsoRicerca(String percorsoRicerca) {
		this.percorsoRicerca = percorsoRicerca;
	}

	public String getFlagTipoRicerca() {
		return flagTipoRicerca;
	}

	public void setFlagTipoRicerca(String flagTipoRicerca) {
		this.flagTipoRicerca = flagTipoRicerca;
	}

	public String getFinalita() {
		return finalita;
	}

	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

	public void setUserIdLavoro(String userIdLavoro) {
		this.userIdLavoro = userIdLavoro;
	}

	public String getUserIdLavoro() {
		return userIdLavoro;
	}

	public Integer getOverflowlimitin() {
		return overflowlimitin;
	}

	public void setOverflowlimitin(Integer overflowlimitin) {
		this.overflowlimitin = overflowlimitin;
	}

}
