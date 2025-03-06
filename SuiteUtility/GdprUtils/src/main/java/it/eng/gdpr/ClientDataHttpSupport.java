package it.eng.gdpr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ClientDataHttpSupport {

	public static final String KEY_REMOTEUSER = "RemoteUser";
	public static final String KEY_FORWARDED = "Forwarded";
	public static final String KEY_XFF = "X-Forwarded-For";
	public static final String KEY_XFH = "X-Forwarded-Host";
	public static final String KEY_REMOTEADDR = "RemoteAddr";
	public static final String KEY_REMOTEHOST = "RemoteHost";
	public static final String KEY_REMOTEPORT = "RemotePort";
	public static final String KEY_USERAGENT = "User-Agent";

	public static final String REQUEST_ATTRIBUTE_NAME_CLIENT_DATA = "it.eng.gdpr.ClientData";

	public static final Map<String, String> retrieveData(HttpServletRequest httpReq) {
		final Map<String, String> result = new HashMap<String, String>();
		result.put(KEY_FORWARDED, httpReq.getHeader("Forwarded"));
		result.put(KEY_REMOTEADDR, httpReq.getRemoteAddr());
		result.put(KEY_REMOTEHOST, httpReq.getRemoteHost());
		result.put(KEY_REMOTEPORT, String.valueOf(httpReq.getRemotePort()));
		result.put(KEY_REMOTEUSER, httpReq.getRemoteUser());
		result.put(KEY_USERAGENT, httpReq.getHeader("User-Agent"));
		result.put(KEY_XFF, httpReq.getHeader("X-Forwarded-For"));
		result.put(KEY_XFH, httpReq.getHeader("X-Forwarded-Host"));
		return result;
	}

	public static final Map<String, String> setData(HttpServletRequest httpServletRequest) {
		final Map<String, String> clientData = retrieveData(httpServletRequest);
		httpServletRequest.setAttribute(REQUEST_ATTRIBUTE_NAME_CLIENT_DATA, clientData);
		return clientData;
	}

	public static final Map<String, String> setEmptyData(HttpServletRequest httpServletRequest) {
		final Map<String, String> clientData = new HashMap<String, String>();
		httpServletRequest.setAttribute(REQUEST_ATTRIBUTE_NAME_CLIENT_DATA, clientData);
		return clientData;
	}

	@SuppressWarnings("unchecked")
	public static final Map<String, String> getData(HttpServletRequest httpServletRequest) {
		Map<String, String> clientData = (Map<String, String>) httpServletRequest.getAttribute(REQUEST_ATTRIBUTE_NAME_CLIENT_DATA);
		if (clientData == null) {
			clientData = retrieveData(httpServletRequest);
		}
		return clientData;
	}

}
