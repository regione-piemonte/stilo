/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
//import com.smartgwt.client.widgets.form.FormItemIfFunction;
//import com.smartgwt.client.widgets.form.fields.FormItem;
//import com.smartgwt.client.widgets.form.fields.HiddenItem;
//import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
//import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoInterface;
//import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaDatiContabiliDinamicaItem;
//import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaDatiContabiliRichiestiDinamicaWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
//import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class TaskRevocaNuovaPropostaAtto2Detail extends RevocaNuovaPropostaAtto2Detail implements PropostaAttoInterface {

	protected TaskRevocaNuovaPropostaAtto2Detail instance;
	
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
	protected Boolean flgParteDispositivoTaskDoc;
	
	protected String idModCopertina;
	protected String nomeModCopertina;
	protected String uriModCopertina;
	protected String tipoModCopertina;
	protected String idModCopertinaFinale;
	protected String nomeModCopertinaFinale;
	protected String uriModCopertinaFinale;
	protected String tipoModCopertinaFinale;
	protected String idModAppendice;
	protected String nomeModAppendice;
	protected String uriModAppendice;
	protected String tipoModAppendice;
	protected String idModAssDocTask;
	protected String nomeModAssDocTask;
	protected String displayFilenameModAssDocTask;
	
	protected Boolean flgUnioneFile;
	protected Boolean flgFirmaFile;
	protected Boolean flgPubblica;
	protected Boolean flgInvioNotEmail;
	protected Boolean flgDatiSpesaEditabili;
	protected Boolean flgCIGEditabile;
	
	protected String tipoEventoSIB;
	protected Set<String> esitiTaskEventoSIB;	
	protected HashMap<String, String> mappaTipiEventoSIBXEsitoTask;	
	protected String idUoDirAdottanteSIB;
	protected String codUoDirAdottanteSIB;
	protected String desUoDirAdottanteSIB;
	
	protected Boolean flgAttivaRequestMovimentiDaAMC;
	protected Boolean flgAttivaSalvataggioMovimentiDaAMC;	
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	protected RecordList listaRecordModelli;
	protected Record allegatoGeneratoDaModelloTask;
	
	protected Set<String> esitiTaskOk;	
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;	
	
	protected Set<String> esitiTaskAzioni;
	
	protected HashSet<String> attributiAddDocTabsDatiStorici;

	protected Record attrEsito;
	protected Record attrEsitoNotEmail;
	protected String messaggio;
	
