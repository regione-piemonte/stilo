package it.eng.client.applet.operation.detector;

import it.eng.common.type.SignerType;

import java.io.File;
import java.io.InputStream;

 
/**
 * rileva il foramto di una busta
 * @author Russo
 *
 */
public interface EnvelopeDetector {
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
