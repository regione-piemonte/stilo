/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */




public interface FileLockListener {

	public void fileUnlocked();
	
	public void error(Exception e);
}
