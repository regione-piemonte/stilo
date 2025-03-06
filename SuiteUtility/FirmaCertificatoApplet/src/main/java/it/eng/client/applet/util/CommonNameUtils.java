package it.eng.client.applet.util;

import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.common.LogWriter;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class CommonNameUtils {
	
	public static String extractCommonName(X509Certificate certificate) throws SmartCardException, CertificateEncodingException {
		X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
		if(x500name!=null ){
			RDN[] cns = x500name.getRDNs( BCStyle.CN );
			if( cns!=null && cns.length>0){
				RDN cn = cns[0];
				if( cn!=null && cn.getFirst()!=null && cn.getFirst().getValue()!=null){
					String commonNameToSend = IETFUtils.valueToString(cn.getFirst().getValue());
					LogWriter.writeLog("commonNameToSend " + commonNameToSend );
					if( commonNameToSend.contains("'")){
						commonNameToSend = commonNameToSend.replaceAll("'", "\\\\'");
						LogWriter.writeLog("commonNameToSend " + commonNameToSend );
					}
					
					return commonNameToSend;
				}
			}
		}
		throw new SmartCardException( Messages.getMessage( MessageKeys.MSG_UNABLE_TO_EXTRACT_CN_FROM_CERTIFICATE ) );
	}
}
