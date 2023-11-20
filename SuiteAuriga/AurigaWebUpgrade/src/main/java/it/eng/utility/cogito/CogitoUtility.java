/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TemporaryResources;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.cogito.config.AurigaCogitoClientConfig;
import it.eng.utility.cogito.exception.AurigaCogitoException;
import it.eng.utility.cogito.response.CategorizzazioneCogitoBean;
import it.eng.utility.cogito.response.CogitoResponseBean;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class CogitoUtility {

	private static final Logger logger = Logger.getLogger(CogitoUtility.class);
	private static final long MEGABYTE = 1024L * 1024L;
	private static final BigDecimal minMbSize = new BigDecimal(0);
	private static String maxNroTitolazioni;
	private static String maxMbSize;
	private Date startCall;
	private Date endCall;
	private String error="";

	/**
	 * Effettua la chiamata a Cogito passandogli il contenuto di tutti i file in
	 * input e restituisce un oggett {@link CogitoResponseBean}
	 * 
	 * @param cogitoEndpoint
	 * @param cogitoUser
	 * @param cogitoPassword
	 * @param minOutSizeMB
	 *            parametro minimo 0 MB
	 * @param maxOutSizeMB
	 *            parametri massimo 10 MB
	 * @param filesStream
	 * @return
	 * @throws AurigaCogitoException
	 */
	public CogitoResponseBean submitFilesToCogito(List<File> filesStream) throws AurigaCogitoException {
		CogitoServices services = getCogitoServicesClient();			
		
		if (filesStream.size() == 0) {
			startCall = new Date();
			endCall = new Date();
			throw new AurigaCogitoException("Nessun file selezionato per l'invocazione di Cogito.");
		}
		
		// 1. Effettuo il merge dei contenuti dei files in input in un unico
		// file di testo
		String wsInput = mergeFilesText(minMbSize, new BigDecimal(maxMbSize), filesStream);

		// 3. invio del testo a Cogito
		long start = System.currentTimeMillis();
		startCall = new Date();
		String cogitoResponse = callCogito(services, wsInput);
		long stop = System.currentTimeMillis();
		endCall = new Date();
		logger.debug("Invocazione ws Rest COGITO inizio chiamata=" + start + " fine chiamata=" + stop);

		// definisco la durata della chiamata in secondi
		BigDecimal durataChiamata = (new BigDecimal(stop).subtract(new BigDecimal(start), MathContext.DECIMAL128))
				.divide(new BigDecimal(1000), 0, RoundingMode.HALF_UP);

		// 3. Parsing della risposta
		CogitoResponseBean cr = cogitoResponseParser(cogitoResponse);
		cr.setDurataChiamata(durataChiamata.intValue());

		return cr;
	}

	/**
	 * 
	 * Chiamata a cogito effettuata con HttpClient
	 * 
	 * @param testo
	 *            da sottoporre a Cogito
	 * @return response xml
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String callCogito(CogitoServices services, String inputText) throws AurigaCogitoException {
		try {
			return services.integrazioneClassificazioneCogito(inputText);
		} catch (Exception e) {
			endCall = new Date();
			error = "Il servizio Cogito non risponde. Riprovare più tardi "+ e;
			throw new AurigaCogitoException("Il servizio Cogito non risponde. Riprovare più tardi", e);
		}
	}

	/**
	 * 
	 * Il metodo riceve in input n files in formato inputStream e restituisce
	 * una stringa composta dall'unione dei contenuti di tutti i files
	 * 
	 * @param stream
	 *            dei file da sottoporre a Cogito
	 * @return Stringa formata dall'unione del contenuto di tutti i file
	 * @throws IOException
	 */
	private String mergeFilesText(BigDecimal minOutSizeMB, BigDecimal maxOutSizeMB, List<File> fileStream)
			throws AurigaCogitoException {
		StringBuffer sb = new StringBuffer();
		BigDecimal filesTextSizeMB = new BigDecimal(0);
		try {
			for (int i = 0; i < fileStream.size(); i++) {
				File fileIn = fileStream.get(0);
				Tika tika = new Tika();				
				TemporaryResources tmp = new TemporaryResources();						
				try {
					File tmpFile = tmp.createTemporaryFile();
					String estensioneFile = getEstensioneFile(fileIn);
					if (estensioneFile != null && !estensioneFile.equals("") && estensioneFile.equalsIgnoreCase("application/vnd.oasis.opendocument.text")) {
						InputStream stream = new FileInputStream(fileIn);
						InputStream pdfStream = convertiFileToPdf(stream);
						try(OutputStream outputStream = new FileOutputStream(tmpFile)){
						    IOUtils.copy(pdfStream, outputStream);
						} finally {
							stream.close();
							pdfStream.close();
						}
					} else {
						FileUtils.copyFile(fileIn, tmpFile);
					}
					String content = "";
					try (TikaInputStream tis = TikaInputStream.get(tmpFile)) {
						content = extractContentUsingParser(tis);	
		            }					
					sb.append(content);
					sb.append("\n");
				} catch (Exception e) {
					logger.error("Errore in fase di estrazione del contenuto dei file da sottoporre a Cogito", e);
					sb.append("");					
				} finally {
					tmp.dispose();					
				}				
			}

			// 1.1 preprocessing file
			// rimozione righe vuote
			// TODO aggiungere altre regole???
			String filesText = sb.toString().replaceAll("(?m)^\\s+$", ""); 

			filesTextSizeMB = new BigDecimal(filesText.getBytes("UTF-8").length).divide(new BigDecimal(MEGABYTE), 2);
		} catch (Exception e) {
			throw new AurigaCogitoException(
					"Si è verificato un errore in fase di estrazione del contenuto dei file da sottoporre a Cogito.",
					e);
		}

		// 1.2 controllo su size del messaggio da sottoporre a Cogito
		if (filesTextSizeMB.compareTo(minOutSizeMB) <= 0) {
			throw new AurigaCogitoException("I file selezionati non risultano contenere del testo.");
		} else if (filesTextSizeMB.compareTo(maxOutSizeMB) >= 0) {
			throw new AurigaCogitoException("La dimensione del file di test da sottoporre a Cogito (" + filesTextSizeMB
					+ " MB) risulta essere maggiore del massimo valore consentito (" + maxOutSizeMB + " MB).");
		} else {
			return sb.toString();
		}
	}

	private String extractContentUsingParser(InputStream stream) throws IOException, TikaException, SAXException {
		Parser parser = new AutoDetectParser();
		ContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();
		try {
			parser.parse(stream, handler, metadata, context);
			return handler.toString();
		}
		catch (Exception e) {
			logger.error("Errore in fase di parser file da sottoporre a Cogito", e);
			return "";
		}
	}

	/**
	 * Effettua il parsing della response xml di Cogito restituendo
	 * 
	 * @param responseXml
	 *            di Cogito
	 * @throws AurigaCogitoException
	 */
	private CogitoResponseBean cogitoResponseParser(String response) throws AurigaCogitoException {

		CogitoResponseBean cr = new CogitoResponseBean();
		List<CategorizzazioneCogitoBean> listaCategorizzazioni = new ArrayList<>();

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
			doc.getDocumentElement().normalize();

			NodeList nodeDescriptorList = doc.getElementsByTagName("descriptor");
			for (int idxDescriptor = 0; idxDescriptor < nodeDescriptorList.getLength(); idxDescriptor++) {
				String freqValue = "0";
				String hierarchyValue = "";
				String pathValue = "";

				// 1.3 soglia massima di titolazioni da estrarre
				if (new BigDecimal(maxNroTitolazioni).compareTo(new BigDecimal(idxDescriptor)) < 0) {
					break;
				}

				Node nodeDescriptor = nodeDescriptorList.item(idxDescriptor);

				String descrCategorizzazione = nodeDescriptor.getAttributes().getNamedItem("label").getNodeValue();
				String codeCategorizzazione = nodeDescriptor.getAttributes().getNamedItem("name").getNodeValue();

				NodeList nodeAttributeList = nodeDescriptor.getChildNodes();
				for (int idxAttribute = 0; idxAttribute < nodeAttributeList.getLength(); idxAttribute++) {
					Node nodeAttribute = nodeAttributeList.item(idxAttribute);
					if (nodeAttribute.getNodeType() == Node.ELEMENT_NODE) {
						String attributeName = nodeAttribute.getAttributes().getNamedItem("name").getNodeValue();
						if (attributeName != null && attributeName.equals("FREQ")) {
							freqValue = nodeAttribute.getAttributes().getNamedItem("value").getNodeValue();
						}
						if (attributeName != null && attributeName.equals("hierarchy")) {
							hierarchyValue = nodeAttribute.getAttributes().getNamedItem("value").getNodeValue();
						}
						if (attributeName != null && attributeName.equals("path")) {
							pathValue = nodeAttribute.getAttributes().getNamedItem("value").getNodeValue();
						}

					}
				}

				listaCategorizzazioni.add(new CategorizzazioneCogitoBean(codeCategorizzazione, descrCategorizzazione,
						new BigDecimal(freqValue), hierarchyValue, pathValue));
			}

			Collections.sort(listaCategorizzazioni);
			cr.setListaCategorizzazioni(listaCategorizzazioni);
			cr.setRisposta(response);
			return cr;

		} catch (Exception e) {
			throw new AurigaCogitoException("Si è verificato un errore in fase di parsing della risposta di Cogito", e);
		}
	}

	/**
	 * Inizializzazione client per la chiamata Rest del ws Cogito
	 * 
	 * @return
	 * @throws AurigaCogitoException
	 */
	private CogitoServices getCogitoServicesClient() throws AurigaCogitoException {
		AurigaCogitoClientConfig lProxyCogitoClientConfig = (AurigaCogitoClientConfig) SpringAppContext.getContext().getBean("CogitoClientConfig");
		CogitoServices lCogitoServices = new CogitoServices();		
		maxNroTitolazioni = lProxyCogitoClientConfig.getMaxNroTitolazioni();
		maxMbSize = lProxyCogitoClientConfig.getMaxMbSize();
		return lCogitoServices;
	}

	public Date getStartCall() {
		return startCall;
	}

	public Date getEndCall() {
		return endCall;
	}

	public String getError() {
		return error;
	}
	
	private InputStream convertiFileToPdf(InputStream lInputStream) throws AurigaCogitoException {
		try {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			String fileUrl = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().storeStream(lInputStream, new String[] {}))
					.toURI().toString();
			return lInfoFileUtility.converti(fileUrl, "");
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			throw new AurigaCogitoException("Non è stato possibile convertire il file");
		}
	}
	
	private String getEstensioneFile (File file) throws Exception {
		String result = "";
		MimeTypeFirmaBean infoFromFile = new InfoFileUtility().getInfoFromFile(file.toURI().toString(), file.getName(), false, null);
		result = infoFromFile.getMimetype();
		return result;
	}

}
