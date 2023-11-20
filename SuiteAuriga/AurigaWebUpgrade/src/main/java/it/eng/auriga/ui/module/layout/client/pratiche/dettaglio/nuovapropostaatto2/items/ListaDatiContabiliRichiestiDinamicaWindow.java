/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ListaDatiContabiliRichiestiDinamicaWindow extends ModalWindow {
	
	protected ListaDatiContabiliRichiestiDinamicaWindow window;
	
	protected DynamicForm datiContabiliRichiestiForm;		
	protected ListaDatiContabiliDinamicaItem listaDatiContabiliRichiestiItem;
	
	public ListaDatiContabiliRichiestiDinamicaWindow(String nomeEntita, Record attrLista, ArrayList<Map> dettAttrLista, RecordList listaDatiContabiliRichiesti) {
		
		super(nomeEntita, false);
		
		setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_datiStoriciWindow_title());  
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(300);
		setWidth(1000);
		
		datiContabiliRichiestiForm = new DynamicForm();
		datiContabiliRichiestiForm.setWidth100();
		datiContabiliRichiestiForm.setNumCols(10);
		datiContabiliRichiestiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliRichiestiForm.setWrapItemTitles(true);
		
		listaDatiContabiliRichiestiItem = new ListaDatiContabiliDinamicaItem(attrLista, dettAttrLista) {
			
			@Override
			public boolean isGrigliaEditabile() {
				return false;
			}
			
			@Override
			public boolean isShowRefreshListButton() {
				return false;
			}
		};
		listaDatiContabiliRichiestiItem.setStartRow(true);
		listaDatiContabiliRichiestiItem.setShowTitle(false);
		listaDatiContabiliRichiestiItem.setHeight(245);		
				
		datiContabiliRichiestiForm.setFields(listaDatiContabiliRichiestiItem);	
				
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(datiContabiliRichiestiForm);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		if(listaDatiContabiliRichiesti != null && listaDatiContabiliRichiesti.getLength() > 0) {
			Record record = new Record();
			record.setAttribute("listaDatiContabiliRichiesti", listaDatiContabiliRichiesti);
			datiContabiliRichiestiForm.editRecord(record);
		} else {
			datiContabiliRichiestiForm.editNewRecord();
		}
		
		listaDatiContabiliRichiestiItem.setCanEdit(false);
		
		setIcon("pratiche/task/buttons/datiStorici.png");		
	}
	
}
