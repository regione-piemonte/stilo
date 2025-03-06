package it.eng.proxyselector;

import it.eng.progressbar.AbortWaitAction;
import it.eng.progressbar.SuccessWaitAction;
import it.eng.progressbar.WaitDialog;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import it.eng.proxyselector.menu.ProxyMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

public class ProxySelector extends JApplet {
	
	private static Logger mLogger = Logger.getLogger(ProxySelector.class);
	
	public void init() {
		setJMenuBar(new ProxyMenu());
		JFrame lJFrame = new JFrame();
		lJFrame.setSize(600,600);
		lJFrame.setVisible(true);
		WaitDialog lWaitDialog = new WaitDialog(lJFrame, "Upload file in corso", "Upload...", "Upload completato",
				new SuccessWaitAction() {

			@Override
			public void success() {
				System.out.println("ok");
			}
		}, new AbortWaitAction() {

			@Override
			public void abort() {
				System.out.println("bad");
			}

			@Override
			public void error() {
				// TODO Auto-generated method stub

			}
		});
		lWaitDialog.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lWaitDialog.stop();
	}

	public void externalMethod(){
		System.out.println("Esco ora, prima stoppo");
		stop();
		System.exit(1);

	}

	public static String testDiConnessione(String serviceUrl) throws Exception {
		mLogger.info("testDiConnessione a "+serviceUrl);
		CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
		HttpPost request = new HttpPost(serviceUrl);
		StringBody comment = new StringBody("Messaggio di prova", ContentType.TEXT_PLAIN);
		HttpEntity reqEntity = MultipartEntityBuilder.create()
		.addPart("messageTest", comment)
		.build();
		RequestConfig config = RequestConfig.custom().build();
		request.setEntity(reqEntity);
		request.setConfig(config);
		mLogger.info("Executing request " + request.getRequestLine() + " to " + serviceUrl);
		String result = null;
		BufferedReader br = null;
		CloseableHttpResponse response = null;
		try {
			response = lDefaultHttpClient.execute(request);
			mLogger.info(response.getStatusLine());
			StringBuilder sb = new StringBuilder();
			String line;
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			if (response.getStatusLine().getStatusCode()!=200) throw new IOException("Il server ha risposto: " + response.getStatusLine() + ": " + result);
		} catch (Exception e) {
			throw e;
		} finally {
			if(response != null)
			{
				/*
				 * Nel caso le istruzioni nel try non vadano a buon fine si potrebbe arrivare a questo punto con response null
				 */
				response.close();
			}
			
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
