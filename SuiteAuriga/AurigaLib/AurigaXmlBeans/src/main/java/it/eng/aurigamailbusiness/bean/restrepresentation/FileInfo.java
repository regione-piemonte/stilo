/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "FileInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Parametri essenziali di un file")
public class FileInfo {

	@XmlElement(name = "Nome")
	@ApiModelProperty(example = "nome.pdf", value = "Nome originale del file.")
	private String name;
	@XmlElement(name = "Dimensione")
	@ApiModelProperty(example = "367,12 KB", value = "Dimensione del file.")
	private String size;

	public FileInfo() {
	}

	public FileInfo(String name, String size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
