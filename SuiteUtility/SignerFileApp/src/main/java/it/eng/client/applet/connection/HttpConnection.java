package it.eng.client.applet.connection;

import it.eng.client.applet.bean.ProxyBean;
import it.eng.common.bean.HashFileBean;
import it.eng.common.constants.Constants;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;



public class HttpConnection {
	
	public static void downloadfile(String url,File file,HashFileBean hashbean,ProxyBean configuration,String cookie) throws Exception{
		CloseableHttpResponse response = null;
		HttpUriRequest downloadRequest = null;
		try{
			CloseableHttpClient pClient = ProxyDefaultHttpClient.getClientToUse();		    
			System.out.println("URL DOWNLOAD:"+url);
			downloadRequest = RequestBuilder.post()
			.setUri(new URI(url)).addHeader("Cookie", cookie)
			.addParameter(Constants.HASHBEAN_PARAMETER, hashbean.getId())
			.addParameter(Constants.OPERATION_TYPE_PARAMETER, Constants.OPERATION_TYPE_DOWNLOAD)
			.build();

			response = pClient.execute(downloadRequest);
			if (response.getStatusLine().getStatusCode() !=HttpURLConnection.HTTP_OK){
				throw new Exception(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
			}
			InputStream in = response.getEntity().getContent();
			if(file.exists()){
				file.delete();
			}
			FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(in));
			in.close();
		}finally{
			if (response != null)
				response.close();

		}
	}


	public static void uploadfile(String url,File file,HashFileBean hashbean,ProxyBean configuration,String cookie) throws Exception{
		CloseableHttpResponse response = null;
		try{
			CloseableHttpClient pClient = ProxyDefaultHttpClient.getClientToUse();		    
			HttpPost request = new HttpPost(url+"?"+Constants.HASHBEAN_PARAMETER+"="+hashbean.getId()+"&"+Constants.OPERATION_TYPE_PARAMETER+"="+Constants.OPERATION_TYPE_UPLOAD);
			request.addHeader("Cookie", cookie);
			FileBody lFileBody = new FileBody(file);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
			.addPart(file.getName(), lFileBody)
			.build();
			RequestConfig config = RequestConfig.custom().setExpectContinueEnabled(true).build();
			request.setEntity(reqEntity);
			request.setConfig(config);
			response = pClient.execute(request);
			if (response.getStatusLine().getStatusCode() !=HttpURLConnection.HTTP_OK){
				throw new Exception(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
			}
		}finally{
			if (response != null)
				response.close();

		}
	}
	
	

	
}
