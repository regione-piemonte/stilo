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

public class GruppiAttiCampionamentoGridItem extends GridItem {
		
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
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	
	protected HashMap<String, String> mappaErrori = new HashMap<String, String>();
	
	protected int count = 0;
	
	public GruppiAttiCampionamentoGridItem(String name) {
		this(name, null);
	}
	
	public GruppiAttiCampionamentoGridItem(String name, String title) {
		
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
				lRecord.setAttribute("valuesOrig", data.get(i).toMap());
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
	
	protected List<ControlListGridField> getButtonsFields() {
		return new ArrayList<ControlListGridField>();					
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {
		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons					
		ListaBean configLista = Layout.getListConfig(getNomeLista());	
		formattedFields = new ListGridField[length];
		controlFields = new ArrayList<String>();
		int count = 0;
		for (final ListGridField field : fields){	
			String fieldName = field.getName();			
			boolean skip = false;
			for(ControlListGridField buttonField : getButtonsFields()) {
				if(fieldName.equals(buttonField.getName())) {
					skip = true;
					break;
				}
			}						
			if(!skip) {
				try {				
//					field.setCanEdit(false);
					if(field instanceof ControlListGridField) {
						controlFields.add(fieldName);
					}
					field.setAlign(Alignment.CENTER);					
					if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
						field.setPrompt(field.getTitle());					
					}							
					if(configLista.getColonneOrdinabili() != null) {
//						if(configLista.getColonneDefault().contains(fieldName)) {
//							field.setHidden(false);
//						} else {
//							field.setHidden(true);
//						}
						if(configLista.getColonneOrdinabili().contains(fieldName)) {
							field.setCanSort(true);
						} else {
							field.setCanSort(false);
						}		
					}
					//Recupero il tipo relativo
					ListGridFieldType fieldType = field.getType();
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellAlign(Alignment.LEFT);	
						}
					} else if (fieldType.equals(ListGridFieldType.INTEGER) 
							|| fieldType.equals(ListGridFieldType.BINARY) 
							|| fieldType.equals(ListGridFieldType.FLOAT)) {
						field.setCellAlign(Alignment.RIGHT);
					} else if (fieldType.equals(ListGridFieldType.DATE) 
							|| fieldType.equals(ListGridFieldType.TIME)) {
						field.setCellAlign(Alignment.CENTER);										
					} else if (fieldType.equals(ListGridFieldType.IMAGE) 
							|| fieldType.equals(ListGridFieldType.IMAGEFILE) 
							|| fieldType.equals(ListGridFieldType.LINK) 
							|| fieldType.equals(ListGridFieldType.ICON)) {
						field.setCellAlign(Alignment.CENTER);
					} else {
						field.setCellAlign(Alignment.LEFT);
					}
					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellFormatter(new CellFormatter() {							
								@Override
								public String format(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									if(record != null) {
										record.setAttribute("realValue"+colNum, value);
									}
									if (value==null) return null;
									String lStringValue = value.toString();
									if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
										return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
									} else return lStringValue;
								}
							});
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
									return (realValue != null) ? (String) realValue : (String) value;
								}
							});			
						}			
					} else if (fieldType.equals(ListGridFieldType.INTEGER)) {	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								String value = record.getAttribute(fieldName);
								return value != null && !"".equals(value) ? Long.parseLong(value.replace(".", "")) : 0;																						
							}
						});		
					} else if (fieldType.equals(ListGridFieldType.DATE)) {			
						LinkedHashMap<String, String> groupingModes = new LinkedHashMap<String, String>();
						groupingModes.put("date", I18NUtil.getMessages().groupingModePerGiorno_title()); 
						groupingModes.put("month", I18NUtil.getMessages().groupingModePerMese_title());
						groupingModes.put("year", I18NUtil.getMessages().groupingModePerAnno_title());         
						field.setGroupingModes(groupingModes);  															
						field.setGroupValueFunction(new GroupValueFunction() {             
							public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) { 
								Date date = record != null ? DateUtil.parseInput(record.getAttributeAsString(fieldName)) : null;                  								
								DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
								if (date == null) return " ";								
								if(field.getGroupingMode() != null) {
									DateTimeFormat dateFormatYear = DateTimeFormat.getFormat("yyyy");	
									String year = dateFormatYear.format(date);
									if(field.getGroupingMode().equals("year")) {
										return year;
									} else if(field.getGroupingMode().equals("month")) {
										DateTimeFormat dateFormatMonth = DateTimeFormat.getFormat("MM");
										switch(Integer.parseInt(dateFormatMonth.format(date))) {
										case 1: return I18NUtil.getMessages().decodificaMese_1() + " " + year;
										case 2: return I18NUtil.getMessages().decodificaMese_2() + " " + year;
										case 3: return I18NUtil.getMessages().decodificaMese_3() + " " + year;
										case 4: return I18NUtil.getMessages().decodificaMese_4() + " " + year;
										case 5: return I18NUtil.getMessages().decodificaMese_5() + " " + year;
										case 6: return I18NUtil.getMessages().decodificaMese_6() + " " + year;
										case 7: return I18NUtil.getMessages().decodificaMese_7() + " " + year;
										case 8: return I18NUtil.getMessages().decodificaMese_8() + " " + year;
										case 9: return I18NUtil.getMessages().decodificaMese_9() + " " + year;
										case 10: return I18NUtil.getMessages().decodificaMese_10() + " " + year;
										case 11: return I18NUtil.getMessages().decodificaMese_11() + " " + year;
										case 12: return I18NUtil.getMessages().decodificaMese_12() + " " + year;
										}										
									} 	
								}
								return dateFormat.format(date); 								      																																			
							}
						}); 	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								return formatDateForSorting(record, fieldName);																									
							}
						});		
						field.setCellFormatter(new CellFormatter() {	
							@Override
							public String format(Object value, ListGridRecord record, int rowNum,
									int colNum) {
								return manageDateCellFormat(value, field, record);
							}
						});																										
					} else if (fieldType.equals(ListGridFieldType.ICON)){						
						final Map<String, String> valueHovers = field.getAttributeAsMap("valueHovers");
						if (valueHovers != null) {
							final ListGridField iconfield = field;
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
									if(record != null) return valueHovers.get(record.getAttribute(iconfield.getName()));
									else return (String) value;
								}
							});
						}
					}
					formattedFields[count] = field;					
				} catch (Exception e) {
					formattedFields[count] = field;
				}			
				count++;
			}
		}						
		if(getButtonsFields() != null) {
			for(int i = 0; i < getButtonsFields().size(); i++) {
				controlFields.add(getButtonsFields().get(i).getName());
				formattedFields[count] = getButtonsFields().get(i);
				count++;
			}					
		}						
		super.setGridFields(formattedFields);
	}	
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}

	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}
	
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		return value != null && !"".equals(value) ? DateUtil.parseInput(value) : null;
	}
	
	public String manageDateCellFormat(Object value, ListGridField field, ListGridRecord record) {
		if (value==null) return null;
		String lStringValue = value.toString();	
		if(field.getDateFormatter() != null && field.getDateFormatter().equals(DateDisplayFormat.TOEUROPEANSHORTDATE)) {									
			lStringValue = (lStringValue != null && !"".equals(lStringValue)) ? lStringValue.substring(0, 10) : "";
		}
		return lStringValue;
	}
	
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		redrawRecordButtons();		
	}	
	
}
