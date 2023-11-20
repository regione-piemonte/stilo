/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class ProponentiDetail extends CustomDetail {
	
	protected ListaProponentiItem gridItem;
	protected Record gridRecord;
	protected DynamicForm mDynamicForm;
	protected ProponentiItem listaProponentiItem;
	
	public ProponentiDetail(final ListaProponentiItem gridItem, String nomeEntita, Record record, boolean canEdit) {
		
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailSection); // altrimenti si vede il contrasto con le ripetibili listaRdP e listaDirigenti in ProponentiItem
		
		this.gridItem = gridItem;
		this.gridRecord = record;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
				
		listaProponentiItem = new ProponentiItem() {
			
			@Override
			public boolean isFromProponentiDetailInGridItem() {
				return true;
			}
			
			@Override
			public boolean isAbilSelezioneProponentiEstesa() {
				return gridItem.isAbilSelezioneProponentiEstesa();
			}
						
			@Override
			public String getIdTipoDocProposta() {
				return gridItem.getIdTipoDocProposta();
			}
			
			@Override
			public LinkedHashMap<String, String> getProponentiValueMap() {
				return gridItem.getProponentiValueMap();
			}
			
			@Override
			public LinkedHashMap<String, String> getSelezioneProponentiValueMap() {
				return gridItem.getSelezioneProponentiValueMap();	
			}
			
			@Override
			public LinkedHashMap<String, String> getFlgUfficioGareProponentiMap() {
				return gridItem.getFlgUfficioGareProponentiMap();
			}
			
			@Override
			public boolean showIdUo() {
				return gridItem.showIdUo();
			}
			
			@Override
			public String getTitleIdUo() {
				return gridItem.getTitleIdUo();
			}
			
			@Override
			public boolean isRequiredIdUo() {
				return gridItem.isRequiredIdUo();
			}
			
			@Override
			public String getAltriParamLoadComboIdUo() {
				return gridItem.getAltriParamLoadComboIdUo();
			}
			
			@Override
			public boolean isEditableIdUo() {
				return gridItem.isEditableIdUo();
			}
					
			@Override
			public boolean showIdScrivaniaRdP() {
				return gridItem.showIdScrivaniaRdP();
			}
			
			@Override
			public String getTitleIdScrivaniaRdP() {
				return gridItem.getTitleIdScrivaniaRdP();
			}
			
			@Override
			public boolean isRequiredIdScrivaniaRdP() {
				return gridItem.isRequiredIdScrivaniaRdP();
			}
			
			@Override
			public String getAltriParamLoadComboIdScrivaniaRdP() {
				return gridItem.getAltriParamLoadComboIdScrivaniaRdP();
			}		
			
			@Override
			public boolean isEditableIdScrivaniaRdP() {
				return gridItem.isEditableIdScrivaniaRdP();
			}
			
			@Override
			public boolean showIdScrivaniaDirigente() {
				return gridItem.showIdScrivaniaDirigente();
			}
			
			@Override
			public String getTitleIdScrivaniaDirigente() {
				return gridItem.getTitleIdScrivaniaDirigente();
			}
			
			@Override
			public boolean isRequiredIdScrivaniaDirigente() {
				return gridItem.isRequiredIdScrivaniaDirigente();
			}
			
			@Override
			public String getAltriParamLoadComboIdScrivaniaDirigente() {
				return gridItem.getAltriParamLoadComboIdScrivaniaDirigente();
			}
			
			@Override
			public boolean isEditableIdScrivaniaDirigente() {
				return gridItem.isEditableIdScrivaniaDirigente();
			}
			
			@Override
			public boolean showIdScrivaniaDirettore() {
				return gridItem.showIdScrivaniaDirettore();
			}
			
			@Override
			public String getTitleIdScrivaniaDirettore() {
				return gridItem.getTitleIdScrivaniaDirettore();
			}
			
			@Override
			public boolean isRequiredIdScrivaniaDirettore() {
				return gridItem.isRequiredIdScrivaniaDirettore();
			}
			
			@Override
			public String getAltriParamLoadComboIdScrivaniaDirettore() {
				return gridItem.getAltriParamLoadComboIdScrivaniaDirettore();
			}
			
			@Override
			public boolean isEditableIdScrivaniaDirettore() {
				return gridItem.isEditableIdScrivaniaDirettore();
			}
			
			@Override
			public boolean showTipoVistoScrivaniaDirigente() {
				return gridItem.showTipoVistoScrivaniaDirigente();
			}
			
			@Override
			public boolean isRequiredTipoVistoScrivaniaDirigente() {
				return gridItem.isRequiredTipoVistoScrivaniaDirigente();
			}
			
			@Override
			public boolean showTipoVistoScrivaniaDirettore() {
				return gridItem.showTipoVistoScrivaniaDirettore();
			}
			
			@Override
			public boolean isRequiredTipoVistoScrivaniaDirettore() {
				return gridItem.isRequiredTipoVistoScrivaniaDirettore();
			}
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return gridItem.getSelectItemOrganigrammaWidth();
			}
			
			@Override
			public void manageChangedUoSelezionata() {						
				gridItem.manageChangedUoSelezionata();
			}
		};
		listaProponentiItem.setName("listaProponenti");
		listaProponentiItem.setShowTitle(false);
		listaProponentiItem.setColSpan(20);
		listaProponentiItem.setAttribute("obbligatorio", true);
		listaProponentiItem.setNotReplicable(true);			
		
		mDynamicForm.setFields(listaProponentiItem);
				
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
		
		if(record != null) {
			editRecord(record);
		} else {
			editNewRecord();
		}
		
		setCanEdit(canEdit);				
	}
	
	@Override
	public void editRecord(Record record) {
		
		//TODO
		
		Record recordProponente = new Record();		
		for(String attributo : record.getAttributes()) {
			if("listaRdP".equals(attributo) || "listaDirigenti".equals(attributo) || "listaDirettori".equals(attributo)) {
				recordProponente.setAttribute(attributo, record.getAttributeAsRecordList(attributo));
			} else {
				recordProponente.setAttribute(attributo, record.getAttribute(attributo));
			}
		}
		
		RecordList recordList = new RecordList();
		recordList.add(recordProponente);
					
		Record recordToEdit = new Record();
		recordToEdit.setAttribute("listaProponenti", recordList);
		
		super.editRecord(recordToEdit);
	}
	
	@Override
	public Record getRecordToSave() {
		
		//TODO
		
		Record lRecordToSave = super.getRecordToSave();
		
//		RecordList recordList = lRecordToSave.getAttributeAsRecordList("listaProponenti");
//		for(int i = 0; i < recordList.getLength(); i++) {
//			Record recordProponente = recordList.get(i);
//			
//		}
		
		return lRecordToSave;
	}
	
}