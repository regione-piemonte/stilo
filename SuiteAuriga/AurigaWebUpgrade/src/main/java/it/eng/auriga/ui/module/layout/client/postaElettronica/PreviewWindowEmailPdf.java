/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AbstractPreviewCanvas;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.shared.bean.MimeTypeNonGestitiBean;

public class PreviewWindowEmailPdf extends Window implements AbstractPreviewCanvas {

	public boolean isModal() {
		return true;
	}

	public PreviewWindowEmailPdf(Record lRecord) {
		
		setShowTitle(true);
		setIsModal(isModal());
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setAutoSize(false);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowCloseButton(true);
		setAlign(Alignment.CENTER);
		setCanDragResize(true);
		setShowStatusBar(true);
		setShowResizer(true);
		setRedrawOnResize(true);
		setAutoDraw(false);

		manageResponse(lRecord);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});
	}

	protected void manageResponse(Record lRecord) {
		try {
			String progressivo = lRecord.getAttribute("id");
			String idEmail = lRecord.getAttribute("idEmail");
			String uriFileEml = null;

			// Gestisco il caso in cui sono in una bozza o in una mail
			if (lRecord.getAttribute("uriFileEml") != null && !"".equals(lRecord.getAttribute("uriFileEml"))) {
				uriFileEml = lRecord.getAttribute("uriFileEml");
			} else {
				uriFileEml = lRecord.getAttribute("uri");
			}
			String tipo = lRecord.getAttribute("tipo");
			String sottoTipo = lRecord.getAttribute("sottotipo");
			String messageId = lRecord.getAttribute("messageId");
			String casellaAccount = lRecord.getAttribute("casellaAccount");
			String casellaIsPEC = lRecord.getAttribute("casellaIsPEC");
			String accountMittente = lRecord.getAttribute("accountMittente");
			String subject = lRecord.getAttribute("subject");
			String body = lRecord.getAttribute("escapedHtmlBody");
			String tsInvio = lRecord.getAttributeAsString("tsInvio");
			String tsRicezione = lRecord.getAttributeAsString("tsInsRegistrazione");
			String destinatariPrimari = lRecord.getAttributeAsString("destinatariPrimari");
			String assegnatario = lRecord.getAttribute("desUOAssegnataria");
			String title = lRecord.getAttribute("titoloGUIDettaglioEmail");
			String tipoFormattazione = lRecord.getAttribute("completa");
			String progrDownloadStampa = lRecord.getAttribute("progrDownloadStampa");
			String tsSalvataggioEmail = lRecord.getAttributeAsString("tsSalvataggioEmail");
			String statoLavorazione = lRecord.getAttributeAsString("statoLavorazione");
			String flgIO = lRecord.getAttribute("flgIO");
			String tsStatoLavorazioneDal = lRecord.getAttribute("tsStatoLavorazioneDal");
			String tsAssegnazioneDal = lRecord.getAttribute("tsAssegnazioneDal");
			String inCaricoA = lRecord.getAttribute("inCaricoA");
			String tsInCaricoDal = lRecord.getAttribute("tsInCaricoDal");
			String codAzioneDaFare = null;
			String descrizioneAzioneDaFare = null;
			if (lRecord.getAttributeAsJavaScriptObject("azioneDaFareBean") != null) {
				Map azioniDaFareMap = new Record(lRecord.getAttributeAsJavaScriptObject("azioneDaFareBean")).toMap();
				codAzioneDaFare = azioniDaFareMap.get("azioneDaFare") != null ? (String) azioniDaFareMap.get("azioneDaFare") : null;
				descrizioneAzioneDaFare = azioniDaFareMap.get("dettaglioAzioneDaFare") != null ? (String) azioniDaFareMap.get("dettaglioAzioneDaFare") : null;
			}
			String estremiDocDerivati = lRecord.getAttribute("estremiDocDerivati");
			String statoInvio = lRecord.getAttribute("statoInvio");
			String statoAccettazione = lRecord.getAttribute("statoAccettazione");
			String statoConsegna = lRecord.getAttribute("statoConsegna");
			String dataAggStatoLavorazione = lRecord.getAttribute("dataAggStatoLavorazione");
			String orarioAggStatoLavorazione = lRecord.getAttribute("orarioAggStatoLavorazione");
			String dataUltimaAssegnazione = lRecord.getAttribute("dataUltimaAssegnazione");
			String orarioUltimaAssegnazione = lRecord.getAttribute("orarioUltimaAssegnazione");
			String desUtenteLock = lRecord.getAttribute("desUtenteLock");
			String dataLock = lRecord.getAttribute("dataLock");
			String orarioLock = lRecord.getAttribute("orarioLock");

			tsRicezione = tsRicezione == null ? tsSalvataggioEmail : tsRicezione;

			if (title != null && title.length() > 0)
				setTitle(title + " - Anteprima di stampa ");
			else
				setTitle(progressivo + " - " + tipo + " - " + sottoTipo + " del " + tsInvio + " - Anteprima di stampa");

			String allegati = "";
			RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
			if (listaAllegati != null) {
				for (int n = 0; listaAllegati != null && n < listaAllegati.getLength(); n++) {
					Record allegatoRecord = listaAllegati.get(n);

					String file = allegatoRecord.getAttributeAsString("nomeFile");
					String numeroProgAllegato = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
					if ((numeroProgAllegato != null) && (numeroProgAllegato.length() > 0)) {
						file = "Allegato n° " + numeroProgAllegato + ": " + file;
					}
					allegati += " " + file + ";";
				}
			}

			String dest = "";
			RecordList listaDestinatari = lRecord.getAttributeAsRecordList("listaDestinatariPrincipali");
			if (listaDestinatari != null) {
				for (int n = 0; n < listaDestinatari.getLength(); n++) {
					Record destin = listaDestinatari.get(n);

					String indirizzo = destin.getAttributeAsString("indirizzo");
					dest += "" + indirizzo + ";";
				}
			}

			if ("".equals(dest) && destinatariPrimari != null)
				dest = destinatariPrimari;

			String destEntrata = lRecord.getAttributeAsString("destinatariPrincipali");
			if ("".equals(dest) && destEntrata != null)
				dest = destEntrata;

			String destCC = "";
			RecordList listaDestinatariCC = lRecord.getAttributeAsRecordList("listaDestinatariCC");
			if (listaDestinatariCC != null) {
				for (int n = 0; n < listaDestinatariCC.getLength(); n++) {
					Record destin = listaDestinatariCC.get(n);

					String indirizzo = destin.getAttributeAsString("indirizzo");
					destCC += "" + indirizzo + ";";
				}
			}

			String destinatariCC = lRecord.getAttribute("destinatariCC");

			if ("".equals(destCC) && destinatariCC != null)
				destCC = destinatariCC;

			Record lRecord1 = new Record();
			lRecord1.setAttribute("idEmail", idEmail);
			lRecord1.setAttribute("uriFileEml", uriFileEml);
			lRecord1.setAttribute("progressivo", progressivo);
			lRecord1.setAttribute("progrDownloadStampa", progrDownloadStampa);
			lRecord1.setAttribute("messageId", messageId);
			lRecord1.setAttribute("tipo", tipo);
			lRecord1.setAttribute("sottoTipo", sottoTipo);
			lRecord1.setAttribute("casellaAccount", casellaAccount);
			lRecord1.setAttribute("casellaIsPEC", casellaIsPEC);
			lRecord1.setAttribute("accountMittente", accountMittente);
			lRecord1.setAttribute("listaDestinatariPrincipali", dest);
			lRecord1.setAttribute("subject", subject);
			// Controllo per evitare l'errore di visualizzazione di una mail con corpo vuoto nel caso di corpo testo
			if(body != null && !"".equals(body)){
				lRecord1.setAttribute("body", body);
			}else{
				lRecord1.setAttribute("body", " ");
			}
			lRecord1.setAttribute("tsInvio", tsInvio);
			lRecord1.setAttribute("tsRicezione", tsRicezione);
			lRecord1.setAttribute("desUOAssegnataria", assegnatario);
			lRecord1.setAttribute("tsAssegnazioneDal", tsStatoLavorazioneDal);
			lRecord1.setAttribute("dataUltimaAssegnazione", dataUltimaAssegnazione);
			lRecord1.setAttribute("orarioUltimaAssegnazione", orarioUltimaAssegnazione);
			lRecord1.setAttribute("allegati", allegati);
			lRecord1.setAttribute("completa", tipoFormattazione);
			lRecord1.setAttribute("statoLavorazione", statoLavorazione);
			lRecord1.setAttribute("flgIO", flgIO);
			lRecord1.setAttribute("dataAggStatoLavorazione", dataAggStatoLavorazione);
			lRecord1.setAttribute("orarioAggStatoLavorazione", orarioAggStatoLavorazione);
			lRecord1.setAttribute("tsAssegnazioneDal", tsAssegnazioneDal);
			lRecord1.setAttribute("inCaricoA", inCaricoA);
			lRecord1.setAttribute("tsInCaricoDal", tsInCaricoDal);
			lRecord1.setAttribute("codAzioneDaFare", codAzioneDaFare);
			lRecord1.setAttribute("descrizioneAzioneDaFare", descrizioneAzioneDaFare);
			lRecord1.setAttribute("estremiDocDerivati", estremiDocDerivati);
			lRecord1.setAttribute("statoInvio", statoInvio);
			lRecord1.setAttribute("statoAccettazione", statoAccettazione);
			lRecord1.setAttribute("statoConsegna", statoConsegna);
			lRecord1.setAttribute("destinatariCC", destCC);
			lRecord1.setAttribute("desUtenteLock", desUtenteLock);
			lRecord1.setAttribute("dataLock", dataLock);
			lRecord1.setAttribute("orarioLock", orarioLock);

			String url = GWT.getHostPageBaseURL() + "springdispatcher/createpdf";

			final HTMLPane lHtmlPane = new HTMLPane();
			lHtmlPane.setWidth100();
			lHtmlPane.setHeight100();
			lHtmlPane.setContentsType(ContentsType.PAGE);

			final String filenamePdf = progrDownloadStampa + ".pdf";

			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
			requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("mimetype=application/pdf&").append("record=" + encodeURL(JSON.encode(lRecord1.getJsObj(), new JSONEncoder())));

			requestBuilder.sendRequest(stringBuilder.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					lHtmlPane.setContentsURL(GWT.getHostPageBaseURL() + "springdispatcher/stream?filename=" + URL.encode(filenamePdf) + "&uri="
							+ URL.encode(response.getText()));
				}

				@Override
				public void onError(Request request, Throwable exception) {

				}
			});

			int width = com.google.gwt.user.client.Window.getClientWidth() * 80 / 100;
			int height = com.google.gwt.user.client.Window.getClientHeight() * 80 / 100;

			setWidth(width);
			setHeight(height);
			markForRedraw();

			VLayout lVLayout = new VLayout();
			lVLayout.setWidth100();
			lVLayout.setHeight100();
			lVLayout.addMember(lHtmlPane);
			addItem(lVLayout);

			show();
		} catch (Exception e) {
			markForDestroy();
		}
	}

	public static String encodeURL(String str) {
		if (str != null) {
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

	public static boolean canBePreviewed(InfoFileRecord lInfoFileRecord) {
		if (lInfoFileRecord == null)
			return false;
		else {
			MimeTypeNonGestitiBean mimeTypeNonGestitiMap = Layout.getMimeTypeNonGestitiBean();
			String mimetype = lInfoFileRecord.getMimetype();
			String correctFileName = lInfoFileRecord.getCorrectFileName();
			if (mimetype != null && !isMimeTypeNonGestito(mimeTypeNonGestitiMap, mimetype)) {
				if (mimetype.equals("application/pdf") || mimetype.startsWith("image") || lInfoFileRecord.isConvertibile() || isEmlExtension(correctFileName)
						|| isEmlMimetype(mimetype)) {
					return true;
				} else
					return false;
			} else
				return false;
		}
	}

	private static boolean isMimeTypeNonGestito(MimeTypeNonGestitiBean mimeTypeNonGestitiMap, String mimetype) {
		if(mimeTypeNonGestitiMap!=null && mimeTypeNonGestitiMap.getMimeTypeMap() != null && mimeTypeNonGestitiMap.getMimeTypeMap().containsKey("mimeTypeNonGestiti")) {
			for (String mimeTypeNonGestito : mimeTypeNonGestitiMap.getMimeTypeMap().get("mimeTypeNonGestiti")) {
				if(mimeTypeNonGestito.equals(mimetype)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isEmlMimetype(String mimetype) {
		for (String lString : Layout.getGenericConfig().getEmlMimetype()) {
			if (lString.equalsIgnoreCase(mimetype)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmlExtension(String correctFileName) {
		if (correctFileName == null)
			return false;
		int startIndex = correctFileName.lastIndexOf(".");
		if (startIndex == -1)
			return false;
		String extension = correctFileName.substring(startIndex + 1);
		for (String lString : Layout.getGenericConfig().getEmlExtension()) {
			if (lString.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void finishLoad(VLayout lVLayout) {
		addItem(lVLayout);
		show();
	}
}
