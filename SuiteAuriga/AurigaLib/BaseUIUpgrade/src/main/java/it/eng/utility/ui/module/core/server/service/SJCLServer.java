/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;


public class SJCLServer {
	
	private static Logger logger = Logger.getLogger(SJCLServer.class);

	private boolean enabled;
	private String password;
	
	private String fileJS;
	private ScriptEngine engine;

	
	public SJCLServer() {
       this(null, null);
	}
	
	public SJCLServer(String password) {
       this(null, password);
	}
	
	public SJCLServer(InputStream in) {
	   this(in, null);
	}

	public SJCLServer(InputStream in, String password) {
		this.enabled = false;
		this.password = password;
		this.fileJS = "sjcl.js";
		InputStream input = in;
		if (input == null) {
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			input = classLoader.getResourceAsStream(this.fileJS);
		}
		setEngine(input);
	}

	public ScriptEngine getEngine() {
		return engine;
	}
	public void setEngine(ScriptEngine engine) {
		this.engine = engine;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getFileJS() {
		return fileJS;
	}

	public void setFileJS(String fileJS) {
		this.fileJS = fileJS;
	}
	
	public String decrypt(String str, boolean flagEnabled) throws ScriptException {
		// logger.debug("decrypt() called with flagEnabled: "+flagEnabled + " and enabled: "+enabled);
		if ( !enabled ) return str;
		if ((!flagEnabled) || engine == null) return str;
		if (str == null || str.trim().isEmpty()) return str;
        final String strParam = str.replace("\\", "\\\\").replace("\"", "\\\"");
        // logger.debug("DECRYPT");
        final String command = String.format("sjcl.decrypt(\""+password+"\",\"%s\")", strParam);
        final String strDecrypted = (String) engine.eval(command);
        return strDecrypted;
	}
	
	public String encrypt(String str, boolean flagEnabled) throws ScriptException {
		// logger.debug("encrypt() called with flagEnabled: "+flagEnabled + " and enabled: "+enabled);
		if ( !enabled ) return str;
		if ((!flagEnabled) || engine == null) return str;
		if (str == null || str.trim().isEmpty()) return str;
		final String strParam = str.replace("\\", "\\\\").replace("\"", "\\\"");
		// logger.debug("ENCRYPT");
		final String command = String.format("sjcl.encrypt(\""+password+"\",\"%s\")", strParam);       
		final String strEncrypted = (String) engine.eval(command);
        return strEncrypted;
	}
	

	public String decryptIfNeeded(String str, boolean flagEnabled) throws ScriptException, GeneralSecurityException {
		// logger.debug("decryptIfNeeded() called with flagEnabled: "+flagEnabled + " and enabled: "+enabled);
		String result = str;
		if ( !enabled ) return result;
		if (/*(!flagEnabled) || */engine == null) return result;
		if (str == null || str.trim().isEmpty()) return result;
		
		final boolean flagEncrypted = str.indexOf("cipher") >= 0;
		if (flagEncrypted) {
	        final String strParam = str.replace("\\", "\\\\").replace("\"", "\\\"");
	        // logger.debug("DECRYPT");
	        final String command = String.format("sjcl.decrypt(\""+password+"\",\"%s\")", strParam);
	        final String strDecrypted = (String) engine.eval(command);
	        result = strDecrypted;
		} else if (flagEnabled) {
            throw new GeneralSecurityException("I dati in ingresso devono essere criptati.");
			// logger.debug("WARNING: ALREADY DECRYPTED!!!!!!!");
		}
		
        return result;
	}
	
	public void setEngine(InputStream in) {
		try {
			engine = in != null ? createEngine(in) : null;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		} catch (ScriptException e) {
			logger.error(e.toString());
		}
	}

	private ScriptEngine createEngine(InputStream in) throws ScriptException, UnsupportedEncodingException {
		// logger.debug("Create engine for sjcl");
		final ScriptEngineManager factory = new ScriptEngineManager();
		final ScriptEngine engine = factory.getEngineByName("JavaScript");
	    engine.eval(new InputStreamReader(in, "UTF-8"));
        return engine;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[enabled=");
		builder.append(enabled);
		builder.append(", password=");
		builder.append(password);
		builder.append(", fileJS=");
		builder.append(fileJS);
		builder.append(", engine=");
		builder.append(engine);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
