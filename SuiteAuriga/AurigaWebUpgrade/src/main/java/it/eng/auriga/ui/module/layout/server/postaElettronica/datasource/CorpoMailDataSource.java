/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SalvaInBozzaMailBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id = "CorpoMailDataSource")
public class CorpoMailDataSource extends AbstractServiceDataSource<InvioMailBean, InvioMailBean> {

	private static final Logger log = Logger.getLogger(CorpoMailDataSource.class);

	private static final String startDivSignature = "<!-- Inizio firmaInCalce -->";
	private static final String endDivSignature = "<!-- Fine firmaInCalce -->";

	private static final String startDivActualSignature = "<!-- Inizio firmaAttuale -->";
	private static final String endDivActualSignature = "<!-- Fine firmaAttuale -->";

	private static int startIndexMarker = -1;
	private static int endIndexMarker = -1;

	// Il marker per identificare che si tratta della mail che si vuole inoltrare o
	// a cui si vuole rispondere
	// private String startHeaderMarker = "<div id=inizioheadermail>";
	// private String endHeaderMarker = "</div><!--fineheadermail-->";
	private String startHeaderMarker = "<!--inizioheadermail-->";
	private String endHeaderMarker = "<!--fineheadermail-->";

	private String fontDivHeaderMail = "<div style=\"font-size: 10pt; font-family: arial\">";

	private String modalita = "";

	public String removeMarker(String bodyHtml) throws Exception {

		if (bodyHtml != null && !bodyHtml.equals("")) {

			try {

				String buffText = "";

				// RIMPIAZZO IL MARKER ATTUALMENTE PRESENTE (SE C'E') RELATIVO ALLA FIRMA
				// ATTUALE CON VUOTO
				// Individuo dove si trova il primo marker relativo alla firma attuale
				int startIndexFirstMarkerActualSignature = bodyHtml.toLowerCase()
						.indexOf(startDivActualSignature.toLowerCase());

				if (startIndexFirstMarkerActualSignature != -1) {

					// Prelevo il testo da eliminare
					buffText = bodyHtml.subSequence(startIndexFirstMarkerActualSignature,
							startIndexFirstMarkerActualSignature + startDivActualSignature.length()).toString();
					bodyHtml = bodyHtml.replace(buffText, "");
				}

				// Individuo dove si trova il secondo marker relativo alla firma attuale
				int startIndexSecondMarkerActualSignature = bodyHtml.toLowerCase()
						.indexOf(endDivActualSignature.toLowerCase());

				if (startIndexSecondMarkerActualSignature != -1) {
					// Prelevo il testo da eliminare
					buffText = bodyHtml.subSequence(startIndexSecondMarkerActualSignature,
							startIndexSecondMarkerActualSignature + endDivActualSignature.length()).toString();
					bodyHtml = bodyHtml.replace(buffText, "");
				}

				// RIMPIAZZO IL MARKER DELLA FIRMA IN CALCE CON IL MARKER DELLA FIRMA ATTUALE
				// Individuo dove si trova il primo marker
				int startIndexFirstMarker = bodyHtml.toLowerCase().indexOf(startDivSignature.toLowerCase());

				if (startIndexFirstMarker != -1) {

					// Prelevo il testo da eliminare
					buffText = bodyHtml
							.subSequence(startIndexFirstMarker, startIndexFirstMarker + startDivSignature.length())
							.toString();
					bodyHtml = bodyHtml.replace(buffText, startDivActualSignature);
				}

				// Individuo dove si trova il secondo marker
				int startIndexSecondMarker = bodyHtml.toLowerCase().indexOf(endDivSignature.toLowerCase());

				if (startIndexSecondMarker != -1) {
					// Prelevo il testo da eliminare
					buffText = bodyHtml
							.subSequence(startIndexSecondMarker, startIndexSecondMarker + endDivSignature.length())
							.toString();
					bodyHtml = bodyHtml.replace(buffText, endDivActualSignature);
				}

				// Rimuovo anche i marker relativi all'header di un inoltro o risposta (se
				// presenti)
				int startIndexHeaderMarker = bodyHtml.toLowerCase().indexOf(startHeaderMarker);
				if (startIndexHeaderMarker != -1) {
					bodyHtml = bodyHtml.replace(bodyHtml.substring(startIndexHeaderMarker,
							startIndexHeaderMarker + startHeaderMarker.length()), "");
				}

				startIndexHeaderMarker = bodyHtml.toLowerCase().indexOf(endHeaderMarker);
				if (startIndexHeaderMarker != -1) {
					bodyHtml = bodyHtml.replace(bodyHtml.substring(startIndexHeaderMarker,
							startIndexHeaderMarker + endHeaderMarker.length()), "");
				}

			} catch (Exception e) {
				throw e;
			}
		}

		return bodyHtml;
	}

