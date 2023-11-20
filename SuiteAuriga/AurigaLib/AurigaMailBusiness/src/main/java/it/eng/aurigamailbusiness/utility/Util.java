/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import it.eng.aurigamailbusiness.sender.AccountConfigKey;

public class Util {

	private static Properties defaultProperties;

	/**
	 * Funzione che pulisce il valore in input dai caratteri non ASCII
	 * 
	 * @param value
	 * @return
	 */

	public static String clearNotASCII(String value) {

		String result = value;

		if (StringUtils.isNotBlank(value)) { // escludo caratteri di controllo
												// tranne carriage return e
												// tabulazioni

			for (int indexASCII = 0; indexASCII <= 31; indexASCII++) {

				if (indexASCII < 10 || indexASCII > 13) {

					result = result.replaceAll(Character.toString((char) indexASCII), "");
				}
			}
		}

		result = result.replaceAll(Character.toString((char) 127), "");

		result = result.replaceAll(Character.toString((char) 139), "'");

		result = result.replaceAll(Character.toString((char) 145), "'");

		result = result.replaceAll(Character.toString((char) 146), "'");

		result = result.replaceAll(Character.toString((char) 147), "\"");

		result = result.replaceAll(Character.toString((char) 148), "\"");

		result = result.replaceAll(Character.toString((char) 149), "\"");

		result = result.replaceAll(Character.toString((char) 150), "-");

		result = result.replaceAll(Character.toString((char) 151), "-");

		result = result.replaceAll(Character.toString((char) 191), "");

		result = result.replaceAll(Character.toString((char) 196), "-");

		result = result.replaceAll(Character.toString((char) 96), "'");

		result = result.replaceAll(Character.toString((char) 180), "'");

		result = result.replaceAll("\\p{Pd}", "-"); // sostituzione di eventuali
													// dash in altri formati

		result = result.replaceAll("[\\u2018\\u2019]", "'"); // sostituzione di
																// eventuali e
																// ulteriori
																// apici

		result = result.replaceAll("[\\u201C\\u201D]", "\""); // sostituzione di
																// eventuali e
																// ulteriori
																// doppi apici

		return result;

	}

	/**
	 * Metodo che ricerca i carriage return e li sostituisce con degli spazi
	 * 
	 * @param original
	 * @return
	 */

	public static String clearCarriageReturn(String original) {
		if (StringUtils.isNotEmpty(original)) {
			Document doc = Jsoup.parseBodyFragment(original);
			if (!doc.body().text().equals(original)) {
				// E' html
				String result = Jsoup.parseBodyFragment(original.replaceAll("\r\n", "br2nl").replaceAll("\n", "br2nl").replaceAll("\r", "br2nl")
						.replaceAll("</br>", "br2nl").replaceAll("<br>", "br2nl").replaceAll("<br/>", "br2nl")).text();
				result = result.replaceAll("br2nl ", "\n").replaceAll("br2nl", "\n").trim().replaceAll("\n", " ").trim();
				return result;
			} else
				return original;
		} else
			return original;
	}

	/**
	 * Metodo che aggiunge le proprietà di javaMail ad un insieme in input
	 * 
	 * @param properties
	 * @throws Exception
	 */

