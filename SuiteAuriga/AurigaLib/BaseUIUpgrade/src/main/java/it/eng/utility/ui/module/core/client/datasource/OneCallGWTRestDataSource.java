/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.types.FieldType;

public class OneCallGWTRestDataSource extends GWTRestDataSource {
	
	public OneCallGWTRestDataSource(String serverid) {
		super(serverid);			
	}
	
	public OneCallGWTRestDataSource(String serverid, String keyproperty, FieldType keytype) {
		super(serverid, keyproperty, keytype);			
	}
	
	public OneCallGWTRestDataSource(String serverid, Map<String, String> params) {
		super(serverid);	
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, Map<String, String> params) {
		super(serverid, keyproperty, keytype);
		if(params != null) {
			this.extraparam.putAll(params);
		}		
	}
	
	@Override
	public boolean isOneCallDataSource() {
		return true;
	}
	
}