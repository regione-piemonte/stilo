package it.eng.utility.cryptosigner.controller;

import it.eng.utility.cryptosigner.controller.bean.DocumentAndTimeStampInfoBean;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Definisce l'interfaccia di un controller preposto alle attivita di analisi, verifica e
 * validazione di marche temporali embedded o detached.
 * @author Stefano Zennaro
 */
public interface ITimeStampController {
	
	/**
	 * Controlla e restituisce le informazioni riguardo alle marche temporali presenti in un di un file
	 * @param file file contenente il documento e la marca temporale
	 * @return informazioni relative alla marca temporale
	 * @throws FileNotFoundException 
	 * @throws CryptoSignerException 
	 */
	
	
	/**
	 * Controlla e restituisce le informazioni riguardo alle marche temporali presenti in un file, 
	 * verificando la validita della lista di estensioni di marche temporali passate in ingresso.
	 * @param file file contenente il documento e la marca temporale
	 * @return informazioni relative alla marca temporale
	 * @throws FileNotFoundException 
	 * @throws CryptoSignerException 
	 */
	
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file) throws FileNotFoundException, CryptoSignerException;
	
	/**
	 * Controlla e restituisce le informazioni riguardo alla marca temporale di un file, 
	 * verificando la validita della lista delle estensioni
	 * @param file file contenente il documento e la marca temporale
	 * @param timeStampExtensionChain lista dei timestamp che estendono le marche temporali
	 * @return informazioni relative alle marche temporali
	 * @throws FileNotFoundException 
	 * @throws CryptoSignerException 
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File...timeStampExtensionChain) throws FileNotFoundException, CryptoSignerException;
	
	/**
	 * Controlla e restituisce le informazioni riguardo alle marche temporali presenti in un file detached
	 * @param file	file contenente il documento
	 * @param detachedTimeStamp file contente la marca temporale del documento
	 * @return informazioni relative alle marche temporali
	 * @throws CryptoSignerException 
	 * @throws FileNotFoundException 
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File detachedTimeStamp) throws FileNotFoundException, CryptoSignerException;
	
	/**
	 * Controlla e restituisce le informazioni riguardo alle marche temporali presenti in un file detached, 
	 * verificando la validita della lista delle estensioni verificando la validita della lista delle estensioni
	 * @param file file contenente il documento
	 * @param detachedTimeStamp file contente le marche temporali del documento
	 * @param timeStampExtensionChain lista dei timestamp che estendono le marche temporali
	 * @return informazioni relative alle marche temporali
	 * @throws FileNotFoundException 
	 * @throws CryptoSignerException 
	 */
	public DocumentAndTimeStampInfoBean[] checkTimeStamps(File file, File detachedTimeStamp, File...timeStampExtensionChain) throws FileNotFoundException, CryptoSignerException;
	
	/**
	 * Recupera il {@link it.eng.utility.cryptosigner.data.AbstractSigner signer} utilizzato per l'analisi
	 * e verifica delle marche temporali in seguito alla chiamata a un metodo check
	 * @return il signer delle marche temporali
	 */
	public AbstractSigner getSigner();
	
	/**
	 * Resetta lo stato interno del controller successivamente alla chiamata ad un metodo check 
	 * Puo essere richiama
	 */
	public void reset();
	

	/**
	 * Recupara il validatore temporale delle marche 
	 * @return il validatore
	 */
	public ITimeStampValidator getTimeStampValidator();
	
	/**
	 * Definisce l'istanza preposta al controllo della validita
	 * temporale delle marche
	 * @param timeStampValidator validatore temporale delle marche
	 */
	public void setTimeStampValidator(ITimeStampValidator timeStampValidator);
}
