package it.eng.utility.cryptosigner.data.signature;

import it.eng.utility.cryptosigner.bean.SignerBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;

import java.util.List;


/**
 * Definisce l'interfaccia per la descrizione di una firma digitale omologando 
 * i diversi formati e implementazioni, garantendo un accesso comune alle
 * operazioni di verifica ed estrazione del contenuto 
 * @author Stefano Zennaro
 *
 */
public interface ISignature {

	/**
	 * Recupera la firma digitale sotto forma di array doi byte
	 * @return il valore in bytes della firma digitale
	 */
	public byte[] getSignatureBytes();
	
	/**
	 * Recupera il bean contenente le informazioni sulla 
	 * la firma digitale 
	 * @return il bean contenente le informazioni sulla firma digitale 
	 */
	public SignerBean getSignerBean();
	
	/**
	 * Effettua la verifica della firma digitale 
	 * @return il bean contenente le informazioni sull'esito della verifica
	 */
	public ValidationInfos verify();
	
	/**
	 * Recupera la lista delle controfirme 
	 * @return la lista delle controfirme
	 */
	public List<ISignature> getCounterSignatures();
	
}
