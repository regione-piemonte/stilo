/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
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
