/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.types.FieldType;

/**
 * Datasource che prevede nella costruzione di passare i campi che 
 * verranno mostrati nel display value dell'item una volta
 * selezionato il valore
 * @author Rametta
 *
 */
public class SelectGWTRestDataSource extends GWTRestDataSource {
	
	public SelectGWTRestDataSource(String serverid) {
		super(serverid);	
	}
	
	public SelectGWTRestDataSource(String serverid, boolean isSelectFilter) {
		super(serverid, isSelectFilter);	
	}
	
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype) {
		super(serverid, keyproperty, keytype);
	}
	
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, isSelectFilter);
	}
	
	
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields) {
		super(serverid, keyproperty, keytype, fields);
	}
	
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, fields, isSelectFilter);
	}
	
	public SelectGWTRestDataSource(String serverid, boolean hierarchical, String keyproperty, FieldType keytype) {
		super(serverid, hierarchical, keyproperty, keytype);
	}
	
	public SelectGWTRestDataSource(String serverid, boolean hierarchical, String keyproperty, FieldType keytype, List<DataSourceField> fields) {
		super(serverid, hierarchical, keyproperty, keytype);
		setFields(fields.toArray(new DataSourceField[0]));
	}
	
	/**
	 * Costruttore come la {@link GWTRestDataSource} solo che accetta anche 
	 * i nomi dei campi da mostrare nel display value
	 * @param serverid
	 * @param valoriDaMostrare
	 */
	public SelectGWTRestDataSource(String serverid, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, isSelectFilter);		
		addParameters(valoriDaMostrare);
	}

	/**
	 * Costruttore come la {@link GWTRestDataSource} solo che accetta anche 
	 * i nomi dei campi da mostrare nel display value
	 * @param serverid
	 * @param keyproperty
	 * @param keytype
	 * @param valoriDaMostrare
	 */
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, isSelectFilter);
		addParameters(valoriDaMostrare);	
	}

	/**
	 * Costruttore come la {@link GWTRestDataSource} solo che accetta anche 
	 * i nomi dei campi da mostrare nel display value
	 * @param serverid
	 * @param keyproperty
	 * @param keytype
	 * @param fields
	 * @param valoriDaMostrare
	 */
	public SelectGWTRestDataSource(String serverid, String keyproperty, FieldType keytype, List<DataSourceField> fields, String[] valoriDaMostrare, boolean isSelectFilter) {
		super(serverid, keyproperty, keytype, fields, isSelectFilter);		
		addParameters(valoriDaMostrare);
	}
	
	protected void addParameters(String[] valoriDaMostrare) {
		if (valoriDaMostrare!=null && valoriDaMostrare.length>0){
			StringBuffer lStringBuffer = new StringBuffer();
			boolean first = true;
			for (String lStringField : valoriDaMostrare){
				if (first) first = false;
				else lStringBuffer.append(";");
				lStringBuffer.append(lStringField);
			}
			addParam("fieldToShow", lStringBuffer.toString());
		} else addParam("showAll", "true");
	}

}
