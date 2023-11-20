/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * Estensione specifica della CustomList per visualizzare le mail correlate di una mail in entrata
 *
 */
//@TODO esiste una dipendenza forte verso DettaglioPostaElettronica, in prima battuta può andare bene, 
//ma sarebbe poi necessario rifattorizzare implementando dei setter in maniera tale che dalla classe chiamante si possano impostare i comportamenti voluti
//oppure utilizzare un sistema ad eventi per disacoppiare le classi
public class MailEntrataMailCorrelateList extends CustomList {

	private DettaglioPostaElettronica dettaglioPostaElettronica;

	public MailEntrataMailCorrelateList(DettaglioPostaElettronica dettaglioPostaElettronica) {
		
		super("listaEmailCollegateMailEntrata");
		
		this.dettaglioPostaElettronica = dettaglioPostaElettronica;
		
		initialize();
	}

	/**
	 * Inizializza l'istanza, ovvero crea i ListGridField che dovranno essere mostrati
	 */
	protected void initialize() {

		ListGridField progressivo = buildProgressivoField();
		
		ListGridField idEmail = buildIdMailField();
				
		ListGridField iconaMicroCategoriaMail = buildIconaMicroCatField();
		//MARINA MODIFICA
		//ListGridField tipoMail  = new ListGridField("tipo","Tipo"); 
		ListGridField tipoMail  = new ListGridField("tipo",I18NUtil.getMessages().postaElettronica_list_tipoMail());
		//MARINA MODIFICA 
		//ListGridField sottoTipoMail  = new ListGridField("sottotipo","Sotto-tipo"); 
		ListGridField sottoTipoMail  = new ListGridField("sottotipo",I18NUtil.getMessages().postaElettronica_list_sottoTipoMail()); 
		//MARINA MODIFICA
		//ListGridField tsInvioMail  = new ListGridField("tsInvio","Inviata il");
		ListGridField tsInvioMail  = new ListGridField("tsInvio",I18NUtil.getMessages().postaElettronica_list_tsInvioMail());
		//Cacco Federico 12-10-2015 Settato typo colonna per correggere l'ordinamento
		tsInvioMail.setType(ListGridFieldType.DATE);
		//MARINA MODIFICA
		//ListGridField cognomeNomeMittMail  = new ListGridField("cognomeNomeMitt","Invio effettuato da");
		ListGridField cognomeNomeMittMail  = new ListGridField("cognomeNomeMitt",I18NUtil.getMessages().postaElettronica_list_cognomeNomeMittMail());
		//MARINA MODIFICA
		//ListGridField indirizzoMittMail  = new ListGridField("indirizzoMitt","Mitt.");
		ListGridField indirizzoMittMail  = new ListGridField("indirizzoMitt",I18NUtil.getMessages().postaElettronica_list_indirizzoMittMail());
		
		//ListGridField statoConsolidamentoMail = statoConsolidamentoField();
		
		ListGridField flgPECMail = buildFlagPec();
		
		ListGridField flgInteroperabileMail = buildFlagInteroperabileField();		

		//ListGridField contrassegnoMail = buildContrassegnoField();
		
		ListGridField statoInvioMail = buildStatoInvioField();
		ListGridField statoAccettazioneMail = buildStatoAccettazioneField();
		ListGridField statoConsegnaMail = buildStatoConsegnaField();
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Space") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						manageDetailButtonClick(record);
	//                    System.out.println("ENTER PRESSED !!!!" + listGrid.getSelectedRecord());
	                }
	            }
	        });		
		}
		setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		setFields(
				progressivo,
				idEmail,
				/*flgIOMail,*/
				iconaMicroCategoriaMail,
				flgPECMail,
				flgInteroperabileMail,
				tipoMail,
				sottoTipoMail,
				tsInvioMail,
				cognomeNomeMittMail,
				indirizzoMittMail,
				//statoConsolidamentoMail,
				//statoLavorazioneMail,
				//contrassegnoMail,
				statoInvioMail,
				statoAccettazioneMail,
				statoConsegnaMail
				);
		setSortField("tsInvio");
		setSortDirection(SortDirection.DESCENDING);
		setCanExpandRecords(true);
		setWidth100();
		setHeight100();
		setSelectionType(SelectionStyle.NONE);
		setBaseStyle(it.eng.utility.Styles.newTallCell);
		setCanGroupBy(false);	
		
		setShowHeaderContextMenu(false);		
	}

	private ListGridField buildStatoConsegnaField() 
	{	//MARINA MODIFICA
		//ListGridField statoConsegnaField = new ListGridField("statoConsegna","Stato Consegna");
		ListGridField statoConsegnaField = new ListGridField("statoConsegna",I18NUtil.getMessages().postaElettronica__list_statoConsegnaField());
		statoConsegnaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoConsegnaField.setType(ListGridFieldType.ICON);
		statoConsegnaField.setAlign(Alignment.CENTER);
		statoConsegnaField.setWrap(false);		
		statoConsegnaField.setWidth(30);
		statoConsegnaField.setIconWidth(16);
		statoConsegnaField.setIconHeight(16);		
		statoConsegnaField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) 
			{
				String statoConsegna = (String) record.getAttribute("iconaStatoConsegna");
				
				return getIconaStatoConsolidamento(statoConsegna);
				
				
				
			}
		});
		
		statoConsegnaField.setHoverCustomizer(new HoverCustomizer() 
		{
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				return (String) record.getAttribute("statoConsegna");
			}
		});
		return statoConsegnaField;
	}

	private String getIconaStatoConsolidamento(String stato)
	{
		if(stato != null && "OK".equals(stato)) 
		{											
			return buildIconHtml("postaElettronica/statoConsolidamento/consegnata.png");						
		}
		
		if(stato != null && "IP".equals(stato)) 
		{											
			return buildIconHtml("postaElettronica/statoConsolidamento/presunto_ok.png");						
		}
		
		if(stato != null && "W".equals(stato)) 
		{											
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-arancione.png");						
		}
		
		if(stato != null && "KO".equals(stato)) 
		{											
			return buildIconHtml("postaElettronica/statoConsolidamento/ko-red.png");						
		}
		
		if(stato != null && "ND".equals(stato)) 
		{											
			return buildIconHtml("postaElettronica/statoConsolidamento/stato_consegna.png");						
		}
		
		return null;
	}
	
	private ListGridField buildStatoAccettazioneField() 
	{
		//MARINA MODIFICA
		//ListGridField statoAccettazioneField = new ListGridField("statoAccettazione","Stato Accettazione");
		ListGridField statoAccettazioneField = new ListGridField("statoAccettazione",I18NUtil.getMessages().postaElettronica_list_statoAccettazioneField());
		statoAccettazioneField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoAccettazioneField.setType(ListGridFieldType.ICON);
		statoAccettazioneField.setAlign(Alignment.CENTER);
		statoAccettazioneField.setWrap(false);		
		statoAccettazioneField.setWidth(30);
		statoAccettazioneField.setIconWidth(16);
		statoAccettazioneField.setIconHeight(16);		
		statoAccettazioneField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) 
			{
				String statoAccettazione = (String) record.getAttribute("iconaStatoAccettazione");
				
				return getIconaStatoConsolidamento(statoAccettazione);							
			}
		});
		
		statoAccettazioneField.setHoverCustomizer(new HoverCustomizer() 
		{
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				return (String) record.getAttribute("statoAccettazione");
			}
		});
		
		
		
		return statoAccettazioneField;
	}

	private ListGridField buildStatoInvioField() 
	{
		//MARINA MODIFICA
		//ListGridField statoInvioField = new ListGridField("statoInvio","Stato Invio");
		ListGridField statoInvioField = new ListGridField("statoInvio",I18NUtil.getMessages().postaElettronica_list_statoInvioField());
		statoInvioField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoInvioField.setType(ListGridFieldType.ICON);
		statoInvioField.setAlign(Alignment.CENTER);
		statoInvioField.setWrap(false);		
		statoInvioField.setWidth(30);
		statoInvioField.setIconWidth(16);
		statoInvioField.setIconHeight(16);		
		statoInvioField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) 
			{
				String statoInvio = (String) record.getAttribute("iconaStatoInvio");
			
				return getIconaStatoConsolidamento(statoInvio);	
											
			}
		});
		
		statoInvioField.setHoverCustomizer(new HoverCustomizer() 
		{
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				return (String) record.getAttribute("statoInvio");
			}
		});
		
		return statoInvioField;
	}

	private ListGridField buildContrassegnoField() {
		ListGridField contrassegnoMail = new ListGridField("idRecDizContrassegno", I18NUtil.getMessages().postaElettronica_list_contrassegnoField_title());
		contrassegnoMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		contrassegnoMail.setType(ListGridFieldType.ICON);
		contrassegnoMail.setAlign(Alignment.CENTER);
		contrassegnoMail.setWrap(false);		
		contrassegnoMail.setWidth(30);
		contrassegnoMail.setIconWidth(16);
		contrassegnoMail.setIconHeight(16);		
		
		contrassegnoMail.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {	
				String idRecDizContrassegno = (String) value;
				if(idRecDizContrassegno != null && !"".equals(idRecDizContrassegno)) {		
					return buildIconHtml("postaElettronica/contrassegno/"+  idRecDizContrassegno.substring(6).toLowerCase() + ".png");						
				}
				return null;								
			}
		});
		contrassegnoMail.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return (String) record.getAttribute("contrassegno");				
			}
		});
		return contrassegnoMail;
	}

	private ListGridField buildFlagInteroperabileField() {
		//MRAINA MODIFICA
		//ListGridField flgInteroperabileMail = new ListGridField("flgInteroperabile","Interoperabile");
		ListGridField flgInteroperabileMail = new ListGridField("flgInteroperabile",I18NUtil.getMessages().postaElettronica_list_flgInteroperabileMail());
		flgInteroperabileMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgInteroperabileMail.setType(ListGridFieldType.ICON);
		flgInteroperabileMail.setAlign(Alignment.CENTER);
		flgInteroperabileMail.setWrap(false);		
		flgInteroperabileMail.setWidth(30);
		flgInteroperabileMail.setIconWidth(16);
		flgInteroperabileMail.setIconHeight(16);	
		
		flgInteroperabileMail.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				String flgInteroperabile = record.getAttribute("flgInteroperabile");
				if(flgInteroperabile!=null && flgInteroperabile.equals("1")) {
					return dettaglioPostaElettronica.iconHtml("postaElettronica/interoperabile.png");
				} 
				return null;
			}});
		
		flgInteroperabileMail.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				String flgInteroperabile = record.getAttribute("flgInteroperabile");
				if(flgInteroperabile!=null && flgInteroperabile.equals("1")) {
					return "Interoperabile";
				} 
				return null;
			}
		});
		return flgInteroperabileMail;
	}

	private ListGridField buildFlagPec() {
		//MARINA MODIFICA
		//ListGridField flgPECMail = new ListGridField("flgPEC","Mitt. certificato");
		ListGridField flgPECMail = new ListGridField("flgPEC",I18NUtil.getMessages().postaElettronica_list_flgPECMail());
		flgPECMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPECMail.setType(ListGridFieldType.ICON);
		flgPECMail.setAlign(Alignment.CENTER);
		flgPECMail.setWrap(false);		
		flgPECMail.setWidth(30);
		flgPECMail.setIconWidth(16);
		flgPECMail.setIconHeight(16);	
		
		flgPECMail.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				String flgPEC = record.getAttribute("flgPEC");
				if(flgPEC!=null && flgPEC.equals("1")) {
					return dettaglioPostaElettronica.iconHtml("coccarda.png");
				} 
				return null;
			}});
		
		flgPECMail.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				String flgPEC = record.getAttribute("flgPEC");
				if(flgPEC!=null && flgPEC.equals("1")) {
					return "Mitt. certificato";
				} 
				return null;
			}
		});
		return flgPECMail;
	}

	

	private ListGridField buildIconaMicroCatField() {
		//MARINA MODIFICA
		//ListGridField iconaMicroCategoriaMail = new ListGridField("iconaMicroCategoria", "Categoria");	
		ListGridField iconaMicroCategoriaMail = new ListGridField("iconaMicroCategoria",I18NUtil.getMessages().postaElettronica_iconaMicroCategoriaMail());	
		iconaMicroCategoriaMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaMicroCategoriaMail.setType(ListGridFieldType.ICON);
		iconaMicroCategoriaMail.setAlign(Alignment.CENTER);
		iconaMicroCategoriaMail.setWrap(false);		
		iconaMicroCategoriaMail.setWidth(30);
		iconaMicroCategoriaMail.setIconWidth(16);
		iconaMicroCategoriaMail.setIconHeight(16);			
		iconaMicroCategoriaMail.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				String iconaMicroCategoria = (String) record.getAttribute("iconaMicroCategoria");
				if(iconaMicroCategoria != null && !"".equals(iconaMicroCategoria)) {

					return buildIconHtml("postaElettronica/iconMilano/" + iconaMicroCategoria + ".png");	
				}
				return null;
			}
		});	 
		
		iconaMicroCategoriaMail.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String iconaMicroCategoria = (String) record.getAttribute("iconaMicroCategoria");
				
				if(iconaMicroCategoria != null && !"".equals(iconaMicroCategoria)) {
					return iconaMicroCategoria.replaceAll("_", " ");					
				}				
				return null;	
			}
		});
		return iconaMicroCategoriaMail;
	}

