/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
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
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SortChangedHandler;
import com.smartgwt.client.widgets.grid.events.SortEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaTipoAttoWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class ListaDatiConvocazioneSedutaItem extends GridItem {
	
	private ListaDatiConvocazioneSedutaItem instance = this;
	
	protected ListGridField idUd;
	protected ListGridField tipo;
	protected ListGridField codTipo;
	protected ListGridField estremiPropostaUDXOrd;
	protected ListGridField nrOrdineOdg;
	protected ListGridField oggetto;
	protected ListGridField nominativoProponente;
	protected ListGridField dtInoltro;
	protected ListGridField flgInoltro;
	protected ListGridField flgAggiunto;
	protected ListGridField nroAllegati;
	protected ListGridField idUdPropostaDelibera;
	protected ListGridField flgCanRemove;
	protected ListGridField strutturaProponente;
	protected ListGridField centroDiCosto;
	protected ListGridField nroCircoscrizione;
	protected ListGridField nomeCircoscrizione;
	protected ListGridField flgReinviata;
	protected ListGridField flgPresenteInOdg;
	protected ListGridField nrOrdUltimoOdgCons;
	protected ListGridField dtOdgConsolidato;
	protected ListGridField flgElimina;
	protected ListGridField uriFilePrimario;
	protected ListGridField nomeFilePrimario;	
	protected ListGridField statoRevisioneTesto;
	protected ListGridField flgEmendamenti;
	protected ListGridField iniziativaDelibera;
	protected ListGridField dettTipoAtto;
	protected ListGridField flgImmEseguibile;
	protected ListGridField noteAtto;
	protected ListGridField flgFuoriPacco;
	protected ListGridField ultimPassoIter;
	protected ListGridField inDefinizione;
	protected ListGridField trasmissioneAtto;
	protected ListGridField flgAttoTrasmesso;
	protected ListGridField estremiUDTrasmissione;
	protected ListGridField idUDTrasmissione;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modificaTestoButtonField;
	protected ControlListGridField riepilogoFirmeVistiButtonField;
	protected ControlListGridField fileAssociatiButtonField;
	protected ControlListGridField scaricaFileCompletiXAttiButtonField;
	protected ControlListGridField removeButtonField;
	
	protected String organoCollegiale;
	protected String codCircoscrizione;
	private String headerClicked;
			
	public ListaDatiConvocazioneSedutaItem(String name, String organoCollegiale, String codCircoscrizione) {
		super(name, "lista_dati_convocazione_seduta");
		
		this.organoCollegiale = organoCollegiale;
		this.codCircoscrizione = codCircoscrizione;
		
		setGridPkField("idUd");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(!isOdGChiuso());
		setShowEditButtons(true);
		
		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false); 
		idUd.setCanSort(false);
		
		tipo = new ListGridField("tipo", "Tipo");
		tipo.setCanSort(true);
		
		codTipo = new ListGridField("codTipo","Cod. tipo");
		codTipo.setCanSort(true);
		
		estremiPropostaUDXOrd = new ListGridField("estremiPropostaUDXOrd", "N°");
		estremiPropostaUDXOrd.setCanSort(true);
		estremiPropostaUDXOrd.setDisplayField("estremiPropostaUD");
		estremiPropostaUDXOrd.setSortByDisplayField(false);
		estremiPropostaUDXOrd.setWidth(100);
		estremiPropostaUDXOrd.setAlign(Alignment.LEFT);
		estremiPropostaUDXOrd.setCanDragResize(true);
		estremiPropostaUDXOrd.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {											
					onClickDetailButton(event.getRecord());	
				}
			}
		});
		estremiPropostaUDXOrd.setBaseStyle(it.eng.utility.Styles.cellTextBlueClickable);
		
		nrOrdineOdg = new ListGridField("nrOrdineOdg", "N° in Odg");
		setTypeNrOrdineOdg(nrOrdineOdg);
		nrOrdineOdg.setCanSort(true);
		
		oggetto = new ListGridField("oggetto", "Oggetto");
		oggetto.setCanSort(false);
		
		nominativoProponente = new ListGridField("nominativoProponente", "Proponente");
		nominativoProponente.setCanSort(true);
		 
		dtInoltro = new ListGridField("dtInoltro", "Inoltro alla seduta in data");
		dtInoltro.setType(ListGridFieldType.DATE);
		dtInoltro.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dtInoltro.setCanSort(true);
		
		flgInoltro = new ListGridField("flgInoltro", "Inoltro fuori termine");
		flgInoltro.setType(ListGridFieldType.ICON);
		flgInoltro.setWidth(30);
		flgInoltro.setIconWidth(16);
		flgInoltro.setIconHeight(16);
		Map<String, String> flgInoltroIcons = new HashMap<String, String>();		
		flgInoltroIcons.put("1", "protocollazione/operazioniEffettuate.png");
		flgInoltroIcons.put("0", "blank.png");
		flgInoltro.setValueIcons(flgInoltroIcons);
		flgInoltro.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgInoltro") != null && "1".equals(record.getAttribute("flgInoltro"))) {
					return "Fuori termine";
				} else {
					return "Normale";
				} 	
			}
		});
		flgInoltro.setCanSort(true);
		
		flgAggiunto = new ListGridField("flgAggiunto", "Aggiunto");
		flgAggiunto.setType(ListGridFieldType.ICON);
		flgAggiunto.setWidth(30);
		flgAggiunto.setIconWidth(16);
		flgAggiunto.setIconHeight(16);
		Map<String, String> flgAggiuntoIcons = new HashMap<String, String>();		
		flgAggiuntoIcons.put("1", "delibere/A.png");
		flgAggiuntoIcons.put("0", "blank.png");
		flgAggiunto.setValueIcons(flgAggiuntoIcons);
		flgAggiunto.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgAggiunto") != null && "1".equals(record.getAttribute("flgAggiunto"))) {
					return "Atto aggiunto in OdG dalla segreteria";
				} else {
					return "Atto inoltrato in OdG dal proponente";
				} 
			}
		});
		flgAggiunto.setCanSort(true);
		
		nroAllegati = new ListGridField("nroAllegati", "N° allegati");
		nroAllegati.setType(ListGridFieldType.INTEGER);
		nroAllegati.setCanSort(true);
		
		//estremiPropostaDelibera = new ListGridField("estremiPropostaDelibera", "Relativo alla proposta N°");
		//estremiPropostaDelibera.setCanSort(true);
		
		idUdPropostaDelibera = new ListGridField("idUdPropostaDelibera");
		idUdPropostaDelibera.setHidden(true);
		idUdPropostaDelibera.setCanHide(false); 
		idUdPropostaDelibera.setCanSort(false);
		
		flgCanRemove = new ListGridField("flgCanRemove");
		flgCanRemove.setHidden(true);
		flgCanRemove.setCanHide(false); 
		flgCanRemove.setCanSort(false);
		
		strutturaProponente = new ListGridField("strutturaProponente", "Struttura prop.");
		strutturaProponente.setCanSort(true);
		
		centroDiCosto = new ListGridField("centroDiCosto", "CdC");
		centroDiCosto.setCanSort(true);
		
		nroCircoscrizione = new ListGridField("nroCircoscrizione", "N° circ");
		nroCircoscrizione.setCanSort(true);
		
		nomeCircoscrizione = new ListGridField("nomeCircoscrizione", "Nome circ.");
		nomeCircoscrizione.setCanSort(true);

		flgReinviata = new ListGridField("flgReinviata", "Reinviata");
		flgReinviata.setType(ListGridFieldType.ICON);
		flgReinviata.setWidth(30);
		flgReinviata.setIconWidth(16);
		flgReinviata.setIconHeight(16);
		Map<String, String> flgReinviataIcons = new HashMap<String, String>();		
		flgReinviataIcons.put("1", "buttons/send.png");
		flgReinviataIcons.put("0", "blank.png");
		flgReinviata.setValueIcons(flgReinviataIcons);
		flgReinviata.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgReinviata") != null && "1".equals(record.getAttribute("flgReinviata"))) {
					return "Reinviata";
				}						
				return null;
			}
		});
		flgReinviata.setCanSort(true);
		
		flgPresenteInOdg = new ListGridField("flgPresenteInOdg", "Presente in OdG già consolidato");
		flgPresenteInOdg.setType(ListGridFieldType.ICON);
		flgPresenteInOdg.setWidth(30);
		flgPresenteInOdg.setIconWidth(16);
		flgPresenteInOdg.setIconHeight(16);
		Map<String, String> flgPresenteInOdgIcons = new HashMap<String, String>();		
		flgPresenteInOdgIcons.put("1", "ok.png");
		flgPresenteInOdgIcons.put("0", "blank.png");
		flgPresenteInOdg.setValueIcons(flgPresenteInOdgIcons);
		flgPresenteInOdg.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgPresenteInOdg") != null && "1".equals(record.getAttribute("flgPresenteInOdg"))) {
					return "Presente";
				}						
				return null;
			}
		});
		flgPresenteInOdg.setCanSort(true);

		nrOrdUltimoOdgCons = new ListGridField("nrOrdUltimoOdgCons", "N° ord. in ultimo OdG consolidato");
		setTypeNrOrdUltimoOdgCons(nrOrdUltimoOdgCons);
		nrOrdUltimoOdgCons.setCanSort(true);
		
		dtOdgConsolidato = new ListGridField("dtOdgConsolidato", "Presente in OdG consolidato il");
		dtOdgConsolidato.setType(ListGridFieldType.DATE);
		dtOdgConsolidato.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dtOdgConsolidato.setCanSort(true);
		
		flgElimina = new ListGridField("flgElimina");
		flgElimina.setHidden(true);
		flgElimina.setCanHide(false); 
		flgElimina.setCanSort(false);
		
		uriFilePrimario = new ListGridField("uriFilePrimario");
		uriFilePrimario.setHidden(true);
		uriFilePrimario.setCanHide(false); 
		uriFilePrimario.setCanSort(false);
		
		nomeFilePrimario = new ListGridField("nomeFilePrimario");
		nomeFilePrimario.setHidden(true);
		nomeFilePrimario.setCanHide(false); 
		nomeFilePrimario.setCanSort(false);
		
		statoRevisioneTesto = new ListGridField("statoRevisioneTesto", "Stato");
		statoRevisioneTesto.setType(ListGridFieldType.ICON);
		statoRevisioneTesto.setWidth(30);
		statoRevisioneTesto.setIconWidth(16);
		statoRevisioneTesto.setIconHeight(16);
		Map<String, String> statoRevisioneTestoIcons = new HashMap<String, String>();		
		statoRevisioneTestoIcons.put("firmato", 			 "delibere/statoRevisioneTesto/firmato.png");
		statoRevisioneTestoIcons.put("firmato_parzialmente", "delibere/statoRevisioneTesto/firmato_parzialmente.png");
		statoRevisioneTestoIcons.put("pronto_da_firmare", 	 "delibere/statoRevisioneTesto/pronto_da_firmare.png");
		statoRevisioneTestoIcons.put("testo_in_lavorazione", "delibere/statoRevisioneTesto/testo_in_lavorazione.png");
		statoRevisioneTestoIcons.put("lavorazione_conclusa", "delibere/statoRevisioneTesto/lavorazione_conclusa.png");
		statoRevisioneTestoIcons.put("rinviata", 			 "delibere/statoRevisioneTesto/rinviata.png");
		statoRevisioneTestoIcons.put("ritirato", 			 "delibere/statoRevisioneTesto/ritirato.png");
		statoRevisioneTestoIcons.put("", "blank.png");
		statoRevisioneTesto.setValueIcons(statoRevisioneTestoIcons);
		statoRevisioneTesto.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("statoRevisioneTesto") != null) {
					if("firmato".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Firmato: apposte tutte le firme previste";
					} else if("firmato_parzialmente".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "In raccolta firme (NON tutte le firme previste)";
					} else if("pronto_da_firmare".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Testo coordinato completato";
					} else if("testo_in_lavorazione".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Testo coordinato da ultimare";
					} else if("lavorazione_conclusa".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Lavorazione conclusa";
					} else if("rinviata".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Rinviata";
					}  else if("ritirato".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Ritirato";
					}
				}
				return null;
			}
		});
		statoRevisioneTesto.setCanSort(true);
		
		flgEmendamenti = new ListGridField("flgEmendamenti", "Presenza emendamenti");
		flgEmendamenti.setType(ListGridFieldType.ICON);
		flgEmendamenti.setWidth(30);
		flgEmendamenti.setIconWidth(16);
		flgEmendamenti.setIconHeight(16);
		Map<String, String> flgEmendamentiIcons = new HashMap<String, String>();		
		flgEmendamentiIcons.put("1", "attiInLavorazione/EM.png");
		flgEmendamentiIcons.put("0", "blank.png");
		flgEmendamenti.setValueIcons(flgEmendamentiIcons);
		flgEmendamenti.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgEmendamenti") != null && "1".equals(record.getAttribute("flgEmendamenti"))) {
					return "Presenti emendamenti";
				}						
				return null;
			}
		});
		flgEmendamenti.setCanSort(true);
		
		iniziativaDelibera = new ListGridField("iniziativaDelibera", "Iniziativa");
		iniziativaDelibera.setCanSort(true);
		
		dettTipoAtto = new ListGridField("dettTipoAtto", "Tipo di dettaglio");
		dettTipoAtto.setCanSort(true);
		
		flgImmEseguibile = new ListGridField("flgImmEseguibile", "I.E.");
		flgImmEseguibile.setType(ListGridFieldType.ICON);
		flgImmEseguibile.setWidth(30);
		flgImmEseguibile.setIconWidth(16);
		flgImmEseguibile.setIconHeight(16);
		Map<String, String> flgImmEseguibileIcons = new HashMap<String, String>();		
		flgImmEseguibileIcons.put("1", "attiInLavorazione/IE.png");
		flgImmEseguibileIcons.put("0", "blank.png");
		flgImmEseguibile.setValueIcons(flgImmEseguibileIcons);
		flgImmEseguibile.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgImmEseguibile") != null && "1".equals(record.getAttribute("flgImmEseguibile"))) {
					return "I.E.";
				}						
				return null;
			}
		});
		flgImmEseguibile.setCanSort(true);
		
		noteAtto = new ListGridField("iconaNoteAtto", "Note atto in seduta");
		noteAtto.setType(ListGridFieldType.ICON);
		noteAtto.setWidth(30);
		noteAtto.setIconWidth(16);
		noteAtto.setIconHeight(16);
		noteAtto.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					return buildImgButtonHtml("buttons/note_seduta.png");
				} else if(!fromStoricoSeduta()) {
					return buildImgButtonHtml("buttons/add_note_seduta.png");
				}	
				return null;
			}
		});
		noteAtto.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					return "Annotazioni apposte";
				} else if(!fromStoricoSeduta()) {
					return "Apponi annotazioni";
				}	
				return null;				
			}
		});
		noteAtto.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = event.getRecord();
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					Record recordNote = new Record();
					recordNote.setAttribute("noteAtto", record.getAttribute("noteAtto"));
					recordNote.setAttribute("storico" , fromStoricoSeduta());
					NoteAttoPopup noteAttoPopup = new NoteAttoPopup(recordNote) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							if(!fromStoricoSeduta()) {
								grid.deselectAllRecords();
								record.setAttribute("noteAtto", object.getAttribute("noteAtto"));
								updateGridItemValue();		
							}
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					};
					noteAttoPopup.show();
				} else if(!fromStoricoSeduta()) {
					NoteAttoPopup noteAttoPopup = new NoteAttoPopup(new Record()) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							grid.deselectAllRecords();
							record.setAttribute("noteAtto", object.getAttribute("noteAtto"));
							updateGridItemValue();						
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					};
					noteAttoPopup.show();
				}
			}
		});
		noteAtto.setCanSort(true);
		
		flgFuoriPacco = new ListGridField("flgFuoriPacco", "Fuori pacco");
		flgFuoriPacco.setType(ListGridFieldType.ICON);
		flgFuoriPacco.setWidth(30);
		flgFuoriPacco.setIconWidth(16);
		flgFuoriPacco.setIconHeight(16);
		Map<String, String> flgFuoriPaccoIcons = new HashMap<String, String>();		
		flgFuoriPaccoIcons.put("1", "delibere/fuoripacco.png");
		flgFuoriPaccoIcons.put("0", "blank.png");
		flgFuoriPacco.setValueIcons(flgFuoriPaccoIcons);
		flgFuoriPacco.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgFuoriPacco") != null && "1".equals(record.getAttribute("flgFuoriPacco"))) {
					return "Fuori pacco";
				}						
				return null;
			}
		});
		flgFuoriPacco.setCanSort(true);
		
		ultimPassoIter = new ListGridField("ultimPassoIter");
		ultimPassoIter.setHidden(true);
		ultimPassoIter.setCanHide(false);
		
		inDefinizione = new ListGridField("inDefinizione", "In definizione");
		inDefinizione.setType(ListGridFieldType.ICON);
		inDefinizione.setWidth(30);
		inDefinizione.setIconWidth(16);
		inDefinizione.setIconHeight(16);
		Map<String, String> flgInDefinizioneIcons = new HashMap<String, String>();		
		flgInDefinizioneIcons.put("1", "delibere/proposta_in_definizione.png");
		flgInDefinizioneIcons.put("0", "blank.png");
		inDefinizione.setValueIcons(flgInDefinizioneIcons);
		inDefinizione.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("inDefinizione") != null && "1".equals(record.getAttribute("inDefinizione"))) {
					return "Ultimo passo iter: " + record.getAttribute("ultimPassoIter");
				}						
				return null;
			}
		});
		inDefinizione.setCanSort(true);
		
		trasmissioneAtto = new ListGridField("iconaTrasmissioneAtto", "Trasmissione/i atto");
		trasmissioneAtto.setType(ListGridFieldType.ICON);
		trasmissioneAtto.setWidth(30);
		trasmissioneAtto.setIconWidth(16);
		trasmissioneAtto.setIconHeight(16);
		trasmissioneAtto.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgAttoTrasmesso") != null && !"".equalsIgnoreCase(record.getAttribute("flgAttoTrasmesso"))) {
					return buildImgButtonHtml("postaElettronica/iconMilano/mail.png");
				} 	
				return null;
			}
		});
		trasmissioneAtto.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgAttoTrasmesso") != null && !"".equalsIgnoreCase(record.getAttribute("flgAttoTrasmesso"))) {
					return "Atto trasmesso";
				} 
				return null;				
			}
		});
		trasmissioneAtto.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = event.getRecord();
				if(record.getAttribute("flgAttoTrasmesso") != null && "1".equalsIgnoreCase(record.getAttribute("flgAttoTrasmesso"))) {
					final String estremiUDTrasmissione = record.getAttribute("estremiUDTrasmissione");
					if(estremiUDTrasmissione != null && !"".equalsIgnoreCase(estremiUDTrasmissione)) {
						
						final Menu menu = new Menu(); 
						if (AurigaLayout.getIsAttivaAccessibilita()) {
							menu.setTabIndex(null);
							menu.setCanFocus(true);		
						}

						String[] listaValoriString = estremiUDTrasmissione.split(";");
						List<String> listaValoriList  = Arrays.asList(listaValoriString);
						for(String item : listaValoriList) {
							final String titlePG = "Dettaglio prot. N° "+ item;
							MenuItem estremiUDTrasmissioneItem = new MenuItem(item);
							estremiUDTrasmissioneItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

								@Override
								public void onClick(MenuItemClickEvent event) {
									
									String idUDTrasmissione = record.getAttributeAsString("idUDTrasmissione");
									if(idUDTrasmissione != null && !"".equalsIgnoreCase(idUDTrasmissione)) {
										Record lRecord = new Record();
										lRecord.setAttribute("idUd", idUDTrasmissione);											
										new DettaglioRegProtAssociatoWindow(lRecord, titlePG);		
									}
								}
							});
							menu.addItem(estremiUDTrasmissioneItem);
						}
						
						menu.showContextMenu();
					}
				}
			}
		});
		trasmissioneAtto.setCanSort(true);
		
		flgAttoTrasmesso = new ListGridField("flgAttoTrasmesso");
		flgAttoTrasmesso.setHidden(true);
		flgAttoTrasmesso.setCanHide(false); 
		flgAttoTrasmesso.setCanSort(false);
		
		estremiUDTrasmissione = new ListGridField("estremiUDTrasmissione");
		estremiUDTrasmissione.setHidden(true);
		estremiUDTrasmissione.setCanHide(false); 
		estremiUDTrasmissione.setCanSort(false);
		
		idUDTrasmissione = new ListGridField("idUDTrasmissione");
		idUDTrasmissione.setHidden(true);
		idUDTrasmissione.setCanHide(false); 
		idUDTrasmissione.setCanSort(false);
		
		if(codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			setGridFields(
					idUd,
					tipo,
					codTipo,
					estremiPropostaUDXOrd,
					nrOrdineOdg,
					oggetto,
					nominativoProponente,
					dtInoltro,
					flgInoltro,
					flgAggiunto,
					nroAllegati,
					idUdPropostaDelibera,
					flgCanRemove,
					strutturaProponente,
					centroDiCosto,
					flgReinviata,
					flgPresenteInOdg,
					nrOrdUltimoOdgCons,
					dtOdgConsolidato,
					flgElimina,
					uriFilePrimario,
					nomeFilePrimario,
					statoRevisioneTesto,
					flgEmendamenti,
					iniziativaDelibera,
					dettTipoAtto,
					flgImmEseguibile,
					noteAtto,
					flgFuoriPacco,
					inDefinizione,
					ultimPassoIter,
					trasmissioneAtto,
					flgAttoTrasmesso,
					estremiUDTrasmissione,
					idUDTrasmissione
			);
		} else {
			setGridFields(
					idUd,
					tipo,
					codTipo,
					estremiPropostaUDXOrd,
					nrOrdineOdg,
					oggetto,
					nominativoProponente,
					dtInoltro,
					flgInoltro,
					flgAggiunto,
					nroAllegati,
					idUdPropostaDelibera,
					flgCanRemove,
					strutturaProponente,
					centroDiCosto,
					nroCircoscrizione,
					nomeCircoscrizione,
					flgReinviata,
					flgPresenteInOdg,
					nrOrdUltimoOdgCons,
					dtOdgConsolidato,
					flgElimina,
					uriFilePrimario,
					nomeFilePrimario,
					statoRevisioneTesto,
					flgEmendamenti,
					iniziativaDelibera,
					dettTipoAtto,
					flgImmEseguibile,
					noteAtto,
					flgFuoriPacco,
					inDefinizione,
					ultimPassoIter,
					trasmissioneAtto,
					flgAttoTrasmesso,
					estremiUDTrasmissione,
					idUDTrasmissione
			);
		}
	
	}
	
	@Override
	public ListGrid buildGrid() {
		
		final ListGrid grid = super.buildGrid();
			
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.addDropCompleteHandler(new DropCompleteHandler() {
		
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				if(attivoOrdNumOdgXTipoSeduta()) {
					refreshNroOrdineGiorno();
				}
			}
		});

		grid.addHeaderClickHandler(new HeaderClickHandler() {

		    @Override
		    public void onHeaderClick(HeaderClickEvent event) {
		    	if(attivoOrdNumOdgXTipoSeduta()) {
		    		headerClicked = grid.getField(event.getFieldNum()).getName();
		    	}
		    }
		});
		
		grid.addSortChangedHandler(new SortChangedHandler() {
			
			@Override
			public void onSortChanged(SortEvent event) {
				
				if(attivoOrdNumOdgXTipoSeduta() && headerClicked != null && !"nrOrdineOdg".equalsIgnoreCase(headerClicked)) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione", "Vuoi aggiornare il valore del campo 'N° in OdG' in base all'ordinamento che stai chiedendo?", "Si", "No", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								refreshNroOrdineGiorno();
							}
						}
					});
				}

			}
		});
		
		return grid;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}	
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("lista_dati_convocazione_seduta", fields);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addPropostaButton = getAddPropostaButton();  
		buttons.add(addPropostaButton);
		
		ToolStripButton addAttoArgomentoButton = getAddAttoArgomentoButton();  
		buttons.add(addAttoArgomentoButton);
		
		ToolStripButton refreshButton = new ToolStripButton();   
		refreshButton.setIcon("buttons/refreshList.png");   
		refreshButton.setIconSize(16);
		refreshButton.setPrefix("ricarica");
		refreshButton.setPrompt("Ricarica");
		refreshButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickRefreshListButton();
			}   
		});  
		buttons.add(refreshButton);
			
		return buttons;
	}

	protected ToolStripButton getAddAttoArgomentoButton() {
		
		ToolStripButton addAttoArgomentoButton = new ToolStripButton();   
		addAttoArgomentoButton.setIcon("buttons/new.png");   
		addAttoArgomentoButton.setIconSize(16);
		addAttoArgomentoButton.setPrefix("aggiunta_atto");
		addAttoArgomentoButton.setPrompt("Nuovo atto/argomento/nota");
		addAttoArgomentoButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				nuovoAttoArgomento();
			}   
		});
		return addAttoArgomentoButton;
	}

	protected ToolStripButton getAddPropostaButton() {
		
		ToolStripButton addPropostaButton = new ToolStripButton();   
		addPropostaButton.setIcon("buttons/importaFileDocumenti.png");   
		addPropostaButton.setIconSize(16);
		addPropostaButton.setPrefix("aggiunta_proposta");
		addPropostaButton.setPrompt("Aggiungi");
		addPropostaButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				aggiungiProposta();     	
			}   
		});
		return addPropostaButton;
	}
	
	public void onClickRefreshListButton() {
		
		Record lRecord = new Record();
		lRecord.setAttribute("organoCollegiale", organoCollegiale);
		lRecord.setAttribute("idSeduta", getIdSeduta());
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				RecordList recordList = object.getAttributeAsRecordList("listaArgomentiOdg");
				setData(recordList);
			}
		});
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		if(modificaTestoButtonField == null) {
			modificaTestoButtonField = buildModificaTestoButtonField();
		}
		if (riepilogoFirmeVistiButtonField == null) {
			riepilogoFirmeVistiButtonField = buildRiepilogoFirmeVistiButtonField();
		}
		if(fileAssociatiButtonField == null) {
			fileAssociatiButtonField = buildFileAssociatiButtonField();
		}
		if(scaricaFileCompletiXAttiButtonField == null) {
			scaricaFileCompletiXAttiButtonField = buildScaricaFileCompletiXAttiButtonField();
		}	
		if(removeButtonField == null) {
			removeButtonField = buildRemoveButtonField();
		}

		buttonsList.add(detailButtonField);
		
		if(isButtonEnabled()) {
			buttonsList.add(modificaTestoButtonField);
		}
		
		buttonsList.add(riepilogoFirmeVistiButtonField);
		buttonsList.add(fileAssociatiButtonField);
		buttonsList.add(scaricaFileCompletiXAttiButtonField);
		buttonsList.add(removeButtonField);
		
		return buttonsList;
	}
	
	protected boolean isButtonEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/C");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/C");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/C");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMG/C");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/OPR/C");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNF/C");
		} else {
			return false;
		}
	}
	
	protected ControlListGridField buildDetailButtonField() {
		
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {											
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {										
					return "Dettaglio proposta/argomento";
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {											
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		String title = "Dettaglio proposta/argomento " + record.getAttributeAsString("estremiPropostaUD");		
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));		
		if(record.getAttributeAsString("idProcessoAuriga") != null && !"".equals(record.getAttributeAsString("idProcessoAuriga"))) {
			AurigaLayout.apriDettaglioPratica(record.getAttributeAsString("idProcessoAuriga"), title, null);
		} else {
			new DettaglioRegProtAssociatoWindow(lRecord, title);		
		}
	}
	
	protected boolean isRecordAbilToModificaTesto(ListGridRecord record) {
		if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
			if(record.getAttributeAsString("idProcessoAuriga") != null && !"".equals(record.getAttributeAsString("idProcessoAuriga"))) {			
				boolean isFirmato = record.getAttribute("statoRevisioneTesto") != null && "firmato".equalsIgnoreCase(record.getAttribute("statoRevisioneTesto"));
				return !isFirmato;
			}
		}
		return false;
	}
	
	protected ControlListGridField buildModificaTestoButtonField() {
		
		ControlListGridField modificaTestoButtonField = new ControlListGridField("modificaTestoButton");  
		modificaTestoButtonField.setAttribute("custom", true);	
		modificaTestoButtonField.setShowHover(true);		
		modificaTestoButtonField.setCanReorder(false);
		modificaTestoButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToModificaTesto(record)) {				
					return buildImgButtonHtml("file/editDoc.png");
				}
				return null;
			}
		});
		modificaTestoButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToModificaTesto(record)) {				
					return "Modifica testo";
				}
				return null;
			}
		});		
		modificaTestoButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(isRecordAbilToModificaTesto(event.getRecord())) {			
					onClickModificaTestoButton(event.getRecord());
				}
			}
		});		
		return modificaTestoButtonField;
	}
	
	public void onClickModificaTestoButton(final ListGridRecord record) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));
		lRecord.setAttribute("idProcess", record.getAttributeAsString("idProcessoAuriga"));
		lRecord.setAttribute("activityName", "#POST_DISCUSSIONE_AULA_" + organoCollegiale);
		lRecord.setAttribute("idDefProcFlow", record.getAttributeAsString("idTipoFlussoActiviti"));
		lRecord.setAttribute("idInstProcFlow", record.getAttributeAsString("idIstanzaFlussoActiviti"));
		new DettaglioPropostaDeliberaWindow(lRecord, "Modifica testo " + record.getAttributeAsString("estremiPropostaUD"), true, false);
	}

	protected boolean isRecordAbilToRiepilogoFirmeVisti(ListGridRecord record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
			return (record.getAttributeAsString("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("uriRiepilogoFirmeVisti"))) ||
					(record.getAttributeAsString("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("idDocRiepilogoFirmeVisti")));			
		}
		return false;
	}
	
	protected ControlListGridField buildRiepilogoFirmeVistiButtonField() {
		
		ControlListGridField riepilogoFirmeVistiButtonField = new ControlListGridField("riepilogoFirmeVistiButton");
		riepilogoFirmeVistiButtonField.setAttribute("custom", Boolean.TRUE);
		riepilogoFirmeVistiButtonField.setShowHover(Boolean.TRUE);
		riepilogoFirmeVistiButtonField.setCanReorder(Boolean.FALSE);
		riepilogoFirmeVistiButtonField.setCellFormatter(new CellFormatter() {
	
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					return buildImgButtonHtml("file/attestato.png");
				} 
				return null;
			}
		});
		riepilogoFirmeVistiButtonField.setHoverCustomizer(new HoverCustomizer() {
	
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {						
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					return "Foglio riepilogo";
				} 
				return null;
			}
		});
		riepilogoFirmeVistiButtonField.addRecordClickHandler(new RecordClickHandler() {
	
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = event.getRecord();									
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					Menu menu = buildRiepilogoFirmeVistiMenu(record);
					menu.showContextMenu();
				}
			}
		});		
		return riepilogoFirmeVistiButtonField;
	}
	
	public Menu buildRiepilogoFirmeVistiMenu(final Record record) {
		final Menu riepilogoFirmeVistiMenu = new Menu();
		if (record.getAttribute("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("uriRiepilogoFirmeVisti"))) {
			MenuItem visualizzaRiepilogoFirmeVistiMenuItem = new MenuItem("Visualizza", "file/preview.png");
			visualizzaRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {					
					final String uriRiepilogoFirmeVisti = record.getAttribute("uriRiepilogoFirmeVisti");
					final String nomeFileRiepilogoFirmeVisti = record.getAttribute("nomeFileRiepilogoFirmeVisti");					
					Record lRecordFileRiepilogoFirmeVisti = new Record();
					lRecordFileRiepilogoFirmeVisti.setAttribute("uri", uriRiepilogoFirmeVisti);
					lRecordFileRiepilogoFirmeVisti.setAttribute("nomeFile", nomeFileRiepilogoFirmeVisti);
					new GWTRestDataSource("ProtocolloDataSource").executecustom("getInfoFromFile", lRecordFileRiepilogoFirmeVisti, new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record result = dsResponse.getData()[0];
								InfoFileRecord infoFileRiepilogoFirmeVisti = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
								PreviewControl.switchPreview(uriRiepilogoFirmeVisti, false, infoFileRiepilogoFirmeVisti, "FileToExtractBean", nomeFileRiepilogoFirmeVisti);					
							}
						}
					});		
				}
			});
			riepilogoFirmeVistiMenu.addItem(visualizzaRiepilogoFirmeVistiMenuItem);
		}		
		if (record.getAttribute("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("idDocRiepilogoFirmeVisti"))) {
			MenuItem rigeneraRiepilogoFirmeVistiMenuItem = new MenuItem("Rigenera", "protocollazione/generaDaModello.png");
			rigeneraRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idUd = record.getAttribute("idUd");
					final String idDocRiepilogoFirmeVisti = record.getAttribute("idDocRiepilogoFirmeVisti");
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", record.getAttribute("idProcedimento"));
					lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", null); // se non lo passo in teoria carica l'ultimo task
					if (idUd != null && !idUd.equalsIgnoreCase("")) {
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", idUd);
						lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record detailRecord = response.getData()[0];
									detailRecord.setAttribute("idModello", record.getAttribute("idModRiepilogoFirmeVisti") != null ? record.getAttribute("idModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("nomeModello", record.getAttribute("nomeModRiepilogoFirmeVisti") != null ? record.getAttribute("nomeModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("displayFilenameModello", record.getAttribute("displayFilenameModRiepilogoFirmeVisti") != null ? record.getAttribute("displayFilenameModRiepilogoFirmeVisti") : "");
									generaRiepilogoFirmeVistiDaModello(detailRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(final Record recordPreview) {
											previewWithCallback(recordPreview, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {													
													Record recordToVers = new Record();
													recordToVers.setAttribute("idFile", idDocRiepilogoFirmeVisti);
													recordToVers.setAttribute("uri", recordPreview.getAttributeAsString("uri"));
													recordToVers.setAttribute("nomeFile", recordPreview.getAttributeAsString("nomeFile"));		
													recordToVers.setAttribute("infoFile", recordPreview.getAttributeAsRecord("infoFile"));		
													Layout.showWaitPopup("Salvataggio file in corso...");				
													lNuovaPropostaAtto2CompletaDataSource.executecustom("versionaDocumento", recordToVers, new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															Layout.hideWaitPopup();
															if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																onClickRefreshListButton();
															}									
														}		
													});
												}
											});
											
											
										}
									});
								}
							}
						});				
					}					
				}
			});
			riepilogoFirmeVistiMenu.addItem(rigeneraRiepilogoFirmeVistiMenuItem);
		}		
		return riepilogoFirmeVistiMenu;
	}
	
	public void generaRiepilogoFirmeVistiDaModello(Record record, final ServiceCallback<Record> callback) {

		Layout.showWaitPopup("Generazione riepilogo firme e visti in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("generaRiepilogoFirmeVistiDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	protected ControlListGridField buildFileAssociatiButtonField() {
		
		ControlListGridField fileAssociatiButtonField = new ControlListGridField("fileAssociatiButton");  
		fileAssociatiButtonField.setAttribute("custom", true);	
		fileAssociatiButtonField.setShowHover(true);		
		fileAssociatiButtonField.setCanReorder(false);
		fileAssociatiButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {							
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
					return buildImgButtonHtml("archivio/file.png");
				}
				return null;
			}
		});
		fileAssociatiButtonField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
					return "File";
				}
				return null;
			}
		});		
		fileAssociatiButtonField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {						
					GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
					lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", event.getRecord().getAttributeAsString("idUd"));
					lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
								final Record detailRecord = response.getData()[0];	
								final Menu fileAssociatiMenu = buildFileAssociatiMenu(detailRecord);
								fileAssociatiMenu.showContextMenu();
							}
						}
					});
				}
			}
		});		
		return fileAssociatiButtonField;
	}
	
	public Menu buildFileAssociatiMenu(final Record detailRecord) {
		
		Menu fileAssociatiMenu = new Menu();
		fileAssociatiMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		
		Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
		
		//File primario senza omissis
		if ((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") == null) || "".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiMenu.addItem(filePrimarioIntegraleMenuItem);
			
		}
			
		//File primario con versione omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			//File primario integrale
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario (vers. integrale) - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiMenu.addItem(filePrimarioIntegraleMenuItem);
			
			//File primario omissis
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " +filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//File primario solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") == null
				|| "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " + filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//Aggiungo al menu gli allegati
		RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
		
		if (listaAllegati != null) {
			for (int n = 0; n < listaAllegati.getLength(); n++) {
				final int nroAllegato = n;
				final Record allegatoRecord = listaAllegati.get(n);
				
				boolean flgParteDispositivo = allegatoRecord.getAttribute("flgParteDispositivo") != null && "true".equals(allegatoRecord.getAttribute("flgParteDispositivo"));					
				
				//Allegato senza omissis
				if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true);
					fileAssociatiMenu.addItem(fileAllegatoIntegraleMenuItem);
				}
				
				//Entrambi versioni di allegati
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					//Allegato integrale
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true);
					fileAssociatiMenu.addItem(fileAllegatoIntegraleMenuItem);
					
					//Alegato omissis
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiMenu.addItem(fileAllegatoOmissisMenuItem);
				}
				
				//Allegato solo omissis
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiMenu.addItem(fileAllegatoOmissisMenuItem);
				}
			}
		}
		
		return fileAssociatiMenu;
	}

	private void buildFilePrimarioMenuItem(final Record detailRecord, MenuItem filePrimarioMenuItem, final boolean fileIntegrale) {
		
		Menu operazioniFilePrimarioSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//file primario integrale
		if(fileIntegrale){
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
		}
		//file primario omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsRecord("filePrimarioOmissis").getAttributeAsObject("infoFile"));
		}
		
		MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
			@Override
			public void onClick(MenuItemClickEvent event) {
				if(fileIntegrale) {
					String idUd = detailRecord.getAttributeAsString("idUd");
					String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
					String display = detailRecord.getAttributeAsString("nomeFilePrimario");
					String uri = detailRecord.getAttributeAsString("uriFilePrimario");
					String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
					Object infoFile = detailRecord.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
				else {
					String idUd = detailRecord.getAttributeAsString("idUd");
					final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
					String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
					String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
					String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
					String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
					Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
			}
		});
		visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
					"file/previewEdit.png");
			visualizzaEditFilePrimarioMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(fileIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
								String display = detailRecord.getAttributeAsString("nomeFilePrimario");
								String uri = detailRecord.getAttributeAsString("uriFilePrimario");
								String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
								Object infoFile = detailRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
								String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
								String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
								String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
								String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
								Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFilePrimarioMenuItem
					.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);
		}
		
		MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFilePrimarioSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						Object infoFile = detailRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
			scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
		} else {
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
		}
		operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);
	
		filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
	}
		
	private void buildAllegatiMenuItem(final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
		
		Menu operazioniFileAllegatoSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFileAllegatoMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						//se è un allegato integrale
						if(allegatoIntegrale) {
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
							String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
						//versione con omissis
						else{
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
							String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
					}
				});
		visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
			visualizzaEditFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							// se è un allegato integrale
							if (allegatoIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							// versione con omissis
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
		}
		
		MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFileAllegatoSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
			scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
		} else {
			scaricaFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(allegatoIntegrale) {
								//se è un allegato integrale
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
							//versione con omissis
							else{
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
						}
					});
		}
		operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);
	
		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
	}
	
	protected boolean isRecordAbilToScaricaFileCompletiXAtti(ListGridRecord record) {
		if(AurigaLayout.isPrivilegioAttivo("ATT/SDC")) {
			return record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"));			
		}
		return false;
	}
	
	protected ControlListGridField buildScaricaFileCompletiXAttiButtonField() {
		
		ControlListGridField scaricaFileCompletiXAttiButtonField = new ControlListGridField("scaricaFileCompletiXAttiButton");  
		scaricaFileCompletiXAttiButtonField.setAttribute("custom", true);	
		scaricaFileCompletiXAttiButtonField.setShowHover(true);		
		scaricaFileCompletiXAttiButtonField.setCanReorder(false);
		scaricaFileCompletiXAttiButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToScaricaFileCompletiXAtti(record)) {
					return buildImgButtonHtml("buttons/download_zip.png");
				}
				return null;
			}
		});
		scaricaFileCompletiXAttiButtonField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(isRecordAbilToScaricaFileCompletiXAtti(record)) {
					return "Scarica documentazione completa";
				}
				return null;
			}
		});		
		scaricaFileCompletiXAttiButtonField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord listRecord = event.getRecord();
				if(isRecordAbilToScaricaFileCompletiXAtti(listRecord)) {
					GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
					lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", listRecord.getAttributeAsString("idUd"));
					lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
								final Record detailRecord = response.getData()[0];														
								// devo fare uno zip con tutti i file: testo, allegati, foglio riepilogo, emandamenti e pareri emedamento
								final GWTRestDataSource datasource = new GWTRestDataSource("ProtocolloDataSource");
								if(detailRecord.getAttribute("segnatura") != null && !"".equals(detailRecord.getAttribute("segnatura"))) {
									datasource.extraparam.put("segnatura", detailRecord.getAttribute("segnatura"));
								} else {
									datasource.extraparam.put("segnatura", listRecord.getAttribute("estremiPropostaUD"));
								}
								datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
								datasource.extraparam.put("operazione", "scaricaCompletiXAtti");
								datasource.setForceToShowPrompt(false);
								
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
								datasource.executecustom("generateDocsZip", detailRecord, new DSCallback() {
				
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {		
											Record result = response.getData()[0];
											String message = result.getAttribute("message");
											if (message != null) {
												Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
											} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
												String zipUri = response.getData()[0].getAttribute("storageZipRemoteUri");
												String zipName = response.getData()[0].getAttribute("zipName");							
												scaricaFile(listRecord.getAttribute("idUdFolder"), "", zipName, zipUri, zipUri);															
											}
										}
									}
								});
							}
						}
					});													
				}
			}
		});		
		return scaricaFileCompletiXAttiButtonField;
	}
	
	protected ControlListGridField buildRemoveButtonField() {
		
		ControlListGridField removeButtonField = new ControlListGridField("removeButton");  
		removeButtonField.setAttribute("custom", true);	
		removeButtonField.setShowHover(true);		
		removeButtonField.setCanReorder(false);
		removeButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return buildImgButtonHtml("buttons/remove.png");
			}
		});
		removeButtonField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				return "Rimuovi";
			}
		});		
		removeButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				onClickRemoveButton(event.getRecord());
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
		onClickRemoveButton(record, null);
	}
	
	public void onClickRemoveButton(final ListGridRecord record, final ServiceCallback<Record> callback) {
		
		if(AurigaLayout.getParametroDB("MODALITA_ELIM_ARG_ODG_ORG_COLL") != null &&
		   !"".equalsIgnoreCase(AurigaLayout.getParametroDB("MODALITA_ELIM_ARG_ODG_ORG_COLL"))) {
			
			if("ASK".equalsIgnoreCase(AurigaLayout.getParametroDB("MODALITA_ELIM_ARG_ODG_ORG_COLL"))) {
				SC.ask("Vuoi che l'argomento/proposta/atto sia riproposto nella prossima seduta (SOLO quando l'iter è concluso) ?", 
						new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(value) {
									record.setAttribute("flgElimina", "1");
									instance.removeData(record);
									if(attivoOrdNumOdgXTipoSeduta()) {
										refreshNroOrdineGiorno();
									}
									if(callback != null) {
										callback.execute(record);
									}
								} else  {
									record.setAttribute("flgElimina", "2");
									instance.removeData(record);
									if(attivoOrdNumOdgXTipoSeduta()) {
										refreshNroOrdineGiorno();
									}
									if(callback != null) {
										callback.execute(record);
									}
								}
							}
						});
			} else if("DEF".equalsIgnoreCase(AurigaLayout.getParametroDB("MODALITA_ELIM_ARG_ODG_ORG_COLL"))) {
				SC.ask("Sei sicuro di voler eliminare l'argomento/proposta/atto dalla seduta ?", 
						new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(value) {
									record.setAttribute("flgElimina", "2");
									instance.removeData(record);
									if(attivoOrdNumOdgXTipoSeduta()) {
										refreshNroOrdineGiorno();
									}
									if(callback != null) {
										callback.execute(record);
									}
								}
							}
						});
			} else if("SOLO_SEDUTA".equalsIgnoreCase(AurigaLayout.getParametroDB("MODALITA_ELIM_ARG_ODG_ORG_COLL"))) {
				SC.ask("Sei sicuro di voler eliminare l'argomento/proposta/atto dalla seduta ?", 
						new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if(value) {
									record.setAttribute("flgElimina", "1");
									instance.removeData(record);
									if(attivoOrdNumOdgXTipoSeduta()) {
										refreshNroOrdineGiorno();
									}
									if(callback != null) {
										callback.execute(record);
									}
								} 
							}
						});
			}
		} else {	
			SC.ask("Sei sicuro di voler eliminare l'argomento/proposta/atto dalla seduta ?", 
					new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								record.setAttribute("flgElimina", "1");
								instance.removeData(record);
								if(attivoOrdNumOdgXTipoSeduta()) {
									refreshNroOrdineGiorno();
								}
								if(callback != null) {
									callback.execute(record);
								}
							} 
						}
					});
		}
		
	}
	
	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {

					}
				};
				previewDocWindow.show();
			}
		});
	}

	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
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
	
	public void aggiungiProposta() {
		String idNodoRicerca = getIdNodoRicerca();
		AttiOdgMultiLookupArchivio lookupMultiplaArchivio = new AttiOdgMultiLookupArchivio(null, idNodoRicerca);				
		lookupMultiplaArchivio.show(); 
	}
	
	public void nuovoAttoArgomento() {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("LoadComboAttoConFlussoWFDataSource");
		lGWTRestDataSource.addParam("organoCollegiale", organoCollegiale);
		lGWTRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record[] data = response.getData();
				if (data.length > 0) {
					SelezionaTipoAttoWindow lSelezionaTipoAttoWindow = new SelezionaTipoAttoWindow(getIdSeduta(), organoCollegiale,
						"CONVOCAZIONE", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								
								onClickRefreshListButton();
							}
						 });
					lSelezionaTipoAttoWindow.show();
				} else {
					Layout.addMessage(new MessageBean("Nessun tipo atto disponibile", "", MessageType.ERROR));
				}
			}
		});

	}
	
	private String getIdNodoRicerca() {
		return "/";
	}
	
	public String getFinalitaAttiOdgLookupArchivio() {
		String finalita = "IMPORT_ATTI_IN_ODG_" + organoCollegiale.toUpperCase();
		if (codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			finalita += "_CIRC_" + codCircoscrizione; 
		}
		return finalita;
	}
	
	public class AttiOdgMultiLookupArchivio extends LookupArchivioPopup {

		private RecordList multiLookupList = new RecordList(); 

		public AttiOdgMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		public AttiOdgMultiLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, false);
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAttiOdgLookupArchivio();
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			
			Record recordToInsert = new Record();
			
			RecordList listaRecordOdgPresenti = instance.getData();
			if(attivoOrdNumOdgXTipoSeduta()) {
				int nrOrdineOdgMax = 0;
				if (listaRecordOdgPresenti != null && !listaRecordOdgPresenti.isEmpty()) {
					for (int i = 0; i < listaRecordOdgPresenti.getLength(); i++) {
						Record recordOdg = listaRecordOdgPresenti.get(i);
						int nrOrdineOdg = recordOdg.getAttribute("nrOrdineOdg") != null ? Integer.parseInt(recordOdg.getAttribute("nrOrdineOdg")): 0;
						if (nrOrdineOdg > nrOrdineOdgMax) {
							nrOrdineOdgMax = nrOrdineOdg;
						}
					}
				}
				int nrOrdineOdg = nrOrdineOdgMax + 1;
				recordToInsert.setAttribute("nrOrdineOdg", "" + nrOrdineOdg);
			}
			recordToInsert.setAttribute("idUd", record.getAttribute("idUdFolder"));
			recordToInsert.setAttribute("tipo", record.getAttribute("tipo"));
			recordToInsert.setAttribute("estremiPropostaUD", record.getAttribute("segnatura"));
			recordToInsert.setAttribute("oggetto", record.getAttribute("oggetto"));
			recordToInsert.setAttribute("flgAggiunto", "1");
			// Devo mettere a 0 tutti flag non valorizzati che nel bean sono mappati come Integer altrimenti va in errore il salvataggio 
			recordToInsert.setAttribute("flgCanRemove", "1");
			multiLookupList.add(recordToInsert);
			instance.addData(recordToInsert);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			String idUdToRemove = record.getAttribute("id") != null ? record.getAttribute("id") : "";
			int posToRemove = -1;
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.getLength(); i++) {
					if (idUdToRemove.equalsIgnoreCase(multiLookupList.get(i).getAttribute("idUd"))) {
						posToRemove = i;
					}
				}
				if (posToRemove > -1) {
					multiLookupList.removeAt(posToRemove);
				}
				
				posToRemove = -1;
				if (instance.getData() != null) {
					RecordList listaOdg = instance.getData();
					for (int i = 0; i < listaOdg.getLength(); i++) {
						Record odg = listaOdg.get(i);
						if (odg.getAttribute("idUd") != null && idUdToRemove.equalsIgnoreCase(odg.getAttribute("idUd")) && odg.getAttribute("flgCanRemove") != null && "1".equalsIgnoreCase(odg.getAttribute("flgCanRemove"))) {
							posToRemove = i;
						}
					}
					
					if (posToRemove > -1) {
						listaOdg.removeAt(posToRemove);
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		
		}
	}
	
	protected Boolean isOdGChiuso() {
		return getStatoSeduta() != null && "OdG_chiuso".equalsIgnoreCase(getStatoSeduta());
	}

	public Boolean showButtons() {
		return getIdSeduta() != null && !"".equalsIgnoreCase(getIdSeduta());
	}
	
	private void refreshNroOrdineGiorno() {
		grid.deselectAllRecords();
		int n = 1;
		for (ListGridRecord record : grid.getRecords()) {
			record.setAttribute("nrOrdineOdg", "" + n);
			n++;
		}
		updateGridItemValue();
	}
	
	private boolean attivoOrdNumOdgXTipoSeduta() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_GNT")) {
				return false;
			} else {
				return true;
			}
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNS")) {
				return false;
			} else {
				return true;
			}
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMM")) {
				return false;
			} else {
				return true;
			}
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMG")) {
				return false;
			} else {
				return true;
			}
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_OPR")) {
				return false;
			} else {
				return true;
			}
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNF")) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	private void setTypeNrOrdineOdg(ListGridField nrOrdineOdg) {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_GNT")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNS")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMM")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMG")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_OPR")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNF")) {
				nrOrdineOdg.setType(ListGridFieldType.INTEGER);
			}
		}
	}
	
	private void setTypeNrOrdUltimoOdgCons(ListGridField nrOrdUltimoOdgCons) {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_GNT")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNS")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMM")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CMG")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_OPR")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			if(!AurigaLayout.getParametroDBAsBoolean("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNF")) {
				nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);
			}
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("aggiunta_proposta")) {
					if(showButtons() && !fromStoricoSeduta()) {
						member.show();	
					} else {
						member.hide();						
					}
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("aggiunta_atto")) {
					if(showButtons() && !fromStoricoSeduta()) {
						member.show();	
					} else {
						member.hide();						
					}		
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("ricarica")) {
					if(showButtons()) {
						member.show();	
					} else {
						member.hide();						
					}		
				}
			}		
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}
	
	public String getIdSeduta() {
		return null;
	}
	
	public String getStatoSeduta() {
		return null;
	}
	
	public boolean fromStoricoSeduta() {
		return false;
	}
	 
}