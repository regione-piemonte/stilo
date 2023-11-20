/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Reader;

import javax.persistence.Entity;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;

@Entity
public class ProjectDocumentReader extends AbstractDocumentReader {

	@Override
	public Reader getContent(File file) throws DocumentReaderException {
		return null;
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		return null;
	}
}
