/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EgrammataConfig {

	/**
	 * Rappresenta il prefisso da mettere allo storage configurato come StorageUtil
	 */
	private String prefix;
	//Eventuale url per il recupero dei file (la differenza vale solo in test)
	private String url;
	
	/**
	 * Rappresenta il nome dello storage configurato con lo storage vecchio
	 */
	private String descriptorName;
//	<property name="prefix" value="SU"></property>
//	<property name="descriptorName" value="FS"></property>
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getDescriptorName() {
		return descriptorName;
	}
	public void setDescriptorName(String descriptorName) {
		this.descriptorName = descriptorName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
