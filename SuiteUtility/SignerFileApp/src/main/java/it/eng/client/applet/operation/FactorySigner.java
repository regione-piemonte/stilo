package it.eng.client.applet.operation;

import it.eng.common.type.SignerType;

/**
 * ritorna il signer {@link ISigner} da usare 
 * @author Russo
 *
 */
public class FactorySigner {
	public static ISigner getSigner(SignerType signerType){
		ISigner signer=null;
		switch (signerType) {
		case PDF:
			signer=new PDFSigner();
			break;
		
		case P7M:
			signer=new P7MSigner();
			break;
			
		default:
			signer=new CadesSigner();
			break;
		}
		return signer;
	}
}
