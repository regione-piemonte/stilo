/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvvioProcFascicoloServiceInBean extends AvvioProcedimentoServiceInBean implements Serializable {

	private static final long serialVersionUID = -321364154455285500L;
	
	private String idTipoDoc;
	private String nomeTipoDoc;
	private List<String> listaIdUd;
	
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	public List<String> getListaIdUd() {
		return listaIdUd;
	}
	public void setListaIdUd(List<String> listaIdUd) {
		this.listaIdUd = listaIdUd;
	}
	
}
