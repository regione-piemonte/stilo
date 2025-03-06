package it.eng.auriga.opentext.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * Provides utilities to get DataSource from Byte Array
 */
public class ByteArrayDataSourceUtil implements DataSource {

	/**
	 * The Byte Array data
	 */
	private byte[] data;

	/**
	 * The MIME type of Byte Array data
	 */
	private final String type;

	/**
	 * The ByteArrayDataSourceUtil constructor
	 * 
	 * @param data
	 *            the Byte Array data
	 * @param the
	 *            MIME type of Byte Array data
	 */
	public ByteArrayDataSourceUtil(byte[] data, String type) {

		this.data = data;
		this.type = type;
	}

	public ByteArrayDataSourceUtil(byte[] data) {

		this.data = data;
		this.type = "";
	}

	/**
	 * Provides an Input Stream for Byte Array data
	 * 
	 * @return the Input Stream for Byte Array data
	 */

	public InputStream getInputStream() {

		ByteArrayInputStream inputStream = data != null ? new ByteArrayInputStream(data) : null;

		return inputStream;
	}

	/**
	 * Provides the MIME type of Byte Array data
	 * 
	 * @return the MIME type of Byte Array data
	 */

	public String getContentType() {

		String mimeType = type;

		return mimeType;
	}

	/**
	 * Not implemented
	 * 
	 * @return null value
	 */

	public OutputStream getOutputStream() {

		return null;
	}

	/**
	 * Not implemented
	 * 
	 * @return null value
	 */

	public String getName() {

		return null;
	}
}