package test;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestPostNew {

	@Test
	public void testPost(){
		 RequestConfig defaultRequestConfig = RequestConfig.custom()
         .build();
		 
		 CredentialsProvider credsProvider = new BasicCredentialsProvider();
	        credsProvider.setCredentials(
	                AuthScope.ANY,
	                new UsernamePasswordCredentials("frametta", "hipl32cm"));

		// Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpClient httpclient = HttpClients.custom()
            .setProxy(new HttpHost("proxy.eng.it", 3128))
            .setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider)
            .build();
        HttpHost target = new HttpHost("digidoc.eng.it", 443, "https");
        HttpHost proxy = new HttpHost("proxy.eng.it", 3128, "http");

        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        HttpPost request = new HttpPost("/PortaleFatture/springdispatcher/TestUploadServlet/");
        StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("messageTest", comment)
                .build();


        request.setEntity(reqEntity);
        request.setConfig(config);
        

        System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

        try {
			CloseableHttpResponse response = httpclient.execute(target, request);
			try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(IOUtils.toString(response.getEntity().getContent()));
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
