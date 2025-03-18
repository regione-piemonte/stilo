/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import javax.activation.MimeType;

public interface DynamicCodeDetector {
	
	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException;
		
}
