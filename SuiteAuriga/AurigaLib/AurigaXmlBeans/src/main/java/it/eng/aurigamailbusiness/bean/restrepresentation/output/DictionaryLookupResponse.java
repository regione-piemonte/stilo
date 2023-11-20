/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.DictionaryLookupRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaListaXMLTrovaValoriDizionario;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaTrovaValoriDizionario")
public class DictionaryLookupResponse {
	
	@XmlElement(name="numeroElementi")
	private Integer totalSize;
	@XmlElement(name="numeroElementiInPagina")
	private Integer pageSize;
	@XmlElement(name="pagina", defaultValue="1")
	private Integer pageNumber;

	@XmlElement(name="valoriDizionario")
	@ApiModelProperty(name="valoriDizionario")
	private ListaListaXMLTrovaValoriDizionario listaListaXML;
	
	@XmlElement(name="richiestaTrovaValoriDizionario")
	private DictionaryLookupRequest request;

	public ListaListaXMLTrovaValoriDizionario getListaListaXML() {
		return listaListaXML;
	}
	public void setListaListaXML(ListaListaXMLTrovaValoriDizionario listaListaXML) {
		this.listaListaXML = listaListaXML;
	}
	
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public DictionaryLookupRequest getRequest() {
		return request;
	}
	public void setRequest(DictionaryLookupRequest request) {
		this.request = request;
	}
	
}//DictionaryLookupResponse
