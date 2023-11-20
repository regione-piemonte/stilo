/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

/**
 * 
 * @author matzanin
 *
 */

public class ListaGruppiAttiCampionamentoItem extends GridItem {
		
	protected ListGridField rowId;
	protected ListGridField mostraErrori;
	protected ListGridField tipologiaAtto;
//	protected ListGridField codiceAtto;
	protected ListGridField flgCodiceAttoParticolare;
	protected ListGridField flgDeterminaAContrarre;
//	protected ListGridField strutturaProponente;
	protected ListGridField rangeImporto;
	protected ListGridField percentualeCampionamento;
	protected ListGridField idRegola;
	protected ListGridField dataDecorrenzaRegola;
	protected ListGridField flgCessaRegolaPreEsistente;		
	protected ListGridField flgMantieniRegolaPreEsistente;		
	
	protected HashMap<String, String> mappaErrori = new HashMap<String, String>();
	
	protected int count = 0;
	
	public ListaGruppiAttiCampionamentoItem(String name) {
		this(name, null);
	}
	
	public ListaGruppiAttiCampionamentoItem(String name, String title) {
		
		super(name, "listaGruppiAttiCampionamento");
		
		if(title != null && !"".equals(title)) {
			setTitle(title);
			setShowTitle(true);
		} else {
			setShowTitle(false);
		}
		
		setGridPkField("rowId");
		setShowPreference(true);
		setShowEditButtons(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		
		rowId = new ListGridField("rowId"); 
		rowId.setHidden(true); 
		rowId.setCanHide(false);		 
		
		mostraErrori = new ListGridField("mostraErrori");
		mostraErrori.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		mostraErrori.setCanHide(false);
		mostraErrori.setCanDragResize(false);				
		mostraErrori.setWidth(25);		
		mostraErrori.setAttribute("custom", true);	
		mostraErrori.setAlign(Alignment.CENTER);
		mostraErrori.setShowHover(true);		
		mostraErrori.setCanReorder(false);
		mostraErrori.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				if(mappaErrori != null && mappaErrori.get(record.getAttribute("rowId")) != null && !"".equals(mappaErrori.get(record.getAttribute("rowId")))) {
					return buildIconHtml("exclamation.png");
				}
				return null;
			}
		});
		mostraErrori.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(mappaErrori != null && mappaErrori.get(record.getAttribute("rowId")) != null && !"".equals(mappaErrori.get(record.getAttribute("rowId")))) {
					return mappaErrori.get(record.getAttribute("rowId"));
				}
				return null;				
			}
		});		
		
		tipologiaAtto = new ListGridField("tipologiaAtto", "Tipologia atto");
		
//		codiceAtto = new ListGridField("codiceAtto", "Cod. atto");
		
		flgCodiceAttoParticolare = new ListGridField("flgCodiceAttoParticolare", "Tipo atto particolare");
		
		flgDeterminaAContrarre = new ListGridField("flgDeterminaAContrarre", "Det. a contrarre");
		
