/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestDataDeterminaClearoAslVcBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private RequestAttributesDeterminaClearoAslVcBean attributes;
	private RequestRelationshipsClearoAslVcBean relationships;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RequestAttributesDeterminaClearoAslVcBean getAttributes() {
		return attributes;
	}

	public void setAttributes(RequestAttributesDeterminaClearoAslVcBean attributes) {
		this.attributes = attributes;
	}

	public RequestRelationshipsClearoAslVcBean getRelationships() {
		return relationships;
	}

	public void setRelationships(RequestRelationshipsClearoAslVcBean relationships) {
		this.relationships = relationships;
	}

	@Override
	public String toString() {
		return "RequestDataClearoBean [type=" + type + ", attributes=" + attributes + ", relationships=" + relationships
				+ "]";
	}
	
}
