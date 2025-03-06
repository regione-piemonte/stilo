package it.eng.utility.cryptosigner.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.tsp.TimeStampToken;

import it.eng.utility.FileUtil;
import it.eng.utility.cryptosigner.controller.MasterSignerController;
import it.eng.utility.cryptosigner.controller.MasterTimeStampController;
import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.InputTimeStampBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputTimeStampBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.CAdESSigner;
import it.eng.utility.cryptosigner.data.PdfSigner;
import it.eng.utility.cryptosigner.data.SignerUtil;
import it.eng.utility.cryptosigner.data.XMLSigner;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.NoSignerException;

/**
 * Definisce il punto di ingresso per l�esecuzione dei controlli sulle firme e marche temporali. <br/>
 * Prevede l'esecuzione dei controlli a partire da 6 possibili configurazioni, corrispondenti ai diversi formati di firma trattati.<br/>
 * Ovvero:
 * <ol>
 * <li><b>Contenuto&Firma</b> - unico file contenente la firma e l'oggetto firmato. Esempio: P7M</li>
 * <li><b>Contenuto&Firma&Timestamp</b> - unico file contenente la firma, l'oggetto firmato e la marca temporale.Esempio: M7M / CaDES / XaDES Embedded</li>
 * <li><b>Contenuto&Firma + Timestamp</b> - 2 file contenenti rispettivamente: la firma con il contenuto firmato, il timestampEsempio: P7M + TSR</li>
 * <li><b>Contenuto + Firma</b> - 2 file contenenti rispettivamente: l'oggetto della firma, la firmaEsempio: fileDetached + P7M</li>
 * <li><b>Contenuto + Firma&TimeStamp</b> - 2 file contenenti rispettivamente: l'oggetto della firma, la firma con il timestampEsempio: fileDetached + M7M /
 * CaDES [/ XaDES] Detached</li>
 * <li><b>Contenuto + Firma + Timestamp</b> - 3 file contenenti rispettivamente: l'oggetto della firma, la firma, il timestampEsempio: fileDetached + P7M + TSR
 * </li>
 * </ol>
 * Per evitare ambiguita', i metodi precedenti devono essere richiamati racchiudendo le marche temporali estensive all�interno di un array.<br/>
 * L'esecuzione attiva l'analisi dei file, che puo' essere suddivisa in 3 fasi:
 * <ol>
 * <li>Estrazione della marca temporale: viene richiamato il filtro di analisi della marca temporale sul file con timestamp embedded o detached</li>
 * <li>Controllo delle firme sulla busta: richiama il popolando il bean di input con i dati estratti dalla fase precedente</li>
 * <li>Verifica del contenuto della busta e iterazione dei controlli sulla firma</li>
 * <li>Se il contenuto puo' essere a sua volta firmato , viene richiamato il controllo al passo 2, estraendo il contenuto della busta su file e considerando
 * questo come un file di tipo Contenuto&Firma&Timestamp (configurazione 2)</li>
 * </ol>
 * </p>
 * 
 * @author Stefano Zennaro
 *
 */
public class SignatureManager {

	MasterTimeStampController masterTimeStampController;

	MasterSignerController masterSignerController;

	/**
	 * File contenente l'oggetto della firma
	 */
	public File detachedContentFile;

	/**
	 * File contenente la firma
	 */
	public File signatureFile;

	/**
	 * File contenente la firma e l'oggetto della firma
	 */
	public File signatureWithContentFile;

	/**
	 * File contenente la firma e l'eventuale timestamp
	 */
	public File timeStampedSignatureFile;

	/**
	 * File contenente il timestamp
	 */
	public File timeStampFile;

	/**
	 * File contenente la firma, l'oggetto firmato e l'eventuale timestamp
	 */
	public File timeStampedSignatureWithContentFile;

	public String fileName;
	protected AbstractSigner signer = null;
	protected Boolean isSigned = null;

	/**
	 * Lista dei file corrispondenti alla catena di marche temporali
	 */
	protected File[] timeStampsChain;

	/**
	 * Configurazione del metodo di analisi
	 */
	protected SignatureManagerConfig config;

