package it.eng.client.applet.operation;


import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.tsp.TimeStampToken;

import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
 
public class TsrGenerator {
 private String url;
 
	public TsrGenerator(String url){
		this.url=url;
	}
	
	public TimeStampToken generateTsrData(byte[] fingerprint)throws Exception
	{
		TimeStampToken tst=null;
        //con httpclient
//        TimeStampTokenGetter tstGetter =  new TimeStampTokenGetter
//		(
//		    new PostMethod(url),
//	        fingerprint,
//	        new BigInteger(""+new SecureRandom().nextInt())//BigInteger.valueOf(0)//use a generator!?
//        );
        
//        tst = tstGetter.getTimeStampToken();
		//ora utilizzo quello di itext
		TSAClientBouncyCastle tsc = new TSAClientBouncyCastle(url);
		byte[] timeStampTokenEncoded=tsc.getTimeStampToken(fingerprint);
		 tst=new TimeStampToken(new CMSSignedData(timeStampTokenEncoded));
        return tst;
	}
}
