/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
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
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.MostraIterSvoltoModalWindows;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * 
 * @author dbe4235
 *
 */

public class TSOInIterList extends CustomList {
	
	private ListGridField idProcedimentoField;
	private ListGridField estremiProcedimentoField;
	private ListGridField tipoProcedimentoField;
	private ListGridField documentoInizialeProcedimentoField;
	private ListGridField dataPresentazioneField;
	private ListGridField inFaseProcedimentoField;
	private ListGridField ultimoTaskProcedimentoField;
	private ListGridField messaggioUltimoTaskProcedimentoField;
	private ListGridField prossimeAttivitaField;
	private ListGridField assegnatarioProcedimentoField;
	private ListGridField uriFileField;
	private ListGridField nomeFileField;
	private ListGridField idUdFolderField;
	private ListGridField esitoUltimaAttivitaField;
	private ListGridField esitoEstesoUltimaAttivitaField;
	private ListGridField dataCertificatoField;
	private ListGridField cognomeMedicoField;
	private ListGridField nomeMedicoField;
	private ListGridField contattiMedicoField;
	private ListGridField cognomePazienteField;
	private ListGridField nomePazienteField;
	private ListGridField dataNascitaPazienteField;
	private ListGridField minorePazienteField;
	private ListGridField luogoNascitaPazienteField;
	
	protected ControlListGridField folderButtonField;
	protected ControlListGridField folderDetailButtonField;
	protected ControlListGridField iterSvoltoButtonField;
	protected ControlListGridField scaricaRichiestaButtonField;
	
