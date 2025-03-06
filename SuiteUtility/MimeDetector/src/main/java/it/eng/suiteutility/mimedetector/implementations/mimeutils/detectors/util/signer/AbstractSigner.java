package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.signer;

import java.io.File;

import org.apache.log4j.Logger;


public abstract class AbstractSigner{

		Logger log = Logger.getLogger(AbstractSigner.class);
		
		/**
		 * File di firma con/senza contenuto
		 */
		protected File file;

		/**
		 * Recupera il formato della busta nel caso sia stato riconosciuto
		 * @return formato
		 */
		public abstract SignerType getFormat();
	
		/**
		 * Controlla se il file contiene firme in formato riconosciuto
		 * @param file
		 * @return true se il vi sono firme con formato riconosciuto
		 */
		public abstract boolean isSignedType(File file);

		public void setFile(File file) {
			this.file = file;
		}
		
		
		

}