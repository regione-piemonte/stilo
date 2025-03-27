/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pubblicazioneAlbo;

import java.util.Date;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class PubblicazioneAlboRicercaPubblicazioniLayout extends CustomLayout {

	public PubblicazioneAlboRicercaPubblicazioniLayout() {
		this(null, null, null);
	}

	public PubblicazioneAlboRicercaPubblicazioniLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public PubblicazioneAlboRicercaPubblicazioniLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("pubblicazione_albo_ricerca_pubblicazioni", 
		      getDataSource(),
		      new PubblicazioneAlboRicercaPubblicazioniFilter("pubblicazione_albo_ricerca_pubblicazioni"), 
			  new PubblicazioneAlboRicercaPubblicazioniList("pubblicazione_albo_ricerca_pubblicazioni"), 
			  new PubblicazioneAlboConsultazioneRichiesteDetail("pubblicazione_albo_ricerca_pubblicazioni"), 
			  finalita,
			  flgSelezioneSingola, 
			  showOnlyDetail
			  );
		
		multiselectButton.hide();
        newButton.hide();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione")));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_detail_view_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione")));
	}

	public String getTipoEstremiRecord(Record record) {
		if(record.getAttribute("nroPubblicazione") != null && !"".equals(record.getAttribute("nroPubblicazione"))) {
			return "N. " + record.getAttribute("nroPubblicazione") + " Atto " + record.getAttribute("segnatura");
		} else {
			return "Atto " + record.getAttribute("segnatura");
		}		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if (isAbilModificaPubblicazione(detail.getValuesManager().getValues())) {
			editButton.show();
		} else {
			editButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	public boolean getDefaultMultiselect() {
		return false;
	}
	
	@Override
	public void onEditButtonClick() {
		Record record = new Record(detail.getValuesManager().getValues());
//		boolean isAbilModificaPubblicazione = record != null && record.getAttributeAsBoolean("abilModificabile") != null &&
//				record.getAttributeAsBoolean("abilModificabile") ? record.getAttributeAsBoolean("abilModificabile") : false;
		boolean isAbilModificaPubblicazione = record != null && record.getAttributeAsBoolean("abilModifica") != null &&
				record.getAttributeAsBoolean("abilModifica") ? record.getAttributeAsBoolean("abilModifica") : false;
		if(isAbilModificaPubblicazione) {
			detail.editNewRecord(record.toMap());
			newMode();
			Layout.changeTitleOfPortlet(this.nomePortlet, I18NUtil.getMessages().pubblicazione_albo_ricerca_pubblicazioni_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione"))));
		} else {
			super.onEditButtonClick();
		}
		
	}
	
	@Override
	protected void realSave(Record record) {
//		if(record != null && record.getAttributeAsBoolean("abilModificabile") != null &&
//				record.getAttributeAsBoolean("abilModificabile")) {
		if(record != null && record.getAttributeAsBoolean("abilModifica") != null &&
				record.getAttributeAsBoolean("abilModifica")) {
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = buildDSCallback();
			try {
				GWTRestDataSource lPubblicazioneAlboConsultazioneRichiesteDataSource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource");
				if (record.getAttribute("idUdFolder")==null || record.getAttribute("idUdFolder").equals("")) {
					detail.getDataSource().addData(record, callback);
				} else {
					lPubblicazioneAlboConsultazioneRichiesteDataSource.updateData(record, callback);
				}
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			super.realSave(record);
		}
		
	}
	
	public static boolean isAbilToIns() {
		return false; //Layout.isPrivilegioAttivo("PUB/RIC/INT;I");
	}

	public static boolean isAbilToMod() {
		return false; //Layout.isPrivilegioAttivo("PUB/RIC/INT;M");
	}

	public static boolean isAbilToDel() {
		return false; //Layout.isPrivilegioAttivo("PUB/RIC/INT;FC");
	}

	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}

	public static boolean isAbilModificaPubblicazione(Map record) {
//		return record != null && record.get("abilModificabile") != null &&
//				(Boolean) record.get("abilModificabile") ? true : false;
		return record != null && record.get("abilModifica") != null &&
				(Boolean) record.get("abilModifica") ? true : false;
	}
	
	private static GWTRestDataSource getDataSource() {
		GWTRestDataSource dataSource = new GWTRestDataSource("PubblicazioneAlboRicercaPubblicazioniDataSource", "idUdFolder", FieldType.TEXT);
		dataSource.addParam("interesseCessato", "I");
		return dataSource;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource gWTRestDataSource = (GWTRestDataSource) getList().getDataSource();
		gWTRestDataSource.addParam("interesseCessato", "I");
		gWTRestDataSource.setForceToShowPrompt(false);

		return gWTRestDataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		} else {
			return super.extractRecords(fields);
		}
	}

	protected String getTipoEstremiRecordProtocollazione(Record record) {
		String estremi = "";
		{
			if (record.getAttributeAsString("tipoProtocollo") != null && !"".equals(record.getAttributeAsString("tipoProtocollo"))) {
				if ("NI".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "bozza ";
				} else if ("PP".equals(record.getAttributeAsString("tipoProtocollo"))) {
					estremi += "Prot. ";
				} else {
					estremi += record.getAttributeAsString("tipoProtocollo") + " ";
				}
			}
			if (record.getAttributeAsString("siglaProtocollo") != null && !"".equals(record.getAttributeAsString("siglaProtocollo"))) {
				estremi += record.getAttributeAsString("siglaProtocollo") + " ";
			}
			if (record.getAttributeAsString("nroProtocollo") != null && !"".equals(record.getAttributeAsString("nroProtocollo"))) {
				estremi += record.getAttributeAsString("nroProtocollo") + " ";
			}
			if (record.getAttributeAsString("subProtocollo") != null && !"".equals(record.getAttributeAsString("subProtocollo"))) {
				estremi += "sub " + record.getAttributeAsString("subProtocollo") + " ";
			}
			if (record.getAttributeAsDate("dataProtocollo") != null) {
				estremi += "del " + DateUtil.format((Date) record.getAttributeAsDate("dataProtocollo"));
			}
		}
		return estremi;
	}
}
