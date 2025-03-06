package it.eng.proxyselector.scheme;

import it.eng.proxyselector.jcfis.EngJCIFSEngine;
import it.eng.proxyselector.jcfis.EngNTLMScheme;
import it.eng.proxyselector.jcfis.EngNTLMSchemeFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

public class NTLMSchemeFactory implements AuthSchemeFactory {
	private static final Log log = LogFactory.getLog(EngNTLMSchemeFactory.class);
	public AuthScheme newInstance(final HttpParams params) {
		return new EngNTLMScheme(new EngJCIFSEngine());
	}

}
