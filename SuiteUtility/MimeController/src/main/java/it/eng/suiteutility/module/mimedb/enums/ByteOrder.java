package it.eng.suiteutility.module.mimedb.enums;

/**
 * Byte order possibile/i per il formato/versione di formato: 
 * Little-Endian (L), 
 * Big-Endian (B), 
 * Mixed-Endian o Middle-Endian (M).
 * @author upescato
 *
 */

public enum ByteOrder {

	LITTLE_ENDIAN("L"),
	BIG_ENDIAN("B"),
	MIXED_ENDIAN("M"),
	MIDDLE_ENDIAN("M"),
	LITTLE_BIG_ENDIAN(LITTLE_ENDIAN.type()+BIG_ENDIAN.type()),									//LB
	LITTLE_MIXED_ENDIAN(LITTLE_ENDIAN.type()+MIXED_ENDIAN.type()),								//LM
	BIG_MIXED_ENDIAN(BIG_ENDIAN.type()+MIXED_ENDIAN.type()),									//BM
	LITTLE_BIG_MIXED_ENDIAN(LITTLE_ENDIAN.type()+BIG_ENDIAN.type()+MIXED_ENDIAN.type());		//LBM
	
	private final String byteOrdering;

	ByteOrder(String byteOrdering) {
		this.byteOrdering=byteOrdering;
	}

	public String type(){
		return this.byteOrdering;
	}
	
}
