/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomBean;
import it.eng.auriga.ui.module.layout.server.task.bean.FileDaUnireBean;

public class RegistrazioneAttoBean extends EventoCustomBean{
	
	private List<FileDaUnireBean> fileDaUnire;
	private int annoRegistrazione;
	private String uriPdf;
	private String siglaRegistroAtto;
	private String idTipoDoc;
	private String estremiRegAtto;
	
	public List<FileDaUnireBean> getFileDaUnire() {
		return fileDaUnire;
	}
	public void setFileDaUnire(List<FileDaUnireBean> fileDaUnire) {
		this.fileDaUnire = fileDaUnire;
	}
	public int getAnnoRegistrazione() {
		return annoRegistrazione;
	}
	public void setAnnoRegistrazione(int annoRegistrazione) {
		this.annoRegistrazione = annoRegistrazione;
	}
	public String getUriPdf() {
		return uriPdf;
	}
	public void setUriPdf(String uriPdf) {
		this.uriPdf = uriPdf;
	}
	public String getSiglaRegistroAtto() {
		return siglaRegistroAtto;
	}
	public void setSiglaRegistroAtto(String siglaRegistroAtto) {
		this.siglaRegistroAtto = siglaRegistroAtto;
	}
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getEstremiRegAtto() {
		return estremiRegAtto;
	}
	public void setEstremiRegAtto(String estremiRegAtto) {
		this.estremiRegAtto = estremiRegAtto;
	}
	
}
