/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;


public class ListaDichiarazioniDiVotoItem extends GridItem {
	
	private ListaDichiarazioniDiVotoItem instance = this;
	
	protected ListGridField idUser;
	protected ListGridField gruppoConsigliere;
	protected ListGridField delegato;
	protected ListGridField ruolo;
	protected ListGridField voto;
	protected ListGridField flgVotoExtra;

	protected String idSeduta;
	private String organoCollegiale;
	
	protected ControlListGridField removeButtonField;

	public ListaDichiarazioniDiVotoItem(String name,String organoCollegiale, String idSeduta) {
		
		super(name, "lista_dichiarazioni_di_voto");
		
		this.idSeduta = idSeduta;
		this.organoCollegiale = organoCollegiale;
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(true);  
		
		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);
		idUser.setCanSort(true);
		idUser.setCanEdit(false);
		
		gruppoConsigliere = new ListGridField("denominazione", "Gruppo / Consigliere");
		gruppoConsigliere.setCanSort(true);
		gruppoConsigliere.setCanEdit(false);
		
		delegato = new ListGridField("decodificaDelegato", "Delegato");
		delegato.setCanSort(true);
		delegato.setCanEdit(false);
		
		voto = new ListGridField("voto", "Voto");
		voto.setAttribute("custom", true);
		voto.setCanSort(true);
		voto.setCanEdit(true);
		voto.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				LinkedHashMap<String, String> votoValueMap = getVotoValueMap();
				if(votoValueMap != null && votoValueMap.containsKey(record.getAttribute("voto"))) {
					return votoValueMap.get(record.getAttribute("voto"));
				}
				return null;							
			}
		});
		voto.setEmptyCellValue("<i>Seleziona...</i>");
		
		flgVotoExtra = new ListGridField("flgVotoExtra");
		flgVotoExtra.setHidden(true);
		flgVotoExtra.setCanHide(false);
		flgVotoExtra.setCanSort(true);
		flgVotoExtra.setCanEdit(false);
		
		setGridFields(
				idUser,
				gruppoConsigliere,
				delegato,
				voto,
				flgVotoExtra
		);
	}
	
	public LinkedHashMap<String, String> getVotoValueMap() {
		LinkedHashMap<String, String> votoValueMap = new LinkedHashMap<String, String>();
		votoValueMap.put("contrario", "contrario");
		votoValueMap.put("astenuto", "astenuto");
		votoValueMap.put("in_aula", "in aula");
		if ("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			votoValueMap.put("non presente al voto", "non presente al voto");
		}
		
		return votoValueMap;
	}
	
	@Override
	public ListGrid buildGrid() {	
		
		ListGrid grid = super.buildGrid();		
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.setEditByCell(true);
		grid.setEditorCustomizer(new ListGridEditorCustomizer() {
			
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				if(context.getEditField().getName().equals("voto")) {
					RadioGroupItem radioPresenzaItem = new RadioGroupItem("voto");
					radioPresenzaItem.setShowTitle(false);
					radioPresenzaItem.setValueMap(getVotoValueMap());
					radioPresenzaItem.setVertical(true);
					radioPresenzaItem.setWrap(false);
					radioPresenzaItem.setDefaultValue("contrario");
					return radioPresenzaItem; 
				}		
				return null;				
			}
		});	
		return grid;
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("lista_dichiarazioni_di_voto", fields);
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(removeButtonField == null) {
			removeButtonField = buildRemoveButtonField();
		}
		buttonsList.add(removeButtonField);
		
		return buttonsList;
	}
	
	protected ControlListGridField buildRemoveButtonField() {
		
		ControlListGridField removeButtonField = new ControlListGridField("removeButton");  
		removeButtonField.setAttribute("custom", true);	
		removeButtonField.setShowHover(true);		
		removeButtonField.setCanReorder(false);
		removeButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgVotoExtra") != null && "1".equals(record.getAttributeAsString("flgVotoExtra"))) {
					return buildImgButtonHtml("buttons/remove.png");
				}
				return null;
			}
		});
		removeButtonField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttributeAsString("flgVotoExtra") != null && "1".equals(record.getAttributeAsString("flgVotoExtra"))) {
					return "Rimuovi";
				}
				return null;
			}
		});		
		removeButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("flgVotoExtra") != null && "1".equals(event.getRecord().getAttributeAsString("flgVotoExtra"))) {
					onClickRemoveButton(event.getRecord());
				}
			}
		});	
		removeButtonField.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return true;
			}
		});
		
		return removeButtonField;
	}

	public void onClickRemoveButton(final ListGridRecord record) {
		
		instance.removeData(record);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> editButtons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addDichiarazioniButton = new ToolStripButton();   
		addDichiarazioniButton.setIcon("buttons/new.png");   
		addDichiarazioniButton.setIconSize(16);
		addDichiarazioniButton.setPrefix("aggiungi_dichiarazioni");
		addDichiarazioniButton.setPrompt("Aggiungi dichiarazioni");
		addDichiarazioniButton.addClickHandler(new ClickHandler() {	

			@Override
			public void onClick(ClickEvent event) {
				
				SceltaDichiarazioneVotiPopup sceltaDichiarazioneVotiPopup = new SceltaDichiarazioneVotiPopup(idSeduta) {
					
					@Override
					public void manageOnOkButtonClick(ListGridRecord[] selectedRecords) {
						for(int i = 0; i<selectedRecords.length; i++) {
							ListGridRecord listGridRecord = selectedRecords[i];
							listGridRecord.setAttribute("flgVotoExtra", "1");
							addData(listGridRecord);
						}
					}
					
				};
				sceltaDichiarazioneVotiPopup.show();
			}   
		});

				
		if(showAddDichiarazioniButton()) {
			editButtons.add(addDichiarazioniButton);
		}

		return editButtons;
	}
	
	public boolean showAddDichiarazioniButton() {
		return true;
	}
	
}