//		strutturaProponente = new ListGridField("strutturaProponente", "Struttura");
		
		rangeImporto = new ListGridField("rangeImporto", "Range importo");
		
		percentualeCampionamento = new ListGridField("percentualeCampionamento", "% camp.");
		percentualeCampionamento.setType(ListGridFieldType.INTEGER);
		
		idRegola = new ListGridField("idRegola", "Id. regola");
		
		dataDecorrenzaRegola = new ListGridField("dataDecorrenzaRegola", "Decorrenza regola");
		dataDecorrenzaRegola.setType(ListGridFieldType.DATE);
		dataDecorrenzaRegola.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataDecorrenzaRegola.setWrap(false);
		
		flgCessaRegolaPreEsistente = new ListGridField("flgCessaRegolaPreEsistente", "Cessa regola pre-esistente");
		flgCessaRegolaPreEsistente.setAttribute("isCheckEditabile", true);
		flgCessaRegolaPreEsistente.setAttribute("custom", true);	
		flgCessaRegolaPreEsistente.setAlign(Alignment.CENTER);
		flgCessaRegolaPreEsistente.setShowHover(false);		
		flgCessaRegolaPreEsistente.setCanReorder(false);
		flgCessaRegolaPreEsistente.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isIdRegolaValorizzata = record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola").trim());
				if(isIdRegolaValorizzata) {
					if(record.getAttribute("flgCessaRegolaPreEsistente") != null && "true".equals(record.getAttribute("flgCessaRegolaPreEsistente"))) {
						return buildImgButtonHtml("checked.png");	
					} else {
						return buildImgButtonHtml("unchecked.png");
					}
				}
				return null;
			}
		});	
		/*
		flgCessaRegolaPreEsistente.setType(ListGridFieldType.ICON);
		flgCessaRegolaPreEsistente.setWidth(30);
		flgCessaRegolaPreEsistente.setIconWidth(16);
		flgCessaRegolaPreEsistente.setIconHeight(16);
		flgCessaRegolaPreEsistente.setDefaultValue(false);
		Map<String, String> flgCessaRegolaPreEsistenteValueIcons = new HashMap<String, String>();		
		flgCessaRegolaPreEsistenteValueIcons.put("true", "checked.png");
		flgCessaRegolaPreEsistenteValueIcons.put("false", "unchecked.png");
		flgCessaRegolaPreEsistenteValueIcons.put("undefined", "blank.png");
		flgCessaRegolaPreEsistenteValueIcons.put("", "blank.png");
		flgCessaRegolaPreEsistente.setValueIcons(flgCessaRegolaPreEsistenteValueIcons);		
		flgCessaRegolaPreEsistente.setBaseStyle(it.eng.utility.Styles.cellClickable);		
		*/
		flgCessaRegolaPreEsistente.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				if(isEditable() && event.getRecord().getAttribute("idRegola") != null && !"".equals(event.getRecord().getAttribute("idRegola"))) {									
					if(event.getRecord().getAttribute("flgCessaRegolaPreEsistente") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgCessaRegolaPreEsistente"))) {
						event.getRecord().setAttribute("flgCessaRegolaPreEsistente", false);
					} else {
						event.getRecord().setAttribute("flgCessaRegolaPreEsistente", true);
						event.getRecord().setAttribute("flgMantieniRegolaPreEsistente", false);						
					}
					updateGridItemValue();
				}
			}
		});

		flgMantieniRegolaPreEsistente = new ListGridField("flgMantieniRegolaPreEsistente", "Mantieni regola pre-esistente");
		flgMantieniRegolaPreEsistente.setAttribute("isCheckEditabile", true);
		flgMantieniRegolaPreEsistente.setAttribute("custom", true);	
		flgMantieniRegolaPreEsistente.setAlign(Alignment.CENTER);
		flgMantieniRegolaPreEsistente.setShowHover(false);		
		flgMantieniRegolaPreEsistente.setCanReorder(false);
		flgMantieniRegolaPreEsistente.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isIdRegolaValorizzata = record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola").trim());
				if(isIdRegolaValorizzata) {
					if(record.getAttribute("flgMantieniRegolaPreEsistente") != null && "true".equals(record.getAttribute("flgMantieniRegolaPreEsistente"))) {
						return buildImgButtonHtml("checked.png");	
					} else {
						return buildImgButtonHtml("unchecked.png");
					}
				}
				return null;
			}
		});	
		/*
		flgMantieniRegolaPreEsistente.setType(ListGridFieldType.ICON);
		flgMantieniRegolaPreEsistente.setWidth(30);
		flgMantieniRegolaPreEsistente.setIconWidth(16);
		flgMantieniRegolaPreEsistente.setIconHeight(16);
		flgMantieniRegolaPreEsistente.setDefaultValue(false);
		Map<String, String> flgMantieniRegolaPreEsistenteValueIcons = new HashMap<String, String>();		
		flgMantieniRegolaPreEsistenteValueIcons.put("true", "checked.png");
		flgMantieniRegolaPreEsistenteValueIcons.put("false", "unchecked.png");
		flgMantieniRegolaPreEsistenteValueIcons.put("undefined", "blank.png");
		flgMantieniRegolaPreEsistenteValueIcons.put("", "blank.png");
//		flgMantieniRegolaPreEsistente.setValueIcons(flgMantieniRegolaPreEsistenteValueIcons);		
		flgMantieniRegolaPreEsistente.setBaseStyle(it.eng.utility.Styles.cellClickable);		
		*/
		flgMantieniRegolaPreEsistente.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				if(isEditable() && event.getRecord().getAttribute("idRegola") != null && !"".equals(event.getRecord().getAttribute("idRegola"))) {									
					if(event.getRecord().getAttribute("flgMantieniRegolaPreEsistente") != null && "true".equalsIgnoreCase(event.getRecord().getAttribute("flgMantieniRegolaPreEsistente"))) {
						event.getRecord().setAttribute("flgMantieniRegolaPreEsistente", false);
					} else {
						event.getRecord().setAttribute("flgMantieniRegolaPreEsistente", true);
						event.getRecord().setAttribute("flgCessaRegolaPreEsistente", false);						
					}
					updateGridItemValue();
				}
			}
		});
		
		setGridFields(
			rowId,
			mostraErrori,
			tipologiaAtto,
//			codiceAtto,
			flgCodiceAttoParticolare,
			flgDeterminaAContrarre,
//			strutturaProponente,
			rangeImporto,
			percentualeCampionamento,
			idRegola,
			dataDecorrenzaRegola,
			flgCessaRegolaPreEsistente,
			flgMantieniRegolaPreEsistente
		);	
	}
	
	@Override
	public void clearErrors() {
		super.clearErrors();
		mappaErrori = new HashMap<String, String>();	
		for (ListGridRecord record : grid.getRecords()) {
			grid.refreshRow(grid.getRecordIndex(record));
		}
	}
	
	public Map getMapErrors() {
		HashMap<String, String> errors = new HashMap<String, String>();
		if(mappaErrori != null) {
			for(String rowId: mappaErrori.keySet()) {
				if(mappaErrori.get(rowId) != null && mappaErrori.get(rowId) != null && !"".equals(mappaErrori.get(rowId))) {
					errors.put(rowId, mappaErrori.get(rowId));
				}
			}
		}
		return errors;
	}
	
	@Override
	public Boolean validate() {
		
		boolean valid = true;
		
		mappaErrori = new HashMap<String, String>();
		
		for (ListGridRecord record : grid.getRecords()) {
			
			String rowId = record.getAttribute("rowId");
			
			boolean isIdRegolaValorizzata = record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola").trim());			
			boolean flgCessaRegolaPreEsistente = record.getAttributeAsBoolean("flgCessaRegolaPreEsistente") != null && record.getAttributeAsBoolean("flgCessaRegolaPreEsistente");
			boolean flgMantieniRegolaPreEsistente = record.getAttributeAsBoolean("flgMantieniRegolaPreEsistente") != null && record.getAttributeAsBoolean("flgMantieniRegolaPreEsistente");	
			
			if(isIdRegolaValorizzata && !flgCessaRegolaPreEsistente && !flgMantieniRegolaPreEsistente) {
				mappaErrori.put(rowId, "Obbligatorio indicare se mantenere o cessare la regola pre-esistente");
				valid = false;	
			}
			
			grid.refreshRow(grid.getRecordIndex(record));
		}
		
		return valid;
	}
	
	@Override
	public void setData(RecordList data) {
		mappaErrori = new HashMap<String, String>();
		if (data != null) {
			for (int i = 0; i < data.getLength(); i++) {			
				Record lRecord = data.get(i);
				lRecord.setAttribute("rowId", i + 1);				
			}
		}
		super.setData(data);		
	}
	
	@Override
	public void addData(Record record) {		
		super.addData(record);
	}
		
	@Override
	public void updateData(Record record, Record oldRecord) {
		String rowId = record.getAttribute("rowId");		
		mappaErrori.remove(rowId);		
		super.updateData(record, oldRecord);
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		return grid;		
	}
	
	@Override
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			boolean isIdRegolaValorizzata = record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola").trim());
			boolean isDataDecorrenzaRegolaValorizzata = record.getAttribute("dataDecorrenzaRegola") != null && !"".equals(record.getAttribute("dataDecorrenzaRegola").trim());
			boolean isPercentualeCampionamentoValorizzata = record.getAttribute("percentualeCampionamento") != null && !"".equals(record.getAttribute("percentualeCampionamento").trim());
			if(isIdRegolaValorizzata && isDataDecorrenzaRegolaValorizzata && isPercentualeCampionamentoValorizzata) {
				if(record.getAttribute("colonneRegola") != null && !"".equals(record.getAttribute("colonneRegola"))) {
					StringSplitterClient st = new StringSplitterClient(record.getAttribute("colonneRegola"), "|*|");
					HashSet<String> setColonneRegola = new HashSet<String>(Arrays.asList(st.getTokens()));
					if (setColonneRegola.contains(grid.getFieldName(colNum))) {  
						return it.eng.utility.Styles.cellVariazione; 		             
			        }
				}			
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> editButtons = new ArrayList<ToolStripButton>();		
		ToolStripButton flgCessaRegolePreEsistentiButton = new ToolStripButton("Cessa tutte le regole pre-esistenti");   
		flgCessaRegolePreEsistentiButton.setIcon("buttons/checkboxField.png");   
		flgCessaRegolePreEsistentiButton.setIconSize(16);
		flgCessaRegolePreEsistentiButton.setPrefix("flgCessaRegolePreEsistentiButton");
		flgCessaRegolePreEsistentiButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				for (ListGridRecord record : grid.getRecords()) {
					if(isEditable() && record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola"))) {									
						record.setAttribute("flgCessaRegolaPreEsistente", true);
						record.setAttribute("flgMantieniRegolaPreEsistente", false);												
					}
				}
				updateGridItemValue();
			}   
		});  		
		editButtons.add(flgCessaRegolePreEsistentiButton);	
		ToolStripButton flgMantieniRegolePreEsistentiButton = new ToolStripButton("Mantieni tutte le regole pre-esistenti");   
		flgMantieniRegolePreEsistentiButton.setIcon("buttons/checkboxField.png");   
		flgMantieniRegolePreEsistentiButton.setIconSize(16);
		flgMantieniRegolePreEsistentiButton.setPrefix("flgMantieniRegolePreEsistentiButton");
		flgMantieniRegolePreEsistentiButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				for (ListGridRecord record : grid.getRecords()) {
					if(isEditable() && record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola"))) {									
						record.setAttribute("flgMantieniRegolaPreEsistente", true);
						record.setAttribute("flgCessaRegolaPreEsistente", false);												
					}
				}
				updateGridItemValue();
			}   
		});  		
		editButtons.add(flgMantieniRegolePreEsistentiButton);		
		return editButtons;		
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		return new ArrayList<ControlListGridField>();					
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
//		field.setCanEdit(false);
	}	
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(getNomeLista(), fields);
	}
	
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		redrawRecordButtons();		
	}	
	
}
