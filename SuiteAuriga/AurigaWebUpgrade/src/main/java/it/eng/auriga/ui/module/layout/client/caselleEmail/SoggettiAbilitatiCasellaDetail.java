/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LegendaDinamicaPanel;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;

public class SoggettiAbilitatiCasellaDetail extends CustomDetail {

	protected DynamicForm form;
	protected DynamicForm formFruitoriCasella;
	protected DynamicForm formUtentiCasella;

	protected HiddenItem idCasellaItem;
	protected HiddenItem indirizzoEmailItem;
	protected SelectItem dominioItem;
	protected HiddenItem idSpAooItem;
	protected FruitoriCasellaItem fruitoriCasellaItem;
	protected UtentiCasellaItem utentiCasellaItem;

	protected Record recordOrig;

	private DynamicForm formImage;

	public SoggettiAbilitatiCasellaDetail(String nomeEntita) {

		super(nomeEntita);

		recordOrig = null;

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(7);
		form.setColWidths("1", "1", "1", "1", "1", "1", "*");

		idCasellaItem = new HiddenItem("idCasella");
		indirizzoEmailItem = new HiddenItem("indirizzoEmail");

		final GWTRestDataSource dominiDS = new GWTRestDataSource("LoadComboEnteAOOCasellaDataSource", "key", FieldType.TEXT);
		// SELEZIONA TIPO ORGANIGRAMMA
		dominioItem = new SelectItem("dominio", I18NUtil.getMessages().caselleEmail_detail_dominioItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				form.setValue("idSpAoo", record.getAttribute("key"));
			}
		};
		dominioItem.setValueField("key");
		dominioItem.setDisplayField("value");
		dominioItem.setOptionDataSource(dominiDS);
		dominioItem.setCachePickListResults(false);
		dominioItem.setStartRow(true);
		dominioItem.setClearable(false);
		dominioItem.setAttribute("obbligatorio", true);
		// dominioItem.setVisible(false);
		dominioItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				RecordList data = event.getData();
				if (data != null && data.getLength() == 1) {
					Record record = data.get(0);
					form.setValue("idSpAoo", record.getAttribute("key"));
					dominioItem.hide();
				} else {
					dominioItem.show();
				}
			};
		});
		dominioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				
				String idSpAooOrig = recordOrig != null ? recordOrig.getAttribute("idSpAoo") : null;
				if (event.getValue() != null && idSpAooOrig != null) {
					if (event.getValue().equals(idSpAooOrig)) {
						RecordList listaFruitoriCasellaOrig = recordOrig != null ? recordOrig.getAttributeAsRecordList("listaFruitoriCasella") : null;
						Record lRecordFruitoriCasella = new Record();
						lRecordFruitoriCasella.setAttribute("listaFruitoriCasella", listaFruitoriCasellaOrig);
						formFruitoriCasella.editRecord(lRecordFruitoriCasella);
						RecordList listaUtentiCasellaOrig = recordOrig != null ? recordOrig.getAttributeAsRecordList("listaUtentiCasella") : null;
						Record lRecordUtentiCasella = new Record();
						lRecordUtentiCasella.setAttribute("listaUtentiCasella", listaUtentiCasellaOrig);
						formUtentiCasella.editRecord(lRecordUtentiCasella);
					} else {
						Record lRecordFruitoriCasella = new Record();
						lRecordFruitoriCasella.setAttribute("listaFruitoriCasella", new RecordList());
						formFruitoriCasella.editRecord(lRecordFruitoriCasella);
						Record lRecordUtentiCasella = new Record();
						lRecordUtentiCasella.setAttribute("listaUtentiCasella", new RecordList());
						formUtentiCasella.editRecord(lRecordUtentiCasella);

					}
				}
			}
		});

		idSpAooItem = new HiddenItem("idSpAoo");

		form.setItems(idCasellaItem, indirizzoEmailItem, dominioItem, idSpAooItem);

		formFruitoriCasella = new DynamicForm();
		formFruitoriCasella.setValuesManager(vm);
		formFruitoriCasella.setWidth("100%");
		formFruitoriCasella.setHeight("5");
		formFruitoriCasella.setPadding(5);
		formFruitoriCasella.setWrapItemTitles(false);
		formFruitoriCasella.setNumCols(12);

		fruitoriCasellaItem = new FruitoriCasellaItem();
		fruitoriCasellaItem.setName("listaFruitoriCasella");
		fruitoriCasellaItem.setShowTitle(false);
		fruitoriCasellaItem.setCanEdit(true);

		formFruitoriCasella.setItems(fruitoriCasellaItem);

		formUtentiCasella = new DynamicForm();
		formUtentiCasella.setValuesManager(vm);
		formUtentiCasella.setWidth("100%");
		formUtentiCasella.setHeight("5");
		formUtentiCasella.setPadding(5);
		formUtentiCasella.setWrapItemTitles(false);
		formUtentiCasella.setNumCols(12);

		utentiCasellaItem = new UtentiCasellaItem();
		utentiCasellaItem.setName("listaUtentiCasella");
		utentiCasellaItem.setShowTitle(false);
		utentiCasellaItem.setCanEdit(true);

		formUtentiCasella.setItems(utentiCasellaItem);

		DetailSection detailSectionFruitoriCasella = new DetailSection("AOO/U.O.", true, true, false, formFruitoriCasella);
		DetailSection detailSectionUtentiCasella = new DetailSection("Utenti", true, true, false, formUtentiCasella);

		/*
		 * LAYOUT
		 */

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_LEGENDA_DIN_TIPO_UO")) {
				LegendaDinamicaPanel leg = new LegendaDinamicaPanel();
				lVLayout.addMember(leg);
			} else {
				buildLegendImageUO();
				lVLayout.addMember(formImage);
			}
		}

		lVLayout.addMember(form);
		lVLayout.addMember(detailSectionFruitoriCasella);
		lVLayout.addMember(detailSectionUtentiCasella);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

		markForRedraw();

	}

	private void buildLegendImageUO() {
		formImage = new DynamicForm();
		formImage.setKeepInParentRect(true);
		formImage.setCellPadding(5);
		formImage.setWrapItemTitles(false);

		StaticTextItem tipoUOImage = new StaticTextItem("iconaStatoConsolidamento");
		tipoUOImage.setShowValueIconOnly(true);
		tipoUOImage.setShowTitle(false);
		tipoUOImage.setValueIconWidth(600);
		tipoUOImage.setValueIconHeight(60);
		tipoUOImage.setAlign(Alignment.LEFT);
		Map<String, String> valueIcons = new HashMap<String, String>();
		valueIcons.put("1", "organigramma/legenda_uo.png");
		tipoUOImage.setValueIcons(valueIcons);
		tipoUOImage.setDefaultValue("1");
		tipoUOImage.setDefaultIconSrc("organigramma/legenda_uo.png");

		formImage.setItems(tipoUOImage);
	}

	@Override
	public void editRecord(Record record) {
		
		recordOrig = record;
		record.setAttribute("dominio", record.getAttribute("idSpAoo"));
		super.editRecord(record);
	}

}
