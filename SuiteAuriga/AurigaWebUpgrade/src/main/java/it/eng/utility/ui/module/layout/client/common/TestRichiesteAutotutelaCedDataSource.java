/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.JSOHelper;

public class TestRichiesteAutotutelaCedDataSource extends RestDataSource {

	public TestRichiesteAutotutelaCedDataSource() {
		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		// fetch.setDataProtocol(DSProtocol.POSTMESSAGE);
		fetch.setDataProtocol(DSProtocol.GETPARAMS);
		DSRequest requestProperties = new DSRequest();
		requestProperties.setHttpMethod("GET");
		requestProperties.setUseSimpleHttp(true);
		requestProperties.setWillHandleError(true);
		fetch.setRequestProperties(requestProperties);
		setOperationBindings(fetch);

		setDataFormat(DSDataFormat.JSON);
		setJsonRecordXPath("/listaRichiesteAutotutelaCed");

		DataSourceTextField idRichiestaField = new DataSourceTextField("idRichiesta");
		idRichiestaField.setPrimaryKey(true);
		idRichiestaField.setCanEdit(false);
		idRichiestaField.setHidden(true);

		DataSourceTextField codiceTipoRichiestaField = new DataSourceTextField("codiceTipoRichiesta");
		codiceTipoRichiestaField.setCanEdit(false);
		codiceTipoRichiestaField.setHidden(true);

		DataSourceTextField descrizioneStatoRichiestaField = new DataSourceTextField("descrizioneStatoRichiesta", "Stato");

		DataSourceTextField codiceStatoRichiestaField = new DataSourceTextField("codiceStatoRichiesta");
		codiceStatoRichiestaField.setCanEdit(false);
		codiceStatoRichiestaField.setHidden(true);

		DataSourceTextField descrizioneTipoRichiestaField = new DataSourceTextField("descrizioneTipoRichiesta", "Tipo");

		DataSourceTextField codicefiscaleUtenteField = new DataSourceTextField("codicefiscaleUtente", "Cod. fiscale");
		DataSourceTextField numeroDocumentoField = new DataSourceTextField("numeroDocumento", "N. doc.");
		DataSourceTextField numeroProtocolloField = new DataSourceTextField("numeroProtocollo", "N. prot.");
		DataSourceTextField dataRichiestaField = new DataSourceTextField("dataRichiesta", "Data richiesta");
		DataSourceTextField dataCambioStatoField = new DataSourceTextField("dataCambioStato", "Data cambio stato");
		DataSourceTextField flagLettoField = new DataSourceTextField("flagLetto", "Letto");
		DataSourceTextField idEnteField = new DataSourceTextField("idEnte", "Ente");

		setFields(idRichiestaField, codiceTipoRichiestaField, descrizioneTipoRichiestaField, codiceStatoRichiestaField, descrizioneStatoRichiestaField,
				codicefiscaleUtenteField, numeroDocumentoField, numeroProtocolloField, dataRichiestaField, dataCambioStatoField, flagLettoField, idEnteField);

		setFetchDataURL("http://localhost:8080/AurigaWeb/json/GetListaRichiesteAutotutelaCed.json");
		// setFetchDataURL("http://gericolmi.tributi.eng.it/WebApiCartellaCittadino/api/Service/GetListaRichiesteAutotutelaCed?tipoRichiesta=ATTTL&flagLetto=&dataRichiesta=");
	}

	@Override
	protected Object transformRequest(DSRequest request) {
		return super.transformRequest(request);
	}

	@Override
	protected void transformResponse(DSResponse response, DSRequest request, Object data) {
//		System.out.println("REQUEST");
//		for (String attr : request.getAttributes()) {
//			System.out.println(attr + " = " + request.getAttribute(attr));
//		}
//		System.out.println("RESPONSE");
//		for (String attr : response.getAttributes()) {
//			System.out.println(attr + " = " + response.getAttribute(attr));
//		}
//		System.out.println("DATA");
//		for (String prop : JSOHelper.getProperties((JavaScriptObject) data)) {
//			System.out.println(prop + " = " + JSOHelper.getAttribute((JavaScriptObject) data, prop));
//		}
		super.transformResponse(response, request, data);
	}

}