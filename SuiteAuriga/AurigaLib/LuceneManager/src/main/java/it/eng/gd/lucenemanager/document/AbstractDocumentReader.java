/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.gd.lucenemanager.LuceneSpringAppContext;
import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.config.LuceneIndexConfig;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDocumentReader 
{
	public abstract Reader getContent(File file) throws Exception;
	public abstract FileVO[] getContents(File file, String mimetype) throws Exception;
	
	private boolean ocrOperation;
	
	//Restituisce un file random creato sulla directory temporanea
	public File getRandomFile() throws IOException{
		LuceneIndexConfig config = (LuceneIndexConfig)LuceneSpringAppContext.getContext().getBean(LuceneIndexConfig.CONFIG_NAME);
		File directory = config.getPath();
		File pathtmpdir = new File(directory,"temp");
		if(!pathtmpdir.exists()){
			pathtmpdir.mkdirs();
		}	
		File temp = File.createTempFile("indextemporaneo",".tmp", pathtmpdir);		
		return temp;	
	}

	public boolean isOcrOperation() {
		return ocrOperation;
	}

	public void setOcrOperation(boolean ocrOperation) {
		this.ocrOperation = ocrOperation;
	}
}	
