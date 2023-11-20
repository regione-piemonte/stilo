/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTreeLayout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ContenutiAmmTraspLayout extends CustomSimpleTreeLayout {

	private String idNode;
	
	private Boolean showRifNormativiButton;
	private Boolean showHeaderButton;
	private Boolean showTitoloSezioneNewButton; 
	private Boolean showFineSezioneNewButton;
	private Boolean showParagrafoNewButton;
	private Boolean showDocumentoSempliceNewButton;
	private Boolean showDocumentoConAllegatiNewButton;
	private Boolean showTabellaNewButton;
	private Boolean showContenutiTabellaButton;
	
	
	private ToolStripButton ordinamentoButton;
	private ToolStripButton rifNormativiButton;
	private ToolStripButton headerButton;
	private ToolStripButton titoloSezioneNewButton;
	private ToolStripButton fineSezioneNewButton;
	private ToolStripButton paragrafoNewButton;
	private ToolStripButton documentoSempliceNewButton;
	private ToolStripButton documentoConAllegatiNewButton;
	private ToolStripButton tabellaNewButton;
	
	private ToolStripSeparator toolStripSeparator;
	private DateItem dataPubblStoricheDa;
	private DateItem dataPubblStoricheA;
	private TextItem cercaNelTitoloContenuto;
	private CheckboxItem includiContenutiEliminati;
	private ButtonItem cercaButtonItem;
	
	protected Record recordToOrd;
	
	public static final String TIPO_CONTENUTO_FINE_SEZIONE = "fine_sezione";
	public static final String TIPO_CONTENUTO_PARAGRAFO = "paragrafo";
	public static final String TIPO_CONTENUTO_FILE_SEMPLICE = "file_semplice";
	public static final String TIPO_CONTENUTO_DOCUMENTO_COMPLESSO = "documento_complesso";
	public static final String TIPO_CONTENUTO_TABELLA = "tabella";
	public static final String TIPO_CONTENUTO_TITOLO_SEZIONE = "titolo_sezione";
	public static final String TIPO_CONTENUTO_HEADER = "header";
	public static final String TIPO_CONTENUTO_RIF_NORMATIVI = "rif_norm";

	private String flgPresenteHeader;
	private String flgPresenteRifNormativi;	
	
	public Record cutNodeContenuto;
	
	public ContenutiAmmTraspLayout() {

		super("contenuti_amministrazione_trasparente",
				"contenuti_amministrazione_trasparente",
				new GWTRestDataSource("ContenutiAmmTraspTreeDatasource", true, "idNode", FieldType.TEXT), 
				new GWTRestDataSource("ContenutiAmmTraspDatasource", "idSezione", FieldType.TEXT), 
				new ContenutiAmmTraspTree("contenuti_amministrazione_trasparente"), 
				null,
				new ContenutiAmmTraspList("contenuti_amministrazione_trasparente"),
				new ContenutiAmmTraspDetail("contenuti_amministrazione_trasparente") 
		);
		
		multiselectButton.hide();	
		
		setAmmTraspTopDynamicFormStyle();
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
	}
	
	public static boolean isAbilToIns() {
		return false; //Layout.isPrivilegioAttivo("SIC/RA;I");
	}
	
	public static boolean isAbilToMod() {
		return true; //Layout.isPrivilegioAttivo("SIC/RA;M");
	}
	
	public static boolean isAbilToDel() {
		return true; //Layout.isPrivilegioAttivo("SIC/RA;FC");
	}	
	
	public static boolean isAbilToContenutiTabella() {
		return true;
	}
	
	public static boolean isRecordAbilToMod(boolean flgAbilModificaContenuto) {
		return flgAbilModificaContenuto && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgAbilEliminaContenuto) {
		return flgAttivo && flgAbilEliminaContenuto && isAbilToDel();
	}	
	
	public static boolean isRecordAbilToView(boolean flgAttivo, boolean flgLocked) {
		return flgAttivo && !flgLocked && isAbilToDel();
	}	

	public static boolean isRecordAbilToContenutiTabella(boolean showContenutiTabellaButton) {
		return showContenutiTabellaButton && isAbilToContenutiTabella();
	}

	
	@Override
	protected ToolStripButton[] getCustomBottomListButtons() {
		
		// Ordinamento lista
		if(ordinamentoButton == null) {			
			ordinamentoButton = new ToolStripButton("Salva ordinamento");   
			ordinamentoButton.setIcon("buttons/sorting.png");   
			ordinamentoButton.setIconSize(16);
			ordinamentoButton.setAutoFit(true);
			ordinamentoButton.setPrompt("Salva ordinamento");
			ordinamentoButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					
					Record recordToOrd = getRecordToOrd();
					final GWTRestDataSource gwtRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");					
					gwtRestDataSource.executecustom("ordinamentoSezioneContenuti", recordToOrd, new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean("Ordinamento salvato con successo", "", MessageType.INFO));
								reloadList();
							}
						}
					});
				}   
			}); 
		}
		
		return new ToolStripButton[]{ ordinamentoButton };
		 
	}
	
	@Override
	protected ToolStripButton[] getCustomTopListButtons() {			
		
		// Aggiunta/modifica rif. normativi
		if(rifNormativiButton == null) {
			rifNormativiButton = new ToolStripButton();   
			rifNormativiButton.setIcon("buttons/riferimentiNormativi.png");   
			rifNormativiButton.setIconSize(16); 
			rifNormativiButton.addHoverHandler(new HoverHandler() {
				
				@Override
				public void onHover(HoverEvent event) {
					rifNormativiButton.setPrompt(getRifNormativiButtonTitle());
				}
			});
			rifNormativiButton.setCanHover(true);
			rifNormativiButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageHeaderRifNormativiButtonClick(TIPO_CONTENUTO_RIF_NORMATIVI, getFlgPresenteRifNormativi());
				}   
			}); 
		}
		
		// Aggiunta/modifica header
		if(headerButton == null) {
			headerButton = new ToolStripButton();   
			headerButton.setIcon("buttons/header.png");   
			headerButton.setIconSize(16); 
			headerButton.addHoverHandler(new HoverHandler() {
				
				@Override
				public void onHover(HoverEvent event) {
					headerButton.setPrompt(getHeaderButtonTitle());
				}
			});
			headerButton.setCanHover(true);
			headerButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageHeaderRifNormativiButtonClick(TIPO_CONTENUTO_HEADER, getFlgPresenteHeader());
				}   
			}); 
		}

		// Aggiunta titolo sezione 
		if(titoloSezioneNewButton == null) {
			titoloSezioneNewButton = new ToolStripButton();   
			titoloSezioneNewButton.setIcon("buttons/titoloSezione.png");   
			titoloSezioneNewButton.setIconSize(16); 
			titoloSezioneNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_titoloSezioneMenuItem_title());
			titoloSezioneNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageSezioneClick(TIPO_CONTENUTO_TITOLO_SEZIONE, null, false, null, null);
				}   
			}); 
		}
		
		// Aggiunta fine sezione
		if(fineSezioneNewButton == null) {
			fineSezioneNewButton = new ToolStripButton();   
			fineSezioneNewButton.setIcon("buttons/fineSezione.png");   
			fineSezioneNewButton.setIconSize(16); 
			fineSezioneNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_fineSezioneMenuItem_title());
			fineSezioneNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageFineSezioneClick(null, null);
				}   
			}); 
		}

		// Aggiunta paragrafo	
		if(paragrafoNewButton == null) {
			paragrafoNewButton = new ToolStripButton();   
			paragrafoNewButton.setIcon("buttons/paragrafo.png");   
			paragrafoNewButton.setIconSize(16); 
			paragrafoNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_paragrafoMenuItem_title());
			paragrafoNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageSezioneClick(TIPO_CONTENUTO_PARAGRAFO, null, false, null, null);
				}   
			}); 
		}
		
		// Aggiunta documento semplice
		if(documentoSempliceNewButton == null) {
			documentoSempliceNewButton = new ToolStripButton();   
			documentoSempliceNewButton.setIcon("buttons/documentoSemplice.png");   
			documentoSempliceNewButton.setIconSize(16); 
			documentoSempliceNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoSempliceMenuItem_title());
			documentoSempliceNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageSezioneClick(TIPO_CONTENUTO_FILE_SEMPLICE, null, false, null, null);
				}   
			}); 
		}
		
		// Aggiunta documento con allegati
		if(documentoConAllegatiNewButton == null) {
			documentoConAllegatiNewButton = new ToolStripButton();   
			documentoConAllegatiNewButton.setIcon("buttons/documentoConAllegati.png");   
			documentoConAllegatiNewButton.setIconSize(16); 
			documentoConAllegatiNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_documentoConAllegatiMenuItem_title());
			documentoConAllegatiNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event) {
					manageSezioneClick(TIPO_CONTENUTO_DOCUMENTO_COMPLESSO, null, false, null, null);
				}   
			}); 
		}
		
		// Aggiunta tabella
		if(tabellaNewButton == null) {
			tabellaNewButton = new ToolStripButton();   
			tabellaNewButton.setIcon("buttons/tabella.png");   
			tabellaNewButton.setIconSize(16); 
			tabellaNewButton.setPrompt(I18NUtil.getMessages().contenuti_amministrazione_trasparente_tabellaMenuItem_title());
			tabellaNewButton.addClickHandler(new ClickHandler() {	
				
				@Override
				public void onClick(ClickEvent event){
					manageSezioneClick(TIPO_CONTENUTO_TABELLA, null, false, null, null);
				}   
			}); 
		}
		
		return new ToolStripButton[]{ rifNormativiButton, headerButton, titoloSezioneNewButton, fineSezioneNewButton, paragrafoNewButton, documentoSempliceNewButton, documentoConAllegatiNewButton, tabellaNewButton };
	}
	
	@Override
	protected FormItem[] getCustomTopListFormItems() {
		 
		if(dataPubblStoricheDa == null) {
			dataPubblStoricheDa = new DateItem("dataPubblStoricheDa", "Pubblicazioni storiche dal");
			dataPubblStoricheDa.setWrapTitle(false);
		}
		
		if(dataPubblStoricheA == null) {
			dataPubblStoricheA = new DateItem("dataPubblStoricheA", "al");   			
		}
		
		if(cercaNelTitoloContenuto == null) {
			cercaNelTitoloContenuto = new TextItem("cercaNelTitoloContenuto", "Cerca nel titolo/contenuto");
			cercaNelTitoloContenuto.setWidth(200);
		}
		
		if(includiContenutiEliminati == null) {
			includiContenutiEliminati = new CheckboxItem("includiContenutiEliminati", "includi contenuti eliminati");
			includiContenutiEliminati.setWidth(200);
		}
		
		if(cercaButtonItem == null) {
			cercaButtonItem = new ButtonItem(I18NUtil.getMessages().searchButton_title());
			cercaButtonItem.setIcon("search.png");
			cercaButtonItem.setStartRow(false);
			cercaButtonItem.setTabIndex(4);
			cercaButtonItem.setAutoFit(false);		
			cercaButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

				@Override
				public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					doSearch();
				}
			});
		}
		
		if (toolStripSeparator == null) {
			toolStripSeparator = new ToolStripSeparator();
			topListToolStrip.addMember(toolStripSeparator);
		}

		
		return new FormItem[] { dataPubblStoricheDa, dataPubblStoricheA, cercaNelTitoloContenuto, includiContenutiEliminati, cercaButtonItem};
	}
	
	public void refreshCustomTopListButtons() {
		
		if (isShowRifNormativiButton()) {
			if(rifNormativiButton != null) {
				rifNormativiButton.show();
			}
		} else {	
			if(rifNormativiButton != null) {
				rifNormativiButton.hide();
			}
		}
		
		if (isShowHeaderButton()) {
			if(headerButton != null) {
				headerButton.show();
			}
		} else {	
			if(headerButton != null) {
				headerButton.hide();
			}
		}
		
		if (isShowTitoloSezioneNewButton()) {
			if(titoloSezioneNewButton != null) {
				titoloSezioneNewButton.show();
			}
		} else {	
			if(titoloSezioneNewButton != null) {
				titoloSezioneNewButton.hide();
			}
		}
		
		if (isShowFineSezioneNewButton()) {
			if(fineSezioneNewButton != null) {
				fineSezioneNewButton.show();
			}
		} else {	
			if(fineSezioneNewButton != null) {
				fineSezioneNewButton.hide();
			}
		}
		
		if (isShowParagrafoNewButton()) {
			if(paragrafoNewButton != null) {
				paragrafoNewButton.show();
			}
		} else {	
			if(paragrafoNewButton != null) {
				paragrafoNewButton.hide();
			}
		}
		
		if (isShowDocumentoSempliceNewButton()) {
			if(documentoSempliceNewButton != null) {
				documentoSempliceNewButton.show();
			}			
		} else {	
			if(documentoSempliceNewButton != null) {
				documentoSempliceNewButton.hide();
			}
		}
				
		if (isShowDocumentoConAllegatiNewButton()) {
			if(documentoConAllegatiNewButton != null) {
				documentoConAllegatiNewButton.show();
			}
		} else {	
			if(documentoConAllegatiNewButton != null) {
				documentoConAllegatiNewButton.hide();
			}
		}
		
		if (isShowTabellaNewButton()) {
			if(tabellaNewButton != null) {
				tabellaNewButton.show();
			}
		} else {
			if(tabellaNewButton != null) {
				tabellaNewButton.hide();
			}
		}
		topListToolStrip.markForRedraw();
	}
	
	public void refreshCustomBottomListButtons() {
		
		if (isShowRifNormativiButton() ||
		    isShowHeaderButton() ||
		    isShowTitoloSezioneNewButton() ||
		    isShowFineSezioneNewButton() ||
		    isShowParagrafoNewButton() ||
		    isShowDocumentoSempliceNewButton() ||
		    isShowDocumentoConAllegatiNewButton() ||
		    isShowTabellaNewButton()){
			if(ordinamentoButton != null) {
				ordinamentoButton.show();
			}
		} else {
			if(ordinamentoButton != null) {
				ordinamentoButton.hide();
			}
		}
			
		bottomListToolStrip.markForRedraw();
	}
	
	public void manageFineSezioneClick(final Integer numOrdine, final ListGridRecord listRecord) {
		String idSezione = idNode;
		Record record = new Record();
		record.setAttribute("idSezione", idSezione);
		record.setAttribute("nroOrdineInSez", numOrdine);
		record.setAttribute("tipoContenuto", TIPO_CONTENUTO_FINE_SEZIONE);
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lDataSource.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Fine lista contenuti aggiunta con successo", "", MessageType.INFO));
					reloadList();
				}
			}
		});
	}

	public void manageHeaderRifNormativiButtonClick(String tipoContenuto,String flgPresente){
		
		// Se ci sono i riferimenti normativi/header
		if (flgPresente.equalsIgnoreCase("1")){
			
			// Carico i dati
			String idSezione = idNode;
			Record record = new Record();
			record.setAttribute("idSezione", idSezione);
			record.setAttribute("tipoContenuto", tipoContenuto);
			
			final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
			lDataSource.getData(record, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordDettaglio = response.getData()[0];
						String tipoContenuto = recordDettaglio.getAttribute("tipoContenuto");
						Integer numOrdine = recordDettaglio.getAttributeAsInt("nroOrdineInSez");
						manageSezioneClick(tipoContenuto, numOrdine, true, recordDettaglio, null);
					}
				}
			});			
		}
		// Altrimento apro la popup in new
		else{
			manageSezioneClick(tipoContenuto , null, false, null, null);
		}		
	}
	
	public void manageSezioneClick(final String tipoContenuto, Integer numOrdine, final boolean modify, final Record recordDettaglio, final ListGridRecord listRecord){
		String idSezione = idNode;		
		
		boolean flgAbil = isShowRifNormativiButton() ||
				          isShowHeaderButton() ||
            			  isShowTitoloSezioneNewButton() ||
				          isShowFineSezioneNewButton() ||
				          isShowParagrafoNewButton() ||
				          isShowDocumentoSempliceNewButton() ||
				          isShowDocumentoConAllegatiNewButton() ||
				          isShowTabellaNewButton();
		
		SezionePopup sezionePopUp = new SezionePopup(instance, numOrdine, idSezione, flgAbil, tipoContenuto, modify, recordDettaglio) {
			
			@Override
			protected void afterInserimento() {
				if(tipoContenuto != null) {
					if(tipoContenuto.equals(TIPO_CONTENUTO_HEADER)) {
						setFlgPresenteHeader("1");
					} else if(tipoContenuto.equals(TIPO_CONTENUTO_RIF_NORMATIVI)) {
						setFlgPresenteRifNormativi("1");
					}
				}
			}
		};		
		if(modify) {
			sezionePopUp.editRecordModificaContenuto(recordDettaglio);
			sezionePopUp.setEditMode();
		} else {			
			if(recordDettaglio!=null) {
				sezionePopUp.editRecordModificaContenuto(recordDettaglio);
				sezionePopUp.setViewMode();
			} else {
				sezionePopUp.editRecordNuovoContenuto(tipoContenuto, idSezione, numOrdine);
				sezionePopUp.setEditMode();
			}
		}
		sezionePopUp.show();
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_new_title();	
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_edit_title(getTipoEstremiRecord(record));	
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().contenuti_amministrazione_trasparente_detail_view_title(getTipoEstremiRecord(record));	
	}
		
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		
		super.viewMode();		
		Record record = new Record(detail.getValuesManager().getValues());
				
		final boolean flgAbilEliminaContenuto   = record.getAttribute("flgAbilEliminaContenuto")  != null  && record.getAttributeAsBoolean("flgAbilEliminaContenuto");
		final boolean flgAbilModificaContenuto  = record.getAttribute("flgAbilModificaContenuto") != null  && record.getAttributeAsBoolean("flgAbilModificaContenuto");
		
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		
		if(isRecordAbilToDel(flgValido, flgAbilEliminaContenuto)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(flgAbilModificaContenuto)) {
			editButton.show();
		} else{
			editButton.hide();
		}		
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();
		return newRecord;
	}
	
	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		
		List<Criterion> criterionList = new ArrayList<Criterion>();
		
		if (idNode != null && !"".equals(idNode)) {
			criterionList.add(new Criterion("idNode", OperatorId.EQUALS, idNode));
		}
		if (dataPubblStoricheDa != null && dataPubblStoricheDa.getValueAsDate() != null) {
			criterionList.add(new Criterion("dataPubblStoricheDa", OperatorId.EQUALS, dataPubblStoricheDa.getValueAsDate()));
		}
		if (dataPubblStoricheA != null && dataPubblStoricheA.getValueAsDate() != null) {
			criterionList.add(new Criterion("dataPubblStoricheA", OperatorId.EQUALS, dataPubblStoricheA.getValueAsDate()));
		}
		if (cercaNelTitoloContenuto != null && cercaNelTitoloContenuto.getValueAsString() != null) {
			criterionList.add(new Criterion("cercaNelTitoloContenuto", OperatorId.EQUALS, cercaNelTitoloContenuto.getValueAsString()));
		}
		if (includiContenutiEliminati != null && includiContenutiEliminati.getValueAsBoolean() != null) {
			criterionList.add(new Criterion("includiContenutiEliminati", OperatorId.EQUALS, includiContenutiEliminati.getValueAsBoolean()));
		}
		
		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	
	private String getHeaderButtonTitle(){
		String title = "";
		
		if (getFlgPresenteHeader() !=null && getFlgPresenteHeader().equalsIgnoreCase("1")){
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_headerUpdButtonItem_title();
		}
		else{
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_headerNewButtonItem_title();
		}
		return title;
	}
	
	private String getRifNormativiButtonTitle(){
		String title = "";
		if (getFlgPresenteRifNormativi() !=null && getFlgPresenteRifNormativi().equalsIgnoreCase("1")){
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_rifNormativiUpdButtonItem_title();
		}
		else{
			title = I18NUtil.getMessages().contenuti_amministrazione_trasparente_rifNormativiNewButtonItem_title();
		}
		return title;
	}
	
	// Faccio Incolla del contenuto sezione
	public void paste(final Record currentRecord,final Boolean isFromList) {
		
		Record record = new Record();
		record.setAttribute("idSezione",  idNode);                                                 // Id sezione ricevente
		record.setAttribute("idContenuto", getCutNodeContenuto().getAttribute("idContenuto"));     // Id contenuto di provenienza		
		final GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lDataSource.executecustom("spostaContenutoSez", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					setCutNodeContenuto(null);
					Layout.addMessage(new MessageBean("Spostamento contenuto effettuato con successo", "", MessageType.INFO));
					reloadList();
				}
			}
		});
	}
	
	public void setAmmTraspTopDynamicFormStyle() {
		if(topListToolStrip != null) {
			topListToolStrip.setHeight(55);
			topListToolStrip.setOverflow(Overflow.AUTO);
			Canvas[] members = topListToolStrip.getMembers();
			for (Canvas canvas : members) {
				if(canvas instanceof DynamicForm) {
					canvas.setStyleName("amm-trasp-top-dynamic-form");
				}
			}
		}
	}
	
	public String getIdNode() {
		return idNode;
	}

	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	
	public Record getRecordToOrd() {
		return recordToOrd;
	}

	public void setRecordToOrd(Record recordToOrd) {
		this.recordToOrd = recordToOrd;
	}

	public String getFlgPresenteHeader() {
		return flgPresenteHeader;
	}

	public void setFlgPresenteHeader(String flgPresenteHeader) {
		this.flgPresenteHeader = flgPresenteHeader;
	}

	public String getFlgPresenteRifNormativi() {
		return flgPresenteRifNormativi;
	}

	public void setFlgPresenteRifNormativi(String flgPresenteRifNormativi) {
		this.flgPresenteRifNormativi = flgPresenteRifNormativi;
	}


	public Boolean isShowRifNormativiButton() {
		return showRifNormativiButton;
	}

	public void setShowRifNormativiButton(Boolean showRifNormativiButton) {
		this.showRifNormativiButton = showRifNormativiButton;
	}

	public Boolean isShowHeaderButton() {
		return showHeaderButton;
	}

	public void setShowHeaderButton(Boolean showHeaderButton) {
		this.showHeaderButton = showHeaderButton;
	}

	public Boolean isShowTitoloSezioneNewButton() {
		return showTitoloSezioneNewButton;
	}

	public void setShowTitoloSezioneNewButton(Boolean showTitoloSezioneNewButton) {
		this.showTitoloSezioneNewButton = showTitoloSezioneNewButton;
	}

	public Boolean isShowFineSezioneNewButton() {
		return showFineSezioneNewButton;
	}

	public void setShowFineSezioneNewButton(Boolean showFineSezioneNewButton) {
		this.showFineSezioneNewButton = showFineSezioneNewButton;
	}

	public Boolean isShowParagrafoNewButton() {
		return showParagrafoNewButton;
	}

	public void setShowParagrafoNewButton(Boolean showParagrafoNewButton) {
		this.showParagrafoNewButton = showParagrafoNewButton;
	}

	public Boolean isShowDocumentoSempliceNewButton() {
		return showDocumentoSempliceNewButton;
	}

	public void setShowDocumentoSempliceNewButton(Boolean showDocumentoSempliceNewButton) {
		this.showDocumentoSempliceNewButton = showDocumentoSempliceNewButton;
	}

	public Boolean isShowDocumentoConAllegatiNewButton() {
		return showDocumentoConAllegatiNewButton;
	}

	public void setShowDocumentoConAllegatiNewButton(Boolean showDocumentoConAllegatiNewButton) {
		this.showDocumentoConAllegatiNewButton = showDocumentoConAllegatiNewButton;
	}

	public Boolean isShowTabellaNewButton() {
		return showTabellaNewButton;
	}

	public void setShowTabellaNewButton(Boolean showTabellaNewButton) {
		this.showTabellaNewButton = showTabellaNewButton;
	}

	public Boolean isShowContenutiTabellaButton() {
		return showContenutiTabellaButton;
	}

	public void setShowContenutiTabellaButton(Boolean showContenutiTabellaButton) {
		this.showContenutiTabellaButton = showContenutiTabellaButton;
	}

	public Record getCutNodeContenuto() {
		return cutNodeContenuto;
	}

	public void setCutNodeContenuto(Record cutNodeContenuto) {
		this.cutNodeContenuto = cutNodeContenuto;
	}
}