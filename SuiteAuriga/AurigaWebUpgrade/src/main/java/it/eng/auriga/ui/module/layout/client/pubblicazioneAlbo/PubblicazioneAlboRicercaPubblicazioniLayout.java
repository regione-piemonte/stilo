/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
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
		if (isAbilToMod()) {
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