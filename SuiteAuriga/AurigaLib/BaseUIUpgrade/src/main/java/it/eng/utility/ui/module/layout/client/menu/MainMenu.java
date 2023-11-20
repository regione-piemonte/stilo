/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.LoginBean;
import it.eng.utility.ui.module.layout.shared.bean.MenuBean;

public class MainMenu extends Menu {
	
	private Map<String, MenuBean> vociMenu = new HashMap<String, MenuBean>();	
	private Map<String, MenuItem> menuItems = new HashMap<String, MenuItem>();	
	
	private Layout layout;
	private PreferenceMenu preferences;
	
	public MainMenu(Layout layout, PreferenceMenu preferences, final ServiceCallback<Record> callback) {
		
		this.vociMenu = new HashMap<String, MenuBean>();
		this.menuItems = new HashMap<String, MenuItem>();
		
		this.layout = layout;
		this.preferences = preferences;
		
		//Service to open menu		
		GWTRestService<LoginBean,MenuBean> datasource = UserInterfaceFactory.getServiceRestMenu();
//		setShowShadow(true);  
//	    setShadowDepth(10);		
		setShowIcons(true);
		setCanHover(true);
	    
	    datasource.call(new Record(),new ServiceCallback<Record>() {	    	
	    	public void execute(Record menu) {	    		
				
	    		ListGridField icon = new ListGridField("icon",16);
				icon.setType(ListGridFieldType.IMAGE);
				icon.setBaseStyle(it.eng.utility.Styles.menuIconField);
				icon.setImageSize(16);
					 												
				ListGridField title = new ListGridField("title");
				title.setBaseStyle(it.eng.utility.Styles.menuTitleField);
				
				ListGridField preferiti = new ListGridField("iconpreferiti",15);
				preferiti.setType(ListGridFieldType.IMAGE);
				preferiti.setBaseStyle(it.eng.utility.Styles.menuIconField);
				preferiti.setImageHeight(12);
				preferiti.setImageWidth(15);	
				preferiti.setAlign(Alignment.RIGHT);
				preferiti.setHoverCustomizer(new HoverCustomizer() {					
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {						

						if(record.getAttribute("iconpreferiti") != null) {
							if(record.getAttribute("iconpreferiti").equals("menu/preferiti_on.png")) {
								return I18NUtil.getMessages().removeFromPreferiti_prompt();
							} else if(record.getAttribute("iconpreferiti").equals("menu/preferiti_off.png")) {
								return I18NUtil.getMessages().addToPreferiti_prompt();								
							}		
						}						
						return null;
					}
				});
				
				setFields(icon, title, preferiti);	  
				
				createMenu(MainMenu.this,menu);		
				
				callback.execute(new Record());
	    	};	    		    	
		});	    
	    
	    setSelectionType(SelectionStyle.NONE);  
        setCanDragRecordsOut(true);  
        setDragDataAction(DragDataAction.COPY); 	   
	}
		
