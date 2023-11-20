/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioEmailWindow;

import it.eng.auriga.ui.module.layout.client.postaElettronica.VisualizzaContenutoHeaderWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.List;


import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PostaInUscitaRegistrazioneList extends CustomList {
	
	private ListGridField id;
	private ListGridField tsInvioClientField;
	private ListGridField tsInvioField;
	private ListGridField accountMittenteField;
	private ListGridField destinatariField;
	private ListGridField oggettoField;
	private ListGridField statoInvioField;
	private ListGridField statoAccettazioneField;
	private ListGridField statoConsegnaField;
	private ListGridField flgEmailCertificataField;
	private ListGridField flgInteroperabileField;
	private ListGridField statoConsolidamentoField;
	private ListGridField idEmailField;
	private ListGridField flgIoField;
	private ListGridField categoriaField;
	
	public PostaInUscitaRegistrazioneList(String nomeEntita) {
		
		super(nomeEntita);
		
		buildIdField();
		
		// Colonne nascoste
		idEmailField   = new ListGridField("idEmail");   idEmailField.setHidden(true);	   idEmailField.setCanHide(false);
		flgIoField     = new ListGridField("flgIo");     flgIoField.setHidden(true);       flgIoField.setCanHide(false);
		categoriaField = new ListGridField("categoria"); categoriaField.setHidden(true);   categoriaField.setCanHide(false);
		
		// Colonne visbili
		
		// Inviata il (dal client)
		tsInvioClientField = new ListGridField("tsInvioClient", I18NUtil.getMessages().postaElettronica_list_tsInvioClientField_title()); 
		tsInvioClientField.setType(ListGridFieldType.DATE); 
		tsInvioClientField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME); 
		tsInvioClientField.setWrap(false);
		
		
		// Inviata il 
		tsInvioField = new ListGridField("tsInvio", I18NUtil.getMessages().postaElettronica_list_tsInvioField_title()); 
		tsInvioField.setType(ListGridFieldType.DATE); 
		tsInvioField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME); 
		tsInvioField.setWrap(false);

		
		// "Da"
		accountMittenteField = new ListGridField("accountMittente", I18NUtil.getMessages().postaElettronica_list_accountMittenteField_title()); 
		accountMittenteField.setWrap(false);
		
		// "A"
		destinatariField = new ListGridField("destinatari", I18NUtil.getMessages().postaElettronica_list_destinatariField_title()); 
		destinatariField.setCellAlign(Alignment.LEFT); 
		destinatariField.setWrap(false);
		
		// Oggetto		
		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().postaElettronica_list_oggettoField_title()); 
		oggettoField.setWrap(false);
		
		oggettoField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				String body = event.getRecord().getAttribute("oggetto");
				Record record = new Record();
				record.setAttribute("message", body);
				String idMail = event.getRecord().getAttribute("id") != null ? event.getRecord().getAttribute("id") : "";
				String tipologia = event.getRecord().getAttribute("tipoEmail") != null ? event.getRecord().getAttribute("tipoEmail") : "";
				String estremiEmail = "Dettaglio oggetto mail: "+idMail +" "+tipologia;
				record.setAttribute("estremiEmail", estremiEmail);
				new VisualizzaContenutoHeaderWindow("visualizza_contenuto_campi_email",record);
			}
		});
				
		// Stato Invio
		statoInvioField = new ListGridField("statoInvio", I18NUtil.getMessages().posta_elettronica_list_statoInvioField()); 
		statoInvioField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton); 
		statoInvioField.setType(ListGridFieldType.ICON); 
		statoInvioField.setAlign(Alignment.CENTER); 
		statoInvioField.setWrap(false); 
		statoInvioField.setWidth(30); 
		statoInvioField.setIconWidth(16); 
		statoInvioField.setIconHeight(16);
		statoInvioField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoInvio = (String) record.getAttribute("statoInvio");
					return getIconaStatoConsolidamento(statoInvio);
				}
		});
		statoInvioField.setHoverCustomizer(new HoverCustomizer() {
				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					return (String) record.getAttribute("descrizioneStatoInvio");
				}
		});
		
		// Stato Accettazione
		statoAccettazioneField = new ListGridField("statoAccettazione", I18NUtil.getMessages().posta_elettronica_list_statoAccettazioneField());
		statoAccettazioneField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoAccettazioneField.setType(ListGridFieldType.ICON);
		statoAccettazioneField.setAlign(Alignment.CENTER);
		statoAccettazioneField.setWrap(false);
		statoAccettazioneField.setWidth(30);
		statoAccettazioneField.setIconWidth(16);
		statoAccettazioneField.setIconHeight(16);
		statoAccettazioneField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoAccettazione = (String) record.getAttribute("statoAccettazione");
						return getIconaStatoConsolidamento(statoAccettazione);
				}
		});
		statoAccettazioneField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("descrizioneStatoAccettazione");
			}
		});

		// Stato Consegna
		statoConsegnaField = new ListGridField("statoConsegna", I18NUtil.getMessages().posta_elettronica_list_statoConsegnaField());
		statoConsegnaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsegnaField.setType(ListGridFieldType.ICON);
		statoConsegnaField.setAlign(Alignment.CENTER);
		statoConsegnaField.setWrap(false);
		statoConsegnaField.setWidth(30);
		statoConsegnaField.setIconWidth(16);
		statoConsegnaField.setIconHeight(16);
		statoConsegnaField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String statoConsegna = (String) record.getAttribute("statoConsegna");
				return getIconaStatoConsolidamento(statoConsegna);
			}
		});
		statoConsegnaField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("descrizioneStatoConsegna");
			}
		});

		// Stato consolidamento
		statoConsolidamentoField = new ListGridField("statoConsolidamento", I18NUtil.getMessages().postainuscitaregistrazione_list_statoConsolidamentoField_title());
		statoConsolidamentoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsolidamentoField.setType(ListGridFieldType.ICON);
		statoConsolidamentoField.setAlign(Alignment.CENTER);
		statoConsolidamentoField.setWrap(false);		
		statoConsolidamentoField.setWidth(30);
		statoConsolidamentoField.setIconWidth(16);
		statoConsolidamentoField.setIconHeight(16);		
		statoConsolidamentoField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				String statoConsolidamento = (String) value;
				if(statoConsolidamento != null && !"".equals(statoConsolidamento)) {											
					return "<div align=\"center\"><img src=\"images/postaElettronica/statoConsolidamento/"+  statoConsolidamento + ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
				}
				return null;								
			}
		});
		statoConsolidamentoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {								
				String statoConsolidamento = (String) record.getAttribute("statoConsolidamento");
				if(statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					statoConsolidamento = statoConsolidamento.toString().replaceAll("_", " ");
					return statoConsolidamento;
				}
				return null;	
			}
		});
		statoConsolidamentoField.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {				
				event.cancel();
				String statoConsolidamento = (String) event.getRecord().getAttribute("statoConsolidamento");
				if(statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					manageRicevutePostaInUscitaClick(event.getRecord());
				}
			}
		});
				
		// Mail certificata
		flgEmailCertificataField = new ListGridField("flgEmailCertificata", I18NUtil.getMessages().posta_elettronica_list_flgEmailCertificataField());
		flgEmailCertificataField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgEmailCertificataField.setType(ListGridFieldType.ICON);
		flgEmailCertificataField.setAlign(Alignment.CENTER);
		flgEmailCertificataField.setWrap(false);
		flgEmailCertificataField.setWidth(30);
		flgEmailCertificataField.setIconWidth(16);
		flgEmailCertificataField.setIconHeight(16);
		flgEmailCertificataField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgEmailCertificata = (String) record.getAttribute("flgEmailCertificata");
				if (flgEmailCertificata != null && "1".equals(flgEmailCertificata)) {
					return buildIconHtml("coccarda.png");
				}
				return null;
			}
		});
		flgEmailCertificataField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Mitt. certificato";
			}
		});
		
		// Interoperabile
		flgInteroperabileField = new ListGridField("flgInteroperabile", I18NUtil.getMessages().posta_elettronica_list_flgInteroperabileField());
		flgInteroperabileField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInteroperabileField.setType(ListGridFieldType.ICON);
		flgInteroperabileField.setAlign(Alignment.CENTER);
		flgInteroperabileField.setWrap(false);
		flgInteroperabileField.setWidth(30);
		flgInteroperabileField.setIconWidth(16);
		flgInteroperabileField.setIconHeight(16);
		flgInteroperabileField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInteroperabile = (String) record.getAttribute("flgInteroperabile");
				if (flgInteroperabile != null && "1".equals(flgInteroperabile)) {
					return buildIconHtml("postaElettronica/interoperabile.png");
				}
				return null;
			}
		});
		flgInteroperabileField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Interoperabile";
			}
		});
				
		setFields(new ListGridField[] { 
				                        // Visibili
				                        id,
										tsInvioClientField,
										tsInvioField,
										accountMittenteField,
										destinatariField,
										oggettoField,
										statoInvioField,
										statoAccettazioneField,
										statoConsegnaField,
										flgEmailCertificataField,
										flgInteroperabileField,
										statoConsolidamentoField,
										
										// Nascoste
										idEmailField,
										flgIoField,
										categoriaField
		});  		
	}
	
	protected void manageRicevutePostaInUscitaClick(Record pRecord) {
		final String idEmailRif = pRecord.getAttribute("idEmail");
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RicevutePostaInUscitaDataSource", "idEmail", FieldType.TEXT);
		lGWTRestDataSource.addParam("idEmailRif", idEmailRif);
		lGWTRestDataSource.fetchData(null, new DSCallback() {					
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				RecordList data = response.getDataAsRecordList();   						
				if(data.getLength() == 0) {							
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().ricevutePostaInUscitaWindow_empty_message(), "", MessageType.INFO));							
				} else if(data.getLength() == 1) {		                	
		        	RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(data.get(0));						  				    						    					                			                
		        } else if(data.getLength() > 0) {		                	
		        	RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(idEmailRif);						        		    				
		        }
			}
		});			
	}
		
	/**
	 * Inizializza la colonna contenente l'id della mail
	 */
	protected void buildIdField() {

		id = new ListGridField("progrOrdinamento", I18NUtil.getMessages().postaElettronica_list_messageProgressivoField_title());
		id.setAttribute("custom", true);
		id.setCellAlign(Alignment.RIGHT);
		id.setSortByDisplayField(false);
		id.setDisplayField("id");
		id.setCanGroupBy(false);
		id.setCanReorder(false);
		id.setCanFreeze(false);
		id.setShowDefaultContextMenu(false);
		id.setCanDragResize(true);
		
		id.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageDetailButtonClick(record);
			}
		});
	}

	@Override
	protected void manageDetailButtonClick(final ListGridRecord record) {
		final String classifica = getLayout().getFilter().getExtraParam().get("classifica");
		String flgIo = record.getAttributeAsString("flgIo");
		final String idEmail = record.getAttributeAsString("idEmail");
		final String tipoRel = record.getAttributeAsString("tipoRel");
		final Record lRecord = new Record();
		lRecord.setAttribute("idEmail", idEmail);
		lRecord.setAttribute("flgIo", flgIo);
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_dettaglio_mail());

		if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaAbilitazioniEmailDataSource");
			lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, tipoRel, classifica, record, object, getLayout());
				}
			});
		} else {
			DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, tipoRel, classifica, record, null, getLayout());
		}
	}
	
	private String getIconaStatoConsolidamento(String stato) {
		if (stato != null && "OK".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/consegnata.png");
		}

		if (stato != null && "IP".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/presunto_ok.png");
		}

		if (stato != null && "W".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-arancione.png");
		}

		if (stato != null && "KO".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-red.png");
		}

		if (stato != null && "ND".equals(stato)) {
			return buildIconHtml("postaElettronica/statoConsolidamento/stato_consegna.png");
		}
		return null;
	}	
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("progrOrdinamento")) {
				if (record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {
					return it.eng.utility.Styles.cellTextOrangeClickable;
				} else if (record.getAttribute("flgIo") != null) {
					if (record.getAttribute("flgIo").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
								|| record.getAttribute("categoria").equalsIgnoreCase("PEC") || record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA")
								|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellTextGreenClickable;
						} else {
							return it.eng.utility.Styles.cellTextGreyClickable;
						}
					} else if (record.getAttribute("flgIo").equalsIgnoreCase("O")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) {
							return it.eng.utility.Styles.cellTextGreyClickable;
						} else
							return it.eng.utility.Styles.cellTextBlueClickable;
					}
				}
			} else {
				if (record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {
					return it.eng.utility.Styles.cellOrange;
				} else if (record.getAttribute("flgIo") != null) {
					if (record.getAttribute("flgIo").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
								|| record.getAttribute("categoria").equalsIgnoreCase("PEC") || record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA")
								|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellGreen;
						} else {
							return it.eng.utility.Styles.cellGrey;
						}
					} else if (record.getAttribute("flgIo").equalsIgnoreCase("O")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG")
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) {
							return it.eng.utility.Styles.cellGrey;
						}
						return it.eng.utility.Styles.cellBlue;
					}
				}
			}
			return super.getBaseStyle(record, rowNum, colNum);
		} catch (Exception e) {
			return super.getBaseStyle(record, rowNum, colNum);
		}
	}
	
	
	@Override
	protected boolean showDetailButtonField() {
		return false;
	}	
	
	@Override
	protected boolean showModifyButtonField() {
		return false;
	}	
	
	@Override
	protected boolean showDeleteButtonField() {
		return false;
	}	
	
	@Override
	protected boolean showAltreOpButtonField() {
		return false;
	}	
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		return buttonsFields;
	}
	
	
	@Override
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
		return value != null && !"".equals(value) ? dateFormat.parse(value) : null;
	}

	@Override
	public Boolean sort() {
		return super.sort();
	}
	
}
