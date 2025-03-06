package it.eng.client.applet.operation.pdf;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
 


public class TSABouncy extends TSAClientBouncyCastle {

	
	public TSABouncy(String url) {
		super(url);
		
	}

	public byte[] getTimeStampTokenExt(byte[] imprint) throws Exception {
       return super.getTimeStampToken(imprint);
    }
	
	
	@Override
	protected byte[] getTSAResponse(byte[] requestBytes) throws IOException {

		System.setProperty("http.proxyHost", "proxy.eng.it");
		System.setProperty("http.proxyPort", "3128");
		URL url=new URL(this.tsaURL);
		
		URLConnection tsaConnection;
        tsaConnection = (URLConnection) url.openConnection();
        
        String encoded = new String
	      (Base64.encodeBase64(new String("mirigo:fv54kagz").getBytes()));
        tsaConnection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
        //tsaConnection.connect();
                
        tsaConnection.setDoInput(true);
        tsaConnection.setDoOutput(true);
        tsaConnection.setUseCaches(false);
        tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
        //tsaConnection.setRequestProperty("Content-Transfer-Encoding", "base64");
        tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");
        
        if ((tsaUsername != null) && !tsaUsername.equals("") ) {
            String userPassword = tsaUsername + ":" + tsaPassword;
            tsaConnection.setRequestProperty("Authorization", "Basic " +
                new String(Base64.encodeBase64(userPassword.getBytes())));
        }
        OutputStream out = tsaConnection.getOutputStream();
        out.write(requestBytes);
        out.close();
        
        // Get TSA response as a byte array
        InputStream inp = tsaConnection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inp.read(buffer, 0, buffer.length)) >= 0) {
            baos.write(buffer, 0, bytesRead);
        }
        byte[] respBytes = baos.toByteArray();
        
        String encoding = tsaConnection.getContentEncoding();
        if (encoding != null && encoding.equalsIgnoreCase("base64")) {
            respBytes = org.bouncycastle.util.encoders.Base64.decode(new String(respBytes));
        }
        return respBytes;
	}
	
}
