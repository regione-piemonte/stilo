/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.util.ClientFactory;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;
import it.eng.utility.ui.module.layout.shared.bean.GetListaDefPrefsBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class UserInterfaceFactory {

	private static UserInterfaceConfig config;
	
	public static void configure(UserInterfaceConfig config) {
		UserInterfaceFactory.config = config;
	}
			
	public static Canvas getPortletLayout(String nomeEntita, HashMap<String, String> params) {
		return config != null ? config.getPortletLayout(nomeEntita, params) : null;
	}
	
	public static String getPrefKeyPrefix() {
		return config != null ? config.getPrefKeyPrefix() : null;	
	}
	
	public static GWTRestDataSource getPreferenceDataSource() {
		return config != null ? config.getPreferenceDataSource() : null;	
	}
	
	public static GWTRestService<GetListaDefPrefsBean, GetListaDefPrefsBean> getListaDefPrefsService() {
		return config != null ? config.getListaDefPrefsService() : null;	
	}
	
	public static GWTRestService<LoginBean, MenuBean> getServiceRestMenu() {
		return config != null ? config.getServiceRestMenu() : null;
	}

	public static void onClickLookupFilterLookupButton(FilterLookupType type, ServiceCallback<Record> callback) {
		if(config != null) config.onClickLookupFilterLookupButton(type, callback);	
	}
	
	public static void onClickLookupButtonWithFilters(FilterLookupType type, Record filters, Map<String, Object> extraparams, ServiceCallback<Record> callback) {
		if(config != null) config.onClickLookupButtonWithFilters(type, filters, extraparams, callback);	
	}
	
	public static void onClickLookupFilterDetailButton(FilterLookupType type, String id, String descrizione) {
		if(config != null) config.onClickLookupFilterDetailButton(type, id, descrizione);	
	}
		
	public static FormItem buildCustomFilterEditorType(FilterFieldBean filterFieldBean) {
		return config != null ? config.buildCustomFilterEditorType(filterFieldBean) : null;
	}
	
	public static String getParametroDB(String paramName) {
		return config != null ? config.getParametroDB(paramName) : null;	
	}
	
	public static Boolean getParametroDBAsBoolean(String paramName) {
		return config != null && config.getParametroDBAsBoolean(paramName);	
	}
	
	public static Boolean isAbilToExportList() {
		return config != null && config.isAbilToExportList();
	}
	
	public static ClientFactory getClientFactory() {
		return config != null ? config.getClientFactory() : null;
	}
	
	public static boolean isAttivaAccessibilita() {
		return config != null && config.isAttivaAccessibilita();
	}
	
	public static void initIsAttivaAccessibilita() {
		if(config != null) config.initIsAttivaAccessibilita();
	}
	
	public static boolean isAttivoClienteRER() {
		return config.isAttivoClienteRER();
	}
		
}
