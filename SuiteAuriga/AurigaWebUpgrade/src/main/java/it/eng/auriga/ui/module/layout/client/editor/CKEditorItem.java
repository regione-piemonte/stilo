/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.utility.Styles;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.IEditorItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class CKEditorItem extends CanvasItem implements IEditorItem, IDatiSensibiliItem {
	
	private final static String SEPARATORE_FILE_DA_INIETTARE = "##@@FILE_DA_INIETTARE@@##";
	
	protected final ValuesManager vm;
	private boolean editing;
	private boolean abilitaIniezioneCkEditorDaFile;
	private boolean ignoraGestioneOmissis;
	private CKEditor ckeCanvas;
	private Record paramsViewerCKEditor;
//	private String ckeTitle;
	protected DynamicForm iniezioneFileForm;
	protected DocumentItem fileIniezioneFileItem;
	protected CheckboxItem selezionaIniezioneFileItem;
	
	public CKEditorItem(String name)  {
		this(name, -1, "STANDARD", 10, -1, "", false, false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri)  {
		this(name, numMaxCaratteri, "STANDARD", 10, -1, "", false, false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor)  {
		this(name, numMaxCaratteri, tipoEditor, 10, -1, "", false, false);
	}
	
	public CKEditorItem(String name, String tipoEditor)  {
		this(name, -1, tipoEditor, 10, -1, "", false, false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor, int altezzaInRighe)  {
		this(name, numMaxCaratteri, tipoEditor, altezzaInRighe, -1, "", false, false);
	}
	
	public CKEditorItem(String name, int numMaxCaratteri, String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue) {
		this(name, numMaxCaratteri, tipoEditor, altezzaInRighe, larghezza, defaultValue, false, false);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder) {
		this(name, numMaxCaratteri, tipoEditor, (altezzaInRighe * 20) + "", larghezza, defaultValue, upperCase, hideBorder);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, int altezzaInRighe, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder, boolean abilitaIniezioneCkEditorDaFile, boolean ignoraGestioneOmissis) {
		this(name, numMaxCaratteri, tipoEditor, (altezzaInRighe * 20) + "", larghezza, defaultValue, upperCase, hideBorder, abilitaIniezioneCkEditorDaFile, ignoraGestioneOmissis);
	}
	
	public CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, String altezza, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder) {
		this(name, numMaxCaratteri, tipoEditor, altezza, larghezza, defaultValue, upperCase, hideBorder, false, false);
	}
	
	private CKEditorItem(String name, final int numMaxCaratteri, final String tipoEditor, String altezza, int larghezza, String defaultValue, boolean upperCase, boolean hideBorder, boolean abilitaIniezioneCkEditorDaFile, boolean ignoraGestioneOmissis) {
		
		super(name);
		
		this.vm = new ValuesManager();
		
		this.abilitaIniezioneCkEditorDaFile = abilitaIniezioneCkEditorDaFile;
		this.ignoraGestioneOmissis = ignoraGestioneOmissis;
		
		// devo salvare i parametri per il viewer del CKEditor, per instanziarlo nello stesso modo rispetto all'item di partenza
		paramsViewerCKEditor = new Record();
		paramsViewerCKEditor.setAttribute("numMaxCaratteri", numMaxCaratteri);
		paramsViewerCKEditor.setAttribute("tipoEditor", tipoEditor);
		paramsViewerCKEditor.setAttribute("upperCase", upperCase);
		paramsViewerCKEditor.setAttribute("hideBorder", hideBorder);
		
		if (altezza == null || "".equalsIgnoreCase(altezza)) {
			// Metto una altezza di default, ovvero quello che corrisponde a 10 righe)
			altezza = "200";
		}
		String strLarghezza =  larghezza > 0 ? larghezza + "" : "100%";
		ckeCanvas = new CKEditor(name + "_" + SC.generateID(), numMaxCaratteri, tipoEditor, altezza, strLarghezza, defaultValue, upperCase, hideBorder);
		ckeCanvas.setKeepInParentRect(true);
		ckeCanvas.addHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				fireEvent(new ChangedEvent(getJsObj()));
			}
		}, ChangedEvent.getType());
		
		selezionaIniezioneFileItem = new CheckboxItem("selezionaIniezioneFile", "File DOCX alternativo al testo HTML");
		selezionaIniezioneFileItem.setWidth(50);
		selezionaIniezioneFileItem.setStartRow(true);

		selezionaIniezioneFileItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (editing) {
					if (isAttivataIniezioneCkEditorDaFile()) {
						if (isIniezioneFileSelezionata()) {
								setCanEditCkeCanvas(false);
						} else {
								setCanEditCkeCanvas(true);
						}
					} else {
						if (iniezioneFileForm != null) {
							iniezioneFileForm.setValue("selezionaIniezioneFile", false);
						}
						setCanEditCkeCanvas(true);
					}
				}
				return isAttivataIniezioneCkEditorDaFile();
			}
		});
		
		selezionaIniezioneFileItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				iniezioneFileForm.markForRedraw();
			}
		});
		
		fileIniezioneFileItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return false;
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return false;
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return false;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				String correctName = info != null ? info.getCorrectFileName() : "";
				boolean formatoAmmesso = (correctName.toLowerCase().endsWith(".docx") || correctName.toLowerCase().endsWith(".doc") || correctName.toLowerCase().endsWith(".odt"));
				return formatoAmmesso;
			}
			
			@Override
			public boolean isNomeFileEditable() {
				return false;
			}
			
			@Override
			public void manageOnChangedDocument() {
				if (!isFormatoFileIniezioneValido() && editing) {
					pulisciFormIniezioneFile();
					// Riselezioni la checkbox di iniezione tramite file
					if (iniezioneFileForm != null) {
						iniezioneFileForm.setValue("selezionaIniezioneFile", true);
					}
				} else {
					super.manageOnChangedDocument();
				}
			}
		};
		fileIniezioneFileItem.setStartRow(false);
		fileIniezioneFileItem.setName("fileIniezioneFile");
		fileIniezioneFileItem.setTitle("File DOCX alternativo al testo HTML");
		fileIniezioneFileItem.setShowTitle(false);
		fileIniezioneFileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (editing) {
					if (isAttivataIniezioneCkEditorDaFile()) {
						if (isIniezioneFileSelezionata()) {
							setCanEditCkeCanvas(false);
						} else {
							setCanEditCkeCanvas(true);
						}
					} else {
						setCanEditCkeCanvas(true);
					}
				}
				return isIniezioneFileSelezionata();
			}
		});
		
		fileIniezioneFileItem.setAttribute("obbligatorio", true);
