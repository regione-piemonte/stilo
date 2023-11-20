/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Reader;
import java.util.List;

import javax.persistence.Entity;

import org.apache.log4j.Logger;

import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;
import it.eng.gd.lucenemanager.util.JobUtils;

@Entity
public class CompressedFileDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(CompressedFileDocumentReader.class);

	public CompressedFileDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws DocumentReaderException {
		return null;
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		JobUtils jobUtils = (JobUtils) LuceneSpringAppContext.getContext().getBean("jobUtils");
		System.out.println(file);
		List<FileVO> listaFileVO = jobUtils.getContentOfZipFile(file, mimetype);
		System.out.println(listaFileVO);

		return listaFileVO.toArray(new FileVO[listaFileVO.size()]);
	}

}
