/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ButtonClickEvent;
import com.smartgwt.client.widgets.events.ButtonClickHandler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.common.WaitPopup;
import it.eng.utility.ui.module.layout.client.menu.MainMenu;
import it.eng.utility.ui.module.layout.client.menu.MenuSceltaBarraDesktop;
import it.eng.utility.ui.module.layout.client.menu.OpenedPortletMenu;
import it.eng.utility.ui.module.layout.client.menu.OpenedPortletMenuButton;
import it.eng.utility.ui.module.layout.client.menu.PreferenceMenu;
import it.eng.utility.ui.module.layout.client.menu.PreferenceMenuBarraDesktop;
import it.eng.utility.ui.module.layout.client.message.MessageBox;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;
import it.eng.utility.ui.module.layout.client.portal.Portlet;
import it.eng.utility.ui.module.layout.shared.bean.AllFilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiUtil;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSelectLayoutBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterType;
import it.eng.utility.ui.module.layout.shared.bean.FilterTypeBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterType;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;
import it.eng.utility.ui.module.layout.shared.bean.MimeTypeNonGestitiBean;

public class Layout extends VLayout {

	// protected static final String HISTORY_KEY = "HISTORY_KEY";

	protected static Layout instance;

	protected static MainMenu menu;
	protected static Img menuImg;
	protected static MenuSceltaBarraDesktop menuBarraDesktop;
	public static PreferenceMenuBarraDesktop shortcutEnumDefault = PreferenceMenuBarraDesktop.BARRA;
	// protected static Img menuBarraDesktopImg;
	protected static Img logoImg;
	protected static PreferenceMenu preferences;
	protected static Canvas portalDesktop;
	protected static OpenedPortletMenu openedPortlets;
	protected static MessageBox messagebox;
	protected static List<String> openedWindows;
	protected static Map <String,Portlet>mapOpenedWindowsWithoutParent;
	protected static ToolStrip header;
	protected static ToolStrip user;
	protected static ToolStrip footer;
	
	protected Label chatButton;

	protected static HLayout layoutDesktopButton;

	protected String username;
	protected final Label userLabel;
	protected static WaitPopup waitPopup;

	protected static String idApplicazione;
	protected static GenericConfigBean config;
	protected static Map listConfig;
	protected static Map filterConfig;
	protected static Map filterTypeConfig;
	protected static String attributiDataSource;
	protected static Map filterAttributiValueMap;
	protected static MimeTypeNonGestitiBean mimeTypeNonGestitiBean;

	protected static boolean disableRecordComponent;
	protected static boolean disableSetFields;

	protected static Map<String, Map<String, String>> appletParameter;

	protected static AllFilterSelectBean filterSelectConfig;
	public static FilterPrivilegiUtil filterPrivilegi;

	public static LoginBean utenteLoggato;

	protected boolean configured;

	protected String logoImage;
	protected String bkgndImage;

	protected Img configUtenteButton;
	protected Img logoutButton;
	protected Img cookiesButton;

	protected String defaultMenuImage = "imagemenu.png";
	protected String defaultLogoImage = "blank.png";
	protected String defaultBkgndImage = "blank.png";

	private static String encoding;
	private static String algoritmoImpronta;

	public static boolean isExternalPortlet = false;

	protected static boolean reloadDisabled;

	protected static HashMap<String, BaseWindow> openedViewers = new HashMap<String, BaseWindow>();

	protected String portalDesktopBackgroundImage;
	protected String portalDesktopContents;
	
	protected static String shibbolethLogoutUrl;
	protected static String shibbolethRedirectUrlAfterSessionExpired;
	
//	public static final int _HEADER_TOOLSTRIP_HEIGHT = 40;
//	public static final int _FOOTER_TOOLSTRIP_HEIGHT = 30;
	private boolean nascondiPreferitiOnToolBar = false;
	
