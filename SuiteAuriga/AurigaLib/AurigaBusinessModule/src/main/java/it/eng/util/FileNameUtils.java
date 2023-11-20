/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileNameUtils {

	public static String normalize(String fileName) {
		String newFileName = fileName; 
		newFileName = newFileName.replace(" ", "_");
		newFileName = newFileName.replace("\\", "_");
		newFileName = newFileName.replace("/", "_");
		newFileName = newFileName.replace(":", "_");
		newFileName = newFileName.replace("*", "_");
		newFileName = newFileName.replace("?", "_");
		newFileName = newFileName.replace("<", "_");
		newFileName = newFileName.replace(">", "_");
		newFileName = newFileName.replace("|", "_");	
		newFileName = newFileName.replace("\"", "_");	
		return newFileName;
	}
	
}
