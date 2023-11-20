/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.FolderProcedimentoPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.ProcedimentiCollegatiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * 
 * @author dbe4235
 *
 */

public class RichiesteAutotuteleCedInIterList extends CustomList {

	private ListGridField iconaMessaggioUltimoTaskField;
	private ListGridField idUdFolderField;
	private ListGridField idProcedimentoField;	
	private ListGridField estremiProcedimentoField;
	private ListGridField nrRichiestaAutotutelaField;
	private ListGridField tipoProcedimentoField;
	private ListGridField oggettoProcedimentoField;
	private ListGridField dataAvvioProcedimentoField;
	private ListGridField decorrenzaProcedimentoField;
	private ListGridField processoPadreProcedimentoField;
	private ListGridField statoProcedimentoField;
	private ListGridField documentoInizialeProcedimentoField;
	private ListGridField attoParereFinaleProcedimentoField;
	private ListGridField inFaseProcedimentoField;
	private ListGridField scadenzaTermineProcedimentoField;
	private ListGridField flgScadenzaTermineProcedimentoField;
	private ListGridField ultimoTaskProcedimentoField;
	private ListGridField messaggioUltimoTaskProcedimentoField;
	private ListGridField noteProcedimentoField;
	private ListGridField denominazioneRichiedenteField;
	private ListGridField codFiscalePivaRichiedenteField;
	private ListGridField assegnatarioProcedimentoField;
	private ListGridField prossimeAttivitaField;
	private ListGridField avviatoDaField;
	private ListGridField dataPresentazioneProcedimentoField;	
	private ListGridField tipiTributoField;
	private ListGridField anniTributoField;
	private ListGridField attiRiferimentoField;
	private ListGridField flgPresenzaProcedimentiField;
	private ListGridField idUdRispostaCedAutotuteleField;
	private ListGridField iDocRispostaCedAutotuteleField;
	private ListGridField uriRispostaCedAutotuteleField;
	private ListGridField displayFilenameRispostaCedAutotuteleField;
	private ListGridField idDocTypeRispostaCedAutotuteleField;
	private ListGridField mimetypeRispostaCedAutotuteleField;
	private ListGridField firmatoRispostaCedAutotuteleField;
	private ListGridField convertibileRispostaCedAutotuteleField;
	private ListGridField previstaNumerazioneField;
	
	protected ControlListGridField folderButtonField;
	protected ControlListGridField folderDetailButtonField;	
	protected ControlListGridField visualizzaRispostaCedAutotuteleButtonField;
	
