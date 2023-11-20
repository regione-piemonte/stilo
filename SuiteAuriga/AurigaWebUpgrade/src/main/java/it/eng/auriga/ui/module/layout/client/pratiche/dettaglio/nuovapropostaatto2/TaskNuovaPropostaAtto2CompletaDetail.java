/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreProposteConcorrentiPopUp;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaNonEseguitaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.MessaggioTaskWindow;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoInterface;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiGridItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowPageSelectionCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class TaskNuovaPropostaAtto2CompletaDetail extends NuovaPropostaAtto2CompletaDetail implements PropostaAttoInterface {

	protected TaskNuovaPropostaAtto2CompletaDetail instance;
	
	protected Record recordEvento;
	
	protected String idProcess;
	protected String nomeFlussoWF;
	protected String processNameWF;
	protected String idTipoEvento;
	protected String idEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;	
	protected String alertConfermaSalvaDefinitivo;

	protected String idUd;
	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParereTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;
	protected Boolean flgNoPubblTaskDoc;
	protected Boolean flgPubblicaSeparatoTaskDoc;
	
	protected String idModCopertina;
	protected String nomeModCopertina;
	protected String uriModCopertina;
	protected String tipoModCopertina;
	protected String idModCopertinaFinale;
	protected String nomeModCopertinaFinale;
	protected String uriModCopertinaFinale;
	protected String tipoModCopertinaFinale;
	protected String idModAllegatiParteIntSeparati;
	protected String nomeModAllegatiParteIntSeparati;
	protected String uriModAllegatiParteIntSeparati;
	protected String tipoModAllegatiParteIntSeparati;
	protected String idModAllegatiParteIntSeparatiXPubbl;
	protected String nomeModAllegatiParteIntSeparatiXPubbl;
	protected String uriModAllegatiParteIntSeparatiXPubbl;
	protected String tipoModAllegatiParteIntSeparatiXPubbl;
	protected Boolean flgAppendiceDaUnire;
	protected String idModAppendice;
	protected String nomeModAppendice;
	protected String uriModAppendice;
	protected String tipoModAppendice;
	protected String uriAppendice;
	protected String displayFilenameModAppendice;
	protected String idModFoglioFirme;
	protected String nomeModFoglioFirme;
	protected String displayFilenameModFoglioFirme;
	protected String idModFoglioFirme2;
	protected String nomeModFoglioFirme2;
	protected String displayFilenameModFoglioFirme2;
	protected String idModSchedaTrasp;
	protected String nomeModSchedaTrasp;
	protected String displayFilenameModSchedaTrasp;
	protected String idModAssDocTask;
	protected String nomeModAssDocTask;
	protected String displayFilenameModAssDocTask;
	
	protected Boolean flgUnioneFile;
	protected Boolean flgTimbraFile;
	protected Boolean flgFirmaFile;
	protected Boolean flgShowAnteprimaFileDaFirmare;
	protected Boolean flgFirmaAutomatica;
	protected Boolean flgPubblica;
	protected Boolean flgInvioNotEmail;
	protected Boolean flgDatiSpesaEditabili;
	
	protected String tipoEventoSIB;
	protected Set<String> esitiTaskEventoSIB;	
	protected HashMap<String, String> mappaTipiEventoSIBXEsitoTask;	
	protected String idUoDirAdottanteSIB;
	protected String codUoDirAdottanteSIB;
	protected String desUoDirAdottanteSIB;
	
	protected Boolean flgAttivaUploadPdfAtto;
	protected Boolean flgAttivaUploadPdfAttoOmissis;
	
	protected Boolean flgAttivaRequestMovimentiDaAMC;
	protected Boolean flgAttivaSalvataggioMovimentiDaAMC;	
	protected Boolean flgEscludiFiltroCdCVsAMC;
	
	protected HashMap<String, String> mappaTipiEventoContabiliaXEsitoTask;
	
	protected HashMap<String, String> mappaTipiEventoSICRAXEsitoTask;
	
	protected HashMap<String, String> mappaTipiEventoCWOLXEsitoTask;
	protected Boolean flgRecuperaMovimentiContabDefinitivi;
	
	protected HashMap<String, String> mappaWarningMsgXEsitoTask;
	
	protected String esitoTaskDaPreimpostare;
	protected String msgTaskDaPreimpostare;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	protected RecordList listaRecordModelli;
	protected Record allegatoGeneratoDaModelloTask;
	
	protected Set<String> esitiTaskOk;	
	protected Set<String> esitiTaskKo;	
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;
	
	protected Set<String> esitiTaskAzioni;
	
	protected HashSet<String> attributiAddDocTabsDatiStorici;

	protected Record attrEsito;
	protected Record attrEsitoNotEmail;
	protected String messaggio;
	protected String msgTaskProvvisorio;
	
	protected Map<String, Record> mappaInfoFirmaGrafica;
	
	private final int ALT_POPUP_ERR_MASS = 375;
	private final int LARG_POPUP_ERR_MASS = 620;
	
	public TaskNuovaPropostaAtto2CompletaDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, getAttributAddDocTabs(lRecordEvento));

		instance = this;

		this.recordEvento = lRecordEvento;
		this.recordParametriTipoAtto = lRecordEvento != null ? lRecordEvento.getAttributeAsRecord("parametriTipoAtto") : null;
		this.flgPubblicazioneAllegatiUguale = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null;
		this.flgSoloPreparazioneVersPubblicazione = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgSoloPreparazioneVersPubblicazione") : null;
		this.flgSoloOmissisModProprietaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgSoloOmissisModProprietaFile") : null;		
		this.flgCtrlMimeTypeAllegPI = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgCtrlMimeTypeAllegPI") : null;
		
		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.idUd = idUd;		
		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParereTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParereTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		this.flgNoPubblTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgNoPubblTaskDoc") : null;
		this.flgPubblicaSeparatoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblicaSeparatoTaskDoc") : null;
		
		this.idModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertina") : null;
		this.nomeModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertina") : null;
		this.uriModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertina") : null;
		this.tipoModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertina") : null;		
		this.idModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertinaFinale") : null;
		this.nomeModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertinaFinale") : null;
		this.uriModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertinaFinale") : null;
		this.tipoModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertinaFinale") : null;
		this.idModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("idModAllegatiParteIntSeparati") : null;
		this.nomeModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAllegatiParteIntSeparati") : null;
		this.uriModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAllegatiParteIntSeparati") : null;
		this.tipoModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAllegatiParteIntSeparati") : null;
		this.idModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("idModAllegatiParteIntSeparatiXPubbl") : null;
		this.nomeModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAllegatiParteIntSeparatiXPubbl") : null;
		this.uriModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAllegatiParteIntSeparatiXPubbl") : null;
		this.tipoModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAllegatiParteIntSeparatiXPubbl") : null;
		this.flgAppendiceDaUnire = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAppendiceDaUnire") : null;
		this.idModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("idModAppendice") : null;
		this.nomeModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAppendice") : null;
		this.uriModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAppendice") : null;
		this.tipoModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAppendice") : null;
		this.uriAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriAppendice") : null;
		this.displayFilenameModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAppendice") : null;
		this.idModFoglioFirme = lRecordEvento != null ? lRecordEvento.getAttribute("idModFoglioFirme") : null;
		this.nomeModFoglioFirme = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModFoglioFirme") : null;
		this.displayFilenameModFoglioFirme = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModFoglioFirme") : null;
		this.idModFoglioFirme2 = lRecordEvento != null ? lRecordEvento.getAttribute("idModFoglioFirme2") : null;
		this.nomeModFoglioFirme2 = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModFoglioFirme2") : null;
		this.displayFilenameModFoglioFirme2 = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModFoglioFirme2") : null;
		this.idModSchedaTrasp = lRecordEvento != null ? lRecordEvento.getAttribute("idModSchedaTrasp") : null;
		this.nomeModSchedaTrasp = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModSchedaTrasp") : null;
		this.displayFilenameModSchedaTrasp = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModSchedaTrasp") : null;
		this.idModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idModAssDocTask") : null;		
		this.nomeModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAssDocTask") : null;
		this.displayFilenameModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : null;
		
		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgTimbraFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgTimbraFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgShowAnteprimaFileDaFirmare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgShowAnteprimaFileDaFirmare") : null;		
		this.flgFirmaAutomatica = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgDocActionsFirmaAutomatica") : null;
		this.flgPubblica = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblica") : null;
		this.flgInvioNotEmail = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioNotEmail") : null;
		this.flgDatiSpesaEditabili = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgDatiSpesaEditabili") : null;
		this.flgProtocollazioneProsa = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgProtocollazioneProsa") : null;
		this.flgFirmaVersPubblAggiornata = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaVersPubblAggiornata") : null;
		
		this.tipoEventoSIB = lRecordEvento != null ? lRecordEvento.getAttribute("tipoEventoSIB") : null;
		this.idUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("idUoDirAdottanteSIB") : null;
		this.codUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("codUoDirAdottanteSIB") : null;
		this.desUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("desUoDirAdottanteSIB") : null;
		
		this.flgAttivaUploadPdfAtto = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaUploadPdfAtto") : false;
		this.flgAttivaUploadPdfAttoOmissis = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaUploadPdfAttoOmissis") : false;
		
		this.flgAttivaRequestMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaRequestMovimentiDaAMC") : null;
		this.flgAttivaSalvataggioMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaSalvataggioMovimentiDaAMC") : null;
		this.flgEscludiFiltroCdCVsAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgEscludiFiltroCdCVsAMC") : null;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		this.listaRecordModelli = dettaglioPraticaLayout.getListaModelliAttivita(activityName);

		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if(listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskOk.getLength(); i++) {				
				Record esito = listaEsitiTaskOk.get(i);
				esitiTaskOk.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaEsitiTaskKo = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskKo") : null;
		if(listaEsitiTaskKo != null && listaEsitiTaskKo.getLength() > 0) {
			esitiTaskKo = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskKo.getLength(); i++) {				
				Record esito = listaEsitiTaskKo.get(i);
				esitiTaskKo.add(esito.getAttribute("valore"));
			}			
		}

		RecordList listaControlliXEsitiTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("controlliXEsitiTaskDoc") : null;
		if (listaControlliXEsitiTaskDoc != null && listaControlliXEsitiTaskDoc.getLength() > 0) {
			controlliXEsitiTaskDoc = new HashMap<String, Record>();
			for (int i = 0; i < listaControlliXEsitiTaskDoc.getLength(); i++) {
				Record recordControllo = listaControlliXEsitiTaskDoc.get(i);
				controlliXEsitiTaskDoc.put(recordControllo.getAttribute("esito"), recordControllo);
			}
		}
		
		RecordList listaValoriEsito = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("valoriEsito") : null;
		if (listaValoriEsito != null && listaValoriEsito.getLength() > 0) {
			valoriEsito = new HashSet<String>();
			for (int i = 0; i < listaValoriEsito.getLength(); i++) {
				valoriEsito.add(listaValoriEsito.get(i).getAttribute("valore"));
			}
		}
		
		RecordList listaEsitiTaskAzioni = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskAzioni") : null;
		if(listaEsitiTaskAzioni != null && listaEsitiTaskAzioni.getLength() > 0) {
			esitiTaskAzioni = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskAzioni.getLength(); i++) {				
				Record esito = listaEsitiTaskAzioni.get(i);
				esitiTaskAzioni.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaEsitiTaskEventoSIB = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskEventoSIB") : null;
		if(listaEsitiTaskEventoSIB != null && listaEsitiTaskEventoSIB.getLength() > 0) {
			esitiTaskEventoSIB = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskEventoSIB.getLength(); i++) {				
				Record esito = listaEsitiTaskEventoSIB.get(i);
				esitiTaskEventoSIB.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaTipiEventoSIBXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoSIBXEsitoTask") : null;
		if(listaTipiEventoSIBXEsitoTask != null && listaTipiEventoSIBXEsitoTask.getLength() > 0) {
			mappaTipiEventoSIBXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoSIBXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoSIBXEsitoTask.get(i);
				mappaTipiEventoSIBXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaTipiEventoContabiliaXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoContabiliaXEsitoTask") : null;
		if(listaTipiEventoContabiliaXEsitoTask != null && listaTipiEventoContabiliaXEsitoTask.getLength() > 0) {
			mappaTipiEventoContabiliaXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoContabiliaXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoContabiliaXEsitoTask.get(i);
				mappaTipiEventoContabiliaXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaTipiEventoSICRAXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoSICRAXEsitoTask") : null;
		if(listaTipiEventoSICRAXEsitoTask != null && listaTipiEventoSICRAXEsitoTask.getLength() > 0) {
			mappaTipiEventoSICRAXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoSICRAXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoSICRAXEsitoTask.get(i);
				mappaTipiEventoSICRAXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaTipiEventoCWOLXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoCWOLXEsitoTask") : null;
		if(listaTipiEventoCWOLXEsitoTask != null && listaTipiEventoCWOLXEsitoTask.getLength() > 0) {
			mappaTipiEventoCWOLXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoCWOLXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoCWOLXEsitoTask.get(i);
				mappaTipiEventoCWOLXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		flgRecuperaMovimentiContabDefinitivi = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgRecuperaMovimentiContabDefinitivi") : null;
		
		RecordList listaWarningMsgXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("warningMsgXEsitoTask") : null;
		if(listaWarningMsgXEsitoTask != null && listaWarningMsgXEsitoTask.getLength() > 0) {
			mappaWarningMsgXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaWarningMsgXEsitoTask.getLength(); i++) {				
				Record warningMsgXEsito = listaWarningMsgXEsitoTask.get(i);
				mappaWarningMsgXEsitoTask.put(warningMsgXEsito.getAttribute("esito"), warningMsgXEsito.getAttribute("warningMsg"));
			}			
		}
		
		this.esitoTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("esitoTaskDaPreimpostare") : null;		
		this.msgTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("msgTaskDaPreimpostare") : null;
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TASTI_AVANZAMENTO_TASK_SEPARATI")) {
			this.msgTaskProvvisorio = lRecordEvento != null ? lRecordEvento.getAttribute("msgTaskProvvisorio") : null;;
		}
		
		// lista dei tab di attributi dinamici che gestiscono i dati storici
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			attributiAddDocTabsDatiStorici = new HashSet<String>();
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("flgDatiStorici") != null && "1".equals(tab.getAttribute("flgDatiStorici"))) {
					attributiAddDocTabsDatiStorici.add(tab.getAttribute("codice"));
				}
			}
		}
		
		// Parametri del'eventuale firma grafica del passo
		RecordList listaInfoFirmaGrafica = lRecordEvento.getAttributeAsRecordList("infoFirmaGrafica");
		mappaInfoFirmaGrafica = new LinkedHashMap<>();
		if (listaInfoFirmaGrafica != null && listaInfoFirmaGrafica.getLength() > 0) {
			for (int i = 0; i < listaInfoFirmaGrafica.getLength(); i++) {
				mappaInfoFirmaGrafica.put("ruoloFirmaGrafica" + i, listaInfoFirmaGrafica.get(i));
			}
		}
		
		build();
	}
	
	@Override
	public boolean skipSuperBuild() {
		return true; // evito di fare la build nel costruttore della superclasse, in modo da farla poi alla fine, dopo aver inizializzato recordEvento e le altre variabili che mi servono nella build
	}
	
	public static LinkedHashMap<String, String> getAttributAddDocTabs(Record lRecordEvento) {
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
			}
		}
		return tabs;
	}
	
	public HashSet<String> getAttributiAddDocTabsDatiStorici() {
		return attributiAddDocTabsDatiStorici;
	}
	
	public void visualizzaDatiStorici() {

		if (attributiAddDocTabsDatiStorici != null && attributiAddDocTabsDatiStorici.size() > 0) {

			final TabSet tabSetDatiStorici = new TabSet();

			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			// lGwtRestService.addParam("suffisso", "_CMMI");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			lGwtRestService.addParam("flgDatiStorici", "true");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", tipoDocumento);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {
						for (final String key : attributiAddDocTabsDatiStorici) {
							RecordList attributiAddCategoria = new RecordList();
							for (int i = 0; i < attributiAdd.getLength(); i++) {
								Record attr = attributiAdd.get(i);
								if (attr.getAttribute("categoria") != null && attr.getAttribute("categoria").equalsIgnoreCase(key)) {
									attributiAddCategoria.add(attr);
								}
							}
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, null, null);
								detail.setCanEdit(false);
								String messaggioTabDatiStorici = getMessaggioTabDatiStorici(key);
								if (messaggioTabDatiStorici != null && !"".equals(messaggioTabDatiStorici)) {
									Label labelMessaggioTabDatiStorici = new Label(messaggioTabDatiStorici);
									labelMessaggioTabDatiStorici.setAlign(Alignment.LEFT);
									labelMessaggioTabDatiStorici.setWidth100();
									labelMessaggioTabDatiStorici.setHeight(2);
									labelMessaggioTabDatiStorici.setPadding(5);
									detail.addMember(labelMessaggioTabDatiStorici, 0);
								}

								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);

								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);

								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);

								tabSetDatiStorici.addTab(tab);
							}
						}
						AurigaLayout.addModalWindow("datiStorici", "Dati storici", "protocollazione/variazioni.png", tabSetDatiStorici);
					}
				}
			});
		}
	}
	
	@Override
	public String getMessaggioTab(String tabID) {
		
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTab");
				}
			}
		}
		return null;
	}

	public String getMessaggioTabDatiStorici(String tabID) {
		
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTabDatiStorici");
				}
			}
		}
		return null;
	}
	
	@Override
	public void afterCaricaAttributiDinamiciDoc() {
		
		super.afterCaricaAttributiDinamiciDoc();
		try {
			if (codTabDefault != null && !"".equals(codTabDefault)) {
				tabSet.selectTab(codTabDefault);
			} else {
				tabSet.selectTab(0);
			}
		} catch (Exception e) {
		}
		afterShow();
	}
	
	public boolean hasActionUnioneFile() {
		return flgUnioneFile != null && flgUnioneFile;
	}
	
	public boolean hasActionTimbra() {
		return flgTimbraFile != null && flgTimbraFile;
	}
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean isShowAnteprimaFileDaFirmare() {
		return flgShowAnteprimaFileDaFirmare != null && flgShowAnteprimaFileDaFirmare;
	}
	
	public boolean hasActionFirmaAutomatica() {
		return flgFirmaAutomatica != null && flgFirmaAutomatica;
	}
	
	public boolean hasActionPubblica() {
		return flgPubblica != null && flgPubblica;
	}
	
	public boolean isDaPubblicare() {
		boolean flgDaPubblicare = false;
		if(showAttributoCustomCablato("FLG_PUBBL_ALBO")) {
			flgDaPubblicare = _FLG_SI.equals(getValueAsString("flgPubblAlbo"));							
		} else {
			if(showAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO")) {
				flgDaPubblicare = !"".equals(getValueAsString("tipoDecorrenzaPubblAlbo")); 
			} else if(showAttributoCustomCablato("PUBBL_ALBO_DAL")) {
				flgDaPubblicare = getValueAsDate("dataPubblAlboDal") != null;
			}
		} 
		
		return hasActionPubblica() && flgDaPubblicare;
	}
	
	public boolean hasActionInvioNotEmail() {
		return flgInvioNotEmail != null && flgInvioNotEmail;
	}
	
	public boolean isDatiSpesaEditabili() {
		return flgDatiSpesaEditabili != null && flgDatiSpesaEditabili;
	}
	
	public boolean hasActionProtocollazioneProsa() {
		return flgProtocollazioneProsa != null && flgProtocollazioneProsa;
	}
	
	public boolean hasActionFirmaVersPubblAggiornata() {
		return flgFirmaVersPubblAggiornata != null && flgFirmaVersPubblAggiornata;
	}
	
	public boolean hasModelloAllegatiParteIntSeparatiXPubbl() {
		return idModAllegatiParteIntSeparatiXPubbl != null && !"".equals(idModAllegatiParteIntSeparatiXPubbl);					
	}
	
	/*
	public boolean isEsitoTaskSelezionatoOk() {
		return isEsitoTaskOk(attrEsito);
	}
	*/
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}
	
	public boolean isEsitoTaskKo(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);		
	}
	
	public boolean isEsitoTaskAzioniValorizzato() {
		return esitiTaskAzioni != null; 		
	}
	
	public boolean isEsitoTaskAzioni(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskAzioni == null || (esito != null && esitiTaskAzioni != null && esitiTaskAzioni.contains(esito)));		
	}
	
	public boolean isEsitoTaskEventoSIBValorizzato() {
		return esitiTaskEventoSIB != null;		
	}
		
	public boolean isEsitoTaskEventoSIB(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return esito != null && esitiTaskEventoSIB != null && esitiTaskEventoSIB.contains(esito);		
	}
	
	public String getEventoSIBXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoSIBXEsitoTask != null) {
				if(mappaTipiEventoSIBXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoSIBXEsitoTask.get(esito);
				} else if(mappaTipiEventoSIBXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoSIBXEsitoTask.get("#ANY");
				}				
			}
			return null;
		} else {
			return mappaTipiEventoSIBXEsitoTask != null ? mappaTipiEventoSIBXEsitoTask.get("#ANY") : null;			
		}			
	}

	public String getEventoContabiliaXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoContabiliaXEsitoTask != null) {
				if(mappaTipiEventoContabiliaXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoContabiliaXEsitoTask.get(esito);
				} else if(mappaTipiEventoContabiliaXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoContabiliaXEsitoTask.get("#ANY");
				}				
			}
			return null;
		} else {
			return mappaTipiEventoContabiliaXEsitoTask != null ? mappaTipiEventoContabiliaXEsitoTask.get("#ANY") : null;			
		}		
	}
	
	public String getEventoSICRAXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoSICRAXEsitoTask != null) {
				if(mappaTipiEventoSICRAXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoSICRAXEsitoTask.get(esito);
				} else if(mappaTipiEventoSICRAXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoSICRAXEsitoTask.get("#ANY");
				}				
			}
			return null;			
		} else {
			return mappaTipiEventoSICRAXEsitoTask != null ? mappaTipiEventoSICRAXEsitoTask.get("#ANY") : null;			
		}		
	}
	
	public String getEventoCWOLXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoCWOLXEsitoTask != null) {
				if(mappaTipiEventoCWOLXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoCWOLXEsitoTask.get(esito);
				} else if(mappaTipiEventoCWOLXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoCWOLXEsitoTask.get("#ANY");
				}				
			}
			return null;			
		} else {
			return mappaTipiEventoCWOLXEsitoTask != null ? mappaTipiEventoCWOLXEsitoTask.get("#ANY") : null;			
		}		
	}
	
	public boolean isAttivaRequestMovimentiDaAMC() {
		return flgAttivaRequestMovimentiDaAMC;
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC() {
		return flgAttivaSalvataggioMovimentiDaAMC;
	}	
	
	public boolean isEscludiFiltroCdCVsAMC() {
		return flgEscludiFiltroCdCVsAMC;
	}	
	
	public boolean isEseguibile() {
		boolean isEseguibile = true;
		if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
		}
		return isEseguibile;
	}
	
	public boolean isSoloPreparazioneVersPubblicazione() {
		boolean isSoloPreparazioneVersPubblicazione = false;
		if (recordEvento != null && recordEvento.getAttribute("flgSoloPreparazioneVersPubblicazione") != null) {
			isSoloPreparazioneVersPubblicazione = recordEvento.getAttributeAsBoolean("flgSoloPreparazioneVersPubblicazione");
		}
		return isSoloPreparazioneVersPubblicazione;
	}
	
	public boolean isSoloOmissisModProprietaFile() {
		boolean isSoloOmissisModProprietaFile = false;
		if (recordEvento != null && recordEvento.getAttribute("flgSoloOmissisModProprietaFile") != null) {
			isSoloOmissisModProprietaFile = recordEvento.getAttributeAsBoolean("flgSoloOmissisModProprietaFile");
		}
		return isSoloOmissisModProprietaFile;
	}
	
	public boolean isReadOnly() {
		boolean isReadOnly = false;
		if (recordEvento != null && recordEvento.getAttribute("flgReadOnly") != null) {
			isReadOnly = recordEvento.getAttributeAsBoolean("flgReadOnly");
		}
		return isReadOnly;
	}
	
	@Override
	protected String getIdUd() {
		return idUd;
	}
	
	@Override
	public String getIdProcessTask() {
		return idProcess;
	}
	
	@Override
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}
	
	/*********************************************************************************************/
	
	@Override
	public boolean showDesUORegistrazioneItem() {
		return false; // nei task degli atti non va mostrato (si vede già nell'intestazione)
	}
		
