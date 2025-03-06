package it.eng.suiteutility.module.mimedb.enums;
/**
 * Se 1 indica che e' un formato le cui specifiche sono coperte da copyright,
 * se 0 che � un formato aperto
 * 
 * @author upescato
 *
 */

public enum FlagCopyright {

	COPYRIGHT(true),
	COPYLEFT(false);
	
	private final Boolean copyright;

	FlagCopyright(Boolean copyright){
		this.copyright=copyright;
	}

	public Boolean type(){
		return this.copyright;
	}
	
}
