package it.eng.hybrid.module.jpedal.io;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.RecipientInformation;

import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.security.Key;
import java.security.cert.Certificate;

public class CertificateReader {

	public final static Logger logger = Logger.getLogger(CertificateReader.class);
	
	//<start-adobe><start-wrap>
	public static byte[] readCertificate(byte[][] recipients, Certificate certificate, Key key) {
		
		byte[] envelopedData=null;
		
		/**
         * values for BC
         */
        String provider="BC";

		/**
         * loop through all and get data if match found
         */
        for (byte[] recipient : recipients) {

            try {
                CMSEnvelopedData recipientEnvelope = new CMSEnvelopedData(recipient);

                Object[] recipientList = recipientEnvelope.getRecipientInfos().getRecipients().toArray();
                int listCount = recipientList.length;

                for (int ii = 0; ii < listCount; ii++) {
                    RecipientInformation recipientInfo = (RecipientInformation) recipientList[ii];

//                    if (recipientInfo.getRID().match(certificate)) {
//                        envelopedData = recipientInfo.getContent(key, provider);
//                        ii = listCount;
//                    }
                }
            } catch (Exception e) {
                //tell user and log
                logger.info("Exception: "+e.getMessage());
            }
        }

        return envelopedData;
	}
	
	//<end-wrap><end-adobe>

}
