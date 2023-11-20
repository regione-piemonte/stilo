/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class InputStreamDataSource implements DataSource {
	private String _nome = null;
	private InputStream _is = null;

	/**
	 * Costruttore con InputStream e Nome
	 * Non controlla che InputStream sia nullo.
	 * 
	 * @param is
	 * @param nome
	 */
	public InputStreamDataSource (InputStream is, String nome){
		_is = is;
		if (nome==null || nome.trim().equals("")){
			_nome = _is.toString();
		} else {
			_nome = nome;
		}
	}

	/**
	 * Costruttore con solo InputStream
	 * Non controlla che InputStream sia nullo.
	 * Il nome viene messo mediante _is.toString()
	 *   
	 * @param is
	 */
	public InputStreamDataSource (InputStream is){
		this(is, null);
	}
	
	public String getContentType() {
		// come da suggerimento preso nella doc. ufficiale
		// http://java.sun.com/products/javabeans/glasgow/javadocs/javax/activation/DataSource.html
		return "application/octet-stream";
	}

	public InputStream getInputStream() throws IOException {
		return _is;
	}

	public String getName() {
		return _nome;
	}

	public OutputStream getOutputStream() throws IOException {
        throw new java.io.IOException("Datasource per sola lettura: impossibile referenziare un OutputStream");
	}

}
