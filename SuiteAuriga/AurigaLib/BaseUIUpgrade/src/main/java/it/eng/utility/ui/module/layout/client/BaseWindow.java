/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.JSONDateFormat;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

public class BaseWindow extends Window
{

	
	protected Menu settingsMenu;
	protected MenuItem saveDimPosMenuItem;
	
	protected String portletOpened;
	protected Boolean offset;
	
	public String getPortletOpened() {
		return portletOpened;
	}

	public void setPortletOpened(String portletOpened) {
		this.portletOpened = portletOpened;
	}

	public Boolean isOffset() {
		return offset;
	}

	public void setOffset(Boolean offset) {
		this.offset = offset;
	}
	
	public BaseWindow(String nomeEntita) {  
		this(nomeEntita, true);
	}

	public BaseWindow(final String nomeEntita, boolean showPreference) {
		
		
		setOffset(false);
					
		setAutoCenter(true);
				
		GenericConfigBean config = Layout.getGenericConfig();
        setWidth(config.getDefaultPortletWidth());   
        setHeight(config.getDefaultPortletHeight());	
        	        
        if(showPreference)	{
        	
        	
        	saveDimPosMenuItem = new MenuItem(I18NUtil.getMessages().saveDimPosMenuItem_title(), "menu/dimpos.png");

			final GWTRestDataSource layoutPortletDS = UserInterfaceFactory.getPreferenceDataSource();
			layoutPortletDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");
			saveDimPosMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
					layoutPortletDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							Record portletDimPos = new Record();
							if (getMaximized() != null && getMaximized()) {
								portletDimPos.setAttribute("maximized", true);								
							} else {
								portletDimPos.setAttribute("maximized", false);
								int pageLeft = getPageLeft();
								int pageTop = getPageTop() - 32;
								portletDimPos.setAttribute("pageLeft", pageLeft >= 0 ? pageLeft : 0);
								portletDimPos.setAttribute("pageTop", pageTop >= 0 ? pageTop : 0);
								portletDimPos.setAttribute("width", getWidth());
								portletDimPos.setAttribute("height", getHeight());								
							}								
							JSONEncoder encoder = new JSONEncoder();
							encoder.setDateFormat(JSONDateFormat.DATE_CONSTRUCTOR);
							if (data.length != 0) {
								Record record = data[0];
								record.setAttribute("value", JSON.encode(portletDimPos.getJsObj(), encoder));
								layoutPortletDS.updateData(record);
							} else {
								Record record = new Record();
								record.setAttribute("prefName", "DEFAULT");
								record.setAttribute("value", JSON.encode(portletDimPos.getJsObj(), encoder));
								layoutPortletDS.addData(record);
							}															
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSaveDimPos_message(), "", MessageType.INFO));
						}
					});
				}
			});
    		
    		
        	settingsMenu = new Menu();
    		settingsMenu.setOverflow(Overflow.VISIBLE);
    		settingsMenu.setShowIcons(true);
    		settingsMenu.setSelectionType(SelectionStyle.SINGLE);
    		settingsMenu.setCanDragRecordsOut(false);
    		settingsMenu.setWidth("*");
    		settingsMenu.setHeight("*");

    		settingsMenu.addItem(saveDimPosMenuItem);

        	Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        		@Override
        		public void execute() {		
        			final GWTRestDataSource layoutPortletDS = UserInterfaceFactory.getPreferenceDataSource();
        			layoutPortletDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");
		        
        			final GWTRestDataSource layoutPortletDefaultDS = UserInterfaceFactory.getPreferenceDataSource();
        			layoutPortletDefaultDS.addParam("userId", "DEFAULT");
        			layoutPortletDefaultDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + nomeEntita + ".layout.window");		
        			layoutPortletDefaultDS.addParam("prefName", "DEFAULT");        
		        
        			AdvancedCriteria criteria = new AdvancedCriteria();                             
        			criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");                     	        
        			layoutPortletDS.fetchData(criteria, new DSCallback() {   
        				@Override  
        				public void execute(DSResponse response, Object rawData, DSRequest request) {   
        					Record[] data = response.getData();   
        					if (data.length > 0 && data[0].getAttribute("value") != null) {   
        						Record record = new Record(JSON.decode(data[0].getAttribute("value")));
        						if(record.getAttributeAsBoolean("maximized")) {	
        							setWidth100();   
        							setHeight100();	 
        							setTop(0);
        							setLeft(0);
        							Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        								@Override
        								public void execute() {		
        									maximize();
        								}
        							});
        						} else {
        							int pageLeft = Integer.parseInt(record.getAttribute("pageLeft"));	    								
									int pageTop = Integer.parseInt(record.getAttribute("pageTop"));	    								
									setPageLeft(pageLeft >= 0 ? pageLeft : 0);
        							setPageTop(pageTop >= 0 ? pageTop : 0);
        							int width = Integer.parseInt(record.getAttribute("width"));
        							int height = Integer.parseInt(record.getAttribute("height"));
        							setWidth100();
        							setHeight100();                	
        							if(width < getWidth()) {
        								setWidth(width);
        							}
        							if(height < getHeight()) {
        								setHeight(height);
        							}                	
        							setOffset(false);
        						}
        					} else {
        						layoutPortletDefaultDS.fetchData(null, new DSCallback() {      
        							@Override  
        							public void execute(DSResponse response, Object rawData, DSRequest request) {   
        								Record[] data = response.getData();   
        								if (data.length > 0 && data[0].getAttribute("value") != null) {   
        									Record record = new Record(JSON.decode(data[0].getAttribute("value")));
        									if(record.getAttributeAsBoolean("maximized")) {	
        										setWidth100();   
        										setHeight100();	 
        										setTop(0);
        										setLeft(0);
        										Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        											@Override
        											public void execute() {		
        												maximize();
        											}
        										});
        									} else {
        										int pageLeft = Integer.parseInt(record.getAttribute("pageLeft"));	    								
        										int pageTop = Integer.parseInt(record.getAttribute("pageTop"));	    								
        										setPageLeft(pageLeft >= 0 ? pageLeft : 0);
        	        							setPageTop(pageTop >= 0 ? pageTop : 0);
        										int width = Integer.parseInt(record.getAttribute("width"));
        										int height = Integer.parseInt(record.getAttribute("height"));
        										setWidth100();
        										setHeight100();                	
        										if(width < getWidth()) {
        											setWidth(width);
        										}
        										if(height < getHeight()) {
        											setHeight(height);
        										}                	
        										setOffset(false);
        									}
        								}
        							}   
        						});   
        					}
        				}   
        			});
        		}
        	});	
        }

        int left = 5;
		int top = 5;
        setTop(isOffset()?top + 20:top);
        setLeft(isOffset()?left+20:left);
        setVisible(false);
        setCanDragReposition(true);  
        setCanDragResize(true);   
        setRedrawOnResize(true);
        setKeepInParentRect(true);
        setAutoDraw(true);
        setShowMinimizeButton(false);
        
        setIsModal(isModal());
        setShowModalMask(true);
        setModalMaskOpacity(50);
         
        HeaderControl settingsHeaderControl = new HeaderControl(HeaderControl.SETTINGS, new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
					
				settingsMenu.showContextMenu();				
			}
		});   
        settingsHeaderControl.setCursor(Cursor.HAND);    
         
        if (showPreference) {
        	setHeaderControls(HeaderControls.HEADER_ICON, 
        					  HeaderControls.HEADER_LABEL, 
        				      HeaderControls.MAXIMIZE_BUTTON, 
        				      settingsHeaderControl, 
        				      HeaderControls.CLOSE_BUTTON);    	        
        }
        else {
        	setHeaderControls(HeaderControls.HEADER_ICON, 
        	HeaderControls.HEADER_LABEL, 
		    HeaderControls.MAXIMIZE_BUTTON, 
		    HeaderControls.CLOSE_BUTTON);    
        }

	}
	
	public boolean isModal() {
		return true;
	}

}
