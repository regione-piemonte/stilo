/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.TipoFile;

public class AurigaFileUtils {

	private static Logger logger = Logger.getLogger(AurigaFileUtils.class);

	/**
	 * Rimpiazza i . con _
	 * 
	 * @param nomeFile
	 * @return
	 */
	public static String pulisciNomeFile(String nomeFile) {
		if (StringUtils.isNotBlank(nomeFile))
			return nomeFile.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
		else
			return null;
	}

	public static FileInfoBean createFileInfoBean(File currentFile, String improntaDefaultAlgoritmo,
			String improntaDefaultEncoding) throws IOException, MimeTypeParseException {
		String impronta = calcolaImpronta(currentFile, improntaDefaultAlgoritmo, improntaDefaultEncoding);

		MimeType mimeType = AurigaFileUtils.getMimeType(currentFile);

		GenericFile genericFile = new GenericFile();
		genericFile.setMimetype(mimeType.getPrimaryType() + "/" + mimeType.getSubType());
		genericFile.setDisplayFilename(currentFile.getName());
		genericFile.setImpronta(impronta);
		genericFile.setAlgoritmo(improntaDefaultAlgoritmo);
		genericFile.setEncoding(improntaDefaultEncoding);

		FileInfoBean fileInfoBean = new FileInfoBean();
		fileInfoBean.setTipo(TipoFile.ALLEGATO);
		fileInfoBean.setAllegatoRiferimento(genericFile);

		return fileInfoBean;
	}

	/**
	 * Calcola l'impronta del file specificato, utilizzando l'algoritmo e
	 * l'encoding forniti
	 * 
	 * @param file
	 * @param algoritmo
	 * @param encoding
	 * @return l'encoded hash oppure null se algoritmo od encoding non sono
	 *         validi oppure se si è verificata un'eccezione
	 * @throws Exception
	 */
	public static String calcolaImpronta(File file, String algoritmo, String encoding) {

		BufferedInputStream bis = null;
		byte[] hash = null;
		String encodedHash = null;

		try {

			if (StringUtils.isBlank(algoritmo)) {
				logger.error("Algoritmo per il calcolo dell'impronta non valorizzato");
			} else {
				bis = new BufferedInputStream(new FileInputStream(file));
				if (algoritmo.equalsIgnoreCase("SHA-256")) {
					hash = DigestUtils.sha256(bis);
				}/* else if (algoritmo.equalsIgnoreCase("SHA-1")) {
					hash = org.apache.commons.codec.digest.DigestUtils.sha1(bis);
				} */else {
					logger.error(String.format("L'algoritmo %1$s non è uno di quelli previsti", algoritmo));
				}
			}

			if (StringUtils.isBlank(encoding)) {
				logger.error("Encoding per il calcolo dell'impronta non valorizzato");
			} else if (encoding.equalsIgnoreCase("base64")) {
				encodedHash = Base64.encodeBase64String(hash);
			} else if (encoding.equalsIgnoreCase("hex")) {
				encodedHash = Hex.encodeHexString(hash);
			} else {
				logger.error(String.format("L'encoding %1$s non è uno di quelli previsti", encoding));
			}
		} catch (Exception e) {
			logger.error("Durante il calcolo dell'impronta si è verificata la seguente eccezione", e);
		} finally {
			try {
				bis.close();
			} catch (IOException ioe) {
			}
		}

		return encodedHash;

	}

	/**
	 * Estrae il contenuto di un file, ritornandolo in formato stringa
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File src) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(src));
		StringBuilder builder = new StringBuilder();
		String nl = System.getProperty("line.separator");

		String readLine = null;

		while ((readLine = reader.readLine()) != null) {
			builder.append(readLine).append(nl);
		}

		reader.close();

		return builder.toString();
	}

	/**
	 * Determina il mime type del file specificato
	 * 
	 * @param src
	 * @return
	 * @throws MimeTypeParseException
	 */
	public static MimeType getMimeType(File src) throws IOException, MimeTypeParseException {

		Tika tika = new Tika();
		String mimeType = tika.detect(src);

		return new MimeType(mimeType);

	}

	/**
	 * Determina il mime type del file specificato
	 * 
	 * @param src
	 * @return
	 * @throws MimeTypeParseException
	 * @throws IOException
	 */
	public synchronized static MimeType getMimeTypeSynchronized(File src) throws IOException, MimeTypeParseException {

		return getMimeType(src);
	}

	/**
	 * Crea una directory temporanea e la imposta per la cancellazione
	 * all'uscita dalla jvm
	 * 
	 * @return {@link java.io.File} il riferimento alla directory
	 * @throws Exception
	 */
	public static File createTempDirectory() throws Exception {
		File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if (!temp.delete())
			throw new IOException((new StringBuilder()).append("Could not delete temp file: ")
					.append(temp.getAbsolutePath()).toString());
		if (!temp.mkdirs()) {
			throw new IOException((new StringBuilder()).append("Could not create temp directory: ")
					.append(temp.getAbsolutePath()).toString());
		} else {
			temp.deleteOnExit();
			return temp;
		}
	}
}
