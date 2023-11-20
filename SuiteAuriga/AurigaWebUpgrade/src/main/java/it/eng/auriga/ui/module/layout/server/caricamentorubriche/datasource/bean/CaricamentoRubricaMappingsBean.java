/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class CaricamentoRubricaMappingsBean {

	private List<CaricamentoRubricaMappingBean> mappings;

	private String errorMessage;
	
	private boolean successful;
	
	public List<CaricamentoRubricaMappingBean> getMappings() {
		return mappings;
	}

	public void setMappings(List<CaricamentoRubricaMappingBean> mappings) {
		this.mappings = mappings;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
}
