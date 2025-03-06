package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class SignerUtil {
	
	Logger log = Logger.getLogger(SignerUtil.class);
	
	private DataSigner dataSigner;
	
	
	private SignerUtil() {
		dataSigner = new DataSigner();
		
		List<Class> signersManager = new ArrayList<Class>();
		signersManager.add(P7MSigner.class);
		signersManager.add(CAdESSigner.class);
		signersManager.add(TsrSigner.class);
		signersManager.add(TsdSigner.class);
		dataSigner.setSignersManager(signersManager);
	}
	
	
	/**
	 * Crea una nuova istanza della classe
	 * @return nuova istanza della classe
	 */
	public static SignerUtil newInstance() {
		return new SignerUtil();
	}
	
	/**
	 * Recupera l'{@link it.eng.crypto.data.AbstractSigner}
	 * preposto al riconoscimento del file firmato in input
	 * @param file il file firmato di cui ricavare il signer
	 * @return l'{@link it.eng.crypto.data.AbstractSigner} da utilizzare
	 * @throws Exception
	 */
	public AbstractSigner getSignerManager(File file) throws Exception {
		//Controllo che tipo di Signer Utilizzare
		for (Class signer: dataSigner.getSignersManager()) {
			try {
				AbstractSigner newSigner = (AbstractSigner)signer.newInstance();
				if (newSigner.isSignedType(file)) {
					newSigner.setFile(file);
					return newSigner;
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//Se sono arrivato fino a qui lancio una eccezione;
		throw new Exception("Nessun Manager Signer Trovato per il file specificato: " + file);
		
	}
	

	


}