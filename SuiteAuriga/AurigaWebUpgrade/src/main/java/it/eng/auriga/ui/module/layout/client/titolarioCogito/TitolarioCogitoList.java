/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class TitolarioCogitoList extends CustomList {

	private ListGridField score;
	
	public TitolarioCogitoList(final String nomeEntita) {

		super(nomeEntita);

		// Colonne hidden 
		ListGridField idClassificazione = new ListGridField("idClassificazione"); idClassificazione.setHidden(true); idClassificazione.setCanHide(false);
		ListGridField tipo              = new ListGridField("tipo");              tipo.setHidden(true);             tipo.setCanHide(false);
		ListGridField idClassificaSup   = new ListGridField("idClassificaSup");   idClassificaSup.setHidden(true);  idClassificaSup.setCanHide(false);
		ListGridField desClassificaSup  = new ListGridField("desClassificaSup");  desClassificaSup.setHidden(true); desClassificaSup.setCanHide(false);
		ListGridField flgSelXFinalita   = new ListGridField("flgSelXFinalita");   flgSelXFinalita.setHidden(true);  flgSelXFinalita.setCanHide(false);
		ListGridField indice            = new ListGridField("indice");            indice.setHidden(true);           indice.setCanHide(false);
		ListGridField idCogitoLogIO     = new ListGridField("idCogitoLogIO");	  idCogitoLogIO.setHidden(true);	idCogitoLogIO.setCanHide(false);
		
		// Colonne visibili
		ListGridField folderUp = new ControlListGridField("folderUp"); folderUp.setAttribute("custom", true); folderUp.setShowHover(true);
		folderUp.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					return buildImgButtonHtml("buttons/folderUp.png");
				}
				return null;
			}
		});
		folderUp.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					return I18NUtil.getMessages().titolario_list_folderUpButton_prompt() + " " + record.getAttribute("desClassificaSup");
				}
				return null;
			}
		});
		folderUp.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					if (layout instanceof TitolarioCogitoLayout) {
					//	((TitolarioCogitoLayout) layout).esploraFromList((record.getAttribute("idClassificaSup")));
					}
				}
			}
		});

		ListGridField icona = new ControlListGridField("icona"); icona.setAttribute("custom", true); icona.setShowHover(true);
		icona.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (layout instanceof TitolarioCogitoLayout) {
					return buildImgButtonHtml(nomeEntita + "/tipo/" + record.getAttributeAsString("nroLivello") + ".png");
				}
				return buildIconHtml(nomeEntita + "/tipo/" + record.getAttributeAsString("tipo") + ".png");
			}
		});
		icona.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (layout instanceof TitolarioCogitoLayout) {
					return I18NUtil.getMessages().titolario_list_iconaFolderButton_prompt();
				}
				return null;
			}
		});
		icona.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (layout instanceof TitolarioCogitoLayout) {
					//((TitolarioCogitoLayout) layout).esploraFromList((record.getAttribute("idClassificazione")));
				}
			}
		});

		
		
		ListGridField descrizione       = new ListGridField("descrizione",       I18NUtil.getMessages().titolario_list_descrizioneField_title());
		ListGridField descrizioneEstesa = new ListGridField("descrizioneEstesa", I18NUtil.getMessages().titolario_list_descrizioneEstesaField_title());
		ListGridField paroleChiave      = new ListGridField("paroleChiave",      I18NUtil.getMessages().titolario_list_paroleChiaveField_title());
		ListGridField indiceXOrd        = new ListGridField("indiceXOrd",        I18NUtil.getMessages().titolario_list_indiceField_title()); indiceXOrd.setDisplayField("indice"); indiceXOrd.setSortByDisplayField(false);
		ListGridField tsValidaDal       = new ListGridField("tsValidaDal",       I18NUtil.getMessages().titolario_list_tsValidaDalField_title()); tsValidaDal.setType(ListGridFieldType.DATE); tsValidaDal.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); tsValidaDal.setWrap(false);
		ListGridField tsValidaFinoAl    = new ListGridField("tsValidaFinoAl",    I18NUtil.getMessages().titolario_list_tsValidaFinoAlField_title()); tsValidaFinoAl.setType(ListGridFieldType.DATE); tsValidaFinoAl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE); tsValidaFinoAl.setWrap(false);
		
		score = new ListGridField("score", I18NUtil.getMessages().titolario_list_scoreField_title());score.setType(ListGridFieldType.INTEGER); score.setSortByDisplayField(false);
		score.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if (score != null) {
					String res = "";
					for (int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});

		ListGridField periodoConservAnni = new ListGridField("periodoConservAnni", I18NUtil.getMessages().titolario_list_periodoConservAnni());

		setFields(
				  // Colonne hidden
				  idClassificazione,
				  tipo,
				  idClassificaSup,
				  desClassificaSup,
				  flgSelXFinalita,
				  indice,
				  
				  // Colonne visibili
				  folderUp, 
				  icona, 
				  descrizione, 
				  descrizioneEstesa, 
				  paroleChiave, 
				  indiceXOrd, 
				  tsValidaDal, 
				  tsValidaFinoAl,
				  score, 
				  periodoConservAnni);

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 50;
	}

	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
					String parole = (String) value.get("parole");
					if (parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		if (isFulltextSearch) {
			score.setHidden(false);
		} else {
			score.setHidden(true);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
	}
	
	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton lookupButton = buildLookupButton(record);

			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);

			if (layout.isLookup()) {
				if (!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton); // aggiungo il bottone SELEZIONA
			}

			lCanvasReturn = recordCanvas;

		}

		return lCanvasReturn;
	}

	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	protected boolean showModifyButtonField() {
		return TitolarioCogitoLayout.isAbilToMod();
	}

	protected boolean showDeleteButtonField() {
		return TitolarioCogitoLayout.isAbilToDel();
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	@Override
	protected void manageLookupButtonClick(final ListGridRecord record) {
		super.manageLookupButtonClick(record);
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("TitolarioCogitoDatasource");
		Record recInput = new Record ();
		recInput.setAttribute("nriLivelliSceltaIn",record.getAttribute("indice"));
		recInput.setAttribute("idCogitoLogIO",record.getAttribute("idCogitoLogIO"));
		lGwtRestDataSourceProtocollo.performCustomOperation("tracciaSceltaDaCogito", recInput);
	}
	
}