//		fileIniezioneFileItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				if (isAttivaIniezioneCkEditorDaFile()) {
//					if (isIniezioneFileSelezionata()) {
//						return isIniezioneFileSelezionataEValida();
//					} else {
//						return true;
//					}
//				} else {
//					return true;
//				}
//			}
//		}));
		
		CustomValidator lCustomValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (isAttivataIniezioneCkEditorDaFile()) {
					if (isIniezioneFileSelezionata()) {
						return isIniezioneFileSelezionataEValida();
					} else {
						return true;
					}
				} else {
					return true;
				}
			}				
		};
		lCustomValidator.setErrorMessage("File mancante o di un formato non consentito");
		fileIniezioneFileItem.setValidators(lCustomValidator);
		
		iniezioneFileForm = new DynamicForm();
		iniezioneFileForm.setValuesManager(vm);
		iniezioneFileForm.setWidth100();
		iniezioneFileForm.setPadding(5);
		iniezioneFileForm.setWrapItemTitles(false);
		iniezioneFileForm.setNumCols(20);
		iniezioneFileForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		iniezioneFileForm.setHeight(1);
		
		iniezioneFileForm.setFields(selezionaIniezioneFileItem, fileIniezioneFileItem);
		
		VLayout lVLayout = creaVLayout();
		lVLayout.addMembers(iniezioneFileForm);
		lVLayout.addMembers(ckeCanvas);
		setCanvas(lVLayout);
		
		setOverflow(Overflow.VISIBLE);
		setWidth("100%");
		if (altezza.indexOf('%') != -1) {
			setHeight(altezza);
		} else {
			setHeight(numMaxCaratteri > -1 ? Integer.parseInt(altezza) + 70 : Integer.parseInt(altezza) + 58);
		}
		setShowTitle(false);
		setShowHint(false);
		setShowIcons(showViewer());		
		setColSpan(1);


		buildViewer(this);
		
		addShowValueHandler(new ShowValueHandler() {
			
			@Override
			public void onShowValue(ShowValueEvent event) {
				String value = (String) event.getDataValue();
				if(ckeCanvas != null) {
					ckeCanvas.setValue(value);			
				}
			}
		});
	}
	
	protected void buildViewer(final CKEditorItem item) {
    	if(showViewer()) {
    		FormItemIcon viewerIcon = new FormItemIcon();
    		viewerIcon.setHeight(16);
    		viewerIcon.setWidth(16);
//    		viewerIcon.setInline(true);       
    		viewerIcon.setNeverDisable(true);
    		viewerIcon.setSrc("buttons/view.png");
    		viewerIcon.setPrompt("Visualizza contenuti");
    		viewerIcon.setCursor(Cursor.POINTER);
    		viewerIcon.setAttribute("cellStyle", Styles.formCellClickable);
    		viewerIcon.addFormItemClickHandler(new FormItemClickHandler() {

    			@Override
    			public void onFormItemClick(FormItemIconClickEvent event) {
    				manageOnViewerClick(item);    				
    			}
    		});
    			
    		List<FormItemIcon> icons = new ArrayList<FormItemIcon>();
    		icons.add(viewerIcon);
    		item.setIcons(icons.toArray(new FormItemIcon[icons.size()]));
    		item.setIconVAlign(VerticalAlignment.CENTER);
    	}
    }
	
	public void manageOnViewerClick(final CKEditorItem item) {
    	ViewerCKEditorValueWindow lViewerCKEditorValueWindow = new ViewerCKEditorValueWindow(item);
    	lViewerCKEditorValueWindow.show();
    }
    
    public boolean showViewer() {
    	return true;
    }
	
