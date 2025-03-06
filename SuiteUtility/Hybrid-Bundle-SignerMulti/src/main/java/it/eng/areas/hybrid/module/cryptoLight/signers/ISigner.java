package it.eng.areas.hybrid.module.cryptoLight.signers;

import it.eng.areas.hybrid.module.cryptoLight.sign.FileBean;

import java.security.AuthProvider;
import java.security.cert.X509Certificate;

public interface ISigner {

	public abstract boolean firma(FileBean bean,
			PrivateKeyAndCert pvc, AuthProvider provider, boolean timemark,
			boolean detached, boolean congiunta,boolean isSigned) throws Exception;
	
	public boolean addCounterSignature(FileBean bean,
			PrivateKeyAndCert pvc,
			X509Certificate aCertToBeSign, 
			AuthProvider provider
			) throws Exception;

}