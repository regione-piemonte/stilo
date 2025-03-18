/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileNotSupportedException extends Exception {

	private static final long serialVersionUID = 8830145611898107521L;

	public FileNotSupportedException(String formato){
		super("Il tipo di file con estensione " + formato + " non è supportato"); 
	}
}
