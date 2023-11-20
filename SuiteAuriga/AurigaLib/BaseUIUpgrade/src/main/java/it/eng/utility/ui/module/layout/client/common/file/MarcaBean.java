/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class MarcaBean {

	protected String tsaName;
	protected String serialNumber;
	protected String date;
	protected String policy;

	public String getTsaName() {
		return tsaName;
	}

	public void setTsaName(String tsaName) {
		this.tsaName = tsaName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

}
