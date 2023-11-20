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

public interface UserInterfaceConfig {
		
	public Canvas getPortletLayout(String nomeEntita, HashMap<String, String> params);		
	
	public GWTRestDataSource getPreferenceDataSource();
	
	public GWTRestService<GetListaDefPrefsBean, GetListaDefPrefsBean> getListaDefPrefsService();
	
	public GWTRestService<LoginBean,MenuBean> getServiceRestMenu();
	
	public String getPrefKeyPrefix();
	
	public void onClickLookupFilterLookupButton(FilterLookupType type, ServiceCallback<Record> callback);

	public void onClickLookupButtonWithFilters(FilterLookupType type, Record filters, Map<String, Object> extraparams, ServiceCallback<Record> callback);

	public void onClickLookupFilterDetailButton(FilterLookupType type, String id, String descrizione);
	
	public FormItem buildCustomFilterEditorType(FilterFieldBean filterFieldBean);
	
	public String getParametroDB(String paramName);
	
	public boolean getParametroDBAsBoolean(String paramName);
	
	public boolean isAbilToExportList();
		
	public ClientFactory getClientFactory();
		
	public boolean isAttivaAccessibilita();
	
	public void initIsAttivaAccessibilita();
	
	public boolean isAttivoClienteRER();

}
