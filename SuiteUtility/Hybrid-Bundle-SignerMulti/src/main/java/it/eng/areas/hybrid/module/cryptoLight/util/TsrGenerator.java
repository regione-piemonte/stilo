package it.eng.areas.hybrid.module.cryptoLight.util;


import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.tsp.TimeStampToken;

import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
 
public class TsrGenerator {
	
	public final static Logger logger = Logger.getLogger( TsrGenerator.class );
 
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
		logger.info("Url server marca  " + url);
		TSAClientBouncyCastle tsc = null;
		boolean tsaAuth = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_TSAAUTH ) ;
		if( tsaAuth ){
			String username = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAUSER ); 
			String password = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_TSAPASSWORD ); 
			//logger.info("tsaUser: " + username + " tsaPass:" +password);
			tsc = new TSAClientBouncyCastle(url, username, password);
		} else {
			tsc = new TSAClientBouncyCastle(url);
		}
		
		byte[] timeStampTokenEncoded=tsc.getTimeStampToken(fingerprint);
		 tst=new TimeStampToken(new CMSSignedData(timeStampTokenEncoded));
        return tst;
	}
}
