/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.File;
/**
 * helper di utility per semplificare al lettura della conf
 * @author Russo
 *
 */
public class FileOpConfigHelper {
	
	public static  File makeTempDir(String requestKey){
		return FileoperationContextProvider.getApplicationContext().getBean(FileOpConfig.class).makeTempDir(requestKey);
	}
	
	public static File getTempDir(){
		return FileoperationContextProvider.getApplicationContext().getBean(FileOpConfig.class).getTempDirectory();
	}
}
