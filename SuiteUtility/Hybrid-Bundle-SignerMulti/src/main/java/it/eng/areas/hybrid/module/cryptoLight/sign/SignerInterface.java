/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;


public interface SignerInterface {
	
	public void generate(InputStream src, OutputStream dest, PrivateKey pkey, Certificate[] chain) throws Exception;

}
