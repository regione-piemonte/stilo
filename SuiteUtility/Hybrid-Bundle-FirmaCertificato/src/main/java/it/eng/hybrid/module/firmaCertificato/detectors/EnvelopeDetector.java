package it.eng.hybrid.module.firmaCertificato.detectors;

import java.io.File;
import java.io.InputStream;

import it.eng.common.type.SignerType;

 
/**
 * rileva il foramto di una busta
 * @author Russo
 *
 */
public interface EnvelopeDetector {
	
	public int maxByteRead=100*1000*1000;
	
	/**
	 * ritorna la busta rilevata null altrimenti
	 * @param file
	 * @return
	 */
	public SignerType isSignedType(File file) ;
	/**
	 * estrae il contenuto dalla busta
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public InputStream getContent(File file) throws Exception ;
}
