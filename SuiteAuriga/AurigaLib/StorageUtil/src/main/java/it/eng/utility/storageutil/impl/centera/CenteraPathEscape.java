/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.commons.lang3.StringEscapeUtils;

public class CenteraPathEscape extends StringEscapeUtils {

//	public static final String separator = "/";
	public static final String separator = File.separator;
	public static final String separatorEscape = "::";
	
	public static final String escapeCenteraTag(String str){
		return str.replace(separator, separatorEscape);
	}
	
	public static final String unescapeCenteraTag(String str){
		return str.replace(separatorEscape, separator);
	}
	
}
