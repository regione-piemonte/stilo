/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.FileUtil;
import it.eng.utility.pdfUtility.merge.MergePdfUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class MergeDocument {

	private MergeDocument() {
	}

	public static MergeDocument newInstance() {
		MergeDocument instance = new MergeDocument();
		return instance;
	}
	
	public void mergeDocument(File[] mergeFiles, OutputStream output) throws Exception {
		//File[] mergeFiles = new File[mergeFiles.length];
		try {
			// Effettuo il merge dei documenti PDF
			MergePdfUtil mergeUtil = new MergePdfUtil();
			mergeUtil.concatPDFs(mergeFiles, output);
		} finally {
			// Elimino tutti i file temporanei anche in caso di errore
			for (int i = 0; i < mergeFiles.length; i++) {
				if (mergeFiles[i] != null) {
					FileUtil.deleteFile(mergeFiles[i]);
				}
			}
		}
	}

	
}