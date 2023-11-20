/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.EditaProtocolloWindowFromRegEmergenza;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class RegistrazioniEmergenzaList extends CustomList {
	
	private ListGridField idRegEmergenza;
	private ListGridField registro;
	private ListGridField numero;
	private ListGridField effettuataIl;
	private ListGridField effettuataDa; 
	private ListGridField tipoProt;
	private ListGridField desUoProt;
	private ListGridField oggetto;
	private ListGridField mittente;
	private ListGridField destinatari;
	private ListGridField protRicevuto;
	private ListGridField dataOraArrivo;
	private ListGridField riversataIl;
	private ListGridField flgCreataDaMe;
	
	protected ControlListGridField protocollaButtonField;		
	
	public RegistrazioniEmergenzaList(String nomeEntita){

		super(nomeEntita);
		
		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);	
		setSelectionType(SelectionStyle.NONE);
		
		idRegEmergenza = new ListGridField("idRegEmergenza", "Id.");
		idRegEmergenza.setHidden(true);

		registro = new ListGridField("registro", "Registro");
		
		numero = new ListGridField("numero", "N°");
		
		effettuataIl  = new ListGridField("effettuataIl", "Effettuata il");
		effettuataIl.setType(ListGridFieldType.DATE);
		effettuataIl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		effettuataIl.setWrap(false);
						
		tipoProt = new ListGridField("tipoProt", "Tipo protocollo");
		tipoProt.setType(ListGridFieldType.ICON);
		tipoProt.setWidth(30);
		tipoProt.setIconWidth(16);
		tipoProt.setIconHeight(16);
		Map<String, String> tipoProtValueIcons = new HashMap<String, String>();
		tipoProtValueIcons.put("E", "menu/protocollazione_entrata.png");
		tipoProtValueIcons.put("U", "menu/protocollazione_uscita.png");
		tipoProtValueIcons.put("I", "menu/protocollazione_interna.png");
		tipoProtValueIcons.put("" , "blank.png");
		tipoProt.setValueIcons(tipoProtValueIcons);
		Map<String, String> tipoProtValueHovers = new HashMap<String, String>();
		tipoProtValueHovers.put("E", I18NUtil.getMessages().archivio_list_tipoProtocolloInEntrataAlt_value());
		tipoProtValueHovers.put("U", I18NUtil.getMessages().archivio_list_tipoProtocolloInUscitaAlt_value());
		tipoProtValueHovers.put("I", I18NUtil.getMessages().archivio_list_tipoProtocolloInternoAlt_value());
		tipoProtValueHovers.put("",  "");
		tipoProt.setAttribute("valueHovers", tipoProtValueHovers);
		
		effettuataDa = new ListGridField("effettuataDa", "Effettuata da");
		
		desUoProt = new ListGridField("desUoProt", "U.O. protocollante");
		
		oggetto = new ListGridField("oggetto", "Oggetto");
		
		mittente = new ListGridField("mittente", "Mittente");		
		
		destinatari = new ListGridField("destinatari", "Destinatario/i");		
		
		protRicevuto = new ListGridField("protRicevuto", "Prot. ricevuto");		
		
		dataOraArrivo  = new ListGridField("dataOraArrivo", "Data e ora arrivo");
		dataOraArrivo.setType(ListGridFieldType.DATE);
		dataOraArrivo.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataOraArrivo.setWrap(false);
		
		riversataIl  = new ListGridField("riversataIl", "Riversata sul sistema il");
		riversataIl.setType(ListGridFieldType.DATE);
		riversataIl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		riversataIl.setWrap(false);
		
		flgCreataDaMe = new ListGridField("flgCreataDaMe", "Registrata da me");
		flgCreataDaMe.setType(ListGridFieldType.ICON);
		flgCreataDaMe.setWidth(30);
		flgCreataDaMe.setIconWidth(16);
		flgCreataDaMe.setIconHeight(16);
		Map<String, String> flgCreataDaMeValueIcons = new HashMap<String, String>();		
		flgCreataDaMeValueIcons.put("1", "ok.png");
		flgCreataDaMeValueIcons.put("0", "blank.png");
		flgCreataDaMeValueIcons.put("", "blank.png");
		flgCreataDaMe.setValueIcons(flgCreataDaMeValueIcons);
		flgCreataDaMe.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("1".equals(record.getAttribute("flgCreataDaMe"))) {
					return "Registrata da me";
				}				
				return null;
			}
		});		
		
		setFields(
				idRegEmergenza,
				registro,
				numero,
				effettuataIl,
				tipoProt,
				effettuataDa, 
				desUoProt,
				oggetto,
				mittente,
				destinatari,
				protRicevuto,
				dataOraArrivo,
				riversataIl,
				flgCreataDaMe
		);

	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 25;
	}
			
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {		
		
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	

			ImgButton protocollaButton = new ImgButton();   
			protocollaButton.setShowDown(false);   
			protocollaButton.setShowRollOver(false);      
			protocollaButton.setSrc("menu/protocollazione.png");   
			protocollaButton.setPrompt("Protocolla");   
			protocollaButton.setSize(16);  
			protocollaButton.addClickHandler(new ClickHandler() {   
				public void onClick(ClickEvent event) {      
					manageProtocollaButtonClick(record);
				}  
			});  
						
			lCanvasReturn = protocollaButton;

		}			

		return lCanvasReturn;
	}
	
	protected void manageProtocollaButtonClick(ListGridRecord record) {		
		
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocollazioneRegEmergenzaDatasource");
		Record lRecordToLoad = new Record();
		final String idRegEmergenza = record.getAttribute("idRegEmergenza");
		lRecordToLoad.setAttribute("idRegEmergenza", record.getAttribute("idRegEmergenza"));
		final String tipoProt = record.getAttribute("tipoProt") != null ? record.getAttribute("tipoProt") : "";													
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecordDetail = response.getData()[0];
					EditaProtocolloWindowFromRegEmergenza lEditaProtocolloWindowFromRegEmergenza = new EditaProtocolloWindowFromRegEmergenza("protocollazione_reg_emergenza", lRecordDetail, tipoProt, idRegEmergenza, new DSCallback() {						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							layout.doSearch();
						}
					});		
					lEditaProtocolloWindowFromRegEmergenza.show();										
				}
			}
		});			
	}
	
	@Override  
	public void manageContextClick(final Record record) {											
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)), null);			
		}	
	}	
	
	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		final Menu contextMenu = new Menu();					
		MenuItem protocollaMenuItem = new MenuItem("Protocolla", "menu/protocollazione.png");   
		protocollaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {

				manageProtocollaButtonClick(record); 
			}   
		});   			
		contextMenu.addItem(protocollaMenuItem);
		contextMenu.showContextMenu();								
	}

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	 
	@Override  
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();			
		if(!isDisableRecordComponent()) {
			if(buttonsField == null) {
				buttonsField = new ControlListGridField("buttons");		
				buttonsField.setWidth(getButtonsFieldWidth());
				buttonsField.setCanReorder(false);	
				buttonsField.addRecordClickHandler(new RecordClickHandler() {					
					@Override
					public void onRecordClick(RecordClickEvent event) {

						String rowClickEventSource = event.getRecord().getAttribute("rowClickEventSource");  
						if(rowClickEventSource == null || "".equals(rowClickEventSource)) {
							event.cancel();
						}
					}
				});				
			}
			buttonsList.add(buttonsField);
		} else {
			if(protocollaButtonField == null) {
				protocollaButtonField = new ControlListGridField("protocollaButton");  
				protocollaButtonField.setAttribute("custom", true);	
				protocollaButtonField.setShowHover(true);	
				protocollaButtonField.setCanReorder(false);		
				protocollaButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {										
						return buildImgButtonHtml("menu/protocollazione.png");																
					}
				});
				protocollaButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
									
						return "Protocolla";
					}
				});		
				protocollaButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {

						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						manageProtocollaButtonClick(record);
					}
				});													
			}	
			buttonsList.add(protocollaButtonField);
		}	
		return buttonsList;		
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}