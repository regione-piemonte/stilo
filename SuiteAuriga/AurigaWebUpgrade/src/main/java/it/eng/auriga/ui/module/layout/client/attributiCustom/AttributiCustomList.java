/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class AttributiCustomList extends CustomList {

	private ListGridField nome;
	private ListGridField etichetta;
	private ListGridField tipo;
	private ListGridField descrizione;
	private ListGridField appartenenza;
	private ListGridField valido;

	public AttributiCustomList(String nomeEntita) {

		super(nomeEntita);

		nome = new ListGridField("nome", I18NUtil.getMessages().attributi_custom_nome());

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().attributi_custom_descrizione());

		etichetta = new ListGridField("etichetta", I18NUtil.getMessages().attributi_custom_etichetta());

		tipo = new ListGridField("tipo", I18NUtil.getMessages().attributi_custom_tipo());

		appartenenza = new ListGridField("appartenenza", I18NUtil.getMessages().attributi_custom_appartenente());

		valido = new ListGridField("valido", I18NUtil.getMessages().attributi_custom_valido());
		valido.setType(ListGridFieldType.ICON);
		valido.setWidth(30);
		valido.setIconWidth(16);
		valido.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();
		flgValidoValueIcons.put("0", "buttons/grant.png");
		valido.setValueIcons(flgValidoValueIcons);
		valido.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("0".equals(record.getAttribute("valido"))) {
					return "Valido";
				}
				return null;
			}
		});

		setFields(nome, etichetta, tipo, descrizione, appartenenza, valido);
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
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (isRecordSelezionabileForLookup(record)) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		if(layout != null && layout instanceof AttributiCustomLayout) {
			return ((AttributiCustomLayout)layout).isRecordSelezionabileForLookup(record);
		} else {
			return record.getAttributeAsString("appartenenza") == null || "".equalsIgnoreCase(record.getAttributeAsString("appartenenza"));			
		}			
	}

	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {

		SC.ask("Sei sicuro di voler cancellare questo attributo?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {

				if (value) {
					removeData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {

							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "",
										MessageType.INFO));
								layout.hideDetail(true);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "",
										MessageType.ERROR));
							}
						}
					});
				}
			}
		});
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {

		return true;
	}

	@Override
	protected boolean showDetailButtonField() {

		return true;
	}

	@Override
	protected boolean showModifyButtonField() {

		return AttributiCustomLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {

		return AttributiCustomLayout.isAbilToDel();
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}