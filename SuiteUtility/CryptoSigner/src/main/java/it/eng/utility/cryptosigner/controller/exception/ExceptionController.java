/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Eccezione specializzata per il processo di controllo dei file firmati
 * @author Rigo Michele
 *
 */
public class ExceptionController extends Exception{
	 public ExceptionController(String message) {
			super(message);
		    }
	 
	 public ExceptionController(Exception e) {
			super(e);
		    }
}
