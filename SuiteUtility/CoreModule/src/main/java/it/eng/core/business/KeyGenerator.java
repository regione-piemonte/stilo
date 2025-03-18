/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Classe che espone un metodo per la generazione automatica di un identificativo univoco
 *
 * @author Mattia Zanin
 */
public class KeyGenerator {

	/**
	 * Metodo per generare un UUID
	 *
	 * @author Mattia Zanin
	 */
	public static String gen(){
		return java.util.UUID.randomUUID().toString();
	}
}