//  @Override
//	public void setTitle(String title) {
//		ckeTitle = title;
//		VLayout lVLayout = (VLayout) getCanvas();
//		if(lVLayout != null && lVLayout.getIsGroup()) {
//			lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + title + "</span>");
//		}
//	}
//	
//	@Override
//	public String getTitle() {
//		return ckeTitle;
//	}
	
	protected VLayout creaVLayout() {
		VLayout lVLayout = new VLayout();
		lVLayout.setAlign(VerticalAlignment.CENTER);		
		return lVLayout;
	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		if(ckeCanvas != null) {
			ckeCanvas.setWidth(width);
		}
	}
	
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		if(ckeCanvas != null) {
			ckeCanvas.setWidth(width);
		}
	}
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		if(ckeCanvas != null) {
			ckeCanvas.setHeight(height);
		}
	}
	
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		if(ckeCanvas != null) {
			ckeCanvas.setHeight(height);
		}
	}
	
	@Override
	public void clearValue() {
		setValue("");
	}
	
	@Override
	public void setValue(String value) {
		super.setValue(value);
	}
	
	public void setFileValue(String stringaJsonFile) {
		if (stringaJsonFile != null && !"".equalsIgnoreCase(stringaJsonFile)) {
			JavaScriptObject objectJsonFile = JSON.decode(stringaJsonFile);
			Record iniezioneFileRecord = new Record(objectJsonFile);
			Record fileIniezioneFileRecord = iniezioneFileRecord.getAttributeAsRecord("fileIniezioneFile");
			if (fileIniezioneFileRecord != null) {
				fileIniezioneFileRecord.setAttribute("isChanged", false);
			}
			if (!isAttivataIniezioneCkEditorDaFile()) {
				iniezioneFileRecord.setAttribute("selezionaIniezioneFile", false);
			}
			iniezioneFileForm.setValues(iniezioneFileRecord.toMap());
		} else {
			iniezioneFileForm.clearValues();
		}
	}
	
	@Override
	public String getValue() {
		if(ckeCanvas != null) {
			// la pulizia dei caratteri dell'html che danno problemi con Shibboleth viene fatta lato DB quindi commento
//			return cleanTestoHtml(ckeCanvas.getValue());
			return ckeCanvas.getValue();
		}
		return null;
	}
	
	public String getFileValue() {
		String value = null;
		if (iniezioneFileForm != null) {
			Record recordIniezioneFileForm = iniezioneFileForm.getValuesAsRecord();
			if (recordIniezioneFileForm != null && recordIniezioneFileForm.getAttribute("fileIniezioneFile") != null) {
				Record recordFile = recordIniezioneFileForm.getAttributeAsRecord("fileIniezioneFile");
				if (recordFile.getAttribute("uriFile") != null && !"".equalsIgnoreCase(recordFile.getAttributeAsString("uriFile"))) {
					value = JSON.encode(recordIniezioneFileForm.getJsObj());
				}
			}
		}
		return value;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		this.editing = canEdit != null && canEdit;
		setCanEditCkeCanvas(canEdit);
		if (fileIniezioneFileItem != null) {
			fileIniezioneFileItem.setCanEdit(canEdit);
		}
		if (selezionaIniezioneFileItem != null) {
			selezionaIniezioneFileItem.setCanEdit(canEdit);
		}
	}

	private void setCanEditCkeCanvas(Boolean canEdit) {
		if(ckeCanvas != null) {
			ckeCanvas.setReadOnly(!canEdit);	
			setAttribute("readOnly", !canEdit);
		}
	}
	
	@Override
	public Boolean getCanEdit() {
		boolean readOnly = getAttributeAsBoolean("readOnly") != null ? getAttributeAsBoolean("readOnly") : false;
		return !readOnly;
	}
	
	@Override
	public void setRequired(Boolean required) {
		setAttribute("obbligatorio", required);
	}
	
	@Override
	public Boolean getRequired() {
		return getAttributeAsBoolean("obbligatorio") != null ? getAttributeAsBoolean("obbligatorio") : false;
	}
	
	@Override
	public Boolean validate() {		
		iniezioneFileForm.validate();
		boolean valid;
		if (isAttivataIniezioneCkEditorDaFile()) {
			if (isIniezioneFileSelezionata()) {
				valid = isIniezioneFileSelezionataEValida();
			} else {
				valid = !getRequired() || ((getValue() != null && !"".equals(getValue())) || isIniezioneFileSelezionataEValida());
			}
		} else {
			valid = !getRequired() || ((getValue() != null && !"".equals(getValue())) || isIniezioneFileSelezionataEValida());
		}
		if(getForm() != null) {
			getForm().clearFieldErrors(getName(), true);
			if(!valid) {
				getForm().setFieldErrors(getName(), "Campo obbligatorio");
			}
		}
		return valid;
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if (isIgnoraGestioneOmissis()) {
			return false;
		} else {
			return getValue() != null && getValue().contains("<s>") && getValue().contains("</s>");
		}
	}
	
	@Override
	public boolean isCKEditor() {
		return true;
	}
	
	@Override
	public void manageOnDestroy() {
		if(ckeCanvas != null) {
			ckeCanvas.close();
			ckeCanvas.markForDestroy();
		}		
		if(iniezioneFileForm != null) {
			iniezioneFileForm.markForDestroy();
		}
		if(fileIniezioneFileItem != null) {
			fileIniezioneFileItem.manageOnDestroy();
		}
		if(vm != null) {
			vm.destroy();
		}
	}
	
	@Override
	public void setVisible(Boolean visible) {
		super.setVisible(visible);
		if(ckeCanvas != null) {
			ckeCanvas.setVisible(visible);
		}
	}	
	
	// Usare questo metodo e passare forceInit a true quando si lavora in una maschera in cui il ckeditor può essere mostrato o meno in base a delle condizioni dinamiche - 
	// ES: Vedere funzione Vocabolario (VocabolarioDetail.java)
	public void setVisibleAndForceInit(Boolean visible, boolean forceInit) {
		// Se visible è false, il super.setVisible del formItem tronca l'html del ckeditor portando errori quando si fa il setvalue
		super.setVisible(visible);
		if(ckeCanvas != null) {
			ckeCanvas.setVisible(visible, forceInit);
		}
	}
	
	private String cleanTestoHtml(String html) {
		if (html != null && !"".equals(html)) {
			String cleanedHtml = html.replaceAll("&nbsp;", "&#160;")
					.replaceAll("&ndash;", "&#8211;")
					.replaceAll("&ldquo;", "&#8220;")
					.replaceAll("&rdquo;", "&#8221;")
					.replaceAll("&rsquo;", "&#8217;")
					.replaceAll("&lsquo;", "&#8216;")
					.replaceAll("&sbquo;", "&#8218;")
					.replaceAll("&bdquo;", "&#8222;");
			
			return cleanedHtml;
		} else {
			return html;
		}
	}

	public Record getParamsViewerCKEditor() {
		return paramsViewerCKEditor;
	}
	
