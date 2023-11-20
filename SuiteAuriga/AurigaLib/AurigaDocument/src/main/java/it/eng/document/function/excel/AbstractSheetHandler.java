/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractSheetHandler extends DefaultHandler {
	private static final Logger log = Logger.getLogger(AbstractSheetHandler.class);
	
	protected SharedStringsTable sst;
	protected int row;
	protected int column;
	protected String lastContents;
	protected boolean nextIsString;

	protected AbstractSheetHandler(SharedStringsTable sst) {
		this.sst = sst;
	}

	protected abstract void atStartRow(String name, Attributes attributes) throws SAXException;
	protected abstract void atEndRow(String name) throws SAXException;

	protected abstract void atStartColumn(String name, Attributes attributes) throws SAXException;
	protected abstract void atEndColumn(String name) throws SAXException;
	

	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

		//log.debug(uri + " - " + localName + " - " + name + " - " + attributes);
		if (name.equals("row")) {

			atStartRow(name, attributes);

		} else if (name.equals("c")) {

			String rif = attributes.getValue("r");
			this.column = rifToColumn(rif);

			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue("t");
			if (cellType != null && cellType.equals("s")) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}

			atStartColumn(name, attributes);
		}

		// Clear contents cache
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		// Process the last contents as required.
		// Do now, as characters() may be called more than once
		if (nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			nextIsString = false;
		}

		// v => contents of a cell
		// Output after we've seen the string contents
		if (name.equals("v")) {
			atEndColumn(name);
		}

		if (name.equals("row")) {
			atEndRow(name);
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	/**
	 * Converts an Excel column name like "C" to a zero-based index.
	 *
	 * @param rif riferimento della cella, ad es. "A1"
	 * @return Index corresponding to the specified name
	 */
	private int rifToColumn(String rif) {
		int firstDigit = -1;
		for (int c = 0; c < rif.length(); ++c) {
			if (Character.isDigit(rif.charAt(c))) {
				firstDigit = c;
				break;
			}
		}
		String name = rif.substring(0, firstDigit);
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}

}