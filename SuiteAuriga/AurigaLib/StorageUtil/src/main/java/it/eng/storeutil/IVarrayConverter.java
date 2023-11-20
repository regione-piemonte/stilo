/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Interfaccia di implementazione per la conversione dei varray
 * @author michele
 *
 */
public interface IVarrayConverter {

	/**
	 * 
	 * @param varrayvalue
	 * @return
	 */
	public String[] convert(String varrayvalue);
	
}
