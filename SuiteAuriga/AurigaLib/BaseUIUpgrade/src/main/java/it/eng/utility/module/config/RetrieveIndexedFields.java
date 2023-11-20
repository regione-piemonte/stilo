/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

public class RetrieveIndexedFields {
	
	private static HashMap<String, String> indexedFieldsCategoria;	
	
	public static void addIndexedFields(String categoria, String indexedFields) {
		if(indexedFieldsCategoria == null) {
			indexedFieldsCategoria = new HashMap<String, String>();
		}
		indexedFieldsCategoria.put(categoria, indexedFields);
	}

	public static HashMap<String, String> getIndexedFieldsCategoria() {
		return indexedFieldsCategoria;
	}

	public static void setIndexedFieldsCategoria(HashMap<String, String> indexedFieldsCategoria) {
		RetrieveIndexedFields.indexedFieldsCategoria = indexedFieldsCategoria;
	}

}
