/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class DestinatariPrincipaliGridItem extends GridItem {
		
	private DestinatariPrincipaliListGridField spacer;	
	private DestinatariPrincipaliListGridField indirizzo;	
	private DestinatariPrincipaliListGridField statoConsolidamento; 
	private DestinatariPrincipaliListGridField statoConsegna;
	private DestinatariPrincipaliListGridField msgErrMancataConsegnaDestinatario;
	private DestinatariPrincipaliListGridField iconaStatoConsegna;
	private DestinatariPrincipaliListGridField flgRicevutaLettura;	
	private DestinatariPrincipaliListGridField estremiProt;
	private DestinatariPrincipaliListGridField flgNotifInteropEcc;		
	private DestinatariPrincipaliListGridField flgNotifInteropConf;		
	private DestinatariPrincipaliListGridField flgNotifInteropAgg;		
	private DestinatariPrincipaliListGridField flgNotifInteropAnn;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected ControlListGridField detailButtonField;
	
	protected int count = 0;
	
	private Record detailRecord;
	
	public DestinatariPrincipaliGridItem(String name, String title) {
		
		super(name, "listaDestinatariDettaglioEmail");
		
		setTitle(title);
		setShowTitle(true);
		
		setHeight("*");
		setGridPkField("indirizzo");
		setShowPreference(false);
		setShowEditButtons(false);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		
		spacer = new DestinatariPrincipaliListGridField("spacer");	
		spacer.setWidth(5);		
		
		indirizzo = new DestinatariPrincipaliListGridField("indirizzo", "Indirizzo");	
		
		statoConsolidamento = new DestinatariPrincipaliListGridField("statoConsolidamento"); statoConsolidamento.setHidden(true); statoConsolidamento.setCanHide(false);
		statoConsegna = new DestinatariPrincipaliListGridField("statoConsegna"); statoConsegna.setHidden(true); statoConsegna.setCanHide(false);
		msgErrMancataConsegnaDestinatario = new DestinatariPrincipaliListGridField("msgErrMancataConsegnaDestinatario"); msgErrMancataConsegnaDestinatario.setHidden(true); msgErrMancataConsegnaDestinatario.setCanHide(false);					
		
		estremiProt = new DestinatariPrincipaliListGridField("estremiProt", "Prot. assegnato");
		estremiProt.setAttribute("custom", true);			
		estremiProt.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("estremiProt") != null && !"".equals(record.getAttribute("estremiProt"))) {
					return "Prot. assegnato: " + record.getAttribute("estremiProt");
				}
				return null;
			}
		});
		estremiProt.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isCategoriaInteropSegn();
			}
		});
		
		flgNotifInteropConf = new DestinatariPrincipaliListGridField("flgNotifInteropConf");		
		flgNotifInteropConf.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgNotifInteropConf.setCanDragResize(false);				
		flgNotifInteropConf.setWidth(25);		
		flgNotifInteropConf.setAttribute("custom", true);	
		flgNotifInteropConf.setAlign(Alignment.CENTER);
		flgNotifInteropConf.setShowHover(true);		
		flgNotifInteropConf.setCanReorder(false);
		flgNotifInteropConf.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("flgNotifInteropConf") != null && "1".equals(record.getAttribute("flgNotifInteropConf"))) {
					return buildIconHtml("postaElettronica/notifInteropConf.png");					
				}
				return null;
			}
		});
		flgNotifInteropConf.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgNotifInteropConf") != null && "1".equals(record.getAttribute("flgNotifInteropConf"))) {
					return "Pervenuta/e notifiche interoperabili di conferma";					
				}			
				return null;				
			}
		});
		flgNotifInteropConf.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isCategoriaInteropSegn();
			}
		});
		

		flgNotifInteropEcc = new DestinatariPrincipaliListGridField("flgNotifInteropEcc");		
		flgNotifInteropEcc.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgNotifInteropEcc.setCanDragResize(false);				
		flgNotifInteropEcc.setWidth(25);		
		flgNotifInteropEcc.setAttribute("custom", true);	
		flgNotifInteropEcc.setAlign(Alignment.CENTER);
		flgNotifInteropEcc.setShowHover(true);		
		flgNotifInteropEcc.setCanReorder(false);
		flgNotifInteropEcc.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("flgNotifInteropEcc") != null && "1".equals(record.getAttribute("flgNotifInteropEcc"))) {
					return buildIconHtml("postaElettronica/notifInteropEcc.png");				
				}
				return null;
			}
		});
		flgNotifInteropEcc.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgNotifInteropEcc") != null && "1".equals(record.getAttribute("flgNotifInteropEcc"))) {
					return "Pervenuta/e notifiche interoperabili di eccezione";				
				}			
				return null;				
			}
		});
		flgNotifInteropEcc.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isCategoriaInteropSegn();
			}
		});
		
		flgNotifInteropAgg = new DestinatariPrincipaliListGridField("flgNotifInteropAgg");		
		flgNotifInteropAgg.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgNotifInteropAgg.setCanDragResize(false);				
		flgNotifInteropAgg.setWidth(25);		
		flgNotifInteropAgg.setAttribute("custom", true);	
		flgNotifInteropAgg.setAlign(Alignment.CENTER);
		flgNotifInteropAgg.setShowHover(true);		
		flgNotifInteropAgg.setCanReorder(false);
		flgNotifInteropAgg.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("flgNotifInteropAgg") != null && "1".equals(record.getAttribute("flgNotifInteropAgg"))) {
					return buildIconHtml("postaElettronica/notifInteropAgg.png");
				}
				return null;
			}
		});
		flgNotifInteropAgg.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgNotifInteropAgg") != null && "1".equals(record.getAttribute("flgNotifInteropAgg"))) {
					return "Pervenuta/e notifiche interoperabili di aggiornamento";
				}				
				return null;				
			}
		});
		flgNotifInteropAgg.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isCategoriaInteropSegn();
			}
		});
		
		flgNotifInteropAnn = new DestinatariPrincipaliListGridField("flgNotifInteropAnn");
		flgNotifInteropAnn.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgNotifInteropAnn.setCanDragResize(false);				
		flgNotifInteropAnn.setWidth(25);		
		flgNotifInteropAnn.setAttribute("custom", true);	
		flgNotifInteropAnn.setAlign(Alignment.CENTER);
		flgNotifInteropAnn.setShowHover(true);		
		flgNotifInteropAnn.setCanReorder(false);
		flgNotifInteropAnn.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("flgNotifInteropAnn") != null && "1".equals(record.getAttribute("flgNotifInteropAnn"))) {
					return buildIconHtml("postaElettronica/notifInteropAnn.png");					
				}
				return null;
			}
		});
		flgNotifInteropAnn.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgNotifInteropAnn") != null && "1".equals(record.getAttribute("flgNotifInteropAnn"))) {
					return "Pervenuta/e notifiche interoperabili di annullamento protocollazione";					
				}				
				return null;				
			}
		});
		flgNotifInteropAnn.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isCategoriaInteropSegn();
			}
		});
		
		flgRicevutaLettura = new DestinatariPrincipaliListGridField("flgRicevutaLettura");	
		flgRicevutaLettura.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRicevutaLettura.setCanDragResize(false);				
		flgRicevutaLettura.setWidth(25);		
		flgRicevutaLettura.setAttribute("custom", true);	
		flgRicevutaLettura.setAlign(Alignment.CENTER);
		flgRicevutaLettura.setShowHover(true);		
		flgRicevutaLettura.setCanReorder(false);
		flgRicevutaLettura.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("flgRicevutaLettura") != null && "1".equals(record.getAttribute("flgRicevutaLettura"))) {
					return buildIconHtml("postaElettronica/flgRicevutaLettura.png");					
				}
				return null;
			}
		});
		flgRicevutaLettura.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgRicevutaLettura") != null && "1".equals(record.getAttribute("flgRicevutaLettura"))) {
					return "Pervenuta ricevuta di avvenuta lettura";
				}				
				return null;				
			}
		});
		
		iconaStatoConsegna = new DestinatariPrincipaliListGridField("iconaStatoConsegna");
		iconaStatoConsegna.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaStatoConsegna.setCanDragResize(false);				
		iconaStatoConsegna.setWidth(25);		
		iconaStatoConsegna.setAttribute("custom", true);	
		iconaStatoConsegna.setAlign(Alignment.CENTER);
		iconaStatoConsegna.setShowHover(true);		
		iconaStatoConsegna.setCanReorder(false);
		iconaStatoConsegna.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttribute("iconaStatoConsegna") != null && !"".equals(record.getAttribute("iconaStatoConsegna"))) {
					Map<String, String> lMap = new HashMap<String, String>();
					lMap.put("OK", "postaElettronica/statoConsolidamento/consegnata.png");
					lMap.put("KO", "postaElettronica/statoConsolidamento/ko-red.png");
					lMap.put("IP", "postaElettronica/statoConsolidamento/presunto_ok.png");
					lMap.put("W", "postaElettronica/statoConsolidamento/ko-arancione.png");
					lMap.put("ND", "postaElettronica/statoConsolidamento/stato_consegna.png");
					if(lMap.containsKey(record.getAttribute("iconaStatoConsegna"))) {
						return buildIconHtml(lMap.get(record.getAttribute("iconaStatoConsegna")));
					}
				}
				return null;
			}
		});
		iconaStatoConsegna.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String result = null;
				String iconaStatoConsegna = record.getAttribute("iconaStatoConsegna") != null ? record.getAttribute("iconaStatoConsegna") : "";
				String statoConsegna = record.getAttribute("statoConsegna") != null ? record.getAttribute("statoConsegna") : "";
				String msgErrMancataConsegnaDestinatario = record.getAttribute("msgErrMancataConsegnaDestinatario") != null ? record.getAttribute("msgErrMancataConsegnaDestinatario") : "";
				if(!"".equals(msgErrMancataConsegnaDestinatario) && ("KO".equals(iconaStatoConsegna) || "W".equals(iconaStatoConsegna)) ) {
					result = statoConsegna + " - " + msgErrMancataConsegnaDestinatario;
				} else {
					result = statoConsegna;
				}
				return result;			
			}
		});

		setGridFields(
			spacer,
			indirizzo,
			statoConsolidamento,
			statoConsegna,
			msgErrMancataConsegnaDestinatario,
			estremiProt,
			flgNotifInteropEcc,
			flgNotifInteropConf,
			flgNotifInteropAgg,
			flgNotifInteropAnn,
			flgRicevutaLettura,
			iconaStatoConsegna,
			spacer
		);	
	}
	
	private boolean isCategoriaInteropSegn(){
		final Record detailRecord = getDetailRecord();
		return detailRecord != null && detailRecord.getAttribute("categoria") != null && detailRecord.getAttribute("categoria").equals("INTEROP_SEGN");
	}
	
	@Override
	protected VLayout buildLayout() {
		VLayout lVLayout = super.buildLayout();
		lVLayout.setBackgroundColor("#F0F0F0");		
		lVLayout.setOverflow(Overflow.VISIBLE);
		lVLayout.setHeight(1);	
//		if(getTitle() != null && !"".equals(getTitle())) {
//			lVLayout.setWidth100();
//			lVLayout.setPadding(11);
//			lVLayout.setMargin(4);
//			lVLayout.setIsGroup(true);
//			lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
//			lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitle() + "</span>");
//		}
		return lVLayout;
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setHeight(1);
		grid.setShowHeader(false);
		grid.setShowAllRecords(true);
//		grid.setBodyOverflow(Overflow.VISIBLE);
//		grid.setOverflow(Overflow.VISIBLE);
//		grid.setLeaveScrollbarGap(false);
		grid.setAlternateRecordStyles(false);	
//		grid.setCanDragRecordsOut(true);  
//		grid.setCanAcceptDroppedRecords(true);  
//		grid.setDragDataAction(DragDataAction.MOVE); 
//		grid.addSort(new SortSpecifier("account", SortDirection.ASCENDING));	
		grid.setBodyBackgroundColor("#F0F0F0");
		grid.setBackgroundColor("#F0F0F0");
		grid.setMinHeight(18);
		grid.setCellHeight(18);
		grid.setAutoFitMaxRecords(10);
		grid.setAutoFitData(com.smartgwt.client.types.Autofit.VERTICAL);
		return grid;		
	}
	
	@Override
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		return it.eng.utility.Styles.cellReadonly;
	}
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
//		if(detailButtonField == null) {
//			detailButtonField = buildDetailButtonField();					
//		}
//		buttonsList.add(detailButtonField);
		return buttonsList;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("buttons/detail.png");
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return I18NUtil.getMessages().detailButton_prompt();				
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				onClickDetailButton(event.getRecord());						
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		//TODO
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {
		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons					
		ListaBean configLista = Layout.getListConfig("listaDestinatariDettaglioEmail");	
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

	public class DestinatariPrincipaliListGridField extends ListGridField {
		
		public DestinatariPrincipaliListGridField(String name) {
	    	super(name);
	    	init();
	    }
	   
	    public DestinatariPrincipaliListGridField(String name, String title) {
	    	super(name, title);
	    	init();
	    }

	    private void init() {
	    	if(getTitle() != null && !"".equals(getTitle())) {
				setCanHide(true);
			} else {
				setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
				setCanHide(false);
			}
			setCanReorder(false);
			setCanFreeze(false);		
			setCanDragResize(true);		
			setShowDefaultContextMenu(false);
	    }
	}
	
	public Record getDetailRecord() {
		return detailRecord;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}
	
}
