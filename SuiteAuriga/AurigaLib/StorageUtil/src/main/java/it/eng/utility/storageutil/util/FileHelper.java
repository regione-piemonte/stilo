/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.utility.storageutil.exception.FileHelperException;

public class FileHelper extends java.security.DigestInputStream
{
	public static final String INVALID_SHA1 = "SHA1 non valido";
    private String expectedSha1 = null;
    private static final String LOGGER_NAME = "versionhandler";

    static Logger aLogger = Logger.getLogger(LOGGER_NAME);    
    
    /**
     * Costruttore mediante InputStream.
     *     Il digester e' sempre SHA1
     *     la stringa di confronto viene impostata a null
     *
     * @param is InputStream
     * @throws NoSuchAlgorithmException
     */
    public FileHelper(java.io.InputStream is) throws java.security.NoSuchAlgorithmException {
        super (is, MessageDigest.getInstance("SHA"));
        this.on(true);
    }

    /**
     * Metodo per impostare lo sha1 atteso.
     * Se l'impronta calcolata alla fine e' diversa, allora lancia l'eccezione.
     * Se expectedSha1 == null, ignora il confronto.
     * @param sha1 String
     */
    public void setExpectedSha1 (String sha1) {
        expectedSha1 = sha1;
    }

    public String getExpectedSha1() {
        return expectedSha1;
    }

    
    /**
     * Metodo per chiudere lo stream e cancellare il temporaneo
     *
     * @throws IOException
     */
    @Override
    public void close() throws java.io.IOException {
        super.close();
    }

    /**
     * Override del metodo della superclasse 
     * @throws FileHelperException 
     * @throws IOException 
     */
    public String getSha1AndReset() throws FileHelperException, IOException {

    	// calcolo SHA1
    	String sTmp = getSha1();
    	// reset dell'inputStream
		super.reset();
		//reset del Digest
		super.getMessageDigest().reset();
		// fico! tutto OK
		return sTmp;
    }
    
    
    /**
     * Metodo per chiudere lo stream ed estrarre il digest SHA1
     * codificato Base64
     * @return String
     * @throws FileHelperException 
     * @throws IOException
     */
    public String getSha1AndClose() throws FileHelperException {

    	try{
    		// calcolo SHA1
    		return getSha1();
    	} finally {
			// chiudo lo stream sempre
    		try { close(); } catch (Exception e){/**/}
    	}
        
    }
    
    private String getSha1() throws FileHelperException
    {
        String tmpSha1 = null;
        java.security.MessageDigest md = this.getMessageDigest();

        // se md calcolato...
        if (md != null)
        {
            // estraggo lo sha1
            byte[] digest = md.digest();

            // chiudo il digest codificato Base64
            tmpSha1 = new String( (new Base64()).encode(digest));

            // se expectedSha1 != null, faccio il confronto con lo sha1 calcolato
            if ( (expectedSha1 != null) && (!expectedSha1.equals(tmpSha1)) )
            {
                throw new FileHelperException("L'impronta SHA1 dello stream non coincide con l'impronta attesa [" + expectedSha1 + "]");
            }
            // restituisco lo sha1
            return tmpSha1;
        // se md NON calcolato...
        } else {
            if ( expectedSha1 != null)
            {
                throw new FileHelperException("L'impronta SHA1 dello stream e' nulla e non coincide con l'impronta attesa [" + expectedSha1 + "]");
            }
            return null;
        }
    	
    }

	@Override
    public int read() throws IOException {
		return super.read();
		
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return super.read(b, off, len);
	}
    
}

