package it.eng.gdpr;

import static it.eng.gdpr.ClientDataHttpSupport.KEY_FORWARDED;
import static it.eng.gdpr.ClientDataHttpSupport.KEY_REMOTEADDR;
import static it.eng.gdpr.ClientDataHttpSupport.KEY_REMOTEHOST;
import static it.eng.gdpr.ClientDataHttpSupport.KEY_REMOTEPORT;
import static it.eng.gdpr.ClientDataHttpSupport.KEY_XFF;
import static it.eng.gdpr.ClientDataHttpSupport.KEY_XFH;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.google.common.base.Splitter;

public class ParametriClientExtractor {

	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
		// objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String getJsonParametriClient(Map<String, String> clientData) throws IOException {
		return objectMapper.writeValueAsString(clientData);
	}

	public static String extractClientIP(Map<String, String> clientData) {

		// the IP address of the client or last proxy that sent the request
		final String remoteAddr = clientData.get(KEY_REMOTEADDR);
		if (StringUtils.isNotBlank(remoteAddr)) {
			return StringUtils.join(remoteAddr, ":", clientData.get(KEY_REMOTEPORT));
		}

		final String xff = clientData.get(KEY_XFF); // client, proxy1, proxy2
		if (StringUtils.isNotBlank(xff)) {
			final String client = extractClient(xff);
			if (StringUtils.isNotBlank(client)) {
				return StringUtils.join(client, ":", clientData.get(KEY_REMOTEPORT));
			}
		}

		final String f = clientData.get(KEY_FORWARDED); // by=<identifier>; for=<identifier>; host=<host>; proto=<http|https>
		if (StringUtils.isNotBlank(f)) {
			final String forIds = extractForIds(f);
			if (StringUtils.isNotBlank(forIds)) {
				return forIds;
			}
		} // if

		return null;
	}// getClientIP

	public static String extractClientName(Map<String, String> clientData) {

		// the FQN of the client or the last proxy that sent the request
		final String remoteHost = clientData.get(KEY_REMOTEHOST);
		if (StringUtils.isNotBlank(remoteHost)) {
			return StringUtils.join(remoteHost, ":", clientData.get(KEY_REMOTEPORT));
		}

		final String xfh = clientData.get(KEY_XFH); // host
		if (StringUtils.isNotBlank(xfh)) {
			return StringUtils.join(xfh, ":", clientData.get(KEY_REMOTEPORT));
		}

		return null;
	}// extractClientName

	public static void main(String[] args) {
		// String xff = "2001:db8:85a3:8d3:1319:8a2e:370:7348";
		// String xff = "203.0.113.195";
		// String xff = "203.0.113.195, 70.41.3.18, 150.172.238.178";
		// String xff = "";
		// System.out.println(extractClient(xff));

		String f = "for=\"_mdn\"";
		// String f = "For=\"[2001:db8:cafe::17]:4711\"";
		// String f = "for=192.0.2.60; proto=http; by=203.0.113.43";
		// String f = "for=192.0.2.43, for=198.51.100.17";
		// String f = "for=192.0.2.43, for=\"[2001:db8:cafe::17]\"";
		System.out.println("RESULT: " + extractForIds(f));
	}

	private static String extractClient(String xForwardedFor) {
		final String xffvalue = StringUtils.deleteWhitespace(xForwardedFor);
		final String[] values = StringUtils.split(xffvalue, ',');
		if (values != null && values.length > 0) {
			return values[0];
		}
		return null;
	}

	private static String extractForIds(String forwarded) {
		final String fvalue = StringUtils.deleteWhitespace(forwarded.toLowerCase());
		final String[] fvalues = StringUtils.split(fvalue, ',');
		String forIds = null;
		for (String value : fvalues) {
			// final Map<String, String> map = new HashMap<String, String>();
			// // by=<identifier>; for=<identifier>; host=<host>; proto=<http|https>
			// final String[] values = StringUtils.split(value, ';');
			// for (String v : values) {
			// final String[] couple = StringUtils.split(v, '=');
			// map.put(couple[0], couple[1]);
			// } // for
			final Map<String, String> map = Splitter.on(";").withKeyValueSeparator("=").split(value);
			final String id = map.get("for");
			if (StringUtils.isNotBlank(id)) {
				try {
					InetAddress.getByName(id);
					forIds = StringUtils.join(forIds, id, ", ");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		} // for
		return StringUtils.removeEnd(forIds, ", ");
	}

}
