/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttiModelliCustomBean;
import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AvvioIterAttiFieldsVisibilityBean;
import it.eng.auriga.ui.module.layout.shared.bean.ConfigAttiBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.user.ParametriDBUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Datasource(id="ConfigAttiDataSource")
public class ConfigAttiDataSource extends AbstractServiceDataSource<LoginBean, ConfigAttiBean>{
	
	@Override
	public ConfigAttiBean call(LoginBean bean) throws Exception {
		
		ConfigAttiBean result = new ConfigAttiBean();		
		try {
			AttiModelliCustomBean lAttiModelliCustomBean = (AttiModelliCustomBean)SpringAppContext.getContext().getBean("attiModelliCustom");
			result.setNomiModelliAtti(lAttiModelliCustomBean.getConfigMap());
		} catch(Exception e ) {
			result.setNomiModelliAtti(new HashMap<String, String>());
		}
		try {		
			AvvioIterAttiFieldsVisibilityBean lAvvioIterAttiFieldsVisibilityBean = (AvvioIterAttiFieldsVisibilityBean)SpringAppContext.getContext().getBean("avvioIterAttiFieldsVisibilityBean");
			Map<Integer,Set<String>> hiddenFieldsAtti = new HashMap<Integer, Set<String>>();
			hiddenFieldsAtti.put(lAvvioIterAttiFieldsVisibilityBean.getTipoAttoId(), lAvvioIterAttiFieldsVisibilityBean.getHiddenFields());
			result.setHiddenFieldsAtti(hiddenFieldsAtti);
		} catch(Exception e ) {
			result.setHiddenFieldsAtti(new HashMap<Integer, Set<String>>());
		}		
		return result;
	}

}