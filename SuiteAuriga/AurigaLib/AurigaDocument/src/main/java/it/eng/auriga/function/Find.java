/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.function.bean.ArrayBean;
import it.eng.auriga.function.bean.FindElenchiAlbiObjectBean;
import it.eng.auriga.function.bean.FindElenchiAlbiResultBean;
import it.eng.auriga.function.bean.FindOrgStructureObjectBean;
import it.eng.auriga.function.bean.FindProcessObjectBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.function.bean.FindRubricaObjectBean;
import it.eng.auriga.function.bean.FindTitolarioObjectBean;
import it.eng.auriga.function.result.AurigaResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.FilterType;
import it.eng.auriga.repository2.lucene.LuceneParameterFilter;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;

import java.util.Arrays;
import java.util.List;

@Service(name = "Find")
public class Find extends GenericFunction {

	public Find() throws Exception {
		super();
	}

	@Operation(name = "findRepositoryObject")
	public ArrayBean<Object> findRepositoryObject(AurigaLoginBean lAurigaLoginBean, FindRepositoryObjectBean pFindRepositoryObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindRepositoryObjectBean);
			Object[] pObjects = mVersionHandler.findRepositoryObject(mConnection, lAurigaLoginBean.getToken(), pFindRepositoryObjectBean.getUserIdLavoro(),
					pFindRepositoryObjectBean.getFiltroFullText(), pFindRepositoryObjectBean.getCheckAttributes(),
					pFindRepositoryObjectBean.getFormatoEstremiReg(), pFindRepositoryObjectBean.getSearchAllTerms(),
					pFindRepositoryObjectBean.getFlgUdFolder(), pFindRepositoryObjectBean.getIdFolderSearchIn(),
					pFindRepositoryObjectBean.getFlgSubfoderSearchIn(), pFindRepositoryObjectBean.getAdvancedFilters(),
					pFindRepositoryObjectBean.getCustomFilters(), pFindRepositoryObjectBean.getColsOrderBy(), pFindRepositoryObjectBean.getFlgDescOrderBy(),
					pFindRepositoryObjectBean.getFlgSenzaPaginazione(), pFindRepositoryObjectBean.getNumPagina(),
					pFindRepositoryObjectBean.getNumRighePagina(), pFindRepositoryObjectBean.getOnline(), pFindRepositoryObjectBean.getColsToReturn(),
					pFindRepositoryObjectBean.getPercorsoRicerca(), pFindRepositoryObjectBean.getFlagTipoRicerca(), pFindRepositoryObjectBean.getFinalita(),
					pFindRepositoryObjectBean.getOverflowlimitin());
			return getObjectList(pObjects);
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	@Operation(name = "findProcessObject")
	public AurigaResultBean findProcessObject(AurigaLoginBean lAurigaLoginBean, FindProcessObjectBean pFindProcessObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindProcessObjectBean);
			return new AurigaResultBean(mVersionHandler.findProcessObject(mConnection, lAurigaLoginBean.getToken(), pFindProcessObjectBean.getUserIdLavoro(),
					pFindProcessObjectBean.getFiltroFullText(), pFindProcessObjectBean.getCheckAttributes(), pFindProcessObjectBean.getFormatoEstremiReg(),
					pFindProcessObjectBean.getSearchAllTerms(), pFindProcessObjectBean.getAdvancedFilters(), pFindProcessObjectBean.getCustomFilters(),
					pFindProcessObjectBean.getColsOrderBy(), pFindProcessObjectBean.getFlgDescOrderBy(), pFindProcessObjectBean.getFlgSenzaPaginazione(),
					pFindProcessObjectBean.getNumPagina(), pFindProcessObjectBean.getNumRighePagina(), pFindProcessObjectBean.getOnline(),
					pFindProcessObjectBean.getColsToReturn(), pFindProcessObjectBean.getAttoriToReturn(), pFindProcessObjectBean.getAttrCustomToReturn(),
					pFindProcessObjectBean.getFinalita()));
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	@Operation(name = "findOrgStructureObject")
	public ArrayBean<Object> findOrgStructureObject(AurigaLoginBean lAurigaLoginBean, FindOrgStructureObjectBean pFindOrgStructureObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindOrgStructureObjectBean);
			LuceneParameterFilter filter = creaFiltroLucene(pFindOrgStructureObjectBean.getType(), pFindOrgStructureObjectBean.getValues());
			Object[] pObjects = mVersionHandler.findOrgStructureObject(mConnection, lAurigaLoginBean.getToken(), pFindOrgStructureObjectBean.getUserIdLavoro(),
					pFindOrgStructureObjectBean.getFiltroFullText(), pFindOrgStructureObjectBean.getCheckAttributes(),
					pFindOrgStructureObjectBean.getSearchAllTerms(), pFindOrgStructureObjectBean.getFlgObjectTypes(),
					pFindOrgStructureObjectBean.getIdUoSearchIn(), pFindOrgStructureObjectBean.getFlgSubUoSearchIn(),
					pFindOrgStructureObjectBean.getTsRiferimento(), pFindOrgStructureObjectBean.getAdvancedFilters(),
					pFindOrgStructureObjectBean.getCustomFilters(), pFindOrgStructureObjectBean.getColsOrderBy(),
					pFindOrgStructureObjectBean.getFlgDescOrderBy(), pFindOrgStructureObjectBean.getFlgSenzaPaginazione(),
					pFindOrgStructureObjectBean.getNumPagina(), pFindOrgStructureObjectBean.getNumRighePagina(), pFindOrgStructureObjectBean.getOnline(),
					pFindOrgStructureObjectBean.getColsToReturn(), pFindOrgStructureObjectBean.getPercorsoRicerca(), pFindOrgStructureObjectBean.getFinalita(),
					pFindOrgStructureObjectBean.getIdUd(), pFindOrgStructureObjectBean.getOverflowlimitin(), pFindOrgStructureObjectBean.getTyobjxfinalitain(), pFindOrgStructureObjectBean.getIdobjxfinalitain(), filter);
			return getObjectList(pObjects);
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	@Operation(name = "findTitolarioObject")
	public ArrayBean<Object> findTitolarioObject(AurigaLoginBean lAurigaLoginBean, FindTitolarioObjectBean pFindTitolarioObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindTitolarioObjectBean);
			LuceneParameterFilter filter = creaFiltroLucene(pFindTitolarioObjectBean.getType(), pFindTitolarioObjectBean.getValues());
			Object[] pObjects = mVersionHandler.findTitolarioObject(mConnection, lAurigaLoginBean.getToken(), pFindTitolarioObjectBean.getUserIdLavoro(),
					pFindTitolarioObjectBean.getFiltroFullText(), pFindTitolarioObjectBean.getCheckAttributes(), pFindTitolarioObjectBean.getFlgObjectTypes(),
					pFindTitolarioObjectBean.getSearchAllTerms(), pFindTitolarioObjectBean.getIdClSearchIO(), pFindTitolarioObjectBean.getFlgSubClSearchIn(),
					pFindTitolarioObjectBean.getTsRiferimento(), pFindTitolarioObjectBean.getAdvancedFilters(), pFindTitolarioObjectBean.getCustomFilters(),
					pFindTitolarioObjectBean.getColsOrderBy(), pFindTitolarioObjectBean.getFlgDescOrderBy(), pFindTitolarioObjectBean.getFlgSenzaPaginazione(),
					pFindTitolarioObjectBean.getNumPagina(), pFindTitolarioObjectBean.getNumRighePagina(), pFindTitolarioObjectBean.getOverFlowLimit(),
					pFindTitolarioObjectBean.getFlgSenzaTot(), pFindTitolarioObjectBean.getOnline(), pFindTitolarioObjectBean.getColsToReturn(),
					pFindTitolarioObjectBean.getFinalita(), filter);
			return getObjectList(pObjects);
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	@Operation(name = "findRubricaObject")
	public ArrayBean<Object> findRubricaObject(AurigaLoginBean lAurigaLoginBean, FindRubricaObjectBean pFindRubricaObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindRubricaObjectBean);
			LuceneParameterFilter filter = creaFiltroLucene(pFindRubricaObjectBean.getType(), pFindRubricaObjectBean.getValues());
			Object[] pObjects = mVersionHandler.findRubricaObject(mConnection, lAurigaLoginBean.getToken(), pFindRubricaObjectBean.getUserIdLavoro(),
					pFindRubricaObjectBean.getFiltroFullText(), pFindRubricaObjectBean.getCheckAttributes(), pFindRubricaObjectBean.getSearchAllTerms(),
					pFindRubricaObjectBean.getFilterObjectType(), pFindRubricaObjectBean.getMatchByIndexerIn(),
					pFindRubricaObjectBean.getFlgIndexerOverflowIn(), pFindRubricaObjectBean.getFlg2ndCallIn(), pFindRubricaObjectBean.getDenominazioneIO(),
					pFindRubricaObjectBean.getFlgInclAltreDenomIO(), pFindRubricaObjectBean.getFlgInclDenomStoricheIO(), pFindRubricaObjectBean.getcFIO(),
					pFindRubricaObjectBean.getpIVAIO(), pFindRubricaObjectBean.getFlgNotCodTipiSottoTipiIn(), pFindRubricaObjectBean.getFlgFisicaGiuridicaIn(),
					pFindRubricaObjectBean.getListaCodTipiSottoTipiIO(), pFindRubricaObjectBean.geteMailIO(), pFindRubricaObjectBean.getCodRapidoIO(),
					pFindRubricaObjectBean.getcIProvSoggIO(), pFindRubricaObjectBean.getFlgSoloVldIO(), pFindRubricaObjectBean.getTsVldIO(),
					pFindRubricaObjectBean.getCodApplOwnerIO(), pFindRubricaObjectBean.getCodIstApplOwnerIO(), pFindRubricaObjectBean.getFlgRestrApplOwnerIO(),
					pFindRubricaObjectBean.getFlgCertificatiIO(), pFindRubricaObjectBean.getFlgInclAnnullatiIO(), pFindRubricaObjectBean.getIdSoggRubricaIO(),
					pFindRubricaObjectBean.getFlgInIndicePAIO(), pFindRubricaObjectBean.getCodAmmIPAIO(), pFindRubricaObjectBean.getCodAOOIPAIO(),
					pFindRubricaObjectBean.getIsSoggRubricaAppIO(), pFindRubricaObjectBean.getIdGruppoAppIO(), pFindRubricaObjectBean.getNomeGruppoAppIO(),
					pFindRubricaObjectBean.getStrInDenominazioneIn(), pFindRubricaObjectBean.getCriteriPersonalizzatiIO(),
					pFindRubricaObjectBean.getColsOrderBy(), pFindRubricaObjectBean.getFlgDescOrderBy(), pFindRubricaObjectBean.getFlgSenzaPaginazione(),
					pFindRubricaObjectBean.getNumPagina(), pFindRubricaObjectBean.getNumRighePagina(), pFindRubricaObjectBean.getOverFlowLimit(),
					pFindRubricaObjectBean.getFlgSenzaTot(), pFindRubricaObjectBean.getOnline(), pFindRubricaObjectBean.getColsToReturn(),
					pFindRubricaObjectBean.getFinalita(), pFindRubricaObjectBean.getCodIstatComuneIndIn(), pFindRubricaObjectBean.getDesCittaIndIn(),
					pFindRubricaObjectBean.getRestringiARubricaDiUOIn(), pFindRubricaObjectBean.getFiltriAvanzatiIn(), filter);
			return getObjectList(pObjects);
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	@Operation(name = "findElenchiAlbi")
	public FindElenchiAlbiResultBean findElenchiAlbi(AurigaLoginBean lAurigaLoginBean, FindElenchiAlbiObjectBean pFindAlbiElenchiObjectBean) throws Exception {
		try {
			init(lAurigaLoginBean);
			chechArray(pFindAlbiElenchiObjectBean);
			LuceneParameterFilter filter = creaFiltroLucene(pFindAlbiElenchiObjectBean.getType(), pFindAlbiElenchiObjectBean.getValues());
			FindElenchiAlbiResultBean outBean = mVersionHandler.findElenchiAlbi(mConnection, lAurigaLoginBean.getToken(),
					pFindAlbiElenchiObjectBean.getUserIdLavoro(), pFindAlbiElenchiObjectBean.getFiltroFullText(),
					pFindAlbiElenchiObjectBean.getCheckAttributes(), pFindAlbiElenchiObjectBean.getSearchAllTerms(),
					pFindAlbiElenchiObjectBean.getFilterObjectType(), pFindAlbiElenchiObjectBean.getMatchByIndexer(),
					pFindAlbiElenchiObjectBean.getFlgIndexerOverflow(), pFindAlbiElenchiObjectBean.getFlg2ndCall(),
					pFindAlbiElenchiObjectBean.getCriteriPersonalizzati(), pFindAlbiElenchiObjectBean.getAdvancedFilters(),
					pFindAlbiElenchiObjectBean.getColsOrderBy(), pFindAlbiElenchiObjectBean.getFlgDescOrderBy(),
					pFindAlbiElenchiObjectBean.getFlgSenzaPaginazione(), pFindAlbiElenchiObjectBean.getNumPagina(),
					pFindAlbiElenchiObjectBean.getNumRighePagina(), pFindAlbiElenchiObjectBean.getOverFlowLimit(), pFindAlbiElenchiObjectBean.getFlgSenzaTot(),
					pFindAlbiElenchiObjectBean.getOnline(), pFindAlbiElenchiObjectBean.getColsToReturn(), pFindAlbiElenchiObjectBean.getFinalita(), filter,
					pFindAlbiElenchiObjectBean.getIdTipoElencoAlbo());
			return outBean;
		} catch (VersionHandlerException e) {
			throw buildException(e);
		} catch (Exception e) {
			throw buildException(e);
		} finally {
			close();
		}
	}

	private LuceneParameterFilter creaFiltroLucene(String type, String[] values) {
		LuceneParameterFilter lpf = new LuceneParameterFilter();
		FilterType ft = FilterType.valueOfValue(type);
		List<String> valori = Arrays.asList(values);
		lpf.setType(ft);
		lpf.setValues(valori);
		return lpf;
	}

}