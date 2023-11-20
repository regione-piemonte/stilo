/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public class ResponseSbustamentoBean {

	private String nomeFileSbustato;

	private File fileSbustato;

	private String errorMessage;

	public String getNomeFileSbustato() {
		return nomeFileSbustato;
	}

	public void setNomeFileSbustato(String nomeFileSbustato) {
		this.nomeFileSbustato = nomeFileSbustato;
	}

	public File getFileSbustato() {
		return fileSbustato;
	}

	public void setFileSbustato(File fileSbustato) {
		this.fileSbustato = fileSbustato;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