	protected void configure() {
		initErrorCallback(this, "errorCallback");
//		forzato apposta il fuso orario a +00, così le date arrivano 00:00+0.00, e la trasformazione per il json non modifica l'orario
		DateUtil.setDefaultDisplayTimezone("+0:00");
		DateUtil.setAdjustForDST(false);
//		ROLLBACK
//		il formato json non gestisce i fuso orari, quindi passando una data 00:00+2.00 prima lo trasforma in 22.00 e poi fa il json
//		TimeZoneConstants timeZoneConstants = GWT.create(TimeZoneConstants.class);
//		DateUtil.setDefaultDisplayTimezone(timeZoneConstants.europeRome());
//		DateUtil.setAdjustForDST(true);
//		GWT.create(BeanFactory.FormItemMetaFactory.class);
		new OneCallGWTRestService<Record, Record>("ConfigurationDataSource").call(new Record(), new ServiceCallback<Record>() {

			public void execute(Record record) {
				listConfig = record.getAttributeAsRecord("listConfig").getAttributeAsMap("liste");
				disableRecordComponent = record.getAttributeAsRecord("listConfig").getAttributeAsBoolean("disableRecordComponent");
				disableSetFields = record.getAttributeAsRecord("listConfig").getAttributeAsBoolean("disableSetFields");
				reloadDisabled = record.getAttributeAsRecord("listConfig").getAttributeAsBoolean("reloadDisabled");
				/*******************************************************************************************/
				idApplicazione = record.getAttributeAsRecord("applicationConfig").getAttributeAsString("idApplicazione");
				/*******************************************************************************************/
				config = new GenericConfigBean();
				if (record.getAttributeAsRecord("genericConfig").getAttribute("ente") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("ente"))) {
					config.setEnte(record.getAttributeAsRecord("genericConfig").getAttributeAsString("ente"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("applicationName") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("applicationName"))) {
					config.setApplicationName(record.getAttributeAsRecord("genericConfig").getAttributeAsString("applicationName"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("menuImage") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("menuImage"))) {
					config.setMenuImage(record.getAttributeAsRecord("genericConfig").getAttributeAsString("menuImage"));
				}
				
				// Se e' valorizzato il parametro setta la larghezza del logo menuImg
				if (record.getAttributeAsRecord("genericConfig").getAttribute("menuImgWidth") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("menuImgWidth"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("menuImgWidth"))
					) 
				{
					config.setMenuImgWidth(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("menuImgWidth"));
				}
				
				// Se e' valorizzato il parametro setta l'altezza del logo menuImg
				if (record.getAttributeAsRecord("genericConfig").getAttribute("menuImgHeight") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("menuImgHeight"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("menuImgHeight"))
					) 
				{
					config.setMenuImgHeight(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("menuImgHeight"));
				}
				
				if (record.getAttributeAsRecord("genericConfig").getAttribute("logoImage") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("logoImage"))) {
					config.setLogoImage(record.getAttributeAsRecord("genericConfig").getAttributeAsString("logoImage"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("backgroundImage") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("backgroundImage"))) {
					config.setBackgroundImage(record.getAttributeAsRecord("genericConfig").getAttributeAsString("backgroundImage"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("logoDominioFolder") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("logoDominioFolder"))) {
					config.setLogoDominioFolder(record.getAttributeAsRecord("genericConfig").getAttributeAsString("logoDominioFolder"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("backgroundColor") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("backgroundColor"))) {
					config.setBackgroundColor(record.getAttributeAsRecord("genericConfig").getAttributeAsString("backgroundColor"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("defaultPortletWidth") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("defaultPortletWidth"))) {
					config.setDefaultPortletWidth(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("defaultPortletWidth"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("defaultPortletHeight") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("defaultPortletHeight"))) {
					config.setDefaultPortletHeight(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("defaultPortletHeight"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxValueLength") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxValueLength"))) {
					config.setMaxValueLength(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("maxValueLength"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("filterAppliedIcon") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("filterAppliedIcon"))) {
					config.setFilterAppliedIcon(record.getAttributeAsRecord("genericConfig").getAttributeAsString("filterAppliedIcon"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("filtrableSelectPageSize") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("filtrableSelectPageSize"))) {
					config.setFiltrableSelectPageSize(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("filtrableSelectPageSize"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("checkIcon") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("checkIcon"))) {
					config.setCheckIcon(record.getAttributeAsRecord("genericConfig").getAttributeAsString("checkIcon"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("recordHeightDefault") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("recordHeightDefault"))) {
					config.setRecordHeightDefault(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("recordHeightDefault"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("annoPartenza") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("annoPartenza"))) {
					config.setAnnoPartenza(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("annoPartenza"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("delayTimeForDoubleClick") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("delayTimeForDoubleClick"))) {
					config.setDelayTimeForDoubleClick(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("delayTimeForDoubleClick"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("displayValueSeparator") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("displayValueSeparator"))) {
					config.setDisplayValueSeparator(record.getAttributeAsRecord("genericConfig").getAttribute("displayValueSeparator"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("displayValueNull") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("displayValueNull"))) {
					config.setDisplayValueNull(record.getAttributeAsRecord("genericConfig").getAttribute("displayValueNull"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("colonneEstremiRecordMaxValueLength") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("colonneEstremiRecordMaxValueLength"))) {
					config.setColonneEstremiRecordMaxValueLength(record.getAttributeAsRecord("genericConfig").getAttributeAsInt(
							"colonneEstremiRecordMaxValueLength"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("prefKeyPrefix") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("prefKeyPrefix"))) {
					config.setPrefKeyPrefix(record.getAttributeAsRecord("genericConfig").getAttribute("prefKeyPrefix"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("waitingMessageDuration") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("waitingMessageDuration"))) {
					config.setWaitingMessageDuration(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("waitingMessageDuration"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("numMaxShortCut") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("numMaxShortCut"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("numMaxShortCut"))) {
					config.setNumMaxShortCut(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("numMaxShortCut"));
				} else {
					config.setNumMaxShortCut(6);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("welcomeMaxLength") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("welcomeMaxLength"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("welcomeMaxLength"))) {
					config.setWelcomeMaxLength(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("welcomeMaxLength"));
				} else {
					config.setWelcomeMaxLength(30);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("nroMaxRecordsForResizeReorder") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("nroMaxRecordsForResizeReorder"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("nroMaxRecordsForResizeReorder"))) {
					config.setNroMaxRecordsForResizeReorder(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("nroMaxRecordsForResizeReorder"));
				} else {
					config.setNroMaxRecordsForResizeReorder(20);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlHeaderName") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlHeaderName"))) {
					config.setShibbolethLogoutUrlHeaderName(record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlHeaderName"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlSuffix") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlSuffix"))) {
					config.setShibbolethLogoutUrlSuffix(record.getAttributeAsRecord("genericConfig").getAttribute("shibbolethLogoutUrlSuffix"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("reloadAfterSessionExpired") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("reloadAfterSessionExpired"))) {
					config.setReloadAfterSessionExpired(new Boolean(record.getAttributeAsRecord("genericConfig").getAttribute("reloadAfterSessionExpired")));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("logoutRedirectUrl") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("logoutRedirectUrl"))) {
					config.setLogoutRedirectUrl(record.getAttributeAsRecord("genericConfig").getAttribute("logoutRedirectUrl"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("editableExtension") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("editableExtension"))) {
					config.setEditableExtension(Arrays.asList(record.getAttributeAsRecord("genericConfig").getAttributeAsStringArray("editableExtension")));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("emlExtension") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("emlExtension"))) {
					config.setEmlExtension(Arrays.asList(record.getAttributeAsRecord("genericConfig").getAttributeAsStringArray("emlExtension")));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("emlMimetype") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("emlMimetype"))) {
					config.setEmlMimetype(Arrays.asList(record.getAttributeAsRecord("genericConfig").getAttributeAsStringArray("emlMimetype")));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("hidePreferencesButton") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("hidePreferencesButton"))) {
					config.setHidePreferencesButton(record.getAttributeAsRecord("genericConfig").getAttribute("hidePreferencesButton"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("defaultDetailAuto") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("defaultDetailAuto"))) {
					config.setDefaultDetailAuto(new Boolean(record.getAttributeAsRecord("genericConfig").getAttribute("defaultDetailAuto")));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("showResetPasswordLogin") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("showResetPasswordLogin"))) {
					config.setShowResetPasswordLogin(record.getAttributeAsRecord("genericConfig").getAttribute("showResetPasswordLogin"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("displayApplicationName") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("displayApplicationName"))) {
					config.setDisplayApplicationName(record.getAttributeAsRecord("genericConfig").getAttribute("displayApplicationName"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("showAlertMaxRecord") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("showAlertMaxRecord"))) {
					config.setShowAlertMaxRecord(record.getAttributeAsRecord("genericConfig").getAttributeAsBoolean("showPlusAlertMaxRecord"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxNumIconeDesktop") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxNumIconeDesktop"))) {
					config.setMaxNumIconeDesktop(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("maxNumIconeDesktop"));
				} else {
					config.setMaxNumIconeDesktop(6);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("scannerDefaultResolution") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("scannerDefaultResolution"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("scannerDefaultResolution"))) {
					config.setScannerDefaultResolution(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("scannerDefaultResolution"));
				} else {
					config.setScannerDefaultResolution(200);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxNroDocFirmaMassivaClient") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxNroDocFirmaMassivaClient"))) {
					config.setMaxNroDocFirmaMassivaClient(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("maxNroDocFirmaMassivaClient"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxNroDocFirmaMassivaRemotaNonAuto") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxNroDocFirmaMassivaRemotaNonAuto"))) {
					config.setMaxNroDocFirmaMassivaRemotaNonAuto(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("maxNroDocFirmaMassivaRemotaNonAuto"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("minAnno") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("minAnno"))) {
					config.setMinAnno(record.getAttributeAsRecord("genericConfig").getAttribute("minAnno"));
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxAnno") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxAnno"))) {
					config.setMaxAnno(record.getAttributeAsRecord("genericConfig").getAttribute("maxAnno"));
				}
				//Flag per l'abilitazione del debug lato client
				if (record.getAttributeAsRecord("genericConfig").getAttribute("debugClientEnable") != null
						&& "true".equalsIgnoreCase(record.getAttributeAsRecord("genericConfig").getAttribute("debugClientEnable"))) {
					config.setDebugClientEnable(true);
				} else {
					config.setDebugClientEnable(false);
				}
				if (record.getAttributeAsRecord("genericConfig").getAttribute("maxRangeSearcByNumNoAlert") != null
						&& !"".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxRangeSearcByNumNoAlert"))
						&& !"0".equals(record.getAttributeAsRecord("genericConfig").getAttribute("maxRangeSearcByNumNoAlert"))) {
					config.setMaxRangeSearcByNumNoAlert(record.getAttributeAsRecord("genericConfig").getAttributeAsInt("maxRangeSearcByNumNoAlert"));
				} else {
					config.setMaxRangeSearcByNumNoAlert(100);
				}				
				
				refreshMenuImg();
				
				/*******************************************************************************************/
				filterTypeConfig = record.getAttributeAsRecord("filterTypeConfig").getAttributeAsMap("types");
				/*******************************************************************************************/
				attributiDataSource = record.getAttributeAsRecord("filterConfig").getAttributeAsString("attributiDataSource");
				filterConfig = record.getAttributeAsRecord("filterConfig").getAttributeAsMap("liste");
				/*******************************************************************************************/
				Map<String, ArrayList<Map<String, String>>> lAppletParameters = record.getAttributeAsRecord("appletConfig").getAttributeAsMap("appletConfiguration");
				Map<String, Map<String, String>> lMapParametri = new HashMap<String, Map<String, String>>();
				for (String lStrAppletName : lAppletParameters.keySet()) {
					if (!lStrAppletName.equals("__gwt_ObjectId")) {
						ArrayList<Map<String, String>> lRecords = lAppletParameters.get(lStrAppletName);
						Map<String, String> lMapInternalParam = new HashMap<String, String>();
						for (Map<String, String> lRecord : lRecords) {
							lMapInternalParam.put(lRecord.get("name"), lRecord.get("value"));
						}
						lMapParametri.put(lStrAppletName, lMapInternalParam);
					}
				}
				appletParameter = lMapParametri;
				/*******************************************************************************************/
				encoding = record.getAttributeAsString("encoding");
				algoritmoImpronta = record.getAttributeAsString("algoritmoImpronta");
				/*******************************************************************************************/
				Map lMap = record.getAttributeAsRecord("filterSelectConfig").getAttributeAsMap("selects");
				Map<String, FilterSelectBean> lMapResult = new HashMap<String, FilterSelectBean>();
				if (lMap != null) {
					for (Object lObject : lMap.keySet()) {
						String name = (String) lObject;
						if (!name.startsWith("__gwt")) {
							Map lMapSelect = (Map) lMap.get(lObject);
							Record lRecord = new Record(lMapSelect);
							FilterSelectBean lFilterSelectBean = new FilterSelectBean();
							Map lMapLayout = lRecord.getAttributeAsMap("layout");
							if (lMapLayout != null) {
								FilterSelectLayoutBean lFilterSelectLayoutBean = new FilterSelectLayoutBean();
								List<Map> lListItem = (List<Map>) lMapLayout.get("fields");
								List<ItemFilterBean> fieldItem = new ArrayList<ItemFilterBean>();
								for (Map fieldMap : lListItem) {
									ItemFilterBean lItemFilterBean = new ItemFilterBean();
									lItemFilterBean.setName((String) fieldMap.get("name"));
									lItemFilterBean.setTitle((String) fieldMap.get("title"));
									lItemFilterBean.setType(ItemFilterType.valueOf((String) fieldMap.get("type")));
									lItemFilterBean.setValue((Boolean) fieldMap.get("value"));
									lItemFilterBean.setDisplay((Boolean) fieldMap.get("display"));
									lItemFilterBean.setIcon((String) fieldMap.get("icon"));
									lItemFilterBean.setWidth((Integer) fieldMap.get("width"));
									lItemFilterBean.setPrefix((String) fieldMap.get("prefix"));
									lItemFilterBean.setSuffix((String) fieldMap.get("suffix"));
									lItemFilterBean.setParamDBShowIf((String) fieldMap.get("paramDBShowIf"));
									fieldItem.add(lItemFilterBean);
								}
								lFilterSelectLayoutBean.setFields(fieldItem);
								Integer lIntrowsHeight = (Integer) lMapLayout.get("rowsHeight");
								lFilterSelectLayoutBean.setRowsHeight(lIntrowsHeight);
								lFilterSelectBean.setLayout(lFilterSelectLayoutBean);
							}
							lFilterSelectBean.setDatasourceName(lRecord.getAttributeAsString("datasourceName"));
							if (lRecord.getAttributeAsMap("datasourceParams") != null) {
								lFilterSelectBean.setDatasourceParams(lRecord.getAttributeAsMap("datasourceParams"));
							}
							if (lRecord.getAttributeAsMap("valueMap") != null) {
								lFilterSelectBean.setValueMap(lRecord.getAttributeAsMap("valueMap"));
							}
							lFilterSelectBean.setCanFilter(lRecord.getAttributeAsBoolean("canFilter"));
							lFilterSelectBean.setMultiple(lRecord.getAttributeAsBoolean("multiple"));
							lFilterSelectBean.setWidth(lRecord.getAttributeAsInt("width"));
							if (lRecord.getAttributeAsMap("ordinamentoDefault") != null) {
								lFilterSelectBean.setOrdinamentoDefault(lRecord.getAttributeAsMap("ordinamentoDefault"));
							}
							if (lRecord.getAttributeAsString("emptyPickListMessage") != null) {
								lFilterSelectBean.setEmptyPickListMessage(lRecord.getAttributeAsString("emptyPickListMessage"));
							}
							if (lRecord.getAttributeAsMap("customProperties") != null) {
								lFilterSelectBean.setCustomProperties(lRecord.getAttributeAsMap("customProperties"));
							}
							lMapResult.put(name, lFilterSelectBean);
						}
					}
				}
				mimeTypeNonGestitiBean = new MimeTypeNonGestitiBean();
				if(record.getAttributeAsRecord("mimeTypeNonGestiti").getAttributeAsMap("mimeTypeMap") != null && !"".equals(record.getAttributeAsRecord("mimeTypeNonGestiti").getAttributeAsMap("mimeTypeMap"))) {
					mimeTypeNonGestitiBean.setMimeTypeMap(record.getAttributeAsRecord("mimeTypeNonGestiti").getAttributeAsMap("mimeTypeMap"));
				}
				filterSelectConfig = new AllFilterSelectBean();
				filterSelectConfig.setSelects(lMapResult);
				configured = true;
				filterAttributiValueMap = new HashMap();
				if (attributiDataSource != null && !"".equals(attributiDataSource)) {
					new OneCallGWTRestService<Record, Record>(attributiDataSource).call(new Record(), new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							filterAttributiValueMap = object.getAttributeAsMap("attributiValueMap");
							afterConfigure();
						}
					});
				} else {
					afterConfigure();
				}
			}
		});
	}

	protected void afterConfigure() {
		createConfigUtenteMenu(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				aggiornaUtente();
			}
		});
	}

	public void createConfigUtenteMenu(final DSCallback callback) {
		createMenuBarraDesktop(new ServiceCallback<Menu>() {

			@Override
			public void execute(final Menu menuBarraDesktop) {				
				configUtenteButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final List<MenuItem> listaConfigUtenteMenuItems = buildConfigUtenteMenuItems(menuBarraDesktop);
						if (listaConfigUtenteMenuItems != null && listaConfigUtenteMenuItems.size() > 0) {
							Menu configUtenteMenu = new Menu();
							configUtenteMenu.setItems(listaConfigUtenteMenuItems.toArray(new MenuItem[listaConfigUtenteMenuItems.size()]));
							configUtenteMenu.showContextMenu();
						}
					}
				});
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		});
	}
	
	protected native void initErrorCallback(Layout layout, String functionName) /*-{
		$wnd[functionName] = function(value) {
			layout.@it.eng.utility.ui.module.layout.client.Layout::errorCallback(Ljava/lang/String;)(value);
		};
	}-*/;

	public void errorCallback(String error) {
		MessageBean lMessageBean = new MessageBean(error, error, MessageType.ERROR);
		addMessage(lMessageBean);
	}

	public boolean getConfigured() {
		return configured;
	}

	public static String getIdApplicazione() {
		return idApplicazione;
	}

	public static GenericConfigBean getGenericConfig() {
		return config;
	}

	public static MenuBean getMenu(String key) {
		return menu.getVociMenu().get(key);
	}

	public static String getAttributiDataSource() {
		return attributiDataSource;
	}

	public static FilterBean getFilterConfig(final String key) {
		if (filterConfig.containsKey(key)) {
			FilterBean filter = new FilterBean();
			Map prop = (Map) filterConfig.get(key);
			List<Map> lList = (List<Map>) prop.get("fields");
			Boolean lBoolNotAbleToManageAddFilter = (Boolean) prop.get("notAbleToManageAddFilter");
			if (lBoolNotAbleToManageAddFilter == null)
				filter.setNotAbleToManageAddFilter(false);
			else
				filter.setNotAbleToManageAddFilter(lBoolNotAbleToManageAddFilter);
			List<FilterFieldBean> fields = new ArrayList<FilterFieldBean>();
			for (Map lMap : lList) {
				FilterFieldBean lFilterFieldBean = new FilterFieldBean();
				lFilterFieldBean.setName((String) lMap.get("name"));
				lFilterFieldBean.setTitle((String) lMap.get("title"));
				if (lMap.get("type") != null) {
					lFilterFieldBean.setType(FilterType.valueOf((String) lMap.get("type")));
					if (lFilterFieldBean.getType().equals(FilterType.custom)) {
						lFilterFieldBean.setCustomType((String) lMap.get("customType"));
					}
				}
				lFilterFieldBean.setRequired((Boolean) lMap.get("required"));
				lFilterFieldBean.setRequiredIfPrivilegi((Boolean) lMap.get("requiredIfPrivilegi"));
				lFilterFieldBean.setCategoria((String) lMap.get("categoria"));
				lFilterFieldBean.setShowFlgRicorsiva((Boolean) lMap.get("showFlgRicorsiva"));
				lFilterFieldBean.setShowSelectAttributi((Boolean) lMap.get("showSelectAttributi"));
				lFilterFieldBean.setRequiredForDepends((Boolean) lMap.get("requiredForDepends"));
				lFilterFieldBean.setLookupType((String) lMap.get("lookupType"));
				lFilterFieldBean.setLookupField((String) lMap.get("lookupField"));
				lFilterFieldBean.setShowLookupDetail((Boolean) lMap.get("showLookupDetail"));
				lFilterFieldBean.setNomeTabella((String) lMap.get("nomeTabella"));
				lFilterFieldBean.setShowFiltriCustom((Boolean) lMap.get("showFiltriCustom"));
				lFilterFieldBean.setDictionaryEntry((String) lMap.get("dictionaryEntry"));
				if (lFilterFieldBean.isRequiredIfPrivilegi()) {
					lFilterFieldBean.setRequired(getIsRequired(lFilterFieldBean.getName()));
				}
				lFilterFieldBean.setFixed((Boolean) lMap.get("fixed"));
				Map lMapSelect = (Map) lMap.get("select");
				if (lMapSelect != null) {
					FilterSelectBean lFilterSelectBean = new FilterSelectBean();
					Map lMapLayout = (Map) lMapSelect.get("layout");
					if (lMapLayout != null) {
						FilterSelectLayoutBean lFilterSelectLayoutBean = new FilterSelectLayoutBean();
						List<Map> lListItem = (List<Map>) lMapLayout.get("fields");
						List<ItemFilterBean> fieldItem = new ArrayList<ItemFilterBean>();
						for (Map fieldMap : lListItem) {
							ItemFilterBean lItemFilterBean = new ItemFilterBean();
							lItemFilterBean.setName((String) fieldMap.get("name"));
							lItemFilterBean.setTitle((String) fieldMap.get("title"));
							lItemFilterBean.setType(ItemFilterType.valueOf((String) fieldMap.get("type")));
							lItemFilterBean.setValue((Boolean) fieldMap.get("value"));
							lItemFilterBean.setDisplay((Boolean) fieldMap.get("display"));
							lItemFilterBean.setIcon((String) fieldMap.get("icon"));
							lItemFilterBean.setWidth((Integer) fieldMap.get("width"));
							lItemFilterBean.setPrefix((String) fieldMap.get("prefix"));
							lItemFilterBean.setSuffix((String) fieldMap.get("suffix"));
							lItemFilterBean.setParamDBShowIf((String) fieldMap.get("paramDBShowIf"));
							fieldItem.add(lItemFilterBean);
						}
						lFilterSelectLayoutBean.setFields(fieldItem);
						Integer lIntrowsHeight = (Integer) lMapLayout.get("rowsHeight");
						lFilterSelectLayoutBean.setRowsHeight(lIntrowsHeight);
						lFilterSelectBean.setLayout(lFilterSelectLayoutBean);
					}
					if (lMapSelect.get("datasourceName") != null) {
						lFilterSelectBean.setDatasourceName((String) lMapSelect.get("datasourceName"));
					}
					if (lMapSelect.get("datasourceParams") != null) {
						lFilterSelectBean.setDatasourceParams((Map<String, String>) lMapSelect.get("datasourceParams"));
					}
					if (lMapSelect.get("valueMap") != null) {
						lFilterSelectBean.setValueMap((Map<String, String>) lMapSelect.get("valueMap"));
					}
					lFilterSelectBean.setCanFilter((Boolean) lMapSelect.get("canFilter"));
					lFilterSelectBean.setMultiple((Boolean) lMapSelect.get("multiple"));
					lFilterSelectBean.setWidth((Integer) lMapSelect.get("width"));
					if (lMapSelect.get("ordinamentoDefault") != null) {
						lFilterSelectBean.setOrdinamentoDefault((Map<String, String>) lMapSelect.get("ordinamentoDefault"));
					}
					if (lMapSelect.get("emptyPickListMessage") != null) {
						lFilterSelectBean.setEmptyPickListMessage((String) lMapSelect.get("emptyPickListMessage"));
					}
					if (lMapSelect.get("customProperties") != null) {
						lFilterSelectBean.setCustomProperties((Map<String, String>) lMapSelect.get("customProperties"));
					}
					lFilterFieldBean.setSelect(lFilterSelectBean);
				}
				
				if (lMap.get("dependsFrom") != null) {
					lFilterFieldBean.setDependsFrom((List<String>) lMap.get("dependsFrom"));
				}
				if (lMap.get("defaultIdNameField") != null) {
					lFilterFieldBean.setDefaultIdNameField((String) lMap.get("defaultIdNameField"));
				}
				if (lMap.get("defaultDisplayNameField") != null) {
					lFilterFieldBean.setDefaultDisplayNameField((String)lMap.get("defaultDisplayNameField"));
				}
				
				fields.add(lFilterFieldBean);
			}
			filter.setFields(fields);
			return filter;
		} else
			return null;
	}

	public static LinkedHashMap<String, String> getAttributiValueMap(String key) {
		if (filterAttributiValueMap != null && filterAttributiValueMap.containsKey(key)) {
			return (LinkedHashMap) filterAttributiValueMap.get(key);
		} else
			return new LinkedHashMap<String, String>();
	}

	public static FilterTypeBean getFilterTypeConfig(String key) {
		if (filterTypeConfig.containsKey(key)) {
			FilterTypeBean filterType = new FilterTypeBean();
			Map prop = (Map) filterTypeConfig.get(key);
			filterType.setOperators((List<String>) prop.get("operators"));
			return filterType;
		} else
			return null;
	}

	public static AllFilterSelectBean getFilterSelectConfig() {
		return filterSelectConfig;
	}

	public static ListaBean getListConfig(String key) {
		ListaBean bean = new ListaBean();
		if (listConfig.containsKey(key)) {
			Map prop = (Map) listConfig.get(key);
			bean.setKey((String) prop.get("key"));
			bean.setFullScreenDetail((Boolean) prop.get("fullScreenDetail"));
			bean.setNroMaxRecord((String) prop.get("nroMaxRecord"));
			bean.setColonneDefault((List<String>) prop.get("colonneDefault"));
			bean.setColonneOrdinabili((List<String>) prop.get("colonneOrdinabili"));
			bean.setOrdinamentoDefault((Map<String, String>) prop.get("ordinamentoDefault"));
			bean.setTipoRecord((String) prop.get("tipoRecord"));
			bean.setColonneEstremiRecord((List<String>) prop.get("colonneEstremiRecord"));
		}
		return bean;
	}
	
	public static MimeTypeNonGestitiBean getMimeTypeNonGestitiBean() {
		return mimeTypeNonGestitiBean;
	}

	public static Portlet getOpenedPortlet(String nomeEntita) {
		for (Canvas child : portalDesktop.getChildren()) {
			if (child instanceof Portlet) {
				Portlet portlet = ((Portlet) child);
				if (portlet.getNomeEntita().equals(nomeEntita)) {
					return portlet;
				}
			}
		}
		// Controllo delle portlet aperte senza parent
		Portlet result = mapOpenedWindowsWithoutParent != null ? mapOpenedWindowsWithoutParent.get(nomeEntita) : null;
		return result != null && result.isCreated() ? result : null;
	}

	public static int getOpenedPortletIndex(String nomeEntita) {
		int count = 0;
		for (Canvas child : portalDesktop.getChildren()) {
			if (child instanceof Portlet) {
				Portlet portlet = ((Portlet) child);
				if (portlet.getNomeEntita().equals(nomeEntita)) {
					return count;
				}
				count++;
			}
		}
		return -1;
	}

	public static List<Portlet> getOtherOpenedPortlet(String nomeEntita) {
		List<Portlet> res = new ArrayList<Portlet>();
		for (Canvas child : portalDesktop.getChildren()) {
			if (child instanceof Portlet) {
				Portlet portlet = ((Portlet) child);
				if (!portlet.getNomeEntita().equals(nomeEntita)) {
					res.add(portlet);
				}
			}
		}
		return res;
	}

	public static String getConfiguredPrefKeyPrefix() {
		String configPrefKeyPrefix = Layout.getGenericConfig().getPrefKeyPrefix();
		return (UserInterfaceFactory.getPrefKeyPrefix() != null ? UserInterfaceFactory.getPrefKeyPrefix() : "")
				+ (configPrefKeyPrefix != null && !"".equals(configPrefKeyPrefix) ? configPrefKeyPrefix + "." : "");
	}

	public static void showWaitPopup(String text) {
		hideWaitPopup();
		waitPopup = new WaitPopup();
		waitPopup.setZIndex(Integer.MAX_VALUE);
		waitPopup.show(text);
	}

	public static void hideWaitPopup() {
		if (waitPopup != null) {
			waitPopup.hideFinal();
			waitPopup = null;
		}
	}

	public Layout() {
		this(null, null);
	}

	public Layout(String pLogoImg, String pBkgndImg) {

		instance = this;

		logoImage = pLogoImg;
		bkgndImage = pBkgndImg;

		// Massimizzo sulla pagina
		setHeight100();
		setWidth100();
		setBorder("0px");
		setKeepInParentRect(true);

		// //Controllo la history
		// final String initToken = History.getToken();
		// if (initToken.length() == 0) {
		// //Tento di resettare offline
		// try{
		// Offline.remove(HISTORY_KEY);
		// }catch(Exception e){}//
		// History.newItem("history",false);
		// PortletHistory history = new PortletHistory();
		// //Serializzo l'oggetto
		// Offline.put(HISTORY_KEY,JSONUtil.HistoryJsonWriter.toJson(history));
		// }

		VLayout lVLayout = new VLayout();
		lVLayout.setOverflow(Overflow.AUTO);
		lVLayout.setBorder("0px");
		// lVLayout.setBackgroundRepeat(BkgndRepeat.NO_REPEAT);
		lVLayout.setBackgroundImage("blank.png");
		lVLayout.setBackgroundPosition("center");
		lVLayout.setPadding(1); // aggiunto per evitare l'effetto blink degli scroll che compaiono e scompaiono

		header = new ToolStrip();
		header.setWidth100();
		header.setBorder("0px");
		header.setKeepInParentRect(true);
		// header.setShowResizeBar(true);
		header.setStyleName(it.eng.utility.Styles.headerToolStrip);
		header.setPaddingAsLayoutMargin(false);
		
		preferences = new PreferenceMenu();

		menuImg = new Img("blank.png", 24, 24);
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			menuImg.setCanFocus(false);
 		}
		menuImg.setCursor(Cursor.HAND);
		menuImg.setPrompt(I18NUtil.getMessages().menu_prompt());
		menuImg.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.setTop(header.getVisibleHeight());
				menu.show();
				menu.markForRedraw();
			}
		});
		
		header.addSpacer(5);
		header.addMember(menuImg);
		header.addSpacer(5);
		
		logoImg = new Img("blank.png", 150, 28);
		header.addMember(logoImg);
		header.addSpacer(5);
		header.addMember(preferences);
		header.addFill(); // push all buttons to the right
		
		addHeaderButtons(header);
		
		user = new ToolStrip();
		user.setHeight100();
		user.setWidth(30);
		user.setOverflow(Overflow.VISIBLE);
		user.setBorder("0px");
		user.setKeepInParentRect(true);
		// user.setShowResizeBar(true);
		user.setStyleName(it.eng.utility.Styles.userToolStrip);
		user.setPaddingAsLayoutMargin(false);
		
		configUtenteButton = new Img("menuconfig.png", 24, 24);
		configUtenteButton.setCursor(Cursor.HAND);
		configUtenteButton.setPrompt("Personalizzazioni utente");		
		user.addSpacer(5);
		user.addMember(configUtenteButton);
		
		cookiesButton = new Img("cookie.png", 24, 24);
		cookiesButton.setCursor(Cursor.HAND);
		cookiesButton.setPrompt("Cookie policy");
		cookiesButton.hide();
		cookiesButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showCookiePrivacyPreview(UserInterfaceFactory.getParametroDB("URI_PDF_COOKIE_POLICY"));
			}
		});
		user.addSpacer(5);
		user.addMember(cookiesButton);
		
		userLabel = new Label();
		userLabel.setAlign(Alignment.CENTER);
		userLabel.setValign(VerticalAlignment.CENTER);
		userLabel.setStyleName(it.eng.utility.Styles.userLabel);
		user.addSpacer(5);
		user.addMember(userLabel);
		
		if (showLogoutButton()) {
			logoutButton = new Img("logout.png", 24, 24);
			logoutButton.setCursor(Cursor.HAND);
			logoutButton.setPrompt(I18NUtil.getMessages().logoutButton_prompt());
			logoutButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageLogout();
				}
			});
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
 			logoutButton.setCanFocus(true);
 		}
			user.addSpacer(5);
			user.addMember(logoutButton);	
			user.addSpacer(5);								
		}
	
		header.addMember(user);
		lVLayout.addMember(header);
		
		// Creo il layout del portale
		layoutDesktopButton = new HLayout();
		layoutDesktopButton.setHeight100();
		layoutDesktopButton.setWidth100();
		layoutDesktopButton.setAlign(Alignment.CENTER);
		layoutDesktopButton.setDefaultLayoutAlign(Alignment.CENTER);
		layoutDesktopButton.setVisibility(Visibility.HIDDEN);
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			layoutDesktopButton.setCanFocus(true);
			layoutDesktopButton.clearExplicitTabIndex();
 		}

		portalDesktop = new Canvas();
		portalDesktop.setWidth100();
		portalDesktop.setHeight100();
		portalDesktop.setBorder("0px");
		// portalDesktop.setOverflow(Overflow.AUTO);
		portalDesktop.setBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		portalDesktop.setBackgroundImage("blank.png");
		portalDesktop.setBackgroundPosition("center");

		lVLayout.addMember(portalDesktop);
		mapOpenedWindowsWithoutParent = new HashMap<String, Portlet>();
		addMember(lVLayout);

		footer = new ToolStrip();
		footer.setWidth100();
		footer.setBorder("0px");
		footer.setStyleName(it.eng.utility.Styles.footerToolStrip);
		footer.setPaddingAsLayoutMargin(false);
		// footer.setShowResizeBar(true);

		openedPortlets = new OpenedPortletMenu();
		footer.addMember(openedPortlets);

		footer.addFill();

		chatButton = new Label();
		chatButton.setAutoWidth();
		chatButton.setIcon("chat.png");
		chatButton.setIconSize(24);
		chatButton.setPrompt("Chat");
		chatButton.setCursor(Cursor.HAND);
		chatButton.setVisibility(Visibility.HIDDEN);
		chatButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				manageChat();
			}
		});
		footer.addMember(chatButton);
		footer.addSpacer(10);		

		messagebox = buildMessageBox();

		// Setto la messagebox
		GWTRestDataSource.settingMessageBox(messagebox);
		footer.addMember(messagebox);

		addMember(footer);
		openedWindows = new ArrayList<String>();

		configure();
		
	}

	protected void addHeaderButtons(ToolStrip header) {
		
	}

	public void manageChat() {

	}

	public List<MenuItem> buildConfigUtenteMenuItems(Menu menuBarraDesktop) {
		return null;
	}

	public MessageBox buildMessageBox() {
		return new MessageBox();
	}

	public static MessageBox getMessagebox() {
		return messagebox;
	}

	public static OpenedPortletMenu getOpenedPortlets() {
		return openedPortlets;
	}

	public boolean showLogoutButton() {
		return true;
	}

	protected void manageLogout() {
		new OneCallGWTRestService<Record, Record>("LogoutDataSource").call(new Record(), new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				afterLogout(object);
			}
		});

	}

	protected void afterLogout(Record object) {
		if (shibbolethLogoutUrl != null && !"".equals(shibbolethLogoutUrl)) {
			Window.Location.replace(shibbolethLogoutUrl);
		} else if (getGenericConfig().getLogoutRedirectUrl() != null && !"".equals(getGenericConfig().getLogoutRedirectUrl())) {
			Window.Location.replace(getGenericConfig().getLogoutRedirectUrl());
		} else {
			// Cacco Federico 29-10-2015
			// Il reload da problemi con il multilingua di AurigaWeb
			// Utilizzo quindi l'assign sulla directory corrente
			Window.Location.replace(Window.Location.getHref());
			// Window.Location.reload();
		}
	}

	public static void addMessage(MessageBean msg) {
		List<MessageBean> messages = new ArrayList<MessageBean>();
		messages.add(msg);
		messagebox.addMessages(messages);
 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			messagebox.setCanFocus(true);
 		}
	}

	public static void addMessage(MessageBean msg, int waitingTime, WaitingMessageCallback callback) {
		messagebox.addWaitingMessage(msg, waitingTime, callback);
	}

	/**
	 * Aggiunge una portlet al layout
	 * 
	 * @param title
	 * @param body
	 */

	public static void changeTitleOfPortlet(String nomeEntita, String title) {
		Portlet portlet = getOpenedPortlet(nomeEntita);
		if (portlet != null) {
			portlet.setTitle(title);
			openedPortlets.changeTitle(portlet);
		}
	}

	public static void selectPortlet(String nomeEntita) {

		Portlet portlet = getOpenedPortlet(nomeEntita);
		if (portlet != null) {
			if (isOperaBrowser()) {
				portlet.seleziona();
			} 
			/**
			 * Rimossa opacità in fase di sovrapposizione delle portlet. Impostando setOpacity(50) le window
			 * si visualizzavano in modo non corretto.
			 */
//			else {
//				for (Portlet otherPortlet : getOtherOpenedPortlet(nomeEntita)) {
//					otherPortlet.setOpacity(50);
//				}
//				portlet.setOpacity(100);
//			}

			portlet.show();
			portlet.setMinimizedPortlet(false);
			portlet.bringToFront();
			openedPortlets.select(nomeEntita);

			portlet.focus();
		}
	}

	public static void redrawOpenedPortletsHomepageMenuItem(String nomeEntita, boolean isHomepage) {
		Portlet portlet = getOpenedPortlet(nomeEntita);
		if (portlet != null) {
			portlet.redrawHomepageMenuItem(isHomepage);
			if (isHomepage) {
				for (Portlet otherPortlet : getOtherOpenedPortlet(nomeEntita)) {
					otherPortlet.redrawHomepageMenuItem(false);
				}
			}
		}
	}

	public static void removePortlet(String nomeEntita) {
		Portlet portlet = getOpenedPortlet(nomeEntita);
		if (portlet != null) {
			portlet.markForDestroy();
			openedPortlets.remove(portlet);

//			if (menu.getMenuItem(nomeEntita) != null) {
//				menu.getMenuItem(nomeEntita).set_baseStyle(it.eng.utility.Styles.menuItem);
//			}
			menu.markForRedraw();

			int index = 0;
			for (String lStringId : openedWindows) {
				if (lStringId.equals(nomeEntita)) {
					break;
				} else
					index++;
			}
			openedWindows.remove(index);
		}
	}

	public void closeAllPortlets() {
		if (openedWindows != null && openedWindows.size() > 0) {
			List<String> lista = new ArrayList<String>();
			for (String nomeEntita : openedWindows) {
				lista.add(nomeEntita);
			}
			for (String nomeEntita : lista) {
				removePortlet(nomeEntita);
			}
		}
	}

	public static void addPortlet(final String nomeEntita) {
		addPortlet(nomeEntita, null);
	}

	public static void addPortlet(final String nomeEntita, final Canvas body) {
		if (menu != null) {
			final MenuBean menuBean = menu.getVociMenu().get(nomeEntita);
			if (menuBean != null) {
				addPortlet(nomeEntita, menuBean.getTitle(), menuBean.getIcon(), body);
			}
		}
	}

	public static ModalWindow addModalWindow(final String nomeEntita, final String title, final String icon, final Canvas body) {
		ModalWindow modalWindow = new ModalWindow(nomeEntita, true);
		modalWindow.setTitle(title);
		body.setHeight100();
		body.setWidth100();
		modalWindow.setBody(body);
		modalWindow.setIcon(icon);
		modalWindow.show();
		return modalWindow;
	}

	public static void addMaximizedModalWindow(final String nomeEntita, final String title, final String icon, final Canvas body) {
		final ModalWindow modalWindow = new ModalWindow(nomeEntita, true, false);
		modalWindow.setTitle(title);
		body.setHeight100();
		body.setWidth100();
		modalWindow.setBody(body);
		modalWindow.setIcon(icon);
		modalWindow.setMaximized(true);
		modalWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		modalWindow.show();
	}

	public static void addPortlet(final String nomeEntita, final String title, final String icon, final Canvas body) {
		addPortlet(nomeEntita, title, icon, body, true);
	}

	public static void addPortlet(final String nomeEntita, final String title, final String icon, final Canvas body, final boolean addToPortalDesktopChild) {

		Portlet openedPortlet = getOpenedPortlet(nomeEntita);
		if (openedPortlet == null) {
			if (showMaximizedPortlet(nomeEntita)) {

				buildAndShowPortlet(nomeEntita, title, icon, body, true, "0", "0", String.valueOf(config.getDefaultPortletWidth()), String.valueOf(config.getDefaultPortletHeight()), false, addToPortalDesktopChild);

			} else {

				final GWTRestDataSource layoutPortletDS = UserInterfaceFactory.getPreferenceDataSource();
				layoutPortletDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");

				final GWTRestDataSource layoutPortletDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
				layoutPortletDefaultDS.addParam("userId", "DEFAULT");
				layoutPortletDefaultDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");
				layoutPortletDefaultDS.addParam("prefName", "DEFAULT");

				AdvancedCriteria criteria = new AdvancedCriteria();
				criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
				layoutPortletDS.fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length > 0 && data[0].getAttribute("value") != null) {
							Record record = new Record(JSON.decode(data[0].getAttribute("value")));
							buildAndShowPortlet(nomeEntita, title, icon, body, record.getAttributeAsBoolean("maximized"), record.getAttribute("pageLeft"),
									record.getAttribute("pageTop"), record.getAttribute("width"), record.getAttribute("height"), false, addToPortalDesktopChild);
						} else {
							layoutPortletDefaultDS.fetchData(null, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length > 0 && data[0].getAttribute("value") != null) {
										Record record = new Record(JSON.decode(data[0].getAttribute("value")));
										buildAndShowPortlet(nomeEntita, title, icon, body, record.getAttributeAsBoolean("maximized"),
												record.getAttribute("pageLeft"), record.getAttribute("pageTop"), record.getAttribute("width"),
												record.getAttribute("height"), false, addToPortalDesktopChild);
									} else {
										int left = 5;
										int top = 5;
										boolean offset = false;
										if (openedWindows.size() > 0) {
											Portlet lPortlet = getOpenedPortlet(openedWindows.get(0));
											if (lPortlet != null) {
												left = lPortlet.getLeft();
												top = lPortlet.getTop();
												offset = true;
											}
										}
										buildAndShowPortlet(nomeEntita, title, icon, body, false, String.valueOf(offset ? left + 20 : left),
												String.valueOf(offset ? top + 20 : top), String.valueOf(config.getDefaultPortletWidth()),
												String.valueOf(config.getDefaultPortletHeight()), offset, addToPortalDesktopChild);
									}
								}
							});
						}
					}
				});

			}

			selectPortlet(nomeEntita);
			addToOpenedWindows(nomeEntita);
		} else {
			selectPortlet(nomeEntita);
		}

	}

	public static void addToOpenedWindows(String nomeEntita) {
		openedWindows.add(0, nomeEntita);
	}

	public static boolean showMaximizedPortlet(String nomeEntita) {
		return false;
	}

	public static void buildAndShowPortlet(final String nomeEntita, String title, String icon, Canvas body, boolean maximized, String pageLeft, String pageTop,
			String width, String height, boolean offset, boolean addToPortalDesktopChild) {

		try {

			if (body == null) {
				body = UserInterfaceFactory.getPortletLayout(nomeEntita, null);
			}

			if (body != null) {

				boolean hidePrefBtn = false;
				try {
					hidePrefBtn = new Boolean(config.getHidePreferencesButton()).booleanValue();
				} catch (Exception e) {
				}

				final Portlet portlet = new Portlet(nomeEntita, false, !hidePrefBtn);

				portlet.setTitle(title);
				portlet.setIcon(icon);
				if (maximized) {
					portlet.setWidth100();
					portlet.setHeight100();
					portlet.setTop(0);
					portlet.setLeft(0);
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							portlet.maximize();
						}
					});
				} else {
					portlet.setWidth(Integer.parseInt(width));
					portlet.setHeight(Integer.parseInt(height));
					portlet.setLeft(Integer.parseInt(pageLeft) >= 0 ? Integer.parseInt(pageLeft) : 0);
					portlet.setTop(Integer.parseInt(pageTop) >= 0 ? Integer.parseInt(pageTop) : 0);
				}
				portlet.setVisible(false);
				portlet.setCanDragReposition(true);
				portlet.setCanDragResize(true);
				portlet.setRedrawOnResize(true);
				portlet.setKeepInParentRect(true);
				portlet.setAutoDraw(true);

				addPortletToPortalDesktop(portlet, addToPortalDesktopChild);

