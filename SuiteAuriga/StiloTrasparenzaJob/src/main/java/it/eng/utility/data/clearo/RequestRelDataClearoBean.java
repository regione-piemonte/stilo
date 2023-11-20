/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestRelDataClearoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private String id;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "RequestRelDataClearoBean [type=" + type + ", id=" + id + "]";
	}
	
}
