/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioLayout;
import it.eng.auriga.ui.module.layout.client.archivio.GenericWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public class ModelliDocList extends CustomList {
		
	private ListGridField idModelloField;
	private ListGridField nomeModelloField;
	private ListGridField desModelloField;
	private ListGridField noteField;
	private ListGridField tipoModelloField;
	private ListGridField tsCreazioneField;
	private ListGridField creatoDaField;
	private ListGridField tsUltimoAggField;
	private ListGridField ultimoAggEffDaField;
	private ListGridField flgValidoField;
	private ListGridField nomeEntitaAssociataField;
	
	protected ControlListGridField profilaButtonField;		
	protected ControlListGridField scaricaFileButtonField;
	
	public ModelliDocList(String nomeEntita){

		super(nomeEntita);
		
		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);	
		setSelectionType(SelectionStyle.NONE);
		
		idModelloField = new ListGridField("idModello", I18NUtil.getMessages().modellidoclist_idModello_title());
		idModelloField.setHidden(true);

		nomeModelloField = new ListGridField("nomeModello", I18NUtil.getMessages().modellidoclist_nomeModello_title());
		
		desModelloField = new ListGridField("desModello", I18NUtil.getMessages().modellidoclist_desModello_title());
		
		noteField = new ListGridField("note", I18NUtil.getMessages().modellidoclist_note_title());
		
		tipoModelloField = new ListGridField("tipoModello", I18NUtil.getMessages().modellidoclist_tipoModello_title());
		
		tsCreazioneField = new ListGridField("tsCreazione", I18NUtil.getMessages().modellidoclist_tsCreazione_title());
		tsCreazioneField.setType(ListGridFieldType.DATE);
		tsCreazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		creatoDaField = new ListGridField("creatoDa", I18NUtil.getMessages().modellidoclist_creatoDa_title());
		
		tsUltimoAggField = new ListGridField("tsUltimoAgg", I18NUtil.getMessages().modellidoclist_tsUltimoAgg_title());
		tsUltimoAggField.setType(ListGridFieldType.DATE);
		tsUltimoAggField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		ultimoAggEffDaField = new ListGridField("ultimoAggEffDa", I18NUtil.getMessages().modellidoclist_ultimoAggEffDa_title());
		
		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().modellidoclist_flgValido_title());
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("true",  "ok.png");
		flgValidoValueIcons.put("false", "blank.png");
		flgValidoValueIcons.put("",      "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgValido"))) {
					return "Valido";
				}				
				return null;
			}
		});
		
		nomeEntitaAssociataField = new ListGridField("nomeEntitaAssociata", I18NUtil.getMessages().modellidoclist_nomeEntitaAssociata_title());
		
		setFields (				
			idModelloField,
			nomeModelloField,
			desModelloField,
			noteField,
			tipoModelloField,
			tsCreazioneField,
			creatoDaField,
			tsUltimoAggField,
			ultimoAggEffDaField,
			flgValidoField,
			nomeEntitaAssociataField
		);

	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 25;
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}
	
	@Override  
	public void manageContextClick(final Record record) {											
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)), null);			
		}	
	}	
	
	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		final Menu contextMenu = new Menu();			
		if(showProfilaButtonField() && isRecordAbilToProfile(record)) {
			MenuItem profilaMenuItem = new MenuItem("Profila", "lookup/attributiadd.png");   
			profilaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageProfilaButtonClick(record); 
				}   
			});   			
			contextMenu.addItem(profilaMenuItem);
		}
		if(showScaricaButtonField() && isRecordAbilToDownload(record)) {
			MenuItem scaricaFileMenuItem = new MenuItem("Scarica file", "file/download_manager.png");   
			scaricaFileMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageScaricaFileButtonClick(record); 
				}   
			});   			
			contextMenu.addItem(scaricaFileMenuItem);
		}
		if(contextMenu.getItems() != null && contextMenu.getItems().length > 0) {
			contextMenu.showContextMenu();								
		}
	}

	protected void manageProfilaButtonClick(ListGridRecord record) {		
		ProfilaturaModelliDocWindow profilaturaModelliDocWindow = new ProfilaturaModelliDocWindow(record.getAttributeAsString("idModello"), record.getAttributeAsString("nomeModello"), record.getAttributeAsString("nomeTabella"), record.getAttributeAsString("tipoEntitaAssociata"), record.getAttributeAsString("idEntitaAssociata"), record.getAttributeAsString("nomeEntitaAssociata")) {
			
			@Override
			public void manageOnCloseClick() {
				super.manageOnCloseClick();
				if(getLayout() != null) {
					getLayout().doSearch();
				}
			}
		};
		profilaturaModelliDocWindow.show();		
	}
	
	protected void manageScaricaFileButtonClick(ListGridRecord record) {	
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("extractVerModello", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record object = response.getData()[0];
				Record lRecordDownload = new Record();
				lRecordDownload.setAttribute("displayFilename", object.getAttributeAsString("nomeFileModello"));
				lRecordDownload.setAttribute("uri", object.getAttributeAsString("uriFileModello"));
				lRecordDownload.setAttribute("sbustato", "false");
				lRecordDownload.setAttribute("remoteUri", true);
				DownloadFile.downloadFromRecord(lRecordDownload, "FileToExtractBean");
			}
		}, new DSRequest());		
	}

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
	
	@Override
	protected boolean showModifyButtonField() {
		return true;
	}
	 
	protected boolean showProfilaButtonField() {
		return true;
	}
	
	protected boolean showScaricaButtonField() {
		return true;
	}
	
	protected boolean showScaricaFileButtonField() {
		return true;
	}
	
	protected boolean isRecordAbilToProfile(ListGridRecord record) {
		return record.getAttributeAsString("uriFileModello") != null && !"".equals(record.getAttributeAsString("uriFileModello")) &&
			   record.getAttributeAsString("tipoModello") != null && TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equals(record.getAttributeAsString("tipoModello")) /**&&
			   record.getAttributeAsString("nomeTabella") != null && !"".equals(record.getAttributeAsString("nomeTabella")) &&		
			   record.getAttributeAsString("tipoEntitaAssociata") != null && !"".equals(record.getAttributeAsString("tipoEntitaAssociata")) &&		
			   record.getAttributeAsString("idEntitaAssociata") != null && !"".equals(record.getAttributeAsString("idEntitaAssociata")) &&
			   record.getAttributeAsString("nomeEntitaAssociata") != null && !"".equals(record.getAttributeAsString("nomeEntitaAssociata"))*/;
	}
	
	protected boolean isRecordAbilToDownload(ListGridRecord record) {
		return record.getAttributeAsString("uriFileModello") != null && !"".equals(record.getAttributeAsString("uriFileModello"));
	}
	
	@Override  
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if(showProfilaButtonField()) {
			if(profilaButtonField == null) {
				profilaButtonField = new ControlListGridField("profilaButton");  
				profilaButtonField.setAttribute("custom", true);	
				profilaButtonField.setShowHover(true);	
				profilaButtonField.setCanReorder(false);		
				profilaButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isRecordAbilToProfile(record)) {
							return buildImgButtonHtml("lookup/attributiadd.png");		
						}
						return null;
					}
				});
				profilaButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isRecordAbilToProfile(record)) {
							return "Profila";
						}
						return null;
					}
				});		
				profilaButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if(isRecordAbilToProfile(record)) {
							manageProfilaButtonClick(record);
						}
					}
				});													
			}	
			buttonsFields.add(profilaButtonField);
		}
		if(showScaricaButtonField()) {
			if(scaricaFileButtonField == null) {
				scaricaFileButtonField = new ControlListGridField("scaricaFileButton");  
				scaricaFileButtonField.setAttribute("custom", true);	
				scaricaFileButtonField.setShowHover(true);	
				scaricaFileButtonField.setCanReorder(false);		
				scaricaFileButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isRecordAbilToDownload(record)) {
							return buildImgButtonHtml("file/download_manager.png");		
						}
						return null;
					}
				});
				scaricaFileButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isRecordAbilToDownload(record)) {
							return "Scarica file";
						}
						return null;
					}
				});		
				scaricaFileButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if(isRecordAbilToDownload(record)) {
							manageScaricaFileButtonClick(record);
						}
					}
				});													
			}	
			buttonsFields.add(scaricaFileButtonField);
		}
		return buttonsFields;		
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	@Override
	protected void modifyClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.editMode();		
					if (UserInterfaceFactory.isAttivaAccessibilita() && AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal")) {
						GenericWindow window = new GenericWindow(layout.getDetail(), nomeEntita, layout.getDetail().getLayout().getEditDetailTitle(), "menu/archivio.png");
						window.show();
					}
				}
			});
		}
	}
	
	@Override
	protected void detailClick(final Record record, final int recordNum) {
		if(layout != null) {
			layout.getDetail().markForRedraw();
			manageLoadDetail(record, recordNum, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					layout.viewMode();	
					if (UserInterfaceFactory.isAttivaAccessibilita() && AurigaLayout.getImpostazioniSceltaAccessibilitaAsBoolean("mostraModal")) {
						GenericWindow window = new GenericWindow(layout.getDetail(), nomeEntita, layout.getDetail().getLayout().getViewDetailTitle(), "menu/archivio.png");
						window.show();
					}
				}
			}); 
		}
	}
}