//	public static String toJson(Map<String, String> map) {
//	    String json = "";
//	    if (map != null && !map.isEmpty()) {
//	        JSONObject jsonObj = new JSONObject();
//	        for (Map.Entry<String, String> entry: map.entrySet()) {
//	            jsonObj.put(entry.getKey(), new JSONString(entry.getValue().toString()));
//	        }
//	        json = jsonObj.toString();
//	    }
//	    return json;
//	}
//
//	public static Map<String, String> toMap(String jsonStr) {
//	    Map<String, String> map = new HashMap<String, String>();
//
//	    JSONValue parsed = JSONParser.parseStrict(jsonStr);
//	    JSONObject jsonObj = parsed.isObject();
//	    if (jsonObj != null) {
//	        for (String key : jsonObj.keySet()) {
//	            map.put(key, jsonObj.get(key).isString().stringValue());
//	        }
//	    }
//
//	    return map;
//	}
	
	private boolean isAttivataIniezioneCkEditorDaFile() {
		return isAbilitataIniezioneCkEditorDaFile() && isAttivoPrivilegioIniezioneCkEditorDaFile();
	}
	
	private boolean isAbilitataIniezioneCkEditorDaFile() {
		return abilitaIniezioneCkEditorDaFile;
	}
	
	private boolean isIgnoraGestioneOmissis() {
		return ignoraGestioneOmissis;
	}
	
	private boolean isAttivoPrivilegioIniezioneCkEditorDaFile() {
		return Layout.isPrivilegioAttivo("ATT/UPF");
	}
	
	private boolean isIniezioneFileSelezionata() {
		Record recordIniezioneFileForm = iniezioneFileForm.getValuesAsRecord();
		if (recordIniezioneFileForm != null && recordIniezioneFileForm.getAttribute("selezionaIniezioneFile") != null) {
			boolean selezionataIniezioneFile = recordIniezioneFileForm.getAttributeAsBoolean("selezionaIniezioneFile");
			return selezionataIniezioneFile;
		}
		return false;
	}
	
	private boolean isIniezioneFileSelezionataEValida() {
		Record recordIniezioneFileForm = iniezioneFileForm.getValuesAsRecord();
		if (recordIniezioneFileForm != null && isIniezioneFileSelezionata()) {
			Record recordFile = recordIniezioneFileForm.getAttributeAsRecord("fileIniezioneFile");
			if (fileIniezioneFileItem != null && recordFile != null && recordFile.getAttribute("uriFile") != null && !"".equalsIgnoreCase(recordFile.getAttributeAsString("uriFile")) && recordFile.getAttribute("infoFile") != null) {
				InfoFileRecord lInfoFileRecord = new InfoFileRecord(recordFile.getAttributeAsJavaScriptObject("infoFile")); 
				return fileIniezioneFileItem.isFormatoAmmesso(lInfoFileRecord);
			}
		}
		return false;
	}
	
	private boolean isFormatoFileIniezioneValido() {
		Record recordIniezioneFileForm = iniezioneFileForm.getValuesAsRecord();
		if (recordIniezioneFileForm != null) {
			Record recordFile = recordIniezioneFileForm.getAttributeAsRecord("fileIniezioneFile");
			if (fileIniezioneFileItem != null && recordFile != null && recordFile.getAttribute("uriFile") != null && !"".equalsIgnoreCase(recordFile.getAttributeAsString("uriFile")) && recordFile.getAttribute("infoFile") != null) {
				InfoFileRecord lInfoFileRecord = new InfoFileRecord(recordFile.getAttributeAsJavaScriptObject("infoFile")); 
				return fileIniezioneFileItem.isFormatoAmmesso(lInfoFileRecord);
			}
		}
		return false;
	}
	
	private void pulisciFormIniezioneFile() {
		iniezioneFileForm.editRecord(new Record());
		Record fileIniezioneFileRecord = new Record();
//		fileIniezioneFileRecord.setAttribute("nomeFile", "ciao");
		Record iniezioneFileRecord = new Record();
		iniezioneFileRecord.setAttribute("fileIniezioneFile", fileIniezioneFileRecord);
		iniezioneFileForm.editRecord(iniezioneFileRecord);
	}
	
}