//	@Override
//	public boolean showDetailSectionPubblicazione() {
//		Boolean flgCompilaDatiPubblicazione = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgCompilaDatiPubblicazione") : null;
//		if (isEseguibile() && /*hasActionPubblica() &&*/ flgCompilaDatiPubblicazione != null && flgCompilaDatiPubblicazione) {
//			return true;
//		}
//		return false;		
//	}
	
	public boolean isCompilaDatiPubblicazione() {
		Boolean flgCompilaDatiPubblicazione = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgCompilaDatiPubblicazione") : null;
		if (isEseguibile() && /*hasActionPubblica() &&*/ flgCompilaDatiPubblicazione != null && flgCompilaDatiPubblicazione) {
			return true;
		}
		return false;	
	}
	
	@Override
	public Date getDataInizioPubblicazioneValue() {
		String dataInizioPubblicazione = recordEvento != null ? recordEvento.getAttributeAsString("dataInizioPubblicazione") : null;
		return dataInizioPubblicazione != null ? DateTimeFormat.getFormat("dd/MM/yyyy").parse(dataInizioPubblicazione) : null;
	}
	
	@Override
	public String getGiorniPubblicazioneValue() {
		String giorniPubblicazione = recordEvento != null ? recordEvento.getAttributeAsString("giorniPubblicazione") : null;
		return giorniPubblicazione;
	}
	
	/*********************************************************************************************/
	
	@Override
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}
	
	@Override
	public HashSet<String> getTipiModelliAttiInDocFasc() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}

	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio") : null;
	}

	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo") : null;
	}
	
	@Override
	public String getNomeTastoSalvaDefinitivo_2() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo_2") : null;
	}
	
	public Boolean getFlgSoloPreparazioneVersPubblicazione() {
		return flgSoloPreparazioneVersPubblicazione != null && flgSoloPreparazioneVersPubblicazione;		
	}
	
	public Boolean getFlgSoloOmissisModProprietaFile() {
		return flgSoloOmissisModProprietaFile != null && flgSoloOmissisModProprietaFile;		
	}
	
	@Override
	public boolean hasDocumento() {
		return false;
	}

	public void afterShow() {
		
	}
	
	public void soloPreparazioneVersPubblicazioneMode() {
		if (isReadOnly()) {
			readOnlyMode();
		} else {
			editMode();
		}
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem)listaAllegatiItem).soloPreparazioneVersPubblicazioneMode();
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem)listaAllegatiItem).soloPreparazioneVersPubblicazioneMode();
			} 			
		}
	}
	
	public void soloOmissisModProprietaFileMode() {
		if (isReadOnly()) {
			readOnlyMode();
		} else {
			editMode();
		}
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem)listaAllegatiItem).soloOmissisModProprietaFileMode();
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem)listaAllegatiItem).soloOmissisModProprietaFileMode();
			} 			
		}
	}

	public void readOnlyMode() {
		viewMode();
//		if(dataInizioPubblicazioneItem != null) {
//			dataInizioPubblicazioneItem.setCanEdit(true);
//		}		
//		if(giorniPubblicazioneItem != null) {
//			giorniPubblicazioneItem.setCanEdit(true);
//		}	
		if(listaAllegatiItem != null) {
			if(isAllegatiNonParteIntegranteNonEditabiliInTask()) {
				listaAllegatiItem.setCanEdit(false);
			} else {
				if(listaAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)listaAllegatiItem).readOnlyMode();
				} else if(listaAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)listaAllegatiItem).readOnlyMode();
				}
			}
		}
		if(isDatiSpesaEditabili()) {
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CORRENTE_ID)) {
				setCanEdit(true, form);
			}
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID)) {
				setCanEdit(true, form);
			}	
		}
	}
	
	public void editMode() {
		super.editMode();	
		if(!isDatiSpesaEditabili()) {
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CORRENTE_ID)) {
				setCanEdit(false, form);
			}
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID)) {
				setCanEdit(false, form);
			}	
		}
	}

	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}
	
	@Override
	public void back() {
		dettaglioPraticaLayout.caricaDettaglioEventoAnnulla(nome);
	}

	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}

	public Map<String, Object> getAttributiDinamici() {
		return new HashMap<String, Object>();
	}

	public Map<String, String> getTipiAttributiDinamici() {
		return new HashMap<String, String>();
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {
		loadDettPropostaAtto(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);
				if (isEseguibile() && !isForzaModificaAttoDaSbloccare()) {
					if(isSoloPreparazioneVersPubblicazione()) {
						soloPreparazioneVersPubblicazioneMode();
					} else if (isSoloOmissisModProprietaFile()) {
						soloOmissisModProprietaFileMode();
					} else if (isReadOnly()) {
						readOnlyMode();
					} else {
						editMode();
					}
				} else {
					viewMode();
				}
			}
		});
	}
	
	public void loadDettPropostaAtto(final ServiceCallback<Record> callback) {
		
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", idProcess);
		lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", activityName);
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}								
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		
		start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
		
		lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				GWT.log("loadDatiUd() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					recordFromLoadDett = new Record(lRecord.getJsObj());
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
					if (isEseguibile() && !isReadOnly()) {
						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
							RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
								int posModello = !listaRecordModelli.get(i).getAttributeAsBoolean("flgCreaNuovoDoc") ? getPosAllegatoFromTipo(idTipoModello, listaAllegati) : -1;
								if (posModello != -1) {
									listaAllegati.removeAt(posModello);
								}
							}
							lRecord.setAttribute("listaAllegati", listaAllegati);
						}
					}
					
					start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

					editRecord(lRecord);
					
					GWT.log("editRecordUd() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
					
					// IMPORTANTE: quando ricarico i dati da DB devo finire la renderizzazione a maschera prima di chiamare la getRecordToSave(),
					// altrimenti l'html dei CKEditor risulta indentato male e questo crea problemi durante l'iniezione nel modello
					//TODO DA PORTARE ANCHE NELLE ALTRE MASCHERE DEI TASK		
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {		
							if(callback != null) {
								callback.execute(lRecord);
							}
						}
					});
				}
			}
		});
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, null, callback);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, boolean salvaDatiDefinitivoSenzaEseguiAzioni, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, null, salvaDatiDefinitivoSenzaEseguiAzioni, callback);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final Map<String, String> otherExtraparam, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, otherExtraparam, false, callback);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final Map<String, String> otherExtraparam, final boolean salvaDatiDefinitivoSenzaEseguiAzioni, final ServiceCallback<Record> callback) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {				
				setSaved(true);
				final Record lRecordToSave = getRecordToSave();
				// Sbianco il valore in modo che non resti a maschera in caso di errori
				hiddenForm.setValue("uriDocGeneratoFormatoOdt", "");
				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lNuovaPropostaAtto2CompletaDataSource.setTimeout(Integer.MAX_VALUE); // setto al massimo il timeout in salvataggio per evitare di eseguire la setMovimentiAtti di Sicra senza poi allineare i dati a maschera con le modifiche, causando una successiva richiamata identica a quella già eseguita (con conseguenti movimenti doppioni lato Sicra)
				if(otherExtraparam != null) {
					for(String key : otherExtraparam.keySet()) {
						lNuovaPropostaAtto2CompletaDataSource.addParam(key, otherExtraparam.get(key));
					}
				}
				lNuovaPropostaAtto2CompletaDataSource.addParam("isAttivaGestioneErroriFile", "true");
				lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", idProcess);
				lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", activityName);
				lNuovaPropostaAtto2CompletaDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
				lNuovaPropostaAtto2CompletaDataSource.addParam("msgEsitoTask", messaggio);
				if(isReadOnly() || (isPresenteAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE") && !getFlgEditabileAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE"))) {
					// passo l'idTask corrente solo se il task è readonly o se nell'xml di configurazione è presente l'attributo #ALLEGATI_PARTE_INTEGRANTE non è editabile (colonna 10 uguale a zero o null)
					lNuovaPropostaAtto2CompletaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
				}
				if(isAttivaSalvataggioMovimentiDaAMC()) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaSalvataggioMovimentiDaAMC", "true");
				}
				final Map<String, Object> attributiDinamiciEvent = getAttributiDinamici();
				final Map<String, String> tipiAttributiDinamiciEvent = getTipiAttributiDinamici();
				final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
				// se è il salvataggio finale
				if (!flgIgnoreObblig) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("flgSalvataggioDefinitivoPreCompleteTask", "true");
					if (salvaDatiDefinitivoSenzaEseguiAzioni) {
						lNuovaPropostaAtto2CompletaDataSource.addParam("flgSalvaDatiDefinitivoSenzaEseguiAzioni", "true");
					}
					if(hasActionProtocollazioneProsa()) {
						boolean eseguiAzione = false;
						if(isEsitoTaskAzioniValorizzato()) {
							if(isEsitoTaskAzioni(attrEsito)) {
								eseguiAzione = true;
							}
						} else if(isEsitoTaskOk(attrEsito)) {
							eseguiAzione = true;
						}
						if(eseguiAzione) {
							lRecordToSave.setAttribute("flgProtocollazioneProsa", "true");
						}
					}
					String eventoSIBXEsito = getEventoSIBXEsitoTask(attrEsito);
					if(eventoSIBXEsito != null && !"".equals(eventoSIBXEsito)) {
						lRecordToSave.setAttribute("eventoSIB", eventoSIBXEsito);
					} else if(isEsitoTaskEventoSIBValorizzato()) {
						if(isEsitoTaskEventoSIB(attrEsito)) {
							// se mi arriva valorizzata la lista di esiti per cui deve essere eseguito l'evento su SIB, e se l'esito selezionato è uno di questi, allora lo passo
							lRecordToSave.setAttribute("eventoSIB", tipoEventoSIB != null ? tipoEventoSIB : "");
						}
					} else if(isEsitoTaskOk(attrEsito)) {			
						// se l'esito è ok o non c'è nessun esito allora passo l'evento con cui richiamare il servizio aggiornaAtto() di SIB
						lRecordToSave.setAttribute("eventoSIB", tipoEventoSIB != null ? tipoEventoSIB : "");				
					}								
					lRecordToSave.setAttribute("eventoContabilia", getEventoContabiliaXEsitoTask(attrEsito));			
					lRecordToSave.setAttribute("eventoSICRA", getEventoSICRAXEsitoTask(attrEsito));		
					lRecordToSave.setAttribute("eventoCWOL", getEventoCWOLXEsitoTask(attrEsito));
					lRecordToSave.setAttribute("flgRecuperaMovimentiContabDefinitivi", flgRecuperaMovimentiContabDefinitivi);
					if(isDeterminaConSpesa() && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
						if(allegatoGeneratoDaModelloTask != null) {
							lRecordToSave.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
						}								
					}
					if (!isAvvioPropostaAtto() && isEseguibile() && !isReadOnly()) {
						lNuovaPropostaAtto2CompletaDataSource.addParam("versionaFileDispositivo", "true");
						if(hasPrimarioDatiSensibili()) {
							lNuovaPropostaAtto2CompletaDataSource.addParam("hasPrimarioDatiSensibili", "true");
						}
					}
					if(attrEsito != null) {
						lNuovaPropostaAtto2CompletaDataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
						lNuovaPropostaAtto2CompletaDataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
						attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
						tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
						attrEsitoNotEmail = new Record(attrEsito.toMap());
						// devo sbiancare attrEsito solo quando faccio il salvataggio finale
						attrEsito = null;
					}
					// Nel salvataggio finale il messaggio di completamento task provvisorio viene sbiancato
					lRecordToSave.setAttribute("msgTaskProvvisorio", "");
				} else {
					// Salvo il messaggio di completamento task solo nel salva in bozza
					lRecordToSave.setAttribute("msgTaskProvvisorio", msgTaskProvvisorio);					
				}
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());	
				
				start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

				lNuovaPropostaAtto2CompletaDataSource.updateData(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {						
						GWT.log("salvaDatiUd() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));				
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageErroriFile(response.getData()[0], new ServiceCallback<Record>() {
								
								@Override
								public void execute(final Record lRecord) {
									Boolean flgDatiContabiliADSPNonAllineati = lRecord != null ? lRecord.getAttributeAsBoolean("flgDatiContabiliADSPNonAllineati") : null;							
									if(flgDatiContabiliADSPNonAllineati!=null && flgDatiContabiliADSPNonAllineati){
										Layout.hideWaitPopup();
										SC.warn("I dati non sono allineati con il sistema contabile, allineare i dati prima di riprovare");
										getFormDatiContabiliADSP().setValue("listaDatiContabiliADSP", lRecord.getAttributeAsRecordArray("listaDatiContabiliADSP"));
									}else {
										String esitoSetMovimentiAttoSICRA = lRecord != null ? lRecord.getAttribute("esitoSetMovimentiAttoSICRA") : null;
										String messaggioWarning = lRecord != null ? lRecord.getAttribute("messaggioWarning") : null;
										final String codXSalvataggioConWarning = lRecord != null ? lRecord.getAttribute("codXSalvataggioConWarning") : null;
										if(esitoSetMovimentiAttoSICRA != null && "OK".equalsIgnoreCase(esitoSetMovimentiAttoSICRA)) {
											listaInvioMovimentiContabiliSICRAItem.resetListaMovimentiToDeleteAndInsert();
											lRecordToSave.setAttribute("listaMovimentiSICRAToDelete", new RecordList());
											lRecordToSave.setAttribute("listaMovimentiSICRAToInsert", new RecordList());
										}
										if(messaggioWarning != null && !"".equals(messaggioWarning) && codXSalvataggioConWarning != null && !"".equals(codXSalvataggioConWarning)) {
											Layout.hideWaitPopup();
											salvaDatiConWarning(lNuovaPropostaAtto2CompletaDataSource, lRecordToSave, messaggioWarning, codXSalvataggioConWarning, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													afterSalvaDati(flgIgnoreObblig, esito, attributiDinamiciEvent, tipiAttributiDinamiciEvent, callback);
												}
											});												
										} else {
											if(messaggioWarning != null && !"".equals(messaggioWarning)) {
												AurigaLayout.addMessage(new MessageBean(messaggioWarning, "", MessageType.WARNING));
											}
											afterSalvaDati(flgIgnoreObblig, esito, attributiDinamiciEvent, tipiAttributiDinamiciEvent, callback);
										}
									}
								}
							});
						}
					}
				});
			}
		});
	}
	
	public void manageErroriFile(Record lRecord, ServiceCallback<Record> callback) {
		if(lRecord.getAttributeAsMap("erroriFile") != null && !lRecord.getAttributeAsMap("erroriFile").isEmpty()) {
			Layout.hideWaitPopup();
			HashMap<String, String> erroriFile = (HashMap<String, String>) lRecord.getAttributeAsMap("erroriFile");
			// lo devo fare due volte altrimenti ogni tanto al primo giro non da l'evidenza grafica degli errori sui file allegati appena inseriti
			if(listaAllegatiItem != null) {
				allegatiForm.clearErrors(true);
				if(listaAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)listaAllegatiItem).manageErroriFile(erroriFile);	
				} else if(listaAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)listaAllegatiItem).manageErroriFile(erroriFile);							
				}
			}
			if(listaAllegatiItem != null) {
				allegatiForm.clearErrors(true);
				if(listaAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)listaAllegatiItem).manageErroriFile(erroriFile);	
				} else if(listaAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)listaAllegatiItem).manageErroriFile(erroriFile);							
				}
			}
			showTabErrors(tabSet);
		} else {
			if(callback != null) {
				callback.execute(lRecord);
			}
		}
	}
	
	public void salvaDatiConWarning(final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource, final Record lRecordToSave, String messaggioWarning, final String codXSalvataggioConWarning, final ServiceCallback<Record> callbackSalvaDatiConWarning) {
		AurigaLayout.showConfirmDialogWithWarning("Attenzione!", messaggioWarning, "Ok", "Annulla", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value != null && value) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("codXSalvataggioConWarning", codXSalvataggioConWarning);
					
					start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());

					lNuovaPropostaAtto2CompletaDataSource.updateData(lRecordToSave, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							GWT.log("salvaDatiUd() started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));
							
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record lRecord = response.getData()[0];
								String esitoSetMovimentiAttoSICRA = lRecord != null ? lRecord.getAttribute("esitoSetMovimentiAttoSICRA") : null;
								String messaggioWarning2 = lRecord != null ? lRecord.getAttribute("messaggioWarning") : null;
								final String codXSalvataggioConWarning2 = lRecord != null ? lRecord.getAttribute("codXSalvataggioConWarning") : null;
								if(esitoSetMovimentiAttoSICRA != null && "OK".equalsIgnoreCase(esitoSetMovimentiAttoSICRA)) {
									listaInvioMovimentiContabiliSICRAItem.resetListaMovimentiToDeleteAndInsert();
									lRecordToSave.setAttribute("listaMovimentiSICRAToDelete", new RecordList());
									lRecordToSave.setAttribute("listaMovimentiSICRAToInsert", new RecordList());
								}
								if(messaggioWarning2 != null && !"".equals(messaggioWarning2) && codXSalvataggioConWarning2 != null && !"".equals(codXSalvataggioConWarning2)) {
									salvaDatiConWarning(lNuovaPropostaAtto2CompletaDataSource, lRecordToSave, messaggioWarning2, codXSalvataggioConWarning2, callbackSalvaDatiConWarning);
								} else {
									if(messaggioWarning2 != null && !"".equals(messaggioWarning2)) {
										AurigaLayout.addMessage(new MessageBean(messaggioWarning2, "", MessageType.WARNING));
									}
									if(callbackSalvaDatiConWarning != null) {
										callbackSalvaDatiConWarning.execute(null);
									}
								}
							}
						}
					});
				} else {
//					if(callbackSalvaDatiConWarning != null) {
//						callbackSalvaDatiConWarning.execute(null);
//					}
				}
			}
		});		
	}
	
	public void afterSalvaDati(final boolean flgIgnoreObblig, final String esito, final Map<String, Object> attributiDinamiciEvent, final Map<String, String> tipiAttributiDinamiciEvent, final ServiceCallback<Record> callback) {
		salvaAttributiDinamiciDocAfterSalva(flgIgnoreObblig, rowidDoc, activityName, esito, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Record lRecordEvent = new Record();
				lRecordEvent.setAttribute("idProcess", idProcess);
				lRecordEvent.setAttribute("idEvento", idEvento);
				lRecordEvent.setAttribute("idTipoEvento", idTipoEvento);
				lRecordEvent.setAttribute("idUd", idUd);
				lRecordEvent.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
				if (messaggio != null) {
					lRecordEvent.setAttribute("messaggio", messaggio);
				}
				lRecordEvent.setAttribute("valori", attributiDinamiciEvent);
				lRecordEvent.setAttribute("tipiValori", tipiAttributiDinamiciEvent);
				GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
				if (flgIgnoreObblig) {
					lGWTRestService.addParam("flgIgnoreObblig", "1");
				} 
				lGWTRestService.addParam("skipSuccessMsg", "true");							
				lGWTRestService.call(lRecordEvent, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						object.setAttribute("esito", esito);
						if (callback != null) {
							callback.execute(object);
						} else {
							Layout.hideWaitPopup();
						}
					}
				});
			}
		});
	}
	
	/*
	@Override
	public void editRecord(Record record) {
		
		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editRecord(record);

		if (isEseguibile()) {
			if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
				AllegatoCanvas lAllegatoCanvas = listaAllegatiItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaAllegatiItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);	
					boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
					boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
					boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;
					boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;										
					lAllegatoCanvas.getForm()[0].setValue("flgParere", flgParere);
					if(flgParere) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", false);
						lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
						lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", true);
					} else {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", flgParteDispositivo);
						if(!flgParteDispositivo) {
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", true);
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", false);
							lAllegatoCanvas.getForm()[0].setValue("flgDatiSensibili", false);
							lAllegatoCanvas.manageOnChangedFlgDatiSensibili();
						} else {
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);	
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", flgPubblicaSeparato);						
						}
					}
				}					
			}
		}
	}
	*/
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate(); // perche estendo NuovaPropostaAtto2CompletaDetail
		
		/*
		if(isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk()) {
			if(showTabDatiSpesaCorrente() && showDetailSectionInvioDatiSpesaCorrente() && !isPresentiDatiStoriciCorrente()) {
				invioDatiSpesaCorrenteForm.setFieldErrors("listaInvioDatiSpesaCorrente", "Nessun impegno / accertamento specificato nella griglia dei dati della spesa corrente: devi indicarne almeno uno");
				detailSectionInvioDatiSpesaCorrente.open();
				valid = false;
			}
			if(activityName != null && (activityName.equals("controllo_bilancio_corr") || activityName.equals("verifica_po_bilancio_corr"))) {
				if(showTabDatiSpesaCorrente() && showDetailSectionDatiContabiliSIBCorrente() && !isPresentiDatiContabiliSIBCorrente()) {
					invioDatiSpesaCorrenteForm.setFieldErrors("listaDatiContabiliSIBCorrente", "In SIB non risultano impegni/accertamenti o sub associati alla proposta per la parte di spesa corrente: deve essercene almeno uno per poter procedere");
					detailSectionDatiContabiliSIBCorrente.open();
					valid = false;
				}
			}
			if(showTabDatiSpesaContoCapitale() && showDetailSectionInvioDatiSpesaContoCapitale() && !isPresentiDatiStoriciContoCapitale()) {
				invioDatiSpesaContoCapitaleForm.setFieldErrors("listaInvioDatiSpesaContoCapitale", "Nessun impegno / accertamento specificato nella griglia dei dati della spesa in conto capitale: devi indicarne almeno uno");
				detailSectionInvioDatiSpesaContoCapitale.open();
				valid = false;
			}
			if(activityName != null && (activityName.equals("controllo_bilancio_cccap") || activityName.equals("verifica_po_bilancio_ccap") || activityName.equals("validazione_dirigente_ragioneria"))) {
				if(showTabDatiSpesaContoCapitale() && showDetailSectionDatiContabiliSIBContoCapitale() && !isPresentiDatiContabiliSIBContoCapitale()) {
					invioDatiSpesaContoCapitaleForm.setFieldErrors("listaDatiContabiliSIBContoCapitale", "In SIB non risultano impegni/accertamenti o sub associati alla proposta per la parte di spesa in conto capitale: deve essercene almeno uno per poter procedere");
					detailSectionDatiContabiliSIBContoCapitale.open();
					valid = false;
				}
			}	
			if(activityName != null && activityName.equals("verifica_contabilita")) {
				boolean isPresentiDatiContabiliSIBCorrente = showTabDatiSpesaCorrente() && showDetailSectionDatiContabiliSIBCorrente() && isPresentiDatiContabiliSIBCorrente();
				boolean isPresentiDatiContabiliSIBContoCapitale = showTabDatiSpesaContoCapitale() && showDetailSectionDatiContabiliSIBContoCapitale() && isPresentiDatiContabiliSIBContoCapitale();
				if(!isPresentiDatiContabiliSIBCorrente && !isPresentiDatiContabiliSIBContoCapitale) {
					AurigaLayout.addMessage(new MessageBean("In SIB non risultano impegni/accertamenti o sub associati alla proposta: deve essercene almeno uno per poter procedere", "", MessageType.ERROR));
					valid = false;
				}
			}	
		}
		*/
		
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
			Record recordControllo = esito != null && !"".equals(esito) ? controlliXEsitiTaskDoc.get(esito) : null;
			if (attrEsito != null && recordControllo == null) {
			 	final String label = attrEsito.getAttribute("label") != null ? attrEsito.getAttribute("label").toLowerCase() : null;
				String esitoCompleto = label + " " + esito;
				recordControllo = esitoCompleto != null && !"".equals(esitoCompleto) ? controlliXEsitiTaskDoc.get(esitoCompleto) : null;
			}
			if (recordControllo == null) {
				recordControllo = controlliXEsitiTaskDoc.get("#ANY");
			}
			final boolean flgObbligatorio = recordControllo != null && recordControllo.getAttribute("flgObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgObbligatorio"));
			final boolean flgFileObbligatorio = recordControllo != null && recordControllo.getAttribute("flgFileObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgFileObbligatorio"));
			final boolean flgFileFirmato = recordControllo != null && recordControllo.getAttribute("flgFileFirmato") != null
					&& "1".equals(recordControllo.getAttribute("flgFileFirmato"));
			
			if(listaAllegatiItem != null) {
				if(listaAllegatiItem instanceof AllegatiGridItem) {
					/* TODO NUOVA GESTIONE ALLEGATI CON GRIDITEM */
					ListGridRecord lAllegatoRecord = ((AllegatiGridItem)listaAllegatiItem).getAllegatoRecordFromTipo(idTipoTaskDoc);
					if (flgObbligatorio && lAllegatoRecord == null) {
						Record lNewAllegatoRecord = new Record();
						//TODO setFixedTipoFileAllegato
						lNewAllegatoRecord.setAttribute("listaTipiFileAllegato", idTipoTaskDoc);
						lNewAllegatoRecord.setAttribute("idTipoFileAllegato", idTipoTaskDoc);
						lNewAllegatoRecord.setAttribute("descTipoFileAllegato", nomeTipoTaskDoc);
						boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
						boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
						boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;									
						boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;									
						lNewAllegatoRecord.setAttribute("flgParere", flgParere);
						if(flgParere) {
							lNewAllegatoRecord.setAttribute("flgParteDispositivo", false);
							lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", flgNoPubbl);
							lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", true);
						} else {
							lNewAllegatoRecord.setAttribute("flgParteDispositivo", flgParteDispositivo);
							if(!flgParteDispositivo) {
								lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", true);
								lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", false);
								lNewAllegatoRecord.setAttribute("flgDatiSensibili", false);
							} else {
								lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", flgNoPubbl);
								lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", flgPubblicaSeparato);						
							}
						}						
						((AllegatiGridItem)listaAllegatiItem).onClickNewButton(lNewAllegatoRecord);							
					}
					if (lAllegatoRecord != null) {
						String numeroAllegato = lAllegatoRecord.getAttribute("numeroAllegato");
						String uriFileAllegato = lAllegatoRecord.getAttribute("uriFileAllegato");
						InfoFileRecord infoFileAllegato = lAllegatoRecord.getAttribute("infoFile") != null ? new InfoFileRecord(lAllegatoRecord.getAttributeAsRecord("infoFile")) : null;
						List<String> listaErrori = new ArrayList<String>();
						if (flgFileObbligatorio && (uriFileAllegato == null || uriFileAllegato.equals("") || infoFileAllegato == null)) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " è obbligatorio");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmato())) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " non è firmato");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmaValida())) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " presenta una firma non valida alla data odierna");
							valid = false;
						}
						listaAllegatiItem.setErrors(listaErrori.toArray(new String[listaErrori.size()]));					
					}
				} else if(listaAllegatiItem instanceof AllegatiItem) {						
					/* TODO VECCHIA GESTIONE ALLEGATI CON REPLICABLEITEM */
					AllegatoCanvas lAllegatoCanvas = ((AllegatiItem)listaAllegatiItem).getAllegatoCanvasFromTipo(idTipoTaskDoc);
					if (flgObbligatorio && lAllegatoCanvas == null) {
						lAllegatoCanvas = (AllegatoCanvas) ((AllegatiItem)listaAllegatiItem).onClickNewButton();
						lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
						boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
						boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
						boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;									
						boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;									
						lAllegatoCanvas.getForm()[0].setValue("flgParere", flgParere);
						if(flgParere) {
							lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", false);
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", true);
						} else {
							lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", flgParteDispositivo);
							if(!flgParteDispositivo) {
								lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", true);
								lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", false);
								lAllegatoCanvas.getForm()[0].setValue("flgDatiSensibili", false);
								lAllegatoCanvas.manageOnChangedFlgDatiSensibili();
							} else {
								lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
								lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", flgPubblicaSeparato);							
							}
						}						
					}
					if (lAllegatoCanvas != null) {
						String uriFileAllegato = lAllegatoCanvas.getFormValuesAsRecord().getAttribute("uriFileAllegato");
						InfoFileRecord infoFileAllegato = lAllegatoCanvas.getForm()[0].getValue("infoFile") != null ? new InfoFileRecord(
								lAllegatoCanvas.getForm()[0].getValue("infoFile")) : null;
						if (flgFileObbligatorio && (uriFileAllegato == null || uriFileAllegato.equals("") || infoFileAllegato == null)) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file è obbligatorio");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmato())) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file non è firmato");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmaValida())) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file presenta una firma non valida alla data odierna");
							valid = false;
						}
					}	
				}
			}
							
