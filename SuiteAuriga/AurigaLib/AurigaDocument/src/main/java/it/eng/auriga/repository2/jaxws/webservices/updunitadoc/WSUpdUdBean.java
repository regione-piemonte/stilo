/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSUpdUdBean implements Serializable {

	
	private String xml;
	private String OperVsDocXML;
	private String regNumDaRichASistEst;
	public String getXml() {
		return xml;
	}
	public String getOperVsDocXML() {
		return OperVsDocXML;
	}
	public String getRegNumDaRichASistEst() {
		return regNumDaRichASistEst;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public void setOperVsDocXML(String operVsDocXML) {
		OperVsDocXML = operVsDocXML;
	}
	public void setRegNumDaRichASistEst(String regNumDaRichASistEst) {
		this.regNumDaRichASistEst = regNumDaRichASistEst;
	}
	
	
	


	

	}
