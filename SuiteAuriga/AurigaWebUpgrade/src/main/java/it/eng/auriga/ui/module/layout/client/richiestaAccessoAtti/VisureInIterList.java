/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
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
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * 
 * @author DANCRIST
 *
 */

public class VisureInIterList extends CustomList {
	
	private ListGridField iconaMessaggioUltimoTaskField;
	private ListGridField idUdFolderField;
	private ListGridField idProcedimentoField;
	//private ListGridField estremiProcedimentoField;
	private ListGridField dataAvvioProcedimentoField;
	private ListGridField decorrenzaProcedimentoField;
	private ListGridField processoPadreProcedimentoField;
	private ListGridField statoProcedimentoField;
	private ListGridField documentoInizialeProcedimentoField;
	private ListGridField inFaseProcedimentoField;
	private ListGridField scadenzaTermineProcedimentoField;
	private ListGridField flgScadenzaTermineProcedimentoField;
	private ListGridField ultimoTaskProcedimentoField;
	private ListGridField messaggioUltimoTaskProcedimentoField;
	private ListGridField noteProcedimentoField;
	private ListGridField prossimeAttivitaField;
	private ListGridField richiedente;
	private ListGridField tipoRichiedente;
	private ListGridField respIstruttoria;
	private ListGridField indirizzo;
	private ListGridField attiRichiesti;
	private ListGridField classificaAtti;
	private ListGridField archiviCoinvolti;
	private ListGridField udc;
	private ListGridField appCittadella;
	private ListGridField appBernina;
	private ListGridField codPraticaSUE;
	private ListGridField tipologiaRichiedenteSUEField;
	private ListGridField motivazioneVisuraSUEField;
	private ListGridField richAttiDiFabbricaField;
	private ListGridField fabbricatoAttiCostrFino1996Field;
	private ListGridField fabbricatoAttiCostrDa1997Field;
	private ListGridField richModificheField;
	//private ListGridField richiestoCartaceoField;
	private ListGridField ultimaAttivitaEsitoField;
	private ListGridField canaleArrivoField;
	
	private ListGridField uriFile;
	private ListGridField nomeFile;
	private ListGridField idUd;
	
	protected ControlListGridField folderButtonField;
	protected ControlListGridField folderDetailButtonField;
	protected ControlListGridField visualizzaIstanzaButtonField;
	protected ControlListGridField fileRichiestaButtonField;
	
	public VisureInIterList(String nomeEntita) {
		super(nomeEntita);
		
		// campi nascosti
		idUdFolderField  = new ListGridField("idUdFolder"); 
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
		
		uriFile = new ListGridField("uriFile");
		uriFile.setHidden(true);
		uriFile.setCanHide(false);
		
		nomeFile = new ListGridField("nomeFile");
		nomeFile.setHidden(true);
		nomeFile.setCanHide(false);
		
		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false);
				
