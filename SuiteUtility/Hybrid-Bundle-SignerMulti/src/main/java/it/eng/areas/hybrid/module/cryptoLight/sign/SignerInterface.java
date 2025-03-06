package it.eng.areas.hybrid.module.cryptoLight.sign;


import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;


public interface SignerInterface {
	
	public void generate(InputStream src, OutputStream dest, PrivateKey pkey, Certificate[] chain) throws Exception;

}
