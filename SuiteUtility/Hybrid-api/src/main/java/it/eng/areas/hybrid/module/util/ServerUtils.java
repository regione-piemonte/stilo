package it.eng.areas.hybrid.module.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public final class ServerUtils {

	public final static String USER_AGENT = "Mozilla/5.0";

	private ServerUtils() {
		// nop
	}

	// HTTP GET request
	public static String sendGet(String url, String session) throws Exception {

		URL obj = new URL(url);
		URLConnection con =  obj.openConnection();

		// optional default is GET
		if (con instanceof HttpURLConnection) {
		  ((HttpURLConnection) con).setRequestMethod("GET");
		}

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Cookie", "JSESSIONID="+session);		

		if (con instanceof HttpURLConnection) {
			int responseCode =  ((HttpURLConnection) con).getResponseCode();
			if (responseCode < 200 || responseCode > 299) {
				throw new IOException("Http Error "+responseCode);
			}
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return response.toString();

	}

	// HTTP POST request
	public static String sendPost(String url, String data, String session) throws Exception {

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Cookie", "JSESSIONID="+session);		

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if (responseCode < 200 || responseCode > 299) {
			throw new IOException("Http Error "+responseCode);
		}


		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	
	public static Object sendSerializedObject(String url, Object obj, String session) throws Throwable {
		URL urlServlet = new URL(url);
        URLConnection con = urlServlet.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Cookie", "JSESSIONID="+session);		
        con.setRequestProperty("Content-Type", "application/x-java-serialized-object");
        Object result = null;
        if (con != null) {
          OutputStream outstream = con.getOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(outstream);
          oos.writeObject(obj);
          oos.flush();
          oos.close();
          InputStream instr = con.getInputStream();
          if (instr != null) {
            ObjectInputStream inputFromServlet = new ObjectInputStream(instr) {
            	
            	@Override
            	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            		if (Thread.currentThread().getContextClassLoader() != null) {
            			 return Class.forName(desc.getName(), false, Thread.currentThread().getContextClassLoader());
            		}
            		return super.resolveClass(desc);
            	}
            	
            };
            if (inputFromServlet != null) {
			result = inputFromServlet.readObject();
              inputFromServlet.close();
            }
            instr.close();
          }
        }
        return result;
	}

}
