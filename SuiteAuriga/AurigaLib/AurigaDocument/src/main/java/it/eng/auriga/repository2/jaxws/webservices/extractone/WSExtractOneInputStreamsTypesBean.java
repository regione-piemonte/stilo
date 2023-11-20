/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import java.io.InputStream;

public class WSExtractOneInputStreamsTypesBean implements Serializable {

	private InputStream inputStream;
	private String inputStreamsTypes;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getInputStreamsTypes() {
		return inputStreamsTypes;
	}
	public void setInputStreamsTypes(String inputStreamsTypes) {
		this.inputStreamsTypes = inputStreamsTypes;
	}
}