//			if (detailSectionAllegati != null) {
//				detailSectionAllegati.open();
//			}

		}
		return valid;
	}

	public Boolean validateSenzaObbligatorieta() {
		clearTabErrors(tabSet);
		vm.clearErrors(true);
		boolean valid = true;
		//TODO devo gestire anche i form dei tab principali?
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				boolean esitoAttributiAddDocDetail = attributiAddDocDetails.get(key).validateSenzaObbligatorieta();
				valid = valid && esitoAttributiAddDocDetail;
			}
		}
		showTabErrors(tabSet);
		if (valid) {
			setSaved(valid);
		} else {
			reopenAllSections();			
		}
		return valid;
	}
	
	@Override
	public void salvaDatiProvvisorio() {
		// tolgo queste due righe perchè se l'utente preme il bottone "Salva in bozza" mentre sta aspettando che si carichi il modulo hybrid della firma poi si sbianca l'esito
//		messaggio = null;
//		attrEsito = null;		
		salvaWithAggiornaVersDispositivo(null);
	}
	
	public void salvaBeforeSmistaAtto(final ServiceCallback<Record> afterSalvaCallback) {
		salvaWithAggiornaVersDispositivo(afterSalvaCallback);		
	}
	
	public void chiudiAfterSmistaAtto() {
		next();		
	}
	
	public void salvaWithAggiornaVersDispositivo(final ServiceCallback<Record> afterSalvaCallback) {
		final String idModDispositivo = getIdModDispositivo();
		if(AurigaLayout.getParametroDBAsBoolean("VERS_DISPOSITIVO_NUOVA_PROPOSTA_ATTO_2") && AurigaLayout.isPrivilegioAttivo("ATT/SF") 
			&& !isAvvioPropostaAtto() && isEseguibile() && !isReadOnly() && idModDispositivo != null && !"".equals(idModDispositivo)) {
			SC.ask("Vuoi salvare i dati attuali in una nuova versione pdf dell'atto?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					continuaSalvaDatiProvvisorio(value != null && value, afterSalvaCallback);
				}
			});
		} else {
			continuaSalvaDatiProvvisorio(false, afterSalvaCallback);
		}
	}
	
	private void continuaSalvaDatiProvvisorio(boolean flgAggiornaVersDispositivo, final ServiceCallback<Record> afterSalvaCallback) {
		if (validateSenzaObbligatorieta()) {
			Map<String, String> otherExtraparam = new HashMap<String, String>();									
			otherExtraparam.put("flgSalvataggioProvvisorioInBozza", "true");
			if(flgAggiornaVersDispositivo) {
				otherExtraparam.put("versionaFileDispositivo", "true");
				if(hasPrimarioDatiSensibili()) {
					otherExtraparam.put("hasPrimarioDatiSensibili", "true");
				}
			}
			salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							// TODO: qui dovrei ricaricare i tab degli attributi dinamici per vedere le eventuali variazioni
							if(afterSalvaCallback != null) {
								afterSalvaCallback.execute(getRecordToSave());	
							} else {
								reload();
							}
						}
					});
				}
			});
		} else {
			messaggio = null; // qui e in tutti gli altri punti non devo sbiancare messaggio
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	public void salvaDatiDefinitivoOK() {
		// tolgo queste due righe perchè se l'utente preme il bottone "Salva e procedi" mentre sta aspettando che si carichi il modulo hybrid della firma poi si sbianca l'esito
//		messaggio = null;
//		attrEsito = null;
		final String nomeAttrCustomEsitoTask = recordEvento.getAttribute("nomeAttrCustomEsitoTask");
		if (nomeAttrCustomEsitoTask != null && !"".equals(nomeAttrCustomEsitoTask)) {
			GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
			lAttributiDinamiciRecord.setAttribute("rowId", rowId);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
			lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {
	
				@Override
				public void execute(final Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					for (int i = 0; i < attributiAdd.getLength(); i++) {
						final Record attr = attributiAdd.get(i);
						if (attr.getAttribute("nome").equals(nomeAttrCustomEsitoTask)) {													
							String[] esitiValueMap = null;
							if(valoriEsito != null) {
								esitiValueMap = valoriEsito.toArray(new String[valoriEsito.size()]);			
							} else {
								esitiValueMap = new StringSplitterClient(attr.getAttribute("valueMap"), "|*|").getTokens();
							}
							List<String> listaEsitiOkAndWarning = new ArrayList<String>();
							if(esitiValueMap != null && esitiValueMap.length > 0) {
								for(String esito : esitiValueMap) {
									boolean isEsitoTaskOk = (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));;
									boolean isEsitoTaskKo = esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);									
									if(isEsitoTaskOk || !isEsitoTaskKo) {
										listaEsitiOkAndWarning.add(esito);
									}
								}		
							}															
							if(listaEsitiOkAndWarning != null) {
								if(listaEsitiOkAndWarning.size() == 1) {
									selezionaEsito(attr, listaEsitiOkAndWarning.get(0));
								} else {
									Menu esitiMenu = new Menu();									
									for(final String esito : listaEsitiOkAndWarning) {	
										MenuItem esitoMenuItem = new MenuItem(attr != null ? attr.getAttribute("label") + " " + esito : esito);
										esitoMenuItem.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												selezionaEsito(attr, esito);
											}
										});
										esitiMenu.addItem(esitoMenuItem);
									}	
									esitiMenu.showContextMenu();
								}
							}							
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			continuaSalvaDatiDefinitivoWithValidate();
		}
	}
	
	public void salvaDatiDefinitivoKO() {
		// tolgo queste due righe perchè se l'utente preme il bottone "Salva e procedi" mentre sta aspettando che si carichi il modulo hybrid della firma poi si sbianca l'esito
//		messaggio = null;
//		attrEsito = null;
		final String nomeAttrCustomEsitoTask = recordEvento.getAttribute("nomeAttrCustomEsitoTask");
		if (nomeAttrCustomEsitoTask != null && !"".equals(nomeAttrCustomEsitoTask)) {
			GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
			lAttributiDinamiciRecord.setAttribute("rowId", rowId);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
			lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {
	
				@Override
				public void execute(final Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					for (int i = 0; i < attributiAdd.getLength(); i++) {
						final Record attr = attributiAdd.get(i);
						if (attr.getAttribute("nome").equals(nomeAttrCustomEsitoTask)) {													
							String[] esitiValueMap = null;
							if(valoriEsito != null) {
								esitiValueMap = valoriEsito.toArray(new String[valoriEsito.size()]);			
							} else {
								esitiValueMap = new StringSplitterClient(attr.getAttribute("valueMap"), "|*|").getTokens();
							}
							List<String> listaEsitiKo = new ArrayList<String>();
							if(esitiValueMap != null && esitiValueMap.length > 0) {
								for(String esito : esitiValueMap) {
									boolean isEsitoTaskKo = esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);									
									if(isEsitoTaskKo) {										
										listaEsitiKo.add(esito);
									}
								}		
							}															
							if(listaEsitiKo != null) {
								if(listaEsitiKo.size() == 1) {
									selezionaEsito(attr, listaEsitiKo.get(0));
								} else {
									Menu esitiMenu = new Menu();									
									for(final String esito : listaEsitiKo) {										
										MenuItem esitoMenuItem = new MenuItem(attr != null ? attr.getAttribute("label") + " " + esito : esito);
										esitoMenuItem.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(MenuItemClickEvent event) {
												selezionaEsito(attr, esito);
											}
										});
										esitiMenu.addItem(esitoMenuItem);
									}	
									esitiMenu.showContextMenu();
								}
							}							
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			continuaSalvaDatiDefinitivoWithValidate();
		}
	}
	
	public boolean showButtonSalvaDefinitivoKO() {
		if(valoriEsito != null) {
			String[] esitiValueMap = valoriEsito.toArray(new String[valoriEsito.size()]);
			List<String> listaEsitiKo = new ArrayList<String>();
			if(esitiValueMap != null && esitiValueMap.length > 0) {
				for(String esito : esitiValueMap) {
					boolean isEsitoTaskKo = esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);									
					if(isEsitoTaskKo) {										
						listaEsitiKo.add(esito);
					}
				}		
			}																	
			return listaEsitiKo != null && listaEsitiKo.size() > 0;
		} 
		return false;
	}	
	
	public void selezionaEsito(final Record attr, final String esito) {		
		boolean isEsitoTaskOk = (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));;
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_OBBL_MSG_PASSI_WF_NON_OK") && !isEsitoTaskOk && (msgTaskProvvisorio == null || "".equals(msgTaskProvvisorio.trim()))) {
			aggiungiMessaggioTask(true, esito, new ServiceCallback<String>() {
				
				@Override
				public void execute(final String msgTaskProvvisorio) {												
					boolean isForzataModificaAttoWithEsitoKo = getValueAsBoolean("flgForzataModificaAtto") && esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);
					if(isForzataModificaAttoWithEsitoKo) {
						SC.ask("Hai sbloccato la modifica dei dati e/o allegati; sei sicuro di voler completare il passo con questo esito?", new BooleanCallback() {
							
							@Override
							public void execute(Boolean value) {
								if (value != null && value) {
									selezionaEsitoWithWarning(esito, new ServiceCallback<String>() {
										
										@Override
										public void execute(String esito) {	
											messaggio = msgTaskProvvisorio;
											attrEsito = new Record(attr.toMap());
											attrEsito.setAttribute("valore", esito);													
											continuaSalvaDatiDefinitivoWithValidate();
										}
									});													
								}
							}
						});
					} else {
						selezionaEsitoWithWarning(esito, new ServiceCallback<String>() {
							
							@Override
							public void execute(String esito) {	
								messaggio = msgTaskProvvisorio;
								attrEsito = new Record(attr.toMap());
								attrEsito.setAttribute("valore", esito);		
								continuaSalvaDatiDefinitivoWithValidate();
							}
						});
					}												
				}
			});
		} else {
			messaggio = msgTaskProvvisorio;
			attrEsito = new Record(attr.toMap());
			attrEsito.setAttribute("valore", esito);			
			continuaSalvaDatiDefinitivoWithValidate();
		}
	}
	
	public void selezionaEsitoWithWarning(final String esito, final ServiceCallback<String> callback) {
		String warningMsg = getWarningMsgXEsitoTask(esito);
		if(warningMsg != null && !"".equals(warningMsg)) {
			AurigaLayout.showConfirmDialogWithWarning("Attenzione!", warningMsg, "Conferma", "Annulla", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value != null && value) {
						if(callback != null) {
							callback.execute(esito);
						}			
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(esito);
			}	
		}
	}
	
	public String getMsgTaskProvvisorio() {
		return msgTaskProvvisorio;
	}
	
	public String getWarningMsgXEsitoTask(String esito) {		
		if(esito != null && !"".equals(esito)) {
			if(mappaWarningMsgXEsitoTask != null && mappaWarningMsgXEsitoTask.containsKey(esito)) {
				return mappaWarningMsgXEsitoTask.get(esito);							
			}		
		} 
		return null;
	}
	
	public void aggiungiMessaggioTask(boolean required, String esito, final ServiceCallback<String> callback) {
		MessaggioTaskWindow messaggioTaskWindow = new MessaggioTaskWindow(msgTaskProvvisorio, required, esito, 
			new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {	
					msgTaskProvvisorio = object.getAttribute("messaggio");	
					if(callback != null) {
						callback.execute(msgTaskProvvisorio);
					}
				}
			}
		);
		messaggioTaskWindow.show();
	}

	@Override
	public void salvaDatiDefinitivo() {
		// tolgo queste due righe perchè se l'utente preme il bottone "Salva e procedi" mentre sta aspettando che si carichi il modulo hybrid della firma poi si sbianca l'esito
//		messaggio = null;
//		attrEsito = null;
		final String nomeAttrCustomEsitoTask = recordEvento.getAttribute("nomeAttrCustomEsitoTask");
		if (nomeAttrCustomEsitoTask != null && !"".equals(nomeAttrCustomEsitoTask)) {
			GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
			lAttributiDinamiciRecord.setAttribute("rowId", rowId);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
			lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					for (int i = 0; i < attributiAdd.getLength(); i++) {
						final Record attr = attributiAdd.get(i);
						if (attr.getAttribute("nome").equals(nomeAttrCustomEsitoTask)) {
							SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk, valoriEsito, mappaWarningMsgXEsitoTask, esitoTaskDaPreimpostare, msgTaskDaPreimpostare,
								new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {	
										messaggio = object.getAttribute("messaggio");
										attrEsito = new Record(attr.toMap());
										attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));										
										continuaSalvaDatiDefinitivoWithValidate();
									}
								}
							) {
								
								@Override
								public boolean isForzataModificaAttoWithEsitoKo(String esito) {
									return getValueAsBoolean("flgForzataModificaAtto") && esito != null && esitiTaskKo != null && esitiTaskKo.contains(esito);
								}
							};
							selezionaEsitoTaskWindow.show();
							break;
						} else {
							messaggio = null;
							attrEsito = null;
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			continuaSalvaDatiDefinitivoWithValidate();
		}
	}
	
	private void continuaSalvaDatiDefinitivoWithValidate() {
		
		RecordList datiContabiliList = getRecordToSave().getAttributeAsRecordList("listaDatiContabiliADSP");
		
		if(getEventoCWOLXEsitoTask(attrEsito) != null && !"".equalsIgnoreCase(getEventoCWOLXEsitoTask(attrEsito))
				&& getEventoCWOLXEsitoTask(attrEsito).equalsIgnoreCase("CtrlDisponbilitaRdA") 
				&& datiContabiliList != null && datiContabiliList.getLength() > 0) {
			
			Layout.showWaitPopup("Verifica disponibilita importo in corso...");

			/*Verifico l'importo*/
			final GWTRestDataSource lContabilitaADSPDataSource = new GWTRestDataSource("ContabilitaADSPDataSource");
			String cdrUOCompetente = (cdrUOCompetenteItem.getValue() != null) ? String.valueOf(cdrUOCompetenteItem.getValue()) : null;
			lContabilitaADSPDataSource.extraparam.put("cdrUOCompetente", cdrUOCompetente);
			Boolean flgSenzaImpegniCont = (Boolean) ((flgSenzaImpegniContItem.getValue() != null) ? (flgSenzaImpegniContItem.getValue()) : null);
			lContabilitaADSPDataSource.extraparam.put("flgSenzaImpegniCont", flgSenzaImpegniCont != null && flgSenzaImpegniCont == true ? "true" : "false");
			String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
			lContabilitaADSPDataSource.extraparam.put("idUd", idUd); 

			String stringMap = listaDatiContabiliADSPItem.getMappaKeyCapitoli() != null ? listaDatiContabiliADSPItem.getMappaKeyCapitoli().toString() : "";
			lContabilitaADSPDataSource.extraparam.put("mappaKeyCapitoli", stringMap);
			
			Record listaDatiContabiliADSPBean = new Record();
			listaDatiContabiliADSPBean.setAttribute("listaDatiContabiliADSP", datiContabiliList);
			
			lContabilitaADSPDataSource.performCustomOperation("verificaDisponibilitaImportoMultiple", listaDatiContabiliADSPBean, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						Layout.hideWaitPopup();
						
						final Record record = response.getData()[0];
						
						RecordList listaDatiContabiliADSP = record.getAttributeAsRecordList("listaDatiContabiliADSP");
						final RecordList listaProposteConcorrenti = record.getAttributeAsRecordList("listaProposteConcorrenti");
						
						listaDatiContabiliADSPItem.setData(listaDatiContabiliADSP);
						listaDatiContabiliADSPItem.redraw();
						
						if(record.getAttributeAsBoolean("inError")) {
							SC.warn(record.getAttributeAsString("defaultMessage"), new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									if(listaProposteConcorrenti.getLength() > 0) {
										RecordList listaErrori = new RecordList();
										for (int i = 0; i < listaProposteConcorrenti.getLength(); i++) {
											Record propostaConcorrente = listaProposteConcorrenti.get(i);											
											Record recordErrore = new Record();
											recordErrore.setAttribute("estremiProposta", propostaConcorrente.getAttribute("estremiProposta"));
											recordErrore.setAttribute("oggettoProposta", propostaConcorrente.getAttribute("oggettoProposta"));
											recordErrore.setAttribute("importoProposta", propostaConcorrente.getAttribute("importoProposta"));
											recordErrore.setAttribute("capitoloProposta", propostaConcorrente.getAttribute("capitoloProposta"));
											recordErrore.setAttribute("contoProposta", propostaConcorrente.getAttribute("contoProposta"));
											listaErrori.add(recordErrore);
										}
										ErroreProposteConcorrentiPopUp errorePopup = new ErroreProposteConcorrentiPopUp(nomeEntita, listaErrori,
												LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Atti in iter che insistono sullo stesso capitolo/conto") {
											
											@Override
											public void manageOnClick() {
												manageInserimentoSenzaDisponibilita();
											};
										};
										errorePopup.show();
									
									} else {
										manageInserimentoSenzaDisponibilita();
									}
								}
							});								
						} else {
							continuaSalvaDatiDefinitivoWithValidateAfterVerificaRdaADSP();
						}												
					} 				
				}
			}, new DSRequest());
		
		} else {
			continuaSalvaDatiDefinitivoWithValidateAfterVerificaRdaADSP();
		}
		
	}
	
	public void manageInserimentoSenzaDisponibilita() {
		
		String modCtrlDispCapitoliRda = AurigaLayout.getParametroDB("MOD_CTRL_DISP_CAPITOLI_IN_RDA");
		if(modCtrlDispCapitoliRda!=null && !"".equalsIgnoreCase(modCtrlDispCapitoliRda) && modCtrlDispCapitoliRda.equalsIgnoreCase("WARNING")) {
			SC.ask("Per alcuni movimenti contabili non risulta esserci sufficiente disponibilità, vuoi procedere comunque?" , new BooleanCallback() {					
				@Override
				public void execute(Boolean value) {				
					if(value) {
						continuaSalvaDatiDefinitivoWithValidateAfterVerificaRdaADSP();
					}
				}
			});
		} else if(modCtrlDispCapitoliRda!=null && !"".equalsIgnoreCase(modCtrlDispCapitoliRda) && modCtrlDispCapitoliRda.equalsIgnoreCase("BLOCCANTE")) {
//			errorePopup.closeWindow();
		} else {
			continuaSalvaDatiDefinitivoWithValidateAfterVerificaRdaADSP();
		}	
	}
	
	private void continuaSalvaDatiDefinitivoWithValidateAfterVerificaRdaADSP() {
		if(isEsitoTaskOk(attrEsito) || !isEsitoTaskKo(attrEsito)) {
			if (validate()) {	
				List<String> listaWarnings = new ArrayList<String>();
				//TODO gestire nuovi campi flgOpCommerciale, flgEscludiCIG e motivoEsclusioneCIG			
				if(showAttributoCustomCablato("DATI_CONTABILI") && showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()
				   && (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni() || isDeterminaAggiudicaProceduraGara())) {
					boolean isListaCIGEmpty = false;
					if(listaCIGItem != null) {
						RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
						isListaCIGEmpty = true;
						for(int i=0; i < listaCIG.getLength(); i++) {
							if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
									!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
								isListaCIGEmpty = false;
								break;
							}
						}
					}
					if (!isEsclusoCIG() && isListaCIGEmpty) {
						listaWarnings.add("CIG non valorizzato. ");
					}				
				} 
				if(showAttributoCustomCablato("SEZ_NOTIFICA_MESSI") && showAttributoCustomCablato("TASK_RESULT_2_FLG_MESSI_NOTIFICATORI") && getValueAsBoolean("flgMessiNotificatori")) {
					RecordList listaDestinatariNotificaMessi = showAttributoCustomCablato("DESTINATARI_NOTIFICA_MESSI") && notificaMessiForm != null ? notificaMessiForm.getValueAsRecordList("listaDestinatariNotificaMessi") : null;
					if(listaDestinatariNotificaMessi != null && listaDestinatariNotificaMessi.getLength() == 0) {
						listaWarnings.add("Manca l'indicazione dei destinatari della notifica tramite messi. ");
					}
				}
				if(showAttributoCustomCablato("SEZ_NOTIFICA_PEC") && showAttributoCustomCablato("TASK_RESULT_2_FLG_NOTIFICA_PEC") && getValueAsBoolean("flgNotificaPEC")) {
					RecordList listaDestinatariNotificaPEC = showAttributoCustomCablato("DESTINATARI_NOTIFICA_PEC") && notificaPECForm != null ? notificaPECForm.getValueAsRecordList("listaDestinatariNotificaPEC") : null;
					if(listaDestinatariNotificaPEC != null && listaDestinatariNotificaPEC.getLength() == 0) {
						listaWarnings.add("Manca l'indicazione dei destinatari della notifica tramite PEC. ");
					}
				}
				if(isDeterminaConSpesa()) {
					if(isAttivoContabilia() && showAttributoCustomCablato("MOVIMENTO_CONTABILIA") && !"".equals(getValueAsString("idPropostaAMC"))) {
						RecordList listaMovimentiContabilia = movimentiContabiliForm != null ? movimentiContabiliForm.getValueAsRecordList("listaMovimentiContabilia") : null;
						boolean isMovimentiContabiliaWithCodiceGSA = false;
						boolean isMovimentiContabiliaWithCodiceGSASenzaDatiGSA = false;
						if(listaMovimentiContabilia != null) {
							for(int i = 0; i < listaMovimentiContabilia.getLength(); i++) {
								if(listaMovimentiContabilia.get(i).getAttribute("codiceGsa") != null && !"".equals(listaMovimentiContabilia.get(i).getAttribute("codiceGsa"))) {
									boolean isCodiceGsaEditabile = false;
									String codGSAEditabili = AurigaLayout.getParametroDB("LISTA_COD_GSA_EDITABILI");
									if(codGSAEditabili != null && !"".equals(codGSAEditabili)) {
										StringSplitterClient st = new StringSplitterClient(codGSAEditabili, ";");
										Set<String> setCodGSAEditabili = new HashSet<String>();
										for (String token : st.getTokens()) {
											setCodGSAEditabili.add(token);
										}
										// solo se codiceGsa in LISTA_COD_GSA_EDITABILI (valori separati da ;)
										if(setCodGSAEditabili.contains(listaMovimentiContabilia.get(i).getAttribute("codiceGsa"))) {
											isCodiceGsaEditabile = true;							
										}
									} else {
										isCodiceGsaEditabile = true;	
									}
									if(isCodiceGsaEditabile) {
										isMovimentiContabiliaWithCodiceGSA = true;								
										if(listaMovimentiContabilia.get(i).getAttribute("datiGsa") == null || "".equals(listaMovimentiContabilia.get(i).getAttribute("datiGsa"))) {
											isMovimentiContabiliaWithCodiceGSASenzaDatiGSA = true;
										}	
									}
								}
							}
						}
						if(showAttributoCustomCablato("TASK_RESULT_2_RIL_GSA") && _FLG_NO.equals(getValueAsString("flgDatiRilevantiGSA")) && isMovimentiContabiliaWithCodiceGSA) {
							listaWarnings.add("ATTENZIONE: ci sono movimenti contabili che hanno dati GSA ma è stata selezionata l'opzione \"Dati rilevanti GSA = NO\".");
						}						
						if(isDatiRilevantiGSA() && !isMovimentiContabiliaWithCodiceGSA) {
							if(showAttributoCustomCablato("DATI_GSA") && getFlgEditabileAttributoCustomCablato("DATI_GSA")) {
								RecordList listaMovimentiGSA = movimentiGSAForm != null ? movimentiGSAForm.getValueAsRecordList("listaMovimentiGSA") : null;
								boolean isMovimentiGSAWithDatiGSA = false;
								if(listaMovimentiGSA != null) {
									for(int i = 0; i < listaMovimentiGSA.getLength(); i++) {
										if(listaMovimentiGSA.get(i).getAttribute("datiGsa") != null && !"".equals(listaMovimentiGSA.get(i).getAttribute("datiGsa"))) {
											isMovimentiGSAWithDatiGSA = true;
											break;
										}
									}
								}
								if(!isMovimentiGSAWithDatiGSA) {
									messaggio = null;
									attrEsito = null;
									Layout.addMessage(new MessageBean("E' obbligatorio compilare almeno un movimento con dati GSA per poter procedere", "", MessageType.ERROR));	
									return;
								}
							} else if(showAttributoCustomCablato("DATI_CONTABILIA_DETT_GSA") && getFlgEditabileAttributoCustomCablato("DATI_CONTABILIA_DETT_GSA")) {
								messaggio = null;
								attrEsito = null;
								Layout.addMessage(new MessageBean("E' obbligatorio compilare almeno un movimento con dati GSA per poter procedere", "", MessageType.ERROR));	
								return;
							}
						}						
						if(isDatiRilevantiGSA() && showAttributoCustomCablato("DATI_CONTABILIA_DETT_GSA") && getFlgEditabileAttributoCustomCablato("DATI_CONTABILIA_DETT_GSA") && isMovimentiContabiliaWithCodiceGSASenzaDatiGSA) {
							messaggio = null;
							attrEsito = null;
							Layout.addMessage(new MessageBean("E' obbligatorio compilare i dati GSA sui movimenti contabili che hanno il cod. perim. sanitario valorizzato", "", MessageType.ERROR));
							return;						
						}
					} else {
						if(showAttributoCustomCablato("DATI_GSA") && getFlgEditabileAttributoCustomCablato("DATI_GSA")) {
							RecordList listaMovimentiGSA = movimentiGSAForm != null ? movimentiGSAForm.getValueAsRecordList("listaMovimentiGSA") : null;
							boolean isMovimentiGSAWithDatiGSA = false;
							if(listaMovimentiGSA != null) {
								for(int i = 0; i < listaMovimentiGSA.getLength(); i++) {
									if(listaMovimentiGSA.get(i).getAttribute("datiGsa") != null && !"".equals(listaMovimentiGSA.get(i).getAttribute("datiGsa"))) {
										isMovimentiGSAWithDatiGSA = true;
										break;
									}
								}
							}
							if(isDatiRilevantiGSA() && !isMovimentiGSAWithDatiGSA) {
								messaggio = null;
								attrEsito = null;
								Layout.addMessage(new MessageBean("E' obbligatorio compilare almeno un movimento con dati GSA per poter procedere", "", MessageType.ERROR));	
								return;
							}
						}
					}
				}
				if(isDeterminaSenzaSpesa()) {
					if(showAttributoCustomCablato("DATI_GSA") && getFlgEditabileAttributoCustomCablato("DATI_GSA")) {
						RecordList listaMovimentiGSA = movimentiGSAForm != null ? movimentiGSAForm.getValueAsRecordList("listaMovimentiGSA") : null;
						boolean isMovimentiGSAWithDatiGSA = false;
						if(listaMovimentiGSA != null) {
							for(int i = 0; i < listaMovimentiGSA.getLength(); i++) {
								if(listaMovimentiGSA.get(i).getAttribute("datiGsa") != null && !"".equals(listaMovimentiGSA.get(i).getAttribute("datiGsa"))) {
									isMovimentiGSAWithDatiGSA = true;
									break;
								}
							}
						}
						if(isDatiRilevantiGSA() && !isMovimentiGSAWithDatiGSA) {
							messaggio = null;
							attrEsito = null;
							Layout.addMessage(new MessageBean("E' obbligatorio compilare almeno un movimento con dati GSA per poter procedere", "", MessageType.ERROR));	
							return;
						}
					}
				}
				if (listaWarnings != null && listaWarnings.size() > 0) {
					String warnings = "";
					for(String warn : listaWarnings) {
						warnings += warn;
					}
					SC.ask(warnings + "Vuoi procedere comunque?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value != null && value) {
								saveAndGoAlert();
							} else {
								messaggio = null;
								attrEsito = null;	
							}
						}
					});
				} else {
					saveAndGoAlert();
				}
				
			} else {
				messaggio = null;
				attrEsito = null;
				Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
			}
		} else {
			if (validateSenzaObbligatorieta()) {
				saveAndGoAlert();
			} else {
				messaggio = null;
				attrEsito = null;
				Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
			}
		}
	}
	
	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}
	
	public void saveAndGoAlert() {
		if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
			SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value != null && value) {
						saveAndGo();
					} else {
						messaggio = null;
						attrEsito = null;
					}
				}
			});
		} else {
			saveAndGo();
		}
	}
	
	public RecordList getListaRecordModelliXEsitoPreAvanzamentoFlusso(String esito) {
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {	
			RecordList listaRecordModelliConEsitoUguale = new RecordList();		
			RecordList listaRecordModelliSenzaEsito = new RecordList();		
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							if(!listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
								listaRecordModelliConEsitoUguale.add(listaRecordModelli.get(i));
							}
						}
					} 
				} else if(!listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
					listaRecordModelliSenzaEsito.add(listaRecordModelli.get(i));
				}
			}	
			if(listaRecordModelliConEsitoUguale != null && listaRecordModelliConEsitoUguale.getLength() > 0) {
				return listaRecordModelliConEsitoUguale;
			} else if(listaRecordModelliSenzaEsito != null && listaRecordModelliSenzaEsito.getLength() > 0) {
				return listaRecordModelliSenzaEsito;
			} 
		}
		return null;
	}
	
	public RecordList getListaRecordModelliXEsitoPostAvanzamentoFlusso(String esito) {
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {	
			RecordList listaRecordModelliConEsitoUguale = new RecordList();		
			RecordList listaRecordModelliSenzaEsito = new RecordList();		
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							if(listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {
								listaRecordModelliConEsitoUguale.add(listaRecordModelli.get(i));
							}
						}
					} 
				} else if(listaRecordModelli.get(i).getAttributeAsBoolean("flgPostAvanzamentoFlusso")) {					
					listaRecordModelliSenzaEsito.add(listaRecordModelli.get(i));
				}
			}	
			if(listaRecordModelliConEsitoUguale != null && listaRecordModelliConEsitoUguale.getLength() > 0) {
				return listaRecordModelliConEsitoUguale;
			} else if(listaRecordModelliSenzaEsito != null && listaRecordModelliSenzaEsito.getLength() > 0) {
				return listaRecordModelliSenzaEsito;
			} 
		}
		return null;
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("idProcess", getIdProcessTask());
		lRecordToSave.setAttribute("idModCopertina", idModCopertina != null ? idModCopertina : "");
		lRecordToSave.setAttribute("nomeModCopertina", nomeModCopertina != null ? nomeModCopertina : "");
		lRecordToSave.setAttribute("idModCopertinaFinale", idModCopertinaFinale != null ? idModCopertinaFinale : "");
		lRecordToSave.setAttribute("nomeModCopertinaFinale", nomeModCopertinaFinale != null ? nomeModCopertinaFinale : "");
		lRecordToSave.setAttribute("idModAllegatiParteIntSeparati", idModAllegatiParteIntSeparati != null ? idModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("nomeModAllegatiParteIntSeparati", nomeModAllegatiParteIntSeparati != null ? nomeModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("uriModAllegatiParteIntSeparati", uriModAllegatiParteIntSeparati != null ? uriModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("tipoModAllegatiParteIntSeparati", tipoModAllegatiParteIntSeparati != null ? tipoModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("idModAllegatiParteIntSeparatiXPubbl", idModAllegatiParteIntSeparatiXPubbl != null ? idModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("nomeModAllegatiParteIntSeparatiXPubbl", nomeModAllegatiParteIntSeparatiXPubbl != null ? nomeModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("uriModAllegatiParteIntSeparatiXPubbl", uriModAllegatiParteIntSeparatiXPubbl != null ? uriModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("tipoModAllegatiParteIntSeparatiXPubbl", tipoModAllegatiParteIntSeparatiXPubbl != null ? tipoModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("flgAppendiceDaUnire", flgAppendiceDaUnire);		
		lRecordToSave.setAttribute("idModAppendice", idModAppendice != null ? idModAppendice : "");
		lRecordToSave.setAttribute("nomeModAppendice", nomeModAppendice != null ? nomeModAppendice : "");
		lRecordToSave.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		lRecordToSave.setAttribute("nomeModello", nomeModAssDocTask != null ? nomeModAssDocTask : "");
		lRecordToSave.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		lRecordToSave.setAttribute("idUoDirAdottanteSIB", idUoDirAdottanteSIB != null ? idUoDirAdottanteSIB : "");
		lRecordToSave.setAttribute("codUoDirAdottanteSIB", codUoDirAdottanteSIB != null ? codUoDirAdottanteSIB : "");
		lRecordToSave.setAttribute("desUoDirAdottanteSIB", desUoDirAdottanteSIB != null ? desUoDirAdottanteSIB : "");	
		// mi passo avanti l'informazione se è attivo l'upload del pdf atto, in quel caso lo salvo
		lRecordToSave.setAttribute("flgAttivaUploadPdfAtto", flgAttivaUploadPdfAtto != null ? flgAttivaUploadPdfAtto : false);
		lRecordToSave.setAttribute("flgAttivaUploadPdfAttoOmissis", flgAttivaUploadPdfAttoOmissis != null ? flgAttivaUploadPdfAttoOmissis : false);
		if(getFlgSoloPreparazioneVersPubblicazione() && getValueAsBoolean("isChangedFilePrimarioOmissis")) {
			lRecordToSave.setAttribute("flgDatiSensibili", true);
			lRecordToSave.setAttribute("flgPrivacy", _FLG_SI);			
			lRecordToSave.setAttribute("flgAggiornataVersDaPubblicare", true);
		}
		return lRecordToSave;
	}
	
	public void sblocca() {
		hiddenForm.setValue("flgForzataModificaAtto", true);		
		// dopo aver cambiato il file devo risettare il mode del dettaglio
		if (isEseguibile() && !isForzaModificaAttoDaSbloccare()) {
			if(isSoloPreparazioneVersPubblicazione()) {
				soloPreparazioneVersPubblicazioneMode();
			} else if (isSoloOmissisModProprietaFile()) {
				soloOmissisModProprietaFileMode();
			} else if (isReadOnly()) {
				readOnlyMode();
			} else {
				editMode();
			}
		} else {
			viewMode();
		}
	}
	
	public void controlloFormatiAllegPICompletamentoTask(BooleanCallback callback) {
		boolean isFormatiAmmessi = true;
		if(flgCtrlMimeTypeAllegPI != null && flgCtrlMimeTypeAllegPI) {
			String mimetypeAmmessiAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MIMETYPE_AMM_ALL_PI_ATTO");
			if(mimetypeAmmessiAllegatiParteIntegranteAtti != null && !"".equals(mimetypeAmmessiAllegatiParteIntegranteAtti)) {
				String modalitaControlloMimetypeAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MOD_CTRL_MIMETYPE_ALL_PI_ATTO");
				if(modalitaControlloMimetypeAllegatiParteIntegranteAtti != null && "completamento_task".equalsIgnoreCase(modalitaControlloMimetypeAllegatiParteIntegranteAtti)) {				
					StringSplitterClient st = new StringSplitterClient(mimetypeAmmessiAllegatiParteIntegranteAtti, ";");
					HashSet<String> setMimetypeAmmessi = new HashSet<String>();
					for(int i = 0; i < st.getTokens().length; i++) {
						setMimetypeAmmessi.add(st.getTokens()[i]);						
					}
					RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
					for(int i = 0; i < listaAllegati.getLength(); i++) {
						Record allegato = listaAllegati.get(i);
						if(allegato.getAttribute("flgParteDispositivo") != null && allegato.getAttributeAsBoolean("flgParteDispositivo")) {
							if (allegato.getAttribute("uriFileAllegato") != null && !"".equals(allegato.getAttribute("uriFileAllegato"))) {
								final InfoFileRecord infoFileAllegato = allegato.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(allegato.getAttributeAsRecord("infoFile").getJsObj())) : null;
								String mimetype = infoFileAllegato != null ? infoFileAllegato.getMimetype() : null;
								if(!setMimetypeAmmessi.contains(mimetype)) {
									isFormatiAmmessi = false;
									break;
								}
							} 
							if (allegato.getAttribute("flgDatiSensibili") != null && allegato.getAttributeAsBoolean("flgDatiSensibili") && 
							    allegato.getAttribute("uriFileOmissis") != null && !"".equals(allegato.getAttribute("uriFileOmissis"))) {
								final InfoFileRecord infoFileOmissis = allegato.getAttributeAsRecord("infoFileOmissis") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(allegato.getAttributeAsRecord("infoFileOmissis").getJsObj())) : null;
								String mimetypeOmissis = infoFileOmissis != null ? infoFileOmissis.getMimetype() : null;
								if(!setMimetypeAmmessi.contains(mimetypeOmissis)) {
									isFormatiAmmessi = false;
									break;
								}
							}
						}
					}					
				}
			}
		}
		if(callback != null) {
			callback.execute(isFormatiAmmessi);
		}
	}	
	
	public void controlloNumerazioneUnioneFile(BooleanCallback callback) {
		String siglaRegistroAtto = recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null;		
		boolean skipMessaggiFirmaAtti = AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto);
		if(hasActionUnioneFile() && hasActionFirma() && !skipMessaggiFirmaAtti) {
			final Record lRecord = getRecordToSave();
			String numeroRegistrazione = lRecord.getAttribute("numeroRegistrazione");
			String messaggioFirma = "";
			Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? lRecord.getAttributeAsDate("dataRegistrazione") : null;
			if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto) && (numeroRegistrazione == null || "".equalsIgnoreCase(numeroRegistrazione))) {
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneConRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneConRegistrazione();
				}
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {					
					SC.say(messaggioFirma, callback);
				} else if(callback != null){
					callback.execute(true);
				}
			} else if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto)) {
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneSenzaRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneSenzaRegistrazione();
				}
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {					
					SC.say(messaggioFirma, callback);
				} else if(callback != null){
					callback.execute(true);
				}
			} else if(callback != null){
				callback.execute(true);
			}
		} else if(callback != null){
			callback.execute(true);
		}
	}
	
	public void saveAndGo() {
		final String idModDispositivo = getIdModDispositivo();
		final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		final RecordList listaRecordModelliXEsitoPreAvanzamentoFlusso = getListaRecordModelliXEsitoPreAvanzamentoFlusso(esito);
		boolean eseguiAzioni = false;
		if(isEsitoTaskAzioniValorizzato()) {
			if(isEsitoTaskAzioni(attrEsito)) {
				eseguiAzioni = true;
			}
		} else if(isEsitoTaskOk(attrEsito)) {
			eseguiAzioni = true;
		}
		if(eseguiAzioni) {		
			controlloFormatiAllegPICompletamentoTask(new BooleanCallback() {
				
				@Override
				public void execute(Boolean valueCtrlFmtAllPI) {
					if(valueCtrlFmtAllPI != null && valueCtrlFmtAllPI) {
						controlloNumerazioneUnioneFile(new BooleanCallback() {
							
							@Override
							public void execute(Boolean valueCtrlNumUnioneFile) {
								if(valueCtrlNumUnioneFile != null && valueCtrlNumUnioneFile) {
									Map<String, String> otherExtraparam = new HashMap<String, String>();									
									otherExtraparam.put("flgSalvataggioProvvisorioPreCompleteTask", "true");
									if(hasActionUnioneFile()) {
										otherExtraparam.put("siglaRegistroAtto", recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null);		
										otherExtraparam.put("siglaRegistroAtto2", recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto2") : null);								
									}
									salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {
										@Override
										public void execute(Record object) {
											idEvento = object.getAttribute("idEvento");
											Layout.hideWaitPopup();
											loadDettPropostaAtto(new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record dett) {
													saveAndGoWithListaModelliGenAutomatica(true, listaRecordModelliXEsitoPreAvanzamentoFlusso, esito, new ServiceCallback<RecordList>() {

														@Override
														public void execute(final RecordList listaRecordModelliGeneratiDaFirmare) {
															if(hasActionFirmaVersPubblAggiornata()) {
																firmaFileVersPubblAggiornata(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
																	
																	@Override
																	public void execute(Record recordAfterFirma) {
																		salvaDati(false, new ServiceCallback<Record>() {

																			@Override
																			public void execute(Record object) {								
																				if(isDaPubblicare()) {
																					pubblica(object);
																				} else {
																					callbackSalvaDati(object);
																				}
																			}
																		});																					
																	}
																});
															} else if(hasActionUnioneFile()) {
																if(idModDispositivo == null || "".equals(idModDispositivo)) {
																	AurigaLayout.addMessage(new MessageBean("Da configurazione non è prevista la generazione del testo dell'atto", "", MessageType.ERROR));
																} else {
																	// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
																	// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto				
																	unioneFileAndReturn(hasActionTimbra(), listaRecordModelliGeneratiDaFirmare);
																}
															} else if(hasActionFirma()) {
																boolean checkControlloFirme = controlloFirmeEsistentiPreFirma();
																if (checkControlloFirme) {
																	showAnteprimaFileDaFirmare(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<RecordList>() {
																		
																		@Override
																		public void execute(RecordList listaRecordModelliGeneratiDaFirmare) {
																			firmaFile(hasActionTimbra(), listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
																				
																				@Override
																				public void execute(final Record recordAfterFirma) {
																					salvaDati(false, new ServiceCallback<Record>() {
			
																						@Override
																						public void execute(Record object) {
																							if(isDaPubblicare()) {
																								pubblica(object);
																							} else {
																								callbackSalvaDati(object);
																							}
																						}
																					});
																				}
																			});
																		}
																	});
																} else {
																	AurigaLayout.addMessage(new MessageBean("Presenti firme non valide nel testo dell''atto", "", MessageType.ERROR));
																}
															} else if(hasActionTimbra()) {
																timbraFile(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
																	
																	@Override
																	public void execute(final Record recordAfterFirma) {
																		salvaDati(false, new ServiceCallback<Record>() {

																			@Override
																			public void execute(Record object) {								
																				if(isDaPubblicare()) {
																					pubblica(object);
																				} else {
																					callbackSalvaDati(object);
																				}
																			}
																		});		
																	}
																});			
															} else {
																firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

																	@Override
																	public void execute(Record recordFirma) {
																		salvaDati(false, new ServiceCallback<Record>() {

																			@Override
																			public void execute(Record object) {								
																				if(isDaPubblicare()) {
																					pubblica(object);
																				} else {
																					callbackSalvaDati(object);
																				}
																			}
																		});	
																	}
																});																
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
					} else {
						AurigaLayout.addMessage(new MessageBean("Alcuni degli allegati parte integrante hanno formato non ammesso per avanzare l'iter come richiesto: serve convertirli manualmente in pdf e ricaricarne la versione", "", MessageType.ERROR));
					}
				}
			});	
		} else {			
			saveAndGoWithListaModelliGenAutomatica(false, listaRecordModelliXEsitoPreAvanzamentoFlusso, esito, new ServiceCallback<RecordList>() {

				@Override
				public void execute(RecordList listaRecordModelliGeneratiDaFirmare) {
					firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

						@Override
						public void execute(Record recordFirma) {
							salvaDati(false, true, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									callbackSalvaDati(object);
								}
							});	
						}
					});
				}
			});	
		}
	}
	
	public void getFileDaFirmare(boolean fileDaTimbrare, DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("fileDaTimbrare", fileDaTimbrare ? "true" : "");
		// boolean skipFirmaAllegatiParteIntegrante = false;
		// lNuovaPropostaAtto2CompletaDataSource.addParam("skipFirmaAllegatiParteIntegrante", skipFirmaAllegatiParteIntegrante ? "true" : "");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	public void getFileVersPubblAggiornataDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileVersPubblAggiornataDaFirmare", lRecord, callback);
	}
	
	// recupera i file allegati da firmare assieme al file unione nel task di Firma di adozione
	public void getFileAllegatiDaFirmareWithFileUnione(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileAllegatiDaFirmareWithFileUnione", lRecord, callback);
	}
	
	public boolean controlloFirmeEsistentiPreFirma() {
		boolean flgControllofirme = true;
		Record record = getRecordToSave();
		InfoFileRecord infoFilePrimario = record.getAttributeAsRecord("infoFilePrimario") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFilePrimario")) : null;
		if (infoFilePrimario != null) {
			boolean firmato = infoFilePrimario.getAttribute("firmato") != null && infoFilePrimario.getAttribute("firmato").equalsIgnoreCase("true");
			if (firmato) {
				boolean firmaValida = infoFilePrimario.getAttribute("firmaValida") != null && infoFilePrimario.getAttribute("firmaValida").equalsIgnoreCase("true");
				if (!firmaValida)  {
					flgControllofirme = false;
				}
			}
		}
		final InfoFileRecord infoFilePrimarioOmissis = record.getAttributeAsRecord("infoFilePrimarioOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFilePrimarioOmissis")) : null;
		if (infoFilePrimarioOmissis != null) {
			boolean firmato = infoFilePrimarioOmissis.getAttribute("firmato") != null && infoFilePrimarioOmissis.getAttribute("firmato").equalsIgnoreCase("true");
			if (firmato) {
				boolean firmaValida = infoFilePrimarioOmissis.getAttribute("firmaValida") != null && infoFilePrimarioOmissis.getAttribute("firmaValida").equalsIgnoreCase("true");
				if (!firmaValida) {
					flgControllofirme = false;
				}
			}
		}
		return flgControllofirme;
	}
	
	public void showAnteprimaFileDaFirmare(final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<RecordList> callback) {
		if(isShowAnteprimaFileDaFirmare()) {
			Record record = getRecordToSave();
			String uriFilePrimario = record.getAttribute("uriFilePrimario");
			String nomeFilePrimario = record.getAttribute("nomeFilePrimario");
			InfoFileRecord infoFilePrimario = record.getAttributeAsRecord("infoFilePrimario") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFilePrimario")) : null;							
			final String uriFilePrimarioOmissis = record.getAttribute("uriFilePrimarioOmissis");
			final String nomeFilePrimarioOmissis = record.getAttribute("nomeFilePrimarioOmissis");
			final InfoFileRecord infoFilePrimarioOmissis = record.getAttributeAsRecord("infoFilePrimarioOmissis") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFilePrimarioOmissis")) : null;								
			if(uriFilePrimario != null && !"".equals(uriFilePrimario)) {		
				new PreviewWindowWithCallback(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean", nomeFilePrimario, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record recordPreview) {	
						if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
							new PreviewWindowWithCallback(uriFilePrimarioOmissis, true, infoFilePrimarioOmissis, "FileToExtractBean", nomeFilePrimarioOmissis, new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record recordPreview) {		
									if(callback != null) {
										callback.execute(listaRecordModelliGeneratiDaFirmare);
									}
								}
							}) {
								
								@Override
								public boolean hideAnnullaButton() {
									return true;
								}
							};		
						} else if(callback != null) {
							callback.execute(listaRecordModelliGeneratiDaFirmare);
						}
					}
				}) {
					
					@Override
					public boolean hideAnnullaButton() {
						return true;
					}
				};	
			} else if(callback != null) {
				callback.execute(listaRecordModelliGeneratiDaFirmare);
			}
		} else if(callback != null) {
			callback.execute(listaRecordModelliGeneratiDaFirmare);
		}
	}
	
	protected void firmaFile(boolean fileDaTimbrare, final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		getFileDaFirmare(fileDaTimbrare, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];
					Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");
					final RecordList listaFilesDaFirmare = new RecordList();
					if(filesDaFirmare != null) {
						listaFilesDaFirmare.addList(filesDaFirmare);
					}
					if(listaRecordModelliGeneratiDaFirmare != null) {						
						for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
							String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
							InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
							String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
							boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
							if (flgDaFirmare) {								
								Record record = new Record();
								record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare 
								record.setAttribute("uri", uriFileGenerato);
								record.setAttribute("nomeFile", nomeFileModello);
								record.setAttribute("infoFile", infoFileGenerato);
								boolean flgFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgFirmaGrafica");
								if (flgFirmaGrafica) {
									String nroColonnaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroColonnaFirmaGrafica");
									String nroRigaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroRigaFirmaGrafica");
									String nroPaginaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroPaginaFirmaGrafica");
									String testoFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("testoFirmaGrafica");
									Record infoFirmaGraficaRecord = new Record();
									infoFirmaGraficaRecord.setAttribute("areaVerticale", nroRigaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("areaOrizzontale", nroColonnaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("nroPagina", nroPaginaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("testo", testoFirmaGrafica);
									RecordList listaInfoFirmaGrafica = new RecordList();
									listaInfoFirmaGrafica.add(infoFirmaGraficaRecord);
									record.setAttribute("listaInformazioniFirmaGrafica", listaInfoFirmaGrafica);
								}
								listaFilesDaFirmare.add(record);				
							}		
						}
					}					
					if(listaFilesDaFirmare.getLength() > 0) {
						if (mappaInfoFirmaGrafica != null && mappaInfoFirmaGrafica.size() > 1) {
							Record recordToPass = new Record();
							recordToPass.setAttribute("mappaInfoFirmaGrafica", mappaInfoFirmaGrafica);
							new SelezionaRuoloPerApposizioneFirmaPopup(recordToPass, new ServiceCallback<List<String>>() {
								
								@Override
								public void execute(List<String> listaRuoliFirmaSelezionati) {
									continuaFirmaFile(callback, listaFilesDaFirmare, listaRuoliFirmaSelezionati.toArray(new String[listaRuoliFirmaSelezionati.size()]));
								}
							});
							
						} else if (mappaInfoFirmaGrafica != null && mappaInfoFirmaGrafica.size() == 1) {
							continuaFirmaFile(callback, listaFilesDaFirmare, (String[]) mappaInfoFirmaGrafica.keySet().toArray(new String[mappaInfoFirmaGrafica.keySet().size()]));
						} else {
							continuaFirmaFile(callback, listaFilesDaFirmare, null);
						}						
					} else {
						if(callback != null) {
							callback.execute(getRecordToSave());			
						}
					}
				}
			}
		});		
	}
	
	private void continuaFirmaFile(final ServiceCallback<Record> callback, RecordList listaFilesDaFirmare, String[] elencoRuoliFirmaGrafica) {
		// Setto i dati per l'eventuale firma grafica
		if (elencoRuoliFirmaGrafica != null && elencoRuoliFirmaGrafica.length > 0) {
			RecordList elencoRuoliFirmaGraficaDaApplicare = new RecordList();
			for (int i = 0; i < elencoRuoliFirmaGrafica.length; i++) {
				elencoRuoliFirmaGraficaDaApplicare.add(mappaInfoFirmaGrafica.get(elencoRuoliFirmaGrafica[i]));
			}
			// Nel file primario setto tutte le firme grafiche da apporre
			for (int i = 0; i < listaFilesDaFirmare.getLength(); i++) {
				String idFile = listaFilesDaFirmare.get(i).getAttribute("idFile");
				if (idFile != null && idFile.startsWith("primario")) {
					listaFilesDaFirmare.get(i).setAttribute("listaInformazioniFirmaGrafica", elencoRuoliFirmaGraficaDaApplicare);
				}
			}
		}
		// Leggo gli eventuali parametri per forzare il tipo di firma
		String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
		String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
		FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {

			@Override
			public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
				final Record lRecord = new Record();
				lRecord.setAttribute("protocolloOriginale", getRecordToSave());
				if (hasActionFirmaAutomatica()) {
					firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
						
						@Override
						public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
							// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
							Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
							for (String idFileFiramtoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
								signedFiles.put(idFileFiramtoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFiramtoFirmaAutomatica));										
							}
							Record lRecordFileFirmati = new Record();
							lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
							lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
							aggiornaFileFirmati(lRecord, callback);
						}
					}, null);
				} else {
					Record lRecordFileFirmati = new Record();
					lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					aggiornaFileFirmati(lRecord, callback);
				}
			}
		});
	}
	
	protected void timbraFile(final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		getFileDaFirmare(true, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];					
					Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");					
					Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave());
					Record lRecordFileTimbrati = new Record();
					lRecordFileTimbrati.setAttribute("files", filesDaFirmare);
					lRecord.setAttribute("fileFirmati", lRecordFileTimbrati);					
					aggiornaFileTimbrati(lRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordTimbraFile) {	
							RecordList listaFilesDaFirmare = new RecordList();
							if(listaRecordModelliGeneratiDaFirmare != null) {						
								for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
									String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
									InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
									String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
									boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
									if (flgDaFirmare) {								
										Record record = new Record();
										record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare 
										record.setAttribute("uri", uriFileGenerato);
										record.setAttribute("nomeFile", nomeFileModello);
										record.setAttribute("infoFile", infoFileGenerato);
										boolean flgFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgFirmaGrafica");
										if (flgFirmaGrafica) {
											String nroColonnaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroColonnaFirmaGrafica");
											String nroRigaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroRigaFirmaGrafica");
											String nroPaginaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroPaginaFirmaGrafica");
											String testoFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("testoFirmaGrafica");
											Record infoFirmaGraficaRecord = new Record();
											infoFirmaGraficaRecord.setAttribute("areaVerticale", nroRigaFirmaGrafica);
											infoFirmaGraficaRecord.setAttribute("areaOrizzontale", nroColonnaFirmaGrafica);
											infoFirmaGraficaRecord.setAttribute("nroPagina", nroPaginaFirmaGrafica);
											infoFirmaGraficaRecord.setAttribute("testo", testoFirmaGrafica);
											RecordList listaInfoFirmaGrafica = new RecordList();
											listaInfoFirmaGrafica.add(infoFirmaGraficaRecord);
											record.setAttribute("listaInformazioniFirmaGrafica", listaInfoFirmaGrafica);
										}
										listaFilesDaFirmare.add(record);				
									}		
								}
							}					
							if(listaFilesDaFirmare.getLength() > 0) {
								// Leggo gli eventuali parametri per forzare il tipo di firma
								String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
								String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
								FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
						
									@Override
									public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
										final Record lRecord = new Record();
										lRecord.setAttribute("protocolloOriginale", getRecordToSave());
										if (hasActionFirmaAutomatica()) {
											firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
												
												@Override
												public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
													// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
													Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
													for (String idFileFiramtoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
														signedFiles.put(idFileFiramtoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFiramtoFirmaAutomatica));										
													}
													Record lRecordFileFirmati = new Record();
													lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
													lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
													aggiornaFileFirmati(lRecord, callback);
												}
											}, null);
										} else {
											Record lRecordFileFirmati = new Record();
											lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
											lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
											aggiornaFileFirmati(lRecord, callback);
										}
									}
								});
							} else {
								if(callback != null) {
									callback.execute(getRecordToSave());			
								}
							}			
						}
					});					
				}
			}
		});		
	}
	
	protected void firmaFileVersPubblAggiornata(final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		final List<Record> listaFiles = new ArrayList<Record>();
		getFileVersPubblAggiornataDaFirmare(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];
					Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");
					Record lFileUnione = null;
					Record lFileUnioneOmissis = null;
					if(filesDaFirmare != null) {
						for(int i = 0; i < filesDaFirmare.length; i++) {
							if(filesDaFirmare[i].getAttribute("idFile").startsWith("primarioOmissis")) {
								lFileUnioneOmissis = filesDaFirmare[i];
							} else if(filesDaFirmare[i].getAttribute("idFile").startsWith("primario")) {
								lFileUnione = filesDaFirmare[i];
							} 
							listaFiles.add(filesDaFirmare[i]);	
						}
					}					
					final Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
					if(lFileUnioneOmissis != null) {
						String uriFileUnioneOmissis = lFileUnioneOmissis.getAttribute("uri");
						String nomeFileUnioneOmissis = lFileUnioneOmissis.getAttribute("nomeFile");
						InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissis.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(lFileUnioneOmissis.getAttributeAsRecord("infoFile").getJsObj())) : null;													
						new PreviewWindowWithCallback(uriFileUnioneOmissis, true, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record recordPreview) {	
								afterPreviewFirmaFileVersPubblAggiornata(files, listaRecordModelliGeneratiDaFirmare, callback);			
							}
						});
					} else if(lFileUnione != null) {
						String uriFileUnione = lFileUnione.getAttribute("uri");
						String nomeFileUnione = lFileUnione.getAttribute("nomeFile");
						InfoFileRecord infoFileUnione = lFileUnione.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(lFileUnione.getAttributeAsRecord("infoFile").getJsObj())) : null;						
						new PreviewWindowWithCallback(uriFileUnione, true, infoFileUnione, "FileToExtractBean", nomeFileUnione, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record recordPreview) {
								afterPreviewFirmaFileVersPubblAggiornata(files, listaRecordModelliGeneratiDaFirmare, callback);			
							}
						});		
					} else {
						afterPreviewFirmaFileVersPubblAggiornata(files, listaRecordModelliGeneratiDaFirmare, callback);
					}					
				}
			}
		});	
	}		
	
	protected void afterPreviewFirmaFileVersPubblAggiornata(Record[] files, RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		RecordList listaFilesDaFirmare = new RecordList();
		if(files != null) {
			listaFilesDaFirmare.addList(files);
		}
		if(listaRecordModelliGeneratiDaFirmare != null) {						
			for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
				String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
				InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
				String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
				boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
				if (flgDaFirmare) {								
					Record record = new Record();
					record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare
					record.setAttribute("uri", uriFileGenerato);
					record.setAttribute("nomeFile", nomeFileModello);
					record.setAttribute("infoFile", infoFileGenerato);
					boolean flgFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgFirmaGrafica");
					if (flgFirmaGrafica) {
						String nroColonnaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroColonnaFirmaGrafica");
						String nroRigaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroRigaFirmaGrafica");
						String nroPaginaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroPaginaFirmaGrafica");
						String testoFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("testoFirmaGrafica");
						Record infoFirmaGraficaRecord = new Record();
						infoFirmaGraficaRecord.setAttribute("areaVerticale", nroRigaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("areaOrizzontale", nroColonnaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("nroPagina", nroPaginaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("testo", testoFirmaGrafica);
						RecordList listaInfoFirmaGrafica = new RecordList();
						listaInfoFirmaGrafica.add(infoFirmaGraficaRecord);
						record.setAttribute("listaInformazioniFirmaGrafica", listaInfoFirmaGrafica);
					}
					listaFilesDaFirmare.add(record);				
				}		
			}
		}					
		if(listaFilesDaFirmare.getLength() > 0) {
			// Leggo gli eventuali parametri per forzare il tipo d firma
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
				@Override
				public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
					final Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave());
					if (hasActionFirmaAutomatica()) {
						firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
							
							@Override
							public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
								// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
								Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
								for (String idFileFiramtoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
									signedFiles.put(idFileFiramtoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFiramtoFirmaAutomatica));										
								}
								Record lRecordFileFirmati = new Record();
								lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
								lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
								aggiornaFileFirmati(lRecord, callback);
							}
						}, null);
					} else {
						Record lRecordFileFirmati = new Record();
						lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
						lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
						aggiornaFileFirmati(lRecord, callback);
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(getRecordToSave());	
			}
		}
	}		
	
	protected void firmaConFirmaAutomatica(Map<String, Record> signedFiles, final Record[] filesAndUd, final FirmaMultiplaCallback callbackFirmaEseguita, final FirmaMultiplaNonEseguitaCallback callbackFirmaNonEseguita) {
		// Devo rifirmare tutti i file firmati al passo di firma, tranne quelli generati da modello
		Set<String> signedFileKeySet = signedFiles.keySet();
		RecordList recordList = new RecordList();
		for (String idFile : signedFileKeySet) {
			if (idFile != null && !idFile.startsWith("fileGenerato")) {
				recordList.add(signedFiles.get(idFile));
			}
		}
		// Verifico se ho file da firmare con la firma automatica
		if (!recordList.isEmpty()) {
			String userIdFirmatario = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaUseridFirmatario") : null;
			String firmaInDelega = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaFirmaInDelega") : null;
			String password = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaPassword") : null;
			String providerFirma = recordEvento != null ? recordEvento.getAttribute("docActionsFirmaAutomaticaProvider") : null;
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
			String username;
			String usernameDelegante;
			if (firmaInDelega != null && !"".equalsIgnoreCase(firmaInDelega)) {
				username =  firmaInDelega;
				usernameDelegante = userIdFirmatario;
			} else {
				username = userIdFirmatario;
				usernameDelegante = "";
			}
			FirmaUtility.firmaMultiplaHsmAutomatica(true, recordList.toArray(), username, usernameDelegante, password, providerFirma, hsmTipoFirmaAtti, callbackFirmaEseguita, callbackFirmaNonEseguita);
		 } else {
			 // Proseguo normalmente
			 callbackFirmaEseguita.execute(signedFiles, filesAndUd);
		 }
	}
	
	protected void aggiornaFileTimbrati(Record record, final ServiceCallback<Record> callback) {		
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lNuovaPropostaAtto2CompletaDataSource.executecustom("aggiornaFile", record, new DSCallback() {
	
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile() && !isForzaModificaAttoDaSbloccare()) {
						if(isSoloPreparazioneVersPubblicazione()) {
							soloPreparazioneVersPubblicazioneMode();
						} else if (isSoloOmissisModProprietaFile()) {
							soloOmissisModProprietaFileMode();
						} else if (isReadOnly()) {
							readOnlyMode();
						} else {
							editMode();
						}
					} else {
						viewMode();
					}
					if(callback != null) {
						callback.execute(lRecord);
					}				
				}
			}
		});
	}
	
	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		aggiornaFileFirmati(record, null, callback);
	}

	protected void aggiornaFileFirmati(Record record, final Record lFileUnioneOmissisNonFirmato, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lNuovaPropostaAtto2CompletaDataSource.executecustom("aggiornaFileFirmati", record, new DSCallback() {
		
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					if(lFileUnioneOmissisNonFirmato != null) {
						// se il file unione omissis è stato escluso dalla firma lo devo comunque salvare
						InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissisNonFirmato.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnioneOmissisNonFirmato.getAttributeAsRecord("infoFile")) : null;
						lRecord.setAttribute("nomeFilePrimarioOmissis", lFileUnioneOmissisNonFirmato.getAttribute("nomeFile"));
						lRecord.setAttribute("uriFilePrimarioOmissis", lFileUnioneOmissisNonFirmato.getAttribute("uri"));
						lRecord.setAttribute("remoteUriFilePrimarioOmissis", false);
						InfoFileRecord precInfoFileOmissis = lRecord.getAttributeAsRecord("infoFilePrimarioOmissis") != null ? new InfoFileRecord(lRecord.getAttributeAsRecord("infoFilePrimarioOmissis")) : null;
						String precImprontaOmissis = precInfoFileOmissis != null ? precInfoFileOmissis.getImpronta() : null;
						lRecord.setAttribute("infoFilePrimarioOmissis", infoFileUnioneOmissis);
						if (precImprontaOmissis == null || !precImprontaOmissis.equals(infoFileUnioneOmissis.getImpronta())) {
							lRecord.setAttribute("isChangedFilePrimarioOmissis", true);
						}
					}
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile() && !isForzaModificaAttoDaSbloccare()) {
						if(isSoloPreparazioneVersPubblicazione()) {
							soloPreparazioneVersPubblicazioneMode();
						} else if (isSoloOmissisModProprietaFile()) {
							soloOmissisModProprietaFileMode();
						} else if (isReadOnly()) {
							readOnlyMode();
						} else {
							editMode();
						}
					} else {
						viewMode();
					}
					if(callback != null) {
						callback.execute(lRecord);
					}
				}
			}
		});
	}
	
	protected void pubblica(final Record object) {
		Record lRecordPubblica = getRecordToSave();
		// Nel caso di determina con spesa, se sono nel task di firma del visto contabile devo passare il file allegato generato da modello e firmato in quel task, per poterlo pubblicare come allegato
		if(isDeterminaConSpesa() && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
			if(allegatoGeneratoDaModelloTask != null) {
				lRecordPubblica.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
			}	
		}
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		Layout.showWaitPopup("Pubblicazione all'Albo Pretorio in corso...");				
		lNuovaPropostaAtto2CompletaDataSource.executecustom("pubblica", lRecordPubblica, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					lRecord.setAttribute("esito", object.getAttribute("esito"));
					callbackSalvaDati(lRecord);
				} else {
					// Se va in errore l'invio in pubblicazione ricarico il dettaglio del task: in questo modo 
					// la volta successiva viene abilitata solo la pubblicazione e non di nuovo l'unione e la firma
					reload();
				}												
			}
		});		
	}
	
	public void generaFileUnione(boolean fileDaTimbrare, final ServiceCallback<Record> callback) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
		String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		generaFileUnione(fileDaTimbrare, esito, nomeFileUnione, nomeFileUnioneOmissis, impostazioniUnioneFile, callback);
	}
	
	public void generaFileUnioneVersIntegrale(final ServiceCallback<Record> callback) {
		String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		generaFileUnioneVersIntegrale(nomeFileUnione, impostazioniUnioneFile, callback);
	}
	
	public void generaFileUnioneVersXPubbl(final ServiceCallback<Record> callback) {
		String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		generaFileUnioneVersXPubbl(nomeFileUnioneOmissis, impostazioniUnioneFile, callback);
	}
	
	public void unioneFileAndReturn(boolean fileDaTimbrare, final RecordList listaRecordModelliGeneratiDaFirmare) {		
		generaFileUnione(fileDaTimbrare, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordUnioneFile) {
				if (recordEvento != null && recordEvento.getAttribute("exportAttoInDocFmt") != null && "true".equalsIgnoreCase(recordEvento.getAttribute("exportAttoInDocFmt"))) {
					hiddenForm.setValue("uriDocGeneratoFormatoOdt", recordUnioneFile.getAttribute("uriFileOdtGenerato"));
				} else {
					hiddenForm.setValue("uriDocGeneratoFormatoOdt", "");
				}
				previewFileUnioneWithFirmaAndCallback(recordUnioneFile, listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
					
					@Override
					public void execute(final Record recordUnioneFileAfterFirma) {
						Map<String, String> otherExtraparam = new HashMap<String, String>();									
						otherExtraparam.put("isAttoWithFileUnione", "true");																		
						salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {								
								if(isDaPubblicare()) {
									pubblica(object);
								} else {
									callbackSalvaDati(object);
								}
							}
						});								
					}
				});
			}
		});
	}
	
	public void previewFileUnioneWithFirmaAndCallback(final Record record, final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {			
		final String siglaRegistroAtto = recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null;				
		final String uriFileUnione = record.getAttribute("uriVersIntegrale");	
		final String nomeFileUnione = record.getAttribute("nomeFileVersIntegrale");		
		final InfoFileRecord infoFileUnione = record.getAttributeAsRecord("infoFileVersIntegrale") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFileVersIntegrale").getJsObj())) : null;
		final Record lFileUnione = (uriFileUnione != null && !"".equals(uriFileUnione)) ? new Record() : null;
		if(lFileUnione != null) {			
			lFileUnione.setAttribute("idFile", "primario" + uriFileUnione);
			lFileUnione.setAttribute("uri", uriFileUnione);
			lFileUnione.setAttribute("isFilePrincipaleAtto", true);
			lFileUnione.setAttribute("nomeFile", nomeFileUnione);
			lFileUnione.setAttribute("infoFile", infoFileUnione);
		}
		final String uriFileUnioneOmissis = record.getAttribute("uri");	
		final String nomeFileUnioneOmissis = record.getAttribute("nomeFile");		
		final InfoFileRecord infoFileUnioneOmissis = record.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj())) : null;				
		final Record lFileUnioneOmissis = (uriFileUnioneOmissis != null && !"".equals(uriFileUnioneOmissis)) ? new Record() : null;
		if(lFileUnioneOmissis != null) {
			lFileUnioneOmissis.setAttribute("idFile", "primarioOmissis" + uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("uri", uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("isFilePrincipaleAtto", true);
			lFileUnioneOmissis.setAttribute("nomeFile", nomeFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("infoFile", infoFileUnioneOmissis);
		}		
		new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean", nomeFileUnione, null) {
			
			@Override
			public boolean hideAnnullaButton() {
				// se è prevista la numerazione nel passo per l'esito scelto e la rollback NON è attiva per tutti gli atti o per lo specifico registro allora devo nascondere il bottone Annulla della preview
				if(siglaRegistroAtto != null && !"".equals(siglaRegistroAtto) && !AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
					return true;
				} else {
					return false;
				}
			}
			
			@Override
			public void manageCloseClick() {
				super.manageCloseClick();
				if(AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
					rollbackNumerazioneDefAtti();
				}
			}
			
			@Override
			public void manageOkClickAndDestroy() {
				if(lFileUnioneOmissis != null) {
					new PreviewWindowWithCallback(uriFileUnioneOmissis, false, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, null) {
						
						@Override
						public boolean hideAnnullaButton() {
							// se è prevista la numerazione nel passo per l'esito scelto e la rollback NON è attiva per tutti gli atti o per lo specifico registro allora devo nascondere il bottone Annulla della preview
							if(siglaRegistroAtto != null && !"".equals(siglaRegistroAtto) && !AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
								return true;
							} else {
								return false;
							}
						}
						
						@Override
						public void manageCloseClick() {
							super.manageCloseClick();
							if(AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
								rollbackNumerazioneDefAtti();
							}
						}
						
						@Override
						public void manageOkClickAndDestroy() {
							if(siglaRegistroAtto != null && !"".equals(siglaRegistroAtto) && !AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
								afterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, listaRecordModelliGeneratiDaFirmare, callback);
								window.markForDestroy(); // chiudo subito la preview prima di fare la firma
							} else {
								afterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										window.markForDestroy(); // chiudo la preview solo dopo la firma se è andata a buon fine, altrimenti deve rimanere aperta
										if(callback != null) {
											callback.execute(object);
										}
									}
									
								});
							}
						}
					};		
					window.markForDestroy(); // chiudo la prima preview con la versione integrale dopo aver aperto la seconda con la versione con omissis 
				} else {
					if(siglaRegistroAtto != null && !"".equals(siglaRegistroAtto) && !AurigaLayout.isAttivaRollbackNumerazoneDefAtti(siglaRegistroAtto)) {
						afterPreviewFileUnioneWithFirma(lFileUnione, null, listaRecordModelliGeneratiDaFirmare, callback);
						window.markForDestroy(); // chiudo subito la preview prima di fare la firma
					} else {
						afterPreviewFileUnioneWithFirma(lFileUnione, null, listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								window.markForDestroy(); // chiudo la preview solo dopo la firma se è andata a buon fine, altrimenti deve rimanere aperta
								if(callback != null) {
									callback.execute(object);
								}
							}
							
						});
					}
				}						
			}
		};		
	}
	
	public void rollbackNumerazioneDefAtti() {
		final Record lRecordRollback = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("rollbackNumerazioneDefAtti", lRecordRollback, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if(lRecord.getAttribute("esitoRollbackNumDefAtti") != null && "OK".equalsIgnoreCase(lRecord.getAttribute("esitoRollbackNumDefAtti"))) {
						reload();
					} else {
						// se va in errore la rollback della numerazione definitiva atto
						String messaggioFirma = "";
						Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? lRecord.getAttributeAsDate("dataRegistrazione") : null;
						if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
							messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoPostErroreRollbackNumerazioneDefinitiva();
						} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
							messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_PostErroreRollbackNumerazioneDefinitiva();
						}
						if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {					
							SC.say(messaggioFirma, new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									reload();
								}
							});
						} else {
							reload();
						}
					}
				}
			}
		});		
	}
	
	public void afterPreviewFileUnioneWithFirma(final Record lFileUnione, final Record lFileUnioneOmissis, final RecordList listaRecordModelliGeneratiDaFirmare, final ServiceCallback<Record> callback) {
		if (hasActionFirma()) {
			if (mappaInfoFirmaGrafica != null && mappaInfoFirmaGrafica.size() > 1) {
				Record recordToPass = new Record();
				recordToPass.setAttribute("mappaInfoFirmaGrafica", mappaInfoFirmaGrafica);
				new SelezionaRuoloPerApposizioneFirmaPopup(recordToPass, new ServiceCallback<List<String>>() {
					
					@Override
					public void execute(List<String> listaRuoliFirmaSelezionati) {
						continuaFirmaAfterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, listaRecordModelliGeneratiDaFirmare, listaRuoliFirmaSelezionati.toArray(new String[listaRuoliFirmaSelezionati.size()]), callback);
					}
				});
			} else if (mappaInfoFirmaGrafica != null && mappaInfoFirmaGrafica.size() == 1) {
				continuaFirmaAfterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, listaRecordModelliGeneratiDaFirmare, mappaInfoFirmaGrafica.keySet().toArray(new String[mappaInfoFirmaGrafica.keySet().size()]), callback);
			} else {
				continuaFirmaAfterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, listaRecordModelliGeneratiDaFirmare, null, callback);
			}
		} else {
			firmaAggiornaFileGenerati(listaRecordModelliGeneratiDaFirmare, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record object) {
					if(lFileUnione != null) {				
						InfoFileRecord infoFileUnione = lFileUnione.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnione.getAttributeAsRecord("infoFile")) : null;
						hiddenForm.setValue("nomeFilePrimario", lFileUnione.getAttribute("nomeFile"));
						hiddenForm.setValue("uriFilePrimario", lFileUnione.getAttribute("uri"));
						hiddenForm.setValue("remoteUriFilePrimario", false);
						InfoFileRecord precInfoFile = hiddenForm.getValue("infoFilePrimario") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimario")) : null;
						String precImpronta = precInfoFile != null ? precInfoFile.getImpronta() : null;
						hiddenForm.setValue("infoFilePrimario", infoFileUnione);
						if (precImpronta == null || !precImpronta.equals(infoFileUnione.getImpronta())) {
							hiddenForm.setValue("isChangedFilePrimario", true);
						}
						if (infoFileUnione.isFirmato() && !infoFileUnione.isFirmaValida()) {
							GWTRestDataSource.printMessage(new MessageBean("Il file primario presenta una firma non valida alla data odierna", "", MessageType.WARNING));
						}
					}
					if(lFileUnioneOmissis != null) {
						InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissis.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnioneOmissis.getAttributeAsRecord("infoFile")) : null;
						hiddenForm.setValue("nomeFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("nomeFile"));
						hiddenForm.setValue("uriFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("uri"));
						hiddenForm.setValue("remoteUriFilePrimarioOmissis", false);
						InfoFileRecord precInfoFileOmissis = hiddenForm.getValue("infoFilePrimarioOmissis") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimarioOmissis")) : null;
						String precImprontaOmissis = precInfoFileOmissis != null ? precInfoFileOmissis.getImpronta() : null;
						hiddenForm.setValue("infoFilePrimarioOmissis", infoFileUnioneOmissis);
						if (precImprontaOmissis == null || !precImprontaOmissis.equals(infoFileUnioneOmissis.getImpronta())) {
							hiddenForm.setValue("isChangedFilePrimarioOmissis", true);
						}
						if (infoFileUnioneOmissis.isFirmato() && !infoFileUnioneOmissis.isFirmaValida()) {
							GWTRestDataSource.printMessage(new MessageBean("Il file primario (vers. con omissis) presenta una firma non valida alla data odierna", "", MessageType.WARNING));
						}
					}
					if(callback != null) {
						callback.execute(getRecordToSave());
					}
				}
			});			
		}
	}

	private void continuaFirmaAfterPreviewFileUnioneWithFirma(final Record lFileUnione, final Record lFileUnioneOmissis, final RecordList listaRecordModelliGeneratiDaFirmare, String[] elencoRuoliFirmaGrafica, final ServiceCallback<Record> callback) {
		final List<Record> listaFiles = new ArrayList<Record>();
		RecordList elencoRuoliFirmaGraficaDaApplicare = null;
		if (elencoRuoliFirmaGrafica != null && elencoRuoliFirmaGrafica.length > 0) {
			elencoRuoliFirmaGraficaDaApplicare = new RecordList();
			for (int i = 0; i < elencoRuoliFirmaGrafica.length; i++) {
				elencoRuoliFirmaGraficaDaApplicare.add(mappaInfoFirmaGrafica.get(elencoRuoliFirmaGrafica[i]));
			}
		}
		if(lFileUnione != null) {
			// Nel file primario setto tutte le firme grafiche da apporre
			lFileUnione.setAttribute("listaInformazioniFirmaGrafica", elencoRuoliFirmaGraficaDaApplicare);	
			listaFiles.add(lFileUnione);
		}
		boolean skipOmissisInFirmaAdozioneAtto = AurigaLayout.getParametroDBAsBoolean("ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
		if(!skipOmissisInFirmaAdozioneAtto && lFileUnioneOmissis != null) {
			lFileUnioneOmissis.setAttribute("listaInformazioniFirmaGrafica", elencoRuoliFirmaGraficaDaApplicare);
			listaFiles.add(lFileUnioneOmissis);
		}	
		final Record lFileUnioneOmissisNonFirmato = skipOmissisInFirmaAdozioneAtto ? lFileUnioneOmissis : null;			
		getFileAllegatiDaFirmareWithFileUnione(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileAllegati = response.getData()[0];
					Record[] filesAllegati = lRecordFileAllegati.getAttributeAsRecordArray("files");
					for(int i = 0; i < filesAllegati.length; i++) {
						listaFiles.add(filesAllegati[i]);	
					}
					Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
					RecordList listaFilesDaFirmare = new RecordList();
					if(files != null) {
						listaFilesDaFirmare.addList(files);
					}
					if(listaRecordModelliGeneratiDaFirmare != null) {						
						for(int i = 0; i < listaRecordModelliGeneratiDaFirmare.getLength(); i++) {							
							String uriFileGenerato = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("uriFileGenerato");
							InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));							
							String nomeFileModello = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nomeFile") + ".pdf";
							boolean flgDaFirmare = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgDaFirmare");							
							if (flgDaFirmare) {								
								Record record = new Record();
								record.setAttribute("idFile", "fileGenerato" + uriFileGenerato); //ATTENZIONE, la parola "fileGenerato" viene usata nella successiva action FirmaAutomatica per discriminare i file da non rifirmare
								record.setAttribute("uri", uriFileGenerato);
								record.setAttribute("nomeFile", nomeFileModello);
								record.setAttribute("infoFile", infoFileGenerato);
								boolean flgFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttributeAsBoolean("flgFirmaGrafica");
								if (flgFirmaGrafica) {
									String nroColonnaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroColonnaFirmaGrafica");
									String nroRigaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroRigaFirmaGrafica");
									String nroPaginaFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("nroPaginaFirmaGrafica");
									String testoFirmaGrafica = listaRecordModelliGeneratiDaFirmare.get(i).getAttribute("testoFirmaGrafica");
									Record infoFirmaGraficaRecord = new Record();
									infoFirmaGraficaRecord.setAttribute("areaVerticale", nroRigaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("areaOrizzontale", nroColonnaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("nroPagina", nroPaginaFirmaGrafica);
									infoFirmaGraficaRecord.setAttribute("testo", testoFirmaGrafica);
									RecordList listaInfoFirmaGrafica = new RecordList();
									listaInfoFirmaGrafica.add(infoFirmaGraficaRecord);
									record.setAttribute("listaInformazioniFirmaGrafica", listaInfoFirmaGrafica);
								}
								listaFilesDaFirmare.add(record);				
							}		
						}
					}					
					if(listaFilesDaFirmare.getLength() > 0) {
						// Leggo gli eventuali parametri per forzare il tipo di firma
						String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
						String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
						FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
							@Override
							public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {
								final Record lRecord = new Record();
								lRecord.setAttribute("protocolloOriginale", getRecordToSave());
								if (hasActionFirmaAutomatica()) {
									firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
										
										@Override
										public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
											// Aggiorno i file che erano stati firmati firmati nel precedente passo di firma
											Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
											for (String idFileFiramtoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
												signedFiles.put(idFileFiramtoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFiramtoFirmaAutomatica));										
											}
											Record lRecordFileFirmati = new Record();
											lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
											lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
											aggiornaFileFirmati(lRecord, lFileUnioneOmissisNonFirmato, callback);	
										}
									}, null);
								} else {
									Record lRecordFileFirmati = new Record();
									lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
									lRecord.setAttribute("fileFirmati", lRecordFileFirmati);								
									aggiornaFileFirmati(lRecord, lFileUnioneOmissisNonFirmato, callback);								
								}
							}
						});
					} else {
						if(callback != null) {
							callback.execute(getRecordToSave());	
						}
					}
				}
			}
		});
	}
	
	public void convertiFileNonFirmato(Record pFileNonFirmato, final ServiceCallback<Record> callback) {
		if (pFileNonFirmato != null && AurigaLayout.getParametroDBAsBoolean("CONV_PDF_PRE_FIRMA")) {
			Record lRecordFiles = new Record();
			RecordList files = new RecordList();
			files.add(pFileNonFirmato);
			lRecordFiles.setAttribute("files", files);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("ConversionePdfDataSource");
			lGwtRestService.addParam("SCOPO", "FIRMA");
			// Eseguo la chiamata al datasource
			lGwtRestService.call(lRecordFiles, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					Record lFileNonFirmatoPdf = object.getAttributeAsRecordList("files").get(0);
					if(callback != null) {
						callback.execute(lFileNonFirmatoPdf);
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(pFileNonFirmato);
			}
		}
	}
	
	public void aggiornaVersDaPubblicare() {
		
		final String nomeFilePrimario = getValueAsString("nomeFilePrimario");
		final String uriFilePrimario = getValueAsString("uriFilePrimario");
		final InfoFileRecord infoFilePrimario = hiddenForm.getValue("infoFilePrimario") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimario")) : null;
		
		final String nomeFilePrimarioOmissis = getValueAsString("nomeFilePrimarioOmissis");
		final String uriFilePrimarioOmissis = getValueAsString("uriFilePrimarioOmissis");
		final InfoFileRecord infoFilePrimarioOmissis = hiddenForm.getValue("infoFilePrimarioOmissis") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimarioOmissis")) : null;
		
		PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
			
			@Override
			public void executeSalvaVersConOmissis(Record record) {
				
			}
			
			@Override
			public void executeSalva(Record record) {				
				String uri = record.getAttribute("uri");
				InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
				InfoFileRecord precInfo = null;
				if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
					precInfo = infoFilePrimarioOmissis;
				} else {
					precInfo = infoFilePrimario;
				}
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimarioOmissis", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimarioOmissis", true);					
				}
				hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE.pdf");
				hiddenForm.setValue("uriFilePrimarioOmissis", uri);
				hiddenForm.setValue("remoteUriFilePrimarioOmissis", false);				
			}
			
			@Override
			public void executeOnError() {	
				
			}
			
		};
 
		if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
			PreviewControl.switchPreview(uriFilePrimarioOmissis, true, infoFilePrimarioOmissis, "FileToExtractBean", nomeFilePrimarioOmissis, lWindowPageSelectionCallback, false, false);			
		} else {
			PreviewControl.switchPreview(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean", nomeFilePrimario, lWindowPageSelectionCallback, false, false);			
		}
	}
	
	public void riportaVersConOmissisAIntegrale() {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getVersIntegraleSenzaFirma", recordFromLoadDett, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if (lRecord != null) {
						String nomeFile = lRecord.getAttribute("nomeFile");
						String estensione = nomeFile != null ? nomeFile.substring(nomeFile.lastIndexOf(".") + 1) : null;			
						Record infofile = lRecord.getAttributeAsRecord("infoFile");
						String uriFile = lRecord.getAttributeAsString("uri");
						hiddenForm.setValue("infoFilePrimarioOmissis", infofile);
						hiddenForm.setValue("isChangedFilePrimarioOmissis", true);							
						hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE." + estensione);
						hiddenForm.setValue("uriFilePrimarioOmissis", uriFile);
						hiddenForm.setValue("remoteUriFilePrimarioOmissis", true);	
						Layout.addMessage(new MessageBean("La vers. con omissis da pubblicare è stata riportata alla versione integrale", "", MessageType.INFO));
					}
				}
			}
		});		
	}
	
	public void riportaVersConOmissisALastVersPubblicazioneFirmata() {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getLastVersPubblicazioneFirmata", recordFromLoadDett, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if (lRecord != null) {
						String nomeFile = lRecord.getAttribute("nomeFile");
						String estensione = nomeFile != null ? nomeFile.substring(nomeFile.lastIndexOf(".") + 1) : null;			
						Record infofile = lRecord.getAttributeAsRecord("infoFile");
						String uriFile = lRecord.getAttributeAsString("uri");
						hiddenForm.setValue("infoFilePrimarioOmissis", infofile);
						hiddenForm.setValue("isChangedFilePrimarioOmissis", true);							
						hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE." + estensione);
						hiddenForm.setValue("uriFilePrimarioOmissis", uriFile);
						hiddenForm.setValue("remoteUriFilePrimarioOmissis", true);	
					}
				}
			}
		});
	}

	protected void callbackSalvaDati(Record object) {
		
		idEvento = object.getAttribute("idEvento");
		
		final RecordList listaRecordModelliXEsitoPostAvanzamentoFlusso = getListaRecordModelliXEsitoPostAvanzamentoFlusso(object.getAttribute("esito"));		
		
		final Record lRecordSalvaTask = new Record();
		lRecordSalvaTask.setAttribute("instanceId", instanceId);
		lRecordSalvaTask.setAttribute("activityName", activityName);
		lRecordSalvaTask.setAttribute("idProcess", idProcess);
		lRecordSalvaTask.setAttribute("idEventType", idTipoEvento);
		lRecordSalvaTask.setAttribute("idEvento", idEvento);
		lRecordSalvaTask.setAttribute("idUd", idUd);
		lRecordSalvaTask.setAttribute("note", messaggio);
		lRecordSalvaTask.setAttribute("dettaglioBean", getRecordToSave());
		lRecordSalvaTask.setAttribute("listaRecordModelli", listaRecordModelliXEsitoPostAvanzamentoFlusso);
		
		boolean invioNotEmailFlgConfermaInvio = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio");		
		boolean flgCallXDettagliMail = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail");
				
		if (hasActionInvioNotEmail() && flgCallXDettagliMail) {
			//chiamo la store del dettaglio mail, poi se ho il flgConfermaInvio apro la popup e infine chiamo salvatask
			getDatiXInvioNotEmail(new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						recordEvento = dsResponse.getData()[0];
						if (recordEvento != null && recordEvento.getAttributeAsBoolean("flgInvioNotEmail")) {
							attrEsitoNotEmail = null;

							if ( recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio")) {

								invioNotEmail(new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if(value) {
											GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
											lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
														dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

															@Override
															public void execute(Record record) {
																Layout.hideWaitPopup();
																Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
																next();
															}
														});
													} else {
														reload();
													}
												}
											});
										} 
