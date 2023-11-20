/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class Marca {

	protected String tsaName;
	protected String serialNumber;
	protected Date date;
	protected String policy;
	private boolean marcaValida;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public boolean isMarcaValida() {
		return marcaValida;
	}

	public void setMarcaValida(boolean marcaValida) {
		this.marcaValida = marcaValida;
	}

}
