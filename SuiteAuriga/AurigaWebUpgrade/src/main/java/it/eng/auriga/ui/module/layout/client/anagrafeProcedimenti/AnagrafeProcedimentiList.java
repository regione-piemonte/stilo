/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class AnagrafeProcedimentiList extends CustomList {

	private ListGridField id;
	private ListGridField nome;
	private ListGridField descrizione;
	private ListGridField durataMax;
	private ListGridField dtInizioVld;
	private ListGridField dtFineVld;
	// private ListGridField uoCompetente;
	// private ListGridField responsabile;
	private ListGridField iniziativa;
	private ListGridField rifNormativi;
	private ListGridField flgSospendibile;
	private ListGridField nroMaxSospensioni;
	private ListGridField flgInterrompibile;
	private ListGridField nroMaxGGXInterr;
	private ListGridField flgPartiEsterne;
	private ListGridField flgSilenzioAssenso;

	private ControlListGridField listaProcessiButtonField;

	public AnagrafeProcedimentiList(String nomeEntita) {

		super(nomeEntita);

		id = new ListGridField("id", I18NUtil.getMessages().anagrafe_procedimenti_id());

		nome = new ListGridField("nome", I18NUtil.getMessages().anagrafe_procedimenti_nome());

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().anagrafe_procedimenti_descrizione());

		durataMax = new ListGridField("durataMax", I18NUtil.getMessages().anagrafe_procedimenti_duratamax());

		// uoCompetente = new ListGridField("uoCompetente", I18NUtil.getMessages().anagrafe_procedimenti_UOCompetente());

		// responsabile = new ListGridField("responsabile", I18NUtil.getMessages().anagrafe_procedimenti_responsabile());

		dtInizioVld = new ListGridField("dtInizioVld", "Data inizio validita'");
		dtInizioVld.setType(ListGridFieldType.DATE);

		dtFineVld = new ListGridField("dtFineVld", "Data fine validita'");
		dtFineVld.setType(ListGridFieldType.DATE);

		iniziativa = new ListGridField("iniziativa", I18NUtil.getMessages().anagrafe_procedimenti_iniziativa());
		iniziativa.setType(ListGridFieldType.ICON);
		iniziativa.setWidth(30);
		iniziativa.setIconWidth(16);
		iniziativa.setIconHeight(16);
		Map<String, String> flginiziativaIcons = new HashMap<String, String>();
		flginiziativaIcons.put("di Parte", "buttons/icon_iniziativa.png");
		flginiziativaIcons.put("d'Ufficio", "buttons/icon_ufficio.png");
		iniziativa.setValueIcons(flginiziativaIcons);
		iniziativa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("iniziativa");
			}
		});

		rifNormativi = new ListGridField("rifNormativi", I18NUtil.getMessages().anagrafe_procedimenti_Rif_normativi());

		flgSospendibile = new ListGridField("flgSospendibile", I18NUtil.getMessages().anagrafe_procedimenti_Sospensioni());
		flgSospendibile.setType(ListGridFieldType.ICON);
		flgSospendibile.setWidth(30);
		flgSospendibile.setIconWidth(16);
		flgSospendibile.setIconHeight(16);
		Map<String, String> flgSospensioniIcons = new HashMap<String, String>();
		flgSospensioniIcons.put("false", "blank.png");
		flgSospensioniIcons.put("true", "ok.png");
		flgSospendibile.setValueIcons(flgSospensioniIcons);
		flgSospendibile.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record.getAttribute("flgSospendibile") != null && Boolean.valueOf(record.getAttribute("flgSospendibile"))) {
					return "Ammesse sospensioni";
				} else if (record.getAttribute("flgSospendibile") != null && !Boolean.valueOf(record.getAttribute("flgSospendibile"))) {
					return "";
				}
				return null;
			}
		});

		nroMaxSospensioni = new ListGridField("nroMaxSospensioni", I18NUtil.getMessages().anagrafe_procedimenti_N_max_sospensioni());

		flgInterrompibile = new ListGridField("flgInterrompibile", I18NUtil.getMessages().anagrafe_procedimenti_Interruzione());
		flgInterrompibile.setType(ListGridFieldType.ICON);
		flgInterrompibile.setWidth(30);
		flgInterrompibile.setIconWidth(16);
		flgInterrompibile.setIconHeight(16);
		Map<String, String> flgInterruzioneIcons = new HashMap<String, String>();
		flgInterruzioneIcons.put("false", "blank.png");
		flgInterruzioneIcons.put("true", "ok.png");
		flgInterrompibile.setValueIcons(flgInterruzioneIcons);
		flgInterrompibile.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record.getAttribute("flgInterrompibile") != null && Boolean.valueOf(record.getAttribute("flgInterrompibile"))) {
					return "Ammessa interruzione termini";
				}
				return null;
			}
		});

		nroMaxGGXInterr = new ListGridField("nroMaxGGXInterr", I18NUtil.getMessages().anagrafe_procedimenti_Interruzione_entro_gg());

		flgPartiEsterne = new ListGridField("flgPartiEsterne", I18NUtil.getMessages().anagrafe_procedimenti_Fasi_esterne());
		flgPartiEsterne.setType(ListGridFieldType.ICON);
		flgPartiEsterne.setWidth(30);
		flgPartiEsterne.setIconWidth(16);
		flgPartiEsterne.setIconHeight(16);
		Map<String, String> flgFasiEsterneIcons = new HashMap<String, String>();
		flgFasiEsterneIcons.put("false", "blank.png");
		flgFasiEsterneIcons.put("true", "ok.png");
		flgPartiEsterne.setValueIcons(flgFasiEsterneIcons);
		flgPartiEsterne.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record.getAttribute("flgPartiEsterne") != null && Boolean.valueOf(record.getAttribute("flgPartiEsterne"))) {
					return "Previste fasi esterne";
				}
				return null;
			}
		});

		flgSilenzioAssenso = new ListGridField("flgSilenzioAssenso", I18NUtil.getMessages().anagrafe_procedimenti_Silenzio_assenso());
		flgSilenzioAssenso.setType(ListGridFieldType.ICON);
		flgSilenzioAssenso.setWidth(30);
		flgSilenzioAssenso.setIconWidth(16);
		flgSilenzioAssenso.setIconHeight(16);
		Map<String, String> flgSilenzioAssensoIcons = new HashMap<String, String>();
		flgSilenzioAssensoIcons.put("false", "blank.png");
		flgSilenzioAssensoIcons.put("true", "ok.png");
		flgSilenzioAssenso.setValueIcons(flgSilenzioAssensoIcons);
		flgSilenzioAssenso.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record.getAttribute("flgSilenzioAssenso") != null && Boolean.valueOf(record.getAttribute("flgSilenzioAssenso"))) {
					return "Soggetto a silenzio-assenso dopo giorni: " + record.getAttribute("dopoGiorni");
				}
				return null;
			}
		});

		setFields(id, nome, descrizione, durataMax, /* uoCompetente, responsabile, */iniziativa, rifNormativi, flgSospendibile, nroMaxSospensioni, dtInizioVld,
				dtFineVld, flgInterrompibile, nroMaxGGXInterr, flgPartiEsterne, flgSilenzioAssenso);
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)));
		}
	}

	public void showRowContextMenu(final ListGridRecord record) {
		final Menu contextMenu = createContextMenu(record);
		contextMenu.showContextMenu();
	}

	public Menu createContextMenu(final ListGridRecord listRecord) {

		Menu contextMenu = new Menu();

		MenuItem scadenzeConfigurateItem = new MenuItem("Scadenze configurate", "buttons/scadenze.png");
		scadenzeConfigurateItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ScadenzeConfigurateDataSource", "rowId", FieldType.TEXT);
				ScadenzeConfiguratePopup scadenzeConfiguratePopup = new ScadenzeConfiguratePopup(lGWTRestDataSource, listRecord.getAttribute("id"), listRecord
						.getAttribute("nome")) {

					@Override
					public void manageOnCloseClick() {
						super.manageOnCloseClick();
						layout.doSearch();
					}
				};
				scadenzeConfiguratePopup.show();
			}
		});
		contextMenu.addItem(scadenzeConfigurateItem);

		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));
		return contextMenu;
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();

		if (listaProcessiButtonField == null) {
			listaProcessiButtonField = buildListaProcessiButtonField();
		}
		buttonsFields.add(listaProcessiButtonField);

		return buttonsFields;
	}

	protected ControlListGridField buildListaProcessiButtonField() {
		ControlListGridField listaProcessiButtonField = new ControlListGridField("listaProcessi");
		listaProcessiButtonField.setAttribute("custom", true);
		listaProcessiButtonField.setShowHover(true);
		listaProcessiButtonField.setCanReorder(false);
		listaProcessiButtonField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				return buildImgButtonHtml("menu/def_attivita_proc.png");
			}
		});
		listaProcessiButtonField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return "Task associati";
			}
		});
		listaProcessiButtonField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());

				manageListaProcessiButtonClick(record);

			}
		});
		return listaProcessiButtonField;
	}

	private void manageListaProcessiButtonClick(Record record) {
		String idProcessTypeIO = record.getAttribute("id");
		String nomeProcessTypeIO = record.getAttribute("nome");
		RelEventTypeProcessPopup lRelEventTypeProcessPopup = new RelEventTypeProcessPopup(idProcessTypeIO, nomeProcessTypeIO);
		lRelEventTypeProcessPopup.show();
	}

	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return AnagrafeProcedimentiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return AnagrafeProcedimentiLayout.isAbilToDel();
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}