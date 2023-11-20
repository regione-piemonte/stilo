/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestFieldPaginaAlberaturaClearoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private RequestRelDataClearoBean data;

	public RequestRelDataClearoBean getData() {
		return data;
	}

	public void setData(RequestRelDataClearoBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestFieldPaginaAlberaturaClearoBean [data=" + data + "]";
	}
	
}
