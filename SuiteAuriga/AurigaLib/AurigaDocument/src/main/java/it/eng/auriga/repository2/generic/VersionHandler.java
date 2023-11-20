/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.function.bean.FindElenchiAlbiResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.lucene.LuceneParameterFilter;

import java.math.BigDecimal;
import java.sql.Connection;

public interface VersionHandler {

	// costante per applicazione in caso di login interna
	public static final String _CNOME_APPLICAZIONE = "AURIGA";

	/*****************************************************************************************************
	 * STEP VERSION
	 *****************************************************************************************************/

	/**
	 * 
	 * @param codApplicazione
	 * @param codEnte
	 * @param userId
	 * @param oldVersioneDoc
	 * @param idDoc
	 * @param nomeSource
	 * @param noteTxt
	 * @return
	 * @throws VersionHandlerException
	 */
	// public AbstractDocumentInfo stepVersion(Connection conn, String token, String userIdLavoro, java.lang.Long idDoc,
	// AbstractDocumentInfo source) throws VersionHandlerException;

	/*****************************************************************************************************
	 * LOGIN
	 *****************************************************************************************************/

	public String[] externalLogin(Connection con, String userId, String password, String extAppl, String istanzaAppl, String dbSchema) throws VersionHandlerException;

	public String[] internalLogin(Connection con, String userId, String password, String tipoDominio, String idDominio, String dbSchema) throws VersionHandlerException;
    
	/*****************************************************************************************************
	 * API DI RICERCA
	 *****************************************************************************************************/

	public Object[] findRepositoryObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String flgUdFolder, Long idFolderSearchIn, String flgSubfoderSearchIn, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String flagTipoRicerca, String finalita, Integer overflowLimitIn)
			throws VersionHandlerException;

	public Object[] findOrgStructureObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] flgObjectTypes, Integer idUoSearchIn, String flgSubUoSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String finalita, String idUd, Integer overflowLimitIn, String tyobjxfinalitain, String idobjxfinalitain, LuceneParameterFilter filter)
			throws VersionHandlerException;

	public Object[] findTitolarioObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String[] flgObjectTypes, Integer searchAllTerms, Integer idClSearchIO, String flgSubClSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLim, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter)
			throws VersionHandlerException;

	public Object[] findProcessObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String advancedFilters, String customFilters, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer online, String colsToReturn, String attoriToReturn,
			String attrCustomToReturn, String finalita) throws VersionHandlerException;
	
	public String getMatchbyindexerin(FindRepositoryObjectBean pBeanIn,AurigaLoginBean lAurigaLoginBean)throws VersionHandlerException;
	public Object[] findRubricaObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] filterObjType, String matchByIndexerIn, String flgIndexerOverflowIn, String flg2ndCallIn, String denominazioneIO,
			Integer FlgInclAltreDenomIO, Integer FlgInclDenomStoricheIO, String CFIO, String PIVAIO, String FlgNotCodTipiSottoTipiIn,
			String FlgFisicaGiuridicaIn, String ListaCodTipiSottoTipiIO, String eMailIO, String CodRapidoIO, String CIProvSoggIO, Integer FlgSoloVldIO,
			String TsVldIO, String CodApplOwnerIO, String CodIstApplOwnerIO, Integer FlgRestrApplOwnerIO, Integer FlgCertificatiIO, Integer FlgInclAnnullatiIO,
			Integer IdSoggRubricaIO, Integer FlgInIndicePAIO, String CodAmmIPAIO, String CodAOOIPAIO, Integer IsSoggRubricaAppIO, Integer IdGruppoAppIO,
			String NomeGruppoAppIO, String StrInDenominazioneIn, String CriteriPersonalizzatiIO, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer OverFlowLimit, String FlgSenzaTot, Integer online,
			String colsToReturn, String finalita, String CodIstatComuneIndIn, String DesCittaIndIn, String restringiARubricaDiUOIn, String filtriAvanzatiIn,
			LuceneParameterFilter filter) throws VersionHandlerException;

	public FindElenchiAlbiResultBean findElenchiAlbi(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] filterObjType, String matchByIndexer, String flgIndexerOverflow, String flg2ndCall, String criteriPersonalizzati,
			String advancedFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLimit, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter,
			BigDecimal idTipoElencoAlbo) throws VersionHandlerException;
}