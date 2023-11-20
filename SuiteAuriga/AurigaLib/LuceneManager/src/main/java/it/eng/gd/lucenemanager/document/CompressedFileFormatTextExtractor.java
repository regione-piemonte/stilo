/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.log4j.Logger;

@Entity
public class CompressedFileFormatTextExtractor extends CompressedFileFormatController {

	protected CompressedFileFormatTextExtractor() {
	}

	public CompressedFileFormatTextExtractor(File archiveFile, String tempDir, String mimeType, int maxFileToCheck, Map<String, String> formatiAmmessi) {
		super(archiveFile, tempDir, mimeType, maxFileToCheck, formatiAmmessi);
	}

	static Logger aLogger = Logger.getLogger(CompressedFileFormatTextExtractor.class);

	protected Boolean doOperation(File file, String rootpath) throws Exception {
		File[] files = file.listFiles();
		List<File> listaFile = new ArrayList<File>();
		List<File> listaDir = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				listaDir.add(files[i]);
			} else {
				listaFile.add(files[i]);
			}
		}
		return new Boolean(true);

	}

}
