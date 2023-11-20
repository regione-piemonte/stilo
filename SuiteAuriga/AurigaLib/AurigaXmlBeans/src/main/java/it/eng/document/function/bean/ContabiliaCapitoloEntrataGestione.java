/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaCapitoloEntrataGestione extends ContabiliaCapitolo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ContabiliaCategoria categoria;
	private List<ContabiliaImportoCapitoloEntrataGestione> listaImportiCapitoloEG;
	private ContabiliaTipologia tipologia;
	
	public ContabiliaCategoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(ContabiliaCategoria categoria) {
		this.categoria = categoria;
	}
	
	public List<ContabiliaImportoCapitoloEntrataGestione> getListaImportiCapitoloEG() {
		return listaImportiCapitoloEG;
	}
	
	public void setListaImportiCapitoloEG(List<ContabiliaImportoCapitoloEntrataGestione> listaImportiCapitoloEG) {
		this.listaImportiCapitoloEG = listaImportiCapitoloEG;
	}
	
	public ContabiliaTipologia getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(ContabiliaTipologia tipologia) {
		this.tipologia = tipologia;
	}
	
	@Override
	public String toString() {
		return "ContabiliaCapitoloEntrataGestione [categoria=" + categoria + ", listaImportiCapitoloEG="
				+ listaImportiCapitoloEG + ", tipologia=" + tipologia + "]";
	}
	
}