//										else {
//											reload();
//										}
									}				
								});
							} else {
								lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
								lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
//								lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
								
								GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");		
								lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");	
								lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

												@Override
												public void execute(Record record) {
													Layout.hideWaitPopup();
													Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
													next();
												}
											});
										} else {
											reload();
										}
									}
								});
							}
						} else {
							
							GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");	
							lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

											@Override
											public void execute(Record record) {
												Layout.hideWaitPopup();
												Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
												next();
											}
										});
									} else {
										reload();
									}
								}
							});
						}
					}
				}
			}, false);

		} else if (hasActionInvioNotEmail() && invioNotEmailFlgConfermaInvio) {
			// se ho azione invio notifica e ho il flgconferma (ma non flgCallXDettagliMail) apro la popup
			// con i dati della call execatt
			
			attrEsitoNotEmail = null;
			getDatiXInvioNotEmail(new DSCallback() {

				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						invioNotEmail(new BooleanCallback() {

							@Override
							public void execute(Boolean value) {

								if(value) {
									GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
									lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

													@Override
													public void execute(Record record) {
														Layout.hideWaitPopup();
														Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
														next();
													}
												});
											} else {
												reload();
											}
										}
									});
								}
//								else {
//									reload();
//								}
							}
						});
					}
				}
			}, true);

		} else {
						
			lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
			lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
			lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
//			lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
			lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
			
			GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");		
			if(hasActionInvioNotEmail() /*&& isEsitoTaskAzioni(attrEsitoNotEmail)*/) {
				lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
				attrEsitoNotEmail = null;
			}								
			lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

							@Override
							public void execute(Record record) {
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
								next();
							}
						});
					} else {
						reload();
					}
				}
			});
		}
			
	}
	
	protected void invioNotEmail(final BooleanCallback callback) {

		final boolean invioNotEmailFlgInvioMailXComplTask = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask");		

		DSCallback sendCallback = new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(callback != null) {
						callback.execute(true);
					}
				} else {
					if(callback != null) {
						callback.execute(invioNotEmailFlgInvioMailXComplTask ? false : true);
					}
				}
			}				
		};
		final NuovoMessaggioWindow lNuovoMessaggioWindow = new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance, sendCallback) {
			
			@Override
			public boolean isHideSalvaBozzaButton() {
				return true;
			}
			
			@Override
			public boolean getDefaultSaveSentEmail() {
				return true; // forzo il valore di default del check salvaInviati a true
			}
			
			@Override
			public Record getInitialRecordNuovoMessaggio() {
				Record lRecord = new Record();
				lRecord.setAttribute("oggetto", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
				lRecord.setAttribute("bodyHtml", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);				
				RecordList invioNotEmailDestinatari = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null;
				if(invioNotEmailDestinatari != null && invioNotEmailDestinatari.getLength() > 0) {
					String destinatari = null;
					for(int i = 0; i < invioNotEmailDestinatari.getLength(); i++) {				
						if(destinatari == null) {
							destinatari = invioNotEmailDestinatari.get(i).getAttribute("value");
						} else {
							destinatari += "; " + invioNotEmailDestinatari.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatari", destinatari);
				}				
				RecordList invioNotEmailDestinatariCC = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null;
				if(invioNotEmailDestinatariCC != null && invioNotEmailDestinatariCC.getLength() > 0) {
					String destinatariCC = null;
					for(int i = 0; i < invioNotEmailDestinatariCC.getLength(); i++) {				
						if(destinatariCC == null) {
							destinatariCC = invioNotEmailDestinatariCC.get(i).getAttribute("value");
						} else {
							destinatariCC += "; " + invioNotEmailDestinatariCC.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCC", destinatariCC);
				}				
				RecordList invioNotEmailDestinatariCCN = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null;
				if(invioNotEmailDestinatariCCN != null && invioNotEmailDestinatariCCN.getLength() > 0) {
					String destinatariCCN = null;
					for(int i = 0; i < invioNotEmailDestinatariCCN.getLength(); i++) {				
						if(destinatariCCN == null) {
							destinatariCCN = invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						} else {
							destinatariCCN += "; " + invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCCN", destinatariCCN);
				}				
				lRecord.setAttribute("mittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
				
				//TODO FEDERICA BUONO invioNotEmailAttachment
				RecordList files = recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : new RecordList();
//				String idUd = null;
//				for (Record file : files) {
//					if(idUd == null) {
//						idUd = file.getAttribute("idUd");
//					} else {
//						idUd += ";" + file.getAttribute("idUd");
//					}
//				}
//				lRecord.setAttribute("idUD", idUd); // TODO idUd da collegare alla mail
				RecordList attachList = new RecordList();
				for (int i = 0; i < files.getLength(); i++) {
					Record attach = new Record();
					attach.setAttribute("fileNameAttach", files.get(i).getAttribute("nomeFile"));
					attach.setAttribute("infoFileAttach", files.get(i).getAttributeAsRecord("infoFile"));
					attach.setAttribute("uriAttach", files.get(i).getAttribute("uri"));
					attachList.add(attach);
				}
				lRecord.setAttribute("attach", attachList);

				return lRecord;
			};
			
			@Override
			public void manageOnCloseClick() {
				if(invioNotEmailFlgInvioMailXComplTask) {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter non verrà  completato. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(false);
								}
							}
						}
					});
				} else {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter verrà  completato ugualmente. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(true);
								}
							}
						}
					});
				}			
			}
		};

	}

	protected void getDatiXInvioNotEmail(final DSCallback callback, boolean prepareAttach) {
		
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");	
		lAurigaTaskDataSource.addParam("prepareAttach", String.valueOf(prepareAttach));
		lAurigaTaskDataSource.executecustom("getDatiXInvioNotifMail", recordEvento, callback);
	}
	
	@Override
	public void caricaModelloInAllegati(String idModello, String tipoModello, final String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		
		Record lRecordCaricamentoModello = new Record();
		lRecordCaricamentoModello.setAttribute("idModello", idModello);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
		lGwtRestDataSource.getData(lRecordCaricamentoModello, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordModello = dsResponse.getData()[0];							
					Record lRecordCompilaModello = new Record();
					lRecordCompilaModello.setAttribute("processId", idProcess);
					lRecordCompilaModello.setAttribute("idUd", idUd);
					lRecordCompilaModello.setAttribute("idModello", lRecordModello.getAttribute("idModello"));
					lRecordCompilaModello.setAttribute("nomeModello", lRecordModello.getAttribute("nomeModello"));
					lRecordCompilaModello.setAttribute("tipoModello", lRecordModello.getAttribute("tipoModello"));
					lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
					final String nomeFileModello = lRecordModello.getAttribute("nomeModello") + ".pdf";					
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.executecustom("compilazioneAutomaticaModelloPdf", lRecordCompilaModello, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								final Record result = response.getData()[0];
								Record fileToUpload = new Record();								
								fileToUpload.setAttribute("nomeFilePrimario", nomeFileModello);
								fileToUpload.setAttribute("uriFilePrimario", result.getAttribute("uri"));
								fileToUpload.setAttribute("infoFile", result.getAttributeAsRecord("infoFile"));
								callback.execute(fileToUpload);								
							}
						}
					});										
				}
			}
		});
	}
	
	public void saveAndGoWithListaModelliGenAutomatica(boolean flgAfterSalva, final RecordList listaRecordModelli, final String esito, final ServiceCallback<RecordList> callback) {
		if (listaRecordModelli != null) {
			if(!flgAfterSalva) {
				// qui la chiamata per salvare i valori dei tab dinamici la devo lasciare perchè non c'è un salvataggio provvisorio che me li salva prima
				salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						compilazioneAutomaticaListaModelliPdf(listaRecordModelli, esito, callback);
					}
				});
			} else {
				salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						compilazioneAutomaticaListaModelliPdf(listaRecordModelli, esito, callback);
					}
				});
			}
		} else if(callback != null) {
			callback.execute(null);
		}
	}
	
	/**
	 * <ul>
	 * <li>Carica il modello specificato</li>
	 * <li>inietta i valori</li>
	 * <li>genera la versione pdf</li>
	 * <li>se richiesto, il file viene firmato digitalmente</li>
	 * <li>viene aggiunto agli allegati</li>
	 * </ul>
	 * 
	 * @param callback
	 */
	public void compilazioneAutomaticaListaModelliPdf(final RecordList listaRecordModelli, final String esito, final ServiceCallback<RecordList> callback) {

		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {

			Record lRecordCompilaModello = new Record();
			lRecordCompilaModello.setAttribute("processId", idProcess);
			lRecordCompilaModello.setAttribute("idUd", idUd);
			lRecordCompilaModello.setAttribute("listaRecordModelli", listaRecordModelli);
			lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2CompletaDataSource.addParam("esitoTask", esito);
			Layout.showWaitPopup("Generazione automatica file in corso...");	
			lNuovaPropostaAtto2CompletaDataSource.executecustom("compilazioneAutomaticaListaModelliPdf", lRecordCompilaModello, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						RecordList listaRecordModelliGenerati = response.getData()[0].getAttributeAsRecordList("listaRecordModelli");						
						if(listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0) {
							previewFileGenerati(0, listaRecordModelliGenerati, new ServiceCallback<RecordList>() {
								
								@Override
								public void execute(final RecordList listaRecordModelliGenerati) {
									if(listaRecordModelliGenerati != null) {										
										boolean hasFilesDaFirmare = false;
										for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {											
											boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");											
											if (flgDaFirmare) {
												hasFilesDaFirmare = true;			
											}		
										}										
										if(hasFilesDaFirmare) {
											aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, null, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													if(callback != null) {
														callback.execute(listaRecordModelliGenerati);
													}
												}
											});											
										} else {
											aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, null, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													if(callback != null) {
														callback.execute(null);
													}
												}
											});		
										}									
									} else {
										if(callback != null) {
											callback.execute(null);
										}
									}									
								}
							});
						}						
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}
	}
	
	protected void previewFileGenerati(final int i, final RecordList listaRecordModelliGenerati, final ServiceCallback<RecordList> callback) {		
		
		if(i >= 0 && listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0 && i < listaRecordModelliGenerati.getLength()) {				
		
			Record recordModello = listaRecordModelliGenerati.get(i);
			
			final String uriFileGenerato = recordModello.getAttribute("uriFileGenerato");
			final InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(recordModello.getAttributeAsRecord("infoFileGenerato").getJsObj()));
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + ".pdf";
			
			boolean flgSkipAnteprima = recordModello.getAttributeAsBoolean("flgSkipAnteprima") != null && recordModello.getAttributeAsBoolean("flgSkipAnteprima");						
			if(!flgSkipAnteprima) {
				new PreviewWindowWithCallback(uriFileGenerato, false, infoFileGenerato, "FileToExtractBean",	nomeFileModello, new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record object) {									
						previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
					}
				});
			} else {
				previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
			}
		
		} else if(callback != null) {
			callback.execute(listaRecordModelliGenerati);
		}
	}
	
	protected void firmaAggiornaFileGenerati(final RecordList listaRecordModelliGenerati, final ServiceCallback<Record> callback) {		
		if(listaRecordModelliGenerati != null) {		
			RecordList listaFilesDaFirmare = new RecordList();
			for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {				
				String uriFileGenerato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
				InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));				
				String nomeFileModello = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
				String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
				boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");				
				if (flgDaFirmare) {
					Record record = new Record();
					record.setAttribute("idFile", idTipoModello); // uso questo come campo identificativo del record per poi riaggiornare l'altra lista
					record.setAttribute("uri", uriFileGenerato);
					record.setAttribute("nomeFile", nomeFileModello);
					record.setAttribute("infoFile", infoFileGenerato);
					boolean flgFirmaGrafica = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgFirmaGrafica");
					if (flgFirmaGrafica) {
						String nroColonnaFirmaGrafica = listaRecordModelliGenerati.get(i).getAttribute("nroColonnaFirmaGrafica");
						String nroRigaFirmaGrafica = listaRecordModelliGenerati.get(i).getAttribute("nroRigaFirmaGrafica");
						String nroPaginaFirmaGrafica = listaRecordModelliGenerati.get(i).getAttribute("nroPaginaFirmaGrafica");
						String testoFirmaGrafica = listaRecordModelliGenerati.get(i).getAttribute("testoFirmaGrafica");
						Record infoFirmaGraficaRecord = new Record();
						infoFirmaGraficaRecord.setAttribute("areaVerticale", nroRigaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("areaOrizzontale", nroColonnaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("nroPagina", nroPaginaFirmaGrafica);
						infoFirmaGraficaRecord.setAttribute("testo", testoFirmaGrafica);
						RecordList listaInfoFirmaGrafica = new RecordList();
						listaInfoFirmaGrafica.add(infoFirmaGraficaRecord);
						record.setAttribute("listaInformazioniFirmaGrafica", listaInfoFirmaGrafica);
					}
					listaFilesDaFirmare.add(record);				
				}		
			}
			if(listaFilesDaFirmare.getLength() > 0) {
				// Leggo gli eventuali parametri per forzare il tipo d firma
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
				FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {
	
					@Override
					public void execute(final Map<String, Record> signedFiles, Record[] filesAndUd) {		
						if (hasActionFirmaAutomatica()) {
							firmaConFirmaAutomatica(signedFiles, filesAndUd, new FirmaMultiplaCallback() {
								
								@Override
								public void execute(Map<String, Record> filesFirmatiFirmaAutomatica, Record[] filesAndUd) {
									// Aggiorno i file che erano stati firmati nel precedente passo di firma
									Set<String> filesFirmatiFirmaAutomaticaKeySet = filesFirmatiFirmaAutomatica.keySet();
									for (String idFileFiramtoFirmaAutomatica : filesFirmatiFirmaAutomaticaKeySet) {
										signedFiles.put(idFileFiramtoFirmaAutomatica, filesFirmatiFirmaAutomatica.get(idFileFiramtoFirmaAutomatica));										
									}
									aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, signedFiles, callback);
								}
							}, null);
						} else {
							aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, signedFiles, callback);
						}
					}
				});	
			} else {
				aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, null, callback);		
			}		
		} else {
			if(callback != null) {
				callback.execute(null);
			}
		}		
	}
	
	protected void aggiungiListaModelliAdAllegati(RecordList listaRecordModelliGenerati, Map<String, Record> signedFiles, ServiceCallback<Record> callback) {
		if (allegatiForm != null) {
			
			RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
		
			for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {
				
				String descrizioneFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("descrizione");
				String nomeFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
				String uriFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
				String infoFileAllegato = JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj());						
				InfoFileRecord infoAllegato = InfoFileRecord.buildInfoFileString(infoFileAllegato);						
				
				String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
				String nomeTipoModello = listaRecordModelliGenerati.get(i).getAttribute("nomeTipoDoc");
				boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");
										
				if (flgDaFirmare && signedFiles != null) {							
					Record lRecordFileFirmato = signedFiles.get(idTipoModello);
					if(lRecordFileFirmato != null) {
						nomeFileAllegato = lRecordFileFirmato.getAttribute("nomeFile");
						uriFileAllegato = lRecordFileFirmato.getAttribute("uri");
						infoAllegato = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileFirmato.getAttributeAsRecord("infoFile").getJsObj()));
					}
				}
				
				int posModello = -1;
				if(listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgCreaNuovoDoc")) {
					posModello = getPosAllegatoFromTipoSenzaIdDocConFileGenDaModelloDaFirmareNonFirmato(idTipoModello, listaAllegati);
				} else {
					posModello = getPosAllegatoFromTipo(idTipoModello, listaAllegati);
				}
				
				Record lRecordModello = new Record();		
				if (posModello != -1) {
					// versiona perchè non cancella idDoc
					lRecordModello = listaAllegati.get(posModello);
				}
								
				// mi salvo l'informazione che è un file generato da modello
				lRecordModello.setAttribute("flgGenAutoDaModello", true);
				
				// mi salvo l'informazione che è un file generato da modello da firmare
				lRecordModello.setAttribute("flgGenDaModelloDaFirmare", flgDaFirmare);
				
				boolean flgParere = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere");									
				boolean flgParteDispositivo = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo");									
				boolean flgNoPubbl = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl");									
				boolean flgPubblicaSeparato = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato");									
				lRecordModello.setAttribute("flgParere", flgParere);
				if(flgParere) {
					lRecordModello.setAttribute("flgParteDispositivo", false);
					lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);
					lRecordModello.setAttribute("flgPubblicaSeparato", true);
				} else {
					lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
					if(!flgParteDispositivo) {
						lRecordModello.setAttribute("flgNoPubblAllegato", true);
						lRecordModello.setAttribute("flgPubblicaSeparato", false);
						lRecordModello.setAttribute("flgDatiSensibili", false);
					} else {
						lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);	
						lRecordModello.setAttribute("flgPubblicaSeparato", flgPubblicaSeparato);
					}
				}
				
				lRecordModello.setAttribute("nomeFileAllegato", nomeFileAllegato);
				lRecordModello.setAttribute("uriFileAllegato", uriFileAllegato);
				lRecordModello.setAttribute("descrizioneFileAllegato", descrizioneFileAllegato);

				lRecordModello.setAttribute("listaTipiFileAllegato", idTipoModello);
				lRecordModello.setAttribute("idTipoFileAllegato", idTipoModello);
				lRecordModello.setAttribute("descTipoFileAllegato", nomeTipoModello);

				lRecordModello.setAttribute("nomeFileAllegatoTif", "");
				lRecordModello.setAttribute("uriFileAllegatoTif", "");
				lRecordModello.setAttribute("remoteUri", false);
				lRecordModello.setAttribute("isChanged", true);
				lRecordModello.setAttribute("nomeFileVerPreFirma", nomeFileAllegato);
				lRecordModello.setAttribute("uriFileVerPreFirma", uriFileAllegato);
				lRecordModello.setAttribute("infoFileVerPreFirma", infoAllegato);
				lRecordModello.setAttribute("improntaVerPreFirma", infoAllegato.getImpronta());
				lRecordModello.setAttribute("infoFile", infoAllegato);
				
				String idTipoDocAllegatoVistoContabile = AurigaLayout.getParametroDB("ID_DOC_TYPE_VISTO_CONTAB_ITER_ATTI");
				if(idTipoDocAllegatoVistoContabile != null && idTipoModello != null && idTipoModello.equals(idTipoDocAllegatoVistoContabile)) {
					allegatoGeneratoDaModelloTask = lRecordModello;
				}
				
				if (posModello == -1) {
					if (listaAllegati == null || listaAllegati.getLength() == 0) {
						listaAllegati = new RecordList();
					}
					listaAllegati.addAt(lRecordModello, 0);
				} else {
					listaAllegati.set(posModello, lRecordModello);
				}
			}
				
			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaAllegati", listaAllegati);
			allegatiForm.setValues(lRecordForm.toMap());
								
			if(listaAllegatiItem != null) {
				if(listaAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)listaAllegatiItem).resetCanvasChanged();
				} else if(listaAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)listaAllegatiItem).resetCanvasChanged();
				}
			}

