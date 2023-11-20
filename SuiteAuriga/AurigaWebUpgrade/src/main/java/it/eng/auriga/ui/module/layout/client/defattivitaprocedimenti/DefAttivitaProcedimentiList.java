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

public class DefAttivitaProcedimentiList extends CustomList {

	private ListGridField idEventType;
	private ListGridField descrizione;
	private ListGridField categoria;
	private ListGridField puntualeDurativa;// 1 per durativo e 0 per puntuale
	private ListGridField tipoDocumentAssociata;
	private ListGridField flgTuttiProcedimenti;
	private ListGridField note;
	private ListGridField flgValidita;

	public DefAttivitaProcedimentiList(String nomeEntita) {
		super(nomeEntita);

		idEventType = new ListGridField("idEventType");
		idEventType.setHidden(true);
		idEventType.setCanHide(false);
		idEventType.setCanSort(false);

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().definizione_attivita_procedimenti_descrizione());

		categoria = new ListGridField("categoria", I18NUtil.getMessages().definizione_attivita_procedimenti_categoria());

		puntualeDurativa = new ListGridField("puntualeDurativa", "Puntuale/Durativa");
		puntualeDurativa.setType(ListGridFieldType.ICON);
		puntualeDurativa.setWidth(30);
		puntualeDurativa.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		puntualeDurativa.setIconWidth(16);
		puntualeDurativa.setIconHeight(16);
		Map<String, String> flgPuntualeDurativaIcons = new HashMap<String, String>();
		flgPuntualeDurativaIcons.put("true", "buttons/durativo.png");
		flgPuntualeDurativaIcons.put("false", "buttons/puntuale.png");
		puntualeDurativa.setValueIcons(flgPuntualeDurativaIcons);
		puntualeDurativa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("puntualeDurativa") != null && Boolean.valueOf(record.getAttribute("puntualeDurativa"))) {
					return "Durativa";
				} else if (record.getAttribute("puntualeDurativa") != null && !Boolean.valueOf(record.getAttribute("puntualeDurativa"))) {
					return "Puntuale";
				}
				return null;
			}
		});

		tipoDocumentAssociata = new ListGridField("tipologiaDocAss", I18NUtil.getMessages().definizione_attivita_procedimenti_tipologia_doc_ass());

		flgTuttiProcedimenti = new ListGridField("flgTuttiProcedimenti", I18NUtil.getMessages().definizione_attivita_procedimenti_in_tutti_procedimenti());
		flgTuttiProcedimenti.setType(ListGridFieldType.ICON);
		flgTuttiProcedimenti.setWidth(30);
		flgTuttiProcedimenti.setIconWidth(16);
		flgTuttiProcedimenti.setIconHeight(16);
		Map<String, String> flgTuttiProcedimentiIcons = new HashMap<String, String>();
		flgTuttiProcedimentiIcons.put("true", "ok.png");
		flgTuttiProcedimentiIcons.put("false", "blank.png");
		flgTuttiProcedimenti.setValueIcons(flgTuttiProcedimentiIcons);
		flgTuttiProcedimenti.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgTuttiProcedimenti") != null && Boolean.valueOf(record.getAttribute("flgTuttiProcedimenti"))) {
					return "Presente in tutti i procedimenti";
				}
				return null;
			}
		});

		note = new ListGridField("note", I18NUtil.getMessages().definizione_attivita_procedimenti_note());

		flgValidita = new ListGridField("flgAnnLogico", I18NUtil.getMessages().definizione_attivita_procedimenti_validita());
		flgValidita.setType(ListGridFieldType.ICON);
		flgValidita.setWidth(30);
		flgValidita.setIconWidth(16);
		flgValidita.setIconHeight(16);
		Map<String, String> flgValiditaIcons = new HashMap<String, String>();
		flgValiditaIcons.put("true", "buttons/delete2.png");
		flgValiditaIcons.put("false", "blank.png");
		flgValidita.setValueIcons(flgValiditaIcons);
		flgValidita.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flgAnnLogico") != null && Boolean.valueOf(record.getAttribute("flgAnnLogico"))) {
					return "Attività annullata";
				}
				return null;
			}
		});

		setFields(idEventType, descrizione, categoria, puntualeDurativa, tipoDocumentAssociata, flgTuttiProcedimenti, note, flgValidita);

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
		SC.ask("Sei sicuro di voler cancellare questa attività?", new BooleanCallback() {

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
	};

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		return DefAttivitaProcedimentiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return DefAttivitaProcedimentiLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgAnnLogico = record.getAttribute("flgAnnLogico") != null && record.getAttributeAsString("flgAnnLogico").equals("1");
		return DefAttivitaProcedimentiLayout.isRecordAbilToDel(flgAnnLogico);
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}