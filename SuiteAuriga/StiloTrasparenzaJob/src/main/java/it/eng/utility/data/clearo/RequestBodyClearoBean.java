/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestBodyClearoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RequestDataClearoBean data;

	public RequestDataClearoBean getData() {
		return data;
	}

	public void setData(RequestDataClearoBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RequestBodyClearoBean [data=" + data + "]";
	}
	
}
