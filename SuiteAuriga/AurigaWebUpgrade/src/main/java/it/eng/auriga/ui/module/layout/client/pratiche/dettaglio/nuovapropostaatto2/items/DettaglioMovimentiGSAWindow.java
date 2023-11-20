/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
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

public class DettaglioMovimentiGSAWindow extends ModalWindow {
	
	protected DettaglioMovimentiGSAWindow window;
	protected DettaglioMovimentiGSADetail detail;	
	protected ToolStrip detailToolStrip;
	
	public DettaglioMovimentiGSAWindow(ListaMovimentiGSAItem gridItem, String nomeEntita, boolean canEdit, Record record) {
		
		super(nomeEntita, false);
		
		if(record != null) {
			setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));
		} else {
			setTitle("Nuovo movimento");
		}
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(600);
		setWidth(1400);
		setOverflow(Overflow.AUTO);    			
		
		detail = new DettaglioMovimentiGSADetail(nomeEntita, gridItem);		
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
						saveData(lRecord);		
						window.manageOnCloseClick();										
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
		
		if(record != null) {
			detail.editRecord(record);
		} else {
			detail.editNewRecord();
		}
		
		detail.setCanEdit(canEdit);
		
		setIcon("blank.png");		
	}
	
	public void saveData(Record record) {
		
	}

	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if(record.getAttribute("tipoMovimento") != null && !"".equals(record.getAttribute("tipoMovimento"))) {
			estremi += record.getAttribute("tipoMovimento") + " ";
		}
		if(record.getAttribute("annoMovimento") != null && !"".equals(record.getAttribute("annoMovimento"))) {
			estremi += record.getAttribute("annoMovimento") + " ";
		}	
		if(record.getAttribute("numeroMovimento") != null && !"".equals(record.getAttribute("numeroMovimento"))) {
			estremi += "N. " + record.getAttribute("numeroMovimento") + " ";
		}
		if(record.getAttribute("numeroSub") != null && !"".equals(record.getAttribute("numeroSub"))) {
			estremi += "Sub " + record.getAttribute("numeroSub") + " ";
		}
		if(record.getAttribute("numeroModifica") != null && !"".equals(record.getAttribute("numeroModifica"))) {
			estremi += "Modifica N. " + record.getAttribute("numeroModifica") + " ";
		}		
		return estremi;
	}
	
}