		// campi visibili
		iconaMessaggioUltimoTaskField = new ControlListGridField("iconaMessaggioUltimoTask");
		iconaMessaggioUltimoTaskField.setAttribute("custom", true);		
		iconaMessaggioUltimoTaskField.setWidth(40);
		iconaMessaggioUltimoTaskField.setShowHover(true);			
		iconaMessaggioUltimoTaskField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				String messaggioUltimoTask = (String) record.getAttribute("messaggioUltimoTaskProcedimento");
				if(messaggioUltimoTask != null && !"".equals(messaggioUltimoTask)) {
					return buildImgButtonHtml("buttons/doc.png");												
				} 				
				return null;					
			}
		});
		iconaMessaggioUltimoTaskField.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String messaggioUltimoTask = (String) record.getAttribute("messaggioUltimoTaskProcedimento");
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
				String messaggioUltimoTask = (String) record.getAttribute("messaggioUltimoTaskProcedimento");
				if(messaggioUltimoTask != null && !"".equals(messaggioUltimoTask)) {
					messaggioUltimoTaskProcedimentoButtonClick(record);
				}				
			}
		});			

		//estremiProcedimentoField = new ListGridField("estremiProcedimento",I18NUtil.getMessages().visure_in_iter_list_estremiProcedimentoField());  
		//estremiProcedimentoField.setWidth(100);
		
		dataAvvioProcedimentoField = new ListGridField("dataAvvioProcedimento",I18NUtil.getMessages().visure_in_iter_list_dataAvvioProcedimentoField());  dataAvvioProcedimentoField.setType(ListGridFieldType.DATE); dataAvvioProcedimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAvvioProcedimentoField.setWidth(100);
		dataAvvioProcedimentoField.setType(ListGridFieldType.DATE);
		
		statoProcedimentoField = new ListGridField("statoProcedimento",I18NUtil.getMessages().visure_in_iter_list_statoProcedimentoField());
		statoProcedimentoField.setWidth(100);
		
		documentoInizialeProcedimentoField = new ListGridField("documentoInizialeProcedimento",I18NUtil.getMessages().visure_in_iter_list_documentoInizialeProcedimentoField());
		//documentoInizialeProcedimentoField.setDisplayField("numeroProposta");
		documentoInizialeProcedimentoField.setSortByDisplayField(false);
		documentoInizialeProcedimentoField.setWidth(100);
		documentoInizialeProcedimentoField.setAlign(Alignment.LEFT);
		documentoInizialeProcedimentoField.setCanDragResize(true);
		documentoInizialeProcedimentoField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {

				event.cancel();
				final ListGridRecord record = getRecord(event.getRecordNum());
				AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), "Visura SUE richiesta " + record.getAttribute("documentoInizialeProcedimento"), new BooleanCallback() {

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
		
		inFaseProcedimentoField = new ListGridField("inFaseProcedimento",I18NUtil.getMessages().visure_in_iter_list_inFaseProcedimentoField());
		inFaseProcedimentoField.setWidth(100);
		inFaseProcedimentoField.setHidden(true);
		
		scadenzaTermineProcedimentoField = new ListGridField("scadenzaTermineProcedimento",I18NUtil.getMessages().visure_in_iter_list_scadenzaTermineProcedimentoField()); scadenzaTermineProcedimentoField.setType(ListGridFieldType.DATE);scadenzaTermineProcedimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		scadenzaTermineProcedimentoField.setWidth(100);
		scadenzaTermineProcedimentoField.setType(ListGridFieldType.DATE);
		
		flgScadenzaTermineProcedimentoField = new ListGridField("flgScadenzaTermineProcedimento",I18NUtil.getMessages().visure_in_iter_list_flgScadenzaTermineProcedimentoField());
		flgScadenzaTermineProcedimentoField.setType(ListGridFieldType.ICON);
		flgScadenzaTermineProcedimentoField.setWidth(30);
		flgScadenzaTermineProcedimentoField.setIconWidth(16);
		flgScadenzaTermineProcedimentoField.setIconHeight(16);
		flgScadenzaTermineProcedimentoField.setAlign(Alignment.CENTER);
		flgScadenzaTermineProcedimentoField.setWidth(30);		
		
		Map<String, String> flgScadenzaTermineProcedimentoValueIcons = new HashMap<String, String>();		
		flgScadenzaTermineProcedimentoValueIcons.put("1", "prioritaAlta.png");
		flgScadenzaTermineProcedimentoValueIcons.put("0", "blank.png");
		flgScadenzaTermineProcedimentoValueIcons.put("",  "blank.png");
		flgScadenzaTermineProcedimentoField.setValueIcons(flgScadenzaTermineProcedimentoValueIcons);
		flgScadenzaTermineProcedimentoField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgScadenzaTermineProcedimento"))) {
					return I18NUtil.getMessages().visure_in_iter_flgScadenzaTermineProcedimento_1_value();
				}				
				return null;
			}
		});
				
		ultimoTaskProcedimentoField = new ListGridField("ultimoTaskProcedimento",I18NUtil.getMessages().visure_in_iter_list_ultimoTaskProcedimentoField());
		ultimoTaskProcedimentoField.setWidth(100);
		
		messaggioUltimoTaskProcedimentoField = new ListGridField("messaggioUltimoTaskProcedimento", I18NUtil.getMessages().visure_in_iter_list_messaggioUltimoTaskProcedimentoField());
		messaggioUltimoTaskProcedimentoField.setWidth(100);
		
		noteProcedimentoField = new ListGridField("noteProcedimento",I18NUtil.getMessages().visure_in_iter_list_noteProcedimentoField());
		noteProcedimentoField.setWidth(100);
		noteProcedimentoField.setHidden(true);
		
		prossimeAttivitaField = new ListGridField("prossimeAttivita",I18NUtil.getMessages().visure_in_iter_list_prossimeAttivitaField());
		prossimeAttivitaField.setWidth(100);
		prossimeAttivitaField.setHidden(true);
		
		richiedente = new ListGridField("richiedente",I18NUtil.getMessages().visure_in_iter_list_richiedenteField());
		
		tipoRichiedente = new ListGridField("tipoRichiedente",I18NUtil.getMessages().visure_in_iter_list_tipoRichiedenteField());
		tipoRichiedente.setType(ListGridFieldType.ICON);
		tipoRichiedente.setWidth(30);
		tipoRichiedente.setIconWidth(16);
		tipoRichiedente.setIconHeight(16);
		tipoRichiedente.setAlign(Alignment.CENTER);
		tipoRichiedente.setWidth(30);	
		
		Map<String, String> flgTipoRichiedenteValueIcons = new HashMap<String, String>();		
		flgTipoRichiedenteValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		flgTipoRichiedenteValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		tipoRichiedente.setValueIcons(flgTipoRichiedenteValueIcons);
		tipoRichiedente.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("E".equals(record.getAttribute("tipoRichiedente"))) {
					return I18NUtil.getMessages().visure_in_iter_flgTipoRichiedenteEsternovalue();
				} else if("I".equals(record.getAttribute("tipoRichiedente"))) {
					return I18NUtil.getMessages().visure_in_iter_flgTipoRichiedenteInternovalue();
				}				
				return null;
			}
		});
		
		respIstruttoria = new ListGridField("respIstruttoria",I18NUtil.getMessages().visure_in_iter_list_respIstruttoriaField());
		
		indirizzo = new ListGridField("indirizzo",I18NUtil.getMessages().visure_in_iter_list_indirizzoField());

		attiRichiesti = new ListGridField("attiRichiesti",I18NUtil.getMessages().visure_in_iter_list_attiRichiestiField());
		
		classificaAtti = new ListGridField("classificaAtti",I18NUtil.getMessages().visure_in_iter_list_classificaAttiField());
		
		archiviCoinvolti = new ListGridField("archiviCoinvolti",I18NUtil.getMessages().visure_in_iter_list_archiviCoinvoltiField());

		udc = new ListGridField("udc",I18NUtil.getMessages().visure_in_iter_list_udcField());
		
		appCittadella = new ListGridField("appCittadella",I18NUtil.getMessages().visure_in_iter_list_appCittadellaField());
		appCittadella.setType(ListGridFieldType.DATE);
		appCittadella.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		appBernina = new ListGridField("appBernina",I18NUtil.getMessages().visure_in_iter_list_appBerninaField());
		appBernina.setType(ListGridFieldType.DATE);
		appBernina.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		codPraticaSUE = new ListGridField("codPraticaSUE",I18NUtil.getMessages().visure_in_iter_list_codPraticaSUEField());
			
		tipologiaRichiedenteSUEField = new ListGridField("tipologiaRichiedenteSUE",I18NUtil.getMessages().visure_in_iter_list_tipologiaRichiedenteSUEField());
		
		motivazioneVisuraSUEField = new ListGridField("motivazioneVisuraSUE",I18NUtil.getMessages().visure_in_iter_list_motivazioneVisuraSUEField());
		
		richAttiDiFabbricaField = new ListGridField("richAttiDiFabbrica",I18NUtil.getMessages().visure_in_iter_list_richAttiDiFabbricaField());
		
