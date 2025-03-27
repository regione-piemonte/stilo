/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestBodyDeterminaClearoAslVcBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RequestDataDeterminaClearoAslVcBean data;

	public RequestDataDeterminaClearoAslVcBean getData() {
		return data;
	}

	public void setData(RequestDataDeterminaClearoAslVcBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestBodyDeterminaClearoAslVcBean [data=" + data + "]";
	}
	
}
