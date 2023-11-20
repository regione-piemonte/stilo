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

public class ListaDestinatariPrincipaliItem extends GridItem {
		
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
	
	protected ControlListGridField detailButtonField;
	
	protected int count = 0;
	
	private Record detailRecord;
	
	public ListaDestinatariPrincipaliItem(String name, String title) {
		
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
	
	@Override
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
	protected void setCanEditForEachGridField(ListGridField field) {
//		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaDestinatariDettaglioEmail", fields);
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
