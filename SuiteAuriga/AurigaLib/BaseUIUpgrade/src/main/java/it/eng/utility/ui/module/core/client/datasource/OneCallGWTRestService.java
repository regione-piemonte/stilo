/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class OneCallGWTRestService<E,T> extends GWTRestService<E,T> {
	
	public OneCallGWTRestService(String serverid) {
		super(serverid);
	}

	public OneCallGWTRestService(String serverid, Map<String, String> params) {
		super(serverid);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}

	@Override
	public boolean isOneCallDataSource() {
		return true;
	}
	
}