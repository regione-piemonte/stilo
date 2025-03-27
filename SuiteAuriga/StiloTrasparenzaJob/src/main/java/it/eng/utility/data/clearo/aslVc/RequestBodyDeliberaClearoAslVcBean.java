/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestBodyDeliberaClearoAslVcBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RequestDataDeliberaClearoAslVcBean data;

	public RequestDataDeliberaClearoAslVcBean getData() {
		return data;
	}

	public void setData(RequestDataDeliberaClearoAslVcBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestBodyDeliberaClearoAslVcBean [data=" + data + "]";
	}
	
}