//		richAttiDiFabbricaField = new ListGridField("richAttiDiFabbrica",I18NUtil.getMessages().visure_in_iter_list_richAttiDiFabbricaField());
//		richAttiDiFabbricaField.setType(ListGridFieldType.ICON);
//		richAttiDiFabbricaField.setWidth(30);
//		richAttiDiFabbricaField.setIconWidth(16);
//		richAttiDiFabbricaField.setIconHeight(16);
//		richAttiDiFabbricaField.setAlign(Alignment.CENTER);
//		richAttiDiFabbricaField.setWidth(30);		
//		Map<String, String> richAttiDiFabbricaValueIcons = new HashMap<String, String>();		
//		richAttiDiFabbricaValueIcons.put("1", "ok.png");
//		richAttiDiFabbricaValueIcons.put("0", "blank.png");
//		richAttiDiFabbricaValueIcons.put("",  "blank.png");
//		richAttiDiFabbricaField.setValueIcons(richAttiDiFabbricaValueIcons);
//		richAttiDiFabbricaField.setHoverCustomizer(new HoverCustomizer() {	
//			
//			@Override
//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				
//				if("1".equals(record.getAttribute("richAttiDiFabbrica"))) {
//					return I18NUtil.getMessages().visure_in_iter_list_richAttiDiFabbrica_value();
//				}				
//				return null;
//			}
//		});
		
		fabbricatoAttiCostrFino1996Field = new ListGridField("fabbricatoAttiCostrFino1996",I18NUtil.getMessages().visure_in_iter_list_fabbricatoAttiCostrFino1996Field());
		fabbricatoAttiCostrFino1996Field.setType(ListGridFieldType.ICON);
		fabbricatoAttiCostrFino1996Field.setWidth(30);
		fabbricatoAttiCostrFino1996Field.setIconWidth(16);
		fabbricatoAttiCostrFino1996Field.setIconHeight(16);
		fabbricatoAttiCostrFino1996Field.setAlign(Alignment.CENTER);
		fabbricatoAttiCostrFino1996Field.setWidth(30);	
		
		Map<String, String> fabbricatoAttiCostrFino1996ValueIcons = new HashMap<String, String>();		
		fabbricatoAttiCostrFino1996ValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		fabbricatoAttiCostrFino1996ValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		fabbricatoAttiCostrFino1996ValueIcons.put("EI", "protocollazione/flagSoggettiGruppo/EI.png");
		fabbricatoAttiCostrFino1996Field.setValueIcons(fabbricatoAttiCostrFino1996ValueIcons);
		fabbricatoAttiCostrFino1996Field.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("E".equals(record.getAttribute("fabbricatoAttiCostrFino1996"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrFino1996Esterno_value();
				} else if("I".equals(record.getAttribute("fabbricatoAttiCostrFino1996"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrFino1996Interno_value();
				} else if("EI".equals(record.getAttribute("fabbricatoAttiCostrFino1996"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrFino1996EsternoInterno_value();
				}				
				return null;
			}
		});
		
		fabbricatoAttiCostrDa1997Field = new ListGridField("fabbricatoAttiCostrDa1997",I18NUtil.getMessages().visure_in_iter_list_fabbricatoAttiCostrDa1997Field());
		fabbricatoAttiCostrDa1997Field.setType(ListGridFieldType.ICON);
		fabbricatoAttiCostrDa1997Field.setWidth(30);
		fabbricatoAttiCostrDa1997Field.setIconWidth(16);
		fabbricatoAttiCostrDa1997Field.setIconHeight(16);
		fabbricatoAttiCostrDa1997Field.setAlign(Alignment.CENTER);
		fabbricatoAttiCostrDa1997Field.setWidth(30);	
		
		Map<String, String> fabbricatoAttiCostrDa1997ValueIcons = new HashMap<String, String>();		
		fabbricatoAttiCostrDa1997ValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		fabbricatoAttiCostrDa1997ValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		fabbricatoAttiCostrDa1997ValueIcons.put("EI", "protocollazione/flagSoggettiGruppo/EI.png");
		fabbricatoAttiCostrDa1997Field.setValueIcons(fabbricatoAttiCostrDa1997ValueIcons);
		fabbricatoAttiCostrDa1997Field.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("E".equals(record.getAttribute("fabbricatoAttiCostrDa1997"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrDa1997Esterno_value();
				} else if("I".equals(record.getAttribute("fabbricatoAttiCostrDa1997"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrDa1997Interno_value();
				} else if("EI".equals(record.getAttribute("fabbricatoAttiCostrDa1997"))) {
					return I18NUtil.getMessages().visure_in_iter_fabbricatoAttiCostrDa1997EsternoInterno_value();
				}				
				return null;
			}
		});
		
//		richiestoCartaceoField  = new ListGridField("richiestoCartaceo",I18NUtil.getMessages().visure_in_iter_list_richiestoCartaceoField());
//		richiestoCartaceoField.setType(ListGridFieldType.ICON);
//		richiestoCartaceoField.setWidth(30);
//		richiestoCartaceoField.setIconWidth(16);
//		richiestoCartaceoField.setIconHeight(16);
//		richiestoCartaceoField.setAlign(Alignment.CENTER);
//		richiestoCartaceoField.setWidth(30);			
//		Map<String, String> richiestoCartaceoValueIcons = new HashMap<String, String>();		
//		richiestoCartaceoValueIcons.put("1", "ok.png");
//		richiestoCartaceoValueIcons.put("0", "blank.png");
//		richiestoCartaceoValueIcons.put("",  "blank.png");
//		richiestoCartaceoField.setValueIcons(richiestoCartaceoValueIcons);
//		richiestoCartaceoField.setHoverCustomizer(new HoverCustomizer() {	
//			
//			@Override
//			public String hoverHTML(Object vashlue, ListGridRecord record, int rowNum, int colNum) {
//				
//				if("1".equals(record.getAttribute("richiestoCartaceo"))) {
//					return I18NUtil.getMessages().visure_in_iter_richiestoCartaceo_value();
//				}				
//				return null;
//			}
//		});
		
		richModificheField = new ListGridField("richModifiche",I18NUtil.getMessages().visure_in_iter_list_richModificheFieldField());
		
		ultimaAttivitaEsitoField = new ListGridField("ultimaAttivitaEsito", I18NUtil.getMessages().visure_in_iter_list_ultimaAttivitaEsito());
		ultimaAttivitaEsitoField.setType(ListGridFieldType.ICON);
		ultimaAttivitaEsitoField.setWidth(30);
		ultimaAttivitaEsitoField.setIconWidth(16);
		ultimaAttivitaEsitoField.setIconHeight(16);
		ultimaAttivitaEsitoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String ultimaAttivitaEsito = record.getAttribute("ultimaAttivitaEsito") != null ? record.getAttribute("ultimaAttivitaEsito") : "";
				if ("OK".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_OK.png");
				} else if ("KO".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_KO.png");
				} else if ("W".equalsIgnoreCase(ultimaAttivitaEsito)) {
					return buildIconHtml("pratiche/task/icone/svolta_W.png");
				} 
				return null;
			}
		});
		ultimaAttivitaEsitoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("altUltimaAttivitaEsito");
			}
		});
		
		canaleArrivoField = new ListGridField("canaleArrivo", I18NUtil.getMessages().visure_in_iter_list_canaleArrivo());
		canaleArrivoField.setType(ListGridFieldType.ICON);
		canaleArrivoField.setWidth(30);
		canaleArrivoField.setIconWidth(16);
		canaleArrivoField.setIconHeight(16);
		canaleArrivoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
			
				if("PEC".equals(record.getAttribute("canaleArrivo"))) {
					return buildIconHtml("richiesteAccessoAtti/canalearrivo_pec.png");
				} else if("ONLINE".equals(record.getAttribute("canaleArrivo"))) {
					return buildIconHtml("richiesteAccessoAtti/canalearrivo_online.png");
				} else if("CM".equals(record.getAttribute("canaleArrivo"))) {
					return buildIconHtml("richiesteAccessoAtti/canalearrivo_frontoffice.png");
				} else if("ALTRO".equals(record.getAttribute("canaleArrivo"))) {
					return buildIconHtml("richiesteAccessoAtti/canalearrivo_altro.png");
				} else {
					return buildIconHtml("richiesteAccessoAtti/canalearrivo_vuoto.png");
				}		
			}
		});
		canaleArrivoField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("PEC".equals(record.getAttribute("canaleArrivo"))) {
					return "istanza presentata via PEC ";
				} else if("ONLINE".equals(record.getAttribute("canaleArrivo"))) {
					return "istanza presentata da sportello on-line";
				} else if("CM".equals(record.getAttribute("canaleArrivo"))) {
					return "istanza presentata da sportello (cartacea)";
				} else if("ALTRO".equals(record.getAttribute("canaleArrivo"))) {
					return "istanza presentata attraverso altro canale";
				} else {
					return "istanza presentata attraverso canale sconosciuto";
				}
			}
		});

		setFields(new ListGridField[] {
               idUdFolderField,
               idProcedimentoField, 
               //estremiProcedimentoField,
               dataAvvioProcedimentoField,
               decorrenzaProcedimentoField,
               processoPadreProcedimentoField,
               uriFile,
               nomeFile,
               idUd,
               statoProcedimentoField,
               documentoInizialeProcedimentoField,
               inFaseProcedimentoField,
               scadenzaTermineProcedimentoField,
               flgScadenzaTermineProcedimentoField,
               ultimoTaskProcedimentoField,
               messaggioUltimoTaskProcedimentoField,
               iconaMessaggioUltimoTaskField,
               noteProcedimentoField,
               prossimeAttivitaField,
	           richiedente,
	           tipoRichiedente,
	           respIstruttoria,
	           indirizzo,
	           attiRichiesti,
	           classificaAtti,
	           archiviCoinvolti,
	           udc,
	           appCittadella,
	           appBernina,
	           codPraticaSUE,
	           tipologiaRichiedenteSUEField,
	           motivazioneVisuraSUEField,
	           richAttiDiFabbricaField,
	           fabbricatoAttiCostrFino1996Field,
	           fabbricatoAttiCostrDa1997Field,
	           richModificheField,
	           ultimaAttivitaEsitoField,
	           canaleArrivoField
	           //richiestoCartaceoField
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
			folderButton.setPrompt(I18NUtil.getMessages().visure_in_iter_iconaFolderButton_prompt());
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
			folderDetailButton.setPrompt(I18NUtil.getMessages().visure_in_iter_iconaFolderDetailButton_prompt());
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
	
	protected void messaggioUltimoTaskProcedimentoButtonClick(Record pRecord) {
		String messaggio = pRecord.getAttribute("messaggioUltimoTaskProcedimento");
		SC.say(messaggio);		
	}

	protected void folderButtonClick(Record record) {
		String idUdFolder = record.getAttribute("idUdFolder");		
		if(idUdFolder!=null && !idUdFolder.equalsIgnoreCase("")){
			FolderVisurePopup folderVisurePopup = new FolderVisurePopup(record);
			folderVisurePopup.show();
		}
	}
	
	protected void folderDetailButtonClick(final Record record) {
		AurigaLayout.apriDettaglioPratica(record.getAttribute("idProcedimento"), "Visura SUE richiesta " + record.getAttribute("documentoInizialeProcedimento"), new BooleanCallback() {

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
	
	protected void previewIstanza(final Record record) {
		
		final String display = record.getAttributeAsString("nomeFile");
		final String uri = record.getAttributeAsString("uriFile");
		final Boolean remoteUri = true;
		Record infoRecord = getInfoFileRecord(record);
		final InfoFileRecord info = new InfoFileRecord(infoRecord);
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(record, display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(record, display, uri, remoteUri, info);
		}
	}
	
	public void preview(final Record detailRecord, final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {

		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
	}
	
	private Record getInfoFileRecord(final Record record) {
		
		Record infoRecord = new Record();
		infoRecord.setAttribute("tipoFirma", record.getAttributeAsString("nome") != null && record.getAttributeAsString("nome").toLowerCase().endsWith(".p7m") ? "CAdES" : "");
		infoRecord.setAttribute("firmato", record.getAttributeAsString("iconaFirmata") != null && "1".equals(record.getAttributeAsString("iconaFirmata")) ? true : false);
		infoRecord.setAttribute("mimetype", record.getAttributeAsString("mimetype"));
		infoRecord.setAttribute("impronta", record.getAttributeAsString("impronta"));
		infoRecord.setAttribute("bytes", new Long(record.getAttributeAsString("dimensione")));
		infoRecord.setAttribute("convertibile", record.getAttributeAsString("flgConvertibilePdf") != null && "1".equals(record.getAttributeAsString("flgConvertibilePdf")) ? true : false);
		infoRecord.setAttribute("correctFileName", record.getAttributeAsString("nome"));
		return infoRecord;
	}
	
	@Override  
	public void manageContextClick(final Record record) {	
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)), null, null);
		}
	}
	
	public void showRowContextMenu(final ListGridRecord listGridRecord, final Menu navigationContextMenu, final Boolean flgFileRichiesto) {
		
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", listGridRecord.getAttribute("idUd"));
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ProtocolloDataSource", "idUd", FieldType.TEXT);
		lGWTRestDataSource.addParam("flgSoloAbilAzioni", "1");
		lGWTRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					
					final Menu contextMenu = new Menu();
					Menu altreOpMenu = null;
					if(flgFileRichiesto != null && flgFileRichiesto) {
						altreOpMenu = createFileRichiestoMenu(listGridRecord, detailRecord);
					} else {
						altreOpMenu = createAltreOpMenu(listGridRecord, detailRecord);
					}
					for (int i = 0; i < altreOpMenu.getItems().length; i++) {
						contextMenu.addItem(altreOpMenu.getItems()[i], i);
					}
					contextMenu.showContextMenu();	
				}
			}
		});
	}
	
	private Menu createAltreOpMenu(final ListGridRecord listGridRecord, final Record detailRecord) {
		
		Menu altreOpMenu = new Menu();
		
		// Crea la voce APRI FOLDER
		MenuItem folderMenuItem = new MenuItem(I18NUtil.getMessages().visure_in_iter_iconaFolderButton_prompt(), "archivio/flgUdFolder/F.png");   
		folderMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderButtonClick(listGridRecord);
			}   
		});   			
		altreOpMenu.addItem(folderMenuItem);
		
		// Crea la voce DETTAGLIO FOLDER		
		MenuItem folderDetailMenuItem = new MenuItem(I18NUtil.getMessages().visure_in_iter_iconaFolderDetailButton_prompt(), "buttons/detail.png");   
		folderDetailMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				
				folderDetailButtonClick(listGridRecord);
			}   
		});   			
		altreOpMenu.addItem(folderDetailMenuItem);
		
		// Crea la voce File richiesta
		MenuItem fileRichiestaMenuItem = new MenuItem(I18NUtil.getMessages().visure_in_iter_fileRichiestaMenuItem_prompt(), "archivio/file.png");   
		Menu fileAssociatiSubMenu = new Menu();
		if(detailRecord.getAttributeAsRecordList("listaAllegati") != null &&
		   !detailRecord.getAttributeAsRecordList("listaAllegati").isEmpty()) {
					
			for (int i = 0; i < detailRecord.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				
				final int nroAllegato = i;
				final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(i);
				
				if(allegatoRecord.getAttributeAsString("uriFileAllegato") != null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato")) ) {
					
					MenuItem fileAllegatoMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					buildAllegatiMenuItem(listGridRecord,detailRecord,allegatoRecord, fileAllegatoMenuItem, nroAllegato, true);
					fileAssociatiSubMenu.addItem(fileAllegatoMenuItem);
				}
			}
			fileRichiestaMenuItem.setSubmenu(fileAssociatiSubMenu);
			altreOpMenu.addItem(fileRichiestaMenuItem);
		}
		
		return altreOpMenu;
	}
	
	private Menu createFileRichiestoMenu(final ListGridRecord listGridRecord, final Record detailRecord) {
		
		Menu fileRichiestoMenu = new Menu();
		
		// Crea la voce File richiesta
		MenuItem fileRichiestaMenuItem = new MenuItem(I18NUtil.getMessages().visure_in_iter_fileRichiestaMenuItem_prompt(), "archivio/file.png");   
		Menu fileAssociatiSubMenu = new Menu();
		if(detailRecord.getAttributeAsRecordList("listaAllegati") != null &&
		   !detailRecord.getAttributeAsRecordList("listaAllegati").isEmpty()) {
					
			for (int i = 0; i < detailRecord.getAttributeAsRecordList("listaAllegati").getLength(); i++) {
				
				final int nroAllegato = i;
				final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(i);
				
				if(allegatoRecord.getAttributeAsString("uriFileAllegato") != null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato")) ) {
					
					MenuItem fileAllegatoMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					buildAllegatiMenuItem(listGridRecord,detailRecord,allegatoRecord, fileAllegatoMenuItem, nroAllegato, true);
					fileAssociatiSubMenu.addItem(fileAllegatoMenuItem);
				}
			}
			fileRichiestaMenuItem.setSubmenu(fileAssociatiSubMenu);
			fileRichiestoMenu.addItem(fileRichiestaMenuItem);
		}
		
		return fileRichiestoMenu;
	}
		
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
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
																
						return I18NUtil.getMessages().visure_in_iter_iconaFolderButton_prompt();
					}
				});		
				folderButtonField.addRecordClickHandler(new RecordClickHandler() {				
					@Override
					public void onRecordClick(RecordClickEvent event) {
						
						event.cancel();
						folderButtonClick(getRecord(event.getRecordNum()));
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
																
						return I18NUtil.getMessages().visure_in_iter_iconaFolderDetailButton_prompt();
					}
				});		
				folderDetailButtonField.addRecordClickHandler(new RecordClickHandler() {
					
					@Override
					public void onRecordClick(RecordClickEvent event) {
						
						event.cancel();
						folderDetailButtonClick(getRecord(event.getRecordNum()));
					}
				});												
			}		
			buttonsList.add(folderDetailButtonField);
			if(visualizzaIstanzaButtonField == null) {
				visualizzaIstanzaButtonField = new ControlListGridField("visualizzaIstanzaButton");  
				visualizzaIstanzaButtonField.setAttribute("custom", true);	
				visualizzaIstanzaButtonField.setShowHover(true);	
				visualizzaIstanzaButtonField.setCanReorder(false);		
				visualizzaIstanzaButtonField.setCellFormatter(new CellFormatter() {		
					
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isAbilToPreview(record)) {
							return buildImgButtonHtml("file/preview.png");
						}
						return null;
					}
				});
				visualizzaIstanzaButtonField.setHoverCustomizer(new HoverCustomizer() {	
					
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						if(isAbilToPreview(record)) {										
							return I18NUtil.getMessages().visure_in_iter_visualizzaIstanzaButton();
						}
						return null;
					}
				});		
				visualizzaIstanzaButtonField.addRecordClickHandler(new RecordClickHandler() {
					
					@Override
					public void onRecordClick(RecordClickEvent event) {
						
						event.cancel();
						if(isAbilToPreview(getRecord(event.getRecordNum()))) {	
							previewIstanza(getRecord(event.getRecordNum()));
						}
					}
				});
			}
			buttonsList.add(visualizzaIstanzaButtonField);
			
			if(fileRichiestaButtonField == null) {
				fileRichiestaButtonField = new ControlListGridField("fileRichiestaButton");  
				fileRichiestaButtonField.setAttribute("custom", true);	
				fileRichiestaButtonField.setShowHover(true);	
				fileRichiestaButtonField.setCanReorder(false);		
				fileRichiestaButtonField.setCellFormatter(new CellFormatter() {		
					
					@Override
					public String format(Object value, ListGridRecord record, int rowNum,
							int colNum) {
						return buildImgButtonHtml("archivio/file.png");
					}
				});
				fileRichiestaButtonField.setHoverCustomizer(new HoverCustomizer() {	
					
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum,
							int colNum) {						
						return I18NUtil.getMessages().visure_in_iter_fileRichiestaMenuItem_prompt();
					}
				});		
				fileRichiestaButtonField.addRecordClickHandler(new RecordClickHandler() {
					
					@Override
					public void onRecordClick(RecordClickEvent event) {						
						event.cancel();
						showRowContextMenu(getRecord(event.getRecordNum()),null,true);
					}
				});
			}
			buttonsList.add(fileRichiestaButtonField);
			
		}	
		return buttonsList;		
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	protected Boolean isAbilToPreview(Record record) {
		boolean valid = false;
		if(record != null && record.getAttributeAsString("uriFile") != null &&
				!"".equals(record.getAttributeAsString("uriFile"))) {
			valid = true;
		}
		return valid;
	}
	
	private void buildAllegatiMenuItem(final ListGridRecord listRecord, final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
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

//		if() {     TODO: timbro
			buildTimbraAllegato(listRecord, detailRecord, nroAllegato, operazioniFileAllegatoSubmenu, lInfoFileRecord, allegatoIntegrale);
//		}
			
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
			timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "COPIA_CONFORME_CUSTOM");
							timbraFileAllegato(detailRecord, nroAllegato, allegatoIntegrale);
						}
					});

			operazioniFileAllegatoSubmenu.addItem(timbroConformitaCustomAllegatoMenuItem);

		}

		// Attestato conformità all’originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(),
				"file/attestato.png");
		attestatoConformitaOriginaleMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final InfoFileRecord fileAllegato;
						final String uri;
						final String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						final String idDoc;
						
						//se è un allegato integrale
						if(allegatoIntegrale) {
						
							fileAllegato = InfoFileRecord
									.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
							uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						}
						//versione con omissis
						else {
							fileAllegato = InfoFileRecord
									.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
							uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						}
						SC.ask("Vuoi firmare digitalmente l'attestato?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, true);
								} else {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, false);
								}
							}
						});
					}
				});
		attestatoConformitaOriginaleMenuItem.setEnabled(lInfoFileRecord != null);
		operazioniFileAllegatoSubmenu.addItem(attestatoConformitaOriginaleMenuItem);
		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
	}
	
	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
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
	
	private void buildTimbraAllegato(final ListGridRecord listRecord, final Record detailRecord, final int nroAllegato,
		Menu operazioniFileAllegatoSubmenu, InfoFileRecord lInfoFileRecord, final boolean fileIntegrale) {
		boolean flgAddSubMenuTimbra = false;
		
		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();	
		
		MenuItem apponiSegnaturaRegistrazioneFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
			}
		});
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
		timbraSubMenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && (lInfoFileRecord.getMimetype().equals("application/pdf") || lInfoFileRecord.getMimetype().startsWith("image"))) {
				
				MenuItem timbroConformitaOriginaleAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(), "file/timbra.gif");
				timbroConformitaOriginaleAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaOriginaleAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_CARTACEO");
						timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
					}
				});
				
				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbroConformitaOriginaleAllegatoMenuItem);
			}
		}
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {		
			flgAddSubMenuTimbra=true;

			MenuItem timbroCopiaConformeAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformeAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			Menu sottoMenuAllegatoTimbroCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_STAMPA");
							timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeStampaAllegatoItem);
			
			MenuItem timbroCopiaConformeDigitaleAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");			
			timbroCopiaConformeDigitaleAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeDigitaleAllegatoItem);

			timbroCopiaConformeAllegatoMenuItem.setSubmenu(sottoMenuAllegatoTimbroCopiaConformeItem);
			timbraSubMenu.addItem(timbroCopiaConformeAllegatoMenuItem);
		
		}
		
		
		
		if(detailRecord != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia") != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia")){
			MenuItem timbraFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiTipologia(),
					"file/timbra.gif");
			timbraFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idDoc = listRecord.getAttribute("idDocPrimario");
					detailRecord.setAttribute("idDoc", idDoc);
					detailRecord.setAttribute("tipoTimbra", "T");
					timbraFileAllegato(detailRecord, nroAllegato, fileIntegrale);
				}
			});
			timbraFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraFileAllegatoMenuItem);
			
		}
		
		timbraMenuItem.setSubmenu(timbraSubMenu);
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			operazioniFileAllegatoSubmenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setTitle("Timbra");
			operazioniFileAllegatoSubmenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);
		}
	
	}
	
	public void timbraFileAllegato(Record detailRecord, int nroAllegato, boolean fileIntegrale) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		
		String display = null;
		String uri = null;
		String remoteUri = null;
		Object infoFile = null;
		String idDoc = null;
		
		String tipoTimbro = detailRecord.getAttributeAsString("tipoTimbro");
		String finalita = detailRecord.getAttributeAsString("finalita") !=null ? detailRecord.getAttributeAsString("finalita") : "";
		
		if (fileIntegrale) {
			idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
			display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
			uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
			remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
			infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		} else {
			idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
			display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
			uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
			remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
			infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
		}		

		idDoc = idDoc != null ? idDoc : "";
		tipoTimbro = tipoTimbro != null ? tipoTimbro : "";

		timbraFile(idDoc, tipoTimbro, idUd, display, uri, remoteUri, infoFile, finalita);
	}
	
	public void timbraFile(String idDoc, String tipoTimbro, String idUd, String display, String uri, String remoteUri, Object infoFile, String finalita) {
		InfoFileRecord precInfo = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
		
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("nomeFile", display);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remoteUri);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
				
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		} else {
		
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), precInfo.getMimetype(), idUd, idDoc,
					tipoTimbro,"D",rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	protected void creaAttestato(final String idUd, final String idDoc, ListGridRecord listRecord, final InfoFileRecord infoFileAllegato, final String uriFileAllegato, final boolean attestatoFirmato) {

		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUd"));
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record record = response.getData()[0];
				record.setAttribute("infoFileAttach", infoFileAllegato);
				record.setAttribute("uriAttach", uriFileAllegato);
				record.setAttribute("idUd", idUd);
				record.setAttribute("idDoc", idDoc);
				record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
				lGwtRestDataSource.extraparam.put("attestatoFirmato", Boolean.toString(attestatoFirmato));
				lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
				lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record data = response.getData()[0];
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
						final String filename = infoFile.getCorrectFileName();
						final String uri = data.getAttribute("tempFileOut");
						if (!attestatoFirmato) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", filename);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);

							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						} else {
							FirmaUtility.firmaMultipla(uri, filename, infoFile, new FirmaCallback() {

								@Override
								public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFileFirmato);
									lRecord.setAttribute("uri", uriFileFirmato);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", false);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							});
						}
					}
				});
			}
		});
	}
	
	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if (getFieldName(colNum).equals("documentoInizialeProcedimento")) {
				if (record.getAttribute("documentoInizialeProcedimento") != null &&
						!"".equalsIgnoreCase(record.getAttribute("documentoInizialeProcedimento"))) {
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