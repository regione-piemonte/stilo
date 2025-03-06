package it.eng.client.applet.operation;

import it.eng.client.applet.bean.PrivateKeyAndCert;

import java.io.File;
import java.io.OutputStream;
import java.security.AuthProvider;
import java.security.cert.X509Certificate;

public interface ISigner {

	public abstract boolean firma(File inputFile, OutputStream outputStream,
			PrivateKeyAndCert pvc, AuthProvider provider, boolean timemark,
			boolean detached, boolean congiunta,boolean isSigned) throws Exception;
	
	public  void addCounterSignature(File input,
			OutputStream outputStream,
			PrivateKeyAndCert pvc,
			X509Certificate aCertToBeSign, 
			AuthProvider provider
			) throws Exception;

}