/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class EmailCollegateList extends CustomList {

	private ListGridField idEmailField;
	private ListGridField tipoRelField;
	private ListGridField accountMittenteField;
	private ListGridField oggettoField;
	private ListGridField destinatariField;
	private ListGridField destinatariCCField;
	private ListGridField destinatariCCNField;
	private ListGridField tsInvioField;
	private ListGridField tsRicezioneField;

	public EmailCollegateList(String nomeEntita, String tipoRel) {

		super(nomeEntita);

		idEmailField = new ListGridField("idEmail");
		idEmailField.setHidden(true);

		tipoRelField = new ListGridField("tipoRel");
		tipoRelField.setHidden(true);

		accountMittenteField = new ListGridField("accountMittente", I18NUtil.getMessages().postaElettronica_list_accountMittenteField_title());
		accountMittenteField.setWrap(false);
		accountMittenteField.setHidden(false);

		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().postaElettronica_list_oggettoField_title());
		oggettoField.setWrap(false);
		oggettoField.setHidden(false);

		destinatariField = new ListGridField("destinatariPrincipali", I18NUtil.getMessages().postaElettronica_list_destinatariField_title());
		destinatariField.setAttribute("custom", true);
		destinatariField.setWrap(false);
		destinatariField.setHidden(false);
		destinatariField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaLista(value);
			}
		});

		destinatariCCField = new ListGridField("destinatariCC", I18NUtil.getMessages().postaElettronica_list_destinatariCCField_title());
		destinatariCCField.setAttribute("custom", true);
		destinatariCCField.setWrap(false);
		destinatariCCField.setHidden(false);
		destinatariCCField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaLista(value);
			}
		});

		destinatariCCNField = new ListGridField("destinatariCCN", I18NUtil.getMessages().postaElettronica_list_destinatariCCNField_title());
		destinatariCCNField.setAttribute("custom", true);
		destinatariCCNField.setWrap(false);
		destinatariCCNField.setHidden(false);
		destinatariCCNField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaLista(value);
			}
		});

		tsInvioField = new ListGridField("tsInvio", I18NUtil.getMessages().postaElettronica_list_tsInvioClientField_title());
		tsInvioField.setType(ListGridFieldType.DATE);
		tsInvioField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		tsRicezioneField = new ListGridField("tsRicezione", I18NUtil.getMessages().postaElettronica_list_tsRicezioneField_title());
		tsRicezioneField.setType(ListGridFieldType.DATE);
		tsRicezioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		setFields(new ListGridField[] { idEmailField, tipoRelField, accountMittenteField, oggettoField, destinatariField, destinatariCCField,
				destinatariCCNField, tsInvioField });
	}

	protected String trasformaLista(Object value) {
		if (value == null)
			return "";
		RecordList lList = new RecordList((JavaScriptObject) value);
		StringBuffer lStringBuffer = new StringBuffer();
		for (int i = 0; i < lList.getLength(); i++) {
			Record lRecord = lList.get(i);
			if (i > 0)
				lStringBuffer.append(", ");
			lStringBuffer.append(lRecord.getAttribute("account"));
		}
		return lStringBuffer.toString();
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected void manageDetailButtonClick(ListGridRecord record) {
		
		String idEmail = record.getAttributeAsString("idEmail");
		String tipoRel = record.getAttributeAsString("tipoRel");
		DettaglioEmailWindow lDettaglioEmailWindow = new DettaglioEmailWindow(idEmail, tipoRel, getLayout());
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return false;
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return false;
	}

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
}
