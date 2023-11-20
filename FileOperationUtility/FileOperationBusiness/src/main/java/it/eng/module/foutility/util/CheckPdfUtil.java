/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.eng.utility.pdfUtility.multiLayer.PdfMultiLayerUtil;

public class CheckPdfUtil {
	
	public static final Logger log = LogManager.getLogger(CheckPdfUtil.class);
	
	public static File manageMultiLayerPdf(File pdfFile, String mimeTypeFile) {
		return PdfMultiLayerUtil.manageMultiLayerPdf(pdfFile, mimeTypeFile);
	}
	
}
