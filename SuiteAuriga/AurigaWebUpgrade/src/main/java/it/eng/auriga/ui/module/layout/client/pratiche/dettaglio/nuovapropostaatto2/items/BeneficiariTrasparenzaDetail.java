/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class BeneficiariTrasparenzaDetail extends CustomDetail {
	
	protected ListaBeneficiariTrasparenzaItem gridItem;
	protected Record gridRecord;
	protected DynamicForm mDynamicForm;
	protected DestVantaggioItem listaBeneficiariTrasparenzaItem;
	
	public BeneficiariTrasparenzaDetail(final ListaBeneficiariTrasparenzaItem gridItem, String nomeEntita, Record record, final int riga, boolean canEdit) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		this.gridRecord = record;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
				
		listaBeneficiariTrasparenzaItem = new DestVantaggioItem() {
			
			@Override
			public boolean isBeneficiariTrasparenza() {
				return true;
			}
			
			@Override
			public boolean showTipo() {
				return gridItem.showTipo();
			}
			
			@Override
			public String getTitleTipo() {
				return gridItem.getTitleTipo();
			}
			
			@Override
			public String getDefaultValueTipo() {
				return (riga == 0) ? "mandatario" : "mandante";
			}
			
			@Override
			public boolean isRequiredTipo() {
				return gridItem.isRequiredTipo();
			}
			
			@Override
			public boolean isEditableTipo() {
				return gridItem.isEditableTipo();
			}
			
			@Override
			public boolean showTipoPersona() {
				return gridItem.showTipoPersona();
			}
			
			@Override
			public String getTitleTipoPersona() {				
				return gridItem.getTitleTipoPersona();
			}
			
			@Override
			public boolean isRequiredTipoPersona() {
				return gridItem.isRequiredTipoPersona();
			}
			
			@Override
			public HashMap<String, String> getValueMapTipoPersona() {
				return gridItem.getValueMapTipoPersona();
			}
			
			@Override
			public String getDefaultValueTipoPersona() {
				return gridItem.getDefaultValueTipoPersona();
			}
			
			@Override
			public boolean isEditableTipoPersona() {
				return gridItem.isEditableTipoPersona();
			}
			
			@Override
			public boolean showCognome() {
				return gridItem.showCognome();
			}
			
			@Override
			public String getTitleCognome() {
				return gridItem.getTitleCognome();
			}
			
			@Override
			public boolean isRequiredCognome() {
				return gridItem.isRequiredCognome();
			}
			
			@Override
			public boolean isEditableCognome() {
				return gridItem.isEditableCognome();
			}
			
			@Override
			public boolean showNome() {
				return gridItem.showNome();
			}
			
			@Override
			public String getTitleNome() {
				return gridItem.getTitleNome();
			}
			
			@Override
			public boolean isRequiredNome() {
				return gridItem.isRequiredNome();
			}
			
			@Override
			public boolean isEditableNome() {
				return gridItem.isEditableNome();
			}
			
			@Override
			public boolean showRagioneSociale() {
				return gridItem.showRagioneSociale();
			}
			
			@Override
			public String getTitleRagioneSociale() {
				return gridItem.getTitleRagioneSociale();
			}
			
			@Override
			public boolean isRequiredRagioneSociale() {
				return gridItem.isRequiredRagioneSociale();
			}
			
			@Override
			public boolean isEditableRagioneSociale() {
				return gridItem.isEditableRagioneSociale();
			}
			
			@Override
			public boolean showCodFiscalePIVA() {
				return gridItem.showCodFiscalePIVA();
			}
			
			@Override
			public String getTitleCodFiscalePIVA() {
				return gridItem.getTitleCodFiscalePIVA();
			}
			
			@Override
			public String getTitleCodFiscale() {
				return gridItem.getTitleCodFiscale();
			}
			
			@Override
			public boolean isRequiredCodFiscalePIVA() {
				return gridItem.isRequiredCodFiscalePIVA();
			}
			
			@Override
			public boolean isEditableCodFiscalePIVA() {
				return gridItem.isEditableCodFiscalePIVA();
			}
			 
			@Override
			public boolean showImporto() {
				return gridItem.showImporto();
			}
			
			@Override
			public String getTitleImporto() {
				return gridItem.getTitleImporto();
			}
			
			@Override
			public boolean isRequiredImporto() {
				return gridItem.isRequiredImporto();
			}
			
			@Override
			public boolean isEditableImporto() {
				return gridItem.isEditableImporto();
			}
			
			@Override
			public boolean showFlgPrivacy() {
				return gridItem.showFlgPrivacy();
			}
				
			@Override
			public String getTitleFlgPrivacy() {
				return gridItem.getTitleFlgPrivacy();
			}
			
			@Override
			public boolean getDefaultValueAsBooleanFlgPrivacy() {
				return gridItem.getDefaultValueAsBooleanFlgPrivacy();
			}		
			
			@Override
			public boolean isEditableFlgPrivacy() {
				return gridItem.isEditableFlgPrivacy();
			}				
		};
		listaBeneficiariTrasparenzaItem.setName("listaBeneficiariTrasparenza");
		listaBeneficiariTrasparenzaItem.setShowTitle(false);
		listaBeneficiariTrasparenzaItem.setColSpan(20);
		listaBeneficiariTrasparenzaItem.setAttribute("obbligatorio", true);
		listaBeneficiariTrasparenzaItem.setNotReplicable(true);			
		
		mDynamicForm.setFields(listaBeneficiariTrasparenzaItem);
				
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
		
		Record recordBeneficiario = new Record();		
		for(String attributo : record.getAttributes()) {
			recordBeneficiario.setAttribute(attributo, record.getAttribute(attributo));
		}
		// devo passare dal valore boolean del check nel form al valore string ("true"/"false") della colonna in lista
		recordBeneficiario.setAttribute("flgPrivacy", record.getAttribute("flgPrivacy") != null && "true".equalsIgnoreCase(record.getAttribute("flgPrivacy")));
		
		RecordList recordList = new RecordList();
		recordList.add(recordBeneficiario);
					
		Record recordToEdit = new Record();
		recordToEdit.setAttribute("listaBeneficiariTrasparenza", recordList);
		
		super.editRecord(recordToEdit);
	}
	
	@Override
	public Record getRecordToSave() {
		
		Record lRecordToSave = super.getRecordToSave();
		
		RecordList recordList = lRecordToSave.getAttributeAsRecordList("listaBeneficiariTrasparenza");
		for(int i = 0; i < recordList.getLength(); i++) {
			Record recordBeneficiario = recordList.get(i);
			// devo passare dal valore string ("true"/"false") della colonna in lista al valore boolean del check nel form
			recordBeneficiario.setAttribute("flgPrivacy", recordBeneficiario.getAttributeAsBoolean("flgPrivacy") != null && recordBeneficiario.getAttributeAsBoolean("flgPrivacy") ? "true" : "false");
		}
		
		return lRecordToSave;
	}
	
}