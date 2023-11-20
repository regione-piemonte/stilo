/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Peluso
 */

@XmlRootElement(name = "filtroAlbo")
@XmlAccessorType(XmlAccessType.FIELD)
public class FiltroAlbo implements Serializable{

	private static final long serialVersionUID = 2771277889240460374L;

	private String name;
	
	private String value;
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