	public static void setJavaMailDefaultProperties() throws Exception {

		Properties properties = System.getProperties();

		// attenzione al fatto che Properties in realtà è una hashtable e quindi
		// non ammette valori NULL

		final String MAIL_TIMEOUT = "120000"; // imposto un timeout per tutte le
												// operazioni, altrimenti di
												// default sarebbe infinito

		// impostazioni dei parametri javamail e imap da impostare in fase di
		// inizializzazione di AurigaMail
		// valide per tutte le mailbox, i parametri specifici invece vanno
		// inseriti in MAILBOX_ACCOUNT_CONFIG

		// Impostato a true, un valore sconosciuto nell'header
		// Content-Transfer-Encoding sarà
		// ignorato e sarà
		// impostata la codifica "8bit", senza che siano lanciate eccezioni
		properties.setProperty(AccountConfigKey.MAIL_MIME_IGNORE_UNKNOWN_ENCODING.keyname(), "true");

		// Impostato a true, il decoder BASE64 ignorerà errori nel
		// dato, restituendo EOF.
		// Questo può essere utile
		// quando si tratta con messaggi non codificati correttamente che
		// contengono altri
		// dati alla fine dello stream. In caso di errori comunque la decofica
		// viene
		// interrotta.
		properties.setProperty(AccountConfigKey.MAIL_MIME_BASE64_IGNORE_ERRORS.keyname(), "true");

		// Impostato a true, le linee "being" or "end" mancanti in un documento
		// uuencoded saranno
		// ignorate, senza che siano lanciate eccezioni
		properties.setProperty(AccountConfigKey.MAIL_MIME_UUDECODE_IGNORE_MISSING_BEGIN_END.keyname(), "true");

		// Impostato a true, errori nel documento uuencoded saranno ignorati,
		// senza che siano
		// lanciate
		// eccezioni
		properties.setProperty(AccountConfigKey.MAIL_MIME_UUDECODE_IGNORE_ERRORS.keyname(), "true");

		// Impostato a true, se l'header Content-Type di un contentuto
		// multipart non ha un
		// parametro di boundary, allora nel parsing si cercherà la
		// prima linea nel contenuto che sembra un boundary e si estrae
		// il parametro di boundary dalla linea. Viceversa sarà
		// sollevata un'eccezione MessagingException
		properties.setProperty(AccountConfigKey.MAIL_MIME_MULTIPART_IGNORE_MISSING_BOUNDARY_PARAMETER.keyname(), "true");

		// Normalmente il parametro boundary
		// nell'header Content-Type di
		// un body multipart è usata come separatore
		// fra le parti del body. Impostando il parametro a true, impone
		// al parser di cercare per una linea nel multipart body che
		// sembti una linea boundary e usi
		// questo valore come separatore fra le parti sequenziali. Può
		// essere utile nei casi in cui un antivirus
		// riscriva il messagio non correttamente, così i parametri di
		// boundary non corrisponderebbero più
		properties.setProperty(AccountConfigKey.MAIL_MIME_MULTIPART_IGNORE_EXISTING_BOUNDARY_PARAMETER.keyname(), "true");

		properties.setProperty(AccountConfigKey.IMAP_PARTIAL_FETCH.keyname(), "false"); // disabilito
																						// il
																						// fetch
																						// parziale
																						// dal
																						// server
																						// in
																						// download
		properties.setProperty(AccountConfigKey.IMAPS_PARTIAL_FETCH.keyname(), "false"); // disabilito
																							// il
																							// fetch
																							// parziale
																							// dal
																							// server
																							// in
																							// download

		properties.setProperty(AccountConfigKey.IMAP_FETCH_SIZE.keyname(), "1048576"); // imposto
																						// il
																						// fetchsize
																						// a
																						// 1MB,
																						// crea
																						// dei
																						// pacchetti
																						// da
																						// 1MB
																						// l'uno
		properties.setProperty(AccountConfigKey.IMAPS_FETCH_SIZE.keyname(), "1048576"); // imposto
																						// il
																						// fetchsize
																						// a
																						// 1MB,
																						// crea
																						// dei
																						// pacchetti
																						// da
																						// 1MB
																						// l'uno

		properties.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// connection
																									// timeout
																									// in
																									// millisecondi
		properties.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																						// read
																						// connection
																						// timeout
																						// in
																						// millisecondi
		properties.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																								// write
																								// connection
																								// timeout
																								// in
																								// millisecondi

		properties.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// connection
																									// timeout
																									// in
																									// millisecondi
		properties.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																						// read
																						// connection
																						// timeout
																						// in
																						// millisecondi
		properties.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																								// write
																								// connection
																								// timeout
																								// in
																								// millisecondi

		properties.setProperty(AccountConfigKey.IMAP_PEEK.keyname(), "true");
		properties.setProperty(AccountConfigKey.IMAPS_PEEK.keyname(), "true");

		// aggiunta del parametro per la gestione del nome dei filename più lunghi di 60. Gestione dell'RFC 2231
		properties.setProperty(AccountConfigKey.MAIL_MIME_ENCODEPARAMETERS.keyname(), "false");
		
		defaultProperties = properties;

		if (MailUtil.log.isDebugEnabled()) {
			MailUtil.log.debug("Javamail proprietà di default: " + properties.toString());
		}

	}

	/**
	 * Metodo che aggiunge le proprietà di javaMail ad un insieme in input
	 * 
	 * @param properties
	 * @throws Exception
	 */

	public static Properties getJavaMailDefaultProperties() throws Exception {

		if (defaultProperties == null || defaultProperties.isEmpty()) {
			setJavaMailDefaultProperties();
		}

		return SerializationUtils.clone(defaultProperties);

	}

	/**
	 * rimuove i doppioni dalla lista (case insensitive)
	 * 
	 * @param in
	 * @return
	 */
	public static List<String> rimuoviDoppioniDaLista(List<String> in) {
		if (in != null && !in.isEmpty()) {
			SortedSet<String> hs = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
			hs.addAll(in);
			return new ArrayList<String>(hs);
		}
		return in;
	}

}
