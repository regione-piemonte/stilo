/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DatiIstanzeConcorrentiSUAADSPWindow extends ModalWindow {
	
	protected DatiIstanzeConcorrentiSUAADSPWindow window;
	
	protected DynamicForm datiIstanzeConcorrentiSUAADSPForm;
	protected ListaDatiIstanzeConcorrentiSUAADSPItem listaDatiIstanzeConcorrentiSUAADSPItem;
	
	public DatiIstanzeConcorrentiSUAADSPWindow(String nomeEntita, RecordList listaDatiIstanzeConcorrenti) {
		
		super(nomeEntita, false);
		
		setTitle("Istanze concorrenti in avvio comparativo");  
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(300);
		setWidth(1000);
		
		datiIstanzeConcorrentiSUAADSPForm = new DynamicForm();
		datiIstanzeConcorrentiSUAADSPForm.setWidth100();
		datiIstanzeConcorrentiSUAADSPForm.setNumCols(10);
		datiIstanzeConcorrentiSUAADSPForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiIstanzeConcorrentiSUAADSPForm.setWrapItemTitles(true);
		
		listaDatiIstanzeConcorrentiSUAADSPItem = new ListaDatiIstanzeConcorrentiSUAADSPItem("listaDatiIstanzeConcorrentiSUAADSP") {
			
			@Override
			public boolean isGrigliaEditabile() {
				return false;
			}
		};
		listaDatiIstanzeConcorrentiSUAADSPItem.setStartRow(true);
		listaDatiIstanzeConcorrentiSUAADSPItem.setShowTitle(false);
		listaDatiIstanzeConcorrentiSUAADSPItem.setHeight(245);		
				
		datiIstanzeConcorrentiSUAADSPForm.setFields(listaDatiIstanzeConcorrentiSUAADSPItem);	
				
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(datiIstanzeConcorrentiSUAADSPForm);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);
		
		if(listaDatiIstanzeConcorrenti != null && listaDatiIstanzeConcorrenti.getLength() > 0) {
			Record record = new Record();
			record.setAttribute("listaDatiIstanzeConcorrentiSUAADSP", listaDatiIstanzeConcorrenti);
			datiIstanzeConcorrentiSUAADSPForm.editRecord(record);
		} else {
			datiIstanzeConcorrentiSUAADSPForm.editNewRecord();
		}
		
		listaDatiIstanzeConcorrentiSUAADSPItem.setCanEdit(false);
		
		setIcon("buttons/altriDati.png");		
	}
	
}