	public InvioMailBean aggiungiImmagine(InvioMailBean bean) {

		String image = StringUtils.isNotBlank(getExtraparams().get("image")) ? getExtraparams().get("image") : "";
		String bodyHtml = bean.getBodyHtml();
		if (bodyHtml != null && !bodyHtml.isEmpty()) {
			bodyHtml = image + bodyHtml;
		} else {
			bodyHtml = image;
		}

		org.jsoup.nodes.Document document = Jsoup.parse(bodyHtml);
		try {
			// estraggo tutte le immagini, per rimpiazzare l'attributo src originale con il
			// path relativo al server in cui si trova la mail
			Elements images = document.select("img");
			Iterator<org.jsoup.nodes.Element> imagesIterator = images.iterator();

			while (imagesIterator.hasNext()) {

				org.jsoup.nodes.Element currentImage = imagesIterator.next();

				String originalSrc = currentImage.attr("src");

				if (originalSrc != null && !originalSrc.toLowerCase().startsWith("data:")) {
					String[] splittedSrc = originalSrc.split(":");
					String displayFileName = splittedSrc[0];
					String uri = splittedSrc[1];

					InfoFileUtility lInfoFileUtility = new InfoFileUtility();
					MimeTypeFirmaBean infoFile = lInfoFileUtility.getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(uri).toURI().toString(), displayFileName,
							false, null);

					InputStream in = (FileInputStream) StorageImplementation.getStorage().extract(uri);
					String extension = FilenameUtils.getExtension(infoFile.getCorrectFileName());

					final File tempFile = File.createTempFile("attachImg", "." + extension);
					tempFile.deleteOnExit();

					String mimetype = infoFile.getMimetype();
					if (StringUtils.isBlank(mimetype)) {
						mimetype = "unknown";
					}

					FileOutputStream fos = new FileOutputStream(tempFile);

					try {
						IOUtils.copy(in, fos);
					} catch (Exception e) {
					} finally {
						try {
							in.close();
						} catch (Exception ex) {
						}
					}

					String imageBase64 = encodeToBase64String(tempFile);
					if (StringUtils.isNotBlank(mimetype) && StringUtils.isNotBlank(imageBase64)) {
						currentImage.attr("src", "data:" + mimetype + ";base64," + imageBase64);
					}
				}
			}
		} catch (Exception e) {
			addMessage("Si è verificato un errore durante il caricamento dell'immagine", "", MessageType.ERROR);
			// rimuovo le immagini
			document.select("img").remove();
		}

		bean.setBodyHtml(document.outerHtml());

