/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindRubricaObjectBean implements Serializable {

	private static final long serialVersionUID = -8760783452102848371L;

	private String userIdLavoro;
	private String filtroFullText;
	private String[] checkAttributes;
	private String[] filterObjectType;
	private Integer searchAllTerms;
	private String matchByIndexerIn;
	private String flgIndexerOverflowIn;
	private String flg2ndCallIn;
	private String denominazioneIO;
	private Integer flgInclAltreDenomIO;
	private Integer flgInclDenomStoricheIO;
	private String cFIO;
	private String pIVAIO;
	private String flgFisicaGiuridicaIn;
	private String flgNotCodTipiSottoTipiIn;
	private String listaCodTipiSottoTipiIO;
	private String eMailIO;
	private String codRapidoIO;
	private String cIProvSoggIO;
	private Integer flgSoloVldIO;
	private String tsVldIO;
	private String codApplOwnerIO;
	private String codIstApplOwnerIO;
	private Integer flgRestrApplOwnerIO;
	private Integer flgCertificatiIO;
	private Integer flgInclAnnullatiIO;
	private Integer idSoggRubricaIO;
	private Integer flgInIndicePAIO;
	private String codAmmIPAIO;
	private String codAOOIPAIO;
	private Integer isSoggRubricaAppIO;
	private Integer idGruppoAppIO;
	private String nomeGruppoAppIO;
	private String strInDenominazioneIn;
	private String criteriPersonalizzatiIO;
	private String colsOrderBy;
	private String flgDescOrderBy;
	private Integer flgSenzaPaginazione;
	private Integer numPagina;
	private Integer numRighePagina;
	private Integer overFlowLimit;
	private String flgSenzaTot;
	private Integer online;
	private String colsToReturn;
	private String finalita;
	private String codIstatComuneIndIn;
	private String desCittaIndIn;
	private String type;
	private String[] values;
	private String flgSoloClientiIn;
	private String restringiARubricaDiUOIn;
	private String filtriAvanzatiIn;

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

	public String getMatchByIndexerIn() {
		return matchByIndexerIn;
	}

	public void setMatchByIndexerIn(String matchByIndexerIn) {
		this.matchByIndexerIn = matchByIndexerIn;
	}

	public String getFlgIndexerOverflowIn() {
		return flgIndexerOverflowIn;
	}

	public void setFlgIndexerOverflowIn(String flgIndexerOverflowIn) {
		this.flgIndexerOverflowIn = flgIndexerOverflowIn;
	}

	public String getFlg2ndCallIn() {
		return flg2ndCallIn;
	}

	public void setFlg2ndCallIn(String flg2ndCallIn) {
		this.flg2ndCallIn = flg2ndCallIn;
	}

	public String getDenominazioneIO() {
		return denominazioneIO;
	}

	public void setDenominazioneIO(String denominazioneIO) {
		this.denominazioneIO = denominazioneIO;
	}

	public Integer getFlgInclAltreDenomIO() {
		return flgInclAltreDenomIO;
	}

	public void setFlgInclAltreDenomIO(Integer flgInclAltreDenomIO) {
		this.flgInclAltreDenomIO = flgInclAltreDenomIO;
	}

	public Integer getFlgInclDenomStoricheIO() {
		return flgInclDenomStoricheIO;
	}

	public void setFlgInclDenomStoricheIO(Integer flgInclDenomStoricheIO) {
		this.flgInclDenomStoricheIO = flgInclDenomStoricheIO;
	}

	public String getcFIO() {
		return cFIO;
	}

	public void setcFIO(String cFIO) {
		this.cFIO = cFIO;
	}

	public String getpIVAIO() {
		return pIVAIO;
	}

	public void setpIVAIO(String pIVAIO) {
		this.pIVAIO = pIVAIO;
	}

	public String getListaCodTipiSottoTipiIO() {
		return listaCodTipiSottoTipiIO;
	}

	public void setListaCodTipiSottoTipiIO(String listaCodTipiSottoTipiIO) {
		this.listaCodTipiSottoTipiIO = listaCodTipiSottoTipiIO;
	}

	public String geteMailIO() {
		return eMailIO;
	}

	public void seteMailIO(String eMailIO) {
		this.eMailIO = eMailIO;
	}

	public String getCodRapidoIO() {
		return codRapidoIO;
	}

	public void setCodRapidoIO(String codRapidoIO) {
		this.codRapidoIO = codRapidoIO;
	}

	public String getcIProvSoggIO() {
		return cIProvSoggIO;
	}

	public void setcIProvSoggIO(String cIProvSoggIO) {
		this.cIProvSoggIO = cIProvSoggIO;
	}

	public Integer getFlgSoloVldIO() {
		return flgSoloVldIO;
	}

	public void setFlgSoloVldIO(Integer flgSoloVldIO) {
		this.flgSoloVldIO = flgSoloVldIO;
	}

	public String getTsVldIO() {
		return tsVldIO;
	}

	public void setTsVldIO(String tsVldIO) {
		this.tsVldIO = tsVldIO;
	}

	public String getCodApplOwnerIO() {
		return codApplOwnerIO;
	}

	public void setCodApplOwnerIO(String codApplOwnerIO) {
		this.codApplOwnerIO = codApplOwnerIO;
	}

	public String getCodIstApplOwnerIO() {
		return codIstApplOwnerIO;
	}

	public void setCodIstApplOwnerIO(String codIstApplOwnerIO) {
		this.codIstApplOwnerIO = codIstApplOwnerIO;
	}

	public Integer getFlgRestrApplOwnerIO() {
		return flgRestrApplOwnerIO;
	}

	public void setFlgRestrApplOwnerIO(Integer flgRestrApplOwnerIO) {
		this.flgRestrApplOwnerIO = flgRestrApplOwnerIO;
	}

	public Integer getFlgCertificatiIO() {
		return flgCertificatiIO;
	}

	public void setFlgCertificatiIO(Integer flgCertificatiIO) {
		this.flgCertificatiIO = flgCertificatiIO;
	}

	public Integer getFlgInclAnnullatiIO() {
		return flgInclAnnullatiIO;
	}

	public void setFlgInclAnnullatiIO(Integer flgInclAnnullatiIO) {
		this.flgInclAnnullatiIO = flgInclAnnullatiIO;
	}

	public Integer getIdSoggRubricaIO() {
		return idSoggRubricaIO;
	}

	public void setIdSoggRubricaIO(Integer idSoggRubricaIO) {
		this.idSoggRubricaIO = idSoggRubricaIO;
	}

	public Integer getFlgInIndicePAIO() {
		return flgInIndicePAIO;
	}

	public void setFlgInIndicePAIO(Integer flgInIndicePAIO) {
		this.flgInIndicePAIO = flgInIndicePAIO;
	}

	public String getCodAmmIPAIO() {
		return codAmmIPAIO;
	}

	public void setCodAmmIPAIO(String codAmmIPAIO) {
		this.codAmmIPAIO = codAmmIPAIO;
	}

	public String getCodAOOIPAIO() {
		return codAOOIPAIO;
	}

	public void setCodAOOIPAIO(String codAOOIPAIO) {
		this.codAOOIPAIO = codAOOIPAIO;
	}

	public Integer getIsSoggRubricaAppIO() {
		return isSoggRubricaAppIO;
	}

	public void setIsSoggRubricaAppIO(Integer isSoggRubricaAppIO) {
		this.isSoggRubricaAppIO = isSoggRubricaAppIO;
	}

	public Integer getIdGruppoAppIO() {
		return idGruppoAppIO;
	}

	public void setIdGruppoAppIO(Integer idGruppoAppIO) {
		this.idGruppoAppIO = idGruppoAppIO;
	}

	public String getNomeGruppoAppIO() {
		return nomeGruppoAppIO;
	}

	public void setNomeGruppoAppIO(String nomeGruppoAppIO) {
		this.nomeGruppoAppIO = nomeGruppoAppIO;
	}

	public String getCriteriPersonalizzatiIO() {
		return criteriPersonalizzatiIO;
	}

	public void setCriteriPersonalizzatiIO(String criteriPersonalizzatiIO) {
		this.criteriPersonalizzatiIO = criteriPersonalizzatiIO;
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

	public String getCodIstatComuneIndIn() {
		return codIstatComuneIndIn;
	}

	public void setCodIstatComuneIndIn(String codIstatComuneIndIn) {
		this.codIstatComuneIndIn = codIstatComuneIndIn;
	}

	public String getDesCittaIndIn() {
		return desCittaIndIn;
	}

	public void setDesCittaIndIn(String desCittaIndIn) {
		this.desCittaIndIn = desCittaIndIn;
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

	public String getFlgFisicaGiuridicaIn() {
		return flgFisicaGiuridicaIn;
	}

	public void setFlgFisicaGiuridicaIn(String flgFisicaGiuridicaIn) {
		this.flgFisicaGiuridicaIn = flgFisicaGiuridicaIn;
	}

	public String getFlgNotCodTipiSottoTipiIn() {
		return flgNotCodTipiSottoTipiIn;
	}

	public void setFlgNotCodTipiSottoTipiIn(String flgNotCodTipiSottoTipiIn) {
		this.flgNotCodTipiSottoTipiIn = flgNotCodTipiSottoTipiIn;
	}

	public String getStrInDenominazioneIn() {
		return strInDenominazioneIn;
	}

	public void setStrInDenominazioneIn(String strInDenominazioneIn) {
		this.strInDenominazioneIn = strInDenominazioneIn;
	}

	public String getFlgSoloClientiIn() {
		return flgSoloClientiIn;
	}

	public void setFlgSoloClientiIn(String flgSoloClientiIn) {
		this.flgSoloClientiIn = flgSoloClientiIn;
	}

	public String getRestringiARubricaDiUOIn() {
		return restringiARubricaDiUOIn;
	}

	public void setRestringiARubricaDiUOIn(String restringiARubricaDiUOIn) {
		this.restringiARubricaDiUOIn = restringiARubricaDiUOIn;
	}

	/**
	 * @return the filtriAvanzatiIn
	 */
	public String getFiltriAvanzatiIn() {
		return filtriAvanzatiIn;
	}

	/**
	 * @param filtriAvanzatiIn the filtriAvanzatiIn to set
	 */
	public void setFiltriAvanzatiIn(String filtriAvanzatiIn) {
		this.filtriAvanzatiIn = filtriAvanzatiIn;
	}

}
