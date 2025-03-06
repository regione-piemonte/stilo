package it.eng.areas.hybrid.deploy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import it.eng.areas.hybrid.deploy.HybridConfig;
import it.eng.areas.hybrid.deploy.assets.Assets;
import it.eng.areas.hybrid.deploy.beans.HybridPropertyConfigurator;
import it.eng.areas.hybrid.deploy.enums.HybridCasHeaderProperties;
import it.eng.areas.hybrid.deploy.templates.Templates;
import it.eng.areas.hybrid.deploy.util.DeployerUtils;
import it.eng.spring.utility.SpringAppContext;

@SuppressWarnings("serial")
public class HybridServlet extends HttpServlet {

	public final static Logger logger = Logger.getLogger(HybridServlet.class);

	public final static String AREAS_HYBRID_BUILD = "build1005";
	public final static String AREAS_HYBRID_JAR = "AREAS-Hybrid-" + AREAS_HYBRID_BUILD + ".jar";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String uri = request.getRequestURI();
		String pathInfo = request.getPathInfo();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		String base = request.getRequestURL().toString();
		
		ApplicationContext context = SpringAppContext.getContext();
		HybridPropertyConfigurator hybridConfigBean = null;
		if (context != null && context.containsBeanDefinition("HybridPropertyConfigurator") && context.getBean("HybridPropertyConfigurator") != null) {
			hybridConfigBean = (HybridPropertyConfigurator) context.getBean("HybridPropertyConfigurator");
		}
		
		if (hybridConfigBean != null && hybridConfigBean.getEnableHybridCasConfig() != null && hybridConfigBean.getEnableHybridCasConfig()) {
			request.setAttribute(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_ORIGIN.getCodice(), hybridConfigBean.getAccessControlAllowOriginValue());
			request.setAttribute(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_METHODS.getCodice(), hybridConfigBean.getAccessControlAllowMethodsValue());
			request.setAttribute(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_HEADERS.getCodice(), hybridConfigBean.getAccessControlAllowHeadersValue());
			request.setAttribute(HybridCasHeaderProperties.ACCESS_CONTROL_MAX_AGE.getCodice(), hybridConfigBean.getAccessControlMaxAgeValue());
			
			response.addHeader(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_ORIGIN.getCodice(), hybridConfigBean.getAccessControlAllowOriginValue());
			response.addHeader(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_METHODS.getCodice(), hybridConfigBean.getAccessControlAllowMethodsValue());
			response.addHeader(HybridCasHeaderProperties.ACCESS_CONTROL_ALLOW_HEADERS.getCodice(), hybridConfigBean.getAccessControlAllowHeadersValue());
			response.addHeader(HybridCasHeaderProperties.ACCESS_CONTROL_MAX_AGE.getCodice(), hybridConfigBean.getAccessControlMaxAgeValue());
		}
		
		if (hybridConfigBean != null && hybridConfigBean.getEnableHybridCasConfig() != null && hybridConfigBean.getEnableHybridCasConfig()) {
			base = hybridConfigBean.getBaseUrl();
		} else if (request.getPathInfo() != null) {
			base = base.substring(0, base.length() - request.getPathInfo().length()) + "/";
		}

		logger.debug("hybrid uri:" + uri + " pathInfo:" + request.getPathInfo());
		if (request.getPathInfo() == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(path + "/info.html");
			dispatcher.forward(request, response);
			return;
		}

