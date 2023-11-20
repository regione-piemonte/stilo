/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateFileSystemStoragePolicy implements FileSystemStoragePolicy{

	@Override
	public File getStorageFolder(String basePath, int nroMaxFiles)throws StorageException{
		Calendar calendar = new GregorianCalendar();
		String subPath = calendar.get(Calendar.YEAR) + File.separator  + (calendar.get(Calendar.MONTH) + 1) + File.separator + calendar.get(Calendar.DAY_OF_MONTH);
		File baseFolder = new File(basePath);
		File folder = new File(baseFolder, subPath + File.separator + 1);
		if(folder.exists()) {
			int  progr = 2;		
			while(true) {
				folder = new File(baseFolder, subPath + File.separator + progr);
				if(folder.exists()) {
					progr++;
				} else {
					File precFolder = new File(baseFolder, subPath + File.separator + (progr - 1));
					if(precFolder.listFiles() != null && precFolder.listFiles().length < nroMaxFiles) {
						return precFolder;
					}
					folder.mkdirs();
					return folder;			
				}
			}
		}		
		folder.mkdirs();
		return folder;
	}	
	
}
