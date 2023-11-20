/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class CoppiaAttachBeanFile {
	
	private TAttachEmailMgoBean attachMgo;
	private InfoFileBarcodeBean infoFileBarcodeBean;
	private File fileAllegato;
	
	/*Qui vado a salvare il file convertito o sbustato in pdf, in quanto l'operazione di scansione abrcode la effettuiamo solo sui pdf*/
	private File fileAllegatoPdf;

	public TAttachEmailMgoBean getAttachMgo() {
		return attachMgo;
	}

	public InfoFileBarcodeBean getInfoFileBarcodeBean() {
		return infoFileBarcodeBean;
	}

	public File getFileAllegato() {
		return fileAllegato;
	}

	public File getFileAllegatoPdf() {
		return fileAllegatoPdf;
	}

	public void setAttachMgo(TAttachEmailMgoBean attachMgo) {
		this.attachMgo = attachMgo;
	}

	public void setInfoFileBarcodeBean(InfoFileBarcodeBean infoFileBarcodeBean) {
		this.infoFileBarcodeBean = infoFileBarcodeBean;
	}

	public void setFileAllegato(File fileAllegato) {
		this.fileAllegato = fileAllegato;
	}

	public void setFileAllegatoPdf(File fileAllegatoPdf) {
		this.fileAllegatoPdf = fileAllegatoPdf;
	}
	
	
	
}
