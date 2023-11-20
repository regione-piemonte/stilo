/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestDataClearoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private RequestAttributesClearoBean attributes;
	private RequestRelationshipsClearoBean relationships;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RequestAttributesClearoBean getAttributes() {
		return attributes;
	}

	public void setAttributes(RequestAttributesClearoBean attributes) {
		this.attributes = attributes;
	}

	public RequestRelationshipsClearoBean getRelationships() {
		return relationships;
	}

	public void setRelationships(RequestRelationshipsClearoBean relationships) {
		this.relationships = relationships;
	}

	@Override
	public String toString() {
		return "RequestDataClearoBean [type=" + type + ", attributes=" + attributes + ", relationships=" + relationships
				+ "]";
	}
	
}
