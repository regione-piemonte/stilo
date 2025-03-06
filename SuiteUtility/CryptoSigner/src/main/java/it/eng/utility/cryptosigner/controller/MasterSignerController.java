package it.eng.utility.cryptosigner.controller;

import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;

import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.data.SignerUtil;

import java.io.File;
import java.security.Security;
import java.security.cert.CRL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Implementa la gestione dei controller.
 * L'analisi viene innescata dalla chiamata al metodo {@link MasterSignerController#executeControll(InputSignerBean)} 
 * e iterata su tutti i controller definiti nell'attributo controllers
 * @author Stefano Zennaro
 *
 */
public class MasterSignerController {
	
	private static final Logger log = LogManager.getLogger(MasterSignerController.class);
	
	static{
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null)
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	//Directory temporanea di appoggio per la gestione dei file temporanei
	private File temporarydirectroy = new File(System.getProperty("java.io.tmpdir"));

	// Controller da invocare per l'analisi
	private List<ISignerController> controllers;

	// Mappa dei flag indicanti i controlli da effettuare
	private Map<String,Boolean> checks;
	
	// Lista delle crl 
	private CRL crl;
	
	// Indica se uno dei controlli bloccanti non e andato a buon fine
	private boolean interrupted = false;
	
	// Ausiliario
	private SignerUtil signerUtil = SignerUtil.newInstance();
	
	
	public File getTemporarydirectroy() {
		return temporarydirectroy;
	}

	public void setTemporarydirectroy(File temporarydirectroy) {
		this.temporarydirectroy = temporarydirectroy;
	}
	
	/**
	 * Directory temporanea dedicata per la gestione dei file di appoggio
	 */
	private File dedicatedTemporyDirectory;
	
	public MasterSignerController() {
		String dedicated = StringUtils.remove(UUID.randomUUID().toString(),"-");
		dedicatedTemporyDirectory = new File(temporarydirectroy,dedicated);
		dedicatedTemporyDirectory.mkdirs();
	}
	
	
	
	public File getDedicatedTemporyDirectory() {
		return dedicatedTemporyDirectory;
	}

	/**
	 * Recupera i controller configurati
	 * @return i controller configurati
	 */
	public List<ISignerController> getControllers() {
		return controllers;
	}

	/**
	 * Definisce i controller su cui effettuare l'analisi
	 * @param controllers la lista dei controlli cui cui iterare l'analisi
	 */
	public void setControllers(List<ISignerController> controllers) {
		this.controllers = controllers;
	}
	
	
	/**
	 * Effettua l'analisi richiamando l'esecuzione di ciascun 
	 * controller configurato. 
	 * @param input bean contenente le informazioni in input per eseguire i controlli
	 * @return bean
	 * @throws ExceptionController
	 */
	public OutputSignerBean executeControll(InputSignerBean input, boolean eseguiValidazioni)throws ExceptionController{
		OutputSignerBean output = new OutputSignerBean();
		input.setTempDir(this.getDedicatedTemporyDirectory());
		this.execute(input, output, eseguiValidazioni);
		return output;
	}
	
	/**
	 * Esegue la sequenza di controlli sul bean di input, iterandoli 
	 * sul contenuto qualora esso risulti ulteriormente firmato
	 *
	 */
	private void execute(InputSignerBean input,OutputSignerBean output, boolean eseguiValidazioni)throws ExceptionController{
		boolean result;
		input.setChecks(checks);
		input.setCrl(crl);
		for (ISignerController controller:controllers) {
			log.debug("Eseguo il controller " + controller);
			if (controller.canExecute(input)) {
				try {
					long start = System.currentTimeMillis();
					result = controller.execute(input, output, eseguiValidazioni );
					log.debug( "Esito esecuzione controllo " + result );
					if (!result && controller.isCritical()) {
						output.setProperty(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY, controller.getClass().getName());
						interrupted = true;
						break;
					}
					long elapsedTimeMillis = System.currentTimeMillis()-start;
					log.debug("Controllo: " + controller.getClass().getSimpleName() + " eseguito con successo in " + elapsedTimeMillis + "ms");
				}catch(ExceptionController e) {
					if (controller.isCritical()) {
						interrupted = true;
						output.setProperty(OutputSignerBean.MASTER_SIGNER_EXCEPTION_PROPERTY, controller.getClass().getName());
						throw e;
					}
				}
				
			}
		}
	}
	
//	private boolean isSigned(InputSignerBean input, OutputSignerBean output) {
//		ContentBean content = output.getContent();
//		if (!content.isPossiblySigned())
//			return false;
//		AbstractSigner signer = null; 
//		try {
//			//Creo un file temporaneo nella directory dedicata
//			File file = File.createTempFile("Sbustato",".tmp", this.dedicatedTemporyDirectory);
//			IOUtils.copyLarge(content.getContentStream(), new FileOutputStream(file));
//			//Risetto lo stream di lettura
//			content.setContentStream(new FileInputStream(file));
//			signer = signerUtil.getSignerManager(file);
//		} catch (CryptoSignerException e) {
//			/*..non fare nulla.. */
//		}catch(Exception e) {
//			
//		}
//		if (signer == null)
//			return false;
//		InputSignerBean newInput = new InputSignerBean();
//		newInput.setChecks(checks);
//		newInput.setCrl(crl);
//		newInput.setDocumentAndTimeStampInfo(input.getDocumentAndTimeStampInfo());
//		try {
//			newInput.setEnvelopeStream(signer.getContentAsInputStream());
//		} catch (IOException e) {
//			return false;
//		}
//		newInput.setSigner(signer);
//		input = newInput;
//		return true;
//	}

	/**
	 * Recupera la mappa dei flag dei controlli
	 * @return la mappa dei flag
	 */
	public Map<String, Boolean> getChecks() {
		return checks;
	}

	/**
	 * Definisce i flag dei controlli da effettuare
	 * @param checks la mappa contenente i flag dei controlli e il loro valore (true/false)
	 */
	public void setChecks(Map<String, Boolean> checks) {
		this.checks = checks;
	}

	/**
	 * Recupera al CRL configurata e utilizzata nella chiamata al metodo executeControll. 
	 * @return crl
	 */
	public CRL getCrl() {
		return crl;
	}

	/**
	 * Definisce la CRL da utilizzare durante la chiamata al metodo
	 * executeControll per verificare la revoca di un certificato
	 * @param crl 
	 */
	public void setCrl(CRL crl) {
		this.crl = crl;
	}

	/**
	 * Restituisce lo stato di esecuzione dei controller
	 * @return true se uno dei controller ha generato un errore bloccante
	 */
	public boolean isInterrupted() {
		return interrupted;
	}
		
}
