/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaResultTrovaEmail;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rispostaTrovaEmail")
public class LookupResponse {
	
	@XmlElement(name="numeroElementi")
	private Integer totalSize;
	@XmlElement(name="numeroElementiInPagina")
	private Integer pageSize;
	@XmlElement(name="pagina", defaultValue="1")
	private Integer pageNumber;

	@XmlElement(name="emailTrovate")
	@ApiModelProperty(name="emailTrovate")
	private ListaResultTrovaEmail resultTrovaEmailList;
	

	public ListaResultTrovaEmail getResultTrovaEmailList() {
		return resultTrovaEmailList;
	}
	public void setResultTrovaEmailList(ListaResultTrovaEmail resultTrovaEmailList) {
		this.resultTrovaEmailList = resultTrovaEmailList;
	}
	
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
