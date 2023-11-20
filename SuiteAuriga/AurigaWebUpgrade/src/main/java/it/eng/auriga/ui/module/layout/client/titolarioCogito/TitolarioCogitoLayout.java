/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class TitolarioCogitoLayout extends CustomLayout {

	public TitolarioCogitoLayout() {
		this(null, null, null, null);
	}

	public TitolarioCogitoLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null, null, null);
	}
	
	public TitolarioCogitoLayout(String finalita, Boolean flgSelezioneSingola,Boolean showOnlyDetail) {
		this(finalita, flgSelezioneSingola, showOnlyDetail, null, null);
	}

	public TitolarioCogitoLayout(String finalita, Boolean flgSelezioneSingola,Boolean showOnlyDetail, Record infoPrimarioEAllegatiProtocollo) {
		this(finalita, flgSelezioneSingola, showOnlyDetail, infoPrimarioEAllegatiProtocollo, null);
	}

	public TitolarioCogitoLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record infoPrimarioEAllegatiProtocollo, String uoProtocollante) {
		super("titolarioCogito", 
		      getTitolarioDataSource(infoPrimarioEAllegatiProtocollo, uoProtocollante),
			  null, 
			  new TitolarioCogitoList("titolarioCogito"), 
			  new CustomDetail("titolarioCogito"), 
			  finalita, 
			  flgSelezioneSingola,
			  showOnlyDetail);

		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}
	}

	public static boolean isAbilToIns() {		
		return false;
	}

	public static boolean isAbilToMod() {
		return false;
	}

	public static boolean isAbilToDel() {
		return false;
	}

	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}

	@Override
	public String getTipoEstremiRecord(Record record) {		
		return super.getTipoEstremiRecord(record);
	}

	@Override
	public void onSaveButtonClick() {
	}

	@Override
	public String getNewDetailTitle() {
		return "";
	}

	@Override
	public String getEditDetailTitle() {
		return "";
	}

	@Override
	public String getViewDetailTitle() {		
		return "";
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();
		newRecord.setAttribute("id", record.getAttributeAsString("idClassificazione"));
		newRecord.setAttribute("icona", "lookup/lampadina.png");
		return newRecord;
	}
	
	public static GWTRestDataSource getTitolarioDataSource(Record record, String uoProtocollante) {
		GWTRestDataSource titolarioDS = new GWTRestDataSource("TitolarioCogitoDatasource", "idClassificazione", FieldType.TEXT);
		
		// Passo al datasource l'uri del file primario
		String uriFilePrimario  = (record.getAttribute("uriFilePrimario")!=null ? record.getAttribute("uriFilePrimario") : "");
		titolarioDS.addParam("uriFilePrimario", uriFilePrimario);
		
		// Passo al datasource il flag remoteUriFilePrimario
		Boolean remoteUriFilePrimario = record.getAttribute("remoteUriFilePrimario") != null && (Boolean) record.getAttributeAsBoolean("remoteUriFilePrimario");
		titolarioDS.addParam("remoteUriFilePrimario", ((remoteUriFilePrimario == true) ? "true" : "false"));
		
		// Passo al datasource la lista degli allegati (uri|*|remote) separati da ;
		String listaAllegati  = (record.getAttribute("listaAllegati")!=null ? record.getAttribute("listaAllegati") : "");
		titolarioDS.addParam("listaAllegati", listaAllegati);

		// uo protocollante
		titolarioDS.addParam("uoProtocollante", uoProtocollante);
		
		
		return titolarioDS;
	}
	
}