	public RichiesteAutotuteleCedInIterList(String nomeEntita) {
		super(nomeEntita);
		
		// campi nascosti
		idUdFolderField = new ListGridField("idUdFolder");
		idUdFolderField.setHidden(true);
		idUdFolderField.setCanHide(false);
		
		idProcedimentoField = new ListGridField("idProcedimento");
		idProcedimentoField.setHidden(true);
		idProcedimentoField.setCanHide(false);
		
		decorrenzaProcedimentoField = new ListGridField("decorrenzaProcedimento");
		decorrenzaProcedimentoField.setHidden(true);
		decorrenzaProcedimentoField.setCanHide(false);
		
		processoPadreProcedimentoField = new ListGridField("processoPadreProcedimento");
		processoPadreProcedimentoField.setHidden(true);
		processoPadreProcedimentoField.setCanHide(false);
		
		estremiProcedimentoField = new ListGridField("estremiProcedimento");
		estremiProcedimentoField.setHidden(true);
		estremiProcedimentoField.setCanHide(false);
		
		nrRichiestaAutotutelaField = new ListGridField("nrRichiestaAutotutela", "N°");
		
		idUdRispostaCedAutotuteleField = new ListGridField("idUdRispostaCedAutotutele");
		idUdRispostaCedAutotuteleField.setHidden(true);
		idUdRispostaCedAutotuteleField.setCanHide(false);
		
		iDocRispostaCedAutotuteleField = new ListGridField("iDocRispostaCedAutotutele");
		iDocRispostaCedAutotuteleField.setHidden(true);
		iDocRispostaCedAutotuteleField.setCanHide(false);
		
		uriRispostaCedAutotuteleField = new ListGridField("uriRispostaCedAutotutele");
		uriRispostaCedAutotuteleField.setHidden(true);
		uriRispostaCedAutotuteleField.setCanHide(false);
		
		displayFilenameRispostaCedAutotuteleField = new ListGridField("displayFilenameRispostaCedAutotuteleField");
		displayFilenameRispostaCedAutotuteleField.setHidden(true);
		displayFilenameRispostaCedAutotuteleField.setCanHide(false);
		
		idDocTypeRispostaCedAutotuteleField = new ListGridField("idDocTypeRispostaCedAutotutele");
		idDocTypeRispostaCedAutotuteleField.setHidden(true);
		idDocTypeRispostaCedAutotuteleField.setCanHide(false);
		
		mimetypeRispostaCedAutotuteleField = new ListGridField("mimetypeRispostaCedAutotutele");
		mimetypeRispostaCedAutotuteleField.setHidden(true);
		mimetypeRispostaCedAutotuteleField.setCanHide(false);
		
		firmatoRispostaCedAutotuteleField = new ListGridField("firmatoRispostaCedAutotutele");
		firmatoRispostaCedAutotuteleField.setHidden(true);
		firmatoRispostaCedAutotuteleField.setCanHide(false);
		
		convertibileRispostaCedAutotuteleField = new ListGridField("convertibileRispostaCedAutotutele");
		convertibileRispostaCedAutotuteleField.setHidden(true);
		convertibileRispostaCedAutotuteleField.setCanHide(false);
		
		previstaNumerazioneField = new ListGridField("flgPrevistaNumerazione");
		previstaNumerazioneField.setHidden(true);
		previstaNumerazioneField.setCanHide(false);

		// campi visibili
		iconaMessaggioUltimoTaskField = new ControlListGridField("iconaMessaggioUltimoTask");
		iconaMessaggioUltimoTaskField.setAttribute("custom", true);			
		iconaMessaggioUltimoTaskField.setShowHover(true);			
		iconaMessaggioUltimoTaskField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				String messaggioUltimoTask = (String) record.getAttribute("ultimaAttivitaMessaggio");
				if(messaggioUltimoTask != null && !"".equals(messaggioUltimoTask)) {
					return buildImgButtonHtml("buttons/doc.png");												
				} 				
				return null;					
			}
		});
		iconaMessaggioUltimoTaskField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String messaggioUltimoTask = (String) record.getAttribute("ultimaAttivitaMessaggio");
				if(messaggioUltimoTask != null && !"".equals(messaggioUltimoTask)) {
					return "Apri il messaggio ultimo task";
				}
				return null;				
			}
		});	
		iconaMessaggioUltimoTaskField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				final Record record = event.getRecord();
				String messaggioUltimoTask = (String) record.getAttribute("ultimaAttivitaMessaggio");
				if(messaggioUltimoTask != null && !"".equals(messaggioUltimoTask)) {
					ultimaAttivitaMessaggioButtonClick(record);
				}				
			}
		});			

		tipoProcedimentoField = new ListGridField("tipoProcedimento", "Tipo");

		oggettoProcedimentoField = new ListGridField("oggettoProcedimento", "Oggetto");
		oggettoProcedimentoField.setAttribute("custom", true);
		oggettoProcedimentoField.setCellAlign(Alignment.LEFT);
		oggettoProcedimentoField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String oggettoProcedimento = (String) record.getAttribute("oggettoProcedimento");
				if (oggettoProcedimento != null && !"".equals(oggettoProcedimento)){
					oggettoProcedimento = oggettoProcedimento.replaceAll("\n", " ").trim();
				}
				if (oggettoProcedimento == null) return null;
				if (oggettoProcedimento.length()>Layout.getGenericConfig().getMaxValueLength()){
					return oggettoProcedimento.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
				} else return oggettoProcedimento;				
			}
		});
		oggettoProcedimentoField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return (String) record.getAttribute("oggettoProcedimento");
			}
		});	
		
		dataAvvioProcedimentoField = new ListGridField("dataAvvioProcedimento", "Avviato il");
		dataAvvioProcedimentoField.setDisplayField("dataAvvioProcedimento");
		dataAvvioProcedimentoField.setSortByDisplayField(false);
		dataAvvioProcedimentoField.setType(ListGridFieldType.DATE); 
		dataAvvioProcedimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAvvioProcedimentoField.setWrap(false);
		dataAvvioProcedimentoField.setWidth(100);
		dataAvvioProcedimentoField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("dataAvvioProcedimento");
			}
		});
		
		statoProcedimentoField = new ListGridField("statoProcedimento", "Stato");
		
		documentoInizialeProcedimentoField = new ListGridField("documentoInizialeProcedimento", "Doc.iniziale");
		
		attoParereFinaleProcedimentoField = new ListGridField("attoParereFinaleProcedimento", "Atto parere finale");
		
		inFaseProcedimentoField = new ListGridField("inFaseProcedimento", "In fase");

		scadenzaTermineProcedimentoField = new ListGridField("scadenzaTermineProcedimento", "Scadenza termini");
		scadenzaTermineProcedimentoField.setType(ListGridFieldType.DATE);
		scadenzaTermineProcedimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		flgScadenzaTermineProcedimentoField = new ListGridField("flgScadenzaTermineProcedimento", "In scadenza/scaduto");
		flgScadenzaTermineProcedimentoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgScadenzaTermineProcedimentoField.setType(ListGridFieldType.ICON);
		flgScadenzaTermineProcedimentoField.setWidth(30);
		flgScadenzaTermineProcedimentoField.setIconWidth(16);
		flgScadenzaTermineProcedimentoField.setIconHeight(16);
		flgScadenzaTermineProcedimentoField.setAlign(Alignment.CENTER);
		Map<String, String> flgScadenzaTermineProcedimentoValueIcons = new HashMap<String, String>();		
		flgScadenzaTermineProcedimentoValueIcons.put("1", "prioritaAlta.png");
		flgScadenzaTermineProcedimentoValueIcons.put("0", "blank.png");
		flgScadenzaTermineProcedimentoValueIcons.put("",  "blank.png");
		flgScadenzaTermineProcedimentoField.setValueIcons(flgScadenzaTermineProcedimentoValueIcons);
		flgScadenzaTermineProcedimentoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgScadenzaTermineProcedimento"))) {
					return I18NUtil.getMessages().procedimenti_personali_flgScadenzaTermineProcedimento_1_value();
				}				
				return null;
			}
		});
		
		ultimoTaskProcedimentoField = new ListGridField("ultimoTaskProcedimento", "Ultima attività");
		
		messaggioUltimoTaskProcedimentoField = new ListGridField("messaggioUltimoTaskProcedimento", "Messaggio ultima attività");
		
		noteProcedimentoField = new ListGridField("noteProcedimento", "Note");
		
		denominazioneRichiedenteField = new ListGridField("denominazioneRichiedente", "Contribuente");
		
		codFiscalePivaRichiedenteField = new ListGridField("codFiscalePivaRichiedente", "Contribuente C.F. / P.IVA");
		
		assegnatarioProcedimentoField = new ListGridField("assegnatarioProcedimento", "Assegnatario");
		
		prossimeAttivitaField = new ListGridField("prossimeAttivita", "Prossime attività");
		
		avviatoDaField = new ListGridField("avviatoDa", "Avviato da");
		
		dataPresentazioneProcedimentoField = new ListGridField("dataPresentazione", "Data presentazione");
		dataPresentazioneProcedimentoField.setType(ListGridFieldType.DATE);
		dataPresentazioneProcedimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		tipiTributoField = new ListGridField("tipiTributo", "Tipo/i tributo");
		
		anniTributoField = new ListGridField("anniTributo", "Anno/i tributo");
		
		attiRiferimentoField = new ListGridField("attiRiferimento", "Atti di riferimento");
		
		flgPresenzaProcedimentiField = new ListGridField("flgPresenzaProcedimenti", "Presenza procedimenti");
		flgPresenzaProcedimentiField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPresenzaProcedimentiField.setType(ListGridFieldType.ICON);
		flgPresenzaProcedimentiField.setWidth(30);
		flgPresenzaProcedimentiField.setIconWidth(16);
		flgPresenzaProcedimentiField.setIconHeight(16);
		flgPresenzaProcedimentiField.setAlign(Alignment.CENTER);
		flgPresenzaProcedimentiField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgPresenzaProcedimenti = record.getAttribute("flgPresenzaProcedimenti");
				if (flgPresenzaProcedimenti != null && "1".equals(flgPresenzaProcedimenti)) {
					return buildImgButtonHtml("buttons/link.png");
				}
				return null;
			}
		});
		flgPresenzaProcedimentiField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if(record.getAttribute("flgPresenzaProcedimenti") != null &&
						"1".equals(record.getAttribute("flgPresenzaProcedimenti"))) {
					return "Presenza procedimenti dello stesso contribuente";
				}				
				return null;
			}
		});
		flgPresenzaProcedimentiField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				if(event.getRecord() != null && event.getRecord().getAttribute("flgPresenzaProcedimenti") != null && 
				   "1".equals(event.getRecord().getAttribute("flgPresenzaProcedimenti"))) {
							
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUdFolder", event.getRecord().getAttribute("idUdFolder"));
					GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
					lArchivioDatasource.getData(lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record lRecord = response.getData()[0];
								if(lRecord.getAttributeAsRecordList("listaProcCollegati") != null &&
								   lRecord.getAttributeAsRecordList("listaProcCollegati").getLength() > 0) {
									
									RecordList listaProcessi = new RecordList();
									for(int i=0; i < lRecord.getAttributeAsRecordList("listaProcCollegati").getLength(); i++) {
										Record item = lRecord.getAttributeAsRecordList("listaProcCollegati").get(i);
										Record processo = new Record();
										processo.setAttribute("idProcess", item.getAttribute("idProcess"));
										processo.setAttribute("descrizione", item.getAttribute("descrizione"));
										
										listaProcessi.add(processo);
									}
									
									ProcedimentiCollegatiPopup procedimentiCollegatiPopup = new ProcedimentiCollegatiPopup("procedimenti_collegati", listaProcessi) {
										
										@Override
										public void manageOnCloseClick() {
											super.manageOnCloseClick();
											layout.doSearch();
										}
									};
									procedimentiCollegatiPopup.show();
								}
							}
						}
					});
				}
			}
		});

		// Setto le dimensioni
		nrRichiestaAutotutelaField.setWidth(100);
		tipoProcedimentoField.setWidth(100);
		oggettoProcedimentoField.setWidth(200);
		dataAvvioProcedimentoField.setWidth(100);
		statoProcedimentoField.setWidth(100);
		documentoInizialeProcedimentoField.setWidth(100);
		attoParereFinaleProcedimentoField.setWidth(100);
		inFaseProcedimentoField.setWidth(100);
		scadenzaTermineProcedimentoField.setWidth(100);
		flgScadenzaTermineProcedimentoField.setWidth(30);		
		ultimoTaskProcedimentoField.setWidth(100);
		messaggioUltimoTaskProcedimentoField.setWidth(100);
		iconaMessaggioUltimoTaskField.setWidth(40);
		noteProcedimentoField.setWidth(100);
		denominazioneRichiedenteField.setWidth(100);
		codFiscalePivaRichiedenteField.setWidth(100);
		assegnatarioProcedimentoField.setWidth(100);
		prossimeAttivitaField.setWidth(100);
		avviatoDaField.setWidth(100);
		dataPresentazioneProcedimentoField.setWidth(100);
		tipiTributoField.setWidth(100);
		anniTributoField.setWidth(100);
		attiRiferimentoField.setWidth(200);
		flgPresenzaProcedimentiField.setWidth(30);
	
		// Setto la visibilita'
		nrRichiestaAutotutelaField.setHidden(true);
		tipoProcedimentoField.setHidden(true);		
		documentoInizialeProcedimentoField.setHidden(true);
		attoParereFinaleProcedimentoField.setHidden(true);
		inFaseProcedimentoField.setHidden(true);
		noteProcedimentoField.setHidden(true);
		denominazioneRichiedenteField.setHidden(true);
		codFiscalePivaRichiedenteField.setHidden(true);
		assegnatarioProcedimentoField.setHidden(true);
		prossimeAttivitaField.setHidden(true);
		avviatoDaField.setHidden(true);
		dataPresentazioneProcedimentoField.setHidden(true);
		
		setFields(new ListGridField[] {
			idProcedimentoField, 
			idUdFolderField,
			estremiProcedimentoField,
			nrRichiestaAutotutelaField,
			tipoProcedimentoField,
			oggettoProcedimentoField,
			dataAvvioProcedimentoField,
			decorrenzaProcedimentoField,
			processoPadreProcedimentoField,
			statoProcedimentoField,
			documentoInizialeProcedimentoField,
			attoParereFinaleProcedimentoField,
			inFaseProcedimentoField,
			scadenzaTermineProcedimentoField,
			flgScadenzaTermineProcedimentoField,
			ultimoTaskProcedimentoField,
			messaggioUltimoTaskProcedimentoField,
			iconaMessaggioUltimoTaskField,
			noteProcedimentoField,
			denominazioneRichiedenteField,
			codFiscalePivaRichiedenteField,
			assegnatarioProcedimentoField,
			prossimeAttivitaField,
			avviatoDaField,
			dataPresentazioneProcedimentoField,
			tipiTributoField,
			anniTributoField,
			attiRiferimentoField,
			flgPresenzaProcedimentiField,
			idUdRispostaCedAutotuteleField,
		    iDocRispostaCedAutotuteleField,
			uriRispostaCedAutotuteleField,
			displayFilenameRispostaCedAutotuteleField,
			idDocTypeRispostaCedAutotuteleField,
			mimetypeRispostaCedAutotuteleField,
			firmatoRispostaCedAutotuteleField,
			convertibileRispostaCedAutotuteleField,
			previstaNumerazioneField
		});
	}

	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {   			
		
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	
											
			// bottone APRI FOLDER
			ImgButton folderButton = new ImgButton();   
			folderButton.setShowDown(false);   
			folderButton.setShowRollOver(false);   
			folderButton.setLayoutAlign(Alignment.CENTER);   
			folderButton.setSrc("archivio/flgUdFolder/F.png");
			folderButton.setPrompt(I18NUtil.getMessages().procedimenti_personali_iconaFolderButton_prompt());
			folderButton.setSize(16);  
			folderButton.addClickHandler(new ClickHandler() {   
				public void onClick(ClickEvent event) {      
					folderButtonClick(record);	
				}  
			});
			
			// bottone DETTAGLIO FOLDER
			ImgButton folderDetailButton = new ImgButton();   
			folderDetailButton.setShowDown(false);   
			folderDetailButton.setShowRollOver(false);   
			folderDetailButton.setLayoutAlign(Alignment.CENTER);   
			folderDetailButton.setSrc("buttons/detail.png");
			folderDetailButton.setPrompt(I18NUtil.getMessages().procedimenti_personali_iconaFolderDetailButton_prompt());
			folderDetailButton.setSize(16);  
			folderDetailButton.addClickHandler(new ClickHandler() {   
				public void onClick(ClickEvent event) {      
					folderDetailButtonClick(record);
				}  
			});
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(5);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(folderButton);
			recordCanvas.addMember(folderDetailButton);
						
			lCanvasReturn = recordCanvas;
		}	
		return lCanvasReturn;
	}
	
	protected void ultimaAttivitaMessaggioButtonClick(Record pRecord) {
		String messaggio = pRecord.getAttribute("ultimaAttivitaMessaggio");
		SC.say(messaggio);		
	}

	protected void folderButtonClick(Record record) {
		String idUdFolder = record.getAttribute("idUdFolder");		
		if(idUdFolder!=null && !idUdFolder.equalsIgnoreCase("")){
			FolderProcedimentoPopup folderProcedimentoPopup = new FolderProcedimentoPopup(record);
			folderProcedimentoPopup.show();
		}
	}
	
	protected void folderDetailButtonClick(final Record record) {
		AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), record.getAttribute("estremiProcedimento"), new BooleanCallback() {

			@Override
			public void execute(Boolean isSaved) {
				if (getLayout() != null) {
					if(isSaved != null && isSaved) {
						getLayout().reloadListAndSetCurrentRecord(record);
					}
				}
			}
		});
	}
	
	protected void visualizzaRispostaCedAutotuteleButtonClick(final Record record) {

		String displayFilenameRispostaCedAutotutele = record.getAttribute("displayFilenameRispostaCedAutotutele");
		String mimetypeRispostaCedAutotutele = record.getAttribute("mimetypeRispostaCedAutotutele");
		boolean firmatoRispostaCedAutotutele = record.getAttributeAsBoolean("firmatoRispostaCedAutotutele");
		boolean convertibileRispostaCedAutotutele = record.getAttributeAsBoolean("convertibileRispostaCedAutotutele");
		Record infoFile = new Record();
		infoFile.setAttribute("correctFileName", displayFilenameRispostaCedAutotutele);
		infoFile.setAttribute("mimetype", mimetypeRispostaCedAutotutele);
		infoFile.setAttribute("firmato", firmatoRispostaCedAutotutele);
		infoFile.setAttribute("convertibile", convertibileRispostaCedAutotutele);
		infoFile.setAttribute("bytes", 0);
		
		Menu visualizzaRispostaCedAutotuteleMenu = new Menu();
		
		MenuItem previewFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(),
				"file/preview.png");
		previewFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				previewRispostaCedAutotutele(record);
			}
		});
		previewFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(new InfoFileRecord(infoFile)));
		visualizzaRispostaCedAutotuteleMenu.addItem(previewFileAllegatoMenuItem);
		
		MenuItem downloadFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadMenuItem_prompt(),
				"file/download_manager.png");
		downloadFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				downloadRispostaCedAutotutele(record);
			}
		});
		visualizzaRispostaCedAutotuteleMenu.addItem(downloadFileAllegatoMenuItem);
		
		visualizzaRispostaCedAutotuteleMenu.showContextMenu();
	
	}
	
	@Override  
	public void manageContextClick(final Record record) {	
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)), null);
		}
	}
	
	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		final Menu contextMenu = new Menu();
		
		// Crea la voce APRI FOLDER
		MenuItem folderMenuItem = new MenuItem(I18NUtil.getMessages().procedimenti_personali_iconaFolderButton_prompt(), "archivio/flgUdFolder/F.png");   
		folderMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderButtonClick(record);
			}   
		});   			
		contextMenu.addItem(folderMenuItem);
		
		// Crea la voce DETTAGLIO FOLDER		
		MenuItem folderDetailMenuItem = new MenuItem(I18NUtil.getMessages().procedimenti_personali_iconaFolderDetailButton_prompt(), "buttons/detail.png");   
		folderDetailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderDetailButtonClick(record);
			}   
		});   			
		contextMenu.addItem(folderDetailMenuItem);
		
		contextMenu.showContextMenu();
	}
		
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
	
	protected boolean isRecordAbilInviaALibroFirmaVistoRegCont(Record record) {
		return record.getAttributeAsBoolean("abilInviaALibroFirmaVistoRegCont");
	}
	
	protected boolean isRecordAbilTogliDaLibroFirmaVistoRegCont(Record record) {
		return record.getAttributeAsBoolean("abilTogliaDaLibroFirmaVistoRegCont");
	}
    
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
			if(folderButtonField == null) {
				folderButtonField = new ControlListGridField("folderButton");  
				folderButtonField.setAttribute("custom", true);	
				folderButtonField.setShowHover(true);	
				folderButtonField.setCanReorder(false);		
				folderButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {										
						return buildImgButtonHtml("archivio/flgUdFolder/F.png");																
					}
				});
				folderButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
																
						return I18NUtil.getMessages().procedimenti_personali_iconaFolderButton_prompt();
					}
				});		
				folderButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						folderButtonClick(record);	
					}
				});													
			}
			buttonsList.add(folderButtonField);
			
			if(folderDetailButtonField == null) {
				folderDetailButtonField = new ControlListGridField("folderDetailButton");  
				folderDetailButtonField.setAttribute("custom", true);	
				folderDetailButtonField.setShowHover(true);	
				folderDetailButtonField.setCanReorder(false);		
				folderDetailButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {										
						return buildImgButtonHtml("buttons/detail.png");														
					}
				});
				folderDetailButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
																
						return I18NUtil.getMessages().procedimenti_personali_iconaFolderDetailButton_prompt();
					}
				});		
				folderDetailButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						folderDetailButtonClick(record);
					}
				});												
			}		
			buttonsList.add(folderDetailButtonField);
			
			if(visualizzaRispostaCedAutotuteleButtonField == null) {
				visualizzaRispostaCedAutotuteleButtonField = new ControlListGridField("visualizzaRispostaCedAutotuteleButton");  
				visualizzaRispostaCedAutotuteleButtonField.setAttribute("custom", true);	
				visualizzaRispostaCedAutotuteleButtonField.setShowHover(true);	
				visualizzaRispostaCedAutotuteleButtonField.setCanReorder(false);		
				visualizzaRispostaCedAutotuteleButtonField.setCellFormatter(new CellFormatter() {			
					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
						if	(contieneRispostaCedAutotutela(record)) {
							return buildImgButtonHtml("file/preview.png");
						}
						return null;
					}
				});
				visualizzaRispostaCedAutotuteleButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						if	(contieneRispostaCedAutotutela(record)) {										
							return I18NUtil.getMessages().procedimenti_in_iter_iconaVisualizzaRispostaCedAutotuteleButton_prompt();
						}
						return null;
					}
				});		
				visualizzaRispostaCedAutotuteleButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						Record record = event.getRecord();
						if (contieneRispostaCedAutotutela(record)) {
							event.cancel();
							visualizzaRispostaCedAutotuteleButtonClick(record);
						}
					}
				});			
			}		
			buttonsList.add(visualizzaRispostaCedAutotuteleButtonField);	
		}	
		
		return buttonsList;		
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	private boolean contieneRispostaCedAutotutela(Record record) {
		return (record != null && record.getAttribute("uriRispostaCedAutotutele") != null && !"".equalsIgnoreCase(record.getAttribute("uriRispostaCedAutotutele")));
	}
	
	protected void previewRispostaCedAutotutele(Record record) {		
		String idUdRispostaCedAutotutele =  record.getAttribute("idUdRispostaCedAutotutele");
		String idDocRispostaCedAutotutele =  record.getAttribute("idDocRispostaCedAutotutele");
		addToRecent(idUdRispostaCedAutotutele, idDocRispostaCedAutotutele);
		String uriRispostaCedAutotutele = record.getAttribute("uriRispostaCedAutotutele");
		String displayFilenameRispostaCedAutotutele = record.getAttribute("displayFilenameRispostaCedAutotutele");
		String mimetypeRispostaCedAutotutele = record.getAttribute("mimetypeRispostaCedAutotutele");
		boolean firmatoRispostaCedAutotutele = record.getAttributeAsBoolean("firmatoRispostaCedAutotutele");
		boolean convertibileRispostaCedAutotutele = record.getAttributeAsBoolean("convertibileRispostaCedAutotutele");
		Record infoFile = new Record();
		infoFile.setAttribute("correctFileName", displayFilenameRispostaCedAutotutele);
		infoFile.setAttribute("mimetype", mimetypeRispostaCedAutotutele);
		infoFile.setAttribute("firmato", firmatoRispostaCedAutotutele);
		infoFile.setAttribute("convertibile", convertibileRispostaCedAutotutele);
		infoFile.setAttribute("bytes", 0);
		PreviewControl.switchPreview(uriRispostaCedAutotutele, false, new InfoFileRecord(infoFile), "FileToExtractBean", displayFilenameRispostaCedAutotutele);
	}
		
	protected void downloadRispostaCedAutotutele(Record record) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", record.getAttributeAsString("displayFilenameRispostaCedAutotutele"));
		lRecord.setAttribute("uri", record.getAttributeAsString("uriRispostaCedAutotutele"));
		lRecord.setAttribute("sbustato", "false");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
	}
}