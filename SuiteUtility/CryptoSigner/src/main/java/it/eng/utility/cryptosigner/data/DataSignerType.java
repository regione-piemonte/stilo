package it.eng.utility.cryptosigner.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum DataSignerType {

	CAdESSigner("it.eng.utility.cryptosigner.data.CAdESSigner",1), 
	PdfSigner("it.eng.utility.cryptosigner.data.PdfSigner",2), 
	XMLSigner("it.eng.utility.cryptosigner.data.XMLSigner",3), 
	TsdSigner("it.eng.utility.cryptosigner.data.TsdSigner",4), 
	P7MSigner("it.eng.utility.cryptosigner.data.P7MSigner",5),
	P7SSigner("it.eng.utility.cryptosigner.data.P7SSigner",6),
	M7MSigner("it.eng.utility.cryptosigner.data.M7MSigner",7),
	TsrSigner("it.eng.utility.cryptosigner.data.TsrSigner",8);

	private final int ordine;
	private final String name;
	private static Logger log = LogManager.getLogger(DataSignerType.class);
	
	
	private DataSignerType(String name, int ordine) {
		this.ordine = ordine;
		this.name = name;
	}

	public static final int getOrdineFromName(String name) {
		//log.debug("--:: " + name);
		for (DataSignerType value : values()) {
		//	log.debug("value.getName() " + value.getName() );
			if (value.getName().equalsIgnoreCase(name))
				return value.getOrdine();
		}
		return 0;
	}

	public int getOrdine() {
		return this.ordine;
	}
	public String getName() {
		return this.name;
	}
	
}
