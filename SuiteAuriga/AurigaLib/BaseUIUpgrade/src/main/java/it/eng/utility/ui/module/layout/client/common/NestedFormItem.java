/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;

public class NestedFormItem extends CanvasItem {
	
	private DynamicForm nestedForm; 
	
	private int numCols = 2;
	private Object[] colWidths = {"150", "*"};	
	private String border = null;	
		
	private DataSource nestedFormDataSource;
	
	private FormItem[] nestedFormItems;
	
	protected boolean editing;		
	
	public NestedFormItem(String name) {
		this(name, null);
	}
	
	public NestedFormItem(String name, String title) {
		super(name);
		//setWidth("*");
		//setHeight("*");
		if(title != null && !"".equals(title)) {
			setTitle(title);
		} else {
			setShowTitle(false);
		}		
		setColSpan(2);
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {
			@Override
			public void onInit(FormItem item) {
				nestedForm = new DynamicForm();						
				nestedForm.setMargin(-2);
				nestedForm.setKeepInParentRect(true);				
				if(nestedFormDataSource != null)
					nestedForm.setDataSource(nestedFormDataSource);				
				if (nestedFormItems != null)
					nestedForm.setFields(nestedFormItems);
				nestedForm.setBorder(border);
				nestedForm.setNumCols(numCols);				
				nestedForm.setColWidths(colWidths);				
				Record value = (Record) item.getValue();
				if (value != null)
					nestedForm.editRecord(value);				
				
				setCanvas(nestedForm);
				addShowValueHandler(new ShowValueHandler() {
					@Override
					public void onShowValue(ShowValueEvent event) {
						Record record = event.getDataValueAsRecord();
						if (record != null) {
							nestedForm.editRecord(record);
						} else {
							Canvas parent = event.getItem().getCanvas().getParentElement();
							if(parent instanceof DynamicForm) {
								record = ((DynamicForm) parent).getValuesAsRecord();
								nestedForm.editRecord(record);
							}							
						}
					}
				});
			}
		});		
		setShouldSaveValue(true);		
	}	
	
	@Override
	public void setValue(Object value) {
		if(value == null || value instanceof Record) {	
			Record record = (Record) value;
			nestedForm.setValues(record.toMap());
			CanvasItem canvasItem = nestedForm.getCanvasItem();
			canvasItem.storeValue(record);			
		}	
	}
	
	@Override
	public Object getValue() {
		return nestedForm.getValuesAsRecord();
	}

	public void clearValue() {
		nestedForm.editNewRecord();		
	}
	
	public DynamicForm getNestedForm() {
		return nestedForm;
	}

	public void setNestedForm(DynamicForm nestedForm) {
		this.nestedForm = nestedForm;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;		
	}
	
	public void setColWidths(Object... colWidths) {
		this.colWidths = colWidths;
	}
	
	public void setBorder(String border) {
		this.border = border;
	}
	
	public DataSource getNestedFormDataSource() {
		return this.nestedFormDataSource;
	}
	
	public void setNestedFormDataSource(DataSource nestedFormDataSource) {
		this.nestedFormDataSource = nestedFormDataSource;
	}
	
	public FormItem[] getNestedFormItems() {
		return nestedFormItems;
	}
	
	public void setNestedFormItems(FormItem... nestedFormItems) {	
		for(FormItem item : nestedFormItems) {
			item.addChangeHandler(new ChangeHandler() {				
				@Override
				public void onChange(ChangeEvent event) {
					Record record = new Record(nestedForm.getValues());		
					record.setAttribute(event.getItem().getName(), event.getValue());
					CanvasItem canvasItem = nestedForm.getCanvasItem();
					canvasItem.storeValue(record);
				}
			});
		}
		this.nestedFormItems = nestedFormItems;
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		editing = canEdit;
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			DynamicForm nestedForm = (DynamicForm) this.getCanvas();
			nestedForm.setCanEdit(true);
		 	for(FormItem item : nestedForm.getFields()) {		 		
		 		if(!(item instanceof HeaderItem) && 
		 		   !(item instanceof ImgButtonItem) && 
		 		   !(item instanceof TitleItem)) {	
		 			if(item instanceof DateItem || item instanceof DateTimeItem) {
						TextItem textItem = new TextItem();
						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
						if(item instanceof DateItem) {
							((DateItem)item).setTextFieldProperties(textItem);
						} else if(item instanceof DateTimeItem) {
							((DateTimeItem)item).setTextFieldProperties(textItem);
						}							
						item.redraw();
					} else {
						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemReadonly);
					}		 			
					item.setCanEdit(canEdit);
				}
		 		if(item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
				}
			}
		}
	}
	
	public void redraw(){		
		if(this.getCanvas() != null) {			
			DynamicForm nestedForm = (DynamicForm) this.getCanvas();
			nestedForm.markForRedraw(); //TODO verificare che scattino gli showIf dei bottoni altrimenti bisogna usare nestedForm.redraw();
		 	for(FormItem item : nestedForm.getFields()) {
		 		item.redraw();
		 	}
		}
	}
	
	public void markForRedraw(){
		redraw();
	}
	
	public boolean isEditing() {
		return editing;
	}
	
}