//	protected NuovaPropostaAtto2DetailSection detailSectionDatiContabili;
//	protected DynamicForm datiContabiliForm1;	
//	protected CheckboxItem flgModificaDatiContabiliItem;
//	protected DynamicForm datiContabiliForm2;	
//	protected HiddenItem attrListaDatiContabiliRichiestiItem;
//	protected ListaDatiContabiliDinamicaItem listaDatiContabiliDinamicaItem;	
	
	public TaskRevocaNuovaPropostaAtto2Detail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, getAttributAddDocTabs(lRecordEvento));

		instance = this;

		this.recordEvento = lRecordEvento;

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
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		
		this.idModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertina") : null;
		this.nomeModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertina") : null;
		this.uriModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertina") : null;
		this.tipoModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertina") : null;		
		this.idModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertinaFinale") : null;
		this.nomeModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertinaFinale") : null;
		this.uriModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertinaFinale") : null;
		this.tipoModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertinaFinale") : null;
		this.idModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("idModAppendice") : null;
		this.nomeModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAppendice") : null;
		this.uriModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAppendice") : null;
		this.tipoModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAppendice") : null;
		this.idModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idModAssDocTask") : null;		
		this.nomeModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAssDocTask") : null;
		this.displayFilenameModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : null;
		
		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgPubblica = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblica") : null;
		this.flgInvioNotEmail = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioNotEmail") : null;
		this.flgDatiSpesaEditabili = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgDatiSpesaEditabili") : null;
		this.flgCIGEditabile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgCIGEditabile") : null;
		
		this.tipoEventoSIB = lRecordEvento != null ? lRecordEvento.getAttribute("tipoEventoSIB") : null;
		this.idUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("idUoDirAdottanteSIB") : null;
		this.codUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("codUoDirAdottanteSIB") : null;
		this.desUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("desUoDirAdottanteSIB") : null;
		
		this.flgAttivaRequestMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaRequestMovimentiDaAMC") : null;
		this.flgAttivaSalvataggioMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaSalvataggioMovimentiDaAMC") : null;
		
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
		
		build();
	}
	
	@Override
	public boolean skipSuperBuild() {
		return true; // evito di fare la build nel costruttore della superclasse, in modo da farla poi alla fine, dopo aver inizializzato recordEvento e le altre variabili che mi servono nella build
	}
	
	/*
	public String getNomeAttrListaDatiContabili() {
		return recordEvento != null ? recordEvento.getAttribute("nomeAttrListaDatiContabili") : null;
	}
	
	public String getNomeAttrListaDatiContabiliRichiesti() {
		return recordEvento != null ? recordEvento.getAttribute("nomeAttrListaDatiContabiliRichiesti") : null;
	}
	
	public boolean showFlgModificaDatiContabili() {
		return true;
	}
	
	public boolean showDetailSectionDatiContabili() {
		return getNomeAttrListaDatiContabili() != null && !"".equals(getNomeAttrListaDatiContabili());
	}
	
	public boolean isPresentiDatiContabili() {
		return datiContabiliForm2 != null && datiContabiliForm2.getValueAsRecordList(getNomeAttrListaDatiContabili()) != null && datiContabiliForm2.getValueAsRecordList(getNomeAttrListaDatiContabili()).getLength() > 0;		
	}
	
	@Override
	public VLayout getLayoutDatiSpesa() {
		
		VLayout layoutDatiSpesa = super.getLayoutDatiSpesa();
		
		datiContabiliForm1 = new DynamicForm();
		datiContabiliForm1.setValuesManager(vm);
		datiContabiliForm1.setWidth100();
		datiContabiliForm1.setPadding(5);
		datiContabiliForm1.setWrapItemTitles(false);
		datiContabiliForm1.setNumCols(20);
		datiContabiliForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliForm1.setTabSet(tabSet);
		datiContabiliForm1.setTabID(_TAB_DATI_SPESA_ID);
		
		flgModificaDatiContabiliItem = new CheckboxItem("flgModificaDatiContabili", "serve modificare i dati contabili");
		flgModificaDatiContabiliItem.setDefaultValue(false);
		flgModificaDatiContabiliItem.setStartRow(true);
		flgModificaDatiContabiliItem.setColSpan(20);
		flgModificaDatiContabiliItem.setWidth("*");
		flgModificaDatiContabiliItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					listaDatiContabiliDinamicaItem.setCanEdit(true);					
				} else {
					SC.ask("Procedendo verranno ripristinati i dati indicati dal richiedente perdendo eventuali modifiche apportate dalla Ragioneria. Sei sicuro di voler procedere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value != null && value) {
								RecordList data = new RecordList();
								if(getNomeAttrListaDatiContabiliRichiesti() != null && !"".equals(getNomeAttrListaDatiContabiliRichiesti())) {
									data = datiContabiliForm2.getValueAsRecordList(getNomeAttrListaDatiContabiliRichiesti());
								}
								listaDatiContabiliDinamicaItem.setData(data);
								listaDatiContabiliDinamicaItem.setCanEdit(false);
							}
						}
					});
				}
			}
		});
		flgModificaDatiContabiliItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgModificaDatiContabili();
			}
		});
		
		datiContabiliForm1.setItems(flgModificaDatiContabiliItem);
		
		datiContabiliForm2 = new DynamicForm();
		datiContabiliForm2.setValuesManager(vm);
		datiContabiliForm2.setWidth100();
		datiContabiliForm2.setPadding(5);
		datiContabiliForm2.setWrapItemTitles(false);
		datiContabiliForm2.setNumCols(20);
		datiContabiliForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliForm2.setTabSet(tabSet);
		datiContabiliForm2.setTabID(_TAB_DATI_SPESA_ID);
		
		detailSectionDatiContabili = new NuovaPropostaAtto2DetailSection("Griglia dati contabili", true, true, true, datiContabiliForm1, datiContabiliForm2);
		
		layoutDatiSpesa.addMember(detailSectionDatiContabili);

		return layoutDatiSpesa;
	}
	
	public Record getAttrListaDatiContabili(RecordList attributiAddSenzaCategoria) {
		Record attrListaDatiContabili = null;
		if (getNomeAttrListaDatiContabili() != null && !"".equals(getNomeAttrListaDatiContabili())) {			
			if (attributiAddSenzaCategoria != null && !attributiAddSenzaCategoria.isEmpty()) {
				for (int i = 0; i < attributiAddSenzaCategoria.getLength(); i++) {
					Record attr = attributiAddSenzaCategoria.get(i);
					if (attr.getAttribute("nome") != null && attr.getAttribute("nome").equalsIgnoreCase(getNomeAttrListaDatiContabili())) {
						attrListaDatiContabili = attr;
						break;
					}
				}
			}
		}		
		return attrListaDatiContabili;
	}
	
	public Record getAttrListaDatiContabiliRichiesti(RecordList attributiAddSenzaCategoria) {
		Record attrListaDatiContabiliRichiesti = null;
		if (getNomeAttrListaDatiContabiliRichiesti() != null && !"".equals(getNomeAttrListaDatiContabiliRichiesti())) {			
			if (attributiAddSenzaCategoria != null && !attributiAddSenzaCategoria.isEmpty()) {
				for (int i = 0; i < attributiAddSenzaCategoria.getLength(); i++) {
					Record attr = attributiAddSenzaCategoria.get(i);
					if (attr.getAttribute("nome") != null && attr.getAttribute("nome").equalsIgnoreCase(getNomeAttrListaDatiContabiliRichiesti())) {
						attrListaDatiContabiliRichiesti = attr;
						break;
					}
				}
			}
		}
		return attrListaDatiContabiliRichiesti;
	}
	
	@Override
	public void manageAttributiAddSenzaCategoria(RecordList attributiAddSenzaCategoria, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista) {
		
		final Record attrListaDatiContabili = getAttrListaDatiContabili(attributiAddSenzaCategoria);
		final ArrayList<Map> dettAttrListaDatiContabili = attrListaDatiContabili != null ? (ArrayList<Map>) mappaDettAttrLista.get(attrListaDatiContabili.getAttribute("nome")) : null;
		final ArrayList<Map> valoriAttrListaDatiContabili = attrListaDatiContabili != null ? (ArrayList<Map>) mappaValoriAttrLista.get(attrListaDatiContabili.getAttribute("nome")) : null;
		final ArrayList<Map> variazioniAttrListaDatiContabili = attrListaDatiContabili != null ? (ArrayList<Map>) mappaVariazioniAttrLista.get(attrListaDatiContabili.getAttribute("nome")) : null;			
		
		final Record attrListaDatiContabiliRichiesti = getAttrListaDatiContabiliRichiesti(attributiAddSenzaCategoria);
		final ArrayList<Map> dettAttrListaDatiContabiliRichiesti = attrListaDatiContabiliRichiesti != null ? (ArrayList<Map>) mappaDettAttrLista.get(attrListaDatiContabiliRichiesti.getAttribute("nome")) : null;
		final ArrayList<Map> valoriAttrListaDatiContabiliRichiesti = attrListaDatiContabiliRichiesti != null ? (ArrayList<Map>) mappaValoriAttrLista.get(attrListaDatiContabiliRichiesti.getAttribute("nome")) : null;
		final ArrayList<Map> variazioniAttrListaDatiContabiliRichiesti = attrListaDatiContabiliRichiesti != null ? (ArrayList<Map>) mappaVariazioniAttrLista.get(attrListaDatiContabiliRichiesti.getAttribute("nome")) : null;
		
		List<FormItem> datiContabiliItems = new ArrayList<FormItem>();
		Record values = new Record();
		
		if (attrListaDatiContabili != null) {		
			listaDatiContabiliDinamicaItem = new ListaDatiContabiliDinamicaItem(attrListaDatiContabili, dettAttrListaDatiContabili) {
				
				@Override
				public void init(FormItem item) {
					super.init(item);
					setCanEdit(new Boolean(isEseguibile()));
				}
				
				@Override
				public boolean isGrigliaEditabile() {
					return true;
				}
				
				@Override
				public boolean isShowDatiStoriciButton() {
					return attrListaDatiContabiliRichiesti != null && showFlgModificaDatiContabili() && getValueAsBoolean("flgModificaDatiContabili");							
				}
				
				@Override
				public void onClickDatiStoriciButton() {
					ListaDatiContabiliRichiestiDinamicaWindow lListaDatiContabiliRichiestiDinamicaWindow = new ListaDatiContabiliRichiestiDinamicaWindow("datiContabiliRichiestiWindow", attrListaDatiContabiliRichiesti, dettAttrListaDatiContabiliRichiesti, datiContabiliForm2.getValueAsRecordList(getNomeAttrListaDatiContabiliRichiesti()));
					lListaDatiContabiliRichiestiDinamicaWindow.show();
				}
				
				public boolean isShowRefreshListButton() {
					return false;
				};
			};
			listaDatiContabiliDinamicaItem.setStartRow(true);
			listaDatiContabiliDinamicaItem.setShowTitle(false);
			listaDatiContabiliDinamicaItem.setHeight(245);	
			datiContabiliItems.add(listaDatiContabiliDinamicaItem);
			if (valoriAttrListaDatiContabili != null) {
				RecordList valoriLista = new RecordList();
				for (Map mapValori : valoriAttrListaDatiContabili) {
					Record valori = new Record(mapValori);
					for (Map dett : dettAttrListaDatiContabili) {
						if ("CHECK".equals(dett.get("tipo"))) {
							if (valori.getAttribute((String) dett.get("nome")) != null
									&& !"".equals(valori.getAttribute((String) dett.get("nome")))) {
								String valueStr = valori.getAttribute((String) dett.get("nome")) != null ? valori.getAttribute(
										(String) dett.get("nome")).trim() : "";
								Boolean value = Boolean.FALSE;
								if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
									value = Boolean.TRUE;
								}
								valori.setAttribute((String) dett.get("nome"), value);
							}
						}
					}
					valoriLista.add(valori);
				}
				values.setAttribute(getNomeAttrListaDatiContabili(), valoriLista);																		
			}
		}
		if (attrListaDatiContabiliRichiesti != null) {						
			attrListaDatiContabiliRichiestiItem = new HiddenItem(getNomeAttrListaDatiContabiliRichiesti());			
			datiContabiliItems.add(attrListaDatiContabiliRichiestiItem);
			if (valoriAttrListaDatiContabiliRichiesti != null) {
				RecordList valoriLista = new RecordList();
				for (Map mapValori : valoriAttrListaDatiContabiliRichiesti) {
					Record valori = new Record(mapValori);
					for (Map dett : dettAttrListaDatiContabiliRichiesti) {
						if ("CHECK".equals(dett.get("tipo"))) {
							if (valori.getAttribute((String) dett.get("nome")) != null
									&& !"".equals(valori.getAttribute((String) dett.get("nome")))) {
								String valueStr = valori.getAttribute((String) dett.get("nome")) != null ? valori.getAttribute(
										(String) dett.get("nome")).trim() : "";
								Boolean value = Boolean.FALSE;
								if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
									value = Boolean.TRUE;
								}
								valori.setAttribute((String) dett.get("nome"), value);
							}
						}
					}
					valoriLista.add(valori);
				}
				values.setAttribute(getNomeAttrListaDatiContabiliRichiesti(), valoriLista);													
			}
		}		
		
		datiContabiliForm2.setItems(datiContabiliItems.toArray(new FormItem[datiContabiliItems.size()]));		
		datiContabiliForm2.editRecord(values);					
	}
	
	@Override
	public void showHideSections() {
		
		super.showHideSections();
		
		if(detailSectionDatiContabili != null) {			
			if(showDetailSectionDatiContabili()) {
				detailSectionDatiContabili.show();
			} else {
				detailSectionDatiContabili.hide();	
			}
		}
	}
	*/
	
	@Override
	public boolean showDetailSectionPubblicazione() {
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
	
	@Override
	public boolean showDesUORegistrazioneItem() {
		return false; // nei task degli atti non va mostrato (si vede già nell'intestazione)
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
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean hasActionPubblica() {
		return flgPubblica != null && flgPubblica;
	}
	
	public boolean hasActionInvioNotEmail() {
		return flgInvioNotEmail != null && flgInvioNotEmail;
	}
	
	public boolean isDatiSpesaEditabili() {
		return flgDatiSpesaEditabili != null && flgDatiSpesaEditabili;
	}
	
	public boolean isCIGEditabile() {
		return flgCIGEditabile != null && flgCIGEditabile;
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
	
	public boolean isAttivaRequestMovimentiDaAMC() {
		return flgAttivaRequestMovimentiDaAMC;
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC() {
		return flgAttivaSalvataggioMovimentiDaAMC;
	}	
	
	public boolean isEseguibile() {
		boolean isEseguibile = true;
		if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
		}
		return isEseguibile;
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
	
	protected boolean isDelibera() {
		return recordEvento != null ? recordEvento.getAttributeAsBoolean("flgDelibera") : false;
	}
	
	protected boolean showDirigentiProponenti() {
		String showDirigentiProponenti = recordEvento != null ? recordEvento.getAttribute("showDirigentiProponenti") : null;
		return showDirigentiProponenti != null && !"".equals(showDirigentiProponenti);		
	}
	
	protected boolean isRequiredDirigentiProponenti() {
		String showDirigentiProponenti = recordEvento != null ? recordEvento.getAttribute("showDirigentiProponenti") : null;
		return showDirigentiProponenti != null && _MANDATORY.equalsIgnoreCase(showDirigentiProponenti);
	}

	protected boolean showAssessori() {
		String showAssessori = recordEvento != null ? recordEvento.getAttribute("showAssessori") : null;
		return showAssessori != null && !"".equals(showAssessori);	
	}
	
	protected boolean isRequiredAssessori() {
		String showAssessori = recordEvento != null ? recordEvento.getAttribute("showAssessori") : null;
		return showAssessori != null && _MANDATORY.equalsIgnoreCase(showAssessori);	
	}

	protected boolean showConsiglieri() {
		String showConsiglieri = recordEvento != null ? recordEvento.getAttribute("showConsiglieri") : null;
		return showConsiglieri != null && !"".equals(showConsiglieri);		
	}
	
	protected boolean isRequiredConsiglieri() {
		String showConsiglieri = recordEvento != null ? recordEvento.getAttribute("showConsiglieri") : null;
		return showConsiglieri != null && _MANDATORY.equalsIgnoreCase(showConsiglieri);		
	}
	
	@Override
	public HashSet<String> getTipiModelliAttiInAllegati() {
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

	@Override
	public boolean hasDocumento() {
		return false;
	}

	public void afterShow() {
		
	}

	public void readOnlyMode() {
		viewMode();
		if(dataInizioPubblicazioneItem != null) {
			dataInizioPubblicazioneItem.setCanEdit(true);
		}		
		if(giorniPubblicazioneItem != null) {
			giorniPubblicazioneItem.setCanEdit(true);
		}
		if(isCIGEditabile()) {
			setCanEdit(true, CIGForm);
		}
		if(listaAllegatiItem != null) {
			listaAllegatiItem.readOnlyMode();
		}
		if(isDatiSpesaEditabili()) {
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_ID)) {
				setCanEdit(true, form);
			}
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
		if(!isCIGEditabile()) {
			setCanEdit(false, CIGForm);
		}
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
				controllaTotali(true);
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);
				if (isEseguibile()) {
					if (isReadOnly()) {
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
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.addParam("idProcess", idProcess);
		lNuovaPropostaAtto2DataSource.addParam("taskName", activityName);
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2DataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}						
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
					if (isEseguibile() && !isReadOnly()) {
						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
							RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
								int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
								if (posModello != -1) {
									listaAllegati.removeAt(posModello);
								}
							}
							lRecord.setAttribute("listaAllegati", listaAllegati);
						}
					}
					editRecord(lRecord);
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
	
	public void salvaDati(final boolean flgIgnoreObblig, final Map<String, String> otherExtraparam, final ServiceCallback<Record> callback) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {				
				Record lRecordToSave = getRecordToSave();
				final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
				lNuovaPropostaAtto2DataSource.setTimeout(Integer.MAX_VALUE); // setto al massimo il timeout in salvataggio
				if(otherExtraparam != null) {
					for(String key : otherExtraparam.keySet()) {
						lNuovaPropostaAtto2DataSource.addParam(key, otherExtraparam.get(key));
					}
				}
				lNuovaPropostaAtto2DataSource.addParam("idProcess", idProcess);
				lNuovaPropostaAtto2DataSource.addParam("taskName", activityName);
				if (isReadOnly()) {
					lNuovaPropostaAtto2DataSource.addParam("idTaskCorrente", getIdTaskCorrente());
				}
				if(isAttivaSalvataggioMovimentiDaAMC()) {
					lNuovaPropostaAtto2DataSource.addParam("flgAttivaSalvataggioMovimentiDaAMC", "true");
				}				
				final Map<String, Object> attributiDinamiciEvent = getAttributiDinamici();
				final Map<String, String> tipiAttributiDinamiciEvent = getTipiAttributiDinamici();
				final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
				// se è il salvataggio finale
				if (!flgIgnoreObblig) {
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
					if(_FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa")) && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
//						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
//							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
//								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
//								RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
//								int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
//								if (posModello != -1) {						
//									lRecordToSave.setAttribute("allegatoVistoContabile", listaAllegati.get(posModello));
//								}
//							}
//						}
						if(allegatoGeneratoDaModelloTask != null) {
							lRecordToSave.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
						}								
					}
					if (!isAvvioPropostaAtto() && isEseguibile() && !isReadOnly()) {
						lNuovaPropostaAtto2DataSource.addParam("versionaFileDispositivo", "true");
						if(hasPrimarioDatiSensibili()) {
							lNuovaPropostaAtto2DataSource.addParam("hasPrimarioDatiSensibili", "true");
						}
					}
					if(attrEsito != null) {
						lNuovaPropostaAtto2DataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
						lNuovaPropostaAtto2DataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
						attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
						tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
						attrEsitoNotEmail = new Record(attrEsito.toMap());
						attrEsito = null;
					}
				}
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());	
				lNuovaPropostaAtto2DataSource.updateData(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							salvaAttributiDinamiciDoc(flgIgnoreObblig, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

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
					if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
					}
				}					
			}
		}
	}
	*/
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate(); // perche estendo NuovaPropostaAtto2Detail
		
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
		
