/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Valida l'AllegatoCanvas cui viene passato
 * @author massimo malvestio
 *
 */
public interface AllegatoValidator {

	/**
	 * Valida i contenuti dell'AllegatoItem
	 * @return
	 */
	public boolean validate();
	
	/**
	 * @return ritorna l'eventuale messagio impostato
	 */
	public String getMessage();
	
}
