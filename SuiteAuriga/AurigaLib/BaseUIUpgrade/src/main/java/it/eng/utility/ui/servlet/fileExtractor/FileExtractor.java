/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;

public interface FileExtractor {

	public String getFileName() throws Exception;
	public File getFile() throws Exception;
	public InputStream getStream() throws Exception;
}