		if (pathInfo.startsWith("/module")) {
			String[] token = pathInfo.split("/");
			String moduleName = token[2];
			String moduleVersion = token.length > 3 ? token[3] : null;
			try {
				InputStream moduleData = HybridConfig.getInstance().getModuleData(moduleName, moduleVersion);
				if (moduleData != null) {
					response.setContentType(DeployerUtils.MIMETYPE_JAR);
					DeployerUtils.copyStream(moduleData, response.getOutputStream());
					return;
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		
		// Questi valori predefiniti sono presenti anche nella classe HybridConfigurationDataSource di AurigaWebUpgrade 
		String hybridPort = "8181";
		String hybridPortSSL = "8183";
		String hybridworkFolder = ".areas-hybrid";
		boolean useSSL = base.toLowerCase().startsWith("https") || request.isSecure();
		
		if (hybridConfigBean != null) {
			hybridPort = StringUtils.isNotBlank(hybridConfigBean.getHybridPort()) ? hybridConfigBean.getHybridPort() :  hybridPort;
			hybridPortSSL = StringUtils.isNotBlank(hybridConfigBean.getHybridPortSSL()) ? hybridConfigBean.getHybridPortSSL() :  hybridPortSSL;
			hybridworkFolder = StringUtils.isNotBlank(hybridConfigBean.getHybridWorkFolder()) ? hybridConfigBean.getHybridWorkFolder() :  hybridworkFolder;
			// Controllo della forzatura del parametro https
			if(hybridConfigBean.getHybridForceHttps() != null && hybridConfigBean.getHybridForceHttps()){
				base = base.replace("http", "https");
				useSSL = true;
			}
		}
		
		Map<String, String> values = new HashMap<>();
		values.put("base", base);
		values.put("jnlp", base + "deploy/Hybrid.jnlp");
		values.put("cliente", "comune_milano");
		values.put("workFolder", hybridworkFolder);
		logger.debug("La base e: " + base);
		logger.debug("Url base inizia con https: " + base.toLowerCase().startsWith("https"));
		logger.debug("request.isSecure(): " + request.isSecure());
		logger.debug("Sono in https: " + useSSL);
		logger.debug("hybridPort: " + hybridPort);
		logger.debug("hybridPortSSL: " + hybridPortSSL);
		if (useSSL) {
			values.put("client.base", "https://localhost:" + hybridPortSSL);
			values.put("client.ws", "wss://localhost:" + hybridPortSSL);
			logger.debug("client.base: https://localhost:" + hybridPortSSL);
			logger.debug("client.ws: wss://localhost:" + hybridPortSSL);
		} else {
			values.put("client.base", "http://localhost:" + hybridPort);
			values.put("client.ws", "ws://localhost:"+ hybridPort);
			logger.debug("client.base: http://localhost:" + hybridPort);
			logger.debug("client.ws: wss://localhost:" + hybridPort);
		}
		values.put("portSSL", hybridPortSSL);
		values.put("port", hybridPort);

//		HttpSession session = request.getSession(false);
//		if (session != null) {
//			values.put("session", session.getId());
//		} else {
//			values.put("session", "");
//		}

		switch (pathInfo) {
		case "/info.html":
		case "/client-info-ssl.html":
		case "/loading.html":
		case "/starting.html":
		case "/instruction.html":
		case "/abort.html":
		case "/hybrid-dark.css":
		case "/hybrid-light.css":
		case "/hybrid.js":
			response.setContentType(DeployerUtils.mimeTypeByName(pathInfo));
			DeployerUtils.copyStream(Templates.getTemplate(pathInfo, values), response.getOutputStream());
			break;
		case "/deploy/Hybrid.jnlp":
			logger.debug("Copio i jar JNLP");
			values.put("jnlp", base + "deploy/Hybrid.jnlp");
			List<String> jars = new ArrayList<>();

			// Carico le librerie necessarie a JNLP

			// Librerie di Hybrid
			jars.add("lib/Hybrid-v0001.jar");

			// Librerie necessarie per l'avvio di JNLP
			jars.add("lib/log4j-1.2.17.jar");
			jars.add("lib/nanohttpd-2.3.1.jar");
			jars.add("lib/nanohttpd-webserver-2.3.1.jar");
			jars.add("lib/nanohttpd-websocket-2.3.1.jar");

			// Librerie necessarie per caricare i bundle
			jars.add("lib/org.apache.felix.framework-5.4.0.jar");

			// Liberie necessarie per caricare il pannello delle informazioni (sono necessarie solo con alcune versioni di java)
			jars.add("lib/jfxrt.jar");
			
			// JNLP necessita di ProxySelector (da verificare il perch�)
			// e di tutte le librerie ad esso legate.
			// Se si omettono possono andare in errore firma e apertura delle impostazioni di rete dei moduli Hybrid 
			jars.add("lib/ProxySelector-1.0.4-SNAPSHOT.jar");
			jars.add("lib/commons-lang-2.6.jar");
			jars.add("lib/commons-codec-1.6.jar");
			jars.add("lib/commons-configuration-1.9.jar");
			jars.add("lib/commons-io-2.3.jar");
			jars.add("lib/commons-logging-1.2.jar");
			jars.add("lib/commons-logging-api-1.1.jar");

			String resources = "";
			boolean first = true;
			for (String jar : jars) {
				if (first) {
					resources += "  <jar href=\"" + jar + "\" download=\"eager\" eager=\"true\" main=\"true\" />\n";
				} else {
					resources += "  <jar href=\"" + jar + "\" download=\"eager\" />\n";
				}
				first = false;
			}

			values.put("codebase", base);
			values.put("resources", resources);
			response.setContentType("application/x-java-jnlp-file");
			DeployerUtils.copyStream(Templates.getTemplate("Hybrid.jnlp", values), response.getOutputStream());
			break;
		default:
			InputStream asset = Assets.getResource(pathInfo);
			if (asset != null) {
				DeployerUtils.copyStream(asset, response.getOutputStream());
			} else {
				logger.warn(pathInfo + " not found");
			}

		}

	}

}
