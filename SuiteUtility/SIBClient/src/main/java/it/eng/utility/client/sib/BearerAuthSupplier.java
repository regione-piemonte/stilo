package it.eng.utility.client.sib;

import java.net.URI;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;

public final class BearerAuthSupplier implements HttpAuthSupplier {

	private String bearer;

	@Override
	public boolean requiresRequestCaching() {
		return false;
	}

	@Override
	public String getAuthorization(AuthorizationPolicy authPolicy, URI url, Message message, String fullHeader) {
		/*
		 * if (String.valueOf(message.getContextualProperty(Message.PROTOCOL_HEADERS)).contains("getVociPegVLiv")) { return
		 * "Bearer 65118a54-0b7f-3ce4-8f3e-cb89fe60ec17"; }
		 */
		return bearer;
	}

	public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

}
