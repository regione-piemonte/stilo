/*
* Copyright (c) 2001 Sun Microsystems, Inc. All Rights Reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* -Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
*
* -Redistribution in binary form must reproduct the above copyright notice,
* this list of conditions and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* Neither the name of Sun Microsystems, Inc. or the names of contributors may
* be used to endorse or promote products derived from this software without
* specific prior written permission.
*
* This software is provided "AS IS," without a warranty of any kind. ALL
* EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
* IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
* NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
* LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
* OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
* LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
* INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
* CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
* OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGES.
*
* You acknowledge that Software is not designed,licensed or intended for use in
* the design, construction, operation or maintenance of any nuclear facility.
*/
package it.eng.hybrid.module.jpedal.sun;

//import java.util.ResourceBundle;
public class PropertyUtil
{
	//private static ResourceBundle b;
	public static String getString( String key )
	{
		//if (b == null) {
		//  b = getBundle();
		//}
		//return b.getString(key);
		return key;
	}
	
	/** Get bundle from .properties files in the current dir. */
	//private static ResourceBundle getBundle()
	//{
		/**
		 ResourceBundle bundle = null;
		 
		 InputStream in = null;
		 
		 try {
		 try {
		 in = PropertyUtil.class.getResourceAsStream("properties");
		 } catch(Exception e1) {
		 }
		 if(in == null) {
		 in = new FileInputStream("properties");
		 }
		 if (in != null) {
		 bundle = new PropertyResourceBundle(in);
		 return bundle;
		 }
		 }
		 
		 */
	//	return null;
	//}
}
