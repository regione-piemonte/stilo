/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaMovimentiContabilia2Item extends GridItem {
	
	protected ListGridField id;
	protected ListGridField flgEntrataUscita;
	protected ListGridField tipoMovimento;
	protected ListGridField annoMovimento;
	protected ListGridField numeroMovimento;
	protected ListGridField descrizioneMovimento;
	protected ListGridField annoSub;
	protected ListGridField numeroSub;
	protected ListGridField descrizioneSub;	
	protected ListGridField annoModifica;	
	protected ListGridField numeroModifica;	
	protected ListGridField descrizioneModifica;	
	protected ListGridField importoModifica;
	protected ListGridField importoIniziale;
	protected ListGridField importo;		
	protected ListGridField numeroCapitolo;
	protected ListGridField numeroArticolo;
	protected ListGridField numeroUEB;
	protected ListGridField descrizioneCapitolo;
	protected ListGridField descrizioneArticolo;
	protected ListGridField codiceCIG;
	protected ListGridField motivoAssenzaCIG;
	protected ListGridField codiceCUP;
	protected ListGridField codiceSoggetto;	
	protected ListGridField descrizioneSoggetto;
	protected ListGridField codiceClasseSoggetto;
	protected ListGridField descrizioneClasseSoggetto;	
	protected ListGridField codicePdC;
	protected ListGridField descrizionePdC;
	protected ListGridField codiceCategoria;
	protected ListGridField descrizioneCategoria;
	protected ListGridField codiceCodUE;
	protected ListGridField descrizioneCodUE;
	protected ListGridField codiceCofog;
	protected ListGridField descrizioneCofog;
	protected ListGridField codiceGsa;
	protected ListGridField descrizioneGsa;
	protected ListGridField datiGsa;
	protected ListGridField codiceMacroaggregato;
	protected ListGridField descrizioneMacroaggregato;
	protected ListGridField codiceMissione;
	protected ListGridField descrizioneMissione;
	protected ListGridField codiceNaturaRicorrente;
	protected ListGridField descrizioneNaturaRicorrente;
	protected ListGridField prenotazione;
	protected ListGridField prenotazioneLiquidabile;
	protected ListGridField codiceProgetto;
	protected ListGridField descrizioneProgetto;						
	protected ListGridField codiceProgramma;
	protected ListGridField descrizioneProgramma;
	protected ListGridField codiceTipoDebitoSiope;
	protected ListGridField descrizioneTipoDebitoSiope;	
	protected ListGridField codiceTipoFinanziamento;
	protected ListGridField descrizioneTipoFinanziamento;
	protected ListGridField codiceTipologia;
	protected ListGridField descrizioneTipologia;
	protected ListGridField codiceTitolo;
	protected ListGridField descrizioneTitolo;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	public ListaMovimentiContabilia2Item(String name) {
		
		super(name, "listaMovimentiContabilia2");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(false);
		setShowModifyButton(true);
		setShowDeleteButton(false);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);
		  
		flgEntrataUscita = new ListGridField("flgEntrataUscita");
		flgEntrataUscita.setHidden(true);
		flgEntrataUscita.setCanHide(false);
		
		tipoMovimento = new ListGridField("tipoMovimento", "Tipo movim.");
		
		annoMovimento = new ListGridField("annoMovimento", "Anno imp./acc.");
		annoMovimento.setType(ListGridFieldType.INTEGER);
		
		numeroMovimento = new ListGridField("numeroMovimento", "N° imp./acc.");
		numeroMovimento.setType(ListGridFieldType.INTEGER);
		
		descrizioneMovimento = new ListGridField("descrizioneMovimento", "Descrizione imp./acc.");
		
		annoSub = new ListGridField("annoSub", "Anno sub");
		annoSub.setType(ListGridFieldType.INTEGER);
		
		numeroSub = new ListGridField("numeroSub", "N° sub");
		numeroSub.setType(ListGridFieldType.INTEGER);
		
		descrizioneSub = new ListGridField("descrizioneSub", "Descrizione sub");
		
		annoModifica = new ListGridField("annoModifica", "Anno modifica");
		annoModifica.setType(ListGridFieldType.INTEGER);
		
		numeroModifica = new ListGridField("numeroModifica", "N° modifica");
		numeroModifica.setType(ListGridFieldType.INTEGER);
		
		descrizioneModifica = new ListGridField("descrizioneModifica", "Descr. modifica");
		
		importoModifica = new ListGridField("importoModifica", "Importo aumento/riduzione imp./acc. (&euro;)");
		importoModifica.setType(ListGridFieldType.FLOAT);
		importoModifica.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importoModifica"));				
			}
		});
			
		importoIniziale = new ListGridField("importoIniziale", "Importo iniziale (&euro;)");
		importoIniziale.setType(ListGridFieldType.FLOAT);
		importoIniziale.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importoIniziale"));				
			}
		});
		
		importo = new ListGridField("importo", "Importo attuale (&euro;)");
		importo.setType(ListGridFieldType.FLOAT);
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		
		numeroCapitolo = new ListGridField("numeroCapitolo", "Cap.");
		numeroCapitolo.setType(ListGridFieldType.INTEGER);
		
		numeroArticolo = new ListGridField("numeroArticolo", "Art.");
		numeroArticolo.setType(ListGridFieldType.INTEGER);
		
		numeroUEB = new ListGridField("numeroUEB", "UEB");
		numeroUEB.setType(ListGridFieldType.INTEGER);
		
		descrizioneCapitolo = new ListGridField("descrizioneCapitolo", "Des. Cap.");
		
		descrizioneArticolo = new ListGridField("descrizioneArticolo", "Des. Art.");
		
		codiceCIG = new ListGridField("codiceCIG", "CIG");
		
		motivoAssenzaCIG = new ListGridField("motivoAssenzaCIG", "Motivo assenza CIG");
		
		codiceCUP = new ListGridField("codiceCUP", "CUP");
		
		codiceSoggetto = new ListGridField("codiceSoggetto", "Cod. soggetto");
		codiceSoggetto.setHidden(true);
		codiceSoggetto.setCanHide(false);
		
		descrizioneSoggetto = new ListGridField("descrizioneSoggetto", "Soggetto");
		descrizioneSoggetto.setAttribute("custom", true);
		descrizioneSoggetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceSoggetto", "descrizioneSoggetto");				
			}
		});		
		
		codiceClasseSoggetto = new ListGridField("codiceClasseSoggetto", "Cod. classe soggetto");
		codiceClasseSoggetto.setHidden(true);
		codiceClasseSoggetto.setCanHide(false);
		
		descrizioneClasseSoggetto = new ListGridField("descrizioneClasseSoggetto", "Classe soggetto");
		descrizioneClasseSoggetto.setAttribute("custom", true);
		descrizioneClasseSoggetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceClasseSoggetto", "descrizioneClasseSoggetto");				
			}
		});		
		
		codicePdC = new ListGridField("codicePdC", "Cod. PdC finanz.");
		codicePdC.setHidden(true);
		codicePdC.setCanHide(false);
		
		descrizionePdC = new ListGridField("descrizionePdC", "PdC finanz.");
		descrizionePdC.setAttribute("custom", true);
		descrizionePdC.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codicePdC", "descrizionePdC");				
			}
		});		
		
		codiceCategoria = new ListGridField("codiceCategoria", "Cod. categoria");
		codiceCategoria.setHidden(true);
		codiceCategoria.setCanHide(false);
		
		descrizioneCategoria = new ListGridField("descrizioneCategoria", "Categoria");
		descrizioneCategoria.setAttribute("custom", true);
		descrizioneCategoria.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCategoria", "descrizioneCategoria");
			}
		});		
		
		codiceCodUE = new ListGridField("codiceCodUE", "Cod. trans. UE");
		codiceCodUE.setHidden(true);
		codiceCodUE.setCanHide(false);
		
		descrizioneCodUE = new ListGridField("descrizioneCodUE", "Trans. UE");
		descrizioneCodUE.setAttribute("custom", true);
		descrizioneCodUE.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCodUE", "descrizioneCodUE");
			}
		});		
		
		codiceCofog = new ListGridField("codiceCofog", "Cod. Cofog");
		codiceCofog.setHidden(true);
		codiceCofog.setCanHide(false);
		
		descrizioneCofog = new ListGridField("descrizioneCofog", "Cofog");
		descrizioneCofog.setAttribute("custom", true);
		descrizioneCofog.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceCofog", "descrizioneCofog");
			}
		});		
		
		codiceGsa = new ListGridField("codiceGsa", "Cod. perim. sanitario");
		codiceGsa.setHidden(true);
		codiceGsa.setCanHide(false);
		
		descrizioneGsa = new ListGridField("descrizioneGsa", "Perim. sanitario");
		descrizioneGsa.setAttribute("custom", true);
		descrizioneGsa.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceGsa", "descrizioneGsa");
			}
		});
		
		datiGsa = new ListGridField("datiGsa", "Presenti dati GSA");
		datiGsa.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		datiGsa.setType(ListGridFieldType.ICON);
		datiGsa.setWidth(30);
		datiGsa.setIconWidth(16);
		datiGsa.setIconHeight(16);
		datiGsa.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String datiGsa = record.getAttribute("datiGsa") != null ? record.getAttribute("datiGsa") : "";
				if (isDatiRilevantiGsa() && datiGsa != null && !"".equals(datiGsa)) {
					return buildIconHtml("attiInLavorazione/GSA.png");
				}
				return null;
			}
		});
		datiGsa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String datiGsa = record.getAttribute("datiGsa") != null ? record.getAttribute("datiGsa") : "";
				if (isDatiRilevantiGsa() && datiGsa != null && !"".equals(datiGsa)) {
					return "Presenti dati GSA";
				}
				return null;
			}
		});
		
		codiceMacroaggregato = new ListGridField("codiceMacroaggregato", "Cod. macroaggregato");
		codiceMacroaggregato.setHidden(true);
		codiceMacroaggregato.setCanHide(false);
		
		descrizioneMacroaggregato = new ListGridField("descrizioneMacroaggregato", "Macroaggregato");
		descrizioneMacroaggregato.setAttribute("custom", true);
		descrizioneMacroaggregato.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceMacroaggregato", "descrizioneMacroaggregato");
			}
		});	
		
		codiceMissione = new ListGridField("codiceMissione", "Cod. missione");
		codiceMissione.setHidden(true);
		codiceMissione.setCanHide(false);
		
		descrizioneMissione = new ListGridField("descrizioneMissione", "Missione");
		descrizioneMissione.setAttribute("custom", true);
		descrizioneMissione.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceMissione", "descrizioneMissione");
			}
		});		
		
		codiceNaturaRicorrente = new ListGridField("codiceNaturaRicorrente", "Cod. natura ricorrente");
		codiceNaturaRicorrente.setHidden(true);
		codiceNaturaRicorrente.setCanHide(false);
		
		descrizioneNaturaRicorrente = new ListGridField("descrizioneNaturaRicorrente", "Natura ricorrente");
		descrizioneNaturaRicorrente.setAttribute("custom", true);
		descrizioneNaturaRicorrente.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceNaturaRicorrente", "descrizioneNaturaRicorrente");
			}
		});
		
		prenotazione = new ListGridField("prenotazione", "Prenotazione");
		
		prenotazioneLiquidabile = new ListGridField("prenotazioneLiquidabile", "Prenot. liquidabile");
		
		codiceProgetto = new ListGridField("codiceProgetto", "Cod. progetto/iniziativa");
		codiceProgetto.setHidden(true);
		codiceProgetto.setCanHide(false);
		
		descrizioneProgetto = new ListGridField("descrizioneProgetto", "Progetto/iniziativa");
		descrizioneProgetto.setAttribute("custom", true);
		descrizioneProgetto.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceProgetto", "descrizioneProgetto");				
			}
		});	
		
		codiceProgramma = new ListGridField("codiceProgramma", "Cod. programma");
		codiceProgramma.setHidden(true);
		codiceProgramma.setCanHide(false);
		
		descrizioneProgramma = new ListGridField("descrizioneProgramma", "Programma");
		descrizioneProgramma.setAttribute("custom", true);
		descrizioneProgramma.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceProgramma", "descrizioneProgramma");
			}
		});	
		
		codiceTipoDebitoSiope = new ListGridField("codiceTipoDebitoSiope", "Cod. debito Siope");
		codiceTipoDebitoSiope.setHidden(true);
		codiceTipoDebitoSiope.setCanHide(false);
		
		descrizioneTipoDebitoSiope = new ListGridField("descrizioneTipoDebitoSiope", "Debito Siope");
		descrizioneTipoDebitoSiope.setAttribute("custom", true);
		descrizioneTipoDebitoSiope.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipoDebitoSiope", "descrizioneTipoDebitoSiope");				
			}
		});	
		
		codiceTipoFinanziamento = new ListGridField("codiceTipoFinanziamento", "Cod. tipo finanz.");
		codiceTipoFinanziamento.setHidden(true);
		codiceTipoFinanziamento.setCanHide(false);
		
		descrizioneTipoFinanziamento = new ListGridField("descrizioneTipoFinanziamento", "Tipo finanz.");
		descrizioneTipoFinanziamento.setAttribute("custom", true);
		descrizioneTipoFinanziamento.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipoFinanziamento", "descrizioneTipoFinanziamento");
			}
		});		
		
		codiceTipologia = new ListGridField("codiceTipologia", "Cod. tipologia");
		codiceTipologia.setHidden(true);
		codiceTipologia.setCanHide(false);
		
		descrizioneTipologia = new ListGridField("descrizioneTipologia", "Tipologia");
		descrizioneTipologia.setAttribute("custom", true);
		descrizioneTipologia.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTipologia", "descrizioneTipologia");
			}
		});		
		
		codiceTitolo = new ListGridField("codiceTitolo", "Cod. titolo");
		codiceTitolo.setHidden(true);
		codiceTitolo.setCanHide(false);
		
		descrizioneTitolo = new ListGridField("descrizioneTitolo", "Titolo");
		descrizioneTitolo.setAttribute("custom", true);
		descrizioneTitolo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codiceTitolo", "descrizioneTitolo");
			}
		});		
		
		setGridFields(
			id,
			flgEntrataUscita,
			tipoMovimento,
			annoMovimento,
			numeroMovimento,
			descrizioneMovimento,
			annoSub,
			numeroSub,
			descrizioneSub,			
			annoModifica,
			numeroModifica,
			descrizioneModifica,
			importoModifica,
			importoIniziale,
			importo,
			numeroCapitolo,
			numeroArticolo,
			numeroUEB,
			descrizioneCapitolo,
			descrizioneArticolo,
			codiceCIG,
			motivoAssenzaCIG, 
			codiceCUP,
			codiceSoggetto,
			descrizioneSoggetto,
			codiceClasseSoggetto,
			descrizioneClasseSoggetto,
			codicePdC,
			descrizionePdC,
			codiceCategoria,
			descrizioneCategoria,
			codiceCodUE,
			descrizioneCodUE,
			codiceCofog,
			descrizioneCofog,
			codiceGsa,
			descrizioneGsa,
			datiGsa,
			codiceMacroaggregato,
			descrizioneMacroaggregato,
			codiceMissione,
			descrizioneMissione,
			codiceNaturaRicorrente,
			descrizioneNaturaRicorrente,
			prenotazione,
			prenotazioneLiquidabile,
			codiceProgetto, 
			descrizioneProgetto,						
			codiceProgramma,
			descrizioneProgramma,
			codiceTipoDebitoSiope, 
			descrizioneTipoDebitoSiope,	
			codiceTipoFinanziamento,
			descrizioneTipoFinanziamento,
			codiceTipologia,
			descrizioneTipologia,
			codiceTitolo,
			descrizioneTitolo
		);				
	}
	
	public String getDescrizioneWithCodice(Record record, String codiceFieldName, String descrizioneFieldName) {
		String codice = record.getAttribute(codiceFieldName);
		String descrizione = record.getAttribute(descrizioneFieldName);
		if(codice != null && !"".equals(codice) && descrizione != null && !"".equals(descrizione)) {
			return codice + " - " + descrizione;
		} else if(descrizione!= null && !"".equals(descrizione)) {
			return descrizione;													
		} else if(codice != null && !"".equals(codice)) {
			return codice;
		}	
		return null;
	}
	
	@Override
	public void init(FormItem item) {
		
		super.init(item);
		
		totaliToolStrip = new ToolStrip();
		totaliToolStrip.setBackgroundColor("transparent");
		totaliToolStrip.setBackgroundImage("blank.png");
		totaliToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		totaliToolStrip.setBorder("0px");
		totaliToolStrip.setWidth100();           
		totaliToolStrip.setHeight(30);
		
		totaleEntrateLayout = new HLayout();
		totaleEntrateLayout.setOverflow(Overflow.VISIBLE);
		totaleEntrateLayout.setWidth(5);
	
		totaleEntrateLabel = new Label();
		totaleEntrateLabel.setAlign(Alignment.CENTER);
		totaleEntrateLabel.setValign(VerticalAlignment.CENTER);
		totaleEntrateLabel.setWrap(false);
		
		totaleEntrateLayout.setMembers(totaleEntrateLabel);
		
		totaleUsciteLayout = new HLayout();
		totaleUsciteLayout.setOverflow(Overflow.VISIBLE);
		totaleUsciteLayout.setWidth(5);
	
		totaleUsciteLabel = new Label();
		totaleUsciteLabel.setAlign(Alignment.CENTER);
		totaleUsciteLabel.setValign(VerticalAlignment.CENTER);
		totaleUsciteLabel.setWrap(false);
		
		totaleUsciteLayout.setMembers(totaleUsciteLabel);
		
		totaliToolStrip.addMember(totaleEntrateLayout);
		totaliToolStrip.addFill();
		totaliToolStrip.addMember(totaleUsciteLayout);
		
		layout.addMember(totaliToolStrip);			
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("flgEntrataUscita", SortDirection.DESCENDING));
		grid.addSort(new SortSpecifier("annoMovimento", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroMovimento", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoSub", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroSub", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoModifica", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroModifica", SortDirection.ASCENDING));			
		grid.addSort(new SortSpecifier("descrizioneMovimento", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		aggiornaTotali();
	}
	
	public void aggiornaTotali() {
		/*
		if(grid.getRecords().length > 0) {			
			String pattern = "#,##0.00";
			float totaleEntrate = 0;
			float totaleUscite = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				String tipoMovimento = record.getAttribute("tipoMovimento") != null ? record.getAttribute("tipoMovimento") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				//TODO correggere il calcolo dei totali considerando gli importi solo degli impegni/accertamenti padre, e gli importi dei sub e delle modifiche solo se presenti singolarmente (senza padre)
				if("E".equals(flgEntrataUscita) && "Accertamento".equalsIgnoreCase(tipoMovimento)) {
					totaleEntrate += importo;
				} else if("U".equals(flgEntrataUscita) && "Impegno".equalsIgnoreCase(tipoMovimento)) {
					totaleUscite += importo;
				}
			}
			totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro</b></span>");
			totaleUsciteLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totaleUscite) + " euro</b></span>");
			totaliToolStrip.show();
		} else {		
			totaleEntrateLabel.setContents("");
			totaleUsciteLabel.setContents("");
			totaliToolStrip.hide();
		}
		*/
		totaleEntrateLabel.setContents("");
		totaleUsciteLabel.setContents("");
		totaliToolStrip.hide();
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();				
		ToolStripButton refreshListButton = new ToolStripButton();   
		refreshListButton.setIcon("buttons/refreshList.png");   
		refreshListButton.setIconSize(16);
		refreshListButton.setPrefix("refreshListButton");
		refreshListButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickRefreshListButton();   	
			}   
		});  
		if (isShowRefreshListButton()) {
			buttons.add(refreshListButton);
		}		
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickExportXlsButton();   	
			}   
		});  
		if (isShowExportXlsButton()) {
			buttons.add(exportXlsButton);
		}
		return buttons;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {	
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {					
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();
					}
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("refreshListButton"))
					{
						if (isShowRefreshListButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}	
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton"))
					{
						if (isShowExportXlsButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}
				}
			}	
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}	
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();			
		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		buttonsList.add(detailButtonField);
		if(isShowModifyButton()) {
			if(modifyButtonField == null) {
				modifyButtonField = buildModifyButtonField();					
			}
			buttonsList.add(modifyButtonField);
		}
		return buttonsList;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("buttons/detail.png");
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return I18NUtil.getMessages().detailButton_prompt();				
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				onClickDetailButton(event.getRecord());				
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DettaglioMovimentiContabilia2Window lDettaglioMovimentiContabilia2Window = new DettaglioMovimentiContabilia2Window(this, "dettaglioMovimentiContabilia2Window", false, record);
		lDettaglioMovimentiContabilia2Window.show();
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public boolean isShowModifyButton(Record record) {
		String codiceGsa = record.getAttribute("codiceGsa");
		if(isDatiRilevantiGsa() && isEditableDatiContabiliaDettGsa() && codiceGsa != null && !"".equals(codiceGsa)) {
			String codGSAEditabili = AurigaLayout.getParametroDB("LISTA_COD_GSA_EDITABILI");
			if(codGSAEditabili != null && !"".equals(codGSAEditabili)) {
				StringSplitterClient st = new StringSplitterClient(codGSAEditabili, ";");
				Set<String> setCodGSAEditabili = new HashSet<String>();
				for (String token : st.getTokens()) {
					setCodGSAEditabili.add(token);
				}
				// solo se codiceGsa in LISTA_COD_GSA_EDITABILI (valori separati da ;)
				return setCodGSAEditabili.contains(codiceGsa);
			} else {
				return true;
			}
		}
		return false;
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
		final DettaglioMovimentiContabilia2Window lDettaglioMovimentiContabilia2Window = new DettaglioMovimentiContabilia2Window(this, "dettaglioMovimentiContabilia2Window", true, record) {
			
			@Override
			public void saveData(final Record updatedRecord) {
				updateData(updatedRecord, record);	
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		    		
					@Override
					public void execute() {
						grid.selectSingleRecord(updatedRecord);
					}
		    	});		
			}		
		};
		lDettaglioMovimentiContabilia2Window.show();
	}
	
	@Override
	protected void manageOnShowValue(RecordList data) {
		// ATTENZIONE:
		// ogni volta che setto un valore sul gridItem finisco qui dentro, sia facendo il setValue() sull'item sia settando il valore nel form o nel vm che lo contiene, quindi non solo quando carico i valori dalla loadDettaglio
		// anche quando fa lo show per la prima volta del gridItem (tipo selezionando il tab che lo contiene) entra qui dentro 
		if (data != null) {
			for (int i = 0; i < data.getLength(); i++) {
				Record lRecord = data.get(i);
				if(lRecord.getAttribute("id") == null) {
					lRecord.setAttribute("id", i);					
				}
				lRecord.setAttribute("valuesOrig", lRecord.toMap());
			}
		}
		setData(data);
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaMovimentiContabilia2", fields);
	}
	
	public boolean isShowDatiStoriciButton() {
		return false;
	}
	
	public void onClickDatiStoriciButton() {
		
	}
	
	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	public void onClickExportXlsButton() {		

		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for (String key : mappa.keySet()) {
			fields[n] = key;
			headers[n] = mappa.get(key);
			n++;
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaMovimentiContabilia2");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					String filename = "Results." + record.getAttribute("formatoExport");
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					// vuol dire che è abilitato per questo datasource l'esportazione asincrona e quindi la generazione è stata schedulata
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(url));
					}
				}
				/*
				 * else { Layout.addMessage(new MessageBean("Si è verificato un errore durante l'esportazione della lista", "", MessageType.ERROR)); }
				 */
			}
		}, new DSRequest());
	}	
	
	protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();

		for (int i = 0; i < getGrid().getFields().length; i++) {

			ListGridField field = getGrid().getFields()[i];
			String fieldName = field.getName();

			if (fieldName.endsWith("XOrd") && includeXord) {

				String fieldTitle = field.getTitle() + " (Ordinamento)";

				if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField)  && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
					mappa.put(fieldName, fieldTitle);
				}
			}

			if (fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();

			/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
			if (field.getDisplayField() != null)
				fieldName = field.getDisplayField();

			if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
				mappa.put(fieldName, fieldTitle);
			}
		}
		return mappa;
	}
	
	protected Record[] extractRecords(String[] fields) {
		Record[] records = new Record[getGrid().getRecords().length];
		for (int i = 0; i < getGrid().getRecords().length; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
			}
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			rec.setAttribute("descrizioneSoggetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceSoggetto", "descrizioneSoggetto"));
			rec.setAttribute("descrizioneClasseSoggetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceClasseSoggetto", "descrizioneClasseSoggetto"));
			rec.setAttribute("descrizionePdC", getDescrizioneWithCodice(getGrid().getRecords()[i], "codicePdC", "descrizionePdC"));
			rec.setAttribute("descrizioneCategoria", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCategoria", "descrizioneCategoria"));
			rec.setAttribute("descrizioneCodUE", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCodUE", "descrizioneCodUE"));
			rec.setAttribute("descrizioneCofog", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceCofog", "descrizioneCofog"));
			rec.setAttribute("descrizioneGsa", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceGsa", "descrizioneGsa"));
			rec.setAttribute("descrizioneMacroaggregato", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceMacroaggregato", "descrizioneMacroaggregato"));
			rec.setAttribute("descrizioneMissione", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceMissione", "descrizioneMissione"));
			rec.setAttribute("descrizioneNaturaRicorrente", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceNaturaRicorrente", "descrizioneNaturaRicorrente"));
			rec.setAttribute("descrizioneProgetto", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceProgetto", "descrizioneProgetto"));
			rec.setAttribute("descrizioneProgramma", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceProgramma", "descrizioneProgramma"));
			rec.setAttribute("descrizioneTipoDebitoSiope", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipoDebitoSiope", "descrizioneTipoDebitoSiope"));
			rec.setAttribute("descrizioneTipoFinanziamento", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipoFinanziamento", "descrizioneTipoFinanziamento"));
			rec.setAttribute("descrizioneTipologia", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTipologia", "descrizioneTipologia"));
			rec.setAttribute("descrizioneTitolo", getDescrizioneWithCodice(getGrid().getRecords()[i], "codiceTitolo", "descrizioneTitolo"));			
			records[i] = rec;
		}
		return records;
	}
	
	protected boolean showDatiContabiliaDettGsa(){
		return false;
	}
	
	protected String getTitleDatiContabiliaDettGsa(){
		return null; 
	}
	
	protected boolean isEditableDatiContabiliaDettGsa(){
		return false;
	}
	
	protected boolean isDatiRilevantiGsa(){
		return false;
	}

	public abstract boolean isGrigliaEditabile();
	
}
