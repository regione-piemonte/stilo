/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.opensagres.xdocreport.document.json.JSONArray;
import fr.opensagres.xdocreport.document.json.JSONObject;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.LoadComboIndirizzoSoggettoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MezzoTrasmissioneDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ModelloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkLoadComboDmfn_load_combo;
import it.eng.client.SalvataggioFile;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.core.business.TPagingList;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean.Direction;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;

@Datasource(id = "ModelliDataSource")
public class ModelliDataSource extends AbstractFetchDataSource<ModelloBean> {

	private static Logger mLogger = Logger.getLogger(ModelliDataSource.class);

	@Override
	public PaginatorBean<ModelloBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderByBeanList) throws Exception {

		PaginatorBean<ModelloBean> paginatorBean = new PaginatorBean<ModelloBean>();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		List<ModelloBean> listaPrefConDominio = fetchConDominio(criteria, startRow, endRow, orderByBeanList, loginBean);
		List<ModelloBean> listaSenzaDominio   = fetchSenzaDominio(criteria, startRow, endRow, orderByBeanList, loginBean);
		
		List<ModelloBean> data = new ArrayList<ModelloBean>();
		if(listaPrefConDominio != null) {
			data.addAll(listaPrefConDominio);
		}
		if(listaSenzaDominio != null) {
			data.addAll(listaSenzaDominio);
		}
		
		boolean isControllaValiditaIndirizzoMezzoTrasm = getExtraparams().get("isControllaValiditaIndirizzoMezzoTrasm") != null && "1".equals(getExtraparams().get("isControllaValiditaIndirizzoMezzoTrasm"));
		
		if(isControllaValiditaIndirizzoMezzoTrasm) {
			data = rimuoviIndirizzoMezzoTrasmissioneNonValido(data);
		}
		
		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());

		return paginatorBean;
	}

	private List<ModelloBean> fetchConDominio(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList, AurigaLoginBean loginBean) {
		
		String flgIncludiPubbl = "2";
		String idUo = null;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName() != null && criterion.getFieldName().equals("flgIncludiPubbl")) {
					flgIncludiPubbl = (String) criterion.getValue();
				} else if (criterion.getFieldName() != null && criterion.getFieldName().equals("idUo")) {
					idUo = (String) criterion.getValue();
				}
			}
		}

		if (idUo != null && idUo.startsWith("UO")) {
			idUo = idUo.substring(2);
		}

		String userId = getExtraparams().get("userId");
		
		String idDominio = null;
		if (StringUtils.isBlank(userId)) {
			AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
		
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
			if ("0".equalsIgnoreCase(flgIncludiPubbl) && StringUtils.isBlank(idUo)) {
				if (StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			} else if ("1".equalsIgnoreCase(flgIncludiPubbl)) {
				userId = "PUBLIC." + idDominio;
			} else if (StringUtils.isNotBlank(idUo)) {
				userId = "UO." + idUo;
			}
		}

		String prefKey = getExtraparams().get("prefKey") + (StringUtils.isNotBlank(idDominio) ? "."+idDominio : "");

		String prefName = getExtraparams().get("prefName") != null ? getExtraparams().get("prefName").trim() : null;
		if (StringUtils.isBlank(prefName)) {
			if (criteria != null && criteria.getCriteria() != null) {
				for (Criterion criterion : criteria.getCriteria()) {
					if (criterion.getFieldName() != null && criterion.getFieldName().equals("prefName")) {
						prefName = ((String) criterion.getValue()).trim();
						break;
					}
				}
			}
		}

		String strInNome = getExtraparams().get("strInNome");

		List<ModelloBean> result = null;
		try {
			result = getListaModelli(userId, prefKey, prefName, strInNome, startRow, endRow, orderByBeanList);
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
		}
		return result;
	}
	
	private List<ModelloBean> fetchSenzaDominio(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList, AurigaLoginBean loginBean) {
		
		String flgIncludiPubbl = "2";
		String idUo = null;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName() != null && criterion.getFieldName().equals("flgIncludiPubbl")) {
					flgIncludiPubbl = (String) criterion.getValue();
				} else if (criterion.getFieldName() != null && criterion.getFieldName().equals("idUo")) {
					idUo = (String) criterion.getValue();
				}
			}
		}

		if (idUo != null && idUo.startsWith("UO")) {
			idUo = idUo.substring(2);
		}

		String userId = getExtraparams().get("userId");
		
		if (StringUtils.isBlank(userId)) {
			AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
		
			if ("0".equalsIgnoreCase(flgIncludiPubbl) && StringUtils.isBlank(idUo)) {
				if (StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			} else if ("1".equalsIgnoreCase(flgIncludiPubbl)) {
				userId = "PUBLIC.";
			} else if (StringUtils.isNotBlank(idUo)) {
				userId = "UO." + idUo;
			}
		}

		String prefKey = getExtraparams().get("prefKey");

		String prefName = getExtraparams().get("prefName") != null ? getExtraparams().get("prefName").trim() : null;
		if (StringUtils.isBlank(prefName)) {
			if (criteria != null && criteria.getCriteria() != null) {
				for (Criterion criterion : criteria.getCriteria()) {
					if (criterion.getFieldName() != null && criterion.getFieldName().equals("prefName")) {
						prefName = ((String) criterion.getValue()).trim();
						break;
					}
				}
			}
		}

		String strInNome = getExtraparams().get("strInNome");

		List<ModelloBean> result = null;
		try {
			result = getListaModelli(userId, prefKey, prefName, strInNome, startRow, endRow, orderByBeanList);
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
		}
		return result;
	}
	
	private List<ModelloBean> rimuoviIndirizzoMezzoTrasmissioneNonValido(List<ModelloBean> dataIn) throws Exception {
		List<ModelloBean> dataOut = new ArrayList<ModelloBean>();
		
		for (ModelloBean modelloBean : dataIn) {
		
			String idSoggetto = null;
			String rowIdSoggetto = null;
			String rowIdIndirizzo = null;
			String valueTPref = null;
			
			valueTPref = modelloBean.getValue();
		
			if (valueTPref !=null && !valueTPref.equalsIgnoreCase("")){
				
				String valueTPrefTemp = replaceCharJson(valueTPref);
				GsonBuilder builder = GsonBuilderFactory.getIstance();
				Gson gson = builder.create();
				
				ProtocollazioneBean respBean = gson.fromJson(valueTPrefTemp, ProtocollazioneBean.class);
				
				List<DestinatarioProtBean>  listaDestinatari =  respBean.getListaDestinatari();
				
				if (listaDestinatari != null && !listaDestinatari.isEmpty()){
					
					for (DestinatarioProtBean destinatarioProtBean : listaDestinatari) {
						
						// Prendo l'id del soggetto
						idSoggetto = destinatarioProtBean.getIdSoggetto();
						
						// Cerco il rowid del Soggetto 
						rowIdSoggetto = getRowIdIndirizzoSoggetto(idSoggetto);	
												
						// Prendo l'id dell'indirizzo
						MezzoTrasmissioneDestinatarioBean mezzoTrasmissioneDestinatarioBean = destinatarioProtBean.getMezzoTrasmissioneDestinatario();
						rowIdIndirizzo = mezzoTrasmissioneDestinatarioBean != null ? mezzoTrasmissioneDestinatarioBean.getIndirizzoDestinatario() : null;
						
						if (rowIdSoggetto!=null && !rowIdSoggetto.equalsIgnoreCase("") && rowIdIndirizzo !=null && !rowIdIndirizzo.equalsIgnoreCase("")){
							if (!rowIdSoggetto.equalsIgnoreCase(rowIdIndirizzo)){
								// rimuovo il rowid dell'indirizzo	
								valueTPref = valueTPref.replaceAll(rowIdIndirizzo, "");		
							}
						}						
						modelloBean.setValue(valueTPref);						
					}
				}
			}
			dataOut.add(modelloBean);
		}
		return dataOut;
	}
	
	
	/*
	 * Metodo per la modifica nel Json della data, da formato "new Date(726512...)"
	 * al formato 'yyyy-mm-dd'T'HH:mm:ss', per il parsing con GsonBuilder che altrimenti 
	 * andrebbe in errore.
	 */
	private String replaceCharJson(String jsonString) {
		String regExp = "new\\sDate\\([0-9]{13}\\)";
		return jsonString.replaceAll(regExp, "\"2018-01-01'T'00:00:00\"");
	}
	
	private String  getRowIdIndirizzoSoggetto(String idSoggetto) throws Exception {
		String idIndirizzoOut = null;
		if (idSoggetto!= null && !idSoggetto.equalsIgnoreCase("")){
			List<LoadComboIndirizzoSoggettoBean> lListResult = new ArrayList<LoadComboIndirizzoSoggettoBean>();
			// Inizializzo l'INPUT
			DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();
			lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("INDIRIZZI_SOGG");
			lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin("ID_SOGG_RUBRICA|*|"+idSoggetto);
	        lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);
			lDmpkLoadComboDmfn_load_comboBean.setTsvldin(null);
			// eseguo il servizio		
			DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
			StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean =  lDmpkLoadComboDmfn_load_combo.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);
			if(StringUtils.isBlank(lStoreResultBean.getDefaultMessage())) {			
				String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
				try {
					lListResult = XmlListaUtility.recuperaLista(xmlLista, LoadComboIndirizzoSoggettoBean.class);
					idIndirizzoOut= lListResult.get(0).getIdIndirizzo();
				} catch (Exception e) {
					mLogger.warn(e);
				}
			}
		}
		return idIndirizzoOut;
	}
	
	
	public List<ModelloBean> getListaModelli(String userId, String prefKey, String prefName, String strInNome, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList) throws Exception {

		List<ModelloBean> data = null;

		DmpkLoadComboDmfn_load_comboBean lDmpkLoadComboDmfn_load_comboBean = new DmpkLoadComboDmfn_load_comboBean();

		// Inizializzo l'INPUT
		DmpkLoadComboDmfn_load_combo lDmpkLoadComboDmfn_load_combo = new DmpkLoadComboDmfn_load_combo();
		lDmpkLoadComboDmfn_load_comboBean.setTipocomboin("USER_PREF");
		String altriParametri = "ID_USER_LAVORO|*|"
				+ (AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() != null ? AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro() : "")
				+ "|*|PREF_KEY|*|" + prefKey;
		if (StringUtils.isNotBlank(userId)) {
			altriParametri += "|*|USERID|*|" + userId;
		}
		if (StringUtils.isNotBlank(prefName)) {
			altriParametri += "|*|PREF_NAME|*|" + prefName;
		}
		if (StringUtils.isNotBlank(strInNome)) {
			altriParametri += "|*|STR_IN_NOME|*|" + strInNome;
		}
		lDmpkLoadComboDmfn_load_comboBean.setAltriparametriin(altriParametri);
		lDmpkLoadComboDmfn_load_comboBean.setFlgsolovldin(BigDecimal.ONE);

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> lStoreResultBean = lDmpkLoadComboDmfn_load_combo.execute(getLocale(),
				AurigaUserUtil.getLoginInfo(getSession()), lDmpkLoadComboDmfn_load_comboBean);

		if (StringUtils.isNotBlank(lStoreResultBean.getDefaultMessage())) {
			throw new StoreException(lStoreResultBean);
		} else {
			String xmlLista = lStoreResultBean.getResultBean().getListaxmlout();
			List<ModelloBean> lista = XmlListaUtility.recuperaLista(xmlLista, ModelloBean.class);

			data = new ArrayList<ModelloBean>();

			for (ModelloBean modelloBean : lista) {
				modelloBean.setPrefKey(prefKey);
				modelloBean.setKey(modelloBean.getUserid() + "|*|" + modelloBean.getPrefName());
				if (modelloBean.getUserid() != null && modelloBean.getUserid().startsWith("PUBLIC.")) {
					modelloBean.setDisplayValue(modelloBean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
				} else if (modelloBean.getUserid() != null && modelloBean.getUserid().startsWith("UO.")) {
					modelloBean.setDisplayValue(modelloBean.getPrefName()
							+ "&nbsp;<img src=\"images/organigramma/tipo/UO.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
				} else {
					modelloBean.setDisplayValue(modelloBean.getPrefName());
				}
				data.add(modelloBean);
			}
			
			Collections.sort(data, new Comparator<ModelloBean>() {

				@Override
				public int compare(ModelloBean modello1, ModelloBean modello2) {
					return modello1.getPrefName().toLowerCase().compareTo(modello2.getPrefName().toLowerCase());
				}
			});
			
		}

		return data;
	}

	@Deprecated
	public List<ModelloBean> getListaModelliWithDao(String userId, String prefKey, String prefName, Integer startRow, Integer endRow,
			List<OrderByBean> orderByBeanList) throws Exception {

		List<ModelloBean> data = new ArrayList<ModelloBean>();

		PreferenceBean bean = new PreferenceBean();
		bean.setUserid(userId);
		bean.setPrefName(prefName);
		bean.setPrefKey(prefKey);

		TFilterFetch<PreferenceBean> filter = new TFilterFetch<PreferenceBean>();
		filter.setStartRow(startRow);
		filter.setEndRow(endRow);
		// Setto gli ordinamenti
		List<TOrderBy> orderByList = new ArrayList<TOrderBy>();
		if (orderByBeanList != null) {
			for (OrderByBean orderByBean : orderByBeanList) {
				TOrderBy orderBy = new TOrderBy();
				orderBy.setPropname(orderByBean.getColumnname());
				if (orderByBean.getDirection() == Direction.ASC) {
					orderBy.setType(OrderByType.ASCENDING);
				} else {
					orderBy.setType(OrderByType.DESCENDING);
				}
				orderByList.add(orderBy);
			}
		}
		filter.setFilter(bean);
		filter.setOrders(orderByList);

		TPagingList<PreferenceBean> pagingList = AurigaService.getDaoTUserPreferences().search(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), filter);

		if (pagingList.getData() != null) {
			for (PreferenceBean preferenceBean : pagingList.getData()) {
				ModelloBean modelloBean = new ModelloBean();
				modelloBean.setUserid(preferenceBean.getUserid());
				modelloBean.setPrefKey(preferenceBean.getPrefKey());
				modelloBean.setPrefName(preferenceBean.getPrefName());
				modelloBean.setValue(preferenceBean.getValue());
				modelloBean.setSettingTime(preferenceBean.getSettingTime());
				modelloBean.setKey(preferenceBean.getUserid() + "|*|" + preferenceBean.getPrefName());
				if (preferenceBean.getUserid() != null && preferenceBean.getUserid().startsWith("PUBLIC.")) {
					modelloBean
							.setDisplayValue(preferenceBean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
				} else if (preferenceBean.getUserid() != null && preferenceBean.getUserid().startsWith("UO.")) {
					modelloBean.setDisplayValue(preferenceBean.getPrefName()
							+ "&nbsp;<img src=\"images/organigramma/tipo/UO.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
				} else {
					modelloBean.setDisplayValue(preferenceBean.getPrefName());
				}
				modelloBean.setIdUo(preferenceBean.getIdUo());
				modelloBean.setFlgVisibSottoUo(preferenceBean.getFlgVisibSottoUo());
				modelloBean.setFlgGestSottoUo(preferenceBean.getFlgGestSottoUo());
				boolean isAbilToPublic = getExtraparams().get("isAbilToPublic") != null && "1".equals(getExtraparams().get("isAbilToPublic"));
				if (preferenceBean.getUserid() == null || !preferenceBean.getUserid().startsWith("PUBLIC.") || isAbilToPublic) {
					modelloBean.setFlgAbilDel(true);
				}
				data.add(modelloBean);
			}
			
			Collections.sort(data, new Comparator<ModelloBean>() {

				@Override
				public int compare(ModelloBean modello1, ModelloBean modello2) {
					return modello1.getPrefName().toLowerCase().compareTo(modello2.getPrefName().toLowerCase());
				}
			});
			
		}

		return data;
	}

	@Override
	public ModelloBean add(ModelloBean bean) throws Exception {
		
		String idDominio = getExtraparams() != null && getExtraparams().get("idDominio") != null && !"".equals(getExtraparams().get("idDominio")) 
				? getExtraparams().get("idDominio") : null;

		if (StringUtils.isBlank(bean.getUserid())) {
			String userId = getExtraparams().get("userId");
			if (StringUtils.isBlank(userId)) {
				it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
				if (StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			}
			bean.setUserid(userId);
		}

		if (StringUtils.isBlank(bean.getPrefKey())) {
			bean.setPrefKey(getExtraparams().get("prefKey") + idDominio);
		}
		if (StringUtils.isBlank(bean.getPrefName())) {
			String prefName = StringUtils.isNotBlank(getExtraparams().get("prefName")) ? getExtraparams().get("prefName").trim() : "DEFAULT";
			bean.setPrefName(prefName);
		} else {
			bean.setPrefName(bean.getPrefName().trim());
		}
		
		if (bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
		}

		// se flgVisibSottoUo è a false lo setto a null
		if (bean.getFlgVisibSottoUo() != null && !bean.getFlgVisibSottoUo()) {
			bean.setFlgVisibSottoUo(null);
		}

		// se flgGestSottoUo è a false lo setto a null, se invece è a true allora sarà true anche flgVisibSottoUo
		if (bean.getFlgGestSottoUo() != null) {
			if (bean.getFlgGestSottoUo()) {
				bean.setFlgVisibSottoUo(true);
			} else {
				bean.setFlgGestSottoUo(null);
			}
		}

		if (bean.getValue()!= null) {
			//			Controllo gli uri degli excel della listaDestinatari per accertarmi che non siano storati sulla temporanea, in tal caso li salvo in repository e aggiorno il json con l'uri definitivo 
			JSONObject jsonObj = new JSONObject(bean.getValue());
			if (jsonObj.containsKey("listaDestinatari")) {
				JSONArray optJSONArray = jsonObj.getJSONArray("listaDestinatari");
				for (int i = 0; i < optJSONArray.length(); i++) {
					JSONObject jsonObject = optJSONArray.getJSONObject(i);
					String uriFileExcel = jsonObject.containsKey("uriFileExcel") ? jsonObject.getString("uriFileExcel") : "";
					if (uriFileExcel != null && (uriFileExcel.contains("TEMP") || uriFileExcel.contains("temp"))) {
						AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
						Locale local = new Locale(lAurigaLoginBean.getLinguaApplicazione());
						File lFileToSave = StorageImplementation.getStorage().extractFile(uriFileExcel);
						FileSavedIn lFileSavedIn = new FileSavedIn();
						lFileSavedIn.setSaved(lFileToSave);
						SalvataggioFile lSalvataggioFile = new SalvataggioFile();
						FileSavedOut out = lSalvataggioFile.savefile(local, lAurigaLoginBean, lFileSavedIn);
						jsonObject.put("uriFileExcel", out.getUri());
					}
				}
				bean.setValue(jsonObj.toString());
			}
		}
		
		PreferenceBean preferenceBean = new PreferenceBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(preferenceBean, bean);

		AurigaService.getDaoTUserPreferences().save(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), preferenceBean);

		bean.setKey(bean.getUserid() + "|*|" + bean.getPrefName());
		if (bean.getUserid() != null && bean.getUserid().startsWith("PUBLIC.")) {
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else if (bean.getUserid() != null && bean.getUserid().startsWith("UO.")) {
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/organigramma/tipo/UO.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else {
			bean.setDisplayValue(bean.getPrefName());
		}

		return bean;
	}

	@Override
	public ModelloBean update(ModelloBean bean, ModelloBean oldvalue) throws Exception {
		
		String idDominio = getExtraparams() != null && getExtraparams().get("idDominio") != null && !"".equals(getExtraparams().get("idDominio")) 
				? getExtraparams().get("idDominio") : null;

		if (StringUtils.isBlank(bean.getUserid())) {
			String userId = getExtraparams().get("userId");
			if (StringUtils.isBlank(userId)) {
				it.eng.auriga.module.business.beans.AurigaLoginBean loginInfo = AurigaUserUtil.getLoginInfo(getSession());
				if (StringUtils.isNotBlank(loginInfo.getUseridForPrefs())) {
					userId = loginInfo.getUseridForPrefs();
				} else {
					userId = getRequest().getRemoteUser();
				}
			}
			bean.setUserid(userId);
		}
		if (StringUtils.isBlank(bean.getPrefKey())) {
			bean.setPrefKey(getExtraparams().get("prefKey") + idDominio);
		}
		if (StringUtils.isBlank(bean.getPrefName())) {
			String prefName = StringUtils.isNotBlank(getExtraparams().get("prefName")) ? getExtraparams().get("prefName") : "DEFAULT";
			bean.setPrefName(prefName);
		}
		if (bean.getSettingTime() == null) {
			bean.setSettingTime(new Date());
		}

		// se flgVisibSottoUo è a false lo setto a null
		if (bean.getFlgVisibSottoUo() != null && !bean.getFlgVisibSottoUo()) {
			bean.setFlgVisibSottoUo(null);
		}

		// se flgGestSottoUo è a false lo setto a null, se invece è a true allora sarà true anche flgVisibSottoUo
		if (bean.getFlgGestSottoUo() != null) {
			if (bean.getFlgGestSottoUo()) {
				bean.setFlgVisibSottoUo(true);
			} else {
				bean.setFlgGestSottoUo(null);
			}
		}

		if (bean.getValue()!= null) {
			//		Controllo gli uri degli excel della listaDestinatari per accertarmi che non siano storati sulla temporanea, in tal caso li salvo in repository e aggiorno il json con l'uri definitivo 
			JSONObject jsonObj = new JSONObject(bean.getValue());
			if (jsonObj.containsKey("listaDestinatari")) {
				JSONArray optJSONArray = jsonObj.getJSONArray("listaDestinatari");
				for (int i = 0; i < optJSONArray.length(); i++) {
					JSONObject jsonObject = optJSONArray.getJSONObject(i);
					String uriFileExcel = jsonObject.containsKey("uriFileExcel") ? jsonObject.getString("uriFileExcel") : "";
					if (uriFileExcel != null && (uriFileExcel.contains("TEMP") || uriFileExcel.contains("temp"))) {
						AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
						Locale local = new Locale(lAurigaLoginBean.getLinguaApplicazione());
						File lFileToSave = StorageImplementation.getStorage().extractFile(uriFileExcel);
						FileSavedIn lFileSavedIn = new FileSavedIn();
						lFileSavedIn.setSaved(lFileToSave);
						SalvataggioFile lSalvataggioFile = new SalvataggioFile();
						FileSavedOut out = lSalvataggioFile.savefile(local, lAurigaLoginBean, lFileSavedIn);
						jsonObject.put("uriFileExcel", out.getUri());
					}
				}
				bean.setValue(jsonObj.toString());
			}
		}
		PreferenceBean preferenceBean = new PreferenceBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(preferenceBean, bean);

		AurigaService.getDaoTUserPreferences().update(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), preferenceBean);

		bean.setKey(bean.getUserid() + "|*|" + bean.getPrefName());
		if (bean.getUserid() != null && bean.getUserid().startsWith("PUBLIC.")) {
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/public.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else if (bean.getUserid() != null && bean.getUserid().startsWith("UO.")) {
			bean.setDisplayValue(bean.getPrefName() + "&nbsp;<img src=\"images/organigramma/tipo/UO.png\" height=\"12\" width=\"12\" align=MIDDLE/>");
		} else {
			bean.setDisplayValue(bean.getPrefName());
		}

		return bean;
	}

	@Override
	public ModelloBean remove(ModelloBean bean) throws Exception {
		
		try {
			if (StringUtils.isNotBlank(bean.getKey())) {
				bean.setUserid(bean.getKey().substring(0, bean.getKey().indexOf("|*|")));
				bean.setPrefName(bean.getKey().substring(bean.getKey().indexOf("|*|") + 3));
			}
			
			if (StringUtils.isBlank(bean.getPrefKey())) {
				bean.setPrefKey(getExtraparams().get("prefKey") + "."+ getIdDominio());
			}

			PreferenceBean preferenceBean = new PreferenceBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(preferenceBean, bean);

			AurigaService.getDaoTUserPreferences().delete(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), preferenceBean);

		} catch (Exception e) {
			mLogger.warn(e);
		}
		return bean;
	}
	
	public String getIdDominio() {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		if(loginBean != null) {
			if (loginBean.getDominio() != null && loginBean.getDominio().split(":").length == 2) {
				return loginBean.getDominio().split(":")[1];
			} else {
				return loginBean.getDominio();
			}
		}
		return null;
	}

}