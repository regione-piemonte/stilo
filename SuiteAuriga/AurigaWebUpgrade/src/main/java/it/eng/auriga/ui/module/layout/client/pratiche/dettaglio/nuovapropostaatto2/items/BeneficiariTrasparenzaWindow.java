/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class BeneficiariTrasparenzaWindow extends ModalWindow {
	
	protected BeneficiariTrasparenzaWindow window;
	protected BeneficiariTrasparenzaDetail detail;	
	protected ListaBeneficiariTrasparenzaItem gridItem;
	protected ToolStrip detailToolStrip;
		
	public BeneficiariTrasparenzaWindow(ListaBeneficiariTrasparenzaItem gridItem, String nomeEntita, final Record record, int riga, boolean canEdit) {
		
		super(nomeEntita, false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(240);
		setWidth(1300);
		setOverflow(Overflow.AUTO);    			
				
		window = this;
		this.gridItem = gridItem;
		
		final boolean isNew = (record == null);
						
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " beneficiario");				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " beneficiario");	
			}			
		} else {
			setTitle("Nuovo beneficiario");
		}
		
		detail = new BeneficiariTrasparenzaDetail(gridItem, nomeEntita, record, riga, canEdit);		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			final DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					if(detail.validate()) {						
						final Record lRecord = detail.getRecordToSave();								
						RecordList listaBeneficiariTrasparenza = lRecord.getAttributeAsRecordList("listaBeneficiariTrasparenza");
						saveData(listaBeneficiariTrasparenza.get(0));						
						window.markForDestroy();																
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					}
					
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					window.manageOnCloseClick();
				}
			});
			
			ToolStrip detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right		
			detailToolStrip.addButton(saveButton);
			detailToolStrip.addButton(annullaButton);
			
			VLayout detailLayout = new VLayout();  
			detailLayout.setOverflow(Overflow.HIDDEN);		
			detailLayout.setHeight100();
			detailLayout.setWidth100();		
			detailLayout.setMembers(detail, detailToolStrip);	
			
			setBody(detailLayout);
		} else {
			setBody(detail);
		}
		
		setIcon("blank.png");		
	}
	
	public void saveData(Record record) {
		
	}
	
}
