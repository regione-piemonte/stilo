/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RelativeDate;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem.RemoveButton;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

/**
 * Canvas generico gestito da ReplicableItem il quale è in grado di replicare infinite volte.
 * 
 * Quando viene creato, viene chiamato il metodo disegna, che ha il compito di aggiungere al canvas gli elementi grafici.
 * 
 * Il metodo GetForm ha il compito di dire quali form devono essere gestiti dal {@link ReplicableItem}
 * 
 * @author Rametta
 *
 */
public abstract class ReplicableCanvas extends Canvas {

	private ValuesManager vmCanvas;

	private ReplicableCanvas instance;
	private ReplicableItem item;
	private VLayout lVLayout;
	private HLayout lHLayout;
	private RemoveButton removeButton;
	private ImgButton upButton;
	private ImgButton downButton;
	private ImgButton duplicaRigaButton;

	private boolean changed = false;

	// Contatore interno
	private int counter;
	private Boolean editing;
	private HashMap<String, String> params;

	public ReplicableCanvas() {
		this(null);
	}

	public ReplicableCanvas(ReplicableItem item) {
		this(item, null);
	}

	/**
	 * Crea un {@link ReplicableCanvas}
	 */
	public ReplicableCanvas(ReplicableItem item, HashMap<String, String> parameters) {
		instance = this;
		this.vmCanvas = new ValuesManager();
		setItem(item);
		setParams(parameters);
		disegna();
		for (DynamicForm form : getForm()) {
			for (FormItem field : form.getFields()) {				
				field.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						
						setChanged(true);
					}
				});
			}
		}
	}
	
	public void setValuesManager(ValuesManager vm) {
		this.vmCanvas = vm;
	}

	public ValuesManager getValuesManager() {
		return vmCanvas;
	}

	public int getCanvasHeight() {
		int canvasHeight = 0;
		for (DynamicForm form : getForm()) {
			canvasHeight += form.getVisibleHeight();
		}
		return canvasHeight;
	}

	public Record getFormValuesAsRecord() {
		return new Record(vmCanvas.getValues());
	}

	public void editRecord(Record record) {
		vmCanvas.setValues(record.toMap());
		item.updateInternalRecord(getFormValuesAsRecord(), lHLayout);
		instance.setChanged(true);		
	}
	
	public void manageLoadSelectInEditRecord(Record record, FormItem item, String keyFieldName, String displayFieldName, String paramCIToAdd) {
		manageLoadSelectInEditRecord(record, item, keyFieldName, displayFieldName, " ** ", paramCIToAdd);
	}
	
	public void manageLoadSelectInEditRecord(Record record, FormItem item, String keyFieldName, String displayFieldName, String separator, String paramCIToAdd) {
		manageLoadSelectInEditRecord(record, item, keyFieldName, new String[] {displayFieldName}, separator, paramCIToAdd);
	}
	
	public void manageLoadSelectInEditRecord(Record record, FormItem item, String keyFieldName, String[] displayFieldNames, String paramCIToAdd) {
		manageLoadSelectInEditRecord(record, item, keyFieldName, displayFieldNames, " ** ", paramCIToAdd);
	}
	
	public void manageLoadSelectInEditRecord(Record record, FormItem item, String keyFieldName, String[] displayFieldNames, String separator, String paramCIToAdd) {
		
		if (item != null && record != null) {
			String key = keyFieldName != null ? record.getAttribute(keyFieldName) : null;
			String display = null; 
			if(displayFieldNames != null && displayFieldNames.length > 0) {
				display = displayFieldNames[0] != null ? record.getAttribute(displayFieldNames[0]) : "";
				if(displayFieldNames.length > 1) {
					for(int i = 1; i < displayFieldNames.length; i++) {
						display += separator + (displayFieldNames[i] != null ? record.getAttribute(displayFieldNames[i]) : "");	
					}
				}				
			}	
			if(paramCIToAdd != null && !"".equals(paramCIToAdd)) {
				if(item.getOptionDataSource() != null && (item.getOptionDataSource() instanceof GWTRestDataSource)) {
					GWTRestDataSource optionDS = (GWTRestDataSource) item.getOptionDataSource();
					if (key != null && !"".equals(key)) {
						optionDS.addParam(paramCIToAdd, key);
					} else {
						optionDS.addParam(paramCIToAdd, null);
					}
					item.setOptionDataSource(optionDS);
				}
			}									
			if (key != null && !"".equals(key)) {
				if (display != null && !"".equals(display)) {
					if (item.getValueMap() != null ) {
						if(!item.getValueMap().containsKey(key)){
							item.getValueMap().put(key, display);
						}
					} else {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						valueMap.put(key, display);
						item.setValueMap(valueMap);
					}
				}	
				item.setValue(key); 
			}
		}		
	}
	
	public boolean isChangedRecord(Record record) {
		Map oldValues = getFormValuesAsRecord().toMap();
		Map values = record.toMap();
		boolean changed = false;
		for(Object key : oldValues.keySet()) {
			if((oldValues.get(key) == null && values.get(key) != null) ||
			   (oldValues.get(key) != null && values.get(key) == null) ||
			   (oldValues.get(key) != null && values.get(key) != null) && !oldValues.get(key).equals(values.get(key))) {
				changed = true;
			}
		}
		return changed;
	}

	public boolean validate() {
		return true;
	}
	
	public boolean valuesAreValid() {
		return true;
	}

	/**
	 * In questa funzione occorre aggiungere gli elementi grafici del Canvas. Quindi occorre creare Widget e quant'altro e fare l''add all'oggetto padre
	 */
	public abstract void disegna();

	/**
	 * Ha il compito di restituirmi i form che devono essere gestiti dal {@link ReplicableItem}.
	 * 
	 * @return
	 */
	public abstract ReplicableCanvasForm[] getForm();
	
	public boolean hasValue(Record defaultRecord) {
		for (DynamicForm form : getForm()) {
			form.markForRedraw();
		    if(form.hasValue(defaultRecord)) {
				return true;
			}
		}
		return false;
	}

	public Map getErrors() {
		Map errors = null;
		for (DynamicForm form : getForm()) {
			if (form.getErrors() != null && form.getErrors().size() > 0) {
				if (errors == null) {
					errors = new HashMap();
				}
				errors.putAll(form.getErrors());
			}
		}
		return errors;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			setCanFocus(false);
			setTabIndex(-1);
			if (((ReplicableItem) getItem()).getTabIndex() != null) {
				int tabIndex = ((ReplicableItem) getItem()).getTabIndex() + ((counter - 1) * 100) + 1;
				for (DynamicForm form : getForm()) {
					form.setCanFocus(false);
			 		if (!UserInterfaceFactory.isAttivaAccessibilita()){
						form.setTabIndex(-1);	
					}
					for (FormItem item : form.getFields()) {
						if (item.getCanFocus() && !(item instanceof HiddenItem)) {
							item.setTabIndex(0); // item.setTabIndex(tabIndex);
							item.setGlobalTabIndex(0); // item.setGlobalTabIndex(tabIndex);
							CustomDetail.showItemTabIndex(item);
							tabIndex += 1;
						} else
							item.setTabIndex(-1);
					}
				}
			}
		}
		markForRedraw();
	}

	public Boolean getEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public ReplicableItem getItem() {
		return item;
	}

	public void setItem(ReplicableItem item) {
		this.item = item;
	}

	public VLayout getVLayout() {
		return lVLayout;
	}

	public void setVLayout(VLayout lVLayout) {
		this.lVLayout = lVLayout;
	}

	public HLayout getHLayout() {
		return lHLayout;
	}

	public void setHLayout(HLayout lHLayout) {
		this.lHLayout = lHLayout;
	}

	public ReplicableCanvas getInstance() {
		return instance;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public RemoveButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(RemoveButton removeButton) {
		this.removeButton = removeButton;
	}

	public ImgButton getUpButton() {
		return upButton;
	}

	public void setUpButton(ImgButton upButton) {
		this.upButton = upButton;
	}

	public ImgButton getDownButton() {
		return downButton;
	}

	public void setDownButton(ImgButton downButton) {
		this.downButton = downButton;
	}

	public ImgButton getDuplicaRigaButton() {
		return duplicaRigaButton;
	}

	public void setDuplicaRigaButton(ImgButton duplicaRigaButton) {
		this.duplicaRigaButton = duplicaRigaButton;
	}

	public class ReplicableCanvasForm extends DynamicForm {

		public ReplicableCanvasForm() {
			setWrapItemTitles(false);
			setValuesManager(vmCanvas);
		}

		@Override
		public void setValue(String fieldName, String value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, double value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, boolean value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, int[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Date value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, RelativeDate value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, String[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Map value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, JavaScriptObject value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, DataClass value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, DataClass[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Record value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValue(String fieldName, Record[] value) {
			super.setValue(fieldName, value);
			update();
		}

		@Override
		public void setValues(Map values) {
			super.setValues(values);
			update();
		}

		@Override
		public void clearValue(String fieldName) {
			super.clearValue(fieldName);
			update();
		}

		@Override
		public void clearValues() {
			super.clearValues();
			update();
		}

		public void update() {
	 		if (UserInterfaceFactory.isAttivaAccessibilita()){
				setCanFocus(true);
	//			setTabIndex(-1);
	 		} else {
				setCanFocus(false);
				setTabIndex(-1);
			}
			item.updateInternalRecord(vmCanvas != null ? new Record(vmCanvas.getValues()) : getValuesAsRecord(), lHLayout);
			instance.setChanged(true);
			markForRedraw();
		}
	}

	public void setCanEdit(Boolean canEdit) {
		setEditing(canEdit);
		for (DynamicForm form : getForm()) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
			form.markForRedraw();
		}
	}

	public void redraw() {
		for (DynamicForm form : getForm()) {
			for (FormItem item : form.getFields()) {
				item.redraw();
			}
			form.markForRedraw();
			form.clearErrors(true);
		}
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	
	public FormItem getItemByName(String fieldName) {
		for(DynamicForm form : getForm()) {
			FormItem item = form.getItem(fieldName);
			if(item != null) {
				return item;				
			}
		}
		return null;
	}
	
	@Override
	protected void onDestroy() {
		if(vmCanvas != null) {
			try { 
				vmCanvas.destroy(); 
			} catch(Exception e) {}
		}	
		if(getChildren() != null) {
			for(Canvas child : getChildren()) {
				try { 
					child.destroy();
				} catch(Exception e) {}
			}
		}
		super.onDestroy();
	}

}
