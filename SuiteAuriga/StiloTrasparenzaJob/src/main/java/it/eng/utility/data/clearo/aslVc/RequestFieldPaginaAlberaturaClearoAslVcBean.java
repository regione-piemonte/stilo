/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.data.clearo.aslVc;

import java.io.Serializable;

public class RequestFieldPaginaAlberaturaClearoAslVcBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RequestRelDataClearoAslVcBean data;

	public RequestRelDataClearoAslVcBean getData() {
		return data;
	}

	public void setData(RequestRelDataClearoAslVcBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestFieldPaginaAlberaturaClearoBean [data=" + data + "]";
	}
	
}
