/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class StampaEtichettaPdf {
	
	public static void stampa(Record[] pRecordsEtichette, String numeroCopie) {
		Layout.showWaitPopup("Apertura anteprima etichetta in corso...");
		
		Record lMapParams = new Record();
		
		String impostazioniPdf = AurigaLayout.getImpostazioniPdf();
		lMapParams = populateRecord(pRecordsEtichette, numeroCopie);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("GeneraEtichettaPdfDataSource");
		final Record record = new Record();
		
		record.setAttribute("lMapParams", lMapParams);
		record.setAttribute("impostazioniPdf", impostazioniPdf);
		
		try {
			lGwtRestDataSource.executecustom("generaFileEtichette", record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Layout.hideWaitPopup();
						Record data = response.getData()[0];
						String etichettaPdf = data.getAttributeAsString("uriPdfResult");
						InfoFileRecord info = new InfoFileRecord(data.getAttributeAsRecord("infoFile"));
						PreviewControl.switchPreview(etichettaPdf, false, info, "FileToExtractBean", "Etichetta.pdf");
					}

				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
		
	}
	
	public static Record populateRecord (Record[] pRecordsEtichette, String numeroCopie) {
		Record result = new Record();
		result.setAttribute("tipoStampa", "PDF");
		result.setAttribute("idUtente", Layout.getUtenteLoggato().getIdUtente());
		result.setAttribute("idSchema", Layout.getUtenteLoggato().getSchema());
		result.setAttribute("idDominio", Layout.getUtenteLoggato().getDominio().split(":")[1]);
		result.setAttribute("numeroCopie", numeroCopie);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_BARCODE_ETICHETTA_SEPARATA")) {
			result.setAttribute("printCodeInSecondLabel", "true");
		}
		String barcodeEncoding = AurigaLayout.getParametroDB("BARCODE_TYPE_IN_ETICHETTA");
		if(barcodeEncoding != null && !"".equals(barcodeEncoding)) {
			result.setAttribute("barcodeEncoding", barcodeEncoding);
		}
		int i = 1;
		for (Record lRecord : pRecordsEtichette) {
			if(lRecord.getAttribute("testo") != null && !"".equals(lRecord.getAttribute("testo"))) {
				result.setAttribute("testo" + i, lRecord.getAttribute("testo"));	
			}
			if(lRecord.getAttribute("barcode") != null && !"".equals(lRecord.getAttribute("barcode"))) {
				result.setAttribute("barcode" + i, lRecord.getAttribute("barcode"));		
			}
			if(lRecord.getAttribute("testoBarcode") != null && !"".equals(lRecord.getAttribute("testoBarcode"))) {
				result.setAttribute("testoBarcode" + i, lRecord.getAttribute("testoBarcode"));
			}
			if(lRecord.getAttribute("testoRepertorio") != null && !"".equals(lRecord.getAttribute("testoRepertorio"))) {
				result.setAttribute("testoRepertorio" + i, lRecord.getAttribute("testoRepertorio"));
			}
			if(lRecord.getAttribute("testoFaldone") != null && !"".equals(lRecord.getAttribute("testoFaldone"))) {
				result.setAttribute("testoFaldone" + i, lRecord.getAttribute("testoFaldone"));
			}
			if(lRecord.getAttribute("barcodeFaldone") != null && !"".equals(lRecord.getAttribute("barcodeFaldone"))) {
				result.setAttribute("barcodeFaldone" + i, lRecord.getAttribute("barcodeFaldone"));		
			}
			i++;
		}
		String contextPath = GWT.getHostPageBaseURL();
		result.setAttribute("propertiesServlet", contextPath + "springdispatcher/stampaEtichette");
		result.setAttribute("pdfServlet", contextPath + "springdispatcher/pdfProperties");
		
		return result;
	}
}
