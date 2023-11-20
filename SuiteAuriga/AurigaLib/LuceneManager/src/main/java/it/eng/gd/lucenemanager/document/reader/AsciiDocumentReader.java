/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;
import it.eng.gd.lucenemanager.exception.DocumentReaderException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.persistence.Entity;
import org.apache.log4j.Logger;

/**
 * Document Reader per file ASCII
 * @author Administrator
 *
 */
@Entity
public class AsciiDocumentReader extends AbstractDocumentReader  
{
	
	static Logger aLogger = Logger.getLogger(AsciiDocumentReader.class);
	
	public AsciiDocumentReader() 
	{
		super();
	}

	public Reader getContent(File file) throws DocumentReaderException
	{
		try {
			return new FSTemporaryFileReader(file);
		}
		catch (IOException ex)
		{
			aLogger.error(ex.getMessage(), ex);
			throw new DocumentReaderException( "Errore nella lettura delle righe dal documento Ascii", ex);
		}
		catch(Exception ex1)
		{
			aLogger.error(ex1.getMessage(), ex1);
			throw new DocumentReaderException( "Errore nell'estrazione del testo dal documento Ascii", ex1);
		}
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
