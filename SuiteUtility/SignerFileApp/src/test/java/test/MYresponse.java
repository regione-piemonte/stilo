package test;
import java.io.IOException;
import java.io.InputStream;

import org.bouncycastle.asn1.cmp.PKIStatus;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TSPValidationException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.bouncycastle.util.Arrays;


public class MYresponse extends TimeStampResponse{


	
	public MYresponse(InputStream in) throws TSPException, IOException {
		super(in);
		// TODO Auto-generated constructor stub
	}

	public void validate(
	        TimeStampRequest request,TimeStampToken token)throws TSPException
	    {
	        TimeStampToken tok = token;
	        
	        if (tok != null)
	        {
	            TimeStampTokenInfo  tstInfo = tok.getTimeStampInfo();
	            
	            if (request.getNonce() != null && !request.getNonce().equals(tstInfo.getNonce()))
	            {
	                throw new TSPValidationException("response contains wrong nonce value.");
	            }
	            
	            if (this.getStatus() != PKIStatus.GRANTED && this.getStatus() != PKIStatus.GRANTED_WITH_MODS)
	            {
	                throw new TSPValidationException("time stamp token found in failed request.");
	            }
	            
	            if (!Arrays.constantTimeAreEqual(request.getMessageImprintDigest(), tstInfo.getMessageImprintDigest()))
	            {
	                throw new TSPValidationException("response for different message imprint digest.");
	            }
	            
	            if (!tstInfo.getMessageImprintAlgOID().equals(request.getMessageImprintAlgOID()))
	            {
	                throw new TSPValidationException("response for different message imprint algorithm.");
	            }

	            Attribute scV1 = tok.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
	            Attribute scV2 = tok.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);

	            if (scV1 == null && scV2 == null)
	            {
	                throw new TSPValidationException("no signing certificate attribute present.");
	            }

	            if (scV1 != null && scV2 != null)
	            {
	                throw new TSPValidationException("conflicting signing certificate attributes present.");
	            }

	            if (request.getReqPolicy() != null && !request.getReqPolicy().equals(tstInfo.getPolicy()))
	            {
	                throw new TSPValidationException("TSA policy wrong for request.");
	            }
	        }
	        else if (this.getStatus() == PKIStatus.GRANTED || this.getStatus() == PKIStatus.GRANTED_WITH_MODS)
	        {
	            throw new TSPValidationException("no time stamp token found and one expected.");
	        }
	    }

	
	
}
