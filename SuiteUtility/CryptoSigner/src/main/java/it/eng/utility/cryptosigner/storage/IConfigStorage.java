package it.eng.utility.cryptosigner.storage;

import it.eng.utility.cryptosigner.bean.ConfigBean;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;

import java.util.List;

/**
 * Espone i metodi di salvataggio e recupero delle configurazione dei task
 * @author Rigo Michele
 * @version 0.1
 */
public interface IConfigStorage {
	
	/**
	 * Inserisce e sovrascrive la nuova configurazione se esiste.
	 */
	public void insertConfig(ConfigBean config) throws CryptoStorageException;
	
	/**
	 * Recupera la configurazione esistente per subjectDN.
	 * @param subjectDN
	 * @return
	 */
	public ConfigBean retriveConfig(String subjectDN) throws CryptoStorageException;
	
	/**
	 * Elimina la configurazione in base al subjectDN.
	 * @param subjectDN
	 */
	public void deleteConfig(String subjectDN) throws CryptoStorageException;
	
	/**
	 * Recupera la configurazione esistente per subjectDN.
	 * @param subjectDN
	 * @return
	 */
	public List<ConfigBean> retriveAllConfig() throws CryptoStorageException;
	
}