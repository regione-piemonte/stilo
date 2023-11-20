/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.hibernate.cfg.NotYetImplementedException;

import it.eng.utility.filemanager.fs.FsFileManager;
import it.eng.utility.filemanager.ftp.FtpFileManager;

public class FileManagerFactory {

	private FileManagerFactory() {
	}

	public static final FileManager getFileManager(FileManagerConfig config) throws FileManagerException, NotYetImplementedException {
		if (config != null) {
			switch (config.getTipoServizio()) {
			case FS:
				return new FsFileManager(config);

			case FTP:
			case SFTP:
				return new FtpFileManager(config);

			default:
				throw new NotYetImplementedException("Protocollo " + config.getTipoServizio() + " non gestito");
			}
		} else {
			throw new FileManagerException("FileManager non configurato");
		}
	}

}
