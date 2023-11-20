/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

//import it.eng.auriga.module.business.beans.LoginBean;
import java.util.HashMap;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.ExternalPortlet;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.message.MessageBox;
import it.eng.utility.ui.module.layout.client.portal.ExtPortlet;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiContainer;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiImpl;

import com.claudiushauptmann.gwt.multipage.client.MultipageEntryPoint;
import com.google.gwt.i18n.client.Dictionary;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;

@MultipageEntryPoint(urlPattern = "aurigaportlet.jsp")
public class AurigaExternalPortlet extends ExternalPortlet {

	protected String schema;
	protected String useridappl;
	protected String password;
	
	@Override
	public void onModuleLoad() {
		super.onModuleLoad();
		adaptFilterBuilderDefaults();
		layout.filterPrivilegi = new FilterPrivilegiImpl();
		new GWTRestService<Record, Record>("ServiceRestPrivilegiFilterUtil").call(new Record(), new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				FilterPrivilegiContainer lFilterPrivilegiContainer = new FilterPrivilegiContainer();
				lFilterPrivilegiContainer.setConfigMap(object.getAttributeAsMap("configMap"));
				layout.filterPrivilegi.setContainer(lFilterPrivilegiContainer);				
			}			
		});
		layout.isExternalPortlet = true;
	}

	@Override
	public void configure() {
		UserInterfaceFactory.configure(new AurigaUserInterfaceConfig());	
	}
	
	protected native void adaptFilterBuilderDefaults () /*-{	  
	  //set widths for field picker, operator picker and value item
	  $wnd.isc.FilterClause.addProperties ({
	    fieldPickerWidth: 180,
	    operatorPickerWidth: "*",
	    valueItemWidth: 220,
	    valueItemTextHint: ""
	  });
	}-*/;

	@Override
	public Canvas createPortletLayout(String nomeEntita, HashMap<String, String> params) {
		
//		return UserInterfaceFactory.getPortletLayout(nomeEntita);
		final ExtPortlet portlet = new ExtPortlet(nomeEntita, params);				
        portlet.show();         		
		return portlet;
	}
	
	@Override
	protected boolean isExternalLogin() {
		return (useridappl != null && !"".equals(useridappl));
	}
	
	@Override
	protected void setExternalLoginParams(Dictionary dictionary) {
		schema = dictionary.get("schema");
		useridappl = dictionary.get("useridappl");
		password = dictionary.get("password");
	}
	
	@Override
	protected String getUsernameForExternalLogin() {
		return "USERID_APPL#" + useridappl;
	}
	
	@Override
	protected String getPasswordForExternalLogin() {
		return password + "#SCHEMA#" + schema;
	}
	
	@Override
	protected void resetParamsOnLoginError() {
		useridappl = null;
	}
	
	@Override
	protected void createSessionLoginInfo(ServiceCallback<Record> callback) {
		
		Record lRecord = new Record();
		lRecord.setAttribute("schema", schema);
		lRecord.setAttribute("userid", "USERID_APPL#" + useridappl);			
		lRecord.setAttribute("password", password);			
		new GWTRestService("AurigaLoginDataSource").call(lRecord, callback);			
	}
	
	@Override
	public Layout buildPortalLayout() {
		return new AurigaLayout() {
			@Override
			public void afterAggiornaUtente() {					
				portletLayout = createPortletLayout(nomeEntita, null);
				portletLayout.setHeight100();
				portletLayout.setWidth100();
				portletLayout.markForRedraw();		
			}				
			
			@Override
			public MessageBox buildMessageBox() {
				return new MessageBox(false);
			}
		};		
	}
		
}
