/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

public class CaricamentoRubricaBean {

	private List<CampoCaricamentoBean> mappings;

	private Map<String, String> infoFile;

	private boolean removeClient;

	private String errorMessage;

	private String companyId;

	private String uri;

	public Map<String, String> getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(Map<String, String> infoFile) {
		this.infoFile = infoFile;
	}

	public boolean isRemoveClient() {
		return removeClient;
	}

	public void setRemoveClient(boolean removeClient) {
		this.removeClient = removeClient;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public List<CampoCaricamentoBean> getMappings() {
		return mappings;
	}

	public void setMappings(List<CampoCaricamentoBean> mappings) {
		this.mappings = mappings;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
