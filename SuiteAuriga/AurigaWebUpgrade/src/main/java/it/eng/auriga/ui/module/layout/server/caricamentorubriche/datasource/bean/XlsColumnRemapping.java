/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.poi.hssf.util.CellReference;

import it.eng.jaxb.variabili.Lista.Riga.Colonna;

/**
 * Permette di legare il CellReference di una colonna al relativo nome del campo
 * 
 * @author massimo malvestio
 *
 */
public class XlsColumnRemapping {

	private String fieldName;

	private Colonna fieldType;

	private Colonna index;

	private String columnReference;

	public XlsColumnRemapping(String fieldName, String columnReference, Colonna index, Colonna fieldType) {

		this.fieldName = fieldName;

		this.fieldType = fieldType;

		this.index = index;

		this.columnReference = columnReference;
	}

	public Colonna getIndex() {
		return index;
	}

	public Colonna getFieldType() {
		return fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getCol() {
		return CellReference.convertColStringToIndex(columnReference);
	}

}
