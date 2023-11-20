/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;

public class DocumentConfiguration {

	private DigestAlgID algoritmo;
	private DigestEncID encoding;
	private String operationWsAddress;
	private int timeout = 60000;

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = DigestAlgID.fromValue(algoritmo);
	}

	public void setAlgoritmo(DigestAlgID algoritmo) {
		this.algoritmo = algoritmo;
	}

	public DigestAlgID getAlgoritmo() {
		return algoritmo;
	}

	public void setEncoding(String encoding) {
		this.encoding = DigestEncID.fromValue(encoding);
	}

	public void setEncoding(DigestEncID encoding) {
		this.encoding = encoding;
	}

	public DigestEncID getEncoding() {
		return encoding;
	}

	public void setOperationWsAddress(String operationWsAddress) {
		this.operationWsAddress = operationWsAddress;
	}

	public String getOperationWsAddress() {
		return operationWsAddress;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