	/**
	 * Data di riferimento temporale (per la validazione dei certificati di firma)
	 */
	protected Date referenceDate;

	/**
	 * Flag che indica se iterare i controlli sul contenuto sbustato
	 */
	protected boolean singleStep = false;

	protected boolean childValidation = true;

	/**
	 * Falg indicante se l'oggetto timeStampedSignatureWithContentFile è un oggetto temporaneo
	 * 
	 */
	protected boolean timeStampedSignatureWithContentFileIsTemporary = false;

	/**
	 * Ausiliario per recuperare il signer
	 */
	protected SignerUtil signerUtil = SignerUtil.newInstance();

	protected static Logger log = LogManager.getLogger(SignatureManager.class);

	/**
	 * Metodo che chiude il signature manager e cancella la directory temporanea utilizzata per il controllo dei file
	 * 
	 * @throws IOException
	 * 
	 */
	public void close() throws IOException {
		FileUtils.deleteDirectory(this.masterSignerController.getDedicatedTemporyDirectory());
	}

	public OutputTimeStampBean executeMassiveTimeStamp(File timestamp, File[] contentFile) {
		return null;
	}

	/**
	 * Avvia l'esecuzione dei controlli a partire dalla configurazione precedentemente definita con la chiamata al metodo
	 * 
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean execute() throws CryptoSignerException {
		return execute(this.config);
	}

	/**
	 * Avvia l'esecuzione dei controlli a partire dalla configurazione passata in ingresso
	 * 
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean execute(SignatureManagerConfig config) throws CryptoSignerException {
		//log.info("METODO EXECUTE");
		reset();
		if (config == null || !config.isValid())
			throw new CryptoSignerException("Configurazione non valida");
		it.eng.utility.cryptosigner.manager.SignatureManager.CONFIGURATION configuration = config.getConfiguration();
		switch (configuration) {
		case CONFIG_1_2:
			timeStampedSignatureWithContentFile = config.getContentFile();
			break;
		case CONFIG_3:{
			//log.info("Setto timeStampedSignatureWithContentFile " + config.getContentFile());
			timeStampedSignatureWithContentFile = config.getContentFile();
			signatureWithContentFile = config.getContentFile();
			timeStampFile = config.getTimeStampFile();
			break;
		}
		case CONFIG_4_5:
			detachedContentFile = config.getContentFile();
			timeStampedSignatureFile = config.getSignatureFile();
			break;
		case CONFIG_6:
			detachedContentFile = config.getContentFile();
			signatureFile = config.getSignatureFile();
			timeStampFile = config.getTimeStampFile();
		default:
			break;
		}
		timeStampsChain = config.getTimeStampExtensions();
		this.referenceDate = config.getReferenceDate();
		return run();
	}

	/*
	 * #################################################################################### # Modalia' di analisi EMBEDDED - unico file contenente firma e
	 * contenuto
	 */

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp embedded
	 * 
	 * @param timeStampedSignatureWithContentFile
	 *            contenente firma, contenuto firmato ed eventuale marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File timeStampedSignatureWithContentFile) throws CryptoSignerException {
		return executeEmbedded(timeStampedSignatureWithContentFile, (Date) null);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp detached
	 * 
	 * @param signatureWithContentFile
	 *            firma e contenuto firmato
	 * @param timeStampFile
	 *            marca temporale detached
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File signatureWithContentFile, File timeStampFile, String fileName) throws CryptoSignerException {
		return executeEmbedded(signatureWithContentFile, timeStampFile, fileName, (Date) null);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded e marche embedded, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param timeStampedSignatureWithContentFile
	 *            file contenente firma, contenuto firmato ed eventuale marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File timeStampedSignatureWithContentFile, File... timeStampExtensionFiles) throws CryptoSignerException {
		return executeEmbedded(timeStampedSignatureWithContentFile, (Date) null, timeStampExtensionFiles);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param signatureWithContentFile
	 *            file contenente firma e contenuto firmato
	 * @param timeStampFile
	 *            marca temporale detached
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File signatureWithContentFile, File timeStampFile, String fileName, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		return executeEmbedded(signatureWithContentFile, timeStampFile, fileName, (Date) null, timeStampExtensionFiles);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp embedded
	 * 
	 * @param timeStampedSignatureWithContentFile
	 *            contenente firma, contenuto firmato ed eventuale marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File timeStampedSignatureWithContentFile, String fileName, Date reference) throws CryptoSignerException {
		reset();
		log.info("Ricevo il file " + timeStampedSignatureWithContentFile + " e il nome file " + fileName);
		this.timeStampedSignatureWithContentFile = timeStampedSignatureWithContentFile;
		this.fileName = fileName;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp detached
	 * 
	 * @param signatureWithContentFile
	 *            firma e contenuto firmato
	 * @param timeStampFile
	 *            marca temporale detached
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File signatureWithContentFile, Date reference, File timeStampFile) throws CryptoSignerException {
		reset();
		this.signatureWithContentFile = signatureWithContentFile;
		this.timeStampFile = timeStampFile;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded e marche embedded, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param timeStampedSignatureWithContentFile
	 *            file contenente firma, contenuto firmato ed eventuale marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File timeStampedSignatureWithContentFile, Date reference, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		reset();
		this.timeStampedSignatureWithContentFile = timeStampedSignatureWithContentFile;
		this.timeStampsChain = timeStampExtensionFiles;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme embedded con timestamp detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param signatureWithContentFile
	 *            file contenente firma e contenuto firmato
	 * @param timeStampFile
	 *            marca temporale detached
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeEmbedded(File signatureWithContentFile, File timeStampFile, String fileName, Date reference, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		reset();
		log.info("Metodo executeEmbedded con file  " + signatureWithContentFile  + "timeStampFile " + timeStampFile + " fileName " + fileName);
		this.signatureWithContentFile = signatureWithContentFile;
		this.timeStampedSignatureWithContentFile= signatureWithContentFile;
		this.timeStampFile = timeStampFile;
		this.fileName = fileName;
		this.timeStampsChain = timeStampExtensionFiles;
		this.referenceDate = reference;
		return run();
	}

	/*
	 * #################################################################################### # Modalit� di analisi DETACHED - un file per la firma e uno per
	 * contenuto
	 */

	/**
	 * Avvia l'esecuzione dei controlli su firme detached
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param timeStampedSignatureFile
	 *            firma digitale con eventuale marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File timeStampedSignatureFile) throws CryptoSignerException {
		return executeDetached(detachedContentFile, timeStampedSignatureFile, (Date) null);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached con timestamp detached
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param signatureFile
	 *            firma digitale
	 * @param timeStampFile
	 *            marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File signatureFile, File timeStampFile) throws CryptoSignerException {
		return executeDetached(detachedContentFile, signatureFile, timeStampFile, (Date) null);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param timeStampedSignatureFile
	 *            firma digitale con eventuale marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File timeStampedSignatureFile, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		return executeDetached(detachedContentFile, timeStampedSignatureFile, (Date) null, timeStampExtensionFiles);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached con timestamp detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param signatureFile
	 *            firma digitale
	 * @param timeStampFile
	 *            marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File signatureFile, File timeStampFile, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		return executeDetached(detachedContentFile, signatureFile, timeStampFile, (Date) null, timeStampExtensionFiles);
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param timeStampedSignatureFile
	 *            firma digitale con eventuale marca temporale
	 * @param reference
	 *            data di riferimento temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File timeStampedSignatureFile, Date reference) throws CryptoSignerException {
		reset();
		this.detachedContentFile = detachedContentFile;
		this.timeStampedSignatureFile = timeStampedSignatureFile;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached con timestamp detached
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param signatureFile
	 *            firma digitale
	 * @param timeStampFile
	 *            marca temporale
	 * @param reference
	 *            data di riferimento temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File signatureFile, File timeStampFile, Date reference) throws CryptoSignerException {
		reset();
		this.detachedContentFile = detachedContentFile;
		this.signatureFile = signatureFile;
		this.timeStampFile = timeStampFile;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param timeStampedSignatureFile
	 *            firma digitale con eventuale marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @param reference
	 *            data di riferimento temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File timeStampedSignatureFile, Date reference, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		reset();
		this.detachedContentFile = detachedContentFile;
		this.timeStampedSignatureFile = timeStampedSignatureFile;
		this.timeStampsChain = timeStampExtensionFiles;
		this.referenceDate = reference;
		return run();
	}

	/**
	 * Avvia l'esecuzione dei controlli su firme detached con timestamp detached, eseguendo la validazione della catena di estensioni della marca temporale
	 * 
	 * @param detachedContentFile
	 *            contenuto firmato
	 * @param signatureFile
	 *            firma digitale
	 * @param timeStampFile
	 *            marca temporale
	 * @param timeStampExtensionFiles
	 *            catena di estensioni della marca temporale
	 * @param reference
	 *            data di riferimento temporale
	 * @return bean di output contenente i risultati dell'analisi
	 * @throws CryptoSignerException
	 */
	public OutputSignerBean executeDetached(File detachedContentFile, File signatureFile, File timeStampFile, Date reference, File... timeStampExtensionFiles)
			throws CryptoSignerException {
		reset();
		this.detachedContentFile = detachedContentFile;
		this.signatureFile = signatureFile;
		this.timeStampFile = timeStampFile;
		this.timeStampsChain = timeStampExtensionFiles;
		this.referenceDate = reference;
		return run();
	}

	protected void reset() {
		this.detachedContentFile = null;
		this.signatureFile = null;
		this.timeStampFile = null;
		this.timeStampedSignatureFile = null;
		this.timeStampsChain = null;
		this.signatureWithContentFile = null;
		this.timeStampedSignatureWithContentFile = null;
		this.referenceDate = null;
		this.signer = null;
		this.isSigned = null;
	}

	private OutputSignerBean run() {
		CONFIGURATION configuration = getConfiguration();

		// Esegue il primo ciclo di controllo
		log.info("Eseguo il primo ciclo di controllo " + configuration);
		OutputSignerBean outputSigner = executeCycle(configuration, referenceDate, true);

		// Se si vuole eseguire il controllo anche sul contenuto sbustato
		log.info("Verifica ricorsiva? " + !singleStep);
		if (!singleStep) {

			// Esegue il controllo sul contenuto sbustato
			OutputSignerBean currentOutput = outputSigner;
			// log.debug("CurrentOutput " + currentOutput );
			if (currentOutput != null) {
				// log.debug("CurrentOoutput.getContent() " + currentOutput.getContent() );
				if (currentOutput.getContent() != null)
					log.debug("CurrentOoutput.getContent().isPossiblySigned() " + currentOutput.getContent().isPossiblySigned());
			}
			while (currentOutput != null && currentOutput.getContent() != null && currentOutput.getContent().isPossiblySigned()) {
				// Verifico se il controllo precedente si e' interrotto 
				// a causa di un errore di un controllo bloccante
				log.info("Current signer " + currentOutput.getContent().getSigner());
				if (masterSignerController.isInterrupted())
					break;
				else {
					// Creo un oggetto temporaneo
					if (timeStampedSignatureWithContentFileIsTemporary) {
						// Cancello l'oggetto precedente
						boolean deleted = FileUtil.deleteFile(timeStampedSignatureWithContentFile);
						if (deleted) {
							log.debug("File temporaneo cancellato correttamente!" + timeStampedSignatureWithContentFile.getAbsolutePath());
						} else {
							log.warn("Warning di cancellazione del file temporaneo di appoggio " + timeStampedSignatureWithContentFile.getAbsolutePath());
						}
						timeStampedSignatureWithContentFile = null;
						timeStampedSignatureWithContentFileIsTemporary = true;
					}
					try {
						if( currentOutput.getContent().getExtractedFile()!=null ){
							timeStampedSignatureWithContentFile = currentOutput.getContent().getExtractedFile();
							currentOutput.getContent().setExtractedFile(null);
						} else {
						InputStream extractedStream = currentOutput.getContent().getContentStream();
						String estensione = ".tmp";
						timeStampedSignatureWithContentFile = File.createTempFile("TimeStampedSignatureWithContentFile", estensione,
								/*this.masterSignerController.getDedicatedTemporyDirectory()*/
								timeStampedSignatureWithContentFile.getParentFile()
								);//timeStampedSignatureWithContentFile.getParentFile());// this.masterSignerController.getDedicatedTemporyDirectory());
						FileOutputStream outputstream = new FileOutputStream(timeStampedSignatureWithContentFile);
						IOUtils.copyLarge(extractedStream, outputstream);
						IOUtils.closeQuietly(outputstream);
						//currentOutput.getContent().getSigner().closeFileStream();
						}

						log.info("Analizzo il file " + timeStampedSignatureWithContentFile);
						log.info("fileName " + fileName);
						if (fileName != null && fileName.endsWith(".p7m")) {
							log.debug("Verifico se il file " + timeStampedSignatureWithContentFile + " deve essere sbustato. fileName " + fileName);
							try {
								if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
									String estensioneFile = FilenameUtils.getExtension(fileName);
									if (estensioneFile != null && !StringUtils.isBlank(fileName))
										signer = SignerUtil.newInstance().getSignerManager(timeStampedSignatureWithContentFile, estensioneFile);
									else
										signer = SignerUtil.newInstance().getSignerManager(timeStampedSignatureWithContentFile);
								} else
									signer = SignerUtil.newInstance().getSignerManager(timeStampedSignatureWithContentFile);
								log.debug("Ho individuato il Signer  " + signer);
								currentOutput.getContent().setSigner(signer);
								if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
									if (signer != null && signer instanceof CAdESSigner) {

									} else {
										fileName = fileName.substring(0, fileName.indexOf(".p7m"));
									}
								}
								isSigned = true;
							} catch (NoSignerException e) {
								// Se arriva qua non e' stato trovato alcun signer per cui o il file non e' firmato o
								// e' firmato in maniera ignota al cryptosigner
								log.debug("Il file " + timeStampedSignatureWithContentFile + " non e' firmato");
								isSigned = false;
								signer = null;
								if (!StringUtils.isBlank(fileName) && fileName.contains("."))
									fileName = fileName.substring(0, fileName.indexOf(".p7m"));
							}
						} else if (fileName != null && fileName.endsWith(".pdf")) {
							log.debug("Verifico se il file " + timeStampedSignatureWithContentFile + " è firmato PADES. fileName " + fileName);
							signer = new PdfSigner();
							boolean isPdf = signer.isSignedType(timeStampedSignatureWithContentFile);
							log.info("isSignedPdf? " + isPdf);
							if (isPdf) {
								log.debug("Ho individuato il Signer  " + signer);
								isSigned = true;
							} else {
								log.debug("Il file " + timeStampedSignatureWithContentFile + " non e' firmato");
								signer = null;
								isSigned = false;
							}
							if (fileName != null && fileName.contains(".p7m")) {
								fileName = fileName.substring(0, fileName.indexOf(".p7m"));
							}
						} else if (fileName != null && fileName.endsWith(".xml")) {
							log.debug("Verifico se il file " + timeStampedSignatureWithContentFile + " e' firmato XADES. fileName " + fileName);
							signer = new XMLSigner();
							boolean isXml = signer.isSignedType(timeStampedSignatureWithContentFile);
							log.info("isSignedXml? " + isXml);
							if (isXml) {
								log.debug("Ho individuato il Signer  " + signer);
								isSigned = true;
							} else {
								log.debug("Il file " + timeStampedSignatureWithContentFile + " non e' firmato");
								signer = null;
								isSigned = false;
							}
							if (fileName != null && fileName.contains(".p7m")) {
								fileName = fileName.substring(0, fileName.indexOf(".p7m"));
							}
						}

					} catch (Exception e) {
						log.error("Errore recupero file", e);
					}
					Date newReferenceDate = getTimeStampDateFromOutput(currentOutput);
					OutputSignerBean tmpOutput = executeCycle(configuration, newReferenceDate, childValidation);
					if (tmpOutput == null)
						break;
					currentOutput.setChild(tmpOutput);
					currentOutput = currentOutput.getChild();
				}
				//log.debug("CurrentOutput " + currentOutput);
				if (currentOutput != null) {
					// log.debug("CurrentOoutput.getContent() " + currentOutput.getContent() );
					if (currentOutput.getContent() != null)
						log.debug("CurrentOoutput.getContent().isPossiblySigned() " + currentOutput.getContent().isPossiblySigned());
				}
			}

		}
		return outputSigner;
	}

	private OutputSignerBean executeCycle(CONFIGURATION configuration, Date reference, boolean eseguiValidazioni) {
		OutputTimeStampBean outputTimeStamp = getDocumentAndTimeStampInfos(configuration, reference);
		OutputSignerBean outputSigner = getOutputSigner(configuration, outputTimeStamp, eseguiValidazioni);
		return outputSigner;
	}

	/**************************************************
	 * Controllo del timestamp
	 */
	public OutputTimeStampBean getDocumentAndTimeStampInfos(CONFIGURATION configuration, Date reference) {
		//log.debug("Metodo getDocumentAndTimeStampInfos");
		//log.info("masterTimeStampController " + masterTimeStampController);
		if (masterTimeStampController == null)
			return null;

		InputTimeStampBean input = new InputTimeStampBean();

		OutputTimeStampBean output = null;
		try {
			if (configuration != null) {
				// log.info("configuration::: " + configuration);
				switch (configuration) {

				case CONFIG_1_2:
					input.setTimeStampWithContentFile(timeStampedSignatureWithContentFile);
					input.setFileName(fileName);
					input.setSigner(signer);
					input.setIsSigned(isSigned);
					break;

				case CONFIG_3:
					input.setTimeStampFile(timeStampFile);
					input.setContentFile(signatureWithContentFile);
					break;

				case CONFIG_4_5:
					input.setTimeStampWithContentFile(timeStampedSignatureFile);
					break;

				case CONFIG_6:
					input.setTimeStampFile(timeStampFile);
					input.setContentFile(signatureFile);
					break;

				default:
					break;
				}
			}

			input.setReferenceDate(reference);
			input.setTimeStampExtensionsChain(timeStampsChain);
			output = masterTimeStampController.executeControll(input, childValidation);

		} catch (ExceptionController e) {
			log.error("Eccezione getDocumentAndTimeStampInfos", e);
		}
		return output;
	}

	/**************************************************
	 * Controllo delle firme
	 */
	public OutputSignerBean getOutputSigner(CONFIGURATION configuration, OutputTimeStampBean outputTimeStamp, boolean eseguiValidazioni) {
		//log.debug("Metodo getOutputSigner");
		AbstractSigner signer = null;
		InputSignerBean input = new InputSignerBean();

		File envelope = null;
		try {
			// log.debug("Configuration: " + configuration);
			switch (configuration) {
			case CONFIG_1_2:
				signer = outputTimeStamp == null ? null : outputTimeStamp.getSigner();
				envelope = timeStampedSignatureWithContentFile;
				break;

			case CONFIG_3:{
				log.debug("Cerco il signer per il file firmato (non la marca)"); 
				signer = signerUtil.getSignerManager(signatureWithContentFile);
				envelope = signatureWithContentFile;
				break;
			}

			case CONFIG_4_5:
				signer = outputTimeStamp == null ? null : outputTimeStamp.getSigner();
				envelope = timeStampedSignatureFile;
				signer.setDetachedFile(detachedContentFile);
				break;

			case CONFIG_6:
				signer = signerUtil.getSignerManager(signatureFile);
				envelope = signatureFile;
				signer.setDetachedFile(detachedContentFile);
				break;

			default:
				break;
			}

		} catch (NoSignerException e) {
			//if (signer != null) {
				log.warn("Nessun signer individuato");
			//}
		}

		// log.info("Signer " + signer);
		List<DocumentAndTimeStampInfoBean> timeStampInfos = outputTimeStamp == null ? null : outputTimeStamp.getDocumentAndTimeStampInfos();

		// Se il contenuto non e' firmato ritorno i soli dati sul timestamp (qualora presenti)
		if (signer == null) {
			if (timeStampInfos == null)
				return null;
			OutputSignerBean output = new OutputSignerBean();
			output.setProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY, timeStampInfos);
			return output;
		}

		// Mantengo solo i timestamp che sono validi
		ArrayList<DocumentAndTimeStampInfoBean> validTimeStampInfosList = new ArrayList<DocumentAndTimeStampInfoBean>();
		if (timeStampInfos != null && timeStampInfos.size() != 0) {
			for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos) {
				if (timeStampInfo.getValidationInfos().isValid())
					validTimeStampInfosList.add(timeStampInfo);
			}
		}

		DocumentAndTimeStampInfoBean[] validTimeStampInfos = null;
		if (validTimeStampInfosList.size() != 0)
			validTimeStampInfos = validTimeStampInfosList.toArray(new DocumentAndTimeStampInfoBean[validTimeStampInfosList.size()]);

		/*
		 * Popolo il bean di input
		 */
		try {
			input.setEnvelopeStream(new FileInputStream(envelope));
			input.setEnvelope(envelope);
		} catch (FileNotFoundException e1) {
			log.error("File inesistente", e1);
		}

		// boolean esitoCancellazione = timeStampedSignatureWithContentFile.delete();
		// log.info("esito cancellazione " + esitoCancellazione);

		DocumentAndTimeStampInfoBean oldest = null;
		if (validTimeStampInfos != null && validTimeStampInfos.length != 0) {
			oldest = getOldestDocumentAndTimeStampInfoBean(validTimeStampInfos);
			input.setDocumentAndTimeStampInfo(oldest);
		}

		input.setSigner(signer);
		if (referenceDate != null) {
			log.debug("referenceDate:::::::::" + referenceDate);
			if (oldest != null && oldest.getTimeStampToken().getTimeStampInfo().getGenTime().before(referenceDate)){
				Date dataMarca = oldest.getTimeStampToken().getTimeStampInfo().getGenTime();
				log.debug("Data di riferimento valorizzata, poiche' e' presenta una marca precedente uso questa come riferimento " + dataMarca);
				input.setReferenceDate( dataMarca );
			} else {
				log.debug("Data di riferimento valorizzata, uso questa come riferimento " + referenceDate);
				input.setReferenceDate(referenceDate);
			}
		} else if (oldest != null) {
			Date dataMarca = oldest.getTimeStampToken().getTimeStampInfo().getGenTime();
			log.debug("Data di riferimento non valorizzata, poiche' e' presenta una marca considero la data marca come riferimento " + dataMarca);
			input.setReferenceDate( dataMarca );
		}
		
		OutputSignerBean output = null;
		try {
			log.debug("executeControll di " + masterSignerController );
			output = masterSignerController.executeControll(input, eseguiValidazioni);
		} catch (ExceptionController e) {
			log.warn("Eccezione getOutputSigner", e);
		}
		output.setProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY, timeStampInfos);

		Map<byte[], TimeStampToken> mapSignatureTimeStampTokens = outputTimeStamp.getMapSignatureTimeStampTokens();
		output.setProperty(OutputSignerBean.MAP_SIGNATURE_TIME_STAMP_PROPERTY, mapSignatureTimeStampTokens);

		if (input.getEnvelopeStream() != null) {
			try {
				input.getEnvelopeStream().close();
			} catch (IOException e) {
				log.warn("Eccezione getOutputSigner", e);
			}
		}

		return output;
	}

	/**
	 * Recupera il controller preposto alla gestione dei cicli di analisi sui file firmati
	 * 
	 * @return l'istanza del controller preposto alla gestione dei cicli di analisi sui file firmati
	 */
	public MasterSignerController getMasterSignerController() {
		return masterSignerController;
	}

	/**
	 * Definisce il il controller preposto alla gestione dei cicli di analisi sui file firmati
	 * 
	 * @param masterSignerController
	 */
	public void setMasterSignerController(MasterSignerController masterSignerController) {
		this.masterSignerController = masterSignerController;
	}

	protected CONFIGURATION getConfiguration() {
		if (timeStampedSignatureWithContentFile != null)
			return CONFIGURATION.CONFIG_1_2;
		else if (signatureWithContentFile != null && timeStampFile != null)
			return CONFIGURATION.CONFIG_3;
		else if (detachedContentFile != null && timeStampedSignatureFile != null)
			return CONFIGURATION.CONFIG_4_5;
		else if (detachedContentFile != null && signatureFile != null && timeStampFile != null)
			return CONFIGURATION.CONFIG_6;
		return null;
	}

	protected enum CONFIGURATION {
		CONFIG_1_2, CONFIG_3, CONFIG_4_5, CONFIG_6
	}

	/**
	 * Recupera la configurazione su cui eseguire le operazioni di analisi
	 */
	public SignatureManagerConfig getConfig() {
		return config;
	}

	/**
	 * Definisce la configurazione su cui eseguire le operazioni di analisi
	 * 
	 * @param config
	 */
	public void setConfig(SignatureManagerConfig config) {
		this.config = config;
	}

	protected DocumentAndTimeStampInfoBean getOldestDocumentAndTimeStampInfoBean(DocumentAndTimeStampInfoBean[] timeStampInfos) {
		if (timeStampInfos == null || timeStampInfos.length == 0)
			return null;
		DocumentAndTimeStampInfoBean max = timeStampInfos[0];
		for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos) {
			if (max.getTimeStampToken().getTimeStampInfo().getGenTime().before(timeStampInfo.getTimeStampToken().getTimeStampInfo().getGenTime()))
				max = timeStampInfo;
		}
		return max;
	}

	private Date getTimeStampDateFromOutput(OutputSignerBean outputSignerBean) {
		List<DocumentAndTimeStampInfoBean> timeStampInfos = (List<DocumentAndTimeStampInfoBean>) outputSignerBean
				.getProperty(OutputSignerBean.TIME_STAMP_INFO_PROPERTY);
		ArrayList<DocumentAndTimeStampInfoBean> validTimeStampInfosList = new ArrayList<DocumentAndTimeStampInfoBean>();
		if (timeStampInfos != null && timeStampInfos.size() != 0) {
			for (DocumentAndTimeStampInfoBean timeStampInfo : timeStampInfos) {
				if (timeStampInfo.getValidationInfos().isValid())
					validTimeStampInfosList.add(timeStampInfo);
			}
		}
		DocumentAndTimeStampInfoBean[] validTimeStampInfos = null;
		if (validTimeStampInfosList.size() != 0)
			validTimeStampInfos = validTimeStampInfosList.toArray(new DocumentAndTimeStampInfoBean[validTimeStampInfosList.size()]);
		DocumentAndTimeStampInfoBean documentAndTimeStampInfoBean = getOldestDocumentAndTimeStampInfoBean(validTimeStampInfos);
		return documentAndTimeStampInfoBean == null ? null : documentAndTimeStampInfoBean.getTimeStampToken().getTimeStampInfo().getGenTime();
	}

	/**
	 * @return the referenceDate
	 */
	public Date getReferenceDate() {
		return referenceDate;
	}

	/**
	 * @param referenceDate
	 *            the referenceDate to set
	 */
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	/**
	 * @return the masterTimeStampController
	 */
	public MasterTimeStampController getMasterTimeStampController() {
		return masterTimeStampController;
	}

	/**
	 * @param masterTimeStampController
	 *            the masterTimeStampController to set
	 */
	public void setMasterTimeStampController(MasterTimeStampController masterTimeStampController) {
		this.masterTimeStampController = masterTimeStampController;
	}

	/**
	 * @return the singleStep
	 */
	public boolean isSingleStep() {
		return singleStep;
	}

	/**
	 * @param singleStep
	 *            the singleStep to set
	 */
	public void setSingleStep(boolean singleStep) {
		this.singleStep = singleStep;
	}

	public boolean isChildValidation() {
		return childValidation;
	}

	public void setChildValidation(boolean childValidation) {
		this.childValidation = childValidation;
	}

}