//			if (detailSectionAllegati != null) {
//				detailSectionAllegati.openIfhasValue();
//			}	
			
		}
		
		if(callback != null) {
			callback.execute(new Record());
		}	
	}
	
	public boolean isDataRegistrazioneSameToday(Date dataRegistrazione){
		Date today = new Date();
		
		if ( dataRegistrazione == null)
			return true;
		
		if ( CalendarUtil.isSameDate(dataRegistrazione, today) )
			return true;
		else
			return false;
		
	}
	
	@Override
	public Record getRecordEventoXInfoModelli() {
		return recordEvento;
	}
	
	@Override
	public String getIdModDispositivo() {
		return idModAssDocTask != null ? idModAssDocTask : "";
	}
	
	@Override
	public String getNomeModDispositivo() {
		return nomeModAssDocTask != null ? nomeModAssDocTask : "";
	}
	
	@Override
	public String getDisplayFilenameModDispositivo() {
		return displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "";
	}
	
	@Override
	public String getIdModAppendice() {
		return idModAppendice != null ? idModAppendice : "";
	}
	
	@Override
	public String getNomeModAppendice() {
		return nomeModAppendice != null ? nomeModAppendice : "";
	}
	
	@Override
	public String getUriAppendice() {
		return uriAppendice != null ? uriAppendice : "";
	}
	
	@Override
	public String getDisplayFilenameModAppendice() {
		return displayFilenameModAppendice != null ? displayFilenameModAppendice : "";
	}
	
	@Override
	public String getIdModFoglioFirme() {
		return idModFoglioFirme != null ? idModFoglioFirme : "";
	}
	
	@Override
	public String getNomeModFoglioFirme() {
		return nomeModFoglioFirme != null ? nomeModFoglioFirme : "";
	}	
	
	@Override
	public String getDisplayFilenameModFoglioFirme() {
		return displayFilenameModFoglioFirme != null ? displayFilenameModFoglioFirme : "";
	}
	
	@Override
	public String getIdModFoglioFirme2() {
		return idModFoglioFirme2 != null ? idModFoglioFirme2 : "";
	}
	
	@Override
	public String getNomeModFoglioFirme2() {
		return nomeModFoglioFirme2 != null ? nomeModFoglioFirme2 : "";
	}	
	
	@Override
	public String getDisplayFilenameModFoglioFirme2() {
		return displayFilenameModFoglioFirme2 != null ? displayFilenameModFoglioFirme2 : "";
	}
	
	@Override
	public String getIdModSchedaTrasp() {
		return idModSchedaTrasp != null ? idModSchedaTrasp : "";
	}
	
	@Override
	public String getNomeModSchedaTrasp() {
		return nomeModSchedaTrasp != null ? nomeModSchedaTrasp : "";
	}	
	
	@Override
	public String getDisplayFilenameModSchedaTrasp() {
		return displayFilenameModSchedaTrasp != null ? displayFilenameModSchedaTrasp : "";
	}
		
