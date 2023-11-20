/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;

public class OneCallSelectGWTRestDataSource extends SelectGWTRestDataSource {
	
	public OneCallSelectGWTRestDataSource(String serverid) {
		super(serverid);	
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, Map<String, String> params) {
		super(serverid);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, boolean isSelectFilter) {
		super(serverid, isSelectFilter);	
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype) {
		super(serverid, keyproperty, keytype);
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, Map<String, String> params) {
		super(serverid, keyproperty, keytype);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, isSelectFilter);
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, keyproperty, keytype, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields) {
		super(serverid, keyproperty, keytype, fields);
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, Map<String, String> params) {
		super(serverid, keyproperty, keytype, fields);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, fields, isSelectFilter);
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, keyproperty, keytype, fields, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, valoriDaMostrare, isSelectFilter);		
	}

	public OneCallSelectGWTRestDataSource(String serverid, String[] valoriDaMostrare, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, valoriDaMostrare, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}

	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, valoriDaMostrare, isSelectFilter);
	}

	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, String[] valoriDaMostrare, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, keyproperty, keytype, valoriDaMostrare, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}
	}

	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, fields, valoriDaMostrare, isSelectFilter);		
	}
	
	public OneCallSelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, String[] valoriDaMostrare, boolean isSelectFilter, Map<String, String> params) {
		super(serverid, keyproperty, keytype, fields, valoriDaMostrare, isSelectFilter);
		if(params != null) {
			this.extraparam.putAll(params);
		}	
	}

	@Override
	public boolean isOneCallDataSource() {
		return true;
	}
}