	public TSOInIterList(String nomeEntita) {
		super(nomeEntita);
		
		uriFileField = new ListGridField("uriFile");
		uriFileField.setHidden(true);
		uriFileField.setCanHide(false);
		
		nomeFileField = new ListGridField("nomeFile");
		nomeFileField.setHidden(true);
		nomeFileField.setCanHide(false);
		
		idUdFolderField = new ListGridField("idUdFolder");
		idUdFolderField.setHidden(true);
		idUdFolderField.setCanHide(false);
		
		idProcedimentoField = new ListGridField("idProcedimento");
		idProcedimentoField.setHidden(true);
		idProcedimentoField.setCanHide(false);
		
		estremiProcedimentoField = new ListGridField("estremiProcedimento");
		estremiProcedimentoField.setHidden(true);
		estremiProcedimentoField.setCanHide(false);
		
		tipoProcedimentoField = new ListGridField("tipoProcedimento");
		tipoProcedimentoField.setHidden(true);
		tipoProcedimentoField.setCanHide(false);
		
		documentoInizialeProcedimentoField = new ListGridField("documentoInizialeProcedimentoXOrd", I18NUtil.getMessages().tso_in_iter_list_documentoInizialeProcedimento());
		documentoInizialeProcedimentoField.setDisplayField("documentoInizialeProcedimento");
		documentoInizialeProcedimentoField.setSortByDisplayField(false);
		documentoInizialeProcedimentoField.setWrap(false);
		documentoInizialeProcedimentoField.setWidth(100);
		documentoInizialeProcedimentoField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), "Richiesta " + record.getAttribute("documentoInizialeProcedimento"), new BooleanCallback() {

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
		});
		
		dataPresentazioneField = new ListGridField("dataPresentazione", I18NUtil.getMessages().tso_in_iter_list_dataPresentazione());
		dataPresentazioneField.setWidth(100);
		dataPresentazioneField.setType(ListGridFieldType.DATE);
		dataPresentazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		inFaseProcedimentoField = new ListGridField("inFaseProcedimento", I18NUtil.getMessages().tso_in_iter_list_inFaseProcedimento());
		
		ultimoTaskProcedimentoField = new ListGridField("ultimoTaskProcedimento", I18NUtil.getMessages().tso_in_iter_list_ultimoTaskProcedimento());
		
		messaggioUltimoTaskProcedimentoField = new ListGridField("messaggioUltimoTaskProcedimento", I18NUtil.getMessages().tso_in_iter_list_messaggioUltimoTaskProcedimento());
		
		prossimeAttivitaField = new ListGridField("prossimeAttivita", I18NUtil.getMessages().tso_in_iter_list_prossimeAttivita());
		
		assegnatarioProcedimentoField = new ListGridField("assegnatarioProcedimento", I18NUtil.getMessages().tso_in_iter_list_assegnatarioProcedimento());
		
		esitoUltimaAttivitaField = new ListGridField("esitoUltimaAttivita", I18NUtil.getMessages().tso_in_iter_list_esitoUltimaAttivita());
		esitoUltimaAttivitaField.setType(ListGridFieldType.ICON);
		esitoUltimaAttivitaField.setWidth(30);
		esitoUltimaAttivitaField.setIconWidth(16);
		esitoUltimaAttivitaField.setIconHeight(16);
		esitoUltimaAttivitaField.setAlign(Alignment.CENTER);
		esitoUltimaAttivitaField.setWidth(30);		
		
		Map<String, String> flgEsitoUltimaAttivitaValueIcons = new HashMap<String, String>();		
		flgEsitoUltimaAttivitaValueIcons.put("OK", "pratiche/task/icone/svolta_OK.png");
		flgEsitoUltimaAttivitaValueIcons.put("KO", "pratiche/task/icone/svolta_OK.png");
		flgEsitoUltimaAttivitaValueIcons.put("W",  "pratiche/task/icone/svolta_W.png");
		esitoUltimaAttivitaField.setValueIcons(flgEsitoUltimaAttivitaValueIcons);
		esitoUltimaAttivitaField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return record.getAttributeAsString("esitoEstesoUltimaAttivita");
			}
		});
		
		esitoEstesoUltimaAttivitaField = new ListGridField("esitoEstesoUltimaAttivita");
		esitoEstesoUltimaAttivitaField.setHidden(true);
		esitoEstesoUltimaAttivitaField.setCanHide(false);
		
		dataCertificatoField = new ListGridField("dataCertificato", I18NUtil.getMessages().tso_in_iter_list_dataCertificato());
		dataCertificatoField.setWidth(100);
		dataCertificatoField.setType(ListGridFieldType.DATE);
		dataCertificatoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		 
		cognomeMedicoField = new ListGridField("cognomeMedico", I18NUtil.getMessages().tso_in_iter_list_cognomeMedico());
		
		nomeMedicoField = new ListGridField("nomeMedico", I18NUtil.getMessages().tso_in_iter_list_nomeMedico());
		
		contattiMedicoField = new ListGridField("contattiMedico", I18NUtil.getMessages().tso_in_iter_list_contattiMedico());
		
		cognomePazienteField = new ListGridField("cognomePaziente", I18NUtil.getMessages().tso_in_iter_list_cognomePaziente());
		
		nomePazienteField = new ListGridField("nomePaziente", I18NUtil.getMessages().tso_in_iter_list_nomePaziente());
		
		dataNascitaPazienteField  = new ListGridField("dataNascitaPaziente", I18NUtil.getMessages().tso_in_iter_list_dataNascitaPaziente());
		dataNascitaPazienteField.setWidth(100);
		dataNascitaPazienteField.setType(ListGridFieldType.DATE);
		dataNascitaPazienteField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		minorePazienteField = new ListGridField("minorePaziente", I18NUtil.getMessages().tso_in_iter_list_minorePaziente());
		minorePazienteField.setType(ListGridFieldType.ICON);
		minorePazienteField.setWidth(30);
		minorePazienteField.setIconWidth(16);
		minorePazienteField.setIconHeight(16);
		minorePazienteField.setAlign(Alignment.CENTER);
		minorePazienteField.setWidth(30);		
		
		Map<String, String> flgPazienteMinoreValueIcons = new HashMap<String, String>();		
		flgPazienteMinoreValueIcons.put("1", "ok.png");
		flgPazienteMinoreValueIcons.put("0", "blank.png");
		minorePazienteField.setValueIcons(flgPazienteMinoreValueIcons);
		minorePazienteField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("minorePaziente"))) {
					return "Presente";
				}				
				return null;
			}
		});
		
		luogoNascitaPazienteField = new ListGridField("luogoNascitaPaziente", I18NUtil.getMessages().tso_in_iter_list_luogoNascitaPaziente());
		
		setFields(new ListGridField[] {
				idProcedimentoField,
				estremiProcedimentoField,
				tipoProcedimentoField,
				documentoInizialeProcedimentoField,
				dataPresentazioneField,
				inFaseProcedimentoField,
				ultimoTaskProcedimentoField,
				messaggioUltimoTaskProcedimentoField,
				prossimeAttivitaField,
				assegnatarioProcedimentoField,
				uriFileField,
				nomeFileField,
				idUdFolderField,
				esitoUltimaAttivitaField,
				esitoEstesoUltimaAttivitaField,
				dataCertificatoField,
				cognomeMedicoField,
				nomeMedicoField,
				contattiMedicoField,
				cognomePazienteField,
				nomePazienteField,
				dataNascitaPazienteField,
				minorePazienteField,
				luogoNascitaPazienteField
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
			folderButton.setPrompt("Apri fascicolo");
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
			folderDetailButton.setPrompt("Visualizza dettaglio");
			folderDetailButton.setSize(16);  
			folderDetailButton.addClickHandler(new ClickHandler() {   
				public void onClick(ClickEvent event) {      
					folderDetailButtonClick(record);
				}  
			});
			
			// bottone DETTAGLIO FOLDER
			ImgButton mostraIterSvoltoButton = new ImgButton();
			mostraIterSvoltoButton.setShowDown(false);   
			mostraIterSvoltoButton.setShowRollOver(false);   
			mostraIterSvoltoButton.setLayoutAlign(Alignment.CENTER);   
			mostraIterSvoltoButton.setSrc("buttons/iter_svolto.png");
			mostraIterSvoltoButton.setPrompt("Visualizza dettaglio");
			mostraIterSvoltoButton.setSize(16);  
			mostraIterSvoltoButton.addClickHandler(new ClickHandler() {   
				public void onClick(ClickEvent event) {      
					mostraIterSvoltoClick(record);
				}  
			});
			
//			// bottone Scarica richiesta
//			ImgButton scaricaRichiestaButton = new ImgButton();
//			scaricaRichiestaButton.setShowDown(false);   
//			scaricaRichiestaButton.setShowRollOver(false);   
//			scaricaRichiestaButton.setLayoutAlign(Alignment.CENTER);   
//			scaricaRichiestaButton.setPrompt("Scarica richiesta");
//			scaricaRichiestaButton.setSize(16);  
//			scaricaRichiestaButton.addClickHandler(new ClickHandler() {   
//				public void onClick(ClickEvent event) {      
//					if(isAbilToDownload(record)) {
//						scaricaFile(record);
//					}
//				}  
//			});
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(5);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			recordCanvas.addMember(folderButton);
			recordCanvas.addMember(folderDetailButton);
			recordCanvas.addMember(mostraIterSvoltoButton);
//			if(isAbilToDownload(record)) {
//				scaricaRichiestaButton.setSrc("file/download.png");
//				recordCanvas.addMember(scaricaRichiestaButton);
//			} else {
//				scaricaRichiestaButton.setSrc("file/download_manager_Disabled.png");
//				recordCanvas.addMember(scaricaRichiestaButton);
//			}
						
			lCanvasReturn = recordCanvas;
		}	
		return lCanvasReturn;
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
	
	protected void mostraIterSvoltoClick(Record record) {
		String idPratica = record.getAttribute("idProcedimento");
		String estremi = record.getAttribute("documentoInizialeProcedimento");
		MostraIterSvoltoModalWindows windows = new MostraIterSvoltoModalWindows(idPratica, estremi);
		windows.show();
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
		MenuItem folderMenuItem = new MenuItem("Apri fascicolo", "archivio/flgUdFolder/F.png");   
		folderMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderButtonClick(record);
			}   
		});   			
		contextMenu.addItem(folderMenuItem);
		
		// Crea la voce DETTAGLIO FOLDER		
		MenuItem folderDetailMenuItem = new MenuItem("Visualizza dettaglio", "buttons/detail.png");   
		folderDetailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderDetailButtonClick(record);
			}   
		});   			
		contextMenu.addItem(folderDetailMenuItem);
		
		// Crea la voce ITER SVOLTO
		MenuItem mostraIterSvoltoMenuItem = new MenuItem("Mostra iter svolto", "buttons/iter_svolto.png");
		mostraIterSvoltoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				mostraIterSvoltoClick(record);
			}
		});
		contextMenu.addItem(mostraIterSvoltoMenuItem);	
		
		contextMenu.showContextMenu();
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
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
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {										
						return buildImgButtonHtml("archivio/flgUdFolder/F.png");																
					}
				});
				folderButtonField.setHoverCustomizer(new HoverCustomizer() {				
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
																
						return I18NUtil.getMessages().procedimenti_in_iter_iconaFolderButton_prompt();
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
																
						return "Visualizza dettaglio";
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
			
			if (iterSvoltoButtonField == null) {
				iterSvoltoButtonField = new ControlListGridField("iterSvoltoButton");
				iterSvoltoButtonField.setAttribute("custom", Boolean.TRUE);
				iterSvoltoButtonField.setShowHover(Boolean.TRUE);
				iterSvoltoButtonField.setCanReorder(Boolean.FALSE);
				iterSvoltoButtonField.setCellFormatter(new CellFormatter() {

					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						return buildImgButtonHtml("buttons/iter_svolto.png");
					}
				});
				iterSvoltoButtonField.setHoverCustomizer(new HoverCustomizer() {

					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						return "Mostra iter svolto";
					}
				});
				iterSvoltoButtonField.addRecordClickHandler(new RecordClickHandler() {

					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						mostraIterSvoltoClick(record);
					}
				});
			}		
			buttonsList.add(iterSvoltoButtonField);	
		
			if(scaricaRichiestaButtonField == null) {
				scaricaRichiestaButtonField = new ControlListGridField("scaricaRichiestaButton");  
				scaricaRichiestaButtonField.setAttribute("custom", true);	
				scaricaRichiestaButtonField.setShowHover(true);	
				scaricaRichiestaButtonField.setCanReorder(false);		
				scaricaRichiestaButtonField.setCellFormatter(new CellFormatter() {		
					
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isAbilToDownload(record)) {
							return buildImgButtonHtml("file/download.png");
						} else {
							return buildImgButtonHtml("file/download_manager_Disabled.png");
						}
					}
				});
				scaricaRichiestaButtonField.setHoverCustomizer(new HoverCustomizer() {	
					
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {	
						if(isAbilToDownload(record)) {
							return "Scarica richiesta";
						}
						return null;
					}
				});		
				scaricaRichiestaButtonField.addRecordClickHandler(new RecordClickHandler() {
					
					@Override
					public void onRecordClick(RecordClickEvent event) {						
						event.cancel();
						if(isAbilToDownload(getRecord(event.getRecordNum()))) {	
							ListGridRecord record = getRecord(event.getRecordNum());
							scaricaFile(record);
						}
					}
				});
			}
			buttonsList.add(scaricaRichiestaButtonField);
			
		}	
		return buttonsList;		
	}
	
	protected Boolean isAbilToDownload(Record record) {
		boolean valid = false;
		if(record != null && record.getAttributeAsString("uriFile") != null &&
				!"".equals(record.getAttributeAsString("uriFile"))) {
			valid = true;
		}
		return valid;
	}
	
	public void scaricaFile(Record record) {
		
		String nomeFile = record.getAttributeAsString("nomeFile");
		String uriFile = record.getAttributeAsString("uriFile");
		
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", nomeFile);
		lRecord.setAttribute("uri", uriFile);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "true");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("documentoInizialeProcedimentoXOrd")) {
				if (record.getAttribute("documentoInizialeProcedimentoXOrd") != null &&
						!"".equalsIgnoreCase(record.getAttribute("documentoInizialeProcedimentoXOrd"))) {
									return it.eng.utility.Styles.cellTextBlueClickable;
				}
			} else {
				return super.getBaseStyle(record, rowNum, colNum);
			}
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}
}