//	public void saveAndReloadTask() {
//	salvaDatiProvvisorio();
//}
	
	@Override
	public boolean showSalvaModello() {
		return true;
	}
	
	@Override
	public String getPrefKeyModelliDSprefix() {
		return "evento" + dettaglioPraticaLayout.getIdTipoProc();
	}
	
	public boolean isFirmaAppostaTramiteFileOp() {
		return false;
	}

	public void settaInformazioniApposizioneFirmaFileOp(Record fileDaFirmare) {
		// Prendo le informazioni della callExecAtt
		Record informazioniFirmaGrafica = new Record();
		fileDaFirmare.setAttribute("informazioniFirmaGrafica", informazioniFirmaGrafica);
	}

	/**
	 * @param lRecord
	 */
	public void manageResultVerificaDisponibilitaImporto(final Record lRecord) {
		Record resultVerificaImportoADSPBean = lRecord.getAttributeAsRecord("resultVerificaImportoADSPBean");
		if(resultVerificaImportoADSPBean!=null && resultVerificaImportoADSPBean.getAttributeAsBoolean("inError")){
		
			SC.warn(lRecord.getAttributeAsString("defaultMessage"), new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					RecordList listaProposteConcorrenti = lRecord.getAttributeAsRecordList("listaProposteConcorrenti");														
					if(listaProposteConcorrenti != null && listaProposteConcorrenti.getLength() > 0) {

						RecordList listaErrori = new RecordList();
						for (int i = 0; i < listaProposteConcorrenti.getLength(); i++) {
							Record propostaConcorrente = listaProposteConcorrenti.get(i);
							
							Record recordErrore = new Record();
							recordErrore.setAttribute("estremiProposta", propostaConcorrente.getAttribute("estremiProposta"));
							recordErrore.setAttribute("oggettoProposta", propostaConcorrente.getAttribute("oggettoProposta"));
							recordErrore.setAttribute("importoProposta", propostaConcorrente.getAttribute("importoProposta"));
							recordErrore.setAttribute("capitoloProposta", propostaConcorrente.getAttribute("capitoloProposta"));
							recordErrore.setAttribute("contoProposta", propostaConcorrente.getAttribute("contoProposta"));
							listaErrori.add(recordErrore);

						}
						ErroreProposteConcorrentiPopUp errorePopup = new ErroreProposteConcorrentiPopUp(nomeEntita, listaErrori,
								LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS, "Atti in iter che insistono sullo stesso capitolo/conto") {
							
							@Override
							public void manageOnClick() {			
								String modCtrlDispCapitoliRda = AurigaLayout.getParametroDB("MOD_CTRL_DISP_CAPITOLI_IN_RDA");
								if(modCtrlDispCapitoliRda!=null && !"".equalsIgnoreCase(modCtrlDispCapitoliRda) && modCtrlDispCapitoliRda.equalsIgnoreCase("WARNING")) {
									SC.ask("Vuoi comunque procedere all'inserimento/aggiornamento del movimento contabile anche se non risulta esserci sufficiente disponibilità ? " , new BooleanCallback() {					
										@Override
										public void execute(Boolean value) {				
											if(value) {
												salvaDati(editing, attributiAddDocTabs, null);
											}
										}
									});
								}else if(modCtrlDispCapitoliRda!=null && !"".equalsIgnoreCase(modCtrlDispCapitoliRda) && modCtrlDispCapitoliRda.equalsIgnoreCase("BLOCCANTE")) {
									closeWindow();
								}else {
									salvaDati(editing, attributiAddDocTabs, null);
								}
							
							};
						};
						errorePopup.show();
					
					}										
				}
			});
		
		}else {
			salvaDati(editing, attributiAddDocTabs, null);
		}
	}
		
}
