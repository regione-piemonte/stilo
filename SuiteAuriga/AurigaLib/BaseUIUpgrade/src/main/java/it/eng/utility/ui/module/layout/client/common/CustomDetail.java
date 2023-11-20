/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.BaseWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class CustomDetail extends VLayout {

	protected final ValuesManager vm;

	public static final String backgroundColor = "#F0F0F0";

	protected CustomLayout layout;

	protected final String nomeEntita;
	protected int recordNum;

	protected boolean editing;

	protected Record startRecord;
	protected Record finalRecord;

	protected boolean saved = false;

	protected String mode;

	public CustomDetail(String nomeEntita) {
		this(nomeEntita, null);
	}

	public CustomDetail(String pNomeEntita, ValuesManager pValuesManager) {

		this.nomeEntita = pNomeEntita;
		this.vm = pValuesManager != null ? pValuesManager : new ValuesManager();

		setWidth100();
		setHeight100();
		setAutoDraw(false);
		setCanHover(false);
		setPadding(5);
		setMembersMargin(5);
		setOverflow(Overflow.AUTO);
		setStyleName(it.eng.utility.Styles.detailLayout);

		addDrawHandler(new DrawHandler() {

			@Override
			public void onDraw(DrawEvent event) {
				
				refreshTabIndex();
			}
		});
	}

	public void newMode() {
		this.mode = "new";
		setCanEdit(true);
	}

	public void viewMode() {
		this.mode = "view";
		setCanEdit(false);
	}

	public void editMode() {
		this.mode = "edit";
		setCanEdit(true);
	}

	public CustomLayout getLayout() {
		return layout;
	}

	public void setLayout(CustomLayout layout) {
		this.layout = layout;
	}

	public void editNewRecord() {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				item.clearValue();
			}
		}
		vm.editNewRecord();
		this.recordNum = -1;
		for (DynamicForm form : vm.getMembers()) {
			form.markForRedraw();
			if (form.getDetailSection() != null) {
				form.getDetailSection().redrawDetailSectionHeaderTitle();
			}
		}
	}

	public void editNewRecord(Map initialValues) {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				item.clearValue();
			}
		}
		vm.editNewRecord(initialValues);
		this.recordNum = -1;
		for (DynamicForm form : vm.getMembers()) {
			form.markForRedraw();
			if (form.getDetailSection() != null) {
				form.getDetailSection().redrawDetailSectionHeaderTitle();
			}
		}
	}
	
	public void manageLoadSelectInEditNewRecord(Map initialValues, FormItem item, String keyFieldName, String displayFieldName, String paramCIToAdd) {	
		manageLoadSelectInEditNewRecord(initialValues, item, keyFieldName, displayFieldName, " ** ", paramCIToAdd);
	}
	
	public void manageLoadSelectInEditNewRecord(Map initialValues, FormItem item, String keyFieldName, String displayFieldName, String separator, String paramCIToAdd) {	
		manageLoadSelectInEditNewRecord(initialValues, item, keyFieldName, new String[] {displayFieldName}, separator, paramCIToAdd);
	}

	public void manageLoadSelectInEditNewRecord(Map initialValues, FormItem item, String keyFieldName, String[] displayFieldNames, String paramCIToAdd) {	
		manageLoadSelectInEditNewRecord(initialValues, item, keyFieldName, displayFieldNames, " ** ", paramCIToAdd);
	}
	
	public void manageLoadSelectInEditNewRecord(Map initialValues, FormItem item, String keyFieldName, String[] displayFieldNames, String separator, String paramCIToAdd) {	
		manageLoadSelectInEditRecord(initialValues != null ? new Record(initialValues) : null, item, keyFieldName, displayFieldNames, separator, paramCIToAdd);
	}
	
	
	public void editRecord(Record record, int recordNum) {
		editRecord(record);
		this.recordNum = recordNum;
	}

	public void editRecord(Record record) {
		// questo ciclo chiamato prima dell'editRecord() crea problemi (in alcuni casi si perdono alcuni valori della maschera) quindi lo tolgo
		/*
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				item.clearValue();
			}
		}
		*/
		vm.editRecord(record);
		vm.rememberValues();
		startRecord = new Record(vm.getValues());
		for (DynamicForm form : vm.getMembers()) {
			form.markForRedraw();
			if (form.getDetailSection() != null) {
				form.getDetailSection().redrawDetailSectionHeaderTitle();
			}
		}
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

	protected void verifyChanges(final CompareCallback afterVerify) {
		final Record record = new Record(vm.getValues());
		finalRecord = record;
		Record lRecord = new Record();
		lRecord.setAttribute("oldBean", startRecord);
		lRecord.setAttribute("newBean", finalRecord);
		((GWTRestDataSource) vm.getDataSource()).executecustom("compareOldAndNew", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData()[0].getAttributeAsBoolean("hasChanges")) {
						afterVerify.changed(finalRecord);
					} else {
						afterVerify.noChanges(finalRecord);
					}
				}
			}
		});
	}

	public void setDataSource(DataSource dataSource) {
		vm.setDataSource(dataSource);
	}

	public DataSource getDataSource() {
		return vm.getDataSource();
	}

	public ValuesManager getValuesManager() {
		return vm;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void addSubForm(DynamicForm form) {
		// for(FormItem item : form.getFields()) {
		// boolean required = (item.getRequired() != null ? item.getRequired() : false) ||
		// (item.getAttributeAsBoolean("obbligatorio") != null ? item.getAttributeAsBoolean("obbligatorio") : false);
		// if(required) {
		// item.setTitle(FrontendUtil.getRequiredFormItemTitle(item.getTitle()));
		// item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
		// }
		// }
		form.setBorder("0px solid grey");
		form.setColWidths(4);
		form.setColWidths("120", "*", "*", "*");
		form.setValuesManager(vm);
		addMember(form);
	}

	/**
	 * Effettua il ricalcolo di tutti i tabindex di tutti gli elementi di tutte le form presenti nella portlet
	 */

	public void refreshTabIndex() {
		// Ho commentato il metodo perchè non risolve il problema della navigazione tramite tastiera
		// Inoltre in AvvioPropostaAtto2Detail ho notato che nella navigazione tramite tab al prima pressione del pulsante
		// si verifica un'eccezione che si ripete fintanto che non si esaurisce la profondità dello stack, eccezione che scompare
		// nel momento in cui implemento un overriding vuoto del metodo, che quindi non viene chiamato
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			int tabIndex = 0;
			if (getNomeEntita() != null && Layout.getOpenedPortletIndex(getNomeEntita()) > 0) {
				tabIndex = (Layout.getOpenedPortletIndex(getNomeEntita()) * 1000000);
			}
			refreshTabIndex(++tabIndex);
		}		
	}

	public void refreshTabIndex(int tabIndex) {
		if(it.eng.utility.ui.module.layout.client.Layout.isAttivoRefreshTabIndex()) {
			for (int i = 0; i < vm.getMembers().length; i++) {
				DynamicForm form = vm.getMembers()[i];
				for (int j = 0; j < form.getFields().length; j++) {
					FormItem item = form.getFields()[j];
					if (item.getCanFocus() && !(item instanceof HiddenItem)) {
						item.setTabIndex(0); // item.setTabIndex(tabIndex);
						item.setGlobalTabIndex(0); // item.setGlobalTabIndex(tabIndex);
						if (item instanceof ReplicableItem) {
							((ReplicableItem) item).refreshTabIndex();
							tabIndex += 10000;
						} else {
							CustomDetail.showItemTabIndex(item);
							tabIndex += 1;
						}
					} else {
						item.setTabIndex(-1);
					}
				}
			}
		}
	}

	public void setCanEdit(boolean canEdit) {
		editing = canEdit;
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
	}

	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			form.setEditing(canEdit);
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
//					if (item instanceof DateItem || item instanceof DateTimeItem) {
//						TextItem textItem = new TextItem();
//						textItem.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//						if (item instanceof DateItem) {
//							((DateItem) item).setTextFieldProperties(textItem);
//						} else if (item instanceof DateTimeItem) {
//							((DateTimeItem) item).setTextFieldProperties(textItem);
//						}
//					} else {
//						item.setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.readonlyItem);
//					}
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
	
	public List<DetailSection> getAllDetailSections() {
		List<DetailSection> allDetailSections = new ArrayList<DetailSection>();
		for (DynamicForm form : getAllDetailForms()) {
			if(form.getDetailSection() != null) {
				allDetailSections.add(form.getDetailSection());
			}
		}
		return allDetailSections;
	}
	
	public List<DynamicForm> getAllDetailForms() {
		List<DynamicForm> allDetailForms = new ArrayList<DynamicForm>();
		for (DynamicForm form : vm.getMembers()) {
			allDetailForms.add(form);
		}
		return allDetailForms;
	}
	
	public void reopenAllSections() {
		try {
			for (DetailSection section : getAllDetailSections()) {
				if(section.isOpen()) {
					section.open();
				}
			}
		} catch (Exception e) {
		}
	}
	
	public void removeEmptyCanvas() {
		try {
			for (DynamicForm form : getAllDetailForms()) {
				for (FormItem item : form.getFields()) {
					if (item instanceof ReplicableItem) {
						ReplicableItem lReplicableItem = (ReplicableItem) item;
						if(lReplicableItem.getEditing() != null && lReplicableItem.getEditing()/* && !lReplicableItem.isObbligatorio()*/) {	
							lReplicableItem.removeEmptyCanvas();
						}					
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	public void clearTabErrors() {
		
	}
	
	public void showTabErrors() {
		
	}
	
	public boolean customValidate() {
		return true;
	}

	// Per implementare un validatore custom fare l'ovveride del metodo customValidate
	final public Boolean validate() {
		clearErrors();
		Boolean valid = vm.validate();
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof ReplicableItem) {
					ReplicableItem lReplicableItem = (ReplicableItem) item;
					boolean itemValid = lReplicableItem.validate();
					valid = itemValid && valid;
					if(!itemValid) {
						if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
							lReplicableItem.getForm().getDetailSection().open();
						}
					}
				} else if (item instanceof IDocumentItem) {
					IDocumentItem lIDocumentItem = (IDocumentItem) item;
					boolean itemValid = lIDocumentItem.validate();
					valid = itemValid && valid;
					if(!itemValid) {
						if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
							lIDocumentItem.getForm().getDetailSection().open();
						}
					}
				} else {
					boolean itemValid = item.validate();
					valid = itemValid && valid;
					if(!itemValid) {
						if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
							item.getForm().getDetailSection().open();
						}
					}
				}
			}			
		}
		if(!customValidate()) {
			valid = false;
		}
		for (DynamicForm form : vm.getMembers()) {			
			if(form.getErrors() != null && !form.getErrors().isEmpty()) {
				for(Object key : form.getErrors().keySet()) {
					FormItem item = form.getItem((String) key);
					if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
						item.getForm().getDetailSection().open();
					}
				}
			}
		}
		if (valid) {
			setSaved(valid);
		} else {
			showTabErrors();
			reopenAllSections();			
		}
		return valid;
	}	

	public String getNomeEntita() {
		return getLayout() != null ? getLayout().getNomeEntita() : nomeEntita;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void closeViewer() {
		List<String> closedViewers = new ArrayList<String>();
		for (Map.Entry<String, BaseWindow> entry : Layout.getOpenedViewers().entrySet()) {
			BaseWindow pw = entry.getValue();
			if (nomeEntita != null && nomeEntita.equals(pw.getPortletOpened())) {
				pw.destroy();
				closedViewers.add(entry.getKey());
			}
		}
		for (String key : closedViewers) {
			Layout.getOpenedViewers().remove(key);
		}
	}

	public Record getRecordToSave() {
		return new Record(vm.getValues());
	}

	public static boolean isToShowTabIndex() {
		return false;
	}

	public static void showItemTabIndex(FormItem item) {
		if (isToShowTabIndex()) {
			item.setHint("" + item.getTabIndex());
		}
	}

	public void clearTabErrors(TabSet tabSet) {
		try {
			for (Tab tab : tabSet.getTabs()) {
				tabSet.clearTabErrors(tab.getAttribute("tabID"));
			}
		} catch (Exception e) {
		}
	}

	public void showTabErrors(TabSet tabSet) {
		try {
			HashSet<String> tabsInError = new HashSet<String>();
			for (DynamicForm form : vm.getMembers()) {
				if (form.getErrors() != null && form.getErrors().size() > 0) {
					for (Object key : form.getErrors().keySet()) {
						String fieldName = (String) key;
						FormItem item = form.getItem(fieldName);
						String tabID = (item != null && item.getForm() != null) ? item.getForm().getTabID() : null;
						if (tabSet != null && tabID != null && !"".equals(tabID) && tabSet.getTabWithID(tabID) != null) {
							if (!tabsInError.contains(tabID)) {
								tabsInError.add(tabID);
							}
						}
					}
				}
				for (FormItem item : form.getFields()) {
					if (item instanceof ReplicableItem) {
						ReplicableItem lReplicableItem = (ReplicableItem) item;
						if (lReplicableItem.getMapErrors() != null && lReplicableItem.getMapErrors().size() > 0) {
							String tabID = form.getTabID();
							if (tabSet != null && tabID != null && !"".equals(tabID) && tabSet.getTabWithID(tabID) != null) {
								if (!tabsInError.contains(tabID)) {
									tabsInError.add(tabID);
								}
							}
						}
					} else if (item instanceof IDocumentItem) {
						IDocumentItem lIDocumentItem = (IDocumentItem) item;
						if (lIDocumentItem.getMapErrors() != null && lIDocumentItem.getMapErrors().size() > 0) {
							String tabID = form.getTabID();
							if (tabSet != null && tabID != null && !"".equals(tabID) && tabSet.getTabWithID(tabID) != null) {
								if (!tabsInError.contains(tabID)) {
									tabsInError.add(tabID);
								}
							}
						}
					}
				}
			}
			for (String tabID : tabsInError) {
				tabSet.showTabErrors(tabID);
			}
		} catch (Exception e) {
		}
	}
	
	public void clearErrors() {
		clearTabErrors();
		vm.clearErrors(true);
		for (DynamicForm form : vm.getMembers()) {
			form.clearErrors(true);
			for (FormItem item : form.getFields()) {
				if(item != null && (item instanceof ReplicableItem)) {
					ReplicableItem lReplicableItem = (ReplicableItem) item;
					lReplicableItem.clearErrors();
				} else if(item != null && (item instanceof IDocumentItem)) {
					IDocumentItem lIDocumentItem = (IDocumentItem) item;
					lIDocumentItem.clearErrors();
				}
			}				
		}
	}
	
	/*
	 * Se ho delle sezioni ripetute nel form elimino le righe vuote
	 */
	protected static void addFormValues(Record record, DynamicForm form) {

		if (form != null) {
			// lo commento perchè ci sono alcuni dati che devono essere passati anche se le sezioni relative sono nascoste:
			// per es. l'idEmail nella protocollazione di una mail in entrata, il tipoDocumento, il supportoOriginale ecc...
			// metto il controllo solo per quanto riguarda le ReplicableItem, che non devono essere passate quando sono nascoste
//			if(form.getDetailSection() == null || form.getDetailSection().isVisible()) {
				try {
					Record formRecord = form.getValuesAsRecord();
					for (FormItem item : form.getFields()) {
						if (item != null) {
							String fieldName = item.getName();
							if (item instanceof ReplicableItem) {
//								if(((ReplicableItem) item).getVisible()) { //TODO DA VERIFICARE SE VENGONO MESSI I VALORI SE LA SEZIONE E' CHIUSA
									final RecordList lRecordList = new RecordList();
									ReplicableCanvas[] allCanvas = ((ReplicableItem) item).getAllCanvas();
									if(allCanvas != null && allCanvas.length > 0) {
										for (ReplicableCanvas lReplicableCanvas : allCanvas) {
											if(lReplicableCanvas.hasValue(((ReplicableItem) item).getCanvasDefaultRecord())) {
												lRecordList.add(lReplicableCanvas.getFormValuesAsRecord());
											}
										}
										if(((ReplicableItem) item).isObbligatorio() && lRecordList.getLength() == 0) {
											lRecordList.add(allCanvas[0].getFormValuesAsRecord());
										}
									}
									formRecord.setAttribute((String) fieldName, lRecordList);
//								} else {
//									formRecord.setAttribute((String) fieldName, (Object) null);
//								}
							} else if (item instanceof GridItem) {
								formRecord.setAttribute((String) fieldName, ((GridItem) item).getValueAsRecordList());
							} else if (item instanceof IEditorItem) {							
								formRecord.setAttribute((String) fieldName, ((IEditorItem) item).getValue());
							}
						}
					}
					JSOHelper.addProperties(record.getJsObj(), formRecord.getJsObj());
				} catch (Exception e) {
				}
//			}			
		}
	}
	
	public boolean hasDatiSensibili() {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item != null && item instanceof IDatiSensibiliItem) {
					if(((IDatiSensibiliItem)item).hasDatiSensibili()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	protected void onDestroy() {
		if(vm != null) {
//			for (DynamicForm form : vm.getMembers()) {
//				for (FormItem item : form.getFields()) {
//					if (item != null) {
//						if (item instanceof ReplicableItem) {
//							((ReplicableItem)item).manageOnDestroy();
//						} else if (item instanceof IEditorItem) {
//							((IEditorItem)item).manageOnDestroy();
//						}
//					}
//				}
//			}		
			try { 
				vm.destroy(); 
			} catch(Exception e) {				
			}
		}
		super.onDestroy();
	}

}
