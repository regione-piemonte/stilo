/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;

public class ModalWindow  extends Portlet {
	
	public ModalWindow(String nomeEntita) {  
		this(nomeEntita, false, true);
	}
	
	public ModalWindow(final String nomeEntita, boolean isJustWindow) {
		this(nomeEntita, isJustWindow, true);
	}
	
	public ModalWindow(final String nomeEntita, boolean isJustWindow, boolean showPreference) {
		
			super(nomeEntita, isJustWindow, showPreference);
						
			setAutoCenter(true);
			
			settingsMenu.removeItem(setHomepageMenuItem);
			
			GenericConfigBean config = Layout.getGenericConfig();
	        setWidth(config.getDefaultPortletWidth());   
	        setHeight(config.getDefaultPortletHeight());	
	        	        
	        if(showPreference)	{

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
	        
	        setIsModal(true);
	        setShowModalMask(true);
	        setModalMaskOpacity(50);
	         
	        HeaderControl settingsHeaderControl = new HeaderControl(HeaderControl.SETTINGS, new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
						
					settingsMenu.showContextMenu();				
				}
			});   
	        settingsHeaderControl.setCursor(Cursor.HAND);    
	        
//	        HeaderControl helpHeaderControl = new HeaderControl(HeaderControl.HELP, new ClickHandler() {			
//				@Override
//				public void onClick(ClickEvent event) {
//					
//					SC.say("TO DO...");
//				}
//			});
//	        helpHeaderControl.setCursor(Cursor.HAND);
	         
	        if (showPreference) {
	        	setHeaderControls(HeaderControls.HEADER_ICON, 
	        					  HeaderControls.HEADER_LABEL, 
	        				      HeaderControls.MAXIMIZE_BUTTON, 
	        				      settingsHeaderControl, 
//	        				      helpHeaderControl, 
	        				      HeaderControls.CLOSE_BUTTON);    	        
	        }
	        else {
	        	setHeaderControls(HeaderControls.HEADER_ICON, 
	        	HeaderControls.HEADER_LABEL, 
			    HeaderControls.MAXIMIZE_BUTTON, 
			    HeaderControls.CLOSE_BUTTON);    
	        }
	
	        addVisibilityChangedHandler(new VisibilityChangedHandler() {				
				@Override
				public void onVisibilityChanged(VisibilityChangedEvent event) {
					
					if(getForm() != null) {
						getForm().refreshTabIndex(1);
					}
				}
			});
	}
	
	public DynamicForm getForm() {
		return null;
	}
	
	
	@Override
	public void show() {

		super.show();
		Layout.hideWaitPopup();
		if (UserInterfaceFactory.isAttivaAccessibilita()){
//			focus();
			focusInNextTabElement();
		}
	}
}
