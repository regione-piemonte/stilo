/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class DatiContabiliStoriciWindow extends ModalWindow {
	
	protected DatiContabiliStoriciWindow window;
	
	protected DynamicForm datiContabiliStoriciForm;		
	protected ListaInvioDatiSpesaItem listaDatiContabiliStoriciItem;
	
	public DatiContabiliStoriciWindow(String nomeEntita, RecordList listaDatiContabiliStorici) {
		
		super(nomeEntita, false);
		
		setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_datiStoriciWindow_title());  
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(300);
		setWidth(1000);
		
		datiContabiliStoriciForm = new DynamicForm();
		datiContabiliStoriciForm.setWidth100();
		datiContabiliStoriciForm.setNumCols(10);
		datiContabiliStoriciForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliStoriciForm.setWrapItemTitles(true);
		
		listaDatiContabiliStoriciItem = new ListaInvioDatiSpesaItem("listaDatiContabiliStorici") {
			
			@Override
			public String getSIBDataSourceName() {
				return window.getSIBDataSourceName();
			}					
			
			@Override
			public boolean isGrigliaEditabile() {
				return false;
			}
			
			@Override
			public boolean isShowRefreshListButton() {
				return false;
			}
		};
		listaDatiContabiliStoriciItem.setStartRow(true);
		listaDatiContabiliStoriciItem.setShowTitle(false);
		listaDatiContabiliStoriciItem.setHeight(245);		
				
		datiContabiliStoriciForm.setFields(listaDatiContabiliStoriciItem);	
				
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(datiContabiliStoriciForm);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		if(listaDatiContabiliStorici != null && listaDatiContabiliStorici.getLength() > 0) {
			Record record = new Record();
			record.setAttribute("listaDatiContabiliStorici", listaDatiContabiliStorici);
			datiContabiliStoriciForm.editRecord(record);
		} else {
			datiContabiliStoriciForm.editNewRecord();
		}
		
		listaDatiContabiliStoriciItem.setCanEdit(false);
		
		setIcon("pratiche/task/buttons/datiStorici.png");		
	}
	
	public abstract String getSIBDataSourceName();
	
}
