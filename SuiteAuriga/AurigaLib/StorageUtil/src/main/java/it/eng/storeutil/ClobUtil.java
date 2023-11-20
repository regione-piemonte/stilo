/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;

import org.apache.commons.io.IOUtils;

import com.mchange.v2.c3p0.C3P0ProxyConnection;

import oracle.sql.CLOB;

public class ClobUtil {

	/**
	 * Recupero la stringa dal clob
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public String getString(Clob clob) throws Exception {
		if (clob != null) {
			String lString = IOUtils.toString(((CLOB) clob).characterStreamValue());
			// String lString = IOUtils.toString(((CLOB)clob).binaryStreamValue());

			return lString;
		} else {
			return null;
		}
	}

	public byte[] getBytes(Blob blob) throws Exception {
		if (blob != null) {
			byte[] bdata = blob.getBytes(1, (int) blob.length());
			String lString = new String(bdata);// IOUtils.toString(((Blob)blob).binaryStreamValue());
			// String lString = IOUtils.toString(((CLOB)clob).binaryStreamValue());

			return bdata;
		} else {
			return null;
		}
	}

	/**
	 * Creo un oggetto clob e ne setto la stringa
	 * 
	 * @param str
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public CLOB setString(String str, Connection con) throws Exception {
		CLOB clob = null;
		try {
			clob = setStringPoolable(str, con);
		} catch (Exception sqlexp) {
			boolean isFault = true;
			try {

				clob = CLOB.createTemporary(con, true, CLOB.DURATION_SESSION);
				clob.open(CLOB.MODE_READWRITE);
				Writer witer = clob.setCharacterStream(0);
				witer.write(str);
				witer.flush();
				witer.close();
				clob.close();

				isFault = false;
			} catch (Throwable e) {
				throw new Exception("Errore scrittura CLOB con connessione Oracle [" + e.getMessage() + "]");
			}

			if (isFault) {
				if (clob != null) {
					clob.freeTemporary();
				}
				throw new Exception("Errore scrittura CLOB! [" + sqlexp.getMessage() + "]");
			}
		}
		return clob;
	}

	public CLOB setStringPoolable(String str, Connection con) throws Exception {
		CLOB clob = null;
		try {
			Method m = CLOB.class.getMethod("createTemporary", new Class[] { Connection.class, boolean.class, int.class });
			C3P0ProxyConnection castCon = (C3P0ProxyConnection) con;

			Object[] args = new Object[] { C3P0ProxyConnection.RAW_CONNECTION, Boolean.valueOf(true), new Integer(10) };
			clob = (CLOB) castCon.rawConnectionOperation(m, null, args);

			clob.open(CLOB.MODE_READWRITE);
			Writer witer = clob.setCharacterStream(0);
			witer.write(str);
			witer.flush();
			witer.close();
			clob.close();
		} catch (Exception sqlexp) {
			if (clob != null) {
				clob.freeTemporary();
			}
			throw new Exception("Errore scrittura CLOB con connessione C3P0 [" + sqlexp.getMessage() + "]");
		}
		return clob;
	}

}