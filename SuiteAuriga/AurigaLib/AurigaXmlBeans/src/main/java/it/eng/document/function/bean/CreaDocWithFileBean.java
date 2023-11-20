/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaDocWithFileBean implements Serializable {

	private static final long serialVersionUID = 898563555493869868L;
	
	private CreaModDocumentoInBean creaDocumentoIn;
	private FilePrimarioBean filePrimario;
	private AllegatiBean allegati;
	private AttachAndPosizioneCollectionBean attachAndPosizioneCollection;
	private CreaModDocumentoOutBean creaDocumentoOut;
	
	public CreaModDocumentoInBean getCreaDocumentoIn() {
		return creaDocumentoIn;
	}
	public void setCreaDocumentoIn(CreaModDocumentoInBean creaDocumentoIn) {
		this.creaDocumentoIn = creaDocumentoIn;
	}
	public FilePrimarioBean getFilePrimario() {
		return filePrimario;
	}
	public void setFilePrimario(FilePrimarioBean filePrimario) {
		this.filePrimario = filePrimario;
	}
	public AllegatiBean getAllegati() {
		return allegati;
	}
	public void setAllegati(AllegatiBean allegati) {
		this.allegati = allegati;
	}
	public AttachAndPosizioneCollectionBean getAttachAndPosizioneCollection() {
		return attachAndPosizioneCollection;
	}
	public void setAttachAndPosizioneCollection(AttachAndPosizioneCollectionBean attachAndPosizioneCollection) {
		this.attachAndPosizioneCollection = attachAndPosizioneCollection;
	}
	public CreaModDocumentoOutBean getCreaDocumentoOut() {
		return creaDocumentoOut;
	}
	public void setCreaDocumentoOut(CreaModDocumentoOutBean creaDocumentoOut) {
		this.creaDocumentoOut = creaDocumentoOut;
	}
	
}