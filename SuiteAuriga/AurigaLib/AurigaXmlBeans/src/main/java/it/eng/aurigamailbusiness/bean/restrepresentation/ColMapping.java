/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "colonna")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "colContent", "colName"})
@ApiModel(description = "Mappatura di una colonna")
public class ColMapping {

	@XmlElement(name = "lettera", required=true)
	@ApiModelProperty(example = "B", value = "Lettera dell'alfabeto che identifica la colonna", required=true)
	private String colName;
	@XmlElement(name = "contenuto", required=true)
	@ApiModelProperty(example = "IndirizzoEmailTo", value = "Il tipo di valore che contiene la colonna", required=true)
	private String colContent;

	public ColMapping() {
	}

	public ColMapping(String colName, String colContent) {
		this.colName = colName;
		this.colContent = colContent;
	}

	
	public String getColName() {
		return colName;
	}

	
	public void setColName(String colName) {
		this.colName = colName;
	}

	
	public String getColContent() {
		return colContent;
	}

	
	public void setColContent(String colContent) {
		this.colContent = colContent;
	}


}
