package it.eng.gdpr.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import it.eng.gdpr.ClientDataHttpSupport;

public class AurigaValve extends ValveBase {

	private final Log log = LogFactory.getLog(AurigaValve.class); // must not be static
	private boolean enabled = true;
	private String servletPath = "/j_security_check";

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = request.getRequest();
		Map<String, String> clientData = null;
		if (enabled) {
			clientData = ClientDataHttpSupport.setData(request);
		} else {
			clientData = ClientDataHttpSupport.setEmptyData(request);
		}
		final boolean jaasFlag = httpServletRequest.getServletPath().equals(servletPath);
		if (jaasFlag) {
			try {
				// System.out.println(httpServletRequest.getServletPath());
				ClientDataThreadLocal.set(clientData);
			} catch (Exception e) {
				log.error("Fallimento nel recupero e settaggio dati del client.", e);
			}
		}
		if (getNext() != null) {
			getNext().invoke(request, response);
		}
	}// invoke

	public void setEnabled(String enabled) {
		this.enabled = "true".equalsIgnoreCase(enabled);
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	// @Override
	// protected void initInternal() throws LifecycleException {
	// super.initInternal();
	// // altro ...
	// }

}// AurigaValve
