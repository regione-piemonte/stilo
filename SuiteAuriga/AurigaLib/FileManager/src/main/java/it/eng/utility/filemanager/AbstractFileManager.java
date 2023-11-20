/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public abstract class AbstractFileManager implements FileManager {

	protected FileManagerConfig config;

	public AbstractFileManager(FileManagerConfig config) {
		this.config = config;
	}

	@Override
	public void setConfig(FileManagerConfig config) throws FileManagerException {
		this.config = config;
		refreshConfig();
	}

	protected abstract void refreshConfig() throws FileManagerException;
}
