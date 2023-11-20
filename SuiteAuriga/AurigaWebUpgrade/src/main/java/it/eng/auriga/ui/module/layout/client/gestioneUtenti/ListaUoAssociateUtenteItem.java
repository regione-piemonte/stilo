/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public abstract class ListaUoAssociateUtenteItem extends GridItem {	
	
	// hidden 
	protected ListGridField rowIdField;
	protected ListGridField flgModificatoField;
	protected ListGridField idUoField;
	protected ListGridField idRuoloField;
	protected ListGridField tipoRelazioneField;
	protected ListGridField denominazioneScrivaniaUtenteField;
	protected ListGridField descrizioneEstesaField;
	protected ListGridField typeNodoField;
	
	protected ListGridField flgUoPuntoProtocolloField;
	protected ListGridField listaUOPuntoProtocolloEscluseField;
	protected ListGridField listaUOPuntoProtocolloIncluseField;
	protected ListGridField listaUOPuntoProtocolloEreditarietaAbilitataField;
	protected ListGridField flgPresentiDocFascField;
	protected ListGridField flgTipoDestDocField;
	protected ListGridField idUODestDocfascField;
	protected ListGridField livelliUODestDocFascField;
	protected ListGridField desUODestDocFascField;
	protected ListGridField statoSpostamentoDocFascField;
	protected ListGridField dataInizioSpostamentoDocFascField;	
	protected ListGridField dataFineSpostamentoDocFascField;
	protected ListGridField nrDocAssegnatiField;
	protected ListGridField dataConteggioDocAssegnatiField; 
	protected ListGridField nrFascAssegnatiField;
	protected ListGridField dataConteggioFascAssegnatiField;
	protected ListGridField flgGestAttiTuttiItem;
	protected ListGridField listaTipiGestAttiSelezionatiItem;
	protected ListGridField flgVisPropAttiInIterTuttiItem;
	protected ListGridField listaTipiVisPropAttiInIterSelezionatiItem;
	
	// visibili
	private ListGridField codRapidoField;
	private ListGridField denominazioneUoField;
	private ListGridField descTipoRelazioneField;
	private ListGridField descrizioneRuoloField;
	private ListGridField dtInizioVldField;
	private ListGridField dtFineVldField;
	private ListGridField flgIncluseSottoUoField;
	private ListGridField flgIncluseScrivanieField;
	private ListGridField flgAccessoDocLimSVField;
	private ListGridField flgRegistrazioneEField;
	private ListGridField flgRegistrazioneUIField;
	private ListGridField flgGestAttiField;
	private ListGridField flgVisPropAttiInIterField;
	private ListGridField flgRiservatezzaRelUserUoField;
	private ListGridField uoPuntoProtocolloField;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;	
	protected ControlListGridField uoCollegatePuntoProtocolloButtonField;

	protected ListaUoAssociateUtenteItem instance = this;
	
	public ListaUoAssociateUtenteItem(String name) {
				
		super(name, "uo_associate_utente");
		
		setGridPkField("rowId");

		// Nascoste
		rowIdField = new ListGridField("rowId");
		rowIdField.setHidden(true);
		rowIdField.setCanHide(false);
		rowIdField.setCanSort(true);
		rowIdField.setCanEdit(false);
		
		idUoField = new ListGridField("idUo");
		idUoField.setHidden(true);
		idUoField.setCanHide(false);
		idUoField.setCanSort(true);
		idUoField.setCanEdit(false);
		
		idRuoloField = new ListGridField("idRuolo");
		idRuoloField.setHidden(true);
		idRuoloField.setCanHide(false);
		idRuoloField.setCanSort(true);
		idRuoloField.setCanEdit(false);
		
		tipoRelazioneField = new ListGridField("tipoRelazione");
		tipoRelazioneField.setHidden(true);
		tipoRelazioneField.setCanHide(false);
		tipoRelazioneField.setCanSort(true);
		tipoRelazioneField.setCanEdit(false);
		
		denominazioneScrivaniaUtenteField = new ListGridField("denominazioneScrivaniaUtente");
		denominazioneScrivaniaUtenteField.setHidden(true);
		denominazioneScrivaniaUtenteField.setCanHide(false);
		denominazioneScrivaniaUtenteField.setCanSort(true);
		denominazioneScrivaniaUtenteField.setCanEdit(false);
		
		descrizioneEstesaField = new ListGridField("descrizioneEstesa");
		descrizioneEstesaField.setHidden(true);
		descrizioneEstesaField.setCanHide(false);
		descrizioneEstesaField.setCanSort(true);
		descrizioneEstesaField.setCanEdit(false);
		
		typeNodoField = new ListGridField("typeNodo");
		typeNodoField.setHidden(true);
		typeNodoField.setCanHide(false);
        typeNodoField.setCanSort(true);
        typeNodoField.setCanEdit(false);
		
		listaUOPuntoProtocolloIncluseField = new ListGridField("listaUOPuntoProtocolloIncluse");
		listaUOPuntoProtocolloIncluseField.setHidden(true);
		listaUOPuntoProtocolloIncluseField.setCanHide(false);
		listaUOPuntoProtocolloIncluseField.setCanSort(true);
		listaUOPuntoProtocolloIncluseField.setCanEdit(false);
		
		listaUOPuntoProtocolloEscluseField = new ListGridField("listaUOPuntoProtocolloEscluse");
		listaUOPuntoProtocolloEscluseField.setHidden(true);
		listaUOPuntoProtocolloEscluseField.setCanHide(false);
		listaUOPuntoProtocolloEscluseField.setCanSort(true);
		listaUOPuntoProtocolloEscluseField.setCanEdit(false);
		
		listaUOPuntoProtocolloEreditarietaAbilitataField = new ListGridField("listaUOPuntoProtocolloEreditarietaAbilitata");
		listaUOPuntoProtocolloEreditarietaAbilitataField.setHidden(true);
		listaUOPuntoProtocolloEreditarietaAbilitataField.setCanHide(false);
		listaUOPuntoProtocolloEreditarietaAbilitataField.setCanSort(true);
		listaUOPuntoProtocolloEreditarietaAbilitataField.setCanEdit(false);
		
		flgPresentiDocFascField = new ListGridField("flgPresentiDocFasc");
		flgPresentiDocFascField.setHidden(true);
		flgPresentiDocFascField.setCanHide(false);
		flgPresentiDocFascField.setCanSort(true);
		flgPresentiDocFascField.setCanEdit(false);
		
		flgTipoDestDocField = new ListGridField("flgTipoDestDoc");
		flgTipoDestDocField.setHidden(true);
		flgTipoDestDocField.setCanHide(false);
		flgTipoDestDocField.setCanSort(true);
		flgTipoDestDocField.setCanEdit(false);
		
		idUODestDocfascField = new ListGridField("idUODestDocfasc");
		idUODestDocfascField.setHidden(true);
		idUODestDocfascField.setCanHide(false);
		idUODestDocfascField.setCanSort(true);
		idUODestDocfascField.setCanEdit(false);
		
		livelliUODestDocFascField = new ListGridField("livelliUODestDocFasc");
		livelliUODestDocFascField.setHidden(true);
		livelliUODestDocFascField.setCanHide(false);
		livelliUODestDocFascField.setCanSort(true);
		livelliUODestDocFascField.setCanEdit(false);
		
		desUODestDocFascField = new ListGridField("desUODestDocFasc");
		desUODestDocFascField.setHidden(true);
		desUODestDocFascField.setCanHide(false);
		desUODestDocFascField.setCanSort(true);
		desUODestDocFascField.setCanEdit(false);
		
		statoSpostamentoDocFascField = new ListGridField("statoSpostamentoDocFasc");
		statoSpostamentoDocFascField.setHidden(true);
		statoSpostamentoDocFascField.setCanHide(false);
		statoSpostamentoDocFascField.setCanSort(true);
		statoSpostamentoDocFascField.setCanEdit(false);
		
		dataInizioSpostamentoDocFascField = new ListGridField("dataInizioSpostamentoDocFasc");
		dataInizioSpostamentoDocFascField.setHidden(true);
		dataInizioSpostamentoDocFascField.setCanHide(false);
		dataInizioSpostamentoDocFascField.setCanSort(true);
		dataInizioSpostamentoDocFascField.setCanEdit(false);
		
		dataFineSpostamentoDocFascField = new ListGridField("dataFineSpostamentoDocFasc");
		dataFineSpostamentoDocFascField.setHidden(true);
		dataFineSpostamentoDocFascField.setCanHide(false);
		dataFineSpostamentoDocFascField.setCanSort(true);
		dataFineSpostamentoDocFascField.setCanEdit(false);
		
		nrDocAssegnatiField = new ListGridField("nrDocAssegnati");
		nrDocAssegnatiField.setHidden(true);
		nrDocAssegnatiField.setCanHide(false);
		nrDocAssegnatiField.setCanSort(true);
		nrDocAssegnatiField.setCanEdit(false);
		
		dataConteggioDocAssegnatiField = new ListGridField("dataConteggioDocAssegnati");
		dataConteggioDocAssegnatiField.setHidden(true);
		dataConteggioDocAssegnatiField.setCanHide(false);
		dataConteggioDocAssegnatiField.setCanSort(true);
		dataConteggioDocAssegnatiField.setCanEdit(false);
		
		nrFascAssegnatiField = new ListGridField("nrFascAssegnati");
		nrFascAssegnatiField.setHidden(true);
		nrFascAssegnatiField.setCanHide(false);
		nrFascAssegnatiField.setCanSort(true);
		nrFascAssegnatiField.setCanEdit(false);
		
		dataConteggioFascAssegnatiField = new ListGridField("dataConteggioFascAssegnati");
		dataConteggioFascAssegnatiField.setHidden(true);
		dataConteggioFascAssegnatiField.setCanHide(false);
		dataConteggioFascAssegnatiField.setCanSort(true);
		dataConteggioFascAssegnatiField.setCanEdit(false);
		
		flgModificatoField = new ListGridField("flgModificato");
		flgModificatoField.setHidden(true);
		flgModificatoField.setCanHide(false);
		flgModificatoField.setCanSort(true);
		flgModificatoField.setCanEdit(false);
		
		flgGestAttiTuttiItem = new ListGridField("flgGestAttiTutti");
		flgGestAttiTuttiItem.setHidden(true);
		
		listaTipiGestAttiSelezionatiItem = new ListGridField("listaTipiGestAttiSelezionati");
		listaTipiGestAttiSelezionatiItem.setHidden(true);
		
		flgVisPropAttiInIterTuttiItem = new ListGridField("flgVisPropAttiInIterTutti");
		flgVisPropAttiInIterTuttiItem.setHidden(true);
		
		listaTipiVisPropAttiInIterSelezionatiItem = new ListGridField("listaTipiVisPropAttiInIterSelezionati");
		listaTipiVisPropAttiInIterSelezionatiItem.setHidden(true);
		
		// visibili		
		codRapidoField = new ListGridField("codRapido", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_codRapidoItem_title());
		codRapidoField.setWidth(70);
		
		denominazioneUoField = new ListGridField("denominazioneUo", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_denominazioneUoItem_title());
		denominazioneUoField.setWidth(333);
		
		descTipoRelazioneField = new ListGridField("descTipoRelazione", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_descTipoRelazioneItem_title());
		descTipoRelazioneField.setWidth(120);
		
		dtInizioVldField = new ListGridField("dtInizioVld", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_dtInizioVldItem_title());
		dtInizioVldField.setType(ListGridFieldType.DATE);
		dtInizioVldField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dtInizioVldField.setWidth(70);
		
		dtFineVldField = new ListGridField("dtFineVld", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_dtFineVldItem_title());
		dtFineVldField.setType(ListGridFieldType.DATE);
		dtFineVldField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dtFineVldField.setWidth(70);
		
		descrizioneRuoloField = new ListGridField("descrizioneRuolo", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_ruoloItem_title());
		descrizioneRuoloField.setWidth(120);
		
		flgAccessoDocLimSVField            = new ListGridField("flgAccessoDocLimSV",       I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title());   		
		flgAccessoDocLimSVField.setType(ListGridFieldType.ICON);
		flgAccessoDocLimSVField.setAttribute("custom", true);
		flgAccessoDocLimSVField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgAccessoDocLimSVField.setWrap(false);
		flgAccessoDocLimSVField.setWidth(30);
		flgAccessoDocLimSVField.setIconWidth(16);
		flgAccessoDocLimSVField.setIconHeight(16);
		flgAccessoDocLimSVField.setShowHover(true);
		flgAccessoDocLimSVField.setShowTitle(false);
		flgAccessoDocLimSVField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgAccessoDocLimSV");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/accesso_limitato_doc_assegnata_personalmente.png");
				}
				return null;
			}
		});
		
		flgAccessoDocLimSVField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgAccessoDocLimSV");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgAccessoDocLimSVItem_title();
				}
				return null;
				
			}
		});
		
		flgIncluseSottoUoField = new ListGridField("flgIncluseSottoUo", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title());    
		flgIncluseSottoUoField.setType(ListGridFieldType.ICON);
		flgIncluseSottoUoField.setAttribute("custom", true);
		flgIncluseSottoUoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgIncluseSottoUoField.setWrap(false);
		flgIncluseSottoUoField.setWidth(30);
		flgIncluseSottoUoField.setIconWidth(16);
		flgIncluseSottoUoField.setIconHeight(16);
		flgIncluseSottoUoField.setShowHover(true);
		flgIncluseSottoUoField.setShowTitle(false);
		flgIncluseSottoUoField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgIncluseSottoUo = record.getAttributeAsString("flgIncluseSottoUo");
				if (flgIncluseSottoUo == null)
					flgIncluseSottoUo = "false";
				if (flgIncluseSottoUo.equals("true")) {
					return buildImgButtonHtml("buttons/delega_sotto_uo.png");
				}
				return null;
			}
		});
		
		flgIncluseSottoUoField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgIncluseSottoUo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseSottoUoItem_title();
				}
				return null;
				
			}
		});
				
		flgIncluseScrivanieField = new ListGridField("flgIncluseScrivanie", I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title());
		flgIncluseScrivanieField.setType(ListGridFieldType.ICON);
		flgIncluseScrivanieField.setAttribute("custom", true);
		flgIncluseScrivanieField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgIncluseScrivanieField.setWrap(false);
		flgIncluseScrivanieField.setWidth(30);
		flgIncluseScrivanieField.setIconWidth(16);
		flgIncluseScrivanieField.setIconHeight(16);
		flgIncluseScrivanieField.setShowHover(true);
		flgIncluseScrivanieField.setShowTitle(false);
		flgIncluseScrivanieField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgIncluseScrivanie");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/delega_postazioni_utente.png");
				}
				return null;
			}
		});
		
		flgIncluseScrivanieField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgIncluseScrivanie");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgIncluseScrivanieItem_title();
				}
				return null;
				
			}
		});
		
		flgRegistrazioneEField             = new ListGridField("flgRegistrazioneE",       I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title());    
		flgRegistrazioneEField.setType(ListGridFieldType.ICON);
		flgRegistrazioneEField.setAttribute("custom", true);
		flgRegistrazioneEField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRegistrazioneEField.setWrap(false);
		flgRegistrazioneEField.setWidth(30);
		flgRegistrazioneEField.setIconWidth(16);
		flgRegistrazioneEField.setIconHeight(16);
		flgRegistrazioneEField.setShowHover(true);
		flgRegistrazioneEField.setShowTitle(false);
		flgRegistrazioneEField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRegistrazioneE");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/registrazione_entrata.png");
				}
				return null;
			}
		});
		
		flgRegistrazioneEField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRegistrazioneE");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneEItem_title();
				}
				return null;
				
			}
		});
				
		flgRegistrazioneUIField            = new ListGridField("flgRegistrazioneUI",       I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title());   
		flgRegistrazioneUIField.setType(ListGridFieldType.ICON);
		flgRegistrazioneUIField.setAttribute("custom", true);
		flgRegistrazioneUIField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRegistrazioneUIField.setWrap(false);
		flgRegistrazioneUIField.setWidth(30);
		flgRegistrazioneUIField.setIconWidth(16);
		flgRegistrazioneUIField.setIconHeight(16);
		flgRegistrazioneUIField.setShowHover(true);
		flgRegistrazioneUIField.setShowTitle(false);
		flgRegistrazioneUIField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRegistrazioneUI");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/registrazione_uscita_interna.png");
				}
				return null;
			}
		});
		
		flgRegistrazioneUIField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRegistrazioneUI");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRegistrazioneUIItem_title();
				}
				return null;
				
			}
		});
				
		flgGestAttiField                   = new ListGridField("flgGestAtti",          I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgGestAttiItem_title());          
		flgGestAttiField.setType(ListGridFieldType.ICON);
		flgGestAttiField.setAttribute("custom", true);
		flgGestAttiField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgGestAttiField.setWrap(false);
		flgGestAttiField.setWidth(30);
		flgGestAttiField.setIconWidth(16);
		flgGestAttiField.setIconHeight(16);
		flgGestAttiField.setShowHover(true);
		flgGestAttiField.setShowTitle(false);
		flgGestAttiField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgGestAtti");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/avvio_gestione_atti_proposti.png");
				}
				return null;
			}
		});
		
		flgGestAttiField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgGestAtti");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgGestAttiItem_title();
				}
				return null;
				
			}
		});
				
		flgVisPropAttiInIterField          = new ListGridField("flgVisPropAttiInIter",     I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title()); 
		flgVisPropAttiInIterField.setType(ListGridFieldType.ICON);
		flgVisPropAttiInIterField.setAttribute("custom", true);
		flgVisPropAttiInIterField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgVisPropAttiInIterField.setWrap(false);
		flgVisPropAttiInIterField.setWidth(30);
		flgVisPropAttiInIterField.setIconWidth(16);
		flgVisPropAttiInIterField.setIconHeight(16);
		flgVisPropAttiInIterField.setShowHover(true);
		flgVisPropAttiInIterField.setShowTitle(false);
		flgVisPropAttiInIterField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgVisPropAttiInIter");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/visualizzazione_atti_proposti.png");
				}
				return null;
			}
		});
		
		flgVisPropAttiInIterField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgVisPropAttiInIter");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgVisPropAttiInIterItem_title();
				}
				return null;
				
			}
		});
		
		
		// Abilitazione alla documentazione riservata assegnata alla struttura 
		flgRiservatezzaRelUserUoField = new ListGridField("flgRiservatezzaRelUserUo",     I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title()); 
		flgRiservatezzaRelUserUoField.setType(ListGridFieldType.ICON);
		flgRiservatezzaRelUserUoField.setAttribute("custom", true);
		flgRiservatezzaRelUserUoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgRiservatezzaRelUserUoField.setWrap(false);
		flgRiservatezzaRelUserUoField.setWidth(30);
		flgRiservatezzaRelUserUoField.setIconWidth(16);
		flgRiservatezzaRelUserUoField.setIconHeight(16);
		flgRiservatezzaRelUserUoField.setShowHover(true);
		flgRiservatezzaRelUserUoField.setShowTitle(false);
		flgRiservatezzaRelUserUoField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRiservatezzaRelUserUo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("lock.png");
				}
				return null;
			}
		});
		
		flgRiservatezzaRelUserUoField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgRiservatezzaRelUserUo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_flgRiservatezzaRelUserUoItem_title();
				}
				return null;
				
			}
		});
		
		// Se il parametro DB ATTIVA_GEST_RISERVATEZZA_REL_USER_UO = false la nascondo
		if (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_RISERVATEZZA_REL_USER_UO")){
			flgRiservatezzaRelUserUoField.setHidden(true);
			flgRiservatezzaRelUserUoField.setCanHide(false);
		}
		
		
		flgUoPuntoProtocolloField          = new ListGridField("flgUoPuntoProtocollo",     I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title()); 
		flgUoPuntoProtocolloField.setType(ListGridFieldType.ICON);
		flgUoPuntoProtocolloField.setAttribute("custom", true);
		flgUoPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgUoPuntoProtocolloField.setWrap(false);
		flgUoPuntoProtocolloField.setWidth(30);
		flgUoPuntoProtocolloField.setIconWidth(16);
		flgUoPuntoProtocolloField.setIconHeight(16);
		flgUoPuntoProtocolloField.setShowHover(true);
		flgUoPuntoProtocolloField.setShowTitle(false);
		flgUoPuntoProtocolloField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgUoPuntoProtocollo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/uoCollegatePuntoProtocollo.png");
				}
				return null;
			}
		});
		
		flgUoPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgUoPuntoProtocollo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoCollegatePuntoProtocolloButton_title();
				}
				return null;
				
			}
		});
		
		flgUoPuntoProtocolloField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickUOCollegatePuntoProtocolloButton(event.getRecord());
				}
			}
		});	
		
		
		
		uoPuntoProtocolloField             = new ListGridField("uoPuntoProtocollo",        I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoPuntoProtocolloItem_title());
		uoPuntoProtocolloField.setType(ListGridFieldType.ICON);
		uoPuntoProtocolloField.setAttribute("custom", true);
		uoPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		uoPuntoProtocolloField.setWrap(false);
		uoPuntoProtocolloField.setWidth(30);
		uoPuntoProtocolloField.setIconWidth(16);
		uoPuntoProtocolloField.setIconHeight(16);
		uoPuntoProtocolloField.setShowHover(true);
		uoPuntoProtocolloField.setShowTitle(false);
		uoPuntoProtocolloField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgUoPuntoProtocollo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return buildImgButtonHtml("buttons/protocollazione.png");
				}
				return null;
			}
		});
		
		uoPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String lvalue = record.getAttributeAsString("flgUoPuntoProtocollo");
				if (lvalue == null)
					lvalue = "false";
				if (lvalue.equals("true")) {
					return I18NUtil.getMessages().gestioneutenti_uoAssociateUtente_uoPuntoProtocolloItem_title();
				}
				return null;
				
			}
		});
		
		setGridFields( //visibili
		               codRapidoField,
		               denominazioneUoField,
		               uoPuntoProtocolloField,             
		               descTipoRelazioneField,
		               dtInizioVldField,
		               dtFineVldField,				               
		               descrizioneRuoloField,
		               flgAccessoDocLimSVField,
		               flgIncluseSottoUoField,
		               flgIncluseScrivanieField,
		               flgRegistrazioneEField,
		               flgRegistrazioneUIField,
		               flgGestAttiField,
		               flgVisPropAttiInIterField,
		               flgRiservatezzaRelUserUoField,
		               flgUoPuntoProtocolloField,
			               
			           // hidden
		               rowIdField,
			           tipoRelazioneField,
			           idRuoloField,
			           denominazioneScrivaniaUtenteField,				             
			           idUoField,
			           descrizioneEstesaField,
	                   typeNodoField,
	                   				               
	                   listaUOPuntoProtocolloIncluseField,
	                   listaUOPuntoProtocolloEscluseField,
	                   listaUOPuntoProtocolloEreditarietaAbilitataField,
	                   flgPresentiDocFascField,
	                   flgTipoDestDocField,				               
	                   idUODestDocfascField,
	                   livelliUODestDocFascField,
	                   desUODestDocFascField,		
	                   statoSpostamentoDocFascField,
	                   dataInizioSpostamentoDocFascField,
	                   dataFineSpostamentoDocFascField,
	                   nrDocAssegnatiField,
	                   dataConteggioDocAssegnatiField,
	                   nrFascAssegnatiField,
	                   dataConteggioFascAssegnatiField,
	                   flgGestAttiTuttiItem,
	                   listaTipiGestAttiSelezionatiItem,
	                   flgVisPropAttiInIterTuttiItem,
	                   listaTipiVisPropAttiInIterSelezionatiItem
					);	
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		return grid;
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(isShowEditButtons()) {
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}			
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
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
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				} else {
					return buildImgButtonHtml("buttons/detail.png");
				}
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				} else {
					return I18NUtil.getMessages().detailButton_prompt();
				}
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				} else {
					onClickDetailButton(event.getRecord());	
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord recordIn) {	
		Record record = createRecordToAgganciaUtenteUOPopup(recordIn);
		
		AgganciaUtenteUOPopup agganciaUtenteUOPopup = new AgganciaUtenteUOPopup(record, "Dettaglio associazione", false, "view") {
			
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {
				markForRedraw();
				markForDestroy();
			}
		};
		agganciaUtenteUOPopup.show();
	}
	
	public void onClickModifyButton(final ListGridRecord recordIn) {	
		
		Record record = createRecordToAgganciaUtenteUOPopup(recordIn);
		
		AgganciaUtenteUOPopup agganciaUtenteUOPopup = new AgganciaUtenteUOPopup(record, "Modifica associazione", false, "edit") {
			
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {
				Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
				Layout.hideWaitPopup();
				setFormValuesFromRecordAfterMod(formRecord, recordIn);
				markForRedraw();
				markForDestroy();
			}
		};
		agganciaUtenteUOPopup.show();
	}

	private Record createRecordToAgganciaUtenteUOPopup(final ListGridRecord recordIn) {
		Record record = new Record();
		record.setAttribute("rowId", recordIn.getAttribute("rowId"));
		record.setAttribute("dtInizioVld", recordIn.getAttribute("dtInizioVld"));
		record.setAttribute("dtFineVld", recordIn.getAttribute("dtFineVld"));
		record.setAttribute("idRuolo", recordIn.getAttribute("idRuolo"));
		record.setAttribute("descrizioneRuolo", recordIn.getAttribute("descrizioneRuolo"));
		record.setAttribute("tipoRelazione", recordIn.getAttribute("tipoRelazione"));
		record.setAttribute("descTipoRelazione", recordIn.getAttribute("descTipoRelazione"));
		record.setAttribute("denominazioneScrivaniaUtente", recordIn.getAttribute("denominazioneScrivaniaUtente"));
		record.setAttribute("idUo", recordIn.getAttribute("idUo"));
		record.setAttribute("codRapido", recordIn.getAttribute("codRapido"));
		record.setAttribute("organigramma", recordIn.getAttribute("idUo"));
		record.setAttribute("descrizione", recordIn.getAttribute("denominazioneUo"));
		record.setAttribute("descrizioneEstesa", recordIn.getAttribute("descrizioneEstesa"));
		record.setAttribute("typeNodo", recordIn.getAttribute("typeNodo"));
		record.setAttribute("flgAccessoDocLimSV", recordIn.getAttributeAsBoolean("flgAccessoDocLimSV"));
		record.setAttribute("flgIncluseSottoUo", recordIn.getAttributeAsBoolean("flgIncluseSottoUo"));
		record.setAttribute("flgIncluseScrivanie", recordIn.getAttributeAsBoolean("flgIncluseScrivanie"));
		record.setAttribute("flgRegistrazioneE", recordIn.getAttributeAsBoolean("flgRegistrazioneE"));
		record.setAttribute("flgRegistrazioneUI", recordIn.getAttributeAsBoolean("flgRegistrazioneUI"));
		record.setAttribute("flgGestAtti", recordIn.getAttributeAsBoolean("flgGestAtti"));
		record.setAttribute("flgGestAttiTutti", recordIn.getAttributeAsBoolean("flgGestAttiTutti"));
		record.setAttribute("listaTipiGestAttiSelezionati", recordIn.getAttribute("listaTipiGestAttiSelezionati"));
		record.setAttribute("flgVisPropAttiInIter", recordIn.getAttributeAsBoolean("flgVisPropAttiInIter"));
		record.setAttribute("flgVisPropAttiInIterTutti", recordIn.getAttributeAsBoolean("flgVisPropAttiInIterTutti"));
		record.setAttribute("listaTipiVisPropAttiInIterSelezionati", recordIn.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
		record.setAttribute("flgRiservatezzaRelUserUo", recordIn.getAttributeAsBoolean("flgRiservatezzaRelUserUo"));
		record.setAttribute("flgUoPuntoProtocollo", recordIn.getAttribute("flgUoPuntoProtocollo"));
		record.setAttribute("listaUOPuntoProtocolloIncluse", recordIn.getAttribute("listaUOPuntoProtocolloIncluse"));
		record.setAttribute("listaUOPuntoProtocolloEscluse", recordIn.getAttribute("listaUOPuntoProtocolloEscluse"));
		record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", recordIn.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		record.setAttribute("flgPresentiDocFasc", recordIn.getAttribute("flgPresentiDocFasc"));
		record.setAttribute("idUODestDocfasc", recordIn.getAttribute("idUODestDocfasc"));
		record.setAttribute("desUODestDocFasc", recordIn.getAttribute("desUODestDocFasc"));
		record.setAttribute("livelliUODestDocFasc", recordIn.getAttribute("livelliUODestDocFasc"));
		record.setAttribute("statoSpostamentoDocFasc", recordIn.getAttribute("statoSpostamentoDocFasc"));
		record.setAttribute("dataInizioSpostamentoDocFasc", recordIn.getAttribute("dataInizioSpostamentoDocFasc"));
		record.setAttribute("dataFineSpostamentoDocFasc", recordIn.getAttribute("dataFineSpostamentoDocFasc"));
		record.setAttribute("nrDocAssegnati", recordIn.getAttribute("nrDocAssegnati"));
		record.setAttribute("dataConteggioDocAssegnati", recordIn.getAttribute("dataConteggioDocAssegnati"));
		record.setAttribute("nrFascAssegnati", recordIn.getAttribute("nrFascAssegnati"));
		record.setAttribute("dataConteggioFascAssegnati", recordIn.getAttribute("dataConteggioFascAssegnati"));
		
		if(recordIn.getAttribute("flgTipoDestDoc") != null && recordIn.getAttribute("idUODestDocfasc") != null) {
			record.setAttribute("organigrammaSpostaDocFasc", recordIn.getAttribute("flgTipoDestDoc") +  recordIn.getAttribute("idUODestDocfasc"));
		}
		return record;
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
//		grid.deselectAllRecords();
		instance.removeData(record);
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(getNomeLista(), fields);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		ToolStripButton addButton = new ToolStripButton();   
		addButton.setIcon("buttons/add.png");   
		addButton.setIconSize(16);
		addButton.setPrefix("Nuovo");
		addButton.setPrompt("Nuovo");
		
		final String finalita = "uo_associate_utente";
		final boolean flgSelezioneSingola = false;
		
		addButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddButton(finalita, flgSelezioneSingola);     	
			}   
		});  
		buttons.add(addButton);

		return buttons;
	}
	
	public void onClickAddButton(String finalita, Boolean flgSelezioneSingola) {
		
		Record record = new Record();
		AgganciaUtenteUOPopup agganciaUtenteUOPopup = new AgganciaUtenteUOPopup(record, "Nuova associazione", false, "new") {
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				setFormValuesFromRecordAfterNew(formRecord);
				markForDestroy();
			}
		};
		agganciaUtenteUOPopup.show();
	}
	
	public void setFormValuesFromRecordAfterNew(Record record) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		
		String organigramma = record.getAttribute("organigramma");
        String idUo = togliPrefissoUO(organigramma);
       
		recordToInsert.setAttribute("idUo", idUo);
		recordToInsert.setAttribute("denominazioneUo", record.getAttribute("descrizione"));
		recordToInsert.setAttribute("descrizioneEstesa", record.getAttribute("descrizioneEstesa"));
		recordToInsert.setAttribute("typeNodo", record.getAttribute("typeNodo"));
		recordToInsert.setAttribute("flgAccessoDocLimSV", record.getAttributeAsBoolean("flgAccessoDocLimSV"));
		recordToInsert.setAttribute("flgIncluseSottoUo", record.getAttributeAsBoolean("flgInclSottoUo"));
		recordToInsert.setAttribute("flgIncluseScrivanie", record.getAttributeAsBoolean("flgIncluseScrivanie"));
		recordToInsert.setAttribute("flgRegistrazioneE", record.getAttributeAsBoolean("flgRegistrazioneE"));
		recordToInsert.setAttribute("flgRegistrazioneUI", record.getAttributeAsBoolean("flgRegistrazioneUI"));
		recordToInsert.setAttribute("flgGestAtti", record.getAttributeAsBoolean("flgGestAtti"));
		recordToInsert.setAttribute("flgGestAttiTutti", record.getAttributeAsBoolean("flgGestAttiTutti"));
		recordToInsert.setAttribute("listaTipiGestAttiSelezionati", record.getAttribute("listaTipiGestAttiSelezionati"));
		recordToInsert.setAttribute("flgVisPropAttiInIter", record.getAttributeAsBoolean("flgVisPropAttiInIter"));
		recordToInsert.setAttribute("flgVisPropAttiInIterTutti", record.getAttributeAsBoolean("flgVisPropAttiInIterTutti"));
		recordToInsert.setAttribute("listaTipiVisPropAttiInIterSelezionati", record.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
		recordToInsert.setAttribute("flgRiservatezzaRelUserUo", record.getAttributeAsBoolean("flgRiservatezzaRelUserUo"));
		recordToInsert.setAttribute("flgUoPuntoProtocollo", record.getAttribute("flgUoPuntoProtocollo"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		recordToInsert.setAttribute("flgTipoDestDoc", record.getAttribute("typeNodoSpostaDocFasc"));
		recordToInsert.setAttribute("idUODestDocfasc", record.getAttribute("idUoSpostaDocFasc"));
		recordToInsert.setAttribute("livelliUODestDocFasc", record.getAttribute("codRapidoSpostaDocFasc"));
		recordToInsert.setAttribute("desUODestDocFasc", record.getAttribute("descrizioneSpostaDocFasc"));
		recordToInsert.setAttribute("idRuolo", record.getAttribute("idRuolo"));
		recordToInsert.setAttribute("descrizioneRuolo", record.getAttribute("descrizioneRuolo"));
		recordToInsert.setAttribute("tipoRelazione", record.getAttribute("tipoRelUtenteUo"));
		recordToInsert.setAttribute("descTipoRelazione", record.getAttribute("descTipoRelUtenteUo"));
		recordToInsert.setAttribute("denominazioneScrivaniaUtente", record.getAttribute("denominazioneScrivaniaUtente"));
		recordToInsert.setAttribute("codRapido", record.getAttribute("codRapido"));
		recordToInsert.setAttribute("dtInizioVld", (record.getAttribute("dataDal")!=null ? DateUtil.format(record.getAttributeAsDate("dataDal")) : null));
        recordToInsert.setAttribute("dtFineVld",   (record.getAttribute("dataAl")!=null ?  DateUtil.format(record.getAttributeAsDate("dataAl"))  : null));
        
        recordToInsert.setAttribute("flgModificato", "1");
        
        // Aggiungo i dati a quelli gia' presenti
     	instance.addData(recordToInsert);	
	}
	
	public void setFormValuesFromRecordAfterMod(Record record, Record recordOld) {
				
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
				
		String organigramma = record.getAttribute("organigramma");
	    String idUo = togliPrefissoUO(organigramma);
		recordToInsert.setAttribute("idUo", idUo);
		recordToInsert.setAttribute("rowId", record.getAttribute("rowId"));
		recordToInsert.setAttribute("denominazioneUo", record.getAttribute("descrizione"));
		recordToInsert.setAttribute("descrizioneEstesa", record.getAttribute("descrizioneEstesa"));
		recordToInsert.setAttribute("typeNodo", record.getAttribute("typeNodo"));
		recordToInsert.setAttribute("flgAccessoDocLimSV", record.getAttributeAsBoolean("flgAccessoDocLimSV"));
		recordToInsert.setAttribute("flgIncluseSottoUo", record.getAttributeAsBoolean("flgInclSottoUo"));
		recordToInsert.setAttribute("flgIncluseScrivanie", record.getAttributeAsBoolean("flgIncluseScrivanie"));
		recordToInsert.setAttribute("flgRegistrazioneE", record.getAttributeAsBoolean("flgRegistrazioneE"));
		recordToInsert.setAttribute("flgRegistrazioneUI", record.getAttributeAsBoolean("flgRegistrazioneUI"));
		recordToInsert.setAttribute("flgGestAtti", record.getAttributeAsBoolean("flgGestAtti"));
		recordToInsert.setAttribute("flgGestAttiTutti", record.getAttributeAsBoolean("flgGestAttiTutti"));
		recordToInsert.setAttribute("listaTipiGestAttiSelezionati", record.getAttribute("listaTipiGestAttiSelezionati"));
		recordToInsert.setAttribute("flgVisPropAttiInIter", record.getAttributeAsBoolean("flgVisPropAttiInIter"));
		recordToInsert.setAttribute("flgVisPropAttiInIterTutti", record.getAttributeAsBoolean("flgVisPropAttiInIterTutti"));
		recordToInsert.setAttribute("listaTipiVisPropAttiInIterSelezionati", record.getAttribute("listaTipiVisPropAttiInIterSelezionati"));
		recordToInsert.setAttribute("flgRiservatezzaRelUserUo", record.getAttributeAsBoolean("flgRiservatezzaRelUserUo"));
		recordToInsert.setAttribute("flgUoPuntoProtocollo", record.getAttribute("flgUoPuntoProtocollo"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		recordToInsert.setAttribute("flgTipoDestDoc", record.getAttribute("typeNodoSpostaDocFasc"));
		recordToInsert.setAttribute("idUODestDocfasc", record.getAttribute("idUoSpostaDocFasc"));
		recordToInsert.setAttribute("livelliUODestDocFasc", record.getAttribute("codRapidoSpostaDocFasc"));
		recordToInsert.setAttribute("desUODestDocFasc", record.getAttribute("descrizioneSpostaDocFasc"));
		recordToInsert.setAttribute("idRuolo", record.getAttribute("idRuolo"));
		recordToInsert.setAttribute("descrizioneRuolo", record.getAttribute("descrizioneRuolo"));
		recordToInsert.setAttribute("tipoRelazione", record.getAttribute("tipoRelUtenteUo"));
		recordToInsert.setAttribute("descTipoRelazione", record.getAttribute("descTipoRelUtenteUo"));
		recordToInsert.setAttribute("denominazioneScrivaniaUtente", record.getAttribute("denominazioneScrivaniaUtente"));
		recordToInsert.setAttribute("codRapido", record.getAttribute("codRapido"));
		recordToInsert.setAttribute("dtInizioVld", (record.getAttribute("dataDal") != null ? DateUtil.format(record.getAttributeAsDate("dataDal")) : null));
		recordToInsert.setAttribute("dtFineVld", (record.getAttribute("dataAl") != null ? DateUtil.format(record.getAttributeAsDate("dataAl")) : null));
        
        recordToInsert.setAttribute("flgModificato", "1");
		
        // Aggiungo i dati a quelli gia' presenti
        instance.updateData(recordToInsert, recordOld);		
	}
	
	public void setFormValuesFromRecordAfterAbilUOPuntoProtocollo(Record record, Record recordOld) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		
		String organigramma = recordOld.getAttribute("organigramma");
	    String idUo = togliPrefissoUO(organigramma);
		recordToInsert.setAttribute("idUo", idUo);
		recordToInsert.setAttribute("denominazioneUo", recordOld.getAttribute("descrizione"));
		recordToInsert.setAttribute("descrizioneEstesa", recordOld.getAttribute("descrizioneEstesa"));
		recordToInsert.setAttribute("typeNodo", recordOld.getAttribute("typeNodo"));
		recordToInsert.setAttribute("flgAccessoDocLimSV", recordOld.getAttributeAsBoolean("flgAccessoDocLimSV"));
		recordToInsert.setAttribute("flgIncluseSottoUo", recordOld.getAttributeAsBoolean("flgInclSottoUo"));
		recordToInsert.setAttribute("flgIncluseScrivanie", recordOld.getAttributeAsBoolean("flgIncluseScrivanie"));
		recordToInsert.setAttribute("flgRegistrazioneE", recordOld.getAttributeAsBoolean("flgRegistrazioneE"));
		recordToInsert.setAttribute("flgRegistrazioneUI", recordOld.getAttributeAsBoolean("flgRegistrazioneUI"));
		recordToInsert.setAttribute("flgGestAtti", recordOld.getAttributeAsBoolean("flgGestAtti"));
		recordToInsert.setAttribute("flgVisPropAttiInIter", recordOld.getAttributeAsBoolean("flgVisPropAttiInIter"));
		recordToInsert.setAttribute("flgRiservatezzaRelUserUo", recordOld.getAttributeAsBoolean("flgRiservatezzaRelUserUo"));
		recordToInsert.setAttribute("flgUoPuntoProtocollo", recordOld.getAttribute("flgUoPuntoProtocollo"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloIncluse", recordOld.getAttribute("listaUOPuntoProtocolloIncluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEscluse", recordOld.getAttribute("listaUOPuntoProtocolloEscluse"));
		recordToInsert.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", recordOld.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
		recordToInsert.setAttribute("flgTipoDestDoc", recordOld.getAttribute("typeNodoSpostaDocFasc"));
		recordToInsert.setAttribute("idUODestDocfasc", recordOld.getAttribute("idUoSpostaDocFasc"));
		recordToInsert.setAttribute("livelliUODestDocFasc", recordOld.getAttribute("codRapidoSpostaDocFasc"));
		recordToInsert.setAttribute("desUODestDocFasc", recordOld.getAttribute("descrizioneSpostaDocFasc"));
		recordToInsert.setAttribute("idRuolo", recordOld.getAttribute("idRuolo"));
		recordToInsert.setAttribute("descrizioneRuolo", recordOld.getAttribute("descrizioneRuolo"));
		recordToInsert.setAttribute("tipoRelazione", recordOld.getAttribute("tipoRelUtenteUo"));
		recordToInsert.setAttribute("descTipoRelazione", recordOld.getAttribute("descTipoRelUtenteUo"));
		recordToInsert.setAttribute("denominazioneScrivaniaUtente", recordOld.getAttribute("denominazioneScrivaniaUtente"));
		recordToInsert.setAttribute("codRapido", recordOld.getAttribute("codRapido"));
		
		if(record.getAttributeAsDate("dataDal")!=null)
			recordToInsert.setAttribute("dtInizioVld", recordOld.getAttributeAsDate("dataDal"));
	    else
	    	recordToInsert.setAttribute("dtInizioVld", "");
	    
        if(record.getAttributeAsDate("dataAl")!=null)
        	recordToInsert.setAttribute("dtFineVld", recordOld.getAttributeAsDate("dataAl"));
        else
        	recordToInsert.setAttribute("dtFineVld", "");
        
        recordToInsert.setAttribute("listaUOPuntoProtocolloIncluse", record.getAttribute("listaUOPuntoProtocolloIncluse"));
        recordToInsert.setAttribute("listaUOPuntoProtocolloEscluse", record.getAttribute("listaUOPuntoProtocolloEscluse"));
        recordToInsert.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", record.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata"));
        
        recordToInsert.setAttribute("flgModificato", "1");
	}

    public String togliPrefissoUO(String stringIn){
    	String out = stringIn;
    	if(stringIn!=null && !stringIn.equalsIgnoreCase("") && stringIn.indexOf("UO") != -1 ){
    		out = stringIn.replaceAll("UO", "").trim();
    	}
    	return out;
    }

    public void onClickUOCollegatePuntoProtocolloButton(final ListGridRecord recordIn) {
    
    	String idUOPP = recordIn.getAttribute("idUo");
		String listaUOPuntoProtocolloEscluse = recordIn.getAttribute("listaUOPuntoProtocolloEscluse");
		String listaUOPuntoProtocolloEreditarietaAbilitata = recordIn.getAttribute("listaUOPuntoProtocolloEreditarietaAbilitata");
		Record record = new Record();
		record.setAttribute("idUOPP", idUOPP);
		record.setAttribute("listaUOPuntoProtocolloEscluse", listaUOPuntoProtocolloEscluse);				
		record.setAttribute("listaUOPuntoProtocolloEreditarietaAbilitata", listaUOPuntoProtocolloEreditarietaAbilitata);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
		lGwtRestDataSource.executecustom("leggiUOCollegatePuntoProtocollo", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					if (response.getData() != null) {
						Record lRecordDb    = response.getData()[0];
						RecordList listaUOCollegatePuntoProtocollo  = lRecordDb.getAttributeAsRecordList("listaUOCollegatePuntoProtocollo");								
						Record recordNew = new Record();
						recordNew.setAttribute("listaUOCollegatePuntoProtocollo", listaUOCollegatePuntoProtocollo);								
						final String denominazioneUo = recordIn.getAttribute("denominazioneUo");
						final String codRapido = recordIn.getAttribute("codRapido");								
						String title = "Abilitazioni vs UO collegate al punto di protocollo " + codRapido + "-" + denominazioneUo;
						
						String mode = "edit";
						
						UOCollegatePuntoProtocolloPopup uoCollegatePuntoProtocolloPopup = new UOCollegatePuntoProtocolloPopup(recordNew, title, mode) {
							@Override
							public void onClickOkButton(Record formRecord, DSCallback callback) {
								Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");				
								Layout.hideWaitPopup();
								setFormValuesFromRecordAfterAbilUOPuntoProtocollo(formRecord, recordIn);
								markForDestroy();
							}
						};
						uoCollegatePuntoProtocolloPopup.show();								
					}
				}
			}						
		});		
    }
    
    @Override
    public void setCanEdit(Boolean canEdit) {
		
		setEditable(canEdit);
		
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("Nuovo")) {
					if(isEditable() && isShowEditButtons() && isShowNewButton()) {
						member.show();	
					} else {
						member.hide();						
					}
				}
			}	
			
			getGrid().setCanReorderRecords(false);
			redrawRecordButtons();			
		}		
	}
    
    public abstract boolean isShowUOCollegatePuntoProtocolloButton();
    
}