//		if(listaDatiContabiliDinamicaItem != null) {
//			if(showTabDatiSpesa() && showDetailSectionDatiContabili()) {
//				if(listaDatiContabiliDinamicaItem.getAttributeAsBoolean("obbligatorio") != null && listaDatiContabiliDinamicaItem.getAttributeAsBoolean("obbligatorio")) {
//					if(showTabDatiSpesa() && showDetailSectionDatiContabili() && !isPresentiDatiContabili()) {
//						datiContabiliForm2.setFieldErrors(getNomeAttrListaDatiContabili(), "Nessun dato contabile specificato nella griglia: devi indicarne almeno uno");
//						detailSectionDatiContabili.open();
//						valid = false;
//					}						
//				}
//			}			
//		}
		
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
				AllegatoCanvas lAllegatoCanvas = listaAllegatiItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (flgObbligatorio && lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaAllegatiItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
					if(flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", true);
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
				if (detailSectionAllegati != null) {
					detailSectionAllegati.open();
				}			
			}
		}		
		return valid;
	}

	public Boolean validateSenzaObbligatorieta() {
		
		clearTabErrors(tabSet);
		vm.clearErrors(true);
		boolean valid = true;
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
		
		if (validateSenzaObbligatorieta()) {
			Map<String, String> otherExtraparam = new HashMap<String, String>();									
			otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_PROVVISORIO_1");												
			salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							// TODO: qui dovrei ricaricare i tab degli attributi dinamici per vedere le eventuali variazioni
							reload();
						}
					});
				}
			});
		}
	}

	@Override
	public void salvaDatiDefinitivo() {
		
		if (validate()) {			
			if(showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()
				&& (!_FLG_SPESA_NO.equalsIgnoreCase(getValueAsString("flgSpesa")) || getValueAsBoolean("flgDeterminaAggiudicaProceduraGara"))) {
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
				if (isListaCIGEmpty) {
					SC.ask("CIG non valorizzato. Vuoi procedere comunque?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								continuaSalvaDatiDefinitivo();
							} else {
								messaggio = null;
								attrEsito = null;								
							}
						}
					});
				} else {
					continuaSalvaDatiDefinitivo();
				}
			} else {
				continuaSalvaDatiDefinitivo();
			}
		} else {
			messaggio = null;
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}

	private void continuaSalvaDatiDefinitivo() {
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
							SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk, valoriEsito,
								new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
											
										messaggio = object.getAttribute("messaggio");
										attrEsito = new Record(attr.toMap());
										attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
										saveAndGoAlert();
									}
								}
							);
							selezionaEsitoTaskWindow.show();
							break;
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			saveAndGoAlert();
		}
	}
	
	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}
	
	public void saveAndGoAlert() {

		if (validate()) {
			if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							saveAndGo();
						}
					}
				});
			} else {
				saveAndGo();
			}
		} else {
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	public Record getRecordModelloXEsito(String esito) {
		
		Record recordModello = null;		
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {			
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");					
				if (listaEsitiXGenModello == null || "".equals(listaEsitiXGenModello)) {
					recordModello = listaRecordModelli.get(i);
					break;						
				} 				
			}
			if(esito != null) {	
				for (int i = 0; i < listaRecordModelli.getLength(); i++) {
					String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
					if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
						for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
							if (esito.equalsIgnoreCase(esitoXGenModello)) {
								recordModello = listaRecordModelli.get(i);
								break;
							}
						} 
					}
				}			
			}
		}
		return recordModello;
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecord = super.getRecordToSave();
		lRecord.setAttribute("idProcess", getIdProcessTask());
		lRecord.setAttribute("idModCopertina", idModCopertina != null ? idModCopertina : "");
		lRecord.setAttribute("nomeModCopertina", nomeModCopertina != null ? nomeModCopertina : "");
		lRecord.setAttribute("idModCopertinaFinale", idModCopertinaFinale != null ? idModCopertinaFinale : "");
		lRecord.setAttribute("nomeModCopertinaFinale", nomeModCopertinaFinale != null ? nomeModCopertinaFinale : "");
		lRecord.setAttribute("idModAppendice", idModAppendice != null ? idModAppendice : "");
		lRecord.setAttribute("nomeModAppendice", nomeModAppendice != null ? nomeModAppendice : "");
		lRecord.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		lRecord.setAttribute("nomeModello", nomeModAssDocTask != null ? nomeModAssDocTask : "");
		lRecord.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		lRecord.setAttribute("idUoDirAdottanteSIB", idUoDirAdottanteSIB != null ? idUoDirAdottanteSIB : "");
		lRecord.setAttribute("codUoDirAdottanteSIB", codUoDirAdottanteSIB != null ? codUoDirAdottanteSIB : "");
		lRecord.setAttribute("desUoDirAdottanteSIB", desUoDirAdottanteSIB != null ? desUoDirAdottanteSIB : "");
		return lRecord;
	}
	
	public void saveAndGo() {
		final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		final Record recordModello = getRecordModelloXEsito(esito);		
		if(isEsitoTaskOk(attrEsito)) {	
			Map<String, String> otherExtraparam = new HashMap<String, String>();									
			otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_PROVVISORIO_2");												
			salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {
				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					Layout.hideWaitPopup();
					loadDettPropostaAtto(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record dett) {
							if(hasActionUnioneFile()) {
								// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
								// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto				
								unioneFileAndReturn();			
							} else if(hasActionFirma()) {
								getFileDaFirmare(new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record lRecord = response.getData()[0];
											Record[] files = lRecord.getAttributeAsRecordArray("files");
											if (files != null && files.length > 0) {
												firmaFile(files, new ServiceCallback<Record>() {
													
													@Override
													public void execute(final Record recordAfterFirma) {
														aggiornaFile(recordAfterFirma, new ServiceCallback<Record>() {
															
															@Override
															public void execute(Record object) {
																if (recordModello != null) {
																	salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {
																			saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record recordModello) {
																					Map<String, String> otherExtraparam = new HashMap<String, String>();									
																					otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_1");												
																					salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

																						@Override
																						public void execute(Record object) {								
																							if(hasActionPubblica()) {
																								pubblica();
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
																	Map<String, String> otherExtraparam = new HashMap<String, String>();									
																	otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_2");												
																	salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {								
																			if(hasActionPubblica()) {
																				pubblica();
																			} else {
																				callbackSalvaDati(object);
																			}
																		}
																	});		
																}																						
															}
														});	
													}
												});	
											} else {
												if (recordModello != null) {
													salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {
															saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

																@Override
																public void execute(Record recordModello) {
																	Map<String, String> otherExtraparam = new HashMap<String, String>();									
																	otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_3");												
																	salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {								
																			if(hasActionPubblica()) {
																				pubblica();
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
													Map<String, String> otherExtraparam = new HashMap<String, String>();									
													otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_4");												
													salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {								
															if(hasActionPubblica()) {
																pubblica();
															} else {
																callbackSalvaDati(object);
															}
														}
													});		
												}			
											}
										}
									}
								});				
							} else {
								if (recordModello != null) {
									salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {
											saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

												@Override
												public void execute(Record recordModello) {
													Map<String, String> otherExtraparam = new HashMap<String, String>();									
													otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_5");												
													salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

														@Override
														public void execute(Record object) {								
															if(hasActionPubblica()) {
																pubblica();
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
									Map<String, String> otherExtraparam = new HashMap<String, String>();									
									otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_6");												
									salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

										@Override
										public void execute(Record object) {								
											if(hasActionPubblica()) {
												pubblica();
											} else {
												callbackSalvaDati(object);
											}
										}
									});		
								}			
							}						
						}
					});					
				}
			});	
		} else {
			if (recordModello != null) {
				salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

							@Override
							public void execute(Record recordModello) {
								Map<String, String> otherExtraparam = new HashMap<String, String>();									
								otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_7");												
								salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {								
										callbackSalvaDati(object);
									}
								});	
							}
						});
					}
				});
			} else {
				Map<String, String> otherExtraparam = new HashMap<String, String>();									
				otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_8");												
				salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {								
						callbackSalvaDati(object);
					}
				});		
			}	
		}
	}
	
	public void getFileDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	public void getFileAllegatiDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.executecustom("getFileAllegatiDaFirmare", lRecord, callback);
	}
	
	protected void firmaFile(Record[] files, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
	
				@Override
				public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
					Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave());
					Record lRecordFileFirmati = new Record();
					lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					if(callback != null) {
						callback.execute(lRecord);
					}					
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("protocolloOriginale", getRecordToSave());
			Record lRecordFileFirmati = new Record();
			lRecordFileFirmati.setAttribute("files", files);
			lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
			if(callback != null) {
				callback.execute(lRecord);
			}
		}
	}
	
	protected void aggiornaFile(Record record, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			aggiornaFileFirmati(record,  callback);
		} else if(callback != null) {
			callback.execute(getRecordToSave());			
		}		
	}

	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lNuovaPropostaAtto2DataSource.executecustom("aggiornaFileFirmati", record, new DSCallback() {
		
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile()) {
						if (isReadOnly()) {
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
	
	protected void pubblica() {
		Record lRecordPubblica = getRecordToSave();
		// Nel caso di determina con spesa, se sono nel task di firma del visto contabile devo passare il file allegato generato da modello e firmato in quel task, per poterlo pubblicare come allegato
		if(_FLG_SPESA_SI.equalsIgnoreCase(getValueAsString("flgSpesa")) && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
//			if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
//				for (int i = 0; i < listaRecordModelli.getLength(); i++) {
//					final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
//					RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
//					int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
//					if (posModello != -1) {						
//						lRecordPubblica.setAttribute("allegatoVistoContabile", listaAllegati.get(posModello));
//					}
//				}
//			}
			if(allegatoGeneratoDaModelloTask != null) {
				lRecordPubblica.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
			}	
		}
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		Layout.showWaitPopup("Pubblicazione all'Albo Pretorio in corso...");				
		lNuovaPropostaAtto2DataSource.executecustom("pubblica", lRecordPubblica, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					callbackSalvaDati(lRecord);
				} else {
					// Se va in errore l'invio in pubblicazione ricarico il dettaglio del task: in questo modo 
					// la volta successiva viene abilitata solo la pubblicazione e non di nuovo l'unione e la firma
					reload();
				}												
			}
		});		
	}
	
	protected void pubblicaPost() {
		Record lRecordPubblica = getRecordToSave();
//		Date dataInizioPubblicazione = new Date();
//    	CalendarUtil.addDaysToDate(dataInizioPubblicazione, 1);
//    	lRecordPubblica.setAttribute("dataInizioPubblicazione", dataInizioPubblicazione);
//    	lRecordPubblica.setAttribute("giorniPubblicazione", "10");
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
		Layout.showWaitPopup("Pubblicazione all'Albo Pretorio in corso...");				
		lNuovaPropostaAtto2DataSource.executecustom("pubblicaPost", lRecordPubblica, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					AurigaLayout.addMessage(new MessageBean("Pubblicazione avvenuta con successo", "", MessageType.INFO));
				}												
			}
		});	
	}
	
	public void unioneFileAndReturn() {
		if (hasActionFirma()) {
			final Record lRecord = getRecordToSave();
			String numeroRegistrazione = lRecord.getAttribute("numeroRegistrazione");
			String siglaRegistroAtto = recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null;
			String messaggioFirma = "";
			// Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? DateUtil.parseInput(lRecord.getAttributeAsString("dataRegistrazione")) : null;
			Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? lRecord.getAttributeAsDate("dataRegistrazione") : null;
			if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto) && (numeroRegistrazione == null || "".equalsIgnoreCase(numeroRegistrazione))) {			
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneConRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneConRegistrazione();
				}		
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {
					SC.say(messaggioFirma, new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							unioneFileAndReturnAfterControlloNumerazione();
						}
					});
				} else {
					unioneFileAndReturnAfterControlloNumerazione();
				}
			} else if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto)) {
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneSenzaRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneSenzaRegistrazione();
				}
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {
					SC.say(messaggioFirma, new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							unioneFileAndReturnAfterControlloNumerazione();
						}
					});
				} else {
					unioneFileAndReturnAfterControlloNumerazione();
				}
			} else {
				unioneFileAndReturnAfterControlloNumerazione();
			}
		} else {
			unioneFileAndReturnAfterControlloNumerazione();
		}
	}
	
	private void unioneFileAndReturnAfterControlloNumerazione() {
		final Record lRecord = getRecordToSave();		
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");		
		lNuovaPropostaAtto2DataSource.addParam("siglaRegistroAtto", recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null);		
		lNuovaPropostaAtto2DataSource.addParam("nomeFileUnione", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null);
		lNuovaPropostaAtto2DataSource.addParam("nomeFileUnioneOmissis", recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null);
		lNuovaPropostaAtto2DataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
		Layout.showWaitPopup("Generazione del file unione in corso...");				
		lNuovaPropostaAtto2DataSource.executecustom("unioneFile", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					previewFileUnioneWithFirmaAndCallback(recordUnioneFile, new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordUnioneFileAfterFirma) {							
							final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
							final Record recordModello = getRecordModelloXEsito(esito);									
							if (recordModello != null) {
								salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {
										saveAndGoWithModello(recordModello, esito, new ServiceCallback<Record>() {

											@Override
											public void execute(Record recordModello) {
												Map<String, String> otherExtraparam = new HashMap<String, String>();									
												otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_9");												
												salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

													@Override
													public void execute(Record object) {								
														if(hasActionPubblica()) {
															pubblica();
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
								Map<String, String> otherExtraparam = new HashMap<String, String>();									
								otherExtraparam.put("faseSalvataggio", "SALVATAGGIO_DEFINITIVO_10");												
								salvaDati(false, otherExtraparam, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {								
										if(hasActionPubblica()) {
											pubblica();
										} else {
											callbackSalvaDati(object);
										}
									}
								});		
							}									
						}
					});
				}
			}
		});
	}
	
	public void previewFileUnioneWithFirmaAndCallback(final Record record, final ServiceCallback<Record> callback) {			
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
		new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean", nomeFileUnione, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {		
				if(lFileUnioneOmissis != null) {
					new PreviewWindowWithCallback(uriFileUnioneOmissis, false, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordPreview) {		
							afterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, callback);							
						}
					}) {
						
						@Override
						public boolean hideAnnullaButton() {
							return true;
						}
					};		
				} else {
					afterPreviewFileUnioneWithFirma(lFileUnione, null, callback);
				}
			}
		}) {
			
			@Override
			public boolean hideAnnullaButton() {
				return true;
			}
		};		
	}
	
	public void afterPreviewFileUnioneWithFirma(Record lFileUnione, Record lFileUnioneOmissis, final ServiceCallback<Record> callback) {
		if (hasActionFirma()) {	
			final List<Record> listaFiles = new ArrayList<Record>();
			if(lFileUnione != null) {
				listaFiles.add(lFileUnione);
			}
			if(lFileUnioneOmissis != null) {
				listaFiles.add(lFileUnioneOmissis);
			}	
			getFileAllegatiDaFirmare(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordFileAllegati = response.getData()[0];
						Record[] filesAllegati = lRecordFileAllegati.getAttributeAsRecordArray("files");
						for(int i = 0; i < filesAllegati.length; i++) {
							listaFiles.add(filesAllegati[i]);	
						}
						// Leggo gli eventuali parametri per forzare il tipo d firma
						String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
						String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
						Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
						FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
							@Override
							public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
								Record lRecord = new Record();
								lRecord.setAttribute("protocolloOriginale", getRecordToSave());
								Record lRecordFileFirmati = new Record();
								lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
								lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
								aggiornaFileFirmati(lRecord,  callback);						
							}
						});
					}
				}
			});	
		} else {
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
	}

	protected void callbackSalvaDati(Record object) {
		
		idEvento = object.getAttribute("idEvento");
		
		Record lRecord = new Record();
		lRecord.setAttribute("instanceId", instanceId);
		lRecord.setAttribute("activityName", activityName);
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEventType", idTipoEvento);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("note", messaggio);		
		lRecord.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
		lRecord.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
		lRecord.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
		lRecord.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
		lRecord.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
		lRecord.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
		lRecord.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
		lRecord.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
		lRecord.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
		if(hasActionInvioNotEmail() && isEsitoTaskAzioni(attrEsitoNotEmail)) {			
			lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
			attrEsitoNotEmail = null;
		}		
		lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {

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

	public void saveAndGoWithModello(Record recordModello, String esito, ServiceCallback<Record> callback) {
		compilazioneAutomaticaModelloPdf(recordModello, esito, callback);
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
	public void compilazioneAutomaticaModelloPdf(final Record recordModello, String esito, final ServiceCallback<Record> callback) {

		if (recordModello != null) {

			final String descrizione = recordModello.getAttribute("descrizione");
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + ".pdf";
			final String idTipoModello = recordModello.getAttribute("idTipoDoc");
			final String nomeTipoModello = recordModello.getAttribute("nomeTipoDoc");
			final boolean flgDaFirmare = recordModello.getAttributeAsBoolean("flgDaFirmare") != null && recordModello.getAttributeAsBoolean("flgDaFirmare");
			final boolean flgParteDispositivo = recordModello.getAttributeAsBoolean("flgParteDispositivo") != null && recordModello.getAttributeAsBoolean("flgParteDispositivo");
			
			Record lRecordCompilaModello = new Record();
			lRecordCompilaModello.setAttribute("processId", idProcess);
			lRecordCompilaModello.setAttribute("idUd", idUd);
			lRecordCompilaModello.setAttribute("idModello", recordModello.getAttribute("idModello"));
			lRecordCompilaModello.setAttribute("nomeModello", recordModello.getAttribute("nomeModello"));
			lRecordCompilaModello.setAttribute("uriModello", recordModello.getAttribute("uri"));
			lRecordCompilaModello.setAttribute("tipoModello", recordModello.getAttribute("tipoModello"));
			lRecordCompilaModello.setAttribute("nomeFile", nomeFileModello);
			lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
			
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
			lNuovaPropostaAtto2DataSource.addParam("esitoTask", esito);
			lNuovaPropostaAtto2DataSource.executecustom("compilazioneAutomaticaModelloPdf", lRecordCompilaModello, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record result = response.getData()[0];
						final String documentUri = result.getAttribute("uri");
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));						
						boolean flgSkipAnteprima = recordModello.getAttributeAsBoolean("flgSkipAnteprima") != null && recordModello.getAttributeAsBoolean("flgSkipAnteprima");						
						if(!flgSkipAnteprima) {
							new PreviewWindowWithCallback(documentUri, false, infoFile, "FileToExtractBean",	nomeFileModello, new ServiceCallback<Record>() {
	
								@Override
								public void execute(Record object) {									
									if (flgDaFirmare) {
										// Leggo gli eventuali parametri per forzare il tipo d firma
										String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
										String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
										FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFileModello, infoFile, new FirmaCallback() {
	
											@Override
											public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
												aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, uriFileFirmato, nomeFileFirmato, info, callback);
											}
										});
									} else {
										aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, documentUri, nomeFileModello, infoFile, callback);
									}
								}
							});
						} else {
							if (flgDaFirmare) {
								// Leggo gli eventuali parametri per forzare il tipo d firma
								String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
								String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
								FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFileModello, infoFile, new FirmaCallback() {

									@Override
									public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
										aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, uriFileFirmato, nomeFileFirmato, info,
												callback);
									}
								});
							} else {
								aggiungiModelloAdAllegati(flgParteDispositivo, descrizione, idTipoModello, nomeTipoModello, documentUri, nomeFileModello, infoFile, callback);
							}
						}
					}
				}
			});
		}
	}
	
	protected void aggiungiModelloAdAllegati(boolean flgParteDispositivo, String descrizioneFileAllegato, String idTipoModello, String nomeTipoModello, String uriFileAllegato,
			String nomeFileAllegato, InfoFileRecord infoAllegato, ServiceCallback<Record> callback) {

		if (allegatiForm != null) {
			
			RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");

			int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
			
			Record lRecordModello = new Record();		
			if (posModello != -1) {
				lRecordModello = listaAllegati.get(posModello);
			}
			
			lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
			if(!flgParteDispositivo) {
				lRecordModello.setAttribute("flgNoPubblAllegato", true);
				lRecordModello.setAttribute("flgPubblicaSeparato", false);
				lRecordModello.setAttribute("flgDatiSensibili", false);
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
			
			if (posModello == -1) {
				if (listaAllegati == null || listaAllegati.getLength() == 0) {
					listaAllegati = new RecordList();
				}
				listaAllegati.addAt(lRecordModello, 0);
			} else {
				listaAllegati.set(posModello, lRecordModello);
			}

			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaAllegati", listaAllegati);
			allegatiForm.setValues(lRecordForm.toMap());
			
			allegatoGeneratoDaModelloTask = lRecordModello;

			if(listaAllegatiItem != null) {
				listaAllegatiItem.resetCanvasChanged();
			}

			if (detailSectionAllegati != null) {
				detailSectionAllegati.openIfhasValue();
			}
		}
		if (callback != null) {
			callback.execute(new Record());
		}
	}
	
	public int getPosModelloFromTipo(String idTipoModello, RecordList listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttribute("listaTipiFileAllegato") != null && allegato.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
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

	public void saveAndReloadTask() {
		salvaDatiProvvisorio();
	}

}
