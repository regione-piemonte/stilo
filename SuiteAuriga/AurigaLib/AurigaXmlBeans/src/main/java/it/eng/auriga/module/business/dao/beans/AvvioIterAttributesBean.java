/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvvioIterAttributesBean extends AbstractBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idProcess;
	private String attributeName;
	private String attributeValue;
	public BigDecimal getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(BigDecimal idProcess) {
		this.idProcess = idProcess;

	}
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}
	
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
}
