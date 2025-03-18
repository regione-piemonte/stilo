/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

public abstract class AbstractSigner {

	 
	public static String dir = System.getProperty("user.home");
	public static String cryptodll = "dllCrypto";
	public static String cryptoConfig = cryptodll+File.separator+"config";
	public static String cryptoConfigFile = cryptoConfig+File.separator+"crypto.config";
	
	public static String externalDllPath = "dllPath";
	
    
		
}