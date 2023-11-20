/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

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

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class RelEventTypeProcessList extends CustomList {

	private ListGridField idEventTypeIn;
	private ListGridField desEventTypeIn;
	private ListGridField flgDurativoIO;
	private ListGridField categoriaIO;
	private ListGridField idDocTypeIO;
	private ListGridField nomeDocTypeIO;
	private ListGridField flgVldXTuttiProcAmmIO;

	public RelEventTypeProcessList(String nomeEntita) {
		super(nomeEntita);

		idEventTypeIn = new ListGridField("idEventTypeIn", "Id tipo evento");
		idEventTypeIn.setHidden(false);
		idEventTypeIn.setCanHide(false);

		desEventTypeIn = new ListGridField("desEventTypeIn", "Descrizione tipo evento");

		flgDurativoIO = new ListGridField("flgDurativoIO", "Puntuale/Durativa");
		flgDurativoIO.setType(ListGridFieldType.ICON);
		flgDurativoIO.setWidth(30);
		flgDurativoIO.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgDurativoIO.setIconWidth(16);
		flgDurativoIO.setIconHeight(16);
		Map<String, String> flgPuntualeDurativaIcons = new HashMap<String, String>();
		flgPuntualeDurativaIcons.put("true", "buttons/durativo.png");
		flgPuntualeDurativaIcons.put("false", "buttons/puntuale.png");
		flgDurativoIO.setValueIcons(flgPuntualeDurativaIcons);
		flgDurativoIO.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgDurativoIO") != null && Boolean.valueOf(record.getAttribute("flgDurativoIO"))) {
					return "Durativa";
				} else if (record.getAttribute("flgDurativoIO") != null && !Boolean.valueOf(record.getAttribute("flgDurativoIO"))) {
					return "Puntuale";
				}
				return null;
			}
		});

		categoriaIO = new ListGridField("categoriaIO", "Categoria");

		idDocTypeIO = new ListGridField("idEventTypeIn");
		idDocTypeIO.setHidden(true);
		idDocTypeIO.setCanHide(true);

		nomeDocTypeIO = new ListGridField("nomeDocTypeIO", "Nome del tipo di documento associato");

		flgVldXTuttiProcAmmIO = new ListGridField("flgVldXTuttiProcAmmIO", I18NUtil.getMessages().definizione_attivita_procedimenti_in_tutti_procedimenti());
		flgVldXTuttiProcAmmIO.setType(ListGridFieldType.ICON);
		flgVldXTuttiProcAmmIO.setWidth(30);
		flgVldXTuttiProcAmmIO.setIconWidth(16);
		flgVldXTuttiProcAmmIO.setIconHeight(16);
		Map<String, String> flgTuttiProcedimentiIcons = new HashMap<String, String>();
		flgTuttiProcedimentiIcons.put("true", "ok.png");
		flgTuttiProcedimentiIcons.put("false", "blank.png");
		flgVldXTuttiProcAmmIO.setValueIcons(flgTuttiProcedimentiIcons);
		flgVldXTuttiProcAmmIO.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if (record.getAttribute("flgVldXTuttiProcAmmIO") != null && Boolean.valueOf(record.getAttribute("flgVldXTuttiProcAmmIO"))) {
					return "Presente in tutti i procedimenti";
				}
				return null;
			}
		});

		setFields(idEventTypeIn, desEventTypeIn, flgDurativoIO, categoriaIO, idDocTypeIO, nomeDocTypeIO, flgVldXTuttiProcAmmIO);

		setAutoFetchData(false);

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
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		
		removeData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
					layout.hideDetail(true);
					layout.doSearch();
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "",	MessageType.ERROR));
				}
			}
		});
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return RelEventTypeProcessLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RelEventTypeProcessLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgRecordNonCancellabile = record.getAttribute("flgVldXTuttiProcAmmIO") != null && record.getAttributeAsBoolean("flgVldXTuttiProcAmmIO");
		return RelEventTypeProcessLayout.isRecordAbilToDel(flgRecordNonCancellabile);
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

}
