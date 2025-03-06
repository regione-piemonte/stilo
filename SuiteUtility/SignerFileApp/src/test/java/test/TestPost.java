package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;

public class TestPost {

//	String serviceUrl = "https://digidoc.eng.it/PortaleFatture/springdispatcher/TestUploadServlet/";
	String serviceUrl = "https://www.google.it";
	@Test
	public void post(){
		try {
			Properties systemProperties = System.getProperties();
			systemProperties.setProperty( "https.proxyHost", "proxy.eng.it" );
			systemProperties.setProperty( "https.proxyPort", "3128" );
			HttpHost proxy = new HttpHost("proxy.eng.it", 3128, "https");
			AbstractHttpClient client = new DefaultHttpClient();
			KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File("D:\\digidoc.jks"));
			try {
				trustStore.load(instream, "changeit".toCharArray());
			} finally {
				try { instream.close(); } catch (Exception ignore) {}
			}

			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			Scheme sch = new Scheme("https", 443, socketFactory);

			client.getConnectionManager().getSchemeRegistry().register(sch);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			System.out.println("executing head verso "+ serviceUrl+" ...");
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("messageTest", new StringBody("Messaggio di prova", Charset.forName("UTF-8")));
			HttpPost lHttpPost = new HttpPost(serviceUrl); 
			lHttpPost.setEntity(entity);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 120000);
			client.setParams(httpParams);
			HttpGet lHttpGet = new HttpGet(serviceUrl);
			HttpResponse response = client.execute(lHttpGet);
			int result = response.getStatusLine().getStatusCode();
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				Reader reader = new InputStreamReader(response.getEntity().getContent());
				char[] buffer = new char[256];
				StringBuffer lStringBuffer = new StringBuffer();
				for (;;) {
					int rsz = reader.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					lStringBuffer.append(buffer, 0, rsz);
				}
				System.out.println(lStringBuffer.toString());	
			} else {
				throw new Exception("Errore Fallito: La servlet ha risposto con " + status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
