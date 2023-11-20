/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.DigestAlgID;
import it.eng.fileOperation.clientws.DigestEncID;

public class DocumentConfiguration {

	private DigestAlgID algoritmo;
	private DigestEncID encoding;
	private String operationWsAddress;
	private int timeout = 60000;
	private boolean ignoreFONewNameWhenUploaded = false;
	private String flgCallMultiThread;
	private int nroThread;

	public void setAlgoritmo(DigestAlgID algoritmo) {
		this.algoritmo = algoritmo;
	}

	public DigestAlgID getAlgoritmo() {
		return algoritmo;
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
	
	public boolean isIgnoreFONewNameWhenUploaded() {
		return ignoreFONewNameWhenUploaded;
	}

	
	public void setIgnoreFONewNameWhenUploaded(boolean ignoreFONewNameWhenUploaded) {
		this.ignoreFONewNameWhenUploaded = ignoreFONewNameWhenUploaded;
	}

	public String getFlgCallMultiThread() {
		return flgCallMultiThread;
	}

	public void setFlgCallMultiThread(String flgCallMultiThread) {
		this.flgCallMultiThread = flgCallMultiThread;
	}

	public int getNroThread() {
		return nroThread;
	}

	public void setNroThread(int nroThread) {
		this.nroThread = nroThread;
	}

	@Override
	public String toString() {
		return "DocumentConfiguration [algoritmo=" + algoritmo + ", encoding=" + encoding + ", operationWsAddress=" + operationWsAddress + ", timeout="
				+ timeout + ", ignoreFONewNameWhenUploaded=" + ignoreFONewNameWhenUploaded + "]";
	}

}
