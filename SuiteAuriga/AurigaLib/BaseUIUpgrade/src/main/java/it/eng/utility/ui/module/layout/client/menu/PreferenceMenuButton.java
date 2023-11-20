/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.events.DragStopEvent;
import com.smartgwt.client.widgets.events.DragStopHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Item menu dei preferiti
 * @author michele
 *
 */
public class PreferenceMenuButton extends ToolStripButton {
	
	private String nomeEntita;
	
	public PreferenceMenuButton(String nomeEntita, String title, String icon, final PreferenceMenu menu) {
		super();
		setBaseStyle(it.eng.utility.Styles.preferenceMenuButton);		
		setIcon(icon);
		setIconSize(24);
		setPrompt(title);
		setNomeEntita(nomeEntita);
		setCanFocus(false);
		setTabIndex(-1);
		setCanDrag(true);
		setCanDrop(true);
		setDragType("PreferenceMenuButton");
		addDragStopHandler(new DragStopHandler() {			
			@Override
			public void onDragStop(DragStopEvent event) {
				
				final GWTRestDataSource preferenceMenuDS = UserInterfaceFactory.getPreferenceDataSource();
				preferenceMenuDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "preferences");	                 						
				AdvancedCriteria criteria = new AdvancedCriteria();                             
		        criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT"); 
		        preferenceMenuDS.fetchData(criteria, new DSCallback() {   
		        	@Override  
		            public void execute(DSResponse response, Object rawData, DSRequest request) {   		        				        		
		        		Record[] data = response.getData();   		        		
		        				        								      								                
		                if (data.length != 0) {   
		                	Record record = data[0];
		                	record.setAttribute("value", menu.getPreferencesAsString());
		                	preferenceMenuDS.updateData(record);
		                } 				               
		            }   
		        });   
			}
		});
	}
	
    public void setNomeEntita(String nomeEntita) {
    	PreferenceMenuButton.this.nomeEntita = nomeEntita;
    }
    
    public String getNomeEntita()  {
        return nomeEntita;
    }
	
}