//	private ListGridField buildFlagIOField() {
//		final ListGridField flgIOMail  = new ListGridField("flgIO","Entrata / uscita");
//		flgIOMail.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
//		flgIOMail.setCellFormatter(new CellFormatter() {			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
//				if(record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equalsIgnoreCase("I")) {
//					return dettaglioPostaElettronica.iconHtml("postaElettronica/iconMilano/mail_entrata.png");
//				} else {
//					return dettaglioPostaElettronica.iconHtml("postaElettronica/iconMilano/mail_uscita.png");
//				}
//			}});
//		
//		flgIOMail.setHoverCustomizer(new HoverCustomizer() {		
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if(record.getAttribute("flgIO") != null && record.getAttribute("flgIO").equalsIgnoreCase("I")) {
//					return "E-mail in entrata";
//				} else {
//					return "E-mail in uscita";
//				}				
//			}
//		});
//		
//		return flgIOMail;
//	}

	private ListGridField buildIdMailField() {
		//MARINA MODIFICA
		//ListGridField idEmail = new ListGridField("idEmail","Id.");
		ListGridField idEmail = new ListGridField("idEmail",I18NUtil.getMessages().postaElettronica_list_idEmail());
		idEmail.setHidden(true);
		return idEmail;
	}

	private ListGridField buildProgressivoField() {
		//MARINA MODIFICA
		//ListGridField progressivo = new ListGridField("id","N°");
		ListGridField progressivo = new ListGridField("id",I18NUtil.getMessages().postaElettronica_list_progressivo());
		progressivo.setAlign(Alignment.RIGHT);
		progressivo.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageDetailButtonClick(record);
				
			}
		});
		return progressivo;
	}

	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("id")) {
				if(record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {					
					return it.eng.utility.Styles.cellTextOrangeClickable;
				} else if (record.getAttribute("flgIO") != null) {
					if (record.getAttribute("flgIO").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null && (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN")
										|| record.getAttribute("categoria").equalsIgnoreCase("PEC")
										|| record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA") || record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellTextGreenClickable;
						} else {
							return it.eng.utility.Styles.cellTextGreyClickable;
						}
					} 
					else if (record.getAttribute("flgIO").equalsIgnoreCase("O")) 
					{
						
						if(record.getAttribute("categoria") != null && 
								(record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) 
				        {
							return it.eng.utility.Styles.cellTextGreyClickable;
				        }
						else
							return it.eng.utility.Styles.cellTextBlueClickable;
					}
				}
			} else {
				if(record.getAttribute("id") != null && record.getAttribute("id").toUpperCase().endsWith(".B")) {					
					return it.eng.utility.Styles.cellOrange;
				} else if (record.getAttribute("flgIO") != null) {
					if (record.getAttribute("flgIO").equalsIgnoreCase("I")) {
						if (record.getAttribute("categoria") != null
								&& (record.getAttribute("categoria").equalsIgnoreCase("INTEROP_SEGN") 
										|| record.getAttribute("categoria").equalsIgnoreCase("PEC")
										|| record.getAttribute("categoria").equalsIgnoreCase("ANOMALIA") 
										|| record.getAttribute("categoria").equalsIgnoreCase("ALTRO"))) {
							return it.eng.utility.Styles.cellGreen;
						} else {
							return it.eng.utility.Styles.cellGrey;
						}
					} 
					else if (record.getAttribute("flgIO").equalsIgnoreCase("O")) 
					{
						if(record.getAttribute("categoria") != null && 
								(record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ECC") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_CONF") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_AGG") 
								|| record.getAttribute("categoria").equalsIgnoreCase("INTEROP_ANN"))) 
				        {
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
	protected Canvas getExpansionComponent(final ListGridRecord record) 
	{
		return dettaglioPostaElettronica.createExpansionComponentDestinatari(record);
	}

	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(false);
	}

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override 
	protected void manageDetailButtonClick(ListGridRecord record) {
		dettaglioPostaElettronica.caricaDettaglioEmailCollegata(record.getAttribute("idEmail"));
	}
	
	//Cacco Federico 12-10-2015 Sovrascritto metodo per ordinamento in base alla data
	@Override
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) 
	{
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm:ss");
		try{
			Object returnValue = value != null && !"".equals(value) ? dateFormat.parse(value) : null; 
			return returnValue;
		}
		catch(IllegalArgumentException ie)
		{
			/*
			 * Nel caso in cui si sia generato un IllegalArgumentException perchè non si è riusciti a formattare
			 * correttamente la stringa.
			 * E' possibile infatti che non sia del tipo HH:mm:ss ma HH:mm 
			 */
			dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
			Object returnValue = value != null && !"".equals(value) ? dateFormat.parse(value) : null; 
			return returnValue;
		}
	}
}
