/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.FrontendButton;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class RiepilogoProcedimentoDetail extends CustomDetail {

	protected DynamicForm mDynamicFormPrincipale;
	protected StaticTextItem textProcedimentoItem;
	protected StaticTextItem noteProcedimentoItem;
	protected StaticTextItem dichiaraItem;
	
	protected StaticTextItem schedaValutazioneTextItem;
	protected StaticTextItem modelloDomandaTextItem;
	protected StaticTextItem domandaValutazioneTextItem;
	protected StaticTextItem asseverazioneProponenteTextItem;
	protected StaticTextItem asseverazioneEspertoTextItem;
	protected StaticTextItem asseverazioneResponsabileTextItem;
	
	protected StaticTextItem infoATextItem;
	protected StaticTextItem infoBTextItem;
	protected StaticTextItem infoCTextItem;

	protected DynamicForm mDynamicFormButtons;
	protected FrontendButton stampaButtonItem;
	protected FrontendButton allegaDocumentazioneItem;

	private String idProcess;
	private String firstColWidth = "180";

	boolean completo = false;
	public RiepilogoProcedimentoDetail(String nomeEntita, String idProcess) {
		
		super(nomeEntita);
		
		this.idProcess = idProcess;
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		lVLayout.setPadding(10);
		
//		Label labelTitle = new Label("<b style='font-weight:bold;font-size:12px;font-style:normal;color:#003168;padding-right:8px;'>Riepilogo Procedimento</b><br/><br/>");
//		labelTitle.setWrap(true);
//		labelTitle.setAutoHeight();
//		labelTitle.setWidth(firstColWidth);
//		labelTitle.setAlign(Alignment.RIGHT);
//		lVLayout.addMember(labelTitle);
		
		VLayout lVLayoutTask = new VLayout();
		lVLayoutTask.setMembersMargin(10);
		
		buildSummary(lVLayoutTask);
//		buildSummaryContents(getTestRecord(), lVLayoutTask);
		
		lVLayout.addMember(lVLayoutTask);
		
		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();						
		lVLayout.addMember(spacer);		
		
		addMember(lVLayout);		
		
	}
	
	public void buildSummary(final VLayout lVLayoutTask) {
		getTaskProc(idProcess, new GetTaskProcCallback() {			
			@Override
			public void execute(Record data) {
				if(data == null) data = new Record();
				buildSummaryContents(data, lVLayoutTask);
			}
		});				
	}
	
	protected void buildSummaryContents(final Record record, VLayout lVLayout) {
		
		HLayout maskRow1 = new HLayout();
		maskRow1.setAutoHeight();
		maskRow1.setWidth100();
		
		SummaryLabel nPraticaCapt = new SummaryLabel("Pratica N.", firstColWidth, true, true);
		SummaryLabel nPraticaDesc = new SummaryLabel(record.getAttribute("nroPratica") + "/" + record.getAttribute("annoPratica"), null, true, false);
		maskRow1.addMember(nPraticaCapt);
		maskRow1.addMember(nPraticaDesc);
		lVLayout.addMember(maskRow1);
		
		HLayout maskRow2 = new HLayout();
		maskRow2.setAutoHeight();
		maskRow2.setWidth100();

		SummaryLabel procedimentoTipoCapt = new SummaryLabel("Procedimento", firstColWidth, true, true);
		SummaryLabel procedimentoTipoDesc = new SummaryLabel(record.getAttribute("tipoProcedimento"), null, true, false);
		maskRow2.addMember(procedimentoTipoCapt);
		maskRow2.addMember(procedimentoTipoDesc);
		SummaryLabel procedimentoNumCapt = new SummaryLabel("N.", null, true, true);
		SummaryLabel procedimentoNumDesc = new SummaryLabel(record.getAttribute("nroSerieTipoProc") + "/" + record.getAttribute("annoSerieTipoProc"), null, true, false);
		maskRow2.addMember(procedimentoNumCapt);
		maskRow2.addMember(procedimentoNumDesc);
		SummaryLabel procedimentoRegNumCapt = new SummaryLabel("N. registro", null, true, true);
		SummaryLabel procedimentoRegNumDesc = new SummaryLabel(record.getAttribute("nroRegistroTipoProc") + "/" + record.getAttribute("annoRegistroTipoProc"), null, true, false);
		maskRow2.addMember(procedimentoRegNumCapt);
		maskRow2.addMember(procedimentoRegNumDesc);
		lVLayout.addMember(maskRow2);
		
		HLayout maskRow3 = new HLayout();
		maskRow3.setAutoHeight();
		maskRow3.setWidth100();

		SummaryLabel avvioDataCapt = new SummaryLabel("avviato il", firstColWidth, true, true);
		SummaryLabel avvioDataDesc = new SummaryLabel(record.getAttribute("dtAvvioProc"), null, true, false);
		maskRow3.addMember(avvioDataCapt);
		maskRow3.addMember(avvioDataDesc);
		SummaryLabel avvioProtocolloNumCapt = new SummaryLabel("con prot N.", null, true, true);
		SummaryLabel avvioProtocolloNumDesc = new SummaryLabel(record.getAttribute("nroProtAvvio"), null, true, false);
		maskRow3.addMember(avvioProtocolloNumCapt);
		maskRow3.addMember(avvioProtocolloNumDesc);
		lVLayout.addMember(maskRow3);
		
		HLayout maskRow4 = new HLayout();
		maskRow4.setAutoHeight();
		maskRow4.setWidth100();

		SummaryLabel responsabileProcCapt = new SummaryLabel("Responsabile procedimento", firstColWidth, true, true);
		SummaryLabel responsabileProcDesc = new SummaryLabel(record.getAttribute("responsabileProcedimento"), null, true, false);		
		maskRow4.addMember(responsabileProcCapt);
		maskRow4.addMember(responsabileProcDesc);
		if(record != null && record.getAttributeAsRecordList("listaCambiResponsabile") != null && record.getAttributeAsRecordList("listaCambiResponsabile").getLength() > 0) {
			ButtonLabel listaCambiResponsabileButt = new ButtonLabel("buttons/altriDati.png", "Cambi responsabile procedimento");
			listaCambiResponsabileButt.addClickHandler(new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
					
					apriListaCambiPopup("listaCambiResponsabile", "Cambi responsabile procedimento", record.getAttributeAsRecordList("listaCambiResponsabile"));
				}
			});
			maskRow4.addMember(listaCambiResponsabileButt);
		}
		lVLayout.addMember(maskRow4);
		
		HLayout maskRow5 = new HLayout();
		maskRow5.setAutoHeight();
		maskRow5.setWidth100();

		SummaryLabel referenteAmmCapt = new SummaryLabel("Referente amministrativo", firstColWidth, true, true);
		SummaryLabel referenteAmmDesc = new SummaryLabel(record.getAttribute("referenteAmministrativo"), null, true, false);		
		maskRow5.addMember(referenteAmmCapt);
		maskRow5.addMember(referenteAmmDesc);
		if(record != null && record.getAttributeAsRecordList("listaCambiReferenteAmministrativo") != null && record.getAttributeAsRecordList("listaCambiReferenteAmministrativo").getLength() > 0) {
			ButtonLabel listaCambiReferenteAmministrativoButt = new ButtonLabel("buttons/altriDati.png", "Cambi referente amministrativo");
			listaCambiReferenteAmministrativoButt.addClickHandler(new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
					
					apriListaCambiPopup("listaCambiReferenteAmministrativo", "Cambi referente amministrativo", record.getAttributeAsRecordList("listaCambiReferenteAmministrativo"));
				}
			});
			maskRow5.addMember(listaCambiReferenteAmministrativoButt);			
		}
		lVLayout.addMember(maskRow5);
		
		HLayout maskRow6 = new HLayout();
		maskRow6.setAutoHeight();
		maskRow6.setWidth100();

		SummaryLabel referenteTecCapt = new SummaryLabel("Referente tecnico", firstColWidth, true, true);
		SummaryLabel referenteTecDesc = new SummaryLabel(record.getAttribute("referenteTecnico"), null, true, false);		
		maskRow6.addMember(referenteTecCapt);
		maskRow6.addMember(referenteTecDesc);
		if(record != null && record.getAttributeAsRecordList("listaCambiReferenteTecnicoButt") != null && record.getAttributeAsRecordList("listaCambiReferenteTecnicoButt").getLength() > 0) {
			ButtonLabel listaCambiReferenteTecnicoButt = new ButtonLabel("buttons/altriDati.png", "Cambi referente tecnico");
			listaCambiReferenteTecnicoButt.addClickHandler(new ClickHandler() {			
				@Override
				public void onClick(ClickEvent event) {
					
					apriListaCambiPopup("listaCambiReferenteTecnico", "Cambi referente tecnico", record.getAttributeAsRecordList("listaCambiReferenteTecnico"));
				}
			});
			maskRow6.addMember(listaCambiReferenteTecnicoButt);				
		}
		lVLayout.addMember(maskRow6);
		
		HLayout maskRow7 = new HLayout();
		maskRow7.setAutoHeight();
		maskRow7.setWidth100();

		SummaryLabel termineDataCapt = new SummaryLabel("Termine previsto entro il", firstColWidth, true, true);
		SummaryLabel termineDataDesc = new SummaryLabel(record.getAttribute("termineProcEntro"), null, true, false);
		maskRow7.addMember(termineDataCapt);
		maskRow7.addMember(termineDataDesc);
		lVLayout.addMember(maskRow7);
		
	}

	public interface GetTaskProcCallback {

	    void execute(Record data);

	}

	public void getTaskProc(String idProcess, final GetTaskProcCallback callback) {
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("GetListaAttProcFODatasource");
		if(idProcess != null && !"".equals(idProcess)) lGwtRestDataSource.addParam("idProcess", idProcess);
		lGwtRestDataSource.fetchData(null, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						RecordList data = response.getDataAsRecordList();
						if ((data != null) && !data.isEmpty()) {
							callback.execute(data.get(0));
						} 
						
					}
				} else {
					Layout.addMessage(new MessageBean("Errore durante il recupero dei task del procedimento", "", MessageType.ERROR));
				}
			}
		});				
	}
	
	public void apriListaCambiPopup(String nomeEntita, String title, RecordList listaCambi) {
				
		ListGrid grid = new ListGrid();
		grid.setWidth100();
		grid.setHeight100();  
		grid.setShowHeader(true);
		grid.setAlternateRecordStyles(false);
		grid.setLeaveScrollbarGap(false);		
		grid.setKeepInParentRect(true);
		grid.setLoadingMessage(null);
		grid.setDataFetchMode(FetchMode.LOCAL);		
		grid.setShowEmptyMessage(false);
		grid.setShowRollOver(false);		
		grid.setCanReorderFields(false);
		grid.setCanResizeFields(false);
		grid.setCanReorderRecords(false);
		grid.setCanHover(true);		
		grid.setCanGroupBy(false);	
		grid.setCanSort(false);
		grid.setShowHeaderContextMenu(false);
		grid.setNoDoubleClicks(true); 
		grid.setVirtualScrolling(true);      
		grid.setCanEdit(false);
		grid.setData(listaCambi);
		
		ListGridField nominativoField = new ListGridField("nominativo", "Nominativo");
		
		ListGridField dataDalField = new ListGridField("dataDal", "Dal");
		dataDalField.setType(ListGridFieldType.DATE);
		dataDalField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataDalField.setWidth(120);
		
		ListGridField dataAlField = new ListGridField("dataAl", "Al");
		dataAlField.setType(ListGridFieldType.DATE);
		dataAlField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataAlField.setWidth(120);
		
		grid.setFields(nominativoField, dataDalField, dataAlField);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight(250);
		lVLayout.setWidth(500);
		lVLayout.setPadding(5);
		lVLayout.setMembers(grid);
		
		Layout.addModalWindow(nomeEntita, title, "blank.png", lVLayout);		
	}
	
	public class SummaryLabel extends Label {
		
		public SummaryLabel(String testo, String width,	Boolean isVisible, Boolean isCaption) {
			if(isCaption) testo += "&nbsp;:&nbsp;";
			setContents(getTaskLabelContents(testo, isCaption));
			setHeight(20);
			setAlign(isCaption ? Alignment.RIGHT : Alignment.LEFT);
			setValign(VerticalAlignment.BOTTOM);
			setBaseStyle(isVisible ? it.eng.utility.Styles.taskLabelDisabled : it.eng.utility.Styles.taskLabel);
			setPadding(5);
			setVisible(isVisible);
			setWrap(false);
			if (width != null) {
				setWidth(width);
			} else setAutoWidth();
		}			
		
		public String getTaskLabelContents(String testo, Boolean isCaption) {
			String result = null;
			if (isCaption) {
				result  = "<div class='" + it.eng.utility.Styles.summaryLabelContents + "'>" + testo + "</div>";
			} else {				
				result  = "<div class='" + it.eng.utility.Styles.summaryLabelContentsDisabled + "'>" + testo + "</div>";
			}
			return result ;
		}
		
	}
	
	public class ButtonLabel extends Label {
		
		public ButtonLabel(String icon, String prompt) {
			setIcon(icon);
			setIconSize(16);
			setPrompt(prompt);
			setHeight(20);
			setWidth(20);	
			setBaseStyle(it.eng.utility.Styles.taskLabel);			
		}
	}
	
	private Record getTestRecord() {
		Record lRecord = new Record();
		lRecord.setAttribute("nroPratica", "12345");
		lRecord.setAttribute("annoPratica", "2010");
		lRecord.setAttribute("tipoProcedimento", "VIA");
		lRecord.setAttribute("nroSerieTipoProc", "9876");
		lRecord.setAttribute("annoSerieTipoProc", "2003");
		lRecord.setAttribute("nroRegistroTipoProc", "4567");
		lRecord.setAttribute("annoRegistroTipoProc", "2004");
		lRecord.setAttribute("dtAvvioProc", "04/03/2003");
		lRecord.setAttribute("nroProtAvvio", "4072003");
		lRecord.setAttribute("responsabileProcedimento", "Giovanni Riboldi");
		RecordList listaCambiResponsabile = new RecordList();
		Record lCambiResponsabileRecord = new Record();
		lCambiResponsabileRecord.setAttribute("nominativo", "Marco Jacopo Ravagnan");
		lCambiResponsabileRecord.setAttribute("dataDal", new Date());
		lCambiResponsabileRecord.setAttribute("dataAl", new Date());
		listaCambiResponsabile.add(lCambiResponsabileRecord);
		lRecord.setAttribute("listaCambiResponsabile", listaCambiResponsabile);
		lRecord.setAttribute("referenteAmministrativo", "Giuseppe Garibaldi");
		lRecord.setAttribute("listaCambiReferenteAmministrativo", new RecordList());		
		lRecord.setAttribute("referenteTecnico", "Erminio Diotallevi");
		lRecord.setAttribute("listaCambiReferenteTecnico", new RecordList());		
		lRecord.setAttribute("termineProcEntro", "21/06/2010");
		return lRecord;
	}		
	
}
