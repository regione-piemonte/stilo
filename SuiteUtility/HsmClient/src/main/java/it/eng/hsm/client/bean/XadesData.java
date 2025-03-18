/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XadesData {
	
	private String cfg;
	private String oper;
	private String data;
	public String getCfg() {
		return cfg;
	}
	public void setCfg(String cfg) {
		this.cfg = cfg;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
}