	private void createMenu(Menu root, Record record){
				
		Record[] submenu = record.getAttributeAsRecordArray("submenu");
		for(Record menu:submenu){
			String nomeEntita = menu.getAttributeAsString("nomeEntita");
			Boolean isPopup = menu.getAttributeAsBoolean("isPopup");
			
			MenuBean bean = new MenuBean();
			bean.setNomeEntita(nomeEntita);
			bean.setTitle(menu.getAttributeAsString("title"));
			bean.setPrompt(menu.getAttributeAsString("prompt"));
			bean.setIcon(menu.getAttributeAsString("icon"));
			bean.setPortalDesktopIcon(menu.getAttributeAsString("portalDesktopIcon"));
			bean.setPreferiti(menu.getAttributeAsBoolean("preferiti"));			
			
			if(nomeEntita != null && !"".equals(nomeEntita)) {
				vociMenu.put(bean.getNomeEntita(), bean);					
			}
			
			if(!isPopup) {
				MenuItem item = new MenuItem(bean.getTitle(), bean.getIcon());
				
				item.setAttribute("nomeEntita", nomeEntita);									
				item.setAttribute("menu", menu);		
				
				if(menu.getAttributeAsRecordArray("submenu")!=null && menu.getAttributeAsRecordArray("submenu").length!=0) {	
					
//					item.set_baseStyle(it.eng.utility.Styles.menuItem);
					
					Menu parentmenu = new Menu();
//					parentmenu.setShowShadow(true);  
//					parentmenu.setShadowDepth(10);
					parentmenu.setShowIcons(true);
					parentmenu.setCanHover(true);
						
					ListGridField icon = new ListGridField("icon",16);
					icon.setType(ListGridFieldType.IMAGE);
					icon.setBaseStyle(it.eng.utility.Styles.menuIconField);
					icon.setImageSize(16);
					
					ListGridField title = new ListGridField("title");
					title.setBaseStyle(it.eng.utility.Styles.menuTitleField);
										
					ListGridField preferiti = new ListGridField("iconpreferiti",15);
					preferiti.setType(ListGridFieldType.IMAGE);
					preferiti.setBaseStyle(it.eng.utility.Styles.menuIconField);
					preferiti.setImageHeight(12);
					preferiti.setImageWidth(15);
					preferiti.setAlign(Alignment.RIGHT);
					preferiti.setHoverCustomizer(new HoverCustomizer() {					
						@Override
						public String hoverHTML(Object value, ListGridRecord record, int rowNum,
								int colNum) {						

							if(record.getAttribute("iconpreferiti") != null) {
								if(record.getAttribute("iconpreferiti").equals("menu/preferiti_on.png")) {
									return I18NUtil.getMessages().removeFromPreferiti_prompt();
								} else if(record.getAttribute("iconpreferiti").equals("menu/preferiti_off.png")) {
									return I18NUtil.getMessages().addToPreferiti_prompt();								
								}		
							}						
							return null;
						}
					});
					
					parentmenu.setFields(icon, title, preferiti);
					
					createMenu(parentmenu, menu);
					item.setSubmenu(parentmenu);
					
					item.setAttribute("iconpreferiti", "menu/submenu.png");
					
				} else {						
					
					item.setAttribute("preferiti", menu.getAttributeAsBoolean("preferiti"));
					item.setAttribute("iconpreferiti", menu.getAttributeAsBoolean("preferiti") ? "menu/preferiti_on.png" : "menu/preferiti_off.png");		
					
//					if(Layout.isOpenedPortlet(nomeEntita)) {
//						item.set_baseStyle(it.eng.utility.Styles.openedMenuItem);	
//					} else {
//						item.set_baseStyle(it.eng.utility.Styles.menuItem);	
//					}
					
					item.addClickHandler(new ClickHandler() {
						@SuppressWarnings("unused")
						@Override
						public void onClick(final MenuItemClickEvent event) {		
							
							final String nomeEntita = event.getItem().getAttributeAsString("nomeEntita");
							Boolean preferiti = event.getItem().getAttributeAsBoolean("preferiti");
							
							MenuBean bean = vociMenu.get(nomeEntita);	
							
							if(event.getColNum()==2 && preferiti != null) {		
								
								String voceMenuBarraDesktop = getVoceSelezionataMenuBarraDesktop();
								final PreferenceMenuBarraDesktop voceSelez = PreferenceMenuBarraDesktop.fromValore(voceMenuBarraDesktop);
								
								final GWTRestDataSource preferenceMenuDS = UserInterfaceFactory.getPreferenceDataSource();
								preferenceMenuDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "preferences");	  									
								if (!preferiti) {		
									boolean canAddPreferences = true;
									if(true){									
										if(MainMenu.this.preferences.getMembers().length >= Layout.getGenericConfig().getNumMaxShortCut()){
											canAddPreferences = false;
											Layout.addMessage(new MessageBean("Non sono consentiti più di " + Layout.getGenericConfig().getNumMaxShortCut() + " tasti di accesso rapido", "", MessageType.ERROR));
										} 
									} else if(voceSelez == PreferenceMenuBarraDesktop.DESKTOP){									
										if(MainMenu.this.preferences.getMembers().length >= Layout.getGenericConfig().getMaxNumIconeDesktop()){
											canAddPreferences = false;
											Layout.addMessage(new MessageBean("Non sono consentiti più di " + Layout.getGenericConfig().getNumMaxShortCut() + " tasti di accesso rapido sul desktop", "", MessageType.ERROR));
										} 
									} else if(voceSelez == PreferenceMenuBarraDesktop.BARRA){									
										if(MainMenu.this.preferences.getMembers().length >= Layout.getGenericConfig().getNumMaxShortCut()) {
											canAddPreferences = false;
											Layout.addMessage(new MessageBean("Non sono consentiti più di " + Layout.getGenericConfig().getNumMaxShortCut() + " tasti di accesso rapido sulla barra strumenti", "", MessageType.ERROR));											
										}
									} 									
									if(canAddPreferences && MainMenu.this.preferences.addToPreference(bean)) {							
										//Cambio l'icona in on
										event.getItem().setAttribute("preferiti", true);
										event.getItem().setAttribute("iconpreferiti", "menu/preferiti_on.png");
										
										
									}
								} else {								
									MainMenu.this.preferences.removeFromPreference(bean);							
									//Cambio l'icona in off
									event.getItem().setAttribute("preferiti", false);
									event.getItem().setAttribute("iconpreferiti", "menu/preferiti_off.png");								
								}	
								
								if(voceSelez != PreferenceMenuBarraDesktop.BARRA){
									layout.addPortalDesktopButtons();
								}
								AdvancedCriteria criteria = new AdvancedCriteria();                             
						        criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT"); 
						        preferenceMenuDS.fetchData(criteria, new DSCallback() {   
						        	@Override  
						            public void execute(DSResponse response, Object rawData, DSRequest request) {   		        				        		
						        		Record[] data = response.getData();   		        		
      								      								                
						                if (data.length != 0) {   
						                	Record record = data[0];
						                	record.setAttribute("value", MainMenu.this.preferences.getPreferencesAsString());
						                	preferenceMenuDS.updateData(record);
						                } else {
						                	Record record = new Record();
						                	record.setAttribute("prefName", "DEFAULT");
						                	record.setAttribute("value", MainMenu.this.preferences.getPreferencesAsString());
						                	preferenceMenuDS.addData(record);
						                }					               
						            }   
						        });   								
								
							} 
							else {
								if(bean.getSubmenu() == null) {
									Layout.addPortlet(nomeEntita);
								}
							}						
							
						}
					});
				}
				root.addItem(item);		
				menuItems.put(item.getAttribute("nomeEntita"), item);				
			}			
		}		
	}	
	
	public Map<String, MenuBean> getVociMenu() {
		return vociMenu;
	}

	public void setVociMenu(Map<String, MenuBean> vociMenu) {
		this.vociMenu = vociMenu;
	}
	
	public MenuItem getMenuItem(String key) {
		return menuItems.get(key);
	}
	
	// Cambio l'img del menu quando è preferita o no
	private void changeIconMenu(MenuItemClickEvent event, boolean preferiti, String nomeEntita){
		
		MenuBean bean = vociMenu.get(nomeEntita);
		// se è barra menu
		if (!preferiti) {		
			
			if(MainMenu.this.preferences.addToPreference(bean)) {							
				//Cambio l'icona in on
				event.getItem().setAttribute("preferiti", true);
				event.getItem().setAttribute("iconpreferiti", "menu/preferiti_on.png");
			}
		} 
		else {				
			
//			if((MainMenu.this.getVociMenu().containsKey(nomeEntita)) || Layout.getMenuBarraDesktop().getPreferences().contains(nomeEntita) ){
			
				MainMenu.this.preferences.removeFromPreference(bean);						
				//Cambio l'icona in off
				event.getItem().setAttribute("preferiti", false);
				event.getItem().setAttribute("iconpreferiti", "menu/preferiti_off.png");
			}
//			else
//				Layout.addMessage(new MessageBean("Preferenza non presente nella barra" , "", MessageType.INFO));
//		}
		
	}
	
	/**
	 * Metodo che ritorna la shortcut relativa alla voce selezionata.
	 * @return B se la voce selezionata è barra, D se la voce selezionata è sul desktop
	 */
	private String getVoceSelezionataMenuBarraDesktop(){
		
		//Si imposta come valore di default quello della barra superiore
		String voceMenuBarraDesktop = layout.shortcutEnumDefault.getValore();
		
		if(layout.isAbilMenuBarraDesktop()) {	
			if ((Layout.getMenuBarraDesktop() != null) && (Layout.getMenuBarraDesktop().getMenuItems()  != null)){
				
				//Per ognuno dei due elementi (sul desktop o barra) si controlla quale è impostato in questo momento
				for(String key : Layout.getMenuBarraDesktop().getMenuItems().keySet()){				
					
					/*
					 * Per verificare quale è impostato si controlla il flag checked settato nel momento
					 * del click su una delle voci
					 */
					if(Layout.getMenuBarraDesktop().getMenuItems().get(key).getAttributeAsBoolean("checked")){
						
						//Dalla seguente istruzione viene ritornato B o D in base a cosa si sta considerando
						voceMenuBarraDesktop = Layout.getMenuBarraDesktop().getMenuItems().get(key).getAttributeAsString("shortcut");
						break;
					}
				}
			} else if (Layout.getMenuBarraDesktop() != null) {
					  voceMenuBarraDesktop = Layout.getMenuBarraDesktop().getSelectedPreference().getValore();
			}
		}
		return voceMenuBarraDesktop;
	}

}