		return bean;
	}

	public String encodeToBase64String(File file) throws Exception {

		BufferedImage image = ImageIO.read(file);

		String extension = FilenameUtils.getExtension(file.getName());

		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, extension, bos);
			byte[] imageBytes = bos.toByteArray();

			imageString = Base64.encodeBase64String(imageBytes);

			bos.close();
		} catch (IOException e) {
			log.warn(e);
		}

		return imageString;
	}

	/**
	 * Rimpiazzo la firma precedente con quella nuova appena selezionata
	 * 
	 * @param firmaHtmlSelezionata
	 *            la nuova firma da inserire nella mail sostituendo quella
	 *            precedente
	 * @param lRichTextItemBodyValue
	 *            il testo all'interno della mail
	 * @return il testo della mail con la nuova firma inserita
	 */
	public SalvaInBozzaMailBean replaceSignature(SalvaInBozzaMailBean bean) {

		String firmaHtmlSelezionata = (getExtraparams().get("newSignature") != null
				? getExtraparams().get("newSignature")
				: "");
		String starterMailBodyTextHtml = (getExtraparams().get("starterMailBodyTextHtml") != null
				? getExtraparams().get("starterMailBodyTextHtml")
				: "");
		String modalita = (getExtraparams().get("modalita") != null ? getExtraparams().get("modalita") : "");
		String lRichTextItemBodyValue = (bean.getBodyHtml() != null ? bean.getBodyHtml() : "");
		String spaces = "<br/><br/><br/>";

		if (lRichTextItemBodyValue != null) {

			// Inserisco i due div marcatori della firma in quella nuova da inserire
			firmaHtmlSelezionata = refactorSignature(firmaHtmlSelezionata);

			/*
			 * Cerco la firma in calce attuale che si trova nella mail ovvero la firma
			 * contrassegnata con i marker relativi alla firma in calce
			 */
			String actualSignature = findSignature(lRichTextItemBodyValue);

			String newMailBody = "";

			if (!actualSignature.equals("")) {
				// Vuol dire che è stata rilevata una firma all'interno della mail

				// Rimpiazzo la firma precedente con quella nuova e ritorno il valore
				newMailBody = lRichTextItemBodyValue.replace(actualSignature, firmaHtmlSelezionata);

			} else {

				// Non è stata trovata una firma valida da rimpiazzare -> cancello marker
				// inutili relativi alla firma in calce
				lRichTextItemBodyValue = cancellaMarkerObsoleti(lRichTextItemBodyValue);

				if (modalita != null && "nuovaMail".equals(modalita)) {
					newMailBody = lRichTextItemBodyValue + spaces + firmaHtmlSelezionata;
				} else {

					// TODO CANCELLARE?
					int indexHeaderMarker = 0;
					if ((indexHeaderMarker = findMarkerHeader(lRichTextItemBodyValue)) != -1) {
						/*
						 * Se si è trovato il marker dell'header che identifica la presenza dell'header
						 * di risposta o inoltro ma non si è trovata una firma da rimpiazzare allora si
						 * posiziona la mail esattamente prima del marker
						 */
						newMailBody += (lRichTextItemBodyValue.substring(0, indexHeaderMarker).equals("") ? "<br/><br/>"
								: lRichTextItemBodyValue.substring(0, indexHeaderMarker)) // Prendo la parte prima del
																							// marker
								+ firmaHtmlSelezionata + "<br/><br/>"// Aggiungo la firma in questione
								+ lRichTextItemBodyValue.substring(indexHeaderMarker, lRichTextItemBodyValue.length()); // Aggiungo
																														// la
																														// rimanente
																														// parte
																														// della
																														// mail,
																														// ovvero
																														// quella
																														// dopo
																														// l'header
					} else {
						/*
						 * Altrimenti non è stata rilevata una firma all'interno della mail ne un marker
						 * che identifica un header di una mail da inoltrare o da rispondere Si
						 * inserisce la firma all'inizio, prima dell'eventuale corpo
						 */

						// Inserisco la firma dopo il corpo già presente
						newMailBody += spaces + firmaHtmlSelezionata + spaces + lRichTextItemBodyValue;
					}
				}

				/*
				 * Non è stata trovata una firma da rimpiazzare, ovvero non c'è una firma in
				 * calce nella mail.
				 */

				// int indexStarterMailBodyTextHtml =
				// lRichTextItemBodyValue.toLowerCase().indexOf(fontDivHeaderMail);
				// if(indexStarterMailBodyTextHtml != -1){
				// /*
				// * si è trovato il tag dell'header
				// */
				// /*TODO mnodificare commenti
				// * Non è stata modificata la mail di partenza che si voleva inoltrare o a cui
				// rispondere (quella dopo l'header)
				// * si pone la firma esattamente tra il body inserito e l'header
				// */
				// newMailBody = lRichTextItemBodyValue.substring(0,
				// indexStarterMailBodyTextHtml)
				// + spaces
				// + firmaHtmlSelezionata
				// + lRichTextItemBodyValue.substring(indexStarterMailBodyTextHtml,
				// lRichTextItemBodyValue.length());
				// }else{
				// /*
				// * TODO MOdificare commenti
				// * Altrimenti la mail di partenza che si voleva inoltrare o a cui rispondere
				// (quella dopo l'header)
				// * è stata modificata.
				// * Pongo la firma all'inizio della mail e poi tutto il resto del corpo
				// */
				// newMailBody = spaces + firmaHtmlSelezionata + lRichTextItemBodyValue;
				// }
				// if(starterMailBodyTextHtml != null && !"".equals(starterMailBodyTextHtml)){
				//
				// int indexStarterMailBodyTextHtml = isAddedBody(lRichTextItemBodyValue,
				// starterMailBodyTextHtml);
				// if(indexStarterMailBodyTextHtml != -1){
				//
				// /*
				// * Non è stata modificata la mail di partenza che si voleva inoltrare o a cui
				// rispondere (quella dopo l'header)
				// * si pone la firma esattamente tra il body inserito e l'header
				// */
				// newMailBody = lRichTextItemBodyValue.substring(0,
				// indexStarterMailBodyTextHtml)
				// + spaces
				// + firmaHtmlSelezionata
				// + starterMailBodyTextHtml;
				// }else{
				// /*
				// * Altrimenti la mail di partenza che si voleva inoltrare o a cui rispondere
				// (quella dopo l'header)
				// * è stata modificata.
				// * Pongo la firma all'inizio della mail e poi tutto il resto del corpo
				// */
				// newMailBody = spaces + firmaHtmlSelezionata + lRichTextItemBodyValue;
				// }
				// }
			}

			/*
			 * Nel caso in cui newMailBody non sia stata settata per eventuali problemi
			 * oppure perchè non c'è un corpo nella mail allora si inserisce solamente la
			 * firma
			 */
			if ("".equals(newMailBody)) {
				newMailBody = spaces + firmaHtmlSelezionata;
			}

			bean.setBodyHtml(newMailBody);
		}

		return bean;

	}

	private String cancellaMarkerObsoleti(String lRichTextItemBodyValue) {
		if (lRichTextItemBodyValue != null && !"".equals(lRichTextItemBodyValue)) {
			int startMarker = lRichTextItemBodyValue.toLowerCase().indexOf(startDivSignature.toLowerCase());

			if (startMarker != -1) {

				/*
				 * C'è il marker iniziale della firma da rimuovere perchè se arrivo qui non c'è
				 * il marker finale e quindi c'è qualche problema
				 */
				lRichTextItemBodyValue = lRichTextItemBodyValue.replace(
						lRichTextItemBodyValue.substring(startMarker, startMarker + startDivSignature.length()), "");
			}

			int endMarker = lRichTextItemBodyValue.toLowerCase().indexOf(endDivSignature.toLowerCase());

			if (endMarker != -1) {

				/*
				 * C'è il marker finale della firma da rimuovere perchè se arrivo qui non c'è il
				 * marker iniziale e quindi c'è qualche problema
				 */
				lRichTextItemBodyValue = lRichTextItemBodyValue
						.replace(lRichTextItemBodyValue.substring(endMarker, endMarker + endDivSignature.length()), "");
			}
		}

		return lRichTextItemBodyValue;
	}

	/**
	 * Il metodo controlla il corpo della mail inserita dall'utente in modo tale da
	 * avere il contenuto da aggiungere
	 * 
	 * @param lRichTextItemBodyValue
	 * @param starterMailBodyTextHtml
	 * @return
	 */
	// private int isAddedBody(String lRichTextItemBodyValue, String
	// starterMailBodyTextHtml){
	//
	// if(lRichTextItemBodyValue != null && starterMailBodyTextHtml != null &&
	// !lRichTextItemBodyValue.equals("") && !starterMailBodyTextHtml.equals("")){
	//
	// if(lRichTextItemBodyValue.contains(starterMailBodyTextHtml)){
	// /*
	// * Se non è stato modificato l'header iniziale della mail (la mail dopo
	// l'header) e quindi lo ritrovo
	// * allora ritorno l'indice a cui si trova l'header
	// */
	// return lRichTextItemBodyValue.indexOf(starterMailBodyTextHtml);
	//
	// }else{
	// /*
	// * Allora c'è stato un problema oppure è stata modificata la mail di partenza
	// che si voleva inoltrare o
	// * rispondere (quella dopo l'header).
	// */
	// return -1;
	// }
	// }else{
	// /*
	// * Allora c'è stato un problema
	// */
	// return -1;
	// }
	//
	//
	// }

	private int findMarkerHeader(String lRichTextItemBodyValue) {

		if (lRichTextItemBodyValue != null && !"".equals(lRichTextItemBodyValue)
				&& lRichTextItemBodyValue.toLowerCase().contains(startHeaderMarker)) {
			return lRichTextItemBodyValue.toLowerCase().indexOf(startHeaderMarker);
		}

		return -1;
	}

	/**
	 * Inserisco i marcatori nella firma che viene fornita come input
	 * 
	 * @param signature
	 *            firma in cui inserire i due marker
	 * @return stringa con i marker
	 */
	private static String refactorSignature(String signature) {
		return startDivSignature + signature + endDivSignature;
	}

	/**
	 * Ricerco la firma (distinguibile grazie ai marcatori inseriti) presente
	 * all'interno della mail
	 * 
	 * @param lRichTextItemBodyValue
	 *            corpo della mail
	 * @return il testo della firma presente nella mail
	 */
	private static String findSignature(String lRichTextItemBodyValue) {

		// Per evitare problemi di uppercase e lowercase porto sia il testo che i marker
		// nel lowercase
		String lRichTextItemBodyValueLowercase = lRichTextItemBodyValue.toLowerCase();

		if (lRichTextItemBodyValueLowercase.contains(startDivSignature.toLowerCase())
				&& lRichTextItemBodyValueLowercase.contains(endDivSignature.toLowerCase())) {

			// Individuo i punti iniziali e finali dove si trovano i marker
			startIndexMarker = lRichTextItemBodyValueLowercase.indexOf(startDivSignature.toLowerCase());

			endIndexMarker = lRichTextItemBodyValueLowercase.indexOf(endDivSignature.toLowerCase())
					+ endDivSignature.length();

			if (startIndexMarker != -1 && endIndexMarker != -1) {
				// Ritorno il testo tra i due marker
				return (lRichTextItemBodyValue.subSequence(startIndexMarker, endIndexMarker)).toString();
			} else {
				return "";
			}

		} else {
			// Se non ha individuato i marker ritorna la stringa vuota
			return "";
		}

	}

	public InvioMailBean findSignature(InvioMailBean bean) {
		bean.setBodyHtml(findSignature(bean.getBodyHtml()));
		return bean;
	}

	/**
	 * Se swapSignatureTag ritorna una stringa diversa da vuoto allora sono stati
	 * individuati i tag ed è stato eseguito lo swap tra tag firmaAttuale e
	 * FirmaInCalce altrimenti viene ritornata una stringa vuota. Se è stato
	 * eseguito lo scambio allora utilizzo il nuovo body che viene ritornato;
	 * Altrimenti inserisco una firma prima del testo in precedenza presente
	 */
	private String swapSignatureTag(String htmlBody) {

		String buffText = "";

		// RIMPIAZZO IL MARKER ATTUALMENTE PRESENTE (SE C'E') RELATIVO ALLA FIRMA
		// ATTUALE CON IL MARKER DELLA FIRMA IN CALCE
		// Individuo dove si trova il primo marker relativo alla firma attuale
		int startIndexFirstMarkerActualSignature = htmlBody.toLowerCase()
				.indexOf(startDivActualSignature.toLowerCase());

		// Se non l'ho trovato lo ricerco per Chrome
		if (startIndexFirstMarkerActualSignature == -1) {

			return "";

		} else {
			// Prelevo il testo da eliminare
			buffText = htmlBody.subSequence(startIndexFirstMarkerActualSignature,
					startIndexFirstMarkerActualSignature + startDivActualSignature.length()).toString();
			htmlBody = htmlBody.replace(buffText, startDivSignature);
		}

		// Individuo dove si trova il secondo marker relativo alla firma attuale
		int startIndexSecondMarkerActualSignature = htmlBody.toLowerCase().indexOf(endDivActualSignature.toLowerCase());

		if (startIndexSecondMarkerActualSignature != -1) {
			// Prelevo il testo da eliminare
			buffText = htmlBody.subSequence(startIndexSecondMarkerActualSignature,
					startIndexSecondMarkerActualSignature + endDivActualSignature.length()).toString();
			htmlBody = htmlBody.replace(buffText, endDivSignature);
		}

		return htmlBody;
	}

	public SalvaInBozzaMailBean addSignature(SalvaInBozzaMailBean bean) {
		String firmaHtmlSelezionata = (getExtraparams().get("newSignature") != null
				? getExtraparams().get("newSignature")
				: "");

		// Ottengo il body della mail presente all'interno della maschera
		String bodyHtml = bean.getBodyHtml();

		String modalita = getExtraparams().get("modalita");

		if (modalita != null && modalita.equals("NUOVO_COME_COPIA")) {
			String bodyModified = "";
			/*
			 * Allora devo fare lo scambio della firma indicata con firmaAttuale in
			 * firmaInCalce Non devo aggiungere niente altro al corpo della mail; quindi se
			 * non c'è la firma non aggiungo nulla
			 */

			if (bodyHtml != null && !"".equals(bodyHtml)) {
				/*
				 * Se swapSignatureTag ritorna una stringa diversa da vuoto allora sono stati
				 * individuati i tag ed è stato eseguito lo swap tra tag firmaAttuale e
				 * FirmaInCalce altrimenti viene ritornata una stringa vuota. Se è stato
				 * eseguito lo scambio allora utilizzo il nuovo body che viene ritornato;
				 * Altrimenti inserisco una firma prima del testo in precedenza presente
				 */
				bodyModified = swapSignatureTag(bodyHtml);

				// In caso di qualche errore per cui il bodyModified è vuoto o nullo allora si
				// rimette il corpo della mail precedente
				if (bodyModified.equals("") || bodyModified == null) {
					bodyModified = bodyHtml;
				}
			} else {
				bodyModified = "";
			}

			// Setto il nuovo corpo nel bean
			bean.setBodyHtml(bodyModified);

			return bean;
		}

		/*
		 * Se la firma da inserire è diversa da vuoto e diversa da null allora la
		 * inserisce nel body della mail, altrimenti non viene inserita. Altrimenti ci
		 * si trova in una situazione in cui nel corpo della mail viene inserita una
		 * firma vuota e di conseguenza i due marker uno di seguito all'altro <!--
		 * Inizio firmaInCalce --><!-- Fine firmaInCalce -->
		 */
		if (firmaHtmlSelezionata != null && !"".equals(firmaHtmlSelezionata)) {
			firmaHtmlSelezionata = refactorSignature(firmaHtmlSelezionata);

			/*
			 * Allora ci si trova nella situazione di nuova mail / risposta / inoltro Prendo
			 * il corpo presente e aggiungo la relativa firma con i marker di delimitazione
			 */

			if (bodyHtml != null && !"".equals(bodyHtml)) {

				if (modalita != null && modalita.equals("NUOVO_UD")) {
					/*
					 * Se sono nella modalità nuova ud allora devo inserire prima il body e poi la
					 * firma
					 */
					bean.setBodyHtml(bodyHtml + "<br/><br/>" + firmaHtmlSelezionata);
				} else {
					/*
					 * Se sono nella modalità di nuova mail, risposta o inoltro la firma deve
					 * trovarsi prima del body
					 */
					bean.setBodyHtml("<br/><br/>" + firmaHtmlSelezionata + "<br/>" + bodyHtml);
				}

			} else {
				// Il body è null -> metto solo la firma
				bean.setBodyHtml("<br/><br/>" + firmaHtmlSelezionata);
			}
		}

		return bean;
	}

	@Override
	public InvioMailBean call(InvioMailBean pInBean) throws Exception {
		return null;
	}

	/**
	 * Metodo che controlla se il body è vuoto. Il metodo è chiamato da lato client
	 * tramite passaggio del bean relativo. Il nuovo body viene inserito all'interno
	 * del bean
	 * 
	 * @param bean
	 *            bean di input
	 * @return il bean modificato con il nuovo corpo (se quello precedente era
	 *         costituito solo da tag html)
	 * @throws Exception
	 */
	public InvioMailBean checkIfBodyIsEmpty(InvioMailBean bean) throws Exception {

		String bodyHtml = bean.getBodyHtml();

		bodyHtml = checkBodyHtml(bodyHtml);

		bean.setBodyHtml(bodyHtml);
		bean.setBodyText(formatBodyText(bodyHtml));

		return bean;
	}

	/**
	 * Metodo che controlla se la stringa html che viene fornita in input è vuota o
	 * meno. Essendo html possono essere presenti dei tag o dei commenti che non
	 * vengono visualizzati a video all'utente. Si provano quindi a rimuovere questi
	 * tag e a verificare se la stringa in realtà è vuota o meno
	 * 
	 * @param bodyHtml
	 *            stringa html da controllare
	 * @return la stringa iniziale se ha un body valido, una stringa vuota nel caso
	 *         il body sia costituito da soli tag html
	 */
	public String checkBodyHtml(String bodyHtml) {

		String plainText = "";

		try {
			plainText = convertHtmlToPlainText(bodyHtml);
		} catch (Exception e) {
			log.warn(e);
		}

		if (!"".equals(plainText)) {
			// Se non è nullo quanto viene tornato
			return bodyHtml;
		} else {
			/*
			 * Allora, vuol dire che deve essere settato il testo vuoto
			 */
			return "";
		}
	}

	/**
	 * Il metodo controlla se, nella stringa che viene fornita in input, sono
	 * inseriti tag html o meno. I tag html vengono rimossi.
	 * 
	 * @param html
	 *            stringa da controllare
	 * @return stringa senza i tag html; quindi solo il testo presente nella stringa
	 *         di input
	 * @throws Exception
	 */
	private String convertHtmlToPlainText(String html) throws Exception {

		final StringBuilder sb = new StringBuilder();

		HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback() {

			@Override
			public void handleText(final char[] data, final int pos) {
				String s = new String(data);
				sb.append(s.trim());
			}

			@Override
			public void handleStartTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
				super.handleStartTag(t, a, pos);
			}

			@Override
			public void handleSimpleTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
				/*
				 * Se viene inserita soltanto un'immagine come firma questa dev'essere salvata.
				 * Controllo se il tag rilevato è img o meno; nel caso il tag sia img non viene
				 * cancellato
				 */

				if (modalita.equals("ConversioneTestoSemplice")) {

					// Se sto convertendo in testo semplice allora devo considerare i tag in modo
					// diverso
					if (t == HTML.Tag.BR) {
						sb.append("\n");
					}
				} else {
					if (t == HTML.Tag.IMG) {
						sb.append(t.toString());
					}
				}
				handleStartTag(t, a, pos);
			}

			@Override
			public void handleComment(char[] data, int pos) {
				super.handleComment(data, pos);
			}

			@Override
			public void handleError(String errorMsg, int pos) {
				super.handleError(errorMsg, pos);
			}
		};

		try {
			new ParserDelegator().parse(new StringReader(html), parserCallback, false);
		} catch (Exception e) {
			try {
				new ParserDelegator().parse(new StringReader(html), parserCallback, true);
			} catch (Exception e2) {
				throw new StoreException("Il corpo della mail contiene caratteri non consentiti");
			}
		}

		String plainText = sb.toString();
		plainText = plainText.replaceAll("&nbsp;", "");
		plainText = plainText.trim();

		return plainText;
	}

	private String formatBodyText(String bodyHtml) {

		/*
		 * Imposto la modalità in modo che nella conversione vengano mantenuti i
		 * caratteri per andare a capo
		 */
		modalita = "ConversioneTestoSemplice";

		try {
			bodyHtml = convertHtmlToPlainText(bodyHtml);
		} catch (Exception e) {
			log.warn(e);
		}

		// Reimposto la modalità come in precedenza
		modalita = "";

		return bodyHtml;

	}
}
