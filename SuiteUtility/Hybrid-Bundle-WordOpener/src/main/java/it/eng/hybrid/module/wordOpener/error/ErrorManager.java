/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public interface ErrorManager {

	public void manageError(String message);
	
	public void manageExcepion(Exception pException);

	public void justClose();
}
