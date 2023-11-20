/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.richiesteAutotutelaCed.datasource.bean.GetDettaglioRichiestaAutotutelaCedBean;
import it.eng.auriga.ui.module.layout.server.richiesteAutotutelaCed.datasource.bean.GetListaRichiesteAutotutelaCedBean;
import it.eng.auriga.ui.module.layout.server.richiesteAutotutelaCed.datasource.bean.RichiestaAutotutelaCedBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.module.core.shared.message.MessageType;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

@Datasource(id = "RichiesteAutotutelaCedDataSource")
public class RichiesteAutotutelaCedDataSource extends AbstractFetchDataSource<RichiestaAutotutelaCedBean> {
	
	private static Logger mLogger = Logger.getLogger(RichiesteAutotutelaCedDataSource.class);

	private TrustManager trustManager = new X509TrustManager() {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateExpiredException, CertificateNotYetValidException {
			chain[0].checkValidity();
			chain[0].getIssuerUniqueID();
			chain[0].getSubjectDN();
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {

		}
	};

	@Override
	public PaginatorBean<RichiestaAutotutelaCedBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		String tipoRichiesta = "";
		String flagLetto = "";
		String dataRichiesta = "";
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("tipoRichiesta".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						tipoRichiesta = String.valueOf(crit.getValue());
					}
				}
				if ("flagLetto".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						flagLetto = new Boolean(String.valueOf(crit.getValue())) ? "true" : "";
					}
				}
				if ("dataRichiesta".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						dataRichiesta = new SimpleDateFormat("yyyyMMdd").format(parseDate((String) crit.getValue()));
					}
				}
			}
		}
		try {
			// InputStream fin = new FileInputStream("C:\\temp\\geri.jks");
			// KeyStore keyStore = KeyStore.getInstance("jks");
			// if (fin != null) {
			// keyStore.load(fin, new char[] { 'g', 'e', 'r', 'i', '0', '0' });
			// fin.close();
			// }
			SSLContext sslContext = SSLContext.getInstance("SSL");
			// KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// keyManagerFactory.init(keyStore, new char[] { 'g', 'e', 'r', 'i', '0', '0' });
			// sslContext.init(keyManagerFactory.getKeyManagers(), trustManager, new SecureRandom());
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			DefaultClientConfig defaultClientConfig = new com.sun.jersey.api.client.config.DefaultClientConfig();
			defaultClientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
			Client client = Client.create(defaultClientConfig);
			WebResource webResource = client
					.resource("https://gericolmi.tributi.eng.it/WebApiCartellaCittadino/api/Service/GetListaRichiesteAutotutelaCed?tipoRichiesta="
							+ tipoRichiesta + "&flagLetto=" + flagLetto + "&dataRichiesta=" + dataRichiesta);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			// if (response.getStatus() != 200) {
			// throw new RuntimeException("Failed! HTTP error: [" + response.getStatus() + "] ");
			// }
			String respJson = response.getEntity(String.class);
			GsonBuilder builder = GsonBuilderFactory.getIstance();
			Gson gson = builder.create();
			GetListaRichiesteAutotutelaCedBean respBean = gson.fromJson(respJson, GetListaRichiesteAutotutelaCedBean.class);
			return new PaginatorBean<RichiestaAutotutelaCedBean>(respBean.getListaRichiesteAutotutelaCed());
		} catch (Exception e) {
			addMessage(
					"Errore nella chiamata al WS https://gericolmi.tributi.eng.it/WebApiCartellaCittadino/api/Service/GetListaRichiesteAutotutelaCed: "
							+ e.getMessage(), "", MessageType.ERROR);
			mLogger.warn(e);
		}
		return null;
	}

	@Override
	public RichiestaAutotutelaCedBean get(RichiestaAutotutelaCedBean bean) throws Exception {
		try {
			// InputStream fin = new FileInputStream("C:\\temp\\geri.jks");
			// KeyStore keyStore = KeyStore.getInstance("jks");
			// if (fin != null) {
			// keyStore.load(fin, new char[] { 'g', 'e', 'r', 'i', '0', '0' });
			// fin.close();
			// }
			SSLContext sslContext = SSLContext.getInstance("SSL");
			// KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// keyManagerFactory.init(keyStore, new char[] { 'g', 'e', 'r', 'i', '0', '0' });
			// sslContext.init(keyManagerFactory.getKeyManagers(), trustManager, new SecureRandom());
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			DefaultClientConfig defaultClientConfig = new com.sun.jersey.api.client.config.DefaultClientConfig();
			defaultClientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
			Client client = Client.create(defaultClientConfig);
			WebResource webResource = client
					.resource("https://gericolmi.tributi.eng.it/WebApiCartellaCittadino/api/Service/GetDettaglioRichiestaAutotutelaCed?idRichiesta="
							+ bean.getIdRichiesta());
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			// if (response.getStatus() != 200) {
			// throw new RuntimeException("Failed! HTTP error: [" + response.getStatus() + "] ");
			// }
			String respJson = response.getEntity(String.class);
			GsonBuilder builder = GsonBuilderFactory.getIstance();
			Gson gson = builder.create();
			GetDettaglioRichiestaAutotutelaCedBean respBean = gson.fromJson(respJson, GetDettaglioRichiestaAutotutelaCedBean.class);
			return respBean;
		} catch (Exception e) {
			addMessage("Errore nella chiamata al WS https://gericolmi.tributi.eng.it/WebApiCartellaCittadino/api/Service/GetDettaglioRichiestaAutotutelaCed: "
					+ e.getMessage(), "", MessageType.ERROR);
			mLogger.warn(e);
		}
		return null;
	}

}