//				if (menu.getMenuItem(nomeEntita) != null) {
//					menu.getMenuItem(nomeEntita).set_baseStyle(it.eng.utility.Styles.openedMenuItem);
//				}
				menu.markForRedraw();

				body.setHeight100();
				body.setWidth100();
				body.setVisibility(Visibility.INHERIT);
				portlet.setBody(body);

				showPortlet(portlet);

			}

		} catch (Throwable e) {
			e.getMessage();
		}

	}

	public static Rectangle getPortalDesktopRectangle() {
		return portalDesktop.getRect();
	}

	public static void addPortletToPortalDesktop(Portlet portlet, boolean addToPortalDesktopChild) {
		if (addToPortalDesktopChild) {
			portalDesktop.addChild(portlet, portlet.getTitle(), true);
		} else {
			// Setto a false il redrawOnResize altrimenti gli elementi per la preview degli atti (confronto) scompaiono sul resize
			portlet.setRedrawOnResize(false);
			portlet.setAutoDraw(false);
			mapOpenedWindowsWithoutParent.put(portlet.getNomeEntita(), portlet);
		}
		openedPortlets.add(portlet);
	}
	
	public static void showPortlet(final Portlet portlet) {
		// create an outline around the clicked button
//		final Canvas outline = new Canvas();
//		outline.setLeft(0);
//		outline.setTop(0);
//		outline.setWidth(100);
//		outline.setHeight(20);
//		outline.setBorder("2px solid #8289A6");
//		outline.draw();
//		outline.bringToFront();
//		outline.animateRect(portlet.getPageLeft(), portlet.getPageTop(), portlet.getVisibleWidth(), portlet.getViewportHeight(), new AnimationCallback() {
//
//			@Override
//			public void execute(boolean earlyFinish) {
//				outline.destroy();
				portlet.show();
//			}
//		}, 750);
	}

	public String getUsername() {
		return username;
	}

	public void lavoraInDelega() {
		UserUtil.getInfoUtente(new UserUtilCallback() {

			@Override
			public void execute(LoginBean lLoginBean) {
				utenteLoggato = lLoginBean;
				username = lLoginBean.getUserid();
				updateWelcomeMessage();
				closeAllPortlets();
				createMenu();
				Layout.hideWaitPopup();
			}
		});
	}

	public void terminaDelega() {
		UserUtil.getInfoUtente(new UserUtilCallback() {

			@Override
			public void execute(LoginBean lLoginBean) {
				utenteLoggato = lLoginBean;
				username = lLoginBean.getUserid();
				updateWelcomeMessage();
				closeAllPortlets();
				createMenu();
				Layout.hideWaitPopup();
			}
		});

	}

	public void updateWelcomeMessage() {
		if (utenteLoggato != null) {
			if (utenteLoggato.getDelegaDenominazione() != null && !"".equals(utenteLoggato.getDelegaDenominazione())) {
				setUserLabelContents(I18NUtil.getMessages().welcomeDelega(utenteLoggato.getDenominazione(), utenteLoggato.getDelegaDenominazione()));
			} else {
				setUserLabelContents(I18NUtil.getMessages().welcome(utenteLoggato.getDenominazione()));
			}
		}
	}

	public void setUserLabelContents(String welcome) {
		String welcomeTrunked = welcome;
		if (welcome.length() > Layout.getGenericConfig().getWelcomeMaxLength()) {
			welcomeTrunked = welcome.substring(0, Layout.getGenericConfig().getWelcomeMaxLength()) + "...";
		}
		userLabel.setContents("<span>" + welcomeTrunked.replace(" ", "&nbsp;") + "</span>");
		userLabel.setPrompt(welcome.replace(" ", "&nbsp;"));
	}

	public void aggiornaUtente() {
		UserUtil.getInfoUtente(new UserUtilCallback() {

			@Override
			public void execute(LoginBean lLoginBean) {
				utenteLoggato = lLoginBean;
				updateWelcomeMessage();
				if (username == null || !username.equals(lLoginBean.getUserid())) {
					username = lLoginBean.getUserid();
					if (config.getMenuImage() != null && !"".equals(config.getMenuImage())) {
						menuImg.setSrc(config.getMenuImage());
					} else {
						menuImg.setSrc(defaultMenuImage);
					}
			 		if (UserInterfaceFactory.isAttivaAccessibilita()){
			 			menuImg.setAttribute("aria-hidden", "true");
 					}
					
					refreshMenuImg();
					
					
					if (logoImage != null && !"".equals(logoImage)) {
						logoImg.setSrc(logoImage);
					} else if (config.getLogoImage() != null && !"".equals(config.getLogoImage())) {
						logoImg.setSrc(config.getLogoImage());
					} else {
						logoImg.setSrc(defaultLogoImage);
					}
					if (bkgndImage != null && !"".equals(bkgndImage)) {
						portalDesktop.setBackgroundImage(bkgndImage);
					} else if (config.getBackgroundImage() != null && !"".equals(config.getBackgroundImage())) {
						portalDesktop.setBackgroundImage(config.getBackgroundImage());
					} else {
						portalDesktop.setBackgroundImage(defaultBkgndImage);
					}

					portalDesktopBackgroundImage = portalDesktop.getBackgroundImage();

					if (config.getBackgroundColor() != null && !"".equals(config.getBackgroundColor())) {
						portalDesktop.setBackgroundColor(config.getBackgroundColor());
					}

					portalDesktopContents = "<html><body><div style=\"width:99%; height:99%;\">";

					String logoDominioImage = null;
					String logoUtente = null;
					if (config.getLogoDominioFolder() != null && !"".equals(config.getLogoDominioFolder())) {
						logoDominioImage = config.getLogoDominioFolder();
						if (!config.getLogoDominioFolder().endsWith("/")) {
							logoDominioImage += "/";
						}
						String idDominio = null;
						if (lLoginBean.getDominio() != null && !"".equals(lLoginBean.getDominio())) {
							if (lLoginBean.getDominio().split(":").length == 2) {
								idDominio = lLoginBean.getDominio().split(":")[1];
							}
						}
						// se l'utente ha un suo logo mostra quello altrimenti mostra il logo di default del dominio
						if (lLoginBean.getLogoUtente() != null && !"".equals(lLoginBean.getLogoUtente())) {
							logoUtente = (lLoginBean.getLogoUtente()).toString();
							logoDominioImage += logoUtente;
						} else if (idDominio != null && !"".equals(idDominio)) {
							logoDominioImage += "Logo_" + idDominio + ".png";
						} else {
							logoDominioImage = "images/blank.png";
						}
						portalDesktopContents += "<div style=\"width:100%; position:fixed; bottom:" + (footer.getVisibleHeight() + 10) + "px; text-align:right;\"><img src=\"" + logoDominioImage
								+ "\"/></div>";
					}

					portalDesktopContents += "</div></body></html>";
					portalDesktop.setContents(portalDesktopContents);
					portalDesktop.setPosition(Positioning.ABSOLUTE);

					closeAllPortlets();
					createMenu();
				}
			}
		});
	}

	public boolean isAbilMenuBarraDesktop() {
		return false;
	}
	
	private OneCallGWTRestDataSource getShortcutBarraDesktopOneCallGWTRestDataSource() {
		final OneCallGWTRestDataSource shortcutBarraDesktopDS = new OneCallGWTRestDataSource("PreferenceMenuDesktopDataSource");
		shortcutBarraDesktopDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "shortcutBarraDesktop");
		return shortcutBarraDesktopDS;
	}

	public void createMenuBarraDesktop(final ServiceCallback<Menu> callback) {
		
		if (isAbilMenuBarraDesktop()) {

			
			AdvancedCriteria shortcutCriteria = new AdvancedCriteria();
			shortcutCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
			getShortcutBarraDesktopOneCallGWTRestDataSource().fetchData(shortcutCriteria, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record[] data = response.getData();
						String shortcut = (data.length > 0) ? data[0].getAttributeAsString("value") : null;
						PreferenceMenuBarraDesktop shortcutEnum = PreferenceMenuBarraDesktop.fromValore(shortcut);
						// se la preference è vuota oppure non ha un valore valido (B o D) imposto di default B
						if (shortcutEnum == null) {
							shortcutEnum = shortcutEnumDefault;
						}
						menuBarraDesktop = new MenuSceltaBarraDesktop(instance, shortcutEnum);
						if (callback != null) {
							callback.execute(menuBarraDesktop);
						}
					} else {
						if (callback != null) {
							callback.execute(null);
						}
					}
				}
			});

		} else {
			setShowDesktopButtons(shortcutEnumDefault);
			if (callback != null) {
				callback.execute(null);
			}
		}

	}

	public void setShowDesktopButtons(PreferenceMenuBarraDesktop preferenza) {
		if (preferenza == PreferenceMenuBarraDesktop.DESKTOP) {
			preferences.setVisible(true);
			layoutDesktopButton.setVisible(true);
			portalDesktop.setBackgroundImage(defaultBkgndImage);
			// portalDesktop.setContents(null);
		} else if (preferenza == PreferenceMenuBarraDesktop.BARRA) {
			preferences.setVisible(true);
			layoutDesktopButton.setVisible(false);
			portalDesktop.setBackgroundImage(portalDesktopBackgroundImage);
			// portalDesktop.setContents(portalDesktopContents);
		}
		portalDesktop.markForRedraw();
	}
	
	public void createMenu() {
		menu = new MainMenu(this, preferences, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {

				preferences.markForRedraw();
				preferences.removeAllFromPreference();
		 		if (UserInterfaceFactory.isAttivaAccessibilita()){
					final GWTRestDataSource preferenceAccessibilitaDS = UserInterfaceFactory.getPreferenceDataSource();
					preferenceAccessibilitaDS.addParam("prefKey", "impostazioniSceltaAccessibilita");
	
					AdvancedCriteria preferenceAccessibilitaCriteria = new AdvancedCriteria();
					preferenceAccessibilitaCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					preferenceAccessibilitaDS.fetchData(preferenceAccessibilitaCriteria, new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
								
								String value = data[0].getAttributeAsString("value");
								Record mostraPreferitiOnToolBar = new Record(JSON.decode(value));
									nascondiPreferitiOnToolBar = (mostraPreferitiOnToolBar != null && mostraPreferitiOnToolBar.getAttributeAsBoolean("nascondiPreferitiOnToolBar") != null) ? mostraPreferitiOnToolBar.getAttributeAsBoolean("nascondiPreferitiOnToolBar")
											: false;
							}
									
						}
					});		 			
		 		}

				final GWTRestDataSource preferenceMenuDS = UserInterfaceFactory.getPreferenceDataSource();
				preferenceMenuDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "preferences");

				AdvancedCriteria preferenceMenuCriteria = new AdvancedCriteria();
				preferenceMenuCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
				preferenceMenuDS.fetchData(preferenceMenuCriteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
							String value = data[0].getAttributeAsString("value");
							if (value != null && !"".equals(value)) {
								StringSplitterClient st = new StringSplitterClient(value, ",");
								for (String key : st.getTokens()) {
									if (menu.getVociMenu().get(key) != null) {
										if (UserInterfaceFactory.isAttivaAccessibilita()){
											if (preferences.addToPreference(menu.getVociMenu().get(key),nascondiPreferitiOnToolBar)) {
												menu.getMenuItem(key).setAttribute("preferiti", true);
												menu.getMenuItem(key).setAttribute("iconpreferiti", "menu/preferiti_on.png");
											}			
 										} else {
											if (preferences.addToPreference(menu.getVociMenu().get(key))) {
												menu.getMenuItem(key).setAttribute("preferiti", true);
												menu.getMenuItem(key).setAttribute("iconpreferiti", "menu/preferiti_on.png");
											}
										}	
									}
								}
							}
						}
						afterAggiornaUtente();
					}
				});
			}
		});
	}

	public void afterAggiornaUtente() {

		markForRedraw();

		final GWTRestDataSource homepageDS = UserInterfaceFactory.getPreferenceDataSource();
		homepageDS.addParam("prefKey", getConfiguredPrefKeyPrefix() + "homepage");

		AdvancedCriteria homepageCriteria = new AdvancedCriteria();
		homepageCriteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
		homepageDS.fetchData(homepageCriteria, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record[] data = response.getData();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS && data.length != 0) {
					String homepage = data[0].getAttributeAsString("value");
					if (homepage != null && !"".equals(homepage)) {
						addPortlet(homepage);
					}
				}
			}
		});
	}
	
	public static void showConfirmDialogWithWarning(String title, String warningMessage, String confermaButtonTitle, String annullaButtonTitle, final BooleanCallback callback) {
		if (UserInterfaceFactory.isAttivaAccessibilita()) {
			Dialog dialogProperties = new Dialog();
			dialogProperties.setTabIndex(null);
			dialogProperties.setCanFocus(true);
			Button procediButton = Dialog.YES;
			procediButton.setTitle(confermaButtonTitle != null && !"".equals(confermaButtonTitle) ? confermaButtonTitle : "Ok");
			procediButton.setTabIndex(null);
			procediButton.setCanFocus(true);
			procediButton.setPrompt(confermaButtonTitle != null && !"".equals(confermaButtonTitle) ? confermaButtonTitle : "Ok");
			Button annullaButton = Dialog.NO;
			annullaButton.setTitle(annullaButtonTitle != null && !"".equals(annullaButtonTitle) ? annullaButtonTitle : "Annulla");
			annullaButton.setTabIndex(null);
			annullaButton.setCanFocus(true);
			annullaButton.setPrompt(annullaButtonTitle != null && !"".equals(annullaButtonTitle) ? annullaButtonTitle : "Annulla");
			dialogProperties.setButtons(procediButton, annullaButton);
			dialogProperties.setIcon("message/warning.png");
			dialogProperties.setIconSize(32);
			dialogProperties.setShowCloseButton(false);
			SC.ask(title != null && !"".equals(title) ? title : "Attenzione!", warningMessage, callback, dialogProperties);
		} else {
			final Dialog dialog = new Dialog();
			final Button confermaButton = new Button(confermaButtonTitle != null && !"".equals(confermaButtonTitle) ? confermaButtonTitle : "Ok");
			confermaButton.setAutoFit(true);
			confermaButton.setMinWidth(100);
			Button annullaButton = new Button(annullaButtonTitle != null && !"".equals(annullaButtonTitle) ? annullaButtonTitle : "Annulla");		
			annullaButton.setAutoFit(true);
			annullaButton.setMinWidth(100);
			dialog.setButtons(confermaButton, annullaButton);
			dialog.setTitle(title != null && !"".equals(title) ? title : "Attenzione!");
			dialog.setMessage(warningMessage);
			dialog.setIcon("message/warning.png");
			dialog.setIconSize(32);
			dialog.setShowCloseButton(false);
			dialog.addButtonClickHandler(new ButtonClickHandler() {
				
				@Override
				public void onButtonClick(ButtonClickEvent event) {
					String buttonTitle = event.getTargetCanvas().getTitle();
					if(callback != null) {
						callback.execute(buttonTitle != null && buttonTitle.equalsIgnoreCase(confermaButton.getTitle()));
					}
					dialog.markForDestroy();						
				}
			});
			dialog.show();		
		}
	}

	public static boolean isOpenedPortlet(String nomeEntita) {
		for (Canvas member : openedPortlets.getMembers()) {
			if (member instanceof OpenedPortletMenuButton) {
				if (((OpenedPortletMenuButton) member).getNomeEntita().equals(nomeEntita)) {
					return true;
				}
			}
		}
		return false;
	}

	public Canvas getPortalDesktop() {
		return portalDesktop;
	}

	public static Map<String, Map<String, String>> getAppletParameter() {
		return appletParameter;
	}

	// @Override
	// public void onValueChange(ValueChangeEvent<String> event) {
	// //Recupero la history e ne visualizzo il contenuto
	// System.out.println(Offline.get(HISTORY_KEY));
	// String json = (String)Offline.get(HISTORY_KEY);
	// PortletHistory history = (PortletHistory)JSONUtil.HistoryJsonReader.read(json);
	// // PortletHistory history = (PortletHistory)Offline.get(HISTORY_KEY);
	// for(HistoryBean bean:history.getPortlets()) {
	// // addPortlet(bean.getTitle(), new AdempireWindow(),false);
	// }
	// }

	/**
	 * Gets the name of the used browser.
	 */
	public static native String getBrowserName() /*-{
		return navigator.userAgent;
	}-*/;

	public static LoginBean getUtenteLoggato() {
		return utenteLoggato;
	}
	
	public static String getIdDominio() {
		if(getUtenteLoggato() != null) {
			if (getUtenteLoggato().getDominio() != null && getUtenteLoggato().getDominio().split(":").length == 2) {
				return getUtenteLoggato().getDominio().split(":")[1];
			} else {
				return getUtenteLoggato().getDominio();
			}
		}
		return null;
	}

	public static boolean getIsRequired(String nomeFiltro) {
		return filterPrivilegi.isRequired(nomeFiltro, utenteLoggato.getPrivilegi());
	}

	public static Layout getInstance() {
		return instance;
	}

	/**
	 * Returns true if the current browser is IE (Internet Explorer).
	 */
	public static boolean isIEBrowser() {
		try {
			return getBrowserName() != null && getBrowserName().toLowerCase().contains("msie");
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Returns true if the current browser is Firefox.
	 */
	public static boolean isFirefoxBrowser() {
		try {
			return getBrowserName() != null && getBrowserName().toLowerCase().contains("firefox");
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Returns true if the current browser is Opera.
	 */
	public static boolean isOperaBrowser() {
		try {
			return getBrowserName() != null && getBrowserName().toLowerCase().contains("opera");
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isPrivilegioAttivo(String privilegio) {
		String[] lListPrivilegi = Layout.getUtenteLoggato().getPrivilegi();
		boolean found = false;
		if (lListPrivilegi != null && lListPrivilegi.length > 0) {
			for (String lStr : lListPrivilegi) {
				if (lStr.equals(privilegio)) {
					found = true;
				}
			}
		}
		return found;
	}

	public Img getLogoutButton() {
		return logoutButton;
	}

	public static String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public static void setAlgoritmoImpronta(String pAlgoritmoImpronta) {
		algoritmoImpronta = pAlgoritmoImpronta;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		Layout.encoding = encoding;
	}

	public static boolean isDisableRecordComponent() {
		return disableRecordComponent;
	}

	public static boolean isDisableSetFields() {
		return disableSetFields;
	}

	public static boolean isReloadDisabled() {
		return reloadDisabled;
	}

	public static HashMap<String, BaseWindow> getOpenedViewers() {
		return openedViewers;
	}

	public static MenuSceltaBarraDesktop getMenuBarraDesktop() {
		return menuBarraDesktop;
	}
	
	public static boolean isDebugClientEnable() {
	 	return config != null && config.getDebugClientEnable() != null && config.getDebugClientEnable();
	}

	public void addPortalDesktopButtons() {

	}

	public void refreshMenuImg(){
		// Se e' valorizzato il parametro setta la larghezza del logo menuImg
		if (String.valueOf(config.getMenuImgWidth()) != null  && !String.valueOf(config.getMenuImgWidth()).equalsIgnoreCase("0") )  {
			menuImg.setWidth(config.getMenuImgWidth());
		} 
		
		// Se e' valorizzato il parametro setta l'altezza del logo menuImg
		if (String.valueOf(config.getMenuImgHeight()) != null && !String.valueOf(config.getMenuImgHeight()).equalsIgnoreCase("0")) {
			menuImg.setHeight(config.getMenuImgHeight());
		} 

		menuImg.markForRedraw();		
	}	
	
	public static boolean isAttivoRestyling() {
		String skin = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");
			skin = dictionary != null && dictionary.get("skin") != null ? dictionary.get("skin") : "";
		} catch (Exception e) {}
		return skin != null && skin.startsWith("AurigaTahoe");
	}
	
	public static boolean isAttivoRefreshTabIndex() {
		return false;		
	}
	
	@Override
	protected void onDestroy() {
		GWTRestDataSource.destroyAllDataSources();
		super.onDestroy();
	}

	public static String getShibbolethLogoutUrl() {
		return Layout.shibbolethLogoutUrl;
	}

	public static void setShibbolethLogoutUrl(String shibbolethLogoutUrl) {
		Layout.shibbolethLogoutUrl = shibbolethLogoutUrl;
	}
	
	public static String getShibbolethRedirectUrlAfterSessionExpired() {
		return shibbolethRedirectUrlAfterSessionExpired;
	}

	public static void setShibbolethRedirectUrlAfterSessionExpired(String shibbolethRedirectUrlAfterSessionExpired) {
		Layout.shibbolethRedirectUrlAfterSessionExpired = shibbolethRedirectUrlAfterSessionExpired;
	}
	public static boolean getIsAttivaAccessibilita () {
		return UserInterfaceFactory.isAttivaAccessibilita();
	}
	
	public void showCookiePrivacyPreview(String uriPreview) {
		
	}
	
}