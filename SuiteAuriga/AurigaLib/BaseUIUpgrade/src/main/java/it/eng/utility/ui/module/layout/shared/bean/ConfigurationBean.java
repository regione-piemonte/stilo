/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.config.FilterTypeConfigurator;
import it.eng.utility.ui.config.ListConfigurator;


public class ConfigurationBean {
	
	private ListConfigurator listConfig;
	private ApplicationConfigBean applicationConfig;
	private GenericConfigBean genericConfig;
	private FilterTypeConfigurator filterTypeConfig;
	private FilterConfigurator filterConfig;
	private AppletParameterConfigurator appletConfig;
	private AllFilterSelectBean filterSelectConfig;
	private String algoritmoImpronta;
	private String encoding;
	private MimeTypeNonGestitiBean mimeTypeNonGestiti;

	public ListConfigurator getListConfig() {
		return listConfig;
	}

	public void setListConfig(ListConfigurator listConfig) {
		this.listConfig = listConfig;
	}

	public ApplicationConfigBean getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfigBean applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public GenericConfigBean getGenericConfig() {
		return genericConfig;
	}

	public void setGenericConfig(GenericConfigBean genericConfig) {
		this.genericConfig = genericConfig;
	}

	public FilterTypeConfigurator getFilterTypeConfig() {
		return filterTypeConfig;
	}

	public void setFilterTypeConfig(FilterTypeConfigurator filterTypeConfig) {
		this.filterTypeConfig = filterTypeConfig;
	}

	public FilterConfigurator getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfigurator filterConfig) {
		this.filterConfig = filterConfig;
	}

	public AppletParameterConfigurator getAppletConfig() {
		return appletConfig;
	}

	public void setAppletConfig(AppletParameterConfigurator appletConfig) {
		this.appletConfig = appletConfig;
	}

	public AllFilterSelectBean getFilterSelectConfig() {
		return filterSelectConfig;
	}

	public void setFilterSelectConfig(AllFilterSelectBean filterSelectConfig) {
		this.filterSelectConfig = filterSelectConfig;
	}

	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}

	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public MimeTypeNonGestitiBean getMimeTypeNonGestiti() {
		return mimeTypeNonGestiti;
	}

	public void setMimeTypeNonGestiti(MimeTypeNonGestitiBean mimeTypeNonGestiti) {
		this.mimeTypeNonGestiti = mimeTypeNonGestiti;
	}

}
