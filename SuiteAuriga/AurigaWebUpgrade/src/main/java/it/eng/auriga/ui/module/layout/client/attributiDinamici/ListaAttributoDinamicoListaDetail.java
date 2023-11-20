/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas.ReplicableCanvasForm;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ListaAttributoDinamicoListaDetail extends CustomDetail {
	
	protected ListaAttributoDinamicoListaItem gridItem;
	
	protected DynamicForm mDynamicForm;
	protected AttributoListaItem listaAttributoDinamicoListaItem;
	
	public ListaAttributoDinamicoListaDetail(String nomeEntita, final ListaAttributoDinamicoListaItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
		
		listaAttributoDinamicoListaItem = new AttributoListaItem(null, gridItem.getDatiDettLista()) {
			
			@Override
			public boolean showCanvasSection() {
				return false;
			}
			
			@Override
			public boolean isGestioneContenutiTabellaTrasp() {
				return isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp();
			}

			
		};
		listaAttributoDinamicoListaItem.setName("listaAttributoDinamicoLista");
		listaAttributoDinamicoListaItem.setShowTitle(false);
		listaAttributoDinamicoListaItem.setNotReplicable(true);
		listaAttributoDinamicoListaItem.setShowDuplicaRigaButton(false);
		listaAttributoDinamicoListaItem.setAttribute("obbligatorio", true);
		
		mDynamicForm.setFields(listaAttributoDinamicoListaItem);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
		
	@Override
	public void editRecord(Record record) {
		RecordList lRecordList = new RecordList();
		lRecordList.add(record);
		listaAttributoDinamicoListaItem.drawAndSetValue(lRecordList);		
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = mDynamicForm.getValueAsRecordList("listaAttributoDinamicoLista").get(0);
		
		ReplicableCanvas[] rcd = listaAttributoDinamicoListaItem.getAllCanvas();
		AttributoListaCanvas rc = (AttributoListaCanvas )rcd[0];
		ReplicableCanvasForm[] rcf = rc.getForm();
		for (ReplicableCanvasForm replicableCanvasForm : rcf) {
			FormItem[] allItems = replicableCanvasForm.getFields();
			for (FormItem item : allItems) {
				if (item instanceof ReplicableItem) {
					RecordList lRecordList = ((ReplicableItem) item).getValueAsRecordList();
					String lName = item.getName();
					lRecordToSave.setAttribute(lName, lRecordList);
				} else if (item instanceof ListaAttributoDinamicoListaItem) {
					lRecordToSave.setAttribute(item.getName(), ((ListaAttributoDinamicoListaItem) item).getValueAsRecordList());
				} else if (item instanceof CKEditorItem) {
					lRecordToSave.setAttribute(item.getName(), item.getValue());
				}
			}
		}
		return lRecordToSave;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		listaAttributoDinamicoListaItem.setCanEdit(canEdit);
	}
	
	@Override
	public boolean customValidate() {
		listaAttributoDinamicoListaItem.removeEmptyCanvas();
		return super.customValidate() && customValidateItemTrasp();
	}

	public boolean isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp() {
		return false;
	}
	
	public boolean customValidateItemTrasp() {
		return true;
	}
}