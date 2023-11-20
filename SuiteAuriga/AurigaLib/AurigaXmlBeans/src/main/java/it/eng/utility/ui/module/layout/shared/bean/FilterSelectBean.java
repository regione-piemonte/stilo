/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class FilterSelectBean {

	private FilterSelectLayoutBean layout;
	private String datasourceName;
	private Map<String, String> datasourceParams;
	private Map<String, String> valueMap;
	private Boolean canFilter;
	private Boolean multiple;
	private Integer width;
	private String emptyPickListMessage;
	private Map<String, String> ordinamentoDefault;
	private Map<String, String> customProperties;

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public Map<String, String> getDatasourceParams() {
		return datasourceParams;
	}

	public void setDatasourceParams(Map<String, String> datasourceParams) {
		this.datasourceParams = datasourceParams;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

	public void setLayout(FilterSelectLayoutBean layout) {
		this.layout = layout;
	}

	public FilterSelectLayoutBean getLayout() {
		return layout;
	}

	public void setCanFilter(Boolean canFilter) {
		this.canFilter = canFilter;
	}

	public Boolean isCanFilter() {
		return canFilter;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public Map<String, String> getOrdinamentoDefault() {
		return ordinamentoDefault;
	}

	public void setOrdinamentoDefault(Map<String, String> ordinamentoDefault) {
		this.ordinamentoDefault = ordinamentoDefault;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Map<String, String> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Map<String, String> customProperties) {
		this.customProperties = customProperties;
	}

	public String getEmptyPickListMessage() {
		return emptyPickListMessage;
	}

	public void setEmptyPickListMessage(String emptyPickListMessage) {
		this.emptyPickListMessage = emptyPickListMessage;
	}

}
