package it.eng.suiteutility.module.mimedb.beans;



import it.eng.suiteutility.module.mimedb.DaoAnagraficaFormatiDigitali;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Bean di utility per gestire le informazioni relative ai mimetype dei formati digitali.
 * Modella i due campi MIMETYPE e RIF_READER della tabella T_MIMETYPE_FMT_DIG
 * @see DaoAnagraficaFormatiDigitali
 * @author upescato
 *
 */
public class Mimetype implements Serializable {
	
	private static final long serialVersionUID = -1275392332843828401L;
	private String mimetype;
	private String rifReader;
	
	public Mimetype(){
		//fixme need for jaxb
	}
	public Mimetype(String mimetype, String rifReader) {
		super();
		this.mimetype = mimetype;
		this.rifReader = rifReader;
	}
	
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getRifReader() {
		return rifReader;
	}
	public void setRifReader(String rifReader) {
		this.rifReader = rifReader;
	}

	@Override
	/* Effettuo la verifica di uguaglianza sul field mimetype poiché fa parte della chiave primaria della tabella T_MIMETYPE_FMT_DIG
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
	    if ((other == null) || !(other instanceof Mimetype)) {
	        return false;
	    }
	    return ((Mimetype) other).getMimetype().equals(this.getMimetype());
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode( new Object[] { mimetype } );
	}
}
