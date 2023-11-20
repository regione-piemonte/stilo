/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class PermessiConfigFlussiItem extends ReplicableItem{
	
	public PermessiConfigFlussiItem() {
		super();		
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		PermessiConfigFlussiCanvas lPermessiCanvas = new PermessiConfigFlussiCanvas(this);
		return lPermessiCanvas;
	}

	@Override
	protected ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[2];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("[SKIN]actions/add.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});
		
		addButtons[1] = new ImgButton();   
		addButtons[1].setSrc("lookup/organigrammamulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				PermessiMultiLookupOrganigramma lookupOrganigrammaPopup = new PermessiMultiLookupOrganigramma(null);				
				lookupOrganigrammaPopup.show(); 	
			}   
		});
		
		return addButtons;		
	}
	
	public class PermessiMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<PermessiConfigFlussiCanvas> multiLookupList = new ArrayList<PermessiConfigFlussiCanvas>(); 
		
		public PermessiMultiLookupOrganigramma(Record record) {
			super(record, false, new Integer(1));			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			PermessiConfigFlussiCanvas lastCanvas = (PermessiConfigFlussiCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				PermessiConfigFlussiCanvas canvas = (PermessiConfigFlussiCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(PermessiConfigFlussiCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}		
	
		@Override
		public String getFinalita() {
			return "ACL";
		}
		
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		setEditing(canEdit);
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if(lVLayoutMember instanceof HLayout) {				
					RemoveButton lRemoveButton = null;
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ImgButton) {
							if(i == (lVLayout.getMembers().length - 1)) {
								if(canEdit) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}									
							} else {
								lRemoveButton = ((RemoveButton)lHLayoutMember);	
								if(canEdit) {
									lRemoveButton.show();
								} else {
									lRemoveButton.hide();
								}	
							}																			
						} else if(lHLayoutMember instanceof ReplicableCanvas) {
							PermessiConfigFlussiCanvas lPermessiCanvas = (PermessiConfigFlussiCanvas)lHLayoutMember;
							lPermessiCanvas.setCanEdit(canEdit);													
						}							
					}	
				}			
			}
		}
	}

	@Override
	public Record getCanvasDefaultRecord() {
		if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecord = new Record();	
			lRecord.setAttribute("codiceUo", AurigaLayout.getCodRapidoOrganigramma());
			return lRecord;				
		}
		return null;
	}
	
	public int getFilteredSelectItemWidth(){
		return 400;
	}
	
	public abstract String getCodTipoFlusso();
	
}
